package com.Vivern.Arpg.recipes;

import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayer;

public class TerraformingPlayerCommand {
   public TRPlayerCommandType commandType;
   public int boardSelectX;
   public int boardSelectY;
   public int data;
   @Nullable
   public EntityPlayer player;

   public TerraformingPlayerCommand(TRPlayerCommandType commandType, int boardSelectX, int boardSelectY, int data) {
      this.commandType = commandType;
      this.boardSelectX = boardSelectX;
      this.boardSelectY = boardSelectY;
      this.data = data;
   }

   public TerraformingPlayerCommand(
      TRPlayerCommandType commandType, int boardSelectX, int boardSelectY, int data, EntityPlayer player
   ) {
      this(commandType, boardSelectX, boardSelectY, data);
      this.player = player;
   }

   @Override
   public String toString() {
      return "X: " + this.boardSelectX + " | Y: " + this.boardSelectY + " | DATA: " + this.data + " | " + this.commandType.toString();
   }

   public static enum TRPlayerCommandType {
      SKIP,
      SKIP_ALL,
      PLACE_FROM_INVENTORY,
      BOARD_SELECT,
      MOVE_SELECTED,
      GET_ORB,
      SPLIT,
      CANCEL;
   }
}
