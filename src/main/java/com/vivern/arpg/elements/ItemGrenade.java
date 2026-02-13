package com.vivern.arpg.elements;

import com.vivern.arpg.elements.models.AbstractMobModel;
import com.vivern.arpg.elements.models.GrenadeModel;
import com.vivern.arpg.elements.models.ModelSphere;
import com.vivern.arpg.entity.AdvancedFallingBlock;
import com.vivern.arpg.entity.ChlorineCloud;
import com.vivern.arpg.entity.CrystalFanShoot;
import com.vivern.arpg.entity.EntityGrenade;
import com.vivern.arpg.events.Debugger;
import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.Keys;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.SuperKnockback;
import com.vivern.arpg.main.Team;
import com.vivern.arpg.main.WeaponDamage;
import com.vivern.arpg.main.WeaponParameters;
import com.vivern.arpg.main.Weapons;
import com.vivern.arpg.mobs.HostileProjectiles;
import com.vivern.arpg.mobs.OtherMobsPack;
import com.vivern.arpg.potions.Freezing;
import com.vivern.arpg.potions.PotionEffects;
import com.vivern.arpg.renders.AnimatedGParticle;
import com.vivern.arpg.renders.GUNParticle;
import com.vivern.arpg.renders.ParticleTracker;
import com.vivern.arpg.tileentity.TileSpellForge;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemGrenade extends ItemWeapon {
   public static ResourceLocation largesmoke = new ResourceLocation("arpg:textures/largesmoke.png");
   public static ResourceLocation flame = new ResourceLocation("arpg:textures/flame_big.png");
   public static ResourceLocation snow = new ResourceLocation("arpg:textures/snowflake3.png");
   public static ResourceLocation largecloud = new ResourceLocation("arpg:textures/largecloud.png");
   public static ResourceLocation star = new ResourceLocation("arpg:textures/star.png");
   public static ResourceLocation firecircle = new ResourceLocation("arpg:textures/fire_circle.png");
   public static ResourceLocation oildrop = new ResourceLocation("arpg:textures/oildrop.png");
   public static ResourceLocation slimesplash = new ResourceLocation("arpg:textures/slimesplash.png");
   public static ResourceLocation snow5 = new ResourceLocation("arpg:textures/snowflake5.png");
   public static ResourceLocation shadow_round64x = new ResourceLocation("arpg:textures/shadow_round64x.png");
   public static ResourceLocation void_explode = new ResourceLocation("arpg:textures/void_explode.png");
   public static ResourceLocation lightning1 = new ResourceLocation("arpg:textures/lightning1.png");
   public static ResourceLocation whirl = new ResourceLocation("arpg:textures/whirl.png");
   public static HashMap<Byte, ItemGrenade> registry = new HashMap<>();
   public int burntime = -1;
   public boolean beacon = false;
   public boolean ench = false;
   public byte id = 0;
   public static GrenadeModel mainModel = new GrenadeModel();
   public ResourceLocation texture;

   public ItemGrenade(String name, int maxstacksize, byte id, int firstExplodeDelay, float damage, float knockback, ResourceLocation texture) {
      this.setRegistryName(name);
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey(name);
      this.setMaxStackSize(maxstacksize);
      this.id = id;
      this.texture = texture;
      registry.put(id, this);
   }

   public float damage() {
      return WeaponParameters.getWeaponParameters(this).get("damage");
   }

   public float knockback() {
      return WeaponParameters.getWeaponParameters(this).get("knockback");
   }

   public int firstExplodeDelay() {
      return WeaponParameters.getWeaponParameters(this).geti("first_explode_delay");
   }

   public float damageRadius() {
      return WeaponParameters.getWeaponParameters(this).get("damage_radius");
   }

   public boolean doWaterMoveHook() {
      return false;
   }

   public int waterParticlesHookAdding() {
      return 0;
   }

   public void modifySpeedInWater(EntityGrenade grenade) {
   }

   public boolean canDestroyBlockInCreative(World world, BlockPos pos, ItemStack stack, EntityPlayer player) {
      return false;
   }

   public void onUpdate(ItemStack stack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
      if (!world.isRemote) {
         this.setCanShoot(stack, entityIn);
         if (IWeapon.canShoot(stack)) {
            EntityPlayer player = (EntityPlayer)entityIn;
            boolean hascooldown = player.getCooldownTracker().hasCooldown(this);
            if (!hascooldown
               && (
                  Keys.isKeyPressed(player, Keys.PRIMARYATTACK) && player.getHeldItemMainhand() == stack
                     || Keys.isKeyPressed(player, Keys.SECONDARYATTACK) && player.getHeldItemOffhand() == stack
                     || Keys.isKeyPressed(player, Keys.GRENADE) && this.isFirst(player, stack)
               )) {
               Weapons.setPlayerAnimationOnServer(player, 1, player.getHeldItemMainhand() == stack ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND);
               player.getCooldownTracker().setCooldown(this, this.getCooldownTime(stack));
               player.addStat(StatList.getObjectUseStats(this));
               this.playThrowSound(world, player);
               EntityGrenade entitygrenade = new EntityGrenade(world, player);
               entitygrenade.grenade = this;
               entitygrenade.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 0.8F, 2.0F);
               world.spawnEntity(entitygrenade);
               stack.shrink(1);
            }
         }
      }
   }

   public void playThrowSound(World world, EntityPlayer player) {
      world.playSound(
         (EntityPlayer)null,
         player.posX,
         player.posY,
         player.posZ,
         Sounds.grenade_throw,
         SoundCategory.NEUTRAL,
         0.8F,
         0.85F + itemRand.nextFloat() * 0.3F
      );
   }

   public ItemGrenade setEnchantGlow() {
      this.ench = true;
      return this;
   }

   public boolean hasEffect(ItemStack stack) {
      return this.ench ? this.ench : super.hasEffect(stack);
   }

   public void onProjectileUpdate(EntityGrenade projectile) {
      if (!projectile.world.isRemote
         && projectile.flyingTime > projectile.grenade.firstExplodeDelay() - projectile.impacts * projectile.grenade.firstExplodeDelay() / 5
         && !projectile.isDead) {
         projectile.grenade
            .explode(
               projectile.world,
               projectile.getThrower(),
               projectile.posX,
               projectile.posY,
               projectile.posZ,
               null,
               projectile
            );
         projectile.world.setEntityState(projectile, (byte)-1);
      }
   }

   public void explode(
      World world, @Nullable EntityLivingBase player, double x, double y, double z, @Nullable RayTraceResult result, @Nullable EntityGrenade projectile
   ) {
      if (!world.isRemote) {
         double damageRadius = this.damageRadius();
         AxisAlignedBB axisalignedbb = new AxisAlignedBB(
            x - damageRadius, y - damageRadius, z - damageRadius, x + damageRadius, y + damageRadius, z + damageRadius
         );
         List<Entity> list = world.getEntitiesWithinAABB(Entity.class, axisalignedbb);
         if (!list.isEmpty()) {
            ItemStack stack = new ItemStack(this);

            for (Entity entity : list) {
               if (Team.checkIsOpponent(player, entity)) {
                  Weapons.dealDamage(
                     new WeaponDamage(stack, player, projectile, true, false, new Vec3d(x, y, z), WeaponDamage.explode),
                     this.damage(),
                     player,
                     entity,
                     true,
                     this.knockback(),
                     x,
                     y,
                     z
                  );
                  entity.hurtResistantTime = 0;
               } else {
                  Weapons.dealDamage(
                     new WeaponDamage(stack, player, projectile, true, false, new Vec3d(x, y, z), WeaponDamage.explode),
                     this.damage() * 0.7F,
                     player,
                     entity,
                     true,
                     this.knockback(),
                     x,
                     y,
                     z
                  );
               }
            }
         }

         for (BlockPos pos : GetMOP.getPosesInsideSphere(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z), 3)) {
            Weapons.triggerExplodeBlock(world, pos, player, projectile);
         }

         if (projectile != null) {
            projectile.setDead();
         }
      } else {
         world.playSound(x, y, z, Sounds.explode3, SoundCategory.PLAYERS, 1.3F, 0.85F + itemRand.nextFloat() / 5.0F, false);

         for (int ss = 0; ss < 7; ss++) {
            GUNParticle bigsmoke = new GUNParticle(
               largesmoke,
               0.8F + itemRand.nextFloat() / 2.0F,
               -0.001F,
               40,
               60,
               world,
               x,
               y,
               z,
               (float)itemRand.nextGaussian() / 9.0F,
               (float)itemRand.nextGaussian() / 10.0F,
               (float)itemRand.nextGaussian() / 9.0F,
               1.0F,
               1.0F,
               1.0F,
               true,
               itemRand.nextInt(360)
            );
            bigsmoke.alphaTickAdding = -0.01F;
            world.spawnEntity(bigsmoke);
         }

         for (int ss = 0; ss < 13; ss++) {
            int lt = 14 + itemRand.nextInt(8);
            GUNParticle fire = new GUNParticle(
               flame,
               0.4F + itemRand.nextFloat() * 1.6F,
               -0.003F,
               lt,
               240,
               world,
               x,
               y,
               z,
               (float)itemRand.nextGaussian() / 7.0F,
               (float)itemRand.nextGaussian() / 7.0F,
               (float)itemRand.nextGaussian() / 7.0F,
               1.0F - itemRand.nextFloat() * 0.2F,
               1.0F - itemRand.nextFloat() * 0.2F,
               0.7F,
               true,
               itemRand.nextInt(100) - 50
            );
            fire.alphaTickAdding = -1.0F / lt;
            fire.alphaGlowing = true;
            fire.tracker = ItemRocket.trackerExplode1;
            world.spawnEntity(fire);
         }

         for (int ss = 0; ss < 25; ss++) {
            world.spawnParticle(
               EnumParticleTypes.SMOKE_LARGE,
               x + itemRand.nextGaussian(),
               y + itemRand.nextGaussian(),
               z + itemRand.nextGaussian(),
               itemRand.nextGaussian() / 13.0,
               itemRand.nextGaussian() / 13.0,
               itemRand.nextGaussian() / 13.0,
               new int[0]
            );
         }
      }
   }

   public void onProjectileImpact(EntityGrenade projectile, @Nullable RayTraceResult result) {
      if (projectile.bounce(result, 0.5)) {
         if (projectile.world.isRemote && projectile.getspeedSq() > 0.05F) {
            projectile.randomRotate = new Vec3d(itemRand.nextGaussian(), itemRand.nextGaussian(), itemRand.nextGaussian());
         }

         projectile.slowdown(0.5);
      }

      if (result != null
         && result.entityHit != null
         && result.entityHit instanceof EntityLivingBase
         && result.entityHit != projectile.getThrower()) {
         projectile.slowdown(0.5);
      }
   }

   @SideOnly(Side.CLIENT)
   public void renderGrenade(
      @Nullable EntityGrenade entity, double x, double y, double z, float entityYaw, float partialTicks, @Nullable Render render, boolean forEntity
   ) {
      GlStateManager.pushMatrix();
      if (forEntity) {
         GlStateManager.translate((float)x, (float)y + 0.1F, (float)z);
         GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.rotate(
            (entity.prevrotationProgr + (entity.rotationProgr - entity.prevrotationProgr) * partialTicks) * 8.2F,
            (float)entity.randomRotate.x,
            (float)entity.randomRotate.y,
            (float)entity.randomRotate.z
         );
      }

      GlStateManager.disableCull();
      Minecraft.getMinecraft().renderEngine.bindTexture(this.texture);
      mainModel.hideAll();
      mainModel.shapemain.isHidden = false;
      mainModel.pin.isHidden = false;
      mainModel.shapestick.isHidden = false;
      mainModel.render(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      GlStateManager.enableCull();
      GlStateManager.popMatrix();
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return true;
   }

   @Override
   public int getCooldownTime(ItemStack itemstack) {
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(itemstack.getItem());
      return parameters.geti("cooldown");
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.ONE_HANDED;
   }

   public boolean isFirst(EntityPlayer player, ItemStack itemstack) {
      InventoryPlayer inventory = player.inventory;

      for (int i = 0; i < inventory.getSizeInventory(); i++) {
         if (inventory.getStackInSlot(i).getItem() instanceof ItemGrenade) {
            if (inventory.getStackInSlot(i) == itemstack) {
               return true;
            }

            return false;
         }
      }

      return false;
   }

   @Override
   public boolean hasSpecialDescription() {
      return false;
   }

   public static class Bomb extends ItemGrenade {
      public Bomb(String name, int maxstacksize, byte id, int firstExplodeDelay, float damage, float knockback, ResourceLocation texture) {
         super(name, maxstacksize, id, firstExplodeDelay, damage, knockback, texture);
      }

      @Override
      public void explode(
         World world, @Nullable EntityLivingBase player, double x, double y, double z, @Nullable RayTraceResult result, @Nullable EntityGrenade projectile
      ) {
         if (!world.isRemote) {
            world.newExplosion(player, x, y, z, WeaponParameters.getWeaponParameters(this).get("explosion_size"), false, true);
            if (projectile != null) {
               projectile.setDead();
            }
         }
      }

      @SideOnly(Side.CLIENT)
      @Override
      public void renderGrenade(
         @Nullable EntityGrenade entity, double x, double y, double z, float entityYaw, float partialTicks, @Nullable Render render, boolean forEntity
      ) {
         GlStateManager.pushMatrix();
         if (forEntity) {
            GlStateManager.translate((float)x, (float)y + 0.1F, (float)z);
            GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(
               (entity.prevrotationProgr + (entity.rotationProgr - entity.prevrotationProgr) * partialTicks) * 8.2F,
               (float)entity.randomRotate.x,
               (float)entity.randomRotate.y,
               (float)entity.randomRotate.z
            );
         }

         GlStateManager.disableCull();
         Minecraft.getMinecraft().renderEngine.bindTexture(this.texture);
         mainModel.hideAll();
         mainModel.shapecubeup.isHidden = false;
         mainModel.shapecubedown.isHidden = false;
         mainModel.big.isHidden = false;
         mainModel.render(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
         GlStateManager.enableCull();
         GlStateManager.popMatrix();
      }
   }

   public static class Cryogrenade extends ItemGrenade {
      public Cryogrenade(String name, int maxstacksize, byte id, int firstExplodeDelay, float damage, float knockback, ResourceLocation texture) {
         super(name, maxstacksize, id, firstExplodeDelay, damage, knockback, texture);
      }

      @Override
      public void explode(
         World world, @Nullable EntityLivingBase player, double x, double y, double z, @Nullable RayTraceResult result, @Nullable EntityGrenade projectile
      ) {
         if (!world.isRemote) {
            double damageRadius = this.damageRadius();
            AxisAlignedBB axisalignedbb = new AxisAlignedBB(
               x - damageRadius, y - damageRadius, z - damageRadius, x + damageRadius, y + damageRadius, z + damageRadius
            );
            List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(projectile, axisalignedbb);
            int sounds = 3;
            if (!list.isEmpty()) {
               ItemStack stack = new ItemStack(this);
               WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);

               for (Entity entity : list) {
                  if (Team.checkIsOpponent(player, entity)) {
                     Weapons.dealDamage(
                        new WeaponDamage(stack, player, projectile, true, false, new Vec3d(x, y, z), WeaponDamage.frost),
                        this.damage(),
                        player,
                        entity,
                        true,
                        this.knockback(),
                        x,
                        y,
                        z
                     );
                     entity.hurtResistantTime = 0;
                     PotionEffect lastdebaff = Weapons.mixPotion(
                        entity,
                        PotionEffects.FREEZING,
                        (float)parameters.geti("potion_time_add"),
                        (float)parameters.geti("potion_power_add"),
                        Weapons.EnumPotionMix.WITH_MAXIMUM,
                        Weapons.EnumPotionMix.WITH_MAXIMUM,
                        Weapons.EnumMathOperation.PLUS,
                        Weapons.EnumMathOperation.PLUS,
                        parameters.geti("potion_time_max"),
                        parameters.geti("potion_power_max")
                     );
                     if (itemRand.nextFloat() < 0.5 && sounds > 0) {
                        Freezing.tryPlaySound(entity, lastdebaff);
                        sounds--;
                     }
                  } else {
                     PotionEffect lastdebaff = Weapons.mixPotion(
                        entity,
                        PotionEffects.FREEZING,
                        (float)parameters.geti("friendlyfire_potion_time_add"),
                        (float)parameters.geti("friendlyfire_potion_power_add"),
                        Weapons.EnumPotionMix.WITH_MAXIMUM,
                        Weapons.EnumPotionMix.WITH_MAXIMUM,
                        Weapons.EnumMathOperation.PLUS,
                        Weapons.EnumMathOperation.PLUS,
                        parameters.geti("friendlyfire_potion_time_max"),
                        parameters.geti("friendlyfire_potion_power_max")
                     );
                     if (itemRand.nextFloat() < 0.5 && sounds > 0) {
                        Freezing.tryPlaySound(entity, lastdebaff);
                        sounds--;
                     }
                  }
               }
            }

            for (BlockPos pos : GetMOP.getPosesInsideSphere(
               MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z), Math.round((float)damageRadius * 0.652F)
            )) {
               Weapons.freezeBlock(world, pos);
            }

            if (projectile != null) {
               projectile.setDead();
            }
         } else {
            world.playSound(x, y, z, Sounds.cryogrenade, SoundCategory.PLAYERS, 1.0F, 0.85F + itemRand.nextFloat() * 0.3F, false);

            for (int ss = 0; ss < 20; ss++) {
               int lt = 30 + itemRand.nextInt(20);
               GUNParticle bigsmoke = new GUNParticle(
                  largecloud,
                  0.6F + Math.abs((float)itemRand.nextGaussian()),
                  -0.005F,
                  lt,
                  240,
                  world,
                  x,
                  y,
                  z,
                  (float)(itemRand.nextGaussian() / 9.0),
                  (float)(itemRand.nextGaussian() / 9.0),
                  (float)(itemRand.nextGaussian() / 9.0),
                  0.5F + itemRand.nextFloat() / 10.0F,
                  1.0F,
                  1.0F,
                  true,
                  itemRand.nextInt(360)
               );
               bigsmoke.alphaGlowing = true;
               bigsmoke.alphaTickAdding = -1.0F / lt;
               world.spawnEntity(bigsmoke);
            }

            for (int ss = 0; ss < 19; ss++) {
               double xx = x + itemRand.nextGaussian() * 1.3;
               double yy = y + itemRand.nextGaussian() * 1.3;
               double zz = z + itemRand.nextGaussian() * 1.3;
               BlockPos pos = new BlockPos(xx, yy, zz);
               if (!world.getBlockState(pos).isOpaqueCube()) {
                  int lt = 60 + itemRand.nextInt(40);
                  float scl = 0.07F + itemRand.nextFloat() * 0.1F;
                  GUNParticle bigsmoke = new GUNParticle(
                     star,
                     scl,
                     0.0F,
                     lt,
                     240,
                     world,
                     xx,
                     yy,
                     zz,
                     0.0F,
                     0.0F,
                     0.0F,
                     0.8F + itemRand.nextFloat() * 0.2F,
                     1.0F,
                     1.0F,
                     false,
                     itemRand.nextInt(360)
                  );
                  bigsmoke.scaleTickAdding = -scl / (lt + 20);
                  world.spawnEntity(bigsmoke);
               }
            }

            int countOfParticles = 13;
            float R = (float)((0.08 + itemRand.nextGaussian() / 50.0) * 3.0);

            for (int i = 0; i < countOfParticles; i++) {
               float rand1 = itemRand.nextFloat() * 2.0F - 1.0F;
               float rand2 = itemRand.nextFloat() * 2.0F - 1.0F;
               float X = rand1 * R;
               float new_R = (float)Math.sqrt(R * R - X * X);
               float Y = rand2 * new_R;
               float Z = (float)Math.sqrt(new_R * new_R - Y * Y);
               if (itemRand.nextBoolean()) {
                  Z *= -1.0F;
               }

               GUNParticle particle = new GUNParticle(
                  snow,
                  0.35F + (float)itemRand.nextGaussian() / 30.0F,
                  0.01F,
                  22 + itemRand.nextInt(10),
                  180,
                  world,
                  x,
                  y,
                  z,
                  X,
                  Y,
                  Z,
                  0.75F + itemRand.nextFloat() / 10.0F,
                  0.9F,
                  1.0F,
                  false,
                  itemRand.nextInt(360),
                  true,
                  1.0F
               );
               world.spawnEntity(particle);
            }

            for (int ssx = 0; ssx < 25; ssx++) {
               world.spawnParticle(
                  EnumParticleTypes.CLOUD,
                  x + itemRand.nextGaussian(),
                  y + itemRand.nextGaussian(),
                  z + itemRand.nextGaussian(),
                  itemRand.nextGaussian() / 15.0,
                  itemRand.nextGaussian() / 15.0,
                  itemRand.nextGaussian() / 15.0,
                  new int[0]
               );
            }
         }
      }

      @SideOnly(Side.CLIENT)
      @Override
      public void renderGrenade(
         @Nullable EntityGrenade entity, double x, double y, double z, float entityYaw, float partialTicks, @Nullable Render render, boolean forEntity
      ) {
         GlStateManager.pushMatrix();
         if (forEntity) {
            GlStateManager.translate((float)x, (float)y + 0.1F, (float)z);
            GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(
               (entity.prevrotationProgr + (entity.rotationProgr - entity.prevrotationProgr) * partialTicks) * 8.2F,
               (float)entity.randomRotate.x,
               (float)entity.randomRotate.y,
               (float)entity.randomRotate.z
            );
         }

         GlStateManager.disableCull();
         Minecraft.getMinecraft().renderEngine.bindTexture(this.texture);
         mainModel.hideAll();
         mainModel.shapemain.isHidden = false;
         mainModel.stickdiag1.isHidden = false;
         mainModel.stickdiag2.isHidden = false;
         mainModel.stickdiag3.isHidden = false;
         mainModel.stickdiag4.isHidden = false;
         mainModel.shapedown2.isHidden = false;
         mainModel.shapeup2.isHidden = false;
         mainModel.render(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
         GlStateManager.enableCull();
         GlStateManager.popMatrix();
      }
   }

   public static class GasGrenade extends ItemGrenade {
      public GasGrenade(String name, int maxstacksize, byte id, int firstExplodeDelay, float damage, float knockback, ResourceLocation texture) {
         super(name, maxstacksize, id, firstExplodeDelay, damage, knockback, texture);
      }

      @Override
      public void onProjectileUpdate(EntityGrenade projectile) {
         if (projectile.ticksExisted % 12 == 0
            && projectile.flyingTime > projectile.grenade.firstExplodeDelay() - projectile.impacts * 20
            && !projectile.isDead) {
            this.gas(
               projectile.world, projectile.getThrower(), projectile.posX, projectile.posY, projectile.posZ, projectile
            );
         }

         if (projectile.world.isRemote && projectile.explodes > 0 && projectile.ticksExisted % 4 == 0) {
            GUNParticle bigsmoke = new GUNParticle(
               largecloud,
               1.5F + (float)itemRand.nextGaussian() / 3.0F,
               3.0E-4F * (itemRand.nextFloat() - 0.5F),
               65 + (int)Debugger.floats[0],
               111,
               projectile.world,
               projectile.posX,
               projectile.posY,
               projectile.posZ,
               (float)itemRand.nextGaussian() / 16.0F,
               (float)itemRand.nextGaussian() / 16.0F,
               (float)itemRand.nextGaussian() / 16.0F,
               0.8F + itemRand.nextFloat() * 0.2F,
               0.8F + itemRand.nextFloat() * 0.2F,
               0.4F,
               true,
               itemRand.nextInt(360)
            );
            bigsmoke.tracker = ChlorineCloud.trssh;
            bigsmoke.alpha = 0.0F;
            projectile.world.spawnEntity(bigsmoke);
         }
      }

      public void gas(World world, @Nullable EntityLivingBase player, double x, double y, double z, EntityGrenade projectile) {
         if (!world.isRemote) {
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
            double damageRadius = this.damageRadius() + (projectile != null ? Math.min(projectile.explodes, parameters.geti("grow_limit")) : 0);
            AxisAlignedBB axisalignedbb = new AxisAlignedBB(
               x - damageRadius, y - damageRadius, z - damageRadius, x + damageRadius, y + damageRadius, z + damageRadius
            );
            List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(projectile, axisalignedbb);
            if (!list.isEmpty()) {
               for (Entity entity : list) {
                  Weapons.mixPotion(
                     entity,
                     PotionEffects.CHLORITE,
                     (float)parameters.geti("potion_time_add"),
                     (float)parameters.geti("potion_power_add"),
                     Weapons.EnumPotionMix.WITH_MAXIMUM,
                     Weapons.EnumPotionMix.WITH_MAXIMUM,
                     Weapons.EnumMathOperation.PLUS,
                     Weapons.EnumMathOperation.PLUS,
                     parameters.geti("potion_time_max"),
                     parameters.geti("potion_power_max")
                  );
                  if (entity.isBurning()) {
                     this.explode(world, projectile.getThrower(), x, y, z, null, projectile);
                     world.setEntityState(projectile, (byte)-1);
                  }
               }
            }

            for (BlockPos pos : GetMOP.getPosesInsideSphere(
               MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z), MathHelper.floor(damageRadius)
            )) {
               if (Weapons.isBlockFiery(world, world.getBlockState(pos).getBlock())) {
                  this.explode(world, projectile.getThrower(), x, y, z, null, projectile);
                  world.setEntityState(projectile, (byte)-1);
               }
            }

            if (projectile != null) {
               projectile.explodes++;
               if (projectile.explodes >= WeaponParameters.getWeaponParameters(this).geti("max_explosions")) {
                  projectile.setDead();
               }
            }
         } else {
            projectile.explodes++;
            if (projectile.explodes == 1) {
               world.playSound(x, y, z, Sounds.chlorine_belcher, SoundCategory.PLAYERS, 0.9F, 0.85F + itemRand.nextFloat() * 0.3F, false);

               for (int ss = 0; ss < 15; ss++) {
                  world.spawnParticle(
                     EnumParticleTypes.CLOUD,
                     x,
                     y,
                     z,
                     itemRand.nextGaussian() / 15.0,
                     itemRand.nextGaussian() / 25.0,
                     itemRand.nextGaussian() / 15.0,
                     new int[0]
                  );
               }
            }
         }
      }

      @Override
      public void explode(
         World world, @Nullable EntityLivingBase player, double x, double y, double z, @Nullable RayTraceResult result, @Nullable EntityGrenade projectile
      ) {
         WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
         double damageRadius = this.damageRadius() + (projectile != null ? Math.min(projectile.explodes, parameters.geti("grow_limit")) : 0);
         if (!world.isRemote) {
            AxisAlignedBB axisalignedbb = new AxisAlignedBB(
               x - damageRadius, y - damageRadius, z - damageRadius, x + damageRadius, y + damageRadius, z + damageRadius
            );
            List<Entity> list = world.getEntitiesWithinAABB(Entity.class, axisalignedbb);
            if (!list.isEmpty()) {
               ItemStack stack = new ItemStack(this);

               for (Entity entity : list) {
                  if (Team.checkIsOpponent(player, entity)) {
                     Weapons.dealDamage(
                        new WeaponDamage(stack, player, projectile, true, false, null, WeaponDamage.explode),
                        this.damage(),
                        player,
                        entity,
                        true,
                        this.knockback(),
                        x,
                        y,
                        z
                     );
                     entity.hurtResistantTime = 0;
                     entity.setFire(4);
                  } else {
                     Weapons.dealDamage(
                        new WeaponDamage(stack, player, projectile, true, false, null, WeaponDamage.explode),
                        this.damage() * 0.7F,
                        player,
                        entity,
                        true,
                        this.knockback(),
                        x,
                        y,
                        z
                     );
                     entity.setFire(3);
                  }
               }
            }

            for (BlockPos pos : GetMOP.getPosesInsideSphere(
               MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z), Math.round((float)damageRadius)
            )) {
               Weapons.triggerExplodeBlock(world, pos, player, projectile);
            }

            if (projectile != null) {
               projectile.setDead();
            }
         } else {
            world.playSound(x, y, z, Sounds.explode7, SoundCategory.PLAYERS, 1.7F, 0.85F + itemRand.nextFloat() / 5.0F, false);
            float gausMult = (float)(0.06 * damageRadius);
            AxisAlignedBB axisalignedbbx = new AxisAlignedBB(
               x - damageRadius, y - damageRadius, z - damageRadius, x + damageRadius, y + damageRadius, z + damageRadius
            );
            List<GUNParticle> listx = world.getEntitiesWithinAABB(GUNParticle.class, axisalignedbbx);
            if (!listx.isEmpty()) {
               for (GUNParticle entityx : listx) {
                  if (entityx.texture == largecloud && entityx.light == 111) {
                     entityx.setDead();
                  }
               }
            }

            for (int ss = 0; ss < 7; ss++) {
               GUNParticle bigsmoke = new GUNParticle(
                  largesmoke,
                  0.8F + itemRand.nextFloat() / 2.0F,
                  -0.001F,
                  40,
                  60,
                  world,
                  x,
                  y,
                  z,
                  (float)itemRand.nextGaussian() * gausMult,
                  (float)itemRand.nextGaussian() * gausMult,
                  (float)itemRand.nextGaussian() * gausMult,
                  1.0F,
                  1.0F,
                  1.0F,
                  true,
                  itemRand.nextInt(360)
               );
               bigsmoke.alphaTickAdding = -0.01F;
               world.spawnEntity(bigsmoke);
            }

            for (int ss = 0; ss < 13.0 + damageRadius; ss++) {
               int lt = 14 + itemRand.nextInt(8);
               GUNParticle fire = new GUNParticle(
                  flame,
                  0.4F + itemRand.nextFloat() * 1.6F,
                  -0.003F,
                  lt,
                  240,
                  world,
                  x,
                  y,
                  z,
                  (float)itemRand.nextGaussian() * gausMult,
                  (float)itemRand.nextGaussian() * gausMult,
                  (float)itemRand.nextGaussian() * gausMult,
                  1.0F - itemRand.nextFloat() * 0.2F,
                  1.0F - itemRand.nextFloat() * 0.2F,
                  0.7F,
                  true,
                  itemRand.nextInt(100) - 50
               );
               fire.alphaTickAdding = -1.0F / lt;
               fire.alphaGlowing = true;
               fire.tracker = ItemRocket.trackerExplode1;
               world.spawnEntity(fire);
            }

            for (int ss = 0; ss < 35; ss++) {
               world.spawnParticle(
                  EnumParticleTypes.SMOKE_LARGE,
                  x + itemRand.nextGaussian(),
                  y + itemRand.nextGaussian(),
                  z + itemRand.nextGaussian(),
                  itemRand.nextGaussian() * gausMult,
                  itemRand.nextGaussian() * gausMult,
                  itemRand.nextGaussian() * gausMult,
                  new int[0]
               );
            }
         }
      }

      @SideOnly(Side.CLIENT)
      @Override
      public void renderGrenade(
         @Nullable EntityGrenade entity, double x, double y, double z, float entityYaw, float partialTicks, @Nullable Render render, boolean forEntity
      ) {
         if (entity == null || entity.explodes <= 0) {
            GlStateManager.pushMatrix();
            if (forEntity) {
               GlStateManager.translate((float)x, (float)y + 0.1F, (float)z);
               GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
               GlStateManager.rotate(
                  (entity.prevrotationProgr + (entity.rotationProgr - entity.prevrotationProgr) * partialTicks) * 8.2F,
                  (float)entity.randomRotate.x,
                  (float)entity.randomRotate.y,
                  (float)entity.randomRotate.z
               );
            }

            GlStateManager.disableCull();
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texture);
            mainModel.hideAll();
            mainModel.shapemain.isHidden = false;
            mainModel.shapeup2.isHidden = false;
            mainModel.shapedown2.isHidden = false;
            mainModel.pinhandle1.isHidden = false;
            mainModel.pinhandle2.isHidden = false;
            mainModel.render(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
            GlStateManager.enableCull();
            GlStateManager.popMatrix();
         }
      }

      @Override
      public void onProjectileImpact(EntityGrenade projectile, @Nullable RayTraceResult result) {
         float bouncePower = projectile.impacts > 0 ? 0.1F : 0.7F;
         if (projectile.bounce(result, bouncePower)) {
            if (projectile.world.isRemote && projectile.getspeedSq() > 0.05F) {
               projectile.randomRotate = new Vec3d(itemRand.nextGaussian(), itemRand.nextGaussian(), itemRand.nextGaussian());
            }

            projectile.slowdown(bouncePower);
         }

         if (result != null
            && result.entityHit != null
            && result.entityHit instanceof EntityLivingBase
            && result.entityHit != projectile.getThrower()) {
            projectile.slowdown(bouncePower);
         }
      }
   }

   public static class GravityGrenade extends ItemGrenade {
      public static ParticleTracker.TrackerMagicSpin tms = new ParticleTracker.TrackerMagicSpin(false, -5.0F);
      ModelSphere sphere = new ModelSphere(0.25F, 6);

      public GravityGrenade(String name, int maxstacksize, byte id, int firstExplodeDelay, float damage, float knockback, ResourceLocation texture) {
         super(name, maxstacksize, id, firstExplodeDelay, damage, knockback, texture);
      }

      @Override
      public void onProjectileUpdate(EntityGrenade projectile) {
         if (!projectile.world.isRemote) {
            super.onProjectileUpdate(projectile);
         } else if (projectile.explodes < 1) {
            projectile.world
               .spawnParticle(EnumParticleTypes.PORTAL, projectile.posX, projectile.posY, projectile.posZ, 0.0, 0.0, 0.0, new int[0]);
         }
      }

      @Override
      public void explode(
         World world, @Nullable EntityLivingBase player, double x, double y, double z, @Nullable RayTraceResult result, @Nullable EntityGrenade projectile
      ) {
         WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
         float damageRadius = parameters.get("damage_radius");
         float gravity1 = parameters.get("gravity");
         float gravity2 = parameters.get("friendlyfire_gravity");
         int livetime = parameters.geti("livetime");
         if (!world.isRemote) {
            AxisAlignedBB axisalignedbb = new AxisAlignedBB(
               x - damageRadius, y - damageRadius, z - damageRadius, x + damageRadius, y + damageRadius, z + damageRadius
            );
            List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(projectile, axisalignedbb);
            if (!list.isEmpty()) {
               for (Entity entity : list) {
                  double dist = entity.getDistance(x, y, z);
                  if (dist <= damageRadius) {
                     float distMax = (float)Math.max(dist, 1.0);
                     if (Team.checkIsOpponent(player, entity)) {
                        SuperKnockback.applyKnockback(entity, -gravity1 / distMax, x, y, z);
                     } else {
                        SuperKnockback.applyKnockback(entity, -gravity2 / distMax, x, y, z);
                     }
                  }
               }
            }

            if (projectile != null) {
               projectile.explodes++;
               if (projectile.explodes >= livetime) {
                  projectile.setDead();
               } else if (projectile.explodes >= 1) {
                  projectile.motionX /= 2.0;
                  projectile.motionY /= 2.0;
                  projectile.motionZ /= 2.0;
                  projectile.setNoGravity(true);
                  Vec3d posvec = projectile.getPositionVector();
                  if (!GetMOP.thereIsNoBlockCollidesBetween(world, posvec, posvec.add(0.0, -0.45, 0.0), 1.0F, null, false)) {
                     projectile.motionY += 0.15;
                  }
               }
            }
         } else if (projectile != null) {
            if (projectile.explodes == 0) {
               world.playSound(x, y, z, Sounds.gravity_grenade, SoundCategory.PLAYERS, 1.3F, 1.0F, false);
            }

            projectile.explodes++;
            ParticleTracker.TrackerFollowDynamicPoint tracker = new ParticleTracker.TrackerFollowDynamicPoint(
               projectile, false, 1.0F, 0.0F + Debugger.floats[0], 0.08F + Debugger.floats[1]
            );
            tracker.frictionMult = 1.0F;
            tracker.tickfrictionAdd = -0.001F;
            int yawRand = itemRand.nextInt(360);
            Vec3d poss = GetMOP.PitchYawToVec3d(15 - itemRand.nextInt(31), yawRand).scale(damageRadius).add(x, y, z);
            Vec3d direction = GetMOP.YawToVec3d(yawRand - 90).scale(0.5);
            float scl = 0.05F + itemRand.nextFloat() * 0.05F;
            int lt = 60 + itemRand.nextInt(10);
            GUNParticle part = new GUNParticle(
               star,
               scl,
               0.0F,
               lt,
               240,
               world,
               poss.x,
               poss.y,
               poss.z,
               (float)direction.x,
               (float)direction.y,
               (float)direction.z,
               0.5F + itemRand.nextFloat() * 0.5F,
               0.5F + itemRand.nextFloat() * 0.3F,
               1.0F,
               false,
               itemRand.nextInt(360)
            );
            part.scaleTickAdding = scl / lt / 2.0F;
            part.tracker = tracker;
            world.spawnEntity(part);
            if (projectile.ticksExisted % 20 == 0) {
               float scale = itemRand.nextFloat() * 1.5F + 4.5F;
               GUNParticle partx = new GUNParticle(
                  whirl, scale, 0.0F, 50, 210, world, x, y, z, 0.0F, 0.0F, 0.0F, 1.0F, 0.8F, 1.0F, true, itemRand.nextInt(360)
               );
               partx.tracker = tms;
               partx.rotationPitchYaw = new Vec2f(90.0F + (float)itemRand.nextGaussian(), itemRand.nextInt(360));
               partx.alphaTickAdding = 0.05F;
               partx.alpha = 0.0F;
               partx.scaleTickAdding = projectile.explodes > 150 ? -scale / 50.0F : -scale / 70.0F;
               partx.isPushedByLiquids = false;
               partx.randomDeath = false;
               world.spawnEntity(partx);
            }

            if (projectile.ticksExisted % 2 == 0) {
               AnimatedGParticle anim = new AnimatedGParticle(
                  TileSpellForge.forge_absorption,
                  1.4F + itemRand.nextFloat() * 0.8F,
                  0.0F,
                  28,
                  240,
                  world,
                  x,
                  y,
                  z,
                  0.0F,
                  0.0F,
                  0.0F,
                  0.67F,
                  0.36F,
                  0.6F + itemRand.nextFloat() * 0.3F,
                  true,
                  itemRand.nextInt(360)
               );
               anim.framecount = 25;
               anim.alphaGlowing = true;
               anim.randomDeath = false;
               anim.rotationPitchYaw = new Vec2f(itemRand.nextInt(360), itemRand.nextInt(360));
               anim.isPushedByLiquids = false;
               world.spawnEntity(anim);
            }
         }
      }

      @SideOnly(Side.CLIENT)
      @Override
      public void renderGrenade(
         @Nullable EntityGrenade entity, double x, double y, double z, float entityYaw, float partialTicks, @Nullable Render render, boolean forEntity
      ) {
         GlStateManager.pushMatrix();
         boolean renderGrenade = true;
         if (forEntity) {
            GlStateManager.translate((float)x, (float)y + 0.1F, (float)z);
            GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(
               (entity.prevrotationProgr + (entity.rotationProgr - entity.prevrotationProgr) * partialTicks) * 8.2F,
               (float)entity.randomRotate.x,
               (float)entity.randomRotate.y,
               (float)entity.randomRotate.z
            );
         }

         if (entity != null && entity.explodes >= 1) {
            float scale = MathHelper.clamp(0.2F + entity.explodes * 0.2F, 0.2F, 1.0F);
            GlStateManager.color(0.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.pushMatrix();
            GlStateManager.scale(scale, scale, scale);
            GlStateManager.disableTexture2D();
            this.sphere.render();
            GlStateManager.enableTexture2D();
            GlStateManager.popMatrix();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            if (entity.explodes >= 5) {
               renderGrenade = false;
            }
         }

         if (renderGrenade) {
            float scale = 0.2F;
            GlStateManager.color(0.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.pushMatrix();
            GlStateManager.scale(scale, scale, scale);
            GlStateManager.disableTexture2D();
            this.sphere.render();
            GlStateManager.enableTexture2D();
            GlStateManager.popMatrix();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.disableCull();
            GlStateManager.enableBlend();
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texture);
            mainModel.hideAll();
            mainModel.shapemain.isHidden = false;
            mainModel.pin.isHidden = false;
            mainModel.shapeup2.isHidden = false;
            mainModel.shapedown2.isHidden = false;
            mainModel.render(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
            GlStateManager.disableBlend();
            GlStateManager.enableCull();
         }

         GlStateManager.popMatrix();
      }
   }

   public static class HellGrenade extends ItemGrenade {
      public HellGrenade(String name, int maxstacksize, byte id, int firstExplodeDelay, float damage, float knockback, ResourceLocation texture) {
         super(name, maxstacksize, id, firstExplodeDelay, damage, knockback, texture);
      }

      @Override
      public void explode(
         World world, @Nullable EntityLivingBase player, double x, double y, double z, @Nullable RayTraceResult result, @Nullable EntityGrenade projectile
      ) {
         if (!world.isRemote) {
            double damageRadius = this.damageRadius();
            AxisAlignedBB axisalignedbb = new AxisAlignedBB(
               x - damageRadius, y - damageRadius, z - damageRadius, x + damageRadius, y + damageRadius, z + damageRadius
            );
            List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(projectile, axisalignedbb);
            if (!list.isEmpty()) {
               ItemStack stack = new ItemStack(this);

               for (Entity entity : list) {
                  if (entity != projectile) {
                     if (Team.checkIsOpponent(player, entity)) {
                        Weapons.dealDamage(
                           new WeaponDamage(stack, player, projectile, true, false, new Vec3d(x, y, z), WeaponDamage.explode),
                           this.damage(),
                           player,
                           entity,
                           true,
                           this.knockback(),
                           x,
                           y,
                           z
                        );
                        entity.hurtResistantTime = 0;
                     } else {
                        Weapons.dealDamage(
                           new WeaponDamage(stack, player, projectile, true, false, new Vec3d(x, y, z), WeaponDamage.explode),
                           this.damage() * 0.7F,
                           player,
                           entity,
                           true,
                           this.knockback(),
                           x,
                           y,
                           z
                        );
                        entity.hurtResistantTime = 0;
                     }
                  }
               }
            }

            for (BlockPos pos : GetMOP.getPosesInsideSphere(
               MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z), (int)damageRadius
            )) {
               Weapons.triggerExplodeBlock(world, pos, player, projectile);
            }

            if (projectile != null) {
               projectile.explodes++;
               projectile.flyingTime = this.firstExplodeDelay() - 18 + projectile.explodes;
               projectile.impacts = 0;
               if (projectile.explodes >= WeaponParameters.getWeaponParameters(this).geti("max_explosions")) {
                  projectile.setDead();
               }
            }
         } else {
            world.playSound(x, y, z, Sounds.explode6, SoundCategory.PLAYERS, 1.3F, 0.85F + itemRand.nextFloat() * 0.3F, false);

            for (int ss = 0; ss < 10; ss++) {
               int lt = 8 + itemRand.nextInt(7);
               GUNParticle fire = new GUNParticle(
                  flame,
                  0.1F + (float)itemRand.nextGaussian() / 6.0F,
                  -0.003F,
                  lt,
                  240,
                  world,
                  x,
                  y,
                  z,
                  (float)itemRand.nextGaussian() / 7.0F,
                  (float)itemRand.nextGaussian() / 7.0F,
                  (float)itemRand.nextGaussian() / 7.0F,
                  1.0F - itemRand.nextFloat() * 0.2F,
                  0.5F - itemRand.nextFloat() * 0.4F,
                  1.0F,
                  true,
                  itemRand.nextInt(100) - 50
               );
               fire.alphaTickAdding = -1.0F / lt;
               fire.alphaGlowing = true;
               fire.tracker = ItemRocket.trackerExplode1;
               world.spawnEntity(fire);
            }

            for (int ss = 0; ss < 3; ss++) {
               float fsize = (float)(2.0 + itemRand.nextGaussian() / 6.0);
               int lt = 4 + itemRand.nextInt(3);
               GUNParticle part = new GUNParticle(
                  firecircle,
                  0.4F,
                  0.0F,
                  lt,
                  240,
                  world,
                  x,
                  y,
                  z,
                  0.0F,
                  0.0F,
                  0.0F,
                  1.0F,
                  0.4F + itemRand.nextFloat() * 0.4F,
                  1.0F,
                  true,
                  itemRand.nextInt(360)
               );
               part.scaleTickAdding = fsize / lt;
               part.alphaGlowing = true;
               part.alphaTickAdding = -0.8F / lt;
               part.randomDeath = false;
               part.rotationPitchYaw = new Vec2f(itemRand.nextFloat() * 360.0F, itemRand.nextFloat() * 360.0F);
               world.spawnEntity(part);
            }

            float fsize = (float)(2.0 + itemRand.nextGaussian() / 6.0);
            int lt = 4 + itemRand.nextInt(3);
            GUNParticle part = new GUNParticle(
               firecircle,
               0.4F,
               0.0F,
               lt,
               240,
               world,
               x,
               y,
               z,
               0.0F,
               0.0F,
               0.0F,
               1.0F,
               0.4F + itemRand.nextFloat() * 0.4F,
               1.0F,
               true,
               itemRand.nextInt(360)
            );
            part.scaleTickAdding = fsize / lt;
            part.alphaGlowing = true;
            part.alphaTickAdding = -0.8F / lt;
            part.randomDeath = false;
            world.spawnEntity(part);
         }
      }

      @SideOnly(Side.CLIENT)
      @Override
      public void renderGrenade(
         @Nullable EntityGrenade entity, double x, double y, double z, float entityYaw, float partialTicks, @Nullable Render render, boolean forEntity
      ) {
         GlStateManager.pushMatrix();
         if (forEntity) {
            GlStateManager.translate((float)x, (float)y + 0.1F, (float)z);
            GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(
               (entity.prevrotationProgr + (entity.rotationProgr - entity.prevrotationProgr) * partialTicks) * 8.2F,
               (float)entity.randomRotate.x,
               (float)entity.randomRotate.y,
               (float)entity.randomRotate.z
            );
         }

         GlStateManager.disableCull();
         Minecraft.getMinecraft().renderEngine.bindTexture(this.texture);
         mainModel.hideAll();
         mainModel.shapemain.isHidden = false;
         AbstractMobModel.light(200, false);
         mainModel.render(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
         AbstractMobModel.returnlight();
         mainModel.shapemain.isHidden = true;
         mainModel.stick1.isHidden = false;
         mainModel.stick2.isHidden = false;
         mainModel.stick3.isHidden = false;
         mainModel.stick4.isHidden = false;
         mainModel.big.isHidden = false;
         mainModel.shapedown2.isHidden = false;
         mainModel.shapeup2.isHidden = false;
         mainModel.render(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
         GlStateManager.enableCull();
         GlStateManager.popMatrix();
      }

      @Override
      public void onProjectileImpact(EntityGrenade projectile, @Nullable RayTraceResult result) {
         if (projectile.bounce(result, 0.666)) {
            if (projectile.world.isRemote && projectile.getspeedSq() > 0.05F) {
               projectile.randomRotate = new Vec3d(itemRand.nextGaussian(), itemRand.nextGaussian(), itemRand.nextGaussian());
            }

            projectile.slowdown(0.78);
            if (!projectile.world.isRemote && projectile.explodes == 0) {
               this.explode(
                  projectile.world,
                  projectile.getThrower(),
                  projectile.posX,
                  projectile.posY,
                  projectile.posZ,
                  result,
                  projectile
               );
               projectile.world.setEntityState(projectile, (byte)-1);
            }
         }

         if (result != null
            && result.entityHit != null
            && result.entityHit instanceof EntityLivingBase
            && result.entityHit != projectile.getThrower()) {
            projectile.slowdown(0.9);
            if (!projectile.world.isRemote && projectile.explodes == 0) {
               this.explode(
                  projectile.world,
                  projectile.getThrower(),
                  projectile.posX,
                  projectile.posY,
                  projectile.posZ,
                  result,
                  projectile
               );
               projectile.world.setEntityState(projectile, (byte)-1);
            }
         }
      }
   }

   public static class MolotovпїЅocktail extends ItemGrenade {
      public MolotovпїЅocktail(String name, int maxstacksize, byte id, int firstExplodeDelay, float damage, float knockback, ResourceLocation texture) {
         super(name, maxstacksize, id, firstExplodeDelay, damage, knockback, texture);
      }

      @Override
      public void onProjectileUpdate(EntityGrenade projectile) {
         if (!projectile.world.isRemote) {
            if (projectile.flyingTime > projectile.grenade.firstExplodeDelay() && !projectile.isDead) {
               projectile.grenade
                  .explode(
                     projectile.world,
                     projectile.getThrower(),
                     projectile.posX,
                     projectile.posY,
                     projectile.posZ,
                     null,
                     projectile
                  );
               projectile.world.setEntityState(projectile, (byte)-1);
            }
         } else {
            projectile.world
               .spawnParticle(EnumParticleTypes.FLAME, projectile.posX, projectile.posY, projectile.posZ, 0.0, 0.0, 0.0, new int[0]);
         }
      }

      @Override
      public void explode(
         World world, @Nullable EntityLivingBase player, double x, double y, double z, @Nullable RayTraceResult result, @Nullable EntityGrenade projectile
      ) {
         if (!world.isRemote) {
            double damageRadius = this.damageRadius() + (projectile != null ? projectile.explodes : 0);
            AxisAlignedBB axisalignedbb = new AxisAlignedBB(
               x - damageRadius, y - damageRadius, z - damageRadius, x + damageRadius, y + damageRadius, z + damageRadius
            );
            List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(projectile, axisalignedbb);
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
            int fire1 = parameters.geti("fire");
            int fire2 = parameters.geti("friendlyfire_fire");
            if (!list.isEmpty()) {
               for (Entity entity : list) {
                  if (Team.checkIsOpponent(player, entity)) {
                     entity.setFire(fire1);
                  } else {
                     entity.setFire(fire2);
                  }
               }
            }

            for (BlockPos pos : GetMOP.getPosesInsideSphere(
               MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z), MathHelper.floor(damageRadius)
            )) {
               if (itemRand.nextFloat() < 0.4) {
                  boolean isnotl = !world.getBlockState(pos).getMaterial().isLiquid();
                  if (isnotl && world.getBlockState(pos).getBlock().isReplaceable(world, pos) && Blocks.FIRE.canPlaceBlockAt(world, pos)) {
                     world.setBlockState(pos, Blocks.FIRE.getDefaultState());
                  }
               }
            }

            if (projectile != null) {
               projectile.explodes++;
               projectile.flyingTime = this.firstExplodeDelay() - 15;
               if (projectile.explodes >= WeaponParameters.getWeaponParameters(this).geti("max_explosions")) {
                  projectile.setDead();
               }
            }
         } else {
            projectile.explodes++;
            if (projectile.explodes == 1) {
               world.playSound(x, y, z, SoundEvents.ENTITY_SPLASH_POTION_BREAK, SoundCategory.PLAYERS, 0.8F, 0.85F + itemRand.nextFloat() * 0.3F, false);
               world.playSound(x, y, z, Sounds.burn, SoundCategory.PLAYERS, 0.9F, 0.85F + itemRand.nextFloat() * 0.3F, false);
               world.playSound(x, y, z, Sounds.fire_a, SoundCategory.PLAYERS, 1.1F, 0.8F + itemRand.nextFloat() * 0.3F, false);

               for (int ss = 0; ss < 45; ss++) {
                  world.spawnParticle(
                     EnumParticleTypes.FLAME,
                     x,
                     y,
                     z,
                     itemRand.nextGaussian() / 15.0,
                     itemRand.nextGaussian() / 25.0,
                     itemRand.nextGaussian() / 15.0,
                     new int[0]
                  );
               }

               for (int ss = 0; ss < 15; ss++) {
                  world.spawnParticle(
                     EnumParticleTypes.SMOKE_LARGE,
                     x,
                     y,
                     z,
                     itemRand.nextGaussian() / 15.0,
                     itemRand.nextGaussian() / 25.0,
                     itemRand.nextGaussian() / 15.0,
                     new int[0]
                  );
               }

               for (int ss = 0; ss < 4; ss++) {
                  GUNParticle part = new GUNParticle(
                     CrystalFanShoot.shards[itemRand.nextInt(3)],
                     0.08F + itemRand.nextFloat() / 30.0F,
                     0.08F,
                     30 + itemRand.nextInt(10),
                     -1,
                     world,
                     x,
                     y,
                     z,
                     (float)itemRand.nextGaussian() / 13.0F,
                     (float)itemRand.nextGaussian() / 13.0F + 0.1F,
                     (float)itemRand.nextGaussian() / 13.0F,
                     1.0F,
                     1.0F,
                     1.0F,
                     false,
                     itemRand.nextInt(360),
                     true,
                     1.3F
                  );
                  part.dropMode = true;
                  part.rotationPitchYaw = new Vec2f(itemRand.nextInt(360), itemRand.nextInt(360));
                  part.tracker = new ParticleTracker.TrackerGlassShard(
                     (float)itemRand.nextGaussian() * 2.0F, (float)itemRand.nextGaussian() * 2.0F, (int)itemRand.nextGaussian() * 2, false
                  );
                  world.spawnEntity(part);
               }
            }
         }
      }

      @SideOnly(Side.CLIENT)
      @Override
      public void renderGrenade(
         @Nullable EntityGrenade entity, double x, double y, double z, float entityYaw, float partialTicks, @Nullable Render render, boolean forEntity
      ) {
         if (entity == null || entity.explodes <= 0) {
            GlStateManager.pushMatrix();
            if (forEntity) {
               GlStateManager.translate((float)x, (float)y + 0.1F, (float)z);
               GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
               GlStateManager.rotate(
                  (entity.prevrotationProgr + (entity.rotationProgr - entity.prevrotationProgr) * partialTicks) * 8.2F,
                  (float)entity.randomRotate.x,
                  (float)entity.randomRotate.y,
                  (float)entity.randomRotate.z
               );
            }

            GlStateManager.disableCull();
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texture);
            mainModel.hideAll();
            mainModel.shapemain.isHidden = false;
            mainModel.shapeup2.isHidden = false;
            mainModel.shapestick.isHidden = false;
            AbstractMobModel.light(60, true);
            mainModel.render(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
            AbstractMobModel.returnlight();
            GlStateManager.enableCull();
            GlStateManager.popMatrix();
         }
      }

      @Override
      public void onProjectileImpact(EntityGrenade projectile, @Nullable RayTraceResult result) {
         if (projectile.bounce(result, 0.1)) {
            if (projectile.world.isRemote && projectile.getspeedSq() > 0.05F) {
               projectile.randomRotate = new Vec3d(itemRand.nextGaussian(), itemRand.nextGaussian(), itemRand.nextGaussian());
            }

            projectile.slowdown(0.1);
            if (!projectile.world.isRemote && projectile.explodes == 0) {
               this.explode(
                  projectile.world,
                  projectile.getThrower(),
                  projectile.posX,
                  projectile.posY,
                  projectile.posZ,
                  result,
                  projectile
               );
               projectile.world.setEntityState(projectile, (byte)-1);
            }
         }

         if (result != null
            && result.entityHit != null
            && result.entityHit instanceof EntityLivingBase
            && result.entityHit != projectile.getThrower()) {
            projectile.slowdown(0.1);
            if (!projectile.world.isRemote && projectile.explodes == 0) {
               this.explode(
                  projectile.world,
                  projectile.getThrower(),
                  projectile.posX,
                  projectile.posY,
                  projectile.posZ,
                  result,
                  projectile
               );
               projectile.world.setEntityState(projectile, (byte)-1);
            }
         }
      }
   }

   public static class OilBottle extends ItemGrenade {
      public OilBottle(String name, int maxstacksize, byte id, int firstExplodeDelay, float damage, float knockback, ResourceLocation texture) {
         super(name, maxstacksize, id, firstExplodeDelay, damage, knockback, texture);
      }

      @Override
      public void explode(
         World world, @Nullable EntityLivingBase player, double x, double y, double z, @Nullable RayTraceResult result, @Nullable EntityGrenade projectile
      ) {
         if (!world.isRemote) {
            double damageRadius = this.damageRadius();
            AxisAlignedBB axisalignedbb = new AxisAlignedBB(
               x - damageRadius, y - damageRadius, z - damageRadius, x + damageRadius, y + damageRadius, z + damageRadius
            );
            List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(projectile, axisalignedbb);
            if (!list.isEmpty()) {
               WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
               ItemStack stack = new ItemStack(this);

               for (Entity entity : list) {
                  Weapons.dealDamage(
                     new WeaponDamage(stack, player, projectile, true, false, new Vec3d(x, y, z), WeaponDamage.shards),
                     this.damage(),
                     player,
                     entity,
                     true,
                     this.knockback(),
                     x,
                     y,
                     z
                  );
                  entity.hurtResistantTime = 0;
                  Weapons.mixPotion(
                     entity,
                     PotionEffects.FIERYOIL,
                     (float)parameters.geti("potion_time"),
                     (float)parameters.geti("potion_power_add"),
                     Weapons.EnumPotionMix.GREATEST,
                     Weapons.EnumPotionMix.WITH_MAXIMUM,
                     Weapons.EnumMathOperation.NONE,
                     Weapons.EnumMathOperation.PLUS,
                     0,
                     parameters.geti("potion_power_max")
                  );
               }
            }

            if (projectile != null) {
               projectile.setDead();
            }
         } else {
            world.playSound(x, y, z, SoundEvents.ENTITY_SPLASH_POTION_BREAK, SoundCategory.PLAYERS, 0.8F, 0.85F + itemRand.nextFloat() * 0.3F, false);
            world.playSound(x, y, z, Sounds.explode_slime, SoundCategory.PLAYERS, 1.0F, 0.85F + itemRand.nextFloat() * 0.3F, false);

            for (int ss = 0; ss < 20; ss++) {
               int lt = 30 + itemRand.nextInt(20);
               GUNParticle bigsmoke = new GUNParticle(
                  oildrop,
                  0.1F + itemRand.nextFloat() * 0.1F,
                  0.025F,
                  lt,
                  -1,
                  world,
                  x,
                  y,
                  z,
                  (float)(itemRand.nextGaussian() / 9.0),
                  (float)(itemRand.nextGaussian() / 9.0) + 0.1F,
                  (float)(itemRand.nextGaussian() / 9.0),
                  0.8F + itemRand.nextFloat() * 0.2F,
                  1.0F,
                  1.0F,
                  false,
                  itemRand.nextInt(360),
                  true,
                  2.0F
               );
               bigsmoke.dropMode = true;
               world.spawnEntity(bigsmoke);
            }

            for (int ss = 0; ss < 7; ss++) {
               float fsize = (float)(3.0 + itemRand.nextGaussian() / 5.0);
               int lt = 10 + itemRand.nextInt(8);
               GUNParticle part = new GUNParticle(
                  slimesplash,
                  0.4F,
                  0.0F,
                  lt,
                  -1,
                  world,
                  x + itemRand.nextGaussian() / 2.0,
                  y,
                  z + itemRand.nextGaussian() / 2.0,
                  (float)itemRand.nextGaussian() / 16.0F,
                  (float)itemRand.nextGaussian() / 16.0F,
                  (float)itemRand.nextGaussian() / 16.0F,
                  0.7F,
                  0.5F + itemRand.nextFloat() * 0.15F,
                  0.1F + itemRand.nextFloat() * 0.2F,
                  true,
                  itemRand.nextInt(360)
               );
               part.scaleTickAdding = fsize / lt;
               part.alphaTickAdding = -0.8F / lt;
               part.randomDeath = false;
               part.rotationPitchYaw = new Vec2f(itemRand.nextFloat() * 360.0F, itemRand.nextFloat() * 360.0F);
               world.spawnEntity(part);
            }

            for (int ss = 0; ss < 4; ss++) {
               GUNParticle part = new GUNParticle(
                  CrystalFanShoot.shards[itemRand.nextInt(3)],
                  0.08F + itemRand.nextFloat() / 30.0F,
                  0.05F,
                  30 + itemRand.nextInt(10),
                  -1,
                  world,
                  x,
                  y,
                  z,
                  (float)itemRand.nextGaussian() / 13.0F,
                  (float)itemRand.nextGaussian() / 13.0F + 0.1F,
                  (float)itemRand.nextGaussian() / 13.0F,
                  1.0F,
                  1.0F,
                  1.0F,
                  false,
                  itemRand.nextInt(360),
                  true,
                  1.3F
               );
               part.dropMode = true;
               part.rotationPitchYaw = new Vec2f(itemRand.nextInt(360), itemRand.nextInt(360));
               part.tracker = new ParticleTracker.TrackerGlassShard(
                  (float)itemRand.nextGaussian() * 2.0F, (float)itemRand.nextGaussian() * 2.0F, (int)itemRand.nextGaussian() * 2, false
               );
               world.spawnEntity(part);
            }
         }
      }

      @Override
      public void onProjectileImpact(EntityGrenade projectile, @Nullable RayTraceResult result) {
         if (projectile.bounce(result, 0.1)) {
            if (projectile.world.isRemote && projectile.getspeedSq() > 0.05F) {
               projectile.randomRotate = new Vec3d(itemRand.nextGaussian(), itemRand.nextGaussian(), itemRand.nextGaussian());
            }

            projectile.slowdown(0.1);
            if (!projectile.world.isRemote) {
               this.explode(
                  projectile.world,
                  projectile.getThrower(),
                  projectile.posX,
                  projectile.posY,
                  projectile.posZ,
                  result,
                  projectile
               );
               projectile.world.setEntityState(projectile, (byte)-1);
            }
         }

         if (result != null
            && result.entityHit != null
            && result.entityHit instanceof EntityLivingBase
            && result.entityHit != projectile.getThrower()) {
            projectile.slowdown(0.1);
            if (!projectile.world.isRemote) {
               this.explode(
                  projectile.world,
                  projectile.getThrower(),
                  projectile.posX,
                  projectile.posY,
                  projectile.posZ,
                  result,
                  projectile
               );
               projectile.world.setEntityState(projectile, (byte)-1);
            }
         }
      }

      @SideOnly(Side.CLIENT)
      @Override
      public void renderGrenade(
         @Nullable EntityGrenade entity, double x, double y, double z, float entityYaw, float partialTicks, @Nullable Render render, boolean forEntity
      ) {
         GlStateManager.pushMatrix();
         if (forEntity) {
            GlStateManager.translate((float)x, (float)y + 0.1F, (float)z);
            GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(
               (entity.prevrotationProgr + (entity.rotationProgr - entity.prevrotationProgr) * partialTicks) * 8.2F,
               (float)entity.randomRotate.x,
               (float)entity.randomRotate.y,
               (float)entity.randomRotate.z
            );
         }

         GlStateManager.disableCull();
         Minecraft.getMinecraft().renderEngine.bindTexture(this.texture);
         mainModel.hideAll();
         mainModel.big.isHidden = false;
         mainModel.shapestick.isHidden = false;
         mainModel.shapeup2.isHidden = false;
         mainModel.render(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
         GlStateManager.enableCull();
         GlStateManager.popMatrix();
      }
   }

   public static class SeaGrenade extends ItemGrenade {
      public SeaGrenade(String name, int maxstacksize, byte id, int firstExplodeDelay, float damage, float knockback, ResourceLocation texture) {
         super(name, maxstacksize, id, firstExplodeDelay, damage, knockback, texture);
      }

      @Override
      public boolean doWaterMoveHook() {
         return true;
      }

      @Override
      public int waterParticlesHookAdding() {
         return 2;
      }

      @Override
      public void modifySpeedInWater(EntityGrenade grenade) {
         double multiplier = 0.94;
         grenade.motionX *= multiplier;
         grenade.motionY *= multiplier;
         grenade.motionZ *= multiplier;
      }

      @Override
      public void explode(
         World world, @Nullable EntityLivingBase player, double x, double y, double z, @Nullable RayTraceResult result, @Nullable EntityGrenade projectile
      ) {
         if (!world.isRemote) {
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
            float damageRadius = parameters.get("damage_radius");
            int blockDestroyRadius = parameters.geti("destroy_radius");
            int bombs = parameters.geti("bombs");
            int minExplodeDelay = parameters.geti("min_bomb_explode_delay");
            float bombDamage = parameters.get("bomb_damage");
            float bombSpeed = parameters.get("bomb_speed");
            HostileProjectiles.SeaBomb.explodeSeabomb(world, damageRadius, blockDestroyRadius, itemRand, new Vec3d(x, y, z), player, this.damage(), true);

            for (int i = 0; i < bombs; i++) {
               HostileProjectiles.SeaBomb bomb = new HostileProjectiles.SeaBomb(world, x, y, z);
               boolean success = false;

               for (int j = 0; j < 16; j++) {
                  double veclength = success ? 7.0 : 2.5;
                  Vec3d start = new Vec3d(
                     x + (itemRand.nextDouble() - 0.5) * 0.5, y + (itemRand.nextDouble() - 0.5) * 0.5, z + (itemRand.nextDouble() - 0.5) * 0.5
                  );
                  Vec3d end = start.add(
                     (itemRand.nextDouble() - 0.5) * veclength,
                     (itemRand.nextDouble() - 0.5) * veclength,
                     (itemRand.nextDouble() - 0.5) * veclength
                  );
                  RayTraceResult rayTraceResult = GetMOP.fixedRayTraceBlocks(world, player, 0.1, 0.1, true, start, end, false, true, false);
                  boolean hitEntity = rayTraceResult != null
                     && rayTraceResult.entityHit != null
                     && Team.checkIsOpponent(player, rayTraceResult.entityHit);
                  if ((!success || hitEntity) && (rayTraceResult == null || rayTraceResult.typeOfHit != Type.BLOCK)) {
                     bomb.setPosition(start.x, start.y, start.z);
                     SuperKnockback.setMove(bomb, -bombSpeed, end.x, end.y, end.z);
                     success = true;
                     if (hitEntity) {
                        break;
                     }
                  }
               }

               if (!success) {
                  bomb.setPosition(x, y, z);
                  bomb.motionX = itemRand.nextDouble() - 0.5;
                  bomb.motionY = itemRand.nextDouble() - 0.5;
                  bomb.motionZ = itemRand.nextDouble() - 0.5;
               }

               bomb.damage = bombDamage;
               bomb.explodeTimeOffset = -itemRand.nextInt(30) - minExplodeDelay;
               world.spawnEntity(bomb);
            }

            if (projectile != null) {
               projectile.setDead();
            }
         } else {
            world.playSound(x, y, z, Sounds.explode3, SoundCategory.PLAYERS, 1.2F, 1.1F + itemRand.nextFloat() / 5.0F, false);
            world.playSound(x, y, z, Sounds.sea_bomb, SoundCategory.PLAYERS, 1.0F, 1.2F + itemRand.nextFloat() / 5.0F, false);
            HostileProjectiles.SeaBomb.spawnSeabombParticles(world, itemRand, x, y, z);
         }
      }

      @SideOnly(Side.CLIENT)
      @Override
      public void renderGrenade(
         @Nullable EntityGrenade entity, double x, double y, double z, float entityYaw, float partialTicks, @Nullable Render render, boolean forEntity
      ) {
         GlStateManager.pushMatrix();
         if (forEntity) {
            GlStateManager.translate((float)x, (float)y + 0.1F, (float)z);
            GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(
               (entity.prevrotationProgr + (entity.rotationProgr - entity.prevrotationProgr) * partialTicks) * 8.2F,
               (float)entity.randomRotate.x,
               (float)entity.randomRotate.y,
               (float)entity.randomRotate.z
            );
         }

         GlStateManager.disableCull();
         Minecraft.getMinecraft().renderEngine.bindTexture(this.texture);
         mainModel.hideAll();
         mainModel.big.isHidden = false;
         mainModel.stick1.isHidden = false;
         mainModel.stick2.isHidden = false;
         mainModel.stick3.isHidden = false;
         mainModel.stick4.isHidden = false;
         mainModel.shapedown2.isHidden = false;
         mainModel.shapeup2.isHidden = false;
         mainModel.shapedown.isHidden = false;
         mainModel.shapeup.isHidden = false;
         mainModel.render(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
         GlStateManager.enableCull();
         GlStateManager.popMatrix();
      }
   }

   public static class SnowGrenade extends ItemGrenade {
      public SnowGrenade(String name, int maxstacksize, byte id, int firstExplodeDelay, float damage, float knockback, ResourceLocation texture) {
         super(name, maxstacksize, id, firstExplodeDelay, damage, knockback, texture);
      }

      @Override
      public void explode(
         World world, @Nullable EntityLivingBase player, double x, double y, double z, @Nullable RayTraceResult result, @Nullable EntityGrenade projectile
      ) {
         if (!world.isRemote) {
            Vec3d posv = new Vec3d(x, y, z);
            if (world.getBlockState(new BlockPos(posv.x, posv.y, posv.z))
                  .getCollisionBoundingBox(world, new BlockPos(posv.x, posv.y, posv.z))
               != null) {
               for (EnumFacing f : EnumFacing.VALUES) {
                  posv = posv.add(f.getXOffset() * 2, f.getYOffset() * 2, f.getZOffset() * 2);
                  BlockPos posf = new BlockPos(posv.x, posv.y, posv.z);
                  if (world.getBlockState(posf).getCollisionBoundingBox(world, posf) == null) {
                     break;
                  }
               }
            }

            int snowcount = WeaponParameters.getWeaponParameters(this).geti("falling_snow_count");
            float snowspeed = WeaponParameters.getWeaponParameters(this).geti("falling_snow_speed");
            int imax = Math.max(snowcount, 80);
            int v = 0;

            for (int i = 0; i < imax; i++) {
               Vec3d speedVec = new Vec3d(
                  (itemRand.nextFloat() - 0.5) * snowspeed, (itemRand.nextFloat() - 0.5) * snowspeed, (itemRand.nextFloat() - 0.5) * snowspeed
               );
               BlockPos poss = new BlockPos(
                  posv.x + speedVec.x, posv.y + speedVec.y, posv.z + speedVec.z
               );
               if (world.getBlockState(poss).getCollisionBoundingBox(world, poss) == null) {
                  IBlockState statie = Blocks.SNOW_LAYER.getDefaultState();
                  AdvancedFallingBlock afb = new AdvancedFallingBlock(
                     world,
                     posv.x + speedVec.x,
                     posv.y + speedVec.y,
                     posv.z + speedVec.z,
                     statie
                  );
                  afb.motionX = speedVec.x;
                  afb.motionY = speedVec.y;
                  afb.motionZ = speedVec.z;
                  afb.shouldDropItem = false;
                  world.spawnEntity(afb);
                  afb.velocityChanged = true;
                  if (++v > snowcount) {
                     break;
                  }
               }
            }

            for (BlockPos pos : GetMOP.getPosesInsideSphere(
               MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z), (int)this.damageRadius()
            )) {
               boolean isnotl = !world.getBlockState(pos).getMaterial().isLiquid();
               if (isnotl && world.getBlockState(pos).getBlock().isReplaceable(world, pos)) {
                  IBlockState dn = world.getBlockState(pos.down());
                  if (Blocks.SNOW_LAYER.canPlaceBlockAt(world, pos)
                     && dn.getBlock() != Blocks.SNOW_LAYER
                     && dn.getBlock() != BlocksRegister.LOOSESNOW) {
                     world.setBlockState(pos, Blocks.SNOW_LAYER.getDefaultState());
                  } else if (dn.getBlock() == Blocks.ICE || dn.getBlock() == Blocks.FROSTED_ICE) {
                     world.setBlockState(pos, BlocksRegister.LOOSESNOW.getDefaultState());
                  }
               }
            }

            if (projectile != null) {
               projectile.setDead();
            }
         } else {
            world.playSound(x, y, z, Sounds.glacide_blade, SoundCategory.PLAYERS, 1.0F, 0.7F + itemRand.nextFloat() * 0.3F, false);

            for (int ss = 0; ss < 20; ss++) {
               int lt = 20 + itemRand.nextInt(20);
               GUNParticle bigsmoke = new GUNParticle(
                  snow5,
                  0.1F + itemRand.nextFloat() * 0.05F,
                  0.01F,
                  lt,
                  -1,
                  world,
                  x,
                  y,
                  z,
                  (float)(itemRand.nextGaussian() / 9.0),
                  (float)(itemRand.nextGaussian() / 9.0),
                  (float)(itemRand.nextGaussian() / 9.0),
                  0.8F + itemRand.nextFloat() / 10.0F,
                  1.0F,
                  1.0F,
                  true,
                  itemRand.nextInt(360)
               );
               bigsmoke.alphaTickAdding = -1.0F / lt;
               world.spawnEntity(bigsmoke);
            }

            int countOfParticles = 23;
            float R = (float)((0.1 + itemRand.nextFloat() * 0.03) * 3.0);

            for (int ix = 0; ix < countOfParticles; ix++) {
               float rand1 = itemRand.nextFloat() * 2.0F - 1.0F;
               float rand2 = itemRand.nextFloat() * 2.0F - 1.0F;
               float X = rand1 * R;
               float new_R = (float)Math.sqrt(R * R - X * X);
               float Y = rand2 * new_R;
               float Z = (float)Math.sqrt(new_R * new_R - Y * Y);
               if (itemRand.nextBoolean()) {
                  Z *= -1.0F;
               }

               GUNParticle particle = new GUNParticle(
                  snow,
                  0.35F + (float)itemRand.nextGaussian() / 30.0F,
                  0.015F,
                  35 + itemRand.nextInt(20),
                  180,
                  world,
                  x,
                  y,
                  z,
                  X,
                  Y,
                  Z,
                  0.8F + itemRand.nextFloat() / 10.0F,
                  0.9F,
                  1.0F,
                  false,
                  itemRand.nextInt(360),
                  true,
                  1.0F
               );
               world.spawnEntity(particle);
            }

            for (int ss = 0; ss < 25; ss++) {
               world.spawnParticle(
                  EnumParticleTypes.CLOUD,
                  x,
                  y,
                  z,
                  itemRand.nextGaussian() / 13.0,
                  itemRand.nextGaussian() / 13.0,
                  itemRand.nextGaussian() / 13.0,
                  new int[0]
               );
               world.spawnParticle(
                  EnumParticleTypes.SNOWBALL,
                  x + itemRand.nextFloat() - 0.5,
                  y + itemRand.nextFloat() - 0.5,
                  z + itemRand.nextFloat() - 0.5,
                  itemRand.nextGaussian() / 7.0,
                  itemRand.nextGaussian() / 7.0,
                  itemRand.nextGaussian() / 7.0,
                  new int[0]
               );
            }
         }
      }

      @SideOnly(Side.CLIENT)
      @Override
      public void renderGrenade(
         @Nullable EntityGrenade entity, double x, double y, double z, float entityYaw, float partialTicks, @Nullable Render render, boolean forEntity
      ) {
         GlStateManager.pushMatrix();
         if (forEntity) {
            GlStateManager.translate((float)x, (float)y + 0.1F, (float)z);
            GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(
               (entity.prevrotationProgr + (entity.rotationProgr - entity.prevrotationProgr) * partialTicks) * 8.2F,
               (float)entity.randomRotate.x,
               (float)entity.randomRotate.y,
               (float)entity.randomRotate.z
            );
         }

         GlStateManager.disableCull();
         Minecraft.getMinecraft().renderEngine.bindTexture(this.texture);
         mainModel.hideAll();
         mainModel.shapemain.isHidden = false;
         mainModel.big.isHidden = false;
         mainModel.shapedown.isHidden = false;
         mainModel.shapeup.isHidden = false;
         mainModel.shapedown2.isHidden = false;
         mainModel.shapeup2.isHidden = false;
         mainModel.render(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
         GlStateManager.enableCull();
         GlStateManager.popMatrix();
      }
   }

   public static class WatchingGrenade extends ItemGrenade {
      public static ParticleTracker.TrackerSmoothShowHide blackPortal_tssh = new ParticleTracker.TrackerSmoothShowHide(
         null,
         new Vec3d[]{
            new Vec3d(0.0, 6.0, 0.1), new Vec3d(6.0, 8.0, -0.03), new Vec3d(8.0, 10.0, 0.03), new Vec3d(13.0, 15.0, 0.03), new Vec3d(15.0, 22.0, -0.101)
         }
      );

      public WatchingGrenade(String name, int maxstacksize, byte id, int firstExplodeDelay, float damage, float knockback, ResourceLocation texture) {
         super(name, maxstacksize, id, firstExplodeDelay, damage, knockback, texture);
      }

      @Override
      public boolean doWaterMoveHook() {
         return true;
      }

      @Override
      public int waterParticlesHookAdding() {
         return 4;
      }

      @Override
      public void modifySpeedInWater(EntityGrenade grenade) {
         double multiplier = 0.9;
         grenade.motionX *= multiplier;
         grenade.motionY *= multiplier;
         grenade.motionZ *= multiplier;
      }

      @Override
      public void explode(
         World world, @Nullable EntityLivingBase player, double x, double y, double z, @Nullable RayTraceResult result, @Nullable EntityGrenade projectile
      ) {
         if (!world.isRemote) {
            OtherMobsPack.MechanicalWatcher mob = new OtherMobsPack.MechanicalWatcher(world);
            mob.setPosition(x, y, z);
            mob.homeX = x;
            mob.homeY = y + 3.0;
            mob.homeZ = z;
            world.spawnEntity(mob);
            mob.onInitialSpawn();
            if (player != null) {
               mob.team = Team.getTeamFor(player);
               if (player instanceof EntityPlayer) {
                  mob.owner = (EntityPlayer)player;
               }
            }

            mob.livetime = WeaponParameters.getWeaponParameters(this).geti("watcher_livetime");
            mob.rotationYaw = itemRand.nextInt(360) - 180;
            mob.isAgressive = true;
            mob.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(WeaponParameters.getWeaponParameters(this).get("damage"));
            if (projectile != null) {
               projectile.setDead();
            }
         } else {
            world.playSound(x, y, z, Sounds.watching_grenade, SoundCategory.PLAYERS, 2.0F, 0.95F + itemRand.nextFloat() / 10.0F, false);

            for (int i = 0; i < 15; i++) {
               world.spawnParticle(
                  EnumParticleTypes.PORTAL,
                  x + (itemRand.nextDouble() - 0.5) * 2.0,
                  y + itemRand.nextDouble() - 0.25,
                  z + (itemRand.nextDouble() - 0.5) * 2.0,
                  (itemRand.nextDouble() - 0.5) * 2.0,
                  -itemRand.nextDouble(),
                  (itemRand.nextDouble() - 0.5) * 2.0,
                  new int[0]
               );
            }

            GUNParticle part = new GUNParticle(
               shadow_round64x, 0.01F, 0.0F, 22, 0, world, x, y, z, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, false, itemRand.nextInt(360)
            );
            part.tracker = blackPortal_tssh;
            part.randomDeath = false;
            part.isPushedByLiquids = false;
            world.spawnEntity(part);
            float fsize = 1.5F + itemRand.nextFloat() * 0.5F;
            int lt = 4 + itemRand.nextInt(3);
            GUNParticle partx = new GUNParticle(
               void_explode, 0.4F, 0.0F, lt, 240, world, x, y, z, 0.0F, 0.0F, 0.0F, 0.5F, 0.8F, 1.0F, true, itemRand.nextInt(360)
            );
            partx.scaleTickAdding = fsize / lt;
            partx.alphaGlowing = true;
            partx.alphaTickAdding = -0.9F / lt;
            partx.randomDeath = false;
            partx.isPushedByLiquids = false;
            world.spawnEntity(partx);

            for (int i = 0; i < 10; i++) {
               Vec3d motionvec = new Vec3d(itemRand.nextGaussian(), itemRand.nextGaussian(), itemRand.nextGaussian());
               Vec3d pitchYaw = GetMOP.Vec3dToPitchYaw(motionvec);
               int ltx = i < 5 ? 3 + itemRand.nextInt(3) : 10 + itemRand.nextInt(30);
               float scl = 0.5F + itemRand.nextFloat() * 0.5F;
               Vec3d posAdd = motionvec.normalize().scale(scl / 2.0F);
               GUNParticle partxx = new GUNParticle(
                  lightning1,
                  scl,
                  0.0F,
                  ltx + 4,
                  240,
                  world,
                  x + posAdd.x,
                  y + posAdd.y,
                  z + posAdd.z,
                  0.0F,
                  0.0F,
                  0.0F,
                  1.0F,
                  0.4F + itemRand.nextFloat() * 0.3F,
                  1.0F,
                  true,
                  (int)(-pitchYaw.x + 270.0)
               );
               partxx.randomDeath = false;
               partxx.alphaGlowing = true;
               partxx.rotationPitchYaw = new Vec2f(0.0F, (float)pitchYaw.y + 90.0F);
               partxx.isPushedByLiquids = false;
               if (i >= 5) {
                  partxx.alpha = -ltx / 2.0F;
                  partxx.alphaTickAdding = 0.5F;
               }

               world.spawnEntity(partxx);
            }
         }
      }

      @SideOnly(Side.CLIENT)
      @Override
      public void renderGrenade(
         @Nullable EntityGrenade entity, double x, double y, double z, float entityYaw, float partialTicks, @Nullable Render render, boolean forEntity
      ) {
         GlStateManager.pushMatrix();
         if (forEntity) {
            GlStateManager.translate((float)x, (float)y + 0.1F, (float)z);
            GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(
               (entity.prevrotationProgr + (entity.rotationProgr - entity.prevrotationProgr) * partialTicks) * 8.2F,
               (float)entity.randomRotate.x,
               (float)entity.randomRotate.y,
               (float)entity.randomRotate.z
            );
         }

         GlStateManager.disableCull();
         Minecraft.getMinecraft().renderEngine.bindTexture(this.texture);
         mainModel.hideAll();
         mainModel.shapemain.isHidden = false;
         mainModel.shapecubeup.isHidden = false;
         AbstractMobModel.light(200, false);
         mainModel.render(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
         AbstractMobModel.returnlight();
         mainModel.shapemain.isHidden = true;
         mainModel.shapecubeup.isHidden = true;
         mainModel.big.isHidden = false;
         mainModel.pinhandle2.isHidden = false;
         mainModel.shapecubedown.isHidden = false;
         mainModel.shapedown.isHidden = false;
         mainModel.render(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
         GlStateManager.enableCull();
         GlStateManager.popMatrix();
      }
   }
}
