//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.events.Debugger;
import com.Vivern.Arpg.main.Booom;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.Keys;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.WeaponDamage;
import com.Vivern.Arpg.main.WeaponParameters;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.renders.GUNParticle;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class StaticLance extends ItemWeapon {
   ResourceLocation explode = new ResourceLocation("arpg:textures/blueexplode5.png");
   ResourceLocation cloud = new ResourceLocation("arpg:textures/largecloud.png");
   ResourceLocation spell = new ResourceLocation("arpg:textures/spellblue3.png");

   public StaticLance() {
      this.setRegistryName("static_lance");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("static_lance");
      this.setMaxDamage(1700);
      this.setMaxStackSize(1);
   }

   public boolean canDestroyBlockInCreative(World world, BlockPos pos, ItemStack stack, EntityPlayer player) {
      return false;
   }

   public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
      return true;
   }

   public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
      return false;
   }

   @Override
   public boolean canAttackMelee(ItemStack itemstack, EntityPlayer player) {
      return false;
   }

   public void onUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
      if (world.isRemote && Debugger.floats[0] != 0.0F) {
         NBTHelper.SetNBTint(itemstack, (int)Debugger.floats[0], "boomcooldown");
      }

      if (!world.isRemote) {
         this.setCanShoot(itemstack, entityIn);
         if (IWeapon.canShoot(itemstack)) {
            EntityPlayer player = (EntityPlayer)entityIn;
            boolean click = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
            boolean click2 = Keys.isKeyPressed(player, Keys.SECONDARYATTACK);
            int damage = itemstack.getItemDamage();
            boolean hascooldown = player.getCooldownTracker().hasCooldown(this);
            NBTHelper.GiveNBTint(itemstack, 0, "atdelay");
            NBTHelper.GiveNBTint(itemstack, 0, "boomcooldown");
            NBTHelper.GiveNBTint(itemstack, 0, "blowdelay");
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
            int delay = NBTHelper.GetNBTint(itemstack, "atdelay");
            if (delay > 0) {
               NBTHelper.AddNBTint(itemstack, -1, "atdelay");
            }

            int boomcooldown = NBTHelper.GetNBTint(itemstack, "boomcooldown");
            if (boomcooldown > 0) {
               NBTHelper.AddNBTint(itemstack, -1, "boomcooldown");
            }

            int blowdelay = NBTHelper.GetNBTint(itemstack, "blowdelay");
            if (blowdelay > 0) {
               NBTHelper.AddNBTint(itemstack, -1, "blowdelay");
            }

            EnumHand hand = EnumHand.MAIN_HAND;
            if (player.getHeldItemMainhand() == itemstack) {
               if (delay <= 0 && click && !hascooldown) {
                  Weapons.setPlayerAnimationOnServer(player, 2, hand);
                  NBTHelper.SetNBTint(itemstack, 4, "atdelay");
                  double attackspeed = player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getAttributeValue();
                  player.getCooldownTracker().setCooldown(this, this.getModifiedMeleeCooldown(attackspeed, this.getCooldownTime(itemstack)));
               }

               if (delay == 1) {
                  int sharpness = EnchantmentHelper.getEnchantmentLevel(Enchantments.SHARPNESS, itemstack);
                  int sweeping = EnchantmentHelper.getEnchantmentLevel(Enchantments.SWEEPING, itemstack);
                  int knockback = EnchantmentHelper.getEnchantmentLevel(Enchantments.KNOCKBACK, itemstack);
                  if (IWeapon.doMeleeSpearAttack(this, itemstack, player, hand, false).success) {
                     world.playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.melee_sword,
                        SoundCategory.PLAYERS,
                        0.7F,
                        0.8F + itemRand.nextFloat() / 5.0F
                     );
                  } else {
                     world.playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.melee_miss_sword,
                        SoundCategory.PLAYERS,
                        0.6F,
                        0.8F + itemRand.nextFloat() / 5.0F
                     );
                  }

                  player.addExhaustion(0.1F);
               }

               if (damage < this.getMaxDamage() && blowdelay <= 0 && delay <= 0 && !click && click2 && boomcooldown <= 0 && !hascooldown) {
                  NBTHelper.SetNBTint(itemstack, 8, "blowdelay");
                  Weapons.setPlayerAnimationOnServer(player, 7, hand);
                  double attackspeed = player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getAttributeValue();
                  player.getCooldownTracker().setCooldown(this, this.getModifiedMeleeCooldown(attackspeed, this.getCooldownTime(itemstack)));
                  NBTHelper.SetNBTint(
                     itemstack, parameters.getEnchantedi("blow_cooldown", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack)), "boomcooldown"
                  );
               }

               if (blowdelay > 0 && blowdelay <= 4) {
                  double boomDistance = parameters.getEnchanted("blow_distance", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack));
                  Vec3d vec3d = player.getPositionEyes(1.0F);
                  Vec3d vec3d1 = player.getLook(1.0F);
                  Vec3d vec3d2 = vec3d.add(
                     vec3d1.x * boomDistance, vec3d1.y * boomDistance, vec3d1.z * boomDistance
                  );
                  RayTraceResult result = world.rayTraceBlocks(vec3d, vec3d2, false, true, false);
                  if (result != null && result.typeOfHit != null && result.typeOfHit == Type.BLOCK && result.hitVec != null) {
                     NBTHelper.SetNBTint(itemstack, 0, "blowdelay");
                     IWeapon.fireBomEffect(this, player, world, 0);
                     Vec3d vec = result.hitVec;
                     world.playSound(
                        (EntityPlayer)null,
                        vec.x,
                        vec.y,
                        vec.z,
                        Sounds.static_lance,
                        SoundCategory.PLAYERS,
                        1.2F,
                        0.9F + itemRand.nextFloat() / 5.0F
                     );
                     double damageRadius = parameters.getEnchanted("damage_radius", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack));
                     AxisAlignedBB aabb = new AxisAlignedBB(
                        vec.x - damageRadius,
                        vec.y - damageRadius,
                        vec.z - damageRadius,
                        vec.x + damageRadius,
                        vec.y + damageRadius,
                        vec.z + damageRadius
                     );
                     List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(player, aabb);
                     double power = Math.min(
                        (double)parameters.get("special_max_power"),
                        EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack) > 0 ? Math.abs(player.motionY) : 0.0
                     );
                     if (!list.isEmpty()) {
                        for (Entity entity : list) {
                           if (Team.checkIsOpponent(player, entity)) {
                              Weapons.dealDamage(
                                 new WeaponDamage(itemstack, player, null, true, false, vec, WeaponDamage.electric),
                                 (float)(
                                    player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()
                                       + parameters.getEnchanted("blow_damage", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, itemstack))
                                       + power * parameters.get("special_damage")
                                 ),
                                 player,
                                 entity,
                                 true,
                                 parameters.getEnchanted("blow_knockback", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, itemstack)),
                                 vec.x,
                                 vec.y - 0.5,
                                 vec.z
                              );
                              entity.hurtResistantTime = 0;
                              Weapons.setPotionIfEntityLB(
                                 entity,
                                 MobEffects.LEVITATION,
                                 (int)(
                                    parameters.getEnchanted("potion_time", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.WITCHERY, itemstack))
                                       + (float)Math.round(
                                          power
                                             * parameters.getEnchanted(
                                                "special_potion_time", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.WITCHERY, itemstack)
                                             )
                                       )
                                 ),
                                 0
                              );
                           }
                        }
                     }

                     if (!player.capabilities.isCreativeMode) {
                        itemstack.damageItem(1, player);
                        player.addExhaustion(0.3F);
                     }

                     IWeapon.fireEffect(this, player, world, 64.0, vec.x, vec.y, vec.z, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
                  } else if (blowdelay == 1) {
                     world.playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.melee_miss_axe,
                        SoundCategory.PLAYERS,
                        0.6F,
                        0.8F + itemRand.nextFloat() / 5.0F
                     );
                  }
               }
            }
         }
      }
   }

   @Override
   public void effect(EntityPlayer player, World world, double x, double y, double z, double a, double b, double c, double d1, double d2, double d3) {
      GUNParticle bigsmoke1 = new GUNParticle(
         this.explode, 0.1F, 0.0F, 11, 240, world, x, y + 0.05, z, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, true, itemRand.nextInt(360)
      );
      bigsmoke1.alphaTickAdding = -0.095F;
      bigsmoke1.alphaGlowing = true;
      bigsmoke1.scaleTickAdding = 0.5F;
      world.spawnEntity(bigsmoke1);

      for (int ss = 0; ss < 7; ss++) {
         GUNParticle bigsmoke = new GUNParticle(
            this.cloud,
            1.1F + (float)itemRand.nextGaussian() / 6.0F,
            0.0F,
            30,
            180,
            world,
            x,
            y,
            z,
            (float)itemRand.nextGaussian() / 14.0F,
            (float)itemRand.nextGaussian() / 14.0F,
            (float)itemRand.nextGaussian() / 14.0F,
            0.75F + (float)itemRand.nextGaussian() / 10.0F,
            0.45F,
            1.0F,
            true,
            itemRand.nextInt(360)
         );
         bigsmoke.alphaTickAdding = -0.025F;
         bigsmoke.alphaGlowing = true;
         world.spawnEntity(bigsmoke);
      }

      for (int ss = 0; ss < 10; ss++) {
         GUNParticle bigsmoke = new GUNParticle(
            this.spell,
            0.08F + itemRand.nextFloat() / 15.0F,
            0.1F,
            70,
            240,
            world,
            x,
            y,
            z,
            (float)itemRand.nextGaussian() / 6.0F,
            (float)itemRand.nextGaussian() / 5.0F + 0.1F,
            (float)itemRand.nextGaussian() / 6.0F,
            0.8F + itemRand.nextFloat() / 10.0F,
            0.9F,
            1.0F,
            false,
            itemRand.nextInt(360),
            true,
            1.5F
         );
         bigsmoke.scaleTickAdding = -0.001F;
         world.spawnEntity(bigsmoke);
      }
   }

   @Override
   public boolean hasAdditionalDurabilityBar(ItemStack itemstack) {
      return NBTHelper.GetNBTint(itemstack, "boomcooldown") > 0;
   }

   @Override
   public float getAdditionalDurabilityBar(ItemStack itemstack) {
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
      return NBTHelper.GetNBTint(itemstack, "boomcooldown")
         / parameters.getEnchanted("blow_cooldown", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack));
   }

   @SideOnly(Side.CLIENT)
   @Override
   public void bom(int param) {
      Booom.lastTick = 10;
      Booom.frequency = -0.63F;
      Booom.x = 1.0F;
      Booom.y = (itemRand.nextFloat() - 0.5F) / 3.0F;
      Booom.z = (itemRand.nextFloat() - 0.5F) / 3.0F;
      Booom.power = 0.58F;
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return false;
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.SEMI_ONE_HANDED;
   }
}
