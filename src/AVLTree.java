/**
 *
 * AVLTree
 *
 * An implementation of a׳� AVL Tree with
 * distinct integer keys and info.
 *
 */

public class AVLTree {
	final IAVLNode VIRTUAL_NODE = new AVLNode();
	private IAVLNode rootNode = VIRTUAL_NODE;
	private IAVLNode minNode;
	private IAVLNode maxNode;
  /**
   * public boolean empty()
   *
   * Returns true if and only if the tree is empty.
   *
   */
  public boolean empty() {
    return !this.rootNode.isRealNode();
  }

 /**
   * public String search(int k)
   *
   * Returns the info of an item with key k if it exists in the tree.
   * otherwise, returns null.
   */
  public String search(int k)
  {
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
	  return 420;	// to be replaced by student code
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
	      
   }
   
   private int deleteWithNode(int k, IAVLNode curr) {
	   int total = 0;
	   IAVLNode currNode;
	   if (curr == null) {
		   currNode = searchNode(k);
	   }
	   else {
		   currNode = curr;
	   }
	   if (!currNode.isRealNode()) return -1; // Key not in tree
	   if (currNode != this.rootNode) { // Node is not the root
		   // Only deletion
		   IAVLNode parentNode = currNode.getParent();
		   IAVLNode leftChildNode = currNode.getLeft();
		   IAVLNode rightChildNode = currNode.getRight();
		   if (!leftChildNode.isRealNode() && !rightChildNode.isRealNode()) { // Node to be deleted is a leaf
			   if (parentNode.getLeft() == currNode) { // Deletion - Setting parent child to virutal node
				   parentNode.setLeft(VIRTUAL_NODE);
			   }
			   else {
				   parentNode.setRight(VIRTUAL_NODE);
			   }
		   }
		   else if ((leftChildNode.isRealNode() && !rightChildNode.isRealNode()) || (!leftChildNode.isRealNode() && rightChildNode.isRealNode())) { // Node to be deleted is an unary node.
			   if (parentNode.getLeft() == currNode) { // Deleted node is a left child
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
		   else if(leftChildNode.isRealNode() && rightChildNode.isRealNode()) { // Binary node - Deleting the predecessor.
			   // Finding predecessor
			   IAVLNode currSuccessor = successor(currNode);
			   total += deleteWithNode(k, currSuccessor); // Deleting successor
			   // Switching between successor and node to be deleted
			   currSuccessor.setLeft(currNode.getLeft());
			   currSuccessor.setRight(currNode.getRight());
			   currSuccessor.setParent(parentNode);
			   currSuccessor.setHeight(currNode.getHeight());
			   if (parentNode.getLeft() == currNode) { // Deleted node is the left child
				   parentNode.setLeft(currSuccessor);
			   }
			   else { // Deleted node is a right child
				   parentNode.setRight(currSuccessor);
			   }
			   return total;
		   }
		   // Only deletion end
		   // Balancing
		   // currNode is deleted should not be used here in any case;
		   int leftRankDelta = parentNode.getHeight() - leftChildNode.getHeight();
		   int rightRankDelta = parentNode.getHeight() - rightChildNode.getHeight();
		   if ((leftRankDelta == 2 && rightRankDelta == 1) || (leftRankDelta == 1 && rightRankDelta == 2)) { // (1,2) or (2,1) is ok
			   return 0;
		   }
		   if (leftRankDelta == 2 && rightRankDelta == 2) { // Demoting parent and balancing up;
			   parentNode.setHeight(parentNode.getHeight()-1);
			   rebalancing(parentNode.getParent());
		   }
		   if ((leftRankDelta == 3 && rightRankDelta == 1) || (leftRankDelta == 1 && rightRankDelta == 3)) { // Junction is (3,1) or (1,3)
			   if (leftRankDelta == 3) { // Rank delta is 3 on left side - rotate
				   
			   }
		   }
		   
		   
	   }
   }
   
   
   
   private static IAVLNode successor(IAVLNode curr) {
	   return curr;
   }
   
   private int rebalancing(IAVLNode curr) { // Gets the parent of the junction
	   int total = 0;
	   while (curr != this.rootNode) {
		   //int parentHeight = curr.getHeight();
		   IAVLNode parentNode = curr.getParent();
		   IAVLNode leftChildNode = curr.getLeft();
		   IAVLNode rightchildNode = curr.getRight();
		   int leftRankDelta = curr.getHeight() - leftChildNode.getHeight();
		   int rightRankDelta = curr.getHeight() - rightchildNode.getHeight();
		   if ((leftRankDelta == 2 && rightRankDelta == 1) || (leftRankDelta == 1 && rightRankDelta == 2)) { // (1,2) or (2,1) is ok
			   return total; // Nothing done price is 0
		   }
		   if (leftRankDelta == 2 && rightRankDelta == 2) { // Demoting parent and balancing up;
			   curr.setHeight(curr.getHeight()-1);
			   curr = parentNode;
			   total += 1; // Demoting price is 1
		   }
		   if ((leftRankDelta == 3 && rightRankDelta == 1) || (leftRankDelta == 1 && rightRankDelta == 3)) { // Junction is (3,1) or (1,3)
			   if (leftRankDelta == 3) { // Rank delta is 3 on left side - rotate
				   // Parent right child shouldn't be a virtual node
				   int rightChildLeftRankDelta = curr.getRight().getHeight() - curr.getRight().getLeft().getHeight();
				   int rightChildRightRankDelta = curr.getRight().getHeight() - curr.getRight().getRight().getHeight();
				   if (rightChildLeftRankDelta == 1 && rightChildRightRankDelta == 1) { // Right child is a (1,1) junction.
					   rotateLeft(curr, rightchildNode);
					   curr.setHeight(curr.getHeight() - 1); // Demoting the (prev) parent
					   rightchildNode.setHeight(rightchildNode.getHeight() + 1); // Promoting right child (who is now the parent)
					   return total + (1 + 2); // One rotation and 2 promotes/demotes (Parent should be at same height)
				   }
				   else if(rightChildLeftRankDelta == 2 && rightChildRightRankDelta == 1) { // Right child is a (2,1) junction.
					   rotateLeft(curr, curr.getRight());
					   curr.setHeight(curr.getHeight() - 2);
					   total += 3;
					   curr = parentNode;
				   }
				   else if(rightChildLeftRankDelta == 1 && rightChildRightRankDelta == 2) { // Right child is a (1,2) junction
					   // Double rotation
					   IAVLNode prevRightChildLeft = rightchildNode.getLeft();
					   rotateRight(prevRightChildLeft, rightchildNode);
					   rotateRight(curr.getRight(), curr);
					   prevRightChildLeft.setHeight(prevRightChildLeft.getHeight() + 1);
					   rightchildNode.setHeight(rightchildNode.getHeight() - 1);
					   total += 4;
					   curr = parentNode;
				   }
			   }
			   else if (rightRankDelta == 3) { // Symetrical cases
				   int leftChildRightRankDelta = curr.getLeft().getHeight() - curr.getLeft().getRight().getHeight();
				   int leftChildLeftRankDelta = curr.getLeft().getHeight() - curr.getLeft().getLeft().getHeight();
				   if (leftChildRightRankDelta == 1 && leftChildLeftRankDelta == 1) { // Left child is a (1,1) junction -- symterical
					   rotateRight(curr, leftChildNode);
					   curr.setHeight(curr.getHeight() - 1); // Demoting the (prev) parent
					   leftChildNode.setHeight(leftChildNode.getHeight() + 1); // Promoting left child (who is now the parent)
					   return total + (1 + 2); // One rotation and 2 promotes/demotes (Parent should be at same height);
				   }
				   else if (leftChildRightRankDelta == 2 && leftChildLeftRankDelta == 1) { // Left child is a (1,2) junction
					   rotateRight(curr, curr.getLeft());
					   curr.setHeight(curr.getHeight() - 2);
					   total += 3;
					   curr = parentNode;
				   }
				   else if (leftChildRightRankDelta == 1 && leftChildLeftRankDelta == 2) { // Left child is a (2,1) junction
					   // Double rotation
					   IAVLNode prevLeftChildRight = leftChildNode.getRight(); // This is going to be the final parent in the junction
					   rotateLeft(prevLeftChildRight, leftChildNode);
					   rotateLeft(curr.getLeft(), curr);
					   prevLeftChildRight.setHeight(prevLeftChildRight.getHeight() + 1);
					   leftChildNode.setHeight(leftChildNode.getHeight() - 1);
					   total += 4;
					   curr = parentNode;
				   }
			   }
		   }
		   
		   
		   
		   
		   
	   }
	   return total;
	   
   }
   
   
   private IAVLNode searchNode(int k) {
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
	   return currNode;
   }
   
   /**
    * public String min()
    *
    * Returns the info of the item with the smallest key in the tree,
    * or null if the tree is empty.
    */
   public String min()
   {
	   return this.minNode.getValue(); 
	   /**IAVLNode currNode = this.rootNode;
	   while(currNode.getLeft().isRealNode()) {
		   currNode= currNode.getLeft();
	   }
	   return currNode.getValue();
	   */
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
        return new int[33]; // to be replaced by student code
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
        return new String[55]; // to be replaced by student code
  }

   /**
    * public int size()
    *
    * Returns the number of nodes in the tree.
    */
   public int size()
   {
	   return 422; // to be replaced by student code
   }
   
   /**
    * public int getRoot()
    *
    * Returns the root AVL node, or null if the tree is empty
    */
   public IAVLNode getRoot()
   {
	   return null;
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
	   return null; 
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
	   return -1;
   }

   public void rotateRight(IAVLNode x, IAVLNode y) {
	    IAVLNode xPrevRightChildNode = x.getRight();
	    x.setRight(y);
	    x.setParent(y.getParent());
	    y.getParent().setRight(x);
	    y.setParent(x);
	    y.setLeft(xPrevRightChildNode);
	    xPrevRightChildNode.setParent(y);
   }
   
   public void rotateLeft(IAVLNode y, IAVLNode x) {
	    IAVLNode yPrevLeftChildNode = y.getLeft();
	    y.setLeft(x);
	    x.setRight(yPrevLeftChildNode);
	    y.setParent(x.getParent());
	    x.setParent(y);
	    yPrevLeftChildNode.setParent(x);
  }
	/** 
	 * public interface IAVLNode
	 * ! Do not delete or modify this - otherwise all tests will fail !
	 */
	public interface IAVLNode{	
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
	  	
	  	public AVLNode(int key, String value) {
	  		this.key = key;
	  		this.value = value;
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
  }
}
  
