package com.github.mirko0.coinsengine.datatypes;

import com.github.mirko0.coinsengine.datatypes.specifications.CurrencySpecification;
import me.TechsCode.UltraCustomizer.UltraCustomizer;
import me.TechsCode.UltraCustomizer.scriptSystem.objects.datatypes.DataType;
import me.TechsCode.UltraCustomizer.scriptSystem.objects.datatypes.DataTypeSpecification;

public class QDataTypes {
    private static final String PREFIX = "COINSENGINE_";

    private static final String CURRENCY_DATATYPE_NAME = PREFIX + "CURRENCY";

    public static DataTypeSpecification CURRENCY_SPECIFICATION;

    public static void registerDataTypes() {
        registerDataType(CURRENCY_DATATYPE_NAME, new CurrencySpecification());

        CURRENCY_SPECIFICATION = DataType.getCustomDataType(CURRENCY_DATATYPE_NAME);
        UltraCustomizer.getInstance().log("[CoinsEngine Addon] - Custom data types are registered.");
    }

    private static void registerDataType(String name, DataTypeSpecification specification) {
        DataType.registerCustomDataType(name, specification);
    }


}
