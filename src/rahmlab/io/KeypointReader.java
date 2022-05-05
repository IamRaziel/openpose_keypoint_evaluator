package rahmlab.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rahmlab.datatype.FrameData;

public class KeypointReader
{	
	private JsonFileFinder finder;
	private List<FrameData> frames; 
	
	public KeypointReader(JsonFileFinder finder)
	{
		this.finder = finder;
		buildFrameData();
	}
	
	public List<FrameData> getFrames()
	{
		return frames;
	}
	
	public FrameData getFrame(int index)
	{
		if (-1 < index && index < frames.size())
			return frames.get(index);
		return null;
	}
	
	private void buildFrameData()
	{
		frames = new ArrayList<FrameData>();
		String fileValue = null;
		while ((fileValue = readNextFile()) != null)
		{
			frames.add(JsonFormater.build(fileValue));
		}
	}
	
	private String readNextFile()
	{
		String filePath = finder.getNextJsonFilePath();
		if (filePath == null)
		{
			return null;
		}
	
		String lineValue = null;
		StringBuilder fileBuilder = new StringBuilder();
		try 
		{
			BufferedReader reader = new BufferedReader(new FileReader(filePath));
			
			while ((lineValue = reader.readLine()) != null)
			{
				fileBuilder.append(lineValue);
			}
			
			reader.close();
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return fileBuilder.toString();
	}
	
	
}
