package eu.midnightdust.core.mixin;

import eu.midnightdust.core.config.MidnightLibConfig;
import eu.midnightdust.core.screen.MidnightConfigOverviewScreen;
import eu.midnightdust.lib.util.PlatformFunctions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.widget.TextIconButtonWidget;
import net.minecraft.client.gui.widget.ThreePartsLayoutWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(OptionsScreen.class)
public class MixinOptionsScreen extends Screen {
    @Shadow @Final private ThreePartsLayoutWidget layout;
    @Unique TextIconButtonWidget button = TextIconButtonWidget.builder(Text.translatable("midnightlib.overview.title"), (
            buttonWidget) -> Objects.requireNonNull(client).setScreen(new MidnightConfigOverviewScreen(this)), true)
            .texture(Identifier.of("midnightlib","icon/midnightlib"), 16, 16).dimension(20, 20).build();
    @Unique boolean shouldShowButton = MidnightLibConfig.config_screen_list.equals(MidnightLibConfig.ConfigButton.TRUE) || (MidnightLibConfig.config_screen_list.equals(MidnightLibConfig.ConfigButton.MODMENU) && !PlatformFunctions.isModLoaded("modmenu") && PlatformFunctions.getPlatformName() != "neoforge");

    protected MixinOptionsScreen(Text title) {super(title);}
    @Inject(at = @At("HEAD"), method = "init")
    public void midnightlib$onInit(CallbackInfo ci) {
        if (shouldShowButton) {
            this.midnightlib$setupButton();
            this.addDrawableChild(button);
        }
    }

    @Override
    public void resize(MinecraftClient client, int width, int height) {
        super.resize(client, width, height);
        if (shouldShowButton) this.midnightlib$setupButton();
    }
    @Unique
    public void midnightlib$setupButton() {
        button.setPosition(layout.getWidth() / 2  + 158, layout.getY() + layout.getFooterHeight() - 4);
    }
}