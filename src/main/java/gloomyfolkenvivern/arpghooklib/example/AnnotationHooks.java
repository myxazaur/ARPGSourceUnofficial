//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package gloomyfolkenvivern.arpghooklib.example;

import com.Vivern.Arpg.arpgfix.AbstractClientFieldsContainer;
import com.Vivern.Arpg.blocks.AshBlock;
import com.Vivern.Arpg.elements.IWeapon;
import com.Vivern.Arpg.events.Debugger;
import com.Vivern.Arpg.main.AnimationTimer;
import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.ColorConverters;
import com.Vivern.Arpg.main.CreateItemFile;
import com.Vivern.Arpg.main.DeathEffects;
import com.Vivern.Arpg.main.ItemsElements;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.PropertiesRegistry;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.mobs.AbstractMob;
import com.Vivern.Arpg.network.IFixedTrackerEntity;
import com.Vivern.Arpg.network.MyEntityTrackerEntry;
import com.Vivern.Arpg.potions.AdvancedPotion;
import com.Vivern.Arpg.potions.Freezing;
import com.Vivern.Arpg.potions.PotionEffects;
import com.Vivern.Arpg.potions.Stun;
import com.Vivern.Arpg.renders.AmbientOcclusionFace;
import com.Vivern.Arpg.renders.LoadedRGBChunk;
import com.Vivern.Arpg.renders.ManaBar;
import com.Vivern.Arpg.renders.PlayerAnimation;
import com.Vivern.Arpg.renders.PlayerAnimations;
import com.Vivern.Arpg.renders.StaticRGBLight;
import com.google.common.collect.Ordering;
import gloomyfolkenvivern.arpghooklib.asm.Hook;
import gloomyfolkenvivern.arpghooklib.asm.ReturnCondition;
import gloomyfolkenvivern.arpghooklib.example.coloredlightning.ColoredLightning;
import java.lang.reflect.Field;
import java.lang.reflect.GenericSignatureFormatError;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockTNT;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.ITickableSound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemTransformVec3f;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.command.CommandEnchant;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityLookHelper;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketClientStatus;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketClientStatus.State;
import net.minecraft.network.play.server.SPacketCooldown;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.realms.RealmsBridge;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.model.pipeline.LightUtil;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import paulscode.sound.SoundSystem;

public class AnnotationHooks {
   public static List<BlockPos> listMarkRelightPoses = new ArrayList<>();

//   public static BlockColors blockColors = new BlockColors();
   public static Object blockColors;

   private static class ClientFieldsContainer extends AbstractClientFieldsContainer {
      @Override
      public void initFields() {
         blockColors = new BlockColors();
      }
   }

   private static ClientFieldsContainer fieldsContainer;

   public static void initFieldsContainer() {
      if (FMLCommonHandler.instance().getSide().isClient()) {
         fieldsContainer = new ClientFieldsContainer();
      }
   }

   public static float blockcolorIntensity = 0.75F;
   public static ResourceLocation bindEnotherTexture = null;
   public static boolean useTeleportHook = false;
   public static Random hooksRand = new Random();
   static ItemCameraTransforms cameraTransforms;
   public static boolean soundManagerUpdatingNow = false;
   static boolean dontRecurse;
   public static int moveslot = 0;

   @SideOnly(Side.CLIENT)
   public static void renderToolTip(GuiScreen gui, ItemStack stack, int x, int y) {
      ItemsElements.ElementsPack pack = ItemsElements.getAllElements(stack);
      GlStateManager.disableDepth();
      ManaBar.renderElementsVision(x + 40, y + 60, pack);
      GlStateManager.enableDepth();
   }

   @SideOnly(Side.CLIENT)
   @Hook(
      returnCondition = ReturnCondition.ON_TRUE
   )
   public static boolean switchToRealms(RealmsBridge bridge, GuiScreen p_switchToRealms_1_) {
      return false;
   }

   @SideOnly(Side.CLIENT)
   @Hook(
      returnCondition = ReturnCondition.ON_TRUE,
      returnNull = true
   )
   public static boolean getNotificationScreen(RealmsBridge bridge, GuiScreen p_getNotificationScreen_1_) {
      return false;
   }

   @SideOnly(Side.CLIENT)
   @Hook(
      returnCondition = ReturnCondition.ON_TRUE,
      returnAnotherMethod = "getTransformsVec3f"
   )
   public static boolean getTransform(ItemCameraTransforms transforms, TransformType type) {
      return Debugger.itemTransformHookEnabled;
   }

   // TODO нужно или нет
//   public static ItemTransformVec3f getTransformsVec3f(ItemCameraTransforms transforms, TransformType type) {
//      if (cameraTransforms == null || AnimationTimer.normaltick % 10 == 0) {
//         EntityPlayer player = Minecraft.getMinecraft().player;
//         Item item = player.getHeldItemMainhand().isEmpty() ? player.getHeldItemOffhand().getItem() : player.getHeldItemMainhand().getItem();
//         cameraTransforms = CreateItemFile.readJsonItemCameraTransforms(item.getRegistryName().getPath());
//      }
//
//      boolean hooksave = Debugger.itemTransformHookEnabled;
//      Debugger.itemTransformHookEnabled = false;
//      ItemTransformVec3f tr = cameraTransforms != null ? cameraTransforms.getTransform(type) : ItemTransformVec3f.DEFAULT;
//      Debugger.itemTransformHookEnabled = hooksave;
//      return tr;
//   }

   @Hook(
      returnCondition = ReturnCondition.ON_TRUE
   )
   public static boolean track(EntityTracker tracker, Entity entityIn, int trackingRange, int updateFrequency, boolean sendVelocityUpdates) {
      if (entityIn instanceof IFixedTrackerEntity && ((IFixedTrackerEntity)entityIn).canFix()) {
         MyEntityTrackerEntry.track(tracker, entityIn, trackingRange, updateFrequency, sendVelocityUpdates);
         return true;
      } else {
         return false;
      }
   }

   @Hook(
      returnCondition = ReturnCondition.ALWAYS
   )
   public static void tryCatchFire(BlockFire blockfire, World worldIn, BlockPos pos, int chance, Random random, int age, EnumFacing face) {
      int i = worldIn.getBlockState(pos).getBlock().getFlammability(worldIn, pos, face);
      if (random.nextInt(chance) < i) {
         IBlockState iblockstate = worldIn.getBlockState(pos);
         if (random.nextInt(age + 10) < 5 && !worldIn.isRainingAt(pos)) {
            int j = age + random.nextInt(5) / 4;
            if (j > 15) {
               j = 15;
            }

            worldIn.setBlockState(pos, blockfire.getDefaultState().withProperty(BlockFire.AGE, j), 3);
         } else {
            IBlockState has = worldIn.getBlockState(pos);
            if ((!(random.nextFloat() < 0.75F) || has.getMaterial() != Material.WOOD)
               && (!(random.nextFloat() < 0.35F) || has.getMaterial() != Material.LEAVES)) {
               worldIn.setBlockToAir(pos);
            } else {
               worldIn.setBlockState(pos, BlocksRegister.ASHBLOCK.getDefaultState().withProperty(AshBlock.LAYERS, 1).withProperty(AshBlock.ISFALLING, false));
            }
         }

         if (iblockstate.getBlock() == Blocks.TNT) {
            Blocks.TNT.onPlayerDestroy(worldIn, pos, iblockstate.withProperty(BlockTNT.EXPLODE, true));
         }
      }
   }

   @Hook(
      returnCondition = ReturnCondition.ON_TRUE
   )
   @SideOnly(Side.CLIENT)
   public static boolean addBlockHitEffects(ParticleManager particleManager, BlockPos pos, RayTraceResult target) {
      EntityPlayer player = Minecraft.getMinecraft().player;
      return player != null && player.getHeldItemMainhand().getItem() instanceof IWeapon;
   }

   @Hook(
      returnCondition = ReturnCondition.ON_TRUE
   )
   public static boolean onUpdateLook(EntityLookHelper entityLookHelper) {
      return entityLookHelper.getLookPosX() == Double.MAX_VALUE;
   }

   private static float getVolume(SoundManager soundManager, SoundCategory category) {
      // FIX: Hardcoded value (TEMPORARY FIX)
      // return category != null && category != SoundCategory.MASTER ? soundManager.options.getSoundLevel(category) : 1.0F;
      return 1.0F;
   }

   private static float getClampedPitch(SoundManager soundManager, ISound soundIn) {
      return MathHelper.clamp(soundIn.getPitch(), 0.5F, 2.0F);
   }

   private static float getClampedVolume(SoundManager soundManager, ISound soundIn) {
      return MathHelper.clamp(soundIn.getVolume() * getVolume(soundManager, soundIn.getCategory()), 0.0F, 1.0F);
   }

   @Hook(
      returnCondition = ReturnCondition.ON_TRUE
   )
   @SideOnly(Side.CLIENT)
   public static boolean stopAllSounds(SoundManager soundManager) {
      return soundManagerUpdatingNow;
   }

   @Hook(
      returnCondition = ReturnCondition.ON_TRUE
   )
   @SideOnly(Side.CLIENT)
   public static boolean playSound(SoundManager soundManager, ISound p_sound) {
      return soundManagerUpdatingNow;
   }

   @Hook(
      returnCondition = ReturnCondition.ON_TRUE
   )
   @SideOnly(Side.CLIENT)
   public static boolean playDelayedSound(SoundManager soundManager, ISound sound, int delay) {
      return soundManagerUpdatingNow;
   }

   // FIX: this method unused and problematic
//   @Hook(
//      returnCondition = ReturnCondition.ALWAYS
//   )
//   public static void updateAllSounds(SoundManager soundManager) {
//      soundManagerUpdatingNow = true;
//      soundManager.playTime++;
//      SoundSystem soundsystem = soundManager.sndSystem;
//      int tickableSoundsCount = soundManager.tickableSounds.size();
//      ITickableSound[] tickableSoundsCopy = new ITickableSound[tickableSoundsCount];
//
//      for (int i = 0; i < tickableSoundsCount; i++) {
//         if (soundManager.tickableSounds.size() > 0 && i < soundManager.tickableSounds.size()) {
//            tickableSoundsCopy[i] = (ITickableSound)soundManager.tickableSounds.get(i);
//         }
//      }
//
//      for (ITickableSound itickablesound : tickableSoundsCopy) {
//         itickablesound.update();
//         if (itickablesound.isDonePlaying()) {
//            soundManager.stopSound(itickablesound);
//         } else {
//            String s = (String)soundManager.invPlayingSounds.get(itickablesound);
//            soundsystem.setVolume(s, getClampedVolume(soundManager, itickablesound));
//            soundsystem.setPitch(s, getClampedPitch(soundManager, itickablesound));
//            soundsystem.setPosition(s, itickablesound.getXPosF(), itickablesound.getYPosF(), itickablesound.getZPosF());
//         }
//      }
//
//      Iterator<Entry<String, ISound>> iterator = soundManager.playingSounds.entrySet().iterator();
//
//      while (iterator.hasNext()) {
//         Entry<String, ISound> entry = iterator.next();
//         String s1 = entry.getKey();
//         ISound isound = entry.getValue();
//         if (!soundsystem.playing(s1)) {
//            int ix = (Integer)soundManager.playingSoundsStopTime.get(s1);
//            if (ix <= soundManager.playTime) {
//               int j = isound.getRepeatDelay();
//               if (isound.canRepeat() && j > 0) {
//                  soundManager.delayedSounds.put(isound, soundManager.playTime + j);
//               }
//
//               iterator.remove();
//               SoundManager.LOGGER.debug(SoundManager.LOG_MARKER, "Removed channel {} because it's not playing anymore", s1);
//               soundsystem.removeSource(s1);
//               soundManager.playingSoundsStopTime.remove(s1);
//
//               try {
//                  soundManager.categorySounds.remove(isound.getCategory(), s1);
//               } catch (RuntimeException var11) {
//               }
//
//               if (isound instanceof ITickableSound) {
//                  soundManager.tickableSounds.remove(isound);
//               }
//            }
//         }
//      }
//
//      Iterator<Entry<ISound, Integer>> iterator1 = soundManager.delayedSounds.entrySet().iterator();
//
//      while (iterator1.hasNext()) {
//         Entry<ISound, Integer> entry1 = iterator1.next();
//         if (soundManager.playTime >= entry1.getValue()) {
//            ISound isound1 = entry1.getKey();
//            if (isound1 instanceof ITickableSound) {
//               ((ITickableSound)isound1).update();
//            }
//
//            soundManager.playSound(isound1);
//            iterator1.remove();
//         }
//      }
//
//      soundManagerUpdatingNow = false;
//   }

