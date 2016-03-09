package collinear;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
	private Point[] points;
	private ArrayList<LineSegment> L = new ArrayList<LineSegment>();
	private int lineSegments=0;
	public FastCollinearPoints(Point[] points)
	{
		if (points == null)
			throw new java.lang.NullPointerException();
		Arrays.sort(points);
		// check for duplicate points in the array
		for (int i = 0;i< points.length-1;i++)
		{
			if ((points[i].compareTo(points[i+1])== 0) || points[i]==null || points[i+1]==null)
				throw new java.lang.IllegalArgumentException();			
		}
		this.points = points;
		getCollinears();
	}
	
	private void getCollinears()
	{
		for (int i = 0;i<points.length-1;i++)
		{
			ArrayList<Point> parr = new ArrayList<Point>();
			for (int j = i+1; j < points.length;j++)
			{
				parr.add(points[j]);
			}
			Point[] P = new Point[parr.size()];
			P = parr.toArray(P);
			Arrays.sort(P,points[i].slopeOrder());
			int x,y;
			x=0;y=x+2;
			while(x < P.length && y < P.length)
			{
				if (points[i].slopeTo(P[x])==points[i].slopeTo(P[y]))
				{
					// get natural ordering
					Arrays.sort(P,x,y);
					if (points[i].compareTo(P[x])<0)
						L.add(new LineSegment(points[i],P[y]));
					else if (points[i].compareTo(P[y])> 0)
						L.add(new LineSegment(P[x],points[i]));
					else
						L.add(new LineSegment(P[x],P[y]));
					lineSegments++;
					x = y+1;
				}
				else
					x = x+1;
				y = x+2;
			}
		}
	}
	public int numberOfSegments()
	{
		return lineSegments;
	}
	
	public LineSegment[] segments()
	{
		LineSegment[] LArr = new LineSegment[L.size()];
		LArr = L.toArray(LArr);
		return LArr;
		
	}
}
