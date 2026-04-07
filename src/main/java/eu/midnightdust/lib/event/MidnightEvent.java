package eu.midnightdust.lib.event;


import eu.midnightdust.lib.config.MidnightConfigScreen;
//? if neoforge {
/*import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.event.IModBusEvent;
*///? } else if forge {
/*import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.IModBusEvent;
import net.minecraftforge.fml.javafmlmod.FMLModContainer;
*///? } else {
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
//? }

public abstract class MidnightEvent /*? if neoforge || forge {*/ /*extends Event implements IModBusEvent *//*?}*/ {


    //? if neoforge {
    /*private static IEventBus getEventBus() throws java.util.NoSuchElementException {
        ModContainer container = ModList.get().getModContainerById(eu.midnightdust.core.MidnightLib.MOD_ID).orElse(null);
        if (container == null || container.getEventBus() == null) {
            throw new java.util.NoSuchElementException(String.format("Mod %s container or event bus is null", eu.midnightdust.core.MidnightLib.MOD_ID));
        }
        return container.getEventBus();
    }
    *///? } else if forge {
    /*private static IEventBus getEventBus() {
        ModContainer container = ModList.get().getModContainerById(eu.midnightdust.core.MidnightLib.MOD_ID).orElse(null);
        if (container instanceof FMLModContainer fc) {
            return fc.getEventBus();
        } else {
            throw new IllegalStateException(String.format("Mod %s container is not FMLModContainer", eu.midnightdust.core.MidnightLib.MOD_ID));
        }
    }
    *///? }

    //? if neoforge || forge {
    
    /*public static class ConfigRegistered extends MidnightEvent {
        public final String modid;
        public ConfigRegistered(String modid) {
            this.modid = modid;
        }
    }

    public static class ConfigScreenOpened extends MidnightEvent {
        public final String modid;
        public final MidnightConfigScreen screen;
        public ConfigScreenOpened(String modid, MidnightConfigScreen screen) {
            this.modid = modid;
            this.screen = screen;
        }
    }

    public static class ConfigScreenClosed extends MidnightEvent {
        public final String modid;
        public ConfigScreenClosed(String modid) {
            this.modid = modid;
        }
    }

    public static class SetValue extends MidnightEvent {
        public final String modid;
        public final String fieldName;
        public final Object oldValue;
        public final Object newValue;
        public SetValue(String modid, String fieldName, Object oldValue, Object newValue) {
            this.modid = modid;
            this.fieldName = fieldName;
            this.oldValue = oldValue;
            this.newValue = newValue;
        }
    }

    public static class WriteConfig extends MidnightEvent {
        public final String modid;
        public WriteConfig(String modid) {
            this.modid = modid;
        }
    }
    *///? } else {
    public static final Event<ConfigRegistered> CONFIG_REGISTERED = EventFactory.createArrayBacked(ConfigRegistered.class, callbacks -> (modid) -> {
		for (ConfigRegistered callback : callbacks) {
            callback.call(modid);
        }
    });

    public static final Event<ConfigScreenOpened> CONFIG_SCREEN_OPENED = EventFactory.createArrayBacked(ConfigScreenOpened.class, callbacks -> (modid, screen) -> {
		for (ConfigScreenOpened callback : callbacks) {
            callback.call(modid, screen);
        }
    });

    public static final Event<ConfigScreenClosed> CONFIG_SCREEN_CLOSED = EventFactory.createArrayBacked(ConfigScreenClosed.class, callbacks -> (modid) -> {
		for (ConfigScreenClosed callback : callbacks) {
            callback.call(modid);
        }
    });

    public static final Event<SetValue> SET_VALUE = EventFactory.createArrayBacked(SetValue.class, callbacks -> (modid, fieldName, oldValue, newValue) -> {
		for (SetValue callback : callbacks) {
            callback.call(modid, fieldName, oldValue, newValue);
        }
    });

    public static final Event<WriteConfig> WRITE_CONFIG = EventFactory.createArrayBacked(WriteConfig.class, callbacks -> (modid) -> {
		for (WriteConfig callback : callbacks) {
            callback.call(modid);
        }
    });

    @FunctionalInterface
    public interface ConfigRegistered {
        void call(String modid);
    }

    @FunctionalInterface
    public interface ConfigScreenOpened {
        void call(String modid, MidnightConfigScreen screen);
    }

    @FunctionalInterface
    public interface ConfigScreenClosed {
        void call(String modid);
    }

    @FunctionalInterface
    public interface SetValue {
        void call(String modid, String fieldName, Object oldValue, Object newValue);
    }
    
    @FunctionalInterface
    public interface WriteConfig {
        void call(String modid);
    }
    //? }


    public static void trigger(MidnightEventType type, Object... args) {
        switch (type) {
            case CONFIG_REGISTERED -> {
                if (args.length == type.argNum && args[0] instanceof String modid) {
                    //? if neoforge || forge {
                    /*getEventBus().post(new ConfigRegistered(modid));
                     *///? } else {
                    CONFIG_REGISTERED.invoker().call(modid);
                    //? }
                } else throw new IllegalArgumentException(String.format("Invalid arguments for CONFIG_REGISTERED: %s", args));
            }
            case CONFIG_SCREEN_OPENED -> {
                if (args.length == type.argNum && args[0] instanceof String modid && args[1] instanceof MidnightConfigScreen screen) {
                    //? if neoforge || forge {
                    /*getEventBus().post(new ConfigScreenOpened(modid, screen));
                     *///? } else {
                    CONFIG_SCREEN_OPENED.invoker().call(modid, screen);
                    //? }
                } else throw new IllegalArgumentException(String.format("Invalid arguments for CONFIG_SCREEN_OPENED: %s", args));
            }
            case CONFIG_SCREEN_CLOSED -> {
                if (args.length == type.argNum && args[0] instanceof String modid) {
                    //? if neoforge || forge {
                    /*getEventBus().post(new ConfigScreenClosed(modid));
                     *///? } else {
                    CONFIG_SCREEN_CLOSED.invoker().call(modid);
                    //? }
                } else throw new IllegalArgumentException(String.format("Invalid arguments for CONFIG_SCREEN_CLOSED: %s", args));
            }
            case SET_VALUE -> {
                if (args.length == type.argNum && args[0] instanceof String modid && args[1] instanceof String fieldName) {
                    //? if neoforge || forge {
                    /*getEventBus().post(new SetValue(modid, fieldName, args[2], args[3]));
                     *///? } else {
                    SET_VALUE.invoker().call(modid, fieldName, args[2], args[3]);
                    //? }
                } else throw new IllegalArgumentException(String.format("Invalid arguments for SET_VALUE: %s", args));
            }
            case WRITE_CONFIG -> {
                if (args.length == 1 && args[0] instanceof String modid) {
                    //? if neoforge || forge {
                    /*getEventBus().post(new WriteConfig(modid));
                     *///? } else {
                    WRITE_CONFIG.invoker().call(modid);
                    //? }
                } else throw new IllegalArgumentException(String.format("Invalid arguments for WRITE_CONFIG: %s", args));
            }
        }
    }
}
