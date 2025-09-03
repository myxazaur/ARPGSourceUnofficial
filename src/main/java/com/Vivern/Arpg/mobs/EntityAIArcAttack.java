//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.mobs;

import com.Vivern.Arpg.entity.BetweenParticle;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.network.PacketDoSomethingToClients;
import com.Vivern.Arpg.network.PacketHandler;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;

public class EntityAIArcAttack extends EntityAIBase {
   public EntityCreature entity;
   public int attackTimer = 0;
   public int maxCooldown = 30;
   public float grow;
   public float Yoffset;
   public float beamSize;
   public int beamTex;
   public byte sendbyteId;
   public int checkDelay;
   public PotionEffect shock;
   public float shockChance;
   public boolean canStrikeBlock = false;
   public float blockRadius;
   public float blockDestroyPower;
   public static ResourceLocation[] textures = new ResourceLocation[]{
      new ResourceLocation("arpg:textures/lightning1.png"),
      new ResourceLocation("arpg:textures/lightning2.png"),
      new ResourceLocation("arpg:textures/lightning3.png")
   };

   public EntityAIArcAttack(EntityCreature entity, int maxCooldown, int checkDelay, float radius, float Yoffset, float beamSize, int beamTex, byte sendbyteId) {
      this.entity = entity;
      this.maxCooldown = maxCooldown;
      this.grow = radius;
      this.Yoffset = Yoffset;
      this.beamSize = beamSize;
      this.beamTex = beamTex;
      this.sendbyteId = sendbyteId;
      this.checkDelay = checkDelay;
   }

   public EntityAIArcAttack setPotionEffect(PotionEffect shock, float chance) {
      this.shock = shock;
      this.shockChance = chance;
      return this;
   }

   public EntityAIArcAttack setMissToBlock(boolean canStrikeBlock, float blockRadius, float blockDestroyPower) {
      this.canStrikeBlock = canStrikeBlock;
      this.blockRadius = blockRadius;
      this.blockDestroyPower = blockDestroyPower;
      return this;
   }

   public static void spawnParticle(World world, int entityid, float Yoffset, int targetid, float beamSize, int beamTex) {
      Entity e1 = world.getEntityByID(entityid);
      Entity e2 = world.getEntityByID(targetid);
      if (e1 != null && e2 != null) {
         RayTraceResult result = world.rayTraceBlocks(
            new Vec3d(e1.posX, e1.posY + Yoffset, e1.posZ),
            new Vec3d(e2.posX, e2.posY + e2.height / 2.0F, e2.posZ),
            false
         );
         Vec3d pos2 = new Vec3d(e1.posX, e1.posY + Yoffset, e1.posZ);
         Vec3d pos1 = new Vec3d(e2.posX, e2.posY + e2.height / 2.0F, e2.posZ);
         if (result != null && result.typeOfHit == Type.BLOCK && result.hitVec != null) {
            pos1 = result.hitVec;
         }

         BetweenParticle laser = new BetweenParticle(
            world, textures[beamTex], beamSize, 240, 1.0F, 1.0F, 1.0F, 0.1F, pos1.distanceTo(pos2), 9, 0.0F, 9999.0F, pos1, pos2
         );
         laser.alphaGlowing = true;
         laser.setPosition(pos1.x, pos1.y, pos1.z);
         world.spawnEntity(laser);
      }
   }

   public boolean shouldExecute() {
      return this.entity.getAttackTarget() != null;
   }

   public void updateTask() {
      EntityLivingBase attackTarg = this.entity.getAttackTarget();
      this.attackTimer--;
      if (attackTarg != null && !this.entity.world.isRemote && this.attackTimer <= 0 && this.entity.ticksExisted % this.checkDelay == 0) {
         AxisAlignedBB axisalignedbb = this.entity
            .getEntityBoundingBox()
            .expand(this.grow * 2.0F, this.grow * 2.0F, this.grow * 2.0F)
            .offset(-this.grow, -this.grow, -this.grow);
         List<EntityLivingBase> list = this.entity.world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
         if (!list.isEmpty()) {
            EntityLivingBase target = null;
            double maxdist = Double.MAX_VALUE;

            for (EntityLivingBase entitylivingbase : list) {
               if (Team.checkIsOpponent(this.entity, entitylivingbase)) {
                  double distsq = this.entity.getDistanceSq(entitylivingbase);
                  if (distsq < maxdist) {
                     maxdist = distsq;
                     target = entitylivingbase;
                  }
               }
            }

            if (target != null) {
               RayTraceResult result = this.entity
                  .world
                  .rayTraceBlocks(
                     new Vec3d(this.entity.posX, this.entity.posY + this.Yoffset, this.entity.posZ),
                     new Vec3d(target.posX, target.posY + target.height / 2.0F, target.posZ),
                     false
                  );
               if (result == null || result.typeOfHit != Type.BLOCK) {
                  this.entity.attackEntityAsMob(target);
                  this.attackTimer = this.maxCooldown;
                  if (this.shock != null && this.entity.getRNG().nextFloat() < this.shockChance) {
                     target.addPotionEffect(new PotionEffect(this.shock));
                  }
               } else if (result != null && result.typeOfHit == Type.BLOCK && result.getBlockPos() != null) {
                  if (Weapons.easyBreakBlockFor(this.entity.world, this.blockDestroyPower, result.getBlockPos())) {
                     this.entity.world.destroyBlock(result.getBlockPos(), this.entity.getRNG().nextFloat() < 0.5);
                  }

                  this.attackTimer = this.maxCooldown;
               }

               this.entity.world.setEntityState(this.entity, this.sendbyteId);
               PacketDoSomethingToClients packet = new PacketDoSomethingToClients();
               packet.writeargs(this.entity.getEntityId(), this.Yoffset, target.getEntityId(), this.beamSize, this.beamTex, 0.0, 5);
               PacketHandler.sendToAllAround(
                  packet, this.entity.world, this.entity.posX, this.entity.posY, this.entity.posZ, 48.0
               );
            }
         }
      }
   }
}
