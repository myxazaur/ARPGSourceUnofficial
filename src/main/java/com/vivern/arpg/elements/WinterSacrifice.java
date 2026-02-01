package com.vivern.arpg.elements;

import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.mobs.BossWinterFury;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;

public class WinterSacrifice extends Item {
   public WinterSacrifice() {
      this.setRegistryName("winter_sacrifice");
      this.setCreativeTab(CreativeTabs.MISC);
      this.setTranslationKey("winter_sacrifice");
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
      } else if (world.provider.getDimension() != 100) {
         if (player instanceof EntityPlayerSP) {
            ((EntityPlayerSP)player).sendChatMessage("Fail! Boss can be summoned only in Everfrost dimension");
         }

         return new ActionResult(EnumActionResult.FAIL, itemstack);
      } else if (!world.isRemote) {
         BlockPos pos = raytraceresult.getBlockPos();

         for (int i = 0; i < 40; i++) {
            BlockPos pos2 = pos.add(itemRand.nextInt(17) - 8, itemRand.nextInt(11) - 3, itemRand.nextInt(17) - 8);
            BlockPos pos3 = GetMOP.getTrueHeight(world, pos2);
            pos3 = new BlockPos(pos3.getX(), Math.max(pos3.getY() + 60, 100), pos3.getZ());
            AxisAlignedBB aabb = new AxisAlignedBB(pos3.add(-1, -1, -1), pos3.add(1, 1, 1));
            if (!world.collidesWithAnyBlock(aabb)) {
               BossWinterFury boss = new BossWinterFury(world);
               boss.setPosition(pos3.getX() + 0.5, pos3.getY(), pos3.getZ() + 0.5);
               boss.setAttackTarget(player);
               world.spawnEntity(boss);
               boss.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(boss)), (IEntityLivingData)null);
               boss.canDropLoot = true;
               itemstack.shrink(1);
               world.playSound(
                  (EntityPlayer)null,
                  player.posX,
                  player.posY,
                  player.posZ,
                  Sounds.winter_fury_roar,
                  SoundCategory.HOSTILE,
                  3.0F,
                  0.85F + itemRand.nextFloat() / 10.0F
               );
               return new ActionResult(EnumActionResult.SUCCESS, itemstack);
            }
         }

         if (player instanceof EntityPlayerSP) {
            ((EntityPlayerSP)player).sendChatMessage("Fail! пїЅhe sky should not be blocked");
         }

         return new ActionResult(EnumActionResult.FAIL, itemstack);
      } else {
         return new ActionResult(EnumActionResult.PASS, itemstack);
      }
   }
}
