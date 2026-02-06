package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.elements.SpellHammer;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Mana;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.renders.GUNParticle;
import com.Vivern.Arpg.tileentity.TileSpellForge;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BreakerBlock extends Block {
   public static AxisAlignedBB AABB = new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.9375, 1.0, 0.9375);
   static ResourceLocation tex = new ResourceLocation("arpg:textures/spellblue4.png");

   public BreakerBlock() {
      super(Material.ROCK);
      this.setRegistryName("breaker");
      this.setTranslationKey("breaker");
      this.blockHardness = 8.0F;
      this.blockResistance = 8.0F;
      this.setCreativeTab(CreativeTabs.DECORATIONS);
   }

   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      return AABB;
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   public boolean isFullCube(IBlockState state) {
      return false;
   }

   public Class<TileSpellForge> getTileEntityClass() {
      return TileSpellForge.class;
   }

   public TileSpellForge getTileEntity(IBlockAccess world, BlockPos position) {
      return (TileSpellForge)world.getTileEntity(position);
   }

   public boolean hasTileEntity(IBlockState blockState) {
      return true;
   }

   @Nullable
   public TileSpellForge createTileEntity(World world, IBlockState blockState) {
      return new TileSpellForge();
   }

   public boolean onBlockActivated(
      World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ
   ) {
      TileSpellForge tile = this.getTileEntity(worldIn, pos);
      ItemStack helditem = playerIn.getHeldItem(EnumHand.MAIN_HAND);
      boolean nocool = !playerIn.getCooldownTracker().hasCooldown(ItemsRegister.SPELLHAMMER);
      float mana = Mana.getMana(playerIn);
      int sor = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SORCERY, helditem);
      if (worldIn.isRemote) {
         if (helditem.getItem() == ItemsRegister.SPELLHAMMER && nocool && mana >= 5.0F - sor * 1.1F) {
            this.effect(worldIn, pos);
         }

         return true;
      } else {
         if (tile != null) {
            if (helditem.isEmpty()) {
               playerIn.displayGUIChest(tile);
               playerIn.swingArm(hand);
               return true;
            }

            if (helditem.getItem() == ItemsRegister.SPELLHAMMER && helditem.getItem() instanceof SpellHammer && nocool && mana >= 5.0F - sor * 1.1F) {
               helditem.damageItem(1, playerIn);
               worldIn.playSound(null, pos, Sounds.spell_forge, SoundCategory.BLOCKS, 1.5F, 0.9F + RANDOM.nextFloat() / 5.0F);
            }
         }

         return false;
      }
   }

   @SideOnly(Side.CLIENT)
   public void effect(World world, BlockPos pos) {
      for (int ss = 0; ss < 15; ss++) {
         GUNParticle bubble = new GUNParticle(
            tex,
            0.02F + RANDOM.nextFloat() / 50.0F,
            0.025F,
            10 + RANDOM.nextInt(20),
            240,
            world,
            pos.getX() + 0.5,
            pos.getY() + 1.1,
            pos.getZ() + 0.5,
            (float)RANDOM.nextGaussian() / 9.0F,
            (float)RANDOM.nextGaussian() / 9.0F + 0.17F,
            (float)RANDOM.nextGaussian() / 9.0F,
            0.5F + RANDOM.nextFloat() / 2.0F,
            0.5F + RANDOM.nextFloat() / 2.0F,
            0.5F + RANDOM.nextFloat() / 2.0F,
            false,
            RANDOM.nextInt(360),
            true,
            2.5F
         );
         world.spawnEntity(bubble);
      }
   }

   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
      TileSpellForge tile = this.getTileEntity(worldIn, pos);
      if (tile != null) {
         EnumFacing f = placer.getHorizontalFacing();
         if (f == EnumFacing.EAST) {
            tile.rotation = 90;
         }

         if (f == EnumFacing.NORTH) {
            tile.rotation = 180;
         }

         if (f == EnumFacing.WEST) {
            tile.rotation = 270;
         }
      }
   }
}
