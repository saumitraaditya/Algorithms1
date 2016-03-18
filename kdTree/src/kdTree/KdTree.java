package kdTree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Queue;


import java.util.ArrayList;
import java.util.List;



public class KdTree {
	twoDTree tree;
	
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
					}
					else
					{
						n.left = put(n.left, p, !n.isVertical);
						n.left.parent = n;
					}
				}
				else // partitions space horizontally.
				{
					if (p.y() >= n.point.y())
					{
						n.right = put(n.right,p,!n.isVertical);
						n.right.parent = n;
					}
					else
					{
						n.left = put(n.left, p, !n.isVertical);
						n.left.parent = n;
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
						if (p.x()>n.point.x())
							return search_tree(n.right,p);
						else
							return search_tree(n.left,p);
					}
					else
					{
						if (p.y()>n.point.y())
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
							StdDraw.line(n.point.x(),0,n.point.x(),n.parent.point.y());
						}
						else
							StdDraw.line(n.point.x(),n.parent.point.y(),n.point.x(),1);						
					}
					else
					{						
							StdDraw.setPenColor(StdDraw.BLUE);
							if (n.parent.left!=null && n.point.equals(n.parent.left.point))
							{
								StdDraw.line(0,n.point.y(),n.parent.point.x(),n.point.y());
							}
							else
								StdDraw.line(n.parent.point.x(),n.point.y(),1,n.point.y());
					}
					System.out.println("Node "+n.point+" parent "+n.parent.point);
				}				
				StdDraw.setPenColor(StdDraw.BLACK);
	            StdDraw.setPenRadius(.01);
	            n.point.draw();		
			}
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
		this.tree.add(p);
	}
	
	public boolean contains(Point2D p)
	{
		return tree.search(p);
	}
	
	public void draw()
	{
		tree.draw();		
	}
	
	/*public Iterable<Point2D> range(RectHV rect)
	{
		
	}
	
	public Point2D nearest(Point2D p)
	{
		
	}*/
	
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
	        kdtree.draw();
	}

}