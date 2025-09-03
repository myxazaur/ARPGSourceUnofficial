//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.mobs;

import com.Vivern.Arpg.main.SuperKnockback;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

public class EntityAICloudAttack extends EntityAIBase {
   public EntityCreature entity;
   public int frequency = 30;
   public float radius = 2.0F;
   public float damage = 1.0F;
   public float knockback = 0.8F;
   public byte handleStatusUpdateID;
   public Potion effect = null;
   public int potionDur = 100;
   public int potionPower = 0;
   public float potionChance = 0.3F;

   public EntityAICloudAttack(
      EntityCreature entity,
      int frequency,
      float radius,
      float damage,
      float knockback,
      byte handleStatusUpdateID,
      @Nullable Potion effect,
      int potionDur,
      int potionPower,
      float potionChance
   ) {
      this.entity = entity;
      this.frequency = frequency;
      this.radius = radius;
      this.damage = damage;
      this.knockback = knockback;
      this.handleStatusUpdateID = handleStatusUpdateID;
      this.effect = effect;
      this.potionDur = potionDur;
      this.potionPower = potionPower;
      this.potionChance = potionChance;
   }

   public EntityAICloudAttack(
      EntityCreature entity, int frequency, float radius, float damage, float knockback, byte handleStatusUpdateID, @Nullable Potion effect
   ) {
      this.entity = entity;
      this.frequency = frequency;
      this.radius = radius;
      this.damage = damage;
      this.knockback = knockback;
      this.handleStatusUpdateID = handleStatusUpdateID;
      this.effect = effect;
   }

   public EntityAICloudAttack(EntityCreature entity, byte handleStatusUpdateID) {
      this.entity = entity;
      this.handleStatusUpdateID = handleStatusUpdateID;
   }

   public boolean shouldExecute() {
      return this.entity.getAttackTarget() != null;
   }

   public void updateTask() {
      if (this.entity.ticksExisted % this.frequency == 0) {
         EntityLivingBase attackTarg = this.entity.getAttackTarget();
         if (attackTarg != null && !this.entity.world.isRemote) {
            Vec3d vec = this.entity.getPositionVector().add(0.0, this.entity.height / 2.0F, 0.0);
            AxisAlignedBB axisalignedbb = new AxisAlignedBB(
               vec.x - this.radius,
               vec.y - this.radius,
               vec.z - this.radius,
               vec.x + this.radius,
               vec.y + this.radius,
               vec.z + this.radius
            );
            List<EntityLivingBase> list = this.entity.world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
            if (!list.isEmpty()) {
               for (EntityLivingBase entitylivingbase : list) {
                  if (entitylivingbase == attackTarg || entitylivingbase instanceof EntityPlayer) {
                     IAttributeInstance entityAttribute = entitylivingbase.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE);
                     double baseValue = entityAttribute.getBaseValue();
                     entityAttribute.setBaseValue(1.0);
                     entitylivingbase.attackEntityFrom(DamageSource.causeMobDamage(this.entity), this.damage);
                     entitylivingbase.hurtResistantTime = 0;
                     entityAttribute.setBaseValue(baseValue);
                     SuperKnockback.applyKnockback(
                        entitylivingbase, this.knockback, this.entity.posX, this.entity.posY, this.entity.posZ
                     );
                     if (this.effect != null && this.entity.getRNG().nextFloat() < this.potionChance) {
                        PotionEffect debaff = new PotionEffect(this.effect, this.potionDur, this.potionPower);
                        entitylivingbase.addPotionEffect(debaff);
                     }
                  }
               }
            }
         }

         this.entity.world.setEntityState(this.entity, this.handleStatusUpdateID);
      }
   }
}
