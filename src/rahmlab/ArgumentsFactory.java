package rahmlab;

public class ArgumentsFactory 
{	
	public static Arguments build(String[] args)
	{
		if (args == null || args.length == 0)
			return null;
		
		String directory = null;
		for (int i = 0; i < args.length; i++)
		{
			String arg = args[i];
			switch (arg)
			{
			case Arguments.DIR_PATH_KEY: directory = i < args.length - 1? args[++i] : null;
			}
		}
		return new Arguments(directory);
	}
}
