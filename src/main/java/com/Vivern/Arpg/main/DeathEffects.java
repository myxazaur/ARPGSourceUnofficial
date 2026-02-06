package com.Vivern.Arpg.main;

import com.Vivern.Arpg.entity.EntityCubicParticle;
import com.Vivern.Arpg.entity.GunPEmitter;
import com.Vivern.Arpg.entity.INailer;
import com.Vivern.Arpg.entity.ParticleGore;
import com.Vivern.Arpg.mobs.AbstractMob;
import com.Vivern.Arpg.network.PacketDEToClients;
import com.Vivern.Arpg.network.PacketHandler;
import com.Vivern.Arpg.proxy.ClientProxy;
import com.Vivern.Arpg.renders.GUNParticle;
import com.Vivern.Arpg.renders.SparkleSubparticle;
import com.Vivern.Arpg.shader.ShaderMain;
import com.google.common.base.Predicate;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityVex;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.passive.EntitySkeletonHorse;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderLivingEvent.Pre;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import org.lwjgl.opengl.GL11;

@EventBusSubscriber(
   modid = "arpg"
)
public class DeathEffects {
   public static final ResourceLocation textur = new ResourceLocation("arpg:textures/freezing.png");
   public static final ResourceLocation snowflake2 = new ResourceLocation("arpg:textures/snowflake2.png");
   public static final ResourceLocation icecube = new ResourceLocation("arpg:textures/ice_cube.png");
   public static final ResourceLocation snowflake3 = new ResourceLocation("arpg:textures/snowflake3.png");
   public static final ResourceLocation minisand1 = new ResourceLocation("arpg:textures/minisand1.png");
   public static final ResourceLocation minisand2 = new ResourceLocation("arpg:textures/minisand2.png");
   public static final ResourceLocation minisand3 = new ResourceLocation("arpg:textures/minisand3.png");
   public static final ResourceLocation acidbubble = new ResourceLocation("arpg:textures/de_acid_bubble.png");
   public static final ResourceLocation shockbeam = new ResourceLocation("arpg:textures/shock.png");
   public static final ResourceLocation electr = new ResourceLocation("arpg:textures/blueexplode2.png");
   public static final ResourceLocation deelectric = new ResourceLocation("arpg:textures/blueexplode4.png");
   public static final ResourceLocation gore = new ResourceLocation("arpg:textures/gore.png");
   public static final ResourceLocation drop = new ResourceLocation("arpg:textures/normaldrop.png");
   public static ResourceLocation blood = new ResourceLocation("arpg:textures/blood.png");
   public static List<EntityLivingBase> listEFFECTshock = new ArrayList<>();
   public static Map<Class, ModelBase> mainModels = new HashMap<>();
   public static Map<Class, ResourceLocation> mainTextures = new HashMap<>();
   public static Random rand = new Random();
   public static ModelBase playerModel = new ModelPlayer(0.0F, false);
   public static DeathEffect DE_ICING = new DeathEffect() {
      @Override
      public void onRenderLivingPre(Pre event, EntityLivingBase entity) {
         ModelBase model = event.getRenderer().getMainModel();
         GlStateManager.pushMatrix();
         GL11.glDepthMask(false);
         GL11.glEnable(3042);
         Color.doRender(
            entity, event.getX(), event.getY(), event.getZ(), entity.rotationYaw, event.getPartialRenderTick(), model, DeathEffects.textur, 0.0F, false
         );
         GL11.glDisable(3042);
         GL11.glDepthMask(true);
         event.setCanceled(true);
         GlStateManager.popMatrix();
      }

      @Override
      public void onLivingUpdate(World world, LivingUpdateEvent event, EntityLivingBase base, double x, double y, double z) {
         if (base.deathTime == 1) {
            world.playSound((EntityPlayer)null, x, y, z, Sounds.de_icing, SoundCategory.HOSTILE, 0.8F + DeathEffects.rand.nextFloat() / 5.0F, 1.1F);
         }

         if (base.deathTime == 19) {
            for (int i = 0; i < Math.min(base.getMaxHealth() / 5.0F, 10.0F); i++) {
               GunPEmitter emi = new GunPEmitter(
                  DeathEffects.snowflake2,
                  0.3F,
                  0.06F,
                  30,
                  240,
                  world,
                  x,
                  y,
                  z,
                  (float)DeathEffects.rand.nextGaussian() / 4.0F,
                  (float)DeathEffects.rand.nextGaussian() / 4.0F + 0.3F,
                  (float)DeathEffects.rand.nextGaussian() / 4.0F,
                  1.0F,
                  1.0F,
                  1.0F,
                  true,
                  1,
                  false,
                  DeathEffects.snowflake2,
                  0.35F,
                  0.0F,
                  10,
                  100,
                  100.0F,
                  100.0F,
                  100.0F,
                  1.0F,
                  1.0F,
                  1.0F,
                  true
               );
               world.spawnEntity(emi);
            }

            for (int i = 0; i < Math.min(base.getMaxHealth() / 2.0F, 15.0F); i++) {
               Entity spel = new EntityCubicParticle(
                  DeathEffects.icecube,
                  0.02F,
                  0.02F,
                  20 + DeathEffects.rand.nextInt(10),
                  90 + DeathEffects.rand.nextInt(9),
                  world,
                  x,
                  y + 0.5,
                  z,
                  (float)DeathEffects.rand.nextGaussian() / 9.0F,
                  (float)DeathEffects.rand.nextGaussian() / 9.0F + 0.3F,
                  (float)DeathEffects.rand.nextGaussian() / 9.0F,
                  1.0F,
                  1.0F,
                  1.0F,
                  true,
                  DeathEffects.rand.nextFloat(),
                  DeathEffects.rand.nextFloat(),
                  DeathEffects.rand.nextFloat(),
                  0.05F,
                  true,
                  -3.0E-4F
               );
               world.spawnEntity(spel);
               Entity spelll = new GUNParticle(
                  DeathEffects.snowflake3,
                  0.57F,
                  0.005F,
                  20 + DeathEffects.rand.nextInt(10),
                  240,
                  world,
                  x,
                  y + 0.8,
                  z,
                  (float)DeathEffects.rand.nextGaussian() / 20.0F,
                  (float)DeathEffects.rand.nextGaussian() / 20.0F + 0.1F,
                  (float)DeathEffects.rand.nextGaussian() / 20.0F,
                  1.0F,
                  1.0F,
                  0.9F + (float)DeathEffects.rand.nextGaussian() / 10.0F,
                  false,
                  DeathEffects.rand.nextInt(360)
               );
               world.spawnEntity(spelll);
            }
         }
      }
   };
   public static DeathEffect DE_SAND = new DeathEffect() {
      @Override
      public void onRenderLivingPre(Pre event, EntityLivingBase entity) {
         ModelBase model = event.getRenderer().getMainModel();
         GlStateManager.pushMatrix();
         Color.doRender(
            entity,
            event.getX(),
            event.getY(),
            event.getZ(),
            entity.rotationYaw,
            event.getPartialRenderTick(),
            model,
            ClientProxy.sand.get(entity.deathTime - 1),
            0.0F,
            false
         );
         event.setCanceled(true);
         GlStateManager.popMatrix();
      }

      @Override
      public void onLivingUpdate(World world, LivingUpdateEvent event, EntityLivingBase base, double x, double y, double z) {
         if (base.deathTime == 1) {
            world.playSound(
               (EntityPlayer)null,
               x,
               y,
               z,
               Sounds.de_sand,
               SoundCategory.AMBIENT,
               0.8F + DeathEffects.rand.nextFloat() / 5.0F,
               0.9F + DeathEffects.rand.nextFloat() / 5.0F
            );
         }

         AxisAlignedBB aabb = base.getEntityBoundingBox();
         double rY = aabb.minY + (aabb.maxY - aabb.minY) * DeathEffects.rand.nextDouble();
         double rZ = aabb.minZ + (aabb.maxZ - aabb.minZ) * DeathEffects.rand.nextDouble();
         double rX = aabb.minX + (aabb.maxX - aabb.minX) * DeathEffects.rand.nextDouble();
         ResourceLocation sandptex = DeathEffects.minisand1;
         float scale = 0.04F;
         if (DeathEffects.rand.nextFloat() > 0.8) {
            sandptex = DeathEffects.minisand2;
            scale = 0.06F;
         } else if (DeathEffects.rand.nextFloat() > 0.7) {
            sandptex = DeathEffects.minisand3;
            scale = 0.09F;
         }

         Entity sandp = new GUNParticle(
            sandptex,
            scale,
            0.01F,
            30 + DeathEffects.rand.nextInt(15),
            -1,
            world,
            rX,
            rY,
            rZ,
            (float)DeathEffects.rand.nextGaussian() / 30.0F,
            (float)DeathEffects.rand.nextGaussian() / 30.0F,
            (float)DeathEffects.rand.nextGaussian() / 30.0F,
            1.0F,
            1.0F,
            0.9F + (float)DeathEffects.rand.nextGaussian() / 10.0F,
            false,
            DeathEffects.rand.nextInt(360),
            true,
            1.1F
         );
         world.spawnEntity(sandp);
      }
   };
   public static DeathEffect DE_COLOREDACID = new DeathEffect() {
      @Override
      public void onRenderLivingPre(Pre event, EntityLivingBase entity) {
         int mobdeathtime = entity.deathTime;
         ModelBase model = event.getRenderer().getMainModel();
         GlStateManager.pushMatrix();
         GL11.glEnable(3042);
         GL11.glDisable(2896);
         GlStateManager.color(
            (float)entity.getEntityAttribute(PropertiesRegistry.ENTITY_COLOR_RED_MAX).getAttributeValue(),
            (float)entity.getEntityAttribute(PropertiesRegistry.ENTITY_COLOR_GREEN_MAX).getAttributeValue(),
            (float)entity.getEntityAttribute(PropertiesRegistry.ENTITY_COLOR_BLUE_MAX).getAttributeValue()
         );
         int id = entity.getEntityId();
         GlStateManager.pushMatrix();
         GlStateManager.rotate(mobdeathtime / 2, (float)Math.sin(id), (float)Math.sin(id + 20) / 3.0F, (float)Math.sin(id + 13));
         GlStateManager.translate(0.0F, -mobdeathtime / 30.0F, 0.0F);
         Color.doRender(
            entity,
            event.getX(),
            event.getY(),
            event.getZ(),
            entity.rotationYaw,
            event.getPartialRenderTick(),
            model,
            ClientProxy.coloredacidtex.get(mobdeathtime - 1),
            0.0F,
            false
         );
         GlStateManager.popMatrix();
         GL11.glDisable(3042);
         GL11.glEnable(2896);
         event.setCanceled(true);
         GlStateManager.popMatrix();
      }

      @Override
      public void onLivingUpdate(World world, LivingUpdateEvent event, EntityLivingBase base, double x, double y, double z) {
         if (base.deathTime == 1) {
            world.playSound(
               (EntityPlayer)null,
               x,
               y,
               z,
               Sounds.de_acid,
               SoundCategory.AMBIENT,
               0.8F + DeathEffects.rand.nextFloat() / 5.0F,
               0.9F + DeathEffects.rand.nextFloat() / 5.0F
            );
         }

         float re = (float)base.getEntityAttribute(PropertiesRegistry.ENTITY_COLOR_RED_MAX).getAttributeValue();
         float gr = (float)base.getEntityAttribute(PropertiesRegistry.ENTITY_COLOR_GREEN_MAX).getAttributeValue();
         float bl = (float)base.getEntityAttribute(PropertiesRegistry.ENTITY_COLOR_BLUE_MAX).getAttributeValue();
         Entity acidp = new GUNParticle(
            DeathEffects.acidbubble,
            0.04F + DeathEffects.rand.nextFloat() / 20.0F,
            -0.01F,
            30 + DeathEffects.rand.nextInt(25),
            -1,
            world,
            x,
            y,
            z,
            (float)DeathEffects.rand.nextGaussian() / 30.0F,
            (float)DeathEffects.rand.nextGaussian() / 30.0F,
            (float)DeathEffects.rand.nextGaussian() / 30.0F,
            re + (float)DeathEffects.rand.nextGaussian() / 10.0F,
            gr + (float)DeathEffects.rand.nextGaussian() / 10.0F,
            bl + (float)DeathEffects.rand.nextGaussian() / 10.0F,
            true,
            DeathEffects.rand.nextInt(360),
            false,
            1.1F
         );
         world.spawnEntity(acidp);
      }
   };
   public static DeathEffect DE_DISMEMBER = new DeathEffect() {
      @Override
      public void onRenderLivingPre(Pre event, EntityLivingBase entity) {
         event.setCanceled(true);
      }

      @Override
      public void onLivingUpdate(World world, LivingUpdateEvent event, EntityLivingBase base, double x, double y, double z) {
         if (base.deathTime == 1) {
            BloodType bloodtype = DeathEffects.getBloodType(base);
            world.playSound(
               (EntityPlayer)null,
               x,
               y,
               z,
               bloodtype.dismemberSound,
               SoundCategory.NEUTRAL,
               0.8F + DeathEffects.rand.nextFloat() / 5.0F,
               0.9F + DeathEffects.rand.nextFloat() / 5.0F
            );
            float motionX = (float)base.motionX;
            float motionY = (float)base.motionY;
            float motionZ = (float)base.motionZ;
            DeathEffects.spawnGore(base);

            for (int i = 0; i < Math.min(base.getMaxHealth() / 8.0F, 8.0F); i++) {
               EntityCubicParticle spel = new EntityCubicParticle(
                  DeathEffects.gore,
                  0.01F + base.height / 100.0F,
                  0.02F,
                  20 + DeathEffects.rand.nextInt(10),
                  bloodtype.isGlowing ? 240 : 40 + DeathEffects.rand.nextInt(9),
                  world,
                  x,
                  y + 0.5,
                  z,
                  (float)DeathEffects.rand.nextGaussian() / 13.0F + motionX,
                  (float)DeathEffects.rand.nextGaussian() / 25.0F + 0.2F + motionY,
                  (float)DeathEffects.rand.nextGaussian() / 13.0F + motionZ,
                  bloodtype.red,
                  bloodtype.green,
                  bloodtype.blue,
                  bloodtype.isAdditiveBlend,
                  DeathEffects.rand.nextFloat(),
                  DeathEffects.rand.nextFloat(),
                  DeathEffects.rand.nextFloat(),
                  0.05F,
                  true,
                  -3.0E-4F
               );
               spel.alphaGlowing = bloodtype.isAdditiveBlend;
               world.spawnEntity(spel);
            }

            for (int i = 0; i < Math.min(base.getMaxHealth() / 2.0F, 15.0F); i++) {
               GUNParticle sp = new GUNParticle(
                  DeathEffects.drop,
                  0.07F + DeathEffects.rand.nextFloat() / 20.0F,
                  0.02F,
                  50,
                  bloodtype.isGlowing ? 240 : -1,
                  world,
                  x,
                  y + 0.5,
                  z,
                  (float)DeathEffects.rand.nextGaussian() / 11.0F,
                  (float)DeathEffects.rand.nextGaussian() / 14.0F + 0.3F,
                  (float)DeathEffects.rand.nextGaussian() / 11.0F,
                  bloodtype.red,
                  bloodtype.green,
                  bloodtype.blue,
                  false,
                  DeathEffects.rand.nextInt(360),
                  true,
                  5.0F
               );
               sp.dropMode = true;
               sp.alphaGlowing = bloodtype.isAdditiveBlend;
               base.world.spawnEntity(sp);
               int livt = 50 + DeathEffects.rand.nextInt(50);
               float scalmax = 0.35F + DeathEffects.rand.nextFloat() / 4.0F;
               GUNParticle spelll = new GUNParticle(
                  DeathEffects.blood,
                  0.15F,
                  0.015F,
                  livt,
                  bloodtype.isGlowing ? 240 : -1,
                  world,
                  x,
                  y + 0.8,
                  z,
                  (float)DeathEffects.rand.nextGaussian() / 20.0F + motionX / 2.0F,
                  (float)DeathEffects.rand.nextGaussian() / 22.0F + 0.1F + motionY / 2.0F,
                  (float)DeathEffects.rand.nextGaussian() / 20.0F + motionZ / 2.0F,
                  bloodtype.red,
                  bloodtype.green,
                  bloodtype.blue,
                  true,
                  DeathEffects.rand.nextInt(360),
                  true,
                  5.0F
               );
               spelll.dropMode = true;
               if (bloodtype.isAdditiveBlend) {
                  spelll.alphaGlowing = true;
               } else {
                  spelll.acidRender = true;
               }

               spelll.alphaTickAdding = -1.0F / livt;
               spelll.scaleTickAdding = 0.1F;
               spelll.scaleMax = scalmax;
               world.spawnEntity(spelll);
            }
         }
      }
   };
   public static DeathEffect DE_ELECTRIC = new DeathEffect() {
      @Override
      public void onRenderLivingPre(Pre event, EntityLivingBase entity) {
         RenderLivingBase rend = event.getRenderer();
         ModelBase model = rend.getMainModel();
         ResourceLocation retex = null;

         for (Method metod : rend.getClass().getDeclaredMethods()) {
            if (metod.getReturnType() == ResourceLocation.class) {
               metod.setAccessible(true);

               try {
                  ResourceLocation resour = (ResourceLocation)metod.invoke(rend, entity);
                  if (resour != null) {
                     retex = resour;
                  }
               } catch (Exception var11) {
                  var11.printStackTrace();
               }
            }
         }

         if (retex != null) {
            ShaderMain.InverseShader.start();
            GlStateManager.pushMatrix();
            GL11.glDepthMask(false);
            GL11.glEnable(3042);
            Color.doRender(entity, event.getX(), event.getY(), event.getZ(), entity.rotationYaw, event.getPartialRenderTick(), model, retex, 0.0F, true);
            GL11.glDisable(3042);
            GL11.glDepthMask(true);
            event.setCanceled(true);
            GlStateManager.popMatrix();
            ShaderMain.InverseShader.stop();
         }
      }

      @Override
      public void onLivingUpdate(World world, LivingUpdateEvent event, EntityLivingBase base, double x, double y, double z) {
         if (base.deathTime == 1) {
            world.playSound((EntityPlayer)null, x, y, z, Sounds.de_electric, SoundCategory.NEUTRAL, 0.8F + DeathEffects.rand.nextFloat() / 5.0F, 1.1F);
         }

         if (base.deathTime < 18 && base.deathTime > 1) {
            GUNParticle bigboom = new GUNParticle(
               DeathEffects.deelectric,
               base.height,
               0.0F,
               1,
               240,
               world,
               x,
               y + base.height / 2.0F,
               z,
               0.0F,
               0.0F,
               0.0F,
               1.0F,
               1.0F,
               1.0F,
               true,
               DeathEffects.rand.nextInt(360)
            );
            world.spawnEntity(bigboom);
         }

         if (base.deathTime == 19) {
            Vec3d color = DeathEffects.getBloodColor(base);

            for (int i = 0; i < Math.min(base.getMaxHealth() / 5.0F, 10.0F); i++) {
               Entity spel = new EntityCubicParticle(
                  DeathEffects.gore,
                  0.01F + base.height / 100.0F,
                  0.02F,
                  20 + DeathEffects.rand.nextInt(10),
                  40 + DeathEffects.rand.nextInt(9),
                  world,
                  x,
                  y + 0.5,
                  z,
                  (float)DeathEffects.rand.nextGaussian() / 13.0F,
                  (float)DeathEffects.rand.nextGaussian() / 25.0F + 0.2F,
                  (float)DeathEffects.rand.nextGaussian() / 13.0F,
                  (float)color.x,
                  (float)color.y,
                  (float)color.z,
                  false,
                  DeathEffects.rand.nextFloat(),
                  DeathEffects.rand.nextFloat(),
                  DeathEffects.rand.nextFloat(),
                  0.05F,
                  true,
                  -3.0E-4F
               );
               world.spawnEntity(spel);
            }

            for (int i = 0; i < Math.min(base.getMaxHealth() / 2.0F, 15.0F); i++) {
               GUNParticle sp = new GUNParticle(
                  DeathEffects.drop,
                  0.07F + DeathEffects.rand.nextFloat() / 20.0F,
                  0.02F,
                  50,
                  -1,
                  world,
                  x,
                  y + 0.5,
                  z,
                  (float)DeathEffects.rand.nextGaussian() / 14.0F,
                  (float)DeathEffects.rand.nextGaussian() / 14.0F + 0.3F,
                  (float)DeathEffects.rand.nextGaussian() / 14.0F,
                  (float)color.x,
                  (float)color.y,
                  (float)color.z,
                  false,
                  (int)Math.round(DeathEffects.rand.nextGaussian() * 20.0),
                  true,
                  5.0F
               );
               sp.dropMode = true;
               base.world.spawnEntity(sp);
               Entity spelll = new GUNParticle(
                  DeathEffects.snowflake3,
                  0.57F,
                  0.005F,
                  20 + DeathEffects.rand.nextInt(10),
                  -1,
                  world,
                  x,
                  y + 0.8,
                  z,
                  (float)DeathEffects.rand.nextGaussian() / 20.0F,
                  (float)DeathEffects.rand.nextGaussian() / 22.0F + 0.1F,
                  (float)DeathEffects.rand.nextGaussian() / 20.0F,
                  (float)color.x,
                  (float)color.y,
                  (float)color.z,
                  false,
                  DeathEffects.rand.nextInt(360)
               );
               world.spawnEntity(spelll);
            }
         }
      }
   };
   public static DeathEffect DE_FIRE = new DeathEffect() {
      @Override
      public void onRenderLivingPre(Pre event, EntityLivingBase entity) {
         ModelBase model = event.getRenderer().getMainModel();
         GlStateManager.pushMatrix();
         GL11.glEnable(3042);
         GL11.glDisable(2896);
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
         Color.doRender(
            entity,
            event.getX(),
            event.getY(),
            event.getZ(),
            entity.rotationYaw,
            event.getPartialRenderTick(),
            model,
            ClientProxy.firedetex.get(MathHelper.clamp(entity.deathTime - 1, 0, 18)),
            0.0F,
            false
         );
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         GL11.glDisable(3042);
         GL11.glEnable(2896);
         event.setCanceled(true);
         GlStateManager.popMatrix();
      }

      @Override
      public void onLivingUpdate(World world, LivingUpdateEvent event, EntityLivingBase base, double x, double y, double z) {
         if (base.deathTime == 1) {
            world.playSound(
               (EntityPlayer)null,
               x,
               y,
               z,
               Sounds.de_fire,
               SoundCategory.AMBIENT,
               0.8F + DeathEffects.rand.nextFloat() / 5.0F,
               0.9F + DeathEffects.rand.nextFloat() / 5.0F
            );
         }

         if (base.deathTime > 0 && base.deathTime < 15) {
            AxisAlignedBB aabb = base.getEntityBoundingBox();

            for (int i = 0; i < Math.min(base.width + base.height, 10.0F); i++) {
               double rY = aabb.minY + (aabb.maxY - aabb.minY) * DeathEffects.rand.nextDouble();
               double rZ = aabb.minZ + (aabb.maxZ - aabb.minZ) * DeathEffects.rand.nextDouble();
               double rX = aabb.minX + (aabb.maxX - aabb.minX) * DeathEffects.rand.nextDouble();
               if (DeathEffects.rand.nextFloat() < 0.7) {
                  SparkleSubparticle part = new SparkleSubparticle(
                     rX,
                     rY,
                     rZ,
                     0.02F + DeathEffects.rand.nextFloat() * 0.01F,
                     90 + DeathEffects.rand.nextInt(40) + (DeathEffects.rand.nextFloat() < 0.15 ? DeathEffects.rand.nextInt(70) + 40 : 0),
                     (float)(0.01 * DeathEffects.rand.nextGaussian()),
                     (float)(0.01 * DeathEffects.rand.nextGaussian() + 0.01),
                     (float)(0.01 * DeathEffects.rand.nextGaussian()),
                     0.0F
                  );
                  SparkleSubparticle.particles.add(part);
               } else {
                  SparkleSubparticle part = new SparkleSubparticle(
                     rX, rY, rZ, 0.02F + DeathEffects.rand.nextFloat() * 0.01F, 80 + DeathEffects.rand.nextInt(20), 0.0F, -0.02F, 0.0F, 0.1F
                  );
                  SparkleSubparticle.particles.add(part);
               }
            }
         }
      }
   };
   public static DeathEffect DE_CUT = new DeathEffect() {
      @Override
      public void onRenderLivingPre(Pre event, EntityLivingBase entity) {
         RenderLivingBase rend = event.getRenderer();
         int randomId = entity.getUniqueID().hashCode();
         float modelHeight = entity.height * 9.0F;
         boolean head = randomId % 2 == 0;
         ModelBase model = rend.getMainModel();
         if (head) {
            List<ModelRenderer> headboxes = DeathEffects.findModelHeads(entity, model);
            if (!headboxes.isEmpty()) {
               for (ModelRenderer lbox : headboxes) {
                  lbox.isHidden = true;
               }
            }
         } else {
            List<ModelRenderer> nolegsboxes = DeathEffects.findModelNoLegs(entity, model);
            if (!nolegsboxes.isEmpty()) {
               for (ModelRenderer lbox : nolegsboxes) {
                  lbox.isHidden = true;
               }
            } else {
               head = true;
               List<ModelRenderer> headboxes = DeathEffects.findModelHeads(entity, model);
               if (!headboxes.isEmpty()) {
                  for (ModelRenderer lbox : headboxes) {
                     lbox.isHidden = true;
                  }
               }
            }
         }

         ResourceLocation retex = null;

         for (Method metod : rend.getClass().getDeclaredMethods()) {
            if (metod.getReturnType() == ResourceLocation.class) {
               metod.setAccessible(true);

               try {
                  ResourceLocation resour = (ResourceLocation)metod.invoke(rend, entity);
                  if (resour != null) {
                     retex = resour;
                  }
               } catch (Exception var14) {
                  var14.printStackTrace();
               }
            }
         }

         if (retex != null) {
            GlStateManager.pushMatrix();
            GL11.glEnable(3042);
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
            Color.doRender(entity, event.getX(), event.getY(), event.getZ(), entity.rotationYaw, event.getPartialRenderTick(), model, retex, 0.0F, false);
            GL11.glDisable(3042);
            event.setCanceled(true);
            GlStateManager.popMatrix();
         }

         for (ModelRenderer box : model.boxList) {
            box.isHidden = false;
         }
      }

      @Override
      public void onLivingUpdate(World world, LivingUpdateEvent event, EntityLivingBase base, double x, double y, double z) {
         if (base.deathTime == 1) {
            int randomId = base.getUniqueID().hashCode();
            boolean head = randomId % 2 == 0;
            head = DeathEffects.spawnCutParts(base, head);
            BloodType bloodtype = DeathEffects.getBloodType(base);
            world.playSound(
               (EntityPlayer)null,
               x,
               y,
               z,
               bloodtype.cutSound,
               SoundCategory.NEUTRAL,
               0.9F + DeathEffects.rand.nextFloat() / 5.0F,
               0.9F + DeathEffects.rand.nextFloat() / 5.0F
            );
            float motionX = (float)base.motionX / (head ? 6.0F : 2.0F) + (float)DeathEffects.rand.nextGaussian() / 20.0F;
            float motionY = (float)base.motionY / (head ? 6.0F : 2.0F) + (float)DeathEffects.rand.nextGaussian() / 25.0F + 0.3F;
            float motionZ = (float)base.motionZ / (head ? 6.0F : 2.0F) + (float)DeathEffects.rand.nextGaussian() / 20.0F;

            for (int i = 0; i < Math.min(base.getMaxHealth() / 3.0F, 15.0F); i++) {
               GUNParticle sp = new GUNParticle(
                  DeathEffects.drop,
                  0.07F + DeathEffects.rand.nextFloat() / 20.0F,
                  0.02F,
                  50,
                  bloodtype.isGlowing ? 240 : -1,
                  world,
                  x,
                  y + 0.5,
                  z,
                  (float)DeathEffects.rand.nextGaussian() / 11.0F,
                  (float)DeathEffects.rand.nextGaussian() / 14.0F + 0.3F,
                  (float)DeathEffects.rand.nextGaussian() / 11.0F,
                  bloodtype.red,
                  bloodtype.green,
                  bloodtype.blue,
                  false,
                  DeathEffects.rand.nextInt(360),
                  true,
                  5.0F
               );
               sp.dropMode = true;
               sp.alphaGlowing = bloodtype.isAdditiveBlend;
               base.world.spawnEntity(sp);
               int livt = 50 + DeathEffects.rand.nextInt(50);
               float scalmax = 0.35F + DeathEffects.rand.nextFloat() / 4.0F;
               GUNParticle spelll = new GUNParticle(
                  DeathEffects.blood,
                  0.15F,
                  0.015F,
                  livt,
                  bloodtype.isGlowing ? 240 : -1,
                  world,
                  x,
                  y + 0.8,
                  z,
                  motionX,
                  motionY,
                  motionZ,
                  bloodtype.red,
                  bloodtype.green,
                  bloodtype.blue,
                  true,
                  DeathEffects.rand.nextInt(360),
                  true,
                  5.0F
               );
               spelll.dropMode = true;
               if (bloodtype.isAdditiveBlend) {
                  spelll.alphaGlowing = true;
               } else {
                  spelll.acidRender = true;
               }

               spelll.alphaTickAdding = -1.0F / livt;
               spelll.scaleTickAdding = 0.05F;
               spelll.scaleMax = scalmax;
               world.spawnEntity(spelll);
            }
         }
      }
   };
   public static BloodType RED_BLOOD = new BloodType(0.8, 0.1, 0.1);
   public static BloodType SPIDER_BLOOD = new BloodType(0.4, 0.8, 0.5);
   public static BloodType ENDER_BLOOD = new BloodType(0.6, 0.0, 0.6);
   public static BloodType SHULKER_BLOOD = new BloodType(0.6, 0.4, 0.8);
   public static BloodType SKELETON_BLOOD = new BloodType(0.7, 0.7, 0.7);
   public static BloodType BLACK_STRAP_BLOOD = new BloodType(1310720);
   public static BloodType NETHER_BLOOD = new BloodType(13450544);
   public static BloodType LAVA_BLOOD = new BloodType(16757760).setGlowing();
   public static BloodType DEMONIC_VIOLET_BLOOD = new BloodType(4001607);
   public static BloodType CINDER_BLOOD = new BloodType(15095331);
   public static BloodType ICE_BLOOD = new BloodType(10994431);
   public static BloodType SNOW_BLOOD = new BloodType(15070207);
   public static BloodType FROSTED_RED_BLOOD = new BloodType(12928626);
   public static BloodType AURORA_BLOOD = new BloodType(65472).setGlowing();
   public static BloodType GARGOYLE_BLOOD = new BloodType(4931173);
   public static BloodType ATORPID_BLOOD = new BloodType(5526350);
   public static BloodType FROSTED_SKELETON_BLOOD = new BloodType(7308947);
   public static BloodType ROTTEN_BLOOD = new BloodType(14236715);
   public static BloodType MUTANT_PURPLE_BLOOD = new BloodType(14236794);
   public static BloodType TEST_TUBE_BLOOD = new BloodType(10409728);
   public static BloodType ROBOT_OIL_BLOOD = new BloodType(1969920);
   public static BloodType ABOMINATION_BLOOD = new BloodType(6476875);
   public static BloodType VINES_BLOOD = new BloodType(2001194);
   public static BloodType GLOWING_NUCLEAR_BLOOD = new BloodType(13563931).setGlowing();
   public static BloodType LARVA_BLOOD = new BloodType(15135162);
   public static BloodType CRYSTAL_BLOOD = new BloodType(16723079).setGlowing();
   public static BloodType WORM_BLOOD = new BloodType(14181476);
   public static BloodType WORM_RED_BLOOD = new BloodType(15682374);
   public static BloodType WEAVER_BLOOD = new BloodType(5141534);
   public static BloodType FISH_BLOOD = new BloodType(11010113);
   public static BloodType OCEAN_BLOOD = new BloodType(46534);
   public static BloodType POLYP_BLOOD = new BloodType(16749272).setGlowing();
   public static BloodType JELLY_BLOOD = new BloodType(11651042);
   public static BloodType SIREN_BLOOD = new BloodType(2433985);
   public static BloodType KRAKEN_BLOOD = new BloodType(46559).setGlowing();
   public static BloodType WIND_BLOOD = new BloodType(15460863).setGlowing().setAdditiveBlend();
   public static BloodType ELECTRIC_BLOOD = new BloodType(12648447).setGlowing().setAdditiveBlend();

