package svgedit.gui;

/** Manages user preferences for the GUI.
 *
 * Internally uses {@link java.util.prefs.Preferences} to store preferences.
 *
 */
public class Preferences {

    private java.util.prefs.Preferences preferences;

    /** Root path for all preferences */
    public static final String ROOT_NODE = "svgedit.gui";

    /** Key for the default path to use for file dialogs */
    public static final String KEY_DEFAULT_PATH = "defaultPath";

    /** Creates a new preferences object.  Only one object is required, as
     *  all preferences are stored per-user.
     */
    public Preferences() {
        preferences = java.util.prefs.Preferences.userRoot().node(ROOT_NODE);
    }

    /** Gets the default path to present to the user when showing a file
     *  dialog
     *
     * @return a string giving the default path, or {@literal null} if the
     *   preference hasn't been set
     */
    public String getDefaultPath() {
        return preferences.get(KEY_DEFAULT_PATH, null);
    }

    /** Sets the default path to present to the user when showing a file dialog
     *
     * @param path the default path to set
     */
    public void setDefaultPath(String path) {
        preferences.put(KEY_DEFAULT_PATH, path);
    }

}
