//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.entity.ViolenceShoot;
import com.Vivern.Arpg.main.Booom;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.Keys;
import com.Vivern.Arpg.main.Mana;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.SuperKnockback;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.WeaponDamage;
import com.Vivern.Arpg.main.WeaponParameters;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.potions.PotionEffects;
import com.Vivern.Arpg.renders.GUNParticle;
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
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Violence extends ItemWeapon {
   public static ResourceLocation texture = new ResourceLocation("arpg:textures/circle2.png");
   public static ResourceLocation texturefire = new ResourceLocation("arpg:textures/fire_circle.png");

   public Violence() {
      this.setRegistryName("violence");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("violence");
      this.setMaxDamage(1660);
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
      Booom.lastTick = 17 + itemRand.nextInt(5);
      Booom.frequency = -0.7F;
      Booom.x = 0.0F;
      Booom.y = 0.0F;
      Booom.z = 1.0F;
      Booom.power = 0.25F;
   }

   @Override
   public void effect(EntityPlayer player, World world, double x, double y, double z, double a, double b, double c, double d1, double d2, double d3) {
      GUNParticle particle = new GUNParticle(texture, 0.1F, 0.0F, 10, 230, world, x, y, z, 0.0F, 0.0F, 0.0F, 1.0F, 0.2F, 0.8F, true, 0);
      particle.alphaGlowing = true;
      particle.scaleTickAdding = 1.0F;
      particle.alphaTickAdding = -0.08F;
      world.spawnEntity(particle);
      particle = new GUNParticle(texture, 0.1F, 0.0F, 10, 230, world, x, y + 0.1, z, 0.0F, 0.0F, 0.0F, 1.0F, 0.2F, 0.8F, true, itemRand.nextInt(360));
      particle.alphaGlowing = true;
      particle.scaleTickAdding = 0.9F;
      particle.alphaTickAdding = -0.08F;
      particle.rotationPitchYaw = new Vec2f(90.0F, 0.0F);
      particle.randomDeath = false;
      world.spawnEntity(particle);
      particle = new GUNParticle(
         texturefire,
         0.1F,
         0.0F,
         12,
         230,
         world,
         x,
         y,
         z,
         0.0F,
         0.0F,
         0.0F,
         0.5F,
         0.1F - itemRand.nextFloat() * 0.1F,
         1.0F,
         true,
         itemRand.nextInt(360)
      );
      particle.alphaGlowing = true;
      particle.scaleTickAdding = 0.8F;
      particle.alphaTickAdding = -0.07F;
      particle.rotationPitchYaw = new Vec2f(90.0F, 0.0F);
      particle.randomDeath = false;
      world.spawnEntity(particle);
   }

   public void onUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
      if (!world.isRemote) {
         this.setCanShoot(itemstack, entityIn);
         if (IWeapon.canShoot(itemstack)) {
            EntityPlayer player = (EntityPlayer)entityIn;
            int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
            int sor = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SORCERY, itemstack);
            int ran = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack);
            float power = Mana.getMagicPowerMax(player);
            boolean click = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
            boolean click2 = Keys.isKeyPressed(player, Keys.SECONDARYATTACK);
            int hit = NBTHelper.GetNBTint(itemstack, "hit");
            int hitcooldown = NBTHelper.GetNBTint(itemstack, "hitcooldown");
            if (hit > 0) {
               NBTHelper.AddNBTint(itemstack, -1, "hit");
            }

            if (hitcooldown > 0) {
               NBTHelper.AddNBTint(itemstack, -1, "hitcooldown");
            }

            if (player.getHeldItemMainhand() == itemstack) {
               WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
               if (hit == 1) {
                  world.playSound(
                     (EntityPlayer)null,
                     player.posX,
                     player.posY,
                     player.posZ,
                     Sounds.violence_special,
                     SoundCategory.AMBIENT,
                     2.0F,
                     0.9F + itemRand.nextFloat() / 5.0F
                  );
                  int witchery = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.WITCHERY, itemstack);
                  Vec3d castpoint = player.getPositionVector().add(GetMOP.YawToVec3d(player.rotationYaw).scale(0.64));
                  AxisAlignedBB aabb = GetMOP.newAABB(castpoint, (double)parameters.getEnchanted("hit_radius", ran));

                  for (EntityLivingBase entityLivingBase : world.getEntitiesWithinAABB(EntityLivingBase.class, aabb)) {
                     if (Team.checkIsOpponent(player, entityLivingBase)) {
                        PotionEffect effectlast = Weapons.mixPotion(
                           entityLivingBase,
                           PotionEffects.DEMONIC_BURN,
                           (float)parameters.getEnchantedi("hit_potion_time_add", witchery),
                           (float)parameters.geti("hit_potion_power_mult"),
                           Weapons.EnumPotionMix.WITH_MAXIMUM,
                           Weapons.EnumPotionMix.WITH_MAXIMUM,
                           Weapons.EnumMathOperation.PLUS,
                           Weapons.EnumMathOperation.MULTIPLY,
                           parameters.getEnchantedi("hit_potion_time_max", witchery),
                           parameters.getEnchantedi("hit_potion_power_max", witchery)
                        );
                        if (effectlast != null) {
                           PotionEffect effectnew = entityLivingBase.getActivePotionEffect(PotionEffects.DEMONIC_BURN);
                           if (effectlast.getAmplifier() < effectnew.getAmplifier()) {
                              Mana.changeMana(player, parameters.getEnchanted("mana_add", sor));
                           }

                           entityLivingBase.hurtResistantTime = 0;
                           Weapons.dealDamage(
                              new WeaponDamage(itemstack, player, null, false, false, castpoint, WeaponDamage.soul),
                              parameters.getEnchanted("hit_damage", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, itemstack)),
                              player,
                              entityLivingBase,
                              false,
                              parameters.getEnchanted("hit_knockback", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, itemstack)),
                              player.posX,
                              player.posY - 1.0,
                              player.posZ
                           );
                           entityLivingBase.hurtResistantTime = 0;
                        }
                     }
                  }

                  NBTHelper.SetNBTint(itemstack, parameters.geti("hit_cooldown"), "hitcooldown");
                  IWeapon.fireBomEffect(this, player, world, 0);
                  IWeapon.fireEffect(
                     this, player, world, 64.0, castpoint.x, castpoint.y + 0.1, castpoint.z, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0
                  );
               }

               if (!player.getCooldownTracker().hasCooldown(this)) {
                  if (click) {
                     float manacost = parameters.getEnchanted("manacost", sor);
                     if (Mana.getMana(player) > manacost) {
                        Weapons.setPlayerAnimationOnServer(player, 14, EnumHand.MAIN_HAND);
                        world.playSound(
                           (EntityPlayer)null,
                           player.posX,
                           player.posY,
                           player.posZ,
                           Sounds.violence,
                           SoundCategory.AMBIENT,
                           0.9F,
                           0.9F + itemRand.nextFloat() / 5.0F
                        );
                        player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
                        player.addStat(StatList.getObjectUseStats(this));
                        int reuse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.REUSE, itemstack);
                        int amount = parameters.geti("shots") + itemRand.nextInt(parameters.getEnchantedi("shots_random_bonus", reuse));
                        Vec3d vec0 = GetMOP.PosRayTrace(parameters.getEnchanted("range", ran), 1.0F, player, 0.9F, 0.7F);
                        if (vec0 != null) {
                           for (int i = 0; i < amount; i++) {
                              float area = parameters.getEnchanted("area", acc);
                              Vec3d vec1 = vec0.add(
                                 itemRand.nextGaussian() * area, 8.0 + itemRand.nextGaussian(), itemRand.nextGaussian() * area
                              );
                              Vec3d vec2 = vec1;
                              Vec3d vecSubstr = vec1.subtract(vec0).normalize().scale(1.6);
                              RayTraceResult result2 = world.rayTraceBlocks(vec0.add(vecSubstr), vec1);
                              if (result2 != null && result2.hitVec != null) {
                                 if (result2.sideHit != null) {
                                    double offset = 0.2;
                                    vec2 = result2.hitVec
                                       .add(
                                          result2.sideHit.getXOffset() * offset,
                                          result2.sideHit.getYOffset() * offset,
                                          result2.sideHit.getZOffset() * offset
                                       );
                                 } else {
                                    vec2 = result2.hitVec;
                                 }
                              }

                              ViolenceShoot shoot = new ViolenceShoot(world, player, itemstack, power);
                              shoot.setPosition(vec2.x, vec2.y, vec2.z);
                              SuperKnockback.applyMove(shoot, -parameters.get("velocity"), vec0.x, vec0.y, vec0.z);
                              world.spawnEntity(shoot);
                           }

                           if (!player.capabilities.isCreativeMode) {
                              Mana.changeMana(player, -manacost);
                              Mana.setManaSpeed(player, 0.001F);
                              itemstack.damageItem(1, player);
                           }
                        }
                     }
                  } else if (click2 && hitcooldown <= 0 && hit <= 0) {
                     NBTHelper.GiveNBTint(itemstack, 0, "hit");
                     NBTHelper.GiveNBTint(itemstack, 0, "hitcooldown");
                     player.getCooldownTracker().setCooldown(this, 30);
                     Weapons.setPlayerAnimationOnServer(player, 21, EnumHand.MAIN_HAND);
                     world.playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.swosh_c,
                        SoundCategory.AMBIENT,
                        0.6F,
                        0.9F + itemRand.nextFloat() / 5.0F
                     );
                     NBTHelper.SetNBTint(itemstack, 15, "hit");
                     if (!player.capabilities.isCreativeMode) {
                        itemstack.damageItem(2, player);
                     }
                  }
               }
            }
         }
      }
   }

   @Override
   public float getAdditionalDurabilityBar(ItemStack itemstack) {
      return MathHelper.clamp(
         NBTHelper.GetNBTint(itemstack, "hitcooldown") / WeaponParameters.getWeaponParameters(itemstack.getItem()).get("hit_cooldown"), 0.0F, 1.0F
      );
   }

   @Override
   public boolean hasAdditionalDurabilityBar(ItemStack itemstack) {
      return NBTHelper.GetNBTint(itemstack, "hitcooldown") > 0;
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return false;
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
