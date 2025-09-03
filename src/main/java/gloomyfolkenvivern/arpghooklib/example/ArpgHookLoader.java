package gloomyfolkenvivern.arpghooklib.example;

import gloomyfolkenvivern.arpghooklib.minecraft.HookLoader;
import gloomyfolkenvivern.arpghooklib.minecraft.PrimaryClassTransformer;

public class ArpgHookLoader extends HookLoader {
   @Override
   public String[] getASMTransformerClass() {
      return new String[]{PrimaryClassTransformer.class.getName()};
   }

   @Override
   public void registerHooks() {
      registerHookContainer("gloomyfolkenvivern.arpghooklib.example.AnnotationHooks");
   }
}
