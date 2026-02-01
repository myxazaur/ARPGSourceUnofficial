package com.vivern.arpg.main;

import com.vivern.arpg.entity.EntityShard;
import com.vivern.arpg.mobs.AbstractMob;
import com.vivern.arpg.network.PacketHandler;
import com.vivern.arpg.network.PacketSmallSomethingToClients;
import com.vivern.arpg.renders.ManaBar;
import java.util.Random;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(
   modid = "arpg"
)
public class Shards {
   @Deprecated
   public static void onPlayerAttackEntityEvent(AttackEntityEvent event) {
      Random rand = event.getEntityLiving().getRNG();
      if (!event.getEntityLiving().world.isRemote
         && rand.nextFloat() < 0.3F
         && event.getTarget() instanceof EntityCreature
         && !(event.getTarget() instanceof EntityPlayer)) {
         EntityCreature entity = (EntityCreature)event.getTarget();
         NBTTagCompound nbt = entity.getEntityData();
         if (nbt.hasKey("energy_shards")) {
            NBTTagCompound tag = nbt.getCompoundTag("energy_shards");
            if (tag.hasKey("amount") && tag.hasKey("type") && tag.hasKey("maximum")) {
               float max = tag.getFloat("maximum");
               float amount = tag.getFloat("amount");
               ShardType type = ShardType.byNameNullable(tag.getString("type"));
               int samples = rand.nextInt(3) + 1;

               for (int i = 0; i < samples; i++) {
                  if (type != null && amount > 0.0F && amount > max / 2.0F) {
                     float remove = amount >= 1.0F + max / 2.0F ? 0.5F + rand.nextFloat() / 2.0F : amount - max / 2.0F;
                     EntityShard shard = new EntityShard(event.getEntityLiving().world, type, entity);
                     shard.energy = remove;
                     event.getEntityLiving().world.spawnEntity(shard);
                     amount -= remove;
                  }
               }

               tag.setFloat("amount", amount);
            }
         }
      }
   }

   @SubscribeEvent
   public static void onCreatureDeath(LivingDropsEvent event) {
      if (!event.getEntityLiving().world.isRemote
         && event.isRecentlyHit()
         && (!event.getEntityLiving().isNonBoss() || event.getEntityLiving().getRNG().nextFloat() < AbstractMob.dropShardsChance)) {
         ShardType.dropShardsFromMob(event.getEntityLiving());
      }
   }

   @SubscribeEvent
   public static void onPlayerOpenInventory(GuiOpenEvent event) {
      if (event.getGui() instanceof GuiInventory) {
         ManaBar.energy_bars_enable = true;
      }
   }

   public static void setShardTypeToVessel(EntityPlayer player, ShardType type, int number_1a_2b_3c) {
      if (number_1a_2b_3c == 1) {
         player.getDataManager().set(PropertiesRegistry.VESSEL_A_TYPE, type.getName());
      }

      if (number_1a_2b_3c == 2) {
         player.getDataManager().set(PropertiesRegistry.VESSEL_B_TYPE, type.getName());
      }

      if (number_1a_2b_3c == 3) {
         player.getDataManager().set(PropertiesRegistry.VESSEL_C_TYPE, type.getName());
      }
   }

   public static void setShardTypeToVessel(EntityPlayer player, String type, int number_1a_2b_3c) {
      if (number_1a_2b_3c == 1) {
         player.getDataManager().set(PropertiesRegistry.VESSEL_A_TYPE, type);
      }

      if (number_1a_2b_3c == 2) {
         player.getDataManager().set(PropertiesRegistry.VESSEL_B_TYPE, type);
      }

      if (number_1a_2b_3c == 3) {
         player.getDataManager().set(PropertiesRegistry.VESSEL_C_TYPE, type);
      }
   }