   // TODO нужно ли?
//   public static void update(SoundHandler soundhandler) {
//      try {
//         Field field = soundhandler.getClass().getDeclaredField("sndManager");
//         field.setAccessible(true);
//         Object obj = field.get(soundhandler);
//         if (obj instanceof SoundManager) {
//            try {
//               ((SoundManager)obj).updateAllSounds();
//            } catch (ConcurrentModificationException var4) {
//               var4.printStackTrace();
//            }
//         }
//      } catch (SecurityException | NoSuchFieldException var5) {
//         var5.printStackTrace();
//      } catch (IllegalArgumentException var6) {
//         var6.printStackTrace();
//      } catch (IllegalAccessException var7) {
//         var7.printStackTrace();
//      }
//   }

   @SideOnly(Side.CLIENT)
   @Hook(
      returnCondition = ReturnCondition.ALWAYS
   )
   public static void addPotionTooltip(PotionUtils utils, ItemStack itemIn, List<String> lores, float durationFactor) {
      PotionEffects.addPotionTooltip(itemIn, lores, durationFactor);
   }

   @SideOnly(Side.CLIENT)
   @Hook(
      returnCondition = ReturnCondition.ALWAYS
   )
   public static void drawActivePotionEffects(InventoryEffectRenderer renderer) {
      int i = renderer.getGuiLeft() - 124;
      int j = renderer.getGuiTop();
      int k = 166;
      Collection<PotionEffect> collection = renderer.mc.player.getActivePotionEffects();
      if (!collection.isEmpty()) {
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.disableLighting();
         int l = 33;
         if (collection.size() > 5) {
            l = 132 / (collection.size() - 1);
         }

         for (PotionEffect potioneffect : Ordering.natural().sortedCopy(collection)) {
            Potion potion = potioneffect.getPotion();
            if (potion.shouldRender(potioneffect)) {
               GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
               renderer.mc.getTextureManager().bindTexture(InventoryEffectRenderer.INVENTORY_BACKGROUND);
               renderer.drawTexturedModalRect(i, j, 0, 166, 140, 32);
               if (potion.hasStatusIcon()) {
                  int i1 = potion.getStatusIconIndex();
                  renderer.drawTexturedModalRect(i + 6, j + 7, 0 + i1 % 8 * 18, 198 + i1 / 8 * 18, 18, 18);
               }

               potion.renderInventoryEffect(potioneffect, renderer, i, j, 0.0F);
               if (!potion.shouldRenderInvText(potioneffect)) {
                  j += l;
               } else {
                  String s1 = I18n.format(potion.getName(), new Object[0]);
                  s1 = s1 + " " + (potioneffect.getAmplifier() + 1);
                  renderer.mc.fontRenderer.drawStringWithShadow(s1, i + 10 + 18, j + 6, 16777215);
                  String s = Potion.getPotionDurationString(potioneffect, 1.0F);
                  renderer.mc.fontRenderer.drawStringWithShadow(s, i + 10 + 18, j + 6 + 10, 8355711);
                  j += l;
               }
            }
         }
      }
   }

   @Hook(
      returnCondition = ReturnCondition.ON_TRUE,
      booleanReturnConstant = false
   )
   public static boolean interactWithEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase entityIn, EnumHand hand) {
      return "enderio:item_soul_vial".equals(stack.getItem().getRegistryName().toString())
         && entityIn instanceof AbstractMob
         && !((AbstractMob)entityIn).canBeCaptured(playerIn);
   }

   @SideOnly(Side.CLIENT)
   @Hook(
      returnCondition = ReturnCondition.ON_TRUE
   )
   public static boolean bindTexture(Render render, ResourceLocation location) {
      if (bindEnotherTexture != null) {
         Minecraft.getMinecraft().renderEngine.bindTexture(bindEnotherTexture);
         return true;
      } else {
         return false;
      }
   }

   @SideOnly(Side.CLIENT)
   @Hook
   public static void doRenderShadowAndFire(Render render, Entity entityIn, double x, double y, double z, float yaw, float partialTicks) {
      if (!dontRecurse) {
         if (entityIn instanceof EntityLivingBase) {
            EntityLivingBase entitylb = (EntityLivingBase)entityIn;
            Collection<PotionEffect> list = entitylb.getActivePotionEffects();
            if (!list.isEmpty()) {
               for (PotionEffect effect : list) {
                  if (effect.getPotion() instanceof AdvancedPotion) {
                     AdvancedPotion potion = (AdvancedPotion)effect.getPotion();
                     if (potion.shouldRender) {
                        dontRecurse = true;
                        potion.render(entitylb, x, y, z, yaw, partialTicks, effect, render);
                        dontRecurse = false;
                     }
                  }
               }
            }
         }
      }
   }

   @Hook(
      returnCondition = ReturnCondition.ON_TRUE,
      booleanReturnConstant = true
   )
   public static boolean isMovementBlocked(EntityLivingBase entity) {
      PotionEffect eff = entity.getActivePotionEffect(PotionEffects.FREEZING);
      return Freezing.canImmobilizeEntity(entity, eff) || Stun.canImmobilizeEntity(entity, entity.getActivePotionEffect(PotionEffects.STUN));
   }

   @Hook(
      returnCondition = ReturnCondition.ON_TRUE,
      booleanReturnConstant = true
   )
   public static boolean isMovementBlocked(EntityPlayer entity) {
      PotionEffect eff = entity.getActivePotionEffect(PotionEffects.FREEZING);
      return Freezing.canImmobilizeEntity(entity, eff) || Stun.canImmobilizeEntity(entity, entity.getActivePotionEffect(PotionEffects.STUN));
   }

   @SideOnly(Side.CLIENT)
   @Hook(
      returnCondition = ReturnCondition.ON_TRUE,
      returnAnotherMethod = "getFogColorVector"
   )
   public static boolean getFogColor(
      BlockLiquid blockliquid, World world, BlockPos pos, IBlockState state, Entity entity, Vec3d originalColor, float partialTicks
   ) {
      return world.provider.getDimension() == 103;
   }

   // TODO нужно ли?
//   public static Vec3d getFogColorVector(
//      BlockLiquid blockliquid, World world, BlockPos pos, IBlockState state, Entity entity, Vec3d originalColor, float partialTicks
//   ) {
//      Vec3d viewport = ActiveRenderInfo.projectViewFromEntity(entity, partialTicks);
//      if (state.getMaterial().isLiquid()) {
//         float height = 0.0F;
//         if (state.getBlock() instanceof BlockLiquid) {
//            height = BlockLiquid.getLiquidHeightPercent((Integer)state.getValue(BlockLiquid.LEVEL)) - 0.11111111F;
//         }
//
//         float f1 = pos.getY() + 1 - height;
//         if (viewport.y > f1) {
//            BlockPos upPos = pos.up();
//            IBlockState upState = world.getBlockState(upPos);
//            return upState.getBlock().getFogColor(world, upPos, upState, entity, originalColor, partialTicks);
//         }
//      }
//
//      if (state.getMaterial() == Material.WATER) {
//         float r = MathHelper.clamp((-entity.rotationPitch + 90.0F) / 180.0F, 0.0F, 1.0F);
//         return new Vec3d(0.1 + 0.1 * r, 0.4 + 0.35 * r, 0.85 + 0.23 * r);
//      } else {
//         return originalColor;
//      }
//   }

   @Hook(
      returnCondition = ReturnCondition.ON_TRUE
   )
   public static boolean processClientStatus(NetHandlerPlayServer nethandler, CPacketClientStatus packetIn) {
      return packetIn.getStatus() == State.PERFORM_RESPAWN
         && !nethandler.player.queuedEndExit
         && nethandler.player.isPotionActive(PotionEffects.RESPAWN_PENALTY);
   }

   @Hook(
      returnCondition = ReturnCondition.ON_TRUE,
      returnAnotherMethod = "clampv"
   )
   public static boolean clampValue(RangedAttribute rang, double value) {
      return rang == SharedMonsterAttributes.MAX_HEALTH;
   }

   public static double clampv(RangedAttribute rang, double value) {
      return MathHelper.clamp(value, Float.MIN_VALUE, 8000.0);
   }

   @Hook(
      returnCondition = ReturnCondition.ON_TRUE,
      booleanReturnConstant = false
   )
   public static boolean attackEntityFrom(EntityItem item, DamageSource source, float amount) {
      if (!item.world.isRemote && !item.isDead) {
         Item itemitem = item.getItem().getItem();
         if (itemitem != Items.ENCHANTED_BOOK || !source.isFireDamage() && !source.isExplosion() && source != DamageSource.LIGHTNING_BOLT) {
            if (itemitem != ItemsRegister.MAGIC_POWDER || !source.isFireDamage() && !source.isExplosion() && source != DamageSource.LIGHTNING_BOLT) {
               if ((
                     itemitem == ItemsRegister.RHINESTONE
                        || itemitem == ItemsRegister.TOPAZ
                        || itemitem == ItemsRegister.AMETHYST
                        || itemitem == ItemsRegister.CITRINE
                        || itemitem == ItemsRegister.RUBY
                        || itemitem == ItemsRegister.SAPPHIRE
                        || itemitem == Items.DIAMOND
                        || itemitem == Items.EMERALD
                  )
                  && source.isFireDamage()) {
                  Block block = item.world.getBlockState(item.getPosition()).getBlock();
                  if (block == Blocks.LAVA || block == Blocks.FLOWING_LAVA) {
                     checkGemsparkIngridients(item.world, itemitem, item.getPosition());
                     return false;
                  }
               }

               return false;
            } else {
               Block block = item.world.getBlockState(item.getPosition()).getBlock();
               if (block == Blocks.FIRE) {
                  item.world.setBlockToAir(item.getPosition());
               } else if (block == Blocks.LAVA || block == Blocks.FLOWING_LAVA) {
                  checkGemsparkIngridients(item.world, itemitem, item.getPosition());
               }

               return true;
            }
         } else {
            if (item.world.getBlockState(item.getPosition()).getBlock() == Blocks.FIRE) {
               item.world.setBlockToAir(item.getPosition());
            }

            item.world
               .playSound(
                  (EntityPlayer)null, item.getPosition(), Sounds.burn, SoundCategory.BLOCKS, 0.8F, 0.9F + item.world.rand.nextFloat() / 5.0F
               );
            item.setDead();
            EntityItem dust = new EntityItem(
               item.world, item.posX, item.posY, item.posZ, new ItemStack(ItemsRegister.MAGIC_POWDER)
            );
            dust.setFire(4);
            item.world.spawnEntity(dust);
            return false;
         }
      } else {
         return false;
      }
   }

   public static void checkGemsparkIngridients(World world, Item has, BlockPos pos) {
      AxisAlignedBB findGems = new AxisAlignedBB(pos);
      List<EntityItem> list = world.getEntitiesWithinAABB(EntityItem.class, findGems);
      boolean rhinestone = true;
      boolean amethyst = true;
      boolean topaz = true;
      boolean ruby = true;
      boolean sapphire = true;
      boolean citrine = true;
      boolean emerald = true;
      boolean diamond = true;
      boolean haspowder = false;
      int count = 0;
      if (!list.isEmpty()) {
         for (EntityItem eitem : list) {
            Item gem = eitem.getItem().getItem();
            if (rhinestone && gem == ItemsRegister.RHINESTONE) {
               rhinestone = false;
               count++;
            }

            if (amethyst && gem == ItemsRegister.AMETHYST) {
               amethyst = false;
               count++;
            }

            if (topaz && gem == ItemsRegister.TOPAZ) {
               topaz = false;
               count++;
            }

            if (ruby && gem == ItemsRegister.RUBY) {
               ruby = false;
               count++;
            }

            if (sapphire && gem == ItemsRegister.SAPPHIRE) {
               sapphire = false;
               count++;
            }

            if (citrine && gem == ItemsRegister.CITRINE) {
               citrine = false;
               count++;
            }

            if (emerald && gem == Items.EMERALD) {
               emerald = false;
               count++;
            }

            if (diamond && gem == Items.DIAMOND) {
               diamond = false;
               count++;
            }

            if (gem == ItemsRegister.MAGIC_POWDER) {
               haspowder = true;
            }
         }
      }

      if (count >= 4 && haspowder) {
         for (EntityItem eitem : list) {
            eitem.setDead();
         }

         world.setBlockState(pos, BlocksRegister.GEMSPARKBLOCK.getDefaultState());
         world.playSound((EntityPlayer)null, pos, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 0.8F, 0.9F + world.rand.nextFloat() / 5.0F);
      }
   }

   @SideOnly(Side.CLIENT)
   @Hook(
      injectOnExit = true
   )
   public static void handleCooldown(NetHandlerPlayClient clientHandler, SPacketCooldown packetIn) {
      if (packetIn.getTicks() == 0 && Minecraft.getMinecraft().player != null) {
         ItemStack stack = Minecraft.getMinecraft().player.inventory.getCurrentItem();
         if (stack.getItem() instanceof IWeapon && ((IWeapon)stack.getItem()).canChangeItem(stack, Minecraft.getMinecraft().player)) {
            Minecraft.getMinecraft().player.inventory.currentItem = moveslot;
            clientHandler.sendPacket(new CPacketHeldItemChange(moveslot));
         }
      }
   }

   @SideOnly(Side.CLIENT)
   @Hook
   public static void sendPacket(NetHandlerPlayClient playClient, Packet<?> packetIn) {
      if (packetIn instanceof CPacketHeldItemChange) {
         CPacketHeldItemChange packet = (CPacketHeldItemChange)packetIn;
         moveslot = packet.getSlotId();
      }
   }

   @Hook(
      returnCondition = ReturnCondition.ON_TRUE
   )
   public static boolean processHeldItemChange(NetHandlerPlayServer serverHandler, CPacketHeldItemChange packetIn) {
      if (packetIn.getSlotId() >= 0 && packetIn.getSlotId() < InventoryPlayer.getHotbarSize()) {
         InventoryPlayer inventory = serverHandler.player.inventory;
         ItemStack stack = inventory.getCurrentItem();
         Item item = stack.getItem();
         if (item instanceof IWeapon && !((IWeapon)item).canChangeItem(stack, serverHandler.player)) {
            return true;
         }
      }

      return false;
   }

   @SideOnly(Side.CLIENT)
   @Hook(
      returnCondition = ReturnCondition.ON_TRUE
   )
   public static boolean changeCurrentItem(InventoryPlayer inv, int direction) {
      if (direction > 0) {
         direction = 1;
      }

      if (direction < 0) {
         direction = -1;
      }

      moveslot -= direction;

      while (moveslot < 0) {
         moveslot += 9;
      }

      while (moveslot >= 9) {
         moveslot -= 9;
      }

      ItemStack stack = inv.getCurrentItem();
      Item item = stack.getItem();
      return item instanceof IWeapon && !((IWeapon)item).canChangeItem(stack, inv.player);
   }

   public static Class getGenericParameterClass(Class actualClass, int parameterIndex) {
      try {
         return (Class)((ParameterizedType)actualClass.getGenericSuperclass()).getActualTypeArguments()[parameterIndex];
      } catch (TypeNotPresentException var3) {
      } catch (MalformedParameterizedTypeException var4) {
      } catch (GenericSignatureFormatError var5) {
      } catch (ClassCastException var6) {
      }

      return null;
   }

   @SideOnly(Side.CLIENT)
   @Hook(
      targetMethod = "<init>",
      injectOnExit = true
   )
   public static void mobModelReg(RenderLivingBase randerlb, RenderManager renderManagerIn, ModelBase modelBaseIn, float shadowSizeIn) {
      Class clazz = getGenericParameterClass(randerlb.getClass(), 0);
      if (clazz != null && modelBaseIn != null) {
         DeathEffects.tryAddtoMainModels(clazz, modelBaseIn);
      }
   }

   public static ModelRenderer getArmForSide(EnumHandSide side, ModelBiped biped) {
      return side == EnumHandSide.LEFT ? biped.bipedLeftArm : biped.bipedRightArm;
   }

   public static EnumHandSide getMainHand(Entity entityIn) {
      if (entityIn instanceof EntityLivingBase) {
         EntityLivingBase entitylivingbase = (EntityLivingBase)entityIn;
         return entitylivingbase.getPrimaryHand();
      } else {
         return EnumHandSide.RIGHT;
      }
   }

   @Hook(
      returnCondition = ReturnCondition.ALWAYS
   )
   public static void execute(CommandEnchant command, MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
      if (args.length < 2) {
         throw new WrongUsageException("commands.enchant.usage", new Object[0]);
      } else {
         EntityLivingBase entitylivingbase = (EntityLivingBase)CommandEnchant.getEntity(server, sender, args[0], EntityLivingBase.class);

         Enchantment enchantment;
         try {
            enchantment = Enchantment.getEnchantmentByID(CommandEnchant.parseInt(args[1], 0));
         } catch (NumberInvalidException var13) {
            enchantment = Enchantment.getEnchantmentByLocation(args[1]);
         }

         if (enchantment == null) {
            throw new NumberInvalidException("commands.enchant.notFound", new Object[]{args[1]});
         } else {
            int i = 1;
            ItemStack itemstack = entitylivingbase.getHeldItemMainhand();
            if (itemstack.isEmpty()) {
               throw new CommandException("commands.enchant.noItem", new Object[0]);
            } else {
               if (args.length >= 3) {
                  i = CommandEnchant.parseInt(args[2], enchantment.getMinLevel(), 255);
               }

               if (itemstack.hasTagCompound()) {
                  NBTTagList nbttaglist = itemstack.getEnchantmentTagList();
                  ArrayList<Integer> removes = new ArrayList<>();

                  for (int j = 0; j < nbttaglist.tagCount(); j++) {
                     int k = nbttaglist.getCompoundTagAt(j).getShort("id");
                     if (Enchantment.getEnchantmentByID(k) != null) {
                        Enchantment enchantment1 = Enchantment.getEnchantmentByID(k);
                        if (enchantment1 == enchantment) {
                           removes.add(j);
                        }
                     }
                  }

                  if (!removes.isEmpty()) {
                     for (int jx : removes) {
                        nbttaglist.removeTag(jx);
                     }
                  }
               }

               itemstack.addEnchantment(enchantment, i);
               CommandEnchant.notifyCommandListener(sender, command, "commands.enchant.success", new Object[0]);
            }
         }
      }
   }

   // TODO нужно ли?
