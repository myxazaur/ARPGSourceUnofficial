//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.dimensions.generationutils.ReplaceOnlyReplaceable;
import com.Vivern.Arpg.main.BlocksRegister;
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

public class ToxiberryTreeSapling extends Block implements IGrowable {
   protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.125, 0.0, 0.125, 0.875, 0.875, 0.875);

   public ToxiberryTreeSapling() {
      super(Material.PLANTS);
      this.setRegistryName("toxiberry_tree_sapling");
      this.setTranslationKey("toxiberry_tree_sapling");
      this.blockHardness = 0.0F;
      this.blockResistance = 0.0F;
      this.setSoundType(SoundType.PLANT);
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setTickRandomly(true);
   }

   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
      Block block = worldIn.getBlockState(pos.down()).getBlock();
      return block == BlocksRegister.TOXICGRASS
         || block == BlocksRegister.TOXICDIRT
         || block == BlocksRegister.SLUDGE
         || block == BlocksRegister.JUNK
         || block == BlocksRegister.NUCLEARWASTE;
   }

   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
      super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
      Block block = worldIn.getBlockState(pos.down()).getBlock();
      if (block != BlocksRegister.TOXICGRASS
         && block != BlocksRegister.TOXICDIRT
         && block != BlocksRegister.SLUDGE
         && block != BlocksRegister.JUNK
         && block != BlocksRegister.NUCLEARWASTE) {
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

   public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
      if (rand.nextFloat() < 0.15) {
         Block block = worldIn.getBlockState(pos.down()).getBlock();
         worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
         if (block == BlocksRegister.TOXICGRASS) {
            WorldServer worldServer = (WorldServer)worldIn;
            MinecraftServer minecraftServer = worldIn.getMinecraftServer();
            TemplateManager templateManager = worldServer.getStructureTemplateManager();
            Template template = templateManager.get(minecraftServer, new ResourceLocation("arpg:toxic_tree_" + (rand.nextInt(3) + 1)));
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

            template.addBlocksToWorld(worldIn, pos.add(sx * 3, 0, sz * 3), ReplaceOnlyReplaceable.instance, settings, 2);
         } else if (block == BlocksRegister.NUCLEARWASTE) {
            worldIn.setBlockState(pos.down(), Blocks.AIR.getDefaultState());
            WorldServer worldServerx = (WorldServer)worldIn;
            MinecraftServer minecraftServerx = worldIn.getMinecraftServer();
            TemplateManager templateManagerx = worldServerx.getStructureTemplateManager();
            Template templatex = templateManagerx.get(minecraftServerx, new ResourceLocation("arpg:toxic_swamp_tree_" + (rand.nextInt(3) + 1)));
            PlacementSettings settingsx = new PlacementSettings();
            int sxx = -1;
            int szx = -1;
            int swrx = rand.nextInt(4);
            if (swrx == 0) {
               settingsx.setRotation(Rotation.CLOCKWISE_180);
               sxx = 1;
               szx = 1;
            }

            if (swrx == 1) {
               settingsx.setRotation(Rotation.CLOCKWISE_90);
               sxx = 1;
               szx = -1;
            }

            if (swrx == 2) {
               settingsx.setRotation(Rotation.COUNTERCLOCKWISE_90);
               sxx = -1;
               szx = 1;
            }

            if (swrx == 3) {
               settingsx.setRotation(Rotation.NONE);
            }

            templatex.addBlocksToWorld(worldIn, pos.add(sxx * 2, -1, szx * 2), ReplaceOnlyReplaceable.instance, settingsx, 2);
         } else if (block == BlocksRegister.SLUDGE) {
            WorldServer worldServerxx = (WorldServer)worldIn;
            MinecraftServer minecraftServerxx = worldIn.getMinecraftServer();
            TemplateManager templateManagerxx = worldServerxx.getStructureTemplateManager();
            int ty = rand.nextInt(3) + 1;
            Template templatexx = templateManagerxx.get(minecraftServerxx, new ResourceLocation("arpg:toxic_high_tree_" + ty));
            PlacementSettings settingsxx = new PlacementSettings();
            int sxxx = -1;
            int szxx = -1;
            int swrxx = rand.nextInt(4);
            if (swrxx == 0) {
               settingsxx.setRotation(Rotation.CLOCKWISE_180);
               sxxx = 1;
               szxx = 1;
            }

            if (swrxx == 1) {
               settingsxx.setRotation(Rotation.CLOCKWISE_90);
               sxxx = 1;
               szxx = -1;
            }

            if (swrxx == 2) {
               settingsxx.setRotation(Rotation.COUNTERCLOCKWISE_90);
               sxxx = -1;
               szxx = 1;
            }

            if (swrxx == 3) {
               settingsxx.setRotation(Rotation.NONE);
            }

            if (ty == 1) {
               templatexx.addBlocksToWorld(worldIn, pos.add(sxxx * 10, -5, szxx * 10), ReplaceOnlyReplaceable.instance, settingsxx, 2);
            }

            if (ty == 2) {
               templatexx.addBlocksToWorld(worldIn, pos.add(sxxx * 4, 0, szxx * 4), ReplaceOnlyReplaceable.instance, settingsxx, 2);
            }

            if (ty == 3) {
               templatexx.addBlocksToWorld(worldIn, pos.add(sxxx * 6, 0, szxx * 6), ReplaceOnlyReplaceable.instance, settingsxx, 2);
            }
         }
      }
   }
}
