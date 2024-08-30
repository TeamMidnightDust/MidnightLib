package eu.midnightdust.lib.util;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.Objects;
import java.util.Optional;

import static eu.midnightdust.core.MidnightLib.MOD_ID;


/**
 * This class is used to get the icon of any mod.<br>
 * <code>ImageIcon icon = MidnightIconUnit.of("modid").getIcon();</code>
 */
public class MidnightIconUnit {
    ModContainer modContainer;

    protected MidnightIconUnit(String modid) {
        modContainer = FabricLoader.getInstance().getModContainer(modid).orElse(
                FabricLoader.getInstance().getModContainer(MOD_ID).orElse(null)
        );
    }

    private @NotNull String getIconPath() {
        if (modContainer != null) {
            int [] sizes = {8, 16, 32, 48, 64, 96, 128, 256, 512};
            String iconPath = null;
            for (int size : sizes) {
                Optional<String> icon = modContainer.getMetadata().getIconPath(size);
                if (icon.isPresent()) {
                    iconPath = icon.get();     // "assets/modid/.../xxx.png"
                    break;
                }
            }
            iconPath = "/" + iconPath;         // "/assets/modid/.../xxx.png"
            return iconPath;
        } else {
            return "/assets/" + MOD_ID + "/icon.png";
        }
    }

    public ImageIcon getIcon() {
        return new ImageIcon(Objects.requireNonNullElse(
                MidnightIconUnit.class.getResource(this.getIconPath()),
                MidnightIconUnit.class.getResource("/assets/" + MOD_ID + "/icon.png")
        ));
    }

    @Contract("_ -> new")
    public static @NotNull MidnightIconUnit of(String modid) {
        return new MidnightIconUnit(modid);
    }
}
