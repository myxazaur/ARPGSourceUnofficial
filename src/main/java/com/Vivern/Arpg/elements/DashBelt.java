package com.Vivern.Arpg.elements;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import baubles.api.render.IRenderBauble;
import com.Vivern.Arpg.elements.models.AbstractMobModel;
import com.Vivern.Arpg.elements.models.BeltsModel;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.Keys;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.Sounds;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class DashBelt extends Item implements IBauble, IRenderBauble {
   public static BeltsModel beltsModel = new BeltsModel();
   public ResourceLocation texture;
   public ResourceLocation textureGlowOverlay;
   public float exhaustion = 0.1F;
   public float dashVelocity = 0.7F;
   public boolean canDoVerticalDash = false;

   public DashBelt(String name, ResourceLocation texture) {
      this.setRegistryName(name);
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey(name);
      this.setMaxStackSize(1);
      this.texture = texture;
   }

   @Override
   public BaubleType getBaubleType(ItemStack itemstack) {
      return BaubleType.BELT;
   }

   @Override
   public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) {
      return player instanceof EntityPlayer ? !((EntityPlayer)player).getCooldownTracker().hasCooldown(this) : true;
   }

   public boolean canRecharge(EntityPlayer player) {
      return player.onGround || player.isInWater();
   }

   @Override
   public void onWornTick(ItemStack itemstack, EntityLivingBase entityIn) {
      this.applySlip(entityIn, itemstack);
      if (!entityIn.world.isRemote && entityIn instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)entityIn;
         NBTHelper.GiveNBTint(itemstack, 0, "charges");
         NBTHelper.GiveNBTint(itemstack, 0, "recharge");
         int charges = NBTHelper.GetNBTint(itemstack, "charges");
         int recharge = NBTHelper.GetNBTint(itemstack, "recharge");
         int oneCharge = this.getRechargeTime(itemstack, player, this.dashVelocity);
         int maxCharges = 1;
         if (recharge > 0) {
            NBTHelper.AddNBTint(itemstack, -1, "recharge");
         } else if (recharge == 0 && this.canRecharge(player)) {
            NBTHelper.SetNBTint(itemstack, -1, "recharge");
            NBTHelper.SetNBTint(itemstack, maxCharges, "charges");
         }

         if (this.checkAndUpdateBeltCooldown(itemstack) && charges > 0 && Keys.isKeyPressed(player, Keys.SPRINT) && !player.isElytraFlying()) {
            Vec2f dashVec = this.getNormalizedVectorOfDash(player);
            if (dashVec != null) {
               this.doSimpleDash(player, new Vec3d(dashVec.x, 0.0, dashVec.y), this.dashVelocity);
               player.motionY += 0.1F;
               player.getCooldownTracker().setCooldown(this, this.getSlipTime(itemstack, player, this.dashVelocity));
               this.setBeltCooldown(itemstack, player, this.dashVelocity);
               player.addExhaustion(this.exhaustion);
               player.world
                  .playSound(
                     (EntityPlayer)null,
                     player.posX,
                     player.posY,
                     player.posZ,
                     Sounds.dash_a,
                     SoundCategory.PLAYERS,
                     0.7F,
                     0.9F + itemRand.nextFloat() / 5.0F
                  );
               NBTHelper.AddNBTint(itemstack, -1, "charges");
               NBTHelper.AddNBTint(itemstack, oneCharge, "recharge");
            }
         }
      }
   }

   public void doSimpleDash(EntityLivingBase entity, Vec3d dashDirectionNormalized, float velocity) {
      entity.motionX = entity.motionX + dashDirectionNormalized.x * velocity;
      entity.motionY = entity.motionY + dashDirectionNormalized.y * velocity;
      entity.motionZ = entity.motionZ + dashDirectionNormalized.z * velocity;
      entity.velocityChanged = true;
   }

   public void doAdvancedDash(EntityLivingBase entity, ItemStack itemstack, Vec3d dashDirectionNormalized, float velocity) {
      entity.motionX *= 0.8;
      entity.motionZ *= 0.8;
      entity.motionX = entity.motionX + dashDirectionNormalized.x * velocity;
      entity.motionY = entity.motionY + dashDirectionNormalized.y * velocity;
      entity.motionZ = entity.motionZ + dashDirectionNormalized.z * velocity;
      NBTHelper.GiveNBTint(itemstack, 0, "dashtime");
      NBTHelper.SetNBTint(itemstack, this.getCooldown(itemstack, entity, velocity), "dashtime");
      entity.velocityChanged = true;
      NBTHelper.GiveNBTdouble(itemstack, 0.0, "posx");
      NBTHelper.GiveNBTdouble(itemstack, 0.0, "posy");
      NBTHelper.GiveNBTdouble(itemstack, 0.0, "posz");
      NBTHelper.SetNBTdouble(itemstack, entity.posX, "posx");
      NBTHelper.SetNBTdouble(itemstack, entity.posY, "posy");
      NBTHelper.SetNBTdouble(itemstack, entity.posZ, "posz");
      if (this.canDoVerticalDash) {
         if (dashDirectionNormalized.y != 0.0) {
            NBTHelper.GiveNBTboolean(itemstack, false, "isvertical");
            NBTHelper.SetNBTboolean(itemstack, true, "isvertical");
         } else {
            NBTHelper.SetNBTboolean(itemstack, false, "isvertical");
         }
      }
   }

   public void updateAdvancedDash(EntityLivingBase entity, ItemStack itemstack, float maximumDistance, float levitateRate, float stopMotionMultiplier) {
      int timelast = NBTHelper.GetNBTint(itemstack, "dashtime");
      if (timelast > 0) {
         if (!this.canDoVerticalDash || !NBTHelper.GetNBTboolean(itemstack, "isvertical")) {
            entity.motionY *= 1.0F - levitateRate;
            entity.fallDistance = 0.0F;
         }

         if (entity.motionY >= 0.0) {
            entity.fallDistance = 0.0F;
         }

         NBTHelper.AddNBTint(itemstack, -1, "dashtime");
         double x = NBTHelper.GetNBTdouble(itemstack, "posx");
         double y = NBTHelper.GetNBTdouble(itemstack, "posy");
         double z = NBTHelper.GetNBTdouble(itemstack, "posz");
         double dist = entity.getDistanceSq(x, y, z);
         AxisAlignedBB aabb1 = entity.getEntityBoundingBox().offset(entity.motionX, 0.0, entity.motionZ).grow(-0.1);
         if (timelast == 1 || dist >= maximumDistance * maximumDistance || !entity.world.getCollisionBoxes(entity, aabb1).isEmpty()) {
            entity.motionX *= stopMotionMultiplier;
            entity.motionZ *= stopMotionMultiplier;
            if (this.canDoVerticalDash) {
               entity.motionY *= stopMotionMultiplier;
            }

            NBTHelper.SetNBTint(itemstack, 0, "dashtime");
            if (entity instanceof EntityPlayer) {
               EntityPlayer player = (EntityPlayer)entity;
               player.getCooldownTracker().setCooldown(itemstack.getItem(), 0);
            }
         }

         entity.velocityChanged = true;
         if (entity.onGround) {
            entity.onGround = false;
         }
      }
   }

   public void applySlip(EntityLivingBase entity, ItemStack itemstack) {
      if (entity instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)entity;
         if (player.getCooldownTracker().hasCooldown(itemstack.getItem())) {
            entity.onGround = false;
         }
      }
   }

   @Nullable
   public Vec2f getNormalizedVectorOfDash(EntityPlayer player) {
      float velocitythis = 1.0F;
      float velocity = 0.0F;
      int angle = 0;
      boolean FORWARD = Keys.isKeyPressed(player, Keys.FORWARD);
      boolean BACK = Keys.isKeyPressed(player, Keys.BACK);
      boolean LEFT = Keys.isKeyPressed(player, Keys.LEFT);
      boolean RIGHT = Keys.isKeyPressed(player, Keys.RIGHT);
      if (FORWARD) {
         velocity = velocitythis;
      }

      if (BACK) {
         velocity = velocitythis;
         angle = 180;
      }

      if (RIGHT) {
         angle = 90;
         velocity = velocitythis;
      }

      if (LEFT) {
         angle = -90;
         velocity = velocitythis;
      }

      if (LEFT && RIGHT) {
         angle = 0;
         velocity = 0.0F;
      }

      if (FORWARD && BACK) {
         angle = 0;
         velocity = 0.0F;
      }

      if (RIGHT && FORWARD) {
         angle = 45;
         velocity = velocitythis;
      }

      if (LEFT && FORWARD) {
         angle = -45;
         velocity = velocitythis;
      }

      if (RIGHT && BACK) {
         angle = 135;
         velocity = velocitythis;
      }

      if (LEFT && BACK) {
         angle = -135;
         velocity = velocitythis;
      }

      if (velocity != 0.0F) {
         float rotationYawIn = player.rotationYaw + angle;
         float x = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0));
         float z = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0));
         float f = MathHelper.sqrt(x * x + z * z);
         x /= f;
         z /= f;
         return new Vec2f(x, z);
      } else {
         return null;
      }
   }

   @Nullable
   public Vec3d getNormalized3dVectorOfDash(EntityPlayer player) {
      float velocitythis = 1.0F;
      float velocity = 0.0F;
      int angle = 0;
      boolean FORWARD = Keys.isKeyPressed(player, Keys.FORWARD);
      boolean BACK = Keys.isKeyPressed(player, Keys.BACK);
      boolean LEFT = Keys.isKeyPressed(player, Keys.LEFT);
      boolean RIGHT = Keys.isKeyPressed(player, Keys.RIGHT);
      boolean SHIFT = player.isSneaking();
      boolean JUMP = Keys.isKeyPressed(player, Keys.JUMP);
      if (FORWARD) {
         velocity = velocitythis;
      }

      if (BACK) {
         velocity = velocitythis;
         angle = 180;
      }

      if (RIGHT) {
         angle = 90;
         velocity = velocitythis;
      }

      if (LEFT) {
         angle = -90;
         velocity = velocitythis;
      }

      if (LEFT && RIGHT) {
         angle = 0;
         velocity = 0.0F;
      }

      if (FORWARD && BACK) {
         angle = 0;
         velocity = 0.0F;
      }

      if (RIGHT && FORWARD) {
         angle = 45;
         velocity = velocitythis;
      }

      if (LEFT && FORWARD) {
         angle = -45;
         velocity = velocitythis;
      }

      if (RIGHT && BACK) {
         angle = 135;
         velocity = velocitythis;
      }

      if (LEFT && BACK) {
         angle = -135;
         velocity = velocitythis;
      }

      if (velocity != 0.0F && !SHIFT && !JUMP) {
         float rotationYawIn = player.rotationYaw + angle;
         float x = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0));
         float z = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0));
         float f = MathHelper.sqrt(x * x + z * z);
         x /= f;
         z /= f;
         return new Vec3d(x, 0.0, z);
      } else if (velocity == 0.0F || !SHIFT && !JUMP) {
         if (velocity == 0.0F && (SHIFT || JUMP)) {
            if (SHIFT) {
               return new Vec3d(0.0, -1.0, 0.0);
            }

            if (JUMP) {
               return new Vec3d(0.0, 1.0, 0.0);
            }
         }

         return null;
      } else {
         int pitch = 0;
         if (SHIFT) {
            pitch += 45;
         }

         if (JUMP) {
            pitch -= 45;
         }

         return GetMOP.PitchYawToVec3d(pitch, player.rotationYaw + angle);
      }
   }

   public int getCooldown(ItemStack itemStack, EntityLivingBase player, float velocity) {
      return 6;
   }

   public int getRechargeTime(ItemStack itemStack, EntityLivingBase player, float velocity) {
      return 45;
   }

   public int getSlipTime(ItemStack itemStack, EntityLivingBase player, float velocity) {
      return this.getCooldown(itemStack, player, velocity);
   }

   public boolean hasBeltCooldown(ItemStack itemStack) {
      return NBTHelper.GetNBTint(itemStack, "beltcooldown") > 0;
   }

   public void setBeltCooldown(ItemStack itemStack, EntityLivingBase player, float velocity) {
      int cd = this.getCooldown(itemStack, player, velocity);
      NBTHelper.GiveNBTint(itemStack, cd, "beltcooldown");
      NBTHelper.SetNBTint(itemStack, cd, "beltcooldown");
   }

   public boolean checkAndUpdateBeltCooldown(ItemStack itemStack) {
      int bc = NBTHelper.GetNBTint(itemStack, "beltcooldown");
      if (bc > 0) {
         NBTHelper.AddNBTint(itemStack, -1, "beltcooldown");
      }

      return bc <= 0;
   }

   @Override
   public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, RenderType type, float partialTicks) {
      if (type == RenderType.BODY) {
         GlStateManager.pushMatrix();
         RenderHelper.enableStandardItemLighting();
         GlStateManager.tryBlendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
         GlStateManager.enableBlend();
         GlStateManager.disableCull();
         Helper.rotateIfSneaking(player);
         float scale = 0.0625F;
         GlStateManager.scale(scale, scale, scale);
         Minecraft.getMinecraft().renderEngine.bindTexture(this.texture);
         this.readyModel(player, beltsModel);
         beltsModel.render(player, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
         if (this.textureGlowOverlay != null) {
            AbstractMobModel.light(240, false);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.textureGlowOverlay);
            beltsModel.render(player, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
            AbstractMobModel.returnlight();
         }

         GlStateManager.disableBlend();
         GlStateManager.enableCull();
         GlStateManager.popMatrix();
      }
   }

   public void readyModel(EntityPlayer player, BeltsModel model) {
      model.beltBended1.isHidden = true;
      model.beltBended2.isHidden = true;
      model.beltRibbons.isHidden = true;
      model.beltKnotSmall.isHidden = true;
      model.beltClassic.isHidden = true;
      model.beltBig.isHidden = true;
      model.beltKnotBig.isHidden = true;
      model.beltPotions1.isHidden = true;
      model.beltPotions2.isHidden = true;
      model.beltPotions3.isHidden = true;
      model.beltBag.isHidden = true;
      model.beltKnotRightFront.isHidden = true;
      model.beltKnotRightBack.isHidden = true;
      model.beltKnotLeftFront.isHidden = true;
      model.beltKnotLeftBack.isHidden = true;
      model.beltKnotSmallBack.isHidden = true;
      model.beltKnotBigBack.isHidden = true;
      model.beltBigDiagonal.isHidden = true;
   }

   public static class BeltOfShadows extends DashBelt {
      public BeltOfShadows() {
         super("belt_of_shadows", new ResourceLocation("arpg:textures/belt_of_shadows_tex.png"));
         this.dashVelocity = 1.0F;
         this.exhaustion = 0.13F;
         this.textureGlowOverlay = new ResourceLocation("arpg:textures/belt_of_shadows_tex_overlay.png");
      }

      @Override
      public int getCooldown(ItemStack itemStack, EntityLivingBase player, float velocity) {
         return 7;
      }

      @Override
      public int getRechargeTime(ItemStack itemStack, EntityLivingBase player, float velocity) {
         return 18;
      }

      @Override
      public void readyModel(EntityPlayer player, BeltsModel model) {
         super.readyModel(player, model);
         model.beltBig.isHidden = false;
         model.beltBag.isHidden = false;
         model.beltPotions1.isHidden = false;
         model.beltPotions3.isHidden = false;
      }

      @Override
      public void onWornTick(ItemStack itemstack, EntityLivingBase entityIn) {
         this.applySlip(entityIn, itemstack);
         if (!entityIn.world.isRemote && entityIn instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entityIn;
            NBTHelper.GiveNBTint(itemstack, 0, "charges");
            NBTHelper.GiveNBTint(itemstack, 0, "recharge");
            int charges = NBTHelper.GetNBTint(itemstack, "charges");
            int recharge = NBTHelper.GetNBTint(itemstack, "recharge");
            int oneCharge = this.getRechargeTime(itemstack, player, this.dashVelocity);
            int maxCharges = 3;
            if (recharge > 0) {
               NBTHelper.AddNBTint(itemstack, -1, "recharge");
            } else if (recharge == 0 && this.canRecharge(player)) {
               NBTHelper.SetNBTint(itemstack, -1, "recharge");
               NBTHelper.SetNBTint(itemstack, maxCharges, "charges");
               player.world
                  .playSound(
                     (EntityPlayer)null,
                     player.posX,
                     player.posY,
                     player.posZ,
                     Sounds.dash_ready_shadow,
                     SoundCategory.PLAYERS,
                     0.5F,
                     0.9F + itemRand.nextFloat() / 5.0F
                  );
            }

            if (this.checkAndUpdateBeltCooldown(itemstack) && charges > 0 && Keys.isKeyPressed(player, Keys.SPRINT) && !player.isElytraFlying()) {
               Vec2f dashVec = this.getNormalizedVectorOfDash(player);
               if (dashVec != null) {
                  this.doAdvancedDash(player, itemstack, new Vec3d(dashVec.x, 0.0, dashVec.y), this.dashVelocity);
                  player.motionY += 0.15F;
                  player.getCooldownTracker().setCooldown(this, this.getSlipTime(itemstack, player, this.dashVelocity));
                  this.setBeltCooldown(itemstack, player, this.dashVelocity);
                  player.addExhaustion(this.exhaustion);
                  player.world
                     .playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.dash_a,
                        SoundCategory.PLAYERS,
                        0.7F,
                        0.9F + itemRand.nextFloat() / 5.0F
                     );
                  NBTHelper.AddNBTint(itemstack, -1, "charges");
                  NBTHelper.AddNBTint(itemstack, oneCharge, "recharge");
               }
            }

            this.updateAdvancedDash(player, itemstack, 5.0F, 0.2F, 0.5F);
         }
      }

      public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
         tooltip.add("Allows do three dashes");
         tooltip.add("Extinguishes inertia");
         super.addInformation(stack, worldIn, tooltip, flagIn);
      }
   }

   public static class DashBeltBlack extends DashBelt {
      public DashBeltBlack() {
         super("dash_belt_black", new ResourceLocation("arpg:textures/dash_belt_black_tex.png"));
      }

      @Override
      public void readyModel(EntityPlayer player, BeltsModel model) {
         super.readyModel(player, model);
         model.beltBended1.isHidden = false;
         model.beltBended2.isHidden = false;
         model.beltKnotSmall.isHidden = false;
         model.beltRibbons.isHidden = false;
      }

      public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
         tooltip.add("Allows do dash in any horizontal direction");
         super.addInformation(stack, worldIn, tooltip, flagIn);
      }
   }

   public static class HellhoundBelt extends DashBelt {
      public HellhoundBelt() {
         super("hellhound_belt", new ResourceLocation("arpg:textures/hellhound_belt_tex.png"));
         this.dashVelocity = 1.1F;
         this.exhaustion = 0.12F;
      }

      @Override
      public int getCooldown(ItemStack itemStack, EntityLivingBase player, float velocity) {
         return 7;
      }

      @Override
      public int getRechargeTime(ItemStack itemStack, EntityLivingBase player, float velocity) {
         return 30;
      }

      @Override
      public void readyModel(EntityPlayer player, BeltsModel model) {
         super.readyModel(player, model);
         model.beltBig.isHidden = false;
         model.beltKnotBig.isHidden = false;
         model.beltRibbons.isHidden = false;
      }

      @Override
      public void onWornTick(ItemStack itemstack, EntityLivingBase entityIn) {
         this.applySlip(entityIn, itemstack);
         if (!entityIn.world.isRemote && entityIn instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entityIn;
            NBTHelper.GiveNBTint(itemstack, 0, "charges");
            NBTHelper.GiveNBTint(itemstack, 0, "recharge");
            int charges = NBTHelper.GetNBTint(itemstack, "charges");
            int recharge = NBTHelper.GetNBTint(itemstack, "recharge");
            int oneCharge = this.getRechargeTime(itemstack, player, this.dashVelocity);
            int maxCharges = 1;
            if (recharge > 0) {
               NBTHelper.AddNBTint(itemstack, -1, "recharge");
            } else if (recharge == 0 && this.canRecharge(player)) {
               NBTHelper.SetNBTint(itemstack, -1, "recharge");
               NBTHelper.SetNBTint(itemstack, maxCharges, "charges");
            }

            if (this.checkAndUpdateBeltCooldown(itemstack) && charges > 0 && Keys.isKeyPressed(player, Keys.SPRINT) && !player.isElytraFlying()) {
               Vec2f dashVec = this.getNormalizedVectorOfDash(player);
               if (dashVec != null) {
                  this.doAdvancedDash(player, itemstack, new Vec3d(dashVec.x, 0.0, dashVec.y), this.dashVelocity);
                  player.motionY += 0.15F;
                  player.getCooldownTracker().setCooldown(this, this.getSlipTime(itemstack, player, this.dashVelocity));
                  this.setBeltCooldown(itemstack, player, this.dashVelocity);
                  player.addExhaustion(this.exhaustion);
                  player.world
                     .playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.dash_a,
                        SoundCategory.PLAYERS,
                        0.7F,
                        0.9F + itemRand.nextFloat() / 5.0F
                     );
                  NBTHelper.AddNBTint(itemstack, -1, "charges");
                  NBTHelper.AddNBTint(itemstack, oneCharge, "recharge");
               }
            }

            this.updateAdvancedDash(player, itemstack, 3.5F, 0.25F, 0.4F);
         }
      }

      public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
         tooltip.add("Allows do dash");
         tooltip.add("Extinguishes inertia");
         super.addInformation(stack, worldIn, tooltip, flagIn);
      }
   }

   public static class ImpulseCorslet extends DashBelt {
      public ImpulseCorslet() {
         super("impulse_corslet", new ResourceLocation("arpg:textures/impulse_corslet_tex.png"));
         this.dashVelocity = 1.2F;
         this.exhaustion = 0.11F;
      }

      @Override
      public int getCooldown(ItemStack itemStack, EntityLivingBase player, float velocity) {
         return 8;
      }

      @Override
      public int getRechargeTime(ItemStack itemStack, EntityLivingBase player, float velocity) {
         return 20;
      }

      @Override
      public void readyModel(EntityPlayer player, BeltsModel model) {
         super.readyModel(player, model);
         model.beltKnotSmall.isHidden = false;
         AbstractMobModel.light(220, false);
         model.render(player, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
         AbstractMobModel.returnlight();
         model.beltKnotSmall.isHidden = true;
         model.beltClassic.isHidden = false;
         model.beltKnotBig.isHidden = false;
         model.beltKnotLeftBack.isHidden = false;
         model.beltKnotLeftFront.isHidden = false;
         model.beltKnotRightBack.isHidden = false;
         model.beltKnotRightFront.isHidden = false;
         model.beltBigDiagonal.isHidden = false;
      }

      @Override
      public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
         super.onEquipped(itemstack, player);
      }

      @Override
      public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
         super.onUnequipped(itemstack, player);
         player.stepHeight = 0.6F;
      }

      @Override
      public void onWornTick(ItemStack itemstack, EntityLivingBase entityIn) {
         entityIn.setSprinting(false);
         if (entityIn.stepHeight < 1.0625F) {
            entityIn.stepHeight = 1.0625F;
         }

         this.applySlip(entityIn, itemstack);
         if (!entityIn.world.isRemote && entityIn instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entityIn;
            NBTHelper.GiveNBTint(itemstack, 0, "charges");
            NBTHelper.GiveNBTint(itemstack, 0, "recharge");
            int charges = NBTHelper.GetNBTint(itemstack, "charges");
            int recharge = NBTHelper.GetNBTint(itemstack, "recharge");
            int oneCharge = this.getRechargeTime(itemstack, player, this.dashVelocity);
            int maxCharges = 2;
            if (recharge > 0) {
               NBTHelper.AddNBTint(itemstack, -1, "recharge");
            } else if (recharge == 0 && this.canRecharge(player)) {
               NBTHelper.SetNBTint(itemstack, -1, "recharge");
               NBTHelper.SetNBTint(itemstack, maxCharges, "charges");
               player.world
                  .playSound(
                     (EntityPlayer)null,
                     player.posX,
                     player.posY,
                     player.posZ,
                     Sounds.dash_ready,
                     SoundCategory.PLAYERS,
                     0.5F,
                     0.9F + itemRand.nextFloat() / 5.0F
                  );
            }

            if (this.checkAndUpdateBeltCooldown(itemstack) && charges > 0 && Keys.isKeyPressed(player, Keys.SPRINT) && !player.isElytraFlying()) {
               Vec2f dashVec = this.getNormalizedVectorOfDash(player);
               if (dashVec != null) {
                  this.doAdvancedDash(player, itemstack, new Vec3d(dashVec.x, 0.0, dashVec.y), this.dashVelocity);
                  player.motionY += 0.15F;
                  player.getCooldownTracker().setCooldown(this, this.getSlipTime(itemstack, player, this.dashVelocity));
                  this.setBeltCooldown(itemstack, player, this.dashVelocity);
                  player.addExhaustion(this.exhaustion);
                  player.world
                     .playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.dash_b,
                        SoundCategory.PLAYERS,
                        0.7F,
                        0.9F + itemRand.nextFloat() / 5.0F
                     );
                  NBTHelper.AddNBTint(itemstack, -1, "charges");
                  NBTHelper.AddNBTint(itemstack, oneCharge, "recharge");
               }
            }

            this.updateAdvancedDash(player, itemstack, 4.5F, 0.45F, 0.4F);
         }
      }

      public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
         tooltip.add("Allows do two dashes");
         tooltip.add("Extinguishes inertia");
         tooltip.add("Disables sprint");
         tooltip.add("Also allows you to climb up to one block");
         super.addInformation(stack, worldIn, tooltip, flagIn);
      }
   }

   public static class ShipwreakersKnot extends DashBelt {
      public ShipwreakersKnot() {
         super("shipwreakers_knot", new ResourceLocation("arpg:textures/shipwreakers_knot_tex.png"));
         this.dashVelocity = 1.2F;
         this.exhaustion = 0.14F;
         this.canDoVerticalDash = true;
      }

      @Override
      public int getCooldown(ItemStack itemStack, EntityLivingBase player, float velocity) {
         return 7;
      }

      @Override
      public int getRechargeTime(ItemStack itemStack, EntityLivingBase player, float velocity) {
         return player.isInWater() ? 17 : 23;
      }

      @Override
      public void readyModel(EntityPlayer player, BeltsModel model) {
         super.readyModel(player, model);
         model.beltKnotSmall.isHidden = false;
         AbstractMobModel.light(240, false);
         model.render(player, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
         AbstractMobModel.returnlight();
         model.beltKnotSmall.isHidden = true;
         model.beltBig.isHidden = false;
         model.beltKnotSmallBack.isHidden = false;
         model.beltKnotLeftBack.isHidden = false;
         model.beltKnotLeftFront.isHidden = false;
         model.beltKnotRightBack.isHidden = false;
         model.beltKnotRightFront.isHidden = false;
         model.beltBigDiagonal.isHidden = false;
         model.beltRibbons.isHidden = false;
      }

      @Override
      public void onWornTick(ItemStack itemstack, EntityLivingBase entityIn) {
         this.applySlip(entityIn, itemstack);
         if (!entityIn.world.isRemote && entityIn instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entityIn;
            NBTHelper.GiveNBTint(itemstack, 0, "charges");
            NBTHelper.GiveNBTint(itemstack, 0, "recharge");
            int charges = NBTHelper.GetNBTint(itemstack, "charges");
            int recharge = NBTHelper.GetNBTint(itemstack, "recharge");
            int oneCharge = this.getRechargeTime(itemstack, player, this.dashVelocity);
            int maxCharges = 3;
            if (recharge > 0) {
               NBTHelper.AddNBTint(itemstack, -1, "recharge");
            } else if (recharge == 0 && this.canRecharge(player)) {
               NBTHelper.SetNBTint(itemstack, -1, "recharge");
               NBTHelper.SetNBTint(itemstack, maxCharges, "charges");
               player.world
                  .playSound(
                     (EntityPlayer)null,
                     player.posX,
                     player.posY,
                     player.posZ,
                     Sounds.dash_ready_shadow,
                     SoundCategory.PLAYERS,
                     0.5F,
                     0.9F + itemRand.nextFloat() / 5.0F
                  );
            }

            if (this.checkAndUpdateBeltCooldown(itemstack) && charges > 0 && Keys.isKeyPressed(player, Keys.SPRINT) && !player.isElytraFlying()) {
               if (player.isInWater()) {
                  Vec3d dashVec = this.getNormalized3dVectorOfDash(player);
                  if (dashVec != null) {
                     this.doAdvancedDash(player, itemstack, dashVec, this.dashVelocity);
                     player.getCooldownTracker().setCooldown(this, this.getSlipTime(itemstack, player, this.dashVelocity));
                     this.setBeltCooldown(itemstack, player, this.dashVelocity);
                     player.addExhaustion(this.exhaustion);
                     player.world
                        .playSound(
                           (EntityPlayer)null,
                           player.posX,
                           player.posY,
                           player.posZ,
                           Sounds.dash_a,
                           SoundCategory.PLAYERS,
                           0.7F,
                           0.9F + itemRand.nextFloat() / 5.0F
                        );
                     NBTHelper.AddNBTint(itemstack, -1, "charges");
                     NBTHelper.AddNBTint(itemstack, oneCharge, "recharge");
                  }
               } else {
                  Vec2f dashVec = this.getNormalizedVectorOfDash(player);
                  if (dashVec != null) {
                     this.doAdvancedDash(player, itemstack, new Vec3d(dashVec.x, 0.0, dashVec.y), this.dashVelocity);
                     player.getCooldownTracker().setCooldown(this, this.getSlipTime(itemstack, player, this.dashVelocity));
                     this.setBeltCooldown(itemstack, player, this.dashVelocity);
                     player.addExhaustion(this.exhaustion);
                     player.world
                        .playSound(
                           (EntityPlayer)null,
                           player.posX,
                           player.posY,
                           player.posZ,
                           Sounds.dash_a,
                           SoundCategory.PLAYERS,
                           0.7F,
                           0.9F + itemRand.nextFloat() / 5.0F
                        );
                     NBTHelper.AddNBTint(itemstack, -1, "charges");
                     NBTHelper.AddNBTint(itemstack, oneCharge, "recharge");
                  }
               }
            }

            this.updateAdvancedDash(player, itemstack, 5.0F, 0.35F, 0.5F);
         }
      }

      @Override
      public boolean canRecharge(EntityPlayer player) {
         return true;
      }

      public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
         tooltip.add("Allows do three dashes");
         tooltip.add("Extinguishes inertia");
         tooltip.add("Recharges everywhere");
         tooltip.add("Can do vertical and diagonal-vertical dashes in water");
         super.addInformation(stack, worldIn, tooltip, flagIn);
      }
   }

   public static class WhitewindBelt extends DashBelt {
      public WhitewindBelt() {
         super("whitewind_belt", new ResourceLocation("arpg:textures/whitewind_belt_tex.png"));
         this.dashVelocity = 2.0F;
         this.exhaustion = 0.08F;
      }

      @Override
      public int getCooldown(ItemStack itemStack, EntityLivingBase player, float velocity) {
         return 8;
      }

      @Override
      public int getRechargeTime(ItemStack itemStack, EntityLivingBase player, float velocity) {
         return 37;
      }

      @Override
      public void readyModel(EntityPlayer player, BeltsModel model) {
         super.readyModel(player, model);
         model.beltRibbons.isHidden = false;
         model.beltKnotSmall.isHidden = false;
         model.beltKnotSmallBack.isHidden = false;
         AbstractMobModel.light(240, false);
         model.render(player, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
         AbstractMobModel.returnlight();
         model.beltRibbons.isHidden = true;
         model.beltKnotSmall.isHidden = true;
         model.beltKnotSmallBack.isHidden = true;
         model.beltPotions2.isHidden = false;
         model.beltPotions3.isHidden = false;
         model.beltBag.isHidden = false;
         model.beltBended1.isHidden = false;
         model.beltBended2.isHidden = false;
         model.beltClassic.isHidden = false;
      }

      @Override
      public void onWornTick(ItemStack itemstack, EntityLivingBase entityIn) {
         this.applySlip(entityIn, itemstack);
         if (!entityIn.world.isRemote && entityIn instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entityIn;
            NBTHelper.GiveNBTint(itemstack, 0, "charges");
            NBTHelper.GiveNBTint(itemstack, 0, "recharge");
            int charges = NBTHelper.GetNBTint(itemstack, "charges");
            int recharge = NBTHelper.GetNBTint(itemstack, "recharge");
            int oneCharge = this.getRechargeTime(itemstack, player, this.dashVelocity);
            int maxCharges = 1;
            if (recharge > 0) {
               NBTHelper.AddNBTint(itemstack, -1, "recharge");
            } else if (recharge == 0 && this.canRecharge(player)) {
               NBTHelper.SetNBTint(itemstack, -1, "recharge");
               NBTHelper.SetNBTint(itemstack, maxCharges, "charges");
            }

            if (this.checkAndUpdateBeltCooldown(itemstack) && charges > 0 && Keys.isKeyPressed(player, Keys.SPRINT) && !player.isElytraFlying()) {
               Vec2f dashVec = this.getNormalizedVectorOfDash(player);
               if (dashVec != null) {
                  this.doAdvancedDash(player, itemstack, new Vec3d(dashVec.x, 0.0, dashVec.y), this.dashVelocity);
                  player.getCooldownTracker().setCooldown(this, this.getSlipTime(itemstack, player, this.dashVelocity));
                  this.setBeltCooldown(itemstack, player, this.dashVelocity);
                  player.addExhaustion(this.exhaustion);
                  player.world
                     .playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.dash_c,
                        SoundCategory.PLAYERS,
                        0.7F,
                        0.9F + itemRand.nextFloat() / 5.0F
                     );
                  NBTHelper.AddNBTint(itemstack, -1, "charges");
                  NBTHelper.AddNBTint(itemstack, oneCharge, "recharge");
               }
            }

            this.updateAdvancedDash(player, itemstack, 6.0F, 0.95F, 0.4F);
         }
      }

      public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
         tooltip.add("Allows do long dash");
         tooltip.add("Extinguishes inertia");
         super.addInformation(stack, worldIn, tooltip, flagIn);
      }
   }

   public static class Windkeeper extends DashBelt {
      public Windkeeper() {
         super("windkeeper", new ResourceLocation("arpg:textures/windkeeper_tex.png"));
         this.dashVelocity = 1.3F;
         this.exhaustion = 0.1F;
         this.textureGlowOverlay = new ResourceLocation("arpg:textures/windkeeper_tex_overlay.png");
         this.canDoVerticalDash = true;
      }

      @Override
      public int getCooldown(ItemStack itemStack, EntityLivingBase player, float velocity) {
         return 6;
      }

      @Override
      public int getRechargeTime(ItemStack itemStack, EntityLivingBase player, float velocity) {
         return 15;
      }

      @Override
      public void readyModel(EntityPlayer player, BeltsModel model) {
         super.readyModel(player, model);
         model.beltBig.isHidden = false;
         model.beltKnotBig.isHidden = false;
         model.beltKnotLeftBack.isHidden = false;
         model.beltKnotLeftFront.isHidden = false;
         model.beltKnotRightBack.isHidden = false;
         model.beltKnotRightFront.isHidden = false;
         model.beltBigDiagonal.isHidden = false;
      }

      @Override
      public void onWornTick(ItemStack itemstack, EntityLivingBase entityIn) {
         this.applySlip(entityIn, itemstack);
         if (!entityIn.world.isRemote && entityIn instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entityIn;
            NBTHelper.GiveNBTint(itemstack, 0, "charges");
            NBTHelper.GiveNBTint(itemstack, 0, "recharge");
            int charges = NBTHelper.GetNBTint(itemstack, "charges");
            int recharge = NBTHelper.GetNBTint(itemstack, "recharge");
            int oneCharge = this.getRechargeTime(itemstack, player, this.dashVelocity);
            int maxCharges = 4;
            if (recharge > 0) {
               NBTHelper.AddNBTint(itemstack, -1, "recharge");
            } else if (recharge == 0 && this.canRecharge(player)) {
               NBTHelper.SetNBTint(itemstack, -1, "recharge");
               NBTHelper.SetNBTint(itemstack, maxCharges, "charges");
               player.world
                  .playSound(
                     (EntityPlayer)null,
                     player.posX,
                     player.posY,
                     player.posZ,
                     Sounds.dash_ready_cold,
                     SoundCategory.PLAYERS,
                     0.5F,
                     0.9F + itemRand.nextFloat() / 5.0F
                  );
            }

            if (this.checkAndUpdateBeltCooldown(itemstack) && charges > 0 && Keys.isKeyPressed(player, Keys.SPRINT) && !player.isElytraFlying()) {
               Vec3d dashVec = this.getNormalized3dVectorOfDash(player);
               if (dashVec != null) {
                  this.doAdvancedDash(player, itemstack, dashVec, this.dashVelocity);
                  player.getCooldownTracker().setCooldown(this, this.getSlipTime(itemstack, player, this.dashVelocity));
                  this.setBeltCooldown(itemstack, player, this.dashVelocity);
                  player.addExhaustion(this.exhaustion);
                  player.world
                     .playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.dash_c,
                        SoundCategory.PLAYERS,
                        0.7F,
                        0.9F + itemRand.nextFloat() / 5.0F
                     );
                  NBTHelper.AddNBTint(itemstack, -1, "charges");
                  NBTHelper.AddNBTint(itemstack, oneCharge, "recharge");
               }
            }

            this.updateAdvancedDash(player, itemstack, 5.5F, 0.8F, 0.3F);
         }
      }

      @Override
      public boolean canRecharge(EntityPlayer player) {
         return true;
      }

      public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
         tooltip.add("Allows do four dashes");
         tooltip.add("Extinguishes inertia");
         tooltip.add("Recharges everywhere");
         tooltip.add("Can do vertical and diagonal-vertical dashes");
         super.addInformation(stack, worldIn, tooltip, flagIn);
      }
   }
}
