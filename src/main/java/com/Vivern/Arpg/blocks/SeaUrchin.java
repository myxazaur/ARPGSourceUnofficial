//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.mobs.HostileProjectiles;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class SeaUrchin extends Block implements IBlockHardBreak {
   protected static final AxisAlignedBB STANDING_AABB = new AxisAlignedBB(0.01, 0.01, 0.01, 0.99, 0.99, 0.99);

   public SeaUrchin() {
      super(Material.ROCK);
      this.setRegistryName("sea_urchin");
      this.setTranslationKey("sea_urchin");
      this.blockHardness = BlocksRegister.HR_CORALS.HARDNESS;
      this.blockResistance = BlocksRegister.HR_CORALS.RESISTANCE;
      this.setHarvestLevel("axe", BlocksRegister.HR_CORALS.LVL);
      this.setSoundType(SoundTypeCrunchy.CRUNCHY);
      this.setCreativeTab(CreativeTabs.DECORATIONS);
   }

   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      return STANDING_AABB;
   }

   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
      return STANDING_AABB;
   }

   public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entityIn) {
      if (!world.isRemote && entityIn.hurtResistantTime <= 0) {
         if (entityIn instanceof EntityPlayer) {
            entityIn.attackEntityFrom(DamageSource.CACTUS, 8.0F);
            if (RANDOM.nextFloat() < 0.22F) {
               world.destroyBlock(pos, false);
               world.setBlockState(pos, Blocks.WATER.getDefaultState());

               for (int i = 0; i < 3; i++) {
                  HostileProjectiles.SeaBomb entityshoot = new HostileProjectiles.SeaBomb(world);
                  entityshoot.shoot(entityIn, 30 - RANDOM.nextInt(61), RANDOM.nextInt(360), 0.0F, 0.4F, 9.5F);
                  entityshoot.setPosition(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
                  entityshoot.damage = 12.0F;
                  entityshoot.explodeTimeOffset = RANDOM.nextInt(10) - 70;
                  entityshoot.dropBlocks = false;
                  world.spawnEntity(entityshoot);
               }
            }
         } else {
            entityIn.attackEntityFrom(DamageSource.CACTUS, 4.0F);
         }
      }
   }

   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.CUTOUT;
   }

   public boolean isFullCube(IBlockState state) {
      return true;
   }

   @Override
   public float getBlockBreakingSpeed(World world, String tool, int toolLevel, IBlockState state, BlockPos pos, float originalSpeed) {
      return BlocksRegister.HR_CORALS.getBlockBreakingSpeed(world, tool, toolLevel, state, pos, originalSpeed);
   }
}
