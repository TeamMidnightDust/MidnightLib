package eu.midnightdust.core.mixin;

import eu.midnightdust.core.config.MidnightLibConfig;
import eu.midnightdust.core.screen.MidnightConfigOverviewScreen;
import eu.midnightdust.lib.util.screen.TexturedOverlayButtonWidget;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(OptionsScreen.class)
public class MixinOptionsScreen extends Screen {
    private static final Identifier MIDNIGHTLIB_ICON_TEXTURE = new Identifier("midnightlib","textures/gui/midnightlib_button.png");
    protected MixinOptionsScreen(Text title) {
        super(title);
    }

    @Inject(at = @At("HEAD"),method = "init")
    private void midnightlib$init(CallbackInfo ci) {
        if (MidnightLibConfig.config_screen_list.equals(MidnightLibConfig.ConfigButton.TRUE) || (MidnightLibConfig.config_screen_list.equals(MidnightLibConfig.ConfigButton.MODMENU) && !FabricLoader.getInstance().isModLoaded("modmenu")))
            this.addDrawableChild(new TexturedOverlayButtonWidget(this.width / 2 + 158, this.height / 6 - 12, 20, 20, 0, 0, 20, MIDNIGHTLIB_ICON_TEXTURE, 32, 64, (buttonWidget) -> Objects.requireNonNull(client).setScreen(new MidnightConfigOverviewScreen(this)), Text.translatable("midnightlib.overview.title")));
    }
}
