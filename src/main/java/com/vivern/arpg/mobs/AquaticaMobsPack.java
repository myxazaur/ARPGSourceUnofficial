package com.vivern.arpg.mobs;

import baubles.api.BaublesApi;
import com.vivern.arpg.elements.models.AbstractMobModel;
import com.vivern.arpg.elements.models.ModelSphere;
import com.vivern.arpg.elements.models.ModelsAquaticaMobs;
import com.vivern.arpg.elements.models.OceanSpiritModel;
import com.vivern.arpg.entity.BetweenParticle;
import com.vivern.arpg.entity.EntityPart;
import com.vivern.arpg.entity.IEntitySynchronize;
import com.vivern.arpg.entity.IMultipartMob;
import com.vivern.arpg.entity.TrailParticle;
import com.vivern.arpg.events.Debugger;
import com.vivern.arpg.main.AnimationTimer;
import com.vivern.arpg.main.BloodType;
import com.vivern.arpg.main.ColorConverters;
import com.vivern.arpg.main.DeathEffects;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.Mana;
import com.vivern.arpg.main.PropertiesRegistry;
import com.vivern.arpg.main.ShardType;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.SuperKnockback;
import com.vivern.arpg.main.Team;
import com.vivern.arpg.main.WeaponDamage;
import com.vivern.arpg.main.Weapons;
import com.vivern.arpg.potions.PotionEffects;
import com.vivern.arpg.potions.SirenSong;
import com.vivern.arpg.recipes.Seal;
import com.vivern.arpg.recipes.Soul;
import com.vivern.arpg.renders.GUNParticle;
import com.vivern.arpg.renders.IRender;
import com.vivern.arpg.renders.ParticleTracker;
import com.vivern.arpg.renders.mobrender.RenderTentacles;
import com.google.common.base.Predicate;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityLookHelper;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.ai.EntityMoveHelper.Action;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateSwimmer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

public class AquaticaMobsPack {
   public static String mobsteam = "aquatica.mob.team";

   public static void init() {
      AbstractMob.addToRegister(Dunkleosteus.class, "Dunkleosteus", 2318654, 6467902);
      AbstractMob.addToRegister(Needletooth.class, "Needletooth", 7302225, 2965300);
      AbstractMob.addToRegister(Blisterfish.class, "Blisterfish", 8433076, 14474303);
      AbstractMob.addToRegister(Wizardfish.class, "Wizardfish", 13658370, 2379236);
      AbstractMob.addToRegister(Dartfish.class, "Dartfish", 16765562, 2073571);
      AbstractMob.addToRegister(Trachymona.class, "Trachymona", 10143198, 13512282);
      AbstractMob.addToRegister(Hydromona.class, "Hydromona", 9021677, 8041146);
      AbstractMob.addToRegister(Breed.class, "Breed", 10632535, 9891327);
      AbstractMob.addToRegister(Polipoid.class, "Polipoid", 11682606, 9891327);
      AbstractMob.addToRegister(SeaStriker.class, "Sea Striker", 2695999, 8510419);
      AbstractMob.addToRegister(OceanSpirit.class, "Ocean Spirit", 5539211, 8145219);
      AbstractMob.addToRegister(Archelon.class, "Archelon", 3299615, 14142328);
      AbstractMob.addToRegister(ArchelonCreation.class, "Archelon Creation", 3024987, 16156297);
      AbstractMob.addToRegister(Mermaid.class, "Mermaid", 5537368, 3203327);
      AbstractMob.addToRegister(DarkMermaid.class, "Dark Mermaid", 3815243, 16777215);
      AbstractMob.addToRegister(Siren.class, "Siren", 3558860, 16771876);
      AbstractMob.addToRegister(BossKraken.class, "Boss Kraken", 6381130, 11769000, 200, 1);
      AbstractMob.addToRegister(KrakenTentacleBite.class, "Kraken Tentacle Bite", 6381130, 14849192, 200, 1);
      AbstractMob.addToRegister(KrakenTentacleShock.class, "Kraken Tentacle Shock", 6381130, 7783423, 200, 1);
      AbstractMob.addToRegister(KrakenTentacleCrash.class, "Kraken Tentacle Crash", 6381130, 6905751, 200, 1);
      AbstractMob.addToRegister(KrakenTentacleMain.class, "Kraken Tentacle Main", 6381130, 10209415, 200, 1);
      AbstractMob.addToRegister(KrakenTentacleGrab.class, "Kraken Tentacle Grab", 6381130, 14469583, 200, 1);
   }

