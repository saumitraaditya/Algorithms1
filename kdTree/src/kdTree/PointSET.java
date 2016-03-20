package kdTree;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.Iterator;
import java.util.TreeSet;
import java.util.ArrayList;
public class PointSET {
	
	private TreeSet<Point2D> T;
	public PointSET()
	{
		T = new TreeSet<Point2D>();
	}
	
	public boolean isEmpty()
	{
		return T.isEmpty();
	}
	
	public int size()
	{
		return T.size();
	}
	
	public void insert(Point2D p)
	{
		if (p == null) throw new java.lang.NullPointerException();
		T.add(p);
	}
	
	public boolean contains(Point2D p)
	{
		if (p == null) throw new java.lang.NullPointerException();
		return T.contains(p);
	}
	
	public void draw()
	{
		//Get a iterator from TreeSet and than iterate over it drawing the points on std.draw.
		Iterator<Point2D> I = T.iterator();
		Point2D temp;
		while (I.hasNext())
		{
			temp = I.next();
			temp.draw();
			//System.out.println(temp);
		}
	
	}
	
	public Iterable<Point2D> range(RectHV rect)
	{
		if (rect == null) throw new java.lang.NullPointerException();
		ArrayList<Point2D> A = new ArrayList<Point2D>();
		Iterator<Point2D> I = T.iterator();
		Point2D temp;
		while (I.hasNext())
		{
			temp = I.next();
			if (rect.contains(temp))
			{
				A.add(temp);
			}
		}
		System.out.println("BRUTE FORCE RANGE SIZE: "+A.size());
		return A;
	}
	
	public Point2D nearest(Point2D p)
	{
		if (p == null) throw new java.lang.NullPointerException();
		Point2D nearest = null;
		Point2D temp;
		// safe assumption as out=r problem space is within a unit square.
		double minDistance = 1000.0;
		double Distance = 0;
		Iterator<Point2D> I = T.iterator();
		while (I.hasNext())
		{
			temp = I.next();
			Distance = p.distanceTo(temp);
			if (Distance < minDistance)
			{
				nearest = temp;
				minDistance = Distance;
			}
		}
		System.out.println("BRUTE Nearest-- "+nearest);
		return nearest;
	}
	
	public static void main()
	{
		
	}
		
}
