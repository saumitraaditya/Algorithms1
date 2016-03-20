package kdTree;

import java.util.ArrayList;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Queue;



public class KdTree {
	private twoDTree tree;
	
	private class twoDTree
	{
		private node root;
		private class node
		{
			private Point2D point;
			private boolean isVertical;
			private node left;
			private node right;
			private node parent;
			private int size;
			// with each node a 
			// rectangle is associated.
			double x_min = 0;
			double x_max = 1;
			double y_min = 0;
			double y_max = 1;
			
			public node(Point2D p,boolean isH)
			{
				point = p;
				isVertical = isH;
				left = null;
				right = null;
				parent = null;
				size = 0;
				
			}
		}
		private void add (Point2D p)
		{
			if (root==null)
				root = new node(p,true);
			else
			{
				put(root,p,!root.isVertical);
			}
		}
		
		private node put (node n, Point2D p, boolean isH)
		{
			if (n==null)
				return new node(p,isH);
			else
			{
				if (n.isVertical)// partitions space vertically/by drawing a vertical line.
				{
					if (p.x() >= n.point.x())
					{
						n.right = put(n.right,p,!n.isVertical);
						n.right.parent = n;
						n.right.x_min = n.point.x();
						n.right.x_max = n.x_max;
						n.right.y_min = n.y_min;
						n.right.y_max = n.y_max;
						
					}
					else
					{
						n.left = put(n.left, p, !n.isVertical);
						n.left.parent = n;
						n.left.x_max = n.point.x();
						n.left.x_min = n.x_min;
						n.left.y_min = n.y_min;
						n.left.y_max = n.y_max;
						
					}
				}
				else // partitions space horizontally.
				{
					if (p.y() >= n.point.y())
					{
						n.right = put(n.right,p,!n.isVertical);
						n.right.parent = n;
						n.right.y_min = n.point.y();
						n.right.y_max = n.y_max;
						n.right.x_min = n.x_min;
						n.right.x_max = n.x_max;
					}
					else
					{
						n.left = put(n.left, p, !n.isVertical);
						n.left.parent = n;
						n.left.y_max = n.point.y();
						n.left.y_min = n.y_min;
						n.left.x_min = n.x_min;
						n.left.x_max = n.x_max;
					}
				}
				n.size++;
				return n;
			}
			
		}
		
		private boolean search(Point2D p)
		{
			return search_tree(root,p);
		}
		
		private boolean search_tree(node n,Point2D p)
		{
			if (n == null)
				return false;
			else
			{
				if (n.point.equals(p))
					return true;
				else 
				{
					if (n.isVertical)
					{
						if (p.x()>=n.point.x())
							return search_tree(n.right,p);
						else
							return search_tree(n.left,p);
					}
					else
					{
						if (p.y()>=n.point.y())
							return search_tree(n.right,p);
						else
							return search_tree(n.left,p);
					}
				}
			}
		}
		
		private Iterable<node> iterator()
		{
			ArrayList<node> A = new ArrayList<node>();
			Queue<node> queue = new Queue<node>();
			queue.enqueue(root);
			while (!queue.isEmpty()) {
	            node x = queue.dequeue();
	            if (x == null) continue;
	            A.add(x);
	            queue.enqueue(x.left);
	            queue.enqueue(x.right);
	        }
	        return A;
		}
		
		private void rangeSearch(node N,RectHV rect, ArrayList<Point2D>A)
		{
			if (N==null)
				return;
			else
			{
				if (rect.contains(N.point))
					A.add(N.point);
				if (N.left!=null && rect.intersects(new RectHV(N.left.x_min,N.left.y_min,N.left.x_max,N.left.y_max)))
				{
					rangeSearch(N.left,rect,A);
				}
				if (N.right!=null && rect.intersects(new RectHV(N.right.x_min,N.right.y_min,N.right.x_max,N.right.y_max)))
				{
					rangeSearch(N.right,rect,A);
				}
				return;
			}
		}
		
		private void nearest(node N,Point2D p,Point2DWrapper nearest, double nearest_distance)
		{
			if (N==null)
				return;
			else
			{
				double distance = p.distanceTo(N.point);
				if (distance<nearest.nearest)
				{
					nearest.setPoint(N.point,distance);
					System.out.println("NEAREST STEP "+nearest.nearest+" Nearest Point "+N.point);
				}
				if(N.left!=null && new RectHV(N.left.x_min,N.left.y_min,N.left.x_max,N.left.y_max).distanceTo(p) < nearest.nearest)
					nearest(N.left,p,nearest,nearest_distance);
				if(N.right!=null && new RectHV(N.right.x_min,N.right.y_min,N.right.x_max,N.right.y_max).distanceTo(p) < nearest.nearest)
					nearest(N.right,p, nearest, nearest_distance);
				return;
			}
		}
		
