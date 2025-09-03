//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.entity.GraplingHookParticle;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.Keys;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.SuperKnockback;
import com.Vivern.Arpg.main.Team;
import java.util.List;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Mouse;

public class ChainDagger extends Item {
   public ResourceLocation texture = new ResourceLocation("arpg:textures/chain_dagger_chain.png");
   public ResourceLocation texture2 = new ResourceLocation("arpg:textures/chain_dagger_pike.png");
   public int maxlength = 10;
   public float lengthMultiplier = 1.3F;
   public double grapRadius = 0.1;
   public int cooldown = 23;

   public ChainDagger() {
      this.setRegistryName("chain_dagger");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("chain_dagger");
      this.setMaxDamage(1000);
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

   public void onUpdate(ItemStack itemstack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
      if (entityIn instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)entityIn;
         int damage = itemstack.getItemDamage();
         World world = player.getEntityWorld();
         boolean click2 = GameSettings.isKeyDown(Keys.SECONDARYATTACK);
         boolean click = Mouse.isButtonDown(1);
         Item itemIn = itemstack.getItem();
         NBTHelper.GiveNBTboolean(itemstack, false, "throwed");
         NBTHelper.GiveNBTboolean(itemstack, false, "returning");
         NBTHelper.GiveNBTint(itemstack, 0, "length");
         NBTHelper.GiveNBTfloat(itemstack, 0.0F, "rotationPitch");
         NBTHelper.GiveNBTfloat(itemstack, 0.0F, "rotationYaw");
         NBTHelper.GiveNBTint(itemstack, 0, "attackCooldown");
         if (NBTHelper.GetNBTint(itemstack, "attackCooldown") > 0) {
            NBTHelper.AddNBTint(itemstack, -1, "attackCooldown");
         }

         if (player.getHeldItemOffhand() == itemstack) {
            NBTHelper.GiveNBTint(itemstack, 0, "lefthandcooldown");
            if (NBTHelper.GetNBTint(itemstack, "lefthandcooldown") > 0) {
               NBTHelper.AddNBTint(itemstack, -1, "lefthandcooldown");
            }
         }

         boolean throwed = NBTHelper.GetNBTboolean(itemstack, "throwed");
         boolean returning = NBTHelper.GetNBTboolean(itemstack, "returning");
         Vec3d vec = GetMOP.RotatedPosRayTrace(
            NBTHelper.GetNBTint(itemstack, "length") * this.lengthMultiplier,
            1.0F,
            player,
            0.05,
            0.05,
            NBTHelper.GetNBTfloat(itemstack, "rotationPitch"),
            NBTHelper.GetNBTfloat(itemstack, "rotationYaw")
         );
         AxisAlignedBB aabb = new AxisAlignedBB(
            vec.x - this.grapRadius,
            vec.y - this.grapRadius,
            vec.z - this.grapRadius,
            vec.x + this.grapRadius,
            vec.y + this.grapRadius,
            vec.z + this.grapRadius
         );
         if (NBTHelper.GetNBTint(itemstack, "length") <= 0) {
            if (returning) {
               NBTHelper.SetNBTboolean(itemstack, false, "returning");
            }
         } else {
            List<EntityLivingBase> list = world.getEntitiesWithinAABB(EntityLivingBase.class, aabb);
            if (!list.isEmpty()) {
               for (EntityLivingBase entitylivingbase : list) {
                  if (Team.checkIsOpponent(player, entitylivingbase)) {
                     NBTHelper.SetNBTboolean(itemstack, false, "throwed");
                     SuperKnockback.applyKnockback(
                        entitylivingbase,
                        -(0.8F + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, itemstack) / 3),
                        player.posX,
                        player.posY,
                        player.posZ
                     );
                     if (NBTHelper.GetNBTint(itemstack, "attackCooldown") <= 0) {
                        IAttributeInstance entityAttribute = entitylivingbase.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE);
                        double baseValue = entityAttribute.getBaseValue();
                        entityAttribute.setBaseValue(1.0);
                        entitylivingbase.attackEntityFrom(DamageSource.causePlayerDamage(player), 8 + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, itemstack));
                        entitylivingbase.hurtResistantTime = 0;
                        entityAttribute.setBaseValue(baseValue);
                        NBTHelper.SetNBTint(itemstack, 10, "attackCooldown");
                     }
                  }
               }
            }

