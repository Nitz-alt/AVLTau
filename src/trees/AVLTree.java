package trees;
import trees.TreePrinter.PrintableNode;
/**
 *
 * AVLTree
 *
 * An implementation of a׳³ן¿½ AVL Tree with
 * distinct integer keys and info.
 *
 */

public class AVLTree{
	public final static IAVLNode VIRTUAL_NODE = (new AVLTree()).new AVLNode();
	private IAVLNode rootNode = VIRTUAL_NODE;
	private IAVLNode minNode = VIRTUAL_NODE;
	private IAVLNode maxNode = VIRTUAL_NODE;
  /**
   * public boolean empty()
   *
   * Returns true if and only if the tree is empty.
   *
   */
  public boolean empty() {
    return this.rootNode ==  null || this.rootNode == VIRTUAL_NODE;
  }

 /**
   * public String search(int k)
   *
   * Returns the info of an item with key k if it exists in the tree.
   * otherwise, returns null.
   */
  public String search(int k)
  {
	  if (this.empty()) {
		  return null;
	  }
	  IAVLNode currNode = this.rootNode;
	  while (currNode.isRealNode()) {
		  if (currNode.getKey() == k) {
			  return currNode.getValue();
		  }
		  if (k < currNode.getKey()) {
			  currNode = currNode.getLeft();
		  }
		  else {
			  currNode = currNode.getRight();
		  }
	  }
	  return null;
  }
  
  // Looks for key in the subtree of node. If it is found, return it. Else, returns the last node encountered 
  public IAVLNode treePosition(IAVLNode node, int key) {
	  IAVLNode tempNode = node;
	 	  
	  while(node != VIRTUAL_NODE) {
		  tempNode = node; 
		  if(key == node.getKey()) {
			  return tempNode;
		  }
		  else if( key < node.getKey()) {
			  node = node.getLeft();
		  }
		  else {
			  node = node.getRight();
		  }
	  }
	  return tempNode;
  }

  /**
   * public int insert(int k, String i)
   *
   * Inserts an item with key k and info i to the AVL tree.
   * The tree must remain valid, i.e. keep its invariants.
   * Returns the number of re-balancing operations, or 0 if no re-balancing operations were necessary.
   * A promotion/rotation counts as one re-balance operation, double-rotation is counted as 2.
   * Returns -1 if an item with key k already exists in the tree.
   */
  
   public int insert(int k, String i) {
	   IAVLNode inNode = new AVLNode(k, i, /* parent */ null, /* left */ VIRTUAL_NODE, /* right */ VIRTUAL_NODE, /* height */ 0);
	   return insert(inNode);
   	}
   
   public int insert(IAVLNode inNode) {
	   IAVLNode searchNodeResult = treePosition(rootNode, inNode.getKey());
	   // If tree is empty, insert node in the root.
	   if(searchNodeResult == VIRTUAL_NODE) { 
		  this.rootNode = inNode;
		  this.maxNode = rootNode;
		  this.minNode = rootNode;
		  rootNode.setSubTreeSize(1);
		  return 0;
   		}
	   
	   // If node is found in the tree, we do nothing, num of operations has not changed.
	   else if(searchNodeResult.getKey() == inNode.getKey()) {
		   return -1;
	   }
	   // Node isn't in the tree, insertions and modifications are necessary.
	   else {
		   if(inNode.getKey() < minNode.getKey()) {
			   minNode = inNode;
		   }
		   else if(inNode.getKey() > maxNode.getKey()) {
			   maxNode = inNode;
		   }   
	   }
	   //New node should go on the left of search node
	   if (inNode.getKey() < searchNodeResult.getKey()) {
		   searchNodeResult.setLeft(inNode);
		   inNode.setParent(searchNodeResult);
		   inNode.setHeight(0);
	   }
	   else {
		   searchNodeResult.setRight(inNode);
		   inNode.setParent(searchNodeResult); 
		   inNode.setHeight(0);
	   }
	   inNode.setSubTreeSize(1);
	   return rebalancePostInsert(inNode);
   }
 