   public static void applyDeathEffect(Entity entity, DeathEffect effect, float chance) {
      if (entity instanceof EntityLivingBase) {
         EntityLivingBase entitylivingbase = (EntityLivingBase)entity;
         if (entitylivingbase.getHealth() <= 0.0F && rand.nextFloat() < chance && entitylivingbase.deathTime < 1) {
            applyDeathEffect(entitylivingbase, effect);
         }
      }
   }

   public static void applyDeathEffect(Entity entity, DeathEffect effect) {
      if (!entity.world.isRemote) {
         PacketDEToClients packet = new PacketDEToClients();
         packet.writeargs(entity.getEntityId(), effect.id);
         PacketHandler.sendToAllAround(packet, entity.world, entity.posX, entity.posY, entity.posZ, 64.0);
      }
   }

   public static void spawnGore(EntityLivingBase entity) {
      World world = entity.getEntityWorld();
      if (world.isRemote) {
         boolean pl = entity instanceof EntityPlayer;
         ModelBase model = pl ? playerModel : mainModels.get(entity.getClass());
         ResourceLocation res = pl ? new NetworkPlayerInfo(((EntityPlayer)entity).getGameProfile()).getLocationSkin() : mainTextures.get(entity.getClass());
         double range = entity.height + 0.7;
         List<Entity> listnails = world.getEntitiesWithinAABB(
            Entity.class,
            new AxisAlignedBB(
               entity.posX + range,
               entity.posY + range,
               entity.posZ + range,
               entity.posX - range,
               entity.posY - range,
               entity.posZ - range
            ),
            new Predicate<Entity>() {
               public boolean apply(@Nullable Entity e) {
                  return e == null ? false : e instanceof INailer;
               }
            }
         );
         if (res == null || model == null) {
            Render rend = (Render)Minecraft.getMinecraft().getRenderManager().entityRenderMap.get(entity.getClass());
            boolean nullmodel = (model == null || model.boxList == null || model.boxList.isEmpty()) && rend instanceof RenderLivingBase;
            if (rend != null) {
               if (res == null) {
                  for (Method metod : rend.getClass().getDeclaredMethods()) {
                     if (metod.getReturnType() == ResourceLocation.class) {
                        metod.setAccessible(true);

                        try {
                           ResourceLocation resour = (ResourceLocation)metod.invoke(rend, entity);
                           if (resour != null) {
                              res = resour;
                           }
                        } catch (Exception var21) {
                           var21.printStackTrace();
                        }
                     }
                  }
               }

               if (nullmodel) {
                  model = ((RenderLivingBase)rend).getMainModel();
               }
            }
         }

         if (model != null && res != null) {
            List<ModelRenderer> list = model.boxList;
            if (list != null) {
               for (ModelRenderer box : list) {
                  AxisAlignedBB aabb = entity.getEntityBoundingBox();
                  double x = (aabb.maxX - aabb.minX) * rand.nextFloat() + aabb.minX;
                  double y = (aabb.maxY - aabb.minY) * rand.nextFloat() + aabb.minY;
                  double z = (aabb.maxZ - aabb.minZ) * rand.nextFloat() + aabb.minZ;
                  ParticleGore particle = new ParticleGore(
                     world,
                     res,
                     0.0F,
                     0.04F,
                     150,
                     box,
                     x,
                     y,
                     z,
                     rand.nextGaussian() / 13.0 + entity.motionX * 1.3,
                     rand.nextGaussian() / 13.0 + 0.2F + (entity.onGround ? 0.0 : entity.motionY),
                     rand.nextGaussian() / 13.0 + entity.motionZ * 1.3
                  );
                  boolean rotate = true;
                  particle.rotateX = (float)rand.nextGaussian() * 45.0F;
                  particle.rotateY = (float)rand.nextGaussian() * 45.0F;
                  particle.rotateZ = (float)rand.nextGaussian() * 45.0F;
                  world.spawnEntity(particle);
                  if (!listnails.isEmpty() && rand.nextFloat() < 0.8F) {
                     Entity sh = listnails.get(rand.nextInt(listnails.size()));
                     if (((INailer)sh).canPrickParticle()) {
                        rotate = false;
                        particle.setNailer(sh);
                     }
                  }

                  if (rotate) {
                     particle.rotateXspeed = (float)rand.nextGaussian();
                     particle.rotateYspeed = (float)rand.nextGaussian();
                     particle.rotateZspeed = (float)rand.nextGaussian();
                  }
               }
            }
         }
      }
   }

