package svgedit.xml;

import java.io.File;
import java.io.IOException;
import org.w3c.dom.*;
import svgedit.svg.SVGCircleElement;
import svgedit.svg.SVGStylable;
import svgedit.svg.SVGDocument;
import svgedit.svg.SVGElement;
import svgedit.svg.SVGGroup;
import svgedit.svg.SVGLineElement;
import svgedit.svg.SVGRectElement;
import svgedit.svg.SVGVisitor;

/** Writes an SVG document to an XML file.
 */
public class XMLWriter {

    /** Writes the SVG document to a file.
     *
     * @param svgDoc the SVG document to write
     * @param file the XML file to write to
     * @throws IOException if a file I/O error occurs
     */
    public static void write(SVGDocument svgDoc, File file) throws IOException {
	Document xmlDoc = XMLUtil.createDocument("http://www.w3.org/2000/svg", "svg");

	Element root = xmlDoc.getDocumentElement();
	root.setAttribute("width", svgDoc.getWidth().valueAsString());
	root.setAttribute("height", svgDoc.getHeight().valueAsString());

	Visitor visitor = new Visitor(xmlDoc, root);
	visitor.visitGroupChildren(svgDoc.getRootGroup());

	XMLUtil.write(xmlDoc, file);
    }

    /** Writes style attributes of an SVG element to the given XML element.
     *
     * @param elem the XML element to write to
     * @param style the SVG element to read style from
     */
    private static void writeStylable(Element elem, SVGStylable style) {
	elem.setAttribute("fill", style.getFill().valueAsString());
	elem.setAttribute("stroke", style.getStroke().valueAsString());
	elem.setAttribute("stroke-width", style.getStrokeWidth().valueAsString());
    }

    /** Visitor implements element-specific writing depending on the SVG
     * element.
     */
    private static class Visitor implements SVGVisitor {
	Document document;
	Element parent;

	private Visitor(Document document, Element parent) {
	    this.document = document;
	    this.parent = parent;
	}

	public void visitGroup(SVGGroup group) {
	    Element elem = document.createElement("g");
	    parent.appendChild(elem);

	    Visitor childVisitor = new Visitor(document, elem);
	    childVisitor.visitGroupChildren(group);
	}

	public void visitGroupChildren(SVGGroup group) {
	    for (SVGElement element : group) {
		element.acceptVisitor(this);
	    }
	}

	public void visitRect(SVGRectElement rect) {
	    Element elem = document.createElement("rect");
	    parent.appendChild(elem);

	    elem.setAttribute("x", rect.getX().valueAsString());
	    elem.setAttribute("y", rect.getY().valueAsString());
	    elem.setAttribute("width", rect.getWidth().valueAsString());
	    elem.setAttribute("height", rect.getHeight().valueAsString());

	    writeStylable(elem, rect);
	}

	public void visitCircle(SVGCircleElement circle) {
	    Element elem = document.createElement("circle");
	    parent.appendChild(elem);

	    elem.setAttribute("cx", circle.getCX().valueAsString());
	    elem.setAttribute("cy", circle.getCY().valueAsString());
	    elem.setAttribute("r", circle.getR().valueAsString());

	    writeStylable(elem, circle);
	}

	public void visitLine(SVGLineElement line) {
	    Element elem = document.createElement("line");
	    parent.appendChild(elem);

	    elem.setAttribute("x1", line.getX1().valueAsString());
	    elem.setAttribute("y1", line.getY1().valueAsString());
	    elem.setAttribute("x2", line.getX2().valueAsString());
	    elem.setAttribute("y2", line.getY2().valueAsString());

	    writeStylable(elem, line);
	}
    }

}
