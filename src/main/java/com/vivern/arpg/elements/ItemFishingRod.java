package com.vivern.arpg.elements;

import com.vivern.arpg.entity.FishingHook;
import com.vivern.arpg.loot.Fishing;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.NBTHelper;
import com.vivern.arpg.main.Sounds;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Mouse;

public class ItemFishingRod extends Item {
   public float length = 10.0F;
   public float controllability = 1.0F;
   public float roundRadius = 1.0F;
   public float luck = 1.0F;

   public ItemFishingRod() {
      this.setCreativeTab(CreativeTabs.TOOLS);
      this.setMaxDamage(100);
      this.setMaxStackSize(1);
      this.addPropertyOverride(new ResourceLocation("rtype"), new IItemPropertyGetter() {
         @SideOnly(Side.CLIENT)
         public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
            if (NBTHelper.GetNBTboolean(stack, "hasHook")) {
               return !NBTHelper.GetNBTstring(stack, "bait").isEmpty() ? 2.0F : 1.0F;
            } else {
               return 0.0F;
            }
         }
      });
   }

   public int getMaxItemUseDuration(ItemStack stack) {
      return 72000;
   }

   public EnumAction getItemUseAction(ItemStack stack) {
      return EnumAction.BOW;
   }

   public float getFishingLuck(EntityLivingBase player, ItemStack itemstack, World worldIn) {
      Item item = NBTHelper.GetNBTitemFromString(itemstack, "bait");
      float bpower = Fishing.getBaitPower(item);
      float rluck = bpower
         + this.luck
         + (float)player.getEntityAttribute(SharedMonsterAttributes.LUCK).getAttributeValue()
         + EnchantmentHelper.getEnchantmentLevel(Enchantments.LUCK_OF_THE_SEA, itemstack);
      System.out.println(rluck);
      return rluck;
   }

   public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
      ItemStack itemstack = player.getHeldItem(hand);
      player.setActiveHand(hand);
      return new ActionResult(EnumActionResult.SUCCESS, itemstack);
   }

   public void onPlayerStoppedUsing(ItemStack itemstack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
      if (entityLiving instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)entityLiving;
         int damage = itemstack.getItemDamage();
         World world = player.getEntityWorld();
         Item itemIn = itemstack.getItem();
         int usetime = Math.min(72000 - timeLeft, 20);
         if (player.getHeldItemMainhand() == itemstack && !player.getCooldownTracker().hasCooldown(itemIn)) {
            if (NBTHelper.GetNBTboolean(itemstack, "destroy")) {
               NBTHelper.SetNBTboolean(itemstack, true, "hasHook");
            }

            player.getCooldownTracker().setCooldown(this, 22);
            player.addStat(StatList.getObjectUseStats(this));
            if (NBTHelper.GetNBTboolean(itemstack, "hasHook")) {
               world.playSound(
                  (EntityPlayer)null, player.posX, player.posY, player.posZ, Sounds.fishing_throw, SoundCategory.AMBIENT, 0.8F, 1.0F
               );
               NBTHelper.SetNBTboolean(itemstack, false, "hasHook");
               NBTHelper.SetNBTboolean(itemstack, false, "destroy");
               if (!player.capabilities.isCreativeMode) {
                  itemstack.damageItem(1, player);
               }

               if (!world.isRemote) {
                  List<Block> allowedLiquids = new ArrayList<>();
                  allowedLiquids.add(Blocks.WATER);
                  FishingHook bolt = new FishingHook(
                     world,
                     player,
                     (ItemFishingRod)ItemsRegister.MAINFISHINGROD,
                     itemstack,
                     allowedLiquids,
                     Fishing.getBait(NBTHelper.GetNBTitemFromString(itemstack, "bait"))
                  );
                  bolt.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, usetime * 0.0375F, 1.4F);
                  world.spawnEntity(bolt);
               }
            } else if (!NBTHelper.GetNBTboolean(itemstack, "pecked")) {
               world.playSound(
                  (EntityPlayer)null,
                  player.posX,
                  player.posY,
                  player.posZ,
                  SoundEvents.ENTITY_BOBBER_RETRIEVE,
                  SoundCategory.AMBIENT,
                  0.8F,
                  1.0F
               );
               NBTHelper.SetNBTboolean(itemstack, true, "destroy");
            } else {
               NBTHelper.SetNBTint(itemstack, 3, "use");
               world.playSound(
                  (EntityPlayer)null,
                  player.posX,
                  player.posY,
                  player.posZ,
                  Sounds.fishing_capture,
                  SoundCategory.AMBIENT,
                  0.8F,
                  1.0F
               );
            }
         }
      }
   }

   public void onUpdate(ItemStack itemstack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
      if (entityIn instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)entityIn;
         int damage = itemstack.getItemDamage();
         World world = player.getEntityWorld();
         boolean click = Mouse.isButtonDown(1);
         Item itemIn = itemstack.getItem();
         NBTHelper.GiveNBTboolean(itemstack, true, "hasHook");
         NBTHelper.GiveNBTboolean(itemstack, false, "destroy");
         NBTHelper.GiveNBTboolean(itemstack, false, "pecked");
         NBTHelper.GiveNBTint(itemstack, 0, "use");
         NBTHelper.GiveNBTstring(itemstack, "", "bait");
         if (NBTHelper.GetNBTint(itemstack, "use") > 0) {
            NBTHelper.AddNBTint(itemstack, -1, "use");
         }
      }
   }
}
