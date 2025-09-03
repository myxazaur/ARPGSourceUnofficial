//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.mobs;

import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.renders.GUNParticle;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Moonshroom extends AbstractMob {
   static ResourceLocation texture = new ResourceLocation("arpg:textures/largecloud.png");

   public Moonshroom(World worldIn) {
      super(worldIn, 0.6F, 1.6F);
      this.hurtSound = Sounds.moonshroom_hurt;
      this.deathSound = Sounds.moonshroom_dead;
      this.setattributes(28.0, 20.0, 1.0, 0.1, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
      this.registerLOOT(
         new MobDrop[]{
            new MobDrop(ItemsRegister.MOONSHROOMMEAT, 1.0F, 0, 1, 2, 3),
            new MobDrop(ItemsRegister.YEAST, 0.05F, 0, 1, 3, 5),
            new MobDrop(ItemsRegister.WHEYSTARTER, 0.05F, 0, 1, 3, 5)
         }
      );
      this.setNoGravity(true);
      this.experienceValue = 4;
      this.canDropLoot = true;
   }

   public void fall(float distance, float damageMultiplier) {
   }

   public boolean canBreatheUnderwater() {
      return true;
   }

   @SideOnly(Side.CLIENT)
   @Override
   public void handleStatusUpdate(byte id) {
      super.handleStatusUpdate(id);
      if (id == 8) {
         for (int ss = 0; ss < 7; ss++) {
            GUNParticle cloud = new GUNParticle(
               texture,
               0.6F + (float)this.getRNG().nextGaussian() / 20.0F,
               0.0F,
               12,
               80,
               this.world,
               this.posX,
               this.posY + this.height / 2.0F,
               this.posZ,
               (float)this.getRNG().nextGaussian() / 15.0F,
               (float)this.getRNG().nextGaussian() / 17.0F,
               (float)this.getRNG().nextGaussian() / 15.0F,
               0.7F,
               0.4F + (float)this.getRNG().nextGaussian() / 15.0F,
               1.0F,
               true,
               this.getRNG().nextInt(360)
            );
            this.world.spawnEntity(cloud);
         }
      }
   }

   protected void initEntityAI() {
      this.tasks.addTask(0, new EntityAIEasyRunaway(this, 0.03F, 0.3F));
      this.tasks.addTask(1, new EntityAIFlying(this, 120, 10.0F, 0.02F, false));
      this.tasks.addTask(2, new EntityAICloudAttack(this, 30, 2.0F, 3.0F, 0.8F, (byte)8, null));
      this.tasks.addTask(2, new EntityAIEasyFlyingAttack(this, 0.02F));
      this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
      this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
   }
}
