//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.entity.EntitySpellForgeCatalyst;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.Keys;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.Weapons;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class SpellPliers extends Item {
   public SpellPliers() {
      this.setRegistryName("spell_pliers");
      this.setCreativeTab(CreativeTabs.TOOLS);
      this.setTranslationKey("spell_pliers");
      this.setMaxStackSize(1);
   }

   public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
      return slotChanged;
   }

   public void onUpdate(ItemStack stack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
      if (!world.isRemote) {
         if (NBTHelper.GetNBTint(stack, "heat") > 0) {
            NBTHelper.AddNBTint(stack, -1, "heat");
         }

         if (entityIn instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entityIn;
            boolean click = Keys.isKeyPressed(player, Keys.SECONDARYATTACK);
            if ((player.getHeldItemMainhand() == stack || player.getHeldItemOffhand() == stack) && click && !player.getCooldownTracker().hasCooldown(this)) {
               RayTraceResult result = GetMOP.fixedRayTraceBlocks(
                  world, player, player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue(), 0.2F, 0.2F, false, false, true, true
               );
               if (NBTHelper.GetNBTboolean(stack, "picked")) {
                  if (result.hitVec != null) {
                     if (stack.getTagCompound().hasKey("picked_item")) {
                        NBTTagCompound nbtTagCompound = stack.getTagCompound().getCompoundTag("picked_item");
                        ItemStack releasedstack = new ItemStack(nbtTagCompound);
                        EntitySpellForgeCatalyst catalyst = new EntitySpellForgeCatalyst(
                           world,
                           result.hitVec.x,
                           result.hitVec.y,
                           result.hitVec.z,
                           releasedstack,
                           NBTHelper.GetNBTint(stack, "heat")
                        );
                        world.spawnEntity(catalyst);
                        catalyst.fromPliers = true;
                     }

                     stack.getTagCompound().setBoolean("picked", false);
                     player.getCooldownTracker().setCooldown(this, 13);
                     EnumHand hand = player.getHeldItemMainhand() == stack ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;
                     Weapons.setPlayerAnimationOnServer(player, 14, hand);
                  }
               } else if (result.entityHit != null && result.entityHit instanceof EntitySpellForgeCatalyst) {
                  EntitySpellForgeCatalyst catalyst = (EntitySpellForgeCatalyst)result.entityHit;
                  if (catalyst.getItem() != null && !catalyst.isDead) {
                     NBTTagCompound nbtTagCompound = catalyst.getItem().serializeNBT();
                     if (!stack.hasTagCompound()) {
                        NBTTagCompound newTag = new NBTTagCompound();
                        stack.setTagCompound(newTag);
                     }

                     stack.getTagCompound().setTag("picked_item", nbtTagCompound);
                     stack.getTagCompound().setInteger("heat", catalyst.heat);
                     stack.getTagCompound().setBoolean("picked", true);
                     player.getCooldownTracker().setCooldown(this, 13);
                     catalyst.setDead();
                     EnumHand hand = player.getHeldItemMainhand() == stack ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;
                     Weapons.setPlayerAnimationOnServer(player, 14, hand);
                  }
               }
            }
         }
      }
   }
}
