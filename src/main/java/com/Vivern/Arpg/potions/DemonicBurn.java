package com.Vivern.Arpg.potions;

import com.Vivern.Arpg.main.PropertiesRegistry;
import com.Vivern.Arpg.main.WeaponDamage;
import com.Vivern.Arpg.renders.KillScore;
import com.Vivern.Arpg.renders.PotionBurningEffects;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class DemonicBurn extends AdvancedPotion {
   public static String fire0 = "arpg:fires/demonic_fire_layer_0";
   public static String fire1 = "arpg:fires/demonic_fire_layer_1";

   public DemonicBurn(int index) {
      super(true, 7733477, index, true);
      this.setRegistryName("arpg:demonic_burn");
      this.setPotionName("Demonic_Burn");
      this.setIconIndex(26, 1);
      this.registerPotionAttributeModifier(PropertiesRegistry.LEADERSHIP, MathHelper.getRandomUUID().toString(), -2.0, 0);
      this.registerPotionAttributeModifier(SharedMonsterAttributes.MAX_HEALTH, MathHelper.getRandomUUID().toString(), -4.0, 0);
      this.shouldRender = true;
   }

   public void performEffect(EntityLivingBase entityOnEffect, int amplifier) {
      if (!entityOnEffect.world.isRemote && entityOnEffect.ticksExisted % 30 == 0 && this.getThisDuration(entityOnEffect) > 0) {
         float health = entityOnEffect.getHealth();
         if (health > 1.0F) {
            entityOnEffect.setHealth(health - 1.0F);
            KillScore.onHealthReduction(entityOnEffect, -1.0F);
         } else {
            entityOnEffect.attackEntityFrom(new WeaponDamage(null, null, null, false, false, null, WeaponDamage.soul), 1.0F);
         }
      }
   }

   public boolean isReady(int duration, int amplifier) {
      return true;
   }

   @SideOnly(Side.CLIENT)
   @Override
   public void render(EntityLivingBase entityOnEffect, double x, double y, double z, float yaw, float partialTicks, PotionEffect effect, Render entityRenderer) {
      PotionBurningEffects.renderEntityOnFire(entityOnEffect, x, y + 0.15, z, partialTicks, fire0, fire1, 180);
   }

   @Override
   public void renderFirstperson(EntityPlayer player, PotionEffect effect, RenderHandEvent event) {
      PotionBurningEffects.renderFireInFirstPerson(fire1, 0.7F, -0.3F + 0.1F * Math.min(effect.getAmplifier(), 2));
   }
}
