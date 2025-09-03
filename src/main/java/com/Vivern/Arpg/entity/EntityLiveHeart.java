//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.SuperKnockback;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.renders.GUNParticle;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityLiveHeart extends Entity {
   public float health = 1.0F;
   public int ticks = 0;
   public int randomRotat = 0;
   public float randTranslate = 0.0F;
   public float reachDistance = 0.0F;
   public String team = "";
   public int t1r = 0;
   public int t2g = 0;
   public int t3b = 0;
   public boolean doesAttract = false;
   public static ResourceLocation res = new ResourceLocation("arpg:textures/heart_particle.png");
   public TextFormatting clientTeamColor = TextFormatting.WHITE;

   public EntityLiveHeart(World world) {
      super(world);
      this.setSize(0.3F, 0.3F);
      this.noClip = false;
      this.randomRotat = this.rand.nextInt(360);
      this.randTranslate = this.rand.nextFloat() * 50.0F;
   }

   public EntityLiveHeart(World world, float health) {
      super(world);
      this.setSize(0.3F, 0.3F);
      this.noClip = false;
      this.health = health;
      this.motionX = this.rand.nextGaussian() / 13.0;
      this.motionY = this.rand.nextGaussian() / 17.0 + 0.4;
      this.motionZ = this.rand.nextGaussian() / 13.0;
      this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
      this.randomRotat = this.rand.nextInt(360);
      this.randTranslate = this.rand.nextFloat() * 50.0F;
   }

   public static void spawnHearts(World world, double x, double y, double z, int count, float health, boolean attract, float reachDistance) {
      spawnHearts(world, x, y, z, count, health, attract, reachDistance, "");
   }

   public static void spawnHearts(World world, double x, double y, double z, int count, float health, boolean attract, float reachDistance, Entity heartsUser) {
      String team = Team.getTeamFor(heartsUser);
      spawnHearts(world, x, y, z, count, health, attract, reachDistance, team);
   }

   public static void spawnHearts(World world, double x, double y, double z, int count, float health, boolean attract, float reachDistance, String team) {
      for (int i = 0; i < count; i++) {
         EntityLiveHeart heart = new EntityLiveHeart(world, health);
         heart.setPosition(x, y, z);
         heart.doesAttract = attract;
         heart.reachDistance = reachDistance;
         heart.team = team;
         world.spawnEntity(heart);
      }
   }

   @Nullable
   public net.minecraft.scoreboard.Team getTeam() {
      return this.team.isEmpty() ? null : Team.getTeamObjectFromString(this.world, this.team);
   }

   public void sendTeamColor() {
      if (this.getTeam() != null && this.getTeam().getColor() != null) {
         this.world.setEntityState(this, (byte)this.getTeam().getColor().getColorIndex());
      }
   }

   public boolean isGlowing() {
      return true;
   }

   public double getYOffset() {
      return 0.15;
   }

   protected void entityInit() {
   }

   public void readEntityFromNBT(NBTTagCompound nbt) {
      if (nbt.hasKey("health")) {
         this.health = nbt.getFloat("health");
      }

      if (nbt.hasKey("ticks")) {
         this.ticks = nbt.getInteger("ticks");
      }

      if (nbt.hasKey("team")) {
         this.team = nbt.getString("team");
      }
   }

   public void writeEntityToNBT(NBTTagCompound nbt) {
      nbt.setFloat("health", this.health);
      nbt.setInteger("ticks", this.ticks);
      nbt.setString("team", this.team);
   }

   public void onEntityUpdate() {
      if (this.world.isRemote) {
         if (this.t1r > 0) {
            this.t1r--;
         }

         if (this.t2g > 0) {
            this.t2g--;
         }

         if (this.t3b > 0) {
            this.t3b--;
         }

         if (this.rand.nextFloat() < 0.02 && this.t1r == 0) {
            this.t1r = 30;
         }

         if (this.rand.nextFloat() < 0.02 && this.t2g == 0) {
            this.t2g = 30;
         }

         if (this.rand.nextFloat() < 0.025 && this.t3b == 0) {
            this.t3b = 30;
         }

         if (this.rand.nextFloat() < 0.1F) {
            double d0 = this.posX;
            double d1 = this.posY + 0.15;
            double d2 = this.posZ;
            double d3 = 0.22;
            double d4 = 0.27;
            int livetime = 40 + this.rand.nextInt(40);
            float scale = 0.048F + this.rand.nextFloat() / 30.0F;
            float scaleTickAdding = -(scale / livetime);
            float randispl1 = (this.rand.nextFloat() - 0.5F) / 2.0F;
            float randispl2 = (this.rand.nextFloat() - 0.5F) / 2.0F;
            float randispl3 = (this.rand.nextFloat() - 0.5F) / 2.0F;
            float randsp1 = (this.rand.nextFloat() - 0.5F) / 40.0F;
            float randsp2 = (this.rand.nextFloat() - 0.5F) / 40.0F;
            float randsp3 = (this.rand.nextFloat() - 0.5F) / 40.0F;
            GUNParticle spelll = new GUNParticle(
               res,
               scale,
               4.0E-4F,
               livetime,
               160 + this.rand.nextInt(40),
               this.world,
               d0 + randispl1,
               d1 + randispl2,
               d2 + randispl3,
               randsp1,
               randsp2,
               randsp3,
               this.getRed(),
               this.getGreen(),
               this.getBlue(),
               false,
               45,
               true,
               5.0F
            );
            spelll.dropMode = true;
            spelll.scaleTickAdding = scaleTickAdding;
            this.world.spawnEntity(spelll);
         }
      }

      super.onEntityUpdate();
   }

   public float getRed() {
      return 1.0F - (float)Math.sin(this.t1r / 9.5F) / 2.0F;
   }

   public float getGreen() {
      return 1.0F - (float)Math.sin(this.t2g / 9.5F) / 2.0F;
   }

   public float getBlue() {
      return 1.0F - (float)Math.sin(this.t3b / 9.5F) / 2.0F;
   }

   public void onUpdate() {
      super.onUpdate();
      this.ticks++;
      if (!this.hasNoGravity()) {
         this.motionY -= 0.03F;
      }

      this.pushOutOfBlocks(this.posX, (this.getEntityBoundingBox().minY + this.getEntityBoundingBox().maxY) / 2.0, this.posZ);
      this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
      float f = 0.98F;
      if (this.onGround) {
         BlockPos underPos = new BlockPos(
            MathHelper.floor(this.posX),
            MathHelper.floor(this.getEntityBoundingBox().minY) - 1,
            MathHelper.floor(this.posZ)
         );
         IBlockState underState = this.world.getBlockState(underPos);
         f = underState.getBlock().getSlipperiness(underState, this.world, underPos, this) * 0.98F;
      }

      this.motionX *= f;
      this.motionY *= 0.98F;
      this.motionZ *= f;
      if (this.onGround) {
         this.motionY *= -0.9F;
      }

      if (!this.world.isRemote && (this.ticksExisted <= 3 || this.ticksExisted % 20 == 0)) {
         this.sendTeamColor();
      }

      if (this.ticksExisted % 2 == 0) {
         double damageRadius = this.reachDistance;
         AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
            .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
            .offset(-damageRadius, -damageRadius, -damageRadius);
         List<EntityPlayer> list = this.world.getEntitiesWithinAABB(EntityPlayer.class, axisalignedbb);
         double max = Double.MAX_VALUE;
         EntityPlayer targ = null;
         if (!list.isEmpty()) {
            for (EntityPlayer player : list) {
               if (player.getMaxHealth() - player.getHealth() > 0.0F
                  && EntitySelectors.NOT_SPECTATING.apply(player)
                  && (this.team.isEmpty() || this.team.equals(Team.getTeamFor(player)))) {
                  double dist = player.getDistanceSq(this);
                  if (dist < max) {
                     max = dist;
                     targ = player;
                  }
               }
            }

            if (targ != null) {
               SuperKnockback.applyMove(this, -0.2F, targ.posX, targ.posY + targ.height / 2.0F, targ.posZ);
            }
         }
      }

      this.velocityChanged = true;
      if (this.ticks > 600) {
         this.setDead();
      }
   }

   public void onCollideWithPlayer(EntityPlayer entityIn) {
      if (this.health != 0.0F
         && this.ticksExisted > 15
         && !entityIn.world.isRemote
         && entityIn.isEntityAlive()
         && (this.team.isEmpty() || this.team.equals(Team.getTeamFor(entityIn)))
         && entityIn.getMaxHealth() - entityIn.getHealth() > 0.0F) {
         this.world.setEntityState(this, (byte)-5);
         entityIn.heal(this.health);
         this.setDead();
      }
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == -5) {
         this.world
            .playSound(
               this.posX,
               this.posY,
               this.posZ,
               Sounds.live_heart,
               SoundCategory.PLAYERS,
               0.8F,
               0.9F + this.rand.nextFloat() / 5.0F,
               false
            );
      } else if (TextFormatting.fromColorIndex(id) != null) {
         this.clientTeamColor = TextFormatting.fromColorIndex(id);
      }
   }
}
