package com.Vivern.Arpg.elements.animation;

import com.Vivern.Arpg.elements.AnimationCapabilityProvider;
import com.Vivern.Arpg.main.GetMOP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class FlickInertia extends AbstractFlick {
   public int slot;
   public EnumFlick name;
   public int valueMin;
   public int valueMax;
   public int tendency;
   public float value;
   public int valueTarget;
   public float previousValue;
   public float momentum;
   public boolean hasTarget;

   public FlickInertia(int slot, EnumFlick name, int valueMin, int valueMax, int tendency, int value, int valueTarget) {
      this.slot = slot;
      this.name = name;
      this.valueMin = valueMin;
      this.valueMax = valueMax;
      this.tendency = tendency;
      this.value = value;
      this.valueTarget = valueTarget;
   }

   @Override
   public float get(float partialTicks) {
      float partialValue = GetMOP.partial(this.value, this.previousValue, partialTicks);
      return GetMOP.getfromto(partialValue, (float)this.valueMin, (float)this.valueMax);
   }

   @Override
   public float getRaw(float partialTicks) {
      return GetMOP.partial(this.value, this.previousValue, partialTicks);
   }

   @Override
   public void update(EntityPlayer player) {
      this.previousValue = this.value;
      if (this.hasTarget) {
         this.momentum = this.momentum + (this.valueTarget - this.value) * 0.2F;
         if (this.value > this.valueMin && this.momentum < 0.0F) {
            this.value = Math.max(this.value + this.momentum, (float)this.valueMin);
         } else if (this.value < this.valueMax && this.momentum > 0.0F) {
            this.value = Math.min(this.value + this.momentum, (float)this.valueMax);
         }

         if (this.value <= this.valueTarget && this.previousValue >= this.valueTarget
            || this.value >= this.valueTarget && this.previousValue <= this.valueTarget) {
            this.hasTarget = false;
         }
      } else {
         this.momentum *= 0.7F;
         float force = this.tendency + this.momentum;
         if (this.value > this.valueMin && force < 0.0F) {
            this.value = Math.max(this.value + force, (float)this.valueMin);
         } else if (this.value < this.valueMax && force > 0.0F) {
            this.value = Math.min(this.value + force, (float)this.valueMax);
         }
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
      return this.value == 0.0F && this.previousValue == 0.0F;
   }
}
