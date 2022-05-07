package rahmlab.ui.components;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import rahmlab.ui.DrawPointsListener;

public class FiveTimesFivePanel extends JPanel
{
	public static final int BOXES_COUNT = 25;
	
	private KeyPointCheckPanel[] boxes = new KeyPointCheckPanel[BOXES_COUNT];
	private DrawPointsListener listener;
	
	public FiveTimesFivePanel()
	{
		setBorder(BorderFactory.createTitledBorder("Keypoints Body_25"));
		setLayout(new GridLayout(5, 5));
		
		for (int i = 0; i < boxes.length; i++)
		{
			KeyPointCheckPanel box = new KeyPointCheckPanel(i);
			box.addActionListener(e -> box_checked());
			add(box);
			boxes[i] = box;
		} 
	}
	
	public void addDrawPointsListener(DrawPointsListener listener)
	{
		this.listener = listener;
	}
	
	public void setSelected(int keypointBoxIndex, boolean b)
	{
		boxes[keypointBoxIndex].setSelected(b);
	}
	
	public boolean isSelected(int keypointBoxIndex)
	{
		return boxes[keypointBoxIndex].isSelected();
	}
	
	public int length()
	{
		return boxes.length;
	}
	
	public void setColors(Color[] colors)
	{
		if (colors == null || colors.length != boxes.length)
			return;
		for (int i = 0; i < boxes.length; i++)
		{
			boxes[i].setColor(colors[i]);
		}
	}
	
	public void box_checked()
	{
		if (listener != null)
		{
			List<Integer> selectedKeypoints = new ArrayList<Integer>();
			for (int i = 0; i < boxes.length; i++)
			{
				if (boxes[i].isSelected())
				{
					selectedKeypoints.add(i);
				}
			}
			listener.drawPoints(selectedKeypoints);
		}
	}
}
