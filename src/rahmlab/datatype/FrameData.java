package rahmlab.datatype;

import java.util.List;

public class FrameData 
{
	private Double version;
	private List<PeopleKeypoints> people;
	
	public FrameData(Double version, List<PeopleKeypoints> people)
	{
		this.version = version;
		this.people = people;
	}
	
	public Double getVersion()
	{
		return version;
	}
	
	public void addPerson(PeopleKeypoints p)
	{
		people.add(p);
	}
	
	public List<PeopleKeypoints> getPeople()
	{
		return people;
	}
}
