package com.vivern.arpg.renders.mobrender;

import com.vivern.arpg.elements.models.CinderCrawlerModel;
import com.vivern.arpg.elements.models.SmokeDemonModel;
import com.vivern.arpg.elements.models.SpineHeadModel;
import com.vivern.arpg.elements.models.SpineMinionModel;
import com.vivern.arpg.elements.models.SpineSegmentModel;
import com.vivern.arpg.elements.models.SummonedSnowmanModel;
import com.vivern.arpg.mobs.AbstractMob;
import com.vivern.arpg.mobs.BossWinterFury;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class InitMobRenders {
   public static List<AbstractMob.RenderAbstractMobEntry> torender = new ArrayList<>();
   public static SmokeDemonModel model1 = new SmokeDemonModel();
   public static ResourceLocation mobTexture1 = new ResourceLocation("arpg:textures/smoke_demon_model_tex.png");
   public static SummonedSnowmanModel model2 = new SummonedSnowmanModel();
   public static ResourceLocation mobTexture2 = new ResourceLocation("arpg:textures/summoned_snowman_model_tex.png");
   public static SpineHeadModel model3 = new SpineHeadModel();
   public static ResourceLocation mobTexture3 = new ResourceLocation("arpg:textures/spine_head_model_tex.png");
   public static SpineSegmentModel model4 = new SpineSegmentModel();
   public static ResourceLocation mobTexture4 = new ResourceLocation("arpg:textures/spine_segment_model_tex.png");
   public static SpineMinionModel model5 = new SpineMinionModel();
   public static ResourceLocation mobTexture5 = new ResourceLocation("arpg:textures/spine_minion_model_tex.png");
   public static CinderCrawlerModel model6 = new CinderCrawlerModel();
   public static ResourceLocation mobTexture6 = new ResourceLocation("arpg:textures/cinder_crawler_model_tex.png");

   public static void init() {
      RenderingRegistry.registerEntityRenderingHandler(BossWinterFury.class, new RenderWinterFury.RenderWinterFuryFactory());

      for (AbstractMob.RenderAbstractMobEntry entry : torender) {
         if (entry.rocket) {
            RenderingRegistry.registerEntityRenderingHandler(
               entry.mobClass,
               RenderRocketMob.getFACTORY(entry.model, entry.mobTexture, entry.shadowSize, entry.verticalRocket, entry)
                  .setLayerHeldItem(entry.layerHeldItem)
                  .setlayerArmor(entry.layerArmor)
            );
         } else if (entry.getMultitexture) {
            RenderingRegistry.registerEntityRenderingHandler(
               entry.mobClass,
               RenderMobMultitexture.getFACTORY(entry.model, entry.shadowSize).setLayerHeldItem(entry.layerHeldItem).setlayerArmor(entry.layerArmor)
            );
         } else {
            RenderingRegistry.registerEntityRenderingHandler(
               entry.mobClass,
               RenderUniversal.getFACTORY(entry.model, entry.mobTexture, entry.shadowSize, entry.useIRender, entry)
                  .setLayerHeldItem(entry.layerHeldItem)
                  .setlayerArmor(entry.layerArmor)
                  .setLayerRandomItem(entry.layerrandItem)
            );
         }
      }
   }
}
