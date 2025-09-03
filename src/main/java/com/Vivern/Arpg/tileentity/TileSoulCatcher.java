//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.tileentity;

import com.Vivern.Arpg.elements.IWeapon;
import com.Vivern.Arpg.elements.SoulStone;
import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.WeaponDamage;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.renders.ParticleTentacle;
import com.google.common.base.Predicate;
import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;

public class TileSoulCatcher extends TileEntity implements ITickable, ISidedInventory, ITileEntitySynchronize {
   public static ResourceLocation effectTex = new ResourceLocation("arpg:textures/generic_beam6.png");
   public static float maxdamage = 2.0F;
   public float damage = maxdamage;
   public int ticksExisted = 0;
   public float rotationYaw;
   public float rotationPitch;
   public int lastAttackEntityid;
   public NonNullList<ItemStack> stack = NonNullList.withSize(1, ItemStack.EMPTY);
   public static final int[] SLOT = new int[]{0};
   public static Random rand = new Random();
   public static Predicate<IBlockState> CATCHERS = new Predicate<IBlockState>() {
      public boolean apply(IBlockState input) {
         return input.getBlock() == BlocksRegister.SOULCATCHER;
      }
   };

   @Override
   public void onClient(double... args) {
      if (args.length == 4) {
         Vec3d start = new Vec3d(args[0], args[1], args[2]);
         Vec3d end = new Vec3d(this.pos.getX() + 0.5, this.pos.getY() + 0.5, this.pos.getZ() + 0.5);
         float size = (float)args[3] / maxdamage;
         Vec3d vec = GetMOP.PitchYawToVec3d(this.rotationPitch + rand.nextInt(13) - 6.0F, this.rotationYaw + rand.nextInt(13) - 6.0F);
         ParticleTentacle.PTPOINTsimple pointstart = new ParticleTentacle.PTPOINTsimple(
            start, new Vec3d(rand.nextFloat() - 0.5F, rand.nextFloat() - 0.5F, rand.nextFloat() - 0.5F).normalize()
         );
         ParticleTentacle.PTPOINTsimple pointend = new ParticleTentacle.PTPOINTsimple(end, vec);
         ParticleTentacle particleTentacle = new ParticleTentacle(
            effectTex,
            (0.1675F + rand.nextFloat() * 0.05F) * size,
            0.1F,
            0.0F,
            19 + rand.nextInt(9),
            240,
            this.world,
            pointstart,
            pointend,
            0.0F,
            0.0F,
            0.0F,
            1.0F - rand.nextFloat() * 0.3F,
            1.0F - rand.nextFloat() * 0.1F,
            1.0F - rand.nextFloat() * 0.1F,
            true,
            11,
            (1.0F + rand.nextFloat() * 0.25F) * 5.0F
         );
         particleTentacle.alphaGlowing = true;
         this.world.spawnEntity(particleTentacle);
      }
   }

   public void update() {
      this.ticksExisted++;
      if (this.ticksExisted % 60 == 0 && this.getStackInSlot(0).getItem() == ItemsRegister.SOULSTONE) {
         int soulid = SoulStone.getSoul(this.getStackInSlot(0));
         if (soulid == 0) {
            Vec3d vec = GetMOP.PitchYawToVec3d(this.rotationPitch, this.rotationYaw);
            Vec3d thisvec = new Vec3d(
               this.pos.getX() + 0.5, this.pos.getY() + 0.5, this.pos.getZ() + 0.5
            );
            EntityLivingBase entityLivingBase = GetMOP.entityRayTrace(
               this.world, thisvec, thisvec.add(vec.scale(5.0)), 2.5, 1.5, CATCHERS
            );
            if (entityLivingBase != null && entityLivingBase.getHealth() > 0.0F) {
               if (Weapons.dealDamage(new WeaponDamage(null, null, null, false, false, thisvec, WeaponDamage.soul), this.damage, null, entityLivingBase, true)) {
                  if (this.damage > 0.1) {
                     ITileEntitySynchronize.sendSynchronize(
                        this,
                        48.0,
                        entityLivingBase.posX,
                        entityLivingBase.posY + entityLivingBase.height / 2.0F,
                        entityLivingBase.posZ,
                        this.damage
                     );
                  }

                  if (entityLivingBase.getHealth() <= 0.0F) {
                     Random rand = entityLivingBase.getRNG();
                     if (!(entityLivingBase instanceof EntityPlayer) && rand.nextFloat() < entityLivingBase.getMaxHealth() / 20.0F) {
                        ItemStack filledStone = SoulStone.getFilledSoulStoneForMob(entityLivingBase);
                        this.setInventorySlotContents(0, filledStone);
                        this.world
                           .playSound(
                              (EntityPlayer)null,
                              this.pos.getX() + 0.5,
                              this.pos.getY() + 0.5,
                              this.pos.getZ() + 0.5,
                              Sounds.soul_steal,
                              SoundCategory.BLOCKS,
                              1.3F,
                              0.9F + rand.nextFloat() / 5.0F
                           );
                        IWeapon.fireEffect(
                           ItemsRegister.SOULSTONE,
                           entityLivingBase,
                           this.world,
                           32.0,
                           entityLivingBase.posX,
                           entityLivingBase.posY + entityLivingBase.height / 2.0F,
                           entityLivingBase.posZ,
                           thisvec.x,
                           thisvec.y,
                           thisvec.z,
                           0.0,
                           0.0,
                           0.0
                        );
                     }
                  }
               }

               if (this.lastAttackEntityid == entityLivingBase.getEntityId()) {
                  this.damage *= 0.8F;
               } else {
                  this.damage = maxdamage;
                  this.lastAttackEntityid = entityLivingBase.getEntityId();
               }
            }
         }
      }
   }