//   public static void armsToDefaults(ModelBiped biped, float ageInTicks, float limbSwing, float limbSwingAmount) {
//      biped.bipedRightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F;
//      biped.bipedLeftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
//      biped.bipedRightArm.rotateAngleY = 0.0F;
//      biped.bipedRightArm.rotateAngleZ = 0.0F;
//      biped.bipedLeftArm.rotateAngleY = 0.0F;
//      biped.bipedLeftArm.rotateAngleZ = 0.0F;
//      switch (biped.leftArmPose) {
//         case EMPTY:
//            biped.bipedLeftArm.rotateAngleY = 0.0F;
//            break;
//         case BLOCK:
//            biped.bipedLeftArm.rotateAngleX = biped.bipedLeftArm.rotateAngleX * 0.5F - 0.9424779F;
//            biped.bipedLeftArm.rotateAngleY = (float) (Math.PI / 6);
//            break;
//         case ITEM:
//            biped.bipedLeftArm.rotateAngleX = biped.bipedLeftArm.rotateAngleX * 0.5F - (float) (Math.PI / 10);
//            biped.bipedLeftArm.rotateAngleY = 0.0F;
//      }
//
//      switch (biped.rightArmPose) {
//         case EMPTY:
//            biped.bipedRightArm.rotateAngleY = 0.0F;
//            break;
//         case BLOCK:
//            biped.bipedRightArm.rotateAngleX = biped.bipedRightArm.rotateAngleX * 0.5F - 0.9424779F;
//            biped.bipedRightArm.rotateAngleY = (float) (-Math.PI / 6);
//            break;
//         case ITEM:
//            biped.bipedRightArm.rotateAngleX = biped.bipedRightArm.rotateAngleX * 0.5F - (float) (Math.PI / 10);
//            biped.bipedRightArm.rotateAngleY = 0.0F;
//      }
//
//      biped.bipedRightArm.rotateAngleZ = biped.bipedRightArm.rotateAngleZ + (MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F);
//      biped.bipedLeftArm.rotateAngleZ = biped.bipedLeftArm.rotateAngleZ - (MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F);
//      biped.bipedRightArm.rotateAngleX = biped.bipedRightArm.rotateAngleX + MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
//      biped.bipedLeftArm.rotateAngleX = biped.bipedLeftArm.rotateAngleX - MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
//      if (biped.isRiding) {
//         biped.bipedRightArm.rotateAngleX += (float) (-Math.PI / 5);
//         biped.bipedLeftArm.rotateAngleX += (float) (-Math.PI / 5);
//      }
//   }

   @SideOnly(Side.CLIENT)
   @Hook(
      returnCondition = ReturnCondition.ON_TRUE
   )
   public static boolean renderItemInFirstPerson(
      ItemRenderer renderer,
      AbstractClientPlayer player,
      float p_187457_2_,
      float p_187457_3_,
      EnumHand hand,
      float p_187457_5_,
      ItemStack stack,
      float p_187457_7_
   ) {
      p_187457_7_ = 0.0F;
      int id = Weapons.getPlayerAnimationId(player, hand);
      PlayerAnimation animation = Weapons.animationsRegister.getOrDefault((byte)id, PlayerAnimations.DEFAULT);
      if (id != 0) {
         float an = 1.0F - Weapons.getPlayerAnimationValue(player, hand, Minecraft.getMinecraft().getRenderPartialTicks());
         if (an > 0.0F && an < 1.0F) {
            if (animation.transformItemFirstperson()) {
               animation.render(player, hand, an, stack, p_187457_7_);
            } else {
               PlayerAnimations.instance.renderNone(player, hand, an, stack, p_187457_7_);
            }

            return true;
         }
      }

      return false;
   }

   @SideOnly(Side.CLIENT)
   @Hook(
      injectOnExit = true
   )
   public static void setRotationAngles(
      ModelBiped biped, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn
   ) {
      if (entityIn instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)entityIn;
         EnumHandSide enumhandside = getMainHand(entityIn);
         float partialTicks = Minecraft.getMinecraft().getRenderPartialTicks();
         float animMain = 1.0F - Weapons.getPlayerAnimationValue(player, EnumHand.MAIN_HAND, partialTicks);
         float animOff = 1.0F - Weapons.getPlayerAnimationValue(player, EnumHand.OFF_HAND, partialTicks);
         int idMain = Weapons.getPlayerAnimationId(player, EnumHand.MAIN_HAND);
         int idOff = Weapons.getPlayerAnimationId(player, EnumHand.OFF_HAND);
         PlayerAnimation animationMain = Weapons.animationsRegister.getOrDefault((byte)idMain, PlayerAnimations.DEFAULT);
         if (animationMain.ID != 0 && animationMain.transformHandThirdperson() && animMain < 1.0F) {
            animationMain.transform(animMain, biped, getArmForSide(enumhandside, biped), enumhandside, null, player, partialTicks, EnumHand.MAIN_HAND);
         }

         PlayerAnimation animationOff = Weapons.animationsRegister.getOrDefault((byte)idOff, PlayerAnimations.DEFAULT);
         if (animationOff.ID != 0 && animationOff.transformHandThirdperson() && animOff < 1.0F) {
            animationOff.transform(
               animOff, biped, getArmForSide(enumhandside.opposite(), biped), enumhandside.opposite(), null, player, partialTicks, EnumHand.OFF_HAND
            );
         }
      }
   }

   @SideOnly(Side.CLIENT)
   @Hook(
      returnCondition = ReturnCondition.ON_TRUE
   )
   public static boolean renderItemSide(
      ItemRenderer renderer, EntityLivingBase entitylivingbaseIn, ItemStack heldStack, TransformType transform, boolean leftHanded
   ) {
      if (entitylivingbaseIn instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)entitylivingbaseIn;
         EnumHand hand = leftHanded ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
         int id = Weapons.getPlayerAnimationId(player, hand);
         PlayerAnimation animation = Weapons.animationsRegister.getOrDefault((byte)id, PlayerAnimations.DEFAULT);
         if (animation.ID != 0 && animation.transformItemThirdperson()) {
            float partialTicks = Minecraft.getMinecraft().getRenderPartialTicks();
            float anim = 1.0F - Weapons.getPlayerAnimationValue(player, hand, partialTicks);
            if (anim > 0.0F && anim < 1.0F) {
               animation.transform(anim, null, null, leftHanded ? EnumHandSide.LEFT : EnumHandSide.RIGHT, heldStack, player, partialTicks, hand);
            }
         }
      }

      return false;
   }

   @Hook(
      returnCondition = ReturnCondition.ON_TRUE,
      intReturnConstant = 100
   )
   public static boolean getPortalCooldown(EntityPlayer player) {
      return player.dimension == 0;
   }

   @Hook(
      returnCondition = ReturnCondition.ON_TRUE,
      booleanReturnConstant = true
   )
   public static boolean isElytraFlying(EntityLivingBase entity) {
      return entity instanceof EntityPlayer ? (Boolean)entity.getDataManager().get(PropertiesRegistry.FLYING) : false;
   }

   public static int getBrightness(Entity entity, float f) {
      int oldValue = 0;
      int j = (oldValue >> 20 & 15) / 2;
      int k = (oldValue >> 4 & 15) / 2;
      return j << 20 | k << 4;
   }

   // TODO нужно ли?
