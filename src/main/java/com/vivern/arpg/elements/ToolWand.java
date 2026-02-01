package com.vivern.arpg.elements;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;

public class ToolWand extends Item {
   public boolean ench = false;
   public ItemStack consume;
   public boolean ignoreMetaConsume = true;
   public String placename = "";
   public IBlockState place = null;
   public String consumename = "";
   boolean canReplaceOnSneak = false;
   public IBlockState replace = null;
   public IBlockState toreplace = null;
   public String replacename = "";
   public String toreplacename = "";
   public int placemeta = -1;
   public int replacemeta = -1;
   public int toreplacemeta = -1;

   public ToolWand(String name, CreativeTabs tab, int maxdamage, ItemStack consume, boolean ignoreMetaConsume, String placeblock) {
      this.setRegistryName(name);
      this.setCreativeTab(tab);
      this.setTranslationKey(name);
      this.setMaxDamage(maxdamage);
      this.setMaxStackSize(1);
      this.consume = consume;
      this.placename = placeblock;
      this.ignoreMetaConsume = ignoreMetaConsume;
   }

   public ToolWand(String name, CreativeTabs tab, int maxdamage, String consume, boolean ignoreMetaConsume, String placeblock) {
      this.setRegistryName(name);
      this.setCreativeTab(tab);
      this.setTranslationKey(name);
      this.setMaxDamage(maxdamage);
      this.setMaxStackSize(1);
      this.consumename = consume;
      this.placename = placeblock;
      this.ignoreMetaConsume = ignoreMetaConsume;
   }

   public ToolWand setReplaceLogic(String replaceblock, String toblock) {
      this.canReplaceOnSneak = true;
      this.replacename = replaceblock;
      this.toreplacename = toblock;
      return this;
   }

   public ToolWand setPlaceMeta(int metadata) {
      this.placemeta = metadata;
      return this;
   }

   public ToolWand setReplaceMeta(int metadata) {
      this.replacemeta = metadata;
      return this;
   }

   public ToolWand setToReplaceMeta(int metadata) {
      this.toreplacemeta = metadata;
      return this;
   }

   public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
      ItemStack itemstack = player.getHeldItem(hand);
      player.setActiveHand(hand);
      if (this.consume == null) {
         Item it = Item.getByNameOrId(this.consumename);
         if (it == null) {
            Block bl = Block.getBlockFromName(this.consumename);
            if (bl == null) {
               return new ActionResult(EnumActionResult.FAIL, itemstack);
            }

            this.consume = new ItemStack(bl);
         } else {
            this.consume = new ItemStack(it);
         }
      }

      if (this.place == null) {
         this.place = this.placemeta < 0
            ? Block.getBlockFromName(this.placename).getDefaultState()
            : Block.getBlockFromName(this.placename).getStateFromMeta(this.placemeta);
      }

      if (this.canReplaceOnSneak) {
         if (this.replace == null) {
            this.replace = this.replacemeta < 0
               ? Block.getBlockFromName(this.replacename).getDefaultState()
               : Block.getBlockFromName(this.replacename).getStateFromMeta(this.replacemeta);
         }

         if (this.toreplace == null) {
            this.toreplace = this.toreplacemeta < 0
               ? Block.getBlockFromName(this.toreplacename).getDefaultState()
               : Block.getBlockFromName(this.toreplacename).getStateFromMeta(this.toreplacemeta);
         }
      }

      if (player.isSneaking() && this.canReplaceOnSneak) {
         RayTraceResult raytraceresult = this.rayTrace(world, player, false);
         if (raytraceresult == null) {
            return new ActionResult(EnumActionResult.PASS, itemstack);
         }

         if (raytraceresult.typeOfHit != Type.BLOCK) {
            return new ActionResult(EnumActionResult.PASS, itemstack);
         }

         player.swingArm(hand);
         BlockPos pos = raytraceresult.getBlockPos();
         Block blockfrom = world.getBlockState(pos).getBlock();
         Block blockto = this.toreplace.getBlock();
         if (blockfrom == this.replace.getBlock()) {
            world.setBlockState(pos, this.toreplace);
            world.playSound(
               (EntityPlayer)null,
               pos,
               blockto.getSoundType(this.toreplace, world, pos, player).getPlaceSound(),
               SoundCategory.BLOCKS,
               0.8F,
               0.9F + itemRand.nextFloat() / 5.0F
            );
            return new ActionResult(EnumActionResult.SUCCESS, itemstack);
         }
      } else {
         RayTraceResult raytraceresultx = this.rayTrace(world, player, false);
         if (raytraceresultx == null) {
            return new ActionResult(EnumActionResult.PASS, itemstack);
         }

         if (raytraceresultx.typeOfHit != Type.BLOCK) {
            return new ActionResult(EnumActionResult.PASS, itemstack);
         }

         if (raytraceresultx.sideHit != null) {
            player.swingArm(hand);
            BlockPos posr = raytraceresultx.getBlockPos();
            BlockPos pos = posr.offset(raytraceresultx.sideHit);
            boolean repl = world.getBlockState(pos).getBlock().isReplaceable(world, pos);
            Block block = this.place.getBlock();
            if (block.canPlaceBlockOnSide(world, pos, raytraceresultx.sideHit)
               && block.canPlaceBlockAt(world, pos)
               && (repl || world.isAirBlock(pos))
               && player.inventory.hasItemStack(this.consume)) {
               player.inventory
                  .clearMatchingItems(this.consume.getItem(), this.ignoreMetaConsume ? -1 : this.consume.getMetadata(), this.consume.getCount(), null);
               if (repl) {
                  world.destroyBlock(pos, true);
               }

               world.setBlockState(pos, this.place);
               world.playSound(
                  (EntityPlayer)null,
                  pos,
                  block.getSoundType(this.place, world, pos, player).getPlaceSound(),
                  SoundCategory.BLOCKS,
                  0.8F,
                  0.9F + itemRand.nextFloat() / 5.0F
               );
               return new ActionResult(EnumActionResult.SUCCESS, itemstack);
            }
         }
      }

      return super.onItemRightClick(world, player, hand);
   }

   public ToolWand setEnchantGlow() {
      this.ench = true;
      return this;
   }

   public boolean hasEffect(ItemStack stack) {
      return this.ench ? this.ench : super.hasEffect(stack);
   }
}
