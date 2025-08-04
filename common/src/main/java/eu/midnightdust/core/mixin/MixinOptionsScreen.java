package eu.midnightdust.core.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.SugarBridge;
import eu.midnightdust.core.config.MidnightLibConfig;
import eu.midnightdust.core.screen.MidnightConfigOverviewScreen;
import eu.midnightdust.lib.util.PlatformFunctions;
import eu.midnightdust.lib.util.screen.TexturedOverlayButtonWidget;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Objects;

import static eu.midnightdust.core.config.MidnightLibConfig.shouldShowButton;

@Mixin(OptionsScreen.class)
public class MixinOptionsScreen extends Screen {

    @Unique private static final Identifier MIDNIGHTLIB_ICON_TEXTURE = Identifier.of("midnightlib","icon/midnightlib.png");

    @Unique
    TexturedOverlayButtonWidget midnightlib$button = new TexturedOverlayButtonWidget(
            this.width / 2 + 158,
            this.height / 6 - 12,
            20,
            20,
            0, 0, 20,
            MIDNIGHTLIB_ICON_TEXTURE,
            32, 64,
            (buttonWidget) ->
                    Objects.requireNonNull(client).setScreen(new MidnightConfigOverviewScreen(this)),
            Text.translatable("midnightlib.overview.title"));

    protected MixinOptionsScreen(Text title) {
        super(title);
    }

    @Inject(at = @At("HEAD"), method = "init")
    private void midnightlib$init(CallbackInfo ci) {
        if (shouldShowButton()){
            midnightlib$button.setPosition(this.width / 2 + 158, this.height / 6 - 12);
            this.addDrawableChild(midnightlib$button);
        }
    }

}
