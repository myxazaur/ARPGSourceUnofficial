package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.renders.GUNParticle;
import com.Vivern.Arpg.renders.ManaBar;
import com.Vivern.Arpg.tileentity.TileSpellForge;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SpellForgeBlock extends Block {
   public static AxisAlignedBB AABB = new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.9375, 1.0, 0.9375);
   static ResourceLocation tex = new ResourceLocation("arpg:textures/spellblue4.png");

   public SpellForgeBlock() {
      super(Material.ROCK);
      this.setRegistryName("spell_forge");
      this.setTranslationKey("spell_forge");
      this.blockHardness = 10.0F;
      this.blockResistance = 10.0F;
      this.setCreativeTab(CreativeTabs.DECORATIONS);
   }

   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
      TileEntity tileentity = worldIn.getTileEntity(pos);
      if (tileentity instanceof IInventory) {
         InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory)tileentity);
         worldIn.updateComparatorOutputLevel(pos, this);
      }

      super.breakBlock(worldIn, pos, state);
   }

   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      return AABB;
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   public boolean isFullCube(IBlockState state) {
      return false;
   }

   public Class<TileSpellForge> getTileEntityClass() {
      return TileSpellForge.class;
   }

   public TileSpellForge getTileEntity(IBlockAccess world, BlockPos position) {
      return (TileSpellForge)world.getTileEntity(position);
   }

   public boolean hasTileEntity(IBlockState blockState) {
      return true;
   }

   @Nullable
   public TileSpellForge createTileEntity(World world, IBlockState blockState) {
      return new TileSpellForge();
   }

   public boolean onBlockActivated(
      World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ
   ) {
      TileSpellForge tile = this.getTileEntity(worldIn, pos);
      if (worldIn.isRemote) {
         ManaBar.energy_bars_enable = true;
      }

      if (tile != null
         && playerIn.getHeldItemMainhand().getItem() != ItemsRegister.SPELLROLL
         && playerIn.getHeldItemMainhand().getItem() != ItemsRegister.SPELLPLIERS
         && playerIn.getHeldItemOffhand().getItem() != ItemsRegister.SPELLPLIERS
         && playerIn.getHeldItemMainhand().getItem() != ItemsRegister.VIAL
         && playerIn.getHeldItemOffhand().getItem() != ItemsRegister.VIAL
         && !playerIn.getCooldownTracker().hasCooldown(ItemsRegister.VIAL)) {
         playerIn.displayGUIChest(tile);
         playerIn.swingArm(hand);
         return true;
      } else {
         return false;
      }
   }

   @SideOnly(Side.CLIENT)
   public void effect(World world, BlockPos pos) {
      for (int ss = 0; ss < 15; ss++) {
         GUNParticle bubble = new GUNParticle(
            tex,
            0.02F + RANDOM.nextFloat() / 50.0F,
            0.025F,
            10 + RANDOM.nextInt(20),
            240,
            world,
            pos.getX() + 0.5,
            pos.getY() + 1.1,
            pos.getZ() + 0.5,
            (float)RANDOM.nextGaussian() / 9.0F,
            (float)RANDOM.nextGaussian() / 9.0F + 0.17F,
            (float)RANDOM.nextGaussian() / 9.0F,
            0.5F + RANDOM.nextFloat() / 2.0F,
            0.5F + RANDOM.nextFloat() / 2.0F,
            0.5F + RANDOM.nextFloat() / 2.0F,
            false,
            RANDOM.nextInt(360),
            true,
            2.5F
         );
         world.spawnEntity(bubble);
      }
   }

   public static void trySendPacketUpdate(World world, BlockPos pos, TileSpellForge tile) {
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

   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
      TileSpellForge tile = this.getTileEntity(worldIn, pos);
      if (tile != null) {
         EnumFacing f = placer.getHorizontalFacing();
         if (f == EnumFacing.EAST) {
            tile.rotation = 90;
         }

         if (f == EnumFacing.NORTH) {
            tile.rotation = 180;
         }

         if (f == EnumFacing.WEST) {
            tile.rotation = 270;
         }
      }
   }
}
