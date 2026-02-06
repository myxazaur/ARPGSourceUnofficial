package com.Vivern.Arpg.weather;

import com.Vivern.Arpg.dimensions.generationutils.AbstractWorldProvider;
import com.Vivern.Arpg.events.Debugger;
import com.Vivern.Arpg.main.AnimationTimer;
import com.Vivern.Arpg.main.ColorConverters;
import com.Vivern.Arpg.main.GetMOP;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

public class Aurora extends WorldEvent {
   public NoiseGeneratorPerlin perlin1 = new NoiseGeneratorPerlin(new Random(1L), 2);
   public NoiseGeneratorPerlin perlin2 = new NoiseGeneratorPerlin(new Random(2L), 2);
   public NoiseGeneratorPerlin perlin3 = new NoiseGeneratorPerlin(new Random(3L), 2);
   public NoiseGeneratorPerlin perlin4 = new NoiseGeneratorPerlin(new Random(4L), 2);

   public Aurora(AbstractWorldProvider worldProvider, int index, int livetimeMin, int livetimeMax, float chanceToStart) {
      super(worldProvider, index, livetimeMin, livetimeMax, chanceToStart);
   }

   @Override
   public boolean canOverlapWith(WorldEvent other) {
      return false;
   }

   @Override
   public int getCooldown() {
      return 3000 + this.worldProvider.getWorld().rand.nextInt(3000);
   }

   @Override
   public boolean canStart() {
      int t = (int)this.worldProvider.getWorld().getWorldTime() % 24000;
      return t < 0 || t > 13000;
   }

   @Override
   public void onUpdate() {
      super.onUpdate();
      if (this.isStarted && !this.canStart()) {
         this.stop();
      }
   }

