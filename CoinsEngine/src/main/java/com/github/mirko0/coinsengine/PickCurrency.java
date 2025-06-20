package com.github.mirko0.coinsengine;

import com.github.mirko0.coinsengine.datatypes.QDataTypes;
import com.github.mirko0.coinsengine.datatypes.specifications.CurrencyPicker;
import me.TechsCode.UltraCustomizer.UltraCustomizer;
import me.TechsCode.UltraCustomizer.base.item.XMaterial;
import me.TechsCode.UltraCustomizer.scriptSystem.objects.*;
import me.TechsCode.UltraCustomizer.scriptSystem.objects.datatypes.DataType;
import org.bukkit.entity.Player;

public class PickCurrency extends Element {
    public PickCurrency(UltraCustomizer ultraCustomizer) {
        super(ultraCustomizer);
    }

    @Override
    public String getName() {
        return "User Input Currency";
    }

    @Override
    public String getInternalName() {
        return "ce-pick-currency";
    }

    @Override
    public boolean isHidingIfNotCompatible() {
        return false;
    }

    @Override
    public XMaterial getMaterial() {
        return XMaterial.PAPER;
    }

    @Override
    public String[] getDescription() {
        return new String[]{"Allows user to pick a currency"};
    }

    @Override
    public Argument[] getArguments(ElementInfo elementInfo) {
        return new Argument[]{new Argument("player", "Player", DataType.PLAYER, elementInfo)};
    }

    @Override
    public OutcomingVariable[] getOutcomingVariables(ElementInfo elementInfo) {
        return new OutcomingVariable[]{
                new OutcomingVariable("currency", "Currency", QDataTypes.CURRENCY_SPECIFICATION, elementInfo)
        };
    }

    @Override
    public Child[] getConnectors(ElementInfo elementInfo) {
        return new Child[]{new DefaultChild(elementInfo, "next")};
    }

    @Override
    public void run(ElementInfo elementInfo, ScriptInstance scriptInstance) {
        Player player = (Player) this.getArguments(elementInfo)[0].getValue(scriptInstance);

        if (player != null && player.isOnline()) {
            new CurrencyPicker(player, plugin, "Select a Currency", (currency) -> {
                player.closeInventory();
                this.getOutcomingVariables(elementInfo)[0].register(scriptInstance, new DataRequester() {
                    public Object request() {
                        return currency;
                    }
                });
                this.getConnectors(elementInfo)[0].run(scriptInstance);
            });

        }
    }
}
