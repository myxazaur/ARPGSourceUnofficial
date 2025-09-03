//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.mobs;

import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.PropertiesRegistry;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.potions.PotionEffects;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;
import net.minecraft.world.BossInfo.Color;
import net.minecraft.world.BossInfo.Overlay;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BossSpine extends EntityMob {
   public List<Vec3d> poslist = new ArrayList<>();
   public List<BossSpineSegment> segmentlist = new ArrayList<>();
   ResourceLocation texture = new ResourceLocation("arpg:textures/purple_smoke.png");
   public static int segments = 25;
   public int deads = 0;
   private final BossInfoServer bossInfo = new BossInfoServer(this.getDisplayName(), Color.WHITE, Overlay.PROGRESS);

   public BossSpine(World worldIn) {
      super(worldIn);
      this.setSize(1.6F, 1.6F);
      this.setNoGravity(true);
      this.noClip = true;
      this.isImmuneToFire = true;
   }

   public Vec3d getSegmCoord(int number) {
      return number < this.poslist.size() ? this.poslist.get(number) : this.getPositionVector();
   }

   public boolean isNonBoss() {
      return false;
   }

   public void addTrackingPlayer(EntityPlayerMP player) {
      super.addTrackingPlayer(player);
      this.bossInfo.addPlayer(player);
   }

   public void removeTrackingPlayer(EntityPlayerMP player) {
      super.removeTrackingPlayer(player);
      this.bossInfo.removePlayer(player);
   }

   protected void updateAITasks() {
      super.updateAITasks();
      this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
   }

   public boolean canBreatheUnderwater() {
      return true;
   }

   public void addPotionEffect(PotionEffect potioneffectIn) {
      if (potioneffectIn.getPotion() != PotionEffects.FREEZING) {
         super.addPotionEffect(potioneffectIn);
      }
   }

   public void onUpdate() {
      super.onUpdate();
      Vec3d pitchYaw = GetMOP.Vec3dToPitchYaw(new Vec3d(-this.motionX, -this.motionY, -this.motionZ));
      this.rotationPitch = (float)MathHelper.wrapDegrees(pitchYaw.x);
      this.rotationYaw = (float)MathHelper.wrapDegrees(pitchYaw.y);
      if (this.ticksExisted < 20) {
         this.motionX = 0.0;
         this.motionY = 0.0;
         this.motionZ = 0.0;
      }

      if (this.ticksExisted > 300 && this.ticksExisted % 270 == 0) {
         for (int i = 0; i < this.rand.nextInt(4) + 3; i++) {
            if (!this.world.isRemote) {
               BossSpineMinion minion = new BossSpineMinion(this.world, this);
               minion.setPosition(this.posX, this.posY, this.posZ);
               minion.motionX = this.rand.nextGaussian() / 17.0;
               minion.motionZ = this.rand.nextGaussian() / 17.0;
               this.world.spawnEntity(minion);
            }
         }

         this.world
            .playSound(
               this.posX,
               this.posY,
               this.posZ,
               Sounds.spine_summon,
               SoundCategory.AMBIENT,
               1.3F,
               0.9F + this.rand.nextFloat() / 5.0F,
               false
            );
      }

      if (this.ticksExisted == 30) {
         for (int ix = 0; ix < segments; ix++) {
            if (!this.world.isRemote) {
               BossSpineSegment part = new BossSpineSegment(this.world, ix, this);
               part.setPosition(this.posX, this.posY - ix / 5.0, this.posZ);
               this.world.spawnEntity(part);
               this.segmentlist.add(part);
            }
         }
      }

      if (this.poslist != null) {
         Vec3d vec = new Vec3d(this.posX, this.posY, this.posZ);
         if (this.poslist.size() > 0) {
            if (vec != null) {
               if (this.poslist.get(0) == null) {
                  this.poslist.set(0, vec);
               } else if (vec.distanceTo(this.poslist.get(0)) > 1.3) {
                  this.poslist.add(0, vec);
               }

               if (this.poslist.size() > segments + 1) {
                  this.poslist.remove(segments + 1);
               }
            }
         } else {
            this.poslist.add(vec);
         }
      }
   }

   public void fall(float distance, float damageMultiplier) {
   }

   public boolean attackEntityFrom(DamageSource source, float amount) {
      if (source == DamageSource.IN_WALL || source == DamageSource.CRAMMING || source == DamageSource.CACTUS) {
         return false;
      } else if (this.deads <= 20) {
         return false;
      } else {
         return this.isEntityInvulnerable(source) ? false : super.attackEntityFrom(source, amount);
      }
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(450.0);
      this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(37.0);
      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.1);
      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(10.0);
      this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(8.0);
      this.getEntityAttribute(PropertiesRegistry.ARMOR_PROTECTION).setBaseValue(5.0);
   }

   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return Sounds.spine_hurt;
   }

   protected SoundEvent getDeathSound() {
      return Sounds.spine_dead;
   }

   public void onLivingUpdate() {
      if (this.world.isRemote) {
         for (int i = 0; i < 2; i++) {
            this.world
               .spawnParticle(
                  EnumParticleTypes.PORTAL,
                  this.posX + (this.rand.nextDouble() - 0.5) * this.width,
                  this.posY + this.rand.nextDouble() * this.height - 0.25,
                  this.posZ + (this.rand.nextDouble() - 0.5) * this.width,
                  (this.rand.nextDouble() - 0.5) * 2.0,
                  -this.rand.nextDouble(),
                  (this.rand.nextDouble() - 0.5) * 2.0,
                  new int[0]
               );
         }
      }

      super.onLivingUpdate();
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      super.handleStatusUpdate(id);
   }

   protected void initEntityAI() {
      this.tasks.addTask(1, new BossAISpine(this));
   }
}
