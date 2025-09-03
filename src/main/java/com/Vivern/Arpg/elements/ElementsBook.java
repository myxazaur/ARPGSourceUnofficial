//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.container.GUIBookOfElements;
import com.Vivern.Arpg.main.ColorConverters;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.tileentity.TileBookcase;
import com.Vivern.Arpg.tileentity.TileSplitter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;

public class ElementsBook extends Item implements IItemColor {
   public ElementsBook() {
      this.setRegistryName("book_of_elements");
      this.setCreativeTab(CreativeTabs.TOOLS);
      this.setTranslationKey("book_of_elements");
      this.setMaxStackSize(1);
   }

   public static int getMaxPagesCount() {
      return 8;
   }

   public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
      ItemStack itemstack = playerIn.getHeldItem(handIn);
      if (!playerIn.getCooldownTracker().hasCooldown(ItemsRegister.BOOKOFELEMENTS)) {
         RayTraceResult raytraceresult = this.rayTrace(worldIn, playerIn, false);
         if (raytraceresult != null && raytraceresult.typeOfHit == Type.BLOCK) {
            BlockPos blockpos = raytraceresult.getBlockPos();
            TileEntity tentity = worldIn.getTileEntity(blockpos);
            if (tentity != null) {
               if (tentity instanceof TileSplitter) {
                  TileSplitter splitter = (TileSplitter)tentity;
                  if (!worldIn.isRemote && splitter.lastDissolvedItem != null) {
                     NBTTagList tagList = NBTHelper.GetNbtTagList(itemstack, "pages", 10);
                     if (tagList.tagCount() >= getMaxPagesCount()) {
                        playerIn.getCooldownTracker().setCooldown(ItemsRegister.BOOKOFELEMENTS, 3);
                        return new ActionResult(EnumActionResult.FAIL, itemstack);
                     }

                     NBTTagCompound itemInBook = new NBTTagCompound();
                     itemInBook.setString("item", splitter.lastDissolvedItem.getRegistryName().toString());
                     itemInBook.setInteger("metadata", splitter.lastDissolvedMetadata);
                     tagList.appendTag(itemInBook);
                     playerIn.getCooldownTracker().setCooldown(ItemsRegister.BOOKOFELEMENTS, 3);
                     return new ActionResult(EnumActionResult.SUCCESS, itemstack);
                  }

                  playerIn.getCooldownTracker().setCooldown(ItemsRegister.BOOKOFELEMENTS, 3);
                  return new ActionResult(EnumActionResult.PASS, itemstack);
               }

               if (tentity instanceof TileBookcase) {
                  TileBookcase bookcase = (TileBookcase)tentity;
                  if (bookcase.addBook(itemstack)) {
                     itemstack.shrink(1);
                     playerIn.getCooldownTracker().setCooldown(ItemsRegister.BOOKOFELEMENTS, 3);
                     return new ActionResult(EnumActionResult.SUCCESS, itemstack);
                  }

                  playerIn.getCooldownTracker().setCooldown(ItemsRegister.BOOKOFELEMENTS, 3);
                  return new ActionResult(EnumActionResult.PASS, itemstack);
               }
            }
         }

         this.openGui(playerIn, itemstack);
         playerIn.addStat(StatList.getObjectUseStats(this));
         playerIn.getCooldownTracker().setCooldown(ItemsRegister.BOOKOFELEMENTS, 3);
         return new ActionResult(EnumActionResult.SUCCESS, itemstack);
      } else {
         return new ActionResult(EnumActionResult.FAIL, itemstack);
      }
   }

   public void openGui(EntityPlayer player, ItemStack book) {
      if (player instanceof EntityPlayerSP) {
         Minecraft.getMinecraft().displayGuiScreen(new GUIBookOfElements(book));
      }
   }

   public int colorMultiplier(ItemStack stack, int tintIndex) {
      if (tintIndex == 0) {
         return 16777215;
      } else {
         int gem = NBTHelper.GetNBTint(stack, "gem");
         float red = 0.0F;
         float green = 0.0F;
         float blue = 0.0F;
         switch (gem) {
            case 0:
               red = 0.54F;
               green = 0.95F;
               blue = 0.88F;
               break;
            case 1:
               red = 0.9F;
               green = 0.05F;
               blue = 0.1F;
               break;
            case 2:
               red = 0.05F;
               green = 0.05F;
               blue = 0.9F;
               break;
            case 3:
               red = 0.05F;
               green = 0.9F;
               blue = 0.2F;
               break;
            case 4:
               red = 0.9F;
               green = 0.85F;
               blue = 0.3F;
               break;
            case 5:
               red = 0.4F;
               green = 0.08F;
               blue = 0.85F;
               break;
            case 6:
               red = 0.9F;
               green = 0.5F;
               blue = 0.65F;
               break;
            case 7:
               red = 0.9F;
               green = 0.91F;
               blue = 0.85F;
         }

         return ColorConverters.RGBtoDecimal(red, green, blue);
      }
   }

   public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
      if (this.isInCreativeTab(tab)) {
         for (int i = 0; i <= 7; i++) {
            ItemStack itemStack = new ItemStack(this);
            NBTHelper.GiveNBTint(itemStack, i, "gem");
            NBTHelper.SetNBTint(itemStack, i, "gem");
            items.add(itemStack);
         }
      }
   }
}
