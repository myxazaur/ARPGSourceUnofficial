package baubles.api.cap;

import baubles.api.IBauble;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class BaublesContainer extends ItemStackHandler implements IBaublesItemHandler {
   private static final int BAUBLE_SLOTS = 7;
   private boolean[] changed = new boolean[7];
   private boolean blockEvents = false;
   private EntityLivingBase player;

   public BaublesContainer() {
      super(7);
   }

   public void setSize(int size) {
      if (size < 7) {
         size = 7;
      }

      super.setSize(size);
      boolean[] old = this.changed;
      this.changed = new boolean[size];

      for (int i = 0; i < old.length && i < this.changed.length; i++) {
         this.changed[i] = old[i];
      }
   }

   @Override
   public boolean isItemValidForSlot(int slot, ItemStack stack, EntityLivingBase player) {
      if (stack != null && !stack.isEmpty() && stack.hasCapability(BaublesCapabilities.CAPABILITY_ITEM_BAUBLE, null)) {
         IBauble bauble = (IBauble)stack.getCapability(BaublesCapabilities.CAPABILITY_ITEM_BAUBLE, null);
         return bauble.canEquip(stack, player) && bauble.getBaubleType(stack).hasSlot(slot);
      } else {
         return false;
      }
   }

   public void setStackInSlot(int slot, ItemStack stack) {
      if (stack == null || stack.isEmpty() || this.isItemValidForSlot(slot, stack, this.player)) {
         super.setStackInSlot(slot, stack);
      }
   }

   public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
      return !this.isItemValidForSlot(slot, stack, this.player) ? stack : super.insertItem(slot, stack, simulate);
   }

   @Override
   public boolean isEventBlocked() {
      return this.blockEvents;
   }

   @Override
   public void setEventBlock(boolean blockEvents) {
      this.blockEvents = blockEvents;
   }

   protected void onContentsChanged(int slot) {
      this.setChanged(slot, true);
   }

   @Override
   public boolean isChanged(int slot) {
      if (this.changed == null) {
         this.changed = new boolean[this.getSlots()];
      }

      return this.changed[slot];
   }

   @Override
   public void setChanged(int slot, boolean change) {
      if (this.changed == null) {
         this.changed = new boolean[this.getSlots()];
      }

      this.changed[slot] = change;
   }

   @Override
   public void setPlayer(EntityLivingBase player) {
      this.player = player;
   }
}
