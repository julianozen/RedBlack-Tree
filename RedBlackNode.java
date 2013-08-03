import java.util.ArrayList;


public class RedBlackNode {
	
	public int key;
	public boolean color; // Color for Red Black treated as Boolean
	public RedBlackNode left;
	public RedBlackNode right;
	public RedBlackNode parent;
	public boolean originallyIsRed;
	public ArrayList<Event> events = new ArrayList<Event>(); 

	
	
	public RedBlackNode(int key, RedBlackNode nil)
	{
		this.key = key;
		left = nil;
		right = nil;
		parent = nil;
	}

	
	public RedBlackNode(int key, boolean color, RedBlackNode nil)
	{
		this.key = key;
		this.color = color;
		left = nil;
		right = nil;
		parent = nil;
	}
	
	
	public RedBlackNode(boolean color)
	{
		this.color = color;
		key = Integer.MAX_VALUE;
		left = null;
		right = null;
		parent = null;
	}
	
}
