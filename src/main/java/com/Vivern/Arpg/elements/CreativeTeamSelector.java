//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.mobs.AbstractMob;
import java.lang.reflect.Field;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAITasks.EntityAITaskEntry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;

public class CreativeTeamSelector extends Item {
   public CreativeTeamSelector() {
      this.setRegistryName("creative_team_selector");
      this.setCreativeTab(CreativeTabs.MISC);
      this.setTranslationKey("creative_team_selector");
      this.setMaxDamage(100);
      this.setMaxStackSize(1);
   }

   public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
      ItemStack itemstack = player.getHeldItem(hand);
      player.setActiveHand(hand);
      RayTraceResult raytraceresult = this.rayTrace(world, player, false);
      NBTHelper.GiveNBTboolean(itemstack, true, "MakeAgressive");
      NBTHelper.GiveNBTboolean(itemstack, true, "Rewrite");
      NBTHelper.GiveNBTboolean(itemstack, false, "set");
      if (player.isSneaking()) {
         BlockPos pos1 = NBTHelper.GetNBTBlockPos(itemstack, "pos1");
         BlockPos pos2 = NBTHelper.GetNBTBlockPos(itemstack, "pos2");
         if (pos1 != null && pos2 != null) {
            String team = "";
            boolean agressive = true;
            boolean rewrite = true;
            if (itemstack.hasTagCompound()
               && itemstack.getTagCompound().hasKey("display", 10)
               && itemstack.getTagCompound().getCompoundTag("display").hasKey("Name")) {
               team = itemstack.getTagCompound().getCompoundTag("display").getString("Name");
            }

            agressive = NBTHelper.GetNBTboolean(itemstack, "MakeAgressive");
            rewrite = NBTHelper.GetNBTboolean(itemstack, "Rewrite");
            int count = 0;
            AxisAlignedBB aabb = new AxisAlignedBB(pos1, pos2);
            List<EntityCreature> list = world.getEntitiesWithinAABB(EntityCreature.class, aabb);
            if (!list.isEmpty()) {
               for (EntityCreature cr : list) {
                  if (cr instanceof AbstractMob) {
                     if (rewrite) {
                        ((AbstractMob)cr).team = team;
                        ((AbstractMob)cr).isAgressive = agressive;
                        count++;
                     } else {
                        if (((AbstractMob)cr).team.isEmpty()) {
                           ((AbstractMob)cr).team = team;
                           count++;
                        }

                        if (!((AbstractMob)cr).isAgressive) {
                           ((AbstractMob)cr).isAgressive = agressive;
                        }
                     }
                  } else {
                     if (rewrite) {
                        cr.getEntityData().setString("mobteam", team);
                        count++;
                     } else if (!cr.getEntityData().hasKey("mobteam") || cr.getEntityData().getString("mobteam").isEmpty()) {
                        cr.getEntityData().setString("mobteam", team);
                        count++;
                     }

                     if (agressive) {
                        Team.setAttackTeamAI(cr, true);
                        cr.getEntityData().setBoolean("addAgressiveAI", true);
                        boolean checksight = true;

                        try {
                           for (EntityAITaskEntry taske : cr.targetTasks.taskEntries) {
                              if (taske.action instanceof EntityAINearestAttackableTarget) {
                                 EntityAINearestAttackableTarget ai = (EntityAINearestAttackableTarget)taske.action;
                                 Field field = EntityAINearestAttackableTarget.class.getDeclaredField("shouldCheckSight");
                                 field.setAccessible(true);
                                 checksight = field.getBoolean(ai);
                                 break;
                              }
                           }
                        } catch (IllegalArgumentException var21) {
                           var21.printStackTrace();
                        } catch (IllegalAccessException var22) {
                           var22.printStackTrace();
                        } catch (NoSuchFieldException var23) {
                           var23.printStackTrace();
                        } catch (SecurityException var24) {
                           var24.printStackTrace();
                        }

                        cr.getEntityData().setBoolean("agressiveAIcheckSight", checksight);
                     }
                  }
               }
            }

            if (world.isRemote) {
               Minecraft.getMinecraft().player.sendChatMessage("Added " + count + " mobs to team: " + team);
            }
         }
      }

      if (raytraceresult == null) {
         return new ActionResult(EnumActionResult.PASS, itemstack);
      } else if (raytraceresult.typeOfHit != Type.BLOCK) {
         return new ActionResult(EnumActionResult.PASS, itemstack);
      } else {
         if (!player.isSneaking()) {
            BlockPos pos = raytraceresult.getBlockPos();
            BlockPos posup = pos.up();
            if (!NBTHelper.GetNBTboolean(itemstack, "set")) {
               NBTHelper.GiveNBTBlockPos(itemstack, pos, "pos1");
               NBTHelper.SetNBTBlockPos(itemstack, pos, "pos1");
               if (world.isRemote) {
                  Minecraft.getMinecraft().player.sendChatMessage("Setted position 1: " + pos);
               }

               NBTHelper.SetNBTboolean(itemstack, true, "set");
            } else {
               NBTHelper.GiveNBTBlockPos(itemstack, pos, "pos2");
               NBTHelper.SetNBTBlockPos(itemstack, pos, "pos2");
               if (world.isRemote) {
                  Minecraft.getMinecraft().player.sendChatMessage("Setted position 2: " + pos);
               }

               NBTHelper.SetNBTboolean(itemstack, false, "set");
            }
         }

         return new ActionResult(EnumActionResult.SUCCESS, itemstack);
      }
   }
}
