package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.elements.animation.EnumFlick;
import com.Vivern.Arpg.elements.animation.Flicks;
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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class HydraulicShotgun extends ItemWeapon {
   public static int maxammo = 10;

   public HydraulicShotgun() {
      this.setRegistryName("hydraulic_shotgun");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("hydraulic_shotgun");
      this.setMaxDamage(5000);
      this.setMaxStackSize(1);
   }

   public float getXpRepairRatio(ItemStack stack) {
      return 3.0F;
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

   public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
      return new AnimationCapabilityProvider();
   }

   @Override
   public void onStateReceived(EntityPlayer player, ItemStack itemstack, byte state, int slot) {
      if (state == 1) {
         int cooldown = this.getCooldownTime(itemstack);
         Flicks.instance.setClientAnimation(player, slot, EnumFlick.COOLDOWN, 0, cooldown, 1, 0);
      }

      if (state == 3) {
         Flicks.instance.setClientAnimation(player, slot, EnumFlick.RELOAD, 0, 50, -1, 50);
      }
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
            this.decreaseReload(itemstack, player);
            boolean hascooldown = player.getCooldownTracker().hasCooldown(this);
            int ammo = NBTHelper.GetNBTint(itemstack, "ammo");
            boolean mainhand = click && player.getHeldItemMainhand() == itemstack;
            int currentShot = NBTHelper.GetNBTint(itemstack, "currentshot");
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
            if (mainhand) {
               if ((ammo > 0 || currentShot > 0) && this.isReloaded(itemstack)) {
                  if (!hascooldown) {
                     if (currentShot <= 0) {
                        if (!player.capabilities.isCreativeMode) {
                           this.addAmmo(ammo, itemstack, -1);
                        }

                        NBTHelper.GiveNBTint(itemstack, 3, "currentshot");
                        NBTHelper.SetNBTint(itemstack, parameters.geti("burst"), "currentshot");
                        currentShot = 3;
                     }

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
                        Sounds.hydraulic_shotgun,
                        SoundCategory.AMBIENT,
                        1.3F,
                        0.95F + itemRand.nextFloat() / 10.0F
                     );
                     player.getCooldownTracker().setCooldown(this, currentShot == 1 ? this.getCooldownTime(itemstack) : parameters.geti("cooldown_small"));
                     player.addStat(StatList.getObjectUseStats(this));
                     IWeapon.fireBomEffect(this, player, world, 0);
                     Weapons.setPlayerAnimationOnServer(player, currentShot == 1 ? 3 : 13, EnumHand.MAIN_HAND);
                     if (currentShot == 1) {
                        world.playSound(
                           (EntityPlayer)null,
                           player.posX,
                           player.posY,
                           player.posZ,
                           Sounds.hydraulic_shotgun_cool,
                           SoundCategory.AMBIENT,
                           1.0F,
                           0.9F + itemRand.nextFloat() / 5.0F
                        );
                        IWeapon.sendIWeaponState(itemstack, player, 1, itemSlot, EnumHand.MAIN_HAND);
                     }

                     NBTHelper.AddNBTint(itemstack, -1, "currentshot");
                     if (!player.capabilities.isCreativeMode) {
                        itemstack.damageItem(1, player);
                     }

                     double edist = parameters.getEnchanted("distance", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack));
                     double damageRadius = 0.3;
                     int seed = itemRand.nextInt();
                     Random random1 = new Random(seed);
                     int c = nonullbullet ? ColorConverters.RGBtoDecimal(bullet.colorR, bullet.colorG, bullet.colorB) : 16777215;
                     int impacts = 0;
                     int amount = Math.min(parameters.getEnchantedi("shots", reuse), 31);

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
                           if (itemRand.nextFloat() < 0.5F) {
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
                           }

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
               } else if (this.initiateBulletReload(itemstack, player, new ItemStack(ItemsRegister.HYDRAULICSHOTGUNCLIP, 1), maxammo, true)) {
                  world.playSound(
                     (EntityPlayer)null,
                     player.posX,
                     player.posY,
                     player.posZ,
                     Sounds.hydraulic_shotgun_rel,
                     SoundCategory.AMBIENT,
                     0.9F,
                     1.0F
                  );
                  Weapons.setPlayerAnimationOnServer(player, 19, mainhand ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND);
                  IWeapon.sendIWeaponState(itemstack, player, 3, itemSlot, EnumHand.MAIN_HAND);
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
               0.11F,
               (int)MathHelper.clamp(dist / 2.2, 2.0, 8.0),
               4,
               dist,
               (float)col.x,
               (float)col.y,
               (float)col.z,
               full
            );
            part.Red2 = 0.5F;
            part.Green2 = 0.7F;
            part.Blue2 = 0.8F;
            part.textureVariant = 3;
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
      Booom.lastTick = 10;
      Booom.frequency = -0.315F;
      Booom.x = 1.0F;
      Booom.y = 0.0F;
      Booom.z = 0.0F;
      Booom.power = 0.32F;
      Booom.FOVlastTick = 6;
      Booom.FOVfrequency = -0.5F;
      Booom.FOVpower = 0.017F;
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

   @Override
   public int getItemEnchantability() {
      return 2;
   }
}
