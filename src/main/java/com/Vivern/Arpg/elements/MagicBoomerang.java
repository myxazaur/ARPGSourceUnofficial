//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.entity.EntityBoomerangMagic;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Keys;
import com.Vivern.Arpg.main.Mana;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.WeaponParameters;
import com.Vivern.Arpg.main.Weapons;
import javax.annotation.Nullable;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MagicBoomerang extends ItemWeapon {
   public MagicBoomerang() {
      this.setRegistryName("magic_boomerang");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("magic_boomerang");
      this.setMaxStackSize(1);
      this.setFull3D();
      this.setMaxDamage(2);
      this.addPropertyOverride(new ResourceLocation("throw"), new IItemPropertyGetter() {
         @SideOnly(Side.CLIENT)
         public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
            if (entityIn == null) {
               return 0.0F;
            } else if (entityIn instanceof EntityPlayer) {
               EntityPlayer pl = (EntityPlayer)entityIn;
               return pl.getCooldownTracker().hasCooldown(ItemsRegister.MAGICBOOMERANG) ? 1.0F : 0.0F;
            } else {
               return 0.0F;
            }
         }
      });
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

   public void onUpdate(ItemStack stack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
      if (!world.isRemote) {
         this.setCanShoot(stack, entityIn);
         if (IWeapon.canShoot(stack)) {
            EntityPlayer player = (EntityPlayer)entityIn;
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
            float manacost = parameters.getEnchanted("manacost", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SORCERY, stack));
            if (Mana.getMana(player) >= manacost) {
               EnumHand hand = null;
               if (Keys.isKeyPressed(player, Keys.PRIMARYATTACK) && player.getHeldItemMainhand() == stack && !player.getCooldownTracker().hasCooldown(this)) {
                  hand = EnumHand.MAIN_HAND;
               } else if (Keys.isKeyPressed(player, Keys.SECONDARYATTACK) && player.getHeldItemOffhand() == stack && !player.getCooldownTracker().hasCooldown(this)) {
                  hand = EnumHand.OFF_HAND;
               }

               if (hand != null) {
                  Weapons.setPlayerAnimationOnServer(player, 1, hand);
                  player.getCooldownTracker().setCooldown(this, 400);
                  player.addStat(StatList.getObjectUseStats(this));
                  world.playSound(
                     (EntityPlayer)null,
                     player.posX,
                     player.posY,
                     player.posZ,
                     Sounds.swosh,
                     SoundCategory.AMBIENT,
                     0.8F,
                     1.0F / (itemRand.nextFloat() * 0.4F + 0.8F)
                  );
                  EntityBoomerangMagic projectile = new EntityBoomerangMagic(world, player, stack, Mana.getMagicPowerMax(player));
                  projectile.damage = parameters.getEnchanted("damage", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, stack));
                  projectile.knockback = parameters.getEnchanted("knockback", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, stack));
                  Weapons.shoot(
                     projectile,
                     hand,
                     player,
                     player.rotationPitch,
                     player.rotationYaw,
                     0.0F,
                     parameters.getEnchanted("velocity", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, stack)),
                     parameters.get("inaccuracy"),
                     -0.15F,
                     0.4F,
                     0.1F
                  );
                  world.spawnEntity(projectile);
                  if (!player.capabilities.isCreativeMode) {
                     Mana.changeMana(player, -manacost);
                     Mana.setManaSpeed(player, 0.001F);
                  }
               }
            }
         }
      }
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return false;
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.SEMI_ONE_HANDED;
   }

   @Override
   public int getItemEnchantability() {
      return 2;
   }
}
