//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.main;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.UUID;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.player.PlayerEvent.Clone;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(
   modid = "arpg"
)
public class PropertiesRegistry {
   public static final IAttribute MANA_MAX = new RangedAttribute(null, "arpg.mana", 20.0, 0.0, 10000.0).setShouldWatch(true);
   public static final DataParameter<Float> MANA = EntityDataManager.createKey(EntityPlayer.class, DataSerializers.FLOAT);
   public static final IAttribute MANASPEED_MAX = new RangedAttribute(null, "arpg.manaspeed", 0.2, 0.0, 10000.0).setShouldWatch(true);
   public static final DataParameter<Float> MANASPEED = EntityDataManager.createKey(EntityPlayer.class, DataSerializers.FLOAT);
   public static final IAttribute ENTITY_COLOR_RED_MAX = new RangedAttribute(null, "arpg.color_red", 1.0, 0.0, 1.0).setShouldWatch(true);
   public static final IAttribute ENTITY_COLOR_GREEN_MAX = new RangedAttribute(null, "arpg.color_green", 1.0, 0.0, 1.0).setShouldWatch(true);
   public static final IAttribute ENTITY_COLOR_BLUE_MAX = new RangedAttribute(null, "arpg.color_blue", 1.0, 0.0, 1.0).setShouldWatch(true);
   public static final IAttribute MAGIC_POWER_MAX = new RangedAttribute(null, "arpg.magic_power", 1.0, 0.1, 10000.0).setShouldWatch(true);
   public static final IAttribute ARMOR_PROTECTION = new RangedAttribute(null, "arpg.armor_protection", 0.0, -999999.0, 999999.0).setShouldWatch(true);
   public static final IAttribute LEADERSHIP = new RangedAttribute(null, "arpg.leadership", 10.0, 0.0, 1000.0).setShouldWatch(true);
   public static final IAttribute JUMP_HEIGHT = new RangedAttribute(null, "arpg.jump_height_bonus", 0.0, -1000.0, 1000.0).setShouldWatch(true);
   public static final IAttribute MELEE_KNOCKBACK = new RangedAttribute(null, "arpg.melee_knockback", 0.0, -1000.0, 1000.0).setShouldWatch(true);
   public static final IAttribute VAMPIRISM = new RangedAttribute(null, "arpg.vampirism", 0.0, -99000.0, 99000.0).setShouldWatch(true);
   public static final IAttribute AIRBORNE_MOBILITY = new RangedAttribute(null, "arpg.airborne_mobility", 0.0, -1024.0, 1024.0).setShouldWatch(true);
   public static final DataParameter<Boolean> FLYING = EntityDataManager.createKey(EntityPlayer.class, DataSerializers.BOOLEAN);
   public static final DataParameter<Float> VESSEL_A_ENERGY = EntityDataManager.createKey(EntityPlayer.class, DataSerializers.FLOAT);
   public static final DataParameter<Float> VESSEL_B_ENERGY = EntityDataManager.createKey(EntityPlayer.class, DataSerializers.FLOAT);
   public static final DataParameter<Float> VESSEL_C_ENERGY = EntityDataManager.createKey(EntityPlayer.class, DataSerializers.FLOAT);
   public static final DataParameter<String> VESSEL_A_TYPE = EntityDataManager.createKey(EntityPlayer.class, DataSerializers.STRING);
   public static final DataParameter<String> VESSEL_B_TYPE = EntityDataManager.createKey(EntityPlayer.class, DataSerializers.STRING);
   public static final DataParameter<String> VESSEL_C_TYPE = EntityDataManager.createKey(EntityPlayer.class, DataSerializers.STRING);
   public static final DataParameter<Integer> KEYS_PRESSED = EntityDataManager.createKey(EntityPlayer.class, DataSerializers.VARINT);
   public static final DataParameter<Integer> COINS = EntityDataManager.createKey(EntityPlayer.class, DataSerializers.VARINT);
   public static final DataParameter<Integer> RADIATION = EntityDataManager.createKey(EntityPlayer.class, DataSerializers.VARINT);
   public static final DataParameter<Integer> ANIMATIONS = EntityDataManager.createKey(EntityPlayer.class, DataSerializers.VARINT);
   public static final DataParameter<Integer> SWARM_POINTS = EntityDataManager.createKey(EntityPlayer.class, DataSerializers.VARINT);

