package com.vivern.arpg.proxy;

import com.vivern.arpg.elements.CryonedBlock;
import com.vivern.arpg.elements.animation.Flicks;
import com.vivern.arpg.elements.models.BilebiterHomingModel;
import com.vivern.arpg.elements.models.CryoGunEntityModel;
import com.vivern.arpg.elements.models.CrystalStarShootModel;
import com.vivern.arpg.elements.models.CubikModel;
import com.vivern.arpg.elements.models.DragonFireworkModel;
import com.vivern.arpg.elements.models.ElProjectileModel;
import com.vivern.arpg.elements.models.ElectricSieveModel;
import com.vivern.arpg.elements.models.EntityThistleThornModel;
import com.vivern.arpg.elements.models.EtheriteInvocatorModel;
import com.vivern.arpg.elements.models.FireLordChestModel;
import com.vivern.arpg.elements.models.FireLordHelmModel;
import com.vivern.arpg.elements.models.FireMageHatModel;
import com.vivern.arpg.elements.models.ItemChargerModel;
import com.vivern.arpg.elements.models.MagicHoodie;
import com.vivern.arpg.elements.models.MinigunIcicleModel;
import com.vivern.arpg.elements.models.PhoenixGhostModel;
import com.vivern.arpg.elements.models.PistolFishStrikeModel;
import com.vivern.arpg.elements.models.QuadroBeltModel;
import com.vivern.arpg.elements.models.RocketModel;
import com.vivern.arpg.elements.models.SharkRocketModel;
import com.vivern.arpg.elements.models.ShellShardModel;
import com.vivern.arpg.elements.models.SieveModel;
import com.vivern.arpg.elements.models.SpikedBurstModel;
import com.vivern.arpg.elements.models.StingerBoltModel;
import com.vivern.arpg.elements.models.ThornkeeperShootModel;
import com.vivern.arpg.elements.models.TileNexusNiveoliteModel;
import com.vivern.arpg.elements.models.ToxicNukeModel;
import com.vivern.arpg.elements.models.TritonHearthModel;
import com.vivern.arpg.elements.models.VampireKnifes;
import com.vivern.arpg.elements.models.WeatherRocketModel;
import com.vivern.arpg.elements.models.WizardHatModel;
import com.vivern.arpg.elements.models.XmassBallModel;
import com.vivern.arpg.elements.models.XmassRocketModel;
import com.vivern.arpg.entity.AdvancedFallingBlock;
import com.vivern.arpg.entity.AppleEffect;
import com.vivern.arpg.entity.AzureOreShoot;
import com.vivern.arpg.entity.BetweenParticle;
import com.vivern.arpg.entity.BigLightningStrike;
import com.vivern.arpg.entity.BilebiterHomingShoot;
import com.vivern.arpg.entity.BilebiterShoot;
import com.vivern.arpg.entity.BloodDrop;
import com.vivern.arpg.entity.BlowholeShoot;
import com.vivern.arpg.entity.BubbleFishShoot;
import com.vivern.arpg.entity.CannonSnowball;
import com.vivern.arpg.entity.CeratargetShoot;
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
import com.vivern.arpg.entity.EntityCoin;
import com.vivern.arpg.entity.EntityCrystalCutter;
import com.vivern.arpg.entity.EntityCubicParticle;
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
import com.vivern.arpg.entity.EntityLaserParticle;
import com.vivern.arpg.entity.EntityLaunchedRocket;
import com.vivern.arpg.entity.EntityLiveHeart;
import com.vivern.arpg.entity.EntityMagicRocket;
import com.vivern.arpg.entity.EntityMagneticField;
import com.vivern.arpg.entity.EntityMiniNuke;
import com.vivern.arpg.entity.EntityMinigunIcicle;
import com.vivern.arpg.entity.EntityPart;
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
import com.vivern.arpg.entity.EntityStreamLaserP;
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
import com.vivern.arpg.entity.FreezingParticle;
import com.vivern.arpg.entity.GemStaffShoot;
import com.vivern.arpg.entity.GraplingHookParticle;
import com.vivern.arpg.entity.GraveLurkerProjectile;
import com.vivern.arpg.entity.GunPEmitter;
import com.vivern.arpg.entity.HadronBlasterShoot;
import com.vivern.arpg.entity.IchorEffect;
import com.vivern.arpg.entity.LavaDropperShoot;
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
import com.vivern.arpg.entity.SnowballRender;
import com.vivern.arpg.entity.SnowstormEntity;
import com.vivern.arpg.entity.SpikedBurst;
import com.vivern.arpg.entity.StaffFireballEntity;
import com.vivern.arpg.entity.StingerBoltEntity;
import com.vivern.arpg.entity.StingingCellEntity;
import com.vivern.arpg.entity.SurvivorLootSpawner;
import com.vivern.arpg.entity.ThornkeeperShoot;
import com.vivern.arpg.entity.ToxicNuke;
import com.vivern.arpg.entity.ToxicNukeShard;
import com.vivern.arpg.entity.TrailParticle;
import com.vivern.arpg.entity.VacuumGunShoot;
import com.vivern.arpg.entity.ViolenceShoot;
import com.vivern.arpg.entity.WandColdShoot;
import com.vivern.arpg.entity.WandColdWave;
import com.vivern.arpg.entity.WhispersShoot;
import com.vivern.arpg.entity.XmassBall;
import com.vivern.arpg.entity.XmassRocket;
import com.vivern.arpg.main.AnimationTimer;
import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.main.DeathEffects;
import com.vivern.arpg.main.FluidsRegister;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.Keys;
import com.vivern.arpg.mobs.AquaticaMobsPack;
import com.vivern.arpg.mobs.Blubber;
import com.vivern.arpg.mobs.DungeonMobsPack;
import com.vivern.arpg.mobs.EverfrostMobsPack;
import com.vivern.arpg.mobs.Gnater;
import com.vivern.arpg.mobs.HostileProjectiles;
import com.vivern.arpg.mobs.Moonshroom;
import com.vivern.arpg.mobs.NPCMobsPack;
import com.vivern.arpg.mobs.NexusCap;
import com.vivern.arpg.mobs.OtherMobsPack;
import com.vivern.arpg.mobs.StormledgeMobsPack;
import com.vivern.arpg.mobs.SummonedBlaze;
import com.vivern.arpg.mobs.ToxicomaniaMobsPack;
import com.vivern.arpg.mobs.Troglodyte;
import com.vivern.arpg.mobs.WhiteSlime;
import com.vivern.arpg.potions.Freezing;
import com.vivern.arpg.renders.ARPGChestTESR;
import com.vivern.arpg.renders.AdvancedBlockDetectorTESR;
import com.vivern.arpg.renders.AlchemicLabTESR;
import com.vivern.arpg.renders.AnimatedGParticle;
import com.vivern.arpg.renders.AnimatedGunPRender;
import com.vivern.arpg.renders.AquaticaPortalTESR;
import com.vivern.arpg.renders.AssemblyAugmentTESR;
import com.vivern.arpg.renders.AssemblyTableTESR;
import com.vivern.arpg.renders.BetweenRenderFactory;
import com.vivern.arpg.renders.BioCellTESR;
import com.vivern.arpg.renders.BlockEntityFactory;
import com.vivern.arpg.renders.BookcaseTESR;
import com.vivern.arpg.renders.BulletParticle;
import com.vivern.arpg.renders.CalibrationBundleTESR;
import com.vivern.arpg.renders.ChestsTESR;
import com.vivern.arpg.renders.CrystalSphereTESR;
import com.vivern.arpg.renders.CubikFactory;
import com.vivern.arpg.renders.CustomArrowFactory;
import com.vivern.arpg.renders.CustomProjectileRender;
import com.vivern.arpg.renders.DisenchantmentTableTESR;
import com.vivern.arpg.renders.DungeonLadderTESR;
import com.vivern.arpg.renders.DungeonPortalTESR;
import com.vivern.arpg.renders.FireworkRenderFactory;
import com.vivern.arpg.renders.FreezingFactory;
import com.vivern.arpg.renders.FrostPortalTESR;
import com.vivern.arpg.renders.GHFactory;
import com.vivern.arpg.renders.GUNPFactory;
import com.vivern.arpg.renders.GUNParticle;
import com.vivern.arpg.renders.GlossaryTESR;
import com.vivern.arpg.renders.GlowingVeinTESR;
import com.vivern.arpg.renders.IndustrialMixerTESR;
import com.vivern.arpg.renders.InvasionInfo;
import com.vivern.arpg.renders.ItemChargerTESR;
import com.vivern.arpg.renders.JSONFactory;
import com.vivern.arpg.renders.LaserFactory;
import com.vivern.arpg.renders.MagicGeneratorTESR;
import com.vivern.arpg.renders.ManaBottleTESR;
import com.vivern.arpg.renders.ManaPumpTESR;
import com.vivern.arpg.renders.ModelledPartickle;
import com.vivern.arpg.renders.ModelledPartickleRender;
import com.vivern.arpg.renders.ParticleTentacle;
import com.vivern.arpg.renders.ParticleTentacleRender;
import com.vivern.arpg.renders.PlacedItemFactory;
import com.vivern.arpg.renders.PresentBoxTESR;
import com.vivern.arpg.renders.RenderAdvFallingBlock;
import com.vivern.arpg.renders.RenderBigLightningStrike;
import com.vivern.arpg.renders.RenderBoomerangFactory;
import com.vivern.arpg.renders.RenderBossLoot;
import com.vivern.arpg.renders.RenderBulletParticle;
import com.vivern.arpg.renders.RenderCoin;
import com.vivern.arpg.renders.RenderFluidHelper;
import com.vivern.arpg.renders.RenderGeomanticCrystal;
import com.vivern.arpg.renders.RenderHangingAllSides;
import com.vivern.arpg.renders.RenderLikeArrow;
import com.vivern.arpg.renders.RenderLiveHeart;
import com.vivern.arpg.renders.RenderModular;
import com.vivern.arpg.renders.RenderModule;
import com.vivern.arpg.renders.RenderParticleGore;
import com.vivern.arpg.renders.RenderRocketFactory;
import com.vivern.arpg.renders.RenderSeaEffloresce;
import com.vivern.arpg.renders.RenderSpecial;
import com.vivern.arpg.renders.RenderSplash;
import com.vivern.arpg.renders.RenderStingingCell;
import com.vivern.arpg.renders.RenderWhip;
import com.vivern.arpg.renders.ResearchTableTESR;
import com.vivern.arpg.renders.RetortTESR;
import com.vivern.arpg.renders.RunicMirrorTESR;
import com.vivern.arpg.renders.SacrificialAltarTESR;
import com.vivern.arpg.renders.ShardRenderFactory;
import com.vivern.arpg.renders.ShimmeringBeastbloomTESR;
import com.vivern.arpg.renders.SoulCatcherTESR;
import com.vivern.arpg.renders.SpearRenderFactory;
import com.vivern.arpg.renders.SpellForgeTESR;
import com.vivern.arpg.renders.SplitterTESR;
import com.vivern.arpg.renders.StarLanternTESR;
import com.vivern.arpg.renders.StormledgePortalTESR;
import com.vivern.arpg.renders.StreamLaserFactory;
import com.vivern.arpg.renders.SweepParticle;
import com.vivern.arpg.renders.SweepParticleFactory;
import com.vivern.arpg.renders.TESRModel;
import com.vivern.arpg.renders.TESRSieve;
import com.vivern.arpg.renders.TeamBannerTESR;
import com.vivern.arpg.renders.TideBeaconTESR;
import com.vivern.arpg.renders.ToxicPortalTESR;
import com.vivern.arpg.renders.VialTESR;
import com.vivern.arpg.renders.VoidCrystalTESR;
import com.vivern.arpg.renders.WhipParticle;
import com.vivern.arpg.renders.mobrender.InitMobRenders;
import com.vivern.arpg.renders.mobrender.RenderBlubber;
import com.vivern.arpg.renders.mobrender.RenderGnater;
import com.vivern.arpg.renders.mobrender.RenderMoonshroom;
import com.vivern.arpg.renders.mobrender.RenderSummonedBlaze;
import com.vivern.arpg.renders.mobrender.RenderTroglodyte;
import com.vivern.arpg.renders.mobrender.RenderWhiteSlime;
import com.vivern.arpg.shader.ShaderMain;
import com.vivern.arpg.tileentity.TileARPGChest;
import com.vivern.arpg.tileentity.TileAdvancedBlockDetector;
import com.vivern.arpg.tileentity.TileAlchemicLab;
import com.vivern.arpg.tileentity.TileAquaticaPortal;
import com.vivern.arpg.tileentity.TileAssemblyAugment;
import com.vivern.arpg.tileentity.TileAssemblyTable;
import com.vivern.arpg.tileentity.TileBioCell;
import com.vivern.arpg.tileentity.TileBookcase;
import com.vivern.arpg.tileentity.TileCalibrationBundle;
import com.vivern.arpg.tileentity.TileChest;
import com.vivern.arpg.tileentity.TileCrystalSphere;
import com.vivern.arpg.tileentity.TileDisenchantmentTable;
import com.vivern.arpg.tileentity.TileDungeonLadder;
import com.vivern.arpg.tileentity.TileDungeonPortal;
import com.vivern.arpg.tileentity.TileElectricSieve;
import com.vivern.arpg.tileentity.TileEntityFrostPortal;
import com.vivern.arpg.tileentity.TileEtheriteInvocator;
import com.vivern.arpg.tileentity.TileGlossary;
import com.vivern.arpg.tileentity.TileGlowingVein;
import com.vivern.arpg.tileentity.TileIndustrialMixer;
import com.vivern.arpg.tileentity.TileItemCharger;
import com.vivern.arpg.tileentity.TileMagicGenerator;
import com.vivern.arpg.tileentity.TileManaBottle;
import com.vivern.arpg.tileentity.TileManaPump;
import com.vivern.arpg.tileentity.TileNexusBeacon;
import com.vivern.arpg.tileentity.TileNexusNiveolite;
import com.vivern.arpg.tileentity.TilePresentBox;
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
import com.vivern.arpg.tileentity.TileToxicPortal;
import com.vivern.arpg.tileentity.TileTritonHearth;
import com.vivern.arpg.tileentity.TileVial;
import com.vivern.arpg.tileentity.TileVoidCrystal;
import com.vivern.arpg.weather.Weather;
import gloomyfolkenvivern.arpghooklib.example.AnnotationHooks;
import gloomyfolkenvivern.arpghooklib.example.coloredlightning.ColoredLightning;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
   public static AnimationTimer animationtimer = new AnimationTimer();
   public static List<ResourceLocation> sand = new ArrayList<>();
   public static List<ResourceLocation> coloredacidtex = new ArrayList<>();
   public static List<ResourceLocation> firedetex = new ArrayList<>();
   public static QuadroBeltModel qmodel;
   public static PhoenixGhostModel phoenixghostmodel;
   public static WizardHatModel wizardhatmodel;
   public static FireMageHatModel firehatmodel;
   public static MagicHoodie magichoodie;
   public static FireLordChestModel firelordchestmodel;
   public static FireLordHelmModel firelordhelmmodel;

   @Override
   public void preInit(FMLPreInitializationEvent event) throws IllegalArgumentException, IllegalAccessException {
      super.preInit(event);
      Keys.register();
      FluidsRegister.registerRender();
      Flicks.instance = new Flicks();
      RenderingRegistry.registerEntityRenderingHandler(EntityFlyApple.class, new SnowballRender(Items.APPLE));
      RenderingRegistry.registerEntityRenderingHandler(
         AppleEffect.class, new RenderSplash("arpg:textures/splash_apple.png", 1.0F, 10, 1.0F, 0.0F, 0.0F, 0.0F, false, -1, DestFactor.ONE)
      );
      RenderingRegistry.registerEntityRenderingHandler(EntityIchor.class, new SnowballRender(null));
      RenderingRegistry.registerEntityRenderingHandler(
         IchorEffect.class, new RenderSplash("arpg:textures/ichor.png", 0.5F, 4, 0.5F, 0.0F, 0.0F, 0.0F, false, -1, DestFactor.ONE)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         SharkRocket.class, new RenderRocketFactory("arpg:textures/shark_rocket.png", SharkRocketModel.class, 1.0F, false)
      );
      RenderingRegistry.registerEntityRenderingHandler(EntityBoomerangMagic.class, new RenderBoomerangFactory(ItemsRegister.MAGICBOOMERANG));
      RenderingRegistry.registerEntityRenderingHandler(GUNParticle.class, new GUNPFactory());
      RenderingRegistry.registerEntityRenderingHandler(
         EntityButterfly.class, new RenderSplash("arpg:textures/butterflyswing.png", 0.7F, 3, 0.7F, 0.7F, 0.0F, 0.0F, false, -1, DestFactor.ONE)
      );
      RenderingRegistry.registerEntityRenderingHandler(EntitySunrise.class, new SpearRenderFactory(ItemsRegister.SUNRISE, 2.5F, true));
      RenderingRegistry.registerEntityRenderingHandler(EntityLaserParticle.class, new LaserFactory());
      RenderingRegistry.registerEntityRenderingHandler(EntityStreamLaserP.class, new StreamLaserFactory());
      RenderingRegistry.registerEntityRenderingHandler(
         EntityVampireKnife.class, new RenderRocketFactory("arpg:textures/vampire_knifes.png", VampireKnifes.class, 1.0F, false)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         BloodDrop.class, new RenderSplash("arpg:textures/blooddrop.png", 0.1F, 1, 0.1F, 0.0F, 0.0F, 0.0F, true, -1, DestFactor.ONE)
      );
      RenderingRegistry.registerEntityRenderingHandler(EntityCubicParticle.class, new CubikFactory());
      RenderingRegistry.registerEntityRenderingHandler(
         EntityFrostBolt.class,
         new BlockEntityFactory(
            new ResourceLocation("arpg:textures/ice_cube.png"), 0.02F, 0.02F, 0.02F, 99, 1.0F, 1.0F, 1.0F, true, 1.0F, 1.0F, 1.0F, 0.08F, true
         )
      );
      RenderingRegistry.registerEntityRenderingHandler(GunPEmitter.class, new GUNPFactory());
      RenderingRegistry.registerEntityRenderingHandler(EntityLaserDisk.class, new JSONFactory());
      RenderingRegistry.registerEntityRenderingHandler(
         ElementProjectile.class, new RenderRocketFactory("arpg:textures/el_projectile_tex.png", ElProjectileModel.class, 1.0F, false)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         StingerBoltEntity.class, new RenderRocketFactory("arpg:textures/stinger_bolt_tex.png", StingerBoltModel.class, 1.0F, false)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         EntityStingerShard.class,
         new BlockEntityFactory(
            new ResourceLocation("arpg:textures/stinger_shard_tex.png"), 0.01F, 0.01F, 0.01F, 99, 1.0F, 1.0F, 1.0F, false, 1.0F, 1.0F, 1.0F, 0.28F, true
         )
      );
      RenderingRegistry.registerEntityRenderingHandler(FireworkEntity.class, new FireworkRenderFactory());
      RenderingRegistry.registerEntityRenderingHandler(
         DragonFireworkEntity.class, new RenderRocketFactory("arpg:textures/dragon_firework_tex.png", DragonFireworkModel.class, 1.0F, false)
      );
      RenderingRegistry.registerEntityRenderingHandler(BetweenParticle.class, new BetweenRenderFactory());
      RenderingRegistry.registerEntityRenderingHandler(FreezingParticle.class, new FreezingFactory());
      RenderingRegistry.registerEntityRenderingHandler(
         EntitySand.class, new RenderSplash("arpg:textures/minisand1.png", 0.02F, 1, 0.02F, 0.0F, 0.0F, 0.0F, false, -1, DestFactor.ONE)
      );
      RenderingRegistry.registerEntityRenderingHandler(TrailParticle.class, new GUNPFactory());
      RenderingRegistry.registerEntityRenderingHandler(
         BilebiterShoot.class, new RenderSplash("arpg:textures/bilebiter_shoot3.png", 0.13F, 1, 0.13F, 0.0F, 0.0F, 0.0F, true, 240, DestFactor.ONE)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         BilebiterHomingShoot.class, new RenderRocketFactory("arpg:textures/bilebiter_homing_tex.png", BilebiterHomingModel.class, 1.0F, false)
      );
      RenderingRegistry.registerEntityRenderingHandler(EntityPlacedItem.class, new PlacedItemFactory());
      RenderingRegistry.registerEntityRenderingHandler(EntityElectricAcidRadiationPotion.class, new SnowballRender(Items.GLASS_BOTTLE));
      RenderingRegistry.registerEntityRenderingHandler(
         StaffFireballEntity.class, new RenderSplash("arpg:textures/fireball.png", 0.2F, 1, 0.2F, 0.0F, 0.0F, 0.0F, true, 240, DestFactor.ONE)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         SnowstormEntity.class, new RenderSplash("arpg:textures/cold.png", 0.3F, 1, 0.3F, 0.0F, 0.0F, 0.0F, true, 150, DestFactor.ONE)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         EntityElectricBolt.class, new RenderSplash("arpg:textures/electric_bolt.png", 0.3F, 1, 0.3F, 0.0F, 0.0F, 0.0F, true, 240, DestFactor.ONE)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         EntitySlimeBullet.class, new RenderRocketFactory("arpg:textures/slimebullet.png", CubikModel.class, 0.3F, true)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         EntityFiremageSetBonus.class, new RenderSplash("arpg:textures/fireball.png", 0.15F, 1, 0.15F, 0.0F, 0.0F, 0.0F, true, 240, DestFactor.ONE)
      );
      RenderingRegistry.registerEntityRenderingHandler(GraplingHookParticle.class, new GHFactory());
      RenderingRegistry.registerEntityRenderingHandler(
         EnderSeerProjectile.class, new RenderSplash("arpg:textures/ender_seer_proj.png", 0.15F, 1, 0.15F, 0.0F, 0.0F, 0.0F, false, 240, DestFactor.ONE)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         FishingHook.class, new RenderSplash("arpg:textures/floater1.png", 0.05F, 1, 0.15F, 0.0F, 0.0F, 0.0F, false, 240, DestFactor.ONE)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         NetherGrinderBullet.class,
         new RenderModular.RenderModularFactory()
            .add(
               new RenderModule.RModCubic(
                  new ResourceLocation("arpg:textures/nether_grinder_bullet_tex.png"), 0.002F, -1, 1.0F, 1.0F, 1.0F, 1.0F, false, 1, 0.0F
               )
            )
            .add(
               new RenderModule.RModCeratargetTail(new ResourceLocation("arpg:textures/bullet_volumetric_tail.png"), 1.5F, 0.5F, 3.0F, 0.012F, 0.006F, -1)
                  .setMulticolored()
            )
      );
      RenderingRegistry.registerEntityRenderingHandler(CannonSnowball.class, new SnowballRender(Items.SNOWBALL));
      RenderingRegistry.registerEntityRenderingHandler(EntitySnowflakeShuriken.class, new RenderBoomerangFactory(ItemsRegister.SNOWFLAKESHUR));
      RenderingRegistry.registerEntityRenderingHandler(
         GraveLurkerProjectile.class, new RenderSplash("arpg:textures/fireball2.png", 0.2F, 1, 0.2F, 0.0F, 0.0F, 0.0F, true, 240, DestFactor.ONE)
      );
      RenderingRegistry.registerEntityRenderingHandler(EntityThrowedReaper.class, new RenderBoomerangFactory(ItemsRegister.REAPER));
      RenderingRegistry.registerEntityRenderingHandler(
         EntitySwordGhost.class, new RenderSplash("arpg:textures/sword_ghost.png", 0.17F, 1, 0.17F, 0.0F, 0.0F, 0.0F, true, 240, DestFactor.ONE)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         ToxicNuke.class, new RenderRocketFactory("arpg:textures/toxic_nuke_model_tex.png", ToxicNukeModel.class, 1.0F, false)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         ToxicNukeShard.class, new RenderSplash("arpg:textures/acid_splash3.png", 0.3F, 1, 0.3F, 0.0F, 0.0F, 0.0F, true, 200, DestFactor.SRC_COLOR)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         CryoGunEntity.class, new RenderRocketFactory("arpg:textures/cryo_gun_entity_model_tex.png", CryoGunEntityModel.class, 1.4F, false)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         EntityMinigunIcicle.class, new RenderRocketFactory("arpg:textures/minigun_icicle_model_tex.png", MinigunIcicleModel.class, 1.0F, false)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         EntitySummon.class, new RenderSplash("arpg:textures/entity_summon.png", 0.18F, 1, 0.18F, 0.0F, 0.0F, 0.0F, true, 240, DestFactor.ONE)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         PlasmaRailgunShoot.class,
         new BlockEntityFactory(
            new ResourceLocation("arpg:textures/plasma_railgun_bullet_tex.png"),
            0.003F,
            0.003F,
            0.01F,
            240,
            1.0F,
            1.0F,
            1.0F,
            false,
            1.0F,
            1.0F,
            1.0F,
            0.08F,
            false
         )
      );
      RenderingRegistry.registerEntityRenderingHandler(
         EntityHeadShooter.class,
         new BlockEntityFactory(
            new ResourceLocation("arpg:textures/nether_grinder_bullet_tex.png"),
            0.0035F,
            0.0035F,
            0.0035F,
            220,
            1.0F,
            1.0F,
            1.0F,
            false,
            1.0F,
            1.0F,
            1.0F,
            0.08F,
            false
         )
      );
      RenderingRegistry.registerEntityRenderingHandler(
         PlasmaPistolShoot.class, new RenderSplash("arpg:textures/plasma_pistol_shoot.png", 0.2F, 1, 0.2F, 0.0F, 0.0F, 0.0F, true, 240, DestFactor.ONE)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         EntityBogFlower.class, new RenderSplash("arpg:textures/blob.png", 0.08F, 1, 0.08F, 0.0F, 0.0F, 0.0F, true, 150, DestFactor.SRC_COLOR)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         PistolFishStrike.class, new RenderRocketFactory("arpg:textures/pistol_fish_strike_model_tex.png", PistolFishStrikeModel.class, 1.0F, false)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         BubbleFishShoot.class, new RenderSplash("arpg:textures/blob.png", 0.06F, 1, 0.06F, 0.0F, 0.0F, 0.0F, true, -1, DestFactor.ONE)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         LavaDropperShoot.class, new RenderSplash("arpg:textures/lava_dropper_shoot.png", 0.3F, 12, 0.3F, 0.0F, 0.0F, 0.0F, false, 240, DestFactor.ONE)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         PlasmaAcceleratorShoot.class,
         new RenderSplash("arpg:textures/plasma_accelerator_shoot.png", 0.25F, 1, 0.25F, 0.0F, 0.0F, 0.0F, true, 240, DestFactor.ONE)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         VacuumGunShoot.class,
         new BlockEntityFactory(
            new ResourceLocation("arpg:textures/plasma_railgun_bullet_tex.png"),
            0.005F,
            0.005F,
            0.005F,
            200,
            0.8F,
            0.6F,
            1.0F,
            false,
            1.0F,
            1.0F,
            1.0F,
            0.08F,
            false
         )
      );
      RenderingRegistry.registerEntityRenderingHandler(EntityShard.class, new ShardRenderFactory());
      RenderingRegistry.registerEntityRenderingHandler(
         GemStaffShoot.class, new RenderSplash("arpg:textures/simple_magic_shoot.png", 0.21F, 1, 0.21F, 0.0F, 0.0F, 0.0F, true, 200, DestFactor.ONE, true)
      );
      RenderingRegistry.registerEntityRenderingHandler(SweepParticle.class, new SweepParticleFactory());
      RenderingRegistry.registerEntityRenderingHandler(
         EntityArrowFirejet.class, new CustomArrowFactory(new ResourceLocation("arpg:textures/arrow_firejet.png"))
      );
      RenderingRegistry.registerEntityRenderingHandler(CustomMobProjectile.class, new CustomProjectileRender.CustomProjectileFactory());
      RenderingRegistry.registerEntityRenderingHandler(
         EntityAcidFire.class, new RenderSplash("arpg:textures/acid_orb.png", 0.05F, 1, 0.05F, 0.0F, 0.0F, 0.0F, true, 240, DestFactor.ONE)
      );
      RenderingRegistry.registerEntityRenderingHandler(ParticleGore.class, new RenderParticleGore.ParticleGoreFactory());
      RenderingRegistry.registerEntityRenderingHandler(
         NailGunShoot.class, new CustomArrowFactory(new ResourceLocation("arpg:textures/nail_gun_shoot.png"), 0.06625F, -1)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         CrystalStarShoot.class,
         new RenderRocketFactory("arpg:textures/crystal_star_shoot_model_tex.png", CrystalStarShootModel.class, 3.0F, false, 0.0027F, false)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         LordOfPainSpike.class, new RenderSpecial.RenderSpecialFactory(1, new ResourceLocation("arpg:textures/lord_of_pain_spike_model_tex.png"))
      );
      RenderingRegistry.registerEntityRenderingHandler(
         CrystalFanShoot.class, new RenderSpecial.RenderSpecialFactory(2, new ResourceLocation("arpg:textures/crystal_fan_shoot.png"))
      );
      RenderingRegistry.registerEntityRenderingHandler(
         CrystalFanBonus.class, new RenderSplash("arpg:textures/crystal_bonus.png", 0.36F, 14, 0.36F, 0.0F, 0.0F, 0.0F, true, 100, DestFactor.ONE)
      );
      RenderingRegistry.registerEntityRenderingHandler(EntityCoin.class, new RenderCoin.RenderCoinFactory());
      RenderingRegistry.registerEntityRenderingHandler(EntityGeomanticCrystal.class, new RenderGeomanticCrystal.GeomanticCrystalFactory());
      RenderingRegistry.registerEntityRenderingHandler(
         ThornkeeperShoot.class, new RenderRocketFactory("arpg:textures/thornkeeper_shoot.png", ThornkeeperShootModel.class, 0.7F, false)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         EntityThistleThorn.class, new RenderRocketFactory("arpg:textures/entity_thistle_thorn_model_tex.png", EntityThistleThornModel.class, 1.1F, false)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         SpikedBurst.class, new RenderRocketFactory("arpg:textures/spiked_burst_model_tex.png", SpikedBurstModel.class, 1.0F, false)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         EntityRestlessSkull.class, new RenderSplash("arpg:textures/simple_magic_shoot.png", 0.13F, 1, 0.13F, 0.0F, 0.0F, 0.0F, true, 220, DestFactor.ONE)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         EntityMagicRocket.class, new RenderSplash("arpg:textures/magic_rocket.png", 0.22F, 1, 0.22F, 0.0F, 0.0F, 0.0F, true, 240, DestFactor.ONE, true)
      );
      RenderingRegistry.registerEntityRenderingHandler(StingingCellEntity.class, new RenderStingingCell.StingingCellRenderFactory());
      RenderingRegistry.registerEntityRenderingHandler(EntitySeaEffloresce.class, new RenderSeaEffloresce.SeaEffloresceFactory());
      RenderingRegistry.registerEntityRenderingHandler(
         CoralRifleBullet.class, new RenderLikeArrow.RenderLikeArrowFactory(new ResourceLocation("arpg:textures/coral_bullet_arrow_tex.png"), 0.03F, 150, true)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         EntityTimelessSword.class, new RenderSpecial.RenderSpecialFactory(3, new ResourceLocation("arpg:textures/entity_timeless_sword.png"))
      );
      RenderingRegistry.registerEntityRenderingHandler(WhipParticle.class, new RenderWhip.RenderWhipFactory());
      RenderingRegistry.registerEntityRenderingHandler(EntityFrostfireExplosive.class, new RenderSpecial.RenderSpecialFactory(5, TextureMap.LOCATION_BLOCKS_TEXTURE));
      RenderingRegistry.registerEntityRenderingHandler(EntityAcidBomb.class, new RenderSpecial.RenderSpecialFactory(6, TextureMap.LOCATION_BLOCKS_TEXTURE));
      RenderingRegistry.registerEntityRenderingHandler(
         EntityAcidBomb.AcidBombBlob.class,
         new BlockEntityFactory(
            new ResourceLocation("arpg:textures/slimebullet.png"), 0.02F, 0.02F, 0.02F, 200, 1.0F, 0.9F, 1.0F, false, 1.0F, 1.0F, 1.0F, 0.14F, true
         )
      );
      RenderingRegistry.registerEntityRenderingHandler(
         EntityMagneticField.class, new RenderSpecial.RenderSpecialFactory(7, new ResourceLocation("arpg:textures/forcefield.png"))
      );
      RenderingRegistry.registerEntityRenderingHandler(
         HadronBlasterShoot.class,
         new RenderSplash("arpg:textures/simple_magic_shoot.png", 0.14F, 1, 0.14F, 0.0F, 0.0F, 0.0F, true, 240, DestFactor.ONE, true)
            .setColor(1.0F, 0.1F, 0.1F)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         HadronBlasterShoot.HadronBlasterBonus.class,
         new RenderSplash("arpg:textures/stone3_tr.png", 0.3F, 17, 0.3F, 0.0F, 0.0F, 0.0F, true, 220, DestFactor.ONE, true)
      );
      RenderingRegistry.registerEntityRenderingHandler(AnimatedGParticle.class, new AnimatedGunPRender.AnimGUNPFactory());
      RenderingRegistry.registerEntityRenderingHandler(
         EntitySnapball.class, new RenderSpecial.RenderSpecialFactory(8, new ResourceLocation("arpg:textures/snapball.png"))
      );
      RenderingRegistry.registerEntityRenderingHandler(
         EntityLaunchedRocket.class, new RenderRocketFactory("arpg:textures/rocket.png", RocketModel.class, 1.3F, false, 0.0F, true)
      );
      RenderingRegistry.registerEntityRenderingHandler(EntityMiniNuke.class, new RenderSpecial.RenderSpecialFactory(9, TextureMap.LOCATION_BLOCKS_TEXTURE));
      RenderingRegistry.registerEntityRenderingHandler(
         NexusCap.class, new RenderSplash("arpg:textures/simple_magic_shoot.png", 0.0F, 1, 0.0F, 0.0F, 0.0F, 0.0F, false, 200, DestFactor.ONE)
      );
      RenderingRegistry.registerEntityRenderingHandler(EntityChainMace.class, new RenderSpecial.RenderSpecialFactory(10, TextureMap.LOCATION_BLOCKS_TEXTURE));
      RenderingRegistry.registerEntityRenderingHandler(EntityGrenade.class, new RenderSpecial.RenderSpecialFactory(11, TextureMap.LOCATION_BLOCKS_TEXTURE));
      RenderingRegistry.registerEntityRenderingHandler(AdvancedFallingBlock.class, new RenderAdvFallingBlock.RenderAdvFallBlockFactory());
      RenderingRegistry.registerEntityRenderingHandler(EntityLiveHeart.class, new RenderLiveHeart.RenderLiveHeartFactory());
      RenderingRegistry.registerEntityRenderingHandler(BulletParticle.class, new RenderBulletParticle.BulletParticleFactory());
      RenderingRegistry.registerEntityRenderingHandler(BlowholeShoot.class, new RenderSpecial.RenderSpecialFactory(12, TextureMap.LOCATION_BLOCKS_TEXTURE));
      RenderingRegistry.registerEntityRenderingHandler(EntityCrystalCutter.class, new RenderSpecial.RenderSpecialFactory(13, TextureMap.LOCATION_BLOCKS_TEXTURE));
      RenderingRegistry.registerEntityRenderingHandler(
         CeratargetShoot.class, new RenderSpecial.RenderSpecialFactory(14, new ResourceLocation("arpg:textures/ceratarget_shoot.png"))
      );
      RenderingRegistry.registerEntityRenderingHandler(EntityArrowFrozen.class, new CustomArrowFactory(new ResourceLocation("arpg:textures/arrow_frozen.png")));
      RenderingRegistry.registerEntityRenderingHandler(
         EntityArrowVicious.class, new CustomArrowFactory(new ResourceLocation("arpg:textures/arrow_vicious.png"))
      );
      RenderingRegistry.registerEntityRenderingHandler(EntityArrowModern.class, new CustomArrowFactory(new ResourceLocation("arpg:textures/arrow_modern.png")));
      RenderingRegistry.registerEntityRenderingHandler(
         EntityArrowBengal.class, new CustomArrowFactory(new ResourceLocation("arpg:textures/arrow_bengal.png"), 0.05625F, 200)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         EntityArrowFish.class, new CustomArrowFactory(new ResourceLocation("arpg:textures/arrow_fish.png")).setTilt(false).setHorizontalShake(true)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         EntityArrowVoid.class, new CustomArrowFactory(new ResourceLocation("arpg:textures/arrow_void.png"), 0.058F, 160).setBlend(true)
      );
      RenderingRegistry.registerEntityRenderingHandler(EntityArrowShell.class, new CustomArrowFactory(new ResourceLocation("arpg:textures/arrow_shell.png")));
      RenderingRegistry.registerEntityRenderingHandler(
         ShellShard.class, new RenderRocketFactory("arpg:textures/shell_shard_model_tex.png", ShellShardModel.class, 1.5F, false)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         EntityArrowBouncing.class, new CustomArrowFactory(new ResourceLocation("arpg:textures/arrow_bouncing.png")).setBlend(true)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         EntityArrowMithril.class, new CustomArrowFactory(new ResourceLocation("arpg:textures/arrow_mithril.png"))
      );
      RenderingRegistry.registerEntityRenderingHandler(EntityArrowTwin.class, new CustomArrowFactory(new ResourceLocation("arpg:textures/arrow_twin.png")));
      RenderingRegistry.registerEntityRenderingHandler(
         EntityArrowWind.class, new CustomArrowFactory(new ResourceLocation("arpg:textures/arrow_wind.png")).setBlend(true)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         AzureOreShoot.class, new RenderSplash("arpg:textures/azure_ore_shoot.png", 0.17F, 4, 0.17F, 0.0F, 0.0F, 0.0F, true, 230, DestFactor.ONE)
      );
      RenderingRegistry.registerEntityRenderingHandler(ModelledPartickle.class, new ModelledPartickleRender.ModelledPartickleFactory());
      RenderingRegistry.registerEntityRenderingHandler(
         ViolenceShoot.class, new RenderSplash("arpg:textures/fireball2.png", 0.16F, 1, 0.16F, 0.0F, 0.0F, 0.0F, true, 230, DestFactor.ONE)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         WhispersShoot.class, new RenderSpecial.RenderSpecialFactory(17, new ResourceLocation("arpg:textures/whispers_shoot.png"))
      );
      RenderingRegistry.registerEntityRenderingHandler(EntitySpellForgeCatalyst.class, new RenderSpecial.RenderSpecialFactory(19, TextureMap.LOCATION_BLOCKS_TEXTURE));
      RenderingRegistry.registerEntityRenderingHandler(ParticleTentacle.class, new ParticleTentacleRender.ParticleTentacleFactory());
      RenderingRegistry.registerEntityRenderingHandler(
         XmassRocket.class, new RenderRocketFactory("arpg:textures/xmass_rocket_model_tex.png", XmassRocketModel.class, 1.0F, false, 0.0F, false)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         XmassBall.class, new RenderRocketFactory("arpg:textures/xmass_rocket_model_tex.png", XmassBallModel.class, 2.0F, false, 0.0F, false)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         CoralPolyp.class, new RenderSpecial.RenderSpecialFactory(20, new ResourceLocation("arpg:textures/coral_polyp_body.png"))
      );
      RenderingRegistry.registerEntityRenderingHandler(
         EnigmateTwinsShoot.class,
         new RenderSplash("arpg:textures/enigmate_shoot.png", 0.05F, 4, 0.05F, 0.0F, 0.0F, 0.0F, true, 240, DestFactor.ONE_MINUS_SRC_ALPHA)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         CryonedBlock.class,
         new RenderModular.RenderModularFactory()
            .add(
               new RenderModule.RModRenderByBoundingBox(new ResourceLocation("arpg:textures/cryoned_block.png"), 140, 1.0F, 1.0F, 1.0F, 1.0F, true, false)
                  .setBlendFunc(SourceFactor.SRC_COLOR, DestFactor.ONE_MINUS_DST_COLOR)
            )
      );
      RenderingRegistry.registerEntityRenderingHandler(
         CryoDestroyerSpray.class,
         new RenderModular.RenderModularFactory()
            .setDelayedRender(2)
            .add(
               new RenderModule.RModTail(new ResourceLocation("arpg:textures/hail_trace.png"), 0.1F, 2.5F, false, 0.25F, 170, 0.5F, 0.5F, 0.5F, 1.0F, true, 0)
                  .setBlendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE)
            )
      );
      RenderingRegistry.registerEntityRenderingHandler(
         WandColdShoot.class,
         new RenderModular.RenderModularFactory()
            .add(
               new RenderModule.RModTail(new ResourceLocation("arpg:textures/cold_tail.png"), 0.15F, 3.2F, true, 0.125F, 200, 1.0F, 1.0F, 1.0F, 1.0F, true, 0)
                  .setBlendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE)
            )
            .add(
               new RenderModule.RMod2dSprite(new ResourceLocation("arpg:textures/simple_magic_shoot.png"), 0.18F, 200, 0.4F, 0.7F, 1.0F, 1.0F, true, 0.0F)
                  .setBlendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE)
            )
      );
      RenderingRegistry.registerEntityRenderingHandler(
         WandColdWave.class,
         new RenderModular.RenderModularFactory()
            .add(
               new RenderModule.RModCutter(
                     new ResourceLocation("arpg:textures/wand_of_cold_wave.png"),
                     0.0F,
                     3.0F,
                     0.13F,
                     200,
                     1.0F,
                     1.0F,
                     1.0F,
                     1.0F,
                     true,
                     0.0F,
                     WandColdWave.ticksForMaxWidth,
                     28,
                     7
                  )
                  .setBlendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE)
            )
      );
      RenderingRegistry.registerEntityRenderingHandler(
         SurvivorLootSpawner.class,
         new RenderModular.RenderModularFactory()
            .add(new RenderModule.RModCubic(new ResourceLocation("arpg:textures/survivor_loot_spawner.png"), 0.16F, -1, 1.0F, 1.0F, 1.0F, 1.0F, false, 1, 0.0F))
            .add(
               new RenderModule.RModCeratargetTail(
                  new ResourceLocation("arpg:textures/bullet_volumetric_tail.png"), 120.0F, 0.5F, 20.0F, 0.48000002F, 0.24000001F, -1
               )
            )
      );
      CrystalStarShootModel crystalStarShootModel = new CrystalStarShootModel();
      RenderingRegistry.registerEntityRenderingHandler(
         CrystalStarPoweredShoot.class,
         new RenderModular.RenderModularFactory()
            .add(
               new RenderModule.RModModel(
                  new ResourceLocation("arpg:textures/crystal_star_shoot_model_tex.png"),
                  crystalStarShootModel,
                  0.15F,
                  -1,
                  1.0F,
                  1.0F,
                  1.0F,
                  1.0F,
                  false,
                  2,
                  0.0F,
                  1
               )
            )
            .add(
               new RenderModule.RModModel(
                  new ResourceLocation("arpg:textures/crystal_star_shoot_model_tex.png"),
                  crystalStarShootModel,
                  0.15F,
                  -1,
                  1.0F,
                  1.0F,
                  1.0F,
                  1.0F,
                  false,
                  2,
                  0.0F,
                  15
               )
            )
            .add(
               new RenderModule.RModModel(
                  new ResourceLocation("arpg:textures/crystal_star_shoot_model_tex.png"),
                  crystalStarShootModel,
                  0.15F,
                  -1,
                  1.0F,
                  1.0F,
                  1.0F,
                  1.0F,
                  false,
                  2,
                  0.0F,
                  13
               )
            )
            .add(
               new RenderModule.RModModel(
                  new ResourceLocation("arpg:textures/crystal_star_shoot_model_tex.png"),
                  crystalStarShootModel,
                  0.15F,
                  -1,
                  1.0F,
                  1.0F,
                  1.0F,
                  1.0F,
                  false,
                  2,
                  0.0F,
                  88
               )
            )
            .add(
               new RenderModule.RModModel(
                  new ResourceLocation("arpg:textures/crystal_star_shoot_model_tex.png"),
                  crystalStarShootModel,
                  0.15F,
                  -1,
                  1.0F,
                  1.0F,
                  1.0F,
                  1.0F,
                  false,
                  2,
                  0.0F,
                  959
               )
            )
            .add(
               new RenderModule.RModModel(
                  new ResourceLocation("arpg:textures/crystal_star_shoot_model_tex.png"),
                  crystalStarShootModel,
                  0.15F,
                  -1,
                  1.0F,
                  1.0F,
                  1.0F,
                  1.0F,
                  false,
                  2,
                  0.0F,
                  113
               )
            )
            .add(
               new RenderModule.RModModel(
                  new ResourceLocation("arpg:textures/crystal_star_shoot_model_tex.png"),
                  crystalStarShootModel,
                  0.15F,
                  -1,
                  1.0F,
                  1.0F,
                  1.0F,
                  1.0F,
                  false,
                  2,
                  0.0F,
                  4
               )
            )
            .add(
               new RenderModule.RModModel(
                  new ResourceLocation("arpg:textures/crystal_star_shoot_model_tex.png"),
                  crystalStarShootModel,
                  0.15F,
                  -1,
                  1.0F,
                  1.0F,
                  1.0F,
                  1.0F,
                  false,
                  2,
                  0.0F,
                  7
               )
            )
      );
      RenderingRegistry.registerEntityRenderingHandler(
         PlasmaRifleBall.class,
         new RenderModular.RenderModularFactory()
            .add(
               new RenderModule.RMod2dSprite(new ResourceLocation("arpg:textures/plasma_rifle_ball.png"), 0.17F, 240, 1.0F, 1.0F, 1.0F, 1.0F, true, 0.0F)
                  .setAnimation(34, 1, 0, false, true)
                  .setBlendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE)
                  .setMulticolored()
            )
      );
      RenderingRegistry.registerEntityRenderingHandler(
         EntityWeatherRocket.class,
         new RenderModular.RenderModularFactory()
            .add(new RenderModule.RModModel(null, new WeatherRocketModel(), 0.0625F, -1, 1.0F, 1.0F, 1.0F, 1.0F, false, 3, 0.0F))
      );
      RenderingRegistry.registerEntityRenderingHandler(EntityHangingAllSides.class, new RenderHangingAllSides.RenderHangingAllSidesFactory());
      RenderingRegistry.registerEntityRenderingHandler(BigLightningStrike.class, new RenderBigLightningStrike.BigLightningStrikeFactory());
      RenderingRegistry.registerEntityRenderingHandler(EntityBossLoot.class, new RenderBossLoot.BossLootFactory());
      RenderingRegistry.registerEntityRenderingHandler(SummonedBlaze.class, RenderSummonedBlaze.FACTORY);
      RenderingRegistry.registerEntityRenderingHandler(Gnater.class, RenderGnater.FACTORY);
      RenderingRegistry.registerEntityRenderingHandler(WhiteSlime.class, RenderWhiteSlime.FACTORY);
      RenderingRegistry.registerEntityRenderingHandler(Troglodyte.class, RenderTroglodyte.FACTORY);
      RenderingRegistry.registerEntityRenderingHandler(Moonshroom.class, RenderMoonshroom.FACTORY);
      RenderingRegistry.registerEntityRenderingHandler(Blubber.class, RenderBlubber.FACTORY);
      RenderingRegistry.registerEntityRenderingHandler(EntityPart.class, new RenderSpecial.RenderSpecialFactory(-1, null));
      ToxicomaniaMobsPack.initRender();
      NPCMobsPack.initRender();
      EverfrostMobsPack.initRender();
      StormledgeMobsPack.initRender();
      AquaticaMobsPack.initRender();
      OtherMobsPack.initRender();
      DungeonMobsPack.initRender();
      HostileProjectiles.registerRender();
      InitMobRenders.init();
      ColoredLightning.preInitLight();
   }

   @Override
   public void init(FMLInitializationEvent event) {
      super.init(event);
      ItemsRegister.registerItemsRender();
      BlocksRegister.registerBlocksRender();
      ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFrostPortal.class, new FrostPortalTESR());
      ClientRegistry.bindTileEntitySpecialRenderer(TileAdvancedBlockDetector.class, new AdvancedBlockDetectorTESR());
      ClientRegistry.bindTileEntitySpecialRenderer(TileGlowingVein.class, new GlowingVeinTESR());
      ClientRegistry.bindTileEntitySpecialRenderer(TileCrystalSphere.class, new CrystalSphereTESR());
      ClientRegistry.bindTileEntitySpecialRenderer(TileSpellForge.class, new SpellForgeTESR());
      ClientRegistry.bindTileEntitySpecialRenderer(TileSacrificialAltar.class, new SacrificialAltarTESR());
      ClientRegistry.bindTileEntitySpecialRenderer(TileAlchemicLab.class, new AlchemicLabTESR());
      ClientRegistry.bindTileEntitySpecialRenderer(TileChest.class, new ChestsTESR());
      ClientRegistry.bindTileEntitySpecialRenderer(TileRunicMirror.class, new RunicMirrorTESR());
      ClientRegistry.bindTileEntitySpecialRenderer(TileManaBottle.class, new ManaBottleTESR());
      ClientRegistry.bindTileEntitySpecialRenderer(TileStarLantern.class, new StarLanternTESR());
      ClientRegistry.bindTileEntitySpecialRenderer(TilePresentBox.class, new PresentBoxTESR());
      ClientRegistry.bindTileEntitySpecialRenderer(TileShimmeringBeastbloom.class, new ShimmeringBeastbloomTESR());
      ClientRegistry.bindTileEntitySpecialRenderer(TileBioCell.class, new BioCellTESR());
      ClientRegistry.bindTileEntitySpecialRenderer(TileDungeonLadder.class, new DungeonLadderTESR());
      ClientRegistry.bindTileEntitySpecialRenderer(TileToxicPortal.class, new ToxicPortalTESR());
      ClientRegistry.bindTileEntitySpecialRenderer(TileDungeonPortal.class, new DungeonPortalTESR());
      ClientRegistry.bindTileEntitySpecialRenderer(TileVoidCrystal.class, new VoidCrystalTESR());
      ClientRegistry.bindTileEntitySpecialRenderer(TileNexusBeacon.class, new TideBeaconTESR());
      ClientRegistry.bindTileEntitySpecialRenderer(TileResearchTable.class, new ResearchTableTESR());
      ClientRegistry.bindTileEntitySpecialRenderer(TileSplitter.class, new SplitterTESR());
      ClientRegistry.bindTileEntitySpecialRenderer(TileRetort.class, new RetortTESR());
      ClientRegistry.bindTileEntitySpecialRenderer(TileVial.class, new VialTESR());
      ClientRegistry.bindTileEntitySpecialRenderer(TileBookcase.class, new BookcaseTESR());
      ClientRegistry.bindTileEntitySpecialRenderer(TileGlossary.class, new GlossaryTESR());
      ClientRegistry.bindTileEntitySpecialRenderer(TileDisenchantmentTable.class, new DisenchantmentTableTESR());
      ClientRegistry.bindTileEntitySpecialRenderer(
         TileTritonHearth.class, new TESRModel(new TritonHearthModel(), new ResourceLocation("arpg:textures/triton_hearth_model_tex.png"))
      );
      ClientRegistry.bindTileEntitySpecialRenderer(TileIndustrialMixer.class, new IndustrialMixerTESR());
      ClientRegistry.bindTileEntitySpecialRenderer(TileAssemblyTable.class, new AssemblyTableTESR());
      ClientRegistry.bindTileEntitySpecialRenderer(TileAssemblyAugment.class, new AssemblyAugmentTESR());
      ClientRegistry.bindTileEntitySpecialRenderer(
         TileNexusNiveolite.class, new TESRModel(new TileNexusNiveoliteModel(), new ResourceLocation("arpg:textures/tile_nexus_niveolite_model_tex.png"))
      );
      ClientRegistry.bindTileEntitySpecialRenderer(TileCalibrationBundle.class, new CalibrationBundleTESR());
      ClientRegistry.bindTileEntitySpecialRenderer(TileSoulCatcher.class, new SoulCatcherTESR());
      ClientRegistry.bindTileEntitySpecialRenderer(TileManaPump.class, new ManaPumpTESR());
      ClientRegistry.bindTileEntitySpecialRenderer(TileSieve.class, new TESRSieve(new SieveModel(), new ResourceLocation("arpg:textures/sieve_model_tex.png")));
      ClientRegistry.bindTileEntitySpecialRenderer(
         TileElectricSieve.class, new TESRSieve(new ElectricSieveModel(), new ResourceLocation("arpg:textures/electric_sieve_model_tex.png"))
      );
      ClientRegistry.bindTileEntitySpecialRenderer(
         TileItemCharger.class, new ItemChargerTESR(new ItemChargerModel(), new ResourceLocation("arpg:textures/item_charger_model_tex.png"))
      );
      ClientRegistry.bindTileEntitySpecialRenderer(TileARPGChest.class, new ARPGChestTESR());
      ClientRegistry.bindTileEntitySpecialRenderer(TileAquaticaPortal.class, new AquaticaPortalTESR());
      ClientRegistry.bindTileEntitySpecialRenderer(TileStormledgePortal.class, new StormledgePortalTESR());
      ClientRegistry.bindTileEntitySpecialRenderer(TileMagicGenerator.class, new MagicGeneratorTESR());
      ClientRegistry.bindTileEntitySpecialRenderer(
         TileEtheriteInvocator.class,
         new TESRModel(
            new EtheriteInvocatorModel(),
            new ResourceLocation("arpg:textures/etherite_invocator_model_tex.png"),
            tile -> ((TileEtheriteInvocator)tile).hasCell ? new float[]{1.0F} : null
         )
      );
      ClientRegistry.bindTileEntitySpecialRenderer(TileTeamBanner.class, new TeamBannerTESR());
      qmodel = new QuadroBeltModel();
      qmodel.bipedBody.showModel = false;
      qmodel.bipedHead.showModel = false;
      qmodel.bipedHeadwear.showModel = false;
      qmodel.bipedLeftArm.showModel = false;
      qmodel.bipedRightArm.showModel = false;
      qmodel.bipedLeftLeg.showModel = false;
      qmodel.bipedRightLeg.showModel = false;
      phoenixghostmodel = new PhoenixGhostModel();
      wizardhatmodel = new WizardHatModel();
      wizardhatmodel.bipedBody.showModel = false;
      wizardhatmodel.bipedHead.showModel = false;
      wizardhatmodel.bipedHeadwear.showModel = false;
      wizardhatmodel.bipedLeftArm.showModel = false;
      wizardhatmodel.bipedRightArm.showModel = false;
      wizardhatmodel.bipedLeftLeg.showModel = false;
      wizardhatmodel.bipedRightLeg.showModel = false;
      firehatmodel = new FireMageHatModel();
      firehatmodel.bipedBody.showModel = false;
      firehatmodel.bipedHead.showModel = false;
      firehatmodel.bipedHeadwear.showModel = false;
      firehatmodel.bipedLeftArm.showModel = false;
      firehatmodel.bipedRightArm.showModel = false;
      firehatmodel.bipedLeftLeg.showModel = false;
      firehatmodel.bipedRightLeg.showModel = false;
      magichoodie = new MagicHoodie();
      magichoodie.bipedBody.showModel = false;
      magichoodie.bipedHead.showModel = false;
      magichoodie.bipedHeadwear.showModel = false;
      magichoodie.bipedLeftArm.showModel = false;
      magichoodie.bipedRightArm.showModel = false;
      magichoodie.bipedLeftLeg.showModel = false;
      magichoodie.bipedRightLeg.showModel = false;
      firelordchestmodel = new FireLordChestModel();
      firelordhelmmodel = new FireLordHelmModel();
      sand.add(new ResourceLocation("arpg:textures/de_sand/sand1.png"));
      sand.add(new ResourceLocation("arpg:textures/de_sand/sand2.png"));
      sand.add(new ResourceLocation("arpg:textures/de_sand/sand3.png"));
      sand.add(new ResourceLocation("arpg:textures/de_sand/sand4.png"));
      sand.add(new ResourceLocation("arpg:textures/de_sand/sand5.png"));
      sand.add(new ResourceLocation("arpg:textures/de_sand/sand6.png"));
      sand.add(new ResourceLocation("arpg:textures/de_sand/sand7.png"));
      sand.add(new ResourceLocation("arpg:textures/de_sand/sand8.png"));
      sand.add(new ResourceLocation("arpg:textures/de_sand/sand9.png"));
      sand.add(new ResourceLocation("arpg:textures/de_sand/sand10.png"));
      sand.add(new ResourceLocation("arpg:textures/de_sand/sand11.png"));
      sand.add(new ResourceLocation("arpg:textures/de_sand/sand12.png"));
      sand.add(new ResourceLocation("arpg:textures/de_sand/sand13.png"));
      sand.add(new ResourceLocation("arpg:textures/de_sand/sand14.png"));
      sand.add(new ResourceLocation("arpg:textures/de_sand/sand15.png"));
      sand.add(new ResourceLocation("arpg:textures/de_sand/sand16.png"));
      sand.add(new ResourceLocation("arpg:textures/de_sand/sand17.png"));
      sand.add(new ResourceLocation("arpg:textures/de_sand/sand18.png"));
      sand.add(new ResourceLocation("arpg:textures/de_sand/sand19.png"));
      sand.add(new ResourceLocation("arpg:textures/de_sand/sand19.png"));
      coloredacidtex.add(new ResourceLocation("arpg:textures/de_acid/acid1.png"));
      coloredacidtex.add(new ResourceLocation("arpg:textures/de_acid/acid2.png"));
      coloredacidtex.add(new ResourceLocation("arpg:textures/de_acid/acid3.png"));
      coloredacidtex.add(new ResourceLocation("arpg:textures/de_acid/acid4.png"));
      coloredacidtex.add(new ResourceLocation("arpg:textures/de_acid/acid5.png"));
      coloredacidtex.add(new ResourceLocation("arpg:textures/de_acid/acid6.png"));
      coloredacidtex.add(new ResourceLocation("arpg:textures/de_acid/acid7.png"));
      coloredacidtex.add(new ResourceLocation("arpg:textures/de_acid/acid8.png"));
      coloredacidtex.add(new ResourceLocation("arpg:textures/de_acid/acid9.png"));
      coloredacidtex.add(new ResourceLocation("arpg:textures/de_acid/acid10.png"));
      coloredacidtex.add(new ResourceLocation("arpg:textures/de_acid/acid11.png"));
      coloredacidtex.add(new ResourceLocation("arpg:textures/de_acid/acid12.png"));
      coloredacidtex.add(new ResourceLocation("arpg:textures/de_acid/acid13.png"));
      coloredacidtex.add(new ResourceLocation("arpg:textures/de_acid/acid14.png"));
      coloredacidtex.add(new ResourceLocation("arpg:textures/de_acid/acid15.png"));
      coloredacidtex.add(new ResourceLocation("arpg:textures/de_acid/acid16.png"));
      coloredacidtex.add(new ResourceLocation("arpg:textures/de_acid/acid17.png"));
      coloredacidtex.add(new ResourceLocation("arpg:textures/de_acid/acid18.png"));
      coloredacidtex.add(new ResourceLocation("arpg:textures/de_acid/acid19.png"));
      coloredacidtex.add(new ResourceLocation("arpg:textures/de_acid/acid19.png"));

      for (int f = 1; f < 20; f++) {
         firedetex.add(new ResourceLocation("arpg:textures/de_fire/fire" + f + ".png"));
      }

      Weather.registerWeatherRender(Weather.RENDERNETHERSMOKE);
      Freezing.initIceLayers();
   }

   @Override
   public void postInit(FMLPostInitializationEvent event) {
      super.postInit(event);
      ShaderMain.register();
      RenderFluidHelper.init();
      DeathEffects.initMainTextures();
      InvasionInfo.initInfos();

      try {
         for (Field field : BlockModelRenderer.class.getDeclaredFields()) {
            if (field.getType() == BlockColors.class) {
               field.setAccessible(true);
               AnnotationHooks.blockColors = (BlockColors)field.get(Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelRenderer());
            }
         }
      } catch (IllegalArgumentException var6) {
         var6.printStackTrace();
      } catch (IllegalAccessException var7) {
         var7.printStackTrace();
      }
   }
}
