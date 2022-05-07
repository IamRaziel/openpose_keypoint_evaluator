package rahmlab;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import rahmlab.datatype.FrameData;
import rahmlab.io.JsonFileFinder;
import rahmlab.io.KeypointReader;
import rahmlab.ui.GUI;


public class Startup 
{
	private static List<FrameData> frames;

	public static void main(String[] args) 
	{
		if (args == null || args.length == 0)
		{
			System.out.println("I need to know the directory of the keypoint .json files.");
			System.out.println("Either you add an argument --path {path} to my start or write it now.");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			try 
			{
				String line = reader.readLine();
				args = new String[] { Arguments.DIR_PATH_KEY, line}; 
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		Arguments arguments = ArgumentsFactory.build(args);
		KeypointReader reader = new KeypointReader(new JsonFileFinder(arguments.getDirectoryPath()));
		frames = reader.getFrames();
		GUI gui = new GUI();
		gui.uploadFrameData(frames);
	}
}
