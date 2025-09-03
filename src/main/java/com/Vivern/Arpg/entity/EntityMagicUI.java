//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.entity;

import baubles.api.BaublesApi;
import com.Vivern.Arpg.events.Debugger;
import com.Vivern.Arpg.main.AnimationTimer;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.IMagicUI;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Keys;
import com.Vivern.Arpg.main.Main;
import com.Vivern.Arpg.main.Mana;
import com.Vivern.Arpg.main.ShardType;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.SuperKnockback;
import com.Vivern.Arpg.network.PacketMUIClickToServer;
import com.Vivern.Arpg.network.PacketMUIOpenToServer;
import com.Vivern.Arpg.recipes.MUIParameters;
import com.Vivern.Arpg.recipes.ManaProvider;
import com.Vivern.Arpg.recipes.Seal;
import com.Vivern.Arpg.renders.GUNParticle;
import com.Vivern.Arpg.renders.MagicSubparticle;
import com.Vivern.Arpg.renders.ParticleTracker;
import com.Vivern.Arpg.renders.RenderEntityMUI;
import com.Vivern.Arpg.tileentity.IManaBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ITeleporter;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@EventBusSubscriber(
   modid = "arpg"
)
public abstract class EntityMagicUI extends Entity implements IEntitySynchronize {
   public static ResourceLocation texMapRunes1 = new ResourceLocation("arpg:textures/seals/runes1.png");
   public static ResourceLocation texMapRunes2 = new ResourceLocation("arpg:textures/seals/runes2.png");
   public static ResourceLocation texStar = new ResourceLocation("arpg:textures/magic_rocket.png");
   public static ResourceLocation texMapSelection1 = new ResourceLocation("arpg:textures/mui/selection1.png");
   public static ResourceLocation texMapSelection2 = new ResourceLocation("arpg:textures/mui/selection2.png");
   public static ResourceLocation texMapSelection3 = new ResourceLocation("arpg:textures/mui/selection3.png");
   public static ResourceLocation texMapSelection4 = new ResourceLocation("arpg:textures/mui/selection4.png");
   public static ResourceLocation texMapSelection5 = new ResourceLocation("arpg:textures/mui/selection5.png");
   public static ResourceLocation texMapSelection6 = new ResourceLocation("arpg:textures/mui/selection6.png");
   public static ResourceLocation texMapSelection7 = new ResourceLocation("arpg:textures/mui/selection7.png");
   public static ResourceLocation texMapSelection8 = new ResourceLocation("arpg:textures/mui/selection8.png");
   public static ResourceLocation texMapSelection9 = new ResourceLocation("arpg:textures/mui/selection9.png");
   public BlockPos origin = BlockPos.ORIGIN;
   public boolean removing;
   public float removeTick = 0.0F;
   public float displace = 0.0F;
   public boolean inSelection = false;
   public List<EntityMagicUI> linkedEntitymuis = new ArrayList<>();
   public float grabDistance = 0.0F;
   public boolean pressedLastTick = false;
   public boolean isPressedNow = false;
   public EntityPlayer lastPressingPlayer;
   public EnumFacing facing = null;
   public float rotationOnFloor = 0.0F;
   public int timeGrabbed = 0;
   public NBTTagList postReadNbtlist = null;
   public static boolean usemagicPRESS = false;
   public static int regidstart = 500;

   public EntityMagicUI(World worldIn) {
      super(worldIn);
      this.setSize(0.25F, 0.25F);
      this.removing = !worldIn.isRemote;
   }

   public EntityMagicUI(World worldIn, BlockPos origin) {
      super(worldIn);
      this.setSize(0.25F, 0.25F);
      this.origin = origin;
   }

   public void perform(EntityMagicUI entityBy, Entity activatingEntity, ManaProvider mana) {
      System.out.println("performed: " + this.getClass().getName() + " | mana: " + mana.getMana());
   }

   public void cast(EntityMagicUI entityBy, Entity activatingEntity, ManaProvider mana) {
      System.out.println("casted: " + this.getClass().getName() + " | mana: " + mana.getMana());
      if (mana.getMana() >= 1.0F) {
         mana.addMana(-1.0F);

         for (EntityMagicUI emui : this.linkedEntitymuis) {
            if (emui != entityBy) {
               emui.perform(this, activatingEntity, mana);
            }
         }

         for (EntityMagicUI emuix : this.linkedEntitymuis) {
            if (emuix != entityBy) {
               emuix.cast(this, activatingEntity, mana);
            }
         }
      }
   }

   @Nullable
   public Object getValue(EntityMagicUI entityBy, Entity activatingEntity, ManaProvider mana) {
      return this.getClass();
   }

   @Nullable
   public EntityMagicUI getRandomNext(EntityMagicUI entityBy, Entity activatingEntity, ManaProvider mana) {
      if (!this.linkedEntitymuis.isEmpty()) {
         if (this.linkedEntitymuis.size() == 1) {
            return this.linkedEntitymuis.get(0) == entityBy ? null : this.linkedEntitymuis.get(0);
         } else {
            int randint = this.rand.nextInt(this.linkedEntitymuis.size());
            return this.linkedEntitymuis.get(randint) == entityBy
               ? this.linkedEntitymuis.get(GetMOP.next(randint, 1, this.linkedEntitymuis.size()))
               : this.linkedEntitymuis.get(randint);
         }
      } else {
         return null;
      }
   }

   public SoundCategory getSoundCategory() {
      return SoundCategory.NEUTRAL;
   }

   @Nullable
   public static IMagicUI getMUIinPos(World world, @Nullable BlockPos pos) {
      if (pos != null) {
         Block block = world.getBlockState(pos).getBlock();
         if (block instanceof IMagicUI) {
            return (IMagicUI)block;
         }

         TileEntity tile = world.getTileEntity(pos);
         if (tile instanceof IMagicUI) {
            return (IMagicUI)tile;
         }
      }

      return null;
   }

   @SubscribeEvent
   public static void clientTick(ClientTickEvent event) {
      EntityPlayer player = Minecraft.getMinecraft().player;
      if (player != null) {
         boolean key = Keys.isKeyPressed(player, Keys.USE);

         for (Entity entity : player.world.loadedEntityList) {
            if (player.getDistanceSq(entity) <= 64.0
               && entity instanceof EntityMagicUI
               && GetMOP.entityUncollidedRayTrace(EntityMagicUI.class, 8.0, 1.0F, player, 0.1, 0.1, player.rotationPitch, player.rotationYaw)
                  .contains(entity)) {
               ((EntityMagicUI)entity).inSelection = true;
               if (key) {
                  PacketMUIClickToServer.send(entity.getEntityId());
               }

               return;
            }
         }

         if (key && !usemagicPRESS) {
            RayTraceResult res = player.rayTrace(8.0, 1.0F);
            if (res != null && res.getBlockPos() != null && res.typeOfHit == Type.BLOCK) {
               PacketMUIOpenToServer.send(res.getBlockPos());
            }
         }

         usemagicPRESS = key;
      }
   }

   public static void registerRender() {
      RenderEntityMUI.RenderEntityMUIFactory rmuif = new RenderEntityMUI.RenderEntityMUIFactory();
      RenderingRegistry.registerEntityRenderingHandler(EntityMUIMirror.class, rmuif);
      RenderingRegistry.registerEntityRenderingHandler(EntityMUIIf.class, rmuif);
      RenderingRegistry.registerEntityRenderingHandler(EntityMUIThen.class, rmuif);
      RenderingRegistry.registerEntityRenderingHandler(EntityMUIAnd.class, rmuif);
      RenderingRegistry.registerEntityRenderingHandler(EntityMUIOr.class, rmuif);
      RenderingRegistry.registerEntityRenderingHandler(EntityMUIEquals.class, rmuif);
      RenderingRegistry.registerEntityRenderingHandler(EntityMUISound.class, rmuif);
      RenderingRegistry.registerEntityRenderingHandler(EntityMUISeal.class, rmuif);
      RenderingRegistry.registerEntityRenderingHandler(EntityMUILoop.class, rmuif);
      RenderingRegistry.registerEntityRenderingHandler(EntityMUIBig.class, rmuif);
      RenderingRegistry.registerEntityRenderingHandler(EntityMUIPowerful.class, rmuif);
      RenderingRegistry.registerEntityRenderingHandler(EntityMUISharp.class, rmuif);
      RenderingRegistry.registerEntityRenderingHandler(EntityMUIBeing.class, rmuif);
      RenderingRegistry.registerEntityRenderingHandler(EntityMUIManaBuffer.class, rmuif);
      RenderingRegistry.registerEntityRenderingHandler(EntityMUIRedstone.class, rmuif);
      RenderingRegistry.registerEntityRenderingHandler(EntityMUICapacitor.class, rmuif);
      RenderingRegistry.registerEntityRenderingHandler(EntityMUIGain.class, rmuif);
   }

   public static void register() {
      register("mui_mirror", EntityMUIMirror.class);
      register("mui_if", EntityMUIIf.class);
      register("mui_then", EntityMUIThen.class);
      register("mui_and", EntityMUIAnd.class);
      register("mui_or", EntityMUIOr.class);
      register("mui_equals", EntityMUIEquals.class);
      register("mui_sound", EntityMUISound.class);
      register("mui_seal", EntityMUISeal.class);
      register("mui_loop", EntityMUILoop.class);
      register("mui_big", EntityMUIBig.class);
      register("mui_powerful", EntityMUIPowerful.class);
      register("mui_sharp", EntityMUISharp.class);
      register("mui_being", EntityMUIBeing.class);
      register("mui_manabuffer", EntityMUIManaBuffer.class);
      register("mui_redstone", EntityMUIRedstone.class);
      register("mui_capacitor", EntityMUICapacitor.class);
      register("mui_gain", EntityMUIGain.class);
   }

   public static void register(String entityName, Class<? extends Entity> entityClass) {
      EntityRegistry.registerModEntity(new ResourceLocation("arpg", entityName), entityClass, "arpg:" + entityName, regidstart++, Main.instance, 48, 1, true);
   }

   public void onDataSend(double anyVarToSend) {
      IEntitySynchronize.sendSynchronize(this, 64.0, this.origin.getX(), this.origin.getY(), this.origin.getZ());
   }

   public EntityMagicUI setDisplace(float d) {
      this.displace = d;
      return this;
   }

   public void setRemoved() {
      this.removing = true;
      IEntitySynchronize.sendSynchronize(this, 64.0, -2.0);
   }

   public void flyAround(float radius, float speed, float yUp) {
      double movex = this.posX;
      double movey = this.posY;
      double movez = this.posZ;
      movex = this.origin.getX() + 0.5 + radius * Math.sin(Math.toRadians((double)speed * this.ticksExisted + this.displace));
      movey = this.origin.getY() + yUp;
      movez = this.origin.getZ() + 0.5 + radius * Math.cos(Math.toRadians((double)speed * this.ticksExisted + this.displace));
      this.motionX /= 3.0;
      this.motionY /= 3.0;
      this.motionZ /= 3.0;
      float kb = (float)this.getDistance(movex, movey, movez) / 2.0F;
      SuperKnockback.applyMove(this, (float)(-Math.min((double)kb, 0.4)), movex, movey, movez);
      this.velocityChanged = true;
   }

   public boolean isPushedByWater() {
      return false;
   }

   public boolean handleWaterMovement() {
      return false;
   }

   public boolean canBePushed() {
      return false;
   }

   public boolean isBurning() {
      return false;
   }

   public void setFire(int seconds) {
   }

   public boolean isGlowing() {
      return false;
   }

   public Entity changeDimension(int dimensionIn) {
      return this;
   }

   public Entity changeDimension(int dimensionIn, ITeleporter teleporter) {
      return this;
   }

   public int getPortalCooldown() {
      return 999;
   }

   public double getYOffset() {
      return this.height / 2.0F;
   }

   public void checkForLinks() {
      for (Entity entity : GetMOP.getEntitiesInAABBof(this.world, this, 0.4125, this)) {
         if (this.getDistanceSq(entity) <= 0.17015624999999998 && entity instanceof EntityMagicUI) {
            EntityMagicUI mui = (EntityMagicUI)entity;
            if (!mui.linkedEntitymuis.contains(this)) {
               mui.linkedEntitymuis.add(this);
               IEntitySynchronize.sendSynchronize(
                  this,
                  32.0,
                  mui.posX,
                  mui.posY + mui.height / 2.0F,
                  mui.posZ,
                  this.posX,
                  this.posY + this.height / 2.0F,
                  this.posZ,
                  10.0
               );
            }

            if (this.getDistanceSq(entity) < 0.09765625) {
               SuperKnockback.applyMove(entity, 0.05F, this.posX, this.posY, this.posZ);
            }
         }
      }
   }

