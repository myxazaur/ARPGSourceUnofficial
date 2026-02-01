package com.vivern.arpg.main;

import baubles.api.BaubleType;
import com.vivern.arpg.elements.AbstractMiningTool;
import com.vivern.arpg.elements.AcidFire;
import com.vivern.arpg.elements.AdamantiumBattleAxe;
import com.vivern.arpg.elements.AdamantiumKnife;
import com.vivern.arpg.elements.AdamantiumLongsword;
import com.vivern.arpg.elements.AdamantiumMinigun;
import com.vivern.arpg.elements.AdamantiumRevolver;
import com.vivern.arpg.elements.AdamantiumRounds;
import com.vivern.arpg.elements.AimLens;
import com.vivern.arpg.elements.AirborneCirclet;
import com.vivern.arpg.elements.AnnihilationGun;
import com.vivern.arpg.elements.AntiRadInjector;
import com.vivern.arpg.elements.Antidote;
import com.vivern.arpg.elements.AntimatterCharge;
import com.vivern.arpg.elements.Antipotion;
import com.vivern.arpg.elements.AquaticBow;
import com.vivern.arpg.elements.AquaticInstancer;
import com.vivern.arpg.elements.Arrows;
import com.vivern.arpg.elements.AzureOreStaff;
import com.vivern.arpg.elements.BaubleAntipotion;
import com.vivern.arpg.elements.BaublesPack;
import com.vivern.arpg.elements.Beaker;
import com.vivern.arpg.elements.Bilebiter;
import com.vivern.arpg.elements.Blowhole;
import com.vivern.arpg.elements.BogFlower;
import com.vivern.arpg.elements.BouncingRing;
import com.vivern.arpg.elements.BubbleFish;
import com.vivern.arpg.elements.Buckshot;
import com.vivern.arpg.elements.BurningFrostIgniter;
import com.vivern.arpg.elements.Butterfly;
import com.vivern.arpg.elements.Buzdygan;
import com.vivern.arpg.elements.Canister;
import com.vivern.arpg.elements.Carapace;
import com.vivern.arpg.elements.Ceratarget;
import com.vivern.arpg.elements.ChainDagger;
import com.vivern.arpg.elements.ChainMace;
import com.vivern.arpg.elements.Charger;
import com.vivern.arpg.elements.CinderBow;
import com.vivern.arpg.elements.Coin;
import com.vivern.arpg.elements.CompoundBow;
import com.vivern.arpg.elements.ConiferRod;
import com.vivern.arpg.elements.CooledRifle;
import com.vivern.arpg.elements.CoralRifle;
import com.vivern.arpg.elements.CreativeTeamSelector;
import com.vivern.arpg.elements.CryoDestroyer;
import com.vivern.arpg.elements.CryoGun;
import com.vivern.arpg.elements.CrystalCutter;
import com.vivern.arpg.elements.CrystalFan;
import com.vivern.arpg.elements.CrystalStar;
import com.vivern.arpg.elements.CursedBlade;
import com.vivern.arpg.elements.DashBelt;
import com.vivern.arpg.elements.DeadRampart;
import com.vivern.arpg.elements.DemonicIgniter;
import com.vivern.arpg.elements.DragonShell;
import com.vivern.arpg.elements.DragonTail;
import com.vivern.arpg.elements.ElAmmoAir;
import com.vivern.arpg.elements.ElAmmoEarth;
import com.vivern.arpg.elements.ElAmmoFire;
import com.vivern.arpg.elements.ElAmmoWater;
import com.vivern.arpg.elements.ElectricAcidRadiationPotion;
import com.vivern.arpg.elements.ElectricStaff;
import com.vivern.arpg.elements.ElementFocus;
import com.vivern.arpg.elements.ElementsBook;
import com.vivern.arpg.elements.EmeraldEye;
import com.vivern.arpg.elements.EnderGraplingHook;
import com.vivern.arpg.elements.EnderInstancer;
import com.vivern.arpg.elements.EnderProtector;
import com.vivern.arpg.elements.EtherSign;
import com.vivern.arpg.elements.ExpItem;
import com.vivern.arpg.elements.FairyWings;
import com.vivern.arpg.elements.FiberBandage;
import com.vivern.arpg.elements.FinWings;
import com.vivern.arpg.elements.FireWhip;
import com.vivern.arpg.elements.FireballStaff;
import com.vivern.arpg.elements.FireworkDragonRocket;
import com.vivern.arpg.elements.FireworkLauncher;
import com.vivern.arpg.elements.FireworkPack;
import com.vivern.arpg.elements.FrostBoltStaff;
import com.vivern.arpg.elements.FrozenWings;
import com.vivern.arpg.elements.GeigerCounter;
import com.vivern.arpg.elements.GemStaff;
import com.vivern.arpg.elements.GhostSword;
import com.vivern.arpg.elements.GlacideBlade;
import com.vivern.arpg.elements.GlowBlade;
import com.vivern.arpg.elements.GothicBow;
import com.vivern.arpg.elements.GothicSword;
import com.vivern.arpg.elements.GraplingHook;
import com.vivern.arpg.elements.GraveLurker;
import com.vivern.arpg.elements.GrenadeLauncher;
import com.vivern.arpg.elements.HadronBlaster;
import com.vivern.arpg.elements.HailTear;
import com.vivern.arpg.elements.HeadShooter;
import com.vivern.arpg.elements.HealthFruit;
import com.vivern.arpg.elements.HealthfulCapsule;
import com.vivern.arpg.elements.Hellmark;
import com.vivern.arpg.elements.HolographicShield;
import com.vivern.arpg.elements.HolyShotgun;
import com.vivern.arpg.elements.HydraulicShotgun;
import com.vivern.arpg.elements.IceBeam;
import com.vivern.arpg.elements.IceCompass;
import com.vivern.arpg.elements.IceSword;
import com.vivern.arpg.elements.IchorShower;
import com.vivern.arpg.elements.IcicleMinigun;
import com.vivern.arpg.elements.Impetus;
import com.vivern.arpg.elements.InfernalBlade;
import com.vivern.arpg.elements.InkBottle;
import com.vivern.arpg.elements.Instancer;
import com.vivern.arpg.elements.InstantEnchantmentBook;
import com.vivern.arpg.elements.ItemAccumulator;
import com.vivern.arpg.elements.ItemAmmo;
import com.vivern.arpg.elements.ItemAmmoClip;
import com.vivern.arpg.elements.ItemBasicTool;
import com.vivern.arpg.elements.ItemBuckshotClip;
import com.vivern.arpg.elements.ItemBullet;
import com.vivern.arpg.elements.ItemCalibrationThing;
import com.vivern.arpg.elements.ItemEatable;
import com.vivern.arpg.elements.ItemFirst;
import com.vivern.arpg.elements.ItemFishingRod;
import com.vivern.arpg.elements.ItemGeomanticCrystal;
import com.vivern.arpg.elements.ItemGrenade;
import com.vivern.arpg.elements.ItemItem;
import com.vivern.arpg.elements.ItemLootCase;
import com.vivern.arpg.elements.ItemMagicScroll;
import com.vivern.arpg.elements.ItemMobEgg;
import com.vivern.arpg.elements.ItemNoGravivy;
import com.vivern.arpg.elements.ItemRocket;
import com.vivern.arpg.elements.ItemShell;
import com.vivern.arpg.elements.ItemSpellRoll;
import com.vivern.arpg.elements.ItemTurret;
import com.vivern.arpg.elements.ItemVial;
import com.vivern.arpg.elements.ItemWeatherRocket;
import com.vivern.arpg.elements.JungleGraplingHook;
import com.vivern.arpg.elements.Key;
import com.vivern.arpg.elements.LaserPistol;
import com.vivern.arpg.elements.LaserRifle;
import com.vivern.arpg.elements.LaserSniper;
import com.vivern.arpg.elements.LavaDropper;
import com.vivern.arpg.elements.LightningHook;
import com.vivern.arpg.elements.Locker;
import com.vivern.arpg.elements.MagicBoomerang;
import com.vivern.arpg.elements.MagicRocket;
import com.vivern.arpg.elements.MagmaBloomSeed;
import com.vivern.arpg.elements.ManaExpansionPotion;
import com.vivern.arpg.elements.Mauler;
import com.vivern.arpg.elements.MilitaryInstancer;
import com.vivern.arpg.elements.MiningTools;
import com.vivern.arpg.elements.MithrilBow;
import com.vivern.arpg.elements.MoltenGreataxe;
import com.vivern.arpg.elements.NailGun;
import com.vivern.arpg.elements.NetherGrinder;
import com.vivern.arpg.elements.OrbOfDestroy;
import com.vivern.arpg.elements.PhoenixGhostCape;
import com.vivern.arpg.elements.PistolFish;
import com.vivern.arpg.elements.PlasmaAccelerator;
import com.vivern.arpg.elements.PlasmaMinigun;
import com.vivern.arpg.elements.PlasmaPistol;
import com.vivern.arpg.elements.PlasmaRailgun;
import com.vivern.arpg.elements.PlasmaRifle;
import com.vivern.arpg.elements.PumpShotgun;
import com.vivern.arpg.elements.QuadrocopterBelt;
import com.vivern.arpg.elements.Reaper;
import com.vivern.arpg.elements.RestlessSkull;
import com.vivern.arpg.elements.RingOfProtection;
import com.vivern.arpg.elements.RocketLauncher;
import com.vivern.arpg.elements.RopeGraplingHook;
import com.vivern.arpg.elements.RottenShield;
import com.vivern.arpg.elements.SacrificialDagger;
import com.vivern.arpg.elements.SapphireEye;
import com.vivern.arpg.elements.ScepterOfSands;
import com.vivern.arpg.elements.SeaEffloresce;
import com.vivern.arpg.elements.ShadowWings;
import com.vivern.arpg.elements.SharkAmmo;
import com.vivern.arpg.elements.SharkCannon;
import com.vivern.arpg.elements.SkullCrasher;
import com.vivern.arpg.elements.SlimeGraplingHook;
import com.vivern.arpg.elements.SlimeLocator;
import com.vivern.arpg.elements.SlimeShotgun;
import com.vivern.arpg.elements.Snakewhip;
import com.vivern.arpg.elements.Snapball;
import com.vivern.arpg.elements.SnowballCannon;
import com.vivern.arpg.elements.SnowflakeShuriken;
import com.vivern.arpg.elements.SnowstormStaff;
import com.vivern.arpg.elements.SoulCharm;
import com.vivern.arpg.elements.SoulStone;
import com.vivern.arpg.elements.SparklingNecklace;
import com.vivern.arpg.elements.SpellHammer;
import com.vivern.arpg.elements.SpellPliers;
import com.vivern.arpg.elements.SpellRod;
import com.vivern.arpg.elements.SpikeRing;
import com.vivern.arpg.elements.StaffOfCorpse;
import com.vivern.arpg.elements.StaffOfWitherdry;
import com.vivern.arpg.elements.StaticLance;
import com.vivern.arpg.elements.Stinger;
import com.vivern.arpg.elements.StingerBolts;
import com.vivern.arpg.elements.StingingCell;
import com.vivern.arpg.elements.StormSpanner;
import com.vivern.arpg.elements.Submachine;
import com.vivern.arpg.elements.Sunrise;
import com.vivern.arpg.elements.Swarmeter;
import com.vivern.arpg.elements.TheLordOfPain;
import com.vivern.arpg.elements.ThistleThorn;
import com.vivern.arpg.elements.ThunderbirdWings;
import com.vivern.arpg.elements.TimelessSword;
import com.vivern.arpg.elements.ToolWand;
import com.vivern.arpg.elements.ToxiCola;
import com.vivern.arpg.elements.ToxicNuclearCannon;
import com.vivern.arpg.elements.ToxicWings;
import com.vivern.arpg.elements.ToxiniumShield;
import com.vivern.arpg.elements.ToxiniumShotgun;
import com.vivern.arpg.elements.VacuumGun;
import com.vivern.arpg.elements.VampireKnife;
import com.vivern.arpg.elements.VampireKnifes;
import com.vivern.arpg.elements.VampiricHeart;
import com.vivern.arpg.elements.VialOfPoison;
import com.vivern.arpg.elements.ViciousEmblem;
import com.vivern.arpg.elements.Violence;
import com.vivern.arpg.elements.VirulentRod;
import com.vivern.arpg.elements.Voltrident;
import com.vivern.arpg.elements.VortexInABottle;
import com.vivern.arpg.elements.WandOfBlazes;
import com.vivern.arpg.elements.WandOfCold;
import com.vivern.arpg.elements.WebGraplingHook;
import com.vivern.arpg.elements.Whip;
import com.vivern.arpg.elements.WhispersBlade;
import com.vivern.arpg.elements.WinterBreath;
import com.vivern.arpg.elements.WinterInstancer;
import com.vivern.arpg.elements.WinterSacrifice;
import com.vivern.arpg.elements.WoodenSkiing;
import com.vivern.arpg.elements.WorshippersBait;
import com.vivern.arpg.elements.Wrench;
import com.vivern.arpg.elements.XmassLauncher;
import com.vivern.arpg.elements.armor.Armors;
import com.vivern.arpg.elements.armor.BoneHelm;
import com.vivern.arpg.elements.armor.JungleBoots;
import com.vivern.arpg.elements.armor.JungleChestplate;
import com.vivern.arpg.elements.armor.JungleHelm;
import com.vivern.arpg.elements.armor.JungleLeggins;
import com.vivern.arpg.elements.armor.LichHelm;
import com.vivern.arpg.potions.PotionEffects;
import com.vivern.arpg.renders.TEISRGuns;
import com.vivern.arpg.renders.TEISROther;
import com.vivern.arpg.tileentity.ChestLock;
import java.lang.reflect.Field;
import java.util.HashSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ItemsRegister {
   public static HashSet<Item> forrender = new HashSet<>();
   public static ArmorMaterial null_material = EnumHelper.addArmorMaterial(
      "arpg:null_mt", "arpg:null_tx", 0, new int[]{0, 0, 0, 0}, 0, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F
   );
   public static Item FIRST = new ItemFirst();
   public static Item ICHSHOWER = new IchorShower();
   public static Item SHARKCANN = new SharkCannon();
   public static Item SHARKAMMO = new SharkAmmo();
   public static Item MAGICBOOMERANG = new MagicBoomerang();
   public static Item BUTTERFLY = new Butterfly();
   public static Item SUNRISE = new Sunrise();
   public static Item LASERSNIPER = new LaserSniper();
   public static Item IONBATTERY = new ItemAccumulator("ion_battery", ItemAccumulator.REDSTONE_ION_CAPACITY, ItemAccumulator.REDSTONE_ION_THROUGHPUT);
   public static Item LASERPISTOL = new LaserPistol();
   public static Item LASERRIFLE = new LaserRifle();
   public static Item VAMPIREKNIFE = new VampireKnife();
   public static Item VAMPIREKNIFESLAUN = new VampireKnifes();
   public static Item FROSTBOLT = new FrostBoltStaff();
   public static Item ANIHGUN = new AnnihilationGun();
   public static Item ANTICHARGE = new AntimatterCharge();
   public static Item ELAMMOFIRE = new ElAmmoFire();
   public static Item ELAMMOWATER = new ElAmmoWater();
   public static Item ELAMMOAIR = new ElAmmoAir();
   public static Item ELAMMOEARTH = new ElAmmoEarth();
   public static Item ELEMENTFOCUS = new ElementFocus();
   public static Item STINGER = new Stinger();
   public static Item STINGERBOLTS = new StingerBolts();
   public static Item FIREWORKSLAUN = new FireworkLauncher();
   public static Item FIREWORKPACK = new FireworkPack();
   public static Item FIREWORKDRAGON = new FireworkDragonRocket();
   public static Item QUADROBELT = new QuadrocopterBelt();
   public static Item VORTEXBOTTLE = new VortexInABottle();
   public static Item ETHERSIGN = new EtherSign();
   public static Item PHOENIXGHOSTCAPE = new PhoenixGhostCape();
   public static Item SCEPTEROFSANDS = new ScepterOfSands();
   public static Item BILEBITER = new Bilebiter();
   public static Item BILEBITERAMMO = new ItemAmmo(CreativeTabs.COMBAT, "bilebiter_sphere", 64, 1);
   public static Item EARPOTION = new ElectricAcidRadiationPotion();
   public static Item FIREBALLSTAFF = new FireballStaff();
   public static Item SNOWSTORMSTAFF = new SnowstormStaff();
   public static Item ELECTRICSTAFF = new ElectricStaff();
   public static Item SLIMESHOTGUN = new SlimeShotgun();
   public static Item WIZARDHELM = Armors.wizardSET.HELMET;
   public static Item WIZARDCHEST = Armors.wizardSET.CHESTPLATE;
   public static Item WIZARDLEGS = Armors.wizardSET.LEGGINS;
   public static Item WIZARDBOOTS = Armors.wizardSET.BOOTS;
   public static Item FIREMAGEHELM = Armors.firemageSET.HELMET;
   public static Item FIREMAGECHEST = Armors.firemageSET.CHESTPLATE;
   public static Item FIREMAGELEGS = Armors.firemageSET.LEGGINS;
   public static Item FIREMAGEBOOTS = Armors.firemageSET.BOOTS;
   public static Item FIRELORDHELM = Armors.firelordSET.HELMET;
   public static Item FIRELORDCHEST = Armors.firelordSET.CHESTPLATE;
   public static Item FIRELORDLEGS = Armors.firelordSET.LEGGINS;
   public static Item FIRELORDBOOTS = Armors.firelordSET.BOOTS;
   public static Item WANDOFBLAZES = new WandOfBlazes();
   public static Item GRAPLINGHOOK = ((Item)new GraplingHook().setRegistryName("grapling_hook")).setTranslationKey("grapling_hook");
   public static Item JUNGLEGH = new JungleGraplingHook();
   public static Item SEASHELL = new ItemShell();
   public static Item SLIMEGH = new SlimeGraplingHook();
   public static Item ENDERGH = new EnderGraplingHook();
   public static Item MAINFISHINGROD = ((Item)new ItemFishingRod().setRegistryName("fishing_rod")).setTranslationKey("fishing_rod");
   public static Item MOLTENGREATAXE = new MoltenGreataxe();
   public static Item ICESWORD = new IceSword();
   public static Item NETHERGRINDER = new NetherGrinder();
   public static Item NETHERGRINDERAMMO = new ItemAmmoClip("nether_grinder_ammo", CreativeTabs.COMBAT, 64, 35);
   public static Item SNOWBALLCANNON = new SnowballCannon();
   public static Item SNOWFLAKESHUR = new SnowflakeShuriken();
   public static Item CURSEDBLADE = new CursedBlade();
   public static Item GRAVELURKER = new GraveLurker();
   public static Item REAPER = new Reaper();
   public static Item GHOSTSWORD = new GhostSword();
   public static Item CORPSESTAFF = new StaffOfCorpse();
   public static Item CHAINDAGGER = new ChainDagger();
   public static Item DEADRAMPART = new DeadRampart();
   public static Item TOXICNUKECANNON = new ToxicNuclearCannon();
   public static Item TOXICNUCLEARWARHEAD = new ItemAmmo(CreativeTabs.COMBAT, "toxic_nuclear_warhead", 64, 1);
   public static Item CRYOGUN = new CryoGun();
   public static Item CRYOGENCELL = new ItemAmmo(CreativeTabs.COMBAT, "cryogen_cell", 64, 1);
   public static Item EMPTYCELL = new ItemAmmo(CreativeTabs.MATERIALS, "empty_cell", 64, 1);
   public static Item CHARGER = new Charger();
   public static Item LIGHTNINGGH = new LightningHook();
   public static Item ICICLEMINIGUN = new IcicleMinigun();
   public static Item STATICLANCE = new StaticLance();
   public static Item RINGOFPROTECTION = new RingOfProtection();
   public static Item SPIKERING = new SpikeRing();
   public static Item SPARKLINGNECKLACE = new SparklingNecklace();
   public static Item EXP = new ExpItem();
   public static Item DEMONICIGNITER = new DemonicIgniter();
   public static Item BOUNCINGRING = new BouncingRing();
   public static Item SLIMEHELM = Armors.slimeSET.HELMET;
   public static Item SLIMECHEST = Armors.slimeSET.CHESTPLATE;
   public static Item SLIMELEGS = Armors.slimeSET.LEGGINS;
   public static Item SLIMEBOOTS = Armors.slimeSET.BOOTS;
   public static Item ICEHELM = Armors.iceSET.HELMET;
   public static Item ICECHEST = Armors.iceSET.CHESTPLATE;
   public static Item ICELEGS = Armors.iceSET.LEGGINS;
   public static Item ICEBOOTS = Armors.iceSET.BOOTS;
   public static Item SOULCHARM = new SoulCharm();
   public static Item CONIFERROD = new ConiferRod();
   public static Item JUNGLEHELM = new JungleHelm();
   public static Item JUNGLECHEST = new JungleChestplate();
   public static Item JUNGLELEGS = new JungleLeggins();
   public static Item JUNGLEBOOTS = new JungleBoots();
   public static Item WEBGH = new WebGraplingHook();
   public static Item ROPEGH = new RopeGraplingHook();
   public static Item PLASMARAILGUN = new PlasmaRailgun();
   public static Item PLASMARAILGUNBOLTS = new ItemAmmo(CreativeTabs.COMBAT, "plasma_railgun_bolts", 64, 1);
   public static Item PLASMARIFLE = new PlasmaRifle();
   public static Item HOLOSHIELD = new HolographicShield();
   public static Item HEADSHOOTER = new HeadShooter();
   public static Item PLASMAPISTOL = new PlasmaPistol();
   public static Item AIMLENS = new AimLens();
   public static Item BOGFLOWER = new BogFlower();
   public static Item FISHFEED = new ItemAmmo(CreativeTabs.COMBAT, "fish_feed", 64, 1);
   public static Item PISTOLFISH = new PistolFish();
   public static Item BUBBLEFISH = new BubbleFish();
   public static Item VICIOUSEMBLEM = new ViciousEmblem();
   public static Item ORBOFDESTROY = new OrbOfDestroy();
   public static Item WOODENSKIING = new WoodenSkiing();
   public static Item VIALOFPOISON = new VialOfPoison();
   public static Item VAMPIRICHEART = new VampiricHeart();
   public static Item LAVADROPPER = new LavaDropper();
   public static Item PLASMAACCELERATOR = new PlasmaAccelerator();
   public static Item VACUUMGUN = new VacuumGun();
   public static Item GEMSTAFF = new GemStaff();
   public static Item WANDOFCOLD = new WandOfCold();
   public static Item ICEBEAM = new IceBeam();
   public static Item FROZENWINGS = new FrozenWings();
   public static Item TOXICWINGS = new ToxicWings();
   public static Item FAIRYWINGS = new FairyWings();
   public static Item ICEFLOWERSEEDS = BlocksRegister.ICEFLOWER.seed;
   public static Item CRIMBERRYSEEDS = BlocksRegister.CRIMBERRY.seed;
   public static Item WINTERWILLOWSEEDS = BlocksRegister.WINTERWILLOW.seed;
   public static Item GLACIDEBLADE = new GlacideBlade();
   public static Item INFERNALBLADE = new InfernalBlade();
   public static Item CINDERBOW = new CinderBow();
   public static Item SKULLCRASHER = new SkullCrasher();
   public static Item SPELLHAMMER = new SpellHammer();
   public static Item INGOTMOLTEN = new ItemItem("molten_ingot", CreativeTabs.MATERIALS, 0, 64);
   public static Item INGOTINFERNUM = new ItemItem("infernum_ingot", CreativeTabs.MATERIALS, 0, 64);
   public static Item NUGGETMOLTEN = new ItemItem("molten_nugget", CreativeTabs.MATERIALS, 0, 64);
   public static Item NUGGETINFERNUM = new ItemItem("infernum_nugget", CreativeTabs.MATERIALS, 0, 64);
   public static Item MOLTENSTRING = new ItemItem("molten_string", CreativeTabs.MATERIALS, 0, 64);
   public static Item LIQUIDFIRE = new ItemItem("liquid_fire", CreativeTabs.MATERIALS, 0, 64).setFuel(200);
   public static Item DEMONITE = new ItemItem("demonite", CreativeTabs.MATERIALS, 0, 64);
   public static Item DEMONITESHARD = new ItemItem("demonite_shard", CreativeTabs.MATERIALS, 0, 64);
   public static Item RUBY = new ItemItem("ruby", CreativeTabs.MATERIALS, 0, 64).setBeacon();
   public static Item SAPPHIRE = new ItemItem("sapphire", CreativeTabs.MATERIALS, 0, 64).setBeacon();
   public static Item CITRINE = new ItemItem("citrine", CreativeTabs.MATERIALS, 0, 64).setBeacon();
   public static Item AMETHYST = new ItemItem("amethyst", CreativeTabs.MATERIALS, 0, 64).setBeacon();
   public static Item TOPAZ = new ItemItem("topaz", CreativeTabs.MATERIALS, 0, 64).setBeacon();
   public static Item RHINESTONE = new ItemItem("rhinestone", CreativeTabs.MATERIALS, 0, 64).setBeacon();
   public static Item MAGIC_POWDER = new ItemItem("magic_powder", CreativeTabs.MATERIALS, 0, 64).setEnchantGlow();
   public static Item ICEGEM = new ItemItem("ice_gem", CreativeTabs.MATERIALS, 0, 64);
   public static Item WEATHERFRAGMENTS = new ItemItem("weather_fragments", CreativeTabs.MATERIALS, 0, 64);
   public static Item SNOWCLOTH = new ItemItem("snow_cloth", CreativeTabs.MATERIALS, 0, 64);
   public static Item CONIFERSTICK = new ItemItem("conifer_stick", CreativeTabs.MATERIALS, 0, 64);
   public static Item SOULSTONE = new SoulStone();
   public static Item SAPPHIREEYE = new SapphireEye();
   public static Item ENCHANTMENTBOOKINS = new InstantEnchantmentBook();
   public static Item ACIDFIRE = new AcidFire();
   public static Item GLOWBLADE = new GlowBlade();
   public static Item NAILGUN = new NailGun();
   public static Item CRYSTALSTAR = new CrystalStar();
   public static Item THELORDOFPAIN = new TheLordOfPain();
   public static Item VOLTRIDENT = new Voltrident();
   public static Item CRYSTALFAN = new CrystalFan();
   public static Item TOXIBERRYARCANOSEEDS = BlocksRegister.TOXIBERRYARCANO.seed;
   public static Item TOXIBERRYVIBRANTSEEDS = BlocksRegister.TOXIBERRYVIBRANT.seed;
   public static Item MOSSPLANTSEEDS = BlocksRegister.MOSSPLANT.seed;
   public static Item CONTEMPLANTSEEDS = BlocksRegister.CONTEMPLANT.seed;
   public static Item MUCOPHILLUSSEEDS = BlocksRegister.MUCOPHILLUS.seed;
   public static Item MUCOPHILLUSBROWNSEEDS = BlocksRegister.MUCOPHILLUSBROWN.seed;
   public static Item VISCOSASEEDS = BlocksRegister.VISCOSA.seed;
   public static Item TOXIBERRYWEEPINGSEEDS = BlocksRegister.TOXIBERRYWEEPING.seed;
   public static Item TOXINIASEEDS = BlocksRegister.TOXINIA.seed;
   public static Item ARELIASEEDS = BlocksRegister.ARELIA.seed;
   public static Item DECEIDUSSEEDS = BlocksRegister.DECEIDUS.seed;
   public static Item JUNKWEEDSEEDS = BlocksRegister.JUNKWEED.seed;
   public static Item TOXEDGESEEDS = BlocksRegister.TOXEDGE.seed;
   public static Item TOXIBULBSEEDS = BlocksRegister.TOXIBULB.seed;
   public static Item ANTIDOTE = new Antidote();
   public static Item TOXICOLA = new ToxiCola();
   public static Item ANTIPOTION = new Antipotion();
   public static Item WASTEBURGER = new ItemEatable(
      "wasteburger",
      0,
      64,
      9,
      1.0F,
      false,
      32,
      new PotionEffect[]{new PotionEffect(MobEffects.POISON, 500), new PotionEffect(MobEffects.STRENGTH, 500)},
      new float[]{0.9F, 1.0F},
      false,
      50
   );
   public static Item TOXIBERRYMOJITO = new ItemEatable(
         "toxiberry_mojito",
         0,
         64,
         1,
         0.0F,
         false,
         25,
         new PotionEffect[]{new PotionEffect(PotionEffects.TOXIN, 50), new PotionEffect(MobEffects.SPEED, 850)},
         new float[]{0.8F, 1.0F},
         true,
         40
      )
      .setStackToReturn(new ItemStack(Items.GLASS_BOTTLE))
      .setAlwaysEdible();
   public static Item TOXEDGEBREAD = new ItemEatable(
      "toxedge_bread", 0, 64, 3, 0.5F, false, 32, new PotionEffect[]{new PotionEffect(MobEffects.POISON, 50)}, new float[]{0.1F}, false, 20
   );
   public static Item DECEIDUSJUICE = new ItemEatable(
         "deceidus_juice",
         0,
         64,
         2,
         0.0F,
         false,
         20,
         new PotionEffect[]{
            new PotionEffect(PotionEffects.TOXIN, 50), new PotionEffect(PotionEffects.MAGIC_POWER, 550), new PotionEffect(MobEffects.SPEED, 250, 2)
         },
         new float[]{0.25F, 1.0F, 0.97F},
         true,
         30
      )
      .setAlwaysEdible();
   public static Item BROWNSLIMEWAND = new ToolWand(
         "brown_slime_wand", CreativeTabs.TOOLS, 1000, new ItemStack(Blocks.SLIME_BLOCK), true, "arpg:brown_slime"
      )
      .setReplaceLogic("minecraft:slime", "arpg:brown_slime");
   public static Item SLIMEBLOBWAND = new ToolWand(
      "slime_blob_wand", CreativeTabs.TOOLS, 1000, new ItemStack(Items.SLIME_BALL), true, "arpg:slime_blob"
   );
   public static Item BONESWAND = new ToolWand("bones_wand", CreativeTabs.TOOLS, 500, new ItemStack(Items.BONE, 2), true, "arpg:bones_pile");
   public static Item GLOWINGTOXIBERRY = new ItemEatable(
         "glowing_toxiberry",
         0,
         64,
         3,
         0.2F,
         false,
         20,
         new PotionEffect[]{new PotionEffect(PotionEffects.TOXIN, 700), new PotionEffect(MobEffects.GLOWING, 700)},
         new float[]{0.99F, 0.99F},
         false,
         15
      )
      .setAlwaysEdible();
   public static Item SMALLTOXIBERRY = new ItemEatable(
         "small_toxiberry",
         0,
         64,
         1,
         0.0F,
         false,
         10,
         new PotionEffect[]{new PotionEffect(PotionEffects.TOXIN, 100), new PotionEffect(MobEffects.POISON, 100)},
         new float[]{0.99F, 0.99F},
         false,
         25
      )
      .setAlwaysEdible();
   public static Item TOXINIUMHELM = Armors.toxiniumSET.HELMET;
   public static Item TOXINIUMCHEST = Armors.toxiniumSET.CHESTPLATE;
   public static Item TOXINIUMLEGS = Armors.toxiniumSET.LEGGINS;
   public static Item TOXINIUMBOOTS = Armors.toxiniumSET.BOOTS;
   public static Item COIN = new Coin();
   public static Item TOXINIUMSHOTGUN = new ToxiniumShotgun();
   public static Item LOCKER = new Locker();
   public static Item SUBMACHINE = new Submachine();
   public static Item SUBMACHINECLIP = new ItemAmmoClip("submachine_clip", CreativeTabs.COMBAT, 64, 40);
   public static Item BULLETFROZEN = new ItemBullet.BulletFrozen();
   public static Item BULLETCOPPER = new ItemBullet("bullet_copper", "copper", 64, 0.0F, 0.0F, 0.9F, 0.5F, 0.3F);
   public static Item BULLETLEAD = new ItemBullet("bullet_lead", "lead", 64, 1.0F, 0.2F, 0.7F, 0.7F, 0.8F);
   public static Item BULLETSILVER = new ItemBullet.BulletSilver();
   public static Item BULLETGOLD = new ItemBullet("bullet_gold", "gold", 64, 2.5F, 0.4F, 0.9F, 0.7F, 0.2F);
   public static Item BULLETFIRE = new ItemBullet.BulletIncendiary();
   public static Item TOXINIUMSHOTGUNCLIP = new ItemAmmoClip("toxinium_shotgun_clip", CreativeTabs.COMBAT, 64, 32);
   public static Item BULLETTHUNDER = new ItemBullet.BulletThunder();
   public static Item BULLETPOISON = new ItemBullet.BulletPoisonous();
   public static Item BULLETTOXIC = new ItemBullet.BulletToxic();
   public static Item BULLETCRYSTAL = new ItemBullet.BulletCrystal();
   public static Item BULLETEXPLODING = new ItemBullet.BulletExploding();
   public static Item BULLETFESTIVAL = new ItemBullet.BulletFestival();
   public static Item BULLETNIVEOUS = new ItemBullet.BulletNiveous();
   public static Item BULLETERRATIC = new ItemBullet.BulletErratic();
   public static Item BULLETCORAL = new ItemBullet.BulletCoral();
   public static Item BULLETDIVERS = new ItemBullet.BulletDiving();
   public static Item EMERALDEYE = new EmeraldEye();
   public static Item LOOTBOXENCHANTWEAPON = new ItemLootCase("weapon_enchantments_box", CreativeTabs.MISC, 16, ItemLootCase.EMPTY_LIST);
   public static Item LOOTBOXENCHANTSIMPLE = new ItemLootCase("simple_enchantments_box", CreativeTabs.MISC, 16, ItemLootCase.EMPTY_LIST);
   public static Item LOOTBOXENCHANTALL = new ItemLootCase("all_enchantments_box", CreativeTabs.MISC, 16, ItemLootCase.EMPTY_LIST);
   public static Item GEOMANTICCRYSTAL = new ItemGeomanticCrystal();
   public static Item INGOTMANGANESE = new ItemItem("manganese_ingot", CreativeTabs.MATERIALS, 0, 64);
   public static Item DUSTMANGANESE = new ItemItem("manganese_dust", CreativeTabs.MATERIALS, 0, 64);
   public static Item INGOTBERYLLIUM = new ItemItem("beryllium_ingot", CreativeTabs.MATERIALS, 0, 64);
   public static Item DUSTBERYLLIUM = new ItemItem("beryllium_dust", CreativeTabs.MATERIALS, 0, 64);
   public static Item INGOTCHROMIUM = new ItemItem("chromium_ingot", CreativeTabs.MATERIALS, 0, 64);
   public static Item DUSTCHROMIUM = new ItemItem("chromium_dust", CreativeTabs.MATERIALS, 0, 64);
   public static Item AIRBORNECIRCLET = new AirborneCirclet();
   public static Item IMPETUS = new Impetus();
   public static Item GIFT = new ItemItem("gift", CreativeTabs.MATERIALS, 0, 64);
   public static Item CANDYAPPLE = new ItemEatable("candy_apple", 0, 64, 5, 0.3F, false, 32, null, null, false, 0);
   public static Item CANDYCANE = new ItemEatable("candy_cane", 0, 64, 3, 0.1F, false, 13, null, null, false, 0);
   public static Item CRIMBERRYWINE = new ItemEatable(
         "crimberry_wine", 0, 64, 2, 0.2F, false, 32, new PotionEffect[]{new PotionEffect(MobEffects.RESISTANCE, 400)}, new float[]{1.0F}, true, 0
      )
      .setStackToReturn(new ItemStack(Items.GLASS_BOTTLE))
      .setAlwaysEdible();
   public static Item BODYWARMER = new BaubleAntipotion(
      "body_warmer", CreativeTabs.COMBAT, PotionEffects.FREEZING, BaubleType.TRINKET, "Immunity to Freezing"
   );
   public static Item DEVOURERSTEETH = new BaubleAntipotion(
      "devourers_teeth", CreativeTabs.COMBAT, MobEffects.HUNGER, BaubleType.HEAD, "Immunity to Hunger potion effect"
   );
   public static Item ENDERLEECH = new BaubleAntipotion(
      "ender_leech", CreativeTabs.COMBAT, PotionEffects.ENDER_POISON, BaubleType.TRINKET, "Immunity to Ender Poison"
   );
   public static Item ENERGYDRINK = new BaubleAntipotion(
      "gaseous_energy_drink", CreativeTabs.COMBAT, MobEffects.WEAKNESS, BaubleType.TRINKET, "Immunity to Weakness"
   );
   public static Item CONTACTLENSES = new BaubleAntipotion(
      "magic_contact_lenses", CreativeTabs.COMBAT, MobEffects.BLINDNESS, BaubleType.HEAD, "Immunity to Blidness"
   );
   public static Item MINERSGLOVE = new BaubleAntipotion(
      "miners_glove", CreativeTabs.COMBAT, MobEffects.MINING_FATIGUE, BaubleType.RING, "Immunity to Mining Fatigue"
   );
   public static Item RUNNERSOCKS = new BaubleAntipotion(
      "runners_socks", CreativeTabs.COMBAT, MobEffects.SLOWNESS, BaubleType.TRINKET, "Immunity to Slowness"
   );
   public static Item SLIMEEATER = new BaubleAntipotion(
         "slime_eater", CreativeTabs.COMBAT, PotionEffects.SLIME, BaubleType.TRINKET, "Immunity to Slime potion effect"
      )
      .setRender(1);
   public static Item FIREEATER = new BaubleAntipotion(
         "fire_eater", CreativeTabs.COMBAT, PotionEffects.FIERYOIL, BaubleType.TRINKET, "Immunity to Fiery Oil"
      )
      .setRender(1);
   public static Item SLIMEDEVOURER = new BaubleAntipotion(
      "slime_devourer",
      CreativeTabs.COMBAT,
      new Potion[]{PotionEffects.SLIME, MobEffects.HUNGER},
      BaubleType.TRINKET,
      false,
      new String[]{"Immunity to Slime and Hunger potions"}
   );
   public static Item LAVAEATER = new BaubleAntipotion(
      "lava_eater",
      CreativeTabs.COMBAT,
      new Potion[]{PotionEffects.FREEZING, PotionEffects.FIERYOIL},
      BaubleType.TRINKET,
      false,
      new String[]{"Immunity to Freezing and Fiery Oil"}
   );
   public static Item PERSONALEXTINGUISHER = new BaubleAntipotion(
         "personal_extinguisher", CreativeTabs.COMBAT, null, BaubleType.TRINKET, true, new String[]{"Automatically removes fire from you"}
      )
      .setRender(1);
   public static Item ETHERWORM = new BaubleAntipotion.BaubleAntipotionFallless(
      "ether_worm",
      CreativeTabs.COMBAT,
      new Potion[]{PotionEffects.ENDER_POISON},
      BaubleType.TRINKET,
      false,
      new String[]{"Immunity to Ender Poison and fall damage"}
   );
   public static Item ANGELWORM = new BaubleAntipotion.BaubleAntipotionFallless(
         "angelworm",
         CreativeTabs.COMBAT,
         new Potion[]{PotionEffects.ENDER_POISON, PotionEffects.SLIME, MobEffects.HUNGER, PotionEffects.FREEZING, PotionEffects.FIERYOIL},
         BaubleType.TRINKET,
         false,
         new String[]{"Immunity to Slime, Hunger, Freezing, Fiery Oil,", "Ender Poison and fall damage"}
      )
      .setRender(1);
   public static Item CROSSCHAINLET = new BaubleAntipotion(
         "cross_chainlet", CreativeTabs.COMBAT, PotionEffects.DEMONIC_BURN, BaubleType.AMULET, "Immunity to Demonic Burn"
      )
      .setRender(1);
   public static Item DETOXICATOR = new BaubleAntipotion(
         "detoxicator", CreativeTabs.COMBAT, MobEffects.POISON, BaubleType.RING, "Immunity to Poison"
      )
      .setRender(3);
   public static Item HAZARDGLOVE = new BaubleAntipotion(
      "hazard_glove",
      CreativeTabs.COMBAT,
      new Potion[]{MobEffects.MINING_FATIGUE, MobEffects.POISON},
      BaubleType.RING,
      false,
      new String[]{"Immunity to Poison and Mining Fatigue"}
   );
   public static Item AMMONIAFLASK = new BaubleAntipotion(
         "ammonia_flask", CreativeTabs.COMBAT, MobEffects.NAUSEA, BaubleType.TRINKET, "Immunity to Nausea"
      )
      .setRender(1);
   public static Item CORROSIVEFLASK = new BaubleAntipotion(
         "corrosive_flask",
         CreativeTabs.COMBAT,
         new Potion[]{MobEffects.NAUSEA, MobEffects.WEAKNESS},
         BaubleType.TRINKET,
         false,
         new String[]{"Immunity to Weakness and Nausea"}
      )
      .setRender(1);
   public static Item CONDUCTIVEBELT = new BaubleAntipotion(
      "conductive_belt", CreativeTabs.COMBAT, PotionEffects.SHOCK, BaubleType.BELT, "Immunity to Shock"
   );
   public static Item LIGHTNINGSOCKS = new BaubleAntipotion(
      "lightning_socks",
      CreativeTabs.COMBAT,
      new Potion[]{PotionEffects.SHOCK, MobEffects.SLOWNESS},
      BaubleType.TRINKET,
      false,
      new String[]{"Immunity to Shock and Slowness"}
   );
   public static Item FROSTINGUISHER = new BaubleAntipotion(
         "personal_frostinguisher", CreativeTabs.COMBAT, PotionEffects.FROSTBURN, BaubleType.TRINKET, "Immunity to Burning Frost"
      )
      .setRender(1);
   public static Item HOLYEXTINGUISHER = new BaubleAntipotion(
         "holy_extinguisher",
         CreativeTabs.COMBAT,
         new Potion[]{PotionEffects.DEMONIC_BURN},
         BaubleType.TRINKET,
         false,
         new String[]{"Immunity to Demonic Burn"}
      )
      .setRender(1);
   public static Item GHOSTFLAMETRAP = new BaubleAntipotion(
         "ghostflame_trap", CreativeTabs.COMBAT, MobEffects.MINING_FATIGUE, BaubleType.TRINKET, "Immunity to Ghostflame"
      )
      .setRender(1);
   public static Item FLAMESUPPRESSOR = new BaubleAntipotion(
         "flame_suppressor",
         CreativeTabs.COMBAT,
         new Potion[]{PotionEffects.DEMONIC_BURN, PotionEffects.FROSTBURN},
         BaubleType.TRINKET,
         true,
         new String[]{"Immunity to Fire, Burning Frost and Demonic Burn"}
      )
      .setRender(1);
   public static Item ANCIENTICESHARD = new BaublesPack.BaubleAttributed(
         "ancient_ice_shard", CreativeTabs.COMBAT, PropertiesRegistry.MANASPEED_MAX, 0.2, 0, BaubleType.AMULET, "+0.2 Mana Regeneration"
      )
      .setRender(1);
   public static Item MANARUBBLE = new BaublesPack.BaubleAttributed(
      "mana_rubble", CreativeTabs.COMBAT, PropertiesRegistry.MANA_MAX, 5.0, 0, BaubleType.TRINKET, "+5 Maximum Mana"
   );
   public static Item ICEHEART = new BaublesPack.IceHeart();
   public static Item LIVEBLOODNECKLACE = new BaublesPack.LivebloodNecklace();
   public static Item BRASSKNUCKLES = new BaublesPack.BaubleAttributed(
         "brass_knuckles", CreativeTabs.COMBAT, SharedMonsterAttributes.ATTACK_DAMAGE, 2.0, 0, BaubleType.RING, "+2 Melee Attack Damage"
      )
      .setRender(5);
   public static Item HELLHOUNDCOLLAR = new BaublesPack.HellhoundCollar();
   public static Item GOLDENKNUCKLES = new BaublesPack.GoldenKnuckles();
   public static Item PERSISTENCEPENDENT = new BaublesPack.PersistencePendent();
   public static Item CYBERAMULET = new BaublesPack.CyberAmulet();
   public static Item PAINFULROOT = new BaublesPack.PainfulRoot();
   public static Item BLEEDINGROOT = new BaublesPack.BleedingRoot();
   public static Item VENOMEDDAGGER = new BaublesPack.VenomedDagger();
   public static Item SPIRITTHORN = new BaublesPack.SpiritThorn();
   public static Item SPRINGERWAISTBAND = new BaublesPack.SpringerWaistband();
   public static Item LIGHTBAND = new BaublesPack.Lightband();
   public static Item THORNKEEPER = new BaublesPack.Thornkeeper();
   public static Item MANAKEEPER = new BaublesPack.ManaKeeper();
   public static Item THISTLETHORN = new ThistleThorn();
   public static Item RESTLESSSKULL = new RestlessSkull();
   public static Item MAGICROCKET = new MagicRocket();
   public static Item STINGINGCELL = new StingingCell();
   public static Item SEAEFFLORESCE = new SeaEffloresce();
   public static Item CORALRIFLECLIP = new ItemAmmoClip("coral_rifle_clip", CreativeTabs.COMBAT, 64, CoralRifle.maxammo);
   public static Item CORALRIFLE = new CoralRifle();
   public static Item PALMLOGWAND = new ToolWand("palm_log_wand", CreativeTabs.TOOLS, 1000, "arpg:palm_log", true, "arpg:palm_log")
      .setReplaceLogic("arpg:palm_log", "arpg:palm_log")
      .setPlaceMeta(2)
      .setToReplaceMeta(2);
   public static Item TIMELESSSWORD = new TimelessSword();
   public static Item FIREWHIP = new FireWhip();
   public static Item TEAMSELECTOR = new CreativeTeamSelector();
   public static Item HADRONBLASTER = new HadronBlaster();
   public static Item SNAPBALL = new Snapball();
   public static Item SNAPBALLAMMO = new ItemItem("snapball_ammo", CreativeTabs.COMBAT, 0, 64);
   public static Item ROCKETLAUNCHER = new RocketLauncher();
   public static Item ROCKETCOMMON = new ItemRocket.CommonRocket();
   public static Item GLASSHEART = new BaublesPack.GlassHeart();
   public static Item ROCKETFROSTFIRE = new ItemRocket.FrostfireRocket();
   public static Item ROCKETCHEMICAL = new ItemRocket.ChemicalRocket();
   public static Item ROCKETNAPALM = new ItemRocket.NapalmRocket();
   public static Item ROCKETDEMOLISHING = new ItemRocket.DemolishingRocket();
   public static Item ROCKETMINING = new ItemRocket.MiningRocket();
   public static Item ROCKETVOID = new ItemRocket.VoidRocket();
   public static Item ROCKETWATERBLAST = new ItemRocket.WaterblastRocket();
   public static Item ROCKETARCANE = new ItemRocket.ArcaneRocket();
   public static Item ROCKETSURPRISE = new ItemRocket.SurpriseRocket();
   public static Item ROCKETSHELL = new ItemRocket.ShellRocket();
   public static Item BONEARMORHELM = new BoneHelm();
   public static Item BONEARMORCHEST = new BoneHelm.BoneChestplate();
   public static Item BONEARMORLEGS = new BoneHelm.BoneLeggins();
   public static Item BONEARMORBOOTS = new BoneHelm.BoneBoots();
   public static Item LICHARMORHELM = new LichHelm();
   public static Item LICHARMORCHEST = new LichHelm.LichChestplate();
   public static Item LICHARMORLEGS = new LichHelm.LichLeggins();
   public static Item LICHARMORBOOTS = new LichHelm.LichBoots();
   public static Item CORALARMORHELM = Armors.coralSET.HELMET;
   public static Item CORALARMORCHEST = Armors.coralSET.CHESTPLATE;
   public static Item CORALARMORLEGS = Armors.coralSET.LEGGINS;
   public static Item CORALARMORBOOTS = Armors.coralSET.BOOTS;
   public static Item SNOWCOATARMORHELM = Armors.snowcoatSET.HELMET;
   public static Item SNOWCOATARMORCHEST = Armors.snowcoatSET.CHESTPLATE;
   public static Item SNOWCOATARMORLEGS = Armors.snowcoatSET.LEGGINS;
   public static Item SNOWCOATARMORBOOTS = Armors.snowcoatSET.BOOTS;
   public static Item RUSTEDKEY = new Key("rusted_key", 64, ChestLock.RUSTED_KEY, true);
   public static Item GASMASK = new BaublesPack.Gasmask();
   public static Item ANTIRADPILLS = new ItemEatable("anti_rad_pills", 0, 64, 0, 0.0F, false, 14, null, null, false, -250).setAlwaysEdible();
   public static Item ANTIRADINJECTOR = new AntiRadInjector();
   public static Item ANTIRADPACK = new BaublesPack.AntiRadPack();
   public static Item BUNKERKEYCARD = new ItemItem("bunker_keycard", CreativeTabs.TOOLS, 0, 1);
   public static Item VIRULENTROD = new VirulentRod();
   public static Item WRENCH = new Wrench();
   public static Item ITEMTURRET = new ItemTurret();
   public static Item CHAINMACEIRON = new ChainMace("chain_mace");
   public static Item CHAINMACEDIAMOND = new ChainMace.DiamondChainMace();
   public static Item CHAINMACEMOLTEN = new ChainMace.MoltenChainMace();
   public static Item ICEBREAKER = new ChainMace.Icebreaker();
   public static Item GRENADECLASSIC = new ItemGrenade("frag_grenade", 32, (byte)1, 30, 18.0F, 2.0F, new ResourceLocation("arpg:textures/frag_grenade.png"));
   public static Item GRENADEBOMB = new ItemGrenade.Bomb("bomb", 24, (byte)2, 40, 0.0F, 0.0F, new ResourceLocation("arpg:textures/grenade_bomb.png"));
   public static Item GRENADECRYO = new ItemGrenade.Cryogrenade(
      "cryogrenade", 16, (byte)3, 30, 5.0F, 0.0F, new ResourceLocation("arpg:textures/grenade_cryo.png")
   );
   public static Item GRENADEHELL = new ItemGrenade.HellGrenade(
      "hell_grenade", 20, (byte)4, 30, 11.0F, 0.8F, new ResourceLocation("arpg:textures/grenade_hell.png")
   );
   public static Item GRENADEMOLOTOV = new ItemGrenade.MolotovпїЅocktail(
      "molotov_cocktail", 32, (byte)5, 100, 0.0F, 0.0F, new ResourceLocation("arpg:textures/grenade_molotov.png")
   );
   public static Item GRENADEOIL = new ItemGrenade.OilBottle("oil_bottle", 48, (byte)6, 100, 0.0F, 0.0F, new ResourceLocation("arpg:textures/grenade_oil.png"));
   public static Item GRENADESNOW = new ItemGrenade.SnowGrenade(
      "snow_grenade", 24, (byte)7, 30, 0.0F, 0.0F, new ResourceLocation("arpg:textures/grenade_snow.png")
   );
   public static Item GRENADEGAS = new ItemGrenade.GasGrenade(
      "gas_grenade", 20, (byte)8, 80, 30.0F, 1.0F, new ResourceLocation("arpg:textures/grenade_gas.png")
   );
   public static Item GRENADESEA = new ItemGrenade.SeaGrenade(
      "sea_grenade", 18, (byte)9, 30, 15.0F, 1.5F, new ResourceLocation("arpg:textures/grenade_sea.png")
   );
   public static Item GRENADEWATCHING = new ItemGrenade.WatchingGrenade(
      "watching_grenade", 14, (byte)10, 35, 0.0F, 0.0F, new ResourceLocation("arpg:textures/grenade_watching.png")
   );
   public static Item GRENADEGRAVITY = new ItemGrenade.GravityGrenade(
      "gravity_grenade", 16, (byte)11, 40, 0.0F, 0.0F, new ResourceLocation("arpg:textures/grenade_gravity.png")
   );
   public static Item LIVEHEART = new ItemItem("live_heart", CreativeTabs.FOOD, 0, 64);
   public static Item ENDERCROWN = new BaublesPack.EnderCrown();
   public static Item STORMLEDGELOGWANDBRASS = new ToolWand(
         "arthroslelecha_brass_log_wand", CreativeTabs.TOOLS, 1000, "arpg:arthrostelecha_log_brass", true, "arpg:arthrostelecha_log_brass"
      )
      .setReplaceLogic("arpg:arthrostelecha_log_brass", "arpg:arthrostelecha_log_brass")
      .setPlaceMeta(2)
      .setToReplaceMeta(2);
   public static Item STORMLEDGELOGWANDPINK = new ToolWand(
         "arthroslelecha_pink_log_wand", CreativeTabs.TOOLS, 1000, "arpg:arthrostelecha_log_pink", true, "arpg:arthrostelecha_log_pink"
      )
      .setReplaceLogic("arpg:arthrostelecha_log_pink", "arpg:arthrostelecha_log_pink")
      .setPlaceMeta(2)
      .setToReplaceMeta(2);
   public static Item PROCESSORPATTERN = new ItemItem("processor_pattern", CreativeTabs.MATERIALS, 0, 64);
   public static Item CMO = new ItemItem("cmo", CreativeTabs.MATERIALS, 0, 64);
   public static Item MUTAGEN = new ItemItem("mutagen", CreativeTabs.MATERIALS, 0, 64);
   public static Item EMBRYO = new ItemItem("embryo", CreativeTabs.MATERIALS, 0, 64);
   public static Item VILESUBSTANCE = new ItemItem("vile_substance", CreativeTabs.MATERIALS, 0, 64);
   public static Item INGOTNORTHERN = new ItemItem("northern_ingot", CreativeTabs.MATERIALS, 0, 64);
   public static Item NORTHERNSPHERE = new ItemItem("northern_sphere", CreativeTabs.MATERIALS, 0, 64);
   public static Item ADAMANTIUMROUNDS = new AdamantiumRounds();
   public static Item ADAMANTIUMREVOLVER = new AdamantiumRevolver();
   public static Item ADAMANTIUMBATTLEAXE = new AdamantiumBattleAxe();
   public static Item ADAMANTIUMARMORHELM = Armors.adamantiumSET.HELMET;
   public static Item ADAMANTIUMARMORCHEST = Armors.adamantiumSET.CHESTPLATE;
   public static Item ADAMANTIUMARMORLEGS = Armors.adamantiumSET.LEGGINS;
   public static Item ADAMANTIUMARMORBOOTS = Armors.adamantiumSET.BOOTS;
   public static Item ADAMANTIUMLONGSWORD = new AdamantiumLongsword();
   public static Item ADAMANTIUMKNIFE = new AdamantiumKnife();
   public static Item BULLETADAMANTIUM = new ItemBullet("bullet_adamantium", "adamantium", 64, 3.0F, 0.3F, 0.662F, 0.192F, 0.274F);
   public static Item HAZARDHELM = Armors.hazardSET.HELMET;
   public static Item HAZARDCHEST = Armors.hazardSET.CHESTPLATE;
   public static Item HAZARDLEGS = Armors.hazardSET.LEGGINS;
   public static Item HAZARDBOOTS = Armors.hazardSET.BOOTS;
   public static Item STORMSPANNER = new StormSpanner();
   public static Item INGOTADAMANTIUM = new ItemItem("adamantium_ingot", CreativeTabs.MATERIALS, 0, 64);
   public static Item DUSTADAMANTIUM = new ItemItem("adamantium_dust", CreativeTabs.MATERIALS, 0, 64);
   public static Item HEROBRINECURSE = new BaublesPack.HerobrineCurse();
   public static Item VOIDCRYSTAL = new ItemNoGravivy("void_crystal", CreativeTabs.MATERIALS, 0, 64);
   public static Item INGOTTOXINIUM = new ItemItem("toxinium_ingot", CreativeTabs.MATERIALS, 0, 64);
   public static Item DUSTTOXINIUM = new ItemItem("toxinium_dust", CreativeTabs.MATERIALS, 0, 64);
   public static Item NUGGETTOXINIUM = new ItemItem("toxinium_nugget", CreativeTabs.MATERIALS, 0, 64);
   public static Item INGOTSTORMSTEEL = new ItemItem("stormsteel_ingot", CreativeTabs.MATERIALS, 0, 64);
   public static Item DUSTSTORMSTEEL = new ItemItem("stormsteel_dust", CreativeTabs.MATERIALS, 0, 64);
   public static Item NUGGETSTORMSTEEL = new ItemItem("stormsteel_nugget", CreativeTabs.MATERIALS, 0, 64);
   public static Item INGOTSTORMBRASS = new ItemItem("stormbrass_ingot", CreativeTabs.MATERIALS, 0, 64);
   public static Item DUSTSTORMBRASS = new ItemItem("stormbrass_dust", CreativeTabs.MATERIALS, 0, 64);
   public static Item NUGGETSTORMBRASS = new ItemItem("stormbrass_nugget", CreativeTabs.MATERIALS, 0, 64);
   public static Item INGOTARSENIC = new ItemItem("arsenic_ingot", CreativeTabs.MATERIALS, 0, 64);
   public static Item DUSTARSENIC = new ItemItem("arsenic_dust", CreativeTabs.MATERIALS, 0, 64);
   public static Item NUGGETARSENIC = new ItemItem("arsenic_nugget", CreativeTabs.MATERIALS, 0, 64);
   public static Item INGOTWOLFRAM = new ItemItem("wolfram_ingot", CreativeTabs.MATERIALS, 0, 64);
   public static Item DUSTWOLFRAM = new ItemItem("wolfram_dust", CreativeTabs.MATERIALS, 0, 64);
   public static Item NUGGETWOLFRAM = new ItemItem("wolfram_nugget", CreativeTabs.MATERIALS, 0, 64);
   public static Item SKYCRYSTAL = new ItemItem("sky_crystal", CreativeTabs.MATERIALS, 0, 64);
   public static Item SKYCRYSTALPIECE = new ItemItem("sky_crystal_piece", CreativeTabs.MATERIALS, 0, 64);
   public static Item WINDNATURE = new ItemNoGravivy("wind_nature", CreativeTabs.MATERIALS, 0, 64);
   public static Item SKYSPHERE = new ItemItem("sky_sphere", CreativeTabs.MATERIALS, 0, 64);
   public static Item ENDERPROTECTOR = new EnderProtector();
   public static Item DRAGONTAIL = new DragonTail();
   public static Item DRAGONSHELL = new DragonShell();
   public static Item WINTERBREATH = new WinterBreath();
   public static Item TOXINIUMSHIELD = new ToxiniumShield();
   public static Item CARAPACE = new Carapace();
   public static Item ROTTENSHIELD = new RottenShield();
   public static Item HELLMARK = new Hellmark();
   public static Item TOXIBERRYJUICEDRIP = new ItemItem("toxiberry_juice_drip", CreativeTabs.MATERIALS, 0, 64);
   public static Item THUNDERSTONE = new ItemItem("thunder_stone", CreativeTabs.MATERIALS, 0, 64);
   public static Item THUNDERCAPACITOR = new ItemItem("thunder_capacitor", CreativeTabs.MATERIALS, 0, 64);
   public static Item ADVANCED_POLYMER = new ItemItem("advanced_polymer", CreativeTabs.MATERIALS, 0, 64).setFuel(20);
   public static Item DUSTGLOWINGCRYSTAL = new ItemItem("glowing_crystal_dust", CreativeTabs.MATERIALS, 0, 64);
   public static Item DUSTMAGICCRYSTAL = new ItemItem("magic_crystal_dust", CreativeTabs.MATERIALS, 0, 64);
   public static Item COPPERSULFATE = new ItemItem("copper_sulfate", CreativeTabs.MATERIALS, 0, 64);
   public static Item PLANTFIBER = new ItemItem("plant_fiber", CreativeTabs.MATERIALS, 0, 64);
   public static Item DRIEDPLANTFIBER = new ItemItem("dried_plant_fiber", CreativeTabs.MATERIALS, 0, 64).setFuel(10);
   public static Item CRYSTALPOISON = new ItemItem("crystallized_poison", CreativeTabs.MATERIALS, 0, 64);
   public static Item SALT = new ItemItem("salt", CreativeTabs.MATERIALS, 0, 64);
   public static Item GRAINSSALT = new ItemItem("salt_grains", CreativeTabs.MATERIALS, 0, 64);
   public static Item CONIFERROSIN = new ItemItem("conifer_rosin", CreativeTabs.MATERIALS, 0, 64);
   public static Item FIERYOIL = new ItemItem("fiery_oil", CreativeTabs.MATERIALS, 0, 64).setFuel(70);
   public static Item ICEDUST = new ItemItem("ice_dust", CreativeTabs.MATERIALS, 0, 64).setEnchantGlow();
   public static Item CHEMICALGLASS = new ItemItem("chemical_glass", CreativeTabs.MATERIALS, 0, 64);
   public static Item INGOTBRASS = new ItemItem("brass_ingot", CreativeTabs.MATERIALS, 0, 64);
   public static Item DUSTBRASS = new ItemItem("brass_dust", CreativeTabs.MATERIALS, 0, 64);
   public static Item NUGGETBRASS = new ItemItem("brass_nugget", CreativeTabs.MATERIALS, 0, 64);
   public static Item INGOTZINC = new ItemItem("zinc_ingot", CreativeTabs.MATERIALS, 0, 64);
   public static Item DUSTZINC = new ItemItem("zinc_dust", CreativeTabs.MATERIALS, 0, 64);
   public static Item NUGGETZINC = new ItemItem("zinc_nugget", CreativeTabs.MATERIALS, 0, 64);
   public static Item INGOTTITANIUM = new ItemItem("titanium_ingot", CreativeTabs.MATERIALS, 0, 64);
   public static Item DUSTTITANIUM = new ItemItem("titanium_dust", CreativeTabs.MATERIALS, 0, 64);
   public static Item NUGGETTITANIUM = new ItemItem("titanium_nugget", CreativeTabs.MATERIALS, 0, 64);
   public static Item BAUXITE = new ItemItem("bauxite", CreativeTabs.MATERIALS, 0, 64);
   public static Item PIZZACHICKEN = new ItemEatable("pizza_chicken", 0, 64, 8, 1.0F, false, 38, null, null, false, 0);
   public static Item PIZZADIAVOLA = new ItemEatable(
      "pizza_diavola", 0, 64, 9, 1.0F, false, 38, new PotionEffect[]{new PotionEffect(PotionEffects.BERSERK, 666)}, new float[]{1.0F}, false, 0
   );
   public static Item PIZZACHEEZE = new ItemEatable("pizza_cheeze", 0, 64, 7, 1.0F, false, 36, null, null, false, 0);
   public static Item PIZZATOXIC = new ItemEatable(
      "pizza_toxedge", 0, 64, 10, 1.0F, false, 38, new PotionEffect[]{new PotionEffect(MobEffects.POISON, 50)}, new float[]{0.5F}, false, 60
   );
   public static Item PIZZAGLOWING = new ItemEatable(
      "pizza_glowing",
      0,
      64,
      9,
      1.0F,
      false,
      32,
      new PotionEffect[]{
         new PotionEffect(MobEffects.GLOWING, 150), new PotionEffect(PotionEffects.MAGIC_POWER, 250), new PotionEffect(PotionEffects.RAINBOW, 140)
      },
      new float[]{0.9F, 1.0F, 0.5F},
      false,
      60
   );
   public static Item PIZZASEAFOOD = new ItemEatable("pizza_seafood", 0, 64, 12, 1.0F, false, 32, null, null, false, 0);
   public static Item SMOKEDSAUSAGE = new ItemEatable("smoked_sausage", 0, 64, 7, 0.8F, true, 26, null, null, false, 0);
   public static Item RAWRIBS = new ItemEatable("raw_ribs", 0, 64, 3, 0.3F, true, 32, null, null, false, 0);
   public static Item HOTSPICYRIBS = new ItemEatable(
      "hot_spicy_ribs", 0, 64, 8, 0.8F, true, 32, new PotionEffect[]{new PotionEffect(PotionEffects.BERSERK, 400)}, new float[]{1.0F}, false, 0
   );
   public static Item MAGMABLOOMSEEDS = new MagmaBloomSeed();
   public static Item SULFUR = new ItemItem("sulfur_dust", CreativeTabs.MATERIALS, 0, 64);
   public static Item TOXIBERRYSTICK = new ItemItem("toxiberry_stick", CreativeTabs.MATERIALS, 0, 64).setFuel(10);
   public static Item SCRAPMETAL = new ItemItem("scrap_metal", CreativeTabs.MATERIALS, 0, 64);
   public static Item SLIMECELL = new ItemItem("slime_cell", CreativeTabs.MATERIALS, 0, 64);
   public static Item SLIMEPLASTIC = new ItemItem("slime_plastic", CreativeTabs.MATERIALS, 0, 64);
   public static Item WHITESLIMEBALL = new ItemItem("white_slimeball", CreativeTabs.MATERIALS, 0, 64);
   public static Item PLASTIC = new ItemItem("plastic", CreativeTabs.MATERIALS, 0, 64);
   public static Item ICICLEMINIGUNCLIP = new ItemAmmoClip("icicle_minigun_clip", CreativeTabs.COMBAT, 64, IcicleMinigun.maxammo, true);
   public static Item CIRCUIT = new ItemItem("circuit", CreativeTabs.MATERIALS, 0, 64);
   public static Item CIRCUITADVANCED = new ItemItem("advanced_circuit", CreativeTabs.MATERIALS, 0, 64);
   public static Item CIRCUITDIMENSION = new ItemItem("dimension_circuit", CreativeTabs.MATERIALS, 0, 64);
   public static Item CIRCUITTOXIC = new ItemItem("toxic_circuit", CreativeTabs.MATERIALS, 0, 64);
   public static Item WIRECOPPER = new ItemItem("copper_wire", CreativeTabs.MATERIALS, 0, 64);
   public static Item WIREGOLD = new ItemItem("gold_wire", CreativeTabs.MATERIALS, 0, 64);
   public static Item WIRESILVER = new ItemItem("silver_wire", CreativeTabs.MATERIALS, 0, 64);
   public static Item RUBBER = new ItemItem("rubber", CreativeTabs.MATERIALS, 0, 64);
   public static Item PROCESSOR = new ItemItem("processor", CreativeTabs.MATERIALS, 0, 64);
   public static Item EYEOFSEER = new ItemItem("eye_of_seer", CreativeTabs.MATERIALS, 0, 64);
   public static Item ELECTRICMOTOR = new ItemItem("electric_motor", CreativeTabs.MATERIALS, 0, 64);
   public static Item LINEARMOTOR = new ItemItem("linear_motor", CreativeTabs.MATERIALS, 0, 64);
   public static Item BEARINGLEAD = new ItemItem("lead_bearing", CreativeTabs.MATERIALS, 0, 64);
   public static Item BEARINGARSENIC = new ItemItem("arsenic_bearing", CreativeTabs.MATERIALS, 0, 64);
   public static Item BATTERYLEADACID = new ItemAccumulator("lead_acid_battery", ItemAccumulator.LEAD_ACID_CAPACITY, ItemAccumulator.LEAD_ACID_THROUGHPUT);
   public static Item BATTERYLIION = new ItemAccumulator("li_ion_battery", ItemAccumulator.LI_ION_CAPACITY, ItemAccumulator.LI_ION_THROUGHPUT);
   public static Item GASFILTER = new ItemItem("gas_filter", CreativeTabs.MATERIALS, 0, 64);
   public static Item WIREWOLFRAM = new ItemItem("wolfram_wire", CreativeTabs.MATERIALS, 0, 64);
   public static Item NAIL = new ItemItem("nail", CreativeTabs.MATERIALS, 0, 64);
   public static Item NAILGUNCLIP = new ItemAmmoClip("nail_gun_clip", CreativeTabs.COMBAT, 64, NailGun.maxammo, true);
   public static Item NUGGETADAMANTIUM = new ItemItem("adamantium_nugget", CreativeTabs.MATERIALS, 0, 64);
   public static Item BLOWHOLE = new Blowhole();
   public static Item BLOWHOLEPELLETS = new ItemItem("blowhole_pellets", CreativeTabs.COMBAT, 0, 64);
   public static Item CRYSTALCUTTER = new CrystalCutter();
   public static Item CRYSTALCUTTERAMMO = new ItemItem("crystal_cutter_ammo", CreativeTabs.COMBAT, 0, 64);
   public static Item PLASMAMINIGUN = new PlasmaMinigun();
   public static Item PLASMAMINIGUNCLIP = new ItemAmmoClip("plasma_minigun_clip", CreativeTabs.COMBAT, 64, PlasmaMinigun.maxammo);
   public static Item CERATARGET = new Ceratarget();
   public static Item HAILTEAR = new HailTear();
   public static Item WINTERSACRIFICE = new WinterSacrifice();
   public static Item WINTERSCALE = new ItemItem("winter_scale", CreativeTabs.MATERIALS, 0, 64);
   public static Item BURNINGFROSTIGNITER = new BurningFrostIgniter();
   public static Item GOTHICSWORD = new GothicSword();
   public static Item CRYSTALHELM = Armors.crystalSET.HELMET;
   public static Item CRYSTALCHEST = Armors.crystalSET.CHESTPLATE;
   public static Item CRYSTALLEGS = Armors.crystalSET.LEGGINS;
   public static Item CRYSTALBOOTS = Armors.crystalSET.BOOTS;
   public static Item THUNDERERHELM = Armors.thundererSET.HELMET;
   public static Item THUNDERERCHEST = Armors.thundererSET.CHESTPLATE;
   public static Item THUNDERERLEGS = Armors.thundererSET.LEGGINS;
   public static Item THUNDERERBOOTS = Armors.thundererSET.BOOTS;
   public static Item NORTHERNHELM = Armors.northernSET.HELMET;
   public static Item NORTHERNCHEST = Armors.northernSET.CHESTPLATE;
   public static Item NORTHERNLEGS = Armors.northernSET.LEGGINS;
   public static Item NORTHERNBOOTS = Armors.northernSET.BOOTS;
   public static Item GRENADELAUNCHER = new GrenadeLauncher();
   public static Item HOLYSHOTGUN = new HolyShotgun();
   public static Item BUCKSHOT = new Buckshot();
   public static Item ECHINUS = new ChainMace.Echinus();
   public static Item TIDEACTIVATOR1 = new ItemItem("tide_activator_1", CreativeTabs.TOOLS, 0, 1);
   public static Item TIDEACTIVATOR2 = new ItemItem("tide_activator_2", CreativeTabs.TOOLS, 0, 1);
   public static Item TIDEACTIVATOR3 = new ItemItem("tide_activator_3", CreativeTabs.TOOLS, 0, 1);
   public static Item TIDEACTIVATOR4 = new ItemItem("tide_activator_4", CreativeTabs.TOOLS, 0, 1);
   public static Item TIDEACTIVATOR5 = new ItemItem("tide_activator_5", CreativeTabs.TOOLS, 0, 1);
   public static Item ARROWFROZEN = new Arrows.ArrowFrozen();
   public static Item ARROWFIREJET = new Arrows.ArrowFirejet();
   public static Item ARROWVICIOUS = new Arrows.ArrowVicious();
   public static Item ARROWMODERN = new Arrows.ArrowModern();
   public static Item ARROWBENGAL = new Arrows.ArrowBengal();
   public static Item ARROWFISH = new Arrows.ArrowFish();
   public static Item ARROWVOID = new Arrows.ArrowVoid();
   public static Item ARROWSHELL = new Arrows.ArrowShell();
   public static Item ARROWBOUNCING = new Arrows.ArrowBouncing();
   public static Item ARROWMITHRIL = new Arrows.ArrowMithril();
   public static Item ARROWTWIN = new Arrows.ArrowTwin();
   public static Item ARROWWIND = new Arrows.ArrowWind();
   public static Item MITHRILBOW = new MithrilBow();
   public static Item COMPOUNDBOW = new CompoundBow();
   public static Item STAFFOFTHEAZUREORE = new AzureOreStaff();
   public static Item INGOTSILVER = new ItemItem("silver_ingot", CreativeTabs.MATERIALS, 0, 64);
   public static Item DUSTSILVER = new ItemItem("silver_dust", CreativeTabs.MATERIALS, 0, 64);
   public static Item NUGGETSILVER = new ItemItem("silver_nugget", CreativeTabs.MATERIALS, 0, 64);
   public static Item COPPERTRANSFORMER = new ItemItem("copper_transformer", CreativeTabs.MATERIALS, 0, 64);
   public static Item STAMPMOLD = new ItemItem("stamp_mold", CreativeTabs.MATERIALS, 0, 64);
   public static Item STEELSTAMP = new ItemItem("steel_stamp", CreativeTabs.MATERIALS, 0, 64);
   public static Item BEARINGALLOYDUST = new ItemItem("bearing_alloy_dust", CreativeTabs.MATERIALS, 0, 64);
   public static Item INGOTALUMINIUM = new ItemItem("aluminium_ingot", CreativeTabs.MATERIALS, 0, 64);
   public static Item DUSTALUMINIUM = new ItemItem("aluminium_dust", CreativeTabs.MATERIALS, 0, 64);
   public static Item NUGGETALUMINIUM = new ItemItem("aluminium_nugget", CreativeTabs.MATERIALS, 0, 64);
   public static Item MODULEELECTROLYZER = new ItemItem("electrolyzer_module", CreativeTabs.MATERIALS, 256, 64);
   public static Item MODULEBIOFILTERING = new ItemItem("biofiltering_module", CreativeTabs.MATERIALS, 512, 64);
   public static Item GRAINSBERYLLIUM = new ItemItem("beryllium_grains", CreativeTabs.MATERIALS, 0, 64);
   public static Item INSTANCER = new Instancer("instancer", 1.5F, 1.3F, 2000);
   public static Item SLIMYEGGS = new ItemMobEgg("slimy_eggs", 64, new ResourceLocation("arpg:white_slime"));
   public static Item SPAWNERPIECE = new ItemItem("spawner_piece", CreativeTabs.MATERIALS, 0, 64);
   public static Item FROZENSPAWNERPIECE = new ItemItem("frozen_spawner_piece", CreativeTabs.MATERIALS, 0, 64);
   public static Item RUSTEDSPAWNERPIECE = new ItemItem("rusted_spawner_piece", CreativeTabs.MATERIALS, 0, 64);
   public static Item ENDERINSTANCER = new EnderInstancer();
   public static Item WINTERINSTANCER = new WinterInstancer();
   public static Item MILITARYINSTANCER = new MilitaryInstancer();
   public static Item AQUATICINSTANCER = new AquaticInstancer();
   public static Item VIOLENCE = new Violence();
   public static Item STAFFOFWITHERDRY = new StaffOfWitherdry();
   public static Item HELLHOUNDFUR = new ItemItem("hellhound_fur", CreativeTabs.MATERIALS, 0, 64);
   public static Item NIVEOLITE = new ItemItem("niveolite", CreativeTabs.MATERIALS, 0, 64);
   public static Item WHISPERSBLADE = new WhispersBlade();
   public static Item PUMPSHOTGUN = new PumpShotgun();
   public static Item GOTHICBOW = new GothicBow();
   public static Item COOLEDRIFLECLIP = new ItemAmmoClip("cooled_rifle_clip", CreativeTabs.COMBAT, 64, 28);
   public static Item COOLEDRIFLE = new CooledRifle();
   public static Item AQUATICBOW = new AquaticBow();
   public static Item MAGICEXPLORINGKIT = new ItemItem("magic_exploring_kit", CreativeTabs.TOOLS, 0, 1);
   public static Item MAGICRESEARCHKIT = new ItemItem("magic_research_kit", CreativeTabs.TOOLS, 0, 1);
   public static Item MAGICWRITINGKIT = new ItemItem("magic_writing_kit", CreativeTabs.TOOLS, 0, 1);
   public static Item BEAKER = new Beaker();
   public static Item SPELLPLIERS = new SpellPliers();
   public static Item VIALFIRE = new ItemVial(1);
   public static Item VIALEARTH = new ItemVial(2);
   public static Item VIALWATER = new ItemVial(3);
   public static Item VIALAIR = new ItemVial(4);
   public static Item VIALPOISON = new ItemVial(5);
   public static Item VIALCOLD = new ItemVial(6);
   public static Item VIALELECTRIC = new ItemVial(7);
   public static Item VIALVOID = new ItemVial(8);
   public static Item VIALPLEASURE = new ItemVial(9);
   public static Item VIALPAIN = new ItemVial(10);
   public static Item VIALDEATH = new ItemVial(11);
   public static Item VIALLIVE = new ItemVial(12);
   public static Item[] vials = new Item[]{
      VIALFIRE, VIALEARTH, VIALWATER, VIALAIR, VIALPOISON, VIALCOLD, VIALELECTRIC, VIALVOID, VIALPLEASURE, VIALPAIN, VIALDEATH, VIALLIVE
   };
   public static Item VIAL = new ItemVial(0);
   public static Item VIALEMPTY = new ItemVial(-1);
   public static Item SPELLROLL = new ItemSpellRoll();
   public static Item SHADOWWINGS = new ShadowWings();
   public static Item DASHBELTBLACK = new DashBelt.DashBeltBlack();
   public static Item DASHBELTHELLHOUND = new DashBelt.HellhoundBelt();
   public static Item DASHBELTIMPULSECORSLET = new DashBelt.ImpulseCorslet();
   public static Item BOOKOFELEMENTS = new ElementsBook();
   public static Item XMASSLAUNCHER = new XmassLauncher();
   public static Item XMASSBUNDLE = new ItemItem("xmass_bundle", CreativeTabs.COMBAT, 0, 64);
   public static Item ADAMANTIUMMINIGUN = new AdamantiumMinigun();
   public static Item ADAMANTIUMMINIGUNCLIP = new ItemAmmoClip("adamantium_minigun_clip", CreativeTabs.COMBAT, 64, AdamantiumMinigun.maxammo);
   public static Item BUZDYGAN = new Buzdygan();
   public static Item WHIP = new Whip("whip", 450);
   public static Item MAULER = new Mauler();
   public static Item SNAKEWHIP = new Snakewhip();
   public static Item ENIGMATETWINS = new BaublesPack.EnigmateTwins();
   public static Item ALCHEMICALWAX = new ItemItem("alchemical_wax", CreativeTabs.MATERIALS, 0, 64).setFuel(40);
   public static Item MANABERRY = new ItemEatable(
         "mana_berry", 0, 64, 0, 0.0F, false, 32, new PotionEffect[]{new PotionEffect(MobEffects.NAUSEA, 150)}, new float[]{1.0F}, false, 0
      )
      .setMana(3.0F);
   public static Item HEALTHBERRY = new ItemEatable(
      "health_berry",
      0,
      64,
      1,
      0.0F,
      false,
      32,
      new PotionEffect[]{
         new PotionEffect(MobEffects.INSTANT_HEALTH, 1),
         new PotionEffect(PotionEffects.HEALTH_DEGRADATION, 640),
         new PotionEffect(PotionEffects.HEALTH_DEGRADATION, 640, 1)
      },
      new float[]{1.0F, 0.9F, 0.2F},
      false,
      0
   );
   public static Item QUANTUMSLIMEBALL = new ItemItem("quantum_slimeball", CreativeTabs.MATERIALS, 0, 64);
   public static Item CANISTER = new Canister();
   public static Item REDPEPPER = new ItemEatable("red_pepper", 0, 64, 4, 0.2F, false, 28, null, null, false, 0).setBurn(3);
   public static Item MOONSHROOMMEAT = new ItemEatable("moonshroom_meat", 0, 64, 2, 0.1F, false, 38, null, null, false, 0);
   public static Item YEAST = new ItemItem("yeast", CreativeTabs.FOOD, 0, 64);
   public static Item WHEYSTARTER = new ItemItem("whey_starter", CreativeTabs.FOOD, 0, 64);
   public static Item MUSHROOMSINSAUCE = new ItemEatable("mushrooms_in_sauce", 0, 64, 12, 0.9F, false, 34, null, null, false, 0)
      .setStackToReturn(new ItemStack(Items.BOWL));
   public static Item MOZZARELLA = new ItemEatable("mozzarella", 0, 64, 2, 0.2F, false, 32, null, null, false, 0);
   public static Item DOUGH = new ItemItem("dough", CreativeTabs.FOOD, 0, 64);
   public static Item SWEETDOUGH = new ItemItem("sweet_dough", CreativeTabs.FOOD, 0, 64);
   public static Item TOXEDGEDOUGH = new ItemItem("toxedge_dough", CreativeTabs.FOOD, 0, 64);
   public static Item MAGICJAM = new ItemEatable(
         "magic_jam", 0, 64, 6, 0.7F, false, 28, new PotionEffect[]{new PotionEffect(PotionEffects.MANA_REGENERATION, 400)}, new float[]{1.0F}, false, 4
      )
      .setMana(6.0F)
      .setAlwaysEdible();
   public static Item TOMATOCHERRY = new ItemEatable("cherry_tomato", 0, 64, 2, 0.4F, false, 32, null, null, false, 0);
   public static Item MODULEFERMENTER = new ItemItem("fermenter_module", CreativeTabs.MATERIALS, 256, 64);
   public static Item MODULECENTRIFUGE = new ItemItem("centrifuge_module", CreativeTabs.MATERIALS, 256, 64);
   public static Item BORSCH = new ItemEatable("borsch", 0, 64, 9, 0.75F, false, 36, null, null, true, 0).setStackToReturn(new ItemStack(Items.BOWL));
   public static Item STUFFEDFIERYBEAN = new ItemEatable("stuffed_fiery_bean", 0, 64, 9, 0.6F, true, 32, null, null, false, 0);
   public static Item MEATBROTH = new ItemEatable("meat_broth", 0, 64, 3, 0.2F, true, 12, null, null, true, 0)
      .setStackToReturn(new ItemStack(Items.BOWL));
   public static Item FLOUR = new ItemItem("flour", CreativeTabs.MATERIALS, 0, 64);
   public static Item PUDDINGVIOLET = new ItemEatable(
         "violet_pudding", 0, 64, 6, 0.6F, false, 28, new PotionEffect[]{new PotionEffect(PotionEffects.ENDER_POISON, 25)}, new float[]{1.0F}, false, 0
      )
      .setMana(5.0F)
      .setAlwaysEdible();
   public static Item PUDDINGGREEN = new ItemEatable(
         "green_pudding", 0, 64, 6, 0.6F, false, 28, new PotionEffect[]{new PotionEffect(MobEffects.POISON, 60)}, new float[]{1.0F}, false, 15
      )
      .setAlwaysEdible();
   public static Item PUDDINGBROWN = new ItemEatable(
         "brown_pudding", 0, 64, 5, 0.6F, false, 28, new PotionEffect[]{new PotionEffect(MobEffects.SPEED, 100, 1)}, new float[]{1.0F}, false, 0
      )
      .setAlwaysEdible();
   public static Item PUDDINGORANGE = new ItemEatable(
         "orange_pudding", 0, 64, 5, 0.6F, false, 28, new PotionEffect[]{new PotionEffect(MobEffects.FIRE_RESISTANCE, 200)}, new float[]{1.0F}, false, 0
      )
      .setAlwaysEdible();
   public static Item COCOABUTTER = new ItemItem("cocoa_butter", CreativeTabs.MATERIALS, 0, 64);
   public static Item COCOAPOWDER = new ItemItem("cocoa_powder", CreativeTabs.MATERIALS, 0, 64);
   public static Item BUTTER = new ItemItem("butter", CreativeTabs.MATERIALS, 0, 64);
   public static Item CHOCOLATE = new ItemEatable("chocolate", 0, 64, 4, 0.2F, false, 26, null, null, false, 0);
   public static Item CARAMEL = new ItemEatable("caramel", 0, 64, 2, 0.0F, false, 24, null, null, false, 0);
   public static Item BISCUIT = new ItemEatable("biscuit", 0, 64, 2, 0.3F, false, 14, null, null, false, 0);
   public static Item PEARL = new ItemItem("pearl", CreativeTabs.MATERIALS, 0, 64);
   public static Item PEARLBLACK = new ItemItem("black_pearl", CreativeTabs.MATERIALS, 0, 64);
   public static Item PEARLGLOWING = new ItemItem("glowing_pearl", CreativeTabs.MATERIALS, 0, 64);
   public static Item PEARLAQUATIC = new ItemItem("aquatic_pearl", CreativeTabs.MATERIALS, 0, 64);
   public static Item INGOTAQUATIC = new ItemItem("aquatic_ingot", CreativeTabs.MATERIALS, 0, 64);
   public static Item DUSTAQUATIC = new ItemItem("aquatic_dust", CreativeTabs.MATERIALS, 0, 64);
   public static Item NUGGETAQUATIC = new ItemItem("aquatic_nugget", CreativeTabs.MATERIALS, 0, 64);
   public static Item CORAL = new ItemItem("coral", CreativeTabs.MATERIALS, 0, 64);
   public static Item ARCHELONSHELL = new ItemItem("archelon_shell", CreativeTabs.MATERIALS, 0, 64);
   public static Item PLACODERMSCALES = new ItemItem("placoderm_scales", CreativeTabs.MATERIALS, 0, 64);
   public static Item MESOGLEA = new ItemItem("mesoglea", CreativeTabs.MATERIALS, 0, 64);
   public static Item INGOTMITHRIL = new ItemItem("mithril_ingot", CreativeTabs.MATERIALS, 0, 64);
   public static Item DUSTMITHRIL = new ItemItem("mithril_dust", CreativeTabs.MATERIALS, 0, 64);
   public static Item NUGGETMITHRIL = new ItemItem("mithril_nugget", CreativeTabs.MATERIALS, 0, 64);
   public static Item IMPULSETHRUSTER = new ItemItem("impulse_thruster", CreativeTabs.MATERIALS, 0, 64);
   public static Item CIRCUITSTORM = new ItemItem("storm_circuit", CreativeTabs.MATERIALS, 0, 64);
   public static Item STORMBRASSPLASMATRON = new ItemItem("stormbrass_plasmatron", CreativeTabs.MATERIALS, 0, 64);
   public static Item BEARINGELECTROMAGNETIC = new ItemItem("electromagnetic_bearing", CreativeTabs.MATERIALS, 0, 64);
   public static Item BLUEARTHROSTELECHAROD = new ItemItem("blue_arthrostelecha_rod", CreativeTabs.MATERIALS, 0, 64);
   public static Item PINKARTHROSTELECHAROD = new ItemItem("pink_arthrostelecha_rod", CreativeTabs.MATERIALS, 0, 64);
   public static Item TOPAZITRONCRYSTAL = new ItemItem("topazitron_crystal", CreativeTabs.MATERIALS, 0, 64);
   public static Item BATTERYTOPAZITRONCRYSTAL = new ItemAccumulator(
      "battery_topazitron_crystal", ItemAccumulator.TOPAZITRON_CAPACITY, ItemAccumulator.TOPAZITRON_THROUGHPUT
   );
   public static Item GOLDTRANSFORMER = new ItemItem("gold_transformer", CreativeTabs.MATERIALS, 0, 64);
   public static Item SILVERTRANSFORMER = new ItemItem("silver_transformer", CreativeTabs.MATERIALS, 0, 64);
   public static Item CIRCUITRESISTANT = new ItemItem("resistant_circuit", CreativeTabs.MATERIALS, 0, 64);
   public static Item MODULEPOLYMERIZATION = new ItemItem("polymerization_module", CreativeTabs.MATERIALS, 1000, 64);
   public static Item MODULEDISTILLATION = new ItemItem("distillation_module", CreativeTabs.MATERIALS, 1000, 64);
   public static Item MODULEPYROLYSIS = new ItemItem("pyrolysis_module", CreativeTabs.MATERIALS, 500, 64);
   public static Item PARAFFIN = new ItemItem("paraffin", CreativeTabs.MATERIALS, 0, 64).setFuel(40);
   public static Item TAR = new ItemItem("tar", CreativeTabs.MATERIALS, 0, 64).setFuel(100);
   public static Item NYLON = new ItemItem("nylon", CreativeTabs.MATERIALS, 0, 64).setFuel(30);
   public static Item DUSTRADIOACTIVESTONE = new ItemItem("radioactive_dust", CreativeTabs.MATERIALS, 0, 64);
   public static Item DUSTTOXINIUMORE = new ItemItem("toxinium_ore_dust", CreativeTabs.MATERIALS, 0, 64);
   public static Item INGOTURANIUM = new ItemItem("uranium_ingot", CreativeTabs.MATERIALS, 0, 64);
   public static Item DUSTURANIUM = new ItemItem("uranium_dust", CreativeTabs.MATERIALS, 0, 64);
   public static Item NUGGETURANIUM = new ItemItem("uranium_nugget", CreativeTabs.MATERIALS, 0, 64);
   public static Item DUSTSTONE = new ItemItem("stone_dust", CreativeTabs.MATERIALS, 0, 64);
   public static Item DUSTLIMESTONE = new ItemItem("limestone_dust", CreativeTabs.MATERIALS, 0, 64);
   public static Item DUSTBASALT = new ItemItem("basalt_dust", CreativeTabs.MATERIALS, 0, 64);
   public static Item DUSTADAMANTIUMORE = new ItemItem("adamantium_ore_dust", CreativeTabs.MATERIALS, 0, 64);
   public static Item DUSTMITHRILORE = new ItemItem("mithril_ore_dust", CreativeTabs.MATERIALS, 0, 64);
   public static Item AQUATICSPAWNERPIECE = new ItemItem("aquatic_spawner_piece", CreativeTabs.MATERIALS, 0, 64);
   public static Item TITANIUMSLAG = new ItemItem("titanium_slag", CreativeTabs.MATERIALS, 0, 64);
   public static Item BLACKSTRAP = new ItemItem("black_strap", CreativeTabs.MATERIALS, 0, 64);
   public static Item INGOTPURPURALLOY = new ItemItem("purpur_alloy", CreativeTabs.MATERIALS, 0, 64);
   public static Item EMPTYSYRINGE = new ItemItem("empty_syringe", CreativeTabs.MATERIALS, 0, 64);
   public static Item DUSTQUARTZ = new ItemItem("quartz_dust", CreativeTabs.MATERIALS, 0, 64);
   public static Item PHASEOLITE = new ItemItem("phaseolite", CreativeTabs.MATERIALS, 0, 64);
   public static Item INK = new InkBottle("ink_bottle", 256);
   public static Item MISSINGDUST = new ItemItem("missing_dust", null, 0, 64);
   public static Item MISSINGINGOT = new ItemItem("missing_ingot", null, 0, 64);
   public static Item MISSINGNUGGET = new ItemItem("missing_nugget", null, 0, 64);
   public static Item MISSINGMATERIAL = new ItemItem("missing_material", null, 0, 64);
   public static Item SLIMELOCATOR = new SlimeLocator();
   public static Item ASH = new ItemItem("ash", CreativeTabs.MATERIALS, 0, 64);
   public static Item SALTPETER = new ItemItem("saltpeter", CreativeTabs.MATERIALS, 0, 64);
   public static Item RUBBLESTONE = new ItemItem("stone_rubble", CreativeTabs.MATERIALS, 0, 64);
   public static Item SPELLROD = new SpellRod();
   public static Item HEALTHFRUIT = new HealthFruit();
   public static Item MANAEXPANSIONPOTION = new ManaExpansionPotion();
   public static Item ICECIRCLE = new ItemItem("ice_circle", CreativeTabs.MATERIALS, 0, 64);
   public static Item ICECIRCLEFILLED = new ItemItem("ice_circle_filled", CreativeTabs.MATERIALS, 0, 64);
   public static Item CRYODESTROYER = new CryoDestroyer();
   public static Item GOTHICGEAR = new ItemItem("gothic_gear", CreativeTabs.MATERIALS, 0, 64);
   public static Item GOTHICGEM = new ItemItem("gothic_gem", CreativeTabs.MATERIALS, 0, 64);
   public static Item HYDRAULICSHOTGUN = new HydraulicShotgun();
   public static Item HYDRAULICSHOTGUNCLIP = new ItemBuckshotClip("hydraulic_shotgun_clip", CreativeTabs.COMBAT, 64, 10);
   public static Item ICECOMPASS = new IceCompass();
   public static Item CHARMOFUNDYING = new BaublesPack.CharmOfUndying();
   public static Item BEARINGLOWFRICTION = new ItemItem("low_friction_bearing", CreativeTabs.MATERIALS, 0, 64);
   public static Item VACUUMGUNPELLETS = new ItemItem("vacuum_gun_pellets", CreativeTabs.COMBAT, 0, 64);
   public static Item INGOTLITHIUM = new ItemItem("lithium_ingot", CreativeTabs.MATERIALS, 0, 64);
   public static Item DUSTLITHIUM = new ItemItem("lithium_dust", CreativeTabs.MATERIALS, 0, 64);
   public static Item NUGGETLITHIUM = new ItemItem("lithium_nugget", CreativeTabs.MATERIALS, 0, 64);
   public static Item LEPIDOLITE = new ItemItem("lepidolite", CreativeTabs.MATERIALS, 0, 64);
   public static Item SILICIUM = new ItemItem("silicium", CreativeTabs.MATERIALS, 0, 64);
   public static Item SILICIUMWAFER = new ItemItem("silicium_wafer", CreativeTabs.MATERIALS, 0, 64);
   public static Item PHOTORESISTEDPLATE = new ItemItem("photoresisted_plate", CreativeTabs.MATERIALS, 0, 1);
   public static Item LITOGRAPHEDPLATE = new ItemItem("litographed_plate", CreativeTabs.MATERIALS, 0, 64);
   public static Item GALVANIZEDPLATE = new ItemItem("galvanized_plate", CreativeTabs.MATERIALS, 0, 64);
   public static Item EREBRISSHARD = new ItemItem("erebris_shard", CreativeTabs.MATERIALS, 0, 64);
   public static Item EREBRISFRAGMENT = new ItemItem("erebris_fragment", CreativeTabs.MATERIALS, 0, 64);
   public static Item EREBRISCHUNK = new ItemItem("erebris_chunk", CreativeTabs.MATERIALS, 0, 64);
   public static Item BELTOFSHADOWS = new DashBelt.BeltOfShadows();
   public static Item WHITEWINDBELT = new DashBelt.WhitewindBelt();
   public static Item SHIPWREAKERSKNOT = new DashBelt.ShipwreakersKnot();
   public static Item WINDKEEPER = new DashBelt.Windkeeper();
   public static Item BATTERYANCIENT = new ItemAccumulator("ancient_battery", ItemAccumulator.ANCIENT_CAPACITY, ItemAccumulator.ANCIENT_THROUGHPUT);
   public static Item BATTERYAQUATRONIC = new ItemAccumulator("aquatronic_battery", ItemAccumulator.AQUATRONIC_CAPACITY, ItemAccumulator.AQUATRONIC_THROUGHPUT);
   public static Item ANCIENTSPAWNERPIECE = new ItemItem("ancient_spawner_piece", CreativeTabs.MATERIALS, 0, 64);
   public static Item EYEOFBEHOLDER = new ItemItem("eye_of_beholder", CreativeTabs.MATERIALS, 0, 64);
   public static Item THUNDERBIRDWINGS = new ThunderbirdWings();
   public static Item FINWINGS = new FinWings();
   public static Item WORSHIPPERSBAIT = new WorshippersBait();
   public static Item KRAKENSKIN = new ItemItem("kraken_skin", CreativeTabs.MATERIALS, 0, 64);
   public static Item SOLIDIFIEDLIGHTNING = new ItemNoGravivy("solidified_lightning", CreativeTabs.MATERIALS, 0, 64);
   public static Item ETHERITEFUELCELL = new ItemItem("etherite_fuel_cell", CreativeTabs.MATERIALS, 0, 16);
   public static Item VITREOUSHEART = new ItemItem("vitreous_heart", CreativeTabs.MATERIALS, 0, 64);
   public static Item FIBERCLOTH = new ItemItem("fiber_cloth", CreativeTabs.MATERIALS, 0, 64).setFuel(90);
   public static Item ANTIRADPLATING = new ItemItem("anti_rad_plating", CreativeTabs.MATERIALS, 0, 64);
   public static Item BLACKGOO = new ItemItem("black_goo", CreativeTabs.MATERIALS, 0, 64);
   public static Item PALEMEATRAW = new ItemEatable(
      "pale_meat_raw", 0, 64, 2, 0.0F, true, 32, new PotionEffect[]{new PotionEffect(PotionEffects.WAVING, 220)}, new float[]{0.5F}, false, 0
   );
   public static Item PALEMEATSMOKED = new ItemEatable("pale_meat_smoked", 0, 64, 6, 0.5F, true, 32, null, null, false, 0);
   public static Item FISHSTEAKRAW = new ItemEatable("fish_steak_raw", 0, 64, 7, 0.9F, true, 32, null, null, false, 0);
   public static Item FISHSTEAKROASTED = new ItemEatable("fish_steak_roasted", 0, 64, 10, 1.0F, true, 32, null, null, false, 0);
   public static Item THUNDERBIRDFEATHER = new ItemItem("thunderbird_feather", CreativeTabs.MATERIALS, 0, 64);
   public static Item GEIGERCOUNTER = new GeigerCounter();
   public static Item HEALTHFULCAPSULE = new HealthfulCapsule();
   public static Item SCRAPBOMB = new ItemItem("scrap_bomb", CreativeTabs.MATERIALS, 0, 64);
   public static Item STABILIZATIONCELL = new ItemItem("stabilization_cell", CreativeTabs.MATERIALS, 0, 64);
   public static Item CIRCUITAQUATIC = new ItemItem("aquatic_circuit", CreativeTabs.MATERIALS, 0, 64);
   public static Item TIDALHEART = new ItemItem("tidal_heart", CreativeTabs.MATERIALS, 0, 64);
   public static Item PIRATESEXTANT = new ItemItem("pirate_sextant", CreativeTabs.MATERIALS, 0, 64);
   public static Item MERMAIDMEDALLION = new BaublesPack.MermaidMedallion();
   public static Item SIRENKEY = new Key("siren_key", 1, ChestLock.SIREN, false);
   public static Item SACRIFICIALDAGGER = new SacrificialDagger();
   public static Item STORMSPAWNERPIECE = new ItemItem("storm_spawner_piece", CreativeTabs.MATERIALS, 0, 64);
   public static Item SELECTIVELEVITATOR = new BaublesPack.SelectiveLevitator();
   public static Item FIBERBANDAGE = new FiberBandage();
   public static Item DOLERITEKEY = new Key("dolerite_key", 64, ChestLock.DOLERITE, true);
   public static Item WIZARDCLOTH = new ItemItem("wizard_cloth", CreativeTabs.MATERIALS, 0, 64);
   public static Item SWARMETER = new Swarmeter();
   public static Item RICHSCRAP = new ItemItem("rich_scrap", CreativeTabs.MATERIALS, 0, 64);
   public static Item WEATHERROCKETCLEAR = new ItemWeatherRocket("weather_rocket_clear", null, true, 0.8F, 0.8F, 0.5F);
   public static Item WEATHERROCKETSNOWFALL = new ItemWeatherRocket("weather_rocket_snowfall", "Snowfall", true, 0.95F, 0.95F, 1.0F);
   public static Item WEATHERROCKETHAIL = new ItemWeatherRocket("weather_rocket_hail", "Hail", true, 0.5F, 0.75F, 1.0F);
   public static Item WEATHERROCKETAURORA = new ItemWeatherRocket("weather_rocket_aurora", "Aurora", true, 0.1F, 1.0F, 0.57F);
   public static Item WEATHERROCKETPOISONRAIN = new ItemWeatherRocket("weather_rocket_poison_rain", "PoisonRain", true, 0.1F, 1.0F, 0.1F);
   public static Item WEATHERROCKETRAINFALL = new ItemWeatherRocket("weather_rocket_rainfall", "Rainfall", true, 0.3F, 0.8F, 1.0F);
   public static Item WEATHERROCKETSTORM = new ItemWeatherRocket("weather_rocket_storm", "Storm", true, 0.92F, 0.4F, 1.0F);
   public static Item WEATHERROCKETRAINSTORM = new ItemWeatherRocket("weather_rocket_rainstorm", "Rainstorm", true, 0.45F, 0.75F, 1.0F);
   public static Item CALIBRATIONCRYSTAL_ATTRACT_BIG = new ItemCalibrationThing("calibration_crystal_attract_big", 1.0F, 0.0F, 0.0F, false, 3.0F, 9.0F)
      .setRenders(3, ItemCalibrationThing.calibrationCrystalModel, ItemCalibrationThing.attracttex);
   public static Item CALIBRATIONCRYSTAL_ATTRACT_MEDIUM = new ItemCalibrationThing("calibration_crystal_attract_medium", 0.5F, 0.0F, 0.0F, false, 2.0F, 7.0F)
      .setRenders(2, ItemCalibrationThing.calibrationCrystalModel, ItemCalibrationThing.attracttex);
   public static Item CALIBRATIONCRYSTAL_ATTRACT_SMALL = new ItemCalibrationThing("calibration_crystal_attract_small", 0.1F, 0.0F, 0.0F, false, 2.0F, 5.0F)
      .setRenders(1, ItemCalibrationThing.calibrationCrystalModel, ItemCalibrationThing.attracttex);
   public static Item CALIBRATIONCRYSTAL_DETRACT_BIG = new ItemCalibrationThing("calibration_crystal_detract_big", -1.0F, 0.0F, 0.0F, false, 3.0F, 9.0F)
      .setRenders(3, ItemCalibrationThing.calibrationCrystalModel, ItemCalibrationThing.detracttex);
   public static Item CALIBRATIONCRYSTAL_DETRACT_MEDIUM = new ItemCalibrationThing("calibration_crystal_detract_medium", -0.5F, 0.0F, 0.0F, false, 2.0F, 7.0F)
      .setRenders(2, ItemCalibrationThing.calibrationCrystalModel, ItemCalibrationThing.detracttex);
   public static Item CALIBRATIONCRYSTAL_DETRACT_SMALL = new ItemCalibrationThing("calibration_crystal_detract_small", -0.1F, 0.0F, 0.0F, false, 2.0F, 5.0F)
      .setRenders(1, ItemCalibrationThing.calibrationCrystalModel, ItemCalibrationThing.detracttex);
   public static Item CALIBRATIONCRYSTAL_FOCUS_BIG = new ItemCalibrationThing("calibration_crystal_focus_big", 0.0F, -1.0F, 0.0F, false, 3.0F, 9.0F)
      .setRenders(3, ItemCalibrationThing.calibrationCrystalModel, ItemCalibrationThing.focustex);
   public static Item CALIBRATIONCRYSTAL_FOCUS_MEDIUM = new ItemCalibrationThing("calibration_crystal_focus_medium", 0.0F, -0.5F, 0.0F, false, 2.0F, 7.0F)
      .setRenders(2, ItemCalibrationThing.calibrationCrystalModel, ItemCalibrationThing.focustex);
   public static Item CALIBRATIONCRYSTAL_FOCUS_SMALL = new ItemCalibrationThing("calibration_crystal_focus_small", 0.0F, -0.1F, 0.0F, false, 2.0F, 5.0F)
      .setRenders(1, ItemCalibrationThing.calibrationCrystalModel, ItemCalibrationThing.focustex);
   public static Item CALIBRATIONCRYSTAL_RANGE_BIG = new ItemCalibrationThing("calibration_crystal_range_big", 0.0F, 1.0F, 0.0F, false, 3.0F, 9.0F)
      .setRenders(3, ItemCalibrationThing.calibrationCrystalModel, ItemCalibrationThing.rangetex);
   public static Item CALIBRATIONCRYSTAL_RANGE_MEDIUM = new ItemCalibrationThing("calibration_crystal_range_medium", 0.0F, 0.5F, 0.0F, false, 2.0F, 7.0F)
      .setRenders(2, ItemCalibrationThing.calibrationCrystalModel, ItemCalibrationThing.rangetex);
   public static Item CALIBRATIONCRYSTAL_RANGE_SMALL = new ItemCalibrationThing("calibration_crystal_range_small", 0.0F, 0.1F, 0.0F, false, 2.0F, 5.0F)
      .setRenders(1, ItemCalibrationThing.calibrationCrystalModel, ItemCalibrationThing.rangetex);
   public static Item CALIBRATIONCRYSTAL_SPEED_BIG = new ItemCalibrationThing("calibration_crystal_speed_big", 0.0F, 0.0F, 1.0F, false, 3.0F, 9.0F)
      .setRenders(3, ItemCalibrationThing.calibrationCrystalModel, ItemCalibrationThing.speedtex);
   public static Item CALIBRATIONCRYSTAL_SPEED_MEDIUM = new ItemCalibrationThing("calibration_crystal_speed_medium", 0.0F, 0.0F, 0.5F, false, 2.0F, 7.0F)
      .setRenders(2, ItemCalibrationThing.calibrationCrystalModel, ItemCalibrationThing.speedtex);
   public static Item CALIBRATIONCRYSTAL_SPEED_SMALL = new ItemCalibrationThing("calibration_crystal_speed_small", 0.0F, 0.0F, 0.1F, false, 2.0F, 5.0F)
      .setRenders(1, ItemCalibrationThing.calibrationCrystalModel, ItemCalibrationThing.speedtex);
   public static Item CALIBRATIONCRYSTAL_SLOW_BIG = new ItemCalibrationThing("calibration_crystal_slow_big", 0.0F, 0.0F, -1.0F, false, 3.0F, 9.0F)
      .setRenders(3, ItemCalibrationThing.calibrationCrystalModel, ItemCalibrationThing.slowtex);
   public static Item CALIBRATIONCRYSTAL_SLOW_MEDIUM = new ItemCalibrationThing("calibration_crystal_slow_medium", 0.0F, 0.0F, -0.5F, false, 2.0F, 7.0F)
      .setRenders(2, ItemCalibrationThing.calibrationCrystalModel, ItemCalibrationThing.slowtex);
   public static Item CALIBRATIONCRYSTAL_SLOW_SMALL = new ItemCalibrationThing("calibration_crystal_slow_small", 0.0F, 0.0F, -0.1F, false, 2.0F, 5.0F)
      .setRenders(1, ItemCalibrationThing.calibrationCrystalModel, ItemCalibrationThing.slowtex);
   public static Item CANDLE = new ItemCalibrationThing("magic_candle", 0.0F, 0.0F, 0.0F, true, 2.0F, 10.0F)
      .setRenders(0, ItemCalibrationThing.magicCandleModel, ItemCalibrationThing.candletex, 19, 1);
   public static Item CANDLEBLACK = new ItemCalibrationThing("magic_candle_black", 0.0F, 0.0F, 0.0F, true, 2.0F, 10.0F)
      .setRenders(0, ItemCalibrationThing.magicCandleModel, ItemCalibrationThing.candle_black, 19, 1);
   public static Item CANDLERED = new ItemCalibrationThing("magic_candle_red", 0.0F, 0.0F, 0.0F, true, 2.0F, 10.0F)
      .setRenders(0, ItemCalibrationThing.magicCandleModel, ItemCalibrationThing.candle_red, 19, 1);
   public static Item CANDLEGREEN = new ItemCalibrationThing("magic_candle_green", 0.0F, 0.0F, 0.0F, true, 2.0F, 10.0F)
      .setRenders(0, ItemCalibrationThing.magicCandleModel, ItemCalibrationThing.candle_green, 19, 1);
   public static Item CANDLEBROWN = new ItemCalibrationThing("magic_candle_brown", 0.0F, 0.0F, 0.0F, true, 2.0F, 10.0F)
      .setRenders(0, ItemCalibrationThing.magicCandleModel, ItemCalibrationThing.candle_brown, 19, 1);
   public static Item CANDLEBLUE = new ItemCalibrationThing("magic_candle_blue", 0.0F, 0.0F, 0.0F, true, 2.0F, 10.0F)
      .setRenders(0, ItemCalibrationThing.magicCandleModel, ItemCalibrationThing.candle_blue, 19, 1);
   public static Item CANDLEPURPLE = new ItemCalibrationThing("magic_candle_purple", 0.0F, 0.0F, 0.0F, true, 2.0F, 10.0F)
      .setRenders(0, ItemCalibrationThing.magicCandleModel, ItemCalibrationThing.candle_purple, 19, 1);
   public static Item CANDLECYAN = new ItemCalibrationThing("magic_candle_cyan", 0.0F, 0.0F, 0.0F, true, 2.0F, 10.0F)
      .setRenders(0, ItemCalibrationThing.magicCandleModel, ItemCalibrationThing.candle_cyan, 19, 1);
   public static Item CANDLELIGHTGRAY = new ItemCalibrationThing("magic_candle_lightgray", 0.0F, 0.0F, 0.0F, true, 2.0F, 10.0F)
      .setRenders(0, ItemCalibrationThing.magicCandleModel, ItemCalibrationThing.candle_lightgray, 19, 1);
   public static Item CANDLEGRAY = new ItemCalibrationThing("magic_candle_gray", 0.0F, 0.0F, 0.0F, true, 2.0F, 10.0F)
      .setRenders(0, ItemCalibrationThing.magicCandleModel, ItemCalibrationThing.candle_gray, 19, 1);
   public static Item CANDLEPINK = new ItemCalibrationThing("magic_candle_pink", 0.0F, 0.0F, 0.0F, true, 2.0F, 10.0F)
      .setRenders(0, ItemCalibrationThing.magicCandleModel, ItemCalibrationThing.candle_pink, 19, 1);
   public static Item CANDLELIME = new ItemCalibrationThing("magic_candle_lime", 0.0F, 0.0F, 0.0F, true, 2.0F, 10.0F)
      .setRenders(0, ItemCalibrationThing.magicCandleModel, ItemCalibrationThing.candle_lime, 19, 1);
   public static Item CANDLEYELLOW = new ItemCalibrationThing("magic_candle_yellow", 0.0F, 0.0F, 0.0F, true, 2.0F, 10.0F)
      .setRenders(0, ItemCalibrationThing.magicCandleModel, ItemCalibrationThing.candle_yellow, 19, 1);
   public static Item CANDLELIGHTBLUE = new ItemCalibrationThing("magic_candle_lightblue", 0.0F, 0.0F, 0.0F, true, 2.0F, 10.0F)
      .setRenders(0, ItemCalibrationThing.magicCandleModel, ItemCalibrationThing.candle_lightblue, 19, 1);
   public static Item CANDLEMAGENTA = new ItemCalibrationThing("magic_candle_magenta", 0.0F, 0.0F, 0.0F, true, 2.0F, 10.0F)
      .setRenders(0, ItemCalibrationThing.magicCandleModel, ItemCalibrationThing.candle_magenta, 19, 1);
   public static Item CANDLEORANGE = new ItemCalibrationThing("magic_candle_orange", 0.0F, 0.0F, 0.0F, true, 2.0F, 10.0F)
      .setRenders(0, ItemCalibrationThing.magicCandleModel, ItemCalibrationThing.candle_orange, 19, 1);
   public static Item CANDLEWHITE = new ItemCalibrationThing("magic_candle_white", 0.0F, 0.0F, 0.0F, true, 2.0F, 10.0F)
      .setRenders(0, ItemCalibrationThing.magicCandleModel, ItemCalibrationThing.candle_white, 19, 1);
   public static Item RUBYPICKAXE = new ItemBasicTool("ruby_pickaxe", CreativeTabs.TOOLS, 260, 1, true)
      .setHarvestLvl("pickaxe", 2, 7.5F)
      .setItemToRepair(RUBY)
      .setToolEnchantability(23);
   public static Item RUBYAXE = new ItemBasicTool("ruby_axe", CreativeTabs.TOOLS, 260, 1, true)
      .setHarvestLvl("axe", 2, 7.5F)
      .setItemToRepair(RUBY)
      .setToolEnchantability(23);
   public static Item RUBYSHOVEL = new ItemBasicTool("ruby_shovel", CreativeTabs.TOOLS, 260, 1, true)
      .setHarvestLvl("shovel", 2, 7.5F)
      .setItemToRepair(RUBY)
      .setToolEnchantability(23);
   public static Item SAPPHIREPICKAXE = new ItemBasicTool("sapphire_pickaxe", CreativeTabs.TOOLS, 260, 1, true)
      .setHarvestLvl("pickaxe", 2, 7.5F)
      .setItemToRepair(SAPPHIRE)
      .setToolEnchantability(23);
   public static Item SAPPHIREAXE = new ItemBasicTool("sapphire_axe", CreativeTabs.TOOLS, 260, 1, true)
      .setHarvestLvl("axe", 2, 7.5F)
      .setItemToRepair(SAPPHIRE)
      .setToolEnchantability(23);
   public static Item SAPPHIRESHOVEL = new ItemBasicTool("sapphire_shovel", CreativeTabs.TOOLS, 260, 1, true)
      .setHarvestLvl("shovel", 2, 7.5F)
      .setItemToRepair(SAPPHIRE)
      .setToolEnchantability(23);
   public static Item CITRINEPICKAXE = new ItemBasicTool("citrine_pickaxe", CreativeTabs.TOOLS, 260, 1, true)
      .setHarvestLvl("pickaxe", 2, 7.5F)
      .setItemToRepair(CITRINE)
      .setToolEnchantability(23);
   public static Item CITRINEAXE = new ItemBasicTool("citrine_axe", CreativeTabs.TOOLS, 260, 1, true)
      .setHarvestLvl("axe", 2, 7.5F)
      .setItemToRepair(CITRINE)
      .setToolEnchantability(23);
   public static Item CITRINESHOVEL = new ItemBasicTool("citrine_shovel", CreativeTabs.TOOLS, 260, 1, true)
      .setHarvestLvl("shovel", 2, 7.5F)
      .setItemToRepair(CITRINE)
      .setToolEnchantability(23);
   public static Item AMETHYSTPICKAXE = new ItemBasicTool("amethyst_pickaxe", CreativeTabs.TOOLS, 200, 1, true)
      .setHarvestLvl("pickaxe", 1, 7.0F)
      .setItemToRepair(AMETHYST)
      .setToolEnchantability(23);
   public static Item AMETHYSTAXE = new ItemBasicTool("amethyst_axe", CreativeTabs.TOOLS, 200, 1, true)
      .setHarvestLvl("axe", 1, 7.0F)
      .setItemToRepair(AMETHYST)
      .setToolEnchantability(23);
   public static Item AMETHYSTSHOVEL = new ItemBasicTool("amethyst_shovel", CreativeTabs.TOOLS, 200, 1, true)
      .setHarvestLvl("shovel", 1, 7.0F)
      .setItemToRepair(AMETHYST)
      .setToolEnchantability(23);
   public static Item TOPAZPICKAXE = new ItemBasicTool("topaz_pickaxe", CreativeTabs.TOOLS, 200, 1, true)
      .setHarvestLvl("pickaxe", 1, 7.0F)
      .setItemToRepair(TOPAZ)
      .setToolEnchantability(23);
   public static Item TOPAZAXE = new ItemBasicTool("topaz_axe", CreativeTabs.TOOLS, 200, 1, true)
      .setHarvestLvl("axe", 1, 7.0F)
      .setItemToRepair(TOPAZ)
      .setToolEnchantability(23);
   public static Item TOPAZSHOVEL = new ItemBasicTool("topaz_shovel", CreativeTabs.TOOLS, 200, 1, true)
      .setHarvestLvl("shovel", 1, 7.0F)
      .setItemToRepair(TOPAZ)
      .setToolEnchantability(23);
   public static Item RHINESTONEPICKAXE = new ItemBasicTool("rhinestone_pickaxe", CreativeTabs.TOOLS, 140, 1, true)
      .setHarvestLvl("pickaxe", 1, 6.5F)
      .setItemToRepair(RHINESTONE)
      .setToolEnchantability(23);
   public static Item RHINESTONEAXE = new ItemBasicTool("rhinestone_axe", CreativeTabs.TOOLS, 140, 1, true)
      .setHarvestLvl("axe", 1, 6.5F)
      .setItemToRepair(RHINESTONE)
      .setToolEnchantability(23);
   public static Item RHINESTONESHOVEL = new ItemBasicTool("rhinestone_shovel", CreativeTabs.TOOLS, 140, 1, true)
      .setHarvestLvl("shovel", 1, 6.5F)
      .setItemToRepair(RHINESTONE)
      .setToolEnchantability(23);
   public static Item FORGETPICKAXE = new ItemBasicTool("forget_pickaxe", CreativeTabs.TOOLS, 1800, 1, true)
      .setHarvestLvl("pickaxe", 4, 20.0F)
      .setItemToRepair(INGOTINFERNUM)
      .setToolEnchantability(15);
   public static Item FORGETAXE = new ItemBasicTool("forget_axe", CreativeTabs.TOOLS, 1300, 1, true)
      .setHarvestLvl("axe", 4, 20.0F)
      .setItemToRepair(INGOTINFERNUM)
      .setToolEnchantability(15);
   public static Item FORGETSHOVEL = new ItemBasicTool("forget_shovel", CreativeTabs.TOOLS, 1200, 1, true)
      .setHarvestLvl("shovel", 4, 20.0F)
      .setItemToRepair(INGOTINFERNUM)
      .setToolEnchantability(15);
   public static Item GOTHICPICKAXE = new ItemBasicTool("gothic_pickaxe", CreativeTabs.TOOLS, 1500, 1, true)
      .setHarvestLvl("pickaxe", 5, 30.0F)
      .setToolEnchantability(17);
   public static Item GOTHICAXE = new ItemBasicTool("gothic_axe", CreativeTabs.TOOLS, 1500, 1, true)
      .setHarvestLvl("axe", 5, 30.0F)
      .setToolEnchantability(17);
   public static Item GOTHICSHOVEL = new ItemBasicTool("gothic_shovel", CreativeTabs.TOOLS, 1500, 1, true)
      .setHarvestLvl("shovel", 5, 30.0F)
      .setToolEnchantability(17);
   public static Item ARSENICPICKAXE = new ItemBasicTool("arsenic_pickaxe", CreativeTabs.TOOLS, 2050, 1, true)
      .setHarvestLvl("pickaxe", 8, 40.0F)
      .setItemToRepair(INGOTARSENIC)
      .setToolEnchantability(8);
   public static Item ARSENICAXE = new ItemBasicTool("arsenic_axe", CreativeTabs.TOOLS, 2100, 1, true)
      .setHarvestLvl("axe", 8, 40.0F)
      .setItemToRepair(INGOTARSENIC)
      .setToolEnchantability(8);
   public static Item ARSENICSHOVEL = new ItemBasicTool("arsenic_shovel", CreativeTabs.TOOLS, 2000, 1, true)
      .setHarvestLvl("shovel", 8, 40.0F)
      .setItemToRepair(INGOTARSENIC)
      .setToolEnchantability(8);
   public static Item TOXINIUMPICKAXE = new ItemBasicTool("toxinium_pickaxe", CreativeTabs.TOOLS, 4000, 1, true)
      .setHarvestLvl("pickaxe", 9, 47.0F)
      .setItemToRepair(INGOTTOXINIUM)
      .setToolEnchantability(10);
   public static Item TOXINIUMAXE = new ItemBasicTool("toxinium_axe", CreativeTabs.TOOLS, 4000, 1, true)
      .setHarvestLvl("axe", 9, 47.0F)
      .setItemToRepair(INGOTTOXINIUM)
      .setToolEnchantability(10);
   public static Item TOXINIUMSHOVEL = new ItemBasicTool("toxinium_shovel", CreativeTabs.TOOLS, 4000, 1, true)
      .setHarvestLvl("shovel", 9, 47.0F)
      .setItemToRepair(INGOTTOXINIUM)
      .setToolEnchantability(10);
   public static Item ADAMANTIUMPICKAXE = new ItemBasicTool("adamantium_pickaxe", CreativeTabs.TOOLS, 5000, 1, true)
      .setHarvestLvl("pickaxe", 10, 55.0F)
      .setItemToRepair(INGOTADAMANTIUM)
      .setToolEnchantability(12);
   public static Item ADAMANTIUMAXE = new ItemBasicTool("adamantium_axe", CreativeTabs.TOOLS, 5000, 1, true)
      .setHarvestLvl("axe", 10, 55.0F)
      .setItemToRepair(INGOTADAMANTIUM)
      .setToolEnchantability(12);
   public static Item ADAMANTIUMSHOVEL = new ItemBasicTool("adamantium_shovel", CreativeTabs.TOOLS, 5000, 1, true)
      .setHarvestLvl("shovel", 10, 55.0F)
      .setItemToRepair(INGOTADAMANTIUM)
      .setToolEnchantability(12);
   public static Item AQUATICPICKAXE = new ItemBasicTool("aquatic_pickaxe", CreativeTabs.TOOLS, 6000, 1, true)
      .setHarvestLvl("pickaxe", 11, 67.0F)
      .setItemToRepair(INGOTAQUATIC)
      .setToolEnchantability(20);
   public static Item AQUATICAXE = new ItemBasicTool("aquatic_axe", CreativeTabs.TOOLS, 6000, 1, true)
      .setHarvestLvl("axe", 11, 67.0F)
      .setItemToRepair(INGOTAQUATIC)
      .setToolEnchantability(20);
   public static Item AQUATICSHOVEL = new ItemBasicTool("aquatic_shovel", CreativeTabs.TOOLS, 6000, 1, true)
      .setHarvestLvl("shovel", 11, 67.0F)
      .setItemToRepair(INGOTAQUATIC)
      .setToolEnchantability(20);
   public static Item STORMPICKAXE = new ItemBasicTool("storm_pickaxe", CreativeTabs.TOOLS, 7000, 1, true)
      .setHarvestLvl("pickaxe", 12, 85.0F)
      .setItemToRepair(PHASEOLITE)
      .setToolEnchantability(20);
   public static Item STORMAXE = new ItemBasicTool("storm_axe", CreativeTabs.TOOLS, 7000, 1, true)
      .setHarvestLvl("axe", 12, 85.0F)
      .setItemToRepair(PHASEOLITE)
      .setToolEnchantability(20);
   public static Item STORMSHOVEL = new ItemBasicTool("storm_shovel", CreativeTabs.TOOLS, 7000, 1, true)
      .setHarvestLvl("shovel", 12, 85.0F)
      .setItemToRepair(PHASEOLITE)
      .setToolEnchantability(20);
   public static MiningTools toolsDiamond = new MiningTools(
      new ResourceLocation("arpg:textures/mining_tools_diamond.png"),
      "tools_diamond",
      3,
      0.15F,
      "diamond_drill",
      "diamond_chainsaw",
      ItemAccumulator.LEAD_ACID_CAPACITY,
      ItemAccumulator.LEAD_ACID_THROUGHPUT,
      200,
      2048,
      1000,
      new MiningTools.LaserDigger("laser_digger", 5120, ItemAccumulator.LEAD_ACID_CAPACITY * 2, ItemAccumulator.LEAD_ACID_THROUGHPUT, 180),
      new Vec3d(1.0, 0.0, 1.0)
   );
   public static Item DIAMONDDRILLELECTRIC = toolsDiamond.electricDrill;
   public static Item DIAMONDDRILLFUEL = toolsDiamond.fuelDrill;
   public static Item DIAMONDCHAINSAWELECTRIC = toolsDiamond.electricChainsaw;
   public static Item DIAMONDCHAINSAWFUEL = toolsDiamond.fuelChainsaw;
   public static Item LASERDIGGER = toolsDiamond.miningLaser;
   public static MiningTools toolsToxinium = new MiningTools(
      new ResourceLocation("arpg:textures/mining_tools_toxinium.png"),
      "tools_toxinium",
      9,
      1.3F,
      "toxinium_drill",
      "toxinium_chainsaw",
      ItemAccumulator.LI_ION_CAPACITY * 2,
      ItemAccumulator.LI_ION_THROUGHPUT,
      270,
      5000,
      4000,
      new MiningTools.NuclearMiningRay("nuclear_mining_ray", 12500, ItemAccumulator.LI_ION_CAPACITY * 3, ItemAccumulator.LI_ION_THROUGHPUT, 210),
      new Vec3d(0.0, 1.0, 0.0)
   );
   public static Item TOXINIUMDRILLELECTRIC = toolsToxinium.electricDrill;
   public static Item TOXINIUMDRILLFUEL = toolsToxinium.fuelDrill;
   public static Item TOXINIUMCHAINSAWELECTRIC = toolsToxinium.electricChainsaw;
   public static Item TOXINIUMCHAINSAWFUEL = toolsToxinium.fuelChainsaw;
   public static Item NUCLEARMININGRAY = toolsToxinium.miningLaser;
   public static MiningTools toolsAdamantium = new MiningTools(
      new ResourceLocation("arpg:textures/mining_tools_adamantium.png"),
      "tools_adamantium",
      10,
      1.4F,
      "adamantium_drill",
      "adamantium_chainsaw",
      ItemAccumulator.ANCIENT_CAPACITY,
      ItemAccumulator.ANCIENT_THROUGHPUT,
      340,
      6000,
      6000,
      new MiningTools.EyelightProspector("eyelight_prospector", 15000, ItemAccumulator.ANCIENT_CAPACITY * 2, ItemAccumulator.ANCIENT_THROUGHPUT, 240),
      new Vec3d(0.78, 0.87, 0.93)
   );
   public static Item ADAMANTIUMDRILLELECTRIC = toolsAdamantium.electricDrill;
   public static Item ADAMANTIUMDRILLFUEL = toolsAdamantium.fuelDrill;
   public static Item ADAMANTIUMCHAINSAWELECTRIC = toolsAdamantium.electricChainsaw;
   public static Item ADAMANTIUMCHAINSAWFUEL = toolsAdamantium.fuelChainsaw;
   public static Item EYELIGHTPROSPECTOR = toolsAdamantium.miningLaser;
   public static MiningTools toolsAquatic = new MiningTools(
      new ResourceLocation("arpg:textures/mining_tools_aquatic.png"),
      "tools_aquatic",
      11,
      1.6F,
      "aquatic_drill",
      "aquatic_chainsaw",
      ItemAccumulator.AQUATRONIC_CAPACITY,
      ItemAccumulator.AQUATRONIC_THROUGHPUT,
      400,
      7000,
      8000,
      new MiningTools.CorrosiveWaterblaster("corrosive_waterblaster", 15000, 8000, 1),
      new Vec3d(0.2, 0.73, 1.0)
   );
   public static Item AQUATICDRILLELECTRIC = toolsAquatic.electricDrill;
   public static Item AQUATICDRILLFUEL = toolsAquatic.fuelDrill;
   public static Item AQUATICCHAINSAWELECTRIC = toolsAquatic.electricChainsaw;
   public static Item AQUATICCHAINSAWFUEL = toolsAquatic.fuelChainsaw;
   public static Item CORROSIVEWATERBLASTER = toolsAquatic.miningLaser;
   public static MiningTools toolsStorm = new MiningTools(
      new ResourceLocation("arpg:textures/mining_tools_storm.png"),
      "tools_storm",
      12,
      1.9F,
      "storm_drill",
      "storm_chainsaw",
      ItemAccumulator.TOPAZITRON_CAPACITY,
      ItemAccumulator.TOPAZITRON_THROUGHPUT,
      450,
      8000,
      10000,
      new MiningTools.PlasmaCutter("plasma_cutter", 15000, ItemAccumulator.TOPAZITRON_CAPACITY * 2, ItemAccumulator.TOPAZITRON_THROUGHPUT, 320),
      new Vec3d(0.69, 0.58, 0.9)
   );
   public static Item STORMDRILLELECTRIC = toolsStorm.electricDrill;
   public static Item STORMDRILLFUEL = toolsStorm.fuelDrill;
   public static Item STORMCHAINSAWELECTRIC = toolsStorm.electricChainsaw;
   public static Item STORMCHAINSAWFUEL = toolsStorm.fuelChainsaw;
   public static Item PLASMACUTTER = toolsStorm.miningLaser;

   public static void registerItemsRender() {
      ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
      CreateItemFile.ResLocationCreate(MODULEBIOFILTERING);
      CreateItemFile.ResLocationCreate(RICHSCRAP);
      mesher.register(RICHSCRAP, 0, new ModelResourceLocation(RICHSCRAP.getRegistryName(), "inventory"));
      mesher.register(MODULEBIOFILTERING, 0, new ModelResourceLocation(MODULEBIOFILTERING.getRegistryName(), "inventory"));
      mesher.register(SWARMETER, 0, new ModelResourceLocation(SWARMETER.getRegistryName(), "inventory"));
      mesher.register(WIZARDCLOTH, 0, new ModelResourceLocation(WIZARDCLOTH.getRegistryName(), "inventory"));
      mesher.register(WEATHERROCKETPOISONRAIN, 0, new ModelResourceLocation(WEATHERROCKETPOISONRAIN.getRegistryName(), "inventory"));
      mesher.register(WEATHERROCKETRAINFALL, 0, new ModelResourceLocation(WEATHERROCKETRAINFALL.getRegistryName(), "inventory"));
      mesher.register(WEATHERROCKETSTORM, 0, new ModelResourceLocation(WEATHERROCKETSTORM.getRegistryName(), "inventory"));
      mesher.register(WEATHERROCKETRAINSTORM, 0, new ModelResourceLocation(WEATHERROCKETRAINSTORM.getRegistryName(), "inventory"));
      mesher.register(TOXINIUMPICKAXE, 0, new ModelResourceLocation(TOXINIUMPICKAXE.getRegistryName(), "inventory"));
      mesher.register(TOXINIUMAXE, 0, new ModelResourceLocation(TOXINIUMAXE.getRegistryName(), "inventory"));
      mesher.register(TOXINIUMSHOVEL, 0, new ModelResourceLocation(TOXINIUMSHOVEL.getRegistryName(), "inventory"));
      mesher.register(ADAMANTIUMPICKAXE, 0, new ModelResourceLocation(ADAMANTIUMPICKAXE.getRegistryName(), "inventory"));
      mesher.register(ADAMANTIUMAXE, 0, new ModelResourceLocation(ADAMANTIUMAXE.getRegistryName(), "inventory"));
      mesher.register(ADAMANTIUMSHOVEL, 0, new ModelResourceLocation(ADAMANTIUMSHOVEL.getRegistryName(), "inventory"));
      mesher.register(AQUATICPICKAXE, 0, new ModelResourceLocation(AQUATICPICKAXE.getRegistryName(), "inventory"));
      mesher.register(AQUATICAXE, 0, new ModelResourceLocation(AQUATICAXE.getRegistryName(), "inventory"));
      mesher.register(AQUATICSHOVEL, 0, new ModelResourceLocation(AQUATICSHOVEL.getRegistryName(), "inventory"));
      mesher.register(STORMPICKAXE, 0, new ModelResourceLocation(STORMPICKAXE.getRegistryName(), "inventory"));
      mesher.register(STORMAXE, 0, new ModelResourceLocation(STORMAXE.getRegistryName(), "inventory"));
      mesher.register(STORMSHOVEL, 0, new ModelResourceLocation(STORMSHOVEL.getRegistryName(), "inventory"));
      mesher.register(DOLERITEKEY, 0, new ModelResourceLocation(DOLERITEKEY.getRegistryName(), "inventory"));
      mesher.register(FIBERBANDAGE, 0, new ModelResourceLocation(FIBERBANDAGE.getRegistryName(), "inventory"));
      mesher.register(SELECTIVELEVITATOR, 0, new ModelResourceLocation(SELECTIVELEVITATOR.getRegistryName(), "inventory"));
      mesher.register(STORMSPAWNERPIECE, 0, new ModelResourceLocation(STORMSPAWNERPIECE.getRegistryName(), "inventory"));
      mesher.register(SACRIFICIALDAGGER, 0, new ModelResourceLocation(SACRIFICIALDAGGER.getRegistryName(), "inventory"));
      mesher.register(SIRENKEY, 0, new ModelResourceLocation(SIRENKEY.getRegistryName(), "inventory"));
      mesher.register(MERMAIDMEDALLION, 0, new ModelResourceLocation(MERMAIDMEDALLION.getRegistryName(), "inventory"));
      mesher.register(WEATHERROCKETCLEAR, 0, new ModelResourceLocation(WEATHERROCKETCLEAR.getRegistryName(), "inventory"));
      mesher.register(WEATHERROCKETSNOWFALL, 0, new ModelResourceLocation(WEATHERROCKETSNOWFALL.getRegistryName(), "inventory"));
      mesher.register(WEATHERROCKETHAIL, 0, new ModelResourceLocation(WEATHERROCKETHAIL.getRegistryName(), "inventory"));
      mesher.register(WEATHERROCKETAURORA, 0, new ModelResourceLocation(WEATHERROCKETAURORA.getRegistryName(), "inventory"));
      mesher.register(PIRATESEXTANT, 0, new ModelResourceLocation(PIRATESEXTANT.getRegistryName(), "inventory"));
      mesher.register(CIRCUITAQUATIC, 0, new ModelResourceLocation(CIRCUITAQUATIC.getRegistryName(), "inventory"));
      mesher.register(TIDALHEART, 0, new ModelResourceLocation(TIDALHEART.getRegistryName(), "inventory"));
      mesher.register(STABILIZATIONCELL, 0, new ModelResourceLocation(STABILIZATIONCELL.getRegistryName(), "inventory"));
      mesher.register(SCRAPBOMB, 0, new ModelResourceLocation(SCRAPBOMB.getRegistryName(), "inventory"));
      mesher.register(HEALTHFULCAPSULE, 0, new ModelResourceLocation(HEALTHFULCAPSULE.getRegistryName(), "inventory"));
      mesher.register(GEIGERCOUNTER, 0, new ModelResourceLocation(GEIGERCOUNTER.getRegistryName(), "inventory"));
      mesher.register(THUNDERBIRDFEATHER, 0, new ModelResourceLocation(THUNDERBIRDFEATHER.getRegistryName(), "inventory"));
      mesher.register(PIZZASEAFOOD, 0, new ModelResourceLocation(PIZZASEAFOOD.getRegistryName(), "inventory"));
      mesher.register(PALEMEATRAW, 0, new ModelResourceLocation(PALEMEATRAW.getRegistryName(), "inventory"));
      mesher.register(PALEMEATSMOKED, 0, new ModelResourceLocation(PALEMEATSMOKED.getRegistryName(), "inventory"));
      mesher.register(FISHSTEAKRAW, 0, new ModelResourceLocation(FISHSTEAKRAW.getRegistryName(), "inventory"));
      mesher.register(FISHSTEAKROASTED, 0, new ModelResourceLocation(FISHSTEAKROASTED.getRegistryName(), "inventory"));
      mesher.register(BLACKGOO, 0, new ModelResourceLocation(BLACKGOO.getRegistryName(), "inventory"));
      mesher.register(FIBERCLOTH, 0, new ModelResourceLocation(FIBERCLOTH.getRegistryName(), "inventory"));
      mesher.register(ANTIRADPLATING, 0, new ModelResourceLocation(ANTIRADPLATING.getRegistryName(), "inventory"));
      mesher.register(PHASEOLITE, 0, new ModelResourceLocation(PHASEOLITE.getRegistryName(), "inventory"));
      mesher.register(ETHERITEFUELCELL, 0, new ModelResourceLocation(ETHERITEFUELCELL.getRegistryName(), "inventory"));
      mesher.register(VITREOUSHEART, 0, new ModelResourceLocation(VITREOUSHEART.getRegistryName(), "inventory"));
      mesher.register(SOLIDIFIEDLIGHTNING, 0, new ModelResourceLocation(SOLIDIFIEDLIGHTNING.getRegistryName(), "inventory"));
      mesher.register(KRAKENSKIN, 0, new ModelResourceLocation(KRAKENSKIN.getRegistryName(), "inventory"));
      mesher.register(WORSHIPPERSBAIT, 0, new ModelResourceLocation(WORSHIPPERSBAIT.getRegistryName(), "inventory"));
      mesher.register(FINWINGS, 0, new ModelResourceLocation(FINWINGS.getRegistryName(), "inventory"));
      mesher.register(BATTERYANCIENT, 0, new ModelResourceLocation(BATTERYANCIENT.getRegistryName(), "inventory"));
      mesher.register(BATTERYAQUATRONIC, 0, new ModelResourceLocation(BATTERYAQUATRONIC.getRegistryName(), "inventory"));
      mesher.register(ANCIENTSPAWNERPIECE, 0, new ModelResourceLocation(ANCIENTSPAWNERPIECE.getRegistryName(), "inventory"));
      mesher.register(EYEOFBEHOLDER, 0, new ModelResourceLocation(EYEOFBEHOLDER.getRegistryName(), "inventory"));
      mesher.register(THUNDERBIRDWINGS, 0, new ModelResourceLocation(THUNDERBIRDWINGS.getRegistryName(), "inventory"));
      mesher.register(WINDKEEPER, 0, new ModelResourceLocation(WINDKEEPER.getRegistryName(), "inventory"));
      mesher.register(SHIPWREAKERSKNOT, 0, new ModelResourceLocation(SHIPWREAKERSKNOT.getRegistryName(), "inventory"));
      mesher.register(WHITEWINDBELT, 0, new ModelResourceLocation(WHITEWINDBELT.getRegistryName(), "inventory"));
      mesher.register(BELTOFSHADOWS, 0, new ModelResourceLocation(BELTOFSHADOWS.getRegistryName(), "inventory"));
      mesher.register(EREBRISSHARD, 0, new ModelResourceLocation(EREBRISSHARD.getRegistryName(), "inventory"));
      mesher.register(EREBRISFRAGMENT, 0, new ModelResourceLocation(EREBRISFRAGMENT.getRegistryName(), "inventory"));
      mesher.register(EREBRISCHUNK, 0, new ModelResourceLocation(EREBRISCHUNK.getRegistryName(), "inventory"));
      mesher.register(SILICIUM, 0, new ModelResourceLocation(SILICIUM.getRegistryName(), "inventory"));
      mesher.register(SILICIUMWAFER, 0, new ModelResourceLocation(SILICIUMWAFER.getRegistryName(), "inventory"));
      mesher.register(PHOTORESISTEDPLATE, 0, new ModelResourceLocation(PHOTORESISTEDPLATE.getRegistryName(), "inventory"));
      mesher.register(LITOGRAPHEDPLATE, 0, new ModelResourceLocation(LITOGRAPHEDPLATE.getRegistryName(), "inventory"));
      mesher.register(GALVANIZEDPLATE, 0, new ModelResourceLocation(GALVANIZEDPLATE.getRegistryName(), "inventory"));
      mesher.register(LEPIDOLITE, 0, new ModelResourceLocation(LEPIDOLITE.getRegistryName(), "inventory"));
      mesher.register(NUGGETLITHIUM, 0, new ModelResourceLocation(NUGGETLITHIUM.getRegistryName(), "inventory"));
      mesher.register(INGOTLITHIUM, 0, new ModelResourceLocation(INGOTLITHIUM.getRegistryName(), "inventory"));
      mesher.register(DUSTLITHIUM, 0, new ModelResourceLocation(DUSTLITHIUM.getRegistryName(), "inventory"));
      mesher.register(VACUUMGUNPELLETS, 0, new ModelResourceLocation(VACUUMGUNPELLETS.getRegistryName(), "inventory"));
      mesher.register(BEARINGLOWFRICTION, 0, new ModelResourceLocation(BEARINGLOWFRICTION.getRegistryName(), "inventory"));
      mesher.register(CHARMOFUNDYING, 0, new ModelResourceLocation(CHARMOFUNDYING.getRegistryName(), "inventory"));
      mesher.register(GOTHICGEM, 0, new ModelResourceLocation(GOTHICGEM.getRegistryName(), "inventory"));
      mesher.register(HYDRAULICSHOTGUNCLIP, 0, new ModelResourceLocation(HYDRAULICSHOTGUNCLIP.getRegistryName(), "inventory"));
      mesher.register(GOTHICGEAR, 0, new ModelResourceLocation(GOTHICGEAR.getRegistryName(), "inventory"));
      mesher.register(ICECIRCLE, 0, new ModelResourceLocation(ICECIRCLE.getRegistryName(), "inventory"));
      mesher.register(ICECIRCLEFILLED, 0, new ModelResourceLocation(ICECIRCLEFILLED.getRegistryName(), "inventory"));
      mesher.register(HEALTHFRUIT, 0, new ModelResourceLocation(HEALTHFRUIT.getRegistryName(), "inventory"));
      mesher.register(MANAEXPANSIONPOTION, 0, new ModelResourceLocation(MANAEXPANSIONPOTION.getRegistryName(), "inventory"));
      mesher.register(RUBBLESTONE, 0, new ModelResourceLocation(RUBBLESTONE.getRegistryName(), "inventory"));
      mesher.register(SALTPETER, 0, new ModelResourceLocation(SALTPETER.getRegistryName(), "inventory"));
      mesher.register(ASH, 0, new ModelResourceLocation(ASH.getRegistryName(), "inventory"));
      mesher.register(SLIMELOCATOR, 0, new ModelResourceLocation(SLIMELOCATOR.getRegistryName(), "inventory"));
      mesher.register(RHINESTONEPICKAXE, 0, new ModelResourceLocation(RHINESTONEPICKAXE.getRegistryName(), "inventory"));
      mesher.register(RHINESTONEAXE, 0, new ModelResourceLocation(RHINESTONEAXE.getRegistryName(), "inventory"));
      mesher.register(RHINESTONESHOVEL, 0, new ModelResourceLocation(RHINESTONESHOVEL.getRegistryName(), "inventory"));
      mesher.register(TOPAZPICKAXE, 0, new ModelResourceLocation(TOPAZPICKAXE.getRegistryName(), "inventory"));
      mesher.register(TOPAZAXE, 0, new ModelResourceLocation(TOPAZAXE.getRegistryName(), "inventory"));
      mesher.register(TOPAZSHOVEL, 0, new ModelResourceLocation(TOPAZSHOVEL.getRegistryName(), "inventory"));
      mesher.register(SAPPHIREPICKAXE, 0, new ModelResourceLocation(SAPPHIREPICKAXE.getRegistryName(), "inventory"));
      mesher.register(SAPPHIREAXE, 0, new ModelResourceLocation(SAPPHIREAXE.getRegistryName(), "inventory"));
      mesher.register(SAPPHIRESHOVEL, 0, new ModelResourceLocation(SAPPHIRESHOVEL.getRegistryName(), "inventory"));
      mesher.register(AMETHYSTPICKAXE, 0, new ModelResourceLocation(AMETHYSTPICKAXE.getRegistryName(), "inventory"));
      mesher.register(AMETHYSTAXE, 0, new ModelResourceLocation(AMETHYSTAXE.getRegistryName(), "inventory"));
      mesher.register(AMETHYSTSHOVEL, 0, new ModelResourceLocation(AMETHYSTSHOVEL.getRegistryName(), "inventory"));
      mesher.register(CITRINEPICKAXE, 0, new ModelResourceLocation(CITRINEPICKAXE.getRegistryName(), "inventory"));
      mesher.register(CITRINEAXE, 0, new ModelResourceLocation(CITRINEAXE.getRegistryName(), "inventory"));
      mesher.register(CITRINESHOVEL, 0, new ModelResourceLocation(CITRINESHOVEL.getRegistryName(), "inventory"));
      mesher.register(RUBYPICKAXE, 0, new ModelResourceLocation(RUBYPICKAXE.getRegistryName(), "inventory"));
      mesher.register(RUBYAXE, 0, new ModelResourceLocation(RUBYAXE.getRegistryName(), "inventory"));
      mesher.register(RUBYSHOVEL, 0, new ModelResourceLocation(RUBYSHOVEL.getRegistryName(), "inventory"));
      mesher.register(MISSINGDUST, 0, new ModelResourceLocation(MISSINGDUST.getRegistryName(), "inventory"));
      mesher.register(MISSINGINGOT, 0, new ModelResourceLocation(MISSINGINGOT.getRegistryName(), "inventory"));
      mesher.register(MISSINGNUGGET, 0, new ModelResourceLocation(MISSINGNUGGET.getRegistryName(), "inventory"));
      mesher.register(MISSINGMATERIAL, 0, new ModelResourceLocation(MISSINGMATERIAL.getRegistryName(), "inventory"));
      mesher.register(INK, 0, new ModelResourceLocation(INK.getRegistryName(), "inventory"));
      mesher.register(DUSTQUARTZ, 0, new ModelResourceLocation(DUSTQUARTZ.getRegistryName(), "inventory"));
      mesher.register(EMPTYSYRINGE, 0, new ModelResourceLocation(EMPTYSYRINGE.getRegistryName(), "inventory"));
      mesher.register(MODULEPYROLYSIS, 0, new ModelResourceLocation(MODULEPYROLYSIS.getRegistryName(), "inventory"));
      mesher.register(INGOTPURPURALLOY, 0, new ModelResourceLocation(INGOTPURPURALLOY.getRegistryName(), "inventory"));
      mesher.register(BLACKSTRAP, 0, new ModelResourceLocation(BLACKSTRAP.getRegistryName(), "inventory"));
      mesher.register(TITANIUMSLAG, 0, new ModelResourceLocation(TITANIUMSLAG.getRegistryName(), "inventory"));
      mesher.register(AQUATICSPAWNERPIECE, 0, new ModelResourceLocation(AQUATICSPAWNERPIECE.getRegistryName(), "inventory"));
      mesher.register(DUSTADAMANTIUMORE, 0, new ModelResourceLocation(DUSTADAMANTIUMORE.getRegistryName(), "inventory"));
      mesher.register(DUSTMITHRILORE, 0, new ModelResourceLocation(DUSTMITHRILORE.getRegistryName(), "inventory"));
      mesher.register(DUSTSTONE, 0, new ModelResourceLocation(DUSTSTONE.getRegistryName(), "inventory"));
      mesher.register(DUSTLIMESTONE, 0, new ModelResourceLocation(DUSTLIMESTONE.getRegistryName(), "inventory"));
      mesher.register(DUSTBASALT, 0, new ModelResourceLocation(DUSTBASALT.getRegistryName(), "inventory"));
      mesher.register(INGOTURANIUM, 0, new ModelResourceLocation(INGOTURANIUM.getRegistryName(), "inventory"));
      mesher.register(DUSTURANIUM, 0, new ModelResourceLocation(DUSTURANIUM.getRegistryName(), "inventory"));
      mesher.register(NUGGETURANIUM, 0, new ModelResourceLocation(NUGGETURANIUM.getRegistryName(), "inventory"));
      mesher.register(DUSTTOXINIUMORE, 0, new ModelResourceLocation(DUSTTOXINIUMORE.getRegistryName(), "inventory"));
      mesher.register(DUSTRADIOACTIVESTONE, 0, new ModelResourceLocation(DUSTRADIOACTIVESTONE.getRegistryName(), "inventory"));
      mesher.register(NYLON, 0, new ModelResourceLocation(NYLON.getRegistryName(), "inventory"));
      mesher.register(TAR, 0, new ModelResourceLocation(TAR.getRegistryName(), "inventory"));
      mesher.register(PARAFFIN, 0, new ModelResourceLocation(PARAFFIN.getRegistryName(), "inventory"));
      mesher.register(MODULEPOLYMERIZATION, 0, new ModelResourceLocation(MODULEPOLYMERIZATION.getRegistryName(), "inventory"));
      mesher.register(MODULEDISTILLATION, 0, new ModelResourceLocation(MODULEDISTILLATION.getRegistryName(), "inventory"));
      mesher.register(CIRCUITRESISTANT, 0, new ModelResourceLocation(CIRCUITRESISTANT.getRegistryName(), "inventory"));
      mesher.register(GOLDTRANSFORMER, 0, new ModelResourceLocation(GOLDTRANSFORMER.getRegistryName(), "inventory"));
      mesher.register(SILVERTRANSFORMER, 0, new ModelResourceLocation(SILVERTRANSFORMER.getRegistryName(), "inventory"));
      mesher.register(TOPAZITRONCRYSTAL, 0, new ModelResourceLocation(TOPAZITRONCRYSTAL.getRegistryName(), "inventory"));
      mesher.register(BATTERYTOPAZITRONCRYSTAL, 0, new ModelResourceLocation(BATTERYTOPAZITRONCRYSTAL.getRegistryName(), "inventory"));
      mesher.register(BEARINGELECTROMAGNETIC, 0, new ModelResourceLocation(BEARINGELECTROMAGNETIC.getRegistryName(), "inventory"));
      mesher.register(BLUEARTHROSTELECHAROD, 0, new ModelResourceLocation(BLUEARTHROSTELECHAROD.getRegistryName(), "inventory"));
      mesher.register(PINKARTHROSTELECHAROD, 0, new ModelResourceLocation(PINKARTHROSTELECHAROD.getRegistryName(), "inventory"));
      mesher.register(IMPULSETHRUSTER, 0, new ModelResourceLocation(IMPULSETHRUSTER.getRegistryName(), "inventory"));
      mesher.register(CIRCUITSTORM, 0, new ModelResourceLocation(CIRCUITSTORM.getRegistryName(), "inventory"));
      mesher.register(STORMBRASSPLASMATRON, 0, new ModelResourceLocation(STORMBRASSPLASMATRON.getRegistryName(), "inventory"));
      mesher.register(INGOTMITHRIL, 0, new ModelResourceLocation(INGOTMITHRIL.getRegistryName(), "inventory"));
      mesher.register(DUSTMITHRIL, 0, new ModelResourceLocation(DUSTMITHRIL.getRegistryName(), "inventory"));
      mesher.register(NUGGETMITHRIL, 0, new ModelResourceLocation(NUGGETMITHRIL.getRegistryName(), "inventory"));
      mesher.register(NUGGETAQUATIC, 0, new ModelResourceLocation(NUGGETAQUATIC.getRegistryName(), "inventory"));
      mesher.register(PEARL, 0, new ModelResourceLocation(PEARL.getRegistryName(), "inventory"));
      mesher.register(PEARLBLACK, 0, new ModelResourceLocation(PEARLBLACK.getRegistryName(), "inventory"));
      mesher.register(PEARLGLOWING, 0, new ModelResourceLocation(PEARLGLOWING.getRegistryName(), "inventory"));
      mesher.register(PEARLAQUATIC, 0, new ModelResourceLocation(PEARLAQUATIC.getRegistryName(), "inventory"));
      mesher.register(INGOTAQUATIC, 0, new ModelResourceLocation(INGOTAQUATIC.getRegistryName(), "inventory"));
      mesher.register(DUSTAQUATIC, 0, new ModelResourceLocation(DUSTAQUATIC.getRegistryName(), "inventory"));
      mesher.register(CORAL, 0, new ModelResourceLocation(CORAL.getRegistryName(), "inventory"));
      mesher.register(ARCHELONSHELL, 0, new ModelResourceLocation(ARCHELONSHELL.getRegistryName(), "inventory"));
      mesher.register(PLACODERMSCALES, 0, new ModelResourceLocation(PLACODERMSCALES.getRegistryName(), "inventory"));
      mesher.register(MESOGLEA, 0, new ModelResourceLocation(MESOGLEA.getRegistryName(), "inventory"));
      mesher.register(TOXEDGEDOUGH, 0, new ModelResourceLocation(TOXEDGEDOUGH.getRegistryName(), "inventory"));
      mesher.register(BUTTER, 0, new ModelResourceLocation(BUTTER.getRegistryName(), "inventory"));
      mesher.register(BISCUIT, 0, new ModelResourceLocation(BISCUIT.getRegistryName(), "inventory"));
      mesher.register(SWEETDOUGH, 0, new ModelResourceLocation(SWEETDOUGH.getRegistryName(), "inventory"));
      mesher.register(COCOAPOWDER, 0, new ModelResourceLocation(COCOAPOWDER.getRegistryName(), "inventory"));
      mesher.register(CARAMEL, 0, new ModelResourceLocation(CARAMEL.getRegistryName(), "inventory"));
      mesher.register(CHOCOLATE, 0, new ModelResourceLocation(CHOCOLATE.getRegistryName(), "inventory"));
      mesher.register(COCOABUTTER, 0, new ModelResourceLocation(COCOABUTTER.getRegistryName(), "inventory"));
      mesher.register(PUDDINGVIOLET, 0, new ModelResourceLocation(PUDDINGVIOLET.getRegistryName(), "inventory"));
      mesher.register(PUDDINGGREEN, 0, new ModelResourceLocation(PUDDINGGREEN.getRegistryName(), "inventory"));
      mesher.register(PUDDINGBROWN, 0, new ModelResourceLocation(PUDDINGBROWN.getRegistryName(), "inventory"));
      mesher.register(PUDDINGORANGE, 0, new ModelResourceLocation(PUDDINGORANGE.getRegistryName(), "inventory"));
      mesher.register(FLOUR, 0, new ModelResourceLocation(FLOUR.getRegistryName(), "inventory"));
      mesher.register(MEATBROTH, 0, new ModelResourceLocation(MEATBROTH.getRegistryName(), "inventory"));
      mesher.register(STUFFEDFIERYBEAN, 0, new ModelResourceLocation(STUFFEDFIERYBEAN.getRegistryName(), "inventory"));
      mesher.register(BORSCH, 0, new ModelResourceLocation(BORSCH.getRegistryName(), "inventory"));
      mesher.register(MODULEFERMENTER, 0, new ModelResourceLocation(MODULEFERMENTER.getRegistryName(), "inventory"));
      mesher.register(MODULECENTRIFUGE, 0, new ModelResourceLocation(MODULECENTRIFUGE.getRegistryName(), "inventory"));
      mesher.register(TOMATOCHERRY, 0, new ModelResourceLocation(TOMATOCHERRY.getRegistryName(), "inventory"));
      mesher.register(MAGICJAM, 0, new ModelResourceLocation(MAGICJAM.getRegistryName(), "inventory"));
      mesher.register(DOUGH, 0, new ModelResourceLocation(DOUGH.getRegistryName(), "inventory"));
      mesher.register(MOZZARELLA, 0, new ModelResourceLocation(MOZZARELLA.getRegistryName(), "inventory"));
      mesher.register(MUSHROOMSINSAUCE, 0, new ModelResourceLocation(MUSHROOMSINSAUCE.getRegistryName(), "inventory"));
      mesher.register(WHEYSTARTER, 0, new ModelResourceLocation(WHEYSTARTER.getRegistryName(), "inventory"));
      mesher.register(YEAST, 0, new ModelResourceLocation(YEAST.getRegistryName(), "inventory"));
      mesher.register(MOONSHROOMMEAT, 0, new ModelResourceLocation(MOONSHROOMMEAT.getRegistryName(), "inventory"));
      mesher.register(GRAINSSALT, 0, new ModelResourceLocation(GRAINSSALT.getRegistryName(), "inventory"));
      mesher.register(REDPEPPER, 0, new ModelResourceLocation(REDPEPPER.getRegistryName(), "inventory"));
      mesher.register(HEALTHBERRY, 0, new ModelResourceLocation(HEALTHBERRY.getRegistryName(), "inventory"));
      mesher.register(QUANTUMSLIMEBALL, 0, new ModelResourceLocation(QUANTUMSLIMEBALL.getRegistryName(), "inventory"));
      mesher.register(MANABERRY, 0, new ModelResourceLocation(MANABERRY.getRegistryName(), "inventory"));
      mesher.register(ALCHEMICALWAX, 0, new ModelResourceLocation(ALCHEMICALWAX.getRegistryName(), "inventory"));
      mesher.register(ENIGMATETWINS, 0, new ModelResourceLocation(ENIGMATETWINS.getRegistryName(), "inventory"));
      mesher.register(BULLETDIVERS, 0, new ModelResourceLocation(BULLETDIVERS.getRegistryName(), "inventory"));
      mesher.register(BULLETCORAL, 0, new ModelResourceLocation(BULLETCORAL.getRegistryName(), "inventory"));
      mesher.register(ROCKETSHELL, 0, new ModelResourceLocation(ROCKETSHELL.getRegistryName(), "inventory"));
      mesher.register(BULLETERRATIC, 0, new ModelResourceLocation(BULLETERRATIC.getRegistryName(), "inventory"));
      mesher.register(ROCKETSURPRISE, 0, new ModelResourceLocation(ROCKETSURPRISE.getRegistryName(), "inventory"));
      mesher.register(ADAMANTIUMMINIGUNCLIP, 0, new ModelResourceLocation(ADAMANTIUMMINIGUNCLIP.getRegistryName(), "inventory"));
      mesher.register(XMASSBUNDLE, 0, new ModelResourceLocation(XMASSBUNDLE.getRegistryName(), "inventory"));
      mesher.register(BOOKOFELEMENTS, 0, new ModelResourceLocation(BOOKOFELEMENTS.getRegistryName(), "inventory"));
      mesher.register(VIALEMPTY, 0, new ModelResourceLocation(VIALEMPTY.getRegistryName(), "inventory"));
      mesher.register(DASHBELTIMPULSECORSLET, 0, new ModelResourceLocation(DASHBELTIMPULSECORSLET.getRegistryName(), "inventory"));
      mesher.register(DASHBELTHELLHOUND, 0, new ModelResourceLocation(DASHBELTHELLHOUND.getRegistryName(), "inventory"));
      mesher.register(DASHBELTBLACK, 0, new ModelResourceLocation(DASHBELTBLACK.getRegistryName(), "inventory"));
      mesher.register(SHADOWWINGS, 0, new ModelResourceLocation(SHADOWWINGS.getRegistryName(), "inventory"));
      mesher.register(VIALFIRE, 0, new ModelResourceLocation(VIALFIRE.getRegistryName(), "inventory"));
      mesher.register(VIALEARTH, 0, new ModelResourceLocation(VIALEARTH.getRegistryName(), "inventory"));
      mesher.register(VIALWATER, 0, new ModelResourceLocation(VIALWATER.getRegistryName(), "inventory"));
      mesher.register(VIALAIR, 0, new ModelResourceLocation(VIALAIR.getRegistryName(), "inventory"));
      mesher.register(VIALPOISON, 0, new ModelResourceLocation(VIALPOISON.getRegistryName(), "inventory"));
      mesher.register(VIALCOLD, 0, new ModelResourceLocation(VIALCOLD.getRegistryName(), "inventory"));
      mesher.register(VIALELECTRIC, 0, new ModelResourceLocation(VIALELECTRIC.getRegistryName(), "inventory"));
      mesher.register(VIALVOID, 0, new ModelResourceLocation(VIALVOID.getRegistryName(), "inventory"));
      mesher.register(VIALPLEASURE, 0, new ModelResourceLocation(VIALPLEASURE.getRegistryName(), "inventory"));
      mesher.register(VIALPAIN, 0, new ModelResourceLocation(VIALPAIN.getRegistryName(), "inventory"));
      mesher.register(VIALDEATH, 0, new ModelResourceLocation(VIALDEATH.getRegistryName(), "inventory"));
      mesher.register(VIALLIVE, 0, new ModelResourceLocation(VIALLIVE.getRegistryName(), "inventory"));
      mesher.register(MAGICEXPLORINGKIT, 0, new ModelResourceLocation(MAGICEXPLORINGKIT.getRegistryName(), "inventory"));
      mesher.register(MAGICRESEARCHKIT, 0, new ModelResourceLocation(MAGICRESEARCHKIT.getRegistryName(), "inventory"));
      mesher.register(MAGICWRITINGKIT, 0, new ModelResourceLocation(MAGICWRITINGKIT.getRegistryName(), "inventory"));
      mesher.register(COOLEDRIFLECLIP, 0, new ModelResourceLocation(COOLEDRIFLECLIP.getRegistryName(), "inventory"));
      mesher.register(BULLETNIVEOUS, 0, new ModelResourceLocation(BULLETNIVEOUS.getRegistryName(), "inventory"));
      mesher.register(NIVEOLITE, 0, new ModelResourceLocation(NIVEOLITE.getRegistryName(), "inventory"));
      mesher.register(HELLHOUNDFUR, 0, new ModelResourceLocation(HELLHOUNDFUR.getRegistryName(), "inventory"));
      mesher.register(FROZENSPAWNERPIECE, 0, new ModelResourceLocation(FROZENSPAWNERPIECE.getRegistryName(), "inventory"));
      mesher.register(RUSTEDSPAWNERPIECE, 0, new ModelResourceLocation(RUSTEDSPAWNERPIECE.getRegistryName(), "inventory"));
      mesher.register(SPAWNERPIECE, 0, new ModelResourceLocation(SPAWNERPIECE.getRegistryName(), "inventory"));
      mesher.register(SLIMYEGGS, 0, new ModelResourceLocation(SLIMYEGGS.getRegistryName(), "inventory"));
      mesher.register(GRAINSBERYLLIUM, 0, new ModelResourceLocation(GRAINSBERYLLIUM.getRegistryName(), "inventory"));
      mesher.register(MODULEELECTROLYZER, 0, new ModelResourceLocation(MODULEELECTROLYZER.getRegistryName(), "inventory"));
      mesher.register(INGOTALUMINIUM, 0, new ModelResourceLocation(INGOTALUMINIUM.getRegistryName(), "inventory"));
      mesher.register(DUSTALUMINIUM, 0, new ModelResourceLocation(DUSTALUMINIUM.getRegistryName(), "inventory"));
      mesher.register(NUGGETALUMINIUM, 0, new ModelResourceLocation(NUGGETALUMINIUM.getRegistryName(), "inventory"));
      mesher.register(BEARINGALLOYDUST, 0, new ModelResourceLocation(BEARINGALLOYDUST.getRegistryName(), "inventory"));
      mesher.register(COPPERTRANSFORMER, 0, new ModelResourceLocation(COPPERTRANSFORMER.getRegistryName(), "inventory"));
      mesher.register(STAMPMOLD, 0, new ModelResourceLocation(STAMPMOLD.getRegistryName(), "inventory"));
      mesher.register(STEELSTAMP, 0, new ModelResourceLocation(STEELSTAMP.getRegistryName(), "inventory"));
      mesher.register(INGOTSILVER, 0, new ModelResourceLocation(INGOTSILVER.getRegistryName(), "inventory"));
      mesher.register(DUSTSILVER, 0, new ModelResourceLocation(DUSTSILVER.getRegistryName(), "inventory"));
      mesher.register(NUGGETSILVER, 0, new ModelResourceLocation(NUGGETSILVER.getRegistryName(), "inventory"));
      mesher.register(ARROWWIND, 0, new ModelResourceLocation(ARROWWIND.getRegistryName(), "inventory"));
      mesher.register(ARROWTWIN, 0, new ModelResourceLocation(ARROWTWIN.getRegistryName(), "inventory"));
      mesher.register(ARROWMITHRIL, 0, new ModelResourceLocation(ARROWMITHRIL.getRegistryName(), "inventory"));
      mesher.register(ARROWBOUNCING, 0, new ModelResourceLocation(ARROWBOUNCING.getRegistryName(), "inventory"));
      mesher.register(ARROWSHELL, 0, new ModelResourceLocation(ARROWSHELL.getRegistryName(), "inventory"));
      mesher.register(ARROWVOID, 0, new ModelResourceLocation(ARROWVOID.getRegistryName(), "inventory"));
      mesher.register(ARROWFISH, 0, new ModelResourceLocation(ARROWFISH.getRegistryName(), "inventory"));
      mesher.register(ARROWBENGAL, 0, new ModelResourceLocation(ARROWBENGAL.getRegistryName(), "inventory"));
      mesher.register(ARROWMODERN, 0, new ModelResourceLocation(ARROWMODERN.getRegistryName(), "inventory"));
      mesher.register(ARROWVICIOUS, 0, new ModelResourceLocation(ARROWVICIOUS.getRegistryName(), "inventory"));
      mesher.register(ARROWFIREJET, 0, new ModelResourceLocation(ARROWFIREJET.getRegistryName(), "inventory"));
      mesher.register(ARROWFROZEN, 0, new ModelResourceLocation(ARROWFROZEN.getRegistryName(), "inventory"));
      mesher.register(TIDEACTIVATOR1, 0, new ModelResourceLocation(TIDEACTIVATOR1.getRegistryName(), "inventory"));
      mesher.register(TIDEACTIVATOR2, 0, new ModelResourceLocation(TIDEACTIVATOR2.getRegistryName(), "inventory"));
      mesher.register(TIDEACTIVATOR3, 0, new ModelResourceLocation(TIDEACTIVATOR3.getRegistryName(), "inventory"));
      mesher.register(TIDEACTIVATOR4, 0, new ModelResourceLocation(TIDEACTIVATOR4.getRegistryName(), "inventory"));
      mesher.register(TIDEACTIVATOR5, 0, new ModelResourceLocation(TIDEACTIVATOR5.getRegistryName(), "inventory"));
      mesher.register(ROCKETARCANE, 0, new ModelResourceLocation(ROCKETARCANE.getRegistryName(), "inventory"));
      mesher.register(BUCKSHOT, 0, new ModelResourceLocation(BUCKSHOT.getRegistryName(), "inventory"));
      mesher.register(NORTHERNHELM, 0, new ModelResourceLocation(NORTHERNHELM.getRegistryName(), "inventory"));
      mesher.register(NORTHERNCHEST, 0, new ModelResourceLocation(NORTHERNCHEST.getRegistryName(), "inventory"));
      mesher.register(NORTHERNLEGS, 0, new ModelResourceLocation(NORTHERNLEGS.getRegistryName(), "inventory"));
      mesher.register(NORTHERNBOOTS, 0, new ModelResourceLocation(NORTHERNBOOTS.getRegistryName(), "inventory"));
      mesher.register(CRYSTALHELM, 0, new ModelResourceLocation(CRYSTALHELM.getRegistryName(), "inventory"));
      mesher.register(CRYSTALCHEST, 0, new ModelResourceLocation(CRYSTALCHEST.getRegistryName(), "inventory"));
      mesher.register(CRYSTALLEGS, 0, new ModelResourceLocation(CRYSTALLEGS.getRegistryName(), "inventory"));
      mesher.register(CRYSTALBOOTS, 0, new ModelResourceLocation(CRYSTALBOOTS.getRegistryName(), "inventory"));
      mesher.register(THUNDERERHELM, 0, new ModelResourceLocation(THUNDERERHELM.getRegistryName(), "inventory"));
      mesher.register(THUNDERERCHEST, 0, new ModelResourceLocation(THUNDERERCHEST.getRegistryName(), "inventory"));
      mesher.register(THUNDERERLEGS, 0, new ModelResourceLocation(THUNDERERLEGS.getRegistryName(), "inventory"));
      mesher.register(THUNDERERBOOTS, 0, new ModelResourceLocation(THUNDERERBOOTS.getRegistryName(), "inventory"));
      mesher.register(GOTHICSWORD, 0, new ModelResourceLocation(GOTHICSWORD.getRegistryName(), "inventory"));
      mesher.register(BURNINGFROSTIGNITER, 0, new ModelResourceLocation(BURNINGFROSTIGNITER.getRegistryName(), "inventory"));
      mesher.register(WINTERSACRIFICE, 0, new ModelResourceLocation(WINTERSACRIFICE.getRegistryName(), "inventory"));
      mesher.register(WINTERSCALE, 0, new ModelResourceLocation(WINTERSCALE.getRegistryName(), "inventory"));
      mesher.register(HAILTEAR, 0, new ModelResourceLocation(HAILTEAR.getRegistryName(), "inventory"));
      mesher.register(PLASMAMINIGUNCLIP, 0, new ModelResourceLocation(PLASMAMINIGUNCLIP.getRegistryName(), "inventory"));
      mesher.register(CRYSTALCUTTERAMMO, 0, new ModelResourceLocation(CRYSTALCUTTERAMMO.getRegistryName(), "inventory"));
      mesher.register(BLOWHOLEPELLETS, 0, new ModelResourceLocation(BLOWHOLEPELLETS.getRegistryName(), "inventory"));
      mesher.register(NUGGETADAMANTIUM, 0, new ModelResourceLocation(NUGGETADAMANTIUM.getRegistryName(), "inventory"));
      mesher.register(NAILGUNCLIP, 0, new ModelResourceLocation(NAILGUNCLIP.getRegistryName(), "inventory"));
      mesher.register(NAIL, 0, new ModelResourceLocation(NAIL.getRegistryName(), "inventory"));
      mesher.register(WIREWOLFRAM, 0, new ModelResourceLocation(WIREWOLFRAM.getRegistryName(), "inventory"));
      mesher.register(GASFILTER, 0, new ModelResourceLocation(GASFILTER.getRegistryName(), "inventory"));
      mesher.register(BATTERYLEADACID, 0, new ModelResourceLocation(BATTERYLEADACID.getRegistryName(), "inventory"));
      mesher.register(BATTERYLIION, 0, new ModelResourceLocation(BATTERYLIION.getRegistryName(), "inventory"));
      mesher.register(BEARINGLEAD, 0, new ModelResourceLocation(BEARINGLEAD.getRegistryName(), "inventory"));
      mesher.register(BEARINGARSENIC, 0, new ModelResourceLocation(BEARINGARSENIC.getRegistryName(), "inventory"));
      mesher.register(ELECTRICMOTOR, 0, new ModelResourceLocation(ELECTRICMOTOR.getRegistryName(), "inventory"));
      mesher.register(LINEARMOTOR, 0, new ModelResourceLocation(LINEARMOTOR.getRegistryName(), "inventory"));
      mesher.register(EYEOFSEER, 0, new ModelResourceLocation(EYEOFSEER.getRegistryName(), "inventory"));
      mesher.register(PROCESSOR, 0, new ModelResourceLocation(PROCESSOR.getRegistryName(), "inventory"));
      mesher.register(WIRECOPPER, 0, new ModelResourceLocation(WIRECOPPER.getRegistryName(), "inventory"));
      mesher.register(WIREGOLD, 0, new ModelResourceLocation(WIREGOLD.getRegistryName(), "inventory"));
      mesher.register(WIRESILVER, 0, new ModelResourceLocation(WIRESILVER.getRegistryName(), "inventory"));
      mesher.register(RUBBER, 0, new ModelResourceLocation(RUBBER.getRegistryName(), "inventory"));
      mesher.register(CIRCUIT, 0, new ModelResourceLocation(CIRCUIT.getRegistryName(), "inventory"));
      mesher.register(CIRCUITADVANCED, 0, new ModelResourceLocation(CIRCUITADVANCED.getRegistryName(), "inventory"));
      mesher.register(CIRCUITTOXIC, 0, new ModelResourceLocation(CIRCUITTOXIC.getRegistryName(), "inventory"));
      mesher.register(CIRCUITDIMENSION, 0, new ModelResourceLocation(CIRCUITDIMENSION.getRegistryName(), "inventory"));
      mesher.register(ICICLEMINIGUNCLIP, 0, new ModelResourceLocation(ICICLEMINIGUNCLIP.getRegistryName(), "inventory"));
      mesher.register(SLIMEPLASTIC, 0, new ModelResourceLocation(SLIMEPLASTIC.getRegistryName(), "inventory"));
      mesher.register(WHITESLIMEBALL, 0, new ModelResourceLocation(WHITESLIMEBALL.getRegistryName(), "inventory"));
      mesher.register(PLASTIC, 0, new ModelResourceLocation(PLASTIC.getRegistryName(), "inventory"));
      mesher.register(SLIMECELL, 0, new ModelResourceLocation(SLIMECELL.getRegistryName(), "inventory"));
      mesher.register(TOXIBERRYSTICK, 0, new ModelResourceLocation(TOXIBERRYSTICK.getRegistryName(), "inventory"));
      mesher.register(SCRAPMETAL, 0, new ModelResourceLocation(SCRAPMETAL.getRegistryName(), "inventory"));
      mesher.register(SULFUR, 0, new ModelResourceLocation(SULFUR.getRegistryName(), "inventory"));
      mesher.register(MAGMABLOOMSEEDS, 0, new ModelResourceLocation(MAGMABLOOMSEEDS.getRegistryName(), "inventory"));
      mesher.register(PIZZACHICKEN, 0, new ModelResourceLocation(PIZZACHICKEN.getRegistryName(), "inventory"));
      mesher.register(PIZZADIAVOLA, 0, new ModelResourceLocation(PIZZADIAVOLA.getRegistryName(), "inventory"));
      mesher.register(PIZZACHEEZE, 0, new ModelResourceLocation(PIZZACHEEZE.getRegistryName(), "inventory"));
      mesher.register(PIZZATOXIC, 0, new ModelResourceLocation(PIZZATOXIC.getRegistryName(), "inventory"));
      mesher.register(PIZZAGLOWING, 0, new ModelResourceLocation(PIZZAGLOWING.getRegistryName(), "inventory"));
      mesher.register(SMOKEDSAUSAGE, 0, new ModelResourceLocation(SMOKEDSAUSAGE.getRegistryName(), "inventory"));
      mesher.register(RAWRIBS, 0, new ModelResourceLocation(RAWRIBS.getRegistryName(), "inventory"));
      mesher.register(HOTSPICYRIBS, 0, new ModelResourceLocation(HOTSPICYRIBS.getRegistryName(), "inventory"));
      mesher.register(BAUXITE, 0, new ModelResourceLocation(BAUXITE.getRegistryName(), "inventory"));
      mesher.register(INGOTBRASS, 0, new ModelResourceLocation(INGOTBRASS.getRegistryName(), "inventory"));
      mesher.register(DUSTBRASS, 0, new ModelResourceLocation(DUSTBRASS.getRegistryName(), "inventory"));
      mesher.register(NUGGETBRASS, 0, new ModelResourceLocation(NUGGETBRASS.getRegistryName(), "inventory"));
      mesher.register(INGOTZINC, 0, new ModelResourceLocation(INGOTZINC.getRegistryName(), "inventory"));
      mesher.register(DUSTZINC, 0, new ModelResourceLocation(DUSTZINC.getRegistryName(), "inventory"));
      mesher.register(NUGGETZINC, 0, new ModelResourceLocation(NUGGETZINC.getRegistryName(), "inventory"));
      mesher.register(INGOTTITANIUM, 0, new ModelResourceLocation(INGOTTITANIUM.getRegistryName(), "inventory"));
      mesher.register(DUSTTITANIUM, 0, new ModelResourceLocation(DUSTTITANIUM.getRegistryName(), "inventory"));
      mesher.register(NUGGETTITANIUM, 0, new ModelResourceLocation(NUGGETTITANIUM.getRegistryName(), "inventory"));
      mesher.register(CHEMICALGLASS, 0, new ModelResourceLocation(CHEMICALGLASS.getRegistryName(), "inventory"));
      mesher.register(NUGGETTOXINIUM, 0, new ModelResourceLocation(NUGGETTOXINIUM.getRegistryName(), "inventory"));
      mesher.register(ICEDUST, 0, new ModelResourceLocation(ICEDUST.getRegistryName(), "inventory"));
      mesher.register(CONIFERROSIN, 0, new ModelResourceLocation(CONIFERROSIN.getRegistryName(), "inventory"));
      mesher.register(FIERYOIL, 0, new ModelResourceLocation(FIERYOIL.getRegistryName(), "inventory"));
      mesher.register(SALT, 0, new ModelResourceLocation(SALT.getRegistryName(), "inventory"));
      mesher.register(CRYSTALPOISON, 0, new ModelResourceLocation(CRYSTALPOISON.getRegistryName(), "inventory"));
      mesher.register(COPPERSULFATE, 0, new ModelResourceLocation(COPPERSULFATE.getRegistryName(), "inventory"));
      mesher.register(PLANTFIBER, 0, new ModelResourceLocation(PLANTFIBER.getRegistryName(), "inventory"));
      mesher.register(DRIEDPLANTFIBER, 0, new ModelResourceLocation(DRIEDPLANTFIBER.getRegistryName(), "inventory"));
      mesher.register(DUSTGLOWINGCRYSTAL, 0, new ModelResourceLocation(DUSTGLOWINGCRYSTAL.getRegistryName(), "inventory"));
      mesher.register(DUSTMAGICCRYSTAL, 0, new ModelResourceLocation(DUSTMAGICCRYSTAL.getRegistryName(), "inventory"));
      mesher.register(ADVANCED_POLYMER, 0, new ModelResourceLocation(ADVANCED_POLYMER.getRegistryName(), "inventory"));
      mesher.register(TOXIBERRYJUICEDRIP, 0, new ModelResourceLocation(TOXIBERRYJUICEDRIP.getRegistryName(), "inventory"));
      mesher.register(THUNDERSTONE, 0, new ModelResourceLocation(THUNDERSTONE.getRegistryName(), "inventory"));
      mesher.register(THUNDERCAPACITOR, 0, new ModelResourceLocation(THUNDERCAPACITOR.getRegistryName(), "inventory"));
      mesher.register(SKYCRYSTAL, 0, new ModelResourceLocation(SKYCRYSTAL.getRegistryName(), "inventory"));
      mesher.register(SKYCRYSTALPIECE, 0, new ModelResourceLocation(SKYCRYSTALPIECE.getRegistryName(), "inventory"));
      mesher.register(WINDNATURE, 0, new ModelResourceLocation(WINDNATURE.getRegistryName(), "inventory"));
      mesher.register(SKYSPHERE, 0, new ModelResourceLocation(SKYSPHERE.getRegistryName(), "inventory"));
      mesher.register(INGOTARSENIC, 0, new ModelResourceLocation(INGOTARSENIC.getRegistryName(), "inventory"));
      mesher.register(DUSTARSENIC, 0, new ModelResourceLocation(DUSTARSENIC.getRegistryName(), "inventory"));
      mesher.register(NUGGETARSENIC, 0, new ModelResourceLocation(NUGGETARSENIC.getRegistryName(), "inventory"));
      mesher.register(INGOTWOLFRAM, 0, new ModelResourceLocation(INGOTWOLFRAM.getRegistryName(), "inventory"));
      mesher.register(DUSTWOLFRAM, 0, new ModelResourceLocation(DUSTWOLFRAM.getRegistryName(), "inventory"));
      mesher.register(NUGGETWOLFRAM, 0, new ModelResourceLocation(NUGGETWOLFRAM.getRegistryName(), "inventory"));
      mesher.register(INGOTSTORMBRASS, 0, new ModelResourceLocation(INGOTSTORMBRASS.getRegistryName(), "inventory"));
      mesher.register(DUSTSTORMBRASS, 0, new ModelResourceLocation(DUSTSTORMBRASS.getRegistryName(), "inventory"));
      mesher.register(NUGGETSTORMBRASS, 0, new ModelResourceLocation(NUGGETSTORMBRASS.getRegistryName(), "inventory"));
      mesher.register(NUGGETSTORMSTEEL, 0, new ModelResourceLocation(NUGGETSTORMSTEEL.getRegistryName(), "inventory"));
      mesher.register(INGOTTOXINIUM, 0, new ModelResourceLocation(INGOTTOXINIUM.getRegistryName(), "inventory"));
      mesher.register(DUSTTOXINIUM, 0, new ModelResourceLocation(DUSTTOXINIUM.getRegistryName(), "inventory"));
      mesher.register(INGOTSTORMSTEEL, 0, new ModelResourceLocation(INGOTSTORMSTEEL.getRegistryName(), "inventory"));
      mesher.register(DUSTSTORMSTEEL, 0, new ModelResourceLocation(DUSTSTORMSTEEL.getRegistryName(), "inventory"));
      mesher.register(VOIDCRYSTAL, 0, new ModelResourceLocation(VOIDCRYSTAL.getRegistryName(), "inventory"));
      mesher.register(HEROBRINECURSE, 0, new ModelResourceLocation(HEROBRINECURSE.getRegistryName(), "inventory"));
      mesher.register(DUSTADAMANTIUM, 0, new ModelResourceLocation(DUSTADAMANTIUM.getRegistryName(), "inventory"));
      mesher.register(INGOTADAMANTIUM, 0, new ModelResourceLocation(INGOTADAMANTIUM.getRegistryName(), "inventory"));
      mesher.register(STORMSPANNER, 0, new ModelResourceLocation(STORMSPANNER.getRegistryName(), "inventory"));
      mesher.register(HAZARDHELM, 0, new ModelResourceLocation(HAZARDHELM.getRegistryName(), "inventory"));
      mesher.register(HAZARDCHEST, 0, new ModelResourceLocation(HAZARDCHEST.getRegistryName(), "inventory"));
      mesher.register(HAZARDLEGS, 0, new ModelResourceLocation(HAZARDLEGS.getRegistryName(), "inventory"));
      mesher.register(HAZARDBOOTS, 0, new ModelResourceLocation(HAZARDBOOTS.getRegistryName(), "inventory"));
      mesher.register(BULLETADAMANTIUM, 0, new ModelResourceLocation(BULLETADAMANTIUM.getRegistryName(), "inventory"));
      mesher.register(ADAMANTIUMLONGSWORD, 0, new ModelResourceLocation(ADAMANTIUMLONGSWORD.getRegistryName(), "inventory"));
      mesher.register(ADAMANTIUMKNIFE, 0, new ModelResourceLocation(ADAMANTIUMKNIFE.getRegistryName(), "inventory"));
      mesher.register(ADAMANTIUMARMORHELM, 0, new ModelResourceLocation(ADAMANTIUMARMORHELM.getRegistryName(), "inventory"));
      mesher.register(ADAMANTIUMARMORCHEST, 0, new ModelResourceLocation(ADAMANTIUMARMORCHEST.getRegistryName(), "inventory"));
      mesher.register(ADAMANTIUMARMORLEGS, 0, new ModelResourceLocation(ADAMANTIUMARMORLEGS.getRegistryName(), "inventory"));
      mesher.register(ADAMANTIUMARMORBOOTS, 0, new ModelResourceLocation(ADAMANTIUMARMORBOOTS.getRegistryName(), "inventory"));
      mesher.register(ADAMANTIUMROUNDS, 0, new ModelResourceLocation(ADAMANTIUMROUNDS.getRegistryName(), "inventory"));
      mesher.register(INGOTNORTHERN, 0, new ModelResourceLocation(INGOTNORTHERN.getRegistryName(), "inventory"));
      mesher.register(NORTHERNSPHERE, 0, new ModelResourceLocation(NORTHERNSPHERE.getRegistryName(), "inventory"));
      mesher.register(VILESUBSTANCE, 0, new ModelResourceLocation(VILESUBSTANCE.getRegistryName(), "inventory"));
      mesher.register(EMBRYO, 0, new ModelResourceLocation(EMBRYO.getRegistryName(), "inventory"));
      mesher.register(MUTAGEN, 0, new ModelResourceLocation(MUTAGEN.getRegistryName(), "inventory"));
      mesher.register(CMO, 0, new ModelResourceLocation(CMO.getRegistryName(), "inventory"));
      mesher.register(PROCESSORPATTERN, 0, new ModelResourceLocation(PROCESSORPATTERN.getRegistryName(), "inventory"));
      mesher.register(STORMLEDGELOGWANDBRASS, 0, new ModelResourceLocation(STORMLEDGELOGWANDBRASS.getRegistryName(), "inventory"));
      mesher.register(STORMLEDGELOGWANDPINK, 0, new ModelResourceLocation(STORMLEDGELOGWANDPINK.getRegistryName(), "inventory"));
      mesher.register(ENDERCROWN, 0, new ModelResourceLocation(ENDERCROWN.getRegistryName(), "inventory"));
      mesher.register(LIVEHEART, 0, new ModelResourceLocation(LIVEHEART.getRegistryName(), "inventory"));
      mesher.register(ITEMTURRET, 0, new ModelResourceLocation(ITEMTURRET.getRegistryName(), "inventory"));
      mesher.register(WRENCH, 0, new ModelResourceLocation(WRENCH.getRegistryName(), "inventory"));
      mesher.register(VIRULENTROD, 0, new ModelResourceLocation(VIRULENTROD.getRegistryName(), "inventory"));
      mesher.register(BUNKERKEYCARD, 0, new ModelResourceLocation(BUNKERKEYCARD.getRegistryName(), "inventory"));
      mesher.register(ANTIRADPACK, 0, new ModelResourceLocation(ANTIRADPACK.getRegistryName(), "inventory"));
      mesher.register(ANTIRADPILLS, 0, new ModelResourceLocation(ANTIRADPILLS.getRegistryName(), "inventory"));
      mesher.register(ANTIRADINJECTOR, 0, new ModelResourceLocation(ANTIRADINJECTOR.getRegistryName(), "inventory"));
      mesher.register(GASMASK, 0, new ModelResourceLocation(GASMASK.getRegistryName(), "inventory"));
      mesher.register(ARSENICPICKAXE, 0, new ModelResourceLocation(ARSENICPICKAXE.getRegistryName(), "inventory"));
      mesher.register(ARSENICAXE, 0, new ModelResourceLocation(ARSENICAXE.getRegistryName(), "inventory"));
      mesher.register(ARSENICSHOVEL, 0, new ModelResourceLocation(ARSENICSHOVEL.getRegistryName(), "inventory"));
      mesher.register(RUSTEDKEY, 0, new ModelResourceLocation(RUSTEDKEY.getRegistryName(), "inventory"));
      mesher.register(SNOWCOATARMORHELM, 0, new ModelResourceLocation(SNOWCOATARMORHELM.getRegistryName(), "inventory"));
      mesher.register(SNOWCOATARMORCHEST, 0, new ModelResourceLocation(SNOWCOATARMORCHEST.getRegistryName(), "inventory"));
      mesher.register(SNOWCOATARMORLEGS, 0, new ModelResourceLocation(SNOWCOATARMORLEGS.getRegistryName(), "inventory"));
      mesher.register(SNOWCOATARMORBOOTS, 0, new ModelResourceLocation(SNOWCOATARMORBOOTS.getRegistryName(), "inventory"));
      mesher.register(CORALARMORHELM, 0, new ModelResourceLocation(CORALARMORHELM.getRegistryName(), "inventory"));
      mesher.register(CORALARMORCHEST, 0, new ModelResourceLocation(CORALARMORCHEST.getRegistryName(), "inventory"));
      mesher.register(CORALARMORLEGS, 0, new ModelResourceLocation(CORALARMORLEGS.getRegistryName(), "inventory"));
      mesher.register(CORALARMORBOOTS, 0, new ModelResourceLocation(CORALARMORBOOTS.getRegistryName(), "inventory"));
      mesher.register(LICHARMORHELM, 0, new ModelResourceLocation(LICHARMORHELM.getRegistryName(), "inventory"));
      mesher.register(LICHARMORCHEST, 0, new ModelResourceLocation(LICHARMORCHEST.getRegistryName(), "inventory"));
      mesher.register(LICHARMORLEGS, 0, new ModelResourceLocation(LICHARMORLEGS.getRegistryName(), "inventory"));
      mesher.register(LICHARMORBOOTS, 0, new ModelResourceLocation(LICHARMORBOOTS.getRegistryName(), "inventory"));
      mesher.register(BONEARMORHELM, 0, new ModelResourceLocation(BONEARMORHELM.getRegistryName(), "inventory"));
      mesher.register(BONEARMORCHEST, 0, new ModelResourceLocation(BONEARMORCHEST.getRegistryName(), "inventory"));
      mesher.register(BONEARMORLEGS, 0, new ModelResourceLocation(BONEARMORLEGS.getRegistryName(), "inventory"));
      mesher.register(BONEARMORBOOTS, 0, new ModelResourceLocation(BONEARMORBOOTS.getRegistryName(), "inventory"));
      mesher.register(ROCKETVOID, 0, new ModelResourceLocation(ROCKETVOID.getRegistryName(), "inventory"));
      mesher.register(ROCKETWATERBLAST, 0, new ModelResourceLocation(ROCKETWATERBLAST.getRegistryName(), "inventory"));
      mesher.register(ROCKETDEMOLISHING, 0, new ModelResourceLocation(ROCKETDEMOLISHING.getRegistryName(), "inventory"));
      mesher.register(ROCKETMINING, 0, new ModelResourceLocation(ROCKETMINING.getRegistryName(), "inventory"));
      mesher.register(ROCKETCHEMICAL, 0, new ModelResourceLocation(ROCKETCHEMICAL.getRegistryName(), "inventory"));
      mesher.register(ROCKETNAPALM, 0, new ModelResourceLocation(ROCKETNAPALM.getRegistryName(), "inventory"));
      mesher.register(ROCKETFROSTFIRE, 0, new ModelResourceLocation(ROCKETFROSTFIRE.getRegistryName(), "inventory"));
      mesher.register(GLASSHEART, 0, new ModelResourceLocation(GLASSHEART.getRegistryName(), "inventory"));
      mesher.register(ROCKETCOMMON, 0, new ModelResourceLocation(ROCKETCOMMON.getRegistryName(), "inventory"));
      mesher.register(SNAPBALLAMMO, 0, new ModelResourceLocation(SNAPBALLAMMO.getRegistryName(), "inventory"));
      mesher.register(TEAMSELECTOR, 0, new ModelResourceLocation(TEAMSELECTOR.getRegistryName(), "inventory"));
      mesher.register(FIREWHIP, 0, new ModelResourceLocation(TIMELESSSWORD.getRegistryName(), "inventory"));
      mesher.register(TIMELESSSWORD, 0, new ModelResourceLocation(TIMELESSSWORD.getRegistryName(), "inventory"));
      mesher.register(PALMLOGWAND, 0, new ModelResourceLocation(PALMLOGWAND.getRegistryName(), "inventory"));
      mesher.register(CORALRIFLECLIP, 0, new ModelResourceLocation(CORALRIFLECLIP.getRegistryName(), "inventory"));
      mesher.register(MANAKEEPER, 0, new ModelResourceLocation(MANAKEEPER.getRegistryName(), "inventory"));
      mesher.register(THORNKEEPER, 0, new ModelResourceLocation(THORNKEEPER.getRegistryName(), "inventory"));
      mesher.register(LIGHTBAND, 0, new ModelResourceLocation(LIGHTBAND.getRegistryName(), "inventory"));
      mesher.register(SPRINGERWAISTBAND, 0, new ModelResourceLocation(SPRINGERWAISTBAND.getRegistryName(), "inventory"));
      mesher.register(SPIRITTHORN, 0, new ModelResourceLocation(SPIRITTHORN.getRegistryName(), "inventory"));
      mesher.register(VENOMEDDAGGER, 0, new ModelResourceLocation(VENOMEDDAGGER.getRegistryName(), "inventory"));
      mesher.register(BLEEDINGROOT, 0, new ModelResourceLocation(BLEEDINGROOT.getRegistryName(), "inventory"));
      mesher.register(PAINFULROOT, 0, new ModelResourceLocation(PAINFULROOT.getRegistryName(), "inventory"));
      mesher.register(CYBERAMULET, 0, new ModelResourceLocation(CYBERAMULET.getRegistryName(), "inventory"));
      mesher.register(PERSISTENCEPENDENT, 0, new ModelResourceLocation(PERSISTENCEPENDENT.getRegistryName(), "inventory"));
      mesher.register(BRASSKNUCKLES, 0, new ModelResourceLocation(BRASSKNUCKLES.getRegistryName(), "inventory"));
      mesher.register(HELLHOUNDCOLLAR, 0, new ModelResourceLocation(HELLHOUNDCOLLAR.getRegistryName(), "inventory"));
      mesher.register(GOLDENKNUCKLES, 0, new ModelResourceLocation(GOLDENKNUCKLES.getRegistryName(), "inventory"));
      mesher.register(LIVEBLOODNECKLACE, 0, new ModelResourceLocation(LIVEBLOODNECKLACE.getRegistryName(), "inventory"));
      mesher.register(ANCIENTICESHARD, 0, new ModelResourceLocation(ANCIENTICESHARD.getRegistryName(), "inventory"));
      mesher.register(MANARUBBLE, 0, new ModelResourceLocation(MANARUBBLE.getRegistryName(), "inventory"));
      mesher.register(ICEHEART, 0, new ModelResourceLocation(ICEHEART.getRegistryName(), "inventory"));
      mesher.register(FROSTINGUISHER, 0, new ModelResourceLocation(FROSTINGUISHER.getRegistryName(), "inventory"));
      mesher.register(HOLYEXTINGUISHER, 0, new ModelResourceLocation(HOLYEXTINGUISHER.getRegistryName(), "inventory"));
      mesher.register(GHOSTFLAMETRAP, 0, new ModelResourceLocation(GHOSTFLAMETRAP.getRegistryName(), "inventory"));
      mesher.register(FLAMESUPPRESSOR, 0, new ModelResourceLocation(FLAMESUPPRESSOR.getRegistryName(), "inventory"));
      mesher.register(CONDUCTIVEBELT, 0, new ModelResourceLocation(CONDUCTIVEBELT.getRegistryName(), "inventory"));
      mesher.register(LIGHTNINGSOCKS, 0, new ModelResourceLocation(LIGHTNINGSOCKS.getRegistryName(), "inventory"));
      mesher.register(AMMONIAFLASK, 0, new ModelResourceLocation(AMMONIAFLASK.getRegistryName(), "inventory"));
      mesher.register(CORROSIVEFLASK, 0, new ModelResourceLocation(CORROSIVEFLASK.getRegistryName(), "inventory"));
      mesher.register(CROSSCHAINLET, 0, new ModelResourceLocation(CROSSCHAINLET.getRegistryName(), "inventory"));
      mesher.register(DETOXICATOR, 0, new ModelResourceLocation(DETOXICATOR.getRegistryName(), "inventory"));
      mesher.register(HAZARDGLOVE, 0, new ModelResourceLocation(HAZARDGLOVE.getRegistryName(), "inventory"));
      mesher.register(CONTACTLENSES, 0, new ModelResourceLocation(CONTACTLENSES.getRegistryName(), "inventory"));
      mesher.register(MINERSGLOVE, 0, new ModelResourceLocation(MINERSGLOVE.getRegistryName(), "inventory"));
      mesher.register(BODYWARMER, 0, new ModelResourceLocation(BODYWARMER.getRegistryName(), "inventory"));
      mesher.register(DEVOURERSTEETH, 0, new ModelResourceLocation(DEVOURERSTEETH.getRegistryName(), "inventory"));
      mesher.register(ENDERLEECH, 0, new ModelResourceLocation(ENDERLEECH.getRegistryName(), "inventory"));
      mesher.register(ENERGYDRINK, 0, new ModelResourceLocation(ENERGYDRINK.getRegistryName(), "inventory"));
      mesher.register(RUNNERSOCKS, 0, new ModelResourceLocation(RUNNERSOCKS.getRegistryName(), "inventory"));
      mesher.register(SLIMEEATER, 0, new ModelResourceLocation(SLIMEEATER.getRegistryName(), "inventory"));
      mesher.register(FIREEATER, 0, new ModelResourceLocation(FIREEATER.getRegistryName(), "inventory"));
      mesher.register(SLIMEDEVOURER, 0, new ModelResourceLocation(SLIMEDEVOURER.getRegistryName(), "inventory"));
      mesher.register(LAVAEATER, 0, new ModelResourceLocation(LAVAEATER.getRegistryName(), "inventory"));
      mesher.register(PERSONALEXTINGUISHER, 0, new ModelResourceLocation(PERSONALEXTINGUISHER.getRegistryName(), "inventory"));
      mesher.register(ETHERWORM, 0, new ModelResourceLocation(ETHERWORM.getRegistryName(), "inventory"));
      mesher.register(ANGELWORM, 0, new ModelResourceLocation(ANGELWORM.getRegistryName(), "inventory"));
      mesher.register(CANDYAPPLE, 0, new ModelResourceLocation(CANDYAPPLE.getRegistryName(), "inventory"));
      mesher.register(CANDYCANE, 0, new ModelResourceLocation(CANDYCANE.getRegistryName(), "inventory"));
      mesher.register(CRIMBERRYWINE, 0, new ModelResourceLocation(CRIMBERRYWINE.getRegistryName(), "inventory"));
      mesher.register(GIFT, 0, new ModelResourceLocation(GIFT.getRegistryName(), "inventory"));
      mesher.register(IMPETUS, 0, new ModelResourceLocation(AIRBORNECIRCLET.getRegistryName(), "inventory"));
      mesher.register(AIRBORNECIRCLET, 0, new ModelResourceLocation(AIRBORNECIRCLET.getRegistryName(), "inventory"));
      mesher.register(INGOTCHROMIUM, 0, new ModelResourceLocation(INGOTCHROMIUM.getRegistryName(), "inventory"));
      mesher.register(DUSTCHROMIUM, 0, new ModelResourceLocation(DUSTCHROMIUM.getRegistryName(), "inventory"));
      mesher.register(INGOTBERYLLIUM, 0, new ModelResourceLocation(INGOTBERYLLIUM.getRegistryName(), "inventory"));
      mesher.register(DUSTBERYLLIUM, 0, new ModelResourceLocation(DUSTBERYLLIUM.getRegistryName(), "inventory"));
      mesher.register(DUSTMANGANESE, 0, new ModelResourceLocation(DUSTMANGANESE.getRegistryName(), "inventory"));
      mesher.register(INGOTMANGANESE, 0, new ModelResourceLocation(INGOTMANGANESE.getRegistryName(), "inventory"));
      mesher.register(LOOTBOXENCHANTWEAPON, 0, new ModelResourceLocation(LOOTBOXENCHANTWEAPON.getRegistryName(), "inventory"));
      mesher.register(LOOTBOXENCHANTSIMPLE, 0, new ModelResourceLocation(LOOTBOXENCHANTSIMPLE.getRegistryName(), "inventory"));
      mesher.register(LOOTBOXENCHANTALL, 0, new ModelResourceLocation(LOOTBOXENCHANTALL.getRegistryName(), "inventory"));
      mesher.register(EMERALDEYE, 0, new ModelResourceLocation(EMERALDEYE.getRegistryName(), "inventory"));
      mesher.register(BULLETFESTIVAL, 0, new ModelResourceLocation(BULLETFESTIVAL.getRegistryName(), "inventory"));
      mesher.register(BULLETEXPLODING, 0, new ModelResourceLocation(BULLETEXPLODING.getRegistryName(), "inventory"));
      mesher.register(BULLETCRYSTAL, 0, new ModelResourceLocation(BULLETCRYSTAL.getRegistryName(), "inventory"));
      mesher.register(BULLETTOXIC, 0, new ModelResourceLocation(BULLETTOXIC.getRegistryName(), "inventory"));
      mesher.register(BULLETPOISON, 0, new ModelResourceLocation(BULLETPOISON.getRegistryName(), "inventory"));
      mesher.register(BULLETTHUNDER, 0, new ModelResourceLocation(BULLETTHUNDER.getRegistryName(), "inventory"));
      mesher.register(TOXINIUMSHOTGUNCLIP, 0, new ModelResourceLocation(TOXINIUMSHOTGUNCLIP.getRegistryName(), "inventory"));
      mesher.register(BULLETFIRE, 0, new ModelResourceLocation(BULLETFIRE.getRegistryName(), "inventory"));
      mesher.register(BULLETSILVER, 0, new ModelResourceLocation(BULLETSILVER.getRegistryName(), "inventory"));
      mesher.register(BULLETLEAD, 0, new ModelResourceLocation(BULLETLEAD.getRegistryName(), "inventory"));
      mesher.register(BULLETCOPPER, 0, new ModelResourceLocation(BULLETCOPPER.getRegistryName(), "inventory"));
      mesher.register(BULLETGOLD, 0, new ModelResourceLocation(BULLETGOLD.getRegistryName(), "inventory"));
      mesher.register(BULLETFROZEN, 0, new ModelResourceLocation(BULLETFROZEN.getRegistryName(), "inventory"));
      mesher.register(SUBMACHINECLIP, 0, new ModelResourceLocation(SUBMACHINECLIP.getRegistryName(), "inventory"));
      mesher.register(SUBMACHINE, 0, new ModelResourceLocation(SUBMACHINE.getRegistryName(), "inventory"));
      mesher.register(LOCKER, 0, new ModelResourceLocation(LOCKER.getRegistryName(), "inventory"));
      mesher.register(TOXINIUMSHOTGUN, 0, new ModelResourceLocation(TOXINIUMSHOTGUN.getRegistryName(), "inventory"));
      mesher.register(COIN, 0, new ModelResourceLocation(COIN.getRegistryName(), "inventory"));
      mesher.register(TOXINIUMHELM, 0, new ModelResourceLocation(TOXINIUMHELM.getRegistryName(), "inventory"));
      mesher.register(TOXINIUMCHEST, 0, new ModelResourceLocation(TOXINIUMCHEST.getRegistryName(), "inventory"));
      mesher.register(TOXINIUMLEGS, 0, new ModelResourceLocation(TOXINIUMLEGS.getRegistryName(), "inventory"));
      mesher.register(TOXINIUMBOOTS, 0, new ModelResourceLocation(TOXINIUMBOOTS.getRegistryName(), "inventory"));
      mesher.register(FORGETPICKAXE, 0, new ModelResourceLocation(FORGETPICKAXE.getRegistryName(), "inventory"));
      mesher.register(FORGETAXE, 0, new ModelResourceLocation(FORGETAXE.getRegistryName(), "inventory"));
      mesher.register(FORGETSHOVEL, 0, new ModelResourceLocation(FORGETSHOVEL.getRegistryName(), "inventory"));
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(FIRST, 0, new ModelResourceLocation(FIRST.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(ICHSHOWER, 0, new ModelResourceLocation(ICHSHOWER.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(SHARKCANN, 0, new ModelResourceLocation(SHARKCANN.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(SHARKAMMO, 0, new ModelResourceLocation(SHARKAMMO.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(MAGICBOOMERANG, 0, new ModelResourceLocation(MAGICBOOMERANG.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(BUTTERFLY, 0, new ModelResourceLocation(BUTTERFLY.getRegistryName(), "inventory"));
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(SUNRISE, 0, new ModelResourceLocation(SUNRISE.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(LASERSNIPER, 0, new ModelResourceLocation(LASERSNIPER.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(IONBATTERY, 0, new ModelResourceLocation(IONBATTERY.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(LASERPISTOL, 0, new ModelResourceLocation(LASERPISTOL.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(LASERRIFLE, 0, new ModelResourceLocation(LASERRIFLE.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(VAMPIREKNIFE, 0, new ModelResourceLocation(VAMPIREKNIFE.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(VAMPIREKNIFESLAUN, 0, new ModelResourceLocation(VAMPIREKNIFESLAUN.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(FROSTBOLT, 0, new ModelResourceLocation(FROSTBOLT.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(ANTICHARGE, 0, new ModelResourceLocation(ANTICHARGE.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(ELAMMOFIRE, 0, new ModelResourceLocation(ELAMMOFIRE.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(ELAMMOWATER, 0, new ModelResourceLocation(ELAMMOWATER.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(ELAMMOAIR, 0, new ModelResourceLocation(ELAMMOAIR.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(ELAMMOEARTH, 0, new ModelResourceLocation(ELAMMOEARTH.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(STINGERBOLTS, 0, new ModelResourceLocation(STINGERBOLTS.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(FIREWORKPACK, 0, new ModelResourceLocation(FIREWORKPACK.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(FIREWORKDRAGON, 0, new ModelResourceLocation(FIREWORKDRAGON.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(QUADROBELT, 0, new ModelResourceLocation(QUADROBELT.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(VORTEXBOTTLE, 0, new ModelResourceLocation(VORTEXBOTTLE.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(ETHERSIGN, 0, new ModelResourceLocation(ETHERSIGN.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(PHOENIXGHOSTCAPE, 0, new ModelResourceLocation(PHOENIXGHOSTCAPE.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(BILEBITERAMMO, 0, new ModelResourceLocation(BILEBITERAMMO.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(SNOWSTORMSTAFF, 0, new ModelResourceLocation(FROSTBOLT.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(WIZARDHELM, 0, new ModelResourceLocation(WIZARDHELM.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(WIZARDCHEST, 0, new ModelResourceLocation(WIZARDCHEST.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(WIZARDLEGS, 0, new ModelResourceLocation(WIZARDLEGS.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(WIZARDBOOTS, 0, new ModelResourceLocation(WIZARDBOOTS.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(FIREMAGEHELM, 0, new ModelResourceLocation(FIREMAGEHELM.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(FIREMAGECHEST, 0, new ModelResourceLocation(FIREMAGECHEST.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(FIREMAGELEGS, 0, new ModelResourceLocation(FIREMAGELEGS.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(FIREMAGEBOOTS, 0, new ModelResourceLocation(FIREMAGEBOOTS.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(FIRELORDHELM, 0, new ModelResourceLocation(FIRELORDHELM.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(FIRELORDCHEST, 0, new ModelResourceLocation(FIRELORDCHEST.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(FIRELORDLEGS, 0, new ModelResourceLocation(FIRELORDLEGS.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(FIRELORDBOOTS, 0, new ModelResourceLocation(FIRELORDBOOTS.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(GRAPLINGHOOK, 0, new ModelResourceLocation(GRAPLINGHOOK.getRegistryName(), "inventory"));
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(JUNGLEGH, 0, new ModelResourceLocation(JUNGLEGH.getRegistryName(), "inventory"));
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(SEASHELL, 0, new ModelResourceLocation(SEASHELL.getRegistryName(), "inventory"));
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(SLIMEGH, 0, new ModelResourceLocation(SLIMEGH.getRegistryName(), "inventory"));
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(ENDERGH, 0, new ModelResourceLocation(ENDERGH.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(MAINFISHINGROD, 0, new ModelResourceLocation(MAINFISHINGROD.getRegistryName(), "inventory"));
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(ICESWORD, 0, new ModelResourceLocation(ICESWORD.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(NETHERGRINDERAMMO, 0, new ModelResourceLocation(NETHERGRINDERAMMO.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(SNOWFLAKESHUR, 0, new ModelResourceLocation(SNOWFLAKESHUR.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(CHAINDAGGER, 0, new ModelResourceLocation(CHAINDAGGER.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(TOXICNUCLEARWARHEAD, 0, new ModelResourceLocation(TOXICNUCLEARWARHEAD.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(EMPTYCELL, 0, new ModelResourceLocation(EMPTYCELL.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(CRYOGENCELL, 0, new ModelResourceLocation(CRYOGENCELL.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(LIGHTNINGGH, 0, new ModelResourceLocation(LIGHTNINGGH.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(RINGOFPROTECTION, 0, new ModelResourceLocation(RINGOFPROTECTION.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(SPIKERING, 0, new ModelResourceLocation(SPIKERING.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(SPARKLINGNECKLACE, 0, new ModelResourceLocation(SPARKLINGNECKLACE.getRegistryName(), "inventory"));
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(EXP, 0, new ModelResourceLocation(EMPTYCELL.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(DEMONICIGNITER, 0, new ModelResourceLocation(DEMONICIGNITER.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(BOUNCINGRING, 0, new ModelResourceLocation(BOUNCINGRING.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(SLIMEBOOTS, 0, new ModelResourceLocation(SLIMEBOOTS.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(SLIMEHELM, 0, new ModelResourceLocation(SLIMEHELM.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(SLIMECHEST, 0, new ModelResourceLocation(SLIMECHEST.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(SLIMELEGS, 0, new ModelResourceLocation(SLIMELEGS.getRegistryName(), "inventory"));
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(ICEBOOTS, 0, new ModelResourceLocation(ICEBOOTS.getRegistryName(), "inventory"));
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(ICEHELM, 0, new ModelResourceLocation(ICEHELM.getRegistryName(), "inventory"));
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(ICECHEST, 0, new ModelResourceLocation(ICECHEST.getRegistryName(), "inventory"));
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(ICELEGS, 0, new ModelResourceLocation(ICELEGS.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(SOULCHARM, 0, new ModelResourceLocation(SOULCHARM.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(JUNGLEBOOTS, 0, new ModelResourceLocation(JUNGLEBOOTS.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(JUNGLEHELM, 0, new ModelResourceLocation(JUNGLEHELM.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(JUNGLECHEST, 0, new ModelResourceLocation(JUNGLECHEST.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(JUNGLELEGS, 0, new ModelResourceLocation(JUNGLELEGS.getRegistryName(), "inventory"));
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(WEBGH, 0, new ModelResourceLocation(WEBGH.getRegistryName(), "inventory"));
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(ROPEGH, 0, new ModelResourceLocation(ROPEGH.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(PLASMARAILGUNBOLTS, 0, new ModelResourceLocation(PLASMARAILGUNBOLTS.getRegistryName(), "inventory"));
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(AIMLENS, 0, new ModelResourceLocation(AIMLENS.getRegistryName(), "inventory"));
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(FISHFEED, 0, new ModelResourceLocation(FISHFEED.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(VICIOUSEMBLEM, 0, new ModelResourceLocation(VICIOUSEMBLEM.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(ORBOFDESTROY, 0, new ModelResourceLocation(ORBOFDESTROY.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(WOODENSKIING, 0, new ModelResourceLocation(ORBOFDESTROY.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(VIALOFPOISON, 0, new ModelResourceLocation(VIALOFPOISON.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(VAMPIRICHEART, 0, new ModelResourceLocation(VAMPIRICHEART.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(FROZENWINGS, 0, new ModelResourceLocation(FROZENWINGS.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(TOXICWINGS, 0, new ModelResourceLocation(TOXICWINGS.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(GLACIDEBLADE, 0, new ModelResourceLocation(GLACIDEBLADE.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(INFERNALBLADE, 0, new ModelResourceLocation(INFERNALBLADE.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(CINDERBOW, 0, new ModelResourceLocation(CINDERBOW.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(INGOTINFERNUM, 0, new ModelResourceLocation(INGOTINFERNUM.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(INGOTMOLTEN, 0, new ModelResourceLocation(INGOTMOLTEN.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(NUGGETINFERNUM, 0, new ModelResourceLocation(NUGGETINFERNUM.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(NUGGETMOLTEN, 0, new ModelResourceLocation(NUGGETMOLTEN.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(MOLTENSTRING, 0, new ModelResourceLocation(MOLTENSTRING.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(LIQUIDFIRE, 0, new ModelResourceLocation(LIQUIDFIRE.getRegistryName(), "inventory"));
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(DEMONITE, 0, new ModelResourceLocation(DEMONITE.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(DEMONITESHARD, 0, new ModelResourceLocation(DEMONITESHARD.getRegistryName(), "inventory"));
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(RUBY, 0, new ModelResourceLocation(RUBY.getRegistryName(), "inventory"));
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(SAPPHIRE, 0, new ModelResourceLocation(SAPPHIRE.getRegistryName(), "inventory"));
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(CITRINE, 0, new ModelResourceLocation(CITRINE.getRegistryName(), "inventory"));
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(AMETHYST, 0, new ModelResourceLocation(AMETHYST.getRegistryName(), "inventory"));
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(TOPAZ, 0, new ModelResourceLocation(TOPAZ.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(RHINESTONE, 0, new ModelResourceLocation(RHINESTONE.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(MAGIC_POWDER, 0, new ModelResourceLocation(MAGIC_POWDER.getRegistryName(), "inventory"));
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(ICEGEM, 0, new ModelResourceLocation(ICEGEM.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(WEATHERFRAGMENTS, 0, new ModelResourceLocation(WEATHERFRAGMENTS.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(SNOWCLOTH, 0, new ModelResourceLocation(SNOWCLOTH.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(CONIFERSTICK, 0, new ModelResourceLocation(CONIFERSTICK.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(SOULSTONE, 0, new ModelResourceLocation(SOULSTONE.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(SAPPHIREEYE, 0, new ModelResourceLocation(SAPPHIREEYE.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(ENCHANTMENTBOOKINS, 0, new ModelResourceLocation(ENCHANTMENTBOOKINS.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(THELORDOFPAIN, 0, new ModelResourceLocation(PHOENIXGHOSTCAPE.getRegistryName(), "inventory"));
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(CRYSTALFAN, 0, new ModelResourceLocation(RUBY.getRegistryName(), "inventory"));
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(ANTIDOTE, 0, new ModelResourceLocation(ANTIDOTE.getRegistryName(), "inventory"));
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(TOXICOLA, 0, new ModelResourceLocation(TOXICOLA.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(ANTIPOTION, 0, new ModelResourceLocation(ANTIPOTION.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(DECEIDUSJUICE, 0, new ModelResourceLocation(DECEIDUSJUICE.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(TOXEDGEBREAD, 0, new ModelResourceLocation(TOXEDGEBREAD.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(TOXIBERRYMOJITO, 0, new ModelResourceLocation(TOXIBERRYMOJITO.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(WASTEBURGER, 0, new ModelResourceLocation(WASTEBURGER.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(BROWNSLIMEWAND, 0, new ModelResourceLocation(BROWNSLIMEWAND.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(SLIMEBLOBWAND, 0, new ModelResourceLocation(SLIMEBLOBWAND.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(BONESWAND, 0, new ModelResourceLocation(BONESWAND.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(GLOWINGTOXIBERRY, 0, new ModelResourceLocation(GLOWINGTOXIBERRY.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(SMALLTOXIBERRY, 0, new ModelResourceLocation(SMALLTOXIBERRY.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(GOTHICPICKAXE, 0, new ModelResourceLocation(GOTHICPICKAXE.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(GOTHICAXE, 0, new ModelResourceLocation(GOTHICAXE.getRegistryName(), "inventory"));
      Minecraft.getMinecraft()
         .getRenderItem()
         .getItemModelMesher()
         .register(GOTHICSHOVEL, 0, new ModelResourceLocation(GOTHICSHOVEL.getRegistryName(), "inventory"));

      for (Item item : forrender) {
         Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
      }

      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(ANIHGUN, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.ANIHGUN.getRegistryName(), "inventory");
         }
      });
      ANIHGUN.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(ELEMENTFOCUS, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.ELEMENTFOCUS.getRegistryName(), "inventory");
         }
      });
      ELEMENTFOCUS.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(STINGER, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.STINGER.getRegistryName(), "inventory");
         }
      });
      STINGER.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(FIREWORKSLAUN, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.FIREWORKSLAUN.getRegistryName(), "inventory");
         }
      });
      FIREWORKSLAUN.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(SCEPTEROFSANDS, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.SCEPTEROFSANDS.getRegistryName(), "inventory");
         }
      });
      SCEPTEROFSANDS.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(BILEBITER, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.BILEBITER.getRegistryName(), "inventory");
         }
      });
      BILEBITER.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(FIREBALLSTAFF, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.FIREBALLSTAFF.getRegistryName(), "inventory");
         }
      });
      FIREBALLSTAFF.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(ELECTRICSTAFF, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.ELECTRICSTAFF.getRegistryName(), "inventory");
         }
      });
      ELECTRICSTAFF.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(SLIMESHOTGUN, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.SLIMESHOTGUN.getRegistryName(), "inventory");
         }
      });
      SLIMESHOTGUN.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MOLTENGREATAXE, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.MOLTENGREATAXE.getRegistryName(), "inventory");
         }
      });
      MOLTENGREATAXE.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(NETHERGRINDER, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.NETHERGRINDER.getRegistryName(), "inventory");
         }
      });
      NETHERGRINDER.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(SNOWBALLCANNON, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.SNOWBALLCANNON.getRegistryName(), "inventory");
         }
      });
      SNOWBALLCANNON.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(CURSEDBLADE, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.CURSEDBLADE.getRegistryName(), "inventory");
         }
      });
      CURSEDBLADE.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(GRAVELURKER, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.GRAVELURKER.getRegistryName(), "inventory");
         }
      });
      GRAVELURKER.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(REAPER, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.REAPER.getRegistryName(), "inventory");
         }
      });
      REAPER.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(GHOSTSWORD, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.GHOSTSWORD.getRegistryName(), "inventory");
         }
      });
      GHOSTSWORD.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(CORPSESTAFF, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.CORPSESTAFF.getRegistryName(), "inventory");
         }
      });
      CORPSESTAFF.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(TOXICNUKECANNON, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.TOXICNUKECANNON.getRegistryName(), "inventory");
         }
      });
      TOXICNUKECANNON.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(CRYOGUN, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.CRYOGUN.getRegistryName(), "inventory");
         }
      });
      CRYOGUN.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(CHARGER, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.CHARGER.getRegistryName(), "inventory");
         }
      });
      CHARGER.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(ICICLEMINIGUN, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.ICICLEMINIGUN.getRegistryName(), "inventory");
         }
      });
      ICICLEMINIGUN.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(STATICLANCE, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.STATICLANCE.getRegistryName(), "inventory");
         }
      });
      STATICLANCE.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(WANDOFBLAZES, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.WANDOFBLAZES.getRegistryName(), "inventory");
         }
      });
      WANDOFBLAZES.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(CONIFERROD, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.CONIFERROD.getRegistryName(), "inventory");
         }
      });
      CONIFERROD.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(PLASMARAILGUN, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.PLASMARAILGUN.getRegistryName(), "inventory");
         }
      });
      PLASMARAILGUN.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(PLASMARIFLE, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.PLASMARIFLE.getRegistryName(), "inventory");
         }
      });
      PLASMARIFLE.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(HOLOSHIELD, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.HOLOSHIELD.getRegistryName(), "inventory");
         }
      });
      HOLOSHIELD.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(HEADSHOOTER, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.HEADSHOOTER.getRegistryName(), "inventory");
         }
      });
      HEADSHOOTER.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(PLASMAPISTOL, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.PLASMAPISTOL.getRegistryName(), "inventory");
         }
      });
      PLASMAPISTOL.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(BOGFLOWER, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.BOGFLOWER.getRegistryName(), "inventory");
         }
      });
      BOGFLOWER.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(PISTOLFISH, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.PISTOLFISH.getRegistryName(), "inventory");
         }
      });
      PISTOLFISH.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(BUBBLEFISH, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.BUBBLEFISH.getRegistryName(), "inventory");
         }
      });
      BUBBLEFISH.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(LAVADROPPER, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.LAVADROPPER.getRegistryName(), "inventory");
         }
      });
      LAVADROPPER.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(PLASMAACCELERATOR, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.PLASMAACCELERATOR.getRegistryName(), "inventory");
         }
      });
      PLASMAACCELERATOR.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(VACUUMGUN, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.VACUUMGUN.getRegistryName(), "inventory");
         }
      });
      VACUUMGUN.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(GEMSTAFF, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.GEMSTAFF.getRegistryName(), "inventory");
         }
      });
      GEMSTAFF.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(WANDOFCOLD, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.WANDOFCOLD.getRegistryName(), "inventory");
         }
      });
      WANDOFCOLD.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(ICEBEAM, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.ICEBEAM.getRegistryName(), "inventory");
         }
      });
      ICEBEAM.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(SKULLCRASHER, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.SKULLCRASHER.getRegistryName(), "inventory");
         }
      });
      SKULLCRASHER.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(SPELLHAMMER, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.SPELLHAMMER.getRegistryName(), "inventory");
         }
      });
      SPELLHAMMER.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(ACIDFIRE, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.ACIDFIRE.getRegistryName(), "inventory");
         }
      });
      ACIDFIRE.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(GLOWBLADE, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.GLOWBLADE.getRegistryName(), "inventory");
         }
      });
      GLOWBLADE.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(NAILGUN, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.NAILGUN.getRegistryName(), "inventory");
         }
      });
      NAILGUN.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(CRYSTALSTAR, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.CRYSTALSTAR.getRegistryName(), "inventory");
         }
      });
      CRYSTALSTAR.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(TOXINIUMSHOTGUN, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.TOXINIUMSHOTGUN.getRegistryName(), "inventory");
         }
      });
      TOXINIUMSHOTGUN.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(SUBMACHINE, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.SUBMACHINE.getRegistryName(), "inventory");
         }
      });
      SUBMACHINE.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(THISTLETHORN, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.THISTLETHORN.getRegistryName(), "inventory");
         }
      });
      THISTLETHORN.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(RESTLESSSKULL, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.RESTLESSSKULL.getRegistryName(), "inventory");
         }
      });
      RESTLESSSKULL.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MAGICROCKET, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.MAGICROCKET.getRegistryName(), "inventory");
         }
      });
      MAGICROCKET.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(STINGINGCELL, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.STINGINGCELL.getRegistryName(), "inventory");
         }
      });
      STINGINGCELL.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(SEAEFFLORESCE, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.SEAEFFLORESCE.getRegistryName(), "inventory");
         }
      });
      SEAEFFLORESCE.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(CORALRIFLE, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.CORALRIFLE.getRegistryName(), "inventory");
         }
      });
      CORALRIFLE.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(HADRONBLASTER, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.HADRONBLASTER.getRegistryName(), "inventory");
         }
      });
      HADRONBLASTER.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(SNAPBALL, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.SNAPBALL.getRegistryName(), "inventory");
         }
      });
      SNAPBALL.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(ROCKETLAUNCHER, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.ROCKETLAUNCHER.getRegistryName(), "inventory");
         }
      });
      ROCKETLAUNCHER.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(CHAINMACEIRON, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.CHAINMACEIRON.getRegistryName(), "inventory");
         }
      });
      CHAINMACEIRON.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(CHAINMACEDIAMOND, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.CHAINMACEDIAMOND.getRegistryName(), "inventory");
         }
      });
      CHAINMACEDIAMOND.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(CHAINMACEMOLTEN, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.CHAINMACEMOLTEN.getRegistryName(), "inventory");
         }
      });
      CHAINMACEMOLTEN.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(ICEBREAKER, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.ICEBREAKER.getRegistryName(), "inventory");
         }
      });
      ICEBREAKER.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(ADAMANTIUMREVOLVER, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.ADAMANTIUMREVOLVER.getRegistryName(), "inventory");
         }
      });
      ADAMANTIUMREVOLVER.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(ADAMANTIUMBATTLEAXE, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.ADAMANTIUMBATTLEAXE.getRegistryName(), "inventory");
         }
      });
      ADAMANTIUMBATTLEAXE.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(ENDERPROTECTOR, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.ENDERPROTECTOR.getRegistryName(), "inventory");
         }
      });
      ENDERPROTECTOR.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(DRAGONTAIL, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.DRAGONTAIL.getRegistryName(), "inventory");
         }
      });
      DRAGONTAIL.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(DRAGONSHELL, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.DRAGONSHELL.getRegistryName(), "inventory");
         }
      });
      DRAGONSHELL.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(WINTERBREATH, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.WINTERBREATH.getRegistryName(), "inventory");
         }
      });
      WINTERBREATH.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(TOXINIUMSHIELD, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.TOXINIUMSHIELD.getRegistryName(), "inventory");
         }
      });
      TOXINIUMSHIELD.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(CARAPACE, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.CARAPACE.getRegistryName(), "inventory");
         }
      });
      CARAPACE.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(ROTTENSHIELD, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.ROTTENSHIELD.getRegistryName(), "inventory");
         }
      });
      ROTTENSHIELD.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(HELLMARK, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.HELLMARK.getRegistryName(), "inventory");
         }
      });
      HELLMARK.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(BLOWHOLE, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.BLOWHOLE.getRegistryName(), "inventory");
         }
      });
      BLOWHOLE.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(CRYSTALCUTTER, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.CRYSTALCUTTER.getRegistryName(), "inventory");
         }
      });
      CRYSTALCUTTER.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(PLASMAMINIGUN, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.PLASMAMINIGUN.getRegistryName(), "inventory");
         }
      });
      PLASMAMINIGUN.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(CERATARGET, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.CERATARGET.getRegistryName(), "inventory");
         }
      });
      CERATARGET.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(GRENADELAUNCHER, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.GRENADELAUNCHER.getRegistryName(), "inventory");
         }
      });
      GRENADELAUNCHER.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(HOLYSHOTGUN, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.HOLYSHOTGUN.getRegistryName(), "inventory");
         }
      });
      HOLYSHOTGUN.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(ECHINUS, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.ECHINUS.getRegistryName(), "inventory");
         }
      });
      ECHINUS.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MITHRILBOW, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.MITHRILBOW.getRegistryName(), "inventory");
         }
      });
      MITHRILBOW.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(COMPOUNDBOW, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.COMPOUNDBOW.getRegistryName(), "inventory");
         }
      });
      COMPOUNDBOW.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(STAFFOFTHEAZUREORE, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.STAFFOFTHEAZUREORE.getRegistryName(), "inventory");
         }
      });
      STAFFOFTHEAZUREORE.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(INSTANCER, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.INSTANCER.getRegistryName(), "inventory");
         }
      });
      INSTANCER.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(ENDERINSTANCER, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.ENDERINSTANCER.getRegistryName(), "inventory");
         }
      });
      ENDERINSTANCER.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(WINTERINSTANCER, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.WINTERINSTANCER.getRegistryName(), "inventory");
         }
      });
      WINTERINSTANCER.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MILITARYINSTANCER, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.MILITARYINSTANCER.getRegistryName(), "inventory");
         }
      });
      MILITARYINSTANCER.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(AQUATICINSTANCER, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.AQUATICINSTANCER.getRegistryName(), "inventory");
         }
      });
      AQUATICINSTANCER.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(VIOLENCE, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.VIOLENCE.getRegistryName(), "inventory");
         }
      });
      VIOLENCE.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(STAFFOFWITHERDRY, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.STAFFOFWITHERDRY.getRegistryName(), "inventory");
         }
      });
      STAFFOFWITHERDRY.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(WHISPERSBLADE, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.WHISPERSBLADE.getRegistryName(), "inventory");
         }
      });
      WHISPERSBLADE.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(VOLTRIDENT, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.VOLTRIDENT.getRegistryName(), "inventory");
         }
      });
      VOLTRIDENT.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(PUMPSHOTGUN, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.PUMPSHOTGUN.getRegistryName(), "inventory");
         }
      });
      PUMPSHOTGUN.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(GOTHICBOW, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.GOTHICBOW.getRegistryName(), "inventory");
         }
      });
      GOTHICBOW.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(CINDERBOW, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.CINDERBOW.getRegistryName(), "inventory");
         }
      });
      CINDERBOW.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(COOLEDRIFLE, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.COOLEDRIFLE.getRegistryName(), "inventory");
         }
      });
      COOLEDRIFLE.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(AQUATICBOW, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.AQUATICBOW.getRegistryName(), "inventory");
         }
      });
      AQUATICBOW.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(BEAKER, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.BEAKER.getRegistryName(), "inventory");
         }
      });
      BEAKER.setTileEntityItemStackRenderer(TEISROther.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(SPELLPLIERS, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.SPELLPLIERS.getRegistryName(), "inventory");
         }
      });
      SPELLPLIERS.setTileEntityItemStackRenderer(TEISROther.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(VIAL, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.VIAL.getRegistryName(), "inventory");
         }
      });
      VIAL.setTileEntityItemStackRenderer(TEISROther.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(SPELLROLL, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.SPELLROLL.getRegistryName(), "inventory");
         }
      });
      SPELLROLL.setTileEntityItemStackRenderer(TEISROther.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(XMASSLAUNCHER, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.XMASSLAUNCHER.getRegistryName(), "inventory");
         }
      });
      XMASSLAUNCHER.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(ADAMANTIUMMINIGUN, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.ADAMANTIUMMINIGUN.getRegistryName(), "inventory");
         }
      });
      ADAMANTIUMMINIGUN.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(BUZDYGAN, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.BUZDYGAN.getRegistryName(), "inventory");
         }
      });
      BUZDYGAN.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(WHIP, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.WHIP.getRegistryName(), "inventory");
         }
      });
      WHIP.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(MAULER, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.MAULER.getRegistryName(), "inventory");
         }
      });
      MAULER.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(SNAKEWHIP, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.SNAKEWHIP.getRegistryName(), "inventory");
         }
      });
      SNAKEWHIP.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(SPELLROD, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.SPELLROD.getRegistryName(), "inventory");
         }
      });
      SPELLROD.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(CRYODESTROYER, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.CRYODESTROYER.getRegistryName(), "inventory");
         }
      });
      CRYODESTROYER.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(HYDRAULICSHOTGUN, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.HYDRAULICSHOTGUN.getRegistryName(), "inventory");
         }
      });
      HYDRAULICSHOTGUN.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(ICECOMPASS, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.ICECOMPASS.getRegistryName(), "inventory");
         }
      });
      ICECOMPASS.setTileEntityItemStackRenderer(TEISRGuns.instance);
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(CANISTER, new ItemMeshDefinition() {
         public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(ItemsRegister.CANISTER.getRegistryName(), "inventory");
         }
      });
      CANISTER.setTileEntityItemStackRenderer(TEISROther.instance);

      for (final Item item : ItemMagicScroll.magicScrolls.values()) {
         Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, new ItemMeshDefinition() {
            public ModelResourceLocation getModelLocation(ItemStack stack) {
               return new ModelResourceLocation(item.getRegistryName(), "inventory");
            }
         });
         item.setTileEntityItemStackRenderer(TEISRGuns.instance);
      }

      for (Item item : ItemGrenade.registry.values()) {
         Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, new ItemMeshDefinition() {
            public ModelResourceLocation getModelLocation(ItemStack stack) {
               return new ModelResourceLocation(ItemsRegister.GRENADECLASSIC.getRegistryName(), "inventory");
            }
         });
         item.setTileEntityItemStackRenderer(TEISRGuns.instance);
      }

      try {
         Field[] fields = ItemsRegister.class.getFields();

         for (Field field : fields) {
            if (field.getType() == Item.class) {
               final Item item = (Item)field.get(null);
               if (item instanceof ItemCalibrationThing || item instanceof AbstractMiningTool) {
                  Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, new ItemMeshDefinition() {
                     public ModelResourceLocation getModelLocation(ItemStack stack) {
                        return new ModelResourceLocation(item.getRegistryName(), "inventory");
                     }
                  });
                  item.setTileEntityItemStackRenderer(TEISROther.instance);
               }
            }
         }
      } catch (Exception var7) {
         var7.printStackTrace();
      }
   }

   public static void registerItems() throws IllegalAccessException {
      Field[] fields = ItemsRegister.class.getFields();

      for (Field field : fields) {
         if (field.getType() == Item.class) {
            Item item = (Item)field.get(new ItemsRegister());
            ForgeRegistries.ITEMS.register(item);
         }
      }

      ItemMagicScroll.createScrolls();

      for (Item item : ItemMagicScroll.magicScrolls.values()) {
         ForgeRegistries.ITEMS.register(item);
      }

      BlocksRegister.setupAfterItems();
      ItemBullet.init();
      ItemRocket.init();
   }
}
