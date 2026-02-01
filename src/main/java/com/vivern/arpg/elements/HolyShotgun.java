package com.vivern.arpg.elements;

import com.vivern.arpg.main.Booom;
import com.vivern.arpg.main.ColorConverters;
import com.vivern.arpg.main.DeathEffects;
import com.vivern.arpg.main.EnchantmentInit;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.Keys;
import com.vivern.arpg.main.NBTHelper;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.Team;
import com.vivern.arpg.main.WeaponDamage;
import com.vivern.arpg.main.WeaponParameters;
import com.vivern.arpg.main.Weapons;
import com.vivern.arpg.potions.PotionEffects;
import com.vivern.arpg.renders.BulletParticle;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
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

public class HolyShotgun extends ItemWeapon {
   public static int maxammo = 2;

   public HolyShotgun() {
      this.setRegistryName("holy_shotgun");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("holy_shotgun");
      this.setMaxDamage(1700);
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
            boolean click = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
            boolean click2 = Keys.isKeyPressed(player, Keys.SECONDARYATTACK);
            int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
            int reuse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.REUSE, itemstack);
            int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, itemstack);
            int witchery = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.WITCHERY, itemstack);
            int impulse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, itemstack);
            this.decreaseReload(itemstack, player);
            boolean hascooldown = player.getCooldownTracker().hasCooldown(this);
            int ammo = NBTHelper.GetNBTint(itemstack, "ammo");
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
            if ((click || click2) && player.getHeldItemMainhand() == itemstack) {
               if (ammo <= 0 || !this.isReloaded(itemstack)) {
                  if (this.initiateBulletReload(itemstack, player, new ItemStack(ItemsRegister.BUCKSHOT, 2), maxammo, false)) {
                     world.playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.holy_shotgun_rel,
                        SoundCategory.AMBIENT,
                        0.8F,
                        1.0F
                     );
                     Weapons.setPlayerAnimationOnServer(player, 20, EnumHand.MAIN_HAND);
                  }
               } else if (!hascooldown) {
                  int doubleMult = click && ammo > 1 ? 2 : 1;
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
                     Sounds.holy_shotgun,
                     SoundCategory.AMBIENT,
                     1.3F,
                     0.9F + itemRand.nextFloat() / 5.0F + (doubleMult > 1 ? 0.0F : 0.2F)
                  );
                  player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
                  player.addStat(StatList.getObjectUseStats(this));
                  IWeapon.fireBomEffect(this, player, world, doubleMult);
                  Weapons.setPlayerAnimationOnServer(player, 3, EnumHand.MAIN_HAND);
                  if (!player.capabilities.isCreativeMode) {
                     this.addAmmo(ammo, itemstack, -1 * doubleMult);
                     itemstack.damageItem(1 * doubleMult, player);
                  }

                  double edist = parameters.getEnchanted("distance", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack));
                  double damageRadius = 0.3;
                  int seed = itemRand.nextInt();
                  Random random1 = new Random(seed);
                  int c = nonullbullet ? ColorConverters.RGBtoDecimal(bullet.colorR, bullet.colorG, bullet.colorB) : 16777215;
                  int impacts = 0;
                  int amount = Math.min(doubleMult * parameters.getEnchantedi("shots", reuse), 31);

                  for (int i = 0; i < amount; i++) {
                     boolean collideWithAny = false;
                     float inaccuracy = parameters.getEnchanted("inaccuracy", acc);
                     float inaccuracy2 = parameters.getEnchanted("inaccuracy_horizontal", acc);
                     float rotP = player.rotationPitch + (float)random1.nextGaussian() * inaccuracy;
                     float rotY = player.rotationYaw + (float)random1.nextGaussian() * inaccuracy2;
                     Vec3d vec = GetMOP.RotatedPosRayTrace(edist, 1.0F, player, 0.3, 0.2, rotP, rotY);
                     if (nonullbullet && i % 2 == 0) {
                        bullet.onImpact(world, player, vec.x, vec.y, vec.z, null, null);
                     }

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
                        world.playSound(
                           (EntityPlayer)null,
                           vec.x,
                           vec.y,
                           vec.z,
                           Sounds.bullet,
                           SoundCategory.AMBIENT,
                           0.5F,
                           0.9F + itemRand.nextFloat() / 5.0F
                        );
                        collideWithAny = true;
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
                              if (nonullbullet) {
                                 bullet.onDamageCause(world, entity, player, null);
                              }

                              entity.hurtResistantTime = 0;
                              collideWithAny = true;
                              DeathEffects.applyDeathEffect(entity, DeathEffects.DE_DISMEMBER, 0.07F);
                              if (itemRand.nextFloat() < 0.2F) {
                                 parameters.mixPotion(
                                    entity,
                                    "broken_armor",
                                    PotionEffects.BROKEN_ARMOR,
                                    witchery,
                                    Weapons.EnumPotionMix.GREATEST,
                                    Weapons.EnumPotionMix.WITH_MAXIMUM,
                                    Weapons.EnumMathOperation.NONE,
                                    Weapons.EnumMathOperation.PLUS
                                 );
                              }
                           }
                        }
                     }

                     if (collideWithAny) {
                        impacts |= 1 << i;
                     }
                  }

                  IWeapon.fireEffect(
                     this,
                     player,
                     world,
                     64.0,
                     (double)player.getEntityId(),
                     (double)seed,
                     (double)acc,
                     (double)amount,
                     (double)c,
                     (double)impacts,
                     edist,
                     0.0,
                     0.0
                  );
               }
            }
         }
      }
   }

   @Override
   public void effect(EntityPlayer clientplayer, World world, double x, double y, double z, double a, double b, double c, double d1, double d2, double d3) {
      int impacts = (int)c;
      Entity playe = world.getEntityByID((int)x);
      if (playe instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)playe;
         WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
         Vec3d from = Weapons.getPlayerShootPoint(player, EnumHand.MAIN_HAND);
         Random random1 = new Random((long)y);
         double edist = d1;
         Vec3d col = ColorConverters.DecimaltoRGB((int)b);

         for (int i = 0; i < Math.min(a, 31.0); i++) {
            float inaccuracy = parameters.getEnchanted("inaccuracy", (int)z);
            float inaccuracy2 = parameters.getEnchanted("inaccuracy_horizontal", (int)z);
            float rotP = player.rotationPitch + (float)random1.nextGaussian() * inaccuracy;
            float rotY = player.rotationYaw + (float)random1.nextGaussian() * inaccuracy2;
            Vec3d to = GetMOP.RotatedPosRayTrace(edist, 1.0F, player, 0.3, 0.2, rotP, rotY);
            float dist = (float)from.distanceTo(to);
            boolean full = (impacts & 1 << i) > 0;
            BulletParticle part = new BulletParticle(
               world,
               from,
               to,
               0.08F,
               (int)MathHelper.clamp(dist / 3.0F, 2.0F, 6.0F),
               5,
               dist,
               (float)col.x,
               (float)col.y,
               (float)col.z,
               full
            );
            part.Red2 = 0.75F;
            part.Green2 = 0.73F;
            part.Blue2 = 0.6F;
            if (full) {
               double damageRadius = 0.25;
               AxisAlignedBB aabb = new AxisAlignedBB(
                  to.x - damageRadius,
                  to.y - damageRadius,
                  to.z - damageRadius,
                  to.x + damageRadius,
                  to.y + damageRadius,
                  to.z + damageRadius
               );
               IBlockState dustState = GetMOP.firstBlockStateContains(world, aabb, GetMOP.SOLID_BLOCKS);
               if (dustState != null) {
                  part.blockDustId = Block.getStateId(dustState);
               }
            }

            world.spawnEntity(part);
         }
      }
   }

   @SideOnly(Side.CLIENT)
   @Override
   public void bom(int param) {
      Booom.lastTick = 16;
      Booom.frequency = -0.196F;
      Booom.x = 1.0F;
      Booom.y = 0.0F;
      Booom.z = 0.0F;
      Booom.power = 0.42F * param;
      Booom.FOVlastTick = 6;
      Booom.FOVfrequency = -0.5F;
      Booom.FOVpower = 0.03F;
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
   public boolean autoCooldown(ItemStack itemstack) {
      return false;
   }
}
