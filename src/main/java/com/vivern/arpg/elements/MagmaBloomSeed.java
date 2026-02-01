package com.vivern.arpg.elements;

import com.vivern.arpg.blocks.ISeed;
import com.vivern.arpg.main.BlocksRegister;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class MagmaBloomSeed extends Item implements ISeed {
   public MagmaBloomSeed() {
      this.setRegistryName("magma_bloom_seeds");
      this.setCreativeTab(CreativeTabs.MATERIALS);
      this.setTranslationKey("magma_bloom_seeds");
   }

   public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
      ItemStack itemstack = player.getHeldItem(hand);
      player.setActiveHand(hand);
      RayTraceResult raytraceresult = this.rayTrace(world, player, true);
      if (raytraceresult == null) {
         return new ActionResult(EnumActionResult.PASS, itemstack);
      } else if (raytraceresult.typeOfHit != Type.BLOCK) {
         return new ActionResult(EnumActionResult.PASS, itemstack);
      } else {
         BlockPos pos = raytraceresult.getBlockPos();
         if (this.canGrowAt(world, pos.up())) {
            player.getHeldItem(hand).shrink(1);
            world.setBlockState(pos.up(), BlocksRegister.MAGMABLOOM.getDefaultState());
            return new ActionResult(EnumActionResult.SUCCESS, itemstack);
         } else {
            return new ActionResult(EnumActionResult.FAIL, itemstack);
         }
      }
   }

   @Override
   public IBlockState getPlant(IBlockAccess world, BlockPos pos) {
      return BlocksRegister.MAGMABLOOM.getDefaultState();
   }

   @Override
   public boolean canGrowAt(IBlockAccess world, BlockPos pos) {
      IBlockState st = world.getBlockState(pos.down());
      Block block = st.getBlock();
      return (block == Blocks.LAVA || block == Blocks.FLOWING_LAVA) && world.isAirBlock(pos);
   }
}
