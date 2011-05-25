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
import svgedit.svg.SVGLength;
import svgedit.svg.SVGPaint;
import svgedit.svg.SVGStylable;

public class StrokeWidthChangeCommand implements CommandInterface{

	private Frame frame;
	private View view;
	private SVGLength strokeWidth;
	private ArrayList<SVGElement>elements = new ArrayList<SVGElement>();
	//private ArrayList<SVGPaint>paints = new ArrayList<SVGPaint>();
	private ArrayList<String>svgLengths = new ArrayList<String>();
	
	public StrokeWidthChangeCommand(View view,SVGLength strokeWidth){
		elements.clear();
        this.view=view;
        this.strokeWidth=strokeWidth;
	}
	public void execute(){
        for (SVGElement elem : view.getSelectedElements()) {
            if (elem instanceof SVGStylable) {
            	elements.add(elem);
            	//System.out.println("11"+((SVGStylable) elem).getStrokeWidth());
            	svgLengths.add(((SVGStylable) elem).getStrokeWidth().valueAsString());
            	//paints.add(prePaint);
                ((SVGStylable) elem).getStrokeWidth().setValueFromLength(strokeWidth);
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
			SVGLength svgLength = new SVGLength();
			svgLength.setValueFromString(svgLengths.get(i));
			((SVGStylable)elements.get(i)).getStrokeWidth().setValueFromLength(svgLength);
		}
		view.repaint();
	}
}