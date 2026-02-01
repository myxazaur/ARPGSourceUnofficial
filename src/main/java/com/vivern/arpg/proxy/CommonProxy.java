package com.vivern.arpg.proxy;

import com.vivern.arpg.blocks.BurningFrost;
import com.vivern.arpg.blocks.DemonicFire;
import com.vivern.arpg.blocks.RedPepperVine;
import com.vivern.arpg.container.GuiHandler;
import com.vivern.arpg.dimensions.dungeon.DimensionDungeon;
import com.vivern.arpg.dimensions.generationutils.CustomOreGenerator;
import com.vivern.arpg.dimensions.generationutils.GeneratorsRegister;
import com.vivern.arpg.dimensions.generationutils.OreBlockMatcher;
import com.vivern.arpg.dimensions.generationutils.OreGenBauxite;
import com.vivern.arpg.dimensions.generationutils.OreGenChromium;
import com.vivern.arpg.dimensions.generationutils.OreGenZinc;
import com.vivern.arpg.dimensions.generationutils.WorldGenFieryBeans;
import com.vivern.arpg.dimensions.generationutils.WorldGenHorribleVillage;
import com.vivern.arpg.dimensions.generationutils.WorldGenMagmaBloom;
import com.vivern.arpg.dimensions.generationutils.WorldGenManaHealthFlowers;
import com.vivern.arpg.dimensions.generationutils.WorldGenMoltenSpikes;
import com.vivern.arpg.dimensions.generationutils.WorldGenMushroomSpawner;
import com.vivern.arpg.dimensions.generationutils.WorldGenNPC;
import com.vivern.arpg.dimensions.generationutils.WorldGenPetroleum;
import com.vivern.arpg.dimensions.generationutils.WorldGenRoofVines;
import com.vivern.arpg.dimensions.generationutils.WorldGenSulfur;
import com.vivern.arpg.dimensions.generationutils.WorldGenVoidCrystals;
import com.vivern.arpg.elements.AnimationCapabilityProvider;
import com.vivern.arpg.elements.CryonedBlock;
import com.vivern.arpg.elements.IWeapon;
import com.vivern.arpg.elements.ItemLootCase;
import com.vivern.arpg.entity.AbstractArrow;
import com.vivern.arpg.entity.AdvancedFallingBlock;
import com.vivern.arpg.entity.AppleEffect;
import com.vivern.arpg.entity.AzureOreShoot;
import com.vivern.arpg.entity.BigLightningStrike;
import com.vivern.arpg.entity.BilebiterHomingShoot;
import com.vivern.arpg.entity.BilebiterShoot;
import com.vivern.arpg.entity.BlocksDecayer;
import com.vivern.arpg.entity.BloodDrop;
import com.vivern.arpg.entity.BlowholeShoot;
import com.vivern.arpg.entity.BubbleFishShoot;
import com.vivern.arpg.entity.CannonSnowball;
import com.vivern.arpg.entity.CeratargetShoot;
import com.vivern.arpg.entity.ChlorineCloud;
import com.vivern.arpg.entity.CoralPolyp;
import com.vivern.arpg.entity.CoralRifleBullet;
import com.vivern.arpg.entity.CryoDestroyerSpray;
import com.vivern.arpg.entity.CryoGunEntity;
import com.vivern.arpg.entity.CrystalFanBonus;
import com.vivern.arpg.entity.CrystalFanShoot;
import com.vivern.arpg.entity.CrystalStarPoweredShoot;
import com.vivern.arpg.entity.CrystalStarShoot;
import com.vivern.arpg.entity.CustomMobProjectile;
import com.vivern.arpg.entity.DragonFireworkEntity;
import com.vivern.arpg.entity.ElementProjectile;
import com.vivern.arpg.entity.EnderSeerProjectile;
import com.vivern.arpg.entity.EnigmateTwinsShoot;
import com.vivern.arpg.entity.EntityAcidBomb;
import com.vivern.arpg.entity.EntityAcidFire;
import com.vivern.arpg.entity.EntityArcaneExplode;
import com.vivern.arpg.entity.EntityArrowBengal;
import com.vivern.arpg.entity.EntityArrowBouncing;
import com.vivern.arpg.entity.EntityArrowFirejet;
import com.vivern.arpg.entity.EntityArrowFish;
import com.vivern.arpg.entity.EntityArrowFrozen;
import com.vivern.arpg.entity.EntityArrowMithril;
import com.vivern.arpg.entity.EntityArrowModern;
import com.vivern.arpg.entity.EntityArrowShell;
import com.vivern.arpg.entity.EntityArrowTwin;
import com.vivern.arpg.entity.EntityArrowVicious;
import com.vivern.arpg.entity.EntityArrowVoid;
import com.vivern.arpg.entity.EntityArrowWind;
import com.vivern.arpg.entity.EntityBogFlower;
import com.vivern.arpg.entity.EntityBoomerangMagic;
import com.vivern.arpg.entity.EntityBossLoot;
import com.vivern.arpg.entity.EntityButterfly;
import com.vivern.arpg.entity.EntityChainMace;
import com.vivern.arpg.entity.EntityChair;
import com.vivern.arpg.entity.EntityCoin;
import com.vivern.arpg.entity.EntityCrystalCutter;
import com.vivern.arpg.entity.EntityElectricAcidRadiationPotion;
import com.vivern.arpg.entity.EntityElectricBolt;
import com.vivern.arpg.entity.EntityFiremageSetBonus;
import com.vivern.arpg.entity.EntityFlyApple;
import com.vivern.arpg.entity.EntityFrostBolt;
import com.vivern.arpg.entity.EntityFrostfireExplosive;
import com.vivern.arpg.entity.EntityGeomanticCrystal;
import com.vivern.arpg.entity.EntityGrenade;
import com.vivern.arpg.entity.EntityHangingAllSides;
import com.vivern.arpg.entity.EntityHeadShooter;
import com.vivern.arpg.entity.EntityIchor;
import com.vivern.arpg.entity.EntityLaserDisk;
import com.vivern.arpg.entity.EntityLaunchedRocket;
import com.vivern.arpg.entity.EntityLiveHeart;
import com.vivern.arpg.entity.EntityMagicRocket;
import com.vivern.arpg.entity.EntityMagneticField;
import com.vivern.arpg.entity.EntityMiniNuke;
import com.vivern.arpg.entity.EntityMinigunIcicle;
import com.vivern.arpg.entity.EntityPlacedItem;
import com.vivern.arpg.entity.EntityRestlessSkull;
import com.vivern.arpg.entity.EntitySand;
import com.vivern.arpg.entity.EntitySeaEffloresce;
import com.vivern.arpg.entity.EntityShard;
import com.vivern.arpg.entity.EntitySlimeBullet;
import com.vivern.arpg.entity.EntitySnapball;
import com.vivern.arpg.entity.EntitySnowflakeShuriken;
import com.vivern.arpg.entity.EntitySpellForgeCatalyst;
import com.vivern.arpg.entity.EntityStingerShard;
import com.vivern.arpg.entity.EntitySummon;
import com.vivern.arpg.entity.EntitySunrise;
import com.vivern.arpg.entity.EntitySwordGhost;
import com.vivern.arpg.entity.EntityThistleThorn;
import com.vivern.arpg.entity.EntityThrowedReaper;
import com.vivern.arpg.entity.EntityTimelessSword;
import com.vivern.arpg.entity.EntityVampireKnife;
import com.vivern.arpg.entity.EntityWeatherRocket;
import com.vivern.arpg.entity.FireworkEntity;
import com.vivern.arpg.entity.FishingHook;
import com.vivern.arpg.entity.GemStaffShoot;
import com.vivern.arpg.entity.GraveLurkerProjectile;
import com.vivern.arpg.entity.HadronBlasterShoot;
import com.vivern.arpg.entity.IchorEffect;
import com.vivern.arpg.entity.LavaDropperShoot;
import com.vivern.arpg.entity.LightningStrike;
import com.vivern.arpg.entity.LordOfPainSpike;
import com.vivern.arpg.entity.NailGunShoot;
import com.vivern.arpg.entity.NetherGrinderBullet;
import com.vivern.arpg.entity.ParticleGore;
import com.vivern.arpg.entity.PistolFishStrike;
import com.vivern.arpg.entity.PlasmaAcceleratorShoot;
import com.vivern.arpg.entity.PlasmaPistolShoot;
import com.vivern.arpg.entity.PlasmaRailgunShoot;
import com.vivern.arpg.entity.PlasmaRifleBall;
import com.vivern.arpg.entity.SharkRocket;
import com.vivern.arpg.entity.ShellShard;
import com.vivern.arpg.entity.ShootOfWitherdry;
import com.vivern.arpg.entity.SnowstormEntity;
import com.vivern.arpg.entity.SpikedBurst;
import com.vivern.arpg.entity.StaffFireballEntity;
import com.vivern.arpg.entity.StingerBoltEntity;
import com.vivern.arpg.entity.StingingCellEntity;
import com.vivern.arpg.entity.SurvivorLootSpawner;
import com.vivern.arpg.entity.ThornkeeperShoot;
import com.vivern.arpg.entity.ToxicNuke;
import com.vivern.arpg.entity.ToxicNukeShard;
import com.vivern.arpg.entity.VacuumGunShoot;
import com.vivern.arpg.entity.ViolenceShoot;
import com.vivern.arpg.entity.WandColdShoot;
import com.vivern.arpg.entity.WandColdWave;
import com.vivern.arpg.entity.WhispersShoot;
import com.vivern.arpg.entity.XmassBall;
import com.vivern.arpg.entity.XmassRocket;
import com.vivern.arpg.main.AlchemicLabRecipesRegister;
import com.vivern.arpg.main.AssemblyTableRecipesRegister;
import com.vivern.arpg.main.BiomesRegister;
import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.main.DimensionsRegister;
import com.vivern.arpg.main.EnchantmentInit;
import com.vivern.arpg.main.FluidsRegister;
import com.vivern.arpg.main.IndustrialMixerRecipesRegister;
import com.vivern.arpg.main.IntegrationHelper;
import com.vivern.arpg.main.ItemsElements;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.AbstractRPG;
import com.vivern.arpg.main.MobSpawn;
import com.vivern.arpg.main.NetherMelterRecipesRegister;
import com.vivern.arpg.main.OreDicHelper;
import com.vivern.arpg.main.ShardType;
import com.vivern.arpg.main.SieveRecipesRegister;
import com.vivern.arpg.main.Spell;
import com.vivern.arpg.main.SpellForgeRecipesRegister;
import com.vivern.arpg.main.WeaponParameters;
import com.vivern.arpg.mobs.HostileProjectiles;
import com.vivern.arpg.mobs.NPCMobsPack;
import com.vivern.arpg.network.PacketHandler;
import com.vivern.arpg.potions.AdvancedMobEffects;
import com.vivern.arpg.potions.PotionEffects;
import com.vivern.arpg.recipes.CraftingRegister;
import com.vivern.arpg.recipes.GeomanticFocus;
import com.vivern.arpg.recipes.Phenomenons;
import com.vivern.arpg.recipes.PyrocrystallineRecipe;
import com.vivern.arpg.recipes.Seal;
import com.vivern.arpg.recipes.Soul;
import com.vivern.arpg.tileentity.TileARPGChest;
import com.vivern.arpg.tileentity.TileAbsorptionTotem;
import com.vivern.arpg.tileentity.TileAdvancedBlockDetector;
import com.vivern.arpg.tileentity.TileAlchemicLab;
import com.vivern.arpg.tileentity.TileAlchemicVaporizer;
import com.vivern.arpg.tileentity.TileAquaticaPortal;
import com.vivern.arpg.tileentity.TileAssemblyAugment;
import com.vivern.arpg.tileentity.TileAssemblyTable;
import com.vivern.arpg.tileentity.TileBank;
import com.vivern.arpg.tileentity.TileBioCell;
import com.vivern.arpg.tileentity.TileBlockPlacer;
import com.vivern.arpg.tileentity.TileBookcase;
import com.vivern.arpg.tileentity.TileBunkerDoor;
import com.vivern.arpg.tileentity.TileCalibrationBundle;
import com.vivern.arpg.tileentity.TileChest;
import com.vivern.arpg.tileentity.TileCombinationLock;
import com.vivern.arpg.tileentity.TileCrystalSphere;
import com.vivern.arpg.tileentity.TileCrystallizer;
import com.vivern.arpg.tileentity.TileDisenchantmentTable;
import com.vivern.arpg.tileentity.TileDrill;
import com.vivern.arpg.tileentity.TileDungeonLadder;
import com.vivern.arpg.tileentity.TileDungeonPortal;
import com.vivern.arpg.tileentity.TileElectricSieve;
import com.vivern.arpg.tileentity.TileElectromagnet;
import com.vivern.arpg.tileentity.TileElementDistributor;
import com.vivern.arpg.tileentity.TileEntityFrostPortal;
import com.vivern.arpg.tileentity.TileEtheriteInvocator;
import com.vivern.arpg.tileentity.TileGlossary;
import com.vivern.arpg.tileentity.TileGlowingVein;
import com.vivern.arpg.tileentity.TileIndustrialMixer;
import com.vivern.arpg.tileentity.TileInfernumFurnace;
import com.vivern.arpg.tileentity.TileItemCharger;
import com.vivern.arpg.tileentity.TileMagicGenerator;
import com.vivern.arpg.tileentity.TileManaBottle;
import com.vivern.arpg.tileentity.TileManaPump;
import com.vivern.arpg.tileentity.TileMonsterSpawner;
import com.vivern.arpg.tileentity.TileNetherMelter;
import com.vivern.arpg.tileentity.TileNexusBeacon;
import com.vivern.arpg.tileentity.TileNexusFlower;
import com.vivern.arpg.tileentity.TileNexusNiveolite;
import com.vivern.arpg.tileentity.TileNexusWinterAltar;
import com.vivern.arpg.tileentity.TilePresentBox;
import com.vivern.arpg.tileentity.TilePuzzle;
import com.vivern.arpg.tileentity.TilePyrocrystallineConverter;
import com.vivern.arpg.tileentity.TileResearchTable;
import com.vivern.arpg.tileentity.TileRetort;
import com.vivern.arpg.tileentity.TileRunicMirror;
import com.vivern.arpg.tileentity.TileSacrificialAltar;
import com.vivern.arpg.tileentity.TileShimmeringBeastbloom;
import com.vivern.arpg.tileentity.TileSieve;
import com.vivern.arpg.tileentity.TileSoulCatcher;
import com.vivern.arpg.tileentity.TileSpellForge;
import com.vivern.arpg.tileentity.TileSplitter;
import com.vivern.arpg.tileentity.TileStarLantern;
import com.vivern.arpg.tileentity.TileStormledgePortal;
import com.vivern.arpg.tileentity.TileTeamBanner;
import com.vivern.arpg.tileentity.TileTopazCrystal;
import com.vivern.arpg.tileentity.TileToxicPortal;
import com.vivern.arpg.tileentity.TileTritonHearth;
import com.vivern.arpg.tileentity.TileVial;
import com.vivern.arpg.tileentity.TileVoidCrystal;
import com.vivern.arpg.tileentity.TileWeaponSpawner;
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
   public void preInit(FMLPreInitializationEvent event) throws IllegalAccessException {
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
      EntityRegistry.registerModEntity(new ResourceLocation("arpg", "flyingapple"), EntityFlyApple.class,
            "arpg:flying_apple", 0, AbstractRPG.instance, 64, 20, true);
      EntityRegistry.registerModEntity(new ResourceLocation("arpg", "lying_apple"), AppleEffect.class,
            "arpg:apple_effect", 1, AbstractRPG.instance, 64, 20, true);
      EntityRegistry.registerModEntity(new ResourceLocation("arpg", "ichor"), EntityIchor.class, "arpg:entity_ichor", 2,
            AbstractRPG.instance, 64, 20, true);
      EntityRegistry.registerModEntity(new ResourceLocation("arpg", "ichoreffect"), IchorEffect.class,
            "arpg:ichor_effect", 3, AbstractRPG.instance, 64, 20, true);
      EntityRegistry.registerModEntity(new ResourceLocation("arpg", "sharkrocket"), SharkRocket.class,
            "arpg:shark_rocket", 4, AbstractRPG.instance, 64, 1, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "null"), EntityBoomerangMagic.class, "arpg:entity_boomerangmagic", 5,
            AbstractRPG.instance, 64, 1, true);
      EntityRegistry.registerModEntity(new ResourceLocation("arpg", "butterfly"), EntityButterfly.class,
            "arpg:entity_butterfly", 6, AbstractRPG.instance, 64, 1, true);
      EntityRegistry.registerModEntity(new ResourceLocation("arpg", "sunrise"), EntitySunrise.class,
            "arpg:entity_sunrise", 7, AbstractRPG.instance, 64, 1, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "vampire_knife"), EntityVampireKnife.class, "arpg:entity_vampire_knife", 8,
            AbstractRPG.instance, 64, 10, true);
      EntityRegistry.registerModEntity(new ResourceLocation("arpg", "blood_drop"), BloodDrop.class,
            "arpg:entity_blood_drop", 9, AbstractRPG.instance, 64, 1, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "frost_bolt"), EntityFrostBolt.class, "arpg:entity_frost_bolt", 10,
            AbstractRPG.instance, 64, 10, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "laser_disk"), EntityLaserDisk.class, "arpg:entity_laser_disk", 11,
            AbstractRPG.instance, 64, 5, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "element_projectile"), ElementProjectile.class,
            "arpg:entity_element_projectile", 12, AbstractRPG.instance, 64, 1, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "stinger_bolt"), StingerBoltEntity.class, "arpg:entity_stinger", 13,
            AbstractRPG.instance, 64, 5, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "stinger_shard"), EntityStingerShard.class, "arpg:entity_stinger_shard", 14,
            AbstractRPG.instance, 64, 1, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "firework_entity"), FireworkEntity.class, "arpg:entity_firework", 15,
            AbstractRPG.instance, 64, 1, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "dragon_firework_entity"), DragonFireworkEntity.class,
            "arpg:entity_dragon_firework", 16, AbstractRPG.instance, 64, 1, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "sand_scepter_entity"), EntitySand.class, "arpg:entity_sand", 17,
            AbstractRPG.instance, 64, 1, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "bilebiter_shoot"), BilebiterShoot.class, "arpg:entity_bilebiter_shoot", 18,
            AbstractRPG.instance, 64, 1, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "bilebiter_homing_shoot"), BilebiterHomingShoot.class,
            "arpg:entity_bilebiter_homing", 19, AbstractRPG.instance, 64, 1, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "placed_item"), EntityPlacedItem.class, "arpg:entity_placed_item", 20,
            AbstractRPG.instance, 64, 20, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "electric_acid_radiation_potion"),
            EntityElectricAcidRadiationPotion.class,
            "arpg:entity_electric_acid_radiation_potion",
            21,
            AbstractRPG.instance,
            64,
            20,
            true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "staff_fireball"), StaffFireballEntity.class, "arpg:entity_staff_fireball", 22,
            AbstractRPG.instance, 64, 20, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "snowstorm"), SnowstormEntity.class, "arpg:entity_snowstorm", 23,
            AbstractRPG.instance, 64, 20, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "electric_bolt"), EntityElectricBolt.class, "arpg:entity_electric_bolt", 24,
            AbstractRPG.instance, 64, 20, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "slime_bullet"), EntitySlimeBullet.class, "arpg:entity_slime_bullet", 25,
            AbstractRPG.instance, 64, 2, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "firemage_setbonus"), EntityFiremageSetBonus.class,
            "arpg:entity_firemage_setbonus", 26, AbstractRPG.instance, 64, 1, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "ender_seer_projectile"), EnderSeerProjectile.class,
            "arpg:entity_ender_seer_projectile", 27, AbstractRPG.instance, 100, 1, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "fishing_hook"), FishingHook.class, "arpg:entity_fishing_hook", 28,
            AbstractRPG.instance, 64, 1, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "nether_grinder_bullet"), NetherGrinderBullet.class,
            "arpg:entity_nether_grinder_bullet", 29, AbstractRPG.instance, 64, 1, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "cannon_snowball"), CannonSnowball.class, "arpg:entity_cannon_snowball", 30,
            AbstractRPG.instance, 64, 1, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "snowflake_shuriken"), EntitySnowflakeShuriken.class,
            "arpg:entity_snowflake_shuriken", 31, AbstractRPG.instance, 64, 1, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "grave_lurker_projectile"),
            GraveLurkerProjectile.class,
            "arpg:entity_grave_lurker_projectile",
            32,
            AbstractRPG.instance,
            64,
            20,
            true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "throwed_reaper"), EntityThrowedReaper.class, "arpg:entity_throwed_reaper", 33,
            AbstractRPG.instance, 64, 1, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "sword_ghost"), EntitySwordGhost.class, "arpg:entity_sword_ghost", 34,
            AbstractRPG.instance, 64, 1, true);
      EntityRegistry.registerModEntity(new ResourceLocation("arpg", "toxic_nuke"), ToxicNuke.class,
            "arpg:entity_toxic_nuke", 35, AbstractRPG.instance, 64, 10, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "toxic_nuke_shard"), ToxicNukeShard.class, "arpg:entity_toxic_nuke_shard", 36,
            AbstractRPG.instance, 64, 10, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "cryo_gun_entity"), CryoGunEntity.class, "arpg:entity_cryo_gun", 37,
            AbstractRPG.instance, 64, 10, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "minigun_icicle"), EntityMinigunIcicle.class, "arpg:entity_minigun_icicle", 38,
            AbstractRPG.instance, 64, 10, true);
      EntityRegistry.registerModEntity(new ResourceLocation("arpg", "chair"), EntityChair.class, "arpg:entity_chair",
            39, AbstractRPG.instance, 64, 20, true);
      EntityRegistry.registerModEntity(new ResourceLocation("arpg", "summon"), EntitySummon.class, "arpg:entity_summon",
            40, AbstractRPG.instance, 64, 20, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "plasma_railgun_shot"), PlasmaRailgunShoot.class,
            "arpg:entity_plasma_railgun_shot", 41, AbstractRPG.instance, 64, 20, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "plasma_rifle_ball"), PlasmaRifleBall.class, "arpg:entity_plasma_rifle_ball",
            42, AbstractRPG.instance, 64, 20, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "head_shooter_bullet"), EntityHeadShooter.class,
            "arpg:entity_head_shooter_bullet", 43, AbstractRPG.instance, 64, 20, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "plasma_pistol_shoot"), PlasmaPistolShoot.class,
            "arpg:entity_plasma_pistol_shoot", 44, AbstractRPG.instance, 64, 20, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "bog_flower_shoot"), EntityBogFlower.class, "arpg:entity_bog_flower_shoot", 45,
            AbstractRPG.instance, 64, 20, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "pistol_fish_strike"), PistolFishStrike.class,
            "arpg:entity_pistol_fish_strike", 46, AbstractRPG.instance, 64, 20, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "bubble_fish_shoot"), BubbleFishShoot.class, "arpg:entity_bubble_fish_shoot",
            47, AbstractRPG.instance, 64, 20, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "lava_dropper_shoot"), LavaDropperShoot.class,
            "arpg:entity_lava_dropper_shoot", 48, AbstractRPG.instance, 64, 20, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "plasma_accelerator_shoot"),
            PlasmaAcceleratorShoot.class,
            "arpg:entity_plasma_accelerator_shoot",
            49,
            AbstractRPG.instance,
            64,
            20,
            true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "vacuum_gun_shoot"), VacuumGunShoot.class, "arpg:vacuum_gun_shoot", 50,
            AbstractRPG.instance, 64, 20, true);
      EntityRegistry.registerModEntity(new ResourceLocation("arpg", "shard"), EntityShard.class, "arpg:entity_shard",
            51, AbstractRPG.instance, 64, 20, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "gem_staff_shoot"), GemStaffShoot.class, "arpg:entity_gem_staff_shoot", 52,
            AbstractRPG.instance, 64, 20, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "wand_of_cold_shoot"), WandColdShoot.class, "arpg:entity_wand_of_cold_shoot",
            53, AbstractRPG.instance, 64, 20, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "arrow_firejet"), EntityArrowFirejet.class, "arpg:entity_arrow_firejet", 54,
            AbstractRPG.instance, 64, 20, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "custom_mob_projectile"), CustomMobProjectile.class,
            "arpg:entity_custom_mob_projectile", 55, AbstractRPG.instance, 64, 1, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "acid_fire_shoot"), EntityAcidFire.class, "arpg:entity_acid_fire", 56,
            AbstractRPG.instance, 64, 20, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "particle_gore"), ParticleGore.class, "arpg:entity_particle_gore", 57,
            AbstractRPG.instance, 64, 20, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "nail_gun_shoot"), NailGunShoot.class, "arpg:entity_nail_gun_shoot", 58,
            AbstractRPG.instance, 64, 1, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "crystal_star_shoot"), CrystalStarShoot.class,
            "arpg:entity_crystal_star_shoot", 59, AbstractRPG.instance, 64, 1, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "lord_of_pain_spike"), LordOfPainSpike.class, "arpg:entity_lord_of_pain_spike",
            60, AbstractRPG.instance, 64, 1, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "crystal_fan_shoot"), CrystalFanShoot.class, "arpg:entity_crystal_fan_shoot",
            61, AbstractRPG.instance, 64, 20, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "crystal_fan_bonus"), CrystalFanBonus.class, "arpg:entity_crystal_fan_bonus",
            62, AbstractRPG.instance, 64, 2, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "geomantic_crystal"), EntityGeomanticCrystal.class,
            "arpg:entity_geomantic_crystal", 63, AbstractRPG.instance, 64, 20, true);
      EntityRegistry.registerModEntity(new ResourceLocation("arpg", "coin"), EntityCoin.class, "arpg:coin", 64,
            AbstractRPG.instance, 64, 20, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "thornkeeper_shoot"), ThornkeeperShoot.class, "arpg:entity_thornkeeper_shoot",
            65, AbstractRPG.instance, 64, 20, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "thistle_thorn_entity"), EntityThistleThorn.class, "arpg:entity_thistle_thorn",
            66, AbstractRPG.instance, 64, 20, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "spiked_burst"), SpikedBurst.class, "arpg:entity_spiked_burst", 67,
            AbstractRPG.instance, 64, 20, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "restless_skull_shoot"), EntityRestlessSkull.class,
            "arpg:entity_restless_skull", 68, AbstractRPG.instance, 64, 20, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "magic_rocket"), EntityMagicRocket.class, "arpg:entity_magic_rocket", 69,
            AbstractRPG.instance, 64, 20, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "stinging_cell_entity"), StingingCellEntity.class, "arpg:entity_stinging_cell",
            70, AbstractRPG.instance, 64, 1, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "sea_effloresce_entity"), EntitySeaEffloresce.class,
            "arpg:entity_sea_effloresce", 71, AbstractRPG.instance, 64, 1, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "coral_rifle_bullet"), CoralRifleBullet.class,
            "arpg:entity_coral_rifle_bullet", 72, AbstractRPG.instance, 64, 20, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "timeless_sword_entity"), EntityTimelessSword.class,
            "arpg:entity_timeless_sword", 73, AbstractRPG.instance, 64, 40, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "lightning_strike"), LightningStrike.class, "arpg:entity_lightning_strike", 74,
            AbstractRPG.instance, 64, 20, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "frostfire_explosive"),
            EntityFrostfireExplosive.class,
            "arpg:entity_frostfire_explosive",
            75,
            AbstractRPG.instance,
            64,
            20,
            true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "acid_bomb"), EntityAcidBomb.class, "arpg:entity_acid_bomb", 76,
            AbstractRPG.instance, 64, 20, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "acid_bomb_blob"), EntityAcidBomb.AcidBombBlob.class,
            "arpg:entity_acid_bomb_blob", 77, AbstractRPG.instance, 64, 20, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "magnetic_field"), EntityMagneticField.class, "arpg:entity_magnetic_field", 78,
            AbstractRPG.instance, 64, 20, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "hadron_shoot"), HadronBlasterShoot.class, "arpg:entity_hadron_shoot", 79,
            AbstractRPG.instance, 64, 20, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "hadron_bonus"), HadronBlasterShoot.HadronBlasterBonus.class,
            "arpg:entity_hadron_bonus", 80, AbstractRPG.instance, 64, 20, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "snapball_entity"), EntitySnapball.class, "arpg:entity_snapball", 81,
            AbstractRPG.instance, 64, 100, false);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "launched_rocket"), EntityLaunchedRocket.class, "arpg:entity_launched_rocket",
            82, AbstractRPG.instance, 64, 20, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "mini_nuke_entity"), EntityMiniNuke.class, "arpg:entity_mini_nuke", 83,
            AbstractRPG.instance, 64, 20, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "chlorine_cloud"), ChlorineCloud.class, "arpg:entity_chlorine_cloud", 84,
            AbstractRPG.instance, 64, 20, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "chain_mace_entity"), EntityChainMace.class, "arpg:entity_chain_mace", 85,
            AbstractRPG.instance, 64, 1, true);
      EntityRegistry.registerModEntity(new ResourceLocation("arpg", "grenade"), EntityGrenade.class,
            "arpg:entity_grenade", 86, AbstractRPG.instance, 64, 1, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "advanced_falling_block"),
            AdvancedFallingBlock.class,
            "arpg:entity_advanced_falling_block",
            87,
            AbstractRPG.instance,
            64,
            20,
            true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "live_heart"), EntityLiveHeart.class, "arpg:entity_live_heart", 88,
            AbstractRPG.instance, 64, 20, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "blowhole_shoot"), BlowholeShoot.class, "arpg:entity_blowhole_shoot", 89,
            AbstractRPG.instance, 64, 20, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "crystal_cutter_entity"), EntityCrystalCutter.class,
            "arpg:entity_crystal_cutter", 90, AbstractRPG.instance, 64, 20, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "ceratarget_shoot"), CeratargetShoot.class, "arpg:entity_ceratarget_shoot", 91,
            AbstractRPG.instance, 64, 2, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "arcane_explode"), EntityArcaneExplode.class, "arpg:entity_arcane_explode", 93,
            AbstractRPG.instance, 64, 20, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "arrow_frozen"),
            EntityArrowFrozen.class,
            "arpg:entity_arrow_frozen",
            94,
            AbstractRPG.instance,
            64,
            AbstractArrow.updateFrequency,
            true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "arrow_vicious"),
            EntityArrowVicious.class,
            "arpg:entity_arrow_vicious",
            95,
            AbstractRPG.instance,
            64,
            AbstractArrow.updateFrequency,
            true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "arrow_modern"),
            EntityArrowModern.class,
            "arpg:entity_arrow_modern",
            96,
            AbstractRPG.instance,
            64,
            AbstractArrow.updateFrequency,
            true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "arrow_bengal"),
            EntityArrowBengal.class,
            "arpg:entity_arrow_bengal",
            97,
            AbstractRPG.instance,
            64,
            AbstractArrow.updateFrequency,
            true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "arrow_fish"),
            EntityArrowFish.class,
            "arpg:entity_arrow_fish",
            98,
            AbstractRPG.instance,
            64,
            AbstractArrow.updateFrequency,
            true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "arrow_void"),
            EntityArrowVoid.class,
            "arpg:entity_arrow_void",
            99,
            AbstractRPG.instance,
            64,
            AbstractArrow.updateFrequency,
            true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "arrow_shell"),
            EntityArrowShell.class,
            "arpg:entity_arrow_shell",
            100,
            AbstractRPG.instance,
            64,
            AbstractArrow.updateFrequency,
            true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "shell_shard"), ShellShard.class, "arpg:entity_shell_shard", 101,
            AbstractRPG.instance, 64, 20, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "arrow_bouncing"),
            EntityArrowBouncing.class,
            "arpg:entity_arrow_bouncing",
            102,
            AbstractRPG.instance,
            64,
            AbstractArrow.updateFrequency,
            true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "arrow_mithril"),
            EntityArrowMithril.class,
            "arpg:entity_arrow_mithril",
            103,
            AbstractRPG.instance,
            64,
            AbstractArrow.updateFrequency,
            true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "arrow_twin"),
            EntityArrowTwin.class,
            "arpg:entity_arrow_twin",
            104,
            AbstractRPG.instance,
            64,
            AbstractArrow.updateFrequency,
            true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "arrow_wind"),
            EntityArrowWind.class,
            "arpg:entity_arrow_wind",
            105,
            AbstractRPG.instance,
            64,
            AbstractArrow.updateFrequency,
            true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "azure_ore_shoot"), AzureOreShoot.class, "arpg:entity_azure_ore_shoot", 106,
            AbstractRPG.instance, 64, 1, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "violence_shoot"), ViolenceShoot.class, "arpg:entity_violence_shoot", 107,
            AbstractRPG.instance, 64, 20, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "shoot_of_witherdry"), ShootOfWitherdry.class,
            "arpg:entity_shoot_of_witherdry", 108, AbstractRPG.instance, 64, 20, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "whispers_shoot"), WhispersShoot.class, "arpg:entity_whispers_shoot", 109,
            AbstractRPG.instance, 64, 20, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "spell_forge_catalyst"),
            EntitySpellForgeCatalyst.class,
            "arpg:entity_spell_forge_catalyst",
            110,
            AbstractRPG.instance,
            64,
            20,
            true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "xmass_rocket"), XmassRocket.class, "arpg:entity_xmass_rocket", 111,
            AbstractRPG.instance, 64, 20, true);
      EntityRegistry.registerModEntity(new ResourceLocation("arpg", "xmass_ball"), XmassBall.class,
            "arpg:entity_xmass_ball", 112, AbstractRPG.instance, 64, 20, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "coral_polyp"), CoralPolyp.class, "arpg:entity_coral_polyp", 113,
            AbstractRPG.instance, 64, 2, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "enigmate_twins_shoot"), EnigmateTwinsShoot.class,
            "arpg:entity_enigmate_twins_shoot", 114, AbstractRPG.instance, 64, 1, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "cryo_destroyer_spray"), CryoDestroyerSpray.class,
            "arpg:entity_cryo_destroyer_spray", 115, AbstractRPG.instance, 64, 20, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "cryoned_block"), CryonedBlock.class, "arpg:entity_cryoned_block", 116,
            AbstractRPG.instance, 64, 20, false);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "wand_of_cold_wave"), WandColdWave.class, "arpg:entity_wand_of_cold_wave", 117,
            AbstractRPG.instance, 64, 20, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "survivor_loot_spawner"),
            SurvivorLootSpawner.class,
            "arpg:entity_survivor_loot_spawner",
            118,
            AbstractRPG.instance,
            128,
            20,
            true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "crystal_star_powered_shoot"),
            CrystalStarPoweredShoot.class,
            "arpg:entity_crystal_star_powered_shoot",
            119,
            AbstractRPG.instance,
            64,
            1,
            true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "weather_rocket"), EntityWeatherRocket.class, "arpg:entity_weather_rocket",
            120, AbstractRPG.instance, 128, 20, true);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "entity_hanging_allsides"),
            EntityHangingAllSides.class,
            "arpg:entity_hanging_allsides",
            121,
            AbstractRPG.instance,
            24,
            20,
            false);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "big_lightning_strike"), BigLightningStrike.class,
            "arpg:entity_big_lightning_strike", 122, AbstractRPG.instance, 256, 20, true);
      EntityRegistry.registerModEntity(new ResourceLocation("arpg", "decayer"), BlocksDecayer.class,
            "arpg:entity_decayer", 123, AbstractRPG.instance, 64, 20, false);
      EntityRegistry.registerModEntity(
            new ResourceLocation("arpg", "boss_loot"), EntityBossLoot.class, "arpg:entity_boss_loot", 124,
            AbstractRPG.instance, 64, 20, true);
      HostileProjectiles.register();
      GameRegistry.registerTileEntity(TileEntityFrostPortal.class, new ResourceLocation("arpg", "te_frost_portal"));
      GameRegistry.registerTileEntity(TileAdvancedBlockDetector.class,
            new ResourceLocation("arpg", "te_advanced_block_detector"));
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
      GameRegistry.registerTileEntity(TileAlchemicVaporizer.class,
            new ResourceLocation("arpg", "te_alchemic_vaporizer"));
      GameRegistry.registerTileEntity(TilePuzzle.class, new ResourceLocation("arpg", "te_puzzle"));
      GameRegistry.registerTileEntity(TileCombinationLock.class, new ResourceLocation("arpg", "te_combination_lock"));
      GameRegistry.registerTileEntity(TileChest.class, new ResourceLocation("arpg", "te_chest"));
      GameRegistry.registerTileEntity(TileRunicMirror.class, new ResourceLocation("arpg", "te_runic_mirror"));
      GameRegistry.registerTileEntity(TileCrystallizer.class, new ResourceLocation("arpg", "te_crystallizer"));
      GameRegistry.registerTileEntity(TilePyrocrystallineConverter.class,
            new ResourceLocation("arpg", "te_pyrocrystalline_converter"));
      GameRegistry.registerTileEntity(TileDrill.class, new ResourceLocation("arpg", "te_drill"));
      GameRegistry.registerTileEntity(TileAssemblyTable.class, new ResourceLocation("arpg", "te_assembly_table"));
      GameRegistry.registerTileEntity(TileWeaponSpawner.class, new ResourceLocation("arpg", "te_weapon_spawner"));
      GameRegistry.registerTileEntity(TileStarLantern.class, new ResourceLocation("arpg", "te_star_lantern"));
      GameRegistry.registerTileEntity(TilePresentBox.class, new ResourceLocation("arpg", "te_present_box"));
      GameRegistry.registerTileEntity(TileElectromagnet.class, new ResourceLocation("arpg", "te_electromagnet"));
      GameRegistry.registerTileEntity(TileShimmeringBeastbloom.class,
            new ResourceLocation("arpg", "te_shimmering_beastbloom"));
      GameRegistry.registerTileEntity(TileBioCell.class, new ResourceLocation("arpg", "te_bio_cell"));
      GameRegistry.registerTileEntity(TileNexusFlower.class, new ResourceLocation("arpg", "te_nexus_flower"));
      GameRegistry.registerTileEntity(TileMonsterSpawner.class, new ResourceLocation("arpg", "te_monster_spawner"));
      GameRegistry.registerTileEntity(TileBunkerDoor.class, new ResourceLocation("arpg", "te_bunker_door"));
      GameRegistry.registerTileEntity(TileNexusWinterAltar.class,
            new ResourceLocation("arpg", "te_nexus_winter_altar"));
      GameRegistry.registerTileEntity(TileDungeonLadder.class, new ResourceLocation("arpg", "te_dungeon_ladder"));
      GameRegistry.registerTileEntity(TileItemCharger.class, new ResourceLocation("arpg", "te_item_charger"));
      GameRegistry.registerTileEntity(TileToxicPortal.class, new ResourceLocation("arpg", "te_toxic_portal"));
      GameRegistry.registerTileEntity(TileDungeonPortal.class, new ResourceLocation("arpg", "te_dungeon_portal"));
      GameRegistry.registerTileEntity(TileVoidCrystal.class, new ResourceLocation("arpg", "te_void_crystal"));
      GameRegistry.registerTileEntity(TileNexusBeacon.class, new ResourceLocation("arpg", "te_nexus_beacon"));
      GameRegistry.registerTileEntity(TileIndustrialMixer.class, new ResourceLocation("arpg", "te_industrial_mixer"));
      GameRegistry.registerTileEntity(TileResearchTable.class, new ResourceLocation("arpg", "te_research_table"));
      GameRegistry.registerTileEntity(TileElementDistributor.class,
            new ResourceLocation("arpg", "te_creative_element_distributor"));
      GameRegistry.registerTileEntity(TileSplitter.class, new ResourceLocation("arpg", "te_splitter"));
      GameRegistry.registerTileEntity(TileRetort.class, new ResourceLocation("arpg", "te_retort"));
      GameRegistry.registerTileEntity(TileVial.class, new ResourceLocation("arpg", "te_vial"));
      GameRegistry.registerTileEntity(TileBookcase.class, new ResourceLocation("arpg", "te_bookcase"));
      GameRegistry.registerTileEntity(TileGlossary.class, new ResourceLocation("arpg", "te_glossary"));
      GameRegistry.registerTileEntity(TileAbsorptionTotem.class, new ResourceLocation("arpg", "te_absorption_totem"));
      GameRegistry.registerTileEntity(TileDisenchantmentTable.class,
            new ResourceLocation("arpg", "te_disenchantment_table"));
      GameRegistry.registerTileEntity(TileTritonHearth.class, new ResourceLocation("arpg", "te_triton_hearth"));
      GameRegistry.registerTileEntity(TileAssemblyAugment.class, new ResourceLocation("arpg", "te_assembly_augment"));
      GameRegistry.registerTileEntity(TileBank.class, new ResourceLocation("arpg", "te_bank"));
      GameRegistry.registerTileEntity(TileNexusNiveolite.class, new ResourceLocation("arpg", "te_nexus_niveolite"));
      GameRegistry.registerTileEntity(TileCalibrationBundle.class,
            new ResourceLocation("arpg", "te_calibration_bundle"));
      GameRegistry.registerTileEntity(TileSoulCatcher.class, new ResourceLocation("arpg", "te_soul_catcher"));
      GameRegistry.registerTileEntity(TileManaPump.class, new ResourceLocation("arpg", "te_mana_pump"));
      GameRegistry.registerTileEntity(TileSieve.class, new ResourceLocation("arpg", "te_sieve"));
      GameRegistry.registerTileEntity(TileElectricSieve.class, new ResourceLocation("arpg", "te_electric_sieve"));
      GameRegistry.registerTileEntity(TileARPGChest.class, new ResourceLocation("arpg", "te_arpg_chest"));
      GameRegistry.registerTileEntity(TileAquaticaPortal.class, new ResourceLocation("arpg", "te_aquatica_portal"));
      GameRegistry.registerTileEntity(TileStormledgePortal.class, new ResourceLocation("arpg", "te_stormledge_portal"));
      GameRegistry.registerTileEntity(TileMagicGenerator.class, new ResourceLocation("arpg", "te_magic_generator"));
      GameRegistry.registerTileEntity(TileEtheriteInvocator.class,
            new ResourceLocation("arpg", "te_etherite_invocator"));
      GameRegistry.registerTileEntity(TileTeamBanner.class, new ResourceLocation("arpg", "te_team_banner"));
   }

   public void init(FMLInitializationEvent event) {
      DimensionsRegister.registerTeleporters();
      OreDicHelper.init();
      OreBlockMatcher matcherNether = new OreBlockMatcher(new Block[] { Blocks.NETHERRACK });
      OreBlockMatcher matcherOverworld = new OreBlockMatcher(new Block[] { Blocks.STONE });
      OreBlockMatcher matcherOverwNethToxi = new OreBlockMatcher(
            new Block[] { Blocks.STONE, Blocks.NETHERRACK, BlocksRegister.RADIOSTONE });
      OreBlockMatcher matcherLava = new OreBlockMatcher(new Block[] { Blocks.LAVA, Blocks.FLOWING_LAVA });
      registerWorldGen(
            new CustomOreGenerator(BlocksRegister.OREDEMONITE.getDefaultState(), -1, matcherNether, 1, 4, 9, 0, 128));
      registerWorldGen(
            new CustomOreGenerator(BlocksRegister.OREINFERNUM.getDefaultState(), -1, matcherNether, 2, 8, 14, 0, 128));
      registerWorldGen(
            new CustomOreGenerator(BlocksRegister.OREMOLTEN.getDefaultState(), -1, matcherNether, 1, 2, 6, 0, 128));
      registerWorldGen(
            new CustomOreGenerator(BlocksRegister.ORERUBY.getDefaultState(), 0, matcherOverworld, 4, 8, 2, 0, 36));
      registerWorldGen(
            new CustomOreGenerator(BlocksRegister.ORESAPPHIRE.getDefaultState(), 0, matcherOverworld, 4, 8, 2, 0, 36));
      registerWorldGen(
            new CustomOreGenerator(BlocksRegister.ORECITRINE.getDefaultState(), 0, matcherOverworld, 4, 8, 2, 0, 36));
      registerWorldGen(
            new CustomOreGenerator(BlocksRegister.ORETOPAZ.getDefaultState(), 0, matcherOverworld, 3, 8, 3, 0, 48));
      registerWorldGen(
            new CustomOreGenerator(BlocksRegister.OREAMETHYST.getDefaultState(), 0, matcherOverworld, 3, 9, 3, 0, 48));
      registerWorldGen(new CustomOreGenerator(BlocksRegister.ORERHINESTONE.getDefaultState(), 0, matcherOverworld, 5,
            10, 6, 10, 84));
      registerWorldGen(
            new CustomOreGenerator(BlocksRegister.ORESILVER.getDefaultState(), 0, matcherOverworld, 3, 8, 5, 0, 58));
      registerWorldGen(
            new CustomOreGenerator(BlocksRegister.ORESALT.getDefaultState(), 0, matcherOverworld, 3, 10, 3, 25, 64));
      registerWorldGen(
            new CustomOreGenerator(BlocksRegister.ORETITANIUM.getDefaultState(), 0, matcherOverworld, 1, 5, 1, 0, 20));
      registerWorldGen(new CustomOreGenerator(BlocksRegister.ORETITANIUMRADIOACTIVE.getDefaultState(), 101,
            matcherOverwNethToxi, 1, 6, 2, 0, 32));
      registerWorldGen(new CustomOreGenerator(BlocksRegister.ORETITANIUM.getDefaultState(), 102, matcherOverworld, 1, 6,
            16, 0, 255));
      registerWorldGen(
            new OreGenChromium(
                  BlocksRegister.ORECHROMIUM.getDefaultState(), 0,
                  new OreBlockMatcher(new Block[] { Blocks.STONE, Blocks.AIR }), 3, 7, 2, 6, 43)
                  .setGeode(
                        0.3F,
                        Blocks.IRON_ORE.getDefaultState(),
                        Blocks.LAVA.getDefaultState(),
                        Blocks.WATER.getDefaultState(),
                        OreDicHelper.getBlock("oreLead")));
      registerWorldGen(
            new OreGenZinc(BlocksRegister.OREZINC.getDefaultState(), 0, matcherOverworld, 26, 64, 0.35F, 8, 56)
                  .setFalsiveBlocks(
                        0.26F,
                        Blocks.MONSTER_EGG.getDefaultState(),
                        Blocks.FLOWING_LAVA.getDefaultState(),
                        Blocks.OBSIDIAN.getDefaultState(),
                        Blocks.GRAVEL.getDefaultState()));
      registerWorldGen(
            new OreGenBauxite(BlocksRegister.OREALUMINIUM.getDefaultState(),
                  BlocksRegister.BAUXITEBLOCK.getDefaultState(), 0, matcherOverworld, 14, 45, 0.5F, 40, 75));
      registerWorldGen(new WorldGenFieryBeans());
      registerWorldGen(
            new WorldGenSulfur(
                  BlocksRegister.SULFURCRYSTAL.getDefaultState(),
                  BlocksRegister.FLUIDSULFURICGAS.getDefaultState(),
                  0.4F,
                  new int[] { 0, -1, 101 },
                  matcherOverwNethToxi,
                  16,
                  46,
                  0.6F,
                  10,
                  90));
      registerWorldGen(new WorldGenMagmaBloom(BlocksRegister.MAGMABLOOM.getDefaultState(), -1, matcherLava, 3, 10,
            0.05F, 14, 50, 2));
      registerWorldGen(
            new WorldGenRoofVines(
                  BlocksRegister.REDPEPPERVINE.getDefaultState().withProperty(RedPepperVine.FRUITAGE, false), 3, -1, 80,
                  120, 2, 8, 6, 4));
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
                  false)
                  .setOptions(0.08, 0.04, 4.0, 2.0, 0.4, 0.6, 0.8, 1.2, 0.2, 0.8));
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
                  false)
                  .setOptions(0.08, 0.04, 4.0, 2.0, 0.2, 0.5, 1.0, 1.1, 0.14, 0.45));
      registerWorldGen(new WorldGenHorribleVillage());
      registerWorldGen(new WorldGenManaHealthFlowers(false));
      registerWorldGen(new WorldGenManaHealthFlowers(true));
      registerWorldGen(
            new WorldGenNPC(EntityRegistry.getEntry(NPCMobsPack.NpcMerchant.class), ":npchome_merchant", 20, 0, true));
      registerWorldGen(
            new WorldGenNPC(EntityRegistry.getEntry(NPCMobsPack.NpcWizard.class), ":npchome_wizard", 1050, 0, false));
      registerWorldGen(new WorldGenNPC(EntityRegistry.getEntry(NPCMobsPack.NpcMechanic.class), ":npchome_mechanic",
            1050, 0, false));
      if (Blocks.BROWN_MUSHROOM instanceof BlockMushroom && Blocks.RED_MUSHROOM instanceof BlockMushroom) {
         registerWorldGen(
               new WorldGenMushroomSpawner((BlockMushroom) Blocks.BROWN_MUSHROOM, (BlockMushroom) Blocks.RED_MUSHROOM,
                     Blocks.MYCELIUM, 10, 0, 0.003F));
      }

      GameRegistry.registerWorldGenerator(new WorldGenMoltenSpikes(), 18930414);
      NetworkRegistry.INSTANCE.registerGuiHandler(AbstractRPG.instance, new GuiHandler());
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
      ((ItemLootCase) ItemsRegister.LOOTBOXENCHANTALL).entries = ItemLootCase.entriesAllEnch();
      ((ItemLootCase) ItemsRegister.LOOTBOXENCHANTWEAPON).entries = ItemLootCase.entriesWeaponEnch();
      ((ItemLootCase) ItemsRegister.LOOTBOXENCHANTSIMPLE).entries = ItemLootCase.entriesSimpleEnch();
      System.out.println("arpg | Init new flaming blocks");
      ((DemonicFire) BlocksRegister.DEMONICFIRE).init();
      ((BurningFrost) BlocksRegister.BURNINGFROST).init();
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
