//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.Mana;
import java.util.Random;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BrownSlime extends BlockBlockHard {
   public BrownSlime() {
      super(Material.CLAY, "brown_slime", BlocksRegister.HR_RADIOACTIVE_WASTE, "shovel", true);
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setSoundType(SoundType.SLIME);
      this.setSlipperiness(0.8F);
   }

   @Override
   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   @Override
   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
      return Items.SLIME_BALL;
   }

   public int quantityDropped(IBlockState state, int fortune, Random random) {
      return 9;
   }

   @SideOnly(Side.CLIENT)
   @Override
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.TRANSLUCENT;
   }

   public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
      if (entityIn.isSneaking()) {
         super.onFallenUpon(worldIn, pos, entityIn, fallDistance);
      } else {
         entityIn.fall(fallDistance, 0.0F);
      }
   }

   public void onLanded(World worldIn, Entity entityIn) {
      if (entityIn.isSneaking()) {
         if (!(entityIn instanceof EntityLivingBase)) {
            super.onLanded(worldIn, entityIn);
         } else {
            entityIn.motionY = -entityIn.motionY * 0.7;
         }
      } else if (entityIn.motionY < 0.0) {
         entityIn.motionY = -entityIn.motionY;
         if (!(entityIn instanceof EntityLivingBase)) {
            entityIn.motionY *= 0.8;
         }
      }
   }

   public void onEntityWalk(World world, BlockPos pos, Entity entityIn) {
      if (Math.abs(entityIn.motionY) < 0.1 && !entityIn.isSneaking()) {
         double d0 = 0.4 + Math.abs(entityIn.motionY) * 0.2;
         entityIn.motionX *= d0;
         entityIn.motionZ *= d0;
      }

      if (!world.isRemote && entityIn instanceof EntityPlayer) {
         Mana.addRad((EntityPlayer)entityIn, 1, true);
      }

      super.onEntityWalk(world, pos, entityIn);
   }

   public boolean isStickyBlock(IBlockState state) {
      return true;
   }
}
