package com.vivern.arpg.elements;

import baubles.api.render.IRenderBauble;
import com.vivern.arpg.elements.models.ToxicWingsModel;
import com.vivern.arpg.main.Booom;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.IAttributedBauble;
import com.vivern.arpg.main.NBTHelper;
import com.vivern.arpg.main.PropertiesRegistry;
import com.vivern.arpg.main.Sounds;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.Random;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ToxicWings extends AbstractWings implements IAttributedBauble, IRenderBauble {
   public static ToxicWingsModel model = new ToxicWingsModel();
   public static ResourceLocation texture = new ResourceLocation("arpg:textures/toxic_wings_model_tex.png");

   public ToxicWings() {
      this.setRegistryName("toxic_wings");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("toxic_wings");
      this.setMaxDamage(4500);
      this.setMaxStackSize(1);
      this.flapPeriod = 10;
      this.flapPeriodFloat = 1.9F;
   }

   @Override
   public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, RenderType type, float partialTicks) {
      if (type == RenderType.BODY) {
         float flytime = GetMOP.partial((float)NBTHelper.GetNBTint(stack, "cflytime"), (float)NBTHelper.GetNBTint(stack, "prevcflytime"), partialTicks);
         float flyupStarted = GetMOP.partial(
            (float)NBTHelper.GetNBTint(stack, "flyupStarted"), (float)NBTHelper.GetNBTint(stack, "prevflyupStarted"), partialTicks
         );
         float glidingRaw = GetMOP.partial(NBTHelper.GetNBTfloat(stack, "gliding"), NBTHelper.GetNBTfloat(stack, "prevgliding"), partialTicks);
         float expand = (-player.rotationPitch + 90.0F) / 180.0F;
         float nofly = 0.0F;
         if (!player.isElytraFlying()) {
            nofly = 1.0F;
         } else {
            float ticksElytraFlying = player.getTicksElytraFlying();
            if (ticksElytraFlying > 0.0F) {
               ticksElytraFlying += partialTicks;
            }

            nofly = Math.max(0.0F, 1.0F - ticksElytraFlying / 20.0F);
         }

         expand /= 1.0F + nofly * 1.5F;
         int maxflytime = this.getMaxFlyTime(stack);
         float upward = GetMOP.getfromto(flytime, 0.0F, 5.0F) * GetMOP.getfromto(flyupStarted, 0.0F, (float)(this.flapPeriod / 2));
         float upwardProgress = flytime / this.flapPeriodFloat;
         float gliding = glidingRaw / 8.0F * nofly;
         GlStateManager.pushMatrix();
         RenderHelper.enableStandardItemLighting();
         GlStateManager.tryBlendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
         GlStateManager.enableBlend();
         GlStateManager.disableCull();
         GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
         Helper.rotateIfSneaking(player);
         if (player.isSneaking()) {
            GlStateManager.translate(0.0F, -0.4F, 0.2F);
         }

         Minecraft.getMinecraft().renderEngine.bindTexture(texture);
         GlStateManager.scale(0.08F, 0.08F, 0.08F);
         GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.translate(0.0F, -0.5F, -0.5F);
         float livingAnimation = MathHelper.sin((player.ticksExisted + partialTicks) / 20.0F) * 0.02F;
         model.renderWings(
            player,
            Math.max(expand, gliding * 0.65F) + livingAnimation,
            nofly,
            upward * nofly,
            upwardProgress,
            gliding,
            player.limbSwing,
            GetMOP.partial(player.limbSwingAmount, player.prevLimbSwingAmount, partialTicks),
            1.0F
         );
         GlStateManager.disableBlend();
         GlStateManager.enableCull();
         GlStateManager.popMatrix();
      }
   }

   @Override
   public double getMaxUpwardMotion(ItemStack itemstack) {
      return 0.55;
   }

   @Override
   public double getUpwardMotionAdd(ItemStack itemstack) {
      return 0.12;
   }

   @Override
   public double getFallingMotionAdd(ItemStack itemstack) {
      return 0.3;
   }

   @Override
   public int getMaxFlyTime(ItemStack stack) {
      return 50;
   }

   @Override
   public double getFallingMotionSlowdown(ItemStack itemstack) {
      return 0.82;
   }

   @Override
   public void startElytraSound(EntityPlayerSP player) {
      Minecraft.getMinecraft().getSoundHandler().playSound(new ToxicWingsSound(player));
   }

   @Override
   public void tryPlayFlapSound(EntityPlayer player, ItemStack itemstack, int clientFlyTime) {
      NBTHelper.GiveNBTboolean(itemstack, false, "soundPlayed");
      boolean soundPlayed = NBTHelper.GetNBTboolean(itemstack, "soundPlayed");
      double soundCycle = clientFlyTime / this.flapPeriodFloat % Math.PI;
      if (soundCycle >= 1.8) {
         if (!soundPlayed) {
            this.playFlapSound(player);
            NBTHelper.SetNBTboolean(itemstack, true, "soundPlayed");
         }
      } else {
         NBTHelper.SetNBTboolean(itemstack, false, "soundPlayed");
      }
   }

   @Override
   public void playFlapSound(EntityPlayer player) {
      player.world
         .playSound(
            player.posX,
            player.posY,
            player.posZ,
            Sounds.wings,
            SoundCategory.PLAYERS,
            0.5F,
            1.05F + itemRand.nextFloat() * 0.1F,
            false
         );
   }

   @Override
   public IAttribute getAttribute() {
      return PropertiesRegistry.JUMP_HEIGHT;
   }

   @Override
   public double value() {
      return 0.05;
   }

   @Override
   public int operation() {
      return 0;
   }

   @Override
   public String itemName() {
      return "toxic_wings";
   }

   @Override
   public boolean useMultimap() {
      return true;
   }

   @Override
   public Multimap<String, AttributeModifier> getAttributeModifiers(EntityPlayer player, int equipmentSlot, ItemStack itemstack) {
      Multimap<String, AttributeModifier> multimap = HashMultimap.create();
      UUID uuid = UUID.fromString("CB2F4" + equipmentSlot + "D3-64" + equipmentSlot + "A-4F78-A497-9C56A33DB" + equipmentSlot + "BB");
      multimap.put(PropertiesRegistry.AIRBORNE_MOBILITY.getName(), new AttributeModifier(uuid, "airborn mobility modifier", 0.04, 0));
      return multimap;
   }

   @SideOnly(Side.CLIENT)
   public class ToxicWingsSound extends MovingSound {
      private final EntityPlayerSP player;
      private int time;
      Random rand = new Random();

      public ToxicWingsSound(EntityPlayerSP p_i47113_1_) {
         super(Sounds.toxic_wings_flying, SoundCategory.PLAYERS);
         this.player = p_i47113_1_;
         this.repeat = true;
         this.repeatDelay = 0;
         this.volume = 0.1F;
      }

      public void update() {
         this.time++;
         if (!this.player.isDead && (this.time <= 20 || this.player.isElytraFlying())) {
            this.xPosF = (float)this.player.posX;
            this.yPosF = (float)this.player.posY;
            this.zPosF = (float)this.player.posZ;
            float f = MathHelper.sqrt(
               this.player.motionX * this.player.motionX
                  + this.player.motionZ * this.player.motionZ
                  + this.player.motionY * this.player.motionY
            );
            float f1 = f / 2.0F;
            if (f1 > 0.7F && Booom.lastTick == 0) {
               Booom.lastTick = 8;
               Booom.frequency = 4.0F;
               Booom.x = (float)this.rand.nextGaussian();
               Booom.y = (float)this.rand.nextGaussian();
               Booom.z = (float)this.rand.nextGaussian();
               Booom.power = f1 / 25.0F;
            }

            if (f >= 0.01) {
               this.volume = MathHelper.clamp(f1 * f1, 0.0F, 1.0F);
            } else {
               this.volume = 0.0F;
            }

            if (this.time < 20) {
               this.volume = 0.0F;
            } else if (this.time < 40) {
               this.volume = (float)(this.volume * ((this.time - 20) / 20.0));
            }

            float f2 = 0.8F;
            if (this.volume > 0.8F) {
               this.pitch = 1.0F + (this.volume - 0.8F);
            } else {
               this.pitch = 1.0F;
            }
         } else {
            this.donePlaying = true;
         }
      }
   }
}
