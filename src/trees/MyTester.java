package trees;

import java.util.Arrays;

public class MyTester {
	public static void main(String[] args) {
		AVLTree tree = new AVLTree();
		int[] ar = {1,2 ,3, 4, 5, 6, 7};
		for (int i : ar) {
			tree.insert(i, ""+i);
		}
		
		print(tree);
		int[] deleteOrder = {4, 5, 2, 6, 3, 7, 1};
		for (int i : deleteOrder) {
			System.out.println(tree.delete(i));
			print(tree);
		}
	}
	
	
	private static void print(AVLTree tree) {
		TreePrinter.print(tree.getRoot());
	}
}
