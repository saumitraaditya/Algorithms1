package collinear;

import java.util.Arrays;

import edu.princeton.cs.algs4.In;

import java.util.*;

public class BruteCollinearPoints {
	
	private Point[] points;
	private Point[] coll_p;
	private ArrayList<LineSegment> L = new ArrayList<LineSegment>();
	private int lineSegments=0;
	public BruteCollinearPoints(Point[] points)
	{	if (points == null)
			throw new java.lang.NullPointerException();
		Arrays.sort(points);
		// check for duplicate points in the array
		for (int i = 0;i< points.length-1;i++)
		{
			if ((points[i].compareTo(points[i+1])== 0) || points[i]==null || points[i+1]==null)
				throw new java.lang.IllegalArgumentException();			
		}
		this.points = points;
		coll_p = new Point[4];
		getCombinations(0,0);
	}
	
	public int numberOfSegments()
	{		
		return lineSegments;
	}
	
	private void getCombinations(int index, int start)
	{
		if (index == 4)
		{
			// check for collinearity
			Arrays.sort(coll_p);
			double slope_1 = coll_p[0].slopeTo(coll_p[1]);
			double slope_2 = coll_p[0].slopeTo(coll_p[2]);
			double slope_3 = coll_p[0].slopeTo(coll_p[3]);
			if (slope_1==slope_2)
				{
					if (slope_2==slope_3)
					{						
						System.out.println(coll_p[0].toString()+"\t"+coll_p[1].toString()+"\t"+coll_p[2].toString()+"\t"+coll_p[3].toString());
						System.out.println(slope_1+"\t"+slope_2+"\t"+slope_3);
						L.add(new LineSegment(coll_p[0],coll_p[3]));
						lineSegments++;
					}
				}
			//System.out.println(coll_p[0].toString()+"\t"+coll_p[1].toString()+"\t"+coll_p[2].toString()+"\t"+coll_p[3].toString());
			return ;
		}
		else if (start >= points.length)
			return;
		else
		{
			// two possibilities if current Point pointed to by "start" is included in "coll_p" as position index or not.
			coll_p[index]=points[start];
			getCombinations(index+1,start+1);
			// not included
			getCombinations(index,start+1);
		}
	}
	
	public LineSegment[] segments()
	{
		LineSegment[] LArr = new LineSegment[L.size()];
		LArr = L.toArray(LArr);
		return LArr;
	}
	
	public static void main(String[] args)
	{
		In in = new In(args[0]);
	    int N = in.readInt();
	    Point[] points = new Point[N];
	    for (int i = 0; i < N; i++) {
	        int x = in.readInt();
	        int y = in.readInt();
	        points[i] = new Point(x, y);
	    }
	    BruteCollinearPoints BP = new BruteCollinearPoints(points);
	}

}
