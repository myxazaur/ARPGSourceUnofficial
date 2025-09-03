//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Sounds;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityArrowMithril extends AbstractArrow {
   public EntityArrowMithril(World worldIn) {
      super(worldIn);
   }

   public EntityArrowMithril(World worldIn, double x, double y, double z) {
      super(worldIn, x, y, z);
   }

   public EntityArrowMithril(World worldIn, EntityLivingBase shooter) {
      super(worldIn, shooter);
   }

   @Override
   public boolean doWaterMoveHook() {
      return true;
   }

   @Override
   public int waterParticlesHookAdding() {
      return this.rand.nextFloat() < 0.6F ? 1 : 0;
   }

   @Override
   public void modifySpeedInWater() {
      double multiplier = 0.93;
      this.motionX *= multiplier;
      this.motionY *= multiplier;
      this.motionZ *= multiplier;
   }

   public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
      super.shoot(x, y, z, velocity * 1.4F, inaccuracy / 2.0F);
   }

   @Override
   public SoundEvent getHitSound() {
      return Sounds.arrow_mithril;
   }

   protected ItemStack getArrowStack() {
      return new ItemStack(ItemsRegister.ARROWMITHRIL);
   }
}
