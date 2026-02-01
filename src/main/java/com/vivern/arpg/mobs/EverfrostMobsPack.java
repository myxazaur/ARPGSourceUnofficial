package com.vivern.arpg.mobs;

import com.vivern.arpg.elements.models.FrostModel;
import com.vivern.arpg.elements.models.ModelsEverfrostMob;
import com.vivern.arpg.entity.BetweenParticle;
import com.vivern.arpg.entity.CannonSnowball;
import com.vivern.arpg.entity.IEntitySynchronize;
import com.vivern.arpg.events.Debugger;
import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.main.BloodType;
import com.vivern.arpg.main.ColorConverters;
import com.vivern.arpg.main.DeathEffects;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.ParticleFastSummon;
import com.vivern.arpg.main.ShardType;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.Team;
import com.vivern.arpg.main.WeaponDamage;
import com.vivern.arpg.main.Weapons;
import com.vivern.arpg.potions.Freezing;
import com.vivern.arpg.potions.PotionEffects;
import com.vivern.arpg.recipes.Soul;
import com.vivern.arpg.renders.GUNParticle;
import com.vivern.arpg.renders.ParticleTracker;
import com.vivern.arpg.tileentity.TileNexusNiveolite;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EverfrostMobsPack {
   public static String mobsteam = "frozen";

   public static void init() {
      AbstractMob.addToRegister(Fentral.class, "Fentral", 6846619, 13612721);
      AbstractMob.addToRegister(IceWarrior.class, "Ice Warrior", 4156050, 7646194);
      AbstractMob.addToRegister(HailWraith.class, "Hail Wraith", 2766405, 10285566);
      AbstractMob.addToRegister(IceApparition.class, "Ice Apparition", 8363432, 1252404);
      AbstractMob.addToRegister(IceCube.class, "Ice Cube", 9291497, 274050);
      AbstractMob.addToRegister(Atorpid.class, "Atorpid", 5919558, 3627348);
      AbstractMob.addToRegister(Gargoyle.class, "Gargoyle", 3947606, 12896230);
      AbstractMob.addToRegister(Gelum.class, "Gelum", 15924991, 11860735);
      AbstractMob.addToRegister(BossWinterFury.class, "Winter Fury", 804995, 4908287);
      AbstractMob.addToRegister(Snowrover.class, "Snowrover", 16056319, 3812645);
      AbstractMob.addToRegister(HarridanOfIce.class, "Harridan Of Ice", 7641840, 12121599);
      AbstractMob.addToRegister(Frost.class, "Frost", 9420008, 1721192);
      AbstractMob.addToRegister(NiveousSlider.class, "Niveous Slider", 14938106, 13456729);
      AbstractMob.addToRegister(AurorasPhantasm.class, "Auroras Phantasm", 3162213, 7008857);
   }

   public static void initRender() {
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new ModelsEverfrostMob.FentralModel(), new ResourceLocation("arpg:textures/fentral_model_tex.png"), 0.6F, Fentral.class
         )
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
               new ModelsEverfrostMob.IceWarriorModel(),
               new ResourceLocation("arpg:textures/ice_warrior_model_tex.png"),
               0.6F,
               IceWarrior.class
            )
            .setLayerHeldItem()
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new ModelsEverfrostMob.HailWraithModel(), new ResourceLocation("arpg:textures/hail_wraith_model_tex.png"), 0.8F, HailWraith.class
         )
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new ModelsEverfrostMob.IceApparitionModel(),
            new ResourceLocation("arpg:textures/ice_apparition_model_tex.png"),
            1.0F,
            IceApparition.class
         )
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new ModelsEverfrostMob.SlimeModel(), new ResourceLocation("arpg:textures/ice_cube_tex.png"), 0.2F, IceCube.class
         )
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
               new ModelsEverfrostMob.AtorpidModel(), new ResourceLocation("arpg:textures/atorpid_model_tex.png"), 0.4F, Atorpid.class
            )
            .setLayerHeldItem()
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new ModelsEverfrostMob.GargoyleModel(), new ResourceLocation("arpg:textures/gargoyle_model_tex.png"), 0.4F, Gargoyle.class
         )
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new ModelsEverfrostMob.GelumModel(), new ResourceLocation("arpg:textures/gelum_model_tex.png"), 0.5F, Gelum.class
         )
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new ModelsEverfrostMob.SnowroverModel(), new ResourceLocation("arpg:textures/snowrover_model_tex.png"), 0.4F, Snowrover.class
         )
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new ModelsEverfrostMob.HarridanOfIceModel(),
            new ResourceLocation("arpg:textures/harridan_of_ice_model_tex.png"),
            0.5F,
            HarridanOfIce.class
         )
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new FrostModel(), new ResourceLocation("arpg:textures/frost_model_tex.png"), 0.7F, Frost.class
         )
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new ModelsEverfrostMob.NiveousSliderModel(),
            new ResourceLocation("arpg:textures/niveous_slider_model_tex.png"),
            0.435F,
            NiveousSlider.class
         )
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new ModelsEverfrostMob.AurorasPhantasmModel(new ResourceLocation("arpg:textures/auroras_phantasm_model_tex_overlay.png")),
            new ResourceLocation("arpg:textures/auroras_phantasm_model_tex.png"),
            0.5F,
            AurorasPhantasm.class
         )
      );
   }

   public static class Atorpid extends AbstractMob {
      public Atorpid(World world) {
         super(world, 0.65F, 1.85F);
         this.hurtSound = Sounds.mob_ice_hurt;
         this.deathSound = Sounds.mob_ice_death;
         this.livingSound = Sounds.mob_mummy_living;
         this.defaultteam = EverfrostMobsPack.mobsteam;
         this.setattributes(30.0, 64.0, 4.5, 0.245, 6.0, 2.0, 0.0, 0.0, 0.0, 0.0);
         this.registerLOOT(
            new MobDrop[]{
               new MobDrop(Items.ROTTEN_FLESH, 0.75F, 0, 0, 2, 2), new MobDrop(Items.BONE, 0.75F, 0, 0, 2, 2)
            }
         );
         this.setRoleValues(EnumMobRole.SOLDIER, 2);
         this.soul = Soul.PALE;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.5) {
            ShardType.spawnShards(ShardType.DEATH, this, 1.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.ATORPID_BLOOD;
      }

      @Nullable
      @Override
      public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
         super.onInitialSpawn(difficulty, livingdata);
         if (this.rand.nextFloat() < 0.4) {
            int i = this.rand.nextInt(14);
            if (i < 2) {
               this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.STONE_AXE));
            } else if (i == 2) {
               this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_AXE));
            } else if (i == 3) {
               this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.STONE_SWORD));
            } else if (i == 4) {
               this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.BONE));
            } else if (i == 5) {
               this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.STONE_PICKAXE));
            } else if (i == 6) {
               this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(ItemsRegister.GOTHICAXE));
               this.inventoryHandsDropChances = new float[]{0.0F, 0.0F};
            }

            int t = this.rand.nextInt(17);
            if (t < 2) {
               this.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, new ItemStack(Items.STONE_AXE));
            } else if (t == 2) {
               this.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, new ItemStack(Items.IRON_AXE));
            } else if (t == 3) {
               this.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, new ItemStack(Items.STONE_SWORD));
            } else if (t == 4) {
               this.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, new ItemStack(Items.BONE));
            } else if (t == 5) {
               this.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, new ItemStack(Items.STONE_PICKAXE));
            }
         }

         return livingdata;
      }

      @Override
      public boolean attackEntityAsMob(Entity entityIn) {
         this.triggerAnimation(-1);
         this.playSound(Sounds.mob_mummy_attack, 0.9F, 0.85F + this.rand.nextFloat() * 0.3F);
         return super.attackEntityAsMob(entityIn);
      }

      public EnumCreatureAttribute getCreatureAttribute() {
         return EnumCreatureAttribute.UNDEAD;
      }

      public boolean isPotionApplicable(PotionEffect potioneffectIn) {
         return potioneffectIn.getPotion() == PotionEffects.WINTER_CURSE ? false : super.isPotionApplicable(potioneffectIn);
      }

      protected void initEntityAI() {
         this.tasks.addTask(0, new EntityAISwimming(this));
         this.tasks.addTask(2, new EntityAIRush(this, false, true, true));
         this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0));
         this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0));
         this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
         this.tasks.addTask(8, new EntityAILookIdle(this));
         this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
         this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }
   }

   public static class AurorasPhantasm extends AbstractMob implements IEntitySynchronize {
      static ResourceLocation texture = new ResourceLocation("arpg:textures/star2.png");
      static ResourceLocation texturelaser = new ResourceLocation("arpg:textures/phantasm_beam.png");
      static ResourceLocation largesmoke = new ResourceLocation("arpg:textures/largecloud.png");
      public int laserAttackTick = 0;
      public int laserAttackTickMax = 40;
      public int laserAttackCooldown = 0;
      public int laserAttackCooldownMax = 100;
      public Vec3d laserTarget1;
      public Vec3d laserTarget2;

      public AurorasPhantasm(World world) {
         super(world, 1.0F, 1.0F);
         this.hurtSound = Sounds.frost_hurt;
         this.deathSound = Sounds.frost_dead;
         this.livingSound = Sounds.phantasm_living;
         this.defaultteam = EverfrostMobsPack.mobsteam;
         this.setattributes(60.0, 48.0, 6.0, 0.1, 3.0, 1.0, 0.2, 0.0, 0.0, 0.0);
         this.registerLOOT(
            new MobDrop[]{
               new MobDrop(ItemsRegister.ICEGEM, 0.3F, 0, 1, 1, 1), new MobDrop(Items.SNOWBALL, 0.5F, 0, 1, 2, 4)
            }
         );
         this.setNoGravity(true);
         this.setRoleValues(EnumMobRole.STRONG_ENEMY, 2);
         this.soul = Soul.MYSTIC;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.7) {
            ShardType.spawnShards(ShardType.ELECTRIC, this, 3.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.5) {
            ShardType.spawnShards(ShardType.PLEASURE, this, 3.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.AURORA_BLOOD;
      }

      public boolean isPotionApplicable(PotionEffect potioneffectIn) {
         return potioneffectIn.getPotion() == PotionEffects.FREEZING ? false : super.isPotionApplicable(potioneffectIn);
      }

      public void fall(float distance, float damageMultiplier) {
      }

      @SideOnly(Side.CLIENT)
      public void spawnBetwParticle(Vec3d from, Vec3d to) {
         Vec3d rainbow = getPhantasmColor(this);
         if (from.lengthSquared() > 1.0E-6 && to.lengthSquared() > 1.0E-6) {
            BetweenParticle laser = new BetweenParticle(
               this.world, texturelaser, 0.35F, 240, 1.0F, 1.0F, 1.0F, 0.15F, from.distanceTo(to), 3, 0.08F, 5.0F, from, to
            );
            laser.ignoreFrustumCheck = true;
            laser.setPosition(from.x, from.y, from.z);
            laser.alphaGlowing = true;
            laser.softAnimation = true;
            this.world.spawnEntity(laser);

            for (int ss = 0; ss < 3; ss++) {
               GUNParticle bigsmoke = new GUNParticle(
                  largesmoke,
                  0.4F + (float)this.rand.nextGaussian() / 20.0F,
                  0.0F,
                  12,
                  180,
                  this.world,
                  to.x,
                  to.y,
                  to.z,
                  (float)this.rand.nextGaussian() / 23.0F,
                  (float)this.rand.nextGaussian() / 27.0F,
                  (float)this.rand.nextGaussian() / 23.0F,
                  (float)rainbow.x,
                  (float)rainbow.y,
                  (float)rainbow.z,
                  true,
                  this.rand.nextInt(360)
               );
               bigsmoke.alphaGlowing = true;
               bigsmoke.alphaTickAdding = -0.07F;
               this.world.spawnEntity(bigsmoke);
            }

            for (int ss = 0; ss < 3; ss++) {
               GUNParticle sparkle = new GUNParticle(
                  texture,
                  0.1F + (float)this.rand.nextGaussian() / 30.0F,
                  0.01F,
                  10,
                  240,
                  this.world,
                  to.x,
                  to.y,
                  to.z,
                  (float)this.rand.nextGaussian() / 14.0F,
                  (float)this.rand.nextGaussian() / 14.0F + 0.15F,
                  (float)this.rand.nextGaussian() / 14.0F,
                  (float)rainbow.x,
                  (float)rainbow.y,
                  (float)rainbow.z,
                  true,
                  0
               );
               sparkle.alphaGlowing = true;
               this.world.spawnEntity(sparkle);
            }
         }
      }

      public void onLaser(Vec3d vec) {
         if (this.ticksExisted % 4 == 0 && !this.world.isRemote) {
            List<Entity> list = GetMOP.findEntitiesOnPath(GetMOP.entityCenterPos(this), vec, this.world, this, 0.7, 0.5);
            if (!list.isEmpty()) {
               for (Entity entity : list) {
                  if (Team.checkIsOpponent(this, entity)) {
                     Weapons.dealDamage(
                        new WeaponDamage(null, this, null, false, false, this, WeaponDamage.laser),
                        (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue(),
                        this,
                        entity,
                        true,
                        0.05F
                     );
                     entity.hurtResistantTime = 0;
                  }
               }
            }

            Vec3d rainbow = getPhantasmColor(this);
            HostileProjectiles.Plasma plasma = new HostileProjectiles.Plasma(
               this.world,
               this,
               0.9F,
               0.0F,
               100,
               vec.x,
               vec.y,
               vec.z,
               4.0F,
               0.4F,
               1,
               false,
               (float)rainbow.x,
               (float)rainbow.y,
               (float)rainbow.z
            );
            this.world.spawnEntity(plasma);
         }

         IEntitySynchronize.sendSynchronize(this, 64.0, vec.x, vec.y, vec.z);
      }

      @Override
      public void onClient(double... args) {
         if (args.length == 3) {
            Vec3d vec = new Vec3d(args[0], args[1], args[2]);
            this.world
               .playSound(
                  vec.x,
                  vec.y,
                  vec.z,
                  Sounds.phantasm_shoot,
                  SoundCategory.AMBIENT,
                  0.7F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );
            this.spawnBetwParticle(this.getPositionVector().add(0.0, this.height / 2.0F, 0.0), vec);
         }
      }

      public static Vec3d getPhantasmColor(Entity entity) {
         float time = entity.ticksExisted * 1.8F;
         return entity.getEntityId() % 2 == 0
            ? ColorConverters.getRainbow(0.55F + (float)GetMOP.arcsinusoid(time * 0.01) * 0.25F)
            : ColorConverters.getRainbow(0.47F + (float)GetMOP.arcsinusoid(time * 0.01) * 0.2F);
      }

      @Override
      public void onUpdate() {
         super.onUpdate();
         if (this.world.isRemote) {
            Vec3d rainbow = getPhantasmColor(this);
            float scl = 0.08F + this.rand.nextFloat() / 10.0F;
            int lt = 25 + this.rand.nextInt(10);
            GUNParticle snow = new GUNParticle(
               texture,
               scl,
               0.01F,
               lt,
               220,
               this.world,
               this.posX + (this.rand.nextDouble() - 0.5) * this.width,
               this.posY + this.rand.nextDouble() * this.height,
               this.posZ + (this.rand.nextDouble() - 0.5) * this.width,
               (this.rand.nextFloat() - 0.5F) * 0.02F,
               -this.rand.nextFloat() * 0.02F,
               (this.rand.nextFloat() - 0.5F) * 0.02F,
               (float)rainbow.x,
               (float)rainbow.y,
               (float)rainbow.z,
               true,
               this.rand.nextInt(100) - 50,
               true,
               3.0F
            );
            snow.alphaGlowing = true;
            snow.scaleTickAdding = -scl / lt * 0.9F;
            this.world.spawnEntity(snow);
         } else if (this.isEntityAlive() && !this.isAIDisabled()) {
            if (this.laserAttackCooldown <= 0) {
               if (this.getAttackTarget() != null) {
                  float range = (float)this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getAttributeValue() * 0.6666F;
                  range *= range;
                  if (this.getDistanceSq(this.getAttackTarget()) < range) {
                     this.laserAttackTick = this.laserAttackTickMax;
                     this.laserAttackCooldown = this.laserAttackCooldownMax;
                     Vec3d centerThis = GetMOP.entityCenterPos(this);
                     Vec3d centerTarget = GetMOP.entityCenterPos(this.getAttackTarget());
                     Vec3d beam = centerTarget.subtract(centerThis);
                     Vec3d pitchYaw = GetMOP.Vec3dToPitchYaw(beam);
                     Vec3d perp = GetMOP.PitchYawToVec3d((float)pitchYaw.x - 90.0F, (float)pitchYaw.y).scale(5.0);
                     Vec3d randomPerpVector = GetMOP.rotateVecAroundAxis(perp, beam, this.rand.nextFloat() * 6.283185F);
                     Vec3d normalizedbeam = beam.normalize().scale(2.0);
                     this.laserTarget1 = centerTarget.add(randomPerpVector).add(normalizedbeam);
                     this.laserTarget2 = centerTarget.subtract(randomPerpVector).add(normalizedbeam);
                  }
               }
            } else if (this.laserAttackTick > 0) {
               this.laserAttackTick--;
               this.motionX *= 0.4;
               this.motionY *= 0.4;
               this.motionZ *= 0.4;
               float ft1 = GetMOP.getfromto((float)this.laserAttackTick, 0.0F, (float)this.laserAttackTickMax);
               Vec3d laserPos = new Vec3d(
                  GetMOP.partial(this.laserTarget2.x, this.laserTarget1.x, (double)ft1),
                  GetMOP.partial(this.laserTarget2.y, this.laserTarget1.y, (double)ft1),
                  GetMOP.partial(this.laserTarget2.z, this.laserTarget1.z, (double)ft1)
               );
               if (this.getAttackTarget() != null && this.getEntitySenses().canSee(this.getAttackTarget())) {
                  Vec3d centerThis = GetMOP.entityCenterPos(this);
                  RayTraceResult result = this.world.rayTraceBlocks(centerThis, laserPos, false, true, false);
                  if (result != null && result.typeOfHit == Type.BLOCK && result.hitVec != null) {
                     laserPos = result.hitVec;
                  }
               }

               this.getLookHelper().setLookPosition(laserPos.x, laserPos.y, laserPos.z, 90.0F, 90.0F);
               this.onLaser(laserPos);
            } else {
               this.laserAttackCooldown--;
            }
         }
      }

      @Override
      public void onDeath(DamageSource cause) {
         super.onDeath(cause);
         if (!this.world.isRemote) {
            this.world.spawnEntity(new HostileProjectiles.AurorasTear(this.world, this.posX, this.posY, this.posZ));
         }
      }

      protected void initEntityAI() {
         this.tasks.addTask(1, new EntityAIFlying(this, 150, 10.0F, 0.03F, false));
         this.tasks.addTask(2, new EntityAIFlyingKeepDistToTarget(this, 60, 8.0F, 0.08F, 2.0F, 2.0F, 1.0F, 1.05F));
         this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(2, new EntityAIAgree(this, EntityPlayer.class, false));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }
   }

   public static class Fentral extends AbstractMob {
      public static UUID lowhpbonus = UUID.fromString("215FD27B-F321-978F-4C9F-2421A4A38B3B");
      public static UUID lowhpbonus2 = UUID.fromString("225FD27B-F321-978F-4C9F-2421A4A38B3B");

      public Fentral(World world) {
         super(world, 0.9F, 0.7F);
         this.hurtSound = SoundEvents.ENTITY_WOLF_HURT;
         this.deathSound = SoundEvents.ENTITY_WOLF_DEATH;
         this.livingSound = SoundEvents.ENTITY_WOLF_GROWL;
         this.stepSound = SoundEvents.ENTITY_WOLF_STEP;
         this.defaultteam = EverfrostMobsPack.mobsteam;
         this.setattributes(45.0, 48.0, 5.0, 0.3, 2.0, 0.0, 0.2, 0.0, 0.0, 0.0);
         this.registerLOOT(new MobDrop[]{new MobDrop(Item.getItemFromBlock(Blocks.WOOL), 0.4F, 8, 1, 1, 2)});
         this.setRoleValues(EnumMobRole.STRONG_SOLDIER, 2);
         this.soul = Soul.WINTRY;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.5) {
            ShardType.spawnShards(ShardType.PAIN, this, 2.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.4) {
            ShardType.spawnShards(ShardType.LIVE, this, 1.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.FROSTED_RED_BLOOD;
      }

      @Override
      public boolean attackEntityFrom(DamageSource source, float amount) {
         boolean at = super.attackEntityFrom(source, amount);
         if (this.getHealth() < this.getMaxHealth() / 2.0F) {
            if (this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getModifier(lowhpbonus) == null) {
               this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE)
                  .applyModifier(new AttributeModifier(lowhpbonus, "lowhpbonus", 2.0 + this.getMobDifficulty() * 0.5, 0));
            }

            if (this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getModifier(lowhpbonus2) == null) {
               this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED)
                  .applyModifier(new AttributeModifier(lowhpbonus2, "lowhpbonus2", 0.1 + this.getMobDifficulty() * 0.05, 2));
            }
         }

         return at;
      }

      protected void initEntityAI() {
         this.tasks.addTask(0, new EntityAISwimming(this));
         this.tasks.addTask(2, new EntityAIRush(this, false, true, false));
         this.tasks.addTask(1, new EntityAIDash(this, 40, 2.0F, 0.0F, 0.3F, true, 0.3F));
         this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0));
         this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0));
         this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
         this.tasks.addTask(8, new EntityAILookIdle(this));
         this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
         this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }
   }

   public static class Frost extends AbstractMob implements ILongLaserAttackMob {
      static ResourceLocation texture = new ResourceLocation("arpg:textures/snowflake2.png");
      static ResourceLocation texturelaser = new ResourceLocation("arpg:textures/ice_beam.png");
      static ResourceLocation largesmoke = new ResourceLocation("arpg:textures/largecloud.png");

      public Frost(World world) {
         super(world, 1.0F, 1.0F);
         this.hurtSound = Sounds.frost_hurt;
         this.deathSound = Sounds.frost_dead;
         this.livingSound = Sounds.frost_living;
         this.defaultteam = EverfrostMobsPack.mobsteam;
         this.setattributes(25.0, 42.0, 5.0, 0.5, 3.0, 0.5, 0.0, 0.0, 0.0, 0.0);
         this.registerLOOT(
            new MobDrop[]{
               new MobDrop(ItemsRegister.ICEGEM, 0.3F, 0, 1, 1, 1), new MobDrop(Items.SNOWBALL, 0.5F, 0, 1, 2, 4)
            }
         );
         this.setNoGravity(true);
         this.setRoleValues(EnumMobRole.MIDDLE_ENEMY, 2);
         this.soul = Soul.WINTRY;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.5) {
            ShardType.spawnShards(ShardType.COLD, this, 3.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.5) {
            ShardType.spawnShards(ShardType.WATER, this, 3.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.ICE_BLOOD;
      }

      public boolean isPotionApplicable(PotionEffect potioneffectIn) {
         return potioneffectIn.getPotion() == PotionEffects.FREEZING ? false : super.isPotionApplicable(potioneffectIn);
      }

      public void fall(float distance, float damageMultiplier) {
      }

      @SideOnly(Side.CLIENT)
      public void spawnBetwParticle(Vec3d from, Vec3d to) {
         if (from.lengthSquared() > 1.0E-6 && to.lengthSquared() > 1.0E-6) {
            BetweenParticle laser = new BetweenParticle(
               this.world, texturelaser, 0.23F, 240, 1.0F, 1.0F, 1.0F, 0.15F, from.distanceTo(to), 3, 0.5F, 4.0F, from, to
            );
            laser.ignoreFrustumCheck = true;
            laser.setPosition(from.x, from.y, from.z);
            laser.alphaGlowing = true;
            this.world.spawnEntity(laser);

            for (int ss = 0; ss < 3; ss++) {
               GUNParticle bigsmoke = new GUNParticle(
                  largesmoke,
                  0.4F + (float)this.rand.nextGaussian() / 20.0F,
                  0.0F,
                  12,
                  180,
                  this.world,
                  to.x,
                  to.y,
                  to.z,
                  (float)this.rand.nextGaussian() / 23.0F,
                  (float)this.rand.nextGaussian() / 27.0F,
                  (float)this.rand.nextGaussian() / 23.0F,
                  0.8F,
                  1.0F,
                  1.0F,
                  true,
                  this.rand.nextInt(360)
               );
               bigsmoke.alphaGlowing = true;
               bigsmoke.alphaTickAdding = -0.07F;
               this.world.spawnEntity(bigsmoke);
            }

            for (int ss = 0; ss < 4; ss++) {
               GUNParticle sparkle = new GUNParticle(
                  texture,
                  0.1F + (float)this.rand.nextGaussian() / 30.0F,
                  0.01F,
                  10,
                  240,
                  this.world,
                  to.x,
                  to.y,
                  to.z,
                  (float)this.rand.nextGaussian() / 14.0F,
                  (float)this.rand.nextGaussian() / 14.0F + 0.15F,
                  (float)this.rand.nextGaussian() / 14.0F,
                  0.8F + (float)this.rand.nextGaussian() / 5.0F,
                  1.0F,
                  1.0F,
                  false,
                  0
               );
               this.world.spawnEntity(sparkle);
            }
         }
      }

      @Override
      public void onLaserTick(Vec3d vec, int attackDuration) {
         if (this.ticksExisted % 10 == 0 && !this.world.isRemote) {
            double radius = 0.3;
            AxisAlignedBB aabb = new AxisAlignedBB(
               vec.x - radius,
               vec.y - radius,
               vec.z - radius,
               vec.x + radius,
               vec.y + radius,
               vec.z + radius
            );
            List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, aabb);
            if (!list.isEmpty()) {
               for (EntityLivingBase entitylivingbase : list) {
                  if (entitylivingbase != this) {
                     float damg = (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
                     Weapons.dealDamage(
                        new WeaponDamage(null, this, null, false, false, this, WeaponDamage.frost),
                        damg,
                        this,
                        entitylivingbase,
                        true,
                        1.0F,
                        vec.x,
                        vec.y,
                        vec.z
                     );
                     entitylivingbase.hurtResistantTime = 0;
                     int timeAmount = 30 + this.getMobDifficulty() * 15;
                     PotionEffect baff = new PotionEffect(MobEffects.SLOWNESS, timeAmount, 1);
                     entitylivingbase.addPotionEffect(baff);
                  }
               }
            }
         }

         this.world.setEntityState(this, (byte)9);
      }

      @Override
      public void onUpdate() {
         super.onUpdate();
         if (this.world.isRemote) {
            GUNParticle snow = new GUNParticle(
               texture,
               0.07F + this.rand.nextFloat() / 10.0F,
               0.01F,
               25 + this.rand.nextInt(5),
               -1,
               this.world,
               this.posX + (this.rand.nextDouble() - 0.5) * this.width,
               this.posY + this.rand.nextDouble() * this.height,
               this.posZ + (this.rand.nextDouble() - 0.5) * this.width,
               (this.rand.nextFloat() - 0.5F) * 0.02F,
               -this.rand.nextFloat() * 0.02F,
               (this.rand.nextFloat() - 0.5F) * 0.02F,
               0.9F + (float)this.rand.nextGaussian() / 10.0F,
               1.0F,
               1.0F,
               false,
               this.rand.nextInt(100) - 50,
               true,
               3.0F
            );
            snow.scaleTickAdding = -0.004F;
            this.world.spawnEntity(snow);
         }
      }

      @SideOnly(Side.CLIENT)
      @Override
      public void handleStatusUpdate(byte id) {
         super.handleStatusUpdate(id);
         if (id == 9) {
            Vec3d vec = GetMOP.PosRayTrace(15.0, 1.0F, this, 0.05, 0.05);
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.frost_shoot,
                  SoundCategory.AMBIENT,
                  0.7F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );
            this.spawnBetwParticle(this.getPositionVector().add(0.0, this.height / 2.0F, 0.0), vec);
         }
      }

      protected void initEntityAI() {
         this.tasks.addTask(1, new EntityAIFlying(this, 150, 10.0F, 0.04F, false));
         this.tasks.addTask(2, new EntityAIFlyingKeepDistToTarget(this, 60, 8.0F, 0.3F, 2.0F, 2.0F, 1.0F, 1.05F));
         this.tasks.addTask(2, new EntityAILongLaserAttack(this, 50, 15.0, 0.05, 15, false));
         this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(2, new EntityAIAgree(this, EntityPlayer.class, true));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }
   }

   public static class Gargoyle extends AbstractMob {
      public float friction = 0.97F;

      public Gargoyle(World world) {
         super(world, 0.75F, 1.7F);
         this.hurtSound = Sounds.mob_ice_hurt;
         this.deathSound = Sounds.mob_ice_death;
         this.livingSound = Sounds.mob_ice_living;
         this.defaultteam = EverfrostMobsPack.mobsteam;
         this.setattributes(40.0, 48.0, 8.0, 0.09, 3.0, 3.0, 0.6, 0.1, 0.0, 0.0);
         this.registerLOOT(new MobDrop[]{new MobDrop(Item.getItemFromBlock(Blocks.OBSIDIAN), 0.55F, 0, 0, 2, 1)});
         this.setNoGravity(true);
         this.setRoleValues(EnumMobRole.STRONG_SOLDIER, 2);
         this.soul = Soul.PALE;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.65) {
            ShardType.spawnShards(ShardType.EARTH, this, 2.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.GARGOYLE_BLOOD;
      }

      public void fall(float distance, float damageMultiplier) {
      }

      @Override
      public void onUpdate() {
         super.onUpdate();
         this.motionX = this.motionX * this.friction;
         this.motionY = this.motionY * this.friction;
         this.motionZ = this.motionZ * this.friction;
      }

      protected void initEntityAI() {
         this.tasks.addTask(1, new EntityAIRayLogicFly(this));
         this.tasks
            .addTask(2, new EntityAIAttackSweep(this, 40, 0.5F, 0.5F, 4.3F, 10, true, 4.0F, 10.0F, Sounds.melee_block).setTriggerOnStart());
         this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
         this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
         this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }
   }

   public static class Gelum extends AbstractMob implements ILongLaserAttackMob {
      static ResourceLocation texture = new ResourceLocation("arpg:textures/snowflake2.png");
      static ResourceLocation texturelaser = new ResourceLocation("arpg:textures/ice_beam.png");
      static ResourceLocation largesmoke = new ResourceLocation("arpg:textures/largecloud.png");

      public Gelum(World world) {
         super(world, 1.65F, 1.65F);
         this.hurtSound = Sounds.mob_ice_hurt;
         this.deathSound = Sounds.mob_ice_death;
         this.livingSound = Sounds.frost_living;
         this.defaultteam = EverfrostMobsPack.mobsteam;
         this.setattributes(55.0, 40.0, 6.0, 0.13, 3.0, 1.0, 0.4, 0.0, 0.0, 0.0);
         this.registerLOOT(new MobDrop[]{new MobDrop(ItemsRegister.ICEGEM, 0.6F, 0, 1, 1, 1)});
         this.setNoGravity(true);
         this.setRoleValues(EnumMobRole.STRONG_ENEMY, 2);
         this.soul = Soul.WINTRY;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.8) {
            ShardType.spawnShards(ShardType.COLD, this, 3.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.8) {
            ShardType.spawnShards(ShardType.AIR, this, 3.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.SNOW_BLOOD;
      }

      public boolean isPotionApplicable(PotionEffect potioneffectIn) {
         return potioneffectIn.getPotion() == PotionEffects.FREEZING ? false : super.isPotionApplicable(potioneffectIn);
      }

      public void fall(float distance, float damageMultiplier) {
      }

      @SideOnly(Side.CLIENT)
      public void spawnBetwParticle(Vec3d from, Vec3d to) {
         if (from.lengthSquared() > 1.0E-6 && to.lengthSquared() > 1.0E-6) {
            BetweenParticle laser = new BetweenParticle(
               this.world, texturelaser, 0.23F, 240, 1.0F, 1.0F, 1.0F, 0.15F, from.distanceTo(to), 3, 0.5F, 4.0F, from, to
            );
            laser.ignoreFrustumCheck = true;
            laser.setPosition(from.x, from.y, from.z);
            laser.alphaGlowing = true;
            this.world.spawnEntity(laser);

            for (int ss = 0; ss < 3; ss++) {
               GUNParticle bigsmoke = new GUNParticle(
                  largesmoke,
                  0.4F + (float)this.rand.nextGaussian() / 20.0F,
                  0.0F,
                  12,
                  180,
                  this.world,
                  to.x,
                  to.y,
                  to.z,
                  (float)this.rand.nextGaussian() / 23.0F,
                  (float)this.rand.nextGaussian() / 27.0F,
                  (float)this.rand.nextGaussian() / 23.0F,
                  0.8F,
                  1.0F,
                  1.0F,
                  true,
                  this.rand.nextInt(360)
               );
               bigsmoke.alphaGlowing = true;
               bigsmoke.alphaTickAdding = -0.07F;
               this.world.spawnEntity(bigsmoke);
            }

            for (int ss = 0; ss < 4; ss++) {
               GUNParticle sparkle = new GUNParticle(
                  texture,
                  0.1F + (float)this.rand.nextGaussian() / 30.0F,
                  0.01F,
                  10,
                  240,
                  this.world,
                  to.x,
                  to.y,
                  to.z,
                  (float)this.rand.nextGaussian() / 14.0F,
                  (float)this.rand.nextGaussian() / 14.0F + 0.15F,
                  (float)this.rand.nextGaussian() / 14.0F,
                  0.8F + (float)this.rand.nextGaussian() / 5.0F,
                  1.0F,
                  1.0F,
                  false,
                  0
               );
               this.world.spawnEntity(sparkle);
            }
         }
      }

      @Override
      public void onLaserTick(Vec3d vec, int attackDuration) {
         if (this.ticksExisted % 10 == 0 && !this.world.isRemote) {
            double radius = 0.3;
            AxisAlignedBB aabb = new AxisAlignedBB(
               vec.x - radius,
               vec.y - radius,
               vec.z - radius,
               vec.x + radius,
               vec.y + radius,
               vec.z + radius
            );
            List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, aabb);
            if (!list.isEmpty()) {
               for (EntityLivingBase entitylivingbase : list) {
                  if (entitylivingbase != this) {
                     float damg = (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
                     Weapons.dealDamage(
                        new WeaponDamage(null, this, null, false, false, this, WeaponDamage.frost),
                        damg,
                        this,
                        entitylivingbase,
                        true,
                        1.0F,
                        vec.x,
                        vec.y,
                        vec.z
                     );
                     entitylivingbase.hurtResistantTime = 0;
                     int timeAmount = 60 + this.getMobDifficulty() * 20;
                     PotionEffect lastdebaff = Weapons.mixPotion(
                        entitylivingbase,
                        PotionEffects.FREEZING,
                        (float)timeAmount,
                        1.0F,
                        Weapons.EnumPotionMix.WITH_MAXIMUM,
                        Weapons.EnumPotionMix.WITH_MAXIMUM,
                        Weapons.EnumMathOperation.PLUS,
                        Weapons.EnumMathOperation.PLUS,
                        250,
                        10
                     );
                     Freezing.tryPlaySound(entitylivingbase, lastdebaff);
                     if (entitylivingbase.getHealth() <= 0.0F && this.rand.nextFloat() < 0.5) {
                        DeathEffects.applyDeathEffect(entitylivingbase, DeathEffects.DE_ICING);
                     }
                  }
               }
            }
         }

         this.world.setEntityState(this, (byte)9);
      }

      public void addPotionEffect(PotionEffect potioneffectIn) {
         if (potioneffectIn.getPotion() != PotionEffects.FREEZING) {
            super.addPotionEffect(potioneffectIn);
         }
      }

      @Override
      public void onUpdate() {
         super.onUpdate();
         if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)8);
         }
      }

      @SideOnly(Side.CLIENT)
      @Override
      public void handleStatusUpdate(byte id) {
         super.handleStatusUpdate(id);
         if (id == 8) {
            GUNParticle snow = new GUNParticle(
               texture,
               0.07F + this.rand.nextFloat() / 10.0F,
               0.01F,
               25 + this.rand.nextInt(5),
               -1,
               this.world,
               this.posX + (this.rand.nextDouble() - 0.5) * this.width,
               this.posY + this.rand.nextDouble() * this.height,
               this.posZ + (this.rand.nextDouble() - 0.5) * this.width,
               (this.rand.nextFloat() - 0.5F) * 0.02F,
               -this.rand.nextFloat() * 0.02F,
               (this.rand.nextFloat() - 0.5F) * 0.02F,
               0.9F + (float)this.rand.nextGaussian() / 10.0F,
               1.0F,
               1.0F,
               false,
               this.rand.nextInt(100) - 50,
               true,
               3.0F
            );
            snow.scaleTickAdding = -0.004F;
            this.world.spawnEntity(snow);
         }

         if (id == 9) {
            Vec3d vec = GetMOP.PosRayTrace(15.0, 1.0F, this, 0.05, 0.05);
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.frost_shoot,
                  SoundCategory.AMBIENT,
                  0.7F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );
            this.spawnBetwParticle(this.getPositionVector().add(0.0, this.height / 2.0F, 0.0), vec);
         }
      }

      protected void initEntityAI() {
         this.tasks.addTask(1, new EntityAIFlying(this, 150, 12.0F, 0.02F, false));
         this.tasks.addTask(2, new EntityAIFlyingKeepDistToTarget(this, 100, 10.0F, 0.17F, 2.0F, 2.0F, 1.0F, 1.08F));
         this.tasks.addTask(2, new EntityAILongLaserAttack(this, 70, 15.0, 0.07, 25, false));
         this.tasks.addTask(4, new EntityAICorrupterIdle(this));
         this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
         this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }
   }

   public static class HailWraith extends AbstractMob implements IEntitySynchronize {
      public static ResourceLocation texture = new ResourceLocation("arpg:textures/lightning3.png");
      int zapCooldown = 0;

      public HailWraith(World world) {
         super(world, 0.75F, 1.75F);
         this.hurtSound = Sounds.mob_ice_hurt;
         this.deathSound = Sounds.mob_ice_death;
         this.livingSound = Sounds.mob_ice_living;
         this.defaultteam = EverfrostMobsPack.mobsteam;
         this.setattributes(45.0, 30.0, 6.0, 0.09, 2.0, 2.0, 0.2, 0.1, 0.0, 0.0);
         this.registerLOOT(
            new MobDrop[]{
               new MobDrop(ItemsRegister.WEATHERFRAGMENTS, 0.35F, 0, 1, 1, 0), new MobDrop(ItemsRegister.HAILTEAR, 0.1F, 0, 1, 2, 2)
            }
         );
         this.setRoleValues(EnumMobRole.MIDDLE_ENEMY, 2);
         this.soul = Soul.WINTRY;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.3) {
            ShardType.spawnShards(ShardType.COLD, this, 2.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.7) {
            ShardType.spawnShards(ShardType.AIR, this, 3.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.5) {
            ShardType.spawnShards(ShardType.ELECTRIC, this, 2.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.ICE_BLOOD;
      }

      @Override
      public boolean attackEntityFrom(DamageSource source, float amount) {
         if (source.getImmediateSource() != null && source.getImmediateSource() instanceof HostileProjectiles.Hailblast) {
            amount += 7.0F;
         }

         return super.attackEntityFrom(source, amount);
      }

      public void fall(float distance, float damageMultiplier) {
      }

      @Override
      public void shoot() {
         if (!this.world.isRemote) {
            HostileProjectiles.Hailblast entity = new HostileProjectiles.Hailblast(this.world, this);
            entity.shoot(this, this.rotationPitch - 8.0F, this.rotationYaw, 0.0F, 1.1F, 2.5F);
            entity.damage = 5.0F;
            entity.spawnFragmentChance = 0.0F;
            this.world.spawnEntity(entity);
            this.world.setEntityState(this, (byte)8);
         }
      }

      @Override
      public void handleStatusUpdate(byte id) {
         super.handleStatusUpdate(id);
         if (id == 8) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.magic_n,
                  SoundCategory.HOSTILE,
                  0.8F,
                  0.8F + this.rand.nextFloat() / 2.5F,
                  false
               );
         }
      }

      @Override
      public void onUpdate() {
         super.onUpdate();
         if (this.getAttackTarget() != null) {
            EntityLivingBase attarg = this.getAttackTarget();
            double d0 = this.getDistanceSq(attarg.posX, attarg.posY, attarg.posZ);
            this.zapCooldown--;
            if (d0 <= 15.0
               && this.zapCooldown <= 0
               && GetMOP.thereIsNoBlockCollidesBetween(this.world, GetMOP.entityCenterPos(this), GetMOP.entityCenterPos(attarg), 1.0F, null, true)) {
               this.zapCooldown = 60;
               IEntitySynchronize.sendSynchronize(
                  this,
                  32.0,
                  this.posX,
                  this.posY + this.height / 2.0F,
                  this.posZ,
                  attarg.posX,
                  attarg.posY + attarg.height / 2.0F,
                  attarg.posZ
               );
               IAttributeInstance entityAttribute = attarg.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE);
               double baseValue = entityAttribute.getBaseValue();
               entityAttribute.setBaseValue(1.0);
               attarg.attackEntityFrom(DamageSource.causeMobDamage(this), (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
               attarg.hurtResistantTime = 0;
               entityAttribute.setBaseValue(baseValue);
            }
         }
      }

      @Override
      public void onClient(double x, double y, double z, double a, double b, double c) {
         Vec3d pos2 = new Vec3d(x, y, z);
         Vec3d pos1 = new Vec3d(a, b, c);
         BetweenParticle laser = new BetweenParticle(
            this.world,
            texture,
            0.1F + this.rand.nextFloat() / 5.0F,
            240,
            0.6F,
            1.0F,
            0.8F,
            0.1F,
            pos1.distanceTo(pos2),
            10,
            0.2F,
            9999.0F,
            pos1,
            pos2
         );
         laser.alphaGlowing = true;
         laser.setPosition(pos1.x, pos1.y, pos1.z);
         this.world.spawnEntity(laser);
         this.world
            .playSound(
               this.posX,
               this.posY,
               this.posZ,
               Sounds.electric_arc,
               SoundCategory.HOSTILE,
               0.7F,
               0.8F + this.rand.nextFloat() / 2.5F,
               false
            );
      }

      public boolean isPotionApplicable(PotionEffect potioneffectIn) {
         return potioneffectIn.getPotion() == PotionEffects.FREEZING ? false : super.isPotionApplicable(potioneffectIn);
      }

      protected void initEntityAI() {
         this.tasks.addTask(0, new EntityAISwimming(this));
         this.tasks.addTask(1, new EntityAIFloatingSkeleton(this, 110, 20.0F, 5, true, 8.0F, 3.0F, 1.25F, 0.2F, false, false));
         this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
         this.tasks.addTask(8, new EntityAILookIdle(this));
         this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
         this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }
   }

   public static class HarridanOfIce extends AbstractMob {
      public static ResourceLocation snow1 = new ResourceLocation("arpg:textures/snowflake.png");

      public HarridanOfIce(World world) {
         super(world, 0.85F, 2.45F);
         this.hurtSound = Sounds.frost_hurt;
         this.deathSound = Sounds.mob_ghost_death;
         this.livingSound = Sounds.frost_living;
         this.defaultteam = EverfrostMobsPack.mobsteam;
         this.setattributes(60.0, 64.0, 2.0, 0.2, 4.0, 5.0, 0.3, 0.0, 0.0, 0.0);
         this.registerLOOT(
            new MobDrop[]{
               new MobDrop(ItemsRegister.ICEGEM, 0.5F, 0, 1, 1, 1), new MobDrop(Items.SNOWBALL, 0.5F, 0, 1, 2, 4)
            }
         );
         this.stepHeight = 1.1F;
         this.setRoleValues(EnumMobRole.STRONG_ENEMY, 2);
         this.soul = Soul.WINTRY;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.8) {
            ShardType.spawnShards(ShardType.COLD, this, 3.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.8) {
            ShardType.spawnShards(ShardType.WATER, this, 3.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.ICE_BLOOD;
      }

      public float getEyeHeight() {
         return this.height * 0.83F;
      }

      public float getAIMoveSpeed() {
         return this.getAttackTarget() != null && this.getAttackTarget().getDistanceSq(this) <= 30.0 ? super.getAIMoveSpeed() * 0.5F : super.getAIMoveSpeed();
      }

      @Override
      public void onUpdate() {
         super.onUpdate();
         if (!this.world.isRemote) {
            if (this.ticksExisted % 30 == 0) {
               for (Entity entity : GetMOP.getEntitiesInAABBof(this.world, this, 10.0, this)) {
                  double distSq = this.getDistanceSq(entity);
                  if (distSq < 100.0) {
                     if (Team.checkIsOpponent(this, entity)) {
                        if (entity instanceof EntityPlayer) {
                           EntityPlayer player = (EntityPlayer)entity;
                           if (!Freezing.canImmobilizeEntity(player, player.getActivePotionEffect(PotionEffects.FREEZING))) {
                              PotionEffect lastdebaff = Weapons.mixPotion(
                                 player,
                                 PotionEffects.FREEZING,
                                 50.0F,
                                 1.0F,
                                 Weapons.EnumPotionMix.WITH_MAXIMUM,
                                 Weapons.EnumPotionMix.WITH_MAXIMUM,
                                 Weapons.EnumMathOperation.PLUS,
                                 Weapons.EnumMathOperation.PLUS,
                                 130,
                                 8
                              );
                              Freezing.tryPlaySound(player, lastdebaff);
                           }
                        } else {
                           PotionEffect lastdebaff = Weapons.mixPotion(
                              entity,
                              PotionEffects.FREEZING,
                              50.0F,
                              1.0F,
                              Weapons.EnumPotionMix.WITH_MAXIMUM,
                              Weapons.EnumPotionMix.WITH_MAXIMUM,
                              Weapons.EnumMathOperation.PLUS,
                              Weapons.EnumMathOperation.PLUS,
                              130,
                              8
                           );
                           Freezing.tryPlaySound(entity, lastdebaff);
                        }

                        if (distSq < 25.0) {
                           Weapons.dealDamage(
                              new WeaponDamage(null, this, null, false, false, this, WeaponDamage.frost),
                              (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue(),
                              this,
                              entity,
                              true
                           );
                           DeathEffects.applyDeathEffect(entity, DeathEffects.DE_ICING, 0.8F);
                        }
                     } else {
                        Weapons.setPotionIfEntityLB(entity, MobEffects.RESISTANCE, 50, 1);
                     }
                  }
               }
            }
         } else if (this.ticksExisted % 5 == 0) {
            ParticleTracker.TrackerFollowDynamicPoint tracker = new ParticleTracker.TrackerFollowDynamicPoint(
               this, false, 1.0F, 0.0F + Debugger.floats[0], 0.05F + Debugger.floats[1]
            );
            tracker.frictionMult = 1.0F;
            tracker.tickfrictionAdd = -0.001F;

            for (int i = 0; i < 4; i++) {
               int yawRand = this.rand.nextInt(360);
               Vec3d poss = GetMOP.YawToVec3d(yawRand).scale(10.0).add(GetMOP.entityCenterPos(this));
               Vec3d direction = GetMOP.YawToVec3d(yawRand + 90).scale(0.5);
               float scl = 0.2F + this.rand.nextFloat() * 0.1F;
               int lt = 60 + this.rand.nextInt(10);
               GUNParticle part = new GUNParticle(
                  snow1,
                  scl,
                  0.0F,
                  lt,
                  190,
                  this.world,
                  poss.x,
                  poss.y,
                  poss.z,
                  (float)direction.x,
                  (float)direction.y,
                  (float)direction.z,
                  0.8F + this.rand.nextFloat() * 0.1F,
                  0.85F + this.rand.nextFloat() * 0.15F,
                  1.0F,
                  false,
                  this.rand.nextInt(360)
               );
               part.scaleTickAdding = -scl / lt / 1.5F;
               part.tracker = tracker;
               this.world.spawnEntity(part);
            }
         }
      }

      public boolean isPotionApplicable(PotionEffect potioneffectIn) {
         return potioneffectIn.getPotion() == PotionEffects.FREEZING ? false : super.isPotionApplicable(potioneffectIn);
      }

      protected void initEntityAI() {
         this.tasks.addTask(0, new EntityAISwimming(this));
         this.tasks.addTask(2, new EntityAISkeleton(this, 1.0, 40, 0.0F, 1));
         this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0));
         this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0));
         this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
         this.tasks.addTask(8, new EntityAILookIdle(this));
         this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(2, new EntityAIAgree(this, EntityPlayer.class, true));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }
   }

   public static class IceApparition extends AbstractMob {
      public static ResourceLocation cloud = new ResourceLocation("arpg:textures/impetus.png");
      public static ResourceLocation cloud2 = new ResourceLocation("arpg:textures/clouds3.png");

      public IceApparition(World world) {
         super(world, 0.75F, 2.15F);
         this.hurtSound = Sounds.mob_ghost_hurt;
         this.deathSound = Sounds.mob_ghost_death;
         this.livingSound = Sounds.mob_ghost_living;
         this.defaultteam = EverfrostMobsPack.mobsteam;
         this.setattributes(75.0, 42.0, 5.0, 0.11, 4.0, 4.0, 0.6, 0.15, 0.0, 0.0);
         this.registerLOOT(
            new MobDrop[]{
               new MobDrop(ItemsRegister.ICEGEM, 0.95F, 0, 0, 3, 2), new MobDrop(ItemsRegister.SNOWCLOTH, 0.35F, 0, 1, 3, 2)
            }
         );
         this.setRoleValues(EnumMobRole.ELITE_ENEMY, 2);
         this.soul = Soul.WINTRY;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.9) {
            ShardType.spawnShards(ShardType.COLD, this, 3.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.9) {
            ShardType.spawnShards(ShardType.WATER, this, 4.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.8) {
            ShardType.spawnShards(ShardType.DEATH, this, 3.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.FROSTED_SKELETON_BLOOD;
      }

      @Override
      public boolean attackEntityAsMob(Entity entityIn) {
         if (this.rand.nextFloat() < 0.5F) {
            Weapons.setPotionIfEntityLB(entityIn, PotionEffects.FROSTBURN, 200, 0);
         }

         return super.attackEntityAsMob(entityIn);
      }

      public boolean isPotionApplicable(PotionEffect potioneffectIn) {
         if (potioneffectIn.getPotion() == PotionEffects.FREEZING) {
            return false;
         } else if (potioneffectIn.getPotion() == PotionEffects.WINTER_CURSE) {
            return false;
         } else {
            return potioneffectIn.getPotion() == PotionEffects.FROSTBURN ? false : super.isPotionApplicable(potioneffectIn);
         }
      }

      @Override
      public void shoot() {
         if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)8);

            for (int i = 0; i < 8; i++) {
               HostileProjectiles.IceShardShoot entity = new HostileProjectiles.IceShardShoot(this.world, this);
               entity.shoot(this, this.rotationPitch - 10.0F, this.rotationYaw, 0.0F, 1.1F, 8.5F);
               entity.damage = 3.0F;
               this.world.spawnEntity(entity);
            }
         }
      }

      public void fall(float distance, float damageMultiplier) {
      }

      public EnumCreatureAttribute getCreatureAttribute() {
         return EnumCreatureAttribute.UNDEAD;
      }

      @Override
      public void handleStatusUpdate(byte id) {
         super.handleStatusUpdate(id);
         if (id == 8) {
            for (int i = 0; i < 5; i++) {
               this.world
                  .playSound(
                     this.posX,
                     this.posY,
                     this.posZ,
                     Sounds.crystal_fan,
                     SoundCategory.HOSTILE,
                     1.1F,
                     1.3F + this.rand.nextFloat() / 4.0F,
                     false
                  );
            }
         }

         if (id == 11) {
            for (int i = 0; i < 5; i++) {
               GUNParticle part = new GUNParticle(
                  cloud,
                  0.7F - i / 10.0F,
                  0.0F,
                  20,
                  240,
                  this.world,
                  this.posX,
                  this.posY + this.getEyeHeight(),
                  this.posZ,
                  0.0F,
                  0.0F,
                  0.0F,
                  0.5F,
                  0.8F + this.rand.nextFloat() / 10.0F,
                  0.9F + this.rand.nextFloat() / 10.0F,
                  true,
                  this.rand.nextInt(360)
               );
               part.shoot(this, this.rotationPitch, this.rotationYaw, 0.0F, 1.4F - i / 8.0F, 0.0F);
               part.rotationPitchYaw = new Vec2f(this.rotationPitch, this.rotationYaw);
               part.alphaTickAdding = -0.04F - i * 0.01F;
               part.alphaGlowing = true;
               part.scaleTickAdding = 0.07F - i * 0.01F;
               this.world.spawnEntity(part);
            }

            for (int i = 0; i < 6; i++) {
               GUNParticle part = new GUNParticle(
                  cloud2,
                  0.3F + this.rand.nextFloat() / 3.0F,
                  0.0F,
                  20,
                  240,
                  this.world,
                  this.posX,
                  this.posY + this.getEyeHeight(),
                  this.posZ,
                  0.0F,
                  0.0F,
                  0.0F,
                  0.7F,
                  0.8F + this.rand.nextFloat() / 10.0F,
                  0.9F + this.rand.nextFloat() / 10.0F,
                  true,
                  this.rand.nextInt(360)
               );
               part.shoot(this, this.rotationPitch, this.rotationYaw, 0.0F, 1.3F - i / 8.0F, 6.0F);
               part.alphaTickAdding = -0.04F;
               part.alphaGlowing = true;
               part.scaleTickAdding = 0.07F - i * 0.01F;
               this.world.spawnEntity(part);
            }
         }
      }

      protected void initEntityAI() {
         this.tasks.addTask(0, new EntityAISwimming(this));
         this.tasks.addTask(1, new EntityAIFloatingSkeleton(this, 60, 13.0F, 8, true, 6.0F, 4.0F, 1.5F, 0.23F, true, true));
         this.tasks.addTask(2, new EntityAIAABBAttack(this, 20, 0.4F));
         this.tasks.addTask(3, new EntityAIForceAttack(this, 5.0F, 70, 3.0F, 0.0F, 3.0F, 1.0F, 4.0F, 1.0F).setSoundOnAttack(Sounds.impetus_2));
         this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
         this.tasks.addTask(8, new EntityAILookIdle(this));
         this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
         this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }
   }

   public static class IceCube extends AbstractMob {
      public float jumpscale = 0.0F;
      public float slimesize = 1.0F;

      public IceCube(World world) {
         super(world, 0.8F, 0.8F);
         this.slimesize = 0.8F * (0.7F + this.rand.nextFloat() / 1.5F);
         this.setSize(this.slimesize, this.slimesize);
         this.hurtSound = SoundEvents.BLOCK_SLIME_STEP;
         this.deathSound = Sounds.mob_ice_death;
         this.defaultteam = EverfrostMobsPack.mobsteam;
         this.setattributes(15.0, 40.0, 4.0, 0.5, 0.0, 0.0, 0.0, 0.0, 0.0, 0.2);
         this.registerLOOT(new MobDrop[]{new MobDrop(Items.SNOWBALL, 0.8F, 0, 0, 3, 8)});
         this.setRoleValues(EnumMobRole.SWARMER, 2);
         this.soul = Soul.PALE;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.3) {
            ShardType.spawnShards(ShardType.COLD, this, 1.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.3) {
            ShardType.spawnShards(ShardType.WATER, this, 1.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.ICE_BLOOD;
      }

      protected void jump() {
         this.triggerAnimation(-3);
         super.jump();
      }

      public void fall(float distance, float damageMultiplier) {
         distance -= 2.0F;
         if (distance < 0.0F) {
            distance = 0.0F;
         }

         super.fall(distance, damageMultiplier);
      }

      public boolean canBreatheUnderwater() {
         return true;
      }

      protected void initEntityAI() {
         this.tasks.addTask(1, new EntityAIJumpingMovement(this, 30, 0.1F));
         this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0, false));
         this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
         this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }

      @Override
      public void readEntityFromNBT(NBTTagCompound compound) {
         super.readEntityFromNBT(compound);
         this.slimesize = compound.getFloat("slimesize");
      }

      @Override
      public void writeEntityToNBT(NBTTagCompound compound) {
         super.writeEntityToNBT(compound);
         compound.setFloat("slimesize", this.slimesize);
      }
   }

   public static class IceWarrior extends AbstractMob {
      public IceWarrior(World world) {
         super(world, 0.65F, 1.85F);
         this.hurtSound = Sounds.mob_ice_hurt;
         this.deathSound = Sounds.mob_ice_death;
         this.livingSound = Sounds.mob_ice_living;
         this.defaultteam = EverfrostMobsPack.mobsteam;
         this.setattributes(40.0, 64.0, 8.0, 0.25, 3.0, 4.0, 0.5, 0.1, 0.0, 0.0);
         this.registerLOOT(new MobDrop[]{new MobDrop(ItemsRegister.ICEGEM, 0.12F, 0, 1, 1, 2)});
         this.setRoleValues(EnumMobRole.MIDDLE_ENEMY, 2);
         this.soul = Soul.WINTRY;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.6) {
            ShardType.spawnShards(ShardType.COLD, this, 2.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.ICE_BLOOD;
      }

      public boolean isPotionApplicable(PotionEffect potioneffectIn) {
         if (potioneffectIn.getPotion() == PotionEffects.FREEZING) {
            return false;
         } else {
            return potioneffectIn.getPotion() == PotionEffects.WINTER_CURSE ? false : super.isPotionApplicable(potioneffectIn);
         }
      }

      @Nullable
      @Override
      public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
         super.onInitialSpawn(difficulty, livingdata);
         ItemStack stack;
         if (this.rand.nextFloat() < 0.8F) {
            stack = new ItemStack(ItemsRegister.ICESWORD);
         } else if (this.rand.nextFloat() < 0.6F) {
            stack = new ItemStack(ItemsRegister.GOTHICAXE);
         } else if (this.rand.nextFloat() < 0.5F) {
            stack = new ItemStack(ItemsRegister.GOTHICSHOVEL);
         } else {
            stack = new ItemStack(ItemsRegister.GOTHICPICKAXE);
         }

         this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, stack);
         return livingdata;
      }

      @Override
      public boolean attackEntityAsMob(Entity entityIn) {
         if (this.rand.nextFloat() < 0.2F + this.getMobDifficulty() * 0.1F
            && entityIn instanceof EntityLivingBase
            && this.getHeldItemMainhand().getItem() == ItemsRegister.ICESWORD) {
            PotionEffect lastdebaff = Weapons.mixPotion(
               entityIn,
               PotionEffects.FREEZING,
               70.0F,
               2.0F,
               Weapons.EnumPotionMix.WITH_MAXIMUM,
               Weapons.EnumPotionMix.WITH_MAXIMUM,
               Weapons.EnumMathOperation.PLUS,
               Weapons.EnumMathOperation.PLUS,
               100,
               7
            );
            Freezing.tryPlaySound(entityIn, lastdebaff);
         }

         return super.attackEntityAsMob(entityIn);
      }

      protected void initEntityAI() {
         this.tasks.addTask(0, new EntityAISwimming(this));
         this.tasks
            .addTask(
               2,
               new EntityAIAttackSweep(this, 40, 0.7F, 1.0F, 2.3F, 13, true, 5.0F, 11.0F, Sounds.melee_block).setTriggerOnStart().setknockbackOnReflect(1.8F)
            );
         this.tasks.addTask(2, new EntityAIRush(this, false, false, false));
         this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0));
         this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0));
         this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
         this.tasks.addTask(8, new EntityAILookIdle(this));
         this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
         this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }
   }

   public static class NiveousSlider extends AbstractMob {
      public Entity lastAttackEntity;
      public BlockPos nexusPosition;
      public boolean goaled = false;
      static ResourceLocation laserOnSummon = new ResourceLocation("arpg:textures/laser_rifle_laser.png");
      static ResourceLocation hit = new ResourceLocation("arpg:textures/forge_hit_a.png");
      static ResourceLocation circle = new ResourceLocation("arpg:textures/circle.png");
      static ResourceLocation spellblue3 = new ResourceLocation("arpg:textures/spellblue3.png");
      public boolean spawnSummonParticle = true;

      public NiveousSlider(World world) {
         super(world, 0.875F, 0.375F);
         this.hurtSound = Sounds.slider_hit;
         this.deathSound = Sounds.slider_hit;
         this.defaultteam = EverfrostMobsPack.mobsteam;
         this.setattributes(200.0, 1.0, 0.0, 0.0, 0.0, 10.0, 0.0, 0.0, 0.0, 0.0);
         this.setRoleValues(EnumMobRole.SPECIAL_MOB, 2);
         this.soul = null;
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.SNOW_BLOOD;
      }

      @Override
      protected void playStepSound(BlockPos pos, Block blockIn) {
      }

      @Override
      public void writeEntityToNBT(NBTTagCompound compound) {
         super.writeEntityToNBT(compound);
         if (this.nexusPosition != null) {
            compound.setInteger("nexusposX", this.nexusPosition.getX());
            compound.setInteger("nexusposY", this.nexusPosition.getY());
            compound.setInteger("nexusposZ", this.nexusPosition.getZ());
         }
      }

      @Override
      public void readEntityFromNBT(NBTTagCompound compound) {
         super.readEntityFromNBT(compound);
         if (compound.hasKey("nexusposX") && compound.hasKey("nexusposY") && compound.hasKey("nexusposZ")) {
            this.nexusPosition = new BlockPos(compound.getInteger("nexusposX"), compound.getInteger("nexusposY"), compound.getInteger("nexusposZ"));
         }
      }

      public void onFallToHole() {
         if (!this.goaled) {
            if (this.nexusPosition != null) {
               TileEntity tile = this.world.getTileEntity(this.nexusPosition);
               if (tile instanceof TileNexusNiveolite) {
                  TileNexusNiveolite nexusNiveolite = (TileNexusNiveolite)tile;
                  nexusNiveolite.onGoal();
               }
            }

            this.goaled = true;
         }
      }

      public float getCollisionBorderSize() {
         return 0.2F;
      }

      @Override
      public boolean attackEntityFrom(DamageSource source, float amount) {
         boolean ret = super.attackEntityFrom(source, amount);
         if (ret && source.getTrueSource() != null) {
            this.lastAttackEntity = source.getTrueSource();
         }

         return ret;
      }

      @Override
      public void onUpdate() {
         if (this.goaled && !this.world.isRemote) {
            this.world.setEntityState(this, (byte)9);
            this.setDead();
         }

         super.onUpdate();
         if (!this.world.isRemote) {
            if (this.spawnSummonParticle) {
               this.world.setEntityState(this, (byte)11);
               this.spawnSummonParticle = false;
            }

            if (this.onGround) {
               if (this.ticksExisted % 20 == 0) {
                  BlockPos blockpos = new BlockPos(this.posX, this.getEntityBoundingBox().minY - 1.0, this.posZ);
                  IBlockState underState = this.world.getBlockState(blockpos);
                  Block block = underState.getBlock();
                  if (block == BlocksRegister.SNOWICE
                     || block == BlocksRegister.GLACIER
                     || block == Blocks.PACKED_ICE
                     || block == BlocksRegister.FROZENSTONE
                     || block == BlocksRegister.FROZENBRICKS
                     || block == BlocksRegister.FROZENCOBBLE) {
                     this.setHealth(this.getHealth() - 10.0F);
                     if (this.getHealth() <= 0.0F) {
                        this.setDead();
                     }
                  }
               }

               this.motionX /= 0.92F;
               this.motionZ /= 0.92F;
            }

            if (this.motionX > 0.0) {
               AxisAlignedBB aabb = this.getHitboxOnFacing(EnumFacing.EAST);
               if (this.world.collidesWithAnyBlock(aabb)) {
                  this.motionX = -this.motionX;
               }
            }

            if (this.motionX < 0.0) {
               AxisAlignedBB aabb = this.getHitboxOnFacing(EnumFacing.WEST);
               if (this.world.collidesWithAnyBlock(aabb)) {
                  this.motionX = -this.motionX;
               }
            }

            if (this.motionZ > 0.0) {
               AxisAlignedBB aabb = this.getHitboxOnFacing(EnumFacing.SOUTH);
               if (this.world.collidesWithAnyBlock(aabb)) {
                  this.motionZ = -this.motionZ;
               }
            }

            if (this.motionZ < 0.0) {
               AxisAlignedBB aabb = this.getHitboxOnFacing(EnumFacing.NORTH);
               if (this.world.collidesWithAnyBlock(aabb)) {
                  this.motionZ = -this.motionZ;
               }
            }

            float minspeed = 0.15F;
            float maxspeed = 1.0F;
            boolean spawnParticle = true;
            float movspeed = (float)GetMOP.getSpeed(this);
            if (movspeed >= minspeed) {
               List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(this.getCollisionBorderSize()));
               Collection<PotionEffect> effects = this.getActivePotionEffects();

               for (Entity entity : list) {
                  if (Weapons.dealDamage(
                        new WeaponDamage(null, this.lastAttackEntity, this, false, false, this, WeaponDamage.heavymelee),
                        GetMOP.getfromto(movspeed, minspeed, maxspeed) * 60.0F,
                        this.lastAttackEntity,
                        entity,
                        true,
                        (float)(movspeed * 0.6),
                        this.posX,
                        this.posY,
                        this.posZ
                     )
                     && spawnParticle) {
                     this.world.setEntityState(this, (byte)10);
                     spawnParticle = false;
                  }

                  if (!effects.isEmpty() && entity instanceof EntityLivingBase) {
                     EntityLivingBase entitylivingbase = (EntityLivingBase)entity;

                     for (PotionEffect effect : effects) {
                        PotionEffect eff = new PotionEffect(effect.getPotion(), (int)(effect.getDuration() * 0.7F), effect.getAmplifier());
                        entitylivingbase.addPotionEffect(eff);
                     }
                  }
               }
            }
         }
      }

      public AxisAlignedBB getHitboxOnFacing(EnumFacing facing) {
         float width2 = this.width * 0.8F;
         AxisAlignedBB aabb = this.getEntityBoundingBox().offset(facing.getXOffset() * this.width, 0.0, facing.getZOffset() * this.width);
         aabb = aabb.contract(facing.getXOffset() * width2, 0.0, facing.getZOffset() * width2);
         return aabb.expand(this.motionX, 0.0, this.motionZ);
      }

      public void fall(float distance, float damageMultiplier) {
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
                  Sounds.slider_summon,
                  SoundCategory.NEUTRAL,
                  3.0F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );
            ParticleFastSummon.round(this.world, this.getPositionVector(), 2.5F, 0.0F, 15, 240, 1.0F, 0.2F, 0.22F, 1, true);

            for (int ss = 0; ss < 5; ss++) {
               ParticleFastSummon.coloredLightning(
                  this.world,
                  0.1F,
                  7 + this.rand.nextInt(8),
                  200,
                  1.0F,
                  0.2F,
                  0.22F,
                  7,
                  this.getPositionVector().add(0.0, this.height / 2.0F, 0.0),
                  this.getPositionVector().add(this.rand.nextGaussian() * 0.7, 9.0, this.rand.nextGaussian() * 0.7),
                  0.7F,
                  0.0F,
                  ParticleFastSummon.rand
               );
            }

            for (int ss = 0; ss < 15; ss++) {
               GUNParticle bigsmoke = new GUNParticle(
                  spellblue3,
                  0.15F + this.rand.nextFloat() * 0.08F,
                  0.04F,
                  30,
                  200,
                  this.world,
                  this.posX,
                  this.posY + 0.1,
                  this.posZ,
                  (float)this.rand.nextGaussian() / 7.0F,
                  (float)this.rand.nextGaussian() / 7.0F + 0.1F,
                  (float)this.rand.nextGaussian() / 7.0F,
                  1.0F,
                  0.2F,
                  0.22F,
                  false,
                  this.rand.nextInt(360)
               );
               bigsmoke.scaleTickAdding = -0.004F;
               this.world.spawnEntity(bigsmoke);
            }
         }

         if (id == 10) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.slider_hit,
                  SoundCategory.NEUTRAL,
                  2.1F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );
            GUNParticle part = new GUNParticle(
               hit,
               0.01F,
               0.0F,
               6,
               220,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               0.0F,
               0.0F,
               0.0F,
               1.0F,
               1.0F,
               1.0F,
               true,
               this.rand.nextInt(360)
            );
            part.scaleTickAdding = 0.25F + this.rand.nextFloat() * 0.08F;
            part.alphaGlowing = true;
            this.world.spawnEntity(part);
         }

         if (id == 9) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.goal,
                  SoundCategory.NEUTRAL,
                  4.0F,
                  0.95F + this.rand.nextFloat() / 10.0F,
                  false
               );
            GUNParticle part = new GUNParticle(
               hit,
               0.01F,
               0.0F,
               7,
               220,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               0.0F,
               0.0F,
               0.0F,
               1.0F,
               0.2F,
               0.22F,
               true,
               this.rand.nextInt(360)
            );
            part.scaleTickAdding = 0.34F;
            part.alphaGlowing = true;
            this.world.spawnEntity(part);
            ParticleFastSummon.round(
               this.world, this.getPositionVector(), 0.1F, 2.75F + this.rand.nextFloat() * 1.25F, 13, 240, 0.7F, 0.88F, 1.0F, 1, true
            );
            ParticleFastSummon.round(
               this.world, this.getPositionVector(), 0.1F, 2.75F + this.rand.nextFloat() * 1.25F, 13, 240, 0.7F, 0.88F, 1.0F, 1, false
            );

            for (int ss = 0; ss < 25; ss++) {
               GUNParticle bigsmoke = new GUNParticle(
                  spellblue3,
                  0.16F + this.rand.nextFloat() * 0.08F,
                  0.04F,
                  30,
                  220,
                  this.world,
                  this.posX,
                  this.posY + 0.1,
                  this.posZ,
                  (float)this.rand.nextGaussian() / 5.0F,
                  (float)this.rand.nextGaussian() / 5.0F + 0.1F,
                  (float)this.rand.nextGaussian() / 5.0F,
                  1.0F,
                  1.0F,
                  1.0F,
                  false,
                  this.rand.nextInt(360),
                  true,
                  1.0F
               );
               bigsmoke.scaleTickAdding = -0.004F;
               this.world.spawnEntity(bigsmoke);
            }
         }
      }

      protected void initEntityAI() {
      }
   }

   public static class Snowrover extends AbstractMob {
      public boolean hasGun = false;
      public int gunTime = 0;
      public int gunCooldown = 0;
      public int gunCooldownMax = 60;
      public double distancewalked = 0.0;

      public Snowrover(World world) {
         super(world, 0.65F, 1.85F);
         this.hurtSound = Sounds.mob_ice_hurt;
         this.deathSound = Sounds.mob_ice_death;
         this.livingSound = Sounds.mob_ice_living;
         this.defaultteam = EverfrostMobsPack.mobsteam;
         this.setattributes(40.0, 64.0, 6.5, 0.26, 2.0, 1.0, 0.0, 0.0, 0.0, 0.0);
         this.registerLOOT(
            new MobDrop[]{
               new MobDrop(ItemsRegister.CONIFERSTICK, 0.34F, 0, 1, 2, 2), new MobDrop(Items.SNOWBALL, 0.95F, 0, 1, 4, 4)
            }
         );
         this.setRoleValues(EnumMobRole.SOLDIER, 2);
         this.soul = Soul.WINTRY;
      }

      @Override
      public void dropShards() {
         if (this.rand.nextFloat() < 0.3) {
            ShardType.spawnShards(ShardType.COLD, this, 1.0F * dropShardsQuantity);
         }

         if (this.rand.nextFloat() < 0.5) {
            ShardType.spawnShards(ShardType.AIR, this, 1.0F * dropShardsQuantity);
         }
      }

      @Override
      public BloodType getBloodType() {
         return DeathEffects.SNOW_BLOOD;
      }

      public float getEyeHeight() {
         return this.height * 0.75F;
      }

      @Override
      public void shoot() {
         if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)8);

            for (int i = 0; i < 5; i++) {
               CannonSnowball entity = new CannonSnowball(this.world, this);
               entity.setPosition(this.posX, this.posY + this.getEyeHeight() - 0.7, this.posZ);
               entity.shootBetter(this, this.rotationPitch - 11.0F, this.rotationYawHead, 0.0F, 0.8F, 7.8F);
               entity.damage = (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue() / 2.5F;
               this.world.spawnEntity(entity);
            }
         }
      }

      public float getAIMoveSpeed() {
         return this.hasGun && this.getAttackTarget() != null && this.getAttackTarget().getDistanceSq(this) <= 50.0
            ? super.getAIMoveSpeed() * 0.05F
            : super.getAIMoveSpeed();
      }

      @Override
      public void onUpdate() {
         super.onUpdate();
         if (this.hasGun && !this.world.isRemote) {
            if (this.ticksExisted < 3 || this.ticksExisted % 40 == 0) {
               this.world.setEntityState(this, (byte)9);
            }

            if (this.gunTime <= 0) {
               if (this.gunCooldown <= 0) {
                  if (this.getAttackTarget() != null
                     && this.getEntitySenses().canSee(this.getAttackTarget())
                     && this.getAttackTarget().getDistanceSq(this) <= 100.0) {
                     this.gunTime = 35;
                     this.gunCooldown = this.gunCooldownMax;
                     this.triggerAnimation(-2);
                     this.getLookHelper().setLookPositionWithEntity(this.getAttackTarget(), 50.0F, 70.0F);
                     this.faceEntity(this.getAttackTarget(), 50.0F, 70.0F);
                     this.world.setEntityState(this, (byte)7);
                  }
               } else {
                  this.gunCooldown--;
               }
            } else {
               this.gunTime--;
               if (this.gunTime == 1) {
                  this.shoot();
               } else if (this.getAttackTarget() != null && this.gunTime < 10) {
                  this.getLookHelper().setLookPositionWithEntity(this.getAttackTarget(), 50.0F, 70.0F);
                  this.faceEntity(this.getAttackTarget(), 50.0F, 70.0F);
               }
            }
         }

         if (this.world.isRemote) {
            double horizontalSpeed = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.prevDistanceWalkedModified = (float)(this.distancewalked * 0.6);
            this.distancewalked += horizontalSpeed;
            this.distanceWalkedModified = (float)(this.distancewalked * 0.6);
         }
      }

      @Override
      public void handleStatusUpdate(byte id) {
         super.handleStatusUpdate(id);
         if (id == 7) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.snowball_cannon_wooden,
                  SoundCategory.HOSTILE,
                  1.0F,
                  0.9F + this.rand.nextFloat() * 0.2F,
                  false
               );
         }

         if (id == 8) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.snowball_cannon,
                  SoundCategory.HOSTILE,
                  1.6F,
                  0.8F + this.rand.nextFloat() * 0.2F,
                  false
               );
         }

         if (id == 9) {
            this.hasGun = true;
         }
      }

      public boolean isPotionApplicable(PotionEffect potioneffectIn) {
         return potioneffectIn.getPotion() == PotionEffects.FREEZING ? false : super.isPotionApplicable(potioneffectIn);
      }

      @Nullable
      @Override
      public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
         super.onInitialSpawn(difficulty, livingdata);
         if (this.rand.nextFloat() < 0.15F) {
            this.hasGun = true;
         }

         return livingdata;
      }

      @Override
      public void readEntityFromNBT(NBTTagCompound compound) {
         super.readEntityFromNBT(compound);
         if (compound.hasKey("hasGun")) {
            this.hasGun = compound.getBoolean("hasGun");
         }
      }

      @Override
      public void writeEntityToNBT(NBTTagCompound compound) {
         super.writeEntityToNBT(compound);
         compound.setBoolean("hasGun", this.hasGun);
      }

      protected void initEntityAI() {
         this.tasks.addTask(0, new EntityAISwimming(this));
         this.tasks.addTask(2, new EntityAIRush(this, true, true, false));
         this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0));
         this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0));
         this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
         this.targetTasks.addTask(2, new EntityAIAgree(this, EntityPlayer.class, true));
         this.targetTasks.addTask(3, new EntityAIAttackOtherTeam(this, EntityLiving.class, true));
      }
   }
}
