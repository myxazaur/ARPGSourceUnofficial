//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.entity.EntitySwordGhost;
import com.Vivern.Arpg.main.NBTHelper;
import com.google.common.collect.Multimap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class GhostSword extends Item {
   ResourceLocation largesmoke = new ResourceLocation("arpg:textures/largecloud.png");

   public GhostSword() {
      this.setRegistryName("ghost_sword");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("ghost_sword");
      this.setMaxDamage(1000);
      this.setMaxStackSize(1);
   }

   public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
      Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
      if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
         multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 8.0, 0));
         multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.3000000953674316, 0));
      }

      return multimap;
   }

   public boolean hitEntity(ItemStack itemstack, EntityLivingBase target, EntityLivingBase attacker) {
      if (attacker instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)attacker;
         World world = player.getEntityWorld();
         if (!world.isRemote && NBTHelper.GetNBTfloat(itemstack, "stength") == 1.0F) {
            for (int ss = 0; ss < 3; ss++) {
               if (itemRand.nextFloat() < 0.3333) {
                  EntitySwordGhost ghost1 = new EntitySwordGhost(world, player, itemstack);
                  ghost1.shoot(player, player.rotationPitch - 15.0F + itemRand.nextInt(25), player.rotationYaw - 60.0F, 0.5F, 0.5F, 3.7F);
                  world.spawnEntity(ghost1);
               } else if (itemRand.nextFloat() < 0.5) {
                  EntitySwordGhost ghost2 = new EntitySwordGhost(world, player, itemstack);
                  ghost2.shoot(player, player.rotationPitch - 15.0F + itemRand.nextInt(25), player.rotationYaw + 60.0F, 0.5F, 0.5F, 3.7F);
                  world.spawnEntity(ghost2);
               }
            }
         }
      }

      itemstack.damageItem(1, attacker);
      return super.hitEntity(itemstack, target, attacker);
   }

   public void onUpdate(ItemStack itemstack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
      if (!entityIn.world.isRemote && entityIn instanceof EntityPlayer) {
         NBTHelper.GiveNBTfloat(itemstack, 0.0F, "stength");
         NBTHelper.SetNBTfloat(itemstack, ((EntityPlayer)entityIn).getCooledAttackStrength(0.0F), "stength");
      }
   }
}
