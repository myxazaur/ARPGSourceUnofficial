package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.entity.EntityGeomanticCrystal;
import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.OreDicHelper;
import com.Vivern.Arpg.main.Sounds;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemGeomanticCrystal extends Item implements IItemColor {
   public ItemGeomanticCrystal() {
      this.setRegistryName("geomantic_crystal");
      this.setCreativeTab(CreativeTabs.MATERIALS);
      this.setTranslationKey("geomantic_crystal");
      this.setMaxDamage(100);
      this.addPropertyOverride(new ResourceLocation("type"), new IItemPropertyGetter() {
         @SideOnly(Side.CLIENT)
         public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
            float size = NBTHelper.GetNBTfloat(stack, "size");
            return size < 40.0F ? (size < 20.0F ? -2 : -1) : NBTHelper.GetNBTint(stack, "type");
         }
      });
   }

   public int colorMultiplier(ItemStack stack, int tintIndex) {
      return tintIndex == 0 ? NBTHelper.GetNBTint(stack, "color") : NBTHelper.GetNBTint(stack, "colorover");
   }

   public boolean onEntityItemUpdate(EntityItem entityItem) {
      if (!entityItem.world.isRemote
         && entityItem.ticksExisted > 40
         && entityItem.world.getBlockState(entityItem.getPosition()).getBlock() == BlocksRegister.FLUIDHYDROTHERMAL) {
         ItemStack stack = entityItem.getItem();
         NBTHelper.GiveNBTfloat(stack, 0.0F, OreDicHelper.DUSTALUMINIUM);
         NBTHelper.GiveNBTfloat(stack, 0.0F, OreDicHelper.DUSTCOPPER);
         NBTHelper.GiveNBTfloat(stack, 0.0F, OreDicHelper.DUSTGOLD);
         NBTHelper.GiveNBTfloat(stack, 0.0F, OreDicHelper.DUSTIRON);
         NBTHelper.GiveNBTfloat(stack, 0.0F, OreDicHelper.DUSTLEAD);
         NBTHelper.GiveNBTfloat(stack, 0.0F, OreDicHelper.DUSTQUARTZ);
         NBTHelper.GiveNBTfloat(stack, 0.0F, OreDicHelper.DUSTSILVER);
         NBTHelper.GiveNBTfloat(stack, 0.0F, OreDicHelper.DUSTSULFUR);
         NBTHelper.GiveNBTfloat(stack, 0.0F, OreDicHelper.DUSTTIN);
         NBTHelper.GiveNBTfloat(stack, 0.0F, OreDicHelper.DUSTTITANIUM);
         NBTHelper.GiveNBTfloat(stack, 0.0F, OreDicHelper.DUSTCOAL);
         NBTHelper.GiveNBTfloat(stack, 0.0F, OreDicHelper.DUSTCHROMIUM);
         NBTHelper.GiveNBTfloat(stack, 0.0F, OreDicHelper.DUSTBERYLLIUM);
         NBTHelper.GiveNBTfloat(stack, 0.0F, OreDicHelper.DUSTTITANIUM);
         NBTHelper.GiveNBTfloat(stack, 0.0F, OreDicHelper.DUSTMANGANESE);
         NBTHelper.GiveNBTfloat(stack, 0.0F, OreDicHelper.DUSTPLATINUM);
         NBTHelper.GiveNBTfloat(stack, 0.0F, OreDicHelper.DUSTNICKEL);
         NBTHelper.GiveNBTfloat(stack, 0.0F, "magic");
         NBTHelper.GiveNBTfloat(stack, 0.0F, "size");
         NBTHelper.GiveNBTint(stack, 0, "color");
         NBTHelper.GiveNBTint(stack, 0, "colorover");
         NBTHelper.GiveNBTint(stack, 0, "type");
         NBTHelper.GiveNBTfloat(stack, 1.0F, "durability");
         EntityGeomanticCrystal crystal = new EntityGeomanticCrystal(entityItem.world, entityItem);
         entityItem.world.spawnEntity(crystal);
         entityItem.world
            .playSound(
               entityItem.posX,
               entityItem.posY,
               entityItem.posZ,
               Sounds.item_misc_e,
               SoundCategory.HOSTILE,
               0.9F,
               0.9F + itemRand.nextFloat() / 5.0F,
               false
            );
         entityItem.setDead();
      }

      return false;
   }
}