   public static List<ModelRenderer> findModelHeads(Entity entity, ModelBase model) {
      Map<ModelRenderer, Float> map = new HashMap<>();
      model.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, entity);

      for (ModelRenderer box : model.boxList) {
         map.put(box, box.rotateAngleY);
      }

      model.setRotationAngles(0.0F, 0.0F, 0.0F, 20.0F, 0.0F, 0.0F, entity);
      List<ModelRenderer> list = new ArrayList<>();

      for (ModelRenderer box : model.boxList) {
         if (box.rotateAngleY != map.get(box)) {
            list.add(box);
         }
      }

      return list;
   }

   public static List<ModelRenderer> findModelNoLegs(Entity entity, ModelBase model) {
      float upperHeight = 10000.0F;
      float downHeight = -10000.0F;

      for (ModelRenderer box : model.boxList) {
         if (box.rotationPointY < upperHeight) {
            upperHeight = box.rotationPointY;
         }

         if (box.rotationPointY > downHeight) {
            downHeight = box.rotationPointY;
         }
      }

      float amplitude = downHeight - upperHeight;
      List<ModelRenderer> list = new ArrayList<>();

      for (ModelRenderer box : model.boxList) {
         if (box.rotationPointY < upperHeight + amplitude * 0.6F) {
            list.add(box);
         }
      }

      return list;
   }

   public static boolean spawnCutParts(EntityLivingBase entity, boolean type) {
      World world = entity.getEntityWorld();
      if (world.isRemote) {
         boolean pl = entity instanceof EntityPlayer;
         ModelBase model = pl ? playerModel : mainModels.get(entity.getClass());
         ResourceLocation res = pl ? new NetworkPlayerInfo(((EntityPlayer)entity).getGameProfile()).getLocationSkin() : mainTextures.get(entity.getClass());
         double range = entity.height + 0.7;
         List<Entity> listnails = world.getEntitiesWithinAABB(
            Entity.class,
            new AxisAlignedBB(
               entity.posX + range,
               entity.posY + range,
               entity.posZ + range,
               entity.posX - range,
               entity.posY - range,
               entity.posZ - range
            ),
            new Predicate<Entity>() {
               public boolean apply(@Nullable Entity e) {
                  return e == null ? false : e instanceof INailer;
               }
            }
         );
         if (res == null || model == null) {
            Render rend = (Render)Minecraft.getMinecraft().getRenderManager().entityRenderMap.get(entity.getClass());
            boolean nullmodel = (model == null || model.boxList == null || model.boxList.isEmpty()) && rend instanceof RenderLivingBase;
            if (rend != null) {
               if (res == null) {
                  for (Method metod : rend.getClass().getDeclaredMethods()) {
                     if (metod.getReturnType() == ResourceLocation.class) {
                        metod.setAccessible(true);

                        try {
                           ResourceLocation resour = (ResourceLocation)metod.invoke(rend, entity);
                           if (resour != null) {
                              res = resour;
                           }
                        } catch (Exception var21) {
                           var21.printStackTrace();
                        }
                     }
                  }
               }

               if (nullmodel) {
                  model = ((RenderLivingBase)rend).getMainModel();
               }
            }
         }

         if (model != null && res != null) {
            List<ModelRenderer> list = type ? findModelHeads(entity, model) : findModelNoLegs(entity, model);
            if (!type && list.isEmpty()) {
               list = findModelHeads(entity, model);
               type = true;
            }

            if (list != null && !list.isEmpty()) {
               if (type) {
                  for (ModelRenderer box : list) {
                     double x = entity.posX;
                     double y = entity.posY + entity.height * 0.8;
                     double z = entity.posZ;
                     ParticleGore particle = new ParticleGore(
                        world,
                        res,
                        0.0F,
                        0.04F,
                        150,
                        box,
                        x,
                        y,
                        z,
                        rand.nextGaussian() / 13.0 + entity.motionX * 0.5,
                        rand.nextGaussian() / 13.0 + 0.4F + (entity.onGround ? 0.0 : entity.motionY),
                        rand.nextGaussian() / 13.0 + entity.motionZ * 0.5
                     );
                     boolean rotate = true;
                     particle.rotateX = (float)rand.nextGaussian() * 45.0F;
                     particle.rotateY = (float)rand.nextGaussian() * 45.0F;
                     particle.rotateZ = (float)rand.nextGaussian() * 45.0F;
                     world.spawnEntity(particle);
                     if (!listnails.isEmpty() && rand.nextFloat() < 0.8F) {
                        Entity sh = listnails.get(rand.nextInt(listnails.size()));
                        if (((INailer)sh).canPrickParticle()) {
                           rotate = false;
                           particle.setNailer(sh);
                        }
                     }

                     if (rotate) {
                        particle.rotateXspeed = (float)rand.nextGaussian();
                        particle.rotateYspeed = (float)rand.nextGaussian();
                        particle.rotateZspeed = (float)rand.nextGaussian();
                     }
                  }
               } else {
                  AxisAlignedBB aabb = entity.getEntityBoundingBox();
                  double xx = (aabb.maxX - aabb.minX) * rand.nextFloat() + aabb.minX;
                  double yx = entity.posY + entity.height / 2.0F + entity.height * rand.nextFloat() / 2.0F;
                  double zx = (aabb.maxZ - aabb.minZ) * rand.nextFloat() + aabb.minZ;
                  ParticleGore particlex = new ParticleGore(
                     world,
                     res,
                     0.0F,
                     0.04F,
                     150,
                     null,
                     xx,
                     yx,
                     zx,
                     rand.nextGaussian() / 13.0 + entity.motionX * 0.4,
                     rand.nextGaussian() / 13.0 + 0.5 + (entity.onGround ? 0.0 : entity.motionY),
                     rand.nextGaussian() / 13.0 + entity.motionZ * 0.4
                  );
                  boolean rotatex = true;
                  particlex.rotateX = (float)rand.nextGaussian() * 25.0F + 180.0F;
                  particlex.rotateY = (float)rand.nextGaussian() * 25.0F;
                  particlex.rotateZ = (float)rand.nextGaussian() * 25.0F;
                  particlex.renderCutList = list;
                  world.spawnEntity(particlex);
                  if (!listnails.isEmpty() && rand.nextFloat() < 0.8F) {
                     Entity sh = listnails.get(rand.nextInt(listnails.size()));
                     if (((INailer)sh).canPrickParticle()) {
                        rotatex = false;
                        particlex.setNailer(sh);
                     }
                  }

                  if (rotatex) {
                     particlex.rotateXspeed = (float)rand.nextGaussian();
                     particlex.rotateYspeed = (float)rand.nextGaussian();
                     particlex.rotateZspeed = (float)rand.nextGaussian();
                  }
               }
            }
         }
      }

      return type;
   }

   public static void initMainTextures() {
      for (Class<? extends Entity> entity : Minecraft.getMinecraft().getRenderManager().entityRenderMap.keySet()) {
         Render rend = (Render)Minecraft.getMinecraft().getRenderManager().entityRenderMap.get(entity);
         if (rend != null) {
            for (Method metod : rend.getClass().getDeclaredMethods()) {
               if (metod.getReturnType() == ResourceLocation.class) {
                  metod.setAccessible(true);

                  try {
                     ResourceLocation res = (ResourceLocation)metod.invoke(rend, (Entity)null);
                     if (res != null) {
                        tryAddMainTexture(entity, res);
                     }
                  } catch (Exception var8) {
                  }
               }
            }
         }
      }
   }

   public static void tryAddtoMainModels(Class entityclass, ModelBase model) {
      if (!mainModels.containsKey(entityclass)) {
         mainModels.put(entityclass, model);
      }
   }

   public static void tryAddMainTexture(Class entityclass, ResourceLocation tex) {
      if (!mainTextures.containsKey(entityclass)) {
         mainTextures.put(entityclass, tex);
      }
   }

   public static void addTolistEFFECTshock(EntityLivingBase base) {
      if (!listEFFECTshock.contains(base)) {
         listEFFECTshock.add(base);
      }
   }

   public static List<EntityLivingBase> getlistEFFECTshock() {
      return listEFFECTshock;
   }

   public static Vec3d getBloodColor(EntityLivingBase base) {
      BloodType typee = getBloodType(base);
      return new Vec3d(typee.red, typee.green, typee.blue);
   }

   public static BloodType getBloodType(EntityLivingBase base) {
      if (base instanceof AbstractMob) {
         return ((AbstractMob)base).getBloodType();
      } else if (base instanceof EntityCreeper) {
         return new BloodType(0.2, 1.0, 0.2);
      } else if (base instanceof EntityPigZombie) {
         return NETHER_BLOOD;
      } else if (base instanceof EntityWither || base instanceof EntityWitherSkeleton) {
         return new BloodType(0.05, 0.05, 0.05);
      } else if (base instanceof AbstractSkeleton || base instanceof EntitySkeletonHorse) {
         return SKELETON_BLOOD;
      } else if (base instanceof EntityGhast || base instanceof EntityVex) {
         return new BloodType(0.8, 0.8, 0.8);
      } else if (base instanceof EntityBlaze) {
         return new BloodType(1.0, 0.8, 0.0).setGlowing();
      } else if (base instanceof EntityMagmaCube) {
         return new BloodType(1.0, 0.4, 0.0).setGlowing();
      } else if (base instanceof EntityEnderman || base instanceof EntityEndermite) {
         return ENDER_BLOOD;
      } else if (base instanceof EntitySlime) {
         return new BloodType(0.5, 1.0, 0.65);
      } else if (base instanceof EntitySquid) {
         return new BloodType(0.1, 0.7, 0.76);
      } else if (base instanceof EntityIronGolem) {
         return new BloodType(0.8, 0.8, 0.8, true, false);
      } else if (base instanceof EntitySpider) {
         return SPIDER_BLOOD;
      } else if (base instanceof EntityShulker) {
         return SPIDER_BLOOD;
      } else {
         return base instanceof EntitySilverfish ? new BloodType(0.4, 0.8, 0.75) : RED_BLOOD;
      }
   }

   protected static float interpolateRotation(float prevYawOffset, float yawOffset, float partialTicks) {
      float f = yawOffset - prevYawOffset;

      while (f < -180.0F) {
         f += 360.0F;
      }

      while (f >= 180.0F) {
         f -= 360.0F;
      }

      return prevYawOffset + partialTicks * f;
   }

   protected static void renderModel(
      EntityLivingBase entitylivingbaseIn,
      float limbSwing,
      float limbSwingAmount,
      float ageInTicks,
      float netHeadYaw,
      float headPitch,
      float scaleFactor,
      ModelBase mainModel
   ) {
      mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
   }

   protected static void renderLivingAt(EntityLivingBase entityLivingBaseIn, double x, double y, double z) {
      GlStateManager.translate((float)x, (float)y, (float)z);
   }

   protected static float handleRotationFloat(EntityLivingBase livingBase, float partialTicks) {
      return livingBase.ticksExisted + partialTicks;
   }

   protected static float getSwingProgress(EntityLivingBase livingBase, float partialTickTime) {
      return livingBase.getSwingProgress(partialTickTime);
   }

   protected static void applyRotations(EntityLivingBase entityLiving, float ageInTicks, float rotationYaw, float partialTicks) {
      GlStateManager.rotate(180.0F - rotationYaw, 0.0F, 1.0F, 0.0F);
      if (entityLiving.deathTime > 0) {
         float f = (entityLiving.deathTime + partialTicks - 1.0F) / 20.0F * 1.6F;
         f = MathHelper.sqrt(f);
         if (f > 1.0F) {
            f = 1.0F;
         }

         GlStateManager.rotate(f * 90.0F, 0.0F, 0.0F, 1.0F);
      } else {
         String s = TextFormatting.getTextWithoutFormattingCodes(entityLiving.getName());
         if (s != null
            && ("Dinnerbone".equals(s) || "Grumm".equals(s))
            && (!(entityLiving instanceof EntityPlayer) || ((EntityPlayer)entityLiving).isWearing(EnumPlayerModelParts.CAPE))) {
            GlStateManager.translate(0.0F, entityLiving.height + 0.1F, 0.0F);
            GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
         }
      }
   }

   public static float prepareScale(EntityLivingBase entitylivingbaseIn, float partialTicks) {
      GlStateManager.enableRescaleNormal();
      GlStateManager.scale(-1.0F, -1.0F, 1.0F);
      float f = 0.0625F;
      GlStateManager.translate(0.0F, -1.501F, 0.0F);
      return 0.0625F;
   }
}
