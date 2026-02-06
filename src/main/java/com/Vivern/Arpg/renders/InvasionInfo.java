package com.Vivern.Arpg.renders;

import com.Vivern.Arpg.main.ColorConverters;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.Sounds;
import java.util.HashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;

@EventBusSubscriber(
   modid = "arpg"
)
public class InvasionInfo {
   public static ResourceLocation tex = new ResourceLocation("arpg:textures/invasion_bars.png");
   public static HashMap<Integer, InvasionInfo> invasionsIdToInfo = new HashMap<>();
   public static float nexusHealth = 0.0F;
   public static float waveCompleteRate = 0.0F;
   public static int wave = 0;
   public static InvasionInfo currentInfo = null;
   public static boolean finalwave = false;
   public static String unlocalizedwave = "wave: ";
   public static String unlocalizedFinal = "FINAL WAVE!";
   public static String unlocalizedBigwave = "WAVE ";
   public static String unlocalizedWin = "Is Completed!";
   public static String unlocalizedLose1 = "The ";
   public static String unlocalizedLose2 = " was destroyed";
   public static int finalwaveAnim = 0;
   public static int nextwaveAnim = 0;
   public static int winAnim = 0;
   public static int loseAnim = 0;
   public static long lastUpdateWorldTime;
   public static float zLevel = 0.0F;
   public String unlocalizedInvasionName;
   public String unlocalizedNexusName;
   public Vec3d color;
   public Vec3d color2;
   public int colorFont;

   public InvasionInfo(String unlocalizedInvasionName, String unlocalizedNexusName, Vec3d colorProgress, Vec3d colorHealth, int fontColor) {
      this.unlocalizedInvasionName = unlocalizedInvasionName;
      this.unlocalizedNexusName = unlocalizedNexusName;
      this.color = colorProgress;
      this.color2 = colorHealth;
      this.colorFont = fontColor;
   }

   public static void initInfos() {
      invasionsIdToInfo.put(1, new InvasionInfo("The Slimyland Baiting", "flower", new Vec3d(0.55, 0.7, 0.4), new Vec3d(0.9, 0.32, 0.55), 13762439));
      invasionsIdToInfo.put(2, new InvasionInfo("The Tide", "beacon", new Vec3d(0.02, 0.71, 0.86), new Vec3d(0.38, 0.63, 0.5), 5918320));
      invasionsIdToInfo.put(3, new InvasionInfo("Niveous Hockey", "molder", new Vec3d(0.7F, 0.88F, 1.0), new Vec3d(0.28, 0.33, 0.38), 16251902));
      invasionsIdToInfo.put(4, new InvasionInfo("Winter Ritual", "altar", new Vec3d(0.07F, 0.79F, 0.3F), new Vec3d(0.1, 0.05, 0.65), 48842));
   }

