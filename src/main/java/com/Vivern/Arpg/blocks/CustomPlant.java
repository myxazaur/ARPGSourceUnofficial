//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.elements.CustomPlantSeed;
import com.Vivern.Arpg.elements.CustomPlantSeedEatable;
import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.CreateItemFile;
import com.Vivern.Arpg.main.ItemsRegister;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.Block.EnumOffsetType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CustomPlant extends Block implements IGrowable, IShearable {
   public static final PropertyBool GROWED = PropertyBool.create("growed");
   public static AxisAlignedBB CP_AABB = new AxisAlignedBB(0.2, 0.0, 0.2, 0.8, 0.8, 0.8);
   public static AxisAlignedBB CPsmall_AABB = new AxisAlignedBB(0.25, 0.0, 0.25, 0.75, 0.3, 0.75);
   public static AxisAlignedBB CPdouble_AABB = new AxisAlignedBB(0.2, 0.0, 0.2, 0.8, 1.2, 0.8);
   public AxisAlignedBB COLLISION_AABB = null;
   public final Block[] groundBlocks;
   public final boolean canUseBonemeal;
   public final String drops;
   public int minLightForGrow = 0;
   public int maxLightForGrow = 15;
   public float growChance = 0.0F;
   public Item seed = null;
   public int modelType = 0;
   public int seedRadiation = 0;

   public CustomPlant(
      String name,
      float hardnessResistance,
      float lightLvl,
      SoundType soundType,
      AxisAlignedBB collision_aabb,
      Block[] groundBlocks,
      boolean canUseBonemeal,
      String drops,
      int minLightForGrow,
      int maxLightForGrow,
      float growChance,
      int modelType
   ) {
      super(Material.PLANTS);
      this.setRegistryName(name);
      this.setTranslationKey(name);
      this.blockHardness = hardnessResistance;
      this.blockResistance = hardnessResistance;
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setLightLevel(lightLvl);
      this.setSoundType(soundType);
      this.COLLISION_AABB = collision_aabb;
      this.groundBlocks = groundBlocks;
      this.setTickRandomly(true);
      this.canUseBonemeal = canUseBonemeal;
      this.drops = drops;
      this.minLightForGrow = minLightForGrow;
      this.maxLightForGrow = maxLightForGrow;
      this.growChance = growChance;
      this.modelType = modelType;
   }

   public static CustomPlant createCustomPlant(
      String name,
      float hardnessResistance,
      float lightLvl,
      SoundType soundType,
      AxisAlignedBB collision_aabb,
      Block[] groundBlocks,
      boolean canUseBonemeal,
      String drops,
      int minLightForGrow,
      int maxLightForGrow,
      float growChance,
      int seedEatable,
      Potion potion,
      int dur,
      int amp,
      float effectChance,
      int modelType
   ) {
      CustomPlant plant = new CustomPlant(
         name,
         hardnessResistance,
         lightLvl,
         soundType,
         collision_aabb,
         groundBlocks,
         canUseBonemeal,
         drops,
         minLightForGrow,
         maxLightForGrow,
         growChance,
         modelType
      );
      Item seed = (Item)(seedEatable > 0 ? new CustomPlantSeedEatable(plant, seedEatable, potion, dur, amp, effectChance) : new CustomPlantSeed(plant));
      plant.seed = seed;
      CreateItemFile.customPlantResLocationCreate(plant, modelType);
      BlocksRegister.forrender.add(plant);
      ItemsRegister.forrender.add(seed);
      return plant;
   }

   public CustomPlant setFuelToSeed(int time) {
      if (this.seed instanceof CustomPlantSeed) {
         ((CustomPlantSeed)this.seed).burntime = time;
      } else if (this.seed instanceof CustomPlantSeedEatable) {
         ((CustomPlantSeedEatable)this.seed).burntime = time;
      }

      return this;
   }

   public CustomPlant setSeedRadioactive(int rad) {
      this.seedRadiation = rad;
      return this;
   }

   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
      if (rand.nextFloat() < this.growChance) {
         int light = Math.max(worldIn.getLightFor(EnumSkyBlock.BLOCK, pos), worldIn.getLightFor(EnumSkyBlock.SKY, pos));
         if (light <= this.maxLightForGrow && light >= this.minLightForGrow) {
            this.grow(worldIn, rand, pos, state);
         }
      }
   }

   public int quantityDropped(IBlockState state, int fortune, Random random) {
      return this.drops.isEmpty() ? 1 : 0;
   }

   public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos) {
      return (Boolean)world.getBlockState(pos).getValue(GROWED);
   }

   public EnumOffsetType getOffsetType() {
      return EnumOffsetType.XYZ;
   }

   public void getDrops(NonNullList<ItemStack> lastdrops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
      super.getDrops(lastdrops, world, pos, state, fortune);
      if ((Boolean)state.getValue(GROWED)) {
         String[] lootTypes = this.drops.split(" ");

         for (String lootType : lootTypes) {
            String[] words = lootType.split(",");
            Item item = Item.getByNameOrId(words[0]);
            int countmin = Integer.parseInt(words[1]);
            int countmax = Integer.parseInt(words[2]);
            int meta = Integer.parseInt(words[3]);
            lastdrops.add(new ItemStack(item, countmin + RANDOM.nextInt(countmax - countmin + 1), meta));
         }
      } else if (RANDOM.nextFloat() < 0.7) {
         lastdrops.add(new ItemStack(this.seed, 1));
      }
   }

   public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
      return !(Boolean)state.getValue(GROWED);
   }

   public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
      return this.canUseBonemeal;
   }

   public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
      worldIn.setBlockState(pos, state.withProperty(GROWED, true), 2);
   }

   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
      if (!this.canBlockStay(worldIn, pos)) {
         this.dropBlock(worldIn, pos, state);
      }
   }

   private void dropBlock(World worldIn, BlockPos pos, IBlockState state) {
      worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
      this.dropBlockAsItem(worldIn, pos, state, 0);
   }

   public boolean canBlockStay(World worldIn, BlockPos pos) {
      Block blockd = worldIn.getBlockState(pos.down()).getBlock();

      for (Block block : this.groundBlocks) {
         if (blockd == block) {
            return true;
         }
      }

      return false;
   }

   @SideOnly(Side.CLIENT)
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.CUTOUT;
   }

   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
      return this.COLLISION_AABB != null ? this.COLLISION_AABB : NULL_AABB;
   }

   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      if (!(Boolean)state.getValue(GROWED)) {
         return CPsmall_AABB;
      } else if (this.modelType == 1) {
         return CP_AABB;
      } else if (this.modelType == 2) {
         return CPdouble_AABB;
      } else {
         return this.modelType == 3 ? CP_AABB : NULL_AABB;
      }
   }

   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
      return super.canPlaceBlockAt(worldIn, pos) && this.canBlockStay(worldIn, pos);
   }

   public IBlockState getStateFromMeta(int meta) {
      return this.getDefaultState().withProperty(GROWED, meta == 1);
   }

   public int getMetaFromState(IBlockState state) {
      return state.getValue(GROWED) ? 1 : 0;
   }

   protected BlockStateContainer createBlockState() {
      return new BlockStateContainer(this, new IProperty[]{GROWED});
   }

   public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
      return this.getDefaultState().withProperty(GROWED, true);
   }

   public boolean isFullBlock(IBlockState state) {
      return false;
   }

   public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
      return false;
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   public NonNullList<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
      return NonNullList.withSize(1, new ItemStack(this, 1, 0));
   }
}
