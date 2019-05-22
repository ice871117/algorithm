import java.util.Arrays;
import java.util.List;

/**
 * leetcode 139 单词拆分
 * 给定一个非空字符串 s 和一个包含非空单词列表的字典 wordDict，判定 s 是否可以被空格拆分为一个或多个在字典中出现的单词。
 * <p>
 * 说明：
 * <p>
 * 拆分时可以重复使用字典中的单词。
 * 你可以假设字典中没有重复的单词。
 * 示例 1：
 * <p>
 * 输入: s = "leetcode", wordDict = ["leet", "code"]
 * 输出: true
 * 解释: 返回 true 因为 "leetcode" 可以被拆分成 "leet code"。
 * 示例 2：
 * <p>
 * 输入: s = "applepenapple", wordDict = ["apple", "pen"]
 * 输出: true
 * 解释: 返回 true 因为 "applepenapple" 可以被拆分成 "apple pen apple"。
 * 注意你可以重复使用字典中的单词。
 * 示例 3：
 * <p>
 * 输入: s = "catsandog", wordDict = ["cats", "dog", "sand", "and", "cat"]
 * 输出: false
 */
class Solution {

    class TrieTree {
        String value = null;
        TrieTree[] children = new TrieTree[26];
    }

    private TrieTree mRoot;
    private boolean[] mFails;

    public boolean wordBreak(String s, List<String> wordDict) {
        mFails = new boolean[s.length()];
        s = s.toLowerCase();
        mRoot = new TrieTree();
        for (String word : wordDict) {
            addToTrieTree(mRoot, word);
        }
        return findRecursively(s, 0);
    }


    private boolean findRecursively(String s, final int start) {
        boolean ret = false;
        if (start >= s.length()) {
            return true;
        }
        if (mFails[start]) return false;
        TrieTree curr = mRoot;
        int i = start;
        while(!ret && (curr = curr.children[s.charAt(i++) - 'a']) != null) {
            if (curr.value != null) {
                ret = findRecursively(s, i);
            }
            if (i > s.length() - 1) {
                break;
            }
        }
        mFails[start] = !ret;
        return ret;
    }

    private void addToTrieTree(TrieTree root, String word) {
        word = word.toLowerCase();
        TrieTree curr = root;
        for (char c : word.toCharArray()) {
            if (curr.children[c - 'a'] == null) {
                curr.children[c - 'a'] = new TrieTree();
            }
            curr = curr.children[c - 'a'];
        }
        if (curr != root) {
            curr.value = word;
        }
    }

    public static void main(String[] args) {
        String s = "leetcode";
        String[] ss = {"leet", "code"};
        System.out.print(new Solution().wordBreak(s, Arrays.asList(ss)));
    }
}