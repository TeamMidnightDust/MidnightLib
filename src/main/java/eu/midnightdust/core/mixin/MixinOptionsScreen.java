package eu.midnightdust.core.mixin;

import eu.midnightdust.core.screen.MidnightConfigOverviewScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

import net.minecraft.client.gui.screens.Screen;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

//? if >= 1.21 {
 import org.spongepowered.asm.mixin.Final;
 import org.spongepowered.asm.mixin.Shadow;
 import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
 import net.minecraft.client.gui.components.SpriteIconButton;
 import net.minecraft.client.gui.screens.options.OptionsScreen;
 import static eu.midnightdust.core.MidnightLib.MOD_ID;
//?} else {
/*import net.minecraft.client.gui.components.TextAndImageButton;
import net.minecraft.client.gui.screens.OptionsScreen;
*///?}

import static eu.midnightdust.core.config.MidnightLibConfig.shouldShowButton;

@Mixin(OptionsScreen.class)
public abstract class MixinOptionsScreen extends Screen {
    private MixinOptionsScreen(Component title) {super(title);}
    //? if >= 1.20.4 {
    @Shadow @Final private HeaderAndFooterLayout layout;
    @Unique SpriteIconButton midnightlib$button = SpriteIconButton.builder(Component.translatable("midnightlib.overview.title"), (
            buttonWidget) -> minecraft.gui.setScreen(new MidnightConfigOverviewScreen(this)), true)
            .sprite(Identifier.fromNamespaceAndPath(MOD_ID,"icon/"+MOD_ID), 16, 16).size(20, 20).build();

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
    //?} else {
    /*@Unique TextAndImageButton midnightlib$button = TextAndImageButton.builder(Component.translatable("midnightlib.overview.title"), new Identifier("midnightlib", "icon/midnightlib.png"),
            button -> minecraft.gui.setScreen(new MidnightConfigOverviewScreen(this))).textureSize(16, 16).usedTextureSize(16, 16).offset(0, 2).build();

    @Inject(at = @At("HEAD"), method = "init")
    private void midnightlib$init(CallbackInfo ci) {
        if (shouldShowButton()){
            midnightlib$button.setWidth(20);
            midnightlib$button.setPosition(this.width / 2 + 158, this.height / 6 - 12);
            this.addRenderableWidget(midnightlib$button);
        }
    }
    *///?}
}