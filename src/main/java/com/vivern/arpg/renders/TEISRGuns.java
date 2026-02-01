package com.vivern.arpg.renders;

import com.vivern.arpg.elements.AbstractBow;
import com.vivern.arpg.elements.Buzdygan;
import com.vivern.arpg.elements.FireworkLauncher;
import com.vivern.arpg.elements.IWeapon;
import com.vivern.arpg.elements.IceCompass;
import com.vivern.arpg.elements.ItemBullet;
import com.vivern.arpg.elements.ItemGrenade;
import com.vivern.arpg.elements.ItemMagicScroll;
import com.vivern.arpg.elements.ItemRocket;
import com.vivern.arpg.elements.Stinger;
import com.vivern.arpg.elements.animation.EnumFlick;
import com.vivern.arpg.elements.animation.Flicks;
import com.vivern.arpg.elements.models.AbstractMobModel;
import com.vivern.arpg.elements.models.AcidFireModel;
import com.vivern.arpg.elements.models.AdamantiumBattleAxeModel;
import com.vivern.arpg.elements.models.AdamantiumMinigunModel;
import com.vivern.arpg.elements.models.AdamantiumRevolverModel;
import com.vivern.arpg.elements.models.AnnihilationGunModel;
import com.vivern.arpg.elements.models.AquaticBowModel;
import com.vivern.arpg.elements.models.AquaticInstancerModel;
import com.vivern.arpg.elements.models.AquaticTridentModel;
import com.vivern.arpg.elements.models.AzureOreStaffModel;
import com.vivern.arpg.elements.models.BilebiterModel;
import com.vivern.arpg.elements.models.BlowholeModel;
import com.vivern.arpg.elements.models.BogFlowerModel;
import com.vivern.arpg.elements.models.BubbleFishModel;
import com.vivern.arpg.elements.models.BuzdyganModel;
import com.vivern.arpg.elements.models.CarapaceModel;
import com.vivern.arpg.elements.models.CeratargetModel;
import com.vivern.arpg.elements.models.ChainMaceModel;
import com.vivern.arpg.elements.models.ChargerModel;
import com.vivern.arpg.elements.models.CinderBowModel;
import com.vivern.arpg.elements.models.CompoundBowModel;
import com.vivern.arpg.elements.models.ConiferRodModel;
import com.vivern.arpg.elements.models.CooledRifleModel;
import com.vivern.arpg.elements.models.CoralRifleModel;
import com.vivern.arpg.elements.models.CryoDestroyerModel;
import com.vivern.arpg.elements.models.CryoGunModel;
import com.vivern.arpg.elements.models.CrystalCutterModel;
import com.vivern.arpg.elements.models.CrystalStarModel;
import com.vivern.arpg.elements.models.CursedBladeModel;
import com.vivern.arpg.elements.models.DragonShellModel;
import com.vivern.arpg.elements.models.DragonTailModel;
import com.vivern.arpg.elements.models.EchinusModel;
import com.vivern.arpg.elements.models.ElectricStaffModel;
import com.vivern.arpg.elements.models.ElementFocusModel;
import com.vivern.arpg.elements.models.EnderInstancerModel;
import com.vivern.arpg.elements.models.EnderProtectorModel;
import com.vivern.arpg.elements.models.FireballStaffModel;
import com.vivern.arpg.elements.models.FireworkLauncherModel;
import com.vivern.arpg.elements.models.GemStaffModel;
import com.vivern.arpg.elements.models.GhostSwordModel;
import com.vivern.arpg.elements.models.GlowBladeModel;
import com.vivern.arpg.elements.models.GothicBowModel;
import com.vivern.arpg.elements.models.GraveLurkerModel;
import com.vivern.arpg.elements.models.GrenadeLauncherModel;
import com.vivern.arpg.elements.models.HadronBlasterModel;
import com.vivern.arpg.elements.models.HeadShooterModel;
import com.vivern.arpg.elements.models.HeadShooterModelOverlay;
import com.vivern.arpg.elements.models.HellmarkModel;
import com.vivern.arpg.elements.models.HolographicShieldModel;
import com.vivern.arpg.elements.models.HolyShotgunModel;
import com.vivern.arpg.elements.models.HydraulicShotgunModel;
import com.vivern.arpg.elements.models.IceBeamModel;
import com.vivern.arpg.elements.models.IceCompassModel;
import com.vivern.arpg.elements.models.IcicleMinigunModel;
import com.vivern.arpg.elements.models.InstancerModel;
import com.vivern.arpg.elements.models.LavaDropperModel;
import com.vivern.arpg.elements.models.MagicRocketModel;
import com.vivern.arpg.elements.models.MagicScrollModel;
import com.vivern.arpg.elements.models.MilitaryInstancerModel;
import com.vivern.arpg.elements.models.MithrilBowModel;
import com.vivern.arpg.elements.models.ModelSphere;
import com.vivern.arpg.elements.models.MoltenGreataxeModel;
import com.vivern.arpg.elements.models.NailGunModel;
import com.vivern.arpg.elements.models.NetherGrinderModel;
import com.vivern.arpg.elements.models.PistolFishModel;
import com.vivern.arpg.elements.models.PlasmaAcceleratorModel;
import com.vivern.arpg.elements.models.PlasmaAcceleratorModelOverlay;
import com.vivern.arpg.elements.models.PlasmaMinigunModel;
import com.vivern.arpg.elements.models.PlasmaPistolModel;
import com.vivern.arpg.elements.models.PlasmaRailgunModel;
import com.vivern.arpg.elements.models.PlasmaRifleModel;
import com.vivern.arpg.elements.models.PumpShotgunModel;
import com.vivern.arpg.elements.models.ReaperModel;
import com.vivern.arpg.elements.models.RestlessSkullModel;
import com.vivern.arpg.elements.models.RocketLauncherModel;
import com.vivern.arpg.elements.models.RottenShieldModel;
import com.vivern.arpg.elements.models.SandScepterModel;
import com.vivern.arpg.elements.models.SeaEffloresceModel;
import com.vivern.arpg.elements.models.SkullCrasherModel;
import com.vivern.arpg.elements.models.SlimeShotgunModel;
import com.vivern.arpg.elements.models.SnapballModel;
import com.vivern.arpg.elements.models.SnowballCannonModel;
import com.vivern.arpg.elements.models.SpellHammerModel;
import com.vivern.arpg.elements.models.SpellRodModel;
import com.vivern.arpg.elements.models.StaffOfCorpseModel;
import com.vivern.arpg.elements.models.StaffOfWitherdryModel;
import com.vivern.arpg.elements.models.StaticLanceModel;
import com.vivern.arpg.elements.models.StingerModel;
import com.vivern.arpg.elements.models.StingingCellModel;
import com.vivern.arpg.elements.models.SubmachineModel;
import com.vivern.arpg.elements.models.ThistleThornModel;
import com.vivern.arpg.elements.models.ToxicNuclearCannonModel;
import com.vivern.arpg.elements.models.ToxiniumShieldModel;
import com.vivern.arpg.elements.models.ToxiniumShotgunModel;
import com.vivern.arpg.elements.models.VacuumGunModel;
import com.vivern.arpg.elements.models.ViolenceModel;
import com.vivern.arpg.elements.models.VoltridentModel;
import com.vivern.arpg.elements.models.WandOfBlazesModel;
import com.vivern.arpg.elements.models.WandOfColdModel;
import com.vivern.arpg.elements.models.WhipModel;
import com.vivern.arpg.elements.models.WhispersBladeModel;
import com.vivern.arpg.elements.models.WinterBreathModel;
import com.vivern.arpg.elements.models.WinterInstancerModel;
import com.vivern.arpg.elements.models.XmassLauncherModel;
import com.vivern.arpg.elements.models.XmassRocketModel;
import com.vivern.arpg.events.Debugger;
import com.vivern.arpg.main.AnimationTimer;
import com.vivern.arpg.main.ColorConverters;
import com.vivern.arpg.main.EnchantmentInit;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.NBTHelper;
import com.vivern.arpg.main.WeaponParameters;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelShield;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.tileentity.TileEntityShulkerBox;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class TEISRGuns extends TileEntityItemStackRenderer {
   public static TileEntityItemStackRenderer instance;
   private final ModelShield modelShield = new ModelShield();
   private final TileEntityBanner banner = new TileEntityBanner();
   private static final TileEntityShulkerBox[] SHULKER_BOXES = new TileEntityShulkerBox[16];
   private final AnnihilationGunModel anihgun = new AnnihilationGunModel();
   private final ElementFocusModel elfocus = new ElementFocusModel();
   private final StingerModel stinger = new StingerModel();
   private final FireworkLauncherModel fireworklaun = new FireworkLauncherModel();
   private final SandScepterModel sandscepter = new SandScepterModel();
   private final BilebiterModel bilebiter = new BilebiterModel();
   private final FireballStaffModel fireballstaff = new FireballStaffModel();
   private final ElectricStaffModel electricstaff = new ElectricStaffModel();
   private final SlimeShotgunModel slimeshotgun = new SlimeShotgunModel();
   private final MoltenGreataxeModel moltgreataxe = new MoltenGreataxeModel();
   private final NetherGrinderModel nethergrinder = new NetherGrinderModel();
   private final SnowballCannonModel snowballcannon = new SnowballCannonModel();
   private final CursedBladeModel cursedblade = new CursedBladeModel();
   private final GraveLurkerModel gravelurker = new GraveLurkerModel();
   private final ReaperModel reaper = new ReaperModel();
   private final GhostSwordModel ghostsword = new GhostSwordModel();
   private final StaffOfCorpseModel corpsestaff = new StaffOfCorpseModel();
   private final ToxicNuclearCannonModel toxicnuclearcannon = new ToxicNuclearCannonModel();
   private final CryoGunModel cryogun = new CryoGunModel();
   private final ChargerModel charger = new ChargerModel();
   private final IcicleMinigunModel icicleminigun = new IcicleMinigunModel();
   private final StaticLanceModel staticlance = new StaticLanceModel();
   private final WandOfBlazesModel wandofblazes = new WandOfBlazesModel();
   private final ConiferRodModel coniferrod = new ConiferRodModel();
   private final PlasmaRailgunModel plasmarailgun = new PlasmaRailgunModel();
   private final PlasmaRifleModel plasmarifle = new PlasmaRifleModel();
   private final HolographicShieldModel holoshield = new HolographicShieldModel();
   private final HeadShooterModel headshooter = new HeadShooterModel();
   private final HeadShooterModelOverlay headshooterOv = new HeadShooterModelOverlay();
   private final PlasmaPistolModel plasmapistol = new PlasmaPistolModel();
   private final BogFlowerModel bogflower = new BogFlowerModel();
   private final PistolFishModel pistolfish = new PistolFishModel();
   private final BubbleFishModel bubblefish = new BubbleFishModel();
   private final LavaDropperModel lavadropper = new LavaDropperModel();
   private final PlasmaAcceleratorModel plasmaaccelerator = new PlasmaAcceleratorModel();
   private final PlasmaAcceleratorModelOverlay plasmaacceleratorOv = new PlasmaAcceleratorModelOverlay();
   private final VacuumGunModel vacuumgun = new VacuumGunModel();
   private final GemStaffModel gemstaff = new GemStaffModel();
   private final WandOfColdModel wandofcold = new WandOfColdModel();
   private final IceBeamModel icebeam = new IceBeamModel();
   private final SkullCrasherModel skullcrasher = new SkullCrasherModel();
   private final SpellHammerModel spellhammer = new SpellHammerModel();
   private final AcidFireModel acidfire = new AcidFireModel();
   private final ToxiniumShotgunModel toxiniumshotgun = new ToxiniumShotgunModel();
   private final SubmachineModel submachine = new SubmachineModel();
   private final NailGunModel nailgun = new NailGunModel();
   private final AquaticTridentModel aquatictrident = new AquaticTridentModel();
   private final SeaEffloresceModel seaeffloresce = new SeaEffloresceModel();
   private final GlowBladeModel glowblade = new GlowBladeModel();
   private final CrystalStarModel crystalstar = new CrystalStarModel();
   private final ThistleThornModel thistlethorn = new ThistleThornModel();
   private final RestlessSkullModel restlessSkullModel = new RestlessSkullModel();
   private final MagicRocketModel magicRocketModel = new MagicRocketModel();
   private final StingingCellModel stingingCellModel = new StingingCellModel();
   private final CoralRifleModel coralRifleModel = new CoralRifleModel();
   private final HadronBlasterModel hadronBlasterModel = new HadronBlasterModel();
   private final SnapballModel snapballModel = new SnapballModel();
   private final RocketLauncherModel rocketLauncherModel = new RocketLauncherModel();
   private final MagicScrollModel magicScrollModel = new MagicScrollModel();
   public static final ChainMaceModel chainMaceModel = new ChainMaceModel();
   public static final EchinusModel echinusModel = new EchinusModel();
   private final AdamantiumRevolverModel adamantiumRevolverModel = new AdamantiumRevolverModel();
   private final AdamantiumBattleAxeModel adamantiumBattleAxeModel = new AdamantiumBattleAxeModel();
   private final EnderProtectorModel enderProtectorModel = new EnderProtectorModel();
   private final DragonTailModel dragonTailModel = new DragonTailModel();
   private final DragonShellModel dragonShellModel = new DragonShellModel();
   private final WinterBreathModel winterBreathModel = new WinterBreathModel();
   private final ToxiniumShieldModel toxiniumShieldModel = new ToxiniumShieldModel();
   private final CarapaceModel carapaceModel = new CarapaceModel();
   private final RottenShieldModel rottenShieldModel = new RottenShieldModel();
   private final HellmarkModel hellmarkModel = new HellmarkModel();
   private final BlowholeModel blowholeModel = new BlowholeModel();
   private final CrystalCutterModel crystalCutterModel = new CrystalCutterModel();
   private final PlasmaMinigunModel plasmaMinigunModel = new PlasmaMinigunModel();
   private final CeratargetModel ceratargetModel = new CeratargetModel();
   private final GrenadeLauncherModel grenadeLauncherModel = new GrenadeLauncherModel();
   private final HolyShotgunModel holyShotgunModel = new HolyShotgunModel();
   private final MithrilBowModel mithrilBowModel = new MithrilBowModel();
   private final CompoundBowModel compoundBowModel = new CompoundBowModel();
   private final AzureOreStaffModel azureOreStaffModel = new AzureOreStaffModel();
   private final InstancerModel instancerModel = new InstancerModel();
   private final EnderInstancerModel enderInstancerModel = new EnderInstancerModel();
   private final WinterInstancerModel winterInstancerModel = new WinterInstancerModel();
   private final MilitaryInstancerModel militaryInstancerModel = new MilitaryInstancerModel();
   private final AquaticInstancerModel aquaticInstancerModel = new AquaticInstancerModel();
   private final ViolenceModel violenceModel = new ViolenceModel();
   private final StaffOfWitherdryModel staffOfWitherdryModel = new StaffOfWitherdryModel();
   private final WhispersBladeModel whispersBladeModel = new WhispersBladeModel();
   private final VoltridentModel voltridentModel = new VoltridentModel();
   private final PumpShotgunModel pumpShotgunModel = new PumpShotgunModel();
   private final CinderBowModel cinderBowModel = new CinderBowModel();
   private final GothicBowModel gothicBowModel = new GothicBowModel();
   private final CooledRifleModel cooledRifleModel = new CooledRifleModel();
   private final AquaticBowModel aquaticBowModel = new AquaticBowModel();
   private final XmassLauncherModel xmassLauncherModel = new XmassLauncherModel();
   private final XmassRocketModel xmassRocketModel = new XmassRocketModel();
   private final AdamantiumMinigunModel adamantiumMinigunModel = new AdamantiumMinigunModel();
   private final BuzdyganModel buzdyganModel = new BuzdyganModel();
   private final WhipModel whipModel = new WhipModel();
   private final SpellRodModel spellRodModel = new SpellRodModel();
   private final CryoDestroyerModel cryoDestroyerModel = new CryoDestroyerModel();
   private final HydraulicShotgunModel hydraulicShotgunModel = new HydraulicShotgunModel();
   private final IceCompassModel iceCompassModel = new IceCompassModel();
   private final ResourceLocation texIceCompassModel = new ResourceLocation("arpg:textures/ice_compass_model_tex.png");
   private final ResourceLocation texHydraulicShotgunModel = new ResourceLocation("arpg:textures/hydraulic_shotgun_model_tex.png");
   private final ResourceLocation texCryoDestroyerModel = new ResourceLocation("arpg:textures/cryo_destroyer_model_tex.png");
   private final ResourceLocation texSpellRodModel = new ResourceLocation("arpg:textures/spell_rod_model_tex.png");
   private final ResourceLocation texSnakewhip = new ResourceLocation("arpg:textures/snakewhip_tex.png");
   private final ResourceLocation texMauler = new ResourceLocation("arpg:textures/mauler_tex.png");
   private final ResourceLocation texWhip = new ResourceLocation("arpg:textures/whip_tex.png");
   private final ResourceLocation texBuzdyganModel = new ResourceLocation("arpg:textures/buzdygan_model_tex.png");
   private final ResourceLocation texAdamantiumMinigunOverheat = new ResourceLocation("arpg:textures/adamantium_minigun_overheat.png");
   private final ResourceLocation texAdamantiumMinigunModel = new ResourceLocation("arpg:textures/adamantium_minigun_model_tex.png");
   private final ResourceLocation texXmassRocketModel = new ResourceLocation("arpg:textures/xmass_rocket_model_tex.png");
   private final ResourceLocation texXmassLauncherModel = new ResourceLocation("arpg:textures/xmass_launcher_model_tex.png");
   private final ResourceLocation texAquaticBowModel = new ResourceLocation("arpg:textures/aquatic_bow_model_tex.png");
   private final ResourceLocation texAnihgun = new ResourceLocation("arpg:textures/anihguntex.png");
   private final ResourceLocation texElfocus = new ResourceLocation("arpg:textures/elfocus_tex.png");
   private final ResourceLocation texStinger = new ResourceLocation("arpg:textures/stinger_model_tex.png");
   private final ResourceLocation texFireworkLaun = new ResourceLocation("arpg:textures/firework_launcher_model_tex.png");
   private final ResourceLocation texEnch1 = new ResourceLocation("arpg:textures/sobig-6.png");
   private final ResourceLocation texEnch2 = new ResourceLocation("arpg:textures/sobig-8.png");
   private final ResourceLocation texEnch3 = new ResourceLocation("arpg:textures/sobig-10.png");
   private final ResourceLocation texEnch4 = new ResourceLocation("arpg:textures/sobig-11.png");
   private final ResourceLocation texSandScepter = new ResourceLocation("arpg:textures/sand_scepter_model_tex.png");
   private final ResourceLocation texBilebiter = new ResourceLocation("arpg:textures/bilebiter_model_tex.png");
   private final ResourceLocation texFireballStaff = new ResourceLocation("arpg:textures/fireball_staff_model_tex.png");
   private final ResourceLocation texElectricStaff = new ResourceLocation("arpg:textures/electric_staff_model_tex.png");
   private final ResourceLocation texSlimeShotgun = new ResourceLocation("arpg:textures/slime_shotgun_model_tex.png");
   private final ResourceLocation texMoltGreataxe = new ResourceLocation("arpg:textures/molten_greataxe_model_tex.png");
   private final ResourceLocation texNetherGrinder = new ResourceLocation("arpg:textures/nether_grinder_model_tex.png");
   private final ResourceLocation texSnowballCannon = new ResourceLocation("arpg:textures/snowball_cannon_model_tex.png");
   private final ResourceLocation texCursedBlade = new ResourceLocation("arpg:textures/cursed_blade_model_tex.png");
   private final ResourceLocation texGraveLurker = new ResourceLocation("arpg:textures/gravelurker_model_tex.png");
   private final ResourceLocation texReaper = new ResourceLocation("arpg:textures/reaper_model_tex.png");
   private final ResourceLocation texGhostSword = new ResourceLocation("arpg:textures/ghost_sword_model_tex.png");
   private final ResourceLocation texStaffOfCorpse = new ResourceLocation("arpg:textures/staff_of_corpse_model_tex.png");
   private final ResourceLocation texToxicNuclearCannon = new ResourceLocation("arpg:textures/toxic_nuclear_cannon_model_tex.png");
   private final ResourceLocation texCryoGun = new ResourceLocation("arpg:textures/cryo_gun_model_tex.png");
   private final ResourceLocation texCharger = new ResourceLocation("arpg:textures/charger_model_tex.png");
   private final ResourceLocation texIcicleMinigun = new ResourceLocation("arpg:textures/icicle_minigun_model_tex.png");
   private final ResourceLocation texStaticLance = new ResourceLocation("arpg:textures/static_lance_model_tex.png");
   private final ResourceLocation texWandofBlazes = new ResourceLocation("arpg:textures/wand_of_blazes_model_tex.png");
   private final ResourceLocation texConiferRod = new ResourceLocation("arpg:textures/conifer_rod_model_tex.png");
   private final ResourceLocation texPlasmaRailgun = new ResourceLocation("arpg:textures/plasma_railgun_model_tex.png");
   private final ResourceLocation texPlasmaRifle = new ResourceLocation("arpg:textures/plasma_rifle_model_tex.png");
   private final ResourceLocation texHolographicShield = new ResourceLocation("arpg:textures/holographic_shield_model_tex.png");
   private final ResourceLocation texHeadShooter = new ResourceLocation("arpg:textures/head_shooter_model_tex.png");
   private final ResourceLocation texHeadShooterOv = new ResourceLocation("arpg:textures/head_shooter_model_overlay_tex.png");
   private final ResourceLocation texPlasmaPistol = new ResourceLocation("arpg:textures/plasma_pistol_model_tex.png");
   private final ResourceLocation texBogFlower = new ResourceLocation("arpg:textures/bog_flower_model_tex.png");
   private final ResourceLocation texPistolFish = new ResourceLocation("arpg:textures/pistol_fish_model_tex.png");
   private final ResourceLocation texBubbleFish = new ResourceLocation("arpg:textures/bubble_fish_model_tex.png");
   private final ResourceLocation texLavaDropper = new ResourceLocation("arpg:textures/lava_dropper_model_tex.png");
   private final ResourceLocation texPlasmaAccelerator = new ResourceLocation("arpg:textures/plasma_accelerator_model_tex.png");
   private final ResourceLocation texPlasmaAcceleratorOv = new ResourceLocation("arpg:textures/plasma_accelerator_model_overlay_tex.png");
   private final ResourceLocation texVacuumGun = new ResourceLocation("arpg:textures/vacuum_gun_model_tex.png");
   private final ResourceLocation texGemStaff0 = new ResourceLocation("arpg:textures/gem_staff_model_tex_diam.png");
   private final ResourceLocation texGemStaff1 = new ResourceLocation("arpg:textures/gem_staff_model_tex_rub.png");
   private final ResourceLocation texGemStaff2 = new ResourceLocation("arpg:textures/gem_staff_model_tex_sapp.png");
   private final ResourceLocation texGemStaff3 = new ResourceLocation("arpg:textures/gem_staff_model_tex_emer.png");
   private final ResourceLocation texGemStaff4 = new ResourceLocation("arpg:textures/gem_staff_model_tex_citr.png");
   private final ResourceLocation texGemStaff5 = new ResourceLocation("arpg:textures/gem_staff_model_tex_amet.png");
   private final ResourceLocation texGemStaff6 = new ResourceLocation("arpg:textures/gem_staff_model_tex_top.png");
   private final ResourceLocation texGemStaff7 = new ResourceLocation("arpg:textures/gem_staff_model_tex_cryst.png");
   private final ResourceLocation texWandOfCold = new ResourceLocation("arpg:textures/wand_of_cold_model_tex.png");
   private final ResourceLocation texIceBeam = new ResourceLocation("arpg:textures/ice_beam_model_tex.png");
   private final ResourceLocation texSkullCrasher = new ResourceLocation("arpg:textures/skull_crasher_model_tex.png");
   private final ResourceLocation texSpellHammer = new ResourceLocation("arpg:textures/spell_hammer_model_tex.png");
   private final ResourceLocation texAcidFire1 = new ResourceLocation("arpg:textures/acid_fire_tex_1.png");
   private final ResourceLocation texAcidFire2 = new ResourceLocation("arpg:textures/acid_fire_tex_2.png");
   private final ResourceLocation texAcidFire3 = new ResourceLocation("arpg:textures/acid_fire_tex_3.png");
   private final ResourceLocation texToxiniumShotgun = new ResourceLocation("arpg:textures/toxinium_shotgun_model_tex.png");
   private final ResourceLocation texSubmachine = new ResourceLocation("arpg:textures/submachine_model_tex.png");
   private final ResourceLocation texSubmachineOverheat = new ResourceLocation("arpg:textures/submachine_overheat.png");
   private final ResourceLocation texNailGun = new ResourceLocation("arpg:textures/nail_gun_model_tex.png");
   private final ResourceLocation texAquaticTrident = new ResourceLocation("arpg:textures/aquatic_trident_model_tex.png");
   private final ResourceLocation texSeaEffloresce = new ResourceLocation("arpg:textures/sea_effloresce_model_tex.png");
   private final ResourceLocation texGlowBlade = new ResourceLocation("arpg:textures/glow_blade_model_tex.png");
   private final ResourceLocation texCrystalStar = new ResourceLocation("arpg:textures/crystal_star_model_tex.png");
   private final ResourceLocation texThistleThorn = new ResourceLocation("arpg:textures/thistle_thorn_model_tex.png");
   private final ResourceLocation texRestlessSkull = new ResourceLocation("arpg:textures/restless_skull_model_tex.png");
   private final ResourceLocation texMagicRocket = new ResourceLocation("arpg:textures/magic_rocket_model_tex.png");
   private final ResourceLocation texStingingCell = new ResourceLocation("arpg:textures/stinging_cell_model_tex.png");
   private final ResourceLocation texCoralRifle = new ResourceLocation("arpg:textures/coral_rifle_model_tex.png");
   private final ResourceLocation texHadronBlaster = new ResourceLocation("arpg:textures/hadron_blaster_model_tex.png");
   private final ResourceLocation texSnapball = new ResourceLocation("arpg:textures/snapball_model_tex.png");
   public static ResourceLocation texSnapballCore = new ResourceLocation("arpg:textures/snapball.png");
   public static ResourceLocation texRocketLauncher = new ResourceLocation("arpg:textures/rocket_launcher_model_tex.png");
   public static ResourceLocation texMagicScroll = new ResourceLocation("arpg:textures/magic_scroll_model_tex.png");
   public static ResourceLocation texScrollSpells = new ResourceLocation("arpg:textures/scroll_spells.png");
   public static ResourceLocation texChainMaceIron = new ResourceLocation("arpg:textures/chain_mace_iron_tex.png");
   public static ResourceLocation texChainMaceDiamond = new ResourceLocation("arpg:textures/chain_mace_diamond_tex.png");
   public static ResourceLocation texChainMaceMolten = new ResourceLocation("arpg:textures/chain_mace_molten_tex.png");
   public static ResourceLocation texIcebreaker = new ResourceLocation("arpg:textures/icebreaker_tex.png");
   public static ResourceLocation texAdamantiumRevolver = new ResourceLocation("arpg:textures/adamantium_revolver_model_tex.png");
   public static ResourceLocation texAdamantiumBattleAxe = new ResourceLocation("arpg:textures/adamantium_battle_axe_model_tex.png");
   public static ResourceLocation texEnderProtector = new ResourceLocation("arpg:textures/ender_protector_model_tex.png");
   public static ResourceLocation texDragonTail = new ResourceLocation("arpg:textures/dragon_tail_model_tex.png");
   public static ResourceLocation texDragonShell = new ResourceLocation("arpg:textures/dragon_shell_model_tex.png");
   public static ResourceLocation texWinterBreath = new ResourceLocation("arpg:textures/winter_breath_model_tex.png");
   public static ResourceLocation texToxiniumShield = new ResourceLocation("arpg:textures/toxinium_shield_model_tex.png");
   public static ResourceLocation texCarapace = new ResourceLocation("arpg:textures/carapace_model_tex.png");
   public static ResourceLocation texRottenShield = new ResourceLocation("arpg:textures/rotten_shield_model_tex.png");
   public static ResourceLocation texHellmark = new ResourceLocation("arpg:textures/hellmark_model_tex.png");
   public static ResourceLocation texBlowhole = new ResourceLocation("arpg:textures/blowhole_model_tex.png");
   public static ResourceLocation texBlowholeBubble = new ResourceLocation("arpg:textures/gloss_map2.png");
   public static ResourceLocation texCrystalCutter = new ResourceLocation("arpg:textures/crystal_cutter_model_tex.png");
   public static ResourceLocation texPlasmaMinigun = new ResourceLocation("arpg:textures/plasma_minigun_model_tex.png");
   public static ResourceLocation texPlasmaMinigunOverheat = new ResourceLocation("arpg:textures/plasma_minigun_overheat.png");
   public static ResourceLocation texCeratargetModel = new ResourceLocation("arpg:textures/ceratarget_model_tex.png");
   public static ResourceLocation texGrenadeLauncherModel = new ResourceLocation("arpg:textures/grenade_launcher_model_tex.png");
   public static ResourceLocation texHolyShotgunModel = new ResourceLocation("arpg:textures/holy_shotgun_model_tex.png");
   public static ResourceLocation texEchinusModel = new ResourceLocation("arpg:textures/echinus_model_tex.png");
   public static ResourceLocation texMithrilBowModel = new ResourceLocation("arpg:textures/mithril_bow_model_tex.png");
   public static ResourceLocation texCompoundBowModel = new ResourceLocation("arpg:textures/compound_bow_model_tex.png");
   public static ResourceLocation texAzureOreStaffModel = new ResourceLocation("arpg:textures/azure_ore_staff_model_tex.png");
   public static ResourceLocation texInstancerModel = new ResourceLocation("arpg:textures/instancer_model_tex.png");
   public static ResourceLocation texEnderInstancerModel = new ResourceLocation("arpg:textures/ender_instancer_model_tex.png");
   public static ResourceLocation texWinterInstancerModel = new ResourceLocation("arpg:textures/winter_instancer_model_tex.png");
   public static ResourceLocation texMilitaryInstancerModel = new ResourceLocation("arpg:textures/military_instancer_model_tex.png");
   public static ResourceLocation texAquaticInstancerModel = new ResourceLocation("arpg:textures/aquatic_instancer_model_tex.png");
   public static ResourceLocation texViolenceModel = new ResourceLocation("arpg:textures/violence_model_tex.png");
   public static ResourceLocation texStaffOfWitherdryModel = new ResourceLocation("arpg:textures/staff_of_witherdry_model_tex.png");
   public static ResourceLocation texWhispersBladeModel = new ResourceLocation("arpg:textures/whispers_blade_model_tex.png");
   public static ResourceLocation texVoltridentModel = new ResourceLocation("arpg:textures/voltrident_model_tex.png");
   public static ResourceLocation texPumpShotgunModel = new ResourceLocation("arpg:textures/pump_shotgun_model_tex.png");
   public static ResourceLocation texCinderBowModel = new ResourceLocation("arpg:textures/cinder_bow_model_tex.png");
   public static ResourceLocation texGothicBowModel = new ResourceLocation("arpg:textures/gothic_bow_model_tex.png");
   public static ResourceLocation texCooledRifleModel = new ResourceLocation("arpg:textures/cooled_rifle_model_tex.png");
   public static ModelSphere sphere1 = new ModelSphere(0.18F, 10);
   public static ModelSphere sphere2 = new ModelSphere(0.25F, 16);
   public static ResourceLocation gloss1 = new ResourceLocation("arpg:textures/gloss_map1.png");
   public static ResourceLocation lightnings = new ResourceLocation("arpg:textures/lightnings.png");
   public static int mainhandRotationTick = 0;
   public static int offhandRotationTick = 0;
   public static boolean mainhandShoot = false;
   public static boolean offhandShoot = false;
   public static float mainhandfrequency = 4.0F;
   public static float mainhandpower = 0.14F;
   public static float offhandfrequency = 4.0F;
   public static float offhandpower = 0.14F;

   public static TEISRGuns getTeisr() {
      return new TEISRGuns();
   }

   public void renderByItem(ItemStack itemStackIn) {
      this.renderByItem(itemStackIn, Minecraft.getMinecraft().getRenderPartialTicks());
   }

   public void renderByItem(ItemStack Itemstack, float partialTicks) {
      if (IWeapon.canShoot(Itemstack)) {
         Item item = Itemstack.getItem();
         int timer = AnimationTimer.tick;
         if (item == ItemsRegister.ANIHGUN) {
            int charge = 0;
            float modifier = 17.0F;
            if (Itemstack.hasTagCompound() && Itemstack.getTagCompound().hasKey("charge")) {
               charge = Itemstack.getTagCompound().getInteger("charge");
               if (charge < 60) {
                  modifier = 1.0F + charge / 10.0F;
               }
            }

            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texAnihgun);
            this.anihgun.render(null, 1.0F, charge * modifier, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               float f4 = (float)(Math.sin(timer / 100) / 4.0);
               GlStateManager.color(0.2F, 0.58F + f4, 1.0F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.anihgun.render(null, 1.0F, charge * modifier, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.ELEMENTFOCUS) {
            int chargex = 0;
            if (Itemstack.hasTagCompound() && Itemstack.getTagCompound().hasKey("charge")) {
               chargex = Itemstack.getTagCompound().getInteger("charge");
            }

            GlStateManager.pushMatrix();
            GlStateManager.scale(0.04F, 0.04F, 0.04F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texElfocus);
            this.elfocus.render(null, 1.0F, (chargex + partialTicks) * 15.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.04F, 0.04F, 0.04F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               GlStateManager.color((float)(0.75 + Math.sin(timer / 100) / 4.0), (float)(0.75 + Math.sin(timer / 100) / 4.0), 1.0F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.elfocus.render(null, 1.0F, (chargex + partialTicks) * 15.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.STINGER) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.065F, 0.065F, 0.065F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texStinger);
            int amm = Stinger.maxammo - NBTHelper.GetNBTint(Itemstack, "ammo");
            int relTime = NBTHelper.GetNBTint(Itemstack, "reload_time");
            if (relTime <= 16) {
               if (relTime > 0) {
                  amm = relTime;
               }
            } else {
               amm = Stinger.maxammo;
            }

            this.stinger.render(null, 1.0F, amm, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.065F, 0.065F, 0.065F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               GlStateManager.color(1.0F, 1.0F, (float)(0.75 + Math.sin(timer / 100) / 4.0), 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.stinger.render(null, 1.0F, amm, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.FIREWORKSLAUN) {
            float dragonf = -2.0F;
            if (Itemstack.hasTagCompound() && Itemstack.getTagCompound().hasKey("dragon")) {
               if (!Itemstack.getTagCompound().getBoolean("dragon")) {
                  dragonf = Itemstack.getItemDamage() * 8.7F;
               }

               if (Itemstack.getTagCompound().getBoolean("dragon") && NBTHelper.GetNBTint(Itemstack, "ammo") == FireworkLauncher.maxammo) {
                  dragonf = -1.0F;
               }
            }

            GlStateManager.pushMatrix();
            GlStateManager.scale(0.065F, 0.065F, 0.065F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texFireworkLaun);
            this.fireworklaun.render(null, 1.0F, dragonf, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.065F, 0.065F, 0.065F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               GlStateManager.color(
                  0.6F + AnimationTimer.rainbow1 / 34.0F, 0.4F + AnimationTimer.rainbow2 / 34.0F, 0.4F + AnimationTimer.rainbow3 / 34.0F, 1.0F
               );
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.fireworklaun.render(null, 1.0F, dragonf, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.SCEPTEROFSANDS) {
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(Itemstack.getItem());
            float chargeFullness = NBTHelper.GetNBTint(Itemstack, "crystal") / parameters.get("max_crystal_charge");
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.09F, 0.09F, 0.09F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texSandScepter);
            this.sandscepter.render(null, chargeFullness, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.09F, 0.09F, 0.09F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch2);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1 / 20.0F, f2 / 20.0F, 0.0F);
               GlStateManager.matrixMode(5888);
               GL11.glEnable(3042);
               float uncharge = 1.0F - chargeFullness;
               GlStateManager.color(1.0F, 1.0F * uncharge, (float)(0.75 + Math.sin(timer / 100) / 4.0) * uncharge, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.sandscepter.render(null, chargeFullness, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GL11.glDisable(3042);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.BILEBITER) {
            int bbb = NBTHelper.GetNBTint(Itemstack, "charge_primary");
            int aaa = NBTHelper.GetNBTint(Itemstack, "charge_secondary");
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.09F, 0.09F, 0.09F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texBilebiter);
            this.bilebiter.render(null, 1.0F, 1.0F, aaa, bbb, EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, Itemstack), 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.09F, 0.09F, 0.09F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch3);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1 / 5.0F, f2 / 5.0F, 0.0F);
               GlStateManager.matrixMode(5888);
               GL11.glEnable(3042);
               GlStateManager.color(1.0F, 1.0F, 0.6F + AnimationTimer.rainbow1 / 35.0F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.bilebiter.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GL11.glDisable(3042);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.FIREBALLSTAFF) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.09F, 0.09F, 0.09F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texFireballStaff);
            this.fireballstaff.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.09F, 0.09F, 0.09F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               GlStateManager.color((float)(0.75 + Math.sin(timer / 100) / 4.0), (float)(0.75 + Math.sin(timer / 100) / 4.0), 1.0F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.fireballstaff.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.ELECTRICSTAFF) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.09F, 0.09F, 0.09F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texElectricStaff);
            this.electricstaff.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.09F, 0.09F, 0.09F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               GlStateManager.color((float)(0.55F + Math.sin(timer / 100) / 4.0), 0.4F, 1.0F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.electricstaff.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.SLIMESHOTGUN) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texSlimeShotgun);
            this.slimeshotgun.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               GlStateManager.color(0.3F, (float)(0.78F + Math.sin(timer / 100) / 4.0), 0.5F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.slimeshotgun.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.MOLTENGREATAXE) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.09F, 0.09F, 0.09F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texMoltGreataxe);
            this.moltgreataxe.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.09F, 0.09F, 0.09F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch4);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               GlStateManager.color(1.0F, (float)(0.65F + Math.sin(timer / 100) / 4.0), 0.4F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.moltgreataxe.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.NETHERGRINDER) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texNetherGrinder);
            this.nethergrinder.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch4);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 3.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               GlStateManager.color(1.0F, (float)(0.65F + Math.sin(timer / 100) / 4.0), 0.4F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.nethergrinder.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.SNOWBALLCANNON) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texSnowballCannon);
            float df1 = 1.0F / Itemstack.getMaxDamage() * Itemstack.getItemDamage();
            this.snowballcannon.render(null, 1.0F, df1, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               GlStateManager.color(0.3F, (float)(0.78F + Math.sin(timer / 100) / 4.0), 1.0F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.snowballcannon.render(null, 1.0F, df1, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.CURSEDBLADE) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.09F, 0.09F, 0.09F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texCursedBlade);
            this.cursedblade.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.09F, 0.09F, 0.09F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               GlStateManager.color((float)(0.45F + Math.sin(timer / 100) / 7.0), 0.2F, (float)(0.85F + Math.sin(timer / 78) / 4.0), 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.cursedblade.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.GRAVELURKER) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texGraveLurker);
            this.gravelurker.render(null, 1.0F, !NBTHelper.GetNBTboolean(Itemstack, "crit") ? 0.0F : 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               GlStateManager.color((float)(0.45F + Math.sin(timer / 100) / 7.0), 0.2F, 1.0F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.gravelurker.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.REAPER) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texReaper);
            this.reaper.render(null, 1.0F, Math.min(NBTHelper.GetNBTfloat(Itemstack, "charge"), 10.0F), 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               GlStateManager.color((float)(0.45F + Math.sin(timer / 100) / 7.0), 0.2F, 1.0F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.reaper.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.GHOSTSWORD) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.09F, 0.09F, 0.09F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texGhostSword);
            this.ghostsword.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.09F, 0.09F, 0.09F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               GlStateManager.color((float)(0.65F + Math.sin(timer / 100) / 7.0), 1.0F, (float)(0.55F + Math.sin(timer / 78) / 4.0), 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.ghostsword.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.CORPSESTAFF) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.09F, 0.09F, 0.09F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texStaffOfCorpse);
            this.corpsestaff.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.09F, 0.09F, 0.09F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               GlStateManager.color((float)(0.65F + Math.sin(timer / 100) / 7.0), 1.0F, (float)(0.55F + Math.sin(timer / 78) / 4.0), 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.corpsestaff.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.TOXICNUKECANNON) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texToxicNuclearCannon);
            this.toxicnuclearcannon.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch3);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1 / 5.0F, f2 / 5.0F, 0.0F);
               GlStateManager.matrixMode(5888);
               GL11.glEnable(3042);
               GlStateManager.color(0.5F, 1.0F, 0.7F + AnimationTimer.rainbow1 / 45.0F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.toxicnuclearcannon.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.CRYOGUN) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texCryoGun);
            this.cryogun.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               GlStateManager.color(0.3F, (float)(0.78F + Math.sin(timer / 100) / 4.0), 1.0F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.cryogun.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.CHARGER) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texCharger);
            this.charger.render(null, 1.0F, timer * 2, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               GlStateManager.color((float)(0.55F + Math.sin(timer / 100) / 4.0), 0.4F, 1.0F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.charger.render(null, 1.0F, -timer * 2, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.ICICLEMINIGUN) {
            float anim1 = Flicks.instance.getClientAnimation(Itemstack, EnumFlick.SHOOT, partialTicks, true);
            float anim2 = 1.0F - Flicks.instance.getClientAnimation(Itemstack, EnumFlick.RELOAD, partialTicks, false);
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texIcicleMinigun);
            this.icicleminigun.render(null, 1.0F, anim1, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               GlStateManager.color(0.25F, (float)(0.7F + Math.sin(timer / 100) / 4.0), 1.0F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.icicleminigun.render(null, 1.0F, anim1, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.STATICLANCE) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texStaticLance);
            this.staticlance.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               GlStateManager.color((float)(0.55F + Math.sin(timer / 100) / 4.0), 0.4F, 1.0F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.staticlance.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.WANDOFBLAZES) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texWandofBlazes);
            this.wandofblazes.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 3.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               GlStateManager.color(1.0F, (float)(0.65F + Math.sin(timer / 100) / 4.0), 0.4F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.wandofblazes.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.CONIFERROD) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texConiferRod);
            this.coniferrod.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               GlStateManager.color(0.3F, (float)(0.78F + Math.sin(timer / 100) / 4.0), 1.0F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.coniferrod.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.PLASMARAILGUN) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texPlasmaRailgun);
            this.plasmarailgun.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               GlStateManager.color((float)(0.55F + Math.sin(timer / 100) / 4.0), 0.4F, 1.0F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.plasmarailgun.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.PLASMARIFLE) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texPlasmaRifle);
            this.plasmarifle.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               GlStateManager.color((float)(0.55F + Math.sin(timer / 100) / 4.0), 0.4F, 1.0F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.plasmarifle.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.HOLOSHIELD) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texHolographicShield);
            this.holoshield.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               GlStateManager.color((float)(0.55F + Math.sin(timer / 100) / 4.0), 0.4F, 1.0F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.holoshield.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.HEADSHOOTER) {
            float lbX = OpenGlHelper.lastBrightnessX;
            float lbY = OpenGlHelper.lastBrightnessY;
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texHeadShooter);
            this.headshooter.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.depthMask(false);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texHeadShooterOv);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            float of = timer;
            float of1 = MathHelper.cos(of * 0.01F) / 4.0F;
            float of2 = of * 0.01F;
            GlStateManager.translate(of1, of2, 0.0F);
            GlStateManager.matrixMode(5888);
            GlStateManager.enableBlend();
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lbX, lbY);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
            this.headshooterOv.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            GlStateManager.matrixMode(5888);
            GlStateManager.disableBlend();
            GlStateManager.depthMask(true);
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               GlStateManager.color(1.0F, 0.2F + (float)(Math.sin(timer / 100) / 4.0), 0.2F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.headshooter.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               this.headshooterOv.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.PLASMAPISTOL) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texPlasmaPistol);
            this.plasmapistol.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               GlStateManager.color((float)(0.55F + Math.sin(timer / 100) / 4.0), 0.5F, 1.0F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.plasmapistol.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.BOGFLOWER) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texBogFlower);
            this.bogflower.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch3);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1 / 5.0F, f2 / 5.0F, 0.0F);
               GlStateManager.matrixMode(5888);
               GL11.glEnable(3042);
               GlStateManager.color(1.0F, 1.0F, 0.6F + AnimationTimer.rainbow1 / 35.0F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.bogflower.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.PISTOLFISH) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texPistolFish);
            this.pistolfish.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               GlStateManager.color((float)(0.55F + Math.sin(timer / 100) / 4.0), 0.5F, 1.0F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.pistolfish.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.BUBBLEFISH) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texBubbleFish);
            this.bubblefish.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               GlStateManager.color((float)(0.55F + Math.sin(timer / 100) / 4.0), 0.5F, 1.0F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.bubblefish.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.LAVADROPPER) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texLavaDropper);
            this.lavadropper.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               GlStateManager.color((float)(0.75 + Math.sin(timer / 100) / 4.0), (float)(0.75 + Math.sin(timer / 100) / 4.0), 1.0F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.lavadropper.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.PLASMAACCELERATOR) {
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(Itemstack.getItem());
            float lbX = OpenGlHelper.lastBrightnessX;
            float lbY = OpenGlHelper.lastBrightnessY;
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texPlasmaAccelerator);
            float chargexx = Math.abs(NBTHelper.GetNBTint(Itemstack, "charge"));
            int cooldown = parameters.getEnchantedi("cooldown", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, Itemstack));
            float ff1 = timer * (chargexx / 4.0F) * 0.01745F;
            int special = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, Itemstack);
            this.plasmaaccelerator
               .render(
                  null,
                  ff1,
                  !(chargexx > 0.0F) ? 30.0F : 240.0F,
                  !(chargexx > cooldown) ? 30.0F : 240.0F,
                  !(chargexx > cooldown * 2) ? 30.0F : 240.0F,
                  special > 0 && chargexx > cooldown * 3 ? 1.0F : 0.0F,
                  1.0F
               );
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.depthMask(false);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texPlasmaAcceleratorOv);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            float of = timer;
            float of1 = MathHelper.cos(of * 0.015F) / 4.0F;
            float of2 = of * 0.015F;
            GlStateManager.translate(of1, of2, 0.0F);
            GlStateManager.matrixMode(5888);
            GlStateManager.enableBlend();
            Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
            this.plasmaacceleratorOv.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            GlStateManager.matrixMode(5888);
            GlStateManager.disableBlend();
            GlStateManager.depthMask(true);
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               GlStateManager.color((float)(0.55F + Math.sin(timer / 100) / 4.0), 0.4F, 1.0F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.plasmaaccelerator
                  .render(
                     null,
                     ff1,
                     !(chargexx > 0.0F) ? 30.0F : 240.0F,
                     !(chargexx > cooldown) ? 30.0F : 240.0F,
                     !(chargexx > cooldown * 2) ? 30.0F : 240.0F,
                     0.0F,
                     1.0F
                  );
               this.plasmaacceleratorOv.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lbX, lbY);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.VACUUMGUN) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texVacuumGun);
            this.vacuumgun.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               GlStateManager.color((float)(0.55F + Math.sin(timer / 100) / 4.0), 0.4F, 1.0F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.vacuumgun.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.GEMSTAFF) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            int type = NBTHelper.GetNBTint(Itemstack, "type");
            float red = 0.0F;
            float green = 0.0F;
            float blue = 0.0F;
            switch (type) {
               case 0:
                  Minecraft.getMinecraft().renderEngine.bindTexture(this.texGemStaff0);
                  red = 0.4F;
                  green = 0.7F;
                  blue = 1.0F;
                  break;
               case 1:
                  Minecraft.getMinecraft().renderEngine.bindTexture(this.texGemStaff1);
                  red = 0.9F;
                  green = 0.05F;
                  blue = 0.1F;
                  break;
               case 2:
                  Minecraft.getMinecraft().renderEngine.bindTexture(this.texGemStaff2);
                  red = 0.05F;
                  green = 0.05F;
                  blue = 0.9F;
                  break;
               case 3:
                  Minecraft.getMinecraft().renderEngine.bindTexture(this.texGemStaff3);
                  red = 0.05F;
                  green = 0.9F;
                  blue = 0.2F;
                  break;
               case 4:
                  Minecraft.getMinecraft().renderEngine.bindTexture(this.texGemStaff4);
                  red = 0.9F;
                  green = 0.85F;
                  blue = 0.3F;
                  break;
               case 5:
                  Minecraft.getMinecraft().renderEngine.bindTexture(this.texGemStaff5);
                  red = 0.7F;
                  green = 0.05F;
                  blue = 0.8F;
                  break;
               case 6:
                  Minecraft.getMinecraft().renderEngine.bindTexture(this.texGemStaff6);
                  red = 0.9F;
                  green = 0.5F;
                  blue = 0.65F;
                  break;
               case 7:
                  Minecraft.getMinecraft().renderEngine.bindTexture(this.texGemStaff7);
                  red = 0.9F;
                  green = 0.91F;
                  blue = 0.85F;
            }

            this.gemstaff.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               GlStateManager.color(red, green, blue, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.gemstaff.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.WANDOFCOLD) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texWandOfCold);
            this.wandofcold.render(null, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               GlStateManager.color(0.3F, (float)(0.78F + Math.sin(timer / 100) / 4.0), 1.0F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.wandofcold.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.ICEBEAM) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texIceBeam);
            this.icebeam.render(null, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               GlStateManager.color(0.3F, (float)(0.78F + Math.sin(timer / 100) / 4.0), 1.0F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.icebeam.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.SKULLCRASHER) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texSkullCrasher);
            this.skullcrasher.render(null, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               float f4 = (float)(Math.sin(timer / 100) / 2.0);
               GlStateManager.color(1.0F - f4, 0.5F + f4, 0.3F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.skullcrasher.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.SPELLHAMMER) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texSpellHammer);
            this.spellhammer.render(null, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               GlStateManager.color(
                  0.4F + AnimationTimer.rainbow2 / 34.0F, (float)(0.48F + Math.sin(timer / 100) / 4.0), 0.6F + AnimationTimer.rainbow1 / 34.0F, 1.0F
               );
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.spellhammer.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.ACIDFIRE) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            int mode = NBTHelper.GetNBTint(Itemstack, "mode");
            if (mode != 0) {
               if (mode != 1) {
                  Minecraft.getMinecraft().renderEngine.bindTexture(this.texAcidFire3);
               } else {
                  Minecraft.getMinecraft().renderEngine.bindTexture(this.texAcidFire2);
               }
            } else {
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texAcidFire1);
            }

            this.acidfire.render(null, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               float f4 = (float)(Math.sin(timer / 100) / 5.0);
               GlStateManager.color(0.9F - f4, 0.8F + f4, 0.3F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.acidfire.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.GLOWBLADE) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texGlowBlade);
            this.glowblade.render(null, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               float f4 = (float)(Math.sin(timer / 100) / 4.0);
               GlStateManager.color(0.7F - f4, 0.8F - f4 / 2.0F, 0.55F + f4, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.glowblade.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.NAILGUN) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texNailGun);
            this.nailgun.render(null, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               float f4 = (float)(Math.sin(timer / 100) / 2.0);
               GlStateManager.color(1.0F - f4, 0.5F + f4, 0.3F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.nailgun.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.CRYSTALSTAR) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texCrystalStar);
            this.crystalstar.render(null, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               float f4 = (float)(Math.sin(timer / 100) / 2.0);
               GlStateManager.color(0.4F, 0.6F + f4, 0.5F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.crystalstar.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.TOXINIUMSHOTGUN) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texToxiniumShotgun);
            this.toxiniumshotgun.render(null, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               float f4 = (float)(Math.sin(timer / 100) / 2.0);
               GlStateManager.color(0.85F - f4, 0.8F + f4, 0.3F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.toxiniumshotgun.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.SUBMACHINE) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texSubmachine);
            float heat = Math.max((NBTHelper.GetNBTint(Itemstack, "heat") - 700) / 1700.0F, 0.0F);
            this.submachine.render(null, 1.0F, 0.0F, 1.0F, 1.0F, heat, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               float f4 = (float)(Math.sin(timer / 100) / 2.0);
               GlStateManager.color(0.95F - f4, 0.75F + f4, 0.6F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.submachine.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }

            if (heat > 0.0F) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texSubmachineOverheat);
               GlStateManager.enableBlend();
               GlStateManager.color(heat * 1.69F, heat * 1.3F, heat, 1.0F);
               AbstractMobModel.light(240, false);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               this.submachine.render(null, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               AbstractMobModel.returnlight();
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.THISTLETHORN) {
            boolean powered = NBTHelper.GetNBTboolean(Itemstack, "powered");
            boolean using = false;
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            if (using) {
               GlStateManager.rotate(60.0F, 1.0F, 0.0F, 0.0F);
               GlStateManager.translate(0.0F, -4.0F, -5.0F);
            }

            Minecraft.getMinecraft().renderEngine.bindTexture(this.texThistleThorn);
            this.thistlethorn.render(null, 1.0F, 0.0F, !powered ? 1.0F : 2.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               if (using) {
                  GlStateManager.rotate(60.0F, 1.0F, 0.0F, 0.0F);
                  GlStateManager.translate(0.0F, -4.0F, -5.0F);
               }

               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               if (!powered) {
                  GlStateManager.color(0.95F, 1.0F, (float)(0.25 + Math.sin(timer / 100) / 5.0), 1.0F);
               } else {
                  GlStateManager.color(0.75F, (float)(0.28F + Math.sin(timer / 100) / 5.0), 1.0F, 1.0F);
               }

               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.thistlethorn.render(null, 1.0F, 1.0F, !powered ? 1.0F : 2.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.RESTLESSSKULL) {
            int challengeblock = !NBTHelper.GetNBTboolean(Itemstack, "challengeblock") ? 0 : 1;
            int challengemob = !NBTHelper.GetNBTboolean(Itemstack, "challengemob") ? 0 : 1;
            int challengeflame = !NBTHelper.GetNBTboolean(Itemstack, "challengeflame") ? 0 : 1;
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texRestlessSkull);
            this.restlessSkullModel.render(null, challengeblock, challengemob, challengeflame, 0.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               GlStateManager.color(0.5F, (float)(0.78F + Math.sin(timer / 100) / 4.0), 1.0F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.restlessSkullModel.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.MAGICROCKET) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texMagicRocket);
            this.magicRocketModel.render(null, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               float f4 = (float)(Math.sin(timer / 100) / 4.0);
               GlStateManager.color(0.1F, 0.5F + f4, 1.0F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.magicRocketModel.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.STINGINGCELL) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texStingingCell);
            this.stingingCellModel.render(null, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               float f4 = (float)(Math.sin(timer / 100) / 4.0);
               GlStateManager.color(0.4F, 0.7F + f4, 1.0F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.stingingCellModel.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.SEAEFFLORESCE) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texSeaEffloresce);
            this.seaeffloresce.render(null, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               float f4 = (float)(Math.sin(timer / 100) / 4.0);
               GlStateManager.color(0.1F, 0.7F + f4, 0.9F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.seaeffloresce.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.CORALRIFLE) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texCoralRifle);
            this.coralRifleModel.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               GlStateManager.color(0.4F + AnimationTimer.rainbow1 / 34.0F, (float)(0.75 + Math.sin(timer / 100) / 4.0), 1.0F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.coralRifleModel.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.HADRONBLASTER) {
            float lazerdel = Math.abs(NBTHelper.GetNBTint(Itemstack, "laserdelay"));
            float capturetime = NBTHelper.GetNBTint(Itemstack, "capturetime");
            float lazerF = Math.min(lazerdel / 16.0F, 1.0F);
            float rotatF = MathHelper.wrapDegrees(timer * 2) * lazerF + capturetime * 9.0F;
            float coreF = Math.min(NBTHelper.GetNBTint(Itemstack, "hadrons") / 680.0F + 0.8F, 2.0F);
            float captureF;
            if (!(capturetime < 10.0F)) {
               if (!(capturetime > 30.0F)) {
                  captureF = 1.0F;
               } else {
                  captureF = 1.0F - (capturetime - 30.0F) / 10.0F;
               }
            } else {
               captureF = capturetime / 10.0F;
            }

            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texHadronBlaster);
            this.hadronBlasterModel.render(null, lazerF, captureF, rotatF, coreF, 0.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               GlStateManager.color(1.0F, (float)(0.75 + Math.sin(timer / 100) / 4.0), 0.1F + AnimationTimer.rainbow1 / 34.0F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.hadronBlasterModel.render(null, lazerF, captureF, rotatF, coreF, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }

            if (NBTHelper.GetNBTboolean(Itemstack, "sensor")) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texHadronBlaster);
               GlStateManager.enableBlend();
               GlStateManager.color(1.0F, 0.75F, 0.0F, 1.0F);
               AbstractMobModel.light(240, false);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               this.hadronBlasterModel.render(null, lazerF, captureF, rotatF, coreF, 2.0F, 1.0F);
               AbstractMobModel.returnlight();
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.SNAPBALL) {
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(Itemstack.getItem());
            float reloadTime = ((IWeapon)item).getReloadTime(Itemstack);
            boolean charged = NBTHelper.GetNBTint(Itemstack, "charge") > parameters.geti("charge_to_powered");
            float reloadTick = NBTHelper.GetNBTint(Itemstack, "reload_time");
            if (reloadTick < reloadTime) {
               reloadTick -= Minecraft.getMinecraft().getRenderPartialTicks();
            }

            float reloadF = 1.0F - MathHelper.clamp(reloadTick, 0.0F, reloadTime) / reloadTime;
            float coreScale = 3.0F + 3.5F * GetMOP.getfromto(reloadF, 0.3F, 0.8F);
            boolean rendercore = NBTHelper.GetNBTint(Itemstack, "ammo") > 0;
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texSnapball);
            this.snapballModel.render(null, reloadF, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
            if (rendercore) {
               GlStateManager.translate(0.0, 0.5 - 1.3 * GetMOP.getfromto(reloadF, 0.2F, 0.3F), 6.0F - 5.0F * GetMOP.getfromto(reloadF, 0.0F, 0.2F));
               GlStateManager.scale(coreScale, coreScale, coreScale);
               GlStateManager.pushMatrix();
               Minecraft.getMinecraft().renderEngine.bindTexture(texSnapballCore);
               GlStateManager.rotate(AnimationTimer.tick * 2, 0.0F, 4.0F, 1.0F);
               if (!charged) {
                  sphere1.renderWithLight(1.0F, 1.0F, 1.4F);
               } else {
                  sphere1.renderWithLight(1.4F, 1.2F, 0.9F);
               }

               GlStateManager.popMatrix();
               GlStateManager.pushMatrix();
               GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
               GlStateManager.rotate(AnimationTimer.tick / 3.0F, 0.0F, 1.0F, 0.0F);
               GlStateManager.enableBlend();
               if (!charged) {
                  AbstractMobModel.light(200, false);
                  GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.DST_COLOR);
                  Minecraft.getMinecraft().renderEngine.bindTexture(gloss1);
               } else {
                  AbstractMobModel.light(230, false);
                  GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
                  Minecraft.getMinecraft().renderEngine.bindTexture(lightnings);
                  GlStateManager.color(1.0F, 1.0F, 0.4F);
               }

               sphere2.renderScaledtexture();
               AbstractMobModel.returnlight();
               AbstractMobModel.alphaGlowDisable();
               GlStateManager.disableBlend();
               GlStateManager.popMatrix();
            }

            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               float colRG = MathHelper.clamp(0.1F + AnimationTimer.rainbow1 / 34.0F, 0.0F, 1.0F);
               GlStateManager.color(colRG, colRG, 1.0F - colRG * 0.6F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.snapballModel.render(null, reloadF, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.popMatrix();
            }

            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         }

         if (item == ItemsRegister.ROCKETLAUNCHER) {
            int reltimeMax = ((IWeapon)item).getReloadTime(Itemstack);
            float rel = NBTHelper.GetNBTint(Itemstack, "reload_time");
            if (rel < reltimeMax) {
               rel -= Minecraft.getMinecraft().getRenderPartialTicks();
            }

            float reloadValue = MathHelper.clamp(1.0F - rel / reltimeMax, 0.0F, 1.0F);
            int needModeTick = (17 - EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, Itemstack) * 4) * 3;
            float modev = GetMOP.partial(
               (float)Math.abs(NBTHelper.GetNBTint(Itemstack, "firemode")), (float)Math.abs(NBTHelper.GetNBTint(Itemstack, "prevfiremode"))
            );
            float modeF = Math.min(modev / needModeTick, 1.0F);
            int ammo = NBTHelper.GetNBTint(Itemstack, "ammo");
            ItemRocket rocket = ItemRocket.getItemRocketFromString(NBTHelper.GetNBTstring(Itemstack, "rocket"));
            int color = rocket == null ? 16777215 : ColorConverters.RGBtoDecimal(rocket.colorR, rocket.colorG, rocket.colorB);
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(texRocketLauncher);
            this.rocketLauncherModel
               .render(
                  null,
                  GetMOP.partial(NBTHelper.GetNBTfloat(Itemstack, "angle"), NBTHelper.GetNBTfloat(Itemstack, "prevangle")),
                  reloadValue,
                  modeF,
                  ammo,
                  color,
                  1.0F
               );
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               GlStateManager.color(1.0F, (float)(0.45F + Math.sin(timer / 100) / 4.0), 0.45F + AnimationTimer.rainbow1 / 34.0F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.rocketLauncherModel.render(null, NBTHelper.GetNBTfloat(Itemstack, "angle"), reloadValue, modeF, ammo, color, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item instanceof ItemMagicScroll) {
            ItemMagicScroll itemms = (ItemMagicScroll)item;
            int scrolling = NBTHelper.GetNBTint(Itemstack, "scrolling");
            float scrF = MathHelper.clamp(scrolling, 0, 10) / 10.0F;
            float unscrF = 1.0F - scrF;
            float runeF = GetMOP.getfromto(scrF, 0.3F, 1.0F);
            GlStateManager.pushMatrix();
            GlStateManager.disableCull();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(texMagicScroll);
            GlStateManager.color(itemms.Red, itemms.Green, itemms.Blue, 1.0F);
            this.magicScrollModel.render(null, unscrF, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               GlStateManager.color(itemms.Red, itemms.Green, itemms.Blue, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.magicScrollModel.render(null, unscrF, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }

            GlStateManager.pushMatrix();
            GlStateManager.scale(0.4F, 0.4F, 0.4F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(0.0, 1.9 + Math.sin(timer / 80.0F) * 0.1, 1.8F);
            int indx = itemms.scrollID;
            float one = 0.125F;
            float two = 0.25F;
            float texX = indx % 4 * two;
            float texY = indx / 4 * one;
            GL11.glDisable(2896);
            GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
            GlStateManager.enableBlend();
            Minecraft.getMinecraft().renderEngine.bindTexture(texScrollSpells);
            float col = (float)(0.75 + Math.sin(timer / 67.0F) * 0.25);
            GlStateManager.color(col, col, col, 1.0F);
            AbstractMobModel.light(180, true);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
            bufferbuilder.pos(-runeF, -runeF, -0.25).tex(texX + one, texY).endVertex();
            bufferbuilder.pos(-runeF, runeF, -0.25).tex(texX + one, texY + one).endVertex();
            bufferbuilder.pos(runeF, runeF, -0.25).tex(texX + two, texY + one).endVertex();
            bufferbuilder.pos(runeF, -runeF, -0.25).tex(texX + two, texY).endVertex();
            tessellator.draw();
            GlStateManager.rotate(timer / 2.0F, 0.0F, 0.0F, 1.0F);
            float col2 = (float)(0.75 + Math.sin(timer / 52.0F) * 0.25);
            GlStateManager.color(col2, col2, col2, 1.0F);
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
            bufferbuilder.pos(-runeF, -runeF, 0.0).tex(texX, texY).endVertex();
            bufferbuilder.pos(-runeF, runeF, 0.0).tex(texX, texY + one).endVertex();
            bufferbuilder.pos(runeF, runeF, 0.0).tex(texX + one, texY + one).endVertex();
            bufferbuilder.pos(runeF, -runeF, 0.0).tex(texX + one, texY).endVertex();
            tessellator.draw();
            AbstractMobModel.returnlight();
            GL11.glEnable(2896);
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
            GlStateManager.disableBlend();
            GlStateManager.enableCull();
            GlStateManager.popMatrix();
         }

         if (item == ItemsRegister.CHAINMACEIRON) {
            boolean throwed = NBTHelper.GetNBTboolean(Itemstack, "throwed");
            GlStateManager.pushMatrix();
            GlStateManager.disableCull();
            GlStateManager.scale(0.09F, 0.09F, 0.09F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(texChainMaceIron);
            chainMaceModel.render(null, !throwed ? 0.0F : -1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.enableCull();
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.09F, 0.09F, 0.09F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               GlStateManager.color(0.75F, 0.0F, 0.75F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               chainMaceModel.render(null, !throwed ? 0.0F : -1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.CHAINMACEDIAMOND) {
            boolean throwed = NBTHelper.GetNBTboolean(Itemstack, "throwed");
            GlStateManager.pushMatrix();
            GlStateManager.disableCull();
            GlStateManager.scale(0.09F, 0.09F, 0.09F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(texChainMaceDiamond);
            chainMaceModel.render(null, !throwed ? 0.0F : -1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.enableCull();
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.09F, 0.09F, 0.09F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               GlStateManager.color(0.75F, 0.0F, 0.75F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               chainMaceModel.render(null, !throwed ? 0.0F : -1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.CHAINMACEMOLTEN) {
            boolean throwed = NBTHelper.GetNBTboolean(Itemstack, "throwed");
            GlStateManager.pushMatrix();
            GlStateManager.disableCull();
            GlStateManager.scale(0.09F, 0.09F, 0.09F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(texChainMaceMolten);
            GL11.glDisable(2896);
            AbstractMobModel.light(180, true);
            if (!throwed) {
               chainMaceModel.render(null, -2.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
            }

            AbstractMobModel.returnlight();
            GL11.glEnable(2896);
            chainMaceModel.render(null, -1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.enableCull();
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.09F, 0.09F, 0.09F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               GlStateManager.color(1.0F, (float)(0.65F + Math.sin(timer / 100) / 4.0), 0.4F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               chainMaceModel.render(null, !throwed ? 0.0F : -1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.ICEBREAKER) {
            boolean throwedx = NBTHelper.GetNBTboolean(Itemstack, "throwed");
            GlStateManager.pushMatrix();
            GlStateManager.disableCull();
            GlStateManager.scale(0.09F, 0.09F, 0.09F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(texIcebreaker);
            GL11.glDisable(2896);
            AbstractMobModel.light(180, true);
            if (!throwedx) {
               chainMaceModel.render(null, -2.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
            }

            AbstractMobModel.returnlight();
            GL11.glEnable(2896);
            chainMaceModel.render(null, -1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.enableCull();
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.09F, 0.09F, 0.09F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               GlStateManager.color(0.3F, (float)(0.78F + Math.sin(timer / 100) / 4.0), 1.0F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               chainMaceModel.render(null, !throwedx ? 0.0F : -1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.ECHINUS) {
            boolean throwedxx = NBTHelper.GetNBTboolean(Itemstack, "throwed");
            int kills = NBTHelper.GetNBTint(Itemstack, "kills");
            boolean active = NBTHelper.GetNBTboolean(Itemstack, "active");
            GlStateManager.pushMatrix();
            GlStateManager.disableCull();
            GlStateManager.scale(0.09F, 0.09F, 0.09F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(texEchinusModel);
            float anim1 = 0.0F;
            if (kills >= 16 && !active) {
               anim1 = MathHelper.sin(timer / 15.0F) + 1.0F;
            }

            float anim2 = 0.0F;
            if (active) {
               anim2 = MathHelper.sin(timer / 15.0F) + 1.0F;
            }

            GlStateManager.color(1.0F - anim1 * 0.35F, 1.0F - anim2 * 0.43F, 1.0F - anim1 * 0.06F, 1.0F);
            AbstractMobModel.light((int)(60.0F * Math.max(anim1, anim2)), true);
            echinusModel.render(null, !throwedxx ? 0.0F : -1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            AbstractMobModel.returnlight();
            GlStateManager.enableCull();
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.09F, 0.09F, 0.09F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f4 = (float)Math.sin(timer / 110);
               GlStateManager.color(0.1F + f4 * 0.6F, 0.7F - f4 * 0.3F, 0.9F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               echinusModel.render(null, !throwedxx ? 0.0F : -1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item instanceof ItemGrenade) {
            ItemGrenade granade = (ItemGrenade)item;
            GlStateManager.pushMatrix();
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            granade.renderGrenade(null, 0.0, 0.0, 0.0, 0.0F, partialTicks, null, false);
            GlStateManager.popMatrix();
         }

         if (item == ItemsRegister.ADAMANTIUMREVOLVER) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(texAdamantiumRevolver);
            this.adamantiumRevolverModel.render(null, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               float f4 = (float)(Math.sin(timer / 100) / 2.0);
               GlStateManager.color(1.0F - f4, 0.1F, 0.1F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.adamantiumRevolverModel.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.ADAMANTIUMBATTLEAXE) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(texAdamantiumBattleAxe);
            this.adamantiumBattleAxeModel.render(null, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               float f4 = (float)(Math.sin(timer / 100) / 2.0);
               GlStateManager.color(1.0F - f4, 0.1F, 0.1F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.adamantiumBattleAxeModel.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.ENDERPROTECTOR) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(texEnderProtector);
            this.enderProtectorModel.render(null, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               GlStateManager.color((float)(0.55F + Math.sin(timer / 100) / 4.0), 0.4F, 1.0F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.enderProtectorModel.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.DRAGONTAIL) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(texDragonTail);
            this.dragonTailModel.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               GlStateManager.color((float)(0.55F + Math.sin(timer / 100) / 4.0), 0.4F, 1.0F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.dragonTailModel.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.DRAGONSHELL) {
            float fsh = NBTHelper.GetNBTint(Itemstack, "blocking");
            float f1sh = NBTHelper.GetNBTint(Itemstack, "eyerand");
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(texDragonShell);
            this.dragonShellModel.render(null, fsh, f1sh, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               GlStateManager.color((float)(0.55F + Math.sin(timer / 100) / 4.0), 0.4F, 1.0F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.dragonShellModel.render(null, fsh, f1sh, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.WINTERBREATH) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(texWinterBreath);
            this.winterBreathModel.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               GlStateManager.color(0.3F, (float)(0.78F + Math.sin(timer / 100) / 4.0), 1.0F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.winterBreathModel.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.TOXINIUMSHIELD) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(texToxiniumShield);
            this.toxiniumShieldModel.render(null, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               float f4 = (float)(Math.sin(timer / 100) / 2.0);
               GlStateManager.color(0.8F - f4, 0.8F + f4, 0.3F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.toxiniumShieldModel.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.CARAPACE) {
            WeaponParameters parametersx = WeaponParameters.getWeaponParameters(Itemstack.getItem());
            int ticksusing = NBTHelper.GetNBTint(Itemstack, "tickusing");
            int endtime = parametersx.getEnchantedi("bonus_end_time", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, Itemstack));
            int begintime = parametersx.getEnchantedi("bonus_begin_time", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, Itemstack));
            float ft1 = GetMOP.getfromto((float)ticksusing, (float)(endtime - 15), (float)endtime);
            float ft2 = 1.0F - ft1;
            float ft3 = GetMOP.getfromto((float)ticksusing, (float)(begintime - 10), (float)begintime)
               - GetMOP.getfromto((float)ticksusing, (float)begintime, (float)(begintime + 10));
            if (ft3 > 0.0F) {
               ft2 = ft3;
               ft1 = 0.1F;
            }

            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(texCarapace);
            this.carapaceModel.render(null, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               float f4 = (float)(Math.sin(timer / 100) / 4.0);
               GlStateManager.color(0.5F + f4, 1.0F - f4, 0.55F - f4 / 2.0F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.carapaceModel.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }

            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            GL11.glDisable(2896);
            GlStateManager.depthMask(false);
            Minecraft.getMinecraft().renderEngine.bindTexture(texCarapace);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
            if (ft1 > 0.0F && ft1 < 1.0F) {
               if (!(ft3 > 0.0F)) {
                  GlStateManager.color(0.6F * ft2, 1.0F * ft2, 0.55F * ft2, 1.0F);
               } else {
                  GlStateManager.color(1.0F * ft2, 0.2F * ft2, 0.25F * ft2, 1.0F);
               }

               Minecraft.getMinecraft().renderEngine.bindTexture(texCarapace);
               this.carapaceModel.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F + 0.15F * ft1);
            }

            Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
            GlStateManager.disableBlend();
            GlStateManager.depthMask(true);
            GL11.glEnable(2896);
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
            GlStateManager.popMatrix();
         }

         if (item == ItemsRegister.ROTTENSHIELD) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(texRottenShield);
            this.rottenShieldModel.render(null, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               float f4 = (float)(Math.sin(timer / 100) / 2.0);
               GlStateManager.color(0.8F - f4, 0.8F + f1, 0.2F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.rottenShieldModel.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.HELLMARK) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.09F, 0.09F, 0.09F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(texHellmark);
            this.hellmarkModel.render(null, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.09F, 0.09F, 0.09F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch4);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               GlStateManager.color(1.0F, (float)(0.65F + Math.sin(timer / 100) / 4.0), 0.4F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.hellmarkModel.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.BLOWHOLE) {
            int chargeint = NBTHelper.GetNBTint(Itemstack, "charge");
            float chargexx = chargeint <= 0 ? 0.0F : Math.min(chargeint - 1.0F + partialTicks, 30.0F);
            float chargeInfin = chargeint <= 0 ? 0.0F : chargeint - 1.0F + partialTicks;
            int relx = NBTHelper.GetNBTint(Itemstack, "reload_time");
            int maxrel = relx <= 0 ? 0 : ((IWeapon)Itemstack.getItem()).getReloadTime(Itemstack);
            GlStateManager.pushMatrix();
            float translateAmount = chargexx / 2000.0F;
            GlStateManager.translate(0.0, Math.sin(chargeInfin * 1.8F + 7.0F) * translateAmount, Math.sin(-chargeInfin * 1.6F + 11.0F) * translateAmount);
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.disableCull();
            Minecraft.getMinecraft().renderEngine.bindTexture(texBlowhole);
            this.blowholeModel.render(null, 0.0F, 0.0F, chargexx, relx, maxrel, 1.0F);
            GlStateManager.enableCull();
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               float f4 = (float)(Math.sin(timer / 100) / 4.0);
               GlStateManager.color(0.4F + AnimationTimer.rainbow1 / 34.0F, 0.7F + f4, 1.0F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.blowholeModel.render(null, 1.0F, 1.0F, chargexx, relx, maxrel, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }

            if (chargeint > 0) {
               GlStateManager.enableBlend();
               AbstractMobModel.light(150, false);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.DST_COLOR);
               GlStateManager.pushMatrix();
               GlStateManager.translate(-0.45F - chargexx * 0.015F, -0.1F, 0.0F);
               Minecraft.getMinecraft().renderEngine.bindTexture(texBlowholeBubble);
               GlStateManager.rotate(chargexx, 1.0F, 0.0F, 0.0F);
               GlStateManager.rotate(AnimationTimer.tick, 0.0F, 1.0F, 0.0F);
               GlStateManager.rotate(-chargexx / 2.0F, 0.0F, 0.0F, 1.0F);
               float scl = chargexx * 0.06F + 0.3F;
               GlStateManager.scale(scl, scl, scl);
               if (chargeint >= 10) {
                  if (chargeint >= 20) {
                     RenderSpecial.sphere6.renderScaledtexture();
                  } else {
                     RenderSpecial.sphere5.renderScaledtexture();
                  }
               } else {
                  RenderSpecial.sphere4.renderScaledtexture();
               }

               GlStateManager.popMatrix();
               GlStateManager.pushMatrix();
               GlStateManager.translate(-0.45F - chargexx * 0.015F, -0.1F, 0.0F);
               GlStateManager.rotate(-chargexx / 3.0F, 1.0F, 0.0F, 0.0F);
               GlStateManager.rotate(-AnimationTimer.tick * 2, 0.0F, 1.0F, 0.0F);
               GlStateManager.rotate(chargexx * 2.0F, 0.0F, 0.0F, 1.0F);
               scl += 0.1F;
               GlStateManager.scale(scl, scl, scl);
               if (!(chargexx < 10.0F)) {
                  if (!(chargexx < 20.0F)) {
                     RenderSpecial.sphere6.renderScaledtexture();
                  } else {
                     RenderSpecial.sphere5.renderScaledtexture();
                  }
               } else {
                  RenderSpecial.sphere4.renderScaledtexture();
               }

               GlStateManager.popMatrix();
               AbstractMobModel.returnlight();
               AbstractMobModel.alphaGlowDisable();
               GlStateManager.disableBlend();
            }

            GlStateManager.popMatrix();
         }

         if (item == ItemsRegister.CRYSTALCUTTER) {
            float anim = GetMOP.partial(
               (float)NBTHelper.GetNBTint(Itemstack, "animation"), (float)NBTHelper.GetNBTint(Itemstack, "prevanimation"), partialTicks
            );
            int ammo = Math.min(NBTHelper.GetNBTint(Itemstack, "ammo"), 7 - (int)(anim / 10.0F));
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.09F, 0.09F, 0.09F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(texCrystalCutter);
            this.crystalCutterModel.render(null, 0.0F, ammo, anim / 10.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.09F, 0.09F, 0.09F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               GlStateManager.color(1.0F, 0.4F + AnimationTimer.rainbow1 / 44.0F, (float)(0.65F + Math.sin(timer / 100) / 4.0), 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.crystalCutterModel.render(null, 1.0F, ammo, anim / 10.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.PLASMAMINIGUN) {
            float animDeploy = GetMOP.partial(
               NBTHelper.GetNBTfloat(Itemstack, "deploy"), NBTHelper.GetNBTfloat(Itemstack, "prevdeploy"), Minecraft.getMinecraft().getRenderPartialTicks()
            );
            float animRotate = GetMOP.partial(
               NBTHelper.GetNBTfloat(Itemstack, "rotate"), NBTHelper.GetNBTfloat(Itemstack, "prevrotate"), Minecraft.getMinecraft().getRenderPartialTicks()
            );
            int maxheat = WeaponParameters.getWeaponParameters(Itemstack.getItem()).geti("max_heat");
            float overheat = GetMOP.getfromto(Math.abs(NBTHelper.GetNBTfloat(Itemstack, "heat")), maxheat / 4.0F, (float)maxheat);
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(texPlasmaMinigun);
            this.plasmaMinigunModel.render(null, 1.0F, animDeploy, animRotate, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               GlStateManager.color((float)(0.55F + Math.sin(timer / 100) / 4.0), 0.4F, 1.0F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.plasmaMinigunModel.render(null, 1.0F, animDeploy, animRotate, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }

            if (overheat > 0.0F) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(texPlasmaMinigunOverheat);
               GlStateManager.enableBlend();
               GlStateManager.color(overheat, overheat, overheat, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               this.plasmaMinigunModel.render(null, 1.0F, animDeploy, animRotate, 0.0F, 1.0F, 1.0F);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.CERATARGET) {
            int ammo = NBTHelper.GetNBTint(Itemstack, "ammo");
            int reltimeMaxx = ((IWeapon)item).getReloadTime(Itemstack);
            int relxx = NBTHelper.GetNBTint(Itemstack, "reload_time");
            float reloadValue = MathHelper.clamp(1.0F - (float)relxx / reltimeMaxx, 0.0F, 1.0F);
            float ftPearl = 1.0F - GetMOP.getfromto(reloadValue, 0.15F, 0.65F);
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.disableCull();
            Minecraft.getMinecraft().renderEngine.bindTexture(texCeratargetModel);
            this.ceratargetModel.render(null, 0.0F, reloadValue, ammo, 0.0F, 0.0F, 1.0F);
            GlStateManager.enableCull();
            GlStateManager.popMatrix();
            boolean b1 = ftPearl > 0.0F && ftPearl < 1.0F;
            if (Itemstack.isItemEnchanted() || b1) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float f = timer;
               float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
               float f2 = f * 0.01F;
               GlStateManager.translate(f1, f2, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3 = 0.5F;
               float f4 = (float)(Math.sin(timer / 100) / 4.0);
               GlStateManager.color(0.4F + AnimationTimer.rainbow1 / 34.0F, 0.7F + f4, 1.0F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               if (Itemstack.isItemEnchanted()) {
                  this.ceratargetModel.render(null, 1.0F, reloadValue, ammo, 0.0F, 0.0F, 1.0F);
               }

               if (b1) {
                  Minecraft.getMinecraft().renderEngine.bindTexture(texCeratargetModel);

                  for (int i = 1; i < 6; i++) {
                     float sizee = ftPearl * (3.0F + Debugger.floats[5]) * i;
                     float color = 3.0F + Debugger.floats[4] - sizee;
                     if (color > 0.0F) {
                        GlStateManager.color(color, color, color, color);
                        this.ceratargetModel.render(null, 0.0F, reloadValue, ammo, i, sizee, 1.0F);
                     }
                  }
               }

               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.GRENADELAUNCHER) {
            int ammo = NBTHelper.GetNBTint(Itemstack, "ammo");
            int reltimeMaxx = ((IWeapon)item).getReloadTime(Itemstack);
            float relxx = NBTHelper.GetNBTint(Itemstack, "reload_time");
            if (relxx < reltimeMaxx) {
               relxx -= Minecraft.getMinecraft().getRenderPartialTicks();
            }

            float reloadValue = MathHelper.clamp(1.0F - relxx / reltimeMaxx, 0.0F, 1.0F);
            ItemRocket rocket = ItemRocket.getItemRocketFromString(NBTHelper.GetNBTstring(Itemstack, "rocket"));
            int color = rocket == null ? 16777215 : ColorConverters.RGBtoDecimal(rocket.colorR, rocket.colorG, rocket.colorB);
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.09F, 0.09F, 0.09F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(texGrenadeLauncherModel);
            this.grenadeLauncherModel.render(null, 0.0F, reloadValue, ammo, color, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.09F, 0.09F, 0.09F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float fx = timer;
               float f1x = MathHelper.cos(fx * 0.02F) / 2.0F;
               float f2x = fx * 0.01F;
               GlStateManager.translate(f1x, f2x, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               GlStateManager.color(0.75F, 0.0F, 0.75F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.grenadeLauncherModel.render(null, 1.0F, reloadValue, ammo, color, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.HOLYSHOTGUN) {
            int ammox = NBTHelper.GetNBTint(Itemstack, "ammo");
            int reltimeMaxxx = ((IWeapon)item).getReloadTime(Itemstack);
            float relxxx = NBTHelper.GetNBTint(Itemstack, "reload_time");
            if (relxxx < reltimeMaxxx) {
               relxxx -= Minecraft.getMinecraft().getRenderPartialTicks();
            }

            float reloadValue = MathHelper.clamp(1.0F - relxxx / reltimeMaxxx, 0.0F, 1.0F);
            ItemBullet bullet = ItemBullet.getItemBulletFromString(NBTHelper.GetNBTstring(Itemstack, "bullet"));
            int color = bullet == null ? 16777215 : ColorConverters.RGBtoDecimal(bullet.colorR, bullet.colorG, bullet.colorB);
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.09F, 0.09F, 0.09F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(texHolyShotgunModel);
            this.holyShotgunModel.render(null, 0.0F, reloadValue, ammox, color, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.09F, 0.09F, 0.09F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float fx = timer;
               float f1x = MathHelper.cos(fx * 0.02F) / 2.0F;
               float f2x = fx * 0.01F;
               GlStateManager.translate(f1x, f2x, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               GlStateManager.color(0.85F, 0.64F, 0.35F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.holyShotgunModel.render(null, 1.0F, reloadValue, ammox, color, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.MITHRILBOW) {
            float pull = NBTHelper.GetNBTint(Itemstack, "pulling");
            float pullMax = ((IWeapon)item).getCooldownTime(Itemstack);
            if (pull > 0.0F && pull < pullMax) {
               pull += Minecraft.getMinecraft().getRenderPartialTicks();
            }

            float progress = pull / pullMax;
            progress = MathHelper.clamp(progress * progress, 0.0F, 1.0F);
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.09F, 0.09F, 0.09F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(texMithrilBowModel);
            this.mithrilBowModel.render(null, progress, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.popMatrix();
            AbstractBow.renderArrowInBow(Itemstack, progress, partialTicks, 0.62F);
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.09F, 0.09F, 0.09F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float fx = timer;
               float f1x = MathHelper.cos(fx * 0.02F) / 2.0F;
               float f2x = fx * 0.01F;
               GlStateManager.translate(f1x, f2x, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               GlStateManager.color(0.85F, 0.64F, 0.35F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.mithrilBowModel.render(null, progress, 1.0F, 0.0F, 0.0F, 0.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.COMPOUNDBOW) {
            float pullx = NBTHelper.GetNBTint(Itemstack, "pulling");
            float pullMaxx = ((IWeapon)item).getCooldownTime(Itemstack);
            if (pullx > 0.0F && pullx < pullMaxx) {
               pullx += Minecraft.getMinecraft().getRenderPartialTicks();
            }

            float progress = pullx / pullMaxx;
            progress = MathHelper.clamp(progress * progress, 0.0F, 1.0F);
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.09F, 0.09F, 0.09F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(texCompoundBowModel);
            this.compoundBowModel.render(null, progress, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.popMatrix();
            AbstractBow.renderArrowInBow(Itemstack, progress, partialTicks, 0.69F);
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.09F, 0.09F, 0.09F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float fx = timer;
               float f1x = MathHelper.cos(fx * 0.02F) / 2.0F;
               float f2x = fx * 0.01F;
               GlStateManager.translate(f1x, f2x, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f4x = (float)(Math.sin(timer / 100) / 2.0);
               GlStateManager.color(0.9F - f4x, 0.5F + f4x, 0.45F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.compoundBowModel.render(null, progress, 1.0F, 0.0F, 0.0F, 0.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.STAFFOFTHEAZUREORE) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(texAzureOreStaffModel);
            this.azureOreStaffModel.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            GL11.glDisable(2896);
            GlStateManager.depthMask(false);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            float fx = timer / 2.0F;
            float f1x = MathHelper.cos(fx * 0.02F) / 4.0F;
            float f2x = fx * 0.01F;
            GlStateManager.translate(f1x, f2x, 0.0F);
            GlStateManager.matrixMode(5888);
            GlStateManager.enableBlend();
            GlStateManager.color(0.1F, (float)(0.25 + Math.sin(timer / 140) / 5.0), 1.0F, 1.0F);
            GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch2);
            this.azureOreStaffModel.render(null, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
            if (Itemstack.isItemEnchanted()) {
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               this.azureOreStaffModel.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
            }

            Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            GlStateManager.matrixMode(5888);
            GlStateManager.disableBlend();
            GlStateManager.depthMask(true);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glEnable(2896);
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
            GlStateManager.popMatrix();
         }

         if (item == ItemsRegister.INSTANCER) {
            float animReady = GetMOP.partial(
                  (float)NBTHelper.GetNBTint(Itemstack, "ready"), (float)NBTHelper.GetNBTint(Itemstack, "prevready"), Minecraft.getMinecraft().getRenderPartialTicks()
               )
               / 15.0F;
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.09F, 0.09F, 0.09F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(texInstancerModel);
            this.instancerModel.render(null, animReady, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.09F, 0.09F, 0.09F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float fx = timer;
               float f1x = MathHelper.cos(fx * 0.02F) / 2.0F;
               float f2x = fx * 0.01F;
               GlStateManager.translate(f1x, f2x, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               GlStateManager.color(0.75F, 0.0F, 0.75F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.instancerModel.render(null, animReady, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.ENDERINSTANCER) {
            float animReady = GetMOP.partial(
                  (float)NBTHelper.GetNBTint(Itemstack, "ready"), (float)NBTHelper.GetNBTint(Itemstack, "prevready"), Minecraft.getMinecraft().getRenderPartialTicks()
               )
               / 15.0F;
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.09F, 0.09F, 0.09F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(texEnderInstancerModel);
            this.enderInstancerModel.render(null, animReady, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.09F, 0.09F, 0.09F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float fx = timer;
               float f1x = MathHelper.cos(fx * 0.02F) / 2.0F;
               float f2x = fx * 0.01F;
               GlStateManager.translate(f1x, f2x, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               GlStateManager.color(0.75F, 0.14F, 0.95F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.enderInstancerModel.render(null, animReady, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.WINTERINSTANCER) {
            float animReady = GetMOP.partial(
                  (float)NBTHelper.GetNBTint(Itemstack, "ready"), (float)NBTHelper.GetNBTint(Itemstack, "prevready"), Minecraft.getMinecraft().getRenderPartialTicks()
               )
               / 15.0F;
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.09F, 0.09F, 0.09F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(texWinterInstancerModel);
            this.winterInstancerModel.render(null, animReady, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.09F, 0.09F, 0.09F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float fx = timer;
               float f1x = MathHelper.cos(fx * 0.02F) / 2.0F;
               float f2x = fx * 0.01F;
               GlStateManager.translate(f1x, f2x, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               GlStateManager.color(0.3F, (float)(0.78F + Math.sin(timer / 100) / 4.0), 1.0F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.winterInstancerModel.render(null, animReady, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.MILITARYINSTANCER) {
            float animReady = GetMOP.partial(
                  (float)NBTHelper.GetNBTint(Itemstack, "ready"), (float)NBTHelper.GetNBTint(Itemstack, "prevready"), Minecraft.getMinecraft().getRenderPartialTicks()
               )
               / 15.0F;
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.09F, 0.09F, 0.09F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(texMilitaryInstancerModel);
            this.militaryInstancerModel.render(null, animReady, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.09F, 0.09F, 0.09F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float fx = timer;
               float f1x = MathHelper.cos(fx * 0.02F) / 2.0F;
               float f2x = fx * 0.01F;
               GlStateManager.translate(f1x, f2x, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f4x = (float)(Math.sin(timer / 100) / 2.0);
               GlStateManager.color(1.0F - f4x, 0.5F + f4x, 0.3F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.militaryInstancerModel.render(null, animReady, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.AQUATICINSTANCER) {
            float animReady = GetMOP.partial(
                  (float)NBTHelper.GetNBTint(Itemstack, "ready"), (float)NBTHelper.GetNBTint(Itemstack, "prevready"), Minecraft.getMinecraft().getRenderPartialTicks()
               )
               / 15.0F;
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.09F, 0.09F, 0.09F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(texAquaticInstancerModel);
            this.aquaticInstancerModel.render(null, animReady, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.09F, 0.09F, 0.09F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float fx = timer;
               float f1x = MathHelper.cos(fx * 0.02F) / 2.0F;
               float f2x = fx * 0.01F;
               GlStateManager.translate(f1x, f2x, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f4x = (float)(Math.sin(timer / 100) / 4.0);
               GlStateManager.color(0.4F + AnimationTimer.rainbow1 / 34.0F, 0.7F + f4x, 1.0F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.aquaticInstancerModel.render(null, animReady, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.VIOLENCE) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.09F, 0.09F, 0.09F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(texViolenceModel);
            this.violenceModel.render(null, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.09F, 0.09F, 0.09F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float fx = timer;
               float f1x = MathHelper.cos(fx * 0.02F) / 2.0F;
               float f2x = fx * 0.01F;
               GlStateManager.translate(f1x, f2x, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               GlStateManager.color(1.0F, 0.2F, 0.8F + (float)(Math.sin(timer / 100) / 4.0), 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.violenceModel.render(null, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.STAFFOFWITHERDRY) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.09F, 0.09F, 0.09F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(texStaffOfWitherdryModel);
            this.staffOfWitherdryModel.render(null, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.09F, 0.09F, 0.09F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch4);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float fx = timer;
               float f1x = MathHelper.cos(fx * 0.02F) / 2.0F;
               float f2x = fx * 0.01F;
               GlStateManager.translate(f1x, f2x, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3x = 0.5F;
               GlStateManager.color(1.0F, (float)(0.65F + Math.sin(timer / 100) / 4.0), 0.4F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.staffOfWitherdryModel.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.WHISPERSBLADE) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.09F, 0.09F, 0.09F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(texWhispersBladeModel);
            this.whispersBladeModel.render(null, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.09F, 0.09F, 0.09F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch4);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float fx = timer;
               float f1x = MathHelper.cos(fx * 0.02F) / 2.0F;
               float f2x = fx * 0.01F;
               GlStateManager.translate(f1x, f2x, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               GlStateManager.color(0.2F, 0.9F, 0.1F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.whispersBladeModel.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.VOLTRIDENT) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.09F, 0.09F, 0.09F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(texVoltridentModel);
            this.voltridentModel.render(null, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.09F, 0.09F, 0.09F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float fx = timer;
               float f1x = MathHelper.cos(fx * 0.02F) / 2.0F;
               float f2x = fx * 0.01F;
               GlStateManager.translate(f1x, f2x, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float ttt = MathHelper.sin(timer / 120) / 4.0F + 0.25F;
               GlStateManager.color(0.5F - ttt, 0.85F - ttt, 1.0F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.voltridentModel.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.PUMPSHOTGUN) {
            int reltimeMaxxxx = ((IWeapon)item).getReloadTime(Itemstack);
            float relxxxx = NBTHelper.GetNBTint(Itemstack, "reload_time");
            if (relxxxx < reltimeMaxxxx) {
               relxxxx -= Minecraft.getMinecraft().getRenderPartialTicks();
            }

            float reloadValue = MathHelper.clamp(1.0F - relxxxx / reltimeMaxxxx, 0.0F, 1.0F);
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.09F, 0.09F, 0.09F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(texPumpShotgunModel);
            this.pumpShotgunModel.render(null, 0.0F, reloadValue, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.09F, 0.09F, 0.09F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float fx = timer;
               float f1x = MathHelper.cos(fx * 0.02F) / 2.0F;
               float f2x = fx * 0.01F;
               GlStateManager.translate(f1x, f2x, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               GlStateManager.color(0.75F, 0.0F, 0.75F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.pumpShotgunModel.render(null, 1.0F, reloadValue, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.CINDERBOW) {
            float pullxx = NBTHelper.GetNBTint(Itemstack, "pulling");
            float pullMaxxx = ((IWeapon)item).getCooldownTime(Itemstack);
            if (pullxx > 0.0F && pullxx < pullMaxxx) {
               pullxx += Minecraft.getMinecraft().getRenderPartialTicks();
            }

            float progress = pullxx / pullMaxxx;
            progress = GetMOP.getfromto(progress, 0.0F, 0.45F) * 0.7F
               + GetMOP.getfromto(progress, 0.45F, 0.95F) * 0.1F
               + GetMOP.getfromto(progress, 0.95F, 1.0F) * 0.2F;
            progress = MathHelper.clamp(progress * progress, 0.0F, 1.0F);
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.09F, 0.09F, 0.09F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(texCinderBowModel);
            this.cinderBowModel.render(null, progress, 1.0F, 0.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.popMatrix();
            AbstractBow.renderArrowInBow(Itemstack, progress, partialTicks, 0.62F);
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.09F, 0.09F, 0.09F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.enableBlend();
            GL11.glDisable(2896);
            GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
            AbstractMobModel.light(150, false);
            GlStateManager.depthMask(false);
            Minecraft.getMinecraft().renderEngine.bindTexture(texCinderBowModel);
            this.cinderBowModel.render(null, progress, 2.0F, 0.0F, 0.0F, 0.0F, 1.0F);
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float fx = timer;
               float f1x = MathHelper.cos(fx * 0.02F) / 2.0F;
               float f2x = fx * 0.01F;
               GlStateManager.translate(f1x, f2x, 0.0F);
               GlStateManager.matrixMode(5888);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch4);
               GlStateManager.color(1.0F, (float)(0.65F + Math.sin(timer / 100) / 4.0), 0.4F, 1.0F);
               this.cinderBowModel.render(null, progress, 1.0F, 0.0F, 0.0F, 0.0F, 1.0F);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
            }

            AbstractMobModel.returnlight();
            Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
            GlStateManager.disableBlend();
            GlStateManager.depthMask(true);
            GL11.glEnable(2896);
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
            GlStateManager.popMatrix();
         }

         if (item == ItemsRegister.GOTHICBOW) {
            float pullxxx = NBTHelper.GetNBTint(Itemstack, "pulling");
            float pullMaxxxx = ((IWeapon)item).getCooldownTime(Itemstack);
            if (pullxxx > 0.0F && pullxxx < pullMaxxxx) {
               pullxxx += Minecraft.getMinecraft().getRenderPartialTicks();
            }

            float progress = pullxxx / pullMaxxxx;
            progress = MathHelper.clamp(progress * progress, 0.0F, 1.0F);
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.09F, 0.09F, 0.09F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(texGothicBowModel);
            this.gothicBowModel.render(null, progress, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.popMatrix();
            AbstractBow.renderArrowInBow(Itemstack, progress, partialTicks, 0.62F);
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.09F, 0.09F, 0.09F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float fx = timer;
               float f1x = MathHelper.cos(fx * 0.02F) / 2.0F;
               float f2x = fx * 0.01F;
               GlStateManager.translate(f1x, f2x, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               GlStateManager.color(0.3F, (float)(0.78F + Math.sin(timer / 100) / 4.0), 1.0F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.gothicBowModel.render(null, progress, 1.0F, 0.0F, 0.0F, 0.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.COOLEDRIFLE) {
            int reltimeMaxxxxx = ((IWeapon)item).getReloadTime(Itemstack);
            float relxxxxx = NBTHelper.GetNBTint(Itemstack, "reload_time");
            if (relxxxxx < reltimeMaxxxxx) {
               relxxxxx -= Minecraft.getMinecraft().getRenderPartialTicks();
            }

            float reloadValue = MathHelper.clamp(1.0F - relxxxxx / reltimeMaxxxxx, 0.0F, 1.0F);
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(texCooledRifleModel);
            this.cooledRifleModel.render(null, 1.0F, reloadValue, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float fx = timer;
               float f1x = MathHelper.cos(fx * 0.02F) / 2.0F;
               float f2x = fx * 0.01F;
               GlStateManager.translate(f1x, f2x, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3x = 0.5F;
               GlStateManager.color(0.3F, (float)(0.78F + Math.sin(timer / 100) / 4.0), 1.0F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.cooledRifleModel.render(null, 1.0F, reloadValue, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.AQUATICBOW) {
            float pullxxxx = NBTHelper.GetNBTint(Itemstack, "pulling");
            float pullMaxxxxx = ((IWeapon)item).getCooldownTime(Itemstack);
            if (pullxxxx > 0.0F && pullxxxx < pullMaxxxxx) {
               pullxxxx += Minecraft.getMinecraft().getRenderPartialTicks();
            }

            float progress = pullxxxx / pullMaxxxxx;
            progress = MathHelper.clamp(progress * progress, 0.0F, 1.0F);
            int specialAttack = !NBTHelper.GetNBTboolean(Itemstack, "specattack") ? 0 : 1;
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.09F, 0.09F, 0.09F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texAquaticBowModel);
            this.aquaticBowModel.render(null, progress, 0.0F, specialAttack, 0.0F, 0.0F, 1.0F);
            GlStateManager.popMatrix();
            AbstractBow.renderArrowInBow(Itemstack, progress, partialTicks, 0.9F);
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.09F, 0.09F, 0.09F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float fx = timer;
               float f1x = MathHelper.cos(fx * 0.02F) / 2.0F;
               float f2x = fx * 0.01F;
               GlStateManager.translate(f1x, f2x, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f4x = (float)(Math.sin(timer / 100) / 4.0);
               float nonBlueMult = specialAttack <= 0 ? 1.0F : 1.0F - progress;
               GlStateManager.color((0.4F + AnimationTimer.rainbow1 / 34.0F) * nonBlueMult, (0.7F + f4x) * nonBlueMult, 1.0F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.aquaticBowModel.render(null, progress, 1.0F, specialAttack, 0.0F, 0.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.XMASSLAUNCHER) {
            float anim1x = 1.0F - Flicks.instance.getClientAnimation(Itemstack, EnumFlick.SHOOT, partialTicks, false);
            float anim2x = 1.0F - Flicks.instance.getClientAnimation(Itemstack, EnumFlick.ROCKET, partialTicks, false);
            float anim3 = 1.0F - Flicks.instance.getClientAnimation(Itemstack, EnumFlick.RELOAD, partialTicks, false);
            EntityPlayer pl = Minecraft.getMinecraft().player;
            int ammoxx = NBTHelper.GetNBTint(Itemstack, "ammo");
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.09F, 0.09F, 0.09F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texXmassLauncherModel);
            this.xmassLauncherModel.render(null, anim1x, 0.0F, anim3, 1.0F, 1.0F, 1.0F);
            if (ammoxx > 0) {
               GlStateManager.translate(0.0F, -1.5F, -16.0F * GetMOP.getfromto(anim2x, 0.3F, 0.7F));
               float siz = GetMOP.getfromto(anim2x, 0.6F, 1.0F) * 0.8F + 0.2F;
               GlStateManager.scale(siz, siz, siz);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texXmassRocketModel);
               this.xmassRocketModel.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            }

            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.09F, 0.09F, 0.09F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float fx = timer;
               float f1x = MathHelper.cos(fx * 0.02F) / 2.0F;
               float f2x = fx * 0.01F;
               GlStateManager.translate(f1x, f2x, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               int prT = timer % 180;
               float col1 = Math.max(GetMOP.getfromto((float)prT, 0.0F, 60.0F) - GetMOP.getfromto((float)prT, 60.0F, 120.0F), 0.0F);
               float col2 = Math.max(GetMOP.getfromto((float)prT, 60.0F, 120.0F) - GetMOP.getfromto((float)prT, 120.0F, 180.0F), 0.0F);
               float col3 = Math.max(GetMOP.getfromto((float)prT, 120.0F, 180.0F) - GetMOP.getfromto((float)prT, 0.0F, 60.0F), 0.0F);
               GlStateManager.color(
                  0.05F * col1 + 0.8F * col2 + 0.4F * col3, 0.5F * col1 + 0.2F * col2 + 0.8F * col3, 0.25F * col1 + 0.2F * col2 + 1.0F * col3, 1.0F
               );
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.xmassLauncherModel.render(null, anim1x, 1.0F, anim3, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.ADAMANTIUMMINIGUN) {
            float anim1xx = Flicks.instance.getClientAnimation(Itemstack, EnumFlick.SHOOT, partialTicks, true);
            float anim2xx = 1.0F - Flicks.instance.getClientAnimation(Itemstack, EnumFlick.RELOAD, partialTicks, false);
            int heatInt = NBTHelper.GetNBTint(Itemstack, "heat");
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texAdamantiumMinigunModel);
            this.adamantiumMinigunModel.render(null, anim1xx, 0.0F, anim2xx, 1.0F, 0.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               boolean coolingMode = NBTHelper.GetNBTboolean(Itemstack, "coolingMode") && EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.REUSE, Itemstack) > 0;
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float fx = timer;
               float f1x = MathHelper.cos(fx * 0.02F) / 2.0F;
               float f2x;
               if (!coolingMode) {
                  f2x = fx * 0.01F;
                  float f4x = (float)(Math.sin(timer / 100) / 2.0);
                  GlStateManager.color(1.0F - f4x, 0.1F, 0.1F, 1.0F);
               } else {
                  f2x = fx * 0.005F;
                  float f4x = (float)(Math.sin(timer / 100) / 3.0);
                  GlStateManager.color(1.0F - f4x, 0.75F - f4x, 0.85F - f4x, 1.0F);
               }

               GlStateManager.translate(f1x, f2x, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.adamantiumMinigunModel.render(null, anim1xx, 1.0F, anim2xx, 1.0F, 0.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }

            if (heatInt > 70 || heatInt < -30) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texAdamantiumMinigunOverheat);
               GlStateManager.enableBlend();
               float col = heatInt <= 0 ? GetMOP.getfromto((float)(-heatInt), 30.0F, 100.0F) : GetMOP.getfromto((float)heatInt, 70.0F, 120.0F);
               GlStateManager.color(col, col, col, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               this.adamantiumMinigunModel.render(null, anim1xx, 1.0F, anim2xx, 1.0F, 0.0F, 1.0F);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.BUZDYGAN) {
            float anim1xxx = Flicks.instance.getClientAnimation(Itemstack, EnumFlick.SPIN, partialTicks, true);
            float runes = NBTHelper.GetNBTfloat(Itemstack, "runes");
            boolean activex = NBTHelper.GetNBTboolean(Itemstack, "active");
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texBuzdyganModel);
            this.buzdyganModel.render(null, runes, 0.0F, anim1xxx, 1.0F, 1.0F, 1.0F);
            if (activex) {
               float ft1x = GetMOP.getfromto(anim1xxx, 0.0F, 400.0F) - GetMOP.getfromto(anim1xxx, (float)(Buzdygan.maxAngle - 250), (float)Buzdygan.maxAngle);
               GL11.glDisable(2896);
               GlStateManager.enableBlend();
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               float scal = 1.75F;
               GlStateManager.scale(scal, scal, scal);
               GlStateManager.color(0.3F * ft1x, 1.0F * ft1x, 0.6F * ft1x, 1.0F * ft1x);
               GlStateManager.translate(0.0F, -8.0F, -2.1F);
               this.buzdyganModel.render(null, 0.0F, 1.0F, -anim1xxx, 0.0F, 1.0F, 1.0F);
               GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
               GlStateManager.disableBlend();
               GL11.glEnable(2896);
            }

            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float fx = timer;
               float f1x = MathHelper.cos(fx * 0.02F) / 2.0F;
               float f2x = fx * 0.01F;
               GlStateManager.translate(f1x, f2x, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3x = 0.5F;
               GlStateManager.color(0.3F, 1.0F, (float)(0.78F + Math.sin(timer / 100) / 4.0), 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.buzdyganModel.render(null, runes, 1.0F, anim1xxx, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.popMatrix();
            }

            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         }

         if (item == ItemsRegister.WHIP) {
            float anim1xxxx = Flicks.instance.getClientAnimation(Itemstack, EnumFlick.ATTACK, partialTicks, false) * 24.0F;
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.09F, 0.09F, 0.09F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texWhip);
            this.whipModel.shapeOverlay.isHidden = false;
            this.whipModel.render(null, 0.0F, anim1xxxx, 0.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.09F, 0.09F, 0.09F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float fx = timer;
               float f1x = MathHelper.cos(fx * 0.01F) / 2.0F;
               float f2x = fx * 0.005F;
               GlStateManager.translate(f1x, f2x, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               GlStateManager.color(1.0F, 0.0F, 1.0F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.whipModel.shapeGem.isHidden = true;
               this.whipModel.shapeOverlay.isHidden = true;
               this.whipModel.render(null, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.MAULER) {
            float anim1xxxx = Flicks.instance.getClientAnimation(Itemstack, EnumFlick.ATTACK, partialTicks, false) * 24.0F;
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.09F, 0.09F, 0.09F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texMauler);
            this.whipModel.shapeOverlay.isHidden = false;
            this.whipModel.render(null, 0.0F, anim1xxxx, 150.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.09F, 0.09F, 0.09F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch4);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float fx = timer;
               float f1x = MathHelper.cos(fx * 0.01F) / 2.0F;
               float f2x = fx * 0.005F;
               GlStateManager.translate(f1x, f2x, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               GlStateManager.color(1.0F, (float)(0.65F + Math.sin(timer / 100) / 4.0), 0.4F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.whipModel.shapeGem.isHidden = true;
               this.whipModel.shapeOverlay.isHidden = true;
               this.whipModel.render(null, 1.0F, 0.0F, 150.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.SNAKEWHIP) {
            float anim1xxxx = Flicks.instance.getClientAnimation(Itemstack, EnumFlick.ATTACK, partialTicks, false) * 24.0F;
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.09F, 0.09F, 0.09F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texSnakewhip);
            this.whipModel.shapeUp.isHidden = false;
            this.whipModel.shapeOverlay.isHidden = false;
            this.whipModel.render(null, 0.0F, anim1xxxx, 0.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.09F, 0.09F, 0.09F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float fx = timer;
               float f1x = MathHelper.cos(fx * 0.01F) / 2.0F;
               float f2x = fx * 0.005F;
               GlStateManager.translate(f1x, f2x, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               GlStateManager.color(0.3F, (float)(0.75 + Math.sin(timer / 100) / 4.0), 0.4F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.whipModel.shapeGem.isHidden = true;
               this.whipModel.shapeOverlay.isHidden = false;
               this.whipModel.shapeUp.isHidden = true;
               this.whipModel.render(null, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.SPELLROD) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texSpellRodModel);
            this.spellRodModel.render(null, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float fx = timer;
               float f1x = MathHelper.cos(fx * 0.02F) / 2.0F;
               float f2x = fx * 0.01F;
               GlStateManager.translate(f1x, f2x, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3x = 0.5F;
               GlStateManager.color(
                  0.4F + AnimationTimer.rainbow2 / 34.0F, (float)(0.48F + Math.sin(timer / 100) / 4.0), 0.6F + AnimationTimer.rainbow1 / 34.0F, 1.0F
               );
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.spellRodModel.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.CRYODESTROYER) {
            float anim1xxxx = 1.0F - Flicks.instance.getClientAnimation(Itemstack, EnumFlick.SHOOT, partialTicks, false);
            float anim2xxx = 1.0F - Flicks.instance.getClientAnimation(Itemstack, EnumFlick.RELOAD, partialTicks, false);
            float anim3x = Flicks.instance.getClientAnimation(Itemstack, EnumFlick.INFO, partialTicks, true);
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texCryoDestroyerModel);
            this.cryoDestroyerModel.render(null, 0.0F, anim1xxxx, anim2xxx, anim3x, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.1F, 0.1F, 0.1F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float fx = timer;
               float f1x = MathHelper.cos(fx * 0.02F) / 2.0F;
               float f2x = fx * 0.01F;
               GlStateManager.translate(f1x, f2x, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f3x = 0.5F;
               GlStateManager.color(0.2F, (float)(0.62F + Math.sin(timer / 100) / 4.0), 1.0F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.cryoDestroyerModel.render(null, 1.0F, anim1xxxx, anim2xxx, anim3x, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.HYDRAULICSHOTGUN) {
            float anim1xxxx = 0.0F;
            int currentShot = NBTHelper.GetNBTint(Itemstack, "currentshot");
            if (currentShot != 0) {
               anim1xxxx = 1.0F - currentShot / 3.0F;
            } else {
               anim1xxxx = 1.0F - Flicks.instance.getClientAnimation(Itemstack, EnumFlick.COOLDOWN, partialTicks, false);
            }

            float anim3x = 1.0F - Flicks.instance.getClientAnimation(Itemstack, EnumFlick.RELOAD, partialTicks, false);
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.09F, 0.09F, 0.09F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texHydraulicShotgunModel);
            this.hydraulicShotgunModel.render(null, anim1xxxx, 0.0F, anim3x, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            if (Itemstack.isItemEnchanted()) {
               GlStateManager.pushMatrix();
               GlStateManager.scale(0.09F, 0.09F, 0.09F);
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glDisable(2896);
               GlStateManager.depthMask(false);
               Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               float fx = timer;
               float f1x = MathHelper.cos(fx * 0.02F) / 2.0F;
               float f2x = fx * 0.01F;
               GlStateManager.translate(f1x, f2x, 0.0F);
               GlStateManager.matrixMode(5888);
               GlStateManager.enableBlend();
               float f4x = (float)(Math.sin(timer / 100) / 4.0);
               GlStateManager.color(0.4F + AnimationTimer.rainbow1 / 34.0F, 0.7F + f4x, 1.0F, 1.0F);
               GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
               this.hydraulicShotgunModel.render(null, anim1xxxx, 1.0F, anim3x, 1.0F, 1.0F, 1.0F);
               Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
               GlStateManager.matrixMode(5890);
               GlStateManager.loadIdentity();
               GlStateManager.matrixMode(5888);
               GlStateManager.disableBlend();
               GlStateManager.depthMask(true);
               GL11.glEnable(2896);
               GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
               GlStateManager.popMatrix();
            }
         }

         if (item == ItemsRegister.ICECOMPASS) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.09F, 0.09F, 0.09F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texIceCompassModel);
            this.iceCompassModel
               .render(null, GetMOP.partial(IceCompass.arrowRotation, IceCompass.prevArrowRotation, partialTicks), 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
         }
      }
   }

   static {
      for (EnumDyeColor enumdyecolor : EnumDyeColor.values()) {
         SHULKER_BOXES[enumdyecolor.getMetadata()] = new TileEntityShulkerBox(enumdyecolor);
      }

      instance = new TEISRGuns();
   }
}
