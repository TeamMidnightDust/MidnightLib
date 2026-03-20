package eu.midnightdust.lib.config;

import com.google.common.collect.Lists;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.MultiLineTextWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.ConfirmLinkScreen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
//? if >= 1.21.9 {
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.world.item.ItemStack;
//?}
//? if >= 26.1-pre.1 {
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
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
    public void extractContent(GuiGraphicsExtractor context, int mouseX, int mouseY, boolean hovered, float tickDelta) {
    int y = this.getY();
    //?} else {
    /*public void extractRenderState(GuiGraphicsExtractor context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
    *///?}
        buttons.forEach(b -> {
            b.setY(y);
            b.extractRenderState(context, mouseX, mouseY, tickDelta);
        });
        if (title != null) {
            title.setY(y + 5);
            title.extractRenderState(context, mouseX, mouseY, tickDelta);
        }
        if (info.entry != null && !this.buttons.isEmpty() && this.info.entry.idMode() != -1) {
            var id = Identifier.tryParse(this.info.tempValue);
            var item = this.info.entry.idMode() == 0 ? BuiltInRegistries.ITEM./*? if >= 1.21.4 {*/ getValue /*?} else {*/ /*get *//*?}*/(id) : BuiltInRegistries.BLOCK./*? if >= 1.21.4 {*/ getValue /*?} else {*/ /*get *//*?}*/(id).asItem();
            var stack = /*? if >= 26.1-pre.1 {*/ new ItemStack(Holder.direct(item, DataComponentMap.builder().set(DataComponents.ITEM_MODEL, Identifier.tryParse(this.info.tempValue)).build())) /*?} else {*/ /*item.getDefaultInstance()*/ /*?}*/;
            context./*? if >= 26.1-pre.1 {*/ fakeItem /*?} else {*/ /*renderItem *//*?}*/(stack, this.buttons.get(0).getX() + this.buttons.get(0).getWidth() - 18, y + 2);
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