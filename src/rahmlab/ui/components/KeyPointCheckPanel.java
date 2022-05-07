package rahmlab.ui.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

public class KeyPointCheckPanel extends JPanel
{
	private int keyPointNumber;
	private Color color = null;
	
	private JCheckBox box;
	private JPanel colorBox;
	
	public KeyPointCheckPanel(int keypointNumber)
	{
		this.keyPointNumber = keypointNumber;
		setLayout(new GridLayout(0, 2));
		box = new JCheckBox("" + keyPointNumber);
		add(box);
		colorBox = new JPanel();
		add(colorBox);
	}
	
	public void setColor(Color color)
	{
		this.color = color;
		repaint();
	}
	
	public void addActionListener(ActionListener listener)
	{
		box.addActionListener(listener);
	}
	
	public void paintComponent(Graphics g)
	{
		if (color != null)
		{
			g.setColor(color);
			g.fillRect(50, 8, 10, 10);
			colorBox.repaint();
			g.setColor(Color.black);
			repaint();
		}	
	}
	
	public boolean isSelected()
	{
		return box.isSelected();
	}
	
	public void setSelected(boolean b)
	{
		box.setSelected(b);
	}
}