   @Override
   public void render(float partialTicks, WorldClient world, Minecraft mc) {
      super.render(partialTicks, world, mc);
      Entity entity = mc.getRenderViewEntity();
      if (entity != null) {
         int entityPosX = MathHelper.floor(entity.posX);
         int entityPosY = MathHelper.floor(entity.posY);
         int entityPosZ = MathHelper.floor(entity.posZ);
         Tessellator tessellator = Tessellator.getInstance();
         BufferBuilder bufferbuilder = tessellator.getBuffer();
         GlStateManager.disableCull();
         GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
         GlStateManager.enableBlend();
         GlStateManager.disableTexture2D();
         GlStateManager.disableFog();
         GlStateManager.shadeModel(7425);
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
         GlStateManager.alphaFunc(516, 0.1F);
         double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks;
         double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks;
         double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks;
         float time = AnimationTimer.tick * 3.0E-4F;
         bufferbuilder.setTranslation(-d0, -d1, -d2);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
         int range = (128 + mc.gameSettings.renderDistanceChunks * 13) * 9;
         int rangeMin = (int)(range * 0.55F);
         int quantityZ = 70;
         int quantityX = 490;
         int entZ = (int)entity.posZ / quantityZ * quantityZ + quantityZ / 2;
         int entX = (int)entity.posX / quantityX * quantityX + quantityX / 2;
         double height0 = 480.0F + Debugger.floats[3];
         double height1 = 560.0F + Debugger.floats[3];
         double height2 = 640.0F + Debugger.floats[3];
         double height3 = 880.0F + Debugger.floats[3];
         double perlinMult = 0.002 + Debugger.floats[4];
         double perlinPower = 135.0F + Debugger.floats[5];
         double perlinLengthMult = 0.0015 + Debugger.floats[6];
         double perlinLengthPower = 34.0F + Debugger.floats[7];
         double perlinColorMult = 0.002F + Debugger.floats[9];
         double perlinColorPower = 0.45F;

         for (int xx = -range; xx <= range; xx += quantityX) {
            double x = entX + xx;
            double z2 = entZ - range - quantityZ;
            double sin2 = Math.sin(z2 * 0.1) * 4.0;
            double displ2 = this.perlin1.getValue(x * perlinMult + time, z2 * perlinMult) * perlinPower;
            double ylength2 = this.perlin2.getValue(x * perlinLengthMult + time, z2 * perlinLengthMult) * perlinLengthPower;
            Vec3d color2 = getAuroraColor(this.perlin3.getValue(x * perlinColorMult + time, z2 * perlinColorMult) * perlinColorPower);
            float bottomMult = (float)this.perlin4.getValue(x * perlinColorMult + time, z2 * perlinColorMult);
            bottomMult = GetMOP.getfromto(bottomMult, -1.0F, 1.0F);
            float lineR = 0.0F * bottomMult * this.showness;
            float lineG = 0.5F * bottomMult * this.showness;
            float lineB = 0.3F * bottomMult * this.showness;

            for (int zz = -range; zz <= range; zz += quantityZ) {
               double z = entZ + zz;
               double sin1 = Math.sin(z * 0.1) * 4.0;
               double displ1 = this.perlin1.getValue(x * perlinMult + time, z * perlinMult) * perlinPower;
               double ylength1 = this.perlin2.getValue(x * perlinLengthMult + time, z * perlinLengthMult) * perlinLengthPower;
               Vec3d color1 = getAuroraColor(this.perlin3.getValue(x * perlinColorMult + time, z * perlinColorMult) * perlinColorPower);
               double distX1 = Math.abs(x + sin1 + displ1 - entity.posX);
               double distX2 = Math.abs(x + sin2 + displ2 - entity.posX);
               double distZ1 = Math.abs(z - entity.posZ);
               double distZ2 = Math.abs(z2 - entity.posZ);
               float colorReduct1 = (1.0F - GetMOP.getfromto((float)Math.max(distX1, distZ1), (float)rangeMin, (float)range)) * this.showness;
               float colorReduct2 = (1.0F - GetMOP.getfromto((float)Math.max(distX2, distZ2), (float)rangeMin, (float)range)) * this.showness;
               float r1 = (float)color1.x * colorReduct1;
               float g1 = (float)color1.y * colorReduct1;
               float b1 = (float)color1.z * colorReduct1;
               float r2 = (float)color2.x * colorReduct2;
               float g2 = (float)color2.y * colorReduct2;
               float b2 = (float)color2.z * colorReduct2;
               bufferbuilder.pos(x + sin1 + displ1, height2 + ylength1 * 0.5, z).color(r1, g1, b1, 1.0F).endVertex();
               bufferbuilder.pos(x + sin1 + displ1, height3 + ylength1, z).color(0.0F, 0.0F, 0.0F, 0.0F).endVertex();
               bufferbuilder.pos(x + sin2 + displ2, height3 + ylength2, z2).color(0.0F, 0.0F, 0.0F, 0.0F).endVertex();
               bufferbuilder.pos(x + sin2 + displ2, height2 + ylength2 * 0.5, z2).color(r2, g2, b2, 1.0F).endVertex();
               bufferbuilder.pos(x + sin1 + displ1, height1 - ylength1 * 0.5, z)
                  .color(lineR * colorReduct1, lineG * colorReduct1, lineB * colorReduct1, 1.0F)
                  .endVertex();
               bufferbuilder.pos(x + sin1 + displ1, height2 + ylength1 * 0.5, z).color(r1, g1, b1, 1.0F).endVertex();
               bufferbuilder.pos(x + sin2 + displ2, height2 + ylength2 * 0.5, z2).color(r2, g2, b2, 1.0F).endVertex();
               bufferbuilder.pos(x + sin2 + displ2, height1 - ylength2 * 0.5, z2)
                  .color(lineR * colorReduct2, lineG * colorReduct2, lineB * colorReduct2, 1.0F)
                  .endVertex();
               bufferbuilder.pos(x + sin1 + displ1, height0 - ylength1, z).color(0.0F, 0.0F, 0.0F, 0.0F).endVertex();
               bufferbuilder.pos(x + sin1 + displ1, height1 - ylength1 * 0.5, z)
                  .color(lineR * colorReduct1, lineG * colorReduct1, lineB * colorReduct1, 1.0F)
                  .endVertex();
               bufferbuilder.pos(x + sin2 + displ2, height1 - ylength2 * 0.5, z2)
                  .color(lineR * colorReduct2, lineG * colorReduct2, lineB * colorReduct2, 1.0F)
                  .endVertex();
               bufferbuilder.pos(x + sin2 + displ2, height0 - ylength2, z2).color(0.0F, 0.0F, 0.0F, 0.0F).endVertex();
               z2 = z;
               sin2 = sin1;
               displ2 = displ1;
               ylength2 = ylength1;
               color2 = color1;
            }
         }

         tessellator.draw();
         bufferbuilder.setTranslation(0.0, 0.0, 0.0);
         GlStateManager.enableCull();
         GlStateManager.enableTexture2D();
         GlStateManager.enableFog();
         GlStateManager.shadeModel(7424);
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         GlStateManager.disableBlend();
         GlStateManager.alphaFunc(516, 0.1F);
      }
   }

   public static Vec3d getAuroraColor(double value) {
      return ColorConverters.getRainbow(0.55F + (float)value * 0.25F).scale(0.4);
   }
}
