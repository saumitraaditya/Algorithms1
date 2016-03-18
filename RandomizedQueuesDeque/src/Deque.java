import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> 
{
	
	private class Node{	
		private Item item;
		private Node next=null;
		private Node prev = null;
	}
	private Node first;
	private Node last;
	private int N;
	
	private class DequeIterator implements Iterator<Item>
	{
		private Node current = first;
		public boolean hasNext()
		{
			return 	current != null;
		}
		public void remove() {throw new UnsupportedOperationException();}
		public Item next()
		{
			if (!hasNext()) throw new NoSuchElementException();
			Item item = current.item;
			current=current.next;
			return item;
		}
	}
	public Deque()
	{
		first = null;
		last = null;
		N = 0;
	}
	public boolean isEmpty()
	{
		return (N==0);
	}
	
	public int size()
	{
		return N;
	}
	public void addFirst(Item item)
	{
		if (item==null)
			throw new java.lang.NullPointerException();
		Node oldfirst = first;
		first = new Node();
		first.item = item;
		if (oldfirst==null)
		{
			last = first;
		}
		else
		{
			first.next=oldfirst;
			oldfirst.prev=first;
		}
		N++;
	}
	public void addLast(Item item)
	{
		if (item==null)
			throw new java.lang.NullPointerException();
		Node newlast= new Node();
		newlast.item = item;
		if (first==null)
		{
			first = newlast;
			last = newlast;
		}
		else
		{
			last.next=newlast;
			newlast.prev=last;
			last = newlast;
		}
		N++;
	}
	public Item removeFirst()
	{
		if (isEmpty())
			throw new java.util.NoSuchElementException();
		
		Item item = first.item;
		if (first==last)
		{
			first=null;
			last=null;
		}
		else
		{
			first = first.next;
			first.prev=null;
		}
		N--;
		return item;
	}
	public Item removeLast()
	{
		if (isEmpty())
			throw new java.util.NoSuchElementException();
		Item item = last.item;
		if (first==last)
		{
			first=null;
			last=null;
		}
		else
		{
			last=last.prev;
			last.next=null;
		}
		N--;
		return item;
	}
	public Iterator<Item> iterator()
	{
		return new DequeIterator();
	}
	public static void main(String[] args)
	{
		
	}
	
}