//   public static void markAndNotifyBlock(World world, BlockPos pos, @Nullable Chunk chunk, IBlockState oldState, IBlockState newState, int flags) {
//      if ((
//            newState.getLightOpacity(world, pos) != oldState.getLightOpacity(world, pos)
//               || newState.getLightValue(world, pos) != oldState.getLightValue(world, pos)
//         )
//         && world.isRemote) {
//         ColoredLightning.doColorUpdate(pos.getX(), pos.getY(), pos.getZ(), false, world);
//      }
//   }

   public static void setBlockState(World world, BlockPos pos, IBlockState newState, int flags, @Hook.ReturnValue boolean returned) {
      if (newState.getLightValue(world, pos) > 0 || newState.getLightOpacity(world, pos) > 0 || newState.getBlock() == Blocks.AIR) {
         world.markBlockRangeForRenderUpdate(
            pos.getX() - 15,
            pos.getY() - 15,
            pos.getZ() - 15,
            pos.getX() + 15,
            pos.getY() + 15,
            pos.getZ() + 15
         );
      }
   }

   // TODO нужно ли?
//   @SideOnly(Side.CLIENT)
//   public static boolean renderModel(
//      BlockModelRenderer renderer,
//      IBlockAccess worldIn,
//      IBakedModel modelIn,
//      IBlockState stateIn,
//      BlockPos posIn,
//      BufferBuilder buffer,
//      boolean checkSides,
//      long rand
//   ) {
//      boolean flag = Minecraft.isAmbientOcclusionEnabled() && stateIn.getLightValue(worldIn, posIn) == 0 && modelIn.isAmbientOcclusion(stateIn);
//      boolean flag2 = flag && Minecraft.getMinecraft().gameSettings.ambientOcclusion == 2;
//
//      try {
//         if (flag2) {
//            return renderModelMaxSmooth(worldIn, modelIn, stateIn, posIn, buffer, checkSides, rand);
//         } else {
//            return flag
//               ? renderModelSmooth(worldIn, modelIn, stateIn, posIn, buffer, checkSides, rand)
//               : renderModelFlat(worldIn, modelIn, stateIn, posIn, buffer, checkSides, rand);
//         }
//      } catch (Throwable var14) {
//         CrashReport crashreport = CrashReport.makeCrashReport(var14, "Tesselating block model");
//         CrashReportCategory crashreportcategory = crashreport.makeCategory("Block model being tesselated");
//         CrashReportCategory.addBlockInfo(crashreportcategory, posIn, stateIn);
//         crashreportcategory.addCrashSection("Using AO", flag);
//         throw new ReportedException(crashreport);
//      }
//   }

   // TODO нужно ли?
//   public static boolean renderModelFlat(
//      IBlockAccess worldIn, IBakedModel modelIn, IBlockState stateIn, BlockPos posIn, BufferBuilder buffer, boolean checkSides, long rand
//   ) {
//      boolean flag = false;
//      BitSet bitset = new BitSet(3);
//      World world;
//      if (worldIn instanceof World) {
//         world = (World)worldIn;
//      } else {
//         world = Minecraft.getMinecraft().world;
//      }
//
//      long lig = world.getWorldTime() % 24000L;
//
//      for (EnumFacing enumfacing : EnumFacing.values()) {
//         List<BakedQuad> list = modelIn.getQuads(stateIn, enumfacing, rand);
//         if (!list.isEmpty() && (!checkSides || stateIn.shouldSideBeRendered(worldIn, posIn, enumfacing))) {
//            int i = stateIn.getPackedLightmapCoords(worldIn, posIn.offset(enumfacing));
//            BlockPos posoff = posIn.offset(enumfacing);
//            LoadedRGBChunk loadedrgb = StaticRGBLight.getActualLoadedRGBChunk(posoff.getX(), posoff.getZ());
//            if (loadedrgb != null) {
//               long reds = loadedrgb.getBakedLight(LoadedRGBChunk.getBakedCoordRed(posoff.getX(), posoff.getY(), posoff.getZ()));
//               long greens = loadedrgb.getBakedLight(LoadedRGBChunk.getBakedCoordGreen(posoff.getX(), posoff.getY(), posoff.getZ()));
//               long blues = loadedrgb.getBakedLight(LoadedRGBChunk.getBakedCoordBlue(posoff.getX(), posoff.getY(), posoff.getZ()));
//               renderQuadsFlat(worldIn, lig, reds, greens, blues, stateIn, posIn, i, false, buffer, list, bitset);
//            }
//
//            flag = true;
//         }
//      }
//
//      List<BakedQuad> list1 = modelIn.getQuads(stateIn, (EnumFacing)null, rand);
//      if (!list1.isEmpty()) {
//         LoadedRGBChunk loadedrgb = StaticRGBLight.getActualLoadedRGBChunk(posIn.getX(), posIn.getZ());
//         if (loadedrgb != null) {
//            long reds = loadedrgb.getBakedLight(LoadedRGBChunk.getBakedCoordRed(posIn.getX(), posIn.getY(), posIn.getZ()));
//            long greens = loadedrgb.getBakedLight(LoadedRGBChunk.getBakedCoordGreen(posIn.getX(), posIn.getY(), posIn.getZ()));
//            long blues = loadedrgb.getBakedLight(LoadedRGBChunk.getBakedCoordBlue(posIn.getX(), posIn.getY(), posIn.getZ()));
//            renderQuadsFlat(worldIn, lig, reds, greens, blues, stateIn, posIn, -1, true, buffer, list1, bitset);
//         }
//
//         flag = true;
//      }
//
//      return flag;
//   }

//   public static void renderQuadsFlat(
//      IBlockAccess blockAccessIn,
//      long dayNightLight,
//      long bakColR,
//      long bakColG,
//      long bakColB,
//      IBlockState stateIn,
//      BlockPos posIn,
//      int brightnessIn,
//      boolean ownBrightness,
//      BufferBuilder buffer,
//      List<BakedQuad> list,
//      BitSet bitSet
//   ) {
//      Vec3d vec3d = stateIn.getOffset(blockAccessIn, posIn);
//      double d0 = posIn.getX() + vec3d.x;
//      double d1 = posIn.getY() + vec3d.y;
//      double d2 = posIn.getZ() + vec3d.z;
//      int i = 0;
//      int brightnessX = 0;
//      int brightnessZ = 0;
//      int brighZadding = 0;
//      int brighXadding = 0;
//      float red = LoadedRGBChunk.finalColorAdditive(bakColR);
//      float green = LoadedRGBChunk.finalColorAdditive(bakColG);
//      float blue = LoadedRGBChunk.finalColorAdditive(bakColB);
//      if (!ownBrightness) {
//         brightnessX = ColorConverters.UnpackLightmapCoordsX(brightnessIn);
//         brightnessZ = ColorConverters.UnpackLightmapCoordsZ(brightnessIn);
//         brighZadding = Math.min(brightnessZ + (int)Math.round((red + green + blue) / 1000.0 * 220.0), 240);
//         brightnessIn = ColorConverters.RGBtoDecimal255(brightnessX, 0, brighZadding);
//      }
//
//      for (int j = list.size(); i < j; i++) {
//         BakedQuad bakedquad = list.get(i);
//         if (ownBrightness) {
//            fillQuadBounds(stateIn, bakedquad.getVertexData(), bakedquad.getFace(), (float[])null, bitSet);
//            BlockPos blockpos = bitSet.get(0) ? posIn.offset(bakedquad.getFace()) : posIn;
//            brightnessIn = stateIn.getPackedLightmapCoords(blockAccessIn, blockpos);
//            brightnessX = ColorConverters.UnpackLightmapCoordsX(brightnessIn);
//            brightnessZ = ColorConverters.UnpackLightmapCoordsZ(brightnessIn);
//            brighZadding = Math.min(brightnessZ + (int)Math.round((red + green + blue) / 1000.0 * 220.0), 240);
//            brightnessIn = ColorConverters.RGBtoDecimal255(brightnessX, 0, brighZadding + (brightnessIn == -1 ? (int)(red + green + blue) * 70 : 0));
//         }
//
//         buffer.addVertexData(bakedquad.getVertexData());
//         buffer.putBrightness4(brightnessIn, brightnessIn, brightnessIn, brightnessIn);
//         if (bakedquad.hasTintIndex()) {
//            int k = blockColors.colorMultiplier(stateIn, blockAccessIn, posIn, bakedquad.getTintIndex());
//            if (EntityRenderer.anaglyphEnable) {
//               k = TextureUtil.anaglyphColor(k);
//            }
//
//            float f = (k >> 16 & 0xFF) / 255.0F;
//            float f1 = (k >> 8 & 0xFF) / 255.0F;
//            float f2 = (k & 0xFF) / 255.0F;
//            if (bakedquad.shouldApplyDiffuseLighting()) {
//               float diffuse = LightUtil.diffuseLight(bakedquad.getFace());
//               f *= diffuse;
//               f1 *= diffuse;
//               f2 *= diffuse;
//            }
//
//            float dayadd = 0.0F;
//            float minimalbrightness = 0.3F;
//            if (dayNightLight >= 0L && dayNightLight < 1500L) {
//               dayadd = ((float)dayNightLight / 2142.0F + minimalbrightness) * brightnessX / 240.0F;
//            } else if (dayNightLight >= 1500L && dayNightLight < 12000L) {
//               dayadd = 1.0F * brightnessX / 240.0F;
//            } else if (dayNightLight >= 12000L && dayNightLight < 13500L) {
//               dayadd = (minimalbrightness + (float)(1500L - (dayNightLight - 12000L)) / 2142.0F) * brightnessX / 240.0F;
//            } else {
//               dayadd = minimalbrightness * brightnessX / 240.0F;
//            }
//
//            float brpow = (float)Math.pow(f, blockcolorIntensity);
//            float brpow1 = (float)Math.pow(f1, blockcolorIntensity);
//            float brpow2 = (float)Math.pow(f2, blockcolorIntensity);
//            float cmR = MathHelper.clamp(dayadd * f + red * brpow, 0.0F, brpow);
//            float cmG = MathHelper.clamp(dayadd * f1 + green * brpow1, 0.0F, brpow1);
//            float cmB = MathHelper.clamp(dayadd * f2 + blue * brpow2, 0.0F, brpow2);
//            buffer.putColorMultiplier(cmR, cmG, cmB, 4);
//            buffer.putColorMultiplier(cmR, cmG, cmB, 3);
//            buffer.putColorMultiplier(cmR, cmG, cmB, 2);
//            buffer.putColorMultiplier(cmR, cmG, cmB, 1);
//         } else if (bakedquad.shouldApplyDiffuseLighting()) {
//            float diffuse = LightUtil.diffuseLight(bakedquad.getFace());
//            float dayadd = 0.0F;
//            float minimalbrightness = 0.3F;
//            if (dayNightLight >= 0L && dayNightLight < 1500L) {
//               dayadd = ((float)dayNightLight / 2142.0F + minimalbrightness) * brightnessX / 240.0F;
//            } else if (dayNightLight >= 1500L && dayNightLight < 12000L) {
//               dayadd = 1.0F * brightnessX / 240.0F;
//            } else if (dayNightLight >= 12000L && dayNightLight < 13500L) {
//               dayadd = (minimalbrightness + (float)(1500L - (dayNightLight - 12000L)) / 2142.0F) * brightnessX / 240.0F;
//            } else {
//               dayadd = minimalbrightness * brightnessX / 240.0F;
//            }
//
//            float bound = Math.min(diffuse + Math.max(red + green + blue - 0.5F, 0.0F), 1.0F);
//            float cmR = MathHelper.clamp(red + dayadd, 0.0F, bound);
//            float cmG = MathHelper.clamp(green + dayadd, 0.0F, bound);
//            float cmB = MathHelper.clamp(blue + dayadd, 0.0F, bound);
//            buffer.putColorMultiplier(cmR, cmG, cmB, 4);
//            buffer.putColorMultiplier(cmR, cmG, cmB, 3);
//            buffer.putColorMultiplier(cmR, cmG, cmB, 2);
//            buffer.putColorMultiplier(cmR, cmG, cmB, 1);
//         }
//
//         buffer.putPosition(d0, d1, d2);
//      }
//   }

