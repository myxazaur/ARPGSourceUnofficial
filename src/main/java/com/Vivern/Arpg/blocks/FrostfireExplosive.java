//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.entity.EntityFrostfireExplosive;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class FrostfireExplosive extends AbstractBomb {
   public static final PropertyBool EXPLODE = PropertyBool.create("explode");
   public static float basePower = 5.0F;

   public FrostfireExplosive() {
      super(Material.TNT);
      this.setRegistryName("frostfire_explosive");
      this.setTranslationKey("frostfire_explosive");
      this.blockHardness = 0.3F;
      this.blockResistance = 0.5F;
      this.setDefaultState(this.blockState.getBaseState().withProperty(EXPLODE, false));
      this.setCreativeTab(CreativeTabs.REDSTONE);
      this.setSoundType(SoundType.METAL);
   }

   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
      super.onBlockAdded(worldIn, pos, state);
      if (worldIn.isBlockPowered(pos)) {
         this.onPlayerDestroy(worldIn, pos, state.withProperty(EXPLODE, true));
         worldIn.setBlockToAir(pos);
      }
   }

   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
      if (worldIn.isBlockPowered(pos)) {
         this.onPlayerDestroy(worldIn, pos, state.withProperty(EXPLODE, true));
         worldIn.setBlockToAir(pos);
      }
   }

   public void onExplosionDestroy(World worldIn, BlockPos pos, Explosion explosionIn) {
      if (!worldIn.isRemote) {
         EntityFrostfireExplosive entitytntprimed = new EntityFrostfireExplosive(
            worldIn, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5F, explosionIn.getExplosivePlacedBy(), basePower
         );
         entitytntprimed.fuse = 10 + RANDOM.nextInt(10);
         worldIn.spawnEntity(entitytntprimed);
      }
   }

   @Override
   public void blockexploded(World worldIn, BlockPos pos, EntityLivingBase igniter, Entity entityExplosive) {
      if (!worldIn.isRemote) {
         EntityFrostfireExplosive entitytntprimed = new EntityFrostfireExplosive(
            worldIn, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5F, igniter, basePower
         );
         entitytntprimed.fuse = 10 + RANDOM.nextInt(10);
         worldIn.spawnEntity(entitytntprimed);
         entitytntprimed.velocityChanged = true;
      }
   }

   @Override
   public void activate(World worldIn, BlockPos pos, @Nullable EntityLivingBase igniter) {
      this.explode(worldIn, pos, worldIn.getBlockState(pos), igniter);
   }

   public void onPlayerDestroy(World worldIn, BlockPos pos, IBlockState state) {
      this.explode(worldIn, pos, state, (EntityLivingBase)null);
   }

   public void explode(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase igniter) {
      if (!worldIn.isRemote && (Boolean)state.getValue(EXPLODE)) {
         EntityFrostfireExplosive entitytntprimed = new EntityFrostfireExplosive(
            worldIn, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5F, igniter, basePower
         );
         worldIn.spawnEntity(entitytntprimed);
         worldIn.playSound(
            (EntityPlayer)null,
            entitytntprimed.posX,
            entitytntprimed.posY,
            entitytntprimed.posZ,
            SoundEvents.ENTITY_TNT_PRIMED,
            SoundCategory.BLOCKS,
            1.0F,
            1.0F
         );
      }
   }

   public boolean onBlockActivated(
      World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ
   ) {
      ItemStack itemstack = playerIn.getHeldItem(hand);
      if (!itemstack.isEmpty() && (itemstack.getItem() == Items.FLINT_AND_STEEL || itemstack.getItem() == Items.FIRE_CHARGE)) {
         this.explode(worldIn, pos, state.withProperty(EXPLODE, true), playerIn);
         worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 11);
         if (itemstack.getItem() == Items.FLINT_AND_STEEL) {
            itemstack.damageItem(1, playerIn);
         } else if (!playerIn.capabilities.isCreativeMode) {
            itemstack.shrink(1);
         }

         return true;
      } else {
         return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
      }
   }

   public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
   }

   public boolean canDropFromExplosion(Explosion explosionIn) {
      return false;
   }

   public IBlockState getStateFromMeta(int meta) {
      return this.getDefaultState().withProperty(EXPLODE, (meta & 1) > 0);
   }

   public int getMetaFromState(IBlockState state) {
      return state.getValue(EXPLODE) ? 1 : 0;
   }

   protected BlockStateContainer createBlockState() {
      return new BlockStateContainer(this, new IProperty[]{EXPLODE});
   }
}
