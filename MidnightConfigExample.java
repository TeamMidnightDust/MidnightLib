package eu.midnightdust.core.config;

import com.google.common.collect.Lists;
import eu.midnightdust.lib.config.MidnightConfig;

import java.util.List;

/** MidnightConfig documentation & examples:
 * Thanks for choosing MidnightConfig - the fancy, tiny and lightweight config library.
 * If you want to use the lib in your mod, here are some examples and hints:
 * Every option in a MidnightConfig class has to be public and static, so we can access it from other classes.
 * The config class also has to extend MidnightConfig*/

public class MidnightConfigExample extends MidnightConfig {

    @Comment(category = "text") public static Comment text1;                       // Comments are rendered like an option without a button and are excluded from the config file
    @Comment(category = "text", centered = true) public static Comment text2;      // Centered comments are the same as normal ones - just centered!
    @Comment(category = "text") public static Comment spacer1;                     // Comments containing the word "spacer" will just appear as a blank line
    @Entry(category = "text") public static boolean showInfo = true;               // Example for a boolean option
    @Entry(category = "text") public static String name = "Hello World!";          // Example for a string option, which is in a category!
    @Entry(category = "text") public static TestEnum testEnum = TestEnum.FABRIC;   // Example for an enum option
    public enum TestEnum {                               // Enums allow the user to cycle through predefined options
        QUILT, FABRIC, FORGE
    }
    @Entry(category = "numbers") public static int fabric = 16777215;                 // Example for an int option
    @Entry(category = "numbers") public static double world = 1.4D;                   // Example for a double option
    @Entry(category = "numbers", min=69,max=420) public static int hello = 420;   // - The entered number has to be larger than 69 and smaller than 420
    @Entry(category = "text", width = 7, min = 7, isColor = true, name = "I am a color!") public static String titleColor = "#ffffff"; // The isColor property adds a preview box for a hexadecimal color
    @Entry(category = "text", name = "I am an array list!") public static List<String> arrayList = Lists.newArrayList("String1", "String2"); // Array String Lists are also supported
    @Entry(category = "sliders", name = "I am an int slider.",isSlider = true, min = 0, max = 100) public static int intSlider = 35; // Int fields can also be displayed as a Slider
    @Entry(category = "sliders", name = "I am a float slider!", isSlider = true, min = 0f, max = 1f, precision = 1000) public static float floatSlider = 0.24f; // And so can floats! Precision defines the amount of decimal places
    // The name field can be used to specify a custom translation string or plain text

    public static int imposter = 16777215; // - Entries without an @Entry or @Comment annotation are ignored

    /*
    The .json language file for your config class could look similar to this:
    {
	    "modid.midnightconfig.title":"I am a title",        // "*.midnightconfig.title" defines the title of the screen
	    "modid.midnightconfig.text1":"I am a comment *u*",  // Translation for the comment "text1" defined in the example config
	    "modid.midnightconfig.text2":"I am a centered comment (╯°□°）╯︵ ┻━┻",
	    "modid.midnightconfig.name":"I am a string!",             // Translation for the field "name" defined in the example config
	    "modid.midnightconfig.name.tooltip":"I am a tooltip uwu \nI am a new line",
	    // When hovering over the option "showInfo",
	    // this text will appear as a tooltip.
	    // "\n" inserts a line break.
	    "modid.midnightconfig.fabric":"I am an int",
	    "modid.midnightconfig.world":"I am a double",
	    "modid.midnightconfig.showInfo":"I am a boolean",
	    "modid.midnightconfig.hello":"I am a limited int!",
	    "modid.midnightconfig.testEnum":"I am an enum!",
	    "modid.midnightconfig.enum.TestEnum.FORGE":"Slow",
	    "modid.midnightconfig.enum.TestEnum.FABRIC":"Fancy",
	    "modid.midnightconfig.enum.TestEnum.QUILT":"Fabulous",
	    "modid.midnightconfig.category.numbers": "Numbers",
	    "modid.midnightconfig.category.text": "Text",
	    "modid.midnightconfig.category.sliders": "Sliders"
    }
    To initialize the config you have to call "MidnightConfig.init("modid", MidnightConfigExample.class)" in your ModInitializer
    To get an instance of the config screen you have to call "MidnightConfig.getScreen(parent, "modid");"
    If you don't use the whole library and therefore not the automatic ModMenu integration, the code in your ModMenu integration class would look something like this:
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> MidnightConfig.getScreen(parent, "modid");
    }
    */
}