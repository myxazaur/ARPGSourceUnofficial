package com.vivern.arpg.dimensions.toxicomania;

import com.vivern.arpg.dimensions.generationutils.BlockAtPos;
import com.vivern.arpg.dimensions.generationutils.TeleporterUniversal;
import com.vivern.arpg.main.DimensionsRegister;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.tileentity.TilePortal;
import java.util.ArrayList;
import java.util.Iterator;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class ARPGTeleporter {
   public ArrayList<BlockPos> membraneConfiguration;
   public ArrayList<BlockAtPos> frameConfiguration;
   public ArrayList<BlockPos> groundCheck;
   public ArrayList<BlockPos> spawnPoints;
   public IBlockState portalBlock;
   public IBlockState prototypePortalBlock = null;
   public int dimensionID;
   public int portalHeight;
   public boolean setAxisProperty;
   public boolean canPlaceUnderground = false;
   public int undergroundAttempts = 8;

   public ARPGTeleporter(
      ArrayList<BlockPos> membraneConfiguration,
      ArrayList<BlockAtPos> frameConfiguration,
      ArrayList<BlockPos> groundCheck,
      ArrayList<BlockPos> spawnPoints,
      int dimensionID,
      IBlockState portalBlock,
      int portalHeight,
      boolean setAxisProperty
   ) {
      this.membraneConfiguration = membraneConfiguration;
      this.frameConfiguration = frameConfiguration;
      this.portalBlock = portalBlock;
      this.dimensionID = dimensionID;
      this.portalHeight = portalHeight;
      this.groundCheck = groundCheck;
      this.setAxisProperty = setAxisProperty;
      this.spawnPoints = spawnPoints;
   }

   public boolean isActivePortalAtPos(World world, BlockPos pos, boolean Zaxis) {
      if (Zaxis) {
         for (BlockAtPos blockp : this.frameConfiguration) {
            BlockPos poss = pos.add(blockp.getZ(), blockp.getY(), blockp.getX());
            IBlockState stateon = world.getBlockState(poss);
            if (stateon.getBlock() != blockp.state.getBlock()
               || stateon.getBlock().getMetaFromState(stateon) != blockp.state.getBlock().getMetaFromState(blockp.state)) {
               return false;
            }
         }

         Iterator var10 = this.membraneConfiguration.iterator();

         IBlockState stateon;
         do {
            if (!var10.hasNext()) {
               return true;
            }

            BlockPos blockpx = (BlockPos)var10.next();
            BlockPos poss = pos.add(blockpx.getZ(), blockpx.getY(), blockpx.getX());
            stateon = world.getBlockState(poss);
         } while (stateon.getBlock() == this.portalBlock.getBlock());

         return false;
      } else {
         for (BlockAtPos blockpx : this.frameConfiguration) {
            BlockPos poss = pos.add(blockpx);
            IBlockState stateon = world.getBlockState(poss);
            if (stateon.getBlock() != blockpx.state.getBlock()
               || stateon.getBlock().getMetaFromState(stateon) != blockpx.state.getBlock().getMetaFromState(blockpx.state)) {
               return false;
            }
         }

         for (BlockPos blockpxx : this.membraneConfiguration) {
            BlockPos poss = pos.add(blockpxx);
            IBlockState stateon = world.getBlockState(poss);
            if (stateon.getBlock() != this.portalBlock.getBlock()) {
               return false;
            }
         }

         return true;
      }
   }

   public boolean isReadyToActivate(World world, BlockPos pos, boolean Zaxis) {
      if (Zaxis) {
         for (BlockAtPos blockp : this.frameConfiguration) {
            BlockPos poss = pos.add(blockp.getZ(), blockp.getY(), blockp.getX());
            IBlockState stateon = world.getBlockState(poss);
            if (stateon.getBlock() != blockp.state.getBlock()
               || stateon.getBlock().getMetaFromState(stateon) != blockp.state.getBlock().getMetaFromState(blockp.state)) {
               return false;
            }
         }

         if (this.prototypePortalBlock != null) {
            Iterator var11 = this.membraneConfiguration.iterator();

            IBlockState stateon;
            do {
               if (!var11.hasNext()) {
                  return true;
               }

               BlockPos blockpx = (BlockPos)var11.next();
               BlockPos poss = pos.add(blockpx.getZ(), blockpx.getY(), blockpx.getX());
               stateon = world.getBlockState(poss);
            } while (stateon.getBlock() == this.prototypePortalBlock.getBlock());

            return false;
         } else {
            Iterator var12 = this.membraneConfiguration.iterator();

            BlockPos poss;
            IBlockState stateon;
            do {
               if (!var12.hasNext()) {
                  return true;
               }

               BlockPos blockpx = (BlockPos)var12.next();
               poss = pos.add(blockpx.getZ(), blockpx.getY(), blockpx.getX());
               stateon = world.getBlockState(poss);
            } while (stateon.getBlock().isReplaceable(world, poss));

            return false;
         }
      } else {
         for (BlockAtPos blockpx : this.frameConfiguration) {
            BlockPos possx = pos.add(blockpx);
            IBlockState stateon = world.getBlockState(possx);
            if (stateon.getBlock() != blockpx.state.getBlock()
               || stateon.getBlock().getMetaFromState(stateon) != blockpx.state.getBlock().getMetaFromState(blockpx.state)) {
               return false;
            }
         }

         if (this.prototypePortalBlock != null) {
            for (BlockPos blockpxx : this.membraneConfiguration) {
               BlockPos possx = pos.add(blockpxx);
               IBlockState stateon = world.getBlockState(possx);
               if (stateon.getBlock() != this.prototypePortalBlock.getBlock()) {
                  return false;
               }
            }
         } else {
            for (BlockPos blockpxxx : this.membraneConfiguration) {
               BlockPos possx = pos.add(blockpxxx);
               IBlockState stateon = world.getBlockState(possx);
               if (!stateon.getBlock().isReplaceable(world, possx)) {
                  return false;
               }
            }
         }
      }

      return true;
   }

   public boolean tryActivate(World world, BlockPos pos, @Nullable IBlockState dontCheck, @Nullable Axis axis) {
      boolean canbreak = DimensionsRegister.canPortalsBreak;
      DimensionsRegister.canPortalsBreak = false;
      if ((axis == null || axis == Axis.Z) && this.isReadyToActivate(world, pos, true)) {
         for (BlockPos mpos : this.membraneConfiguration) {
            BlockPos fposs = pos.add(mpos.getZ(), mpos.getY(), mpos.getX());
            world.setBlockState(fposs, this.portalBlock);
            TileEntity tile = world.getTileEntity(fposs);
            if (tile instanceof TilePortal) {
               TilePortal portalTile = (TilePortal)tile;
               portalTile.isMainPortalBlock = mpos.getX() == 0 && mpos.getY() == 0 && mpos.getZ() == 0;
               portalTile.mainBlockPosition = pos;
               portalTile.dimensionToTeleport = world.provider.getDimension() == this.dimensionID ? 0 : this.dimensionID;
            }
         }

         DimensionsRegister.canPortalsBreak = canbreak;
         return true;
      } else if ((axis == null || axis == Axis.X) && this.isReadyToActivate(world, pos, false)) {
         for (BlockPos mposx : this.membraneConfiguration) {
            BlockPos fposs = pos.add(mposx);
            world.setBlockState(fposs, this.portalBlock);
            TileEntity tile = world.getTileEntity(fposs);
            if (tile instanceof TilePortal) {
               TilePortal portalTile = (TilePortal)tile;
               portalTile.isMainPortalBlock = mposx.getX() == 0 && mposx.getY() == 0 && mposx.getZ() == 0;
               portalTile.mainBlockPosition = pos;
               portalTile.dimensionToTeleport = world.provider.getDimension() == this.dimensionID ? 0 : this.dimensionID;
            }
         }

         DimensionsRegister.canPortalsBreak = canbreak;
         return true;
      } else {
         DimensionsRegister.canPortalsBreak = canbreak;
         return false;
      }
   }

   public boolean hasSolidGround(World world, BlockPos pos, boolean zAxis) {
      for (BlockPos blockp : this.groundCheck) {
         BlockPos poss = zAxis ? pos.add(blockp.getZ(), blockp.getY(), blockp.getX()) : pos.add(blockp);
         IBlockState stateon = world.getBlockState(poss);
         if (stateon.getMaterial().isSolid() || stateon.getMaterial().isLiquid()) {
            return true;
         }
      }

      return false;
   }

   @Nullable
   public BlockPos tryPlacePortal(World world, BlockPos blockpos, boolean zAxis, int fromDimension) {
      boolean canbreak = DimensionsRegister.canPortalsBreak;
      DimensionsRegister.canPortalsBreak = false;
      int maxy = (world.provider.getDimension() == -1 ? 127 : 255) - this.portalHeight;

      for (int y = maxy; y >= blockpos.getY(); y--) {
         BlockPos pos = new BlockPos(blockpos.getX(), y, blockpos.getZ());
         if (y == blockpos.getY() || this.hasSolidGround(world, pos, zAxis)) {
            boolean canPlacePortal = true;

            for (BlockAtPos blockp : this.frameConfiguration) {
               BlockPos poss = zAxis ? pos.add(blockp.getZ(), blockp.getY(), blockp.getX()) : pos.add(blockp);
               IBlockState stateon = world.getBlockState(poss);
               if (!stateon.getBlock().isReplaceable(world, poss)) {
                  canPlacePortal = false;
                  break;
               }
            }

            if (canPlacePortal) {
               for (BlockPos blockpx : this.membraneConfiguration) {
                  BlockPos poss = zAxis
                     ? pos.add(blockpx.getZ(), blockpx.getY(), blockpx.getX())
                     : pos.add(blockpx);
                  IBlockState stateon = world.getBlockState(poss);
                  if (!stateon.getBlock().isReplaceable(world, poss)) {
                     canPlacePortal = false;
                     break;
                  }
               }
            }

            if (canPlacePortal) {
               for (BlockAtPos blockpxx : this.frameConfiguration) {
                  BlockPos poss = zAxis
                     ? pos.add(blockpxx.getZ(), blockpxx.getY(), blockpxx.getX())
                     : pos.add(blockpxx);
                  world.setBlockState(poss, blockpxx.state);
               }

               for (BlockPos blockpxx : this.membraneConfiguration) {
                  BlockPos poss = zAxis
                     ? pos.add(blockpxx.getZ(), blockpxx.getY(), blockpxx.getX())
                     : pos.add(blockpxx);
                  world.setBlockState(poss, this.setAxisProperty && zAxis ? this.portalBlock.getBlock().getStateFromMeta(1) : this.portalBlock);
                  TileEntity tile = world.getTileEntity(poss);
                  if (tile instanceof TilePortal) {
                     TilePortal portalTile = (TilePortal)tile;
                     portalTile.isMainPortalBlock = blockpxx.getX() == 0 && blockpxx.getY() == 0 && blockpxx.getZ() == 0;
                     portalTile.mainBlockPosition = pos;
                     portalTile.linkedPortal = blockpos;
                     portalTile.dimensionToTeleport = fromDimension;
                  }
               }

               DimensionsRegister.canPortalsBreak = canbreak;
               return pos;
            }
         }
      }

      DimensionsRegister.canPortalsBreak = canbreak;
      return null;
   }

   @Nullable
   public BlockPos tryPlacePortalUnderground(World world, BlockPos blockpos, boolean zAxis, int fromDimension) {
      boolean canbreak = DimensionsRegister.canPortalsBreak;
      DimensionsRegister.canPortalsBreak = false;
      int maxy = 255 - this.portalHeight;
      double xadd = 0.0;
      double zadd = 0.0;

      for (int i = 0; i < this.undergroundAttempts; i++) {
         if (i > 0) {
            xadd = world.rand.nextGaussian() * 8.0;
            zadd = world.rand.nextGaussian() * 8.0;
         }

         for (int y = maxy; y >= 1 + this.portalHeight; y--) {
            BlockPos pos = new BlockPos(blockpos.getX() + xadd, y, blockpos.getZ() + zadd);
            if (world.getBlockState(pos).getBlock().isReplaceable(world, pos) && this.hasSolidGround(world, pos, zAxis)) {
               boolean canPlacePortal = true;

               for (BlockAtPos blockp : this.frameConfiguration) {
                  BlockPos poss = zAxis ? pos.add(blockp.getZ(), blockp.getY(), blockp.getX()) : pos.add(blockp);
                  IBlockState stateon = world.getBlockState(poss);
                  if (!stateon.getBlock().isReplaceable(world, poss)) {
                     canPlacePortal = false;
                     break;
                  }
               }

               if (canPlacePortal) {
                  for (BlockPos blockpx : this.membraneConfiguration) {
                     BlockPos poss = zAxis
                        ? pos.add(blockpx.getZ(), blockpx.getY(), blockpx.getX())
                        : pos.add(blockpx);
                     IBlockState stateon = world.getBlockState(poss);
                     if (!stateon.getBlock().isReplaceable(world, poss)) {
                        canPlacePortal = false;
                        break;
                     }
                  }
               }

               if (canPlacePortal) {
                  for (BlockAtPos blockpxx : this.frameConfiguration) {
                     BlockPos poss = zAxis
                        ? pos.add(blockpxx.getZ(), blockpxx.getY(), blockpxx.getX())
                        : pos.add(blockpxx);
                     world.setBlockState(poss, blockpxx.state);
                  }

                  for (BlockPos blockpxx : this.membraneConfiguration) {
                     BlockPos poss = zAxis
                        ? pos.add(blockpxx.getZ(), blockpxx.getY(), blockpxx.getX())
                        : pos.add(blockpxx);
                     world.setBlockState(poss, this.setAxisProperty && zAxis ? this.portalBlock.getBlock().getStateFromMeta(1) : this.portalBlock);
                     TileEntity tile = world.getTileEntity(poss);
                     if (tile instanceof TilePortal) {
                        TilePortal portalTile = (TilePortal)tile;
                        portalTile.isMainPortalBlock = blockpxx.getX() == 0 && blockpxx.getY() == 0 && blockpxx.getZ() == 0;
                        portalTile.mainBlockPosition = pos;
                        portalTile.linkedPortal = blockpos;
                        portalTile.dimensionToTeleport = fromDimension;
                     }
                  }

                  DimensionsRegister.canPortalsBreak = canbreak;
                  return pos;
               }
            }
         }
      }

      DimensionsRegister.canPortalsBreak = canbreak;
      return null;
   }

   public void teleport(EntityPlayer player, BlockPos blockPos) {
      int oldDimension = player.getEntityWorld().provider.getDimension();
      EntityPlayerMP playerMP = (EntityPlayerMP)player;
      MinecraftServer server = player.getEntityWorld().getMinecraftServer();
      TilePortal mainPortalTile = null;
      TileEntity tile = playerMP.getEntityWorld().getTileEntity(blockPos);
      if (tile instanceof TilePortal) {
         TilePortal portalTile = (TilePortal)tile;
         if (portalTile.isMainPortalBlock) {
            mainPortalTile = portalTile;
         } else if (portalTile.mainBlockPosition != null) {
            TileEntity tile2 = playerMP.getEntityWorld().getTileEntity(portalTile.mainBlockPosition);
            if (tile2 instanceof TilePortal) {
               TilePortal portalTile2 = (TilePortal)tile2;
               if (portalTile2.isMainPortalBlock) {
                  mainPortalTile = portalTile2;
               }
            }
         }
      }

      if (mainPortalTile == null) {
         System.out.println("Can't find portal main tile entity");
      } else {
         int dimensionTo = mainPortalTile.dimensionToTeleport;
         if (oldDimension != dimensionTo) {
            WorldServer worldserver = server.getWorld(dimensionTo);
            if (worldserver == null || server == null) {
               mainPortalTile.dimensionToTeleport = 0;
               System.out.println("illegal dimension id " + dimensionTo);
               return;
            }

            boolean Zaxis = false;
            if (this.isActivePortalAtPos(player.getEntityWorld(), mainPortalTile.getPos(), true)) {
               Zaxis = true;
            }

            if (mainPortalTile.linkedPortal == null) {
               if (!this.isActivePortalAtPos(worldserver, mainPortalTile.getPos(), Zaxis)) {
                  BlockPos placedPortal = this.canPlaceUnderground
                     ? this.tryPlacePortalUnderground(worldserver, mainPortalTile.getPos(), Zaxis, oldDimension)
                     : this.tryPlacePortal(worldserver, mainPortalTile.getPos(), Zaxis, oldDimension);
                  if (placedPortal == null) {
                     System.out.println("Can't place portal on other side");
                     return;
                  }

                  mainPortalTile.linkedPortal = placedPortal;
               } else {
                  mainPortalTile.linkedPortal = mainPortalTile.getPos();
               }
            } else if (!this.isActivePortalAtPos(worldserver, mainPortalTile.linkedPortal, Zaxis)) {
               System.out.println("Can't find linked portal at " + mainPortalTile.linkedPortal);
               mainPortalTile.linkedPortal = null;
               return;
            }

            double relativePlayerX = player.posX - mainPortalTile.getPos().getX();
            double relativePlayerY = player.posY - mainPortalTile.getPos().getY();
            double relativePlayerZ = player.posZ - mainPortalTile.getPos().getZ();
            double playerX = relativePlayerX + mainPortalTile.linkedPortal.getX();
            double playerY = relativePlayerY + mainPortalTile.linkedPortal.getY();
            double playerZ = relativePlayerZ + mainPortalTile.linkedPortal.getZ();
            BlockPos bedLocation = playerMP.getBedLocation(dimensionTo);
            worldserver.getMinecraftServer()
               .getPlayerList()
               .transferPlayerToDimension(playerMP, dimensionTo, new TeleporterUniversal(worldserver, playerX, playerY, playerZ));
            if (DimensionsRegister.isAbstractRPGDimension(dimensionTo)) {
               boolean isSet = false;
               if (bedLocation == null) {
                  int id = player.getRNG().nextInt(this.spawnPoints.size());

                  for (int i = 0; i < this.spawnPoints.size(); i++) {
                     BlockPos relativePos = this.spawnPoints.get(id);
                     BlockPos posToSpawn = mainPortalTile.linkedPortal.add(relativePos);
                     if (worldserver.isAirBlock(posToSpawn) && worldserver.isAirBlock(posToSpawn.up())) {
                        playerMP.setSpawnPoint(posToSpawn, true);
                        isSet = true;
                        break;
                     }

                     id = GetMOP.next(id, 1, this.spawnPoints.size());
                  }
               } else {
                  isSet = true;
               }

               if (!isSet) {
                  playerMP.sendMessage(new TextComponentString("Warning! Spawn point has not been set, something is blocking it"));
               }
            }
         }
      }
   }
}
