//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.main;

import com.Vivern.Arpg.dimensions.generationutils.AbstractWorldProvider;
import com.Vivern.Arpg.mobs.AbstractMob;
import com.Vivern.Arpg.mobs.EntityAIAttackOtherTeam;
import com.Vivern.Arpg.potions.PotionEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITasks.EntityAITaskEntry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.EntitySelectors;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(
   modid = "arpg"
)
public class Team {
   public static String nameOfPersonalTeam(String playerName) {
      return "p " + playerName;
   }

   @SubscribeEvent
   public static void onEntityConstructing(EntityJoinWorldEvent e) {
      if (!e.getWorld().isRemote) {
         Entity entity = e.getEntity();
         AbstractWorldProvider.onEntityJoinWorld(e);
         if (entity.getEntityData().hasKey("addAgressiveAI")
            && entity.getEntityData().hasKey("agressiveAIcheckSight")
            && entity.getEntityData().getBoolean("addAgressiveAI")) {
            setAttackTeamAI(entity, entity.getEntityData().getBoolean("agressiveAIcheckSight"));
         }

         if (entity instanceof EntityPlayer) {
            checkAndCreateTeam((EntityPlayer)entity);
         }
      }
   }

   public static void checkAndCreateTeam(EntityPlayer player) {
      if (!player.world.isRemote && player.getTeam() == null) {
         try {
            Scoreboard scoreboard = player.getWorldScoreboard();
            String teamToCreateName = nameOfPersonalTeam(player.getName());
            if (scoreboard.getTeam(teamToCreateName) == null) {
               scoreboard.createTeam(teamToCreateName);
               scoreboard.addPlayerToTeam(player.getName(), teamToCreateName);
            } else {
               scoreboard.addPlayerToTeam(player.getName(), teamToCreateName);
            }
         } catch (Exception var3) {
            System.out.println("Unable to create presonal player team for: " + player);
         }
      }
   }

   public static ScorePlayerTeam getTeamObjectFromString(World world, String teamname) {
      return world.getScoreboard().getTeam(teamname);
   }

   public static boolean isOnSameTeam(Entity entity1, Entity entity2) {
      if (entity1.getTeam() != null && entity2.getTeam() != null && entity1.getTeam() == entity2.getTeam()) {
         return true;
      } else {
         String team1 = "";
         String team2 = "";
         if (entity1 instanceof AbstractMob) {
            team1 = ((AbstractMob)entity1).team;
         } else if (entity1.getEntityData().hasKey("mobteam")) {
            team1 = entity1.getEntityData().getString("mobteam");
         } else if (entity1.getTeam() != null) {
            team1 = entity1.getTeam().getName();
         }

         if (entity2 instanceof AbstractMob) {
            team2 = ((AbstractMob)entity2).team;
         } else if (entity2.getEntityData().hasKey("mobteam")) {
            team2 = entity2.getEntityData().getString("mobteam");
         } else if (entity2.getTeam() != null) {
            team2 = entity2.getTeam().getName();
         }

         return !team1.isEmpty() && !team2.isEmpty() && team1.equals(team2);
      }
   }

   public static void setAttackTeamAI(Entity entity, boolean checkSight) {
      if (entity instanceof EntityCreature) {
         setAttackTeamAI((EntityCreature)entity, checkSight);
      }
   }

   public static void setAttackTeamAI(EntityCreature entity, boolean checkSight) {
      for (EntityAITaskEntry task : entity.targetTasks.taskEntries) {
         if (task.action instanceof EntityAIAttackOtherTeam) {
            return;
         }
      }

      entity.targetTasks.addTask(3, new EntityAIAttackOtherTeam(entity, EntityLiving.class, checkSight));
   }

   public static String getTeamFor(Entity entity) {
      if (entity.getTeam() != null && entity.getTeam().getName() != null) {
         return entity.getTeam().getName();
      } else if (entity instanceof AbstractMob) {
         return ((AbstractMob)entity).team;
      } else {
         return entity.getEntityData().hasKey("mobteam") ? entity.getEntityData().getString("mobteam") : "";
      }
   }

   public static boolean checkIsOpponent(Entity damager, Entity target) {
      if (target == null) {
         return false;
      } else if (damager == null) {
         return true;
      } else if (damager == target) {
         return false;
      } else if (!EntitySelectors.CAN_AI_TARGET.apply(target)) {
         return false;
      } else {
         if (damager instanceof EntityLivingBase) {
            EntityLivingBase amb = (EntityLivingBase)damager;
            PotionEffect eff = amb.getActivePotionEffect(PotionEffects.INSANE);
            if (eff != null && eff.getAmplifier() == 3) {
               return true;
            }
         }

         if (target instanceof EntityLivingBase) {
            EntityLivingBase amb = (EntityLivingBase)target;
            PotionEffect eff = amb.getActivePotionEffect(PotionEffects.INSANE);
            if (eff != null && eff.getAmplifier() == 3) {
               return true;
            }
         }

         return !isOnSameTeam(damager, target);
      }
   }
}
