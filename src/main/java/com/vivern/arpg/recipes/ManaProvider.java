package com.vivern.arpg.recipes;

import com.vivern.arpg.main.Mana;
import com.vivern.arpg.main.ShardType;
import com.vivern.arpg.main.Shards;
import com.vivern.arpg.tileentity.IManaBuffer;
import net.minecraft.entity.player.EntityPlayer;

public abstract class ManaProvider {
   public static ManaProvider EMPTY = new ManaProvider() {
      @Override
      public float getMana() {
         return 0.0F;
      }

      @Override
      public float getElementEnergy(ShardType type) {
         return 0.0F;
      }

      @Override
      public void addMana(float amount) {
      }

      @Override
      public void addElementEnergy(ShardType type, float amount) {
      }
   };

   public abstract float getMana();

   public abstract void addMana(float var1);

   public abstract float getElementEnergy(ShardType var1);

   public abstract void addElementEnergy(ShardType var1, float var2);

   public static class ManaProviderBlock extends ManaProvider {
      public final IManaBuffer buffer;

      public ManaProviderBlock(IManaBuffer buffer) {
         this.buffer = buffer;
      }

      @Override
      public float getMana() {
         return this.buffer.getManaBuffer().getManaStored();
      }

      @Override
      public void addMana(float amount) {
      }

      @Override
      public float getElementEnergy(ShardType type) {
         return 0.0F;
      }

      @Override
      public void addElementEnergy(ShardType type, float amount) {
      }
   }

   public static class ManaProviderPlayer extends ManaProvider {
      public final EntityPlayer player;

      public ManaProviderPlayer(EntityPlayer player) {
         this.player = player;
      }

      @Override
      public float getMana() {
         return Mana.getMana(this.player);
      }

      @Override
      public void addMana(float amount) {
         Mana.changeMana(this.player, amount);
      }

      @Override
      public float getElementEnergy(ShardType type) {
         if (Shards.getShardTypeInVessel(this.player, 1) == type) {
            return Shards.getEnergyInVessel(this.player, 1);
         } else if (Shards.getShardTypeInVessel(this.player, 2) == type) {
            return Shards.getEnergyInVessel(this.player, 2);
         } else {
            return Shards.getShardTypeInVessel(this.player, 3) == type ? Shards.getEnergyInVessel(this.player, 3) : 0.0F;
         }
      }

      @Override
      public void addElementEnergy(ShardType type, float amount) {
         Shards.addEnergyToPlayer(this.player, type, amount);
      }
   }
}
