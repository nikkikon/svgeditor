package svgedit.xml;

import java.io.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/** Utility class to simplify reading and writing XML DOM */
public class XMLUtil {

    /** Creates a new XML document.
     *
     * @param namespace the default namespace of the document
     * @param documentElement the name of the document element
     * @return a new XML document
     */
    public static Document createDocument(String namespace, String documentElement) {
	try {
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    DOMImplementation impl = builder.getDOMImplementation();
	    return impl.createDocument(namespace, documentElement, null);
	} catch (ParserConfigurationException e) {
	    // This exception is never thrown, treat as fatal if it is
	    throw new RuntimeException(e);
	}
    }

    /** Read an XML file into a DOM tree
     * 
     * @param file the file to read
     * @return a new XML document
     * @throws SAXException if an XML parse error occurs
     * @throws IOException if a file I/O error occurs
     */
    public static Document read(File file) throws SAXException, IOException {
	try {
	    DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = builderFactory.newDocumentBuilder();

            // Workaround for missing external DTD.  When the parser looks up
            // an external reference, we provide an empty document back.  While
            // not correct, we don't expect SVG files loaded by this program
            // to depend on externally defined entities; nor do we require
            // validation.
            //
            // http://stackoverflow.com/questions/2640825/how-to-parse-a-xhtml-ignoring-the-doctype-declaration-using-dom-parser
            builder.setEntityResolver(new EntityResolver() {
                public InputSource resolveEntity(String publicId, String systemId)
                        throws SAXException, IOException {
                    return new InputSource(new StringReader(""));
                }
            });

	    return builder.parse(file);
	} catch (ParserConfigurationException e) {
	    // This exception is never thrown, treat as fatal if it is
	    throw new RuntimeException(e);
	}
    }

    /** Write an XML DOM tree to a file
     *
     * @param doc the document to write
     * @param file the file to write to
     * @throws IOException if a file I/O error occurs
     */
    public static void write(Document doc, File file) throws IOException {
	try {
	    DOMSource domSource = new DOMSource(doc);
	    FileOutputStream stream = new FileOutputStream(file);

	    // OutputStreamWriter works around indent bug 6296446 in JDK
	    // http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6296446
	    StreamResult streamResult = new StreamResult(new OutputStreamWriter(stream));
	    TransformerFactory tf = TransformerFactory.newInstance();
	    tf.setAttribute("indent-number", new Integer(2));
	    Transformer serializer = tf.newTransformer();
	    serializer.setOutputProperty(OutputKeys.INDENT, "yes");
	    serializer.transform(domSource, streamResult);
	} catch (TransformerException e) {
	    // This exception is never thrown, treat as fatal if it is
	    throw new RuntimeException(e);
	}
    }

}
