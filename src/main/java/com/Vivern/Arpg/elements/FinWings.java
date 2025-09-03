//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import baubles.api.render.IRenderBauble;
import com.Vivern.Arpg.elements.models.AbstractMobModel;
import com.Vivern.Arpg.elements.models.FinWingsModel;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.IAttributedBauble;
import com.Vivern.Arpg.main.Keys;
import com.Vivern.Arpg.main.PropertiesRegistry;
import com.Vivern.Arpg.main.Sounds;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;

public class FinWings extends AbstractWings implements IAttributedBauble, IRenderBauble {
   public static FinWingsModel model = new FinWingsModel();
   public static ResourceLocation texture = new ResourceLocation("arpg:textures/fin_wings_model_tex.png");
   public static ResourceLocation overlay = new ResourceLocation("arpg:textures/fin_wings_model_tex_overlay.png");

   public FinWings() {
      this.setRegistryName("fin_wings");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("fin_wings");
      this.setMaxDamage(6000);
      this.setMaxStackSize(1);
      this.flapPeriod = 10;
      this.flapPeriodFloat = 1.9F;
   }

   @Override
   public void onFlyingTick(ItemStack itemstack, EntityPlayer player, boolean likeElytra) {
      super.onFlyingTick(itemstack, player, likeElytra);
      if (player.isInWater() && Keys.isKeyPressed(player, Keys.FORWARD)) {
         Vec3d vec = GetMOP.PitchYawToVec3d(player.rotationPitch, player.rotationYaw);
         double sped = 0.1;
         player.motionX = player.motionX + vec.x * sped;
         player.motionY = player.motionY + vec.y * sped;
         player.motionZ = player.motionZ + vec.z * sped;
      }
   }

   @Override
   public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, RenderType type, float partialTicks) {
      AbstractMobModel.light(60, true);
      this.renderDefaultWings(texture, model, stack, player, type, partialTicks, overlay, 220);
      AbstractMobModel.returnlight();
   }

   @Override
   public double getMaxUpwardMotion(ItemStack itemstack) {
      return 0.75;
   }

   @Override
   public double getUpwardMotionAdd(ItemStack itemstack) {
      return 0.1;
   }

   @Override
   public double getFallingMotionAdd(ItemStack itemstack) {
      return 0.4;
   }

   @Override
   public int getMaxFlyTime(ItemStack stack) {
      return 100;
   }

   @Override
   public double getFallingMotionSlowdown(ItemStack itemstack) {
      return 0.7;
   }

   @Override
   public void startElytraSound(EntityPlayerSP player) {
      Minecraft.getMinecraft().getSoundHandler().playSound(new WingsSound(player, Sounds.toxic_wings_flying));
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
            0.85F + itemRand.nextFloat() * 0.1F,
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
      return "fin_wings";
   }

   @Override
   public boolean useMultimap() {
      return true;
   }

   @Override
   public Multimap<String, AttributeModifier> getAttributeModifiers(EntityPlayer player, int equipmentSlot, ItemStack itemstack) {
      Multimap<String, AttributeModifier> multimap = HashMultimap.create();
      UUID uuid = UUID.fromString("CB2F4" + equipmentSlot + "D3-64" + equipmentSlot + "A-4F78-A497-9C56A33DB" + equipmentSlot + "BB");
      multimap.put(PropertiesRegistry.AIRBORNE_MOBILITY.getName(), new AttributeModifier(uuid, "airborn mobility modifier", 0.05, 0));
      multimap.put(EntityPlayer.SWIM_SPEED.getName(), new AttributeModifier(uuid, "swim speed modifier", 0.2, 0));
      return multimap;
   }
}
