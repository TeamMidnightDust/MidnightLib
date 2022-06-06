package eu.midnightdust.lib.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import eu.midnightdust.core.config.MidnightLibConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AutoModMenu implements ModMenuApi {
    protected static List<String> hiddenMods = List.of();

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> MidnightLibConfig.getScreen(parent,"midnightlib");
    }

    @Override
    public Map<String, ConfigScreenFactory<?>> getProvidedConfigScreenFactories() {
        HashMap<String, ConfigScreenFactory<?>> map = new HashMap<>();
        MidnightConfig.configClass.forEach((modid, cClass) -> {
                    if (!hiddenMods.contains(modid))
                        map.put(modid, parent -> MidnightConfig.getScreen(parent, modid));
                }
        );
        return map;
    }
    public static void hideFromModMenu(String hiddenMod) {
        hiddenMods.add(hiddenMod);
    }
}