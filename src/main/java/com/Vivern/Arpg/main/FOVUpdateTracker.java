//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.main;

import baubles.api.BaublesApi;
import com.Vivern.Arpg.elements.IWeapon;
import java.lang.reflect.Field;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.RenderSpecificHandEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber(
   modid = "arpg"
)
@SideOnly(Side.CLIENT)
public class FOVUpdateTracker {
   public static AttributeModifier SPRINTING_SPEED_BOOST;
   public static boolean canTryReflection = true;

   @Nullable
   public static AttributeModifier getSprintingSpeedBoost(EntityLivingBase entityLivingBase) {
      if (SPRINTING_SPEED_BOOST == null) {
         IAttributeInstance iattributeinstance = entityLivingBase.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);

         for (AttributeModifier modifier : iattributeinstance.getModifiers()) {
            if ("Sprinting speed boost".equals(modifier.getName())) {
               return modifier;
            }
         }

         if (canTryReflection) {
            canTryReflection = false;

            try {
               Field field = EntityLivingBase.class.getDeclaredField("SPRINTING_SPEED_BOOST");
               field.setAccessible(true);
               Object obj = field.get(null);
               if (obj instanceof AttributeModifier) {
                  SPRINTING_SPEED_BOOST = (AttributeModifier)obj;
               }
            } catch (NoSuchFieldException var4) {
            } catch (SecurityException var5) {
            } catch (IllegalArgumentException var6) {
            } catch (IllegalAccessException var7) {
            }
         }
      }

      return SPRINTING_SPEED_BOOST;
   }

   public static float getStandartFovModifierWithoutSpeed(EntityPlayer player) {
      float f = 1.0F;
      if (player.capabilities.isFlying) {
         f *= 1.1F;
      }

      double defaultSpeed = SharedMonsterAttributes.MOVEMENT_SPEED.getDefaultValue();
      if (player.isSprinting() && getSprintingSpeedBoost(player) != null) {
         defaultSpeed *= 1.0 + getSprintingSpeedBoost(player).getAmount();
      }

      f = (float)(f * ((defaultSpeed / player.capabilities.getWalkSpeed() + 1.0) / 2.0));
      if (player.capabilities.getWalkSpeed() == 0.0F || Float.isNaN(f) || Float.isInfinite(f)) {
         f = 1.0F;
      }

      if (player.isHandActive() && player.getActiveItemStack().getItem() instanceof ItemBow) {
         int i = player.getItemInUseMaxCount();
         float f1 = i / 20.0F;
         if (f1 > 1.0F) {
            f1 = 1.0F;
         } else {
            f1 *= f1;
         }

         f *= 1.0F - f1 * 0.15F;
      }

      return f;
   }

   public static boolean disableSpeedFOV(EntityPlayer player) {
      return true;
   }

   @SubscribeEvent
   public static void onZoom(FOVUpdateEvent e) {
      ItemStack current = e.getEntity().getHeldItemMainhand();
      IWeapon iw = current.getItem() instanceof IWeapon ? (IWeapon)current.getItem() : null;
      boolean aimlens = BaublesApi.isBaubleEquipped(e.getEntity(), ItemsRegister.AIMLENS) > -1;
      boolean active = PlayerButtonTracker.getScopeActive(e.getEntity(), iw != null && iw.hasZoom(current) || aimlens);
      float fov = disableSpeedFOV(e.getEntity()) ? getStandartFovModifierWithoutSpeed(e.getEntity()) : e.getFov();
      boolean iweaponhaszoom = false;
      if (iw != null && iw.hasZoom(current) && active) {
         iweaponhaszoom = true;
         fov -= iw.getZoom(current, e.getEntity());
      }

      fov -= Booom.FOVboom;
      if (aimlens && active) {
         if (iweaponhaszoom) {
            fov -= 0.3F;
         } else {
            fov -= 0.55F;
         }
      }

      e.setNewfov(fov);
   }

   @SubscribeEvent
   public static void RenderHand(RenderSpecificHandEvent event) {
      if (PlayerButtonTracker.scopeactived
         && event.getItemStack().getItem() instanceof IWeapon
         && ((IWeapon)event.getItemStack().getItem()).hasZoom(event.getItemStack())) {
         event.setCanceled(true);
      }
   }
}
