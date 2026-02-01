package com.vivern.arpg.blocks;

import com.vivern.arpg.dimensions.generationutils.ReplaceOnlyReplaceable;
import com.vivern.arpg.main.BlocksRegister;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MutatedFlowerRed extends Block implements IGrowable {
   protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.125, 0.0, 0.125, 0.875, 0.875, 0.875);

   public MutatedFlowerRed() {
      super(Material.PLANTS);
      this.setRegistryName("mutated_flower_red");
      this.setTranslationKey("mutated_flower_red");
      this.blockHardness = 0.0F;
      this.blockResistance = 0.0F;
      this.setSoundType(SoundType.PLANT);
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setTickRandomly(true);
   }

   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
      Block block = worldIn.getBlockState(pos.down()).getBlock();
      return block == BlocksRegister.TOXICGRASS || block == BlocksRegister.TOXICDIRT || block == BlocksRegister.TOXIBERRYLOG;
   }

   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
      super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
      Block block = worldIn.getBlockState(pos.down()).getBlock();
      if (block != BlocksRegister.TOXICGRASS && block != BlocksRegister.TOXICDIRT && block != BlocksRegister.TOXIBERRYLOG) {
         this.dropBlockAsItem(worldIn, pos, state, 0);
         worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
      }
   }

   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      return AABB;
   }

   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
      return NULL_AABB;
   }

   @SideOnly(Side.CLIENT)
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.CUTOUT;
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   public boolean isFullCube(IBlockState state) {
      return false;
   }

   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
      this.grow(worldIn, rand, pos, state);
   }

   public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
      int light = Math.max(worldIn.getLightFor(EnumSkyBlock.BLOCK, pos), worldIn.getLightFor(EnumSkyBlock.SKY, pos));
      return light > 13;
   }

   public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
      return true;
   }

   public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
      if (rand.nextFloat() < 0.07) {
         Block block = worldIn.getBlockState(pos.down()).getBlock();
         if (block == BlocksRegister.TOXICGRASS || block == BlocksRegister.TOXICDIRT) {
            worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
            WorldServer worldServer = (WorldServer)worldIn;
            MinecraftServer minecraftServer = worldIn.getMinecraftServer();
            TemplateManager templateManager = worldServer.getStructureTemplateManager();
            boolean small = rand.nextFloat() < 0.4F;
            Template template = templateManager.get(
               minecraftServer, new ResourceLocation("arpg" + (small ? ":toxic_tree_flower_3" : ":toxic_tree_flower_2"))
            );
            PlacementSettings settings = new PlacementSettings();
            int sx = -1;
            int sz = -1;
            int swr = rand.nextInt(4);
            if (swr == 0) {
               settings.setRotation(Rotation.CLOCKWISE_180);
               sx = 1;
               sz = 1;
            }

            if (swr == 1) {
               settings.setRotation(Rotation.CLOCKWISE_90);
               sx = 1;
               sz = -1;
            }

            if (swr == 2) {
               settings.setRotation(Rotation.COUNTERCLOCKWISE_90);
               sx = -1;
               sz = 1;
            }

            if (swr == 3) {
               settings.setRotation(Rotation.NONE);
            }

            template.addBlocksToWorld(worldIn, pos.add(sx * (small ? 3 : 8), 0, sz * (small ? 3 : 8)), ReplaceOnlyReplaceable.instance, settings, 2);
         }
      }
   }
}