   public void readFromNBT(NBTTagCompound compound) {
      this.stack = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
      ItemStackHelper.loadAllItems(compound, this.stack);
      if (compound.hasKey("rotationYaw")) {
         this.rotationYaw = compound.getFloat("rotationYaw");
      }

      if (compound.hasKey("rotationPitch")) {
         this.rotationPitch = compound.getFloat("rotationPitch");
      }

      super.readFromNBT(compound);
   }

   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
      ItemStackHelper.saveAllItems(compound, this.stack);
      compound.setFloat("rotationYaw", this.rotationYaw);
      compound.setFloat("rotationPitch", this.rotationPitch);
      return super.writeToNBT(compound);
   }

   public void read(NBTTagCompound compound) {
      if (compound.hasKey("rotationYaw")) {
         this.rotationYaw = compound.getFloat("rotationYaw");
      }

      if (compound.hasKey("rotationPitch")) {
         this.rotationPitch = compound.getFloat("rotationPitch");
      }

      super.readFromNBT(compound);
   }

   public NBTTagCompound write(NBTTagCompound compound) {
      compound.setFloat("rotationYaw", this.rotationYaw);
      compound.setFloat("rotationPitch", this.rotationPitch);
      return super.writeToNBT(compound);
   }

   public NBTTagCompound getUpdateTag() {
      NBTTagCompound compound = super.getUpdateTag();
      this.write(compound);
      return compound;
   }

   public void handleUpdateTag(NBTTagCompound compound) {
      this.read(compound);
      super.handleUpdateTag(compound);
   }

   public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
      NBTTagCompound compound = packet.getNbtCompound();
      this.read(compound);
   }

   public SPacketUpdateTileEntity getUpdatePacket() {
      NBTTagCompound compound = new NBTTagCompound();
      this.write(compound);
      return new SPacketUpdateTileEntity(this.pos, 1, compound);
   }

   public int getSizeInventory() {
      return 1;
   }

   public boolean isEmpty() {
      return ((ItemStack)this.stack.get(0)).isEmpty();
   }

   public ItemStack getStackInSlot(int index) {
      return (ItemStack)this.stack.get(index);
   }

   public ItemStack decrStackSize(int index, int count) {
      return ItemStackHelper.getAndSplit(this.stack, index, count);
   }

   public ItemStack removeStackFromSlot(int index) {
      return ItemStackHelper.getAndRemove(this.stack, index);
   }

   public void setInventorySlotContents(int index, ItemStack stack) {
      this.stack.set(index, stack);
   }

   public int getInventoryStackLimit() {
      return 1;
   }

   public boolean isUsableByPlayer(EntityPlayer player) {
      return false;
   }

   public void openInventory(EntityPlayer player) {
   }

   public void closeInventory(EntityPlayer player) {
   }

   public boolean isItemValidForSlot(int index, ItemStack stack) {
      return stack.getItem() == ItemsRegister.SOULSTONE;
   }

   public int getField(int id) {
      return 0;
   }

   public void setField(int id, int value) {
   }

   public int getFieldCount() {
      return 0;
   }

   public void clear() {
      this.stack.set(0, ItemStack.EMPTY);
   }

   public String getName() {
      return "tile_soul_catcher";
   }

   public boolean hasCustomName() {
      return false;
   }

   public int[] getSlotsForFace(EnumFacing side) {
      return SLOT;
   }

   public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
      return ((ItemStack)this.stack.get(0)).isEmpty() && this.isItemValidForSlot(index, itemStackIn) && direction != EnumFacing.DOWN;
   }

   public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
      return SoulStone.getSoul((ItemStack)this.stack.get(0)) != 0 && direction == EnumFacing.DOWN;
   }
}
