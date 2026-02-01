package com.vivern.arpg.elements.animation;

import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public class AnimationDefinition {
   public static AnimationDefinition Builder = new AnimationDefinition();
   public ArrayList<AnimationChannel> channels = new ArrayList<>();
   public boolean isLoop = false;
   public float lengthSeconds = 0.0F;

   public AnimationDefinition withLength(float lengthSeconds) {
      AnimationDefinition newanimation = new AnimationDefinition();
      newanimation.lengthSeconds = lengthSeconds;
      return newanimation;
   }

   public AnimationDefinition looping() {
      this.isLoop = true;
      return this;
   }

   public AnimationDefinition addAnimation(ModelRenderer modelRenderer, AnimationChannel channel) {
      channel.modelRenderer = modelRenderer;
      channel.defaultRotation = new Vec3d(modelRenderer.rotateAngleX, modelRenderer.rotateAngleY, modelRenderer.rotateAngleZ);
      this.channels.add(channel);
      return this;
   }

   public AnimationDefinition build() {
      return this;
   }

   public void animate(Entity entity, float animationSpeed) {
      this.animate((entity.ticksExisted + Minecraft.getMinecraft().getRenderPartialTicks()) * animationSpeed);
   }

   public void animate(float time) {
      float seconds = time / 20.0F;
      if (this.isLoop) {
         seconds = (int)(seconds * 100.0F) % (int)(this.lengthSeconds * 100.0F) / 100.0F;
      }

      for (AnimationChannel animationChannel : this.channels) {
         animationChannel.animate(seconds);
      }
   }

   public void returnDefaults() {
      for (AnimationChannel animationChannel : this.channels) {
         animationChannel.returnDefaults();
      }
   }
}
