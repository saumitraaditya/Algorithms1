package percolation;


public class PercolationStats {
	
	//Datastructure to hold the results
	private double[] results;
	private int arr_counter = 0;
	
	
	public PercolationStats(int N, int T)
	{
	 
		if (N < 0) throw new java.lang.IllegalArgumentException();
		int openSites;
		int row;
		int col;
		results = new double[T];
		for (int i = 0; i < T ; i++)
		{
			Percolation P = new Percolation(N);
			openSites = 0;
			do 
			{
				//choose random row and column
				do
				{
					row = edu.princeton.cs.algs4.StdRandom.uniform(1, N+1);
					col = edu.princeton.cs.algs4.StdRandom.uniform(1, N+1);
					
					
				}while(P.isOpen(row,col)!=false);
				// open the site
				P.open(row, col);
				// increment openSites counter
				openSites+=1;
			} while((P.percolates() != true) && (openSites < N*N+1) );
			results[arr_counter++]=(openSites/Math.pow(N, 2));
		}
	}
	
	public double stddev()
	{
		double stddev = edu.princeton.cs.algs4.StdStats.stddev(results);
		return stddev;
	}
	
	public double mean()
	{
		
		double mean = edu.princeton.cs.algs4.StdStats.mean(results);
		return mean;
	}
	
	public double confidenceLo()
	{
		double mean = mean();
		double stddev = stddev();
		return (mean - ((1.96*stddev)/Math.sqrt(arr_counter+1)));
	}
	
	public double confidenceHi()
	{
		double mean = mean();
		double stddev = stddev();
		return (mean + ((1.96*stddev)/Math.sqrt(arr_counter+1)));
	}
	
	public static void main(String [] args)
	{
		int N = Integer.parseInt(args[0]);
		int T = Integer.parseInt(args[1]);
		PercolationStats P = new PercolationStats(N,T);
		System.out.println("mean\t"+P.mean()+"\nstddev\t"+P.stddev());
		System.out.println("95% confidence interval\t"+P.confidenceLo()+", "+P.confidenceHi());
	}

}