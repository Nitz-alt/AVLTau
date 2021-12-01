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
		AVLTree[] trees = tree.split(4);
		System.out.println("\n\n***Split at 4***\n\n");
		print(trees[0]);
		print(trees[1]);
		AVLTree factory = new AVLTree();
		AVLTree.AVLNode node = factory.new AVLNode(4, null);
		node.setLeft(factory.VIRTUAL_NODE);
		node.setRight(factory.VIRTUAL_NODE);
		trees[0].join(node, trees[1]);
		print(trees[0]);
		
	}
	
	
	private static void print(AVLTree tree) {
		TreePrinter.print(tree.getRoot());
	}
}
