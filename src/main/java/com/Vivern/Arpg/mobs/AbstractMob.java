package com.Vivern.Arpg.mobs;

import com.Vivern.Arpg.loot.Treasure;
import com.Vivern.Arpg.main.BloodType;
import com.Vivern.Arpg.main.Coins;
import com.Vivern.Arpg.main.DeathEffects;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.Main;
import com.Vivern.Arpg.main.Mana;
import com.Vivern.Arpg.main.MobRegister;
import com.Vivern.Arpg.main.MobSpawn;
import com.Vivern.Arpg.main.PropertiesRegistry;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.network.PacketHandler;
import com.Vivern.Arpg.network.PacketSmallSomethingToClients;
import com.Vivern.Arpg.recipes.Soul;
import com.Vivern.Arpg.renders.LayerRandomItem;
import com.Vivern.Arpg.renders.mobrender.IMultitexture;
import com.Vivern.Arpg.renders.mobrender.InitMobRenders;
import com.Vivern.Arpg.tileentity.TileMonsterSpawner;
import com.Vivern.Arpg.tileentity.TileNexus;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAITasks.EntityAITaskEntry;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.BlockPos.PooledMutableBlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class AbstractMob extends EntityMob implements IMultitexture {
   public static float dropShardsQuantity = 4.0F;
   public static float dropShardsChance = 0.4F;
   public double maxHealth;
   public double followRange;
   public double damage;
   public double moveSpeed;
   public double armor;
   public double protection;
   public double knockResist;
   public double knockback;
   public double vampirism;
   public double jumpHeight;
   public SoundEvent hurtSound = SoundEvents.ENTITY_HOSTILE_HURT;
   public SoundEvent deathSound = SoundEvents.ENTITY_HOSTILE_DEATH;
   public SoundEvent livingSound = null;
   public SoundEvent stepSound = null;
   public int[] animations = new int[]{0, 0, 0, 0};
   public MobDrop[] loot = null;
   public String team = "";
   public String defaultteam = "";
   public boolean isSubMob = false;
   public int var1 = 0;
   public float var2;
   public float var3;
   public float var4;
   public float var5;
   public int emptyTime = 0;
   public boolean emptyDespawn = true;
   public boolean byMobSpawn = false;
   public double followRangeSqDecr;
   public boolean isAgressive = false;
   public TileNexus nexus;
   public TileMonsterSpawner spawner;
   public boolean serializeNbtWasCalled = false;
   public EntityPlayer owner;
   public String ownerName;
   public int leadershipBase = 0;
   public boolean isStaying;
   public int ownerFollowDistanceSq = 256;
   public int moneyDroppedMin;
   public int moneyDroppedMax;
   public float moneyDroppedChance;
   public Soul soul = Soul.COMMON;
   public boolean canDropLoot = false;

   public AbstractMob(World worldIn, float sizeWidth, float sizeHeight) {
      super(worldIn);
      this.setSize(sizeWidth, sizeHeight);
   }

   public static final void addToRegister(Class thismobClass, String name, int eggPrimary, int eggSecondary, int trackerRange, int updateFrequency) {
      EntityEntry ENTRY = EntityEntryBuilder.create()
         .entity(thismobClass)
         .name(name)
         .id(name.toLowerCase().replace(" ", "_"), MobRegister.startid++)
         .egg(eggPrimary, eggSecondary)
         .tracker(trackerRange, updateFrequency, true)
         .build();
      MobRegister.toregister.add(ENTRY);
   }

   public static final void addToRegister(Class thismobClass, String name, int eggPrimary, int eggSecondary) {
      addToRegister(thismobClass, name, eggPrimary, eggSecondary, 64, 1);
   }

   public static final void addToRender(RenderAbstractMobEntry entry) {
      InitMobRenders.torender.add(entry);
   }

   public boolean isOnSameTeam(Entity entityIn) {
      return this.isOnScoreboardTeam(entityIn.getTeam()) || Team.isOnSameTeam(this, entityIn);
   }

   public void readEntityFromNBT(NBTTagCompound compound) {
      if (compound.hasKey("agressive")) {
         this.isAgressive = compound.getBoolean("agressive");
      }

      if (compound.hasKey("mobteam")) {
         this.team = compound.getString("mobteam");
      }

      if (compound.hasKey("ownername")) {
         this.ownerName = compound.getString("ownername");
         this.owner = this.world.getPlayerEntityByName(this.ownerName);
      }

      if (compound.hasKey("followOwnerDistSq")) {
         this.ownerFollowDistanceSq = compound.getInteger("followOwnerDistSq");
      }

      if (compound.hasKey("canDropLoot")) {
         this.canDropLoot = compound.getBoolean("canDropLoot");
      }

      super.readEntityFromNBT(compound);
   }

   public void writeEntityToNBT(NBTTagCompound compound) {
      compound.setBoolean("agressive", this.isAgressive);
      compound.setString("mobteam", this.team);
      if (this.ownerName != null) {
         compound.setString("ownername", this.ownerName);
      } else if (this.owner != null) {
         compound.setString("ownername", this.owner.getName());
      }

      compound.setInteger("followOwnerDistSq", this.ownerFollowDistanceSq);
      compound.setBoolean("canDropLoot", this.canDropLoot);
      super.writeEntityToNBT(compound);
   }

   public void onUpdate() {
      super.onUpdate();

      for (int i = 0; i < 4; i++) {
         if (this.animations[i] > 0) {
            this.animations[i]--;
         }
      }

      if (this.emptyDespawn && !this.isNoDespawnRequired() && this.byMobSpawn && !this.world.isRemote) {
         if (this.getAttackTarget() == null) {
            this.emptyTime++;
         } else if (this.ticksExisted % 50 == 0) {
            if (this.getDistanceSq(this.getAttackTarget()) > this.followRangeSqDecr) {
               this.emptyTime += 50;
            } else {
               this.emptyTime = 0;
            }
         }

         if (this.emptyTime > 400 && this.rand.nextInt(80) == 0) {
            if (this.nexus != null) {
               this.nexus.onInvaderDespawn(this);
            }

            this.setDead();
         }
      }

      if (this.owner != null && !this.owner.isDead) {
         if (this.ticksExisted % 100 == 0) {
            this.controlLeadership();
         }
      } else if (this.ownerName != null && this.ticksExisted % 20 == 0) {
         this.owner = this.world.getPlayerEntityByName(this.ownerName);
      }

      this.serializeNbtWasCalled = false;
   }

   public void shoot() {
   }

   public void specialAttack(double x, double y, double z, byte id) {
   }

   @Nullable
   public AbstractMob getOwnerIfSegment() {
      return null;
   }

   public void triggerAnimation(int id) {
      int num = -id - 1;
      num = MathHelper.clamp(num, 0, 3);
      this.animations[num] = 100;
      if (!this.world.isRemote) {
         this.world.setEntityState(this, (byte)id);
      }
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      super.handleStatusUpdate(id);
      if (this.world.isRemote && id < 0) {
         int num = -id - 1;
         num = MathHelper.clamp(num, 0, 3);
         this.animations[num] = 100;
      }
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
   }

   public void registerLOOT(MobDrop... values) {
      this.loot = values;
   }

   protected void dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source) {
      if (this.canDropLoot && this.loot != null) {
         for (int i = 0; i < this.loot.length; i++) {
            MobDrop drop = this.loot[i];
            if (this.rand.nextFloat() < drop.chance * this.getTamedDropChances(drop, i, lootingModifier, source)) {
               int lootadd = Math.round(lootingModifier / 3.0F * drop.maxlootingAdd * this.rand.nextFloat());
               int count = lootadd + drop.mincount + (drop.maxcount <= drop.mincount ? 0 : this.rand.nextInt(drop.maxcount - drop.mincount + 1));
               if (count > 0) {
                  ItemStack stack = new ItemStack(drop.item, count, drop.damage);
                  this.entityDropItem(stack, 0.0F);
               }
            }
         }
      }

      this.dropFewItems(wasRecentlyHit, lootingModifier);
      this.dropEquipment(wasRecentlyHit, lootingModifier);
      if (this.canDropLoot && this.rand.nextFloat() < this.moneyDroppedChance) {
         int difference = this.moneyDroppedMax - this.moneyDroppedMin + 1;
         if (difference > 0) {
            int coinsfall = this.moneyDroppedMin + this.rand.nextInt(difference);
            Coins.dropMoneyToWorld(this.world, coinsfall, 10, this.posX, this.posY + this.height * 0.25, this.posZ);
         }
      }
   }

   public float getTamedDropChances(MobDrop drop, int mobDropIndex, int lootingModifier, DamageSource source) {
      return 1.0F;
   }

   public void setattributes(
      double maxHealth,
      double followRange,
      double damage,
      double moveSpeed,
      double armor,
      double protection,
      double knockResist,
      double knockback,
      double vampirism,
      double jumpHeight
   ) {
      int mobDifficulty = this.getMobDifficulty();
      if (mobDifficulty >= 3) {
         damage++;
         moveSpeed *= 1.1F;
      } else if (mobDifficulty < 2) {
         damage--;
         moveSpeed *= 0.85F;
      }

      this.maxHealth = maxHealth;
      this.followRange = followRange;
      this.damage = damage;
      this.moveSpeed = moveSpeed;
      this.armor = armor;
      this.protection = protection;
      this.knockResist = knockResist;
      this.knockback = knockback;
      this.vampirism = vampirism;
      this.jumpHeight = jumpHeight;
      this.followRangeSqDecr = followRange * followRange * 0.3;
      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(maxHealth);
      this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(followRange);
      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(moveSpeed);
      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(damage);
      this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(armor);
      this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(knockResist);
      this.getEntityAttribute(PropertiesRegistry.ARMOR_PROTECTION).setBaseValue(protection);
      this.getEntityAttribute(PropertiesRegistry.JUMP_HEIGHT).setBaseValue(jumpHeight);
      this.getEntityAttribute(PropertiesRegistry.VAMPIRISM).setBaseValue(vampirism);
      this.getEntityAttribute(PropertiesRegistry.MELEE_KNOCKBACK).setBaseValue(knockback);
   }

   public void setDropMoney(int min, int max, float chance) {
      this.moneyDroppedMin = min;
      this.moneyDroppedMax = max;
      this.moneyDroppedChance = chance;
   }

   public void setRoleValues(EnumMobRole role, int dimensionNumber) {
      this.experienceValue = role.getXpValue();
      role.setMoneyValue(this, dimensionNumber);
   }

   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return this.hurtSound;
   }

   protected SoundEvent getDeathSound() {
      return this.deathSound;
   }

   protected SoundEvent getAmbientSound() {
      return this.livingSound;
   }

   public void playLivingSound() {
      if (!this.isSubMob) {
         super.playLivingSound();
      }
   }

   protected void playStepSound(BlockPos pos, Block blockIn) {
      if (this.stepSound != null) {
         this.playSound(this.stepSound, 0.15F, 1.0F);
      } else {
         super.playStepSound(pos, blockIn);
      }
   }

   public boolean attackEntityFrom(DamageSource source, float amount) {
      if (amount > 0.0F) {
         this.emptyTime = 0;
      }

      for (EntityAITaskEntry task : this.tasks.taskEntries) {
         if (task.action instanceof AbstractAI) {
            amount = ((AbstractAI)task.action).onEntityAttacked(source, amount);
         }
      }

      return amount > 0.0F ? super.attackEntityFrom(source, amount) : false;
   }

   public boolean attackEntityAsMob(Entity entityIn) {
      this.emptyTime = 0;
      return super.attackEntityAsMob(entityIn);
   }

   protected void onDeathUpdate() {
      super.onDeathUpdate();
   }

   public void onDeath(DamageSource cause) {
      if (this.nexus != null) {
         this.nexus.onInvaderKilled(this, cause);
      }

      if (this.spawner != null) {
         this.spawner.onMobKilled(this, cause);
      }

      if (!this.isNonBoss() && !this.world.isRemote) {
         for (Entity enitty : GetMOP.getEntitiesInAABBof(this.world, this, 64.0, this)) {
            if (enitty instanceof EntityPlayer) {
               MobSpawn.chillAfterBoss((EntityPlayer)enitty);
            }
         }
      }

      super.onDeath(cause);
   }

   protected void despawnEntity() {
      super.despawnEntity();
      if (this.isDead && this.nexus != null) {
         this.nexus.onInvaderDespawn(this);
      }
   }

   protected void updateAITasks() {
      super.updateAITasks();
   }

   public boolean canFreezingImmobilizeMob(PotionEffect effect) {
      int effectPower = effect.getAmplifier() + 1;
      float entityPower = this.getMaxHealth() * 0.025F
         + (this.width + this.height) / 2.5F
         + (float)this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).getAttributeValue();
      if (this.isImmuneToFire()) {
         entityPower--;
      }

      return effectPower >= entityPower;
   }

   public boolean canBeCaptured(@Nullable EntityLivingBase capturer) {
      return this.getHealth() <= 5.0F || capturer != null && Team.isOnSameTeam(this, capturer);
   }

   public void setDead() {
      if (this.serializeNbtWasCalled && this.getHealth() > 5.0F) {
         if (!this.world.tickableTileEntities.isEmpty()) {
            for (TileEntity tile : this.world.tickableTileEntities) {
               float radius = 6.0F;
               double xx = tile.getPos().getX();
               if (Math.abs(xx - this.posX) <= radius) {
                  double yy = tile.getPos().getY();
                  if (Math.abs(yy - this.posY) <= radius) {
                     double zz = tile.getPos().getZ();
                     if (Math.abs(zz - this.posZ) <= radius) {
                        IBlockState state = this.world.getBlockState(tile.getPos());
                        if ("thermalexpansion:device".equals(state.getBlock().getRegistryName().toString())
                           && state.getBlock().getMetaFromState(state) == 11) {
                           this.world.setBlockToAir(tile.getPos());
                           this.world.newExplosion(null, xx, yy, zz, 3.0F, false, true);
                           System.out.println("Thermalexpansion creature encaptulator was trying to capture too powerful mob");
                           return;
                        }
                     }
                  }
               }
            }
         }

         if (Main.ENDERIO_installed) {
            boolean shouldReturn = false;
            AxisAlignedBB bb = this.getEntityBoundingBox().grow(1.0);
            int j2 = MathHelper.floor(bb.minX);
            int k2 = MathHelper.ceil(bb.maxX);
            int l2 = MathHelper.floor(bb.minY);
            int i3 = MathHelper.ceil(bb.maxY);
            int j3 = MathHelper.floor(bb.minZ);
            int k3 = MathHelper.ceil(bb.maxZ);
            PooledMutableBlockPos blockpos$pooledmutableblockpos = PooledMutableBlockPos.retain();

            for (int l3 = j2; l3 < k2; l3++) {
               for (int i4 = l2; i4 < i3; i4++) {
                  for (int j4 = j3; j4 < k3; j4++) {
                     IBlockState iblockstate1 = this.world.getBlockState(blockpos$pooledmutableblockpos.setPos(l3, i4, j4));
                     if (iblockstate1.getBlock() == Blocks.DISPENSER) {
                        TileEntity tilex = this.world.getTileEntity(blockpos$pooledmutableblockpos);
                        if (tilex != null && tilex instanceof TileEntityDispenser) {
                           TileEntityDispenser dispenser = (TileEntityDispenser)tilex;
                           boolean hasVial = false;

                           for (int i = 0; i < dispenser.getSizeInventory(); i++) {
                              if ("enderio:item_soul_vial".equals(dispenser.getStackInSlot(i).getItem().getRegistryName().toString())) {
                                 hasVial = true;
                                 break;
                              }
                           }

                           if (hasVial) {
                              dispenser.clear();
                              shouldReturn = true;
                              System.out.println("Dispenser with enderio soul vials was trying to capture too powerful mob");
                           }
                        }
                     }
                  }
               }
            }

            blockpos$pooledmutableblockpos.release();
            if (shouldReturn) {
               return;
            }
         }
      }

      super.setDead();
      if (this.owner != null && this.owner instanceof EntityPlayerMP) {
         int leadershipAll = calculateLeadership(this.world, this.owner);
         PacketSmallSomethingToClients packet = new PacketSmallSomethingToClients();
         packet.writeargs(2, leadershipAll);
         PacketHandler.sendTo(packet, (EntityPlayerMP)this.owner);
      }
   }

   public NBTTagCompound serializeNBT() {
      this.serializeNbtWasCalled = true;
      return super.serializeNBT();
   }

   public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
      this.team = this.defaultteam;
      this.setHealth((float)this.maxHealth);
      return super.onInitialSpawn(difficulty, livingdata);
   }

   public IEntityLivingData onInitialSpawn() {
      return this.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(this)), (IEntityLivingData)null);
   }

   @SideOnly(Side.CLIENT)
   @Nullable
   @Override
   public ResourceLocation getMultitexture() {
      return null;
   }

   public int getLeadershipNeed() {
      return this.leadershipBase;
   }

   public void controlLeadership() {
      int playerleadership = Mana.getLeadership(this.owner);
      int allLeadersh = calculateLeadership(this.world, this.owner);
      if (allLeadersh > playerleadership) {
         EntityPlayer lastOwner = this.owner;
         this.owner = null;
         this.team = this.defaultteam;
         this.isStaying = false;
         this.ownerName = null;
         if (lastOwner instanceof EntityPlayerMP) {
            int leadershipAll = allLeadersh - this.getLeadershipNeed();
            PacketSmallSomethingToClients packet = new PacketSmallSomethingToClients();
            packet.writeargs(2, leadershipAll);
            PacketHandler.sendTo(packet, (EntityPlayerMP)lastOwner);
         }
      }
   }

   public static int calculateLeadership(World world, EntityPlayer player) {
      int allLeadersh = 0;

      for (Entity entity : world.loadedEntityList) {
         if (!entity.isDead && entity instanceof AbstractMob) {
            AbstractMob mob = (AbstractMob)entity;
            if (player == mob.owner) {
               allLeadersh += mob.getLeadershipNeed();
            }
         }
      }

      return allLeadersh;
   }

   protected boolean isMovementBlocked() {
      return this.isStaying ? true : super.isMovementBlocked();
   }

   @Nullable
   public static Entity getEntityByUUID(World world, UUID uuid) {
      for (Entity entity : world.getLoadedEntityList()) {
         if (uuid.equals(entity.getUniqueID())) {
            return entity;
         }
      }

      return null;
   }

   public void tame(EntityPlayer player) {
      if (!this.world.isRemote) {
         String playerteam = Team.getTeamFor(player);
         if (!playerteam.isEmpty()) {
            this.isAgressive = false;
            this.owner = player;
            this.team = playerteam;
            this.navigator.clearPath();
            this.setAttackTarget((EntityLivingBase)null);
            if (player instanceof EntityPlayerMP) {
               int leadershipAll = calculateLeadership(this.world, player);
               PacketSmallSomethingToClients packet = new PacketSmallSomethingToClients();
               packet.writeargs(2, leadershipAll);
               PacketHandler.sendTo(packet, (EntityPlayerMP)player);
            }
         } else if (player instanceof EntityPlayerMP) {
            player.sendMessage(
               new TextComponentString(
                  "Unable to tame a creature without being in a team. Create or join team, using scoreboard commands (/scoreboard teams add _teamName_) (/scoreboard teams join _teamName_)"
               )
            );
         }
      }
   }

   public int getMobDifficulty() {
      return this.defaultteam != null && this.world != null && this.world.getDifficulty() != null && this.defaultteam.equals(this.team)
         ? this.world.getDifficulty().getId()
         : 2;
   }

   public BloodType getBloodType() {
      return DeathEffects.RED_BLOOD;
   }

   public void dropShards() {
   }

   protected int getExperiencePoints(EntityPlayer player) {
      if (this.isNonBoss()) {
         return this.rand.nextFloat() < 0.35 ? Math.round(this.experienceValue / 0.35F) : 0;
      } else {
         return this.experienceValue;
      }
   }

   public abstract static class AbstractAI extends EntityAIBase {
      public abstract float onEntityAttacked(DamageSource var1, float var2);

      public float onEntityAttacks(Entity entityAttacked, DamageSource source, float amount) {
         return amount;
      }
   }

   public static class MobDrop extends Treasure {
      public int maxlootingAdd;

      public MobDrop(Item item, float chance, int damage, int mincount, int maxcount, int maxlootingAdd) {
         super(item, chance, damage, mincount, maxcount);
         this.maxlootingAdd = maxlootingAdd;
      }

      public MobDrop(Block item, float chance, int damage, int mincount, int maxcount, int maxlootingAdd) {
         super(Item.getItemFromBlock(item), chance, damage, mincount, maxcount);
         this.maxlootingAdd = maxlootingAdd;
      }
   }

   public static class RenderAbstractMobEntry {
      public final ModelBase model;
      public final ResourceLocation mobTexture;
      public final float shadowSize;
      public final Class mobClass;
      public boolean layerHeldItem = false;
      public boolean layerArmor = false;
      public boolean getMultitexture = false;
      public boolean rocket = false;
      public boolean verticalRocket;
      public boolean useIRender = false;
      public boolean lightLayer = false;
      public ResourceLocation lightLayerTexture;
      public LayerRandomItem layerrandItem;

      public RenderAbstractMobEntry(ModelBase model, ResourceLocation mobTexture, float shadowSize, Class mobClass) {
         this.model = model;
         this.mobTexture = mobTexture;
         this.shadowSize = shadowSize;
         this.mobClass = mobClass;
      }

      public RenderAbstractMobEntry(ModelBase model, float shadowSize, Class mobClass) {
         this.model = model;
         this.mobTexture = null;
         this.shadowSize = shadowSize;
         this.mobClass = mobClass;
         this.getMultitexture = true;
      }

      public RenderAbstractMobEntry setLayerHeldItem() {
         this.layerHeldItem = true;
         return this;
      }

      public RenderAbstractMobEntry setlayerArmor() {
         this.layerArmor = true;
         return this;
      }

      public RenderAbstractMobEntry setLightLayer(ResourceLocation lightLayerTexture) {
         this.lightLayer = true;
         this.lightLayerTexture = lightLayerTexture;
         return this;
      }

      public RenderAbstractMobEntry setRenderAsRocket(boolean vertical) {
         this.rocket = true;
         this.verticalRocket = vertical;
         return this;
      }

      public RenderAbstractMobEntry setLayerRandomItem(LayerRandomItem layerrandItem) {
         this.layerrandItem = layerrandItem;
         return this;
      }

      public RenderAbstractMobEntry setUseIRender() {
         this.useIRender = true;
         return this;
      }
   }
}
