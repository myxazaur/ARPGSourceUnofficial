package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.entity.CeratargetShoot;
import com.Vivern.Arpg.main.Booom;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.Keys;
import com.Vivern.Arpg.main.Mana;
import com.Vivern.Arpg.main.MovingSoundEntity;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.WeaponParameters;
import com.Vivern.Arpg.main.Weapons;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Ceratarget extends ItemWeapon {
   public static int maxammo = 18;

   public Ceratarget() {
      this.setRegistryName("ceratarget");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("ceratarget");
      this.setMaxDamage(5300);
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

   public void onUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
      if (!world.isRemote) {
         this.setCanShoot(itemstack, entityIn);
         if (IWeapon.canShoot(itemstack)) {
            EntityPlayer player = (EntityPlayer)entityIn;
            this.decreaseReload(itemstack, player);
            boolean click = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
            float mana = Mana.getMana(player);
            float spee = Mana.getManaSpeed(player);
            float power = Mana.getMagicPowerMax(player);
            int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
            int sor = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SORCERY, itemstack);
            int ammo = NBTHelper.GetNBTint(itemstack, "ammo");
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
            if (player.getHeldItemMainhand() == itemstack) {
               if (click) {
                  if (ammo > 0 && this.isReloaded(itemstack)) {
                     if (!player.getCooldownTracker().hasCooldown(this)) {
                        world.playSound(
                           (EntityPlayer)null,
                           player.posX,
                           player.posY,
                           player.posZ,
                           Sounds.ceratarget,
                           SoundCategory.AMBIENT,
                           0.9F,
                           0.9F + itemRand.nextFloat() / 5.0F
                        );
                        player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
                        player.addStat(StatList.getObjectUseStats(this));
                        IWeapon.fireBomEffect(this, player, world, 0);
                        Weapons.setPlayerAnimationOnServer(player, 13, EnumHand.MAIN_HAND);
                        Vec3d vec3d = player.getPositionEyes(1.0F);
                        Vec3d vec3d1 = player.getLook(1.0F);
                        Vec3d vec3d2 = vec3d.add(vec3d1.x * 16.0, vec3d1.y * 16.0, vec3d1.z * 16.0);
                        RayTraceResult resul = world.rayTraceBlocks(vec3d, vec3d2, false);
                        CeratargetShoot projectile = new CeratargetShoot(world, player, itemstack, power);
                        float displAngle = parameters.getEnchanted("displace_angle", acc);
                        float yawAdd = (itemRand.nextFloat() + 2.0F) * (itemRand.nextFloat() < 0.5F ? -displAngle : displAngle);
                        projectile.followPoint = resul != null && resul.hitVec != null && resul.typeOfHit != Type.MISS
                           ? resul.hitVec
                           : vec3d2;
                        Weapons.shoot(
                           projectile,
                           EnumHand.MAIN_HAND,
                           player,
                           player.rotationPitch,
                           player.rotationYaw + yawAdd,
                           0.0F,
                           parameters.get("velocity"),
                           parameters.getEnchanted("inaccuracy", acc),
                           -0.2F,
                           0.0F,
                           0.6F,
                           0.1F
                        );
                        projectile.canBlow = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack) == 0;
                        world.spawnEntity(projectile);
                        if (!player.capabilities.isCreativeMode) {
                           if (itemRand.nextFloat()
                              < parameters.getEnchanted("ammo_consume_chance", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.REUSE, itemstack))) {
                              this.addAmmo(ammo, itemstack, -1);
                           }

                           itemstack.damageItem(1, player);
                        }
                     }
                  } else if (this.initiateReload(itemstack, player, null, maxammo)) {
                     IWeapon.fireEffect(this, player, world, 64.0, (double)player.getEntityId(), 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
                  }
               }

               if (Keys.isKeyPressed(player, Keys.SECONDARYATTACK) && EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack) > 0) {
                  AxisAlignedBB aabb = player.getEntityBoundingBox().grow(48.0);

                  for (CeratargetShoot shoot : world.getEntitiesWithinAABB(CeratargetShoot.class, aabb)) {
                     if (shoot.getThrower() == player) {
                        shoot.expl();
                        shoot.setDead();
                     }
                  }
               }
            }
         }
      }
   }

   @Override
   public void decreaseReload(ItemStack itemstack, EntityPlayer player) {
      if (player.getHeldItemMainhand() == itemstack || player.getHeldItemOffhand() == itemstack || this.autoReload(itemstack)) {
         int reltime = this.getReloadTime(itemstack);
         int rel = NBTHelper.GetNBTint(itemstack, "reload_time");
         if (rel > 0 && rel < reltime) {
            if (rel > reltime / 2) {
               WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
               float mananeed1 = parameters.getEnchanted("manacost", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SORCERY, itemstack)) / reltime * 2.0F;
               if (Mana.getMana(player) > mananeed1) {
                  NBTHelper.AddNBTint(itemstack, -1, "reload_time");
                  if (!player.capabilities.isCreativeMode) {
                     Mana.changeMana(player, -mananeed1);
                     Mana.setManaSpeed(player, 0.001F);
                  }
               }
            } else {
               NBTHelper.AddNBTint(itemstack, -1, "reload_time");
            }
         }
      }
   }

   @Override
   public boolean initiateReload(ItemStack itemstack, EntityPlayer player, Item itemAmmo, int maxAmmo) {
      if (!player.getCooldownTracker().hasCooldown(itemstack.getItem())) {
         NBTHelper.GiveNBTint(itemstack, 0, "ammo");
         if (NBTHelper.GetNBTint(itemstack, "ammo") == 0) {
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
            float mananeed1 = parameters.getEnchanted("manacost", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SORCERY, itemstack));
            if (Mana.getMana(player) > mananeed1) {
               this.startReload(itemstack);
               NBTHelper.SetNBTint(itemstack, maxAmmo, "ammo");
               return true;
            }
         } else if (this.isReloadNeed(itemstack)) {
            this.startReload(itemstack);
            return true;
         }
      }

      return false;
   }

   @SideOnly(Side.CLIENT)
   @Override
   public void bom(int param) {
      Booom.lastTick = 14;
      Booom.frequency = -0.225F;
      Booom.x = 1.0F;
      Booom.y = (itemRand.nextFloat() - 0.5F) / 4.0F;
      Booom.z = (itemRand.nextFloat() - 0.5F) / 4.0F;
      Booom.power = 0.3F;
   }

   @Override
   public void effect(EntityPlayer player, World world, double x, double y, double z, double a, double b, double c, double d1, double d2, double d3) {
      Entity en = world.getEntityByID((int)x);
      if (en != null) {
         MovingSoundEntity movingsound = new MovingSoundEntity(
            en, Sounds.ceratarget_rel, SoundCategory.PLAYERS, 0.7F, 0.975F + itemRand.nextFloat() / 20.0F, false
         );
         Minecraft.getMinecraft().getSoundHandler().playSound(movingsound);
      }
   }

   @Override
   public float getAdditionalDurabilityBar(ItemStack itemstack) {
      return MathHelper.clamp((float)NBTHelper.GetNBTint(itemstack, "ammo") / maxammo, 0.0F, 1.0F);
   }

   @Override
   public boolean hasAdditionalDurabilityBar(ItemStack itemstack) {
      return true;
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return false;
   }

   @Override
   public boolean autoReload(ItemStack itemstack) {
      return true;
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.TWO_HANDED;
   }

   @Override
   public int getItemEnchantability() {
      return 2;
   }
}
