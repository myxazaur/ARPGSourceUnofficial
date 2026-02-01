package com.vivern.arpg.blocks;

import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.Mana;
import com.vivern.arpg.main.NBTHelper;
import java.util.List;
import java.util.Random;
import net.minecraft.block.SoundType;
import net.minecraft.block.Block.EnumOffsetType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class ToxicBarrel extends BlockBlockHard implements IHasSubtypes {
   public static final PropertyInteger VARIANT = PropertyInteger.create("type", 0, 12);
   public static final AxisAlignedBB AABB_STAY = new AxisAlignedBB(0.1875, 0.0, 0.1875, 0.8125, 1.0, 0.8125);
   public static final AxisAlignedBB AABB_FLAT1 = new AxisAlignedBB(0.1875, 0.0, 0.0, 0.8125, 0.5, 1.0);
   public static final AxisAlignedBB AABB_FLAT2 = new AxisAlignedBB(0.0, 0.0, 0.1875, 1.0, 0.5, 0.8125);
   public static final AxisAlignedBB AABB_FLAT3 = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.5, 1.0);

   public ToxicBarrel() {
      super(Material.IRON, "toxic_barrel", BlocksRegister.HR_RADIOACTIVE_WASTE, "pickaxe", false);
      this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, 0));
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setSoundType(SoundType.METAL);
      this.setTickRandomly(true);
   }

   public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
      if (!worldIn.isRemote) {
         double distance = 4.0;
         AxisAlignedBB axisalignedbb = new AxisAlignedBB(pos.add(-distance, -distance, -distance), pos.add(distance, distance, distance));
         List<EntityPlayer> list = worldIn.getEntitiesWithinAABB(EntityPlayer.class, axisalignedbb);
         if (!list.isEmpty()) {
            for (EntityPlayer player : list) {
               Mana.addRad(player, 42, true);
            }
         }
      }

      super.randomTick(worldIn, pos, state, random);
   }

   @Override
   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      int i = (Integer)state.getValue(VARIANT);
      if (i < 5) {
         return AABB_STAY;
      } else if (i < 7 || i == 9) {
         return AABB_FLAT1;
      } else {
         return i == 10 ? AABB_FLAT3 : AABB_FLAT2;
      }
   }

   @Override
   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
      int i = (Integer)blockState.getValue(VARIANT);
      if (i < 5) {
         return AABB_STAY;
      } else if (i < 7 || i == 9) {
         return AABB_FLAT1;
      } else {
         return i == 10 ? AABB_FLAT3 : AABB_FLAT2;
      }
   }

   public IBlockState getStateForPlacement(
      World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand
   ) {
      return placer.getHeldItem(hand).getItem() == Item.getItemFromBlock(BlocksRegister.TOXICBARREL)
         ? this.getDefaultState().withProperty(VARIANT, MathHelper.clamp(NBTHelper.GetNBTint(placer.getHeldItem(hand), "type"), 0, 12))
         : this.getDefaultState();
   }

   @Override
   public EnumOffsetType getOffsetType() {
      return EnumOffsetType.XYZ;
   }

   @Override
   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   @Override
   public boolean isFullCube(IBlockState state) {
      return false;
   }

   public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
      return this.getItemStack(state);
   }

   public ItemStack getItemStack(IBlockState state) {
      int variant = (Integer)state.getValue(VARIANT);
      ItemStack stack = new ItemStack(this);
      NBTHelper.GiveNBTint(stack, variant, "type");
      NBTHelper.SetNBTint(stack, variant, "type");
      return stack;
   }

   public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
      if (RANDOM.nextFloat() < 0.75F) {
         drops.add(new ItemStack(ItemsRegister.RICHSCRAP));
      }

      if (RANDOM.nextFloat() < 0.75F) {
         drops.add(new ItemStack(ItemsRegister.SCRAPMETAL));
      }
   }

   protected ItemStack getSilkTouchDrop(IBlockState state) {
      return this.getItemStack(state);
   }

   protected boolean canSilkHarvest() {
      return true;
   }

   public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
      for (int i = 0; i < 12; i++) {
         ItemStack stack = new ItemStack(this);
         NBTHelper.GiveNBTint(stack, i, "type");
         NBTHelper.SetNBTint(stack, i, "type");
         items.add(stack);
      }
   }

   public IBlockState getStateFromMeta(int meta) {
      return this.getDefaultState().withProperty(VARIANT, meta);
   }

   public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
      return MapColor.BROWN;
   }

   public int getMetaFromState(IBlockState state) {
      return (Integer)state.getValue(VARIANT);
   }

   protected BlockStateContainer createBlockState() {
      return new BlockStateContainer(this, new IProperty[]{VARIANT});
   }
}
