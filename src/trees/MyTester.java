package trees;

import java.util.Arrays;
import java.util.Random;

import trees.AVLTree.IAVLNode;


public class MyTester {
	public static void main(String[] args) {
		final AVLTree FACTORY = new AVLTree();
		int[] ar = {1,2,3,4,5,7,8};
		AVLTree tree = new AVLTree();
		for (int i : ar) {
			tree.insert(i, ""+i);
			if (!testTree(tree.getRoot())) {
				System.out.println("failed after inserting " + i);
			}
		}
		/*ar = new int[] {4, 5, 7, 2, 3, 8};
		for (int i : ar) {
			tree.delete(i);
			if (!testTree(tree.getRoot())) {
				System.out.println("failed after deleting " + i);
			} 
		}*/
		
		print(tree);
		AVLTree[] t = tree.split(5);
		System.out.println(testTree(t[0].getRoot()));
		System.out.println(testTree(t[1].getRoot()));
		
		print(t[0]);
		print(t[1]);
		IAVLNode node = FACTORY.new AVLNode(5, "" + 5);
		t[0].join(node, t[1]);
		print(t[0]);
		System.out.println(testTree(t[0].getRoot()));
		
		
	}
	
	private static void print(AVLTree tree) {
		TreePrinter.print(tree.getRoot());
	}
	
	public static boolean testTree(IAVLNode node) {
		boolean leftFlag = true;
		boolean rightFlag = true;
		if (node.getLeft().isRealNode()) {
			if (node.getLeft().getParent() != node)
				return false;
			leftFlag = testTree(node.getLeft());
			
		}
		if (node.getRight().isRealNode()) {
			if (node.getRight().getParent() != node) {
				return false;
			}
			rightFlag = testTree(node.getRight());
		}
		return leftFlag && rightFlag;
	}
}
