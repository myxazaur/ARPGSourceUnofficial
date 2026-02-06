package com.Vivern.Arpg.elements;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import baubles.api.render.IRenderBauble;
import com.Vivern.Arpg.elements.models.PhoenixGhostCapeModel;
import com.Vivern.Arpg.elements.models.PhoenixGhostModel;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Keys;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.SuperKnockback;
import com.Vivern.Arpg.proxy.ClientProxy;
import com.Vivern.Arpg.renders.GUNParticle;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderPlayerEvent.Pre;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@EventBusSubscriber(
   modid = "arpg"
)
public class PhoenixGhostCape extends Item implements IBauble, IRenderBauble {
   ResourceLocation sparkle = new ResourceLocation("arpg:textures/sparkle.png");
   ResourceLocation largesmoke = new ResourceLocation("arpg:textures/largesmoke.png");
   private final PhoenixGhostCapeModel model = new PhoenixGhostCapeModel();
   private final ResourceLocation textur = new ResourceLocation("arpg:textures/phoenix_ghost_cape_model_tex.png");

   public PhoenixGhostCape() {
      this.setRegistryName("phoenix_ghost_cape");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("phoenix_ghost_cape");
      this.setMaxDamage(10);
      this.setMaxStackSize(1);
   }

   @Override
   public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, RenderType type, float partialTicks) {
      if (type == RenderType.BODY) {
         float plrotation = MathHelper.wrapDegrees(player.rotationYaw);
         float mx = (float)player.motionX;
         float mz = (float)player.motionZ;
         float cosangle = 0.0F;
         float tangle = 0.0F;
         float rotate = 0.0F;
         float excl = 0.0F;
         float moti = (float)Math.sqrt(mx * mx + mz * mz);
         if (moti == 0.0F) {
            rotate = 0.0F;
         } else {
            cosangle = mz / moti;
            tangle = (float)Math.toDegrees(Math.acos(cosangle));
            if (mx > 0.0F) {
               tangle = -tangle;
            }

            excl = Math.abs(tangle - plrotation);
            excl = Math.min(excl, 360.0F - excl);
            rotate = -(moti * (excl - 90.0F) / 70.0F);
         }

         rotate = Math.min(0.35F, rotate);
         rotate = (float)(rotate + -player.motionY / 7.0);
         rotate = Math.max(-0.06F, rotate);
         this.model.setRotateAngle(this.model.shape15, rotate, 0.0F, 0.0F);
         this.model.setRotateAngle(this.model.shape15_3, rotate, 0.0F, 0.0F);
         this.model.setRotateAngle(this.model.shape15_4, rotate, 0.0F, 0.0F);
         this.model.setRotateAngle(this.model.shape15_5, rotate, 0.0F, 0.0F);
         this.model.setRotateAngle(this.model.shape15_6, rotate, 0.0F, 0.0F);
         this.model.setRotateAngle(this.model.shape15_7, rotate, 0.0F, 0.0F);
         GlStateManager.pushMatrix();
         Helper.rotateIfSneaking(player);
         Minecraft.getMinecraft().renderEngine.bindTexture(this.textur);
         this.model.render(player, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.065F);
         GlStateManager.popMatrix();
      }
   }

   @Override
   public BaubleType getBaubleType(ItemStack itemstack) {
      return BaubleType.BODY;
   }

   @SideOnly(Side.CLIENT)
   public void Effects(World world, EntityPlayer player, boolean bom1, boolean bom2) {
      float param1 = (float)(Math.sin(player.ticksExisted / 5) / 2.0);
      float param2 = (float)(Math.cos(player.ticksExisted / 4) / 3.0);
      float param3 = (float)(Math.sin(player.ticksExisted / 6) / 2.0);
      Entity bigboom = new GUNParticle(
         this.sparkle,
         0.1F + (float)itemRand.nextGaussian() / 25.0F,
         -0.001F,
         30,
         240,
         world,
         player.posX + param1,
         player.posY + 1.0 + param2,
         player.posZ + param3,
         0.0F,
         0.0F,
         0.0F,
         1.0F,
         0.8F + (float)itemRand.nextGaussian() / 5.0F,
         1.0F,
         true,
         0
      );
      world.spawnEntity(bigboom);
      Entity bigboom2 = new GUNParticle(
         this.sparkle,
         0.1F + (float)itemRand.nextGaussian() / 25.0F,
         -0.001F,
         30,
         240,
         world,
         player.posX + param2,
         player.posY + 1.0 + param1,
         player.posZ + param3,
         0.0F,
         0.0F,
         0.0F,
         1.0F,
         0.8F + (float)itemRand.nextGaussian() / 5.0F,
         1.0F,
         true,
         0
      );
      world.spawnEntity(bigboom2);
      Entity bigboom3 = new GUNParticle(
         this.sparkle,
         0.1F + (float)itemRand.nextGaussian() / 25.0F,
         -0.001F,
         30,
         240,
         world,
         player.posX + param2,
         player.posY + 1.0 + param3,
         player.posZ + param1,
         0.0F,
         0.0F,
         0.0F,
         1.0F,
         0.8F + (float)itemRand.nextGaussian() / 5.0F,
         1.0F,
         true,
         0
      );
      world.spawnEntity(bigboom3);
      if (bom1) {
         for (int ss = 0; ss < 25; ss++) {
            Entity bigsmoke = new GUNParticle(
               this.largesmoke,
               0.6F,
               0.0F,
               14,
               60,
               world,
               player.posX,
               player.posY + 1.0,
               player.posZ,
               (float)itemRand.nextGaussian() / 10.0F,
               (float)itemRand.nextGaussian() / 10.0F,
               (float)itemRand.nextGaussian() / 10.0F,
               1.0F,
               1.0F,
               1.0F,
               true,
               0
            );
            world.spawnEntity(bigsmoke);
         }

         world.playSound(
            (EntityPlayer)null,
            player.posX,
            player.posY,
            player.posZ,
            Sounds.phoenix_ghost,
            SoundCategory.AMBIENT,
            0.6F,
            0.7F + itemRand.nextFloat() / 4.0F
         );
      }

      if (bom2) {
         for (int ss = 0; ss < 15; ss++) {
            Entity bigsmoke = new GUNParticle(
               this.largesmoke,
               0.4F,
               0.0F,
               10,
               60,
               world,
               player.posX,
               player.posY + 1.0,
               player.posZ,
               (float)itemRand.nextGaussian() / 15.0F,
               (float)itemRand.nextGaussian() / 15.0F,
               (float)itemRand.nextGaussian() / 15.0F,
               1.0F,
               1.0F,
               1.0F,
               true,
               0
            );
            world.spawnParticle(
               EnumParticleTypes.FLAME,
               player.posX,
               player.posY + 1.0,
               player.posZ,
               (float)itemRand.nextGaussian() / 25.0F,
               (float)itemRand.nextGaussian() / 25.0F,
               (float)itemRand.nextGaussian() / 25.0F,
               new int[0]
            );
            world.spawnEntity(bigsmoke);
         }

         world.playSound(
            (EntityPlayer)null,
            player.posX,
            player.posY,
            player.posZ,
            Sounds.fire,
            SoundCategory.AMBIENT,
            0.9F,
            0.8F + itemRand.nextFloat() / 5.0F
         );
      }
   }

   @Override
   public void onWornTick(ItemStack itemstack, EntityLivingBase entityIn) {
      if (entityIn instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)entityIn;
         boolean click = GameSettings.isKeyDown(Keys.SPRINT);
         float damage = itemstack.getItemDamage();
         if (player.ticksExisted % 39 == 0) {
            player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 40, 0, true, true));
         }

         if (player.onGround && !click) {
            itemstack.setItemDamage(0);
         }

         if (!player.getCooldownTracker().hasCooldown(this) && !player.onGround && click && damage == 0.0F) {
            itemstack.damageItem(1, entityIn);
            this.Effects(player.world, player, true, false);
         }

         if (damage > 0.0F && damage < 10.0F) {
            if (itemstack.getItemDamage() == 9) {
               this.Effects(player.world, player, false, true);
               player.getCooldownTracker().setCooldown(this, 40);
            }

            itemstack.damageItem(1, entityIn);
            this.Effects(player.world, player, false, false);
            float rotationYawIn = player.rotationYaw;
            float rotationPitchIn = player.rotationPitch;
            float velocity = 1.3F;
            float x = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0))
               * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
            float y = -MathHelper.sin(rotationPitchIn * (float) (Math.PI / 180.0));
            float z = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
            float f = MathHelper.sqrt(x * x + y * y + z * z);
            x /= f;
            y /= f;
            z /= f;
            x *= velocity;
            y *= velocity;
            z *= velocity;
            player.motionX = x;
            player.motionY = y;
            player.motionZ = z;
            AxisAlignedBB axisalignedbb = new AxisAlignedBB(
               player.posX - 0.5,
               player.posY,
               player.posZ - 0.5,
               player.posX + 0.5,
               player.posY + 2.0,
               player.posZ + 0.5
            );
            List<EntityLivingBase> list = player.world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
            if (!list.isEmpty()) {
               for (EntityLivingBase entitylivingbase : list) {
                  if (entitylivingbase != player) {
                     SuperKnockback.applyKnockback(entitylivingbase, 0.3F, player.posX, player.posY, player.posZ);
                     IAttributeInstance entityAttribute = entitylivingbase.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE);
                     double baseValue = entityAttribute.getBaseValue();
                     entityAttribute.setBaseValue(1.0);
                     entitylivingbase.attackEntityFrom(DamageSource.causeExplosionDamage(player), 7.7F);
                     entitylivingbase.hurtResistantTime = 0;
                     entityAttribute.setBaseValue(baseValue);
                  }
               }
            }

            if (!click) {
               itemstack.setItemDamage(10);
               this.Effects(player.world, player, false, true);
               player.getCooldownTracker().setCooldown(this, 40);
            }
         }
      }
   }

   @SubscribeEvent
   public static void onPlayerRender(Pre e) {
      ItemStack stack = BaublesApi.getBaubles(e.getEntityPlayer()).getStackInSlot(5);
      if (stack.getItem() == ItemsRegister.PHOENIXGHOSTCAPE && stack.getItemDamage() != 0 && stack.getItemDamage() != 10) {
         e.setCanceled(true);
         float partialTicks = e.getPartialRenderTick();
         PhoenixGhostModel model = ClientProxy.phoenixghostmodel;
         ResourceLocation textur = new NetworkPlayerInfo(Minecraft.getMinecraft().player.getGameProfile()).getLocationSkin();
         EntityPlayer entity = e.getEntityPlayer();
         int ticks = entity.ticksExisted;
         model.setRotateAngle(model.body, 1.3658947F + (float)Math.sin(ticks / 2) / 7.0F, 0.0F, 0.0F);
         float scale1 = (float)((entity.motionX + entity.motionY + entity.motionZ) / 3.0);
         GlStateManager.pushMatrix();
         GL11.glDisable(2884);
         GL11.glEnable(3042);
         GL11.glDisable(2896);
         GlStateManager.rotate(-entity.rotationYaw, 0.0F, 1.0F, 0.0F);
         GlStateManager.rotate(entity.rotationPitch, 1.0F, 0.0F, 0.0F);
         GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.translate(0.0F, -1.0F, 0.0F);
         Minecraft.getMinecraft().renderEngine.bindTexture(textur);
         GlStateManager.color(2.5F, 0.3F, 0.3F, 0.7F);
         model.render(entity, 1.0F, 1.0F + scale1, 1.0F, 1.0F, 1.0F, 0.05F);
         GL11.glEnable(2884);
         GL11.glDisable(3042);
         GL11.glEnable(2896);
         GlStateManager.popMatrix();
      }
   }
}
