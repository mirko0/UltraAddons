package com.github.mirko0.coinsengine;

import com.github.mirko0.coinsengine.datatypes.QDataTypes;
import me.TechsCode.UltraCustomizer.UltraCustomizer;
import me.TechsCode.UltraCustomizer.base.item.XMaterial;
import me.TechsCode.UltraCustomizer.scriptSystem.objects.*;
import me.TechsCode.UltraCustomizer.scriptSystem.objects.datatypes.DataType;
import org.bukkit.entity.Player;
import su.nightexpress.coinsengine.api.CoinsEngineAPI;
import su.nightexpress.coinsengine.api.currency.Currency;

public class RemoveBalance extends Element {
    public RemoveBalance(UltraCustomizer ultraCustomizer) {
        super(ultraCustomizer);
    }

    @Override
    public String getName() {
        return "Remove Balance (CoinsEngine)";
    }

    @Override
    public String getInternalName() {
        return "ce-remove-balance";
    }

    @Override
    public boolean isHidingIfNotCompatible() {
        return false;
    }

    @Override
    public XMaterial getMaterial() {
        return XMaterial.REDSTONE_BLOCK;
    }

    @Override
    public String[] getDescription() {
        return new String[]{"Remove money from users balance for selected currency."};
    }

    @Override
    public Argument[] getArguments(ElementInfo elementInfo) {
        return new Argument[]{
                new Argument("currency", "Currency", QDataTypes.CURRENCY_SPECIFICATION, elementInfo),
                new Argument("player", "Player", DataType.PLAYER, elementInfo),
                new Argument("balance", "Balance", DataType.DOUBLE, elementInfo)
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
        Currency currency = (Currency) this.getArguments(elementInfo)[0].getValue(scriptInstance);
        Player player = (Player) this.getArguments(elementInfo)[1].getValue(scriptInstance);
        double balance = (double) this.getArguments(elementInfo)[2].getValue(scriptInstance);
        CoinsEngineAPI.removeBalance(player, currency, balance);

        this.getConnectors(elementInfo)[0].run(scriptInstance);

    }
}
