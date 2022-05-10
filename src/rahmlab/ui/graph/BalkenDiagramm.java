package rahmlab.ui.graph;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import rahmlab.datatype.FrameData;
import rahmlab.datatype.KeyPoint;
import rahmlab.datatype.PersonKeypoints;
import rahmlab.datatype.Point;

public class BalkenDiagramm extends Graph
{
	private int distanceY = -1;
	
	public BalkenDiagramm()
	{
		super();
	}
	
	@Override
	public void setFrames(List<FrameData> frames)
	{
		this.frames = frames;
		sizeTheXAxis(frames.size());
	}

	@Override
	public void drawPoints(List<Integer> pointsIndex) 
	{
		distanceY = sizeTheYAxis(pointsIndex.size());
		this.pointsIndex = pointsIndex;
		repaint();
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		for (int i = 0; i < frames.size(); i++)
		{
			paintFrame(g, frames.get(i), getXPoints().get(i));
		}
	}
	
	public void paintFrame(Graphics g, FrameData frame, Point xAxisPoint)
	{
		if (frame.getPeople().size() > 0 && distanceY != -1)
		{
			PersonKeypoints person = frame.getPeople().get(0);
			KeyPoint[] keyPoints = person.getPoseKeypoints_2d();
			int counts = 0;
			for (int i = 0; i < pointsIndex.size(); i++)
			{
				KeyPoint point = keyPoints[pointsIndex.get(i)];
				if (point.isNotNullValue())
				{
					int x = (int)xAxisPoint.getX();
					int yAxis = (int)xAxisPoint.getY() - counts * distanceY;
					g.setColor(Color.BLACK);
					g.drawRect(x - 2, yAxis - distanceY, 5, distanceY);
					g.setColor(point.getColor());
					g.fillRect(x - 2, yAxis - distanceY, 5, distanceY);
					counts++;
				}
			}
			g.setColor(Color.BLACK);
		}
	}
}
