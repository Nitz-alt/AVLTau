package trees;

import java.util.Arrays;

public class MyTester {
	public static void main(String[] args) {
		AVLTree tree = new AVLTree();
		int[] ar = {1,2 ,3, 4, 5, 6, 7, 8, 9, 10 , 11, 12 ,13, 14};
		for (int i : ar) {
			tree.insert(i, ""+i);
		}
		
		
		
		print(tree);
		AVLTree[] trees = tree.split(4);
		System.out.println("\n\n***Split at 4***\n\n");
		print(trees[0]);
		print(trees[1]);
	}
	
	
	private static void print(AVLTree tree) {
		TreePrinter.print(tree.getRoot());
	}
}
