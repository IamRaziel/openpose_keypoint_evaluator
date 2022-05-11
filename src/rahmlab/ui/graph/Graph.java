package rahmlab.ui.graph;

import java.awt.Color;
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
	protected List<FrameData> frames = new ArrayList<FrameData>();
	protected List<Integer> pointsIndex = new ArrayList<Integer>();
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
	
	public List<FrameData> getFrames()
	{
		return new ArrayList<FrameData>(frames);
	}
	
	public List<Integer> getSelectedPoints()
	{
		return new ArrayList<Integer>(pointsIndex);
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
		
		paintVisibilityPercentage(g);
		
		g.setFont(new Font(Font.DIALOG, Font.ITALIC, 8));
		
		//y-Axis
		g.drawLine(SPACING, SPACING, SPACING, height - SPACING);
		g.drawLine(SPACING + SPACING / 2, SPACING + SPACING / 2, SPACING, SPACING);
		g.drawLine(SPACING / 2, SPACING + SPACING / 2, SPACING, SPACING);
		g.drawString(yAxisName, SPACING / 2, SPACING / 2);
		drawStepsOnYAxis(g);
		
		//x-Axis
		g.drawLine(SPACING, height - SPACING, width - SPACING, height - SPACING);
		g.drawLine(width - SPACING - SPACING / 2, height - SPACING - SPACING / 2, width - SPACING, height - SPACING);
		g.drawLine(width - SPACING - SPACING / 2, height - SPACING / 2, width - SPACING, height - SPACING);
		g.drawString(xAxisName, width - SPACING, height - SPACING / 2);
		drawStepsOnXAxis(g);
	}
	
	private void paintVisibilityPercentage(Graphics g) 
	{
		double percent = calcAverageVisibilityOfTheSelectedKeypoints();
		if (percent > 0)
		{
			g.setColor(Color.BLACK);
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 12));
			g.drawString("Keypoints Visible: " + percent + "%", width - 14 * SPACING, SPACING * 4 / 5);
		}
	}

	private void drawStepsOnYAxis(Graphics g)
	{
		int iDiff = 1;
		if (yPoints.size() >= 500)
		{
			iDiff = 50;
		}
		if (yPoints.size() >= 200)
		{
			iDiff = 20;
		}
		else if (yPoints.size() >= 100)
		{
			iDiff = 10;
		}
		else if (yPoints.size() >= 50)
		{
			iDiff = 5;
		}
		
		for (int i = 0; i < yPoints.size(); i+=iDiff)
		{
			Point p = yPoints.get(i);
			int y = (int)p.getY();
			int xS = (int)p.getX() - SPACING / 2;
			int x1 = (int)p.getX() - SPACING / 4;
			int x2 = (int)p.getX() + SPACING / 4;
			g.drawLine(x1, y, x2, y);
			g.drawString("" + (i + 1), xS, y);
			if (i == 0 && iDiff > 1) i--;
		}
	}
	
	private void drawStepsOnXAxis(Graphics g)
	{
		int iDiff = 1;
		if (xPoints.size() >= 5000)
		{
			iDiff = 100;
		}
		else if (xPoints.size() >= 1000)
		{
			iDiff = 50;
		}
		else if (xPoints.size() >= 200)
		{
			iDiff = 10;
		}
		else if (xPoints.size() >= 100)
		{
			iDiff = 5;
		}
		
		
		for (int i = 0; i < xPoints.size(); i+=iDiff)
		{
			Point p = xPoints.get(i);
			int yS = (int)p.getY() + SPACING / 2;
			int y1 = (int)p.getY() - SPACING / 4;
			int y2 = (int)p.getY() + SPACING / 4;
			int x = (int)p.getX();
			g.drawLine(x, y1, x, y2);
			g.drawString("" + (i + 1), x, yS);
			if (i == 0 && iDiff > 1) i--;
		}
	}
	
	//ResizeListener
	public void resized()
	{
		this.width = getWidth();
		this.height = getHeight();
	}
	
	public double calcAverageVisibilityOfTheSelectedKeypoints()
	{
		if (pointsIndex.size() == 0) return 0;
		
		double countPoints = pointsIndex.size();
		double counterPercent = 0;
		for (int index : pointsIndex)
		{
			counterPercent += calcVisibilityOfKeypoint(index);
		}
		double percentTimes100 = (counterPercent / countPoints) * 100 * 100;
		int roundedTwoAfterComma = (int)(percentTimes100 + 0.5);
		double percent = ((double)roundedTwoAfterComma) / 100;
		return percent;
	}
	
	public double calcVisibilityOfKeypoint(int keypointIndex)
	{
		if (frames.size() == 0) return 0;
		
		double c = 0;
		double n = frames.size();
		
		for (FrameData frame : frames)
		{
			if (frame.getPeople() != null && frame.getPeople().size() > 0)
			{
				Point p = frame.getPeople().get(0).getPoseKeypoints_2d()[keypointIndex];
				c += p.isNotNullValue() ? 1 : 0;
			}
				
		}
		return c / n;
	}
	
	public abstract void setFrames(List<FrameData> frames);
	
	public abstract void drawPoints(List<Integer> pointsIndex);
}
