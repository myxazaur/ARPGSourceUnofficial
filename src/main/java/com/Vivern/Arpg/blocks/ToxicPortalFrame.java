//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.main.DimensionsRegister;
import com.Vivern.Arpg.tileentity.TilePortal;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ToxicPortalFrame extends Block {
   public ToxicPortalFrame() {
      super(Material.IRON);
      this.setRegistryName("toxicomania_portal_frame");
      this.setTranslationKey("toxicomania_portal_frame");
      this.blockHardness = 5.0F;
      this.blockResistance = 60.0F;
      this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
      this.setSoundType(SoundType.ANVIL);
   }

   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
      boolean flag = worldIn.isBlockPowered(pos) || worldIn.isBlockPowered(pos.up());
      if (flag) {
         boolean canbreak = DimensionsRegister.canPortalsBreak;
         DimensionsRegister.canPortalsBreak = false;

         for (int x = -2; x < 2; x++) {
            for (int y = -2; y < 2; y++) {
               for (int z = -2; z < 2; z++) {
                  BlockPos poss = pos.add(x, y, z);
                  if (DimensionsRegister.teleporterTOXICOMANIA.isReadyToActivate(worldIn, poss, false)) {
                     for (BlockPos mpos : DimensionsRegister.teleporterTOXICOMANIA.membraneConfiguration) {
                        BlockPos fposs = poss.add(mpos);
                        worldIn.destroyBlock(fposs, false);
                        worldIn.setBlockState(fposs, DimensionsRegister.teleporterTOXICOMANIA.portalBlock);
                        TileEntity tile = worldIn.getTileEntity(fposs);
                        if (tile instanceof TilePortal) {
                           TilePortal portalTile = (TilePortal)tile;
                           portalTile.isMainPortalBlock = mpos.getX() == 0 && mpos.getY() == 0 && mpos.getZ() == 0;
                           portalTile.mainBlockPosition = poss;
                           portalTile.dimensionToTeleport = worldIn.provider.getDimension() == DimensionsRegister.teleporterTOXICOMANIA.dimensionID
                              ? 0
                              : DimensionsRegister.teleporterTOXICOMANIA.dimensionID;
                        }
                     }

                     DimensionsRegister.canPortalsBreak = canbreak;
                     return;
                  }

                  if (DimensionsRegister.teleporterTOXICOMANIA.isReadyToActivate(worldIn, poss, true)) {
                     for (BlockPos mposx : DimensionsRegister.teleporterTOXICOMANIA.membraneConfiguration) {
                        BlockPos fposs = poss.add(mposx.getZ(), mposx.getY(), mposx.getX());
                        worldIn.destroyBlock(fposs, false);
                        worldIn.setBlockState(fposs, DimensionsRegister.teleporterTOXICOMANIA.portalBlock.getBlock().getStateFromMeta(1));
                        TileEntity tile = worldIn.getTileEntity(fposs);
                        if (tile instanceof TilePortal) {
                           TilePortal portalTile = (TilePortal)tile;
                           portalTile.isMainPortalBlock = mposx.getX() == 0 && mposx.getY() == 0 && mposx.getZ() == 0;
                           portalTile.mainBlockPosition = poss;
                           portalTile.dimensionToTeleport = worldIn.provider.getDimension() == DimensionsRegister.teleporterTOXICOMANIA.dimensionID
                              ? 0
                              : DimensionsRegister.teleporterTOXICOMANIA.dimensionID;
                        }
                     }

                     DimensionsRegister.canPortalsBreak = canbreak;
                     return;
                  }
               }
            }
         }

         DimensionsRegister.canPortalsBreak = canbreak;
      }
   }

   public boolean isFullCube(IBlockState state) {
      return true;
   }
}
