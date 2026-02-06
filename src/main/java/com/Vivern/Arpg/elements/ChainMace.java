package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.elements.models.AbstractMobModel;
import com.Vivern.Arpg.entity.EntityChainMace;
import com.Vivern.Arpg.events.Debugger;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.Keys;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.PropertiesRegistry;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.SuperKnockback;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.WeaponParameters;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.mobs.HostileProjectiles;
import com.Vivern.Arpg.potions.Freezing;
import com.Vivern.Arpg.potions.PotionEffects;
import com.Vivern.Arpg.renders.GUNParticle;
import com.Vivern.Arpg.renders.ParticleTracker;
import com.Vivern.Arpg.renders.TEISRGuns;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

public class ChainMace extends ItemWeapon {
   public double maxDistanceSq = 36.0;
   public double radiusSpin = 2.3;
   public float angleSpin = 20.0F;
   public float powerSpin = 1.4F;
   public int noclipDamage = 4;
   public float gravity = 0.05F;
   public byte itemSendId = 1;
   public boolean canBreak = true;
   public float entitySize = 0.5F;
   public float collisionBorderSize = 0.0F;
   public int soundFrequency = 21;
   public float powerSpinLimit = 0.8F;
   public float returnMotion = 0.35F;

   public ChainMace(String name) {
      this.setRegistryName(name);
      this.setTranslationKey(name);
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setMaxDamage(300);
      this.setMaxStackSize(1);
   }

