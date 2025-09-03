//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.main.BlocksRegister;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;

public class StormSpanner extends Item {
   public StormSpanner() {
      this.setRegistryName("storm_spanner");
      this.setCreativeTab(CreativeTabs.TOOLS);
      this.setTranslationKey("storm_spanner");
      this.setMaxStackSize(1);
   }

   public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
      ItemStack itemstack = player.getHeldItem(hand);
      RayTraceResult raytraceresult = this.rayTrace(world, player, false);
      if (raytraceresult == null) {
         return new ActionResult(EnumActionResult.PASS, itemstack);
      } else if (raytraceresult.typeOfHit != Type.BLOCK) {
         return new ActionResult(EnumActionResult.PASS, itemstack);
      } else {
         player.swingArm(hand);
         BlockPos pos = raytraceresult.getBlockPos();
         Block blockfrom = world.getBlockState(pos).getBlock();
         if (player.canPlayerEdit(pos, EnumFacing.UP, itemstack)) {
            if (blockfrom == BlocksRegister.BEAMROCK) {
               IBlockState st = BlocksRegister.BEAMROCK.getStateFromMeta(1);
               world.setBlockState(pos, st);
               world.playSound(
                  (EntityPlayer)null,
                  pos,
                  BlocksRegister.BEAMROCK.getSoundType(st, world, pos, player).getPlaceSound(),
                  SoundCategory.BLOCKS,
                  0.8F,
                  0.9F + itemRand.nextFloat() / 5.0F
               );
               return new ActionResult(EnumActionResult.SUCCESS, itemstack);
            }

            if (blockfrom == BlocksRegister.STORMCONDUCTOR) {
               IBlockState st = BlocksRegister.STORMCONDUCTOR.getStateFromMeta(0);
               world.setBlockState(pos, st);
               world.playSound(
                  (EntityPlayer)null,
                  pos,
                  BlocksRegister.STORMCONDUCTOR.getSoundType(st, world, pos, player).getPlaceSound(),
                  SoundCategory.BLOCKS,
                  0.8F,
                  0.9F + itemRand.nextFloat() / 5.0F
               );
               return new ActionResult(EnumActionResult.SUCCESS, itemstack);
            }
         }

         return new ActionResult(EnumActionResult.FAIL, itemstack);
      }
   }
}
