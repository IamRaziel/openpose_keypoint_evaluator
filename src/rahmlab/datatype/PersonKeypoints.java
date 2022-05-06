package rahmlab.datatype;

import java.util.Map;

public class PersonKeypoints 
{
	private KeyPoint[] poseKeypoints_2d = new KeyPoint[25];
	private KeyPoint[] faceKeypoints_2d = new KeyPoint[0];
	private KeyPoint[] handLeftKeypoints_2d = new KeyPoint[0];
	private KeyPoint[] handRightKeypoints_2d = new KeyPoint[0];
	private KeyPoint[] poseKeypoints_3d = new KeyPoint[0];
	private KeyPoint[] faceKeypoints_3d = new KeyPoint[0];
	private KeyPoint[] handLeftKeypoints_3d = new KeyPoint[0];
	private KeyPoint[] handRightKeypoints_3d = new KeyPoint[0];
	
	public PersonKeypoints(KeyPoint[] poseKeypoints_2d)
	{
		if (poseKeypoints_2d.length == this.poseKeypoints_2d.length)
		{
			this.poseKeypoints_2d = poseKeypoints_2d;
		}
	}
	
	public PersonKeypoints(Map<Integer, KeyPoint> poseKeypoints_2d)
	{
		for (Integer i : poseKeypoints_2d.keySet())
		{
			if (-1 < i && i < this.poseKeypoints_2d.length)
			{
				this.poseKeypoints_2d[i] = poseKeypoints_2d.get(i);
			}
		}
	}

	public KeyPoint[] getPoseKeypoints_2d() 
	{
		return poseKeypoints_2d;
	}

	public void setPoseKeypoints_2d(KeyPoint[] poseKeypoints_2d) 
	{
		if (this.poseKeypoints_2d.length == poseKeypoints_2d.length)
			this.poseKeypoints_2d = poseKeypoints_2d;
	}

	public KeyPoint[] getFaceKeypoints_2d() 
	{
		return faceKeypoints_2d;
	}

	public void setFaceKeypoints_2d(KeyPoint[] faceKeypoints_2d) 
	{
		if (this.faceKeypoints_2d.length == faceKeypoints_2d.length)
			this.faceKeypoints_2d = faceKeypoints_2d;
	}

	public KeyPoint[] getHandLeftKeypoints_2d() 
	{
		return handLeftKeypoints_2d;
	}

	public void setHandLeftKeypoints_2d(KeyPoint[] handLeftKeypoints_2d) 
	{
		if (this.handLeftKeypoints_2d.length == handLeftKeypoints_2d.length)
			this.handLeftKeypoints_2d = handLeftKeypoints_2d;
	}

	public KeyPoint[] getHandRightKeypoints_2d() 
	{
		return handRightKeypoints_2d;
	}

	public void setHandRightKeypoints_2d(KeyPoint[] handRightKeypoints_2d) 
	{
		if (this.handRightKeypoints_2d.length == handRightKeypoints_2d.length)
			this.handRightKeypoints_2d = handRightKeypoints_2d;
	}

	public KeyPoint[] getPoseKeypoints_3d() 
	{
		return poseKeypoints_3d;
	}

	public void setPoseKeypoints_3d(KeyPoint[] poseKeypoints_3d) 
	{
		if (this.poseKeypoints_3d.length == poseKeypoints_3d.length)
			this.poseKeypoints_3d = poseKeypoints_3d;
	}

	public KeyPoint[] getFaceKeypoints_3d() 
	{
		return faceKeypoints_3d;
	}

	public void setFaceKeypoints_3d(KeyPoint[] faceKeypoints_3d)
	{
		if (this.faceKeypoints_3d.length == faceKeypoints_3d.length)
			this.faceKeypoints_3d = faceKeypoints_3d;
	}

	public KeyPoint[] getHandLeftKeypoints_3d() 
	{
		return handLeftKeypoints_3d;
	}

	public void setHandLeftKeypoints_3d(KeyPoint[] handLeftKeypoints_3d)
	{
		if (this.handLeftKeypoints_3d.length == handLeftKeypoints_3d.length)
			this.handLeftKeypoints_3d = handLeftKeypoints_3d;
	}

	public KeyPoint[] getHandRightKeypoints_3d()
	{
		return handRightKeypoints_3d;
	}

	public void setHandRightKeypoints_3d(KeyPoint[] handRightKeypoints_3d) 
	{
		if (this.handRightKeypoints_3d.length == handRightKeypoints_3d.length)
			this.handRightKeypoints_3d = handRightKeypoints_3d;
	}
}
