package eu.midnightdust.lib.config;

/** MidnightConfig documentation & examples:
 * Thanks for choosing MidnightConfig - the fancy, tiny and lightweight config library.
 * If you want to use the lib in your mod, here are some examples and hints:
 * Every option in a MidnightConfig class has to be public and static, so we can access it from other classes.
 * The config class also has to extend MidnightConfig*/

public class MidnightConfigExample extends MidnightConfig {

    @Comment public static Comment text1;                       // Comments are rendered like an option without a button and are excluded from the config file
    @Entry public static int fabric = 16777215;                 // Example for a int option
    @Entry public static double world = 1.4D;                   // Example for a double option
    @Entry public static boolean showInfo = true;               // Example for a boolean option
    @Entry public static String name = "Hi";                    // Example for a string option
    @Entry public static TestEnum testEnum = TestEnum.FABRIC;   // Example for a enum option
    public static enum TestEnum {                               // Enums allow the user to cycle through predefined options
        QUILT, FABRIC
    }
    @Entry(min=10,max=30) public static int hello = 15675965;   // - The entered number has to be larger than 10 and smaller than 30


    public static int imposter = 16777215; // - Entries without an @Entry or @Comment annotation are ignored

    /*
    The .json language file for your config class could look similar to this:
    {
	    "modid.midnightconfig.title":"I am a title",        // "*.midnightconfig.title" defines the title of the screen
	    "modid.midnightconfig.text1":"I am a comment *u*",  // Translation for the comment "text1" defined in the example config
	    "modid.midnightconfig.name":"Pet Name",             // Translation for the field "name" defined in the example config

	    "modid.midnightconfig.name.tooltip":"Name your Pet! \n I am a new line uwu",
	    // When hovering over the option "showInfo",
	    // this text will appear as a tooltip.
	    // "\n" inserts a line break.

	    "modid.midnightconfig.fabric":"I am an int",
	    "modid.midnightconfig.world":"I am a double",
	    "modid.midnightconfig.showInfo":"I am a boolean",
	    "modid.midnightconfig.hello":"I am a limited int!",
	    "modid.midnightconfig.testEnum":"I am an enum!",
	    "modid.midnightconfig.enum.TestEnum.FABRIC":"Fancy",
	    "modid.midnightconfig.enum.TestEnum.QUILT":"Fabulous"
    }
    To initialize the config you have to call "MidnightConfig.init("modid", MidnightConfigExample.class)" in your ModInitializer

    To get an instance of the config screen you have to call "MidnightConfig.getScreen(parent, "modid");"

    The code in your ModMenu integration class would look something like this:
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> MidnightConfig.getScreen(parent, "modid");
    }
    */
}
