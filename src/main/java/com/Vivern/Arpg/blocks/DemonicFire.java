//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.DimensionsRegister;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.potions.PotionEffects;
import com.Vivern.Arpg.tileentity.TilePortal;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class DemonicFire extends Block {
   public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 15);
   public static final PropertyBool NORTH = PropertyBool.create("north");
   public static final PropertyBool EAST = PropertyBool.create("east");
   public static final PropertyBool SOUTH = PropertyBool.create("south");
   public static final PropertyBool WEST = PropertyBool.create("west");
   public static final PropertyBool UPPER = PropertyBool.create("up");
   private final Map<Block, Integer> encouragements = Maps.newIdentityHashMap();
   private final Map<Block, Integer> flammabilities = Maps.newIdentityHashMap();

   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
      return !worldIn.getBlockState(pos.down()).isSideSolid(worldIn, pos.down(), EnumFacing.UP)
            && !Blocks.FIRE.canCatchFire(worldIn, pos.down(), EnumFacing.UP)
         ? state.withProperty(NORTH, this.canCatchFire(worldIn, pos.north(), EnumFacing.SOUTH))
            .withProperty(EAST, this.canCatchFire(worldIn, pos.east(), EnumFacing.WEST))
            .withProperty(SOUTH, this.canCatchFire(worldIn, pos.south(), EnumFacing.NORTH))
            .withProperty(WEST, this.canCatchFire(worldIn, pos.west(), EnumFacing.EAST))
            .withProperty(UPPER, this.canCatchFire(worldIn, pos.up(), EnumFacing.DOWN))
         : this.getDefaultState();
   }

   public DemonicFire() {
      super(Material.FIRE);
      this.setRegistryName("demonic_fire");
      this.setTranslationKey("demonic_fire");
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setDefaultState(
         this.blockState
            .getBaseState()
            .withProperty(AGE, 0)
            .withProperty(NORTH, false)
            .withProperty(EAST, false)
            .withProperty(SOUTH, false)
            .withProperty(WEST, false)
            .withProperty(UPPER, false)
      );
      this.setTickRandomly(true);
      this.setLightLevel(0.85F);
   }

   public void init() {
      this.setFireInfo(BlocksRegister.MANABOTTLE, 5, 20);
      this.setFireInfo(BlocksRegister.CRYSTALSPHERE, 5, 10);
      this.setFireInfo(BlocksRegister.ALCHEMICLAB, 5, 15);
      this.setFireInfo(BlocksRegister.ALCHEMICVAPORIZER, 5, 20);
      this.setFireInfo(BlocksRegister.CAVECRYSTALS, 7, 35);
      this.setFireInfo(BlocksRegister.GLOWINGCAVECRYSTALS, 5, 30);
      this.setFireInfo(BlocksRegister.MAGICSTONE, 7, 35);
      this.setFireInfo(BlocksRegister.GLOWINGVEIN, 4, 10);
      this.setFireInfo(BlocksRegister.INFERNUMFURNACE, 5, 20);
      this.setFireInfo(BlocksRegister.NETHERMELTER, 5, 15);
      this.setFireInfo(BlocksRegister.TOPAZCRYSTAL, 10, 30);
      this.setFireInfo(BlocksRegister.SACRIFICIALALTAR, 10, 25);
   }

   public void setFireInfo(Block blockIn, int encouragement, int flammability) {
      if (blockIn == Blocks.AIR) {
         throw new IllegalArgumentException("Tried to set air on fire... This is bad.");
      } else {
         this.encouragements.put(blockIn, encouragement);
         this.flammabilities.put(blockIn, flammability);
      }
   }

   public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entityIn) {
      if (!world.isRemote) {
         if (entityIn instanceof EntityItem) {
            EntityItem item = (EntityItem)entityIn;
            if (item.getItem().getItem() == Items.ENCHANTED_BOOK) {
               int powder = getBookPowderCount(item.getItem());
               world.playSound(
                  (EntityPlayer)null, item.getPosition(), Sounds.burn, SoundCategory.BLOCKS, 0.8F, 0.9F + world.rand.nextFloat() / 5.0F
               );
               item.setDead();
               EntityItem dust = new EntityItem(
                  item.world, item.posX, item.posY, item.posZ, new ItemStack(ItemsRegister.MAGIC_POWDER, powder)
               );
               item.world.spawnEntity(dust);
            }
         } else if (entityIn instanceof EntityLivingBase) {
            EntityLivingBase base = (EntityLivingBase)entityIn;
            base.addPotionEffect(new PotionEffect(PotionEffects.DEMONIC_BURN, 140));
         }
      }
   }

   public static int getBookPowderCount(ItemStack book) {
      Map<Enchantment, Integer> ench = EnchantmentHelper.getEnchantments(book);
      int i = 0;

      for (Entry<Enchantment, Integer> entry : ench.entrySet()) {
         if (entry.getKey() != null) {
            i += (entry.getKey().getRarity().ordinal() + 1) * entry.getValue();
         }
      }

      return i;
   }

   public void trySpawnPortal(BlockPos poss, World worldIn) {
      if (DimensionsRegister.teleporterEVERFROST.isReadyToActivate(worldIn, poss, false)) {
         for (BlockPos mpos : DimensionsRegister.teleporterEVERFROST.membraneConfiguration) {
            BlockPos fposs = poss.add(mpos);
            worldIn.setBlockState(fposs, DimensionsRegister.teleporterEVERFROST.portalBlock);
            TileEntity tile = worldIn.getTileEntity(fposs);
            if (tile instanceof TilePortal) {
               TilePortal portalTile = (TilePortal)tile;
               portalTile.isMainPortalBlock = mpos.getX() == 0 && mpos.getY() == 0 && mpos.getZ() == 0;
               portalTile.mainBlockPosition = poss;
               portalTile.dimensionToTeleport = worldIn.provider.getDimension() == DimensionsRegister.teleporterEVERFROST.dimensionID
                  ? 0
                  : DimensionsRegister.teleporterEVERFROST.dimensionID;
            }
         }
      } else if (DimensionsRegister.teleporterEVERFROST.isReadyToActivate(worldIn, poss, true)) {
         for (BlockPos mposx : DimensionsRegister.teleporterEVERFROST.membraneConfiguration) {
            BlockPos fposs = poss.add(mposx.getZ(), mposx.getY(), mposx.getX());
            worldIn.setBlockState(fposs, DimensionsRegister.teleporterEVERFROST.portalBlock);
            TileEntity tile = worldIn.getTileEntity(fposs);
            if (tile instanceof TilePortal) {
               TilePortal portalTile = (TilePortal)tile;
               portalTile.isMainPortalBlock = mposx.getX() == 0 && mposx.getY() == 0 && mposx.getZ() == 0;
               portalTile.mainBlockPosition = poss;
               portalTile.dimensionToTeleport = worldIn.provider.getDimension() == DimensionsRegister.teleporterEVERFROST.dimensionID
                  ? 0
                  : DimensionsRegister.teleporterEVERFROST.dimensionID;
            }
         }
      }
   }

   @Nullable
   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
      return NULL_AABB;
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   public boolean isFullCube(IBlockState state) {
      return false;
   }

   public int quantityDropped(Random random) {
      return 0;
   }

   public int tickRate(World worldIn) {
      return 30;
   }

   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
      if (worldIn.getGameRules().getBoolean("doFireTick")) {
         if (!worldIn.isAreaLoaded(pos, 2)) {
            return;
         }

         if (!this.canPlaceBlockAt(worldIn, pos)) {
            worldIn.setBlockToAir(pos);
         }

         Block block = worldIn.getBlockState(pos.down()).getBlock();
         boolean flag = block == Blocks.SOUL_SAND;
         int i = (Integer)state.getValue(AGE);
         if (!flag && worldIn.isRaining() && this.canDie(worldIn, pos) && rand.nextFloat() < 0.2F + i * 0.03F) {
            worldIn.setBlockToAir(pos);
         } else {
            if (i < 15) {
               state = state.withProperty(AGE, i + rand.nextInt(3) / 2);
               worldIn.setBlockState(pos, state, 4);
            }

            worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn) + rand.nextInt(10));
            if (!flag) {
               if (!this.canNeighborCatchFire(worldIn, pos)) {
                  if (!worldIn.getBlockState(pos.down()).isSideSolid(worldIn, pos.down(), EnumFacing.UP) || i > 3) {
                     worldIn.setBlockToAir(pos);
                  }

                  return;
               }

               if (!this.canCatchFire(worldIn, pos.down(), EnumFacing.UP) && i == 15 && rand.nextInt(4) == 0) {
                  worldIn.setBlockToAir(pos);
                  return;
               }
            }

            boolean flag1 = worldIn.isBlockinHighHumidity(pos);
            int j = 0;
            if (flag1) {
               j = -50;
            }

            this.tryCatchFire(worldIn, pos.east(), 300 + j, rand, i, EnumFacing.WEST);
            this.tryCatchFire(worldIn, pos.west(), 300 + j, rand, i, EnumFacing.EAST);
            this.tryCatchFire(worldIn, pos.down(), 250 + j, rand, i, EnumFacing.UP);
            this.tryCatchFire(worldIn, pos.up(), 250 + j, rand, i, EnumFacing.DOWN);
            this.tryCatchFire(worldIn, pos.north(), 300 + j, rand, i, EnumFacing.SOUTH);
            this.tryCatchFire(worldIn, pos.south(), 300 + j, rand, i, EnumFacing.NORTH);

            for (int k = -1; k <= 1; k++) {
               for (int l = -1; l <= 1; l++) {
                  for (int i1 = -1; i1 <= 4; i1++) {
                     if (k != 0 || i1 != 0 || l != 0) {
                        int j1 = 100;
                        if (i1 > 1) {
                           j1 += (i1 - 1) * 100;
                        }

                        BlockPos blockpos = pos.add(k, i1, l);
                        int k1 = this.getNeighborEncouragement(worldIn, blockpos);
                        if (k1 > 0) {
                           int l1 = (k1 + 40 + worldIn.getDifficulty().getId() * 7) / (i + 30);
                           if (flag1) {
                              l1 /= 2;
                           }

                           if (l1 > 0 && rand.nextInt(j1) <= l1 && (!worldIn.isRaining() || !this.canDie(worldIn, blockpos))) {
                              int i2 = i + rand.nextInt(5) / 4;
                              if (i2 > 15) {
                                 i2 = 15;
                              }

                              worldIn.setBlockState(blockpos, state.withProperty(AGE, i2), 3);
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   protected boolean canDie(World worldIn, BlockPos pos) {
      return worldIn.isRainingAt(pos)
         || worldIn.isRainingAt(pos.west())
         || worldIn.isRainingAt(pos.east())
         || worldIn.isRainingAt(pos.north())
         || worldIn.isRainingAt(pos.south());
   }

   public boolean requiresUpdates() {
      return false;
   }

   public int getFlammability(Block blockIn) {
      Integer integer = this.flammabilities.get(blockIn);
      return integer == null ? 0 : integer;
   }

   public int getEncouragement(Block blockIn) {
      Integer integer = this.encouragements.get(blockIn);
      return integer == null ? 0 : integer;
   }

   @Deprecated
   private void catchOnFire(World worldIn, BlockPos pos, int chance, Random random, int age) {
      this.tryCatchFire(worldIn, pos, chance, random, age, EnumFacing.UP);
   }

   private void tryCatchFire(World worldIn, BlockPos pos, int chance, Random random, int age, EnumFacing face) {
      int i = this.getFlammability(worldIn.getBlockState(pos).getBlock());
      if (random.nextInt(chance) < i) {
         IBlockState iblockstate = worldIn.getBlockState(pos);
         if (random.nextInt(age + 10) < 5 && !worldIn.isRainingAt(pos)) {
            int j = age + random.nextInt(5) / 4;
            if (j > 15) {
               j = 15;
            }

            worldIn.setBlockState(pos, this.getDefaultState().withProperty(AGE, j), 3);
         } else {
            worldIn.setBlockToAir(pos);
         }
      }
   }

   private boolean canNeighborCatchFire(World worldIn, BlockPos pos) {
      for (EnumFacing enumfacing : EnumFacing.values()) {
         if (this.canCatchFire(worldIn, pos.offset(enumfacing), enumfacing.getOpposite())) {
            return true;
         }
      }

      return false;
   }

   private int getNeighborEncouragement(World worldIn, BlockPos pos) {
      if (!worldIn.isAirBlock(pos)) {
         return 0;
      } else {
         int i = 0;

         for (EnumFacing enumfacing : EnumFacing.values()) {
            i = Math.max(this.getEncouragement(worldIn.getBlockState(pos.offset(enumfacing)).getBlock()), i);
         }

         return i;
      }
   }

   public boolean isCollidable() {
      return false;
   }

   @Deprecated
   public boolean canCatchFire(IBlockAccess worldIn, BlockPos pos) {
      return this.canCatchFire(worldIn, pos, EnumFacing.UP);
   }

   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
      return worldIn.getBlockState(pos.down()).isTopSolid() || this.canNeighborCatchFire(worldIn, pos);
   }

   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
      if (!worldIn.getBlockState(pos.down()).isTopSolid() && !this.canNeighborCatchFire(worldIn, pos)) {
         worldIn.setBlockToAir(pos);
      }
   }

   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
      boolean canbreak = DimensionsRegister.canPortalsBreak;
      DimensionsRegister.canPortalsBreak = false;
      this.trySpawnPortal(pos.add(0, 1, 0), worldIn);
      this.trySpawnPortal(pos.add(1, 1, 0), worldIn);
      this.trySpawnPortal(pos.add(0, 1, 1), worldIn);
      this.trySpawnPortal(pos.add(-1, 1, 0), worldIn);
      this.trySpawnPortal(pos.add(0, 1, -1), worldIn);
      DimensionsRegister.canPortalsBreak = canbreak;
      if (!worldIn.getBlockState(pos.down()).isTopSolid() && !this.canNeighborCatchFire(worldIn, pos)) {
         worldIn.setBlockToAir(pos);
      } else {
         worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn) + worldIn.rand.nextInt(10));
      }
   }

   public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
      return MapColor.PURPLE;
   }

   @SideOnly(Side.CLIENT)
   public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
      if (rand.nextInt(24) == 0) {
         worldIn.playSound(
            pos.getX() + 0.5F,
            pos.getY() + 0.5F,
            pos.getZ() + 0.5F,
            SoundEvents.BLOCK_FIRE_AMBIENT,
            SoundCategory.BLOCKS,
            1.0F + rand.nextFloat(),
            rand.nextFloat() * 0.7F + 0.3F,
            false
         );
      }

      if (!worldIn.getBlockState(pos.down()).isSideSolid(worldIn, pos.down(), EnumFacing.UP)
         && !Blocks.FIRE.canCatchFire(worldIn, pos.down(), EnumFacing.UP)) {
         if (Blocks.FIRE.canCatchFire(worldIn, pos.west(), EnumFacing.EAST)) {
            for (int j = 0; j < 2; j++) {
               double d3 = pos.getX() + rand.nextDouble() * 0.1F;
               double d8 = pos.getY() + rand.nextDouble();
               double d13 = pos.getZ() + rand.nextDouble();
               worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d3, d8, d13, 0.0, 0.0, 0.0, new int[0]);
            }
         }

         if (Blocks.FIRE.canCatchFire(worldIn, pos.east(), EnumFacing.WEST)) {
            for (int k = 0; k < 2; k++) {
               double d4 = pos.getX() + 1 - rand.nextDouble() * 0.1F;
               double d9 = pos.getY() + rand.nextDouble();
               double d14 = pos.getZ() + rand.nextDouble();
               worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d4, d9, d14, 0.0, 0.0, 0.0, new int[0]);
            }
         }

         if (Blocks.FIRE.canCatchFire(worldIn, pos.north(), EnumFacing.SOUTH)) {
            for (int l = 0; l < 2; l++) {
               double d5 = pos.getX() + rand.nextDouble();
               double d10 = pos.getY() + rand.nextDouble();
               double d15 = pos.getZ() + rand.nextDouble() * 0.1F;
               worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d5, d10, d15, 0.0, 0.0, 0.0, new int[0]);
            }
         }

         if (Blocks.FIRE.canCatchFire(worldIn, pos.south(), EnumFacing.NORTH)) {
            for (int i1 = 0; i1 < 2; i1++) {
               double d6 = pos.getX() + rand.nextDouble();
               double d11 = pos.getY() + rand.nextDouble();
               double d16 = pos.getZ() + 1 - rand.nextDouble() * 0.1F;
               worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d6, d11, d16, 0.0, 0.0, 0.0, new int[0]);
            }
         }

         if (Blocks.FIRE.canCatchFire(worldIn, pos.up(), EnumFacing.DOWN)) {
            for (int j1 = 0; j1 < 2; j1++) {
               double d7 = pos.getX() + rand.nextDouble();
               double d12 = pos.getY() + 1 - rand.nextDouble() * 0.1F;
               double d17 = pos.getZ() + rand.nextDouble();
               worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d7, d12, d17, 0.0, 0.0, 0.0, new int[0]);
            }
         }
      } else {
         for (int i = 0; i < 3; i++) {
            double d0 = pos.getX() + rand.nextDouble();
            double d1 = pos.getY() + rand.nextDouble() * 0.5 + 0.5;
            double d2 = pos.getZ() + rand.nextDouble();
            worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d0, d1, d2, 0.0, 0.0, 0.0, new int[0]);
         }
      }
   }

   public IBlockState getStateFromMeta(int meta) {
      return this.getDefaultState().withProperty(AGE, meta);
   }

   @SideOnly(Side.CLIENT)
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.CUTOUT;
   }

   public int getMetaFromState(IBlockState state) {
      return (Integer)state.getValue(AGE);
   }

   protected BlockStateContainer createBlockState() {
      return new BlockStateContainer(this, new IProperty[]{AGE, NORTH, EAST, SOUTH, WEST, UPPER});
   }

   public boolean canCatchFire(IBlockAccess world, BlockPos pos, EnumFacing face) {
      return this.getFlammability(world.getBlockState(pos).getBlock()) > 0;
   }

   public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
      return BlockFaceShape.UNDEFINED;
   }
}
