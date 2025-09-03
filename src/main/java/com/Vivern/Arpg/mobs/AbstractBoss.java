//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.mobs;

import com.Vivern.Arpg.entity.EntityBossLoot;
import com.Vivern.Arpg.potions.PotionEffects;
import com.Vivern.Arpg.potions.RespawnPenalty;
import javax.annotation.Nullable;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EntitySelectors;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;
import net.minecraft.world.BossInfo.Color;
import net.minecraft.world.BossInfo.Overlay;

public abstract class AbstractBoss extends AbstractMob {
   public final BossInfoServer bossInfo;

   public AbstractBoss(World worldIn, float sizeWidth, float sizeHeight, Color barColor) {
      super(worldIn, sizeWidth, sizeHeight);
      this.bossInfo = new BossInfoServer(this.getDisplayName(), barColor, Overlay.PROGRESS);
   }

   public boolean isNonBoss() {
      return false;
   }

   public boolean isLootNoGravity() {
      return false;
   }

   public void addTrackingPlayer(EntityPlayerMP player) {
      super.addTrackingPlayer(player);
      this.bossInfo.addPlayer(player);
   }

   public void removeTrackingPlayer(EntityPlayerMP player) {
      super.removeTrackingPlayer(player);
      this.bossInfo.removePlayer(player);
   }

   @Override
   protected void updateAITasks() {
      super.updateAITasks();
      this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
   }

   @Override
   public void onUpdate() {
      super.onUpdate();
      if (!this.world.isRemote && this.ticksExisted % 10 == 0) {
         for (EntityPlayer trackingPlayer : this.bossInfo.getPlayers()) {
            if (!trackingPlayer.isDead) {
               trackingPlayer.addPotionEffect(new PotionEffect(PotionEffects.RESPAWN_PENALTY, RespawnPenalty.getNormalBossPenalty(), 0, true, false));
            }
         }

         if (!this.isNoDespawnRequired() && this.ticksExisted % 100 == 0) {
            boolean alive = false;

            for (EntityPlayer trackingPlayerx : this.bossInfo.getPlayers()) {
               if (trackingPlayerx.isEntityAlive() && EntitySelectors.NOT_SPECTATING.apply(trackingPlayerx)) {
                  alive = true;
                  break;
               }
            }

            if (!alive) {
               this.setDead();
            }
         }
      }
   }

   @Nullable
   public EntityItem entityDropItem(ItemStack stack, float offsetY) {
      if (stack.isEmpty()) {
         return null;
      } else {
         EntityBossLoot entityitem = new EntityBossLoot(this.world, this.posX, this.posY + offsetY, this.posZ, stack);
         entityitem.setNoGravity(this.isLootNoGravity());
         entityitem.setHomePosition();
         this.world.spawnEntity(entityitem);
         return entityitem;
      }
   }
}
