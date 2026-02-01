package com.vivern.arpg.elements;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import baubles.api.render.IRenderBauble;
import com.vivern.arpg.entity.BetweenParticle;
import com.vivern.arpg.main.DeathEffects;
import com.vivern.arpg.main.IAttributedBauble;
import com.vivern.arpg.main.Mana;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.Team;
import com.vivern.arpg.main.WeaponDamage;
import com.vivern.arpg.main.WeaponParameters;
import com.vivern.arpg.main.Weapons;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

public class SparklingNecklace extends Item implements IBauble, IAttributedBauble, IRenderBauble {
   ResourceLocation start = new ResourceLocation("arpg:textures/plasma_beam.png");

   public SparklingNecklace() {
      this.setRegistryName("sparkling_necklace");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("sparkling_necklace");
      this.setMaxDamage(800);
      this.setMaxStackSize(1);
   }

   @Override
   public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, RenderType type, float partialTicks) {
      if (type == RenderType.BODY) {
         GlStateManager.pushMatrix();
         GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
         Helper.rotateIfSneaking(player);
         Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
         Minecraft.getMinecraft().getRenderItem().renderItem(stack, TransformType.HEAD);
         GlStateManager.popMatrix();
      }
   }

   @Override
   public BaubleType getBaubleType(ItemStack itemstack) {
      return BaubleType.AMULET;
   }

   @Override
   public IAttribute getAttribute() {
      return SharedMonsterAttributes.MOVEMENT_SPEED;
   }

   @Override
   public double value() {
      return 0.05;
   }

   @Override
   public int operation() {
      return 0;
   }

   @Override
   public String itemName() {
      return "sparkling_necklace";
   }

   @Override
   public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
      if (!((EntityPlayer)player).getCooldownTracker().hasCooldown(this)) {
         double max = Double.MAX_VALUE;
         EntityLivingBase targ = null;
         ((EntityPlayer)player).getCooldownTracker().setCooldown(this, 40 + itemRand.nextInt(10));
         double damageRadius = 5.0;
         AxisAlignedBB axisalignedbb = player.getEntityBoundingBox()
            .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
            .offset(-damageRadius, -damageRadius, -damageRadius);
         List<EntityLivingBase> list = player.world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
         Vec3d startPos = player.getPositionVector().add(0.0, player.height / 2.0F, 0.0);
         if (!list.isEmpty()) {
            for (EntityLivingBase entitylivingbase : list) {
               if (Team.checkIsOpponent(player, entitylivingbase) && entitylivingbase != player && EntitySelectors.NOT_SPECTATING.apply(entitylivingbase)) {
                  RayTraceResult raytraceresult = player.world
                     .rayTraceBlocks(
                        startPos, entitylivingbase.getPositionVector().add(0.0, entitylivingbase.height / 2.0F, 0.0), false, true, false
                     );
                  if (raytraceresult == null) {
                     double dist = entitylivingbase.getDistance(player);
                     if (dist < max) {
                        max = dist;
                        targ = entitylivingbase;
                     }
                  }
               }
            }
         }

         if (targ != null) {
            targ.hurtResistantTime = 0;
            Weapons.dealDamage(
               new WeaponDamage(itemstack, player, null, false, false, startPos, WeaponDamage.electric),
               4.0F * WeaponParameters.EXlevelPLASMA * Mana.getMagicPowerMax((EntityPlayer)player),
               player,
               targ,
               false
            );
            targ.hurtResistantTime = 0;
            if (targ.getHealth() <= 0.0F && itemRand.nextFloat() < 0.75) {
               DeathEffects.applyDeathEffect(targ, DeathEffects.DE_ELECTRIC);
            }

            player.world
               .playSound(
                  (EntityPlayer)null,
                  player.posX,
                  player.posY,
                  player.posZ,
                  Sounds.lightning_gh_grap,
                  SoundCategory.AMBIENT,
                  0.5F,
                  1.2F + itemRand.nextFloat() / 5.0F
               );
            if (player.world.isRemote) {
               Vec3d vect = player.getPositionVector().add(0.0, player.height / 1.4, 0.0);
               Vec3d newvec = targ.getPositionVector().add(0.0, targ.height / 2.0F, 0.0);
               if (vect.lengthSquared() > 1.0E-6 && newvec.lengthSquared() > 1.0E-6) {
                  BetweenParticle laser = new BetweenParticle(
                     player.world, this.start, 0.1F, 240, 1.0F, 1.0F, 1.0F, 0.1F, vect.distanceTo(newvec), 5, 0.3F, 1.0F, vect, newvec
                  );
                  laser.setPosition(vect.x, vect.y, vect.z);
                  laser.alphaGlowing = true;
                  player.world.spawnEntity(laser);
               }
            }
         }
      }
   }
}
