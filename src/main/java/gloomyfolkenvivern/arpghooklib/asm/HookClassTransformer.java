package gloomyfolkenvivern.arpghooklib.asm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

public class HookClassTransformer {
   public HookLogger logger = new HookLogger.SystemOutLogger();
   protected HashMap<String, List<AsmHook>> hooksMap = new HashMap<>();
   private HookContainerParser containerParser = new HookContainerParser(this);
   protected ClassMetadataReader classMetadataReader = new ClassMetadataReader();

   public void registerHook(AsmHook hook) {
      if (this.hooksMap.containsKey(hook.getTargetClassName())) {
         this.hooksMap.get(hook.getTargetClassName()).add(hook);
      } else {
         List<AsmHook> list = new ArrayList<>(2);
         list.add(hook);
         this.hooksMap.put(hook.getTargetClassName(), list);
      }
   }

   public void registerHookContainer(String className) {
      this.containerParser.parseHooks(className);
   }

   public void registerHookContainer(byte[] classData) {
      this.containerParser.parseHooks(classData);
   }

   public byte[] transform(String className, byte[] bytecode) {
      List<AsmHook> hooks = this.hooksMap.get(className);
      if (hooks != null) {
         Collections.sort(hooks);
         this.logger.debug("Injecting hooks into class " + className);

         try {
            int majorVersion = (bytecode[6] & 255) << 8 | bytecode[7] & 255;
            boolean java7 = majorVersion > 50;
            ClassReader cr = new ClassReader(bytecode);
            ClassWriter cw = this.createClassWriter(java7 ? 2 : 1);
            HookInjectorClassVisitor hooksWriter = this.createInjectorClassVisitor(cw, hooks);
            cr.accept(hooksWriter, java7 ? 4 : 8);
            bytecode = cw.toByteArray();

            for (AsmHook hook : hooksWriter.injectedHooks) {
               this.logger.debug("Patching method " + hook.getPatchedMethodName());
            }

            hooks.removeAll(hooksWriter.injectedHooks);
         } catch (Exception var11) {
            this.logger.severe("A problem has occurred during transformation of class " + className + ".");
            this.logger.severe("Attached hooks:");

            for (AsmHook hook : hooks) {
               this.logger.severe(hook.toString());
            }

            this.logger.severe("Stack trace:", var11);
         }

         for (AsmHook notInjected : hooks) {
            if (notInjected.isMandatory()) {
               throw new RuntimeException("Can not find target method of mandatory hook " + notInjected);
            }

            this.logger.warning("Can not find target method of hook " + notInjected);
         }
      }

      return bytecode;
   }

   protected HookInjectorClassVisitor createInjectorClassVisitor(ClassWriter cw, List<AsmHook> hooks) {
      return new HookInjectorClassVisitor(this, cw, hooks);
   }

   protected ClassWriter createClassWriter(int flags) {
      return new SafeClassWriter(this.classMetadataReader, flags);
   }
}
