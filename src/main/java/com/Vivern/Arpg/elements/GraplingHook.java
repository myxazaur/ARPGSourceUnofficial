package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.entity.GraplingHookParticle;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.Keys;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.SuperKnockback;
import com.Vivern.Arpg.network.PacketGrapplingHookToClients;
import javax.annotation.Nullable;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
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

public class GraplingHook extends Item {
   public static float resetJumpPower = 0.8F;
   public ResourceLocation texture = new ResourceLocation("arpg:textures/grapling_hook_chain.png");
   public ResourceLocation texture2 = new ResourceLocation("arpg:textures/grapling_hook_pike.png");
   public int maxlength = 10;
   public float lengthMultiplier = 1.0F;
   public double grapRadius = 0.1;
   public float power = 0.42F;
   public int cooldown = 20;
   public float maxTension = 10.0F;
   public float friction = 0.92F;

   public GraplingHook() {
      this.setCreativeTab(CreativeTabs.TOOLS);
      this.setMaxDamage(200);
      this.setMaxStackSize(1);
   }

   public void onUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
      if (!world.isRemote && entityIn.isEntityAlive() && entityIn instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)entityIn;
         int damage = itemstack.getItemDamage();
         boolean click = Keys.isKeyPressed(player, Keys.GRAPLINGHOOK);
         Item itemIn = itemstack.getItem();
         double xpos = NBTHelper.GetNBTdouble(itemstack, "pointX");
         double ypos = NBTHelper.GetNBTdouble(itemstack, "pointY");
         double zpos = NBTHelper.GetNBTdouble(itemstack, "pointZ");
         boolean reset = Keys.isKeyPressed(player, Keys.JUMP);
         if (entityIn.ticksExisted < 2) {
            NBTHelper.SetNBTboolean(itemstack, false, "graped");
         }

         NBTHelper.GiveNBTboolean(itemstack, false, "throwed");
         NBTHelper.GiveNBTboolean(itemstack, false, "graped");
         NBTHelper.GiveNBTint(itemstack, 0, "length");
         NBTHelper.GiveNBTfloat(itemstack, 0.0F, "rotationPitch");
         NBTHelper.GiveNBTfloat(itemstack, 0.0F, "rotationYaw");
         NBTHelper.GiveNBTdouble(itemstack, 0.0, "pointX");
         NBTHelper.GiveNBTdouble(itemstack, 0.0, "pointY");
         NBTHelper.GiveNBTdouble(itemstack, 0.0, "pointZ");
         NBTHelper.GiveNBTint(itemstack, 0, "grapCooldown");
         if (NBTHelper.GetNBTint(itemstack, "grapCooldown") > 0) {
            NBTHelper.AddNBTint(itemstack, -1, "grapCooldown");
         }

