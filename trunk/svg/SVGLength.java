package svgedit.svg;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.w3c.dom.DOMException;

/**
 * Distance unit type.
 * 
 * Lengths are specified with a unit and a value in that unit. When rendered,
 * the "user unit" is used; which can be converted to or from any other unit
 * given the context of the element the length is defined against.
 * 
 * Specified in 4.5.11 Interface SVGLength
 */
public class SVGLength {

    public static final int SVG_LENGTHTYPE_UNKNOWN = 0;
    public static final int SVG_LENGTHTYPE_NUMBER = 1;
    public static final int SVG_LENGTHTYPE_PERCENTAGE = 2;
    public static final int SVG_LENGTHTYPE_EMS = 3;
    public static final int SVG_LENGTHTYPE_EXS = 4;
    public static final int SVG_LENGTHTYPE_PX = 5;
    public static final int SVG_LENGTHTYPE_CM = 6;
    public static final int SVG_LENGTHTYPE_MM = 7;
    public static final int SVG_LENGTHTYPE_IN = 8;
    public static final int SVG_LENGTHTYPE_PT = 9;
    public static final int SVG_LENGTHTYPE_PC = 10;

    static final int SVG_DIMENSION_UNKNOWN = 0;
    static final int SVG_DIMENSION_X = 1;
    static final int SVG_DIMENSION_Y = 2;
    static final int SVG_DIMENSION_OTHER = 3;

    private SVGViewport viewport;
    private int unitType;
    private int dimensionType;
    private float valueInSpecifiedUnits;

    /**
     * Constructs a new length with unspecified value and unit, not situated in
     * a viewport.
     * 
     * Because the length does not belong to an element, its value in user units
     * cannot be determined. {@link DOMException} will be thrown if setValue or
     * getValue are called.
     */
    public SVGLength() {
    }

    /**
     * Constructs a new length not situated in a viewport.
     * 
     * Because the length does not belong to an element, its value in user units
     * cannot be determined. {@link DOMException} will be thrown if setValue or
     * getValue are called.
     * 
     * @param unitType
     *            the specified units of the length
     * @param valueInSpecifiedUnits
     *            the value of the length in the specified units
     */
    public SVGLength(int unitType, float valueInSpecifiedUnits) {
        this.unitType = unitType;
        this.valueInSpecifiedUnits = valueInSpecifiedUnits;
    }

    /**
     * Constructs a new length situated within a viewport.
     * 
     * @param viewport
     *            viewport containing this length
     * @param dimensionType
     *            dimension along which to measure this length
     */
    SVGLength(SVGViewport viewport, int dimensionType) {
        assert (dimensionType == SVG_DIMENSION_X || dimensionType == SVG_DIMENSION_Y || dimensionType == SVG_DIMENSION_OTHER);

        this.viewport = viewport;
        this.dimensionType = dimensionType;
        this.unitType = SVG_LENGTHTYPE_UNKNOWN;
        this.valueInSpecifiedUnits = 0.0f;
    }

    /**
     * Gets the value of the length, in user units.
     * 
     * @return the value of the length
     * @throws DOMException
     *             if the specified units are incomplete, or if the length is
     *             not situated within a viewport
     */
    public float getValue() {
        return valueInSpecifiedUnits * getUserUnitsPerSpecifiedUnits();
    }

    /**
     * Sets the value of the length, in user units.
     * 
     * @param value
     *            the value of the length
     * @throws DOMException
     *             if the specified units are incomplete, or if the length is
     *             not situated within a viewport
     */
    public void setValue(float value) {
        this.valueInSpecifiedUnits = value / getUserUnitsPerSpecifiedUnits();
    }

    /**
     * Gets the value of the length in its specified units.
     * 
     * @return the value of the length, in specified units
     */
    public float getValueInSpecifiedUnits() {
        return valueInSpecifiedUnits;
    }

    /**
     * Sets the value of the length in its specified units.
     * 
     * @param valueInSpecifiedUnits
     *            the value of the length, in specified units
     */
    public void setValueInSpecifiedUnits(float valueInSpecifiedUnits) {
        this.valueInSpecifiedUnits = valueInSpecifiedUnits;
    }

    /**
     * Gets the specified units of the length.
     * 
     * @return the unitType
     */
    public int getUnitType() {
        return unitType;
    }

    /**
     * Sets the specified units of the length, while maintaining the same actual
     * value in user units
     * 
     * @param unitType
     *            the unitType to set
     * @throws DOMException
     *             if the specified units are incomplete, or if the length is
     *             not situated within a viewport
     */
    public void convertToSpecifiedUnits(int unitType) {
        float value = getValue();
        this.unitType = unitType;
        setValue(value);
    }

    /**
     * Sets a new value for the length in the given units.
     * 
     * @param unitType
     *            the specified units of the length
     * @param valueInSpecifiedUnits
     *            the value of the length in the specified units
     */
    public void newValueSpecifiedUnits(int unitType, float valueInSpecifiedUnits) {
        this.unitType = unitType;
        this.valueInSpecifiedUnits = valueInSpecifiedUnits;
    }

