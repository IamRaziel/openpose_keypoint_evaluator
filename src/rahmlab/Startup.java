package rahmlab;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Startup 
{

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
	}
}
