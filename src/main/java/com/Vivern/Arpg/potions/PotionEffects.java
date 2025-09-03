//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.potions;

import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.PropertiesRegistry;
import com.google.common.collect.Lists;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PotionEffects {
   public static final Potion ICHOR_CURSE = new IchorCurse(true, 16378466)
      .registerPotionAttributeModifier(SharedMonsterAttributes.ARMOR, MathHelper.getRandomUUID().toString(), -0.5, 2)
      .registerPotionAttributeModifier(PropertiesRegistry.ENTITY_COLOR_BLUE_MAX, MathHelper.getRandomUUID().toString(), -0.8F, 2)
      .registerPotionAttributeModifier(PropertiesRegistry.ENTITY_COLOR_GREEN_MAX, MathHelper.getRandomUUID().toString(), -0.2F, 2);
   public static final Potion WAVING = new Waving(true, 9032348);
   public static final Potion RAINBOW = new Rainbow(true, 16748420);
   public static final Potion LENSBLUR = new Lensblur(true, 16777199);
   public static final Potion SHOCK = new Shock(true, 15333119);
   public static final Potion SLIME = new Slime(true, 8642693)
      .registerPotionAttributeModifier(PropertiesRegistry.ENTITY_COLOR_GREEN_MAX, MathHelper.getRandomUUID().toString(), 0.6F, 2)
      .registerPotionAttributeModifier(PropertiesRegistry.ENTITY_COLOR_BLUE_MAX, MathHelper.getRandomUUID().toString(), -0.2F, 2)
      .registerPotionAttributeModifier(PropertiesRegistry.ENTITY_COLOR_RED_MAX, MathHelper.getRandomUUID().toString(), -0.3F, 2);
   public static final Potion FIERYOIL = new FieryOil(true, 10632192)
      .registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, MathHelper.getRandomUUID().toString(), -0.3F, 2)
      .registerPotionAttributeModifier(PropertiesRegistry.ENTITY_COLOR_GREEN_MAX, MathHelper.getRandomUUID().toString(), -0.5, 2)
      .registerPotionAttributeModifier(PropertiesRegistry.ENTITY_COLOR_BLUE_MAX, MathHelper.getRandomUUID().toString(), -0.85F, 2)
      .registerPotionAttributeModifier(PropertiesRegistry.ENTITY_COLOR_RED_MAX, MathHelper.getRandomUUID().toString(), -0.35F, 2);
   public static final Potion INSTANT_MANA = new InstantMana(false, 39167);
   public static final Potion MANA_REGENERATION = new ManaRegeneration(false, 8550911)
      .registerPotionAttributeModifier(PropertiesRegistry.MANASPEED_MAX, MathHelper.getRandomUUID().toString(), 0.15F, 0);
   public static final Potion MAGIC_POWER = new MagicPower(false, 8192255)
      .registerPotionAttributeModifier(PropertiesRegistry.MAGIC_POWER_MAX, MathHelper.getRandomUUID().toString(), 0.2F, 0);
   public static final Potion FIRE_AURA = new FireAura(false, 16756992);
   public static final Potion ENDER_POISON = new EnderPoison(true, 3873882, 5);
   public static final Potion INCORPOREITY = new Incorporeity(true, 14214863)
      .registerPotionAttributeModifier(PropertiesRegistry.ARMOR_PROTECTION, MathHelper.getRandomUUID().toString(), -2.0, 0);
   public static final Potion TOXIN = new Toxin(true, 5292288)
      .registerPotionAttributeModifier(PropertiesRegistry.ENTITY_COLOR_RED_MAX, MathHelper.getRandomUUID().toString(), -0.7F, 2)
      .registerPotionAttributeModifier(PropertiesRegistry.ENTITY_COLOR_BLUE_MAX, MathHelper.getRandomUUID().toString(), -0.6F, 2);
   public static final Potion ELECTRIFICATION = new Electrification(true, 15391743);
   public static final Potion WALKING_BOMB = new WalkingBomb();
   public static final Potion BERSERK = new Berserk();
   public static final Potion BLOOD_THIRST = new BloodThirst();
   public static final Potion DEMONIC_BURN = new DemonicBurn(3);
   public static final Potion SPIKED = new Spiked();
   public static final Potion INSANE = new Insane(true, 16711700);
   public static final Potion INSTANT_AGREE = new InstantAgree(true, 14474495);
   public static final Potion FROSTBURN = new Frostburn(4);
   public static final Potion CHLORITE = new Chlorite(6);
   public static final Potion RAD_REDUCTION = new RadReduction(7);
   public static final Potion WINTER_CURSE = new WinterCurse(8);
   public static final Potion RESPAWN_PENALTY = new RespawnPenalty(9);
   public static final Potion FREEZING = new Freezing(true, 6991103, 10);
   public static final Potion TENACITY = new TenacityPotion(false, 3036176);
   public static final Potion BROKEN_ARMOR = new BrokenArmor(11);
   public static final Potion SIREN_SONG = new SirenSong(12);
   public static final Potion SWIMMING = new Swimming(13);
   public static final Potion MANA_OIL = new ManaOil(14);
   public static final Potion HEALTH_DEGRADATION = new HealthDegradation(15);
   public static final Potion COLD_SOUL = new ColdSoul(16);
   public static final Potion SQUASHED = new Squashed(17);
   public static final Potion STUN = new Stun(18);
   public static final Potion FIBER_BANDAGING = new FiberBandaging(19);
   public static final Potion FREEZE_IMMUNITY = new FreezeImmunity(20);
   public static final PotionType ICHOR_POTIONTYPE_MAIN = (PotionType)new PotionType("Ichor_Curse", new PotionEffect[]{new PotionEffect(ICHOR_CURSE, 1000)})
      .setRegistryName("ichor_potion");
   public static final PotionType ICHOR_POTIONTYPE_LONG = (PotionType)new PotionType("Ichor_Curse", new PotionEffect[]{new PotionEffect(ICHOR_CURSE, 2000)})
      .setRegistryName("ichor_long_potion");
   public static final PotionType FREEZING_POTIONTYPE_MAIN = (PotionType)new PotionType("Freezing", new PotionEffect[]{new PotionEffect(FREEZING, 200, 1)})
      .setRegistryName("freezing_potion");
   public static final PotionType FREEZING_POTIONTYPE_STRONG = (PotionType)new PotionType("Freezing", new PotionEffect[]{new PotionEffect(FREEZING, 200, 3)})
      .setRegistryName("freezing_strong_potion");
   public static final PotionType FREEZING_POTIONTYPE_VERYSTRONG = (PotionType)new PotionType(
         "Freezing", new PotionEffect[]{new PotionEffect(FREEZING, 200, 5)}
      )
      .setRegistryName("freezing_verystrong_potion");
   public static final PotionType WAVING_POTIONTYPE_MAIN = (PotionType)new PotionType("Waving", new PotionEffect[]{new PotionEffect(WAVING, 1300)})
      .setRegistryName("waving_potion");
   public static final PotionType WAVING_POTIONTYPE_LONG = (PotionType)new PotionType("Waving", new PotionEffect[]{new PotionEffect(WAVING, 2500)})
      .setRegistryName("waving_long_potion");
   public static final PotionType WAVING_POTIONTYPE_STRONG = (PotionType)new PotionType("Waving", new PotionEffect[]{new PotionEffect(WAVING, 900, 1)})
      .setRegistryName("waving_strong_potion");
   public static final PotionType WAVING_POTIONTYPE_VERYSTRONG = (PotionType)new PotionType("Waving", new PotionEffect[]{new PotionEffect(WAVING, 700, 2)})
      .setRegistryName("waving_verystrong_potion");
   public static final PotionType RAINBOW_POTIONTYPE_MAIN = (PotionType)new PotionType("Rainbow", new PotionEffect[]{new PotionEffect(RAINBOW, 1100)})
      .setRegistryName("rainbow_potion");
   public static final PotionType LENSBLUR_POTIONTYPE_MAIN = (PotionType)new PotionType("Lensblur", new PotionEffect[]{new PotionEffect(LENSBLUR, 950)})
      .setRegistryName("lensblur_potion");
   public static final PotionType SHOCK_POTIONTYPE_MAIN = (PotionType)new PotionType("Shock", new PotionEffect[]{new PotionEffect(SHOCK, 500)})
      .setRegistryName("shock_potion");
   public static final PotionType SHOCK_POTIONTYPE_STRONG = (PotionType)new PotionType("Shock", new PotionEffect[]{new PotionEffect(SHOCK, 250, 1)})
      .setRegistryName("shock_strong_potion");
   public static final PotionType SLIME_POTIONTYPE_MAIN = (PotionType)new PotionType("Slime", new PotionEffect[]{new PotionEffect(SLIME, 750)})
      .setRegistryName("slime_potion");
   public static final PotionType SLIME_POTIONTYPE_STRONG = (PotionType)new PotionType("Slime", new PotionEffect[]{new PotionEffect(SLIME, 450, 1)})
      .setRegistryName("slime_strong_potion");
   public static final PotionType SLIME_POTIONTYPE_LONG = (PotionType)new PotionType("Slime", new PotionEffect[]{new PotionEffect(SLIME, 1250)})
      .setRegistryName("slime_long_potion");
   public static final PotionType FIERYOIL_POTIONTYPE_MAIN = (PotionType)new PotionType("Fiery_Oil", new PotionEffect[]{new PotionEffect(FIERYOIL, 550)})
      .setRegistryName("fieryoil_potion");
   public static final PotionType FIERYOIL_POTIONTYPE_LONG = (PotionType)new PotionType("Fiery_Oil", new PotionEffect[]{new PotionEffect(FIERYOIL, 950)})
      .setRegistryName("fieryoil_long_potion");
   public static final PotionType FIERYOIL_POTIONTYPE_STRONG = (PotionType)new PotionType("Fiery_Oil", new PotionEffect[]{new PotionEffect(FIERYOIL, 400, 1)})
      .setRegistryName("fieryoil_strong_potion");
   public static final PotionType INSTANT_MANA_POTIONTYPE_MAIN = (PotionType)new PotionType(
         "Instant_Mana", new PotionEffect[]{new PotionEffect(INSTANT_MANA, 1)}
      )
      .setRegistryName("instant_mana_potion");
   public static final PotionType INSTANT_MANA_POTIONTYPE_STRONG = (PotionType)new PotionType(
         "Instant_Mana", new PotionEffect[]{new PotionEffect(INSTANT_MANA, 1, 1)}
      )
      .setRegistryName("instant_mana_strong_potion");
   public static final PotionType INSTANT_MANA_POTIONTYPE_VERYSTRONG = (PotionType)new PotionType(
         "Instant_Mana", new PotionEffect[]{new PotionEffect(INSTANT_MANA, 1, 2)}
      )
      .setRegistryName("instant_mana_verystrong_potion");
   public static final PotionType INSTANT_MANA_POTIONTYPE_GREAT = (PotionType)new PotionType(
         "Instant_Mana", new PotionEffect[]{new PotionEffect(INSTANT_MANA, 1, 3)}
      )
      .setRegistryName("instant_mana_great_potion");
   public static final PotionType INSTANT_MANA_POTIONTYPE_POWERFUL = (PotionType)new PotionType(
         "Instant_Mana", new PotionEffect[]{new PotionEffect(INSTANT_MANA, 1, 4)}
      )
      .setRegistryName("instant_mana_powerful_potion");
   public static final PotionType INSTANT_MANA_POTIONTYPE_MIGHTY = (PotionType)new PotionType(
         "Instant_Mana", new PotionEffect[]{new PotionEffect(INSTANT_MANA, 1, 5)}
      )
      .setRegistryName("instant_mana_mighty_potion");
   public static final PotionType INSTANT_MANA_POTIONTYPE_IMPERIOUS = (PotionType)new PotionType(
         "Instant_Mana", new PotionEffect[]{new PotionEffect(INSTANT_MANA, 1, 6)}
      )
      .setRegistryName("instant_mana_imperious_potion");
   public static final PotionType INSTANT_MANA_POTIONTYPE_MAGNIFICENT = (PotionType)new PotionType(
         "Instant_Mana", new PotionEffect[]{new PotionEffect(INSTANT_MANA, 1, 7)}
      )
      .setRegistryName("instant_mana_magnificent_potion");
   public static final PotionType INSTANT_MANA_POTIONTYPE_UNBELIEVABLE = (PotionType)new PotionType(
         "Instant_Mana", new PotionEffect[]{new PotionEffect(INSTANT_MANA, 1, 8)}
      )
      .setRegistryName("instant_mana_unbelievable_potion");
   public static final PotionType INSTANT_MANA_POTIONTYPE_CELESTIAL = (PotionType)new PotionType(
         "Instant_Mana", new PotionEffect[]{new PotionEffect(INSTANT_MANA, 1, 9)}
      )
      .setRegistryName("instant_mana_celestial_potion");
   public static final PotionType INSTANT_MANA_POTIONTYPE_UNIMAGINABLE = (PotionType)new PotionType(
         "Instant_Mana", new PotionEffect[]{new PotionEffect(INSTANT_MANA, 1, 10)}
      )
      .setRegistryName("instant_mana_unimaginable_potion");
   public static final PotionType MANA_REGENERATION_POTIONTYPE_MAIN = (PotionType)new PotionType(
         "Mana_Regeneration", new PotionEffect[]{new PotionEffect(MANA_REGENERATION, 3550)}
      )
      .setRegistryName("manaregen_potion");
   public static final PotionType MANA_REGENERATION_POTIONTYPE_STRONG = (PotionType)new PotionType(
         "Mana_Regeneration", new PotionEffect[]{new PotionEffect(MANA_REGENERATION, 2550, 1)}
      )
      .setRegistryName("manaregen_strong_potion");
   public static final PotionType MANA_REGENERATION_POTIONTYPE_LONG = (PotionType)new PotionType(
         "Mana_Regeneration", new PotionEffect[]{new PotionEffect(MANA_REGENERATION, 5250)}
      )
      .setRegistryName("manaregen_long_potion");
   public static final PotionType MAGIC_POWER_POTIONTYPE_MAIN = (PotionType)new PotionType(
         "Magic_Power", new PotionEffect[]{new PotionEffect(MAGIC_POWER, 3300)}
      )
      .setRegistryName("wizardry_potion");
   public static final PotionType MAGIC_POWER_POTIONTYPE_STRONG = (PotionType)new PotionType(
         "Magic_Power", new PotionEffect[]{new PotionEffect(MAGIC_POWER, 2000, 1)}
      )
      .setRegistryName("wizardry_strong_potion");
   public static final PotionType MAGIC_POWER_POTIONTYPE_LONG = (PotionType)new PotionType(
         "Magic_Power", new PotionEffect[]{new PotionEffect(MAGIC_POWER, 4500)}
      )
      .setRegistryName("wizardry_long_potion");
   public static final PotionType FIRE_AURA_POTIONTYPE_MAIN = (PotionType)new PotionType("Fire_Aura", new PotionEffect[]{new PotionEffect(FIRE_AURA, 1500)})
      .setRegistryName("fire_aura_potion");
   public static final PotionType FIRE_AURA_POTIONTYPE_LONG = (PotionType)new PotionType("Fire_Aura", new PotionEffect[]{new PotionEffect(FIRE_AURA, 2500)})
      .setRegistryName("fire_aura_long_potion");
   public static final PotionType FIRE_AURA_POTIONTYPE_STRONG = (PotionType)new PotionType(
         "Fire_Aura", new PotionEffect[]{new PotionEffect(FIRE_AURA, 1000, 1)}
      )
      .setRegistryName("fire_aura_strong_potion");
   public static final PotionType ENDER_POISON_POTIONTYPE_MAIN = (PotionType)new PotionType(
         "Ender_Poison", new PotionEffect[]{new PotionEffect(ENDER_POISON, 200)}
      )
      .setRegistryName("ender_poison_potion");
   public static final PotionType INCORPOREITY_POTIONTYPE_MAIN = (PotionType)new PotionType(
         "Incorporeity", new PotionEffect[]{new PotionEffect(INCORPOREITY, 1000)}
      )
      .setRegistryName("ethereal_potion");
   public static final PotionType INCORPOREITY_POTIONTYPE_LONG = (PotionType)new PotionType(
         "Incorporeity", new PotionEffect[]{new PotionEffect(INCORPOREITY, 2500)}
      )
      .setRegistryName("ethereal_long_potion");
   public static final PotionType INCORPOREITY_POTIONTYPE_STRONG = (PotionType)new PotionType(
         "Incorporeity", new PotionEffect[]{new PotionEffect(INCORPOREITY, 800, 1)}
      )
      .setRegistryName("ethereal_strong_potion");
   public static final PotionType TOXIN_POTIONTYPE_MAIN = (PotionType)new PotionType("Toxin", new PotionEffect[]{new PotionEffect(TOXIN, 800)})
      .setRegistryName("toxin_potion");
   public static final PotionType TOXIN_POTIONTYPE_LONG = (PotionType)new PotionType("Toxin", new PotionEffect[]{new PotionEffect(TOXIN, 1500)})
      .setRegistryName("toxin_long_potion");
   public static final PotionType TOXIN_POTIONTYPE_STRONG = (PotionType)new PotionType("Toxin", new PotionEffect[]{new PotionEffect(TOXIN, 400, 1)})
      .setRegistryName("toxin_strong_potion");
   public static final PotionType ELECTRIFICATION_POTIONTYPE_POSITIVE = (PotionType)new PotionType(
         "Electrification", new PotionEffect[]{new PotionEffect(ELECTRIFICATION, 500, 3)}
      )
      .setRegistryName("electrification_pos_potion");
   public static final PotionType ELECTRIFICATION_POTIONTYPE_NEGATIVE = (PotionType)new PotionType(
         "Electrification", new PotionEffect[]{new PotionEffect(ELECTRIFICATION, 500, 4)}
      )
      .setRegistryName("electrification_neg_potion");
   public static final PotionType WALKING_BOMB_POTIONTYPE_MAIN = (PotionType)new PotionType(
         "Walking_Bomb", new PotionEffect[]{new PotionEffect(WALKING_BOMB, 400)}
      )
      .setRegistryName("walking_bomb_potion");
   public static final PotionType WALKING_BOMB_POTIONTYPE_STRONG = (PotionType)new PotionType(
         "Walking_Bomb", new PotionEffect[]{new PotionEffect(WALKING_BOMB, 400, 1)}
      )
      .setRegistryName("walking_bomb_strong_potion");
   public static final PotionType BERSERK_POTIONTYPE_MAIN = (PotionType)new PotionType("Berserk", new PotionEffect[]{new PotionEffect(BERSERK, 2000)})
      .setRegistryName("berserk_potion");
   public static final PotionType BERSERK_POTIONTYPE_LONG = (PotionType)new PotionType("Berserk", new PotionEffect[]{new PotionEffect(BERSERK, 3500)})
      .setRegistryName("berserk_long_potion");
   public static final PotionType BERSERK_POTIONTYPE_STRONG = (PotionType)new PotionType("Berserk", new PotionEffect[]{new PotionEffect(BERSERK, 1000, 1)})
      .setRegistryName("berserk_strong_potion");
   public static final PotionType BLOOD_THIRST_POTIONTYPE_MAIN = (PotionType)new PotionType(
         "Blood_Thirst", new PotionEffect[]{new PotionEffect(BLOOD_THIRST, 1000)}
      )
      .setRegistryName("blood_thirst_potion");
   public static final PotionType DEMONIC_BURN_POTIONTYPE_MAIN = (PotionType)new PotionType(
         "Demonic_Burn", new PotionEffect[]{new PotionEffect(DEMONIC_BURN, 1000)}
      )
      .setRegistryName("demonic_burn_potion");
   public static final PotionType SPIKED_POTIONTYPE_MAIN = (PotionType)new PotionType("Spiked", new PotionEffect[]{new PotionEffect(SPIKED, 450, 5)})
      .setRegistryName("spiked_potion");
   public static final PotionType INSANE_POTIONTYPE_LOW = (PotionType)new PotionType("Insane", new PotionEffect[]{new PotionEffect(INSANE, 2000)})
      .setRegistryName("insane_potion");
   public static final PotionType INSANE_POTIONTYPE_STRONG = (PotionType)new PotionType("Insane", new PotionEffect[]{new PotionEffect(INSANE, 2000, 1)})
      .setRegistryName("insane_potion_strong");
   public static final PotionType INSANE_POTIONTYPE_IDENTIC = (PotionType)new PotionType("Insane", new PotionEffect[]{new PotionEffect(INSANE, 2000, 2)})
      .setRegistryName("insane_potion_identic");
   public static final PotionType INSANE_POTIONTYPE_KILLALL = (PotionType)new PotionType("Insane", new PotionEffect[]{new PotionEffect(INSANE, 2000, 3)})
      .setRegistryName("insane_potion_killall");
   public static final PotionType INSTANT_AGREE_POTIONTYPE = (PotionType)new PotionType("Instant_Agree", new PotionEffect[]{new PotionEffect(INSTANT_AGREE)})
      .setRegistryName("instant_agree");
   public static final PotionType FROSTBURN_POTIONTYPE_MAIN = (PotionType)new PotionType("FrostBurn", new PotionEffect[]{new PotionEffect(FROSTBURN, 400)})
      .setRegistryName("frostburn_potion");
   public static final PotionType CHLORITE_POTIONTYPE_MAIN = (PotionType)new PotionType("Chlorite", new PotionEffect[]{new PotionEffect(CHLORITE, 5000, 10)})
      .setRegistryName("hydrogen_chlorite_potion");
   public static final PotionType RAD_REDUCT_POTIONTYPE_MAIN = (PotionType)new PotionType(
         "Rad_Reduction", new PotionEffect[]{new PotionEffect(RAD_REDUCTION, 300)}
      )
      .setRegistryName("rad_reduction_potion");
   public static final PotionType WINTER_CURSE_POTIONTYPE_MAIN = (PotionType)new PotionType(
         "Winter_Curse", new PotionEffect[]{new PotionEffect(WINTER_CURSE, 99000)}
      )
      .setRegistryName("winter_curse_potion");
   public static final PotionType HUNGER_POTIONTYPE_MAIN = (PotionType)new PotionType(
         "Hunger", new PotionEffect[]{new PotionEffect(MobEffects.HUNGER, 1000)}
      )
      .setRegistryName("hunger_potion");
   public static final PotionType WITHER_POTIONTYPE_MAIN = (PotionType)new PotionType(
         "Wither", new PotionEffect[]{new PotionEffect(MobEffects.WITHER, 360)}
      )
      .setRegistryName("wither_potion");
   public static final PotionType TENACITY_POTIONTYPE_MAIN = (PotionType)new PotionType("Tenacity", new PotionEffect[]{new PotionEffect(TENACITY, 3600)})
      .setRegistryName("tenacity_potion");
   public static final PotionType TENACITY_POTIONTYPE_LONG = (PotionType)new PotionType("Tenacity", new PotionEffect[]{new PotionEffect(TENACITY, 9600)})
      .setRegistryName("tenacity_long_potion");
   public static final PotionType TENACITY_POTIONTYPE_STRONG = (PotionType)new PotionType("Tenacity", new PotionEffect[]{new PotionEffect(TENACITY, 1560, 1)})
      .setRegistryName("tenacity_strong_potion");
   public static final PotionType TENACITY_POTIONTYPE_VERYSTRONG = (PotionType)new PotionType(
         "Tenacity", new PotionEffect[]{new PotionEffect(TENACITY, 1200, 2)}
      )
      .setRegistryName("tenacity_verystrong_potion");
   public static final PotionType TENACITY_POTIONTYPE_GREAT = (PotionType)new PotionType("Tenacity", new PotionEffect[]{new PotionEffect(TENACITY, 1000, 3)})
      .setRegistryName("tenacity_great_potion");
   public static final PotionType BROKEN_ARMOR_POTIONTYPE_MAIN = (PotionType)new PotionType(
         "Broken_Armor", new PotionEffect[]{new PotionEffect(BROKEN_ARMOR, 3600)}
      )
      .setRegistryName("broken_armor_potion");
   public static final PotionType BROKEN_ARMOR_POTIONTYPE_LONG = (PotionType)new PotionType(
         "Broken_Armor", new PotionEffect[]{new PotionEffect(BROKEN_ARMOR, 9600)}
      )
      .setRegistryName("broken_armor_long_potion");
   public static final PotionType BROKEN_ARMOR_POTIONTYPE_STRONG = (PotionType)new PotionType(
         "Broken_Armor", new PotionEffect[]{new PotionEffect(BROKEN_ARMOR, 1560, 1)}
      )
      .setRegistryName("broken_armor_strong_potion");
   public static final PotionType BROKEN_ARMOR_POTIONTYPE_VERYSTRONG = (PotionType)new PotionType(
         "Broken_Armor", new PotionEffect[]{new PotionEffect(BROKEN_ARMOR, 1200, 2)}
      )
      .setRegistryName("broken_armor_verystrong_potion");
   public static final PotionType SIREN_SONG_POTIONTYPE_MAIN = (PotionType)new PotionType("Siren_Song", new PotionEffect[]{new PotionEffect(SIREN_SONG, 500)})
      .setRegistryName("siren_song_potion");
   public static final PotionType SWIMMING_POTIONTYPE_MAIN = (PotionType)new PotionType("Swimming", new PotionEffect[]{new PotionEffect(SWIMMING, 4000)})
      .setRegistryName("swimming_potion");
   public static final PotionType MANA_OIL_POTIONTYPE_MAIN = (PotionType)new PotionType("Mana_Oil", new PotionEffect[]{new PotionEffect(MANA_OIL, 160)})
      .setRegistryName("mana_oil_potion");
   public static final PotionType HEALTH_DEGRADATION_POTIONTYPE_MAIN = (PotionType)new PotionType(
         "Health_Degradation", new PotionEffect[]{new PotionEffect(HEALTH_DEGRADATION, 640)}
      )
      .setRegistryName("health_degradation_potion");
   public static final PotionType HEALTH_DEGRADATION_POTIONTYPE_LONG = (PotionType)new PotionType(
         "Health_Degradation", new PotionEffect[]{new PotionEffect(HEALTH_DEGRADATION, 2000)}
      )
      .setRegistryName("health_degradation_long_potion");
   public static final PotionType HEALTH_DEGRADATION_POTIONTYPE_STRONG = (PotionType)new PotionType(
         "Health_Degradation", new PotionEffect[]{new PotionEffect(HEALTH_DEGRADATION, 400, 1)}
      )
      .setRegistryName("health_degradation_strong_potion");
   public static final PotionType HEALTH_BOOST_POTIONTYPE_MAIN = (PotionType)new PotionType(
         "Health_Boost", new PotionEffect[]{new PotionEffect(MobEffects.HEALTH_BOOST, 3600)}
      )
      .setRegistryName("health_boost_potion");
   public static final PotionType HEALTH_BOOST_POTIONTYPE_LONG = (PotionType)new PotionType(
         "Health_Boost", new PotionEffect[]{new PotionEffect(MobEffects.HEALTH_BOOST, 9600)}
      )
      .setRegistryName("health_boost_long_potion");
   public static final PotionType HEALTH_BOOST_POTIONTYPE_STRONG = (PotionType)new PotionType(
         "Health_Boost", new PotionEffect[]{new PotionEffect(MobEffects.HEALTH_BOOST, 1800, 1)}
      )
      .setRegistryName("health_boost_strong_potion");
   public static final PotionType FREEZE_IMMUNITY_POTIONTYPE_MAIN = (PotionType)new PotionType(
         "Freeze_Immunity", new PotionEffect[]{new PotionEffect(FREEZE_IMMUNITY, 3600)}
      )
      .setRegistryName("Freeze_immunity_potion");
   public static final PotionType WATER_BREATHING_POTIONTYPE_VERYLONG = (PotionType)new PotionType(
         "water_breathing", new PotionEffect[]{new PotionEffect(MobEffects.WATER_BREATHING, 24000)}
      )
      .setRegistryName("water_breathing_verylong_potion");
   public static final PotionType NIGHT_VISION_POTIONTYPE_VERYLONG = (PotionType)new PotionType(
         "night_vision", new PotionEffect[]{new PotionEffect(MobEffects.NIGHT_VISION, 24000)}
      )
      .setRegistryName("night_vision_verylong_potion");

   public static void RegisterPotions() {
      ForgeRegistries.POTIONS.register(ICHOR_CURSE);
      ForgeRegistries.POTIONS.register(FREEZING);
      ForgeRegistries.POTIONS.register(WAVING);
      ForgeRegistries.POTIONS.register(RAINBOW);
      ForgeRegistries.POTIONS.register(LENSBLUR);
      ForgeRegistries.POTIONS.register(SHOCK);
      ForgeRegistries.POTIONS.register(SLIME);
      ForgeRegistries.POTIONS.register(FIERYOIL);
      ForgeRegistries.POTIONS.register(INSTANT_MANA);
      ForgeRegistries.POTIONS.register(MANA_REGENERATION);
      ForgeRegistries.POTIONS.register(MAGIC_POWER);
      ForgeRegistries.POTIONS.register(FIRE_AURA);
      ForgeRegistries.POTIONS.register(ENDER_POISON);
      ForgeRegistries.POTIONS.register(INCORPOREITY);
      ForgeRegistries.POTIONS.register(TOXIN);
      ForgeRegistries.POTIONS.register(ELECTRIFICATION);
      ForgeRegistries.POTIONS.register(WALKING_BOMB);
      ForgeRegistries.POTIONS.register(BERSERK);
      ForgeRegistries.POTIONS.register(BLOOD_THIRST);
      ForgeRegistries.POTIONS.register(DEMONIC_BURN);
      ForgeRegistries.POTIONS.register(SPIKED);
      ForgeRegistries.POTIONS.register(INSANE);
      ForgeRegistries.POTIONS.register(INSTANT_AGREE);
      ForgeRegistries.POTIONS.register(FROSTBURN);
      ForgeRegistries.POTIONS.register(CHLORITE);
      ForgeRegistries.POTIONS.register(RAD_REDUCTION);
      ForgeRegistries.POTIONS.register(RESPAWN_PENALTY);
      ForgeRegistries.POTIONS.register(TENACITY);
      ForgeRegistries.POTIONS.register(BROKEN_ARMOR);
      ForgeRegistries.POTIONS.register(SIREN_SONG);
      ForgeRegistries.POTIONS.register(SWIMMING);
      ForgeRegistries.POTIONS.register(MANA_OIL);
      ForgeRegistries.POTIONS.register(HEALTH_DEGRADATION);
      ForgeRegistries.POTIONS.register(COLD_SOUL);
      ForgeRegistries.POTIONS.register(SQUASHED);
      ForgeRegistries.POTIONS.register(STUN);
      ForgeRegistries.POTIONS.register(FIBER_BANDAGING);
      ForgeRegistries.POTIONS.register(FREEZE_IMMUNITY);
      Field[] fields = PotionEffects.class.getFields();

      for (Field field : fields) {
         if (field.getType() == PotionType.class) {
            PotionType item = null;

            try {
               item = (PotionType)field.get(new PotionEffects());
            } catch (IllegalArgumentException var7) {
               var7.printStackTrace();
            } catch (IllegalAccessException var8) {
               var8.printStackTrace();
            }

            ForgeRegistries.POTION_TYPES.register(item);
         }
      }
   }

   public static void RegisterPotionRecipes() {
      addMixWithRedstoneAndGlowsone(PotionTypes.AWKWARD, ItemsRegister.ICEGEM, FREEZING_POTIONTYPE_MAIN, null, FREEZING_POTIONTYPE_STRONG);
      PotionHelper.addMix(FREEZING_POTIONTYPE_STRONG, ItemsRegister.CRYOGENCELL, FREEZING_POTIONTYPE_VERYSTRONG);
      PotionHelper.addMix(PotionTypes.AWKWARD, ItemsRegister.CONIFERROSIN, FROSTBURN_POTIONTYPE_MAIN);
      addMixWithRedstoneAndGlowsone(
         PotionTypes.AWKWARD, ItemsRegister.FIERYOIL, FIERYOIL_POTIONTYPE_MAIN, FIERYOIL_POTIONTYPE_LONG, FIERYOIL_POTIONTYPE_STRONG
      );
      addMixWithRedstoneAndGlowsone(
         PotionTypes.FIRE_RESISTANCE, ItemsRegister.INGOTMOLTEN, FIRE_AURA_POTIONTYPE_MAIN, FIRE_AURA_POTIONTYPE_LONG, FIRE_AURA_POTIONTYPE_STRONG
      );
      PotionHelper.addMix(PotionTypes.AWKWARD, ItemsRegister.TOXIBERRYJUICEDRIP, PotionTypes.POISON);
      addMixWithRedstoneAndGlowsone(
         PotionTypes.POISON, ItemsRegister.NUGGETTOXINIUM, TOXIN_POTIONTYPE_MAIN, TOXIN_POTIONTYPE_LONG, TOXIN_POTIONTYPE_STRONG
      );
      addMixWithRedstoneAndGlowsone(
         PotionTypes.STRENGTH, ItemsRegister.MAGIC_POWDER, MAGIC_POWER_POTIONTYPE_MAIN, MAGIC_POWER_POTIONTYPE_LONG, MAGIC_POWER_POTIONTYPE_STRONG
      );
      addMixWithRedstoneAndGlowsone(
         PotionTypes.AWKWARD,
         Item.getItemFromBlock(BlocksRegister.ICEFLOWER),
         MANA_REGENERATION_POTIONTYPE_MAIN,
         MANA_REGENERATION_POTIONTYPE_LONG,
         MANA_REGENERATION_POTIONTYPE_STRONG
      );
      PotionHelper.addMix(PotionTypes.AWKWARD, ItemsRegister.MANABERRY, INSTANT_MANA_POTIONTYPE_MAIN);
      PotionHelper.addMix(INSTANT_MANA_POTIONTYPE_MAIN, Items.GLOWSTONE_DUST, INSTANT_MANA_POTIONTYPE_STRONG);
      PotionHelper.addMix(INSTANT_MANA_POTIONTYPE_STRONG, ItemsRegister.MAGIC_POWDER, INSTANT_MANA_POTIONTYPE_VERYSTRONG);
      PotionHelper.addMix(INSTANT_MANA_POTIONTYPE_VERYSTRONG, Item.getItemFromBlock(BlocksRegister.ICEFLOWER), INSTANT_MANA_POTIONTYPE_GREAT);
      PotionHelper.addMix(INSTANT_MANA_POTIONTYPE_GREAT, ItemsRegister.DUSTGLOWINGCRYSTAL, INSTANT_MANA_POTIONTYPE_POWERFUL);
      PotionHelper.addMix(PotionTypes.AWKWARD, ItemsRegister.QUANTUMSLIMEBALL, ENDER_POISON_POTIONTYPE_MAIN);
      addMixWithRedstoneAndGlowsone(
         PotionTypes.AWKWARD, ItemsRegister.WHITESLIMEBALL, SLIME_POTIONTYPE_MAIN, SLIME_POTIONTYPE_LONG, SLIME_POTIONTYPE_STRONG
      );
      PotionHelper.addMix(PotionTypes.AWKWARD, ItemsRegister.DEMONITE, DEMONIC_BURN_POTIONTYPE_MAIN);
      addMixWithRedstoneAndGlowsone(
         PotionTypes.AWKWARD,
         ItemsRegister.HEALTHBERRY,
         HEALTH_DEGRADATION_POTIONTYPE_MAIN,
         HEALTH_DEGRADATION_POTIONTYPE_LONG,
         HEALTH_DEGRADATION_POTIONTYPE_STRONG
      );
      addMixWithRedstoneAndGlowsone(
         HEALTH_DEGRADATION_POTIONTYPE_MAIN, Items.FERMENTED_SPIDER_EYE, HEALTH_BOOST_POTIONTYPE_MAIN, HEALTH_BOOST_POTIONTYPE_LONG, HEALTH_BOOST_POTIONTYPE_STRONG
      );
      PotionHelper.addMix(FREEZING_POTIONTYPE_MAIN, ItemsRegister.MOLTENSTRING, FREEZE_IMMUNITY_POTIONTYPE_MAIN);
      PotionHelper.addMix(PotionTypes.AWKWARD, ItemsRegister.PLACODERMSCALES, PotionTypes.WATER_BREATHING);
      PotionHelper.addMix(PotionTypes.LONG_WATER_BREATHING, ItemsRegister.DUSTMAGICCRYSTAL, WATER_BREATHING_POTIONTYPE_VERYLONG);
      PotionHelper.addMix(PotionTypes.LONG_NIGHT_VISION, ItemsRegister.DUSTMAGICCRYSTAL, NIGHT_VISION_POTIONTYPE_VERYLONG);
   }

   public static void addMixWithRedstoneAndGlowsone(
      PotionType input, Item ingridient, PotionType output, @Nullable PotionType outputLONG, @Nullable PotionType outputSTRONG
   ) {
      PotionHelper.addMix(input, ingridient, output);
      if (outputLONG != null) {
         PotionHelper.addMix(output, Items.REDSTONE, outputLONG);
      }

      if (outputSTRONG != null) {
         PotionHelper.addMix(output, Items.GLOWSTONE_DUST, outputSTRONG);
      }
   }

   @SideOnly(Side.CLIENT)
   public static void addPotionTooltip(ItemStack itemIn, List<String> lores, float durationFactor) {
      List<PotionEffect> list = PotionUtils.getEffectsFromStack(itemIn);
      List<Tuple<String, AttributeModifier>> list1 = Lists.newArrayList();
      if (list.isEmpty()) {
         String s = I18n.translateToLocal("effect.none").trim();
         lores.add(TextFormatting.GRAY + s);
      } else {
         for (PotionEffect potioneffect : list) {
            String s1 = I18n.translateToLocal(potioneffect.getEffectName()).trim();
            Potion potion = potioneffect.getPotion();
            Map<IAttribute, AttributeModifier> map = potion.getAttributeModifierMap();
            if (!map.isEmpty()) {
               for (Entry<IAttribute, AttributeModifier> entry : map.entrySet()) {
                  AttributeModifier attributemodifier = entry.getValue();
                  AttributeModifier attributemodifier1 = new AttributeModifier(
                     attributemodifier.getName(), potion.getAttributeModifierAmount(potioneffect.getAmplifier(), attributemodifier), attributemodifier.getOperation()
                  );
                  list1.add(new Tuple(entry.getKey().getName(), attributemodifier1));
               }
            }

            if (potioneffect.getAmplifier() > 0) {
               s1 = s1 + " " + (1 + potioneffect.getAmplifier());
            }

            if (potioneffect.getDuration() > 10) {
               s1 = s1 + " (" + Potion.getPotionDurationString(potioneffect, durationFactor) + ")";
            }

            if (potion.isBadEffect()) {
               lores.add(TextFormatting.RED + s1);
            } else {
               lores.add(TextFormatting.BLUE + s1);
            }
         }
      }

      if (!list1.isEmpty()) {
         lores.add("");
         lores.add(TextFormatting.DARK_PURPLE + I18n.translateToLocal("potion.whenDrank"));

         for (Tuple<String, AttributeModifier> tuple : list1) {
            AttributeModifier attributemodifier2 = (AttributeModifier)tuple.getSecond();
            double d0 = attributemodifier2.getAmount();
            double d1;
            if (attributemodifier2.getOperation() != 1 && attributemodifier2.getOperation() != 2) {
               d1 = attributemodifier2.getAmount();
            } else {
               d1 = attributemodifier2.getAmount() * 100.0;
            }

            if (d0 > 0.0) {
               lores.add(
                  TextFormatting.BLUE
                     + I18n.translateToLocalFormatted(
                        "attribute.modifier.plus." + attributemodifier2.getOperation(),
                        new Object[]{ItemStack.DECIMALFORMAT.format(d1), I18n.translateToLocal("attribute.name." + (String)tuple.getFirst())}
                     )
               );
            } else if (d0 < 0.0) {
               d1 *= -1.0;
               lores.add(
                  TextFormatting.RED
                     + I18n.translateToLocalFormatted(
                        "attribute.modifier.take." + attributemodifier2.getOperation(),
                        new Object[]{ItemStack.DECIMALFORMAT.format(d1), I18n.translateToLocal("attribute.name." + (String)tuple.getFirst())}
                     )
               );
            }
         }
      }
   }
}
