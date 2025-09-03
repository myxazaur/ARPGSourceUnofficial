//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.potions;

import com.Vivern.Arpg.main.ColorConverters;
import com.Vivern.Arpg.main.DeathEffects;
import com.Vivern.Arpg.main.Mana;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.WeaponDamage;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class Shock extends Potion {
   Random rand = new Random();

   protected Shock(boolean isBadEffectIn, int liquidColorIn) {
      super(isBadEffectIn, liquidColorIn);
      this.setRegistryName("arpg:shock");
      this.setPotionName("Shock");
      this.setIconIndex(6, 1);
   }

   public boolean hasStatusIcon() {
      Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("arpg:textures/potions.png"));
      return true;
   }

   public void performEffect(EntityLivingBase entityLivingBase, int amplifier) {
      World world = entityLivingBase.world;
      DeathEffects.addTolistEFFECTshock(entityLivingBase);
      if (entityLivingBase instanceof EntityPlayer && Mana.getMana((EntityPlayer)entityLivingBase) > 0.0F) {
         Mana.changeMana((EntityPlayer)entityLivingBase, -3.5F * (amplifier + 1));
      }

      entityLivingBase.attackEntityFrom(new WeaponDamage(null, null, null, false, false, null, WeaponDamage.electric), 2 * (amplifier + 1));
      entityLivingBase.motionX /= 1.5;
      entityLivingBase.motionY /= 1.5;
      entityLivingBase.motionZ /= 1.5;
      double damageRadius = 4.0;
      world.playSound(
         (EntityPlayer)null,
         entityLivingBase.posX,
         entityLivingBase.posY,
         entityLivingBase.posZ,
         Sounds.shock,
         SoundCategory.AMBIENT,
         0.8F,
         0.9F + this.rand.nextFloat() / 5.0F
      );
      AxisAlignedBB axisalignedbb = entityLivingBase.getEntityBoundingBox()
         .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
         .offset(-damageRadius, -damageRadius, -damageRadius);
      List<EntityLivingBase> list = world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
      if (!list.isEmpty()) {
         for (EntityLivingBase entitylivingb : list) {
            if (entitylivingb != entityLivingBase
               && (entitylivingb.isPotionActive(MobEffects.RESISTANCE) || entitylivingb.isPotionActive(PotionEffects.SLIME) || entitylivingb.isInWater())) {
               int pow = ColorConverters.InBorder(0, 250, this.rand.nextFloat() > 0.3 ? amplifier : amplifier - 1);
               int dur = Math.round((float)(entityLivingBase.getActivePotionEffect(PotionEffects.SHOCK).getDuration() / 2));
               if (dur > 10) {
                  PotionEffect debaff = new PotionEffect(PotionEffects.SHOCK, dur, pow);
                  entitylivingb.addPotionEffect(debaff);
               }
            }
         }
      }
   }

   public boolean isReady(int duration, int amplifier) {
      return duration % 20 / (amplifier + 1) == 0;
   }
}
