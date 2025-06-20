package com.github.mirko0.coinsengine;

import com.github.mirko0.coinsengine.datatypes.QDataTypes;
import me.TechsCode.UltraCustomizer.UltraCustomizer;
import me.TechsCode.UltraCustomizer.base.item.XMaterial;
import me.TechsCode.UltraCustomizer.scriptSystem.objects.*;
import me.TechsCode.UltraCustomizer.scriptSystem.objects.datatypes.DataType;
import org.bukkit.entity.Player;
import su.nightexpress.coinsengine.api.CoinsEngineAPI;
import su.nightexpress.coinsengine.api.currency.Currency;

public class GetBalance extends Element {
    public GetBalance(UltraCustomizer ultraCustomizer) {
        super(ultraCustomizer);
    }

    @Override
    public String getName() {
        return "Get Balance (CoinsEngine)";
    }

    @Override
    public String getInternalName() {
        return "ce-get-balance";
    }

    @Override
    public boolean isHidingIfNotCompatible() {
        return false;
    }

    @Override
    public XMaterial getMaterial() {
        return XMaterial.GOLD_BLOCK;
    }

    @Override
    public String[] getDescription() {
        return new String[]{"Retrieve user balance for selected currency."};
    }

    @Override
    public Argument[] getArguments(ElementInfo elementInfo) {
        return new Argument[]{
                new Argument("currency", "Currency", QDataTypes.CURRENCY_SPECIFICATION, elementInfo),
                new Argument("player", "Player", DataType.PLAYER, elementInfo)
        };
    }

    @Override
    public OutcomingVariable[] getOutcomingVariables(ElementInfo elementInfo) {
        return new OutcomingVariable[]{
                new OutcomingVariable("balance", "Balance", DataType.DOUBLE, elementInfo)
        };
    }

    @Override
    public Child[] getConnectors(ElementInfo elementInfo) {
        return new Child[]{new DefaultChild(elementInfo, "next")};
    }

    @Override
    public void run(ElementInfo elementInfo, ScriptInstance scriptInstance) {
        Currency currency = (Currency) this.getArguments(elementInfo)[0].getValue(scriptInstance);
        Player player = (Player) this.getArguments(elementInfo)[1].getValue(scriptInstance);
        double balance = CoinsEngineAPI.getBalance(player, currency);

        this.getOutcomingVariables(elementInfo)[0].register(scriptInstance, new DataRequester() {
            @Override
            public Object request() {
                return balance;
            }
        });

        this.getConnectors(elementInfo)[0].run(scriptInstance);
    }
}
