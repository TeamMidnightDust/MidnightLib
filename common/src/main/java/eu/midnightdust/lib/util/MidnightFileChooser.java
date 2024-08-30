package eu.midnightdust.lib.util;

import javax.swing.JFileChooser;
import javax.swing.JDialog;
import javax.swing.ImageIcon;
import java.awt.Component;
import java.awt.HeadlessException;

/**
 * This class is used create a file chooser dialog with the icon of the target mod.<br>
 * <code>MidnightFileChooser fileChooser = new MidnightFileChooser("currentDirectoryPath", "modid");</code>
 */
public class MidnightFileChooser extends JFileChooser {
    final ImageIcon icon;

    public MidnightFileChooser(String currentDirectoryPath, String modid) {
        super(currentDirectoryPath);
        this.icon = MidnightIconUnit.of(modid).getIcon();
    }

    @Override
    protected JDialog createDialog(Component parent) throws HeadlessException {
        JDialog dialog = super.createDialog(parent);
        dialog.setIconImage(icon.getImage());
        return dialog;
    }
}
