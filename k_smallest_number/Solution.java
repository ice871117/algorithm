import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {

    public static void main(String[] args) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream("3.in");
            Scanner scanner = new Scanner(fileInputStream.getChannel());
            List<Integer> input = new ArrayList<>();
            while (scanner.hasNextInt()) {
                input.add(scanner.nextInt());
            }
            int k = input.remove(input.size() - 1);
            int[] arr = new int[input.size()];
            for (int i = 0; i < input.size(); i++) {
                arr[i] = input.get(i);
            }
            int[] result = getKSmall(arr, k);
            for (int i = 0; i < k; i++) {
                System.out.print(result[i] + " ");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (Exception e) {

            }
        }
    }

    private static int[] getKSmall(int[] input, int k) {
        int[] result = new int[k];
        if (input.length > 0) {
            for (int i = 0; i < k; i++) {
                result[i] = heapTop(input, input.length - i - 1);
            }
        }
        return result;
    }

    private static int heapTop(int[] a, int end) {
        if (end == 0) {
            return a[0];
        }
        for (int i = (end + 1) / 2 - 1; i >= 0; i--) {
            int rightIndex = i * 2 + 2;
            int leftIndex = i * 2 + 1;
            if (rightIndex <= end && a[i] > a[rightIndex]) {
                swap(a, i, rightIndex);
            }
            if (leftIndex <= end && a[i] > a[leftIndex]) {
                swap(a, i, leftIndex);
            }
        }
        swap(a, 0, end);
        return a[end];
    }

    private static void swap(int[] a, int left, int right) {
        int temp = a[left];
        a[left] = a[right];
        a[right] = temp;
    }
}