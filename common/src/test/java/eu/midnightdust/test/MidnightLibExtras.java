package eu.midnightdust.test;

import com.google.common.collect.Lists;
import eu.midnightdust.lib.config.EntryInfo;
import eu.midnightdust.lib.config.MidnightConfigListWidget;
import eu.midnightdust.lib.config.MidnightConfigScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.TextIconButtonWidget;
import net.minecraft.client.input.KeyInput;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

/*
 Pre-made additional (niche) functionality that is not included in MidnightLib to keep the file size small.
 Feel free to copy the parts you need :)
*/
public class MidnightLibExtras {
    public static class KeybindButton extends ButtonWidget {
        public static ButtonWidget focusedButton;

        public static void add(KeyBinding binding, MidnightConfigListWidget list, MidnightConfigScreen screen) {
            KeybindButton editButton = new KeybindButton(screen.width - 185, 0, 150, 20, binding);
            TextIconButtonWidget resetButton = TextIconButtonWidget.builder(Text.translatable("controls.reset"), (button -> {
                binding.setBoundKey(binding.getDefaultKey());
                screen.updateList();
            }), true).texture(Identifier.of("midnightlib","icon/reset"), 12, 12).dimension(20, 20).build();
            resetButton.setPosition(screen.width - 205 + 150 + 25, 0);
            editButton.resetButton = resetButton;
            editButton.updateMessage(false);
            EntryInfo info = new EntryInfo(null, screen.modid);

            list.addButton(Lists.newArrayList(editButton, resetButton), Text.translatable(binding.getId()), info);
        }

        private final KeyBinding binding;
        private @Nullable ClickableWidget resetButton;
        public KeybindButton(int x, int y, int width, int height, KeyBinding binding) {
            super(x, y, width, height, binding.getBoundKeyLocalizedText(), (button) -> {
                ((KeybindButton) button).updateMessage(true);
                focusedButton = button;
            }, (textSupplier) -> binding.isUnbound() ? Text.translatable("narrator.controls.unbound", binding.getId()) : Text.translatable("narrator.controls.bound", binding.getId(), textSupplier.get()));
            this.binding = binding;
            updateMessage(false);
        }
        @Override
        public boolean keyPressed(KeyInput input) {
            if (focusedButton == this) {
                if (input.key() == GLFW.GLFW_KEY_ESCAPE) {
                    this.binding.setBoundKey(InputUtil.UNKNOWN_KEY);
                } else {
                    this.binding.setBoundKey(InputUtil.fromKeyCode(input));
                }
                updateMessage(false);

                focusedButton = null;
                return true;
            }
            return super.keyPressed(input);
        }

        public void updateMessage(boolean focused) {
            boolean hasConflicts = false;
            MutableText conflictingBindings = Text.empty();
            if (focused) this.setMessage(Text.literal("> ").append(this.binding.getBoundKeyLocalizedText().copy().formatted(Formatting.WHITE, Formatting.UNDERLINE)).append(" <").formatted(Formatting.YELLOW));
            else {
                this.setMessage(this.binding.getBoundKeyLocalizedText());

                if (!this.binding.isUnbound()) {
                    for(KeyBinding keyBinding : MinecraftClient.getInstance().options.allKeys) {
                        if (keyBinding != this.binding && this.binding.equals(keyBinding)) {
                            if (hasConflicts) conflictingBindings.append(", ");

                            hasConflicts = true;
                            conflictingBindings.append(Text.translatable(keyBinding.getId()));
                        }
                    }
                }
            }

            if (this.resetButton != null) this.resetButton.active = !this.binding.isDefault();

            if (hasConflicts) {
                this.setMessage(Text.literal("[ ").append(this.getMessage().copy().formatted(Formatting.WHITE)).append(" ]").formatted(Formatting.RED));
                this.setTooltip(Tooltip.of(Text.translatable("controls.keybinds.duplicateKeybinds", conflictingBindings)));
            } else {
                this.setTooltip(null);
            }
        }
    }
}
