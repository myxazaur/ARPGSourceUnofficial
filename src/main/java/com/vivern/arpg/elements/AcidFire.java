package com.vivern.arpg.elements;

import com.vivern.arpg.entity.EntityAcidFire;
import com.vivern.arpg.main.Booom;
import com.vivern.arpg.main.EnchantmentInit;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.Keys;
import com.vivern.arpg.main.Mana;
import com.vivern.arpg.main.NBTHelper;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.WeaponParameters;
import com.vivern.arpg.main.Weapons;
import com.vivern.arpg.potions.PotionEffects;
import com.vivern.arpg.renders.TEISRGuns;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AcidFire extends ItemWeapon {
   public AcidFire() {
      this.setRegistryName("acid_fire");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("acid_fire");
      this.setMaxDamage(4000);
      this.setMaxStackSize(1);
   }

   public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
      return true;
   }

   @Override
   public boolean canAttackMelee(ItemStack itemstack, EntityPlayer player) {
      return false;
   }

   public boolean canDestroyBlockInCreative(World world, BlockPos pos, ItemStack stack, EntityPlayer player) {
      return false;
   }

   public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
      return slotChanged;
   }

   public void onUpdate(ItemStack itemstack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
      if (!worldIn.isRemote) {
         this.setCanShoot(itemstack, entityIn);
         if (IWeapon.canShoot(itemstack)) {
            EntityPlayer player = (EntityPlayer)entityIn;
            int damage = itemstack.getItemDamage();
            World world = player.getEntityWorld();
            Item itemIn = itemstack.getItem();
            EnumHand hand = player.getActiveHand();
            boolean click = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
            boolean click2 = Keys.isKeyPressed(player, Keys.SECONDARYATTACK);
            int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
            float power = Mana.getMagicPowerMax(player);
            int sor = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SORCERY, itemstack);
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
            float manacost = parameters.getEnchanted("manacost", sor);
            if (player.getHeldItemMainhand() == itemstack && !player.getCooldownTracker().hasCooldown(itemIn)) {
               if (Mana.getMana(player) > manacost && click) {
                  Weapons.setPlayerAnimationOnServer(player, 13, EnumHand.MAIN_HAND);
                  world.playSound(
                     (EntityPlayer)null,
                     player.posX,
                     player.posY,
                     player.posZ,
                     Sounds.acid_fire,
                     SoundCategory.AMBIENT,
                     0.9F,
                     0.9F + itemRand.nextFloat() / 5.0F
                  );
                  player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
                  player.addStat(StatList.getObjectUseStats(this));
                  this.bom();
                  EntityAcidFire projectile = new EntityAcidFire(world, player, itemstack, power);
                  Weapons.shoot(
                     projectile,
                     EnumHand.MAIN_HAND,
                     player,
                     player.rotationPitch,
                     player.rotationYaw,
                     0.0F,
                     parameters.get("velocity"),
                     parameters.getEnchanted("inaccuracy", acc),
                     -0.1F,
                     0.5F,
                     0.5F
                  );
                  int mode = NBTHelper.GetNBTint(itemstack, "mode");
                  if (mode == 0) {
                     projectile.potion = MobEffects.WEAKNESS;
                  } else if (mode == 1) {
                     projectile.potion = PotionEffects.BROKEN_ARMOR;
                  } else {
                     projectile.potion = PotionEffects.TOXIN;
                  }

                  world.spawnEntity(projectile);
                  if (!player.capabilities.isCreativeMode) {
                     Mana.changeMana(player, -manacost);
                     Mana.setManaSpeed(player, 0.001F);
                     itemstack.damageItem(1, player);
                  }
               } else if (click2) {
                  NBTHelper.GiveNBTint(itemstack, 0, "mode");
                  NBTHelper.SetNBTint(itemstack, GetMOP.next(NBTHelper.GetNBTint(itemstack, "mode"), 1, 3), "mode");
                  player.getCooldownTracker().setCooldown(this, 20);
                  Weapons.setPlayerAnimationOnServer(player, 28, EnumHand.MAIN_HAND);
                  world.playSound(
                     (EntityPlayer)null,
                     player.posX,
                     player.posY,
                     player.posZ,
                     Sounds.acid_fire_mode,
                     SoundCategory.AMBIENT,
                     0.9F,
                     0.9F + itemRand.nextFloat() / 5.0F
                  );
               }
            }
         }
      }
   }

   @SideOnly(Side.CLIENT)
   private void bom() {
      Booom.lastTick = 8;
      Booom.frequency = 0.4F;
      Booom.x = 1.0F;
      Booom.y = (float)itemRand.nextGaussian();
      Booom.z = (float)itemRand.nextGaussian();
      Booom.power = 0.2F;
      TEISRGuns.mainhandShoot = true;
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.TWO_HANDED;
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return false;
   }

   @Override
   public int getItemEnchantability() {
      return 1;
   }

   @Override
   public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
      return enchantment.type == EnchantmentInit.enchantmentTypeWeapon;
   }
}
