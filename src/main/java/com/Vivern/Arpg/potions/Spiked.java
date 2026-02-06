package com.Vivern.Arpg.potions;

import com.Vivern.Arpg.entity.SpikedBurst;
import com.Vivern.Arpg.main.SuperKnockback;
import com.Vivern.Arpg.main.Team;
import java.util.HashMap;
import java.util.List;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class Spiked extends AdvancedPotion {
   public Spiked() {
      super(true, 13814095, 4, true);
      this.setRegistryName("arpg:spiked");
      this.setPotionName("Spiked");
      this.setIconIndex(28, 1);
   }

   @Override
   public void onThisDeath(LivingDeathEvent event, PotionEffect effect) {
      EntityLivingBase entityOnEffect = event.getEntityLiving();
      if (effect instanceof SpikedEffect) {
         SpikedEffect spiked = (SpikedEffect)effect;
         AxisAlignedBB aabb = entityOnEffect.getEntityBoundingBox().grow(5.0, 3.0, 5.0);
         List<EntityLivingBase> list = entityOnEffect.world.getEntitiesWithinAABB(EntityLivingBase.class, aabb);
         float chanceToSkip = 0.1F * spiked.getAmplifier() - 0.1F * list.size();
         int overcount = 0;

         for (String team : spiked.mapTeams.keySet()) {
            int count = spiked.mapTeams.get(team);
            overcount += count;

            for (int i = 0; i < count; i++) {
               SpikedBurst burst = new SpikedBurst(entityOnEffect.world, entityOnEffect, team);
               burst.setLocationAndAngles(
                  entityOnEffect.posX,
                  entityOnEffect.posY + rand.nextFloat() * entityOnEffect.height,
                  entityOnEffect.posZ,
                  0.0F,
                  0.0F
               );
               EntityLivingBase targ = list.isEmpty() ? null : list.get(rand.nextInt(list.size()));
               if (targ != null && targ != entityOnEffect && !team.equals(Team.getTeamFor(targ)) && rand.nextFloat() > chanceToSkip) {
                  SuperKnockback.applyMove(burst, -2.0F, targ.posX, targ.posY + targ.height, targ.posZ);
               } else {
                  burst.motionX = rand.nextFloat() - 0.5;
                  burst.motionY = (rand.nextFloat() - 0.5) / 2.0;
                  burst.motionZ = rand.nextFloat() - 0.5;
               }

               entityOnEffect.world.spawnEntity(burst);
            }
         }

         if (spiked.getAmplifier() > overcount) {
            int rz = spiked.getAmplifier() - overcount;

            for (int i = 0; i < rz; i++) {
               SpikedBurst burst = new SpikedBurst(entityOnEffect.world, entityOnEffect, "");
               burst.setLocationAndAngles(
                  entityOnEffect.posX,
                  entityOnEffect.posY + rand.nextFloat() * entityOnEffect.height,
                  entityOnEffect.posZ,
                  0.0F,
                  0.0F
               );
               EntityLivingBase targ = list.isEmpty() ? null : list.get(rand.nextInt(list.size()));
               if (targ != null && targ != entityOnEffect && rand.nextFloat() > chanceToSkip) {
                  SuperKnockback.applyMove(burst, -2.0F, targ.posX, targ.posY + targ.height, targ.posZ);
               } else {
                  burst.motionX = rand.nextFloat() - 0.5;
                  burst.motionY = (rand.nextFloat() - 0.5) / 2.0;
                  burst.motionZ = rand.nextFloat() - 0.5;
               }

               entityOnEffect.world.spawnEntity(burst);
            }
         }
      }
   }

   public static class SpikedEffect extends PotionEffect {
      public HashMap<String, Integer> mapTeams = new HashMap<>();

      public SpikedEffect(Potion potionIn) {
         this(potionIn, 0, 0);
      }

      public SpikedEffect(Potion potionIn, int durationIn) {
         this(potionIn, durationIn, 0);
      }

      public SpikedEffect(Potion potionIn, int durationIn, int amplifierIn) {
         this(potionIn, durationIn, amplifierIn, false, true);
      }

      public SpikedEffect(Potion potionIn, int durationIn, int amplifierIn, boolean ambientIn, boolean showParticlesIn) {
         super(potionIn, durationIn, amplifierIn, ambientIn, showParticlesIn);
      }

      public SpikedEffect(PotionEffect other) {
         super(other);
      }

      public void setTeams(String team, int value) {
         if (this.mapTeams.containsKey(team)) {
            this.mapTeams.replace(team, value);
         } else {
            this.mapTeams.put(team, value);
         }
      }

      public void addToTeams(String team, int value) {
         if (this.mapTeams.containsKey(team)) {
            int vl = this.mapTeams.get(team);
            this.mapTeams.replace(team, vl + value);
         } else {
            this.mapTeams.put(team, value);
         }
      }

      public NBTTagCompound writeCustomPotionEffectToNBT(NBTTagCompound nbt) {
         NBTTagCompound tag = new NBTTagCompound();

         for (String key : this.mapTeams.keySet()) {
            tag.setInteger(key, this.mapTeams.get(key));
         }

         nbt.setTag("teams", tag);
         return super.writeCustomPotionEffectToNBT(nbt);
      }
   }
}
