package com.Vivern.Arpg.elements.animation;

import com.Vivern.Arpg.elements.AnimationCapabilityProvider;
import com.Vivern.Arpg.main.GetMOP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class FlickInteger extends AbstractFlick {
   public int slot;
   public EnumFlick name;
   public int valueMin;
   public int valueMax;
   public int tendency;
   public int value;
   public int previousValue;

   public FlickInteger(int slot, EnumFlick name, int valueMin, int valueMax, int tendency, int value) {
      this.slot = slot;
      this.name = name;
      this.valueMin = valueMin;
      this.valueMax = valueMax;
      this.tendency = tendency;
      this.value = value;
   }

   @Override
   public float get(float partialTicks) {
      float partialValue = GetMOP.partial((float)this.value, (float)this.previousValue, partialTicks);
      return GetMOP.getfromto(partialValue, (float)this.valueMin, (float)this.valueMax);
   }

   @Override
   public float getRaw(float partialTicks) {
      return GetMOP.partial((float)this.value, (float)this.previousValue, partialTicks);
   }

   @Override
   public void update(EntityPlayer player) {
      this.previousValue = this.value;
      if (this.value > this.valueMin && this.tendency < 0) {
         this.value = Math.max(this.value + this.tendency, this.valueMin);
      } else if (this.value < this.valueMax && this.tendency > 0) {
         this.value = Math.min(this.value + this.tendency, this.valueMax);
      }

      if (player != null) {
         ItemStack stack = player.inventory.getStackInSlot(this.slot);
         Object obj = stack.getCapability(AnimationCapabilityProvider.CAPABILITY, null);
         if (obj != null && obj instanceof ItemFlickersPack) {
            ItemFlickersPack animations = (ItemFlickersPack)obj;
            animations.flickers.put(this.name, this);
         }
      }
   }

   public void set(int amount) {
      this.value = amount;
   }

   @Override
   public boolean canRemove() {
      return this.value == 0 && this.previousValue == 0;
   }
}