		private void draw()
		{
			double defaultPenRadius = StdDraw.getPenRadius();
			for (node n:tree.iterator())
			{
				//System.out.println(p);
				StdDraw.setPenRadius(defaultPenRadius);
				if (n.parent==null)
				{
					if (n.isVertical)
						{
							StdDraw.setPenColor(StdDraw.RED);
							StdDraw.line(n.point.x(),0,n.point.x(),1);
						}
						
					else
						{
							StdDraw.setPenColor(StdDraw.RED);
							StdDraw.line(0,n.point.y(),1,n.point.y());
						}
					System.out.println("Node "+n.point+" parent NULL");
					System.out.println("Rectangle "+ new RectHV(n.x_min,n.y_min,n.x_max,n.y_max));
				}
				else
				{
					if (n.isVertical)
					{	// Am I left child of my parent
						// if yes my ymax is my parents
						// y coordinate
						// else my ymin is my parents
						// coordinate
						StdDraw.setPenColor(StdDraw.RED);
						if (n.parent.left!=null && n.point.equals(n.parent.left.point))
						{
							StdDraw.line(n.point.x(),n.y_min,n.point.x(),n.y_max);
						}
						else
							StdDraw.line(n.point.x(),n.y_min,n.point.x(),n.y_max);		
					}
					else
					{						
							StdDraw.setPenColor(StdDraw.BLUE);
							if (n.parent.left!=null && n.point.equals(n.parent.left.point))
							{
								StdDraw.line(n.x_min,n.point.y(),n.x_max,n.point.y());
							}
							else
								StdDraw.line(n.x_min,n.point.y(),n.x_max,n.point.y());
					}
					System.out.println("Node "+n.point+" parent "+n.parent.point);
					System.out.println("Rectangle "+ new RectHV(n.x_min,n.y_min,n.x_max,n.y_max));
				}				
				StdDraw.setPenColor(StdDraw.BLACK);
	            StdDraw.setPenRadius(.01);
	            n.point.draw();		
			}
		}
		
	}
	
	private class Point2DWrapper
	{
		private Point2D point;
		private double nearest;
		public Point2DWrapper(Point2D p)
		{
			point = p;
			nearest = 9999;
		}
		private void setPoint(Point2D p,double distance)
		{
			point = p;
			nearest = distance;
		}
	}
	
	public KdTree()
	{
		tree = new twoDTree();
	}
	
	public boolean isEmpty()
	{
		if (this.tree.root==null)
			return true;
		else
			return false;
	}
	
	public int size()
	{
		if (this.tree.root==null)
			return 0;
		else
			return 1+tree.root.size;
	}
	
	public void insert(Point2D p)
	{
		if (p == null) throw new java.lang.NullPointerException();
		this.tree.add(p);
	}
	
	public boolean contains(Point2D p)
	{
		if (p == null) throw new java.lang.NullPointerException();
		return tree.search(p);
	}
	
	public void draw()
	{
		tree.draw();		
	}
	
	public Iterable<Point2D> range(RectHV rect)
	{
		if (rect == null) throw new java.lang.NullPointerException();
		ArrayList<Point2D> A = new ArrayList<Point2D>();
		tree.rangeSearch(tree.root, rect, A);
		return A;
	}
	
	public Point2D nearest(Point2D p)
	{
		if (p == null) throw new java.lang.NullPointerException();
		Point2DWrapper nearest_point = new Point2DWrapper(null);
		double nearest_distance = 1000;
		tree.nearest(tree.root, p, nearest_point, nearest_distance);
		System.out.println("KDTree Nearest-- "+nearest_point.point);
		return nearest_point.point;
	}
	
	public static void main(String[] args)
	{
		 	String filename = args[0];
	        In in = new In(filename);

	        // initialize the two data structures with point from standard input
	        KdTree kdtree = new KdTree();
	        while (!in.isEmpty()) {
	            double x = in.readDouble();
	            double y = in.readDouble();
	            Point2D p = new Point2D(x, y);
	            //kdtree.insert(p);
	            kdtree.insert(p);
	        }
	        
	        System.out.println(kdtree.contains(new Point2D((double)0.500000,(double) 0.000000)));
	        System.out.println(kdtree.nearest(new Point2D((double)0.024,(double) 0.654)));
	        System.out.println(kdtree.size());
	        kdtree.draw();
	}

}