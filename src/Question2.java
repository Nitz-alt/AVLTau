import java.util.*;

public class Question2 {

	public static void main(String[] args) {
		System.out.println("Sorted");
		testSorted();
		System.out.println("Random");
		testRandom();
		
	}
	
	
	
	
	public static void testSorted() {
		int power = 1000 * (int) Math.pow(2, 5);
		AVLTree tree = new AVLTree();
		List<Integer> keys = new ArrayList<Integer>();
		for (int j = power; j > 0; j-- ) {
			keys.add(j);
		}
		
		int lowerBound = 0;
		int sum = 0;
		for (int b = 1; b <= 5 ; b++) {
			int upperBound = 1000 * (int) Math.pow(2, b);
			for (int j = lowerBound; j < upperBound; j++ ) {
				sum += tree.insert(keys.get(j), ""+keys.get(j));
			}
			System.out.println(String.format("%d\t%d", sum, hilufim(keys, upperBound)));
			lowerBound = upperBound;
		}
	}
	
	public static void testRandom() {
		AVLTree tree = new AVLTree();
		Random rand = new Random();
		Set<Integer> k = new HashSet<Integer>();
		int bound = 10 * 1000 * (int) Math.pow(2, 5);
		int size = bound / 5;
		while (k.size() < size) {
			k.add(rand.nextInt(bound));
		}
		
		List<Integer> keys = new ArrayList<Integer>(k);
		int lowerBound = 0;
		int sum = 0;
		for (int b = 1; b <= 5; b++) {
			int topBound = 1000 * (int) Math.pow(2, b);
			for (int j = lowerBound; j < topBound ; j++) {
				sum += tree.insert(keys.get(j), "");
			}
			lowerBound = topBound;
			System.out.println(sum + "\t" + hilufim(keys, topBound));
		}
		
	}
	
	
	
	
	
	
	
	public static int hilufim(List<Integer> ar, int upperBound) {
		int sum = 0;
		for (int j = 0; j < upperBound; j++) {
			for (int i = j+1 ; i < upperBound; i++) {
				if (ar.get(i) < ar.get(j))
					sum += 1;
			}
		}
		return sum;
	}
	
	
	
}
