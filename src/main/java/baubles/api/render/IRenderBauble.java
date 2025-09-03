//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package baubles.api.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

public interface IRenderBauble {
   void onPlayerBaubleRender(ItemStack var1, EntityPlayer var2, RenderType var3, float var4);

   public static final class Helper {
      public static void rotateIfSneaking(EntityPlayer player) {
         if (player.isSneaking()) {
            applySneakingRotation();
         }
      }

      public static void applySneakingRotation() {
         GlStateManager.translate(0.0F, 0.2F, 0.0F);
         GlStateManager.rotate(28.647888F, 1.0F, 0.0F, 0.0F);
      }

      public static void translateToHeadLevel(EntityPlayer player) {
         GlStateManager.translate(0.0F, -player.getDefaultEyeHeight(), 0.0F);
         if (player.isSneaking()) {
            GlStateManager.translate(
               0.25F * MathHelper.sin(player.rotationPitch * (float) Math.PI / 180.0F),
               0.25F * MathHelper.cos(player.rotationPitch * (float) Math.PI / 180.0F),
               0.0F
            );
         }
      }

      public static void translateToFace() {
         GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
         GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.translate(0.0F, -4.35F, -1.27F);
      }

      public static void defaultTransforms() {
         GlStateManager.translate(0.0, 3.0, 1.0);
         GlStateManager.scale(0.55, 0.55, 0.55);
      }

      public static void translateToChest() {
         GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.translate(0.0F, -3.2F, -0.85F);
      }
   }

   public static enum RenderType {
      BODY,
      HEAD;
   }
}
