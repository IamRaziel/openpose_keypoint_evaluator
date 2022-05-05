package rahmlab.datatype;

import java.util.List;

public class FrameData 
{
	private Double version;
	private List<PersonKeypoints> people;
	
	public FrameData(Double version, List<PersonKeypoints> people)
	{
		this.version = version;
		this.people = people;
	}
	
	public Double getVersion()
	{
		return version;
	}
	
	public void addPerson(PersonKeypoints p)
	{
		people.add(p);
	}
	
	public List<PersonKeypoints> getPeople()
	{
		return people;
	}
}
