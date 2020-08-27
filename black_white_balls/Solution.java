import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {

    public static void main(String[] args) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream("1.in");
            Scanner scanner = new Scanner(fileInputStream.getChannel());
            List<Integer> input = new ArrayList<>();
            while (scanner.hasNextInt()) {
                input.add(scanner.nextInt());
            }
            long m = input.get(0);
            long n = input.get(1);
            System.out.print(fullsequence(m + n) / (fullsequence(m) * fullsequence(n)));
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

    private static long fullsequence(long a) {
        long result = 1;
        for (long i = a; i >= 1; i--) {
            result *= i;
        }
        return result;
    }
}