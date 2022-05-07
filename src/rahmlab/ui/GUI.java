package rahmlab.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import rahmlab.datatype.FrameData;
import rahmlab.ui.graph.BalkenDiagramm;
import rahmlab.ui.graph.Graph;

public class GUI extends JFrame
{
	private final int X = 1200;
	private final int Y = 800;
	
	private List<ResizeListener> resizables = new ArrayList<ResizeListener>();
	private JCheckBox[] diagramTypeBoxes = new JCheckBox[4];
	private JCheckBox[] fiveTimeFiveBoxes = new JCheckBox[25];
	private Graph graph;
	
	public GUI()
	{
		setSize(X, Y);
		setLayout(new BorderLayout()); 
		add(createHeadPanel(), BorderLayout.PAGE_START);
		
		graph = new BalkenDiagramm();
		resizables.add(graph);
		
		add(graph, BorderLayout.CENTER);
		//in this order, so the graph gets an size
		setVisible(true);
		graph.initialize();
		
		this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
            	resizables.forEach(a -> a.resized());
            }
        });
	}
	
	private JPanel createHeadPanel()
	{
		JPanel headPanel = new JPanel();
		headPanel.setLayout(new GridLayout(0, 2));
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new GridLayout(0, 2));
		
		leftPanel.add(createDiagrammTypePanel());
		leftPanel.add(createKeypointPartBoxes());
		
		headPanel.add(leftPanel);
		headPanel.add(createKeypointsCheckPanel());
		
		return headPanel;
	}
	
	private JPanel createDiagrammTypePanel()
	{
		JPanel panel = new JPanel();
		
		GridLayout layout = new GridLayout(4, 0);
		panel.setLayout(layout);
		panel.setBorder(BorderFactory.createTitledBorder("Diagramm Type"));
		
		JCheckBox boxGraphOne = new JCheckBox();
		boxGraphOne.setLabel("One");
		boxGraphOne.addActionListener(e -> diagramTypeBox_checked(boxGraphOne.isSelected(), 0));
		JCheckBox boxGraphTwo = new JCheckBox();
		boxGraphTwo.setLabel("Two");
		boxGraphTwo.addActionListener(e -> diagramTypeBox_checked(boxGraphTwo.isSelected(), 1));
		JCheckBox boxGraphThree = new JCheckBox();
		boxGraphThree.setLabel("Three");
		boxGraphThree.addActionListener(e -> diagramTypeBox_checked(boxGraphThree.isSelected(), 2));
		JCheckBox boxGraphFour = new JCheckBox();
		boxGraphFour.setLabel("Four");
		boxGraphFour.addActionListener(e -> diagramTypeBox_checked(boxGraphFour.isSelected(), 3));
		
		diagramTypeBoxes[0] = boxGraphOne;
		diagramTypeBoxes[1] = boxGraphTwo;
		diagramTypeBoxes[2] = boxGraphThree;
		diagramTypeBoxes[3] = boxGraphFour;
		
		panel.add(boxGraphOne);
		panel.add(boxGraphTwo);
		panel.add(boxGraphThree);
		panel.add(boxGraphFour);
		
		return panel;
	}
	
	private JPanel createKeypointPartBoxes()
	{
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Combined Keypoint Parts"));
		panel.setLayout(new GridLayout(4, 2));
		
		JCheckBox headBox = new JCheckBox("Head");
		headBox.addActionListener(e -> combinedKeypointBoxes_checked(headBox.isSelected(), 0, 15, 16, 17, 18));
		JCheckBox spineBox = new JCheckBox("Spine");
		spineBox.addActionListener(e -> combinedKeypointBoxes_checked(spineBox.isSelected(), 1, 8));
		JCheckBox leftArmBox = new JCheckBox("Left Arm");
		leftArmBox.addActionListener(e -> combinedKeypointBoxes_checked(leftArmBox.isSelected(), 5, 6, 7));
		JCheckBox rightArmBox = new JCheckBox("Right Arm");
		rightArmBox.addActionListener(e -> combinedKeypointBoxes_checked(rightArmBox.isSelected(), 2, 3, 4));
		JCheckBox leftLegBox = new JCheckBox("Left Leg");
		leftLegBox.addActionListener(e -> combinedKeypointBoxes_checked(leftLegBox.isSelected(), 9, 10, 11));
		JCheckBox rightLegBox = new JCheckBox("Right Leg");
		rightLegBox.addActionListener(e -> combinedKeypointBoxes_checked(rightLegBox.isSelected(), 12, 13, 14));
		JCheckBox leftFootBox = new JCheckBox("Left Foot");
		leftFootBox.addActionListener(e -> combinedKeypointBoxes_checked(leftFootBox.isSelected(), 22, 23, 24));
		JCheckBox rightFootBox = new JCheckBox("Right Foot");
		rightFootBox.addActionListener(e -> combinedKeypointBoxes_checked(rightFootBox.isSelected(), 19, 20, 21));
		
		panel.add(headBox);
		panel.add(spineBox);
		panel.add(leftArmBox);
		panel.add(rightArmBox);
		panel.add(leftLegBox);
		panel.add(rightLegBox);
		panel.add(leftFootBox);
		panel.add(rightFootBox);
		
		return panel;
	}
	
	private JPanel createKeypointsCheckPanel()
	{
		JPanel fiveTimesFivePanel = new JPanel();
		fiveTimesFivePanel.setBorder(BorderFactory.createTitledBorder("Keypoints Body_25"));
		GridLayout fiveTimesFiveLayout = new GridLayout(5, 5);
		fiveTimesFivePanel.setLayout(fiveTimesFiveLayout);
		
		for (int i = 0; i < fiveTimeFiveBoxes.length; i++)
		{
			JCheckBox box = new JCheckBox();
			box.setLabel("K: " + i);
			box.addActionListener(e -> fiveTimesFive_checked());
			fiveTimeFiveBoxes[i] = box;
			fiveTimesFivePanel.add(box);
		}
		
		return fiveTimesFivePanel;
	}
	
	private JPanel createOneKeypointPanel()
	
	private void diagramTypeBox_checked(boolean checked, int index)
	{
		if (checked)
		{
			diagramTypeBoxes[index].setSelected(checked);
			for (int i = 0; i < diagramTypeBoxes.length; i++)
			{
				if (i != index) diagramTypeBoxes[i].setSelected(false);
				if (i == index)
				{
					//TODO
				}
			}
		}
	}
	
	private void fiveTimesFive_checked()
	{
		List<Integer> selectedKeypoints = new ArrayList<Integer>();
		for (int i = 0; i < fiveTimeFiveBoxes.length; i++)
		{
			if (fiveTimeFiveBoxes[i].isSelected())
			{
				selectedKeypoints.add(i);
			}
		}
		graph.drawPoints(selectedKeypoints);
	}
	
	private void combinedKeypointBoxes_checked(boolean checked, int... keypoints)
	{
		for (int keypoint : keypoints)
		{
			fiveTimeFiveBoxes[keypoint].setSelected(checked);
		}
		fiveTimesFive_checked();
	}
	
	public void uploadFrameData(List<FrameData> frameData)
	{
		if (graph != null)
		{
			graph.addFrames(frameData);
		}
	}
}
