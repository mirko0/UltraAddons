package com.github.mirko0.playerpoints;

import me.TechsCode.UltraCustomizer.UltraCustomizer;
import me.TechsCode.UltraCustomizer.base.item.XMaterial;
import me.TechsCode.UltraCustomizer.scriptSystem.objects.*;
import me.TechsCode.UltraCustomizer.scriptSystem.objects.datatypes.DataType;
import org.black_ixx.playerpoints.PlayerPoints;
import org.black_ixx.playerpoints.PlayerPointsAPI;
import org.bukkit.entity.Player;

public class GetPlayerPointsFormatted extends Element {
    public GetPlayerPointsFormatted(UltraCustomizer plugin) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "Get Player Points (Formatted)";
    }

    @Override
    public String getInternalName() {
        return "get-player-points-formatted";
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
        return new String[]{"Get the player points balance (Formatted)."};
    }

    @Override
    public Argument[] getArguments(ElementInfo elementInfo) {
        return new Argument[]{new Argument("player", "Player", DataType.PLAYER, elementInfo)};
    }

    @Override
    public OutcomingVariable[] getOutcomingVariables(ElementInfo elementInfo) {
        return new OutcomingVariable[]{new OutcomingVariable("points", "Points", DataType.STRING, elementInfo)};
    }

    @Override
    public Child[] getConnectors(ElementInfo elementInfo) {
        return new Child[]{new DefaultChild(elementInfo, "next")};
    }

    @Override
    public void run(ElementInfo elementInfo, ScriptInstance scriptInstance) {
        Player player = (Player) this.getArguments(elementInfo)[0].getValue(scriptInstance);
        PlayerPointsAPI api = PlayerPoints.getInstance().getAPI();
        if (api == null) {
            UltraCustomizer.getInstance().log("PlayerPoints API is null. Unable to obtain data.");
            return;
        }

        this.getOutcomingVariables(elementInfo)[0].register(scriptInstance, new DataRequester() {
            @Override
            public Object request() {
                return api.lookFormatted(player.getUniqueId());
            }
        });
        this.getConnectors(elementInfo)[0].run(scriptInstance);
    }
}
