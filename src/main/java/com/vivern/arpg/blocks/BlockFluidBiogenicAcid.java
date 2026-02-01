package com.vivern.arpg.blocks;

import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.main.DeathEffects;
import com.vivern.arpg.main.FluidsRegister;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.PropertiesRegistry;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.Weapons;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;

public class BlockFluidBiogenicAcid extends BlockFluidClassic {
   public BlockFluidBiogenicAcid() {
      super(FluidsRegister.BIOGENICACID, Material.WATER);
      this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
      this.setTranslationKey("fluid_biogenic_acid");
      this.setRegistryName("fluid_biogenic_acid");
      this.setTickRandomly(true);
      this.setTickRate(5);
   }

   public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
      super.onBlockAdded(world, pos, state);
      this.mergerFluids(pos, world);
   }

   public void neighborChanged(IBlockState state, World world, BlockPos pos, Block neighborBlock, BlockPos neighbourPos) {
      super.neighborChanged(state, world, pos, neighborBlock, neighbourPos);
      this.mergerFluids(pos, world);
   }

   public void flownext(World worldIn, BlockPos pos, IBlockState state, Random random) {
      int x = 0;
      int z = 0;
      int y = 0;
      if (random.nextFloat() < 0.5) {
         y = -random.nextInt(2);
      } else if (random.nextFloat() < 0.5) {
         x = random.nextInt(3) - 1;
      } else {
         z = random.nextInt(3) - 1;
      }

      BlockPos randpos = pos.add(x, y, z);
      IBlockState randstate = worldIn.getBlockState(randpos);
      if (randstate.getBlock() == this) {
         if ((Integer)randstate.getValue(LEVEL) != 0) {
            if (!(random.nextFloat() < 0.55) && worldIn.isAirBlock(pos.up())) {
               worldIn.setBlockState(pos, state.withProperty(LEVEL, 1));
            } else {
               worldIn.setBlockState(randpos, state);
               worldIn.setBlockState(pos, state.withProperty(LEVEL, 1));
            }
         }
      } else if (randstate.getBlock().isReplaceable(worldIn, randpos)) {
         worldIn.setBlockState(randpos, state);
         worldIn.setBlockState(pos, state.withProperty(LEVEL, 1));
      } else if (Weapons.easyBreakBlockFor(worldIn, 4.0F, randpos, randstate)
         && randstate.getMaterial() != Material.GLASS
         && randstate.getBlock() != Blocks.CONCRETE) {
         worldIn.setBlockToAir(pos);
         if (random.nextFloat() < 0.55) {
            worldIn.setBlockState(randpos, state);
         } else {
            worldIn.setBlockState(randpos, state.withProperty(LEVEL, 1));
         }
      }
   }

   public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
      super.randomTick(worldIn, pos, state, random);
      if ((Integer)state.getValue(LEVEL) == 0) {
         this.flownext(worldIn, pos, state, random);
      } else if (random.nextFloat() < 0.4) {
         int x = 0;
         int z = 0;
         int y = 0;
         if (random.nextFloat() < 0.5) {
            y = -random.nextInt(2);
         } else if (random.nextFloat() < 0.5) {
            x = random.nextInt(3) - 1;
         } else {
            z = random.nextInt(3) - 1;
         }

         BlockPos randpos = pos.add(x, y, z);
         IBlockState randstate = worldIn.getBlockState(randpos);
         if (randstate.getBlock() != this) {
            if (Weapons.easyBreakBlockFor(worldIn, 3.0F, randpos, randstate) && randstate.getMaterial() != Material.GLASS) {
               worldIn.setBlockToAir(randpos);
            }
         } else if ((Integer)randstate.getValue(LEVEL) == 0) {
            this.flownext(worldIn, randpos, randstate, random);
         }
      } else {
         BlockPos newpos = pos.up();
         IBlockState newstate = worldIn.getBlockState(newpos);
         if (newstate.getBlock() == this && (Integer)newstate.getValue(LEVEL) == 0) {
            this.flownext(worldIn, newpos, newstate, random);
         }
      }
   }

   public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
      super.onEntityCollision(worldIn, pos, state, entityIn);
      if (entityIn.ticksExisted % 10 == 0) {
         if (entityIn instanceof EntityItem && ((EntityItem)entityIn).getItem().getItem() == ItemsRegister.EMBRYO) {
            return;
         }

         entityIn.attackEntityFrom(DamageSource.WITHER, 3.0F);
         if (entityIn instanceof EntityLivingBase && ((EntityLivingBase)entityIn).getHealth() <= 0.0F) {
            IAttributeInstance red = ((EntityLivingBase)entityIn).getEntityAttribute(PropertiesRegistry.ENTITY_COLOR_RED_MAX);
            IAttributeInstance blue = ((EntityLivingBase)entityIn).getEntityAttribute(PropertiesRegistry.ENTITY_COLOR_BLUE_MAX);
            red.applyModifier(new AttributeModifier("acid.explode.red.modif", 0.5, 1));
            blue.applyModifier(new AttributeModifier("acid.explode.blue.modif", 0.5, 1));
            DeathEffects.applyDeathEffect(entityIn, DeathEffects.DE_COLOREDACID);
         }
      }
   }

   private void mergerFluids(BlockPos pos, World world) {
      if (!world.isRemote) {
         for (EnumFacing facing : EnumFacing.values()) {
            BlockPos frompos = pos.offset(facing);
            Block block = world.getBlockState(frompos).getBlock();
            if (block != Blocks.LAVA && block == Blocks.FLOWING_LAVA) {
            }

            if (block == BlocksRegister.FLUIDCRYON) {
               world.setBlockState(pos, Blocks.ICE.getDefaultState());
               world.playSound(null, pos, Sounds.fluid_freezing, SoundCategory.BLOCKS, 1.0F, 0.85F + world.rand.nextFloat() / 4.0F);
            }
         }
      }
   }

   @Nullable
   public PathNodeType getAiPathNodeType(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable EntityLiving entity) {
      return entity != null && entity.isEntityInvulnerable(DamageSource.WITHER) ? PathNodeType.WATER : PathNodeType.LAVA;
   }
}
