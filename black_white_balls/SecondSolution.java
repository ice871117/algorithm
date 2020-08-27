import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class SecondSolution {
    
    public static void main(String[] args) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream("1.in");
            Scanner scanner = new Scanner(fileInputStream.getChannel());
            List<Integer> input = new ArrayList<>();
            while (scanner.hasNextInt()) {
                input.add(scanner.nextInt());
            }
            int m = input.get(0);
            int n = input.get(1);
            int[] a = new int[m + n];
            for (int i = 0; i < m + n; i++) {
                if (i < m) {
                    a[i] = 0;
                } else {
                    a[i] = 1;
                }
            }
            System.out.print(fullsequence(a));
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

    private static int fullsequence(int[] input) {
        int result = 0;
        int len = input.length;
        if (len == 1) {
            return 1;
        }
        Arrays.sort(input);
        while (true) {
            result++;
            int index = -1;
            for (int i = len-2, j = len - 1; i >= 0; j--, i--) {
                if (input[i] < input[j]) {
                    index = i;
                    break;
                }
            }
            if (index < 0) {
                break;
            }
            int k = len - 1;
            for (; k >= 0; k--) {
                if (input[k] > input[index]) {
                    break;
                }
            }
            swap(input, k, index);
            for (int i = index + 1, j = len - 1; i < j; i++, j--) {
                swap(input, i, j);
            }
        }
        return result;
    }

    private static void swap(int[] a, int left, int right) {
        int temp = a[left];
        a[left] = a[right];
        a[right] = temp;
    }
}