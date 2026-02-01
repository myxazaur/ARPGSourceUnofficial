package com.vivern.arpg.entity;

import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.Sounds;
import com.google.common.base.Predicate;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.Validate;

public class EntityHangingAllSides extends EntityHanging implements IEntitySynchronize {
   private static final Predicate<Entity> IS_HANGING_ENTITY = new Predicate<Entity>() {
      public boolean apply(@Nullable Entity p_apply_1_) {
         return p_apply_1_ instanceof EntityHanging;
      }
   };
   private static final DataParameter<ItemStack> ITEM = EntityDataManager.createKey(EntityHangingAllSides.class, DataSerializers.ITEM_STACK);
   private static final DataParameter<Integer> ROTATION = EntityDataManager.createKey(EntityHangingAllSides.class, DataSerializers.VARINT);
   private float itemDropChance = 1.0F;
   public SoundEvent dropSound = SoundEvents.ENTITY_ITEMFRAME_REMOVE_ITEM;
   public SoundEvent breakSound = SoundEvents.ENTITY_ITEMFRAME_BREAK;
   public SoundEvent rotateSound = SoundEvents.ENTITY_ITEMFRAME_ROTATE_ITEM;
   public SoundEvent placeSound = SoundEvents.ENTITY_ITEMFRAME_PLACE;
   public SoundEvent addItemSound = SoundEvents.ENTITY_ITEMFRAME_ADD_ITEM;
   public int type;

   public EntityHangingAllSides(World worldIn) {
      super(worldIn);
   }

   public EntityHangingAllSides(World worldIn, BlockPos pos, EnumFacing facing) {
      super(worldIn, pos);
      this.updateFacingWithBoundingBox(facing);
   }

   protected void entityInit() {
      this.getDataManager().register(ITEM, ItemStack.EMPTY);
      this.getDataManager().register(ROTATION, 0);
   }

   public void setType(int type) {
      this.type = type;
      if (type == 1) {
         this.breakSound = Sounds.seashell_pickup;
         this.dropSound = Sounds.seashell_pickup;
         this.rotateSound = Sounds.seashell;
      }
   }

   public void onUpdate() {
      super.onUpdate();
      if (!this.world.isRemote && (this.ticksExisted < 2 || this.ticksExisted % 50 == 0)) {
         IEntitySynchronize.sendSynchronize(
            this,
            24.0,
            this.hangingPosition.getX(),
            this.hangingPosition.getY(),
            this.hangingPosition.getZ(),
            this.facingDirection.getIndex()
         );
      }
   }

   protected void updateFacingWithBoundingBox(EnumFacing facingDirectionIn) {
      Validate.notNull(facingDirectionIn);
      this.facingDirection = facingDirectionIn;
      this.rotationYaw = this.facingDirection.getHorizontalIndex() * 90;
      this.prevRotationYaw = this.rotationYaw;
      if (facingDirectionIn.getAxis() == Axis.Y) {
         this.rotationPitch = facingDirectionIn == EnumFacing.UP ? -90.0F : 90.0F;
         this.prevRotationPitch = this.rotationPitch;
      }

      this.updateBoundingBox();
   }

   @Override
   public void onClient(double... args) {
      if (args.length == 4) {
         this.hangingPosition = new BlockPos(args[0], args[1], args[2]);
         this.updateFacingWithBoundingBox(EnumFacing.byIndex(MathHelper.clamp((int)args[3], 0, 5)));
      }
   }

