import java.util.NoSuchElementException;

public class BinarySearchTree<K extends Comparable<K>,E> implements IBinarySearchTree<K, E>
{
	int size = 0; //initializes size and height
	int height = 0;
	Node root; //initializes root

	public Node getReplacement(Node input) //helper method for finding replacement node
	{
		Node newParent = input; //initialize nodes
		Node replacement = input;
		Node current = input.rightChild; //set current to right child

		while (current != null) //while current exists
		{
			newParent = replacement; //set parent to replacement
			replacement = current; //replacement to current
			current = current.leftChild; //current to left child
		}
		if(replacement != input.rightChild)
		{
			newParent.leftChild = replacement.rightChild; //parent left child to replacement right
			replacement.rightChild = input.rightChild; //replacement right to current nodes right
		}
		return replacement;
	}


	public String moveInOrder(Node current, String str) //in order traversal
	{
		if (current == null) //if no current
		{
			return str; //just return string
		}
		else //if current exists
		{
			str = moveInOrder(current.leftChild, str); //traverse left child
			if (str != "")
				{
				str += ", "; //add comma
				}
			str += current.print(); //add current to string
			String temp = moveInOrder(current.rightChild, ""); //traverse right child
			if (temp == "") //if empty
			{
				return str; //return the string
			}
			else
			{
				return str + ", " + temp; //add string and temp with comma between
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
			String result = "["+ current.key + ", "; //starts with current string
			if (current.leftChild == null) //if no left child
			{
			result += "null"; //result is string stating null
			}
			else
			{
			result += buildString(current.leftChild); //add left child to string
			}
			result += ", "; //add comma
			if (current.rightChild == null) //if no right child
			{
			result += "null"; //state null
			}
			else
			{
			result += buildString(current.rightChild); //add right
			}
			result += "]"; //close

/*			result += buildString(current.leftChild); //traverse left child
			result += buildString(current.rightChild); //traverse right child
*/			return result;
		}
	}


	@Override
	public void put(K key, E element)
	{
		Node makeNode = new Node(key, element);
		size++; //increments size

		if (root == null)
		{
			root = makeNode; //creates node if space
		}
		else
		{
			Node current = root; //sets current to root
			Node parent; //initializes parent
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
		Node current = root;
		Node parent = root;
		boolean isLeft = true; //boolean to decide left or right, starts as left
		size--; //decrements size

		while(current.key != key) //while current isn't key we're looking for
		{
			parent = current; //set parent to current
			if(key.compareTo((K) current.key) < 1)
			{
				isLeft = true; //remain true
				current = current.leftChild;
			}
			else
			{
				isLeft = false; //now is right
				current = current.rightChild; //set equal to right
			}
			if (current == null)
			{
				return false;
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
				root = current.leftChild; //set root to left
			}
			else if (isLeft == true)
			{
				parent.leftChild = current.leftChild; //set left of parent to current's left
			}
			else parent.rightChild = current.leftChild;
		}
		else if (current.leftChild == null)//if there is no left child
		{
			if(current == root)
			{
				root = current.rightChild; //set root to right child
			}
			else if (isLeft == true)
			{
				parent.leftChild = current.rightChild; //parent of left is current's right child
			}
			else parent.rightChild = current.leftChild;//parent of right is current's left child
		}
		else //if 2 children are present
		{
			Node replacement = getReplacement(current);

			if(current == root)
			{
				root = replacement;
			}
			else if (isLeft == true)
			{
				parent.leftChild = replacement;
			}
			else
			{
				 parent.rightChild = replacement;
				 replacement.leftChild = current.leftChild;
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
			if (key.compareTo((K) current.key)<1) //less than
			{
				current = current.leftChild; //current is left
			}
			else current = current.rightChild; //otherwise right

			if(current == null) //if no current
			{
				throw new NoSuchElementException();//throw exception
			}
		}
		return (E) (current.element);
	}

	@Override
	public int size()
	{
		return size; //getter for size
	}

    public String toString()
    {
    	if (size == 0)
    	{
    		return "[]"; //returns empty brackets for no elements
    	}
    	else
    		{
    		return "[" + moveInOrder(root, "") + "]"; //calls helper method and adds unnecessary required brackets
    		}
    }

    public int findHeight(Node root)
    {
    	if (root == null)
    	{
    		return 0; //0 if no root
    	}
    	else return (1+ Math.max(findHeight(root.leftChild),findHeight(root.rightChild))); //gets max of left and right children
    }
	@Override
	public int getHeight()
	{
		if (size == 0)
		{
			return 0; //0 if empty
		}
		else
		{
			return findHeight(root)-1; //calls helper method and subtracts 1
		}
	}

	@Override
	public String getTreeString()
	{
		if (size == 0)
		{
			return "[]"; //returns empty brackets if empty
		}
		else
		{
			return buildString(root); //calls helper method with root
		}
	}
}
