package com.vivern.arpg.renders;

import com.vivern.arpg.elements.IWeapon;
import com.vivern.arpg.events.Debugger;
import com.vivern.arpg.hooks.ARPGHooks;
import com.vivern.arpg.main.AnimationTimer;
import com.vivern.arpg.main.Coins;
import com.vivern.arpg.main.ColorConverters;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.ItemsElements;
import com.vivern.arpg.main.Mana;
import com.vivern.arpg.main.NBTHelper;
import com.vivern.arpg.main.ShardType;
import com.vivern.arpg.main.Shards;
import com.vivern.arpg.tileentity.TileSpellForge;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ManaBar {
   private Minecraft mc = Minecraft.getMinecraft();
   private static final ManaBar INSTANCE = new ManaBar();
   private static final ResourceLocation BarsEmpty = new ResourceLocation("arpg:textures/gui_bars_empty.png");
   private static final ResourceLocation BarsFilled = new ResourceLocation("arpg:textures/gui_bars_filled.png");
   private static final ResourceLocation BarsManaVision = new ResourceLocation("arpg:textures/gui_bars_mana_vision.png");
   private static final ResourceLocation MagicPowerIcon = new ResourceLocation("arpg:textures/magic_icon.png");
   private static final ResourceLocation ManaRegenIcon = new ResourceLocation("arpg:textures/manaregen_icon.png");
   private static final ResourceLocation ManaMaxIcon = new ResourceLocation("arpg:textures/manamax_icon.png");
   private static final ResourceLocation ArmorIcon = new ResourceLocation("arpg:textures/armor_icon.png");
   private static final ResourceLocation HealthIcon = new ResourceLocation("arpg:textures/health_icon.png");
   private static final ResourceLocation LeadershipIcon = new ResourceLocation("arpg:textures/leadership_icon.png");
   private static final ResourceLocation FishingBig = new ResourceLocation("arpg:textures/fishing_round.png");
   private static final ResourceLocation FishingSmall = new ResourceLocation("arpg:textures/fishing_round2.png");
   private static final ResourceLocation TextureVessel = new ResourceLocation("arpg:textures/energy_vessel.png");
   private static final ResourceLocation FlyingIcon = new ResourceLocation("arpg:textures/flying_icon.png");
   private static final ResourceLocation CoinsIcon = new ResourceLocation("arpg:textures/coin_icon.png");
   private static final ResourceLocation HotbarSlot = new ResourceLocation("arpg:textures/hotbar_slot.png");
   private static final ResourceLocation EnergyBars = new ResourceLocation("arpg:textures/energy_bars.png");
   private static final ResourceLocation EnergyRunes = new ResourceLocation("arpg:textures/gui_main_runes.png");
   private static final ResourceLocation Forging = new ResourceLocation("arpg:textures/gui_forging.png");
   public static int summonedCount = 0;
   public static List<ResourceLocation> summonedlist = new ArrayList<>();
   public static int dungeonLadderAnim = 0;
   public static int dungeonLadderAnimMax = 60;
   public static int energy_bars_time = 0;
   public static boolean energy_bars_enable = true;
   public static int leadershipUsed = 0;
   public static TileSpellForge spellForgeDisplayed;
   public static int timeSpellForge;
   public static int shownSpellForge;
   public static float[] savedMagicVision = new float[15];
   public static int amountMagicVision = 0;
   public static boolean canupdateMagicVision = false;

   private ManaBar() {
   }

   public static ManaBar getInstance() {
      return INSTANCE;
   }

   public static void displaySpellForge(TileSpellForge spellForge) {
      spellForgeDisplayed = spellForge;
      timeSpellForge = AnimationTimer.normaltick + 300;
   }

   public void renderPlayerManaBar() {
      if (this.mc.getRenderViewEntity() instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)this.mc.getRenderViewEntity();
         float mana = Mana.getMana(player);
         ScaledResolution resolution = new ScaledResolution(this.mc);
         int i = resolution.getScaledWidth();
         int f = resolution.getScaledHeight();
         GlStateManager.pushMatrix();
         GlStateManager.disableRescaleNormal();
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.disableLighting();
         if (!this.mc.gameSettings.showDebugInfo) {
            if (energy_bars_enable) {
               energy_bars_time++;
            } else if (energy_bars_time > 0) {
               energy_bars_time--;
            }

            int fps = Minecraft.getMinecraft().gameSettings.limitFramerate;
            if (energy_bars_time > 7 * fps) {
               energy_bars_time = fps;
               energy_bars_enable = false;
            }

            float shown = GetMOP.getfromto((float)energy_bars_time, 0.0F, (float)(fps / 2));
            float shown2 = GetMOP.getfromto((float)energy_bars_time, (float)(fps / 4), fps / 1.5F);
            int screenOffset = 5;
            this.mc.getTextureManager().bindTexture(BarsEmpty);
            drawCustomSizedTexturedRect(i - 10 - screenOffset, f - 224 - screenOffset, 64, 256, 54, 32, 10, 224);
            drawCustomSizedTexturedRect(i - 46 - screenOffset, f - (int)(64.0F * shown) - screenOffset, 64, 256, 18, 192, 36, (int)(64.0F * shown));
            this.mc.getTextureManager().bindTexture(BarsFilled);
            int progr = (int)(224.0F * mana / Math.max(Mana.getManaMax(player), 1.0F));
            drawCustomSizedTexturedRect(i - 10 - screenOffset, f - progr - screenOffset, 64, 256, 54, 256 - progr, 10, progr);

            for (int vess = 1; vess <= 3; vess++) {
               float energy = Shards.getEnergyInVessel(this.mc.player, vess);
               if (energy > 0.0F) {
                  ShardType shard = Shards.getShardTypeInVessel(this.mc.player, vess);
                  float maxenergy = 100.0F;
                  int id = shard.id - 1;
                  int u = id % 3 * 12;
                  int v = id / 3 * 64;
                  int progrEner = (int)((61.0F * energy / Math.max(maxenergy, 1.0F) + 1.0F) * shown2);
                  drawCustomSizedTexturedRect(
                     i - 58 - screenOffset + vess * 12, f - progrEner - screenOffset, 64, 256, 18 + u, v + 64 - progrEner, 10, progrEner
                  );
               }
            }

            if (AnimationTimer.normaltick < timeSpellForge) {
               if (shownSpellForge < 15) {
                  shownSpellForge++;
               }

               if (spellForgeDisplayed != null && spellForgeDisplayed.currentForging != null) {
                  float open = shownSpellForge / 15.0F;
                  int Yadd = (int)(-34.0F + 34.0F * open);
                  this.mc.getTextureManager().bindTexture(Forging);
                  float progress = MathHelper.clamp(
                     spellForgeDisplayed.currentForging.hammerCompleteness / spellForgeDisplayed.currentForging.hitsNeed, 0.0F, 1.0F
                  );
                  drawCustomSizedTexturedRect(i / 2 - 128, 10 + Yadd, 256, 256, 0, 0, 256, 24);
                  drawCustomSizedTexturedRect(i / 2 - 117, 15 + Yadd, 256, 256, 11, 24, (int)(234.0F * progress), 10);
                  if (spellForgeDisplayed.currentForging.canAcceptSpells) {
                     float progress2 = spellForgeDisplayed.currentForging.fullComplete
                        ? 1.0F
                        : MathHelper.clamp(1.0F - spellForgeDisplayed.currentForging.ticks / 160.0F, 0.0F, 1.0F);
                     drawCustomSizedTexturedRect(
                        i / 2 - 104, 30 + Yadd, 256, 256, 24, spellForgeDisplayed.currentForging.fullComplete ? 36 : 34, (int)(208.0F * progress2), 2
                     );
                     drawCustomSizedTexturedRect(i / 2 - 148, 10 + Yadd, 256, 256, spellForgeDisplayed.currentForging.fullComplete ? 40 : 20, 40, 20, 20);
                  } else if (progress < 1.0F) {
                     drawCustomSizedTexturedRect(i / 2 - 148, 10 + Yadd, 256, 256, 0, 40, 20, 20);
                  }
               }
            } else if (shownSpellForge > 0) {
               shownSpellForge--;
            }
         }

         if (this.mc.currentScreen != null && this.mc.currentScreen instanceof InventoryEffectRenderer) {
            GlStateManager.disableDepth();
            int xOffsetIcon = 25;
            int xOffsetText = 40;
            this.mc.getTextureManager().bindTexture(MagicPowerIcon);
            drawCustomSizedTexturedRect(xOffsetIcon, f - 40, 16, 16, 0.0);
            this.mc.fontRenderer.drawStringWithShadow(asString(Mana.getMagicPowerMax(player)), xOffsetText, f - 35, 16777215);
            this.mc.getTextureManager().bindTexture(ManaRegenIcon);
            drawCustomSizedTexturedRect(xOffsetIcon, f - 55, 16, 16, 0.0);
            this.mc.fontRenderer.drawStringWithShadow(asString(Mana.getManaSpeedMax(player)), xOffsetText, f - 50, 16777215);
            this.mc.getTextureManager().bindTexture(ManaMaxIcon);
            drawCustomSizedTexturedRect(xOffsetIcon, f - 70, 16, 16, 0.0);
            String manf = mana + "/";
            this.mc.fontRenderer.drawStringWithShadow(manf.substring(0, 4) + "/" + asString(Mana.getManaMax(player)), xOffsetText, f - 65, 16777215);
            this.mc.getTextureManager().bindTexture(ArmorIcon);
            drawCustomSizedTexturedRect(xOffsetIcon, f - 85, 16, 16, 0.0);
            this.mc.fontRenderer.drawStringWithShadow(asString((float)Mana.getArmorProtection(player)), xOffsetText, f - 80, 16777215);
            this.mc.getTextureManager().bindTexture(HealthIcon);
            drawCustomSizedTexturedRect(xOffsetIcon, f - 100, 16, 16, 0.0);
            this.mc
               .fontRenderer
               .drawStringWithShadow(
                  asString(player.getHealth()) + "/" + asString((float)player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getAttributeValue()),
                  xOffsetText,
                  f - 95,
                  16777215
               );
            this.mc.getTextureManager().bindTexture(CoinsIcon);
            drawCustomSizedTexturedRect(xOffsetIcon, f - 115, 16, 16, 0.0);
            this.mc.fontRenderer.drawStringWithShadow(Coins.getCoins(player) + "", xOffsetText, f - 110, 16777215);
            GlStateManager.enableDepth();
         }

         KillScore.renderAll(this.mc.fontRenderer, i, f);
         InvasionInfo.render(this.mc.fontRenderer, player, i, f);
         if (ARPGHooks.moveSlot != player.inventory.currentItem) {
            this.mc.getTextureManager().bindTexture(HotbarSlot);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            drawCustomSizedTexturedRect(i / 2 - 91 + ARPGHooks.moveSlot * 20, f - 22, 22, 22, 0.0);
         }

         GlStateManager.enableRescaleNormal();
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
         RenderHelper.enableGUIStandardItemLighting();

         for (int l = 0; l < 9; l++) {
            ItemStack stw = (ItemStack)player.inventory.mainInventory.get(l);
            Item itw = stw.getItem();
            if (itw instanceof IWeapon) {
               int i1 = i / 2 - 90 + l * 20 + 2;
               int j1 = f - 16 - 3;
               this.renderItemOverlayIntoGUI((IWeapon)itw, stw, i1, j1);
            }
         }

         ItemStack stackoffhand = player.getHeldItemOffhand();
         if (!stackoffhand.isEmpty()) {
            Item itw = stackoffhand.getItem();
            if (itw instanceof IWeapon) {
               int l1 = f - 16 - 3;
               if (player.getPrimaryHand() == EnumHandSide.RIGHT) {
                  this.renderItemOverlayIntoGUI((IWeapon)itw, stackoffhand, i / 2 - 91 - 26, l1);
               } else {
                  this.renderItemOverlayIntoGUI((IWeapon)itw, stackoffhand, i / 2 + 91 + 10, l1);
               }
            }
         }

         RenderHelper.disableStandardItemLighting();
         GlStateManager.disableRescaleNormal();
         GlStateManager.disableBlend();
         if (dungeonLadderAnim > 0) {
            dungeonLadderAnim--;
            this.renderDarknessScreen();
         }

         if (player.ticksExisted % 8 == 0) {
            if (canupdateMagicVision) {
               updateElementsVision(player);
            }
         } else {
            canupdateMagicVision = true;
         }

         this.renderElementsVision(i, f, player);
         GlStateManager.popMatrix();
      }
   }

   public static void updateElementsVision(EntityPlayer player) {
      RayTraceResult result = Minecraft.getMinecraft().objectMouseOver;
      if (result != null && result.getBlockPos() != null) {
         TileEntity tileEntity = player.world.getTileEntity(result.getBlockPos());
         if (tileEntity != null && tileEntity instanceof IMagicVision) {
            amountMagicVision = 0;
            IMagicVision iMagicVision = (IMagicVision)tileEntity;

            for (int s = 1; s <= 12; s++) {
               ShardType shardType = ShardType.byId(s);
               float energy = iMagicVision.getElementEnergy(shardType);
               savedMagicVision[s] = energy;
               if (energy > 0.0F) {
                  amountMagicVision++;
               }
            }

            float mana = iMagicVision.getMana();
            float manaMax = iMagicVision.getManaStorageSize(player.world, result.getBlockPos());
            savedMagicVision[0] = mana;
            savedMagicVision[13] = manaMax;
            if (mana > 0.0F) {
               amountMagicVision++;
            }

            return;
         }
      }

      amountMagicVision = 0;
      canupdateMagicVision = false;
   }

   public void renderElementsVision(int i, int f, EntityPlayer player) {
      if (amountMagicVision > 0) {
         int oneLength = 12;
         int space = 10;
         int fullLength = oneLength * amountMagicVision + space * (amountMagicVision - 1);
         int halfLength = fullLength / 2;
         int currentTranslation = -halfLength;
         int ypos = -108;
         int ypos2 = -100;
         boolean renderManaDown = false;

         for (int s = 1; s < 13; s++) {
            float amount = savedMagicVision[s];
            if (amount > 0.0F) {
               renderManaDown = true;
               this.mc.getTextureManager().bindTexture(EnergyRunes);
               drawCustomSizedTexturedRect(i / 2 + currentTranslation, f + ypos, 12, 156, 0, 12 * s, 12, 12);
               this.mc.fontRenderer.drawStringWithShadow(asString(amount), i / 2 + currentTranslation + 8, f + ypos2, 16777215);
               currentTranslation += oneLength + space;
            }
         }

         float mana = savedMagicVision[0];
         if (mana != 0.0F) {
            int down = renderManaDown ? 18 : 0;
            float manaMax = savedMagicVision[13];
            float manaProgress = mana / manaMax;
            this.mc.getTextureManager().bindTexture(BarsManaVision);
            drawCustomSizedTexturedRect(i / 2 - 32, f + ypos + down, 64, 64, 0, 10, 64, 10);
            drawCustomSizedTexturedRect(i / 2 - 32, f + ypos + down, 64, 64, 0, 0, Math.round(64.0F * manaProgress), 10);
            String manastring = asString(mana) + "/" + asString(manaMax);
            this.mc.fontRenderer.drawStringWithShadow(manastring, i / 2 - this.mc.fontRenderer.getStringWidth(manastring) / 2, f + ypos + down + 8, 12378879);
         }
      }
   }

   public static void renderElementsVision(int x, int y, ItemsElements.ElementsPack elements) {
      int fullCount = 0;

      for (int s = 0; s < 12; s++) {
         if (elements.elementsAmount[s] > 0.0F) {
            fullCount++;
         }
      }

      int oneLength = 12;
      int space = 10;
      int fullLength = oneLength * fullCount + space * (fullCount - 1);
      int halfLength = fullLength / 2;
      int currentTranslation = -halfLength;
      int ypos = -108;
      int ypos2 = -100;

      for (int sx = 0; sx < 12; sx++) {
         float amount = elements.elementsAmount[sx];
         if (amount > 0.0F) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(EnergyRunes);
            drawCustomSizedTexturedRect(x + currentTranslation, y + ypos, 12, 156, 0, 12 * (sx + 1), 12, 12);
            Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(asString(amount), x + currentTranslation + 8, y + ypos2, 16777215);
            Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("purity: " + asString(elements.purity), x - halfLength, y + ypos2 + 14, 16777215);
            currentTranslation += oneLength + space;
         }
      }
   }

   public static String asString(float value) {
      int valInt = (int)value;
      if (valInt != value) {
         String s = "" + value;
         int inxOf = s.lastIndexOf(46);
         return s.substring(0, inxOf + 2);
      } else {
         return "" + valInt;
      }
   }

   public static void drawCustomSizedTexturedRect(double x, double y, int width, int height, double progr) {
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
      bufferbuilder.pos(x, y + height, 0.0).tex(0.0, 1.0).endVertex();
      bufferbuilder.pos(x + width, y + height, 0.0).tex(1.0, 1.0).endVertex();
      bufferbuilder.pos(x + width, y + height * progr, 0.0).tex(1.0, progr).endVertex();
      bufferbuilder.pos(x, y + height * progr, 0.0).tex(0.0, progr).endVertex();
      tessellator.draw();
   }

   public static void drawCustomSizedTexturedRect(double x, double y, int allTextureSizeX, int allTextureSizeY, int texu, int texv, int width, int height) {
      drawCustomSizedTexturedRect(x, y, 0.0, allTextureSizeX, allTextureSizeY, texu, texv, width, height);
   }

   public static void drawCustomSizedTexturedRect(
      double x, double y, double z, int allTextureSizeX, int allTextureSizeY, int texu, int texv, int width, int height
   ) {
      float onePixelX = 1.0F / allTextureSizeX;
      float onePixelY = 1.0F / allTextureSizeY;
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
      bufferbuilder.pos(x, y + height, 0.0).tex(texu * onePixelX, (texv + height) * onePixelY).endVertex();
      bufferbuilder.pos(x + width, y + height, 0.0).tex((texu + width) * onePixelX, (texv + height) * onePixelY).endVertex();
      bufferbuilder.pos(x + width, y, 0.0).tex((texu + width) * onePixelX, texv * onePixelY).endVertex();
      bufferbuilder.pos(x, y, 0.0).tex(texu * onePixelX, texv * onePixelY).endVertex();
      tessellator.draw();
   }

   public static void drawCustomSizedTexturedRectAntiProgr(double x, double y, int width, int height, double progr, float framecount, int animSpeed) {
      int time = AnimationTimer.tick / animSpeed;
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
      bufferbuilder.pos(x, y + height, 0.0).tex(0.0F + time / framecount, progr).endVertex();
      bufferbuilder.pos(x + width, y + height, 0.0).tex(1.0 / framecount + time / framecount, progr).endVertex();
      bufferbuilder.pos(x + width, y + height * progr, 0.0).tex(1.0 / framecount + time / framecount, 1.0).endVertex();
      bufferbuilder.pos(x, y + height * progr, 0.0).tex(0.0F + time / framecount, 1.0).endVertex();
      tessellator.draw();
   }

   public void renderItemOverlayIntoGUI(IWeapon weapon, ItemStack stack, int xPosition, int yPosition) {
      if (!stack.isEmpty()) {
         if (weapon.hasAdditionalDurabilityBar(stack)) {
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            GlStateManager.disableTexture2D();
            GlStateManager.disableAlpha();
            GlStateManager.disableBlend();
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            double health = weapon.getAdditionalDurabilityBar(stack);
            int rgbfordisplay = 7519743;
            int i = Math.round((float)health * 13.0F);
            this.draw(bufferbuilder, xPosition + 2, yPosition + 1, 13, 2, 0, 0, 0, 255);
            this.draw(bufferbuilder, xPosition + 2, yPosition + 1, i, 1, rgbfordisplay >> 16 & 0xFF, rgbfordisplay >> 8 & 0xFF, rgbfordisplay & 0xFF, 255);
            GlStateManager.enableBlend();
            GlStateManager.enableAlpha();
            GlStateManager.enableTexture2D();
            GlStateManager.enableLighting();
            GlStateManager.enableDepth();
         }

         EntityPlayerSP entityplayersp = Minecraft.getMinecraft().player;
         float f3 = (float)NBTHelper.GetNBTint(stack, "reload_time") / weapon.getReloadTime(stack);
         f3 = MathHelper.clamp(f3, 0.0F, 1.0F);
         if (f3 > 0.0F) {
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            GlStateManager.disableTexture2D();
            Tessellator tessellator1 = Tessellator.getInstance();
            BufferBuilder bufferbuilder1 = tessellator1.getBuffer();
            this.draw(
               bufferbuilder1, xPosition, yPosition + MathHelper.floor(16.0F * (1.0F - f3)), 16, MathHelper.ceil(16.0F * f3), 255, 219, 148, 127
            );
            GlStateManager.enableTexture2D();
            GlStateManager.enableLighting();
            GlStateManager.enableDepth();
         }
      }
   }

   public void draw(BufferBuilder renderer, int x, int y, int width, int height, int red, int green, int blue, int alpha) {
      renderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
      renderer.pos(x + 0, y + 0, 0.0).color(red, green, blue, alpha).endVertex();
      renderer.pos(x + 0, y + height, 0.0).color(red, green, blue, alpha).endVertex();
      renderer.pos(x + width, y + height, 0.0).color(red, green, blue, alpha).endVertex();
      renderer.pos(x + width, y + 0, 0.0).color(red, green, blue, alpha).endVertex();
      Tessellator.getInstance().draw();
   }

   public void renderDarknessScreen() {
      int anim = dungeonLadderAnimMax - dungeonLadderAnim;
      float alpha = GetMOP.getfromto((float)anim, 0.0F, 5.0F) - GetMOP.getfromto((float)anim, 40.0F, 60.0F);
      if (alpha > 0.0F) {
         GlStateManager.disableLighting();
         GlStateManager.disableDepth();
         GlStateManager.disableTexture2D();
         GlStateManager.disableAlpha();
         GlStateManager.enableBlend();
         Tessellator tessellator = Tessellator.getInstance();
         BufferBuilder bufferbuilder = tessellator.getBuffer();
         this.draw(bufferbuilder, -10000, -10000, 20000, 20000, 0, 0, 0, (int)(alpha * 255.0F));
         GlStateManager.disableBlend();
         GlStateManager.enableAlpha();
         GlStateManager.enableTexture2D();
         GlStateManager.enableLighting();
         GlStateManager.enableDepth();
      }
   }

   public void renderExpBar(ScaledResolution scaledRes, int indx) {
      this.mc.getTextureManager().bindTexture(EnergyBars);
      float energy = Shards.getEnergyInVessel(this.mc.player, indx);
      ShardType shard = Shards.getShardTypeInVessel(this.mc.player, indx);
      if (energy > 0.0F) {
         float energyMaximum = 100.0F;
         int j = 182;
         int k = (int)(energy * 183.0F / energyMaximum);
         int l = scaledRes.getScaledHeight() - 32 + 3;
         int x = scaledRes.getScaledWidth() / 2 - 91;
         this.drawTexturedModalRect(x, l, 0, (shard.id - 1) * 10, 182, 5);
         if (k > 0) {
            this.drawTexturedModalRect(x, l, 0, (shard.id - 1) * 10 + 5, k, 5);
         }

         String s = "" + (int)energy;
         int i1 = (scaledRes.getScaledWidth() - this.mc.fontRenderer.getStringWidth(s)) / 2;
         int j1 = scaledRes.getScaledHeight() - 31 - 4;
         this.mc.fontRenderer.drawString(s, i1 + 1, j1, 0);
         this.mc.fontRenderer.drawString(s, i1 - 1, j1, 0);
         this.mc.fontRenderer.drawString(s, i1, j1 + 1, 0);
         this.mc.fontRenderer.drawString(s, i1, j1 - 1, 0);
         this.mc.fontRenderer.drawString(s, i1, j1, ColorConverters.RGBtoDecimal(shard.colorR, shard.colorG, shard.colorB));
      }
   }

   public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height) {
      float f = 0.00390625F;
      float f1 = 0.00390625F;
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
      bufferbuilder.pos(x + 0, y + height, 0.0).tex((textureX + 0) * 0.00390625F, (textureY + height) * 0.00390625F).endVertex();
      bufferbuilder.pos(x + width, y + height, 0.0)
         .tex((textureX + width) * 0.00390625F, (textureY + height) * 0.00390625F)
         .endVertex();
      bufferbuilder.pos(x + width, y + 0, 0.0).tex((textureX + width) * 0.00390625F, (textureY + 0) * 0.00390625F).endVertex();
      bufferbuilder.pos(x + 0, y + 0, 0.0).tex((textureX + 0) * 0.00390625F, (textureY + 0) * 0.00390625F).endVertex();
      tessellator.draw();
   }

   public void renderDebugMatrix(FontRenderer fontRenderer, int screenWidth, int screenHeight) {
      int margin = 20;
      int offsetY = 20;

      for (int xx = 0; xx < 4; xx++) {
         for (int yy = 0; yy < 4; yy++) {
            int ii = yy * 4 + xx;
            fontRenderer.drawStringWithShadow(asString(Debugger.floats[ii + 1]), screenWidth - 100 + xx * margin, offsetY + yy * margin, 11781046);
         }
      }
   }
}
