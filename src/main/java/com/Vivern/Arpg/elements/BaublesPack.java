//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import baubles.api.render.IRenderBauble;
import com.Vivern.Arpg.elements.armor.IItemDamaging;
import com.Vivern.Arpg.elements.armor.IItemHurted;
import com.Vivern.Arpg.elements.armor.IItemKilling;
import com.Vivern.Arpg.elements.models.AntiRadPackModel;
import com.Vivern.Arpg.elements.models.EnigmateTwinsModel;
import com.Vivern.Arpg.elements.models.GasmaskModel;
import com.Vivern.Arpg.entity.EnigmateTwinsShoot;
import com.Vivern.Arpg.entity.EntityCoin;
import com.Vivern.Arpg.entity.EntityLiveHeart;
import com.Vivern.Arpg.entity.ThornkeeperShoot;
import com.Vivern.Arpg.main.Booom;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.IAttributedBauble;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Keys;
import com.Vivern.Arpg.main.Mana;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.PropertiesRegistry;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.SuperKnockback;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.mobs.ToxicomaniaMobsPack;
import com.Vivern.Arpg.network.PacketDoSomethingToClients;
import com.Vivern.Arpg.network.PacketHandler;
import com.Vivern.Arpg.potions.PotionEffects;
import com.Vivern.Arpg.renders.GUNParticle;
import com.Vivern.Arpg.renders.HandBaubleRender;
import com.Vivern.Arpg.renders.ParticleTracker;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BaublesPack {
   public static ResourceLocation voidexplode = new ResourceLocation("arpg:textures/void_explode.png");

   public static class AntiRadPack extends Item implements IBauble, IRenderBauble {
      public static AntiRadPackModel model = new AntiRadPackModel();
      public static ResourceLocation tex = new ResourceLocation("arpg:textures/antirad_pack_model_tex.png");

      public AntiRadPack() {
         this.setRegistryName("antirad_pack");
         this.setCreativeTab(CreativeTabs.COMBAT);
         this.setTranslationKey("antirad_pack");
         this.setMaxStackSize(1);
      }

      @Override
      public BaubleType getBaubleType(ItemStack itemstack) {
         return BaubleType.BODY;
      }

      @Override
      public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
         if (player.ticksExisted % 5 == 0 && player instanceof EntityPlayer) {
            Mana.addRad((EntityPlayer)player, -2, false);
         }
      }

      @Override
      public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, RenderType type, float partialTicks) {
         if (type == RenderType.BODY) {
            GlStateManager.pushMatrix();
            GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
            Helper.rotateIfSneaking(player);
            if (player.isSneaking()) {
               GlStateManager.translate(0.0F, -0.4F, 0.2F);
            }

            Minecraft.getMinecraft().renderEngine.bindTexture(tex);
            GlStateManager.scale(0.08F, 0.08F, 0.08F);
            GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.translate(0.0F, -0.5F, -0.5F);
            model.render(player, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.popMatrix();
         }
      }

      public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
         tooltip.add("Faster decreases rad and gives 75% resistance from biome radiation");
         super.addInformation(stack, worldIn, tooltip, flagIn);
      }
   }

   public static class BaubleAttributed extends Item implements IBauble, IAttributedBauble, IRenderBauble {
      public BaubleType btype;
      public IAttribute attribute;
      public String text;
      public double value;
      public int operation;
      public boolean render = false;
      public int rendertype;

      public BaubleAttributed(String name, CreativeTabs tab, IAttribute attribute, double value, int operation, BaubleType btype, String text) {
         this.setRegistryName(name);
         this.setCreativeTab(tab);
         this.setTranslationKey(name);
         this.setMaxStackSize(1);
         this.btype = btype;
         this.attribute = attribute;
         this.text = text;
         this.value = value;
         this.operation = operation;
      }

      public BaubleAttributed setRender(int rendertype) {
         this.rendertype = rendertype;
         this.render = true;
         return this;
      }

      @Override
      public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, RenderType type, float partialTicks) {
         if (this.render) {
            if (this.rendertype == 1 && type == RenderType.BODY) {
               GlStateManager.pushMatrix();
               GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
               Helper.rotateIfSneaking(player);
               Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
               Minecraft.getMinecraft().getRenderItem().renderItem(stack, TransformType.HEAD);
               GlStateManager.popMatrix();
            }

            if (this.rendertype == 2 && type == RenderType.HEAD) {
               GlStateManager.pushMatrix();
               GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
               Helper.rotateIfSneaking(player);
               Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
               Minecraft.getMinecraft().getRenderItem().renderItem(stack, TransformType.HEAD);
               GlStateManager.popMatrix();
            }

            if (this.rendertype == 3 && type == RenderType.BODY) {
               GlStateManager.pushMatrix();
               HandBaubleRender.doRenderLayer(player, stack, EnumHandSide.RIGHT);
               GlStateManager.popMatrix();
            }

            if (this.rendertype == 4 && type == RenderType.BODY) {
               GlStateManager.pushMatrix();
               HandBaubleRender.doRenderLayer(player, stack, EnumHandSide.LEFT);
               GlStateManager.popMatrix();
            }

            if (this.rendertype == 5 && type == RenderType.BODY) {
               GlStateManager.pushMatrix();
               if (BaublesApi.getBaubles(player).getStackInSlot(1) == stack) {
                  HandBaubleRender.doRenderLayer(player, stack, EnumHandSide.RIGHT, TransformType.THIRD_PERSON_RIGHT_HAND);
               } else if (BaublesApi.getBaubles(player).getStackInSlot(2) == stack) {
                  HandBaubleRender.doRenderLayer(player, stack, EnumHandSide.LEFT, TransformType.THIRD_PERSON_LEFT_HAND);
               }

               GlStateManager.popMatrix();
            }
         }
      }

      @Override
      public BaubleType getBaubleType(ItemStack itemstack) {
         return this.btype;
      }

      @Override
      public IAttribute getAttribute() {
         return this.attribute;
      }

      @Override
      public double value() {
         return this.value;
      }

      @Override
      public int operation() {
         return this.operation;
      }

      @Override
      public String itemName() {
         return this.getRegistryName().getNamespace();
      }
   }

   public static class BleedingRoot extends Item implements IBauble, IRenderBauble, IItemHurted, IAttributedBauble {
      public BleedingRoot() {
         this.setRegistryName("bleeding_root");
         this.setCreativeTab(CreativeTabs.COMBAT);
         this.setTranslationKey("bleeding_root");
         this.setMaxStackSize(1);
      }

      @Override
      public BaubleType getBaubleType(ItemStack itemstack) {
         return BaubleType.AMULET;
      }

      @Override
      public float onHurtWithItem(float hurtdamage, ItemStack stack, EntityPlayer player, DamageSource source) {
         if (!player.world.isRemote
            && !player.getCooldownTracker().hasCooldown(stack.getItem())
            && source.getTrueSource() != null
            && source.getTrueSource() instanceof EntityLivingBase) {
            ToxicomaniaMobsPack.Mosquito mosq = new ToxicomaniaMobsPack.Mosquito(
               player.world, player, (EntityLivingBase)source.getTrueSource(), true, false
            );
            mosq.setLocationAndAngles(
               player.posX, player.posY + itemRand.nextFloat(), player.posZ, itemRand.nextFloat() * 360.0F, 0.0F
            );
            mosq.motionX = (itemRand.nextFloat() - 0.5F) / 2.0F;
            mosq.motionZ = (itemRand.nextFloat() - 0.5F) / 2.0F;
            mosq.velocityChanged = true;
            IAttributeInstance attr = mosq.getEntityAttribute(PropertiesRegistry.ENTITY_COLOR_GREEN_MAX);
            attr.setBaseValue(0.5);
            IAttributeInstance attr2 = mosq.getEntityAttribute(PropertiesRegistry.ENTITY_COLOR_BLUE_MAX);
            attr.setBaseValue(0.5);
            player.world.spawnEntity(mosq);
            if (hurtdamage > 4.0F || itemRand.nextFloat() < 0.5) {
               mosq = new ToxicomaniaMobsPack.Mosquito(player.world, player, (EntityLivingBase)source.getTrueSource(), true, false);
               mosq.setLocationAndAngles(
                  player.posX, player.posY + itemRand.nextFloat(), player.posZ, itemRand.nextFloat() * 360.0F, 0.0F
               );
               mosq.motionX = (itemRand.nextFloat() - 0.5F) / 2.0F;
               mosq.motionZ = (itemRand.nextFloat() - 0.5F) / 2.0F;
               mosq.velocityChanged = true;
               attr = mosq.getEntityAttribute(PropertiesRegistry.ENTITY_COLOR_GREEN_MAX);
               attr.setBaseValue(0.5);
               attr2 = mosq.getEntityAttribute(PropertiesRegistry.ENTITY_COLOR_BLUE_MAX);
               attr.setBaseValue(0.5);
               player.world.spawnEntity(mosq);
            }

            player.getCooldownTracker().setCooldown(stack.getItem(), 30);
            player.world
               .playSound(
                  (EntityPlayer)null,
                  player.posX,
                  player.posY,
                  player.posZ,
                  Sounds.larva_water_attack,
                  SoundCategory.PLAYERS,
                  0.6F,
                  0.95F + itemRand.nextFloat() / 10.0F
               );
         }

         return hurtdamage;
      }

      @Override
      public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, RenderType type, float partialTicks) {
         if (type == RenderType.BODY) {
            GlStateManager.pushMatrix();
            GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
            Helper.rotateIfSneaking(player);
            Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            Minecraft.getMinecraft().getRenderItem().renderItem(stack, TransformType.HEAD);
            GlStateManager.popMatrix();
         }
      }

      public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
         tooltip.add("Releases mosquitoes when you take damage");
         tooltip.add("Mosquito attack cause Berserk");
         tooltip.add("+2 Maximum health");
         super.addInformation(stack, worldIn, tooltip, flagIn);
      }

      @Override
      public IAttribute getAttribute() {
         return SharedMonsterAttributes.MAX_HEALTH;
      }

      @Override
      public double value() {
         return 2.0;
      }

      @Override
      public int operation() {
         return 0;
      }

      @Override
      public String itemName() {
         return "bleeding_root";
      }
   }

   public static class CharmOfUndying extends Item implements IBauble, IRenderBauble, IItemDamaged {
      public CharmOfUndying() {
         this.setRegistryName("charm_of_undying");
         this.setCreativeTab(CreativeTabs.COMBAT);
         this.setTranslationKey("charm_of_undying");
         this.setMaxStackSize(1);
      }

      @Override
      public BaubleType getBaubleType(ItemStack itemstack) {
         return BaubleType.CHARM;
      }

      @Override
      public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, RenderType type, float partialTicks) {
         if (type == RenderType.BODY) {
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0F, 0.45F, -0.13F);
            GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
            Helper.rotateIfSneaking(player);
            Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            Minecraft.getMinecraft().getRenderItem().renderItem(stack, TransformType.GROUND);
            GlStateManager.popMatrix();
         }
      }

      @Override
      public void onWornTick(ItemStack itemstack, EntityLivingBase entityIn) {
         if (!entityIn.world.isRemote
            && (entityIn.getHeldItemMainhand().getItem() == Items.TOTEM_OF_UNDYING || entityIn.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING)) {
            entityIn.entityDropItem(itemstack.copy(), 0.0F);
            itemstack.shrink(1);
         }
      }

      @Override
      public float onDamagedWithItem(float hurtdamage, ItemStack stack, EntityPlayer player, DamageSource source) {
         if (player.getHealth() - hurtdamage <= 0.0F) {
            if (source.canHarmInCreative()) {
               return hurtdamage;
            } else {
               ItemStack copy = stack.copy();
               stack.shrink(1);
               if (player instanceof EntityPlayerMP) {
                  EntityPlayerMP entityplayermp = (EntityPlayerMP)player;
                  entityplayermp.addStat(StatList.getObjectUseStats(Items.TOTEM_OF_UNDYING));
                  CriteriaTriggers.USED_TOTEM.trigger(entityplayermp, copy);
               }

               player.setHealth(1.0F);
               player.clearActivePotions();
               player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 900, 1));
               player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 100, 1));
               player.addPotionEffect(new PotionEffect(PotionEffects.TENACITY, 100, 3));
               player.world.setEntityState(player, (byte)35);
               return 0.0F;
            }
         } else {
            return hurtdamage;
         }
      }

      public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
         tooltip.add("Saves the wearer from death once");
         super.addInformation(stack, worldIn, tooltip, flagIn);
      }
   }

   public static class CyberAmulet extends Item implements IBauble, IRenderBauble, IItemHurted, IAttributedBauble {
      public CyberAmulet() {
         this.setRegistryName("cyber_amulet");
         this.setCreativeTab(CreativeTabs.COMBAT);
         this.setTranslationKey("cyber_amulet");
         this.setMaxStackSize(1);
      }

      @Override
      public BaubleType getBaubleType(ItemStack itemstack) {
         return BaubleType.AMULET;
      }

      @Override
      public float onHurtWithItem(float hurtdamage, ItemStack stack, EntityPlayer player, DamageSource source) {
         if (source.getTrueSource() != null) {
            player.getCooldownTracker().setCooldown(stack.getItem(), 60);
         }

         return hurtdamage;
      }

      @Override
      public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, RenderType type, float partialTicks) {
         if (type == RenderType.BODY) {
            GlStateManager.pushMatrix();
            GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
            Helper.rotateIfSneaking(player);
            Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            Minecraft.getMinecraft().getRenderItem().renderItem(stack, TransformType.HEAD);
            GlStateManager.popMatrix();
         }
      }

      @Override
      public IAttribute getAttribute() {
         return SharedMonsterAttributes.MAX_HEALTH;
      }

      @Override
      public double value() {
         return 3.0;
      }

      @Override
      public int operation() {
         return 0;
      }

      @Override
      public String itemName() {
         return "cyber_amulet";
      }

      @Override
      public boolean useMultimap() {
         return true;
      }

      @Override
      public Multimap<String, AttributeModifier> getAttributeModifiers(EntityPlayer player, int equipmentSlot, ItemStack itemstack) {
         Multimap<String, AttributeModifier> multimap = HashMultimap.create();
         UUID uuid = UUID.fromString("CB2F4" + equipmentSlot + "D3-64" + equipmentSlot + "A-4F78-A497-9C56A33DB" + equipmentSlot + "BB");
         multimap.put(SharedMonsterAttributes.MAX_HEALTH.getName(), new AttributeModifier(uuid, "max health modifier", 3.0, 0));
         multimap.put(
            SharedMonsterAttributes.MOVEMENT_SPEED.getName(),
            new AttributeModifier(uuid, "movement speed modifier", player.getCooldownTracker().hasCooldown(itemstack.getItem()) ? 0.01 : 0.08, 0)
         );
         return multimap;
      }

      public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
         tooltip.add("Adds +3 Max Health and also +0.08 Movement speed if");
         tooltip.add("you didn't take damage for 3 seconds");
         super.addInformation(stack, worldIn, tooltip, flagIn);
      }
   }

   public static class EnderCrown extends Item implements IBauble, IRenderBauble, IAttributedBauble {
      public static ParticleTracker.TrackerSmoothShowHide ssh = new ParticleTracker.TrackerSmoothShowHide(
         null, new Vec3d[]{new Vec3d(0.0, 8.0, 0.85), new Vec3d(7.0, 10.0, 0.2), new Vec3d(9.0, 12.0, -0.2), new Vec3d(11.0, 20.0, -0.85)}
      );

      public EnderCrown() {
         this.setRegistryName("ender_crown");
         this.setCreativeTab(CreativeTabs.COMBAT);
         this.setTranslationKey("ender_crown");
         this.setMaxStackSize(1);
      }

      @Override
      public IAttribute getAttribute() {
         return PropertiesRegistry.MAGIC_POWER_MAX;
      }

      @Override
      public double value() {
         return 0.2;
      }

      @Override
      public int operation() {
         return 0;
      }

      @Override
      public String itemName() {
         return "ender_crown";
      }

      @Override
      public BaubleType getBaubleType(ItemStack itemstack) {
         return BaubleType.HEAD;
      }

      @Override
      public void onWornTick(ItemStack itemstack, EntityLivingBase entity) {
         if (!entity.world.isRemote && entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entity;
            if (!player.getCooldownTracker().hasCooldown(this) && Keys.isKeyPressed(player, Keys.HEADABILITY)) {
               double blockReachDistance = 12.0;
               Vec3d vec3d = entity.getPositionEyes(1.0F);
               Vec3d vec3d1 = GetMOP.PitchYawToVec3d(player.rotationPitch, player.rotationYaw);
               Vec3d vec3d2 = vec3d.add(
                  vec3d1.x * blockReachDistance, vec3d1.y * blockReachDistance, vec3d1.z * blockReachDistance
               );
               RayTraceResult res = fixedRayTraceBlocks(player.world, player, 0.8, 0.5, true, vec3d, vec3d2, false, true, false);
               if (res != null && res.entityHit != null && res.entityHit instanceof EntityLiving) {
                  EntityLiving entitylive = (EntityLiving)res.entityHit;
                  PotionEffect eff = entitylive.getActivePotionEffect(PotionEffects.FROSTBURN);
                  if (eff != null
                     && entitylive.getHealth()
                        <= (eff.getAmplifier() + 1) * 21 - entitylive.getEntityAttribute(PropertiesRegistry.ARMOR_PROTECTION).getAttributeValue()) {
                     float pit = 0.95F + itemRand.nextFloat() / 5.0F;
                     entitylive.attackEntityFrom(DamageSource.causePlayerDamage(player), (eff.getAmplifier() + 1) * 21);
                     player.world
                        .playSound(
                           (EntityPlayer)null,
                           player.posX,
                           player.posY,
                           player.posZ,
                           Sounds.fast_teleport,
                           SoundCategory.AMBIENT,
                           0.8F,
                           pit
                        );
                     PacketDoSomethingToClients packet = new PacketDoSomethingToClients();
                     packet.writeargs(
                        entitylive.posX,
                        entitylive.posY + entitylive.height / 2.0F,
                        entitylive.posZ,
                        player.posX,
                        player.posY + player.height / 2.0F,
                        player.posZ,
                        7
                     );
                     PacketHandler.sendToAllAround(packet, entity.world, entity.posX, entity.posY, entity.posZ, 64.0);
                     player.attemptTeleport(entitylive.posX, entitylive.posY, entitylive.posZ);
                     EntityLiveHeart.spawnHearts(
                        entity.world,
                        entitylive.posX,
                        entitylive.posY + 0.1,
                        entitylive.posZ,
                        3,
                        2.0F,
                        true,
                        2.5F,
                        Team.getTeamFor(player)
                     );
                     player.world
                        .playSound(
                           (EntityPlayer)null,
                           entitylive.posX,
                           entitylive.posY,
                           entitylive.posZ,
                           Sounds.fast_teleport,
                           SoundCategory.AMBIENT,
                           0.8F,
                           pit
                        );
                     player.getCooldownTracker().setCooldown(this, 150);
                     player.fallDistance = 0.0F;
                     entitylive.removePotionEffect(PotionEffects.FROSTBURN);
                     double damageRadius = 7.0;
                     AxisAlignedBB axisalignedbb = player.getEntityBoundingBox()
                        .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
                        .offset(-damageRadius, -damageRadius, -damageRadius);
                     List<EntityLivingBase> list = player.world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
                     if (!list.isEmpty()) {
                        for (EntityLivingBase entitylivingbase : list) {
                           if (Team.checkIsOpponent(player, entitylivingbase) && entitylivingbase != entitylive) {
                              SuperKnockback.applyKnockback(
                                 entitylivingbase, 2.0F, entitylive.posX, entitylive.posY - 1.0, entitylive.posZ
                              );
                              Weapons.mixPotion(
                                 entitylivingbase,
                                 MobEffects.SLOWNESS,
                                 60.0F,
                                 3.0F,
                                 Weapons.EnumPotionMix.ABSOLUTE,
                                 Weapons.EnumPotionMix.GREATEST,
                                 Weapons.EnumMathOperation.NONE,
                                 Weapons.EnumMathOperation.NONE,
                                 0,
                                 0
                              );
                              if (entitylivingbase.isPotionActive(PotionEffects.FROSTBURN)) {
                                 EntityLiveHeart.spawnHearts(
                                    entity.world,
                                    entitylive.posX,
                                    entitylive.posY + 0.1,
                                    entitylive.posZ,
                                    entitylivingbase.getActivePotionEffect(PotionEffects.FROSTBURN).getAmplifier() + 1,
                                    2.0F,
                                    true,
                                    2.5F,
                                    Team.getTeamFor(player)
                                 );
                                 entitylivingbase.removePotionEffect(PotionEffects.FROSTBURN);
                              }
                           }
                        }
                     }

                     this.bom();
                     return;
                  }
               }

               player.getCooldownTracker().setCooldown(this, 3);
            }
         }
      }

      public static RayTraceResult fixedRayTraceBlocks(
         World world,
         Entity shooter,
         double size,
         double raystep,
         boolean checkTeam,
         Vec3d start,
         Vec3d end,
         boolean stopOnLiquid,
         boolean ignoreBlockWithoutBoundingBox,
         boolean returnLastUncollidableBlock
      ) {
         RayTraceResult result = world.rayTraceBlocks(start, end, stopOnLiquid, ignoreBlockWithoutBoundingBox, returnLastUncollidableBlock);
         if (result != null && result.typeOfHit == Type.BLOCK && result.hitVec != null) {
            RayTraceResult baseres = findEntityAndPosOnPath(start, result.hitVec, world, shooter, size, raystep, checkTeam);
            if (baseres != null) {
               result.entityHit = baseres.entityHit;
               result.typeOfHit = Type.ENTITY;
               result.hitVec = baseres.hitVec;
            }
         } else if (result == null || result.hitVec == null || result.typeOfHit == Type.MISS) {
            RayTraceResult baseres = findEntityAndPosOnPath(start, end, world, shooter, size, raystep, checkTeam);
            if (baseres != null) {
               result = new RayTraceResult(baseres.entityHit, baseres.hitVec);
               result.typeOfHit = Type.ENTITY;
            }
         }

         return result;
      }

      public static RayTraceResult findEntityAndPosOnPath(Vec3d start, Vec3d end, World world, Entity shooter, double size, double raystep, boolean checkTeam) {
         Vec3d FromStartToEnd = end.subtract(start);
         Vec3d ToVertex = new Vec3d(size / 2.0, size / 2.0, size / 2.0);
         new ArrayList();
         double step = raystep / FromStartToEnd.length();

         for (double k = 0.0; k <= 1.0; k += step) {
            Vec3d CenterVertex = start.add(FromStartToEnd.scale(k));
            Vec3d DownVertex = CenterVertex.subtract(ToVertex);
            Vec3d UpVertex = CenterVertex.add(ToVertex);
            AxisAlignedBB Cube = new AxisAlignedBB(DownVertex, UpVertex);
            List<EntityLivingBase> list = world.getEntitiesWithinAABB(EntityLivingBase.class, Cube);
            if (!list.isEmpty()) {
               for (EntityLivingBase entityliving : list) {
                  PotionEffect eff = entityliving.getActivePotionEffect(PotionEffects.FROSTBURN);
                  if (entityliving != shooter
                     && eff != null
                     && entityliving.getHealth()
                        <= (eff.getAmplifier() + 1) * 21 - entityliving.getEntityAttribute(PropertiesRegistry.ARMOR_PROTECTION).getAttributeValue()) {
                     if (!checkTeam) {
                        return new RayTraceResult(entityliving, CenterVertex);
                     }

                     if (Team.checkIsOpponent(shooter, entityliving)) {
                        return new RayTraceResult(entityliving, CenterVertex);
                     }
                  }
               }
            }
         }

         return null;
      }

      @SideOnly(Side.CLIENT)
      public void bom() {
         Booom.lastTick = 13;
         Booom.frequency = 0.315F * (itemRand.nextFloat() < 0.5 ? 1 : -1);
         Booom.x = 0.0F;
         Booom.y = 0.0F;
         Booom.z = 1.0F;
         Booom.power = 0.45F;
         Booom.FOVlastTick = 24;
         Booom.FOVfrequency = -0.131F;
         Booom.FOVpower = -0.05F;
      }

      @SideOnly(Side.CLIENT)
      public static void effect(World world, double x, double y, double z, double prevx, double prevy, double prevz) {
         for (int ss = 0; ss < 55; ss++) {
            world.spawnParticle(
               EnumParticleTypes.DRAGON_BREATH,
               x,
               y,
               z,
               itemRand.nextGaussian() / 10.0,
               itemRand.nextGaussian() / 10.0,
               itemRand.nextGaussian() / 10.0,
               new int[0]
            );
         }

         for (int ss = 0; ss < 200; ss++) {
            world.spawnParticle(
               EnumParticleTypes.SUSPENDED_DEPTH,
               x + itemRand.nextGaussian() * 2.0,
               y + itemRand.nextGaussian() * 2.0,
               z + itemRand.nextGaussian() * 2.0,
               0.0,
               0.0,
               0.0,
               new int[0]
            );
         }

         float fsize = (float)(2.0 + itemRand.nextGaussian() / 6.0);
         int lt = 20;
         GUNParticle part = new GUNParticle(
            BaublesPack.voidexplode,
            0.1F,
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
            0.8F + itemRand.nextFloat() * 0.1F,
            1.0F,
            1.0F,
            true,
            itemRand.nextInt(360)
         );
         part.alphaGlowing = true;
         part.randomDeath = false;
         part.rotationPitchYaw = new Vec2f(90.0F, 0.0F);
         part.tracker = ssh;
         world.spawnEntity(part);
         double d0 = x - prevx;
         double d1 = y - prevy;
         double d2 = z - prevz;
         float fmax = MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
         Vec3d vecstart = new Vec3d(d0, d1, d2);
         float add = Math.max(0.25F / fmax, 0.019F);

         for (float f = 0.0F; f < 1.0F; f += add) {
            Vec3d vecscl = vecstart.scale(f);
            double pX = prevx + vecscl.x;
            double pY = prevy + vecscl.y;
            double pZ = prevz + vecscl.z;

            for (int i = 0; i < 2; i++) {
               world.spawnParticle(
                  EnumParticleTypes.PORTAL,
                  pX + (itemRand.nextDouble() - 0.5),
                  pY + itemRand.nextDouble() - 0.25,
                  pZ + (itemRand.nextDouble() - 0.5),
                  (itemRand.nextDouble() - 0.5) * 2.0,
                  -itemRand.nextDouble(),
                  (itemRand.nextDouble() - 0.5) * 2.0,
                  new int[0]
               );
            }
         }
      }

      @Override
      public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, RenderType type, float partialTicks) {
         if (type == RenderType.BODY) {
            GlStateManager.pushMatrix();
            HandBaubleRender.doRenderLayer(player, stack, EnumHandSide.LEFT);
            GlStateManager.popMatrix();
         }
      }
   }

   public static class EnigmateTwins extends Item implements IBauble, IRenderBauble, IItemDamaging {
      public static EnigmateTwinsModel model = new EnigmateTwinsModel();
      public static ResourceLocation texture = new ResourceLocation("arpg:textures/enigmate_twins_model_tex.png");

      public EnigmateTwins() {
         this.setRegistryName("enigmate_twins");
         this.setCreativeTab(CreativeTabs.COMBAT);
         this.setTranslationKey("enigmate_twins");
         this.setMaxStackSize(1);
      }

      @Override
      public BaubleType getBaubleType(ItemStack itemstack) {
         return BaubleType.AMULET;
      }

      @Override
      public void onWornTick(ItemStack itemstack, EntityLivingBase playerattacker) {
         if (!playerattacker.world.isRemote && playerattacker.ticksExisted % 3 == 0) {
            int shots = NBTHelper.GetNBTint(itemstack, "shots");
            if (shots > 0) {
               boolean nexttwin = NBTHelper.GetNBTboolean(itemstack, "nexttwin");
               int targetId = NBTHelper.GetNBTint(itemstack, "target");
               Entity target = playerattacker.world.getEntityByID(targetId);
               if (target != null && target instanceof EntityLivingBase) {
                  Vec3d vec3dEyes = new Vec3d(
                     playerattacker.posX, playerattacker.posY + playerattacker.getEyeHeight() + 0.2F, playerattacker.posZ
                  );
                  Vec3d yawVec = GetMOP.YawToVec3d(playerattacker.rotationYawHead + (nexttwin ? 90 : -90));
                  float shoulders = 0.4F;
                  Vec3d pos = vec3dEyes.add(yawVec.x * shoulders, yawVec.y * shoulders, yawVec.z * shoulders);
                  EnigmateTwinsShoot projectile = new EnigmateTwinsShoot(playerattacker.world, playerattacker, itemstack);
                  projectile.shoot(
                     playerattacker,
                     playerattacker.rotationPitch - 7.0F,
                     playerattacker.rotationYaw + (nexttwin ? 14 : -14),
                     0.0F,
                     0.5F + itemRand.nextFloat() * 0.2F,
                     4.0F
                  );
                  projectile.setPosition(pos.x, pos.y, pos.z);
                  projectile.targ = (EntityLivingBase)target;
                  projectile.damage = 6.0F;
                  playerattacker.world.spawnEntity(projectile);
                  playerattacker.world
                     .playSound(
                        (EntityPlayer)null,
                        pos.x,
                        pos.y,
                        pos.z,
                        Sounds.enigmate_twins,
                        SoundCategory.AMBIENT,
                        0.7F,
                        0.9F + itemRand.nextFloat() / 5.0F
                     );
                  NBTHelper.GiveNBTboolean(itemstack, false, "nexttwin");
                  NBTHelper.SetNBTboolean(itemstack, !nexttwin, "nexttwin");
                  NBTHelper.AddNBTint(itemstack, -1, "shots");
               } else {
                  NBTHelper.SetNBTint(itemstack, 0, "shots");
               }
            }
         }
      }

      @Override
      public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, RenderType type, float partialTicks) {
         if (type == RenderType.BODY) {
            float f = GetMOP.interpolateRotation(player.prevRenderYawOffset, player.renderYawOffset, partialTicks);
            float f1 = GetMOP.interpolateRotation(player.prevRotationYawHead, player.rotationYawHead, partialTicks);
            float f2 = f1 - f;
            GlStateManager.pushMatrix();
            RenderHelper.enableStandardItemLighting();
            GlStateManager.tryBlendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
            GlStateManager.enableBlend();
            GlStateManager.disableCull();
            Helper.rotateIfSneaking(player);
            float scale = 0.0625F;
            GlStateManager.scale(scale, scale, scale);
            Minecraft.getMinecraft().renderEngine.bindTexture(texture);
            model.render(player, GetMOP.partial(player.rotationPitch, player.prevRotationPitch, partialTicks), f2, 0.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.disableBlend();
            GlStateManager.enableCull();
            GlStateManager.popMatrix();
         }
      }

      @Override
      public float onCauseDamageWithItem(float hurtdamage, ItemStack itemstack, EntityPlayer playerattacker, EntityLivingBase target, DamageSource source) {
         if (!playerattacker.world.isRemote && target != null && !(source.getImmediateSource() instanceof EnigmateTwinsShoot)) {
            int damageToShot = 9;
            int hurtdamageInt = (int)hurtdamage;
            int shootsAmount = hurtdamageInt / damageToShot;
            if (itemRand.nextFloat() < (hurtdamage - shootsAmount * damageToShot) / damageToShot) {
               shootsAmount++;
            }

            if (shootsAmount > 0) {
               NBTHelper.GiveNBTint(itemstack, 0, "shots");
               NBTHelper.GiveNBTint(itemstack, 0, "target");
               NBTHelper.AddNBTint(itemstack, shootsAmount, "shots");
               NBTHelper.SetNBTint(itemstack, target.getEntityId(), "target");
            }
         }

         return hurtdamage;
      }
   }

   public static class Gasmask extends Item implements IBauble, IRenderBauble {
      public static GasmaskModel model = new GasmaskModel();
      public static ResourceLocation tex = new ResourceLocation("arpg:textures/gasmask_model_tex.png");

      public Gasmask() {
         this.setRegistryName("gasmask");
         this.setCreativeTab(CreativeTabs.COMBAT);
         this.setTranslationKey("gasmask");
         this.setMaxStackSize(1);
         this.setMaxDamage(1000);
      }

      public boolean isRepairable() {
         return true;
      }

      public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
         return repair.getItem() == ItemsRegister.GASFILTER;
      }

      @Override
      public BaubleType getBaubleType(ItemStack itemstack) {
         return BaubleType.HEAD;
      }

      @Override
      public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, RenderType type, float partialTicks) {
         if (type == RenderType.HEAD) {
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0F, 0.03F, 0.0F);
            GlStateManager.scale(0.065F, 0.065F, 0.065F);
            GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
            Helper.rotateIfSneaking(player);
            Minecraft.getMinecraft().renderEngine.bindTexture(tex);
            model.render(player, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.popMatrix();
         }
      }

      public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
         tooltip.add("Protects against chlorite inhalation");
         super.addInformation(stack, worldIn, tooltip, flagIn);
      }
   }

   public static class GlassHeart extends Item implements IBauble, IRenderBauble, IAttributedBauble {
      public GlassHeart() {
         this.setRegistryName("glass_heart");
         this.setCreativeTab(CreativeTabs.COMBAT);
         this.setTranslationKey("glass_heart");
         this.setMaxStackSize(1);
      }

      @Override
      public BaubleType getBaubleType(ItemStack itemstack) {
         return BaubleType.AMULET;
      }

      @Override
      public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, RenderType type, float partialTicks) {
         if (type == RenderType.BODY) {
            GlStateManager.pushMatrix();
            GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
            Helper.rotateIfSneaking(player);
            Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            Minecraft.getMinecraft().getRenderItem().renderItem(stack, TransformType.HEAD);
            GlStateManager.popMatrix();
         }
      }

      @Override
      public IAttribute getAttribute() {
         return SharedMonsterAttributes.MAX_HEALTH;
      }

      @Override
      public double value() {
         return -19.0;
      }

      @Override
      public int operation() {
         return 0;
      }

      @Override
      public String itemName() {
         return "glass_heart";
      }

      public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
         tooltip.add("Removes 19 Maximum Health");
         super.addInformation(stack, worldIn, tooltip, flagIn);
      }
   }

   public static class GoldenKnuckles extends Item implements IBauble, IAttributedBauble, IRenderBauble {
      public GoldenKnuckles() {
         this.setRegistryName("golden_knuckles");
         this.setCreativeTab(CreativeTabs.COMBAT);
         this.setTranslationKey("golden_knuckles");
         this.setMaxStackSize(1);
      }

      @Override
      public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, RenderType type, float partialTicks) {
         if (type == RenderType.BODY) {
            GlStateManager.pushMatrix();
            if (BaublesApi.getBaubles(player).getStackInSlot(1) == stack) {
               HandBaubleRender.doRenderLayer(player, stack, EnumHandSide.RIGHT, TransformType.THIRD_PERSON_RIGHT_HAND);
            } else if (BaublesApi.getBaubles(player).getStackInSlot(2) == stack) {
               HandBaubleRender.doRenderLayer(player, stack, EnumHandSide.LEFT, TransformType.THIRD_PERSON_LEFT_HAND);
            }

            GlStateManager.popMatrix();
         }
      }

      @Override
      public BaubleType getBaubleType(ItemStack itemstack) {
         return BaubleType.RING;
      }

      @Override
      public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
         if (player.ticksExisted % 20 == 0 && player.getHealth() < 8.0F) {
            PotionEffect baff = new PotionEffect(PotionEffects.BERSERK, 30);
            player.addPotionEffect(baff);
         }
      }

      @Override
      public IAttribute getAttribute() {
         return PropertiesRegistry.ARMOR_PROTECTION;
      }

      @Override
      public double value() {
         return 1.0;
      }

      @Override
      public int operation() {
         return 0;
      }

      @Override
      public String itemName() {
         return "golden_knuckles";
      }

      @Override
      public boolean useMultimap() {
         return true;
      }

      @Override
      public Multimap<String, AttributeModifier> getAttributeModifiers(EntityPlayer player, int equipmentSlot, ItemStack itemstack) {
         Multimap<String, AttributeModifier> multimap = HashMultimap.create();
         UUID uuid = UUID.fromString("CB2F4" + equipmentSlot + "D3-64" + equipmentSlot + "A-4F78-A497-7C56D11AA" + equipmentSlot + "DB");
         multimap.put(PropertiesRegistry.ARMOR_PROTECTION.getName(), new AttributeModifier(uuid, "armor protection modifier", 1.0, 0));
         multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(uuid, "attack damage modifier", 2.0, 0));
         return multimap;
      }

      public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
         tooltip.add("Adds +1 Armor Protection, +2 Melee Attack Damage");
         tooltip.add("and imposes Berserk when health lower than 8");
         super.addInformation(stack, worldIn, tooltip, flagIn);
      }
   }

   public static class HellhoundCollar extends Item implements IBauble, IRenderBauble {
      public HellhoundCollar() {
         this.setRegistryName("hellhound_collar");
         this.setCreativeTab(CreativeTabs.COMBAT);
         this.setTranslationKey("hellhound_collar");
         this.setMaxStackSize(1);
      }

      @Override
      public BaubleType getBaubleType(ItemStack itemstack) {
         return BaubleType.AMULET;
      }

      @Override
      public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
         if (player.ticksExisted % 20 == 0 && player.getHealth() < 8.0F) {
            PotionEffect baff = new PotionEffect(PotionEffects.BERSERK, 30);
            player.addPotionEffect(baff);
         }
      }

      @Override
      public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, RenderType type, float partialTicks) {
         if (type == RenderType.BODY) {
            GlStateManager.pushMatrix();
            GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
            Helper.rotateIfSneaking(player);
            Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            Minecraft.getMinecraft().getRenderItem().renderItem(stack, TransformType.HEAD);
            GlStateManager.popMatrix();
         }
      }

      public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
         tooltip.add("Imposes Berserk when health lower than 8");
         super.addInformation(stack, worldIn, tooltip, flagIn);
      }
   }

   public static class HerobrineCurse extends Item implements IBauble, IItemKilling, IItemHurted {
      public HerobrineCurse() {
         this.setRegistryName("herobrine_curse");
         this.setCreativeTab(CreativeTabs.COMBAT);
         this.setTranslationKey("herobrine_curse");
         this.setMaxStackSize(1);
      }

      @Override
      public BaubleType getBaubleType(ItemStack itemstack) {
         return BaubleType.TRINKET;
      }

      @Override
      public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
         if (player instanceof EntityPlayer) {
            IInventory inv = BaublesApi.getBaubles((EntityPlayer)player);

            for (int i = 0; i < 7; i++) {
               ItemStack bitem = inv.getStackInSlot(i);
               if (bitem.getItem() == this) {
                  return false;
               }
            }
         }

         return IBauble.super.canEquip(itemstack, player);
      }

      @Override
      public void onKillWithItem(ItemStack stack, EntityPlayer playerattacker, EntityLivingBase target, DamageSource source) {
         if (!target.isChild() && target.world.getGameRules().getBoolean("doMobLoot")) {
            int i = 0;

            try {
               Method method = EntityLivingBase.class.getDeclaredMethod("getExperiencePoints", EntityPlayer.class);
               method.setAccessible(true);
               Object objectt = method.invoke(target, playerattacker);
               if (objectt instanceof Integer) {
                  i = (Integer)objectt;
               }
            } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException var8) {
               var8.printStackTrace();
            }

            i = ForgeEventFactory.getExperienceDrop(target, playerattacker, i);

            while (i > 0) {
               int j = EntityXPOrb.getXPSplit(i);
               i -= j;
               target.world.spawnEntity(new EntityXPOrb(target.world, target.posX, target.posY, target.posZ, j));
            }
         }
      }

      @Override
      public float onHurtWithItem(float hurtdamage, ItemStack stack, EntityPlayer player, DamageSource source) {
         if (hurtdamage > 0.0F) {
            player.setHealth(0.0F);
         }

         return hurtdamage;
      }

      public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
         tooltip.add("Any damage kills you, but mobs drop double experience");
         super.addInformation(stack, worldIn, tooltip, flagIn);
      }
   }

   public static class IceHeart extends Item implements IBauble, IRenderBauble, IItemHurted, IAttributedBauble {
      public IceHeart() {
         this.setRegistryName("ice_heart");
         this.setCreativeTab(CreativeTabs.COMBAT);
         this.setTranslationKey("ice_heart");
         this.setMaxStackSize(1);
      }

      @Override
      public BaubleType getBaubleType(ItemStack itemstack) {
         return BaubleType.AMULET;
      }

      @Override
      public float onHurtWithItem(float hurtdamage, ItemStack stack, EntityPlayer player, DamageSource source) {
         if (source != DamageSource.DROWN && source != DamageSource.STARVE) {
            Mana.giveMana(player, hurtdamage / 2.0F);
         }

         return hurtdamage;
      }

      @Override
      public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, RenderType type, float partialTicks) {
         if (type == RenderType.BODY) {
            GlStateManager.pushMatrix();
            GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
            Helper.rotateIfSneaking(player);
            Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            Minecraft.getMinecraft().getRenderItem().renderItem(stack, TransformType.HEAD);
            GlStateManager.popMatrix();
         }
      }

      @Override
      public IAttribute getAttribute() {
         return PropertiesRegistry.MANA_MAX;
      }

      @Override
      public double value() {
         return 5.0;
      }

      @Override
      public int operation() {
         return 0;
      }

      @Override
      public String itemName() {
         return "ice_heart";
      }

      @Override
      public boolean useMultimap() {
         return true;
      }

      @Override
      public Multimap<String, AttributeModifier> getAttributeModifiers(EntityPlayer player, int equipmentSlot, ItemStack itemstack) {
         Multimap<String, AttributeModifier> multimap = HashMultimap.create();
         UUID uuid = UUID.fromString("CB2F4" + equipmentSlot + "D3-64" + equipmentSlot + "A-4F78-A497-9C56A33DB" + equipmentSlot + "BB");
         multimap.put(PropertiesRegistry.MANA_MAX.getName(), new AttributeModifier(uuid, "mana max modifier", 5.0, 0));
         multimap.put(PropertiesRegistry.MANASPEED_MAX.getName(), new AttributeModifier(uuid, "manaspeed max modifier", 0.2, 0));
         return multimap;
      }

      public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
         tooltip.add("Adds +5 Maximum Mana, +0.2 Mana Regeneration and gives mana when you take damage");
         super.addInformation(stack, worldIn, tooltip, flagIn);
      }
   }

   public static class Lightband extends Item implements IBauble, IRenderBauble, IAttributedBauble {
      public static ResourceLocation textur = new ResourceLocation("arpg:textures/circle.png");

      public Lightband() {
         this.setRegistryName("lightband");
         this.setCreativeTab(CreativeTabs.COMBAT);
         this.setTranslationKey("lightband");
         this.setMaxStackSize(1);
         this.setMaxDamage(5);
      }

      @Override
      public BaubleType getBaubleType(ItemStack itemstack) {
         return BaubleType.BELT;
      }

      @Override
      public void onWornTick(ItemStack itemstack, EntityLivingBase entityIn) {
         entityIn.removeActivePotionEffect(PotionEffects.SHOCK);
         Item itemIn = itemstack.getItem();
         if (entityIn instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entityIn;
            boolean click = Keys.isPressedDoubleJump(player);
            int dm = itemstack.getItemDamage();
            if (click && (dm == 0 || dm == 2 || dm == 4)) {
               player.motionY = player.motionY * 0.1 + 0.6;
               player.fallDistance = 0.0F;
               itemstack.damageItem(1, entityIn);
               player.world
                  .playSound(
                     (EntityPlayer)null,
                     player.posX,
                     player.posY,
                     player.posZ,
                     Sounds.cyber_jump,
                     SoundCategory.PLAYERS,
                     0.5F,
                     0.95F + itemRand.nextFloat() / 10.0F
                  );
               if (!player.world.isRemote) {
                  PacketDoSomethingToClients packet = new PacketDoSomethingToClients();
                  packet.writeargs(player.posX, player.posY + 0.3, player.posZ, 0.0, 0.0, 0.0, 4);
                  PacketHandler.sendToAllAround(packet, player.world, player.posX, player.posY, player.posZ, 32.0);
               }
            }

            if (!click && (dm == 1 || dm == 3)) {
               itemstack.damageItem(1, entityIn);
            }

            if (player.onGround && !click) {
               itemstack.setItemDamage(0);
            }
         }
      }

      @Override
      public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, RenderType type, float partialTicks) {
         if (type == RenderType.BODY) {
            float lbX = OpenGlHelper.lastBrightnessX;
            float lbY = OpenGlHelper.lastBrightnessY;
            GlStateManager.pushMatrix();
            GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
            Helper.rotateIfSneaking(player);
            Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
            Minecraft.getMinecraft().getRenderItem().renderItem(stack, TransformType.HEAD);
            GlStateManager.translate(0.0, 0.0, -0.3);
            Minecraft.getMinecraft().getRenderItem().renderItem(stack, TransformType.HEAD);
            GlStateManager.popMatrix();
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lbX, lbY);
         }
      }

      @Override
      public IAttribute getAttribute() {
         return PropertiesRegistry.AIRBORNE_MOBILITY;
      }

      @Override
      public double value() {
         return 0.05;
      }

      @Override
      public int operation() {
         return 0;
      }

      @Override
      public String itemName() {
         return "lightband";
      }

      public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
         tooltip.add("Allows do four jumps, adds +0.05 Airborne Mobility and grants immunity to Shock");
         super.addInformation(stack, worldIn, tooltip, flagIn);
      }
   }

   public static class LivebloodNecklace extends Item implements IBauble, IRenderBauble, IItemHurted {
      public LivebloodNecklace() {
         this.setRegistryName("liveblood_necklace");
         this.setCreativeTab(CreativeTabs.COMBAT);
         this.setTranslationKey("liveblood_necklace");
         this.setMaxStackSize(1);
      }

      @Override
      public BaubleType getBaubleType(ItemStack itemstack) {
         return BaubleType.AMULET;
      }

      @Override
      public float onHurtWithItem(float hurtdamage, ItemStack stack, EntityPlayer player, DamageSource source) {
         if (source != DamageSource.DROWN && source != DamageSource.STARVE) {
            Mana.giveMana(player, hurtdamage / 2.0F);
         }

         return hurtdamage;
      }

      @Override
      public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, RenderType type, float partialTicks) {
         if (type == RenderType.BODY) {
            GlStateManager.pushMatrix();
            GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
            Helper.rotateIfSneaking(player);
            Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            Minecraft.getMinecraft().getRenderItem().renderItem(stack, TransformType.HEAD);
            GlStateManager.popMatrix();
         }
      }

      public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
         tooltip.add("Gives mana when you take damage");
         super.addInformation(stack, worldIn, tooltip, flagIn);
      }
   }

   public static class ManaKeeper extends Item implements IBauble, IRenderBauble, IAttributedBauble {
      public ManaKeeper() {
         this.setRegistryName("mana_keeper");
         this.setCreativeTab(CreativeTabs.COMBAT);
         this.setTranslationKey("mana_keeper");
         this.setMaxStackSize(1);
      }

      @Override
      public IAttribute getAttribute() {
         return PropertiesRegistry.MANA_MAX;
      }

      @Override
      public double value() {
         return 7.0;
      }

      @Override
      public int operation() {
         return 0;
      }

      @Override
      public String itemName() {
         return "mana_keeper";
      }

      @Override
      public BaubleType getBaubleType(ItemStack itemstack) {
         return BaubleType.CHARM;
      }

      @Override
      public void onWornTick(ItemStack itemstack, EntityLivingBase entity) {
         if (!entity.world.isRemote && entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entity;
            if (!player.getCooldownTracker().hasCooldown(itemstack.getItem())) {
               AxisAlignedBB aabb = player.getEntityBoundingBox().grow(6.0, 4.0, 6.0);
               List<EntityLivingBase> list = player.world.getEntitiesWithinAABB(EntityLivingBase.class, aabb);
               double sqdistmin = 9999999.0;
               EntityLivingBase target = null;

               for (EntityLivingBase base : list) {
                  if (base instanceof IMob && Team.checkIsOpponent(entity, base)) {
                     double sqdist = base.getDistanceSq(entity);
                     if (sqdist < sqdistmin) {
                        sqdistmin = sqdist;
                        target = base;
                     }
                  }
               }

               if (target != null) {
                  ThornkeeperShoot shoot = new ThornkeeperShoot(player.world, player, itemstack, Mana.getMagicPowerMax(player));
                  shoot.setLocationAndAngles(player.posX, player.posY + 1.0, player.posZ, 0.0F, 0.0F);
                  SuperKnockback.applyMove(
                     shoot,
                     target.isInWater() ? -3.2F : -1.4F,
                     target.posX,
                     target.posY + (target.isInWater() ? target.height / 2.0F : 2.0F),
                     target.posZ
                  );
                  shoot.isMana = true;
                  player.world.spawnEntity(shoot);
                  player.world
                     .playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.larva_water_attack,
                        SoundCategory.AMBIENT,
                        0.7F,
                        0.9F + itemRand.nextFloat() / 5.0F
                     );
                  player.getCooldownTracker().setCooldown(itemstack.getItem(), 45);
               }
            }
         }
      }

      @Override
      public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, RenderType type, float partialTicks) {
         if (type == RenderType.BODY) {
            GlStateManager.pushMatrix();
            HandBaubleRender.doRenderLayer(player, stack, EnumHandSide.LEFT);
            GlStateManager.popMatrix();
         }
      }

      public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
         tooltip.add("Periodically shoots thorny balls to mobs");
         tooltip.add("Balls regen you mana when cause damage");
         tooltip.add(" +7 Maximum mana");
         super.addInformation(stack, worldIn, tooltip, flagIn);
      }
   }

   public static class MermaidMedallion extends Item implements IBauble, IRenderBauble {
      public MermaidMedallion() {
         this.setRegistryName("mermaid_medallion");
         this.setCreativeTab(CreativeTabs.COMBAT);
         this.setTranslationKey("mermaid_medallion");
         this.setMaxStackSize(1);
      }

      @Override
      public BaubleType getBaubleType(ItemStack itemstack) {
         return BaubleType.AMULET;
      }

      @Override
      public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, RenderType type, float partialTicks) {
         if (type == RenderType.BODY) {
            GlStateManager.pushMatrix();
            GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
            Helper.rotateIfSneaking(player);
            Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            Minecraft.getMinecraft().getRenderItem().renderItem(stack, TransformType.HEAD);
            GlStateManager.popMatrix();
         }
      }

      public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
         tooltip.add("The mermaids won't attack you first");
         super.addInformation(stack, worldIn, tooltip, flagIn);
      }
   }

   public static class PainfulRoot extends Item implements IBauble, IRenderBauble, IItemHurted {
      public PainfulRoot() {
         this.setRegistryName("painful_root");
         this.setCreativeTab(CreativeTabs.COMBAT);
         this.setTranslationKey("painful_root");
         this.setMaxStackSize(1);
      }

      @Override
      public BaubleType getBaubleType(ItemStack itemstack) {
         return BaubleType.AMULET;
      }

      @Override
      public float onHurtWithItem(float hurtdamage, ItemStack stack, EntityPlayer player, DamageSource source) {
         if (!player.world.isRemote
            && !player.getCooldownTracker().hasCooldown(stack.getItem())
            && source.getTrueSource() != null
            && source.getTrueSource() instanceof EntityLivingBase) {
            ToxicomaniaMobsPack.Mosquito mosq = new ToxicomaniaMobsPack.Mosquito(
               player.world, player, (EntityLivingBase)source.getTrueSource(), false, false
            );
            mosq.setLocationAndAngles(
               player.posX, player.posY + itemRand.nextFloat(), player.posZ, itemRand.nextFloat() * 360.0F, 0.0F
            );
            mosq.motionX = (itemRand.nextFloat() - 0.5F) / 2.0F;
            mosq.motionZ = (itemRand.nextFloat() - 0.5F) / 2.0F;
            mosq.velocityChanged = true;
            player.world.spawnEntity(mosq);
            if (hurtdamage > 4.0F || itemRand.nextFloat() < 0.5) {
               mosq = new ToxicomaniaMobsPack.Mosquito(player.world, player, (EntityLivingBase)source.getTrueSource(), false, false);
               mosq.setLocationAndAngles(
                  player.posX, player.posY + itemRand.nextFloat(), player.posZ, itemRand.nextFloat() * 360.0F, 0.0F
               );
               mosq.motionX = (itemRand.nextFloat() - 0.5F) / 2.0F;
               mosq.motionZ = (itemRand.nextFloat() - 0.5F) / 2.0F;
               mosq.velocityChanged = true;
               player.world.spawnEntity(mosq);
            }

            player.getCooldownTracker().setCooldown(stack.getItem(), 30);
            player.world
               .playSound(
                  (EntityPlayer)null,
                  player.posX,
                  player.posY,
                  player.posZ,
                  Sounds.larva_water_attack,
                  SoundCategory.PLAYERS,
                  0.6F,
                  0.95F + itemRand.nextFloat() / 10.0F
               );
         }

         return hurtdamage;
      }

      @Override
      public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, RenderType type, float partialTicks) {
         if (type == RenderType.BODY) {
            GlStateManager.pushMatrix();
            GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
            Helper.rotateIfSneaking(player);
            Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            Minecraft.getMinecraft().getRenderItem().renderItem(stack, TransformType.HEAD);
            GlStateManager.popMatrix();
         }
      }

      public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
         tooltip.add("Releases mosquitoes when you take damage");
         super.addInformation(stack, worldIn, tooltip, flagIn);
      }
   }

   public static class PersistencePendent extends Item implements IBauble, IRenderBauble, IItemHurted, IAttributedBauble {
      public PersistencePendent() {
         this.setRegistryName("persistence_pendent");
         this.setCreativeTab(CreativeTabs.COMBAT);
         this.setTranslationKey("persistence_pendent");
         this.setMaxStackSize(1);
      }

      @Override
      public BaubleType getBaubleType(ItemStack itemstack) {
         return BaubleType.AMULET;
      }

      @Override
      public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
         NBTHelper.GiveNBTfloat(itemstack, 0.0F, "charge");
         float i = NBTHelper.GetNBTfloat(itemstack, "charge");
         if (i < 10.0F) {
            NBTHelper.AddNBTfloat(itemstack, 0.04F, "charge");
         }
      }

      @Override
      public float onHurtWithItem(float hurtdamage, ItemStack stack, EntityPlayer player, DamageSource source) {
         if (source.getTrueSource() != null) {
            float i = NBTHelper.GetNBTfloat(stack, "charge");
            NBTHelper.SetNBTfloat(stack, Math.max(i - hurtdamage, 0.0F), "charge");
            hurtdamage = Math.max(hurtdamage - i, 0.0F);
         }

         return hurtdamage;
      }

      @Override
      public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, RenderType type, float partialTicks) {
         if (type == RenderType.BODY) {
            GlStateManager.pushMatrix();
            GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
            Helper.rotateIfSneaking(player);
            Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            Minecraft.getMinecraft().getRenderItem().renderItem(stack, TransformType.HEAD);
            GlStateManager.popMatrix();
         }
      }

      @Override
      public IAttribute getAttribute() {
         return SharedMonsterAttributes.ATTACK_SPEED;
      }

      @Override
      public double value() {
         return 0.1;
      }

      @Override
      public int operation() {
         return 0;
      }

      @Override
      public String itemName() {
         return "persistence_pendent";
      }

      @Override
      public boolean useMultimap() {
         return true;
      }

      @Override
      public Multimap<String, AttributeModifier> getAttributeModifiers(EntityPlayer player, int equipmentSlot, ItemStack itemstack) {
         Multimap<String, AttributeModifier> multimap = HashMultimap.create();
         UUID uuid = UUID.fromString("CB2F4" + equipmentSlot + "D3-64" + equipmentSlot + "A-4F78-A497-9C56A33DB" + equipmentSlot + "BB");
         multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(uuid, "attack speed modifier", 0.1, 0));
         multimap.put(SharedMonsterAttributes.KNOCKBACK_RESISTANCE.getName(), new AttributeModifier(uuid, "knockback resistance modifier", 0.2, 0));
         return multimap;
      }

      public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
         tooltip.add("Adds +0.1 Melee attack speed, +0.2 Knockback resistance");
         tooltip.add("and accumulates charge (up to 10) that protects against damage.");
         tooltip.add("Works with damage from mobs and players");
         super.addInformation(stack, worldIn, tooltip, flagIn);
      }
   }

   public static class SelectiveLevitator extends Item implements IBauble {
      public SelectiveLevitator() {
         this.setRegistryName("selective_levitator");
         this.setCreativeTab(CreativeTabs.COMBAT);
         this.setTranslationKey("selective_levitator");
         this.setMaxStackSize(1);
      }

      @Override
      public BaubleType getBaubleType(ItemStack itemstack) {
         return BaubleType.TRINKET;
      }

      @Override
      public void onWornTick(ItemStack itemstack, EntityLivingBase entityIn) {
         if (!entityIn.world.isRemote) {
            if (entityIn.ticksExisted % 11 == 0) {
               for (Entity entity : entityIn.world.getEntitiesWithinAABBExcludingEntity(entityIn, entityIn.getEntityBoundingBox().grow(32.0))) {
                  if (entity instanceof EntityItem || entity instanceof EntityCoin) {
                     entity.setNoGravity(true);
                  }
               }
            }
         } else if (entityIn.ticksExisted % 9 == 0) {
            List<Entity> list = entityIn.world.getEntitiesWithinAABBExcludingEntity(entityIn, entityIn.getEntityBoundingBox().grow(32.0));
            int max = 30;

            for (Entity entityx : list) {
               if (entityx instanceof EntityItem || entityx instanceof EntityCoin) {
                  entityIn.world
                     .spawnParticle(EnumParticleTypes.END_ROD, entityx.posX, entityx.posY, entityx.posZ, 0.0, 0.0, 0.0, new int[0]);
                  if (--max <= 0) {
                     break;
                  }
               }
            }
         }
      }

      public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
         tooltip.add("Levitates all items and coins in large area, preventing them from falling into the void");
         super.addInformation(stack, worldIn, tooltip, flagIn);
      }
   }

   public static class SpiritThorn extends Item implements IBauble, IRenderBauble, IItemHurted, IAttributedBauble {
      public SpiritThorn() {
         this.setRegistryName("spirit_thorn");
         this.setCreativeTab(CreativeTabs.COMBAT);
         this.setTranslationKey("spirit_thorn");
         this.setMaxStackSize(1);
      }

      @Override
      public BaubleType getBaubleType(ItemStack itemstack) {
         return BaubleType.AMULET;
      }

      @Override
      public float onHurtWithItem(float hurtdamage, ItemStack stack, EntityPlayer player, DamageSource source) {
         if (!player.world.isRemote
            && !player.getCooldownTracker().hasCooldown(stack.getItem())
            && source.getTrueSource() != null
            && source.getTrueSource() instanceof EntityLivingBase) {
            ToxicomaniaMobsPack.Mosquito mosq = new ToxicomaniaMobsPack.Mosquito(
               player.world, player, (EntityLivingBase)source.getTrueSource(), false, true
            );
            mosq.setLocationAndAngles(
               player.posX, player.posY + itemRand.nextFloat(), player.posZ, itemRand.nextFloat() * 360.0F, 0.0F
            );
            mosq.motionX = (itemRand.nextFloat() - 0.5F) / 2.0F;
            mosq.motionZ = (itemRand.nextFloat() - 0.5F) / 2.0F;
            mosq.velocityChanged = true;
            IAttributeInstance attr = mosq.getEntityAttribute(PropertiesRegistry.ENTITY_COLOR_RED_MAX);
            attr.setBaseValue(0.5);
            IAttributeInstance attr2 = mosq.getEntityAttribute(PropertiesRegistry.ENTITY_COLOR_BLUE_MAX);
            attr.setBaseValue(0.5);
            player.world.spawnEntity(mosq);
            if (hurtdamage > 4.0F || itemRand.nextFloat() < 0.6) {
               mosq = new ToxicomaniaMobsPack.Mosquito(player.world, player, (EntityLivingBase)source.getTrueSource(), false, true);
               mosq.setLocationAndAngles(
                  player.posX, player.posY + itemRand.nextFloat(), player.posZ, itemRand.nextFloat() * 360.0F, 0.0F
               );
               mosq.motionX = (itemRand.nextFloat() - 0.5F) / 2.0F;
               mosq.motionZ = (itemRand.nextFloat() - 0.5F) / 2.0F;
               mosq.velocityChanged = true;
               attr = mosq.getEntityAttribute(PropertiesRegistry.ENTITY_COLOR_RED_MAX);
               attr.setBaseValue(0.5);
               attr2 = mosq.getEntityAttribute(PropertiesRegistry.ENTITY_COLOR_BLUE_MAX);
               attr.setBaseValue(0.5);
               player.world.spawnEntity(mosq);
            }

            player.getCooldownTracker().setCooldown(stack.getItem(), 25);
            player.world
               .playSound(
                  (EntityPlayer)null,
                  player.posX,
                  player.posY,
                  player.posZ,
                  Sounds.larva_water_attack,
                  SoundCategory.PLAYERS,
                  0.6F,
                  0.95F + itemRand.nextFloat() / 10.0F
               );
         }

         return hurtdamage;
      }

      @Override
      public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, RenderType type, float partialTicks) {
         if (type == RenderType.BODY) {
            GlStateManager.pushMatrix();
            GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
            Helper.rotateIfSneaking(player);
            Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            Minecraft.getMinecraft().getRenderItem().renderItem(stack, TransformType.HEAD);
            GlStateManager.popMatrix();
         }
      }

      public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
         tooltip.add("Releases mosquitoes when you take damage");
         tooltip.add("Mosquito attack cause Weakness");
         tooltip.add("+5 Leadership");
         super.addInformation(stack, worldIn, tooltip, flagIn);
      }

      @Override
      public IAttribute getAttribute() {
         return PropertiesRegistry.LEADERSHIP;
      }

      @Override
      public double value() {
         return 5.0;
      }

      @Override
      public int operation() {
         return 0;
      }

      @Override
      public String itemName() {
         return "spirit_thorn";
      }
   }

   public static class SpringerWaistband extends Item implements IBauble, IRenderBauble {
      public static ResourceLocation textur = new ResourceLocation("arpg:textures/circle.png");

      public SpringerWaistband() {
         this.setRegistryName("springer_waistband");
         this.setCreativeTab(CreativeTabs.COMBAT);
         this.setTranslationKey("springer_waistband");
         this.setMaxStackSize(1);
         this.setMaxDamage(3);
      }

      @Override
      public BaubleType getBaubleType(ItemStack itemstack) {
         return BaubleType.BELT;
      }

      @Override
      public void onWornTick(ItemStack itemstack, EntityLivingBase entityIn) {
         Item itemIn = itemstack.getItem();
         if (entityIn instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entityIn;
            boolean click = Keys.isPressedDoubleJump(player);
            int dm = itemstack.getItemDamage();
            if (click && (dm == 0 || dm == 2)) {
               player.motionY = player.motionY * 0.1 + 0.5;
               player.fallDistance = 0.0F;
               itemstack.damageItem(1, entityIn);
               player.world
                  .playSound(
                     (EntityPlayer)null,
                     player.posX,
                     player.posY,
                     player.posZ,
                     Sounds.cyber_jump,
                     SoundCategory.PLAYERS,
                     0.5F,
                     0.95F + itemRand.nextFloat() / 10.0F
                  );
               if (!player.world.isRemote) {
                  PacketDoSomethingToClients packet = new PacketDoSomethingToClients();
                  packet.writeargs(player.posX, player.posY + 0.3, player.posZ, 0.0, 0.0, 0.0, 4);
                  PacketHandler.sendToAllAround(packet, player.world, player.posX, player.posY, player.posZ, 32.0);
               }
            }

            if (!click && dm == 1) {
               itemstack.damageItem(1, entityIn);
            }

            if (player.onGround && !click) {
               itemstack.setItemDamage(0);
            }
         }
      }

      public static void particle(World world, double x, double y, double z) {
         GUNParticle bigboom = new GUNParticle(textur, 0.7F, 0.0F, 12, 240, world, x, y, z, 0.0F, -0.1F, 0.0F, 0.5F, 0.75F, 1.0F, true, 1);
         bigboom.dropped = true;
         bigboom.scaleTickAdding = -0.05F;
         bigboom.alphaGlowing = true;
         world.spawnEntity(bigboom);
         bigboom = new GUNParticle(textur, 0.5F, 0.0F, 13, 240, world, x, y, z, 0.0F, -0.15F, 0.0F, 0.5F, 0.75F, 1.0F, true, 1);
         bigboom.dropped = true;
         bigboom.scaleTickAdding = -0.035F;
         bigboom.alphaGlowing = true;
         world.spawnEntity(bigboom);
         bigboom = new GUNParticle(textur, 0.35F, 0.0F, 14, 240, world, x, y, z, 0.0F, -0.2F, 0.0F, 0.5F, 0.75F, 1.0F, true, 1);
         bigboom.dropped = true;
         bigboom.scaleTickAdding = -0.015F;
         bigboom.alphaGlowing = true;
         world.spawnEntity(bigboom);
      }

      @Override
      public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, RenderType type, float partialTicks) {
         if (type == RenderType.BODY) {
            GlStateManager.pushMatrix();
            GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
            Helper.rotateIfSneaking(player);
            Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            Minecraft.getMinecraft().getRenderItem().renderItem(stack, TransformType.HEAD);
            GlStateManager.translate(0.0, 0.0, -0.3);
            Minecraft.getMinecraft().getRenderItem().renderItem(stack, TransformType.HEAD);
            GlStateManager.popMatrix();
         }
      }

      public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
         tooltip.add("Allows do triple jump");
         super.addInformation(stack, worldIn, tooltip, flagIn);
      }
   }

   public static class Thornkeeper extends Item implements IBauble, IRenderBauble {
      public Thornkeeper() {
         this.setRegistryName("thornkeeper");
         this.setCreativeTab(CreativeTabs.COMBAT);
         this.setTranslationKey("thornkeeper");
         this.setMaxStackSize(1);
      }

      @Override
      public BaubleType getBaubleType(ItemStack itemstack) {
         return BaubleType.CHARM;
      }

      @Override
      public void onWornTick(ItemStack itemstack, EntityLivingBase entity) {
         if (!entity.world.isRemote && entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entity;
            if (!player.getCooldownTracker().hasCooldown(itemstack.getItem())) {
               AxisAlignedBB aabb = player.getEntityBoundingBox().grow(5.0, 4.0, 5.0);
               List<EntityLivingBase> list = player.world.getEntitiesWithinAABB(EntityLivingBase.class, aabb);
               double sqdistmin = 9999999.0;
               EntityLivingBase target = null;

               for (EntityLivingBase base : list) {
                  if (base instanceof IMob && Team.checkIsOpponent(entity, base)) {
                     double sqdist = base.getDistanceSq(entity);
                     if (sqdist < sqdistmin) {
                        sqdistmin = sqdist;
                        target = base;
                     }
                  }
               }

               if (target != null) {
                  ThornkeeperShoot shoot = new ThornkeeperShoot(player.world, player, itemstack, Mana.getMagicPowerMax(player));
                  shoot.setLocationAndAngles(player.posX, player.posY + 1.0, player.posZ, 0.0F, 0.0F);
                  SuperKnockback.applyMove(
                     shoot,
                     target.isInWater() ? -3.0F : -1.3F,
                     target.posX,
                     target.posY + (target.isInWater() ? target.height / 2.0F : 1.5),
                     target.posZ
                  );
                  player.world.spawnEntity(shoot);
                  player.world
                     .playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.larva_water_attack,
                        SoundCategory.AMBIENT,
                        0.7F,
                        0.9F + itemRand.nextFloat() / 5.0F
                     );
                  player.getCooldownTracker().setCooldown(itemstack.getItem(), 50);
               }
            }
         }
      }

      @Override
      public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, RenderType type, float partialTicks) {
         if (type == RenderType.BODY) {
            GlStateManager.pushMatrix();
            HandBaubleRender.doRenderLayer(player, stack, EnumHandSide.LEFT);
            GlStateManager.popMatrix();
         }
      }

      public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
         tooltip.add("Periodically shoots thorny balls to mobs");
         super.addInformation(stack, worldIn, tooltip, flagIn);
      }
   }

   public static class VenomedDagger extends Item implements IBauble, IItemDamaging {
      public VenomedDagger() {
         this.setRegistryName("venomed_dagger");
         this.setCreativeTab(CreativeTabs.COMBAT);
         this.setTranslationKey("venomed_dagger");
         this.setMaxStackSize(1);
      }

      @Override
      public BaubleType getBaubleType(ItemStack itemstack) {
         return BaubleType.TRINKET;
      }

      @Override
      public float onCauseDamageWithItem(float hurtdamage, ItemStack stack, EntityPlayer playerattacker, EntityLivingBase target, DamageSource source) {
         if ((source.getImmediateSource() == null || source.getImmediateSource() == source.getTrueSource()) && !source.isMagicDamage() && playerattacker != null) {
            PotionEffect debaff = new PotionEffect(MobEffects.WEAKNESS, 40);
            target.addPotionEffect(debaff);
         }

         return hurtdamage;
      }

      public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
         tooltip.add("Melee attack cause Weaknes");
         super.addInformation(stack, worldIn, tooltip, flagIn);
      }
   }
}
