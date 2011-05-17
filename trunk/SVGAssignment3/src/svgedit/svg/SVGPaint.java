package svgedit.svg;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Defines the type used for 'fill' and 'stroke' properties.
 *
 * Supports only 'none' and 'rgbcolor' paint types.
 *
 * Specified by 11.9.1 Interface SVGPaint.
 *
 */
public class SVGPaint {

    // Paint Types
    public static final int SVG_PAINTTYPE_UNKNOWN = 0;
    public static final int SVG_PAINTTYPE_RGBCOLOR = 1;
    public static final int SVG_PAINTTYPE_RGBCOLOR_ICCCOLOR = 2;
    public static final int SVG_PAINTTYPE_NONE = 101;
    public static final int SVG_PAINTTYPE_CURRENTCOLOR = 102;
    public static final int SVG_PAINTTYPE_URI_NONE = 103;
    public static final int SVG_PAINTTYPE_URI_CURRENTCOLOR = 104;
    public static final int SVG_PAINTTYPE_URI_RGBCOLOR = 105;
    public static final int SVG_PAINTTYPE_URI_RGBCOLOR_ICCCOLOR = 106;
    public static final int SVG_PAINTTYPE_URI = 107;

    private static final Map<String, Color> colorKeywords;
    static {
        // Section 4.4 Recognized color keyword names
        colorKeywords = new HashMap<String, Color>();
        colorKeywords.put("aliceblue", new Color(240, 248, 255));
        colorKeywords.put("antiquewhite", new Color(250, 235, 215));
        colorKeywords.put("aqua", new Color(0, 255, 255));
        colorKeywords.put("aquamarine", new Color(127, 255, 212));
        colorKeywords.put("azure", new Color(240, 255, 255));
        colorKeywords.put("beige", new Color(245, 245, 220));
        colorKeywords.put("bisque", new Color(255, 228, 196));
        colorKeywords.put("black", new Color(0, 0, 0));
        colorKeywords.put("blanchedalmond", new Color(255, 235, 205));
        colorKeywords.put("blue", new Color(0, 0, 255));
        colorKeywords.put("blueviolet", new Color(138, 43, 226));
        colorKeywords.put("brown", new Color(165, 42, 42));
        colorKeywords.put("burlywood", new Color(222, 184, 135));
        colorKeywords.put("cadetblue", new Color(95, 158, 160));
        colorKeywords.put("chartreuse", new Color(127, 255, 0));
        colorKeywords.put("chocolate", new Color(210, 105, 30));
        colorKeywords.put("coral", new Color(255, 127, 80));
        colorKeywords.put("cornflowerblue", new Color(100, 149, 237));
        colorKeywords.put("cornsilk", new Color(255, 248, 220));
        colorKeywords.put("crimson", new Color(220, 20, 60));
        colorKeywords.put("cyan", new Color(0, 255, 255));
        colorKeywords.put("darkblue", new Color(0, 0, 139));
        colorKeywords.put("darkcyan", new Color(0, 139, 139));
        colorKeywords.put("darkgoldenrod", new Color(184, 134, 11));
        colorKeywords.put("darkgray", new Color(169, 169, 169));
        colorKeywords.put("darkgreen", new Color(0, 100, 0));
        colorKeywords.put("darkgrey", new Color(169, 169, 169));
        colorKeywords.put("darkkhaki", new Color(189, 183, 107));
        colorKeywords.put("darkmagenta", new Color(139, 0, 139));
        colorKeywords.put("darkolivegreen", new Color(85, 107, 47));
        colorKeywords.put("darkorange", new Color(255, 140, 0));
        colorKeywords.put("darkorchid", new Color(153, 50, 204));
        colorKeywords.put("darkred", new Color(139, 0, 0));
        colorKeywords.put("darksalmon", new Color(233, 150, 122));
        colorKeywords.put("darkseagreen", new Color(143, 188, 143));
        colorKeywords.put("darkslateblue", new Color(72, 61, 139));
        colorKeywords.put("darkslategray", new Color(47, 79, 79));
        colorKeywords.put("darkslategrey", new Color(47, 79, 79));
        colorKeywords.put("darkturquoise", new Color(0, 206, 209));
        colorKeywords.put("darkviolet", new Color(148, 0, 211));
        colorKeywords.put("deeppink", new Color(255, 20, 147));
        colorKeywords.put("deepskyblue", new Color(0, 191, 255));
        colorKeywords.put("dimgray", new Color(105, 105, 105));
        colorKeywords.put("dimgrey", new Color(105, 105, 105));
        colorKeywords.put("dodgerblue", new Color(30, 144, 255));
        colorKeywords.put("firebrick", new Color(178, 34, 34));
        colorKeywords.put("floralwhite", new Color(255, 250, 240));
        colorKeywords.put("forestgreen", new Color(34, 139, 34));
        colorKeywords.put("fuchsia", new Color(255, 0, 255));
        colorKeywords.put("gainsboro", new Color(220, 220, 220));
        colorKeywords.put("ghostwhite", new Color(248, 248, 255));
        colorKeywords.put("gold", new Color(255, 215, 0));
        colorKeywords.put("goldenrod", new Color(218, 165, 32));
        colorKeywords.put("gray", new Color(128, 128, 128));
        colorKeywords.put("grey", new Color(128, 128, 128));
        colorKeywords.put("green", new Color(0, 128, 0));
        colorKeywords.put("greenyellow", new Color(173, 255, 47));
        colorKeywords.put("honeydew", new Color(240, 255, 240));
        colorKeywords.put("hotpink", new Color(255, 105, 180));
        colorKeywords.put("indianred", new Color(205, 92, 92));
        colorKeywords.put("indigo", new Color(75, 0, 130));
        colorKeywords.put("ivory", new Color(255, 255, 240));
        colorKeywords.put("khaki", new Color(240, 230, 140));
        colorKeywords.put("lavender", new Color(230, 230, 250));
        colorKeywords.put("lavenderblush", new Color(255, 240, 245));
        colorKeywords.put("lawngreen", new Color(124, 252, 0));
        colorKeywords.put("lemonchiffon", new Color(255, 250, 205));
        colorKeywords.put("lightblue", new Color(173, 216, 230));
        colorKeywords.put("lightcoral", new Color(240, 128, 128));
        colorKeywords.put("lightcyan", new Color(224, 255, 255));
        colorKeywords.put("lightgoldenrodyellow", new Color(250, 250, 210));
        colorKeywords.put("lightgray", new Color(211, 211, 211));
        colorKeywords.put("lightgreen", new Color(144, 238, 144));
        colorKeywords.put("lightgrey", new Color(211, 211, 211));
        colorKeywords.put("lightpink", new Color(255, 182, 193));
        colorKeywords.put("lightsalmon", new Color(255, 160, 122));
        colorKeywords.put("lightseagreen", new Color(32, 178, 170));
        colorKeywords.put("lightskyblue", new Color(135, 206, 250));
        colorKeywords.put("lightslategray", new Color(119, 136, 153));
        colorKeywords.put("lightslategrey", new Color(119, 136, 153));
        colorKeywords.put("lightsteelblue", new Color(176, 196, 222));
        colorKeywords.put("lightyellow", new Color(255, 255, 224));
        colorKeywords.put("lime", new Color(0, 255, 0));
        colorKeywords.put("limegreen", new Color(50, 205, 50));
        colorKeywords.put("linen", new Color(250, 240, 230));
        colorKeywords.put("magenta", new Color(255, 0, 255));
        colorKeywords.put("maroon", new Color(128, 0, 0));
        colorKeywords.put("mediumaquamarine", new Color(102, 205, 170));
        colorKeywords.put("mediumblue", new Color(0, 0, 205));
        colorKeywords.put("mediumorchid", new Color(186, 85, 211));
        colorKeywords.put("mediumpurple", new Color(147, 112, 219));
        colorKeywords.put("mediumseagreen", new Color(60, 179, 113));
        colorKeywords.put("mediumslateblue", new Color(123, 104, 238));
        colorKeywords.put("mediumspringgreen", new Color(0, 250, 154));
        colorKeywords.put("mediumturquoise", new Color(72, 209, 204));
        colorKeywords.put("mediumvioletred", new Color(199, 21, 133));
        colorKeywords.put("midnightblue", new Color(25, 25, 112));
        colorKeywords.put("mintcream", new Color(245, 255, 250));
        colorKeywords.put("mistyrose", new Color(255, 228, 225));
        colorKeywords.put("moccasin", new Color(255, 228, 181));
        colorKeywords.put("navajowhite", new Color(255, 222, 173));
        colorKeywords.put("navy", new Color(0, 0, 128));
        colorKeywords.put("oldlace", new Color(253, 245, 230));
        colorKeywords.put("olive", new Color(128, 128, 0));
        colorKeywords.put("olivedrab", new Color(107, 142, 35));
        colorKeywords.put("orange", new Color(255, 165, 0));
        colorKeywords.put("orangered", new Color(255, 69, 0));
        colorKeywords.put("orchid", new Color(218, 112, 214));
        colorKeywords.put("palegoldenrod", new Color(238, 232, 170));
        colorKeywords.put("palegreen", new Color(152, 251, 152));
        colorKeywords.put("paleturquoise", new Color(175, 238, 238));
        colorKeywords.put("palevioletred", new Color(219, 112, 147));
        colorKeywords.put("papayawhip", new Color(255, 239, 213));
        colorKeywords.put("peachpuff", new Color(255, 218, 185));
        colorKeywords.put("peru", new Color(205, 133, 63));
        colorKeywords.put("pink", new Color(255, 192, 203));
        colorKeywords.put("plum", new Color(221, 160, 221));
        colorKeywords.put("powderblue", new Color(176, 224, 230));
        colorKeywords.put("purple", new Color(128, 0, 128));
        colorKeywords.put("red", new Color(255, 0, 0));
        colorKeywords.put("rosybrown", new Color(188, 143, 143));
        colorKeywords.put("royalblue", new Color(65, 105, 225));
        colorKeywords.put("saddlebrown", new Color(139, 69, 19));
        colorKeywords.put("salmon", new Color(250, 128, 114));
        colorKeywords.put("sandybrown", new Color(244, 164, 96));
        colorKeywords.put("seagreen", new Color(46, 139, 87));
        colorKeywords.put("seashell", new Color(255, 245, 238));
        colorKeywords.put("sienna", new Color(160, 82, 45));
        colorKeywords.put("silver", new Color(192, 192, 192));
        colorKeywords.put("skyblue", new Color(135, 206, 235));
        colorKeywords.put("slateblue", new Color(106, 90, 205));
        colorKeywords.put("slategray", new Color(112, 128, 144));
        colorKeywords.put("slategrey", new Color(112, 128, 144));
        colorKeywords.put("snow", new Color(255, 250, 250));
        colorKeywords.put("springgreen", new Color(0, 255, 127));
        colorKeywords.put("steelblue", new Color(70, 130, 180));
        colorKeywords.put("tan", new Color(210, 180, 140));
        colorKeywords.put("teal", new Color(0, 128, 128));
        colorKeywords.put("thistle", new Color(216, 191, 216));
        colorKeywords.put("tomato", new Color(255, 99, 71));
        colorKeywords.put("turquoise", new Color(64, 224, 208));
        colorKeywords.put("violet", new Color(238, 130, 238));
        colorKeywords.put("wheat", new Color(245, 222, 179));
        colorKeywords.put("white", new Color(255, 255, 255));
        colorKeywords.put("whitesmoke", new Color(245, 245, 245));
        colorKeywords.put("yellow", new Color(255, 255, 0));
        colorKeywords.put("yellowgreen", new Color(154, 205, 50));
    }

