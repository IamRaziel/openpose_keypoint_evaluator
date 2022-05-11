package rahmlab.ui;

import java.awt.BorderLayout;
import java.awt.Color;
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
import rahmlab.datatype.KeyPoint;
import rahmlab.ui.components.FiveTimesFivePanel;
import rahmlab.ui.graph.BarChart;
import rahmlab.ui.graph.Graph;
import rahmlab.ui.graph.LineChart;
import rahmlab.ui.graph.PointCloud;

public class GUI extends JFrame
{
	private final int X = 1200;
	private final int Y = 800;
	
	private List<ResizeListener> resizables = new ArrayList<ResizeListener>();
	private JCheckBox[] diagramTypeBoxes = new JCheckBox[4];
	private FiveTimesFivePanel fiveTimeFivePanel = new FiveTimesFivePanel();
	
	private List<FrameData> frames;
	private Graph graph;
	private final Graph balkenDiagramm;
	private final Graph pointCloud;
	private final Graph lineGraph;
	private final Graph[] graphs = new Graph[4];
	
	public GUI()
	{
		balkenDiagramm = new BarChart();
		pointCloud = new PointCloud();
		lineGraph = new LineChart();
		graphs[0] = balkenDiagramm;
		graphs[1] = pointCloud;
		graphs[2] = lineGraph;
		
		setSize(X, Y);
		setLayout(new BorderLayout()); 
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		add(createHeadPanel(), BorderLayout.PAGE_START);
		
		setGraphAndInitialize(balkenDiagramm);
		
		this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
            	resizables.forEach(a -> a.resized());
            }
        });
	}
	
	private boolean setGraphAndInitialize(Graph g)
	{
		if (g == null || graph == g)
			return false;
		
		Graph oldGraph = graph;
		if (oldGraph != null) 
			resizables.remove(oldGraph);
		graph = g;
		resizables.add(graph);
		add(graph, BorderLayout.CENTER);
		setVisible(true);
		graph.initialize();
		if (oldGraph != null)
		{
			graph.setFrames(frames);
			graph.drawPoints(oldGraph.getSelectedPoints());
		}
		graph.repaint();
		return true;
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
		
		fiveTimeFivePanel = new FiveTimesFivePanel();
		fiveTimeFivePanel.addDrawPointsListener(e -> graph.drawPoints(e));
		headPanel.add(fiveTimeFivePanel);
		
		return headPanel;
	}
	
	private JPanel createDiagrammTypePanel()
	{
		JPanel panel = new JPanel();
		
		GridLayout layout = new GridLayout(4, 0);
		panel.setLayout(layout);
		panel.setBorder(BorderFactory.createTitledBorder("Diagramm Type"));
		
		JCheckBox boxGraphOne = new JCheckBox();
		boxGraphOne.setLabel("Bar Chart");
		boxGraphOne.setSelected(true);
		boxGraphOne.addActionListener(e -> diagramTypeBox_checked(boxGraphOne.isSelected(), 0));
		JCheckBox boxGraphTwo = new JCheckBox();
		boxGraphTwo.setLabel("Point Cloud");
		boxGraphTwo.addActionListener(e -> diagramTypeBox_checked(boxGraphTwo.isSelected(), 1));
		JCheckBox boxGraphThree = new JCheckBox();
		boxGraphThree.setLabel("Line Chart");
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
//		panel.add(boxGraphFour);
		
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
	
	private void diagramTypeBox_checked(boolean checked, int index)
	{
		if (checked)
		{
			boolean graphChanged = setGraphAndInitialize(graphs[index]);
			if (graphChanged)
			{
				for (int i = 0; i < diagramTypeBoxes.length; i++)
				{
					diagramTypeBoxes[i].setSelected(i == index);
				}
			}
		}
	}
	
	private void combinedKeypointBoxes_checked(boolean checked, int... keypoints)
	{
		for (int keypoint : keypoints)
		{
			fiveTimeFivePanel.setSelected(keypoint, checked);
		}
		fiveTimeFivePanel.box_checked();
	}
	
	public void uploadFrameData(List<FrameData> frameData)
	{
		if (graph != null)
		{
			frames = frameData;
			graph.setFrames(frameData);
			addColorsToFiveTimesFiveBoxes(frameData.get(0));
		}
	}
	
	private void addColorsToFiveTimesFiveBoxes(FrameData frame)
	{
		KeyPoint[] points = frame.getPeople().get(0).getPoseKeypoints_2d();
		Color[] colors = new Color[FiveTimesFivePanel.BOXES_COUNT];
		for (int i = 0; i < colors.length; i++)
		{
			colors[i] = points[i].getColor();
		}
		fiveTimeFivePanel.setColors(colors);
	}
}
