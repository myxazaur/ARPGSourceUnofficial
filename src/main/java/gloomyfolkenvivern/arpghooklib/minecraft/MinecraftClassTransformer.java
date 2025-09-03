package gloomyfolkenvivern.arpghooklib.minecraft;

import gloomyfolkenvivern.arpghooklib.asm.AsmHook;
import gloomyfolkenvivern.arpghooklib.asm.HookClassTransformer;
import gloomyfolkenvivern.arpghooklib.asm.HookInjectorClassVisitor;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassWriter;

public class MinecraftClassTransformer extends HookClassTransformer implements IClassTransformer {
   static MinecraftClassTransformer instance;
   private Map<Integer, String> methodNames;
   private static List<IClassTransformer> postTransformers = new ArrayList<>();

   public MinecraftClassTransformer() {
      instance = this;
      if (HookLibPlugin.getObfuscated()) {
         try {
            long timeStart = System.currentTimeMillis();
            this.methodNames = this.loadMethodNames();
            long time = System.currentTimeMillis() - timeStart;
            this.logger.debug("Methods dictionary loaded in " + time + " ms");
         } catch (IOException var5) {
            this.logger.severe("Can not load obfuscated method names", var5);
         }
      }

      this.classMetadataReader = HookLoader.getDeobfuscationMetadataReader();
      this.hooksMap.putAll(PrimaryClassTransformer.instance.getHooksMap());
      PrimaryClassTransformer.instance.getHooksMap().clear();
      PrimaryClassTransformer.instance.registeredSecondTransformer = true;
   }

   private HashMap<Integer, String> loadMethodNames() throws IOException {
      InputStream resourceStream = this.getClass().getResourceAsStream("/methods.bin");
      if (resourceStream == null) {
         throw new IOException("Methods dictionary not found");
      } else {
         DataInputStream input = new DataInputStream(new BufferedInputStream(resourceStream));
         int numMethods = input.readInt();
         HashMap<Integer, String> map = new HashMap<>(numMethods);

         for (int i = 0; i < numMethods; i++) {
            map.put(input.readInt(), input.readUTF());
         }

         input.close();
         return map;
      }
   }

   public byte[] transform(String oldName, String newName, byte[] bytecode) {
      bytecode = this.transform(newName, bytecode);

      for (int i = 0; i < postTransformers.size(); i++) {
         bytecode = postTransformers.get(i).transform(oldName, newName, bytecode);
      }

      return bytecode;
   }

   @Override
   protected HookInjectorClassVisitor createInjectorClassVisitor(ClassWriter cw, List<AsmHook> hooks) {
      return new HookInjectorClassVisitor(this, cw, hooks) {
         @Override
         protected boolean isTargetMethod(AsmHook hook, String name, String desc) {
            if (HookLibPlugin.getObfuscated()) {
               String mcpName = MinecraftClassTransformer.this.methodNames.get(MinecraftClassTransformer.getMethodId(name));
               if (mcpName != null && super.isTargetMethod(hook, mcpName, desc)) {
                  return true;
               }
            }

            return super.isTargetMethod(hook, name, desc);
         }
      };
   }

   public Map<Integer, String> getMethodNames() {
      return this.methodNames;
   }

   public static int getMethodId(String srgName) {
      if (srgName.startsWith("func_")) {
         int first = srgName.indexOf(95);
         int second = srgName.indexOf(95, first + 1);
         return Integer.valueOf(srgName.substring(first + 1, second));
      } else {
         return -1;
      }
   }

   public static void registerPostTransformer(IClassTransformer transformer) {
      postTransformers.add(transformer);
   }
}
