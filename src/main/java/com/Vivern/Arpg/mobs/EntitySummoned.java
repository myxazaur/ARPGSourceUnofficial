//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.mobs;

import com.Vivern.Arpg.main.Team;
import com.google.common.base.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntitySummoned extends EntityCreature {
   public EntityPlayer owner;
   protected static final DataParameter<Optional<UUID>> OWNER_UNIQUE_ID = EntityDataManager.createKey(EntitySummoned.class, DataSerializers.OPTIONAL_UNIQUE_ID);
   public boolean allowedFollow = true;
   public boolean allowedAttack = false;
   public boolean allowedHunt = false;
   public boolean allowedSpecial = false;
   public double followPlayerMaxRange = 15.0;
   public double followPlayerMinRange = 2.0;
   public String team = "";
   public Vec3d followPoint;

   public EntitySummoned(World world) {
      super(world);
   }

   public EntitySummoned(World world, double x, double y, double z) {
      super(world);
      this.setPositionAndUpdate(x, y, z);
   }

   public EntitySummoned(World world, double x, double y, double z, EntityPlayer owner, ItemStack itemstack) {
      super(world);
      this.setPositionAndUpdate(x, y, z);
      this.setOwner(owner);
      this.team = Team.getTeamFor(owner);
   }

   public void expelling() {
      this.setDead();
   }

   protected boolean canDespawn() {
      return false;
   }

   public SoundCategory getSoundCategory() {
      return SoundCategory.NEUTRAL;
   }

   public void onLivingUpdate() {
      this.updateArmSwingProgress();
      float f = this.getBrightness();
      if (f > 0.5F) {
         this.idleTime += 2;
      }

      super.onLivingUpdate();
   }

   public void onUpdate() {
      super.onUpdate();
   }

   protected SoundEvent getSwimSound() {
      return SoundEvents.ENTITY_GENERIC_SWIM;
   }

   protected SoundEvent getSplashSound() {
      return SoundEvents.ENTITY_GENERIC_SPLASH;
   }

   public boolean attackEntityFrom(DamageSource source, float amount) {
      if (source == DamageSource.IN_WALL) {
         return false;
      } else {
         return this.isEntityInvulnerable(source) ? false : super.attackEntityFrom(source, amount);
      }
   }

   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return SoundEvents.ENTITY_GENERIC_HURT;
   }

   protected SoundEvent getDeathSound() {
      return SoundEvents.ENTITY_GENERIC_DEATH;
   }

   protected SoundEvent getFallSound(int heightIn) {
      return heightIn > 4 ? SoundEvents.ENTITY_GENERIC_BIG_FALL : SoundEvents.ENTITY_GENERIC_SMALL_FALL;
   }

   public boolean attackEntityAsMob(Entity entityIn) {
      float f = (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
      int i = 0;
      if (entityIn instanceof EntityLivingBase) {
         f += EnchantmentHelper.getModifierForCreature(this.getHeldItemMainhand(), ((EntityLivingBase)entityIn).getCreatureAttribute());
         i += EnchantmentHelper.getKnockbackModifier(this);
      }

      boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), f);
      if (flag) {
         if (i > 0 && entityIn instanceof EntityLivingBase) {
            ((EntityLivingBase)entityIn)
               .knockBack(
                  this,
                  i * 0.5F,
                  MathHelper.sin(this.rotationYaw * (float) (Math.PI / 180.0)),
                  -MathHelper.cos(this.rotationYaw * (float) (Math.PI / 180.0))
               );
            this.motionX *= 0.6;
            this.motionZ *= 0.6;
         }

         int j = EnchantmentHelper.getFireAspectModifier(this);
         if (j > 0) {
            entityIn.setFire(j * 4);
         }

         if (entityIn instanceof EntityPlayer) {
            EntityPlayer entityplayer = (EntityPlayer)entityIn;
            ItemStack itemstack = this.getHeldItemMainhand();
            ItemStack itemstack1 = entityplayer.isHandActive() ? entityplayer.getActiveItemStack() : ItemStack.EMPTY;
            if (!itemstack.isEmpty()
               && !itemstack1.isEmpty()
               && itemstack.getItem().canDisableShield(itemstack, itemstack1, entityplayer, this)
               && itemstack1.getItem().isShield(itemstack1, entityplayer)) {
               float f1 = 0.25F + EnchantmentHelper.getEfficiencyModifier(this) * 0.05F;
               if (this.rand.nextFloat() < f1) {
                  entityplayer.getCooldownTracker().setCooldown(itemstack1.getItem(), 100);
                  this.world.setEntityState(entityplayer, (byte)30);
               }
            }
         }

         this.applyEnchantments(this, entityIn);
      }

      return flag;
   }

   public float getBlockPathWeight(BlockPos pos) {
      return 0.5F - this.world.getLightBrightness(pos);
   }

   protected boolean isValidLightLevel() {
      return false;
   }

   public boolean getCanSpawnHere() {
      return super.getCanSpawnHere();
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
   }

   protected boolean canDropLoot() {
      return true;
   }

   protected void entityInit() {
      super.entityInit();
      this.dataManager.register(OWNER_UNIQUE_ID, Optional.absent());
   }

   public void writeEntityToNBT(NBTTagCompound compound) {
      super.writeEntityToNBT(compound);
      if (this.getOwnerId() == null) {
         compound.setString("OwnerUUID", "");
      } else {
         compound.setString("OwnerUUID", this.getOwnerId().toString());
      }

      compound.setBoolean("allowedFollow", this.allowedFollow);
      compound.setBoolean("allowedAttack", this.allowedAttack);
      compound.setBoolean("allowedHunt", this.allowedHunt);
      compound.setBoolean("allowedSpecial", this.allowedSpecial);
      compound.setString("team", this.team);
   }

   public void setOwner(EntityPlayer player) {
      this.setOwnerId(player.getUniqueID());
      this.owner = player;
   }

   public void readEntityFromNBT(NBTTagCompound compound) {
      super.readEntityFromNBT(compound);
      String s;
      if (compound.hasKey("OwnerUUID", 8)) {
         s = compound.getString("OwnerUUID");
      } else {
         String s1 = compound.getString("Owner");
         s = PreYggdrasilConverter.convertMobOwnerIfNeeded(this.getServer(), s1);
      }

      if (!s.isEmpty()) {
         try {
            this.setOwnerId(UUID.fromString(s));
         } catch (Throwable var4) {
         }
      }

      this.allowedFollow = compound.getBoolean("allowedFollow");
      this.allowedAttack = compound.getBoolean("allowedAttack");
      this.allowedHunt = compound.getBoolean("allowedHunt");
      this.allowedSpecial = compound.getBoolean("allowedSpecial");
      this.owner = this.getOwner();
      this.team = compound.getString("team");
      if (this.owner != null) {
         this.followPoint = this.owner.getPositionVector();
      } else {
         this.followPoint = this.getPositionVector();
      }
   }

   @Nullable
   public UUID getOwnerId() {
      return (UUID)((Optional)this.dataManager.get(OWNER_UNIQUE_ID)).orNull();
   }

   public void setOwnerId(@Nullable UUID p_184754_1_) {
      this.dataManager.set(OWNER_UNIQUE_ID, Optional.fromNullable(p_184754_1_));
   }

   @Nullable
   public EntityPlayer getOwner() {
      try {
         UUID uuid = this.getOwnerId();
         return uuid == null ? null : this.world.getPlayerEntityByUUID(uuid);
      } catch (IllegalArgumentException var21) {
         return null;
      }
   }

   public String getEntitySummonedTeam() {
      return this.team;
   }

   public void setEntitySummonedTeam(String t) {
      this.team = t;
   }

   public ResourceLocation getDisplayIcon() {
      return null;
   }

   public boolean isPreventingPlayerRest(EntityPlayer playerIn) {
      return false;
   }
}
