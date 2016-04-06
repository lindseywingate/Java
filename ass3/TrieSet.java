import java.util.Iterator;

interface TrieSet extends Iterable<String>
{
	public boolean add();
	public boolean clear();
	public boolean contains();
	public boolean remove();
	public boolean size();
	public String toString();
	public Iterator<String> iterator();
}
