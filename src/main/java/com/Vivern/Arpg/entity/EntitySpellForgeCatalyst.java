package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.main.BlocksRegister;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntitySpellForgeCatalyst extends Entity implements IEntitySynchronize {
   private static final DataParameter<ItemStack> ITEM = EntityDataManager.createKey(EntitySpellForgeCatalyst.class, DataSerializers.ITEM_STACK);
   public int age;
   public int health;
   private String thrower;
   private String owner;
   public float hoverStart;
   public int lifespan = 6000;
   public int heat;
   public int heatClient = 0;
   public boolean fromPliers = false;
   public int spendTick = 0;

   public EntitySpellForgeCatalyst(World worldIn, double x, double y, double z, int heat) {
      super(worldIn);
      this.health = 5;
      this.hoverStart = (float)(Math.random() * Math.PI * 2.0);
      this.setSize(0.25F, 0.5F);
      this.setPosition(x, y, z);
      this.rotationYaw = (float)(Math.random() * 360.0);
      this.heat = heat;
   }

   public EntitySpellForgeCatalyst(World worldIn, double x, double y, double z, ItemStack stack, int heat) {
      this(worldIn, x, y, z, heat);
      this.setItem(stack);
      this.lifespan = stack.getItem() == null ? 6000 : stack.getItem().getEntityLifespan(stack, worldIn);
   }

   public EntitySpellForgeCatalyst(World worldIn) {
      super(worldIn);
      this.health = 5;
      this.hoverStart = (float)(Math.random() * Math.PI * 2.0);
      this.setSize(0.25F, 0.5F);
      this.setItem(ItemStack.EMPTY);
   }

   public void addHeat(int add) {
      if (!this.world.isRemote) {
         this.heat = MathHelper.clamp(this.heat + add, 0, 800);
         IEntitySynchronize.sendSynchronize(this, 64.0, this.heat);
      }
   }

   @Override
   public void onClient(double... args) {
      if (args.length == 1) {
         this.heat = (int)args[0];
      } else if (args.length == 2 && args[1] > 0.0) {
         this.heat = (int)args[0];
         this.heatClient = this.heat;
      }
   }

   public boolean canBeCollidedWith() {
      return true;
   }

   protected boolean canTriggerWalking() {
      return false;
   }

   protected void entityInit() {
      this.getDataManager().register(ITEM, ItemStack.EMPTY);
   }

   public void startSpendForForge() {
      if (!this.world.isRemote) {
         this.setItem(ItemStack.EMPTY);
         this.spendTick++;
         this.world.setEntityState(this, (byte)11);
      }
   }

   public void handleStatusUpdate(byte id) {
      super.handleStatusUpdate(id);
      if (id == 11) {
         this.spendTick++;
      }
   }

   public void onUpdate() {
      if (this.spendTick <= 0) {
         if (this.heat <= 0) {
            if (!this.world.isRemote) {
               EntityItem entityItem = new EntityItem(this.world, this.posX, this.posY, this.posZ, this.getItem());
               entityItem.motionX = this.motionX;
               entityItem.motionY = this.motionY;
               entityItem.motionZ = this.motionZ;
               this.world.spawnEntity(entityItem);
               this.setDead();
            }
         } else if (this.world.getBlockState(this.getPosition().down()).getBlock() != BlocksRegister.GEMSPARKBLOCK) {
            this.heat--;
         }
      } else if (this.world.isRemote && this.spendTick < 8) {
         this.spendTick++;
      }

      if (this.getItem().isEmpty()) {
         if (this.spendTick <= 0) {
            this.setDead();
         } else {
            this.spendTick++;
            if (this.spendTick >= 8) {
               this.setDead();
            }
         }
      } else {
         super.onUpdate();
         this.prevPosX = this.posX;
         this.prevPosY = this.posY;
         this.prevPosZ = this.posZ;
         double d0 = this.motionX;
         double d1 = this.motionY;
         double d2 = this.motionZ;
         if (!this.hasNoGravity()) {
            this.motionY -= 0.04F;
         }

         if (this.world.isRemote) {
            this.noClip = false;
            if (this.heatClient < this.heat) {
               this.heatClient++;
            }

            if (this.heatClient > this.heat) {
               this.heatClient--;
            }
         } else {
            this.noClip = this.pushOutOfBlocks(
               this.posX, (this.getEntityBoundingBox().minY + this.getEntityBoundingBox().maxY) / 2.0, this.posZ
            );
            if (this.fromPliers && this.ticksExisted < 3) {
               IEntitySynchronize.sendSynchronize(this, 64.0, this.heat, 1.0);
            }
         }

         this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
         boolean flag = (int)this.prevPosX != (int)this.posX
            || (int)this.prevPosY != (int)this.posY
            || (int)this.prevPosZ != (int)this.posZ;
         if ((flag || this.ticksExisted % 25 == 0) && this.world.getBlockState(new BlockPos(this)).getMaterial() == Material.LAVA) {
            this.motionY = 0.1;
            this.motionX = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F;
            this.motionZ = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F;
            this.playSound(SoundEvents.ENTITY_GENERIC_BURN, 0.3F, 2.0F + this.rand.nextFloat() * 0.4F);
         }

         float f = 0.98F;
         if (this.onGround) {
            BlockPos underPos = new BlockPos(
               MathHelper.floor(this.posX),
               MathHelper.floor(this.getEntityBoundingBox().minY) - 1,
               MathHelper.floor(this.posZ)
            );
            IBlockState underState = this.world.getBlockState(underPos);
            f = underState.getBlock().getSlipperiness(underState, this.world, underPos, this) * 0.98F;
         }

         this.motionX *= f;
         this.motionY *= 0.98F;
         this.motionZ *= f;
         if (this.onGround) {
            this.motionY *= -0.5;
         }

         if (this.age != -32768) {
            this.age++;
         }

         this.handleWaterMovement();
         if (!this.world.isRemote) {
            double d3 = this.motionX - d0;
            double d4 = this.motionY - d1;
            double d5 = this.motionZ - d2;
            double d6 = d3 * d3 + d4 * d4 + d5 * d5;
            if (d6 > 0.01) {
               this.isAirBorne = true;
            }
         }

         ItemStack item = this.getItem();
         if (!this.world.isRemote && this.age >= this.lifespan) {
            this.setDead();
         }

         if (item.isEmpty()) {
            this.setDead();
         }
      }
   }

   public boolean handleWaterMovement() {
      if (this.world.handleMaterialAcceleration(this.getEntityBoundingBox(), Material.WATER, this)) {
         if (!this.inWater && !this.firstUpdate) {
            this.doWaterSplashEffect();
         }

         this.inWater = true;
         if (!this.world.isRemote) {
            this.addHeat(-40);
         }

         for (int j = 0; j < 2; j++) {
            float f3 = 0.25F;
            this.world
               .spawnParticle(
                  EnumParticleTypes.WATER_BUBBLE,
                  this.posX - this.motionX * 0.25,
                  this.posY - this.motionY * 0.25,
                  this.posZ - this.motionZ * 0.25,
                  this.motionX,
                  this.motionY,
                  this.motionZ,
                  new int[0]
               );
            this.world
               .spawnParticle(
                  EnumParticleTypes.CLOUD,
                  this.posX - this.motionX * 0.25,
                  this.posY - this.motionY * 0.25,
                  this.posZ - this.motionZ * 0.25,
                  this.motionX,
                  this.motionY,
                  this.motionZ,
                  new int[0]
               );
         }
      } else {
         this.inWater = false;
      }

      return this.inWater;
   }

   protected void dealFireDamage(int amount) {
   }

   public boolean attackEntityFrom(DamageSource source, float amount) {
      if (this.world.isRemote || this.isDead) {
         return false;
      } else if (this.isEntityInvulnerable(source)) {
         return false;
      } else {
         this.markVelocityChanged();
         this.health = (int)(this.health - amount);
         if (this.health <= 0) {
            this.setDead();
         }

         return false;
      }
   }

   public void writeEntityToNBT(NBTTagCompound compound) {
      compound.setShort("Health", (short)this.health);
      compound.setShort("Age", (short)this.age);
      compound.setInteger("Lifespan", this.lifespan);
      compound.setInteger("Heat", this.heat);
      if (this.getThrower() != null) {
         compound.setString("Thrower", this.thrower);
      }

      if (this.getOwner() != null) {
         compound.setString("Owner", this.owner);
      }

      if (!this.getItem().isEmpty()) {
         compound.setTag("Item", this.getItem().writeToNBT(new NBTTagCompound()));
      }
   }

   public void readEntityFromNBT(NBTTagCompound compound) {
      this.health = compound.getShort("Health");
      this.age = compound.getShort("Age");
      if (compound.hasKey("Owner")) {
         this.owner = compound.getString("Owner");
      }

      if (compound.hasKey("Thrower")) {
         this.thrower = compound.getString("Thrower");
      }

      NBTTagCompound nbttagcompound = compound.getCompoundTag("Item");
      this.setItem(new ItemStack(nbttagcompound));
      if (this.getItem().isEmpty()) {
         this.setDead();
      }

      if (compound.hasKey("Lifespan")) {
         this.lifespan = compound.getInteger("Lifespan");
      }

      if (compound.hasKey("Heat")) {
         this.heat = compound.getInteger("Heat");
      }
   }

   public boolean canBeAttackedWithItem() {
      return false;
   }

   public ItemStack getItem() {
      return (ItemStack)this.getDataManager().get(ITEM);
   }

   public void setItem(ItemStack stack) {
      this.getDataManager().set(ITEM, stack);
      this.getDataManager().setDirty(ITEM);
   }

   public String getOwner() {
      return this.owner;
   }

   public void setOwner(String owner) {
      this.owner = owner;
   }

   public String getThrower() {
      return this.thrower;
   }

   public void setThrower(String thrower) {
      this.thrower = thrower;
   }
}
