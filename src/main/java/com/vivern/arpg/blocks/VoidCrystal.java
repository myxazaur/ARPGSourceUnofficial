package com.vivern.arpg.blocks;

import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.tileentity.TileVoidCrystal;
import com.google.common.base.Predicate;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class VoidCrystal extends BlockSpawner {
   protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.15, 0.15, 0.15, 0.85, 0.85, 0.85);

   public VoidCrystal(Material mater, String name, BlocksRegister.Hardres hardres, int xpDropMin, int xpDropMax) {
      super(mater, name, hardres, xpDropMin, xpDropMax);
      this.setSoundType(SoundTypeShards.SHARDS);
   }

   @Override
   public boolean isReplaceableOreGen(IBlockState state, IBlockAccess world, BlockPos pos, Predicate<IBlockState> target) {
      return false;
   }

   public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
      entityIn.attackEntityFrom(DamageSource.OUT_OF_WORLD, 2.0F);
   }

   @Override
   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
      return false;
   }

   @Override
   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      return AABB;
   }

   @Override
   public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      return AABB;
   }

   @Override
   public void randomDisplayTick(IBlockState stateIn, World world, BlockPos pos, Random rand) {
      for (int i = 0; i < 2; i++) {
         world.spawnParticle(
            EnumParticleTypes.PORTAL,
            pos.getX() + rand.nextDouble(),
            pos.getY() + rand.nextDouble(),
            pos.getZ() + rand.nextDouble(),
            (rand.nextDouble() - 0.5) * 2.0,
            -rand.nextDouble(),
            (rand.nextDouble() - 0.5) * 2.0,
            new int[0]
         );
      }
   }

   public EnumBlockRenderType getRenderType(IBlockState state) {
      return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
   }

   public static void spawnAsEntityVC(World worldIn, BlockPos pos, ItemStack stack) {
      if (!worldIn.isRemote && !stack.isEmpty() && worldIn.getGameRules().getBoolean("doTileDrops") && !worldIn.restoringBlockSnapshots) {
         if ((Boolean)captureDrops.get()) {
            ((NonNullList)capturedDrops.get()).add(stack);
            return;
         }

         EntityItem entityitem = new EntityItem(worldIn, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, stack);
         entityitem.setPickupDelay(20);
         worldIn.spawnEntity(entityitem);
         entityitem.motionX = (float)(Math.random() * 0.2F - 0.1F);
         entityitem.motionY = (float)(Math.random() * 0.2F - 0.1F);
         entityitem.motionZ = (float)(Math.random() * 0.2F - 0.1F);
         entityitem.velocityChanged = true;
      }
   }

   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
      if (!worldIn.isRemote && !worldIn.restoringBlockSnapshots) {
         List<ItemStack> drops = this.getDrops(worldIn, pos, state, fortune);
         chance = ForgeEventFactory.fireBlockHarvesting(drops, worldIn, pos, state, fortune, chance, false, (EntityPlayer)this.harvesters.get());

         for (ItemStack drop : drops) {
            if (worldIn.rand.nextFloat() <= chance) {
               spawnAsEntityVC(worldIn, pos, drop);
            }
         }
      }
   }

   @Override
   public int quantityDropped(IBlockState state, int fortune, Random random) {
      return random.nextInt(5) + 3;
   }

   @Override
   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
      return ItemsRegister.VOIDCRYSTAL;
   }

   @Override
   public Class getTileEntityClass() {
      return TileVoidCrystal.class;
   }

   public TileVoidCrystal getTileEntity(IBlockAccess world, BlockPos position) {
      return (TileVoidCrystal)world.getTileEntity(position);
   }

   @Nullable
   public TileVoidCrystal createTileEntity(World world, IBlockState blockState) {
      return new TileVoidCrystal();
   }
}
