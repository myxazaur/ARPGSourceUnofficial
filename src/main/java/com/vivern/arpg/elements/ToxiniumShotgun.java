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
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ToxiniumShotgun extends ItemWeapon {
   static ResourceLocation texture = new ResourceLocation("arpg:textures/bullet_trace2.png");
   static ResourceLocation sparkle3 = new ResourceLocation("arpg:textures/sparkle3.png");
   public static int maxammo = 8;

   public ToxiniumShotgun() {
      this.setRegistryName("toxinium_shotgun");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("toxinium_shotgun");
      this.setMaxDamage(1100);
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
            int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
            int reuse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.REUSE, itemstack);
            int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, itemstack);
            int impulse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, itemstack);
            int witchery = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.WITCHERY, itemstack);
            this.decreaseReload(itemstack, player);
            boolean hascooldown = player.getCooldownTracker().hasCooldown(this);
            int ammo = NBTHelper.GetNBTint(itemstack, "ammo");
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
            if (click && player.getHeldItemMainhand() == itemstack) {
               if (ammo > 0 && this.isReloaded(itemstack)) {
                  if (!hascooldown) {
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
                        Sounds.explode,
                        SoundCategory.AMBIENT,
                        0.9F,
                        0.9F + itemRand.nextFloat() / 5.0F
                     );
                     player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
                     player.addStat(StatList.getObjectUseStats(this));
                     IWeapon.fireBomEffect(this, player, world, 0);
                     Weapons.setPlayerAnimationOnServer(player, 3, EnumHand.MAIN_HAND);
                     if (!player.capabilities.isCreativeMode) {
                        this.addAmmo(ammo, itemstack, -1);
                        itemstack.damageItem(1, player);
                     }

                     double edist = parameters.getEnchanted("distance", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack));
                     double damageRadius = 0.25;
                     int seed = itemRand.nextInt();
                     Random random1 = new Random(seed);
                     int c = nonullbullet ? ColorConverters.RGBtoDecimal(bullet.colorR, bullet.colorG, bullet.colorB) : 16777215;
                     int impacts = 0;
                     int amount = Math.min(parameters.getEnchantedi("shots", reuse), 31);
                     float inaccuracy = parameters.getEnchanted("inaccuracy", acc);

                     for (int i = 0; i < amount; i++) {
                        boolean collideWithAny = false;
                        float rotP = player.rotationPitch + (float)random1.nextGaussian() * inaccuracy;
                        float rotY = player.rotationYaw + (float)random1.nextGaussian() * inaccuracy;
                        Vec3d vec = GetMOP.RotatedPosRayTrace(edist, 1.0F, player, 0.25, 0.2, rotP, rotY);
                        if (nonullbullet) {
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
                                 if (entity instanceof EntityLivingBase) {
                                    EntityLivingBase entitylivingbase = (EntityLivingBase)entity;
                                    if (entitylivingbase.getHealth() <= 0.0F) {
                                       if (itemRand.nextFloat() < 0.2) {
                                          if (itemRand.nextFloat() < 0.8) {
                                             entitylivingbase.removePotionEffect(PotionEffects.TOXIN);
                                          }

                                          DeathEffects.applyDeathEffect(entitylivingbase, DeathEffects.DE_DISMEMBER);
                                       }
                                    } else if (bullet == ItemsRegister.BULLETTOXIC) {
                                       PotionEffect baff = new PotionEffect(
                                          PotionEffects.TOXIN,
                                          parameters.getEnchantedi("potion_time", witchery),
                                          parameters.getEnchantedi("potion_power", witchery)
                                       );
                                       entitylivingbase.addPotionEffect(baff);
                                    } else {
                                       PotionEffect baff = new PotionEffect(
                                          PotionEffects.TOXIN,
                                          parameters.getEnchantedi("potion_time", witchery) + ItemBullet.BulletToxic.duration,
                                          parameters.getEnchantedi("potion_power", witchery)
                                       );
                                       entitylivingbase.addPotionEffect(baff);
                                    }
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
                        edist,
                        (double)amount,
                        (double)c,
                        (double)impacts,
                        (double)inaccuracy,
                        0.0,
                        0.0
                     );
                  }
               } else if (this.initiateBulletReload(itemstack, player, ItemsRegister.TOXINIUMSHOTGUNCLIP, maxammo, true)) {
                  world.playSound(
                     (EntityPlayer)null,
                     player.posX,
                     player.posY,
                     player.posZ,
                     Sounds.shotgun_rel,
                     SoundCategory.AMBIENT,
                     0.7F,
                     1.0F
                  );
                  Weapons.setPlayerAnimationOnServer(player, 4, EnumHand.MAIN_HAND);
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
         Vec3d from = Weapons.getPlayerShootPoint(player, EnumHand.MAIN_HAND);
         Random random1 = new Random((long)y);
         double edist = z;
         Vec3d col = ColorConverters.DecimaltoRGB((int)b);

         for (int i = 0; i < Math.min(a, 31.0); i++) {
            float rotP = player.rotationPitch + (float)random1.nextGaussian() * (float)d1;
            float rotY = player.rotationYaw + (float)random1.nextGaussian() * (float)d1;
            Vec3d to = GetMOP.RotatedPosRayTrace(edist, 1.0F, player, 0.25, 0.2, rotP, rotY);
            float dist = (float)from.distanceTo(to);
            boolean full = (impacts & 1 << i) > 0;
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
               full
            );
            part.Red2 = 0.3F;
            part.Green2 = 1.0F;
            part.Blue2 = 0.3F;
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
      Booom.lastTick = 13;
      Booom.frequency = -0.245F;
      Booom.x = 1.0F;
      Booom.y = 0.0F;
      Booom.z = 0.0F;
      Booom.power = 0.35F;
      Booom.FOVlastTick = 6;
      Booom.FOVfrequency = -0.5F;
      Booom.FOVpower = 0.035F;
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
      int rapidity = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack);
      return 8 - rapidity;
   }

   @Override
   public int getReloadTime(ItemStack itemstack) {
      int rel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RELOADING, itemstack);
      return 33 - rel * 5;
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return false;
   }
}