   public static void render(FontRenderer fontRenderer, EntityPlayer player, int i, int f) {
      if (wave != 0 && currentInfo != null) {
         Minecraft.getMinecraft().getTextureManager().bindTexture(tex);
         float opx = 0.0052083335F;
         float opy = 0.029411765F;
         GlStateManager.color((float)currentInfo.color.x, (float)currentInfo.color.y, (float)currentInfo.color.z);
         drawTexturedModalRect(i / 2 - 91, 10, 0, 0, 182, 5, opx, opy);
         drawTexturedModalRect(i / 2 - 91, 10, 0, 5, (int)(182.0F * waveCompleteRate), 5, opx, opy);
         GlStateManager.color((float)currentInfo.color2.x, (float)currentInfo.color2.y, (float)currentInfo.color2.z);
         drawTexturedModalRect(i / 2 - 51, 18, 0, 10, 103, 4, opx, opy);
         drawTexturedModalRect(i / 2 - 51, 18, 0, 14, (int)(103.0F * nexusHealth), 4, opx, opy);
         if (finalwave) {
            drawTexturedModalRect(i / 2 - 96, 5, 0, 19, 192, 15, opx, opy);
         }

         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         fontRenderer.drawStringWithShadow(
            currentInfo.unlocalizedInvasionName, i / 2 - fontRenderer.getStringWidth(currentInfo.unlocalizedInvasionName) / 2, 1.0F, currentInfo.colorFont
         );
         fontRenderer.drawStringWithShadow(
            currentInfo.unlocalizedNexusName + ":",
            i / 2 - 55 - fontRenderer.getStringWidth(currentInfo.unlocalizedNexusName + ":"),
            16.0F,
            currentInfo.colorFont
         );
         fontRenderer.drawStringWithShadow(unlocalizedwave + wave, i / 2 + 55, 16.0F, currentInfo.colorFont);
         if (finalwaveAnim > 0) {
            GlStateManager.pushMatrix();
            float fromto = GetMOP.getfromto((float)finalwaveAnim, 120.0F, 161.0F);
            float scale = fromto * 10.0F + 2.0F;
            GlStateManager.scale(scale, scale, 1.0F);
            Minecraft.getMinecraft()
               .fontRenderer
               .drawStringWithShadow(
                  unlocalizedFinal,
                  (i / 2 - fontRenderer.getStringWidth(unlocalizedFinal) / 2 * scale) / scale,
                  30.0F / scale,
                  ColorConverters.RGBAtoDecimal255(227, 30, 30, (int)(GetMOP.getfromto((float)finalwaveAnim, 0.0F, 40.0F) * 255.0F - fromto * 255.0F))
               );
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            finalwaveAnim--;
         }

         if (nextwaveAnim > 0) {
            GlStateManager.pushMatrix();
            float fromto = GetMOP.getfromto((float)nextwaveAnim, 100.0F, 131.0F);
            float scale = fromto * 10.0F + 2.0F;
            GlStateManager.scale(scale, scale, 1.0F);
            String ubw = unlocalizedBigwave + wave;
            Minecraft.getMinecraft()
               .fontRenderer
               .drawStringWithShadow(
                  ubw,
                  (i / 2 - fontRenderer.getStringWidth(ubw) / 2 * scale) / scale,
                  30.0F / scale,
                  ColorConverters.RGBAtoDecimal255(90, 55, 227, (int)(GetMOP.getfromto((float)nextwaveAnim, 0.0F, 40.0F) * 255.0F - fromto * 255.0F))
               );
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            nextwaveAnim--;
         }

         if (winAnim > 0) {
            GlStateManager.pushMatrix();
            float fromto = GetMOP.getfromto((float)winAnim, 160.0F, 201.0F);
            float scale = fromto * 10.0F + 2.0F;
            GlStateManager.scale(scale, scale, 1.0F);
            String ubw = currentInfo.unlocalizedInvasionName;
            Minecraft.getMinecraft()
               .fontRenderer
               .drawStringWithShadow(
                  ubw,
                  (i / 2 - fontRenderer.getStringWidth(ubw) / 2 * scale) / scale,
                  40.0F / scale,
                  ColorConverters.RGBAtoDecimal255(245, 223, 77, (int)(GetMOP.getfromto((float)winAnim, 0.0F, 40.0F) * 255.0F - fromto * 255.0F))
               );
            GlStateManager.popMatrix();
            if (winAnim < 161 && winAnim > 10) {
               GlStateManager.pushMatrix();
               float fromto2 = GetMOP.getfromto((float)winAnim, 120.0F, 161.0F);
               float scale2 = fromto2 * 10.0F + 2.0F;
               GlStateManager.scale(scale2, scale2, 1.0F);
               String ubw2 = unlocalizedWin;
               Minecraft.getMinecraft()
                  .fontRenderer
                  .drawStringWithShadow(
                     ubw2,
                     (i / 2 - fontRenderer.getStringWidth(ubw2) / 2 * scale2) / scale2,
                     65.0F / scale2,
                     ColorConverters.RGBAtoDecimal255(245, 223, 77, (int)(GetMOP.getfromto((float)winAnim, 10.0F, 50.0F) * 255.0F - fromto2 * 255.0F))
                  );
               GlStateManager.popMatrix();
            }

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            if (winAnim == 160) {
               player.world
                  .playSound(player, player.posX, player.posY, player.posZ, Sounds.next_wave, SoundCategory.AMBIENT, 2.0F, 1.1F);
            }

            winAnim--;
         }

         if (loseAnim > 0) {
            GlStateManager.pushMatrix();
            float fromtox = GetMOP.getfromto((float)loseAnim, 100.0F, 161.0F);
            float scalex = 2.0F;
            GlStateManager.scale(scalex, scalex, 1.0F);
            String ubwx = unlocalizedLose1 + currentInfo.unlocalizedNexusName + unlocalizedLose2;
            Minecraft.getMinecraft()
               .fontRenderer
               .drawStringWithShadow(
                  ubwx,
                  (i / 2 - fontRenderer.getStringWidth(ubwx) / 2 * scalex) / scalex,
                  30.0F / scalex,
                  ColorConverters.RGBAtoDecimal255(190, 30, 36, (int)(GetMOP.getfromto((float)loseAnim, 0.0F, 60.0F) * 255.0F - fromtox * 255.0F))
               );
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
            loseAnim--;
         }

         if (player.world.getTotalWorldTime() - 110L > lastUpdateWorldTime) {
            wave = 0;
            currentInfo = null;
         }
      }
   }

   @SubscribeEvent
   public static void onplayerRespawn(PlayerRespawnEvent event) {
      if (event.player == Minecraft.getMinecraft().player) {
         wave = 0;
         currentInfo = null;
      }
   }

   public static void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height, float onepixelX, float onepixelY) {
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
      bufferbuilder.pos(x + 0, y + height, zLevel).tex((textureX + 0) * onepixelX, (textureY + height) * onepixelY).endVertex();
      bufferbuilder.pos(x + width, y + height, zLevel).tex((textureX + width) * onepixelX, (textureY + height) * onepixelY).endVertex();
      bufferbuilder.pos(x + width, y + 0, zLevel).tex((textureX + width) * onepixelX, (textureY + 0) * onepixelY).endVertex();
      bufferbuilder.pos(x + 0, y + 0, zLevel).tex((textureX + 0) * onepixelX, (textureY + 0) * onepixelY).endVertex();
      tessellator.draw();
   }
}
