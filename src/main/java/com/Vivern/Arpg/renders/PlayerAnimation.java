package com.Vivern.Arpg.renders;

import com.Vivern.Arpg.events.Debugger;
import javax.annotation.Nullable;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;

public class PlayerAnimation {
   public final byte ID;
   public byte animationLength;

   public PlayerAnimation(byte ID, byte animationLength) {
      this.ID = ID;
      this.animationLength = animationLength;
   }

   public byte cascadeAnimation(byte animationPlaying, int timePlaying) {
      return this.ID;
   }

   public boolean transformItemFirstperson() {
      return true;
   }

   public boolean transformHandThirdperson() {
      return true;
   }

   public boolean transformItemThirdperson() {
      return true;
   }

   public void render(AbstractClientPlayer player, EnumHand hand, float progress, ItemStack stack, float p_187457_7_) {
      boolean flag = hand == EnumHand.MAIN_HAND;
      EnumHandSide enumhandside = flag ? player.getPrimaryHand() : player.getPrimaryHand().opposite();
      GlStateManager.pushMatrix();
      if (stack.isEmpty()) {
         if (flag && !player.isInvisible()) {
            PlayerAnimations.instance.renderArmFirstPerson(p_187457_7_, progress, enumhandside);
         }
      } else {
         boolean flag1 = enumhandside == EnumHandSide.RIGHT;
         float translateX = 0.0F;
         float translateY = 0.0F;
         float translateZ = 0.0F;
         float rotateX = 0.0F;
         float rotateY = 0.0F;
         float rotateZ = 0.0F;
         GlStateManager.translate(Debugger.floats[0] + translateX, Debugger.floats[1] + translateY, Debugger.floats[2] + translateZ);
         PlayerAnimations.instance.transformSideFirstPerson(enumhandside, p_187457_7_);
         GlStateManager.rotate(Debugger.floats[3] + rotateX, 1.0F, 0.0F, 0.0F);
         GlStateManager.rotate(Debugger.floats[4] + rotateY, 0.0F, 1.0F, 0.0F);
         GlStateManager.rotate(Debugger.floats[5] + rotateZ, 0.0F, 0.0F, 1.0F);
         PlayerAnimations.instance.renderItemSide(player, stack, flag1 ? TransformType.FIRST_PERSON_RIGHT_HAND : TransformType.FIRST_PERSON_LEFT_HAND, !flag1);
      }

      GlStateManager.popMatrix();
   }

   public void transform(
      float progress,
      @Nullable ModelBiped model,
      @Nullable ModelRenderer handrenderer,
      EnumHandSide hand,
      @Nullable ItemStack stack,
      EntityPlayer player,
      float partialTicks
   ) {
      if (model != null) {
         if (hand != EnumHandSide.RIGHT) {
            ;
         }

         handrenderer.rotateAngleX = handrenderer.rotateAngleX + Debugger.floats[6] * (float) (Math.PI / 180.0);
         handrenderer.rotateAngleY = handrenderer.rotateAngleY + Debugger.floats[7] * (float) (Math.PI / 180.0);
         handrenderer.rotateAngleZ = handrenderer.rotateAngleZ + Debugger.floats[8] * (float) (Math.PI / 180.0);
      } else {
         float rotateX = 0.0F;
         float rotateY = 0.0F;
         float rotateZ = 0.0F;
         float translateZ = 0.0F;
         GlStateManager.rotate(Debugger.floats[3] + rotateX, 1.0F, 0.0F, 0.0F);
         GlStateManager.rotate(Debugger.floats[4] + rotateY, 0.0F, 1.0F, 0.0F);
         GlStateManager.rotate(Debugger.floats[5] + rotateZ, 0.0F, 0.0F, 1.0F);
         GlStateManager.translate(Debugger.floats[0], Debugger.floats[1], Debugger.floats[2] + translateZ);
      }
   }

   public void transform(
      float progress,
      @Nullable ModelBiped model,
      @Nullable ModelRenderer handrenderer,
      EnumHandSide hand,
      @Nullable ItemStack stack,
      EntityPlayer player,
      float partialTicks,
      EnumHand handWithItem
   ) {
      this.transform(progress, model, handrenderer, hand, stack, player, partialTicks);
   }

   public static ModelRenderer getArmForSide(EnumHandSide side, ModelBiped biped) {
      return side == EnumHandSide.LEFT ? biped.bipedLeftArm : biped.bipedRightArm;
   }
}
