package percolation;

public class Percolation {
	private int [][]Grid;
	private int top,bottom;
	private int N;
	private edu.princeton.cs.algs4.WeightedQuickUnionUF UF ;
	public Percolation (int N)
	{
		if (N<= 0) throw new java.lang.IllegalArgumentException();
		// create a 2D array to store N^2 elements
		this.Grid = new int[N][N];
		this.N = N;
		this.top = 0;
		this.bottom = N*N+1;
		// all elements of Grid are initially closed
		for (int i= 0;i<N;i++)
		{
			for(int j = 0; j< N; j++)
			{
				this.Grid[i][j] = 0;
			}
		}
		// create a union find data structure
		UF = new edu.princeton.cs.algs4.WeightedQuickUnionUF(N*N+2);
	}
	
	private int toPos(int i,int j)
	{
		i = i+1;
		j = j+1;
		return (i-1)*this.N + j;
	}
	
	public void open(int i, int j)
	{
		int grid_row,grid_col;
		if (i < 1 || i > N) throw  new java.lang.IndexOutOfBoundsException();
		if (j < 1 || j > N) throw  new java.lang.IndexOutOfBoundsException();
		grid_row = i-1;
		grid_col = j-1;
		this.Grid[grid_row][grid_col] = 1;
		/*get position values*/
		int pos = this.toPos(grid_row,grid_col);
		/* Connect this to any other open sites in vicinity
		 * do proper bound checks 
		 * 1. if site is in top row do union with TOP
		 * 2. if site is in bottom row do union with BOTTOM
		 * 3. do union with open sites in all 4 directions
		 */
		if (grid_row == 0)
		{
			this.UF.union(this.top, pos);
		}
		else if (grid_row == this.N-1)
		{
			this.UF.union(this.bottom, pos);
		}
		else
		{
			/*cover left and right boundaries*/
			if (grid_col == 0)
			{
				if (this.isOpen(i-1,j)) //top
				{
					this.UF.union(this.toPos(grid_row-1, grid_col), pos);
				}
				if (this.isOpen(i+1, j)) //bottom
				{
					this.UF.union(this.toPos(grid_row+1, grid_col), pos);
				}
				if (this.isOpen(i, j+1)) //right
				{
					this.UF.union(this.toPos(grid_row, grid_col+1), pos);
				}
			}
			else if (grid_col == N-1)
			{
				if (this.isOpen(i-1, j)) //top
				{
					this.UF.union(this.toPos(grid_row-1, grid_col), pos);
				}
				if (this.isOpen(i+1, j)) //bottom
				{
					this.UF.union(this.toPos(grid_row+1, grid_col), pos);
				}
				if (this.isOpen(i, j-1)) //left
				{
					this.UF.union(this.toPos(grid_row, grid_col-1), pos);
				}
			}
			else
			{
				if (this.isOpen(i-1, j)) //top
				{
					this.UF.union(this.toPos(grid_row-1, grid_col), pos);
				}
				if (this.isOpen(i+1, j)) //bottom
				{
					this.UF.union(this.toPos(grid_row+1, grid_col), pos);
				}
				if (this.isOpen(i, j-1)) //left
				{
					this.UF.union(this.toPos(grid_row, grid_col-1), pos);
				}
				if (this.isOpen(i, j+1)) //right
				{
					this.UF.union(this.toPos(grid_row, grid_col+1), pos);
				}
			}
		}
				
	}
	
	public boolean isOpen (int i, int j)
	{
		if (i < 1 || i > N) throw  new java.lang.IndexOutOfBoundsException();
		if (j < 1 || j > N) throw  new java.lang.IndexOutOfBoundsException();
		i = i-1;
		j = j-1;
		if (this.Grid[i][j]==1)
			return true;
		else
			return false;
	}
	
	public boolean isFull(int i, int j)
	{
		if (i < 1 || i > N) throw  new java.lang.IndexOutOfBoundsException();
		if (j < 1 || j > N) throw  new java.lang.IndexOutOfBoundsException();
		i = i-1;
		j = j-1;
		int pos = this.toPos(i, j);
		if (this.UF.connected(this.top, pos))
		{
			return true;
		}
		else
			return false;
	}
	
	public boolean percolates()
	{
		if (this.UF.connected(this.top, this.bottom))
			return true;
		else
			return false;
	}

}