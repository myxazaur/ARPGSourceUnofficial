package gloomyfolkenvivern.arpghooklib.minecraft;

import java.lang.reflect.Field;
import java.util.Map;
import net.minecraftforge.fml.relauncher.CoreModManager;
import net.minecraftforge.fml.relauncher.FMLRelaunchLog;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

public class HookLibPlugin implements IFMLLoadingPlugin {
   private static boolean obf;
   private static boolean checked;

   public String[] getLibraryRequestClass() {
      return null;
   }

   public String getAccessTransformerClass() {
      return null;
   }

   public String[] getASMTransformerClass() {
      return new String[]{PrimaryClassTransformer.class.getName()};
   }

   public String getModContainerClass() {
      return null;
   }

   public String getSetupClass() {
      return null;
   }

   public void injectData(Map<String, Object> data) {
   }

   public static boolean getObfuscated() {
      if (!checked) {
         try {
            Field deobfField = CoreModManager.class.getDeclaredField("deobfuscatedEnvironment");
            deobfField.setAccessible(true);
            obf = !deobfField.getBoolean(null);
            FMLRelaunchLog.info("[HOOKLIB]  Obfuscated: " + obf, new Object[0]);
         } catch (Exception var1) {
            var1.printStackTrace();
         }

         checked = true;
      }

      return obf;
   }
}
