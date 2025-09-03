//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.main;

import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import com.Vivern.Arpg.biomes.BiomeControlled;
import com.Vivern.Arpg.dimensions.generationutils.AbstractWorldProvider;
import com.Vivern.Arpg.elements.IWings;
import com.Vivern.Arpg.elements.animation.Flicks;
import com.Vivern.Arpg.events.Debugger;
import com.Vivern.Arpg.potions.AdvancedMobEffects;
import com.Vivern.Arpg.potions.PotionEffects;
import com.Vivern.Arpg.renders.KillScore;
import com.Vivern.Arpg.renders.ManaBar;
import com.Vivern.Arpg.weather.Weather;
import com.google.common.collect.Multimap;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Post;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber(
   modid = "arpg"
)
public class Mana {
   public static long radTime = 0L;

   public static float getMana(EntityPlayer player) {
      return (Float)player.getDataManager().get(PropertiesRegistry.MANA);
   }

   public static float getManaMax(EntityPlayer player) {
      return (float)player.getEntityAttribute(PropertiesRegistry.MANA_MAX).getAttributeValue();
   }

   public static void setMana(EntityPlayer player, float value) {
      player.getDataManager().set(PropertiesRegistry.MANA, value);
      player.getEntityData().setFloat("arpg.mana", getMana(player));
   }

   public static void changeMana(EntityPlayer player, float value) {
      player.getDataManager().set(PropertiesRegistry.MANA, (Float)player.getDataManager().get(PropertiesRegistry.MANA) + value);
      player.getEntityData().setFloat("arpg.mana", getMana(player));
      player.getDataManager().set(PropertiesRegistry.MANASPEED, 0.001F);
   }

   public static void giveMana(EntityPlayer player, float value) {
      player.getDataManager().set(PropertiesRegistry.MANA, (Float)player.getDataManager().get(PropertiesRegistry.MANA) + value);
   }

   private static void saveManaToNBT(EntityPlayer player) {
      player.getEntityData().setFloat("arpg.mana", getMana(player));
   }

   private static float loadManaFromNBT(EntityPlayer player) {
      return player.getEntityData().hasKey("arpg.mana") ? player.getEntityData().getFloat("arpg.mana") : getManaMax(player);
   }

   @SubscribeEvent
   public static void onPlayerLogIn(PlayerLoggedInEvent event) {
      setMana(event.player, loadManaFromNBT(event.player));
      setManaSpeed(event.player, loadManaSpeedFromNBT(event.player));
      Shards.loadShardsFromNBT(event.player);
      Coins.loadCoinsFromNBT(event.player);
      boolean fly = event.player.getEntityData().hasKey("wflying") ? event.player.getEntityData().getBoolean("wflying") : false;
      event.player.getEntityData().setBoolean("wflying", fly);
      boolean rad = event.player.getEntityData().hasKey("arpg.radiation");
      if (rad) {
         event.player.getDataManager().set(PropertiesRegistry.RADIATION, event.player.getEntityData().getInteger("arpg.radiation"));
      }

      boolean swarm = event.player.getEntityData().hasKey("arpg.swarm_points");
      if (swarm) {
         event.player.getDataManager().set(PropertiesRegistry.SWARM_POINTS, event.player.getEntityData().getInteger("arpg.swarm_points"));
      }

      event.player.sendMessage(new TextComponentString("" + TextFormatting.DARK_PURPLE + TextFormatting.ITALIC + "<AbstractRPG> /arpg info"));
   }

   @SubscribeEvent
   public static void onPlayerLogOut(PlayerLoggedOutEvent event) {
      saveManaToNBT(event.player);
      saveManaSpeedToNBT(event.player);
      Shards.saveShardsToNBT(event.player);
      Coins.saveCoinsToNBT(event.player);
      event.player.getEntityData().setBoolean("wflying", (Boolean)event.player.getDataManager().get(PropertiesRegistry.FLYING));
      event.player.getEntityData().setInteger("arpg.radiation", (Integer)event.player.getDataManager().get(PropertiesRegistry.RADIATION));
      event.player.getEntityData().setInteger("arpg.swarm_points", (Integer)event.player.getDataManager().get(PropertiesRegistry.SWARM_POINTS));
   }

   public static float getManaSpeed(EntityPlayer player) {
      return (Float)player.getDataManager().get(PropertiesRegistry.MANASPEED);
   }

   public static float getManaSpeedMax(EntityPlayer player) {
      return (float)player.getEntityAttribute(PropertiesRegistry.MANASPEED_MAX).getAttributeValue();
   }

   public static void changeManaSpeed(EntityPlayer player, float value) {
      player.getDataManager().set(PropertiesRegistry.MANASPEED, (Float)player.getDataManager().get(PropertiesRegistry.MANASPEED) + value);
   }

   public static void multipleManaSpeed(EntityPlayer player, float value, float plus) {
      player.getDataManager()
         .set(PropertiesRegistry.MANASPEED, (Float)player.getDataManager().get(PropertiesRegistry.MANASPEED) * value + plus);
   }

   public static void setManaSpeed(EntityPlayer player, float value) {
      player.getDataManager().set(PropertiesRegistry.MANASPEED, value);
   }

