package gloomyfolkenvivern.arpghooklib.asm;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

public class HookContainerParser {
   private HookClassTransformer transformer;
   private String currentClassName;
   private String currentMethodName;
   private String currentMethodDesc;
   private boolean currentMethodPublicStatic;
   private HashMap<String, Object> annotationValues;
   private HashMap<Integer, Integer> parameterAnnotations = new HashMap<>();
   private boolean inHookAnnotation;
   private static final String HOOK_DESC = Type.getDescriptor(Hook.class);
   private static final String LOCAL_DESC = Type.getDescriptor(Hook.LocalVariable.class);
   private static final String RETURN_DESC = Type.getDescriptor(Hook.ReturnValue.class);

   public HookContainerParser(HookClassTransformer transformer) {
      this.transformer = transformer;
   }

   protected void parseHooks(String className) {
      this.transformer.logger.debug("Parsing hooks container " + className);

      try {
         this.transformer.classMetadataReader.acceptVisitor(className, new HookClassVisitor());
      } catch (IOException var3) {
         this.transformer.logger.severe("Can not parse hooks container " + className, var3);
      }
   }

   protected void parseHooks(byte[] classData) {
   }

   private void invalidHook(String message) {
      this.transformer.logger.warning("Found invalid hook " + this.currentClassName + "#" + this.currentMethodName);
      this.transformer.logger.warning(message);
   }

   private void createHook() {
      AsmHook.Builder builder = AsmHook.newBuilder();
      Type methodType = Type.getMethodType(this.currentMethodDesc);
      Type[] argumentTypes = methodType.getArgumentTypes();
      if (!this.currentMethodPublicStatic) {
         this.invalidHook("Hook method must be public and static.");
      } else if (argumentTypes.length < 1) {
         this.invalidHook("Hook method has no parameters. First parameter of a hook method must belong the type of the target class.");
      } else if (argumentTypes[0].getSort() != 10) {
         this.invalidHook("First parameter of the hook method is not an object. First parameter of a hook method must belong the type of the target class.");
      } else {
         builder.setTargetClass(argumentTypes[0].getClassName());
         if (this.annotationValues.containsKey("targetMethod")) {
            builder.setTargetMethod((String)this.annotationValues.get("targetMethod"));
         } else {
            builder.setTargetMethod(this.currentMethodName);
         }

         builder.setHookClass(this.currentClassName);
         builder.setHookMethod(this.currentMethodName);
         builder.addThisToHookMethodParameters();
         boolean injectOnExit = Boolean.TRUE.equals(this.annotationValues.get("injectOnExit"));
         int currentParameterId = 1;

         for (int i = 1; i < argumentTypes.length; i++) {
            Type argType = argumentTypes[i];
            if (this.parameterAnnotations.containsKey(i)) {
               int localId = this.parameterAnnotations.get(i);
               if (localId == -1) {
                  builder.setTargetMethodReturnType(argType);
                  builder.addReturnValueToHookMethodParameters();
               } else {
                  builder.addHookMethodParameter(argType, localId);
               }
            } else {
               builder.addTargetMethodParameters(argType);
               builder.addHookMethodParameter(argType, currentParameterId);
               currentParameterId += argType != Type.LONG_TYPE && argType != Type.DOUBLE_TYPE ? 1 : 2;
            }
         }

         if (injectOnExit) {
            builder.setInjectorFactory(AsmHook.ON_EXIT_FACTORY);
         }

         if (this.annotationValues.containsKey("injectOnLine")) {
            int line = (Integer)this.annotationValues.get("injectOnLine");
            builder.setInjectorFactory(new HookInjectorFactory.LineNumber(line));
         }

         if (this.annotationValues.containsKey("returnType")) {
            builder.setTargetMethodReturnType((String)this.annotationValues.get("returnType"));
         }

         ReturnCondition returnCondition = ReturnCondition.NEVER;
         if (this.annotationValues.containsKey("returnCondition")) {
            returnCondition = ReturnCondition.valueOf((String)this.annotationValues.get("returnCondition"));
            builder.setReturnCondition(returnCondition);
         }

         if (returnCondition != ReturnCondition.NEVER) {
            Object primitiveConstant = this.getPrimitiveConstant();
            if (primitiveConstant != null) {
               builder.setReturnValue(ReturnValue.PRIMITIVE_CONSTANT);
               builder.setPrimitiveConstant(primitiveConstant);
            } else if (Boolean.TRUE.equals(this.annotationValues.get("returnNull"))) {
               builder.setReturnValue(ReturnValue.NULL);
            } else if (this.annotationValues.containsKey("returnAnotherMethod")) {
               builder.setReturnValue(ReturnValue.ANOTHER_METHOD_RETURN_VALUE);
               builder.setReturnMethod((String)this.annotationValues.get("returnAnotherMethod"));
            } else if (methodType.getReturnType() != Type.VOID_TYPE) {
               builder.setReturnValue(ReturnValue.HOOK_RETURN_VALUE);
            }
         }

         builder.setHookMethodReturnType(methodType.getReturnType());
         if (returnCondition == ReturnCondition.ON_TRUE && methodType.getReturnType() != Type.BOOLEAN_TYPE) {
            this.invalidHook("Hook method must return boolean if returnCodition is ON_TRUE.");
         } else if ((returnCondition == ReturnCondition.ON_NULL || returnCondition == ReturnCondition.ON_NOT_NULL)
            && methodType.getReturnType().getSort() != 10
            && methodType.getReturnType().getSort() != 9) {
            this.invalidHook("Hook method must return object if returnCodition is ON_NULL or ON_NOT_NULL.");
         } else {
            if (this.annotationValues.containsKey("priority")) {
               builder.setPriority(HookPriority.valueOf((String)this.annotationValues.get("priority")));
            }

            if (this.annotationValues.containsKey("createMethod")) {
               builder.setCreateMethod(Boolean.TRUE.equals(this.annotationValues.get("createMethod")));
            }

            if (this.annotationValues.containsKey("isMandatory")) {
               builder.setMandatory(Boolean.TRUE.equals(this.annotationValues.get("isMandatory")));
            }

            this.transformer.registerHook(builder.build());
         }
      }
   }

