package com.vivern.arpg.elements;

import com.vivern.arpg.entity.BetweenParticle;
import com.vivern.arpg.entity.CoralPolyp;
import com.vivern.arpg.main.DeathEffects;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.Ln;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.SuperKnockback;
import com.vivern.arpg.main.Team;
import com.vivern.arpg.main.WeaponDamage;
import com.vivern.arpg.main.WeaponParameters;
import com.vivern.arpg.main.Weapons;
import com.vivern.arpg.network.PacketBulletEffectToClients;
import com.vivern.arpg.network.PacketHandler;
import com.vivern.arpg.potions.Freezing;
import com.vivern.arpg.potions.PotionEffects;
import com.vivern.arpg.renders.BulletParticle;
import com.vivern.arpg.renders.GUNParticle;
import com.vivern.arpg.renders.ParticleTracker;
import com.google.common.base.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class ItemBullet extends Item {
   public static List<ItemBullet> bulletsRegister = new ArrayList<>();
   public float damage = 0.0F;
   public float knockback = 0.0F;
   public float colorR = 1.0F;
   public float colorG = 1.0F;
   public float colorB = 1.0F;
   public String NBTname = "";
   public int id = 0;
   public static ResourceLocation thundertex = new ResourceLocation("arpg:textures/lightning1.png");
   public static ResourceLocation crystal_scatter = new ResourceLocation("arpg:textures/crystal_scatter.png");
   public static ResourceLocation[] present = new ResourceLocation[]{
      new ResourceLocation("arpg:textures/present_random_1.png"),
      new ResourceLocation("arpg:textures/present_random_2.png"),
      new ResourceLocation("arpg:textures/present_random_3.png"),
      new ResourceLocation("arpg:textures/present_random_4.png"),
      new ResourceLocation("arpg:textures/present_random_5.png"),
      new ResourceLocation("arpg:textures/blob.png"),
      new ResourceLocation("arpg:textures/fireball3.png"),
      new ResourceLocation("arpg:textures/firework_sparkle.png"),
      new ResourceLocation("arpg:textures/frostlight.png"),
      new ResourceLocation("arpg:textures/health_icon.png"),
      new ResourceLocation("arpg:textures/light.png"),
      new ResourceLocation("arpg:textures/magic_powder.png"),
      new ResourceLocation("arpg:textures/manamax_icon.png"),
      new ResourceLocation("arpg:textures/snowflake.png"),
      new ResourceLocation("arpg:textures/snowflake3.png"),
      new ResourceLocation("arpg:textures/spellblue3.png"),
      new ResourceLocation("arpg:textures/vortex.png")
   };

   public ItemBullet(String name, CreativeTabs tab, int maxstacksize, float damage, float knockback) {
      this.setRegistryName(name);
      this.setCreativeTab(tab);
      this.setTranslationKey(name);
      this.setMaxStackSize(maxstacksize);
      this.damage = damage;
      this.knockback = knockback;
      this.NBTname = this.getNbtName();
   }

   public ItemBullet(String name, String NBTname, int maxstacksize, float damage, float knockback, float colorR, float colorG, float colorB) {
      this.setRegistryName(name);
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey(name);
      this.setMaxStackSize(maxstacksize);
      this.damage = damage;
      this.knockback = knockback;
      this.colorR = colorR;
      this.colorG = colorG;
      this.colorB = colorB;
      this.NBTname = NBTname;
   }

   public ItemBullet setId(int id) {
      this.id = id;
      return this;
   }

   public static void init() {
      registerBullet(((ItemBullet)ItemsRegister.BULLETCOPPER).setId(1));
      registerBullet(((ItemBullet)ItemsRegister.BULLETSILVER).setId(2));
      registerBullet(((ItemBullet)ItemsRegister.BULLETLEAD).setId(3));
      registerBullet(((ItemBullet)ItemsRegister.BULLETGOLD).setId(4));
      registerBullet(((ItemBullet)ItemsRegister.BULLETFROZEN).setId(5));
      registerBullet(((ItemBullet)ItemsRegister.BULLETFIRE).setId(6));
      registerBullet(((ItemBullet)ItemsRegister.BULLETTHUNDER).setId(7));
      registerBullet(((ItemBullet)ItemsRegister.BULLETPOISON).setId(8));
      registerBullet(((ItemBullet)ItemsRegister.BULLETTOXIC).setId(9));
      registerBullet(((ItemBullet)ItemsRegister.BULLETCRYSTAL).setId(10));
      registerBullet(((ItemBullet)ItemsRegister.BULLETEXPLODING).setId(11));
      registerBullet(((ItemBullet)ItemsRegister.BULLETFESTIVAL).setId(12));
      registerBullet(((ItemBullet)ItemsRegister.BULLETADAMANTIUM).setId(13));
      registerBullet(((ItemBullet)ItemsRegister.BULLETNIVEOUS).setId(14));
      registerBullet(((ItemBullet)ItemsRegister.BULLETERRATIC).setId(15));
      registerBullet(((ItemBullet)ItemsRegister.BULLETCORAL).setId(16));
      registerBullet(((ItemBullet)ItemsRegister.BULLETDIVERS).setId(17));

      for (ItemBullet bullet : bulletsRegister) {
         bullet.damage = WeaponParameters.getWeaponParameters(bullet).get("damage");
         bullet.knockback = WeaponParameters.getWeaponParameters(bullet).get("knockback");
      }
   }

   public static void registerBullet(ItemBullet item) {
      if (!bulletsRegister.contains(item)) {
         bulletsRegister.add(item);
      }
   }

   @Nullable
   public static ItemBullet getItemBulletFromString(String st) {
      for (ItemBullet bullet : bulletsRegister) {
         if (bullet.getNbtName().equals(st)) {
            return bullet;
         }
      }

      return null;
   }

   @Nullable
   public static ItemBullet getItemBulletFromId(int id) {
      for (ItemBullet bullet : bulletsRegister) {
         if (bullet.id == id) {
            return bullet;
         }
      }

      return null;
   }

   public String getNbtName() {
      return this.NBTname;
   }

   public boolean onImpact(World world, EntityLivingBase player, double x, double y, double z, @Nullable RayTraceResult result, @Nullable Entity projectile) {
      return true;
   }

   public void onDamageCause(World world, EntityLivingBase damaget, EntityLivingBase player, @Nullable Entity projectile) {
   }

   public void onDamageCause(World world, Entity damaget, EntityLivingBase player, @Nullable Entity projectile) {
      if (damaget instanceof EntityLivingBase) {
         this.onDamageCause(world, (EntityLivingBase)damaget, player, projectile);
      }
   }

   public void onProjectileUpdate(Entity projectile) {
   }

   public static void onEffectPacket(World world, double x, double y, double z, double a, double b, double c, int id) {
      if (id == 1) {
         Vec3d vect = new Vec3d(a, b, c);
         Vec3d newvec = new Vec3d(x, y, z);
         if (vect.lengthSquared() > 1.0E-6 && newvec.lengthSquared() > 1.0E-6) {
            BetweenParticle laser = new BetweenParticle(
               world, thundertex, 0.45F, 240, 0.95F, 0.9F, 1.0F, 0.25F, vect.distanceTo(newvec), 6, 0.05F, 4.0F, vect, newvec
            );
            laser.setPosition(vect.x, vect.y, vect.z);
            laser.alphaGlowing = true;
            world.spawnEntity(laser);
         }
      }

      if (id == 2) {
         double damageRadius = 0.35;
         Random rand = new Random((int)a);
         EntityPlayer player = Minecraft.getMinecraft().player;
         Vec3d newvec = new Vec3d(x, y, z);
         Entity impacted = world.getEntityByID((int)b);
         if (player != null) {
            for (int i = 0; i < 8; i++) {
               Vec3d vect = GetMOP.RotatedPosRayTrace(
                  2.5,
                  1.0F,
                  newvec,
                  (EntityLivingBase)(impacted != null && impacted instanceof EntityLivingBase ? (EntityLivingBase)impacted : player),
                  0.35,
                  0.3,
                  rand.nextInt(360) - 180,
                  rand.nextInt(360) - 180
               );
               if (vect.lengthSquared() > 1.0E-6 && newvec.lengthSquared() > 1.0E-6) {
                  double dto = newvec.distanceTo(vect);
                  BetweenParticle laser = new BetweenParticle(
                     world,
                     crystal_scatter,
                     0.17F,
                     170,
                     1.0F - player.getRNG().nextFloat() / 10.0F,
                     0.7F,
                     1.0F - player.getRNG().nextFloat() / 10.0F,
                     0.17F,
                     dto,
                     14,
                     0.0F,
                     (float)dto * 40.0F,
                     newvec,
                     vect
                  );
                  laser.setPosition(x, y, z);
                  laser.alphaGlowing = true;
                  world.spawnEntity(laser);
               }
            }
         }
      }

      if (id == 3) {
         world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, x, y, z, 1.0, 0.0, 0.0, new int[0]);
      }

      if (id == 4) {
         BlockPos pos = new BlockPos(x, y, z);
         int light = itemRand.nextFloat() < 0.2
            ? itemRand.nextInt(100) + 140
            : (
               b == 0.0
                  ? Math.max(world.getLightFromNeighborsFor(EnumSkyBlock.BLOCK, pos), world.getLightFromNeighborsFor(EnumSkyBlock.SKY, pos)) * 15
                  : Math.max(world.getLightFor(EnumSkyBlock.BLOCK, pos), world.getLightFor(EnumSkyBlock.SKY, pos)) * 15
            );
         int countOfParticles = 5 + itemRand.nextInt(10);
         float R = 0.1F + (float)itemRand.nextGaussian() / 40.0F;
         boolean shouldDrop = itemRand.nextFloat() < 0.3;
         ResourceLocation textureto = present[MathHelper.clamp((int)a, 0, present.length)];
         boolean randColor = itemRand.nextFloat() < 0.2;
         float colR = 0.0F;
         float colG = 0.0F;
         float colB = 0.0F;
         if (!randColor) {
            colR = 0.65F + (float)itemRand.nextGaussian() / 3.0F;
            colG = 0.65F + (float)itemRand.nextGaussian() / 3.0F;
            colB = 0.65F + (float)itemRand.nextGaussian() / 3.0F;
         }

         for (int ix = 0; ix < countOfParticles; ix++) {
            if (randColor) {
               colR = 0.65F + (float)itemRand.nextGaussian() / 3.0F;
               colG = 0.65F + (float)itemRand.nextGaussian() / 3.0F;
               colB = 0.65F + (float)itemRand.nextGaussian() / 3.0F;
            }

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
               textureto,
               0.09F + (float)itemRand.nextGaussian() / 30.0F,
               0.025F,
               22 + itemRand.nextInt(10),
               light,
               world,
               x,
               y,
               z,
               X,
               Y + 0.1F,
               Z,
               colR,
               colG,
               colB,
               false,
               itemRand.nextInt(360),
               false,
               3.0F
            );
            particle.dropMode = shouldDrop;
            particle.tracker = ParticleTracker.dcinstance;
            world.spawnEntity(particle);
         }
      }

      if (id == 5) {
         Vec3d from = new Vec3d(x, y, z);
         Vec3d to = new Vec3d(a, b, c);
         double damageRadius = 0.1;
         AxisAlignedBB aabb = new AxisAlignedBB(
            to.x - damageRadius,
            to.y - damageRadius,
            to.z - damageRadius,
            to.x + damageRadius,
            to.y + damageRadius,
            to.z + damageRadius
         );
         boolean collidesWithAny = false;
         int dustId = -1;
         if (world.collidesWithAnyBlock(aabb)) {
            collidesWithAny = true;
            IBlockState dustState = GetMOP.firstBlockStateContains(world, aabb, GetMOP.SOLID_BLOCKS);
            if (dustState != null) {
               dustId = Block.getStateId(dustState);
            }
         }

         if (!world.checkNoEntityCollision(aabb)) {
            collidesWithAny = true;
         }

         float dist = (float)from.distanceTo(to);
         BulletParticle part = new BulletParticle(
            world, from, to, 0.2F, (int)MathHelper.clamp(dist / 1.2, 1.0, 6.0), 8, dist, 0.38F, 0.21F, 0.63F, collidesWithAny
         );
         part.smokeRed = 0.5F;
         part.smokeGreen = 0.57F;
         part.smokeBlue = 0.35F;
         part.blockDustId = dustId;
         world.spawnEntity(part);
      }
   }

   public static void sendEffectPacket(
      World world, double distance, double sendX, double sendY, double sendZ, double x, double y, double z, double a, double b, double c, int id
   ) {
      if (!world.isRemote) {
         PacketBulletEffectToClients packet = new PacketBulletEffectToClients();
         packet.writeargs(x, y, z, a, b, c, id);
         PacketHandler.sendToAllAround(packet, world, sendX, sendY, sendZ, distance);
      }
   }

   public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(stack.getItem());
      tooltip.add("пїЅ7" + Ln.translate("damage") + ": " + parameters.get("damage"));
      tooltip.add("пїЅ7" + Ln.translate("knockback") + ": " + parameters.get("knockback"));
      String name = this.getRegistryName().getPath();
      tooltip.add("пїЅf" + Ln.translate("description." + name));
   }

   public static class BulletCoral extends ItemBullet {
      public BulletCoral() {
         super("bullet_coral", CreativeTabs.COMBAT, 64, 2.0F, 0.0F);
         this.colorR = 0.93F;
         this.colorG = 0.83F;
         this.colorB = 0.89F;
      }

      @Override
      public String getNbtName() {
         return "coral";
      }

      @Override
      public boolean onImpact(World world, EntityLivingBase player, double x, double y, double z, @Nullable RayTraceResult result, @Nullable Entity projectile) {
         if (!world.isRemote) {
            float damagePolyp = WeaponParameters.getWeaponParameters(this).get("damage_polyp");
            CoralPolyp polyp = new CoralPolyp(world, player, damagePolyp);
            polyp.setPosition(x, y, z);
            world.spawnEntity(polyp);
         }

         return true;
      }
   }

   public static class BulletCrystal extends ItemBullet {
      public BulletCrystal() {
         super("bullet_crystal", CreativeTabs.COMBAT, 64, 3.5F, 0.0F);
         this.colorR = 1.0F;
         this.colorG = 0.65F;
         this.colorB = 1.0F;
      }

      @Override
      public String getNbtName() {
         return "crystal";
      }

      @Override
      public boolean onImpact(World world, EntityLivingBase player, double x, double y, double z, @Nullable RayTraceResult result, @Nullable Entity projectile) {
         if (result != null) {
            if (result.entityHit == null && result.hitVec != null) {
               x = result.hitVec.x;
               y = result.hitVec.y;
               z = result.hitVec.z;
            } else if (result.entityHit != null && !Team.checkIsOpponent(player, result.entityHit)) {
               return false;
            }
         }

         double damageRadius = 0.35;
         int seed = world.rand.nextInt();
         Random rand = new Random(seed);
         Vec3d vec1 = new Vec3d(x, y, z);
         EntityLivingBase impacted = result != null && result.entityHit != null && result.entityHit instanceof EntityLivingBase
            ? (EntityLivingBase)result.entityHit
            : GetMOP.findNearestEntityWithinAABB(world, EntityLivingBase.class, new AxisAlignedBB(x - 0.5, y - 0.5, z - 0.5, x + 0.5, y + 0.5, z + 0.5), vec1);
         sendEffectPacket(world, 64.0, x, y, z, x, y, z, seed, impacted == null ? -1.0 : impacted.getEntityId(), 0.0, 2);
         if (!world.isRemote) {
            float damageCrystals = WeaponParameters.getWeaponParameters(this).get("damage_crystals");
            int amountCrystals = WeaponParameters.getWeaponParameters(this).geti("amount_crystals");

            for (int i = 0; i < amountCrystals; i++) {
               Vec3d vec = GetMOP.RotatedPosRayTrace(
                  2.5, 1.0F, vec1, impacted == null ? player : impacted, 0.35, 0.3, rand.nextInt(360) - 180, rand.nextInt(360) - 180
               );
               AxisAlignedBB aabb = new AxisAlignedBB(
                  vec.x - damageRadius,
                  vec.y - damageRadius,
                  vec.z - damageRadius,
                  vec.x + damageRadius,
                  vec.y + damageRadius,
                  vec.z + damageRadius
               );
               List<EntityLivingBase> list = world.getEntitiesWithinAABB(EntityLivingBase.class, aabb);
               if (!list.isEmpty()) {
                  for (EntityLivingBase entitylivingbase : list) {
                     if (entitylivingbase != impacted && Team.checkIsOpponent(entitylivingbase, player)) {
                        Weapons.dealDamage(
                           new WeaponDamage(null, player, projectile, false, false, vec1, WeaponDamage.shards), damageCrystals, player, entitylivingbase, true
                        );
                        entitylivingbase.hurtResistantTime = 0;
                        SuperKnockback.applyKnockback(entitylivingbase, 1.0F, x, y, z);
                     }
                  }
               }
            }
         }

         return true;
      }
   }

   public static class BulletDiving extends ItemBullet {
      public BulletDiving() {
         super("bullet_diving", CreativeTabs.COMBAT, 64, 3.0F, 0.2F);
         this.colorR = 0.07F;
         this.colorG = 0.19F;
         this.colorB = 0.49F;
      }

      @Override
      public String getNbtName() {
         return "diving";
      }

      @Override
      public void onDamageCause(World world, EntityLivingBase damaget, EntityLivingBase player, @Nullable Entity projectile) {
         if (player != null && damaget != null) {
            if (damaget.getHealth() <= 0.0F && damaget.deathTime < 1) {
               Weapons.mixPotion(
                  player,
                  PotionEffects.SWIMMING,
                  120.0F,
                  1.0F,
                  Weapons.EnumPotionMix.WITH_MAXIMUM,
                  Weapons.EnumPotionMix.WITH_MAXIMUM,
                  Weapons.EnumMathOperation.PLUS,
                  Weapons.EnumMathOperation.PLUS,
                  1000,
                  4
               );
            }

            player.setAir(300);
         }
      }
   }

   public static class BulletErratic extends ItemBullet {
      public BulletErratic() {
         super("bullet_erratic", CreativeTabs.COMBAT, 64, 2.5F, 0.3F);
         this.colorR = 0.6F;
         this.colorG = 0.67F;
         this.colorB = 0.45F;
      }

      @Override
      public String getNbtName() {
         return "erratic";
      }

      @Override
      public boolean onImpact(World world, EntityLivingBase player, double x, double y, double z, @Nullable RayTraceResult result, @Nullable Entity projectile) {
         float fixRadius = 0.4F;
         List<EntityLivingBase> listResultFix = GetMOP.getHostilesInAABBto(world, new Vec3d(x, y, z), fixRadius, fixRadius, player, player);
         Entity entityIgnore = (Entity)(listResultFix.isEmpty() ? player : (Entity)listResultFix.get(0));
         List<EntityLivingBase> list = GetMOP.getHostilesInAABBto(world, new Vec3d(x, y, z), 4.0, 4.0, player, entityIgnore);
         if (!list.isEmpty()) {
            for (int i = 0; i < 3; i++) {
               EntityLivingBase livingBase = list.get(itemRand.nextInt(list.size()));
               if (!listResultFix.contains(livingBase)) {
                  this.attemptShoot(
                     world,
                     player,
                     x,
                     y,
                     z,
                     livingBase.posX,
                     livingBase.posY + livingBase.height * (0.25F + itemRand.nextFloat() * 0.5F),
                     livingBase.posZ,
                     entityIgnore
                  );
                  return true;
               }
            }
         }

         if (result != null && result.sideHit != null) {
            double toX = x + itemRand.nextDouble() * this.getFrontRicochetOffset(result.sideHit, Axis.X) * 4.0;
            double toY = y + itemRand.nextDouble() * this.getFrontRicochetOffset(result.sideHit, Axis.Y) * 4.0;
            double toZ = z + itemRand.nextDouble() * this.getFrontRicochetOffset(result.sideHit, Axis.Z) * 4.0;
            this.attemptShoot(world, player, x, y, z, toX, toY, toZ, entityIgnore);
         } else {
            this.attemptShoot(
               world,
               player,
               x,
               y,
               z,
               x + itemRand.nextGaussian() * 2.0,
               y + itemRand.nextGaussian() * 2.0,
               z + itemRand.nextGaussian() * 2.0,
               entityIgnore
            );
         }

         return true;
      }

      public void attemptShoot(
         World world, final EntityLivingBase player, double x, double y, double z, double tox, double toy, double toz, @Nullable final Entity entityIgnore
      ) {
         Vec3d vec1 = new Vec3d(x, y, z);
         Vec3d vec2 = new Vec3d(tox, toy, toz);
         RayTraceResult raytraceresult = world.rayTraceBlocks(vec1, vec2, false, true, false);
         if (raytraceresult != null) {
            vec2 = new Vec3d(raytraceresult.hitVec.x, raytraceresult.hitVec.y, raytraceresult.hitVec.z);
         }

         Predicate<? super Entity> filterEntityToIgnore = new Predicate<Entity>() {
            public boolean apply(Entity input) {
               return input == entityIgnore || !Team.checkIsOpponent(player, input);
            }
         };
         Vec3d vec = GetMOP.findEndCoordOnPath(vec1, vec2, world, filterEntityToIgnore, 0.2, 0.2);
         double damageRadius = 0.25;
         AxisAlignedBB aabb = new AxisAlignedBB(
            vec.x - damageRadius,
            vec.y - damageRadius,
            vec.z - damageRadius,
            vec.x + damageRadius,
            vec.y + damageRadius,
            vec.z + damageRadius
         );
         List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(entityIgnore, aabb);
         if (!list.isEmpty()) {
            for (Entity entity : list) {
               if (entity != entityIgnore && Team.checkIsOpponent(player, entity)) {
                  Weapons.dealDamage(
                     new WeaponDamage(null, player, null, false, true, vec1, WeaponDamage.bullet), 5.0F, player, entity, true, this.knockback, x, y, z
                  );
                  entity.hurtResistantTime = 0;
               }
            }
         }

         sendEffectPacket(world, 64.0, x, y, z, x, y, z, vec2.x, vec2.y, vec2.z, 5);
      }

      public float getFrontRicochetOffset(EnumFacing facing, Axis x_y_or_z) {
         if (x_y_or_z == Axis.X) {
            if (facing.getAxis() == Axis.X) {
               return facing.getXOffset();
            }
         } else if (x_y_or_z == Axis.Y) {
            if (facing.getAxis() == Axis.Y) {
               return facing.getYOffset();
            }
         } else if (x_y_or_z == Axis.X && facing.getAxis() == Axis.X) {
            return facing.getXOffset();
         }

         return itemRand.nextFloat() * 2.0F - 1.0F;
      }
   }

   public static class BulletExploding extends ItemBullet {
      public BulletExploding() {
         super("bullet_exploding", CreativeTabs.COMBAT, 64, 1.5F, 0.5F);
         this.colorR = 1.0F;
         this.colorG = 0.9F;
         this.colorB = 0.9F;
      }

      @Override
      public String getNbtName() {
         return "exploding";
      }

      @Override
      public boolean onImpact(World world, EntityLivingBase player, double x, double y, double z, @Nullable RayTraceResult result, @Nullable Entity projectile) {
         Explosion explosion = new Explosion(world, projectile, x, y, z, 1.0F, false, true);
         explosion.doExplosionA();
         explosion.doExplosionB(false);
         sendEffectPacket(world, 32.0, x, y, z, x, y, z, 0.0, 0.0, 0.0, 3);
         return true;
      }
   }

   public static class BulletFestival extends ItemBullet {
      public BulletFestival() {
         super("bullet_festival", CreativeTabs.COMBAT, 64, 1.0F, 0.0F);
         this.colorR = 1.0F;
         this.colorG = 0.85F;
         this.colorB = 0.4F;
      }

      @Override
      public String getNbtName() {
         return "festival";
      }

      @Override
      public boolean onImpact(World world, EntityLivingBase player, double x, double y, double z, @Nullable RayTraceResult result, @Nullable Entity projectile) {
         if (result != null && result.hitVec != null) {
            sendEffectPacket(
               world,
               32.0,
               x,
               y,
               z,
               result.hitVec.x,
               result.hitVec.y,
               result.hitVec.z,
               itemRand.nextInt(present.length),
               projectile == null ? 0.0 : 1.0,
               0.0,
               4
            );
         } else {
            sendEffectPacket(world, 32.0, x, y, z, x, y, z, itemRand.nextInt(present.length), projectile == null ? 0.0 : 1.0, 0.0, 4);
         }

         return true;
      }

      @Override
      public void onDamageCause(World world, EntityLivingBase damaget, EntityLivingBase player, Entity projectile) {
         int i = player.getRNG().nextInt(14 + (damaget instanceof EntityPlayer ? 2 : 0));
         PotionEffect debaff;
         switch (i) {
            case 0:
               debaff = new PotionEffect(PotionEffects.BLOOD_THIRST, 250);
               break;
            case 1:
               debaff = new PotionEffect(PotionEffects.FIERYOIL, 350);
               break;
            case 2:
               debaff = new PotionEffect(PotionEffects.FREEZING, 50, 5);
               break;
            case 3:
               debaff = new PotionEffect(PotionEffects.SLIME, 500, 1);
               break;
            case 4:
               debaff = new PotionEffect(MobEffects.LEVITATION, 130);
               break;
            case 5:
               debaff = new PotionEffect(MobEffects.GLOWING, 400);
               break;
            case 6:
               debaff = new PotionEffect(MobEffects.INVISIBILITY, 80);
               break;
            case 7:
               debaff = new PotionEffect(MobEffects.JUMP_BOOST, 200, 4);
               break;
            case 8:
               debaff = new PotionEffect(MobEffects.POISON, 300, 1);
               break;
            case 9:
               debaff = new PotionEffect(MobEffects.REGENERATION, 200);
               break;
            case 10:
               debaff = new PotionEffect(MobEffects.SLOWNESS, 600, 1);
               break;
            case 11:
               debaff = new PotionEffect(MobEffects.SPEED, 600);
               break;
            case 12:
               debaff = new PotionEffect(MobEffects.WEAKNESS, 300, 1);
               break;
            case 13:
               debaff = new PotionEffect(MobEffects.WITHER, 100, 2);
               break;
            case 14:
               debaff = new PotionEffect(PotionEffects.RAINBOW, 400);
               break;
            default:
               debaff = new PotionEffect(MobEffects.NIGHT_VISION, 100);
         }

         damaget.addPotionEffect(debaff);
      }
   }

   public static class BulletFrozen extends ItemBullet {
      public BulletFrozen() {
         super("bullet_frozen", CreativeTabs.COMBAT, 64, 3.0F, 0.0F);
         this.colorR = 0.5F;
         this.colorG = 0.8F;
         this.colorB = 1.0F;
      }

      @Override
      public String getNbtName() {
         return "frozen";
      }

      @Override
      public void onDamageCause(World world, EntityLivingBase damaget, EntityLivingBase player, @Nullable Entity projectile) {
         PotionEffect lastdebaff = Weapons.mixPotion(
            damaget,
            PotionEffects.FREEZING,
            20.0F,
            1.0F,
            Weapons.EnumPotionMix.WITH_MAXIMUM,
            Weapons.EnumPotionMix.WITH_MAXIMUM,
            Weapons.EnumMathOperation.PLUS,
            Weapons.EnumMathOperation.PLUS,
            80,
            12
         );
         Freezing.tryPlaySound(damaget, lastdebaff);
      }
   }

   public static class BulletIncendiary extends ItemBullet {
      public BulletIncendiary() {
         super("bullet_incendiary", CreativeTabs.COMBAT, 64, 3.5F, 0.0F);
         this.colorR = 1.0F;
         this.colorG = 0.5F;
         this.colorB = 0.3F;
      }

      @Override
      public String getNbtName() {
         return "incendiary";
      }

      @Override
      public boolean onImpact(World world, EntityLivingBase player, double x, double y, double z, @Nullable RayTraceResult result, @Nullable Entity projectile) {
         return result != null && result.typeOfHit == Type.BLOCK
            ? Weapons.triggerExplodeBlock(world, result.getBlockPos(), player, null)
            : Weapons.triggerExplodeBlock(world, new BlockPos(x, y, z), player, null);
      }

      @Override
      public void onDamageCause(World world, EntityLivingBase damaget, EntityLivingBase player, @Nullable Entity projectile) {
         damaget.setFire(5);
      }
   }

   public static class BulletNiveous extends ItemBullet {
      public BulletNiveous() {
         super("bullet_niveous", CreativeTabs.COMBAT, 64, 2.0F, 0.1F);
         this.colorR = 0.88F;
         this.colorG = 0.93F;
         this.colorB = 0.98F;
      }

      @Override
      public String getNbtName() {
         return "niveous";
      }

      @Override
      public void onDamageCause(World world, EntityLivingBase damaget, EntityLivingBase player, @Nullable Entity projectile) {
         float damageBonus = WeaponParameters.getWeaponParameters(this).get("damage_bonus");
         if (damaget.isBurning()) {
            damaget.hurtResistantTime = 0;
            Weapons.dealDamage(new WeaponDamage(null, player, projectile, false, false, projectile, WeaponDamage.fire), damageBonus, player, damaget, true);
         } else if (Freezing.canImmobilizeEntity(damaget, damaget.getActivePotionEffect(PotionEffects.FREEZING))) {
            damaget.hurtResistantTime = 0;
            Weapons.dealDamage(new WeaponDamage(null, player, projectile, false, false, projectile, WeaponDamage.frost), damageBonus, player, damaget, true);
         } else if (damaget.isPotionActive(MobEffects.POISON)) {
            damaget.hurtResistantTime = 0;
            Weapons.dealDamage(new WeaponDamage(null, player, projectile, false, false, projectile, WeaponDamage.toxin), damageBonus, player, damaget, true);
         }
      }
   }

   public static class BulletPoisonous extends ItemBullet {
      public BulletPoisonous() {
         super("bullet_poisonous", CreativeTabs.COMBAT, 64, 3.0F, 0.0F);
         this.colorR = 0.25F;
         this.colorG = 0.7F;
         this.colorB = 0.55F;
      }

      @Override
      public String getNbtName() {
         return "poisonous";
      }

      @Override
      public void onDamageCause(World world, EntityLivingBase damaget, EntityLivingBase player, @Nullable Entity projectile) {
         PotionEffect debaff = new PotionEffect(MobEffects.POISON, 180);
         damaget.addPotionEffect(debaff);
      }
   }

   public static class BulletSilver extends ItemBullet {
      public BulletSilver() {
         super("bullet_silver", CreativeTabs.COMBAT, 64, 2.0F, 0.0F);
         this.colorR = 1.0F;
         this.colorG = 1.0F;
         this.colorB = 1.0F;
      }

      @Override
      public String getNbtName() {
         return "silver";
      }

      @Override
      public void onDamageCause(World world, EntityLivingBase damaget, EntityLivingBase player, @Nullable Entity projectile) {
         if (damaget.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD) {
            damaget.hurtResistantTime = 0;
            Weapons.dealDamage(
               new WeaponDamage(null, player, projectile, false, false, projectile == null ? player : projectile, WeaponDamage.soul),
               this.damage,
               player,
               damaget,
               true
            );
         }
      }
   }

   public static class BulletThunder extends ItemBullet {
      public BulletThunder() {
         super("bullet_thunder", CreativeTabs.COMBAT, 64, 4.5F, 0.0F);
         this.colorR = 0.87F;
         this.colorG = 0.84F;
         this.colorB = 1.0F;
      }

      @Override
      public String getNbtName() {
         return "thunder";
      }

      @Override
      public void onDamageCause(World world, EntityLivingBase damaget, EntityLivingBase player, @Nullable Entity projectile) {
         double damageRadius = 4.0;
         AxisAlignedBB aabb = new AxisAlignedBB(
            damaget.posX - damageRadius,
            damaget.posY - damageRadius,
            damaget.posZ - damageRadius,
            damaget.posX + damageRadius,
            damaget.posY + damageRadius,
            damaget.posZ + damageRadius
         );
         EntityLivingBase entitylivingbase = (EntityLivingBase)world.findNearestEntityWithinAABB(EntityLivingBase.class, aabb, damaget);
         Vec3d from = new Vec3d(damaget.posX, damaget.posY + damaget.height / 2.0F, damaget.posZ);
         if (Team.checkIsOpponent(player, entitylivingbase)
            && (
               GetMOP.thereIsNoBlockCollidesBetween(
                     world,
                     from,
                     new Vec3d(
                        entitylivingbase.posX, entitylivingbase.posY + entitylivingbase.height / 2.0F, entitylivingbase.posZ
                     ),
                     1.0F,
                     null,
                     false
                  )
                  || GetMOP.thereIsNoBlockCollidesBetween(
                     world,
                     new Vec3d(damaget.posX, damaget.posY + damaget.height, damaget.posZ),
                     new Vec3d(entitylivingbase.posX, entitylivingbase.posY + entitylivingbase.height, entitylivingbase.posZ),
                     1.0F,
                     null,
                     false
                  )
            )) {
            float damageThunder = WeaponParameters.getWeaponParameters(this).get("damage_thunder");
            Weapons.dealDamage(
               new WeaponDamage(null, player, projectile, false, false, from, WeaponDamage.electric), damageThunder, player, entitylivingbase, true
            );
            entitylivingbase.hurtResistantTime = 0;
            world.playSound(
               (EntityPlayer)null,
               damaget.posX,
               damaget.posY,
               damaget.posZ,
               Sounds.electric_impact,
               SoundCategory.AMBIENT,
               1.0F,
               0.9F + damaget.getRNG().nextFloat() / 5.0F
            );
            sendEffectPacket(
               world,
               64.0,
               damaget.posX,
               damaget.posY,
               damaget.posZ,
               damaget.posX,
               damaget.posY + damaget.height / 2.0F,
               damaget.posZ,
               entitylivingbase.posX,
               entitylivingbase.posY + entitylivingbase.height / 2.0F,
               entitylivingbase.posZ,
               1
            );
            if (entitylivingbase.getHealth() <= 0.0F && damaget.getRNG().nextFloat() < 0.2) {
               DeathEffects.applyDeathEffect(entitylivingbase, DeathEffects.DE_ELECTRIC);
            }
         }
      }
   }

   public static class BulletToxic extends ItemBullet {
      public static int duration = 100;

      public BulletToxic() {
         super("bullet_toxic", CreativeTabs.COMBAT, 64, 5.0F, 0.0F);
         this.colorR = 0.5F;
         this.colorG = 1.0F;
         this.colorB = 0.3F;
      }

      @Override
      public String getNbtName() {
         return "toxic";
      }

      @Override
      public void onDamageCause(World world, EntityLivingBase damaget, EntityLivingBase player, @Nullable Entity projectile) {
         PotionEffect debaff = new PotionEffect(PotionEffects.TOXIN, duration);
         damaget.addPotionEffect(debaff);
      }
   }
}