  public int rebalancePostInsert(IAVLNode insertNode){
      IAVLNode x = insertNode;
      IAVLNode topAfterActioNode = insertNode;
      int numOps = 0;
      //Traverse the tree until you get to the top
      while(!isRoot(x)){
    	  System.out.println("in rebalance, going up");
    	  IAVLNode z = x.getParent();
    	  if (x.getHeight() != z.getHeight()) {
				x.setSubTreeSize(x.getLeft().getSubTreeSize() + x.getRight().getSubTreeSize() + 1);
				break;
    	  } 
    	  //Child and parent are of the same height - promote or rotate
          if (x.getHeight() == z.getHeight()){
        	  if (z.getLeft() ==x) { // x is left child
        		  IAVLNode y = z.getRight();
				int rankDifZY = z.getHeight() - y.getHeight();
				int rankDifZX = z.getHeight() - x.getHeight();
				if(rankDifZY == 1) {//Promote
					z.setHeight(z.getHeight() + 1);
					z.setSubTreeSize(z.getSubTreeSize() + 1);
					x = z;
					numOps++;
					topAfterActioNode = z;
					continue;
				}
				else if(rankDifZY == 2) { //Rotate(single or double) 
					IAVLNode b = x.getRight();
					IAVLNode a = x.getLeft();
					int rankDifXA = x.getHeight() - a.getHeight();
					int rankDifXB = x.getHeight() - b.getHeight();
					if(rankDifXA == 1 && rankDifXB == 2) {//rotate single right
						rotateRight(x);
						z.setHeight(z.getHeight() - 1);
						topAfterActioNode = x;
						z.setSubTreeSize(b.getSubTreeSize() + y.getSubTreeSize() + 1);
						x.setSubTreeSize(z.getSubTreeSize() + a.getSubTreeSize() + 1);
						numOps++; 
						break;
					}
					else if(rankDifXA == 2 && rankDifXB == 1) {//double rotate right
						rotateLeft(b);
						rotateRight(b);
						x.setHeight(x.getHeight() - 1);
						z.setHeight(z.getHeight() - 1);
						b.setHeight(b.getHeight() + 1);
						x.setSubTreeSize(x.getLeft().getSubTreeSize() + x.getRight().getSubTreeSize() + 1);
						z.setSubTreeSize(z.getRight().getSubTreeSize() + z.getLeft().getSubTreeSize() + 1);
						b.setSubTreeSize(x.getSubTreeSize() + z.getSubTreeSize() + 1);
						topAfterActioNode = b;
						numOps++;
						  break;
					}
					else if(rankDifXA == 1 && rankDifXB == 1) {
						rotateRight(x);
						x.setHeight(x.getHeight() + 1);
						x.setSubTreeSize(x.getLeft().getSubTreeSize() + x.getRight().getSubTreeSize() + 1);
						z.setSubTreeSize(z.getRight().getSubTreeSize() + z.getLeft().getSubTreeSize() + 1);
						x.setParent(z.getParent());
						z.setParent(x);
						x.setRight(z);
						z.setLeft(b);
						b.setParent(z);
						topAfterActioNode =x;
						numOps++;
					}
						
					 
				}//(rankDifZY == 2)
        	  }// x is right child cases
        	  if(z.getRight() == x) {
        		  IAVLNode y = z.getLeft();
  				  int rankDifZY = z.getHeight() - y.getHeight();
  				  if(rankDifZY == 1) {//Promote
					z.setHeight(z.getHeight() + 1);
					z.setSubTreeSize(z.getSubTreeSize() + 1);
					x = z;
					numOps++;
					topAfterActioNode = z;
					continue;
				}
  				  else if(rankDifZY == 2) {//single or double rotation left
	        		  IAVLNode a = x.getRight();
	        		  IAVLNode b = x.getLeft();
	        		  int rankDifXA = x.getHeight() - a.getHeight();
					  int rankDifXB = x.getHeight() - b.getHeight();
					  if(rankDifXA == 1 && rankDifXB == 2) {//rotate single left
						  rotateLeft(x);
						  z.setHeight(z.getHeight() - 1);
						  z.setSubTreeSize(b.getSubTreeSize() + y.getSubTreeSize() + 1);
						  x.setSubTreeSize(z.getSubTreeSize() + a.getSubTreeSize() + 1);
						  numOps++;
						  topAfterActioNode = x;
						  break;
					  }
					  if(rankDifXA ==2 && rankDifXB == 1) {
							rotateRight(b);
							rotateLeft(b);
							x.setHeight(x.getHeight() - 1);
							z.setHeight(z.getHeight() - 1);
							b.setHeight(b.getHeight() + 1);
							x.setSubTreeSize(x.getLeft().getSubTreeSize() + x.getRight().getSubTreeSize() + 1);
							z.setSubTreeSize(z.getRight().getSubTreeSize() + z.getLeft().getSubTreeSize() + 1);
							b.setSubTreeSize(x.getSubTreeSize() + z.getSubTreeSize() + 1);
							numOps++;
							topAfterActioNode = b;
							  break;
					  }
					  
					  else if(rankDifXA == 1 && rankDifXB == 1) {
							rotateLeft(x);
							x.setHeight(x.getHeight() + 1);
							x.setSubTreeSize(x.getLeft().getSubTreeSize() + x.getRight().getSubTreeSize() + 1);
							z.setSubTreeSize(z.getRight().getSubTreeSize() + z.getLeft().getSubTreeSize() + 1);
							x.setParent(z.getParent());
							z.setParent(x);
							x.setLeft(z);
							z.setRight(b);
							b.setParent(z);
							topAfterActioNode = x;
							numOps++;

						}
					 
	        	  }
        	  }
          
          }// 
          }//while
	  System.out.println("done rebalance");

      topAfterActioNode.increaseSizeOfSubTreeOfAllParents();
      return numOps;
  }  
  
  
 
/**
   * public int delete(int k)
   *
   * Deletes an item with key k from the binary tree, if it is there.
   * The tree must remain valid, i.e. keep its invariants.
   * Returns the number of re-balancing operations, or 0 if no re-balancing operations were necessary.
   * A promotion/rotation counts as one re-balance operation, double-rotation is counted as 2.
   * Returns -1 if an item with key k was not found in the tree.
   */
   public int delete(int k)
   {
	      return deleteWithNode(k, null);
   }
   
