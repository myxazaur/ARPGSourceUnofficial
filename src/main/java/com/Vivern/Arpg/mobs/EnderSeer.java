//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.mobs;

import com.Vivern.Arpg.main.PropertiesRegistry;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.renders.GUNParticle;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EnderSeer extends EntityMob {
   ResourceLocation texture = new ResourceLocation("arpg:textures/purple_smoke.png");

   public EnderSeer(World worldIn) {
      super(worldIn);
      this.setSize(4.5F, 2.25F);
      this.setNoGravity(true);
   }

   public void fall(float distance, float damageMultiplier) {
   }

   protected boolean canDespawn() {
      return this.getAttackTarget() == null || this.getDistance(this.getAttackTarget()) > 100.0F;
   }

   protected float getSoundVolume() {
      return 15.0F;
   }

   public void onUpdate() {
      super.onUpdate();
      if (!this.world.isRemote) {
         this.world.setEntityState(this, (byte)8);
      }
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(150.0);
      this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(100.0);
      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.1);
      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0);
      this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.7);
      this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(5.0);
      this.getEntityAttribute(PropertiesRegistry.ARMOR_PROTECTION).setBaseValue(3.0);
   }

   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return Sounds.ender_seer_hurt;
   }

   protected SoundEvent getDeathSound() {
      return Sounds.ender_seer_dead;
   }

   protected SoundEvent getAmbientSound() {
      return Sounds.ender_seer_living;
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      super.handleStatusUpdate(id);
      if (id == 8) {
         GUNParticle fire2 = new GUNParticle(
            this.texture,
            2.7F + (float)this.rand.nextGaussian() / 3.0F,
            -0.002F,
            15 + this.rand.nextInt(5),
            240,
            this.world,
            this.posX,
            this.posY,
            this.posZ,
            0.0F,
            -0.3F,
            0.0F,
            0.8F + (float)this.rand.nextGaussian() / 5.0F,
            0.5F + (float)this.rand.nextGaussian() / 5.0F,
            1.0F,
            true,
            this.rand.nextInt(100) - 50
         );
         fire2.alphaTickAdding = -0.02F;
         fire2.scaleTickAdding = -0.07F;
         fire2.alphaGlowing = true;
         this.world.spawnEntity(fire2);
      }

      if (id == 9) {
         this.world
            .playSound(
               this.posX,
               this.posY,
               this.posZ,
               Sounds.ender_seer_shoot,
               SoundCategory.AMBIENT,
               15.6F,
               0.9F + this.rand.nextFloat() / 5.0F,
               false
            );
      }
   }

   protected void initEntityAI() {
      this.tasks.addTask(1, new EntityAIFlying(this, 150, 25.0F, 0.02F, false));
      this.tasks.addTask(2, new EntityAIEnderSeerAttack(this));
      this.tasks.addTask(2, new EntityAIFlyingKeepDistToTarget(this, 200, 60.0F, 0.4F, 20.0F, 65.0F, 20.0F, 2.0F));
      this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
      this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
   }
}
