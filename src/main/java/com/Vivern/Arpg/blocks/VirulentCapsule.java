package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.entity.ChlorineCloud;
import com.Vivern.Arpg.entity.EntityCubicParticle;
import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.renders.GUNParticle;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
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

public class VirulentCapsule extends BlockBlockHard {
   protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.1875, 0.0, 0.1875, 0.8125, 1.0, 0.8125);
   public static ResourceLocation resou = new ResourceLocation("arpg:textures/virulent_capsule.png");
   public static ResourceLocation largesmoke = new ResourceLocation("arpg:textures/largecloud.png");

   public VirulentCapsule() {
      super(Material.IRON, "virulent_capsule", BlocksRegister.HR_BUNKER, "pickaxe", false);
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setSoundType(SoundType.METAL);
   }

   public boolean onBlockActivated(
      World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ
   ) {
      if (player.getHeldItem(hand).getItem() == ItemsRegister.BUNKERKEYCARD) {
         player.swingArm(hand);
         if (!world.isRemote) {
            world.playSound(null, pos, Sounds.keycard_open, SoundCategory.BLOCKS, 1.3F, 0.9F + world.rand.nextFloat() / 5.0F);
            world.setBlockToAir(pos);
            EntityItem it = new EntityItem(
               world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, new ItemStack(ItemsRegister.VIRULENTROD)
            );
            it.motionX = 0.0;
            it.motionZ = 0.0;
            world.spawnEntity(it);
         } else {
            for (int i = 0; i < 6; i++) {
               EntityCubicParticle spel = new EntityCubicParticle(
                  resou,
                  0.027F,
                  0.03F,
                  40,
                  80,
                  world,
                  pos.getX() + 0.5,
                  pos.getY() + 0.5,
                  pos.getZ() + 0.5,
                  (float)RANDOM.nextGaussian() / 13.0F,
                  (float)RANDOM.nextGaussian() / 13.0F + 0.17F,
                  (float)RANDOM.nextGaussian() / 13.0F,
                  1.0F,
                  1.0F,
                  1.0F,
                  false,
                  RANDOM.nextFloat(),
                  RANDOM.nextFloat(),
                  RANDOM.nextFloat(),
                  0.08F,
                  true,
                  0.0F
               );
               world.spawnEntity(spel);
               GUNParticle bigsmoke = new GUNParticle(
                  largesmoke,
                  0.5F + RANDOM.nextFloat(),
                  3.0E-4F,
                  45,
                  130,
                  world,
                  pos.getX() + 0.5,
                  pos.getY() + 0.5,
                  pos.getZ() + 0.5,
                  (float)RANDOM.nextGaussian() / 14.0F,
                  (float)RANDOM.nextGaussian() / 23.0F,
                  (float)RANDOM.nextGaussian() / 14.0F,
                  0.4F + RANDOM.nextFloat() * 0.4F,
                  0.7F + RANDOM.nextFloat() * 0.3F,
                  0.7F,
                  true,
                  RANDOM.nextInt(360),
                  true,
                  1.0F
               );
               bigsmoke.tracker = ChlorineCloud.trssh;
               bigsmoke.alpha = 0.0F;
               world.spawnEntity(bigsmoke);
            }
         }
      }

      return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
   }

   @Override
   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   @Override
   public boolean isFullCube(IBlockState state) {
      return false;
   }

   @Override
   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      return AABB;
   }

   @Override
   public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      return AABB;
   }
}