   public static void initRender() {
      AbstractMob.addToRender(new AbstractMob.RenderAbstractMobEntry(new ModelsAquaticaMobs.NeedletoothModel(), 0.4F, Needletooth.class));
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
               new ModelsAquaticaMobs.BlisterfishModel(),
               new ResourceLocation("arpg:textures/blisterfish_model_tex.png"),
               0.4F,
               Blisterfish.class
            )
            .setLightLayer(new ResourceLocation("arpg:textures/blisterfish_model_tex_overlay.png"))
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
               new ModelsAquaticaMobs.WizardfishModel(),
               new ResourceLocation("arpg:textures/wizardfish_model_tex.png"),
               0.4F,
               Wizardfish.class
            )
            .setLightLayer(new ResourceLocation("arpg:textures/wizardfish_model_tex_overlay.png"))
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
               new ModelsAquaticaMobs.DartfishModel(), new ResourceLocation("arpg:textures/dartfish_model_tex.png"), 0.5F, Dartfish.class
            )
            .setLightLayer(new ResourceLocation("arpg:textures/dartfish_model_tex_overlay.png"))
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
               new ModelsAquaticaMobs.TrachymonaModel(),
               new ResourceLocation("arpg:textures/trachymona_model_tex.png"),
               0.25F,
               Trachymona.class
            )
            .setRenderAsRocket(true)
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
               new ModelsAquaticaMobs.HydromonaModel(), new ResourceLocation("arpg:textures/hydromona_model_tex.png"), 0.35F, Hydromona.class
            )
            .setRenderAsRocket(true)
            .setLightLayer(new ResourceLocation("arpg:textures/hydromona_model_tex_overlay.png"))
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
               new ModelsAquaticaMobs.BreedModel(), new ResourceLocation("arpg:textures/breed_model_tex.png"), 0.15F, Breed.class
            )
            .setRenderAsRocket(false)
            .setLightLayer(new ResourceLocation("arpg:textures/breed_model_tex_overlay.png"))
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
               new ModelsAquaticaMobs.PolipoidModel(), new ResourceLocation("arpg:textures/polipoid_model_tex.png"), 0.3F, Polipoid.class
            )
            .setRenderAsRocket(false)
            .setLightLayer(new ResourceLocation("arpg:textures/polipoid_model_tex_overlay.png"))
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new ModelsAquaticaMobs.SeaStrikerModel(), new ResourceLocation("arpg:textures/sea_striker_model_tex.png"), 0.35F, SeaStriker.class
         )
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new OceanSpiritModel(), new ResourceLocation("arpg:textures/ocean_spirit_model_tex.png"), 0.4F, OceanSpirit.class
         )
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
               new ModelsAquaticaMobs.ArchelonModel(), new ResourceLocation("arpg:textures/archelon_model_tex.png"), 1.3F, Archelon.class
            )
            .setLightLayer(new ResourceLocation("arpg:textures/archelon_model_tex_overlay.png"))
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new ModelsAquaticaMobs.ArchelonCreationModel(),
            new ResourceLocation("arpg:textures/archelon_creation_model_tex.png"),
            0.5F,
            ArchelonCreation.class
         )
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new ModelsAquaticaMobs.MermaidModel(), new ResourceLocation("arpg:textures/mermaid_model_tex.png"), 0.35F, Mermaid.class
         )
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new ModelsAquaticaMobs.MermaidModel(), new ResourceLocation("arpg:textures/dark_mermaid_tex.png"), 0.35F, DarkMermaid.class
         )
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new ModelsAquaticaMobs.SirenModel(), new ResourceLocation("arpg:textures/siren_model_tex.png"), 0.4F, Siren.class
         )
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
               new ModelsAquaticaMobs.BossKrakenModel(), new ResourceLocation("arpg:textures/boss_kraken_model_tex.png"), 1.5F, BossKraken.class
            )
            .setRenderAsRocket(true)
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
               new ModelsAquaticaMobs.KrakenTentacleModel(0),
               new ResourceLocation("arpg:textures/kraken_tentacle_model_tex.png"),
               0.3F,
               KrakenTentacleBite.class
            )
            .setUseIRender()
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
               new ModelsAquaticaMobs.KrakenTentacleModel(1),
               new ResourceLocation("arpg:textures/kraken_tentacle_model_tex.png"),
               0.3F,
               KrakenTentacleShock.class
            )
            .setUseIRender()
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
               new ModelsAquaticaMobs.KrakenTentacleModel(2),
               new ResourceLocation("arpg:textures/kraken_tentacle_model_tex.png"),
               0.3F,
               KrakenTentacleCrash.class
            )
            .setUseIRender()
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
               new ModelsAquaticaMobs.KrakenTentacleModel(3),
               new ResourceLocation("arpg:textures/kraken_tentacle_model_tex.png"),
               0.3F,
               KrakenTentacleMain.class
            )
            .setUseIRender()
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
               new ModelsAquaticaMobs.KrakenTentacleModel(4),
               new ResourceLocation("arpg:textures/kraken_tentacle_model_tex.png"),
               0.3F,
               KrakenTentacleGrab.class
            )
            .setUseIRender()
      );
   }

   public static class Archelon extends AbstractMob implements IMultipartMob, IEntitySynchronize {
      public EntityPart archelonHead;
      public ArchelonCreation archelonCreation;
      public boolean blocksDamage = true;
      public EntityAIRush rush;
      public EntityAIAttackSweep sweep;

      public Archelon(World world) {
         super(world, 2.2F, 2.0F);
         this.hurtSound = Sounds.mob_zombie_hurt;
         this.deathSound = Sounds.mob_zombie_death;
         this.livingSound = Sounds.mob_zombie_living;
         this.defaultteam = AquaticaMobsPack.mobsteam;
         this.moveHelper = new WaterMoveHelper(this, false, false);
         this.setattributes(320.0, 64.0, 16.0, 0.12, 12.0, 7.0, 0.8, 0.0, 0.0, 0.0);
         this.registerLOOT(
            new MobDrop[]{
               new MobDrop(ItemsRegister.ARCHELONSHELL, 0.85F, 0, 1, 1, 0), new MobDrop(ItemsRegister.ARCHELONSHELL, 0.15F, 0, 1, 1, 0)
            }
         );
         this.stepHeight = 1.0F;
         this.var1 = 0;
         this.setRoleValues(EnumMobRole.TANK, 5);
         this.soul = Soul.DEEPWATER;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.5) {
            ShardType.spawnShards(ShardType.WATER, this, 2.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.9) {
            ShardType.spawnShards(ShardType.EARTH, this, 4.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.RED_BLOOD;
      }

      @Override
      public void setDead() {
         if (!this.world.isRemote && this.archelonCreation != null) {
            this.archelonCreation.maxspawnDelay *= 2;
         }

         super.setDead();
      }

      @Override
      public EntityLivingBase getMob() {
         return this;
      }

      @Override
      public boolean isDead() {
         return !this.isEntityAlive();
      }

      @Override
      public String getTeamString() {
         return this.team;
      }

      @Override
      public boolean attackEntityFromPart(EntityPart part, DamageSource source, float damage) {
         if (this.animations[3] < 85 && this.animations[3] > 15) {
            return false;
         } else {
            this.blocksDamage = false;
            boolean aef = this.attackEntityFrom(source, damage);
            if (aef && this.rand.nextFloat() < 0.4F && (this.sweep == null || !this.sweep.attackstarted) && this.animations[3] <= 0) {
               this.triggerAnimation(-4);
               this.sweep.actualCooldown = 100;
            }

            this.blocksDamage = true;
            return aef;
         }
      }

      public float getEyeHeight() {
         return this.height * 0.4F;
      }

      @Override
      public void onUpdate() {
         super.onUpdate();
         if (!this.world.isRemote) {
            if ((this.ticksExisted < 2 || this.ticksExisted % 43 == 0) && this.var1 == 1) {
               this.world.setEntityState(this, (byte)8);
            }

            float headProtectAn = 100 - this.animations[3];
            float headProtect = GetMOP.getfromto(headProtectAn, 0.0F, 20.0F) - GetMOP.getfromto(headProtectAn, 80.0F, 100.0F);
            float unheadProtect = 1.0F - headProtect;
            if (this.archelonHead == null || this.archelonHead.isDead) {
               this.archelonHead = new EntityPart(this.world, this, this.team, 0.7F, 0.7F);
               Vec3d vec = this.getPositionVector().add(0.0, 4.5, 0.0);
               this.archelonHead.setPositionAndUpdate(vec.x, vec.y, vec.z);
               this.world.spawnEntity(this.archelonHead);
            }

            float f2 = MathHelper.clamp((this.rotationYawHead - this.renderYawOffset) / 2.42F * unheadProtect, -17.0F, 17.0F);
            Vec3d vec = this.getPositionVector()
               .add(this.getVectorForRotation(this.rotationPitch * 0.8F, this.renderYawOffset + f2).scale(2.25 - 1.8F * headProtect))
               .add(0.0, 0.23, 0.0);
            this.archelonHead.setPosition(vec.x, vec.y, vec.z);
            IEntitySynchronize.sendSynchronize(this, 64.0, this.renderYawOffset);
         }
      }

      @Override
      public void onClient(double... args) {
         this.renderYawOffset = (float)args[0];
         if (this.archelonCreation != null) {
            this.archelonCreation.renderYawOffset = (float)args[0];
         }
      }

      @Override
      public boolean attackEntityFrom(DamageSource source, float amount) {
         return super.attackEntityFrom(source, this.blocksDamage ? this.onAttacked(amount, source, 60, 60.0F) : amount);
      }

      public float onAttacked(float hurtdamage, DamageSource source, int shieldAngle, float damageBlocks) {
         Entity attacker = source.getImmediateSource() == null ? source.getTrueSource() : source.getImmediateSource();
         Vec3d vec1 = this.getVectorForRotation(this.rotationPitch * 0.4F, this.renderYawOffset);
         if (attacker == null
            || GetMOP.getAngleBetweenVectors(
                  vec1.x,
                  vec1.y,
                  vec1.z,
                  attacker.posX - this.posX,
                  attacker.posY - this.posY,
                  attacker.posZ - this.posZ
               )
               < shieldAngle) {
            if (attacker == null) {
               return hurtdamage;
            }

            SuperKnockback.applyShieldBlock(this, attacker, 0.5F, 0.3F);
            hurtdamage -= damageBlocks;
            if (hurtdamage < 1.5) {
               this.world
                  .playSound(
                     (EntityPlayer)null,
                     this.posX,
                     this.posY,
                     this.posZ,
                     Sounds.shield_hit_soft,
                     SoundCategory.HOSTILE,
                     0.6F,
                     0.9F + this.rand.nextFloat() / 5.0F
                  );
            } else {
               this.world
                  .playSound(
                     (EntityPlayer)null,
                     this.posX,
                     this.posY,
                     this.posZ,
                     Sounds.shield_break,
                     SoundCategory.HOSTILE,
                     0.6F,
                     0.9F + this.rand.nextFloat() / 5.0F
                  );
            }
         }

         return hurtdamage;
      }

      @SideOnly(Side.CLIENT)
      @Override
      public void handleStatusUpdate(byte id) {
         super.handleStatusUpdate(id);
         if (id == 8) {
            this.var1 = 1;
         }
      }

      public boolean canBreatheUnderwater() {
         return true;
      }

      protected PathNavigate createNavigator(World worldIn) {
         return new PathNavigateSwimmer(this, worldIn);
      }

      public void travel(float strafe, float vertical, float forward) {
         if (this.isServerWorld() && this.isInWater()) {
            this.moveRelative(strafe, vertical, forward, 0.1F);
            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.9F;
            this.motionY *= 0.9F;
            this.motionZ *= 0.9F;
         } else {
            super.travel(strafe, vertical, forward);
         }
      }

      protected float updateDistance(float p_110146_1_, float p_110146_2_) {
         float maxadd = 1.5F;
         float f = MathHelper.wrapDegrees(p_110146_1_ - this.renderYawOffset);
         this.renderYawOffset = this.renderYawOffset + MathHelper.clamp(f * 0.3F, -maxadd, maxadd);
         float f1 = MathHelper.wrapDegrees(this.rotationYaw - this.renderYawOffset);
         boolean flag = f1 < -90.0F || f1 >= 90.0F;
         if (f1 < -75.0F) {
            f1 = -75.0F;
         }

         if (f1 >= 75.0F) {
            f1 = 75.0F;
         }

         if (f1 * f1 > 2500.0F) {
            this.renderYawOffset = this.renderYawOffset + MathHelper.clamp(f1 * 0.2F, -maxadd, maxadd);
         }

         if (flag) {
            p_110146_2_ *= -1.0F;
         }

         return p_110146_2_;
      }

      protected void initEntityAI() {
         this.rush = new EntityAIRush(this, true, false, false);
         this.sweep = new EntityAIAttackSweep(this, 49 - this.getMobDifficulty() * 6, 1.0F, 1.8F, 3.75F, 16).setTriggerOnStart();
         this.sweep.useYawOffset = true;
         this.tasks.addTask(1, this.rush);
         this.tasks.addTask(3, this.sweep);
         this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0));
         this.tasks.addTask(7, new EntityAIWander(this, 1.0, 80));
         this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
         this.tasks.addTask(8, new EntityAILookIdle(this));
         this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
         this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }

      @Override
      public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
         IEntityLivingData ield = super.onInitialSpawn(difficulty, livingdata);
         if (!this.world.isRemote && this.rand.nextFloat() < 0.7F) {
            ArchelonCreation mob = new ArchelonCreation(this.world);
            mob.setPosition(this.posX, this.posY, this.posZ);
            this.world.spawnEntity(mob);
            mob.onInitialSpawn();
            mob.team = this.team;
            mob.isAgressive = this.isAgressive;
            mob.parent = this;
            mob.setType(this.rand.nextInt(3));
            this.archelonCreation = mob;
            this.var1 = 1;
         }

         return ield;
      }
   }

   public static class ArchelonCreation extends AbstractMob {
      public int spawnDelay = 40;
      public int maxspawnDelay = 100;
      public int spawns = 0;
      public Archelon parent;
      public UUID parentEntityID;

      public ArchelonCreation(World world) {
         super(world, 1.0F, 1.0F);
         this.hurtSound = Sounds.mob_squish_hurt;
         this.deathSound = Sounds.mob_squish_death;
         this.livingSound = Sounds.mob_squish_living;
         this.defaultteam = AquaticaMobsPack.mobsteam;
         this.setattributes(110.0, 16.0, 0.0, 0.0, 0.0, 2.0, 1.0, 0.0, 0.0, 0.0);
         this.setRoleValues(EnumMobRole.SOLDIER, 5);
         this.soul = Soul.COLLECTIVE;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.9) {
            ShardType.spawnShards(ShardType.LIVE, this, 1.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.JELLY_BLOOD;
      }

      public boolean hasNoGravity() {
         return this.parent != null && this.parent.isEntityAlive() ? true : super.hasNoGravity();
      }

      public void fall(float distance, float damageMultiplier) {
      }

      @Override
      public void writeEntityToNBT(NBTTagCompound compound) {
         if (this.parent != null) {
            compound.setUniqueId("ownerId", this.parent.getUniqueID());
         }

         compound.setInteger("type", this.var1);
         compound.setInteger("spawns", this.spawns);
         compound.setInteger("maxspawnDelay", this.maxspawnDelay);
         super.writeEntityToNBT(compound);
      }

      @Override
      public void readEntityFromNBT(NBTTagCompound compound) {
         if (compound.hasKey("ownerIdMost") && compound.hasKey("ownerIdLeast")) {
            this.parentEntityID = compound.getUniqueId("ownerId");
         }

         if (compound.hasKey("type")) {
            this.var1 = compound.getInteger("type");
         }

         if (compound.hasKey("spawns")) {
            this.spawns = compound.getInteger("spawns");
         }

         if (compound.hasKey("maxspawnDelay")) {
            this.maxspawnDelay = compound.getInteger("maxspawnDelay");
         }

         super.readEntityFromNBT(compound);
      }

      public void setType(int type) {
         this.var1 = MathHelper.clamp(type, 0, 2);
         if (this.var1 == 0) {
            this.maxspawnDelay = 180;
         }

         if (this.var1 == 1) {
            this.maxspawnDelay = 40;
         }

         if (this.var1 == 2) {
            this.maxspawnDelay = 120;
            this.spawns = this.rand.nextInt(3) + 2;
         }
      }

      public boolean canBreatheUnderwater() {
         return true;
      }

      @Override
      public void onUpdate() {
         super.onUpdate();
         if (!this.world.isRemote) {
            if (this.ticksExisted < 2 || this.ticksExisted % 43 == 0) {
               if (this.parent != null && !this.parent.isEntityAlive()) {
                  this.parent = null;
                  this.parentEntityID = null;
               }

               this.world.setEntityState(this, (byte)(this.var1 + 8));
            }

            if (this.spawnDelay > 0) {
               this.spawnDelay--;
            } else if (this.var1 == 0) {
               if (this.getAttackTarget() != null) {
                  boolean shoott = this.getDistanceSq(this.getAttackTarget()) < 25.0;
                  int hostiles = 0;
                  if (!shoott) {
                     for (EntityLivingBase entitylb : this.world.getEntitiesWithinAABB(EntityLivingBase.class, GetMOP.newAABB(this.getPositionVector(), 8.0))) {
                        if (Team.checkIsOpponent(this, entitylb)) {
                           if (++hostiles >= 4) {
                              break;
                           }
                        }
                     }
                  }

                  if (shoott || hostiles >= 3) {
                     this.bombShoot();
                     this.spawnDelay = this.maxspawnDelay;
                  }
               }
            } else if (this.var1 == 1) {
               if (this.isInWater() && this.getAttackTarget() != null && this.getDistanceSq(this.getAttackTarget()) < 256.0) {
                  Breed mob = new Breed(this.world);
                  mob.setPosition(this.posX, this.posY, this.posZ);
                  mob.motionX = (this.rand.nextFloat() - 0.5F) / 13.0F;
                  mob.motionY = (this.rand.nextFloat() - 0.5F) / 13.0F;
                  mob.motionZ = (this.rand.nextFloat() - 0.5F) / 13.0F;
                  this.world.spawnEntity(mob);
                  mob.onInitialSpawn();
                  mob.team = this.team;
                  mob.isAgressive = this.isAgressive;
                  mob.canDropLoot = this.canDropLoot;
                  this.spawnDelay = this.maxspawnDelay;
                  this.maxspawnDelay += 10;
                  this.world.setEntityState(this, (byte)11);
               }
            } else if (this.var1 == 2
               && this.spawns > 0
               && this.isInWater()
               && this.getAttackTarget() != null
               && this.getDistanceSq(this.getAttackTarget()) < 144.0) {
               Hydromona mob = new Hydromona(this.world);
               mob.setPosition(this.posX, this.posY, this.posZ);
               mob.motionX = (this.rand.nextFloat() - 0.5F) / 13.0F;
               mob.motionY = (this.rand.nextFloat() - 0.5F) / 13.0F;
               mob.motionZ = (this.rand.nextFloat() - 0.5F) / 13.0F;
               this.world.spawnEntity(mob);
               mob.onInitialSpawn();
               mob.team = this.team;
               mob.isAgressive = this.isAgressive;
               mob.canDropLoot = this.canDropLoot;
               this.spawnDelay = this.maxspawnDelay;
               this.spawns--;
               this.world.setEntityState(this, (byte)11);
            }

            if (this.parent != null) {
               Vec3d point = this.parent.getPositionVector().add(this.getVectorForRotation(-35.0F, this.parent.renderYawOffset + 180.0F).scale(3.0));
               this.motionX *= 0.93;
               this.motionY *= 0.93;
               this.motionZ *= 0.93;
               SuperKnockback.applyMove(this, -0.2F, point.x, point.y, point.z);
               this.canDropLoot = this.parent.canDropLoot;
            } else if (this.parentEntityID != null) {
               Entity entit = getEntityByUUID(this.world, this.parentEntityID);
               if (entit != null && entit instanceof Archelon) {
                  this.parent = (Archelon)entit;
                  this.parent.archelonCreation = this;
               }
            }
         }
      }

      public void bombShoot() {
         this.world.setEntityState(this, (byte)7);
         int bombcost = this.rand.nextInt(4) + 6;
         float oneAngle = 360.0F / bombcost;

         for (int i = 0; i < bombcost; i++) {
            HostileProjectiles.SeaBomb entityshoot = new HostileProjectiles.SeaBomb(this.world, this);
            entityshoot.shoot(this, this.rotationPitch - 13.0F, this.rotationYaw + oneAngle * i, 0.0F, 0.6F, 2.5F);
            entityshoot.damage = 30.0F;
            entityshoot.explodeTimeOffset = this.rand.nextInt(15) - 40;
            entityshoot.dropBlocks = false;
            this.world.spawnEntity(entityshoot);
         }
      }

      @SideOnly(Side.CLIENT)
      @Override
      public void handleStatusUpdate(byte id) {
         super.handleStatusUpdate(id);
         if (id == 11) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.slimeshoot_a,
                  SoundCategory.HOSTILE,
                  1.5F,
                  1.1F + this.rand.nextFloat() / 5.0F,
                  false
               );
         }

         if (id == 7) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.sea_bomb,
                  SoundCategory.HOSTILE,
                  1.6F,
                  1.1F + this.rand.nextFloat() / 5.0F,
                  false
               );
         }

         if (id == 8) {
            this.var1 = 0;
         }

         if (id == 9) {
            this.var1 = 1;
         }

         if (id == 10) {
            this.var1 = 2;
         }
      }

      protected void initEntityAI() {
         this.tasks.addTask(8, new EntityAIWatchClosest(this, Archelon.class, 8.0F));
         this.tasks.addTask(8, new EntityAILookIdle(this));
         this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
         this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }
   }

   public static class Blisterfish extends AbstractMob {
      public Blisterfish(World world) {
         super(world, 0.85F, 0.85F);
         this.hurtSound = Sounds.mob_fish_hurt;
         this.deathSound = Sounds.mob_fish_death;
         this.livingSound = Sounds.mob_fish_living;
         this.defaultteam = AquaticaMobsPack.mobsteam;
         this.moveHelper = new WaterMoveHelper(this);
         this.setattributes(65.0, 48.0, 13.0, 0.22, 8.0, 4.0, 0.0, 0.0, 0.0, 0.0);
         this.registerLOOT(
            new MobDrop[]{
               new MobDrop(ItemsRegister.FISHSTEAKRAW, 1.0F, 0, 1, 1, 1), new MobDrop(ItemsRegister.PLACODERMSCALES, 0.6F, 0, 1, 1, 0)
            }
         );
         this.var3 = 0.0F;
         this.var4 = 0.0F;
         this.var5 = 0.0F;
         this.setRoleValues(EnumMobRole.MIDDLE_ENEMY, 5);
         this.soul = Soul.COMMON;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.5) {
            ShardType.spawnShards(ShardType.WATER, this, 2.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.5) {
            ShardType.spawnShards(ShardType.POISON, this, 2.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.FISH_BLOOD;
      }

      public float getEyeHeight() {
         return this.height * 0.6F;
      }

      @Override
      public void shoot() {
         if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)7);
            HostileProjectiles.BlisterfishShoot entityshoot = new HostileProjectiles.BlisterfishShoot(this.world, this);
            entityshoot.shoot(this, this.rotationPitch - 5.0F, this.rotationYaw, 0.0F, 1.0F, 1.4F);
            entityshoot.damage = (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
            this.world.spawnEntity(entityshoot);
         }
      }

      @Override
      public void onUpdate() {
         int airr = this.getAir();
         super.onUpdate();
         if (this.world.isRemote) {
            this.var3 = this.var4;
            this.var4 = this.var4 + this.var5;
         }

         if (this.isEntityAlive() && !this.isInWater()) {
            this.setAir(--airr);
            if (this.getAir() == -20) {
               this.setAir(0);
               this.attackEntityFrom(DamageSource.DROWN, 2.0F);
            }
         } else {
            this.setAir(300);
         }
      }

      @SideOnly(Side.CLIENT)
      @Override
      public void handleStatusUpdate(byte id) {
         super.handleStatusUpdate(id);
         if (id == 8) {
            this.var5 = Math.max(this.var5 - 0.04F, 0.0F);
         }

         if (id == 9) {
            this.var5 = Math.min(this.var5 + 0.05F, 2.0F);
         }

         if (id == 7) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.slimeshoot,
                  SoundCategory.HOSTILE,
                  0.9F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );
         }

         if (id == 15) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.blisterfish_prepare,
                  SoundCategory.HOSTILE,
                  1.3F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );
            this.triggerAnimation(-1);
         }
      }

      public boolean canBreatheUnderwater() {
         return true;
      }

      protected PathNavigate createNavigator(World worldIn) {
         return new PathNavigateSwimmer(this, worldIn);
      }

      public void travel(float strafe, float vertical, float forward) {
         if (this.isServerWorld() && this.isInWater()) {
            this.moveRelative(strafe, vertical, forward, 0.1F);
            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.9F;
            this.motionY *= 0.9F;
            this.motionZ *= 0.9F;
         } else {
            super.travel(strafe, vertical, forward);
         }
      }

      protected void initEntityAI() {
         this.tasks.addTask(1, new EntityAIShootAndSwim(this, 57 - this.getMobDifficulty() * 5, 17.0F, 23 - this.getMobDifficulty() * 4, 10, 60));
         this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0));
         this.tasks.addTask(7, new EntityAIWander(this, 1.0, 80));
         this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
         this.tasks.addTask(8, new EntityAILookIdle(this));
         this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
         this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }
   }

   public static class Breed extends AbstractMob {
      public static float motionMaxSpeed = 1.0F;
      public static float speedIncrease = 0.05F;
      public int shootCooldown = 0;
      public boolean move = false;

      public Breed(World world) {
         super(world, 0.5F, 0.5F);
         this.hurtSound = Sounds.mob_squish_hurt;
         this.deathSound = Sounds.mob_squish_death;
         this.defaultteam = AquaticaMobsPack.mobsteam;
         this.moveHelper = new WaterMoveHelper(this);
         this.setattributes(30.0, 32.0, 8.0, 0.26, 0.0, 0.0, 0.0, 0.0, 0.5, 0.0);
         this.registerLOOT(new MobDrop[]{new MobDrop(ItemsRegister.MESOGLEA, 0.03F, 0, 1, 1, 0)});
         if (world.isRemote) {
            this.var3 = 0.0F;
            this.var4 = 0.0F;
         }

         this.setRoleValues(EnumMobRole.SWARMER, 5);
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.1) {
            ShardType.spawnShards(ShardType.WATER, this, 1.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.POLYP_BLOOD;
      }

      protected float getSoundPitch() {
         return 1.2F;
      }

      public float getEyeHeight() {
         return this.height * 0.5F;
      }

      @Override
      public void onUpdate() {
         int airr = this.getAir();
         super.onUpdate();
         if (this.world.isRemote) {
            this.var3 = this.var4;
            this.var4 = this.var4
               + (float)this.getDistance(
                  this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ
               );
         }

         if (this.isEntityAlive() && !this.isInWater()) {
            this.setAir(--airr);
            if (this.getAir() == -20) {
               this.setAir(0);
               this.attackEntityFrom(DamageSource.DROWN, 2.0F);
               if (this.getHealth() <= 0.0F) {
                  this.loot = null;
               }
            }
         } else {
            this.setAir(300);
         }

         float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
         this.rotationYaw = (float)(MathHelper.atan2(this.motionX, this.motionZ) * (180.0 / Math.PI));
         this.rotationPitch = (float)(MathHelper.atan2(this.motionY, f) * (180.0 / Math.PI));

         while (this.rotationPitch - this.prevRotationPitch < -180.0F) {
            this.prevRotationPitch -= 360.0F;
         }

         while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
            this.prevRotationPitch += 360.0F;
         }

         while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
            this.prevRotationYaw -= 360.0F;
         }

         while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
            this.prevRotationYaw += 360.0F;
         }

         this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
         this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
      }

      @SideOnly(Side.CLIENT)
      public void setVelocity(double x, double y, double z) {
         this.motionX = x;
         this.motionY = y;
         this.motionZ = z;
         if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
            float f = MathHelper.sqrt(x * x + z * z);
            this.rotationYaw = (float)(MathHelper.atan2(x, z) * (180.0 / Math.PI));
            this.rotationPitch = (float)(MathHelper.atan2(y, f) * (180.0 / Math.PI));
            this.prevRotationYaw = this.rotationYaw;
            this.prevRotationPitch = this.rotationPitch;
         }
      }

      @SideOnly(Side.CLIENT)
      @Override
      public void handleStatusUpdate(byte id) {
         super.handleStatusUpdate(id);
      }

      public boolean canBreatheUnderwater() {
         return true;
      }

      protected PathNavigate createNavigator(World worldIn) {
         return new PathNavigateSwimmer(this, worldIn);
      }

      public void travel(float strafe, float vertical, float forward) {
         if (this.isServerWorld() && this.isInWater()) {
            this.moveRelative(strafe, vertical, forward, 0.1F);
            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.9F;
            this.motionY *= 0.9F;
            this.motionZ *= 0.9F;
         } else {
            super.travel(strafe, vertical, forward);
         }
      }

      protected void initEntityAI() {
         this.tasks.addTask(1, new EntityAIRush(this, true, true, false));
         this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0));
         this.tasks.addTask(7, new EntityAIWander(this, 1.0, 80));
         this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
         this.tasks.addTask(8, new EntityAILookIdle(this));
         this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
         this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }
   }

   public static class DarkMermaid extends Mermaid {
      public DarkMermaid(World world) {
         super(world);
         this.setattributes(200.0, 64.0, 20.0, 0.27, 10.0, 7.0, 0.4, 0.2, 0.0, 0.0);
         this.registerLOOT(
            new MobDrop[]{
               new MobDrop(ItemsRegister.FISHSTEAKRAW, 0.5F, 0, 1, 1, 3),
               new MobDrop(ItemsRegister.NUGGETAQUATIC, 0.5F, 0, 1, 3, 0),
               new MobDrop(ItemsRegister.PEARLBLACK, 0.1F, 0, 1, 1, 0)
            }
         );
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.7) {
            ShardType.spawnShards(ShardType.WATER, this, 4.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.8) {
            ShardType.spawnShards(ShardType.LIVE, this, 4.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.75) {
            ShardType.spawnShards(ShardType.FIRE, this, 3.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.6) {
            ShardType.spawnShards(ShardType.VOID, this, 1.0F * dropShardsQuantity);
         }
      }
   }

   public static class Dartfish extends AbstractMob {
      public Dartfish(World world) {
         super(world, 1.0F, 1.0F);
         this.hurtSound = Sounds.mob_fish_hurt;
         this.deathSound = Sounds.mob_fish_death;
         this.livingSound = Sounds.mob_fish_living;
         this.defaultteam = AquaticaMobsPack.mobsteam;
         this.moveHelper = new WaterMoveHelper(this);
         this.setattributes(100.0, 48.0, 15.0, 0.25, 9.0, 5.0, 0.0, 0.0, 0.0, 0.0);
         this.registerLOOT(
            new MobDrop[]{
               new MobDrop(ItemsRegister.FISHSTEAKRAW, 1.0F, 0, 1, 2, 2), new MobDrop(ItemsRegister.PLACODERMSCALES, 0.9F, 0, 1, 1, 0)
            }
         );
         this.var3 = 0.0F;
         this.var4 = 0.0F;
         this.var5 = 0.0F;
         this.setRoleValues(EnumMobRole.STRONG_ENEMY, 5);
         this.soul = Soul.DEEPWATER;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.7) {
            ShardType.spawnShards(ShardType.WATER, this, 2.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.8) {
            ShardType.spawnShards(ShardType.PAIN, this, 2.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.FISH_BLOOD;
      }

      protected float getSoundPitch() {
         return 0.87F;
      }

      public float getEyeHeight() {
         return this.height * 0.6F;
      }

      @Override
      public void shoot() {
         if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)7);
            HostileProjectiles.ArrowDartfish entityshoot = new HostileProjectiles.ArrowDartfish(this.world, this);
            entityshoot.shoot(this, this.rotationPitch - 3.0F, this.rotationYaw, 0.0F, 1.4F, 1.0F);
            entityshoot.setDamage(this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
            this.world.spawnEntity(entityshoot);
         }
      }

      @Override
      public void onUpdate() {
         int airr = this.getAir();
         super.onUpdate();
         if (this.world.isRemote) {
            this.var3 = this.var4;
            this.var4 = this.var4 + this.var5;
         }

         if (this.isEntityAlive() && !this.isInWater()) {
            this.setAir(--airr);
            if (this.getAir() == -20) {
               this.setAir(0);
               this.attackEntityFrom(DamageSource.DROWN, 2.0F);
            }
         } else {
            this.setAir(300);
         }
      }

      @SideOnly(Side.CLIENT)
      @Override
      public void handleStatusUpdate(byte id) {
         super.handleStatusUpdate(id);
         if (id == 8) {
            this.var5 = Math.max(this.var5 - 0.04F, 0.0F);
         }

         if (id == 9) {
            this.var5 = Math.min(this.var5 + 0.05F, 2.0F);
         }

         if (id == 7) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.dartfish_shoot,
                  SoundCategory.HOSTILE,
                  1.4F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );
         }

         if (id == 15) {
            this.triggerAnimation(-1);
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.dartfish_prepare,
                  SoundCategory.HOSTILE,
                  1.5F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );
         }
      }

      public boolean canBreatheUnderwater() {
         return true;
      }

      protected PathNavigate createNavigator(World worldIn) {
         return new PathNavigateSwimmer(this, worldIn);
      }

      public void travel(float strafe, float vertical, float forward) {
         if (this.isServerWorld() && this.isInWater()) {
            this.moveRelative(strafe, vertical, forward, 0.1F);
            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.9F;
            this.motionY *= 0.9F;
            this.motionZ *= 0.9F;
         } else {
            super.travel(strafe, vertical, forward);
         }
      }

      protected void initEntityAI() {
         this.tasks.addTask(1, new EntityAIShootAndSwim(this, 71 - this.getMobDifficulty() * 8, 20.0F, 20 - this.getMobDifficulty() * 4, 15, 80));
         this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0));
         this.tasks.addTask(7, new EntityAIWander(this, 1.0, 80));
         this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
         this.tasks.addTask(8, new EntityAILookIdle(this));
         this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
         this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }
   }

   public static class Dunkleosteus extends AbstractMob {
      public Dunkleosteus(World world) {
         super(world, 1.3F, 1.2F);
         this.hurtSound = Sounds.mob_zombie_hurt;
         this.deathSound = Sounds.mob_zombie_death;
         this.livingSound = Sounds.mob_zombie_living;
         this.stepSound = SoundEvents.ENTITY_HUSK_STEP;
         this.defaultteam = AquaticaMobsPack.mobsteam;
         this.moveHelper = new WaterMoveHelper(this);
         this.setattributes(30.0, 64.0, 2.0, 0.45, 5.0, 5.0, 0.3, 0.3, 0.0, 0.0);
         this.registerLOOT(new MobDrop[]{new MobDrop(Items.ROTTEN_FLESH, 1.0F, 0, 0, 4, 4)});
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.FISH_BLOOD;
      }

      public boolean canBreatheUnderwater() {
         return true;
      }

      protected PathNavigate createNavigator(World worldIn) {
         return new PathNavigateSwimmer(this, worldIn);
      }

      public void travel(float strafe, float vertical, float forward) {
         if (this.isServerWorld() && this.isInWater()) {
            this.moveRelative(strafe, vertical, forward, 0.1F);
            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.9F;
            this.motionY *= 0.9F;
            this.motionZ *= 0.9F;
         } else {
            super.travel(strafe, vertical, forward);
         }
      }

      protected void initEntityAI() {
         this.tasks.addTask(1, new EntityAIGrapBite(this, true, 60, 2.5F, 1.0F, 1.0F, 80, 10.0F, false, 1.0F, true, false, false));
         this.tasks.addTask(2, new EntityAIRush(this, true, true, false));
         this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0));
         this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
         this.tasks.addTask(8, new EntityAILookIdle(this));
         this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
         this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }
   }

   public static class EntityAISiren extends EntityAIBase {
      public Siren mob;
      public EntityAICorrupter corrupter;
      public EntityAIRayLogicFly fly;
      public EntityAICorrupterIdle idle;
      public int dashCooldown = 0;
      public int dashCooldownMax = 50;
      public int attackTick;
      public int dashState = 0;
      public Vec3d dashVector = null;
      public static int dashTIME = 7;

      public EntityAISiren(Siren mob) {
         this.mob = mob;
         this.corrupter = new EntityAICorrupter(mob, 34 + mob.getRNG().nextInt(7), 40.0F, 1, true, 22.0F, 10.0F);
         this.fly = new EntityAIRayLogicFly(mob);
         this.idle = new EntityAICorrupterIdle(mob);
         this.corrupter.deltaRotation = 70;
      }

      public boolean shouldExecute() {
         return true;
      }

      public void updateTask() {
         EntityLivingBase attarget = this.mob.getAttackTarget();
         if (this.dashCooldown > 0) {
            this.dashCooldown--;
         }

         this.attackTick = Math.max(this.attackTick - 1, 0);
         if (this.dashState > 0) {
            this.dashState++;
            if (this.dashState > dashTIME) {
               this.dashState = -this.dashState;
            }

            if (this.dashVector != null) {
               this.mob.motionX = this.mob.motionX + this.dashVector.x;
               this.mob.motionY = this.mob.motionY + this.dashVector.y;
               this.mob.motionZ = this.mob.motionZ + this.dashVector.z;
            }

            if (attarget != null) {
               this.checkAndPerformAttack(attarget, this.mob.getDistanceSq(attarget));
            }
         } else if (this.dashState < 0) {
            this.dashState++;
            if (this.dashVector != null) {
               this.mob.motionX = this.mob.motionX - this.dashVector.x;
               this.mob.motionY = this.mob.motionY - this.dashVector.y;
               this.mob.motionZ = this.mob.motionZ - this.dashVector.z;
            }

            if (this.dashState == 0) {
               this.dashVector = null;
               this.dashCooldown = this.dashCooldownMax;
            }
         } else if (attarget != null) {
            double distSq = this.mob.getDistanceSq(attarget);
            if (!this.mob.dangerMode) {
               if (this.mob.MANA >= 10.0F && distSq > 47.0) {
                  this.mob.attackStyle = 1;
               } else {
                  this.mob.attackStyle = 2;
               }
            } else {
               this.mob.attackStyle = 1;
            }

            if (this.mob.attackStyle == 2) {
               this.fly.updateTask();
               if (distSq <= 25.0 && this.dashCooldown <= 0) {
                  this.dashState = 1;
                  this.dashVector = attarget.getPositionVector().subtract(this.mob.getPositionVector()).normalize().scale(0.25);
               }
            }
         } else {
            this.mob.attackStyle = 0;
         }

         if (this.mob.attackStyle == 0) {
            this.idle.updateTask();
         }

         if (this.mob.attackStyle == 1) {
            this.corrupter.updateTask();
         }
      }

      protected void checkAndPerformAttack(EntityLivingBase enemy, double distToEnemySqr) {
         double d0 = this.getAttackReachSqr(enemy);
         if (distToEnemySqr <= d0 && this.attackTick <= 0) {
            this.attackTick = 20;
            this.mob.attackEntityAsMob(enemy);
         }
      }

      protected double getAttackReachSqr(EntityLivingBase attackTarget) {
         return this.mob.width * 2.3F * this.mob.width * 2.3F + attackTarget.width;
      }
   }

   public static class Hydromona extends AbstractMob {
      public static ResourceLocation cloud = new ResourceLocation("arpg:textures/waterhammer.png");
      public static ResourceLocation cloud2 = new ResourceLocation("arpg:textures/slimesplash.png");
      public static float motionMaxSpeed = 1.0F;
      public static float speedIncrease = 0.05F;
      public static float motionMaxSpeedRush = 1.2F;
      public boolean move = false;

      public Hydromona(World world) {
         super(world, 0.98F, 0.98F);
         this.hurtSound = Sounds.mob_squish_hurt;
         this.deathSound = Sounds.mob_squish_death;
         this.defaultteam = AquaticaMobsPack.mobsteam;
         this.moveHelper = new WaterMoveHelper(this);
         this.setattributes(135.0, 30.0, 16.0, 0.07, 0.0, 0.0, 0.3F, 0.0, 0.0, 0.0);
         this.registerLOOT(new MobDrop[]{new MobDrop(ItemsRegister.MESOGLEA, 0.3F, 0, 0, 2, 0)});
         if (world.isRemote) {
            this.var3 = 0.0F;
            this.var4 = 0.0F;
            this.var5 = 0.0F;
         }

         this.setRoleValues(EnumMobRole.STRONG_SOLDIER, 5);
         this.soul = Soul.COLLECTIVE;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.6) {
            ShardType.spawnShards(ShardType.WATER, this, 2.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.1) {
            ShardType.spawnShards(ShardType.VOID, this, 1.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.JELLY_BLOOD;
      }

      public float getEyeHeight() {
         return this.height * 0.5F;
      }

      @Override
      public void onUpdate() {
         int airr = this.getAir();
         super.onUpdate();
         if (this.world.isRemote) {
            this.var3 = this.var4;
            if (this.move) {
               this.var5 = (float)(this.var5 * 0.9);
               this.var4 = this.var4 + this.var5;
            } else if (this.var5 < motionMaxSpeed) {
               this.var5 = this.var5 + speedIncrease;
            }
         }

         if (this.isEntityAlive() && !this.isInWater()) {
            this.setAir(--airr);
            if (this.getAir() == -20) {
               this.setAir(0);
               this.attackEntityFrom(DamageSource.DROWN, 2.0F);
               if (this.getHealth() <= 0.0F) {
                  this.loot = null;
               }
            }
         } else {
            this.setAir(300);
         }

         float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
         this.rotationYaw = (float)(MathHelper.atan2(this.motionX, this.motionZ) * (180.0 / Math.PI));
         this.rotationPitch = (float)(MathHelper.atan2(this.motionY, f) * (180.0 / Math.PI));

         while (this.rotationPitch - this.prevRotationPitch < -180.0F) {
            this.prevRotationPitch -= 360.0F;
         }

         while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
            this.prevRotationPitch += 360.0F;
         }

         while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
            this.prevRotationYaw -= 360.0F;
         }

         while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
            this.prevRotationYaw += 360.0F;
         }

         this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
         this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
         this.rotationYawHead = this.rotationYaw;
      }

      @SideOnly(Side.CLIENT)
      public void setVelocity(double x, double y, double z) {
         this.motionX = x;
         this.motionY = y;
         this.motionZ = z;
         if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
            float f = MathHelper.sqrt(x * x + z * z);
            this.rotationYaw = (float)(MathHelper.atan2(x, z) * (180.0 / Math.PI));
            this.rotationPitch = (float)(MathHelper.atan2(y, f) * (180.0 / Math.PI));
            this.prevRotationYaw = this.rotationYaw;
            this.prevRotationPitch = this.rotationPitch;
         }
      }

      @SideOnly(Side.CLIENT)
      @Override
      public void handleStatusUpdate(byte id) {
         super.handleStatusUpdate(id);
         if (id == 29) {
            this.move = true;
         }

         if (id == 28) {
            this.move = false;
         }

         if (id == 11) {
            for (int i = 2; i < 6; i++) {
               GUNParticle part = new GUNParticle(
                  cloud,
                  0.7F - i / 10.0F,
                  0.0F,
                  20,
                  140,
                  this.world,
                  this.posX,
                  this.posY + this.getEyeHeight(),
                  this.posZ,
                  0.0F,
                  0.0F,
                  0.0F,
                  0.25F,
                  0.6F + this.rand.nextFloat() / 10.0F,
                  0.9F + this.rand.nextFloat() / 10.0F,
                  true,
                  this.rand.nextInt(360),
                  true,
                  1.0F
               );
               part.shoot(this, -this.rotationPitch, -this.rotationYaw, 0.0F, 1.0F - i / 9.0F, 0.0F);
               part.rotationPitchYaw = new Vec2f(-this.rotationPitch, -this.rotationYaw);
               part.alphaTickAdding = -0.04F - i * 0.01F;
               part.scaleTickAdding = 0.07F - i * 0.01F;
               part.alphaGlowing = true;
               this.world.spawnEntity(part);
            }

            for (int i = 0; i < 2; i++) {
               GUNParticle part = new GUNParticle(
                  cloud,
                  0.5F + i / 10.0F,
                  0.0F,
                  20,
                  140,
                  this.world,
                  this.posX,
                  this.posY + this.getEyeHeight(),
                  this.posZ,
                  0.0F,
                  0.0F,
                  0.0F,
                  0.25F,
                  0.6F + this.rand.nextFloat() / 10.0F,
                  0.9F + this.rand.nextFloat() / 10.0F,
                  true,
                  this.rand.nextInt(360),
                  true,
                  1.0F
               );
               part.shoot(this, -this.rotationPitch, -this.rotationYaw, 0.0F, 0.9F - i / 9.0F, 0.0F);
               part.rotationPitchYaw = new Vec2f(-this.rotationPitch, -this.rotationYaw);
               part.alphaTickAdding = -0.04F - i * 0.01F;
               part.scaleTickAdding = 0.07F - i * 0.01F;
               part.alphaGlowing = true;
               this.world.spawnEntity(part);
            }

            for (int i = 0; i < 5; i++) {
               GUNParticle part = new GUNParticle(
                  cloud2,
                  0.2F + this.rand.nextFloat() / 4.0F,
                  0.0F,
                  20,
                  140,
                  this.world,
                  this.posX,
                  this.posY + this.getEyeHeight(),
                  this.posZ,
                  0.0F,
                  0.0F,
                  0.0F,
                  0.2F,
                  0.6F + this.rand.nextFloat() / 10.0F,
                  0.9F + this.rand.nextFloat() / 10.0F,
                  true,
                  this.rand.nextInt(360),
                  true,
                  1.0F
               );
               part.shoot(this, -this.rotationPitch, -this.rotationYaw, 0.0F, 0.9F - i / 9.0F, 6.0F);
               part.alpha = 0.7F;
               part.alphaTickAdding = -0.035F;
               part.scaleTickAdding = 0.07F - i * 0.01F;
               this.world.spawnEntity(part);
            }
         }
      }

      public boolean canBreatheUnderwater() {
         return true;
      }

      protected PathNavigate createNavigator(World worldIn) {
         return new PathNavigateSwimmer(this, worldIn);
      }

      public void travel(float strafe, float vertical, float forward) {
         if (this.isServerWorld() && this.isInWater()) {
            this.moveRelative(strafe, vertical, forward, 0.1F);
            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.9F;
            this.motionY *= 0.9F;
            this.motionZ *= 0.9F;
         } else {
            super.travel(strafe, vertical, forward);
         }
      }

      protected void initEntityAI() {
         this.tasks.addTask(2, new EntityAIJellyfishRush(this, motionMaxSpeedRush, speedIncrease, 0.1F));
         this.tasks
            .addTask(
               3,
               new EntityAIForceAttack(this, -1.0F, 60, 3.5F, 0.0F, 3.0F, 0.2F, 3.5F, 1.3F)
                  .setSoundOnAttack(Sounds.explode_water_b)
                  .setuseRocketRotations()
                  .setWaterhammer()
            );
         this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0));
         this.tasks.addTask(7, new EntityAIRandomSwim(this, motionMaxSpeed, speedIncrease, 0.01F, true));
         this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
         this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }
   }

   public abstract static class KrakenTentacle extends AbstractMob implements IEntitySynchronize {
      public BossKraken kraken;
      public double maxLengthSq = 1444.0;
      public double reagreeDistSq = 784.0;

      public KrakenTentacle(World worldIn, float sizeWidth, float sizeHeight) {
         super(worldIn, sizeWidth, sizeHeight);
         this.getLookHelper().setLookPosition(Double.MAX_VALUE, 0.0, 0.0, 0.0F, 0.0F);
         this.setRoleValues(EnumMobRole.SPECIAL_MOB, 5);
         this.soul = null;
      }

      public AxisAlignedBB getRenderBoundingBox() {
         return super.getRenderBoundingBox().grow(100.0);
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.KRAKEN_BLOOD;
      }

      public float getEyeHeight() {
         return this.height * 0.5F;
      }

      @SideOnly(Side.CLIENT)
      public boolean isInRangeToRenderDist(double distance) {
         double d0 = 256.0;
         return distance < d0 * d0;
      }

      @Override
      public AbstractMob getOwnerIfSegment() {
         return this.kraken;
      }

      protected boolean canDespawn() {
         return false;
      }

      public float getCollisionBorderSize() {
         return 0.8F;
      }

      @Override
      public void onUpdate() {
         super.onUpdate();
         if (this.kraken != null) {
            if (!this.world.isRemote && (this.ticksExisted % 40 == 0 || this.ticksExisted < 2)) {
               IEntitySynchronize.sendSynchronize(this, 64.0, this.kraken.getEntityId());
               this.isAgressive = this.kraken.isAgressive;
               this.team = this.kraken.team;
               if (this.getAttackTarget() != null
                  && this.kraken.getAttackTarget() != null
                  && this.getAttackTarget().getDistanceSq(this.kraken.getAttackTarget()) > this.reagreeDistSq) {
                  this.setAttackTarget(this.kraken.getAttackTarget());
               }
            }

            double followpower = 0.8;
            this.motionX = this.motionX
               + MathHelper.clamp((this.kraken.tentaclesAlignX - this.kraken.prevtentaclesAlignX) * followpower, -0.05F, 0.05F);
            this.motionY = this.motionY
               + MathHelper.clamp((this.kraken.tentaclesAlignY - this.kraken.prevtentaclesAlignY) * followpower, -0.05F, 0.05F);
            this.motionZ = this.motionZ
               + MathHelper.clamp((this.kraken.tentaclesAlignZ - this.kraken.prevtentaclesAlignZ) * followpower, -0.05F, 0.05F);
            if (!this.world.isRemote && (this.kraken.isDead || !this.kraken.isAddedToWorld() && this.ticksExisted > 20)) {
               this.setDead();
            }
         } else if (!this.world.isRemote && this.ticksExisted > 20) {
            this.setDead();
         }

         if (!this.isInWater()) {
            this.motionX *= 0.96;
            this.motionY *= 0.96;
            this.motionZ *= 0.96;
         }
      }

      @Override
      public void onClient(double... args) {
         if (args.length == 1) {
            Entity entity = this.world.getEntityByID((int)args[0]);
            if (entity instanceof BossKraken) {
               this.kraken = (BossKraken)entity;
            }
         }
      }

      @Override
      public boolean attackEntityFrom(DamageSource source, float amount) {
         return source != DamageSource.DROWN && source != DamageSource.IN_WALL ? super.attackEntityFrom(source, amount) : false;
      }

      public void fall(float distance, float damageMultiplier) {
      }
   }

   public static class KrakenTentacleBite extends KrakenTentacle implements IRender {
      public static ResourceLocation textureTentacle = new ResourceLocation("arpg:textures/kraken_tentacle_segment.png");

      public KrakenTentacleBite(World world) {
         super(world, 1.4F, 1.4F);
         this.hurtSound = Sounds.mob_fish_hurt;
         this.deathSound = Sounds.mob_fish_death;
         this.livingSound = Sounds.mob_squish_living;
         this.defaultteam = AquaticaMobsPack.mobsteam;
         this.setattributes(90.0, 64.0, 19.0, 0.18, 0.0, 0.0, 0.2, 0.0, 0.5, 0.0);
         this.registerLOOT(new MobDrop[]{new MobDrop(Item.getItemFromBlock(Blocks.OBSIDIAN), 0.55F, 0, 0, 2, 1)});
         this.setNoGravity(true);
         this.noClip = true;
         this.ignoreFrustumCheck = true;
      }

      @Override
      public void onDeath(DamageSource cause) {
         if (cause.getTrueSource() != null && this.kraken != null) {
            this.kraken.attackEntityFrom(DamageSource.GENERIC, this.getMaxHealth() * 1.75F);
         }

         super.onDeath(cause);
      }

      @Override
      public void onUpdate() {
         super.onUpdate();
         if (this.kraken != null) {
            double distSq = this.getDistanceSq(this.kraken.tentaclesAlignX, this.kraken.tentaclesAlignY, this.kraken.tentaclesAlignZ);
            if (distSq > this.maxLengthSq) {
               SuperKnockback.applyMove(this, -0.2F, this.kraken.tentaclesAlignX, this.kraken.tentaclesAlignY, this.kraken.tentaclesAlignZ);
            }
         }

         while (this.rotationPitch - this.prevRotationPitch < -180.0F) {
            this.prevRotationPitch -= 360.0F;
         }

         while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
            this.prevRotationPitch += 360.0F;
         }

         while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
            this.prevRotationYaw -= 360.0F;
         }

         while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
            this.prevRotationYaw += 360.0F;
         }
      }

      protected void initEntityAI() {
         this.tasks.addTask(1, new EntityAITentacle(this, true, 8.0F, 4.0F, 0.2F, 140, 60, 2.0F));
         this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(2, new EntityAIAgree(this, EntityPlayer.class, false));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, false));
      }

      @SideOnly(Side.CLIENT)
      @Override
      public void renderPost(EntityLiving entity, double x, double y, double z, float entityYaw, float partialTicks) {
         Minecraft.getMinecraft().getTextureManager().bindTexture(textureTentacle);
         if (entity instanceof KrakenTentacle) {
            KrakenTentacle tentacle = (KrakenTentacle)entity;
            if (tentacle.kraken != null) {
               Vec3d pos1 = tentacle.kraken.getTentaclesAlign(partialTicks);
               Vec3d pos2 = GetMOP.entityCenterPos(tentacle, partialTicks);
               Vec3d normalizedRotation1 = GetMOP.PitchYawToVec3d(
                  -GetMOP.partial(tentacle.kraken.rotationPitch, tentacle.kraken.prevRotationPitch),
                  -GetMOP.partial(tentacle.kraken.rotationYaw, tentacle.kraken.prevRotationYaw)
               );
               Vec3d normalizedRotation2 = tentacle.getLook(partialTicks).scale(-1.0);
               Vec3d[] segmentsPoses = RenderTentacles.getTentacleSegmenstPositions(pos1, pos2, normalizedRotation1, normalizedRotation2, 40.0, 20);
               int lightStart = Math.max(
                     this.world.getLightFor(EnumSkyBlock.BLOCK, new BlockPos(pos1)),
                     this.world.getLightFor(EnumSkyBlock.SKY, new BlockPos(pos1))
                  )
                  * 15;
               int lightEnd = Math.max(
                     this.world.getLightFor(EnumSkyBlock.BLOCK, new BlockPos(pos2)),
                     this.world.getLightFor(EnumSkyBlock.SKY, new BlockPos(pos2))
                  )
                  * 15;
               RenderTentacles.renderTentacle(segmentsPoses, partialTicks, 1.1F, 0.4F, lightStart, lightEnd);
            }
         }
      }
   }

   public static class KrakenTentacleCrash extends KrakenTentacle implements IRender {
      public static ResourceLocation textureTentacle = new ResourceLocation("arpg:textures/kraken_tentacle_crash_segment.png");

      public KrakenTentacleCrash(World world) {
         super(world, 2.4F, 2.4F);
         this.hurtSound = Sounds.shield_hit_soft;
         this.deathSound = Sounds.mob_fish_death;
         this.defaultteam = AquaticaMobsPack.mobsteam;
         this.setattributes(160.0, 64.0, 12.0, 0.065, 6.0, 8.0, 0.8, 0.5, 0.0, 0.0);
         this.registerLOOT(new MobDrop[]{new MobDrop(Item.getItemFromBlock(Blocks.OBSIDIAN), 0.55F, 0, 0, 2, 1)});
         this.setNoGravity(true);
         this.ignoreFrustumCheck = true;
      }

      @Override
      public void onUpdate() {
         super.onUpdate();
         if (this.kraken != null) {
            double distSq = this.getDistanceSq(this.kraken.tentaclesAlignX, this.kraken.tentaclesAlignY, this.kraken.tentaclesAlignZ);
            if (distSq > this.maxLengthSq) {
               SuperKnockback.applyMove(this, -0.2F, this.kraken.tentaclesAlignX, this.kraken.tentaclesAlignY, this.kraken.tentaclesAlignZ);
               this.noClip = true;
            } else {
               this.noClip = false;
            }
         }

         while (this.rotationPitch - this.prevRotationPitch < -180.0F) {
            this.prevRotationPitch -= 360.0F;
         }

         while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
            this.prevRotationPitch += 360.0F;
         }

         while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
            this.prevRotationYaw -= 360.0F;
         }

         while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
            this.prevRotationYaw += 360.0F;
         }
      }

      @SideOnly(Side.CLIENT)
      @Override
      public void handleStatusUpdate(byte id) {
         super.handleStatusUpdate(id);
         if (id == 13) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.kraken_crash,
                  SoundCategory.HOSTILE,
                  2.7F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );
         }
      }

      protected void initEntityAI() {
         this.tasks
            .addTask(1, new EntityAITentacle(this, true, 7.0F, 6.0F, 0.25F, 140, 60, 2.4F).setBreakBlocks(BossKraken.hardnessBreaks, 0.12F, 2));
         this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(2, new EntityAIAgree(this, EntityPlayer.class, false));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, false));
      }

      @SideOnly(Side.CLIENT)
      @Override
      public void renderPost(EntityLiving entity, double x, double y, double z, float entityYaw, float partialTicks) {
         Minecraft.getMinecraft().getTextureManager().bindTexture(textureTentacle);
         if (entity instanceof KrakenTentacle) {
            KrakenTentacle tentacle = (KrakenTentacle)entity;
            if (tentacle.kraken != null) {
               Vec3d pos1 = tentacle.kraken.getTentaclesAlign(partialTicks);
               Vec3d pos2 = GetMOP.entityCenterPos(tentacle, partialTicks);
               Vec3d normalizedRotation1 = GetMOP.PitchYawToVec3d(
                  -GetMOP.partial(tentacle.kraken.rotationPitch, tentacle.kraken.prevRotationPitch),
                  -GetMOP.partial(tentacle.kraken.rotationYaw, tentacle.kraken.prevRotationYaw)
               );
               Vec3d normalizedRotation2 = tentacle.getLook(partialTicks).scale(-1.0);
               Vec3d[] segmentsPoses = RenderTentacles.getTentacleSegmenstPositions(pos1, pos2, normalizedRotation1, normalizedRotation2, 40.0, 20);
               int lightStart = Math.max(
                     this.world.getLightFor(EnumSkyBlock.BLOCK, new BlockPos(pos1)),
                     this.world.getLightFor(EnumSkyBlock.SKY, new BlockPos(pos1))
                  )
                  * 15;
               int lightEnd = Math.max(
                     this.world.getLightFor(EnumSkyBlock.BLOCK, new BlockPos(pos2)),
                     this.world.getLightFor(EnumSkyBlock.SKY, new BlockPos(pos2))
                  )
                  * 15;
               RenderTentacles.renderTentacle(segmentsPoses, partialTicks, 1.2F, 0.45F, lightStart, lightEnd);
            }
         }
      }
   }

   public static class KrakenTentacleGrab extends KrakenTentacle implements IRender {
      public boolean isAngryBiteTentacle = false;
      public static ResourceLocation textureTentacle = new ResourceLocation("arpg:textures/kraken_tentacle_grab_segment.png");
      EntityAIGrapBite grab;

      public KrakenTentacleGrab(World world) {
         super(world, 1.4F, 1.4F);
         this.hurtSound = Sounds.mob_fish_hurt;
         this.deathSound = Sounds.mob_fish_death;
         this.livingSound = Sounds.mob_squish_living;
         this.defaultteam = AquaticaMobsPack.mobsteam;
         this.setattributes(100.0, 64.0, 6.0, 0.16, 2.0, 4.0, 0.3, 0.0, 0.3, 0.0);
         this.registerLOOT(new MobDrop[]{new MobDrop(Item.getItemFromBlock(Blocks.OBSIDIAN), 0.55F, 0, 0, 2, 1)});
         this.setNoGravity(true);
         this.noClip = true;
         this.ignoreFrustumCheck = true;
      }

      public KrakenTentacleGrab(World world, boolean isAngry) {
         super(world, 1.4F, 1.4F);
         this.hurtSound = Sounds.mob_fish_hurt;
         this.deathSound = Sounds.mob_fish_death;
         this.livingSound = Sounds.mob_squish_living;
         this.defaultteam = AquaticaMobsPack.mobsteam;
         this.setattributes(isAngry ? 150.0 : 100.0, 64.0, 6.0, isAngry ? 0.2 : 0.16, 2.0, isAngry ? 6.0 : 4.0, 0.3, 0.0, 0.3, 0.0);
         this.registerLOOT(new MobDrop[]{new MobDrop(Item.getItemFromBlock(Blocks.OBSIDIAN), 0.55F, 0, 0, 2, 1)});
         this.setNoGravity(true);
         this.noClip = true;
         this.ignoreFrustumCheck = true;
         this.isAngryBiteTentacle = isAngry;
      }

      @Override
      public void onDeath(DamageSource cause) {
         if (this.isAngryBiteTentacle && this.kraken != null) {
            this.kraken.hasAngryTentacle = false;
            this.kraken.timeEnemyNoInWater = -50;
         }

         super.onDeath(cause);
      }

      public boolean isAngryAndGrabSomebody() {
         return this.grab != null && this.isAngryBiteTentacle && this.grab.grapped != null;
      }

      @Override
      public void onUpdate() {
         super.onUpdate();
         if (this.kraken != null) {
            double distSq = this.getDistanceSq(this.kraken.tentaclesAlignX, this.kraken.tentaclesAlignY, this.kraken.tentaclesAlignZ);
            if (distSq > this.maxLengthSq || this.isAngryAndGrabSomebody()) {
               SuperKnockback.applyMove(this, -0.2F, this.kraken.tentaclesAlignX, this.kraken.tentaclesAlignY, this.kraken.tentaclesAlignZ);
            }
         }

         while (this.rotationPitch - this.prevRotationPitch < -180.0F) {
            this.prevRotationPitch -= 360.0F;
         }

         while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
            this.prevRotationPitch += 360.0F;
         }

         while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
            this.prevRotationYaw -= 360.0F;
         }

         while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
            this.prevRotationYaw += 360.0F;
         }
      }

      @SideOnly(Side.CLIENT)
      @Override
      public void handleStatusUpdate(byte id) {
         super.handleStatusUpdate(id);
         if (id == 15) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.grab_bite,
                  SoundCategory.HOSTILE,
                  1.3F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );
         }
      }

      protected void initEntityAI() {
         if (this.isAngryBiteTentacle) {
            this.tasks.addTask(1, new EntityAITentacle(this, true, 7.0F, 4.0F, 0.1F, 60, 20, 1.7F));
         } else {
            this.tasks.addTask(1, new EntityAITentacle(this, true, 8.0F, 5.0F, 0.23F, 130, 40, 1.7F));
         }

         if (this.isAngryBiteTentacle) {
            this.grab = new EntityAIGrapBite(
               this, true, 30, 3.5F, 0.0F, 3.0F, 160, 60 + this.getMobDifficulty() >= 3 ? 35.0F : 0.0F, false, 1.5F, false, false, true
            );
         } else {
            this.grab = new EntityAIGrapBite(
               this, true, 40, 2.5F, 0.0F, 3.0F, 160, 30 + this.getMobDifficulty() >= 3 ? 15.0F : 0.0F, false, 1.5F, true, false, true
            );
         }

         this.tasks.addTask(2, this.grab);
         this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(2, new EntityAIAgree(this, EntityPlayer.class, false));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, false));
      }

      @SideOnly(Side.CLIENT)
      @Override
      public void renderPost(EntityLiving entity, double x, double y, double z, float entityYaw, float partialTicks) {
         Minecraft.getMinecraft().getTextureManager().bindTexture(textureTentacle);
         if (entity instanceof KrakenTentacle) {
            KrakenTentacle tentacle = (KrakenTentacle)entity;
            if (tentacle.kraken != null) {
               Vec3d pos1 = tentacle.kraken.getTentaclesAlign(partialTicks);
               Vec3d pos2 = GetMOP.entityCenterPos(tentacle, partialTicks);
               Vec3d normalizedRotation1 = GetMOP.PitchYawToVec3d(
                  -GetMOP.partial(tentacle.kraken.rotationPitch, tentacle.kraken.prevRotationPitch),
                  -GetMOP.partial(tentacle.kraken.rotationYaw, tentacle.kraken.prevRotationYaw)
               );
               Vec3d normalizedRotation2 = tentacle.getLook(partialTicks).scale(-1.0);
               Vec3d[] segmentsPoses = RenderTentacles.getTentacleSegmenstPositions(pos1, pos2, normalizedRotation1, normalizedRotation2, 40.0, 20);
               int lightStart = Math.max(
                     this.world.getLightFor(EnumSkyBlock.BLOCK, new BlockPos(pos1)),
                     this.world.getLightFor(EnumSkyBlock.SKY, new BlockPos(pos1))
                  )
                  * 15;
               int lightEnd = Math.max(
                     this.world.getLightFor(EnumSkyBlock.BLOCK, new BlockPos(pos2)),
                     this.world.getLightFor(EnumSkyBlock.SKY, new BlockPos(pos2))
                  )
                  * 15;
               RenderTentacles.renderTentacle(segmentsPoses, partialTicks, 1.0F, 0.4F, lightStart, lightEnd);
            }
         }
      }
   }

   public static class KrakenTentacleMain extends KrakenTentacle implements IRender {
      public static ResourceLocation textureTentacle = new ResourceLocation("arpg:textures/kraken_tentacle_main_segment.png");
      public int sounddelay = 0;

      public KrakenTentacleMain(World world) {
         super(world, 2.2F, 2.2F);
         this.hurtSound = Sounds.mob_fish_hurt;
         this.deathSound = Sounds.mob_fish_death;
         this.defaultteam = AquaticaMobsPack.mobsteam;
         this.setattributes(250.0, 64.0, 8.0, 0.08, 5.0, 5.0, 0.5, 0.0, 0.0, 0.0);
         this.registerLOOT(new MobDrop[]{new MobDrop(Item.getItemFromBlock(Blocks.OBSIDIAN), 0.55F, 0, 0, 2, 1)});
         this.setNoGravity(true);
         this.ignoreFrustumCheck = true;
      }

      @Override
      public void onUpdate() {
         super.onUpdate();
         if (this.kraken != null) {
            double distSq = this.getDistanceSq(this.kraken.tentaclesAlignX, this.kraken.tentaclesAlignY, this.kraken.tentaclesAlignZ);
            if (distSq > this.maxLengthSq) {
               SuperKnockback.applyMove(this, -0.2F, this.kraken.tentaclesAlignX, this.kraken.tentaclesAlignY, this.kraken.tentaclesAlignZ);
               this.noClip = true;
            } else {
               this.noClip = false;
            }
         }

         this.sounddelay--;
      }

      @Override
      public void shoot() {
         if (!this.world.isRemote) {
            if (this.sounddelay <= 0) {
               this.world.setEntityState(this, (byte)7);
            }

            this.sounddelay = 60;
            HostileProjectiles.KrakenSlime entityshoot = new HostileProjectiles.KrakenSlime(this.world, this);
            entityshoot.shoot(this, this.rotationPitch, this.rotationYaw, 0.0F, 1.0F, 2.0F);
            entityshoot.damage = (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
            this.world.spawnEntity(entityshoot);
         }
      }

      @SideOnly(Side.CLIENT)
      @Override
      public void handleStatusUpdate(byte id) {
         super.handleStatusUpdate(id);
         if (id == 7) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.kraken_main_shoot,
                  SoundCategory.HOSTILE,
                  3.5F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );
         }
      }

      protected void initEntityAI() {
         this.tasks.addTask(1, new EntityAITentacle(this, false, 15.0F, 6.0F, 0.2F, 0, 0, 0.0F).setShoot(80, 2, 18, false));
         this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(2, new EntityAIAgree(this, EntityPlayer.class, false));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, false));
      }

      @SideOnly(Side.CLIENT)
      @Override
      public void renderPost(EntityLiving entity, double x, double y, double z, float entityYaw, float partialTicks) {
         Minecraft.getMinecraft().getTextureManager().bindTexture(textureTentacle);
         if (entity instanceof KrakenTentacle) {
            KrakenTentacle tentacle = (KrakenTentacle)entity;
            if (tentacle.kraken != null) {
               Vec3d pos1 = tentacle.kraken.getTentaclesAlign(partialTicks);
               Vec3d pos2 = GetMOP.entityCenterPos(tentacle, partialTicks);
               Vec3d normalizedRotation1 = GetMOP.PitchYawToVec3d(
                  -GetMOP.partial(tentacle.kraken.rotationPitch, tentacle.kraken.prevRotationPitch),
                  -GetMOP.partial(tentacle.kraken.rotationYaw, tentacle.kraken.prevRotationYaw)
               );
               Vec3d normalizedRotation2 = tentacle.getLook(partialTicks).scale(-1.0);
               Vec3d[] segmentsPoses = RenderTentacles.getTentacleSegmenstPositions(pos1, pos2, normalizedRotation1, normalizedRotation2, 40.0, 20);
               int lightStart = Math.max(
                     this.world.getLightFor(EnumSkyBlock.BLOCK, new BlockPos(pos1)),
                     this.world.getLightFor(EnumSkyBlock.SKY, new BlockPos(pos1))
                  )
                  * 15;
               int lightEnd = Math.max(
                     this.world.getLightFor(EnumSkyBlock.BLOCK, new BlockPos(pos2)),
                     this.world.getLightFor(EnumSkyBlock.SKY, new BlockPos(pos2))
                  )
                  * 15;
               RenderTentacles.renderTentacle(segmentsPoses, partialTicks, 1.45F, 0.55F, lightStart, lightEnd);
            }
         }
      }
   }

   public static class KrakenTentacleShock extends KrakenTentacle implements IRender {
      public static ResourceLocation textureTentacle = new ResourceLocation("arpg:textures/kraken_tentacle_shock_segment.png");
      public static ResourceLocation textureShock = new ResourceLocation("arpg:textures/blueexplode4.png");
      public static ResourceLocation textureForcefield = new ResourceLocation("arpg:textures/forcefield3.png");
      public int charge = 40;
      public static int maxCharge = 80;
      public boolean attacking;
      public HostileProjectiles.KrakenShockbolt lastBolt = null;
      public static ModelSphere forcefieldModel = new ModelSphere(4.5F, 6);

      public KrakenTentacleShock(World world) {
         super(world, 1.6F, 1.6F);
         this.hurtSound = Sounds.mob_squish_hurt;
         this.deathSound = Sounds.mob_squish_death;
         this.defaultteam = AquaticaMobsPack.mobsteam;
         this.setattributes(140.0, 48.0, 8.0, 0.09, 2.0, 1.0, 0.0, 0.0, 0.0, 0.0);
         this.registerLOOT(new MobDrop[]{new MobDrop(Item.getItemFromBlock(Blocks.OBSIDIAN), 0.55F, 0, 0, 2, 1)});
         this.setNoGravity(true);
         this.noClip = true;
         this.ignoreFrustumCheck = true;
      }

      @Override
      public boolean attackEntityFrom(DamageSource source, float amount) {
         boolean succ = super.attackEntityFrom(source, amount);
         if (succ && this.attacking && amount >= 3.0F) {
            this.attacking = false;
            this.world.setEntityState(this, (byte)7);
            this.charge = 0;
            if (source.getTrueSource() != null) {
               SuperKnockback.applyKnockback(
                  this, 2.0F, source.getTrueSource().posX, source.getTrueSource().posY, source.getTrueSource().posZ
               );
            }
         }

         return succ;
      }

      @Override
      public void shoot() {
         if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)11);
            HostileProjectiles.KrakenShockbolt entityshoot = new HostileProjectiles.KrakenShockbolt(this.world, this);
            entityshoot.shoot(this, this.rotationPitch, this.rotationYaw, 0.0F, 0.72F, 1.3F);
            entityshoot.damage = (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue() * 1.6F + this.getMobDifficulty() * 0.2F;
            this.world.spawnEntity(entityshoot);
            this.lastBolt = entityshoot;
         }
      }

      @Override
      public void onUpdate() {
         super.onUpdate();
         if (this.kraken != null) {
            double distSq = this.getDistanceSq(this.kraken.tentaclesAlignX, this.kraken.tentaclesAlignY, this.kraken.tentaclesAlignZ);
            if (distSq > this.maxLengthSq) {
               SuperKnockback.applyMove(this, -0.2F, this.kraken.tentaclesAlignX, this.kraken.tentaclesAlignY, this.kraken.tentaclesAlignZ);
            }

            if (!this.world.isRemote) {
               if (this.getAttackTarget() != null) {
                  List<HostileProjectiles.KrakenShockbolt> listbolts = this.world
                     .getEntitiesWithinAABB(HostileProjectiles.KrakenShockbolt.class, this.getAttackTarget().getEntityBoundingBox().grow(3.3));
                  if (!listbolts.isEmpty()) {
                     if (this.lastBolt != null) {
                        this.lastBolt.startExplosion(this);
                        this.lastBolt = null;
                     } else {
                        listbolts.get(this.rand.nextInt(listbolts.size())).startExplosion(this);
                     }
                  }
               }

               if (this.ticksExisted % 10 == 0) {
                  if (this.charge < maxCharge && !this.attacking) {
                     this.charge += 10;
                     if (this.charge > 40) {
                        this.world.setEntityState(this, (byte)9);
                     }
                  } else {
                     List<EntityLivingBase> list = GetMOP.getHostilesInAABBto(this.world, this.getPositionVector(), 3.0, 3.0, this, this);
                     if (!list.isEmpty() && !this.attacking) {
                        this.attacking = true;
                        this.world.setEntityState(this, (byte)10);
                     }

                     if (this.attacking) {
                        for (EntityLivingBase entity : list) {
                           float damage = (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
                           Weapons.dealDamage(
                              new WeaponDamage(null, this.kraken, this, false, false, this, WeaponDamage.electric), damage, this, entity, true, 0.3F
                           );
                           entity.hurtResistantTime = 0;
                           DeathEffects.applyDeathEffect(entity, DeathEffects.DE_ELECTRIC, 0.8F);
                        }

                        this.charge -= 10;
                        if (this.charge <= 0) {
                           this.attacking = false;
                           this.world.setEntityState(this, (byte)7);
                        } else {
                           this.world.setEntityState(this, (byte)8);
                        }
                     }
                  }
               }
            }
         }

         if (this.world.isRemote) {
            if (this.attacking) {
               GUNParticle part = new GUNParticle(
                  textureShock,
                  3.2F,
                  0.0F,
                  3,
                  240,
                  this.world,
                  this.posX,
                  this.posY + this.height / 2.0F,
                  this.posZ,
                  0.0F,
                  0.0F,
                  0.0F,
                  0.82F - this.rand.nextFloat() * 0.1F,
                  1.0F,
                  0.85F + this.rand.nextFloat() * 0.1F,
                  true,
                  this.rand.nextInt(360)
               );
               part.alphaGlowing = true;
               if (this.rand.nextFloat() < 0.75F) {
                  part.rotationPitchYaw = new Vec2f(this.rand.nextInt(360), this.rand.nextInt(360));
               }

               this.world.spawnEntity(part);
            }

            if (this.ticksExisted % 12 == 0) {
               this.world
                  .playSound(
                     this.posX,
                     this.posY,
                     this.posZ,
                     Sounds.kraken_shock_loop,
                     SoundCategory.HOSTILE,
                     1.0F,
                     0.95F + this.rand.nextFloat() / 10.0F,
                     false
                  );
            }
         }
      }

      @SideOnly(Side.CLIENT)
      @Override
      public void handleStatusUpdate(byte id) {
         super.handleStatusUpdate(id);
         if (id == 7) {
            this.attacking = false;
            this.charge = 0;
         }

         if (id == 8) {
            this.attacking = true;
            this.charge = maxCharge;
         }

         if (id == 9) {
            this.charge = 50;
         }

         if (id == 11) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.kraken_shock_shoot,
                  SoundCategory.HOSTILE,
                  2.0F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );
         }

         if (id == 10) {
            this.attacking = true;
            this.charge = maxCharge;
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.kraken_shock_attack,
                  SoundCategory.HOSTILE,
                  1.5F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );
         }
      }

      protected void initEntityAI() {
         this.tasks.addTask(1, new EntityAITentacle(this, true, 12.0F, 6.0F, 0.0F, 270, 50, 1.0F).setShoot(150, 10, 8, false));
         this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(2, new EntityAIAgree(this, EntityPlayer.class, false));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, false));
      }

      @SideOnly(Side.CLIENT)
      @Override
      public void renderPost(EntityLiving entity, double x, double y, double z, float entityYaw, float partialTicks) {
         Minecraft.getMinecraft().getTextureManager().bindTexture(textureTentacle);
         if (entity instanceof KrakenTentacleShock) {
            KrakenTentacleShock tentacle = (KrakenTentacleShock)entity;
            if (tentacle.kraken != null) {
               Vec3d pos1 = tentacle.kraken.getTentaclesAlign(partialTicks);
               Vec3d pos2 = GetMOP.entityCenterPos(tentacle, partialTicks);
               Vec3d normalizedRotation1 = GetMOP.PitchYawToVec3d(
                  -GetMOP.partial(tentacle.kraken.rotationPitch, tentacle.kraken.prevRotationPitch),
                  -GetMOP.partial(tentacle.kraken.rotationYaw, tentacle.kraken.prevRotationYaw)
               );
               Vec3d normalizedRotation2 = tentacle.getLook(partialTicks).scale(-1.0);
               int segments = 20;
               Vec3d[] segmentsPoses = RenderTentacles.getTentacleSegmenstPositions(pos1, pos2, normalizedRotation1, normalizedRotation2, 40.0, segments);
               int lightStart = Math.max(
                     this.world.getLightFor(EnumSkyBlock.BLOCK, new BlockPos(pos1)),
                     this.world.getLightFor(EnumSkyBlock.SKY, new BlockPos(pos1))
                  )
                  * 15;
               int lightEnd = Math.max(
                     this.world.getLightFor(EnumSkyBlock.BLOCK, new BlockPos(pos2)),
                     this.world.getLightFor(EnumSkyBlock.SKY, new BlockPos(pos2))
                  )
                  * 15;
               RenderTentacles.renderTentacleAnimGlow(
                  segmentsPoses, partialTicks, 1.1F, 0.4F, lightStart, lightEnd, AnimationTimer.normaltick / 4 % segments, 0.3F, 1.0F, 1.0F, 210
               );
            }

            if (tentacle.charge > 40) {
               GlStateManager.pushMatrix();
               GlStateManager.enableBlend();
               AbstractMobModel.light(240, false);
               AbstractMobModel.alphaGlow();
               GlStateManager.disableFog();
               GlStateManager.translate(x, y, z);
               GL11.glDepthMask(false);
               Minecraft.getMinecraft().getTextureManager().bindTexture(textureForcefield);
               GlStateManager.translate(0.0F, 0.325F, 0.0F);
               forcefieldModel.renderScaledtextureAnimated(3.0F, AnimationTimer.normaltick / 8);
               GL11.glDepthMask(true);
               AbstractMobModel.returnlight();
               AbstractMobModel.alphaGlowDisable();
               GlStateManager.disableBlend();
               GlStateManager.enableFog();
               GlStateManager.popMatrix();
            }
         }
      }
   }

   public static class Mermaid extends AbstractMob implements IEntitySynchronize {
      public static ResourceLocation star2 = new ResourceLocation("arpg:textures/star2.png");
      public static ResourceLocation circle = new ResourceLocation("arpg:textures/circle.png");
      public static ResourceLocation circle2 = new ResourceLocation("arpg:textures/circle2.png");
      public boolean agred = false;
      public boolean unstoppableAgred = false;
      public int WEAPON = 0;
      public int maxSpellhitCoolown = 250;
      public int spellhitCoolown = 40;
      public int spellhitReady = 0;
      public int maxSpellhitReady = 30;
      public boolean spellhitActive = false;
      public Vec3d spellhitPos = null;
      public EntityAIShootAndSwim shootswim = null;
      public EntityAIRush rush = null;
      public EntityAIAttackSweep tridentsweep = null;
      public static Predicate<? super EntityLivingBase> targetEntitySelector = new Predicate<EntityLivingBase>() {
         public boolean apply(EntityLivingBase input) {
            if (input instanceof EntityPlayer) {
               boolean med = BaublesApi.getBaublesHandler((EntityPlayer)input).getStackInSlot(0).getItem() == ItemsRegister.MERMAIDMEDALLION;
               if (med) {
                  return false;
               }
            }

            return true;
         }
      };

      public Mermaid(World world) {
         super(world, 0.75F, 1.95F);
         this.hurtSound = Sounds.mob_fish_hurt;
         this.deathSound = Sounds.mob_fish_death;
         this.livingSound = SoundEvents.ENTITY_GUARDIAN_AMBIENT;
         this.defaultteam = AquaticaMobsPack.mobsteam;
         this.moveHelper = new WaterMoveHelper(this);
         this.setattributes(230.0, 64.0, 20.0, 0.27, 10.0, 8.0, 0.4, 0.2, 0.0, 0.0);
         this.registerLOOT(
            new MobDrop[]{
               new MobDrop(ItemsRegister.FISHSTEAKRAW, 0.5F, 0, 1, 1, 3),
               new MobDrop(ItemsRegister.NUGGETAQUATIC, 0.5F, 0, 1, 3, 0),
               new MobDrop(ItemsRegister.TIDEACTIVATOR1, 0.1F, 0, 1, 1, 0),
               new MobDrop(ItemsRegister.PEARL, 0.3F, 0, 0, 3, 0)
            }
         );
         this.var3 = 0.0F;
         this.var4 = 0.0F;
         this.var5 = 0.0F;
         this.setRoleValues(EnumMobRole.STRONG_ENEMY, 5);
         this.soul = Soul.DEEPWATER;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.7) {
            ShardType.spawnShards(ShardType.WATER, this, 4.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.8) {
            ShardType.spawnShards(ShardType.LIVE, this, 4.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.75) {
            ShardType.spawnShards(ShardType.FIRE, this, 3.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.6) {
            ShardType.spawnShards(ShardType.PLEASURE, this, 1.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.FISH_BLOOD;
      }

      protected float getSoundPitch() {
         return 0.8F;
      }

      @Override
      public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
         IEntityLivingData ois = super.onInitialSpawn(difficulty, livingdata);
         this.WEAPON = this.rand.nextInt(3);
         if (this.WEAPON == 0) {
            this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0);
         }

         if (this.WEAPON == 2) {
            this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(48.0);
         }

         return ois;
      }

      @Override
      public void writeEntityToNBT(NBTTagCompound compound) {
         compound.setInteger("weapon", this.WEAPON);
         super.writeEntityToNBT(compound);
      }

      @Override
      public void readEntityFromNBT(NBTTagCompound compound) {
         if (compound.hasKey("weapon")) {
            this.WEAPON = compound.getInteger("weapon");
            this.var1 = this.WEAPON;
         }

         super.readEntityFromNBT(compound);
      }

      @Override
      public void shoot() {
         if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)7);
            if (this.WEAPON == 0) {
               HostileProjectiles.MermaidShoot entityshoot = new HostileProjectiles.MermaidShoot(this.world, this);
               entityshoot.shoot(this, this.rotationPitch, this.rotationYaw, 0.0F, 0.7F, 1.1F);
               entityshoot.damage = 16.0F;
               if (this.getAttackTarget() != null) {
                  entityshoot.explodeTime = (int)Math.round(this.getDistance(this.getAttackTarget()) * 1.7);
               }

               this.world.spawnEntity(entityshoot);
            }

            if (this.WEAPON == 2) {
               HostileProjectiles.ArrowMermaid entityshoot = new HostileProjectiles.ArrowMermaid(this.world, this);
               float up = 3.0F;
               if (this.getAttackTarget() != null) {
                  up = this.getDistance(this.getAttackTarget()) * 0.3F;
               }

               entityshoot.shoot(this, this.rotationPitch - up, this.rotationYaw, 0.0F, 1.7F, 0.7F);
               entityshoot.setPosition(this.posX, this.posY + this.height / 2.0F, this.posZ);
               entityshoot.setDamage(8.0);
               this.world.spawnEntity(entityshoot);
            }
         }
      }

      @Override
      public void onClient(double... args) {
         if (args.length == 3) {
            this.spellhitPos = new Vec3d(args[0], args[1], args[2]);
            this.spellhitActive = true;
            this.triggerAnimation(-3);
            this.world
               .playSound(
                  this.spellhitPos.x,
                  this.spellhitPos.y,
                  this.spellhitPos.z,
                  Sounds.magic_m,
                  SoundCategory.HOSTILE,
                  1.8F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );
            GUNParticle part = new GUNParticle(
               circle2,
               3.2F,
               0.0F,
               this.maxSpellhitReady,
               220,
               this.world,
               this.spellhitPos.x,
               this.spellhitPos.y,
               this.spellhitPos.z,
               0.0F,
               0.0F,
               0.0F,
               1.0F,
               1.0F,
               0.55F - this.rand.nextFloat() * 0.2F,
               true,
               1
            );
            part.alphaGlowing = true;
            part.alphaTickAdding = -0.8F / this.maxSpellhitReady;
            part.rotationPitchYaw = new Vec2f(90.0F, this.rand.nextInt(360));
            this.world.spawnEntity(part);
         }
      }

      @Override
      public void onUpdate() {
         int airr = this.getAir();
         super.onUpdate();
         if (this.world.isRemote) {
            this.var3 = this.var4;
            this.var4 = this.var4 + this.var5;
            if (this.spellhitActive && this.spellhitPos != null) {
               GUNParticle spell = new GUNParticle(
                  star2,
                  0.1F + this.rand.nextFloat() * 0.1F,
                  0.0F,
                  20,
                  200,
                  this.world,
                  this.spellhitPos.x + (this.rand.nextDouble() - 0.5) * 6.0,
                  this.spellhitPos.y,
                  this.spellhitPos.z + (this.rand.nextDouble() - 0.5) * 6.0,
                  0.0F,
                  (float)this.rand.nextGaussian() / 4.0F,
                  0.0F,
                  1.0F,
                  1.0F,
                  0.4F - this.rand.nextFloat() * 0.2F,
                  true,
                  this.rand.nextInt(31) - 30
               );
               spell.alphaGlowing = true;
               spell.scaleTickAdding = -0.0016F;
               this.world.spawnEntity(spell);
            }
         } else {
            if (!this.isAIDisabled() && this.isEntityAlive()) {
               if (this.WEAPON == 0) {
                  if (this.spellhitCoolown > 0) {
                     this.spellhitCoolown--;
                  }

                  if (this.shootswim != null) {
                     if (this.shootswim.shouldExecute()) {
                        this.shootswim.attackCooldown = 53;
                        this.shootswim.updateTask();
                     }
                  } else {
                     this.shootswim = new EntityAIShootAndSwim(this, 65, 32.0F, 15, 20, 65);
                  }

                  if (this.ticksExisted % 9 == 0 && this.getAttackTarget() != null && this.spellhitCoolown <= 0) {
                     List<EntityLivingBase> around = GetMOP.getHostilesInAABBto(this.world, GetMOP.entityCenterPos(this), 8.0, 5.0, this, this);
                     int maxcount = 0;
                     Vec3d maxpos = null;
                     if (!around.isEmpty()) {
                        for (int i = 0; i < 16; i++) {
                           Vec3d pos = around.get(this.rand.nextInt(around.size()))
                              .getPositionVector()
                              .add(
                                 (this.rand.nextDouble() - 0.5) * 6.0,
                                 (this.rand.nextDouble() - 0.5) * 3.0,
                                 (this.rand.nextDouble() - 0.5) * 6.0
                              );
                           List<EntityLivingBase> hostiles = GetMOP.getHostilesInAABBto(this.world, pos, 3.2, 1.8, this, this);
                           if (maxcount < hostiles.size()) {
                              maxcount = hostiles.size();
                              maxpos = pos;
                           }
                        }
                     }

                     if (maxpos != null) {
                        this.spellhitPos = maxpos;
                        this.spellhitActive = true;
                        this.spellhitCoolown = this.maxSpellhitCoolown;
                        this.spellhitReady = 0;
                        IEntitySynchronize.sendSynchronize(
                           this, 64.0, this.spellhitPos.x, this.spellhitPos.y, this.spellhitPos.z
                        );
                     }
                  }

                  if (this.spellhitActive) {
                     if (this.spellhitReady < this.maxSpellhitReady) {
                        this.spellhitReady++;
                     } else {
                        for (EntityLivingBase entity : GetMOP.getHostilesInAABBto(this.world, this.spellhitPos, 3.2, 1.8, this, this)) {
                           Weapons.dealDamage(
                              new WeaponDamage(null, this, null, false, false, this.spellhitPos, WeaponDamage.acid),
                              25.0F,
                              this,
                              entity,
                              true,
                              1.3F,
                              this.spellhitPos.x,
                              this.spellhitPos.y,
                              this.spellhitPos.z
                           );
                           entity.hurtResistantTime = 0;
                           entity.addPotionEffect(new PotionEffect(PotionEffects.ICHOR_CURSE, 500));
                        }

                        this.world.setEntityState(this, (byte)11);
                        this.spellhitActive = false;
                        this.spellhitPos = null;
                     }
                  }
               }

               if (this.WEAPON == 1) {
                  if (this.tridentsweep != null) {
                     if (this.tridentsweep.shouldExecute()) {
                        this.tridentsweep.updateTask();
                     }
                  } else {
                     this.tridentsweep = new EntityAIAttackSweep(this, 50, 0.6F, 1.0F, 4.0F, 24).setTriggerOnStart();
                     this.tridentsweep.animTriggered = -3;
                  }

                  if (this.rush != null) {
                     if (this.rush.shouldExecute()) {
                        this.rush.updateTask();
                     }
                  } else {
                     this.rush = new EntityAIRush(this, true, false, false);
                  }
               }

               if (this.WEAPON == 2) {
                  if (this.shootswim != null) {
                     if (this.shootswim.shouldExecute()) {
                        this.shootswim.attackCooldown = 65;
                        this.shootswim.updateTask();
                     }
                  } else {
                     this.shootswim = new EntityAIShootAndSwim(this, 65, 32.0F, 15, 20, 65);
                  }
               }
            }

            if (this.ticksExisted <= 2 || this.ticksExisted % 40 == 0) {
               this.world.setEntityState(this, (byte)(this.WEAPON + 64));
            }
         }

         if (this.isEntityAlive() && !this.isInWater()) {
            this.setAir(--airr);
            if (this.getAir() == -20) {
               this.setAir(0);
               this.attackEntityFrom(DamageSource.DROWN, 2.0F);
            }
         } else {
            this.setAir(300);
         }
      }

      @SideOnly(Side.CLIENT)
      @Override
      public void handleStatusUpdate(byte id) {
         super.handleStatusUpdate(id);
         if (id == 8) {
            this.var5 = Math.max(this.var5 - 0.04F, 0.0F);
         }

         if (id == 9) {
            this.var5 = Math.min(this.var5 + 0.05F, 2.0F);
         }

         if (id == 7) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.slimeshoot,
                  SoundCategory.HOSTILE,
                  1.5F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );
         }

         if (id == 15) {
            this.triggerAnimation(-3);
         }

         if (id == 11 && this.spellhitActive && this.spellhitPos != null) {
            this.world
               .playSound(
                  this.spellhitPos.x,
                  this.spellhitPos.y,
                  this.spellhitPos.z,
                  Sounds.explode4,
                  SoundCategory.HOSTILE,
                  1.8F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );

            for (int ss = 0; ss < 20; ss++) {
               GUNParticle spell = new GUNParticle(
                  star2,
                  0.1F + this.rand.nextFloat() * 0.1F,
                  0.0F,
                  40,
                  240,
                  this.world,
                  this.spellhitPos.x + (this.rand.nextDouble() - 0.5) * 6.0,
                  this.spellhitPos.y,
                  this.spellhitPos.z + (this.rand.nextDouble() - 0.5) * 6.0,
                  0.0F,
                  (float)this.rand.nextGaussian() / 8.0F,
                  0.0F,
                  1.0F,
                  1.0F,
                  0.4F - this.rand.nextFloat() * 0.2F,
                  true,
                  this.rand.nextInt(31) - 30
               );
               spell.alphaGlowing = true;
               spell.scaleTickAdding = -0.0016F;
               this.world.spawnEntity(spell);
            }

            for (int ss = -3; ss <= 3; ss++) {
               float fsize = 5.8F + this.rand.nextFloat() * 0.4F - Math.abs(ss);
               int lt = 8 + this.rand.nextInt(4);
               GUNParticle part = new GUNParticle(
                  circle,
                  0.2F,
                  0.0F,
                  lt,
                  220,
                  this.world,
                  this.spellhitPos.x,
                  this.spellhitPos.y,
                  this.spellhitPos.z,
                  0.0F,
                  ss * 0.1F,
                  0.0F,
                  1.0F,
                  1.0F,
                  0.4F - this.rand.nextFloat() * 0.2F,
                  true,
                  1
               );
               part.scaleTickAdding = fsize / lt;
               part.alphaGlowing = true;
               part.alphaTickAdding = -0.4F / lt;
               part.randomDeath = false;
               part.rotationPitchYaw = new Vec2f(88 + this.rand.nextInt(5), this.rand.nextInt(360));
               this.world.spawnEntity(part);
            }

            this.spellhitPos = null;
            this.spellhitActive = false;
         }

         if (id >= 64 && id <= 67) {
            this.WEAPON = id - 64;
            this.var1 = this.WEAPON;
         }
      }

      public boolean canBreatheUnderwater() {
         return true;
      }

      protected PathNavigate createNavigator(World worldIn) {
         return new PathNavigateSwimmer(this, worldIn);
      }

      public void travel(float strafe, float vertical, float forward) {
         if (this.isServerWorld() && this.isInWater()) {
            this.moveRelative(strafe, vertical, forward, 0.1F);
            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.9F;
            this.motionY *= 0.9F;
            this.motionZ *= 0.9F;
         } else {
            super.travel(strafe, vertical, forward);
         }
      }

      protected void initEntityAI() {
         this.tasks.addTask(3, new EntityAIAttackSweep(this, 30, 1.0F, 2.0F, 2.5F, 10).setTriggerOnStart());
         this.tasks.addTask(7, new EntityAIWander(this, 1.0, 80));
         this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
         if (this instanceof DarkMermaid) {
            this.targetTasks.addTask(2, new EntityAIAgree(this, EntityPlayer.class, true));
         } else {
            this.targetTasks.addTask(2, new EntityAIAgree(this, EntityPlayer.class, 10, true, false, targetEntitySelector, 0.25));
         }

         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }
   }

   public static class Needletooth extends AbstractMob implements IEntitySynchronize {
      public static ResourceLocation tex1 = new ResourceLocation("arpg:textures/needletooth_model_tex1.png");
      public static ResourceLocation tex2 = new ResourceLocation("arpg:textures/needletooth_model_tex2.png");
      public static ResourceLocation tex3 = new ResourceLocation("arpg:textures/needletooth_model_tex3.png");
      public static ResourceLocation tex4 = new ResourceLocation("arpg:textures/needletooth_model_tex4.png");
      public static ResourceLocation tex5 = new ResourceLocation("arpg:textures/needletooth_model_tex5.png");

      public Needletooth(World world) {
         super(world, 0.65F, 0.65F);
         this.hurtSound = Sounds.mob_fish_hurt;
         this.deathSound = Sounds.mob_fish_death;
         this.defaultteam = AquaticaMobsPack.mobsteam;
         this.moveHelper = new WaterMoveHelper(this);
         this.setattributes(80.0, 48.0, 12.0, 0.32, 8.0, 4.0, 0.0, 0.0, 0.0, 0.0);
         this.registerLOOT(
            new MobDrop[]{
               new MobDrop(ItemsRegister.FISHSTEAKRAW, 0.5F, 0, 1, 1, 1), new MobDrop(ItemsRegister.PLACODERMSCALES, 0.2F, 0, 1, 1, 0)
            }
         );
         this.var3 = 0.0F;
         this.var4 = 0.0F;
         this.var5 = 0.0F;
         this.setRoleValues(EnumMobRole.SOLDIER, 5);
         this.soul = Soul.COMMON;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.3) {
            ShardType.spawnShards(ShardType.WATER, this, 1.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.FISH_BLOOD;
      }

      public float getEyeHeight() {
         return this.height * 0.6F;
      }

      @Override
      public void onUpdate() {
         int airr = this.getAir();
         super.onUpdate();
         if (this.ticksExisted <= 2 || this.ticksExisted % 41 == 0) {
            IEntitySynchronize.sendSynchronize(this, 64.0, this.var2, 0.0, 0.0, 0.0, 0.0, 0.0);
         }

         if (this.world.isRemote) {
            this.var3 = this.var4;
            this.var4 = this.var4 + this.var5;
         }

         if (this.isEntityAlive() && !this.isInWater()) {
            this.setAir(--airr);
            if (this.getAir() == -20) {
               this.setAir(0);
               this.attackEntityFrom(DamageSource.DROWN, 2.0F);
            }
         } else {
            this.setAir(300);
         }
      }

      @Override
      public void onClient(double x, double y, double z, double a, double b, double c) {
         this.var2 = (float)x;
         this.setSize(this.var2, this.var2);
      }

      @SideOnly(Side.CLIENT)
      @Override
      public void handleStatusUpdate(byte id) {
         super.handleStatusUpdate(id);
         if (id == 8) {
            this.var5 = Math.max(this.var5 - 0.04F, 0.0F);
         }

         if (id == 9) {
            this.var5 = Math.min(this.var5 + 0.05F, 2.0F);
         }
      }

      public boolean canBreatheUnderwater() {
         return true;
      }

      protected PathNavigate createNavigator(World worldIn) {
         return new PathNavigateSwimmer(this, worldIn);
      }

      public void travel(float strafe, float vertical, float forward) {
         if (this.isServerWorld() && this.isInWater()) {
            this.moveRelative(strafe, vertical, forward, 0.1F);
            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.9F;
            this.motionY *= 0.9F;
            this.motionZ *= 0.9F;
         } else {
            super.travel(strafe, vertical, forward);
         }
      }

      @Override
      public void writeEntityToNBT(NBTTagCompound compound) {
         compound.setFloat("size", this.var2);
         super.writeEntityToNBT(compound);
      }

      @Override
      public void readEntityFromNBT(NBTTagCompound compound) {
         if (compound.hasKey("size")) {
            this.var2 = compound.getFloat("size");
         }

         super.readEntityFromNBT(compound);
      }

      protected void initEntityAI() {
         this.tasks.addTask(1, new EntityAIRush(this, true, true, true));
         this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0));
         this.tasks.addTask(7, new EntityAIWander(this, 1.0, 80));
         this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
         this.tasks.addTask(8, new EntityAILookIdle(this));
         this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
         this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }

      @Override
      public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
         if (!this.world.isRemote) {
            if (this.rand.nextFloat() < 0.9) {
               this.var2 = 0.85F + (this.rand.nextFloat() * 0.4F - 0.2F);
            } else {
               this.var2 = 0.85F + (this.rand.nextFloat() * 0.8F - 0.4F);
            }

            this.setSize(this.var2, this.var2);
            long l = this.getUniqueID().getLeastSignificantBits() ^ this.getUniqueID().getMostSignificantBits();
            long lpr = l % 20L;
            int type = 0;
            byte var8;
            if (lpr <= 6L) {
               var8 = 1;
            } else if (lpr <= 11L) {
               var8 = 2;
            } else if (lpr <= 14L) {
               var8 = 3;
            } else if (lpr <= 16L) {
               var8 = 4;
            } else {
               var8 = 5;
            }

            this.maxHealth = this.maxHealth + this.rand.nextInt((int)(this.maxHealth * 0.428)) * this.var2;
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.maxHealth);
            this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed + this.rand.nextFloat() * 0.1F);
            if (var8 == 3) {
               this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(this.damage + 1.0 + 3.0F * this.rand.nextFloat());
            } else {
               this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(this.damage + 2.0F * this.rand.nextFloat());
            }

            if (var8 == 4) {
               this.getEntityAttribute(PropertiesRegistry.ARMOR_PROTECTION).setBaseValue(this.protection + 1.5);
            }

            if (var8 == 5) {
               this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE)
                  .setBaseValue(Math.min(this.knockResist + 0.4F * this.rand.nextFloat(), 1.0));
            }
         }

         return super.onInitialSpawn(difficulty, livingdata);
      }

      @SideOnly(Side.CLIENT)
      @Nullable
      @Override
      public ResourceLocation getMultitexture() {
         long l = this.getUniqueID().getLeastSignificantBits() ^ this.getUniqueID().getMostSignificantBits();
         long lpr = l % 20L;
         if (lpr <= 6L) {
            return tex1;
         } else if (lpr <= 11L) {
            return tex2;
         } else if (lpr <= 14L) {
            return tex3;
         } else {
            return lpr <= 16L ? tex4 : tex5;
         }
      }
   }

   public static class OceanSpirit extends AbstractMob implements IEntitySynchronize {
      ResourceLocation texturelaser = new ResourceLocation("arpg:textures/water_beam.png");
      public int oceanShootDelay = 20;
      public int oceanShootDelayMax = 150;
      public int lazerDelay = 20;
      public int lazerDelayMax = 125;
      public int waterDelay = 50;
      public int waterDelayMax = 250;
      public int bombs = 2;
      public int lookBombTimer = 0;
      public int lazerTimer = 0;
      public EntityAIFloatingSkeletonSwim aiswim;
      public EntityAISkeleton aiground;

      public OceanSpirit(World world) {
         super(world, 0.95F, 2.4F);
         this.hurtSound = Sounds.mob_fish_hurt;
         this.deathSound = Sounds.mob_ghost_death;
         this.livingSound = SoundEvents.ENTITY_GUARDIAN_AMBIENT;
         this.defaultteam = AquaticaMobsPack.mobsteam;
         this.setattributes(375.0, 48.0, 9.0, 0.08, 3.0, 6.0, 0.4, 0.0, 0.0, 0.0);
         this.registerLOOT(new MobDrop[]{new MobDrop(ItemsRegister.PEARLAQUATIC, 0.25F, 0, 1, 1, 0)});
         this.var3 = 0.0F;
         this.var4 = 0.0F;
         this.var5 = 0.0F;
         this.stepHeight = 1.0F;
         this.setRoleValues(EnumMobRole.ELITE_ENEMY, 5);
         this.soul = Soul.DEEPWATER;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.9) {
            ShardType.spawnShards(ShardType.WATER, this, 4.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.8) {
            ShardType.spawnShards(ShardType.VOID, this, 2.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.OCEAN_BLOOD;
      }

      public float getEyeHeight() {
         return this.height * 0.93F;
      }

      @Override
      public void shoot() {
         if (!this.world.isRemote) {
            EntityLivingBase at = this.getAttackTarget();
            int prioriryOceanSh = 0;
            int prioriryLazer = 0;
            if (at != null) {
               for (Entity e : GetMOP.getEntitiesInAABBof(this.world, at, 4.5, null)) {
                  if (Team.checkIsOpponent(this, e)) {
                     if (++prioriryOceanSh > 4) {
                        break;
                     }
                  }
               }

               if (at instanceof EntityPlayer) {
                  prioriryLazer = (int)(prioriryLazer + Mana.getMana((EntityPlayer)at) / 10.0F);
               }

               if (GetMOP.getSpeed(at) > 0.2) {
                  prioriryLazer += 2;
               }

               if (at.getDistanceSq(this) > 324.0) {
                  prioriryLazer -= 3;
               }
            }

            if (prioriryOceanSh > prioriryLazer) {
               if (!this.oceanShoot() && !this.lazerShoot() && !this.waterShoot()) {
                  this.bombShoot();
               }
            } else if (!this.lazerShoot() && !this.oceanShoot() && !this.waterShoot()) {
               this.bombShoot();
            }
         }
      }

      public boolean oceanShoot() {
         if (this.oceanShootDelay <= 0) {
            this.world.setEntityState(this, (byte)7);
            HostileProjectiles.OceanSpiritShoot entityshoot = new HostileProjectiles.OceanSpiritShoot(this.world, this);
            entityshoot.shoot(this, this.rotationPitch, this.rotationYaw, 0.0F, 0.9F, 1.1F);
            entityshoot.damage = 17.0F;
            this.world.spawnEntity(entityshoot);
            this.oceanShootDelay = this.oceanShootDelayMax;
            return true;
         } else {
            return false;
         }
      }

      public boolean lazerShoot() {
         if (this.lazerDelay <= 0) {
            this.world.setEntityState(this, (byte)8);
            this.lazerTimer = 20;
            this.lazerDelay = this.lazerDelayMax;
            return true;
         } else {
            return false;
         }
      }

      public boolean bombShoot() {
         if (this.bombs <= 0) {
            return false;
         } else {
            this.world.setEntityState(this, (byte)10);
            int bombcost = this.rand.nextInt(Math.min(this.bombs, 4)) + 1;

            for (int i = 0; i < bombcost; i++) {
               HostileProjectiles.SeaBomb entityshoot = new HostileProjectiles.SeaBomb(this.world, this);
               entityshoot.shoot(this, this.rotationPitch - 13.0F, this.rotationYaw, 0.0F, 0.7F, 9.5F);
               entityshoot.damage = 30.0F;
               entityshoot.explodeTimeOffset = this.rand.nextInt(8) - 50;
               entityshoot.dropBlocks = false;
               this.world.spawnEntity(entityshoot);
            }

            this.bombs -= bombcost;
            return true;
         }
      }

      public boolean waterShoot() {
         if (this.waterDelay > 0) {
            return false;
         } else {
            this.world.setEntityState(this, (byte)9);
            EntityLivingBase target = null;
            if (this.isBurning()) {
               target = this;
            } else {
               for (Entity entit : GetMOP.getEntitiesInAABBof(this.world, this, 10.0, this)) {
                  if (entit instanceof EntityLivingBase) {
                     EntityLivingBase base = (EntityLivingBase)entit;
                     if (Team.isOnSameTeam(this, base) && (base.canBreatheUnderwater() && !base.isInWater() || base.isBurning())) {
                        target = base;
                        break;
                     }
                  }
               }

               if (target == null) {
                  target = this.getAttackTarget();
               }

               if (target == null || target.isInWater()) {
                  target = this;
               }

               if (target.isInWater()) {
                  return false;
               }
            }

            for (BlockPos pos : GetMOP.getPosesInsideSphere((int)target.posX, (int)target.posY, (int)target.posZ, 5)) {
               if (this.world.isAirBlock(pos) && this.world.isSideSolid(pos.down(), EnumFacing.UP)) {
                  this.world.setBlockState(pos, Blocks.FLOWING_WATER.getStateFromMeta(8));
               }
            }

            this.waterDelay = this.waterDelayMax;
            return true;
         }
      }

      @SideOnly(Side.CLIENT)
      public void spawnBetwParticle(Vec3d from, Vec3d to) {
         if (from.lengthSquared() > 1.0E-6 && to.lengthSquared() > 1.0E-6) {
            BetweenParticle laser = new BetweenParticle(
               this.world, this.texturelaser, 0.2F, 220, 0.5F, 1.0F, 0.9F, 0.15F, from.distanceTo(to), 3, 0.5F, 4.0F, from, to
            );
            laser.ignoreFrustumCheck = true;
            laser.setPosition(from.x, from.y, from.z);
            laser.alphaGlowing = true;
            this.world.spawnEntity(laser);

            for (int ss = 0; ss < 3; ss++) {
               int lt = 20 + this.rand.nextInt(10);
               float scl = 0.02F + this.rand.nextFloat() * 0.034F;
               GUNParticle water = new GUNParticle(
                  Seal.splashes[this.rand.nextInt(3)],
                  scl,
                  0.023F,
                  lt,
                  150,
                  this.world,
                  to.x,
                  to.y,
                  to.z,
                  (float)this.rand.nextGaussian() / 25.0F,
                  (float)this.rand.nextGaussian() / 25.0F,
                  (float)this.rand.nextGaussian() / 25.0F,
                  0.65F - this.rand.nextFloat() * 0.2F,
                  1.0F - this.rand.nextFloat() * 0.22F,
                  1.0F - this.rand.nextFloat() * 0.2F,
                  false,
                  this.rand.nextInt(360),
                  true,
                  1.15F
               );
               water.scaleTickAdding = -scl / lt * 0.7F;
               this.world.spawnEntity(water);
            }
         }
      }

      @Override
      public void onUpdate() {
         super.onUpdate();
         if (!this.world.isRemote) {
            if ((this.getAttackTarget() == null || this.getEntitySenses().canSee(this.getAttackTarget())) && this.lookBombTimer < 40) {
               if (this.lookBombTimer > 0) {
                  this.lookBombTimer--;
               }
            } else {
               if (this.getAttackTarget() != null) {
                  this.getLookHelper().setLookPositionWithEntity(this.getAttackTarget(), 80.0F, 80.0F);
               }

               this.lookBombTimer++;
               if (this.lookBombTimer == 40) {
                  this.triggerAnimation(-3);
               }

               if (this.lookBombTimer > 50) {
                  this.bombShoot();
                  this.lookBombTimer = 0;
               }
            }
         }

         if (this.aiswim != null && this.aiground != null) {
            if (this.isInWater()) {
               this.aiswim.enable = true;
               this.aiground.enable = false;
            } else {
               this.aiground.enable = true;
               this.aiswim.enable = false;
            }
         }

         this.lazerDelay--;
         this.oceanShootDelay--;
         this.waterDelay--;
         if (this.bombs < 8 && this.ticksExisted % 40 == 0) {
            this.bombs++;
         }

         if (this.lazerTimer > 0) {
            this.lazerTimer--;
            Vec3d vec = GetMOP.PosRayTrace(18.0, 1.0F, this, 0.3, 0.2);
            if (this.world.isRemote) {
               this.spawnBetwParticle(this.getPositionEyes(1.0F), vec);
            } else {
               for (Entity entity : GetMOP.getEntitiesInAABBof(this.world, vec, 1.0, this)) {
                  if (Team.checkIsOpponent(this, entity)) {
                     Weapons.dealDamage(new WeaponDamage(null, this, null, false, false, vec, WeaponDamage.water), 10.0F, this, entity, false);
                     if (entity instanceof EntityPlayer) {
                        Mana.changeMana((EntityPlayer)entity, -0.5F);
                     }
                  }
               }
            }
         }
      }

      @SideOnly(Side.CLIENT)
      @Override
      public void handleStatusUpdate(byte id) {
         super.handleStatusUpdate(id);
         if (id == 8) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.magic_water,
                  SoundCategory.HOSTILE,
                  1.2F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );
            this.lazerTimer = 20;
         }

         if (id == 7) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.ocean_shoot,
                  SoundCategory.HOSTILE,
                  1.0F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );
         }

         if (id == 9) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.explode_water_a,
                  SoundCategory.HOSTILE,
                  1.6F,
                  0.7F + this.rand.nextFloat() / 5.0F,
                  false
               );
         }

         if (id == 10) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.sea_bomb,
                  SoundCategory.HOSTILE,
                  1.6F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );
         }

         if (id == 15) {
            this.triggerAnimation(-1);
         }
      }

      public boolean canBreatheUnderwater() {
         return true;
      }

      public void travel(float strafe, float vertical, float forward) {
         if (this.isServerWorld() && this.isInWater()) {
            this.moveRelative(strafe, vertical, forward, 0.1F);
            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.9F;
            this.motionY *= 0.9F;
            this.motionZ *= 0.9F;
         } else {
            super.travel(strafe, vertical, forward);
         }
      }

      protected void initEntityAI() {
         this.aiswim = new EntityAIFloatingSkeletonSwim(this, 70, 32.0F, 11, true, 10.0F, 3.0F, 2.0F, 0.14F, true, true);
         this.aiswim.deltaRotation = 80;
         this.aiground = new EntityAISkeleton(this, 1.0, 70, 32.0F, 1);
         this.aiground.useshoot = false;
         this.aiswim.useshoot = false;
         this.tasks.addTask(2, new EntityAIShootDelayed(this, 50, 32.0F, 11, true));
         this.tasks.addTask(2, this.aiswim);
         this.tasks.addTask(2, this.aiground);
         this.tasks.addTask(4, new EntityAIAttackSweep(this, 50, 0.75F, 1.0F, 2.5F, 10).setTriggerOnStart());
         this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0));
         this.tasks.addTask(7, new EntityAIWander(this, 1.0, 80));
         this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
         this.tasks.addTask(8, new EntityAILookIdle(this));
         this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
         this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }
   }

   public static class Polipoid extends AbstractMob implements IEntitySynchronize {
      public static float motionMaxSpeed = 1.0F;
      public static float speedIncrease = 0.05F;
      public int shootCooldown = 0;
      public boolean move = false;
      public float attackCount = 0.0F;
      public int breeds = 0;

      public Polipoid(World world) {
         super(world, 0.99F, 0.99F);
         this.hurtSound = Sounds.mob_squish_hurt;
         this.deathSound = Sounds.mob_squish_death;
         this.defaultteam = AquaticaMobsPack.mobsteam;
         this.moveHelper = new WaterMoveHelper(this);
         this.setattributes(150.0, 32.0, 14.0, 0.17, 1.0, 1.0, 0.0, 0.0, 0.2, 0.0);
         this.registerLOOT(new MobDrop[]{new MobDrop(ItemsRegister.MESOGLEA, 0.25F, 0, 1, 1, 0)});
         if (world.isRemote) {
            this.var3 = 0.0F;
            this.var4 = 0.0F;
         }

         this.setRoleValues(EnumMobRole.MIDDLE_ENEMY, 5);
         this.soul = Soul.COLLECTIVE;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.7) {
            ShardType.spawnShards(ShardType.WATER, this, 2.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.9) {
            ShardType.spawnShards(ShardType.LIVE, this, 3.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.POLYP_BLOOD;
      }

      @Override
      public void onClient(double... args) {
         this.var1 = (int)args[0] + 4;
      }

      @Override
      public boolean attackEntityFrom(DamageSource source, float amount) {
         if (!this.world.isRemote && this.breeds > 0) {
            this.attackCount += amount;
            if (this.rand.nextFloat() < 0.3 || this.attackCount > this.getMaxHealth() / 5.0F) {
               this.breeds--;
               this.attackCount = 0.0F;
               Breed mob = new Breed(this.world);
               mob.setPosition(this.posX, this.posY, this.posZ);
               mob.motionX = (this.rand.nextFloat() - 0.5F) / 13.0F;
               mob.motionY = (this.rand.nextFloat() - 0.5F) / 13.0F;
               mob.motionZ = (this.rand.nextFloat() - 0.5F) / 13.0F;
               this.world.spawnEntity(mob);
               mob.onInitialSpawn();
               mob.team = this.team;
               mob.isAgressive = this.isAgressive;
               mob.canDropLoot = this.canDropLoot;
               IEntitySynchronize.sendSynchronize(this, 64.0, this.breeds);
            }
         }

         return super.attackEntityFrom(source, amount);
      }

      @Override
      public void writeEntityToNBT(NBTTagCompound compound) {
         compound.setInteger("breeds", this.breeds);
         super.writeEntityToNBT(compound);
      }

      @Override
      public void readEntityFromNBT(NBTTagCompound compound) {
         if (compound.hasKey("breeds")) {
            this.breeds = compound.getInteger("breeds");
         }

         super.readEntityFromNBT(compound);
      }

      public float getEyeHeight() {
         return this.height * 0.5F;
      }

      @Override
      public void onUpdate() {
         int airr = this.getAir();
         super.onUpdate();
         if (this.world.isRemote) {
            this.var3 = this.var4;
            this.var4 = this.var4
               + (float)this.getDistance(
                  this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ
               );
         } else if (this.ticksExisted < 3 || this.ticksExisted % 43 == 0) {
            IEntitySynchronize.sendSynchronize(this, 64.0, this.breeds, 0.0, 0.0, 0.0, 0.0, 0.0);
         }

         if (this.isEntityAlive() && !this.isInWater()) {
            this.setAir(--airr);
            if (this.getAir() == -20) {
               this.setAir(0);
               this.attackEntityFrom(DamageSource.DROWN, 2.0F);
               if (this.getHealth() <= 0.0F) {
                  this.loot = null;
               }
            }
         } else {
            this.setAir(300);
         }

         float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
         this.rotationYaw = (float)(MathHelper.atan2(this.motionX, this.motionZ) * (180.0 / Math.PI));
         this.rotationPitch = (float)(MathHelper.atan2(this.motionY, f) * (180.0 / Math.PI));

         while (this.rotationPitch - this.prevRotationPitch < -180.0F) {
            this.prevRotationPitch -= 360.0F;
         }

         while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
            this.prevRotationPitch += 360.0F;
         }

         while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
            this.prevRotationYaw -= 360.0F;
         }

         while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
            this.prevRotationYaw += 360.0F;
         }

         this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
         this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
      }

      @SideOnly(Side.CLIENT)
      public void setVelocity(double x, double y, double z) {
         this.motionX = x;
         this.motionY = y;
         this.motionZ = z;
         if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
            float f = MathHelper.sqrt(x * x + z * z);
            this.rotationYaw = (float)(MathHelper.atan2(x, z) * (180.0 / Math.PI));
            this.rotationPitch = (float)(MathHelper.atan2(y, f) * (180.0 / Math.PI));
            this.prevRotationYaw = this.rotationYaw;
            this.prevRotationPitch = this.rotationPitch;
         }
      }

      @SideOnly(Side.CLIENT)
      @Override
      public void handleStatusUpdate(byte id) {
         super.handleStatusUpdate(id);
      }

      public boolean canBreatheUnderwater() {
         return true;
      }

      protected PathNavigate createNavigator(World worldIn) {
         return new PathNavigateSwimmer(this, worldIn);
      }

      public void travel(float strafe, float vertical, float forward) {
         if (this.isServerWorld() && this.isInWater()) {
            this.moveRelative(strafe, vertical, forward, 0.1F);
            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.9F;
            this.motionY *= 0.9F;
            this.motionZ *= 0.9F;
         } else {
            super.travel(strafe, vertical, forward);
         }
      }

      @Override
      public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
         this.breeds = this.rand.nextInt(4) + 3;
         return super.onInitialSpawn(difficulty, livingdata);
      }

      protected void initEntityAI() {
         this.tasks.addTask(1, new EntityAIRush(this, true, true, false));
         this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0));
         this.tasks.addTask(7, new EntityAIWander(this, 1.0, 80));
         this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
         this.tasks.addTask(8, new EntityAILookIdle(this));
         this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
         this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }
   }

   public static class SeaStriker extends AbstractMob {
      public SeaStriker(World world) {
         super(world, 0.75F, 1.2F);
         this.hurtSound = SoundEvents.ENTITY_GUARDIAN_HURT;
         this.deathSound = SoundEvents.ENTITY_GUARDIAN_DEATH;
         this.livingSound = Sounds.mob_fish_living;
         this.defaultteam = AquaticaMobsPack.mobsteam;
         this.setattributes(65.0, 32.0, 7.0, 0.25, 6.0, 4.0, 0.4, 0.0, 0.0, 0.0);
         this.setRoleValues(EnumMobRole.STRONG_SOLDIER, 5);
         this.soul = Soul.COMMON;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.6) {
            ShardType.spawnShards(ShardType.WATER, this, 1.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.6) {
            ShardType.spawnShards(ShardType.EARTH, this, 1.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.OCEAN_BLOOD;
      }

      public float getEyeHeight() {
         return this.height * 0.75F;
      }

      @Override
      public void shoot() {
         if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)7);
            HostileProjectiles.ArrowDartfish entityshoot = new HostileProjectiles.ArrowDartfish(this.world, this);
            entityshoot.shoot(this, this.rotationPitch - 3.0F, this.rotationYaw, 0.0F, 1.4F, 1.0F);
            this.world.spawnEntity(entityshoot);
         }
      }

      @Override
      public void onUpdate() {
         int airr = this.getAir();
         super.onUpdate();
         if (this.world.isRemote) {
            this.var3 = this.var4;
            this.var4 = this.var4 + this.var5;
         }

         if (this.isEntityAlive() && !this.isInWater()) {
            this.setAir(--airr);
            if (this.getAir() == -20) {
               this.setAir(0);
               this.attackEntityFrom(DamageSource.DROWN, 2.0F);
            }
         } else {
            this.setAir(300);
         }
      }

      protected void handleJumpWater() {
         this.motionY = this.motionY + 0.04F * this.getEntityAttribute(SWIM_SPEED).getAttributeValue() * Debugger.floats[0];
      }

      protected float getWaterSlowDown() {
         return Debugger.floats[1];
      }

      @SideOnly(Side.CLIENT)
      @Override
      public void handleStatusUpdate(byte id) {
         super.handleStatusUpdate(id);
         if (id == 7) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.slimeshoot,
                  SoundCategory.HOSTILE,
                  0.9F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );
         }
      }

      public boolean canBreatheUnderwater() {
         return true;
      }

      protected PathNavigate createNavigator(World worldIn) {
         return new PathNavigateBottom(this, worldIn);
      }

      protected void initEntityAI() {
         this.tasks.addTask(1, new EntityAIRush(this, true, true, false));
         this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0));
         this.tasks.addTask(7, new EntityAIWander(this, 1.0, 80));
         this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
         this.tasks.addTask(8, new EntityAILookIdle(this));
         this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
         this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }
   }

   public static class Siren extends AbstractMob {
      public float friction = 0.96F;
      public int attackStyle = 0;
      public float MANA = 35.0F;
      public float MAX_MANA = 35.0F;
      public boolean dangerMode = false;
      public float dangerModeDamage = 0.0F;
      public float otherModeDamage = 0.0F;

      public Siren(World world) {
         super(world, 0.75F, 1.7F);
         this.hurtSound = Sounds.mob_fish_hurt;
         this.deathSound = Sounds.mob_fish_death;
         this.livingSound = Sounds.mob_fish_living;
         this.defaultteam = AquaticaMobsPack.mobsteam;
         this.setattributes(150.0, 54.0, 18.0, 0.13, 7.0, 0.0, 0.3, 0.0, 0.0, 0.0);
         this.setNoGravity(true);
         this.setRoleValues(EnumMobRole.STRONG_SOLDIER, 5);
         this.soul = Soul.WORSHIPPING;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.3) {
            ShardType.spawnShards(ShardType.AIR, this, 2.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.5) {
            ShardType.spawnShards(ShardType.PLEASURE, this, 2.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.SIREN_BLOOD;
      }

      @Override
      public void writeEntityToNBT(NBTTagCompound compound) {
         compound.setFloat("MANA", this.MANA);
         compound.setFloat("MAX_MANA", this.MAX_MANA);
         compound.setBoolean("dangerMode", this.dangerMode);
         compound.setFloat("dangerModeDamage", this.dangerModeDamage);
         compound.setFloat("otherModeDamage", this.otherModeDamage);
         super.writeEntityToNBT(compound);
      }

      @Override
      public void readEntityFromNBT(NBTTagCompound compound) {
         if (compound.hasKey("MANA")) {
            this.MANA = compound.getFloat("MANA");
         }

         if (compound.hasKey("MAX_MANA")) {
            this.MAX_MANA = compound.getFloat("MAX_MANA");
         }

         if (compound.hasKey("dangerMode")) {
            this.dangerMode = compound.getBoolean("dangerMode");
         }

         if (compound.hasKey("dangerModeDamage")) {
            this.dangerModeDamage = compound.getFloat("dangerModeDamage");
         }

         if (compound.hasKey("otherModeDamage")) {
            this.otherModeDamage = compound.getFloat("otherModeDamage");
         }

         super.readEntityFromNBT(compound);
      }

      @Override
      public boolean attackEntityFrom(DamageSource source, float amount) {
         if (this.attackStyle == 2) {
            if (this.dangerModeDamage < amount) {
               this.dangerModeDamage = amount;
            }
         } else if (this.otherModeDamage < amount) {
            this.otherModeDamage = amount;
         }

         boolean atefr = super.attackEntityFrom(source, amount);
         if (this.getHealth() <= this.dangerModeDamage && this.dangerModeDamage > this.otherModeDamage) {
            this.dangerMode = true;
         }

         return atefr;
      }

      @Override
      public void shoot() {
         if (!this.world.isRemote && this.MANA >= 5.0F) {
            this.world.setEntityState(this, (byte)7);
            HostileProjectiles.SirenShoot entityshoot = new HostileProjectiles.SirenShoot(this.world, this);
            entityshoot.shoot(this, this.rotationPitch, this.rotationYaw, 0.0F, 0.9F + this.getMobDifficulty() * 0.1F, 0.7F);
            entityshoot.damage = 18.0F;
            this.world.spawnEntity(entityshoot);
            this.MANA -= 5.0F;
            if (this.dangerMode && this.getHealth() >= this.getMaxHealth() / 2.0F && this.getHealth() > this.dangerModeDamage) {
               this.dangerMode = false;
            }
         }
      }

      public void fall(float distance, float damageMultiplier) {
      }

      @Override
      public void onUpdate() {
         super.onUpdate();
         this.motionX = this.motionX * this.friction;
         this.motionY = this.motionY * this.friction;
         this.motionZ = this.motionZ * this.friction;
         if (!this.world.isRemote) {
            if (this.MANA < this.MAX_MANA) {
               this.MANA = (float)(this.MANA + 0.05);
            }

            if (this.world.getBlockState(this.getPosition().down()).getMaterial() == Material.WATER) {
               this.motionY += 0.1;
            }
         }
      }

      @SideOnly(Side.CLIENT)
      @Override
      public void handleStatusUpdate(byte id) {
         super.handleStatusUpdate(id);
         if (id == 7) {
            this.triggerAnimation(-3);
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.fire,
                  SoundCategory.HOSTILE,
                  1.7F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );
         }
      }

      @SideOnly(Side.CLIENT)
      public AxisAlignedBB getRenderBoundingBox() {
         if (Minecraft.getMinecraft().player != null && Minecraft.getMinecraft().player.isPotionActive(PotionEffects.SIREN_SONG)) {
            Random rand = new Random(this.getEntityId());
            float offX = MathHelper.sin((this.ticksExisted + rand.nextInt(500)) / (35.0F + rand.nextFloat() * 15.0F));
            float offY = MathHelper.sin((this.ticksExisted + rand.nextInt(500)) / (35.0F + rand.nextFloat() * 15.0F));
            float offZ = MathHelper.sin((this.ticksExisted + rand.nextInt(500)) / (35.0F + rand.nextFloat() * 15.0F));
            float power = SirenSong.clientPotionPower / 15.0F;
            return super.getRenderBoundingBox().offset(offX * power, offY * power, offZ * power);
         } else {
            return super.getRenderBoundingBox();
         }
      }

      protected void initEntityAI() {
         this.tasks.addTask(1, new EntityAISiren(this));
         this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
         this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
         this.targetTasks.addTask(2, new EntityAIAgree(this, EntityPlayer.class, true));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }
   }

   public static class Trachymona extends AbstractMob {
      public static float motionMaxSpeed = 1.0F;
      public static float speedIncrease = 0.05F;
      public int shootCooldown = 0;
      public boolean move = false;
      public int prjectiles = 0;

      public Trachymona(World world) {
         super(world, 0.95F, 0.95F);
         this.hurtSound = Sounds.mob_squish_hurt;
         this.deathSound = Sounds.mob_squish_death;
         this.defaultteam = AquaticaMobsPack.mobsteam;
         this.moveHelper = new WaterMoveHelper(this);
         this.setattributes(115.0, 22.0, 8.0, 0.05, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
         this.registerLOOT(new MobDrop[]{new MobDrop(ItemsRegister.MESOGLEA, 0.45F, 0, 1, 2, 0)});
         if (world.isRemote) {
            this.var3 = 0.0F;
            this.var4 = 0.0F;
            this.var5 = 0.0F;
         }

         this.setRoleValues(EnumMobRole.MIDDLE_ENEMY, 5);
         this.soul = Soul.COLLECTIVE;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.9) {
            ShardType.spawnShards(ShardType.WATER, this, 2.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.8) {
            ShardType.spawnShards(ShardType.PLEASURE, this, 1.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.JELLY_BLOOD;
      }

      public float getEyeHeight() {
         return this.height * 0.5F;
      }

      @Override
      public void shoot() {
         if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)7);
            HostileProjectiles.TrachymonaShoot entityshoot = new HostileProjectiles.TrachymonaShoot(this.world, this);
            entityshoot.damage = (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
            entityshoot.ticksOffset = -this.rand.nextInt(11);
            entityshoot.motionX = (this.rand.nextFloat() - 0.5F) / 4.5F;
            entityshoot.motionY = (this.rand.nextFloat() - 0.5F) / 4.5F;
            entityshoot.motionZ = (this.rand.nextFloat() - 0.5F) / 4.5F;
            this.world.spawnEntity(entityshoot);
         }
      }

      @Override
      public void onUpdate() {
         int airr = this.getAir();
         super.onUpdate();
         if (this.world.isRemote) {
            this.var3 = this.var4;
            if (this.move) {
               this.var5 = (float)(this.var5 * 0.9);
               this.var4 = this.var4 + this.var5;
            } else if (this.var5 < motionMaxSpeed) {
               this.var5 = this.var5 + speedIncrease;
            }
         } else if (this.getAttackTarget() != null) {
            if (this.prjectiles > 0) {
               this.shoot();
               this.prjectiles--;
            }

            this.shootCooldown--;
            if (this.shootCooldown <= 0 && this.getEntitySenses().canSee(this.getAttackTarget())) {
               this.prjectiles = 8;
               this.shootCooldown = 100;
               this.triggerAnimation(-2);
            }
         }

         if (this.isEntityAlive() && !this.isInWater()) {
            this.setAir(--airr);
            if (this.getAir() == -20) {
               this.setAir(0);
               this.attackEntityFrom(DamageSource.DROWN, 2.0F);
               if (this.getHealth() <= 0.0F) {
                  this.loot = null;
               }
            }
         } else {
            this.setAir(300);
         }

         float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
         this.rotationYaw = (float)(MathHelper.atan2(this.motionX, this.motionZ) * (180.0 / Math.PI));
         this.rotationPitch = (float)(MathHelper.atan2(this.motionY, f) * (180.0 / Math.PI));

         while (this.rotationPitch - this.prevRotationPitch < -180.0F) {
            this.prevRotationPitch -= 360.0F;
         }

         while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
            this.prevRotationPitch += 360.0F;
         }

         while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
            this.prevRotationYaw -= 360.0F;
         }

         while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
            this.prevRotationYaw += 360.0F;
         }

         this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
         this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
      }

      @SideOnly(Side.CLIENT)
      public void setVelocity(double x, double y, double z) {
         this.motionX = x;
         this.motionY = y;
         this.motionZ = z;
         if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
            float f = MathHelper.sqrt(x * x + z * z);
            this.rotationYaw = (float)(MathHelper.atan2(x, z) * (180.0 / Math.PI));
            this.rotationPitch = (float)(MathHelper.atan2(y, f) * (180.0 / Math.PI));
            this.prevRotationYaw = this.rotationYaw;
            this.prevRotationPitch = this.rotationPitch;
         }
      }

      @SideOnly(Side.CLIENT)
      @Override
      public void handleStatusUpdate(byte id) {
         super.handleStatusUpdate(id);
         if (id == 29) {
            this.move = true;
         }

         if (id == 28) {
            this.move = false;
         }

         if (id == 7) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.item_misc_e,
                  SoundCategory.HOSTILE,
                  0.8F,
                  1.2F + this.rand.nextFloat() / 5.0F,
                  false
               );
         }

         if (id == 15) {
            this.triggerAnimation(-1);
         }
      }

      public boolean canBreatheUnderwater() {
         return true;
      }

      protected PathNavigate createNavigator(World worldIn) {
         return new PathNavigateSwimmer(this, worldIn);
      }

      public void travel(float strafe, float vertical, float forward) {
         if (this.isServerWorld() && this.isInWater()) {
            this.moveRelative(strafe, vertical, forward, 0.1F);
            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.9F;
            this.motionY *= 0.9F;
            this.motionZ *= 0.9F;
         } else {
            super.travel(strafe, vertical, forward);
         }
      }

      protected void initEntityAI() {
         this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0));
         this.tasks.addTask(7, new EntityAIRandomSwim(this, motionMaxSpeed, speedIncrease, 0.01F, false));
         this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
         this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }
   }

   public static class WaterMoveHelper extends EntityMoveHelper {
      private final AbstractMob entitymob;
      public boolean stir = true;
      public boolean setYawOffset = true;

      public WaterMoveHelper(AbstractMob guardian) {
         super(guardian);
         this.entitymob = guardian;
      }

      public WaterMoveHelper(AbstractMob guardian, boolean stir, boolean setYawOffset) {
         this(guardian);
         this.stir = stir;
         this.setYawOffset = setYawOffset;
      }

      public void onUpdateMoveHelper() {
         if (this.action == Action.MOVE_TO && !this.entitymob.getNavigator().noPath()) {
            double d0 = this.posX - this.entitymob.posX;
            double d1 = this.posY - this.entitymob.posY;
            double d2 = this.posZ - this.entitymob.posZ;
            double d3 = MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
            d1 /= d3;
            float f = (float)(MathHelper.atan2(d2, d0) * (180.0 / Math.PI)) - 90.0F;
            this.entitymob.rotationYaw = this.limitAngle(this.entitymob.rotationYaw, f, 90.0F);
            if (this.setYawOffset) {
               this.entitymob.renderYawOffset = this.entitymob.rotationYaw;
            }

            float f1 = (float)(this.speed * this.entitymob.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue());
            this.entitymob.setAIMoveSpeed(this.entitymob.getAIMoveSpeed() + (f1 - this.entitymob.getAIMoveSpeed()) * 0.125F);
            double d4 = this.stir ? Math.sin((this.entitymob.ticksExisted + this.entitymob.getEntityId()) * 0.5) * 0.05 : 0.0;
            double d5 = Math.cos(this.entitymob.rotationYaw * (float) (Math.PI / 180.0));
            double d6 = Math.sin(this.entitymob.rotationYaw * (float) (Math.PI / 180.0));
            this.entitymob.motionX += d4 * d5;
            this.entitymob.motionZ += d4 * d6;
            d4 = this.stir ? Math.sin((this.entitymob.ticksExisted + this.entitymob.getEntityId()) * 0.75) * 0.05 : 0.0;
            this.entitymob.motionY += d4 * (d6 + d5) * 0.25;
            this.entitymob.motionY = this.entitymob.motionY + this.entitymob.getAIMoveSpeed() * d1 * 0.1;
            EntityLookHelper entitylookhelper = this.entitymob.getLookHelper();
            double d7 = this.entitymob.posX + d0 / d3 * 2.0;
            double d8 = this.entitymob.getEyeHeight() + this.entitymob.posY + d1 / d3;
            double d9 = this.entitymob.posZ + d2 / d3 * 2.0;
            double d10 = entitylookhelper.getLookPosX();
            double d11 = entitylookhelper.getLookPosY();
            double d12 = entitylookhelper.getLookPosZ();
            if (!entitylookhelper.getIsLooking()) {
               d10 = d7;
               d11 = d8;
               d12 = d9;
            }

            this.entitymob.getLookHelper().setLookPosition(d10 + (d7 - d10) * 0.125, d11 + (d8 - d11) * 0.125, d12 + (d9 - d12) * 0.125, 10.0F, 40.0F);
            this.entitymob.var5 = Math.min(this.entitymob.var5 + 0.05F, 2.0F);
            this.entitymob.world.setEntityState(this.entitymob, (byte)9);
         } else {
            this.entitymob.setAIMoveSpeed(0.0F);
            this.entitymob.var5 = Math.max(this.entitymob.var5 - 0.04F, 0.0F);
            this.entitymob.world.setEntityState(this.entitymob, (byte)8);
         }
      }
   }

   public static class Wizardfish extends AbstractMob implements IEntitySynchronize {
      public int magicCooldown = 150;
      public static ResourceLocation tex1 = new ResourceLocation("arpg:textures/simple_magic_shoot.png");
      public int particklesTime = 0;
      public Entity particklesEntity;
      public Vec3d particklesColor;

      public Wizardfish(World world) {
         super(world, 0.95F, 0.95F);
         this.hurtSound = SoundEvents.ENTITY_GUARDIAN_HURT;
         this.deathSound = SoundEvents.ENTITY_GUARDIAN_DEATH;
         this.livingSound = SoundEvents.ENTITY_GUARDIAN_AMBIENT;
         this.defaultteam = AquaticaMobsPack.mobsteam;
         this.moveHelper = new WaterMoveHelper(this);
         this.setattributes(150.0, 48.0, 14.0, 0.24, 5.0, 2.0, 0.0, 0.0, 0.0, 0.0);
         this.registerLOOT(new MobDrop[]{new MobDrop(ItemsRegister.FISHSTEAKRAW, 1.0F, 0, 1, 1, 2)});
         this.var3 = 0.0F;
         this.var4 = 0.0F;
         this.var5 = 0.0F;
         this.setRoleValues(EnumMobRole.STRONG_ENEMY, 5);
         this.soul = Soul.MYSTIC;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.9) {
            ShardType.spawnShards(ShardType.WATER, this, 2.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.8) {
            ShardType.spawnShards(ShardType.LIVE, this, 4.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.7) {
            ShardType.spawnShards(ShardType.VOID, this, 2.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.FISH_BLOOD;
      }

      public float getEyeHeight() {
         return this.height * 0.6F;
      }

      @Override
      public void shoot() {
         if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)7);
            HostileProjectiles.WizardfishShoot entityshoot = new HostileProjectiles.WizardfishShoot(this.world, this);
            entityshoot.shoot(this, this.rotationPitch, this.rotationYaw, 0.0F, 0.7F + this.getMobDifficulty() * 0.1F, 1.1F);
            entityshoot.damage = (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
            this.world.spawnEntity(entityshoot);
         }
      }

      @Override
      public void onClient(double x, double y, double z, double a, double b, double c) {
         this.particklesTime = 15;
         this.particklesEntity = this.world.getEntityByID((int)x);
         this.particklesColor = new Vec3d(a, b, c);
         this.world
            .playSound(
               this.posX,
               this.posY,
               this.posZ,
               Sounds.magic_o,
               SoundCategory.HOSTILE,
               0.9F,
               0.8F + this.rand.nextFloat() * 0.4F,
               false
            );
         this.world.playSound(x, y, z, Sounds.item_misc_c, SoundCategory.HOSTILE, 0.6F, 0.9F + this.rand.nextFloat() / 5.0F, false);
      }

      public void spawnParticles1(Entity target, Vec3d pos, float r, float g, float b, int count) {
         ParticleTracker<TrailParticle> tracker = new ParticleTracker.TrackerFollowDynamicPoint(target, false, 0.4F, 0.002F, 0.025F);

         for (int i = 0; i < count; i++) {
            int lt = (int)Math.min(Math.sqrt(this.getDistanceSq(pos.x, pos.y, pos.z)) * 10.0, 80.0);
            float scl = 0.085F + this.rand.nextFloat() * 0.15F;
            GUNParticle spelll = new GUNParticle(
               tex1,
               scl,
               0.01F - this.rand.nextFloat() * 0.02F,
               lt,
               220,
               this.world,
               this.posX + (this.rand.nextFloat() - 0.5),
               this.posY + (this.rand.nextFloat() - 0.5),
               this.posZ + (this.rand.nextFloat() - 0.5),
               (float)this.rand.nextGaussian() / 15.0F,
               0.15F,
               (float)this.rand.nextGaussian() / 15.0F,
               r,
               g,
               b,
               true,
               0
            );
            spelll.scaleTickAdding = scl * -0.8F / lt;
            spelll.alphaGlowing = true;
            spelll.tracker = tracker;
            this.world.spawnEntity(spelll);
         }
      }

      @Override
      public void onUpdate() {
         int airr = this.getAir();
         super.onUpdate();
         if (this.world.isRemote) {
            this.var3 = this.var4;
            this.var4 = this.var4 + this.var5;
            if (this.particklesTime > 0) {
               if (this.particklesEntity != null) {
                  this.spawnParticles1(
                     this.particklesEntity,
                     GetMOP.entityCenterPos(this.particklesEntity),
                     (float)this.particklesColor.x,
                     (float)this.particklesColor.y,
                     (float)this.particklesColor.z,
                     this.rand.nextInt(3) + 1
                  );
               }

               this.particklesTime--;
            }
         } else if (--this.magicCooldown <= 0 && !this.isAIDisabled()) {
            if (this.getAttackTarget() != null) {
               double damageRadius = 15.0;
               AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
                  .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
                  .offset(-damageRadius, -damageRadius, -damageRadius);
               List<EntityLiving> list = this.world.getEntitiesWithinAABB(EntityLiving.class, axisalignedbb);
               if (!list.isEmpty()) {
                  EntityLiving mob = null;
                  int v = 0;

                  for (int i = this.rand.nextInt(list.size()); v < list.size(); i = GetMOP.next(i, 1, list.size())) {
                     v++;
                     EntityLiving e = list.get(i);
                     if (!Team.checkIsOpponent(this, e) && EntitySelectors.CAN_AI_TARGET.apply(e)) {
                        mob = e;
                        break;
                     }
                  }

                  if (mob != null) {
                     if (mob.canBreatheUnderwater()) {
                        if (this.rand.nextFloat() < 0.3 && !mob.getNavigator().noPath()) {
                           mob.addPotionEffect(new PotionEffect(MobEffects.SPEED, 700, 1));
                           this.magicCooldown = this.modifyMagicCooldown(150);
                           Vec3d col = ColorConverters.DecimaltoRGB(MobEffects.SPEED.getLiquidColor());
                           IEntitySynchronize.sendSynchronize(
                              this, 64.0, mob.getEntityId(), 0.0, 0.0, col.x, col.y, col.z
                           );
                        } else if (this.rand.nextFloat() < 0.3 && mob.getAttackTarget() != null) {
                           mob.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 700));
                           this.magicCooldown = this.modifyMagicCooldown(130);
                           Vec3d col = ColorConverters.DecimaltoRGB(MobEffects.STRENGTH.getLiquidColor());
                           IEntitySynchronize.sendSynchronize(
                              this, 64.0, mob.getEntityId(), 0.0, 0.0, col.x, col.y, col.z
                           );
                        } else if (this.rand.nextFloat() < 0.3 && mob.getHealth() + 20.0F < mob.getMaxHealth()) {
                           mob.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 900, 4));
                           this.magicCooldown = this.modifyMagicCooldown(160);
                           Vec3d col = ColorConverters.DecimaltoRGB(MobEffects.REGENERATION.getLiquidColor());
                           IEntitySynchronize.sendSynchronize(
                              this, 64.0, mob.getEntityId(), 0.0, 0.0, col.x, col.y, col.z
                           );
                        } else {
                           mob.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 600, 1));
                           this.magicCooldown = this.modifyMagicCooldown(100);
                           Vec3d col = ColorConverters.DecimaltoRGB(MobEffects.RESISTANCE.getLiquidColor());
                           IEntitySynchronize.sendSynchronize(
                              this, 64.0, mob.getEntityId(), 0.0, 0.0, col.x, col.y, col.z
                           );
                        }
                     } else if (mob.isInsideOfMaterial(Material.WATER) && mob.getHealth() > 25.0F) {
                        mob.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 1500));
                        this.magicCooldown = this.modifyMagicCooldown(100);
                        Vec3d col = ColorConverters.DecimaltoRGB(MobEffects.WATER_BREATHING.getLiquidColor());
                        IEntitySynchronize.sendSynchronize(this, 64.0, mob.getEntityId(), 0.0, 0.0, col.x, col.y, col.z);
                     } else if (this.rand.nextFloat() < 0.3 && !mob.getNavigator().noPath()) {
                        mob.addPotionEffect(new PotionEffect(MobEffects.SPEED, 700));
                        this.magicCooldown = this.modifyMagicCooldown(150);
                        Vec3d col = ColorConverters.DecimaltoRGB(MobEffects.SPEED.getLiquidColor());
                        IEntitySynchronize.sendSynchronize(this, 64.0, mob.getEntityId(), 0.0, 0.0, col.x, col.y, col.z);
                     } else if (this.rand.nextFloat() < 0.3 && mob.getAttackTarget() != null) {
                        mob.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 700));
                        this.magicCooldown = this.modifyMagicCooldown(130);
                        Vec3d col = ColorConverters.DecimaltoRGB(MobEffects.STRENGTH.getLiquidColor());
                        IEntitySynchronize.sendSynchronize(this, 64.0, mob.getEntityId(), 0.0, 0.0, col.x, col.y, col.z);
                     } else {
                        mob.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 600, 1));
                        this.magicCooldown = this.modifyMagicCooldown(100);
                        Vec3d col = ColorConverters.DecimaltoRGB(MobEffects.RESISTANCE.getLiquidColor());
                        IEntitySynchronize.sendSynchronize(this, 64.0, mob.getEntityId(), 0.0, 0.0, col.x, col.y, col.z);
                     }
                  } else {
                     this.magicCooldown = 15;
                  }
               } else {
                  this.magicCooldown = 15;
               }
            } else if (this.getHealth() < this.getMaxHealth()) {
               this.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 900, 4));
               this.magicCooldown = this.modifyMagicCooldown(160);
               Vec3d col = ColorConverters.DecimaltoRGB(MobEffects.REGENERATION.getLiquidColor());
               IEntitySynchronize.sendSynchronize(this, 64.0, this.getEntityId(), 0.0, 0.0, col.x, col.y, col.z);
            }
         }

         if (this.isEntityAlive() && !this.isInWater()) {
            this.setAir(--airr);
            if (this.getAir() == -20) {
               this.setAir(0);
               this.attackEntityFrom(DamageSource.DROWN, 2.0F);
            }
         } else {
            this.setAir(300);
         }
      }

      public int modifyMagicCooldown(int baseCooldown) {
         float diffMult = 1.4F - this.getMobDifficulty() * 0.2F;
         return (int)(baseCooldown * diffMult);
      }

      @SideOnly(Side.CLIENT)
      @Override
      public void handleStatusUpdate(byte id) {
         super.handleStatusUpdate(id);
         if (id == 8) {
            this.var5 = Math.max(this.var5 - 0.04F, 0.0F);
         }

         if (id == 9) {
            this.var5 = Math.min(this.var5 + 0.05F, 2.0F);
         }

         if (id == 7) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.restless_skull,
                  SoundCategory.HOSTILE,
                  0.9F,
                  1.4F + this.rand.nextFloat() / 5.0F,
                  false
               );
         }

         if (id == 15) {
            this.triggerAnimation(-1);
         }
      }

      public boolean canBreatheUnderwater() {
         return true;
      }

      protected PathNavigate createNavigator(World worldIn) {
         return new PathNavigateSwimmer(this, worldIn);
      }

      public void travel(float strafe, float vertical, float forward) {
         if (this.isServerWorld() && this.isInWater()) {
            this.moveRelative(strafe, vertical, forward, 0.1F);
            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.9F;
            this.motionY *= 0.9F;
            this.motionZ *= 0.9F;
         } else {
            super.travel(strafe, vertical, forward);
         }
      }

      protected void initEntityAI() {
         this.tasks.addTask(1, new EntityAIShootAndSwim(this, 65, 19.0F, 23 - this.getMobDifficulty() * 4, 18, 130));
         this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0));
         this.tasks.addTask(7, new EntityAIWander(this, 1.0, 80));
         this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
         this.tasks.addTask(8, new EntityAILookIdle(this));
         this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
         this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }
   }
}
