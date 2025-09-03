//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.renders;

import com.Vivern.Arpg.elements.models.AbstractMobModel;
import java.util.HashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderFluidHelper {
   public static HashMap<Fluid, ItemStack> renderfluidStacks = new HashMap<>();

   public static void init() {
      for (Fluid fluid : FluidRegistry.getRegisteredFluids().values()) {
         if (fluid != null) {
            ItemStack stack = new ItemStack(Item.getItemFromBlock(fluid.getBlock()));
            renderfluidStacks.put(fluid, stack);
         }
      }
   }

   public static void drawFluidCube(float width, float height, float filledValue, Fluid fluid, FluidStack fluidstack) {
      int lum = fluid.getLuminosity() * 16;
      AbstractMobModel.light(lum, true);
      int color = fluid.getColor(fluidstack);
      float r = (color >> 16 & 0xFF) / 255.0F;
      float g = (color >> 8 & 0xFF) / 255.0F;
      float b = (color >> 0 & 0xFF) / 255.0F;
      TextureMap textureMapBlocks = Minecraft.getMinecraft().getTextureMapBlocks();
      TextureAtlasSprite still = textureMapBlocks.getAtlasSprite(fluid.getStill(fluidstack).toString());
      TextureAtlasSprite flowing = textureMapBlocks.getAtlasSprite(fluid.getFlowing(fluidstack).toString());
      float one = still.getMaxV() - still.getMinV();
      float onelength = one * filledValue;
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
      bufferbuilder.pos(0.0, height, 0.0).tex(still.getMinU(), still.getMaxV()).color(r, g, b, 1.0F).endVertex();
      bufferbuilder.pos(width, height, 0.0).tex(still.getMaxU(), still.getMaxV()).color(r, g, b, 1.0F).endVertex();
      bufferbuilder.pos(width, 0.0, 0.0)
         .tex(still.getMaxU(), still.getMinV() + one - onelength)
         .color(r, g, b, 1.0F)
         .endVertex();
      bufferbuilder.pos(0.0, 0.0, 0.0)
         .tex(still.getMinU(), still.getMinV() + one - onelength)
         .color(r, g, b, 1.0F)
         .endVertex();
      bufferbuilder.pos(0.0, height, 0.0).tex(still.getMinU(), still.getMaxV()).color(r, g, b, 1.0F).endVertex();
      bufferbuilder.pos(0.0, height, width).tex(still.getMaxU(), still.getMaxV()).color(r, g, b, 1.0F).endVertex();
      bufferbuilder.pos(0.0, 0.0, width)
         .tex(still.getMaxU(), still.getMinV() + one - onelength)
         .color(r, g, b, 1.0F)
         .endVertex();
      bufferbuilder.pos(0.0, 0.0, 0.0)
         .tex(still.getMinU(), still.getMinV() + one - onelength)
         .color(r, g, b, 1.0F)
         .endVertex();
      bufferbuilder.pos(0.0, height, width).tex(still.getMinU(), still.getMaxV()).color(r, g, b, 1.0F).endVertex();
      bufferbuilder.pos(width, height, width).tex(still.getMaxU(), still.getMaxV()).color(r, g, b, 1.0F).endVertex();
      bufferbuilder.pos(width, 0.0, width)
         .tex(still.getMaxU(), still.getMinV() + one - onelength)
         .color(r, g, b, 1.0F)
         .endVertex();
      bufferbuilder.pos(0.0, 0.0, width)
         .tex(still.getMinU(), still.getMinV() + one - onelength)
         .color(r, g, b, 1.0F)
         .endVertex();
      bufferbuilder.pos(width, height, 0.0).tex(still.getMinU(), still.getMaxV()).color(r, g, b, 1.0F).endVertex();
      bufferbuilder.pos(width, height, width).tex(still.getMaxU(), still.getMaxV()).color(r, g, b, 1.0F).endVertex();
      bufferbuilder.pos(width, 0.0, width)
         .tex(still.getMaxU(), still.getMinV() + one - onelength)
         .color(r, g, b, 1.0F)
         .endVertex();
      bufferbuilder.pos(width, 0.0, 0.0)
         .tex(still.getMinU(), still.getMinV() + one - onelength)
         .color(r, g, b, 1.0F)
         .endVertex();
      bufferbuilder.pos(0.0, height, 0.0).tex(still.getMinU(), still.getMaxV()).color(r, g, b, 1.0F).endVertex();
      bufferbuilder.pos(width, height, 0.0).tex(still.getMaxU(), still.getMaxV()).color(r, g, b, 1.0F).endVertex();
      bufferbuilder.pos(width, height, width).tex(still.getMaxU(), still.getMinV()).color(r, g, b, 1.0F).endVertex();
      bufferbuilder.pos(0.0, height, width).tex(still.getMinU(), still.getMinV()).color(r, g, b, 1.0F).endVertex();
      bufferbuilder.pos(0.0, 0.0, 0.0).tex(still.getMinU(), still.getMaxV()).color(r, g, b, 1.0F).endVertex();
      bufferbuilder.pos(width, 0.0, 0.0).tex(still.getMaxU(), still.getMaxV()).color(r, g, b, 1.0F).endVertex();
      bufferbuilder.pos(width, 0.0, width).tex(still.getMaxU(), still.getMinV()).color(r, g, b, 1.0F).endVertex();
      bufferbuilder.pos(0.0, 0.0, width).tex(still.getMinU(), still.getMinV()).color(r, g, b, 1.0F).endVertex();
      tessellator.draw();
      AbstractMobModel.returnlight();
   }

   public static void drawTexturedFluidRect(int x, int y, int width, int height, float filledValue, Fluid fluid, FluidStack fluidstack, double zLevel) {
      int color = fluid.getColor(fluidstack);
      float r = (color >> 16 & 0xFF) / 255.0F;
      float g = (color >> 8 & 0xFF) / 255.0F;
      float b = (color >> 0 & 0xFF) / 255.0F;
      TextureMap textureMapBlocks = Minecraft.getMinecraft().getTextureMapBlocks();
      TextureAtlasSprite still = textureMapBlocks.getAtlasSprite(fluid.getStill(fluidstack).toString());
      TextureAtlasSprite flowing = textureMapBlocks.getAtlasSprite(fluid.getFlowing(fluidstack).toString());
      float one = still.getMaxV() - still.getMinV();
      float onelength = one * filledValue;
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
      bufferbuilder.pos(x + 0, y + height, zLevel)
         .tex(still.getMinU(), still.getMaxV())
         .color(r, g, b, 1.0F)
         .endVertex();
      bufferbuilder.pos(x + width, y + height, zLevel)
         .tex(still.getMaxU(), still.getMaxV())
         .color(r, g, b, 1.0F)
         .endVertex();
      bufferbuilder.pos(x + width, y + 0, zLevel)
         .tex(still.getMaxU(), still.getMinV() + one - onelength)
         .color(r, g, b, 1.0F)
         .endVertex();
      bufferbuilder.pos(x + 0, y + 0, zLevel)
         .tex(still.getMinU(), still.getMinV() + one - onelength)
         .color(r, g, b, 1.0F)
         .endVertex();
      tessellator.draw();
   }

   public static void drawTexturedFluidRect(
      int x, int y, int width, int height, float filledValue, Fluid fluid, FluidStack fluidstack, double zLevel, float rr, float gg, float bb
   ) {
      int color = fluid.getColor(fluidstack);
      float r = (color >> 16 & 0xFF) / 255.0F * rr;
      float g = (color >> 8 & 0xFF) / 255.0F * gg;
      float b = (color >> 0 & 0xFF) / 255.0F * bb;
      TextureMap textureMapBlocks = Minecraft.getMinecraft().getTextureMapBlocks();
      TextureAtlasSprite still = textureMapBlocks.getAtlasSprite(fluid.getStill(fluidstack).toString());
      TextureAtlasSprite flowing = textureMapBlocks.getAtlasSprite(fluid.getFlowing(fluidstack).toString());
      float one = still.getMaxV() - still.getMinV();
      float onelength = one * filledValue;
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
      bufferbuilder.pos(x + 0, y + height, zLevel)
         .tex(still.getMinU(), still.getMaxV())
         .color(r, g, b, 1.0F)
         .endVertex();
      bufferbuilder.pos(x + width, y + height, zLevel)
         .tex(still.getMaxU(), still.getMaxV())
         .color(r, g, b, 1.0F)
         .endVertex();
      bufferbuilder.pos(x + width, y + 0, zLevel)
         .tex(still.getMaxU(), still.getMinV() + one - onelength)
         .color(r, g, b, 1.0F)
         .endVertex();
      bufferbuilder.pos(x + 0, y + 0, zLevel)
         .tex(still.getMinU(), still.getMinV() + one - onelength)
         .color(r, g, b, 1.0F)
         .endVertex();
      tessellator.draw();
   }
}
