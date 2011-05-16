package svgedit.gui;

/** The listener interface for receiving view events.
 *
 * Implement this interface and register with {@link View#addViewListener} to
 * receive events from a view.
 *
 */
public interface ViewListener {

    /** Invoked with the current selection in the view is changed.
     *
     * @param view the view which invoked the event
     */
    public void viewSelectionChanged(View view);

    /** Invoked with the view's current manipulator is changed.
     *
     * @param view the view which invoked the event
     */
    public void viewManipulatorChanged(View view);

}
