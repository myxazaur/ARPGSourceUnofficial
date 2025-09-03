//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.entity.EntityThistleThorn;
import com.Vivern.Arpg.entity.GunPEmitter;
import com.Vivern.Arpg.main.Booom;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.Keys;
import com.Vivern.Arpg.main.Mana;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.Sounds;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Mouse;

public class ThistleThorn extends ItemWeapon {
   ResourceLocation eff = new ResourceLocation("arpg:textures/magic_effect_1.png");
   ResourceLocation sparkle = new ResourceLocation("arpg:textures/spellblue4.png");

   public ThistleThorn() {
      this.setRegistryName("thistle_thorn");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("thistle_thorn");
      this.setMaxDamage(1000);
      this.setMaxStackSize(1);
   }

   public int getMaxItemUseDuration(ItemStack itemstack) {
      return 72000;
   }

   public EnumAction getItemUseAction(ItemStack stack) {
      return EnumAction.BOW;
   }

   public boolean canContinueUsing(ItemStack oldStack, ItemStack newStack) {
      return true;
   }

   public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
      return false;
   }

   public void onUpdate(ItemStack itemstack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
      if (!worldIn.isRemote) {
         this.setCanShoot(itemstack, entityIn);
         if (IWeapon.canShoot(itemstack)) {
            EntityPlayer player = (EntityPlayer)entityIn;
            int damage = itemstack.getItemDamage();
            World world = player.getEntityWorld();
            Item itemIn = itemstack.getItem();
            EnumHand hand = player.getActiveHand();
            boolean click = Mouse.isButtonDown(1);
            boolean click2 = Keys.isKeyPressed(player, Keys.SECONDARYATTACK);
            int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
            float power = Mana.getMagicPowerMax(player);
            int sor = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SORCERY, itemstack);
            boolean spec = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack) > 0;
            NBTHelper.GiveNBTint(itemstack, 0, "charge");
            NBTHelper.GiveNBTboolean(itemstack, false, "powered");
            NBTHelper.GiveNBTboolean(itemstack, false, "using");
            int charge = NBTHelper.GetNBTint(itemstack, "charge");
            if (charge > (spec ? 5 : 4)) {
               NBTHelper.SetNBTboolean(itemstack, true, "powered");
            }

            if (player.getHeldItemMainhand() == itemstack && Mana.getMana(player) > 1.5F - sor / 3.3F && (click || click2)) {
               NBTHelper.SetNBTboolean(itemstack, true, "using");
               if (!player.getCooldownTracker().hasCooldown(itemIn)) {
                  if (click) {
                     world.playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.thistle,
                        SoundCategory.AMBIENT,
                        0.9F,
                        0.9F + itemRand.nextFloat() / 5.0F
                     );
                     player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
                     player.addStat(StatList.getObjectUseStats(this));
                     this.bom(false);
                     EntityThistleThorn projectile = new EntityThistleThorn(world, player, itemstack, power);
                     projectile.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.0F, 2 / (acc + 1));
                     projectile.setPosition(player.posX, player.posY + player.getEyeHeight() - 0.15, player.posZ);
                     world.spawnEntity(projectile);
                     if (!player.capabilities.isCreativeMode) {
                        Mana.changeMana(player, -1.5F + sor / 3.3F);
                        Mana.setManaSpeed(player, 0.001F);
                     }

                     RayTraceResult result = player.rayTrace(1.2, 1.0F);
                     if (result != null) {
                        IWeapon.fireEffect(
                           this,
                           player,
                           world,
                           32.0,
                           result.hitVec.x,
                           result.hitVec.y,
                           result.hitVec.z,
                           (double)player.rotationPitch,
                           (double)player.rotationYaw,
                           0.0,
                           0.0,
                           0.0,
                           0.0
                        );
                     }
                  } else if (click2 && NBTHelper.GetNBTboolean(itemstack, "powered")) {
                     world.playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.thistle,
                        SoundCategory.AMBIENT,
                        0.9F,
                        0.7F + itemRand.nextFloat() / 5.0F
                     );
                     player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack) + 5);
                     player.addStat(StatList.getObjectUseStats(this));
                     this.bom(true);
                     EntityThistleThorn projectile1 = new EntityThistleThorn(world, player, itemstack, power);
                     EntityThistleThorn projectile2 = new EntityThistleThorn(world, player, itemstack, power);
                     EntityThistleThorn projectile3 = new EntityThistleThorn(world, player, itemstack, power);
                     projectile1.other1 = projectile2;
                     projectile1.other2 = projectile3;
                     projectile2.other1 = projectile1;
                     projectile2.other2 = projectile3;
                     projectile3.other1 = projectile1;
                     projectile3.other2 = projectile2;
                     projectile1.canMultiple = true;
                     projectile1.charged = true;
                     projectile2.canMultiple = true;
                     projectile2.charged = true;
                     projectile3.canMultiple = true;
                     projectile3.charged = true;
                     projectile1.shoot(player, player.rotationPitch, player.rotationYaw - 10.0F, 0.0F, 1.0F, 2 / (acc + 1));
                     projectile2.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.0F, 2 / (acc + 1));
                     projectile3.shoot(player, player.rotationPitch, player.rotationYaw + 10.0F, 0.0F, 1.0F, 2 / (acc + 1));
                     projectile1.setPosition(player.posX, player.posY + player.getEyeHeight() - 0.15, player.posZ);
                     projectile2.setPosition(player.posX, player.posY + player.getEyeHeight() - 0.15, player.posZ);
                     projectile3.setPosition(player.posX, player.posY + player.getEyeHeight() - 0.15, player.posZ);
                     world.spawnEntity(projectile1);
                     world.spawnEntity(projectile2);
                     world.spawnEntity(projectile3);
                     if (!player.capabilities.isCreativeMode) {
                        Mana.changeMana(player, -1.5F + sor / 3.3F);
                        Mana.setManaSpeed(player, 0.001F);
                     }

                     if (spec) {
                        NBTHelper.AddNBTint(itemstack, -3, "charge");
                        if (charge - 3 <= 0) {
                           NBTHelper.SetNBTboolean(itemstack, false, "powered");
                        }
                     } else {
                        NBTHelper.SetNBTboolean(itemstack, false, "powered");
                        NBTHelper.SetNBTint(itemstack, 0, "charge");
                     }

                     IWeapon.fireEffect(
                        this,
                        player,
                        world,
                        32.0,
                        player.posX,
                        player.posY,
                        player.posZ,
                        (double)player.rotationPitch,
                        (double)player.rotationYaw,
                        1.0,
                        0.0,
                        0.0,
                        0.0
                     );
                  }
               }
            } else {
               NBTHelper.SetNBTboolean(itemstack, false, "using");
            }
         }
      }
   }

   @Override
   public void effect(EntityPlayer player, World world, double x, double y, double z, double a, double b, double c, double d1, double d2, double d3) {
      float rr = 0.95F;
      float gg = 1.0F;
      float bb = 0.25F;
      if (c == 1.0) {
         rr = 0.8F;
         gg = 0.27F;
         bb = 0.95F;
      }

      for (int i = 0; i < 2; i++) {
         GunPEmitter emit = new GunPEmitter(
            this.sparkle,
            0.02F,
            0.001F,
            15,
            240,
            world,
            x,
            y,
            z,
            0.0F,
            0.0F,
            0.0F,
            rr,
            gg,
            bb,
            true,
            1,
            false,
            this.sparkle,
            0.025F,
            0.003F,
            13,
            240,
            74.0F,
            74.0F,
            74.0F,
            rr,
            gg,
            bb,
            true
         );
         emit.alphaTickAdding = -0.01F;
         emit.scaleTickAdding = -0.001F;
         emit.EEsclTickAdd = -0.0016F;
         double fff = -Math.sin(b * (float) (Math.PI / 180.0)) * Math.cos(a * (float) (Math.PI / 180.0));
         double fff1 = -Math.sin(a * (float) (Math.PI / 180.0));
         double fff2 = Math.cos(b * (float) (Math.PI / 180.0)) * Math.cos(a * (float) (Math.PI / 180.0));
         emit.shoot(fff, fff1, fff2, 0.5F, 17.0F);
         emit.setPosition(x, y - 0.17, z);
         world.spawnEntity(emit);
      }
   }

   @SideOnly(Side.CLIENT)
   private void bom(boolean full) {
      if (full) {
         Booom.FOVlastTick = 9;
         Booom.FOVfrequency = -0.35F;
         Booom.FOVpower = 0.07F;
      } else {
         Booom.FOVlastTick = 6;
         Booom.FOVfrequency = -0.52F;
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
      return 6 - rapidity;
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
}
