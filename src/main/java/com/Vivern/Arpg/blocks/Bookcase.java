package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.container.GUIBookOfElements;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.network.PacketHandler;
import com.Vivern.Arpg.tileentity.IManaBuffer;
import com.Vivern.Arpg.tileentity.TileBookcase;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.NonNullList;
import net.minecraft.util.Rotation;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class Bookcase extends Block {
   public static final PropertyDirection FACING = PropertyDirection.create("facing");

   public Bookcase(String name, SoundType sound, String toolClass, int harvestLvl) {
      super(IManaBuffer.MAGIC_BLOCK);
      this.setRegistryName(name);
      this.setTranslationKey(name);
      this.blockHardness = 2.5F;
      this.blockResistance = 10.0F;
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setSoundType(sound);
      this.setHarvestLevel(toolClass, harvestLvl);
   }

   public boolean onBlockActivated(
      World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ
   ) {
      if (hand == EnumHand.MAIN_HAND && !player.getCooldownTracker().hasCooldown(ItemsRegister.BOOKOFELEMENTS)) {
         TileEntity tile = worldIn.getTileEntity(pos);
         if (tile instanceof TileBookcase) {
            TileBookcase tileBookcase = (TileBookcase)tile;
            ItemStack inhand = player.getHeldItem(hand);
            if (inhand.isEmpty()) {
               if (!player.isSneaking()) {
                  if (worldIn.isRemote && player instanceof EntityPlayerSP && tileBookcase.hasBooks()) {
                     Minecraft.getMinecraft().displayGuiScreen(new GUIBookOfElements(tileBookcase.stacks));
                  }

                  return true;
               }

               if (!worldIn.isRemote) {
                  for (int i = 2; i >= 0; i--) {
                     if (tileBookcase.booksGems[i] != 50) {
                        ItemStack itemStack = ((ItemStack)tileBookcase.stacks.get(i)).copy();
                        EntityItem entityItem = new EntityItem(worldIn, player.posX, player.posY, player.posZ, itemStack);
                        entityItem.setNoPickupDelay();
                        worldIn.spawnEntity(entityItem);
                        tileBookcase.stacks.set(i, ItemStack.EMPTY);
                        tileBookcase.booksGems[i] = 50;
                        PacketHandler.trySendPacketUpdate(worldIn, pos, tileBookcase, 64.0);
                        player.getCooldownTracker().setCooldown(ItemsRegister.BOOKOFELEMENTS, 3);
                        return true;
                     }
                  }
               }

               return false;
            }
         }
      }

      return false;
   }

   public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
      TileEntity tile = world.getTileEntity(pos);
      if (tile instanceof TileBookcase) {
         System.out.println("instanceof");
         TileBookcase tileBookcase = (TileBookcase)tile;

         for (int i = 0; i < 3; i++) {
            System.out.println("for");
            if (tileBookcase.booksGems[i] != 50) {
               System.out.println("add");
               drops.add(((ItemStack)tileBookcase.stacks.get(i)).copy());
            }
         }
      }
   }

   public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
      IBlockState state = world.getBlockState(pos);
      return ((EnumFacing)state.getValue(FACING)).getAxis() != side.getAxis();
   }

   public boolean hasTileEntity(IBlockState blockState) {
      return true;
   }

   @Nullable
   public TileBookcase createTileEntity(World world, IBlockState blockState) {
      TileBookcase tileBookcase = new TileBookcase();
      tileBookcase.rotation = ((EnumFacing)blockState.getValue(FACING)).getHorizontalIndex();
      return tileBookcase;
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
      return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
   }

   public IBlockState getStateFromMeta(int meta) {
      EnumFacing enumfacing = EnumFacing.byIndex(meta);
      if (enumfacing.getAxis() == Axis.Y) {
         enumfacing = EnumFacing.NORTH;
      }

      return this.getDefaultState().withProperty(FACING, enumfacing);
   }

   public int getMetaFromState(IBlockState state) {
      return ((EnumFacing)state.getValue(FACING)).getIndex();
   }

   public IBlockState withRotation(IBlockState state, Rotation rot) {
      return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
   }

   public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
      return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
   }

   protected BlockStateContainer createBlockState() {
      return new BlockStateContainer(this, new IProperty[]{FACING});
   }
}
