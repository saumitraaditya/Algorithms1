import edu.princeton.cs.algs4.StdIn;

public class Subset {
	public static void main(String[] args)
	{
		 RandomizedQueue<String> rq = new RandomizedQueue<String>();
		 String[] words = StdIn.readAllStrings();
		 for (String word:words)
		 {
			 rq.enqueue(word);
		 }
		 int k = Integer.parseInt(args[0]);
		 for (int i = 0;i<k;i++)
		 {
			 System.out.println(rq.dequeue());
		 }
		
	}

}
