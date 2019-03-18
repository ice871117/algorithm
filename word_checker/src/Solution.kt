/**
leetcode 966 元音拼写检查器

在给定单词列表 wordlist 的情况下，我们希望实现一个拼写检查器，将查询单词转换为正确的单词。

对于给定的查询单词 query，拼写检查器将会处理两类拼写错误：

大小写：如果查询匹配单词列表中的某个单词（不区分大小写），则返回的正确单词与单词列表中的大小写相同。
例如：wordlist = ["yellow"], query = "YellOw": correct = "yellow"
例如：wordlist = ["Yellow"], query = "yellow": correct = "Yellow"
例如：wordlist = ["yellow"], query = "yellow": correct = "yellow"
元音错误：如果在将查询单词中的元音（‘a’、‘e’、‘i’、‘o’、‘u’）分别替换为任何元音后，能与单词列表中的单词匹配（不区分大小写），则返回的正确单词与单词列表中的匹配项大小写相同。
例如：wordlist = ["YellOw"], query = "yollow": correct = "YellOw"
例如：wordlist = ["YellOw"], query = "yeellow": correct = "" （无匹配项）
例如：wordlist = ["YellOw"], query = "yllw": correct = "" （无匹配项）
此外，拼写检查器还按照以下优先级规则操作：

当查询完全匹配单词列表中的某个单词（区分大小写）时，应返回相同的单词。
当查询匹配到大小写问题的单词时，您应该返回单词列表中的第一个这样的匹配项。
当查询匹配到元音错误的单词时，您应该返回单词列表中的第一个这样的匹配项。
如果该查询在单词列表中没有匹配项，则应返回空字符串。
给出一些查询 queries，返回一个单词答案列表 answer，其中 answer[i] 是由查询 query = queries[i] 得到的正确单词。



示例：

输入：wordlist = ["KiTe","kite","hare","Hare"], queries = ["kite","Kite","KiTe","Hare","HARE","Hear","hear","keti","keet","keto"]
输出：["kite","KiTe","KiTe","Hare","hare","","","KiTe","","KiTe"]
 *
 */

class Solution {

    class PrieNode {
        val children: Array<PrieNode?> = Array(27) { i -> null }
        val values = mutableListOf<String>()
    }

    fun spellchecker(wordlist: Array<String>, queries: Array<String>): Array<String> {
        val ret = mutableListOf<String>()
        val root = PrieNode()
        for (word in wordlist) {
            addToTree(root, word)
        }
        for (word in queries) {
            val node = findInTree(root, word, 0)
            if (node == null) {
                ret.add("")
            } else {
                var found = 0
                var caseIgnore: String? = null
                for (item in node.values) {
                    if (word == item) {
                        ret.add(item)
                        found = 1
                        break
                    } else if (found != -1 && word.equals(item, true)) {
                        caseIgnore = item
                        found = -1
                    }
                }
                if (found == -1) {
                    ret.add(caseIgnore!!)
                } else if (found == 0) {
                    ret.add(node.values.first())
                }
            }

        }
        return ret.toTypedArray()
    }

    private fun isVowel(c: Char) = VOWEL.any { item -> c == item }

    private inline fun alphabetIndex(c: Char): Int {
        return if (c >= 'a') {
            c - 'a'
        } else {
            c - 'A'
        }
    }

    private fun findInTree(node: PrieNode?, word: String, index: Int): PrieNode? {
        if (node == null) {
            return null
        }
        val last = index === word.length - 1
        val c = word[index]
        val alphabetIndex = alphabetIndex(c)
        return if (isVowel(c)) {
            if (last) {
                node.children[26]
            } else findInTree(node.children[26], word, index + 1)
        } else {
            if (last)
                node.children[alphabetIndex]
            else
                findInTree(node.children[alphabetIndex], word, index + 1)
        }
    }

    private fun addToTree(root: PrieNode, str: String) {
        var curr = root
        for (c in str) {
            val vowel = isVowel(c)
            if (vowel) {
                if (curr.children[26] == null) {
                    curr.children[26] = PrieNode()
                }
                curr = curr.children[26]!!
            } else {
                val alphabetIndex = alphabetIndex(c)
                if (curr.children[alphabetIndex] == null) {
                    curr.children[alphabetIndex] = PrieNode()
                }
                curr = curr.children[alphabetIndex]!!
            }
        }
        curr.values.add(str)
    }


    companion object {

        private const val VOWEL: String = "aeiouAEIOU"

        @JvmStatic
        fun main(args: Array<String>) {
            val words = arrayOf("KiTe","kite","hare","Hare")
            val queries = arrayOf("kite","Kite","KiTe","Hare","HARE","Hear","hear","keti","keet","keto")
            System.out.print("[")
            Solution().spellchecker(words, queries).forEach { it -> System.out.print("$it,") }
            System.out.print("]")
        }
    }
}