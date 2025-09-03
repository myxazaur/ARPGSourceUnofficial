//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.dimensions.generationutils.WorldGenGroundFoliage;
import com.Vivern.Arpg.main.BlocksRegister;
import java.util.Random;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ToxicGrass extends BlockBlockHard {
   public ToxicGrass() {
      super(Material.GRASS, "toxic_grass", BlocksRegister.HR_RADIOACTIVE_WASTE, "shovel", true);
      this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
      this.setTickRandomly(true);
      this.setSoundType(SoundType.PLANT);
   }

   @Override
   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
      return Item.getItemFromBlock(BlocksRegister.TOXICDIRT);
   }

   @Override
   public boolean isFullCube(IBlockState state) {
      return true;
   }

   public boolean onBlockActivated(
      World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ
   ) {
      if (facing == EnumFacing.UP && playerIn.getHeldItem(hand).getItem() == Items.DYE && playerIn.getHeldItem(hand).getMetadata() == 15) {
         WorldGenGroundFoliage tallgrass = new WorldGenGroundFoliage(BlocksRegister.TOXICTALLGRASS, 20, 5, 3);
         playerIn.getHeldItem(hand).shrink(1);
         tallgrass.generate(worldIn, RANDOM, pos);
         return true;
      } else {
         return false;
      }
   }

   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
      if (!worldIn.isRemote) {
         if (!worldIn.isAreaLoaded(pos, 3)) {
            return;
         }

         if (worldIn.getLightFromNeighbors(pos.up()) < 4 && worldIn.getBlockState(pos.up()).getLightOpacity(worldIn, pos.up()) > 2) {
            worldIn.setBlockState(pos, BlocksRegister.TOXICDIRT.getDefaultState());
         } else if (worldIn.getLightFromNeighbors(pos.up()) >= 9) {
            for (int i = 0; i < 4; i++) {
               BlockPos blockpos = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);
               if (blockpos.getY() >= 0 && blockpos.getY() < 256 && !worldIn.isBlockLoaded(blockpos)) {
                  return;
               }

               IBlockState iblockstate = worldIn.getBlockState(blockpos.up());
               IBlockState iblockstate1 = worldIn.getBlockState(blockpos);
               if (iblockstate1.getBlock() == BlocksRegister.TOXICDIRT
                  && worldIn.getLightFromNeighbors(blockpos.up()) >= 4
                  && iblockstate.getLightOpacity(worldIn, pos.up()) <= 2) {
                  worldIn.setBlockState(blockpos, this.getDefaultState());
               }
            }
         }
      }
   }
}
