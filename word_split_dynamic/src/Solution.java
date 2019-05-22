import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        int len = s.length();
        boolean[] d = new boolean[len + 1];
        HashSet<String> set = new HashSet<>();
        set.addAll(wordDict);
        d[0] = true;
        for (int i = 1; i <= len; i++) {
            for (int j = 0; j < i; j++) {
                if (d[j] && set.contains(s.substring(j, i))) {
                    d[i] = true;
                }
            }
        }
        return d[len];
    }


    public static void main(String[] args) {
        String s = "leetcode";
        String[] ss = {"leet", "code"};
        System.out.print(new Solution().wordBreak(s, Arrays.asList(ss)));
    }

}
