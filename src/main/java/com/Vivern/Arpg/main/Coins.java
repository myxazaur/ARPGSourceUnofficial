//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.main;

import com.Vivern.Arpg.entity.EntityCoin;
import com.Vivern.Arpg.recipes.ExploringField;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent.Clone;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(
   modid = "arpg"
)
public class Coins {
   public static void loadCoinsFromNBT(EntityPlayer player) {
      int coins = player.getEntityData().hasKey("arpg.coins") ? player.getEntityData().getInteger("arpg.coins") : 0;
      setCoins(player, coins);
   }

   public static void saveCoinsToNBT(EntityPlayer player) {
      player.getEntityData().setInteger("arpg.coins", getCoins(player));
   }

   public static void setCoins(EntityPlayer player, int value) {
      player.getDataManager().set(PropertiesRegistry.COINS, value);
      player.getEntityData().setInteger("arpg.coins", getCoins(player));
   }

   public static int getCoins(EntityPlayer player) {
      return (Integer)player.getDataManager().get(PropertiesRegistry.COINS);
   }

   public static int addCoins(EntityPlayer player, int value) {
      int result = getCoins(player) + value;
      if (result >= 0) {
         player.getDataManager().set(PropertiesRegistry.COINS, result);
         player.getEntityData().setInteger("arpg.coins", result);
         return 0;
      } else {
         player.getDataManager().set(PropertiesRegistry.COINS, 0);
         player.getEntityData().setInteger("arpg.coins", 0);
         return -result;
      }
   }

   @SubscribeEvent
   public static void onPlayerDeath(Clone e) {
      ExploringField.onPlayerClone(e);
      PropertiesRegistry.onPlayerClone(e);
      Mana.setSwarmsWithoutMiniboss(e.getEntityPlayer(), Mana.getSwarmsWithoutMiniboss(e.getOriginal()));
      if (e.isWasDeath()) {
         EntityPlayer player = e.getOriginal();
         int coins = getCoins(player);
         int coinsfall = (int)(coins * 0.75);
         int coinspick = (int)(coins * 0.25);
         setCoins(e.getEntityPlayer(), coinspick);
         dropMoneyToWorld(player.world, coinsfall, 30, player.posX, player.posY + 0.5, player.posZ);
      }
   }

   public static void dropMoneyToWorld(World world, int coinsfall, int maximumEntities, double x, double y, double z) {
      if (coinsfall > 0) {
         int count = Math.min((int)Math.round(Math.pow(coinsfall, 0.3333333333333333)), maximumEntities);
         if (count > 0) {
            int forone = coinsfall / count;
            int forlast = coinsfall % count;
            if (forlast == 0) {
               if (count > 0) {
                  for (int c = 0; c < count; c++) {
                     EntityCoin entity = new EntityCoin(world, forone);
                     entity.setPosition(x, y, z);
                     world.spawnEntity(entity);
                  }
               }
            } else if (count > 0) {
               for (int c = 0; c < count; c++) {
                  EntityCoin entity = new EntityCoin(world, forone);
                  entity.setPosition(x, y, z);
                  world.spawnEntity(entity);
               }

               EntityCoin entity = new EntityCoin(world, forlast);
               entity.setPosition(x, y, z);
               world.spawnEntity(entity);
            }
         }
      }
   }
}
