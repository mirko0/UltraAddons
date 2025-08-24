package com.github.mirko0.blocklocation;

import me.TechsCode.UltraCustomizer.UltraCustomizer;
import me.TechsCode.UltraCustomizer.base.item.XMaterial;
import me.TechsCode.UltraCustomizer.scriptSystem.objects.*;
import me.TechsCode.UltraCustomizer.scriptSystem.objects.datatypes.DataType;
import org.bukkit.Location;
import org.bukkit.block.Block;

public class GetBlockLocation extends Element {
    public GetBlockLocation(UltraCustomizer ultraCustomizer) {
        super(ultraCustomizer);
    }

    @Override
    public String getName() {
        return "Get Block Location";
    }

    @Override
    public String getInternalName() {
        return "get-block-location";
    }

    @Override
    public boolean isHidingIfNotCompatible() {
        return false;
    }

    @Override
    public XMaterial getMaterial() {
        return XMaterial.COMPASS;
    }

    @Override
    public String[] getDescription() {
        return new String[]{
                "Obtain the location from block."
        };
    }

    @Override
    public Argument[] getArguments(ElementInfo elementInfo) {
        return new Argument[]{new Argument("block", "Block", DataType.BLOCK, elementInfo)};
    }

    @Override
    public OutcomingVariable[] getOutcomingVariables(ElementInfo elementInfo) {
        return new OutcomingVariable[]{
                new OutcomingVariable("x", "X", DataType.NUMBER, elementInfo),
                new OutcomingVariable("y", "Y", DataType.NUMBER, elementInfo),
                new OutcomingVariable("z", "Z", DataType.NUMBER, elementInfo),
                new OutcomingVariable("xyz", "XYZ", DataType.STRING, elementInfo),
                new OutcomingVariable("xString", "X String", DataType.STRING, elementInfo),
                new OutcomingVariable("yString", "Y String", DataType.STRING, elementInfo),
                new OutcomingVariable("zString", "Z String", DataType.STRING, elementInfo),
                new OutcomingVariable("world", "World Name", DataType.STRING, elementInfo)
        };
    }

    @Override
    public Child[] getConnectors(ElementInfo elementInfo) {
        return new Child[]{
                new DefaultChild(elementInfo, "next")
        };
    }

    @Override
    public void run(ElementInfo elementInfo, ScriptInstance scriptInstance) {
        Block block = (Block) this.getArguments(elementInfo)[0].getValue(scriptInstance);
        Location location = block.getLocation();
        this.getOutcomingVariables(elementInfo)[0].register(scriptInstance, new DataRequester() {
            @Override
            public Object request() {
                return location.getBlockX();
            }
        });
        this.getOutcomingVariables(elementInfo)[1].register(scriptInstance, new DataRequester() {
            @Override
            public Object request() {
                return location.getBlockY();
            }
        });
        this.getOutcomingVariables(elementInfo)[2].register(scriptInstance, new DataRequester() {
            @Override
            public Object request() {
                return location.getBlockZ();
            }
        });
        this.getOutcomingVariables(elementInfo)[3].register(scriptInstance, new DataRequester() {
            @Override
            public Object request() {
                return location.getBlockX() + " " + location.getBlockY() + " " + location.getBlockZ();
            }
        });
        this.getOutcomingVariables(elementInfo)[4].register(scriptInstance, new DataRequester() {
            @Override
            public Object request() {
                return location.getBlockX() + "";
            }
        });
        this.getOutcomingVariables(elementInfo)[5].register(scriptInstance, new DataRequester() {
            @Override
            public Object request() {
                return location.getBlockY() + "";
            }
        });
        this.getOutcomingVariables(elementInfo)[6].register(scriptInstance, new DataRequester() {
            @Override
            public Object request() {
                return location.getBlockZ() + "";
            }
        });
        this.getOutcomingVariables(elementInfo)[7].register(scriptInstance, new DataRequester() {
            @Override
            public Object request() {
                return location.getWorld() == null ? "null" : location.getWorld().getName();
            }
        });

        this.getConnectors(elementInfo)[0].run(scriptInstance);
    }
}
