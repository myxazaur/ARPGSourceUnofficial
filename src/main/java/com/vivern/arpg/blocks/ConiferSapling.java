package com.vivern.arpg.blocks;

import com.vivern.arpg.dimensions.generationutils.ReplaceOnlyReplaceable;
import com.vivern.arpg.main.BlocksRegister;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
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

public class ConiferSapling extends Block implements IGrowable {
   public static final PropertyBool SNOWY = PropertyBool.create("snowy");
   protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.125, 0.0, 0.125, 0.875, 0.875, 0.875);

   public ConiferSapling() {
      super(Material.PLANTS);
      this.setRegistryName("conifer_sapling");
      this.setTranslationKey("conifer_sapling");
      this.blockHardness = 0.0F;
      this.blockResistance = 0.0F;
      this.setSoundType(SoundType.PLANT);
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setTickRandomly(true);
   }

   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
      return worldIn.getBlockState(pos.down()).getBlock() == Blocks.SNOW
         ? state.withProperty(SNOWY, true)
         : state.withProperty(SNOWY, false);
   }

   public int quantityDropped(Random random) {
      return 0;
   }

   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
      Block block = worldIn.getBlockState(pos.down()).getBlock();
      return block == Blocks.SNOW || block == Blocks.GRASS || block == Blocks.DIRT || block == BlocksRegister.SNOWICE;
   }

   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
      super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
      Block block = worldIn.getBlockState(pos.down()).getBlock();
      if (block != Blocks.SNOW && block != Blocks.GRASS && block != Blocks.DIRT && block != BlocksRegister.SNOWICE) {
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
      return light > 10;
   }

   public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
      return true;
   }

   public void grow(World worldIn, Random random, BlockPos position, IBlockState state) {
      if (random.nextFloat() < 0.1) {
         worldIn.setBlockToAir(position);
         Block blockd = worldIn.getBlockState(position.down()).getBlock();
         if (blockd == Blocks.SNOW || blockd == Blocks.GRASS || blockd == Blocks.DIRT || blockd == BlocksRegister.SNOWICE) {
            WorldServer worldServer = (WorldServer)worldIn;
            MinecraftServer minecraftServer = worldIn.getMinecraftServer();
            TemplateManager templateManager = worldServer.getStructureTemplateManager();
            Template template = templateManager.get(minecraftServer, new ResourceLocation("arpg:conifer_tree_" + (random.nextInt(3) + 1)));
            PlacementSettings settings = new PlacementSettings();
            int sx = -1;
            int sz = -1;
            int swr = random.nextInt(4);
            BlockPos transfpos = position;
            if (swr == 0) {
               settings.setRotation(Rotation.CLOCKWISE_180);
               sx = 1;
               sz = 1;
               transfpos = position.add(-1, 0, -1);
            }

            if (swr == 1) {
               settings.setRotation(Rotation.CLOCKWISE_90);
               sx = 1;
               sz = -1;
               transfpos = position.add(-1, 0, 0);
            }

            if (swr == 2) {
               settings.setRotation(Rotation.COUNTERCLOCKWISE_90);
               sx = -1;
               sz = 1;
               transfpos = position.add(0, 0, -1);
            }

            if (swr == 3) {
               settings.setRotation(Rotation.NONE);
            }

            template.addBlocksToWorld(worldIn, position.add(sx * 7, random.nextInt(6) + 4, sz * 7), ReplaceOnlyReplaceable.instance, settings, 2);

            for (int rt = -4; rt < 14; rt++) {
               BlockPos fpos = transfpos.up(rt);
               if (!worldIn.getBlockState(fpos).getBlock().isReplaceable(worldIn, fpos)
                  && worldIn.getBlockState(fpos).getBlock() != Blocks.SNOW) {
                  if (worldIn.getBlockState(fpos).getBlock() == BlocksRegister.CONIFERLOG) {
                     break;
                  }
               } else {
                  worldIn.setBlockState(fpos, BlocksRegister.CONIFERLOG.getDefaultState());
               }
            }

            for (int rtx = -2; rtx < 14; rtx++) {
               BlockPos fpos = transfpos.add(1, 0, 1).up(rtx);
               if (!worldIn.getBlockState(fpos).getBlock().isReplaceable(worldIn, fpos)
                  && worldIn.getBlockState(fpos).getBlock() != Blocks.SNOW) {
                  if (worldIn.getBlockState(fpos).getBlock() == BlocksRegister.CONIFERLOG) {
                     break;
                  }
               } else {
                  worldIn.setBlockState(fpos, BlocksRegister.CONIFERLOG.getDefaultState());
               }
            }

            for (int rtxx = -2; rtxx < 14; rtxx++) {
               BlockPos fpos = transfpos.add(0, 0, 1).up(rtxx);
               if (!worldIn.getBlockState(fpos).getBlock().isReplaceable(worldIn, fpos)
                  && worldIn.getBlockState(fpos).getBlock() != Blocks.SNOW) {
                  if (worldIn.getBlockState(fpos).getBlock() == BlocksRegister.CONIFERLOG) {
                     break;
                  }
               } else {
                  worldIn.setBlockState(fpos, BlocksRegister.CONIFERLOG.getDefaultState());
               }
            }

            for (int rtxxx = -2; rtxxx < 14; rtxxx++) {
               BlockPos fpos = transfpos.add(1, 0, 0).up(rtxxx);
               if (!worldIn.getBlockState(fpos).getBlock().isReplaceable(worldIn, fpos)
                  && worldIn.getBlockState(fpos).getBlock() != Blocks.SNOW) {
                  if (worldIn.getBlockState(fpos).getBlock() == BlocksRegister.CONIFERLOG) {
                     break;
                  }
               } else {
                  worldIn.setBlockState(fpos, BlocksRegister.CONIFERLOG.getDefaultState());
               }
            }
         }
      }
   }

   public IBlockState getStateFromMeta(int meta) {
      return this.getDefaultState().withProperty(SNOWY, meta > 0);
   }

   public int getMetaFromState(IBlockState state) {
      return state.getValue(SNOWY) ? 1 : 0;
   }

   protected BlockStateContainer createBlockState() {
      return new BlockStateContainer(this, new IProperty[]{SNOWY});
   }
}
