//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.network.PacketFallingBlockToClients;
import com.Vivern.Arpg.network.PacketHandler;
import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AdvancedFallingBlock extends Entity {
   public IBlockState fallTile;
   public int fallTime;
   public boolean shouldDropItem = true;
   public boolean dontSetBlock;
   public boolean hurtEntities;
   public int fallHurtMax = 40;
   public float fallHurtAmount = 2.0F;
   public NBTTagCompound tileEntityData;
   public static final DataParameter<BlockPos> ORIGIN = EntityDataManager.createKey(AdvancedFallingBlock.class, DataSerializers.BLOCK_POS);

   public AdvancedFallingBlock(World worldIn) {
      super(worldIn);
      this.setSize(0.98F, 0.98F);
   }

   public AdvancedFallingBlock(World worldIn, double x, double y, double z, IBlockState fallingBlockState) {
      super(worldIn);
      this.fallTile = fallingBlockState;
      this.preventEntitySpawning = true;
      this.setSize(0.98F, 0.98F);
      this.setPosition(x, y + (1.0F - this.height) / 2.0F, z);
      this.motionX = 0.0;
      this.motionY = 0.0;
      this.motionZ = 0.0;
      this.prevPosX = x;
      this.prevPosY = y;
      this.prevPosZ = z;
      this.setOrigin(new BlockPos(this));
   }

   public boolean canBeAttackedWithItem() {
      return false;
   }

   public void setOrigin(BlockPos p_184530_1_) {
      this.dataManager.set(ORIGIN, p_184530_1_);
   }

   @SideOnly(Side.CLIENT)
   public BlockPos getOrigin() {
      return (BlockPos)this.dataManager.get(ORIGIN);
   }

   protected boolean canTriggerWalking() {
      return false;
   }

   protected void entityInit() {
      this.dataManager.register(ORIGIN, BlockPos.ORIGIN);
   }

   public boolean canBeCollidedWith() {
      return !this.isDead;
   }

   public void onUpdate() {
      if (this.fallTile != null) {
         if (!this.world.isRemote && (this.ticksExisted <= 3 || this.ticksExisted % 20 == 0)) {
            PacketFallingBlockToClients packet = new PacketFallingBlockToClients();
            packet.write(
               this.fallTile.getBlock().getRegistryName().toString(), this.getEntityId(), this.fallTile.getBlock().getMetaFromState(this.fallTile)
            );
            PacketHandler.sendToAllAround(packet, this.world, this.posX, this.posY, this.posZ, 64.0);
         }

         Block block = this.fallTile.getBlock();
         if (this.fallTile.getMaterial() == Material.AIR) {
            this.setDead();
         } else {
            this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            this.prevPosZ = this.posZ;
            if (!this.hasNoGravity()) {
               this.motionY -= 0.04F;
            }

            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
            if (!this.world.isRemote) {
               BlockPos blockpos1 = new BlockPos(this);
               boolean flag = this.fallTile.getBlock() == Blocks.CONCRETE_POWDER;
               boolean flag1 = flag && this.world.getBlockState(blockpos1).getMaterial() == Material.WATER;
               double d0 = this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ;
               if (flag && d0 > 1.0) {
                  RayTraceResult raytraceresult = this.world
                     .rayTraceBlocks(
                        new Vec3d(this.prevPosX, this.prevPosY, this.prevPosZ),
                        new Vec3d(this.posX, this.posY, this.posZ),
                        true
                     );
                  if (raytraceresult != null && this.world.getBlockState(raytraceresult.getBlockPos()).getMaterial() == Material.WATER) {
                     blockpos1 = raytraceresult.getBlockPos();
                     flag1 = true;
                  }
               }

               if (!this.onGround && !flag1) {
                  if (this.fallTime > 40 && !this.world.isRemote && (blockpos1.getY() < 1 || blockpos1.getY() > 256)
                     || this.fallTime > 80) {
                     if (this.shouldDropItem && this.world.getGameRules().getBoolean("doEntityDrops")) {
                        for (ItemStack stack : this.fallTile.getBlock().getDrops(this.world, blockpos1, this.fallTile, 0)) {
                           this.entityDropItem(stack, 0.0F);
                        }
                     }

                     this.setDead();
                  }
               } else {
                  IBlockState iblockstate = this.world.getBlockState(blockpos1);
                  if (this.world.isAirBlock(new BlockPos(this.posX, this.posY - 0.01F, this.posZ))
                     && !flag1
                     && BlockFalling.canFallThrough(
                        this.world.getBlockState(new BlockPos(this.posX, this.posY - 0.01F, this.posZ))
                     )) {
                     this.onGround = false;
                     return;
                  }

                  this.motionX *= 0.7F;
                  this.motionZ *= 0.7F;
                  this.motionY *= -0.5;
                  if (iblockstate.getBlock() != Blocks.PISTON_EXTENSION) {
                     this.setDead();
                     if (this.dontSetBlock) {
                        if (block instanceof BlockFalling) {
                           ((BlockFalling)block).onBroken(this.world, blockpos1);
                        }
                     } else if (this.world.mayPlace(block, blockpos1, true, EnumFacing.UP, this)
                        && (flag1 || !BlockFalling.canFallThrough(this.world.getBlockState(blockpos1.down())))
                        && this.world.setBlockState(blockpos1, this.fallTile, 3)) {
                        if (block instanceof BlockFalling) {
                           ((BlockFalling)block).onEndFalling(this.world, blockpos1, this.fallTile, iblockstate);
                        }

                        if (this.tileEntityData != null && block.hasTileEntity(this.fallTile)) {
                           TileEntity tileentity = this.world.getTileEntity(blockpos1);
                           if (tileentity != null) {
                              NBTTagCompound nbttagcompound = tileentity.writeToNBT(new NBTTagCompound());

                              for (String s : this.tileEntityData.getKeySet()) {
                                 NBTBase nbtbase = this.tileEntityData.getTag(s);
                                 if (!"x".equals(s) && !"y".equals(s) && !"z".equals(s)) {
                                    nbttagcompound.setTag(s, nbtbase.copy());
                                 }
                              }

                              tileentity.readFromNBT(nbttagcompound);
                              tileentity.markDirty();
                           }
                        }
                     } else if (this.shouldDropItem && this.world.getGameRules().getBoolean("doEntityDrops")) {
                        for (ItemStack stack : this.fallTile.getBlock().getDrops(this.world, blockpos1, this.fallTile, 0)) {
                           this.entityDropItem(stack, 0.0F);
                        }
                     }
                  }
               }
            }

            this.motionX *= 0.98F;
            this.motionY *= 0.98F;
            this.motionZ *= 0.98F;
         }
      }
   }

   public void fall(float distance, float damageMultiplier) {
      Block block = this.fallTile.getBlock();
      if (this.hurtEntities) {
         int i = MathHelper.ceil(distance - 1.0F);
         if (i > 0) {
            List<Entity> list = Lists.newArrayList(this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox()));
            boolean flag = block == Blocks.ANVIL;
            DamageSource damagesource = flag ? DamageSource.ANVIL : DamageSource.FALLING_BLOCK;

            for (Entity entity : list) {
               entity.attackEntityFrom(damagesource, Math.min(MathHelper.floor(i * this.fallHurtAmount), this.fallHurtMax));
            }

            if (flag && this.rand.nextFloat() < 0.05F + i * 0.05) {
               int j = (Integer)this.fallTile.getValue(BlockAnvil.DAMAGE);
               if (++j > 2) {
                  this.dontSetBlock = true;
               } else {
                  this.fallTile = this.fallTile.withProperty(BlockAnvil.DAMAGE, j);
               }
            }
         }
      }
   }

   public static void registerFixesFallingBlock(DataFixer fixer) {
   }

   protected void writeEntityToNBT(NBTTagCompound compound) {
      Block block = this.fallTile != null ? this.fallTile.getBlock() : Blocks.AIR;
      ResourceLocation resourcelocation = (ResourceLocation)Block.REGISTRY.getNameForObject(block);
      compound.setString("Block", resourcelocation == null ? "" : resourcelocation.toString());
      compound.setByte("Data", (byte)block.getMetaFromState(this.fallTile));
      compound.setInteger("Time", this.fallTime);
      compound.setBoolean("DropItem", this.shouldDropItem);
      compound.setBoolean("HurtEntities", this.hurtEntities);
      compound.setFloat("FallHurtAmount", this.fallHurtAmount);
      compound.setInteger("FallHurtMax", this.fallHurtMax);
      if (this.tileEntityData != null) {
         compound.setTag("TileEntityData", this.tileEntityData);
      }
   }

   protected void readEntityFromNBT(NBTTagCompound compound) {
      int i = compound.getByte("Data") & 255;
      if (compound.hasKey("Block", 8)) {
         this.fallTile = Block.getBlockFromName(compound.getString("Block")).getStateFromMeta(i);
      } else if (compound.hasKey("TileID", 99)) {
         this.fallTile = Block.getBlockById(compound.getInteger("TileID")).getStateFromMeta(i);
      } else {
         this.fallTile = Block.getBlockById(compound.getByte("Tile") & 255).getStateFromMeta(i);
      }

      this.fallTime = compound.getInteger("Time");
      Block block = this.fallTile.getBlock();
      if (compound.hasKey("HurtEntities", 99)) {
         this.hurtEntities = compound.getBoolean("HurtEntities");
         this.fallHurtAmount = compound.getFloat("FallHurtAmount");
         this.fallHurtMax = compound.getInteger("FallHurtMax");
      } else if (block == Blocks.ANVIL) {
         this.hurtEntities = true;
      }

      if (compound.hasKey("DropItem", 99)) {
         this.shouldDropItem = compound.getBoolean("DropItem");
      }

      if (compound.hasKey("TileEntityData", 10)) {
         this.tileEntityData = compound.getCompoundTag("TileEntityData");
      }

      if (block == null || block.getDefaultState().getMaterial() == Material.AIR) {
         this.fallTile = Blocks.SAND.getDefaultState();
      }
   }

   public void setHurtEntities(boolean p_145806_1_) {
      this.hurtEntities = p_145806_1_;
   }

   public void addEntityCrashInfo(CrashReportCategory category) {
      super.addEntityCrashInfo(category);
      if (this.fallTile != null) {
         Block block = this.fallTile.getBlock();
         category.addCrashSection("Immitating block ID", Block.getIdFromBlock(block));
         category.addCrashSection("Immitating block data", block.getMetaFromState(this.fallTile));
      }
   }

   @SideOnly(Side.CLIENT)
   public World getWorldObj() {
      return this.world;
   }

   @SideOnly(Side.CLIENT)
   public boolean canRenderOnFire() {
      return false;
   }

   @Nullable
   public IBlockState getBlock() {
      return this.fallTile;
   }

   public boolean ignoreItemEntityData() {
      return true;
   }
}
