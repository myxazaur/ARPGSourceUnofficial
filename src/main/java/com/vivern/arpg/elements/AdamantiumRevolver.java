package com.vivern.arpg.elements;

import com.vivern.arpg.entity.EntityLiveHeart;
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
import com.vivern.arpg.renders.BulletParticle;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
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
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AdamantiumRevolver extends ItemWeapon {
   public static int maxammo = 4;

   public AdamantiumRevolver() {
      this.setRegistryName("adamantium_revolver");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("adamantium_revolver");
      this.setMaxDamage(1000);
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
            int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, itemstack);
            int impulse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, itemstack);
            this.decreaseReload(itemstack, player);
            boolean hascooldown = player.getCooldownTracker().hasCooldown(this);
            boolean hascooldown2 = player.getCooldownTracker().hasCooldown(ItemsRegister.EXP);
            int ammo = NBTHelper.GetNBTint(itemstack, "ammo");
            EnumHand hand = click && player.getHeldItemMainhand() == itemstack
               ? EnumHand.MAIN_HAND
               : (click2 && player.getHeldItemOffhand() == itemstack ? EnumHand.OFF_HAND : null);
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
            boolean almostReloaded = NBTHelper.GetNBTint(itemstack, "reload_time") == 1
               && EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack) > 0;
            if (hand != null) {
               if (ammo > 0 && (this.isReloaded(itemstack) || almostReloaded)) {
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
                        Sounds.adamantium_revolver,
                        SoundCategory.AMBIENT,
                        0.9F,
                        0.9F + itemRand.nextFloat() / 5.0F
                     );
                     player.getCooldownTracker().setCooldown((Item)(hand == EnumHand.MAIN_HAND ? this : ItemsRegister.EXP), this.getCooldownTime(itemstack));
                     player.addStat(StatList.getObjectUseStats(this));
                     IWeapon.fireBomEffect(this, player, world, 0);
                     Weapons.setPlayerAnimationOnServer(player, 5, hand);
                     if (!player.capabilities.isCreativeMode) {
                        this.addAmmo(ammo, itemstack, -1);
                        itemstack.damageItem(1, player);
                     }

                     double edist = parameters.getEnchanted("distance", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack));
                     double damageRadius = 0.32;
                     float rotP = player.rotationPitch + (float)itemRand.nextGaussian() / (acc + 1);
                     float rotY = player.rotationYaw + (float)itemRand.nextGaussian() / (acc + 1);
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

                     boolean damageDealed = false;
                     if (!list.isEmpty()) {
                        for (Entity entity : list) {
                           if (Team.checkIsOpponent(player, entity)) {
                              if (Weapons.dealDamage(
                                 new WeaponDamage(itemstack, player, null, false, true, player, WeaponDamage.bullet),
                                 parameters.getEnchanted("damage", might) + damageadd * parameters.get("bullet_damage"),
                                 player,
                                 entity,
                                 true,
                                 parameters.getEnchanted("knockback", impulse) + knockbackadd * parameters.get("bullet_knockback"),
                                 player.posX,
                                 player.posY,
                                 player.posZ
                              )) {
                                 if (almostReloaded) {
                                    Weapons.setPotionIfEntityLB(
                                       entity,
                                       parameters.getPotion(
                                          entity instanceof EntityPlayer ? "stun_players" : "stun",
                                          MobEffects.SLOWNESS,
                                          EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.WITCHERY, itemstack)
                                       )
                                    );
                                    EntityLiveHeart.spawnHearts(
                                       world,
                                       vec.x,
                                       vec.y,
                                       vec.z,
                                       parameters.geti("hearts"),
                                       parameters.get("hearts_health"),
                                       true,
                                       4.0F,
                                       player
                                    );
                                 }

                                 damageDealed = true;
                              }

                              entity.hurtResistantTime = 0;
                              collidesWithAny = true;
                              if (nonullbullet) {
                                 bullet.onDamageCause(world, entity, player, null);
                              }

                              DeathEffects.applyDeathEffect(entity, DeathEffects.DE_DISMEMBER, 0.2F);
                           }
                        }
                     }

                     if (almostReloaded && !damageDealed) {
                        Weapons.setPotionIfEntityLB(
                           player,
                           parameters.getPotion("stun_players", MobEffects.SLOWNESS, EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.WITCHERY, itemstack))
                        );
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
                        collidesWithAny ? 1.0 : 0.0
                     );
                  }
               } else if (this.initiateBulletReload(itemstack, player, ItemsRegister.ADAMANTIUMROUNDS, maxammo, false)) {
                  world.playSound(
                     (EntityPlayer)null,
                     player.posX,
                     player.posY,
                     player.posZ,
                     Sounds.pistol_rel2,
                     SoundCategory.AMBIENT,
                     0.7F,
                     0.85F + itemRand.nextFloat() / 10.0F
                  );
                  Weapons.setPlayerAnimationOnServer(player, 6, hand);
               }
            }
         }
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
         0.2F,
         (int)MathHelper.clamp(dist / 4.0F, 1.0F, 6.0F),
         9,
         dist,
         (float)col.x,
         (float)col.y,
         (float)col.z,
         d3 > 0.0
      );
      part.smokeRed = 1.0F;
      part.smokeGreen = 0.15F;
      part.smokeBlue = 0.15F;
      part.blockDustId = (int)d2;
      world.spawnEntity(part);
   }

   @SideOnly(Side.CLIENT)
   @Override
   public void bom(int param) {
      Booom.lastTick = 16;
      Booom.frequency = -0.196F;
      Booom.x = 1.0F;
      Booom.y = (float)itemRand.nextGaussian() / 10.0F;
      Booom.z = (float)itemRand.nextGaussian() / 10.0F;
      Booom.power = 0.34F;
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
