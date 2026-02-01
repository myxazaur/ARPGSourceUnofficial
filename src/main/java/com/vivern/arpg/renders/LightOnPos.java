package com.vivern.arpg.renders;

import javax.annotation.concurrent.Immutable;

@Immutable
public class LightOnPos {
   public final int bakedCoord;
   public final long channel;
   public final long value;
   public LoadedRGBChunk chunk;

   public LightOnPos(int bakedCoord, long channel, long value, LoadedRGBChunk chunk) {
      this.bakedCoord = bakedCoord;
      this.channel = channel;
      this.value = value;
      this.chunk = chunk;
   }
}
