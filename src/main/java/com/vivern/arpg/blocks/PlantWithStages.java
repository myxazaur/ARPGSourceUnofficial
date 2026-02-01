package com.vivern.arpg.blocks;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.Block.EnumOffsetType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PlantWithStages extends Block implements IGrowable {
   public static Vec3d FARMLAND_OFFSET = new Vec3d(0.0, -0.0625, 0.0);
   public static PropertyInteger AGE = PropertyInteger.create("age", 0, 15);
   public int maxStage;
   public Block[] groundBlocks;
   public boolean canUseBonemeal;
   @Nullable
   public Item specialFertilizer;
   public AxisAlignedBB[] COLLISION_BOXES;
   public int minLightForGrow = 0;
   public int maxLightForGrow = 15;
   public float growChance = 0.0F;
   public String drops;
   public double randOffsetAmountXZ = 0.5;
   public double randOffsetAmountY = 0.4;

   public PlantWithStages(
      Material material,
      String name,
      Block[] groundBlocks,
      float hardness,
      float resistance,
      SoundType soundType,
      int maxStage,
      AxisAlignedBB[] COLLISION_BOXES,
      int minLightForGrow,
      int maxLightForGrow,
      float growChance,
      String drops
   ) {
      this(material);
      this.setRegistryName(name);
      this.setTranslationKey(name);
      this.blockHardness = hardness;
      this.blockResistance = resistance;
      this.setSoundType(soundType);
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setTickRandomly(true);
      this.maxStage = maxStage;
      this.COLLISION_BOXES = COLLISION_BOXES;
      this.minLightForGrow = minLightForGrow;
      this.maxLightForGrow = maxLightForGrow;
      this.growChance = growChance;
      this.drops = drops;
      this.groundBlocks = groundBlocks;
   }

   public PlantWithStages(Material material) {
      super(material);
   }

   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
      return super.canPlaceBlockAt(worldIn, pos) && this.canStayAtPos(worldIn, pos);
   }

   public boolean canStayAtPos(World worldIn, BlockPos pos) {
      IBlockState st = worldIn.getBlockState(pos.down());
      Block block = st.getBlock();

      for (Block block2 : this.groundBlocks) {
         if (block2 == block) {
            return true;
         }
      }

      return false;
   }

   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
      super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
      if (!this.canStayAtPos(worldIn, pos)) {
         this.dropBlockAsItem(worldIn, pos, state, 0);
         worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
      }
   }

   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      return this.COLLISION_BOXES[state.getValue(AGE)];
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
      if (!worldIn.isRemote) {
         this.grow(worldIn, rand, pos, state);
      }
   }

   public boolean onBlockActivated(
      World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ
   ) {
      if (!worldIn.isRemote && this.specialFertilizer != null && player.getHeldItem(hand).getItem() == this.specialFertilizer) {
         player.getHeldItem(hand).shrink(1);
         this.grow(worldIn, RANDOM, pos, state);
         return true;
      } else {
         return super.onBlockActivated(worldIn, pos, state, player, hand, facing, hitX, hitY, hitZ);
      }
   }

   public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
      return true;
   }

   public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
      return this.canUseBonemeal;
   }

   public EnumOffsetType getOffsetType() {
      return EnumOffsetType.XYZ;
   }

   public Vec3d getOffset(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
      EnumOffsetType block$enumoffsettype = this.getOffsetType();
      if (block$enumoffsettype == EnumOffsetType.NONE) {
         return Vec3d.ZERO;
      } else {
         long i = MathHelper.getCoordinateRandom(pos.getX(), 0, pos.getZ());
         return new Vec3d(
            ((float)(i >> 16 & 15L) / 15.0F - 0.5) * this.randOffsetAmountXZ,
            ((float)(i >> 20 & 15L) / 15.0F - 0.4) * this.randOffsetAmountY,
            ((float)(i >> 24 & 15L) / 15.0F - 0.5) * this.randOffsetAmountXZ
         );
      }
   }

   public void getDrops(NonNullList<ItemStack> lastdrops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
      if (this.drops != null && (Integer)state.getValue(AGE) >= this.maxStage) {
         String[] lootTypes = this.drops.split(" ");

         for (String lootType : lootTypes) {
            String[] words = lootType.split(",");
            Item item = Item.getByNameOrId(words[0]);
            int countmin = Integer.parseInt(words[1]);
            int countmax = Integer.parseInt(words[2]);
            int meta = Integer.parseInt(words[3]);
            lastdrops.add(new ItemStack(item, countmin + RANDOM.nextInt(countmax - countmin + 1), meta));
         }
      }
   }

   public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
      if (rand.nextFloat() < this.growChance) {
         if (this.canStayAtPos(worldIn, pos)) {
            worldIn.setBlockState(pos, state.withProperty(AGE, Math.min((Integer)state.getValue(AGE) + 1, this.maxStage)));
         } else {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
         }
      }
   }

   public IBlockState getStateFromMeta(int meta) {
      return this.getDefaultState().withProperty(AGE, meta);
   }

   public int getMetaFromState(IBlockState state) {
      return (Integer)state.getValue(AGE);
   }

   protected BlockStateContainer createBlockState() {
      return new BlockStateContainer(this, new IProperty[]{AGE});
   }
}
