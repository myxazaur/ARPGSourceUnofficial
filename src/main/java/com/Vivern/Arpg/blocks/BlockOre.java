package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.loot.OreDrop;
import com.Vivern.Arpg.main.BlocksRegister;
import com.google.common.base.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockOre extends Block {
   public OreDrop[] drops;
   public int minXP;
   public int maxXP;
   public BlockRenderLayer layer = BlockRenderLayer.SOLID;

   public BlockOre(Material mater, String name, float hard, float resi, int minXP, int maxXP) {
      super(mater);
      this.setRegistryName(name);
      this.setTranslationKey(name);
      this.blockHardness = hard;
      this.blockResistance = resi;
      this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
      this.minXP = minXP;
      this.maxXP = maxXP;
   }

   @SideOnly(Side.CLIENT)
   public BlockRenderLayer getRenderLayer() {
      return this.layer;
   }

   public BlockOre setRenderLayer(BlockRenderLayer render) {
      this.layer = render;
      return this;
   }

   public BlockOre setHarvestLvl(String toolClass, int level) {
      this.setHarvestLevel(toolClass, level);
      return this;
   }

   public Block setSoundType(SoundType sound) {
      return super.setSoundType(sound);
   }

   public void setOreDrops(OreDrop... drops) {
      this.drops = drops;
   }

   public boolean isReplaceableOreGen(IBlockState state, IBlockAccess world, BlockPos pos, Predicate<IBlockState> target) {
      return false;
   }

   public boolean isFullCube(IBlockState state) {
      return true;
   }

   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
      if (!worldIn.isRemote && !worldIn.restoringBlockSnapshots) {
         List<ItemStack> stacksd = new ArrayList<>();

         for (OreDrop odrop : this.drops) {
            stacksd.add(odrop.getStackDropped(worldIn, pos, state, fortune, RANDOM));
         }

         chance = ForgeEventFactory.fireBlockHarvesting(stacksd, worldIn, pos, state, fortune, chance, false, (EntityPlayer)this.harvesters.get());

         for (ItemStack drop : stacksd) {
            if (worldIn.rand.nextFloat() <= chance) {
               spawnAsEntity(worldIn, pos, drop);
            }
         }
      }
   }

   public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune) {
      Random rand = world instanceof World ? ((World)world).rand : RANDOM;
      return MathHelper.getInt(rand, this.minXP, this.maxXP);
   }

   public static class BlockOreHard extends BlockOre implements IBlockHardBreak {
      public BlocksRegister.Hardres hardres;
      public String tool;

      public BlockOreHard(Material mater, String name, BlocksRegister.Hardres hardres, String tool, int minXP, int maxXP) {
         super(mater, name, hardres.HARDNESS, hardres.RESISTANCE, minXP, maxXP);
         this.setHarvestLevel(tool, hardres.LVL);
         this.hardres = hardres;
         this.tool = tool;
      }

      @Override
      public float getBlockBreakingSpeed(World world, String tool, int toolLevel, IBlockState state, BlockPos pos, float originalSpeed) {
         return toolLevel >= this.hardres.LVL && tool.equals(this.tool) ? originalSpeed * this.hardres.FAST : originalSpeed * this.hardres.SLOW;
      }
   }
}
