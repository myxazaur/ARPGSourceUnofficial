//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.entity.GraplingHookParticle;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EnderGraplingHook extends GraplingHook {
   public EnderGraplingHook() {
      this.setRegistryName("ender_grapling_hook");
      this.setCreativeTab(CreativeTabs.TOOLS);
      this.setTranslationKey("ender_grapling_hook");
      this.setMaxDamage(2800);
      this.setMaxStackSize(1);
      this.maxlength = 14;
      this.lengthMultiplier = 1.07F;
      this.grapRadius = 0.1;
      this.power = 0.45F;
      this.cooldown = 30;
      this.texture = new ResourceLocation("arpg:textures/ender_gh_chain.png");
      this.texture2 = new ResourceLocation("arpg:textures/ender_gh_pike.png");
      this.maxTension = 16.0F;
      this.friction = 0.81F;
   }

   @SideOnly(Side.CLIENT)
   @Override
   public void spawnParticle(Vec3d pos1, Vec3d pos2, World world, EntityPlayer player) {
      if (world.isRemote && pos1.lengthSquared() > 1.0E-6 && pos2.lengthSquared() > 1.0E-6) {
         GraplingHookParticle laser = new GraplingHookParticle(
            world,
            this.texture,
            this.texture2,
            0.1F,
            -1,
            1.0F,
            1.0F,
            1.0F,
            0.0F,
            pos1.distanceTo(pos2),
            1,
            0.0F,
            0.8F,
            player.ticksExisted,
            0.19F,
            0.19F,
            pos1,
            pos2
         );
         laser.setPosition(pos1.x, pos1.y - 0.3, pos1.z);
         world.spawnEntity(laser);
         laser.entityOn = player;
      }
   }
}