   public void removeFarLinks() {
      List<EntityMagicUI> UNlinkedEntitymuis = new ArrayList<>();

      for (EntityMagicUI linked : this.linkedEntitymuis) {
         if (this.getDistanceSq(linked) > 0.6400000000000001) {
            UNlinkedEntitymuis.add(linked);
            IEntitySynchronize.sendSynchronize(
               this,
               32.0,
               linked.posX,
               linked.posY + linked.height / 2.0F,
               linked.posZ,
               this.posX,
               this.posY + this.height / 2.0F,
               this.posZ,
               -10.0
            );
         }
      }

      this.linkedEntitymuis.removeAll(UNlinkedEntitymuis);
   }

   public void onUpdate() {
      float frontOffset = 0.01F;
      this.lastTickPosX = this.posX;
      this.lastTickPosY = this.posY;
      this.lastTickPosZ = this.posZ;
      super.onUpdate();
      this.motionX *= 0.8;
      this.motionY *= 0.8;
      this.motionZ *= 0.8;
      if (!this.world.isRemote) {
         if (this.ticksExisted > 1 && this.postReadNbtlist != null) {
            this.linkedEntitymuis.clear();

            for (NBTBase base : this.postReadNbtlist) {
               NBTTagCompound tag = (NBTTagCompound)base;
               if (tag.hasKey("posX") && tag.hasKey("posZ") && tag.hasKey("uuidLeast") && tag.hasKey("uuidMost")) {
                  UUID uuid = tag.getUniqueId("uuid");
                  Entity loadedEntity = GetMOP.loadEntity(this.world, tag.getInteger("posX"), tag.getInteger("posZ"), uuid);
                  if (loadedEntity != null && loadedEntity instanceof EntityMagicUI) {
                     this.linkedEntitymuis.add((EntityMagicUI)loadedEntity);
                  }
               }
            }

            this.postReadNbtlist = null;
         }

         if (this.ticksExisted < 2 || this.ticksExisted % 40 == 0) {
            IEntitySynchronize.sendSynchronize(this, 64.0, this.facing == null ? -1.0 : this.facing.getIndex(), this.rotationOnFloor);
         }

         if (this.grabDistance > 0.0F && this.lastPressingPlayer != null) {
            this.timeGrabbed++;
            double blockReachDist = this.grabDistance + 0.25;
            if (Keys.isKeyPressed(this.lastPressingPlayer, Keys.USE)) {
               if (this.timeGrabbed > 6) {
                  Vec3d vec = this.lastPressingPlayer
                     .getPositionEyes(1.0F)
                     .add(this.lastPressingPlayer.getLookVec().scale(this.grabDistance));
                  float power = 0.13F;
                  float prunex = (float)((vec.x - this.posX) * power);
                  float pruney = (float)((vec.y - this.posY) * power);
                  float prunez = (float)((vec.z - this.posZ) * power);
                  this.motionX += prunex;
                  this.motionY += pruney;
                  this.motionZ += prunez;
                  if (this.timeGrabbed > 15 && this.ticksExisted % 5 == 0) {
                     this.checkForLinks();
                  }
               }
            } else {
               this.grabDistance = 0.0F;
               if (this.timeGrabbed < 6 && this.timeGrabbed > 0) {
                  ManaProvider mpPlayer = new ManaProvider.ManaProviderPlayer(this.lastPressingPlayer);
                  this.perform(this, this.lastPressingPlayer, mpPlayer);
                  this.cast(this, this.lastPressingPlayer, mpPlayer);
               }

               this.timeGrabbed = 0;
            }

            if (this.timeGrabbed > 6) {
               Vec3d vec3d = this.lastPressingPlayer.getPositionEyes(1.0F);
               Vec3d vec3d1 = this.lastPressingPlayer.getLook(1.0F);
               Vec3d vec3d2 = vec3d.add(
                  vec3d1.x * blockReachDist, vec3d1.y * blockReachDist, vec3d1.z * blockReachDist
               );
               RayTraceResult res = this.world.rayTraceBlocks(vec3d, vec3d2, false, true, false);
               if (res != null && res.typeOfHit == Type.BLOCK && res.sideHit != null && res.hitVec != null) {
                  float addx = res.sideHit.getXOffset() * frontOffset;
                  float addy = res.sideHit.getYOffset() * frontOffset;
                  float addz = res.sideHit.getZOffset() * frontOffset;
                  this.setRunePosition(
                     this.lastPressingPlayer,
                     res.hitVec.x + addx,
                     res.hitVec.y + addy - this.height / 2.0F,
                     res.hitVec.z + addz,
                     res.sideHit
                  );
                  this.motionX = 0.0;
                  this.motionY = 0.0;
                  this.motionZ = 0.0;
                  float prevrotationOnFloor = this.rotationOnFloor;
                  if (res.sideHit == EnumFacing.UP) {
                     this.setRuneFloorAngle(this.lastPressingPlayer, this.lastPressingPlayer.rotationYaw);
                  } else if (res.sideHit == EnumFacing.DOWN) {
                     this.setRuneFloorAngle(this.lastPressingPlayer, -this.lastPressingPlayer.rotationYaw);
                  } else {
                     this.rotationOnFloor = 0.0F;
                  }

                  if (prevrotationOnFloor != this.rotationOnFloor) {
                     IEntitySynchronize.sendSynchronize(this, 64.0, res.sideHit.getIndex(), this.rotationOnFloor);
                  } else if (this.facing != res.sideHit) {
                     IEntitySynchronize.sendSynchronize(this, 64.0, res.sideHit.getIndex());
                  }

                  this.facing = res.sideHit;
               }
            }
         } else {
            Vec3d vec1 = GetMOP.entityCenterPos(this);
            Vec3d vec2 = vec1.add(this.motionX, this.motionY, this.motionZ);
            RayTraceResult res = this.world.rayTraceBlocks(vec1, vec2, false, true, false);
            if (res != null && res.typeOfHit == Type.BLOCK && res.sideHit != null && res.hitVec != null) {
               float addxx = res.sideHit.getXOffset() * frontOffset;
               float addyx = res.sideHit.getYOffset() * frontOffset;
               float addzx = res.sideHit.getZOffset() * frontOffset;
               this.posX = res.hitVec.x + addxx;
               this.posY = res.hitVec.y + addyx - this.height / 2.0F;
               this.posZ = res.hitVec.z + addzx;
               if (res.sideHit.getAxis() == Axis.X) {
                  this.motionX = 0.0;
               }

               if (res.sideHit.getAxis() == Axis.Y) {
                  this.motionY = 0.0;
               }

               if (res.sideHit.getAxis() == Axis.Z) {
                  this.motionZ = 0.0;
               }

               if (this.facing != res.sideHit) {
                  IEntitySynchronize.sendSynchronize(this, 64.0, res.sideHit.getIndex());
               }

               this.facing = res.sideHit;
            }
         }

         if (this.ticksExisted % 15 == 0) {
            this.removeFarLinks();
         }
      }

      this.posX = this.posX + this.motionX;
      this.posY = this.posY + this.motionY;
      this.posZ = this.posZ + this.motionZ;
      if (this.world.isRemote) {
         this.inSelection = false;
      }

      if (this.removing) {
         this.removeTick++;
         if (this.removeTick >= 10.0F) {
            this.setDead();
         }
      }

      this.setPosition(this.posX, this.posY, this.posZ);
      if (!this.isPressedNow && this.pressedLastTick) {
         this.onPressEnd();
      }

      this.pressedLastTick = this.isPressedNow;
      if (this.lastPressingPlayer == null || !Keys.isKeyPressed(this.lastPressingPlayer, Keys.USE)) {
         this.isPressedNow = false;
      }
   }

   public boolean onPressTick(EntityPlayer player) {
      this.isPressedNow = Keys.isKeyPressed(player, Keys.USE);
      this.lastPressingPlayer = player;
      if (!this.pressedLastTick) {
         this.onPressStart(player);
      }

      return false;
   }

   public void onPressEnd() {
   }

   public void onPressStart(EntityPlayer player) {
      this.grabDistance = (float)player.getPositionEyes(1.0F).distanceTo(GetMOP.entityCenterPos(this));
   }

   public void setRunePosition(@Nullable EntityPlayer playerBy, double x, double y, double z, EnumFacing facingOn) {
      int equip = playerBy == null ? -1 : BaublesApi.isBaubleEquipped(playerBy, ItemsRegister.SAPPHIREEYE);
      if (equip < 0) {
         this.posX = x;
         this.posY = y;
         this.posZ = z;
      } else if (equip == 3) {
         if (facingOn.getAxis() == Axis.X) {
            this.posX = x;
         } else {
            this.posX = (int)(x * 16.0) / 16.0;
         }

         if (facingOn.getAxis() == Axis.Y) {
            this.posY = y;
         } else {
            this.posY = (int)(y * 16.0) / 16.0;
         }

         if (facingOn.getAxis() == Axis.Z) {
            this.posZ = z;
         } else {
            this.posZ = (int)(z * 16.0) / 16.0;
         }
      } else {
         if (facingOn.getAxis() == Axis.X) {
            this.posX = x;
         } else {
            this.posX = (int)(x * 16.0) / 16.0 + 0.03125;
         }

         if (facingOn.getAxis() == Axis.Y) {
            this.posY = y;
         } else {
            this.posY = (int)(y * 16.0) / 16.0 + 0.03125;
         }

         if (facingOn.getAxis() == Axis.Z) {
            this.posZ = z;
         } else {
            this.posZ = (int)(z * 16.0) / 16.0 + 0.03125;
         }
      }
   }

   public void setRuneFloorAngle(@Nullable EntityPlayer playerBy, float angle) {
      if (playerBy != null && BaublesApi.isBaubleEquipped(playerBy, ItemsRegister.SAPPHIREEYE) >= 0) {
         this.rotationOnFloor = (int)(angle / 5.0F) * 5;
      } else {
         this.rotationOnFloor = angle;
      }
   }

   @SideOnly(Side.CLIENT)
   public void renderAsEntity(double x, double y, double z, float entityYaw, float partialTicks, RenderManager renderManager) {
   }

   protected void entityInit() {
   }

   protected void readEntityFromNBT(NBTTagCompound compound) {
      if (compound.hasKey("linked")) {
         this.postReadNbtlist = compound.getTagList("linked", 10);
      }

      if (compound.hasKey("removing")) {
         this.removing = compound.getBoolean("removing");
      }

      if (compound.hasKey("originX") && compound.hasKey("originY") && compound.hasKey("originZ")) {
         this.origin = new BlockPos(compound.getInteger("originX"), compound.getInteger("originY"), compound.getInteger("originZ"));
      }

      int facingId = -1;
      if (compound.hasKey("facing")) {
         facingId = compound.getInteger("facing");
         this.facing = facingId < 0 ? null : EnumFacing.byIndex(facingId);
      }

      if (compound.hasKey("rotationOnFloor")) {
         this.rotationOnFloor = compound.getFloat("rotationOnFloor");
      }
   }

   protected void writeEntityToNBT(NBTTagCompound compound) {
      NBTTagList linked = new NBTTagList();

      for (EntityMagicUI inlist : this.linkedEntitymuis) {
         NBTTagCompound tag = new NBTTagCompound();
         tag.setUniqueId("uuid", inlist.getUniqueID());
         ChunkPos cpos = new ChunkPos(new BlockPos(inlist));
         tag.setInteger("posX", cpos.x);
         tag.setInteger("posZ", cpos.z);
         linked.appendTag(tag);
      }

      compound.setTag("linked", linked);
      compound.setBoolean("removing", this.removing);
      if (this.origin != null) {
         compound.setInteger("originX", this.origin.getX());
         compound.setInteger("originY", this.origin.getY());
         compound.setInteger("originZ", this.origin.getZ());
      }

      int facId = this.facing == null ? -1 : this.facing.getIndex();
      compound.setInteger("facing", facId);
      compound.setFloat("rotationOnFloor", this.rotationOnFloor);
   }

   @Override
   public void onClient(double... args) {
      if (args.length == 1) {
         int i = (int)args[0];
         if (i >= 0 && i <= 5) {
            this.facing = EnumFacing.byIndex(i);
         } else if (i == -1) {
            this.facing = null;
         } else if (i == -2) {
            this.removing = true;
         }
      }

      if (args.length == 2) {
         int i = (int)args[0];
         if (i >= 0 && i <= 5) {
            this.facing = EnumFacing.byIndex(i);
         } else if (i == -1) {
            this.facing = null;
         }

         this.rotationOnFloor = (float)args[1];
      }

      if (args.length == 3) {
         this.origin = new BlockPos(args[0], args[1], args[2]);
      }

      if (args.length == 7) {
         if (args[6] < 0.0) {
            this.spawnParticlesUnlink(new Vec3d(args[0], args[1], args[2]), new Vec3d(args[3], args[4], args[5]), (int)(-args[6]));
         } else {
            this.spawnParticlesLink(new Vec3d(args[0], args[1], args[2]), new Vec3d(args[3], args[4], args[5]), (int)args[6]);
         }
      }
   }