   private int deleteWithNode(int k, IAVLNode curr) {
	   int total = 0;
	   IAVLNode currNode;
	   if (curr == null) {
		   currNode = this.searchNode(k);
	   }
	   else {
		   currNode = curr;
	   }
	   	   
	   
	   if (currNode == null) {return -1;} // Key not in tree
	   
	   boolean newMin = currNode.getKey() == this.minNode.getKey();
	   boolean newMax = currNode.getKey() == this.maxNode.getKey();
	   
	   // Only deletion
	   IAVLNode parentNode = currNode.getParent();
	   IAVLNode leftChildNode = currNode.getLeft();
	   IAVLNode rightChildNode = currNode.getRight();
	   if (!leftChildNode.isRealNode() && !rightChildNode.isRealNode()) { // Node to be deleted is a leaf
		   if (currNode == this.getRoot()) { // The root is a leaf therefore the tree will now be empty
			   this.rootNode = VIRTUAL_NODE;
			   this.maxNode = VIRTUAL_NODE;
			   this.minNode = VIRTUAL_NODE;
			   detachNode(currNode);
			   return 0;
		   }
		   if (parentNode.getLeft() == currNode) { // Deletion - Setting parent left child to virutal node
			   parentNode.setLeft(VIRTUAL_NODE);
		   }
		   else {
			   parentNode.setRight(VIRTUAL_NODE);
		   }
	   }
	   else if ((leftChildNode.isRealNode() && !rightChildNode.isRealNode()) || (!leftChildNode.isRealNode() && rightChildNode.isRealNode())) { // Node to be deleted is an unary node.
		   if (currNode == this.getRoot()) {
			   if (currNode.getLeft().isRealNode()) { // The root only has a left child
				   this.rootNode = currNode.getLeft();
			   }
			   else { // Only right child
				   this.rootNode = currNode.getRight();
			   }
			   // Root has no parent
			   this.rootNode.setParent(null);
		   }
		   else if (parentNode.getLeft() == currNode) { // Deleted node is a left child
			   if (leftChildNode.isRealNode()) { // Has left child
				   parentNode.setLeft(leftChildNode);
				   leftChildNode.setParent(parentNode);
			   }
			   else { // Has right child
				   parentNode.setLeft(rightChildNode);
				   rightChildNode.setParent(parentNode);
			   }
		   }
		   else { // Deleted node is a right child
			   if (leftChildNode.isRealNode()) { // Has left child
				   parentNode.setRight(leftChildNode);
				   leftChildNode.setParent(parentNode);
			   }
			   else { // Has right child
				   parentNode.setRight(rightChildNode);
				   rightChildNode.setParent(parentNode);
			   }
		   }
	   }
	   else if(leftChildNode.isRealNode() && rightChildNode.isRealNode()) { // Binary node - Deleting the successor.
		   // Finding successor
		   IAVLNode currSuccessor = this.successor(currNode);
		   total += deleteWithNode(k, currSuccessor); // Deleting successor
		   // Switching between successor and node to be deleted
		   currSuccessor.setLeft(currNode.getLeft());
		   currSuccessor.setRight(currNode.getRight());
		   currSuccessor.setParent(currNode.getParent());
		   currSuccessor.setHeight(currNode.getHeight());
		   currSuccessor.setSubTreeSize(currNode.getSubTreeSize());
		   currSuccessor.getRight().setParent(currSuccessor);
		   currSuccessor.getLeft().setParent(currSuccessor);
		   
		   // The successor could be the maximum therefore we need to replace that too
		   if (this.maxNode.getKey() < currSuccessor.getKey()) {
			   this.maxNode = currSuccessor;
		   }
		   
		   parentNode = currNode.getParent(); // Updating parent after rebalance
		   if (this.getRoot() == currNode) { // Current node is the root
			   this.rootNode = currSuccessor;
		   }
		   else {
			   if (parentNode.getLeft() == currNode) { // Deleted node is the left child (current isn't the root therefore parent is no null)
				   parentNode.setLeft(currSuccessor);
			   }
			   else { // Deleted node is a right child
				   parentNode.setRight(currSuccessor);
			   }
		   }
		   
		   
		   detachNode(currNode);
		   return total;
	   }
	   detachNode(currNode);
	   
	   // Only deletion end
	   
	   // Fixing sub tree sizes
	   IAVLNode decreaseNode = parentNode;
	   while (decreaseNode != null) {
		   decreaseNode.setSubTreeSize(decreaseNode.getSubTreeSize() - 1);
		   decreaseNode = decreaseNode.getParent();
	   }
	   
	   
	   
	   total += rebalancing(parentNode);
	   setNewMinAndMax(newMin, newMax);
	   return total;
   }
   
