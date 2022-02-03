package GemCreator;

import Characters.Type.ColorType;

import java.awt.*;

public class Gem
{
    private ColorType type;

    private static final int SCALE_RATE = 8;
    private int dim;

    // Top left point of the gem
    private Point point;
    private Image style;
    private Image selectedStyle;
    private Image defaultStyle;

    public Gem(Image defaultStyle, Image selectedStyle, Point point, int scale, ColorType type)
    {
        this.type = type;
        this.dim = scale;
        this.style = defaultStyle;
        this.defaultStyle = defaultStyle;
        this.selectedStyle = selectedStyle;
        this.point = point;
    }

    public int getDim() {
        return dim;
    }

    public Image getStyle() {
        return style;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point p) { point.setLocation(p);}

    public void Select()
    {
        this.style = selectedStyle;
    }

    public void UnSelect()
    {
        this.style = defaultStyle;
    }

    public void scaleDown()
    {
        dim -= SCALE_RATE;
        if (dim > 0)
        {
            defaultStyle = defaultStyle.getScaledInstance(dim, dim, Image.SCALE_AREA_AVERAGING);
            style = style.getScaledInstance(dim, dim, Image.SCALE_AREA_AVERAGING);
            selectedStyle = selectedStyle.getScaledInstance(dim, dim, Image.SCALE_AREA_AVERAGING);

            Point p = this.getPoint();
            this.getPoint().setLocation(p.x+(SCALE_RATE/2), p.y+(SCALE_RATE/2));
        }

        MediaTracker tracker = new MediaTracker(new java.awt.Container());
        tracker.addImage(style, 0);
        try {
            tracker.waitForAll();
        } catch (InterruptedException ex) {
            throw new RuntimeException("Image loading interrupted", ex);
        }
    }

    public ColorType getType() {
        return type;
    }

    @Override
    public boolean equals(Object obj)
    {
        return this.type == ((Gem)obj).type;
    }
}
