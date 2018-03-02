import java.math.BigInteger; //imports necessary packages
import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Random;

public class BstMain extends BinarySearchTree
{
	static IBinarySearchTree bst;
	static long StartTime; //initializes startTime variable


	public static void startTime() //start time method
	{
		StartTime = System.nanoTime(); //gets current time down to  milliseconds
	}


	public static long endTime() //end time method
	{
		long EndTime = System.nanoTime(); //gets current time and stores in variable
		long difference = EndTime - StartTime; //gets difference between 2 times
		return difference; //returns difference
	}

	public static void main(String[] args)
	{
		BinarySearchTree tree = new BinarySearchTree<>();
	    ArrayList<Integer> list = new ArrayList<Integer>(128);

        for(int i = 1; i < 128; i++) //adds 10,000 items to array
        {
            list.add(i); //adds items to list
            tree.put(i, String.valueOf(i)); //puts in random key and element
        }
        Collections.shuffle(list); //shuffles list to random order

        startTime(); //starts time
        for(int i: list) //iterates through items in list
        {
    		tree.get(i); //gets item from tree
        }
        System.out.println("Random sequence of size 128: " + endTime() + " nanoseconds"); //ends time, prints result
        System.out.println("Tree height: " + tree.getHeight()+"\n"); //prints height of random tree


        BinarySearchTree tree2 = new BinarySearchTree<>(); //creates second tree
        for(int i = 1; i < 128; i++) //adds 10,000 items to array
        {
            list.add(i); //fills it
            tree2.put(i, String.valueOf(i)); //puts random key and element
        }

        startTime(); //starts time
        for(int i: list) //iterates through things in list
        {
    		tree2.get(i); //calls get on each
        }
        System.out.println("Chronological sequence of size 128: " + endTime() + " nanoseconds"); // ends time, prints result
        System.out.println("Tree height: " + tree2.getHeight()+"\n"); //prints height of chronological tree

        //the chronological sequence runs faster than the random because it doesn't have to go as deep into the statements since the order is already set.
        //This is true even though the heights are the same
	}
}
