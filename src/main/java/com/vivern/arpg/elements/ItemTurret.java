package com.vivern.arpg.elements;

import com.vivern.arpg.main.EnchantmentInit;
import com.vivern.arpg.main.NBTHelper;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.Team;
import com.vivern.arpg.mobs.ToxicomaniaMobsPack;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;

public class ItemTurret extends Item {
   public ItemTurret() {
      this.setRegistryName("item_turret");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("item_turret");
      this.setMaxStackSize(1);
   }

   public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
      ItemStack itemstack = player.getHeldItem(hand);
      player.setActiveHand(hand);
      RayTraceResult raytraceresult = this.rayTrace(world, player, false);
      if (raytraceresult == null) {
         return new ActionResult(EnumActionResult.PASS, itemstack);
      } else if (raytraceresult.typeOfHit != Type.BLOCK) {
         return new ActionResult(EnumActionResult.PASS, itemstack);
      } else {
         BlockPos pos = raytraceresult.getBlockPos();
         BlockPos posup = pos.up();
         if (!world.checkBlockCollision(
               new AxisAlignedBB(
                  posup.getX() + 0.1,
                  posup.getY(),
                  posup.getZ() + 0.1,
                  posup.getX() + 0.9,
                  posup.getY() + 0.9,
                  posup.getZ() + 0.9
               )
            )
            && !world.isRemote) {
            ToxicomaniaMobsPack.Turret turret = new ToxicomaniaMobsPack.Turret(world);
            turret.setPosition(posup.getX() + 0.5, posup.getY(), posup.getZ() + 0.5);
            world.spawnEntity(turret);
            turret.onInitialSpawn();
            turret.setHealth(NBTHelper.GetNBTfloat(itemstack, "health"));
            if (itemstack.hasDisplayName()) {
               String cName = itemstack.getDisplayName();
               turret.setCustomNameTag(cName);
            }

            turret.team = Team.getTeamFor(player);
            turret.deployStage = 0.0F;
            turret.autoDeploy = false;
            turret.isAgressive = true;
            turret.infinityAmmo = false;
            turret.ammo = NBTHelper.GetNBTint(itemstack, "ammo");
            turret.bullets = ItemBullet.getItemBulletFromString(NBTHelper.GetNBTstring(itemstack, "bullet"));
            turret.enablePersistence();
            turret.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE)
               .setBaseValue(4.0F + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, itemstack) / 2.0F);
            itemstack.shrink(1);
            player.world
               .playSound(
                  (EntityPlayer)null,
                  turret.posX,
                  turret.posY,
                  turret.posZ,
                  Sounds.turret_set,
                  SoundCategory.PLAYERS,
                  0.9F,
                  0.9F + itemRand.nextFloat() / 5.0F
               );
            return new ActionResult(EnumActionResult.SUCCESS, itemstack);
         } else {
            return new ActionResult(EnumActionResult.FAIL, itemstack);
         }
      }
   }

   public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
      if (this.isInCreativeTab(tab)) {
         ItemStack stack = new ItemStack(this);

         while (stack.getTagCompound() == null || !stack.getTagCompound().hasKey("health")) {
            NBTHelper.GiveNBTfloat(stack, 55.0F, "health");
            NBTHelper.SetNBTfloat(stack, 55.0F, "health");
         }

         items.add(stack);
      }
   }
}