//   public static boolean renderModelSmooth(
//      IBlockAccess worldIn, IBakedModel modelIn, IBlockState stateIn, BlockPos posIn, BufferBuilder buffer, boolean checkSides, long rand
//   ) {
//      boolean flag = false;
//      float[] afloat = new float[EnumFacing.values().length * 2];
//      World world;
//      if (worldIn instanceof World) {
//         world = (World)worldIn;
//      } else {
//         world = Minecraft.getMinecraft().world;
//      }
//
//      long lig = world.getWorldTime() % 24000L;
//      BitSet bitset = new BitSet(3);
//      AmbientOcclusionFace blockmodelrenderer$ambientocclusionface = new AmbientOcclusionFace();
//      Vec3d[] nbColors = new Vec3d[]{
//         ColoredLightning.getAdditiveColorInPos(posIn.add(1, 0, 1)),
//         ColoredLightning.getAdditiveColorInPos(posIn.add(-1, 0, 1)),
//         ColoredLightning.getAdditiveColorInPos(posIn.add(1, 0, -1)),
//         ColoredLightning.getAdditiveColorInPos(posIn.add(-1, 0, -1)),
//         ColoredLightning.getAdditiveColorInPos(posIn.add(0, 1, 1)),
//         ColoredLightning.getAdditiveColorInPos(posIn.add(0, 1, -1)),
//         ColoredLightning.getAdditiveColorInPos(posIn.add(0, -1, 1)),
//         ColoredLightning.getAdditiveColorInPos(posIn.add(0, -1, -1)),
//         ColoredLightning.getAdditiveColorInPos(posIn.add(1, 1, 0)),
//         ColoredLightning.getAdditiveColorInPos(posIn.add(-1, 1, 0)),
//         ColoredLightning.getAdditiveColorInPos(posIn.add(1, -1, 0)),
//         ColoredLightning.getAdditiveColorInPos(posIn.add(-1, -1, 0)),
//         ColoredLightning.getAdditiveColorInPos(posIn.add(0, -1, 0)),
//         ColoredLightning.getAdditiveColorInPos(posIn.add(0, 1, 0)),
//         ColoredLightning.getAdditiveColorInPos(posIn.add(0, 0, -1)),
//         ColoredLightning.getAdditiveColorInPos(posIn.add(0, 0, 1)),
//         ColoredLightning.getAdditiveColorInPos(posIn.add(-1, 0, 0)),
//         ColoredLightning.getAdditiveColorInPos(posIn.add(1, 0, 0))
//      };
//
//      for (EnumFacing enumfacing : EnumFacing.values()) {
//         List<BakedQuad> list = modelIn.getQuads(stateIn, enumfacing, rand);
//         if (!list.isEmpty() && (!checkSides || stateIn.shouldSideBeRendered(worldIn, posIn, enumfacing))) {
//            renderQuadsSmooth(worldIn, lig, nbColors, stateIn, posIn, buffer, list, afloat, bitset, blockmodelrenderer$ambientocclusionface);
//            flag = true;
//         }
//      }
//
//      List<BakedQuad> list1 = modelIn.getQuads(stateIn, (EnumFacing)null, rand);
//      if (!list1.isEmpty()) {
//         renderQuadsSmooth(worldIn, lig, nbColors, stateIn, posIn, buffer, list1, afloat, bitset, blockmodelrenderer$ambientocclusionface);
//         flag = true;
//      }
//
//      return flag;
//   }

//   public static void renderQuadsSmooth(
//      IBlockAccess blockAccessIn,
//      long dayNightLight,
//      Vec3d[] nbColors,
//      IBlockState stateIn,
//      BlockPos posIn,
//      BufferBuilder buffer,
//      List<BakedQuad> list,
//      float[] quadBounds,
//      BitSet bitSet,
//      AmbientOcclusionFace aoFace
//   ) {
//      Vec3d vec3d = stateIn.getOffset(blockAccessIn, posIn);
//      double d0 = posIn.getX() + vec3d.x;
//      double d1 = posIn.getY() + vec3d.y;
//      double d2 = posIn.getZ() + vec3d.z;
//      int i = 0;
//      int brightnessX = 0;
//
//      for (int j = list.size(); i < j; i++) {
//         BakedQuad bakedquad = list.get(i);
//         fillQuadBounds(stateIn, bakedquad.getVertexData(), bakedquad.getFace(), quadBounds, bitSet);
//         aoFace.updateVertexBrightness(blockAccessIn, stateIn, posIn, bakedquad.getFace(), quadBounds, bitSet);
//         buffer.addVertexData(bakedquad.getVertexData());
//         buffer.putBrightness4(aoFace.vertexBrightness[0], aoFace.vertexBrightness[1], aoFace.vertexBrightness[2], aoFace.vertexBrightness[3]);
//         brightnessX = (int)(
//            (
//                  ColorConverters.UnpackLightmapCoordsX(aoFace.vertexBrightness[0])
//                     + ColorConverters.UnpackLightmapCoordsX(aoFace.vertexBrightness[1])
//                     + ColorConverters.UnpackLightmapCoordsX(aoFace.vertexBrightness[2])
//                     + ColorConverters.UnpackLightmapCoordsX(aoFace.vertexBrightness[3])
//               )
//               * 0.25
//         );
//         if (bakedquad.shouldApplyDiffuseLighting()) {
//            float diffuse = LightUtil.diffuseLight(bakedquad.getFace());
//            aoFace.vertexColorMultiplier[0] = aoFace.vertexColorMultiplier[0] * diffuse;
//            aoFace.vertexColorMultiplier[1] = aoFace.vertexColorMultiplier[1] * diffuse;
//            aoFace.vertexColorMultiplier[2] = aoFace.vertexColorMultiplier[2] * diffuse;
//            aoFace.vertexColorMultiplier[3] = aoFace.vertexColorMultiplier[3] * diffuse;
//         }
//
//         if (bakedquad.hasTintIndex()) {
//            int k = blockColors.colorMultiplier(stateIn, blockAccessIn, posIn, bakedquad.getTintIndex());
//            if (EntityRenderer.anaglyphEnable) {
//               k = TextureUtil.anaglyphColor(k);
//            }
//
//            float f = (k >> 16 & 0xFF) / 255.0F;
//            float f1 = (k >> 8 & 0xFF) / 255.0F;
//            float f2 = (k & 0xFF) / 255.0F;
//            float brpow = (float)Math.pow(f, blockcolorIntensity);
//            float brpow1 = (float)Math.pow(f1, blockcolorIntensity);
//            float brpow2 = (float)Math.pow(f2, blockcolorIntensity);
//            float diffuse = 1.0F;
//            float dayadd = 0.0F;
//            float minimalbrightness = 0.3F;
//            if (dayNightLight >= 0L && dayNightLight < 1500L) {
//               dayadd = ((float)dayNightLight / 2142.0F + minimalbrightness) * brightnessX / 240.0F;
//            } else if (dayNightLight >= 1500L && dayNightLight < 12000L) {
//               dayadd = 1.0F * brightnessX / 240.0F;
//            } else if (dayNightLight >= 12000L && dayNightLight < 13500L) {
//               dayadd = (minimalbrightness + (float)(1500L - (dayNightLight - 12000L)) / 2142.0F) * brightnessX / 240.0F;
//            } else {
//               dayadd = minimalbrightness * brightnessX / 240.0F;
//            }
//
//            for (int cfi = 4; cfi >= 1; cfi--) {
//               int cfn = 4 - cfi;
//               Vec3d colort = getNbColor(bakedquad, cfi, nbColors);
//               float cf = (float)colort.x;
//               float cf1 = (float)colort.y;
//               float cf2 = (float)colort.z;
//               float vertColorm = aoFace.vertexColorMultiplier[cfn];
//               float cmR = MathHelper.clamp((dayadd * f + cf * brpow) * vertColorm, 0.0F, brpow);
//               float cmG = MathHelper.clamp((dayadd * f1 + cf1 * brpow1) * vertColorm, 0.0F, brpow1);
//               float cmB = MathHelper.clamp((dayadd * f2 + cf2 * brpow2) * vertColorm, 0.0F, brpow2);
//               buffer.putColorMultiplier(cmR, cmG, cmB, cfi);
//            }
//         } else {
//            float diffuse = 1.0F;
//            float dayadd = 0.0F;
//            float minimalbrightness = 0.3F;
//            if (dayNightLight >= 0L && dayNightLight < 1500L) {
//               dayadd = ((float)dayNightLight / 2142.0F + minimalbrightness) * brightnessX / 240.0F;
//            } else if (dayNightLight >= 1500L && dayNightLight < 12000L) {
//               dayadd = 1.0F * brightnessX / 240.0F;
//            } else if (dayNightLight >= 12000L && dayNightLight < 13500L) {
//               dayadd = (minimalbrightness + (float)(1500L - (dayNightLight - 12000L)) / 2142.0F) * brightnessX / 240.0F;
//            } else {
//               dayadd = minimalbrightness * brightnessX / 240.0F;
//            }
//
//            for (int cfi = 4; cfi >= 1; cfi--) {
//               int cfn = 4 - cfi;
//               Vec3d colort = getNbColor(bakedquad, cfi, nbColors);
//               float cf = (float)colort.x;
//               float cf1 = (float)colort.y;
//               float cf2 = (float)colort.z;
//               float bound = Math.min(diffuse + Math.max(cf + cf1 + cf2 - 1.5F, 0.0F), 1.0F);
//               float vertColorm = aoFace.vertexColorMultiplier[cfn];
//               float cmR = MathHelper.clamp((cf + dayadd) * vertColorm, 0.0F, bound);
//               float cmG = MathHelper.clamp((cf1 + dayadd) * vertColorm, 0.0F, bound);
//               float cmB = MathHelper.clamp((cf2 + dayadd) * vertColorm, 0.0F, bound);
//               buffer.putColorMultiplier(cmR, cmG, cmB, cfi);
//            }
//         }
//
//         buffer.putPosition(d0, d1, d2);
//      }
//   }

