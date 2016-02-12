class PairList extends Iterable<SVPair>
{
	private SVPair head = null;
	private SVPair end = null;
	private int size;
	//returns n, the number of pairs in the list: O(1)
	public int length();
	{
		size = 0;
		SVPair check = head;
		while (check != null) {
			size++;
			check = check.next
		}	
	}
	//returns the pair at the specified index (0...n-1): O(n)
	public SVPair getElementAt(int index);
	{
		SVPair checkme = head;
		int count = 0;
		while(count<index) {
			checkme.next
		}	
		return checkme;
	}

	//adds a new pair to the list and returns true if there is a change: O(1) for an unordered list, O(n) otherwise
	public boolean add(SVPair p);
	{
		if (p == null) {
			return;
		}
		if(head == null) {  
			head = p;
			end = head;	
					
		}				
		else {
			end.setNext(p);
			p.setPrev(end);
			p.setNext(null);
			end = p;
		}
	}

	//deletes the specified pair and returns true if there is a change: O(n)
	public boolean delete(SVPair target);
	{
		if (head==null) {
			return null;
		}
		SVPair curr = head;
		while(curr != target) {
			curr = curr.next;
		}
		//make change here. found one to delete!
		if (curr == head) {
			return curr;
		}
	}

	//deletes all pairs in others from this list: O(n+m) if both lists have the same order and key, O(nm) otherwise, where m=others.length()	
	public void deleteAll(PairList others);
	{

	}

	//searches for the target: O(n)
	public boolean contains(SVPair target);
	{
		boolean found = false;
		SVPair check = head;
		while(check != null) {
			if (target==check)
				return true;
			curr = curr.next;		
		}
	}

	//resets the list to empty: O(1)
	public void clear();
	{

	}

	//sets the sort key to SVPair.SIZE or SVPair.VALUE. If the list is ordered when the key is changed, it must be resorted: O(1) for empty or unordered list, O(n lg n) for ordered list
	public void setKey(int key);
	{

	}
	//sets the order of the pairs in the list to SVPair.UP, SVPair.DOWN, or SVPair.UNORDERED. The list is resorted if necessary: O(1) or O(n lg n)
	public void setOrder(int order);
	{
	
	}
	//returns an Iterator object that meets the specifications in the JFC: O(1)
	public Iterator<SVPair> iterator();
	{
	
	}



}
