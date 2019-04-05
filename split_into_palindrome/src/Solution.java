import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

class Solution {
    public List<List<String>> partition(String s) {
        List<List<String>> ret = new ArrayList<>();
        findPalindromes(s, 0, "", new LinkedList<>(), ret);
        return ret;
    }

    private void findPalindromes(String s, int start, String cache, LinkedList<String> currCollection, List<List<String>> result) {
        if (start >= s.length()) {
            result.add((List<String>)(currCollection.clone()));
            return;
        }
        String temp = cache + s.charAt(start);
        if (isPalindrome(temp)) {
            currCollection.addLast(temp);
            findPalindromes(s, start + 1, "", currCollection, result);
            currCollection.removeLast();
        }
        if (start < s.length() - 1) {
            findPalindromes(s, start + 1, temp, currCollection, result);
        }
    }

    public boolean isPalindrome(String s) {
        int i = 0;
        int j = s.length() - 1;
        while (i < j) {
            if (!isAlphabetOrNum(s.charAt(i))) {
                i++;
                continue;
            }
            if (!isAlphabetOrNum(s.charAt(j))) {
                j--;
                continue;
            }
            if (!equalsIgnoreCase(s.charAt(i), s.charAt(j))) {
                return false;
            }
            i++;
            j--;
        }
        return true;
    }

    private boolean isAlphabetOrNum(char c) {
        return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9');
    }

    private boolean equalsIgnoreCase(char left, char right) {
        if (left <= 'Z') {
            left += 'a' - 'A';
        }
        if (right <= 'Z') {
            right += 'a' - 'A';
        }
        return left == right;
    }

    public static void main(String[] args) {
        String s = "aab";
        List<List<String>> ret = new Solution().partition(s);
        for (List<String> list : ret) {
            System.out.print("[");
            for (String item : list) {
                System.out.print(item + ",");
            }
            System.out.print("]\n");
        }

    }
}