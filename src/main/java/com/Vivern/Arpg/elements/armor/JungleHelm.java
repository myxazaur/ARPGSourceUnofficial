//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.armor;

import com.Vivern.Arpg.elements.models.JungleHelmetModel;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Mana;
import com.Vivern.Arpg.main.PropertiesRegistry;
import com.google.common.collect.Multimap;
import java.util.UUID;
import net.minecraft.block.BlockDispenser;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class JungleHelm extends ItemArmor {
   private static final UUID[] ARMOR_MODIFIERSG = new UUID[]{
      UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"),
      UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"),
      UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"),
      UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")
   };
   public static ArmorMaterial jungle_mt = EnumHelper.addArmorMaterial(
      "arpg:jungle_mt", "arpg:jungle_tx", 9, new int[]{3, 5, 3, 2}, 7, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F
   );
   public static JungleHelmetModel modelh = new JungleHelmetModel();

   public JungleHelm() {
      super(jungle_mt, 0, EntityEquipmentSlot.HEAD);
      this.setRegistryName("jungle_mask");
      this.setTranslationKey("jungle_mask");
      this.setMaxDamage(450);
      this.maxStackSize = 1;
      this.setCreativeTab(CreativeTabs.COMBAT);
      BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, ItemArmor.DISPENSER_BEHAVIOR);
   }

   public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
      Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
      if (equipmentSlot == this.armorType) {
         multimap.put(
            SharedMonsterAttributes.ARMOR.getName(),
            new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor modifier", this.damageReduceAmount, 0)
         );
         multimap.put(
            PropertiesRegistry.LEADERSHIP.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor souls", 4.0, 0)
         );
         multimap.put(PropertiesRegistry.MANA_MAX.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor mana", 1.0, 0));
         multimap.put(
            PropertiesRegistry.MANASPEED_MAX.getName(),
            new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor manaregen", 0.05, 0)
         );
      }

      return multimap;
   }

   public void onUpdate(ItemStack itemstack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
      if (entityIn instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)entityIn;
         World world = player.getEntityWorld();
         float power = Mana.getMagicPowerMax(player);
         if (((ItemStack)player.inventory.armorInventory.get(3)).getItem() == ItemsRegister.FIREMAGEHELM
            && ((ItemStack)player.inventory.armorInventory.get(2)).getItem() == ItemsRegister.FIREMAGECHEST
            && ((ItemStack)player.inventory.armorInventory.get(1)).getItem() == ItemsRegister.FIREMAGELEGS
            && ((ItemStack)player.inventory.armorInventory.get(0)).getItem() == ItemsRegister.FIREMAGEBOOTS) {
         }
      }
   }

   @SideOnly(Side.CLIENT)
   public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped model) {
      if (armorSlot != EntityEquipmentSlot.HEAD) {
         return null;
      } else if (itemStack != ItemStack.EMPTY) {
         modelh.isSneak = entityLiving.isSneaking();
         modelh.isRiding = entityLiving.isRiding();
         modelh.isChild = entityLiving.isChild();
         return modelh;
      } else {
         return null;
      }
   }

   public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
      return slot == EntityEquipmentSlot.HEAD ? "arpg:textures/jungle_helmet_model_tex.png" : null;
   }
}
