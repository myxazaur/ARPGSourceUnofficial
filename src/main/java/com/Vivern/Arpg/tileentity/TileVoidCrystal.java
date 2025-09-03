package com.Vivern.Arpg.tileentity;

public class TileVoidCrystal extends TileMonsterSpawner {
   public int renderseed = 20;

   public TileVoidCrystal() {
      this.renderseed = rand.nextInt();
   }
}
