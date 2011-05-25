package svgedit.svg;

import java.io.File;

/** Top-level document object describing an SVG document.
 *
 * The contents of the document are contained within the "root group" -- a
 * fictional group used to simplify the representation (this group does not
 * correspond to any {@literal g} element in the file.
 *
 */
public class SVGDocument implements SVGViewport, Iterable<SVGElement> {

    private boolean modified;
    private File file;

    private SVGLength width;
    private SVGLength height;

    private SVGGroup root;

    /** Creates a new SVG document situated within a viewport. */
    public SVGDocument(SVGViewport viewport) {
        modified = false;
        root = new SVGGroup(this);
        width = new SVGLength(viewport, SVGLength.SVG_DIMENSION_X);
        height = new SVGLength(viewport, SVGLength.SVG_DIMENSION_Y);

        // Arbitrary default document size
        width.newValueSpecifiedUnits(SVGLength.SVG_LENGTHTYPE_NUMBER, 640);
        height.newValueSpecifiedUnits(SVGLength.SVG_LENGTHTYPE_NUMBER, 480);
    }

    /** Gets the root group containing the top-level document elements.
     *
     * @return the root group element
     */
    public SVGGroup getRootGroup() {
        return root;
    }

    /** Gets the width attribute of the document.
     *
     * @return a length object representing the width of the document
     */
    public SVGLength getWidth() {
        return width;
    }

    /** Gets the height attribute of the document.
     *
     * @return a length object representing the height of the document
     */
    public SVGLength getHeight() {
        return height;
    }

    public float getViewportWidth() {
        return width.getValue();
    }

    public float getViewportHeight() {
        return height.getValue();
    }
    
    /** Gets a value indicating if the document has been modified since the last
     *  time it was saved.
     *
     * @return {@literal true} if the document contains unsaved changes
     */
    public boolean getModified() {
        return modified;
    }

    /** Sets a value indicating if the document has been modified since the
     *  last time it was saved.
     *
     * @param modified {@literal true} if the document contains unsaved
     *  modifications.
     */
    public void setModified(boolean modified) {
        this.modified = modified;
    }

    /**
     * Gets the file that the document opened from or mostly recently saved as.
     * 
     * @return the file backing this document
     */
    public File getFile() {
        return file;
    }

    /**
     * Sets the file that the document is backed by.
     * 
     * @param file
     *            the file to set as backing this document
     */
    public void setFile(File file) {
        this.file = file;
    }

    public SVGIterator iterator() {
        return new SVGIterator(this);
    }
}
