package eu.midnightdust.lib.event;

public enum MidnightEventType {

    /**
     * Config registered event.
     * <p>
     * {@link String modid} The modid of the config.
     * </p>
     */
    CONFIG_REGISTERED(1),

    /**
     * Config screen opened event.
     * <p>
     * {@link String modid} The modid of the config.<br>
     * {@link eu.midnightdust.lib.config.MidnightConfigScreen screen} The config screen that opened.
     * </p>
     */
    CONFIG_SCREEN_OPENED(2),

    /**
     * Config screen closed event.
     * <p>
     * {@link String modid} The modid of the config.
     * </p>
     */
    CONFIG_SCREEN_CLOSED(1),

    /**
     * Set value event.
     * <p>
     * {@link String modid} The modid of the config.<br>
     * {@link String fieldName} The name of the field that was set.<br>
     * {@link Object oldValue} The old value of the field.<br>
     * {@link Object newValue} The new value of the field.
     * </p>
     */
    SET_VALUE(4),

    /**
     * Write config event.
     * <p>
     * {@link String modid} The modid of the config.
     * </p>
     */
    WRITE_CONFIG(1);

    public final int argNum;
    MidnightEventType(int argNum) {
        this.argNum = argNum;
    }
}