   private static void detachNode(IAVLNode node) {
	   node.setParent(null);
	   node.setRight(VIRTUAL_NODE);
	   node.setLeft(VIRTUAL_NODE);
   }
   
   
   private void setNewMinAndMax(boolean setMin, boolean setMax) {
	   if (setMin) {
		   this.minNode = this.findMinNode(this.getRoot());
	   }
	   if (setMax) {
		   this.maxNode = this.findMaxNode(this.getRoot());
	   }
   }
   
   
   private IAVLNode findMinNode(IAVLNode curr) {
	   if (curr == null) {return null;}
	   IAVLNode nextCurr = curr.getLeft();
	   while (nextCurr != null && nextCurr.isRealNode()) {
		   curr = nextCurr;
		   nextCurr = curr.getLeft();
	   }
	   return curr;
   }
   
   private IAVLNode findMaxNode(IAVLNode curr) {
	   if (curr == null) { return null;}
	   IAVLNode nextCurr = curr.getRight();
	   while (nextCurr != null && nextCurr.isRealNode()) {
		   curr = nextCurr;
		   nextCurr = curr.getRight();
	   }
	   return curr;
   }
   
   
   private IAVLNode successor(IAVLNode curr) {
	   if (curr.getRight().isRealNode()) {
		   return this.findMinNode(curr.getRight());
	   }
	   IAVLNode succNode = curr.getParent();
	   while (succNode != null && curr == succNode.getRight()) {
		   curr = succNode;
		   succNode = curr.getParent();
	   }
	   return succNode;
   }
   
   
   
   private int rebalancing(IAVLNode curr) { // Gets the parent of the junction
	   int total = 0;
	   while (curr != null) {
		   IAVLNode parentNode = curr.getParent();
		   IAVLNode leftChildNode = curr.getLeft();
		   IAVLNode rightchildNode = curr.getRight();
		   int leftRankDelta = curr.getHeight() - leftChildNode.getHeight();
		   int rightRankDelta = curr.getHeight() - rightchildNode.getHeight();
		   if ((leftRankDelta == 2 && rightRankDelta == 1) || (leftRankDelta == 1 && rightRankDelta == 2) || (leftRankDelta == 1 && rightRankDelta == 1)) { // (1,2) or (2,1) is ok
			   return total; // Nothing done price is 0
		   }
		   else if (leftRankDelta == 2 && rightRankDelta == 2) { // Demoting parent and balancing up;
			   curr.setHeight(curr.getHeight()-1);
			   total += 1; // Demoting price is 1
		   }
		   else if ((leftRankDelta == 3 && rightRankDelta == 1) || (leftRankDelta == 1 && rightRankDelta == 3)) { // Junction is (3,1) or (1,3)
			   if (leftRankDelta == 3) { // Rank delta is 3 on left side - rotate
				   // Parent right child shouldn't be a virtual node
				   int rightChildLeftRankDelta = curr.getRight().getHeight() - curr.getRight().getLeft().getHeight();
				   int rightChildRightRankDelta = curr.getRight().getHeight() - curr.getRight().getRight().getHeight();
				   if (rightChildLeftRankDelta == 1 && rightChildRightRankDelta == 1) { // Right child is a (1,1) junction.
					   rotateLeft(curr.getRight());
					   curr.setHeight(curr.getHeight() - 1); // Demoting the (prev) parent
					   rightchildNode.setHeight(rightchildNode.getHeight() + 1); // Promoting right child (who is now the parent)
					   return total + (1 + 2); // One rotation and 2 promotes/demotes (Parent should be at same height)
				   }
				   else if(rightChildLeftRankDelta == 2 && rightChildRightRankDelta == 1) { // Right child is a (2,1) junction.
					   rotateLeft(curr.getRight());
					   curr.setHeight(curr.getHeight() - 2);
					   total += (1 + 2); // One rotation and demoting by 2.
				   }
				   else if(rightChildLeftRankDelta == 1 && rightChildRightRankDelta == 2) { // Right child is a (1,2) junction
					   // Double rotation
					   IAVLNode prevRightChildLeft = rightchildNode.getLeft();
					   rotateRight(prevRightChildLeft);
					   rotateLeft(prevRightChildLeft);
					   prevRightChildLeft.setHeight(prevRightChildLeft.getHeight() + 1);
					   rightchildNode.setHeight(rightchildNode.getHeight() - 1);
					   curr.setHeight(curr.getHeight() - 2);
					   total += (2 + 4); // Double rotation and 4 demotes
				   }
			   }
			   else if (rightRankDelta == 3) { // Symetrical cases
				   int leftChildRightRankDelta = curr.getLeft().getHeight() - curr.getLeft().getRight().getHeight();
				   int leftChildLeftRankDelta = curr.getLeft().getHeight() - curr.getLeft().getLeft().getHeight();
				   if (leftChildRightRankDelta == 1 && leftChildLeftRankDelta == 1) { // Left child is a (1,1) junction -- symterical
					   rotateRight(leftChildNode);
					   curr.setHeight(curr.getHeight() - 1); // Demoting the (prev) parent
					   leftChildNode.setHeight(leftChildNode.getHeight() + 1); // Promoting left child (who is now the parent)
					   return total + (1 + 2); // One rotation and 2 promotes/demotes (Parent should be at same height);
				   }
				   else if (leftChildRightRankDelta == 2 && leftChildLeftRankDelta == 1) { // Left child is a (1,2) junction
					   rotateRight(leftChildNode);
					   curr.setHeight(curr.getHeight() - 2);
					   total += (1 + 2); // One rotation and demoting by 2.
				   }
				   else if (leftChildRightRankDelta == 1 && leftChildLeftRankDelta == 2) { // Left child is a (2,1) junction
					   // Double rotation
					   IAVLNode prevLeftChildRight = leftChildNode.getRight(); // This is going to be the final parent in the junction
					   rotateLeft(prevLeftChildRight);
					   rotateRight(prevLeftChildRight);
					   prevLeftChildRight.setHeight(prevLeftChildRight.getHeight() + 1);
					   leftChildNode.setHeight(leftChildNode.getHeight() - 1);
					   curr.setHeight(curr.getHeight() - 2);
					   total += (2 + 4); // One rotation and 4 demotes;
				   }
			   }
		   }
		   curr = parentNode;
	   }
	   return total;
   }
   
   
   private IAVLNode searchNode(int k) {
	   if (this.empty()) {
		   return null;
	   }
	   IAVLNode currNode = this.rootNode;
	   while (currNode.isRealNode()) {
		   if (currNode.getKey() == k) {
			   return currNode;
		   }
		   if (k < currNode.getKey()) {
			   currNode = currNode.getLeft();
		   }
		   else {
			   currNode = currNode.getRight();
		   }
	   }
	   return null;
   }
   