   @SideOnly(Side.CLIENT)
   public void drawSelection(
      double x,
      double y,
      double z,
      float entityYaw,
      float partialTicks,
      RenderManager renderManager,
      float r,
      float g,
      float b,
      float size,
      ResourceLocation tex
   ) {
      GlStateManager.pushMatrix();
      GlStateManager.translate((float)x, (float)y + this.getYOffset(), (float)z);
      GlStateManager.enableRescaleNormal();
      GlStateManager.disableLighting();
      GL11.glDepthMask(false);
      GlStateManager.rotate(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate((renderManager.options.thirdPersonView == 2 ? -1 : 1) * renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
      GlStateManager.rotate(AnimationTimer.tick, 0.0F, 0.0F, 1.0F);
      GL11.glEnable(3042);
      GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
      renderManager.renderEngine.bindTexture(tex);
      if (this.removeTick > 0.0F) {
         size += this.removeTick * size / 30.0F;
         r /= this.removeTick;
         g /= this.removeTick;
         b /= this.removeTick;
      }

      size *= Math.min((float)this.ticksExisted, 10.0F) / 10.0F;
      GlStateManager.color(r, g, b, 1.0F);
      GlStateManager.scale(size, size, size);
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
      GL11.glDisable(2896);
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
      bufferbuilder.pos(-1.0, -1.0, 0.0).tex(0.0, 1.0).endVertex();
      bufferbuilder.pos(-1.0, 1.0, 0.0).tex(0.0, 0.0).endVertex();
      bufferbuilder.pos(1.0, 1.0, 0.0).tex(1.0, 0.0).endVertex();
      bufferbuilder.pos(1.0, -1.0, 0.0).tex(1.0, 1.0).endVertex();
      tessellator.draw();
      GlStateManager.disableRescaleNormal();
      GL11.glDisable(3042);
      GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      GL11.glEnable(2896);
      GL11.glDepthMask(true);
      GlStateManager.popMatrix();
   }

   @SideOnly(Side.CLIENT)
   public static void drawDecorative(
      ResourceLocation tex,
      double x,
      double y,
      double z,
      float partialTicks,
      RenderManager renderManager,
      float r,
      float g,
      float b,
      float size,
      float rotationSpeed
   ) {
      GlStateManager.pushMatrix();
      GlStateManager.translate((float)x, (float)y, (float)z);
      GlStateManager.enableRescaleNormal();
      GlStateManager.disableLighting();
      GL11.glDepthMask(false);
      GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.rotate(AnimationTimer.tick * rotationSpeed, 0.0F, 0.0F, 1.0F);
      GL11.glEnable(3042);
      GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
      renderManager.renderEngine.bindTexture(tex);
      GlStateManager.color(r, g, b, 1.0F);
      GlStateManager.scale(size, size, size);
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
      GL11.glDisable(2896);
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
      bufferbuilder.pos(-1.0, -1.0, 0.0).tex(0.0, 1.0).endVertex();
      bufferbuilder.pos(-1.0, 1.0, 0.0).tex(0.0, 0.0).endVertex();
      bufferbuilder.pos(1.0, 1.0, 0.0).tex(1.0, 0.0).endVertex();
      bufferbuilder.pos(1.0, -1.0, 0.0).tex(1.0, 1.0).endVertex();
      tessellator.draw();
      GlStateManager.disableRescaleNormal();
      GL11.glDisable(3042);
      GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      GL11.glEnable(2896);
      GL11.glDepthMask(true);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.popMatrix();
   }

   @SideOnly(Side.CLIENT)
   public static void drawDefaultRune(
      ResourceLocation tex, float rotation, double x, double y, double z, float partialTicks, RenderManager renderManager, float size
   ) {
      GlStateManager.pushMatrix();
      GlStateManager.translate((float)x, (float)y, (float)z);
      GlStateManager.enableRescaleNormal();
      GlStateManager.disableCull();
      GlStateManager.rotate(rotation, 0.0F, 1.0F, 0.0F);
      GL11.glEnable(3042);
      GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
      renderManager.renderEngine.bindTexture(tex);
      GL11.glDepthMask(false);
      GlStateManager.scale(size, size, size);
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
      GL11.glDisable(2896);
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
      bufferbuilder.pos(-1.0, -1.0, 0.0).tex(0.0, 1.0).endVertex();
      bufferbuilder.pos(-1.0, 1.0, 0.0).tex(0.0, 0.0).endVertex();
      bufferbuilder.pos(1.0, 1.0, 0.0).tex(1.0, 0.0).endVertex();
      bufferbuilder.pos(1.0, -1.0, 0.0).tex(1.0, 1.0).endVertex();
      tessellator.draw();
      GlStateManager.disableRescaleNormal();
      GL11.glDisable(3042);
      GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      GL11.glEnable(2896);
      GL11.glDepthMask(true);
      GlStateManager.enableCull();
      GlStateManager.popMatrix();
   }

   @SideOnly(Side.CLIENT)
   public void drawSpriteRune(ResourceLocation tex, float rotation, double x, double y, double z, float partialTicks, RenderManager renderManager, float size) {
      GlStateManager.pushMatrix();
      GlStateManager.translate((float)x, (float)y, (float)z);
      GlStateManager.enableRescaleNormal();
      GlStateManager.disableLighting();
      GL11.glDepthMask(false);
      if (this.facing == null) {
         GlStateManager.rotate(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
         GlStateManager.rotate((renderManager.options.thirdPersonView == 2 ? -1 : 1) * renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
         GlStateManager.rotate(rotation, 0.0F, 0.0F, 1.0F);
      } else {
         if (this.facing == EnumFacing.EAST) {
            GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
         }

         if (this.facing == EnumFacing.WEST) {
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
         }

         if (this.facing == EnumFacing.NORTH) {
            GlStateManager.rotate(0.0F, 0.0F, 1.0F, 0.0F);
         }

         if (this.facing == EnumFacing.SOUTH) {
            GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
         }

         if (this.facing == EnumFacing.DOWN) {
            GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(rotation, 0.0F, 0.0F, 1.0F);
         }

         if (this.facing == EnumFacing.UP) {
            GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(rotation, 0.0F, 0.0F, 1.0F);
         }
      }

      GL11.glEnable(3042);
      GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
      renderManager.renderEngine.bindTexture(tex);
      GlStateManager.scale(size, size, size);
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
      GL11.glDisable(2896);
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
      bufferbuilder.pos(-1.0, -1.0, 0.0).tex(1.0, 1.0).endVertex();
      bufferbuilder.pos(-1.0, 1.0, 0.0).tex(1.0, 0.0).endVertex();
      bufferbuilder.pos(1.0, 1.0, 0.0).tex(0.0, 0.0).endVertex();
      bufferbuilder.pos(1.0, -1.0, 0.0).tex(0.0, 1.0).endVertex();
      tessellator.draw();
      GlStateManager.disableRescaleNormal();
      GL11.glDisable(3042);
      GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      GL11.glEnable(2896);
      GL11.glDepthMask(true);
      GlStateManager.popMatrix();
   }

   public void spawnParticles1(boolean moveToOrigin, float r, float g, float b, int count) {
      ParticleTracker<TrailParticle> tracker = moveToOrigin
         ? new ParticleTracker.TrackerFollowStaticPoint(
            new Vec3d(this.origin.getX() + 0.5, this.origin.getY() + 0.5, this.origin.getZ() + 0.5), true, 0.4F, 0.004F, 0.1F
         )
         : null;

      for (int i = 0; i < count; i++) {
         int lt = moveToOrigin ? (int)Math.min(Math.sqrt(this.getDistanceSq(this.origin)) * 10.0, 80.0) : 20 + this.rand.nextInt(20);
         float scl = 0.075F + this.rand.nextFloat() * 0.05F;
         GUNParticle spelll = new GUNParticle(
            texStar,
            scl,
            0.0F,
            lt,
            240,
            this.world,
            this.posX + (this.rand.nextFloat() - 0.5),
            this.posY + (this.rand.nextFloat() - 0.5),
            this.posZ + (this.rand.nextFloat() - 0.5),
            0.0F,
            moveToOrigin ? 0.15F : 0.0F,
            0.0F,
            r,
            g,
            b,
            true,
            0
         );
         spelll.scaleTickAdding = scl * -0.8F / lt;
         spelll.alphaGlowing = true;
         if (moveToOrigin) {
            spelll.tracker = tracker;
         }

         this.world.spawnEntity(spelll);
      }
   }

   public void spawnParticlesLink(Vec3d from, Vec3d to, int count) {
      Vec3d fromto = new Vec3d(to.x - from.x, to.y - from.y, to.z - from.z)
         .scale(1.0 / count);

      for (int i = 1; i <= 40; i++) {
         Vec3d vec = from.add(fromto.scale(i));
         int lt = 40;
         MagicSubparticle part = new MagicSubparticle(
            vec.x,
            vec.y,
            vec.z,
            lt,
            0.0F,
            0.0F,
            0.0F,
            0.6F,
            0.4F,
            1.0F,
            0.6F + this.rand.nextFloat() * 0.4F,
            0.4F + this.rand.nextFloat() * 0.4F,
            1.0F - this.rand.nextFloat() * 0.4F
         );
         MagicSubparticle.particles.add(part);
      }
   }

   public void spawnParticlesUnlink(Vec3d from, Vec3d to, int count) {
      Vec3d fromto = new Vec3d(to.x - from.x, to.y - from.y, to.z - from.z)
         .scale(1.0 / count);

      for (int i = 1; i <= count; i++) {
         Vec3d vec = from.add(fromto.scale(i));
         int lt = 20;
         float scl = 0.035F + this.rand.nextFloat() * 0.01F;
         GUNParticle spelll = new GUNParticle(
            texStar,
            scl,
            0.0F,
            lt,
            240,
            this.world,
            vec.x,
            vec.y,
            vec.z,
            (float)this.rand.nextGaussian() / 24.0F,
            (float)this.rand.nextGaussian() / 24.0F,
            (float)this.rand.nextGaussian() / 24.0F,
            0.9F,
            0.2F,
            0.9F,
            true,
            0
         );
         spelll.scaleTickAdding = scl * -0.8F / lt;
         spelll.alphaGlowing = true;
         this.world.spawnEntity(spelll);
      }
   }

   public static class EntityMUIAnd extends EntityMagicUI {
      public static ResourceLocation texMap = new ResourceLocation("arpg:textures/mui/and.png");

      public EntityMUIAnd(World worldIn) {
         super(worldIn);
      }

      public EntityMUIAnd(World worldIn, BlockPos origin) {
         super(worldIn, origin);
      }

      @Override
      public Object getValue(EntityMagicUI entityBy, Entity activatingEntity, ManaProvider mana) {
         if (mana.getMana() >= 1.0F) {
            mana.addMana(-1.0F);

            for (EntityMagicUI emui : this.linkedEntitymuis) {
               if (emui != entityBy) {
                  Object objct = emui.getValue(this, activatingEntity, mana);
                  if (objct instanceof Boolean && !(Boolean)objct) {
                     return false;
                  }
               }
            }
         }

         return true;
      }

      @Override
      public void renderAsEntity(double x, double y, double z, float entityYaw, float partialTicks, RenderManager renderManager) {
         if (this.inSelection) {
            this.drawSelection(x, y, z, entityYaw, partialTicks, renderManager, 0.8F, 0.7F, 0.7F, 0.28125F, texMapSelection2);
         }

         float etsc = Math.min(this.ticksExisted + partialTicks, 10.0F) * 0.1F - (this.removeTick + Math.min(partialTicks, this.removeTick)) * 0.1F;
         this.drawSpriteRune(texMap, this.rotationOnFloor, x, y + this.getYOffset(), z, partialTicks, renderManager, etsc * 0.03125F * 5.0F);
      }
   }

   public static class EntityMUIBeing extends EntityMagicUI implements MUIParameters {
      public static ResourceLocation texMap = new ResourceLocation("arpg:textures/mui/being.png");
      public List<MUIParameters> parametersLinked = new ArrayList<>();

      public EntityMUIBeing(World worldIn) {
         super(worldIn);
      }

      public EntityMUIBeing(World worldIn, BlockPos origin) {
         super(worldIn, origin);
      }

      @Override
      public Object getValue(EntityMagicUI entityBy, Entity activatingEntity, ManaProvider mana) {
         if (mana.getMana() >= 1.0F) {
            mana.addMana(-1.0F);
            this.updateModifiers(entityBy, activatingEntity, mana);
            return this;
         } else {
            return super.getValue(entityBy, activatingEntity, mana);
         }
      }

      public void updateModifiers(EntityMagicUI entityBy, Entity activatingEntity, ManaProvider mana) {
         this.parametersLinked.clear();

         for (EntityMagicUI emui : this.linkedEntitymuis) {
            if (emui != entityBy) {
               Object objct = emui.getValue(this, activatingEntity, mana);
               if (objct instanceof MUIParameters) {
                  MUIParameters magicParams = (MUIParameters)objct;
                  this.parametersLinked.add(magicParams);
               }
            }
         }
      }

      @Override
      public float modifyPower(EntityMagicUI muiExecutor, EntityMagicUI muiModificator, float power, Entity activatingEntity, ManaProvider mana) {
         for (MUIParameters magicParams : this.parametersLinked) {
            power = magicParams.modifyPower(muiExecutor, muiModificator, power, activatingEntity, mana);
         }

         return power;
      }

      @Override
      public float modifyRange(EntityMagicUI muiExecutor, EntityMagicUI muiModificator, float range, Entity activatingEntity, ManaProvider mana) {
         for (MUIParameters magicParams : this.parametersLinked) {
            range = magicParams.modifyRange(muiExecutor, muiModificator, range, activatingEntity, mana);
         }

         return range;
      }

      @Override
      public Vec3d modifyFlowVector(EntityMagicUI muiExecutor, EntityMagicUI muiModificator, Vec3d flow, Entity activatingEntity, ManaProvider mana) {
         for (MUIParameters magicParams : this.parametersLinked) {
            flow = magicParams.modifyFlowVector(muiExecutor, muiModificator, flow, activatingEntity, mana);
         }

         return flow;
      }

      @Override
      public float modifyChaos(EntityMagicUI muiExecutor, EntityMagicUI muiModificator, float chaos, Entity activatingEntity, ManaProvider mana) {
         for (MUIParameters magicParams : this.parametersLinked) {
            chaos = magicParams.modifyChaos(muiExecutor, muiModificator, chaos, activatingEntity, mana);
         }

         return chaos;
      }

      @Override
      public boolean modifyMight(EntityMagicUI muiExecutor, EntityMagicUI muiModificator, boolean might, Entity activatingEntity, ManaProvider mana) {
         for (MUIParameters magicParams : this.parametersLinked) {
            might = magicParams.modifyMight(muiExecutor, muiModificator, might, activatingEntity, mana);
         }

         return might;
      }

      @Override
      public void modifyEvil(
         EntityMagicUI muiExecutor,
         EntityMagicUI muiModificator,
         float power,
         float range,
         Vec3d flow,
         float chaos,
         boolean might,
         Entity activatingEntity,
         ManaProvider mana,
         float manaSpended
      ) {
         for (MUIParameters magicParams : this.parametersLinked) {
            magicParams.modifyEvil(muiExecutor, muiModificator, power, range, flow, chaos, might, activatingEntity, mana, manaSpended);
         }
      }

      @Override
      public void renderAsEntity(double x, double y, double z, float entityYaw, float partialTicks, RenderManager renderManager) {
         if (this.inSelection) {
            this.drawSelection(x, y, z, entityYaw, partialTicks, renderManager, 0.75F, 0.85F, 0.9F, 0.28125F, texMapSelection8);
         }

         float etsc = Math.min(this.ticksExisted + partialTicks, 10.0F) * 0.1F - (this.removeTick + Math.min(partialTicks, this.removeTick)) * 0.1F;
         this.drawSpriteRune(texMap, this.rotationOnFloor, x, y + this.getYOffset(), z, partialTicks, renderManager, etsc * 0.03125F * 5.0F);
      }
   }

   public static class EntityMUIBig extends EntityMagicUI implements MUIParameters {
      public static ResourceLocation texMap = new ResourceLocation("arpg:textures/mui/big.png");

      public EntityMUIBig(World worldIn) {
         super(worldIn);
      }

      public EntityMUIBig(World worldIn, BlockPos origin) {
         super(worldIn, origin);
      }

      @Override
      public Object getValue(EntityMagicUI entityBy, Entity activatingEntity, ManaProvider mana) {
         if (mana.getMana() >= 2.0F) {
            mana.addMana(-2.0F);
            return this;
         } else {
            return super.getValue(entityBy, activatingEntity, mana);
         }
      }

      @Override
      public float modifyRange(EntityMagicUI muiExecutor, EntityMagicUI muiModificator, float range, Entity activatingEntity, ManaProvider mana) {
         return (range + 1.0F) * 1.2F;
      }

      @Override
      public float modifyChaos(EntityMagicUI muiExecutor, EntityMagicUI muiModificator, float chaos, Entity activatingEntity, ManaProvider mana) {
         return Math.max(chaos - 0.1F, 0.0F);
      }

      @Override
      public void renderAsEntity(double x, double y, double z, float entityYaw, float partialTicks, RenderManager renderManager) {
         if (this.inSelection) {
            this.drawSelection(x, y, z, entityYaw, partialTicks, renderManager, 0.75F, 0.16F, 0.0F, 0.28125F, texMapSelection8);
         }

         float etsc = Math.min(this.ticksExisted + partialTicks, 10.0F) * 0.1F - (this.removeTick + Math.min(partialTicks, this.removeTick)) * 0.1F;
         this.drawSpriteRune(texMap, this.rotationOnFloor, x, y + this.getYOffset(), z, partialTicks, renderManager, etsc * 0.03125F * 5.0F);
      }
   }

   public static class EntityMUICapacitor extends EntityMagicUI {
      public static ResourceLocation texMap = new ResourceLocation("arpg:textures/mui/capacitor.png");
      public float manaGets = 10.0F;
      public float manaToCast = 10.0F;
      public float manaStored = 0.0F;

      public EntityMUICapacitor(World worldIn) {
         super(worldIn);
      }

      public EntityMUICapacitor(World worldIn, BlockPos origin) {
         super(worldIn, origin);
      }

      @Override
      public Object getValue(EntityMagicUI entityBy, Entity activatingEntity, ManaProvider mana) {
         return this.manaStored;
      }

      @Override
      public void perform(EntityMagicUI entityBy, Entity activatingEntity, ManaProvider mana) {
         if (this.manaStored < this.manaToCast) {
            float m = Math.min(Math.min(mana.getMana(), this.manaGets), this.manaToCast - this.manaStored);
            mana.addMana(-m);
            this.manaStored += m;
         }

         super.perform(entityBy, activatingEntity, mana);
      }

      @Override
      public void cast(EntityMagicUI entityBy, Entity activatingEntity, ManaProvider mana) {
         System.out.println(this.manaStored);
         if (this.manaStored >= this.manaToCast) {
            mana.addMana(this.manaStored - 1.0F);
            this.manaStored = 0.0F;
            super.cast(entityBy, activatingEntity, mana);
         }
      }

      @Override
      public void renderAsEntity(double x, double y, double z, float entityYaw, float partialTicks, RenderManager renderManager) {
         if (this.inSelection) {
            this.drawSelection(x, y, z, entityYaw, partialTicks, renderManager, 0.3F, 0.3F, 0.9F, 0.28125F, texMapSelection9);
         }

         float etsc = Math.min(this.ticksExisted + partialTicks, 10.0F) * 0.1F - (this.removeTick + Math.min(partialTicks, this.removeTick)) * 0.1F;
         this.drawSpriteRune(texMap, this.rotationOnFloor, x, y + this.getYOffset(), z, partialTicks, renderManager, etsc * 0.03125F * 5.0F);
      }
   }

   public static class EntityMUICloser extends EntityMagicUI {
      public static ResourceLocation texMap = new ResourceLocation("arpg:textures/seals/closer.png");
      public List<EntityMagicUI> list;
      public boolean shouldRenderDecor = true;
      public IMagicUI mui;

      public EntityMUICloser(World worldIn) {
         super(worldIn);
      }

      public EntityMUICloser(World worldIn, BlockPos origin) {
         super(worldIn, origin);
      }

      public EntityMUICloser addALL(EntityMagicUI... entities) {
         if (this.list == null) {
            this.list = new ArrayList<>();
         }

         Collections.addAll(this.list, entities);
         return this;
      }

      @Override
      public void onUpdate() {
         super.onUpdate();
         if (this.origin != null) {
            this.flyAround(0.8F, 0.8F, 0.65F);
         }
      }

      @Override
      public void renderAsEntity(double x, double y, double z, float entityYaw, float partialTicks, RenderManager renderManager) {
         if (this.shouldRenderDecor && this.origin != null) {
            if (this.mui == null) {
               Block block = this.world.getBlockState(this.origin).getBlock();
               if (block instanceof IMagicUI) {
                  this.mui = (IMagicUI)block;
               } else {
                  TileEntity tile = this.world.getTileEntity(this.origin);
                  if (tile instanceof IMagicUI) {
                     this.mui = (IMagicUI)tile;
                  }
               }
            } else {
               double x1 = this.posX - this.origin.getX() - 0.5;
               double y1 = this.posY - this.origin.getY() - 0.5;
               double z1 = this.posZ - this.origin.getZ() - 0.5;
               this.mui.renderDecoratives(x - x1, y - y1, z - z1, this.ticksExisted, this.removeTick, partialTicks, renderManager);
            }
         }

         if (this.inSelection) {
         }

         int time = AnimationTimer.tick;
         GlStateManager.pushMatrix();
         GlStateManager.translate((float)x, (float)y + this.getYOffset(), (float)z);
         GlStateManager.enableRescaleNormal();
         GlStateManager.disableCull();
         GlStateManager.rotate(0.8F * (this.ticksExisted + partialTicks) + this.displace, 0.0F, 1.0F, 0.0F);
         GL11.glEnable(3042);
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
         renderManager.renderEngine.bindTexture(texMap);
         GL11.glDepthMask(false);
         float etsc = Math.min(this.ticksExisted + partialTicks, 10.0F) * 0.04F - (this.removeTick + Math.min(partialTicks, this.removeTick)) * 0.04F;
         GlStateManager.scale(etsc, etsc, etsc);
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
         GL11.glDisable(2896);
         Tessellator tessellator = Tessellator.getInstance();
         BufferBuilder bufferbuilder = tessellator.getBuffer();
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
         bufferbuilder.pos(-1.0, -1.0, 0.0).tex(0.0, 1.0).endVertex();
         bufferbuilder.pos(-1.0, 1.0, 0.0).tex(0.0, 0.0).endVertex();
         bufferbuilder.pos(1.0, 1.0, 0.0).tex(1.0, 0.0).endVertex();
         bufferbuilder.pos(1.0, -1.0, 0.0).tex(1.0, 1.0).endVertex();
         tessellator.draw();
         GlStateManager.disableRescaleNormal();
         GL11.glDisable(3042);
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         GL11.glEnable(2896);
         GL11.glDepthMask(true);
         GlStateManager.enableCull();
         GlStateManager.popMatrix();
      }

      @Override
      public boolean onPressTick(EntityPlayer player) {
         if (this.ticksExisted > 15) {
            if (!this.removing) {
               this.playSound(Sounds.mui_close, 0.8F, 0.85F + this.rand.nextFloat() * 0.3F);
            }

            this.setRemoved();
         }

         return true;
      }

      @Override
      public void setRemoved() {
         super.setRemoved();
         if (this.list != null && !this.world.isRemote && !this.list.isEmpty()) {
            for (EntityMagicUI entity : this.list) {
               entity.setRemoved();
            }
         }
      }
   }

   public static class EntityMUIDealMana extends EntityMagicUI {
      public static ResourceLocation[] texMaps = new ResourceLocation[]{
         new ResourceLocation("arpg:textures/seals/mana1.png"),
         new ResourceLocation("arpg:textures/seals/mana2.png"),
         new ResourceLocation("arpg:textures/seals/mana3.png")
      };
      public float amount = 1.0F;
      public float lastPlayerAngle = -999.0F;

      public EntityMUIDealMana(World worldIn) {
         super(worldIn);
      }

      public EntityMUIDealMana(World worldIn, BlockPos origin, float amount) {
         super(worldIn, origin);
         this.amount = amount;
      }

      @Override
      public boolean onPressTick(EntityPlayer player) {
         if (this.origin != null && !this.world.isRemote) {
            Block block = this.world.getBlockState(this.origin).getBlock();
            if (block instanceof IMagicUI) {
               ((IMagicUI)block).acceptMana(this.world, player, this.amount, this.origin, null);
               return true;
            }

            TileEntity tile = this.world.getTileEntity(this.origin);
            if (tile instanceof IMagicUI) {
               ((IMagicUI)tile).acceptMana(this.world, player, this.amount, this.origin, null);
               return true;
            }
         }

         return false;
      }

      @Override
      public void onUpdate() {
         super.onUpdate();
         if (this.origin != null) {
            this.flyAround(0.8F, 0.8F, 0.65F);
         }
      }

      @Override
      public void onDataSend(double anyVarToSend) {
         super.onDataSend(0.0);
         this.world.setEntityState(this, (byte)(this.amount < 1.0F ? 5 : (this.amount > 2.0F ? 7 : 6)));
      }

      @SideOnly(Side.CLIENT)
      public void handleStatusUpdate(byte id) {
         if (id == 5) {
            this.amount = 0.2F;
         }

         if (id == 6) {
            this.amount = 1.0F;
         }

         if (id == 7) {
            this.amount = 3.0F;
         }
      }

      @Override
      public void renderAsEntity(double x, double y, double z, float entityYaw, float partialTicks, RenderManager renderManager) {
         if (this.inSelection) {
         }

         int time = AnimationTimer.tick;
         GlStateManager.pushMatrix();
         GlStateManager.translate((float)x, (float)y + this.getYOffset(), (float)z);
         GlStateManager.enableRescaleNormal();
         GlStateManager.disableCull();
         GlStateManager.rotate(0.8F * (this.ticksExisted + partialTicks) + this.displace, 0.0F, 1.0F, 0.0F);
         GL11.glEnable(3042);
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
         renderManager.renderEngine.bindTexture(texMaps[this.amount < 1.0F ? 0 : (this.amount > 2.0F ? 2 : 1)]);
         GL11.glDepthMask(false);
         float etsc = Math.min(this.ticksExisted + partialTicks, 10.0F) * 0.04F - (this.removeTick + Math.min(partialTicks, this.removeTick)) * 0.04F;
         GlStateManager.scale(etsc, etsc, etsc);
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
         GL11.glDisable(2896);
         Tessellator tessellator = Tessellator.getInstance();
         BufferBuilder bufferbuilder = tessellator.getBuffer();
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
         bufferbuilder.pos(-1.0, -1.0, 0.0).tex(0.0, 1.0).endVertex();
         bufferbuilder.pos(-1.0, 1.0, 0.0).tex(0.0, 0.0).endVertex();
         bufferbuilder.pos(1.0, 1.0, 0.0).tex(1.0, 0.0).endVertex();
         bufferbuilder.pos(1.0, -1.0, 0.0).tex(1.0, 1.0).endVertex();
         tessellator.draw();
         GlStateManager.disableRescaleNormal();
         GL11.glDisable(3042);
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         GL11.glEnable(2896);
         GL11.glDepthMask(true);
         GlStateManager.enableCull();
         GlStateManager.popMatrix();
      }
   }

   public static class EntityMUIElementEnergy extends EntityMagicUI {
      public ShardType type;
      public float amount;
      public int pressTick = 0;
      public boolean gettingTex = false;
      public static ResourceLocation texG = new ResourceLocation("arpg:textures/seals/get_energy.png");

      public EntityMUIElementEnergy(World worldIn) {
         super(worldIn);
      }

      public EntityMUIElementEnergy(World worldIn, BlockPos origin, ShardType type, float amountMultipl) {
         super(worldIn, origin);
         this.type = type;
         this.amount = amountMultipl;
      }

      @Override
      public void onClient(double x, double y, double z, double a, double b, double c) {
         if (a == 0.0) {
            if (c >= 0.0) {
               this.type = ShardType.byId((int)c);
            } else {
               this.gettingTex = true;
            }
         }

         super.onClient(x, y, z, a, b, c);
      }

      @Override
      public boolean onPressTick(EntityPlayer player) {
         if (this.origin != null && !this.world.isRemote && this.ticksExisted > 10 && this.type != null) {
            IMagicUI mui = getMUIinPos(this.world, this.origin);
            mui.acceptElementEnergy(this.world, player, this.type, this.pressTick / 25.0F * this.amount, this.origin, null);
            if (this.pressTick < 50) {
               this.pressTick += 2;
            }
         }

         return false;
      }

      @Override
      public void onUpdate() {
         super.onUpdate();
         if (this.origin != null) {
            this.flyAround(0.8F, 0.8F, 0.65F);
         }

         if (this.pressTick > 0) {
            this.pressTick--;
         }
      }

      @Override
      public void onDataSend(double anyVarToSend) {
         if (this.amount >= 0.0F) {
            if (this.type != null) {
               super.onDataSend(this.type.id);
            }
         } else {
            super.onDataSend(-1.0);
         }
      }

      @Override
      public void renderAsEntity(double x, double y, double z, float entityYaw, float partialTicks, RenderManager renderManager) {
         if (this.type != null || this.gettingTex) {
            if (this.inSelection) {
            }

            int time = AnimationTimer.tick;
            GlStateManager.pushMatrix();
            GlStateManager.translate((float)x, (float)y + this.getYOffset(), (float)z);
            GlStateManager.enableRescaleNormal();
            GlStateManager.disableCull();
            GlStateManager.rotate(0.8F * (this.ticksExisted + partialTicks) + this.displace, 0.0F, 1.0F, 0.0F);
            GL11.glEnable(3042);
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
            renderManager.renderEngine.bindTexture(this.gettingTex ? texG : this.type.textureMui);
            GL11.glDepthMask(false);
            float etsc = Math.min(this.ticksExisted + partialTicks, 10.0F) * 0.04F - (this.removeTick + Math.min(partialTicks, this.removeTick)) * 0.04F;
            GlStateManager.scale(etsc, etsc, etsc);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
            GL11.glDisable(2896);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
            bufferbuilder.pos(-1.0, -1.0, 0.0).tex(0.0, 1.0).endVertex();
            bufferbuilder.pos(-1.0, 1.0, 0.0).tex(0.0, 0.0).endVertex();
            bufferbuilder.pos(1.0, 1.0, 0.0).tex(1.0, 0.0).endVertex();
            bufferbuilder.pos(1.0, -1.0, 0.0).tex(1.0, 1.0).endVertex();
            tessellator.draw();
            GlStateManager.disableRescaleNormal();
            GL11.glDisable(3042);
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
            GL11.glEnable(2896);
            GL11.glDepthMask(true);
            GlStateManager.enableCull();
            GlStateManager.popMatrix();
         }
      }
   }

   public static class EntityMUIEquals extends EntityMagicUI {
      public static ResourceLocation texMap = new ResourceLocation("arpg:textures/mui/equals.png");

      public EntityMUIEquals(World worldIn) {
         super(worldIn);
      }

      public EntityMUIEquals(World worldIn, BlockPos origin) {
         super(worldIn, origin);
      }

      @Override
      public Object getValue(EntityMagicUI entityBy, Entity activatingEntity, ManaProvider mana) {
         if (mana.getMana() >= 1.0F) {
            mana.addMana(-1.0F);
            if (this.linkedEntitymuis.size() > 0) {
               EntityMagicUI muiToCheck = this.linkedEntitymuis.get(0);
               Object muiToCheckObjct = muiToCheck.getValue(this, activatingEntity, mana);

               for (EntityMagicUI emui : this.linkedEntitymuis) {
                  if (emui != entityBy) {
                     Object objct = emui.getValue(this, activatingEntity, mana);
                     if (objct != muiToCheckObjct && objct != null && !objct.equals(muiToCheckObjct)) {
                        return false;
                     }
                  }
               }

               return true;
            }
         }

         return false;
      }

      @Override
      public void renderAsEntity(double x, double y, double z, float entityYaw, float partialTicks, RenderManager renderManager) {
         if (this.inSelection) {
            this.drawSelection(x, y, z, entityYaw, partialTicks, renderManager, 0.6F, 0.8F, 0.65F, 0.28125F, texMapSelection5);
         }

         float etsc = Math.min(this.ticksExisted + partialTicks, 10.0F) * 0.1F - (this.removeTick + Math.min(partialTicks, this.removeTick)) * 0.1F;
         this.drawSpriteRune(texMap, this.rotationOnFloor, x, y + this.getYOffset(), z, partialTicks, renderManager, etsc * 0.03125F * 5.0F);
      }
   }

   public static class EntityMUIGain extends EntityMagicUI {
      public static ResourceLocation texMap = new ResourceLocation("arpg:textures/mui/gain.png");
      public float manaGainMax = 50.0F;
      public List<EntityMagicUI> blocked = new ArrayList<>();

      public EntityMUIGain(World worldIn) {
         super(worldIn);
      }

      public EntityMUIGain(World worldIn, BlockPos origin) {
         super(worldIn, origin);
      }

      @Override
      public Object getValue(EntityMagicUI entityBy, Entity activatingEntity, ManaProvider mana) {
         return this.manaGainMax;
      }

      @Override
      public void perform(EntityMagicUI entityBy, Entity activatingEntity, ManaProvider mana) {
         this.blocked.clear();
         float manaGained = 0.0F;

         for (EntityMagicUI emui : this.linkedEntitymuis) {
            if (emui != entityBy) {
               Object objct = emui.getValue(this, activatingEntity, mana);
               if (objct instanceof IManaBuffer) {
                  IManaBuffer manabuffer = (IManaBuffer)objct;
                  this.blocked.add(emui);
                  ManaProvider tempMP = new ManaProvider.ManaProviderBlock(manabuffer);
                  float toadd = Math.min(tempMP.getMana(), this.manaGainMax - manaGained);
                  manaGained += toadd;
                  tempMP.addMana(-toadd);
                  mana.addMana(toadd);
                  if (manaGained >= this.manaGainMax) {
                     break;
                  }
               }
            }
         }

         super.perform(entityBy, activatingEntity, mana);
      }

      @Override
      public void cast(EntityMagicUI entityBy, Entity activatingEntity, ManaProvider mana) {
         System.out.println("casted: " + this.getClass().getName() + " | mana: " + mana.getMana());
         if (mana.getMana() >= 1.0F) {
            mana.addMana(-1.0F);

            for (EntityMagicUI emui : this.linkedEntitymuis) {
               if (emui != entityBy && !this.blocked.contains(emui)) {
                  emui.perform(this, activatingEntity, mana);
               }
            }

            for (EntityMagicUI emuix : this.linkedEntitymuis) {
               if (emuix != entityBy && !this.blocked.contains(emuix)) {
                  emuix.cast(this, activatingEntity, mana);
               }
            }
         }
      }

      @Override
      public void renderAsEntity(double x, double y, double z, float entityYaw, float partialTicks, RenderManager renderManager) {
         if (this.inSelection) {
            this.drawSelection(x, y, z, entityYaw, partialTicks, renderManager, 0.03F, 0.7F, 0.76F, 0.28125F, texMapSelection9);
         }

         float etsc = Math.min(this.ticksExisted + partialTicks, 10.0F) * 0.1F - (this.removeTick + Math.min(partialTicks, this.removeTick)) * 0.1F;
         this.drawSpriteRune(texMap, this.rotationOnFloor, x, y + this.getYOffset(), z, partialTicks, renderManager, etsc * 0.03125F * 5.0F);
      }
   }

   public static class EntityMUIIf extends EntityMagicUI {
      public static ResourceLocation texMap = new ResourceLocation("arpg:textures/mui/if.png");

      public EntityMUIIf(World worldIn) {
         super(worldIn);
      }

      public EntityMUIIf(World worldIn, BlockPos origin) {
         super(worldIn, origin);
      }

      @Override
      public void cast(EntityMagicUI entityBy, Entity activatingEntity, ManaProvider mana) {
         if (mana.getMana() >= 1.0F) {
            mana.addMana(-0.5F);
            boolean is = false;

            for (EntityMagicUI emui : this.linkedEntitymuis) {
               if (emui != entityBy && !(emui instanceof EntityMUIThen)) {
                  Object objct = emui.getValue(this, activatingEntity, mana);
                  if (objct instanceof Boolean && (Boolean)objct) {
                     is = true;
                     break;
                  }
               }
            }

            if (is) {
               mana.addMana(-0.5F);

               for (EntityMagicUI emuix : this.linkedEntitymuis) {
                  if (emuix != entityBy && emuix instanceof EntityMUIThen) {
                     emuix.perform(this, activatingEntity, mana);
                     emuix.cast(this, activatingEntity, mana);
                  }
               }
            }
         }
      }

      @Override
      public void renderAsEntity(double x, double y, double z, float entityYaw, float partialTicks, RenderManager renderManager) {
         if (this.inSelection) {
            this.drawSelection(x, y, z, entityYaw, partialTicks, renderManager, 0.4F, 0.45F, 0.55F, 0.28125F, texMapSelection4);
         }

         float etsc = Math.min(this.ticksExisted + partialTicks, 10.0F) * 0.1F - (this.removeTick + Math.min(partialTicks, this.removeTick)) * 0.1F;
         this.drawSpriteRune(texMap, this.rotationOnFloor, x, y + this.getYOffset(), z, partialTicks, renderManager, etsc * 0.03125F * 5.0F);
      }
   }

   public static class EntityMUILink extends EntityMagicUI {
      public static ResourceLocation texMap = new ResourceLocation("arpg:textures/seals/link.png");
      public boolean active = false;
      public EntityPlayer player;
      public int returnTick = 0;

      public EntityMUILink(World worldIn) {
         super(worldIn);
      }

      public EntityMUILink(World worldIn, BlockPos origin) {
         super(worldIn, origin);
      }

      @Override
      public void renderAsEntity(double x, double y, double z, float entityYaw, float partialTicks, RenderManager renderManager) {
         if (this.inSelection) {
         }

         float etsc = Math.min(this.ticksExisted + partialTicks, 10.0F) * 0.04F - (this.removeTick + Math.min(partialTicks, this.removeTick)) * 0.04F;
         if (this.active) {
            this.drawSpriteRune(texMap, -1.5F * (this.ticksExisted + partialTicks), x, y + this.getYOffset(), z, partialTicks, renderManager, etsc);
         } else {
            drawDefaultRune(
               texMap, 0.8F * (this.ticksExisted + partialTicks) + this.displace, x, y + this.getYOffset(), z, partialTicks, renderManager, etsc
            );
         }
      }

      @Override
      public void onUpdate() {
         super.onUpdate();
         if (!this.world.isRemote) {
            if (this.active && this.player != null) {
               Vec3d vec3d = this.player.getPositionEyes(1.0F);
               Vec3d vec3d1 = this.player.getLook(1.0F);
               Vec3d vec3d2 = vec3d.add(vec3d1.x * 2.5, vec3d1.y * 2.5, vec3d1.z * 2.5);
               double dist = Math.sqrt(vec3d2.squareDistanceTo(this.posX, this.posY, this.posZ));
               if (dist > 0.1) {
                  this.motionX *= 0.6;
                  this.motionY *= 0.6;
                  this.motionZ *= 0.6;
                  SuperKnockback.applyMove(this, -0.06F - (float)((dist - 0.1) * 0.07), vec3d2.x, vec3d2.y, vec3d2.z);
                  this.velocityChanged = true;
               }

               if (this.ticksExisted % 3 == 0 && !this.world.isRemote && this.origin != null) {
                  BlockPos thispos = new BlockPos(this.posX, this.posY, this.posZ);
                  if (!thispos.equals(this.origin)) {
                     IMagicUI muiOnThisPos = getMUIinPos(this.world, thispos);
                     IMagicUI muiOnOrigin = getMUIinPos(this.world, this.origin);
                     if (muiOnThisPos != null && muiOnOrigin != null) {
                        muiOnThisPos.onAcceptLink(this.world, this.player, thispos, null, this.origin, null);
                        muiOnOrigin.onProvideLink(this.world, this.player, this.origin, null, thispos, null);
                        if (!this.removing) {
                           this.playSound(Sounds.mui_link_end, 0.8F, 0.9F + this.rand.nextFloat() * 0.2F);
                           this.world.setEntityState(this, (byte)7);
                        }

                        this.setRemoved();
                     }
                  }
               }

               if (this.ticksExisted % 10 == 0 && !this.removing) {
                  this.playSound(Sounds.mui_link, 0.6F, 0.95F + this.rand.nextFloat() * 0.1F);
               }
            } else if (this.origin != null) {
               this.flyAround(0.8F, 0.8F, 0.65F);
            }

            if (this.returnTick < 0) {
               this.returnTick++;
            }
         } else if (this.active && this.ticksExisted % 3 == 0) {
            this.spawnParticles1(true, 0.6F, 0.15F, 1.0F, 3);
         }
      }

      @Override
      public void onDataSend(double anyVarToSend) {
         super.onDataSend(0.0);
         if (this.active) {
            this.world.setEntityState(this, (byte)5);
         } else {
            this.world.setEntityState(this, (byte)6);
         }
      }

      @SideOnly(Side.CLIENT)
      public void handleStatusUpdate(byte id) {
         if (id == 5) {
            this.active = true;
         }

         if (id == 6) {
            this.active = false;
         }

         if (id == 7) {
            this.spawnParticles1(false, 0.6F, 0.15F, 1.0F, 15);
         }
      }

      @Override
      public boolean onPressTick(EntityPlayer player) {
         if (this.origin != null && !this.world.isRemote && this.ticksExisted > 10) {
            if (this.active) {
               this.returnTick++;
               if (this.returnTick > 40) {
                  this.active = false;
                  this.world.setEntityState(this, (byte)6);
                  this.returnTick = -40;
                  this.playSound(Sounds.mui_hit, 0.8F, 0.85F + this.rand.nextFloat() * 0.3F);
               }
            } else if (this.returnTick >= 0) {
               this.active = true;
               this.player = player;
               this.world.setEntityState(this, (byte)5);
               this.playSound(Sounds.mui_link_start, 0.8F, 0.85F + this.rand.nextFloat() * 0.3F);
            }
         }

         return false;
      }
   }

   public static class EntityMUILoop extends EntityMagicUI {
      public static ResourceLocation texMap = new ResourceLocation("arpg:textures/mui/loop.png");
      public int tickrate = 0;
      public ManaProvider lastManaSource = null;

      public EntityMUILoop(World worldIn) {
         super(worldIn);
      }

      public EntityMUILoop(World worldIn, BlockPos origin) {
         super(worldIn, origin);
      }

      @Override
      public void onUpdate() {
         super.onUpdate();
         if (this.tickrate != 0 && this.ticksExisted % this.tickrate == 0) {
            if (this.lastManaSource != null && !(this.lastManaSource.getMana() < 1.0F)) {
               this.perform(this, this, this.lastManaSource);
               this.cast(this, this, this.lastManaSource);
            } else {
               this.lastManaSource = null;

               for (EntityMagicUI emui : this.linkedEntitymuis) {
                  ManaProvider mp = getManaProviderFromMui(emui, this);
                  if (mp != null && mp.getMana() >= 1.0F) {
                     this.lastManaSource = mp;
                     break;
                  }
               }
            }
         }
      }

      @Override
      public void perform(EntityMagicUI entityBy, Entity activatingEntity, ManaProvider mana) {
         if (activatingEntity instanceof EntityPlayer) {
            this.tickrate = (int)Math.max(25.0F - Mana.getMagicPowerMax((EntityPlayer)activatingEntity) * 5.0F, 1.0F);
            this.lastManaSource = new ManaProvider.ManaProviderPlayer((EntityPlayer)activatingEntity);
         }
      }

      @Nullable
      public static ManaProvider getManaProviderFromMui(EntityMagicUI emui, EntityMagicUI entityBy) {
         Object objct = emui.getValue(entityBy, entityBy, ManaProvider.EMPTY);
         if (objct != null) {
            if (objct instanceof EntityPlayer) {
               return new ManaProvider.ManaProviderPlayer((EntityPlayer)objct);
            }

            if (objct instanceof IManaBuffer) {
               return new ManaProvider.ManaProviderBlock((IManaBuffer)objct);
            }
         }

         return null;
      }

      @Override
      public Object getValue(EntityMagicUI entityBy, Entity activatingEntity, ManaProvider mana) {
         return this.tickrate;
      }

      @Override
      public void renderAsEntity(double x, double y, double z, float entityYaw, float partialTicks, RenderManager renderManager) {
         if (this.inSelection) {
            this.drawSelection(x, y, z, entityYaw, partialTicks, renderManager, 0.4F, 1.0F, 0.8F, 0.28125F, texMapSelection7);
         }

         float etsc = Math.min(this.ticksExisted + partialTicks, 10.0F) * 0.1F - (this.removeTick + Math.min(partialTicks, this.removeTick)) * 0.1F;
         this.drawSpriteRune(texMap, this.rotationOnFloor, x, y + this.getYOffset(), z, partialTicks, renderManager, etsc * 0.03125F * 7.0F);
      }
   }

   public static class EntityMUIManaBuffer extends EntityMagicUI {
      public static ResourceLocation texMap = new ResourceLocation("arpg:textures/mui/manabuffer.png");

      public EntityMUIManaBuffer(World worldIn) {
         super(worldIn);
      }

      public EntityMUIManaBuffer(World worldIn, BlockPos origin) {
         super(worldIn, origin);
      }

      @Override
      public Object getValue(EntityMagicUI entityBy, Entity activatingEntity, ManaProvider mana) {
         if (this.origin != null) {
            TileEntity tile = this.world.getTileEntity(this.origin);
            if (tile instanceof IManaBuffer) {
               return (IManaBuffer)tile;
            }
         }

         return super.getValue(entityBy, activatingEntity, mana);
      }

      @Override
      public void perform(EntityMagicUI entityBy, Entity activatingEntity, ManaProvider mana) {
         if (this.origin != null) {
            float power = 5.0F;
            float range = 3.0F;
            float chaos = 1.0F;
            Vec3d flowVector = Vec3d.ZERO;
            boolean mighty = false;

            for (EntityMagicUI emui : this.linkedEntitymuis) {
               if (emui != entityBy) {
                  Object objct = emui.getValue(this, activatingEntity, mana);
                  if (objct instanceof MUIParameters) {
                     MUIParameters magicParams = (MUIParameters)objct;
                     power = magicParams.modifyPower(this, emui, power, activatingEntity, mana);
                     range = magicParams.modifyRange(this, emui, range, activatingEntity, mana);
                     chaos = magicParams.modifyChaos(this, emui, chaos, activatingEntity, mana);
                     flowVector = magicParams.modifyFlowVector(this, emui, flowVector, activatingEntity, mana);
                     mighty = magicParams.modifyMight(this, emui, mighty, activatingEntity, mana);
                  }
               }
            }

            float manacost = ((power * 2.5F + range) * (1.25F - chaos * 0.5F) + (float)flowVector.length()) * (1.0F + Debugger.floats[1]);
            float finalmanacost = Math.min(mana.getMana(), manacost);
            if (mana.getMana() >= finalmanacost) {
               mana.addMana(-finalmanacost);
               if (this.origin != null) {
                  TileEntity tile = this.world.getTileEntity(this.origin);
                  if (tile instanceof IManaBuffer) {
                     IManaBuffer var16 = (IManaBuffer)tile;
                  }
               }
            }
         }

         super.perform(entityBy, activatingEntity, mana);
      }

      @Override
      public void renderAsEntity(double x, double y, double z, float entityYaw, float partialTicks, RenderManager renderManager) {
         if (this.inSelection) {
            this.drawSelection(x, y, z, entityYaw, partialTicks, renderManager, 0.4F, 0.8F, 0.95F, 0.28125F, texMapSelection1);
         }

         float etsc = Math.min(this.ticksExisted + partialTicks, 10.0F) * 0.1F - (this.removeTick + Math.min(partialTicks, this.removeTick)) * 0.1F;
         this.drawSpriteRune(texMap, this.rotationOnFloor, x, y + this.getYOffset(), z, partialTicks, renderManager, etsc * 0.03125F * 5.0F);
      }
   }

   public static class EntityMUIMirror extends EntityMagicUI {
      public static ResourceLocation texMap = new ResourceLocation("arpg:textures/mui/mirror.png");

      public EntityMUIMirror(World worldIn) {
         super(worldIn);
      }

      public EntityMUIMirror(World worldIn, BlockPos origin) {
         super(worldIn, origin);
      }

      @Override
      public void cast(EntityMagicUI entityBy, Entity activatingEntity, ManaProvider mana) {
         super.cast(entityBy, activatingEntity, mana);
      }

      @Override
      public void renderAsEntity(double x, double y, double z, float entityYaw, float partialTicks, RenderManager renderManager) {
         if (this.inSelection) {
            this.drawSelection(x, y, z, entityYaw, partialTicks, renderManager, 0.7F, 0.6F, 0.3F, 0.28125F, texMapSelection1);
         }

         float etsc = Math.min(this.ticksExisted + partialTicks, 10.0F) * 0.1F - (this.removeTick + Math.min(partialTicks, this.removeTick)) * 0.1F;
         this.drawSpriteRune(texMap, this.rotationOnFloor, x, y + this.getYOffset(), z, partialTicks, renderManager, etsc * 0.03125F * 5.0F);
      }
   }

   public static class EntityMUINext extends EntityMagicUI {
      public static ResourceLocation[] texMaps = new ResourceLocation[]{
         new ResourceLocation("arpg:textures/seals/energy_fire.png"),
         new ResourceLocation("arpg:textures/seals/energy_water.png"),
         new ResourceLocation("arpg:textures/seals/energy_air.png"),
         new ResourceLocation("arpg:textures/seals/energy_earth.png"),
         new ResourceLocation("arpg:textures/seals/energy_poison.png"),
         new ResourceLocation("arpg:textures/seals/energy_cold.png"),
         new ResourceLocation("arpg:textures/seals/add_energy.png")
      };
      public static Vec3d[] colorVects = new Vec3d[]{
         new Vec3d(1.0, 0.8, 0.5),
         new Vec3d(0.6, 0.7, 1.0),
         new Vec3d(0.97, 0.94, 1.0),
         new Vec3d(0.9, 0.8, 0.6),
         new Vec3d(0.5, 1.0, 0.4),
         new Vec3d(0.7, 0.95, 1.0),
         new Vec3d(1.0, 0.4, 0.7)
      };
      public List<EntityMagicUI> listNext;
      public List<EntityMagicUI> listToRemove;
      public int tex = 0;

      public EntityMUINext(World worldIn) {
         super(worldIn);
      }

      public EntityMUINext(World worldIn, BlockPos origin, int tex) {
         super(worldIn, origin);
         this.tex = tex;
      }

      public EntityMUINext addALLnext(EntityMagicUI... entities) {
         if (this.listNext == null) {
            this.listNext = new ArrayList<>();
         }

         Collections.addAll(this.listNext, entities);
         return this;
      }

      public EntityMUINext addALLtoRemove(EntityMagicUI... entities) {
         if (this.listToRemove == null) {
            this.listToRemove = new ArrayList<>();
         }

         Collections.addAll(this.listToRemove, entities);
         return this;
      }

      @Override
      public void onClient(double x, double y, double z, double a, double b, double c) {
         if (a == 0.0) {
            this.tex = (int)c;
         }

         super.onClient(x, y, z, a, b, c);
      }

      @Override
      public void onDataSend(double anyVarToSend) {
         super.onDataSend(this.tex);
      }

      @Override
      public void onUpdate() {
         super.onUpdate();
         if (this.origin != null && !this.world.isRemote) {
            this.flyAround(0.8F, 0.8F, 0.65F);
         }
      }

      @Override
      public void renderAsEntity(double x, double y, double z, float entityYaw, float partialTicks, RenderManager renderManager) {
         if (this.inSelection) {
            Vec3d time = colorVects[this.tex];
         }

         int time = AnimationTimer.tick;
         GlStateManager.pushMatrix();
         GlStateManager.translate((float)x, (float)y + this.getYOffset(), (float)z);
         GlStateManager.enableRescaleNormal();
         GlStateManager.disableCull();
         GlStateManager.rotate(0.8F * (this.ticksExisted + partialTicks) + this.displace, 0.0F, 1.0F, 0.0F);
         GL11.glEnable(3042);
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
         renderManager.renderEngine.bindTexture(texMaps[this.tex]);
         GL11.glDepthMask(false);
         float etsc = Math.min(this.ticksExisted + partialTicks, 10.0F) * 0.04F - (this.removeTick + Math.min(partialTicks, this.removeTick)) * 0.04F;
         GlStateManager.scale(etsc, etsc, etsc);
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
         GL11.glDisable(2896);
         Tessellator tessellator = Tessellator.getInstance();
         BufferBuilder bufferbuilder = tessellator.getBuffer();
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
         bufferbuilder.pos(-1.0, -1.0, 0.0).tex(0.0, 1.0).endVertex();
         bufferbuilder.pos(-1.0, 1.0, 0.0).tex(0.0, 0.0).endVertex();
         bufferbuilder.pos(1.0, 1.0, 0.0).tex(1.0, 0.0).endVertex();
         bufferbuilder.pos(1.0, -1.0, 0.0).tex(1.0, 1.0).endVertex();
         tessellator.draw();
         GlStateManager.disableRescaleNormal();
         GL11.glDisable(3042);
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         GL11.glEnable(2896);
         GL11.glDepthMask(true);
         GlStateManager.enableCull();
         GlStateManager.popMatrix();
      }

      @Override
      public boolean onPressTick(EntityPlayer player) {
         if (this.ticksExisted > 15 && !this.removing) {
            this.setRemoved();
            if (!this.world.isRemote && this.listNext != null && this.listToRemove != null) {
               IMagicUI.spawnEntityMUIinRound(this.world, player, this.origin, null, 0.1, 0.6F, this.listNext);

               for (EntityMagicUI entitymui : this.listToRemove) {
                  entitymui.setRemoved();
               }

               this.playSound(Sounds.mui_hit, 0.8F, 0.85F + this.rand.nextFloat() * 0.3F);
            }
         }

         return true;
      }
   }

   public static class EntityMUIOr extends EntityMagicUI {
      public static ResourceLocation texMap = new ResourceLocation("arpg:textures/mui/or.png");

      public EntityMUIOr(World worldIn) {
         super(worldIn);
      }

      public EntityMUIOr(World worldIn, BlockPos origin) {
         super(worldIn, origin);
      }

      @Override
      public Object getValue(EntityMagicUI entityBy, Entity activatingEntity, ManaProvider mana) {
         if (mana.getMana() >= 1.0F) {
            mana.addMana(-1.0F);

            for (EntityMagicUI emui : this.linkedEntitymuis) {
               if (emui != entityBy) {
                  Object objct = emui.getValue(this, activatingEntity, mana);
                  if (objct instanceof Boolean && (Boolean)objct) {
                     return true;
                  }
               }
            }
         }

         return false;
      }

      @Override
      public void renderAsEntity(double x, double y, double z, float entityYaw, float partialTicks, RenderManager renderManager) {
         if (this.inSelection) {
            this.drawSelection(x, y, z, entityYaw, partialTicks, renderManager, 0.6F, 0.8F, 0.74F, 0.28125F, texMapSelection3);
         }

         float etsc = Math.min(this.ticksExisted + partialTicks, 10.0F) * 0.1F - (this.removeTick + Math.min(partialTicks, this.removeTick)) * 0.1F;
         this.drawSpriteRune(texMap, this.rotationOnFloor, x, y + this.getYOffset(), z, partialTicks, renderManager, etsc * 0.03125F * 5.0F);
      }
   }

   public static class EntityMUIPowerful extends EntityMagicUI implements MUIParameters {
      public static ResourceLocation texMap = new ResourceLocation("arpg:textures/mui/powerful.png");

      public EntityMUIPowerful(World worldIn) {
         super(worldIn);
      }

      public EntityMUIPowerful(World worldIn, BlockPos origin) {
         super(worldIn, origin);
      }

      @Override
      public Object getValue(EntityMagicUI entityBy, Entity activatingEntity, ManaProvider mana) {
         if (mana.getMana() >= 2.5F) {
            mana.addMana(-2.5F);
            return this;
         } else {
            return super.getValue(entityBy, activatingEntity, mana);
         }
      }

      @Override
      public float modifyPower(EntityMagicUI muiExecutor, EntityMagicUI muiModificator, float power, Entity activatingEntity, ManaProvider mana) {
         return power + 1.3F;
      }

      @Override
      public float modifyRange(EntityMagicUI muiExecutor, EntityMagicUI muiModificator, float range, Entity activatingEntity, ManaProvider mana) {
         return range + 0.6F;
      }

      @Override
      public float modifyChaos(EntityMagicUI muiExecutor, EntityMagicUI muiModificator, float chaos, Entity activatingEntity, ManaProvider mana) {
         return Math.min(chaos + 0.15F, 1.0F);
      }

      @Override
      public void renderAsEntity(double x, double y, double z, float entityYaw, float partialTicks, RenderManager renderManager) {
         if (this.inSelection) {
            this.drawSelection(x, y, z, entityYaw, partialTicks, renderManager, 0.5F, 0.0F, 0.86F, 0.28125F, texMapSelection8);
         }

         float etsc = Math.min(this.ticksExisted + partialTicks, 10.0F) * 0.1F - (this.removeTick + Math.min(partialTicks, this.removeTick)) * 0.1F;
         this.drawSpriteRune(texMap, this.rotationOnFloor, x, y + this.getYOffset(), z, partialTicks, renderManager, etsc * 0.03125F * 5.0F);
      }
   }

   public static class EntityMUIRedstone extends EntityMagicUI {
      public static ResourceLocation texMap = new ResourceLocation("arpg:textures/mui/redstone.png");

      public EntityMUIRedstone(World worldIn) {
         super(worldIn);
      }

      public EntityMUIRedstone(World worldIn, BlockPos origin) {
         super(worldIn, origin);
      }

      @Override
      public Object getValue(EntityMagicUI entityBy, Entity activatingEntity, ManaProvider mana) {
         if (mana.getMana() >= 1.0F) {
            mana.addMana(-0.1F);
            if (this.origin != null) {
               return this.world.isBlockPowered(this.origin);
            }
         }

         return super.getValue(entityBy, activatingEntity, mana);
      }

      @Override
      public void perform(EntityMagicUI entityBy, Entity activatingEntity, ManaProvider mana) {
         if (mana.getMana() >= 5.0F && this.origin != null) {
            mana.addMana(-5.0F);
            IBlockState state = this.world.getBlockState(this.origin);
            if (state.canProvidePower()) {
               for (IProperty<?> pr : state.getPropertyKeys()) {
                  if ("powered".equals(pr.getName()) && pr instanceof PropertyBool) {
                     state = state.cycleProperty(pr);
                     this.world.setBlockState(this.origin, state, 3);
                     // FIX: Hardcode `f` variable
                     // float f = state.getValue(pr) ? 0.6F : 0.5F;
                     float f = 0.5F;
                     this.world.playSound((EntityPlayer)null, this.origin, SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.BLOCKS, 0.3F, f);
                     this.world.notifyNeighborsOfStateChange(this.origin, state.getBlock(), false);
                  }
               }
            }
         }

         super.perform(entityBy, activatingEntity, mana);
      }

      @Override
      public void renderAsEntity(double x, double y, double z, float entityYaw, float partialTicks, RenderManager renderManager) {
         if (this.inSelection) {
            this.drawSelection(x, y, z, entityYaw, partialTicks, renderManager, 1.0F, 0.0F, 0.0F, 0.28125F, texMapSelection7);
         }

         float etsc = Math.min(this.ticksExisted + partialTicks, 10.0F) * 0.1F - (this.removeTick + Math.min(partialTicks, this.removeTick)) * 0.1F;
         this.drawSpriteRune(texMap, this.rotationOnFloor, x, y + this.getYOffset(), z, partialTicks, renderManager, etsc * 0.03125F * 5.0F);
      }
   }

   public static class EntityMUISeal extends EntityMagicUI {
      public Seal seal;

      public EntityMUISeal(World worldIn) {
         super(worldIn);
      }

      public EntityMUISeal(World worldIn, BlockPos origin, Seal seal) {
         super(worldIn, origin);
         this.seal = seal;
      }

      @Override
      protected void writeEntityToNBT(NBTTagCompound compound) {
         if (this.seal != null) {
            super.writeEntityToNBT(compound);
            compound.setInteger("seal", this.seal.id);
         }
      }

      @Override
      protected void readEntityFromNBT(NBTTagCompound compound) {
         super.readEntityFromNBT(compound);
         if (compound.hasKey("seal")) {
            this.seal = Seal.byId(compound.getInteger("seal"));
         }
      }

      @Override
      public Object getValue(EntityMagicUI entityBy, Entity activatingEntity, ManaProvider mana) {
         return this.seal;
      }

      @Override
      public void onClient(double... args) {
         if (args.length == 1 && args[0] >= 6.0) {
            this.seal = Seal.byId((int)args[0] - 6);
         } else {
            super.onClient(args);
         }
      }

      @Override
      public void onUpdate() {
         super.onUpdate();
         if (!this.world.isRemote && this.seal != null && (this.ticksExisted < 2 || this.ticksExisted % 50 == 0)) {
            IEntitySynchronize.sendSynchronize(this, 64.0, this.seal.id + 6);
         }
      }

      @Override
      public void perform(EntityMagicUI entityBy, Entity activatingEntity, ManaProvider mana) {
         if (this.origin != null && this.seal != null) {
            float power = 2.0F;
            float range = 1.0F;
            float chaos = 1.0F;
            Vec3d flowVector = Vec3d.ZERO;
            boolean mighty = false;

            for (EntityMagicUI emui : this.linkedEntitymuis) {
               if (emui != entityBy) {
                  Object objct = emui.getValue(this, activatingEntity, mana);
                  if (objct instanceof MUIParameters) {
                     MUIParameters magicParams = (MUIParameters)objct;
                     power = magicParams.modifyPower(this, emui, power, activatingEntity, mana);
                     range = magicParams.modifyRange(this, emui, range, activatingEntity, mana);
                     chaos = magicParams.modifyChaos(this, emui, chaos, activatingEntity, mana);
                     flowVector = magicParams.modifyFlowVector(this, emui, flowVector, activatingEntity, mana);
                     mighty = magicParams.modifyMight(this, emui, mighty, activatingEntity, mana);
                  }
               }
            }

            float manacost = ((power * 2.5F + range) * (1.25F - chaos * 0.5F) + (float)flowVector.length()) * (1.0F + Debugger.floats[1]);
            if (mana.getMana() >= manacost) {
               mana.addMana(-manacost);
               System.out.println("power: " + power + " | range: " + range + " | chaos: " + chaos + " | flowVector: " + flowVector + " | mighty: " + mighty);
               if (this.seal
                  .evil(
                     this.world,
                     this.origin.getX() + 0.5,
                     this.origin.getY() + 0.5,
                     this.origin.getZ() + 0.5,
                     power,
                     range,
                     chaos,
                     flowVector,
                     mighty
                  )) {
                  for (EntityMagicUI emuix : this.linkedEntitymuis) {
                     if (emuix != entityBy) {
                        Object objct = emuix.getValue(this, activatingEntity, mana);
                        if (objct instanceof MUIParameters) {
                           MUIParameters magicParams = (MUIParameters)objct;
                           magicParams.modifyEvil(this, emuix, power, range, flowVector, chaos, mighty, activatingEntity, mana, manacost);
                        }
                     }
                  }
               }
            }
         }

         super.perform(entityBy, activatingEntity, mana);
      }

      @Override
      public void cast(EntityMagicUI entityBy, Entity activatingEntity, ManaProvider mana) {
      }

      @Override
      public void renderAsEntity(double x, double y, double z, float entityYaw, float partialTicks, RenderManager renderManager) {
         if (this.seal != null) {
            if (this.inSelection) {
               this.drawSelection(
                  x, y, z, entityYaw, partialTicks, renderManager, this.seal.colorR, this.seal.colorG, this.seal.colorB, 0.28125F, texMapSelection3
               );
            }

            float etsc = Math.min(this.ticksExisted + partialTicks, 10.0F) * 0.1F - (this.removeTick + Math.min(partialTicks, this.removeTick)) * 0.1F;
            this.drawSpriteRune(this.seal.texture, this.rotationOnFloor, x, y + this.getYOffset(), z, partialTicks, renderManager, etsc * 0.03125F * 7.0F);
         }
      }
   }

   public static class EntityMUISetSeal extends EntityMagicUI {
      public Seal seal;

      public EntityMUISetSeal(World worldIn) {
         super(worldIn);
      }

      public EntityMUISetSeal(World worldIn, BlockPos origin, Seal seal) {
         super(worldIn, origin);
         this.seal = seal;
      }

      @Override
      public void onClient(double x, double y, double z, double a, double b, double c) {
         if (a == 0.0) {
            this.seal = Seal.byId((int)c);
         }

         super.onClient(x, y, z, a, b, c);
      }

      @Override
      public boolean onPressTick(EntityPlayer player) {
         if (this.origin != null && !this.world.isRemote && this.ticksExisted > 10) {
            Block block = this.world.getBlockState(this.origin).getBlock();
            if (block instanceof IMagicUI) {
               if (!this.removing) {
                  this.playSound(Sounds.mui_hit, 0.8F, 0.85F + this.rand.nextFloat() * 0.3F);
               }

               ((IMagicUI)block).acceptSeal(this.world, player, this.seal, this.origin, null);
               this.setRemoved();
               return true;
            }

            TileEntity tile = this.world.getTileEntity(this.origin);
            if (tile instanceof IMagicUI) {
               if (!this.removing) {
                  this.playSound(Sounds.mui_hit, 0.8F, 0.85F + this.rand.nextFloat() * 0.3F);
               }

               ((IMagicUI)tile).acceptSeal(this.world, player, this.seal, this.origin, null);
               this.setRemoved();
               return true;
            }
         }

         return false;
      }

      @Override
      public void onUpdate() {
         super.onUpdate();
         if (this.origin != null) {
            this.flyAround(0.8F, 0.65F, 0.65F);
         }
      }

      @Override
      public void onDataSend(double anyVarToSend) {
         if (this.seal != null) {
            super.onDataSend(this.seal.id);
         }
      }

      @Override
      public void renderAsEntity(double x, double y, double z, float entityYaw, float partialTicks, RenderManager renderManager) {
         if (this.seal != null) {
            if (this.inSelection) {
            }

            int time = AnimationTimer.tick;
            GlStateManager.pushMatrix();
            GlStateManager.translate((float)x, (float)y + this.getYOffset(), (float)z);
            GlStateManager.enableRescaleNormal();
            GlStateManager.disableCull();
            GlStateManager.rotate(0.65F * (this.ticksExisted + partialTicks) + this.displace, 0.0F, 1.0F, 0.0F);
            GL11.glEnable(3042);
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
            renderManager.renderEngine.bindTexture(this.seal.texture);
            GL11.glDepthMask(false);
            float etsc = Math.min(this.ticksExisted + partialTicks, 10.0F) * 0.04F - (this.removeTick + Math.min(partialTicks, this.removeTick)) * 0.04F;
            GlStateManager.scale(etsc, etsc, etsc);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
            GL11.glDisable(2896);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
            bufferbuilder.pos(-1.0, -1.0, 0.0).tex(0.0, 1.0).endVertex();
            bufferbuilder.pos(-1.0, 1.0, 0.0).tex(0.0, 0.0).endVertex();
            bufferbuilder.pos(1.0, 1.0, 0.0).tex(1.0, 0.0).endVertex();
            bufferbuilder.pos(1.0, -1.0, 0.0).tex(1.0, 1.0).endVertex();
            tessellator.draw();
            GlStateManager.disableRescaleNormal();
            GL11.glDisable(3042);
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
            GL11.glEnable(2896);
            GL11.glDepthMask(true);
            GlStateManager.enableCull();
            GlStateManager.popMatrix();
         }
      }
   }

   public static class EntityMUISharp extends EntityMagicUI implements MUIParameters {
      public static ResourceLocation texMap = new ResourceLocation("arpg:textures/mui/sharp.png");

      public EntityMUISharp(World worldIn) {
         super(worldIn);
      }

      public EntityMUISharp(World worldIn, BlockPos origin) {
         super(worldIn, origin);
      }

      @Override
      public Object getValue(EntityMagicUI entityBy, Entity activatingEntity, ManaProvider mana) {
         if (mana.getMana() >= 1.0F) {
            mana.addMana(-1.0F);
            return this;
         } else {
            return super.getValue(entityBy, activatingEntity, mana);
         }
      }

      @Override
      public float modifyPower(EntityMagicUI muiExecutor, EntityMagicUI muiModificator, float power, Entity activatingEntity, ManaProvider mana) {
         return power + 1.0F;
      }

      @Override
      public float modifyRange(EntityMagicUI muiExecutor, EntityMagicUI muiModificator, float range, Entity activatingEntity, ManaProvider mana) {
         return Math.max(range - 0.4F, 0.0F);
      }

      @Override
      public float modifyChaos(EntityMagicUI muiExecutor, EntityMagicUI muiModificator, float chaos, Entity activatingEntity, ManaProvider mana) {
         return Math.max(chaos - 0.13F, 0.0F);
      }

      @Override
      public void renderAsEntity(double x, double y, double z, float entityYaw, float partialTicks, RenderManager renderManager) {
         if (this.inSelection) {
            this.drawSelection(x, y, z, entityYaw, partialTicks, renderManager, 0.1F, 0.9F, 0.0F, 0.28125F, texMapSelection8);
         }

         float etsc = Math.min(this.ticksExisted + partialTicks, 10.0F) * 0.1F - (this.removeTick + Math.min(partialTicks, this.removeTick)) * 0.1F;
         this.drawSpriteRune(texMap, this.rotationOnFloor, x, y + this.getYOffset(), z, partialTicks, renderManager, etsc * 0.03125F * 5.0F);
      }
   }

   public static class EntityMUISound extends EntityMagicUI {
      public static ResourceLocation texMap = new ResourceLocation("arpg:textures/mui/sound.png");
      public SoundEvent lastPlayedSound = null;

      public EntityMUISound(World worldIn) {
         super(worldIn);
      }

      public EntityMUISound(World worldIn, BlockPos origin) {
         super(worldIn, origin);
      }

      @Override
      protected void writeEntityToNBT(NBTTagCompound compound) {
         super.writeEntityToNBT(compound);
         if (this.lastPlayedSound != null) {
            compound.setInteger("lastSound", SoundEvent.REGISTRY.getIDForObject(this.lastPlayedSound));
         }
      }

      @Override
      protected void readEntityFromNBT(NBTTagCompound compound) {
         super.readEntityFromNBT(compound);
         if (compound.hasKey("lastSound")) {
            this.lastPlayedSound = (SoundEvent)SoundEvent.REGISTRY.getObjectById(compound.getInteger("lastSound"));
         }
      }

      @Override
      public Object getValue(EntityMagicUI entityBy, Entity activatingEntity, ManaProvider mana) {
         if (mana.getMana() >= 2.0F) {
            mana.addMana(-2.0F);
            return this.lastPlayedSound;
         } else {
            return Sounds.magic_c;
         }
      }

      @Override
      public void perform(EntityMagicUI entityBy, Entity activatingEntity, ManaProvider mana) {
         if (mana.getMana() >= 2.0F) {
            mana.addMana(-2.0F);
            boolean played = false;

            for (EntityMagicUI emui : this.linkedEntitymuis) {
               Object objct = emui.getValue(this, activatingEntity, mana);
               SoundEvent soundevent = null;
               if (objct instanceof SoundEvent) {
                  soundevent = (SoundEvent)objct;
                  played = true;
               } else if (objct instanceof Block) {
                  Block block = (Block)objct;
                  soundevent = block.getSoundType().getPlaceSound();
               } else if (objct instanceof String) {
                  String str = (String)objct;
                  SoundEvent event = (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(str));
                  if (event != null) {
                     soundevent = event;
                  }
               } else if (objct instanceof ResourceLocation) {
                  ResourceLocation res = (ResourceLocation)objct;
                  SoundEvent event = (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(res);
                  if (event != null) {
                     soundevent = event;
                  }
               }

               if (soundevent != null) {
                  this.world
                     .playSound(
                        (EntityPlayer)null,
                        this.posX,
                        this.posY,
                        this.posZ,
                        soundevent,
                        SoundCategory.BLOCKS,
                        1.0F,
                        0.9F + this.rand.nextFloat() / 5.0F
                     );
                  played = true;
                  this.lastPlayedSound = soundevent;
               }
            }

            if (!played) {
               this.world
                  .playSound(
                     (EntityPlayer)null,
                     this.posX,
                     this.posY,
                     this.posZ,
                     Sounds.magic_c,
                     SoundCategory.BLOCKS,
                     1.0F,
                     0.9F + this.rand.nextFloat() / 5.0F
                  );
               this.lastPlayedSound = Sounds.magic_c;
            }
         }

         super.perform(entityBy, activatingEntity, mana);
      }

      @Override
      public void renderAsEntity(double x, double y, double z, float entityYaw, float partialTicks, RenderManager renderManager) {
         if (this.inSelection) {
            this.drawSelection(x, y, z, entityYaw, partialTicks, renderManager, 0.5F, 0.5F, 0.8F, 0.28125F, texMapSelection3);
         }

         float etsc = Math.min(this.ticksExisted + partialTicks, 10.0F) * 0.1F - (this.removeTick + Math.min(partialTicks, this.removeTick)) * 0.1F;
         this.drawSpriteRune(texMap, this.rotationOnFloor, x, y + this.getYOffset(), z, partialTicks, renderManager, etsc * 0.03125F * 7.0F);
      }
   }

   public static class EntityMUIThen extends EntityMagicUI {
      public static ResourceLocation texMap = new ResourceLocation("arpg:textures/mui/then.png");

      public EntityMUIThen(World worldIn) {
         super(worldIn);
      }

      public EntityMUIThen(World worldIn, BlockPos origin) {
         super(worldIn, origin);
      }

      @Override
      public void renderAsEntity(double x, double y, double z, float entityYaw, float partialTicks, RenderManager renderManager) {
         if (this.inSelection) {
            this.drawSelection(x, y, z, entityYaw, partialTicks, renderManager, 0.4F, 0.45F, 0.55F, 0.28125F, texMapSelection4);
         }

         float etsc = Math.min(this.ticksExisted + partialTicks, 10.0F) * 0.1F - (this.removeTick + Math.min(partialTicks, this.removeTick)) * 0.1F;
         this.drawSpriteRune(texMap, this.rotationOnFloor, x, y + this.getYOffset(), z, partialTicks, renderManager, etsc * 0.03125F * 5.0F);
      }
   }
}