   protected void updateBoundingBox() {
      if (this.facingDirection != null) {
         double d0 = this.hangingPosition.getX() + 0.5;
         double d1 = this.hangingPosition.getY() + 0.5;
         double d2 = this.hangingPosition.getZ() + 0.5;
         if (this.facingDirection == EnumFacing.UP) {
            this.posX = d0;
            this.posY = d1 - 0.5;
            this.posZ = d2;
            this.setEntityBoundingBox(GetMOP.newAABB(12, 1, 0).offset(this.posX - 0.5, this.posY, this.posZ - 0.5));
            return;
         }

         if (this.facingDirection == EnumFacing.DOWN) {
            this.posX = d0;
            this.posY = d1 + 0.5 - 0.0625;
            this.posZ = d2;
            this.height = 0.0625F;
            this.setEntityBoundingBox(GetMOP.newAABB(12, 1, 0).offset(this.posX - 0.5, this.posY, this.posZ - 0.5));
            return;
         }

         double d3 = 0.46875;
         double d4 = this.offs(this.getWidthPixels());
         double d5 = this.offs(this.getHeightPixels());
         d0 -= this.facingDirection.getXOffset() * 0.46875;
         d2 -= this.facingDirection.getZOffset() * 0.46875;
         d1 += d5;
         EnumFacing enumfacing = this.facingDirection.rotateYCCW();
         d0 += d4 * enumfacing.getXOffset();
         d2 += d4 * enumfacing.getZOffset();
         this.posX = d0;
         this.posY = d1;
         this.posZ = d2;
         double d6 = this.getWidthPixels();
         double d7 = this.getHeightPixels();
         double d8 = this.getWidthPixels();
         if (this.facingDirection.getAxis() == Axis.Z) {
            d8 = 1.0;
         } else {
            d6 = 1.0;
         }

         d6 /= 32.0;
         d7 /= 32.0;
         d8 /= 32.0;
         this.setEntityBoundingBox(new AxisAlignedBB(d0 - d6, d1 - d7, d2 - d8, d0 + d6, d1 + d7, d2 + d8));
      }
   }

   public double offs(int p_190202_1_) {
      return p_190202_1_ % 32 == 0 ? 0.5 : 0.0;
   }

   public boolean onValidSurface() {
      if (!this.world.getCollisionBoxes(this, this.getEntityBoundingBox()).isEmpty()) {
         return false;
      } else {
         BlockPos blockpos = this.hangingPosition.offset(this.facingDirection.getOpposite());
         return !this.world.isSideSolid(blockpos, this.facingDirection)
            ? false
            : this.world.getEntitiesInAABBexcluding(this, this.getEntityBoundingBox(), IS_HANGING_ENTITY).isEmpty();
      }
   }

   public float getEyeHeight() {
      return this.height / 2.0F;
   }

   public float getCollisionBorderSize() {
      return 0.0F;
   }

   public boolean attackEntityFrom(DamageSource source, float amount) {
      if (this.isEntityInvulnerable(source)) {
         return false;
      } else if (!source.isExplosion() && !this.getDisplayedItem().isEmpty()) {
         if (!this.world.isRemote) {
            this.dropItemOrSelf(source.getTrueSource());
            this.playSound(this.dropSound, 1.0F, 1.0F);
         }

         return true;
      } else {
         return super.attackEntityFrom(source, amount);
      }
   }

   public int getWidthPixels() {
      return 12;
   }

   public int getHeightPixels() {
      return 12;
   }

   @SideOnly(Side.CLIENT)
   public boolean isInRangeToRenderDist(double distance) {
      double d0 = 16.0;
      d0 = d0 * 64.0 * getRenderDistanceWeight();
      return distance < d0 * d0;
   }

   public void onBroken(@Nullable Entity brokenEntity) {
      this.playSound(this.breakSound, 1.0F, 1.0F);
      this.dropItemOrSelf(brokenEntity);
   }

   public void playPlaceSound() {
      this.playSound(this.placeSound, 1.0F, 1.0F);
   }

   public void dropItemOrSelf(@Nullable Entity entityIn) {
      if (this.world.getGameRules().getBoolean("doEntityDrops")) {
         ItemStack itemstack = this.getDisplayedItem();
         float offsetY = this.facingDirection == EnumFacing.DOWN ? -0.5F : 0.0F;
         if (!itemstack.isEmpty() && this.rand.nextFloat() < this.itemDropChance) {
            itemstack = itemstack.copy();
            this.entityDropItem(itemstack, offsetY);
         }

         if (this.type == 1) {
            if (this.rand.nextFloat() < 0.33F) {
               this.entityDropItem(new ItemStack(ItemsRegister.PEARL), offsetY);
            }

            if (this.rand.nextFloat() < 0.066F) {
               this.entityDropItem(new ItemStack(ItemsRegister.PEARLBLACK), offsetY);
            }
         }
      }

      this.setDead();
   }

