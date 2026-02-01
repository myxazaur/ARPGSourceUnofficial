package com.vivern.arpg.elements;

import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.NBTHelper;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemShell extends Item {
   public ItemShell() {
      this.setRegistryName("seashell");
      this.setCreativeTab(CreativeTabs.MATERIALS);
      this.setTranslationKey("seashell");
      this.setMaxStackSize(64);
      this.addPropertyOverride(new ResourceLocation("rtype"), new IItemPropertyGetter() {
         @SideOnly(Side.CLIENT)
         public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
            return NBTHelper.GetNBTint(stack, "type");
         }
      });
   }

   public static ItemStack getRandomSeashell(Random rand) {
      ItemStack stack = new ItemStack(ItemsRegister.SEASHELL);
      int type = rand.nextInt(5);

      while (!stack.hasTagCompound() || !stack.getTagCompound().hasKey("type")) {
         NBTHelper.GiveNBTint(stack, type, "type");
         NBTHelper.SetNBTint(stack, type, "type");
      }

      return stack;
   }

   public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
      if (this.isInCreativeTab(tab)) {
         for (int i = 0; i <= 4; i++) {
            ItemStack stack = new ItemStack(this);

            while (!stack.hasTagCompound() || !stack.getTagCompound().hasKey("type")) {
               NBTHelper.GiveNBTint(stack, i, "type");
               NBTHelper.SetNBTint(stack, i, "type");
            }

            items.add(stack);
         }
      }
   }
}
