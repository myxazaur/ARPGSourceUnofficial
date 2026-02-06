package com.Vivern.Arpg.elements.animation;

import com.Vivern.Arpg.elements.AnimationCapabilityProvider;
import java.util.HashMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class Flicks {
   public static Flicks instance;
   public HashMap<String, PlayerFlickersPack> flicksMap = new HashMap<>();

   public void clientTick(int tick) {
      if (tick % 21 == 0) {
         for (PlayerFlickersPack flickersPack : this.flicksMap.values()) {
            flickersPack.updateAndRemove();
         }
      } else {
         for (PlayerFlickersPack flickersPack : this.flicksMap.values()) {
            flickersPack.update();
         }
      }
   }

   public PlayerFlickersPack confirmPack(EntityPlayer player) {
      String username = player.getName();
      if (this.flicksMap.containsKey(username)) {
         return this.flicksMap.get(username);
      } else {
         PlayerFlickersPack newpack = new PlayerFlickersPack(username);
         this.flicksMap.put(username, newpack);
         return newpack;
      }
   }

   public FlickInertia getOrCreateFlickInertia(
      EntityPlayer player, int slot, EnumFlick name, int valueMin, int valueMax, int tendency, int value, int valueTarget
   ) {
      PlayerFlickersPack pack = this.confirmPack(player);
      AbstractFlick flick = pack.get(slot, name);
      if (flick != null && flick instanceof FlickInertia) {
         return (FlickInertia)flick;
      } else {
         FlickInertia flicker = new FlickInertia(slot, name, valueMin, valueMax, tendency, value, valueTarget);
         pack.add(slot, name, flicker);
         return flicker;
      }
   }

   public void setClientAnimation(EntityPlayer player, int slot, EnumFlick name, int valueMin, int valueMax, int tendency, int value) {
      FlickInteger flicker = new FlickInteger(slot, name, valueMin, valueMax, tendency, value);
      PlayerFlickersPack pack = this.confirmPack(player);
      pack.add(slot, name, flicker);
   }

   public void setClientAnimation(EntityPlayer player, int slot, EnumFlick name, int value) {
      PlayerFlickersPack pack = this.confirmPack(player);
      if (pack.contains(slot, name)) {
         AbstractFlick flickHas = pack.get(slot, name);
         if (flickHas instanceof FlickInteger) {
            FlickInteger flickInteger = (FlickInteger)flickHas;
            flickInteger.value = value;
         }
      }
   }

   public void setTendency(EntityPlayer player, int slot, EnumFlick name, int tendency) {
      PlayerFlickersPack pack = this.confirmPack(player);
      AbstractFlick flick = pack.get(slot, name);
      if (flick != null && flick instanceof FlickInteger) {
         ((FlickInteger)flick).tendency = tendency;
      }
   }

   public void addTendency(EntityPlayer player, int slot, EnumFlick name, int tendency, int min, int max) {
      PlayerFlickersPack pack = this.confirmPack(player);
      AbstractFlick flick = pack.get(slot, name);
      if (flick != null && flick instanceof FlickInteger) {
         FlickInteger flickInteger = (FlickInteger)flick;
         flickInteger.tendency = MathHelper.clamp(flickInteger.tendency + tendency, min, max);
      }
   }

   public float getClientAnimation(EntityPlayer player, int slot, EnumFlick name, float partialTicks, boolean raw) {
      PlayerFlickersPack pack = this.confirmPack(player);
      AbstractFlick flick = pack.get(slot, name);
      if (flick != null) {
         return raw ? flick.getRaw(partialTicks) : flick.get(partialTicks);
      } else {
         return 0.0F;
      }
   }

   public float getClientAnimation(ItemStack itemstack, EnumFlick name, float partialTicks, boolean raw) {
      Object obj = itemstack.getCapability(AnimationCapabilityProvider.CAPABILITY, null);
      if (obj != null && obj instanceof ItemFlickersPack) {
         ItemFlickersPack animations = (ItemFlickersPack)obj;
         AbstractFlick flick = animations.flickers.get(name);
         if (flick != null) {
            return raw ? flick.getRaw(partialTicks) : flick.get(partialTicks);
         }
      }

      return 0.0F;
   }
}
