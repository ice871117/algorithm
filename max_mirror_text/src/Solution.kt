/**
409 leetcode 最长回文
给定一个包含大写字母和小写字母的字符串，找到通过这些字母构造成的最长的回文串。

在构造过程中，请注意区分大小写。比如 "Aa" 不能当做一个回文字符串。

注意:
假设字符串的长度不会超过 1010。

示例 1:

输入:
"abccccdd"

输出:
7

解释:
我们可以构造的最长的回文串是"dccaccd", 它的长度是 7。
 */
class Solution {
    fun longestPalindrome(s: String): Int {
        val map = mutableMapOf<Char, Int>()
        val len = s.length
        var count = 0
        for (i in 0 until len) {
            val item = s[i]
            val old = map[item]
            if (old != null) {
                map.remove(item)
                count += 2
            } else {
                map[item] = 1
            }
        }
        return count + if (map.isNotEmpty()) 1 else 0
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val s = "abccccdd"
            val solution = Solution()
            System.out.println(solution.longestPalindrome(s))
        }
    }
}