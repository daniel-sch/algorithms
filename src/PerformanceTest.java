import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class PerformanceTest {
	private static final int num = 10000;
	private static final boolean slow = true;

	public static void main(String[] args) {
		ArrayList<Integer> l = new ArrayList<Integer>(num);
		Random r = new Random();
		for (int i = 0; i < num; i++)
			l.add(r.nextInt(1000000));

		SuperArray<Integer> aBubble = new SuperArray<Integer>(l);
		SuperArray<Integer> aInsertion = new SuperArray<Integer>(l);
		SuperArray<Integer> aShell = new SuperArray<Integer>(l);
		SuperArray<Integer> aSelection = new SuperArray<Integer>(l);
		SuperArray<Integer> aQuick = new SuperArray<Integer>(l);
		SuperArray<Integer> aMerge = new SuperArray<Integer>(l);
		SuperArray<Integer> aHeap = new SuperArray<Integer>(l);
		SuperList<Integer> lBubble = new SuperList<Integer>(l);
		SuperList<Integer> lInsertion = new SuperList<Integer>(l);
		SuperList<Integer> lSelection = new SuperList<Integer>(l);
		SuperList<Integer> lQuick = new SuperList<Integer>(l);
		SuperList<Integer> lMerge = new SuperList<Integer>(l);

		if (slow) {
			long aBubbleStart = System.currentTimeMillis();
			aBubble.bubbleSort();
			System.out.println("Bubblesort (Array): "
					+ (System.currentTimeMillis() - aBubbleStart) + "ms");

			long aInsertionStart = System.currentTimeMillis();
			aInsertion.insertionSort();
			System.out.println("Insertionsort (Array): "
					+ (System.currentTimeMillis() - aInsertionStart) + "ms");

			long aSelectionStart = System.currentTimeMillis();
			aSelection.selectionSort();
			System.out.println("Selectionsort (Array): "
					+ (System.currentTimeMillis() - aSelectionStart) + "ms");

			long lBubbleStart = System.currentTimeMillis();
			lBubble.bubbleSort();
			System.out.println("Bubblesort (Liste): "
					+ (System.currentTimeMillis() - lBubbleStart) + "ms");

			long lInsertionStart = System.currentTimeMillis();
			lInsertion.insertionSort();
			System.out.println("Insertionsort (Liste): "
					+ (System.currentTimeMillis() - lInsertionStart) + "ms");

			long lSelectionStart = System.currentTimeMillis();
			lSelection.selectionSort();
			System.out.println("Selectionsort (Liste): "
					+ (System.currentTimeMillis() - lSelectionStart) + "ms");
		}

		long aShellStart = System.currentTimeMillis();
		aShell.shellSort();
		System.out.println("Shellsort (Array): "
				+ (System.currentTimeMillis() - aShellStart) + "ms");

		long aQuickStart = System.currentTimeMillis();
		aQuick.quickSort();
		System.out.println("Quicksort (Array): "
				+ (System.currentTimeMillis() - aQuickStart) + "ms");

		long aMergeStart = System.currentTimeMillis();
		aMerge.mergeSort();
		System.out.println("Mergesort (Array): "
				+ (System.currentTimeMillis() - aMergeStart) + "ms");

		long aHeapStart = System.currentTimeMillis();
		aHeap.heapSort();
		System.out.println("Heapsort (Array): "
				+ (System.currentTimeMillis() - aHeapStart) + "ms");

		long lQuickStart = System.currentTimeMillis();
		lQuick.quickSort();
		System.out.println("Quicksort (Liste): "
				+ (System.currentTimeMillis() - lQuickStart) + "ms");

		long lMergeStart = System.currentTimeMillis();
		lMerge.mergeSort();
		System.out.println("Mergesort (Liste): "
				+ (System.currentTimeMillis() - lMergeStart) + "ms");

		long referenceStart = System.currentTimeMillis();
		Collections.sort(l);
		System.out.println("Referenzimplementierung: "
				+ (System.currentTimeMillis() - referenceStart) + "ms");

		if (slow) {
			if (!l.equals(aBubble))
				System.out.println("Bubblesort failed!");
			if (!l.equals(aInsertion))
				System.out.println("Insertionsort failed!");
			if (!l.equals(aSelection))
				System.out.println("Selectionsort failed!");
			if (!l.equals(lBubble))
				System.out.println("Bubblesort failed!");
			if (!l.equals(lInsertion))
				System.out.println("Insertionsort failed!");
			if (!l.equals(lSelection))
				System.out.println("Selectionsort failed!");
		}
		if (!l.equals(aShell))
			System.out.println("Shellsort failed!");
		if (!l.equals(aQuick))
			System.out.println("Quicksort failed!");
		if (!l.equals(aMerge))
			System.out.println("Mergesort failed!");
		if (!l.equals(aHeap))
			System.out.println("Heapsort failed!");
		if (!l.equals(lQuick))
			System.out.println("Quicksort failed!");
		if (!l.equals(lMerge))
			System.out.println("Mergesort failed!");
	}
}
