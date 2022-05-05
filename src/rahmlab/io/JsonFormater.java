package rahmlab.io;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rahmlab.datatype.FrameData;
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
		
		System.out.println(fileValue);
		
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
		
		Map<String, Point[]> points = buildPoints(parts);
		return buildPersonKeypointsFromPointMap(points);
	}
	
	private PersonKeypoints buildPersonKeypointsFromPointMap(Map<String, Point[]> points)
	{
		Point[] poseKeypoints_2d = new Point[25];
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

	private Map<String, Point[]> buildPoints(List<String> parts)
	{
		Map<String, Point[]> pointMap = new HashMap<String, Point[]>();
		
		for (String part : parts)
		{
			String[] splits = part.split("" + DOUBLE_POINT);
			String key = removeOuterBrackes(splits[0]);
			String value = removeOuterBrackes(splits[1]);
			Point[] points = buildPointsForOnePart(value);
			pointMap.put(key, points);
 		}
		return pointMap;
	}
	
	private Point[] buildPointsForOnePart(String part)
	{
		String[] numbers = part.split("" + COMMA);
		Point[] points = new Point[numbers.length / 3];
		for (int i = 0; i < points.length; i++)
		{
			int n = i * 3;
			Double x = Double.valueOf(numbers[n + 0]);
			Double y = Double.valueOf(numbers[n + 1]);
			Double z = Double.valueOf(numbers[n + 2]);
			points[i] = new Point(x, y, z);
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
}
