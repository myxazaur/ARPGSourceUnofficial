//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.OreDicHelper;
import com.Vivern.Arpg.main.Sounds;
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
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RedPepperVine extends Block implements IGrowable {
   public static final AxisAlignedBB AABB = new AxisAlignedBB(0.2, 0.0, 0.2, 0.8, 1.0, 0.8);
   public static final PropertyBool FRUITAGE = PropertyBool.create("fruitage");

   public RedPepperVine() {
      super(Material.PLANTS);
      this.setRegistryName("red_pepper_vine");
      this.setTranslationKey("red_pepper_vine");
      this.blockHardness = 0.3F;
      this.blockResistance = 0.0F;
      this.setSoundType(SoundType.PLANT);
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setTickRandomly(true);
   }

   public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
      super.onEntityCollision(worldIn, pos, state, entityIn);
      entityIn.motionX *= 0.7;
      entityIn.motionY *= 0.9;
      entityIn.motionZ *= 0.7;
   }

   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
      return super.canPlaceBlockAt(worldIn, pos) && this.canStayAtPos(worldIn, pos);
   }

   public boolean canStayAtPos(World worldIn, BlockPos pos) {
      IBlockState st = worldIn.getBlockState(pos.up());
      Block block = st.getBlock();
      return block == Blocks.NETHERRACK || block == this || block == Blocks.MAGMA || block == Blocks.SOUL_SAND;
   }

   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
      if (!this.canStayAtPos(worldIn, pos)) {
         worldIn.destroyBlock(pos, true);
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
      if (!worldIn.isRemote && rand.nextFloat() < 0.15F) {
         this.grow(worldIn, rand, pos, state);
      }
   }

   public boolean onBlockActivated(
      World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ
   ) {
      if (!worldIn.isRemote) {
         if (player.getHeldItem(hand).isEmpty() && (Boolean)state.getValue(FRUITAGE)) {
            int count = RANDOM.nextInt(3) + 1 + (RANDOM.nextFloat() < 0.25F ? 1 : 0);

            for (int i = 0; i < count; i++) {
               spawnAsEntity(worldIn, pos, new ItemStack(ItemsRegister.REDPEPPER));
            }

            worldIn.playSound((EntityPlayer)null, pos, Sounds.fruit_pickup, SoundCategory.BLOCKS, 0.6F, 0.9F + RANDOM.nextFloat() / 5.0F);
            worldIn.setBlockState(pos, state.withProperty(FRUITAGE, false));
         } else if (OreDicHelper.itemStringOredigMatches(player.getHeldItem(hand), OreDicHelper.DUSTCHARCOAL)
            || OreDicHelper.itemStringOredigMatches(player.getHeldItem(hand), OreDicHelper.DUSTCOAL)) {
            player.getHeldItem(hand).shrink(1);
            if (RANDOM.nextFloat() < 0.7F) {
               this.grow(worldIn, RANDOM, pos, state);
            }

            return true;
         }
      }

      return super.onBlockActivated(worldIn, pos, state, player, hand, facing, hitX, hitY, hitZ);
   }

   public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
      return true;
   }

   public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
      return false;
   }

   public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
      drops.add(new ItemStack(this));
      if ((Boolean)state.getValue(FRUITAGE)) {
         int count = RANDOM.nextInt(3) + 1 + (RANDOM.nextFloat() < 0.25F ? 1 : 0);
         drops.add(new ItemStack(ItemsRegister.REDPEPPER, count));
      }
   }

   public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
      if (rand.nextFloat() < 0.5) {
         if (rand.nextFloat() < 0.6 && worldIn.getBlockState(pos.up(4)).getBlock() != this && this.canPlaceBlockAt(worldIn, pos.down())) {
            worldIn.setBlockState(pos.down(), state.withProperty(FRUITAGE, false));
         } else {
            worldIn.setBlockState(pos, state.withProperty(FRUITAGE, true));
         }
      }
   }

   public IBlockState getStateFromMeta(int meta) {
      return this.getDefaultState().withProperty(FRUITAGE, meta > 0);
   }

   public int getMetaFromState(IBlockState state) {
      return state.getValue(FRUITAGE) ? 1 : 0;
   }

   protected BlockStateContainer createBlockState() {
      return new BlockStateContainer(this, new IProperty[]{FRUITAGE});
   }
}
