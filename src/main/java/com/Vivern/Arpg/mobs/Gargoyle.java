//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.mobs;

import com.Vivern.Arpg.renders.SweepParticle;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Gargoyle extends EntityMob {
   static ResourceLocation sweeptex = new ResourceLocation("arpg:textures/sweep2.png");
   public float friction = 0.97F;
   public EntityAIAttackSweep sweepControl;

   public Gargoyle(World worldIn) {
      super(worldIn);
      this.setSize(0.75F, 0.75F);
      this.setNoGravity(true);
   }

   public void fall(float distance, float damageMultiplier) {
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 10) {
         SweepParticle particle = new SweepParticle(
            sweeptex,
            1.7F + this.getRNG().nextFloat() / 2.0F,
            6,
            1,
            4,
            2,
            (int)(this.getBrightness() * 150.0F),
            this.world,
            this.posX,
            this.posY + this.getEyeHeight(),
            this.posZ,
            this.rotationPitch,
            this.rotationYawHead,
            0.2F,
            0.2F,
            0.3F,
            false,
            80 + this.getRNG().nextInt(20)
         );
         this.world.spawnEntity(particle);
      }
   }

   public void onUpdate() {
      super.onUpdate();
      this.motionX = this.motionX * this.friction;
      this.motionY = this.motionY * this.friction;
      this.motionZ = this.motionZ * this.friction;
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0);
      this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(20.0);
      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0);
   }

   protected void initEntityAI() {
      this.tasks.addTask(1, new EntityAIRayLogicFly(this));
      this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
      this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
   }
}