   private static void saveManaSpeedToNBT(EntityPlayer player) {
      player.getEntityData().setFloat("arpg.manaspeed", getManaSpeed(player));
   }

   private static float loadManaSpeedFromNBT(EntityPlayer player) {
      return player.getEntityData().hasKey("arpg.manaspeed") ? player.getEntityData().getFloat("arpg.manaspeed") : getManaSpeedMax(player);
   }

   public static int getSwarmTicks(EntityPlayer player) {
      return player.getEntityData().hasKey("arpg.swarmticks") ? player.getEntityData().getInteger("arpg.swarmticks") : 0;
   }

   public static void setSwarmTicks(EntityPlayer player, int value) {
      player.getEntityData().setInteger("arpg.swarmticks", value);
   }

   public static int addSwarmTicks(EntityPlayer player, int value) {
      int sett = getSwarmTicks(player) + value;
      setSwarmTicks(player, sett);
      return sett;
   }

   public static int getSwarmsWithoutMiniboss(EntityPlayer player) {
      return player.getEntityData().hasKey("arpg.swarmsnominiboss") ? player.getEntityData().getInteger("arpg.swarmsnominiboss") : 0;
   }

   public static void setSwarmsWithoutMiniboss(EntityPlayer player, int value) {
      player.getEntityData().setInteger("arpg.swarmsnominiboss", value);
   }

   public static int addSwarmsWithoutMiniboss(EntityPlayer player, int value) {
      int sett = getSwarmsWithoutMiniboss(player) + value;
      setSwarmsWithoutMiniboss(player, sett);
      return sett;
   }

