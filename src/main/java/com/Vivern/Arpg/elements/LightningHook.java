//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.entity.GraplingHookParticle;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.renders.GUNParticle;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class LightningHook extends GraplingHook {
   ResourceLocation largesmoke = new ResourceLocation("arpg:textures/blueexplode.png");

   public LightningHook() {
      this.setRegistryName("lightning_hook");
      this.setCreativeTab(CreativeTabs.TOOLS);
      this.setTranslationKey("lightning_hook");
      this.setMaxDamage(2000);
      this.setMaxStackSize(1);
      this.maxlength = 4;
      this.lengthMultiplier = 3.2F;
      this.grapRadius = 0.1;
      this.power = 0.49F;
      this.cooldown = 10;
      this.texture = new ResourceLocation("arpg:textures/lightning_gh_chain.png");
      this.texture2 = new ResourceLocation("arpg:textures/lightning_gh_pike.png");
      this.maxTension = 15.0F;
   }

   @Override
   public void onThrow(EntityPlayer player, ItemStack itemstack) {
      player.world
         .playSound(
            (EntityPlayer)null,
            player.posX,
            player.posY,
            player.posZ,
            Sounds.lightning_gh_throw,
            SoundCategory.PLAYERS,
            0.7F,
            0.9F + itemRand.nextFloat() / 5.0F
         );
   }

   @Override
   public void onGrap(EntityPlayer player, ItemStack itemstack) {
      if (!player.capabilities.isCreativeMode) {
         itemstack.damageItem(1, player);
      }

      double xpos = NBTHelper.GetNBTdouble(itemstack, "pointX");
      double ypos = NBTHelper.GetNBTdouble(itemstack, "pointY");
      double zpos = NBTHelper.GetNBTdouble(itemstack, "pointZ");
      GUNParticle bigsmoke = new GUNParticle(
         this.largesmoke,
         0.4F + (float)itemRand.nextGaussian() / 20.0F,
         0.0F,
         10,
         240,
         player.world,
         xpos,
         ypos,
         zpos,
         0.0F,
         0.0F,
         0.0F,
         0.95F + (float)itemRand.nextGaussian() / 10.0F,
         1.0F,
         1.0F,
         true,
         itemRand.nextInt(360)
      );
      bigsmoke.alphaTickAdding = -0.1F;
      bigsmoke.alphaGlowing = true;
      player.world.spawnEntity(bigsmoke);
      player.world
         .playSound(
            (EntityPlayer)null,
            player.posX,
            player.posY,
            player.posZ,
            Sounds.lightning_gh_grap,
            SoundCategory.PLAYERS,
            0.5F,
            0.9F + itemRand.nextFloat() / 5.0F
         );
   }

   @SideOnly(Side.CLIENT)
   @Override
   public void spawnParticle(Vec3d pos1, Vec3d pos2, World world, EntityPlayer player) {
      if (world.isRemote && pos1.lengthSquared() > 1.0E-6 && pos2.lengthSquared() > 1.0E-6) {
         GraplingHookParticle laser = new GraplingHookParticle(
            world,
            this.texture,
            this.texture2,
            0.04F,
            240,
            1.0F,
            1.0F,
            1.0F,
            0.0F,
            pos1.distanceTo(pos2),
            1,
            0.1F,
            0.5F,
            player.ticksExisted,
            0.16F,
            0.16F,
            pos1,
            pos2
         );
         laser.setPosition(pos1.x, pos1.y - 0.3, pos1.z);
         world.spawnEntity(laser);
         laser.entityOn = player;
      }
   }
}
