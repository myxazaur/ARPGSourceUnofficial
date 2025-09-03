//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.main.BlocksRegister;
import com.google.common.base.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockRotated extends Block {
   public boolean replaceableOreGen = true;
   public Item dropped = null;
   public BlockRenderLayer layer = BlockRenderLayer.SOLID;
   public boolean opaque = true;
   public static final PropertyDirection FACING = PropertyDirection.create("facing");
   public static final PropertyDirection FACING_FULL = PropertyDirection.create("facing");
   public AxisAlignedBB[] AABB;
   public boolean useBBOne = false;
   public boolean fullcube = true;
   public boolean fullFacing;

   public BlockRotated(Material mater, String name, float hard, float resi, boolean fullFacing) {
      super(mater);
      this.fullFacing = fullFacing;
      this.setRegistryName(name);
      this.setTranslationKey(name);
      this.blockHardness = hard;
      this.blockResistance = resi;
      this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
      this.AABB = null;
      this.setDefaultState(this.blockState.getBaseState().withProperty(this.getProperty(), EnumFacing.NORTH));
   }

   public BlockRotated(Material mater, String name, float hard, float resi, boolean fullFacing, AxisAlignedBB[] AABB) {
      super(mater);
      this.fullFacing = fullFacing;
      this.setRegistryName(name);
      this.setTranslationKey(name);
      this.blockHardness = hard;
      this.blockResistance = resi;
      this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
      this.AABB = AABB;
      this.setDefaultState(this.blockState.getBaseState().withProperty(this.getProperty(), EnumFacing.NORTH));
   }

   public BlockRotated(Material mater, String name, float hard, float resi, boolean fullFacing, AxisAlignedBB aabb) {
      super(mater);
      this.fullFacing = fullFacing;
      this.setRegistryName(name);
      this.setTranslationKey(name);
      this.blockHardness = hard;
      this.blockResistance = resi;
      this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
      this.AABB = new AxisAlignedBB[]{aabb};
      this.useBBOne = true;
      this.setDefaultState(this.blockState.getBaseState().withProperty(this.getProperty(), EnumFacing.NORTH));
   }

   public PropertyDirection getProperty() {
      return this.fullFacing ? FACING_FULL : FACING;
   }

   public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
      return this.fullFacing
         ? this.getDefaultState().withProperty(FACING, facing)
         : this.getDefaultState().withProperty(this.getProperty(), placer.getHorizontalFacing());
   }

   public IBlockState getStateFromMeta(int meta) {
      EnumFacing enumfacing = EnumFacing.byIndex(meta);
      if (!this.fullFacing && enumfacing.getAxis() == Axis.Y) {
         enumfacing = EnumFacing.NORTH;
      }

      return this.getDefaultState().withProperty(this.getProperty(), enumfacing);
   }

   public int getMetaFromState(IBlockState state) {
      return ((EnumFacing)state.getValue(this.getProperty())).getIndex();
   }

   public IBlockState withRotation(IBlockState state, Rotation rot) {
      return state.withProperty(this.getProperty(), rot.rotate((EnumFacing)state.getValue(this.getProperty())));
   }

   public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
      return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(this.getProperty())));
   }

   protected BlockStateContainer createBlockState() {
      return new BlockStateContainer(this, new IProperty[]{this.getProperty()});
   }

   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      if (this.useBBOne) {
         return this.AABB[0];
      } else {
         return this.AABB == null
            ? FULL_BLOCK_AABB
            : this.AABB[((EnumFacing)state.getValue(this.getProperty())).getIndex() - (this.fullFacing ? 0 : 2)];
      }
   }

   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
      if (this.useBBOne) {
         return this.AABB[0];
      } else {
         return this.AABB == null
            ? FULL_BLOCK_AABB
            : this.AABB[((EnumFacing)blockState.getValue(this.getProperty())).getIndex() - (this.fullFacing ? 0 : 2)];
      }
   }

   @SideOnly(Side.CLIENT)
   public BlockRenderLayer getRenderLayer() {
      return this.layer;
   }

   public boolean isOpaqueCube(IBlockState state) {
      return this.opaque;
   }

   public BlockRotated setOpaque(boolean b) {
      this.opaque = b;
      return this;
   }

   public BlockRotated setFullCube(boolean b) {
      this.fullcube = b;
      return this;
   }

   public BlockRotated setRenderLayer(BlockRenderLayer render) {
      this.layer = render;
      return this;
   }

   public BlockRotated setSound(SoundType sound) {
      this.setSoundType(sound);
      return this;
   }

   public BlockRotated setisReplaceableOreGen(boolean b) {
      this.replaceableOreGen = b;
      return this;
   }

   public BlockRotated setHarvest(String tool, int lvl) {
      this.setHarvestLevel(tool, lvl);
      return this;
   }

   public BlockRotated setItemDropped(Item dropped) {
      this.dropped = dropped;
      return this;
   }

   public BlockRotated setFullFacing() {
      this.fullFacing = true;
      return this;
   }

   public boolean isReplaceableOreGen(IBlockState state, IBlockAccess world, BlockPos pos, Predicate<IBlockState> target) {
      return this.replaceableOreGen;
   }

   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
      return this.dropped == null ? Item.getItemFromBlock(this) : this.dropped;
   }

   public boolean isFullCube(IBlockState state) {
      return this.fullcube;
   }

   public static class HardBlockRotated extends BlockRotated implements IBlockHardBreak {
      public float slowSpeed;
      public float fastSpeed;
      public boolean canDropWhithoutTool;
      public int level;
      public String tool;

      public HardBlockRotated(
         Material mater,
         String name,
         float hard,
         float resi,
         boolean fullFacing,
         float slowSpeed,
         float fastSpeed,
         int harvestlvl,
         String tool,
         boolean canDropWhithoutTool
      ) {
         super(mater, name, hard, resi, fullFacing);
         this.slowSpeed = slowSpeed;
         this.canDropWhithoutTool = canDropWhithoutTool;
         this.level = harvestlvl;
         this.tool = tool;
         this.fastSpeed = fastSpeed;
         this.setHarvest(tool, harvestlvl);
      }

      public HardBlockRotated(
         Material mater, String name, BlocksRegister.Hardres hardres, String tool, boolean canDropWhithoutTool, boolean fullFacing, AxisAlignedBB aabb
      ) {
         super(mater, name, hardres.HARDNESS, hardres.RESISTANCE, fullFacing);
         this.slowSpeed = hardres.SLOW;
         this.canDropWhithoutTool = canDropWhithoutTool;
         this.level = hardres.LVL;
         this.tool = tool;
         this.fastSpeed = hardres.FAST;
         this.AABB = new AxisAlignedBB[]{aabb};
         this.useBBOne = true;
         this.setHarvest(tool, hardres.LVL);
      }

      public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
         player.addStat(StatList.getBlockStats(this));
         player.addExhaustion(0.005F);
         if (this.canSilkHarvest(worldIn, pos, state, player) && EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack) > 0) {
            List<ItemStack> items = new ArrayList<>();
            ItemStack itemstack = this.getSilkTouchDrop(state);
            if (!itemstack.isEmpty()) {
               items.add(itemstack);
            }

            ForgeEventFactory.fireBlockHarvesting(items, worldIn, pos, state, 0, 1.0F, true, player);

            for (ItemStack item : items) {
               spawnAsEntity(worldIn, pos, item);
            }
         } else {
            this.harvesters.set(player);
            int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);
            if (this.canDropWhithoutTool || stack.getItem().getHarvestLevel(stack, this.tool, player, state) >= this.level) {
               this.dropBlockAsItem(worldIn, pos, state, i);
            }

            this.harvesters.set(null);
         }
      }

      @Override
      public float getBlockBreakingSpeed(World world, String tool, int toolLevel, IBlockState state, BlockPos pos, float originalSpeed) {
         return toolLevel >= this.level && tool.equals(this.getHarvestTool(state)) ? originalSpeed * this.fastSpeed : originalSpeed * this.slowSpeed;
      }
   }
}
