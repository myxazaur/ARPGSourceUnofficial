//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.PropertiesRegistry;
import com.google.common.collect.Multimap;
import java.util.UUID;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class DeadRampart extends Item {
   private static final UUID[] SHIELD_MODIFIERSG = new UUID[]{
      UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"),
      UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"),
      UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"),
      UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")
   };
   ResourceLocation largesmoke = new ResourceLocation("arpg:textures/largecloud.png");

   public DeadRampart() {
      this.setRegistryName("dead_rampart");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("dead_rampart");
      this.setMaxDamage(800);
      this.setMaxStackSize(1);
   }

   public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
      return false;
   }

   public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot, ItemStack itemstack) {
      Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(equipmentSlot, itemstack);
      float power = NBTHelper.GetNBTint(itemstack, "stength");
      if (equipmentSlot == EntityEquipmentSlot.MAINHAND || equipmentSlot == EntityEquipmentSlot.OFFHAND) {
         multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Shield attack modifier", 1.0, 0));
         multimap.put(
            SharedMonsterAttributes.KNOCKBACK_RESISTANCE.getName(),
            new AttributeModifier(SHIELD_MODIFIERSG[equipmentSlot.getIndex()], "Shield knockback modifier", 0.1 + power / 250.0F, 0)
         );
         multimap.put(
            PropertiesRegistry.ARMOR_PROTECTION.getName(),
            new AttributeModifier(SHIELD_MODIFIERSG[equipmentSlot.getIndex()], "Shield protection", 3.0 + power / 25.0F, 0)
         );
      }

      return multimap;
   }

   public void onUpdate(ItemStack itemstack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
      if (entityIn instanceof EntityPlayer) {
         NBTHelper.GiveNBTint(itemstack, 0, "stength");
         double rast = entityIn.getPositionVector().distanceTo(new Vec3d(entityIn.lastTickPosX, entityIn.lastTickPosY, entityIn.lastTickPosZ));
         System.out.println(rast);
         if (rast < 5.0E-10 && NBTHelper.GetNBTint(itemstack, "stength") < 50) {
            NBTHelper.AddNBTint(itemstack, 1, "stength");
         }

         if (rast > 5.0E-10 && NBTHelper.GetNBTint(itemstack, "stength") > 0) {
            NBTHelper.AddNBTint(itemstack, -1, "stength");
         }
      }
   }
}
