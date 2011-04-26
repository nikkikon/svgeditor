package svgedit.resize;

import javax.swing.event.MouseInputListener;

import svgedit.svg.SVGElement;

 public interface MouseListenerInterface {
	public void addResizeMouseListener();
	public void resize();
	public MouseInputListener getListener();
	public SVGElement getSVGElement();

}