         boolean throwed = NBTHelper.GetNBTboolean(itemstack, "throwed");
         if (NBTHelper.GetNBTboolean(itemstack, "graped")) {
            if (reset) {
               NBTHelper.SetNBTboolean(itemstack, false, "graped");
               this.onReset(player, itemstack);
            }

            double distToGrapPoint = player.getDistance(xpos, ypos, zpos);
            if (distToGrapPoint <= this.maxTension) {
               if (!Double.isNaN(xpos) && !Double.isNaN(ypos) && !Double.isNaN(zpos)) {
                  float powerApply = 0.1F + 0.9F * GetMOP.getfromto((float)distToGrapPoint, 0.0F, 2.0F);
                  float finalFriction = this.friction * 0.5F + this.friction * 0.5F * powerApply;
                  player.motionX *= finalFriction;
                  player.motionY *= finalFriction;
                  player.motionZ *= finalFriction;
                  SuperKnockback.applyMove(player, -(this.power * powerApply), xpos, ypos, zpos);
                  player.velocityChanged = true;
                  PacketGrapplingHookToClients.send(player, xpos, ypos, zpos, itemstack.getItem());
                  this.onGrapTick(player, itemstack);
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
                  NBTHelper.SetNBTboolean(itemstack, false, "graped");
               }
            } else {
               NBTHelper.SetNBTboolean(itemstack, false, "graped");
            }
         } else {
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
                  NBTHelper.SetNBTboolean(itemstack, true, "graped");
                  NBTHelper.SetNBTdouble(itemstack, vec.x, "pointX");
                  NBTHelper.SetNBTdouble(itemstack, vec.y, "pointY");
                  NBTHelper.SetNBTdouble(itemstack, vec.z, "pointZ");
                  NBTHelper.SetNBTint(itemstack, 10, "grapCooldown");
                  NBTHelper.SetNBTboolean(itemstack, false, "throwed");
                  this.onGrap(player, itemstack);
               }
            }
         }

         if (click && !player.getCooldownTracker().hasCooldown(itemIn) && this.canThrow(player, itemstack) && this.isFirst(player, itemstack)) {
            NBTHelper.SetNBTfloat(itemstack, MathHelper.clamp(player.rotationPitch, -89.0F, 89.0F), "rotationPitch");
            NBTHelper.SetNBTfloat(itemstack, player.rotationYaw, "rotationYaw");
            NBTHelper.SetNBTboolean(itemstack, true, "throwed");
            NBTHelper.SetNBTboolean(itemstack, false, "graped");
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

   public void onThrow(EntityPlayer player, ItemStack itemstack) {
      player.world
         .playSound(
            (EntityPlayer)null,
            player.posX,
            player.posY,
            player.posZ,
            Sounds.swosh_a,
            SoundCategory.PLAYERS,
            0.7F,
            0.95F + itemRand.nextFloat() / 10.0F
         );
   }

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
            Sounds.grapling_hook,
            SoundCategory.PLAYERS,
            0.5F,
            0.9F + itemRand.nextFloat() / 5.0F
         );
   }

   public void onGrapTick(EntityPlayer player, ItemStack itemstack) {
      if (player.motionY < 0.2) {
         player.fallDistance = 0.0F;
      }
   }

   public boolean canThrow(EntityPlayer player, ItemStack itemstack) {
      return itemstack.getItemDamage() < this.getMaxDamage(itemstack);
   }

   public boolean isFirst(EntityPlayer player, ItemStack itemstack) {
      InventoryPlayer inventory = player.inventory;

      for (int i = 0; i < inventory.getSizeInventory(); i++) {
         if (inventory.getStackInSlot(i).getItem() instanceof GraplingHook) {
            if (inventory.getStackInSlot(i) == itemstack) {
               return true;
            }

            return false;
         }
      }

      return false;
   }

   @Nullable
   public static ItemStack getFirst(EntityPlayer player) {
      InventoryPlayer inventory = player.inventory;

      for (int i = 0; i < inventory.getSizeInventory(); i++) {
         if (inventory.getStackInSlot(i).getItem() instanceof GraplingHook) {
            return inventory.getStackInSlot(i);
         }
      }

      return null;
   }

   public void onReset(EntityPlayer player, ItemStack itemstack) {
      if (!player.isSneaking()) {
         player.motionY /= 1.5;
         player.motionY = player.motionY + resetJumpPower;
         player.velocityChanged = true;
      }
   }

   @SideOnly(Side.CLIENT)
   public void spawnParticle(Vec3d pos1, Vec3d pos2, World world, EntityPlayer player) {
      if (world.isRemote && pos1.lengthSquared() > 1.0E-6 && pos2.lengthSquared() > 1.0E-6) {
         GraplingHookParticle laser = new GraplingHookParticle(
            world,
            this.texture,
            this.texture2,
            0.05F,
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
            0.2F,
            0.2F,
            pos1,
            pos2
         );
         laser.setPosition(pos1.x, pos1.y - 0.3, pos1.z);
         world.spawnEntity(laser);
         laser.entityOn = player;
      }
   }
}
