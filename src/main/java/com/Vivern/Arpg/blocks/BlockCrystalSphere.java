//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.tileentity.TileCrystalSphere;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCrystalSphere extends Block {
   public static AxisAlignedBB AABB = new AxisAlignedBB(0.3125, 0.0, 0.3125, 0.6875, 0.5625, 0.6875);

   public BlockCrystalSphere() {
      super(Material.GLASS);
      this.setRegistryName("block_crystal_sphere");
      this.setTranslationKey("block_crystal_sphere");
      this.blockHardness = 1.5F;
      this.blockResistance = 15.0F;
      this.setCreativeTab(CreativeTabs.MISC);
      this.setLightLevel(0.2F);
      this.setSoundType(SoundTypeShards.SHARDS);
   }

   public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
      if (!world.isRemote) {
         ItemStack stack = new ItemStack(Item.getItemFromBlock(this));
         TileEntity tile = this.getTileEntity(world, pos);
         if (tile != null && tile instanceof TileCrystalSphere) {
            TileCrystalSphere sphere = (TileCrystalSphere)tile;
            NBTTagCompound compound = new NBTTagCompound();
            compound.setFloat("stored", sphere.energyStored);
            if (sphere.energyType != null) {
               compound.setString("type", sphere.energyType.getName());
            }

            NBTHelper.GiveNBTtag(stack, compound, "BlockEntityTag");
            NBTHelper.SetNBTtag(stack, compound, "BlockEntityTag");
         }

         spawnAsEntity(world, pos, stack);
      }
   }

   public int quantityDropped(IBlockState state, int fortune, Random random) {
      return 0;
   }

   @SideOnly(Side.CLIENT)
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.TRANSLUCENT;
   }

   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      return AABB;
   }

   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
      return AABB;
   }

   public Vec3d getOffset(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
      return super.getOffset(state, worldIn, pos);
   }

   public static void trySendPacketUpdate(World world, BlockPos pos, TileCrystalSphere tile) {
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

   public void onExplosionDestroy(World worldIn, BlockPos pos, Explosion explosionIn) {
      this.onPlayerDestroy(worldIn, pos, worldIn.getBlockState(pos));
   }

   public Class<TileCrystalSphere> getTileEntityClass() {
      return TileCrystalSphere.class;
   }

   public TileCrystalSphere getTileEntity(IBlockAccess world, BlockPos position) {
      return (TileCrystalSphere)world.getTileEntity(position);
   }

   public boolean hasTileEntity(IBlockState blockState) {
      return true;
   }

   @Nullable
   public TileCrystalSphere createTileEntity(World world, IBlockState blockState) {
      return new TileCrystalSphere();
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