    private int paintType;
    private Color rgbColor;

    /**
     * Not supported.
     *
     * @param uri
     * @throws UnsupportedOperationException
     */
    public void setUri(String uri) {
        throw new UnsupportedOperationException("not implemented");
    }

    /**
     * Sets the paint value.
     *
     * @param paintType
     *            type of paint value to set
     * @param uri
     *            not supported
     * @param rgbColor
     *            string representing RGB color, if type is
     *            SVG_PAINTTYPE_RGBCOLOR
     * @param iccColor
     *            not supported
     * @throws NumberFormatException
     *             if an unsupported paint type is specified
     */
    public void setPaint(int paintType, String uri, String rgbColor, String iccColor) throws NumberFormatException {
        switch (paintType) {
        case SVG_PAINTTYPE_NONE:
            this.paintType = paintType;
            this.rgbColor = null;
            break;
        case SVG_PAINTTYPE_RGBCOLOR:
            this.paintType = paintType;
            this.rgbColor = parseColor(rgbColor);
            break;
        default:
            throw new UnsupportedOperationException("this paint type is not supported");
        }
    }

    /**
     * Gets the type of paint represented.
     *
     * @return the paint type
     */
    public int getPaintType() {
        return paintType;
    }

    /**
     * Gets the RGB color represented by the paint.
     *
     * Returns {@literal null} if the paint type is not set to
     * {@link SVGPaint#SVG_PAINTTYPE_RGBCOLOR}.
     *
     * @return the RGB color
     */
    public Color getRGBColor() {
        return rgbColor;
    }

