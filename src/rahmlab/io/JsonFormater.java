package rahmlab.io;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rahmlab.datatype.FrameData;
import rahmlab.datatype.KeyPoint;
import rahmlab.datatype.PersonKeypoints;
import rahmlab.datatype.Point;

public class JsonFormater 
{
	public static final char COMMA = ',';
	public static final char DOUBLE_POINT = ':';
	public static final char CURVED_OPEN = '{';
	public static final char CURVED_CLOSED = '}';
	public static final char SQUARED_OPEN = '[';
	public static final char SQUARED_CLOSED = ']';
	private static final String POSE_KEYPOINTS_2D = "pose_keypoints_2d";
	
	private String originalFileValue;
	private String fileValue;
	
	private JsonFormater(String fileValue)
	{
		this.fileValue = new String(fileValue);
		this.originalFileValue = new String(fileValue);
	}
	
	public static FrameData build(String fileValue)
	{
		//	{"version":1.2,"people":[{},{}]}
		JsonFormater formater = new JsonFormater(fileValue);
		
		formater.fileValue = formater.removeOuterBrackes(formater.fileValue);
		Double version = formater.getVersion();
		List<PersonKeypoints> peopleKeypoints = formater.buildPeopleKeypoints();
		
		return new FrameData(version, peopleKeypoints);
	}
	
	private String removeOuterBrackes(String text)
	{
		return text.substring(1, text.length() - 1);
	}
	
	private Double getVersion()
	{
		int pos = findFirstChar(fileValue, COMMA);
		String version = fileValue.substring(0, pos);
		fileValue = fileValue.substring(pos + 1, fileValue.length());
		String versionValue = version.split("" + DOUBLE_POINT)[1];
		return Double.valueOf(versionValue);
	}

	private List<PersonKeypoints> buildPeopleKeypoints()
	{
		List<PersonKeypoints> peopleKeypoints = new ArrayList<PersonKeypoints>();
		int pos = findFirstChar(fileValue, DOUBLE_POINT);
		fileValue = fileValue.substring(pos + 1, fileValue.length());
		fileValue = removeOuterBrackes(fileValue);
		
		//Searching for every person in the array and builduing it
		int oldPos = 0;
		while ((pos = findFirstChar(fileValue, CURVED_CLOSED)) != -1 && pos != oldPos)
		{
			String personJson = fileValue.substring(0, pos + 1);
			peopleKeypoints.add(builPersonKeypoints(personJson));
			if (fileValue.length() > pos + 1 && fileValue.charAt(pos + 1) == COMMA)
				fileValue = fileValue.substring(pos + 2, fileValue.length());
			oldPos = pos;
		}
		
		return peopleKeypoints;
	}
	
	private PersonKeypoints builPersonKeypoints(String personJson)
	{
		String local = new String(personJson);
		local = removeOuterBrackes(local);
		List<String> parts = new ArrayList<String>();
		int pos = 0;
		while ((pos = findFirstChar(local, SQUARED_CLOSED)) != -1)
		{
			String part = local.substring(0, pos + 1);
			if (parts.size() > 0 && parts.get(parts.size() - 1).equals(part))
				break;
			parts.add(part);
			if (local.length() > pos + 1 && local.charAt(pos + 1) == COMMA)
				local = local.substring(pos + 2, local.length());
		}
		
		Map<String, KeyPoint[]> points = buildPoints(parts);
		return buildPersonKeypointsFromPointMap(points);
	}
	
	private PersonKeypoints buildPersonKeypointsFromPointMap(Map<String, KeyPoint[]> points)
	{
		KeyPoint[] poseKeypoints_2d = new KeyPoint[25];
		for (String key : points.keySet())
		{
			switch (key)
			{
				case POSE_KEYPOINTS_2D: poseKeypoints_2d = points.get(key);
					break;
			}
		}
		return new PersonKeypoints(poseKeypoints_2d);
	}

	private Map<String, KeyPoint[]> buildPoints(List<String> parts)
	{
		Map<String, KeyPoint[]> pointMap = new HashMap<String, KeyPoint[]>();
		
		for (String part : parts)
		{
			String[] splits = part.split("" + DOUBLE_POINT);
			String key = removeOuterBrackes(splits[0]);
			String value = removeOuterBrackes(splits[1]);
			KeyPoint[] points = buildPointsForOnePart(value);
			pointMap.put(key, points);
 		}
		return pointMap;
	}
	
	private KeyPoint[] buildPointsForOnePart(String part)
	{
		String[] numbers = part.split("" + COMMA);
		KeyPoint[] points = new KeyPoint[numbers.length / 3];
		for (int i = 0; i < points.length; i++)
		{
			int n = i * 3;
			Double x = Double.valueOf(numbers[n + 0]);
			Double y = Double.valueOf(numbers[n + 1]);
			Double z = Double.valueOf(numbers[n + 2]);
			points[i] = new KeyPoint(getKeypointColor(i), x, y, z);
		}
		return points;
	}

	private int findFirstChar(String text, char c)
	{
		char[] chars = text.toCharArray();
		for (int i = 0; i < chars.length; i++)
		{
			if (chars[i] == c)
			{
				return i;
			}
		}
		return -1;
	}
	
	private Color getKeypointColor(int index)
	{	
		switch (index)
		{
		//HEAD
			case 0: return Color.RED;
			case 15: return Color.PINK;
			//Purple
			case 16: return new Color(172, 35, 210);
			//Dark pink / light Purple
			case 17: return new Color(210, 35, 61);
			//Mix between dark blue and purple
			case 18: return new Color(99, 35, 210);
		//SPINE
			//Darker Red
			case 1: return new Color(200, 3, 3);
			//Even Darker Red
			case 8: return new Color(170, 0, 0);
		//Left Arm
			//Yellow-Green
			case 5: return new Color(194, 255, 0);
			//Darker Yellow-Green
			case 6: return new Color(160, 209, 0);
			//Even Darker Yellow-Green
			case 7: return new Color(130, 170, 0);
		//Right Arm
			//Even Darerk Orange
			case 2: return new Color(174, 71, 0);
			//Brown Orange
			case 3: return new Color(255, 151, 0);
			//Orange
			case 4: return new Color(255, 120, 0);
		//Left Leg
			//Light Green-Blue
			case 9: return new Color(0, 255, 141);
			//Dark Cyan
			case 10: return new Color(0, 175, 148);
			//LIghter Cyan
			case 11: 
		//Left Foot
			case 22: 
			case 23:
			case 24:return new Color(0, 200, 169);
		//Right Leg
			//Blue
			case 12: return new Color(0, 135, 255);
			//darker Blue
			case 13: return new Color(0, 106, 200);
			//Dark Blue
			case 14: 
		//Right Foot
			case 19:
			case 20:
			case 21: return new Color(8, 0, 255);
		}
		
		return Color.BLACK;
	}
}
