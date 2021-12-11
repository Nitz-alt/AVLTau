
import java.util.ArrayList;
import java.util.List;

/**
 * Binary tree printer
 * 
 * @author MightyPork
 */
public class TreePrinter
{
    /** Node that can be printed */
    public interface PrintableNode
    {
        /** Get left child */
        PrintableNode getLeft();


        /** Get right child */
        PrintableNode getRight();


        /** Get text to be printed */
        int getKey();
        
        boolean isRealNode();
        
        int getHeight();
        
        int  getSubTreeSize();
    }


    /**
     * Print a tree
     * 
     * @param root
     *            tree root node
     */
    public static void print(PrintableNode root)
    {
        List<List<String>> lines = new ArrayList<List<String>>();

        List<PrintableNode> level = new ArrayList<PrintableNode>();
        List<PrintableNode> next = new ArrayList<PrintableNode>();

        level.add(root);
        int nn = 1;

        int widest = 0;

        while (nn != 0) {
            List<String> line = new ArrayList<String>();

            nn = 0;

            for (PrintableNode n : level) {
                if (n == null) {
                    line.add(null);

                    next.add(null);
                    next.add(null);
                } else {
                    String aa = n.getKey() + ":" + n.getSubTreeSize() + ":" + n.getHeight();
                    line.add(aa);
                    if (aa.length() > widest) widest = aa.length();

                    next.add(n.getLeft());
                    next.add(n.getRight());

                    if (n.getLeft() != null) nn++;
                    if (n.getRight() != null) nn++;
                }
            }

            if (widest % 2 == 1) widest++;

            lines.add(line);

            List<PrintableNode> tmp = level;
            level = next;
            next = tmp;
            next.clear();
        }

        int perpiece = lines.get(lines.size() - 1).size() * (widest + 4);
        for (int i = 0; i < lines.size(); i++) {
            List<String> line = lines.get(i);
            int hpw = (int) Math.floor(perpiece / 2f) - 1;

            if (i > 0) {
                for (int j = 0; j < line.size(); j++) {

                    // split node
                    char c = ' ';
                    if (j % 2 == 1) {
                        if (line.get(j - 1) != null) {
                            c = (line.get(j) != null) ? '┴' : '┘';
                        } else {
                            if (j < line.size() && line.get(j) != null) c = '└';
                        }
                    }
                    System.out.print(c);

                    // lines and spaces
                    if (line.get(j) == null) {
                        for (int k = 0; k < perpiece - 1; k++) {
                            System.out.print(" ");
                        }
                    } else {

                        for (int k = 0; k < hpw; k++) {
                            System.out.print(j % 2 == 0 ? " " : "─");
                        }
                        System.out.print(j % 2 == 0 ? "┌" : "┐");
                        for (int k = 0; k < hpw; k++) {
                            System.out.print(j % 2 == 0 ? "─" : " ");
                        }
                    }
                }
                System.out.println();
            }

            // print line of numbers
            for (int j = 0; j < line.size(); j++) {

                String f = line.get(j);
                if (f == null) f = "";
                int gap1 = (int) Math.ceil(perpiece / 2f - f.length() / 2f);
                int gap2 = (int) Math.floor(perpiece / 2f - f.length() / 2f);

                // a number
                for (int k = 0; k < gap1; k++) {
                    System.out.print(" ");
                }
                System.out.print(f);
                for (int k = 0; k < gap2; k++) {
                    System.out.print(" ");
                }
            }
            System.out.println();

            perpiece /= 2;
        }
    }
    
    public static void main(String[] args) {
    	AVLTree tree = new AVLTree();
    	int[] arr = {7, 10 ,5, 20 , 13};
    	for (int i : arr) {
    		tree.insert(i, null);
    	}
    	AVLTree tree2 = new AVLTree();
    	int[] arr2 = {100, 200, 50};
    	for (int i : arr2) {
    		tree2.insert(i, null);
    	}
    
    	System.out.println("printing both trees");
    	print(tree.getRoot());
    	print(tree2.getRoot());
    	
    	System.out.println("joining");
    	IAVLNode node = new AVLTree().new AVLNode(49,"");
    	tree2.join(node, tree);
   	
    	System.out.println("\n\n\n************\n\n\n\n");
    	System.out.println("printing both trees");
    	print(tree.getRoot());
    	print(tree2.getRoot());
    }
    
    
    
}