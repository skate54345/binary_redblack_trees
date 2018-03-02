import java.util.NoSuchElementException;

public class RedBlackTree<K extends Comparable<K>,E> implements IBinarySearchTree<K, E>
{
	static BinarySearchTree tree = new BinarySearchTree<>();
	int size = 0; //initializes variables
	int height = 0;
	Node root;

	public Node getReplacement(Node input) //helper method for finding replacement node
	{
		Node newParent = input; //sets up nodes
		Node replacement = input;
		Node current = input.rightChild; //sets current to right child

		while (current != null) //while current exists
		{
			newParent = replacement; //set parent to replacement
			replacement = current; //replacement to current
			current = current.leftChild; //current to left child
		}
		if(replacement != input.rightChild)
		{
			newParent.leftChild = replacement.rightChild; //parents left child to replacements right
			replacement.rightChild = input.rightChild; //replacements right to currents right
		}
		return replacement;
	}


	public String moveInOrder(Node current, String str) //in order traversal
	{
		if (current == null) //if current doesnt exist
		{
			return str; //return string
		}
		else //if current exists
		{
			current.color = "Black"; //sets to black
			str = moveInOrder(current.leftChild, str); //traverse left child
			if (str != "") //while not empty
				{
				str += ", "; //add comma
				}
			str += current.print(); //add current to string
			String temp = moveInOrder(current.rightChild, ""); //traverse right child
			if (temp == "") //if empty string
			{
				return str; //just return string
			}
			else
			{
				return str + ", " + temp; //otherwise add comma
			}
		}
	}

	public String buildString(Node current)
	{
		if (current == null)
		{
			return ""; //stops from adding unnecessary commas
		}
		else //if current exists
		{
			String result = "[" + current.color + ", " + current.key + ", "; //starts with current string and color
			if (current.leftChild == null) //if there is no left child
			{
			result += "null"; //result is string stating null
			}
			else
			{
			result += buildString(current.leftChild); //add left child to string
			}
			result += ", "; //add comma
			if (current.rightChild == null) //if there is no right child
			{
			result += "null"; //state null
			}
			else
			{
			result += buildString(current.rightChild); //add right
			}
			result += "]"; //close bracket

			result += buildString(current.leftChild); //traverse left child
			result += buildString(current.rightChild); //traverse right child
			return result;
		}
	}


	@Override
	public void put(K key, E element)
	{
		Node makeNode = new Node(key, element); //creates node
		makeNode.color = "Red"; //initializes as red
		size++; //increments size

		if (root == null)
		{
			root = makeNode;
			root.color = "Black"; //make root black
		}
		else
		{
			Node current = root; //sets current to root
			Node parent;
			while(true)
			{
				parent = current;
				if(key.compareTo((K) current.key)<1) //check for left or right
				{
					current = current.leftChild; //change current to left child
					if (current == null) //if left child has no children
					{
						parent.leftChild = makeNode; //put on left
						return;
					}
				}
				else
				{
					current = current.rightChild;
					if (current == null) //if right child has no children
					{
						parent.rightChild = makeNode; //put on right
						return;
					}
				}
			}
		}
	}

	public boolean delete(K key)
	{
		Node current = root; //creates local current and parent
		Node parent = root;
		boolean isLeft = true; //start off as left
		size--; //decrement size

		while(current.key != key) //while current isn't key we're looking for
		{
			parent = current; //sets parent to current
			if(key.compareTo((K) current.key) < 1) //if less than
			{
				isLeft = true; //stay on left
				current = current.leftChild;
			}
			else
			{
				isLeft = false; //now is right
				current = current.rightChild; //set equal to right
			}
			if (current == null)
			{
				return false; //false if no current
			}
		}
		if (current.leftChild == null && current.rightChild == null) //if doesnt have left or right child
		{
			if(current == root) //and the current is the root
			{
				root = null; //set root to null
			}
			else if (isLeft == true)
			{
				parent.leftChild = null; //deletes it
			}
			else parent.rightChild = null; //else gets rid of right child
		}
		else if (current.rightChild == null)//if there is no right child
		{
			if (current == root)
			{
				root = current.leftChild; //set root to currents left if current is root
			}
			else if (isLeft == true)
			{
				parent.leftChild = current.leftChild; //set parents left to currents
			}
			else parent.rightChild = current.leftChild;
		}
		else if (current.leftChild == null)//if there is no left child
		{
			if(current == root)
			{
				root = current.rightChild;
			}
			else if (isLeft == true)
			{
				parent.leftChild = current.rightChild; //if left sent parents left to currents right
			}
			else parent.rightChild = current.leftChild;
		}
		else //if 2 children are present
		{
			Node replacement = getReplacement(current); //sets replacement as return of helper method

			if(current == root)
			{
				root = replacement; //sets root to replacement if it is the current
			}
			else if (isLeft == true)
			{
				parent.leftChild = replacement; //set left to replacement
			}
			else
			{
				 parent.rightChild = replacement; //sets parent right to replacement
				 replacement.leftChild = current.leftChild;//set replacement left to currents
			}

		}
		return true;
	}

	@Override
	public E get(K key) throws NoSuchElementException
	{
		Node current = root;

		while (current.key != key) //key not equal to key we're looking for
		{
			if (key.compareTo((K) current.key)<1) //if key is less than current
			{
				current = current.leftChild; //set left to current
			}
			else current = current.rightChild; //otherwise set to right

			if(current == null) //if no current
			{
				throw new NoSuchElementException();
			}
		}
		return (E) (current.element); //return element of current node
	}

	@Override
	public int size() //getter for size
	{
		return size;
	}


    public String toString()
    {
    	if (size == 0)
    	{
    		return "[]"; //returns empty brackets if empty
    	}
    	else
    		{
    		return "[" + moveInOrder(root, "") + "]"; //calls move in order with added unnecessary brackets
    		}
    }

    public int findHeight(Node root)
    {
    	if (root == null)
    	{
    		return 0; //0 if empty
    	}
    	else return (1+ Math.max(findHeight(root.leftChild),findHeight(root.rightChild))); //gets max of left and right
    }
	@Override
	public int getHeight() //getter for height
	{
		if (size == 0)
			{
			return 0;
			}
		else
			{
			return findHeight(root)-1; //calls helper method -1
			}
	}


	public String getTreeString() //getter for string representation
	{
		if (size == 0)
		{
			return "[]";
		}
		else
		{
			return buildString(root); //calls helper method with root as parameter
		}
	}
}

