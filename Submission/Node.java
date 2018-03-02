
public class Node<K extends Comparable<K>,E>

{
	K key; //initializes generic items
	E element;
	String color; //only referenced in RB tree


	Node leftChild; //creates nodes for each child
	Node rightChild;
	Node parent;

	Node(K key, E element) //takes in key and element and sets to top variable
	{
		this.key = key;
		this.element = element;
	}



	public String print() //prints out elements
	{
		return key + ": " + element;
	}

	public String RBprint() //same but includes color
	{
		return color + key + ": " + element;
	}

}
