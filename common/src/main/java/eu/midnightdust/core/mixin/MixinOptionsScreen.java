package eu.midnightdust.core.mixin;

import eu.midnightdust.core.screen.MidnightConfigOverviewScreen;
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

import static eu.midnightdust.core.MidnightLib.MOD_ID;
import static eu.midnightdust.core.config.MidnightLibConfig.shouldShowButton;

@Mixin(OptionsScreen.class)
public abstract class MixinOptionsScreen extends Screen {
    @Shadow @Final private ThreePartsLayoutWidget layout;
    @Unique TextIconButtonWidget midnightlib$button = TextIconButtonWidget.builder(Text.translatable("midnightlib.overview.title"), (
            buttonWidget) -> Objects.requireNonNull(client).setScreen(new MidnightConfigOverviewScreen(this)), true)
            .texture(Identifier.of(MOD_ID,"icon/"+MOD_ID), 16, 16).dimension(20, 20).build();

    private MixinOptionsScreen(Text title) {super(title);}

    @Inject(at = @At("HEAD"), method = "init")
    public void midnightlib$onInit(CallbackInfo ci) {
        if (shouldShowButton()) {
            this.midnightlib$setButtonPos();
            this.addDrawableChild(midnightlib$button);
        }
    }

    @Inject(at = @At("TAIL"), method = "refreshWidgetPositions")
    public void midnightlib$onResize(CallbackInfo ci) {
        if (shouldShowButton()) this.midnightlib$setButtonPos();
    }

    @Unique
    public void midnightlib$setButtonPos() {
        midnightlib$button.setPosition(layout.getWidth() / 2  + 158, layout.getY() + layout.getFooterHeight() - 4);
    }
}