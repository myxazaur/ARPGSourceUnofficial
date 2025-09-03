//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.entity.EntitySummon;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.Keys;
import com.Vivern.Arpg.main.Mana;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.mobs.SummonedBlaze;
import java.util.List;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import org.lwjgl.input.Mouse;

public class WandOfBlazes extends ItemWeapon {
   public WandOfBlazes() {
      this.setRegistryName("wand_of_blazes");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("wand_of_blazes");
      this.setMaxDamage(10);
      this.setMaxStackSize(1);
   }

   public int getMaxItemUseDuration(ItemStack itemstack) {
      return 72000;
   }

   public EnumAction getItemUseAction(ItemStack stack) {
      return EnumAction.BOW;
   }

   public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
      ItemStack itemstack = player.getHeldItem(hand);
      player.setActiveHand(hand);
      return new ActionResult(EnumActionResult.PASS, itemstack);
   }

   public boolean canContinueUsing(ItemStack oldStack, ItemStack newStack) {
      return true;
   }

   public void onUpdate(ItemStack itemstack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
      if (!worldIn.isRemote && entityIn instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)entityIn;
         int damage = itemstack.getItemDamage();
         World world = player.getEntityWorld();
         Item itemIn = itemstack.getItem();
         EnumHand hand = player.getActiveHand();
         boolean click = Mouse.isButtonDown(1);
         float mana = Mana.getMana(player);
         float spee = Mana.getManaSpeed(player);
         int soulsmax = Mana.getLeadership(player);
         int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
         int sor = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SORCERY, itemstack);
         float power = Mana.getMagicPowerMax(player);
         boolean clickcec = GameSettings.isKeyDown(Keys.SECONDARYATTACK);
         if (player.getActiveItemStack() == itemstack && mana > 15.0F - sor * 2.5F && click && !player.getCooldownTracker().hasCooldown(itemIn)) {
            world.playSound(
               (EntityPlayer)null,
               player.posX,
               player.posY,
               player.posZ,
               Sounds.magic_k,
               SoundCategory.AMBIENT,
               0.8F,
               0.9F + itemRand.nextFloat() / 5.0F
            );
            player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
            player.addStat(StatList.getObjectUseStats(this));
            if (!player.capabilities.isCreativeMode) {
               Mana.changeMana(player, -15.0F + sor * 2.5F);
               Mana.setManaSpeed(player, 0.001F);
            }

            if (!world.isRemote) {
               EntitySummon projectile = new EntitySummon(world, player, 1, 1 + soulsmax, itemstack);
               projectile.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.0F, 1.0F);
               projectile.setPosition(player.posX, player.posY + player.getEyeHeight() - 0.2, player.posZ);
               world.spawnEntity(projectile);
            }
         }

         if (player.getHeldItemMainhand() == itemstack && clickcec && !player.getCooldownTracker().hasCooldown(itemIn)) {
            double damageRadius = 200.0;
            AxisAlignedBB axisalignedbb = player.getEntityBoundingBox()
               .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
               .offset(-damageRadius, -damageRadius, -damageRadius);
            List<EntityLivingBase> list = player.world.getEntitiesWithinAABB(SummonedBlaze.class, axisalignedbb);
            if (!list.isEmpty()) {
               for (Entity loaded : list) {
                  if (loaded instanceof SummonedBlaze
                     && (((SummonedBlaze)loaded).getOwner() == player || ((SummonedBlaze)loaded).getOwnerId() == player.getUniqueID())) {
                     ((SummonedBlaze)loaded).expelling();
                  }
               }
            }

            List<EntitySummon> list2 = player.world.getEntitiesWithinAABB(EntitySummon.class, axisalignedbb);
            if (!list2.isEmpty()) {
               for (EntitySummon loadedx : list2) {
                  if (loadedx.getThrower() == player) {
                     loadedx.setDead();
                  }
               }
            }

            player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
            player.addStat(StatList.getObjectUseStats(this));
            world.playSound(
               (EntityPlayer)null,
               player.posX,
               player.posY,
               player.posZ,
               Sounds.unsummon,
               SoundCategory.AMBIENT,
               0.8F,
               0.9F + itemRand.nextFloat() / 5.0F
            );
         }
      }
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.SEMI_ONE_HANDED;
   }

   @Override
   public boolean autoReload(ItemStack itemstack) {
      return false;
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return true;
   }

   @Override
   public boolean hasZoom(ItemStack itemstack) {
      return false;
   }

   @Override
   public int getCooldownTime(ItemStack itemstack) {
      return 30 - EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack) * 10;
   }

   @Override
   public int getReloadTime(ItemStack itemstack) {
      return 0;
   }

   @Override
   public float getZoom(ItemStack itemstack, EntityPlayer player) {
      return 0.0F;
   }
}
