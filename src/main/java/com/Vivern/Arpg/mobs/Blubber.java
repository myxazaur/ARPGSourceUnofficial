//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.mobs;

import com.Vivern.Arpg.main.BloodType;
import com.Vivern.Arpg.main.DeathEffects;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.potions.PotionEffects;
import com.Vivern.Arpg.renders.GUNParticle;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Blubber extends AbstractMob {
   ResourceLocation texture = new ResourceLocation("arpg:textures/blob.png");

   public Blubber(World world) {
      super(world, 0.4F, 0.6F);
      this.hurtSound = Sounds.blubber_hurt;
      this.deathSound = Sounds.blubber_dead;
      this.livingSound = Sounds.blubber_living;
      this.defaultteam = OtherMobsPack.mobsteamender;
      this.setNoGravity(true);
      this.setattributes(20.0, 20.0, 4.0, 0.08F, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
      this.registerLOOT(new MobDrop[]{new MobDrop(ItemsRegister.QUANTUMSLIMEBALL, 0.6F, 0, 1, 2, 1)});
      this.setRoleValues(EnumMobRole.WEAK_SOLDIER, 1);
   }

   @Override
   public BloodType getBloodType() {
      return DeathEffects.ENDER_BLOOD;
   }

   public void fall(float distance, float damageMultiplier) {
   }

   @Override
   public void onUpdate() {
      super.onUpdate();
      if (this.world.isRemote && this.getRNG().nextFloat() < 0.4F) {
         GUNParticle cloud = new GUNParticle(
            this.texture,
            0.05F + this.getRNG().nextFloat() / 10.0F,
            -0.007F,
            13 + this.getRNG().nextInt(5),
            80,
            this.world,
            this.posX,
            this.posY + this.height / 2.0F,
            this.posZ,
            (float)this.getRNG().nextGaussian() / 30.0F,
            -0.05F,
            (float)this.getRNG().nextGaussian() / 30.0F,
            0.7F + (float)this.getRNG().nextGaussian() / 10.0F,
            0.5F + (float)this.getRNG().nextGaussian() / 10.0F,
            1.0F,
            true,
            this.getRNG().nextInt(360)
         );
         this.world.spawnEntity(cloud);
      }
   }

   @SideOnly(Side.CLIENT)
   @Override
   public void handleStatusUpdate(byte id) {
      super.handleStatusUpdate(id);
      if (id == 8) {
         for (int ss = 0; ss < 15; ss++) {
            GUNParticle cloud = new GUNParticle(
               this.texture,
               0.05F + this.getRNG().nextFloat() / 10.0F,
               -0.007F,
               11 + this.getRNG().nextInt(5),
               80,
               this.world,
               this.posX,
               this.posY + this.height / 2.0F,
               this.posZ,
               (float)this.getRNG().nextGaussian() / 12.0F,
               (float)this.getRNG().nextGaussian() / 17.0F,
               (float)this.getRNG().nextGaussian() / 12.0F,
               0.7F + (float)this.getRNG().nextGaussian() / 10.0F,
               0.5F + (float)this.getRNG().nextGaussian() / 10.0F,
               1.0F,
               true,
               this.getRNG().nextInt(360)
            );
            this.world.spawnEntity(cloud);
         }
      }
   }

   protected void initEntityAI() {
      this.tasks.addTask(1, new EntityAIFlying(this, 120, 16.0F, 0.015F, false));
      this.tasks.addTask(2, new EntityAICloudAttack(this, 40, 1.8F, 4.0F, 1.8F, (byte)8, PotionEffects.ENDER_POISON));
      this.tasks.addTask(2, new EntityAIEasyFlyingAttack(this, 0.025F));
      this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
      this.targetTasks.addTask(2, new EntityAIAgree(this, EntityPlayer.class, true));
      this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
   }
}