    /**
     * Gets the value as a string, in SVG length format.
     * 
     * @return a string representing the length
     */
    public String valueAsString() {
        String result = Float.toString(valueInSpecifiedUnits);

        switch (unitType) {
        case SVG_LENGTHTYPE_UNKNOWN:
            return "";
        case SVG_LENGTHTYPE_NUMBER:
            return result;
        case SVG_LENGTHTYPE_PERCENTAGE:
            return result + "%";
        case SVG_LENGTHTYPE_EMS:
            return result + "em";
        case SVG_LENGTHTYPE_EXS:
            return result = "ex";
        case SVG_LENGTHTYPE_PX:
            return result + "px";
        case SVG_LENGTHTYPE_CM:
            return result + "cm";
        case SVG_LENGTHTYPE_MM:
            return result + "mm";
        case SVG_LENGTHTYPE_IN:
            return result + "in";
        case SVG_LENGTHTYPE_PT:
            return result + "pt";
        case SVG_LENGTHTYPE_PC:
            return result + "pc";
        default:
            throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Invalid unit type");
        }
    }

    private static Pattern stringPattern = Pattern.compile("(.*)(em|ex|px|in|cm|mm|pt|pc|%)");

    /**
     * Sets a new value for the length by parsing the string.
     * 
     * The string must specify a length as defined by the SVG length data type.
     * 
     * @param s
     *            the string to parse
     * @throws NumberFormatException
     *             if the string is not a valid length
     */
    public void setValueFromString(String s) throws NumberFormatException {
        // If there's an exception while parsing, leave unit type
        // undefined.
        unitType = SVG_LENGTHTYPE_UNKNOWN;

        // Try to parse unit type and value part of string
        Matcher match = stringPattern.matcher(s);
        if (!match.matches()) {
            // No unit, so try to parse as a number
            valueInSpecifiedUnits = Float.parseFloat(s);
            unitType = SVG_LENGTHTYPE_NUMBER;
            return;
        }

        // Try to convert value
        String valueString = match.group(1);
        valueInSpecifiedUnits = Float.parseFloat(valueString);

        // Parse unit type was sucessful, convert to unit type enum
        String unitString = match.group(2);
        if (unitString.equals("em"))
            unitType = SVG_LENGTHTYPE_EMS;
        else if (unitString.equals("ex"))
            unitType = SVG_LENGTHTYPE_EXS;
        else if (unitString.equals("px"))
            unitType = SVG_LENGTHTYPE_PX;
        else if (unitString.equals("in"))
            unitType = SVG_LENGTHTYPE_IN;
        else if (unitString.equals("cm"))
            unitType = SVG_LENGTHTYPE_CM;
        else if (unitString.equals("mm"))
            unitType = SVG_LENGTHTYPE_MM;
        else if (unitString.equals("pt"))
            unitType = SVG_LENGTHTYPE_PT;
        else if (unitString.equals("pc"))
            unitType = SVG_LENGTHTYPE_PC;
        else if (unitString.equals("%"))
            unitType = SVG_LENGTHTYPE_PERCENTAGE;
        else
            throw new RuntimeException("Unexpected unit string");
    }

    /**
     * Copies unit and value from another length into this length.
     * 
     * @param length
     *            the length from which to copy unit and value
     */
    public void setValueFromLength(SVGLength length) {
        newValueSpecifiedUnits(length.getUnitType(), length.getValueInSpecifiedUnits());
    }

    private float getUserUnitsPerSpecifiedUnits() {
        if (viewport == null)
            throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Length is not situated within a viewport");

        // Unit conversions defined in "CSS3 Values and Units"
        // Section 3.4.3 Absolute unit lengths
        // http://www.w3.org/TR/css3-values/

        switch (unitType) {
        case SVG_LENGTHTYPE_UNKNOWN:
            throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Unknown unit type");
        case SVG_LENGTHTYPE_NUMBER:
            return 1.0f;
        case SVG_LENGTHTYPE_PERCENTAGE:
            return getViewportSize() / 100.0f;
        case SVG_LENGTHTYPE_EMS:
            // TODO: requires font size.  Assume arbitrarily that default font size is 10px
            return 10.0f;
        case SVG_LENGTHTYPE_EXS:
            // TODO: requires font size.  Assume arbitrarily that default font size is 10px
            return 10.0f / 2.0f;
        case SVG_LENGTHTYPE_PX:
            return 1.0f;
        case SVG_LENGTHTYPE_CM:
            return getViewportDPI() / 2.54f;
        case SVG_LENGTHTYPE_MM:
            return getViewportDPI() / 25.4f;
        case SVG_LENGTHTYPE_IN:
            return getViewportDPI();
        case SVG_LENGTHTYPE_PT:
            return getViewportDPI() / 72.0f;
        case SVG_LENGTHTYPE_PC:
            return getViewportDPI() / 72.0f / 12.0f;
        default:
            throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Invalid unit type");
        }

    }

    /**
     * Gets the size of the viewport along this length's dimension.
     * 
     * Specified by 7.10 Units
     * 
     * @return the dimension of the viewport to measure this length against
     */
    private float getViewportSize() {
        float viewportWidth = viewport.getViewportWidth();
        float viewportHeight = viewport.getViewportHeight();

        switch (dimensionType) {
        case SVG_DIMENSION_UNKNOWN:
            throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Unknown dimension type");
        case SVG_DIMENSION_X:
            return viewportWidth;
        case SVG_DIMENSION_Y:
            return viewportHeight;
        case SVG_DIMENSION_OTHER:
            return (float) (Math.sqrt(viewportWidth * viewportWidth + viewportHeight * viewportHeight) / Math.sqrt(2));
        default:
            throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Unknown dimension type");
        }
    }

    private float getViewportDPI() {
        // This could be defined by the viewport, or the document; SVG
        // does not specify. We use the default value on OS X and
        // Windows.
        return 96.0f;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof SVGLength) {
            SVGLength otherLength = (SVGLength) other;
            return otherLength.unitType == unitType && otherLength.valueInSpecifiedUnits == valueInSpecifiedUnits;
        }
        return super.equals(other);
    }

}
