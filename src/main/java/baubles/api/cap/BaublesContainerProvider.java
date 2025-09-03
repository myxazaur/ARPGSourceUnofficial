package baubles.api.cap;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;

public class BaublesContainerProvider implements INBTSerializable<NBTTagCompound>, ICapabilityProvider {
   private final BaublesContainer container;

   public BaublesContainerProvider(BaublesContainer container) {
      this.container = container;
   }

   public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
      return capability == BaublesCapabilities.CAPABILITY_BAUBLES;
   }

   public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
      return (T)(capability == BaublesCapabilities.CAPABILITY_BAUBLES ? this.container : null);
   }

   public NBTTagCompound serializeNBT() {
      return this.container.serializeNBT();
   }

   public void deserializeNBT(NBTTagCompound nbt) {
      this.container.deserializeNBT(nbt);
   }
}
