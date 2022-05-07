package rahmlab.datatype;

import java.awt.Color;

public class KeyPoint extends Point
{
	private Color color;

	public KeyPoint(Color color, Point point) 
	{
		this(color, point.getX(), point.getY(), point.getZ());
	}
	
	public KeyPoint(Color color, double x, double y, double z)
	{
		super(x, y, z);
		this.color = color;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public boolean isNotNullValue()
	{
		return getX() != 0 || getY() != 0 || getZ() != 0;
	}
	
	@Override
	public KeyPoint clone()
	{
		return new KeyPoint(color, getX(), getY(), getZ());
	}
}
