package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.mobs.AbstractMob;
import java.util.List;
import net.minecraft.block.BlockLiquid;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;

public class ItemMobEgg extends ItemItem {
   public ResourceLocation mobname;

   public ItemMobEgg(String name, int maxstacksize, ResourceLocation mobname) {
      super(name, CreativeTabs.MISC, 0, maxstacksize);
      this.mobname = mobname;
   }

   public void applyTeam(Entity entity, EntityPlayer player) {
      if (entity instanceof AbstractMob) {
         AbstractMob absmob = (AbstractMob)entity;
         absmob.tame(player);
      }
   }

   protected double getYOffset(World p_190909_1_, BlockPos p_190909_2_) {
      AxisAlignedBB axisalignedbb = new AxisAlignedBB(p_190909_2_).expand(0.0, -1.0, 0.0);
      List<AxisAlignedBB> list = p_190909_1_.getCollisionBoxes((Entity)null, axisalignedbb);
      if (list.isEmpty()) {
         return 0.0;
      } else {
         double d0 = axisalignedbb.minY;

         for (AxisAlignedBB axisalignedbb1 : list) {
            d0 = Math.max(axisalignedbb1.maxY, d0);
         }

         return d0 - p_190909_2_.getY();
      }
   }

   public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
      ItemStack itemstack = player.getHeldItem(hand);
      if (worldIn.isRemote) {
         return EnumActionResult.SUCCESS;
      } else if (!player.canPlayerEdit(pos.offset(facing), facing, itemstack)) {
         return EnumActionResult.FAIL;
      } else {
         BlockPos blockpos = pos.offset(facing);
         double d0 = this.getYOffset(worldIn, blockpos);
         Entity entity = ItemMonsterPlacer.spawnCreature(
            worldIn, this.mobname, blockpos.getX() + 0.5, blockpos.getY() + d0, blockpos.getZ() + 0.5
         );
         if (entity != null) {
            if (entity instanceof EntityLivingBase && itemstack.hasDisplayName()) {
               entity.setCustomNameTag(itemstack.getDisplayName());
            }

            if (!player.capabilities.isCreativeMode) {
               itemstack.shrink(1);
            }

            this.applyTeam(entity, player);
         }

         return EnumActionResult.SUCCESS;
      }
   }

   public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
      ItemStack itemstack = playerIn.getHeldItem(handIn);
      if (worldIn.isRemote) {
         return new ActionResult(EnumActionResult.PASS, itemstack);
      } else {
         RayTraceResult raytraceresult = this.rayTrace(worldIn, playerIn, true);
         if (raytraceresult != null && raytraceresult.typeOfHit == Type.BLOCK) {
            BlockPos blockpos = raytraceresult.getBlockPos();
            if (!(worldIn.getBlockState(blockpos).getBlock() instanceof BlockLiquid)) {
               return new ActionResult(EnumActionResult.PASS, itemstack);
            } else if (worldIn.isBlockModifiable(playerIn, blockpos) && playerIn.canPlayerEdit(blockpos, raytraceresult.sideHit, itemstack)) {
               Entity entity = ItemMonsterPlacer.spawnCreature(
                  worldIn, this.mobname, blockpos.getX() + 0.5, blockpos.getY() + 0.5, blockpos.getZ() + 0.5
               );
               if (entity == null) {
                  return new ActionResult(EnumActionResult.PASS, itemstack);
               } else {
                  if (entity instanceof EntityLivingBase && itemstack.hasDisplayName()) {
                     entity.setCustomNameTag(itemstack.getDisplayName());
                  }

                  if (!playerIn.capabilities.isCreativeMode) {
                     itemstack.shrink(1);
                  }

                  this.applyTeam(entity, playerIn);
                  playerIn.addStat(StatList.getObjectUseStats(this));
                  return new ActionResult(EnumActionResult.SUCCESS, itemstack);
               }
            } else {
               return new ActionResult(EnumActionResult.FAIL, itemstack);
            }
         } else {
            return new ActionResult(EnumActionResult.PASS, itemstack);
         }
      }
   }
}
