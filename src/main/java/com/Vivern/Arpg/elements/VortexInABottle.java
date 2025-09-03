//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import baubles.api.render.IRenderBauble;
import com.Vivern.Arpg.elements.models.VortexInABottleModel;
import com.Vivern.Arpg.main.Keys;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.renders.GUNParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class VortexInABottle extends Item implements IBauble, IRenderBauble {
   private final ResourceLocation textur = new ResourceLocation("arpg:textures/vortex_inbottle_tex.png");
   private final VortexInABottleModel model = new VortexInABottleModel();
   ResourceLocation texturep = new ResourceLocation("arpg:textures/vortex.png");

   public VortexInABottle() {
      this.setRegistryName("vortex_in_a_bottle");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("vortex_in_a_bottle");
      this.setMaxDamage(20);
      this.setMaxStackSize(1);
   }

   @Override
   public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, RenderType type, float partialTicks) {
      if (type == RenderType.BODY) {
         GlStateManager.pushMatrix();
         Helper.rotateIfSneaking(player);
         Minecraft.getMinecraft().renderEngine.bindTexture(this.textur);
         this.model
            .render(
               player,
               1.0F,
               (float)(1.0 - Math.sin(player.ticksExisted / 60) / 2.0),
               (float)(1.0 - Math.sin(player.ticksExisted / 47) / 2.0),
               2.0F,
               1.0F,
               0.065F
            );
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         Helper.rotateIfSneaking(player);
         Minecraft.getMinecraft().renderEngine.bindTexture(this.textur);
         this.model
            .render(
               player,
               1.0F,
               (float)(1.0 - Math.sin(player.ticksExisted / 60) / 2.0),
               (float)(1.0 - Math.sin(player.ticksExisted / 47) / 2.0),
               1.0F,
               1.0F,
               0.065F
            );
         GlStateManager.popMatrix();
      }
   }

   @Override
   public BaubleType getBaubleType(ItemStack itemstack) {
      return BaubleType.AMULET;
   }

   @Override
   public void onWornTick(ItemStack itemstack, EntityLivingBase entityIn) {
      Item itemIn = itemstack.getItem();
      if (!entityIn.world.isRemote) {
         if (entityIn instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entityIn;
            boolean click = Keys.isPressedDoubleJump(player);
            int damage = itemstack.getItemDamage();
            if (click && damage < 20) {
               itemstack.damageItem(1, entityIn);
               player.motionY = player.motionY * 0.8 + 0.25;
               player.velocityChanged = true;
               this.Effects(player.world, player);
               if (player.motionY > -0.5) {
                  player.fallDistance = 0.0F;
               }
            }

            if (player.onGround && !click) {
               itemstack.setItemDamage(0);
            }
         }
      } else if (entityIn.motionY > -0.5) {
         entityIn.fallDistance = 0.0F;
      }
   }

   @SideOnly(Side.CLIENT)
   public void Effects(World world, EntityPlayer player) {
      Entity bigboom = new GUNParticle(
         this.texturep,
         0.1F + (float)itemRand.nextGaussian() / 20.0F,
         -0.02F,
         17,
         240,
         world,
         player.posX + (float)itemRand.nextGaussian() / 8.0F,
         player.posY,
         player.posZ + (float)itemRand.nextGaussian() / 8.0F,
         (float)itemRand.nextGaussian() / 22.0F,
         (float)itemRand.nextGaussian() / 22.0F - 0.2F,
         (float)itemRand.nextGaussian() / 22.0F,
         1.0F,
         1.0F,
         1.0F - (float)itemRand.nextGaussian() / 8.0F,
         false,
         (int)Math.round(itemRand.nextGaussian() * 180.0)
      );
      world.spawnEntity(bigboom);
      world.playSound(
         (EntityPlayer)null,
         player.posX,
         player.posY,
         player.posZ,
         Sounds.ichorsteam,
         SoundCategory.AMBIENT,
         0.6F,
         0.8F + itemRand.nextFloat() / 5.0F
      );
   }
}
