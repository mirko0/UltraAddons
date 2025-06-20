package com.github.mirko0.coinsengine.datatypes.specifications;

import me.TechsCode.UltraCustomizer.base.SpigotTechPlugin;
import me.TechsCode.UltraCustomizer.base.gui.Button;
import me.TechsCode.UltraCustomizer.base.gui.pageableview.BasicSearch;
import me.TechsCode.UltraCustomizer.base.gui.pageableview.PageableGUI;
import me.TechsCode.UltraCustomizer.base.gui.pageableview.SearchFeature;
import me.TechsCode.UltraCustomizer.base.item.XMaterial;
import me.TechsCode.UltraCustomizer.base.misc.Callback;
import me.TechsCode.UltraCustomizer.base.translations.Phrase;
import me.TechsCode.UltraCustomizer.base.visual.Animation;
import me.TechsCode.UltraCustomizer.base.visual.Colors;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import su.nightexpress.coinsengine.api.CoinsEngineAPI;
import su.nightexpress.coinsengine.api.currency.Currency;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

public class CurrencyPicker extends PageableGUI<Currency> {
    private static final Phrase CHOOSE_ACTION = Phrase.create("currencyPicker.choose.action", "**Click** to choose", Colors.GRAY, Colors.AQUA);;
    private final Currency[] currencies;
    private final String title;
    private Callback<Currency> callback;

    public CurrencyPicker(Player player, SpigotTechPlugin plugin, String title,  Callback<Currency> callback) {
        super(player, plugin);
        Collection<Currency> found = CoinsEngineAPI.getCurrencyManager().getCurrencies();
        this.currencies = found.toArray(new Currency[0]);
        this.title = title;
        this.callback = callback;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void onBack() {

    }

    public void construct(Button button, Currency item) {
        button
                .material(XMaterial.fromItem(item.getIcon()))
                .name(Animation.wave(item.getName(), Colors.GREEN, Colors.WHITE))
                .lore(CHOOSE_ACTION.get(), ChatColor.GRAY + "Currency Id: " + item.getId());
        button.action((actionType) -> this.choose(this.p, item));
    }

    public Currency[] getObjects() {
        return this.currencies;
    }

    public void choose(Player player, Currency currency) {
        this.callback.run(currency);
    }

    public SearchFeature<Currency> getSearch() {
        return new BasicSearch<>() {
            public String[] getSearchableText(Currency currency) {
                return new String[]{currency.getName(), currency.getId()};
            }
        };
    }
}
