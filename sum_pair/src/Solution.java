import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class Solution {

    public int count(int[] a) {
        HashMap<Integer, List<Integer>> map = new HashMap<>();
        int count = 0;
        for (int i = 0; i < a.length; i++) {
            if (map.containsKey(a[i])) {
                List<Integer> info = map.get(a[i]);
                info.add(i);
            } else {
                List<Integer> info = new ArrayList<Integer>();
                info.add(i);
                map.put(a[i], info);
            }
        }
        for (int i = 0; i < a.length; i++) {
            List<Integer> info = map.get(100 - a[i]);
            if (info != null) {
                count += info.size();
                if (info.contains(i)) {
                    count--;
                }
            }
        }
        return count / 2;
    }


    public static void main(String[] args) {
        int[] a = {1, 99};
        System.out.println(new Solution().count(a));
    }
}