    /**
     * Sets the paint to the specified RGB color.
     *
     * If {@literal null} is given, the paint type is set to SVG_PAINTTYPE_NONE.
     *
     * @param color
     */
    public void setRGBColor(Color color) {
        this.rgbColor = color;
        if (color != null)
            this.paintType = SVG_PAINTTYPE_RGBCOLOR;
        else
            this.paintType = SVG_PAINTTYPE_NONE;
    }

    /**
     * Copies the paint value from another paint.
     *
     * @param paint
     *            the paint value to copy
     */
    public void setValueFromPaint(SVGPaint paint) {
        paintType = paint.paintType;
        rgbColor = paint.rgbColor;
    }

    /**
     * Sets the paint value from a string.
     *
     * The string must be one of
     * <ul>
     * <li>{@literal "none"}</li>
     * <li>a valid color literal defined in 4.2</li>
     * <li>a valid color keyword defined in 4.4</li>
     * </ul>
     *
     *
     * @param s
     *            the string to parse
     * @throws NumberFormatException
     *             if the string does not represent a paint
     */
    public void setValueFromString(String s) throws NumberFormatException {
        if (s.equals("none"))
            setRGBColor(null);
        else
            setRGBColor(parseColor(s));
    }

    /** Represents this paint as a string */
    public String valueAsString() {
        switch (paintType) {
        case SVG_PAINTTYPE_NONE:
            return "none";
        case SVG_PAINTTYPE_RGBCOLOR:
            return formatColor(rgbColor);
        default:
            throw new UnsupportedOperationException("this paint type is not supported");
        }
    }

