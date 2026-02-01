package com.vivern.arpg.elements;

import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.mobs.BossKraken;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorshippersBait extends Item {
   static List<int[]> krakenOffsets = new ArrayList<>();

   public WorshippersBait() {
      this.setRegistryName("worshippers_bait");
      this.setCreativeTab(CreativeTabs.MISC);
      this.setTranslationKey("worshippers_bait");
      this.setMaxStackSize(16);
   }

   public int getEntityLifespan(ItemStack itemStack, World world) {
      return 600;
   }

   public boolean onEntityItemUpdate(EntityItem entityItem) {
      if (entityItem.dimension == 103 && entityItem.ticksExisted % 18 == 0 && !entityItem.world.isRemote) {
         if (krakenOffsets.isEmpty()) {
            for (int x = -32; x <= 32; x += 4) {
               for (int z = -32; z <= 32; z += 4) {
                  krakenOffsets.add(new int[]{x, z});
               }
            }
         }

         Collections.shuffle(krakenOffsets);

         for (int[] offset : krakenOffsets) {
            BlockPos xzxpos = entityItem.getPosition().add(offset[0], 0, offset[1]);
            GetMOP.BlockTraceResult result = GetMOP.blockTrace(entityItem.world, xzxpos, EnumFacing.DOWN, 200, GetMOP.SOLID_BLOCKS);
            if (result != null) {
               for (int y = result.pos.getY() + 8; y < entityItem.posY - 16.0; y++) {
                  AxisAlignedBB aabb = new AxisAlignedBB(
                     xzxpos.getX() - 4, y, xzxpos.getZ() - 4, xzxpos.getX() + 4, y + 8, xzxpos.getZ() + 4
                  );
                  List<BlockPos> list = GetMOP.getBlockPosesCollidesAABBwithTheirHitbox(entityItem.world, aabb, false);
                  if (list.isEmpty()) {
                     BossKraken boss = new BossKraken(entityItem.world);
                     boss.setPosition(xzxpos.getX(), y, xzxpos.getZ());
                     entityItem.world.spawnEntity(boss);
                     boss.onInitialSpawn(entityItem.world.getDifficultyForLocation(new BlockPos(boss)), (IEntityLivingData)null);
                     boss.canDropLoot = true;
                     entityItem.world
                        .playSound(
                           (EntityPlayer)null,
                           xzxpos.getX(),
                           y,
                           xzxpos.getZ(),
                           Sounds.kraken_roar,
                           SoundCategory.HOSTILE,
                           10.0F,
                           0.85F + itemRand.nextFloat() / 10.0F
                        );
                     entityItem.setDead();
                     return super.onEntityItemUpdate(entityItem);
                  }
               }
            }
         }
      }

      return super.onEntityItemUpdate(entityItem);
   }
}
