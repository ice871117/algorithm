import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.atomic.AtomicInteger;

public class Solution {

    public static void main(String[] args) {
        int[] nums = {-1, 0, 1};
        List<List<Integer>> result = threeSum(nums);
        System.out.println("[");
        for (List list : result) {
            System.out.println(list);
        }
        System.out.println("]");
    }

    private static class MyComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o1 - o2;
        }
    }

    private static final AtomicInteger inc = new AtomicInteger(0);
    private static boolean debug = false;

    private static void increase() {
        if (debug) {
            System.out.println("+ inc: " + inc.incrementAndGet());
        } else {
            inc.incrementAndGet();
        }
    }

    private static void decrease() {
        if (debug) {
            System.out.println("- inc: " + inc.decrementAndGet());
        } else {
            inc.decrementAndGet();
        }
    }

    private static class CalcTask extends ForkJoinTask<Object> {
        private int start;
        private int end;
        private int[] nums;
        private ConcurrentLinkedQueue<List<Integer>> result;
        private static HashSet<String> visted = new HashSet<>();

        public CalcTask(int start, int end, int[] nums, ConcurrentLinkedQueue<List<Integer>> result) {
            this.start = start;
            this.end = end;
            this.nums = nums;
            this.result = result;
        }

        @Override
        protected void setRawResult(Object value) {

        }

        @Override
        public Object getRawResult() {
            return null;
        }

        @Override
        protected boolean exec() {
            boolean working = true;
            while (start < end + 1 && working) {
                int startValue = nums[start];
                int endValue = nums[end];
                int left = 0 - startValue - endValue;
                List<Integer> ret = new ArrayList<>();
                if (find(nums, start + 1, end - 1, left) > 0) {
                    ret.add(startValue);
                    ret.add(left);
                    ret.add(endValue);
                    String pair = formatPair(startValue, left, endValue);
                    if (!visted.contains(pair)) {
                        result.add(ret);
                        visted.add(pair);
                    }
                    new CalcTask( start + 1, end, nums, result).fork();
                    increase();
                    new CalcTask(start, end - 1, nums, result).fork();
                    increase();
                    working = false;
                } else if (startValue + endValue > 0) {
                    end--;
                } else if (startValue + endValue < 0) {
                    start++;
                } else {
                    new CalcTask( start + 1, end, nums, result).fork();
                    increase();
                    new CalcTask(start, end - 1, nums, result).fork();
                    increase();
                    working = false;
                }
            }
            decrease();
            return true;
        }

    }

    private static String formatPair(int first, int second, int third) {
        return first + "_" + second + "_" + third;
    }

    public static List<List<Integer>> threeSum(int[] nums) {
        ForkJoinPool pool = new ForkJoinPool(30);
        ConcurrentLinkedQueue<List<Integer>> tempQueue = new ConcurrentLinkedQueue<>();
        Arrays.sort(nums);
        int start = 0;
        int end = nums.length - 1;

        pool.invoke(new CalcTask(start, end, nums, tempQueue));
        increase();
        for (;;) {
            if (inc.get() == 0) {
                break;
            }
        }
        pool.shutdownNow();
        //HashSet<List<Integer>> set = new HashSet<List<Integer>>();
        //Iterator<List<Integer>> iter = result.iterator();
        //MyComparator c = new MyComparator();
        //while (iter.hasNext()) {
        //    List next = iter.next();
        //    //Collections.sort(next, c);
        //    if (!set.contains(next)) {
        //        set.add(next);
        //    } else {
        //        iter.remove();
        //    }
        //}
        return new ArrayList<List<Integer>>(tempQueue);
    }

    private static int find(int[] nums, int start, int end, int target) {
        int low = start;
        int high = end;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            int cmp = nums[mid] - target;

            if (cmp < 0)
                low = mid + 1;
            else if (cmp > 0)
                high = mid - 1;
            else
                return mid; // key found
        }
        return -(low + 1);  // key not found
    }

}