package rahmlab.datatype;

import java.awt.Color;

public class KeyPoint extends Point
{
	private Color color;

	public KeyPoint(Color color, Point point) 
	{
		this(color, point.getX(), point.getY(), point.getZ());
	}
	
	public KeyPoint(Color color, double d, double e, double f)
	{
		super(d, e, f);
		this.color = color;
	}
	
	public Color getColor()
	{
		return color;
	}
}
