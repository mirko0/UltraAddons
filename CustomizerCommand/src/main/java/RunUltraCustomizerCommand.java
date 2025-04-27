import me.TechsCode.UltraCustomizer.Folder;
import me.TechsCode.UltraCustomizer.UltraCustomizer;
import me.TechsCode.UltraCustomizer.base.item.XMaterial;
import me.TechsCode.UltraCustomizer.base.translations.Phrase;
import me.TechsCode.UltraCustomizer.scriptSystem.elements.constructors.Command;
import me.TechsCode.UltraCustomizer.scriptSystem.objects.*;
import me.TechsCode.UltraCustomizer.scriptSystem.objects.datatypes.DataType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.ArrayList;
import java.util.List;

public class RunUltraCustomizerCommand extends Element {
    public RunUltraCustomizerCommand(UltraCustomizer plugin) {
        super(plugin);
    }

    private static final Phrase ELEMENT_NAME = Phrase.create("CustomizerCommand.name", "Customizer Command");

    @Override
    public String getName() {
        return ELEMENT_NAME.get();
    }

    @Override
    public String getInternalName() {
        return "customizer-command";
    }

    @Override
    public boolean isHidingIfNotCompatible() {
        return false;
    }

    @Override
    public XMaterial getMaterial() {
        return XMaterial.COMMAND_BLOCK;
    }

    private static final Phrase GET_DESCRIPTION_1 = Phrase.create("PlayerCommand.desc1", "Will perform a specific");

    private static final Phrase GET_DESCRIPTION_2 = Phrase.create("PlayerCommand.desc2", "command for a specific player");
    private static final Phrase GET_DESCRIPTION_3 = Phrase.create("CustomizerCommand.desc3", "Works for customizer commands.");

    @Override
    public String[] getDescription() {
        return new String[]{
                GET_DESCRIPTION_1.get(),
                GET_DESCRIPTION_2.get(),
                GET_DESCRIPTION_3.get()
        };
    }

    private static final Phrase GET_ARGUMENTS_PLAYER = Phrase.create("PlayerCommand.player", "Player");
    private static final Phrase GET_ARGUMENTS_COMMAND = Phrase.create("PlayerCommand.command", "Command");

    public Argument[] getArguments(ElementInfo elementInfo) {
        return new Argument[]{new Argument("player", GET_ARGUMENTS_PLAYER
                .get(), DataType.PLAYER, elementInfo), new Argument("command", GET_ARGUMENTS_COMMAND
                .get(), DataType.STRING, elementInfo)};
    }

    public OutcomingVariable[] getOutcomingVariables(ElementInfo elementInfo) {
        return new OutcomingVariable[0];
    }

    public Child[] getConnectors(ElementInfo elementInfo) {
        return new Child[]{new DefaultChild(elementInfo, "next")};
    }

    public void run(ElementInfo elementInfo, ScriptInstance instance) {
        Player player = (Player) getArguments(elementInfo)[0].getValue(instance);
        String fullCommand = (String) getArguments(elementInfo)[1].getValue(instance);
        String[] split = fullCommand.split(" ");
        String cmdName = split[0];
        if (fullCommand.startsWith("/")) fullCommand = fullCommand.replace("/", "");
        List<String> commands = new ArrayList<>();
        for (Folder folder : UltraCustomizer.getInstance().getFolders()) {
            for (Script script : folder.getScripts()) {
                if (script.getFirstElement().getElement() instanceof Command) {
                    Command command = (Command) script.getFirstElement().getElement();
                    String name = command.getCommandName(script.getFirstElement());
                    if (name.contains(" "))
                        continue;
                    commands.add(name);
                }
            }
        }
        boolean isUcCommand = commands.contains(cmdName) || commands.contains("/" + cmdName) || commands.contains("//" + cmdName);
        if (isUcCommand) {
            Bukkit.getPluginManager().callEvent(new PlayerCommandPreprocessEvent(player, "/" + fullCommand));
            this.getConnectors(elementInfo)[0].run(instance);
        }
    }
}
