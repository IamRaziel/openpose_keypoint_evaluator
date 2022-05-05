package rahmlab;

public class Arguments 
{
	public static final String DIR_PATH_KEY = "--path";
	
	private String directoryPath;
	
	public Arguments(String directoryPath)
	{
		this.directoryPath = directoryPath;
	}

	public String getDirectoryPath() {
		return directoryPath;
	}
}
