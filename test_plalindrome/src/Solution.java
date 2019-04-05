class Solution {
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
        String target = "A man, a plan, a canal: Panama";
        System.out.println(new Solution().isPalindrome(target));
    }
}