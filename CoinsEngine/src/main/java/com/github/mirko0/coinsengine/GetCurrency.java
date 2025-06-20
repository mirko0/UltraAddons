package com.github.mirko0.coinsengine;

import com.github.mirko0.coinsengine.datatypes.QDataTypes;
import me.TechsCode.UltraCustomizer.UltraCustomizer;
import me.TechsCode.UltraCustomizer.base.item.XMaterial;
import me.TechsCode.UltraCustomizer.scriptSystem.objects.*;
import me.TechsCode.UltraCustomizer.scriptSystem.objects.datatypes.DataType;
import org.bukkit.entity.Player;
import su.nightexpress.coinsengine.api.CoinsEngineAPI;
import su.nightexpress.coinsengine.api.currency.Currency;

public class GetCurrency extends Element {
    public GetCurrency(UltraCustomizer ultraCustomizer) {
        super(ultraCustomizer);
        QDataTypes.registerDataTypes();
    }

    @Override
    public String getName() {
        return "Get Currency (CoinsEngine)";
    }

    @Override
    public String getInternalName() {
        return "ce-get-currency";
    }

    @Override
    public boolean isHidingIfNotCompatible() {
        return false;
    }

    @Override
    public XMaterial getMaterial() {
        return XMaterial.EMERALD;
    }

    @Override
    public String[] getDescription() {
        return new String[]{"Retrieve CoinsEngine currency by id."};
    }

    @Override
    public Argument[] getArguments(ElementInfo elementInfo) {
        return new Argument[]{
                new Argument("name", "Name", DataType.STRING, elementInfo),
        };
    }

    @Override
    public OutcomingVariable[] getOutcomingVariables(ElementInfo elementInfo) {
        return new OutcomingVariable[]{
                new OutcomingVariable("currency", "Currency", QDataTypes.CURRENCY_SPECIFICATION, elementInfo)
        };
    }

    @Override
    public Child[] getConnectors(ElementInfo elementInfo) {
        return new Child[]{
                new Child(elementInfo, "success") {
                    @Override
                    public String getName() {
                        return "Currency Found";
                    }

                    @Override
                    public String[] getDescription() {
                        return new String[]{"Will be executed if the", "currency was successfully found."};
                    }

                    @Override
                    public XMaterial getIcon() {
                        return XMaterial.GREEN_STAINED_GLASS_PANE;
                    }
                },
                new Child(elementInfo, "error") {
                    @Override
                    public String getName() {
                        return "Currency Not Found";
                    }

                    @Override
                    public String[] getDescription() {
                        return new String[]{"Will be executed if the currency", "was not found."};
                    }
                    @Override
                    public XMaterial getIcon() {
                        return XMaterial.RED_STAINED_GLASS_PANE;
                    }
                }};
    }

    @Override
    public void run(ElementInfo elementInfo, ScriptInstance scriptInstance) {
        String currencyId = (String) this.getArguments(elementInfo)[0].getValue(scriptInstance);
        Currency currency = CoinsEngineAPI.getCurrency(currencyId);
        if (currency != null) {
            this.getOutcomingVariables(elementInfo)[0].register(scriptInstance, new DataRequester() {
                @Override
                public Object request() {
                    return currency;
                }
            });
            this.getConnectors(elementInfo)[0].run(scriptInstance);
            return;
        }
        this.getOutcomingVariables(elementInfo)[0].register(scriptInstance, new DataRequester() {
            @Override
            public Object request() {
                return null;
            }
        });
        this.getConnectors(elementInfo)[1].run(scriptInstance);
        return;
    }
}