   public static void setEnergyToVessel(EntityPlayer player, float value, int number_1a_2b_3c) {
      if (number_1a_2b_3c == 1) {
         player.getDataManager().set(PropertiesRegistry.VESSEL_A_ENERGY, value);
      }

      if (number_1a_2b_3c == 2) {
         player.getDataManager().set(PropertiesRegistry.VESSEL_B_ENERGY, value);
      }

      if (number_1a_2b_3c == 3) {
         player.getDataManager().set(PropertiesRegistry.VESSEL_C_ENERGY, value);
      }
   }

   public static ShardType getShardTypeInVessel(EntityPlayer player, int number_1a_2b_3c) {
      if (number_1a_2b_3c == 1) {
         return ShardType.byName((String)player.getDataManager().get(PropertiesRegistry.VESSEL_A_TYPE));
      } else if (number_1a_2b_3c == 2) {
         return ShardType.byName((String)player.getDataManager().get(PropertiesRegistry.VESSEL_B_TYPE));
      } else {
         return number_1a_2b_3c == 3 ? ShardType.byName((String)player.getDataManager().get(PropertiesRegistry.VESSEL_C_TYPE)) : null;
      }
   }

   public static ShardType getShardTypeInVesselNonull(EntityPlayer player, int number_1a_2b_3c) {
      if (number_1a_2b_3c == 1) {
         return ShardType.byName((String)player.getDataManager().get(PropertiesRegistry.VESSEL_A_TYPE));
      } else if (number_1a_2b_3c == 2) {
         return ShardType.byName((String)player.getDataManager().get(PropertiesRegistry.VESSEL_B_TYPE));
      } else {
         return number_1a_2b_3c == 3 ? ShardType.byName((String)player.getDataManager().get(PropertiesRegistry.VESSEL_C_TYPE)) : ShardType.FIRE;
      }
   }

   public static String getShardTypeInVesselAsString(EntityPlayer player, int number_1a_2b_3c) {
      if (number_1a_2b_3c == 1) {
         return (String)player.getDataManager().get(PropertiesRegistry.VESSEL_A_TYPE);
      } else if (number_1a_2b_3c == 2) {
         return (String)player.getDataManager().get(PropertiesRegistry.VESSEL_B_TYPE);
      } else {
         return number_1a_2b_3c == 3 ? (String)player.getDataManager().get(PropertiesRegistry.VESSEL_C_TYPE) : "";
      }
   }

   public static float getEnergyInVessel(EntityPlayer player, int number_1a_2b_3c) {
      if (number_1a_2b_3c == 1) {
         return (Float)player.getDataManager().get(PropertiesRegistry.VESSEL_A_ENERGY);
      } else if (number_1a_2b_3c == 2) {
         return (Float)player.getDataManager().get(PropertiesRegistry.VESSEL_B_ENERGY);
      } else {
         return number_1a_2b_3c == 3 ? (Float)player.getDataManager().get(PropertiesRegistry.VESSEL_C_ENERGY) : 0.0F;
      }
   }

   public static boolean addEnergyToPlayer(EntityPlayer player, ShardType type, float value) {
      for (int i = 1; i < 4; i++) {
         float energy = getEnergyInVessel(player, i);
         ShardType typein = getShardTypeInVessel(player, i);
         boolean isempty = typein.getName().isEmpty() || energy == 0.0F;
         if (typein == type || isempty) {
            if (isempty) {
               setShardTypeToVessel(player, type, i);
            }

            setEnergyToVessel(player, MathHelper.clamp(energy + value, 0.0F, 100.0F), i);
            if (player instanceof EntityPlayerMP) {
               PacketSmallSomethingToClients packet = new PacketSmallSomethingToClients();
               packet.writeargs(1, i);
               PacketHandler.sendTo(packet, (EntityPlayerMP)player);
            }

            if (energy + value <= 100.0F) {
               return true;
            }

            value = energy + value - 100.0F;
         }
      }

      return false;
   }

