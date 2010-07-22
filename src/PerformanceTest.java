import java.util.ArrayList;
import java.util.Collections;
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
		lSelection.selectionSort();
		System.out.println("Selectionsort: "
				+ (System.currentTimeMillis() - selectionStart) + "ms");

		SuperList<Integer> lQuick = new SuperList<Integer>(l);
		long quickStart = System.currentTimeMillis();
		lQuick.quickSort();
		System.out.println("Quicksort: "
				+ (System.currentTimeMillis() - quickStart) + "ms");

		SuperList<Integer> lMerge = new SuperList<Integer>(l);
		long mergeStart = System.currentTimeMillis();
		lMerge.mergeSort();
		System.out.println("Mergesort: "
				+ (System.currentTimeMillis() - mergeStart) + "ms");

		long referenceStart = System.currentTimeMillis();
		Collections.sort(l);
		System.out.println("Referenzimplementierung: "
				+ (System.currentTimeMillis() - referenceStart) + "ms");

		if(!l.equals(lBubble))
			System.out.println("Bubblesort failed!");
		if(!l.equals(lInsertion))
			System.out.println("Insertionsort failed!");
		if(!l.equals(lSelection))
			System.out.println("Selectionsort failed!");
		if(!l.equals(lQuick))
			System.out.println("Quicksort failed!");
		if(!l.equals(lMerge))
			System.out.println("Mergesort failed!");
	}
}
