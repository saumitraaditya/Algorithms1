package eight_puzzle;
import edu.princeton.cs.algs4.In;
import java.util.LinkedList;


public class Board {
	private int[][] board;
	private int dimension;
	
	
	private int position(int row, int col)
	{
		// 0 based indexing
		return (row*this.dimension+col+1);
	}
	
	public Board(int [][]blocks)
	{
		this.board = new int[blocks.length][blocks[0].length];
		for (int i = 0;i<blocks.length;i++)
		{
			for (int j = 0;j<blocks[0].length;j++)
			{
				this.board[i][j]=blocks[i][j];
			}
		}		
		this.dimension = blocks[0].length;		
	}
	
	
	
	
	public int dimension()
	{
		return this.dimension;
	}
	
	private int manhattanMoves(int val,int curr_row,int curr_col)
	{
		// input row and cols are zero indexed
		int correct_row = (int)Math.ceil((double) val/(double)this.dimension)-1;
		int mod_val = val%this.dimension;
		int correct_col = 0;
		// make corrections
		if (mod_val == 0)
			correct_col = this.dimension -1;
		else
			correct_col = mod_val -1;
		int vertical_moves = Math.abs(correct_row - curr_row);
		int horizontal_moves = Math.abs(correct_col - curr_col);
		
		return vertical_moves+horizontal_moves;
	}
	
	public int hamming()
	{
		int blocksInWrongPosition = 0;
		for (int i = 0;i<this.dimension;i++)
		{
			for (int j = 0;j<this.dimension;j++)
			{
				if (this.board[i][j]!=0 && this.board[i][j]!=this.position(i, j) )
				{
						blocksInWrongPosition++;
				}
			}
		}
		return blocksInWrongPosition;
		
	}
	
	public int manhattan()
	{
		int moves_required = 0;
		for (int i = 0;i<this.dimension;i++)
		{
			for (int j = 0;j<this.dimension;j++)
			{
				if (this.board[i][j]!=0 )
				{
					moves_required+=manhattanMoves(this.board[i][j],i,j);
				}
			}
		}
		return moves_required;
	}
	
	public boolean isGoal()
	{
		boolean status = true;
		for (int i = 0;i<this.dimension;i++)
		{
			for (int j = 0;j<this.dimension;j++)
			{
				if (this.board[i][j]!=this.position(i, j) )
				{
					if (i!=this.dimension-1 || j != this.dimension-1)
						status = false;
				}
			}
		}
		return status;
	}
	
	private Board sibling()
	{
		int [][]clone = new int[this.dimension][this.dimension];
		for (int i = 0;i<this.dimension;i++)
		{
			for (int j = 0;j<this.dimension;j++)
			{
				clone[i][j]=this.board[i][j];
			}
		}
		Board sibling = new Board(clone);
		return sibling;
	}
	