   /**
    * public String min()
    *
    * Returns the info of the item with the smallest key in the tree,
    * or null if the tree is empty.
    */
   public String min()
   {
	   if (this.empty())
		   return null;
	   return this.minNode.getValue(); 
   }
    
    
   /**
    * 
    * public String max()
    *
    * Returns the info of the item with the largest key in the tree,
    * or null if the tree is empty.
    */
   public String max()
   {
	   if (this.empty())
		   return null;
	   return this.maxNode.getValue(); 
   }

  /**
   * public int[] keysToArray()
   *
   * Returns a sorted array which contains all keys in the tree,
   * or an empty array if the tree is empty.
   */
  public int[] keysToArray()
  {
	  if(this.empty()) {
		  return new int[0];
	  }
	  int numOfNodes = rootNode.getSubTreeSize();
      int[] keysArray = new int[numOfNodes];
      IAVLNode minimalNode = this.minNode;
      IAVLNode currNode = minimalNode;
      for (int i = 0; i < numOfNodes; i++){// This is O(n), proved in recitation
          keysArray[i] = currNode.getKey();
          currNode = this.successor(currNode);
      }
      return keysArray;
  }

 

/**
   * public String[] infoToArray()
   *
   * Returns an array which contains all info in the tree,
   * sorted by their respective keys,
   * or an empty array if the tree is empty.
   */
  public String[] infoToArray()
  {
	  if(this.empty()) {
		  return new String[0];
	  }
	  int numOfNodes = rootNode.getSubTreeSize();
      String[] infoArray = new String[numOfNodes];
      IAVLNode minimalNode = this.minNode;
      IAVLNode currNode = minimalNode;
      for (int i = 0; i < numOfNodes; i++){// This is O(n), proved in recitation
          infoArray[i] = currNode.getValue();
          currNode = successor(currNode);
      }
      return infoArray;
  }

   /**
    * public int size()
    *
    * Returns the number of nodes in the tree.
    */
   public int size()
   {
	if (!this.empty())   
		return rootNode.getSubTreeSize();
	return 0;
   }
   
   /**
    * public int getRoot()
    *
    * Returns the root AVL node, or null if the tree is empty
    */
   public IAVLNode getRoot()
   {
	   return this.rootNode;
   }
   
