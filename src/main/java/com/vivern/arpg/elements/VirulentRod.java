package com.vivern.arpg.elements;

import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.mobs.BossAbomination;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;

public class VirulentRod extends Item {
   public VirulentRod() {
      this.setRegistryName("virulent_rod");
      this.setCreativeTab(CreativeTabs.MISC);
      this.setTranslationKey("virulent_rod");
      this.setMaxStackSize(16);
   }

   public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
      ItemStack itemstack = player.getHeldItem(hand);
      player.setActiveHand(hand);
      RayTraceResult raytraceresult = this.rayTrace(world, player, false);
      if (raytraceresult == null) {
         return new ActionResult(EnumActionResult.PASS, itemstack);
      } else if (raytraceresult.typeOfHit != Type.BLOCK) {
         return new ActionResult(EnumActionResult.PASS, itemstack);
      } else if (world.provider.getDimension() != 101) {
         if (player instanceof EntityPlayerSP) {
            ((EntityPlayerSP)player).sendChatMessage("Fail! Boss can be summoned only in Toxicomania dimension");
         }

         return new ActionResult(EnumActionResult.FAIL, itemstack);
      } else if (!world.isRemote) {
         BlockPos pos = raytraceresult.getBlockPos();

         for (int i = 0; i < 40; i++) {
            BlockPos pos2 = pos.add(itemRand.nextInt(17) - 8, itemRand.nextInt(11) - 3, itemRand.nextInt(17) - 8);
            BlockPos pos3 = GetMOP.getTrueHeight(world, pos2);
            AxisAlignedBB aabb = new AxisAlignedBB(pos3.add(-1, 1, -1), pos3.add(1, 3, 1));
            if (!world.collidesWithAnyBlock(aabb)) {
               BossAbomination boss = new BossAbomination(world);
               boss.setPosition(pos3.getX(), Math.max(pos3.getY() - 40, 2), pos3.getZ());
               boss.summonning = true;
               boss.summonY = pos3.getY();
               world.spawnEntity(boss);
               boss.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(boss)), (IEntityLivingData)null);
               boss.canDropLoot = true;
               itemstack.shrink(1);
               return new ActionResult(EnumActionResult.SUCCESS, itemstack);
            }
         }

         if (player instanceof EntityPlayerSP) {
            ((EntityPlayerSP)player).sendChatMessage("Fail! Try to summon on flatter terrain");
         }

         return new ActionResult(EnumActionResult.FAIL, itemstack);
      } else {
         return new ActionResult(EnumActionResult.PASS, itemstack);
      }
   }
}
