package com.Vivern.Arpg.elements;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import baubles.api.render.IRenderBauble;
import com.Vivern.Arpg.elements.models.FairyWingsModel;
import com.Vivern.Arpg.main.Booom;
import com.Vivern.Arpg.main.IAttributedBauble;
import com.Vivern.Arpg.main.Keys;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.PlayerButtonTracker;
import com.Vivern.Arpg.main.PropertiesRegistry;
import com.Vivern.Arpg.main.Sounds;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.Random;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

public class FairyWings extends Item implements IBauble, IAttributedBauble, IWings, IRenderBauble {
   public static FairyWingsModel model = new FairyWingsModel();
   public static ResourceLocation texture = new ResourceLocation("arpg:textures/fairy_wings_model_tex.png");

   public FairyWings() {
      this.setRegistryName("fairy_wings");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("fairy_wings");
      this.setMaxDamage(2500);
      this.setMaxStackSize(1);
   }

   @Override
   public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
      return player instanceof EntityPlayer
         ? IWings.isEquippableWithChestplate(((ItemStack)((EntityPlayer)player).inventory.armorInventory.get(2)).getItem())
         : false;
   }

   @Override
   public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, RenderType type, float partialTicks) {
      if (type == RenderType.BODY) {
         float sweep = (-player.rotationPitch + 90.0F) / 180.0F;
         float animationflap = NBTHelper.GetNBTint(stack, "animationflap");
         float flapnormal = (float)Math.sin(animationflap * 0.4F) * animationflap;
         float animationforw = NBTHelper.GetNBTint(stack, "animationforw");
         int ticksflying = NBTHelper.GetNBTint(stack, "animationinair");
         float nofly = 1.0F - ticksflying / 10.0F;
         GlStateManager.pushMatrix();
         GL11.glEnable(3042);
         GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
         Helper.rotateIfSneaking(player);
         if (player.isSneaking()) {
            GlStateManager.translate(0.0F, -0.4F, 0.2F);
         }

         Minecraft.getMinecraft().renderEngine.bindTexture(texture);
         GlStateManager.scale(0.09F, 0.09F, 0.09F);
         GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.translate(0.0, -1.2F, -1.0);
         model.render(player, sweep, flapnormal, animationforw, nofly, player.isSneaking() ? 0.7F : 0.0F, 1.0F);
         GL11.glDisable(3042);
         GlStateManager.popMatrix();
      }
   }

   @Override
   public void onWornTick(ItemStack itemstack, EntityLivingBase entityIn) {
      int maxb = 4000;
      int boostsPerOne = 40;
      float velocity = 0.2F;
      Item itemIn = itemstack.getItem();
      if (entityIn instanceof EntityPlayer) {
         NBTHelper.GiveNBTint(itemstack, 0, "boost");
         NBTHelper.GiveNBTint(itemstack, 0, "animationflap");
         NBTHelper.GiveNBTint(itemstack, 0, "animationforw");
         NBTHelper.GiveNBTint(itemstack, 0, "animationinair");
         int animationflap = NBTHelper.GetNBTint(itemstack, "animationflap");
         int animationforw = NBTHelper.GetNBTint(itemstack, "animationforw");
         if (animationflap > 0) {
            NBTHelper.AddNBTint(itemstack, -1, "animationflap");
         }

         if (animationforw > 0) {
            NBTHelper.AddNBTint(itemstack, -1, "animationforw");
         }

         if (NBTHelper.GetNBTint(itemstack, "animationinair") > 0 && entityIn.onGround) {
            NBTHelper.AddNBTint(itemstack, -1, "animationinair");
         }

         if (NBTHelper.GetNBTint(itemstack, "animationinair") < 10 && !entityIn.onGround) {
            NBTHelper.AddNBTint(itemstack, 1, "animationinair");
         }

         int boost = NBTHelper.GetNBTint(itemstack, "boost");
         if (boost < maxb) {
            NBTHelper.AddNBTint(itemstack, 1, "boost");
         }

         EntityPlayer player = (EntityPlayer)entityIn;
         IWings.chestplateReturnToInv(player);
         boolean clickdoublejump = PlayerButtonTracker.getDoubleJump(player);
         int damage = itemstack.getItemDamage();
         boolean flying = (Boolean)player.getDataManager().get(PropertiesRegistry.FLYING);
         boolean cool = player.getCooldownTracker().hasCooldown(itemIn);
         boolean clickforw = GameSettings.isKeyDown(Keys.FORWARD);
         boolean clicksprint = GameSettings.isKeyDown(Keys.SPRINT);
         boolean clickback = GameSettings.isKeyDown(Keys.BACK);
         boolean jump = GameSettings.isKeyDown(Keys.JUMP);
         boolean pressflying = false;
         if (!player.onGround && clicksprint && damage < this.getMaxDamage(itemstack) && !flying && !cool) {
            player.getDataManager().set(PropertiesRegistry.FLYING, true);
            pressflying = true;
            if (player instanceof EntityPlayerSP) {
               Minecraft.getMinecraft().getSoundHandler().playSound(new FairyWingsSound((EntityPlayerSP)player));
            }
         }

         if (damage < this.getMaxDamage(itemstack)) {
            if (player.ticksExisted % 100 == 0 && flying) {
               itemstack.damageItem(1, entityIn);
            }

            if (boost > boostsPerOne) {
               boolean sucess = false;
               if (!clickback || !flying && !jump) {
                  if (clickforw && flying) {
                     if (!cool) {
                        float rotationYawIn = player.rotationYaw;
                        float rotationPitchIn = player.rotationPitch;
                        float x = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0))
                           * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
                        float y = -MathHelper.sin(rotationPitchIn * (float) (Math.PI / 180.0));
                        float z = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0))
                           * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
                        float f = MathHelper.sqrt(x * x + y * y + z * z);
                        x /= f;
                        y /= f;
                        z /= f;
                        x *= velocity;
                        y *= velocity;
                        z *= velocity;
                        multipleMotion(player, 0.9F);
                        player.motionX += x;
                        player.motionY += y;
                        player.motionZ += z;
                        if (!player.world.isRemote) {
                           player.world
                              .playSound(
                                 (EntityPlayer)null,
                                 player.posX,
                                 player.posY,
                                 player.posZ,
                                 SoundEvents.ENTITY_ENDERDRAGON_FLAP,
                                 SoundCategory.PLAYERS,
                                 0.5F,
                                 0.9F + itemRand.nextFloat() / 5.0F
                              );
                        }

                        sucess = true;
                     }

                     if (animationforw == 0) {
                        NBTHelper.SetNBTint(itemstack, 8, "animationforw");
                     }
                  } else if (jump) {
                     if (!cool) {
                        player.motionY *= 0.8;
                        player.motionY += velocity + 0.15;
                        player.fallDistance = 0.0F;
                        if (!player.world.isRemote) {
                           player.world
                              .playSound(
                                 (EntityPlayer)null,
                                 player.posX,
                                 player.posY,
                                 player.posZ,
                                 SoundEvents.ENTITY_ENDERDRAGON_FLAP,
                                 SoundCategory.PLAYERS,
                                 0.5F,
                                 0.9F + itemRand.nextFloat() / 5.0F
                              );
                        }

                        sucess = true;
                     }

                     if (flying) {
                        if (animationflap == 0) {
                           NBTHelper.SetNBTint(itemstack, 8, "animationflap");
                        }
                     } else if (animationforw == 0) {
                        NBTHelper.SetNBTint(itemstack, 8, "animationforw");
                     }
                  }
               } else {
                  if (!cool) {
                     float rotationYawIn = player.rotationYaw;
                     float rotationPitchIn = player.rotationPitch;
                     float x = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0))
                        * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
                     float y = -MathHelper.sin(rotationPitchIn * (float) (Math.PI / 180.0));
                     float z = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0))
                        * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
                     float f = MathHelper.sqrt(x * x + y * y + z * z);
                     x /= f;
                     y /= f;
                     z /= f;
                     x *= -velocity;
                     y *= -velocity;
                     z *= -velocity;
                     multipleMotion(player, 0.9F);
                     player.motionX += x;
                     player.motionY += y;
                     player.motionZ += z;
                     if (!player.world.isRemote) {
                        player.world
                           .playSound(
                              (EntityPlayer)null,
                              player.posX,
                              player.posY,
                              player.posZ,
                              SoundEvents.ENTITY_ENDERDRAGON_FLAP,
                              SoundCategory.PLAYERS,
                              0.5F,
                              0.9F + itemRand.nextFloat() / 5.0F
                           );
                     }

                     sucess = true;
                  }

                  if (animationflap == 0) {
                     NBTHelper.SetNBTint(itemstack, 8, "animationflap");
                  }
               }

               if (sucess && !cool) {
                  NBTHelper.AddNBTint(itemstack, -boostsPerOne, "boost");
                  if (!player.world.isRemote) {
                     player.getCooldownTracker().setCooldown(this, 4);
                  }
               }

               if (player.isSneaking() && flying) {
                  multipleMotion(player, 0.95F);
                  player.motionY += 0.05;
                  player.fallDistance = 0.0F;
                  NBTHelper.AddNBTint(itemstack, -5, "boost");
                  if (animationflap == 0) {
                     NBTHelper.SetNBTint(itemstack, 8, "animationflap");
                  }
               }
            }

            if (flying) {
               float f = MathHelper.sqrt(
                  player.motionX * player.motionX + player.motionZ * player.motionZ + player.motionY * player.motionY
               );
               float f1 = f / 2.0F;
               if (f1 > 0.9F) {
                  multipleMotion(player, 0.95F);
               }
            }
         } else {
            NBTHelper.SetNBTint(itemstack, 0, "boost");
            if (!entityIn.world.isRemote) {
               player.getDataManager().set(PropertiesRegistry.FLYING, false);
            }
         }

         if (player.onGround) {
            if (boost < maxb) {
               NBTHelper.AddNBTint(itemstack, 49, "boost");
            }

            if (!entityIn.world.isRemote) {
               player.getDataManager().set(PropertiesRegistry.FLYING, false);
            }
         }
      }
   }

   public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
      NBTHelper.SetNBTint(stack, 0, "boost");
   }

   public static void multipleMotion(EntityLivingBase entity, float mult) {
      entity.motionX *= mult;
      entity.motionY *= mult;
      entity.motionZ *= mult;
   }

   @Override
   public BaubleType getBaubleType(ItemStack itemstack) {
      return BaubleType.BODY;
   }

   @Override
   public IAttribute getAttribute() {
      return PropertiesRegistry.JUMP_HEIGHT;
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
      return "fairy_wings";
   }

   @Override
   public boolean useMultimap() {
      return true;
   }

   @Override
   public Multimap<String, AttributeModifier> getAttributeModifiers(EntityPlayer player, int equipmentSlot, ItemStack itemstack) {
      Multimap<String, AttributeModifier> multimap = HashMultimap.create();
      UUID uuid = UUID.fromString("CB2F4" + equipmentSlot + "D3-64" + equipmentSlot + "A-4F78-A497-9C56A33DB" + equipmentSlot + "BB");
      multimap.put(PropertiesRegistry.JUMP_HEIGHT.getName(), new AttributeModifier(uuid, "jump height modifier", 0.1, 0));
      multimap.put(PropertiesRegistry.MAGIC_POWER_MAX.getName(), new AttributeModifier(uuid, "magic power modifier", 0.1, 0));
      return multimap;
   }

   public int getChargesCount(ItemStack stack) {
      return NBTHelper.GetNBTint(stack, "boost") / 1000;
   }

   @Override
   public int getMaxFlyTime(ItemStack stack) {
      return 0;
   }

   @SideOnly(Side.CLIENT)
   public class FairyWingsSound extends MovingSound {
      private final EntityPlayerSP player;
      private int time;
      Random rand = new Random();

      public FairyWingsSound(EntityPlayerSP p_i47113_1_) {
         super(Sounds.toxic_wings_flying, SoundCategory.PLAYERS);
         this.player = p_i47113_1_;
         this.repeat = true;
         this.repeatDelay = 0;
         this.volume = 0.1F;
      }

      public void update() {
         this.time++;
         if (!this.player.isDead && (this.time <= 20 || this.player.isElytraFlying())) {
            this.xPosF = (float)this.player.posX;
            this.yPosF = (float)this.player.posY;
            this.zPosF = (float)this.player.posZ;
            float f = MathHelper.sqrt(
               this.player.motionX * this.player.motionX
                  + this.player.motionZ * this.player.motionZ
                  + this.player.motionY * this.player.motionY
            );
            float f1 = f / 2.0F;
            if (f1 > 0.7F && Booom.lastTick == 0) {
               Booom.lastTick = 8;
               Booom.frequency = 4.0F;
               Booom.x = (float)this.rand.nextGaussian();
               Booom.y = (float)this.rand.nextGaussian();
               Booom.z = (float)this.rand.nextGaussian();
               Booom.power = f1 / 25.0F;
            }

            if (f >= 0.01) {
               this.volume = MathHelper.clamp(f1 * f1, 0.0F, 1.0F);
            } else {
               this.volume = 0.0F;
            }

            if (this.time < 20) {
               this.volume = 0.0F;
            } else if (this.time < 40) {
               this.volume = (float)(this.volume * ((this.time - 20) / 20.0));
            }

            float f2 = 0.8F;
            if (this.volume > 0.8F) {
               this.pitch = 1.0F + (this.volume - 0.8F);
            } else {
               this.pitch = 1.0F;
            }
         } else {
            this.donePlaying = true;
         }
      }
   }
}
