package com.vivern.arpg.weather;

import com.vivern.arpg.dimensions.generationutils.AbstractWorldProvider;
import com.vivern.arpg.main.Sounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.ResourceLocation;

public class Rainstorm extends Storm {
   public static ResourceLocation tex = new ResourceLocation("arpg:textures/stormledge_rainstorm.png");

   public Rainstorm(AbstractWorldProvider worldProvider, int index, int livetimeMin, int livetimeMax, float chanceToStart) {
      super(worldProvider, index, livetimeMin, livetimeMax, chanceToStart);
      this.soundRain = Sounds.weather_rainstorm;
      this.soundRainAbove = Sounds.weather_rainstorm;
   }

   @Override
   public void render(float partialTicks, WorldClient world, Minecraft mc) {
      super.render(partialTicks, world, mc);
      WorldEventsHandler.renderRainSnow(partialTicks, this.rand, 1.0F, tex, this.showness, 0.012F, -0.2F, 0.02F, 1.0F, null, null);
   }
}
