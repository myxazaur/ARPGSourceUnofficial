//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.dimensions.ethernalfrost.DimensionEthernalFrost;
import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Sounds;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class SnowSewingTable extends Block {
   public static final PropertyBool READY = PropertyBool.create("ready");
   public static final PropertyInteger DAMAGE = PropertyInteger.create("damage", 0, 3);

   public SnowSewingTable() {
      super(Material.WOOD);
      this.setRegistryName("snow_sewing_table");
      this.setTranslationKey("snow_sewing_table");
      this.blockHardness = BlocksRegister.HR_CONIFER_PLANKS.HARDNESS;
      this.blockResistance = BlocksRegister.HR_CONIFER_PLANKS.RESISTANCE;
      this.setHarvestLevel("axe", BlocksRegister.HR_CONIFER_PLANKS.LVL);
      this.setSoundType(SoundType.WOOD);
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setTickRandomly(true);
   }

   public boolean isFullCube(IBlockState state) {
      return true;
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
      if (!(Boolean)state.getValue(READY)) {
         if (DimensionEthernalFrost.isSnowfallNow(world)) {
            world.setBlockState(pos, state.withProperty(READY, true));
            world.playSound(null, pos, Sounds.sewing_activate, SoundCategory.BLOCKS, 1.0F, 0.85F + RANDOM.nextFloat() * 0.3F);
         }
      } else if (rand.nextFloat() < 0.5F && !DimensionEthernalFrost.isSnowfallNow(world)) {
         world.setBlockState(pos, state.withProperty(READY, false));
      }
   }

   public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
      Random rand = RANDOM;
      int damage = (Integer)state.getValue(DAMAGE);
      if (damage == 0) {
         super.getDrops(drops, world, pos, state, fortune);
      } else {
         int count = 5 - damage - rand.nextInt(3);
         if (count > 0) {
            for (int i = 0; i < count; i++) {
               drops.add(new ItemStack(ItemsRegister.CONIFERSTICK, 1));
            }
         }

         count = 5 - damage - rand.nextInt(3);
         if (count > 0) {
            for (int i = 0; i < count; i++) {
               drops.add(new ItemStack(ItemsRegister.WINTERWILLOWSEEDS, 1));
            }
         }

         if (rand.nextFloat() > 0.25F * damage) {
            drops.add(new ItemStack(ItemsRegister.ICEGEM, 1));
         }
      }
   }

   public void deactivateAndDamage(World world, BlockPos pos, IBlockState state) {
      int damage = (Integer)state.getValue(DAMAGE);
      if (RANDOM.nextFloat() < 0.8) {
         if (damage < 3) {
            world.setBlockState(pos, state.withProperty(READY, false).withProperty(DAMAGE, damage + 1));
         } else {
            world.destroyBlock(pos, false);
            world.playSound(null, pos, SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.BLOCKS, 0.7F, 0.85F + RANDOM.nextFloat() * 0.3F);
         }
      } else {
         world.setBlockState(pos, state.withProperty(READY, false));
      }
   }

   public boolean onBlockActivated(
      World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ
   ) {
      if (!world.isRemote && hand == EnumHand.MAIN_HAND && (Boolean)state.getValue(READY)) {
         IBlockState stateup = world.getBlockState(pos.up());
         Block block = stateup.getBlock();
         if (block == Blocks.SNOW || block == Blocks.SNOW_LAYER && (Integer)stateup.getValue(BlockSnow.LAYERS) > 5) {
            world.setBlockState(pos.up(), Blocks.SNOW_LAYER.getDefaultState().withProperty(BlockSnow.LAYERS, 5));
            world.playSound(null, pos, Sounds.sewing, SoundCategory.BLOCKS, 0.7F, 0.85F + RANDOM.nextFloat() * 0.3F);
            return true;
         }

         if (block == Blocks.SNOW_LAYER) {
            if (RANDOM.nextFloat() < 0.9) {
               int l = (Integer)stateup.getValue(BlockSnow.LAYERS);
               if (l > 1) {
                  world.setBlockState(pos.up(), Blocks.SNOW_LAYER.getDefaultState().withProperty(BlockSnow.LAYERS, l - 1));
               } else {
                  world.setBlockToAir(pos.up());
               }

               world.playSound(null, pos, Sounds.sewing, SoundCategory.BLOCKS, 0.7F, 0.85F + RANDOM.nextFloat() * 0.3F);
               return true;
            }

            world.setBlockToAir(pos.up());
            world.spawnEntity(
               new EntityItem(world, pos.getX() + 0.5, pos.getY() + 1.15, pos.getZ() + 0.5, new ItemStack(ItemsRegister.SNOWCLOTH))
            );
            this.deactivateAndDamage(world, pos, state);
            world.playSound(null, pos, Sounds.sewing, SoundCategory.BLOCKS, 0.7F, 0.85F + RANDOM.nextFloat() * 0.3F);
            return true;
         }

         if (block == BlocksRegister.LOOSESNOW) {
            if (RANDOM.nextFloat() < 0.9) {
               int l = (Integer)stateup.getValue(LooseSnow.LAYERS);
               if (l > 1) {
                  world.setBlockState(pos.up(), BlocksRegister.LOOSESNOW.getDefaultState().withProperty(LooseSnow.LAYERS, l - 1));
               } else {
                  world.setBlockToAir(pos.up());
               }

               world.playSound(null, pos, Sounds.sewing, SoundCategory.BLOCKS, 0.7F, 0.85F + RANDOM.nextFloat() * 0.3F);
               return true;
            }

            world.setBlockToAir(pos.up());
            world.spawnEntity(
               new EntityItem(world, pos.getX() + 0.5, pos.getY() + 1.15, pos.getZ() + 0.5, new ItemStack(ItemsRegister.SNOWCLOTH))
            );
            this.deactivateAndDamage(world, pos, state);
            world.playSound(null, pos, Sounds.sewing, SoundCategory.BLOCKS, 0.7F, 0.85F + RANDOM.nextFloat() * 0.3F);
            return true;
         }
      }

      return false;
   }

   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.CUTOUT;
   }

   public IBlockState getStateFromMeta(int meta) {
      return meta <= 3
         ? this.getDefaultState().withProperty(DAMAGE, meta).withProperty(READY, false)
         : this.getDefaultState().withProperty(DAMAGE, Math.min(meta - 4, 4)).withProperty(READY, true);
   }

   public int getMetaFromState(IBlockState state) {
      return (Integer)state.getValue(DAMAGE) + (state.getValue(READY) ? 4 : 0);
   }

   protected BlockStateContainer createBlockState() {
      return new BlockStateContainer(this, new IProperty[]{DAMAGE, READY});
   }
}
