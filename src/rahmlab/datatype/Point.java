package rahmlab.datatype;

public class Point 
{
	private double x;
	private double y;
	private double z;
	
	public Point(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public double getX()
	{
		return x;
	}
	
	public double getY()
	{
		return y;
	}

	public double getZ()
	{
		return z;
	}
	
	public boolean isNotNullValue()
	{
		return getX() != 0 || getY() != 0 || getZ() != 0;
	}
	
	public Point clone()
	{
		return new Point(x, y, z);
	}
}
