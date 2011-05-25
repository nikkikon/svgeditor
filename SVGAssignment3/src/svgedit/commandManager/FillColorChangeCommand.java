package svgedit.commandManager;

import java.awt.Color;
import java.awt.Paint;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JColorChooser;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import svgedit.gui.Frame;
import svgedit.gui.PaintDropDown;
import svgedit.gui.View;
import svgedit.svg.SVGElement;
import svgedit.svg.SVGGroup;
import svgedit.svg.SVGPaint;
import svgedit.svg.SVGStylable;

public class FillColorChangeCommand implements CommandInterface{

	private Frame frame;
	private View view;
	private SVGPaint paint;
	private ArrayList<SVGElement>elements = new ArrayList<SVGElement>();
	//private ArrayList<SVGPaint>paints = new ArrayList<SVGPaint>();
	private ArrayList<Color>paints = new ArrayList<Color>();
	
	public FillColorChangeCommand(View view,SVGPaint paint){
		elements.clear();
		paints.clear();
        this.view=view;
        this.paint=paint;
	}
	public void execute(){
        for (SVGElement elem : view.getSelectedElements()) {
            if (elem instanceof SVGStylable) {
            	elements.add(elem);
            	
            	paints.add(((SVGStylable) elem).getFill().getRGBColor());
            	//paints.add(prePaint);
            	//System.out.println("111"+paints.get(0));
                ((SVGStylable) elem).getFill().setValueFromPaint(paint);
                //System.out.println("111"+paints.get(0));
               // System.out.println("222");
                view.getDocument().setModified(true);
            }
        }

        view.repaint();
    }
	public void undo(){
		//int i=0;
		//System.out.println("111"+paints.get(0));
		/*
		for(SVGElement elem : view.getSelectedElements()){
			((SVGStylable)elem).getFill().setValueFromPaint(paints.get(i));
			i++;
		}
*/
		for(int i = 0; i<elements.size();i++){
			//System.out.println(elements.get(i));
			SVGPaint svgPaint = new SVGPaint();
			svgPaint.setRGBColor(paints.get(i));
			//System.out.println(paints.get(i));
			((SVGStylable)elements.get(i)).getFill().setValueFromPaint(svgPaint);
		}
		view.repaint();
	}
}