package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.elements.animation.ItemFlickersPack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class AnimationCapabilityProvider implements ICapabilityProvider {
   public ItemFlickersPack animations = new ItemFlickersPack();
   @CapabilityInject(ItemFlickersPack.class)
   public static Capability<ItemFlickersPack> CAPABILITY = null;
   public static ItemFlickersPack missing = new ItemFlickersPack();

   public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
      return capability == CAPABILITY;
   }

   public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
      return (T)(capability == CAPABILITY ? this.animations : null);
   }

   public static void register() {
      CapabilityManager.INSTANCE.register(ItemFlickersPack.class, new IStorage<ItemFlickersPack>() {
         public NBTBase writeNBT(Capability<ItemFlickersPack> capability, ItemFlickersPack instance, EnumFacing side) {
            return null;
         }

         public void readNBT(Capability<ItemFlickersPack> capability, ItemFlickersPack instance, EnumFacing side, NBTBase nbt) {
         }
      }, AnimationCapabilityProvider::getMissing);
   }

   public static ItemFlickersPack getMissing() {
      return missing;
   }
}
