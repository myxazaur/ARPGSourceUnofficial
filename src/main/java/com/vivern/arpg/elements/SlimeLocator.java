package com.vivern.arpg.elements;

import java.util.List;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Biomes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;

public class SlimeLocator extends Item {
   public SlimeLocator() {
      this.setRegistryName("slime_locator");
      this.setCreativeTab(CreativeTabs.TOOLS);
      this.setTranslationKey("slime_locator");
      this.setMaxStackSize(1);
   }

   public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer playerIn, EnumHand handIn) {
      if (!world.isRemote) {
         BlockPos blockpos = new BlockPos(
            MathHelper.floor(playerIn.posX), MathHelper.floor(playerIn.posY), MathHelper.floor(playerIn.posZ)
         );
         if (playerIn instanceof EntityPlayerMP) {
            playerIn.sendMessage(new TextComponentString(this.getCanSpawnHere(world, blockpos)));
         }
      }

      return new ActionResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
   }

   public String getCanSpawnHere(World world, BlockPos blockpos) {
      Chunk chunk = world.getChunk(blockpos);
      if (world.getDifficulty() != EnumDifficulty.PEACEFUL) {
         Biome biome = world.getBiome(blockpos);
         if (biome == Biomes.SWAMPLAND) {
            if (blockpos.getY() > 50.0 && blockpos.getY() < 70.0) {
               return "пїЅaSlimes can spawn in Swampland biome";
            }

            return "пїЅ6Slimes can spawn in Swampland biome, but below 70 and above 50 height";
         }

         if (chunk.getRandomWithSeed(987234911L).nextInt(10) == 0) {
            if (blockpos.getY() < 40.0) {
               return "пїЅaSlimes can spawn here";
            }

            return "пїЅ6Slimes can spawn in this chunk, but below 40 height";
         }
      }

      return "пїЅ4Slimes can't spawn here";
   }

   public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
      tooltip.add("This locator can be used to find chunks, where vanilla slimes are spawn");
      super.addInformation(stack, worldIn, tooltip, flagIn);
   }
}
