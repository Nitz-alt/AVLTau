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
  
  // Looks for key in the subtree of node. If it is found, return it. Else, returns the last node encountered 
  public IAVLNode treePosition(IAVLNode node, int key) {
	  IAVLNode tempNode = node;
	  while(node != null) {
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
	   int numOfOps = 0;
	   IAVLNode searchNodeResult = treePosition(rootNode, k);
	   IAVLNode inNode = new AVLNode(k,i);
	   // If tree is empty, insert node in the root.
	   if(searchNodeResult == null) { 
		  this.rootNode = new AVLNode(k,i,null, VIRTUAL_NODE,VIRTUAL_NODE,0);
		  this.maxNode = rootNode;
		  this.minNode = rootNode;
		  return 0;
	   }
	   // If node is found in the tree, we do nothing, num of operations has not changed.
	   else if(searchNodeResult.getKey() == k) {
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
		 insertByCase(searchNodeResult,inNode);
	   }
   
   //Determines side of insertion
   public int insertByCase(IAVLNode posNode, IAVLNode newNode) {
	   int numOps = 0;
	   if (newNode.getKey() > posNode.getKey()) {
		   numOps = rightInsert(posNode, newNode);
	   }
	   else {
		   numOps = leftInsert(posNode, newNode);
	   }
	   return numOps;
   }
   
   
private int leftInsert(AVLTree.IAVLNode posNode, AVLTree.IAVLNode newNode) {
	return 0;
}

// Determines case of insertion for right side insertion  
  public int rightInsert(IAVLNode posNode, IAVLNode insertNode) {
	  int numOps = 0;
	  posNode.setRight(insertNode);
	  insertNode.setParent(posNode);
  }
  
 
  public int rebalancePostInsert (IAVLNode insertNode){
      IAVLNode currNode = insertNode;
      int numOps = 0;
      int currOpsNum = 0;
      
      //Traverse the tree until you get to the top
      while(!isRoot(currNode)){ 
    	  //Child and parent are of the same height
          if (currNode.getHeight() == currNode.getParent().getHeight()){
             numOps += this.rotateIndex(currNode); 
             //Rotations finalized, no need for promote
              if (currOpsNum > 0){ 
                  this.updateSizeOfSubTreeInsert(insertNode);
                  return numOps;
              }
              // Rotation has not happened yet, time to promote. 
              else {
            	  //Promote operation
                  currNode.getParent().setHeight(currNode.getHeight()+1);
                  numOps++;
                  currNode = currNode.getParent();
              }
          }
          
          //Problem did not go up the tree
          else if (currNode.getParent().getHeight() > insertNode.getHeight()){
              this.updateSizeOfSubTreeInsert(insertNode);
              return numberOfOperations;
          }
      }
      //from here it's taking care of the root maybe it's son was promoted and now rankDif = 0
      if(rankDif(curr.getLeftGood(),curr) == 0 && rankDif(curr.getRightGood(),curr) == 2){
          numberOfOperations += rotate(curr.getLeftGood());
      }
      else if (rankDif(curr.getLeftGood(),curr) == 2 && rankDif(curr.getRightGood(),curr) == 0){
          numberOfOperations += rotate(curr.getRightGood());
      }
      this.updateSizeOfSubTreeInsert(newNode);
      return numberOfOperations;
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
	   return 421;	// to be replaced by student code
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
	return rootNode.subTreeSize();	   
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

   public void rotateRight(IAVLNode x) {
       IAVLNode z = x.getParent();
       IAVLNode b = x.getRight();
       IAVLNode zParent = z.getParent();
       IAVLNode a = x.getLeft();
       if (this.isRoot(z)) {
           this.rootNode = x;
       }
       else if (isRightSon(z)) {
           zParent.setRight(x);
       }
       else {
           zParent.setLeft(x);
       }
       x.setParent(zParent);
       x.setRight(z);
       z.setParent(x);
       z.setLeft(b);
       b.setParent(z);
       int zTempSize = z.getSubTreeSize();
       z.setSubTreeSize(zTempSize - a.getSubTreeSize());
       x.setSubTreeSize(zTempSize);
   }
   
   public void rotateLeft(IAVLNode x){
	   IAVLNode z = x.getParent();
	   IAVLNode b = x.getLeft();
	   IAVLNode zParent = z.getParent();
	   IAVLNode a = x.getRight();
	      if(this.isRoot(z)){
	          this.rootNode = x;
	      }
	      else if(isRightSon(z)){
	          zParent.setRight(x);
	      }
	      else{
	          zParent.setLeft(x);
	      }
	      x.setParent(zParent);
	      x.setLeft(z);
	      z.setParent(x);;
	      z.setRight(b);
	      b.setParent(z);
	      int zTempSize = z.getSubTreeSize();
	      z.setSubTreeSize(zTempSize - a.getSubTreeSize());
	      x.setSubTreeSize(zTempSize); 
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
    	public int subTreeSize();
    	public void setSubTreeSize(int size);
    	public int getSubTreeSize();
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
	  		this.subTreeSize = subTreeSize();
	  		
	  	}
	  	
	  	public AVLNode(int key, String value, IAVLNode parentNode, IAVLNode left, IAVLNode right, int height) {
	  		this.key = key;
	  		this.height =height;
	  		this.value = value;
	  		this.parentNode = (AVLTree.AVLNode) parentNode;
	  		this.leftSonNode = (AVLTree.AVLNode) left;
	  		this.rightSonNode = (AVLTree.AVLNode) right;
	  		this.subTreeSize = subTreeSize();
	  		
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
		
		public int subTreeSize() {
			if (!this.isRealNode()) {
				return 0;
			}
			if(this.isLeaf()) {
				return 1;
			}
			return this.getLeft().subTreeSize() + this.getRight().subTreeSize() + 1;
		}
		
		public void setSubTreeSize(int size) {
			this.subTreeSize = size;
		}
		
		public int  getSubTreeSize() {
			return this.subTreeSize;
		}
		
	    
		
  }
}
  
