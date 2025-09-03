//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.entity.GraplingHookParticle;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.Keys;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.SuperKnockback;
import com.Vivern.Arpg.network.PacketGrapplingHookToClients;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WebGraplingHook extends GraplingHook {
   public WebGraplingHook() {
      this.setRegistryName("web_grapling_hook");
      this.setCreativeTab(CreativeTabs.TOOLS);
      this.setTranslationKey("web_grapling_hook");
      this.setMaxDamage(500);
      this.setMaxStackSize(1);
      this.maxlength = 11;
      this.lengthMultiplier = 0.9F;
      this.grapRadius = 0.1;
      this.power = 0.4F;
      this.cooldown = 11;
      this.texture = new ResourceLocation("arpg:textures/web_gh_chain.png");
      this.texture2 = new ResourceLocation("arpg:textures/web_gh_pike.png");
      this.maxTension = 14.5F;
      this.friction = 0.88F;
   }

   @Override
   public void onUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
      if (!world.isRemote && entityIn.isEntityAlive() && entityIn instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)entityIn;
         int damage = itemstack.getItemDamage();
         boolean click = Keys.isKeyPressed(player, Keys.GRAPLINGHOOK);
         Item itemIn = itemstack.getItem();
         boolean reset = Keys.isKeyPressed(player, Keys.JUMP);
         if (entityIn.ticksExisted < 2) {
            NBTHelper.SetNBTboolean(itemstack, false, "graped1");
            NBTHelper.SetNBTboolean(itemstack, false, "graped2");
            NBTHelper.SetNBTboolean(itemstack, false, "graped3");
            NBTHelper.SetNBTboolean(itemstack, false, "graped4");
         }

         NBTHelper.GiveNBTboolean(itemstack, false, "throwed");
         NBTHelper.GiveNBTboolean(itemstack, false, "graped1");
         NBTHelper.GiveNBTboolean(itemstack, false, "graped2");
         NBTHelper.GiveNBTboolean(itemstack, false, "graped3");
         NBTHelper.GiveNBTboolean(itemstack, false, "graped4");
         NBTHelper.GiveNBTint(itemstack, 0, "length");
         NBTHelper.GiveNBTfloat(itemstack, 0.0F, "rotationPitch");
         NBTHelper.GiveNBTfloat(itemstack, 0.0F, "rotationYaw");
         NBTHelper.GiveNBTdouble(itemstack, 0.0, "pointX1");
         NBTHelper.GiveNBTdouble(itemstack, 0.0, "pointY1");
         NBTHelper.GiveNBTdouble(itemstack, 0.0, "pointZ1");
         NBTHelper.GiveNBTdouble(itemstack, 0.0, "pointX2");
         NBTHelper.GiveNBTdouble(itemstack, 0.0, "pointY2");
         NBTHelper.GiveNBTdouble(itemstack, 0.0, "pointZ2");
         NBTHelper.GiveNBTdouble(itemstack, 0.0, "pointX3");
         NBTHelper.GiveNBTdouble(itemstack, 0.0, "pointY3");
         NBTHelper.GiveNBTdouble(itemstack, 0.0, "pointZ3");
         NBTHelper.GiveNBTdouble(itemstack, 0.0, "pointX4");
         NBTHelper.GiveNBTdouble(itemstack, 0.0, "pointY4");
         NBTHelper.GiveNBTdouble(itemstack, 0.0, "pointZ4");
         NBTHelper.GiveNBTint(itemstack, 0, "grapCooldown");
         NBTHelper.GiveNBTint(itemstack, 1, "actualNumber");
         if (NBTHelper.GetNBTint(itemstack, "grapCooldown") > 0) {
            NBTHelper.AddNBTint(itemstack, -1, "grapCooldown");
         }

         boolean throwed = NBTHelper.GetNBTboolean(itemstack, "throwed");
         int actual = NBTHelper.GetNBTint(itemstack, "actualNumber");
         boolean onGrapTicknotUsed = true;
         boolean canJumpOnReset = true;

         for (int i = 1; i <= 4; i++) {
            double xpos = NBTHelper.GetNBTdouble(itemstack, "pointX" + i);
            double ypos = NBTHelper.GetNBTdouble(itemstack, "pointY" + i);
            double zpos = NBTHelper.GetNBTdouble(itemstack, "pointZ" + i);
            if (NBTHelper.GetNBTboolean(itemstack, "graped" + i)) {
               if (reset) {
                  NBTHelper.SetNBTboolean(itemstack, false, "graped" + i);
                  if (canJumpOnReset) {
                     this.onReset(player, itemstack);
                     canJumpOnReset = false;
                  }
               }

               if (player.getDistance(xpos, ypos, zpos) <= this.maxTension) {
                  if (!Double.isNaN(xpos) && !Double.isNaN(ypos) && !Double.isNaN(zpos)) {
                     player.motionX = player.motionX * this.friction;
                     player.motionY = player.motionY * this.friction;
                     player.motionZ = player.motionZ * this.friction;
                     SuperKnockback.applyMove(player, -this.power, xpos, ypos, zpos);
                     player.velocityChanged = true;
                     PacketGrapplingHookToClients.send(player, xpos, ypos, zpos, itemstack.getItem());
                     if (onGrapTicknotUsed) {
                        this.onGrapTick(player, itemstack);
                        onGrapTicknotUsed = false;
                     }
                  }

                  AxisAlignedBB aabb = new AxisAlignedBB(
                     xpos - this.grapRadius,
                     ypos - this.grapRadius,
                     zpos - this.grapRadius,
                     xpos + this.grapRadius,
                     ypos + this.grapRadius,
                     zpos + this.grapRadius
                  );
                  if (!world.collidesWithAnyBlock(aabb)) {
                     NBTHelper.SetNBTboolean(itemstack, false, "graped" + i);
                  }
               } else {
                  NBTHelper.SetNBTboolean(itemstack, false, "graped" + i);
               }
            }
         }

         Vec3d vec = GetMOP.RotatedPosRayTrace(
            NBTHelper.GetNBTint(itemstack, "length") * this.lengthMultiplier,
            1.0F,
            player,
            0.05,
            0.05,
            NBTHelper.GetNBTfloat(itemstack, "rotationPitch"),
            NBTHelper.GetNBTfloat(itemstack, "rotationYaw")
         );
         AxisAlignedBB aabb = new AxisAlignedBB(
            vec.x - this.grapRadius,
            vec.y - this.grapRadius,
            vec.z - this.grapRadius,
            vec.x + this.grapRadius,
            vec.y + this.grapRadius,
            vec.z + this.grapRadius
         );
         if (NBTHelper.GetNBTint(itemstack, "length") > 0) {
            PacketGrapplingHookToClients.send(player, vec.x, vec.y, vec.z, itemstack.getItem());
            if (world.collidesWithAnyBlock(aabb) && NBTHelper.GetNBTint(itemstack, "grapCooldown") == 0) {
               NBTHelper.SetNBTboolean(itemstack, true, "graped" + actual);
               NBTHelper.SetNBTdouble(itemstack, vec.x, "pointX" + actual);
               NBTHelper.SetNBTdouble(itemstack, vec.y, "pointY" + actual);
               NBTHelper.SetNBTdouble(itemstack, vec.z, "pointZ" + actual);
               NBTHelper.SetNBTint(itemstack, 10, "grapCooldown");
               NBTHelper.SetNBTboolean(itemstack, false, "throwed");
               NBTHelper.SetNBTint(itemstack, 0, "length");
               this.onGrap(player, itemstack);
               this.actualNext(itemstack, actual);
            }
         }

         if (click && !player.getCooldownTracker().hasCooldown(itemIn) && this.canThrow(player, itemstack) && this.isFirst(player, itemstack)) {
            NBTHelper.SetNBTfloat(itemstack, MathHelper.clamp(player.rotationPitch, -89.0F, 89.0F), "rotationPitch");
            NBTHelper.SetNBTfloat(itemstack, player.rotationYaw, "rotationYaw");
            NBTHelper.SetNBTboolean(itemstack, true, "throwed");
            player.getCooldownTracker().setCooldown(this, this.cooldown);
            this.onThrow(player, itemstack);
         }

         if (NBTHelper.GetNBTint(itemstack, "length") >= this.maxlength) {
            NBTHelper.SetNBTboolean(itemstack, false, "throwed");
         }

         if (throwed) {
            if (NBTHelper.GetNBTint(itemstack, "length") < this.maxlength) {
               NBTHelper.AddNBTint(itemstack, 1, "length");
            }
         } else if (NBTHelper.GetNBTint(itemstack, "length") > 0) {
            NBTHelper.AddNBTint(itemstack, -1, "length");
         }
      }
   }

   public void actualNext(ItemStack itemstack, int actual) {
      if (++actual > 4) {
         actual -= 4;
      }

      NBTHelper.SetNBTint(itemstack, actual, "actualNumber");
   }

   public int getActualBack(int actual) {
      if (--actual < 1) {
         actual += 4;
      }

      return actual;
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
            Sounds.grapling_hook_soft,
            SoundCategory.PLAYERS,
            0.5F,
            0.9F + itemRand.nextFloat() / 5.0F
         );
   }

   @SideOnly(Side.CLIENT)
   @Override
   public void spawnParticle(Vec3d pos1, Vec3d pos2, World world, EntityPlayer player) {
      if (world.isRemote && pos1.lengthSquared() > 1.0E-6 && pos2.lengthSquared() > 1.0E-6) {
         float pike = 0.13F;
         GraplingHookParticle laser = new GraplingHookParticle(
            world,
            this.texture,
            this.texture2,
            0.03F,
            world.getLight(player.getPosition()) * 11,
            1.0F,
            1.0F,
            1.0F,
            0.0F,
            pos1.distanceTo(pos2),
            1,
            0.0F,
            0.5F,
            player.ticksExisted,
            0.17F + pike,
            0.1F + pike,
            pos1,
            pos2
         );
         laser.setPosition(pos1.x, pos1.y - 0.3, pos1.z);
         world.spawnEntity(laser);
         laser.entityOn = player;
      }
   }
}
