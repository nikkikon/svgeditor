package svgedit.xml;

import java.io.*;
import java.util.logging.*;
import org.xml.sax.SAXException;
import org.w3c.dom.*;

import svgedit.svg.*;

/** Reads an SVG document from XML and creates an {@link svgedit.svg.SVGDocument}.
 *
 */
public class XMLReader {

    private static final Logger logger = Logger.getLogger("svgedit");

    private boolean fail;
    private static Document doc; 
    private SVGDocument document;

    /** Creates an {@link svgedit.svg.SVGDocument} from a file.  Any errors
     * in the file are logged, and will throw an {@link IOException}.
     *
     * @param file the file to read
     * @param viewport the viewport the document will be situated within
     * @return a new SVG document
     * @throws IOException if any errors were encountered
     */
    public static SVGDocument read(File file, SVGViewport viewport) throws IOException {
	try {
	    XMLReader reader = new XMLReader(viewport);

	    doc = XMLUtil.read(file);
	    Element elem = doc.getDocumentElement();
	    reader.readSVGElement(elem);

	    if (!reader.fail)
		return reader.document;

	} catch (SAXException e) {
	    logger.severe(e.getMessage());
	    throw new IOException("Unable to read SVG file");
	}

	throw new IOException("Unable to read SVG file");
    }

    private XMLReader(SVGViewport viewport) {
	document = new SVGDocument(viewport);
    }

    /** Reads the root SVG element.
     *
     * @param elem the XML document element
     */
    private void readSVGElement(Element elem) throws SAXException {
        if (!elem.getNodeName().equals("svg"))
            throw new SAXException("SVG document must have 'svg' document element");
     
	// Read document properties
	readLengthAttribute(elem, "width", document.getWidth());
	readLengthAttribute(elem, "height", document.getHeight());

	readGroupChildrenElements(elem, document.getRootGroup());
    }

    /** Reads an XML element from an SVG document.  Returns the corresponding
     * SVG element fully parsed
     *
     * @param elem the XML element to parse
     * @return a new SVG element
     */
    private SVGElement readElement(Element elem) {
	String name = elem.getNodeName();
	if (name.equals("rect"))
	    return readRectElement(elem);
	else if (name.equals("g"))
	    return readGroupElement(elem);
	else if (name.equals("circle"))
	    return readCircleElement(elem);
	else if (name.equals("line"))
	    return readLineElement(elem);

	return null;
    }

    /** Reads a rect element
     *
     * @param elem the XML element to read
     * @return a new SVG element
     */
    private SVGElement readRectElement(Element elem) {
	SVGRectElement rect = new SVGRectElement(document,elem);
	readLengthAttribute(elem, "x", rect.getX());
	readLengthAttribute(elem, "y", rect.getY());
	readLengthAttribute(elem, "width", rect.getWidth());
	readLengthAttribute(elem, "height", rect.getHeight());

	readStylableElement(elem, rect);

	return rect;
    }

    /** Reads a circle element
     *
     * @param elem the XML element to read
     * @return a new SVG element
     */
    private SVGElement readCircleElement(Element elem) {
	SVGCircleElement circle = new SVGCircleElement(document,elem);
	readLengthAttribute(elem, "cx", circle.getCX());
	readLengthAttribute(elem, "cy", circle.getCY());
	readLengthAttribute(elem, "r", circle.getR());

	readStylableElement(elem, circle);

	return circle;
    }


    /** Reads a line element
     *
     * @param elem the XML element to read
     * @return a new SVG element
     */
    private SVGElement readLineElement(Element elem) {
	SVGLineElement line = new SVGLineElement(document,elem);
	readLengthAttribute(elem, "x1", line.getX1());
	readLengthAttribute(elem, "y1", line.getY1());
	readLengthAttribute(elem, "x2", line.getX2());
	readLengthAttribute(elem, "y2", line.getY2());

	readStylableElement(elem, line);

	return line;
    }

    /** Reads style attributes on an SVG element
     *
     * @param elem the XML element to read
     * @param stylable the SVG element to set attributes on
     */
    private void readStylableElement(Element elem, SVGStylable stylable) {
	if (elem.hasAttribute("fill"))
	    readPaintAttribute(elem, "fill", stylable.getFill());
	if (elem.hasAttribute("stroke"))
	    readPaintAttribute(elem, "stroke", stylable.getStroke());
	if (elem.hasAttribute("stroke-width"))
	    readLengthAttribute(elem, "stroke-width", stylable.getStrokeWidth());
    }

    /** Reads a group element
     *
     * @param elem the XML element to read
     * @return a new SVG element
     */
    private SVGElement readGroupElement(Element elem) {
	SVGGroup group = new SVGGroup(document);
	readGroupChildrenElements(elem, group);
	return group;
    }

    /** Reads child elements into a group
     *
     * @param elem the XML group element to read
     * @param group the SVG group to add children to
     */
    private void readGroupChildrenElements(Element elem, SVGGroup group) {
	for (Node child = elem.getFirstChild(); child != null; child = child.getNextSibling()) {

	    if (child.getNodeType() == Node.ELEMENT_NODE) {

		SVGElement element = readElement((Element) child);
		if (element != null) {
		    group.appendChild(element);
		}

	    }
	}
    }

    /** Reads an SVG length value
     *
     * @param elem the XML element to read
     * @param attribute the name of the attribute on the XML element
     * @param length the SVG length attribute to set
     */
    private void readLengthAttribute(Element elem, String attribute, SVGLength length) {
	if (elem.hasAttribute(attribute)) {
	    String value = elem.getAttribute(attribute);
	    try {
		length.setValueFromString(value);
	    } catch (NumberFormatException e) {
		logger.severe(String.format("In <%s %s='%s' ... />, value must be valid length", elem.getNodeName(), attribute, value));
		fail = true;
	    }
	} else {
	    logger.severe(String.format("<%s ... /> element is missing required attribute '%s'", elem.getNodeName(), attribute));
	    fail = true;
	}
    }

    /** Reads an SVG paint value
     *
     * @param elem the XML element to read
     * @param attribute the name of the attribute on the XML element
     * @param length the SVG paint attribute to set
     */
    private void readPaintAttribute(Element elem, String attribute, SVGPaint paint) {
	if (elem.hasAttribute(attribute)) {
	    String value = elem.getAttribute(attribute);
	    try {
		paint.setValueFromString(value);
	    } catch (NumberFormatException e) {
		logger.severe(String.format("In <%s %s='%s' ... />, value must be valid paint", elem.getNodeName(), attribute, value));
		fail = true;
	    }
	} else {
	    logger.severe(String.format("<%s ... /> element is missing required attribute '%s'", elem.getNodeName(), attribute));
	    fail = true;
	}
    }
    
    public static Document getDocument(){
    	return doc;
    }
}