   /**
    * public AVLTree[] split(int x)
    *
    * splits the tree into 2 trees according to the key x. 
    * Returns an array [t1, t2] with two AVL trees. keys(t1) < x < keys(t2).
    * 
	* precondition: search(x) != null (i.e. you can also assume that the tree is not empty)
    * postcondition: none
    */   
   public AVLTree[] split(int x)
   {
	   final IAVLNode END_OF_TREE_NODE = VIRTUAL_NODE;
	   IAVLNode currNode = this.searchNode(x);
	   if (currNode == null) {return null;};
	   
	   AVLTree lowerTree = new AVLTree(); // Lower tree
	   lowerTree.rootNode = currNode.getLeft();
	   lowerTree.getRoot().setParent(null); // Detaching child from parent
	   currNode.setLeft(END_OF_TREE_NODE);
	   
	   AVLTree higherTree = new AVLTree(); // Higher tree
	   higherTree.rootNode = currNode.getRight();
	   higherTree.getRoot().setParent(null); // Detaching child from parent
	   currNode.setRight(END_OF_TREE_NODE);
	   IAVLNode splitNode = currNode;

	   if (currNode != this.getRoot()) {
		   boolean currIsLower = currNode.getParent().getRight() == currNode;
		   currNode = currNode.getParent();
		   
		   while (currNode != null) {
			   IAVLNode nextCurr = currNode.getParent();
			   boolean nextCurrIsLower = false;
			   if (nextCurr != null) {
				   nextCurrIsLower = nextCurr.getRight() == currNode;
			   }
			   if (currIsLower) {
				   IAVLNode leftSubTreeRoot = currNode.getLeft();
				   detachNode(currNode);
				   currNode.setHeight(0);
				   leftSubTreeRoot.setParent(null);
				   AVLTree addToLower = new AVLTree();
				   addToLower.rootNode = leftSubTreeRoot;
				   lowerTree.join(currNode, addToLower);
				   
			   }
			   else {
				   IAVLNode rightSubTreeRoot = currNode.getRight();
				   detachNode(currNode);
				   currNode.setHeight(0);
				   rightSubTreeRoot.setParent(null);
				   AVLTree addToHigher = new AVLTree();
				   addToHigher.rootNode = rightSubTreeRoot;
				   higherTree.join(currNode, addToHigher);
			   }
			   currNode = nextCurr;
			   currIsLower = nextCurrIsLower;
		   }
	   }
	   
	   detachNode(splitNode);
	   
	   
	   lowerTree.minNode = this.minNode;
	   lowerTree.setNewMinAndMax(x == this.minNode.getKey(), true);
	   
	   higherTree.maxNode = this.maxNode;
	   higherTree.setNewMinAndMax(true, x == this.maxNode.getKey());
	   return new AVLTree[] {lowerTree, higherTree};
   }
   
   /**
    * public int join(IAVLNode x, AVLTree t)
    *
    * joins t and x with the tree. 	
    * Returns the complexity of the operation (|tree.rank - t.rank| + 1).
	*
	* precondition: keys(t) < x < keys() or keys(t) > x > keys(). t/tree might be empty (rank = -1).
    * postcondition: none
    */   
   public int join(IAVLNode x, AVLTree t)
   {
	   //Empty Cases
	   if(this.empty() && t.empty()) {
		   this.insert(x);
		   t.rootNode = this.rootNode;
		   t.maxNode = x;
		   t.minNode = x;
		   this.maxNode = x;
		   this.minNode = x;
		   return 1;
	   }
	   
	   if(this.empty()) {
		  t.insert(x);
		  this.rootNode = t.rootNode;
		  t.minNode = findMinNode(t.rootNode);
		  t.maxNode = findMaxNode(t.rootNode);
		  this.minNode = t.minNode;
		  this.maxNode = t.maxNode;
		  return t.getRoot().getHeight();
	   }
	   
	   if(t.empty()) {
		   this.insert(x);
		   t.rootNode = this.rootNode;
		   this.minNode = findMinNode(this.rootNode);
		   this.maxNode = findMaxNode(this.rootNode);
		   t.minNode = this.minNode;
		   t.maxNode = this.maxNode;
		   return this.getRoot().getSubTreeSize();
	   }
	  int runtime = this.getRoot().getSubTreeSize() - t.getRoot().getSubTreeSize() + 1;
	  int higherTree = checkWhichIsLarger(this.getRoot().getHeight(), t.getRoot().getHeight());
	  int greaterKeysTree = checkWhichIsLarger(this.getRoot().getKey(), t.getRoot().getKey());
	  AVLTree tree1 = this;
	  AVLTree tree2 = t;
	  //this object is larger in size

	  if(higherTree == 2) {
		 //Check which tree has larger keys
		  if(greaterKeysTree == 2) {
			  joinSmallerWithLarger(x,/*lowKey and short */ tree1, /* highKey and tall */tree2);
		  } else  {
			  joinLargerWithSmaller(x, /* high key and short */ tree1, /* low key and high */ tree2);
		  }
		  
	  }
	  else if(higherTree == 1) {
		  if(greaterKeysTree == 1) {
			  joinSmallerWithLarger(x,/*lowKey and short */ tree2, /* highKey and tall */tree1);
		  } else {
			  joinLargerWithSmaller(x, /* high key and short */  tree2, /* low key and high */ tree1);
	  }
	  }
	  else {
		  if(greaterKeysTree == 1) {
			  joinSameSize(x,t,this); 
		  }
		  else {
			  joinSameSize(x, this, t);
		  }
	}
	  //finding min/max - O(logn)
	  this.minNode = findMinNode(this.getRoot());
	  this.maxNode = findMaxNode(this.getRoot());
	  return Math.abs(runtime);
   }
   
   
   
  
   
