package rahmlab.ui.graph;

import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import rahmlab.datatype.FrameData;
import rahmlab.datatype.Point;
import rahmlab.ui.ResizeListener;

public abstract class Graph extends JPanel implements ResizeListener
{
	protected Point upperLeftCorner;
	protected Point upperRightCorner;
	protected Point downLeftCorner;
	protected Point downRightCorner;
	protected int height;
	protected int width;
	protected final int SPACING = 20;
	private String xAxisName = "X";
	private String yAxisName = "Y";
	private List<Point> xPoints = new ArrayList<Point>();
	private List<Point> yPoints = new ArrayList<Point>();
	
	public Graph()
	{
		
	}
	
	public void initialize()
	{
		this.width = getWidth();
		this.height = getHeight();
		
		upperLeftCorner = new Point(this.getX(), this.getY(), 0);
		upperRightCorner = new Point(this.getX(), this.getY() + width, 0);
		downLeftCorner = new Point(this.getX() + height, this.getY(), 0);
		downRightCorner = new Point(this.getX() + height, this.getY() + width, 0);
	}
	
	protected int x(int value)
	{
		return this.getX() + value;
	}
	
	protected int y(int value)
	{
		return this.getY() + value;
	}
	
	protected List<Point> getXPoints()
	{
		return xPoints;
	}
	
	protected List<Point> getYPoints()
	{
		return yPoints;
	}
	
	public void labelTheAxis(String xAxisName, String yAxisName)
	{
		this.xAxisName = xAxisName;
		this.yAxisName = yAxisName;
	}
	
	public void sizeTheAxis(int countX, int countY)
	{
		sizeTheXAxis(countX);
		sizeTheYAxis(countY);
	}
	
	public int sizeTheXAxis(int countX)
	{
		if (countX > 0)
		{
			xPoints = new ArrayList<Point>();
			int distanceX = (width - 2 * SPACING) / countX;
			int y = height - SPACING;
			for (int i = 1; i <= countX; i++)
			{
				int x = SPACING + i * distanceX;
				xPoints.add(new Point(x, y, 0));
			}
			return distanceX;
		}
		return -1;
	}
	
	public int sizeTheYAxis(int countY)
	{
		if (countY > 0)
		{
			yPoints = new ArrayList<Point>();
			int distanceY = (height - 2 * SPACING) / countY;
			int x = SPACING;
			for (int i = 1; i <= countY; i++)
			{
				int y = height - SPACING - distanceY * i;
				yPoints.add(new Point(x, y, 0));
			}
			return distanceY;
		}
		return -1;
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.clearRect(0, 0, width, height);
		g.setFont(new Font(Font.DIALOG, Font.ITALIC, 8));
		
		//y-Axis
		g.drawLine(SPACING, SPACING, SPACING, height - SPACING);
		g.drawLine(SPACING + SPACING / 2, SPACING + SPACING / 2, SPACING, SPACING);
		g.drawLine(SPACING / 2, SPACING + SPACING / 2, SPACING, SPACING);
		g.drawString(yAxisName, SPACING / 2, SPACING / 2);
		for (int i = 0; i < yPoints.size(); i++)
		{
			Point p = yPoints.get(i);
			int y = (int)p.getY();
			int xS = (int)p.getX() - SPACING / 2;
			int x1 = (int)p.getX() - SPACING / 4;
			int x2 = (int)p.getX() + SPACING / 4;
			g.drawLine(x1, y, x2, y);
			g.drawString("" + (i + 1), xS, y);
		}
		
		//x-Axis
		g.drawLine(SPACING, height - SPACING, width - SPACING, height - SPACING);
		g.drawLine(width - SPACING - SPACING / 2, height - SPACING - SPACING / 2, width - SPACING, height - SPACING);
		g.drawLine(width - SPACING - SPACING / 2, height - SPACING / 2, width - SPACING, height - SPACING);
		g.drawString(xAxisName, width - SPACING, height - SPACING / 2);
		for (int i = 0; i < xPoints.size(); i++)
		{
			Point p = xPoints.get(i);
			int yS = (int)p.getY() + SPACING / 2;
			int y1 = (int)p.getY() - SPACING / 4;
			int y2 = (int)p.getY() + SPACING / 4;
			int x = (int)p.getX();
			g.drawLine(x, y1, x, y2);
			g.drawString("" + (i + 1), x, yS);
		}
	}
	
	//ResizeListener
	public void resized()
	{
		this.width = getWidth();
		this.height = getHeight();
	}
	
	public abstract void addFrames(List<FrameData> frames);
	
	public abstract void drawPoints(List<Integer> pointsIndex);
}