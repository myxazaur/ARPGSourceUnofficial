package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.entity.BetweenParticle;
import com.Vivern.Arpg.entity.EntityMagicRocket;
import com.Vivern.Arpg.entity.TrailParticle;
import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.Mana;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.potions.Freezing;
import com.Vivern.Arpg.potions.PotionEffects;
import com.Vivern.Arpg.renders.GUNParticle;
import com.Vivern.Arpg.renders.ParticleTracker;
import java.util.HashMap;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.BlockPos.PooledMutableBlockPos;
import net.minecraft.world.World;
import org.lwjgl.input.Mouse;

public class ItemMagicScroll extends ItemWeapon {
   public static HashMap<Integer, ItemMagicScroll> magicScrolls;
   public static ResourceLocation beam = new ResourceLocation("arpg:textures/laser_sniper_laser.png");
   public static ResourceLocation impact = new ResourceLocation("arpg:textures/simple_magic_shoot.png");
   public static ResourceLocation effect = new ResourceLocation("arpg:textures/magic_effect_2.png");
   public static ResourceLocation spellblue = new ResourceLocation("arpg:textures/spellblue4.png");
   public static ResourceLocation manaf = new ResourceLocation("arpg:textures/mana_flow.png");
   public static ParticleTracker tracker1 = new ParticleTracker.TrackerSmoothShowHideBetweenP(
      new Vec3d[]{new Vec3d(-1.0, 5.0, 0.25), new Vec3d(4.0, 13.0, -0.125)}, null
   );
   public float manacost;
   public float sorcery4manacost;
   public int cooldown;
   public float damage;
   public int scrollID;
   public boolean projectile;
   public float spellRadius;
   public float spellHeight;
   public double distance;
   public float sizeStepWidth;
   public int rapidityValue;
   public SoundEvent castSound;
   public int effectType;
   public float Red;
   public float Green;
   public float Blue;

   public ItemMagicScroll(String name, int scrollID, CreativeTabs tab, int maxdamage, float Red, float Green, float Blue) {
      name = name + "_scroll";
      this.setRegistryName(name);
      this.setCreativeTab(tab);
      this.setTranslationKey(name);
      this.setMaxDamage(maxdamage);
      this.setMaxStackSize(1);
      this.scrollID = scrollID;
      this.Red = Red;
      this.Green = Green;
      this.Blue = Blue;
   }

   public static HashMap<Integer, ItemMagicScroll> createScrolls() {
      HashMap<Integer, ItemMagicScroll> registry = new HashMap<>();
      ItemMagicScroll scroll = new ItemMagicScroll("freeze", 0, CreativeTabs.COMBAT, 200, 0.5F, 0.5F, 1.0F) {
         @Override
         public void onAffectEntity(ItemStack itemstack, World worldIn, Entity entity, boolean isopponent, EntityPlayer player) {
            if (isopponent) {
               PotionEffect lastdebaff = Weapons.mixPotion(
                  entity,
                  PotionEffects.FREEZING,
                  (float)(100 + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, itemstack) * 20),
                  8.0F,
                  Weapons.EnumPotionMix.GREATEST,
                  Weapons.EnumPotionMix.GREATEST,
                  Weapons.EnumMathOperation.NONE,
                  Weapons.EnumMathOperation.NONE,
                  0,
                  0
               );
               Freezing.tryPlaySound(entity, lastdebaff);
            }
         }
      };
      scroll.manacost = 5.0F;
      scroll.sorcery4manacost = 1.0F;
      scroll.cooldown = 140;
      scroll.damage = 0.0F;
      scroll.projectile = false;
      scroll.spellRadius = 0.5F;
      scroll.spellHeight = 0.5F;
      scroll.distance = 20.0;
      scroll.sizeStepWidth = 0.5F;
      scroll.rapidityValue = 20;
      scroll.effectType = 0;
      scroll.castSound = Sounds.fluid_freezing;
      registry.put(scroll.scrollID, scroll);
      scroll = new ItemMagicScroll("demonic_curse", 1, CreativeTabs.COMBAT, 300, 0.65F, 0.4F, 0.9F) {
         @Override
         public void onAffectEntity(ItemStack itemstack, World worldIn, Entity entity, boolean isopponent, EntityPlayer player) {
            if (isopponent && entity.isBurning()) {
               int firetick = 0;
               NBTTagCompound tag = new NBTTagCompound();
               entity.writeToNBT(tag);
               if (tag.hasKey("Fire")) {
                  firetick = tag.getShort("Fire");
               }

               Weapons.setPotionIfEntityLB(
                  entity, PotionEffects.DEMONIC_BURN, firetick + 100 + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, itemstack) * 10, 1
               );
               entity.extinguish();
            }
         }

