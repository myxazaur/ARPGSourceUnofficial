package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.events.Debugger;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Keys;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.SuperKnockback;
import com.Vivern.Arpg.network.PacketHandler;
import com.Vivern.Arpg.network.PacketWhipToClients;
import com.Vivern.Arpg.renders.GUNParticle;
import com.Vivern.Arpg.renders.WhipParticle;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FireWhip extends ItemWeapon {
   static ResourceLocation sweeptex = new ResourceLocation("arpg:textures/sweep2.png");
   static ResourceLocation texture = new ResourceLocation("arpg:textures/fire_beam.png");
   static ResourceLocation texture2 = new ResourceLocation("arpg:textures/fishing_round2.png");
   public int segmentCount = 8;
   public float maxDist = 0.4F;
   public float minDist = 0.2F;

   public FireWhip() {
      this.setRegistryName("fire_whip");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("fire_whip");
      this.setMaxDamage(1100);
      this.setMaxStackSize(1);
   }

   public void onUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
      if (!world.isRemote) {
         this.setCanShoot(itemstack, entityIn);
         if (IWeapon.canShoot(itemstack)) {
            float power = Debugger.floats[0] - 1.2F;
            float friction = Debugger.floats[1] + 0.49F;
            float del = Debugger.floats[2] + 12.0F;
            EntityPlayer player = (EntityPlayer)entityIn;
            boolean hascooldown = player.getCooldownTracker().hasCooldown(this);
            boolean hascooldown2 = player.getCooldownTracker().hasCooldown(ItemsRegister.EXP);
            boolean click = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
            boolean click2 = Keys.isKeyPressed(player, Keys.SECONDARYATTACK);
            int damage = itemstack.getItemDamage();
            Vec3d vec = GetMOP.RotatedPosRayTrace(1.0, 1.0F, player, 0.2, 0.2, 40.0F, player.rotationYaw + 25.0F);
            double startX = vec.x;
            double startY = vec.y;
            double startZ = vec.z;
            if (!itemstack.hasTagCompound() || !itemstack.getTagCompound().hasKey("posX0")) {
               NBTTagCompound itemCompound = new NBTTagCompound();
               itemstack.setTagCompound(itemCompound);

               for (int s = 0; s < this.segmentCount; s++) {
                  NBTHelper.GiveNBTdouble(itemstack, startX, "posX" + s);
                  NBTHelper.GiveNBTdouble(itemstack, startY, "posY" + s);
                  NBTHelper.GiveNBTdouble(itemstack, startZ, "posZ" + s);
                  NBTHelper.GiveNBTdouble(itemstack, 0.0, "motionX" + s);
                  NBTHelper.GiveNBTdouble(itemstack, 0.0, "motionY" + s);
                  NBTHelper.GiveNBTdouble(itemstack, 0.0, "motionZ" + s);
               }
            }

            if (player.getHeldItemMainhand() != itemstack) {
               for (int s = 0; s < this.segmentCount; s++) {
                  NBTHelper.SetNBTdouble(itemstack, startX, "posX" + s);
                  NBTHelper.SetNBTdouble(itemstack, startY, "posY" + s);
                  NBTHelper.SetNBTdouble(itemstack, startZ, "posZ" + s);
               }
            } else {
               for (int s = 0; s < this.segmentCount; s++) {
                  if (s != 0) {
                     NBTHelper.AddNBTdouble(itemstack, NBTHelper.GetNBTdouble(itemstack, "motionX" + s), "posX" + s);
                     NBTHelper.AddNBTdouble(itemstack, NBTHelper.GetNBTdouble(itemstack, "motionY" + s), "posY" + s);
                     NBTHelper.AddNBTdouble(itemstack, NBTHelper.GetNBTdouble(itemstack, "motionZ" + s), "posZ" + s);
                     double possX = NBTHelper.GetNBTdouble(itemstack, "posX" + s);
                     double possY = NBTHelper.GetNBTdouble(itemstack, "posY" + s);
                     double possZ = NBTHelper.GetNBTdouble(itemstack, "posZ" + s);
                     double poss2X = NBTHelper.GetNBTdouble(itemstack, "posX" + (s - 1));
                     double poss2Y = NBTHelper.GetNBTdouble(itemstack, "posY" + (s - 1));
                     double poss2Z = NBTHelper.GetNBTdouble(itemstack, "posZ" + (s - 1));
                     double d0 = possX - poss2X;
                     double d1 = possY - poss2Y;
                     double d2 = possZ - poss2Z;
                     float dist = MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                     float finalpower = (power - s / del) * dist;
                     float prunex = (float)((poss2X - possX) / dist / 2.0 * finalpower);
                     float pruney = (float)((poss2Y - possY) / dist / 2.0 * finalpower);
                     float prunez = (float)((poss2Z - possZ) / dist / 2.0 * finalpower);
                     NBTHelper.AddNBTdouble(itemstack, -prunex, "motionX" + s);
                     NBTHelper.AddNBTdouble(itemstack, -pruney, "motionY" + s);
                     NBTHelper.AddNBTdouble(itemstack, -prunez, "motionZ" + s);
                     NBTHelper.SetNBTdouble(itemstack, NBTHelper.GetNBTdouble(itemstack, "motionX" + s) * friction, "motionX" + s);
                     NBTHelper.SetNBTdouble(itemstack, NBTHelper.GetNBTdouble(itemstack, "motionY" + s) * friction - 0.03, "motionY" + s);
                     NBTHelper.SetNBTdouble(itemstack, NBTHelper.GetNBTdouble(itemstack, "motionZ" + s) * friction, "motionZ" + s);
                  } else {
                     NBTHelper.SetNBTdouble(itemstack, startX, "posX" + s);
                     NBTHelper.SetNBTdouble(itemstack, startY, "posY" + s);
                     NBTHelper.SetNBTdouble(itemstack, startZ, "posZ" + s);
                  }
               }

               for (int sx = 0; sx < this.segmentCount - 1; sx++) {
                  double posaX = NBTHelper.GetNBTdouble(itemstack, "posX" + sx);
                  double posaY = NBTHelper.GetNBTdouble(itemstack, "posY" + sx);
                  double posaZ = NBTHelper.GetNBTdouble(itemstack, "posZ" + sx);
                  double mX = NBTHelper.GetNBTdouble(itemstack, "motionX" + sx);
                  double mY = NBTHelper.GetNBTdouble(itemstack, "motionY" + sx);
                  double mZ = NBTHelper.GetNBTdouble(itemstack, "motionZ" + sx);
                  double posbX = NBTHelper.GetNBTdouble(itemstack, "posX" + (sx + 1));
                  double posbY = NBTHelper.GetNBTdouble(itemstack, "posY" + (sx + 1));
                  double posbZ = NBTHelper.GetNBTdouble(itemstack, "posZ" + (sx + 1));
                  double mbX = NBTHelper.GetNBTdouble(itemstack, "motionX" + (sx + 1));
                  double mbY = NBTHelper.GetNBTdouble(itemstack, "motionY" + (sx + 1));
                  double mbZ = NBTHelper.GetNBTdouble(itemstack, "motionZ" + (sx + 1));
                  PacketWhipToClients packet = new PacketWhipToClients();
                  packet.writeargs(posaX, posaY, posaZ, mX, mY, mZ, posbX, posbY, posbZ, mbX, mbY, mbZ, Item.getIdFromItem(this));
                  PacketHandler.sendToAllAround(packet, world, player.posX, player.posY, player.posZ, 64.0);
                  if (mX + mY + mZ > 0.6) {
                     Vec3d start = new Vec3d(posaX, posaY, posaZ);
                     Vec3d end = new Vec3d(posbX, posbY, posbZ);
                     EntityLivingBase entitylivingbase = GetMOP.findEntityOnPath(start, end, world, player, 0.2, 0.2, true);
                     if (entitylivingbase != null) {
                        SuperKnockback.applyKnockback(
                           entitylivingbase,
                           0.4F + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, itemstack) / 5,
                           player.posX,
                           player.posY,
                           player.posZ
                        );
                        IAttributeInstance entityAttribute = entitylivingbase.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE);
                        double baseValue = entityAttribute.getBaseValue();
                        entityAttribute.setBaseValue(1.0);
                        entitylivingbase.attackEntityFrom(
                           DamageSource.causePlayerDamage(player), 3.0F + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, itemstack) / 2.0F
                        );
                        entitylivingbase.hurtResistantTime = 0;
                        entityAttribute.setBaseValue(baseValue);
                     }
                  }
               }

               if (damage < this.getMaxDamage() && click && !hascooldown) {
                  player.getCooldownTracker().setCooldown(this, 7);
                  shoot(itemstack, 1, player.rotationPitch, player.rotationYaw, 0.0F, 0.9F);
                  shoot(itemstack, 2, player.rotationPitch, player.rotationYaw, 0.0F, 1.6F);
                  shoot(itemstack, 3, player.rotationPitch, player.rotationYaw, 0.0F, 1.3F);
                  shoot(itemstack, 4, player.rotationPitch, player.rotationYaw, 0.0F, 1.0F);
                  shoot(itemstack, 5, player.rotationPitch, player.rotationYaw, 0.0F, 0.8F);
                  shoot(itemstack, 6, player.rotationPitch, player.rotationYaw, 0.0F, 0.6F);
                  shoot(itemstack, 7, player.rotationPitch, player.rotationYaw, 0.0F, 0.4F);
               }
            }

            if (click2 && player.getHeldItemOffhand() == itemstack && damage < this.getMaxDamage() && !hascooldown2) {
            }
         }
      }
   }

   public void effectt(
      EntityPlayer player,
      World world,
      double x,
      double y,
      double z,
      double a,
      double b,
      double c,
      double d1,
      double d2,
      double d3,
      double v1,
      double v2,
      double v3
   ) {
      GUNParticle bigsmoke = new GUNParticle(texture2, 0.05F, 0.0F, 1, 230, world, x, y, z, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, false, 0);
      world.spawnEntity(bigsmoke);
      Vec3d fr = new Vec3d(x, y, z);
      Vec3d to = new Vec3d(d1, d2, d3);
      WhipParticle part = new WhipParticle(world, texture, 0.1F, 220, 1.0F, 1.0F, 1.0F, 0.0F, fr.distanceTo(to), 1, 0.0F, 8.0F, fr, to);
      part.setPosition(fr.x, fr.y, fr.z);
      part.startmotionX = (float)a;
      part.startmotionY = (float)b;
      part.startmotionZ = (float)c;
      part.targetmotionX = (float)v1;
      part.targetmotionY = (float)v2;
      part.targetmotionZ = (float)v3;
      world.spawnEntity(part);
   }

   @Override
   public boolean autoReload(ItemStack itemstack) {
      return false;
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return false;
   }

   @Override
   public boolean hasZoom(ItemStack itemstack) {
      return false;
   }

   @Override
   public int getCooldownTime(ItemStack itemstack) {
      return 0;
   }

   @Override
   public int getReloadTime(ItemStack itemstack) {
      return 0;
   }

   @Override
   public float getZoom(ItemStack itemstack, EntityPlayer player) {
      return 0.0F;
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.ONE_HANDED;
   }

   @Override
   public boolean canAttackMelee(ItemStack itemstack, EntityPlayer player) {
      return false;
   }

   public static void shoot(ItemStack stack, int segment, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity) {
      float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
      float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      shoot(stack, segment, (double)f, (double)f1, (double)f2, velocity);
   }

   public static void shoot(ItemStack stack, int segment, double x, double y, double z, float velocity) {
      float f = MathHelper.sqrt(x * x + y * y + z * z);
      x /= f;
      y /= f;
      z /= f;
      x *= velocity;
      y *= velocity;
      z *= velocity;
      NBTHelper.AddNBTdouble(stack, x, "motionX" + segment);
      NBTHelper.AddNBTdouble(stack, y, "motionY" + segment);
      NBTHelper.AddNBTdouble(stack, z, "motionZ" + segment);
   }
}
