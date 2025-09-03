//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.FluidsRegister;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.potions.PotionEffects;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;

public class BlockFluidPoison extends BlockFluidClassic {
   public BlockFluidPoison() {
      super(FluidsRegister.POISON, Material.WATER);
      this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
      this.setTranslationKey("fluid_poison_block");
      this.setRegistryName("fluid_poison_block");
      this.setLightOpacity(2);
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
      if (entityIn instanceof EntityLivingBase && entityIn.ticksExisted % 25 == 0) {
         EntityLivingBase base = (EntityLivingBase)entityIn;
         if (base.isPotionActive(PotionEffects.FIBER_BANDAGING) && RANDOM.nextFloat() < 0.6F) {
            return;
         }

         float filled = this.getFilledPercentage(worldIn, pos);
         if (filled < 0.0F) {
            filled++;
         }

         double minY = pos.getY();
         double maxY = pos.getY() + filled;
         boolean boots = false;
         boolean chest = false;
         boolean leggs = false;
         boolean helmt = false;

         for (ItemStack stack : base.getArmorInventoryList()) {
            if (stack.getItem() == ItemsRegister.TOXINIUMBOOTS || !(entityIn.posY > minY) || !(entityIn.posY < maxY)) {
               boots = true;
            }

            if (stack.getItem() == ItemsRegister.TOXINIUMCHEST || !(entityIn.posY + 0.8 > minY) || !(entityIn.posY + 1.3 < maxY)) {
               chest = true;
            }

            if (stack.getItem() == ItemsRegister.TOXINIUMLEGS || !(entityIn.posY > minY) || !(entityIn.posY + 0.8 < maxY)) {
               leggs = true;
            }

            if (stack.getItem() == ItemsRegister.TOXINIUMHELM
               || !(entityIn.posY + 1.3 > minY)
               || !(entityIn.posY + entityIn.height < maxY)) {
               helmt = true;
            }
         }

         if (boots && chest && leggs && helmt) {
            return;
         }

         PotionEffect baff = new PotionEffect(MobEffects.POISON, 320);
         base.addPotionEffect(baff);
      }
   }

   private void mergerFluids(BlockPos pos, World world) {
      if (!world.isRemote) {
         for (EnumFacing facing : EnumFacing.values()) {
            BlockPos frompos = pos.offset(facing);
            Block block = world.getBlockState(frompos).getBlock();
            if (block == Blocks.LAVA || block == Blocks.FLOWING_LAVA) {
               if (frompos.getY() > pos.getY()) {
                  world.setBlockState(pos, BlocksRegister.GREENONYX.getDefaultState());
               } else {
                  world.setBlockState(frompos, BlocksRegister.GREENONYX.getDefaultState());
               }

               world.playSound(null, frompos, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 0.85F + world.rand.nextFloat() / 4.0F);
            }

            if (block == BlocksRegister.FLUIDCRYON) {
               world.setBlockState(frompos, Blocks.ICE.getDefaultState());
               world.playSound(null, frompos, Sounds.fluid_freezing, SoundCategory.BLOCKS, 1.0F, 0.85F + world.rand.nextFloat() / 4.0F);
            }
         }
      }
   }
}
