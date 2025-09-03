//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.renders;

import baubles.api.render.IRenderBauble;
import com.Vivern.Arpg.elements.models.CubikModel;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class RenderBaubleX implements IRenderBauble {
   private final CubikModel model = new CubikModel();

   @Override
   public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, RenderType type, float partialTicks) {
      System.out.print("I'm alive!");
      GlStateManager.pushMatrix();
      GlStateManager.translate((float)player.posX, (float)player.posY, (float)player.posZ);
      this.model.render(player, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.popMatrix();
   }
}
