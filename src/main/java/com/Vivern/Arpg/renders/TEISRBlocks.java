package com.Vivern.Arpg.renders;

import com.Vivern.Arpg.elements.ItemARPGChest;
import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.ShardType;
import com.Vivern.Arpg.tileentity.TileARPGChest;
import com.Vivern.Arpg.tileentity.TileAssemblyAugment;
import com.Vivern.Arpg.tileentity.TileAssemblyTable;
import com.Vivern.Arpg.tileentity.TileBioCell;
import com.Vivern.Arpg.tileentity.TileCrystalSphere;
import com.Vivern.Arpg.tileentity.TileElectricSieve;
import com.Vivern.Arpg.tileentity.TileEtheriteInvocator;
import com.Vivern.Arpg.tileentity.TileGlossary;
import com.Vivern.Arpg.tileentity.TileIndustrialMixer;
import com.Vivern.Arpg.tileentity.TileItemCharger;
import com.Vivern.Arpg.tileentity.TileMagicGenerator;
import com.Vivern.Arpg.tileentity.TileManaBottle;
import com.Vivern.Arpg.tileentity.TileManaPump;
import com.Vivern.Arpg.tileentity.TileNexusBeacon;
import com.Vivern.Arpg.tileentity.TileNexusNiveolite;
import com.Vivern.Arpg.tileentity.TilePresentBox;
import com.Vivern.Arpg.tileentity.TileRunicMirror;
import com.Vivern.Arpg.tileentity.TileShimmeringBeastbloom;
import com.Vivern.Arpg.tileentity.TileSieve;
import com.Vivern.Arpg.tileentity.TileSoulCatcher;
import com.Vivern.Arpg.tileentity.TileTeamBanner;
import com.Vivern.Arpg.tileentity.TileTritonHearth;
import com.Vivern.Arpg.tileentity.TileVoidCrystal;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TEISRBlocks extends TileEntityItemStackRenderer {
   public static TileEntityItemStackRenderer instance = new TEISRBlocks();

   public void renderByItem(ItemStack itemStackIn) {
      this.renderByItem(itemStackIn, 1.0F);
   }

   public void renderByItem(ItemStack itemstack, float partialTicks) {
      Item item = itemstack.getItem();
      if (item instanceof ItemBlock) {
         Block block = ((ItemBlock)item).getBlock();
         GlStateManager.pushMatrix();
         if (block == BlocksRegister.ASSEMBLYTABLE) {
            TileEntitySpecialRenderer TESRrenderer = (TileEntitySpecialRenderer)TileEntityRendererDispatcher.instance
               .renderers
               .get(TileAssemblyTable.class);
            TESRrenderer.render(null, 0.0, 0.0, 0.0, partialTicks, -1, 1.0F);
         }

         if (block == BlocksRegister.AUGMENTCUT
            || block == BlocksRegister.AUGMENTPRESS
            || block == BlocksRegister.AUGMENTWELD
            || block == BlocksRegister.AUGMENTPLASMA
            || block == BlocksRegister.AUGMENTMOLECULAR) {
            int id = 0;
            if (block == BlocksRegister.AUGMENTCUT) {
               id = 1;
            }

            if (block == BlocksRegister.AUGMENTPRESS) {
               id = 2;
            }

            if (block == BlocksRegister.AUGMENTWELD) {
               id = 3;
            }

            if (block == BlocksRegister.AUGMENTPLASMA) {
               id = 4;
            }

            if (block == BlocksRegister.AUGMENTMOLECULAR) {
               id = 5;
            }

            TileEntitySpecialRenderer TESRrenderer = (TileEntitySpecialRenderer)TileEntityRendererDispatcher.instance
               .renderers
               .get(TileAssemblyAugment.class);
            TESRrenderer.render(null, 0.0, 0.0, 0.0, partialTicks, id, 1.0F);
         }

         if (block == BlocksRegister.SHIMMERINGBEASTBLOOM) {
            TileEntitySpecialRenderer TESRrenderer = (TileEntitySpecialRenderer)TileEntityRendererDispatcher.instance
               .renderers
               .get(TileShimmeringBeastbloom.class);
            TESRrenderer.render(null, 0.0, 0.0, 0.0, partialTicks, -1, 1.0F);
         }

         if (block == BlocksRegister.BIOCELL) {
            TileEntitySpecialRenderer TESRrenderer = (TileEntitySpecialRenderer)TileEntityRendererDispatcher.instance
               .renderers
               .get(TileBioCell.class);
            TESRrenderer.render(null, 0.0, 0.0, 0.0, partialTicks, -1, 1.0F);
         }

         if (block == BlocksRegister.GLOSSARY) {
            TileEntitySpecialRenderer TESRrenderer = (TileEntitySpecialRenderer)TileEntityRendererDispatcher.instance
               .renderers
               .get(TileGlossary.class);
            TESRrenderer.render(null, 0.0, 0.0, 0.0, partialTicks, -1, 1.0F);
         }

         if (block == BlocksRegister.TIDEBEACON) {
            TileEntitySpecialRenderer TESRrenderer = (TileEntitySpecialRenderer)TileEntityRendererDispatcher.instance
               .renderers
               .get(TileNexusBeacon.class);
            TESRrenderer.render(null, 0.0, 0.0, 0.0, partialTicks, -1, 1.0F);
         }

         if (block == BlocksRegister.TRITONHEARTH) {
            TileEntitySpecialRenderer TESRrenderer = (TileEntitySpecialRenderer)TileEntityRendererDispatcher.instance
               .renderers
               .get(TileTritonHearth.class);
            TESRrenderer.render(null, 0.0, 0.0, 0.0, partialTicks, -1, 1.0F);
         }

         if (block == BlocksRegister.TRITONHEARTH) {
            TileEntitySpecialRenderer TESRrenderer = (TileEntitySpecialRenderer)TileEntityRendererDispatcher.instance
               .renderers
               .get(TileTritonHearth.class);
            TESRrenderer.render(null, 0.0, 0.0, 0.0, partialTicks, -1, 1.0F);
         }

         if (block == BlocksRegister.VOIDCRYSTAL) {
            TileEntitySpecialRenderer TESRrenderer = (TileEntitySpecialRenderer)TileEntityRendererDispatcher.instance
               .renderers
               .get(TileVoidCrystal.class);
            TESRrenderer.render(null, 0.0, 0.0, 0.0, partialTicks, -1, 1.0F);
         }

         if (block == BlocksRegister.RUNICMIRROR) {
            TileEntitySpecialRenderer TESRrenderer = (TileEntitySpecialRenderer)TileEntityRendererDispatcher.instance
               .renderers
               .get(TileRunicMirror.class);
            TESRrenderer.render(null, 0.0, 0.0, 0.0, partialTicks, -1, 1.0F);
         }

         if (block == BlocksRegister.INDUSTRIALMIXER) {
            TileEntitySpecialRenderer TESRrenderer = (TileEntitySpecialRenderer)TileEntityRendererDispatcher.instance
               .renderers
               .get(TileIndustrialMixer.class);
            TESRrenderer.render(null, 0.0, 0.0, 0.0, partialTicks, -1, 1.0F);
         }

         if (block == BlocksRegister.CRYSTALSPHERE) {
            NBTTagCompound blockEntityTag = NBTHelper.GetNBTtag(itemstack, "BlockEntityTag");
            float stored = blockEntityTag != null && blockEntityTag.hasKey("stored") ? blockEntityTag.getFloat("stored") : 0.0F;
            ShardType type = blockEntityTag != null && blockEntityTag.hasKey("type")
               ? ShardType.byName(blockEntityTag.getString("type"))
               : ShardType.FIRE;
            TileEntitySpecialRenderer TESRrenderer = (TileEntitySpecialRenderer)TileEntityRendererDispatcher.instance
               .renderers
               .get(TileCrystalSphere.class);
            TESRrenderer.render(null, stored, type.id, 0.0, partialTicks, -1, 1.0F);
         }

         if (block == BlocksRegister.MANABOTTLE) {
            NBTTagCompound blockEntityTag = NBTHelper.GetNBTtag(itemstack, "BlockEntityTag");
            float max = blockEntityTag != null && blockEntityTag.hasKey("max") ? blockEntityTag.getFloat("max") : 0.0F;
            float stored = blockEntityTag != null && blockEntityTag.hasKey("manaStored") ? blockEntityTag.getFloat("manaStored") : 0.0F;
            TileEntitySpecialRenderer TESRrenderer = (TileEntitySpecialRenderer)TileEntityRendererDispatcher.instance
               .renderers
               .get(TileManaBottle.class);
            TESRrenderer.render(null, max, stored, 0.0, partialTicks, -1, 1.0F);
         }

         if (block == BlocksRegister.SOULCATCHER) {
            TileEntitySpecialRenderer TESRrenderer = (TileEntitySpecialRenderer)TileEntityRendererDispatcher.instance
               .renderers
               .get(TileSoulCatcher.class);
            TESRrenderer.render(null, 0.0, 0.0, 0.0, partialTicks, -1, 1.0F);
         }

         if (block == BlocksRegister.MANAPUMP) {
            TileEntitySpecialRenderer TESRrenderer = (TileEntitySpecialRenderer)TileEntityRendererDispatcher.instance
               .renderers
               .get(TileManaPump.class);
            TESRrenderer.render(null, 0.0, 0.0, 0.0, partialTicks, -1, 1.0F);
         }

         if (block == BlocksRegister.SIEVE) {
            TileEntitySpecialRenderer TESRrenderer = (TileEntitySpecialRenderer)TileEntityRendererDispatcher.instance.renderers.get(TileSieve.class);
            TESRrenderer.render(null, 0.0, 0.0, 0.0, partialTicks, -1, 1.0F);
         }

         if (block == BlocksRegister.ELECTRICSIEVE) {
            TileEntitySpecialRenderer TESRrenderer = (TileEntitySpecialRenderer)TileEntityRendererDispatcher.instance
               .renderers
               .get(TileElectricSieve.class);
            TESRrenderer.render(null, 0.0, 0.0, 0.0, partialTicks, -1, 1.0F);
         }

         if (block == BlocksRegister.NIVEOLITEGAMEBLOCK) {
            TileEntitySpecialRenderer TESRrenderer = (TileEntitySpecialRenderer)TileEntityRendererDispatcher.instance
               .renderers
               .get(TileNexusNiveolite.class);
            TESRrenderer.render(null, 0.0, 0.0, 0.0, partialTicks, -1, 1.0F);
         }

         if (block == BlocksRegister.PRESENTBOX) {
            TileEntitySpecialRenderer TESRrenderer = (TileEntitySpecialRenderer)TileEntityRendererDispatcher.instance
               .renderers
               .get(TilePresentBox.class);
            TESRrenderer.render(null, 0.0, 0.0, 0.0, partialTicks, -1, 1.0F);
         }

         if (block == BlocksRegister.BLOCKITEMCHARGER) {
            TileEntitySpecialRenderer TESRrenderer = (TileEntitySpecialRenderer)TileEntityRendererDispatcher.instance
               .renderers
               .get(TileItemCharger.class);
            TESRrenderer.render(null, 0.0, 0.0, 0.0, partialTicks, -1, 1.0F);
         }

         if (item instanceof ItemARPGChest) {
            ItemARPGChest chest = (ItemARPGChest)item;
            ARPGChestTESR.reservedChestType = chest.chestType;
            TileEntitySpecialRenderer TESRrenderer = (TileEntitySpecialRenderer)TileEntityRendererDispatcher.instance
               .renderers
               .get(TileARPGChest.class);
            TESRrenderer.render(null, 0.0, 0.0, 0.0, partialTicks, -1, 1.0F);
         }

         if (block == BlocksRegister.MAGICGENERATOR) {
            TileEntitySpecialRenderer TESRrenderer = (TileEntitySpecialRenderer)TileEntityRendererDispatcher.instance
               .renderers
               .get(TileMagicGenerator.class);
            TESRrenderer.render(null, 0.0, 0.0, 0.0, partialTicks, -1, 1.0F);
         }

         if (block == BlocksRegister.ETHERITEINVOCATOR) {
            TileEntitySpecialRenderer TESRrenderer = (TileEntitySpecialRenderer)TileEntityRendererDispatcher.instance
               .renderers
               .get(TileEtheriteInvocator.class);
            TESRrenderer.render(null, 0.0, 0.0, 0.0, partialTicks, -1, 1.0F);
         }

         if (block == BlocksRegister.TEAMBANNER) {
            TileEntitySpecialRenderer TESRrenderer = (TileEntitySpecialRenderer)TileEntityRendererDispatcher.instance
               .renderers
               .get(TileTeamBanner.class);
            TESRrenderer.render(null, 0.0, 0.0, 0.0, partialTicks, -1, 1.0F);
         }

         GlStateManager.popMatrix();
      }
   }
}
