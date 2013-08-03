
public class Node {

	public Event event;
	public NodeList next;
	public int level;
	
	/**
	 * On each level of the array list contains a node class that points to the next pillar at each level as well as holds its individual level and event
	 * 
	 * orginally this class was to be used exclusively, so it had more features. change to made codebase perhaps obfuscated the need for this class, but 
	 * it still makes my code work succinctly 
	 */


	public Node(Event event, NodeList next, int level)
	{
		this.event= event;
		this.next = next;
		this.level = level;
		
	}
}
