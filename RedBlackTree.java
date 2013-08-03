import java.awt.Color;
import java.util.HashMap;


public class RedBlackTree {

	public RedBlackNode root;
	public RedBlackNode nil;
	public HashMap<Integer, RedBlackNode> find = new HashMap<Integer, RedBlackNode>();


	public RedBlackTree(RedBlackNode nil)
	{
		this.nil = nil; //Bottom of tree
		this.root = nil;
	}

	//
	// Insert value from Tree
	//
	public void insert(RedBlackNode z,Event e)
	{
		z.events.add(e);
		RedBlackNode y = nil;
		RedBlackNode x = root;
		while(x!=nil) 
		{
			y = x;
			if(z.key < x.key) //text inserted key, to current position in ree
			{
				x = x.left; // go left down tree
			}
			else if (z.key == x.key){ //if value is found, insert it into the event list
				x.events.add(e);
				return;
			}
			else
			{
				x = x.right;
			}
		}
		if (z.key == y.key){// probablt redudant, but if value is found, insert it into the event list
			y.events.add(e);
		}else {
			z.parent=y; // set z's parent
			if(y == nil)
			{
				root = z; //if at the top, z becomes the root
			}
			else if(z.key < y.key)
			{
				y.left = z;
			}
			else
			{
				y.right = z;
			}
			find.put(z.key, z);
			z.left = nil;
			z.right = nil;
			z.color = true;
			insertFixup(z); //fix RED Black Tree property
		}
	}

	//
	// Maintains invariance when inserted
	//
	public void insertFixup(RedBlackNode z)
	{
		RedBlackNode y;
		while(z.parent.color) {
			if(z.parent == z.parent.parent.left) { //determine if parent of inserted value is on left or right side
				y = z.parent.parent.right;
				if(y.color) { // Case 1
					z.parent.color = false; 
					y.color = false;
					z.parent.parent.color=true;
					z = z.parent.parent;
				}
				else { //Case 2
					if(z == z.parent.right){
						z = z.parent;
						leftRotate(z);
					}
					z.parent.color=false; //Case 3
					z.parent.parent.color=true;
					rightRotate(z.parent.parent); //rotates right around z's parent's parent
				}
			}
			else // Same as a above, but for when the parent of the the inserted value is on the right
			{
				y = z.parent.parent.left;
				if(y.color) { //Case 1
					z.parent.color = false;
					y.color=false;
					z.parent.parent.color = false;
					z = z.parent.parent;
				}
				else { //Case 2
					if(z == z.parent.left)	{
						z = z.parent;
						rightRotate(z);
					}
					z.parent.color = false; //Case 3
					z.parent.parent.color = true;
					leftRotate(z.parent.parent); //rotates left around z's parent's parent
				}

			}
		}

		root.color=false; //makes the root black
	}

	//
	// Remove value from Tree
	//
	public void remove(RedBlackNode z)
	{
		if (!find.containsKey(z.key)){
			return;
		}
		z = find.get(z.key); //associate value being searched for, with its actual value in tree
		RedBlackNode x;
		RedBlackNode y = z;
		y.originallyIsRed= y.color;
		if (z.left == nil){
			x = z.right;
			transplant(z,z.right); //removes node properly
		}
		else if (z.right == nil){ //checks to see if right child is empty
			x = z.left;
			transplant(z,z.left); //removes node properly
		}
		else {
			y = min(z.right); //y is minimum of right fild
			y.originallyIsRed=y.color;
			x = y.right;
			if (y.parent == z){
				x.parent = y;
			}
			else {
				transplant(y,y.right);
				y.right = z.right;
				y.right.parent = y;
			}
			transplant(z,y);
			y.left = z.left;
			y.left.parent = y;
			y.color = z.color;
		}
		if (!y.originallyIsRed){
			removeFixup(x);
		}
		find.remove(z.key);

		System.out.println("");
	}

