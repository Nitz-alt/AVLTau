package trees;

import java.util.Arrays;
import java.util.Random;


public class MyTester {
	public static void main(String[] args) {
		Random rand = new Random();
		int[] ar = new int[100];
		int j=0;
		for (;j < 19; j++) {
			
			ar[j] = rand.nextInt(500);
		}
		System.out.println(j);
		
		AVLTree tree = new AVLTree();
		for (int i : ar) {
			tree.insert(i, ""+i);
		}
		print(tree);
		
		/*print(tree);
		int[] deleteOrder = {0, 4, 5, 6, 2,  3, 7, 1};
		for (int i : deleteOrder) {
			System.out.println(tree.delete(i));
			print(tree);
		}*/
	}
	
	
	private static void print(AVLTree tree) {
		TreePrinter.print(tree.getRoot());
	}
}