   private Object getPrimitiveConstant() {
      for (Entry<String, Object> entry : this.annotationValues.entrySet()) {
         if (entry.getKey().endsWith("Constant")) {
            return entry.getValue();
         }
      }

      return null;
   }

   private class HookAnnotationVisitor extends AnnotationVisitor {
      public HookAnnotationVisitor() {
         super(327680);
      }

      public void visit(String name, Object value) {
         if (HookContainerParser.this.inHookAnnotation) {
            HookContainerParser.this.annotationValues.put(name, value);
         }
      }

      public void visitEnum(String name, String desc, String value) {
         this.visit(name, value);
      }

      public void visitEnd() {
         HookContainerParser.this.inHookAnnotation = false;
      }
   }

   private class HookClassVisitor extends ClassVisitor {
      public HookClassVisitor() {
         super(327680);
      }

      public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
         HookContainerParser.this.currentClassName = name.replace('/', '.');
      }

      public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
         HookContainerParser.this.currentMethodName = name;
         HookContainerParser.this.currentMethodDesc = desc;
         HookContainerParser.this.currentMethodPublicStatic = (access & 1) != 0 && (access & 8) != 0;
         return HookContainerParser.this.new HookMethodVisitor();
      }
   }

   private class HookMethodVisitor extends MethodVisitor {
      public HookMethodVisitor() {
         super(327680);
      }

      public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
         if (HookContainerParser.HOOK_DESC.equals(desc)) {
            HookContainerParser.this.annotationValues = new HashMap<>();
            HookContainerParser.this.inHookAnnotation = true;
         }

         return HookContainerParser.this.new HookAnnotationVisitor();
      }

      public AnnotationVisitor visitParameterAnnotation(final int parameter, String desc, boolean visible) {
         if (HookContainerParser.RETURN_DESC.equals(desc)) {
            HookContainerParser.this.parameterAnnotations.put(parameter, -1);
         }

         return HookContainerParser.LOCAL_DESC.equals(desc) ? new AnnotationVisitor(327680) {
            public void visit(String name, Object value) {
               HookContainerParser.this.parameterAnnotations.put(parameter, (Integer)value);
            }
         } : null;
      }

      public void visitEnd() {
         if (HookContainerParser.this.annotationValues != null) {
            HookContainerParser.this.createHook();
         }

         HookContainerParser.this.parameterAnnotations.clear();
         HookContainerParser.this.currentMethodName = HookContainerParser.this.currentMethodDesc = null;
         HookContainerParser.this.currentMethodPublicStatic = false;
         HookContainerParser.this.annotationValues = null;
      }
   }
}
