package com.vivern.arpg.elements;

import com.vivern.arpg.main.Booom;
import com.vivern.arpg.main.EnchantmentInit;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.Keys;
import com.vivern.arpg.main.Mana;
import com.vivern.arpg.main.NBTHelper;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.Weapons;
import com.vivern.arpg.mobs.AbstractMob;
import com.vivern.arpg.mobs.NPCMobsPack;
import com.vivern.arpg.network.PacketHandler;
import com.vivern.arpg.network.PacketSmallSomethingToClients;
import com.vivern.arpg.renders.GUNParticle;
import com.vivern.arpg.renders.ModelledPartickle;
import com.vivern.arpg.renders.ParticleTracker;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Instancer extends ItemWeapon {
   public static ResourceLocation star = new ResourceLocation("arpg:textures/star2.png");
   public static ResourceLocation pixel = new ResourceLocation("arpg:textures/pixel.png");
   public static ResourceLocation[] texturesAnimation = new ResourceLocation[]{
      new ResourceLocation("arpg:textures/summon_soul/soul1.png"),
      new ResourceLocation("arpg:textures/summon_soul/soul2.png"),
      new ResourceLocation("arpg:textures/summon_soul/soul3.png"),
      new ResourceLocation("arpg:textures/summon_soul/soul4.png"),
      new ResourceLocation("arpg:textures/summon_soul/soul5.png"),
      new ResourceLocation("arpg:textures/summon_soul/soul6.png"),
      new ResourceLocation("arpg:textures/summon_soul/soul7.png"),
      new ResourceLocation("arpg:textures/summon_soul/soul8.png"),
      new ResourceLocation("arpg:textures/summon_soul/soul9.png"),
      new ResourceLocation("arpg:textures/summon_soul/soul10.png"),
      new ResourceLocation("arpg:textures/summon_soul/soul11.png"),
      new ResourceLocation("arpg:textures/summon_soul/soul12.png"),
      new ResourceLocation("arpg:textures/summon_soul/soul13.png")
   };
   public float carrySlowness = 1.0F;
   public float deploySlowness = 1.0F;
   public static HashMap<String, MovingSoundInstancer[]> soundsMap = new HashMap<>();

   public Instancer(String name, float carrySlowness, float deploySlowness, int maxDamage) {
      this.carrySlowness = carrySlowness;
      this.deploySlowness = deploySlowness;
      this.setRegistryName(name);
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey(name);
      this.setMaxDamage(maxDamage);
      this.setMaxStackSize(1);
   }

   public static void playMovingSound(EntityPlayer entity) {
      String name = entity.getName();
      if (soundsMap.containsKey(name)) {
         MovingSoundInstancer[] sounds = soundsMap.get(name);
         if (sounds[0].isDonePlaying() && sounds[1].isDonePlaying()) {
            soundsMap.remove(name);
         }
      }

      if (!soundsMap.containsKey(name)) {
         MovingSoundInstancer sound1 = new MovingSoundInstancer(entity, true, SoundCategory.PLAYERS, 1.0F, 1.0F, true);
         Minecraft.getMinecraft().getSoundHandler().playSound(sound1);
         MovingSoundInstancer sound2 = new MovingSoundInstancer(entity, false, SoundCategory.PLAYERS, 1.0F, 1.0F, true);
         Minecraft.getMinecraft().getSoundHandler().playSound(sound2);
         soundsMap.put(name, new MovingSoundInstancer[]{sound1, sound2});
      }
   }

   @Override
   public void effect(EntityPlayer player, World world, double x, double y, double z, double a, double b, double c, double d1, double d2, double d3) {
      if (y == 0.0) {
         Entity entity = world.getEntityByID((int)x);
         if (entity instanceof EntityPlayer) {
            playMovingSound((EntityPlayer)entity);
         }
      } else if (y == 1.0) {
         Entity entity = world.getEntityByID((int)x);
         Entity entityplayer = world.getEntityByID((int)z);
         if (entityplayer instanceof EntityPlayer) {
            Render render = (Render)Minecraft.getMinecraft().getRenderManager().entityRenderMap.get(entity.getClass());
            if (render != null && render instanceof RenderLivingBase) {
               ModelBase model = ((RenderLivingBase)render).getMainModel();
               if (model != null) {
                  EntityPlayer playerTo = (EntityPlayer)entityplayer;
                  Vec3d partpos = this.getInstancerCorePoint(playerTo, playerTo.getPrimaryHand());
                  ParticleTracker tracker = new ParticleTracker.TrackerFollowStaticPoint(partpos, true, 0.3F, 0.001F, 0.18F);
                  float scl = 1.0F;
                  int lt = 14;
                  ModelledPartickle part = new ModelledPartickle(
                     texturesAnimation[0],
                     scl,
                     0.0F,
                     lt,
                     240,
                     world,
                     entity.posX,
                     entity.posY,
                     entity.posZ,
                     0.0F,
                     0.0F,
                     0.0F,
                     1.0F,
                     1.0F,
                     1.0F,
                     true,
                     0
                  );
                  part.setModel(model, entity);
                  part.scaleTickAdding = -scl / 26.0F;
                  part.tracker = tracker;
                  part.setTextureAnimation(texturesAnimation, 1);
                  world.spawnEntity(part);
                  ParticleTracker tracker2 = new ParticleTracker.TrackerFollowStaticPoint(partpos, true, 0.3F, 0.002F, 0.25F);

                  for (int i = 0; i < entity.height * 4.0F; i++) {
                     double posxx = entity.posX + (itemRand.nextFloat() - 0.5F) * entity.width;
                     double posyy = entity.posY + itemRand.nextFloat() * entity.height;
                     double poszz = entity.posZ + (itemRand.nextFloat() - 0.5F) * entity.width;
                     float sclx = 0.0325F;
                     int ltx = 15 + itemRand.nextInt(10);
                     float col = itemRand.nextFloat();
                     GUNParticle partx = new GUNParticle(
                        pixel,
                        sclx,
                        0.0F,
                        ltx,
                        240,
                        world,
                        posxx,
                        posyy,
                        poszz,
                        (float)itemRand.nextGaussian() / 8.0F,
                        (float)itemRand.nextGaussian() / 8.0F,
                        (float)itemRand.nextGaussian() / 8.0F,
                        1.0F - col * 0.35F,
                        1.0F - col * 0.2F,
                        1.0F,
                        false,
                        0
                     );
                     partx.scaleTickAdding = -sclx / ltx;
                     partx.tracker = tracker2;
                     world.spawnEntity(partx);
                  }
               }
            }
         }
      } else if (y == 2.0) {
         for (int i = 0; i < z * 6.0; i++) {
            double posxx = a + (itemRand.nextFloat() - 0.5F) * x;
            double posyy = b + itemRand.nextFloat() * z;
            double poszz = c + (itemRand.nextFloat() - 0.5F) * x;
            float scl = 0.0325F;
            int lt = 18 + itemRand.nextInt(12);
            float col = itemRand.nextFloat();
            GUNParticle part = new GUNParticle(
               pixel,
               scl,
               0.01F,
               lt,
               240,
               world,
               posxx,
               posyy,
               poszz,
               (float)itemRand.nextGaussian() / 5.0F,
               (float)itemRand.nextGaussian() / 5.0F,
               (float)itemRand.nextGaussian() / 5.0F,
               1.0F - col * 0.35F,
               1.0F - col * 0.2F,
               1.0F,
               true,
               1
            );
            part.scaleTickAdding = -scl / lt;
            world.spawnEntity(part);
         }
      }
   }

   public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
      return true;
   }

   @Override
   public boolean canAttackMelee(ItemStack itemstack, EntityPlayer player) {
      return false;
   }

   public boolean canDestroyBlockInCreative(World world, BlockPos pos, ItemStack stack, EntityPlayer player) {
      return false;
   }

   public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
      return slotChanged;
   }

   @SideOnly(Side.CLIENT)
   @Override
   public void bom(int param) {
      Booom.lastTick = 16;
      Booom.frequency = param == 1 ? 0.4F : 0.8F;
      Booom.x = 0.0F;
      Booom.y = (float)itemRand.nextGaussian();
      Booom.z = (float)itemRand.nextGaussian();
      Booom.power = 0.06F;
   }

   public Vec3d getInstancerCorePoint(EntityLivingBase player, EnumHandSide hand) {
      Vec3d plpos = player.getPositionVector();
      float height = 1.3125F;
      if (player.isSneaking()) {
         height -= 0.3125F;
      }

      float shoulders = 0.25F;
      float gunLength = 1.15F;
      float gunUp = -0.5F;
      Vec3d yawOffet = GetMOP.YawToVec3d(player.renderYawOffset + (hand == EnumHandSide.RIGHT ? 90 : -90)).scale(shoulders);
      Vec3d shoulderVec = yawOffet.add(player.posX, player.posY + height, player.posZ);
      Vec3d gunTipVec = shoulderVec.add(GetMOP.PitchYawToVec3d(player.rotationPitch, player.rotationYaw).scale(gunLength));
      return gunTipVec.add(GetMOP.PitchYawToVec3d(player.rotationPitch - 90.0F, player.rotationYaw).scale(gunUp));
   }

   public void spawnPartickles(World world, EntityPlayer player, boolean deploy) {
      if (deploy) {
         if (player.ticksExisted % 4 == 0) {
            this.bom(0);
         }

         Vec3d partpos = this.getInstancerCorePoint(player, player.getPrimaryHand());

         for (int i = 0; i < 4; i++) {
            float scl = 0.05F + itemRand.nextFloat() * 0.05F;
            int lt = 5 + itemRand.nextInt(7);
            GUNParticle part = new GUNParticle(
               star,
               scl,
               0.0F,
               lt,
               200,
               world,
               partpos.x + (itemRand.nextFloat() - 0.5F) / 2.0F,
               partpos.y + (itemRand.nextFloat() - 0.5F) / 2.0F,
               partpos.z + (itemRand.nextFloat() - 0.5F) / 2.0F,
               0.0F,
               0.0F,
               0.0F,
               1.0F,
               0.4F,
               0.2F,
               true,
               itemRand.nextInt(360),
               true,
               1.0F
            );
            part.alphaGlowing = true;
            part.scaleTickAdding = -scl / lt;
            part.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.0F, 10.0F);
            world.spawnEntity(part);
         }
      } else {
         if (player.ticksExisted % 4 == 0) {
            this.bom(1);
         }

         Vec3d partpos = this.getInstancerCorePoint(player, player.getPrimaryHand());
         ParticleTracker tracker = new ParticleTracker.TrackerFollowStaticPoint(partpos, true, 0.3F, 0.001F, 0.2F);

         for (int i = 0; i < 4; i++) {
            RayTraceResult result = GetMOP.fixedRayTraceBlocks(
               world,
               player,
               6.0,
               0.4,
               0.4,
               false,
               false,
               true,
               false,
               player.rotationPitch + itemRand.nextInt(17) - 8.0F,
               player.rotationYaw + itemRand.nextInt(17) - 8.0F
            );
            if (result != null && result.hitVec != null) {
               float scl = 0.05F + itemRand.nextFloat() * 0.05F;
               int lt = 15 + itemRand.nextInt(10);
               GUNParticle part = new GUNParticle(
                  star,
                  scl,
                  0.0F,
                  lt,
                  200,
                  world,
                  result.hitVec.x,
                  result.hitVec.y,
                  result.hitVec.z,
                  (float)itemRand.nextGaussian() / 20.0F,
                  (float)itemRand.nextGaussian() / 20.0F,
                  (float)itemRand.nextGaussian() / 20.0F,
                  1.0F,
                  0.4F,
                  0.2F,
                  true,
                  itemRand.nextInt(360)
               );
               part.alphaGlowing = true;
               part.scaleTickAdding = -scl / lt;
               part.tracker = tracker;
               part.alpha = 0.0F;
               part.alphaTickAdding = 0.12F;
               world.spawnEntity(part);
            }
         }
      }
   }

   public void onUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
      if (world.isRemote) {
         if (entityIn.ticksExisted % 2 == 0 && entityIn instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entityIn;
            if (player.getHeldItemMainhand() == itemstack && NBTHelper.GetNBTint(itemstack, "ready") >= 10) {
               boolean click = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
               boolean click2 = Keys.isKeyPressed(player, Keys.SECONDARYATTACK);
               if (click) {
                  this.spawnPartickles(world, player, true);
               } else if (click2) {
                  this.spawnPartickles(world, player, false);
               }
            }
         }
      } else {
         this.setCanShoot(itemstack, entityIn);
         if (IWeapon.canShoot(itemstack)) {
            EntityPlayer player = (EntityPlayer)entityIn;
            boolean click = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
            boolean click2 = Keys.isKeyPressed(player, Keys.SECONDARYATTACK);
            if (!itemstack.hasTagCompound() || !itemstack.getTagCompound().hasKey("mobs")) {
               NBTTagCompound itemCompound = new NBTTagCompound();
               NBTTagList taglistt = new NBTTagList();
               taglistt.appendTag(new NBTTagCompound());
               taglistt.removeTag(0);
               itemCompound.setTag("mobs", taglistt);
               itemstack.setTagCompound(itemCompound);
            }

            boolean sendAnim = false;
            int ready = NBTHelper.GetNBTint(itemstack, "ready");
            NBTHelper.GiveNBTint(itemstack, 0, "prevready");
            NBTHelper.SetNBTint(itemstack, ready, "prevready");
            if (player.getHeldItemMainhand() != itemstack) {
               if (ready > 0) {
                  NBTHelper.AddNBTint(itemstack, -1, "ready");
               }
            } else {
               if (click || click2) {
                  this.shootTick(itemstack, player, ready);
                  NBTHelper.GiveNBTint(itemstack, 0, "ready");
                  if (ready < 15) {
                     if (ready == 0) {
                        sendAnim = true;
                        world.playSound(
                           (EntityPlayer)null,
                           player.posX,
                           player.posY,
                           player.posZ,
                           Sounds.instancer_ready,
                           SoundCategory.AMBIENT,
                           0.9F,
                           1.0F
                        );
                     }

                     NBTHelper.AddNBTint(itemstack, 1, "ready");
                     if (ready == 14) {
                        world.playSound(
                           (EntityPlayer)null,
                           player.posX,
                           player.posY,
                           player.posZ,
                           click ? Sounds.instancer_deploy : Sounds.instancer_carry,
                           SoundCategory.AMBIENT,
                           0.9F,
                           1.0F
                        );
                        IWeapon.fireEffect(this, player, world, 16.0, (double)player.getEntityId(), 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
                     }
                  }

                  if (player.ticksExisted % 10 == 0) {
                     sendAnim = true;
                  }
               } else if (ready > 0) {
                  NBTHelper.AddNBTint(itemstack, -1, "ready");
                  if (ready == 15) {
                     world.playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.instancer_unready,
                        SoundCategory.AMBIENT,
                        0.9F,
                        1.0F
                     );
                  }
               }

               if (!player.getCooldownTracker().hasCooldown(this) && ready >= 10) {
                  if (click) {
                     NBTTagList taglist = itemstack.getTagCompound().getTagList("mobs", 10);
                     if (!taglist.isEmpty()) {
                        int index = itemRand.nextInt(taglist.tagCount());
                        NBTTagCompound entitytag = taglist.getCompoundTagAt(index);
                        int leadershipNeed = entitytag.getInteger("leadership");
                        int playerleadership = Mana.getLeadership(player);
                        int allLeadersh = AbstractMob.calculateLeadership(world, player);
                        if (allLeadersh + leadershipNeed <= playerleadership) {
                           NBTTagCompound mobtag = entitytag.getCompoundTag("mob");
                           Entity mob = this.getMob(world, mobtag, player.posX, player.posY, player.posZ);
                           if (mob != null) {
                              RayTraceResult result = GetMOP.rayTrace(world, 6.0, player, false);
                              if (result.hitVec != null) {
                                 double posX = result.hitVec.x;
                                 double posY = result.hitVec.y;
                                 double posZ = result.hitVec.z;
                                 if (result.sideHit != null) {
                                    float offset = mob.width / 2.0F + 0.01F;
                                    posX += result.sideHit.getXOffset() * offset;
                                    posY += result.sideHit.getYOffset() * offset;
                                    posZ += result.sideHit.getZOffset() * offset;
                                 }

                                 this.setupMobPos(world, mob, posX, posY, posZ);
                                 if (world.spawnEntity(mob)) {
                                    taglist.removeTag(index);
                                    player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack) + (int)(leadershipNeed * this.deploySlowness));
                                    NBTHelper.GiveNBTint(itemstack, 0, "leadershipHold");
                                    NBTHelper.AddNBTint(itemstack, -leadershipNeed, "leadershipHold");
                                    world.playSound(
                                       (EntityPlayer)null,
                                       mob.posX,
                                       mob.posY,
                                       mob.posZ,
                                       Sounds.instancer_summon,
                                       SoundCategory.AMBIENT,
                                       0.9F,
                                       1.0F
                                    );
                                    IWeapon.fireEffect(
                                       this,
                                       player,
                                       world,
                                       64.0,
                                       (double)mob.width,
                                       2.0,
                                       (double)mob.height,
                                       mob.posX,
                                       mob.posY,
                                       mob.posZ,
                                       0.0,
                                       0.0,
                                       0.0
                                    );
                                    if (!player.isCreative()) {
                                       itemstack.damageItem(1, player);
                                    }

                                    this.sendNewLeadership(world, player, allLeadersh + leadershipNeed);
                                 }
                              }
                           }
                        } else {
                           player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
                        }
                     } else {
                        player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
                     }
                  } else if (click2) {
                     int leadershipCaptured = 0;

                     for (EntityLivingBase base : this.getTracedCreatures(itemstack, player)) {
                        if (base instanceof AbstractMob) {
                           AbstractMob mob = (AbstractMob)base;
                           if (mob.owner == player) {
                              int leadershipNeed = Math.max(mob.getLeadershipNeed(), 1);
                              if (NBTHelper.GetNBTint(itemstack, "leadershipHold") + leadershipNeed <= this.getMaxLeadership(itemstack)
                                 && this.canCarry(itemstack, player, mob, leadershipNeed)) {
                                 this.doCarry(mob, mob, leadershipNeed, player, itemstack);
                                 leadershipCaptured += leadershipNeed;
                              }
                           }
                        } else if (base instanceof EntityTameable) {
                           EntityTameable mob = (EntityTameable)base;
                           if (mob.isTamed() && mob.isOwner(player)) {
                              int leadershipNeed = this.calculateLeadershipForNonAbstractMob(mob);
                              if (NBTHelper.GetNBTint(itemstack, "leadershipHold") + leadershipNeed <= this.getMaxLeadership(itemstack)
                                 && this.canCarry(itemstack, player, mob, leadershipNeed)) {
                                 this.doCarry(mob, null, leadershipNeed, player, itemstack);
                                 leadershipCaptured += leadershipNeed;
                              }
                           }
                        } else if (base instanceof NPCMobsPack.NpcTrader) {
                           NPCMobsPack.NpcTrader mob = (NPCMobsPack.NpcTrader)base;
                           int leadershipNeed = 25;
                           if (NBTHelper.GetNBTint(itemstack, "leadershipHold") + leadershipNeed <= this.getMaxLeadership(itemstack)
                              && this.canCarry(itemstack, player, mob, leadershipNeed)) {
                              this.doCarry(mob, null, leadershipNeed, player, itemstack);
                              leadershipCaptured += leadershipNeed;
                           }
                        } else if (base instanceof EntityCreature) {
                           EntityCreature mob = (EntityCreature)base;
                           int leadershipNeed = this.calculateLeadershipForNonAbstractMob(mob);
                           if (NBTHelper.GetNBTint(itemstack, "leadershipHold") + leadershipNeed <= this.getMaxLeadership(itemstack)
                              && this.canCarry(itemstack, player, mob, leadershipNeed)
                              && mob.getAttackTarget() != player) {
                              this.doCarry(mob, null, leadershipNeed, player, itemstack);
                              leadershipCaptured += leadershipNeed;
                           }
                        }
                     }

                     if (leadershipCaptured > 0) {
                        player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack) + (int)(leadershipCaptured * this.carrySlowness));
                     }
                  }
               }
            }

            if (sendAnim) {
               Weapons.setPlayerAnimationOnServer(player, 11, EnumHand.MAIN_HAND);
            }
         }
      }
   }

   public void doCarry(EntityLiving mob, @Nullable AbstractMob absMob, int leadershipNeed, EntityPlayer player, ItemStack itemstack) {
      NBTTagCompound serealized = mob.serializeNBT();
      if (absMob != null) {
         absMob.serializeNbtWasCalled = false;
      }

      NBTTagCompound mobtag = new NBTTagCompound();
      mobtag.setTag("mob", serealized);
      mobtag.setInteger("leadership", leadershipNeed);
      NBTTagList taglist = itemstack.getTagCompound().getTagList("mobs", 10);
      taglist.appendTag(mobtag);
      IWeapon.fireEffect(this, player, mob.world, 64.0, (double)mob.getEntityId(), 1.0, (double)player.getEntityId(), 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
      mob.setDead();
      NBTHelper.GiveNBTint(itemstack, 0, "leadershipHold");
      NBTHelper.AddNBTint(itemstack, leadershipNeed, "leadershipHold");
      mob.world
         .playSound(
            (EntityPlayer)null, mob.posX, mob.posY, mob.posZ, Sounds.instancer_capture, SoundCategory.AMBIENT, 0.9F, 1.0F
         );
      if (!player.isCreative()) {
         itemstack.damageItem(1, player);
      }
   }

   public boolean canCarry(ItemStack stack, EntityPlayer player, EntityLiving entity, int leadershipNeed) {
      return true;
   }

   public void shootTick(ItemStack stack, EntityPlayer player, int ready) {
   }

   public List<EntityLivingBase> getTracedCreatures(ItemStack stack, EntityPlayer player) {
      return GetMOP.MopRayTrace(16.0, 1.0F, player, 2.0, 1.0);
   }

   public int calculateLeadershipForNonAbstractMob(EntityLiving entity) {
      float hpbonus = entity.getMaxHealth() / 20.0F;
      double damagebonus = entity.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE) != null
         ? entity.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue() / 5.0
         : 0.0;
      double speedbonus = entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED) != null
         ? entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue() / 0.25
         : 0.0;
      double armorbonus = entity.getEntityAttribute(SharedMonsterAttributes.ARMOR) != null
         ? entity.getEntityAttribute(SharedMonsterAttributes.ARMOR).getAttributeValue() / 4.0
         : 0.0;
      double followrangebonus = entity.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE) != null
         ? entity.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getAttributeValue() / 64.0
         : 0.0;
      return (int)Math.max(hpbonus + damagebonus + speedbonus + armorbonus + followrangebonus, 1.0);
   }

   public Entity getMob(World world, NBTTagCompound nbttagcompound, double x, double y, double z) {
      double spawnRange = 2.0;
      return AnvilChunkLoader.readWorldEntityPos(nbttagcompound, world, x, y, z, false);
   }

   public void setupMobPos(World world, Entity entity, double x, double y, double z) {
      double spawnRange = 2.0;
      float yaw = world.rand.nextFloat() * 360.0F;
      double height = getLandHeight(world, new BlockPos(x, y, z));
      entity.setLocationAndAngles(x, height, z, yaw, 0.0F);

      for (int i = 0; i < 16 && world.collidesWithAnyBlock(entity.getEntityBoundingBox()); i++) {
         double d0 = x + (itemRand.nextDouble() - itemRand.nextDouble()) * spawnRange;
         double d2 = z + (itemRand.nextDouble() - itemRand.nextDouble()) * spawnRange;
         double d1 = getLandHeight(world, new BlockPos(d0, y, d2));
         entity.setLocationAndAngles(d0, d1, d2, yaw, 0.0F);
         spawnRange += 0.2;
      }
   }

   public static double getLandHeight(World world, BlockPos pos) {
      int y = 0;

      for (int i = 0; i < 5; i++) {
         BlockPos pos1 = pos.up(y);
         BlockPos pos2 = pos.up(y + 1);
         IBlockState state = world.getBlockState(pos1);
         IBlockState stateUp = world.getBlockState(pos2);
         if (stateUp.getCollisionBoundingBox(world, pos2) == null) {
            AxisAlignedBB aabb = state.getCollisionBoundingBox(world, pos1);
            if (aabb != null) {
               return pos1.getY() + aabb.maxY;
            }
         }

         if (y >= 0) {
            y = -y - 1;
         } else {
            y = -y;
         }
      }

      return pos.getY();
   }

   public void sendNewLeadership(World world, EntityPlayer player, int leadershipAll) {
      if (player instanceof EntityPlayerMP) {
         PacketSmallSomethingToClients packet = new PacketSmallSomethingToClients();
         packet.writeargs(2, leadershipAll);
         PacketHandler.sendTo(packet, (EntityPlayerMP)player);
      }
   }

   @Override
   public float getAdditionalDurabilityBar(ItemStack itemstack) {
      return MathHelper.clamp((float)NBTHelper.GetNBTint(itemstack, "leadershipHold") / this.getMaxLeadership(itemstack), 0.0F, 1.0F);
   }

   @Override
   public boolean hasAdditionalDurabilityBar(ItemStack itemstack) {
      return true;
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return false;
   }

   @Override
   public int getReloadTime(ItemStack itemstack) {
      int rellvl = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RELOADING, itemstack);
      return 25 - rellvl * 3;
   }

   @Override
   public int getCooldownTime(ItemStack itemstack) {
      int rapidity = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack);
      return 9 - rapidity;
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.TWO_HANDED;
   }

   @Override
   public int getItemEnchantability() {
      return 2;
   }

   public int getMaxLeadership(ItemStack itemstack) {
      int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, itemstack);
      return 50 + might * 5;
   }

   @SideOnly(Side.CLIENT)
   public static class MovingSoundInstancer extends MovingSound {
      public final EntityPlayer entity;
      public boolean deploy;

      public MovingSoundInstancer(EntityPlayer entity, boolean deploy, SoundCategory category, float volume, float pitch, boolean repeat) {
         super(deploy ? Sounds.instancer_deploy_loop : Sounds.instancer_carry_loop, category);
         this.entity = entity;
         this.repeat = repeat;
         this.volume = volume;
         this.pitch = pitch;
         this.deploy = deploy;
      }

      public void update() {
         if (!this.entity.isDead) {
            this.xPosF = (float)this.entity.posX;
            this.yPosF = (float)this.entity.posY;
            this.zPosF = (float)this.entity.posZ;
            ItemStack stack = this.entity.getHeldItemMainhand();
            if (!stack.isEmpty() && stack.getItem() instanceof Instancer) {
               int ready = NBTHelper.GetNBTint(stack, "ready");
               if (ready > 13) {
                  if (this.deploy) {
                     if (Keys.isKeyPressed(this.entity, Keys.PRIMARYATTACK)) {
                        this.volume = 1.0F;
                     } else if (Keys.isKeyPressed(this.entity, Keys.SECONDARYATTACK)) {
                        this.volume = 0.0F;
                     }
                  } else if (Keys.isKeyPressed(this.entity, Keys.SECONDARYATTACK) && !Keys.isKeyPressed(this.entity, Keys.PRIMARYATTACK)) {
                     this.volume = 1.0F;
                  } else {
                     this.volume = 0.0F;
                  }
               } else {
                  float ft = GetMOP.getfromto((float)ready, 5.0F, 13.0F);
                  if (this.volume > ft) {
                     this.volume = ft;
                  }
               }
            } else {
               this.donePlaying = true;
            }
         } else {
            this.donePlaying = true;
         }
      }
   }
}
