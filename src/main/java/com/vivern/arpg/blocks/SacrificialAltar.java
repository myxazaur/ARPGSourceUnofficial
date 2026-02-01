package com.vivern.arpg.blocks;

import com.vivern.arpg.elements.SoulStone;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.renders.GUNParticle;
import com.vivern.arpg.tileentity.TileSacrificialAltar;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SacrificialAltar extends Block {
   public static final AxisAlignedBB ALL_AABB = new AxisAlignedBB(0.125, 0.0, 0.125, 0.875, 0.875, 0.875);
   public static ResourceLocation tex = new ResourceLocation("arpg:textures/clouds3.png");

   public SacrificialAltar() {
      super(Material.ROCK);
      this.setRegistryName("sacrificial_altar");
      this.setTranslationKey("sacrificial_altar");
      this.blockHardness = 7.5F;
      this.blockResistance = 16.0F;
      this.setCreativeTab(CreativeTabs.MISC);
   }

   @SideOnly(Side.CLIENT)
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.CUTOUT_MIPPED;
   }

   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      return ALL_AABB;
   }

   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
      return ALL_AABB;
   }

   public boolean onBlockActivated(
      World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ
   ) {
      if (!worldIn.isRemote) {
         TileSacrificialAltar tile = this.getTileEntity(worldIn, pos);
         ItemStack stack = player.getHeldItem(hand);
         if (tile != null) {
            if (stack.getItem() == ItemsRegister.SOULSTONE && SoulStone.getSoul(stack) > 0 && tile.isEmpty()) {
               tile.setInventorySlotContents(0, stack.copy());
               stack.shrink(1);
               trySendPacketUpdate(worldIn, pos, tile);
               worldIn.playSound((EntityPlayer)null, pos, Sounds.item_misc_d, SoundCategory.PLAYERS, 0.5F, 0.7F + RANDOM.nextFloat() / 5.0F);
               return true;
            }

            ItemStack stackintile = tile.getStackInSlot(0);
            if (!stackintile.isEmpty() && stackintile.getItem() == ItemsRegister.SOULSTONE && SoulStone.getSoul(stackintile) == 0) {
               worldIn.spawnEntity(
                  new EntityItem(worldIn, pos.getX() + 0.5, pos.getY() + 1.1, pos.getZ() + 0.5, stackintile.copy())
               );
               tile.setInventorySlotContents(0, ItemStack.EMPTY);
               trySendPacketUpdate(worldIn, pos, tile);
               worldIn.playSound((EntityPlayer)null, pos, Sounds.item_misc_d, SoundCategory.PLAYERS, 0.5F, 0.8F + RANDOM.nextFloat() / 5.0F);
               return true;
            }
         }
      }

      return false;
   }

   public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
      if (rand.nextFloat() < 0.5F) {
         TileSacrificialAltar tile = this.getTileEntity(worldIn, pos);
         if (tile != null && tile.charge > 0.0F) {
            float scale = 0.2F + rand.nextFloat() / 5.0F;
            GUNParticle spelll = new GUNParticle(
               tex,
               scale,
               0.01F,
               15,
               220,
               worldIn,
               pos.getX() + 0.5,
               pos.getY() + 1.1,
               pos.getZ() + 0.5,
               0.0F,
               0.0F,
               0.0F,
               1.0F,
               1.0F,
               1.0F,
               true,
               rand.nextInt(360)
            );
            spelll.alpha = 0.1F;
            spelll.alphaTickAdding = 0.1F;
            spelll.isPushedByLiquids = false;
            spelll.alphaGlowing = true;
            spelll.scaleTickAdding = -scale / 16.0F;
            worldIn.spawnEntity(spelll);
         }
      }
   }

   public static void trySendPacketUpdate(World world, BlockPos pos, TileSacrificialAltar tile) {
      int range = 64;

      for (EntityPlayerMP playerIn : world.getEntitiesWithinAABB(
         EntityPlayerMP.class,
         new AxisAlignedBB(
            pos.getX() + 64,
            pos.getY() + 64,
            pos.getZ() + 64,
            pos.getX() - 64,
            pos.getY() - 64,
            pos.getZ() - 64
         )
      )) {
         SPacketUpdateTileEntity spacketupdatetileentity = tile.getUpdatePacket();
         if (spacketupdatetileentity != null) {
            playerIn.connection.sendPacket(spacketupdatetileentity);
         }
      }
   }

   public Class<TileSacrificialAltar> getTileEntityClass() {
      return TileSacrificialAltar.class;
   }

   public TileSacrificialAltar getTileEntity(IBlockAccess world, BlockPos position) {
      return (TileSacrificialAltar)world.getTileEntity(position);
   }

   public boolean hasTileEntity(IBlockState blockState) {
      return true;
   }

   @Nullable
   public TileSacrificialAltar createTileEntity(World world, IBlockState blockState) {
      return new TileSacrificialAltar();
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   public boolean isFullCube(IBlockState state) {
      return false;
   }
}
