package com.github.mirko0.blocklocation;

import me.TechsCode.UltraCustomizer.UltraCustomizer;
import me.TechsCode.UltraCustomizer.base.item.XMaterial;
import me.TechsCode.UltraCustomizer.scriptSystem.objects.*;
import me.TechsCode.UltraCustomizer.scriptSystem.objects.datatypes.DataType;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class GetTargetedBlock extends Element {
    public GetTargetedBlock(UltraCustomizer ultraCustomizer) {
        super(ultraCustomizer);
    }

    @Override
    public String getName() {
        return "Get Targeted Block";
    }

    @Override
    public String getInternalName() {
        return "get-targeted-block-mirko0";
    }

    @Override
    public boolean isHidingIfNotCompatible() {
        return false;
    }

    @Override
    public XMaterial getMaterial() {
        return XMaterial.BOW;
    }

    @Override
    public String[] getDescription() {
        return new String[]{
                "Get the targeted block."
        };
    }

    @Override
    public Argument[] getArguments(ElementInfo elementInfo) {
        return new Argument[]{
                new Argument("player", "Player", DataType.PLAYER, elementInfo)
        };
    }

    @Override
    public OutcomingVariable[] getOutcomingVariables(ElementInfo elementInfo) {
        return new OutcomingVariable[]{
                new OutcomingVariable("block", "Block", DataType.BLOCK, elementInfo)
        };
    }

    @Override
    public Child[] getConnectors(ElementInfo elementInfo) {
        return new Child[]{
                new Child(elementInfo, "hasBlock") {
                    @Override
                    public String getName() {
                        return "Block Targeted";
                    }

                    @Override
                    public String[] getDescription() {
                        return new String[]{"Will be executed if player.", "is targeting a block."};
                    }

                    @Override
                    public XMaterial getIcon() {
                        return XMaterial.WHITE_STAINED_GLASS_PANE;
                    }
                },
                new Child(elementInfo, "noBlock") {
                    @Override
                    public String getName() {
                        return "No Block Targeted";
                    }

                    @Override
                    public String[] getDescription() {
                        return new String[]{"Will be executed when player", "is not targeting a block."};
                    }

                    @Override
                    public XMaterial getIcon() {
                        return XMaterial.BLACK_STAINED_GLASS_PANE;
                    }
                }};
    }

    @Override
    public void run(ElementInfo elementInfo, ScriptInstance scriptInstance) {
        Player player = (Player) this.getArguments(elementInfo)[0].getValue(scriptInstance);
        Block targetBlock = player.getTargetBlockExact(21);

        if (targetBlock != null) {
            this.getOutcomingVariables(elementInfo)[0].register(scriptInstance, new DataRequester() {
                @Override
                public Object request() {
                    return targetBlock;
                }
            });
            this.getConnectors(elementInfo)[0].run(scriptInstance);
        } else {
            this.getOutcomingVariables(elementInfo)[0].register(scriptInstance, new DataRequester() {
                @Override
                public Object request() {
                    return player.getEyeLocation().getBlock();
                }
            });
            this.getConnectors(elementInfo)[1].run(scriptInstance);
        }
    }
}
