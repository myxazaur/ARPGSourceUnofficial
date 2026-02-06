package com.Vivern.Arpg.main;

import baubles.api.BaublesApi;
import com.Vivern.Arpg.elements.IWeapon;
import com.Vivern.Arpg.network.PacketHandler;
import com.Vivern.Arpg.network.PacketKeysToServer;
import com.Vivern.Arpg.potions.Freezing;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.settings.IKeyConflictContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class Keys implements IKeyConflictContext {
   public static int clientLastKeys = 0;
   private static final String catergory = "Arpg keys";
   public static final KeyBinding FORWARD = new KeyBinding("keys.forward", 17, "Arpg keys");
   public static final KeyBinding RIGHT = new KeyBinding("keys.right", 32, "Arpg keys");
   public static final KeyBinding LEFT = new KeyBinding("keys.left", 30, "Arpg keys");
   public static final KeyBinding BACK = new KeyBinding("keys.back", 31, "Arpg keys");
   public static final KeyBinding JUMP = new KeyBinding("keys.jump", 57, "Arpg keys");
   public static final KeyBinding SPRINT = new KeyBinding("keys.sprint", 29, "Arpg keys");
   public static final KeyBindFixed PRIMARYATTACK = new KeyBindFixed("keys.primaryattack", 1, "Arpg keys");
   public static final KeyBinding SECONDARYATTACK = new KeyBinding("keys.secondaryattack", 34, "Arpg keys");
   public static final KeyBinding HEADABILITY = new KeyBinding("keys.headability", 34, "Arpg keys");
   public static final KeyBinding SCOPE = new KeyBinding("keys.scope", 3, "Arpg keys");
   public static final KeyBinding GRAPLINGHOOK = new KeyBinding("keys.hook", 19, "Arpg keys");
   public static final KeyBinding USE = new KeyBinding("keys.use", 1, "Arpg keys");
   public static final KeyBinding GRENADE = new KeyBinding("keys.grenade", 34, "Arpg keys");
   public static Keys keyconflictcontext = new Keys();

   public static void register() {
      setRegister(FORWARD);
      setRegister(RIGHT);
      setRegister(LEFT);
      setRegister(BACK);
      setRegister(JUMP);
      setRegister(SPRINT);
      setRegister(PRIMARYATTACK);
      setRegister(SECONDARYATTACK);
      setRegister(HEADABILITY);
      setRegister(SCOPE);
      setRegister(GRAPLINGHOOK);
      setRegister(USE);
      setRegister(GRENADE);
      PRIMARYATTACK.setKeyConflictContext(keyconflictcontext);
      SECONDARYATTACK.setKeyConflictContext(keyconflictcontext);
      FORWARD.setKeyConflictContext(keyconflictcontext);
      RIGHT.setKeyConflictContext(keyconflictcontext);
      LEFT.setKeyConflictContext(keyconflictcontext);
      BACK.setKeyConflictContext(keyconflictcontext);
      JUMP.setKeyConflictContext(keyconflictcontext);
      SPRINT.setKeyConflictContext(keyconflictcontext);
      HEADABILITY.setKeyConflictContext(keyconflictcontext);
      SCOPE.setKeyConflictContext(keyconflictcontext);
      GRAPLINGHOOK.setKeyConflictContext(keyconflictcontext);
      USE.setKeyConflictContext(keyconflictcontext);
      GRENADE.setKeyConflictContext(keyconflictcontext);
   }

   private static void setRegister(KeyBinding binding) {
      ClientRegistry.registerKeyBinding(binding);
   }

   public static boolean isKeyDown(KeyBinding key) {
      int i = key == PRIMARYATTACK ? ((KeyBindFixed)key).getKeyIndex() : key.getKeyCode();
      if (i != 0 && i < 256) {
         return i < 0 ? Mouse.isButtonDown(i + 100) : Keyboard.isKeyDown(i);
      } else {
         return false;
      }
   }

   @SideOnly(Side.CLIENT)
   public static void onUpdate(EntityPlayer player) {
      if (player != null) {
         int newkeys = getKeysPacked();
         if (newkeys != clientLastKeys) {
            PacketKeysToServer packet = new PacketKeysToServer();
            packet.writeint(newkeys);
            PacketHandler.NETWORK.sendToServer(packet);
            Freezing.onKeysChange(player, newkeys);
         }

         clientLastKeys = newkeys;
      }
   }

   public static int getKeysPacked() {
      int keys = 0;
      if (Minecraft.getMinecraft().currentScreen != null) {
         return keys;
      } else {
         if (isKeyDown(FORWARD)) {
            keys |= 1;
         }

         if (isKeyDown(RIGHT)) {
            keys |= 2;
         }

         if (isKeyDown(LEFT)) {
            keys |= 4;
         }

         if (isKeyDown(BACK)) {
            keys |= 8;
         }

         if (isKeyDown(JUMP)) {
            keys |= 16;
         }

         if (isKeyDown(SPRINT)) {
            keys |= 32;
         }

         if (Mouse.isButtonDown(0)) {
            keys |= 64;
         }

         if (Mouse.isButtonDown(1)) {
            keys |= 128;
         }

         if (isKeyDown(HEADABILITY)) {
            keys |= 256;
         }

         if (isKeyDown(SCOPE)) {
            keys |= 512;
         }

         if (isKeyDown(GRAPLINGHOOK)) {
            keys |= 1024;
         }

         if (isKeyDown(USE)) {
            keys |= 8192;
         }

         if (isKeyDown(GRENADE)) {
            keys |= 16384;
         }

         if (Minecraft.getMinecraft().player != null) {
            if (PlayerButtonTracker.getDoubleJump(Minecraft.getMinecraft().player)) {
               keys |= 2048;
            }

            ItemStack current = Minecraft.getMinecraft().player.getHeldItemMainhand();
            IWeapon iw = current.getItem() instanceof IWeapon ? (IWeapon)current.getItem() : null;
            boolean aimlens = BaublesApi.isBaubleEquipped(Minecraft.getMinecraft().player, ItemsRegister.AIMLENS) > -1;
            if (PlayerButtonTracker.getScopeActive(Minecraft.getMinecraft().player, iw != null && iw.hasZoom(current) || aimlens)) {
               keys |= 4096;
            }
         }

         return keys;
      }
   }

   public static boolean unpackCheckKey(int allKeysPacked, KeyBinding key) {
      if (key == FORWARD) {
         return (allKeysPacked & 1) != 0;
      } else if (key == RIGHT) {
         return (allKeysPacked & 2) != 0;
      } else if (key == LEFT) {
         return (allKeysPacked & 4) != 0;
      } else if (key == BACK) {
         return (allKeysPacked & 8) != 0;
      } else if (key == JUMP) {
         return (allKeysPacked & 16) != 0;
      } else if (key == SPRINT) {
         return (allKeysPacked & 32) != 0;
      } else if (key == PRIMARYATTACK) {
         return (allKeysPacked & 64) != 0;
      } else if (key == SECONDARYATTACK) {
         return (allKeysPacked & 128) != 0;
      } else if (key == HEADABILITY) {
         return (allKeysPacked & 256) != 0;
      } else if (key == SCOPE) {
         return (allKeysPacked & 512) != 0;
      } else if (key == GRAPLINGHOOK) {
         return (allKeysPacked & 1024) != 0;
      } else if (key == USE) {
         return (allKeysPacked & 8192) != 0;
      } else {
         return key == GRENADE ? (allKeysPacked & 16384) != 0 : false;
      }
   }

   public static boolean isKeyPressed(EntityPlayer player, KeyBinding key) {
      if (key == PRIMARYATTACK || key == SECONDARYATTACK) {
         if (player.isHandActive()) {
            return false;
         }

         if (key == SECONDARYATTACK) {
            if (isKeyBlockedByItem(player.getHeldItemMainhand(), player, key)) {
               return false;
            }

            if (isKeyBlockedByItem(player.getHeldItemOffhand(), player, key)) {
               return false;
            }
         }
      }

      int keys = (Integer)player.getDataManager().get(PropertiesRegistry.KEYS_PRESSED);
      return unpackCheckKey(keys, key);
   }

   public static boolean isKeyBlockedByItem(ItemStack itemstack, EntityPlayer player, KeyBinding key) {
      return itemstack.getItem() instanceof ItemFood ? true : itemstack.getItem() instanceof ItemBlock;
   }

   public static boolean isPressedDoubleJump(EntityPlayer player) {
      int keys = (Integer)player.getDataManager().get(PropertiesRegistry.KEYS_PRESSED);
      return (keys & 2048) != 0;
   }

   public static boolean isScopeActived(EntityPlayer player) {
      int keys = (Integer)player.getDataManager().get(PropertiesRegistry.KEYS_PRESSED);
      return (keys & 4096) != 0;
   }

   public boolean isActive() {
      return true;
   }

   public boolean conflicts(IKeyConflictContext other) {
      return false;
   }
}
