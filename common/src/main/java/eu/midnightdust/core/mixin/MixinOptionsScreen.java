package eu.midnightdust.core.mixin;

import eu.midnightdust.core.screen.MidnightConfigOverviewScreen;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;
import net.minecraft.client.gui.components.SpriteIconButton;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import static eu.midnightdust.core.MidnightLib.MOD_ID;
import static eu.midnightdust.core.config.MidnightLibConfig.shouldShowButton;

@Mixin(OptionsScreen.class)
public abstract class MixinOptionsScreen extends Screen {
    @Shadow @Final private HeaderAndFooterLayout layout;
    @Unique SpriteIconButton midnightlib$button = SpriteIconButton.builder(Component.translatable("midnightlib.overview.title"), (
            buttonWidget) -> Objects.requireNonNull(minecraft).setScreen(new MidnightConfigOverviewScreen(this)), true)
            .sprite(ResourceLocation.fromNamespaceAndPath(MOD_ID,"icon/"+MOD_ID), 16, 16).size(20, 20).build();

    private MixinOptionsScreen(Component title) {super(title);}

    @Inject(at = @At("HEAD"), method = "init")
    public void midnightlib$onInit(CallbackInfo ci) {
        if (shouldShowButton()) {
            this.midnightlib$setButtonPos();
            this.addRenderableWidget(midnightlib$button);
        }
    }

    @Inject(at = @At("TAIL"), method = "repositionElements")
    public void midnightlib$onResize(CallbackInfo ci) {
        if (shouldShowButton()) this.midnightlib$setButtonPos();
    }

    @Unique
    public void midnightlib$setButtonPos() {
        midnightlib$button.setPosition(layout.getWidth() / 2  + 158, layout.getY() + layout.getFooterHeight() - 4);
    }
}