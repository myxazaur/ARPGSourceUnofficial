package com.Vivern.Arpg.mobs;

import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.renders.GUNParticle;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SmokeDemon extends AbstractMob {
   ResourceLocation largesmoke = new ResourceLocation("arpg:textures/largesmoke.png");

   public SmokeDemon(World world) {
      super(world, 1.0F, 1.0F);
      this.hurtSound = Sounds.smoke_demon_hurt;
      this.deathSound = Sounds.smoke_demon_dead;
      this.defaultteam = OtherMobsPack.mobsteamnether;
      this.setNoGravity(true);
      this.setattributes(15.0, 32.0, 5.0, 0.5, 5.0, 2.0, 0.0, 0.0, 0.0, 0.0);
      this.registerLOOT(
         new MobDrop[]{
            new MobDrop(ItemsRegister.NUGGETBRASS, 0.5F, 0, 1, 2, 3), new MobDrop(ItemsRegister.INGOTBRASS, 0.05F, 0, 1, 1, 1)
         }
      );
      this.leadershipBase = 4;
      this.isImmuneToFire = true;
      this.setDropMoney(-4, 5, 1.0F);
   }

   public void fall(float distance, float damageMultiplier) {
   }

   @Override
   public void onUpdate() {
      super.onUpdate();
      if (this.world.isRemote && this.getRNG().nextFloat() > 0.4F) {
         GUNParticle bigsmoke = new GUNParticle(
            this.largesmoke,
            0.65F,
            -2.0E-4F,
            35,
            -1,
            this.world,
            this.posX,
            this.posY + this.height / 2.0F,
            this.posZ,
            (float)this.rand.nextGaussian() / 35.0F,
            (float)this.rand.nextGaussian() / 35.0F,
            (float)this.rand.nextGaussian() / 35.0F,
            1.0F,
            1.0F,
            1.0F,
            true,
            this.rand.nextInt(360)
         );
         bigsmoke.alphaTickAdding = -0.028571429F;
         bigsmoke.randomDeath = false;
         this.world.spawnEntity(bigsmoke);
      }

      Vec3d pitchYaw = GetMOP.Vec3dToPitchYaw(new Vec3d(-this.motionX, -this.motionY, -this.motionZ));
      this.rotationPitch = (float)MathHelper.wrapDegrees(pitchYaw.x);
      this.rotationYaw = (float)MathHelper.wrapDegrees(pitchYaw.y);
   }

   protected void initEntityAI() {
      this.tasks.addTask(1, new EntityAIFlying(this, 30, 5.0F, 0.3F, false));
      this.tasks.addTask(2, new EntityAIFlyingWorm(this, 70, 12.0F, 0.2F, 4.0F, 2.0F, 2.0F, 0.6F, 1.15F, true));
      this.tasks.addTask(3, new EntityAIAABBAttack(this, 40, 0.2F));
      this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
      this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
   }
}
