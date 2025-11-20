package eu.midnightdust.lib.config;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.MultiLineTextWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.ConfirmLinkScreen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
//? if >= 1.21.9 {
import net.minecraft.client.input.MouseButtonEvent;
//?}

public class ButtonEntry extends ContainerObjectSelectionList.Entry<ButtonEntry> {
    private static final Font textRenderer = Minecraft.getInstance().font;
    public final Component text;
    public final List<AbstractWidget> buttons;
    public final EntryInfo info;
    public boolean centered = false;
    public MultiLineTextWidget title;

    public ButtonEntry(List<AbstractWidget> buttons, Component text, EntryInfo info) {
        this.buttons = buttons;
        this.text = text;
        this.info = info;
        if (info != null && info.comment != null)
            this.centered = info.comment.centered();
        int scaledWidth = Minecraft.getInstance().getWindow().getGuiScaledWidth();

        if (text != null && (!text.getString().contains("spacer") || !buttons.isEmpty())) {
            title = new MultiLineTextWidget(12, 0, text.copy(), textRenderer).setCentered(centered);
            if (info != null)
                title.setTooltip(info.getTooltip(false));
            title.setMaxWidth(!buttons.isEmpty() ? buttons.get(buttons.size() > 2 ? buttons.size() - 1 : 0).getX() - 16 : scaledWidth - 24);
            if (centered) title.setX(scaledWidth / 2 - (title.getWidth() / 2));
        }
    }

    @Override
    //? if >= 1.21.9 {
    public void renderContent(GuiGraphics context, int mouseX, int mouseY, boolean hovered, float tickDelta) {
    int y = this.getY();
    //?} else {
    /*public void render(GuiGraphics context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
    *///?}
        buttons.forEach(b -> {
            b.setY(y);
            b.render(context, mouseX, mouseY, tickDelta);
        });
        if (title != null) {
            title.setY(y + 5);
            title.render(context, mouseX, mouseY, tickDelta);

            if (info.entry != null && !this.buttons.isEmpty()) {
                Optional.ofNullable(this.buttons.get(0)).ifPresent(widget -> {
                    int idMode = this.info.entry.idMode();
                    if (idMode != -1) context.renderItem(idMode == 0 ?
                                BuiltInRegistries.ITEM./*? if >= 1.21.4 {*/ getValue /*?} else {*/ /*get*/ /*?}*/(ResourceLocation.tryParse(this.info.tempValue)).getDefaultInstance()
                                : BuiltInRegistries.BLOCK./*? if >= 1.21.4 {*/ getValue /*?} else {*/ /*get*/ /*?}*/(ResourceLocation.tryParse(this.info.tempValue)).asItem().getDefaultInstance(),
                            widget.getX() + widget.getWidth() - 18, y + 2);
                });
            }
        }
    }

    @Override
    //? if >= 1.21.9 {
    public boolean mouseClicked(MouseButtonEvent click, boolean doubled) {
    //?} else {
    /*public boolean mouseClicked(double d, double e, int i) {
    *///?}
        if (this.info != null && this.info.comment != null && !this.info.comment.url().isBlank())
            //? if >= 1.21 {
             ConfirmLinkScreen.confirmLinkNow(Minecraft.getInstance().screen, this.info.comment.url(), true);
            //?} else {
            /*ConfirmLinkScreen.confirmLinkNow(this.info.comment.url(), Minecraft.getInstance().screen, true);
            *///?}
        //? if >= 1.21.9 {
        return super.mouseClicked(click, doubled);
        //?} else {
        /*return super.mouseClicked(d, e, i);
        *///?}
    }

    public List<? extends GuiEventListener> children() {
        return Lists.newArrayList(buttons);
    }

    public List<? extends NarratableEntry> narratables() {
        return Lists.newArrayList(buttons);
    }
}