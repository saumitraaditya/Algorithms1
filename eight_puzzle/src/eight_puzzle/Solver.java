package eight_puzzle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;

import java.util.LinkedList;
import java.util.Stack;
public class Solver {
	MinPQ<Board> pq_original = new MinPQ<Board>();
	MinPQ<Board> pq_twin = new MinPQ<Board>();
	Board original;
	Board twin;
	private Board solution;
	private Stack<Board> trace = new Stack<Board>();
	public Solver(Board initial)
	{
		original = initial;
		twin = original.twin();
		// put the original and twin boards in there respective queues
		pq_original.insert(original);
		pq_twin.insert(twin);
	}
	
	public boolean isSolvable()
	{
		/* Solve in lock step fashion
		 * do till the pq is empty or goal is reached
		 * deque a item, if it is not goal
		 * enque its siblings*/
		Board fromOrig = pq_original.delMin();
		Board fromTwin = pq_twin.delMin();
		//int counter = 0;
		while(!fromOrig.isGoal() && !fromTwin.isGoal() /*&& counter < 5*/)
		{
			
			//get list of siblings for original and put them in pq
			for (Board board:fromOrig.neighbors())
			{
				//if (!board.equals(fromOrig.prevBoard))
				{
					pq_original.insert(board);
				}
			}
			//get list of siblings in twin and put them in pq
			for (Board board:fromTwin.neighbors())
			{
				//if (!board.equals(fromTwin.prevBoard))
				{
					pq_twin.insert(board);
				}
			}
			fromOrig = pq_original.delMin();
			
			fromTwin = pq_twin.delMin();
			/*System.out.println(fromOrig.hamming());
			System.out.println(fromOrig);
			System.out.println("counter "+Integer.toString(counter));
			counter++;*/
		}
		if (fromOrig.isGoal())
			{
				solution = fromOrig;
				return true;
			}
		else 
			{
			solution = null;
			return false;
			}
					
	}
	
	public int moves()
	{
		return solution.moves;
	}
	
	public Iterable<Board> solution()
	{
		Board temp = solution;
		trace.push(temp);
		while (temp.prevBoard != null)
		{
			trace.push(temp.prevBoard);
			temp = temp.prevBoard;
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
