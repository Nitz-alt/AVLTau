import java.util.Currency;

/**
 *
 * AVLTree
 *
 * An implementation of a׳� AVL Tree with
 * distinct integer keys and info.
 *
 */

public class AVLTree{
	public final IAVLNode VIRTUAL_NODE = new AVLNode();
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
	   IAVLNode searchNodeResult = treePosition(rootNode, k);
	   IAVLNode inNode = new AVLNode(k, i, /* parent */ null, /* left */ VIRTUAL_NODE, /* right */ VIRTUAL_NODE, /* height */ 0);
	   // If tree is empty, insert node in the root.
	   if(searchNodeResult == VIRTUAL_NODE) { 
		  this.rootNode = inNode;
		  this.maxNode = rootNode;
		  this.minNode = rootNode;
		  rootNode.setSubTreeSize(1);
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
    	  IAVLNode z = x.getParent();
    	  //Child and parent are of the same height - promote or rotate
          if (x.getHeight() == z.getHeight()){
        	  if (z.getLeft() ==x) { // x is left child
        		  IAVLNode y = z.getRight();
				int rankDifZY = z.getHeight() - y.getHeight();
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
						System.out.println("drr");
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
							System.out.println("drl");
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
	        	  }
        	  }
          
          }// 
          }//while
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
	  if(this.empty()) {
		  return new int[0];
	  }
	  int numOfNodes = rootNode.getSubTreeSize();
      int[] keysArray = new int[numOfNodes];
      IAVLNode minimalNode = this.minNode;
      IAVLNode currNode = minimalNode;
      for (int i = 0; i < numOfNodes; i++){// This is O(n), proved in recitation
          keysArray[i] = currNode.getKey();
          //currNode = succesor(currNode);
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
          //currNode = succesor(currNode);
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
	return rootNode.getSubTreeSize();	   
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
//	   AVLTree smallerThanXAvlTree = new AVLTree();
//	   AVLTree greaterThanXAvlTree = new AVLTree();
//	   String valString = search(x);
//	   IAVLNode searchNode = new AVLNode(x, valString);
//	   IAVLNode currNode = treePosition(searchNode,x);
//	   while(currNode != null) {
//		   if(currNode.getParent() != null)//We did not reach the root yet
//			   if(currNode.getLeft() != null)
//				   
//			   
//			   
//			   
//		   }
//		   
//		   
//	   }
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
	   //t tree is the larger of the two
	  if(t.getRoot().getSubTreeSize() > this.getRoot().getSubTreeSize()) {
		 IAVLNode b = t.getRoot();
		 while(b.getHeight() != -1) {
			 //We arrived to a node with the same rank as x, join the two
			 if(b.getHeight() <= this.getRoot().getHeight()) {
				 IAVLNode c = b.getParent();
				 IAVLNode a = this.getRoot();
				 c.setLeft(x);
				 x.setLeft(a);
				 x.setRight(b);
				 b.setParent(x);
				 a.setParent(x);
				 x.setHeight(this.getRoot().getHeight() + 1);
				 if(x.getHeight() == c.getHeight()) {
					 rebalancePostInsert(x);
				 }
				 break;
			 }
			 else {
				 b = b.getLeft();
			 }
	  }
	  }
	 return 0; 
		  
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

    	//
    	public void increaseSizeOfSubTreeOfAllParents();
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
		
	    
		
  }
}
  
