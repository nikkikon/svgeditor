package svgedit.svg;

import java.util.Iterator;
import java.util.Stack;

/**
 * Iterator over SVG elements, traversing into groups in preorder.
 * 
 * For example, in the structure:
 * 
 * <pre>
 * rect1
 * g2
 *   rect3
 *   circle4
 *   g5
 *     rect6
 * circle7
 * </pre>
 * 
 * the elements will be iterated in the order:
 * 
 * <pre>
 * rect1
 * g2
 * rect3
 * circle4
 * g5
 * rect6
 * circle7
 * </pre>
 */
public class SVGIterator implements Iterator<SVGElement> {

    private Stack<Iterator<SVGElement>> iterators;

    /**
     * Create an iterator over a document's elements.
     * 
     * @param document the document to iterate
     */
    public SVGIterator(SVGDocument document) {
        this(document.getRootGroup());
    }

    /**
     * Create an iterator over a group's elements.
     * 
     * @param group the group to iterate
     */
    public SVGIterator(SVGGroup group) {
        iterators = new Stack<Iterator<SVGElement>>();
        iterators.push(group.iterator());
        validate();
    }

    public boolean hasNext() {
        // Ask the current iterator if it hasNext
        return !iterators.isEmpty() && iterators.peek().hasNext();
    }

    public SVGElement next() {
        // Return the next element from the current iterator
        Iterator<SVGElement> iterator = iterators.peek();
        SVGElement result = iterator.next();

        // Push a new iterator for group children -- they will be returned
        // after the group itself
        if (result instanceof SVGGroup)
            iterators.push(((SVGGroup) result).iterator());

        // Pop iterators off the stack that have no more elements
        validate();

        return result;
    }

    /**
     * Not supported.
     * 
     * @throws UnsupportedOperationException
     *             always
     */
    public void remove() {
        throw new UnsupportedOperationException("Not supported.");
    }

    /** Remove expired iterators from the stack */
    private void validate() {
        while (!iterators.isEmpty() && !iterators.peek().hasNext())
            iterators.pop();

    }

}
