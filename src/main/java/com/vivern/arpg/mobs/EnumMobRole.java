package com.vivern.arpg.mobs;

public enum EnumMobRole {
   SWARMER,
   WEAK_SOLDIER,
   SOLDIER,
   STRONG_SOLDIER,
   WEAK_TANK,
   TANK,
   MIDDLE_ENEMY,
   STRONG_ENEMY,
   ELITE_ENEMY,
   SPECIAL_MOB,
   MINI_BOSS;

   public int getXpValue() {
      if (this == SWARMER) {
         return 0;
      } else if (this == WEAK_SOLDIER) {
         return 3;
      } else if (this == SOLDIER) {
         return 5;
      } else if (this == STRONG_SOLDIER) {
         return 6;
      } else if (this == WEAK_TANK) {
         return 8;
      } else if (this == TANK) {
         return 12;
      } else if (this == MIDDLE_ENEMY) {
         return 8;
      } else if (this == STRONG_ENEMY) {
         return 12;
      } else if (this == ELITE_ENEMY) {
         return 20;
      } else {
         return this == MINI_BOSS ? 50 : 0;
      }
   }

   public void setMoneyValue(AbstractMob mob, int dimensionNumber) {
      if (this != SPECIAL_MOB) {
         float dimensionRaise = 0.9F;
         float multiplier = 1.0F + dimensionRaise * dimensionNumber;
         float middleMoneyPerMob = 0.6F;
         float chance = 0.2F;
         if (this == SWARMER) {
            middleMoneyPerMob = 0.6F;
            chance = 0.2F;
         }

         if (this == WEAK_SOLDIER) {
            middleMoneyPerMob = 1.2F;
            chance = 0.25F;
         }

         if (this == SOLDIER) {
            middleMoneyPerMob = 1.7F;
            chance = 0.25F;
         }

         if (this == STRONG_SOLDIER) {
            middleMoneyPerMob = 2.0F;
            chance = 0.25F;
         }

         if (this == WEAK_TANK) {
            middleMoneyPerMob = 3.5F;
            chance = 0.75F;
         }

         if (this == TANK) {
            middleMoneyPerMob = 6.0F;
            chance = 1.0F;
         }

         if (this == MIDDLE_ENEMY) {
            middleMoneyPerMob = 2.5F;
            chance = 0.35F;
         }

         if (this == STRONG_ENEMY) {
            middleMoneyPerMob = 4.5F;
            chance = 0.5F;
         }

         if (this == ELITE_ENEMY) {
            middleMoneyPerMob = 9.0F;
            chance = 1.0F;
         }

         if (this == MINI_BOSS) {
            middleMoneyPerMob = 18.0F;
            chance = 1.0F;
         }

         float middleDropped = middleMoneyPerMob * multiplier / chance;
         float randomizeDropped = Math.max(middleDropped * 0.4F, 6.0F);
         int min = (int)(middleDropped - randomizeDropped);
         int max = (int)(middleDropped + randomizeDropped);
         mob.setDropMoney(min, max, chance);
      }
   }
}
