package svgedit.gui;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * Provides filtering for file chooser dialogs to select SVG files.
 *
 * Accepts only files with the {@literal .svgz} extension, ignoring case.
 */
public class SVGZFileFilter extends FileFilter {

    @Override
    public boolean accept(File file) {
        return file.isDirectory() ||
               file.getName().toLowerCase().endsWith(".svgz");
    }

    @Override
    public String getDescription() {
        return "SVGZ Files";
    }
}