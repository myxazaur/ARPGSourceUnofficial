//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.main.Booom;
import com.Vivern.Arpg.main.ColorConverters;
import com.Vivern.Arpg.main.DeathEffects;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.FindAmmo;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Keys;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.WeaponDamage;
import com.Vivern.Arpg.main.WeaponParameters;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.mobs.HostileProjectiles;
import com.Vivern.Arpg.renders.BulletParticle;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CooledRifle extends ItemWeapon {
   public static int maxammo = 28;

   public CooledRifle() {
      this.setRegistryName("cooled_rifle");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("cooled_rifle");
      this.setMaxDamage(3000);
      this.setMaxStackSize(1);
   }

   public float getXpRepairRatio(ItemStack stack) {
      return 4.0F;
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

   @Override
   public boolean isReloaded(ItemStack itemstack) {
      return NBTHelper.GetNBTint(itemstack, "reload_time") < 1;
   }

   public void onUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
      if (!world.isRemote) {
         this.setCanShoot(itemstack, entityIn);
         if (IWeapon.canShoot(itemstack)) {
            EntityPlayer player = (EntityPlayer)entityIn;
            this.decreaseReload(itemstack, player);
            boolean click = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
            boolean click2 = Keys.isKeyPressed(player, Keys.SECONDARYATTACK);
            int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
            int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, itemstack);
            int impulse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, itemstack);
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
            int ammo = NBTHelper.GetNBTint(itemstack, "ammo");
            if (player.getHeldItemMainhand() == itemstack) {
               if (click2 && NBTHelper.GetNBTboolean(itemstack, "shotgun") && this.isReloaded(itemstack)) {
                  world.playSound(
                     (EntityPlayer)null,
                     player.posX,
                     player.posY,
                     player.posZ,
                     Sounds.pump_shotgun,
                     SoundCategory.AMBIENT,
                     1.3F,
                     0.9F + itemRand.nextFloat() / 5.0F
                  );
                  IWeapon.fireBomEffect(this, player, world, 1);
                  Weapons.setPlayerAnimationOnServer(player, 3, EnumHand.MAIN_HAND);
                  itemstack.damageItem(1, player);
                  NBTHelper.SetNBTboolean(itemstack, false, "shotgun");
                  int shots = parameters.getEnchantedi("shots", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.REUSE, itemstack));

                  for (int j = 0; j < shots; j++) {
                     HostileProjectiles.BulletCooled projectile = new HostileProjectiles.BulletCooled(world, player);
                     Weapons.shoot(
                        projectile,
                        EnumHand.MAIN_HAND,
                        player,
                        player.rotationPitch - 3.0F,
                        player.rotationYaw,
                        0.0F,
                        parameters.get("velocity"),
                        parameters.getEnchanted("inaccuracy_shotgun", acc),
                        -0.2F,
                        0.5F,
                        0.5F
                     );
                     projectile.damage = parameters.getEnchanted("damage_shotgun", might);
                     projectile.knockback = parameters.getEnchanted("knockback_shotgun", impulse);
                     projectile.weaponstack = itemstack;
                     String bulletname = NBTHelper.GetNBTstring(itemstack, "bulletshotgun");
                     ItemBullet bullet = ItemBullet.getItemBulletFromString(bulletname);
                     projectile.bullet = bullet;
                     world.spawnEntity(projectile);
                  }
               }

               if (click) {
                  if (ammo > 0 && this.isReloaded(itemstack)) {
                     if (!player.getCooldownTracker().hasCooldown(this)) {
                        String bulletname = NBTHelper.GetNBTstring(itemstack, "bullet");
                        ItemBullet bullet = ItemBullet.getItemBulletFromString(bulletname);
                        boolean nonullbullet = bullet != null;
                        float damageadd = 0.0F;
                        float knockbackadd = 0.0F;
                        if (nonullbullet) {
                           damageadd = bullet.damage;
                           knockbackadd = bullet.knockback;
                        }

                        world.playSound(
                           (EntityPlayer)null,
                           player.posX,
                           player.posY,
                           player.posZ,
                           Sounds.cooled_rifle,
                           SoundCategory.AMBIENT,
                           0.9F,
                           0.9F + itemRand.nextFloat() / 5.0F
                        );
                        player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
                        player.addStat(StatList.getObjectUseStats(this));
                        IWeapon.fireBomEffect(this, player, world, 0);
                        Weapons.setPlayerAnimationOnServer(player, 13, EnumHand.MAIN_HAND);
                        if (!player.capabilities.isCreativeMode) {
                           this.addAmmo(ammo, itemstack, -1);
                           itemstack.damageItem(1, player);
                        }

                        double edist = parameters.getEnchanted("distance", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack));
                        double damageRadius = 0.2;
                        float inaccuracy = parameters.getEnchanted("inaccuracy", acc);
                        float rotP = player.rotationPitch + (float)itemRand.nextGaussian() * inaccuracy;
                        float rotY = player.rotationYaw + (float)itemRand.nextGaussian() * inaccuracy;
                        Vec3d vec = GetMOP.RotatedPosRayTrace(edist, 1.0F, player, 0.2, 0.15, rotP, rotY);
                        if (nonullbullet) {
                           bullet.onImpact(world, player, vec.x, vec.y, vec.z, null, null);
                        }

                        boolean collidesWithAny = false;
                        AxisAlignedBB aabb = new AxisAlignedBB(
                           vec.x - damageRadius,
                           vec.y - damageRadius,
                           vec.z - damageRadius,
                           vec.x + damageRadius,
                           vec.y + damageRadius,
                           vec.z + damageRadius
                        );
                        List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(player, aabb);
                        if (world.collidesWithAnyBlock(aabb)) {
                           collidesWithAny = true;
                           world.playSound(
                              (EntityPlayer)null,
                              vec.x,
                              vec.y,
                              vec.z,
                              Sounds.bullet,
                              SoundCategory.AMBIENT,
                              0.9F,
                              0.97F + itemRand.nextFloat() / 5.0F
                           );
                        }

                        if (!list.isEmpty()) {
                           for (Entity entity : list) {
                              if (Team.checkIsOpponent(player, entity)) {
                                 Weapons.dealDamage(
                                    new WeaponDamage(itemstack, player, null, false, true, player, WeaponDamage.bullet),
                                    parameters.getEnchanted("damage", might) + damageadd * parameters.get("bullet_damage"),
                                    player,
                                    entity,
                                    true,
                                    parameters.getEnchanted("knockback", impulse) + knockbackadd * parameters.get("bullet_knockback"),
                                    player.posX,
                                    player.posY,
                                    player.posZ
                                 );
                                 entity.hurtResistantTime = 0;
                                 collidesWithAny = true;
                                 if (nonullbullet) {
                                    bullet.onDamageCause(world, entity, player, null);
                                 }

                                 DeathEffects.applyDeathEffect(entity, DeathEffects.DE_DISMEMBER, 0.1F);
                              }
                           }
                        }

                        int c = nonullbullet ? ColorConverters.RGBtoDecimal(bullet.colorR, bullet.colorG, bullet.colorB) : 16777215;
                        IBlockState dustState = GetMOP.firstBlockStateContains(world, aabb, GetMOP.SOLID_BLOCKS);
                        Vec3d shootPoint = Weapons.getPlayerShootPoint(player, EnumHand.MAIN_HAND);
                        IWeapon.fireEffect(
                           this,
                           player,
                           world,
                           64.0,
                           shootPoint.x,
                           shootPoint.y,
                           shootPoint.z,
                           vec.x,
                           vec.y,
                           vec.z,
                           (double)c,
                           dustState == null ? -1.0 : Block.getStateId(dustState),
                           collidesWithAny ? 1.0 : 0.0
                        );
                     }
                  } else if (this.initiateBulletReload(itemstack, player, ItemsRegister.COOLEDRIFLECLIP, maxammo, true)) {
                     world.playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.cooled_rifle_rel,
                        SoundCategory.AMBIENT,
                        0.7F,
                        1.0F + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RELOADING, itemstack) * 0.2F
                     );
                     Weapons.setPlayerAnimationOnServer(player, 28, EnumHand.MAIN_HAND);
                     NBTHelper.GiveNBTboolean(itemstack, false, "shotgun");
                     this.loadShotgun(itemstack, player, new ItemStack(ItemsRegister.BUCKSHOT, 1), 1, false);
                  }
               }
            }
         }
      }
   }

   public boolean loadShotgun(ItemStack itemstack, EntityPlayer player, ItemStack itemClip, int maxAmmo, boolean returnEmptyClip) {
      if (!player.getCooldownTracker().hasCooldown(itemstack.getItem()) && !NBTHelper.GetNBTboolean(itemstack, "shotgun")) {
         int clipstackIndex = FindAmmo.getNonEmptyClipStack(player.inventory, itemClip.getItem(), itemClip.getCount());
         if (clipstackIndex < 0) {
            return false;
         } else {
            ItemStack clipstack = (ItemStack)player.inventory.mainInventory.get(clipstackIndex);
            if (clipstack.isEmpty()) {
               return false;
            } else {
               String nbtname = NBTHelper.GetNBTstring(clipstack, "bullet");
               NBTHelper.GiveNBTstring(itemstack, nbtname, "bulletshotgun");
               NBTHelper.SetNBTstring(itemstack, nbtname, "bulletshotgun");
               player.inventory.clearMatchingItems(itemClip.getItem(), -1, itemClip.getCount(), clipstack.getTagCompound());
               NBTHelper.SetNBTboolean(itemstack, true, "shotgun");
               if (returnEmptyClip) {
                  ItemStack newstack = new ItemStack(itemClip.getItem(), itemClip.getCount());
                  if (!player.addItemStackToInventory(newstack)) {
                     player.world
                        .spawnEntity(new EntityItem(player.world, player.posX, player.posY, player.posZ, newstack));
                  }
               }

               return true;
            }
         }
      } else {
         return false;
      }
   }

   @Override
   public void effect(EntityPlayer player, World world, double x, double y, double z, double a, double b, double c, double d1, double d2, double d3) {
      Vec3d from = new Vec3d(x, y, z);
      Vec3d to = new Vec3d(a, b, c);
      float dist = (float)from.distanceTo(to);
      Vec3d col = ColorConverters.DecimaltoRGB((int)d1);
      BulletParticle part = new BulletParticle(
         world,
         from,
         to,
         0.06F,
         (int)MathHelper.clamp(dist / 4.0F, 2.0F, 7.0F),
         4,
         dist,
         (float)col.x,
         (float)col.y,
         (float)col.z,
         d3 > 0.0
      );
      part.Red2 = 0.2F;
      part.Green2 = 0.7F;
      part.Blue2 = 1.0F;
      part.smokeRed = 0.42F;
      part.smokeGreen = 0.63F;
      part.smokeBlue = 0.8F;
      part.blockDustId = (int)d2;
      world.spawnEntity(part);
   }

   @SideOnly(Side.CLIENT)
   @Override
   public void bom(int param) {
      Booom.lastTick = 4;
      Booom.frequency = -0.8F;
      Booom.x = 1.0F;
      Booom.y = (float)itemRand.nextGaussian() / 10.0F;
      Booom.z = (float)itemRand.nextGaussian() / 10.0F;
      Booom.power = 0.19F;
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
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.TWO_HANDED;
   }

   @Override
   public int getCooldownTime(ItemStack itemstack) {
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(itemstack.getItem());
      int rapidity = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack);
      return GetMOP.floatToIntWithChance(parameters.getEnchanted("cooldown", rapidity), itemRand);
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return false;
   }

   @Override
   public int getItemEnchantability() {
      return 2;
   }

   @Override
   public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
      return enchantment.type == EnchantmentInit.enchantmentTypeWeapon;
   }
}
