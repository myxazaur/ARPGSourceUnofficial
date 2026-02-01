package com.vivern.arpg.recipes;

import com.vivern.arpg.main.ShardType;
import com.vivern.arpg.main.Shards;
import com.vivern.arpg.tileentity.TileSpellForge;
import net.minecraft.entity.player.EntityPlayer;

public class EnergyCost {
   public final ShardType type;
   public final float amount;

   public EnergyCost(ShardType type, float amount) {
      this.type = type;
      this.amount = amount;
   }

   public boolean playerHave(EntityPlayer player) {
      if (Shards.getShardTypeInVessel(player, 1) == this.type && Shards.getEnergyInVessel(player, 1) >= this.amount) {
         return true;
      } else {
         return Shards.getShardTypeInVessel(player, 2) == this.type && Shards.getEnergyInVessel(player, 2) >= this.amount
            ? true
            : Shards.getShardTypeInVessel(player, 3) == this.type && Shards.getEnergyInVessel(player, 3) >= this.amount;
      }
   }

   public void drain(EntityPlayer player) {
      if (Shards.getShardTypeInVessel(player, 1) == this.type) {
         float en = Shards.getEnergyInVessel(player, 1);
         if (en >= this.amount) {
            Shards.setEnergyToVessel(player, en - this.amount, 1);
         }
      }

      if (Shards.getShardTypeInVessel(player, 2) == this.type) {
         float en = Shards.getEnergyInVessel(player, 2);
         if (en >= this.amount) {
            Shards.setEnergyToVessel(player, en - this.amount, 2);
         }
      }

      if (Shards.getShardTypeInVessel(player, 3) == this.type) {
         float en = Shards.getEnergyInVessel(player, 3);
         if (en >= this.amount) {
            Shards.setEnergyToVessel(player, en - this.amount, 3);
         }
      }
   }

   public boolean tileHave(TileSpellForge spellForge) {
      return spellForge.getElementEnergy(this.type) >= this.amount;
   }

   public void drain(TileSpellForge spellForge) {
      spellForge.addElementEnergy(this.type, -this.amount);
   }
}
