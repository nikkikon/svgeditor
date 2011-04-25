package svgedit.resize;

import javax.swing.event.MouseInputListener;

 public interface MouseListenerInterface {
	abstract public void addResizeMouseListener();
	abstract public void resize();
	abstract public MouseInputListener getListener();

}
