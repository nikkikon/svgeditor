package svgedit.gui;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * Provides filtering for file chooser dialogs to select SVG files.
 *
 * Accepts only files with the {@literal .svg} extension, ignoring case.
 */
public class SVGFileFilter extends FileFilter {

    @Override
    public boolean accept(File file) {
        return file.getName().toLowerCase().endsWith(".svg");
    }

    @Override
    public String getDescription() {
        return "SVG Files";
    }
}