            this.spawnParticle(player.getPositionEyes(0.0F), vec, world, player);
         }

         if (world.collidesWithAnyBlock(aabb) && throwed) {
            NBTHelper.SetNBTboolean(itemstack, false, "throwed");
            NBTHelper.SetNBTboolean(itemstack, true, "returning");
            this.onGrap(player, itemstack);
         }

         if (player.getHeldItemOffhand() == itemstack) {
            if (click2 && NBTHelper.GetNBTint(itemstack, "lefthandcooldown") <= 0 && this.canThrow(player, itemstack)) {
               NBTHelper.SetNBTfloat(itemstack, player.rotationPitch, "rotationPitch");
               NBTHelper.SetNBTfloat(itemstack, player.rotationYaw, "rotationYaw");
               NBTHelper.SetNBTboolean(itemstack, true, "throwed");
               NBTHelper.SetNBTboolean(itemstack, false, "returning");
               NBTHelper.SetNBTint(itemstack, this.cooldown, "lefthandcooldown");
               this.onThrow(player, itemstack);
            }
         } else if (click && !player.getCooldownTracker().hasCooldown(itemIn) && this.canThrow(player, itemstack) && player.getActiveItemStack() == itemstack) {
            NBTHelper.SetNBTfloat(itemstack, player.rotationPitch, "rotationPitch");
            NBTHelper.SetNBTfloat(itemstack, player.rotationYaw, "rotationYaw");
            NBTHelper.SetNBTboolean(itemstack, true, "throwed");
            NBTHelper.SetNBTboolean(itemstack, false, "returning");
            player.getCooldownTracker().setCooldown(this, this.cooldown);
            this.onThrow(player, itemstack);
         }

         if (NBTHelper.GetNBTint(itemstack, "length") >= this.maxlength) {
            NBTHelper.SetNBTboolean(itemstack, false, "throwed");
         }

         if (throwed) {
            if (NBTHelper.GetNBTint(itemstack, "length") < this.maxlength) {
               NBTHelper.AddNBTint(itemstack, 1, "length");
            }
         } else if (NBTHelper.GetNBTint(itemstack, "length") > 0) {
            NBTHelper.AddNBTint(itemstack, -1, "length");
         }
      }
   }

   public void onThrow(EntityPlayer player, ItemStack itemstack) {
      player.world
         .playSound(
            (EntityPlayer)null,
            player.posX,
            player.posY,
            player.posZ,
            Sounds.chain_dagger,
            SoundCategory.PLAYERS,
            0.7F,
            0.95F + itemRand.nextFloat() / 10.0F
         );
   }

   public void onGrap(EntityPlayer player, ItemStack itemstack) {
      if (!player.capabilities.isCreativeMode) {
         itemstack.damageItem(1, player);
      }

      player.world
         .playSound(
            (EntityPlayer)null,
            player.posX,
            player.posY,
            player.posZ,
            Sounds.sword_hit,
            SoundCategory.PLAYERS,
            0.5F,
            0.9F + itemRand.nextFloat() / 5.0F
         );
   }

   public void onGrapTick(EntityPlayer player, ItemStack itemstack) {
      if (player.motionY < 0.2) {
         player.fallDistance = 0.0F;
      }
   }

   public boolean canThrow(EntityPlayer player, ItemStack itemstack) {
      return itemstack.getItemDamage() < this.getMaxDamage(itemstack);
   }

   @SideOnly(Side.CLIENT)
   public void spawnParticle(Vec3d pos1, Vec3d pos2, World world, EntityPlayer player) {
      if (world.isRemote && pos1.lengthSquared() > 1.0E-6 && pos2.lengthSquared() > 1.0E-6) {
         GraplingHookParticle laser = new GraplingHookParticle(
            world,
            this.texture,
            this.texture2,
            0.05F,
            -1,
            1.0F,
            1.0F,
            1.0F,
            0.0F,
            pos1.distanceTo(pos2),
            1,
            0.0F,
            0.5F,
            player.ticksExisted,
            0.25F,
            0.2F,
            pos1,
            pos2
         );
         laser.setPosition(pos1.x, pos1.y - 0.3, pos1.z);
         world.spawnEntity(laser);
      }
   }
}
