package com.github.mirko0.coinsengine.datatypes.specifications;

import me.TechsCode.UltraCustomizer.Folder;
import me.TechsCode.UltraCustomizer.UltraCustomizer;
import me.TechsCode.UltraCustomizer.base.misc.Callback;
import me.TechsCode.UltraCustomizer.base.translations.Phrase;
import me.TechsCode.UltraCustomizer.scriptSystem.objects.OutcomingVariable;
import me.TechsCode.UltraCustomizer.scriptSystem.objects.ScriptInstance;
import me.TechsCode.UltraCustomizer.scriptSystem.objects.datatypes.DataType;
import me.TechsCode.UltraCustomizer.scriptSystem.objects.datatypes.DataTypeSpecification;
import me.TechsCode.UltraCustomizer.scriptSystem.objects.datatypes.MaterialSpecification;
import org.bukkit.entity.Player;
import su.nightexpress.coinsengine.api.CoinsEngineAPI;
import su.nightexpress.coinsengine.api.currency.Currency;

import java.time.OffsetDateTime;

public class CurrencySpecification extends DataTypeSpecification {

    private static final String PREFIX = "CurrencySpecification";

    private static final Phrase GET_PHRASE = Phrase.create(PREFIX + ".title", "Select Currency");

    @Override
    public String getName() {
        return "Currency";
    }

    @Override
    public String getCreatePhrase() {
        return GET_PHRASE.get();
    }

    private static final Phrase GET_DESCRIPTION = Phrase.create(PREFIX + "description", "Open Inventory to select CoinsEngine currency.");

    @Override
    public String[] getCreateDescription() {
        return new String[]{
                GET_DESCRIPTION.get()
        };
    }

    private static final Phrase GET_DISPLAY = Phrase.create(PREFIX + "display", "Currency");

    @Override
    public String getDisplay(Object object, OutcomingVariable[] variables) {
        return GET_DISPLAY.get();
    }

    @Override
    public Object getAsValue(Object object, ScriptInstance instance, OutcomingVariable[] variables) {
        return object;
    }

    @Override
    public String serialize(Object object) {
        Currency casted = (Currency) object;
        return casted.getId();
    }

    @Override
    public Object deserialize(String data, Folder folder) {
        return CoinsEngineAPI.getCurrency(data);
    }


    @Override
    public void open(Player p, UltraCustomizer plugin, String name, String description, OutcomingVariable[] variables, Folder folder, Callback<Object> callback) {
        new CurrencyPicker(p, plugin, name, callback::run);
    }
}
