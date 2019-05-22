/**
给定一个二维网格 board 和一个字典中的单词列表 words，找出所有同时在二维网格和字典中出现的单词。

单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母在一个单词中不允许被重复使用。

示例:

输入:
words = ["oath","pea","eat","rain"] and board =
[
['o','a','a','n'],
['e','t','a','e'],
['i','h','k','r'],
['i','f','l','v']
]

输出: ["eat","oath"]
说明:
你可以假设所有输入都由小写字母 a-z 组成
 */
class Solution {

    private val tree = Trie()
    private var columns: Int = 0
    private var rows: Int = 0
    private val ret = HashSet<String>()

    class Trie {

        /** Initialize your data structure here. */
        var value: String? = null
        val children = Array<Trie?>(26) { null }

        /** Inserts a word into the trie. */
        fun insert(word: String) {
            var curr = this
            for (c in word.toCharArray()) {
                if (curr.children[c - 'a'] == null) {
                    curr.children[c - 'a'] = Trie()
                }
                curr = curr.children[c - 'a']!!
            }
            curr.value = word
        }

        /** Returns if the word is in the trie. */
        fun search(word: String): Boolean {
            var curr = this
            for (c in word.toCharArray()) {
                if (curr.children[c - 'a'] == null) {
                    return false
                }
                curr = curr.children[c - 'a']!!
            }
            return curr.value != null
        }

        /** Returns if there is any word in the trie that starts with the given prefix. */
        fun startsWith(prefix: String): Boolean {
            var curr = this
            for (c in prefix.toCharArray()) {
                if (curr.children[c - 'a'] == null) {
                    return false
                }
                curr = curr.children[c - 'a']!!
            }
            return true
        }

    }

    private fun getChildTree(curr: Trie, c: Char): Trie? {
        return curr.children[c - 'a']
    }

    fun findWords(board: Array<CharArray>, words: Array<String>): List<String> {
        for (item in words) {
            tree.insert(item)
        }
        columns = board[0].size
        rows = board.size
        for (i in 0 until rows) {
            for (j in 0 until columns) {
                doFind(tree, mutableListOf(), board, i, j, HashSet())
            }
        }
        return ret.toList()
    }

    private fun doFind(currTree: Trie, temp: MutableList<Char>, board: Array<CharArray>, row: Int, col: Int, path: MutableSet<Pair<Int, Int>>) {
        if (row < 0 || col < 0 || row >= rows || col >= columns) {
            return
        }
        val currChar = board[row][col]
        val childTree = getChildTree(currTree, currChar)
        if (childTree != null) {
            temp.add(currChar)
            if (childTree.value != null) {
                ret.add(makeString(temp))
            }
            path.add(Pair(row, col))
            // find bottom
            if (!inPath(path, row + 1, col)) {
                doFind(childTree, temp, board, row + 1, col, path)
            }
            // find top
            if (!inPath(path, row - 1, col)) {
                doFind(childTree, temp, board, row - 1, col, path)
            }
            // find left
            if (!inPath(path, row, col - 1)) {
                doFind(childTree, temp, board, row, col - 1, path)
            }
            // find right
            if (!inPath(path, row, col + 1)) {
                doFind(childTree, temp, board, row, col + 1, path)
            }
            temp.removeAt(temp.size - 1)
            path.remove(Pair(row, col))
        }
    }

    private fun inPath(path: Set<Pair<Int, Int>>, row: Int, col: Int): Boolean {
        return path.contains(Pair(row, col))
    }

    private fun makeString(chars: List<Char>): String {
        val sb = StringBuilder()
        for (c in chars) {
            sb.append(c)
        }
        return sb.toString()
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            val words = arrayOf("oath","pea","eat","rain")
            val board = arrayOf(
                charArrayOf('o', 'a', 'a', 'n'),
                charArrayOf('e', 't', 'a', 'e'),
                charArrayOf('i', 'h', 'k', 'r'),
                charArrayOf('i', 'f', 'l', 'v'))
            for (item in Solution().findWords(board, words)) {
                println(item)
            }
        }
    }
}

