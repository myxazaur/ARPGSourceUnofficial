package com.vivern.arpg.main;

import java.util.HashMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class WeaponParameters {
   public static WeaponParameters EMPTY = new WeaponParameters("empty");
   public static HashMap<String, WeaponParameters> registry = new HashMap<>();
   HashMap<String, Float> parameters = new HashMap<>();
   public String name;
   public static final float AUTO = -8.792019E7F;
   private static float levelRate = 1.0F;
   public static float EXlevelFROZEN = 0.0F;
   public static float EXlevelTOXINIUM = 0.0F;
   public static float EXlevelDUNGEON_CHEST = 0.0F;
   public static float EXlevelSEA_MIDDLE = 0.0F;
   public static float EXlevelPLASMA = 0.0F;

   protected WeaponParameters(String name) {
      this.name = name;
   }

   public WeaponParameters add(String key, float value) {
      this.parameters.put(key, value);
      return this;
   }

   public WeaponParameters potion(String potion, int time, int power, int timeEnchAdd, float powerEnchAdd, boolean isPowerLeveled) {
      this.parameters.put(potion + "_time", (float)time);
      this.parameters.put(potion + "_time_ench", (float)timeEnchAdd);
      if (isPowerLeveled) {
         this.parameters.put(potion + "_power", (power + 1.0F) * levelRate - 1.0F);
         this.parameters.put(potion + "_power_ench", powerEnchAdd * levelRate);
      } else {
         this.parameters.put(potion + "_power", (float)power);
         this.parameters.put(potion + "_power_ench", powerEnchAdd);
      }

      return this;
   }

   public WeaponParameters potion(
      String potion,
      int time,
      int timeEnchAdd,
      int timeMax,
      int timeMaxEnchAdd,
      int power,
      float powerEnchAdd,
      int powerMax,
      int powerMaxEnchAdd,
      boolean isPowerLeveled
   ) {
      this.parameters.put(potion + "_time", (float)time);
      this.parameters.put(potion + "_time_ench", (float)timeEnchAdd);
      if (isPowerLeveled) {
         this.parameters.put(potion + "_power", power * levelRate);
         this.parameters.put(potion + "_power_ench", powerEnchAdd * levelRate);
      } else {
         this.parameters.put(potion + "_power", (float)power);
         this.parameters.put(potion + "_power_ench", powerEnchAdd);
      }

      this.parameters.put(potion + "_time_max", (float)timeMax);
      this.parameters.put(potion + "_time_max_ench", (float)timeMaxEnchAdd);
      this.parameters.put(potion + "_power_max", (float)powerMax);
      this.parameters.put(potion + "_power_max_ench", (float)powerMaxEnchAdd);
      return this;
   }

   public WeaponParameters add(String key, float value, float enchantBonus) {
      this.parameters.put(key, value);
      this.parameters.put(key + "_ench", enchantBonus);
      return this;
   }

   public WeaponParameters lvl(String key, float value) {
      this.parameters.put(key, value * levelRate);
      return this;
   }

   public WeaponParameters lvl(String key, float value, float enchantBonus) {
      this.parameters.put(key, value * levelRate);
      if (enchantBonus == -8.792019E7F) {
         float d = value * (levelRate + 0.6F) - value * levelRate;
         this.parameters.put(key + "_ench", d / 7.0F);
      } else {
         this.parameters.put(key + "_ench", enchantBonus * levelRate);
      }

      return this;
   }

   public boolean has(String key) {
      return this.parameters.containsKey(key);
   }

   public float get(String key) {
      return this.parameters.getOrDefault(key, 0.0F);
   }

   public int geti(String key) {
      return (int)this.parameters.getOrDefault(key, 0.0F).floatValue();
   }

   public float getEnchanted(String key, int enchantLevel) {
      return this.parameters.getOrDefault(key, 0.0F) + this.parameters.getOrDefault(key + "_ench", 0.0F) * enchantLevel;
   }

   public int getEnchantedi(String key, int enchantLevel) {
      return (int)this.getEnchanted(key, enchantLevel);
   }

   public PotionEffect getPotion(String potionName, Potion potion, int enchantLevel) {
      return new PotionEffect(
         potion,
         this.getEnchantedi(potionName + "_time", enchantLevel),
         GetMOP.floatToIntWithChance(this.getEnchanted(potionName + "_power", enchantLevel), GetMOP.rand)
      );
   }

   public PotionEffect mixPotion(
      Entity entity,
      String potionName,
      Potion potion,
      int enchantLevel,
      Weapons.EnumPotionMix mixTypeTime,
      Weapons.EnumPotionMix mixTypePower,
      Weapons.EnumMathOperation operationTime,
      Weapons.EnumMathOperation operationPower
   ) {
      int time = this.getEnchantedi(potionName + "_time", enchantLevel);
      int power = GetMOP.floatToIntWithChance(this.getEnchanted(potionName + "_power", enchantLevel), GetMOP.rand);
      int timeBound = this.getEnchantedi(potionName + "_time_max", enchantLevel);
      int powerBound = this.getEnchantedi(potionName + "_power_max", enchantLevel);
      return Weapons.mixPotion(entity, potion, (float)time, (float)power, mixTypeTime, mixTypePower, operationTime, operationPower, timeBound, powerBound);
   }

   public static WeaponParameters newparam(Item weapon) {
      return newparam(weapon.getRegistryName().getPath());
   }

   public static WeaponParameters newparam(String name) {
      WeaponParameters weaponParameters = new WeaponParameters(name);
      registry.put(name, weaponParameters);
      return weaponParameters;
   }

   public static WeaponParameters getWeaponParameters(Item weapon) {
      return getWeaponParameters(weapon.getRegistryName().getPath());
   }

   public static WeaponParameters getWeaponParameters(String name) {
      return registry.getOrDefault(name, EMPTY);
   }

   public static void registerAll() {
      float raise = 1.2F;
      levelRate = 1.0F;
      newparam(ItemsRegister.PUMPSHOTGUN)
         .lvl("damage", 3.0F, -8.792019E7F)
         .add("knockback", 0.26F, 0.21F)
         .add("bullet_damage", 0.5F)
         .add("bullet_knockback", 0.33F)
         .add("cooldown", 5.0F, -1.0F)
         .add("reload", 20.0F, -4.0F)
         .add("shots", 6.0F, 1.0F)
         .add("distance", 12.0F, 1.5F)
         .add("inaccuracy", 3.0F, -0.25F);
      newparam(ItemsRegister.GRENADELAUNCHER)
         .lvl("damage", 6.25F, -8.792019E7F)
         .add("knockback", 1.2F, 0.35F)
         .add("cooldown", 10.0F, -4.0F)
         .add("reload", 40.0F, -8.0F)
         .add("velocity", 1.3F)
         .add("inaccuracy", 6.0F, -1.6F);
      newparam(ItemsRegister.SLIMESHOTGUN)
         .lvl("damage", 1.6F, -8.792019E7F)
         .lvl("impact_damage", 0.8F, -8.792019E7F)
         .add("damage_radius", 2.5F, 0.2F)
         .add("knockback", 0.5F, 0.33F)
         .add("slime_duration_explode", 330.0F, 40.0F)
         .add("slime_duration_impact", 130.0F, 20.0F)
         .add("slime_power", 0.0F, 0.4F)
         .add("cooldown", 17.0F, -2.0F)
         .add("reload", 50.0F, -13.0F)
         .add("shots", 5.0F)
         .add("reus_ammo_save_chance", 0.2F)
         .add("max_charge", 40.0F)
         .add("charge_to_shots", 0.1F)
         .add("velocity", 2.0F)
         .add("inaccuracy", 10.0F, -2.0F);
      newparam(ItemsRegister.CHAINMACEIRON)
         .lvl("damage", 5.0F, -8.792019E7F)
         .add("knockback", 0.2F, 0.06F)
         .add("cooldown", 13.0F, -2.0F)
         .add("velocity", 0.8F)
         .add("fire", 0.0F, 3.0F)
         .add("burning_mace_fire", 4.0F);
      newparam(ItemsRegister.CHAINMACEDIAMOND)
         .lvl("damage", 6.0F, -8.792019E7F)
         .add("knockback", 0.22F, 0.06F)
         .add("cooldown", 13.0F, -2.0F)
         .add("velocity", 0.8F)
         .add("fire", 0.0F, 3.0F)
         .add("burning_mace_fire", 4.0F);
      newparam(ItemsRegister.WHIP)
         .lvl("damage", 5.4500003F, -8.792019E7F)
         .add("knockback", 0.0F, 0.5F)
         .add("fire", 0.0F, 3.0F)
         .add("length", 5.0F, 0.5F)
         .add("size", 0.25F, 0.1F)
         .add("end_size", 0.5F, 0.2F)
         .add("splash_radius", 2.5F, 0.4F)
         .add("splash_knockback", 0.4F, 0.5F)
         .lvl("splash_damage", 5.4500003F, -8.792019E7F)
         .add("cooldown", 24.0F, -2.0F)
         .add("max_charge", 120.0F, -70.0F)
         .add("potion_time", 40.0F, 10.0F)
         .add("potion_power", 4.0F)
         .add("sound_pitch_swosh", 0.9F)
         .add("sound_pitch_clap", 0.9F);
      newparam(ItemsRegister.SPELLHAMMER)
         .lvl("damage", 8.0F, -8.792019E7F)
         .add("knockback", 0.4F, 0.3F)
         .add("length", 5.0F, 0.4F)
         .add("size", 0.5F, 0.1F)
         .add("end_size", 1.0F, 0.3F)
         .add("cooldown", 17.0F, -1.0F);
      newparam("tools_diamond")
         .lvl("damage_drill", 2.25F, -8.792019E7F)
         .add("knockback_drill", 0.1F, 0.05F)
         .lvl("damage_chainsaw", 2.75F, -8.792019E7F)
         .add("knockback_chainsaw", 0.1F, 0.05F)
         .lvl("damage_laser", 1.25F, -8.792019E7F)
         .add("knockback_laser", 0.0F, 0.05F)
         .add("length", 4.0F, 0.33F)
         .add("length_laser", 10.0F, 1.0F)
         .add("reload", 45.0F, -7.0F);

      for (int type = 0; type < 8; type++) {
         float inaccuracy = 0.0F;
         float velocity = 0.0F;
         float manacost = 0.0F;
         float sdamage = 0.0F;
         float knockback = 0.0F;
         int livetime = 0;
         int cooldown = 0;
         float red = 0.0F;
         float green = 0.0F;
         float blue = 0.0F;
         switch (type) {
            case 0:
               inaccuracy = 1.0F;
               velocity = 0.7F;
               manacost = 2.3F;
               sdamage = 8.0F;
               knockback = 1.1F;
               livetime = 50;
               red = 0.8F;
               green = 0.9F;
               blue = 1.0F;
               cooldown = 23;
               break;
            case 1:
               inaccuracy = 2.0F;
               velocity = 0.55F;
               manacost = 2.2F;
               sdamage = 7.5F;
               knockback = 1.0F;
               livetime = 40;
               red = 0.9F;
               green = 0.05F;
               blue = 0.1F;
               cooldown = 24;
               break;
            case 2:
               inaccuracy = 2.2F;
               velocity = 0.55F;
               manacost = 1.9F;
               sdamage = 6.5F;
               knockback = 1.0F;
               livetime = 45;
               red = 0.05F;
               green = 0.05F;
               blue = 0.9F;
               cooldown = 21;
               break;
            case 3:
               inaccuracy = 3.0F;
               velocity = 0.45F;
               manacost = 2.1F;
               sdamage = 8.0F;
               knockback = 1.15F;
               livetime = 35;
               red = 0.05F;
               green = 0.9F;
               blue = 0.2F;
               cooldown = 22;
               break;
            case 4:
               inaccuracy = 2.0F;
               velocity = 0.7F;
               manacost = 2.2F;
               sdamage = 7.0F;
               knockback = 1.0F;
               livetime = 43;
               red = 0.9F;
               green = 0.85F;
               blue = 0.3F;
               cooldown = 21;
               break;
            case 5:
               inaccuracy = 3.0F;
               velocity = 0.4F;
               manacost = 1.7F;
               sdamage = 6.0F;
               knockback = 0.8F;
               livetime = 35;
               red = 0.7F;
               green = 0.05F;
               blue = 0.8F;
               cooldown = 18;
               break;
            case 6:
               inaccuracy = 2.5F;
               velocity = 0.45F;
               manacost = 1.8F;
               sdamage = 6.0F;
               knockback = 0.9F;
               livetime = 35;
               red = 0.9F;
               green = 0.5F;
               blue = 0.65F;
               cooldown = 19;
               break;
            case 7:
               inaccuracy = 1.5F;
               velocity = 0.7F;
               manacost = 1.6F;
               sdamage = 5.5F;
               knockback = 0.8F;
               livetime = 48;
               red = 0.9F;
               green = 0.91F;
               blue = 0.85F;
               cooldown = 20;
         }

         newparam("gem_staff_" + type)
            .lvl("damage", sdamage, -8.792019E7F)
            .add("knockback", knockback, 0.25F)
            .add("cooldown", cooldown - 5, -5.0F)
            .add("manacost", manacost * 0.7F, -0.3125F)
            .add("red", red)
            .add("green", green)
            .add("blue", blue)
            .add("livetime", livetime, 10.0F)
            .add("velocity", velocity * 1.4F)
            .add("inaccuracy", inaccuracy, -inaccuracy / 3.5F);
      }

      levelRate += 0.2F * raise;
      newparam(ItemsRegister.MAGICBOOMERANG)
         .lvl("damage", 10.08F, -8.792019E7F)
         .add("knockback", 0.0F, 0.4F)
         .add("damage_radius", 1.2F, 0.35F)
         .add("cooldown", 10.0F, -3.0F)
         .add("manacost", 2.5F, -0.37F)
         .add("velocity", 0.9F, 0.45F)
         .add("inaccuracy", 2.5F, -0.8F)
         .add("ticks_flying", 15.0F, 2.0F)
         .add("return_speed", 1.0F, 0.5F);
      newparam(ItemsRegister.LASERSNIPER)
         .lvl("damage", 30.0F, -8.792019E7F)
         .add("knockback", 0.0F, 0.3F)
         .add("damage_radius", 0.01F, 0.15F)
         .add("cooldown", 40.0F, -4.0F)
         .add("reload", 70.0F, -11.0F)
         .add("distance", 100.0F, 8.0F)
         .add("clipsize", 5.0F, 1.0F);
      newparam(ItemsRegister.LASERRIFLE)
         .lvl("damage", 5.5F, -8.792019E7F)
         .add("knockback", 0.0F, 0.3F)
         .add("damage_radius", 0.05F, 0.15F)
         .add("cooldown", 8.0F, -2.0F)
         .add("reload", 50.0F, -8.0F)
         .add("cooldown_small", 4.0F, -0.4F)
         .add("shoots", 3.0F, 1.0F)
         .add("distance", 25.0F, 3.0F)
         .add("inaccuracy", 3.0F, -1.0F)
         .add("clipsize", 35.0F, 4.0F);
      newparam(ItemsRegister.LASERPISTOL)
         .lvl("damage", 0.86F, -8.792019E7F)
         .add("knockback", 0.0F, 0.1F)
         .add("damage_radius", 0.05F, 0.15F)
         .add("rapid_multiplier", 0.2F)
         .add("cooldown", 0.0F, 0.0F)
         .add("reload", 40.0F, -7.0F)
         .add("distance", 15.0F, 3.0F)
         .add("clipsize", 500.0F, 60.0F);
      levelRate += 0.35F * raise;
      newparam(ItemsRegister.NETHERGRINDER)
         .lvl("damage", 3.36F, -8.792019E7F)
         .add("knockback", 0.7F, 0.2F)
         .lvl("fire_bonus", 2.0F)
         .add("bullet_damage", 1.0F)
         .add("bullet_knockback", 1.0F)
         .add("cooldown", 4.0F, -1.0F)
         .add("reload", 60.0F, -15.0F)
         .add("velocity", 3.1F)
         .add("inaccuracy", 2.0F, -0.6F)
         .add("livetime", 20.0F, 4.0F);
      newparam(ItemsRegister.CINDERBOW)
         .lvl("damage", 1.64F, -8.792019E7F)
         .add("knockback", 0.0F, 0.5F)
         .lvl("fullcharged_damage", 3.28F, -8.792019E7F)
         .add("fullcharged_knockback", 2.0F, 0.5F)
         .add("manacost", 0.4F, -0.0625F)
         .add("velocity", 3.2F)
         .add("inaccuracy", 1.4F, -0.4F)
         .add("max_pull_time", 55.0F)
         .add("min_pull_time", 3.0F);
      newparam(ItemsRegister.STAFFOFWITHERDRY)
         .lvl("damage", 0.88F, -8.792019E7F)
         .add("knockback", 0.25F, 0.1F)
         .add("tick_damage_reduction", 0.02173F)
         .add("fire", 4.0F, 2.0F)
         .add("incinerate_chance", 0.85F)
         .add("manacost", 0.2F, -0.035F)
         .add("maxfire_time", 45.0F, -5.0F)
         .add("charge_consume_chance", 1.0F, -0.22F)
         .add("max_charge", 6.0F, 1.0F)
         .add("mob_health_for_charge", 20.0F)
         .add("cooldown", 20.0F, -4.0F)
         .add("velocity", 1.0F)
         .add("inaccuracy", 4.0F, -1.0F)
         .add("livetime", 20.0F, 3.0F);
      newparam(ItemsRegister.VIOLENCE)
         .lvl("damage", 2.34F, -8.792019E7F)
         .add("knockback", 0.2F, 0.1F)
         .add("damage_radius", 2.2F, 0.2F)
         .add("manacost", 3.0F, -0.4F)
         .add("mana_add", 1.5F, 0.25F)
         .add("hit_radius", 9.0F, 1.6F)
         .lvl("hit_damage", 1.56F, 0.78F)
         .add("hit_knockback", 2.0F, 0.5F)
         .add("hit_potion_power_mult", 2.0F)
         .add("hit_potion_time_add", 40.0F, 10.0F)
         .add("hit_potion_time_max", 200.0F, 20.0F)
         .add("hit_potion_power_max", 2.0F, 0.5F)
         .add("potion_time", 160.0F, 10.0F)
         .add("potion_power", 0.0F)
         .add("cooldown", 16.0F, -2.0F)
         .add("hit_cooldown", 100.0F)
         .add("shots", 4.0F)
         .add("shots_random_bonus", 3.0F, 1.0F)
         .add("velocity", 1.0F)
         .add("range", 16.0F, 1.5F)
         .add("area", 2.0F, -0.35F);
      newparam(ItemsRegister.LAVADROPPER)
         .lvl("damage", 12.150001F, -8.792019E7F)
         .add("knockback", 1.5F, 0.5F)
         .add("fire", 9.0F, 2.0F)
         .add("manacost", 2.0F, -0.4F)
         .add("cooldown", 35.0F, -8.0F)
         .add("velocity", 1.1F)
         .add("inaccuracy", 3.5F, -1.0F);
      newparam(ItemsRegister.FIREBALLSTAFF)
         .lvl("damage", 19.0F, -8.792019E7F)
         .add("knockback", 1.5F, 0.43F)
         .add("friendlyfire", 0.5F)
         .add("damage_radius", 5.0F, 0.75F)
         .add("fire", 4.0F, 2.0F)
         .add("special_explosion_size", 2.0F)
         .add("manacost", 20.0F, -2.5F)
         .add("cooldown", 220.0F, -20.0F)
         .add("velocity", 1.0F)
         .add("inaccuracy", 2.8F, -0.8F);
      newparam(ItemsRegister.INFERNALBLADE)
         .lvl("damage", 5.46F, -8.792019E7F)
         .add("knockback", 0.0F, 0.5F)
         .add("fire", 4.0F, 4.0F)
         .add("damage_radius", 2.0F, 0.4F)
         .lvl("damage_explode_per_health", 0.39F, 0.038999997F)
         .lvl("damage_explode_max", 30.0F)
         .add("knockback_explode", 1.0F, 0.25F)
         .add("length", 2.5F, 0.3F)
         .add("size", 0.55F, 0.15F)
         .add("end_size", 0.7F, 0.15F)
         .add("cooldown", 14.0F, -2.0F);
      newparam(ItemsRegister.MOLTENGREATAXE)
         .lvl("damage", 9.75F, -8.792019E7F)
         .add("knockback", 0.4F, 0.5F)
         .add("fire", 0.0F, 4.0F)
         .add("length", 4.5F, 0.3F)
         .add("size", 0.7F, 0.1F)
         .add("end_size", 1.5F, 0.35F)
         .add("potion_time", 200.0F, 50.0F)
         .add("potion_consume", 20.0F, -4.0F)
         .add("cooldown", 30.0F, -4.0F);
      newparam(ItemsRegister.HELLMARK)
         .lvl("damage", 4.0F, -8.792019E7F)
         .add("knockback", 0.6F, 0.1F)
         .add("self_knockback", 0.35F)
         .add("fire", 0.0F, 3.0F)
         .add("length", 2.2F, 0.2F)
         .add("size", 1.35F, 0.2F)
         .add("end_size", 1.35F, 0.25F)
         .add("shield_angle", 80.0F, 6.0F)
         .lvl("damage_reduce", 11.0F, 1.0F)
         .add("max_hits", 7.0F)
         .lvl("fire_damage_bonus", 4.0F)
         .lvl("heart_health", 1.0F)
         .add("cooldown", 60.0F, -9.0F);
      newparam(ItemsRegister.MAULER)
         .lvl("damage", 4.6200004F, -8.792019E7F)
         .add("knockback", 0.0F, 0.5F)
         .add("fire", 0.0F, 3.0F)
         .add("length", 5.5F, 0.5F)
         .add("size", 0.4F, 0.1F)
         .add("end_size", 0.65F, 0.2F)
         .add("splash_radius", 2.5F, 0.4F)
         .add("splash_knockback", 0.4F, 0.5F)
         .lvl("splash_damage", 4.6200004F, -8.792019E7F)
         .add("cooldown", 27.0F, -2.0F)
         .add("max_charge", 140.0F)
         .add("special_explosion_size", 2.5F)
         .add("potion_time", 130.0F, 20.0F)
         .add("potion_power", 0.0F)
         .add("sound_pitch_swosh", 0.9F)
         .add("sound_pitch_clap", 0.77F);
      newparam(ItemsRegister.ROTTENSHIELD)
         .lvl("damage", 4.0F, -8.792019E7F)
         .add("knockback", 0.45F, 0.1F)
         .add("self_knockback", 0.5F)
         .add("shield_angle", 70.0F, 6.0F)
         .lvl("damage_reduce", 8.0F, 1.0F)
         .add("max_hits", 3.0F)
         .lvl("heart_health", 1.0F)
         .add("exhaustion_on_use", 1.4F, 3.0F)
         .add("exhaustion_on_block", 0.5F, 0.5F)
         .add("starved_cooldown_multiplier", 0.5F)
         .add("food_level_to_starve", 1.0F, 2.0F)
         .add("cooldown", 43.0F, -6.0F)
         .add("eat_chance", 0.55F, 0.22F);
      newparam(ItemsRegister.CHAINMACEMOLTEN)
         .lvl("damage", 6.44F, -8.792019E7F)
         .add("knockback", 0.22F, 0.06F)
         .add("cooldown", 13.0F, -2.0F)
         .add("velocity", 0.8F)
         .add("fire", 7.0F, 3.0F)
         .add("burning_mace_fire", 7.0F);
      levelRate += 0.35F * raise;
      newparam(ItemsRegister.ENDERPROTECTOR)
         .lvl("damage", 5.3999996F, -8.792019E7F)
         .add("knockback", 1.0F, 0.35F)
         .add("cooldown", 15.0F, -4.0F)
         .add("reload", 47.0F, -10.0F)
         .add("velocity", 1.3F)
         .add("inaccuracy", 4.0F, -1.3F)
         .add("distance", 32.0F, 4.0F)
         .add("raytrace_size", 0.8F, 1.4F);
      newparam(ItemsRegister.DRAGONSHELL)
         .add("knockback", 0.5F, 0.1F)
         .add("self_knockback", 0.5F)
         .add("teleport_radius", 3.0F, 0.4F)
         .add("teleport_distance", 2.2F)
         .add("mob_teleport_distance", 8.0F)
         .add("shield_angle", 60.0F, 4.0F)
         .lvl("damage_reduce", 10.0F, 1.0F)
         .add("max_hits", 7.0F)
         .add("cooldown", 45.0F, -7.0F)
         .add("projectile_spend_hit_chance", 1.0F, -0.3F);
      newparam(ItemsRegister.DRAGONTAIL)
         .lvl("damage", 6.96F, -8.792019E7F)
         .add("knockback", 0.1F, 0.5F)
         .add("fire", 0.0F, 3.0F)
         .add("length", 4.2F, 0.5F)
         .add("size", 0.3F, 0.1F)
         .add("cooldown", 15.0F, -3.0F)
         .potion("potion", 1, 0, 0, 0.5F, false)
         .add("cloud_chance", 0.23F, 0.13F)
         .add("cloud_duration", 600.0F)
         .add("cloud_radius", 2.5F, 0.325F)
         .add("max_charge", 50.0F);
      newparam(ItemsRegister.VACUUMGUN)
         .lvl("damage", 8.8F, -8.792019E7F)
         .add("knockback", 0.5F, 0.33F)
         .add("damage_radius", 1.0F, 0.3F)
         .add("attract_knockback", -0.3F, -0.136F)
         .add("attract_damage_radius", 4.3F, 0.5F)
         .add("cooldown", 23.0F, -6.0F)
         .add("reload", 50.0F, -11.0F)
         .add("velocity", 1.4F)
         .add("inaccuracy", 3.3F, -1.0F)
         .add("livetime", 35.0F);
      levelRate += 0.25F * raise;
      newparam(ItemsRegister.ICESWORD)
         .lvl("damage", 4.76F, -8.792019E7F)
         .add("knockback", 0.0F, 0.7F)
         .add("freeze_chance", 0.2F)
         .add("length", 3.0F, 0.3F)
         .add("size", 0.5F, 0.1F)
         .add("end_size", 0.5F, 0.1F)
         .add("cooldown", 13.0F, -2.0F)
         .add("special_shot_chance", 0.25F)
         .add("special_shot_radius", 4.0F, 0.4F)
         .add("potion_time_add", 25.0F, 10.0F)
         .add("potion_power_add", 2.0F, 0.35F)
         .add("potion_time_max", 100.0F, 25.0F)
         .add("potion_power_max", 7.0F, 0.0F);
      newparam(ItemsRegister.SNOWFLAKESHUR)
         .lvl("damage", 5.3900003F)
         .add("knockback", 1.5F)
         .add("velocity", 1.8F)
         .add("inaccuracy", 2.2F)
         .add("cooldown", 10.0F);
      newparam(ItemsRegister.SNOWBALLCANNON)
         .lvl("damage", 2.96F, -8.792019E7F)
         .add("knockback", 1.0F, 0.33F)
         .add("cooldown", 3.0F, -1.0F)
         .add("cooldown_special", 12.0F, -3.0F)
         .add("reload", 40.0F, -10.0F)
         .add("shots_special", 4.0F)
         .add("velocity", 1.8F)
         .add("inaccuracy", 3.0F, -1.0F)
         .add("inaccuracy_special", 5.0F, -0.5F)
         .add("ammo_consume_chance", 1.0F, -0.1F)
         .add("snow_set_chance", 0.0F, 0.25F);
      levelRate += 0.2F * raise;
      newparam(ItemsRegister.GLACIDEBLADE)
         .lvl("damage", 6.1000004F, -8.792019E7F)
         .add("knockback", 0.3F, 0.5F)
         .add("length", 2.5F, 0.3F)
         .add("size", 0.4F, 0.25F)
         .add("end_size", 2.0F, 0.33F)
         .add("cooldown", 15.0F, -2.0F);
      newparam(ItemsRegister.GOTHICSWORD)
         .lvl("damage", 6.84F, -8.792019E7F)
         .add("knockback", 0.0F, 0.5F)
         .add("length", 3.5F, 0.3F)
         .add("size", 0.35F, 0.1F)
         .add("end_size", 0.45F, 0.1F)
         .add("cooldown", 11.0F, -2.0F);
      newparam(ItemsRegister.CRYOGUN)
         .lvl("damage", 7.63F, -8.792019E7F)
         .add("knockback", 1.0F, 0.6F)
         .add("damage_radius", 1.2F, 0.18F)
         .add("potion_time_add", 50.0F, 10.0F)
         .add("potion_power_add", 1.0F)
         .add("potion_time_max", 160.0F, 10.0F)
         .add("potion_power_max", 14.0F, 1.0F)
         .add("cooldown", 12.0F, -2.0F)
         .add("reload", 70.0F, -16.0F)
         .add("ammo_on_water_reload", 4.0F)
         .add("velocity", 1.0F)
         .add("inaccuracy", 4.0F, -1.0F)
         .add("livetime", 30.0F, 5.0F)
         .add("ammo_consume_chance", 1.01F, -0.2F);
      newparam(ItemsRegister.COOLEDRIFLE)
         .lvl("damage", 2.67F, -8.792019E7F)
         .add("knockback", 1.1F, 0.3F)
         .lvl("damage_shotgun", 2.225F, 0.33F)
         .add("knockback_shotgun", 1.0F, 0.1F)
         .add("bullet_damage", 1.0F)
         .add("bullet_knockback", 1.0F)
         .add("cooldown", 2.5F, -0.5F)
         .add("reload", 20.0F, -4.0F)
         .add("shots", 6.0F)
         .add("distance", 29.0F, 2.0F)
         .add("inaccuracy", 1.3F, -0.4F)
         .add("velocity", 3.9F)
         .add("inaccuracy_shotgun", 7.0F, -0.8F);
      newparam(ItemsRegister.FIREWORKSLAUN)
         .lvl("damage", 1.96F, -8.792019E7F)
         .add("knockback", 0.5F, 0.15F)
         .add("min_damage_radius", 0.5F, 0.1F)
         .add("max_firework_size", 30.0F, 4.0F)
         .lvl("damage_dragon", 9.8F, -8.792019E7F)
         .add("knockback_dragon", 5.5F, 1.0F)
         .add("dragon_damage_radius", 3.5F, 0.2F)
         .lvl("flying_damage_bonus", 0.0F, 1.96F)
         .add("explode_size", 2.0F, 1.0F)
         .add("cooldown", 5.0F, -1.0F)
         .add("reload", 50.0F, -6.0F)
         .add("cooldown_dragon", 10.0F, -1.0F)
         .add("reload_dragon", 40.0F, -5.0F)
         .add("velocity", 1.2F, 0.125F)
         .add("inaccuracy", 7.0F, -2.0F)
         .add("velocity_dragon", 1.1F)
         .add("inaccuracy_dragon", 0.3F, -0.1F);
      newparam(ItemsRegister.GOTHICBOW)
         .lvl("damage", 1.85F, -8.792019E7F)
         .add("knockback", 0.0F, 0.5F)
         .lvl("shard_damage", 3.7F, -8.792019E7F)
         .add("max_charges", 13.0F, 2.0F)
         .add("charges_to_shoot", 10.0F)
         .add("charges_decay_rate", 5.0F, 4.0F)
         .add("velocity", 3.3F)
         .add("inaccuracy", 0.9F, -0.22F)
         .add("shard_velocity", 2.3F)
         .add("shard_inaccuracy", 3.0F, -1.0F)
         .add("shards_min", 1.0F)
         .add("shards_max", 3.0F)
         .add("max_pull_time", 18.0F)
         .add("min_pull_time", 12.0F);
      newparam(ItemsRegister.ICEBEAM)
         .lvl("damage", 0.366F, -8.792019E7F)
         .add("knockback", 0.2F, 0.1F)
         .add("damage_radius", 0.4F)
         .add("potion_time_add", 5.0F, 1.0F)
         .add("potion_power_depends_on_time", 0.066F)
         .add("potion_time_max", 250.0F, 20.0F)
         .add("potion_power_max", 12.0F, 1.0F)
         .add("manacost", 0.1F, -0.01818F)
         .add("cooldown", 0.0F, 0.0F)
         .add("rapid_multiplier", 0.2F)
         .add("distance", 25.0F, 5.0F);
      newparam(ItemsRegister.WANDOFCOLD)
         .lvl("damage", 3.4F, -8.792019E7F)
         .add("knockback", 1.5F, 0.5F)
         .lvl("wave_damage", 9.35F, -8.792019E7F)
         .add("wave_knockback", 4.0F, 1.0F)
         .add("wave_width", 4.0F, 0.5F)
         .add("max_shots", 3.0F)
         .add("max_charges", 20.0F, -3.0F)
         .add("max_bounces", 4.0F, 5.0F)
         .add("manacost", 1.5F, -0.3F)
         .add("cooldown", 8.0F, -2.0F)
         .add("wave_cooldown", 10.0F)
         .add("potion_time", 600.0F, 65.0F)
         .add("potion_power", 1.0F, 0.4F)
         .add("velocity", 0.5F)
         .add("inaccuracy", 3.0F, -1.0F)
         .add("wave_velocity", 1.2F)
         .add("wave_inaccuracy", 1.0F, -0.33F);
      newparam(ItemsRegister.ICEBREAKER)
         .lvl("damage", 6.2999997F, -8.792019E7F)
         .add("knockback", 0.22F, 0.06F)
         .add("cooldown", 10.0F, -2.0F)
         .add("velocity", 0.95F)
         .lvl("icebreak_damage", 3.5F, -8.792019E7F)
         .add("fire", 0.0F, 1.0F)
         .add("burning_mace_fire", 2.0F);
      newparam(ItemsRegister.WINTERBREATH)
         .lvl("damage", 4.0F, -8.792019E7F)
         .add("knockback", 0.5F, 0.1F)
         .add("self_knockback", 0.4F)
         .add("damage_radius", 7.0F, 0.7F)
         .add("length", 2.2F, 0.2F)
         .add("size", 1.35F, 0.2F)
         .add("end_size", 1.35F, 0.25F)
         .add("shield_angle", 100.0F, 7.0F)
         .lvl("damage_reduce", 12.0F, 1.0F)
         .add("max_hits", 4.0F)
         .add("cooldown", 65.0F, -10.0F)
         .add("freezing_time_add", 60.0F, 10.0F)
         .add("freezing_highest_power_add", 4.0F, 0.0F)
         .add("freezing_time_max", 140.0F, 15.0F)
         .add("freezing_power_max", 15.0F, 1.0F)
         .add("slowness_time_add", 100.0F, 15.0F)
         .add("slowness_power_add", 3.0F, 0.0F)
         .add("slowness_time_max", 250.0F, 25.0F)
         .add("frostburn_time_add", 140.0F, 20.0F)
         .add("frostburn_power_add", 1.0F, 0.0F)
         .add("frostburn_time_max", 550.0F, 25.0F)
         .add("winterbreak_knockback", 3.0F, 0.5F)
         .add("winterbreak_snow_need", 8.0F, -2.0F);
      EXlevelFROZEN = levelRate;
      levelRate += 0.15F * raise;
      newparam(ItemsRegister.ICICLEMINIGUN)
         .lvl("damage", 2.1375F, -8.792019E7F)
         .lvl("icebreak_damage", 1.71F, -8.792019E7F)
         .add("knockback", 0.8F, 0.4F)
         .add("min_cooldown", 2.0F)
         .add("max_cooldown", 10.0F)
         .add("attacktime_for_fast", 64.0F, -12.0F)
         .add("reload", 120.0F, -30.0F)
         .add("slowdown", 3.0F, -2.0F)
         .add("velocity", 2.8F)
         .add("inaccuracy", 4.0F, -1.0F)
         .add("livetime", 10.0F, 2.0F);
      newparam(ItemsRegister.XMASSLAUNCHER)
         .lvl("damage", 8.4F, -8.792019E7F)
         .add("knockback", 2.0F, 0.3F)
         .add("damage_radius", 1.2F, 0.16F)
         .add("damage_explode_mult", 0.4F)
         .lvl("ball_damage", 4.2F, -8.792019E7F)
         .add("cooldown", 18.0F, -2.0F)
         .add("reload", 60.0F, -8.0F)
         .add("balls", 8.0F, 1.0F)
         .add("ball_radius", 0.7F, 0.06F)
         .add("alternative_ball_radius", 0.85F, 0.06F)
         .add("ball_knockback", 1.0F, 0.4F)
         .add("velocity", 1.5F)
         .add("inaccuracy", 1.3F, -0.4F);
      levelRate += 0.15F * raise;
      newparam(ItemsRegister.BUZDYGAN)
         .lvl("damage", 6.104F, -8.792019E7F)
         .add("knockback", 0.25F, 0.3F)
         .add("length", 3.0F, 0.5F)
         .add("size", 0.65F, 0.1F)
         .add("end_size", 0.8F, 0.13F)
         .add("cooldown", 17.0F, -2.0F)
         .add("max_charges", 3.0F)
         .add("charged_length", 4.2F, 0.7F)
         .add("charged_size", 1.8F, 0.35F)
         .lvl("charged_damage", 2.289F, -8.792019E7F)
         .add("charged_knockback", 0.5F, 0.2F)
         .add("charged_slowdown", 0.6F)
         .add("charged_hit_delay", 4.0F)
         .add("charge_decrement", -0.2F)
         .add("slowness_time", 40.0F, 14.0F)
         .add("slowness_power", 2.0F)
         .add("brokenarmor_time", 1000.0F, 100.0F)
         .add("brokenarmor_power_add", 1.0F)
         .add("brokenarmor_power_max", 8.0F);
      levelRate += 0.55F * raise;
      newparam(ItemsRegister.SUBMACHINE)
         .lvl("damage", 6.0F, -8.792019E7F)
         .add("knockback", 1.3F, 0.4F)
         .lvl("per_heat_damage", 0.00166F)
         .add("bullet_damage", 1.0F)
         .add("bullet_knockback", 1.0F)
         .add("cooldown", 10.0F, -1.0F)
         .add("reload", 20.0F, -5.0F)
         .add("distance", 32.0F, 3.0F)
         .add("inaccuracy", 1.0F, -0.3F)
         .add("heat_per_shoot", 40.0F)
         .add("special_heat_overclock", 0.002F);
      newparam(ItemsRegister.NAILGUN)
         .lvl("damage", 3.8500001F, -8.792019E7F)
         .add("knockback", 1.5F, 0.5F)
         .add("cooldown", 4.0F, -1.0F)
         .add("reload", 75.0F, -20.0F)
         .add("velocity", 3.0F)
         .add("inaccuracy", 2.0F, -0.6F)
         .add("health_to_prick", 15.0F, 13.0F);
      newparam(ItemsRegister.COMPOUNDBOW)
         .lvl("damage", 2.24F, -8.792019E7F)
         .add("knockback", 0.0F, 0.5F)
         .add("velocity", 4.2F)
         .add("inaccuracy", 0.5F, -0.16F)
         .add("max_pull_time", 28.0F)
         .add("min_pull_time", 10.0F);
      newparam(ItemsRegister.SNAKEWHIP)
         .lvl("damage", 4.3395F, -8.792019E7F)
         .add("knockback", 0.0F, 0.5F)
         .add("fire", 0.0F, 2.0F)
         .add("length", 5.0F, 0.5F)
         .add("size", 0.35F, 0.1F)
         .add("end_size", 0.7F, 0.2F)
         .add("splash_radius", 2.75F, 0.4F)
         .add("splash_knockback", 0.5F, 0.5F)
         .lvl("splash_damage", 6.312F, -8.792019E7F)
         .add("cooldown", 22.0F, -2.0F)
         .add("max_charge", 3.0F)
         .add("slime_time", 40.0F, 14.0F)
         .add("slime_power", 3.0F)
         .add("toxin_time_min", 35.0F, 10.0F)
         .add("toxin_time_max", 60.0F, 10.0F)
         .add("toxin_power", 1.0F)
         .add("sound_pitch_swosh", 0.94F)
         .add("sound_pitch_clap", 0.96F);
      newparam(ItemsRegister.SKULLCRASHER)
         .lvl("damage", 7.63F, -8.792019E7F)
         .add("knockback", 0.5F, 0.35F)
         .add("fire", 0.0F, 4.0F)
         .add("length", 4.0F, 0.4F)
         .add("size", 0.4F, 0.1F)
         .add("end_size", 1.0F, 0.4F)
         .add("cooldown", 25.0F, -4.0F)
         .lvl("potion_power", 2.0F, 0.5F)
         .add("hardness_breaks", 8.0F, 1.0F)
         .add("hardness_penetrate", 20.0F, 2.0F)
         .add("blocks_dig", 2.0F, 1.0F);
      newparam(ItemsRegister.ROCKETLAUNCHER)
         .lvl("damage", 3.66F, -8.792019E7F)
         .add("knockback", 1.0F, 0.33F)
         .add("cooldown", 17.0F, -4.0F)
         .add("reload", 45.0F, -10.0F)
         .add("velocity", 1.5F)
         .add("inaccuracy", 4.0F, -1.2F);
      levelRate += 0.25F * raise;
      EXlevelTOXINIUM = levelRate;
      newparam("tools_toxinium")
         .lvl("damage_drill", 2.25F, -8.792019E7F)
         .add("knockback_drill", 0.1F, 0.05F)
         .lvl("damage_chainsaw", 2.75F, -8.792019E7F)
         .add("knockback_chainsaw", 0.1F, 0.05F)
         .lvl("damage_laser", 1.25F, -8.792019E7F)
         .add("knockback_laser", 0.0F, 0.05F)
         .add("length", 4.0F, 0.33F)
         .add("length_laser", 15.0F, 1.5F)
         .add("reload", 45.0F, -7.0F);
      newparam(ItemsRegister.TOXINIUMSHOTGUN)
         .lvl("damage", 4.0F, -8.792019E7F)
         .add("knockback", 0.8F, 0.2F)
         .add("bullet_damage", 1.0F)
         .add("bullet_knockback", 1.0F)
         .add("cooldown", 15.0F, -2.0F)
         .add("reload", 25.0F, -4.0F)
         .add("shots", 6.0F, 1.0F)
         .add("distance", 22.0F, 1.0F)
         .add("inaccuracy", 4.0F, -0.5F)
         .add("potion_time", 40.0F, 10.0F)
         .add("potion_power", 0.0F, 0.0F);
      newparam(ItemsRegister.TOXICNUKECANNON)
         .lvl("damage", 16.33F, -8.792019E7F)
         .add("knockback", 2.2F, 0.43F)
         .add("damage_radius", 5.5F, 0.6F)
         .add("cooldown", 10.0F, -4.0F)
         .add("reload", 30.0F, -5.0F)
         .add("explosion_power", 3.0F)
         .add("friendlyfire", 0.43F)
         .add("shards_count", 15.0F, 15.0F)
         .add("shard_livetime", 150.0F)
         .add("shard_damage_radius", 1.4F, 0.2F)
         .add("toxin_time", 300.0F, 40.0F)
         .add("toxin_power", 0.0F, 0.0F)
         .add("dig_chance", 0.5F)
         .add("velocity", 1.2F)
         .add("inaccuracy", 3.0F, -0.8F);
      newparam(ItemsRegister.ACIDFIRE)
         .lvl("damage", 4.16F, -8.792019E7F)
         .add("knockback", 0.5F, 0.3F)
         .add("manacost", 0.6F, -0.117F)
         .add("cooldown", 5.0F, -1.0F)
         .add("velocity", 1.2F)
         .add("inaccuracy", 5.0F, -1.3F)
         .add("potion1_time_add", 80.0F, 20.0F)
         .add("potion1_time_max", 150.0F, 20.0F)
         .add("potion1_power", 1.0F, 0.0F)
         .add("potion2_time_add", 40.0F, 10.0F)
         .add("potion2_time_max", 200.0F, 20.0F)
         .add("potion2_power", 0.0F, 0.0F)
         .add("potion3_time_add", 25.0F, 5.0F)
         .add("potion3_time_max", 100.0F, 20.0F)
         .add("potion3_power", 0.0F, 0.0F);
      newparam(ItemsRegister.TOXINIUMSHIELD)
         .add("knockback", 0.5F, 0.1F)
         .add("self_knockback", 0.3F)
         .add("shield_angle", 110.0F, 6.0F)
         .lvl("damage_reduce", 14.0F, 1.0F)
         .add("max_hits", 6.0F)
         .add("cooldown", 50.0F, -8.0F)
         .potion("toxin", 70, 17, 100, 20, 2, 0.4F, 0, 0, false);
      newparam(ItemsRegister.HADRONBLASTER)
         .lvl("damage", 2.75F, -8.792019E7F)
         .add("knockback", 2.0F, 0.45F)
         .add("damage_radius", 1.4F, 0.2F)
         .lvl("damage_laser", 3.0F, -8.792019E7F)
         .add("knockback_laser", 0.2F, 0.16F)
         .add("damage_radius_laser", 2.0F, 0.4F)
         .add("cooldown", 7.0F, -2.0F)
         .add("capture_time", 40.0F)
         .add("distance", 14.0F, 3.0F)
         .add("hadron_chance", 0.1F, 0.05F)
         .add("hadron_chance_mob", 0.25F, 0.25F)
         .add("velocity", 2.5F)
         .add("inaccuracy", 0.0F, 0.0F)
         .add("hadron_price", 20.0F, 0.0F)
         .add("hadron_points_max", 400.0F, 40.0F)
         .add("hadrons_to_laser", 200.0F)
         .add("rf_to_shoot", 800.0F, -100.0F)
         .add("rf_to_lazer", 40.0F, -5.0F);
      newparam(ItemsRegister.SNAPBALL)
         .lvl("damage", 8.33F, -8.792019E7F)
         .add("knockback", 3.0F, 0.66F)
         .lvl("damage_explode", 8.33F, -8.792019E7F)
         .add("knockback_explode", 1.5F, 0.66F)
         .add("damage_radius", 1.75F, 0.3F)
         .lvl("damage_powered", 2.45F, -8.792019E7F)
         .add("damage_radius_powered", 4.5F, 0.7F)
         .add("cooldown", 15.0F, -7.0F)
         .add("reload", 50.0F, -10.0F)
         .add("rf_to_reload", 1000.0F, -150.0F)
         .add("charge_to_powered", 6.0F)
         .add("reuse_chance", 0.0F, 0.25F)
         .add("minimal_special_livetime", 60.0F)
         .add("velocity", 2.5F)
         .add("velocity_charged", 0.6F)
         .add("velocity_grenade", 1.0F)
         .add("inaccuracy", 2.0F, -0.66F)
         .add("bounce_find_radius", 8.0F, 1.0F);
      levelRate += 0.55F * raise;
      newparam(ItemsRegister.MAGICROCKET)
         .lvl("damage", 6.2999997F, -8.792019E7F)
         .add("knockback", 1.5F, 0.5F)
         .add("find_radius", 4.0F)
         .add("manacost", 1.6F, -0.27F)
         .add("friction", 0.93F, -0.025F)
         .add("acceleration", 0.17F, 0.06F)
         .add("cooldown", 7.0F, -1.0F)
         .add("distance", 35.0F, 5.0F)
         .add("livetime", 50.0F, 10.0F)
         .add("velocity", 0.95F)
         .add("inaccuracy", 2.0F, -0.6F);
      newparam(ItemsRegister.CRYSTALSTAR)
         .lvl("damage", 2.075F, -8.792019E7F)
         .add("knockback", 0.4F, 0.2F)
         .lvl("damage_powered", 8.3F, -8.792019E7F)
         .add("manacost", 2.0F, -0.33F)
         .add("xp_decrease", 1.0F)
         .add("cooldown", 21.0F, -2.0F)
         .add("cooldown_powered", 7.0F, -1.0F)
         .add("shots", 8.0F)
         .add("find_radius", 6.5F, 0.5F)
         .add("explode_find_radius", 8.0F)
         .add("homing_power", 0.35F)
         .add("homing_power_random", 0.1F)
         .add("homing_power_max", 0.55F)
         .add("velocity", 2.5F)
         .add("inaccuracy", 2.0F, -0.666F)
         .add("health_to_prick", 20.0F)
         .add("health_to_prick_powered", 26.0F);
      newparam(ItemsRegister.ICHSHOWER)
         .lvl("damage", 1.84F, -8.792019E7F)
         .add("knockback", 0.5F, 0.2F)
         .add("manacost", 0.7F, -0.105F)
         .add("cooldown", 3.0F, -0.5F)
         .potion("ichor", 100, 0, 40, 0.0F, false)
         .add("velocity", 3.5F)
         .add("inaccuracy", 0.1F, -0.03F);
      newparam(ItemsRegister.STINGER)
         .lvl("damage", 2.4F, -8.792019E7F)
         .add("knockback", 1.0F, 0.37F)
         .add("damage_radius", 1.4F, 0.2F)
         .lvl("damage_shard", 1.8000001F, -8.792019E7F)
         .add("knockback_shard", 0.8F, 0.33F)
         .add("damage_radius_shard", 1.4F, 0.25F)
         .add("cooldown", 7.0F, -1.0F)
         .add("reload", 25.0F, -3.0F)
         .add("shards", 3.0F, 3.0F)
         .add("velocity", 1.5F)
         .add("inaccuracy", 2.0F, -0.6F);
      newparam(ItemsRegister.WHISPERSBLADE)
         .lvl("damage", 6.16F, -8.792019E7F)
         .add("knockback", 1.5F, 0.4F)
         .lvl("damage_edge", 4.62F, -8.792019E7F)
         .add("knockback_edge", 1.4F, 0.33F)
         .lvl("damage_powered", 23.76F, 0.88F)
         .add("knockback_powered", 1.4F, 0.33F)
         .add("cutter_size", 2.6F, 0.35F)
         .add("cutter_size_powered", 4.6F, 0.28F)
         .add("bounces", 5.0F, 5.0F)
         .add("manacost", 1.3F, -0.18F)
         .add("xp_decrease", 1.0F)
         .add("cooldown", 17.0F, -2.0F)
         .add("cooldown_powered", 22.0F, -3.0F)
         .add("velocity", 1.3F)
         .add("inaccuracy", 3.0F, -1.0F)
         .add("velocity_powered", 1.5F);
      newparam(ItemsRegister.SCEPTEROFSANDS)
         .lvl("damage", 0.984F, -8.792019E7F)
         .add("knockback", 0.25F, 0.12F)
         .add("knockback_trace", 0.15F, 0.05F)
         .add("knockback_to_blocks", 1.2F, 0.08F)
         .add("manacost", 0.22F, -0.033F)
         .add("hardness_breaks", 10.0F)
         .add("cooldown", 0.0F)
         .add("rapid_multiplier", 0.2F)
         .add("velocity", 1.6F)
         .add("inaccuracy", 9.4F, -1.0F)
         .add("inaccuracy_trace", 15.0F, -1.5F)
         .add("distance", 10.5F, 1.5F)
         .add("trace_count", 4.0F)
         .add("livetime", 13.0F, 2.0F)
         .add("max_crystal_charge", 80.0F)
         .add("fallingblock_chance", 0.2F, 0.125F);
      EXlevelDUNGEON_CHEST = levelRate;
      newparam("tools_adamantium")
         .lvl("damage_drill", 2.25F, -8.792019E7F)
         .add("knockback_drill", 0.1F, 0.05F)
         .lvl("damage_chainsaw", 2.75F, -8.792019E7F)
         .add("knockback_chainsaw", 0.1F, 0.05F)
         .lvl("damage_laser", 1.25F, -8.792019E7F)
         .add("knockback_laser", 0.0F, 0.05F)
         .add("length", 4.0F, 0.33F)
         .add("length_laser", 18.0F, 1.5F)
         .add("reload", 40.0F, -7.0F);
      newparam(ItemsRegister.ADAMANTIUMLONGSWORD)
         .lvl("damage", 10.5F, -8.792019E7F)
         .add("knockback", 0.4F, 0.45F)
         .add("length", 3.8F, 0.4F)
         .add("size", 0.4F, 0.1F)
         .add("end_size", 0.6F, 0.15F)
         .add("cooldown", 20.0F, -4.0F);
      newparam(ItemsRegister.ADAMANTIUMKNIFE)
         .lvl("damage", 7.0F, -8.792019E7F)
         .add("knockback", -0.15F, 0.2F)
         .lvl("critical_damage", 5.0F)
         .add("length", 2.7F, 0.4F)
         .add("cooldown", 9.0F, -1.0F);
      newparam(ItemsRegister.ADAMANTIUMBATTLEAXE)
         .lvl("damage", 15.0F, -8.792019E7F)
         .add("knockback", 0.6F, 0.6F)
         .lvl("damage_special", 30.0F, -8.792019E7F)
         .add("length", 5.0F, 0.5F)
         .add("size", 0.6F, 0.1F)
         .add("end_size", 1.8F, 0.4F)
         .add("cooldown", 40.0F, -5.0F)
         .add("cooldown_special", 80.0F, -10.0F);
      newparam(ItemsRegister.CRYSTALCUTTER)
         .lvl("damage", 3.2400002F, -8.792019E7F)
         .add("knockback", 0.0F, 0.2F)
         .add("cutter_size", 2.5F, 0.6F)
         .add("livetime", 100.0F, 15.0F)
         .add("cutter_thickness", 0.2F)
         .add("cutter_thickness_triple", 0.6F)
         .add("cooldown", 15.0F, -2.0F)
         .add("reload", 70.0F, -10.0F)
         .add("velocity", 0.4F)
         .add("inaccuracy", 2.0F, -0.6F);
      newparam(ItemsRegister.MITHRILBOW)
         .lvl("damage", 2.94F, -8.792019E7F)
         .add("knockback", 0.0F, 0.5F)
         .add("velocity", 3.4F)
         .add("inaccuracy", 0.8F, -0.24F)
         .add("max_pull_time", 20.0F)
         .add("min_pull_time", 4.0F);
      newparam(ItemsRegister.ADAMANTIUMREVOLVER)
         .lvl("damage", 8.4F, -8.792019E7F)
         .add("knockback", 1.3F, 0.4F)
         .add("bullet_damage", 2.0F)
         .add("bullet_knockback", 1.0F)
         .add("cooldown", 10.0F, -3.0F)
         .add("reload", 20.0F, -5.0F)
         .add("distance", 40.0F, 5.0F)
         .add("inaccuracy", 3.0F, -0.25F)
         .add("hearts", 3.0F)
         .lvl("hearts_health", 2.0F)
         .potion("stun", 80, 5, 10, 0.35F, false)
         .potion("stun_players", 40, 5, 0, 0.0F, false);
      newparam(ItemsRegister.ADAMANTIUMMINIGUN)
         .lvl("damage_ranged", 2.816F, -8.792019E7F)
         .add("knockback_ranged", 0.2F, 0.15F)
         .add("bullet_damage", 0.85F)
         .add("bullet_knockback", 0.8F)
         .add("cooldown_melee", 17.0F, -2.0F)
         .add("reload", 80.0F, -10.0F)
         .add("min_shoot_delay", 3.0F, 0.0F)
         .add("max_shoot_delay", 10.0F, 0.0F)
         .add("speed_to_cooldown", -0.125F, 0.0F)
         .lvl("damage", 9.68F, -8.792019E7F)
         .add("knockback", 1.0F, 0.55F)
         .add("length", 3.0F, 0.5F)
         .add("size", 2.5F, 0.5F)
         .add("end_size", 3.5F, 0.5F)
         .add("distance", 46.0F, 5.0F)
         .add("inaccuracy", 3.0F, -0.65F)
         .add("spread_reduction", 0.03F, -0.0065F)
         .add("heat_to_melee", 140.0F)
         .add("reuse_cooling_delay", 2.0F);
      newparam(ItemsRegister.HOLYSHOTGUN)
         .lvl("damage", 3.48F, -8.792019E7F)
         .add("knockback", 0.3F, 0.22F)
         .add("bullet_damage", 0.5F)
         .add("bullet_knockback", 0.33F)
         .add("cooldown", 4.0F, -1.0F)
         .add("reload", 32.0F, -4.0F)
         .add("shots", 7.0F, 1.0F)
         .potion("broken_armor", 1000, 100, 1000, 100, 1, 0.0F, 12, 1, false)
         .add("distance", 14.0F, 1.0F)
         .add("inaccuracy", 3.5F, -0.25F)
         .add("inaccuracy_horizontal", 4.5F, -0.25F);
      levelRate += 0.55F * raise;
      newparam(ItemsRegister.BUBBLEFISH)
         .lvl("damage", 1.1F, -8.792019E7F)
         .add("knockback", 0.3F, 0.12F)
         .add("manacost", 0.1F, -0.02F)
         .add("cooldown", 0.0F, 0.0F)
         .add("reload", 45.0F, -10.0F)
         .add("velocity", 0.56F)
         .add("inaccuracy", 5.8F, -1.2F)
         .add("livetime", 50.0F, 10.0F)
         .add("livetime_random_add", 40.0F)
         .add("shots", 2.0F, 0.4F)
         .potion("slime", 80, 1, 20, 0.25F, false);
      newparam(ItemsRegister.PISTOLFISH)
         .lvl("damage", 6.0F, -8.792019E7F)
         .add("knockback", 1.0F, 0.4F)
         .add("cooldown", 20.0F, -4.0F)
         .add("reload", 50.0F, -8.0F)
         .add("livetime", 60.0F, 7.0F)
         .add("velocity", 2.0F)
         .add("inaccuracy", 3.5F, -1.0F)
         .potion("toxin", 40, 0, 10, 0.25F, true)
         .add("ammo_consume_chance", 1.0F, -0.15F)
         .add("feed_drop_chance", 0.0F, 0.1F)
         .add("special_shoot_chance", 0.0F, 0.15F);
      levelRate += 0.4F * raise;
      newparam("tools_aquatic")
         .lvl("damage_drill", 2.25F, -8.792019E7F)
         .add("knockback_drill", 0.1F, 0.05F)
         .lvl("damage_chainsaw", 2.75F, -8.792019E7F)
         .add("knockback_chainsaw", 0.1F, 0.05F)
         .lvl("damage_laser", 1.25F, -8.792019E7F)
         .add("knockback_laser", 0.1F, 0.05F)
         .add("length", 4.0F, 0.33F)
         .add("length_laser", 14.0F, 1.75F)
         .add("reload", 42.0F, -7.0F);
      newparam(ItemsRegister.CERATARGET)
         .lvl("damage", 4.0F, -8.792019E7F)
         .add("knockback", 1.2F, 0.33F)
         .lvl("damage_explode", 4.8F, -8.792019E7F)
         .add("knockback_explode", 1.0F, 0.4F)
         .add("damage_radius", 1.3F, 0.23F)
         .add("manacost", 10.0F, -1.5F)
         .add("ammo_consume_chance", 1.0F, -0.18F)
         .add("cooldown", 2.0F, 0.0F)
         .add("reload", 120.0F, -20.0F)
         .add("velocity", 1.34F)
         .add("inaccuracy", 4.0F, -1.0F)
         .add("displace_angle", 3.0F, -0.7F)
         .add("friction", 0.8F)
         .add("follow_power_entity", 0.4F)
         .add("follow_power_point", 0.3F);
      newparam(ItemsRegister.BLOWHOLE)
         .lvl("damage", 6.4F, -8.792019E7F)
         .add("knockback", 1.5F, 0.43F)
         .add("bubble_size_multiplier", 0.1F, 0.012F)
         .lvl("damage_plasma", 4.8F, -8.792019E7F)
         .add("knockback_plasma", 0.4F, 0.1F)
         .add("cooldown", 5.0F, -1.0F)
         .add("reload", 55.0F, -8.0F)
         .add("ammo_add", -5.0F, 1.0F)
         .add("ammo_add_charged_multiplier", 1.0F, -0.1F)
         .add("charge_add", 1.0F)
         .add("max_charge", 30.0F)
         .add("velocity", 1.4F, 0.3F)
         .add("inaccuracy", 2.5F, -0.8F)
         .add("velocity_charge_multiplier", -0.0125F, -0.0225F)
         .add("harvest_lvl", 5.0F)
         .add("hardness_breaks", 20.0F);
      newparam(ItemsRegister.CORALRIFLE)
         .lvl("damage", 2.22F, -8.792019E7F)
         .add("knockback", 1.1F, 0.4F)
         .add("bullet_damage", 1.0F)
         .add("bullet_knockback", 1.0F)
         .add("cooldown", 2.0F, -0.3F)
         .add("reload", 45.0F, -9.0F)
         .add("livetime", 20.0F, 3.0F)
         .add("polyps", 4.0F)
         .lvl("polyps_damage", 3.7F, -8.792019E7F)
         .add("velocity", 3.5F)
         .add("inaccuracy", 3.2F, -1.0F);
      newparam(ItemsRegister.SEAEFFLORESCE)
         .lvl("damage", 1.72F, -8.792019E7F)
         .add("knockback", -0.6F, -0.25F)
         .add("damage_radius", 2.5F, 0.33F)
         .add("manacost", 8.0F, -1.2F)
         .add("cooldown", 35.0F, -5.0F)
         .add("velocity", 0.45F)
         .add("inaccuracy", 4.0F, -1.2F)
         .add("attraction", 1.0F)
         .add("livetime", 400.0F);
      newparam(ItemsRegister.STINGINGCELL)
         .lvl("damage", 7.0699997F, -8.792019E7F)
         .lvl("damage_growed", 14.139999F, -8.792019E7F)
         .add("knockback", 2.0F, 0.5F)
         .add("damage_radius", 2.5F, 0.33F)
         .add("manacost", 2.0F, -0.3125F)
         .add("cooldown", 10.0F, -2.0F)
         .add("velocity", 0.35F)
         .add("inaccuracy", 7.0F, -2.0F)
         .add("shots", 3.0F)
         .add("livetime", 400.0F)
         .add("special_grow_time", 60.0F)
         .add("move_speed", 0.01F);
      newparam(ItemsRegister.HYDRAULICSHOTGUN)
         .lvl("damage", 2.25F, -8.792019E7F)
         .add("knockback", 0.22F, 0.185F)
         .add("bullet_damage", 0.5F)
         .add("bullet_knockback", 0.26F)
         .add("cooldown", 40.0F, -5.0F)
         .add("reload", 65.0F, -10.0F)
         .add("cooldown_small", 3.0F)
         .add("shots", 7.0F, 1.0F)
         .add("burst", 3.0F)
         .add("distance", 19.0F, 2.0F)
         .add("inaccuracy", 2.75F, -0.27F)
         .add("inaccuracy_horizontal", 2.15F, -0.22F);
      newparam(ItemsRegister.CARAPACE)
         .add("knockback", 0.55F, 0.1F)
         .add("self_knockback", 0.4F)
         .add("shield_angle", 90.0F, 5.0F)
         .lvl("damage_reduce", 12.0F, 1.0F)
         .add("max_hits", 8.0F)
         .add("cooldown", 37.0F, -6.0F)
         .add("bonus_begin_time", 60.0F, -40.0F)
         .add("bonus_end_time", 100.0F, 60.0F)
         .lvl("bonus_damage_reduce", 5.0F, 3.0F);
      newparam(ItemsRegister.ECHINUS)
         .lvl("damage", 5.04F, -8.792019E7F)
         .add("knockback", 0.2F, 0.06F)
         .add("cooldown", 12.0F, -2.0F)
         .add("velocity", 1.15F)
         .add("fire", 0.0F, 1.0F)
         .add("burning_mace_fire", 2.0F)
         .lvl("bomb_damage", 5.04F, -8.792019E7F)
         .add("charges_per_kill", 2.0F, 1.0F)
         .add("charges_effects_bonus", 2.0F)
         .add("charges_max", 40.0F, 30.0F);
      EXlevelSEA_MIDDLE = levelRate;
      levelRate += 0.2F * raise;
      newparam(ItemsRegister.STAFFOFTHEAZUREORE)
         .lvl("damage", 1.84F, -8.792019E7F)
         .add("knockback", 0.35F, 0.25F)
         .add("manacost", 1.0F, -0.18F)
         .add("xp_decrease", 1.0F)
         .add("cooldown", 6.0F, -1.0F)
         .add("cooldown_powered", 4.0F, 0.0F)
         .add("velocity", 1.2F)
         .add("inaccuracy", 4.5F, -0.8F)
         .add("random_motion", 0.0666F, -0.015F)
         .add("random_motion_powered", 0.03F, -0.01F)
         .add("find_radius", 5.5F, 0.3F)
         .add("homing_power", 0.32F)
         .add("homing_power_random", 0.1F)
         .add("homing_power_max", 0.55F)
         .add("shots", 2.0F)
         .add("shots_powered", 5.0F);
      newparam(ItemsRegister.AQUATICBOW)
         .lvl("damage", 1.4F, -8.792019E7F)
         .add("knockback", 0.0F, 0.5F)
         .add("velocity", 4.5F)
         .add("inaccuracy", 1.85F, -0.5F)
         .add("max_pull_time", 25.0F)
         .add("min_pull_time", 6.0F)
         .add("waterblast_cooldown", 50.0F, -3.0F)
         .lvl("waterblast_damage", 12.8F, 0.8F)
         .add("arrows_spin", 3.0F)
         .add("angle_between_arrows", 120.0F)
         .add("waterblast_velocity", 1.0F)
         .add("waterblast_inaccuracy", 2.0F, -0.65F);
      newparam(ItemsRegister.SACRIFICIALDAGGER)
         .lvl("damage", 3.0F, -8.792019E7F)
         .add("knockback", -0.15F, 0.2F)
         .lvl("critical_damage", 2.0F)
         .add("length", 2.9F, 0.4F)
         .add("cooldown", 13.0F, -1.0F);
      levelRate += 0.55F * raise;
      newparam(ItemsRegister.PLASMAPISTOL)
         .lvl("damage", 3.84F, -8.792019E7F)
         .add("knockback", 0.7F, 0.35F)
         .add("damage_radius", 1.5F, 0.25F)
         .lvl("damage_plasma", 3.84F, -8.792019E7F)
         .add("knockback_plasma", 0.5F, 0.1F)
         .add("cooldown", 11.0F, -1.0F)
         .add("livetime", 40.0F, 5.0F)
         .add("velocity", 1.6F)
         .add("inaccuracy", 4.5F, -1.0F)
         .add("rf_to_shoot", 1000.0F, -100.0F);
      newparam(ItemsRegister.PLASMARIFLE)
         .lvl("damage", 2.56F, -8.792019E7F)
         .add("knockback", 0.67F, 0.27F)
         .lvl("damage_powered", 3.84F, -8.792019E7F)
         .add("cooldown", 2.0F, 0.0F)
         .add("velocity", 0.8F)
         .add("velocity_powered", 1.1F)
         .add("inaccuracy", 3.0F, -1.0F)
         .add("unstable", 0.6F, -0.2F)
         .add("rf_to_shoot", 250.0F, -25.0F)
         .add("heat_per_shoot", 5.0F, 1.0F)
         .add("heat_max", 350.0F)
         .add("fire", 5.0F)
         .lvl("wave_damage", 6.3999996F, -8.792019E7F)
         .add("wave_knockback", 2.5F, 0.25F)
         .add("wave_cooldown", 10.0F, -3.0F)
         .add("wave_length", 7.5F, 0.6F)
         .add("wave_size", 4.0F, 0.5F);
      newparam(ItemsRegister.PLASMARAILGUN)
         .lvl("damage", 13.75F, -8.792019E7F)
         .add("knockback", 2.0F, 0.83F)
         .lvl("damage_per_pierces", -1.6500001F, 0.11000001F)
         .add("cooldown", 19.0F, -3.0F)
         .add("reload", 45.0F, -10.0F)
         .add("livetime", 14.0F, 2.0F)
         .add("velocity", 3.9F)
         .add("inaccuracy", 0.0F, 0.0F)
         .add("rf_to_shoot", 5000.0F, -250.0F)
         .add("max_pierces", 1.0F, 2.0F)
         .potion("shock", 50, 1, 15, 0.2F, true);
      newparam(ItemsRegister.ELECTRICSTAFF)
         .lvl("damage", 6.18F, -8.792019E7F)
         .add("knockback", 0.3F, 0.25F)
         .add("manacost", 2.8F, -0.45F)
         .add("cooldown", 10.0F, -2.0F)
         .add("velocity", 1.4F)
         .add("inaccuracy", 3.8F, -1.1F)
         .add("livetime", 100.0F, 10.0F)
         .potion("shock", 125, 0, 10, 0.43F, true);
      newparam(ItemsRegister.CHARGER)
         .lvl("damage", 0.35999998F, -8.792019E7F)
         .add("knockback", 0.0F, 0.125F)
         .add("damage_radius", 5.0F, 0.8F)
         .add("manacost", 0.11F, -0.02F)
         .add("cooldown", 0.0F, 0.0F)
         .add("rapid_multiplier", 0.2F)
         .add("distance", 10.0F, 2.0F)
         .add("targets", 3.0F, 2.0F);
      newparam(ItemsRegister.STATICLANCE)
         .lvl("damage", 7.7999997F, -8.792019E7F)
         .add("knockback", 0.0F, 0.6F)
         .add("length", 5.0F, 0.5F)
         .add("size", 0.5F, 0.1F)
         .add("cooldown", 20.0F, -3.0F)
         .add("blow_cooldown", 110.0F, -20.0F)
         .add("damage_radius", 4.0F, 0.5F)
         .add("blow_distance", 7.5F, 0.25F)
         .lvl("blow_damage", 7.7999997F, -8.792019E7F)
         .add("blow_knockback", 2.6F, 0.666F)
         .add("special_max_power", 2.0F)
         .lvl("special_damage", 5.46F)
         .add("special_potion_time", 50.0F, 5.0F)
         .add("potion_time", 50.0F, 15.0F);
      newparam(ItemsRegister.VOLTRIDENT)
         .lvl("damage", 1.54F, -8.792019E7F)
         .add("knockback", 0.0F, 0.33F)
         .add("damage_radius", 15.0F, 1.0F)
         .lvl("damage_overkill", 7.7F, -8.792019E7F)
         .add("manacost", 0.4F, -0.077F)
         .add("manacost_powered", 0.2F, -0.038F)
         .add("cooldown", 4.0F, -1.0F)
         .add("xp_decrease", 1.0F)
         .add("distance", 22.0F, 5.0F)
         .add("targets", 8.0F, 2.0F)
         .add("voltage_to_overkill", 3.0F);
      EXlevelPLASMA = levelRate;
      levelRate += 0.25F * raise;
      newparam("tools_storm")
         .lvl("damage_drill", 2.25F, -8.792019E7F)
         .add("knockback_drill", 0.1F, 0.05F)
         .lvl("damage_chainsaw", 2.75F, -8.792019E7F)
         .add("knockback_chainsaw", 0.1F, 0.05F)
         .lvl("damage_laser", 1.25F, -8.792019E7F)
         .add("knockback_laser", 0.1F, 0.05F)
         .add("length", 4.0F, 0.33F)
         .add("length_laser", 20.0F, 1.75F)
         .add("reload", 40.0F, -7.0F);
      newparam(ItemsRegister.PLASMAMINIGUN)
         .lvl("damage", 1.5899999F, -8.792019E7F)
         .add("knockback", 0.4F, 0.15F)
         .add("bullet_damage", 0.85F)
         .add("bullet_knockback", 0.85F)
         .add("cooldown", 1.0F, 0.0F)
         .add("reload", 65.0F, -10.0F)
         .add("deploy_speed", 0.05F, 0.03F)
         .add("cool_rate", 2.0F, 1.0F)
         .add("max_speed", 20.0F)
         .add("distance", 45.0F, 5.0F)
         .add("inaccuracy", 1.1F, -0.3F)
         .add("rf_to_shoot", 50.0F, -10.0F)
         .add("max_heat", 80.0F);
      newparam(ItemsRegister.PLASMAACCELERATOR)
         .lvl("damage", 7.8F, -8.792019E7F)
         .add("knockback", 1.8F, 0.666F)
         .add("damage_radius", 2.0F, 0.3F)
         .add("radius_blocks", 3.0F, 0.5F)
         .lvl("friendlyfire_damage", 6.0F, -8.792019E7F)
         .add("friendlyfire", 0.5F)
         .add("damage_multiplier", 3.0F)
         .add("knockback_to_blocks", 2.0F, 0.2F)
         .add("hardness_breaks", 2.0F, 0.5F)
         .add("cooldown", 37.0F, -7.0F)
         .add("livetime", 40.0F, 5.0F)
         .add("velocity", 1.5F)
         .add("inaccuracy", 2.0F, -0.65F)
         .add("rf_to_shoot", 15000.0F, -1000.0F)
         .add("shots", 2.0F, 1.0F);
      newparam(ItemsRegister.ANIHGUN)
         .lvl("damage", 3.0F, -8.792019E7F)
         .add("knockback", 1.5F, 0.33F)
         .add("damage_radius", 6.0F, 0.4F)
         .add("reload", 100.0F, -20.0F)
         .add("distance", 30.0F, 3.0F)
         .add("charge_need", 60.0F, -10.0F)
         .add("explode_chance", 0.3F, 0.08F)
         .add("explode_power", 2.0F, 0.33F);
      levelRate = 1.0F;
      newparam(ItemsRegister.GRENADECLASSIC)
         .add("cooldown", 100.0F)
         .add("first_explode_delay", 30.0F)
         .lvl("damage", 18.0F)
         .add("knockback", 2.0F)
         .add("damage_radius", 4.0F);
      newparam(ItemsRegister.GRENADEBOMB).add("cooldown", 120.0F).add("first_explode_delay", 40.0F).add("explosion_size", 3.0F);
      newparam(ItemsRegister.GRENADECRYO)
         .add("cooldown", 200.0F)
         .add("first_explode_delay", 30.0F)
         .lvl("damage", 5.0F)
         .add("knockback", 0.0F)
         .add("damage_radius", 4.6F)
         .add("potion_time_add", 120.0F)
         .add("potion_power_add", 6.0F)
         .add("potion_time_max", 220.0F)
         .add("potion_power_max", 18.0F)
         .add("friendlyfire_potion_time_add", 80.0F)
         .add("friendlyfire_potion_power_add", 6.0F)
         .add("friendlyfire_potion_time_max", 160.0F)
         .add("friendlyfire_potion_power_max", 16.0F);
      newparam(ItemsRegister.GRENADEHELL)
         .add("cooldown", 230.0F)
         .add("first_explode_delay", 30.0F)
         .lvl("damage", 15.0F)
         .add("knockback", 0.8F)
         .add("damage_radius", 3.0F)
         .add("max_explosions", 5.0F);
      newparam(ItemsRegister.GRENADEMOLOTOV)
         .add("cooldown", 180.0F)
         .add("first_explode_delay", 100.0F)
         .add("damage_radius", 2.0F)
         .add("fire", 10.0F)
         .add("friendlyfire_fire", 5.0F)
         .add("max_explosions", 3.0F);
      newparam(ItemsRegister.GRENADEOIL)
         .add("cooldown", 80.0F)
         .add("first_explode_delay", 100.0F)
         .lvl("damage", 0.0F)
         .add("knockback", 0.0F)
         .add("damage_radius", 4.6F)
         .add("potion_time", 800.0F)
         .add("potion_power_add", 1.0F)
         .add("potion_power_max", 6.0F);
      newparam(ItemsRegister.GRENADESNOW)
         .add("cooldown", 180.0F)
         .add("first_explode_delay", 30.0F)
         .add("damage_radius", 5.0F)
         .add("falling_snow_count", 30.0F)
         .add("falling_snow_speed", 1.0F);
      newparam(ItemsRegister.GRENADEGAS)
         .add("cooldown", 120.0F)
         .add("first_explode_delay", 80.0F)
         .lvl("damage", 30.0F)
         .add("knockback", 1.0F)
         .add("damage_radius", 1.3F)
         .add("max_explosions", 30.0F)
         .add("grow_limit", 4.0F)
         .add("potion_time_add", 30.0F)
         .add("potion_power_add", 1.0F)
         .add("potion_time_max", 300.0F)
         .add("potion_power_max", 25.0F);
      newparam(ItemsRegister.GRENADESEA)
         .add("cooldown", 220.0F)
         .add("first_explode_delay", 30.0F)
         .lvl("damage", 15.0F * EXlevelSEA_MIDDLE)
         .lvl("bomb_damage", 7.5F * EXlevelSEA_MIDDLE)
         .add("damage_radius", 3.0F)
         .add("destroy_radius", 2.0F)
         .add("bombs", 8.0F)
         .add("min_bomb_explode_delay", 30.0F)
         .add("bomb_speed", 0.8F);
      newparam(ItemsRegister.GRENADEWATCHING).add("cooldown", 300.0F).add("first_explode_delay", 35.0F).lvl("damage", 8.0F).add("watcher_livetime", 400.0F);
      newparam(ItemsRegister.GRENADEGRAVITY)
         .add("cooldown", 400.0F)
         .add("first_explode_delay", 40.0F)
         .add("damage_radius", 7.0F)
         .add("livetime", 200.0F)
         .add("gravity", 0.65F)
         .add("friendlyfire_gravity", 0.3F);
      newparam(ItemsRegister.ROCKETCOMMON).lvl("damage", 5.0F).add("knockback", 1.0F).add("damage_radius", 2.5F, 0.18F);
      newparam(ItemsRegister.ROCKETFROSTFIRE)
         .lvl("damage", 6.0F)
         .add("knockback", 0.4F)
         .add("damage_radius", 2.0F, 0.16F)
         .add("radius_freeze", 2.0F, 0.35F)
         .add("potion_time", 180.0F, 20.0F)
         .add("friendlyfire_potion_time", 150.0F)
         .add("potion_power", 1.0F, 0.3F);
      newparam(ItemsRegister.ROCKETCHEMICAL)
         .lvl("damage", 7.0F)
         .add("knockback", 0.7F)
         .add("damage_radius", 2.6F, 0.15F)
         .add("turn_water_chance", 0.3F, 0.3F)
         .add("radius_turn_water", 2.0F, 0.35F)
         .add("toxin_time", 70.0F, 5.0F)
         .add("friendlyfire_toxin_time", 50.0F)
         .add("toxin_power", 1.0F, 0.25F)
         .add("poison_time", 150.0F, 20.0F)
         .add("friendlyfire_poison_time", 80.0F)
         .add("poison_power", 0.0F, 0.4F)
         .add("slime_time", 45.0F, 14.0F)
         .add("friendlyfire_slime_time", 45.0F)
         .add("slime_power", 1.0F, 0.2F);
      newparam(ItemsRegister.ROCKETNAPALM)
         .lvl("damage", 5.0F)
         .add("knockback", 0.8F)
         .add("damage_radius", 3.0F, 0.2F)
         .add("fire", 4.0F, 1.0F)
         .add("chance_ignite", 0.5F)
         .add("radius_ignite", 1.0F, 0.5F);
      newparam(ItemsRegister.ROCKETDEMOLISHING).add("base_explosion_size", 2.0F, 0.2F).add("rooted_weapondamage_explode_multiplier", 0.35F);
      newparam(ItemsRegister.ROCKETMINING)
         .lvl("damage", 4.0F)
         .add("knockback", 1.2F)
         .add("damage_radius", 1.8F, 0.3F)
         .add("radius_block_destroy", 2.0F, 0.5F)
         .add("damage_to_hardness_breaks", 0.3F);
      newparam(ItemsRegister.ROCKETVOID).lvl("damage", 6.0F).add("knockback", 1.0F).add("damage_radius", 2.8F, 0.25F);
      newparam(ItemsRegister.ROCKETWATERBLAST).lvl("damage", 7.0F).add("knockback", 0.5F).add("damage_radius", 3.5F, 0.28F).add("wet_radius", 3.0F, 0.5F);
      newparam(ItemsRegister.ROCKETARCANE).lvl("damage", 8.0F).add("knockback", 0.7F).add("damage_radius", 3.0F, 0.19F);
      newparam(ItemsRegister.ROCKETSURPRISE).lvl("damage", 3.0F).add("knockback", 0.0F).add("surprises", 8.0F, 1.0F);
      newparam(ItemsRegister.ROCKETSHELL)
         .lvl("damage", 8.0F)
         .lvl("shells_damage", 2.0F * EXlevelSEA_MIDDLE)
         .add("knockback", 0.6F)
         .add("damage_radius", 2.0F, 0.17F)
         .add("shells_min", 6.0F)
         .add("shells_max", 12.0F);
      newparam(ItemsRegister.ARROWBOUNCING).lvl("damage", 2.0F);
      newparam(ItemsRegister.ARROWFIREJET).lvl("damage", 2.5F);
      newparam(ItemsRegister.ARROWVOID).lvl("damage", 3.0F);
      newparam(ItemsRegister.ARROWBENGAL).lvl("damage", 3.0F).lvl("bengal_damage", 3.0F * EXlevelFROZEN);
      newparam(ItemsRegister.ARROWFROZEN).lvl("damage", 3.0F);
      newparam(ItemsRegister.ARROWVICIOUS).lvl("damage", 3.5F);
      newparam(ItemsRegister.ARROWMODERN).lvl("damage", 4.0F);
      newparam(ItemsRegister.ARROWMITHRIL).lvl("damage", 5.0F);
      newparam(ItemsRegister.ARROWFISH).lvl("damage", 4.5F);
      newparam(ItemsRegister.ARROWSHELL).lvl("damage", 4.5F).lvl("shard_damage", 2.0F * EXlevelSEA_MIDDLE);
      newparam(ItemsRegister.ARROWTWIN)
         .lvl("damage", 5.0F)
         .lvl("damage_link", 5.0F * EXlevelPLASMA)
         .lvl("damage_link_length_mult", -0.5F)
         .lvl("damage_link_max", 50.0F * EXlevelPLASMA);
      newparam(ItemsRegister.ARROWWIND).lvl("damage", 7.0F);
      newparam(ItemsRegister.BULLETCOPPER).lvl("damage", 0.0F).lvl("knockback", 0.0F);
      newparam(ItemsRegister.BULLETLEAD).lvl("damage", 0.5F).lvl("knockback", 0.2F);
      newparam(ItemsRegister.BULLETSILVER).lvl("damage", 1.0F).lvl("knockback", 0.0F);
      newparam(ItemsRegister.BULLETGOLD).lvl("damage", 1.5F).lvl("knockback", 0.4F);
      newparam(ItemsRegister.BULLETEXPLODING).lvl("damage", 1.0F).lvl("knockback", 0.5F);
      newparam(ItemsRegister.BULLETFIRE).lvl("damage", 2.5F).lvl("knockback", 0.0F);
      newparam(ItemsRegister.BULLETERRATIC).lvl("damage", 2.0F).lvl("knockback", 0.3F);
      newparam(ItemsRegister.BULLETFROZEN).lvl("damage", 2.5F).lvl("knockback", 0.0F);
      newparam(ItemsRegister.BULLETFESTIVAL).lvl("damage", 0.5F).lvl("knockback", 0.0F);
      newparam(ItemsRegister.BULLETNIVEOUS).lvl("damage", 1.5F).lvl("knockback", 0.1F).lvl("damage_bonus", 2.3F);
      newparam(ItemsRegister.BULLETPOISON).lvl("damage", 3.0F).lvl("knockback", 0.0F);
      newparam(ItemsRegister.BULLETTOXIC).lvl("damage", 3.5F).lvl("knockback", 0.0F);
      newparam(ItemsRegister.BULLETADAMANTIUM).lvl("damage", 5.0F).lvl("knockback", 0.3F);
      newparam(ItemsRegister.BULLETCRYSTAL)
         .lvl("damage", 3.0F)
         .lvl("knockback", 0.0F)
         .lvl("damage_crystals", 5.0F * EXlevelDUNGEON_CHEST)
         .add("amount_crystals", 8.0F);
      newparam(ItemsRegister.BULLETCORAL).lvl("damage", 3.5F).lvl("knockback", 0.0F).lvl("damage_polyp", 3.0F * EXlevelSEA_MIDDLE);
      newparam(ItemsRegister.BULLETDIVERS).lvl("damage", 4.5F).lvl("knockback", 0.2F);
      newparam(ItemsRegister.BULLETTHUNDER).lvl("damage", 5.0F).lvl("knockback", 0.0F).lvl("damage_thunder", 5.5F * EXlevelPLASMA);
   }
}
