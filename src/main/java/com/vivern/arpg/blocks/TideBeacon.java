package com.vivern.arpg.blocks;

import com.vivern.arpg.dimensions.aquatica.DimensionAquatica;
import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.Team;
import com.vivern.arpg.renders.GUNParticle;
import com.vivern.arpg.tileentity.TileNexusBeacon;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TideBeacon extends Block implements IBlockHardBreak {
   protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.5, 1.0);
   public static ResourceLocation res = new ResourceLocation("arpg:textures/mana_flow.png");
   public static final PropertyInteger LEVEL = PropertyInteger.create("level", 0, 15);
   public static final PropertyBool WET = PropertyBool.create("wet");

   public TideBeacon() {
      super(Material.PLANTS);
      this.setRegistryName("tide_beacon");
      this.setTranslationKey("tide_beacon");
      this.blockHardness = 25.0F;
      this.blockResistance = 25.0F;
      this.setCreativeTab(CreativeTabs.DECORATIONS);
      this.setSoundType(SoundTypeCrunchy.CRUNCHY);
   }

   public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
      return false;
   }

   public Material getMaterial(IBlockState state) {
      return state.getValue(WET) ? Material.WATER : Material.ROCK;
   }

   public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
      if (!this.isInWater(world, pos)) {
         world.setBlockState(pos, state.withProperty(WET, false));
      } else {
         world.setBlockState(pos, state.withProperty(WET, true));
      }

      super.neighborChanged(state, world, pos, blockIn, fromPos);
   }

   public boolean isInWater(World worldIn, BlockPos pos) {
      IBlockState state1 = worldIn.getBlockState(pos.up());
      if (state1.getMaterial() == Material.WATER || state1.isOpaqueCube()) {
         IBlockState state2 = worldIn.getBlockState(pos.east());
         if (state2.getMaterial() == Material.WATER || state2.isOpaqueCube()) {
            IBlockState state3 = worldIn.getBlockState(pos.south());
            if (state3.getMaterial() == Material.WATER || state3.isOpaqueCube()) {
               IBlockState state4 = worldIn.getBlockState(pos.west());
               if (state4.getMaterial() == Material.WATER || state4.isOpaqueCube()) {
                  IBlockState state5 = worldIn.getBlockState(pos.north());
                  if (state5.getMaterial() == Material.WATER || state5.isOpaqueCube()) {
                     IBlockState state6 = worldIn.getBlockState(pos.down());
                     if (state6.getMaterial() == Material.WATER || state6.isOpaqueCube()) {
                        return true;
                     }
                  }
               }
            }
         }
      }

      return false;
   }

   public Class<TileNexusBeacon> getTileEntityClass() {
      return TileNexusBeacon.class;
   }

   public TileNexusBeacon getTileEntity(IBlockAccess world, BlockPos position) {
      return (TileNexusBeacon)world.getTileEntity(position);
   }

   public boolean hasTileEntity(IBlockState blockState) {
      return true;
   }

   @Nullable
   public TileNexusBeacon createTileEntity(World world, IBlockState blockState) {
      return new TileNexusBeacon();
   }

   public boolean isAroundWater(World world, BlockPos pos) {
      int count = 0;

      for (EnumFacing facing : EnumFacing.VALUES) {
         BlockPos poss = pos.offset(facing);
         IBlockState state2 = world.getBlockState(poss);
         if (state2.getBlock() == Blocks.WATER && (Integer)state2.getValue(BlockStaticLiquid.LEVEL) == 0) {
            if (++count >= 2) {
               return true;
            }
         }
      }

      return false;
   }

   public void breakBlock(World world, BlockPos pos, IBlockState state) {
      TileEntity tile = world.getTileEntity(pos);
      if (tile instanceof TileNexusBeacon) {
         TileNexusBeacon nexus = (TileNexusBeacon)tile;
         if (nexus.invasionStarted) {
            nexus.onInvasionEnd(false);
         }
      }

      if ((Boolean)state.getValue(WET) && this.isAroundWater(world, pos)) {
         world.setBlockState(pos, Blocks.WATER.getDefaultState());
      }

      super.breakBlock(world, pos, state);
   }

   protected boolean canSilkHarvest() {
      return false;
   }

   public int quantityDropped(Random random) {
      return 0;
   }

   protected BlockStateContainer createBlockState() {
      return new BlockStateContainer(this, new IProperty[]{LEVEL, WET});
   }

   public IBlockState getStateFromMeta(int meta) {
      boolean wett = meta > 0;
      return this.getDefaultState().withProperty(LEVEL, 0).withProperty(WET, wett);
   }

   public int getMetaFromState(IBlockState state) {
      return state.getValue(WET) ? 1 : 0;
   }

   public boolean onBlockActivated(
      World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ
   ) {
      player.swingArm(hand);
      if (!world.isRemote && hand == EnumHand.MAIN_HAND) {
         TileEntity tile = world.getTileEntity(pos);
         if (tile instanceof TileNexusBeacon) {
            TileNexusBeacon nexus = (TileNexusBeacon)tile;
            if (!nexus.dried && !nexus.invasionStarted) {
               Item item = player.getHeldItemMainhand().getItem();
               boolean success = false;
               if (item == ItemsRegister.TIDEACTIVATOR1) {
                  nexus.ACTIVATOR = 1;
                  success = true;
               } else if (item == ItemsRegister.TIDEACTIVATOR2) {
                  nexus.ACTIVATOR = 2;
                  success = true;
               } else if (item == ItemsRegister.TIDEACTIVATOR3) {
                  nexus.ACTIVATOR = 3;
                  success = true;
               } else if (item == ItemsRegister.TIDEACTIVATOR4) {
                  nexus.ACTIVATOR = 4;
                  success = true;
               } else if (item == ItemsRegister.TIDEACTIVATOR5) {
                  if (player instanceof EntityPlayerMP) {
                     player.sendMessage(new TextComponentString("This activator is full charged"));
                  }
               } else if (player instanceof EntityPlayerMP) {
                  player.sendMessage(new TextComponentString("You need an tide activator to begin"));
               }

               if (success) {
                  player.getHeldItemMainhand().shrink(1);
                  world.playSound(null, pos, Sounds.chlorine_belcher, SoundCategory.BLOCKS, 1.3F, 0.9F + world.rand.nextFloat() / 5.0F);
                  nexus.startInvasion(Team.getTeamFor(player));
               }
            }
         }
      }

      return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
   }

   public static void trySendPacketUpdate(World world, BlockPos pos, TileNexusBeacon tile, int range) {
      for (EntityPlayerMP playerIn : world.getEntitiesWithinAABB(
         EntityPlayerMP.class,
         new AxisAlignedBB(
            pos.getX() + range,
            pos.getY() + range,
            pos.getZ() + range,
            pos.getX() - range,
            pos.getY() - range,
            pos.getZ() - range
         )
      )) {
         SPacketUpdateTileEntity spacketupdatetileentity = tile.getUpdatePacket();
         if (spacketupdatetileentity != null) {
            playerIn.connection.sendPacket(spacketupdatetileentity);
         }
      }
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   public boolean isFullCube(IBlockState state) {
      return false;
   }

   @SideOnly(Side.CLIENT)
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.CUTOUT;
   }

   public EnumBlockRenderType getRenderType(IBlockState state) {
      return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
   }

   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      return AABB;
   }

   public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      return AABB;
   }

   @Override
   public float getBlockBreakingSpeed(World world, String tool, int toolLevel, IBlockState state, BlockPos pos, float originalSpeed) {
      return BlocksRegister.HR_SANKTUARYBRICKS.getBlockBreakingSpeed(world, tool, toolLevel, state, pos, originalSpeed);
   }

   public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
      return this.getDefaultState().withProperty(LEVEL, 0).withProperty(WET, this.isInWater(worldIn, pos));
   }

   @SideOnly(Side.CLIENT)
   public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
      if (rand.nextFloat() < 0.19F) {
         double d0 = pos.getX() + 0.5;
         double d1 = pos.getY() + 2.0;
         double d2 = pos.getZ() + 0.5;
         int livetime = 80;
         float scale = 0.5F + rand.nextFloat() / 5.0F;
         float scaleTickAdding = scale / livetime;
         GUNParticle spelll = new GUNParticle(
            res,
            0.15F,
            0.0F,
            livetime,
            210,
            worldIn,
            d0,
            d1,
            d2,
            0.0F,
            0.0F,
            0.0F,
            0.45F + rand.nextFloat() * 0.1F,
            0.9F + rand.nextFloat() * 0.1F,
            0.9F + rand.nextFloat() * 0.1F,
            true,
            0
         );
         spelll.alpha = 1.0F;
         spelll.alphaTickAdding = -0.0125F;
         spelll.scaleTickAdding = scaleTickAdding;
         spelll.alphaGlowing = true;
         spelll.isPushedByLiquids = false;
         worldIn.spawnEntity(spelll);
      }
   }

   public Vec3d getFogColor(World world, BlockPos pos, IBlockState state, Entity entity, Vec3d originalColor, float partialTicks) {
      return world.provider.getDimension() == 103
         ? DimensionAquatica.getBlockFogColor(world, pos, state, entity, originalColor, partialTicks)
         : super.getFogColor(world, pos, state, entity, originalColor, partialTicks);
   }
}
