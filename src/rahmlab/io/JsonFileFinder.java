package rahmlab.io;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JsonFileFinder 
{
	private final String JSON_ENDING = ".json";
	
	private List<String> jsonFiles = new ArrayList<String>();
	private int iterator = 0;
	
	public JsonFileFinder(String directoryPath)
	{
		searchDirectoryForFiles(directoryPath);
	}
	
	public JsonFileFinder(List<String> filePaths)
	{
		searchFilesForJsonFiles(filePaths);
	}
	
	private void searchDirectoryForFiles(String directoryPath)
	{
		Path path = Paths.get(directoryPath);
		while (path != null && path.getFileName().toString().contains("."))
		{
			path = path.getParent();
		}
		if (path == null)
		{
			return;
		}
		
		List<String> filePaths = new ArrayList<String>();
		File directory = new File(path.toString());
		File[] files = directory.listFiles();
		for (File f : files)
		{
			filePaths.add(f.getAbsolutePath());
		}
		searchFilesForJsonFiles(filePaths);
	}
	
	private void searchFilesForJsonFiles(List<String> filePaths)
	{
		for (String filePath : filePaths)
		{
			if (filePath.endsWith(JSON_ENDING))
			{
				jsonFiles.add(filePath);
			}
		}
	}
	
	public String getNextJsonFilePath()
	{
		if (jsonFiles == null || iterator >= jsonFiles.size())
		{
			return null;
		}
		String path = jsonFiles.get(iterator);
		iterator++;
		return path;
	}
}
