//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.main;

import com.Vivern.Arpg.network.PacketHandler;
import com.Vivern.Arpg.network.PacketInfluenceToClients;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;

@EventBusSubscriber(
   modid = "arpg"
)
public abstract class EntityInfluence {
   public static HashMap<Entity, EntityInfluence> entitiesWithInfluenceServer = new HashMap<>();
   public static HashMap<Entity, EntityInfluence> entitiesWithInfluenceClient = new HashMap<>();
   public static HashMap<Integer, EntityInfluence> influenceById = new HashMap<>();
   public static ArrayList<Entity> toRemoveServer = new ArrayList<>();
   public static ArrayList<Entity> toRemoveClient = new ArrayList<>();
   public static int startid = 0;
   public int id = startid++;
   public boolean synchronizeToClients;

   public EntityInfluence(boolean synchronizeToClients) {
      if (synchronizeToClients) {
         influenceById.put(this.id, this);
      }

      this.synchronizeToClients = synchronizeToClients;
   }

   public abstract void onUpdate(Entity var1);

   public abstract void onImpact(Entity var1, RayTraceResult var2);

   public abstract void clientTick(Entity var1);

   public static void addEntityInfluence(Entity entity, EntityInfluence influence, double senddistance) {
      if (!entity.world.isRemote) {
         if (!entitiesWithInfluenceServer.containsKey(entity)) {
            entitiesWithInfluenceServer.put(entity, influence);
            if (influence.synchronizeToClients) {
               PacketInfluenceToClients packet = new PacketInfluenceToClients();
               packet.writeargs(influence.id, entity.getEntityId());
               PacketHandler.sendToAllAround(packet, entity.world, entity.posX, entity.posY, entity.posZ, senddistance);
            }
         }
      } else if (!entitiesWithInfluenceClient.containsKey(entity)) {
         entitiesWithInfluenceClient.put(entity, influence);
      }
   }

   public static void removeEntityInfluence(Entity entity) {
      if (!entity.world.isRemote) {
         toRemoveServer.add(entity);
      } else {
         toRemoveClient.add(entity);
      }
   }

   @SubscribeEvent
   public static void TickWorld(WorldTickEvent event) {
      if (!event.world.isRemote) {
         if (!toRemoveServer.isEmpty()) {
            for (Entity entity : toRemoveServer) {
               entitiesWithInfluenceServer.remove(entity);
            }

            toRemoveServer.clear();
         }

         for (Entry<Entity, EntityInfluence> entry : entitiesWithInfluenceServer.entrySet()) {
            Entity entity = entry.getKey();
            if (!entity.isDead) {
               if (!entity.updateBlocked) {
                  entry.getValue().onUpdate(entity);
               }
            } else {
               toRemoveServer.add(entity);
            }
         }
      }
   }

   @SubscribeEvent
   public static void ClientTick(ClientTickEvent event) {
      if (!toRemoveClient.isEmpty()) {
         for (Entity entity : toRemoveClient) {
            entitiesWithInfluenceClient.remove(entity);
         }

         toRemoveClient.clear();
      }

      for (Entry<Entity, EntityInfluence> entry : entitiesWithInfluenceClient.entrySet()) {
         Entity entity = entry.getKey();
         if (!entity.isDead) {
            if (!entity.updateBlocked) {
               entry.getValue().clientTick(entity);
            }
         } else {
            toRemoveClient.add(entity);
         }
      }
   }

   @SubscribeEvent
   public static void ProjectileImpact(ProjectileImpactEvent event) {
      if (!event.getEntity().world.isRemote) {
         EntityInfluence inf = entitiesWithInfluenceServer.get(event.getEntity());
         if (inf != null) {
            inf.onImpact(event.getEntity(), event.getRayTraceResult());
         }
      } else {
         EntityInfluence inf = entitiesWithInfluenceClient.get(event.getEntity());
         if (inf != null) {
            inf.onImpact(event.getEntity(), event.getRayTraceResult());
         }
      }
   }
}