   public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
      return true;
   }

   @Override
   public int getItemEnchantability() {
      return 2;
   }

   public boolean canDestroyBlockInCreative(World world, BlockPos pos, ItemStack stack, EntityPlayer player) {
      return false;
   }

   public void onUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
      if (!world.isRemote) {
         this.setCanShoot(itemstack, entityIn);
         if (IWeapon.canShoot(itemstack)) {
            EntityPlayer player = (EntityPlayer)entityIn;
            boolean click = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
            boolean click2 = Keys.isKeyPressed(player, Keys.SECONDARYATTACK);
            int damage = itemstack.getItemDamage();
            boolean hascooldown = player.getCooldownTracker().hasCooldown(this);
            boolean shouldStop = true;
            if (player.getHeldItemMainhand() == itemstack && (this.canBreak || damage < this.getMaxDamage())) {
               NBTHelper.GiveNBTboolean(itemstack, false, "throwed");
               NBTHelper.GiveNBTboolean(itemstack, false, "spinned");
               NBTHelper.GiveNBTboolean(itemstack, false, "spindirection");
               NBTHelper.GiveNBTint(itemstack, -1, "entityId");
               NBTHelper.GiveNBTint(itemstack, 0, "spinprogress");
               boolean throwed = NBTHelper.GetNBTboolean(itemstack, "throwed");
               boolean spin = NBTHelper.GetNBTboolean(itemstack, "spinned");
               WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
               if (click) {
                  if (!hascooldown) {
                     if (!throwed) {
                        this.throwChainMaceEntity(world, player, itemstack, false);
                        player.addExhaustion(0.3F);
                        double attackspeed = player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getAttributeValue();
                        player.getCooldownTracker().setCooldown(this, this.getModifiedMeleeCooldown(attackspeed, this.getCooldownTime(itemstack)));
                        Weapons.setPlayerAnimationOnServer(player, 8, EnumHand.MAIN_HAND);
                     } else if (spin) {
                        player.addExhaustion(0.3F);
                        Entity entity = world.getEntityByID(NBTHelper.GetNBTint(itemstack, "entityId"));
                        if (entity != null && entity instanceof EntityChainMace && !entity.isDead) {
                           EntityChainMace mace = (EntityChainMace)entity;
                           int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
                           mace.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, parameters.get("velocity"), 3.0F - acc);
                           this.sendEntitySpin(world, mace, false, NBTHelper.GetNBTboolean(itemstack, "spindirection"));
                        } else {
                           NBTHelper.SetNBTboolean(itemstack, false, "throwed");
                        }

                        NBTHelper.SetNBTboolean(itemstack, false, "spinned");
                        double attackspeed = player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getAttributeValue();
                        player.getCooldownTracker().setCooldown(this, this.getModifiedMeleeCooldown(attackspeed, this.getCooldownTime(itemstack)));
                        entity.world
                           .playSound(
                              (EntityPlayer)null,
                              entity.posX,
                              entity.posY,
                              entity.posZ,
                              Sounds.chain_mace_throw,
                              SoundCategory.AMBIENT,
                              0.9F,
                              0.9F + itemRand.nextFloat() / 5.0F
                           );
                        Weapons.setPlayerAnimationOnServer(player, 8, EnumHand.MAIN_HAND);
                     }
                  }

                  shouldStop = false;
               }

               if (click2) {
                  if (!throwed) {
                     this.throwChainMaceEntity(world, player, itemstack, true);
                     player.addExhaustion(0.3F);
                     double attackspeed = player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getAttributeValue();
                     player.getCooldownTracker().setCooldown(this, this.getModifiedMeleeCooldown(attackspeed, this.getCooldownTime(itemstack)));
                     Weapons.setPlayerAnimationOnServer(player, 9, EnumHand.MAIN_HAND);
                  } else if (!hascooldown && !spin) {
                     player.addExhaustion(0.2F);
                     NBTHelper.SetNBTboolean(itemstack, true, "spinned");
                     double attackspeed = player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getAttributeValue();
                     player.getCooldownTracker().setCooldown(this, this.getModifiedMeleeCooldown(attackspeed, this.getCooldownTime(itemstack)));
                     boolean spinpr;
                     if (player.prevRotationYaw > player.rotationYaw) {
                        spinpr = true;
                     } else if (player.prevRotationYaw < player.rotationYaw) {
                        spinpr = false;
                     } else {
                        spinpr = itemRand.nextFloat() < 0.5;
                     }

                     NBTHelper.SetNBTboolean(itemstack, spinpr, "spindirection");
                     this.sendEntitySpin(world, itemstack);
                     Weapons.setPlayerAnimationOnServer(player, 9, EnumHand.MAIN_HAND);
                  }

                  shouldStop = false;
                  player.addExhaustion(0.05F);
               }

               if (throwed) {
                  if (!spin && player.ticksExisted % 8 == 0) {
                     Weapons.setPlayerAnimationOnServer(player, 8, EnumHand.MAIN_HAND);
                  }

                  if (spin && player.ticksExisted % 18 == 0) {
                     Weapons.setPlayerAnimationOnServer(player, 9, EnumHand.MAIN_HAND);
                  }
               }
            }

            if (shouldStop) {
               Entity entity = world.getEntityByID(NBTHelper.GetNBTint(itemstack, "entityId"));
               if (entity != null && entity instanceof EntityChainMace && !entity.isDead) {
                  EntityChainMace mace = (EntityChainMace)entity;
                  if (mace.returnTime <= 0 && mace.thrower != null) {
                     mace.returnTime = 1;
                  }
               } else {
                  NBTHelper.SetNBTboolean(itemstack, false, "throwed");
                  NBTHelper.SetNBTboolean(itemstack, false, "spinned");
               }
            }
         }
      }
   }

   public void returnMace(EntityChainMace entity, ItemStack stack, EntityPlayer player) {
      NBTHelper.SetNBTboolean(stack, false, "throwed");
      NBTHelper.SetNBTboolean(stack, false, "spinned");
      entity.world
         .playSound(
            (EntityPlayer)null,
            entity.posX,
            entity.posY,
            entity.posZ,
            Sounds.chain_mace_pick,
            SoundCategory.AMBIENT,
            0.8F,
            0.9F + itemRand.nextFloat() / 5.0F
         );
      double attackspeed = player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getAttributeValue();
      player.getCooldownTracker().setCooldown(this, this.getModifiedMeleeCooldown(attackspeed, this.getCooldownTime(stack)));
      entity.setDead();
   }

   public void throwChainMaceEntity(World world, EntityPlayer player, ItemStack itemstack, boolean spin) {
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
      int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
      EntityChainMace entity = new EntityChainMace(world, player, itemstack, this);
      entity.setPosition(player.posX, player.posY + player.height / 2.0F, player.posZ);
      entity.gravity = this.gravity;
      if (!spin) {
         entity.shoot(player, player.rotationPitch - 10.0F, player.rotationYaw, 0.0F, parameters.get("velocity"), 3.0F - acc);
      } else {
         entity.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 0.1F, 3.0F);
      }

      entity.itemSendId = this.itemSendId;
      entity.setSize(this.entitySize, this.entitySize);
      entity.collisionBorderSize = this.collisionBorderSize;
      world.spawnEntity(entity);
      itemstack.damageItem(1, player);
      NBTHelper.SetNBTboolean(itemstack, true, "throwed");
      NBTHelper.SetNBTboolean(itemstack, spin, "spinned");
      boolean spinpr;
      if (player.prevRotationYaw > player.rotationYaw) {
         spinpr = true;
      } else if (player.prevRotationYaw < player.rotationYaw) {
         spinpr = false;
      } else {
         spinpr = itemRand.nextFloat() < 0.5;
      }

      this.sendEntitySpin(world, entity, spin, spinpr);
      NBTHelper.SetNBTboolean(itemstack, spinpr, "spindirection");
      NBTHelper.SetNBTint(itemstack, entity.getEntityId(), "entityId");
      entity.world
         .playSound(
            (EntityPlayer)null,
            entity.posX,
            entity.posY,
            entity.posZ,
            Sounds.chain_mace_throw,
            SoundCategory.AMBIENT,
            0.9F,
            0.9F + itemRand.nextFloat() / 5.0F
         );
   }

   public void onImpactMace(EntityChainMace entity, RayTraceResult result) {
      if (result.entityHit != null) {
         if (Team.checkIsOpponent(entity.thrower, result.entityHit) && !entity.world.isRemote) {
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
            float maceDamage = parameters.getEnchanted("damage", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, entity.weaponstack));
            float maceKnockback = parameters.getEnchanted("knockback", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, entity.weaponstack));
            if (this.doMeleeMaceAttack(entity, result.entityHit, entity.weaponstack, entity.thrower, maceDamage, maceKnockback, EnumHand.MAIN_HAND, false)
               && entity.soundCooldown <= 0) {
               entity.world
                  .playSound(
                     (EntityPlayer)null,
                     entity.posX,
                     entity.posY,
                     entity.posZ,
                     this.getImpactSound(),
                     SoundCategory.AMBIENT,
                     0.9F,
                     0.9F + itemRand.nextFloat() / 5.0F
                  );
               entity.soundCooldown = 15 + itemRand.nextInt(10);
            }
         }

         if (entity.returnTime > 0 && result.entityHit == entity.thrower && !entity.isDead) {
            this.returnMace(entity, entity.weaponstack, entity.thrower);
         }
      } else if (entity.soundCooldown <= 0
         && entity.world
               .getBlockState(result.getBlockPos())
               .getBlock()
               .getCollisionBoundingBox(entity.world.getBlockState(result.getBlockPos()), entity.world, result.getBlockPos())
            != null) {
         entity.world
            .playSound(
               (EntityPlayer)null,
               entity.posX,
               entity.posY,
               entity.posZ,
               this.getImpactSound(),
               SoundCategory.AMBIENT,
               0.9F,
               0.9F + itemRand.nextFloat() / 5.0F
            );
         entity.soundCooldown = 15 + itemRand.nextInt(10);
      }
   }

   public SoundEvent getImpactSound() {
      return Sounds.chain_mace_impact;
   }

   public void onTickMace(EntityChainMace entity) {
      if (!entity.world.isRemote) {
         if (entity.weaponstack == null || entity.weaponstack.isEmpty() || entity.weaponstack.getItemDamage() >= entity.weaponstack.getMaxDamage()) {
            entity.setDead();
            return;
         }

         double throwerPosY = entity.thrower.posY + entity.thrower.height / 2.0F;
         if (entity.returnTime <= 0) {
            boolean spin = NBTHelper.GetNBTboolean(entity.weaponstack, "spinned");
            if (spin) {
               Vec3d relativeEntityPos = new Vec3d(
                  entity.posX - entity.thrower.posX, entity.posY - throwerPosY, entity.posZ - entity.thrower.posZ
               );
               float f = entity.thrower.rotationPitch - 90.0F;
               float f1 = entity.thrower.rotationYawHead;
               Vec3d headVector = this.getVectorForRotation(f, f1);
               double distFromEntityToPlane = relativeEntityPos.dotProduct(headVector);
               Vec3d relativeInplanePos = relativeEntityPos.add(
                  -headVector.x * distFromEntityToPlane,
                  -headVector.y * distFromEntityToPlane,
                  -headVector.z * distFromEntityToPlane
               );
               Vec3d relativeNextPos = GetMOP.rotateVecAroundAxis(
                  relativeInplanePos,
                  headVector,
                  (float)Math.toRadians(NBTHelper.GetNBTboolean(entity.weaponstack, "spindirection") ? this.angleSpin : -this.angleSpin)
               );
               relativeNextPos = relativeNextPos.normalize();
               double movex = relativeNextPos.x * this.radiusSpin + entity.thrower.posX;
               double movey = relativeNextPos.y * this.radiusSpin + throwerPosY;
               double movez = relativeNextPos.z * this.radiusSpin + entity.thrower.posZ;
               entity.motionX /= 2.0;
               entity.motionY /= 2.0;
               entity.motionZ /= 2.0;
               float kb = (float)entity.getDistance(movex, movey, movez) * this.powerSpin;
               SuperKnockback.applyMove(entity, -Math.min(kb, this.powerSpinLimit), movex, movey, movez);
               if (entity.ticksExisted % (this.soundFrequency + (int)Debugger.floats[9]) == 0) {
                  entity.world
                     .playSound(
                        (EntityPlayer)null,
                        entity.posX,
                        entity.posY,
                        entity.posZ,
                        Sounds.chain_mace_spin,
                        SoundCategory.AMBIENT,
                        0.9F,
                        0.9F + itemRand.nextFloat() / 5.0F
                     );
               }

               if (entity.ticksExisted % 6 == 0 && entity.thrower != null && entity.getDistanceSq(entity.thrower) > this.maxDistanceSq + 36.0) {
                  entity.returnTime = 1;
               }
            } else if (entity.thrower != null) {
               double distSq = entity.getDistanceSq(entity.thrower);
               if (distSq > this.maxDistanceSq) {
                  entity.motionX *= 0.85;
                  entity.motionY *= 0.85;
                  entity.motionZ *= 0.85;
                  SuperKnockback.applyMove(
                     entity, (float)(this.maxDistanceSq - distSq) / 14.0F, entity.thrower.posX, throwerPosY, entity.thrower.posZ
                  );
               }
            }
         } else {
            entity.returnTime++;
            entity.motionX *= 0.9;
            entity.motionY *= 0.9;
            entity.motionZ *= 0.9;
            SuperKnockback.applyMove(entity, -this.returnMotion, entity.thrower.posX, throwerPosY, entity.thrower.posZ);
            double edistSq = entity.getDistanceSq(entity.thrower);
            if (entity.returnTime > 50 || edistSq > this.maxDistanceSq) {
               if (!entity.noClip) {
                  entity.weaponstack
                     .damageItem(Math.min(this.noclipDamage, entity.weaponstack.getMaxDamage() - entity.weaponstack.getItemDamage()), entity.thrower);
               }

               entity.noClip = true;
               entity.setNoGravity(true);
               if (entity.returnTime > 100) {
                  this.returnMace(entity, entity.weaponstack, entity.thrower);
               }
            }

            if (edistSq <= 0.5 && !entity.isDead) {
               this.returnMace(entity, entity.weaponstack, entity.thrower);
            }
         }
      }
   }

   public boolean doMeleeMaceAttack(
      EntityChainMace maceEntity, Entity entity, ItemStack stack, EntityPlayer player, float damage, float knockback, EnumHand hand, boolean isCritical
   ) {
      IAttributeInstance entityAttribute = player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
      AttributeModifier modifier = new AttributeModifier("iw_attackmelee", damage, 0);
      entityAttribute.applyModifier(modifier);
      IAttributeInstance entityAttributek = player.getEntityAttribute(PropertiesRegistry.MELEE_KNOCKBACK);
      AttributeModifier modifierk = new AttributeModifier("iw_knockbackmelee", knockback, 0);
      if (knockback > 0.0F) {
         entityAttributek.applyModifier(modifierk);
      }

      boolean ret = this.attackEntityMelee(entity, stack, player, hand, isCritical);
      this.applyMaceMeleeEffects(maceEntity, entity, stack, player, damage, knockback, hand, isCritical);
      entityAttribute.removeModifier(modifier);
      if (knockback > 0.0F) {
         entityAttributek.removeModifier(modifierk);
      }

      return ret;
   }

   public void applyMaceMeleeEffects(
      EntityChainMace maceEntity, Entity entity, ItemStack stack, EntityPlayer player, float damage, float knockback, EnumHand hand, boolean isCritical
   ) {
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
      int firelvl = parameters.getEnchantedi("fire", EnchantmentHelper.getEnchantmentLevel(Enchantments.FIRE_ASPECT, stack));
      if (maceEntity.isBurning()) {
         firelvl += parameters.geti("burning_mace_fire");
      }

      if (firelvl > 0) {
         entity.setFire(firelvl);
      }
   }

   @Override
   public boolean attackEntityMelee(Entity entity, ItemStack stack, EntityPlayer player, EnumHand hand, boolean isCritical) {
      float artropods = 1.0F;
      float holy = 1.0F;
      if (entity instanceof EntityLivingBase) {
         artropods = ((EntityLivingBase)entity).getCreatureAttribute() == EnumCreatureAttribute.ARTHROPOD
            ? EnchantmentHelper.getEnchantmentLevel(Enchantments.BANE_OF_ARTHROPODS, stack) * 0.1F + 1.0F
            : 1.0F;
         holy = ((EntityLivingBase)entity).getCreatureAttribute() == EnumCreatureAttribute.UNDEAD
            ? EnchantmentHelper.getEnchantmentLevel(Enchantments.SMITE, stack) * 0.1F + 1.0F
            : 1.0F;
      }

      boolean ret = entity.attackEntityFrom(
         DamageSource.causePlayerDamage(player), (float)player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue() * artropods * holy
      );
      entity.hurtResistantTime = 0;
      return ret;
   }

   @SideOnly(Side.CLIENT)
   public ResourceLocation getTexture() {
      return TEISRGuns.texChainMaceIron;
   }

   @SideOnly(Side.CLIENT)
   public ModelBase getModel() {
      return TEISRGuns.chainMaceModel;
   }

   @SideOnly(Side.CLIENT)
   public void renderEntity(EntityChainMace entity, double x, double y, double z, float entityYaw, float partialTicks, Render render) {
      GlStateManager.pushMatrix();
      GlStateManager.translate((float)x, (float)y + 0.28F, (float)z);
      GlStateManager.enableRescaleNormal();
      GlStateManager.disableCull();
      GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);
      GlStateManager.disableLighting();
      render.bindTexture(this.getTexture());
      this.getModel().render(entity, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0645F);
      GlStateManager.enableCull();
      GlStateManager.enableLighting();
      GlStateManager.disableRescaleNormal();
      GlStateManager.popMatrix();
      if (entity.thrower != null) {
         GlStateManager.pushMatrix();
         GlStateManager.depthMask(false);
         GlStateManager.translate((float)x, (float)y + entity.height / 2.0F, (float)z);
         GlStateManager.enableRescaleNormal();
         Vec3d pwd = entity.getRotatPitchYawAndDist(
            partialTicks, entity.thrower != Minecraft.getMinecraft().player || Minecraft.getMinecraft().gameSettings.thirdPersonView != 0
         );
         GlStateManager.rotate((float)(-pwd.y), 0.0F, 1.0F, 0.0F);
         GlStateManager.rotate((float)pwd.x, 1.0F, 0.0F, 0.0F);
         GL11.glDisable(2884);
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         float scale = 0.05F;
         Tessellator tessellator = Tessellator.getInstance();
         BufferBuilder bufferbuilder = tessellator.getBuffer();
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
         int chainCount = (int)Math.round(pwd.z * 1.5) * 2 + 1;
         int chainCount2 = chainCount / 2;
         float texY = 0.0F;
         float disp = 0.0F;
         float prevDisp = 0.0F;
         float dispAdd = 0.3F;
         if (entity.spinned) {
            if (entity.spinDirection && entity.toHorizontal < 1.0F) {
               entity.toHorizontal += 0.1F;
            }

            if (!entity.spinDirection && entity.toHorizontal > -1.0F) {
               entity.toHorizontal -= 0.1F;
            }
         } else {
            if (entity.toHorizontal > 0.0F) {
               entity.toHorizontal -= 0.1F;
            }

            if (entity.toHorizontal < 0.0F) {
               entity.toHorizontal += 0.1F;
            }
         }

         if (!entity.onGround && !(entity.motionY <= 0.0)) {
            if (entity.toVertical > -0.5F) {
               entity.toVertical -= 0.05F;
            }
         } else if (entity.toVertical < 0.5F) {
            entity.toVertical += 0.05F;
         }

         float toHorizontal = entity.toHorizontal;
         float toVertical = entity.toVertical;
         float oneLength = (float)pwd.z / chainCount;

         for (int i = 0; i < chainCount; i++) {
            prevDisp = disp;
            if (i < chainCount2) {
               disp += dispAdd;
               dispAdd /= 1.5F;
            } else {
               disp -= dispAdd;
               dispAdd *= 1.5F;
            }

            float onelI = oneLength * i;
            float prevDispX = toHorizontal * prevDisp;
            float dispX = toHorizontal * disp;
            float prevDispY = toVertical * prevDisp;
            float dispY = toVertical * disp;
            bufferbuilder.pos(-scale - prevDispX, 0.0F - prevDispY, onelI).tex(0.875, texY).endVertex();
            bufferbuilder.pos(-scale - dispX, 0.0F - dispY, onelI + oneLength).tex(0.875, texY + 0.125F).endVertex();
            bufferbuilder.pos(scale - dispX, 0.0F - dispY, onelI + oneLength).tex(0.9375, texY + 0.125F).endVertex();
            bufferbuilder.pos(scale - prevDispX, 0.0F - prevDispY, onelI).tex(0.9375, texY).endVertex();
            bufferbuilder.pos(0.0F - prevDispX, -scale - prevDispY, onelI).tex(0.875, texY).endVertex();
            bufferbuilder.pos(0.0F - dispX, -scale - dispY, onelI + oneLength).tex(0.875, texY + 0.125F).endVertex();
            bufferbuilder.pos(0.0F - dispX, scale - dispY, onelI + oneLength).tex(0.9375, texY + 0.125F).endVertex();
            bufferbuilder.pos(0.0F - prevDispX, scale - prevDispY, onelI).tex(0.9375, texY).endVertex();
            texY += 0.125F;
            if (texY >= 0.5) {
               texY -= 0.5F;
            }
         }

         tessellator.draw();
         GlStateManager.disableRescaleNormal();
         GL11.glDisable(3042);
         GL11.glEnable(2884);
         GlStateManager.depthMask(true);
         GlStateManager.popMatrix();
      }
   }

   public void sendEntitySpin(World world, ItemStack itemstack) {
      boolean spin = NBTHelper.GetNBTboolean(itemstack, "spinned");
      boolean spindirection = NBTHelper.GetNBTboolean(itemstack, "spindirection");
      Entity entity = world.getEntityByID(NBTHelper.GetNBTint(itemstack, "entityId"));
      if (entity != null && entity instanceof EntityChainMace && !entity.isDead) {
         byte id = 0;
         if (spin && spindirection) {
            id = -1;
         } else if (spin && !spindirection) {
            id = -2;
         } else if (!spin && !spindirection) {
            id = -3;
         } else if (!spin && spindirection) {
            id = -4;
         }

         world.setEntityState(entity, id);
      }
   }

   public void sendEntitySpin(World world, EntityChainMace entity, boolean spin, boolean spindirection) {
      byte id = 0;
      if (spin && spindirection) {
         id = -1;
      } else if (spin && !spindirection) {
         id = -2;
      } else if (!spin && !spindirection) {
         id = -3;
      } else if (!spin && spindirection) {
         id = -4;
      }

      world.setEntityState(entity, id);
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return false;
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.SEMI_ONE_HANDED;
   }

   @Override
   public boolean canAttackMelee(ItemStack itemstack, EntityPlayer player) {
      return false;
   }

   @Override
   public boolean canChangeItem(ItemStack itemstack, EntityPlayer player) {
      return super.canChangeItem(itemstack, player) && !NBTHelper.GetNBTboolean(itemstack, "throwed");
   }

   public final Vec3d getVectorForRotation(float pitch, float yaw) {
      float f = MathHelper.cos(-yaw * (float) (Math.PI / 180.0) - (float) Math.PI);
      float f1 = MathHelper.sin(-yaw * (float) (Math.PI / 180.0) - (float) Math.PI);
      float f2 = -MathHelper.cos(-pitch * (float) (Math.PI / 180.0));
      float f3 = MathHelper.sin(-pitch * (float) (Math.PI / 180.0));
      return new Vec3d(f1 * f2, f3, f * f2);
   }

   public static class DiamondChainMace extends ChainMace {
      public DiamondChainMace() {
         super("diamond_chain_mace");
         this.setCreativeTab(CreativeTabs.COMBAT);
         this.setMaxDamage(1640);
         this.setMaxStackSize(1);
         this.itemSendId = 2;
      }

      @Override
      public ResourceLocation getTexture() {
         return TEISRGuns.texChainMaceDiamond;
      }
   }

   public static class Echinus extends ChainMace {
      ResourceLocation blob = new ResourceLocation("arpg:textures/blob.png");

      public Echinus() {
         super("echinus");
         this.setCreativeTab(CreativeTabs.COMBAT);
         this.setMaxDamage(1950);
         this.setMaxStackSize(1);
         this.itemSendId = 5;
         this.angleSpin = 22.0F;
         this.radiusSpin = 2.4;
         this.maxDistanceSq = 75.0;
         this.entitySize = 0.5F;
         this.powerSpin = 2.0F;
         this.collisionBorderSize = 0.5F;
         this.soundFrequency = 20;
         this.returnMotion = 0.5F;
         this.powerSpinLimit = 1.5F;
      }

      @Override
      public SoundEvent getImpactSound() {
         return Sounds.shield_hit_soft;
      }

      @Override
      public ResourceLocation getTexture() {
         return TEISRGuns.texEchinusModel;
      }

      @Override
      public ModelBase getModel() {
         return TEISRGuns.echinusModel;
      }

      @Override
      public float getAdditionalDurabilityBar(ItemStack itemstack) {
         return MathHelper.clamp(
            (float)NBTHelper.GetNBTint(itemstack, "kills")
               / WeaponParameters.getWeaponParameters(this).getEnchantedi("charges_max", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack)),
            0.0F,
            1.0F
         );
      }

      @Override
      public boolean hasAdditionalDurabilityBar(ItemStack itemstack) {
         return true;
      }

      @Override
      public void onImpactMace(EntityChainMace entity, RayTraceResult result) {
         super.onImpactMace(entity, result);
         if (!entity.world.isRemote && entity.var1 == 0) {
            boolean active = NBTHelper.GetNBTboolean(entity.weaponstack, "active");
            if (active) {
               int kills = NBTHelper.GetNBTint(entity.weaponstack, "kills");
               if (kills <= 0) {
                  NBTHelper.GiveNBTboolean(entity.weaponstack, false, "active");
                  NBTHelper.SetNBTboolean(entity.weaponstack, false, "active");
               } else if (result.entityHit != null) {
                  if (Team.checkIsOpponent(entity.thrower, result.entityHit)) {
                     this.spawnBombs(3, entity);
                  }
               } else if (entity.world
                     .getBlockState(result.getBlockPos())
                     .getBlock()
                     .getCollisionBoundingBox(entity.world.getBlockState(result.getBlockPos()), entity.world, result.getBlockPos())
                  != null) {
                  this.spawnBombs(1 + itemRand.nextInt(3), entity);
               }
            }
         }
      }

      public void spawnBombs(int count, EntityChainMace entity) {
         WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);

         for (int i = 0; i < count; i++) {
            HostileProjectiles.SeaBomb entityshoot = new HostileProjectiles.SeaBomb(entity.world, entity.thrower);
            entityshoot.setPosition(entity.posX, entity.posY, entity.posZ);
            entityshoot.damage = parameters.getEnchanted("bomb_damage", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, entity.weaponstack));
            entity.world.spawnEntity(entityshoot);
            entityshoot.motionX = itemRand.nextGaussian() / 5.0;
            entityshoot.motionY = itemRand.nextGaussian() / 5.0;
            entityshoot.motionZ = itemRand.nextGaussian() / 5.0;
            entityshoot.velocityChanged = true;
            entityshoot.explodeTimeOffset = -40;
         }

         entity.world
            .playSound(
               (EntityPlayer)null,
               entity.posX,
               entity.posY,
               entity.posZ,
               Sounds.sea_bomb,
               SoundCategory.AMBIENT,
               0.9F,
               1.1F + itemRand.nextFloat() / 5.0F
            );
         entity.var1 = 1;
         NBTHelper.AddNBTint(entity.weaponstack, -count, "kills");
      }

      @Override
      public void onTickMace(EntityChainMace entity) {
         super.onTickMace(entity);
         if (entity.world.isRemote) {
            if (itemRand.nextFloat() < 0.15F) {
               int lt = 35;
               float scl = 0.06F + itemRand.nextFloat() / 10.0F;
               GUNParticle part = new GUNParticle(
                  this.blob,
                  scl,
                  -0.01F,
                  lt,
                  -1,
                  entity.world,
                  entity.posX,
                  entity.posY + entity.height / 2.0F,
                  entity.posZ,
                  (float)itemRand.nextGaussian() / 8.0F,
                  (float)itemRand.nextGaussian() / 8.0F,
                  (float)itemRand.nextGaussian() / 8.0F,
                  0.8F + itemRand.nextFloat() / 5.0F,
                  0.8F + itemRand.nextFloat() / 5.0F,
                  0.8F + itemRand.nextFloat() / 5.0F,
                  true,
                  itemRand.nextInt(40) - 20
               );
               entity.world.spawnEntity(part);
            }
         } else if (entity.ticksExisted % 6 == 0) {
            boolean active = NBTHelper.GetNBTboolean(entity.weaponstack, "active");
            if (NBTHelper.GetNBTboolean(entity.weaponstack, "spinned") && (entity.ticksExisted > 80 || active)) {
               int kills = NBTHelper.GetNBTint(entity.weaponstack, "kills");
               if (!active && kills >= 16) {
                  NBTHelper.GiveNBTboolean(entity.weaponstack, true, "active");
                  NBTHelper.SetNBTboolean(entity.weaponstack, true, "active");
               }

               if (active) {
                  if (kills <= 0) {
                     NBTHelper.GiveNBTboolean(entity.weaponstack, false, "active");
                     NBTHelper.SetNBTboolean(entity.weaponstack, false, "active");
                  } else {
                     WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
                     HostileProjectiles.SeaBomb entityshoot = new HostileProjectiles.SeaBomb(entity.world, entity.thrower);
                     entityshoot.setPosition(entity.posX, entity.posY, entity.posZ);
                     entityshoot.damage = parameters.getEnchanted("bomb_damage", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, entity.weaponstack));
                     entity.world.spawnEntity(entityshoot);
                     SuperKnockback.applyMove(entityshoot, 1.0F, entity.thrower.posX, entity.thrower.posY, entity.thrower.posZ);
                     entityshoot.velocityChanged = true;
                     entityshoot.explodeTimeOffset = -40;
                     NBTHelper.AddNBTint(entity.weaponstack, -1, "kills");
                     entity.world
                        .playSound(
                           (EntityPlayer)null,
                           entity.posX,
                           entity.posY,
                           entity.posZ,
                           Sounds.slimeshoot_a,
                           SoundCategory.AMBIENT,
                           0.9F,
                           1.1F + itemRand.nextFloat() / 5.0F
                        );
                  }
               }
            }
         }
      }

      @Override
      public boolean attackEntityMelee(Entity entity, ItemStack stack, EntityPlayer player, EnumHand hand, boolean isCritical) {
         WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
         boolean dead = false;
         boolean checkdead = false;
         int addkills = parameters.getEnchantedi("charges_per_kill", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.REUSE, stack));
         int kills = NBTHelper.GetNBTint(stack, "kills");
         if (kills < parameters.getEnchantedi("charges_max", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, stack))
            && !NBTHelper.GetNBTboolean(stack, "active")
            && entity instanceof EntityLivingBase
            && ((EntityLivingBase)entity).getHealth() > 0.0F) {
            checkdead = true;
         }

         boolean aem = super.attackEntityMelee(entity, stack, player, hand, isCritical);
         if (checkdead) {
            EntityLivingBase elb = (EntityLivingBase)entity;
            if (elb.getHealth() <= 0.0F) {
               dead = true;
               if (elb.isPotionActive(MobEffects.POISON) || elb.isPotionActive(PotionEffects.TOXIN) || elb.isPotionActive(PotionEffects.BROKEN_ARMOR)) {
                  addkills += parameters.geti("charges_effects_bonus");
               }
            }
         }

         if (dead) {
            NBTHelper.GiveNBTint(stack, 0, "kills");
            NBTHelper.AddNBTint(stack, addkills, "kills");
            if (kills < 16 && kills + addkills >= 16) {
               entity.world
                  .playSound(
                     (EntityPlayer)null,
                     entity.posX,
                     entity.posY,
                     entity.posZ,
                     Sounds.item_misc_b,
                     SoundCategory.AMBIENT,
                     0.9F,
                     0.9F + itemRand.nextFloat() / 5.0F
                  );
            }
         }

         return aem;
      }
   }

   public static class Icebreaker extends ChainMace {
      ResourceLocation largecloud = new ResourceLocation("arpg:textures/largecloud.png");
      ResourceLocation snow = new ResourceLocation("arpg:textures/snowflake3.png");
      public static ParticleTracker.TrackerSmoothShowHide tssh = new ParticleTracker.TrackerSmoothShowHide(
         new Vec3d[]{new Vec3d(0.0, 5.0, 0.2F), new Vec3d(5.0, 15.0, -0.1F)}, null
      );

      public Icebreaker() {
         super("icebreaker");
         this.setCreativeTab(CreativeTabs.COMBAT);
         this.setMaxDamage(3140);
         this.setMaxStackSize(1);
         this.itemSendId = 4;
         this.angleSpin = 25.0F;
         this.radiusSpin = 2.0;
         this.maxDistanceSq = 64.0;
         this.entitySize = 0.4F;
         this.powerSpin = 2.0F;
         this.collisionBorderSize = 0.25F;
         this.soundFrequency = 20;
      }

      @Override
      public ResourceLocation getTexture() {
         return TEISRGuns.texIcebreaker;
      }

      @Override
      public boolean attackEntityMelee(Entity entity, ItemStack stack, EntityPlayer player, EnumHand hand, boolean isCritical) {
         float artropods = 1.0F;
         float holy = 1.0F;
         float freezebreak = 0.0F;
         if (entity instanceof EntityLivingBase) {
            artropods = ((EntityLivingBase)entity).getCreatureAttribute() == EnumCreatureAttribute.ARTHROPOD
               ? EnchantmentHelper.getEnchantmentLevel(Enchantments.BANE_OF_ARTHROPODS, stack) * 0.1F + 1.0F
               : 1.0F;
            holy = ((EntityLivingBase)entity).getCreatureAttribute() == EnumCreatureAttribute.UNDEAD
               ? EnchantmentHelper.getEnchantmentLevel(Enchantments.SMITE, stack) * 0.1F + 1.0F
               : 1.0F;
            if (Freezing.canImmobilizeEntity((EntityLivingBase)entity, ((EntityLivingBase)entity).getActivePotionEffect(PotionEffects.FREEZING))) {
               freezebreak += WeaponParameters.getWeaponParameters(this)
                  .getEnchanted("icebreak_damage", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, stack));
            }
         }

         boolean ret = entity.attackEntityFrom(
            DamageSource.causePlayerDamage(player),
            (float)player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue() * artropods * holy + freezebreak
         );
         entity.hurtResistantTime = 0;
         return ret;
      }

      @Override
      public void onTickMace(EntityChainMace entity) {
         super.onTickMace(entity);
         if (entity.world.isRemote) {
            if (Math.abs(entity.motionX) + Math.abs(entity.motionY) + Math.abs(entity.motionZ) > 0.1) {
               int lt = 15;
               float scl = 0.1F + itemRand.nextFloat() / 3.0F;
               GUNParticle fire2 = new GUNParticle(
                  this.largecloud,
                  scl,
                  0.009F,
                  lt,
                  240,
                  entity.world,
                  entity.posX,
                  entity.posY + entity.height / 2.0F,
                  entity.posZ,
                  0.0F,
                  0.0F,
                  0.0F,
                  0.75F + itemRand.nextFloat() / 10.0F,
                  1.0F,
                  1.0F,
                  true,
                  itemRand.nextInt(100) - 50
               );
               fire2.tracker = tssh;
               fire2.scaleTickAdding = -scl / lt / 1.6F;
               fire2.alphaGlowing = true;
               fire2.alpha = 0.1F;
               entity.world.spawnEntity(fire2);
               int lt2 = 15;
               float scl2 = 0.1F + itemRand.nextFloat() / 6.0F;
               GUNParticle snoww = new GUNParticle(
                  this.snow,
                  scl2,
                  0.03F,
                  lt2,
                  200,
                  entity.world,
                  entity.posX,
                  entity.posY + entity.height / 2.0F,
                  entity.posZ,
                  (float)itemRand.nextGaussian() / 15.0F,
                  (float)itemRand.nextGaussian() / 15.0F,
                  (float)itemRand.nextGaussian() / 15.0F,
                  0.9F,
                  0.9F,
                  1.0F,
                  false,
                  itemRand.nextInt(360),
                  true,
                  2.3F
               );
               snoww.scaleTickAdding = -scl2 / lt2 / 2.2F;
               snoww.dropMode = true;
               entity.world.spawnEntity(snoww);
            } else if (itemRand.nextFloat() < 0.4F) {
               int lt = 15;
               float scl = 0.1F + itemRand.nextFloat() / 3.0F;
               GUNParticle fire2 = new GUNParticle(
                  this.largecloud,
                  scl,
                  0.009F,
                  lt,
                  240,
                  entity.world,
                  entity.posX,
                  entity.posY + entity.height / 2.0F,
                  entity.posZ,
                  0.0F,
                  0.0F,
                  0.0F,
                  0.75F + itemRand.nextFloat() / 10.0F,
                  1.0F,
                  1.0F,
                  true,
                  itemRand.nextInt(100) - 50
               );
               fire2.tracker = tssh;
               fire2.scaleTickAdding = -scl / lt / 1.6F;
               fire2.alphaGlowing = true;
               fire2.alpha = 0.1F;
               entity.world.spawnEntity(fire2);
            }
         }
      }
   }

   public static class MoltenChainMace extends ChainMace {
      ResourceLocation flame = new ResourceLocation("arpg:textures/flame_big.png");

      public MoltenChainMace() {
         super("molten_chain_mace");
         this.setCreativeTab(CreativeTabs.COMBAT);
         this.setMaxDamage(2000);
         this.setMaxStackSize(1);
         this.itemSendId = 3;
         this.angleSpin = 25.0F;
         this.radiusSpin = 2.7;
         this.maxDistanceSq = 49.0;
         this.entitySize = 0.4F;
         this.soundFrequency = 25;
      }

      @Override
      public void renderEntity(EntityChainMace entity, double x, double y, double z, float entityYaw, float partialTicks, Render render) {
         GL11.glDisable(2896);
         AbstractMobModel.light(180, true);
         super.renderEntity(entity, x, y, z, entityYaw, partialTicks, render);
         AbstractMobModel.returnlight();
         GL11.glEnable(2896);
      }

      @Override
      public ResourceLocation getTexture() {
         return TEISRGuns.texChainMaceMolten;
      }

      @Override
      public void onTickMace(EntityChainMace entity) {
         super.onTickMace(entity);
         if (entity.world.isRemote) {
            if (entity.spinned) {
               int lt = 10 + itemRand.nextInt(6);
               float scl = 0.1F + itemRand.nextFloat() / 3.0F;
               GUNParticle fire2 = new GUNParticle(
                  this.flame,
                  scl,
                  -0.009F,
                  lt,
                  240,
                  entity.world,
                  entity.posX,
                  entity.posY + entity.height / 2.0F,
                  entity.posZ,
                  0.0F,
                  0.0F,
                  0.0F,
                  1.0F,
                  0.8F + (float)itemRand.nextGaussian() / 5.0F,
                  1.0F,
                  true,
                  itemRand.nextInt(100) - 50
               );
               fire2.alphaTickAdding = -1.0F / lt;
               fire2.scaleTickAdding = -scl / lt / 1.6F;
               fire2.alphaGlowing = true;
               entity.world.spawnEntity(fire2);
               if (itemRand.nextFloat() < 0.2F) {
                  entity.world
                     .spawnParticle(EnumParticleTypes.LAVA, entity.posX, entity.posY, entity.posZ, 0.0, 0.2F, 0.0, new int[0]);
               }
            } else {
               if (itemRand.nextFloat() < 0.1F) {
                  entity.world
                     .spawnParticle(EnumParticleTypes.LAVA, entity.posX, entity.posY, entity.posZ, 0.0, 0.2F, 0.0, new int[0]);
               }

               if (itemRand.nextFloat() < 0.2F) {
                  int lt = 10 + itemRand.nextInt(6);
                  float scl = 0.1F + itemRand.nextFloat() / 3.0F;
                  GUNParticle fire2 = new GUNParticle(
                     this.flame,
                     scl,
                     -0.009F,
                     lt,
                     240,
                     entity.world,
                     entity.posX,
                     entity.posY + entity.height / 2.0F,
                     entity.posZ,
                     0.0F,
                     0.0F,
                     0.0F,
                     1.0F,
                     0.8F + (float)itemRand.nextGaussian() / 5.0F,
                     1.0F,
                     true,
                     itemRand.nextInt(100) - 50
                  );
                  fire2.alphaTickAdding = -1.0F / lt;
                  fire2.scaleTickAdding = -scl / lt / 1.6F;
                  fire2.alphaGlowing = true;
                  entity.world.spawnEntity(fire2);
               }
            }
         }
      }
   }
}