	//
	// Maintains invariance after deletion
	//
	public void removeFixup(RedBlackNode x) {
		while (x != root && (!x.color)){
			if (x == x.parent.left){
				RedBlackNode w = x.parent.right;
				if (w.color){
					w.color = false;
					x.parent.color = true;
					leftRotate(x.parent); //rotates around x's apparent
					w = x.parent.right; //sets w as x's parents right child 
				}
				if ((w.left != null) && (w.right != null)){ //prevents null pointers ///////THIS IF STATEMENT IS WHERE THE EDGE EXAMPLE OCCURS
					if ((!w.left.color) && (!w.right.color)){
						w.color = true;
						x = x.parent;
					}
					else {
						if (!w.right.color){ // if w is black
							w.left.color = false; //set w's child to black
							w.color= true; // set w to red
							rightRotate(w); //rotate around w
							w = x.parent.right;
						}
						w.color = x.parent.color; //set w to its parents color
						x.parent.color = false; // make the parent black
						w.right.color = false; //make w's child right
						leftRotate(x.parent); //roate around the parent
						x = root;//make x the root
					}
				}
				else{
					return; //// THIS RETURN IS NOT CORRECT. THIS BUILD CAUSES NODES TO BE DELTED FROM THE TREE
				}
			}
			else if (x == x.parent.right) { //same as above
				RedBlackNode w = x.parent.left;
				if (w.color){
					w.color = false;
					x.parent.color = true;
					rightRotate(x.parent);
					w = x.parent.left;
				}
				if ((w.left != null) && (w.right != null)){ //prevents null pointers ///////THIS IF STATEMENT IS WHERE THE EDGE EXAMPLE OCCURS
					if ((!w.left.color) && (!w.right.color)){
						w.color = true;
						x = x.parent;
					}
					else {
						if (!w.left.color){
							w.right.color = false;
							w.color= true;
							leftRotate(w);
							w = x.parent.left;
						}
						w.color = x.parent.color;
						x.parent.color = false;
						w.left.color = false;
						rightRotate(x.parent);
						x = root;
					}
				}
				else {
					return; //// THIS RETURN IS NOT CORRECT. THIS BUILD CAUSES NODES TO BE DELTED FROM THE TREE
				}
			}
			x.color = false;
		}

	}

	//
	//Smart deletion for tree
	//	
	public void transplant(RedBlackNode u,RedBlackNode v)
	{
		if (u.parent == nil){ //if u values's parent is empty, make v a root
			root = v;
		}
		else if (u == u.parent.left){ //see if u is the left child
			u.parent.left = v;  //sets v as u's parents left child
		}
		else{ //sees if its the right child
			u.parent.right = v; //sets v as u's parents right child
		} 
		v.parent = u.parent; //sets v's parent as u's parent

	}


	//
	// Finds successor of a given key
	//
	public RedBlackNode succ(RedBlackNode x){
		if (x.right != null){ //if a right child exists, find the minimum on the right side
			if (x.right != nil){	
				return min(x.right);
			}

			else { //else look to the parent to find smallest value on parents greater than x
				RedBlackNode y = x.parent;
				while ((y != nil) && x==y.right){
					x=y;
					y=y.parent;
				}
				return y;
			}
		}
		return null;
	}


	//
	// Find minimum on value from any given node by going left
	//
	public RedBlackNode min(RedBlackNode x){
		if (x.left != null){ //if a left value exisits, go left
			if (x.left != nil){
				return min(x.left);
			}
			return x; //else the current value is the minimum
		}
		return null;
	}

	//
	// Find maximum on value from any given node by going right
	//
	public RedBlackNode max(RedBlackNode x){
		if (x.right != null){//if a right value exisits, go right
			if (x.right != nil){
				return max(x.right);
			}
			return x; //else the current value is the maximum
		}
		return null;
	}

	//
	// Print all values in tree by recursively look left, the right.
	//
	public void inOrder(RedBlackNode x){
		if (x != nil){
			inOrder(x.left);
			System.out.println(x.events);
			inOrder(x.right);
		}
	}

	//
	//  Performs left rotation on graph to maintain invariance
	//
	public void leftRotate(RedBlackNode x)
	{
		RedBlackNode y = x.right;
		x.right = y.left;
		if(y.left != nil)
		{
			y.left.parent = x; // make y's parents left child x
		}
		y.parent = x.parent; //make y's parent x's parent
		if(x.parent == nil)
		{
			root = y; //set y as root if x was previously the root
		}
		else if(x == x.parent.left) //if x is the left child
		{
			x.parent.left = y; //make y the left child
		}
		else//if x is the right child
		{
			x.parent.right =y;   //make y the right child
		}
		y.left = x; //x is now y's child
		x.parent = y; //x's parent is now y
	}

	
	//
	//  Performs right rotation on graph to maintain invariance. Same as the above, with right and left swapped
	//
	public void rightRotate(RedBlackNode x)
	{
		RedBlackNode y = x.left;
		x.left = y.right;
		if(y.right != nil) {
			y.right.parent = x;
		}
		y.parent = x.parent;
		if(x.parent == nil) {
			root = y;
		}
		else if(x == x.parent.right) {
			x.parent.right = y;
		}
		else {
			x.parent.left =y;
		}
		y.right = x;
		x.parent = y;
	}
}
