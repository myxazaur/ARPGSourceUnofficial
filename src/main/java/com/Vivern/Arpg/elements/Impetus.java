package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.entity.EntityStreamLaserP;
import com.Vivern.Arpg.main.Booom;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.Keys;
import com.Vivern.Arpg.main.Mana;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.SuperKnockback;
import com.Vivern.Arpg.renders.GUNParticle;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Impetus extends ItemWeapon {
   ResourceLocation largesmoke = new ResourceLocation("arpg:textures/largecloud.png");
   ResourceLocation start = new ResourceLocation("arpg:textures/ice_beam.png");
   ResourceLocation snow = new ResourceLocation("arpg:textures/snowflake2.png");

   public Impetus() {
      this.setRegistryName("impetus");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("impetus");
      this.setMaxDamage(100);
      this.setMaxStackSize(1);
   }

   public int getMaxItemUseDuration(ItemStack itemstack) {
      return 72000;
   }

   public EnumAction getItemUseAction(ItemStack stack) {
      return EnumAction.BOW;
   }

   public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
      ItemStack itemstack = player.getHeldItem(hand);
      player.setActiveHand(hand);
      return new ActionResult(EnumActionResult.PASS, itemstack);
   }

   public boolean canContinueUsing(ItemStack oldStack, ItemStack newStack) {
      return true;
   }

   public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
      return false;
   }

   public void onUpdate(ItemStack itemstack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
      this.setCanShoot(itemstack, entityIn);
      if (IWeapon.canShoot(itemstack)) {
         EntityPlayer player = (EntityPlayer)entityIn;
         int damage = itemstack.getItemDamage();
         World world = player.getEntityWorld();
         Item itemIn = itemstack.getItem();
         EnumHand hand = player.getActiveHand();
         boolean click = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
         float mana = Mana.getMana(player);
         float spee = Mana.getManaSpeed(player);
         float power = Mana.getMagicPowerMax(player);
         int rapidity = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack);
         int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
         int sor = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SORCERY, itemstack);
         int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, itemstack);
         int impulse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, itemstack);
         int charge = NBTHelper.GetNBTint(itemstack, "usecharge");
         int maxcharge = 100 + impulse * 5;
         if (player.getHeldItemMainhand() == itemstack && mana > 0.1F - sor / 55.0F && click && !player.getCooldownTracker().hasCooldown(itemIn)) {
            NBTHelper.GiveNBTint(itemstack, 0, "usecharge");
            if (charge < maxcharge) {
               NBTHelper.AddNBTint(itemstack, 1, "usecharge");
               if (!player.capabilities.isCreativeMode) {
                  Mana.changeMana(player, -0.1F + sor / 55.0F);
                  Mana.setManaSpeed(player, 0.001F);
               }
            }

            if (player.ticksExisted % 10 == 0) {
               world.playSound(
                  (EntityPlayer)null,
                  player.posX,
                  player.posY,
                  player.posZ,
                  Sounds.impetus_wind,
                  SoundCategory.AMBIENT,
                  0.8F,
                  0.6F + charge / 125.0F
               );
            }
         } else if (charge > 0 && player.getHeldItemMainhand() == itemstack && !click && !player.getCooldownTracker().hasCooldown(itemIn)) {
            if (charge < 30) {
               double edist = 4.0 + charge / (10.0 - acc);
               Vec3d vec = GetMOP.PosRayTrace(edist, 1.0F, player, 1.0, 0.8);
               world.playSound(
                  (EntityPlayer)null,
                  player.posX,
                  player.posY,
                  player.posZ,
                  Sounds.impetus_1,
                  SoundCategory.AMBIENT,
                  0.8F,
                  0.9F + itemRand.nextFloat() / 5.0F
               );
               player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
               this.bom(1);
               NBTHelper.SetNBTint(itemstack, 0, "usecharge");
               double damageRadius = 0.4;
               AxisAlignedBB aabb = new AxisAlignedBB(
                  vec.x - damageRadius,
                  vec.y - damageRadius,
                  vec.z - damageRadius,
                  vec.x + damageRadius,
                  vec.y + damageRadius,
                  vec.z + damageRadius
               );
               List<Entity> list = worldIn.getEntitiesWithinAABB(Entity.class, aabb);
               if (!list.isEmpty()) {
                  for (Entity entitylivingbase : list) {
                     if (entitylivingbase != player && !worldIn.isRemote) {
                        if (entitylivingbase.onGround) {
                           entitylivingbase.motionY += 0.6;
                        }

                        SuperKnockback.applyKnockback(
                           entitylivingbase, 0.5F + charge / (15.0F - impulse), player.posX, player.posY, player.posZ
                        );
                     }
                  }
               }
            } else if (charge < 60) {
               double edist = 4.0 + charge / (9.0 - acc);
               List<Entity> list = GetMOP.entityMopRayTrace(Entity.class, edist, 1.0F, player, 1.5, 1.0, player.rotationPitch, player.rotationYaw);
               world.playSound(
                  (EntityPlayer)null,
                  player.posX,
                  player.posY,
                  player.posZ,
                  Sounds.impetus_2,
                  SoundCategory.AMBIENT,
                  1.5F,
                  0.9F + itemRand.nextFloat() / 5.0F
               );
               player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
               this.bom(2);
               NBTHelper.SetNBTint(itemstack, 0, "usecharge");
               if (!list.isEmpty()) {
                  for (Entity entitylivingbasex : list) {
                     if (entitylivingbasex != player && !worldIn.isRemote) {
                        if (entitylivingbasex.onGround) {
                           entitylivingbasex.motionY += 0.9;
                        }

                        SuperKnockback.applyKnockback(
                           entitylivingbasex, 0.5F + charge / (15.0F - impulse), player.posX, player.posY, player.posZ
                        );
                     }
                  }
               }
            } else {
               world.playSound(
                  (EntityPlayer)null,
                  player.posX,
                  player.posY,
                  player.posZ,
                  Sounds.impetus_3,
                  SoundCategory.AMBIENT,
                  3.0F,
                  0.9F + itemRand.nextFloat() / 5.0F
               );
               player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
               this.bom(3);
               NBTHelper.SetNBTint(itemstack, 0, "usecharge");
               List<Entity> alllist = new ArrayList<>();

               for (int i = 0; i < 14; i++) {
                  float rotP = player.rotationPitch + (float)itemRand.nextGaussian() * (7.0F - acc / 2.0F);
                  float rotY = player.rotationYaw + (float)itemRand.nextGaussian() * (7.0F - acc / 2.0F);
                  double edist = 4.0 + charge / (8.0 - acc);
                  List<Entity> list = GetMOP.entityMopRayTrace(Entity.class, edist, 1.0F, player, 2.5, 1.0, rotP, rotY);
                  if (!list.isEmpty()) {
                     for (Entity entityl : list) {
                        if (!alllist.contains(entityl)) {
                           alllist.add(entityl);
                        }
                     }
                  }
               }

               if (!alllist.isEmpty()) {
                  for (Entity entitylivingbasexx : alllist) {
                     if (entitylivingbasexx != player && !worldIn.isRemote) {
                        if (entitylivingbasexx.onGround) {
                           entitylivingbasexx.motionY++;
                        }

                        SuperKnockback.applyKnockback(
                           entitylivingbasexx, 0.5F + charge / (15.0F - impulse), player.posX, player.posY, player.posZ
                        );
                     }
                  }
               }
            }

            float horizoffset = 0.0F;
            if (player.getHeldItemMainhand() == itemstack) {
               horizoffset = player.getPrimaryHand() == EnumHandSide.RIGHT ? 0.2F : -0.2F;
            }

            if (player.getHeldItemOffhand() == itemstack) {
               horizoffset = player.getPrimaryHand() == EnumHandSide.RIGHT ? -0.2F : 0.2F;
            }
         }
      }
   }

   @Override
   public void effect(EntityPlayer player, World world, double x, double y, double z, double a, double b, double c, double d1, double d2, double d3) {
      GUNParticle bigsmoke = new GUNParticle(
         this.largesmoke,
         0.8F + (float)itemRand.nextGaussian() / 13.0F,
         0.0F,
         15,
         240,
         world,
         x,
         y,
         z,
         (float)itemRand.nextGaussian() / 20.0F,
         (float)itemRand.nextGaussian() / 25.0F,
         (float)itemRand.nextGaussian() / 20.0F,
         0.6F + (float)itemRand.nextGaussian() / 10.0F,
         0.7F,
         1.0F,
         true,
         itemRand.nextInt(360)
      );
      bigsmoke.alphaTickAdding = -0.05F;
      bigsmoke.alphaGlowing = true;
      world.spawnEntity(bigsmoke);
      GUNParticle bigsmoke2 = new GUNParticle(
         this.snow,
         0.15F + (float)itemRand.nextGaussian() / 20.0F,
         0.01F,
         10 + itemRand.nextInt(20),
         180,
         world,
         x,
         y,
         z,
         (float)itemRand.nextGaussian() / 20.0F,
         (float)itemRand.nextGaussian() / 20.0F,
         (float)itemRand.nextGaussian() / 20.0F,
         1.0F,
         1.0F,
         1.0F,
         false,
         itemRand.nextInt(360),
         true,
         1.3F
      );
      world.spawnEntity(bigsmoke2);
      double dd0 = d1 - x;
      double dd1 = d2 - y;
      double dd2 = d3 - z;
      double dist = MathHelper.sqrt(dd0 * dd0 + dd1 * dd1 + dd2 * dd2);
      EntityStreamLaserP laser = new EntityStreamLaserP(
         world, this.start, 0.21F, 240, 1.0F, 0.9F, 0.9F, 0.5F, dist, 1, 0.3F, 8.0F, (float)a, (float)b, player.ticksExisted
      );
      laser.setPosition(d1, d2 + 1.55, d3);
      laser.horizOffset = (float)c;
      world.spawnEntity(laser);
   }

   @SideOnly(Side.CLIENT)
   @Override
   public void bom(int i) {
      if (i == 1) {
         Booom.lastTick = 11;
         Booom.frequency = -0.285F;
         Booom.x = 1.0F;
         Booom.y = (float)itemRand.nextGaussian() / 10.0F;
         Booom.z = (float)itemRand.nextGaussian() / 10.0F;
         Booom.power = 0.15F;
      }

      if (i == 2) {
         Booom.lastTick = 13;
         Booom.frequency = -0.245F;
         Booom.x = 1.0F;
         Booom.y = 0.0F;
         Booom.z = 0.0F;
         Booom.power = 0.25F;
         Booom.FOVlastTick = 6;
         Booom.FOVfrequency = -0.5F;
         Booom.FOVpower = 0.04F;
      }

      if (i == 3) {
         Booom.lastTick = 35;
         Booom.frequency = -0.18F;
         Booom.x = (float)itemRand.nextGaussian() / 5.0F;
         Booom.y = (float)itemRand.nextGaussian() / 5.0F;
         Booom.z = (float)itemRand.nextGaussian() / 5.0F;
         Booom.power = 0.55F;
         Booom.FOVlastTick = 20;
         Booom.FOVfrequency = -0.42F;
         Booom.FOVpower = 0.03F;
      }
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.TWO_HANDED;
   }

   @Override
   public int getCooldownTime(ItemStack itemstack) {
      int rapidity = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack);
      return 15 - rapidity * 4;
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
   public int getItemEnchantability() {
      return 20;
   }

   @Override
   public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
      return enchantment.type == EnchantmentInit.enchantmentTypeWeapon;
   }
}
