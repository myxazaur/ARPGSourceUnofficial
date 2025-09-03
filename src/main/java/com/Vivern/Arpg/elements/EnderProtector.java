//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.entity.EntityLaunchedRocket;
import com.Vivern.Arpg.main.Booom;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.Keys;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.WeaponParameters;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.renders.GUNParticle;
import com.Vivern.Arpg.renders.ParticleTracker;
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
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EnderProtector extends ItemWeapon {
   static ResourceLocation explode = new ResourceLocation("arpg:textures/void_explode.png");
   static ResourceLocation whirl = new ResourceLocation("arpg:textures/whirl.png");
   static ResourceLocation simple_magic_shoot = new ResourceLocation("arpg:textures/simple_magic_shoot.png");
   public static int maxammo = 5;
   public static ParticleTracker.TrackerSmoothShowHide ssh1 = new ParticleTracker.TrackerSmoothShowHide(
      null, new Vec3d[]{new Vec3d(0.0, 5.0, 0.1), new Vec3d(5.0, 10.0, -0.1)}
   );
   public static ParticleTracker.TrackerSmoothShowHide ssh2 = new ParticleTracker.TrackerSmoothShowHide(
      null, new Vec3d[]{new Vec3d(0.0, 5.0, 0.15), new Vec3d(5.0, 14.0, -0.12)}
   );

   public EnderProtector() {
      this.setRegistryName("ender_protector");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("ender_protector");
      this.setMaxDamage(430);
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
      Booom.lastTick = 14;
      Booom.frequency = -0.225F;
      Booom.x = 1.0F;
      Booom.y = 0.0F;
      Booom.z = 0.0F;
      Booom.power = 0.2F;
      Booom.FOVlastTick = 8;
      Booom.FOVfrequency = -0.395F;
      Booom.FOVpower = 0.016F;
   }

   public void onUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
      if (!world.isRemote) {
         this.setCanShoot(itemstack, entityIn);
         if (IWeapon.canShoot(itemstack)) {
            EntityPlayer player = (EntityPlayer)entityIn;
            this.decreaseReload(itemstack, player);
            boolean click = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
            int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
            int ammo = NBTHelper.GetNBTint(itemstack, "ammo");
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
            if (click && player.getHeldItemMainhand() == itemstack) {
               if (ammo > 0 && this.isReloaded(itemstack)) {
                  if (!player.getCooldownTracker().hasCooldown(this)) {
                     world.playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.ender_protector,
                        SoundCategory.AMBIENT,
                        0.9F,
                        0.9F + itemRand.nextFloat() / 5.0F
                     );
                     player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
                     player.addStat(StatList.getObjectUseStats(this));
                     IWeapon.fireBomEffect(this, player, world, 0);
                     Weapons.setPlayerAnimationOnServer(player, 13, EnumHand.MAIN_HAND);
                     double edist = parameters.getEnchanted("distance", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack));
                     float size = parameters.getEnchanted("raytrace_size", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack));
                     Vec3d vec = GetMOP.PosRayTrace(edist, 1.0F, player, size, size * 0.9F);
                     Vec3d rocketPos = null;
                     double maxdist = 0.0;

                     for (int i = 0; i < 10; i++) {
                        Vec3d newvec = vec.add(
                           itemRand.nextGaussian() * 3.5, itemRand.nextGaussian() * 3.5, itemRand.nextGaussian() * 3.5
                        );
                        double dist = vec.squareDistanceTo(newvec);
                        if (dist > maxdist && GetMOP.thereIsNoBlockCollidesBetween(world, newvec, vec, 1.0F, null, false)) {
                           maxdist = dist;
                           rocketPos = newvec;
                        }
                     }

                     if (rocketPos == null) {
                        rocketPos = vec.add(itemRand.nextGaussian() * 1.5, itemRand.nextGaussian() * 1.5, itemRand.nextGaussian() * 1.5);
                     }

                     EntityLaunchedRocket projectile = new EntityLaunchedRocket(world, player, itemstack);
                     projectile.damage = parameters.getEnchanted("damage", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, itemstack));
                     projectile.knockback = parameters.getEnchanted("knockback", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, itemstack));
                     double d0 = vec.x - rocketPos.x;
                     double d1 = vec.y - rocketPos.y;
                     double d2 = vec.z - rocketPos.z;
                     double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
                     float f = (float)(MathHelper.atan2(d2, d0) * (180.0 / Math.PI)) - 90.0F;
                     float f1 = (float)(-(MathHelper.atan2(d1, d3) * (180.0 / Math.PI)));
                     projectile.rotationPitch = f1;
                     projectile.rotationYaw = f;
                     float t = -MathHelper.sin(projectile.rotationYaw * (float) (Math.PI / 180.0))
                        * MathHelper.cos(projectile.rotationPitch * (float) (Math.PI / 180.0));
                     float t1 = -MathHelper.sin(projectile.rotationPitch * (float) (Math.PI / 180.0));
                     float t2 = MathHelper.cos(projectile.rotationYaw * (float) (Math.PI / 180.0))
                        * MathHelper.cos(projectile.rotationPitch * (float) (Math.PI / 180.0));
                     projectile.shoot(t, t1, t2, parameters.get("velocity"), parameters.getEnchanted("inaccuracy", acc));
                     projectile.setPosition(rocketPos.x, rocketPos.y, rocketPos.z);
                     world.spawnEntity(projectile);
                     IWeapon.fireEffect(
                        this,
                        player,
                        world,
                        64.0,
                        rocketPos.x,
                        rocketPos.y,
                        rocketPos.z,
                        (double)f1,
                        (double)f,
                        0.0,
                        0.0,
                        0.0,
                        0.0
                     );
                     world.playSound(
                        (EntityPlayer)null,
                        rocketPos.x,
                        rocketPos.y,
                        rocketPos.z,
                        Sounds.ender_protector_portal,
                        SoundCategory.AMBIENT,
                        1.3F,
                        0.9F + itemRand.nextFloat() / 5.0F
                     );
                     if (!player.capabilities.isCreativeMode) {
                        this.addAmmo(ammo, itemstack, -1);
                        itemstack.damageItem(1, player);
                     }
                  }
               } else if (this.initiateRocketReload(itemstack, player, maxammo)) {
                  world.playSound(
                     (EntityPlayer)null,
                     player.posX,
                     player.posY,
                     player.posZ,
                     Sounds.ender_protector_rel,
                     SoundCategory.NEUTRAL,
                     0.75F,
                     0.95F + itemRand.nextFloat() / 10.0F
                  );
                  Weapons.setPlayerAnimationOnServer(player, 4, EnumHand.MAIN_HAND);
               }
            }
         }
      }
   }

   @Override
   public void effect(EntityPlayer player, World world, double x, double y, double z, double a, double b, double c, double d1, double d2, double d3) {
      Vec2f rotation = new Vec2f((float)a, (float)b);
      float displ = 3.0F;
      GUNParticle particle = new GUNParticle(
         explode,
         0.1F,
         0.0F,
         10,
         240,
         world,
         x,
         y,
         z,
         0.0F,
         0.0F,
         0.0F,
         0.8F + itemRand.nextFloat() / 10.0F,
         0.9F + itemRand.nextFloat() / 10.0F,
         1.0F,
         true,
         itemRand.nextInt(360)
      );
      particle.rotationPitchYaw = rotation;
      particle.alphaGlowing = true;
      particle.scaleTickAdding = 0.1F;
      particle.alphaTickAdding = -0.1F;
      particle.randomDeath = false;
      world.spawnEntity(particle);
      float fx = (itemRand.nextFloat() - 0.5F) / displ;
      float fy = (itemRand.nextFloat() - 0.5F) / displ;
      float fz = (itemRand.nextFloat() - 0.5F) / displ;
      GUNParticle particle2 = new GUNParticle(
         whirl,
         0.1F,
         0.0F,
         10,
         240,
         world,
         x + fx,
         y + fy,
         z + fz,
         0.0F,
         0.0F,
         0.0F,
         0.1F,
         0.9F + itemRand.nextFloat() / 10.0F,
         0.7F,
         true,
         itemRand.nextInt(360)
      );
      particle2.rotationPitchYaw = rotation;
      particle2.alphaGlowing = false;
      particle2.randomDeath = false;
      particle2.tracker = ssh1;
      world.spawnEntity(particle2);
      fx = (itemRand.nextFloat() - 0.5F) / displ;
      fy = (itemRand.nextFloat() - 0.5F) / displ;
      fz = (itemRand.nextFloat() - 0.5F) / displ;
      particle2 = new GUNParticle(
         whirl,
         0.1F,
         0.0F,
         13,
         240,
         world,
         x + fx,
         y + fy,
         z + fz,
         0.0F,
         0.0F,
         0.0F,
         0.5F + itemRand.nextFloat() / 10.0F,
         0.35F + itemRand.nextFloat() / 10.0F,
         1.0F,
         true,
         itemRand.nextInt(360)
      );
      particle2.rotationPitchYaw = rotation;
      particle2.alphaGlowing = true;
      particle2.randomDeath = false;
      particle2.tracker = ssh2;
      world.spawnEntity(particle2);
      fx = (itemRand.nextFloat() - 0.5F) / displ;
      fy = (itemRand.nextFloat() - 0.5F) / displ;
      fz = (itemRand.nextFloat() - 0.5F) / displ;
      particle2 = new GUNParticle(
         simple_magic_shoot,
         0.05F,
         0.0F,
         10,
         200,
         world,
         x + fx,
         y + fy,
         z + fz,
         0.0F,
         0.0F,
         0.0F,
         0.5F + itemRand.nextFloat() / 5.0F,
         0.25F + itemRand.nextFloat() / 5.0F,
         1.0F,
         true,
         itemRand.nextInt(360)
      );
      particle2.rotationPitchYaw = rotation;
      particle2.alphaGlowing = true;
      particle2.randomDeath = false;
      particle2.tracker = ssh1;
      world.spawnEntity(particle2);
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
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.TWO_HANDED;
   }

   @Override
   public int getItemEnchantability() {
      return 2;
   }
}
