//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.potions;

import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Mana;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.mobs.BossWinterFury;
import com.Vivern.Arpg.mobs.EverfrostMobsPack;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.AbstractIllager;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class WinterCurse extends AdvancedPotion {
   public WinterCurse(int index) {
      super(true, 6480072, index, true);
      this.setRegistryName("arpg:winter_curse");
      this.setPotionName("Winter_Curse");
      this.setIconIndex(31, 1);
   }

   public void performEffect(EntityLivingBase entityOnEffect, int amplifier) {
      if (entityOnEffect.ticksExisted % 30 == 0) {
         if (!entityOnEffect.world.isRemote && this.getThisDuration(entityOnEffect) > 0) {
            if (entityOnEffect.isPotionActive(PotionEffects.DEMONIC_BURN)) {
               entityOnEffect.removePotionEffect(this);
               if (entityOnEffect.getActivePotionEffect(PotionEffects.DEMONIC_BURN).getAmplifier() == 0) {
                  entityOnEffect.removePotionEffect(PotionEffects.DEMONIC_BURN);
               }
            }

            entityOnEffect.attackEntityFrom(DamageSource.MAGIC, 2.0F);
            if (entityOnEffect instanceof EntityPlayer) {
               Mana.giveMana((EntityPlayer)entityOnEffect, -5.0F);
               ((EntityPlayer)entityOnEffect).getFoodStats().addExhaustion(2.0F);
            }
         }

         if (rand.nextFloat() < 0.2F) {
            entityOnEffect.world
               .playSound(
                  entityOnEffect.posX,
                  entityOnEffect.posY,
                  entityOnEffect.posZ,
                  Sounds.winter_curse_b,
                  SoundCategory.HOSTILE,
                  0.8F,
                  0.9F + rand.nextFloat() / 5.0F,
                  false
               );
         }
      }
   }

   public List<ItemStack> getCurativeItems() {
      ArrayList<ItemStack> ret = new ArrayList<>();
      ret.add(new ItemStack(Items.MILK_BUCKET));
      ret.add(new ItemStack(ItemsRegister.CRIMBERRYWINE));
      return ret;
   }

   public boolean isReady(int duration, int amplifier) {
      return true;
   }

   @Override
   public void onThisDeath(LivingDeathEvent event, PotionEffect effect) {
      if (!event.getEntityLiving().world.isRemote
         && (
            event.getEntityLiving() instanceof EntityPlayer
               || event.getEntityLiving() instanceof EntityZombie
               || event.getEntityLiving() instanceof AbstractSkeleton
               || event.getEntityLiving() instanceof EntityVillager
               || event.getEntityLiving() instanceof AbstractIllager
               || event.getEntityLiving() instanceof EntityWitch
         )) {
         EverfrostMobsPack.Atorpid mob = new EverfrostMobsPack.Atorpid(event.getEntityLiving().world);
         mob.setPosition(event.getEntityLiving().posX, event.getEntityLiving().posY, event.getEntityLiving().posZ);
         event.getEntityLiving().world.spawnEntity(mob);
         mob.onInitialSpawn();
         mob.canDropLoot = true;
         EntityLivingBase boss = (EntityLivingBase)event.getEntityLiving()
            .world
            .findNearestEntityWithinAABB(BossWinterFury.class, event.getEntityLiving().getEntityBoundingBox().grow(64.0), event.getEntityLiving());
         if (boss != null && boss instanceof BossWinterFury) {
            mob.team = ((BossWinterFury)boss).team;
            mob.isAgressive = ((BossWinterFury)boss).isAgressive;
         }
      }

      super.onThisDeath(event, effect);
   }
}