//   public static boolean renderModelMaxSmooth(
//      IBlockAccess worldIn, IBakedModel modelIn, IBlockState stateIn, BlockPos posIn, BufferBuilder buffer, boolean checkSides, long rand
//   ) {
//      boolean flag = false;
//      float[] afloat = new float[EnumFacing.values().length * 2];
//      World world;
//      if (worldIn instanceof World) {
//         world = (World)worldIn;
//      } else {
//         world = Minecraft.getMinecraft().world;
//      }
//
//      long lig = world.getWorldTime() % 24000L;
//      BitSet bitset = new BitSet(3);
//      AmbientOcclusionFace blockmodelrenderer$ambientocclusionface = new AmbientOcclusionFace();
//      BlockPos down = posIn.add(0, -1, 0);
//      BlockPos up = posIn.add(0, 1, 0);
//      BlockPos north = posIn.add(0, 0, -1);
//      BlockPos south = posIn.add(0, 0, 1);
//      BlockPos west = posIn.add(-1, 0, 0);
//      BlockPos east = posIn.add(1, 0, 0);
//      BlockPos p0 = posIn.add(1, 0, 1);
//      BlockPos p1 = posIn.add(-1, 0, 1);
//      BlockPos p2 = posIn.add(1, 0, -1);
//      BlockPos p3 = posIn.add(-1, 0, -1);
//      BlockPos p4 = posIn.add(0, 1, 1);
//      BlockPos p5 = posIn.add(0, 1, -1);
//      BlockPos p6 = posIn.add(0, -1, 1);
//      BlockPos p7 = posIn.add(0, -1, -1);
//      BlockPos p8 = posIn.add(1, 1, 0);
//      BlockPos p9 = posIn.add(-1, 1, 0);
//      BlockPos p10 = posIn.add(1, -1, 0);
//      BlockPos p11 = posIn.add(-1, -1, 0);
//      BlockPos p12 = posIn.add(1, 1, 1);
//      BlockPos p13 = posIn.add(1, 1, -1);
//      BlockPos p14 = posIn.add(-1, 1, 1);
//      BlockPos p15 = posIn.add(-1, 1, -1);
//      BlockPos p16 = posIn.add(1, -1, 1);
//      BlockPos p17 = posIn.add(1, -1, -1);
//      BlockPos p18 = posIn.add(-1, -1, 1);
//      BlockPos p19 = posIn.add(-1, -1, -1);
//      Vec3d[] nbColors = new Vec3d[]{
//         ColoredLightning.getAdditiveColorInPos(p0),
//         ColoredLightning.getAdditiveColorInPos(p1),
//         ColoredLightning.getAdditiveColorInPos(p2),
//         ColoredLightning.getAdditiveColorInPos(p3),
//         ColoredLightning.getAdditiveColorInPos(p4),
//         ColoredLightning.getAdditiveColorInPos(p5),
//         ColoredLightning.getAdditiveColorInPos(p6),
//         ColoredLightning.getAdditiveColorInPos(p7),
//         ColoredLightning.getAdditiveColorInPos(p8),
//         ColoredLightning.getAdditiveColorInPos(p9),
//         ColoredLightning.getAdditiveColorInPos(p10),
//         ColoredLightning.getAdditiveColorInPos(p11),
//         ColoredLightning.getAdditiveColorInPos(p12),
//         ColoredLightning.getAdditiveColorInPos(p13),
//         ColoredLightning.getAdditiveColorInPos(p14),
//         ColoredLightning.getAdditiveColorInPos(p15),
//         ColoredLightning.getAdditiveColorInPos(p16),
//         ColoredLightning.getAdditiveColorInPos(p17),
//         ColoredLightning.getAdditiveColorInPos(p18),
//         ColoredLightning.getAdditiveColorInPos(p19),
//         ColoredLightning.getAdditiveColorInPos(down),
//         ColoredLightning.getAdditiveColorInPos(up),
//         ColoredLightning.getAdditiveColorInPos(north),
//         ColoredLightning.getAdditiveColorInPos(south),
//         ColoredLightning.getAdditiveColorInPos(west),
//         ColoredLightning.getAdditiveColorInPos(east)
//      };
//      boolean[] nbOpacity = new boolean[]{
//         worldIn.getBlockState(p0).getLightOpacity(worldIn, p0) == 0,
//         worldIn.getBlockState(p1).getLightOpacity(worldIn, p1) == 0,
//         worldIn.getBlockState(p2).getLightOpacity(worldIn, p2) == 0,
//         worldIn.getBlockState(p3).getLightOpacity(worldIn, p3) == 0,
//         worldIn.getBlockState(p4).getLightOpacity(worldIn, p4) == 0,
//         worldIn.getBlockState(p5).getLightOpacity(worldIn, p5) == 0,
//         worldIn.getBlockState(p6).getLightOpacity(worldIn, p6) == 0,
//         worldIn.getBlockState(p7).getLightOpacity(worldIn, p7) == 0,
//         worldIn.getBlockState(p8).getLightOpacity(worldIn, p8) == 0,
//         worldIn.getBlockState(p9).getLightOpacity(worldIn, p9) == 0,
//         worldIn.getBlockState(p10).getLightOpacity(worldIn, p10) == 0,
//         worldIn.getBlockState(p11).getLightOpacity(worldIn, p11) == 0,
//         worldIn.getBlockState(p12).getLightOpacity(worldIn, p12) == 0,
//         worldIn.getBlockState(p13).getLightOpacity(worldIn, p13) == 0,
//         worldIn.getBlockState(p14).getLightOpacity(worldIn, p14) == 0,
//         worldIn.getBlockState(p15).getLightOpacity(worldIn, p15) == 0,
//         worldIn.getBlockState(p16).getLightOpacity(worldIn, p16) == 0,
//         worldIn.getBlockState(p17).getLightOpacity(worldIn, p17) == 0,
//         worldIn.getBlockState(p18).getLightOpacity(worldIn, p18) == 0,
//         worldIn.getBlockState(p19).getLightOpacity(worldIn, p19) == 0
//      };
//
//      for (EnumFacing enumfacing : EnumFacing.values()) {
//         List<BakedQuad> list = modelIn.getQuads(stateIn, enumfacing, rand);
//         if (!list.isEmpty() && (!checkSides || stateIn.shouldSideBeRendered(worldIn, posIn, enumfacing))) {
//            renderQuadsMaxSmooth(worldIn, lig, nbColors, nbOpacity, stateIn, posIn, buffer, list, afloat, bitset, blockmodelrenderer$ambientocclusionface);
//            flag = true;
//         }
//      }
//
//      List<BakedQuad> list1 = modelIn.getQuads(stateIn, (EnumFacing)null, rand);
//      if (!list1.isEmpty()) {
//         renderQuadsMaxSmooth(worldIn, lig, nbColors, nbOpacity, stateIn, posIn, buffer, list1, afloat, bitset, blockmodelrenderer$ambientocclusionface);
//         flag = true;
//      }
//
//      return flag;
//   }

//   public static void renderQuadsMaxSmooth(
//      IBlockAccess blockAccessIn,
//      long dayNightLight,
//      Vec3d[] nbColors,
//      boolean[] nbOpacity,
//      IBlockState stateIn,
//      BlockPos posIn,
//      BufferBuilder buffer,
//      List<BakedQuad> list,
//      float[] quadBounds,
//      BitSet bitSet,
//      AmbientOcclusionFace aoFace
//   ) {
//      Vec3d vec3d = stateIn.getOffset(blockAccessIn, posIn);
//      double d0 = posIn.getX() + vec3d.x;
//      double d1 = posIn.getY() + vec3d.y;
//      double d2 = posIn.getZ() + vec3d.z;
//      int i = 0;
//
//      for (int j = list.size(); i < j; i++) {
//         BakedQuad bakedquad = list.get(i);
//         fillQuadBounds(stateIn, bakedquad.getVertexData(), bakedquad.getFace(), quadBounds, bitSet);
//         aoFace.updateVertexBrightness(blockAccessIn, stateIn, posIn, bakedquad.getFace(), quadBounds, bitSet);
//         buffer.addVertexData(bakedquad.getVertexData());
//         buffer.putBrightness4(aoFace.vertexBrightness[0], aoFace.vertexBrightness[1], aoFace.vertexBrightness[2], aoFace.vertexBrightness[3]);
//         if (bakedquad.shouldApplyDiffuseLighting()) {
//            float diffuse = LightUtil.diffuseLight(bakedquad.getFace());
//            aoFace.vertexColorMultiplier[0] = aoFace.vertexColorMultiplier[0] * diffuse;
//            aoFace.vertexColorMultiplier[1] = aoFace.vertexColorMultiplier[1] * diffuse;
//            aoFace.vertexColorMultiplier[2] = aoFace.vertexColorMultiplier[2] * diffuse;
//            aoFace.vertexColorMultiplier[3] = aoFace.vertexColorMultiplier[3] * diffuse;
//         }
//
//         if (bakedquad.hasTintIndex()) {
//            int k = blockColors.colorMultiplier(stateIn, blockAccessIn, posIn, bakedquad.getTintIndex());
//            if (EntityRenderer.anaglyphEnable) {
//               k = TextureUtil.anaglyphColor(k);
//            }
//
//            float f = (k >> 16 & 0xFF) / 255.0F;
//            float f1 = (k >> 8 & 0xFF) / 255.0F;
//            float f2 = (k & 0xFF) / 255.0F;
//            float brpow = (float)Math.pow(f, blockcolorIntensity);
//            float brpow1 = (float)Math.pow(f1, blockcolorIntensity);
//            float brpow2 = (float)Math.pow(f2, blockcolorIntensity);
//            float diffuse = 1.0F;
//
//            for (int cfi = 4; cfi >= 1; cfi--) {
//               int cfn = 4 - cfi;
//               float dayadd = 0.0F;
//               int brightnessX = ColorConverters.UnpackLightmapCoordsX(aoFace.vertexBrightness[cfn]);
//               float minimalbrightness = 0.3F;
//               if (dayNightLight >= 0L && dayNightLight < 1500L) {
//                  dayadd = ((float)dayNightLight / 2142.0F + minimalbrightness) * brightnessX / 240.0F;
//               } else if (dayNightLight >= 1500L && dayNightLight < 12000L) {
//                  dayadd = 1.0F * brightnessX / 240.0F;
//               } else if (dayNightLight >= 12000L && dayNightLight < 13500L) {
//                  dayadd = (minimalbrightness + (float)(1500L - (dayNightLight - 12000L)) / 2142.0F) * brightnessX / 240.0F;
//               } else {
//                  dayadd = minimalbrightness * brightnessX / 240.0F;
//               }
//
//               Vec3d colort = getNbColor(bakedquad, cfi, nbColors);
//               float cf = (float)colort.x;
//               float cf1 = (float)colort.y;
//               float cf2 = (float)colort.z;
//               float vertColorm = aoFace.vertexColorMultiplier[cfn];
//               float cmR = MathHelper.clamp((dayadd * f + cf * brpow) * vertColorm, 0.0F, brpow);
//               float cmG = MathHelper.clamp((dayadd * f1 + cf1 * brpow1) * vertColorm, 0.0F, brpow1);
//               float cmB = MathHelper.clamp((dayadd * f2 + cf2 * brpow2) * vertColorm, 0.0F, brpow2);
//               buffer.putColorMultiplier(cmR, cmG, cmB, cfi);
//            }
//         } else {
//            float diffuse = 1.0F;
//
//            for (int cfi = 4; cfi >= 1; cfi--) {
//               int cfn = 4 - cfi;
//               float dayadd = 0.0F;
//               int brightnessX = ColorConverters.UnpackLightmapCoordsX(aoFace.vertexBrightness[cfn]);
//               float minimalbrightness = 0.3F;
//               if (dayNightLight >= 0L && dayNightLight < 1500L) {
//                  dayadd = ((float)dayNightLight / 2142.0F + minimalbrightness) * brightnessX / 240.0F;
//               } else if (dayNightLight >= 1500L && dayNightLight < 12000L) {
//                  dayadd = 1.0F * brightnessX / 240.0F;
//               } else if (dayNightLight >= 12000L && dayNightLight < 13500L) {
//                  dayadd = (minimalbrightness + (float)(1500L - (dayNightLight - 12000L)) / 2142.0F) * brightnessX / 240.0F;
//               } else {
//                  dayadd = minimalbrightness * brightnessX / 240.0F;
//               }
//
//               Vec3d colort = getNbColorMaxSmooth(bakedquad, cfi, nbColors, nbOpacity);
//               float cf = (float)colort.x;
//               float cf1 = (float)colort.y;
//               float cf2 = (float)colort.z;
//               float bound = Math.min(diffuse + Math.max(cf + cf1 + cf2 - 1.5F, 0.0F), 1.0F);
//               float vertColorm = aoFace.vertexColorMultiplier[cfn];
//               float cmR = MathHelper.clamp((cf + dayadd) * vertColorm, 0.0F, bound);
//               float cmG = MathHelper.clamp((cf1 + dayadd) * vertColorm, 0.0F, bound);
//               float cmB = MathHelper.clamp((cf2 + dayadd) * vertColorm, 0.0F, bound);
//               buffer.putColorMultiplier(cmR, cmG, cmB, cfi);
//            }
//         }
//
//         buffer.putPosition(d0, d1, d2);
//      }
//   }

   public static Vec3d fixedNoDark(Vec3d check, Vec3d[] nbColors, EnumFacing facing) {
      return check.x == 0.0 && check.y == 0.0 && check.z == 0.0 ? nbColors[facing.getIndex() + 12] : check;
   }

