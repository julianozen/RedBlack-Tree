
public class NodeList {
	public Event event;
	public Node[] list;
	public int height;
	int description; 

/**
 * Essentially and array list of node. 
 * This class too perhaps did not need to exist, after removing unused features of it. 
 * 
 */

	public NodeList(Event event, int height)
	{
		description = event.description.hashCode();
		list = new Node[height];
		this.event= event;	
		this.height = height;
		for (int i = 0; i <= height-1; i++){
			list[i] = new Node(event, null,i);
		}
	}
	

	

}