   // thisTree is being set as the one with smaller keys
   public void joinSameSize(IAVLNode x, AVLTree thisTree, AVLTree t) {
 	  System.out.println("join same size");

	   x.setLeft(thisTree.getRoot());
	   x.setRight(t.getRoot());
	   x.setSubTreeSize(x.getLeft().getSubTreeSize() + x.getRight().getSubTreeSize() + 1);
	   x.getRight().setParent(x);
	   x.getLeft().setParent(x);
	   x.setHeight(x.getLeft().getHeight() + 1);
	   thisTree.rootNode = x;
	   t.rootNode = x;
	 	  System.out.println("join same size done");

   }
   
   
   
   
   
//   lowTree.key > highTree.key
   public void joinLargerWithSmaller(IAVLNode x, AVLTree lowTree, AVLTree highTree) {
	 	  System.out.println("join larger smaller");

	   int sizeOfThisTree = lowTree.getRoot().getSubTreeSize() + 1;
	   IAVLNode b = highTree.getRoot();
		  while(b.getHeight() > lowTree.getRoot().getHeight()) {
			  b = b.getRight();

		  }
		  IAVLNode c = b.getParent();
		  IAVLNode a = lowTree.getRoot();
		  c.setRight(x);
		  x.setRight(a);
		  x.setLeft(b);
		  x.setParent(c);
		  a.setParent(x);
		  b.setParent(x);
		  x.setHeight(lowTree.getRoot().getHeight() + 1);
		  x.setSubTreeSize(x.getLeft().getSubTreeSize() + x.getRight().getSubTreeSize() + 1);
		  IAVLNode newRoot = c;
		  while(newRoot.getParent() != null) {
			  newRoot = newRoot.getParent();
		  }
		  highTree.rootNode = newRoot;
		  lowTree.rootNode = newRoot;
		  if(x.getHeight() == c.getHeight()) {
			  rebalancePostInsert(x);
		  }
		  x.increaseSubTreeSizeAfterJoin(sizeOfThisTree);
	 	  System.out.println("join larger smaller done");

}
   
  

   // lowTree.key < highTree root
   public void joinSmallerWithLarger(IAVLNode x, AVLTree lowTree, AVLTree highTree) {
	 	  System.out.println("join smaller larger ");

	   int sizeOfThisTree = lowTree.getRoot().getSubTreeSize() + 1;
	   IAVLNode b = highTree.getRoot();
		  while(b.getHeight() > lowTree.getRoot().getHeight()) {

			  b = b.getLeft();
		  }
		  System.out.println("height of low size = "+lowTree.getRoot().getHeight() + "height of high tree = " + highTree.getRoot().getHeight());
		  IAVLNode c = b.getParent();
		  IAVLNode a = lowTree.getRoot();

		  c.setLeft(x);
		  x.setLeft(a);
		  x.setRight(b);
		  x.setParent(c);
		  a.setParent(x);
		  b.setParent(x);
		  System.out.println("11111111111");
		  x.setHeight(lowTree.getRoot().getHeight() + 1);
		  x.setSubTreeSize(x.getLeft().getSubTreeSize() + x.getRight().getSubTreeSize() + 1);
		  IAVLNode newRoot = c;
		  while(newRoot.getParent() != null) {
			  newRoot = newRoot.getParent();
		  }
		  System.out.println("22222222");
		  highTree.rootNode = newRoot;
		  lowTree.rootNode = newRoot;
		  if(x.getHeight() == c.getHeight()) {
			  System.out.println("3333333333");
			  rebalancePostInsert(x);
		  }
		  System.out.println("44444444444");
		  x.increaseSubTreeSizeAfterJoin(sizeOfThisTree);
		  System.out.println("55555555");
	 	  System.out.println("join smaller larger done");


}
   
   
   public int checkWhichIsLarger(int x, int y) {
	   if(x > y)
		   return 1;
	   else if(x< y)
		return 2;
	   return -1;
   }
   public void rotateRight(IAVLNode x) {
       IAVLNode y = x.getParent();
       IAVLNode b = x.getRight();
       IAVLNode yParent = y.getParent();
       IAVLNode a = x.getLeft();
       if (this.isRoot(y)) {
           this.rootNode = x;
       }
       else if (isRightSon(y)) {
           yParent.setRight(x);
       }
       else {
           yParent.setLeft(x);
       }
       
       // x case
       x.setParent(yParent);
       x.setRight(y);
       
       // y case
       y.setParent(x);
       y.setLeft(b);
       
       // b case
       b.setParent(y);
       
       
       y.setSubTreeSize(y.getLeft().getSubTreeSize() + y.getRight().getSubTreeSize() + 1);
       x.setSubTreeSize(x.getLeft().getSubTreeSize() + x.getRight().getSubTreeSize() + 1);
   }
   
   public void rotateLeft(IAVLNode y){
	   IAVLNode x = y.getParent();
	   IAVLNode b = y.getLeft();
	   IAVLNode xParent = x.getParent();
	   IAVLNode a = x.getLeft();
	      if(this.isRoot(x)){
	          this.rootNode = y;
	          y.setParent(null);
	      }
	      else if(isRightSon(x)){
	          xParent.setRight(y);
	      }
	      else{
	          xParent.setLeft(y);
	      }
	      
	      // y case
	      y.setParent(xParent);
	      y.setLeft(x);
	      
	      // x case
	      x.setParent(y);
	      x.setRight(b);
	      
	      // b case
	      b.setParent(x);
	      
	      x.setSubTreeSize(x.getLeft().getSubTreeSize() + x.getRight().getSubTreeSize() + 1);
	      y.setSubTreeSize(y.getLeft().getSubTreeSize() + y.getRight().getSubTreeSize() + 1);
	  }
   
