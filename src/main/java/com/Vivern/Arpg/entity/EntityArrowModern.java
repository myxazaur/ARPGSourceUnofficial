//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Sounds;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityArrowModern extends AbstractArrow {
   public EntityArrowModern(World worldIn) {
      super(worldIn);
   }

   public EntityArrowModern(World worldIn, double x, double y, double z) {
      super(worldIn, x, y, z);
   }

   public EntityArrowModern(World worldIn, EntityLivingBase shooter) {
      super(worldIn, shooter);
   }

   public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
      super.shoot(x, y, z, velocity * 1.3F, inaccuracy / 2.0F);
   }

   @Override
   public SoundEvent getHitSound() {
      return Sounds.arrow_modern;
   }

   protected ItemStack getArrowStack() {
      return new ItemStack(ItemsRegister.ARROWMODERN);
   }
}