    @Override
    public String toString() {
        return valueAsString();
    }

    private static final Pattern parseShortHexColorPattern = Pattern.compile("#(\\p{XDigit})(\\p{XDigit})(\\p{XDigit})");
    private static final Pattern parseHexColorPattern = Pattern.compile("#(\\p{XDigit}\\p{XDigit})(\\p{XDigit}\\p{XDigit})(\\p{XDigit}\\p{XDigit})");
    private static final Pattern parseDecimalColorPattern = Pattern.compile("rgb\\(\\s*(\\d+)\\s*,\\s*(\\d+)\\s*,\\s*(\\d+)\\s*\\)");
    private static final Pattern parseDecimalPercentColorPattern = Pattern.compile("rgb\\(\\s*([0-9.]+)%\\s*,\\s*([0-9.]+)%\\s*,\\s*([0-9.]+)%\\s*\\)");

    /**
     * Parses the given string as an RGB color
     *
     * The string must be one of
     * <ul>
     * <li>a valid color literal defined in 4.2</li>
     * <li>a valid color keyword defined in 4.4</li>
     * </ul>
     *
     * @param s
     *            the string to parse
     * @throws NumberFormatException
     *             if the string does not represent a color
     */
    private Color parseColor(String s) {
        // color keyword
        if (colorKeywords.containsKey(s))
            return colorKeywords.get(s);

        // 4.2 <color> basic type

        // #xxx
        Matcher match = parseShortHexColorPattern.matcher(s);
        if (match.matches())
            return parseHexColor(match, 1.0f / 15.0f);

        // #xxxxxx
        match = parseHexColorPattern.matcher(s);
        if (match.matches())
            return parseHexColor(match, 1.0f / 255.0f);

        // rgb(d, d, d)
        match = parseDecimalColorPattern.matcher(s);
        if (match.matches())
            return parseDecimalColor(match, 1.0f / 255.0f);

        // rgb(d%, d%, d%)
        match = parseDecimalPercentColorPattern.matcher(s);
        if (match.matches())
            return parseDecimalColor(match, 0.01f);

        throw new NumberFormatException("Invalid color specification: '" + s + "'");
    }

    private Color parseHexColor(Matcher match, float multiplier) {
        int r = Integer.parseInt(match.group(1), 16);
        int g = Integer.parseInt(match.group(2), 16);
        int b = Integer.parseInt(match.group(3), 16);
        return new Color(r * multiplier, g * multiplier, b * multiplier);
    }

    private Color parseDecimalColor(Matcher match, float multiplier) {
        float r = Float.parseFloat(match.group(1));
        float g = Float.parseFloat(match.group(2));
        float b = Float.parseFloat(match.group(3));
        return new Color(Math.min(r * multiplier, 1.0f),
                         Math.min(g * multiplier, 1.0f),
                         Math.min(b * multiplier, 1.0f));
    }

    private static String formatColor(Color color) {
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof SVGPaint) {
            SVGPaint otherPaint = (SVGPaint) other;
            return otherPaint.paintType == paintType && otherPaint.rgbColor == rgbColor || (otherPaint.rgbColor != null && otherPaint.rgbColor.equals(rgbColor));
        }
        return super.equals(other);
    }

}
