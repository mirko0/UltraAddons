package com.github.mirko0.numberconverter;

import me.TechsCode.UltraCustomizer.UltraCustomizer;
import me.TechsCode.UltraCustomizer.base.item.XMaterial;
import me.TechsCode.UltraCustomizer.base.translations.Phrase;
import me.TechsCode.UltraCustomizer.scriptSystem.objects.*;
import me.TechsCode.UltraCustomizer.scriptSystem.objects.datatypes.DataType;

public class LongToFloat extends Element {
    private static final String PREFIX = "LongToFloat";
    private static final Phrase GET_NAME = Phrase.create(PREFIX + ".name", "Number To Float");
    private static final Phrase GET_DESCRIPTION = Phrase.create(PREFIX + ".desc", "Convert Number To Float");

    private static final Phrase FLOAT_NAME = Phrase.create("CastingElements" + ".float", "Float");
    private static final Phrase LONG_NAME = Phrase.create("CastingElements" + ".long", "Number");

    private static final String INTERNAL_NAME = "number-to-float";

    public LongToFloat(UltraCustomizer plugin) {
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
                new OutcomingVariable("float", FLOAT_NAME.get(), DataType.FLOAT, elementInfo)
        };
    }

    @Override
    public Child[] getConnectors(ElementInfo elementInfo) {
        return new Child[]{new DefaultChild(elementInfo, "next")};
    }

    @Override
    public void run(ElementInfo elementInfo, ScriptInstance scriptInstance) {
        Long argument = (Long) getArguments(elementInfo)[0].getValue(scriptInstance);
        float variable = argument;
        getOutcomingVariables(elementInfo)[0].register(scriptInstance, new DataRequester() {
            @Override
            public Object request() {
                return variable;
            }
        });
        this.getConnectors(elementInfo)[0].run(scriptInstance);
    }
}
