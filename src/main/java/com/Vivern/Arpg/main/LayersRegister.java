//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.main;

import com.Vivern.Arpg.renders.LayerLaser;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;

public class LayersRegister {
   public static void register() {
      setLayer(new LayerLaser());
   }

   private static void setLayer(LayerRenderer layer) {
      ((RenderPlayer)Minecraft.getMinecraft().getRenderManager().getSkinMap().get("default")).addLayer(layer);
      ((RenderPlayer)Minecraft.getMinecraft().getRenderManager().getSkinMap().get("slim")).addLayer(layer);
   }
}
