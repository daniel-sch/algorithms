import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class PerformanceTest {
	public static void main(String[] args) {
		ArrayList<Integer> l = new ArrayList<Integer>(10000);
		Random r = new Random();
		for (int i = 0; i < 10000; i++)
			l.add(r.nextInt(1000000));

		SuperArray<Integer> aBubble = new SuperArray<Integer>(l);
		long aBubbleStart = System.currentTimeMillis();
		aBubble.bubbleSort();
		System.out.println("Bubblesort (Array): "
				+ (System.currentTimeMillis() - aBubbleStart) + "ms");

		SuperArray<Integer> aInsertion = new SuperArray<Integer>(l);
		long aInsertionStart = System.currentTimeMillis();
		aInsertion.insertionSort();
		System.out.println("Insertionsort (Array): "
				+ (System.currentTimeMillis() - aInsertionStart) + "ms");

		SuperArray<Integer> aSelection = new SuperArray<Integer>(l);
		long aSelectionStart = System.currentTimeMillis();
		aSelection.selectionSort();
		System.out.println("Selectionsort (Array): "
				+ (System.currentTimeMillis() - aSelectionStart) + "ms");

		SuperArray<Integer> aQuick = new SuperArray<Integer>(l);
		long aQuickStart = System.currentTimeMillis();
		aQuick.quickSort();
		System.out.println("Quicksort (Array): "
				+ (System.currentTimeMillis() - aQuickStart) + "ms");

		SuperList<Integer> lBubble = new SuperList<Integer>(l);
		long lBubbleStart = System.currentTimeMillis();
		lBubble.bubbleSort();
		System.out.println("Bubblesort (Liste): "
				+ (System.currentTimeMillis() - lBubbleStart) + "ms");

		SuperList<Integer> lInsertion = new SuperList<Integer>(l);
		long lInsertionStart = System.currentTimeMillis();
		lInsertion.insertionSort();
		System.out.println("Insertionsort (Liste): "
				+ (System.currentTimeMillis() - lInsertionStart) + "ms");

		SuperList<Integer> lSelection = new SuperList<Integer>(l);
		long lSelectionStart = System.currentTimeMillis();
		lSelection.selectionSort();
		System.out.println("Selectionsort (Liste): "
				+ (System.currentTimeMillis() - lSelectionStart) + "ms");

		SuperList<Integer> lQuick = new SuperList<Integer>(l);
		long lQuickStart = System.currentTimeMillis();
		lQuick.quickSort();
		System.out.println("Quicksort (Liste): "
				+ (System.currentTimeMillis() - lQuickStart) + "ms");

		SuperList<Integer> lMerge = new SuperList<Integer>(l);
		long lMergeStart = System.currentTimeMillis();
		lMerge.mergeSort();
		System.out.println("Mergesort (Liste): "
				+ (System.currentTimeMillis() - lMergeStart) + "ms");

		long referenceStart = System.currentTimeMillis();
		Collections.sort(l);
		System.out.println("Referenzimplementierung: "
				+ (System.currentTimeMillis() - referenceStart) + "ms");

		if (!l.equals(aBubble))
			System.out.println("Bubblesort failed!");
		if (!l.equals(aInsertion))
			System.out.println("Insertionsort failed!");
		if (!l.equals(aSelection))
			System.out.println("Selectionsort failed!");
		if (!l.equals(aQuick))
			System.out.println("Quicksort failed!");
		if (!l.equals(lBubble))
			System.out.println("Bubblesort failed!");
		if (!l.equals(lInsertion))
			System.out.println("Insertionsort failed!");
		if (!l.equals(lSelection))
			System.out.println("Selectionsort failed!");
		if (!l.equals(lQuick))
			System.out.println("Quicksort failed!");
		if (!l.equals(lMerge))
			System.out.println("Mergesort failed!");
	}
}
