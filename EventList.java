//
// EVENTLIST.JAVA
// Skeleton code for your EventList collection type.
//
import java.util.*;

/**
 * @author Julian
 *
 */
/**
 * @author Julian
 *
 */
class EventList {

	Random randseq;
	int highestlevel = 0; //height of tallest pillar
	int headHeight = 1; //head height (both ints exist to prevent unnecessary traversing of levels 
	NodeList head = new NodeList(new Event((int) Double.NEGATIVE_INFINITY, "Head"), 1);
	NodeList tail = new NodeList(new Event((int) Double.POSITIVE_INFINITY, "Tail"), 1);
	public int width= 0; //how many unique elements have been inserted
	public RedBlackNode nil= new RedBlackNode(false);
	public RedBlackTree tree = new RedBlackTree(nil);


	//
	// Add an Event to the list.
	//
	public void insert(Event e)
	{
		tree.insert(new RedBlackNode(e.year, false, nil), e);
	}



	//
	// Remove all Events in the list with the specified year.
	//
	public void remove(int year)
	{
		tree.remove(new RedBlackNode(year, nil));
		//tree.inOrder(tree.root);
	}



	//
	// Find all events with greatest year <= input year
	//
	public Event [] findMostRecent(int year)
	{
		{
			Event [] temp = findRange((int) Double.NEGATIVE_INFINITY, year); //uses find helper to produce every date from the head to the the greatest year
			if (temp.length == 0){
				return null;
			}
			Event [] finalListLong = new Event [temp.length];
			int recentYear = temp[temp.length-1].year;
			int current = temp.length-1;
			int i = 0;
			while (temp[current].year == recentYear){ //takes all dates that are the same as the highest year value;
				finalListLong[i] = temp[current];
				i++;
				current--;
				if (current < 0){ //don't keep recursing below 0. avoids array out of bounds
					break;
				}
			}
			if (finalListLong.length == 0) { // if final list is empty, return null
				return null;
			}
			Event [] finalLength = new Event [i];
			System.arraycopy(finalListLong, 0, finalLength, 0, i); //create a new list with all the proper value and the correct size
			return finalLength; //return that that list
		}
	}




	//
	// Find all Events within the specific range of years (inclusive).
	//
	public Event [] findRange(int first, int last)
	{
		RedBlackNode current  = tree.root;
		Boolean found = false;
		while (!found){
			if (current.key == first){
				found = true;
			}
			else if (current.key > first){
				if (current.left != nil){
					current = current.left;
				}
				else {
					found = true;
				}
			}
			else {
				if (current.right != nil){
					current = current.right;
				}
				else {
					return new Event[0];
				}
			}
		}
		ArrayList<Event> temp = new ArrayList<Event>();
		if (current.key >= first && current.key <= last){
			temp.addAll(current.events);
		}
		while (tree.succ(current) != nil){
			current= tree.succ(current);
			RedBlackNode max = tree.max(tree.root);
			if (current.key <= last && !current.equals(tree.max(tree.root))){
				temp.addAll(current.events);
			}
			else {
				if (current.key <= last){
					temp.addAll(current.events);
				}
				Event[] finalEvent = new Event[temp.size()];
				int i = 0;
				for(Event event : temp){
					finalEvent[i] = event;
					i++;
				}
				return finalEvent;
			}
		}


		return new Event[0];
	}



	///////////////////////////////////////////////////////////////////

	//
	// Find all events whose descriptions have the specified keyword,
	// and return them in chronological order.
	//
	public Event [] findByKeyword(String keyword)
	{

		return new Event[0];
	}


}
