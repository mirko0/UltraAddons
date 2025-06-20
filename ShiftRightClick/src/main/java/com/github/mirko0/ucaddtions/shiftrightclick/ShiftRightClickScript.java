package com.github.mirko0.ucaddtions.shiftrightclick;


import me.TechsCode.UltraCustomizer.UltraCustomizer;
import me.TechsCode.UltraCustomizer.base.item.XMaterial;
import me.TechsCode.UltraCustomizer.base.legacy.utils.MinecraftVersion;
import me.TechsCode.UltraCustomizer.scriptSystem.objects.*;
import me.TechsCode.UltraCustomizer.scriptSystem.objects.datatypes.DataType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ShiftRightClickScript extends Constructor {
    public static RightClickLogic logic;
    public ShiftRightClickScript(UltraCustomizer ultraCustomizer) {
        super(ultraCustomizer);
    }

    public String getName() {
        return "Shift Right Click";
    }

    public String getInternalName() {
        return "on-shift-right-click";
    }

    public XMaterial getMaterial() {
        return XMaterial.FISHING_ROD;
    }

    public String[] getDescription() {
        return new String[] { "Will be executed when player is shifting and right-clicking." };
    }

    public Argument[] getArguments(ElementInfo elementInfo) {
        return new Argument[0];
    }

    public OutcomingVariable[] getOutcomingVariables(ElementInfo elementInfo) {
        return new OutcomingVariable[] { new OutcomingVariable("player", "Player", DataType.PLAYER, elementInfo),
                new OutcomingVariable("itemInHand", "Item in hand", DataType.ITEM, elementInfo),
                new OutcomingVariable("material", "Material", DataType.MATERIAL, elementInfo) };
    }

    public boolean isUnlisted() {
        return false;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (logic == null){
            if (MinecraftVersion.getServersVersion().isAboveOrEqual(MinecraftVersion.V1_9_R1)){
                logic = new NewVersionLogic();
            }else
                logic = new OldVersionLogic();
        }

        Player p = event.getPlayer();
        if (!p.isSneaking())
            return;
        if (logic.getHand(event))
            return;
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            final ItemStack item = logic.getItemInMainHand(p);
            call(elementInfo -> {
                ScriptInstance instance = new ScriptInstance();
                getOutcomingVariables(elementInfo)[0].register(instance, new DataRequester() {
                    public Object request() {
                        return p;
                    }
                });
                getOutcomingVariables(elementInfo)[1].register(instance, new DataRequester() {
                    public Object request() {
                        return item;
                    }
                });
                getOutcomingVariables(elementInfo)[2].register(instance, new DataRequester() {
                    public Object request() {
                        return XMaterial.fromItem(item);
                    }
                });
                return instance;
            });
        }
    }
}