   @SubscribeEvent
   public static void onPlayerUpdate(LivingUpdateEvent event) {
      if (event.getEntityLiving() instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)event.getEntityLiving();
         World world = event.getEntityLiving().world;
         if (world.isRemote) {
            Weapons.decrementPlayerAnimation(player);
            KillScore.tick();
            if (player == Minecraft.getMinecraft().player) {
               Flicks.instance.clientTick(player.ticksExisted);
               if (world.provider instanceof AbstractWorldProvider) {
                  ((AbstractWorldProvider)world.provider).onUpdatePlayerOnClient(player);
               }
            }
         }

         if (!world.isRemote) {
            float speed = getManaSpeed(player);
            float speedmax = getManaSpeedMax(player);
            float manamax = getManaMax(player);
            if (getMana(player) < manamax) {
               if (speed < speedmax) {
                  multipleManaSpeed(player, 1.0F + 1.0E-4F * speedmax, 0.001F * speedmax);
               }

               if (speed < 0.0F) {
                  setManaSpeed(player, 0.001F);
               }

               giveMana(player, speed);
            } else if (getMana(player) > manamax) {
               setMana(player, manamax);
            }

            if (player.ticksExisted % 60 == 0) {
               int rad = getRad(player);
               if (rad >= 1600) {
                  player.addExperience(-1);
                  if (rad >= 2000) {
                     float health = player.getHealth();
                     if (health > 1.0F) {
                        player.setHealth(health - 1.0F);
                     } else {
                        player.attackEntityFrom(WeaponDamage.RADIATION, 1.0F);
                     }

                     if (rad >= 2200) {
                        int lvl = Math.min(Math.max(rad - 2300, 0) / 200, 16);
                        player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 80, lvl));
                        player.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 80, lvl));
                        player.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 80, lvl));
                     }
                  }
               }

               addRad(player, -1, false);
               Biome biome = world.getBiome(player.getPosition());
               if (biome instanceof BiomeControlled) {
                  ((BiomeControlled)biome).onPlayer60ticksInBiome((BiomeControlled)biome, player);
               }
            }

            if (player != null) {
               IInventory inv = BaublesApi.getBaubles(player);

               for (int i = 0; i < 7; i++) {
                  Item bitem = inv.getStackInSlot(i).getItem();
                  UUID uuid = UUID.fromString("CB2F4" + i + "D3-64" + i + "A-4F78-A497-9C56A33DB" + i + "BB");
                  String name = bitem.getRegistryName().toString();
                  if (!(bitem instanceof IAttributedBauble) || bitem == null) {
                     checkIds(uuid, player);
                  } else if (!((IAttributedBauble)bitem).useMultimap()) {
                     IAttribute attr = ((IAttributedBauble)bitem).getAttribute();
                     AttributeModifier modif = new AttributeModifier(uuid, name, ((IAttributedBauble)bitem).value(), ((IAttributedBauble)bitem).operation());
                     checkNames(uuid, name, player);
                     if (!player.getEntityAttribute(attr).hasModifier(modif)) {
                        player.getEntityAttribute(attr).applyModifier(modif);
                     }
                  } else {
                     Multimap<String, AttributeModifier> multimap = ((IAttributedBauble)bitem).getAttributeModifiers(player, i, inv.getStackInSlot(i));
                     player.getAttributeMap().applyAttributeModifiers(multimap);
                  }

                  if (i == 5 && !(bitem instanceof IWings)) {
                     player.getDataManager().set(PropertiesRegistry.FLYING, false);
                  }
               }
            }

            MobSpawn.onPlayerUpdate(world, player);
         }

         Keys.onUpdate(player);
         JumpBonusTracker.airbornMovement(player);
         Debugger.checkChest(player);
      }

      Weather.updateEntity(event.getEntityLiving());
      AdvancedMobEffects.updateEffects(event.getEntityLiving());
   }

   private static void checkNames(UUID uuid, String name, EntityPlayer player) {
      for (IAttributeInstance instance : player.getAttributeMap().getAllAttributes()) {
         if (instance != null && instance.getModifier(uuid) != null && instance.getModifier(uuid).getName() != name) {
            instance.removeModifier(uuid);
         }
      }
   }

   private static void checkIds(UUID uuid, EntityPlayer player) {
      for (IAttributeInstance instance : player.getAttributeMap().getAllAttributes()) {
         if (instance != null && instance.getModifier(uuid) != null) {
            instance.removeModifier(uuid);
         }
      }
   }

   @SubscribeEvent
   @SideOnly(Side.CLIENT)
   public static void onRenderOverlayPost(Post event) {
      if (event.getType() == ElementType.TEXT) {
         ManaBar.getInstance().renderPlayerManaBar();
      }
   }

   public static float getMagicPowerMax(EntityPlayer player) {
      return (float)player.getEntityAttribute(PropertiesRegistry.MAGIC_POWER_MAX).getAttributeValue();
   }

   public static double getArmorProtection(EntityLivingBase entitylivingbase) {
      return entitylivingbase.getEntityAttribute(PropertiesRegistry.ARMOR_PROTECTION).getAttributeValue();
   }

   public static int getLeadership(EntityPlayer player) {
      return (int)Math.round(player.getEntityAttribute(PropertiesRegistry.LEADERSHIP).getAttributeValue());
   }

   public static float getRadiationProtectionOfArmor(Item armor) {
      if (armor == ItemsRegister.HAZARDHELM) {
         return 0.16F;
      } else if (armor == ItemsRegister.HAZARDCHEST) {
         return 0.2F;
      } else if (armor == ItemsRegister.HAZARDLEGS) {
         return 0.18F;
      } else if (armor == ItemsRegister.HAZARDBOOTS) {
         return 0.16F;
      } else if (armor == ItemsRegister.TOXINIUMHELM) {
         return 0.13F;
      } else if (armor == ItemsRegister.TOXINIUMCHEST) {
         return 0.18F;
      } else if (armor == ItemsRegister.TOXINIUMLEGS) {
         return 0.16F;
      } else {
         return armor == ItemsRegister.TOXINIUMBOOTS ? 0.13F : 0.0F;
      }
   }

   public static int getRad(EntityPlayer player) {
      return (Integer)player.getDataManager().get(PropertiesRegistry.RADIATION);
   }

   public static void addRad(EntityPlayer player, int add, boolean fromOutside) {
      if (fromOutside) {
         if (player.isPotionActive(PotionEffects.FIBER_BANDAGING)) {
            add -= 8;
         }

         float radProtection = 0.0F;

         for (ItemStack armorstack : player.getArmorInventoryList()) {
            radProtection += getRadiationProtectionOfArmor(armorstack.getItem());
         }

         IBaublesItemHandler baublesh = BaublesApi.getBaublesHandler(player);
         if (baublesh != null && baublesh.getStackInSlot(5).getItem() == ItemsRegister.ANTIRADPACK) {
            radProtection += 0.25F;
         }

         add = Math.round(add * Math.max(1.0F - radProtection, 0.0F));
      }

      player.getDataManager().set(PropertiesRegistry.RADIATION, Math.max(getRad(player) + add, 0));
      if (!player.world.isRemote && add > 0) {
         long rz = Math.abs(player.world.getWorldTime() - radTime);
         if ((rz <= 380L || add <= 5) && (rz <= 60L || add < 30)) {
            if (rz > 270L) {
               player.world
                  .playSound(
                     null,
                     player.posX,
                     player.posY,
                     player.posZ,
                     Sounds.rad_small,
                     SoundCategory.AMBIENT,
                     0.7F,
                     0.9F + player.getRNG().nextFloat() / 5.0F
                  );
               radTime = player.world.getWorldTime();
            }
         } else {
            player.world
               .playSound(
                  null,
                  player.posX,
                  player.posY,
                  player.posZ,
                  Sounds.rad,
                  SoundCategory.AMBIENT,
                  0.7F,
                  0.9F + player.getRNG().nextFloat() / 5.0F
               );
            radTime = player.world.getWorldTime();
         }
      }
   }

   public static int getSwarmPoints(EntityPlayer player) {
      return (Integer)player.getDataManager().get(PropertiesRegistry.SWARM_POINTS);
   }

   public static void addSwarmPoints(EntityPlayer player, int add) {
      player.getDataManager().set(PropertiesRegistry.SWARM_POINTS, Math.max(getSwarmPoints(player) + add, 0));
   }

   public static void setSwarmPoints(EntityPlayer player, int set) {
      player.getDataManager().set(PropertiesRegistry.SWARM_POINTS, Math.max(set, 0));
   }
}
