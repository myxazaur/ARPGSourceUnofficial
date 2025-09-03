//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.main.Booom;
import com.Vivern.Arpg.main.ColorConverters;
import com.Vivern.Arpg.main.DeathEffects;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Keys;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.WeaponDamage;
import com.Vivern.Arpg.main.WeaponParameters;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.renders.BulletParticle;
import com.Vivern.Arpg.renders.SparkleSubparticle;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Submachine extends ItemWeapon {
   public static int maxammo = 40;

   public Submachine() {
      this.setRegistryName("submachine");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("submachine");
      this.setMaxDamage(1400);
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
         int reuse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.REUSE, itemstack);
         int heat = NBTHelper.GetNBTint(itemstack, "heat");
         if (heat > 0) {
            NBTHelper.AddNBTint(itemstack, -1 - reuse, "heat");
         }

         if (IWeapon.canShoot(itemstack)) {
            EntityPlayer player = (EntityPlayer)entityIn;
            boolean click = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
            boolean click2 = Keys.isKeyPressed(player, Keys.SECONDARYATTACK);
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
            int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
            int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, itemstack);
            this.decreaseReload(itemstack, player);
            boolean hascooldown = player.getCooldownTracker().hasCooldown(this);
            boolean hascooldown2 = player.getCooldownTracker().hasCooldown(ItemsRegister.EXP);
            NBTHelper.GiveNBTint(itemstack, 0, "heat");
            int ammo = NBTHelper.GetNBTint(itemstack, "ammo");
            EnumHand hand = click && player.getHeldItemMainhand() == itemstack
               ? EnumHand.MAIN_HAND
               : (click2 && player.getHeldItemOffhand() == itemstack ? EnumHand.OFF_HAND : null);
            if (hand != null && heat < 2000) {
               if (ammo > 0 && this.isReloaded(itemstack)) {
                  if ((!hascooldown || hand != EnumHand.MAIN_HAND) && (!hascooldown2 || hand != EnumHand.OFF_HAND)) {
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
                        Sounds.submachine_shoot,
                        SoundCategory.AMBIENT,
                        0.9F,
                        0.9F + itemRand.nextFloat() / 5.0F
                     );
                     player.getCooldownTracker().setCooldown((Item)(hand == EnumHand.MAIN_HAND ? this : ItemsRegister.EXP), this.getCooldownTime(itemstack));
                     player.addStat(StatList.getObjectUseStats(this));
                     IWeapon.fireBomEffect(this, player, world, 0);
                     Weapons.setPlayerAnimationOnServer(player, 5, hand);
                     int addheat = parameters.geti("heat_per_shoot");
                     NBTHelper.AddNBTint(itemstack, addheat, "heat");
                     if (addheat + heat >= 2000) {
                        world.playSound(
                           (EntityPlayer)null,
                           player.posX,
                           player.posY,
                           player.posZ,
                           SoundEvents.BLOCK_FIRE_EXTINGUISH,
                           SoundCategory.AMBIENT,
                           0.6F,
                           0.9F + itemRand.nextFloat() / 5.0F
                        );
                     }

                     boolean isFired = heat > 1500;
                     if (!player.capabilities.isCreativeMode) {
                        this.addAmmo(ammo, itemstack, -1);
                        itemstack.damageItem(1, player);
                     }

                     double edist = parameters.getEnchanted("distance", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack));
                     double damageRadius = 0.25;
                     float inaccuracy = parameters.getEnchanted("inaccuracy", acc);
                     float rotP = player.rotationPitch + (float)itemRand.nextGaussian() * inaccuracy;
                     float rotY = player.rotationYaw + (float)itemRand.nextGaussian() * inaccuracy;
                     Vec3d vec = GetMOP.RotatedPosRayTrace(edist, 1.0F, player, 0.25, 0.2, rotP, rotY);
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
                           0.9F + itemRand.nextFloat() / 5.0F
                        );
                     }

                     float wdamage = parameters.getEnchanted("damage", might)
                        + damageadd * parameters.get("bullet_damage")
                        + heat * parameters.get("per_heat_damage");
                     float wknockback = parameters.getEnchanted("knockback", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, itemstack))
                        + knockbackadd * parameters.get("bullet_knockback");
                     if (!list.isEmpty()) {
                        for (Entity entity : list) {
                           if (Team.checkIsOpponent(player, entity)) {
                              Weapons.dealDamage(
                                 new WeaponDamage(itemstack, player, null, false, true, player.getPositionEyes(1.0F), WeaponDamage.bullet),
                                 wdamage,
                                 player,
                                 entity,
                                 true,
                                 wknockback,
                                 player.posX,
                                 player.posY,
                                 player.posZ
                              );
                              entity.hurtResistantTime = 0;
                              if (isFired) {
                                 entity.setFire(8);
                              }

                              collidesWithAny = true;
                              if (nonullbullet) {
                                 bullet.onDamageCause(world, entity, player, null);
                              }

                              if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).getHealth() <= 0.0F && itemRand.nextFloat() < 0.2) {
                                 DeathEffects.applyDeathEffect(entity, DeathEffects.DE_DISMEMBER);
                              }
                           }
                        }
                     }

                     int c = nonullbullet ? ColorConverters.RGBtoDecimal(bullet.colorR, bullet.colorG, bullet.colorB) : 16777215;
                     IBlockState dustState = GetMOP.firstBlockStateContains(world, aabb, GetMOP.SOLID_BLOCKS);
                     Vec3d shootPoint = Weapons.getPlayerShootPoint(player, hand);
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
                        collidesWithAny ? (isFired ? 3 : 2) : (isFired ? 1 : 0)
                     );
                  }
               } else if (this.initiateBulletReload(itemstack, player, ItemsRegister.SUBMACHINECLIP, maxammo, true)) {
                  world.playSound(
                     (EntityPlayer)null,
                     player.posX,
                     player.posY,
                     player.posZ,
                     Sounds.pistol_rel,
                     SoundCategory.AMBIENT,
                     0.7F,
                     0.95F + itemRand.nextFloat() / 10.0F
                  );
                  Weapons.setPlayerAnimationOnServer(player, 6, hand);
               }
            }
         }
      }
   }

   @Override
   public void effect(EntityPlayer player, World world, double x, double y, double z, double a, double b, double c, double d1, double d2, double d3) {
      boolean collidesWithAny = d3 == 3.0 || d3 == 2.0;
      boolean fired = d3 == 3.0 || d3 == 1.0;
      Vec3d from = new Vec3d(x, y, z);
      Vec3d to = new Vec3d(a, b, c);
      float dist = (float)from.distanceTo(to);
      Vec3d col = ColorConverters.DecimaltoRGB((int)d1);
      BulletParticle part = new BulletParticle(
         world,
         from,
         to,
         0.1F,
         (int)MathHelper.clamp(dist / 3.0F, 2.0F, 8.0F),
         7,
         dist,
         (float)col.x,
         (float)col.y,
         (float)col.z,
         collidesWithAny
      );
      part.Red2 = 0.75F;
      part.Green2 = 0.75F;
      part.Blue2 = 0.5F;
      part.blockDustId = (int)d2;
      world.spawnEntity(part);
      if (fired) {
         Vec3d difference = to.subtract(from);
         Vec3d differenceNorm = difference.normalize().scale(0.15F);
         int amount = 7 + itemRand.nextInt(7);

         for (int i = 0; i < amount; i++) {
            float randF = itemRand.nextFloat();
            SparkleSubparticle sparklepart = new SparkleSubparticle(
               from.x + difference.x * randF,
               from.y + difference.y * randF,
               from.z + difference.z * randF,
               0.02F,
               40 + itemRand.nextInt(20),
               (float)differenceNorm.x + (itemRand.nextFloat() - 0.5F) * 0.04F,
               (float)differenceNorm.y + (itemRand.nextFloat() - 0.5F) * 0.04F,
               (float)differenceNorm.z + (itemRand.nextFloat() - 0.5F) * 0.04F,
               0.0F
            );
            SparkleSubparticle.particles.add(sparklepart);
         }

         if (collidesWithAny) {
            if (itemRand.nextFloat() < 0.5F) {
               world.spawnParticle(EnumParticleTypes.LAVA, a, b, c, 0.0, 0.0, 0.0, new int[0]);
            }

            for (int i = 0; i < 4; i++) {
               float randF = itemRand.nextFloat();
               SparkleSubparticle sparklepart = new SparkleSubparticle(
                  a,
                  b,
                  c,
                  0.02F,
                  40 + itemRand.nextInt(40),
                  (itemRand.nextFloat() - 0.5F) * 0.1F,
                  (itemRand.nextFloat() - 0.5F) * 0.1F,
                  (itemRand.nextFloat() - 0.5F) * 0.1F,
                  0.0F
               );
               SparkleSubparticle.particles.add(sparklepart);
            }
         }
      }
   }

   @SideOnly(Side.CLIENT)
   @Override
   public void bom(int param) {
      Booom.lastTick = 11;
      Booom.frequency = -0.285F;
      Booom.x = 1.0F;
      Booom.y = (float)itemRand.nextGaussian() / 10.0F;
      Booom.z = (float)itemRand.nextGaussian() / 10.0F;
      Booom.power = 0.28F;
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
      return WeaponHandleType.ONE_HANDED;
   }

   @Override
   public int getCooldownTime(ItemStack itemstack) {
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(itemstack.getItem());
      int rapidity = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack);
      return parameters.getEnchantedi("cooldown", rapidity)
         - (
            EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack) > 0
               ? Math.round(Math.max(NBTHelper.GetNBTint(itemstack, "heat") * parameters.get("special_heat_overclock") - 1.0F, 0.0F))
               : 0
         );
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
