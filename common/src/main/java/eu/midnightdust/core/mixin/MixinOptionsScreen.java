package eu.midnightdust.core.mixin;

import eu.midnightdust.core.config.MidnightLibConfig;
import eu.midnightdust.core.screen.MidnightConfigOverviewScreen;
import eu.midnightdust.lib.util.PlatformFunctions;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.widget.TextIconButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(OptionsScreen.class)
public class MixinOptionsScreen extends Screen {
    protected MixinOptionsScreen(Text title) {super(title);}

    @Inject(at = @At("HEAD"),method = "init")
    private void midnightlib$init(CallbackInfo ci) {
        if (MidnightLibConfig.config_screen_list.equals(MidnightLibConfig.ConfigButton.TRUE) || (MidnightLibConfig.config_screen_list.equals(MidnightLibConfig.ConfigButton.MODMENU) && !PlatformFunctions.isModLoaded("modmenu"))) {
            TextIconButtonWidget button = TextIconButtonWidget.builder(Text.translatable("midnightlib.overview.title"), (
                            buttonWidget) -> Objects.requireNonNull(client).setScreen(new MidnightConfigOverviewScreen(this)), true)
                    .texture(new Identifier("midnightlib","icon/midnightlib"), 16, 16).dimension(20, 20).build();
            //button.setPosition(this.width / 2 + 158, this.height / 6 - 17);
            button.setPosition(this.width / 2 + 158, this.height / 2 - 105);
            this.addDrawableChild(button);
        }
    }
}