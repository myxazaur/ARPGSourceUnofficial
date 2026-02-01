package com.vivern.arpg.mobs;

import com.vivern.arpg.elements.ItemBullet;
import com.vivern.arpg.elements.models.HailblastModel;
import com.vivern.arpg.elements.models.PistolFishStrikeModel;
import com.vivern.arpg.elements.models.RocketModel;
import com.vivern.arpg.elements.models.ThornkeeperShootModel;
import com.vivern.arpg.entity.BetweenParticle;
import com.vivern.arpg.entity.CrystalFanShoot;
import com.vivern.arpg.entity.EntityCubicParticle;
import com.vivern.arpg.entity.EntityElectricBolt;
import com.vivern.arpg.entity.IEntitySynchronize;
import com.vivern.arpg.entity.StandardBullet;
import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.main.Booom;
import com.vivern.arpg.main.Catalyst;
import com.vivern.arpg.main.DeathEffects;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.AbstractRPG;
import com.vivern.arpg.main.Mana;
import com.vivern.arpg.main.MovingSoundEntity;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.SuperKnockback;
import com.vivern.arpg.main.Team;
import com.vivern.arpg.main.WeaponDamage;
import com.vivern.arpg.main.WeaponParameters;
import com.vivern.arpg.main.Weapons;
import com.vivern.arpg.potions.Freezing;
import com.vivern.arpg.potions.PotionEffects;
import com.vivern.arpg.renders.AnimatedGParticle;
import com.vivern.arpg.renders.BlockEntityFactory;
import com.vivern.arpg.renders.CustomArrowFactory;
import com.vivern.arpg.renders.GUNParticle;
import com.vivern.arpg.renders.IRenderOptions;
import com.vivern.arpg.renders.ParticleTracker;
import com.vivern.arpg.renders.RenderLikeArrow;
import com.vivern.arpg.renders.RenderModular;
import com.vivern.arpg.renders.RenderModule;
import com.vivern.arpg.renders.RenderRocketFactory;
import com.vivern.arpg.renders.RenderSpecial;
import com.vivern.arpg.renders.RenderSplash;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.PotionTypes;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class HostileProjectiles {
   public static void register() {
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "arrow_radioactive"),
         ArrowRadioactive.class,
         "arpg:arrow_radioactive",
         150,
         AbstractRPG.instance,
         64,
         20,
         true
      );
      EntityRegistry.registerModEntity(new ResourceLocation("arpg", "rocket"), Rocket.class, "arpg:rocket", 151, AbstractRPG.instance, 64, 20, true);
      EntityRegistry.registerModEntity(new ResourceLocation("arpg", "bullet"), Bullet.class, "arpg:bullet", 152, AbstractRPG.instance, 64, 20, true);
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "dron_laser"), DronLaser.class, "arpg:dron_laser", 153, AbstractRPG.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "flower_acid"), FlowerAcidShoot.class, "arpg:flower_acid", 154, AbstractRPG.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "skyguard_shoot"), SkyGuardShoot.class, "arpg:skyguard_shoot", 155, AbstractRPG.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "skyguard_shield"), SkyGuardShield.class, "arpg:skyguard_shield", 156, AbstractRPG.instance, 64, 1, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "ice_shard_shoot"), IceShardShoot.class, "arpg:ice_shard_shoot", 157, AbstractRPG.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "thunderbird_shoot"),
         ThunderbirdShoot.class,
         "arpg:thunderbird_shoot",
         158,
         AbstractRPG.instance,
         64,
         20,
         true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "zarpion_blaster"), ZarpionBlaterShoot.class, "arpg:zarpion_blaster", 159, AbstractRPG.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "mob_grenade"), Grenade.class, "arpg:mob_grenade", 160, AbstractRPG.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "zarpion_beam"), ZarpionBeam.class, "arpg:zarpion_beam", 161, AbstractRPG.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "gust_cloud"), GustCloud.class, "arpg:gust_cloud", 162, AbstractRPG.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(new ResourceLocation("arpg", "whirl"), Whirl.class, "arpg:whirl", 163, AbstractRPG.instance, 64, 2, true);
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "windbreak_shoot"), WindbreakShoot.class, "arpg:windbreak_shoot", 164, AbstractRPG.instance, 64, 2, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "slime_cloud"), SlimeCloud.class, "arpg:slime_cloud", 165, AbstractRPG.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "winter_fury_breath"),
         WinterFuryBreath.class,
         "arpg:winter_fury_breath",
         166,
         AbstractRPG.instance,
         64,
         20,
         true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "hailblast"), Hailblast.class, "arpg:hailblast", 167, AbstractRPG.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "blisterfish_shoot"),
         BlisterfishShoot.class,
         "arpg:blisterfish_shoot",
         168,
         AbstractRPG.instance,
         64,
         20,
         true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "wizardfish_shoot"), WizardfishShoot.class, "arpg:wizardfish_shoot", 169, AbstractRPG.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "arrow_dartfish"), ArrowDartfish.class, "arpg:arrow_dartfish", 170, AbstractRPG.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "trachymona_shoot"), TrachymonaShoot.class, "arpg:trachymona_shoot", 171, AbstractRPG.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "lava_shoot"), LavaShoot.class, "arpg:lava_shoot", 172, AbstractRPG.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "poison_spit"), PoisonSpit.class, "arpg:poison_spit", 173, AbstractRPG.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "ocean_shoot"), OceanSpiritShoot.class, "arpg:ocean_shoot", 174, AbstractRPG.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "sea_bomb"), SeaBomb.class, "arpg:sea_bomb", 175, AbstractRPG.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "circle_blast"), CircleBlast.class, "arpg:circle_blast", 176, AbstractRPG.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "plasma_ring"), PlasmaRing.class, "arpg:plasma_ring", 177, AbstractRPG.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "mermaid_shoot"), MermaidShoot.class, "arpg:mermaid_shoot", 178, AbstractRPG.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "arrow_mermaid"), ArrowMermaid.class, "arpg:arrow_mermaid", 179, AbstractRPG.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "siren_shoot"), SirenShoot.class, "arpg:siren_shoot", 180, AbstractRPG.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "kraken_slime"), KrakenSlime.class, "arpg:kraken_slime", 181, AbstractRPG.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "kraken_shockbolt"), KrakenShockbolt.class, "arpg:kraken_shockbolt", 182, AbstractRPG.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "bullet_cooled"), BulletCooled.class, "arpg:bullet_cooled", 183, AbstractRPG.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "watcher_laser"), WatcherLaser.class, "arpg:watcher_laser", 184, AbstractRPG.instance, 64, 20, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "horrible_emerald_shoot"),
         HorribleEmeraldShoot.class,
         "arpg:horrible_emerald_shoot",
         185,
         AbstractRPG.instance,
         64,
         20,
         true
      );
      EntityRegistry.registerModEntity(new ResourceLocation("arpg", "plasma"), Plasma.class, "arpg:plasma", 186, AbstractRPG.instance, 64, 20, true);
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "auroras_tear"), AurorasTear.class, "arpg:auroras_tear", 187, AbstractRPG.instance, 64, 1, true
      );
      EntityRegistry.registerModEntity(
         new ResourceLocation("arpg", "larva_flyer_shoot"),
         LarvaFlyerShoot.class,
         "arpg:larva_flyer_shoot",
         188,
         AbstractRPG.instance,
         64,
         20,
         true
      );
   }

   public static void registerRender() {
      RenderingRegistry.registerEntityRenderingHandler(
         ArrowRadioactive.class, new CustomArrowFactory(new ResourceLocation("arpg:textures/arrow_radioactive.png"), 0.07025F, 200)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         Rocket.class, new RenderRocketFactory("arpg:textures/rocket_bot_shoot.png", RocketModel.class, 1.1F, false)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         DronLaser.class,
         new RenderLikeArrow.RenderLikeArrowFactory(new ResourceLocation("arpg:textures/arrow_laser.png"), 0.1025F, 240, false)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         FlowerAcidShoot.class,
         new RenderSplash("arpg:textures/flower_acid.png", 0.2F, 1, 0.2F, 0.0F, 0.0F, 0.0F, true, 180, DestFactor.ONE)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         SkyGuardShoot.class,
         new RenderLikeArrow.RenderLikeArrowFactory(new ResourceLocation("arpg:textures/arrow_skyguard.png"), 0.1125F, 240, true)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         IceShardShoot.class,
         new RenderLikeArrow.RenderLikeArrowFactory(new ResourceLocation("arpg:textures/arrow_iceshard.png"), 0.0725F, 180, false)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         ThunderbirdShoot.class,
         new RenderSplash("arpg:textures/blueexplode3.png", 0.35F, 1, 0.35F, 0.0F, 0.0F, 0.0F, true, 240, DestFactor.ONE)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         ZarpionBlaterShoot.class,
         new RenderLikeArrow.RenderLikeArrowFactory(new ResourceLocation("arpg:textures/arrow_zarpion.png"), 0.0625F, 240, true)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         Grenade.class,
         new BlockEntityFactory(
            new ResourceLocation("arpg:textures/mob_grenade.png"), 0.017F, 0.017F, 0.017F, 100, 0.6F, 0.7F, 1.0F, false, 1.0F, 1.0F, 1.0F, 0.08F, true
         )
      );
      RenderingRegistry.registerEntityRenderingHandler(
         ZarpionBeam.class, new RenderSplash("arpg:textures/bluelaser.png", 0.09F, 1, 0.09F, 0.0F, 0.0F, 0.0F, true, 240, DestFactor.ONE)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         GustCloud.class,
         new BlockEntityFactory(
            new ResourceLocation("arpg:textures/clouds4.png"), 0.07F, 0.07F, 0.07F, 50, 0.9F, 0.9F, 0.9F, true, 1.0F, 1.0F, 1.0F, 0.1F, true
         )
      );
      RenderingRegistry.registerEntityRenderingHandler(
         WindbreakShoot.class,
         new RenderSplash("arpg:textures/plasma_ball.png", 0.23F, 15, 0.23F, 0.0F, 0.0F, 0.0F, true, 240, DestFactor.ONE)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         SkyGuardShield.class, new RenderSpecial.RenderSpecialFactory(4, new ResourceLocation("arpg:textures/skyguard_shield.png"))
      );
      RenderingRegistry.registerEntityRenderingHandler(
         WinterFuryBreath.class,
         new RenderSplash("arpg:textures/clouds3.png", 0.35F, 1, 0.35F, 0.0F, 0.0F, 0.0F, true, 220, DestFactor.ONE, true).setColor(0.5F, 0.85F, 1.0F)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         Hailblast.class, new RenderRocketFactory("arpg:textures/hailblast.png", HailblastModel.class, 1.1F, false)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         BlisterfishShoot.class,
         new RenderSplash("arpg:textures/blob.png", 0.11F, 1, 0.11F, 0.0F, 0.0F, 0.0F, true, 200, DestFactor.ONE).setColor(0.4F, 0.95F, 0.9F)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         WizardfishShoot.class,
         new RenderSplash("arpg:textures/simple_magic_shoot.png", 0.09F, 1, 0.09F, 0.0F, 0.0F, 0.0F, true, 220, DestFactor.ONE).setColor(1.0F, 0.75F, 1.0F)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         ArrowDartfish.class, new CustomArrowFactory(new ResourceLocation("arpg:textures/arrow_dartfish.png"), 0.07325F, 230)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         TrachymonaShoot.class, new RenderSplash("arpg:textures/redball.png", 0.06F, 1, 0.06F, 0.0F, 0.0F, 0.0F, false, 180, DestFactor.ONE)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         LavaShoot.class, new RenderSplash("arpg:textures/sparkle.png", 0.06F, 1, 0.06F, 0.0F, 0.0F, 0.0F, true, 180, DestFactor.ONE)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         PoisonSpit.class, new RenderSplash("arpg:textures/acid_splash5.png", 0.06F, 1, 0.06F, 0.0F, 0.0F, 0.0F, false, 150, DestFactor.ONE)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         OceanSpiritShoot.class,
         new RenderSplash("arpg:textures/ocean_shoot.png", 0.28F, 1, 0.28F, 0.0F, 0.0F, 0.0F, true, 220, DestFactor.ONE)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         SeaBomb.class, new RenderRocketFactory("arpg:textures/sea_bomb.png", ThornkeeperShootModel.class, 0.85F, false, 0.0F, true)
      );
      RenderingRegistry.registerEntityRenderingHandler(CircleBlast.class, new RenderSpecial.RenderSpecialFactory(15, null));
      RenderingRegistry.registerEntityRenderingHandler(
         PlasmaRing.class, new RenderSpecial.RenderSpecialFactory(16, new ResourceLocation("arpg:textures/plasma_ring.png"))
      );
      RenderingRegistry.registerEntityRenderingHandler(
         MermaidShoot.class,
         new RenderSplash("arpg:textures/circle2.png", 0.35F, 1, 0.35F, 0.0F, 0.0F, 0.0F, true, 240, DestFactor.ONE).setColor(1.0F, 1.0F, 0.3F)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         ArrowMermaid.class, new CustomArrowFactory(new ResourceLocation("arpg:textures/arrow_water.png"), 0.07F, 190)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         SirenShoot.class,
         new RenderSplash("arpg:textures/siren_shoot.png", 0.25F, 1, 0.25F, 0.0F, 0.0F, 0.0F, true, 240, DestFactor.ONE_MINUS_SRC_ALPHA)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         KrakenSlime.class, new RenderSpecial.RenderSpecialFactory(18, new ResourceLocation("arpg:textures/gloss_map_3.png"))
      );
      RenderingRegistry.registerEntityRenderingHandler(
         KrakenShockbolt.class,
         new RenderSplash("arpg:textures/azure_ore_shoot.png", 0.4F, 4, 0.4F, 0.0F, 0.0F, 0.0F, true, 240, DestFactor.ONE).setColor(0.9F, 0.6F, 1.0F)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         BulletCooled.class,
         new BlockEntityFactory(
            new ResourceLocation("arpg:textures/bullet_tex.png"), 0.006F, 0.006F, 0.006F, 170, 1.0F, 1.0F, 1.0F, false, 1.0F, 1.0F, 1.0F, 0.08F, false
         )
      );
      RenderingRegistry.registerEntityRenderingHandler(
         WatcherLaser.class,
         new RenderLikeArrow.RenderLikeArrowFactory(new ResourceLocation("arpg:textures/arrow_watcherlaser.png"), 0.1025F, 240, false)
      );
      RenderingRegistry.registerEntityRenderingHandler(
         HorribleEmeraldShoot.class,
         new RenderSpecial.RenderSpecialFactory(21, new ResourceLocation("arpg:textures/horrible_emerald_shoot_model_tex.png"))
      );
      RenderingRegistry.registerEntityRenderingHandler(
         Plasma.class,
         new RenderModular.RenderModularFactory()
            .add(
               new RenderModule.RMod2dSprite(new ResourceLocation("arpg:textures/plasma_b.png"), 0.5F, 200, 1.0F, 1.0F, 1.0F, 1.0F, true, 0.0F)
                  .setAnimation(24, 1, 0, false, true)
                  .setMulticolored()
                  .setBlendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE)
            )
            .add(
               new RenderModule.RMod2dSprite(new ResourceLocation("arpg:textures/plasma_a.png"), 0.3F, 240, 1.0F, 1.0F, 1.0F, 1.0F, true, -0.8F)
                  .setAnimation(14, 1, 0, false, true)
                  .setMulticolored()
                  .setBlendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE)
            )
      );
      RenderingRegistry.registerEntityRenderingHandler(
         AurorasTear.class,
         new RenderModular.RenderModularFactory()
            .add(
               new RenderModule.RMod2dSprite(new ResourceLocation("arpg:textures/plasma_b.png"), 0.4F, 240, 1.0F, 1.0F, 1.0F, 1.0F, true, 0.0F)
                  .setAnimation(24, 1, 0, false, true)
                  .setMulticolored()
                  .setBlendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE)
            )
            .add(
               new RenderModule.RMod2dSprite(new ResourceLocation("arpg:textures/plasma_a.png"), 0.3F, 240, 1.0F, 1.0F, 1.0F, 1.0F, true, -0.8F)
                  .setAnimation(14, 1, 0, false, true)
                  .setMulticolored()
                  .setBlendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE)
            )
            .add(
               new RenderModule.RMod2dSprite(new ResourceLocation("arpg:textures/circle_aurora.png"), 0.85F, 200, 0.75F, 0.75F, 0.75F, 1.0F, true, 0.0F)
                  .setRotation(Vec3d.ZERO, new Vec3d(0.0, 0.0, 1.0), true, true)
                  .setBlendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE)
            )
      );
      RenderingRegistry.registerEntityRenderingHandler(
         LarvaFlyerShoot.class,
         new RenderModular.RenderModularFactory()
            .add(
               new RenderModule.RModModel(
                  new ResourceLocation("arpg:textures/pistol_fish_strike_model_tex.png"),
                  new PistolFishStrikeModel(),
                  0.078125F,
                  -1,
                  1.0F,
                  1.0F,
                  0.9F,
                  1.0F,
                  false,
                  3,
                  0.0F
               )
            )
            .add(
               new RenderModule.RModCeratargetTail(
                  new ResourceLocation("arpg:textures/bullet_volumetric_tail.png"), 1.0F, 0.9F, 0.4F, 0.72F, 0.86F, 0.9F, 4.5F, 0.5F, 3.0F, 0.05F, 0.01F, 200
               )
            )
      );
      RenderingRegistry.registerEntityRenderingHandler(
         Bullet.class,
         new RenderModular.RenderModularFactory()
            .add(new RenderModule.RModCubic(new ResourceLocation("arpg:textures/bullet_tex.png"), 0.004F, -1, 1.0F, 1.0F, 1.0F, 1.0F, false, 1, 0.0F))
            .add(
               new RenderModule.RModCeratargetTail(
                  new ResourceLocation("arpg:textures/bullet_volumetric_tail.png"), 0.8F, 0.8F, 0.8F, 0.6F, 0.6F, 0.6F, 1.5F, 0.25F, 1.0F, 0.05F, 0.02F, 190
               )
            )
      );
   }

   public static class ArrowDartfish extends EntityArrow {
      private static final DataParameter<Integer> COLOR = EntityDataManager.createKey(
         ArrowDartfish.class, DataSerializers.VARINT
      );
      private PotionType potion = PotionTypes.EMPTY;
      private final Set<PotionEffect> customPotionEffects = Sets.newHashSet();
      private boolean fixedColor;
      public float firee = 0.0F;
      public int ticksAir = 0;

      public ArrowDartfish(World worldIn) {
         super(worldIn);
         this.setDamage(3.0);
      }

      public ArrowDartfish(World worldIn, double x, double y, double z) {
         super(worldIn, x, y, z);
         this.setDamage(3.0);
      }

      public ArrowDartfish(World worldIn, EntityLivingBase shooter) {
         super(worldIn, shooter);
         this.setDamage(3.0);
      }

      public void setPotionEffect() {
      }

      protected void entityInit() {
         super.entityInit();
         this.dataManager.register(COLOR, -1);
      }

      protected void onHit(RayTraceResult raytraceResultIn) {
         if (raytraceResultIn.entityHit != null) {
            raytraceResultIn.entityHit.hurtResistantTime = 0;
            if (raytraceResultIn.entityHit instanceof EntityLivingBase) {
               EntityLivingBase entitylb = (EntityLivingBase)raytraceResultIn.entityHit;
               PotionEffect eff = entitylb.getActivePotionEffect(PotionEffects.SLIME);
               PotionEffect eff2 = entitylb.getActivePotionEffect(MobEffects.POISON);
               if (eff != null) {
                  if (eff2 != null) {
                     entitylb.addPotionEffect(
                        new PotionEffect(
                           MobEffects.POISON, Math.max(eff.getDuration(), eff2.getDuration()), Math.min(eff2.getAmplifier() + eff.getAmplifier(), 10)
                        )
                     );
                  } else {
                     entitylb.addPotionEffect(new PotionEffect(MobEffects.POISON, eff.getDuration(), eff.getAmplifier()));
                  }

                  entitylb.removePotionEffect(PotionEffects.SLIME);
               }
            }
         }

         this.world.setEntityState(this, (byte)8);
         super.onHit(raytraceResultIn);
      }

      public void onUpdate() {
         super.onUpdate();
         if (this.world.isRemote) {
            if (this.inGround) {
               if (this.timeInGround % 5 == 0) {
                  this.spawnPotionParticles(1);
               }
            } else {
               this.spawnPotionParticles(2);
            }
         } else if (this.inGround && this.timeInGround != 0 && !this.customPotionEffects.isEmpty() && this.timeInGround >= 600) {
            this.world.setEntityState(this, (byte)0);
            this.potion = PotionTypes.EMPTY;
            this.customPotionEffects.clear();
            this.dataManager.set(COLOR, -1);
         }

         if (this.ticksAir > 40) {
            this.setDead();
         }

         if (!this.inGround) {
            this.ticksAir++;
            if (!this.hasNoGravity()) {
               this.motionY += 0.02;
            }
         }
      }

      private void spawnPotionParticles(int particleCount) {
         int i = this.getColor();
         if (i != -1 && particleCount > 0) {
            double d0 = (i >> 16 & 0xFF) / 255.0;
            double d1 = (i >> 8 & 0xFF) / 255.0;
            double d2 = (i >> 0 & 0xFF) / 255.0;

            for (int j = 0; j < particleCount; j++) {
               this.world
                  .spawnParticle(
                     EnumParticleTypes.SPELL_MOB,
                     this.posX + (this.rand.nextDouble() - 0.5) * this.width,
                     this.posY + this.rand.nextDouble() * this.height,
                     this.posZ + (this.rand.nextDouble() - 0.5) * this.width,
                     d0,
                     d1,
                     d2,
                     new int[0]
                  );
            }
         }
      }

      public int getColor() {
         return (Integer)this.dataManager.get(COLOR);
      }

      private void setFixedColor(int p_191507_1_) {
         this.fixedColor = true;
         this.dataManager.set(COLOR, p_191507_1_);
      }

      public static void registerFixesTippedArrow(DataFixer fixer) {
         EntityArrow.registerFixesArrow(fixer, "TippedArrow");
      }

      public void writeEntityToNBT(NBTTagCompound compound) {
         super.writeEntityToNBT(compound);
         if (this.potion != PotionTypes.EMPTY && this.potion != null) {
            compound.setString("Potion", ((ResourceLocation)PotionType.REGISTRY.getNameForObject(this.potion)).toString());
         }

         if (this.fixedColor) {
            compound.setInteger("Color", this.getColor());
         }

         if (!this.customPotionEffects.isEmpty()) {
            NBTTagList nbttaglist = new NBTTagList();

            for (PotionEffect potioneffect : this.customPotionEffects) {
               nbttaglist.appendTag(potioneffect.writeCustomPotionEffectToNBT(new NBTTagCompound()));
            }

            compound.setTag("CustomPotionEffects", nbttaglist);
         }
      }

      public void readEntityFromNBT(NBTTagCompound compound) {
         super.readEntityFromNBT(compound);
         this.setPotionEffect();
      }

      protected void arrowHit(EntityLivingBase living) {
         super.arrowHit(living);
         living.setFire(Math.round(this.firee));

         for (PotionEffect potioneffect : this.potion.getEffects()) {
            living.addPotionEffect(
               new PotionEffect(
                  potioneffect.getPotion(),
                  Math.max(potioneffect.getDuration() / 8, 1),
                  potioneffect.getAmplifier(),
                  potioneffect.getIsAmbient(),
                  potioneffect.doesShowParticles()
               )
            );
         }

         if (!this.customPotionEffects.isEmpty()) {
            for (PotionEffect potioneffect1 : this.customPotionEffects) {
               living.addPotionEffect(potioneffect1);
            }
         }
      }

      protected ItemStack getArrowStack() {
         if (this.customPotionEffects.isEmpty() && this.potion == PotionTypes.EMPTY) {
            return new ItemStack(Items.ARROW);
         } else {
            ItemStack itemstack = new ItemStack(Items.TIPPED_ARROW);
            PotionUtils.addPotionToItemStack(itemstack, this.potion);
            PotionUtils.appendEffects(itemstack, this.customPotionEffects);
            if (this.fixedColor) {
               NBTTagCompound nbttagcompound = itemstack.getTagCompound();
               if (nbttagcompound == null) {
                  nbttagcompound = new NBTTagCompound();
                  itemstack.setTagCompound(nbttagcompound);
               }

               nbttagcompound.setInteger("CustomPotionColor", this.getColor());
            }

            return itemstack;
         }
      }

      @SideOnly(Side.CLIENT)
      public void handleStatusUpdate(byte id) {
         if (id == 0) {
            int i = this.getColor();
            if (i != -1) {
               double d0 = (i >> 16 & 0xFF) / 255.0;
               double d1 = (i >> 8 & 0xFF) / 255.0;
               double d2 = (i >> 0 & 0xFF) / 255.0;

               for (int j = 0; j < 20; j++) {
                  this.world
                     .spawnParticle(
                        EnumParticleTypes.SPELL_MOB,
                        this.posX + (this.rand.nextDouble() - 0.5) * this.width,
                        this.posY + this.rand.nextDouble() * this.height,
                        this.posZ + (this.rand.nextDouble() - 0.5) * this.width,
                        d0,
                        d1 * 0.7,
                        d2 / 3.0,
                        new int[0]
                     );
               }
            }
         } else {
            super.handleStatusUpdate(id);
         }
      }

      @SideOnly(Side.CLIENT)
      public void onEntityUpdate() {
      }
   }

   public static class ArrowMermaid extends EntityArrow {
      private static final DataParameter<Integer> COLOR = EntityDataManager.createKey(ArrowMermaid.class, DataSerializers.VARINT);
      private PotionType potion = PotionTypes.EMPTY;
      private final Set<PotionEffect> customPotionEffects = Sets.newHashSet();
      private boolean fixedColor;
      public float firee = 0.0F;
      public int ticksAir = 0;

      public ArrowMermaid(World worldIn) {
         super(worldIn);
         this.setDamage(3.0);
      }

      public ArrowMermaid(World worldIn, double x, double y, double z) {
         super(worldIn, x, y, z);
         this.setDamage(3.0);
      }

      public ArrowMermaid(World worldIn, EntityLivingBase shooter) {
         super(worldIn, shooter);
         this.setDamage(3.0);
      }

      public void setPotionEffect() {
      }

      protected void entityInit() {
         super.entityInit();
         this.dataManager.register(COLOR, -1);
      }

      protected void onHit(RayTraceResult raytraceResultIn) {
         if (raytraceResultIn.entityHit != null) {
            raytraceResultIn.entityHit.hurtResistantTime = 0;
         }

         this.world.setEntityState(this, (byte)8);
         super.onHit(raytraceResultIn);
      }

      public void onUpdate() {
         super.onUpdate();
         if (this.world.isRemote) {
            if (this.inGround) {
               if (this.timeInGround % 5 == 0) {
                  this.spawnPotionParticles(1);
               }
            } else {
               this.spawnPotionParticles(2);
            }
         } else if (this.inGround && this.timeInGround != 0 && !this.customPotionEffects.isEmpty() && this.timeInGround >= 600) {
            this.world.setEntityState(this, (byte)0);
            this.potion = PotionTypes.EMPTY;
            this.customPotionEffects.clear();
            this.dataManager.set(COLOR, -1);
         }

         if (this.ticksAir > 40) {
            this.setDead();
         }

         if (!this.inGround) {
            this.ticksAir++;
            if (!this.hasNoGravity()) {
               this.motionY += 0.02;
            }
         }
      }

      private void spawnPotionParticles(int particleCount) {
         int i = this.getColor();
         if (i != -1 && particleCount > 0) {
            double d0 = (i >> 16 & 0xFF) / 255.0;
            double d1 = (i >> 8 & 0xFF) / 255.0;
            double d2 = (i >> 0 & 0xFF) / 255.0;

            for (int j = 0; j < particleCount; j++) {
               this.world
                  .spawnParticle(
                     EnumParticleTypes.SPELL_MOB,
                     this.posX + (this.rand.nextDouble() - 0.5) * this.width,
                     this.posY + this.rand.nextDouble() * this.height,
                     this.posZ + (this.rand.nextDouble() - 0.5) * this.width,
                     d0,
                     d1,
                     d2,
                     new int[0]
                  );
            }
         }
      }

      public int getColor() {
         return (Integer)this.dataManager.get(COLOR);
      }

      private void setFixedColor(int p_191507_1_) {
         this.fixedColor = true;
         this.dataManager.set(COLOR, p_191507_1_);
      }

      public static void registerFixesTippedArrow(DataFixer fixer) {
         EntityArrow.registerFixesArrow(fixer, "TippedArrow");
      }

      public void writeEntityToNBT(NBTTagCompound compound) {
         super.writeEntityToNBT(compound);
         if (this.potion != PotionTypes.EMPTY && this.potion != null) {
            compound.setString("Potion", ((ResourceLocation)PotionType.REGISTRY.getNameForObject(this.potion)).toString());
         }

         if (this.fixedColor) {
            compound.setInteger("Color", this.getColor());
         }

         if (!this.customPotionEffects.isEmpty()) {
            NBTTagList nbttaglist = new NBTTagList();

            for (PotionEffect potioneffect : this.customPotionEffects) {
               nbttaglist.appendTag(potioneffect.writeCustomPotionEffectToNBT(new NBTTagCompound()));
            }

            compound.setTag("CustomPotionEffects", nbttaglist);
         }
      }

      public void readEntityFromNBT(NBTTagCompound compound) {
         super.readEntityFromNBT(compound);
         this.setPotionEffect();
      }

      protected void arrowHit(EntityLivingBase living) {
         super.arrowHit(living);
         living.setFire(Math.round(this.firee));

         for (PotionEffect potioneffect : this.potion.getEffects()) {
            living.addPotionEffect(
               new PotionEffect(
                  potioneffect.getPotion(),
                  Math.max(potioneffect.getDuration() / 8, 1),
                  potioneffect.getAmplifier(),
                  potioneffect.getIsAmbient(),
                  potioneffect.doesShowParticles()
               )
            );
         }

         if (!this.customPotionEffects.isEmpty()) {
            for (PotionEffect potioneffect1 : this.customPotionEffects) {
               living.addPotionEffect(potioneffect1);
            }
         }

         if (living.isPotionActive(MobEffects.WATER_BREATHING)) {
            Weapons.mixPotion(
               living,
               MobEffects.WATER_BREATHING,
               0.7F,
               0.0F,
               Weapons.EnumPotionMix.WITH_MINIMUM,
               Weapons.EnumPotionMix.WITH_MINIMUM,
               Weapons.EnumMathOperation.MULTIPLY,
               Weapons.EnumMathOperation.NONE,
               0,
               0
            );
         } else {
            living.setAir(living.getAir() - 90);
         }
      }

      protected ItemStack getArrowStack() {
         if (this.customPotionEffects.isEmpty() && this.potion == PotionTypes.EMPTY) {
            return new ItemStack(Items.ARROW);
         } else {
            ItemStack itemstack = new ItemStack(Items.TIPPED_ARROW);
            PotionUtils.addPotionToItemStack(itemstack, this.potion);
            PotionUtils.appendEffects(itemstack, this.customPotionEffects);
            if (this.fixedColor) {
               NBTTagCompound nbttagcompound = itemstack.getTagCompound();
               if (nbttagcompound == null) {
                  nbttagcompound = new NBTTagCompound();
                  itemstack.setTagCompound(nbttagcompound);
               }

               nbttagcompound.setInteger("CustomPotionColor", this.getColor());
            }

            return itemstack;
         }
      }

      @SideOnly(Side.CLIENT)
      public void handleStatusUpdate(byte id) {
         if (id == 0) {
            int i = this.getColor();
            if (i != -1) {
               double d0 = (i >> 16 & 0xFF) / 255.0;
               double d1 = (i >> 8 & 0xFF) / 255.0;
               double d2 = (i >> 0 & 0xFF) / 255.0;

               for (int j = 0; j < 20; j++) {
                  this.world
                     .spawnParticle(
                        EnumParticleTypes.SPELL_MOB,
                        this.posX + (this.rand.nextDouble() - 0.5) * this.width,
                        this.posY + this.rand.nextDouble() * this.height,
                        this.posZ + (this.rand.nextDouble() - 0.5) * this.width,
                        d0,
                        d1 * 0.7,
                        d2 / 3.0,
                        new int[0]
                     );
               }
            }
         } else {
            super.handleStatusUpdate(id);
         }
      }

      @SideOnly(Side.CLIENT)
      public void onEntityUpdate() {
      }

      public boolean handleWaterMovement() {
         return false;
      }
   }

   public static class ArrowRadioactive extends EntityArrow {
      private static final DataParameter<Integer> COLOR = EntityDataManager.createKey(
         ArrowRadioactive.class, DataSerializers.VARINT
      );
      private PotionType potion = PotionTypes.EMPTY;
      private final Set<PotionEffect> customPotionEffects = Sets.newHashSet();
      private boolean fixedColor;
      public float firee = 0.0F;
      public int ticksAir = 0;

      public ArrowRadioactive(World worldIn) {
         super(worldIn);
         this.setDamage(5.0);
      }

      public ArrowRadioactive(World worldIn, double x, double y, double z) {
         super(worldIn, x, y, z);
         this.setDamage(5.0);
      }

      public ArrowRadioactive(World worldIn, EntityLivingBase shooter) {
         super(worldIn, shooter);
         this.setDamage(5.0);
      }

      public void setPotionEffect() {
         this.customPotionEffects.add(new PotionEffect(MobEffects.POISON, 60, 0));
      }

      protected void entityInit() {
         super.entityInit();
         this.dataManager.register(COLOR, -1);
      }

      protected void onHit(RayTraceResult raytraceResultIn) {
         if (raytraceResultIn.entityHit != null) {
            raytraceResultIn.entityHit.hurtResistantTime = 0;
         }

         this.world.setEntityState(this, (byte)8);
         super.onHit(raytraceResultIn);
      }

      public void onUpdate() {
         super.onUpdate();
         if (this.world.isRemote) {
            if (this.inGround) {
               if (this.timeInGround % 5 == 0) {
                  this.spawnPotionParticles(1);
               }
            } else {
               this.spawnPotionParticles(2);
            }
         } else if (this.inGround && this.timeInGround != 0 && !this.customPotionEffects.isEmpty() && this.timeInGround >= 600) {
            this.world.setEntityState(this, (byte)0);
            this.potion = PotionTypes.EMPTY;
            this.customPotionEffects.clear();
            this.dataManager.set(COLOR, -1);
         }

         if (this.ticksAir > 40) {
            this.setDead();
         }

         if (!this.inGround) {
            this.ticksAir++;
            if (!this.hasNoGravity()) {
               this.motionY += 0.02;
            }
         }
      }

      private void spawnPotionParticles(int particleCount) {
         int i = this.getColor();
         if (i != -1 && particleCount > 0) {
            double d0 = (i >> 16 & 0xFF) / 255.0;
            double d1 = (i >> 8 & 0xFF) / 255.0;
            double d2 = (i >> 0 & 0xFF) / 255.0;

            for (int j = 0; j < particleCount; j++) {
               this.world
                  .spawnParticle(
                     EnumParticleTypes.SPELL_MOB,
                     this.posX + (this.rand.nextDouble() - 0.5) * this.width,
                     this.posY + this.rand.nextDouble() * this.height,
                     this.posZ + (this.rand.nextDouble() - 0.5) * this.width,
                     d0,
                     d1,
                     d2,
                     new int[0]
                  );
            }
         }
      }

      public int getColor() {
         return (Integer)this.dataManager.get(COLOR);
      }

      private void setFixedColor(int p_191507_1_) {
         this.fixedColor = true;
         this.dataManager.set(COLOR, p_191507_1_);
      }

      public static void registerFixesTippedArrow(DataFixer fixer) {
         EntityArrow.registerFixesArrow(fixer, "TippedArrow");
      }

      public void writeEntityToNBT(NBTTagCompound compound) {
         super.writeEntityToNBT(compound);
         if (this.potion != PotionTypes.EMPTY && this.potion != null) {
            compound.setString("Potion", ((ResourceLocation)PotionType.REGISTRY.getNameForObject(this.potion)).toString());
         }

         if (this.fixedColor) {
            compound.setInteger("Color", this.getColor());
         }

         if (!this.customPotionEffects.isEmpty()) {
            NBTTagList nbttaglist = new NBTTagList();

            for (PotionEffect potioneffect : this.customPotionEffects) {
               nbttaglist.appendTag(potioneffect.writeCustomPotionEffectToNBT(new NBTTagCompound()));
            }

            compound.setTag("CustomPotionEffects", nbttaglist);
         }
      }

      public void readEntityFromNBT(NBTTagCompound compound) {
         super.readEntityFromNBT(compound);
         this.setPotionEffect();
      }

      protected void arrowHit(EntityLivingBase living) {
         super.arrowHit(living);
         living.setFire(Math.round(this.firee));
         if (living instanceof EntityPlayer) {
            Mana.addRad((EntityPlayer)living, 20 + this.world.getDifficulty().getId() * 14, true);
         }

         for (PotionEffect potioneffect : this.potion.getEffects()) {
            living.addPotionEffect(
               new PotionEffect(
                  potioneffect.getPotion(),
                  Math.max(potioneffect.getDuration() / 8, 1),
                  potioneffect.getAmplifier(),
                  potioneffect.getIsAmbient(),
                  potioneffect.doesShowParticles()
               )
            );
         }

         if (!this.customPotionEffects.isEmpty()) {
            for (PotionEffect potioneffect1 : this.customPotionEffects) {
               living.addPotionEffect(potioneffect1);
            }
         }
      }

      protected ItemStack getArrowStack() {
         if (this.customPotionEffects.isEmpty() && this.potion == PotionTypes.EMPTY) {
            return new ItemStack(Items.ARROW);
         } else {
            ItemStack itemstack = new ItemStack(Items.TIPPED_ARROW);
            PotionUtils.addPotionToItemStack(itemstack, this.potion);
            PotionUtils.appendEffects(itemstack, this.customPotionEffects);
            if (this.fixedColor) {
               NBTTagCompound nbttagcompound = itemstack.getTagCompound();
               if (nbttagcompound == null) {
                  nbttagcompound = new NBTTagCompound();
                  itemstack.setTagCompound(nbttagcompound);
               }

               nbttagcompound.setInteger("CustomPotionColor", this.getColor());
            }

            return itemstack;
         }
      }

      @SideOnly(Side.CLIENT)
      public void handleStatusUpdate(byte id) {
         if (id == 0) {
            int i = this.getColor();
            if (i != -1) {
               double d0 = (i >> 16 & 0xFF) / 255.0;
               double d1 = (i >> 8 & 0xFF) / 255.0;
               double d2 = (i >> 0 & 0xFF) / 255.0;

               for (int j = 0; j < 20; j++) {
                  this.world
                     .spawnParticle(
                        EnumParticleTypes.SPELL_MOB,
                        this.posX + (this.rand.nextDouble() - 0.5) * this.width,
                        this.posY + this.rand.nextDouble() * this.height,
                        this.posZ + (this.rand.nextDouble() - 0.5) * this.width,
                        d0,
                        d1 * 0.7,
                        d2 / 3.0,
                        new int[0]
                     );
               }
            }
         } else {
            super.handleStatusUpdate(id);
         }
      }

      @SideOnly(Side.CLIENT)
      public void onEntityUpdate() {
      }
   }

   public static class AurorasTear extends EntityThrowable implements RenderModule.IRenderModuleMulticolored {
      public AurorasTear(World world) {
         super(world);
         this.setSize(0.7F, 0.7F);
      }

      public AurorasTear(World world, EntityLivingBase thrower) {
         super(world, thrower);
         this.setSize(0.7F, 0.7F);
      }

      public AurorasTear(World world, double x, double y, double z) {
         super(world, x, y, z);
         this.setSize(0.7F, 0.7F);
      }

      @Override
      public Vec3d getColor(int index) {
         return EverfrostMobsPack.AurorasPhantasm.getPhantasmColor(this);
      }

      public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, EnumHand hand) {
         if (player.getHeldItem(hand).getItem() == ItemsRegister.ICECIRCLE) {
            player.getHeldItem(hand).shrink(1);
            ItemStack stack = new ItemStack(ItemsRegister.ICECIRCLEFILLED);
            if (!player.inventory.addItemStackToInventory(stack)) {
               player.dropItem(stack, false);
            }

            this.world
               .playSound(
                  (EntityPlayer)null,
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.item_misc_c,
                  SoundCategory.AMBIENT,
                  0.6F,
                  0.9F + this.rand.nextFloat() / 5.0F
               );
            this.setDead();
            return EnumActionResult.SUCCESS;
         } else {
            return EnumActionResult.PASS;
         }
      }

      public boolean canBeCollidedWith() {
         return true;
      }

      protected float getGravityVelocity() {
         BlockPos pos = this.getPosition().down(3);
         return this.world.getBlockState(pos).getCollisionBoundingBox(this.world, pos) == null ? 0.001F : 0.0F;
      }

      public void onUpdate() {
         super.onUpdate();
         if (this.ticksExisted > 1200 && !this.world.isRemote) {
            this.setDead();
         }

         this.motionX *= 0.96;
         this.motionY *= 0.96;
         this.motionZ *= 0.96;
      }

      protected void onImpact(RayTraceResult result) {
      }
   }

   public static class BlisterfishShoot extends EntityThrowable {
      static ResourceLocation blob = new ResourceLocation("arpg:textures/blob.png");
      static ResourceLocation bubble = new ResourceLocation("arpg:textures/bubble.png");
      public float damage = 0.0F;

      public BlisterfishShoot(World world) {
         super(world);
         this.setSize(0.25F, 0.25F);
      }

      public BlisterfishShoot(World world, EntityLivingBase thrower) {
         super(world, thrower);
         this.setSize(0.25F, 0.25F);
      }

      public BlisterfishShoot(World world, double x, double y, double z) {
         super(world, x, y, z);
         this.setSize(0.25F, 0.25F);
      }

      public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
         float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
         float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
         float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
         this.shoot(f, f1, f2, velocity, inaccuracy);
         this.motionX = this.motionX + entityThrower.motionX * 0.1;
         this.motionZ = this.motionZ + entityThrower.motionZ * 0.1;
         if (!entityThrower.onGround) {
            this.motionY = this.motionY + entityThrower.motionY * 0.1;
         }
      }

      protected float getGravityVelocity() {
         return 0.04F;
      }

      public void onUpdate() {
         super.onUpdate();
         if (this.ticksExisted > 60) {
            this.setDead();
         }

         if (this.world.isRemote) {
            if (this.rand.nextFloat() < 0.5) {
               GUNParticle part = new GUNParticle(
                  blob,
                  0.11F + this.rand.nextFloat() / 30.0F,
                  0.001F,
                  15 + this.rand.nextInt(15),
                  180,
                  this.world,
                  this.lastTickPosX,
                  this.lastTickPosY,
                  this.lastTickPosZ,
                  (float)this.rand.nextGaussian() / 28.0F,
                  (float)this.rand.nextGaussian() / 28.0F,
                  (float)this.rand.nextGaussian() / 28.0F,
                  0.35F + this.rand.nextFloat() * 0.25F,
                  0.8F,
                  1.0F,
                  true,
                  this.rand.nextInt(20) - 10
               );
               part.alphaTickAdding = -0.028F;
               part.scaleTickAdding = -0.004F;
               this.world.spawnEntity(part);
            } else {
               GUNParticle part = new GUNParticle(
                  bubble,
                  0.055F + this.rand.nextFloat() / 40.0F,
                  0.001F,
                  15 + this.rand.nextInt(15),
                  180,
                  this.world,
                  this.lastTickPosX,
                  this.lastTickPosY,
                  this.lastTickPosZ,
                  (float)this.rand.nextGaussian() / 28.0F,
                  (float)this.rand.nextGaussian() / 28.0F,
                  (float)this.rand.nextGaussian() / 28.0F,
                  0.35F + this.rand.nextFloat() * 0.25F,
                  0.8F,
                  0.7F,
                  false,
                  this.rand.nextInt(20) - 10
               );
               part.scaleTickAdding = -0.001F;
               this.world.spawnEntity(part);
            }
         }
      }

      public void writeEntityToNBT(NBTTagCompound compound) {
         super.writeEntityToNBT(compound);
         compound.setFloat("damage", this.damage);
      }

      public void readEntityFromNBT(NBTTagCompound compound) {
         super.readEntityFromNBT(compound);
         if (compound.hasKey("damage")) {
            this.damage = compound.getFloat("damage");
         }
      }

      @SideOnly(Side.CLIENT)
      public void handleStatusUpdate(byte id) {
         if (id == 8) {
            for (int ss = 0; ss < this.rand.nextInt(5) + 4; ss++) {
               GUNParticle part = new GUNParticle(
                  blob,
                  0.11F + this.rand.nextFloat() / 30.0F,
                  0.002F,
                  20 + this.rand.nextInt(15),
                  -1,
                  this.world,
                  this.posX,
                  this.posY,
                  this.posZ,
                  (float)this.rand.nextGaussian() / 13.0F,
                  (float)this.rand.nextGaussian() / 13.0F,
                  (float)this.rand.nextGaussian() / 13.0F,
                  0.35F + this.rand.nextFloat() * 0.25F,
                  0.8F,
                  1.0F,
                  true,
                  this.rand.nextInt(20) - 10
               );
               part.alphaTickAdding = -0.028F;
               part.scaleTickAdding = -0.004F;
               this.world.spawnEntity(part);
            }

            for (int ss = 0; ss < this.rand.nextInt(5) + 4; ss++) {
               GUNParticle part = new GUNParticle(
                  bubble,
                  0.055F + this.rand.nextFloat() / 40.0F,
                  0.002F,
                  20 + this.rand.nextInt(15),
                  -1,
                  this.world,
                  this.posX,
                  this.posY,
                  this.posZ,
                  (float)this.rand.nextGaussian() / 13.0F,
                  (float)this.rand.nextGaussian() / 13.0F,
                  (float)this.rand.nextGaussian() / 13.0F,
                  0.35F + this.rand.nextFloat() * 0.25F,
                  0.8F,
                  0.7F,
                  false,
                  this.rand.nextInt(20) - 10
               );
               part.scaleTickAdding = -0.001F;
               this.world.spawnEntity(part);
            }
         }
      }

      protected void onImpact(RayTraceResult result) {
         if (result.entityHit != null) {
            if (Team.checkIsOpponent(this.thrower, result.entityHit)
               && !this.world.isRemote
               && result.entityHit instanceof EntityLivingBase) {
               EntityLivingBase entitylivingbase = (EntityLivingBase)result.entityHit;
               Weapons.dealDamage(
                  new WeaponDamage(null, this.getThrower(), this, false, true, this, WeaponDamage.toxin),
                  this.damage,
                  this.getThrower(),
                  entitylivingbase,
                  true,
                  1.0F
               );
               entitylivingbase.hurtResistantTime = 0;
               entitylivingbase.addPotionEffect(new PotionEffect(PotionEffects.SLIME, 260, 1));
               this.world
                  .playSound(
                     (EntityPlayer)null,
                     this.posX,
                     this.posY,
                     this.posZ,
                     Sounds.bb_impact,
                     SoundCategory.AMBIENT,
                     0.5F,
                     1.2F + this.rand.nextFloat() / 5.0F
                  );
               this.world.setEntityState(this, (byte)8);
               this.setDead();
            }
         } else if (this.world
               .getBlockState(result.getBlockPos())
               .getBlock()
               .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
            != null) {
            this.world
               .playSound(
                  (EntityPlayer)null,
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.bb_impact,
                  SoundCategory.AMBIENT,
                  0.5F,
                  1.2F + this.rand.nextFloat() / 5.0F
               );
            if (!this.world.isRemote) {
               this.world.setEntityState(this, (byte)8);
               this.setDead();
            }
         }
      }

      public boolean handleWaterMovement() {
         return false;
      }
   }

   public static class Bullet extends EntityThrowable {
      public boolean bulletCollided = false;
      public ItemBullet bullet;
      public float damage = 0.0F;

      public Bullet(World world) {
         super(world);
      }

      public Bullet(World world, EntityLivingBase thrower) {
         super(world, thrower);
      }

      public Bullet(World world, double x, double y, double z) {
         super(world, x, y, z);
      }

      public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
         float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
         float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
         float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
         this.shoot(f, f1, f2, velocity, inaccuracy);
         this.motionX = this.motionX + entityThrower.motionX * 0.3;
         this.motionZ = this.motionZ + entityThrower.motionZ * 0.3;
         if (!entityThrower.onGround) {
            this.motionY = this.motionY + entityThrower.motionY * 0.3;
         }
      }

      protected float getGravityVelocity() {
         return 0.001F;
      }

      public void onUpdate() {
         super.onUpdate();
         if (this.ticksExisted > 20) {
            this.setDead();
         }

         if (this.bullet != null) {
            this.bullet.onProjectileUpdate(this);
         }
      }

      public void writeEntityToNBT(NBTTagCompound compound) {
         super.writeEntityToNBT(compound);
         compound.setFloat("damage", this.damage);
      }

      public void readEntityFromNBT(NBTTagCompound compound) {
         super.readEntityFromNBT(compound);
         if (compound.hasKey("damage")) {
            this.damage = compound.getFloat("damage");
         }
      }

      protected void onImpact(RayTraceResult result) {
         if (this.bullet != null && !this.bulletCollided) {
            this.bulletCollided = this.bullet
               .onImpact(this.world, this.getThrower(), this.posX, this.posY, this.posZ, result, this);
         }

         if (result.entityHit != null) {
            if (Team.checkIsOpponent(this.thrower, result.entityHit) && !this.world.isRemote) {
               float bdamage = this.bullet == null ? this.damage : this.damage + this.bullet.damage;
               Weapons.dealDamage(
                  new WeaponDamage(null, this.getThrower(), this, false, true, this, WeaponDamage.bullet),
                  bdamage,
                  this.getThrower(),
                  result.entityHit,
                  true,
                  0.6F
               );
               if (result.entityHit instanceof EntityLivingBase) {
                  EntityLivingBase entitylivingbase = (EntityLivingBase)result.entityHit;
                  if (this.bullet != null) {
                     this.bullet.onDamageCause(this.world, entitylivingbase, this.getThrower(), this);
                  }
               }

               result.entityHit.hurtResistantTime = 0;
               this.world
                  .playSound(
                     (EntityPlayer)null,
                     this.posX,
                     this.posY,
                     this.posZ,
                     Sounds.bullet,
                     SoundCategory.AMBIENT,
                     0.8F,
                     0.9F + this.rand.nextFloat() / 5.0F
                  );
               this.setDead();
            }
         } else if (this.world
               .getBlockState(result.getBlockPos())
               .getBlock()
               .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
            != null) {
            this.world
               .playSound(
                  (EntityPlayer)null,
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.bullet,
                  SoundCategory.AMBIENT,
                  0.8F,
                  0.9F + this.rand.nextFloat() / 5.0F
               );
            if (!this.world.isRemote) {
               this.setDead();
            }
         }
      }
   }

   public static class BulletCooled extends StandardBullet implements IEntitySynchronize {
      public static ResourceLocation texture = new ResourceLocation("arpg:textures/generic_beam2.png");
      public static ResourceLocation sparkle2 = new ResourceLocation("arpg:textures/sparkle2.png");
      public static ResourceLocation snowflake5 = new ResourceLocation("arpg:textures/snowflake5.png");
      public boolean bulletCollided = false;
      public ItemBullet bullet;
      public float damage = 0.0F;
      public float knockback = 0.0F;
      public ItemStack weaponstack = ItemStack.EMPTY;

      public BulletCooled(World world) {
         super(world);
      }

      public BulletCooled(World world, EntityLivingBase thrower) {
         super(world, thrower);
      }

      public BulletCooled(World world, double x, double y, double z) {
         super(world, x, y, z);
      }

      @Override
      protected void entityInit() {
      }

      @Override
      public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
         float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
         float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
         float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
         this.shoot(f, f1, f2, velocity, inaccuracy);
         this.motionX = this.motionX + entityThrower.motionX * 0.2;
         this.motionZ = this.motionZ + entityThrower.motionZ * 0.2;
         if (!entityThrower.onGround) {
            this.motionY = this.motionY + entityThrower.motionY * 0.1;
         }
      }

      @Override
      public float getGravityVelocity() {
         return 0.1813F;
      }

      @Override
      public void onUpdate() {
         super.onUpdate();
         if (this.ticksExisted > 30) {
            this.setDead();
         }

         if (this.bullet != null) {
            this.bullet.onProjectileUpdate(this);
            if (!this.world.isRemote && (this.ticksExisted <= 2 || this.ticksExisted % 10 == 0)) {
               IEntitySynchronize.sendSynchronize(this, 64.0, this.bullet.id);
            }
         }
      }

      @Override
      public void onClient(double... args) {
         if (args.length == 1) {
            this.bullet = ItemBullet.getItemBulletFromId((int)args[0]);
         } else if (args.length == 3) {
            GUNParticle sp = new GUNParticle(
               sparkle2,
               0.01F,
               0.0F,
               2 + this.rand.nextInt(3),
               220,
               this.world,
               args[0],
               args[1],
               args[2],
               0.0F,
               0.0F,
               0.0F,
               0.9F,
               1.0F - this.rand.nextFloat() / 5.0F,
               1.0F,
               true,
               this.rand.nextInt(360)
            );
            sp.alphaGlowing = true;
            sp.scaleTickAdding = 0.25F;
            this.world.spawnEntity(sp);
            int imax = this.rand.nextInt(3) + 1;

            for (int i = 0; i < imax; i++) {
               GUNParticle sp1 = new GUNParticle(
                  snowflake5,
                  0.04F + this.rand.nextFloat() * 0.03F,
                  0.04F,
                  8 + this.rand.nextInt(10),
                  170,
                  this.world,
                  args[0],
                  args[1],
                  args[2],
                  0.0F,
                  0.0F,
                  0.0F,
                  0.9F,
                  1.0F - this.rand.nextFloat() / 5.0F,
                  1.0F,
                  false,
                  this.rand.nextInt(360)
               );
               sp1.motionX = -this.motionX * 0.1 * this.rand.nextFloat();
               sp1.motionY = -this.motionY * 0.1 * this.rand.nextFloat();
               sp1.motionZ = -this.motionZ * 0.1 * this.rand.nextFloat();
               this.world.spawnEntity(sp1);
            }

            double damageRadius = 0.25;
            AxisAlignedBB aabb = new AxisAlignedBB(
               args[0] - damageRadius, args[1] - damageRadius, args[2] - damageRadius, args[0] + damageRadius, args[1] + damageRadius, args[2] + damageRadius
            );
            IBlockState dustState = GetMOP.firstBlockStateContains(this.world, aabb, GetMOP.SOLID_BLOCKS);
            int blockDustId = dustState == null ? -1 : Block.getStateId(dustState);
            if (blockDustId != -1) {
               Vec3d normMotVect = new Vec3d(-this.motionX, -this.motionY, -this.motionZ).normalize();

               for (int ss = 0; ss < 2 + this.rand.nextInt(3); ss++) {
                  this.world
                     .spawnParticle(
                        EnumParticleTypes.BLOCK_CRACK,
                        args[0],
                        args[1],
                        args[2],
                        normMotVect.x / 2.5 + this.rand.nextGaussian() / 15.0,
                        normMotVect.y / 2.5 + this.rand.nextGaussian() / 15.0,
                        normMotVect.z / 2.5 + this.rand.nextGaussian() / 15.0,
                        new int[]{blockDustId}
                     );
               }
            }
         }
      }

      @Override
      public void writeEntityToNBT(NBTTagCompound compound) {
         super.writeEntityToNBT(compound);
         compound.setFloat("damage", this.damage);
      }

      @Override
      public void readEntityFromNBT(NBTTagCompound compound) {
         super.readEntityFromNBT(compound);
         if (compound.hasKey("damage")) {
            this.damage = compound.getFloat("damage");
         }
      }

      @Override
      public void onImpact(RayTraceResult result) {
         if (this.bullet != null && !this.bulletCollided) {
            this.bulletCollided = this.bullet
               .onImpact(this.world, this.getThrower(), this.posX, this.posY, this.posZ, result, this);
         }

         if (!this.world.isRemote) {
            if (result.entityHit != null) {
               if (Team.checkIsOpponent(this.thrower, result.entityHit)) {
                  WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
                  float bdamage = this.bullet == null ? this.damage : this.damage + this.bullet.damage * parameters.get("bullet_damage");
                  float bknockback = this.bullet == null ? this.knockback : this.knockback + this.bullet.knockback * parameters.get("bullet_knockback");
                  Weapons.dealDamage(
                     new WeaponDamage(this.weaponstack, this.getThrower(), this, false, true, this, WeaponDamage.bullet),
                     bdamage,
                     this.getThrower(),
                     result.entityHit,
                     true,
                     bknockback
                  );
                  if (this.bullet != null) {
                     this.bullet.onDamageCause(this.world, result.entityHit, this.getThrower(), this);
                  }

                  result.entityHit.hurtResistantTime = 0;
                  this.world
                     .playSound(
                        (EntityPlayer)null,
                        this.posX,
                        this.posY,
                        this.posZ,
                        Sounds.bullet,
                        SoundCategory.AMBIENT,
                        0.8F,
                        0.9F + this.rand.nextFloat() / 5.0F
                     );
                  Vec3d vec1 = GetMOP.getNearestPointInAABBtoPoint(this.getPositionVector(), result.entityHit.getEntityBoundingBox());
                  IEntitySynchronize.sendSynchronize(this, 64.0, vec1.x, vec1.y, vec1.z);
                  this.setDead();
                  DeathEffects.applyDeathEffect(result.entityHit, DeathEffects.DE_DISMEMBER, 0.1F);
               }
            } else if (this.world
                  .getBlockState(result.getBlockPos())
                  .getBlock()
                  .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
               != null) {
               this.world
                  .playSound(
                     (EntityPlayer)null,
                     this.posX,
                     this.posY,
                     this.posZ,
                     Sounds.bullet,
                     SoundCategory.AMBIENT,
                     0.8F,
                     0.9F + this.rand.nextFloat() / 5.0F
                  );
               IEntitySynchronize.sendSynchronize(
                  this, 64.0, result.hitVec.x, result.hitVec.y, result.hitVec.z
               );
               this.setDead();
            }
         }
      }

      @SideOnly(Side.CLIENT)
      public void onEntityUpdate() {
         super.onEntityUpdate();
         Vec3d pos1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
         Vec3d pos2 = this.getPositionVector();
         if (this.bullet != null
            && this.ticksExisted > 2
            && this.world.isRemote
            && pos1.lengthSquared() > 1.0E-6
            && pos2.lengthSquared() > 1.0E-6) {
            BetweenParticle laser = new BetweenParticle(
               this.world,
               texture,
               0.09F,
               170,
               this.bullet.colorR,
               this.bullet.colorG,
               this.bullet.colorB,
               0.14F,
               pos1.distanceTo(pos2),
               Math.min(6, this.ticksExisted - 2),
               0.2F,
               9999.0F,
               pos1,
               pos2
            );
            laser.setPosition(this.posX, this.posY, this.posZ);
            laser.alphaGlowing = true;
            this.world.spawnEntity(laser);
         }
      }
   }

   public static class CircleBlast extends EntityThrowable implements IEntitySynchronize {
      public float damage = 0.0F;
      public float knockback = 0.0F;
      public float maxradius = 0.0F;
      public float radiusIncreace = 0.0F;
      public float radius = 0.0F;
      public float lowRadiusIncreace = 0.0F;
      public float lowRadius = 0.0F;
      public float rotateAngleX;
      public float rotateAngleY;
      public float rotateAngleZ;
      public float rotateAngleYaw;
      public Vec3d normalVector;

      public CircleBlast(World world) {
         super(world);
         this.ignoreFrustumCheck = true;
      }

      public CircleBlast(World world, EntityLivingBase thrower) {
         this(world, thrower.posX, thrower.posY + thrower.getEyeHeight(), thrower.posZ);
         this.thrower = thrower;
         this.ignoreFrustumCheck = true;
      }

      public CircleBlast(World world, double x, double y, double z) {
         super(world, x, y, z);
         this.ignoreFrustumCheck = true;
      }

      @SideOnly(Side.CLIENT)
      public void preRenderBlast() {
         if (this.rotateAngleZ != 0.0F) {
            GlStateManager.rotate(this.rotateAngleZ * (180.0F / (float)Math.PI), 0.0F, 0.0F, 1.0F);
         }

         if (this.rotateAngleY != 0.0F) {
            GlStateManager.rotate(-this.rotateAngleY * (180.0F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
         }

         if (this.rotateAngleX != 0.0F) {
            GlStateManager.rotate(-this.rotateAngleX * (180.0F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
         }
      }

      public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
         float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
         float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
         float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
         this.shoot(f, f1, f2, velocity, inaccuracy);
         this.motionX = this.motionX + entityThrower.motionX;
         this.motionZ = this.motionZ + entityThrower.motionZ;
         if (!entityThrower.onGround) {
            this.motionY = this.motionY + entityThrower.motionY;
         }
      }

      protected float getGravityVelocity() {
         return 0.0F;
      }

      @Override
      public void onClient(double... args) {
         if (args.length == 8) {
            this.radius = (float)args[0];
            this.radiusIncreace = (float)args[1];
            this.lowRadius = (float)args[2];
            this.lowRadiusIncreace = (float)args[3];
            this.rotateAngleX = (float)args[4];
            this.rotateAngleY = (float)args[5];
            this.rotateAngleZ = (float)args[6];
            this.rotateAngleYaw = (float)args[7];
         }
      }

      public boolean isInRangeToRenderDist(double distance) {
         double d0 = this.getEntityBoundingBox().getAverageEdgeLength() * 4.0;
         if (Double.isNaN(d0)) {
            d0 = 4.0;
         }

         d0 *= this.maxradius + 64.0;
         return distance < d0 * d0;
      }

      public void onUpdate() {
         super.onUpdate();
         if (this.world.isRemote) {
            this.radius = this.radius + this.radiusIncreace;
            this.lowRadius = this.lowRadius + this.lowRadiusIncreace;
         } else {
            if (this.radius < this.maxradius) {
               this.radius = this.radius + this.radiusIncreace;
            }

            if (this.lowRadius < this.maxradius) {
               this.lowRadius = this.lowRadius + this.lowRadiusIncreace;
            } else if (this.radius >= this.maxradius) {
               this.setDead();
            }

            if (this.ticksExisted < 3 || this.ticksExisted % 20 == 0) {
               IEntitySynchronize.sendSynchronize(
                  this,
                  this.maxradius + 64.0F,
                  this.radius,
                  this.radiusIncreace,
                  this.lowRadius,
                  this.lowRadiusIncreace,
                  this.rotateAngleX,
                  this.rotateAngleY,
                  this.rotateAngleZ,
                  this.rotateAngleYaw
               );
            }

            if (this.normalVector != null) {
               double radiusSq = this.radius * this.radius;
               double lowradiusSq = this.lowRadius * this.lowRadius;
               Vec3d center = this.getPositionVector();

               for (Entity entity : GetMOP.getEntitiesInAABBof(this.world, center, (double)this.radius, this)) {
                  double distSq = this.getDistanceSq(entity);
                  if (distSq <= radiusSq
                     && distSq >= lowradiusSq
                     && !(entity instanceof CircleBlast)
                     && Team.checkIsOpponent(this.thrower, entity)
                     && GetMOP.isBoxInPlane(center, this.normalVector, entity.getEntityBoundingBox())) {
                     Weapons.dealDamage(
                        new WeaponDamage(null, this.getThrower(), this, false, false, center, WeaponDamage.plasma),
                        this.damage,
                        this.thrower,
                        entity,
                        true,
                        this.knockback,
                        this.posX,
                        this.posY,
                        this.posZ
                     );
                     Weapons.setPotionIfEntityLB(entity, PotionEffects.SHOCK, 100, 1);
                  }
               }
            }
         }
      }

      public void writeEntityToNBT(NBTTagCompound compound) {
         super.writeEntityToNBT(compound);
         compound.setFloat("damage", this.damage);
      }

      public void readEntityFromNBT(NBTTagCompound compound) {
         super.readEntityFromNBT(compound);
         if (compound.hasKey("damage")) {
            this.damage = compound.getFloat("damage");
         }
      }

      protected void onImpact(RayTraceResult result) {
      }
   }

   public static class DronLaser extends EntityThrowable {
      public static ResourceLocation red = new ResourceLocation("arpg:textures/redlaser.png");
      public float damage = 0.0F;

      public DronLaser(World world) {
         super(world);
         this.setSize(0.35F, 0.35F);
      }

      public DronLaser(World world, EntityLivingBase thrower) {
         super(world, thrower);
         this.setSize(0.35F, 0.35F);
      }

      public DronLaser(World world, double x, double y, double z) {
         super(world, x, y, z);
         this.setSize(0.35F, 0.35F);
      }

      public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
         float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
         float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
         float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
         this.shoot(f, f1, f2, velocity, inaccuracy);
         this.motionX = this.motionX + entityThrower.motionX * 0.1;
         this.motionZ = this.motionZ + entityThrower.motionZ * 0.1;
         if (!entityThrower.onGround) {
            this.motionY = this.motionY + entityThrower.motionY * 0.1;
         }
      }

      protected float getGravityVelocity() {
         return 0.0F;
      }

      public void onUpdate() {
         super.onUpdate();
         if (this.ticksExisted > 20) {
            this.setDead();
         }
      }

      public void writeEntityToNBT(NBTTagCompound compound) {
         super.writeEntityToNBT(compound);
         compound.setFloat("damage", this.damage);
      }

      public void readEntityFromNBT(NBTTagCompound compound) {
         super.readEntityFromNBT(compound);
         if (compound.hasKey("damage")) {
            this.damage = compound.getFloat("damage");
         }
      }

      @SideOnly(Side.CLIENT)
      public void handleStatusUpdate(byte id) {
         if (id == 8) {
            for (int ss = 0; ss < 8; ss++) {
               GUNParticle spell = new GUNParticle(
                  red,
                  0.1F,
                  0.02F,
                  40,
                  240,
                  this.world,
                  this.posX,
                  this.posY,
                  this.posZ,
                  (float)this.rand.nextGaussian() / 16.0F,
                  (float)this.rand.nextGaussian() / 16.0F,
                  (float)this.rand.nextGaussian() / 16.0F,
                  0.9F + this.rand.nextFloat() / 10.0F,
                  1.0F,
                  1.0F,
                  false,
                  0,
                  true,
                  1.0F
               );
               spell.scaleTickAdding = -0.0025F;
               this.world.spawnEntity(spell);
            }
         }
      }

      protected void onImpact(RayTraceResult result) {
         if (result.entityHit != null) {
            if (Team.checkIsOpponent(this.thrower, result.entityHit) && !this.world.isRemote) {
               Weapons.dealDamage(
                  new WeaponDamage(null, this.getThrower(), this, false, true, this, WeaponDamage.laser),
                  this.damage,
                  this.getThrower(),
                  result.entityHit,
                  true,
                  1.0F
               );
               this.world
                  .playSound(
                     (EntityPlayer)null,
                     this.posX,
                     this.posY,
                     this.posZ,
                     Sounds.normal_projectile,
                     SoundCategory.AMBIENT,
                     0.4F,
                     1.1F + this.rand.nextFloat() / 5.0F
                  );
               this.world.setEntityState(this, (byte)8);
               this.setDead();
            }
         } else if (this.world
               .getBlockState(result.getBlockPos())
               .getBlock()
               .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
            != null) {
            this.world
               .playSound(
                  (EntityPlayer)null,
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.normal_projectile,
                  SoundCategory.AMBIENT,
                  0.4F,
                  1.1F + this.rand.nextFloat() / 5.0F
               );
            if (!this.world.isRemote) {
               this.world.setEntityState(this, (byte)8);
               this.setDead();
            }
         }
      }
   }

   public static class FlowerAcidShoot extends EntityThrowable {
      public static ResourceLocation tex = new ResourceLocation("arpg:textures/flower_acid.png");
      public float damage = 0.0F;

      public FlowerAcidShoot(World world) {
         super(world);
         this.setSize(0.4F, 0.4F);
      }

      public FlowerAcidShoot(World world, EntityLivingBase thrower) {
         super(world, thrower);
         this.setSize(0.4F, 0.4F);
      }

      public FlowerAcidShoot(World world, double x, double y, double z) {
         super(world, x, y, z);
         this.setSize(0.4F, 0.4F);
      }

      public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
         float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
         float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
         float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
         this.shoot(f, f1, f2, velocity, inaccuracy);
         this.motionX = this.motionX + entityThrower.motionX * 0.4;
         this.motionZ = this.motionZ + entityThrower.motionZ * 0.4;
         if (!entityThrower.onGround) {
            this.motionY = this.motionY + entityThrower.motionY * 0.4;
         }
      }

      protected float getGravityVelocity() {
         return 0.01F;
      }

      public void onUpdate() {
         super.onUpdate();
         if (this.ticksExisted > 80) {
            this.setDead();
         }

         this.world.setEntityState(this, (byte)9);
      }

      public void writeEntityToNBT(NBTTagCompound compound) {
         super.writeEntityToNBT(compound);
         compound.setFloat("damage", this.damage);
      }

      public void readEntityFromNBT(NBTTagCompound compound) {
         super.readEntityFromNBT(compound);
         if (compound.hasKey("damage")) {
            this.damage = compound.getFloat("damage");
         }
      }

      @SideOnly(Side.CLIENT)
      public void handleStatusUpdate(byte id) {
         if (id == 8) {
            for (int ss = 0; ss < 8; ss++) {
               GUNParticle spell = new GUNParticle(
                  tex,
                  0.1F,
                  0.02F,
                  40,
                  180,
                  this.world,
                  this.posX,
                  this.posY,
                  this.posZ,
                  (float)this.rand.nextGaussian() / 16.0F,
                  (float)this.rand.nextGaussian() / 16.0F,
                  (float)this.rand.nextGaussian() / 16.0F,
                  0.9F + this.rand.nextFloat() / 10.0F,
                  1.0F,
                  1.0F,
                  true,
                  0,
                  true,
                  1.0F
               );
               spell.scaleTickAdding = -0.0025F;
               this.world.spawnEntity(spell);
            }
         }

         if (id == 9) {
            GUNParticle spell = new GUNParticle(
               tex,
               0.1F,
               0.02F,
               40,
               180,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               (float)this.rand.nextGaussian() / 36.0F,
               (float)this.rand.nextGaussian() / 36.0F,
               (float)this.rand.nextGaussian() / 36.0F,
               0.9F + this.rand.nextFloat() / 10.0F,
               1.0F,
               1.0F,
               true,
               0,
               true,
               1.0F
            );
            spell.scaleTickAdding = -0.0025F;
            this.world.spawnEntity(spell);
         }
      }

      protected void onImpact(RayTraceResult result) {
         if (result.entityHit != null) {
            if (Team.checkIsOpponent(this.thrower, result.entityHit)) {
               if (!this.world.isRemote && result.entityHit instanceof EntityLivingBase) {
                  EntityLivingBase entitylivingbase = (EntityLivingBase)result.entityHit;
                  SuperKnockback.applyKnockback(
                     entitylivingbase, 1.0F, this.thrower.posX, this.thrower.posY, this.thrower.posZ
                  );
                  IAttributeInstance entityAttribute = entitylivingbase.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE);
                  double baseValue = entityAttribute.getBaseValue();
                  entityAttribute.setBaseValue(1.0);
                  entitylivingbase.attackEntityFrom(DamageSource.causeMobDamage(this.getThrower()), this.damage);
                  entityAttribute.setBaseValue(baseValue);
                  entitylivingbase.addPotionEffect(new PotionEffect(PotionEffects.TOXIN, 50));
                  this.world
                     .playSound(
                        (EntityPlayer)null,
                        this.posX,
                        this.posY,
                        this.posZ,
                        Sounds.normal_projectile,
                        SoundCategory.AMBIENT,
                        0.4F,
                        1.1F + this.rand.nextFloat() / 5.0F
                     );
                  this.world.setEntityState(this, (byte)8);
                  this.setDead();
               }
            } else if (this.thrower == null && !this.world.isRemote && result.entityHit instanceof EntityLivingBase) {
               EntityLivingBase entitylivingbase = (EntityLivingBase)result.entityHit;
               entitylivingbase.attackEntityFrom(DamageSource.MAGIC, this.damage);
               entitylivingbase.addPotionEffect(new PotionEffect(PotionEffects.TOXIN, 50));
               this.world
                  .playSound(
                     (EntityPlayer)null,
                     this.posX,
                     this.posY,
                     this.posZ,
                     Sounds.normal_projectile,
                     SoundCategory.AMBIENT,
                     0.4F,
                     1.1F + this.rand.nextFloat() / 5.0F
                  );
               this.world.setEntityState(this, (byte)8);
               this.setDead();
            }
         } else if (this.world
               .getBlockState(result.getBlockPos())
               .getBlock()
               .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
            != null) {
            this.world
               .playSound(
                  (EntityPlayer)null,
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.normal_projectile,
                  SoundCategory.AMBIENT,
                  0.4F,
                  1.1F + this.rand.nextFloat() / 5.0F
               );
            if (!this.world.isRemote) {
               this.world.setEntityState(this, (byte)8);
               this.setDead();
            }
         }
      }
   }

   public static class Grenade extends EntityThrowable {
      public static ResourceLocation largesmoke = new ResourceLocation("arpg:textures/largesmoke.png");
      public static ResourceLocation flame = new ResourceLocation("arpg:textures/clouds3.png");
      public static ResourceLocation trace = new ResourceLocation("arpg:textures/blueexplode4.png");
      public static ResourceLocation mainboom = new ResourceLocation("arpg:textures/blueexplode2.png");
      public int activateTime = 60;
      public boolean canCheck = true;
      public float damage = 10.0F;

      public Grenade(World world) {
         super(world);
      }

      public Grenade(World world, EntityLivingBase thrower) {
         super(world, thrower);
      }

      public Grenade(World world, double x, double y, double z) {
         super(world, x, y, z);
      }

      public void writeEntityToNBT(NBTTagCompound compound) {
         super.writeEntityToNBT(compound);
         compound.setFloat("damage", this.damage);
      }

      public void readEntityFromNBT(NBTTagCompound compound) {
         super.readEntityFromNBT(compound);
         if (compound.hasKey("damage")) {
            this.damage = compound.getFloat("damage");
         }
      }

      public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
         float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
         float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
         float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
         this.shoot(f, f1, f2, velocity, inaccuracy);
         this.motionX = this.motionX + entityThrower.motionX * 0.8;
         this.motionZ = this.motionZ + entityThrower.motionZ * 0.8;
         if (!entityThrower.onGround) {
            this.motionY = this.motionY + entityThrower.motionY * 0.8;
         }
      }

      protected float getGravityVelocity() {
         return 0.09F;
      }

      public void onUpdate() {
         super.onUpdate();
         if (!this.world.isRemote) {
            if (this.canCheck
               && this.ticksExisted % 10 == 0
               && this.thrower instanceof EntityLiving
               && ((EntityLiving)this.thrower).getAttackTarget() != null) {
               double damageRadius = 2.5;
               AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
                  .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
                  .offset(-damageRadius, -damageRadius, -damageRadius);
               List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
               if (!list.isEmpty()) {
                  for (EntityLivingBase entitylivingbase : list) {
                     if (entitylivingbase == ((EntityLiving)this.thrower).getAttackTarget()) {
                        this.expl();
                        break;
                     }
                  }
               }
            }

            if (this.ticksExisted > this.activateTime) {
               this.expl();
            }

            if (this.rand.nextFloat() < 0.5) {
               this.world.setEntityState(this, (byte)5);
            }
         }
      }

      @SideOnly(Side.CLIENT)
      public void handleStatusUpdate(byte id) {
         if (id == 8) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.explode2,
                  SoundCategory.HOSTILE,
                  2.0F,
                  1.0F + this.rand.nextFloat() / 5.0F,
                  false
               );

            for (int ss = 0; ss < 7; ss++) {
               GUNParticle bigsmoke = new GUNParticle(
                  largesmoke,
                  0.5F + this.rand.nextFloat() / 2.0F,
                  -0.001F,
                  40,
                  60,
                  this.world,
                  this.posX,
                  this.posY,
                  this.posZ,
                  (float)this.rand.nextGaussian() / 20.0F,
                  (float)this.rand.nextGaussian() / 25.0F,
                  (float)this.rand.nextGaussian() / 20.0F,
                  1.0F,
                  1.0F,
                  1.0F,
                  true,
                  this.rand.nextInt(360)
               );
               bigsmoke.alphaTickAdding = -0.01F;
               this.world.spawnEntity(bigsmoke);
            }

            for (int ss = 0; ss < 15; ss++) {
               GUNParticle fire = new GUNParticle(
                  flame,
                  0.5F + (float)this.rand.nextGaussian() / 3.0F,
                  -0.003F,
                  23 + this.rand.nextInt(15),
                  240,
                  this.world,
                  this.posX,
                  this.posY,
                  this.posZ,
                  (float)this.rand.nextGaussian() / 9.0F,
                  (float)this.rand.nextGaussian() / 9.0F,
                  (float)this.rand.nextGaussian() / 9.0F,
                  0.6F,
                  0.8F,
                  1.0F - this.rand.nextFloat() / 5.0F,
                  true,
                  this.rand.nextInt(360)
               );
               fire.alphaTickAdding = -0.026F;
               fire.alphaGlowing = true;
               fire.scaleTickAdding = -0.015F;
               this.world.spawnEntity(fire);
            }

            int lt = 8 + this.rand.nextInt(5);
            GUNParticle fire = new GUNParticle(
               mainboom,
               1.4F,
               0.0F,
               lt,
               240,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               0.0F,
               0.0F,
               0.0F,
               1.0F,
               1.0F,
               1.0F - this.rand.nextFloat() / 5.0F,
               true,
               this.rand.nextInt(360)
            );
            fire.alphaTickAdding = -0.8F / lt;
            fire.alphaGlowing = true;
            fire.scaleTickAdding = 0.3F + this.rand.nextFloat() / 5.0F;
            this.world.spawnEntity(fire);
         }

         if (id == 5) {
            GUNParticle fire2 = new GUNParticle(
               trace,
               0.03F,
               0.0F,
               10,
               240,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               0.0F,
               0.0F,
               0.0F,
               0.6F,
               0.8F + this.rand.nextFloat() / 5.0F,
               0.8F + this.rand.nextFloat() / 5.0F,
               true,
               this.rand.nextInt(360)
            );
            fire2.scaleTickAdding = 0.06F;
            fire2.alphaTickAdding = -0.04F;
            fire2.alphaGlowing = true;
            this.world.spawnEntity(fire2);
         }
      }

      protected void onImpact(RayTraceResult result) {
         if (result.entityHit != null) {
            if (Team.checkIsOpponent(this.thrower, result.entityHit)) {
               this.expl();
            }
         } else if (this.world
               .getBlockState(result.getBlockPos())
               .getBlock()
               .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
            != null) {
            this.world
               .playSound(
                  (EntityPlayer)null,
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.normal_projectile,
                  SoundCategory.AMBIENT,
                  0.7F,
                  0.9F + this.rand.nextFloat() / 5.0F
               );
            float frictionMultipl = 1.15F;
            if (result.sideHit == EnumFacing.UP || result.sideHit == EnumFacing.DOWN) {
               this.motionY *= -0.9;
               this.motionX /= frictionMultipl;
               this.motionZ /= frictionMultipl;
               this.velocityChanged = true;
            }

            if (result.sideHit == EnumFacing.NORTH || result.sideHit == EnumFacing.SOUTH) {
               this.motionZ = -this.motionZ;
               this.motionX /= frictionMultipl;
               this.motionY /= frictionMultipl;
               this.velocityChanged = true;
            }

            if (result.sideHit == EnumFacing.EAST || result.sideHit == EnumFacing.WEST) {
               this.motionX = -this.motionX;
               this.motionZ /= frictionMultipl;
               this.motionY /= frictionMultipl;
               this.velocityChanged = true;
            }
         }
      }

      public void expl() {
         double damageRadius = 4.4;
         AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
            .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
            .offset(-damageRadius, -damageRadius, -damageRadius);
         if (!this.world.isRemote) {
            List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
            if (!list.isEmpty()) {
               for (EntityLivingBase entitylivingbase : list) {
                  if (Team.checkIsOpponent(this.thrower, entitylivingbase)) {
                     Weapons.dealDamage(
                        new WeaponDamage(null, this.getThrower(), this, true, false, this, WeaponDamage.explode),
                        this.damage,
                        this.getThrower(),
                        entitylivingbase,
                        true,
                        1.5F,
                        this.posX,
                        this.posY,
                        this.posZ
                     );
                     if (entitylivingbase.getHealth() <= 0.0F && this.rand.nextFloat() < 0.55) {
                        DeathEffects.applyDeathEffect(entitylivingbase, DeathEffects.DE_DISMEMBER);
                     }
                  } else {
                     SuperKnockback.applyKnockback(entitylivingbase, 1.5F, this.posX, this.posY, this.posZ);
                  }
               }
            }

            if (this.world.isAreaLoaded(this.getPosition().add(-16, -16, -16), this.getPosition().add(16, 16, 16))) {
               for (BlockPos blockpos : GetMOP.getPosesInsideSphere((int)this.posX, (int)this.posY, (int)this.posZ, 3)) {
                  IBlockState state = this.world.getBlockState(blockpos);
                  if (Weapons.easyBreakBlockFor(this.world, 1.0F, blockpos, state)) {
                     if (!state.getBlock().hasTileEntity(state) && !(this.rand.nextFloat() < 0.5F)) {
                        EntityFallingBlock entityfallingblock = new EntityFallingBlock(
                           this.world, blockpos.getX() + 0.5, blockpos.getY() + 0.2, blockpos.getZ() + 0.5, state
                        );
                        this.world.spawnEntity(entityfallingblock);
                        SuperKnockback.applyKnockback(entityfallingblock, 1.5F, this.posX, this.posY - 0.2, this.posZ);
                        entityfallingblock.velocityChanged = true;
                     } else {
                        this.world.destroyBlock(blockpos, true);
                     }
                  }
               }
            }

            this.world.setEntityState(this, (byte)8);
            this.setDead();
         }
      }
   }

   public static class GustCloud extends EntityThrowable {
      static ResourceLocation cloud = new ResourceLocation("arpg:textures/clouds3.png");
      static ResourceLocation lightning = new ResourceLocation("arpg:textures/lightning1.png");
      public float damage = 0.0F;

      public GustCloud(World world) {
         super(world);
         this.setSize(1.8F, 1.8F);
      }

      public GustCloud(World world, EntityLivingBase thrower) {
         super(world, thrower);
         this.setSize(1.8F, 1.8F);
      }

      public GustCloud(World world, double x, double y, double z) {
         super(world, x, y, z);
         this.setSize(1.8F, 1.8F);
      }

      public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
         float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
         float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
         float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
         this.shoot(f, f1, f2, velocity, inaccuracy);
      }

      protected float getGravityVelocity() {
         return 0.0F;
      }

      public void onUpdate() {
         super.onUpdate();
         if (this.ticksExisted > 30 - this.world.getDifficulty().getId() * 6 && !this.world.isRemote) {
            this.expl();
         }
      }

      public void writeEntityToNBT(NBTTagCompound compound) {
         super.writeEntityToNBT(compound);
         compound.setFloat("damage", this.damage);
      }

      public void readEntityFromNBT(NBTTagCompound compound) {
         super.readEntityFromNBT(compound);
         if (compound.hasKey("damage")) {
            this.damage = compound.getFloat("damage");
         }
      }

      @SideOnly(Side.CLIENT)
      public void handleStatusUpdate(byte id) {
         if (id == 8) {
            for (int ss = 0; ss < 8; ss++) {
               GUNParticle fire = new GUNParticle(
                  cloud,
                  0.7F + (float)this.rand.nextGaussian() / 3.0F,
                  -0.003F,
                  23 + this.rand.nextInt(15),
                  80,
                  this.world,
                  this.posX,
                  this.posY,
                  this.posZ,
                  (float)this.rand.nextGaussian() / 11.0F,
                  (float)this.rand.nextGaussian() / 17.0F,
                  (float)this.rand.nextGaussian() / 11.0F,
                  0.9F,
                  0.9F,
                  1.0F,
                  true,
                  this.rand.nextInt(100) - 50
               );
               fire.alphaTickAdding = -0.026F;
               fire.alphaGlowing = true;
               fire.scaleTickAdding = -0.015F;
               this.world.spawnEntity(fire);
            }

            Vec3d pos2 = this.getPositionVector();

            for (int ss = 0; ss < 4; ss++) {
               Vec3d pos1 = new Vec3d(
                  this.posX + this.rand.nextGaussian(),
                  this.posY + this.rand.nextGaussian(),
                  this.posZ + this.rand.nextGaussian()
               );
               if (pos1.lengthSquared() > 1.0E-6 && pos2.lengthSquared() > 1.0E-6) {
                  BetweenParticle laser = new BetweenParticle(
                     this.world, lightning, 0.64F, 240, 1.0F, 0.9F, 0.9F, 0.1F, pos1.distanceTo(pos2), 10, 0.0F, 9999.0F, pos1, pos2
                  );
                  laser.alphaGlowing = true;
                  laser.setPosition(pos1.x, pos1.y, pos1.z);
                  this.world.spawnEntity(laser);
               }
            }
         }
      }

      public void expl() {
         List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox());
         if (!list.isEmpty()) {
            for (EntityLivingBase entitylivingbase : list) {
               if (Team.checkIsOpponent(this.thrower, entitylivingbase)) {
                  Weapons.dealDamage(
                     new WeaponDamage(null, this.getThrower(), this, false, false, this, WeaponDamage.electric),
                     this.damage,
                     this.getThrower(),
                     entitylivingbase,
                     true,
                     0.5F,
                     this.posX,
                     this.posY,
                     this.posZ
                  );
                  entitylivingbase.hurtResistantTime = 0;
                  if (entitylivingbase.getHealth() <= 0.0F && this.rand.nextFloat() < 0.55) {
                     DeathEffects.applyDeathEffect(entitylivingbase, DeathEffects.DE_ELECTRIC);
                  }
               }
            }
         }

         this.world
            .playSound(
               (EntityPlayer)null,
               this.posX,
               this.posY,
               this.posZ,
               Sounds.electric_impact,
               SoundCategory.HOSTILE,
               0.7F,
               0.9F + this.rand.nextFloat() / 5.0F
            );
         this.world.setEntityState(this, (byte)8);
         this.setDead();
      }

      protected void onImpact(RayTraceResult result) {
      }

      public void onEntityUpdate() {
         if (this.world.isRemote && this.ticksExisted % 5 == 0) {
            GUNParticle fire = new GUNParticle(
               cloud,
               0.7F + (float)this.rand.nextGaussian() / 3.0F,
               -0.003F,
               23 + this.rand.nextInt(15),
               80,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               (float)this.rand.nextGaussian() / 13.0F,
               (float)this.rand.nextGaussian() / 17.0F,
               (float)this.rand.nextGaussian() / 13.0F,
               0.9F,
               0.9F,
               0.9F,
               true,
               this.rand.nextInt(100) - 50
            );
            fire.alphaTickAdding = -0.026F;
            fire.alphaGlowing = true;
            fire.scaleTickAdding = -0.015F;
            this.world.spawnEntity(fire);
         }
      }
   }

   public static class Hailblast extends EntityThrowable {
      public float damage = 0.0F;
      public float spawnFragmentChance = 0.0F;
      public float damageRadiusAdd = 0.0F;
      static ResourceLocation trail = new ResourceLocation("arpg:textures/hail_trace.png");
      static ResourceLocation largecloud = new ResourceLocation("arpg:textures/largecloud.png");
      static ResourceLocation expl = new ResourceLocation("arpg:textures/frozen_explode.png");
      static ResourceLocation snow = new ResourceLocation("arpg:textures/snowflake2.png");
      public Vec3d wind;
      public boolean changeHurtResistTime = true;

      public Hailblast(World world) {
         super(world);
         this.setSize(0.3F, 0.3F);
      }

      public Hailblast(World world, EntityLivingBase thrower) {
         super(world, thrower);
         this.setSize(0.3F, 0.3F);
      }

      public Hailblast(World world, double x, double y, double z) {
         super(world, x, y, z);
         this.setSize(0.3F, 0.3F);
      }

      public boolean isInRangeToRenderDist(double distance) {
         return distance < 16384.0;
      }

      public boolean canBeCollidedWith() {
         return true;
      }

      public float getCollisionBorderSize() {
         return 0.5F;
      }

      public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
         float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
         float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
         float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
         this.shoot(f, f1, f2, velocity, inaccuracy);
         this.motionX = this.motionX + entityThrower.motionX * 0.8;
         this.motionZ = this.motionZ + entityThrower.motionZ * 0.8;
         if (!entityThrower.onGround) {
            this.motionY = this.motionY + entityThrower.motionY * 0.2;
         }
      }

      protected float getGravityVelocity() {
         return 0.03F;
      }

      public void onUpdate() {
         super.onUpdate();
         if (this.ticksExisted > 150) {
            this.setDead();
         }

         if (!this.world.isRemote) {
            if (this.ticksExisted % 5 == 0 && this.wind != null) {
               this.motionX = this.motionX + this.wind.x;
               this.motionY = this.motionY + this.wind.y;
               this.motionZ = this.motionZ + this.wind.z;
               this.velocityChanged = true;
            }

            if (this.damage == 0.0F) {
               this.setDead();
            }
         }
      }

      @SideOnly(Side.CLIENT)
      public void handleStatusUpdate(byte id) {
         if (id == 8) {
            int partAdding = 10;
            if (Minecraft.getMinecraft().getRenderViewEntity() != null) {
               float distr = Minecraft.getMinecraft().getRenderViewEntity().getDistance(this);
               if (distr < 6.0F) {
                  Booom.lastTick = Math.round(18.0F - distr);
                  Booom.frequency = 4.0F;
                  Booom.x = (float)this.rand.nextGaussian();
                  Booom.y = (float)this.rand.nextGaussian();
                  Booom.z = (float)this.rand.nextGaussian();
                  Booom.power = 0.2F;
               }

               partAdding = Math.max(partAdding - (int)distr, 0);
            }

            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.frozen_explode,
                  SoundCategory.WEATHER,
                  1.0F,
                  1.3F + this.rand.nextFloat() / 5.0F,
                  false
               );

            for (int ss = 0; ss < 5 + partAdding; ss++) {
               GUNParticle fire = new GUNParticle(
                  largecloud,
                  0.7F + (float)this.rand.nextGaussian() / 3.0F,
                  -0.003F,
                  23 + this.rand.nextInt(15),
                  240,
                  this.world,
                  this.posX,
                  this.posY,
                  this.posZ,
                  (float)this.rand.nextGaussian() / 11.0F,
                  (float)this.rand.nextGaussian() / 11.0F,
                  (float)this.rand.nextGaussian() / 11.0F,
                  0.6F - this.rand.nextFloat() / 5.0F,
                  0.8F,
                  1.0F - this.rand.nextFloat() / 5.0F,
                  true,
                  this.rand.nextInt(100) - 50
               );
               fire.alphaTickAdding = -0.026F;
               fire.alphaGlowing = true;
               fire.scaleTickAdding = -0.015F;
               this.world.spawnEntity(fire);
            }

            for (int ss = 0; ss < 2; ss++) {
               float fsize = 2.7F + this.rand.nextFloat();
               int lt = 8 + this.rand.nextInt(5);
               GUNParticle part = new GUNParticle(
                  expl,
                  1.0F,
                  0.0F,
                  lt,
                  240,
                  this.world,
                  this.posX,
                  this.posY,
                  this.posZ,
                  0.0F,
                  0.0F,
                  0.0F,
                  0.75F + this.rand.nextFloat() / 5.0F,
                  0.8F,
                  1.0F,
                  true,
                  this.rand.nextInt(360)
               );
               part.scaleTickAdding = fsize / lt;
               part.alphaGlowing = true;
               part.alphaTickAdding = -0.025F;
               this.world.spawnEntity(part);
            }

            int countOfParticles = 10 + partAdding;
            float R = 0.1F + (float)this.rand.nextGaussian() / 30.0F;

            for (int i = 0; i < countOfParticles; i++) {
               float rand1 = this.rand.nextFloat() * 2.0F - 1.0F;
               float rand2 = this.rand.nextFloat() * 2.0F - 1.0F;
               float X = rand1 * R;
               float new_R = (float)Math.sqrt(R * R - X * X);
               float Y = rand2 * new_R;
               float Z = (float)Math.sqrt(new_R * new_R - Y * Y);
               if (this.rand.nextBoolean()) {
                  Z *= -1.0F;
               }

               float scale = 0.3F + this.rand.nextFloat() / 7.0F;
               GUNParticle particle = new GUNParticle(
                  snow,
                  scale,
                  0.009F,
                  40,
                  180,
                  this.world,
                  this.posX,
                  this.posY,
                  this.posZ,
                  X,
                  Y,
                  Z,
                  0.9F,
                  1.0F,
                  1.0F,
                  false,
                  0
               );
               particle.scaleTickAdding = -scale / 40.0F;
               particle.dropMode = true;
               this.world.spawnEntity(particle);
            }
         }
      }

      @SideOnly(Side.CLIENT)
      public void onEntityUpdate() {
         Vec3d pos1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
         Vec3d pos2 = this.getPositionVector();
         if (this.world.isRemote && pos1.lengthSquared() > 1.0E-6 && pos2.lengthSquared() > 1.0E-6) {
            BetweenParticle laser = new BetweenParticle(
               this.world, trail, 0.44F, 240, 0.8F, 1.0F, 1.0F, 0.1F, pos1.distanceTo(pos2), 4, -0.15F, 9999.0F, pos1, pos2
            );
            laser.alphaGlowing = true;
            laser.texstart = 0.1F;
            laser.offset = -0.1F;
            laser.setPosition(pos1.x, pos1.y, pos1.z);
            this.world.spawnEntity(laser);
         }
      }

      public boolean attackEntityFrom(DamageSource source, float amount) {
         if (this.isEntityInvulnerable(source)) {
            return false;
         } else {
            this.markVelocityChanged();
            if (source.getTrueSource() != null && source.getTrueSource() instanceof EntityLivingBase) {
               Vec3d vec3d = source.getTrueSource().getLookVec();
               this.thrower = (EntityLivingBase)source.getTrueSource();
               if (vec3d != null) {
                  this.motionX = vec3d.x;
                  this.motionY = vec3d.y;
                  this.motionZ = vec3d.z;
                  ItemStack itemStack = this.thrower.getHeldItemMainhand().isEmpty()
                     ? this.thrower.getHeldItemOffhand()
                     : this.thrower.getHeldItemMainhand();
                  Catalyst.checkAndApplyHexToItem(itemStack, "hex_hail_batter", "Hailblast Batter");
               }

               return true;
            } else {
               return false;
            }
         }
      }

      protected void onImpact(RayTraceResult result) {
         if (result.entityHit != null) {
            if (this.thrower == null || Team.checkIsOpponent(this.thrower, result.entityHit)) {
               this.expl(result);
            }
         } else if (this.world
               .getBlockState(result.getBlockPos())
               .getBlock()
               .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
            != null) {
            this.expl(result);
         }
      }

      public void expl(RayTraceResult result) {
         double damageRadius = 3.0 + this.damageRadiusAdd;
         AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
            .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
            .offset(-damageRadius, -damageRadius, -damageRadius);
         if (!this.world.isRemote && result.hitVec != null) {
            List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, axisalignedbb);
            if (!list.isEmpty()) {
               for (Entity entity : list) {
                  if ((this.thrower != null || EverfrostMobsPack.mobsteam.equals(Team.getTeamFor(entity)))
                     && !Team.checkIsOpponent(this.thrower, entity)) {
                     SuperKnockback.applyKnockback(entity, 1.3F, this.posX, this.posY - 1.0, this.posZ);
                  } else {
                     Weapons.dealDamage(
                        new WeaponDamage(null, this.getThrower(), this, false, false, this, WeaponDamage.frost),
                        this.damage,
                        this.getThrower(),
                        entity,
                        true,
                        2.4F,
                        this.posX,
                        this.posY,
                        this.posZ
                     );
                     if (this.changeHurtResistTime) {
                        entity.hurtResistantTime = 0;
                     }

                     if (entity instanceof EntityLivingBase) {
                        EntityLivingBase entitylivingbase = (EntityLivingBase)entity;
                        if (entitylivingbase.isPotionActive(PotionEffects.FROSTBURN)) {
                           entitylivingbase.addPotionEffect(new PotionEffect(PotionEffects.FREEZING, 60));
                        }

                        if (entitylivingbase.getHealth() <= 0.0F && this.rand.nextFloat() < 0.55) {
                           DeathEffects.applyDeathEffect(entitylivingbase, DeathEffects.DE_ICING);
                        }
                     }
                  }

                  if (entity instanceof EntityItem) {
                     EntityItem entityItem = (EntityItem)entity;
                     if (entityItem.getItem().getItem() == ItemsRegister.ICEGEM) {
                        this.world
                           .spawnEntity(
                              new EntityItem(
                                 this.world,
                                 entityItem.posX,
                                 entityItem.posY,
                                 entityItem.posZ,
                                 new ItemStack(ItemsRegister.ICEDUST, entityItem.getItem().getCount())
                              )
                           );
                        entityItem.setDead();
                     }
                  }
               }
            }

            if (this.rand.nextFloat() < this.spawnFragmentChance
               && this.world.isAreaLoaded(this.getPosition().add(-8, -8, -8), this.getPosition().add(8, 8, 8))) {
               EntityItem itm = new EntityItem(
                  this.world, this.posX, this.posY, this.posZ, new ItemStack(ItemsRegister.WEATHERFRAGMENTS)
               );
               itm.lifespan = 1200;
               this.world.spawnEntity(itm);
            }

            this.world.setEntityState(this, (byte)8);
            this.setDead();
         }
      }
   }

   public static class HorribleEmeraldShoot extends EntityThrowable {
      public float damage = 0.0F;
      public Entity targetEntity;
      public float shootAcceleration;

      public HorribleEmeraldShoot(World world) {
         super(world);
         this.setSize(0.5F, 0.5F);
      }

      public HorribleEmeraldShoot(World world, EntityLivingBase thrower) {
         super(world, thrower);
         this.setSize(0.5F, 0.5F);
      }

      public HorribleEmeraldShoot(World world, double x, double y, double z) {
         super(world, x, y, z);
         this.setSize(0.5F, 0.5F);
      }

      public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
         float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
         float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
         float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
         this.shoot(f, f1, f2, velocity, inaccuracy);
      }

      protected float getGravityVelocity() {
         return 0.0F;
      }

      public void onUpdate() {
         super.onUpdate();
         if (this.ticksExisted > 100) {
            this.setDead();
         }

         if (this.ticksExisted > 35) {
            if (this.targetEntity != null) {
               SuperKnockback.applyMove(
                  this,
                  -this.shootAcceleration,
                  this.targetEntity.posX,
                  this.targetEntity.posY + this.targetEntity.height / 2.0F,
                  this.targetEntity.posZ
               );
               this.targetEntity = null;
               this.velocityChanged = true;
            }
         } else {
            this.motionX *= 0.97;
            this.motionY *= 0.97;
            this.motionZ *= 0.97;
         }
      }

      public void writeEntityToNBT(NBTTagCompound compound) {
         super.writeEntityToNBT(compound);
         compound.setFloat("damage", this.damage);
      }

      public void readEntityFromNBT(NBTTagCompound compound) {
         super.readEntityFromNBT(compound);
         if (compound.hasKey("damage")) {
            this.damage = compound.getFloat("damage");
         }
      }

      @SideOnly(Side.CLIENT)
      public void handleStatusUpdate(byte id) {
         if (id == 8) {
            for (int ss = 0; ss < 8; ss++) {
               double d0 = this.rand.nextGaussian() * 0.02;
               double d1 = this.rand.nextGaussian() * 0.02;
               double d2 = this.rand.nextGaussian() * 0.02;
               this.world
                  .spawnParticle(
                     EnumParticleTypes.VILLAGER_HAPPY,
                     this.posX + this.rand.nextFloat(),
                     this.posY + this.rand.nextFloat(),
                     this.posZ + this.rand.nextFloat(),
                     d0,
                     d1,
                     d2,
                     new int[0]
                  );
            }
         }
      }

      protected void onImpact(RayTraceResult result) {
         if (result.entityHit != null) {
            if (Team.checkIsOpponent(this.thrower, result.entityHit) && !this.world.isRemote) {
               Weapons.dealDamage(
                  new WeaponDamage(null, this.getThrower(), this, false, true, this, WeaponDamage.magic),
                  this.damage,
                  this.thrower,
                  result.entityHit,
                  false
               );
               result.entityHit.hurtResistantTime = 0;
               this.world
                  .playSound(
                     (EntityPlayer)null,
                     this.posX,
                     this.posY,
                     this.posZ,
                     SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP,
                     SoundCategory.AMBIENT,
                     0.5F,
                     1.1F + this.rand.nextFloat() / 5.0F
                  );
               this.world.setEntityState(this, (byte)8);
               this.setDead();
            }
         } else if (this.world
               .getBlockState(result.getBlockPos())
               .getBlock()
               .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
            != null) {
            this.world
               .playSound(
                  (EntityPlayer)null,
                  this.posX,
                  this.posY,
                  this.posZ,
                  SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP,
                  SoundCategory.AMBIENT,
                  0.5F,
                  1.1F + this.rand.nextFloat() / 5.0F
               );
            if (!this.world.isRemote) {
               this.world.setEntityState(this, (byte)8);
               this.setDead();
            }
         }
      }
   }

   public static class IceShardShoot extends EntityThrowable {
      public float damage = 0.0F;

      public IceShardShoot(World world) {
         super(world);
         this.setSize(0.25F, 0.25F);
      }

      public IceShardShoot(World world, EntityLivingBase thrower) {
         super(world, thrower);
         this.setSize(0.25F, 0.25F);
      }

      public IceShardShoot(World world, double x, double y, double z) {
         super(world, x, y, z);
         this.setSize(0.25F, 0.25F);
      }

      public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
         float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
         float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
         float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
         this.shoot(f, f1, f2, velocity, inaccuracy);
         this.motionX = this.motionX + entityThrower.motionX * 0.1;
         this.motionZ = this.motionZ + entityThrower.motionZ * 0.1;
         if (!entityThrower.onGround) {
            this.motionY = this.motionY + entityThrower.motionY * 0.1;
         }
      }

      protected float getGravityVelocity() {
         return 0.07F;
      }

      public void onUpdate() {
         super.onUpdate();
         if (this.ticksExisted > 60) {
            this.setDead();
         }
      }

      public void writeEntityToNBT(NBTTagCompound compound) {
         super.writeEntityToNBT(compound);
         compound.setFloat("damage", this.damage);
      }

      public void readEntityFromNBT(NBTTagCompound compound) {
         super.readEntityFromNBT(compound);
         if (compound.hasKey("damage")) {
            this.damage = compound.getFloat("damage");
         }
      }

      @SideOnly(Side.CLIENT)
      public void handleStatusUpdate(byte id) {
         if (id == 8) {
            for (int ss = 0; ss < this.rand.nextInt(3) + 2; ss++) {
               GUNParticle part = new GUNParticle(
                  CrystalFanShoot.shards[this.rand.nextInt(3)],
                  0.08F + this.rand.nextFloat() / 30.0F,
                  0.08F,
                  30 + this.rand.nextInt(20),
                  -1,
                  this.world,
                  this.lastTickPosX,
                  this.lastTickPosY,
                  this.lastTickPosZ,
                  (float)this.rand.nextGaussian() / 13.0F,
                  (float)this.rand.nextGaussian() / 13.0F + 0.1F,
                  (float)this.rand.nextGaussian() / 13.0F,
                  0.4F,
                  0.8F,
                  1.0F,
                  false,
                  this.rand.nextInt(360),
                  true,
                  1.2F
               );
               part.dropMode = true;
               part.rotationPitchYaw = new Vec2f(this.rand.nextInt(360), this.rand.nextInt(360));
               part.tracker = new ParticleTracker.TrackerGlassShard(
                  (float)this.rand.nextGaussian() * 2.0F,
                  (float)this.rand.nextGaussian() * 2.0F,
                  (int)this.rand.nextGaussian() * 2,
                  false
               );
               this.world.spawnEntity(part);
            }
         }
      }

      protected void onImpact(RayTraceResult result) {
         if (result.entityHit != null) {
            if (Team.checkIsOpponent(this.thrower, result.entityHit) && !this.world.isRemote) {
               Weapons.dealDamage(
                  new WeaponDamage(null, this.getThrower(), this, false, true, this, WeaponDamage.shards),
                  this.damage,
                  this.getThrower(),
                  result.entityHit,
                  true,
                  1.0F
               );
               result.entityHit.hurtResistantTime = 0;
               this.world
                  .playSound(
                     (EntityPlayer)null,
                     this.posX,
                     this.posY,
                     this.posZ,
                     Sounds.glass_break,
                     SoundCategory.AMBIENT,
                     0.5F,
                     1.1F + this.rand.nextFloat() / 5.0F
                  );
               this.world.setEntityState(this, (byte)8);
               this.setDead();
            }
         } else if (this.world
               .getBlockState(result.getBlockPos())
               .getBlock()
               .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
            != null) {
            this.world
               .playSound(
                  (EntityPlayer)null,
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.glass_break,
                  SoundCategory.AMBIENT,
                  0.5F,
                  1.1F + this.rand.nextFloat() / 5.0F
               );
            if (!this.world.isRemote) {
               this.world.setEntityState(this, (byte)8);
               this.setDead();
            }
         }
      }
   }

   public static class KrakenShockbolt extends EntityThrowable implements IEntitySynchronize {
      public static ResourceLocation hadronblast = new ResourceLocation("arpg:textures/hadron_blast.png");
      public static ResourceLocation circle2 = new ResourceLocation("arpg:textures/circle2.png");
      public static ResourceLocation lightning4 = new ResourceLocation("arpg:textures/lightning4.png");
      public int explodeTimer = -1;
      public float damage = 0.0F;

      public KrakenShockbolt(World world) {
         super(world);
         this.setSize(0.3F, 0.3F);
      }

      public KrakenShockbolt(World world, EntityLivingBase thrower) {
         super(world, thrower);
         this.setSize(0.3F, 0.3F);
      }

      public KrakenShockbolt(World world, double x, double y, double z) {
         super(world, x, y, z);
         this.setSize(0.3F, 0.3F);
      }

      protected float getGravityVelocity() {
         return 0.0F;
      }

      public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
         float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
         float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
         float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
         this.shoot(f, f1, f2, velocity, inaccuracy);
      }

      public void onUpdate() {
         super.onUpdate();
         if (this.ticksExisted > 200) {
            this.setDead();
         }

         if (this.explodeTimer > 0) {
            this.explodeTimer--;
         } else if (this.explodeTimer == 0) {
            this.expl();
            this.explodeTimer = -1;
         }
      }

      public boolean handleWaterMovement() {
         return false;
      }

      public void writeEntityToNBT(NBTTagCompound compound) {
         super.writeEntityToNBT(compound);
         compound.setFloat("damage", this.damage);
      }

      public void readEntityFromNBT(NBTTagCompound compound) {
         super.readEntityFromNBT(compound);
         if (compound.hasKey("damage")) {
            this.damage = compound.getFloat("damage");
         }
      }

      @Override
      public void onClient(double... args) {
         if (args.length == 6) {
            Vec3d point1 = new Vec3d(args[0], args[1], args[2]);
            Vec3d point2 = new Vec3d(args[3], args[4], args[5]);
            BetweenParticle particle = new BetweenParticle(
               this.world,
               lightning4,
               0.38F,
               240,
               0.4F + this.rand.nextFloat() / 8.0F,
               0.3F + this.rand.nextFloat() / 8.0F,
               1.0F,
               0.08F,
               point1.distanceTo(point2),
               11,
               0.0F,
               9999.0F,
               point1,
               point2
            );
            particle.setPosition(point1.x, point1.y, point1.z);
            particle.alphaGlowing = true;
            this.world.spawnEntity(particle);
         }
      }

      @SideOnly(Side.CLIENT)
      public void handleStatusUpdate(byte id) {
         if (id == 8) {
            for (int i = 0; i < 3; i++) {
               AnimatedGParticle anim = new AnimatedGParticle(
                  hadronblast,
                  1.6F + this.rand.nextFloat() * 0.5F,
                  0.0F,
                  26,
                  240,
                  this.world,
                  this.posX,
                  this.posY,
                  this.posZ,
                  0.0F,
                  0.0F,
                  0.0F,
                  0.38F + this.rand.nextFloat() / 8.0F,
                  0.35F + this.rand.nextFloat() / 8.0F,
                  1.0F,
                  true,
                  this.rand.nextInt(360)
               );
               anim.framecount = 13;
               anim.alphaGlowing = true;
               anim.scaleTickAdding = 0.07F;
               anim.randomDeath = false;
               anim.animDelay = 2;
               if (i > 0) {
                  anim.rotationPitchYaw = new Vec2f(this.rand.nextInt(360), this.rand.nextInt(360));
               }

               this.world.spawnEntity(anim);
            }

            int lt = 4 + this.rand.nextInt(4);
            GUNParticle part = new GUNParticle(
               circle2,
               8.0F,
               0.0F,
               lt,
               240,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               0.0F,
               0.0F,
               0.0F,
               0.4F + this.rand.nextFloat() / 8.0F,
               0.3F + this.rand.nextFloat() / 8.0F,
               1.0F,
               true,
               this.rand.nextInt(360)
            );
            part.scaleTickAdding = -8.0F / lt;
            part.alphaGlowing = true;
            part.alpha = 0.0F;
            part.alphaTickAdding = 1.0F / lt;
            this.world.spawnEntity(part);
         }
      }

      protected void onImpact(RayTraceResult result) {
         if (!this.world.isRemote) {
            if (result.entityHit != null) {
               if (Team.checkIsOpponent(this.thrower, result.entityHit)) {
                  Weapons.dealDamage(
                     new WeaponDamage(null, this.getThrower(), this, false, true, this, WeaponDamage.magic),
                     this.damage,
                     this.getThrower(),
                     result.entityHit,
                     true,
                     2.2F
                  );
                  result.entityHit.hurtResistantTime = 0;
                  this.world
                     .playSound(
                        (EntityPlayer)null,
                        this.posX,
                        this.posY,
                        this.posZ,
                        Sounds.plasma_rifle_impact,
                        SoundCategory.AMBIENT,
                        0.9F,
                        0.9F + this.rand.nextFloat() / 5.0F
                     );
                  this.setDead();
                  DeathEffects.applyDeathEffect(result.entityHit, DeathEffects.DE_ELECTRIC, 0.3F);
               }
            } else if (this.world
                  .getBlockState(result.getBlockPos())
                  .getBlock()
                  .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
               != null) {
               this.world
                  .playSound(
                     (EntityPlayer)null,
                     this.posX,
                     this.posY,
                     this.posZ,
                     Sounds.plasma_rifle_impact,
                     SoundCategory.AMBIENT,
                     0.9F,
                     0.9F + this.rand.nextFloat() / 5.0F
                  );
               this.setDead();
            }
         }
      }

      public void startExplosion(Entity by) {
         if (!this.world.isRemote && this.explodeTimer < 0) {
            this.explodeTimer = 3;
            IEntitySynchronize.sendSynchronize(
               this,
               64.0,
               this.posX,
               this.posY + this.height / 2.0F,
               this.posZ,
               by.posX,
               by.posY + by.height / 2.0F,
               by.posZ
            );
         }
      }

      public void expl() {
         if (!this.world.isRemote) {
            double damageRadius = 7.0;
            AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
               .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
               .offset(-damageRadius, -damageRadius, -damageRadius);
            List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this.thrower, axisalignedbb);
            if (!list.isEmpty()) {
               for (Entity entity : list) {
                  if (this.getDistanceSq(entity) < 18.5 && Team.checkIsOpponent(this.thrower, entity)) {
                     Weapons.dealDamage(
                        new WeaponDamage(null, this.getThrower(), this, true, false, this, WeaponDamage.magic),
                        this.damage,
                        this.thrower,
                        entity,
                        true,
                        2.0F,
                        this.posX,
                        this.posY,
                        this.posZ
                     );
                     entity.hurtResistantTime = 0;
                     Weapons.setPotionIfEntityLB(entity, MobEffects.WEAKNESS, 350, 0);
                  }

                  if (entity instanceof KrakenShockbolt) {
                     KrakenShockbolt otherBolt = (KrakenShockbolt)entity;
                     otherBolt.startExplosion(this);
                  }
               }
            }

            this.world
               .playSound(
                  (EntityPlayer)null,
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.kraken_shock_explode,
                  SoundCategory.HOSTILE,
                  2.5F,
                  0.9F + this.rand.nextFloat() / 5.0F
               );
            this.world.setEntityState(this, (byte)8);
            this.setDead();
         }
      }
   }

   public static class KrakenSlime extends EntityThrowable {
      public static ResourceLocation acid_splash3 = new ResourceLocation("arpg:textures/acid_splash3.png");
      public static ResourceLocation acid_splash6 = new ResourceLocation("arpg:textures/acid_splash6.png");
      public float damage = 0.0F;

      public KrakenSlime(World world) {
         super(world);
         this.setSize(1.0F, 1.0F);
      }

      public KrakenSlime(World world, EntityLivingBase thrower) {
         super(world, thrower);
         this.setSize(1.0F, 1.0F);
      }

      public KrakenSlime(World world, double x, double y, double z) {
         super(world, x, y, z);
         this.setSize(1.0F, 1.0F);
      }

      protected float getGravityVelocity() {
         return 0.001F;
      }

      public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
         float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
         float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
         float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
         this.shoot(f, f1, f2, velocity, inaccuracy);
      }

      public void onUpdate() {
         super.onUpdate();
         if (this.ticksExisted > 180) {
            this.setDead();
         }
      }

      public boolean handleWaterMovement() {
         return false;
      }

      public void writeEntityToNBT(NBTTagCompound compound) {
         super.writeEntityToNBT(compound);
         compound.setFloat("damage", this.damage);
      }

      public void readEntityFromNBT(NBTTagCompound compound) {
         super.readEntityFromNBT(compound);
         if (compound.hasKey("damage")) {
            this.damage = compound.getFloat("damage");
         }
      }

      @SideOnly(Side.CLIENT)
      public void handleStatusUpdate(byte id) {
         if (id == 8) {
            for (int ss = 0; ss < this.rand.nextInt(4) + 2; ss++) {
               GUNParticle bigsmoke = new GUNParticle(
                  acid_splash3,
                  0.04F,
                  0.03F,
                  60,
                  220,
                  this.world,
                  this.lastTickPosX,
                  this.lastTickPosY,
                  this.lastTickPosZ,
                  (float)this.rand.nextGaussian() / 10.0F,
                  (float)this.rand.nextGaussian() / 10.0F,
                  (float)this.rand.nextGaussian() / 10.0F,
                  1.0F + this.rand.nextFloat() / 5.0F,
                  1.0F,
                  1.0F + this.rand.nextFloat() / 5.0F,
                  true,
                  this.rand.nextInt(360),
                  true,
                  3.6F
               );
               bigsmoke.alphaTickAdding = -0.0166F;
               bigsmoke.acidRender = true;
               bigsmoke.dropMode = true;
               bigsmoke.scaleTickDropAdding = 0.02F;
               bigsmoke.scaleMax = 0.45F + this.rand.nextFloat() / 3.0F;
               this.world.spawnEntity(bigsmoke);
            }

            for (int ss = 0; ss < 4; ss++) {
               GUNParticle spell = new GUNParticle(
                  acid_splash6,
                  0.08F + this.rand.nextFloat() * 0.03F,
                  0.008F,
                  50,
                  220,
                  this.world,
                  this.posX,
                  this.posY,
                  this.posZ,
                  (float)this.rand.nextGaussian() / 16.0F,
                  (float)this.rand.nextGaussian() / 16.0F,
                  (float)this.rand.nextGaussian() / 16.0F,
                  0.5F,
                  0.7F + this.rand.nextFloat() * 0.3F,
                  0.5F,
                  true,
                  0
               );
               spell.alphaGlowing = true;
               spell.scaleTickAdding = -0.0016F;
               this.world.spawnEntity(spell);
            }
         }
      }

      protected void onImpact(RayTraceResult result) {
         if (!this.world.isRemote) {
            if (result.entityHit != null) {
               if (Team.checkIsOpponent(this.thrower, result.entityHit)) {
                  Weapons.dealDamage(
                     new WeaponDamage(null, this.getThrower(), this, false, false, this, WeaponDamage.toxin),
                     this.damage,
                     this.getThrower(),
                     result.entityHit,
                     true,
                     1.0F
                  );
                  result.entityHit.hurtResistantTime = 0;
                  Weapons.setPotionIfEntityLB(result.entityHit, PotionEffects.SLIME, 250, 1);
                  if (this.world.getDifficulty().getId() >= 3) {
                     Weapons.setPotionIfEntityLB(result.entityHit, PotionEffects.TOXIN, 170, 0);
                  } else {
                     Weapons.setPotionIfEntityLB(result.entityHit, MobEffects.POISON, 200, 0);
                  }

                  this.world
                     .playSound(
                        (EntityPlayer)null,
                        this.posX,
                        this.posY,
                        this.posZ,
                        Sounds.ichorhit,
                        SoundCategory.AMBIENT,
                        0.9F,
                        1.0F + this.rand.nextFloat() / 5.0F
                     );
                  this.world.setEntityState(this, (byte)8);
                  this.setDead();
                  DeathEffects.applyDeathEffect(result.entityHit, DeathEffects.DE_COLOREDACID, 0.7F);
               }
            } else if (this.world
                  .getBlockState(result.getBlockPos())
                  .getBlock()
                  .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
               != null) {
               this.world
                  .playSound(
                     (EntityPlayer)null,
                     this.posX,
                     this.posY,
                     this.posZ,
                     Sounds.ichorhit,
                     SoundCategory.AMBIENT,
                     0.9F,
                     1.0F + this.rand.nextFloat() / 5.0F
                  );
               this.world.setEntityState(this, (byte)8);
               this.setDead();
            }
         }
      }
   }

   public static class LarvaFlyerShoot extends EntityThrowable {
      static ResourceLocation slime = new ResourceLocation("arpg:textures/slimesplash.png");
      static ResourceLocation texturbubble = new ResourceLocation("arpg:textures/bilebiter_shoot4.png");
      public float damage = 0.0F;

      public LarvaFlyerShoot(World world) {
         super(world);
         this.setSize(0.4F, 0.4F);
      }

      public LarvaFlyerShoot(World world, EntityLivingBase thrower) {
         super(world, thrower);
         this.setSize(0.4F, 0.4F);
      }

      public LarvaFlyerShoot(World world, double x, double y, double z) {
         super(world, x, y, z);
         this.setSize(0.4F, 0.4F);
      }

      public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
         float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
         float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
         float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
         this.shoot(f, f1, f2, velocity, inaccuracy);
         this.motionX = this.motionX + entityThrower.motionX * 0.1;
         this.motionZ = this.motionZ + entityThrower.motionZ * 0.1;
         if (!entityThrower.onGround) {
            this.motionY = this.motionY + entityThrower.motionY * 0.1;
         }
      }

      protected float getGravityVelocity() {
         return 0.0F;
      }

      public void onUpdate() {
         super.onUpdate();
         if (this.ticksExisted > 80) {
            this.setDead();
         }
      }

      public void writeEntityToNBT(NBTTagCompound compound) {
         super.writeEntityToNBT(compound);
         compound.setFloat("damage", this.damage);
      }

      public void readEntityFromNBT(NBTTagCompound compound) {
         super.readEntityFromNBT(compound);
         if (compound.hasKey("damage")) {
            this.damage = compound.getFloat("damage");
         }
      }

      @SideOnly(Side.CLIENT)
      public void handleStatusUpdate(byte id) {
         if (id == 8) {
            for (int ss = 0; ss < 5; ss++) {
               Entity bubble = new GUNParticle(
                  texturbubble,
                  0.06F + this.rand.nextFloat() * 0.04F,
                  0.03F,
                  50 + this.rand.nextInt(20),
                  200,
                  this.world,
                  this.posX,
                  this.posY,
                  this.posZ,
                  (float)this.rand.nextGaussian() / 9.0F,
                  (float)this.rand.nextGaussian() / 9.0F + 0.15F,
                  (float)this.rand.nextGaussian() / 9.0F,
                  1.0F,
                  1.0F,
                  1.0F,
                  false,
                  this.rand.nextInt(180),
                  true,
                  1.3F
               );
               this.world.spawnEntity(bubble);
            }

            for (int ss = 0; ss < 4; ss++) {
               GUNParticle bubble = new GUNParticle(
                  slime,
                  0.4F + this.rand.nextFloat() * 0.25F,
                  -0.001F,
                  10 + this.rand.nextInt(7),
                  -1,
                  this.world,
                  this.posX,
                  this.posY,
                  this.posZ,
                  (float)this.rand.nextGaussian() / 19.0F,
                  (float)this.rand.nextGaussian() / 22.0F + 0.05F,
                  (float)this.rand.nextGaussian() / 19.0F,
                  1.0F,
                  0.9F,
                  0.4F + this.rand.nextFloat() * 0.2F,
                  true,
                  this.rand.nextInt(180)
               );
               bubble.alphaTickAdding = -0.04F;
               bubble.scaleTickAdding = 0.03F;
               bubble.alphaGlowing = true;
               this.world.spawnEntity(bubble);
            }
         }
      }

      protected void onImpact(RayTraceResult result) {
         if (result.entityHit != null) {
            if (Team.checkIsOpponent(this.thrower, result.entityHit)
               && !this.world.isRemote
               && result.entityHit instanceof EntityLivingBase) {
               Weapons.dealDamage(
                  new WeaponDamage(null, this.getThrower(), this, false, true, this, WeaponDamage.pierce),
                  this.damage,
                  this.getThrower(),
                  result.entityHit,
                  true,
                  0.5F
               );
               Weapons.setPotionIfEntityLB(result.entityHit, MobEffects.SLOWNESS, 50, 3);
               Weapons.setPotionIfEntityLB(result.entityHit, MobEffects.NAUSEA, 100, 0);
               this.world
                  .playSound(
                     (EntityPlayer)null,
                     this.posX,
                     this.posY,
                     this.posZ,
                     Sounds.apple,
                     SoundCategory.AMBIENT,
                     0.5F,
                     1.2F + this.rand.nextFloat() / 5.0F
                  );
               this.world.setEntityState(this, (byte)8);
               this.setDead();
            }
         } else if (this.world
               .getBlockState(result.getBlockPos())
               .getBlock()
               .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
            != null) {
            this.world
               .playSound(
                  (EntityPlayer)null,
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.apple,
                  SoundCategory.AMBIENT,
                  0.5F,
                  1.2F + this.rand.nextFloat() / 5.0F
               );
            if (!this.world.isRemote) {
               this.world.setEntityState(this, (byte)8);
               this.setDead();
            }
         }
      }
   }

   public static class LavaShoot extends EntityThrowable {
      public static ResourceLocation trail = new ResourceLocation("arpg:textures/lava_tail.png");
      public static ResourceLocation tex = new ResourceLocation("arpg:textures/sparkle.png");
      public float damage = 0.0F;

      public LavaShoot(World world) {
         super(world);
         this.setSize(0.5F, 0.5F);
      }

      public LavaShoot(World world, EntityLivingBase thrower) {
         super(world, thrower);
         this.setSize(0.5F, 0.5F);
      }

      public LavaShoot(World world, double x, double y, double z) {
         super(world, x, y, z);
         this.setSize(0.5F, 0.5F);
      }

      public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
         float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
         float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
         float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
         this.shoot(f, f1, f2, velocity, inaccuracy);
         this.motionX = this.motionX + entityThrower.motionX * 0.5;
         this.motionZ = this.motionZ + entityThrower.motionZ * 0.5;
         if (!entityThrower.onGround) {
            this.motionY = this.motionY + entityThrower.motionY * 0.1;
         }
      }

      public void tryPlaceLava(RayTraceResult result) {
         BlockPos pos = result.sideHit == EnumFacing.UP ? this.getPosition() : this.getPosition().down();
         IBlockState state = this.world.getBlockState(pos);
         if (state.getBlock() == Blocks.FLOWING_LAVA) {
            pos = pos.up();
         }

         if (Weapons.easyBreakBlockFor(this.world, 0.4F, pos)) {
            this.world.destroyBlock(pos, true);
            this.world.setBlockState(pos, Blocks.FLOWING_LAVA.getStateFromMeta(2));
         }
      }

      protected float getGravityVelocity() {
         return 0.04F;
      }

      public void onUpdate() {
         super.onUpdate();
         if (this.ticksExisted > 80) {
            this.setDead();
         }
      }

      public void writeEntityToNBT(NBTTagCompound compound) {
         super.writeEntityToNBT(compound);
         compound.setFloat("damage", this.damage);
      }

      public void readEntityFromNBT(NBTTagCompound compound) {
         super.readEntityFromNBT(compound);
         if (compound.hasKey("damage")) {
            this.damage = compound.getFloat("damage");
         }
      }

      @SideOnly(Side.CLIENT)
      public void handleStatusUpdate(byte id) {
         if (id == 8) {
            for (int ss = 0; ss < 8; ss++) {
               GUNParticle spell = new GUNParticle(
                  tex,
                  0.1F,
                  0.02F,
                  40,
                  180,
                  this.world,
                  this.posX,
                  this.posY,
                  this.posZ,
                  (float)this.rand.nextGaussian() / 16.0F,
                  (float)this.rand.nextGaussian() / 16.0F + 0.1F,
                  (float)this.rand.nextGaussian() / 16.0F,
                  0.9F + this.rand.nextFloat() / 10.0F,
                  1.0F,
                  1.0F,
                  true,
                  0,
                  true,
                  1.0F
               );
               spell.scaleTickAdding = -0.0025F;
               spell.alphaGlowing = true;
               this.world.spawnEntity(spell);
               this.world.spawnParticle(EnumParticleTypes.LAVA, this.posX, this.posY, this.posZ, 0.0, 0.5, 0.0, new int[0]);
            }
         }
      }

      protected void onImpact(RayTraceResult result) {
         if (!this.world.isRemote) {
            this.tryPlaceLava(result);
         }

         if (result.entityHit != null) {
            if (Team.checkIsOpponent(this.thrower, result.entityHit) && !this.world.isRemote) {
               Weapons.dealDamage(
                  new WeaponDamage(null, this.getThrower(), this, false, true, this, WeaponDamage.fire),
                  this.damage,
                  this.getThrower(),
                  result.entityHit,
                  false
               );
               this.world
                  .playSound(
                     (EntityPlayer)null,
                     this.posX,
                     this.posY,
                     this.posZ,
                     Sounds.lava_dropper_impact,
                     SoundCategory.AMBIENT,
                     0.4F,
                     1.1F + this.rand.nextFloat() / 5.0F
                  );
               this.world.setEntityState(this, (byte)8);
               this.setDead();
            }
         } else if (this.world
               .getBlockState(result.getBlockPos())
               .getBlock()
               .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
            != null) {
            this.world
               .playSound(
                  (EntityPlayer)null,
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.lava_dropper_impact,
                  SoundCategory.AMBIENT,
                  0.4F,
                  1.1F + this.rand.nextFloat() / 5.0F
               );
            if (!this.world.isRemote) {
               this.world.setEntityState(this, (byte)8);
               this.setDead();
            }
         }
      }

      @SideOnly(Side.CLIENT)
      public void onEntityUpdate() {
         Vec3d pos1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
         Vec3d pos2 = this.getPositionVector();
         if (this.world.isRemote) {
            if (pos1.lengthSquared() > 1.0E-6 && pos2.lengthSquared() > 1.0E-6) {
               BetweenParticle laser = new BetweenParticle(
                  this.world, trail, 0.09F, 240, 1.0F, 0.9F, 1.0F, 0.1F, pos1.distanceTo(pos2), 5, -0.15F, 9999.0F, pos1, pos2
               );
               laser.alphaGlowing = true;
               laser.texstart = 0.1F;
               laser.offset = -0.1F;
               laser.setPosition(pos1.x, pos1.y, pos1.z);
               this.world.spawnEntity(laser);
            }

            GUNParticle spell = new GUNParticle(
               tex,
               0.1F,
               0.01F - this.rand.nextFloat() * 0.02F,
               40,
               180,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               (float)this.rand.nextGaussian() / 36.0F,
               (float)this.rand.nextGaussian() / 36.0F,
               (float)this.rand.nextGaussian() / 36.0F,
               0.9F + this.rand.nextFloat() / 10.0F,
               1.0F,
               1.0F,
               true,
               0
            );
            spell.scaleTickAdding = -0.0025F;
            spell.alphaGlowing = true;
            this.world.spawnEntity(spell);
         }
      }
   }

   public static class MermaidShoot extends EntityThrowable {
      public static ResourceLocation tex = new ResourceLocation("arpg:textures/ichor_cube.png");
      public static ResourceLocation star2 = new ResourceLocation("arpg:textures/star2.png");
      public static ResourceLocation circle = new ResourceLocation("arpg:textures/circle.png");
      public float damage = 0.0F;
      public int explodeTime = 80;

      public MermaidShoot(World world) {
         super(world);
         this.setSize(0.5F, 0.5F);
      }

      public MermaidShoot(World world, EntityLivingBase thrower) {
         super(world, thrower);
         this.setSize(0.5F, 0.5F);
      }

      public MermaidShoot(World world, double x, double y, double z) {
         super(world, x, y, z);
         this.setSize(0.5F, 0.5F);
      }

      public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
         float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
         float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
         float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
         this.shoot(f, f1, f2, velocity, inaccuracy);
      }

      protected float getGravityVelocity() {
         return 0.0F;
      }

      public void onUpdate() {
         super.onUpdate();
         if (this.ticksExisted > this.explodeTime) {
            this.expl();
         }

         if (this.world.isRemote) {
            EntityCubicParticle spel = new EntityCubicParticle(
               tex,
               0.026F,
               0.0F,
               11 + this.rand.nextInt(3),
               240,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               (float)this.rand.nextGaussian() / 48.0F,
               (float)this.rand.nextGaussian() / 48.0F,
               (float)this.rand.nextGaussian() / 48.0F,
               1.0F,
               1.0F,
               1.0F,
               true,
               this.rand.nextFloat(),
               this.rand.nextFloat(),
               this.rand.nextFloat(),
               0.08F,
               true,
               -0.002F
            );
            spel.alphaGlowing = true;
            this.world.spawnEntity(spel);
         }
      }

      public void writeEntityToNBT(NBTTagCompound compound) {
         super.writeEntityToNBT(compound);
         compound.setFloat("damage", this.damage);
      }

      public void readEntityFromNBT(NBTTagCompound compound) {
         super.readEntityFromNBT(compound);
         if (compound.hasKey("damage")) {
            this.damage = compound.getFloat("damage");
         }
      }

      @SideOnly(Side.CLIENT)
      public void handleStatusUpdate(byte id) {
         if (id == 8) {
            for (int ss = 0; ss < 17; ss++) {
               GUNParticle spell = new GUNParticle(
                  star2,
                  0.1F + this.rand.nextFloat() * 0.1F,
                  -5.0E-4F,
                  40,
                  240,
                  this.world,
                  this.posX,
                  this.posY,
                  this.posZ,
                  (float)this.rand.nextGaussian() / 9.0F,
                  (float)this.rand.nextGaussian() / 9.0F,
                  (float)this.rand.nextGaussian() / 9.0F,
                  1.0F,
                  1.0F,
                  0.4F - this.rand.nextFloat() * 0.2F,
                  true,
                  this.rand.nextInt(31) - 30
               );
               spell.alphaGlowing = true;
               spell.scaleTickAdding = -0.0016F;
               this.world.spawnEntity(spell);
            }

            for (int ss = 0; ss < 4; ss++) {
               float fsize = 2.6F + this.rand.nextFloat() * 0.7F;
               int lt = 4 + this.rand.nextInt(4);
               GUNParticle part = new GUNParticle(
                  circle,
                  0.2F,
                  0.0F,
                  lt,
                  220,
                  this.world,
                  this.posX,
                  this.posY,
                  this.posZ,
                  0.0F,
                  0.0F,
                  0.0F,
                  1.0F,
                  1.0F,
                  0.4F - this.rand.nextFloat() * 0.2F,
                  true,
                  this.rand.nextInt(360)
               );
               part.scaleTickAdding = fsize / lt;
               part.alphaGlowing = true;
               part.alphaTickAdding = -0.4F / lt;
               part.randomDeath = false;
               if (ss > 0) {
                  part.rotationPitchYaw = new Vec2f(this.rand.nextInt(360), this.rand.nextInt(360));
               }

               this.world.spawnEntity(part);
            }
         }
      }

      protected void onImpact(RayTraceResult result) {
         if (result.entityHit != null) {
            if (Team.checkIsOpponent(this.thrower, result.entityHit)) {
               this.expl();
            }
         } else if (this.world
               .getBlockState(result.getBlockPos())
               .getBlock()
               .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
            != null) {
            for (BlockPos blockpos : GetMOP.getPosesInsideSphere((int)this.posX, (int)this.posY, (int)this.posZ, 1)) {
               IBlockState state = this.world.getBlockState(blockpos);
               if (state.getBlock() != Blocks.WATER
                  && state.getBlock() != Blocks.FLOWING_WATER
                  && Weapons.easyBreakBlockFor(this.world, 20.0F, blockpos, state)) {
                  if (!state.getBlock().hasTileEntity(state) && !(this.rand.nextFloat() < 0.5F)) {
                     this.world.destroyBlock(blockpos, false);
                  } else {
                     this.world.destroyBlock(blockpos, true);
                  }
               }
            }
         }
      }

      public void expl() {
         if (!this.world.isRemote) {
            double damageRadius = 3.0;
            AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
               .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
               .offset(-damageRadius, -damageRadius, -damageRadius);
            List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this.thrower, axisalignedbb);
            if (!list.isEmpty()) {
               for (Entity entity : list) {
                  if (Team.checkIsOpponent(this.thrower, entity)) {
                     Weapons.dealDamage(
                        new WeaponDamage(null, this.getThrower(), this, false, false, this, WeaponDamage.magic),
                        this.damage,
                        this.thrower,
                        entity,
                        true,
                        1.4F,
                        this.posX,
                        this.posY,
                        this.posZ
                     );
                     entity.hurtResistantTime = 0;
                     Weapons.setPotionIfEntityLB(entity, PotionEffects.ICHOR_CURSE, 300, 0);
                  }
               }
            }

            this.world
               .playSound(
                  (EntityPlayer)null,
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.explode_water_b,
                  SoundCategory.HOSTILE,
                  1.0F,
                  0.9F + this.rand.nextFloat() / 5.0F
               );
            this.world.setEntityState(this, (byte)8);
            this.setDead();
         }
      }

      public boolean handleWaterMovement() {
         return false;
      }
   }

   public static class OceanSpiritShoot extends EntityThrowable {
      public static ResourceLocation trail = new ResourceLocation("arpg:textures/ocean_trace.png");
      public static ResourceLocation tex = new ResourceLocation("arpg:textures/blob.png");
      public static ResourceLocation texmagic1 = new ResourceLocation("arpg:textures/bubbleglow1.png");
      public static ResourceLocation texmagic2 = new ResourceLocation("arpg:textures/bubbleglow2.png");
      public float damage = 0.0F;

      public OceanSpiritShoot(World world) {
         super(world);
         this.setSize(0.2F, 0.2F);
      }

      public OceanSpiritShoot(World world, EntityLivingBase thrower) {
         super(world, thrower);
         this.setSize(0.2F, 0.2F);
      }

      public OceanSpiritShoot(World world, double x, double y, double z) {
         super(world, x, y, z);
         this.setSize(0.2F, 0.2F);
      }

      public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
         float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
         float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
         float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
         this.shoot(f, f1, f2, velocity, inaccuracy);
      }

      protected float getGravityVelocity() {
         return 0.006F;
      }

      public void onUpdate() {
         super.onUpdate();
         if (this.ticksExisted > 45) {
            this.expl();
         }
      }

      public void writeEntityToNBT(NBTTagCompound compound) {
         super.writeEntityToNBT(compound);
         compound.setFloat("damage", this.damage);
      }

      public void readEntityFromNBT(NBTTagCompound compound) {
         super.readEntityFromNBT(compound);
         if (compound.hasKey("damage")) {
            this.damage = compound.getFloat("damage");
         }
      }

      @SideOnly(Side.CLIENT)
      public void handleStatusUpdate(byte id) {
         if (id == 8) {
            for (int ss = 0; ss < 15; ss++) {
               GUNParticle spell = new GUNParticle(
                  this.rand.nextFloat() < 0.5F ? texmagic1 : texmagic2,
                  0.06F + this.rand.nextFloat() * 0.07F,
                  -5.0E-4F,
                  40,
                  220,
                  this.world,
                  this.posX,
                  this.posY,
                  this.posZ,
                  (float)this.rand.nextGaussian() / 13.0F,
                  (float)this.rand.nextGaussian() / 13.0F,
                  (float)this.rand.nextGaussian() / 13.0F,
                  0.5F,
                  1.0F,
                  1.0F - this.rand.nextFloat() * 0.3F,
                  true,
                  this.rand.nextInt(31) - 30
               );
               spell.alphaGlowing = true;
               spell.scaleTickAdding = -0.0016F;
               this.world.spawnEntity(spell);
            }

            int lt = 6 + this.rand.nextInt(4);
            GUNParticle part = new GUNParticle(
               tex,
               7.5F,
               0.0F,
               lt,
               220,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               0.0F,
               0.0F,
               0.0F,
               0.5F,
               1.0F,
               0.9F,
               true,
               this.rand.nextInt(21) - 20
            );
            part.scaleTickAdding = -7.5F / lt;
            part.alphaGlowing = true;
            part.alphaTickAdding = -0.8F / lt;
            part.randomDeath = false;
            this.world.spawnEntity(part);
         }

         Vec3d startpoint = new Vec3d(this.rand.nextGaussian(), this.rand.nextGaussian() / 2.0, this.rand.nextGaussian())
            .normalize()
            .scale(5.0);
         Vec3d axisVector = new Vec3d(this.rand.nextGaussian(), this.rand.nextFloat() * 8.0F, this.rand.nextGaussian());

         for (int ss = 0; ss < 12; ss++) {
            Vec3d rotvec = GetMOP.rotateVecAroundAxis(startpoint, axisVector, 30 * ss);
            Vec3d posvec = rotvec.add(this.posX, this.posY, this.posZ);
            Vec3d pwvec = GetMOP.Vec3dToPitchYaw(rotvec);
            int lt = 16;
            GUNParticle spell = new GUNParticle(
               this.rand.nextFloat() < 0.5F ? texmagic1 : texmagic2,
               0.14F + this.rand.nextFloat() * 0.07F,
               0.0F,
               lt,
               240,
               this.world,
               posvec.x,
               posvec.y,
               posvec.z,
               (float)(-rotvec.x) / lt,
               (float)(-rotvec.y) / lt,
               (float)(-rotvec.z) / lt,
               1.0F,
               1.0F,
               1.0F,
               true,
               this.rand.nextInt(31) - 30
            );
            spell.alphaGlowing = true;
            spell.scaleTickAdding = -0.01F;
            spell.rotationPitchYaw = new Vec2f((float)pwvec.x, (float)pwvec.y);
            this.world.spawnEntity(spell);
         }
      }

      protected void onImpact(RayTraceResult result) {
         if (result.entityHit != null) {
            if (Team.checkIsOpponent(this.thrower, result.entityHit)) {
               this.expl();
            }
         } else if (this.world
               .getBlockState(result.getBlockPos())
               .getBlock()
               .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
            != null) {
            this.expl();
         }
      }

      public void expl() {
         if (!this.world.isRemote) {
            double damageRadius = 4.5;
            AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
               .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
               .offset(-damageRadius, -damageRadius, -damageRadius);
            List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this.thrower, axisalignedbb);
            if (!list.isEmpty()) {
               for (Entity entity : list) {
                  if (Team.checkIsOpponent(this.thrower, entity)) {
                     Weapons.dealDamage(
                        new WeaponDamage(null, this.getThrower(), this, true, false, this, WeaponDamage.water),
                        this.damage,
                        this.thrower,
                        entity,
                        true,
                        -2.5F,
                        this.posX,
                        this.posY,
                        this.posZ
                     );
                     entity.hurtResistantTime = 0;
                  }
               }
            }

            this.world
               .playSound(
                  (EntityPlayer)null,
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.explode_water_b,
                  SoundCategory.HOSTILE,
                  1.0F,
                  0.9F + this.rand.nextFloat() / 5.0F
               );
            this.world.setEntityState(this, (byte)8);
            this.setDead();
         }
      }

      @SideOnly(Side.CLIENT)
      public void onEntityUpdate() {
         Vec3d pos1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
         Vec3d pos2 = this.getPositionVector();
         if (this.world.isRemote && pos1.lengthSquared() > 1.0E-6 && pos2.lengthSquared() > 1.0E-6) {
            BetweenParticle laser = new BetweenParticle(
               this.world, trail, 0.28F, 220, 1.0F, 1.0F, 1.0F, 0.1F, pos1.distanceTo(pos2), 5, -0.15F, 9999.0F, pos1, pos2
            );
            laser.alphaGlowing = true;
            laser.texstart = 0.1F;
            laser.offset = -0.1F;
            laser.setPosition(pos1.x, pos1.y, pos1.z);
            this.world.spawnEntity(laser);
         }
      }
   }

   public static class Plasma extends Entity implements RenderModule.IRenderModuleMulticolored {
      static ResourceLocation plasma_a = new ResourceLocation("arpg:textures/plasma_a.png");
      static ResourceLocation plasma_b = new ResourceLocation("arpg:textures/plasma_b.png");
      protected EntityLivingBase thrower;
      private String throwerName;
      public float gravity;
      public int livetime;
      public float damage;
      public float knockback;
      public int hitsHas;
      public boolean deadOnImpact;
      public float airFrictionMultiplier = 0.0F;
      public int damageCooldown;
      public int damageCooldownMax = 10;
      private static final DataParameter<Float> RED = EntityDataManager.createKey(Plasma.class, DataSerializers.FLOAT);
      private static final DataParameter<Float> GREEN = EntityDataManager.createKey(Plasma.class, DataSerializers.FLOAT);
      private static final DataParameter<Float> BLUE = EntityDataManager.createKey(Plasma.class, DataSerializers.FLOAT);
      private static final DataParameter<Float> SCALE = EntityDataManager.createKey(Plasma.class, DataSerializers.FLOAT);
      private static final DataParameter<Integer> LIVETIMEMAX = EntityDataManager.createKey(Plasma.class, DataSerializers.VARINT);

      public Plasma(World world) {
         super(world);
         this.setSize(0.95F, 0.95F);
         this.livetime = 80;
      }

      public Plasma(World world, EntityLivingBase thrower) {
         super(world);
         this.thrower = thrower;
         this.setSize(0.95F, 0.95F);
         this.livetime = 80;
      }

      public Plasma(World world, double x, double y, double z) {
         super(world);
         this.setPosition(x, y, z);
         this.setSize(0.95F, 0.95F);
         this.livetime = 80;
      }

      public Plasma(
         World world,
         EntityLivingBase thrower,
         float scale,
         float gravity,
         int livetime,
         double x,
         double y,
         double z,
         float damage,
         float knockback,
         int hitsHas,
         boolean deadOnImpact,
         float red,
         float green,
         float blue
      ) {
         super(world);
         this.thrower = thrower;
         this.setPosition(x, y, z);
         this.setSize(scale, scale);
         this.setColor(red, green, blue);
         this.gravity = gravity;
         this.livetime = livetime;
         this.damage = damage;
         this.knockback = knockback;
         this.hitsHas = hitsHas;
         this.deadOnImpact = deadOnImpact;
         this.dataManager.set(SCALE, scale);
         this.dataManager.set(LIVETIMEMAX, livetime);
      }

      protected void entityInit() {
         this.dataManager.register(RED, 1.0F);
         this.dataManager.register(GREEN, 1.0F);
         this.dataManager.register(BLUE, 1.0F);
         this.dataManager.register(SCALE, 0.95F);
         this.dataManager.register(LIVETIMEMAX, 80);
      }

      public void setColor(float r, float g, float b) {
         this.dataManager.set(RED, r);
         this.dataManager.set(GREEN, g);
         this.dataManager.set(BLUE, b);
      }

      public float getSCALE() {
         return (Float)this.dataManager.get(SCALE);
      }

      public float getRED() {
         return (Float)this.dataManager.get(RED);
      }

      public float getGREEN() {
         return (Float)this.dataManager.get(GREEN);
      }

      public float getBLUE() {
         return (Float)this.dataManager.get(BLUE);
      }

      @Override
      public Vec3d getColor(int index) {
         float ticks = this.ticksExisted;
         if (this.world.isRemote) {
            ticks += Minecraft.getMinecraft().getRenderPartialTicks();
         }

         float reduction = MathHelper.clamp(
            1.0F / (ticks - 2.0F - ((Integer)this.dataManager.get(LIVETIMEMAX)).intValue()) * 2.0F + 1.0F, 0.0F, 1.0F
         );
         return new Vec3d(this.getRED() * reduction, this.getGREEN() * reduction, this.getBLUE() * reduction);
      }

      protected float getGravityVelocity() {
         return this.gravity;
      }

      @SideOnly(Side.CLIENT)
      public boolean isInRangeToRenderDist(double distance) {
         double d0 = this.getEntityBoundingBox().getAverageEdgeLength() * 4.0;
         if (Double.isNaN(d0)) {
            d0 = 4.0;
         }

         d0 *= 64.0;
         return distance < d0 * d0;
      }

      public void onUpdate() {
         this.lastTickPosX = this.posX;
         this.lastTickPosY = this.posY;
         this.lastTickPosZ = this.posZ;
         super.onUpdate();
         float f1 = 0.99F;
         float f2 = this.getGravityVelocity();
         if (this.isInWater()) {
            for (int j = 0; j < 4; j++) {
               float f3 = 0.25F;
               this.world
                  .spawnParticle(
                     EnumParticleTypes.WATER_BUBBLE,
                     this.posX - this.motionX * 0.25,
                     this.posY - this.motionY * 0.25,
                     this.posZ - this.motionZ * 0.25,
                     this.motionX,
                     this.motionY,
                     this.motionZ,
                     new int[0]
                  );
            }

            f1 = 0.8F;
         }

         this.motionX *= f1;
         this.motionY *= f1;
         this.motionZ *= f1;
         if (!this.hasNoGravity()) {
            this.motionY -= f2;
         }

         this.setPosition(this.posX, this.posY, this.posZ);
         if (!this.world.isRemote) {
            if (this.ticksExisted > this.livetime) {
               this.setDead();
            }

            if (this.damageCooldown <= 0) {
               if (this.hitsHas > 0) {
                  boolean dealt = false;
                  List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox());
                  if (!list.isEmpty()) {
                     for (Entity entity : list) {
                        if (Team.checkIsOpponent(this.getThrower(), entity)) {
                           if (Weapons.dealDamage(
                              new WeaponDamage(null, this.getThrower(), this, false, false, this, WeaponDamage.plasma),
                              this.damage,
                              this.getThrower(),
                              entity,
                              true,
                              this.knockback,
                              this.posX,
                              this.posY,
                              this.posX
                           )) {
                              dealt = true;
                           }

                           entity.hurtResistantTime = 0;
                        }
                     }
                  }

                  if (dealt) {
                     this.hitsHas--;
                     if (this.hitsHas <= 0) {
                        this.setDead();
                        return;
                     }

                     this.damageCooldown = this.damageCooldownMax;
                  }
               }
            } else {
               this.damageCooldown--;
            }
         } else if (this.ticksExisted % 10 == 0) {
            float scl = 0.1F + this.width * (0.17F + this.rand.nextFloat() * 0.3F);
            int lt = 40 + this.rand.nextInt(40);
            double xx = this.posX + this.rand.nextGaussian() * 0.6 * this.width;
            double yy = this.posY + this.rand.nextGaussian() * 0.6 * this.height + this.height / 2.0F;
            double zz = this.posZ + this.rand.nextGaussian() * 0.6 * this.width;
            ParticleTracker.TrackerSmoothShowHide tssh = new ParticleTracker.TrackerSmoothShowHide(
               new Vec3d[]{new Vec3d(0.0, 10.0, 0.1), new Vec3d(lt - 10, lt, -0.1)}, null
            );
            boolean typee = this.rand.nextFloat() < 0.5F;
            AnimatedGParticle particle = new AnimatedGParticle(
               typee ? plasma_a : plasma_b,
               scl,
               0.0F,
               lt,
               240,
               this.world,
               xx,
               yy,
               zz,
               0.0F,
               0.0F,
               0.0F,
               this.getRED(),
               this.getGREEN(),
               this.getBLUE(),
               true,
               0
            );
            particle.alphaGlowing = true;
            particle.tracker = tssh;
            particle.alpha = 0.0F;
            particle.framecount = typee ? 14 : 24;
            particle.animCounter = this.rand.nextInt(10);
            particle.useNormalTime = true;
            particle.disableOnEnd = false;
            particle.noWaterBubble = true;
            particle.isPushedByLiquids = false;
            this.world.spawnEntity(particle);
         }

         this.motionX = this.motionX * this.airFrictionMultiplier;
         this.motionY = this.motionY * this.airFrictionMultiplier;
         this.motionZ = this.motionZ * this.airFrictionMultiplier;
         if (this.width != this.getSCALE()) {
            this.setSize(this.getSCALE(), this.getSCALE());
         }
      }

      public void writeEntityToNBT(NBTTagCompound compound) {
         compound.setFloat("damage", this.damage);
         if ((this.throwerName == null || this.throwerName.isEmpty()) && this.thrower instanceof EntityPlayer) {
            this.throwerName = this.thrower.getName();
         }

         compound.setString("ownerName", this.throwerName == null ? "" : this.throwerName);
      }

      public void readEntityFromNBT(NBTTagCompound compound) {
         if (compound.hasKey("damage")) {
            this.damage = compound.getFloat("damage");
         }

         this.thrower = null;
         this.throwerName = compound.getString("ownerName");
         if (this.throwerName != null && this.throwerName.isEmpty()) {
            this.throwerName = null;
         }

         this.thrower = this.getThrower();
      }

      @Nullable
      public EntityLivingBase getThrower() {
         if (this.thrower == null && this.throwerName != null && !this.throwerName.isEmpty()) {
            this.thrower = this.world.getPlayerEntityByName(this.throwerName);
            if (this.thrower == null && this.world instanceof WorldServer) {
               try {
                  Entity entity = ((WorldServer)this.world).getEntityFromUuid(UUID.fromString(this.throwerName));
                  if (entity instanceof EntityLivingBase) {
                     this.thrower = (EntityLivingBase)entity;
                  }
               } catch (Throwable var21) {
                  this.thrower = null;
               }
            }
         }

         return this.thrower;
      }
   }

   public static class PlasmaRing extends EntityThrowable implements IEntitySynchronize {
      public float damage = 0.0F;
      public int type = 0;
      public float radius = 0.0F;
      public boolean firstUpdate1 = true;

      public PlasmaRing(World world) {
         super(world);
         this.setSize(0.5F, 0.5F);
      }

      public PlasmaRing(World world, EntityLivingBase thrower) {
         super(world, thrower);
         this.setSize(0.5F, 0.5F);
      }

      public PlasmaRing(World world, double x, double y, double z) {
         super(world, x, y, z);
         this.setSize(0.5F, 0.5F);
      }

      @Override
      public void onClient(double... args) {
         if (args.length == 1) {
            this.radius = (float)args[0];
         }
      }

      @SideOnly(Side.CLIENT)
      public void setGlColor() {
         if (this.type == 1) {
            GlStateManager.color(0.7F, 0.47F, 0.32F, 1.0F);
         }

         if (this.type == 2) {
            GlStateManager.color(0.45F, 0.87F, 0.61F, 1.0F);
         }

         if (this.type == 3) {
            GlStateManager.color(0.47F, 0.72F, 1.0F, 1.0F);
         }
      }

      public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
         float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
         float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
         float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
         this.shoot(f, f1, f2, velocity, inaccuracy);
         this.motionX = this.motionX + entityThrower.motionX * 0.3;
         this.motionZ = this.motionZ + entityThrower.motionZ * 0.3;
         if (!entityThrower.onGround) {
            this.motionY = this.motionY + entityThrower.motionY * 0.3;
         }
      }

      protected float getGravityVelocity() {
         return 0.0F;
      }

      public void onUpdate() {
         super.onUpdate();
         if (this.world.isRemote && this.firstUpdate1) {
            this.firstUpdate1 = false;
            Minecraft.getMinecraft()
               .getSoundHandler()
               .playSound(
                  new MovingSoundEntity(this, Sounds.plasma_ring, SoundCategory.AMBIENT, 1.0F, 0.95F + this.rand.nextFloat() / 10.0F, true)
               );
         }

         if (this.ticksExisted > 100) {
            this.setDead();
         }

         if (!this.world.isRemote) {
            if (this.ticksExisted <= 2 || this.ticksExisted % 40 == 0) {
               this.world.setEntityState(this, (byte)(this.type + 8));
               IEntitySynchronize.sendSynchronize(this, 64.0, this.radius);
            }

            Vec3d normal = this.getVectorForRotation(this.rotationPitch, this.rotationYaw);
            Vec3d center = this.getPositionVector();
            float ringRadius = this.radius;
            float size = Math.min(this.ticksExisted, 15) / 15.0F * ringRadius;
            size *= size;

            for (Entity entity : GetMOP.getEntitiesInAABBof(this.world, center, this.radius + 1.0, this)) {
               if (center.squareDistanceTo(GetMOP.entityCenterPos(entity)) <= size
                  && GetMOP.isBoxInPlane(center, normal, entity.getEntityBoundingBox())
                  && Team.checkIsOpponent(this.thrower, entity)) {
                  Vec3d damageLocation = entity.getPositionVector().subtract(normal);
                  Weapons.dealDamage(
                     new WeaponDamage(null, this.getThrower(), this, false, false, damageLocation, WeaponDamage.plasma),
                     this.damage,
                     this.thrower,
                     entity,
                     true
                  );
                  if (this.type == 1) {
                     Weapons.setPotionIfEntityLB(entity, PotionEffects.BLOOD_THIRST, 100 + this.world.getDifficulty().getId() * 100, 0);
                     entity.setFire(8);
                     DeathEffects.applyDeathEffect(entity, DeathEffects.DE_FIRE, 0.75F);
                  }

                  if (this.type == 2) {
                     Weapons.setPotionIfEntityLB(entity, PotionEffects.TOXIN, 180, this.world.getDifficulty().getId());
                     DeathEffects.applyDeathEffect(entity, DeathEffects.DE_COLOREDACID, 0.4F);
                  }

                  if (this.type == 3) {
                     PotionEffect lastdebaff = Weapons.mixPotion(
                        entity,
                        PotionEffects.FREEZING,
                        60.0F,
                        8.0F,
                        Weapons.EnumPotionMix.WITH_MAXIMUM,
                        Weapons.EnumPotionMix.WITH_MAXIMUM,
                        Weapons.EnumMathOperation.PLUS,
                        Weapons.EnumMathOperation.PLUS,
                        120,
                        16
                     );
                     Freezing.tryPlaySound(entity, lastdebaff);
                     DeathEffects.applyDeathEffect(entity, DeathEffects.DE_ICING, 0.5F);
                  }
               }
            }
         }
      }

      public void handleStatusUpdate(byte id) {
         super.handleStatusUpdate(id);
         if (id >= 8 && id <= 11) {
            this.type = id - 8;
         }
      }

      public void writeEntityToNBT(NBTTagCompound compound) {
         super.writeEntityToNBT(compound);
         compound.setFloat("damage", this.damage);
      }

      public void readEntityFromNBT(NBTTagCompound compound) {
         super.readEntityFromNBT(compound);
         if (compound.hasKey("damage")) {
            this.damage = compound.getFloat("damage");
         }
      }

      protected void onImpact(RayTraceResult result) {
         if (result.entityHit == null
            && this.world
                  .getBlockState(result.getBlockPos())
                  .getBlock()
                  .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
               != null) {
            this.world
               .playSound(
                  (EntityPlayer)null,
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.bullet,
                  SoundCategory.AMBIENT,
                  0.8F,
                  0.9F + this.rand.nextFloat() / 5.0F
               );
            if (!this.world.isRemote) {
               this.setDead();
            }
         }
      }
   }

   public static class PoisonSpit extends EntityThrowable {
      public static ResourceLocation tex = new ResourceLocation("arpg:textures/simple_magic_shoot.png");
      public static ResourceLocation cloud = new ResourceLocation("arpg:textures/largecloud.png");
      public float damage = 0.0F;

      public PoisonSpit(World world) {
         super(world);
         this.setSize(0.2F, 0.2F);
      }

      public PoisonSpit(World world, EntityLivingBase thrower) {
         super(world, thrower);
         this.setSize(0.2F, 0.2F);
      }

      public PoisonSpit(World world, double x, double y, double z) {
         super(world, x, y, z);
         this.setSize(0.2F, 0.2F);
      }

      public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
         float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
         float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
         float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
         this.shoot(f, f1, f2, velocity, inaccuracy);
         this.motionX = this.motionX + entityThrower.motionX * 0.4;
         this.motionZ = this.motionZ + entityThrower.motionZ * 0.4;
         if (!entityThrower.onGround) {
            this.motionY = this.motionY + entityThrower.motionY * 0.4;
         }
      }

      protected float getGravityVelocity() {
         return 0.02F;
      }

      public void onUpdate() {
         super.onUpdate();
         if (this.ticksExisted > 80) {
            this.setDead();
         }

         if (this.world.isRemote) {
            GUNParticle spell = new GUNParticle(
               tex,
               0.12F,
               0.01F,
               30,
               150,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               0.0F,
               0.0F,
               0.0F,
               0.2F + this.rand.nextFloat() / 10.0F,
               1.0F,
               0.35F,
               true,
               0
            );
            spell.alphaGlowing = true;
            spell.scaleTickAdding = -0.0026F;
            this.world.spawnEntity(spell);
         }
      }

      public void writeEntityToNBT(NBTTagCompound compound) {
         super.writeEntityToNBT(compound);
         compound.setFloat("damage", this.damage);
      }

      public void readEntityFromNBT(NBTTagCompound compound) {
         super.readEntityFromNBT(compound);
         if (compound.hasKey("damage")) {
            this.damage = compound.getFloat("damage");
         }
      }

      @SideOnly(Side.CLIENT)
      public void handleStatusUpdate(byte id) {
         if (id == 8) {
            for (int ss = 0; ss < 8; ss++) {
               GUNParticle bigsmoke = new GUNParticle(
                  cloud,
                  0.8F + (float)this.rand.nextGaussian() / 8.0F,
                  -0.005F,
                  20 + this.rand.nextInt(10),
                  160,
                  this.world,
                  this.posX,
                  this.posY,
                  this.posZ,
                  (float)this.rand.nextGaussian() / 16.0F,
                  (float)this.rand.nextGaussian() / 16.0F,
                  (float)this.rand.nextGaussian() / 16.0F,
                  0.25F + this.rand.nextFloat() / 10.0F,
                  1.0F,
                  0.3F,
                  true,
                  this.rand.nextInt(360)
               );
               bigsmoke.alphaGlowing = true;
               bigsmoke.alphaTickAdding = -0.028F;
               this.world.spawnEntity(bigsmoke);
            }
         }
      }

      protected void onImpact(RayTraceResult result) {
         if (!this.world.isRemote && result.hitVec != null && this.getThrower() != null) {
            if (result.entityHit != null) {
               if (Team.checkIsOpponent(this.thrower, result.entityHit)) {
                  this.expl(result);
               }
            } else if (this.world
                  .getBlockState(result.getBlockPos())
                  .getBlock()
                  .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
               != null) {
               this.expl(result);
            }
         }
      }

      public void expl(RayTraceResult result) {
         double damageRadius = 1.8;
         AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
            .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
            .offset(-damageRadius, -damageRadius, -damageRadius);
         List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this.thrower, axisalignedbb);
         if (!list.isEmpty()) {
            for (Entity entity : list) {
               if (Team.checkIsOpponent(this.getThrower(), entity)) {
                  Weapons.dealDamage(
                     new WeaponDamage(null, this.getThrower(), this, false, true, this, WeaponDamage.toxin),
                     (float)this.getThrower().getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue(),
                     this.getThrower(),
                     entity,
                     false
                  );
                  Weapons.setPotionIfEntityLB(entity, MobEffects.POISON, 120, 0);
                  entity.hurtResistantTime = 0;
               }
            }
         }

         this.world
            .playSound(
               (EntityPlayer)null,
               this.posX,
               this.posY,
               this.posZ,
               Sounds.ichorhit,
               SoundCategory.AMBIENT,
               0.6F,
               0.9F + this.rand.nextFloat() / 5.0F
            );
         this.world.setEntityState(this, (byte)8);
         this.setDead();
      }
   }

   public static class Rocket extends EntityThrowable {
      public static ResourceLocation largesmoke = new ResourceLocation("arpg:textures/largesmoke.png");
      public static ResourceLocation flame = new ResourceLocation("arpg:textures/flame_big.png");
      public float damage = 13.0F;
      public float damageRadius = 1.7F;

      public Rocket(World world) {
         super(world);
      }

      public Rocket(World world, EntityLivingBase thrower) {
         super(world, thrower);
      }

      public Rocket(World world, double x, double y, double z) {
         super(world, x, y, z);
      }

      public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
         float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
         float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
         float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
         this.shoot(f, f1, f2, velocity, inaccuracy);
         this.motionX = this.motionX + entityThrower.motionX * 0.8;
         this.motionZ = this.motionZ + entityThrower.motionZ * 0.8;
         if (!entityThrower.onGround) {
            this.motionY = this.motionY + entityThrower.motionY * 0.8;
         }
      }

      protected float getGravityVelocity() {
         return 0.0F;
      }

      public void onUpdate() {
         super.onUpdate();
         if (this.ticksExisted > 50) {
            this.setDead();
         }

         this.world.setEntityState(this, (byte)5);
      }

      @SideOnly(Side.CLIENT)
      public void handleStatusUpdate(byte id) {
         if (id == 8) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.explode,
                  SoundCategory.HOSTILE,
                  1.2F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );

            for (int ss = 0; ss < 7; ss++) {
               GUNParticle bigsmoke = new GUNParticle(
                  largesmoke,
                  0.5F + this.rand.nextFloat() / 2.0F,
                  -0.001F,
                  40,
                  60,
                  this.world,
                  this.posX,
                  this.posY,
                  this.posZ,
                  (float)this.rand.nextGaussian() / 20.0F,
                  (float)this.rand.nextGaussian() / 25.0F,
                  (float)this.rand.nextGaussian() / 20.0F,
                  1.0F,
                  1.0F,
                  1.0F,
                  true,
                  this.rand.nextInt(360)
               );
               bigsmoke.alphaTickAdding = -0.01F;
               this.world.spawnEntity(bigsmoke);
            }

            float rb = this.rand.nextFloat() / 2.0F + 0.4F;
            float g = 0.7F + (float)this.rand.nextGaussian() / 4.0F;

            for (int ss = 0; ss < 10; ss++) {
               GUNParticle fire = new GUNParticle(
                  flame,
                  0.5F + (float)this.rand.nextGaussian() / 3.0F,
                  -0.003F,
                  23 + this.rand.nextInt(15),
                  240,
                  this.world,
                  this.posX,
                  this.posY,
                  this.posZ,
                  (float)this.rand.nextGaussian() / 13.0F,
                  (float)this.rand.nextGaussian() / 13.0F,
                  (float)this.rand.nextGaussian() / 13.0F,
                  rb,
                  g,
                  rb + (float)this.rand.nextGaussian() / 7.0F,
                  true,
                  this.rand.nextInt(100) - 50
               );
               fire.alphaTickAdding = -0.026F;
               fire.alphaGlowing = true;
               fire.scaleTickAdding = -0.015F;
               this.world.spawnEntity(fire);
            }
         }

         if (id == 5) {
            GUNParticle fire2 = new GUNParticle(
               flame,
               0.25F,
               0.0F,
               15,
               240,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               0.0F,
               0.0F,
               0.0F,
               1.0F,
               0.8F + this.rand.nextFloat() / 5.0F,
               0.8F + this.rand.nextFloat() / 5.0F,
               true,
               this.rand.nextInt(360)
            );
            fire2.alphaTickAdding = -0.06F;
            fire2.alphaGlowing = true;
            this.world.spawnEntity(fire2);
         }
      }

      protected void onImpact(RayTraceResult result) {
         if (result.entityHit != null) {
            if (Team.checkIsOpponent(this.thrower, result.entityHit)) {
               this.expl();
            }
         } else if (this.world
               .getBlockState(result.getBlockPos())
               .getBlock()
               .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
            != null) {
            this.expl();
         }
      }

      public void expl() {
         AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
            .expand(this.damageRadius * 2.0F, this.damageRadius * 2.0F, this.damageRadius * 2.0F)
            .offset(-this.damageRadius, -this.damageRadius, -this.damageRadius);
         if (!this.world.isRemote) {
            List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
            if (!list.isEmpty()) {
               for (EntityLivingBase entitylivingbase : list) {
                  if (Team.checkIsOpponent(this.thrower, entitylivingbase)) {
                     Weapons.dealDamage(
                        new WeaponDamage(null, this.getThrower(), this, true, false, this, WeaponDamage.explode),
                        this.damage,
                        this.getThrower(),
                        entitylivingbase,
                        true,
                        1.5F,
                        this.posX,
                        this.posY,
                        this.posZ
                     );
                     DeathEffects.applyDeathEffect(entitylivingbase, DeathEffects.DE_DISMEMBER, 0.55F);
                  } else {
                     Weapons.dealDamage(
                        new WeaponDamage(null, this.getThrower(), this, true, false, this, WeaponDamage.explode),
                        this.damage * 0.5F,
                        this.getThrower(),
                        entitylivingbase,
                        true,
                        1.0F,
                        this.posX,
                        this.posY,
                        this.posZ
                     );
                     DeathEffects.applyDeathEffect(entitylivingbase, DeathEffects.DE_DISMEMBER, 0.55F);
                  }
               }
            }

            if (this.world.isAreaLoaded(this.getPosition().add(-16, -16, -16), this.getPosition().add(16, 16, 16))) {
               for (BlockPos blockpos : GetMOP.getPosesInsideSphere((int)this.posX, (int)this.posY, (int)this.posZ, 3)) {
                  IBlockState state = this.world.getBlockState(blockpos);
                  if (Weapons.easyBreakBlockFor(this.world, 4.0F, blockpos, state)) {
                     if (!state.getBlock().hasTileEntity(state) && !(this.rand.nextFloat() < 0.5F)) {
                        EntityFallingBlock entityfallingblock = new EntityFallingBlock(
                           this.world, blockpos.getX() + 0.5, blockpos.getY() + 0.2, blockpos.getZ() + 0.5, state
                        );
                        this.world.spawnEntity(entityfallingblock);
                        SuperKnockback.applyKnockback(entityfallingblock, 1.5F, this.posX, this.posY - 0.2, this.posZ);
                        entityfallingblock.velocityChanged = true;
                     } else {
                        this.world.destroyBlock(blockpos, true);
                     }
                  }
               }
            }

            this.world.setEntityState(this, (byte)8);
            this.setDead();
         }
      }
   }

   public static class SeaBomb extends EntityThrowable implements IRenderOptions {
      public static ResourceLocation sparkle4 = new ResourceLocation("arpg:textures/sparkle4.png");
      public static ResourceLocation blob_explode = new ResourceLocation("arpg:textures/blob_explode.png");
      public float damage = 0.0F;
      public boolean stopped = false;
      public double stopPosX;
      public double stopPosY;
      public double stopPosZ;
      public int explodeTimeOffset = 0;
      public float damageRadius = 1.9F;
      public boolean dropBlocks = true;

      public SeaBomb(World world) {
         super(world);
      }

      public SeaBomb(World world, EntityLivingBase thrower) {
         super(world, thrower);
      }

      public SeaBomb(World world, double x, double y, double z) {
         super(world, x, y, z);
      }

      public void setThrower(EntityLivingBase thrower) {
         this.thrower = thrower;
      }

      public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
         float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
         float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
         float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
         this.shoot(f, f1, f2, velocity, inaccuracy);
         this.motionX = this.motionX + entityThrower.motionX * 0.6;
         this.motionZ = this.motionZ + entityThrower.motionZ * 0.6;
         if (!entityThrower.onGround) {
            this.motionY = this.motionY + entityThrower.motionY * 0.4;
         }
      }

      public void writeEntityToNBT(NBTTagCompound compound) {
         super.writeEntityToNBT(compound);
         compound.setFloat("damage", this.damage);
      }

      public void readEntityFromNBT(NBTTagCompound compound) {
         super.readEntityFromNBT(compound);
         if (compound.hasKey("damage")) {
            this.damage = compound.getFloat("damage");
         }
      }

      protected float getGravityVelocity() {
         return this.stopped ? 0.0F : (this.isInWater() ? 0.003F : 0.036F);
      }

      public void onUpdate() {
         super.onUpdate();
         if (this.ticksExisted - this.explodeTimeOffset > 80) {
            explodeSeabomb(
               this.world, this.damageRadius, 1, this.rand, GetMOP.entityCenterPos(this), this.thrower, this.damage, this.dropBlocks
            );
            if (!this.world.isRemote) {
               this.world.setEntityState(this, (byte)8);
               this.setDead();
            }
         }

         if (this.ticksExisted - this.explodeTimeOffset > 40 && this.ticksExisted % 6 == 0) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.landmine_trigger,
                  SoundCategory.HOSTILE,
                  0.5F,
                  0.95F + this.rand.nextFloat() / 10.0F,
                  false
               );
         }

         if (this.stopped) {
            this.motionX = 0.0;
            this.motionY = 0.0;
            this.motionZ = 0.0;
            if (!this.world.isRemote) {
               this.setPosition(this.stopPosX, this.stopPosY, this.stopPosZ);
            }
         }
      }

      @SideOnly(Side.CLIENT)
      public void handleStatusUpdate(byte id) {
         if (id == 8) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.explode3,
                  SoundCategory.HOSTILE,
                  1.2F,
                  1.1F + this.rand.nextFloat() / 5.0F,
                  false
               );
            spawnSeabombParticles(this.world, this.rand, this.posX, this.posY, this.posZ);
         }

         if (id == 7) {
            this.stopped = true;
         }
      }

      public static void spawnSeabombParticles(World world, Random rand, double x, double y, double z) {
         for (int ss = 0; ss < 6; ss++) {
            float fsize = (float)(1.3 + rand.nextGaussian() / 10.0);
            int lt = 5 + rand.nextInt(5);
            GUNParticle part = new GUNParticle(
               sparkle4,
               0.3F,
               0.0F,
               lt,
               220,
               world,
               x + rand.nextGaussian() / 3.0,
               y + rand.nextGaussian() / 3.0,
               z + rand.nextGaussian() / 3.0,
               (float)rand.nextGaussian() / 8.0F,
               (float)rand.nextGaussian() / 8.0F,
               (float)rand.nextGaussian() / 8.0F,
               0.6F,
               0.08F,
               0.44F,
               true,
               rand.nextInt(360)
            );
            part.scaleTickAdding = fsize / lt;
            part.alphaGlowing = true;
            part.alphaTickAdding = -0.6F / lt;
            part.randomDeath = false;
            world.spawnEntity(part);
         }

         for (int ss = 0; ss < 3; ss++) {
            float fsize = (float)(1.8 + rand.nextGaussian() / 8.0);
            int lt = 4 + rand.nextInt(4);
            GUNParticle part = new GUNParticle(blob_explode, 0.3F, 0.0F, lt, 220, world, x, y, z, 0.0F, 0.0F, 0.0F, 0.6F, 0.08F, 0.44F, true, rand.nextInt(360));
            part.scaleTickAdding = fsize / lt;
            part.alphaGlowing = true;
            part.alphaTickAdding = -0.6F / lt;
            part.randomDeath = false;
            if (ss > 0) {
               part.rotationPitchYaw = new Vec2f(rand.nextInt(360), rand.nextInt(360));
            }

            world.spawnEntity(part);
         }
      }

      protected void onImpact(RayTraceResult result) {
         if (result.entityHit == null
            && result.getBlockPos() != null
            && !this.stopped
            && this.world
                  .getBlockState(result.getBlockPos())
                  .getBlock()
                  .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
               != null) {
            this.stopped = true;
            if (result.hitVec != null) {
               this.stopPosX = result.hitVec.x;
               this.stopPosY = result.hitVec.y;
               this.stopPosZ = result.hitVec.z;
            } else {
               this.stopPosX = this.lastTickPosX;
               this.stopPosY = this.lastTickPosY;
               this.stopPosZ = this.lastTickPosZ;
            }

            this.world.setEntityState(this, (byte)7);
         }
      }

      public static void explodeSeabomb(
         World world, double damageRadius, int minBlockDestroyRadius, Random rand, Vec3d pos, EntityLivingBase thrower, float damage, boolean dropBlocks
      ) {
         if (!world.isRemote) {
            AxisAlignedBB axisalignedbb = new AxisAlignedBB(
               pos.x - damageRadius,
               pos.y - damageRadius,
               pos.z - damageRadius,
               pos.x + damageRadius,
               pos.y + damageRadius,
               pos.z + damageRadius
            );
            List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(thrower, axisalignedbb);
            if (!list.isEmpty()) {
               for (Entity entity : list) {
                  if (Team.checkIsOpponent(thrower, entity)) {
                     Weapons.dealDamage(
                        new WeaponDamage(null, thrower, null, true, false, pos, WeaponDamage.explode),
                        damage,
                        thrower,
                        entity,
                        true,
                        1.2F,
                        pos.x,
                        pos.y,
                        pos.z
                     );
                     Weapons.mixPotion(
                        entity,
                        PotionEffects.BROKEN_ARMOR,
                        800.0F,
                        1.0F,
                        Weapons.EnumPotionMix.WITH_MAXIMUM,
                        Weapons.EnumPotionMix.WITH_MAXIMUM,
                        Weapons.EnumMathOperation.PLUS,
                        Weapons.EnumMathOperation.PLUS,
                        1000,
                        3
                     );
                     entity.hurtResistantTime = 0;
                  }
               }
            }

            BlockPos startPos = new BlockPos(pos.x, pos.y + 0.5, pos.z);
            if (world.isAreaLoaded(startPos.add(-16, -16, -16), startPos.add(16, 16, 16))) {
               for (BlockPos blockpos : GetMOP.getPosesInsideSphere(
                  (int)pos.x, (int)pos.y, (int)pos.z, minBlockDestroyRadius + rand.nextInt(2)
               )) {
                  if (rand.nextFloat() < 0.5F) {
                     IBlockState state = world.getBlockState(blockpos);
                     if (Weapons.easyBreakBlockFor(world, 20.0F, blockpos, state)) {
                        world.destroyBlock(blockpos, dropBlocks);
                     }
                  }
               }
            }
         }
      }

      @Override
      public void doOptions() {
         if (this.ticksExisted <= 40 || this.ticksExisted % 6 != 0 && this.ticksExisted % 6 != 1 && this.ticksExisted % 6 != 2) {
            GlStateManager.color(1.0F, 1.0F, 1.0F);
         } else {
            GlStateManager.color(0.8F, 0.08F, 0.54F);
         }
      }

      @Override
      public void undoOptions() {
         GlStateManager.color(1.0F, 1.0F, 1.0F);
      }
   }

   public static class SirenShoot extends EntityThrowable {
      public static ResourceLocation tex = new ResourceLocation("arpg:textures/siren_shoot.png");
      public static ResourceLocation tex2 = new ResourceLocation("arpg:textures/simple_magic_shoot.png");
      public float damage = 0.0F;

      public SirenShoot(World world) {
         super(world);
         this.setSize(0.25F, 0.25F);
      }

      public SirenShoot(World world, EntityLivingBase thrower) {
         super(world, thrower);
         this.setSize(0.25F, 0.25F);
      }

      public SirenShoot(World world, double x, double y, double z) {
         super(world, x, y, z);
         this.setSize(0.25F, 0.25F);
      }

      protected float getGravityVelocity() {
         return 0.0F;
      }

      public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
         float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
         float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
         float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
         this.shoot(f, f1, f2, velocity, inaccuracy);
      }

      public void onUpdate() {
         super.onUpdate();
         if (this.ticksExisted > 80) {
            this.setDead();
         }
      }

      public void writeEntityToNBT(NBTTagCompound compound) {
         super.writeEntityToNBT(compound);
         compound.setFloat("damage", this.damage);
      }

      public void readEntityFromNBT(NBTTagCompound compound) {
         super.readEntityFromNBT(compound);
         if (compound.hasKey("damage")) {
            this.damage = compound.getFloat("damage");
         }
      }

      @SideOnly(Side.CLIENT)
      public void handleStatusUpdate(byte id) {
         if (id == 8) {
            for (int ss = 0; ss < 8; ss++) {
               GUNParticle spell = new GUNParticle(
                  tex,
                  0.06F + this.rand.nextFloat() * 0.02F,
                  0.025F,
                  50,
                  240,
                  this.world,
                  this.posX,
                  this.posY,
                  this.posZ,
                  (float)this.rand.nextGaussian() / 16.0F,
                  (float)this.rand.nextGaussian() / 16.0F + 0.1F,
                  (float)this.rand.nextGaussian() / 16.0F,
                  1.0F,
                  1.0F,
                  1.0F,
                  true,
                  0
               );
               spell.scaleTickAdding = -0.0012F;
               this.world.spawnEntity(spell);
            }

            for (int ss = 0; ss < 4; ss++) {
               GUNParticle spell = new GUNParticle(
                  tex2,
                  0.08F + this.rand.nextFloat() * 0.03F,
                  0.005F,
                  20,
                  240,
                  this.world,
                  this.posX,
                  this.posY,
                  this.posZ,
                  (float)this.rand.nextGaussian() / 16.0F,
                  (float)this.rand.nextGaussian() / 16.0F,
                  (float)this.rand.nextGaussian() / 16.0F,
                  0.0F,
                  0.5F + this.rand.nextFloat() * 0.2F,
                  1.0F,
                  true,
                  0
               );
               spell.alphaGlowing = true;
               spell.scaleTickAdding = -0.004F;
               this.world.spawnEntity(spell);
            }

            for (int ss = 0; ss < 4; ss++) {
               GUNParticle spell = new GUNParticle(
                  tex2,
                  0.08F + this.rand.nextFloat() * 0.03F,
                  0.005F,
                  20,
                  240,
                  this.world,
                  this.posX,
                  this.posY,
                  this.posZ,
                  (float)this.rand.nextGaussian() / 16.0F,
                  (float)this.rand.nextGaussian() / 16.0F,
                  (float)this.rand.nextGaussian() / 16.0F,
                  1.0F,
                  0.8F + this.rand.nextFloat() * 0.2F,
                  0.2F,
                  true,
                  0
               );
               spell.alphaGlowing = true;
               spell.scaleTickAdding = -0.004F;
               this.world.spawnEntity(spell);
            }
         }
      }

      protected void onImpact(RayTraceResult result) {
         if (!this.world.isRemote) {
            if (result.entityHit != null) {
               if (Team.checkIsOpponent(this.thrower, result.entityHit)) {
                  Weapons.dealDamage(
                     new WeaponDamage(null, this.getThrower(), this, false, true, this, WeaponDamage.magic),
                     this.damage,
                     this.thrower,
                     result.entityHit,
                     true,
                     1.0F,
                     this.thrower.posX,
                     this.thrower.posY,
                     this.thrower.posZ
                  );
                  result.entityHit.hurtResistantTime = 0;
                  this.world
                     .playSound(
                        (EntityPlayer)null,
                        this.posX,
                        this.posY,
                        this.posZ,
                        Sounds.cryo_gun_impact,
                        SoundCategory.AMBIENT,
                        0.9F,
                        1.0F + this.rand.nextFloat() / 5.0F
                     );
                  this.world.setEntityState(this, (byte)8);
                  this.setDead();
               }
            } else if (this.world
                  .getBlockState(result.getBlockPos())
                  .getBlock()
                  .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
               != null) {
               this.world
                  .playSound(
                     (EntityPlayer)null,
                     this.posX,
                     this.posY,
                     this.posZ,
                     Sounds.cryo_gun_impact,
                     SoundCategory.AMBIENT,
                     0.9F,
                     1.0F + this.rand.nextFloat() / 5.0F
                  );
               this.world.setEntityState(this, (byte)8);
               this.setDead();
            }
         }
      }
   }

   public static class SkyGuardShield extends EntityThrowable {
      public static ResourceLocation circle = new ResourceLocation("arpg:textures/circle.png");
      public static float maxdamage = 60.0F;
      public float shieldHP = 60.0F;
      public int displace = 0;
      public int shieldTick = 0;

      public SkyGuardShield(World world) {
         super(world);
         this.setSize(0.55F, 1.1F);
      }

      public SkyGuardShield(World world, EntityLivingBase thrower) {
         super(world, thrower);
         this.setSize(0.55F, 1.1F);
      }

      public SkyGuardShield(World world, double x, double y, double z) {
         super(world, x, y, z);
         this.setSize(0.55F, 1.1F);
      }

      public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
         float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
         float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
         float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
         this.shoot(f, f1, f2, velocity, inaccuracy);
         this.motionX = this.motionX + entityThrower.motionX * 0.1;
         this.motionZ = this.motionZ + entityThrower.motionZ * 0.1;
         if (!entityThrower.onGround) {
            this.motionY = this.motionY + entityThrower.motionY * 0.1;
         }
      }

      public double getYOffset() {
         return 0.8;
      }

      protected float getGravityVelocity() {
         return 0.0F;
      }

      public void onEntityUpdate() {
         float RP = 0.0F;
         float RY = (float)this.shieldTick + this.displace;
         this.rotationPitch = RP;
         this.rotationYaw = RY;
         super.onEntityUpdate();
         this.rotationPitch = RP;
         this.rotationYaw = RY;
      }

      public void onUpdate() {
         float RP = 0.0F;
         float RY = (float)this.shieldTick + this.displace;
         this.shieldTick += 6;
         super.onUpdate();
         if (!this.world.isRemote) {
            if (this.ticksExisted % 5 == 0) {
               EntityLivingBase th = this.getThrower();
               if (th == null || th.isDead) {
                  this.setDead();
               }

               if (this.shieldHP < maxdamage) {
                  this.shieldHP += 2.0F;
               }
            }

            if (this.ticksExisted % 40 == 0 || this.ticksExisted <= 1) {
               if (this.displace == 120) {
                  this.world.setEntityState(this, (byte)8);
               }

               if (this.displace == 240) {
                  this.world.setEntityState(this, (byte)9);
               }
            }

            if (this.shieldTick % 360 == 0 || this.shieldTick == 0) {
               this.world.setEntityState(this, (byte)10);
               this.shieldTick = 0;
            }
         }

         double movex = this.posX;
         double movey = this.posY;
         double movez = this.posZ;
         if (this.thrower != null) {
            double radius = 1.5;
            if (this.thrower instanceof EntityLiving
               && ((EntityLiving)this.thrower).getAttackTarget() != null
               && !((EntityLiving)this.thrower).getEntitySenses().canSee(((EntityLiving)this.thrower).getAttackTarget())) {
               radius = 0.7;
            }

            movex = this.thrower.posX + radius * Math.sin(((double)this.shieldTick + this.displace) * (float) (Math.PI / 180.0));
            movey = this.thrower.posY - 0.2;
            movez = this.thrower.posZ + radius * Math.cos(((double)this.shieldTick + this.displace) * (float) (Math.PI / 180.0));
         }

         this.motionX /= 3.0;
         this.motionY /= 3.0;
         this.motionZ /= 3.0;
         float kb = (float)this.getDistance(movex, movey, movez) / 2.0F;
         SuperKnockback.applyMove(this, (float)(-Math.min((double)kb, 0.4)), movex, movey, movez);
      }

      public void writeEntityToNBT(NBTTagCompound compound) {
         super.writeEntityToNBT(compound);
         compound.setFloat("shieldhp", this.shieldHP);
      }

      public void readEntityFromNBT(NBTTagCompound compound) {
         super.readEntityFromNBT(compound);
         if (compound.hasKey("shieldhp")) {
            this.shieldHP = compound.getFloat("shieldhp");
         }
      }

      @SideOnly(Side.CLIENT)
      public void handleStatusUpdate(byte id) {
         if (id == 8) {
            this.displace = 120;
         }

         if (id == 9) {
            this.displace = 240;
         }

         if (id == 10) {
            this.shieldTick = 0;
         }
      }

      public boolean attackEntityFrom(DamageSource source, float amount) {
         this.shieldHP -= amount;
         return super.attackEntityFrom(source, amount);
      }

      public boolean canBeCollidedWith() {
         return this.shieldHP > 0.0F;
      }

      public float getCollisionBorderSize() {
         return 0.2F;
      }

      public boolean handleWaterMovement() {
         return false;
      }

      protected void onImpact(RayTraceResult result) {
      }
   }

   public static class SkyGuardShoot extends EntityThrowable implements IRenderOptions {
      public static ResourceLocation circle = new ResourceLocation("arpg:textures/circle.png");
      public float damage = 0.0F;

      public SkyGuardShoot(World world) {
         super(world);
         this.setSize(0.25F, 0.25F);
      }

      public SkyGuardShoot(World world, EntityLivingBase thrower) {
         super(world, thrower);
         this.setSize(0.25F, 0.25F);
      }

      public SkyGuardShoot(World world, double x, double y, double z) {
         super(world, x, y, z);
         this.setSize(0.25F, 0.25F);
      }

      public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
         float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
         float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
         float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
         this.shoot(f, f1, f2, velocity, inaccuracy);
         this.motionX = this.motionX + entityThrower.motionX * 0.1;
         this.motionZ = this.motionZ + entityThrower.motionZ * 0.1;
         if (!entityThrower.onGround) {
            this.motionY = this.motionY + entityThrower.motionY * 0.1;
         }
      }

      protected float getGravityVelocity() {
         return 0.0F;
      }

      public void onUpdate() {
         super.onUpdate();
         if (this.ticksExisted > 50) {
            this.setDead();
         }
      }

      public void writeEntityToNBT(NBTTagCompound compound) {
         super.writeEntityToNBT(compound);
         compound.setFloat("damage", this.damage);
      }

      public void readEntityFromNBT(NBTTagCompound compound) {
         super.readEntityFromNBT(compound);
         if (compound.hasKey("damage")) {
            this.damage = compound.getFloat("damage");
         }
      }

      @SideOnly(Side.CLIENT)
      public void handleStatusUpdate(byte id) {
         if (id == 8) {
            GUNParticle sp = new GUNParticle(
               circle,
               0.1F + this.rand.nextFloat() / 4.0F,
               0.0F,
               4,
               240,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               0.0F,
               0.0F,
               0.0F,
               0.7F,
               1.0F - this.rand.nextFloat() / 6.0F,
               1.0F,
               true,
               this.rand.nextInt(360)
            );
            sp.alphaGlowing = true;
            sp.randomDeath = false;
            sp.tracker = EntityElectricBolt.trackerssh;
            this.world.spawnEntity(sp);
         }
      }

      protected void onImpact(RayTraceResult result) {
         if (result.entityHit != null) {
            if (Team.checkIsOpponent(this.thrower, result.entityHit) && !this.world.isRemote) {
               Weapons.dealDamage(
                  new WeaponDamage(null, this.getThrower(), this, false, true, this, WeaponDamage.plasma),
                  this.damage,
                  this.getThrower(),
                  result.entityHit,
                  true,
                  1.0F
               );
               this.world
                  .playSound(
                     (EntityPlayer)null,
                     this.posX,
                     this.posY,
                     this.posZ,
                     Sounds.plasma_rifle_impact,
                     SoundCategory.AMBIENT,
                     0.4F,
                     1.4F + this.rand.nextFloat() / 5.0F
                  );
               this.world.setEntityState(this, (byte)8);
               this.setDead();
            }
         } else if (this.world
               .getBlockState(result.getBlockPos())
               .getBlock()
               .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
            != null) {
            this.world
               .playSound(
                  (EntityPlayer)null,
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.plasma_rifle_impact,
                  SoundCategory.AMBIENT,
                  0.4F,
                  1.4F + this.rand.nextFloat() / 5.0F
               );
            if (!this.world.isRemote) {
               this.world.setEntityState(this, (byte)8);
               this.setDead();
            }
         }
      }

      @Override
      public void doOptions() {
         GlStateManager.enableBlend();
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
      }

      @Override
      public void undoOptions() {
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         GlStateManager.disableBlend();
      }
   }

   public static class SlimeCloud extends EntityThrowable {
      public static ResourceLocation spl = new ResourceLocation("arpg:textures/acid_splash5.png");
      public float damage = 0.0F;

      public SlimeCloud(World world) {
         super(world);
         this.setSize(3.8F, 1.5F);
      }

      public SlimeCloud(World world, EntityLivingBase thrower) {
         super(world, thrower);
         this.setSize(3.8F, 1.5F);
      }

      public SlimeCloud(World world, double x, double y, double z) {
         super(world, x, y, z);
         this.setSize(3.8F, 1.5F);
      }

      public boolean isInRangeToRenderDist(double distance) {
         return false;
      }

      protected float getGravityVelocity() {
         return 0.0F;
      }

      @Nullable
      public AxisAlignedBB getCollisionBox(Entity entityIn) {
         return entityIn.getEntityBoundingBox();
      }

      public boolean isPushedByWater() {
         return false;
      }

      public boolean canBePushed() {
         return false;
      }

      public void onUpdate() {
         super.onUpdate();
         if (!this.world.isRemote) {
            if (this.ticksExisted > 160) {
               this.setDead();
            }

            if (this.ticksExisted % 2 == 0) {
               List<Entity> list = this.world.getEntitiesWithinAABB(Entity.class, this.getEntityBoundingBox());
               if (!list.isEmpty()) {
                  for (Entity entityl : list) {
                     if (Team.checkIsOpponent(this.thrower, entityl)) {
                        entityl.motionX /= 2.0;
                        entityl.motionY /= 2.0;
                        entityl.motionZ /= 2.0;
                        if (entityl instanceof EntityLivingBase) {
                           EntityLivingBase entitylivingbase = (EntityLivingBase)entityl;
                           Weapons.dealDamage(
                              new WeaponDamage(
                                 null, this.getThrower(), this, false, false, this.getPositionVector().add(0.0, -2.0, 0.0), WeaponDamage.toxin
                              ),
                              this.damage,
                              this.getThrower(),
                              entitylivingbase,
                              true
                           );
                           entitylivingbase.addPotionEffect(new PotionEffect(PotionEffects.SLIME, 430, 1));
                           entitylivingbase.addPotionEffect(new PotionEffect(PotionEffects.TOXIN, 40 + this.world.getDifficulty().getId() * 20));
                        }
                     }
                  }
               }
            }
         }
      }

      public void writeEntityToNBT(NBTTagCompound compound) {
         super.writeEntityToNBT(compound);
         compound.setFloat("damage", this.damage);
      }

      public void readEntityFromNBT(NBTTagCompound compound) {
         super.readEntityFromNBT(compound);
         if (compound.hasKey("damage")) {
            this.damage = compound.getFloat("damage");
         }
      }

      protected void onImpact(RayTraceResult result) {
      }

      public void onEntityUpdate() {
         if (this.world.isRemote && this.ticksExisted % 2 == 0) {
            float scalen = this.rand.nextFloat() * 0.5F + 0.36F;
            GUNParticle part = new GUNParticle(
               spl,
               0.1F,
               -7.0E-4F,
               30,
               150,
               this.world,
               this.posX + this.rand.nextGaussian(),
               this.posY + this.rand.nextFloat() / 2.0F,
               this.posZ + this.rand.nextGaussian(),
               0.0F,
               0.0F,
               0.0F,
               1.0F - this.rand.nextFloat() * 0.3F,
               1.0F - this.rand.nextFloat() * 0.3F,
               1.0F - this.rand.nextFloat() * 0.3F,
               false,
               this.rand.nextInt(21) - 20
            );
            part.scaleTickAdding = scalen / 50.0F;
            this.world.spawnEntity(part);
         }
      }
   }

   public static class ThunderbirdShoot extends EntityThrowable {
      static ResourceLocation trail = new ResourceLocation("arpg:textures/ghostly_shoot.png");
      static ResourceLocation largesmoke = new ResourceLocation("arpg:textures/largesmoke.png");
      static ResourceLocation flame = new ResourceLocation("arpg:textures/plasma_cloud.png");
      static ResourceLocation electr = new ResourceLocation("arpg:textures/blueexplode5.png");
      static ResourceLocation sparkle = new ResourceLocation("arpg:textures/sparkle2.png");
      public float damage = 0.0F;

      public ThunderbirdShoot(World world) {
         super(world);
         this.setSize(0.32F, 0.32F);
      }

      public ThunderbirdShoot(World world, EntityLivingBase thrower) {
         super(world, thrower);
         this.setSize(0.32F, 0.32F);
      }

      public ThunderbirdShoot(World world, double x, double y, double z) {
         super(world, x, y, z);
         this.setSize(0.32F, 0.32F);
      }

      public void writeEntityToNBT(NBTTagCompound compound) {
         super.writeEntityToNBT(compound);
         compound.setFloat("damage", this.damage);
      }

      public void readEntityFromNBT(NBTTagCompound compound) {
         super.readEntityFromNBT(compound);
         if (compound.hasKey("damage")) {
            this.damage = compound.getFloat("damage");
         }
      }

      public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
         float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
         float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
         float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
         this.shoot(f, f1, f2, velocity, inaccuracy);
         this.motionX = this.motionX + entityThrower.motionX * 0.2;
         this.motionZ = this.motionZ + entityThrower.motionZ * 0.2;
         if (!entityThrower.onGround) {
            this.motionY = this.motionY + entityThrower.motionY * 0.2;
         }
      }

      protected float getGravityVelocity() {
         return 0.0F;
      }

      public void onUpdate() {
         super.onUpdate();
         if (this.ticksExisted > 40) {
            this.setDead();
         }

         this.world.setEntityState(this, (byte)5);
      }

      @SideOnly(Side.CLIENT)
      public void handleStatusUpdate(byte id) {
         if (id == 8) {
            if (Minecraft.getMinecraft().getRenderViewEntity() != null) {
               float distr = Minecraft.getMinecraft().getRenderViewEntity().getDistance(this);
               if (distr < 14.0F) {
                  Booom.lastTick = Math.round(18.0F - distr);
                  Booom.frequency = 4.0F;
                  Booom.x = (float)this.rand.nextGaussian();
                  Booom.y = (float)this.rand.nextGaussian();
                  Booom.z = (float)this.rand.nextGaussian();
                  Booom.power = 0.4F;
               }
            }

            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.stormledge_explode_a,
                  SoundCategory.HOSTILE,
                  2.0F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );

            for (int ss = 0; ss < 8; ss++) {
               GUNParticle bigsmoke = new GUNParticle(
                  largesmoke,
                  0.5F + this.rand.nextFloat() / 2.0F,
                  -0.001F,
                  40,
                  60,
                  this.world,
                  this.posX,
                  this.posY,
                  this.posZ,
                  (float)this.rand.nextGaussian() / 20.0F,
                  (float)this.rand.nextGaussian() / 25.0F,
                  (float)this.rand.nextGaussian() / 20.0F,
                  1.0F,
                  1.0F,
                  1.0F,
                  true,
                  this.rand.nextInt(360)
               );
               bigsmoke.alphaTickAdding = -0.01F;
               this.world.spawnEntity(bigsmoke);
            }

            for (int ss = 0; ss < 20; ss++) {
               GUNParticle fire = new GUNParticle(
                  flame,
                  0.7F + (float)this.rand.nextGaussian() / 3.0F,
                  -0.003F,
                  23 + this.rand.nextInt(15),
                  240,
                  this.world,
                  this.posX,
                  this.posY,
                  this.posZ,
                  (float)this.rand.nextGaussian() / 11.0F,
                  (float)this.rand.nextGaussian() / 11.0F,
                  (float)this.rand.nextGaussian() / 11.0F,
                  0.6F,
                  0.9F,
                  1.0F - this.rand.nextFloat() / 5.0F,
                  true,
                  this.rand.nextInt(100) - 50
               );
               fire.alphaTickAdding = -0.026F;
               fire.alphaGlowing = true;
               fire.scaleTickAdding = -0.015F;
               this.world.spawnEntity(fire);
            }

            for (int ss = 0; ss < 4; ss++) {
               int lt = 10 + this.rand.nextInt(10);
               GUNParticle fire = new GUNParticle(
                  electr,
                  0.4F,
                  0.0F,
                  lt,
                  240,
                  this.world,
                  this.posX,
                  this.posY,
                  this.posZ,
                  0.0F,
                  0.0F,
                  0.0F,
                  1.0F,
                  1.0F,
                  1.0F - this.rand.nextFloat() / 5.0F,
                  true,
                  this.rand.nextInt(360)
               );
               fire.alphaTickAdding = -1.0F / lt;
               fire.alphaGlowing = true;
               fire.scaleTickAdding = 0.14F + this.rand.nextFloat() / 10.0F;
               fire.rotationPitchYaw = new Vec2f(this.rand.nextInt(360), this.rand.nextInt(360));
               this.world.spawnEntity(fire);
            }

            int countOfParticles = 17;
            float R = 0.1F + (float)this.rand.nextGaussian() / 30.0F;

            for (int i = 0; i < countOfParticles; i++) {
               float rand1 = this.rand.nextFloat() * 2.0F - 1.0F;
               float rand2 = this.rand.nextFloat() * 2.0F - 1.0F;
               float X = rand1 * R;
               float new_R = (float)Math.sqrt(R * R - X * X);
               float Y = rand2 * new_R;
               float Z = (float)Math.sqrt(new_R * new_R - Y * Y);
               if (this.rand.nextBoolean()) {
                  Z *= -1.0F;
               }

               float scale = 0.3F + this.rand.nextFloat() / 7.0F;
               GUNParticle particle = new GUNParticle(
                  sparkle,
                  scale,
                  0.007F,
                  40,
                  240,
                  this.world,
                  this.posX,
                  this.posY,
                  this.posZ,
                  X,
                  Y,
                  Z,
                  0.9F,
                  1.0F,
                  1.0F,
                  true,
                  0
               );
               particle.alphaGlowing = true;
               particle.scaleTickAdding = -scale / 40.0F;
               this.world.spawnEntity(particle);
            }
         }

         if (id == 5) {
            GUNParticle fire2 = new GUNParticle(
               flame,
               0.35F,
               0.0F,
               15,
               240,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               0.0F,
               0.0F,
               0.0F,
               0.8F + this.rand.nextFloat() / 5.0F,
               0.8F + this.rand.nextFloat() / 5.0F,
               1.0F,
               true,
               this.rand.nextInt(360)
            );
            fire2.alphaTickAdding = -0.06F;
            fire2.alphaGlowing = true;
            this.world.spawnEntity(fire2);
         }
      }

      @SideOnly(Side.CLIENT)
      public void onEntityUpdate() {
         Vec3d pos1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
         Vec3d pos2 = this.getPositionVector();
         if (this.world.isRemote && pos1.lengthSquared() > 1.0E-6 && pos2.lengthSquared() > 1.0E-6) {
            BetweenParticle laser = new BetweenParticle(
               this.world, trail, 0.44F, 240, 1.0F, 0.9F, 1.0F, 0.1F, pos1.distanceTo(pos2), 3, -0.15F, 9999.0F, pos1, pos2
            );
            laser.alphaGlowing = true;
            laser.texstart = 0.1F;
            laser.offset = -0.1F;
            laser.setPosition(pos1.x, pos1.y, pos1.z);
            this.world.spawnEntity(laser);
         }
      }

      protected void onImpact(RayTraceResult result) {
         if (result.entityHit != null) {
            if (Team.checkIsOpponent(this.thrower, result.entityHit)) {
               this.expl(result);
            }
         } else if (this.world
               .getBlockState(result.getBlockPos())
               .getBlock()
               .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
            != null) {
            this.expl(result);
         }
      }

      public void expl(RayTraceResult result) {
         double damageRadius = 4.5;
         AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
            .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
            .offset(-damageRadius, -damageRadius, -damageRadius);
         if (!this.world.isRemote && result.hitVec != null) {
            List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
            if (!list.isEmpty()) {
               for (EntityLivingBase entitylivingbase : list) {
                  if (Team.checkIsOpponent(this.thrower, entitylivingbase)) {
                     float distToHitbox = (float)Weapons.getDistanceToMobHitbox(
                           entitylivingbase.width,
                           entitylivingbase.height,
                           entitylivingbase.posX,
                           entitylivingbase.posY,
                           entitylivingbase.posZ,
                           this.posX,
                           this.posY,
                           this.posZ
                        )
                        * 0.7F;
                     float finalDamage = this.damage * Math.max(2.5F - distToHitbox, 0.45F);
                     Weapons.dealDamage(
                        new WeaponDamage(null, this.getThrower(), this, true, false, this, WeaponDamage.explode),
                        finalDamage,
                        this.getThrower(),
                        entitylivingbase,
                        true,
                        3.0F,
                        this.posX,
                        this.posY,
                        this.posZ
                     );
                     entitylivingbase.hurtResistantTime = 0;
                     if (entitylivingbase.getHealth() <= 0.0F && this.rand.nextFloat() < 0.55) {
                        DeathEffects.applyDeathEffect(entitylivingbase, DeathEffects.DE_DISMEMBER);
                     }
                  } else {
                     SuperKnockback.applyKnockback(entitylivingbase, 1.0F, this.posX, this.posY - 1.0, this.posZ);
                  }
               }
            }

            if (this.world.isAreaLoaded(this.getPosition().add(-16, -16, -16), this.getPosition().add(16, 16, 16))) {
               for (BlockPos blockpos : GetMOP.getPosesInsideSphere((int)this.posX, (int)this.posY, (int)this.posZ, 3)) {
                  IBlockState state = this.world.getBlockState(blockpos);
                  if (Weapons.easyBreakBlockFor(this.world, 4.0F, blockpos, state)) {
                     if (!state.getBlock().hasTileEntity(state) && !(this.rand.nextFloat() < 0.6F)) {
                        EntityFallingBlock entityfallingblock = new EntityFallingBlock(
                           this.world, blockpos.getX() + 0.5, blockpos.getY() + 0.2, blockpos.getZ() + 0.5, state
                        );
                        this.world.spawnEntity(entityfallingblock);
                        SuperKnockback.applyKnockback(entityfallingblock, 2.0F, this.posX, this.posY - 0.2, this.posZ);
                        entityfallingblock.velocityChanged = true;
                     } else {
                        this.world.destroyBlock(blockpos, true);
                     }
                  }
               }
            }

            this.world.setEntityState(this, (byte)8);
            this.setDead();
         }
      }
   }

   public static class TrachymonaShoot extends EntityThrowable {
      public static ResourceLocation tex = new ResourceLocation("arpg:textures/redball.png");
      public float damage = 0.0F;
      public int ticksOffset = 0;

      public TrachymonaShoot(World world) {
         super(world);
         this.setSize(0.2F, 0.2F);
      }

      public TrachymonaShoot(World world, EntityLivingBase thrower) {
         super(world, thrower);
         this.setSize(0.2F, 0.2F);
      }

      public TrachymonaShoot(World world, double x, double y, double z) {
         super(world, x, y, z);
         this.setSize(0.2F, 0.2F);
      }

      protected float getGravityVelocity() {
         return 0.0F;
      }

      public void onUpdate() {
         super.onUpdate();
         if (this.ticksExisted > 80) {
            this.setDead();
         }

         int ticks = this.ticksOffset + this.ticksExisted;
         if (!this.world.isRemote
            && ticks > 0
            && ticks < 15
            && this.thrower != null
            && this.thrower instanceof EntityLiving
            && ((EntityLiving)this.thrower).getAttackTarget() != null) {
            EntityLivingBase targ = ((EntityLiving)this.thrower).getAttackTarget();
            SuperKnockback.applyMove(this, -0.1F, targ.posX, targ.posY + targ.height / 2.0F, targ.posZ);
            this.velocityChanged = true;
         }

         this.world.setEntityState(this, (byte)9);
      }

      public boolean handleWaterMovement() {
         return false;
      }

      public void writeEntityToNBT(NBTTagCompound compound) {
         super.writeEntityToNBT(compound);
         compound.setFloat("damage", this.damage);
      }

      public void readEntityFromNBT(NBTTagCompound compound) {
         super.readEntityFromNBT(compound);
         if (compound.hasKey("damage")) {
            this.damage = compound.getFloat("damage");
         }
      }

      @SideOnly(Side.CLIENT)
      public void handleStatusUpdate(byte id) {
         if (id == 8) {
            for (int ss = 0; ss < 6; ss++) {
               GUNParticle spell = new GUNParticle(
                  tex,
                  0.06F,
                  0.02F,
                  30,
                  180,
                  this.world,
                  this.posX,
                  this.posY,
                  this.posZ,
                  (float)this.rand.nextGaussian() / 16.0F,
                  (float)this.rand.nextGaussian() / 16.0F,
                  (float)this.rand.nextGaussian() / 16.0F,
                  0.8F + this.rand.nextFloat() / 5.0F,
                  1.0F,
                  1.0F,
                  false,
                  0
               );
               spell.scaleTickAdding = -0.002F;
               this.world.spawnEntity(spell);
            }
         }
      }

      protected void onImpact(RayTraceResult result) {
         if (result.entityHit != null) {
            if (Team.checkIsOpponent(this.thrower, result.entityHit) && !this.world.isRemote) {
               Weapons.dealDamage(
                  new WeaponDamage(null, this.getThrower(), this, false, true, this, WeaponDamage.bullet),
                  this.damage,
                  this.getThrower(),
                  result.entityHit,
                  true,
                  1.0F
               );
               result.entityHit.hurtResistantTime = 0;
               this.world
                  .playSound(
                     (EntityPlayer)null,
                     this.posX,
                     this.posY,
                     this.posZ,
                     Sounds.bb_impact,
                     SoundCategory.AMBIENT,
                     0.2F,
                     1.3F + this.rand.nextFloat() / 5.0F
                  );
               this.world.setEntityState(this, (byte)8);
               this.setDead();
            }
         } else if (this.world
               .getBlockState(result.getBlockPos())
               .getBlock()
               .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
            != null) {
            this.world
               .playSound(
                  (EntityPlayer)null,
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.bb_impact,
                  SoundCategory.AMBIENT,
                  0.2F,
                  1.3F + this.rand.nextFloat() / 5.0F
               );
            if (!this.world.isRemote) {
               this.world.setEntityState(this, (byte)8);
               this.setDead();
            }
         }
      }
   }

   public static class WatcherLaser extends EntityThrowable implements IEntitySynchronize {
      static ResourceLocation texturexpl = new ResourceLocation("arpg:textures/blueexplode3.png");
      public float damage = 0.0F;
      public static ParticleTracker.TrackerSmoothShowHide trackerssh = new ParticleTracker.TrackerSmoothShowHide(
         null, new Vec3d[]{new Vec3d(0.0, 3.0, 0.2), new Vec3d(2.0, 5.0, -0.2)}
      );

      public WatcherLaser(World world) {
         super(world);
         this.setSize(0.15F, 0.15F);
      }

      public WatcherLaser(World world, EntityLivingBase thrower) {
         super(world, thrower);
         this.setSize(0.15F, 0.15F);
      }

      public WatcherLaser(World world, double x, double y, double z) {
         super(world, x, y, z);
         this.setSize(0.15F, 0.15F);
      }

      public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
         float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
         float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
         float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
         this.shoot(f, f1, f2, velocity, inaccuracy);
      }

      protected float getGravityVelocity() {
         return 0.0F;
      }

      public void onUpdate() {
         super.onUpdate();
         if (!this.world.isRemote && this.ticksExisted > 25) {
            this.setDead();
         }
      }

      public void writeEntityToNBT(NBTTagCompound compound) {
         super.writeEntityToNBT(compound);
         compound.setFloat("damage", this.damage);
      }

      public void readEntityFromNBT(NBTTagCompound compound) {
         super.readEntityFromNBT(compound);
         if (compound.hasKey("damage")) {
            this.damage = compound.getFloat("damage");
         }
      }

      @Override
      public void onClient(double x, double y, double z, double a, double b, double c) {
         GUNParticle sp = new GUNParticle(
            texturexpl,
            0.05F + this.rand.nextFloat() / 4.0F,
            0.0F,
            4,
            240,
            this.world,
            x,
            y,
            z,
            0.0F,
            0.0F,
            0.0F,
            1.0F,
            0.2F,
            1.0F,
            true,
            this.rand.nextInt(360)
         );
         sp.alphaGlowing = true;
         sp.randomDeath = false;
         sp.tracker = trackerssh;
         this.world.spawnEntity(sp);
      }

      protected void onImpact(RayTraceResult result) {
         if (!this.world.isRemote) {
            if (result.entityHit != null) {
               if (Team.checkIsOpponent(this.thrower, result.entityHit)) {
                  Weapons.dealDamage(
                     new WeaponDamage(null, this.getThrower(), this, false, true, this, WeaponDamage.laser),
                     this.damage,
                     this.getThrower(),
                     result.entityHit,
                     true,
                     0.1F
                  );
                  result.entityHit.hurtResistantTime = 0;
                  this.world
                     .playSound(
                        (EntityPlayer)null,
                        this.posX,
                        this.posY,
                        this.posZ,
                        Sounds.normal_projectile,
                        SoundCategory.AMBIENT,
                        0.9F,
                        1.0F + this.rand.nextFloat() / 5.0F
                     );
                  if (result.entityHit.getEntityBoundingBox() != null) {
                     Vec3d vec = GetMOP.getNearestPointInAABBtoPoint(this.getPositionVector(), result.entityHit.getEntityBoundingBox());
                     IEntitySynchronize.sendSynchronize(this, 64.0, vec.x, vec.y, vec.z);
                  } else {
                     IEntitySynchronize.sendSynchronize(this, 64.0, this.posX, this.posY, this.posZ);
                  }

                  this.setDead();
               }
            } else if (this.world
                  .getBlockState(result.getBlockPos())
                  .getBlock()
                  .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
               != null) {
               this.world
                  .playSound(
                     (EntityPlayer)null,
                     this.posX,
                     this.posY,
                     this.posZ,
                     Sounds.normal_projectile,
                     SoundCategory.AMBIENT,
                     0.4F,
                     1.1F + this.rand.nextFloat() / 5.0F
                  );
               if (!this.world.isRemote) {
                  if (result.hitVec != null) {
                     IEntitySynchronize.sendSynchronize(
                        this, 64.0, result.hitVec.x, result.hitVec.y, result.hitVec.z
                     );
                  }

                  this.setDead();
               }
            }
         }
      }

      public boolean handleWaterMovement() {
         return false;
      }
   }

   public static class Whirl extends EntityThrowable {
      public static ResourceLocation cloud = new ResourceLocation("arpg:textures/largecloud.png");
      public static ResourceLocation round = new ResourceLocation("arpg:textures/whirl.png");
      public static ParticleTracker.TrackerMagicSpin[] spins = new ParticleTracker.TrackerMagicSpin[]{
         new ParticleTracker.TrackerMagicSpin(false, -10.0F),
         new ParticleTracker.TrackerMagicSpin(false, -15.0F),
         new ParticleTracker.TrackerMagicSpin(false, -20.0F)
      };
      public float damage = 0.0F;

      public Whirl(World world) {
         super(world);
         this.setSize(3.0F, 1.5F);
      }

      public Whirl(World world, EntityLivingBase thrower) {
         super(world, thrower);
         this.setSize(3.0F, 1.5F);
      }

      public Whirl(World world, double x, double y, double z) {
         super(world, x, y, z);
         this.setSize(3.0F, 1.5F);
      }

      public boolean isInRangeToRenderDist(double distance) {
         return false;
      }

      protected float getGravityVelocity() {
         return 0.001F;
      }

      @Nullable
      public AxisAlignedBB getCollisionBox(Entity entityIn) {
         return entityIn.getEntityBoundingBox();
      }

      public boolean isPushedByWater() {
         return false;
      }

      public boolean canBePushed() {
         return false;
      }

      public void onUpdate() {
         super.onUpdate();
         if (!this.world.isRemote) {
            if (this.ticksExisted > 230) {
               this.setDead();
            }

            this.pushOutOfBlocks(this.posX, (this.getEntityBoundingBox().minY + this.getEntityBoundingBox().maxY) / 2.0, this.posZ);
            if (this.ticksExisted % 4 == 0) {
               this.motionX *= 0.8;
               this.motionY *= 0.8;
               this.motionZ *= 0.8;
               if (this.thrower != null
                  && this.thrower instanceof EntityLiving
                  && ((EntityLiving)this.thrower).getAttackTarget() != null
                  && ((EntityLiving)this.thrower).getEntitySenses().canSee(((EntityLiving)this.thrower).getAttackTarget())) {
                  EntityLivingBase at = ((EntityLiving)this.thrower).getAttackTarget();
                  SuperKnockback.applyMove(this, -0.08F, at.posX, at.posY, at.posZ);
               }

               double dR = 3.5 + MathHelper.clamp(this.ticksExisted / 115.0F, 0.0F, 2.5F);
               AxisAlignedBB axisalignedbb = new AxisAlignedBB(
                  this.posX - dR,
                  this.posY,
                  this.posZ - dR,
                  this.posX + dR,
                  this.posY + dR,
                  this.posZ + dR
               );
               List<Entity> list = this.world.getEntitiesWithinAABB(Entity.class, axisalignedbb);
               if (!list.isEmpty()) {
                  for (Entity entityl : list) {
                     if (!(entityl instanceof StormledgeMobsPack.Windbreak) && (entityl.canBePushed() || entityl.isPushedByWater())) {
                        SuperKnockback.applyKnockback(entityl, -0.38F, this.posX, this.posY, this.posZ);
                        entityl.velocityChanged = true;
                     }

                     if (this.ticksExisted % 40 == 0 && entityl instanceof EntityLivingBase && Team.checkIsOpponent(this.thrower, entityl)) {
                        EntityLivingBase entitylivingbase = (EntityLivingBase)entityl;
                        Weapons.dealDamage(
                           new WeaponDamage(null, this.getThrower(), this, false, false, this, WeaponDamage.electric),
                           this.damage,
                           this.getThrower(),
                           entitylivingbase,
                           true
                        );
                        entitylivingbase.hurtResistantTime = 0;
                     }
                  }
               }
            }
         }
      }

      public void writeEntityToNBT(NBTTagCompound compound) {
         super.writeEntityToNBT(compound);
         compound.setFloat("damage", this.damage);
      }

      public void readEntityFromNBT(NBTTagCompound compound) {
         super.readEntityFromNBT(compound);
         if (compound.hasKey("damage")) {
            this.damage = compound.getFloat("damage");
         }
      }

      protected void onImpact(RayTraceResult result) {
      }

      public boolean isNotColliding() {
         return !this.world.containsAnyLiquid(this.getEntityBoundingBox())
            && this.world.getCollisionBoxes(this, this.getEntityBoundingBox()).isEmpty()
            && this.world.checkNoEntityCollision(this.getEntityBoundingBox(), this);
      }

      public void onEntityUpdate() {
         if (this.world.isRemote && this.ticksExisted % 5 == 0) {
            float dR = 3.0F + MathHelper.clamp(this.ticksExisted / 115.0F, 0.0F, 2.0F);
            GUNParticle fire = new GUNParticle(
               cloud,
               0.7F + (float)this.rand.nextGaussian() / 3.0F,
               -0.003F,
               27 + this.rand.nextInt(15),
               150,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               (float)this.rand.nextGaussian() / 11.0F,
               (float)this.rand.nextGaussian() / 17.0F + 0.2F,
               (float)this.rand.nextGaussian() / 11.0F,
               0.9F,
               1.0F,
               1.0F,
               true,
               this.rand.nextInt(100) - 50
            );
            fire.alphaTickAdding = -0.026F;
            fire.scaleTickAdding = 0.07F;
            this.world.spawnEntity(fire);
            float scalen = this.rand.nextFloat() * 1.5F + dR * 2.0F;
            GUNParticle part = new GUNParticle(
               round,
               0.3F * this.rand.nextFloat(),
               -0.002F - this.rand.nextFloat() * 0.002F,
               50,
               150,
               this.world,
               this.posX,
               this.posY + this.rand.nextFloat() / 2.0F,
               this.posZ,
               0.0F,
               0.0F,
               0.0F,
               0.9F,
               0.86F,
               0.9F,
               true,
               this.rand.nextInt(360)
            );
            part.tracker = spins[this.rand.nextInt(3)];
            part.rotationPitchYaw = new Vec2f(90.0F + (float)this.rand.nextGaussian() * 5.0F, this.rand.nextInt(360));
            part.alphaTickAdding = -0.02F;
            part.scaleTickAdding = scalen / 50.0F;
            this.world.spawnEntity(part);
         }
      }
   }

   public static class WindbreakShoot extends EntityThrowable {
      static ResourceLocation trail = new ResourceLocation("arpg:textures/lightning2.png");
      static ResourceLocation plasma = new ResourceLocation("arpg:textures/plasma_cloud.png");
      public float damage = 0.0F;
      public Entity target;

      public WindbreakShoot(World world) {
         super(world);
         this.setSize(0.2F, 0.2F);
      }

      public WindbreakShoot(World world, EntityLivingBase thrower) {
         super(world, thrower);
         this.setSize(0.2F, 0.2F);
      }

      public WindbreakShoot(World world, double x, double y, double z) {
         super(world, x, y, z);
         this.setSize(0.2F, 0.2F);
      }

      protected float getGravityVelocity() {
         return 0.0F;
      }

      public void onUpdate() {
         super.onUpdate();
         if (!this.world.isRemote) {
            this.motionX *= 0.9;
            this.motionY *= 0.9;
            this.motionZ *= 0.9;
            if (this.ticksExisted % 5 == 0 && this.target != null) {
               if (this.ticksExisted < 30) {
                  float power = (1.0F - this.ticksExisted / 30.0F) / 9.0F;
                  this.motionX = this.motionX + this.rand.nextGaussian() * power;
                  this.motionY = this.motionY + this.rand.nextGaussian() * power;
                  this.motionZ = this.motionZ + this.rand.nextGaussian() * power;
               } else {
                  SuperKnockback.applyMove(this, -0.3F, this.target.posX, this.target.posY, this.target.posZ);
                  if (this.ticksExisted % 10 == 0
                     && this.ticksExisted > 60
                     && !GetMOP.thereIsNoBlockCollidesBetween(this.world, this.getPositionVector(), this.target.getPositionEyes(1.0F), 1.0F, null, false)) {
                     this.findTarget();
                  }
               }
            }

            if (this.ticksExisted > 200) {
               this.setDead();
            }

            if (this.target == null) {
               this.findTarget();
            }
         }
      }

      public void findTarget() {
         double damageRadius = 14.0;
         AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
            .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
            .offset(-damageRadius, -damageRadius, -damageRadius);
         List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
         if (!list.isEmpty()) {
            EntityLivingBase targ = null;
            double maxDIST = Double.MAX_VALUE;

            for (EntityLivingBase entitylivingbase : list) {
               if (EntitySelectors.CAN_AI_TARGET.apply(entitylivingbase)
                  && entitylivingbase.isEntityAlive()
                  && Team.checkIsOpponent(this.thrower, entitylivingbase)) {
                  double distsq = entitylivingbase.getDistanceSq(this);
                  if (distsq < maxDIST) {
                     targ = entitylivingbase;
                     maxDIST = distsq;
                  }
               }
            }

            if (targ != null) {
               this.target = targ;
            }
         }
      }

      public void writeEntityToNBT(NBTTagCompound compound) {
         super.writeEntityToNBT(compound);
         compound.setFloat("damage", this.damage);
      }

      public void readEntityFromNBT(NBTTagCompound compound) {
         super.readEntityFromNBT(compound);
         if (compound.hasKey("damage")) {
            this.damage = compound.getFloat("damage");
         }
      }

      @SideOnly(Side.CLIENT)
      public void handleStatusUpdate(byte id) {
         if (id == 8) {
            GUNParticle bigsmoke1 = new GUNParticle(
               plasma,
               0.02F,
               0.0F,
               8,
               240,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               0.0F,
               0.0F,
               0.0F,
               1.0F,
               1.0F,
               1.0F,
               true,
               this.rand.nextInt(360)
            );
            bigsmoke1.alphaTickAdding = -0.13F;
            bigsmoke1.alphaGlowing = true;
            bigsmoke1.scaleTickAdding = 0.08F;
            this.world.spawnEntity(bigsmoke1);
         }
      }

      protected void onImpact(RayTraceResult result) {
         if (result.entityHit != null) {
            if (Team.checkIsOpponent(this.thrower, result.entityHit) && !this.world.isRemote) {
               Weapons.dealDamage(
                  new WeaponDamage(null, this.getThrower(), this, false, true, this, WeaponDamage.electric),
                  this.damage,
                  this.getThrower(),
                  result.entityHit,
                  true,
                  1.0F,
                  this.posX,
                  this.posY,
                  this.posZ
               );
               result.entityHit.hurtResistantTime = 0;
               this.world
                  .playSound(
                     (EntityPlayer)null,
                     this.posX,
                     this.posY,
                     this.posZ,
                     Sounds.plasma_rifle_impact,
                     SoundCategory.AMBIENT,
                     0.7F,
                     0.9F + this.rand.nextFloat() / 5.0F
                  );
               this.world.setEntityState(this, (byte)8);
               this.setDead();
            }
         } else if (this.world
               .getBlockState(result.getBlockPos())
               .getBlock()
               .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
            != null) {
            if (result.sideHit == EnumFacing.UP || result.sideHit == EnumFacing.DOWN) {
               this.motionY = -this.motionY * 0.35;
            }

            if (result.sideHit == EnumFacing.NORTH || result.sideHit == EnumFacing.SOUTH) {
               this.motionZ = -this.motionZ * 0.35;
            }

            if (result.sideHit == EnumFacing.EAST || result.sideHit == EnumFacing.WEST) {
               this.motionX = -this.motionX * 0.35;
            }
         }
      }

      @SideOnly(Side.CLIENT)
      public void onEntityUpdate() {
         Vec3d pos1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
         Vec3d pos2 = this.getPositionVector();
         if (this.world.isRemote && pos1.lengthSquared() > 1.0E-6 && pos2.lengthSquared() > 1.0E-6) {
            BetweenParticle laser = new BetweenParticle(
               this.world, trail, 0.23F, 240, 1.0F, 0.9F, 1.0F, 0.1F, pos1.distanceTo(pos2), 5, -0.15F, 9999.0F, pos1, pos2
            );
            laser.alphaGlowing = true;
            laser.texstart = 0.1F;
            laser.offset = -0.1F;
            laser.setPosition(pos1.x, pos1.y, pos1.z);
            this.world.spawnEntity(laser);
         }
      }
   }

   public static class WinterFuryBreath extends EntityThrowable implements IRenderOptions {
      public float damage = 0.0F;
      public boolean burn = false;

      public WinterFuryBreath(World world) {
         super(world);
         this.setSize(0.4F, 0.4F);
      }

      public WinterFuryBreath(World world, EntityLivingBase thrower) {
         super(world, thrower);
         this.setSize(0.4F, 0.4F);
      }

      public WinterFuryBreath(World world, double x, double y, double z) {
         super(world, x, y, z);
         this.setSize(0.4F, 0.4F);
      }

      public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
         float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
         float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
         float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
         this.shoot(f, f1, f2, velocity, inaccuracy);
         this.motionX = this.motionX + entityThrower.motionX * 0.3;
         this.motionZ = this.motionZ + entityThrower.motionZ * 0.3;
         if (!entityThrower.onGround) {
            this.motionY = this.motionY + entityThrower.motionY * 0.3;
         }
      }

      protected float getGravityVelocity() {
         return 0.001F;
      }

      public void onUpdate() {
         super.onUpdate();
         if (this.ticksExisted > 28) {
            this.setDead();
         }
      }

      public void writeEntityToNBT(NBTTagCompound compound) {
         super.writeEntityToNBT(compound);
         compound.setFloat("damage", this.damage);
         compound.setBoolean("burn", this.burn);
      }

      public void readEntityFromNBT(NBTTagCompound compound) {
         super.readEntityFromNBT(compound);
         if (compound.hasKey("damage")) {
            this.damage = compound.getFloat("damage");
         }

         if (compound.hasKey("burn")) {
            this.burn = compound.getBoolean("burn");
         }
      }

      protected void onImpact(RayTraceResult result) {
         if (result.entityHit != null) {
            if (Team.checkIsOpponent(this.thrower, result.entityHit) && !this.world.isRemote) {
               Weapons.dealDamage(
                  new WeaponDamage(null, this.getThrower(), this, false, false, this, WeaponDamage.frost),
                  this.damage,
                  this.getThrower(),
                  result.entityHit,
                  true,
                  0.6F
               );
               result.entityHit.hurtResistantTime = 0;
               if (result.entityHit instanceof EntityLivingBase) {
                  EntityLivingBase entitylivingbase = (EntityLivingBase)result.entityHit;
                  if (!this.burn) {
                     PotionEffect lastdebaff = Weapons.mixPotion(
                        entitylivingbase,
                        PotionEffects.FREEZING,
                        40.0F,
                        1.0F,
                        Weapons.EnumPotionMix.WITH_MAXIMUM,
                        Weapons.EnumPotionMix.WITH_MAXIMUM,
                        Weapons.EnumMathOperation.PLUS,
                        Weapons.EnumMathOperation.PLUS,
                        80,
                        8
                     );
                     Freezing.tryPlaySound(entitylivingbase, lastdebaff);
                     DeathEffects.applyDeathEffect(entitylivingbase, DeathEffects.DE_ICING, 0.8F);
                  } else {
                     this.tryPlaceFire();
                  }

                  this.setDead();
                  if (entitylivingbase.getHealth() <= 0.0F && this.rand.nextFloat() < 0.7) {
                     DeathEffects.applyDeathEffect(entitylivingbase, DeathEffects.DE_ICING);
                  }
               }
            }
         } else if (this.world
               .getBlockState(result.getBlockPos())
               .getBlock()
               .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
            != null) {
            if (this.burn) {
               this.tryPlaceFire();
            }

            if (!this.world.isRemote) {
               this.setDead();
            }
         }
      }

      public void tryPlaceFire() {
         BlockPos tpos = this.getPosition();

         for (EnumFacing f : EnumFacing.VALUES) {
            BlockPos blockpos = tpos.offset(f);
            IBlockState state = this.world.getBlockState(blockpos);
            if (this.rand.nextFloat() < 0.5
               && state.getBlock().isReplaceable(this.world, blockpos)
               && BlocksRegister.BURNINGFROST.canPlaceBlockAt(this.world, blockpos)) {
               this.world
                  .setBlockState(blockpos, BlocksRegister.BURNINGFROST.getActualState(BlocksRegister.BURNINGFROST.getDefaultState(), this.world, blockpos));
            }
         }

         IBlockState state = this.world.getBlockState(tpos);
         if (this.rand.nextFloat() < 0.5
            && state.getBlock().isReplaceable(this.world, tpos)
            && BlocksRegister.BURNINGFROST.canPlaceBlockAt(this.world, tpos)) {
            this.world
               .setBlockState(tpos, BlocksRegister.BURNINGFROST.getActualState(BlocksRegister.BURNINGFROST.getDefaultState(), this.world, tpos));
         }
      }

      @Override
      public void doOptions() {
         float scl = this.ticksExisted / 6.0F;
         GlStateManager.scale(scl, scl, scl);
      }

      @Override
      public void undoOptions() {
      }
   }

   public static class WizardfishShoot extends EntityThrowable {
      public static ResourceLocation trail = new ResourceLocation("arpg:textures/magic_trace.png");
      public static ResourceLocation tex = new ResourceLocation("arpg:textures/flower_acid.png");
      public float damage = 0.0F;

      public WizardfishShoot(World world) {
         super(world);
         this.setSize(0.2F, 0.2F);
      }

      public WizardfishShoot(World world, EntityLivingBase thrower) {
         super(world, thrower);
         this.setSize(0.2F, 0.2F);
      }

      public WizardfishShoot(World world, double x, double y, double z) {
         super(world, x, y, z);
         this.setSize(0.2F, 0.2F);
      }

      public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
         float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
         float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
         float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
         this.shoot(f, f1, f2, velocity, inaccuracy);
      }

      protected float getGravityVelocity() {
         return 0.0F;
      }

      public void onUpdate() {
         super.onUpdate();
         if (this.ticksExisted > 40) {
            this.setDead();
         }
      }

      public void writeEntityToNBT(NBTTagCompound compound) {
         super.writeEntityToNBT(compound);
         compound.setFloat("damage", this.damage);
      }

      public void readEntityFromNBT(NBTTagCompound compound) {
         super.readEntityFromNBT(compound);
         if (compound.hasKey("damage")) {
            this.damage = compound.getFloat("damage");
         }
      }

      @SideOnly(Side.CLIENT)
      public void handleStatusUpdate(byte id) {
         if (id == 8) {
            for (int ss = 0; ss < 15; ss++) {
               GUNParticle spell = new GUNParticle(
                  tex,
                  0.06F + this.rand.nextFloat() * 0.07F,
                  -2.0E-4F,
                  40,
                  210,
                  this.world,
                  this.posX,
                  this.posY,
                  this.posZ,
                  (float)this.rand.nextGaussian() / 16.0F,
                  (float)this.rand.nextGaussian() / 16.0F,
                  (float)this.rand.nextGaussian() / 16.0F,
                  0.7F + this.rand.nextFloat() * 0.3F,
                  1.0F,
                  1.0F,
                  true,
                  0,
                  true,
                  1.0F
               );
               spell.alphaGlowing = true;
               spell.scaleTickAdding = -0.0016F;
               this.world.spawnEntity(spell);
            }
         }
      }

      protected void onImpact(RayTraceResult result) {
         if (result.entityHit != null) {
            if (Team.checkIsOpponent(this.thrower, result.entityHit) && !this.world.isRemote) {
               Weapons.dealDamage(
                  new WeaponDamage(null, this.getThrower(), this, false, true, this, WeaponDamage.magic),
                  this.damage,
                  this.getThrower(),
                  result.entityHit,
                  true,
                  1.0F
               );
               Weapons.setPotionIfEntityLB(result.entityHit, new PotionEffect(MobEffects.MINING_FATIGUE, 300, 1));
               this.world
                  .playSound(
                     (EntityPlayer)null,
                     this.posX,
                     this.posY,
                     this.posZ,
                     Sounds.hitmagic_a,
                     SoundCategory.AMBIENT,
                     0.4F,
                     1.1F + this.rand.nextFloat() / 5.0F
                  );
               this.world.setEntityState(this, (byte)8);
               this.setDead();
            }
         } else if (this.world
               .getBlockState(result.getBlockPos())
               .getBlock()
               .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
            != null) {
            this.world
               .playSound(
                  (EntityPlayer)null,
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.hitmagic_a,
                  SoundCategory.AMBIENT,
                  0.4F,
                  1.1F + this.rand.nextFloat() / 5.0F
               );
            if (!this.world.isRemote) {
               this.world.setEntityState(this, (byte)8);
               this.setDead();
            }
         }
      }

      @SideOnly(Side.CLIENT)
      public void onEntityUpdate() {
         Vec3d pos1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
         Vec3d pos2 = this.getPositionVector();
         if (this.world.isRemote && pos1.lengthSquared() > 1.0E-6 && pos2.lengthSquared() > 1.0E-6) {
            BetweenParticle laser = new BetweenParticle(
               this.world, trail, 0.2F, 220, 1.0F, 0.75F, 1.0F, 0.1F, pos1.distanceTo(pos2), 4, -0.15F, 9999.0F, pos1, pos2
            );
            laser.alphaGlowing = true;
            laser.texstart = 0.1F;
            laser.offset = -0.1F;
            laser.setPosition(pos1.x, pos1.y, pos1.z);
            this.world.spawnEntity(laser);
         }
      }
   }

   public static class ZarpionBeam extends EntityThrowable {
      static ResourceLocation trail = new ResourceLocation("arpg:textures/ghostly_shoot.png");
      static ResourceLocation sparkle = new ResourceLocation("arpg:textures/sparkle2.png");
      public float damage = 0.0F;

      public ZarpionBeam(World world) {
         super(world);
         this.setSize(0.2F, 0.2F);
      }

      public ZarpionBeam(World world, EntityLivingBase thrower) {
         super(world, thrower);
         this.setSize(0.2F, 0.2F);
      }

      public ZarpionBeam(World world, double x, double y, double z) {
         super(world, x, y, z);
         this.setSize(0.2F, 0.2F);
      }

      public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
         float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
         float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
         float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
         this.shoot(f, f1, f2, velocity, inaccuracy);
      }

      protected float getGravityVelocity() {
         return 0.0F;
      }

      public void onUpdate() {
         super.onUpdate();
         if (this.ticksExisted > 30) {
            this.setDead();
         }
      }

      public void writeEntityToNBT(NBTTagCompound compound) {
         super.writeEntityToNBT(compound);
         compound.setFloat("damage", this.damage);
      }

      public void readEntityFromNBT(NBTTagCompound compound) {
         super.readEntityFromNBT(compound);
         if (compound.hasKey("damage")) {
            this.damage = compound.getFloat("damage");
         }
      }

      @SideOnly(Side.CLIENT)
      public void handleStatusUpdate(byte id) {
         if (id == 8) {
            int countOfParticles = 10;
            float R = 0.05F + (float)this.rand.nextGaussian() / 40.0F;

            for (int i = 0; i < countOfParticles; i++) {
               float rand1 = this.rand.nextFloat() * 2.0F - 1.0F;
               float rand2 = this.rand.nextFloat() * 2.0F - 1.0F;
               float X = rand1 * R;
               float new_R = (float)Math.sqrt(R * R - X * X);
               float Y = rand2 * new_R;
               float Z = (float)Math.sqrt(new_R * new_R - Y * Y);
               if (this.rand.nextBoolean()) {
                  Z *= -1.0F;
               }

               float scale = 0.2F + this.rand.nextFloat() / 6.0F;
               GUNParticle particle = new GUNParticle(
                  sparkle,
                  scale,
                  0.007F,
                  40,
                  240,
                  this.world,
                  this.posX,
                  this.posY,
                  this.posZ,
                  X,
                  Y,
                  Z,
                  0.9F,
                  1.0F,
                  1.0F,
                  true,
                  0
               );
               particle.alphaGlowing = true;
               particle.scaleTickAdding = -scale / 40.0F;
               this.world.spawnEntity(particle);
            }
         }
      }

      protected void onImpact(RayTraceResult result) {
         if (result.entityHit != null) {
            if (Team.checkIsOpponent(this.thrower, result.entityHit) && !this.world.isRemote) {
               Weapons.dealDamage(
                  new WeaponDamage(null, this.getThrower(), this, false, true, this, WeaponDamage.electric),
                  this.damage,
                  this.getThrower(),
                  result.entityHit,
                  true,
                  1.5F
               );
               Weapons.setPotionIfEntityLB(result.entityHit, new PotionEffect(PotionEffects.SHOCK, 80));
               this.world
                  .playSound(
                     (EntityPlayer)null,
                     this.posX,
                     this.posY,
                     this.posZ,
                     Sounds.plasma_rifle_impact,
                     SoundCategory.AMBIENT,
                     0.4F,
                     1.4F + this.rand.nextFloat() / 5.0F
                  );
               this.world.setEntityState(this, (byte)8);
               this.setDead();
            }
         } else if (this.world
               .getBlockState(result.getBlockPos())
               .getBlock()
               .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
            != null) {
            this.world
               .playSound(
                  (EntityPlayer)null,
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.plasma_rifle_impact,
                  SoundCategory.AMBIENT,
                  0.4F,
                  1.4F + this.rand.nextFloat() / 5.0F
               );
            if (!this.world.isRemote) {
               this.world.setEntityState(this, (byte)8);
               this.setDead();
            }
         }
      }

      @SideOnly(Side.CLIENT)
      public void onEntityUpdate() {
         Vec3d pos1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
         Vec3d pos2 = this.getPositionVector();
         if (this.world.isRemote && pos1.lengthSquared() > 1.0E-6 && pos2.lengthSquared() > 1.0E-6) {
            BetweenParticle laser = new BetweenParticle(
               this.world, trail, 0.09F, 240, 1.0F, 0.9F, 1.0F, 0.1F, pos1.distanceTo(pos2), 5, -0.15F, 9999.0F, pos1, pos2
            );
            laser.alphaGlowing = true;
            laser.texstart = 0.1F;
            laser.offset = -0.1F;
            laser.setPosition(pos1.x, pos1.y, pos1.z);
            this.world.spawnEntity(laser);
         }
      }
   }

   public static class ZarpionBlaterShoot extends EntityThrowable implements IRenderOptions {
      public static ResourceLocation circle = new ResourceLocation("arpg:textures/blueexplode.png");
      public static ResourceLocation sphere = new ResourceLocation("arpg:textures/blueexplode5.png");
      public float damage = 0.0F;

      public ZarpionBlaterShoot(World world) {
         super(world);
         this.setSize(0.2F, 0.2F);
      }

      public ZarpionBlaterShoot(World world, EntityLivingBase thrower) {
         super(world, thrower);
         this.setSize(0.2F, 0.2F);
      }

      public ZarpionBlaterShoot(World world, double x, double y, double z) {
         super(world, x, y, z);
         this.setSize(0.2F, 0.2F);
      }

      public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
         float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
         float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
         float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
         this.shoot(f, f1, f2, velocity, inaccuracy);
         this.motionX = this.motionX + entityThrower.motionX * 0.1;
         this.motionZ = this.motionZ + entityThrower.motionZ * 0.1;
         if (!entityThrower.onGround) {
            this.motionY = this.motionY + entityThrower.motionY * 0.1;
         }
      }

      protected float getGravityVelocity() {
         return 0.0F;
      }

      public void onUpdate() {
         super.onUpdate();
         if (this.ticksExisted > 50) {
            this.setDead();
         }
      }

      public void writeEntityToNBT(NBTTagCompound compound) {
         super.writeEntityToNBT(compound);
         compound.setFloat("damage", this.damage);
      }

      public void readEntityFromNBT(NBTTagCompound compound) {
         super.readEntityFromNBT(compound);
         if (compound.hasKey("damage")) {
            this.damage = compound.getFloat("damage");
         }
      }

      @SideOnly(Side.CLIENT)
      public void handleStatusUpdate(byte id) {
         if (id == 8) {
            GUNParticle sp = new GUNParticle(
               circle,
               0.1F + this.rand.nextFloat() / 4.0F,
               0.0F,
               4,
               240,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               0.0F,
               0.0F,
               0.0F,
               0.7F,
               1.0F - this.rand.nextFloat() / 6.0F,
               1.0F,
               true,
               this.rand.nextInt(360)
            );
            sp.alphaGlowing = true;
            sp.randomDeath = false;
            sp.tracker = EntityElectricBolt.trackerssh;
            this.world.spawnEntity(sp);
         }
      }

      protected void onImpact(RayTraceResult result) {
         if (result.entityHit != null) {
            if (Team.checkIsOpponent(this.thrower, result.entityHit) && !this.world.isRemote) {
               Weapons.dealDamage(
                  new WeaponDamage(null, this.getThrower(), this, false, true, this, WeaponDamage.plasma),
                  this.damage,
                  this.getThrower(),
                  result.entityHit,
                  true,
                  0.5F
               );
               Weapons.setPotionIfEntityLB(result.entityHit, new PotionEffect(MobEffects.WEAKNESS, 50, 1));
               this.world
                  .playSound(
                     (EntityPlayer)null,
                     this.posX,
                     this.posY,
                     this.posZ,
                     Sounds.plasma_rifle_impact,
                     SoundCategory.AMBIENT,
                     0.4F,
                     1.4F + this.rand.nextFloat() / 5.0F
                  );
               this.world.setEntityState(this, (byte)8);
               this.setDead();
            }
         } else if (this.world
               .getBlockState(result.getBlockPos())
               .getBlock()
               .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
            != null) {
            this.world
               .playSound(
                  (EntityPlayer)null,
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.plasma_rifle_impact,
                  SoundCategory.AMBIENT,
                  0.4F,
                  1.4F + this.rand.nextFloat() / 5.0F
               );
            if (!this.world.isRemote) {
               this.world.setEntityState(this, (byte)8);
               this.setDead();
            }
         }
      }

      public void onEntityUpdate() {
         super.onEntityUpdate();
         if (this.ticksExisted == 2) {
            GUNParticle bigsmoke1 = new GUNParticle(
               sphere,
               0.02F,
               0.0F,
               8,
               240,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               0.0F,
               0.0F,
               0.0F,
               1.0F,
               1.0F,
               1.0F,
               true,
               this.rand.nextInt(360)
            );
            bigsmoke1.alphaTickAdding = -0.13F;
            bigsmoke1.alphaGlowing = true;
            bigsmoke1.scaleTickAdding = 0.09F;
            this.world.spawnEntity(bigsmoke1);
         }
      }

      @Override
      public void doOptions() {
         GlStateManager.enableBlend();
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
      }

      @Override
      public void undoOptions() {
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         GlStateManager.disableBlend();
      }
   }
}
