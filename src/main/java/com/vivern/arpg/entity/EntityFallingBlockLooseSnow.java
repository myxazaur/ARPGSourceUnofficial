package com.vivern.arpg.entity;

import com.vivern.arpg.main.BlocksRegister;
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
import net.minecraft.entity.item.EntityFallingBlock;
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
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityFallingBlockLooseSnow extends Entity {
   private IBlockState fallTile;
   public int fallTime;
   public boolean shouldDropItem = true;
   private boolean dontSetBlock;
   private boolean hurtEntities;
   private int fallHurtMax = 40;
   private float fallHurtAmount = 2.0F;
   public NBTTagCompound tileEntityData;
   protected static final DataParameter<BlockPos> ORIGIN = EntityDataManager.createKey(EntityFallingBlock.class, DataSerializers.BLOCK_POS);

   public EntityFallingBlockLooseSnow(World worldIn) {
      super(worldIn);
   }

   public EntityFallingBlockLooseSnow(World worldIn, double x, double y, double z, IBlockState fallingBlockState) {
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
      Block block = this.fallTile.getBlock();
      if (this.fallTile.getMaterial() == Material.AIR) {
         this.setDead();
      } else {
         this.prevPosX = this.posX;
         this.prevPosY = this.posY;
         this.prevPosZ = this.posZ;
         if (this.fallTime++ == 0) {
            BlockPos blockpos = new BlockPos(this);
            if (this.world.getBlockState(blockpos).getBlock() == block) {
               this.world.setBlockToAir(blockpos);
            } else if (!this.world.isRemote) {
               this.setDead();
               return;
            }
         }

         if (!this.hasNoGravity()) {
            this.motionY -= 0.04F;
         }

         this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
         if (!this.world.isRemote) {
            BlockPos blockpos1 = new BlockPos(this);
            double d0 = this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ;
            IBlockState iblockstate = this.world.getBlockState(blockpos1);
            this.motionX *= 0.7F;
            this.motionZ *= 0.7F;
            this.motionY *= -0.5;
            if (iblockstate.getBlock() != Blocks.PISTON_EXTENSION) {
               this.setDead();
               if (!this.dontSetBlock) {
                  if (this.world.getBlockState(blockpos1.down()).getBlock() != BlocksRegister.LOOSESNOW) {
                     if (this.world.mayPlace(block, blockpos1, true, EnumFacing.UP, (Entity)null)
                        && !BlockFalling.canFallThrough(this.world.getBlockState(blockpos1.down()))
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
                        this.entityDropItem(new ItemStack(block, 1, block.damageDropped(this.fallTile)), 0.0F);
                     }
                  }
               } else if (block instanceof BlockFalling) {
                  ((BlockFalling)block).onBroken(this.world, blockpos1);
               }
            }
         }

         this.motionX *= 0.98F;
         this.motionY *= 0.98F;
         this.motionZ *= 0.98F;
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