//   public static Vec3d getNbColor(BakedQuad bakedquad, int buffervertex, Vec3d[] nbColors) {
//      switch (bakedquad.getFace()) {
//         case UP:
//            switch (buffervertex) {
//               case 1:
//                  return ColorConverters.mix(fixedNoDark(nbColors[8], nbColors, EnumFacing.UP), fixedNoDark(nbColors[5], nbColors, EnumFacing.UP));
//               case 2:
//                  return ColorConverters.mix(fixedNoDark(nbColors[4], nbColors, EnumFacing.UP), fixedNoDark(nbColors[8], nbColors, EnumFacing.UP));
//               case 3:
//                  return ColorConverters.mix(fixedNoDark(nbColors[4], nbColors, EnumFacing.UP), fixedNoDark(nbColors[9], nbColors, EnumFacing.UP));
//               case 4:
//                  return ColorConverters.mix(fixedNoDark(nbColors[5], nbColors, EnumFacing.UP), fixedNoDark(nbColors[9], nbColors, EnumFacing.UP));
//               default:
//                  return new Vec3d(0.0, 0.0, 0.0);
//            }
//         case DOWN:
//            switch (buffervertex) {
//               case 1:
//                  return ColorConverters.mix(fixedNoDark(nbColors[10], nbColors, EnumFacing.DOWN), fixedNoDark(nbColors[6], nbColors, EnumFacing.DOWN));
//               case 2:
//                  return ColorConverters.mix(fixedNoDark(nbColors[7], nbColors, EnumFacing.DOWN), fixedNoDark(nbColors[10], nbColors, EnumFacing.DOWN));
//               case 3:
//                  return ColorConverters.mix(fixedNoDark(nbColors[11], nbColors, EnumFacing.DOWN), fixedNoDark(nbColors[7], nbColors, EnumFacing.DOWN));
//               case 4:
//                  return ColorConverters.mix(fixedNoDark(nbColors[6], nbColors, EnumFacing.DOWN), fixedNoDark(nbColors[11], nbColors, EnumFacing.DOWN));
//               default:
//                  return new Vec3d(0.0, 0.0, 0.0);
//            }
//         case EAST:
//            switch (buffervertex) {
//               case 1:
//                  return ColorConverters.mix(fixedNoDark(nbColors[8], nbColors, EnumFacing.EAST), fixedNoDark(nbColors[2], nbColors, EnumFacing.EAST));
//               case 2:
//                  return ColorConverters.mix(fixedNoDark(nbColors[10], nbColors, EnumFacing.EAST), fixedNoDark(nbColors[2], nbColors, EnumFacing.EAST));
//               case 3:
//                  return ColorConverters.mix(fixedNoDark(nbColors[0], nbColors, EnumFacing.EAST), fixedNoDark(nbColors[10], nbColors, EnumFacing.EAST));
//               case 4:
//                  return ColorConverters.mix(fixedNoDark(nbColors[0], nbColors, EnumFacing.EAST), fixedNoDark(nbColors[8], nbColors, EnumFacing.EAST));
//               default:
//                  return new Vec3d(0.0, 0.0, 0.0);
//            }
//         case NORTH:
//            switch (buffervertex) {
//               case 1:
//                  return ColorConverters.mix(fixedNoDark(nbColors[5], nbColors, EnumFacing.NORTH), fixedNoDark(nbColors[3], nbColors, EnumFacing.NORTH));
//               case 2:
//                  return ColorConverters.mix(fixedNoDark(nbColors[7], nbColors, EnumFacing.NORTH), fixedNoDark(nbColors[3], nbColors, EnumFacing.NORTH));
//               case 3:
//                  return ColorConverters.mix(fixedNoDark(nbColors[2], nbColors, EnumFacing.NORTH), fixedNoDark(nbColors[7], nbColors, EnumFacing.NORTH));
//               case 4:
//                  return ColorConverters.mix(fixedNoDark(nbColors[2], nbColors, EnumFacing.NORTH), fixedNoDark(nbColors[5], nbColors, EnumFacing.NORTH));
//               default:
//                  return new Vec3d(0.0, 0.0, 0.0);
//            }
//         case SOUTH:
//            switch (buffervertex) {
//               case 1:
//                  return ColorConverters.mix(fixedNoDark(nbColors[0], nbColors, EnumFacing.SOUTH), fixedNoDark(nbColors[4], nbColors, EnumFacing.SOUTH));
//               case 2:
//                  return ColorConverters.mix(fixedNoDark(nbColors[0], nbColors, EnumFacing.SOUTH), fixedNoDark(nbColors[6], nbColors, EnumFacing.SOUTH));
//               case 3:
//                  return ColorConverters.mix(fixedNoDark(nbColors[1], nbColors, EnumFacing.SOUTH), fixedNoDark(nbColors[6], nbColors, EnumFacing.SOUTH));
//               case 4:
//                  return ColorConverters.mix(fixedNoDark(nbColors[4], nbColors, EnumFacing.SOUTH), fixedNoDark(nbColors[1], nbColors, EnumFacing.SOUTH));
//               default:
//                  return new Vec3d(0.0, 0.0, 0.0);
//            }
//         case WEST:
//            switch (buffervertex) {
//               case 1:
//                  return ColorConverters.mix(fixedNoDark(nbColors[1], nbColors, EnumFacing.WEST), fixedNoDark(nbColors[9], nbColors, EnumFacing.WEST));
//               case 2:
//                  return ColorConverters.mix(fixedNoDark(nbColors[11], nbColors, EnumFacing.WEST), fixedNoDark(nbColors[1], nbColors, EnumFacing.WEST));
//               case 3:
//                  return ColorConverters.mix(fixedNoDark(nbColors[3], nbColors, EnumFacing.WEST), fixedNoDark(nbColors[11], nbColors, EnumFacing.WEST));
//               case 4:
//                  return ColorConverters.mix(fixedNoDark(nbColors[9], nbColors, EnumFacing.WEST), fixedNoDark(nbColors[3], nbColors, EnumFacing.WEST));
//            }
//      }
//
//      return new Vec3d(0.0, 0.0, 0.0);
//   }

   public static final boolean isVec3dNull(Vec3d vec) {
      return vec.x == 0.0 && vec.y == 0.0 && vec.z == 0.0;
   }

   public static Vec3d fixedNoDarkMxSm(int chec, Vec3d[] nbColors, EnumFacing facing, boolean[] nbOpacity) {
      Vec3d check = nbColors[chec];
      return nbOpacity[chec] ? check : nbColors[facing.getIndex() + 20];
   }

//   public static Vec3d getNbColorMaxSmooth(BakedQuad bakedquad, int buffervertex, Vec3d[] nbColors, boolean[] nbOpacity) {
//      switch (bakedquad.getFace()) {
//         case UP:
//            switch (buffervertex) {
//               case 1:
//                  if (!nbOpacity[5] && !nbOpacity[8]) {
//                     return nbColors[21];
//                  }
//
//                  return ColorConverters.mix(
//                     fixedNoDarkMxSm(8, nbColors, EnumFacing.UP, nbOpacity),
//                     fixedNoDarkMxSm(5, nbColors, EnumFacing.UP, nbOpacity),
//                     fixedNoDarkMxSm(13, nbColors, EnumFacing.UP, nbOpacity),
//                     nbColors[21]
//                  );
//               case 2:
//                  if (!nbOpacity[4] && !nbOpacity[8]) {
//                     return nbColors[21];
//                  }
//
//                  return ColorConverters.mix(
//                     fixedNoDarkMxSm(4, nbColors, EnumFacing.UP, nbOpacity),
//                     fixedNoDarkMxSm(8, nbColors, EnumFacing.UP, nbOpacity),
//                     fixedNoDarkMxSm(12, nbColors, EnumFacing.UP, nbOpacity),
//                     nbColors[21]
//                  );
//               case 3:
//                  if (!nbOpacity[4] && !nbOpacity[9]) {
//                     return nbColors[21];
//                  }
//
//                  return ColorConverters.mix(
//                     fixedNoDarkMxSm(4, nbColors, EnumFacing.UP, nbOpacity),
//                     fixedNoDarkMxSm(9, nbColors, EnumFacing.UP, nbOpacity),
//                     fixedNoDarkMxSm(14, nbColors, EnumFacing.UP, nbOpacity),
//                     nbColors[21]
//                  );
//               case 4:
//                  if (!nbOpacity[5] && !nbOpacity[9]) {
//                     return nbColors[21];
//                  }
//
//                  return ColorConverters.mix(
//                     fixedNoDarkMxSm(5, nbColors, EnumFacing.UP, nbOpacity),
//                     fixedNoDarkMxSm(9, nbColors, EnumFacing.UP, nbOpacity),
//                     fixedNoDarkMxSm(15, nbColors, EnumFacing.UP, nbOpacity),
//                     nbColors[21]
//                  );
//               default:
//                  return new Vec3d(0.0, 0.0, 0.0);
//            }
//         case DOWN:
//            switch (buffervertex) {
//               case 1:
//                  if (!nbOpacity[10] && !nbOpacity[6]) {
//                     return nbColors[20];
//                  }
//
//                  return ColorConverters.mix(
//                     fixedNoDarkMxSm(6, nbColors, EnumFacing.DOWN, nbOpacity),
//                     fixedNoDarkMxSm(10, nbColors, EnumFacing.DOWN, nbOpacity),
//                     fixedNoDarkMxSm(16, nbColors, EnumFacing.DOWN, nbOpacity),
//                     nbColors[20]
//                  );
//               case 2:
//                  if (!nbOpacity[7] && !nbOpacity[10]) {
//                     return nbColors[20];
//                  }
//
//                  return ColorConverters.mix(
//                     fixedNoDarkMxSm(7, nbColors, EnumFacing.DOWN, nbOpacity),
//                     fixedNoDarkMxSm(10, nbColors, EnumFacing.DOWN, nbOpacity),
//                     fixedNoDarkMxSm(17, nbColors, EnumFacing.DOWN, nbOpacity),
//                     nbColors[20]
//                  );
//               case 3:
//                  if (!nbOpacity[11] && !nbOpacity[7]) {
//                     return nbColors[20];
//                  }
//
//                  return ColorConverters.mix(
//                     fixedNoDarkMxSm(7, nbColors, EnumFacing.DOWN, nbOpacity),
//                     fixedNoDarkMxSm(11, nbColors, EnumFacing.DOWN, nbOpacity),
//                     fixedNoDarkMxSm(19, nbColors, EnumFacing.DOWN, nbOpacity),
//                     nbColors[20]
//                  );
//               case 4:
//                  if (!nbOpacity[6] && !nbOpacity[11]) {
//                     return nbColors[20];
//                  }
//
//                  return ColorConverters.mix(
//                     fixedNoDarkMxSm(6, nbColors, EnumFacing.DOWN, nbOpacity),
//                     fixedNoDarkMxSm(11, nbColors, EnumFacing.DOWN, nbOpacity),
//                     fixedNoDarkMxSm(18, nbColors, EnumFacing.DOWN, nbOpacity),
//                     nbColors[20]
//                  );
//               default:
//                  return new Vec3d(0.0, 0.0, 0.0);
//            }
//         case EAST:
//            switch (buffervertex) {
//               case 1:
//                  if (!nbOpacity[8] && !nbOpacity[2]) {
//                     return nbColors[25];
//                  }
//
//                  return ColorConverters.mix(
//                     fixedNoDarkMxSm(8, nbColors, EnumFacing.EAST, nbOpacity),
//                     fixedNoDarkMxSm(2, nbColors, EnumFacing.EAST, nbOpacity),
//                     fixedNoDarkMxSm(13, nbColors, EnumFacing.EAST, nbOpacity),
//                     nbColors[25]
//                  );
//               case 2:
//                  if (!nbOpacity[10] && !nbOpacity[2]) {
//                     return nbColors[25];
//                  }
//
//                  return ColorConverters.mix(
//                     fixedNoDarkMxSm(10, nbColors, EnumFacing.EAST, nbOpacity),
//                     fixedNoDarkMxSm(2, nbColors, EnumFacing.EAST, nbOpacity),
//                     fixedNoDarkMxSm(17, nbColors, EnumFacing.EAST, nbOpacity),
//                     nbColors[25]
//                  );
//               case 3:
//                  if (!nbOpacity[0] && !nbOpacity[10]) {
//                     return nbColors[25];
//                  }
//
//                  return ColorConverters.mix(
//                     fixedNoDarkMxSm(0, nbColors, EnumFacing.EAST, nbOpacity),
//                     fixedNoDarkMxSm(10, nbColors, EnumFacing.EAST, nbOpacity),
//                     fixedNoDarkMxSm(16, nbColors, EnumFacing.EAST, nbOpacity),
//                     nbColors[25]
//                  );
//               case 4:
//                  if (!nbOpacity[0] && !nbOpacity[8]) {
//                     return nbColors[25];
//                  }
//
//                  return ColorConverters.mix(
//                     fixedNoDarkMxSm(0, nbColors, EnumFacing.EAST, nbOpacity),
//                     fixedNoDarkMxSm(8, nbColors, EnumFacing.EAST, nbOpacity),
//                     fixedNoDarkMxSm(12, nbColors, EnumFacing.EAST, nbOpacity),
//                     nbColors[25]
//                  );
//               default:
//                  return new Vec3d(0.0, 0.0, 0.0);
//            }
//         case NORTH:
//            switch (buffervertex) {
//               case 1:
//                  if (!nbOpacity[5] && !nbOpacity[3]) {
//                     return nbColors[22];
//                  }
//
//                  return ColorConverters.mix(
//                     fixedNoDarkMxSm(5, nbColors, EnumFacing.NORTH, nbOpacity),
//                     fixedNoDarkMxSm(3, nbColors, EnumFacing.NORTH, nbOpacity),
//                     fixedNoDarkMxSm(15, nbColors, EnumFacing.NORTH, nbOpacity),
//                     nbColors[22]
//                  );
//               case 2:
//                  if (!nbOpacity[7] && !nbOpacity[3]) {
//                     return nbColors[22];
//                  }
//
//                  return ColorConverters.mix(
//                     fixedNoDarkMxSm(7, nbColors, EnumFacing.NORTH, nbOpacity),
//                     fixedNoDarkMxSm(3, nbColors, EnumFacing.NORTH, nbOpacity),
//                     fixedNoDarkMxSm(19, nbColors, EnumFacing.NORTH, nbOpacity),
//                     nbColors[22]
//                  );
//               case 3:
//                  if (!nbOpacity[2] && !nbOpacity[7]) {
//                     return nbColors[22];
//                  }
//
//                  return ColorConverters.mix(
//                     fixedNoDarkMxSm(2, nbColors, EnumFacing.NORTH, nbOpacity),
//                     fixedNoDarkMxSm(7, nbColors, EnumFacing.NORTH, nbOpacity),
//                     fixedNoDarkMxSm(17, nbColors, EnumFacing.NORTH, nbOpacity),
//                     nbColors[22]
//                  );
//               case 4:
//                  if (!nbOpacity[2] && !nbOpacity[5]) {
//                     return nbColors[22];
//                  }
//
//                  return ColorConverters.mix(
//                     fixedNoDarkMxSm(2, nbColors, EnumFacing.NORTH, nbOpacity),
//                     fixedNoDarkMxSm(5, nbColors, EnumFacing.NORTH, nbOpacity),
//                     fixedNoDarkMxSm(13, nbColors, EnumFacing.NORTH, nbOpacity),
//                     nbColors[22]
//                  );
//               default:
//                  return new Vec3d(0.0, 0.0, 0.0);
//            }
//         case SOUTH:
//            switch (buffervertex) {
//               case 1:
//                  if (!nbOpacity[0] && !nbOpacity[4]) {
//                     return nbColors[23];
//                  }
//
//                  return ColorConverters.mix(
//                     fixedNoDarkMxSm(0, nbColors, EnumFacing.SOUTH, nbOpacity),
//                     fixedNoDarkMxSm(4, nbColors, EnumFacing.SOUTH, nbOpacity),
//                     fixedNoDarkMxSm(12, nbColors, EnumFacing.SOUTH, nbOpacity),
//                     nbColors[23]
//                  );
//               case 2:
//                  if (!nbOpacity[0] && !nbOpacity[6]) {
//                     return nbColors[23];
//                  }
//
//                  return ColorConverters.mix(
//                     fixedNoDarkMxSm(0, nbColors, EnumFacing.SOUTH, nbOpacity),
//                     fixedNoDarkMxSm(6, nbColors, EnumFacing.SOUTH, nbOpacity),
//                     fixedNoDarkMxSm(16, nbColors, EnumFacing.SOUTH, nbOpacity),
//                     nbColors[23]
//                  );
//               case 3:
//                  if (!nbOpacity[1] && !nbOpacity[6]) {
//                     return nbColors[23];
//                  }
//
//                  return ColorConverters.mix(
//                     fixedNoDarkMxSm(1, nbColors, EnumFacing.SOUTH, nbOpacity),
//                     fixedNoDarkMxSm(6, nbColors, EnumFacing.SOUTH, nbOpacity),
//                     fixedNoDarkMxSm(18, nbColors, EnumFacing.SOUTH, nbOpacity),
//                     nbColors[23]
//                  );
//               case 4:
//                  if (!nbOpacity[4] && !nbOpacity[1]) {
//                     return nbColors[23];
//                  }
//
//                  return ColorConverters.mix(
//                     fixedNoDarkMxSm(4, nbColors, EnumFacing.SOUTH, nbOpacity),
//                     fixedNoDarkMxSm(1, nbColors, EnumFacing.SOUTH, nbOpacity),
//                     fixedNoDarkMxSm(14, nbColors, EnumFacing.SOUTH, nbOpacity),
//                     nbColors[23]
//                  );
//               default:
//                  return new Vec3d(0.0, 0.0, 0.0);
//            }
//         case WEST:
//            switch (buffervertex) {
//               case 1:
//                  if (!nbOpacity[9] && !nbOpacity[1]) {
//                     return nbColors[24];
//                  }
//
//                  return ColorConverters.mix(
//                     fixedNoDarkMxSm(9, nbColors, EnumFacing.WEST, nbOpacity),
//                     fixedNoDarkMxSm(1, nbColors, EnumFacing.WEST, nbOpacity),
//                     fixedNoDarkMxSm(14, nbColors, EnumFacing.WEST, nbOpacity),
//                     nbColors[24]
//                  );
//               case 2:
//                  if (!nbOpacity[11] && !nbOpacity[1]) {
//                     return nbColors[24];
//                  }
//
//                  return ColorConverters.mix(
//                     fixedNoDarkMxSm(11, nbColors, EnumFacing.WEST, nbOpacity),
//                     fixedNoDarkMxSm(1, nbColors, EnumFacing.WEST, nbOpacity),
//                     fixedNoDarkMxSm(18, nbColors, EnumFacing.WEST, nbOpacity),
//                     nbColors[24]
//                  );
//               case 3:
//                  if (!nbOpacity[3] && !nbOpacity[11]) {
//                     return nbColors[24];
//                  }
//
//                  return ColorConverters.mix(
//                     fixedNoDarkMxSm(3, nbColors, EnumFacing.WEST, nbOpacity),
//                     fixedNoDarkMxSm(11, nbColors, EnumFacing.WEST, nbOpacity),
//                     fixedNoDarkMxSm(19, nbColors, EnumFacing.WEST, nbOpacity),
//                     nbColors[24]
//                  );
//               case 4:
//                  if (!nbOpacity[9] && !nbOpacity[3]) {
//                     return nbColors[24];
//                  }
//
//                  return ColorConverters.mix(
//                     fixedNoDarkMxSm(9, nbColors, EnumFacing.WEST, nbOpacity),
//                     fixedNoDarkMxSm(3, nbColors, EnumFacing.WEST, nbOpacity),
//                     fixedNoDarkMxSm(15, nbColors, EnumFacing.WEST, nbOpacity),
//                     nbColors[24]
//                  );
//            }
//      }
//
//      return new Vec3d(0.0, 0.0, 0.0);
//   }

