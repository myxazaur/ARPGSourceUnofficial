package com.vivern.arpg.blocks;

import com.vivern.arpg.entity.EntityCoin;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.mobs.ToxicomaniaMobsPack;
import com.vivern.arpg.potions.PotionEffects;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockLootBlob extends Block {
   public BlockLootBlob() {
      super(Material.CLAY);
      this.setRegistryName("loot_blob");
      this.setTranslationKey("loot_blob");
      this.blockHardness = 1.0F;
      this.blockResistance = 1.0F;
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setSoundType(SoundType.SLIME);
      this.setHarvestLevel("shovel", 0);
   }

   @SideOnly(Side.CLIENT)
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.TRANSLUCENT;
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
      if (!worldIn.isRemote && !worldIn.restoringBlockSnapshots) {
         if (RANDOM.nextFloat() < 0.04) {
            if (RANDOM.nextFloat() < 0.8) {
               for (int i = 0; i < 3 + RANDOM.nextInt(5); i++) {
                  ToxicomaniaMobsPack.Springer mob = new ToxicomaniaMobsPack.Springer(worldIn);
                  mob.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                  worldIn.spawnEntity(mob);
                  mob.onInitialSpawn();
               }
            } else {
               for (int i = 0; i < 2 + RANDOM.nextInt(4); i++) {
                  ToxicomaniaMobsPack.TestTubeSubstance mob = new ToxicomaniaMobsPack.TestTubeSubstance(worldIn);
                  mob.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                  worldIn.spawnEntity(mob);
                  mob.onInitialSpawn();
               }
            }

            return;
         }

         if (RANDOM.nextFloat() < 0.22 * chance) {
            spawnAsEntity(worldIn, pos, new ItemStack(ItemsRegister.BULLETPOISON, 10 + RANDOM.nextInt(26)));
         } else if (RANDOM.nextFloat() < 0.06 * chance) {
            spawnAsEntity(worldIn, pos, new ItemStack(ItemsRegister.BULLETTOXIC, 4 + RANDOM.nextInt(26)));
         } else if (RANDOM.nextFloat() < 0.1 * chance) {
            spawnAsEntity(worldIn, pos, new ItemStack(ItemsRegister.BULLETLEAD, 12 + RANDOM.nextInt(26)));
         } else if (RANDOM.nextFloat() < 0.09 * chance) {
            spawnAsEntity(worldIn, pos, new ItemStack(ItemsRegister.BULLETEXPLODING, 6 + RANDOM.nextInt(22)));
         }

         if (RANDOM.nextFloat() < 0.18 * chance) {
            spawnAsEntity(worldIn, pos, new ItemStack(ItemsRegister.ROCKETCHEMICAL, 1 + RANDOM.nextInt(6)));
         } else if (RANDOM.nextFloat() < 0.2 * chance) {
            spawnAsEntity(worldIn, pos, new ItemStack(ItemsRegister.ROCKETCOMMON, 1 + RANDOM.nextInt(7)));
         } else if (RANDOM.nextFloat() < 0.1 * chance) {
            spawnAsEntity(worldIn, pos, new ItemStack(ItemsRegister.ROCKETMINING, 1 + RANDOM.nextInt(8)));
         }

         if (RANDOM.nextFloat() < 0.6 * chance) {
            for (int i = 0; i < 1 + RANDOM.nextInt(9); i++) {
               EntityCoin coin = new EntityCoin(worldIn, 1 + (RANDOM.nextFloat() < 0.3 ? 1 : 0));
               coin.setPosition(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
               worldIn.spawnEntity(coin);
            }
         }

         if (RANDOM.nextFloat() < 0.03 * chance) {
            spawnAsEntity(worldIn, pos, new ItemStack(ItemsRegister.SLIMEBLOBWAND, 1));
         } else if (RANDOM.nextFloat() < 0.03 * chance) {
            spawnAsEntity(worldIn, pos, new ItemStack(ItemsRegister.BROWNSLIMEWAND, 1));
         } else if (RANDOM.nextFloat() < 0.2 * chance) {
            spawnAsEntity(worldIn, pos, new ItemStack(Items.GUNPOWDER, 5 + RANDOM.nextInt(10)));
         } else if (RANDOM.nextFloat() < 0.3 * chance) {
            spawnAsEntity(worldIn, pos, new ItemStack(Items.SLIME_BALL, 3 + RANDOM.nextInt(6)));
         } else if (RANDOM.nextFloat() < 0.2 * chance) {
            spawnAsEntity(worldIn, pos, PotionUtils.addPotionToItemStack(new ItemStack(Items.TIPPED_ARROW, 8 + RANDOM.nextInt(16)), PotionTypes.LONG_POISON));
         } else if (RANDOM.nextFloat() < 0.1 * chance) {
            spawnAsEntity(
               worldIn, pos, PotionUtils.addPotionToItemStack(new ItemStack(Items.TIPPED_ARROW, 6 + RANDOM.nextInt(12)), PotionEffects.TOXIN_POTIONTYPE_LONG)
            );
         } else if (RANDOM.nextFloat() < 0.01 * chance) {
            spawnAsEntity(worldIn, pos, new ItemStack(ItemsRegister.GASMASK, 1));
         } else {
            spawnAsEntity(worldIn, pos, new ItemStack(ItemsRegister.RUSTEDKEY, 1));
         }
      }
   }

   public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune) {
      return MathHelper.getInt(RANDOM, 2, 10);
   }
}
