package com.Vivern.Arpg.mobs;

import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Sounds;
import java.util.Random;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class WhiteSlime extends AbstractMob {
   public float jumpscale = 0.0F;
   public float slimesize = 1.0F;

   public WhiteSlime(World worldIn) {
      super(worldIn, 0.8F, 0.8F);
      Random trand = new Random(this.getEntityId());
      this.slimesize = 0.7F + trand.nextFloat() / 1.5F;
      this.setSize(0.8F * this.slimesize, 0.8F * this.slimesize);
      this.jumpMovementFactor = 0.2F;
      this.hurtSound = SoundEvents.BLOCK_SLIME_STEP;
      this.deathSound = Sounds.slime_dead;
      this.setattributes(10.0, 14.0, 3.0, 0.3, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
      this.registerLOOT(
         new MobDrop[]{
            new MobDrop(ItemsRegister.WHITESLIMEBALL, 0.7F, 0, 1, 2, 2), new MobDrop(ItemsRegister.SLIMEEATER, 0.03F, 0, 1, 1, 0)
         }
      );
      this.leadershipBase = 1;
      this.setDropMoney(-2, 2, 0.9F);
      this.experienceValue = 2;
   }

   public float getCollisionBorderSize() {
      return 0.1F;
   }

   @Override
   protected void dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source) {
      if (this.owner == null) {
         if (source.getTrueSource() != null && source.getTrueSource() instanceof EntityCreeper) {
            int count = 3 + this.rand.nextInt(13);
            ItemStack stack = new ItemStack(ItemsRegister.SLIMYEGGS, count, 0);
            this.entityDropItem(stack, 0.0F);
         } else {
            super.dropLoot(wasRecentlyHit, lootingModifier, source);
         }
      }
   }

   @Override
   public float getTamedDropChances(MobDrop drop, int mobDropIndex, int lootingModifier, DamageSource source) {
      return this.owner != null ? 0.2F : 1.0F;
   }

   @Override
   public void onUpdate() {
      super.onUpdate();
      if (!this.onGround && this.jumpscale < 0.3F) {
         this.jumpscale += 0.06F;
      }

      if (this.onGround && this.jumpscale > 0.0F) {
         this.jumpscale -= 0.15F;
      }
   }

   protected float getJumpUpwardsMotion() {
      return 0.55F;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return SoundEvents.BLOCK_SLIME_STEP;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return Sounds.slime_dead;
   }

   public void fall(float distance, float damageMultiplier) {
      distance -= 2.0F;
      if (distance < 0.0F) {
         distance = 0.0F;
      }

      super.fall(distance, damageMultiplier);
   }

   public boolean canBreatheUnderwater() {
      return true;
   }

   protected void initEntityAI() {
      this.tasks.addTask(1, new EntityAIJumpingMovement(this, 30, 0.0F));
      this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0, false));
      this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
      this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
      this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
   }

   @Override
   public void readEntityFromNBT(NBTTagCompound compound) {
      super.readEntityFromNBT(compound);
      this.slimesize = compound.getFloat("slimesize");
      this.setSize(0.8F * this.slimesize, 0.8F * this.slimesize);
   }

   @Override
   public void writeEntityToNBT(NBTTagCompound compound) {
      super.writeEntityToNBT(compound);
      compound.setFloat("slimesize", this.slimesize);
   }
}
