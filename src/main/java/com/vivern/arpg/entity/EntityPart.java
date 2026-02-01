package com.vivern.arpg.entity;

import com.vivern.arpg.mobs.AbstractMob;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHandSide;
import net.minecraft.world.World;

public class EntityPart extends AbstractMob implements IEntitySynchronize {
   public IMultipartMob owner;
   public int randSendTiming;

   public EntityPart(World world) {
      super(world, 0.25F, 0.25F);
      this.defaultteam = "";
      this.setNoGravity(true);
      this.randSendTiming = this.rand.nextInt(45);
      this.noClip = true;
   }

   public EntityPart(World world, IMultipartMob owner, String team, float width, float height) {
      super(world, width, height);
      this.owner = owner;
      this.defaultteam = team;
      this.team = team;
      this.setNoGravity(true);
      this.randSendTiming = this.rand.nextInt(45);
      this.noClip = true;
   }

   public EntityPart(World worldIn, IMultipartMob owner) {
      this(worldIn, owner, "", 0.25F, 0.25F);
   }

   public boolean canBreatheUnderwater() {
      return true;
   }

   public boolean checkDead() {
      if (this.world.isRemote || this.owner != null && !this.owner.isDead()) {
         return this.owner != null;
      } else {
         this.setDead();
         return false;
      }
   }

   protected void collideWithEntity(Entity entityIn) {
   }

   protected void collideWithNearbyEntities() {
   }

   public float getEyeHeight() {
      return this.height * 0.5F;
   }

   public void addPotionEffect(PotionEffect potioneffectIn) {
      if (this.checkDead()) {
         this.owner.getMob().addPotionEffect(potioneffectIn);
      }
   }

   @Override
   public boolean attackEntityFrom(DamageSource source, float amount) {
      return this.checkDead() ? this.owner.attackEntityFromPart(this, source, amount) : false;
   }

   public boolean handleWaterMovement() {
      return false;
   }

   public boolean canBeCollidedWith() {
      return true;
   }

   @Override
   public void onUpdate() {
      int hrt = this.hurtResistantTime;
      super.onUpdate();
      if (!this.world.isRemote && this.checkDead()) {
         if (this.ticksExisted < 2 || this.ticksExisted % 45 == this.randSendTiming) {
            IEntitySynchronize.sendSynchronize(this, 64.0, this.width, this.height, this.posX, this.posY, this.posZ);
            this.team = this.owner.getTeamString();
         }

         this.setHealth(this.owner.getMob().getHealth());
         if (hrt < this.owner.getMob().hurtResistantTime) {
            this.owner.getMob().hurtResistantTime = hrt;
         }
      }
   }

   @Override
   public void onClient(double... args) {
      if (args.length == 5) {
         this.setSize((float)args[0], (float)args[1]);
         this.setPositionAndUpdate(args[2], args[3], args[4]);
      }
   }

   public ItemStack getItemStackFromSlot(EntityEquipmentSlot slotIn) {
      return ItemStack.EMPTY;
   }

   public EnumHandSide getPrimaryHand() {
      return EnumHandSide.RIGHT;
   }
}
