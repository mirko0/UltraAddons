package com.github.mirko0.playerpoints;

import me.TechsCode.UltraCustomizer.UltraCustomizer;
import me.TechsCode.UltraCustomizer.base.item.XMaterial;
import me.TechsCode.UltraCustomizer.scriptSystem.objects.*;
import me.TechsCode.UltraCustomizer.scriptSystem.objects.datatypes.DataType;
import org.black_ixx.playerpoints.PlayerPoints;
import org.black_ixx.playerpoints.PlayerPointsAPI;
import org.bukkit.entity.Player;

public class HasEnoughPoints extends Element {
    public HasEnoughPoints(UltraCustomizer plugin) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "Player Has Points";
    }

    @Override
    public String getInternalName() {
        return "has-player-points";
    }

    @Override
    public boolean isHidingIfNotCompatible() {
        return false;
    }

    @Override
    public XMaterial getMaterial() {
        return XMaterial.GOLD_NUGGET;
    }

    @Override
    public String[] getDescription() {
        return new String[]{"Check if player has enough points."};
    }

    @Override
    public Argument[] getArguments(ElementInfo elementInfo) {
        return new Argument[]{
                new Argument("player", "Player", DataType.PLAYER, elementInfo),
                new Argument("points", "Points", DataType.NUMBER, elementInfo),
        };
    }

    @Override
    public OutcomingVariable[] getOutcomingVariables(ElementInfo elementInfo) {
        return new OutcomingVariable[0];
    }

    @Override
    public Child[] getConnectors(ElementInfo elementInfo) {
        return new Child[]{
                new Child(elementInfo, "true") {
                    @Override
                    public String getName() {
                        return "Enough Points";
                    }

                    @Override
                    public String[] getDescription() {
                        return new String[]{"Will be executed if", "the player has enough points."};
                    }

                    @Override
                    public XMaterial getIcon() {
                        return XMaterial.WHITE_STAINED_GLASS_PANE;
                    }
                },
                new Child(elementInfo, "false") {
                    @Override
                    public String getName() {
                        return "Not Enough Points";
                    }

                    @Override
                    public String[] getDescription() {
                        return new String[]{"Will be executed if the player", "does not have enough points."};
                    }

                    @Override
                    public XMaterial getIcon() {
                        return XMaterial.BLACK_STAINED_GLASS_PANE;
                    }
                }};
    }
    @Override
    public void run(ElementInfo elementInfo, ScriptInstance scriptInstance) {
        Player player = (Player) this.getArguments(elementInfo)[0].getValue(scriptInstance);
        long requiredPoints = (long) this.getArguments(elementInfo)[1].getValue(scriptInstance);
        PlayerPointsAPI api = PlayerPoints.getInstance().getAPI();
        if (api == null) {
            UltraCustomizer.getInstance().log("PlayerPoints API is null. Unable to obtain data.");
            return;
        }
        long points = api.look(player.getUniqueId());

        if (points >= requiredPoints) {
                this.getConnectors(elementInfo)[0].run(scriptInstance);
        } else {
            this.getConnectors(elementInfo)[1].run(scriptInstance);
        }
    }
}