//   public static long getNbColorMaxSmooth1(BakedQuad bakedquad, int buffervertex, long[] nbColors) {
//      switch (bakedquad.getFace()) {
//         case UP:
//            switch (buffervertex) {
//               case 1:
//                  return ColorConverters.mix(nbColors[8], nbColors[5], nbColors[21]);
//               case 2:
//                  return ColorConverters.mix(nbColors[4], nbColors[8], nbColors[21]);
//               case 3:
//                  return ColorConverters.mix(nbColors[4], nbColors[9], nbColors[21]);
//               case 4:
//                  return ColorConverters.mix(nbColors[5], nbColors[9], nbColors[21]);
//               default:
//                  return 0L;
//            }
//         case DOWN:
//            switch (buffervertex) {
//               case 1:
//                  return ColorConverters.mix(nbColors[10], nbColors[6], nbColors[24]);
//               case 2:
//                  return ColorConverters.mix(nbColors[7], nbColors[10], nbColors[24]);
//               case 3:
//                  return ColorConverters.mix(nbColors[11], nbColors[7], nbColors[24]);
//               case 4:
//                  return ColorConverters.mix(nbColors[6], nbColors[11], nbColors[24]);
//               default:
//                  return 0L;
//            }
//         case EAST:
//            switch (buffervertex) {
//               case 1:
//                  return ColorConverters.mix(nbColors[8], nbColors[2], nbColors[20]);
//               case 2:
//                  return ColorConverters.mix(nbColors[10], nbColors[2], nbColors[20]);
//               case 3:
//                  return ColorConverters.mix(nbColors[0], nbColors[10], nbColors[20]);
//               case 4:
//                  return ColorConverters.mix(nbColors[0], nbColors[8], nbColors[20]);
//               default:
//                  return 0L;
//            }
//         case NORTH:
//            switch (buffervertex) {
//               case 1:
//                  return ColorConverters.mix(nbColors[5], nbColors[3], nbColors[25]);
//               case 2:
//                  return ColorConverters.mix(nbColors[7], nbColors[3], nbColors[25]);
//               case 3:
//                  return ColorConverters.mix(nbColors[2], nbColors[7], nbColors[25]);
//               case 4:
//                  return ColorConverters.mix(nbColors[2], nbColors[5], nbColors[25]);
//               default:
//                  return 0L;
//            }
//         case SOUTH:
//            switch (buffervertex) {
//               case 1:
//                  return ColorConverters.mix(nbColors[0], nbColors[4], nbColors[22]);
//               case 2:
//                  return ColorConverters.mix(nbColors[0], nbColors[6], nbColors[22]);
//               case 3:
//                  return ColorConverters.mix(nbColors[1], nbColors[6], nbColors[22]);
//               case 4:
//                  return ColorConverters.mix(nbColors[4], nbColors[1], nbColors[22]);
//               default:
//                  return 0L;
//            }
//         case WEST:
//            switch (buffervertex) {
//               case 1:
//                  return ColorConverters.mix(nbColors[1], nbColors[9], nbColors[23]);
//               case 2:
//                  return ColorConverters.mix(nbColors[11], nbColors[1], nbColors[23]);
//               case 3:
//                  return ColorConverters.mix(nbColors[3], nbColors[11], nbColors[23]);
//               case 4:
//                  return ColorConverters.mix(nbColors[9], nbColors[3], nbColors[23]);
//            }
//      }
//
//      return 0L;
//   }

   public static void fillQuadBounds(IBlockState stateIn, int[] vertexData, EnumFacing face, @Nullable float[] quadBounds, BitSet boundsFlags) {
      float f = 32.0F;
      float f1 = 32.0F;
      float f2 = 32.0F;
      float f3 = -32.0F;
      float f4 = -32.0F;
      float f5 = -32.0F;

      for (int i = 0; i < 4; i++) {
         float f6 = Float.intBitsToFloat(vertexData[i * 7]);
         float f7 = Float.intBitsToFloat(vertexData[i * 7 + 1]);
         float f8 = Float.intBitsToFloat(vertexData[i * 7 + 2]);
         f = Math.min(f, f6);
         f1 = Math.min(f1, f7);
         f2 = Math.min(f2, f8);
         f3 = Math.max(f3, f6);
         f4 = Math.max(f4, f7);
         f5 = Math.max(f5, f8);
      }

      if (quadBounds != null) {
         quadBounds[EnumFacing.WEST.getIndex()] = f;
         quadBounds[EnumFacing.EAST.getIndex()] = f3;
         quadBounds[EnumFacing.DOWN.getIndex()] = f1;
         quadBounds[EnumFacing.UP.getIndex()] = f4;
         quadBounds[EnumFacing.NORTH.getIndex()] = f2;
         quadBounds[EnumFacing.SOUTH.getIndex()] = f5;
         int j = EnumFacing.values().length;
         quadBounds[EnumFacing.WEST.getIndex() + j] = 1.0F - f;
         quadBounds[EnumFacing.EAST.getIndex() + j] = 1.0F - f3;
         quadBounds[EnumFacing.DOWN.getIndex() + j] = 1.0F - f1;
         quadBounds[EnumFacing.UP.getIndex() + j] = 1.0F - f4;
         quadBounds[EnumFacing.NORTH.getIndex() + j] = 1.0F - f2;
         quadBounds[EnumFacing.SOUTH.getIndex() + j] = 1.0F - f5;
      }

      float f9 = 1.0E-4F;
      float f10 = 0.9999F;
      switch (face) {
         case UP:
            boundsFlags.set(1, f >= 1.0E-4F || f2 >= 1.0E-4F || f3 <= 0.9999F || f5 <= 0.9999F);
            boundsFlags.set(0, (f4 > 0.9999F || stateIn.isFullCube()) && f1 == f4);
            break;
         case DOWN:
            boundsFlags.set(1, f >= 1.0E-4F || f2 >= 1.0E-4F || f3 <= 0.9999F || f5 <= 0.9999F);
            boundsFlags.set(0, (f1 < 1.0E-4F || stateIn.isFullCube()) && f1 == f4);
            break;
         case EAST:
            boundsFlags.set(1, f1 >= 1.0E-4F || f2 >= 1.0E-4F || f4 <= 0.9999F || f5 <= 0.9999F);
            boundsFlags.set(0, (f3 > 0.9999F || stateIn.isFullCube()) && f == f3);
            break;
         case NORTH:
            boundsFlags.set(1, f >= 1.0E-4F || f1 >= 1.0E-4F || f3 <= 0.9999F || f4 <= 0.9999F);
            boundsFlags.set(0, (f2 < 1.0E-4F || stateIn.isFullCube()) && f2 == f5);
            break;
         case SOUTH:
            boundsFlags.set(1, f >= 1.0E-4F || f1 >= 1.0E-4F || f3 <= 0.9999F || f4 <= 0.9999F);
            boundsFlags.set(0, (f5 > 0.9999F || stateIn.isFullCube()) && f2 == f5);
            break;
         case WEST:
            boundsFlags.set(1, f1 >= 1.0E-4F || f2 >= 1.0E-4F || f4 <= 0.9999F || f5 <= 0.9999F);
            boundsFlags.set(0, (f < 1.0E-4F || stateIn.isFullCube()) && f == f3);
      }
   }
}
