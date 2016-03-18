import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;
public class RandomizedQueue<Item> implements Iterable<Item> {
	
	private Item[] A;
	private int N;
	
	public RandomizedQueue()
	{
		A = (Item[]) new Object[2];
		N = 0;
	}
	
	private void resize(int size)
	{
		Item[] temp =  (Item[]) new Object[size];		
		int j = 0;
		//System.out.println("items\t"+Integer.toString(N));
		//System.out.println("Array Size\t"+Integer.toString(A.length));
		for (int k=0;k<A.length;k++)
		{
			//System.out.println("j\t"+Integer.toString(j)+"\tk\t"+Integer.toString(k));
			if (A[k]!=null)
				temp[j++]=A[k];
		}
		
		A = temp;
	}
	
	private class randomArrayIterator implements Iterator<Item>
	{
		int curr;
		Item[] arr = (Item[])new Object[N];
		private randomArrayIterator()
		{
			curr=0;
			
			for (int i = 0;i<N;i++)
			{
				arr[i]=A[i];
			}		
			StdRandom.shuffle(arr);
			
		}
		public boolean hasNext()
		{
			return (curr<N);
		}
		public void remove() 
		{
			throw new UnsupportedOperationException();
		}
		public Item next()
		{
			if (!hasNext()) throw new NoSuchElementException();			
			return arr[curr++];				
		}
	}
	
	public boolean isEmpty()
	{
		return (N==0);
	}
	
	public int size()
	{
		return N;
	}
	
	public void enqueue(Item item)
	{
		if (item==null)
			throw new java.lang.NullPointerException();
		if (N==A.length)
		{
			resize(2*A.length);
		}
		A[N++]=item;
	}
	
	public Item dequeue()
	{
		if (isEmpty())
			throw new java.util.NoSuchElementException();
		int index = edu.princeton.cs.algs4.StdRandom.uniform(N);
		Item item = A[index];
		Item temp;
		A[index]=null;
		if (index!=N-1)
			{
				temp = A[N-1];
				A[N-1] = A[index];
				A[index] = temp;
			}
		N--;
		return item;
	}
	public Item sample()
	{	if (isEmpty())
			throw new java.util.NoSuchElementException();
		int index = edu.princeton.cs.algs4.StdRandom.uniform(N);
		return A[index];
	}
	public Iterator<Item> iterator()
	{
		return new randomArrayIterator();
	}
	public static void main(String[] args)
	{
		RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
		int i = 0;
		while (i<10)
		{
			int c1=edu.princeton.cs.algs4.StdRandom.uniform(1000);
			int val = edu.princeton.cs.algs4.StdRandom.uniform(1000);
			//if (c1<250)
				rq.enqueue(val);
			//else if (!rq.isEmpty())
				//rq.dequeue();
			i++;
				
		}
		Iterator<Integer> it = rq.iterator();
		System.out.println();
		while (it.hasNext())
		{
			
			System.out.print("\t"+it.next().toString());
		}
		Iterator<Integer> it2 = rq.iterator();
		System.out.println();
		while (it2.hasNext())
		{
			
			System.out.print("\t"+it2.next().toString());
		}
		
		
	}
}
