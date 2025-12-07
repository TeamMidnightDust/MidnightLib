package eu.midnightdust.test;

//? if >= 1.21.10 {
import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.InputConstants;
import eu.midnightdust.lib.config.EntryInfo;
import eu.midnightdust.lib.config.MidnightConfigListWidget;
import eu.midnightdust.lib.config.MidnightConfigScreen;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.SpriteIconButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

/*
 Pre-made additional (niche) functionality that is not included in MidnightLib to keep the file size small.
 Feel free to copy the parts you need :)
*/
public class MidnightLibExtras {
    public static class KeybindButton extends Button {
        public static Button focusedButton;

        public static void add(KeyMapping binding, MidnightConfigListWidget list, MidnightConfigScreen screen) {
            KeybindButton editButton = new KeybindButton(screen.width - 185, 0, 150, 20, binding);
            SpriteIconButton resetButton = SpriteIconButton.builder(Component.translatable("controls.reset"), (button -> {
                binding.setKey(binding.getDefaultKey());
                screen.updateList();
            }), true).sprite(Identifier.fromNamespaceAndPath("midnightlib","icon/reset"), 12, 12).size(20, 20).build();
            resetButton.setPosition(screen.width - 205 + 150 + 25, 0);
            editButton.resetButton = resetButton;
            editButton.updateMessage(false);
            EntryInfo info = new EntryInfo(null, screen.modid);

            list.addButton(Lists.newArrayList(editButton, resetButton), Component.translatable(binding.getName()), info);
        }

        private final KeyMapping binding;
        private @Nullable AbstractButton resetButton;
        public KeybindButton(int x, int y, int width, int height, KeyMapping binding) {
            super(x, y, width, height, binding.getTranslatedKeyMessage(), (button) -> {
                ((KeybindButton) button).updateMessage(true);
                focusedButton = button;
            }, (textSupplier) -> binding.isUnbound() ? Component.translatable("narrator.controls.unbound", binding.getName()) : Component.translatable("narrator.controls.bound", binding.getName(), textSupplier.get()));
            this.binding = binding;
            updateMessage(false);
        }
        @Override
        public boolean keyPressed(KeyEvent input) {
            if (focusedButton == this) {
                if (input.key() == GLFW.GLFW_KEY_ESCAPE) {
                    this.binding.setKey(InputConstants.UNKNOWN);
                } else {
                    this.binding.setKey(InputConstants.getKey(input));
                }
                updateMessage(false);

                focusedButton = null;
                return true;
            }
            return super.keyPressed(input);
        }

        public void updateMessage(boolean focused) {
            boolean hasConflicts = false;
            MutableComponent conflictingBindings = Component.empty();
            if (focused) this.setMessage(Component.literal("> ").append(this.binding.getTranslatedKeyMessage().copy().withStyle(ChatFormatting.WHITE, ChatFormatting.UNDERLINE)).append(" <").withStyle(ChatFormatting.YELLOW));
            else {
                this.setMessage(this.binding.getTranslatedKeyMessage());

                if (!this.binding.isUnbound()) {
                    for(KeyMapping keyBinding : Minecraft.getInstance().options.keyMappings) {
                        if (keyBinding != this.binding && this.binding.equals(keyBinding)) {
                            if (hasConflicts) conflictingBindings.append(", ");

                            hasConflicts = true;
                            conflictingBindings.append(Component.translatable(keyBinding.getName()));
                        }
                    }
                }
            }

            if (this.resetButton != null) this.resetButton.active = !this.binding.isDefault();

            if (hasConflicts) {
                this.setMessage(Component.literal("[ ").append(this.getMessage().copy().withStyle(ChatFormatting.WHITE)).append(" ]").withStyle(ChatFormatting.RED));
                this.setTooltip(Tooltip.create(Component.translatable("controls.keybinds.duplicateKeybinds", conflictingBindings)));
            } else {
                this.setTooltip(null);
            }
        }
    }
}
//?}