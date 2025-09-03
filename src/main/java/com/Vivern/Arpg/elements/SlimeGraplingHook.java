//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.entity.GraplingHookParticle;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SlimeGraplingHook extends GraplingHook {
   public SlimeGraplingHook() {
      this.setRegistryName("slime_grapling_hook");
      this.setCreativeTab(CreativeTabs.TOOLS);
      this.setTranslationKey("slime_grapling_hook");
      this.setMaxDamage(1500);
      this.setMaxStackSize(1);
      this.maxlength = 8;
      this.lengthMultiplier = 1.5F;
      this.grapRadius = 0.1;
      this.power = 0.25F;
      this.cooldown = 17;
      this.texture = new ResourceLocation("arpg:textures/slime_gh_chain.png");
      this.texture2 = new ResourceLocation("arpg:textures/slime_gh_pike.png");
      this.maxTension = 30.0F;
   }

   @Override
   public void onGrap(EntityPlayer player, ItemStack itemstack) {
      if (!player.capabilities.isCreativeMode) {
         itemstack.damageItem(1, player);
      }

      player.world
         .playSound(
            (EntityPlayer)null,
            player.posX,
            player.posY,
            player.posZ,
            SoundEvents.ENTITY_SMALL_SLIME_HURT,
            SoundCategory.PLAYERS,
            0.5F,
            0.9F + itemRand.nextFloat() / 5.0F
         );
   }

   @SideOnly(Side.CLIENT)
   @Override
   public void spawnParticle(Vec3d pos1, Vec3d pos2, World world, EntityPlayer player) {
      if (world.isRemote && pos1.lengthSquared() > 1.0E-6 && pos2.lengthSquared() > 1.0E-6) {
         float dis = (float)pos1.distanceTo(pos2);
         GraplingHookParticle laser = new GraplingHookParticle(
            world,
            this.texture,
            this.texture2,
            0.08F - dis / 500.0F,
            -1,
            1.0F,
            1.0F,
            1.0F,
            0.0F,
            dis,
            1,
            0.0F,
            1.5F,
            player.ticksExisted,
            0.4F,
            0.35F,
            pos1,
            pos2
         );
         laser.setPosition(pos1.x, pos1.y - 0.3, pos1.z);
         world.spawnEntity(laser);
         laser.entityOn = player;
      }
   }
}
