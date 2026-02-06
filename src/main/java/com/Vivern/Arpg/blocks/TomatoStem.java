package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Sounds;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class TomatoStem extends PlantWithStages {
   public TomatoStem() {
      super(
         Material.PLANTS,
         "tomato_stem",
         new Block[]{Blocks.FARMLAND},
         0.0F,
         0.0F,
         SoundType.PLANT,
         4,
         new AxisAlignedBB[]{
            GetMOP.newAABB(10, 9, 0), GetMOP.newAABB(12, 12, 0), GetMOP.newAABB(14, 18, 0), GetMOP.newAABB(14, 22, 0), GetMOP.newAABB(14, 22, 0)
         },
         10,
         15,
         0.25F,
         null
      );
      this.canUseBonemeal = true;
   }

   @Override
   public Vec3d getOffset(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
      return FARMLAND_OFFSET;
   }

   public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
      super.onEntityCollision(worldIn, pos, state, entityIn);
      entityIn.motionX *= 0.8;
      entityIn.motionZ *= 0.8;
   }

   @Override
   public boolean onBlockActivated(
      World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ
   ) {
      if (!worldIn.isRemote && player.getHeldItem(hand).isEmpty() && (Integer)state.getValue(AGE) == 4) {
         spawnAsEntity(worldIn, pos, new ItemStack(ItemsRegister.TOMATOCHERRY));
         worldIn.playSound((EntityPlayer)null, pos, Sounds.fruit_pickup, SoundCategory.BLOCKS, 0.6F, 0.9F + RANDOM.nextFloat() / 5.0F);
         worldIn.setBlockState(pos, state.withProperty(AGE, RANDOM.nextFloat() < 0.75F ? 2 : 3));
         return true;
      } else {
         return super.onBlockActivated(worldIn, pos, state, player, hand, facing, hitX, hitY, hitZ);
      }
   }
}
