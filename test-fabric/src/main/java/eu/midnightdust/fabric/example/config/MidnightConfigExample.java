package eu.midnightdust.fabric.example.config;

import com.google.common.collect.Lists;
import eu.midnightdust.lib.config.MidnightConfig;
import net.minecraft.util.Identifier;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/** Every option in a MidnightConfig class has to be public and static, so we can access it from other classes.
 * The config class also has to extend MidnightConfig*/

public class MidnightConfigExample extends MidnightConfig {
    public static final String TEXT = "text";
    public static final String NUMBERS = "numbers";
    public static final String SLIDERS = "sliders";
    public static final String LISTS = "lists";
    public static final String FILES = "files";

    @Comment(category = TEXT) public static Comment text1;                       // Comments are rendered like an option without a button and are excluded from the config file
    @Comment(category = TEXT, centered = true) public static Comment text2;      // Centered comments are the same as normal ones - just centered!
    @Comment(category = TEXT) public static Comment spacer1;                     // Comments containing the word "spacer" will just appear as a blank line
    @Entry(category = TEXT) public static boolean showInfo = true;               // Example for a boolean option
    @Entry(category = TEXT) public static String name = "Hello World!";          // Example for a string option, which is in a category!
    @Entry(category = TEXT, width = 7, min = 7, isColor = true, name = "I am a color!") public static String titleColor = "#ffffff"; // The isColor property adds a color chooser for a hexadecimal color
    @Entry(category = TEXT, idMode = 0) public static Identifier id = Identifier.ofVanilla("diamond");          // Example for an identifier with matching items displayed next to it!
    @Entry(category = TEXT) public static TestEnum testEnum = TestEnum.FABRIC;   // Example for an enum option
    public enum TestEnum {                               // Enums allow the user to cycle through predefined options
        QUILT, FABRIC, FORGE
    }
    @Entry(category = NUMBERS) public static int fabric = 16777215;                 // Example for an int option
    @Entry(category = NUMBERS) public static double world = 1.4D;                   // Example for a double option
    @Entry(category = NUMBERS, min=69,max=420) public static int hello = 420;   // - The entered number has to be larger than 69 and smaller than 420
    @Entry(category = SLIDERS, name = "I am an int slider.",isSlider = true, min = 0, max = 100) public static int intSlider = 35; // Int fields can also be displayed as a Slider
    @Entry(category = SLIDERS, name = "I am a float slider!", isSlider = true, min = 0f, max = 1f, precision = 1000) public static float floatSlider = 0.24f; // And so can floats! Precision defines the amount of decimal places
    // The name field can be used to specify a custom translation string or plain text
    @Entry(category = LISTS, name = "I am a string list!") public static List<String> stringList = Lists.newArrayList("String1", "String2"); // Array String Lists are also supported
    @Entry(category = LISTS, isColor = true, name = "I am a color list!") public static List<String> colorList = Lists.newArrayList("#ac5f99", "#11aa44"); // Lists also support colors
    @Entry(category = LISTS, name = "I am an identifier list!", idMode = 1) public static List<Identifier> idList = Lists.newArrayList(Identifier.ofVanilla("dirt")); // A list of block identifiers
    @Entry(category = LISTS, name = "I am an integer list!") public static List<Integer> intList = Lists.newArrayList(69, 420);
    @Entry(category = LISTS, name = "I am a float list!") public static List<Float> floatList = Lists.newArrayList(4.1f, -1.3f, -1f);

    @Entry(category = FILES,
            fileSelectionMode = JFileChooser.FILES_ONLY,
            fileExtensions = {"json", "txt", "log"}, // Define valid file extensions
            fileChooserType = JFileChooser.SAVE_DIALOG,
            name = "I am a file!")
    public static String myFile = ""; // The isFile property adds a file picker button

    @Entry(category = FILES,
            fileSelectionMode = JFileChooser.DIRECTORIES_ONLY,
            fileChooserType = JFileChooser.OPEN_DIALOG,
            name = "I am a directory!")
    public static String myDirectory = ""; // The isDirectory property adds a directory picker button

    @Entry(category = FILES,
            fileSelectionMode = JFileChooser.FILES_AND_DIRECTORIES,
            fileExtensions = {"png", "jpg", "jpeg"},
            fileChooserType = JFileChooser.OPEN_DIALOG,
            name = "I can choose both files & directories!")
    public static String myFileOrDirectory = ""; // The isFileOrDirectory property adds a file or directory picker button
    @Entry(category = FILES,
            fileSelectionMode = JFileChooser.FILES_AND_DIRECTORIES,
            fileExtensions = {"png", "jpg", "jpeg"},
            fileChooserType = JFileChooser.OPEN_DIALOG,
            name = "I am a mf file/directory list!")
    public static List<String> fileOrDirectoryList = new ArrayList<>(); // Yes, that's right – you can even have lists of files/directories

    public static int imposter = 16777215; // - Entries without an @Entry or @Comment annotation are ignored
}