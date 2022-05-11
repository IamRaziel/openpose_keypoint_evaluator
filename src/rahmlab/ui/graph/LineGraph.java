package rahmlab.ui.graph;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import rahmlab.datatype.FrameData;
import rahmlab.datatype.Point;

public class LineGraph extends Graph
{
	
	public LineGraph()
	{
		super();
	}

	@Override
	public void setFrames(List<FrameData> frames) 
	{
		super.frames = frames;
		sizeTheXAxis(frames.size());
	}

	@Override
	public void drawPoints(List<Integer> pointsIndex) 
	{
		super.pointsIndex = pointsIndex;
		sizeTheYAxis(pointsIndex.size());
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		paintFrames(g);
	}
	
	private void paintFrames(Graphics g)
	{
		Point p1 = null;
		Point p2 = null;
		for (int i = 0; i < frames.size(); i++)
		{
			p1 = p2;
			p2 = calcPointNextFrame(i);
			connectTwoPointsWithLine(g, p1, p2);
		}
	}
	
	private void connectTwoPointsWithLine(Graphics g, Point p1, Point p2)
	{
		if (p1 == null || p2 == null)
		{
			return;
		}
		
		g.setColor(Color.BLACK);
		g.drawLine((int)p1.getX(), (int)p1.getY(), (int)p2.getX(), (int)p2.getY());
	}
	
	private Point calcPointNextFrame(int frameIndex)
	{
		int yCount = countDetectedKeypoints(frames.get(frameIndex));
		int xCount = frameIndex;
		
		if (yCount == -1 || pointsIndex.size() == 0)
		{
			return null;
		}
		
		Point xP = super.getXPoints().get(xCount);
		Point yP = super.getYPoints().get(yCount > 0 ? yCount - 1 : 0);
		return new Point(xP.getX(), yP.getY(), 0);
	}
	
	private int countDetectedKeypoints(FrameData frame)
	{
		int c = -1;
		if (frame != null)
		{
			c = 0;
			if (frame.getPeople().size() > 0)
			{
				Point[] points = frame.getPeople().get(0).getPoseKeypoints_2d();
				for (int i : pointsIndex)
				{
					c += points[i].isNotNullValue() ? 1 : 0;
				}
			}
		}
		return c;
	}
}
