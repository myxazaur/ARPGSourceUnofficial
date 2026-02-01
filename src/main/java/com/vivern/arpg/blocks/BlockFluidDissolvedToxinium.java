package com.vivern.arpg.blocks;

import com.vivern.arpg.main.FluidsRegister;
import com.vivern.arpg.main.Mana;
import com.vivern.arpg.potions.PotionEffects;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;

public class BlockFluidDissolvedToxinium extends BlockFluidClassic {
   public BlockFluidDissolvedToxinium() {
      super(FluidsRegister.DISSOLVEDTOXINIUM, Material.WATER);
      this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
      this.setTranslationKey("fluid_dissolved_toxinium_block");
      this.setRegistryName("fluid_dissolved_toxinium_block");
   }

   public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
      super.onBlockAdded(world, pos, state);
      this.mergerFluids(pos, world);
   }

   public void neighborChanged(IBlockState state, World world, BlockPos pos, Block neighborBlock, BlockPos neighbourPos) {
      super.neighborChanged(state, world, pos, neighborBlock, neighbourPos);
      this.mergerFluids(pos, world);
   }

   public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
      super.onEntityCollision(worldIn, pos, state, entityIn);
      if (entityIn instanceof EntityLivingBase && entityIn.ticksExisted % 15 == 0) {
         EntityLivingBase base = (EntityLivingBase)entityIn;
         PotionEffect baff = new PotionEffect(PotionEffects.TOXIN, 400);
         base.addPotionEffect(baff);
         if (base instanceof EntityPlayer) {
            Mana.addRad((EntityPlayer)base, 10, true);
         }
      }
   }

   private void mergerFluids(BlockPos pos, World world) {
   }

   @Nullable
   public PathNodeType getAiPathNodeType(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable EntityLiving entity) {
      return PathNodeType.DAMAGE_OTHER;
   }
}
