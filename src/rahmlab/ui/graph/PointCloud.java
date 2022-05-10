package rahmlab.ui.graph;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import rahmlab.datatype.FrameData;
import rahmlab.datatype.KeyPoint;
import rahmlab.datatype.PersonKeypoints;
import rahmlab.datatype.Point;

public class PointCloud extends Graph
{
	private List<FrameData> frames;
	private final int POINT_DIAMETER = 8;
	
	public PointCloud()
	{
		super();
	}
	
	@Override
	public void setFrames(List<FrameData> frames) 
	{
		this.frames = frames;
		calcAndSetSizeAxis();
	}

	@Override
	public void drawPoints(List<Integer> pointsIndex) 
	{
		super.pointsIndex = pointsIndex;
		repaint();
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		frames.forEach(frame -> paintPointsOfFrame(g, frame));
	}
	
	private void paintPointsOfFrame(Graphics g, FrameData frame)
	{
		List<PersonKeypoints> people = frame.getPeople();
		if (people != null && people.size() > 0)
		{
			for (int i : pointsIndex)
			{
				KeyPoint point = people.get(0).getPoseKeypoints_2d()[i];
				paintKeyPoint(g, point);
			}
		}
	}
	
	private void paintKeyPoint(Graphics g, KeyPoint point)
	{
		g.setColor(point.getColor());
		Point pointX = getXPoints().get((int)point.getX());
		if (pointX.isNotNullValue())
		{
			int posX = (int)pointX.getX() - POINT_DIAMETER / 2;
			Point pointY = getYPoints().get((int)point.getY());
			int posY = (int)pointY.getY() - POINT_DIAMETER / 2;
			g.fillOval(posX, posY, POINT_DIAMETER, POINT_DIAMETER);
			g.setColor(Color.BLACK);
			g.drawOval(posX, posY, POINT_DIAMETER, POINT_DIAMETER);
		}
	}
	
	private void calcAndSetSizeAxis()
	{
		int x = 0;
		int y = 0;
		if (frames != null)
		{
			for (FrameData frame : frames)
			{
				Point p = calcBiggestPointOfFrame(frame);
				if ((int)p.getX() > x) x = (int)p.getX();
				if ((int)p.getY() > y) y = (int)p.getY();
			}
		}
		sizeTheAxis(x + 5, y + 5);
	}
	
	private Point calcBiggestPointOfFrame(FrameData frame)
	{
		List<PersonKeypoints> people = frame.getPeople();
		double x = 0;
		double y = 0;
		if (people != null && people.size() > 0)
		{
			for (KeyPoint point : people.get(0).getPoseKeypoints_2d())
			{
				if (point.getX() > x) x = point.getX();
				if (point.getY() > y) y = point.getY();
			}
		}
		return new Point(x, y, 0);
	}
	
	@Override
	public int sizeTheYAxis(int countY) 
	{
		int distanceY = super.sizeTheYAxis(countY);
		List<Point> points = getYPoints();
		for (int i = 0; i < points.size() / 2; i++)
		{
			Point point = points.get(i).clone();
			int j = points.size() - i - 1;
			points.set(i, points.get(j).clone());
			points.set(j, point);
		}
		return distanceY;
	}
}