	public Board twin()
	{
		int [][]clone = new int[this.dimension][this.dimension];
		for (int i = 0;i<this.dimension;i++)
		{
			for (int j = 0;j<this.dimension;j++)
			{
				clone[i][j]=this.board[i][j];
			}
		}
		int origin_row = edu.princeton.cs.algs4.StdRandom.uniform(0,this.dimension);
		int origin_col = edu.princeton.cs.algs4.StdRandom.uniform(0, this.dimension);
		int target_row,target_col;
		do
		{
			origin_row = edu.princeton.cs.algs4.StdRandom.uniform(0,this.dimension);
			origin_col = edu.princeton.cs.algs4.StdRandom.uniform(0, this.dimension);
		}while(this.board[origin_row][origin_col]==0);
		// can I swap with one at the top
		if (origin_row > 0 && this.board[origin_row-1][origin_col]!=0)
		{
			target_row = origin_row-1;
			target_col = origin_col;
		}
		//can I swap with one below
		else if (origin_row < this.dimension()-1 && this.board[origin_row+1][origin_col]!=0)
		{
			target_row = origin_row+1;
			target_col = origin_col;
		}
		// can I swap with one at left
		else if (origin_col >0  && this.board[origin_row][origin_col-1]!=0)
		{
			target_row = origin_row;
			target_col = origin_col-1;
		}
		else
		{
			target_row = origin_row;
			target_col = origin_col+1;
		}
		// swap a pair of blocks
		/*int origin_row = edu.princeton.cs.algs4.StdRandom.uniform(0,this.dimension);
		int origin_col = edu.princeton.cs.algs4.StdRandom.uniform(0, this.dimension);
		int target_row = edu.princeton.cs.algs4.StdRandom.uniform(0,this.dimension);
		int target_col = edu.princeton.cs.algs4.StdRandom.uniform(0, this.dimension);
		if (target_row == origin_row && target_col==origin_col)
		{
			if (target_col>0)
				target_col-=1;
			else
				target_col+=1;
		}
		if (this.board[origin_row][origin_col]==0)
		{
			if (origin_row>0)
				origin_row-=1;
			else
				origin_row+=1;
		}
		else if (this.board[target_row][target_col]==0)
		{
			if (target_row>0)
				target_row-=1;
			else
				target_row+=1;
		}*/
		// do the actual swap
		int temp = clone[target_row][target_col];
		clone[target_row][target_col] = clone[origin_row][origin_col];
		clone[origin_row][origin_col] = temp;
		Board twin = new Board(clone);
		return twin;
	}
	
	public boolean equals(Object y)
	{
		if (y == this)
			return true;
		else if (! (y instanceof Board))
			return false;
		Board that = (Board) y;
		boolean status = true;
		if (that.dimension() != this.dimension())
			return false;
		for (int i = 0;i<this.dimension;i++)
		{
			for (int j = 0;j<this.dimension;j++)
			{
				if (this.board[i][j]!=that.board[i][j])
					status = false;
			}
		}
					
		return status;
	}
	
	public Iterable<Board> neighbors()
	{
		LinkedList<Board> neighbors = new LinkedList<Board>();
		// find the location of gap block
		int gap_row=0,gap_col=0;
		for (int i = 0;i<this.dimension;i++)
		{
			for (int j = 0;j<this.dimension;j++)
			{
				if (this.board[i][j]==0)
				{
					gap_row = i;
					gap_col = j;
					break;
				}
			}
		}
		// can I move gap up, if yes move
		if (gap_row>0)
		{
			Board up = this.sibling();
			up.board[gap_row][gap_col]=up.board[gap_row-1][gap_col];
			up.board[gap_row-1][gap_col]=0;
			neighbors.add(up);
		}
		// can i move gap down , if yes move'
		if (gap_row<this.dimension-1)
		{
			Board down = this.sibling();
			down.board[gap_row][gap_col]=down.board[gap_row+1][gap_col];
			down.board[gap_row+1][gap_col]=0;
			neighbors.add(down);
		}
		// can i move gap left, if yes move
		if (gap_col>0)
		{
			Board left = this.sibling();
			left.board[gap_row][gap_col]=left.board[gap_row][gap_col-1];
			left.board[gap_row][gap_col-1]=0;
			neighbors.add(left);
		}
		// can i move right, if yes move
		if (gap_col<this.dimension-1)
		{
			Board right = this.sibling();
			right.board[gap_row][gap_col]=right.board[gap_row][gap_col+1];
			right.board[gap_row][gap_col+1]=0;
			neighbors.add(right);
		}
		
		return neighbors;
	}
	
	
	public String toString()
	{
		StringBuffer strBuf = new StringBuffer("\n"+Integer.toString(this.dimension));
		for (int i = 0;i<this.dimension;i++)
		{
			strBuf.append("\n");
			for (int j = 0;j<this.dimension;j++)
			{
				strBuf.append(Integer.toString(this.board[i][j])+" ");
			}
		}
		return strBuf.toString();
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
	    System.out.println(initial);
	    
	    System.out.println(initial.hamming());
	    System.out.println(initial.manhattan());
	    System.out.println(initial.isGoal());
	    
	    for (Board b:initial.neighbors())
	    {
	    	System.out.println(b);
	    }
	    
	    }
	}

	
	