   public ItemStack getDisplayedItem() {
      return (ItemStack)this.getDataManager().get(ITEM);
   }

   public void setDisplayedItem(ItemStack stack) {
      this.setDisplayedItemWithUpdate(stack, true);
   }

   private void setDisplayedItemWithUpdate(ItemStack stack, boolean p_174864_2_) {
      if (!stack.isEmpty()) {
         stack = stack.copy();
         stack.setCount(1);
      }

      this.getDataManager().set(ITEM, stack);
      this.getDataManager().setDirty(ITEM);
      if (!stack.isEmpty()) {
         this.playSound(this.addItemSound, 1.0F, 1.0F);
      }

      if (p_174864_2_ && this.hangingPosition != null) {
         this.world.updateComparatorOutputLevel(this.hangingPosition, Blocks.AIR);
      }
   }

   public int getRotation() {
      return (Integer)this.getDataManager().get(ROTATION);
   }

   public void setItemRotation(int rotationIn) {
      this.setRotation(rotationIn, true);
   }

   private void setRotation(int rotationIn, boolean p_174865_2_) {
      this.getDataManager().set(ROTATION, rotationIn % 8);
      if (p_174865_2_ && this.hangingPosition != null) {
         this.world.updateComparatorOutputLevel(this.hangingPosition, Blocks.AIR);
      }
   }

   public void writeEntityToNBT(NBTTagCompound compound) {
      if (!this.getDisplayedItem().isEmpty()) {
         compound.setTag("Item", this.getDisplayedItem().writeToNBT(new NBTTagCompound()));
         compound.setByte("ItemRotation", (byte)this.getRotation());
         compound.setFloat("ItemDropChance", this.itemDropChance);
      }

      compound.setByte("Facing", (byte)this.facingDirection.getIndex());
      BlockPos blockpos = this.getHangingPosition();
      compound.setInteger("TileX", blockpos.getX());
      compound.setInteger("TileY", blockpos.getY());
      compound.setInteger("TileZ", blockpos.getZ());
      compound.setInteger("type", this.type);
   }

   public void readEntityFromNBT(NBTTagCompound compound) {
      NBTTagCompound nbttagcompound = compound.getCompoundTag("Item");
      if (nbttagcompound != null && !nbttagcompound.isEmpty()) {
         this.setDisplayedItemWithUpdate(new ItemStack(nbttagcompound), false);
         this.setRotation(compound.getByte("ItemRotation"), false);
         if (compound.hasKey("ItemDropChance", 99)) {
            this.itemDropChance = compound.getFloat("ItemDropChance");
         }
      }

      this.hangingPosition = new BlockPos(compound.getInteger("TileX"), compound.getInteger("TileY"), compound.getInteger("TileZ"));
      if (compound.hasKey("type")) {
         this.setType(compound.getInteger("type"));
      }

      this.updateFacingWithBoundingBox(EnumFacing.byIndex(compound.getByte("Facing")));
   }

   public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
      ItemStack itemstack = player.getHeldItem(hand);
      if (!this.world.isRemote) {
         if (this.getDisplayedItem().isEmpty()) {
            if (!itemstack.isEmpty()) {
               this.setDisplayedItem(itemstack);
               if (!player.capabilities.isCreativeMode) {
                  itemstack.shrink(1);
               }
            }
         } else {
            this.playSound(this.rotateSound, 1.0F, 1.0F);
            this.setItemRotation(this.getRotation() + 1);
         }
      }

      return true;
   }

   public int getAnalogOutput() {
      return this.getDisplayedItem().isEmpty() ? 0 : this.getRotation() % 8 + 1;
   }
}
