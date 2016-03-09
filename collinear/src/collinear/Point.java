package collinear;

import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;
public class Point implements Comparable<Point>{
	
	private final double x;
	private final double y;
	public Point(int x, int y)
	{
		this.x = (double) x;
		this.y = (double) y;
	}
	public void draw()
	{
		StdDraw.point(x, y);
	}
	
	public void drawTo(Point that)
	{
		StdDraw.line(this.x,this.y,that.x,that.y);
	}
	
	public String toString()
	{
		return "(" + x + ", " + y + ")";
    }
	
	public int compareTo(Point that)
	{
		if (this.y < that.y)
			return -1;
		else if (this.y > that.y)
			return 1;
		else
		{
			if (this.x < that.x)
				return -1;
			else if (this.x > that.x)
				return 1;
			else
				return 0;
		}
	}
	
	public double slopeTo(Point that)
	{
		/* Handle infinity cases*/
		if (this.x == that.x)
		{
			if (this.y == that.y)
				return Double.NEGATIVE_INFINITY;
			else 
				return Double.POSITIVE_INFINITY;
		}
		else if (that.y == this.y)
			return 0; // positive zero for horizontal segments.
		else
			return (that.y - this.y)/(that.x - this.x);
	}
	
	private class slopeComparator implements Comparator<Point>
	{
		public int compare(Point a, Point b)
		{
			double slope_A = Point.this.slopeTo(a);
			double slope_B = Point.this.slopeTo(b);
			
			if (slope_A < slope_B)
				return -1;
			else if (slope_A > slope_B)
				return 1;
			else
				return 0;
		}
	}
	
	public Comparator<Point> slopeOrder()
	{
		return new slopeComparator();
	}
}
