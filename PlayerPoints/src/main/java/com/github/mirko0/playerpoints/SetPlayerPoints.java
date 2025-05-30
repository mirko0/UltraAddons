package com.github.mirko0.playerpoints;

import me.TechsCode.UltraCustomizer.UltraCustomizer;
import me.TechsCode.UltraCustomizer.base.item.XMaterial;
import me.TechsCode.UltraCustomizer.scriptSystem.objects.*;
import me.TechsCode.UltraCustomizer.scriptSystem.objects.datatypes.DataType;
import org.black_ixx.playerpoints.PlayerPoints;
import org.black_ixx.playerpoints.PlayerPointsAPI;
import org.bukkit.entity.Player;

public class SetPlayerPoints extends Element {
    public SetPlayerPoints(UltraCustomizer plugin) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "Set Player Points";
    }

    @Override
    public String getInternalName() {
        return "set-player-points";
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
        return new String[]{"Set player points balance.", "Unlike give this one will not add", "but actually set the points balance", "to provided value."};
    }

    @Override
    public Argument[] getArguments(ElementInfo elementInfo) {
        return new Argument[]{
                new Argument("player", "Player", DataType.PLAYER, elementInfo),
                new Argument("points", "Points", DataType.NUMBER, elementInfo)
        };
    }

    @Override
    public OutcomingVariable[] getOutcomingVariables(ElementInfo elementInfo) {
        return new OutcomingVariable[0];
    }

    @Override
    public Child[] getConnectors(ElementInfo elementInfo) {
        return new Child[]{new DefaultChild(elementInfo, "next")};
    }

    @Override
    public void run(ElementInfo elementInfo, ScriptInstance scriptInstance) {
        Player player = (Player) this.getArguments(elementInfo)[0].getValue(scriptInstance);
        long points = (long) this.getArguments(elementInfo)[1].getValue(scriptInstance);
        PlayerPointsAPI api = PlayerPoints.getInstance().getAPI();
        if (api == null) {
            UltraCustomizer.getInstance().log("PlayerPoints API is null. Unable to give points.");
            return;
        }
        api.set(player.getUniqueId(), (int) points);
        this.getConnectors(elementInfo)[0].run(scriptInstance);
    }
}
