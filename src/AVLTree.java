import java.time.Year;

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
	   int total = 0; 
	   IAVLNode currNode = searchNode(k); 
	   if (!currNode.isRealNode()) return -1; // Key not in tree
	   if (currNode != this.rootNode) { // Node is not the root
		   IAVLNode parentNode = currNode.getParent();
		   IAVLNode leftChildNode = currNode.getLeft();
		   IAVLNode rightChildNode = currNode.getRight();
		   if (!leftChildNode.isRealNode() && !rightChildNode.isRealNode()) { // The node is a a leaf
			   int leftRankDelta = parentNode.getHeight() - currNode.getHeight();
			   int rightRankDelta = parentNode.getHeight() - parentNode.getRight().getHeight();
			   // The junction is a (leftRankDelta, rightRankDelta).
			   if (leftRankDelta == 1 && rightRankDelta == 1) { // Junction is 1, 1 can just delete
				   
				   this.deletionNode(currNode); // Deletion
				   
				   return 0;
			   }
			   else if ((leftRankDelta == 1 && rightRankDelta == 2) || (leftRankDelta == 2 && rightRankDelta == 1)) { // Junction is 1,2 or 2,1 delete and demote parent and check up.
				   if ((parentNode.getLeft() == currNode && leftRankDelta == 1) || (parentNode.getRight() == currNode && rightRankDelta == 1)) { // Current node is
					   
					   this.deletionNode(currNode); // Deletion
					   
					   parentNode.setHeight(parentNode.getHeight() - 1); // Demoting parent
					   
					   // Rebalancing
					   currNode = parentNode;
					   return deletionRebalance(currNode); // The can now only be in the parent (of the parent) rebalance needs to be with a loop
				   }
				   else if ((parentNode.getLeft() == currNode && leftRankDelta == 2) || (parentNode.getRight() == currNode && rightRankDelta == 2)) {
					   // Cases of (3,1) and (1,3)
					   this.deletionNode(currNode); // Deletion
					   // Now the junction is at state (3,1) or (1,3)
					   
				   }
			   }
		   }
	   }   
   }
   private void deletionNode(IAVLNode currNode) {
	   if (currNode == this.rootNode) {
		   this.rootNode = VIRTUAL_NODE;
		   return;
	   }
	   IAVLNode parentNode = currNode.getParent();
	   if (parentNode.getLeft() == currNode) { // Current node is the left child
		   parentNode.setLeft(VIRTUAL_NODE);
	   }
	   else { // Current node is right child
		   parentNode.setRight(VIRTUAL_NODE);
	   }
   }
   private int deletionRebalance(IAVLNode currNode) {
	   // Needs Implementing
	   return 0;
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
  
