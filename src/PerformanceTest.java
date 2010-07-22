import java.util.ArrayList;
import java.util.Random;

public class PerformanceTest {
	public static void main(String[] args) {
		ArrayList<Integer> l = new ArrayList<Integer>(10000);
		Random r = new Random();
		for (int i = 0; i < 10000; i++)
			l.add(r.nextInt(1000000));

		SuperList<Integer> lBubble = new SuperList<Integer>(l);
		long bubbleStart = System.currentTimeMillis();
		lBubble.bubbleSort();
		System.out.println("Bubblesort: "
				+ (System.currentTimeMillis() - bubbleStart) + "ms");

		SuperList<Integer> lInsertion = new SuperList<Integer>(l);
		long insertionStart = System.currentTimeMillis();
		lInsertion.insertionSort();
		System.out.println("Insertionsort: "
				+ (System.currentTimeMillis() - insertionStart) + "ms");

		SuperList<Integer> lSelection = new SuperList<Integer>(l);
		long selectionStart = System.currentTimeMillis();
		lSelection.insertionSort();
		System.out.println("Selectionsort: "
				+ (System.currentTimeMillis() - selectionStart) + "ms");
	}
}
