//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.main.FluidsRegister;
import com.Vivern.Arpg.main.ItemsRegister;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;

public class BlockFluidNitricAcid extends BlockFluidClassic {
   public BlockFluidNitricAcid() {
      super(FluidsRegister.NITRICACID, Material.WATER);
      this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
      this.setTranslationKey("fluid_nitric_acid_block");
      this.setRegistryName("fluid_nitric_acid_block");
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

         PotionEffect baff = new PotionEffect(MobEffects.WITHER, 40, 1);
         base.addPotionEffect(baff);
         PotionEffect baff2 = new PotionEffect(MobEffects.POISON, 400);
         base.addPotionEffect(baff2);
      }
   }

   private void mergerFluids(BlockPos pos, World world) {
   }

   @Nullable
   public PathNodeType getAiPathNodeType(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable EntityLiving entity) {
      return entity != null && entity.isEntityInvulnerable(DamageSource.WITHER) && entity.isEntityInvulnerable(DamageSource.MAGIC)
         ? PathNodeType.WATER
         : PathNodeType.LAVA;
   }
}
