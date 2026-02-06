package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.main.FindAmmo;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.Keys;
import com.Vivern.Arpg.main.NBTHelper;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;

public class Wrench extends ItemWeapon {
   public Wrench() {
      this.setRegistryName("wrench");
      this.setCreativeTab(CreativeTabs.TOOLS);
      this.setTranslationKey("wrench");
      this.setMaxStackSize(1);
      this.setMaxDamage(20000);
   }

   public float getPower() {
      return 1.0F;
   }

   public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
      return false;
   }

   public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
      if (!world.isRemote && entity instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)entity;
         if (player.getHeldItemMainhand() == stack && Keys.isKeyPressed(player, Keys.SECONDARYATTACK)) {
            Vec3d vec3d = player.getPositionEyes(1.0F);
            Vec3d vec3d1 = player.getLook(1.0F);
            Vec3d vec3d2 = vec3d.add(vec3d1.x * 3.0, vec3d1.y * 3.0, vec3d1.z * 3.0);
            RayTraceResult res = GetMOP.fixedRayTraceBlocks(world, player, 0.2F, 0.15F, false, vec3d, vec3d2, false, true, false);
            if (res != null) {
               if (res.typeOfHit == Type.BLOCK && res.getBlockPos() != null) {
                  Block bl = world.getBlockState(res.getBlockPos()).getBlock();
                  if (bl instanceof IWrenchUser) {
                     ((IWrenchUser)bl).onUseWrench(world, entity, stack, this.getPower(), res.getBlockPos());
                     return;
                  }

                  TileEntity tile = world.getTileEntity(res.getBlockPos());
                  if (tile != null && tile instanceof IWrenchUser) {
                     ((IWrenchUser)tile).onUseWrench(world, entity, stack, this.getPower(), res.getBlockPos());
                     return;
                  }
               }

               if (res.typeOfHit == Type.ENTITY && res.entityHit != null && res.entityHit instanceof IWrenchUser) {
                  ((IWrenchUser)res.entityHit).onUseWrench(world, entity, stack, this.getPower(), null);
                  return;
               }
            }
         }
      }
   }

   public static void moveToHand(EntityPlayer player, EnumHand hand) {
      if (player.getHeldItem(hand).isEmpty()) {
         int slot = FindAmmo.getSlotForWrench(player.inventory);
         if (slot >= 0) {
            ItemStack wrench = player.inventory.getStackInSlot(slot).copy();
            NBTHelper.GiveNBTint(wrench, slot, "lastslot");
            NBTHelper.SetNBTint(wrench, slot, "lastslot");
            player.setHeldItem(hand, wrench);
            player.inventory.setInventorySlotContents(slot, ItemStack.EMPTY);
         }
      }
   }

   public static void returnToSlot(EntityPlayer player, int slotFrom) {
      ItemStack wrench = player.inventory.getStackInSlot(slotFrom).copy();
      if (wrench.getItem() instanceof Wrench) {
         int slot = NBTHelper.GetNBTint(wrench, "lastslot");
         if (player.inventory.getStackInSlot(slot).isEmpty()) {
            NBTHelper.SetNBTint(wrench, -1, "lastslot");
            player.inventory.setInventorySlotContents(slotFrom, ItemStack.EMPTY);
            player.inventory.setInventorySlotContents(slot, wrench);
         } else {
            slot = player.inventory.getFirstEmptyStack();
            if (slot >= 0) {
               NBTHelper.SetNBTint(wrench, -1, "lastslot");
               player.inventory.setInventorySlotContents(slotFrom, ItemStack.EMPTY);
               player.inventory.setInventorySlotContents(slot, wrench);
            }
         }
      }
   }

   @Override
   public boolean autoReload(ItemStack itemstack) {
      return false;
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return false;
   }

   @Override
   public boolean hasZoom(ItemStack itemstack) {
      return false;
   }

   @Override
   public int getCooldownTime(ItemStack itemstack) {
      return 0;
   }

   @Override
   public int getReloadTime(ItemStack itemstack) {
      return 0;
   }

   @Override
   public float getZoom(ItemStack itemstack, EntityPlayer player) {
      return 0.0F;
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.ONE_HANDED;
   }
}
