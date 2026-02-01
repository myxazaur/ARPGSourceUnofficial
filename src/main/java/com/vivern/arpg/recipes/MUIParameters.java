package com.vivern.arpg.recipes;

import com.vivern.arpg.entity.EntityMagicUI;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public interface MUIParameters {
   default float modifyPower(EntityMagicUI muiExecutor, EntityMagicUI muiModificator, float power, Entity activatingEntity, ManaProvider mana) {
      return power;
   }

   default float modifyRange(EntityMagicUI muiExecutor, EntityMagicUI muiModificator, float range, Entity activatingEntity, ManaProvider mana) {
      return range;
   }

   default Vec3d modifyFlowVector(EntityMagicUI muiExecutor, EntityMagicUI muiModificator, Vec3d flow, Entity activatingEntity, ManaProvider mana) {
      return flow;
   }

   default float modifyChaos(EntityMagicUI muiExecutor, EntityMagicUI muiModificator, float chaos, Entity activatingEntity, ManaProvider mana) {
      return chaos;
   }

   default boolean modifyMight(EntityMagicUI muiExecutor, EntityMagicUI muiModificator, boolean might, Entity activatingEntity, ManaProvider mana) {
      return might;
   }

   default void modifyEvil(
      EntityMagicUI muiExecutor,
      EntityMagicUI muiModificator,
      float power,
      float range,
      Vec3d flow,
      float chaos,
      boolean might,
      Entity activatingEntity,
      ManaProvider mana,
      float manaSpended
   ) {
   }
}
