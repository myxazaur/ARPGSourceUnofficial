package com.Vivern.Arpg.renders;

import com.Vivern.Arpg.container.GUIResearchTable;
import com.Vivern.Arpg.elements.AbstractMiningTool;
import com.Vivern.Arpg.elements.Beaker;
import com.Vivern.Arpg.elements.Instancer;
import com.Vivern.Arpg.elements.ItemCalibrationThing;
import com.Vivern.Arpg.elements.ItemSpellRoll;
import com.Vivern.Arpg.elements.animation.EnumFlick;
import com.Vivern.Arpg.elements.animation.Flicks;
import com.Vivern.Arpg.elements.models.AbstractMobModel;
import com.Vivern.Arpg.elements.models.BeakerModel;
import com.Vivern.Arpg.elements.models.CanisterModel;
import com.Vivern.Arpg.elements.models.RollModel;
import com.Vivern.Arpg.elements.models.SpellPliersModel;
import com.Vivern.Arpg.main.AnimationTimer;
import com.Vivern.Arpg.main.ColorConverters;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.ShardType;
import com.Vivern.Arpg.main.Spell;
import com.Vivern.Arpg.proxy.ClientProxy;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.CullFace;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class TEISROther extends TileEntityItemStackRenderer {
   public static TileEntityItemStackRenderer instance = new TEISROther();
   private final BeakerModel beakerModel = new BeakerModel();
   private final SpellPliersModel spellPliersModel = new SpellPliersModel();
   private final RollModel rollModel = new RollModel(16);
   private final RollModel rollModel32 = new RollModel(32);
   private final CanisterModel canisterModel = new CanisterModel();
   private final ResourceLocation texSpellPliersModel = new ResourceLocation("arpg:textures/spell_pliers_model_tex.png");
   private final ResourceLocation texBeakerModel = new ResourceLocation("arpg:textures/beaker_model_tex.png");
   private final ResourceLocation texRollModel = new ResourceLocation("arpg:textures/roll_model_tex.png");
   private final ResourceLocation texCanisterModel = new ResourceLocation("arpg:textures/canister_model_tex.png");
   private final ResourceLocation texEnch1 = new ResourceLocation("arpg:textures/sobig-6.png");
   public static ResourceLocation beaker_fluid_unstable = new ResourceLocation("arpg:textures/beaker_fluid_unstable.png");
   public static ResourceLocation beaker_fluid_etheric = new ResourceLocation("arpg:textures/beaker_fluid_etheric.png");
   public static ResourceLocation beaker_fluid_unstable_top = new ResourceLocation("arpg:textures/beaker_fluid_unstable_top.png");
   public static ResourceLocation beaker_fluid_etheric_top = new ResourceLocation("arpg:textures/beaker_fluid_etheric_top.png");
   public static final ResourceLocation RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");

   public void renderByItem(ItemStack itemStackIn) {
      this.renderByItem(itemStackIn, 1.0F);
   }

   private byte getModifier(Beaker.BeakerFluid beakerFluid, int indx) {
      return beakerFluid.modifiers[indx] == 2 ? 3 : beakerFluid.modifiers[indx];
   }

   public void renderByItem(ItemStack itemstack, float partialTicks) {
      Item item = itemstack.getItem();
      if (item == ItemsRegister.BEAKER) {
         Beaker.BeakerFluid beakerFluid = Beaker.readFromNbt(itemstack, 16);
         if (!beakerFluid.isEmpty) {
            GlStateManager.pushMatrix();
            GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
            float ypos = -1.7F;
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
            float boundOffset = 0.025F;

            for (int i = 0; i <= beakerFluid.maxLay; i++) {
               if (beakerFluid.elements[i] > 0) {
                  byte modifier = this.getModifier(beakerFluid, i);
                  ShardType shardType = ShardType.byId(beakerFluid.elements[i]);
                  float height = 0.1F;
                  float[] color = shardType == ShardType.VOID && modifier != 0
                     ? RetortTESR.void_additional_color
                     : new float[]{shardType.colorR, shardType.colorG, shardType.colorB};
                  renderSides(0.0, ypos + height * i, 0.0, color, color, 0.125F, height, partialTicks, 0.4F, this.getModifier(beakerFluid, i));
                  boolean firstLay = i == 0;
                  boolean lastLay = i == beakerFluid.maxLay;
                  if (firstLay && lastLay) {
                     renderSides(0.0, ypos + height * i - boundOffset, 0.0, color, color, 0.125F, height + boundOffset * 2.0F, partialTicks, 0.4F, modifier);
                     renderCap(0.0, ypos + height * i - boundOffset, 0.0, color, false, 0.125F, height, partialTicks, 0.4F, modifier);
                     renderCap(0.0, ypos + height * i, 0.0, color, true, 0.125F, height + boundOffset, partialTicks, 0.4F, modifier);
                  } else if (firstLay) {
                     renderSides(0.0, ypos + height * i - boundOffset, 0.0, color, color, 0.125F, height + boundOffset, partialTicks, 0.4F, modifier);
                     renderCap(0.0, ypos + height * i - boundOffset, 0.0, color, false, 0.125F, height, partialTicks, 0.4F, modifier);
                  } else if (lastLay) {
                     renderSides(0.0, ypos + height * i, 0.0, color, color, 0.125F, height + boundOffset, partialTicks, 0.4F, modifier);
                     renderCap(0.0, ypos + height * i, 0.0, color, true, 0.125F, height + boundOffset, partialTicks, 0.4F, modifier);
                  } else {
                     renderSides(0.0, ypos + height * i, 0.0, color, color, 0.125F, height, partialTicks, 0.4F, modifier);
                  }
               }
            }

            float[] prevColor = null;

            for (int ix = 0; ix <= beakerFluid.maxLay; ix++) {
               if (beakerFluid.elements[ix] > 0) {
                  ShardType shardType = ShardType.byId(beakerFluid.elements[ix]);
                  GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, shardType == ShardType.VOID ? DestFactor.ONE_MINUS_SRC_ALPHA : DestFactor.ONE);
                  float height = 0.1F;
                  float[] color = new float[]{shardType.colorR, shardType.colorG, shardType.colorB};
                  if (prevColor == null) {
                     prevColor = color;
                  }

                  byte modifier = this.getModifier(beakerFluid, ix);
                  renderSides(0.0, ypos + height * ix, 0.0, color, prevColor, 0.1F, height, partialTicks, 0.6F, modifier);
                  if (ix == 0) {
                     renderCap(0.0, ypos + height * ix, 0.0, color, false, 0.1F, height, partialTicks, 0.6F, modifier);
                  }

                  if (ix == beakerFluid.maxLay) {
                     renderCap(0.0, ypos + height * ix, 0.0, color, true, 0.1F, height, partialTicks, 0.6F, modifier);
                  }

                  prevColor = color;
               }
            }

            for (int ixx = 0; ixx <= beakerFluid.maxLay; ixx++) {
               if (beakerFluid.elements[ixx] > 0) {
                  ShardType shardTypex = ShardType.byId(beakerFluid.elements[ixx]);
                  ShardType shardTypeFuture = ShardType.byId(beakerFluid.elements[Math.min(ixx + 1, beakerFluid.maxLay)]);
                  GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, shardTypex == ShardType.VOID ? DestFactor.ONE_MINUS_SRC_ALPHA : DestFactor.ONE);
                  float heightx = 0.1F;
                  float[] colorx = new float[]{shardTypex.colorR, shardTypex.colorG, shardTypex.colorB};
                  float[] futureColor = new float[]{shardTypeFuture.colorR, shardTypeFuture.colorG, shardTypeFuture.colorB};
                  renderSides(0.0, ypos + heightx * ixx, 0.0, futureColor, colorx, 0.145F, heightx, partialTicks, 0.18F, this.getModifier(beakerFluid, ixx));
               }
            }

            GlStateManager.popMatrix();
         }

         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         GlStateManager.pushMatrix();
         GlStateManager.enableBlend();
         GlStateManager.scale(0.1F, 0.1F, 0.1F);
         GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
         Minecraft.getMinecraft().renderEngine.bindTexture(this.texBeakerModel);
         this.beakerModel.render(null, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.disableBlend();
         GlStateManager.popMatrix();
      }

      if (item == ItemsRegister.SPELLPLIERS) {
         boolean picked = NBTHelper.GetNBTboolean(itemstack, "picked");
         float pickedAngle = picked ? 0.0F : -0.43F;
         GlStateManager.pushMatrix();
         GlStateManager.scale(0.09F, 0.09F, 0.09F);
         GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
         Minecraft.getMinecraft().renderEngine.bindTexture(this.texSpellPliersModel);
         this.spellPliersModel.render(null, 0.0F, pickedAngle, 1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.popMatrix();
         if (itemstack.isItemEnchanted()) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.09F, 0.09F, 0.09F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            GL11.glDisable(2896);
            GlStateManager.depthMask(false);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            float f = AnimationTimer.tick;
            float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
            float f2 = f * 0.01F;
            GlStateManager.translate(f1, f2, 0.0F);
            GlStateManager.matrixMode(5888);
            GlStateManager.enableBlend();
            GlStateManager.color(0.4F, 0.85F, 1.0F, 1.0F);
            GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
            this.spellPliersModel.render(null, 1.0F, pickedAngle, 1.0F, 1.0F, 1.0F, 1.0F);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            GlStateManager.matrixMode(5888);
            GlStateManager.disableBlend();
            GlStateManager.depthMask(true);
            GL11.glEnable(2896);
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
            GlStateManager.popMatrix();
         }

         if (picked && itemstack.getTagCompound().hasKey("picked_item")) {
            NBTTagCompound nbtTagCompound = itemstack.getTagCompound().getCompoundTag("picked_item");
            ItemStack releasedstack = new ItemStack(nbtTagCompound);
            GlStateManager.pushMatrix();
            GlStateManager.translate(-0.4F, -0.45F, -0.1875F);
            GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(45.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.scale(2.0F, 2.0F, 2.0F);
            SpellForgeTESR.renderItemMolten(releasedstack, MathHelper.clamp(NBTHelper.GetNBTint(itemstack, "heat") / 100.0F, 0.0F, 1.0F));
            GlStateManager.popMatrix();
         }
      }

      if (item == ItemsRegister.VIAL) {
         GlStateManager.pushMatrix();
         GlStateManager.translate(0.5F, 0.5F, 0.5F);
         ShardType shardTypex = ShardType.byId(MathHelper.clamp(itemstack.getMetadata(), 1, 12));
         float[] colorx = new float[]{shardTypex.colorR, shardTypex.colorG, shardTypex.colorB};
         float[] color2 = ShardType.additionalColors[shardTypex.id];
         float thickness = 0.02F;
         if (shardTypex == ShardType.WATER) {
            renderColoredGlowingCuboid(0.0, -0.46F, 0.0, colorx, color2, 0.15F, 0.22F, thickness, partialTicks, true, true, (byte)0);
         } else if (shardTypex == ShardType.AIR) {
            renderColoredGlowingCuboid(0.0, -0.46F, 0.0, colorx, color2, 0.03F, 0.5F, thickness, partialTicks, true, true, (byte)0);
         } else if (shardTypex == ShardType.POISON) {
            renderColoredGlowingCuboid(0.0, -0.46F, 0.0, colorx, color2, 0.08F, 0.37F, thickness, partialTicks, true, true, (byte)0);
         } else if (shardTypex == ShardType.FIRE) {
            renderColoredGlowingCuboid(0.0, -0.46F, 0.0, colorx, color2, 0.08F, 0.22F, thickness, partialTicks, true, true, (byte)0);
            renderColoredGlowingCuboid(0.0, -0.24F, 0.0, colorx, color2, 0.055F, 0.12F, thickness, partialTicks, false, true, (byte)0);
         } else if (shardTypex == ShardType.EARTH) {
            renderColoredGlowingCuboid(0.0, -0.46F, 0.0, colorx, color2, 0.15F, 0.11F, thickness, partialTicks, true, true, (byte)0);
            renderColoredGlowingCuboid(0.0, -0.35F, 0.0, colorx, color2, 0.085F, 0.11F, thickness, partialTicks, false, true, (byte)0);
         } else if (shardTypex == ShardType.DEATH) {
            renderColoredGlowingCuboid(0.0, -0.46F, 0.0, colorx, color2, 0.08F, 0.18F, thickness, partialTicks, true, false, (byte)0);
            renderColoredGlowingCuboid(0.0, -0.28F, 0.0, colorx, colorx, 0.15F, 0.185F, thickness, partialTicks, true, true, (byte)0);
         } else if (shardTypex == ShardType.LIVE) {
            renderColoredGlowingCuboid(0.0, -0.46F, 0.0, colorx, color2, 0.08F, 0.13F, thickness, partialTicks, true, false, (byte)0);
            renderColoredGlowingCuboid(0.0, -0.33F, 0.0, colorx, color2, 0.15F, 0.22F, thickness, partialTicks, true, true, (byte)0);
         } else if (shardTypex == ShardType.VOID) {
            renderColoredCuboid(0.0, -0.46F, 0.0, colorx, color2, 0.055F, 0.055F, 0.11F, thickness, partialTicks, true, true, (byte)0);
            renderColoredCuboid(0.0, -0.35F, 0.0, colorx, color2, 0.005F, 0.005F, 0.6F, thickness, partialTicks, false, true, (byte)0);
         } else if (shardTypex == ShardType.PLEASURE) {
            GlStateManager.pushMatrix();
            GlStateManager.rotate(45.0F, 0.0F, 0.0F, 1.0F);
            renderColoredGlowingCuboid(-0.25, -0.33F, 0.0, colorx, color2, 0.08F, 0.35F, thickness, partialTicks, true, true, (byte)0);
            GlStateManager.rotate(-90.0F, 0.0F, 0.0F, 1.0F);
            renderColoredGlowingCuboid(0.25, -0.17F, 0.0, colorx, colorx, 0.08F, 0.19F, thickness, partialTicks, false, true, (byte)0);
            GlStateManager.popMatrix();
            renderColoredGlowingCuboid(0.0, -0.2F, 0.0, colorx, color2, 0.025F, 0.3F, thickness, partialTicks, false, true, (byte)0);
         } else if (shardTypex == ShardType.PAIN) {
            GlStateManager.pushMatrix();
            GlStateManager.rotate(45.0F, 0.0F, 0.0F, 1.0F);
            renderColoredGlowingCuboid(-0.28F, -0.34F, 0.0, colorx, color2, 0.06F, 0.08F, 0.12F, thickness, partialTicks, true, true, (byte)0);
            GlStateManager.popMatrix();
            renderColoredGlowingCuboid(0.0, -0.34F, 0.0, color2, colorx, 0.03F, 0.37F, thickness, partialTicks, false, true, (byte)0);
         } else if (shardTypex == ShardType.ELECTRIC) {
            renderColoredGlowingCuboid(0.0, -0.46F, 0.0, colorx, color2, 0.15F, 0.09F, 0.17F, thickness, partialTicks, true, true, (byte)0);
         } else if (shardTypex == ShardType.COLD) {
            GlStateManager.pushMatrix();
            GlStateManager.rotate(45.0F, 0.0F, 0.0F, 1.0F);
            renderColoredGlowingCuboid(-0.225F, -0.325F, 0.0, colorx, color2, 0.1F, 0.04F, 0.2F, thickness, partialTicks, true, true, (byte)0);
            GlStateManager.popMatrix();
            renderColoredGlowingCuboid(0.0, -0.2F, 0.0, colorx, color2, 0.025F, 0.11F, thickness, partialTicks, false, true, (byte)0);
         }

         ItemStack vialStack = new ItemStack(ItemsRegister.vials[shardTypex.id - 1]);
         Minecraft.getMinecraft().getRenderItem().renderItem(vialStack, TransformType.NONE);
         this.renderEffect(vialStack, colorx, TransformType.NONE, shardTypex == ShardType.VOID);
         GlStateManager.popMatrix();
      }

      if (item == ItemsRegister.SPELLROLL) {
         float openness = GetMOP.getfromto((float)NBTHelper.GetNBTint(itemstack, "using") / ItemSpellRoll.useMaxTime, 0.2F, 0.8F);
         int burning = NBTHelper.GetNBTint(itemstack, "burning");
         int casting = NBTHelper.GetNBTint(itemstack, "casting");
         GlStateManager.pushMatrix();
         GlStateManager.scale(0.1F, 0.1F, 0.1F);
         GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
         if (burning > 0) {
            AbstractMobModel.light(240, false);
            Minecraft.getMinecraft().renderEngine.bindTexture(Instancer.texturesAnimation[Math.min(burning - 1, Instancer.texturesAnimation.length - 1)]);
            this.rollModel.render(null, openness, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
            AbstractMobModel.returnlight();
         } else {
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texRollModel);
            this.rollModel.render(null, openness, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
         }

         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         float scale = 0.04F;
         GlStateManager.scale(scale, scale, scale);
         GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.rotate(-100.43F, 0.0F, 1.0F, 0.0F);
         GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
         Spell[] spells = NBTHelper.readSpellsFromNbt(itemstack);
         if (spells != null) {
            float colorAdd = MathHelper.clamp(casting > 0 ? (20 - casting) / 19.0F * 1.25F : 0.0F, 0.0F, 1.25F);
            GlStateManager.color(
               Math.min(1.0F * colorAdd, 1.0F), Math.min(0.96F * colorAdd, 1.0F), Math.min(0.8F * colorAdd, 1.0F), 0.75F + 0.2F * colorAdd
            );
            if (colorAdd > 0.0F) {
               AbstractMobModel.light(240, false);
            }

            int maxSpells = Math.min(spells.length, 5);
            float xpos = -(4.0F + 16.0F * openness + maxSpells * 4.0F) * openness + -7.0F * (1.0F - openness);
            float ypos = -0.5F;
            float zpos = -12.1F;
            Minecraft.getMinecraft().renderEngine.bindTexture(GUIResearchTable.ADDITIONAL_RUNES);
            maxSpells = Math.min(spells.length, 1 + (int)(6.0F * openness));
            float spellsFullness = GetMOP.getfromto((float)spells.length, 1.0F, 7.0F);
            float margin = GetMOP.partial(5.53F, 8.0F, spellsFullness);
            float offsetToCenter = (38.0F - maxSpells * margin) / 2.0F;
            float posLeft = -6.0F - 32.0F * openness + offsetToCenter * openness;

            for (int ixxx = 0; ixxx < maxSpells; ixxx++) {
               float xxx = posLeft + ixxx * margin;
               Spell spell = spells[ixxx];
               if (xxx <= -3.0F && spell != null) {
                  int texId = spell.id - 1;
                  int xcell = texId % 14;
                  int ycell = texId / 14;
                  drawTextured3dModalRect(xxx, ypos, zpos, xcell * 9, ycell * 13, 9, 13, 5.53F, 8.0F);
               }
            }

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            if (colorAdd > 0.0F) {
               AbstractMobModel.returnlight();
            }
         }

         GlStateManager.popMatrix();
         if (casting > 0 && casting <= 57 && burning <= 0) {
            int castTex = MathHelper.clamp(casting / 3, 0, 18);
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.1F, 0.1F, 0.1F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            GL11.glDisable(2896);
            GlStateManager.depthMask(false);
            Minecraft.getMinecraft().renderEngine.bindTexture(ClientProxy.firedetex.get(castTex));
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
            AbstractMobModel.light(240, false);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
            this.rollModel32.render(null, openness, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
            AbstractMobModel.returnlight();
            GlStateManager.disableBlend();
            GlStateManager.depthMask(true);
            GL11.glEnable(2896);
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
            GlStateManager.popMatrix();
         }
      }

      if (item instanceof ItemCalibrationThing) {
         ItemCalibrationThing calibrationThing = (ItemCalibrationThing)item;
         calibrationThing.renderInTESR(null);
      }

      if (item == ItemsRegister.CANISTER) {
         FluidStack fluidStack = FluidUtil.getFluidContained(itemstack);
         if (fluidStack != null && fluidStack.amount > 0) {
            GlStateManager.pushMatrix();
            GlStateManager.translate(-0.39F, 1.69F, 0.39F);
            GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
            float cubesize = 0.78F;
            float progr = fluidStack.amount / 1000.0F * cubesize;
            GlStateManager.disableCull();
            RenderFluidHelper.drawFluidCube(cubesize, progr, progr / cubesize, fluidStack.getFluid(), fluidStack);
            GlStateManager.enableCull();
            GlStateManager.popMatrix();
         }

         GlStateManager.pushMatrix();
         GlStateManager.scale(0.1F, 0.1F, 0.1F);
         GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
         Minecraft.getMinecraft().renderEngine.bindTexture(this.texCanisterModel);
         this.canisterModel.render(null, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.popMatrix();
      }

      if (item instanceof AbstractMiningTool) {
         AbstractMiningTool miningTool = (AbstractMiningTool)item;
         float anim1 = Flicks.instance.getClientAnimation(itemstack, EnumFlick.SHOOT, Minecraft.getMinecraft().getRenderPartialTicks(), true);
         GlStateManager.pushMatrix();
         if (NBTHelper.GetNBTboolean(itemstack, "tremor")) {
            GlStateManager.translate(0.0F, MathHelper.sin(AnimationTimer.tick * 0.83F) * 0.006F, 0.0F);
         }

         GlStateManager.pushMatrix();
         GlStateManager.scale(0.09F, 0.09F, 0.09F);
         GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
         Minecraft.getMinecraft().renderEngine.bindTexture(miningTool.toolsSet.mainTexture);
         miningTool.getModel().render(null, anim1, 0.0F, miningTool.asEnergyItem() == null ? 2.0F : 1.0F, 0.0F, 0.0F, 1.0F);
         GlStateManager.popMatrix();
         if (itemstack.isItemEnchanted()) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.09F, 0.09F, 0.09F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            GL11.glDisable(2896);
            GlStateManager.depthMask(false);
            Minecraft.getMinecraft().renderEngine.bindTexture(this.texEnch1);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            float f = AnimationTimer.tick / 4.0F;
            float f1 = MathHelper.cos(f * 0.02F) / 2.0F;
            float f2 = f * 0.01F;
            GlStateManager.translate(f1, f2, 0.0F);
            GlStateManager.matrixMode(5888);
            GlStateManager.enableBlend();
            Vec3d colorx = miningTool.getColor();
            GlStateManager.color((float)colorx.x, (float)colorx.y, (float)colorx.z, 1.0F);
            GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
            miningTool.getModel().render(null, anim1, 1.0F, miningTool.asEnergyItem() == null ? 2.0F : 1.0F, 0.0F, 0.0F, 1.0F);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            GlStateManager.matrixMode(5888);
            GlStateManager.disableBlend();
            GlStateManager.depthMask(true);
            GL11.glEnable(2896);
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
            GlStateManager.popMatrix();
         }

         GlStateManager.popMatrix();
      }
   }

   public static void renderSides(
      double x, double y, double z, float[] color, float[] prevColor, float size, float height, float partialTicks, float alphaa, byte modifier
   ) {
      renderSidesScaled(x, y, z, color, prevColor, size, size, height, partialTicks, alphaa, modifier);
   }

   public static void renderCap(
      double x, double y, double z, float[] color, boolean up, float sizeX, float sizeZ, float height, float partialTicks, float alphaa, byte modifier
   ) {
      AbstractMobModel.light(240, false);
      RenderHelper.disableStandardItemLighting();
      GlStateManager.shadeModel(7425);
      GlStateManager.enableBlend();
      GlStateManager.disableAlpha();
      GlStateManager.depthMask(false);
      GlStateManager.pushMatrix();
      GlStateManager.translate((float)x, (float)y, (float)z);
      if (modifier != 0 && modifier != 4) {
         float oneY;
         float frame;
         if (modifier == 1) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(beaker_fluid_unstable_top);
            frame = AnimationTimer.tick / 4 % 32;
            oneY = 0.03125F;
         } else {
            Minecraft.getMinecraft().getTextureManager().bindTexture(beaker_fluid_etheric_top);
            frame = AnimationTimer.tick / 8 % 32;
            oneY = 0.03125F;
         }

         if (up) {
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            bufferbuilder.pos(-sizeX, height, -sizeZ)
               .tex(0.0, oneY + frame * oneY)
               .color(color[0], color[1], color[2], alphaa)
               .endVertex();
            bufferbuilder.pos(-sizeX, height, sizeZ)
               .tex(0.0, 0.0F + frame * oneY)
               .color(color[0], color[1], color[2], alphaa)
               .endVertex();
            bufferbuilder.pos(sizeX, height, sizeZ)
               .tex(1.0, 0.0F + frame * oneY)
               .color(color[0], color[1], color[2], alphaa)
               .endVertex();
            bufferbuilder.pos(sizeX, height, -sizeZ)
               .tex(1.0, oneY + frame * oneY)
               .color(color[0], color[1], color[2], alphaa)
               .endVertex();
            tessellator.draw();
         } else {
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            bufferbuilder.pos(sizeX, 0.0, -sizeZ)
               .tex(0.0, oneY + frame * oneY)
               .color(color[0], color[1], color[2], alphaa)
               .endVertex();
            bufferbuilder.pos(sizeX, 0.0, sizeZ)
               .tex(0.0, 0.0F + frame * oneY)
               .color(color[0], color[1], color[2], alphaa)
               .endVertex();
            bufferbuilder.pos(-sizeX, 0.0, sizeZ)
               .tex(1.0, 0.0F + frame * oneY)
               .color(color[0], color[1], color[2], alphaa)
               .endVertex();
            bufferbuilder.pos(-sizeX, 0.0, -sizeZ)
               .tex(1.0, oneY + frame * oneY)
               .color(color[0], color[1], color[2], alphaa)
               .endVertex();
            tessellator.draw();
         }
      } else {
         GlStateManager.disableTexture2D();
         if (up) {
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
            bufferbuilder.pos(-sizeX, height, -sizeZ).color(color[0], color[1], color[2], alphaa).endVertex();
            bufferbuilder.pos(-sizeX, height, sizeZ).color(color[0], color[1], color[2], alphaa).endVertex();
            bufferbuilder.pos(sizeX, height, sizeZ).color(color[0], color[1], color[2], alphaa).endVertex();
            bufferbuilder.pos(sizeX, height, -sizeZ).color(color[0], color[1], color[2], alphaa).endVertex();
            tessellator.draw();
         } else {
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
            bufferbuilder.pos(sizeX, 0.0, -sizeZ).color(color[0], color[1], color[2], alphaa).endVertex();
            bufferbuilder.pos(sizeX, 0.0, sizeZ).color(color[0], color[1], color[2], alphaa).endVertex();
            bufferbuilder.pos(-sizeX, 0.0, sizeZ).color(color[0], color[1], color[2], alphaa).endVertex();
            bufferbuilder.pos(-sizeX, 0.0, -sizeZ).color(color[0], color[1], color[2], alphaa).endVertex();
            tessellator.draw();
         }

         GlStateManager.enableTexture2D();
      }

      AbstractMobModel.returnlight();
      GlStateManager.depthMask(true);
      GlStateManager.disableBlend();
      GlStateManager.shadeModel(7424);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.enableAlpha();
      RenderHelper.enableStandardItemLighting();
      GlStateManager.popMatrix();
   }

   public static void renderCap(
      double x, double y, double z, float[] color, boolean up, float size, float height, float partialTicks, float alphaa, byte modifier
   ) {
      renderCap(x, y, z, color, up, size, size, height, partialTicks, alphaa, modifier);
   }

   public static void renderColoredGlowingCuboid(
      double x,
      double y,
      double z,
      float[] color,
      float[] gradientColor,
      float size,
      float height,
      float glowOffset,
      float partialTicks,
      boolean bottom,
      boolean top,
      byte modifier
   ) {
      renderColoredGlowingCuboid(x, y, z, color, gradientColor, size, size, height, glowOffset, partialTicks, bottom, top, modifier);
   }

   public static void renderColoredGlowingCuboid(
      double x,
      double y,
      double z,
      float[] color,
      float[] gradientColor,
      float sizeX,
      float sizeZ,
      float height,
      float glowOffset,
      float partialTicks,
      boolean bottom,
      boolean top,
      byte modifier
   ) {
      float alphaa1 = 1.0F;
      float alphaa2 = 0.4F;
      if (modifier == 4) {
         GlStateManager.blendFunc(SourceFactor.SRC_COLOR, DestFactor.ONE_MINUS_DST_COLOR);
      } else {
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
      }

      renderSidesScaled(x, y, z, color, gradientColor, sizeX, sizeZ, height, partialTicks, alphaa1, modifier);
      if (top) {
         renderCap(x, y, z, color, true, sizeX, sizeZ, height, partialTicks, alphaa1, modifier);
      }

      if (bottom) {
         renderCap(x, y, z, gradientColor, false, sizeX, sizeZ, height, partialTicks, alphaa1, modifier);
      }

      y -= glowOffset;
      sizeX += glowOffset;
      sizeZ += glowOffset;
      height += glowOffset * 2.0F;
      if (modifier == 4) {
         GlStateManager.blendFunc(SourceFactor.DST_COLOR, DestFactor.SRC_COLOR);
         renderSidesScaled(x, y, z, color, color, sizeX, sizeZ, height, partialTicks, alphaa2, modifier);
         if (top) {
            renderCap(x, y, z, color, true, sizeX, sizeZ, height, partialTicks, alphaa2, modifier);
         }

         if (bottom) {
            renderCap(x, y, z, color, false, sizeX, sizeZ, height, partialTicks, alphaa2, modifier);
         }
      } else {
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         renderSidesScaled(x, y, z, color, gradientColor, sizeX, sizeZ, height, partialTicks, alphaa2, modifier);
         if (top) {
            renderCap(x, y, z, color, true, sizeX, sizeZ, height, partialTicks, alphaa2, modifier);
         }

         if (bottom) {
            renderCap(x, y, z, gradientColor, false, sizeX, sizeZ, height, partialTicks, alphaa2, modifier);
         }
      }
   }

   public static void renderColoredCuboid(
      double x,
      double y,
      double z,
      float[] color,
      float[] gradientColor,
      float sizeX,
      float sizeZ,
      float height,
      float glowOffset,
      float partialTicks,
      boolean bottom,
      boolean top,
      byte modifier
   ) {
      float alphaa1 = 1.0F;
      float alphaa2 = 0.4F;
      GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      renderSidesScaled(x, y, z, color, gradientColor, sizeX, sizeZ, height, partialTicks, alphaa1, modifier);
      if (top) {
         renderCap(x, y, z, color, true, sizeX, sizeZ, height, partialTicks, alphaa1, modifier);
      }

      if (bottom) {
         renderCap(x, y, z, gradientColor, false, sizeX, sizeZ, height, partialTicks, alphaa1, modifier);
      }

      y -= glowOffset;
      sizeX += glowOffset;
      sizeZ += glowOffset;
      height += glowOffset * 2.0F;
      renderSidesScaled(x, y, z, color, gradientColor, sizeX, sizeZ, height, partialTicks, alphaa2, modifier);
      if (top) {
         renderCap(x, y, z, color, true, sizeX, sizeZ, height, partialTicks, alphaa2, modifier);
      }

      if (bottom) {
         renderCap(x, y, z, gradientColor, false, sizeX, sizeZ, height, partialTicks, alphaa2, modifier);
      }
   }

   public static void renderSidesScaled(
      double x, double y, double z, float[] color, float[] prevColor, float sizeX, float sizeZ, float height, float partialTicks, float alphaa, byte modifier
   ) {
      AbstractMobModel.light(240, false);
      RenderHelper.disableStandardItemLighting();
      GlStateManager.shadeModel(7425);
      GlStateManager.enableBlend();
      GlStateManager.disableAlpha();
      GlStateManager.depthMask(false);
      GlStateManager.pushMatrix();
      GlStateManager.translate((float)x, (float)y, (float)z);
      GlStateManager.scale(1.0F, 1.0F, sizeZ / sizeX);
      if (modifier != 0 && modifier != 4) {
         float texX;
         float texY;
         float oneX;
         if (modifier == 1) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(beaker_fluid_unstable);
            texX = AnimationTimer.tick / 250.0F;
            texY = (float)y * 3.0F;
            oneX = 0.5F;
         } else {
            Minecraft.getMinecraft().getTextureManager().bindTexture(beaker_fluid_etheric);
            texX = 0.0F;
            texY = (float)y * (modifier == 2 ? 60.0F : 67.5F) + AnimationTimer.tick / 250.0F;
            oneX = 1.0F;
         }

         for (int i = 0; i < 4; i++) {
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            bufferbuilder.pos(sizeX, 0.0, sizeX)
               .tex(oneX + texX, 0.25F + texY)
               .color(prevColor[0], prevColor[1], prevColor[2], alphaa)
               .endVertex();
            bufferbuilder.pos(sizeX, height, sizeX)
               .tex(oneX + texX, 0.0F + texY)
               .color(color[0], color[1], color[2], alphaa)
               .endVertex();
            bufferbuilder.pos(-sizeX, height, sizeX)
               .tex(0.0F + texX, 0.0F + texY)
               .color(color[0], color[1], color[2], alphaa)
               .endVertex();
            bufferbuilder.pos(-sizeX, 0.0, sizeX)
               .tex(0.0F + texX, 0.25F + texY)
               .color(prevColor[0], prevColor[1], prevColor[2], alphaa)
               .endVertex();
            tessellator.draw();
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
         }
      } else {
         GlStateManager.disableTexture2D();

         for (int i = 0; i < 4; i++) {
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
            bufferbuilder.pos(sizeX, 0.0, sizeX).color(prevColor[0], prevColor[1], prevColor[2], alphaa).endVertex();
            bufferbuilder.pos(sizeX, height, sizeX).color(color[0], color[1], color[2], alphaa).endVertex();
            bufferbuilder.pos(-sizeX, height, sizeX).color(color[0], color[1], color[2], alphaa).endVertex();
            bufferbuilder.pos(-sizeX, 0.0, sizeX).color(prevColor[0], prevColor[1], prevColor[2], alphaa).endVertex();
            tessellator.draw();
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
         }

         GlStateManager.enableTexture2D();
      }

      AbstractMobModel.returnlight();
      GlStateManager.depthMask(true);
      GlStateManager.disableBlend();
      GlStateManager.shadeModel(7424);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.enableAlpha();
      RenderHelper.enableStandardItemLighting();
      GlStateManager.popMatrix();
   }

   public void renderEffect(ItemStack itemStack, float[] color, TransformType transform, boolean dark) {
      float alpha = 0.3F;
      int decimalcolor = dark
         ? ColorConverters.RGBAtoDecimal(1.0F, 1.0F, 1.0F, alpha)
         : ColorConverters.RGBAtoDecimal(color[0] * alpha, color[1] * alpha, color[2] * alpha, 1.0F);
      IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(itemStack, null, null);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.enableRescaleNormal();
      GlStateManager.alphaFunc(516, 0.1F);
      GlStateManager.enableBlend();
      GlStateManager.pushMatrix();
      model = ForgeHooksClient.handleCameraTransforms(model, transform, false);
      if (!itemStack.isEmpty()) {
         GlStateManager.pushMatrix();
         GlStateManager.translate(-0.5F, -0.5F, -0.5F);
         GlStateManager.depthMask(false);
         GlStateManager.depthFunc(514);
         GlStateManager.disableLighting();
         if (dark) {
            GlStateManager.blendFunc(SourceFactor.ONE_MINUS_SRC_COLOR, DestFactor.ONE);
            GlStateManager.glBlendEquation(32779);
         } else {
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
         }

         Minecraft.getMinecraft().getTextureManager().bindTexture(RES_ITEM_GLINT);
         GlStateManager.matrixMode(5890);
         GlStateManager.pushMatrix();
         GlStateManager.scale(8.0F, 8.0F, 8.0F);
         float f = (float)(Minecraft.getSystemTime() % 21000L) / 3000.0F / 26.0F;
         GlStateManager.translate(f, 0.0F, 0.0F);
         GlStateManager.rotate(-50.0F, 0.0F, 0.0F, 1.0F);
         this.renderModel(model, decimalcolor, itemStack);
         GlStateManager.popMatrix();
         GlStateManager.matrixMode(5888);
         GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         GlStateManager.enableLighting();
         GlStateManager.depthFunc(515);
         GlStateManager.depthMask(true);
         Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
         GlStateManager.glBlendEquation(32774);
         GlStateManager.popMatrix();
      }

      GlStateManager.cullFace(CullFace.BACK);
      GlStateManager.popMatrix();
      GlStateManager.disableRescaleNormal();
      GlStateManager.disableBlend();
   }

   public void renderModel(IBakedModel model, int color, ItemStack stack) {
      RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      bufferbuilder.begin(7, DefaultVertexFormats.ITEM);

      for (EnumFacing enumfacing : EnumFacing.values()) {
         renderItem.renderQuads(bufferbuilder, model.getQuads((IBlockState)null, enumfacing, 0L), color, stack);
      }

      renderItem.renderQuads(bufferbuilder, model.getQuads((IBlockState)null, (EnumFacing)null, 0L), color, stack);
      tessellator.draw();
   }

   public static void drawTextured3dModalRect(
      float x, float y, float z, int textureX, int textureY, int ontexWidth, int ontexHeight, float rectWidth, float rectHeight
   ) {
      float f = 0.0078125F;
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
      bufferbuilder.pos(x + 0.0F, y + rectHeight, z).tex((textureX + 0) * f, (textureY + ontexHeight) * f).endVertex();
      bufferbuilder.pos(x + rectWidth, y + rectHeight, z).tex((textureX + ontexWidth) * f, (textureY + ontexHeight) * f).endVertex();
      bufferbuilder.pos(x + rectWidth, y + 0.0F, z).tex((textureX + ontexWidth) * f, (textureY + 0) * f).endVertex();
      bufferbuilder.pos(x + 0.0F, y + 0.0F, z).tex((textureX + 0) * f, (textureY + 0) * f).endVertex();
      tessellator.draw();
   }
}