   public static void saveShardsToNBT(EntityPlayer player) {
      player.getEntityData().setFloat("vessel_a_energy", getEnergyInVessel(player, 1));
      player.getEntityData().setFloat("vessel_b_energy", getEnergyInVessel(player, 2));
      player.getEntityData().setFloat("vessel_c_energy", getEnergyInVessel(player, 3));
      player.getEntityData().setString("vessel_a_type", getShardTypeInVesselAsString(player, 1));
      player.getEntityData().setString("vessel_b_type", getShardTypeInVesselAsString(player, 2));
      player.getEntityData().setString("vessel_c_type", getShardTypeInVesselAsString(player, 3));
   }

   public static void loadShardsFromNBT(EntityPlayer player) {
      float ea = player.getEntityData().hasKey("vessel_a_energy") ? player.getEntityData().getFloat("vessel_a_energy") : 0.0F;
      setEnergyToVessel(player, ea, 1);
      float eb = player.getEntityData().hasKey("vessel_b_energy") ? player.getEntityData().getFloat("vessel_b_energy") : 0.0F;
      setEnergyToVessel(player, eb, 2);
      float ec = player.getEntityData().hasKey("vessel_c_energy") ? player.getEntityData().getFloat("vessel_c_energy") : 0.0F;
      setEnergyToVessel(player, ec, 3);
      String ta = player.getEntityData().hasKey("vessel_a_type") ? player.getEntityData().getString("vessel_a_type") : "";
      setShardTypeToVessel(player, ta, 1);
      String tb = player.getEntityData().hasKey("vessel_b_type") ? player.getEntityData().getString("vessel_b_type") : "";
      setShardTypeToVessel(player, tb, 2);
      String tc = player.getEntityData().hasKey("vessel_c_type") ? player.getEntityData().getString("vessel_c_type") : "";
      setShardTypeToVessel(player, tc, 3);
   }

   @Deprecated
   public static void onLivingSpawn(LivingSpawnEvent event) {
      if (event.getEntityLiving() != null && !event.getWorld().isRemote && event.getEntityLiving() instanceof EntityCreature) {
         int dim = event.getWorld().provider.getDimension();
         if (dim == 0) {
            return;
         }

         EntityCreature entity = (EntityCreature)event.getEntityLiving();
         Random rand = entity.getRNG();
         float maxH = entity.getMaxHealth();
         IAttributeInstance insD = entity.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
         IAttributeInstance insA = entity.getEntityAttribute(SharedMonsterAttributes.ARMOR);
         IAttributeInstance insP = entity.getEntityAttribute(PropertiesRegistry.ARMOR_PROTECTION);
         int damage = insD != null ? (int)Math.round(insD.getAttributeValue()) : 0;
         int armor = insA != null ? (int)Math.round(insA.getAttributeValue() + 1.0) : 0;
         int prot = insP != null ? (int)Math.round(insP.getAttributeValue()) : 0;
         maxH = Math.max(maxH, 1.0F);
         damage = Math.max(damage, 1);
         armor = Math.max(armor, 1);
         prot = Math.max(prot, 1);
         if (dim == -1) {
            float amount = rand.nextFloat() * (rand.nextInt(Math.round(maxH / 5.0F)) + rand.nextInt(damage) + rand.nextInt(armor) + rand.nextInt(prot)) * 0.6F;
            if (amount > 0.8) {
               setShards(entity, ShardType.FIRE, amount);
            }
         }

         if (dim == 100) {
            float amount = rand.nextFloat() * (rand.nextInt(Math.round(maxH / 5.0F)) + rand.nextInt(damage) + rand.nextInt(armor) + rand.nextInt(prot)) * 0.4F;
            if (amount > 0.8) {
               setShards(entity, ShardType.COLD, amount);
            }
         }
      }
   }

   public static void setShards(EntityCreature entity, ShardType type, float amount) {
      NBTTagCompound nbt = entity.getEntityData();
      if (!nbt.hasKey("energy_shards")) {
         nbt.setTag("energy_shards", nbt.getCompoundTag("energy_shards"));
      }

      NBTTagCompound tag = nbt.getCompoundTag("energy_shards");
      tag.setString("type", type.getName());
      tag.setFloat("amount", amount);
      tag.setFloat("maximum", amount);
   }
}
