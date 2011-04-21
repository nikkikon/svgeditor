package svgedit.svg;

/** Interface for an element providing a viewport.  Normally the top-level
 * document provides this interface, however the document dimensions themselves
 * may need to reference a viewport (i.e., if specified as a percentage), so
 * the viewport in this case is provided by the application.
 */
public interface SVGViewport {

    /** Get the size of the viewport in user units */
    public float getViewportWidth();

    /** Get the height of the viewport in user units */
    public float getViewportHeight();
}
