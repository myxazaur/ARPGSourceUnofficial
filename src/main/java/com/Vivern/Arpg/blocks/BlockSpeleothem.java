//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.main.Sounds;
import com.google.common.base.Predicate;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.Block.EnumOffsetType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockSpeleothem extends Block {
   public static final PropertyInteger TYPE = PropertyInteger.create("type", 0, 8);
   public static final double d0 = 0.28125;
   public static final double d1 = 0.71875;
   public static final double d2 = 0.34375;
   public static final double d3 = 0.65625;
   public static final double d4 = 0.40625;
   public static final double d5 = 0.59375;
   public static final AxisAlignedBB AABB_BIG = new AxisAlignedBB(0.28125, 0.0, 0.28125, 0.71875, 1.0, 0.71875);
   public static final AxisAlignedBB AABB_MID = new AxisAlignedBB(0.34375, 0.0, 0.34375, 0.65625, 1.0, 0.65625);
   public static final AxisAlignedBB AABB_LOW = new AxisAlignedBB(0.40625, 0.0, 0.40625, 0.59375, 1.0, 0.59375);
   public static final AxisAlignedBB AABB_DOWN_PLATE = new AxisAlignedBB(0.125, 0.0, 0.125, 0.875, 0.125, 0.875);
   public static final AxisAlignedBB AABB_UP_PLATE = new AxisAlignedBB(0.125, 0.875, 0.125, 0.875, 1.0, 0.875);
   public static final AxisAlignedBB AABB_7 = new AxisAlignedBB(0.34375, 0.5, 0.34375, 0.65625, 1.0, 0.65625);
   public static final AxisAlignedBB AABB_8 = new AxisAlignedBB(0.34375, 0.0, 0.34375, 0.65625, 0.5, 0.65625);
   public boolean alwaysDrops;

   public BlockSpeleothem(Material mater, String name, float hard, float resi) {
      super(mater);
      this.setRegistryName(name);
      this.setTranslationKey(name);
      this.blockHardness = hard;
      this.blockResistance = resi;
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setLightOpacity(1);
   }

   public BlockSpeleothem(Material mater, String name, float hard, float resi, SoundType soundType) {
      super(mater);
      this.setRegistryName(name);
      this.setTranslationKey(name);
      this.blockHardness = hard;
      this.blockResistance = resi;
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setLightOpacity(1);
      this.setSoundType(soundType);
   }

   public BlockSpeleothem setAlwaysDrops() {
      this.alwaysDrops = true;
      return this;
   }

   public boolean canStay(World world, BlockPos pos, IBlockState state) {
      int ths = (Integer)state.getValue(TYPE);
      if (ths == 0) {
         return world.isSideSolid(pos.up(), EnumFacing.DOWN);
      } else if (ths == 4) {
         return world.isSideSolid(pos.down(), EnumFacing.UP);
      } else if (ths == 7 || ths == 1 || ths == 5) {
         BlockPos fpos = pos.up();
         IBlockState st = world.getBlockState(fpos);
         return st.isSideSolid(world, fpos, EnumFacing.DOWN) || st.getBlockFaceShape(world, fpos, EnumFacing.DOWN) == BlockFaceShape.CENTER_BIG;
      } else if (ths != 8 && ths != 3 && ths != 6) {
         if (ths == 2) {
            BlockPos fpos1 = pos.down();
            BlockPos fpos2 = pos.up();
            IBlockState st1 = world.getBlockState(fpos1);
            IBlockState st2 = world.getBlockState(fpos2);
            if (st1.getBlock() != this
               && (
                  st1.isSideSolid(world, fpos1, EnumFacing.UP)
                     || st1.getBlockFaceShape(world, fpos1, EnumFacing.UP) == BlockFaceShape.CENTER_BIG
                     || st1.getBlockFaceShape(world, fpos1, EnumFacing.UP) == BlockFaceShape.CENTER_SMALL
                     || st1.getBlockFaceShape(world, fpos1, EnumFacing.UP) == BlockFaceShape.CENTER
               )) {
               return true;
            } else if (st2.getBlock() == this
               || !st2.isSideSolid(world, fpos2, EnumFacing.DOWN)
                  && st2.getBlockFaceShape(world, fpos2, EnumFacing.DOWN) != BlockFaceShape.CENTER_BIG
                  && st2.getBlockFaceShape(world, fpos2, EnumFacing.DOWN) != BlockFaceShape.CENTER_SMALL
                  && st2.getBlockFaceShape(world, fpos2, EnumFacing.DOWN) != BlockFaceShape.CENTER) {
               boolean checkUp = true;
               boolean checkDown = true;

               for (int y = 1; y < 255; y++) {
                  if (checkUp) {
                     BlockPos posy1 = pos.up(y);
                     if (posy1.getY() >= 255) {
                        checkUp = false;
                     }

                     if (world.getBlockState(posy1).getBlock() != this) {
                        if (this.isFaceShapeGood(world, EnumFacing.DOWN, posy1)) {
                           return true;
                        }

                        checkUp = false;
                     }
                  }

                  if (checkDown) {
                     BlockPos posy2 = pos.down(y);
                     if (posy2.getY() <= 0) {
                        checkDown = false;
                     }

                     if (world.getBlockState(posy2).getBlock() != this) {
                        if (this.isFaceShapeGood(world, EnumFacing.UP, posy2)) {
                           return true;
                        }

                        checkDown = false;
                     }
                  }
               }

               return false;
            } else {
               return true;
            }
         } else {
            return false;
         }
      } else {
         BlockPos fpos = pos.down();
         IBlockState st = world.getBlockState(fpos);
         return st.isSideSolid(world, fpos, EnumFacing.UP) || st.getBlockFaceShape(world, fpos, EnumFacing.UP) == BlockFaceShape.CENTER_BIG;
      }
   }

   public boolean isFaceShapeGood(World world, EnumFacing face, BlockPos pos) {
      IBlockState state = world.getBlockState(pos);
      return this.isFaceShapeGood(world, state, face, pos);
   }

   public boolean isFaceShapeGood(World world, IBlockState state, EnumFacing face, BlockPos pos) {
      BlockFaceShape bfs = state.getBlockFaceShape(world, pos, face);
      return state.isSideSolid(world, pos, face) || bfs == BlockFaceShape.CENTER_BIG || bfs == BlockFaceShape.CENTER_SMALL || bfs == BlockFaceShape.CENTER;
   }

   public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
      int ths = (Integer)state.getValue(TYPE);
      if (ths == 0 || ths == 4) {
         return AABB_BIG;
      } else {
         return ths != 1 && ths != 3 ? NULL_AABB : AABB_MID;
      }
   }

   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      Vec3d vec = state.getOffset(source, pos);
      return this.getBox(state, source, pos).offset(vec.x, 0.0, vec.z);
   }

   public AxisAlignedBB getBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      int ths = (Integer)state.getValue(TYPE);
      if (ths == 0 || ths == 4) {
         return AABB_BIG;
      } else if (ths == 1 || ths == 3) {
         return AABB_MID;
      } else if (ths == 2) {
         return AABB_LOW;
      } else if (ths == 5) {
         return AABB_UP_PLATE;
      } else if (ths == 6) {
         return AABB_DOWN_PLATE;
      } else if (ths == 7) {
         return AABB_7;
      } else {
         return ths == 8 ? AABB_8 : NULL_AABB;
      }
   }

   public EnumOffsetType getOffsetType() {
      return EnumOffsetType.XYZ;
   }

   public Vec3d getOffset(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
      int ths = (Integer)state.getValue(TYPE);
      if (ths == 6) {
         long i = MathHelper.getCoordinateRandom(pos.getX(), pos.getY(), pos.getZ());
         return new Vec3d(
            ((float)(i >> 16 & 15L) / 15.0F - 0.5) * 0.4, ((float)(i >> 20 & 15L) / 15.0F - 1.0) * 0.4, ((float)(i >> 24 & 15L) / 15.0F - 0.5) * 0.4
         );
      } else if (ths == 5) {
         long i = MathHelper.getCoordinateRandom(pos.getX(), pos.getY(), pos.getZ());
         return new Vec3d(((float)(i >> 16 & 15L) / 15.0F - 0.5) * 0.4, (float)(i >> 20 & 15L) / 15.0F * 0.4, ((float)(i >> 24 & 15L) / 15.0F - 0.5) * 0.4);
      } else {
         long i = MathHelper.getCoordinateRandom(pos.getX(), 0, pos.getZ());
         return new Vec3d(((float)(i >> 16 & 15L) / 15.0F - 0.5) * 0.4, 0.0, ((float)(i >> 24 & 15L) / 15.0F - 0.5) * 0.4);
      }
   }

   public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
      return false;
   }

   public int quantityDropped(Random random) {
      return this.alwaysDrops ? 1 : 0;
   }

   @SideOnly(Side.CLIENT)
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.CUTOUT;
   }

   protected boolean canSilkHarvest() {
      return true;
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   public boolean isReplaceableOreGen(IBlockState state, IBlockAccess world, BlockPos pos, Predicate<IBlockState> target) {
      return false;
   }

   public boolean isFullCube(IBlockState state) {
      return false;
   }

   public IBlockState getStateFromMeta(int meta) {
      return this.getDefaultState().withProperty(TYPE, meta);
   }

   public int getMetaFromState(IBlockState state) {
      return (Integer)state.getValue(TYPE);
   }

   protected BlockStateContainer createBlockState() {
      return new BlockStateContainer(this, new IProperty[]{TYPE});
   }

   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
      if (this.canStay(worldIn, pos, state)) {
         int ths = (Integer)state.getValue(TYPE);
         if (fromPos.getY() > pos.getY()) {
            IBlockState stateup = worldIn.getBlockState(fromPos);
            if (stateup.getBlock() instanceof BlockSpeleothem) {
               int up = (Integer)stateup.getValue(TYPE);
               if (up == 3 || up == 8) {
                  worldIn.setBlockState(pos, state.withProperty(TYPE, 4));
               }

               if (up == 2 && ths == 8) {
                  worldIn.setBlockState(pos, state.withProperty(TYPE, 3));
               }
            }
         }

         if (fromPos.getY() < pos.getY()) {
            IBlockState statedown = worldIn.getBlockState(fromPos);
            if (statedown.getBlock() instanceof BlockSpeleothem) {
               int down = (Integer)statedown.getValue(TYPE);
               if (down == 1 || down == 7) {
                  worldIn.setBlockState(pos, state.withProperty(TYPE, 0));
               }

               if (down == 2 && ths == 7) {
                  worldIn.setBlockState(pos, state.withProperty(TYPE, 1));
               }
            }
         }
      } else {
         if (this.alwaysDrops) {
            this.dropBlockAsItem(worldIn, pos, state, 0);
         }

         worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
         worldIn.playSound(
            null,
            pos.getX(),
            pos.getY(),
            pos.getZ(),
            Sounds.speleothem_crack,
            SoundCategory.BLOCKS,
            0.8F,
            0.875F + worldIn.rand.nextFloat() / 4.0F
         );
         if (worldIn instanceof WorldServer) {
            Vec3d vec = this.getOffset(state, worldIn, pos).add(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
            ((WorldServer)worldIn)
               .spawnParticle(
                  EnumParticleTypes.BLOCK_CRACK,
                  vec.x,
                  vec.y + RANDOM.nextFloat(),
                  vec.z,
                  12,
                  0.0,
                  0.0,
                  0.0,
                  0.08,
                  new int[]{Block.getStateId(state)}
               );
         }
      }

      super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
   }

   public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
      IBlockState stateup = worldIn.getBlockState(pos.up());
      IBlockState statedown = worldIn.getBlockState(pos.down());
      if (facing == EnumFacing.DOWN) {
         if (!(stateup.getBlock() instanceof BlockSpeleothem)) {
            return this.getDefaultState().withProperty(TYPE, 5);
         }

         int up = (Integer)stateup.getValue(TYPE);
         if (up == 0 || up == 5) {
            return this.getDefaultState().withProperty(TYPE, 7);
         }

         if (up == 1 || up == 2 || up == 7) {
            return this.getDefaultState().withProperty(TYPE, 2);
         }
      } else if (facing == EnumFacing.UP) {
         if (!(statedown.getBlock() instanceof BlockSpeleothem)) {
            return this.getDefaultState().withProperty(TYPE, 6);
         }

         int down = (Integer)statedown.getValue(TYPE);
         if (down == 4 || down == 6) {
            return this.getDefaultState().withProperty(TYPE, 8);
         }

         if (down == 3 || down == 2 || down == 8) {
            return this.getDefaultState().withProperty(TYPE, 2);
         }
      } else {
         if (stateup.getBlock() instanceof BlockSpeleothem) {
            int upx = (Integer)stateup.getValue(TYPE);
            if (upx == 0 || upx == 5) {
               return this.getDefaultState().withProperty(TYPE, 7);
            }

            if (upx == 1 || upx == 2 || upx == 7) {
               return this.getDefaultState().withProperty(TYPE, 2);
            }
         }

         if (statedown.getBlock() instanceof BlockSpeleothem) {
            int downx = (Integer)statedown.getValue(TYPE);
            if (downx != 4 && downx != 6) {
               if (downx != 3 && downx != 2 && downx != 8) {
                  return this.getDefaultState().withProperty(TYPE, 2);
               }

               return this.getDefaultState().withProperty(TYPE, 2);
            }

            return this.getDefaultState().withProperty(TYPE, 8);
         }
      }

      return this.getDefaultState().withProperty(TYPE, 2);
   }

   public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
      int ths = (Integer)state.getValue(TYPE);
      if (ths == 2) {
         return face != EnumFacing.UP && face != EnumFacing.DOWN ? BlockFaceShape.MIDDLE_POLE_THIN : BlockFaceShape.CENTER_SMALL;
      } else if (ths != 1 && ths != 3) {
         if (ths == 0 || ths == 4) {
            return face != EnumFacing.UP && face != EnumFacing.DOWN ? BlockFaceShape.MIDDLE_POLE_THICK : BlockFaceShape.CENTER_BIG;
         } else if (ths == 7 && face == EnumFacing.UP) {
            return BlockFaceShape.CENTER;
         } else {
            return ths == 8 && face == EnumFacing.DOWN ? BlockFaceShape.CENTER : BlockFaceShape.UNDEFINED;
         }
      } else {
         return face != EnumFacing.UP && face != EnumFacing.DOWN ? BlockFaceShape.MIDDLE_POLE : BlockFaceShape.CENTER;
      }
   }
}
