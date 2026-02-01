package com.vivern.arpg.elements;

import com.vivern.arpg.entity.GraplingHookParticle;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class JungleGraplingHook extends GraplingHook {
   public JungleGraplingHook() {
      this.setRegistryName("jungle_grapling_hook");
      this.setCreativeTab(CreativeTabs.TOOLS);
      this.setTranslationKey("jungle_grapling_hook");
      this.setMaxDamage(800);
      this.setMaxStackSize(1);
      this.maxlength = 12;
      this.lengthMultiplier = 1.1F;
      this.grapRadius = 0.1;
      this.power = 0.47F;
      this.cooldown = 25;
      this.texture = new ResourceLocation("arpg:textures/jungle_grapling_hook_chain.png");
      this.texture2 = new ResourceLocation("arpg:textures/jungle_grapling_hook_pike.png");
      this.maxTension = 13.2F;
   }

   @SideOnly(Side.CLIENT)
   @Override
   public void spawnParticle(Vec3d pos1, Vec3d pos2, World world, EntityPlayer player) {
      if (world.isRemote && pos1.lengthSquared() > 1.0E-6 && pos2.lengthSquared() > 1.0E-6) {
         GraplingHookParticle laser = new GraplingHookParticle(
            world,
            this.texture,
            this.texture2,
            0.07F,
            -1,
            1.0F,
            1.0F,
            1.0F,
            0.0F,
            pos1.distanceTo(pos2),
            1,
            0.0F,
            0.5F,
            player.ticksExisted,
            0.23F,
            0.23F,
            pos1,
            pos2
         );
         laser.setPosition(pos1.x, pos1.y - 0.3, pos1.z);
         world.spawnEntity(laser);
         laser.entityOn = player;
      }
   }
}
