package com.Vivern.Arpg.elements.animation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class PlayerFlickersPack {
   public HashMap<Long, AbstractFlick> flickers = new HashMap<>();
   public ArrayList<Long> removes = new ArrayList<>();
   public String username;

   public PlayerFlickersPack(String username) {
      this.username = username;
   }

   public void update() {
      EntityPlayer player = Minecraft.getMinecraft().world.getPlayerEntityByName(this.username);
      if (player != null) {
         for (AbstractFlick aflick : this.flickers.values()) {
            aflick.update(player);
         }
      }
   }

   public void updateAndRemove() {
      EntityPlayer player = Minecraft.getMinecraft().world.getPlayerEntityByName(this.username);

      for (Entry<Long, AbstractFlick> entry : this.flickers.entrySet()) {
         entry.getValue().update(player);
         if (entry.getValue().canRemove()) {
            this.removes.add(entry.getKey());
         }
      }

      for (long key : this.removes) {
         this.flickers.remove(key);
      }

      this.removes.clear();
   }

   public void add(int slot, EnumFlick name, AbstractFlick flick) {
      long key = (long)slot << 32 | name.ordinal();
      this.flickers.put(key, flick);
   }

   public boolean contains(int slot, EnumFlick name) {
      long key = (long)slot << 32 | name.ordinal();
      return this.flickers.containsKey(key);
   }

   @Nullable
   public AbstractFlick get(int slot, EnumFlick name) {
      long key = (long)slot << 32 | name.ordinal();
      return this.flickers.get(key);
   }
}
