package com.vivern.arpg.shader;

public class ShaderMain {
   public static ShaderProgram shaderA;
   public static ShaderProgram GUNPShader;
   public static ShaderProgram WavesShader;
   public static ShaderProgram MixShader;
   public static ShaderProgram RainbowShader;
   public static ShaderProgram LensblurShader;
   public static ShaderProgram InverseShader;
   public static ShaderProgram MoltenShader;
   public static ShaderProgram WindShader;
   public static ShaderProgram RadShader;
   public static ShaderProgram RuneShader;
   public static ShaderProgram ForgeShader;
   public static int shaderCounter = 0;
   public static int maxShaderCounter = 50;

   public static void register() {
      shaderA = new ShaderProgram().addFragment("shaders/testshader.frag").compile();
      GUNPShader = new ShaderProgram().addFragment("shaders/gunparticleshader.frag").compile();
      WavesShader = new ShaderProgram().addFragment("shaders/waves.frag").compile();
      MixShader = new ShaderProgram().addFragment("shaders/mix.frag").compile();
      RainbowShader = new ShaderProgram().addFragment("shaders/rainbow.frag").compile();
      LensblurShader = new ShaderProgram().addFragment("shaders/lensblur.frag").compile();
      InverseShader = new ShaderProgram().addFragment("shaders/inverse.frag").compile();
      MoltenShader = new ShaderProgram().addFragment("shaders/molten.frag").compile();
      WindShader = new ShaderProgram().addFragment("shaders/wind.frag").compile();
      RadShader = new ShaderProgram().addFragment("shaders/rad.frag").compile();
      RuneShader = new ShaderProgram().addFragment("shaders/rune.frag").compile();
      ForgeShader = new ShaderProgram().addFragment("shaders/forge.frag").compile();
   }
}