   @SubscribeEvent
   public static void onPlayerConstructing(EntityConstructing event) {
      if (event.getEntity() instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)event.getEntity();
         player.getAttributeMap().registerAttribute(MANA_MAX);
         player.getAttributeMap().registerAttribute(MANASPEED_MAX);
         player.getAttributeMap().registerAttribute(LEADERSHIP);
         player.getAttributeMap().registerAttribute(AIRBORNE_MOBILITY);
         player.getDataManager().register(MANA, (float)MANA_MAX.getDefaultValue());
         player.getDataManager().register(MANASPEED, (float)MANASPEED_MAX.getDefaultValue());
         player.getDataManager().register(VESSEL_A_ENERGY, 0.0F);
         player.getDataManager().register(VESSEL_B_ENERGY, 0.0F);
         player.getDataManager().register(VESSEL_C_ENERGY, 0.0F);
         player.getDataManager().register(VESSEL_A_TYPE, "");
         player.getDataManager().register(VESSEL_B_TYPE, "");
         player.getDataManager().register(VESSEL_C_TYPE, "");
         player.getDataManager().register(FLYING, false);
         player.getDataManager().register(ANIMATIONS, 0);
         player.getDataManager().register(KEYS_PRESSED, 0);
         player.getDataManager().register(COINS, 0);
         player.getDataManager().register(RADIATION, 0);
         player.getDataManager().register(SWARM_POINTS, 0);
         player.getAttributeMap().registerAttribute(MAGIC_POWER_MAX);
         updatePlayerPermanentAttributes(player);
      }
   }

   @SubscribeEvent
   public static void onEntityLivingConstructing(EntityConstructing event) {
      if (event.getEntity() instanceof EntityLivingBase) {
         EntityLivingBase base = (EntityLivingBase)event.getEntity();
         base.getAttributeMap().registerAttribute(ENTITY_COLOR_RED_MAX);
         base.getAttributeMap().registerAttribute(ENTITY_COLOR_GREEN_MAX);
         base.getAttributeMap().registerAttribute(ENTITY_COLOR_BLUE_MAX);
         base.getAttributeMap().registerAttribute(ARMOR_PROTECTION);
         base.getAttributeMap().registerAttribute(JUMP_HEIGHT);
         base.getAttributeMap().registerAttribute(MELEE_KNOCKBACK);
         base.getAttributeMap().registerAttribute(VAMPIRISM);
      }
   }

   public static void onPlayerClone(Clone event) {
      EntityPlayer originalplayer = event.getOriginal();
      EntityPlayer newplayer = event.getEntityPlayer();
      PermanentAttributes attributes = getPlayerPermanentAttributes(originalplayer);
      setPlayerPermanentAttributes(newplayer, attributes);
   }

   public static PermanentAttributes getPlayerPermanentAttributes(EntityPlayer player) {
      NBTTagCompound data = player.getEntityData().getCompoundTag("permanentAttributes");
      return new PermanentAttributes(data);
   }

   public static void setPlayerPermanentAttributes(EntityPlayer player, PermanentAttributes attributes) {
      player.getEntityData().setTag("permanentAttributes", attributes.writeToNbt());
      Multimap<String, AttributeModifier> multimap = attributes.generateMultimap();
      player.getAttributeMap().applyAttributeModifiers(multimap);
   }

   public static void updatePlayerPermanentAttributes(EntityPlayer player) {
      PermanentAttributes attributes = getPlayerPermanentAttributes(player);
      Multimap<String, AttributeModifier> multimap = attributes.generateMultimap();
      player.getAttributeMap().applyAttributeModifiers(multimap);
   }

   public static class PermanentAttributes {
      public int[] liveBoosters = new int[2];
      public int[] manaBoosters = new int[1];
      public boolean sprintDisabled = false;

      public PermanentAttributes(NBTTagCompound compound) {
         this.readFromNbt(compound);
      }

      public Multimap<String, AttributeModifier> generateMultimap() {
         Multimap<String, AttributeModifier> multimap = HashMultimap.create();
         UUID uuidLive = UUID.fromString("0469067a-020c-4832-bf26-9ddbe60e8252");
         UUID uuidMana = UUID.fromString("4642baf9-a324-4bff-8378-c1f48651ae9f");
         multimap.put(SharedMonsterAttributes.MAX_HEALTH.getName(), new AttributeModifier(uuidLive, "permanent maxlive", this.getBonusMaxHealth(), 0));
         multimap.put(PropertiesRegistry.MANA_MAX.getName(), new AttributeModifier(uuidMana, "permanent maxmana", this.getBonusMaxMana(), 0));
         return multimap;
      }

      public int getBonusMaxHealth() {
         int full = 0;

         for (int i = 0; i < this.liveBoosters.length; i++) {
            full += getBonusHealthOnLevel(i) * this.liveBoosters[i];
         }

         return full;
      }

      public int getBonusMaxMana() {
         int full = 0;

         for (int i = 0; i < this.manaBoosters.length; i++) {
            full += getBonusManaOnLevel(i) * this.manaBoosters[i];
         }

         return full;
      }

      public static int getMaximumLiveBoosts(int level) {
         if (level == 0) {
            return 5;
         } else {
            return level == 1 ? 3 : 0;
         }
      }

      public static int getMaximumManaBoosts(int level) {
         return level == 0 ? 5 : 0;
      }

      public static int getBonusHealthOnLevel(int level) {
         if (level == 0) {
            return 1;
         } else {
            return level == 1 ? 2 : 0;
         }
      }

      public static int getBonusManaOnLevel(int level) {
         return level == 0 ? 1 : 0;
      }

      public NBTTagCompound writeToNbt() {
         NBTTagCompound compound = new NBTTagCompound();

         for (int i = 0; i < this.liveBoosters.length; i++) {
            compound.setInteger("live" + i, this.liveBoosters[i]);
         }

         for (int i = 0; i < this.manaBoosters.length; i++) {
            compound.setInteger("mana" + i, this.manaBoosters[i]);
         }

         compound.setBoolean("nosprint", this.sprintDisabled);
         return compound;
      }

      public void readFromNbt(NBTTagCompound compound) {
         for (int i = 0; i < this.liveBoosters.length; i++) {
            String name = "live" + i;
            if (compound.hasKey(name)) {
               this.liveBoosters[i] = compound.getInteger(name);
            }
         }

         for (int ix = 0; ix < this.manaBoosters.length; ix++) {
            String name = "mana" + ix;
            if (compound.hasKey(name)) {
               this.manaBoosters[ix] = compound.getInteger(name);
            }
         }

         if (compound.hasKey("nosprint")) {
            this.sprintDisabled = compound.getBoolean("nosprint");
         }
      }
   }
}