   public static boolean isRightSon(IAVLNode node){
       return node.getParent().getRight().getKey() == node.getKey(); 
       }
   
   
   
   
   public boolean isRoot(IAVLNode node){
	       return node.getKey() == this.rootNode.getKey();
	      }
	/** 
	 * public interface IAVLNode
	 * ! Do not delete or modify this - otherwise all tests will fail !
	 */
	public interface IAVLNode extends PrintableNode{	
		public int getKey(); // Returns node's key (for virtual node return -1).
		public String getValue(); // Returns node's value [info], for virtual node returns null.
		public void setLeft(IAVLNode node); // Sets left child.
		public IAVLNode getLeft(); // Returns left child, if there is no left child returns null.
		public void setRight(IAVLNode node); // Sets right child.
		public IAVLNode getRight(); // Returns right child, if there is no right child return null.
		public void setParent(IAVLNode node); // Sets parent.
		public IAVLNode getParent(); // Returns the parent, if there is no parent return null.
		public boolean isRealNode(); // Returns True if this is a non-virtual AVL node.
    	public void setHeight(int height); // Sets the height of the node.
    	public int getHeight(); // Returns the height of the node (-1 for virtual nodes).

    	//
    	public void increaseSizeOfSubTreeOfAllParents();
    	public void setSubTreeSize(int size);
    	public int getSubTreeSize();
    	 public void increaseSubTreeSizeAfterJoin(int n);
	}

   /** 
    * public class AVLNode
    *
    * If you wish to implement classes other than AVLTree
    * (for example AVLNode), do it in this file, not in another file. 
    * 
    * This class can and MUST be modified (It must implement IAVLNode).
    */
  public class AVLNode implements IAVLNode{
	  	private AVLNode parentNode;
	  	private AVLNode leftSonNode, rightSonNode;
	  	private int height;
	  	private int key;
	  	private String value;
	  	private int subTreeSize;
	  	
	  	
	  	
	  	public AVLNode(int key, String value) {
	  		this.key = key;
	  		this.value = value;
	  		this.leftSonNode = (AVLNode) VIRTUAL_NODE;
	  		this.rightSonNode = (AVLNode) VIRTUAL_NODE;
	  	}
	  	
	  	public AVLNode(int key, String value, IAVLNode parentNode, IAVLNode left, IAVLNode right, int height) {
	  		this.key = key;
	  		this.height =height;
	  		this.value = value;
	  		this.parentNode = (AVLTree.AVLNode) parentNode;
	  		this.leftSonNode = (AVLTree.AVLNode) left;
	  		this.rightSonNode = (AVLTree.AVLNode) right;
	  		
	  	}
	  	
	  	public AVLNode() {
	  		this.height = -1;
	  	}
	  	
		public int getKey()
		{
			return this.key;
		}
		
		public String getValue()
		{
			return value;
		}
		
		public void setLeft(IAVLNode node)
		{
			this.leftSonNode = (AVLNode) node;
		}
		
		public IAVLNode getLeft()
		{
			return this.leftSonNode;
		}
		
		public void setRight(IAVLNode node)
		{
			this.rightSonNode = (AVLNode) node;
		}
		
		public IAVLNode getRight()
		{
			return this.rightSonNode;
		}
		
		public void setParent(IAVLNode node)
		{
			this.parentNode = (AVLNode) node;
		}
		
		public IAVLNode getParent()
		{
			return this.parentNode;
		}
		
		public boolean isRealNode()
		{
			return this.height != -1;
		}
		
	    public void setHeight(int height)
	    {
	      this.height = height;
	    }
	    
	    public int getHeight()
	    {
	    	return this.height;
	    }

		public boolean isLeaf() {
			if(this.isRealNode() && this.getLeft() == null && this.getRight() == null) {
				return true;
			}
			return false;
		}
		
		public void increaseSizeOfSubTreeOfAllParents() {
			IAVLNode tempNode = this.getParent();
			//Continue until reaching the root, updating all the way up
			while(tempNode != null) {
				tempNode.setSubTreeSize(tempNode.getSubTreeSize() + 1);
				tempNode = tempNode.getParent();
			}
		}
		
		public void setSubTreeSize(int size) {
			this.subTreeSize = size;
		}
		
		public int  getSubTreeSize() {
			return this.subTreeSize;
		}
		public void increaseSubTreeSizeAfterJoin(int n) {
		   IAVLNode tempNode = this.getParent();
		   while(tempNode != null) {
			   tempNode.setSubTreeSize(tempNode.getSubTreeSize() + n);
			   tempNode = tempNode.getParent();
		   }
	   }
		public String toString() {
			return "{Key: " + this.getKey() + ", Height: " + this.getHeight() + ", Tree size: " + this.getSubTreeSize() + "}";
		}
	} 
		
  }