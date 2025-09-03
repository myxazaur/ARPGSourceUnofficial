//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.elements.models.AbstractMobModel;
import com.Vivern.Arpg.main.Booom;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.Keys;
import com.Vivern.Arpg.main.MovingSoundEntity;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.WeaponParameters;
import com.Vivern.Arpg.main.Weapons;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityArrow.PickupStatus;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class AbstractBow extends ItemWeapon {
   public float speedToCritical = 1.0F;
   public MovingSoundEntity clientPullSound = null;
   public float pullSoundPitch = 1.0F;

   public AbstractBow(
      String name, int maxdamage, float velocity, float inaccuracy, int maxPullTime, int minPullTime, float arrowDamageBonus, float mightDamageBonus
   ) {
      this.setRegistryName(name);
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey(name);
      this.setMaxDamage(maxdamage);
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

   @SideOnly(Side.CLIENT)
   @Override
   public void bom(int param) {
      if (param >= 0) {
         Booom.lastTick = 16;
         Booom.frequency = -0.196F;
         Booom.x = -1.0F;
         Booom.y = (itemRand.nextFloat() - 0.5F) * 0.5F;
         Booom.z = (itemRand.nextFloat() - 0.5F) * 0.5F;
         Booom.power = 0.15F * (param / 10.0F);
         if (this.clientPullSound != null) {
            if (Minecraft.getMinecraft().getSoundHandler().isSoundPlaying(this.clientPullSound)) {
               Minecraft.getMinecraft().getSoundHandler().stopSound(this.clientPullSound);
            }

            this.clientPullSound = null;
         }
      } else if (param == -2) {
         if (this.clientPullSound != null) {
            if (Minecraft.getMinecraft().getSoundHandler().isSoundPlaying(this.clientPullSound)) {
               Minecraft.getMinecraft().getSoundHandler().stopSound(this.clientPullSound);
            }

            this.clientPullSound = null;
         }
      } else if (Minecraft.getMinecraft().player != null) {
         this.clientPullSound = new MovingSoundEntity(
            Minecraft.getMinecraft().player, this.getPullSound(), SoundCategory.PLAYERS, 1.0F, this.pullSoundPitch, false
         );
         Minecraft.getMinecraft().getSoundHandler().playSound(this.clientPullSound);
      }
   }

   @Override
   public void effect(EntityPlayer player, World world, double x, double y, double z, double a, double b, double c, double d1, double d2, double d3) {
      player.world
         .playSound(x, y, z, this.getShootSound(), SoundCategory.PLAYERS, 0.7F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + (float)a * 0.5F, false);
   }

   public abstract SoundEvent getShootSound();

   public SoundEvent getPullSound() {
      return Sounds.bow_aim;
   }

   public void onUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
      if (!world.isRemote) {
         boolean[] removePull = new boolean[]{true};
         this.setCanShoot(itemstack, entityIn);
         if (IWeapon.canShoot(itemstack)) {
            EntityPlayer player = (EntityPlayer)entityIn;
            boolean click = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
            NBTHelper.GiveNBTint(itemstack, 0, "pulling");
            int pulling = NBTHelper.GetNBTint(itemstack, "pulling");
            if (this.inUpdate(itemstack, world, entityIn, itemSlot, isSelected, removePull) && player.getHeldItemMainhand() == itemstack) {
               if (click) {
                  if (pulling == 0) {
                     IWeapon.fireBomEffect(this, player, world, -1);
                  }

                  if (pulling == 0 || player.ticksExisted % 10 == 0) {
                     Weapons.setPlayerAnimationOnServer(player, 11, EnumHand.MAIN_HAND);
                     NBTHelper.GiveNBTint(itemstack, -10000, "arrowUsing");
                     ItemStack ammo = this.findAmmo(player);
                     NBTHelper.SetNBTint(itemstack, ammo.isEmpty() ? -10000 : Item.getIdFromItem(ammo.getItem()), "arrowUsing");
                  }

                  if (pulling < this.getCooldownTime(itemstack)) {
                     NBTHelper.AddNBTint(itemstack, 1, "pulling");
                  }

                  removePull[0] = false;
               } else {
                  WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
                  int minPullTime = parameters.geti("min_pull_time");
                  if (pulling >= minPullTime) {
                     boolean creative = player.capabilities.isCreativeMode;
                     ItemStack ammo = this.findAmmo(player);
                     pulling = ForgeEventFactory.onArrowLoose(itemstack, world, player, pulling, !ammo.isEmpty() || creative);
                     if (pulling >= 0 && (!ammo.isEmpty() || creative)) {
                        if (ammo.isEmpty()) {
                           ammo = new ItemStack(Items.ARROW);
                        }

                        float arrowvelocity = this.getArrowVelocity(pulling, itemstack, player);
                        boolean isArrowUnlimit = this.isArrowUnlimit(ammo, world, player, itemstack, pulling);
                        removePull[0] = false;
                        if (this.createAndShootArrow(ammo, world, player, itemstack, pulling, arrowvelocity, isArrowUnlimit)) {
                           IWeapon.fireBomEffect(this, player, world, pulling);
                           itemstack.damageItem(1, player);
                           IWeapon.fireEffect(
                              this,
                              player,
                              world,
                              64.0,
                              player.posX,
                              player.posY,
                              player.posZ,
                              (double)arrowvelocity,
                              0.0,
                              0.0,
                              0.0,
                              0.0,
                              0.0
                           );
                           if (!creative && !isArrowUnlimit) {
                              ammo.shrink(1);
                              if (ammo.isEmpty()) {
                                 player.inventory.deleteStack(ammo);
                              }
                           }

                           player.addStat(StatList.getObjectUseStats(this));
                        }
                     }
                  }
               }
            }
         }

         if (removePull[0]) {
            NBTHelper.SetNBTint(itemstack, 0, "pulling");
            NBTHelper.SetNBTint(itemstack, -10000, "arrowUsing");
            if (entityIn instanceof EntityPlayer) {
               IWeapon.fireBomEffect(this, (EntityPlayer)entityIn, world, -2);
            }
         }
      }
   }

   public float getArrowVelocity(int charge, ItemStack bow, EntityPlayer player) {
      float f = (float)charge / this.getCooldownTime(bow);
      f = (f * f + f * 2.0F) / 3.0F;
      if (f > 1.0F) {
         f = 1.0F;
      }

      return f;
   }

   public void setDamageToArrow(
      EntityArrow entityarrow, ItemStack ammo, World world, EntityPlayer player, ItemStack bow, int pulling, float arrowvelocity, boolean isArrowUnlimit
   ) {
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
      int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, bow);
      entityarrow.setDamage(entityarrow.getDamage() + parameters.getEnchanted("damage", might));
   }

   public boolean createAndShootArrow(ItemStack ammo, World world, EntityPlayer player, ItemStack bow, int pulling, float arrowvelocity, boolean isArrowUnlimit) {
      NBTHelper.SetNBTint(bow, 0, "pulling");
      NBTHelper.SetNBTint(bow, -10000, "arrowUsing");
      ItemArrow itemarrow = (ItemArrow)ammo.getItem();
      EntityArrow entityarrow = itemarrow.createArrow(world, ammo, player);
      if (entityarrow == null) {
         return false;
      } else {
         WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
         entityarrow.shoot(
            player,
            player.rotationPitch,
            player.rotationYaw,
            0.0F,
            arrowvelocity * parameters.get("velocity"),
            parameters.getEnchanted("inaccuracy", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, bow))
         );
         if (arrowvelocity >= this.speedToCritical) {
            entityarrow.setIsCritical(true);
         }

         this.setDamageToArrow(entityarrow, ammo, world, player, bow, pulling, arrowvelocity, isArrowUnlimit);
         int k = GetMOP.floatToIntWithChance(parameters.getEnchanted("knockback", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, bow)), itemRand);
         entityarrow.setKnockbackStrength(k);
         if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, bow) > 0) {
            entityarrow.setFire(100);
         }

         if (isArrowUnlimit || player.capabilities.isCreativeMode) {
            entityarrow.pickupStatus = PickupStatus.CREATIVE_ONLY;
         }

         boolean isspawned = world.spawnEntity(entityarrow);
         this.customizeArrow(entityarrow, ammo, world, player, bow, pulling, arrowvelocity, isArrowUnlimit);
         return isspawned;
      }
   }

   public void customizeArrow(
      EntityArrow arrow, ItemStack ammo, World world, EntityPlayer player, ItemStack bow, int pulling, float arrowvelocity, boolean isArrowUnlimit
   ) {
   }

   public boolean isArrowUnlimit(ItemStack ammo, World world, EntityPlayer player, ItemStack bow, int pulling) {
      return ammo.getItem() instanceof ItemArrow && ((ItemArrow)ammo.getItem()).isInfinite(ammo, bow, player);
   }

   public boolean inUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected, boolean[] removePull) {
      return true;
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.TWO_HANDED;
   }

   public ItemStack findAmmo(EntityPlayer player) {
      if (this.isArrow(player.getHeldItem(EnumHand.OFF_HAND))) {
         return player.getHeldItem(EnumHand.OFF_HAND);
      } else if (this.isArrow(player.getHeldItem(EnumHand.MAIN_HAND))) {
         return player.getHeldItem(EnumHand.MAIN_HAND);
      } else {
         for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
            ItemStack itemstack = player.inventory.getStackInSlot(i);
            if (this.isArrow(itemstack)) {
               return itemstack;
            }
         }

         return ItemStack.EMPTY;
      }
   }

   public boolean isArrow(ItemStack stack) {
      return stack.getItem() instanceof ItemArrow;
   }

   @Override
   public int getItemEnchantability() {
      return 2;
   }

   @Override
   public int getCooldownTime(ItemStack itemstack) {
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(itemstack.getItem());
      int maxPullTime = parameters.geti("max_pull_time");
      int rapidity = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack);
      float mult = 1.0F - 0.2F * rapidity;
      return Math.round(maxPullTime * mult);
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return false;
   }

   @Override
   public boolean getCanShoot(ItemStack itemstack, Entity entity) {
      if (entity instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)entity;
         ItemStack mainH = player.getHeldItemMainhand();
         ItemStack offH = player.getHeldItemOffhand();
         if (offH != itemstack && mainH != itemstack) {
            return true;
         } else {
            switch (this.getWeaponHandleType()) {
               case TWO_HANDED:
                  if (mainH == itemstack) {
                     if (!offH.isEmpty() && !(offH.getItem() instanceof IWeapon) && !this.isArrow(offH)) {
                        return false;
                     }

                     return true;
                  } else if (offH == itemstack) {
                     if (!mainH.isEmpty() && !this.isArrow(mainH)) {
                        return false;
                     }

                     return true;
                  }
               case ONE_HANDED:
                  if (mainH == itemstack) {
                     return true;
                  } else if (offH == itemstack) {
                     if (mainH.getItem() instanceof IWeapon) {
                        if (((IWeapon)mainH.getItem()).getWeaponHandleType() == WeaponHandleType.TWO_HANDED) {
                           return false;
                        }

                        return true;
                     }

                     return true;
                  }
               case SEMI_ONE_HANDED:
                  if (mainH == itemstack) {
                     return true;
                  } else if (offH == itemstack) {
                     if (mainH.getItem() instanceof IWeapon) {
                        if (((IWeapon)mainH.getItem()).getWeaponHandleType() == WeaponHandleType.TWO_HANDED) {
                           return false;
                        }

                        if (((IWeapon)mainH.getItem()).getWeaponHandleType() == WeaponHandleType.SEMI_ONE_HANDED) {
                           return false;
                        }

                        return true;
                     }

                     return true;
                  }
               default:
                  return false;
            }
         }
      } else {
         return false;
      }
   }

   @SideOnly(Side.CLIENT)
   public static void renderArrowInBow(ItemStack itemstack, float progress, float partialTicksTeisr, float arrowOffset) {
      int arrow = NBTHelper.GetNBTint(itemstack, "arrowUsing");
      if (arrow != -10000) {
         Item itemarrow = Item.getItemById(arrow);
         if (itemarrow != null) {
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.43F + progress * arrowOffset, 0.47F, 0.04F);
            GlStateManager.rotate(135.0F, 0.0F, -0.05F, 1.0F);
            float scale = 2.0F;
            GlStateManager.scale(scale, scale, scale);
            GlStateManager.enableBlend();
            AbstractMobModel.alphaGlowDisable();
            Minecraft.getMinecraft().getRenderItem().renderItem(new ItemStack(itemarrow), TransformType.GROUND);
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
         }
      }
   }
}
