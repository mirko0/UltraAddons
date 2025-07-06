package com.github.mirko0.numberconverter;

import me.TechsCode.UltraCustomizer.UltraCustomizer;
import me.TechsCode.UltraCustomizer.base.item.XMaterial;
import me.TechsCode.UltraCustomizer.base.translations.Phrase;
import me.TechsCode.UltraCustomizer.scriptSystem.objects.*;
import me.TechsCode.UltraCustomizer.scriptSystem.objects.datatypes.DataType;

public class LongToDouble extends Element {
    private static final String PREFIX = "LongToDouble";
    private static final Phrase GET_NAME = Phrase.create(PREFIX + ".name", "Number To Double");
    private static final Phrase GET_DESCRIPTION = Phrase.create(PREFIX + ".desc", "Convert Number To Double");

    private static final Phrase LONG_NAME = Phrase.create("CastingElements" + ".long", "Number");
    private static final Phrase DOUBLE_NAME = Phrase.create("CastingElements" + ".double", "Double");

    private static final String INTERNAL_NAME = "float-to-number";

    public LongToDouble(UltraCustomizer plugin) {
        super(plugin);
    }

    @Override
    public String getName() {
        return GET_NAME.get();
    }

    @Override
    public String getInternalName() {
        return INTERNAL_NAME;
    }

    @Override
    public boolean isHidingIfNotCompatible() {
        return false;
    }

    @Override
    public XMaterial getMaterial() {
        return XMaterial.REDSTONE;
    }

    public String[] getDescription() {
        return new String[]{GET_DESCRIPTION.get()};
    }

    @Override
    public Argument[] getArguments(ElementInfo elementInfo) {
        return new Argument[]{
                new Argument("long", LONG_NAME.get(), DataType.NUMBER, elementInfo),
        };
    }

    @Override
    public OutcomingVariable[] getOutcomingVariables(ElementInfo elementInfo) {
        return new OutcomingVariable[]{
                new OutcomingVariable("double", DOUBLE_NAME.get(), DataType.DOUBLE, elementInfo)
        };
    }

    @Override
    public Child[] getConnectors(ElementInfo elementInfo) {
        return new Child[]{new DefaultChild(elementInfo, "next")};
    }

    @Override
    public void run(ElementInfo elementInfo, ScriptInstance scriptInstance) {
        Long argument = (Long) getArguments(elementInfo)[0].getValue(scriptInstance);
        double variable = argument;
        getOutcomingVariables(elementInfo)[0].register(scriptInstance, new DataRequester() {
            @Override
            public Object request() {
                return variable;
            }
        });
        this.getConnectors(elementInfo)[0].run(scriptInstance);
    }
}
