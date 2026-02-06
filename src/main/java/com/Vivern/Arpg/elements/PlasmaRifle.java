package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.entity.PlasmaRifleBall;
import com.Vivern.Arpg.main.Booom;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.Keys;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.WeaponDamage;
import com.Vivern.Arpg.main.WeaponParameters;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.potions.Stun;
import com.Vivern.Arpg.renders.AnimatedGParticle;
import com.Vivern.Arpg.renders.GUNParticle;
import java.util.List;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PlasmaRifle extends ItemWeapon implements IEnergyItem {
   public static ResourceLocation sparkle7 = new ResourceLocation("arpg:textures/sparkle7.png");
   public static ResourceLocation circle3 = new ResourceLocation("arpg:textures/circle3.png");

   public PlasmaRifle() {
      this.setRegistryName("plasma_rifle");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("plasma_rifle");
      this.setMaxDamage(13000);
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

   @Override
   public void effect(EntityPlayer player, World world, double x, double y, double z, double a, double b, double c, double d1, double d2, double d3) {
      Vec3d start = new Vec3d(x, y, z);
      Vec3d look = new Vec3d(a, b, c);
      Vec3d pitchYaw = GetMOP.Vec3dToPitchYaw(look);
      Vec3d lookUp = GetMOP.PitchYawToVec3d((float)pitchYaw.x - 90.0F, (float)pitchYaw.y);
      float move = 1.3F;
      float speedX = (float)a * move;
      float speedY = (float)b * move;
      float speedZ = (float)c * move;
      float offset = 0.5F;
      int lt = 9;
      GUNParticle part = new GUNParticle(
         circle3,
         0.5F,
         0.0F,
         lt,
         240,
         world,
         x + a * offset,
         y + b * offset,
         z + c * offset,
         speedX,
         speedY,
         speedZ,
         0.6F,
         0.45F + itemRand.nextFloat() / 10.0F,
         1.0F,
         true,
         itemRand.nextInt(360)
      );
      part.rotationPitchYaw = new Vec2f((float)pitchYaw.x, (float)pitchYaw.y);
      part.alpha = 3.0F;
      part.alphaTickAdding = -part.alpha / lt;
      part.alphaGlowing = true;
      part.scaleTickAdding = 0.4F;
      part.randomDeath = false;
      world.spawnEntity(part);
      move = 0.1F;
      speedX = (float)a * move;
      speedY = (float)b * move;
      speedZ = (float)c * move;
      offset = 0.5F;
      lt = 9;
      part = new GUNParticle(
         circle3,
         0.01F,
         0.0F,
         lt,
         240,
         world,
         x + a * offset,
         y + b * offset,
         z + c * offset,
         speedX,
         speedY,
         speedZ,
         0.7F,
         0.7F + itemRand.nextFloat() / 10.0F,
         1.0F,
         true,
         itemRand.nextInt(360)
      );
      part.rotationPitchYaw = new Vec2f((float)pitchYaw.x, (float)pitchYaw.y);
      part.alpha = 3.0F;
      part.alphaTickAdding = -part.alpha / lt;
      part.alphaGlowing = true;
      part.scaleTickAdding = 0.1F;
      part.randomDeath = false;
      world.spawnEntity(part);
      move = 0.2F;
      speedX = (float)a * move;
      speedY = (float)b * move;
      speedZ = (float)c * move;
      offset = 2.0F;
      lt = 10;
      part = new GUNParticle(
         circle3,
         0.5F,
         0.0F,
         lt,
         240,
         world,
         x + a * offset,
         y + b * offset,
         z + c * offset,
         speedX,
         speedY,
         speedZ,
         0.4F,
         0.25F + itemRand.nextFloat() / 10.0F,
         1.0F,
         true,
         itemRand.nextInt(360)
      );
      part.rotationPitchYaw = new Vec2f((float)pitchYaw.x, (float)pitchYaw.y);
      part.alpha = 1.0F;
      part.alphaTickAdding = -part.alpha / lt;
      part.alphaGlowing = true;
      part.scaleTickAdding = 0.4F;
      part.randomDeath = false;
      world.spawnEntity(part);
      Vec3d lookScaled = look.scale(6.0);

      for (int i = 0; i < 360; i += 4 + itemRand.nextInt(3)) {
         Vec3d rotated = GetMOP.rotateVecAroundAxis(lookUp, look, i);
         Vec3d pos = start.add(lookScaled).add(rotated);
         offset = 0.5F;
         RayTraceResult result = GetMOP.rayTraceBlocks(world, start, pos, null, false, true, false);
         if (result != null && result.hitVec != null && result.typeOfHit == Type.BLOCK) {
            pos = GetMOP.normalizeRayTraceResult(result, 0.125).hitVec;
            rotated = new Vec3d(
               rotated.x * 0.5 + look.x * 0.5,
               rotated.y * 0.5 + look.y * 0.5,
               rotated.z * 0.5 + look.z * 0.5
            );
         }

         int ltx = 30 + itemRand.nextInt(30);
         float scl = 0.1F + itemRand.nextFloat() * 0.05F;
         AnimatedGParticle partx = new AnimatedGParticle(
            sparkle7,
            scl,
            0.04F,
            ltx,
            240,
            world,
            pos.x,
            pos.y,
            pos.z,
            (float)rotated.x * offset,
            (float)rotated.y * offset,
            (float)rotated.z * offset,
            0.6F + itemRand.nextFloat() * 0.1F,
            0.5F,
            1.0F,
            true,
            itemRand.nextInt(360),
            true,
            1.0F
         );
         partx.alphaGlowing = true;
         partx.scaleTickAdding = -scl / ltx;
         partx.framecount = 4;
         partx.animDelay = 2 + itemRand.nextInt(4);
         partx.disableOnEnd = false;
         partx.animCounter = itemRand.nextInt(4);
         world.spawnEntity(partx);
      }
   }

   public void onUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
      if (!world.isRemote) {
         boolean hasShoot = false;
         this.setCanShoot(itemstack, entityIn);
         if (IWeapon.canShoot(itemstack)) {
            EntityPlayer player = (EntityPlayer)entityIn;
            boolean click = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
            int rapidity = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack);
            int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
            int reuse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.REUSE, itemstack);
            boolean hascooldown = player.getCooldownTracker().hasCooldown(this);
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
            int RFtoShoot = parameters.getEnchantedi("rf_to_shoot", reuse);
            if (player.getHeldItemMainhand() == itemstack) {
               int heat = NBTHelper.GetNBTint(itemstack, "heat");
               if (Keys.isKeyPressed(player, Keys.SECONDARYATTACK) && heat >= parameters.geti("heat_max") && !hascooldown) {
                  int range = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack);
                  int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, itemstack);
                  int impulse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, itemstack);
                  player.getCooldownTracker().setCooldown(this, parameters.getEnchantedi("wave_cooldown", rapidity));
                  IWeapon.fireBomEffect(this, player, world, 1);
                  Weapons.setPlayerAnimationOnServer(player, 3, EnumHand.MAIN_HAND);
                  world.playSound(
                     (EntityPlayer)null,
                     player.posX,
                     player.posY,
                     player.posZ,
                     Sounds.plasma_rifle_wave,
                     SoundCategory.AMBIENT,
                     1.5F,
                     0.9F + itemRand.nextFloat() / 5.0F
                  );
                  float length = parameters.getEnchanted("wave_length", range);
                  float size = parameters.getEnchanted("wave_size", range);
                  Vec3d vec3d = player.getPositionEyes(1.0F);
                  Vec3d vec3d1 = player.getLook(1.0F);
                  Vec3d vec3d2 = vec3d.add(vec3d1.x * length, vec3d1.y * length, vec3d1.z * length);

                  for (Entity entity : GetMOP.findEntitiesOnPath(vec3d, vec3d2, world, player, size, size * 0.8F)) {
                     if (Team.checkIsOpponent(player, entity)) {
                        Weapons.dealDamage(
                           new WeaponDamage(itemstack, player, null, false, false, vec3d, WeaponDamage.plasma),
                           parameters.getEnchanted("wave_damage", might),
                           player,
                           entity,
                           true,
                           parameters.getEnchanted("wave_knockback", impulse)
                        );
                        entity.hurtResistantTime = 0;
                        entity.setFire(parameters.geti("fire"));
                        Stun.stunEntity(entity, 1.0F);
                     }
                  }

                  Vec3d shootpoint = Weapons.getPlayerShootPoint(player, EnumHand.MAIN_HAND, 1.0F, 0.2F, -0.3F);
                  IWeapon.fireEffect(
                     this,
                     player,
                     world,
                     64.0,
                     shootpoint.x,
                     shootpoint.y,
                     shootpoint.z,
                     vec3d1.x,
                     vec3d1.y,
                     vec3d1.z,
                     0.0,
                     0.0,
                     0.0
                  );
                  NBTHelper.GiveNBTboolean(itemstack, true, "fast");
                  NBTHelper.SetNBTboolean(itemstack, true, "fast");
               }

               if (click) {
                  hasShoot = true;
                  if (this.getEnergyStored(itemstack) >= RFtoShoot && !hascooldown) {
                     boolean powered = NBTHelper.GetNBTboolean(itemstack, "fast");
                     world.playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        powered ? Sounds.plasma_rifle_powered : Sounds.plasma_rifle,
                        SoundCategory.AMBIENT,
                        0.9F,
                        0.9F + itemRand.nextFloat() / 5.0F
                     );
                     player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
                     player.addStat(StatList.getObjectUseStats(this));
                     IWeapon.fireBomEffect(this, player, world, 0);
                     Weapons.setPlayerAnimationOnServer(player, 11, EnumHand.MAIN_HAND);
                     if (!player.capabilities.isCreativeMode) {
                        itemstack.damageItem(1, player);
                        this.extractEnergyFromItem(itemstack, RFtoShoot, false);
                     }

                     if (powered) {
                        NBTHelper.AddNBTint(itemstack, -8, "heat");
                        if (NBTHelper.GetNBTint(itemstack, "heat") <= 0) {
                           NBTHelper.SetNBTint(itemstack, 0, "heat");
                           NBTHelper.SetNBTboolean(itemstack, false, "fast");
                        }
                     } else if (EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack) > 0) {
                        NBTHelper.GiveNBTint(itemstack, 0, "heat");
                        int heatadd = parameters.getEnchantedi("heat_per_shoot", rapidity);
                        int max = parameters.geti("heat_max");
                        NBTHelper.SetNBTint(itemstack, Math.min(heat + heatadd, max), "heat");
                        if (heat < max && heat + heatadd >= max) {
                           world.playSound(
                              (EntityPlayer)null,
                              player.posX,
                              player.posY,
                              player.posZ,
                              Sounds.hadron_blaster_sensor,
                              SoundCategory.AMBIENT,
                              0.8F,
                              0.9F + itemRand.nextFloat() / 5.0F
                           );
                        }
                     }

                     PlasmaRifleBall projectile = new PlasmaRifleBall(world, player, itemstack);
                     Weapons.shoot(
                        projectile,
                        EnumHand.MAIN_HAND,
                        player,
                        player.rotationPitch,
                        player.rotationYaw,
                        0.0F,
                        powered ? parameters.get("velocity_powered") : parameters.get("velocity"),
                        parameters.getEnchanted("inaccuracy", acc),
                        -0.23F,
                        0.5F,
                        0.5F
                     );
                     if (powered) {
                        projectile.setPowered();
                     }

                     world.spawnEntity(projectile);
                  }
               }
            }
         }

         if (NBTHelper.GetNBTboolean(itemstack, "fast") && !hasShoot) {
            NBTHelper.AddNBTint(itemstack, -1, "heat");
         }
      }
   }

   @SideOnly(Side.CLIENT)
   @Override
   public void bom(int param) {
      if (param == 0) {
         Booom.lastTick = 8;
         Booom.frequency = 4.0F;
         Booom.x = (float)itemRand.nextGaussian();
         Booom.y = (float)itemRand.nextGaussian();
         Booom.z = (float)itemRand.nextGaussian();
         Booom.power = 0.1F;
      }

      if (param == 1) {
         Booom.lastTick = 18;
         Booom.frequency = -Booom.getFrequencyForTicks(Booom.lastTick);
         Booom.x = 1.0F;
         Booom.y = (float)itemRand.nextGaussian() / 5.0F;
         Booom.z = (float)itemRand.nextGaussian() / 5.0F;
         Booom.power = 0.5F;
         Booom.FOVlastTick = 10;
         Booom.FOVfrequency = -0.32F;
         Booom.FOVpower = 0.017F;
      }
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.TWO_HANDED;
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return true;
   }

   @Override
   public boolean hasAdditionalDurabilityBar(ItemStack itemstack) {
      return NBTHelper.GetNBTint(itemstack, "heat") > 0;
   }

   @Override
   public float getAdditionalDurabilityBar(ItemStack itemstack) {
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(itemstack.getItem());
      return (float)NBTHelper.GetNBTint(itemstack, "heat") / parameters.geti("heat_max");
   }

   @Override
   public int getMaxEnergyStored(ItemStack stack) {
      return ItemAccumulator.TOPAZITRON_CAPACITY;
   }

   @Override
   public int getThroughput() {
      return ItemAccumulator.TOPAZITRON_THROUGHPUT;
   }

   @Override
   public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
      super.addInformation(stack, worldIn, tooltip, flagIn);
      tooltip.add("heat " + NBTHelper.GetNBTint(stack, "heat"));
   }
}
