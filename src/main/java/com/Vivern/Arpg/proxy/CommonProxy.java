package com.Vivern.Arpg.proxy;

import com.Vivern.Arpg.blocks.BurningFrost;
import com.Vivern.Arpg.blocks.DemonicFire;
import com.Vivern.Arpg.blocks.RedPepperVine;
import com.Vivern.Arpg.container.GuiHandler;
import com.Vivern.Arpg.dimensions.dungeon.DimensionDungeon;
import com.Vivern.Arpg.dimensions.generationutils.CustomOreGenerator;
import com.Vivern.Arpg.dimensions.generationutils.GeneratorsRegister;
import com.Vivern.Arpg.dimensions.generationutils.OreBlockMatcher;
import com.Vivern.Arpg.dimensions.generationutils.OreGenBauxite;
import com.Vivern.Arpg.dimensions.generationutils.OreGenChromium;
import com.Vivern.Arpg.dimensions.generationutils.OreGenZinc;
import com.Vivern.Arpg.dimensions.generationutils.WorldGenFieryBeans;
import com.Vivern.Arpg.dimensions.generationutils.WorldGenHorribleVillage;
import com.Vivern.Arpg.dimensions.generationutils.WorldGenMagmaBloom;
import com.Vivern.Arpg.dimensions.generationutils.WorldGenManaHealthFlowers;
import com.Vivern.Arpg.dimensions.generationutils.WorldGenMoltenSpikes;
import com.Vivern.Arpg.dimensions.generationutils.WorldGenMushroomSpawner;
import com.Vivern.Arpg.dimensions.generationutils.WorldGenNPC;
import com.Vivern.Arpg.dimensions.generationutils.WorldGenPetroleum;
import com.Vivern.Arpg.dimensions.generationutils.WorldGenRoofVines;
import com.Vivern.Arpg.dimensions.generationutils.WorldGenSulfur;
import com.Vivern.Arpg.dimensions.generationutils.WorldGenVoidCrystals;
import com.Vivern.Arpg.elements.AnimationCapabilityProvider;
import com.Vivern.Arpg.elements.CryonedBlock;
import com.Vivern.Arpg.elements.IWeapon;
import com.Vivern.Arpg.elements.ItemLootCase;
import com.Vivern.Arpg.entity.AbstractArrow;
import com.Vivern.Arpg.entity.AdvancedFallingBlock;
import com.Vivern.Arpg.entity.AppleEffect;
import com.Vivern.Arpg.entity.AzureOreShoot;
import com.Vivern.Arpg.entity.BigLightningStrike;
import com.Vivern.Arpg.entity.BilebiterHomingShoot;
import com.Vivern.Arpg.entity.BilebiterShoot;
import com.Vivern.Arpg.entity.BlocksDecayer;
import com.Vivern.Arpg.entity.BloodDrop;
import com.Vivern.Arpg.entity.BlowholeShoot;
import com.Vivern.Arpg.entity.BubbleFishShoot;
import com.Vivern.Arpg.entity.CannonSnowball;
import com.Vivern.Arpg.entity.CeratargetShoot;
import com.Vivern.Arpg.entity.ChlorineCloud;
import com.Vivern.Arpg.entity.CoralPolyp;
import com.Vivern.Arpg.entity.CoralRifleBullet;
import com.Vivern.Arpg.entity.CryoDestroyerSpray;
import com.Vivern.Arpg.entity.CryoGunEntity;
import com.Vivern.Arpg.entity.CrystalFanBonus;
import com.Vivern.Arpg.entity.CrystalFanShoot;
import com.Vivern.Arpg.entity.CrystalStarPoweredShoot;
import com.Vivern.Arpg.entity.CrystalStarShoot;
import com.Vivern.Arpg.entity.CustomMobProjectile;
import com.Vivern.Arpg.entity.DragonFireworkEntity;
import com.Vivern.Arpg.entity.ElementProjectile;
import com.Vivern.Arpg.entity.EnderSeerProjectile;
import com.Vivern.Arpg.entity.EnigmateTwinsShoot;
import com.Vivern.Arpg.entity.EntityAcidBomb;
import com.Vivern.Arpg.entity.EntityAcidFire;
import com.Vivern.Arpg.entity.EntityArcaneExplode;
import com.Vivern.Arpg.entity.EntityArrowBengal;
import com.Vivern.Arpg.entity.EntityArrowBouncing;
import com.Vivern.Arpg.entity.EntityArrowFirejet;
import com.Vivern.Arpg.entity.EntityArrowFish;
import com.Vivern.Arpg.entity.EntityArrowFrozen;
import com.Vivern.Arpg.entity.EntityArrowMithril;
import com.Vivern.Arpg.entity.EntityArrowModern;
import com.Vivern.Arpg.entity.EntityArrowShell;
import com.Vivern.Arpg.entity.EntityArrowTwin;
import com.Vivern.Arpg.entity.EntityArrowVicious;
import com.Vivern.Arpg.entity.EntityArrowVoid;
import com.Vivern.Arpg.entity.EntityArrowWind;
import com.Vivern.Arpg.entity.EntityBogFlower;
import com.Vivern.Arpg.entity.EntityBoomerangMagic;
import com.Vivern.Arpg.entity.EntityBossLoot;
import com.Vivern.Arpg.entity.EntityButterfly;
import com.Vivern.Arpg.entity.EntityChainMace;
import com.Vivern.Arpg.entity.EntityChair;
import com.Vivern.Arpg.entity.EntityCoin;
import com.Vivern.Arpg.entity.EntityCrystalCutter;
import com.Vivern.Arpg.entity.EntityElectricAcidRadiationPotion;
import com.Vivern.Arpg.entity.EntityElectricBolt;
import com.Vivern.Arpg.entity.EntityFiremageSetBonus;
import com.Vivern.Arpg.entity.EntityFlyApple;
import com.Vivern.Arpg.entity.EntityFrostBolt;
import com.Vivern.Arpg.entity.EntityFrostfireExplosive;
import com.Vivern.Arpg.entity.EntityGeomanticCrystal;
import com.Vivern.Arpg.entity.EntityGrenade;
import com.Vivern.Arpg.entity.EntityHangingAllSides;
import com.Vivern.Arpg.entity.EntityHeadShooter;
import com.Vivern.Arpg.entity.EntityIchor;
import com.Vivern.Arpg.entity.EntityLaserDisk;
import com.Vivern.Arpg.entity.EntityLaunchedRocket;
import com.Vivern.Arpg.entity.EntityLiveHeart;
import com.Vivern.Arpg.entity.EntityMagicRocket;
import com.Vivern.Arpg.entity.EntityMagneticField;
import com.Vivern.Arpg.entity.EntityMiniNuke;
import com.Vivern.Arpg.entity.EntityMinigunIcicle;
import com.Vivern.Arpg.entity.EntityPlacedItem;
import com.Vivern.Arpg.entity.EntityRestlessSkull;
import com.Vivern.Arpg.entity.EntitySand;
import com.Vivern.Arpg.entity.EntitySeaEffloresce;
import com.Vivern.Arpg.entity.EntityShard;
import com.Vivern.Arpg.entity.EntitySlimeBullet;
import com.Vivern.Arpg.entity.EntitySnapball;
import com.Vivern.Arpg.entity.EntitySnowflakeShuriken;
import com.Vivern.Arpg.entity.EntitySpellForgeCatalyst;
import com.Vivern.Arpg.entity.EntityStingerShard;
import com.Vivern.Arpg.entity.EntitySummon;
import com.Vivern.Arpg.entity.EntitySunrise;
import com.Vivern.Arpg.entity.EntitySwordGhost;
import com.Vivern.Arpg.entity.EntityThistleThorn;
import com.Vivern.Arpg.entity.EntityThrowedReaper;
import com.Vivern.Arpg.entity.EntityTimelessSword;
import com.Vivern.Arpg.entity.EntityVampireKnife;
import com.Vivern.Arpg.entity.EntityWeatherRocket;
import com.Vivern.Arpg.entity.FireworkEntity;
import com.Vivern.Arpg.entity.FishingHook;
import com.Vivern.Arpg.entity.GemStaffShoot;
import com.Vivern.Arpg.entity.GraveLurkerProjectile;
import com.Vivern.Arpg.entity.HadronBlasterShoot;
import com.Vivern.Arpg.entity.IchorEffect;
import com.Vivern.Arpg.entity.LavaDropperShoot;
import com.Vivern.Arpg.entity.LightningStrike;
import com.Vivern.Arpg.entity.LordOfPainSpike;
import com.Vivern.Arpg.entity.NailGunShoot;
import com.Vivern.Arpg.entity.NetherGrinderBullet;
import com.Vivern.Arpg.entity.ParticleGore;
import com.Vivern.Arpg.entity.PistolFishStrike;
import com.Vivern.Arpg.entity.PlasmaAcceleratorShoot;
import com.Vivern.Arpg.entity.PlasmaPistolShoot;
import com.Vivern.Arpg.entity.PlasmaRailgunShoot;
import com.Vivern.Arpg.entity.PlasmaRifleBall;
import com.Vivern.Arpg.entity.SharkRocket;
import com.Vivern.Arpg.entity.ShellShard;
import com.Vivern.Arpg.entity.ShootOfWitherdry;
import com.Vivern.Arpg.entity.SnowstormEntity;
import com.Vivern.Arpg.entity.SpikedBurst;
import com.Vivern.Arpg.entity.StaffFireballEntity;
import com.Vivern.Arpg.entity.StingerBoltEntity;
import com.Vivern.Arpg.entity.StingingCellEntity;
import com.Vivern.Arpg.entity.SurvivorLootSpawner;
import com.Vivern.Arpg.entity.ThornkeeperShoot;
import com.Vivern.Arpg.entity.ToxicNuke;
import com.Vivern.Arpg.entity.ToxicNukeShard;
import com.Vivern.Arpg.entity.VacuumGunShoot;
import com.Vivern.Arpg.entity.ViolenceShoot;
import com.Vivern.Arpg.entity.WandColdShoot;
import com.Vivern.Arpg.entity.WandColdWave;
import com.Vivern.Arpg.entity.WhispersShoot;
import com.Vivern.Arpg.entity.XmassBall;
import com.Vivern.Arpg.entity.XmassRocket;
import com.Vivern.Arpg.main.AlchemicLabRecipesRegister;
import com.Vivern.Arpg.main.AssemblyTableRecipesRegister;
import com.Vivern.Arpg.main.BiomesRegister;
import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.DimensionsRegister;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.FluidsRegister;
import com.Vivern.Arpg.main.IndustrialMixerRecipesRegister;
import com.Vivern.Arpg.main.IntegrationHelper;
import com.Vivern.Arpg.main.ItemsElements;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Main;
import com.Vivern.Arpg.main.MobSpawn;
import com.Vivern.Arpg.main.NetherMelterRecipesRegister;
import com.Vivern.Arpg.main.OreDicHelper;
import com.Vivern.Arpg.main.ShardType;
import com.Vivern.Arpg.main.SieveRecipesRegister;
import com.Vivern.Arpg.main.Spell;
import com.Vivern.Arpg.main.SpellForgeRecipesRegister;
import com.Vivern.Arpg.main.WeaponParameters;
import com.Vivern.Arpg.mobs.HostileProjectiles;
import com.Vivern.Arpg.mobs.NPCMobsPack;
import com.Vivern.Arpg.network.PacketHandler;
import com.Vivern.Arpg.potions.AdvancedMobEffects;
import com.Vivern.Arpg.potions.PotionEffects;
import com.Vivern.Arpg.recipes.CraftingRegister;
import com.Vivern.Arpg.recipes.GeomanticFocus;
import com.Vivern.Arpg.recipes.Phenomenons;
import com.Vivern.Arpg.recipes.PyrocrystallineRecipe;
import com.Vivern.Arpg.recipes.Seal;
import com.Vivern.Arpg.recipes.Soul;
import com.Vivern.Arpg.tileentity.TileARPGChest;
import com.Vivern.Arpg.tileentity.TileAbsorptionTotem;
import com.Vivern.Arpg.tileentity.TileAdvancedBlockDetector;
import com.Vivern.Arpg.tileentity.TileAlchemicLab;
import com.Vivern.Arpg.tileentity.TileAlchemicVaporizer;
import com.Vivern.Arpg.tileentity.TileAquaticaPortal;
import com.Vivern.Arpg.tileentity.TileAssemblyAugment;
import com.Vivern.Arpg.tileentity.TileAssemblyTable;
import com.Vivern.Arpg.tileentity.TileBank;
import com.Vivern.Arpg.tileentity.TileBioCell;
import com.Vivern.Arpg.tileentity.TileBlockPlacer;
import com.Vivern.Arpg.tileentity.TileBookcase;
import com.Vivern.Arpg.tileentity.TileBunkerDoor;
import com.Vivern.Arpg.tileentity.TileCalibrationBundle;
import com.Vivern.Arpg.tileentity.TileChest;
import com.Vivern.Arpg.tileentity.TileCombinationLock;
import com.Vivern.Arpg.tileentity.TileCrystalSphere;
import com.Vivern.Arpg.tileentity.TileCrystallizer;
import com.Vivern.Arpg.tileentity.TileDisenchantmentTable;
import com.Vivern.Arpg.tileentity.TileDrill;
import com.Vivern.Arpg.tileentity.TileDungeonLadder;
import com.Vivern.Arpg.tileentity.TileDungeonPortal;
import com.Vivern.Arpg.tileentity.TileElectricSieve;
import com.Vivern.Arpg.tileentity.TileElectromagnet;
import com.Vivern.Arpg.tileentity.TileElementDistributor;
import com.Vivern.Arpg.tileentity.TileEntityFrostPortal;
import com.Vivern.Arpg.tileentity.TileEtheriteInvocator;
import com.Vivern.Arpg.tileentity.TileGlossary;
import com.Vivern.Arpg.tileentity.TileGlowingVein;
import com.Vivern.Arpg.tileentity.TileIndustrialMixer;
import com.Vivern.Arpg.tileentity.TileInfernumFurnace;
import com.Vivern.Arpg.tileentity.TileItemCharger;
import com.Vivern.Arpg.tileentity.TileMagicGenerator;
import com.Vivern.Arpg.tileentity.TileManaBottle;
import com.Vivern.Arpg.tileentity.TileManaPump;
import com.Vivern.Arpg.tileentity.TileMonsterSpawner;
import com.Vivern.Arpg.tileentity.TileNetherMelter;
import com.Vivern.Arpg.tileentity.TileNexusBeacon;
import com.Vivern.Arpg.tileentity.TileNexusFlower;
import com.Vivern.Arpg.tileentity.TileNexusNiveolite;
import com.Vivern.Arpg.tileentity.TileNexusWinterAltar;
import com.Vivern.Arpg.tileentity.TilePresentBox;
import com.Vivern.Arpg.tileentity.TilePuzzle;
import com.Vivern.Arpg.tileentity.TilePyrocrystallineConverter;
import com.Vivern.Arpg.tileentity.TileResearchTable;
import com.Vivern.Arpg.tileentity.TileRetort;
import com.Vivern.Arpg.tileentity.TileRunicMirror;
import com.Vivern.Arpg.tileentity.TileSacrificialAltar;
import com.Vivern.Arpg.tileentity.TileShimmeringBeastbloom;
import com.Vivern.Arpg.tileentity.TileSieve;
import com.Vivern.Arpg.tileentity.TileSoulCatcher;
import com.Vivern.Arpg.tileentity.TileSpellForge;
import com.Vivern.Arpg.tileentity.TileSplitter;
import com.Vivern.Arpg.tileentity.TileStarLantern;
import com.Vivern.Arpg.tileentity.TileStormledgePortal;
import com.Vivern.Arpg.tileentity.TileTeamBanner;
import com.Vivern.Arpg.tileentity.TileTopazCrystal;
import com.Vivern.Arpg.tileentity.TileToxicPortal;
import com.Vivern.Arpg.tileentity.TileTritonHearth;
import com.Vivern.Arpg.tileentity.TileVial;
import com.Vivern.Arpg.tileentity.TileVoidCrystal;
import com.Vivern.Arpg.tileentity.TileWeaponSpawner;
import com.google.common.base.Predicate;
import java.lang.reflect.Field;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockMushroom;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {
   public void preInit(FMLPreInitializationEvent event) throws IllegalArgumentException, IllegalAccessException {
      DimensionsRegister.registerDimensions();
      FluidsRegister.register();
      this.addEnchType();
      ShardType.registerElements();
      AnimationCapabilityProvider.register();
      WeaponParameters.registerAll();
      Soul.registerSoul();
      ItemsRegister.registerItems();
      BlocksRegister.registerBlocks();
      BiomesRegister.registerBiomes();
      PotionEffects.RegisterPotions();
      AdvancedMobEffects.init();
      PacketHandler.packetHandlerRegister();
      PyrocrystallineRecipe.init();
      GeomanticFocus.init();
      Seal.init();
      Phenomenons.register();
      EntityRegistry.registerModEntity(new ResourceLocation("arpg", "flyingapple"), EntityFlyApple.class, "arpg:flying_apple", 0, Main.instance, 64, 20, true);
      EntityRegistry.registerModEntity(new ResourceLocation("arpg", "lying_apple"), AppleEffect.class, "arpg:apple_effect", 1, Main.instance, 64, 20, true);
      EntityRegistry.registerModEntity(new ResourceLocation("arpg", "ichor"), EntityIchor.class, "arpg:entity_ichor", 2, Main.instance, 64, 20, true);
      EntityRegistry.registerModEntity(new ResourceLocation("arpg", "ichoreffect"), IchorEffect.class, "arpg:ichor_effect", 3, Main.instance, 64, 20, true);
      EntityRegistry.registerModEntity(new ResourceLocation("arpg", "sharkrocket"), SharkRocket.class, "arpg:shark_rocket", 4, Main.instance, 64, 1, true);
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "null"), EntityBoomerangMagic.class, "arpg:entity_boomerangmagic", 5, Main.instance, 64, 1, true
      );
      EntityRegistry.registerModEntity(new ResourceLocation("arpg", "butterfly"), EntityButterfly.class, "arpg:entity_butterfly", 6, Main.instance, 64, 1, true);
      EntityRegistry.registerModEntity(new ResourceLocation("arpg", "sunrise"), EntitySunrise.class, "arpg:entity_sunrise", 7, Main.instance, 64, 1, true);
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "vampire_knife"), EntityVampireKnife.class, "arpg:entity_vampire_knife", 8, Main.instance, 64, 10, true
      );
      EntityRegistry.registerModEntity(new ResourceLocation("arpg", "blood_drop"), BloodDrop.class, "arpg:entity_blood_drop", 9, Main.instance, 64, 1, true);
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "frost_bolt"), EntityFrostBolt.class, "arpg:entity_frost_bolt", 10, Main.instance, 64, 10, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "laser_disk"), EntityLaserDisk.class, "arpg:entity_laser_disk", 11, Main.instance, 64, 5, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "element_projectile"), ElementProjectile.class, "arpg:entity_element_projectile", 12, Main.instance, 64, 1, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "stinger_bolt"), StingerBoltEntity.class, "arpg:entity_stinger", 13, Main.instance, 64, 5, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "stinger_shard"), EntityStingerShard.class, "arpg:entity_stinger_shard", 14, Main.instance, 64, 1, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "firework_entity"), FireworkEntity.class, "arpg:entity_firework", 15, Main.instance, 64, 1, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "dragon_firework_entity"), DragonFireworkEntity.class, "arpg:entity_dragon_firework", 16, Main.instance, 64, 1, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "sand_scepter_entity"), EntitySand.class, "arpg:entity_sand", 17, Main.instance, 64, 1, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "bilebiter_shoot"), BilebiterShoot.class, "arpg:entity_bilebiter_shoot", 18, Main.instance, 64, 1, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "bilebiter_homing_shoot"), BilebiterHomingShoot.class, "arpg:entity_bilebiter_homing", 19, Main.instance, 64, 1, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "placed_item"), EntityPlacedItem.class, "arpg:entity_placed_item", 20, Main.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "electric_acid_radiation_potion"),
         EntityElectricAcidRadiationPotion.class,
         "arpg:entity_electric_acid_radiation_potion",
         21,
         Main.instance,
         64,
         20,
         true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "staff_fireball"), StaffFireballEntity.class, "arpg:entity_staff_fireball", 22, Main.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "snowstorm"), SnowstormEntity.class, "arpg:entity_snowstorm", 23, Main.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "electric_bolt"), EntityElectricBolt.class, "arpg:entity_electric_bolt", 24, Main.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "slime_bullet"), EntitySlimeBullet.class, "arpg:entity_slime_bullet", 25, Main.instance, 64, 2, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "firemage_setbonus"), EntityFiremageSetBonus.class, "arpg:entity_firemage_setbonus", 26, Main.instance, 64, 1, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "ender_seer_projectile"), EnderSeerProjectile.class, "arpg:entity_ender_seer_projectile", 27, Main.instance, 100, 1, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "fishing_hook"), FishingHook.class, "arpg:entity_fishing_hook", 28, Main.instance, 64, 1, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "nether_grinder_bullet"), NetherGrinderBullet.class, "arpg:entity_nether_grinder_bullet", 29, Main.instance, 64, 1, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "cannon_snowball"), CannonSnowball.class, "arpg:entity_cannon_snowball", 30, Main.instance, 64, 1, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "snowflake_shuriken"), EntitySnowflakeShuriken.class, "arpg:entity_snowflake_shuriken", 31, Main.instance, 64, 1, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "grave_lurker_projectile"),
         GraveLurkerProjectile.class,
         "arpg:entity_grave_lurker_projectile",
         32,
         Main.instance,
         64,
         20,
         true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "throwed_reaper"), EntityThrowedReaper.class, "arpg:entity_throwed_reaper", 33, Main.instance, 64, 1, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "sword_ghost"), EntitySwordGhost.class, "arpg:entity_sword_ghost", 34, Main.instance, 64, 1, true
      );
      EntityRegistry.registerModEntity(new ResourceLocation("arpg", "toxic_nuke"), ToxicNuke.class, "arpg:entity_toxic_nuke", 35, Main.instance, 64, 10, true);
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "toxic_nuke_shard"), ToxicNukeShard.class, "arpg:entity_toxic_nuke_shard", 36, Main.instance, 64, 10, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "cryo_gun_entity"), CryoGunEntity.class, "arpg:entity_cryo_gun", 37, Main.instance, 64, 10, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "minigun_icicle"), EntityMinigunIcicle.class, "arpg:entity_minigun_icicle", 38, Main.instance, 64, 10, true
      );
      EntityRegistry.registerModEntity(new ResourceLocation("arpg", "chair"), EntityChair.class, "arpg:entity_chair", 39, Main.instance, 64, 20, true);
      EntityRegistry.registerModEntity(new ResourceLocation("arpg", "summon"), EntitySummon.class, "arpg:entity_summon", 40, Main.instance, 64, 20, true);
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "plasma_railgun_shot"), PlasmaRailgunShoot.class, "arpg:entity_plasma_railgun_shot", 41, Main.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "plasma_rifle_ball"), PlasmaRifleBall.class, "arpg:entity_plasma_rifle_ball", 42, Main.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "head_shooter_bullet"), EntityHeadShooter.class, "arpg:entity_head_shooter_bullet", 43, Main.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "plasma_pistol_shoot"), PlasmaPistolShoot.class, "arpg:entity_plasma_pistol_shoot", 44, Main.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "bog_flower_shoot"), EntityBogFlower.class, "arpg:entity_bog_flower_shoot", 45, Main.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "pistol_fish_strike"), PistolFishStrike.class, "arpg:entity_pistol_fish_strike", 46, Main.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "bubble_fish_shoot"), BubbleFishShoot.class, "arpg:entity_bubble_fish_shoot", 47, Main.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "lava_dropper_shoot"), LavaDropperShoot.class, "arpg:entity_lava_dropper_shoot", 48, Main.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "plasma_accelerator_shoot"),
         PlasmaAcceleratorShoot.class,
         "arpg:entity_plasma_accelerator_shoot",
         49,
         Main.instance,
         64,
         20,
         true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "vacuum_gun_shoot"), VacuumGunShoot.class, "arpg:vacuum_gun_shoot", 50, Main.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(new ResourceLocation("arpg", "shard"), EntityShard.class, "arpg:entity_shard", 51, Main.instance, 64, 20, true);
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "gem_staff_shoot"), GemStaffShoot.class, "arpg:entity_gem_staff_shoot", 52, Main.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "wand_of_cold_shoot"), WandColdShoot.class, "arpg:entity_wand_of_cold_shoot", 53, Main.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "arrow_firejet"), EntityArrowFirejet.class, "arpg:entity_arrow_firejet", 54, Main.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "custom_mob_projectile"), CustomMobProjectile.class, "arpg:entity_custom_mob_projectile", 55, Main.instance, 64, 1, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "acid_fire_shoot"), EntityAcidFire.class, "arpg:entity_acid_fire", 56, Main.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "particle_gore"), ParticleGore.class, "arpg:entity_particle_gore", 57, Main.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "nail_gun_shoot"), NailGunShoot.class, "arpg:entity_nail_gun_shoot", 58, Main.instance, 64, 1, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "crystal_star_shoot"), CrystalStarShoot.class, "arpg:entity_crystal_star_shoot", 59, Main.instance, 64, 1, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "lord_of_pain_spike"), LordOfPainSpike.class, "arpg:entity_lord_of_pain_spike", 60, Main.instance, 64, 1, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "crystal_fan_shoot"), CrystalFanShoot.class, "arpg:entity_crystal_fan_shoot", 61, Main.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "crystal_fan_bonus"), CrystalFanBonus.class, "arpg:entity_crystal_fan_bonus", 62, Main.instance, 64, 2, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "geomantic_crystal"), EntityGeomanticCrystal.class, "arpg:entity_geomantic_crystal", 63, Main.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(new ResourceLocation("arpg", "coin"), EntityCoin.class, "arpg:coin", 64, Main.instance, 64, 20, true);
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "thornkeeper_shoot"), ThornkeeperShoot.class, "arpg:entity_thornkeeper_shoot", 65, Main.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "thistle_thorn_entity"), EntityThistleThorn.class, "arpg:entity_thistle_thorn", 66, Main.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "spiked_burst"), SpikedBurst.class, "arpg:entity_spiked_burst", 67, Main.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "restless_skull_shoot"), EntityRestlessSkull.class, "arpg:entity_restless_skull", 68, Main.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "magic_rocket"), EntityMagicRocket.class, "arpg:entity_magic_rocket", 69, Main.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "stinging_cell_entity"), StingingCellEntity.class, "arpg:entity_stinging_cell", 70, Main.instance, 64, 1, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "sea_effloresce_entity"), EntitySeaEffloresce.class, "arpg:entity_sea_effloresce", 71, Main.instance, 64, 1, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "coral_rifle_bullet"), CoralRifleBullet.class, "arpg:entity_coral_rifle_bullet", 72, Main.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "timeless_sword_entity"), EntityTimelessSword.class, "arpg:entity_timeless_sword", 73, Main.instance, 64, 40, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "lightning_strike"), LightningStrike.class, "arpg:entity_lightning_strike", 74, Main.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "frostfire_explosive"),
         EntityFrostfireExplosive.class,
         "arpg:entity_frostfire_explosive",
         75,
         Main.instance,
         64,
         20,
         true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "acid_bomb"), EntityAcidBomb.class, "arpg:entity_acid_bomb", 76, Main.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "acid_bomb_blob"), EntityAcidBomb.AcidBombBlob.class, "arpg:entity_acid_bomb_blob", 77, Main.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "magnetic_field"), EntityMagneticField.class, "arpg:entity_magnetic_field", 78, Main.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "hadron_shoot"), HadronBlasterShoot.class, "arpg:entity_hadron_shoot", 79, Main.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "hadron_bonus"), HadronBlasterShoot.HadronBlasterBonus.class, "arpg:entity_hadron_bonus", 80, Main.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "snapball_entity"), EntitySnapball.class, "arpg:entity_snapball", 81, Main.instance, 64, 100, false
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "launched_rocket"), EntityLaunchedRocket.class, "arpg:entity_launched_rocket", 82, Main.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "mini_nuke_entity"), EntityMiniNuke.class, "arpg:entity_mini_nuke", 83, Main.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "chlorine_cloud"), ChlorineCloud.class, "arpg:entity_chlorine_cloud", 84, Main.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "chain_mace_entity"), EntityChainMace.class, "arpg:entity_chain_mace", 85, Main.instance, 64, 1, true
      );
      EntityRegistry.registerModEntity(new ResourceLocation("arpg", "grenade"), EntityGrenade.class, "arpg:entity_grenade", 86, Main.instance, 64, 1, true);
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "advanced_falling_block"),
         AdvancedFallingBlock.class,
         "arpg:entity_advanced_falling_block",
         87,
         Main.instance,
         64,
         20,
         true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "live_heart"), EntityLiveHeart.class, "arpg:entity_live_heart", 88, Main.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "blowhole_shoot"), BlowholeShoot.class, "arpg:entity_blowhole_shoot", 89, Main.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "crystal_cutter_entity"), EntityCrystalCutter.class, "arpg:entity_crystal_cutter", 90, Main.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "ceratarget_shoot"), CeratargetShoot.class, "arpg:entity_ceratarget_shoot", 91, Main.instance, 64, 2, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "arcane_explode"), EntityArcaneExplode.class, "arpg:entity_arcane_explode", 93, Main.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "arrow_frozen"),
         EntityArrowFrozen.class,
         "arpg:entity_arrow_frozen",
         94,
         Main.instance,
         64,
         AbstractArrow.updateFrequency,
         true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "arrow_vicious"),
         EntityArrowVicious.class,
         "arpg:entity_arrow_vicious",
         95,
         Main.instance,
         64,
         AbstractArrow.updateFrequency,
         true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "arrow_modern"),
         EntityArrowModern.class,
         "arpg:entity_arrow_modern",
         96,
         Main.instance,
         64,
         AbstractArrow.updateFrequency,
         true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "arrow_bengal"),
         EntityArrowBengal.class,
         "arpg:entity_arrow_bengal",
         97,
         Main.instance,
         64,
         AbstractArrow.updateFrequency,
         true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "arrow_fish"),
         EntityArrowFish.class,
         "arpg:entity_arrow_fish",
         98,
         Main.instance,
         64,
         AbstractArrow.updateFrequency,
         true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "arrow_void"),
         EntityArrowVoid.class,
         "arpg:entity_arrow_void",
         99,
         Main.instance,
         64,
         AbstractArrow.updateFrequency,
         true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "arrow_shell"),
         EntityArrowShell.class,
         "arpg:entity_arrow_shell",
         100,
         Main.instance,
         64,
         AbstractArrow.updateFrequency,
         true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "shell_shard"), ShellShard.class, "arpg:entity_shell_shard", 101, Main.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "arrow_bouncing"),
         EntityArrowBouncing.class,
         "arpg:entity_arrow_bouncing",
         102,
         Main.instance,
         64,
         AbstractArrow.updateFrequency,
         true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "arrow_mithril"),
         EntityArrowMithril.class,
         "arpg:entity_arrow_mithril",
         103,
         Main.instance,
         64,
         AbstractArrow.updateFrequency,
         true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "arrow_twin"),
         EntityArrowTwin.class,
         "arpg:entity_arrow_twin",
         104,
         Main.instance,
         64,
         AbstractArrow.updateFrequency,
         true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "arrow_wind"),
         EntityArrowWind.class,
         "arpg:entity_arrow_wind",
         105,
         Main.instance,
         64,
         AbstractArrow.updateFrequency,
         true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "azure_ore_shoot"), AzureOreShoot.class, "arpg:entity_azure_ore_shoot", 106, Main.instance, 64, 1, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "violence_shoot"), ViolenceShoot.class, "arpg:entity_violence_shoot", 107, Main.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "shoot_of_witherdry"), ShootOfWitherdry.class, "arpg:entity_shoot_of_witherdry", 108, Main.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "whispers_shoot"), WhispersShoot.class, "arpg:entity_whispers_shoot", 109, Main.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "spell_forge_catalyst"),
         EntitySpellForgeCatalyst.class,
         "arpg:entity_spell_forge_catalyst",
         110,
         Main.instance,
         64,
         20,
         true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "xmass_rocket"), XmassRocket.class, "arpg:entity_xmass_rocket", 111, Main.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(new ResourceLocation("arpg", "xmass_ball"), XmassBall.class, "arpg:entity_xmass_ball", 112, Main.instance, 64, 20, true);
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "coral_polyp"), CoralPolyp.class, "arpg:entity_coral_polyp", 113, Main.instance, 64, 2, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "enigmate_twins_shoot"), EnigmateTwinsShoot.class, "arpg:entity_enigmate_twins_shoot", 114, Main.instance, 64, 1, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "cryo_destroyer_spray"), CryoDestroyerSpray.class, "arpg:entity_cryo_destroyer_spray", 115, Main.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "cryoned_block"), CryonedBlock.class, "arpg:entity_cryoned_block", 116, Main.instance, 64, 20, false
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "wand_of_cold_wave"), WandColdWave.class, "arpg:entity_wand_of_cold_wave", 117, Main.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "survivor_loot_spawner"),
         SurvivorLootSpawner.class,
         "arpg:entity_survivor_loot_spawner",
         118,
         Main.instance,
         128,
         20,
         true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "crystal_star_powered_shoot"),
         CrystalStarPoweredShoot.class,
         "arpg:entity_crystal_star_powered_shoot",
         119,
         Main.instance,
         64,
         1,
         true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "weather_rocket"), EntityWeatherRocket.class, "arpg:entity_weather_rocket", 120, Main.instance, 128, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "entity_hanging_allsides"),
         EntityHangingAllSides.class,
         "arpg:entity_hanging_allsides",
         121,
         Main.instance,
         24,
         20,
         false
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "big_lightning_strike"), BigLightningStrike.class, "arpg:entity_big_lightning_strike", 122, Main.instance, 256, 20, true
      );
      EntityRegistry.registerModEntity(new ResourceLocation("arpg", "decayer"), BlocksDecayer.class, "arpg:entity_decayer", 123, Main.instance, 64, 20, false);
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "boss_loot"), EntityBossLoot.class, "arpg:entity_boss_loot", 124, Main.instance, 64, 20, true
      );
      HostileProjectiles.register();
      GameRegistry.registerTileEntity(TileEntityFrostPortal.class, new ResourceLocation("arpg", "te_frost_portal"));
      GameRegistry.registerTileEntity(TileAdvancedBlockDetector.class, new ResourceLocation("arpg", "te_advanced_block_detector"));
      GameRegistry.registerTileEntity(TileBlockPlacer.class, new ResourceLocation("arpg", "te_block_placer"));
      GameRegistry.registerTileEntity(TileGlowingVein.class, new ResourceLocation("arpg", "te_glowing_vein"));
      GameRegistry.registerTileEntity(TileCrystalSphere.class, new ResourceLocation("arpg", "te_crystal_sphere"));
      GameRegistry.registerTileEntity(TileSpellForge.class, new ResourceLocation("arpg", "te_spell_forge"));
      GameRegistry.registerTileEntity(TileManaBottle.class, new ResourceLocation("arpg", "te_mana_bottle"));
      GameRegistry.registerTileEntity(TileTopazCrystal.class, new ResourceLocation("arpg", "te_topaz_crystal"));
      GameRegistry.registerTileEntity(TileNetherMelter.class, new ResourceLocation("arpg", "te_nether_melter"));
      GameRegistry.registerTileEntity(TileSacrificialAltar.class, new ResourceLocation("arpg", "te_sacrificial_altar"));
      GameRegistry.registerTileEntity(TileInfernumFurnace.class, new ResourceLocation("arpg", "te_infernum_furnace"));
      GameRegistry.registerTileEntity(TileAlchemicLab.class, new ResourceLocation("arpg", "te_alchemic_lab"));
      GameRegistry.registerTileEntity(TileAlchemicVaporizer.class, new ResourceLocation("arpg", "te_alchemic_vaporizer"));
      GameRegistry.registerTileEntity(TilePuzzle.class, new ResourceLocation("arpg", "te_puzzle"));
      GameRegistry.registerTileEntity(TileCombinationLock.class, new ResourceLocation("arpg", "te_combination_lock"));
      GameRegistry.registerTileEntity(TileChest.class, new ResourceLocation("arpg", "te_chest"));
      GameRegistry.registerTileEntity(TileRunicMirror.class, new ResourceLocation("arpg", "te_runic_mirror"));
      GameRegistry.registerTileEntity(TileCrystallizer.class, new ResourceLocation("arpg", "te_crystallizer"));
      GameRegistry.registerTileEntity(TilePyrocrystallineConverter.class, new ResourceLocation("arpg", "te_pyrocrystalline_converter"));
      GameRegistry.registerTileEntity(TileDrill.class, new ResourceLocation("arpg", "te_drill"));
      GameRegistry.registerTileEntity(TileAssemblyTable.class, new ResourceLocation("arpg", "te_assembly_table"));
      GameRegistry.registerTileEntity(TileWeaponSpawner.class, new ResourceLocation("arpg", "te_weapon_spawner"));
      GameRegistry.registerTileEntity(TileStarLantern.class, new ResourceLocation("arpg", "te_star_lantern"));
      GameRegistry.registerTileEntity(TilePresentBox.class, new ResourceLocation("arpg", "te_present_box"));
      GameRegistry.registerTileEntity(TileElectromagnet.class, new ResourceLocation("arpg", "te_electromagnet"));
      GameRegistry.registerTileEntity(TileShimmeringBeastbloom.class, new ResourceLocation("arpg", "te_shimmering_beastbloom"));
      GameRegistry.registerTileEntity(TileBioCell.class, new ResourceLocation("arpg", "te_bio_cell"));
      GameRegistry.registerTileEntity(TileNexusFlower.class, new ResourceLocation("arpg", "te_nexus_flower"));
      GameRegistry.registerTileEntity(TileMonsterSpawner.class, new ResourceLocation("arpg", "te_monster_spawner"));
      GameRegistry.registerTileEntity(TileBunkerDoor.class, new ResourceLocation("arpg", "te_bunker_door"));
      GameRegistry.registerTileEntity(TileNexusWinterAltar.class, new ResourceLocation("arpg", "te_nexus_winter_altar"));
      GameRegistry.registerTileEntity(TileDungeonLadder.class, new ResourceLocation("arpg", "te_dungeon_ladder"));
      GameRegistry.registerTileEntity(TileItemCharger.class, new ResourceLocation("arpg", "te_item_charger"));
      GameRegistry.registerTileEntity(TileToxicPortal.class, new ResourceLocation("arpg", "te_toxic_portal"));
      GameRegistry.registerTileEntity(TileDungeonPortal.class, new ResourceLocation("arpg", "te_dungeon_portal"));
      GameRegistry.registerTileEntity(TileVoidCrystal.class, new ResourceLocation("arpg", "te_void_crystal"));
      GameRegistry.registerTileEntity(TileNexusBeacon.class, new ResourceLocation("arpg", "te_nexus_beacon"));
      GameRegistry.registerTileEntity(TileIndustrialMixer.class, new ResourceLocation("arpg", "te_industrial_mixer"));
      GameRegistry.registerTileEntity(TileResearchTable.class, new ResourceLocation("arpg", "te_research_table"));
      GameRegistry.registerTileEntity(TileElementDistributor.class, new ResourceLocation("arpg", "te_creative_element_distributor"));
      GameRegistry.registerTileEntity(TileSplitter.class, new ResourceLocation("arpg", "te_splitter"));
      GameRegistry.registerTileEntity(TileRetort.class, new ResourceLocation("arpg", "te_retort"));
      GameRegistry.registerTileEntity(TileVial.class, new ResourceLocation("arpg", "te_vial"));
      GameRegistry.registerTileEntity(TileBookcase.class, new ResourceLocation("arpg", "te_bookcase"));
      GameRegistry.registerTileEntity(TileGlossary.class, new ResourceLocation("arpg", "te_glossary"));
      GameRegistry.registerTileEntity(TileAbsorptionTotem.class, new ResourceLocation("arpg", "te_absorption_totem"));
      GameRegistry.registerTileEntity(TileDisenchantmentTable.class, new ResourceLocation("arpg", "te_disenchantment_table"));
      GameRegistry.registerTileEntity(TileTritonHearth.class, new ResourceLocation("arpg", "te_triton_hearth"));
      GameRegistry.registerTileEntity(TileAssemblyAugment.class, new ResourceLocation("arpg", "te_assembly_augment"));
      GameRegistry.registerTileEntity(TileBank.class, new ResourceLocation("arpg", "te_bank"));
      GameRegistry.registerTileEntity(TileNexusNiveolite.class, new ResourceLocation("arpg", "te_nexus_niveolite"));
      GameRegistry.registerTileEntity(TileCalibrationBundle.class, new ResourceLocation("arpg", "te_calibration_bundle"));
      GameRegistry.registerTileEntity(TileSoulCatcher.class, new ResourceLocation("arpg", "te_soul_catcher"));
      GameRegistry.registerTileEntity(TileManaPump.class, new ResourceLocation("arpg", "te_mana_pump"));
      GameRegistry.registerTileEntity(TileSieve.class, new ResourceLocation("arpg", "te_sieve"));
      GameRegistry.registerTileEntity(TileElectricSieve.class, new ResourceLocation("arpg", "te_electric_sieve"));
      GameRegistry.registerTileEntity(TileARPGChest.class, new ResourceLocation("arpg", "te_arpg_chest"));
      GameRegistry.registerTileEntity(TileAquaticaPortal.class, new ResourceLocation("arpg", "te_aquatica_portal"));
      GameRegistry.registerTileEntity(TileStormledgePortal.class, new ResourceLocation("arpg", "te_stormledge_portal"));
      GameRegistry.registerTileEntity(TileMagicGenerator.class, new ResourceLocation("arpg", "te_magic_generator"));
      GameRegistry.registerTileEntity(TileEtheriteInvocator.class, new ResourceLocation("arpg", "te_etherite_invocator"));
      GameRegistry.registerTileEntity(TileTeamBanner.class, new ResourceLocation("arpg", "te_team_banner"));
   }

   public void init(FMLInitializationEvent event) {
      DimensionsRegister.registerTeleporters();
      OreDicHelper.init();
      OreBlockMatcher matcherNether = new OreBlockMatcher(new Block[]{Blocks.NETHERRACK});
      OreBlockMatcher matcherOverworld = new OreBlockMatcher(new Block[]{Blocks.STONE});
      OreBlockMatcher matcherOverwNethToxi = new OreBlockMatcher(new Block[]{Blocks.STONE, Blocks.NETHERRACK, BlocksRegister.RADIOSTONE});
      OreBlockMatcher matcherLava = new OreBlockMatcher(new Block[]{Blocks.LAVA, Blocks.FLOWING_LAVA});
      registerWorldGen(new CustomOreGenerator(BlocksRegister.OREDEMONITE.getDefaultState(), -1, matcherNether, 1, 4, 9, 0, 128));
      registerWorldGen(new CustomOreGenerator(BlocksRegister.OREINFERNUM.getDefaultState(), -1, matcherNether, 2, 8, 14, 0, 128));
      registerWorldGen(new CustomOreGenerator(BlocksRegister.OREMOLTEN.getDefaultState(), -1, matcherNether, 1, 2, 6, 0, 128));
      registerWorldGen(new CustomOreGenerator(BlocksRegister.ORERUBY.getDefaultState(), 0, matcherOverworld, 4, 8, 2, 0, 36));
      registerWorldGen(new CustomOreGenerator(BlocksRegister.ORESAPPHIRE.getDefaultState(), 0, matcherOverworld, 4, 8, 2, 0, 36));
      registerWorldGen(new CustomOreGenerator(BlocksRegister.ORECITRINE.getDefaultState(), 0, matcherOverworld, 4, 8, 2, 0, 36));
      registerWorldGen(new CustomOreGenerator(BlocksRegister.ORETOPAZ.getDefaultState(), 0, matcherOverworld, 3, 8, 3, 0, 48));
      registerWorldGen(new CustomOreGenerator(BlocksRegister.OREAMETHYST.getDefaultState(), 0, matcherOverworld, 3, 9, 3, 0, 48));
      registerWorldGen(new CustomOreGenerator(BlocksRegister.ORERHINESTONE.getDefaultState(), 0, matcherOverworld, 5, 10, 6, 10, 84));
      registerWorldGen(new CustomOreGenerator(BlocksRegister.ORESILVER.getDefaultState(), 0, matcherOverworld, 3, 8, 5, 0, 58));
      registerWorldGen(new CustomOreGenerator(BlocksRegister.ORESALT.getDefaultState(), 0, matcherOverworld, 3, 10, 3, 25, 64));
      registerWorldGen(new CustomOreGenerator(BlocksRegister.ORETITANIUM.getDefaultState(), 0, matcherOverworld, 1, 5, 1, 0, 20));
      registerWorldGen(new CustomOreGenerator(BlocksRegister.ORETITANIUMRADIOACTIVE.getDefaultState(), 101, matcherOverwNethToxi, 1, 6, 2, 0, 32));
      registerWorldGen(new CustomOreGenerator(BlocksRegister.ORETITANIUM.getDefaultState(), 102, matcherOverworld, 1, 6, 16, 0, 255));
      registerWorldGen(
         new OreGenChromium(
               BlocksRegister.ORECHROMIUM.getDefaultState(), 0, new OreBlockMatcher(new Block[]{Blocks.STONE, Blocks.AIR}), 3, 7, 2, 6, 43
            )
            .setGeode(
               0.3F,
               Blocks.IRON_ORE.getDefaultState(),
               Blocks.LAVA.getDefaultState(),
               Blocks.WATER.getDefaultState(),
               OreDicHelper.getBlock("oreLead")
            )
      );
      registerWorldGen(
         new OreGenZinc(BlocksRegister.OREZINC.getDefaultState(), 0, matcherOverworld, 26, 64, 0.35F, 8, 56)
            .setFalsiveBlocks(
               0.26F,
               Blocks.MONSTER_EGG.getDefaultState(),
               Blocks.FLOWING_LAVA.getDefaultState(),
               Blocks.OBSIDIAN.getDefaultState(),
               Blocks.GRAVEL.getDefaultState()
            )
      );
      registerWorldGen(
         new OreGenBauxite(BlocksRegister.OREALUMINIUM.getDefaultState(), BlocksRegister.BAUXITEBLOCK.getDefaultState(), 0, matcherOverworld, 14, 45, 0.5F, 40, 75)
      );
      registerWorldGen(new WorldGenFieryBeans());
      registerWorldGen(
         new WorldGenSulfur(
            BlocksRegister.SULFURCRYSTAL.getDefaultState(),
            BlocksRegister.FLUIDSULFURICGAS.getDefaultState(),
            0.4F,
            new int[]{0, -1, 101},
            matcherOverwNethToxi,
            16,
            46,
            0.6F,
            10,
            90
         )
      );
      registerWorldGen(new WorldGenMagmaBloom(BlocksRegister.MAGMABLOOM.getDefaultState(), -1, matcherLava, 3, 10, 0.05F, 14, 50, 2));
      registerWorldGen(
         new WorldGenRoofVines(BlocksRegister.REDPEPPERVINE.getDefaultState().withProperty(RedPepperVine.FRUITAGE, false), 3, -1, 80, 120, 2, 8, 6, 4)
      );
      registerWorldGen(new WorldGenVoidCrystals());
      registerWorldGen(
         new WorldGenPetroleum(
               BlocksRegister.FLUIDPETROLEUM.getDefaultState(),
               Blocks.STONE.getDefaultState(),
               BlocksRegister.FLUIDNATURALGAS.getDefaultState(),
               0,
               WorldGenPetroleum.defaultBlocksToReplace,
               22,
               8,
               15,
               0.65F,
               false
            )
            .setOptions(0.08, 0.04, 4.0, 2.0, 0.4, 0.6, 0.8, 1.2, 0.2, 0.8)
      );
      registerWorldGen(
         new WorldGenPetroleum(
               BlocksRegister.FLUIDNATURALGAS.getDefaultState(),
               Blocks.STONE.getDefaultState(),
               null,
               0,
               WorldGenPetroleum.defaultBlocksToReplace,
               23,
               9,
               17,
               0.0F,
               false
            )
            .setOptions(0.08, 0.04, 4.0, 2.0, 0.2, 0.5, 1.0, 1.1, 0.14, 0.45)
      );
      registerWorldGen(new WorldGenHorribleVillage());
      registerWorldGen(new WorldGenManaHealthFlowers(false));
      registerWorldGen(new WorldGenManaHealthFlowers(true));
      registerWorldGen(new WorldGenNPC(EntityRegistry.getEntry(NPCMobsPack.NpcMerchant.class), ":npchome_merchant", 20, 0, true));
      registerWorldGen(new WorldGenNPC(EntityRegistry.getEntry(NPCMobsPack.NpcWizard.class), ":npchome_wizard", 1050, 0, false));
      registerWorldGen(new WorldGenNPC(EntityRegistry.getEntry(NPCMobsPack.NpcMechanic.class), ":npchome_mechanic", 1050, 0, false));
      if (Blocks.BROWN_MUSHROOM instanceof BlockMushroom && Blocks.RED_MUSHROOM instanceof BlockMushroom) {
         registerWorldGen(
            new WorldGenMushroomSpawner((BlockMushroom)Blocks.BROWN_MUSHROOM, (BlockMushroom)Blocks.RED_MUSHROOM, Blocks.MYCELIUM, 10, 0, 0.003F)
         );
      }

      GameRegistry.registerWorldGenerator(new WorldGenMoltenSpikes(), 18930414);
      NetworkRegistry.INSTANCE.registerGuiHandler(Main.instance, new GuiHandler());
      TileAlchemicVaporizer.init();
      CraftingRegister.register();
      CraftingRegister.addSmeltingRecipes();
      PotionEffects.RegisterPotionRecipes();
      DimensionDungeon.registerAllDecors();
   }

   public static void registerWorldGen(IWorldGenerator generator) {
      GeneratorsRegister.generators.add(generator);
   }

   public void postInit(FMLPostInitializationEvent event) {
      System.out.println("arpg | Adding enchants to lootboxes");
      ((ItemLootCase)ItemsRegister.LOOTBOXENCHANTALL).entries = ItemLootCase.entriesAllEnch();
      ((ItemLootCase)ItemsRegister.LOOTBOXENCHANTWEAPON).entries = ItemLootCase.entriesWeaponEnch();
      ((ItemLootCase)ItemsRegister.LOOTBOXENCHANTSIMPLE).entries = ItemLootCase.entriesSimpleEnch();
      System.out.println("arpg | Init new flaming blocks");
      ((DemonicFire)BlocksRegister.DEMONICFIRE).init();
      ((BurningFrost)BlocksRegister.BURNINGFROST).init();
      System.out.println("arpg | Init advanced recipes");
      AssemblyTableRecipesRegister.register();
      SpellForgeRecipesRegister.register();
      NetherMelterRecipesRegister.register();
      AlchemicLabRecipesRegister.register();
      IndustrialMixerRecipesRegister.register();
      SieveRecipesRegister.register();
      System.out.println("arpg | Init mob spawn");
      MobSpawn.init();
      System.out.println("arpg | Post init fliuds");
      FluidsRegister.postInitFluids();
      System.out.println("arpg | Init items magic elements");
      ItemsElements.init();
      System.out.println("arpg | Init spells");
      Spell.init();
      System.out.println("arpg | Changing water opacity");
      Blocks.WATER.setLightOpacity(1);
      Blocks.FLOWING_WATER.setLightOpacity(1);
      if (Item.getByNameOrId("enderio:item_soul_vial") != null) {
         Main.ENDERIO_installed = true;
      }

      System.out.println("arpg | Applying reflection to EntityThrowable.ticksInAir");
      Field[] fields = EntityThrowable.class.getDeclaredFields();

      for (Field field : fields) {
         if (field.getName() == "ticksInAir" && !field.isAccessible()) {
            field.setAccessible(true);
         }
      }

      System.out.println("arpg | Applying reflection to GlStateManager.activeTextureUnit");
      Field[] fieldss = GlStateManager.class.getDeclaredFields();

      for (Field fieldx : fieldss) {
         if (fieldx.getName() == "activeTextureUnit" && !fieldx.isAccessible()) {
            fieldx.setAccessible(true);
         }
      }

      System.out.println("arpg | Adding other mods integration recipes");
      IntegrationHelper.addRecipes();
      System.out.println("arpg | Post init end");
   }

   public void addEnchType() {
      EnchantmentInit.enchantmentTypeWeapon = EnumHelper.addEnchantmentType("i_weapon", new Predicate<Item>() {
         public boolean apply(@Nullable Item item) {
            return item == null ? false : item instanceof IWeapon;
         }
      });
   }
}
