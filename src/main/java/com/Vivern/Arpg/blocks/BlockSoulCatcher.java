package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.elements.SoulStone;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.tileentity.TileSoulCatcher;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockSoulCatcher extends Block {
   public BlockSoulCatcher() {
      super(Material.ROCK);
      this.setRegistryName("soul_catcher");
      this.setTranslationKey("soul_catcher");
      this.blockHardness = 7.5F;
      this.blockResistance = 16.0F;
      this.setCreativeTab(CreativeTabs.MISC);
   }

   @SideOnly(Side.CLIENT)
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.CUTOUT;
   }

   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      return FULL_BLOCK_AABB;
   }

   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
      return FULL_BLOCK_AABB;
   }

   public boolean onBlockActivated(
      World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ
   ) {
      if (!worldIn.isRemote && hand == EnumHand.MAIN_HAND) {
         TileSoulCatcher tile = this.getTileEntity(worldIn, pos);
         ItemStack stack = player.getHeldItem(hand);
         if (tile != null) {
            if (stack.getItem() == ItemsRegister.SOULSTONE && SoulStone.getSoul(stack) == 0 && tile.isEmpty()) {
               tile.setInventorySlotContents(0, stack.splitStack(1));
               worldIn.playSound((EntityPlayer)null, pos, Sounds.item_misc_d, SoundCategory.PLAYERS, 0.5F, 0.7F + RANDOM.nextFloat() / 5.0F);
               return true;
            }

            ItemStack stackintile = tile.getStackInSlot(0);
            if (!stackintile.isEmpty()) {
               worldIn.spawnEntity(
                  new EntityItem(worldIn, pos.getX() + 0.5, pos.getY() + 1.1, pos.getZ() + 0.5, stackintile.copy())
               );
               tile.setInventorySlotContents(0, ItemStack.EMPTY);
               worldIn.playSound((EntityPlayer)null, pos, Sounds.item_misc_d, SoundCategory.PLAYERS, 0.5F, 0.8F + RANDOM.nextFloat() / 5.0F);
               return true;
            }
         }
      }

      return false;
   }

   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
      super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
      TileSoulCatcher tile = this.getTileEntity(worldIn, pos);
      tile.rotationYaw = placer.rotationYaw;
      tile.rotationPitch = MathHelper.clamp(placer.rotationPitch, -90.0F, 90.0F);
   }

   public Class<TileSoulCatcher> getTileEntityClass() {
      return TileSoulCatcher.class;
   }

   public TileSoulCatcher getTileEntity(IBlockAccess world, BlockPos position) {
      return (TileSoulCatcher)world.getTileEntity(position);
   }

   public boolean hasTileEntity(IBlockState blockState) {
      return true;
   }

   @Nullable
   public TileSoulCatcher createTileEntity(World world, IBlockState blockState) {
      return new TileSoulCatcher();
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   public boolean isFullCube(IBlockState state) {
      return false;
   }

   public EnumBlockRenderType getRenderType(IBlockState state) {
      return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
   }
}
