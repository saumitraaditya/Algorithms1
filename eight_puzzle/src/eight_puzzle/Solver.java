package eight_puzzle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;

import java.util.LinkedList;
import java.util.Stack;
public class Solver {
	private MinPQ<searchNode> pq_original = new MinPQ<searchNode>();
	private MinPQ<searchNode> pq_twin = new MinPQ<searchNode>();
	private searchNode original;
	private searchNode twin;
	private searchNode solution;
	private Stack<Board> trace = new Stack<Board>();
	private boolean solvable = false;
	private boolean notSolvable = false;
	public Solver(Board initial)
	{
		original = new searchNode(initial);
		twin = new searchNode(initial.twin());
		// put the original and twin boards in there respective queues
		pq_original.insert(original);
		pq_twin.insert(twin);
	}
	
	private class searchNode implements Comparable<searchNode>
	{
		private Board board;
		private searchNode prev;
		private int moves;
		private int priority;
		
		public searchNode(Board board)
		{
			this.board = board;
			this.prev = null;
			moves = 0;
			priority = 0;
		}
		
		public int compareTo(searchNode b)
		{
			double priority_B = b.priority;
			
			if (this.priority < priority_B)
				return -1;
			else if (this.priority > priority_B)
				return 1;
			else
				return 0;
		}
	}
	
	public boolean isSolvable()
	{
		/* Solve in lock step fashion
		 * do till the pq is empty or goal is reached
		 * deque a item, if it is not goal
		 * enque its siblings*/
		 if (solvable==true)
		    return true;
         else if (this.notSolvable)
            return false;
		searchNode fromOrig = pq_original.delMin();
		searchNode fromTwin = pq_twin.delMin();
		//int counter = 0;
		while(!fromOrig.board.isGoal() && !fromTwin.board.isGoal() /*&& counter < 5*/)
		{
			
			//get list of siblings for original and put them in pq
			for (Board board:fromOrig.board.neighbors())
			{
				if (fromOrig.prev==null || !board.equals(fromOrig.prev.board))
				{
					searchNode temp = new searchNode(board);
					temp.moves = fromOrig.moves+1;
					temp.prev = fromOrig;
					temp.priority = temp.board.manhattan()+temp.moves;
					pq_original.insert(temp);
				}
			
			}
			//get list of siblings in twin and put them in pq
			for ( Board board:fromTwin.board.neighbors())
			{
				if (fromTwin.prev == null || !board.equals(fromTwin.prev.board))
				{
					searchNode temp = new searchNode(board);
					temp.moves = fromTwin.moves+1;
					temp.prev = fromTwin;
					temp.priority = temp.board.manhattan()+temp.moves;
					pq_twin.insert(temp);
				}
			}
			fromOrig = pq_original.delMin();
			
			fromTwin = pq_twin.delMin();
			/*System.out.println(fromOrig.manhattan());
			System.out.println(fromOrig);
			System.out.println("counter "+Integer.toString(counter));
			counter++;*/
		}
		if (fromOrig.board.isGoal())
			{
				solution = fromOrig;
				this.solvable = true;
				return true;
			}
		else 
			{
			solution = null;
			this.solvable = false;
			this.notSolvable = true;
			return false;
			}
					
	}
	
	public int moves()
	{
	    if (solvable)
	        return solution.moves;
	    else if (this.isSolvable())
		    return solution.moves;
	    else if (this.notSolvable)
	        return -1;
	    else
	        return -1;
	}
	
	public Iterable<Board> solution()
	{
	    
		searchNode temp;
         if (solvable)
            temp = solution;           
	     else if (this.isSolvable())
		    temp = solution;
	    else if (this.notSolvable)
	        return null;
         else
            return null;
		trace.push(temp.board);
		while (temp.prev != null)
		{
			trace.push(temp.prev.board);
			temp = temp.prev;
		}
		LinkedList<Board> ll = new LinkedList<Board>();
		while (!trace.empty())
		{
			ll.add(trace.pop());
		}
		return ll;
	}
	
	public static void main(String[] args) {

	    // create initial board from file
	    In in = new In(args[0]);
	    int N = in.readInt();
	    int[][] blocks = new int[N][N];
	    for (int i = 0; i < N; i++)
	        for (int j = 0; j < N; j++)
	            blocks[i][j] = in.readInt();
	    Board initial = new Board(blocks);

	    // solve the puzzle
	    Solver solver = new Solver(initial);

	    // print solution to standard output
	    if (!solver.isSolvable())
	        StdOut.println("No solution possible");
	    else {
	        StdOut.println("Minimum number of moves = " + solver.moves());
	        for (Board board : solver.solution())
	            StdOut.println(board);
	    }
	}

}