         @Override
         public void onSpellImpact(ItemStack itemstack, World world, Vec3d position, EntityPlayer player) {
            world.playSound(
               (EntityPlayer)null,
               position.x,
               position.y,
               position.z,
               Sounds.soul_steal,
               SoundCategory.PLAYERS,
               1.0F,
               1.05F + itemRand.nextFloat() / 5.0F
            );

            for (int x = -4; x <= 4; x++) {
               for (int y = -2; y <= 2; y++) {
                  for (int z = -4; z <= 4; z++) {
                     BlockPos pos = new BlockPos(position.x + x, position.y + y, position.z + z);
                     if (world.getBlockState(pos).getBlock() == Blocks.FIRE) {
                        world.setBlockState(pos, BlocksRegister.DEMONICFIRE.getActualState(BlocksRegister.DEMONICFIRE.getDefaultState(), world, pos));
                     }
                  }
               }
            }
         }
      };
      scroll.manacost = 8.0F;
      scroll.sorcery4manacost = 3.0F;
      scroll.cooldown = 270;
      scroll.damage = 0.0F;
      scroll.projectile = false;
      scroll.spellRadius = 4.0F;
      scroll.spellHeight = 1.5F;
      scroll.distance = 18.0;
      scroll.sizeStepWidth = 0.3F;
      scroll.rapidityValue = 25;
      scroll.effectType = 0;
      scroll.castSound = Sounds.burn;
      registry.put(scroll.scrollID, scroll);
      scroll = new ItemMagicScroll("winter_bite", 2, CreativeTabs.COMBAT, 180, 0.55F, 0.8F, 1.0F) {
         @Override
         public void onAffectEntity(ItemStack itemstack, World world, Entity entity, boolean isopponent, EntityPlayer player) {
            if (Weapons.isEntityStayOnColdBlock(world, entity)) {
               Weapons.setPotionIfEntityLB(entity, PotionEffects.FROSTBURN, 200 + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, itemstack) * 20, 0);
               BlockPos bpos = entity.getPosition();
               if (world.getBlockState(bpos).getBlock().isReplaceable(world, bpos)) {
                  world.setBlockState(bpos, BlocksRegister.BURNINGFROST.getDefaultState());
               }
            }
         }

         @Override
         public void onSpellImpact(ItemStack itemstack, World world, Vec3d position, EntityPlayer player) {
            if (EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack) > 0) {
               for (int x = -5; x <= 5; x++) {
                  for (int y = -3; y <= 3; y++) {
                     for (int z = -5; z <= 5; z++) {
                        if (Math.abs(x) + Math.abs(z) < 9) {
                           BlockPos pos = new BlockPos(position.x + x, position.y + y, position.z + z);
                           Block blovc = world.getBlockState(pos).getBlock();
                           if (blovc.isReplaceable(world, pos) && Weapons.isSolidColdBlock(blovc)) {
                              world.setBlockState(pos, BlocksRegister.BURNINGFROST.getDefaultState());
                           }
                        }
                     }
                  }
               }
            }
         }
      };
      scroll.manacost = 10.0F;
      scroll.sorcery4manacost = 5.0F;
      scroll.cooldown = 320;
      scroll.damage = 4.0F;
      scroll.projectile = false;
      scroll.spellRadius = 5.5F;
      scroll.spellHeight = 3.5F;
      scroll.distance = 30.0;
      scroll.sizeStepWidth = 0.5F;
      scroll.rapidityValue = 30;
      scroll.effectType = 0;
      scroll.castSound = Sounds.snowstorm;
      registry.put(scroll.scrollID, scroll);
      magicScrolls = registry;
      return registry;
   }

   @Override
   public void effect(EntityPlayer player, World world, double x, double y, double z, double a, double b, double c, double d1, double d2, double d3) {
      if (d1 == 0.0) {
         for (int h = 0; h < 4; h++) {
            int max = 3;
            double itx = (a - x) / max;
            double ity = (b - y) / max;
            double itz = (c - z) / max;
            Vec3d lastpos = new Vec3d(x, y, z);

            for (int i = 1; i <= max; i++) {
               Vec3d newvec = i == max
                  ? new Vec3d(a, b, c)
                  : new Vec3d(
                     x + itx * i + itemRand.nextGaussian() / 2.0,
                     y + ity * i + itemRand.nextGaussian() / 2.0,
                     z + itz * i + itemRand.nextGaussian() / 2.0
                  );
               BetweenParticle laser = new BetweenParticle(
                  world, beam, 0.1F, 240, this.Red, this.Green, this.Blue, 0.0F, lastpos.distanceTo(newvec), 12, 0.3F, 8.0F, lastpos, newvec
               );
               laser.setPosition(lastpos.x, lastpos.y, lastpos.z);
               laser.alphaGlowing = true;
               laser.alpha = 0.0F;
               laser.tracker = tracker1;
               world.spawnEntity(laser);
               lastpos = newvec;
            }
         }

         for (int ss = 0; ss < 15; ss++) {
            GUNParticle part = new GUNParticle(
               spellblue,
               0.02F + itemRand.nextFloat() / 50.0F,
               0.022F,
               20 + itemRand.nextInt(25),
               240,
               world,
               a,
               b,
               c,
               (float)itemRand.nextGaussian() / 9.0F,
               (float)itemRand.nextGaussian() / 9.0F + 0.1F,
               (float)itemRand.nextGaussian() / 9.0F,
               this.Red,
               this.Green,
               this.Blue,
               false,
               itemRand.nextInt(360),
               true,
               2.5F
            );
            world.spawnEntity(part);
         }

         float fsize = 1.5F + itemRand.nextFloat() / 3.0F;
         int lt = 8 + itemRand.nextInt(5);
         GUNParticle part = new GUNParticle(
            effect, 0.2F, 0.0F, lt, 240, world, a, b, c, 0.0F, 0.0F, 0.0F, this.Red, this.Green, this.Blue, true, itemRand.nextInt(360)
         );
         part.scaleTickAdding = fsize / lt;
         part.alphaGlowing = true;
         part.alphaTickAdding = -0.025F;
         world.spawnEntity(part);
         PooledMutableBlockPos blockpos$pooledmutableblockpos = PooledMutableBlockPos.retain();

         for (float xx = -this.spellRadius; xx <= this.spellRadius; xx++) {
            for (float yy = -this.spellHeight; yy <= this.spellHeight; yy++) {
               for (float zz = -this.spellRadius; zz <= this.spellRadius; zz++) {
                  if (itemRand.nextFloat() < 0.5F) {
                     double disst = Math.abs(xx) + Math.abs(yy) + Math.abs(zz);
                     double distinterp = disst / (this.spellRadius + this.spellRadius + this.spellHeight);
                     if (itemRand.nextFloat() > distinterp) {
                        IBlockState iblockstate1 = world.getBlockState(
                           blockpos$pooledmutableblockpos.setPos(
                              MathHelper.floor(a + xx), MathHelper.floor(b + yy), MathHelper.floor(c + zz)
                           )
                        );
                        if (!iblockstate1.isOpaqueCube()) {
                           float fsize1 = 0.15F + itemRand.nextFloat() * 0.1F + (float)(distinterp * 0.2);
                           int lt1 = 25 + itemRand.nextInt(20);
                           GUNParticle part1 = new GUNParticle(
                              manaf,
                              fsize1,
                              0.004F * (float)itemRand.nextGaussian(),
                              lt1,
                              240,
                              world,
                              a + xx,
                              b + yy,
                              c + zz,
                              (float)itemRand.nextGaussian() / 9.0F,
                              (float)itemRand.nextGaussian() / 9.0F + 0.1F,
                              (float)itemRand.nextGaussian() / 9.0F,
                              this.Red,
                              this.Green,
                              this.Blue,
                              true,
                              0
                           );
                           part1.scaleTickAdding = -fsize1 / lt1;
                           part1.alphaGlowing = true;
                           world.spawnEntity(part1);
                        }
                     }
                  }
               }
            }
         }

         blockpos$pooledmutableblockpos.release();
      }

      if (d1 == 1.0) {
         Vec3d posto = new Vec3d(a, b, c);
         ParticleTracker<TrailParticle> tracker = new ParticleTracker.TrackerFollowStaticPoint(posto, true, 0.5F, 0.01F, 0.2F);

         for (int h = 0; h < 4; h++) {
            TrailParticle trailpart = new TrailParticle(
               impact,
               0.1F,
               0.0F,
               50,
               240,
               world,
               x,
               y,
               z,
               (float)itemRand.nextGaussian() / 7.0F,
               (float)itemRand.nextGaussian() / 7.0F,
               (float)itemRand.nextGaussian() / 7.0F,
               this.Red,
               this.Green,
               this.Blue,
               true,
               0,
               false,
               0.0F,
               beam,
               0.1F,
               240,
               this.Red,
               this.Green,
               this.Blue,
               0.15F,
               5,
               0.3F,
               1.0F
            );
            trailpart.alphaGlowing = true;
            trailpart.tracker = tracker;
            trailpart.TalphaGlowing = true;
            trailpart.onlyHorizontal = true;
            world.spawnEntity(trailpart);
         }
      }

      if (d1 == 2.0) {
      }
   }

   public void onAffectEntity(ItemStack itemstack, World worldIn, Entity entity, boolean isopponent, EntityPlayer player) {
   }

   public void onSpellImpact(ItemStack itemstack, World worldIn, Vec3d position, EntityPlayer player) {
   }

   public void onSpellCast(ItemStack itemstack, World worldIn, EntityPlayer player) {
   }

   public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
      return !ItemStack.areItemsEqual(oldStack, newStack);
   }

   public void onUpdate(ItemStack itemstack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
      if (!worldIn.isRemote) {
         this.setCanShoot(itemstack, entityIn);
         if (IWeapon.canShoot(itemstack)) {
            EntityPlayer player = (EntityPlayer)entityIn;
            int damage = itemstack.getItemDamage();
            World world = player.getEntityWorld();
            Item itemIn = itemstack.getItem();
            EnumHand hand = player.getActiveHand();
            boolean click = Mouse.isButtonDown(1);
            int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
            float power = Mana.getMagicPowerMax(player);
            int sor = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SORCERY, itemstack);
            float fmanacost = this.manacost - (this.manacost - this.sorcery4manacost) / 4.0F * sor;
            NBTHelper.GiveNBTint(itemstack, 0, "scrolling");
            int scrolling = NBTHelper.GetNBTint(itemstack, "scrolling");
            boolean cooldown = player.getCooldownTracker().hasCooldown(itemIn);
            if (player.getHeldItemMainhand() == itemstack) {
               if (scrolling == 11) {
                  NBTHelper.SetNBTint(itemstack, 0, "scrolling");
               } else if (scrolling <= 10 && scrolling > 0 && cooldown) {
                  NBTHelper.AddNBTint(itemstack, -1, "scrolling");
               } else if (scrolling < 10 && !cooldown) {
                  NBTHelper.AddNBTint(itemstack, 1, "scrolling");
               }

               if (Mana.getMana(player) > fmanacost && !cooldown && click) {
                  world.playSound(
                     (EntityPlayer)null,
                     player.posX,
                     player.posY,
                     player.posZ,
                     this.castSound,
                     SoundCategory.PLAYERS,
                     0.9F,
                     0.9F + itemRand.nextFloat() / 5.0F
                  );
                  player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
                  player.addStat(StatList.getObjectUseStats(this));
                  if (!player.capabilities.isCreativeMode) {
                     Mana.changeMana(player, -fmanacost);
                     Mana.setManaSpeed(player, 0.001F);
                  }

                  this.onSpellCast(itemstack, worldIn, player);
                  if (this.effectType == 2) {
                     Vec3d vec0 = player.getPositionEyes(1.0F);
                     Vec3d vec2 = player.getLook(1.0F);
                     IWeapon.fireEffect(
                        this,
                        player,
                        world,
                        64.0,
                        vec0.x + vec2.x,
                        vec0.y + vec2.y,
                        vec0.z + vec2.z,
                        vec0.x,
                        vec0.y,
                        vec0.z,
                        (double)this.effectType,
                        0.0,
                        0.0
                     );
                  }

                  if (this.projectile) {
                     EntityMagicRocket projectile = new EntityMagicRocket(world, player, itemstack, power);
                     projectile.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 0.95F, 2 / (acc + 1));
                     world.spawnEntity(projectile);
                  } else {
                     double edist = this.distance * (1.0F + acc * 0.1F);
                     Vec3d vec = GetMOP.PosRayTrace(edist, 1.0F, player, this.sizeStepWidth, this.sizeStepWidth);
                     this.onSpellImpact(itemstack, worldIn, vec, player);
                     if (this.effectType == 0 || this.effectType == 1) {
                        Vec3d vec0 = player.getPositionEyes(1.0F);
                        IWeapon.fireEffect(
                           this,
                           player,
                           world,
                           64.0,
                           vec0.x,
                           vec0.y - 0.3,
                           vec0.z,
                           vec.x,
                           vec.y,
                           vec.z,
                           (double)this.effectType,
                           0.0,
                           0.0
                        );
                     }

                     AxisAlignedBB aabb = new AxisAlignedBB(
                        vec.x - this.spellRadius,
                        vec.y - this.spellHeight,
                        vec.z - this.spellRadius,
                        vec.x + this.spellRadius,
                        vec.y + this.spellHeight,
                        vec.z + this.spellRadius
                     );
                     List<EntityLivingBase> list = worldIn.getEntitiesWithinAABB(EntityLivingBase.class, aabb);
                     if (!list.isEmpty()) {
                        for (EntityLivingBase entitylivingbase : list) {
                           boolean isop = Team.checkIsOpponent(player, entitylivingbase);
                           this.onAffectEntity(itemstack, worldIn, entitylivingbase, isop, player);
                           if (isop && damage != 0) {
                              IAttributeInstance entityAttribute = entitylivingbase.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE);
                              double baseValue = entityAttribute.getBaseValue();
                              entityAttribute.setBaseValue(1.0);
                              boolean ab = entitylivingbase.attackEntityFrom(DamageSource.causePlayerDamage(player), damage);
                              entitylivingbase.hurtResistantTime = 0;
                              entityAttribute.setBaseValue(baseValue);
                           }
                        }
                     }
                  }
               }
            } else if (scrolling != 11) {
               NBTHelper.SetNBTint(itemstack, 11, "scrolling");
            }
         }
      }
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.ONE_HANDED;
   }

   @Override
   public int getCooldownTime(ItemStack itemstack) {
      int rapidity = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack);
      return this.cooldown - rapidity * this.rapidityValue;
   }

   @Override
   public int getReloadTime(ItemStack itemstack) {
      return 0;
   }

   @Override
   public float getZoom(ItemStack itemstack, EntityPlayer player) {
      return 0.0F;
   }

   @Override
   public boolean autoReload(ItemStack itemstack) {
      return false;
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return true;
   }

   @Override
   public boolean hasZoom(ItemStack itemstack) {
      return false;
   }
}
