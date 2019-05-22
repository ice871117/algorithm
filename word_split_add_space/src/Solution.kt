import java.util.*

class Solution {

    private val record = mutableMapOf<Int, MutableList<Pair<Int,Int>>>()

    fun wordBreak(s: String, wordDict: List<String>): List<String> {
        val dict = mutableSetOf<String>()
        val d = BooleanArray(s.length + 1) {false}
        d[0] = true
        wordDict.forEach { dict.add(it) }
        for (i in 1..s.length) {
            for (j in 0 until i) {
                if (d[j] && dict.contains(s.substring(j, i))) {
                    d[i] = true
                    if (record[j] == null) {
                        record[j] = mutableListOf()
                    }
                    record[j]?.add(Pair(j, i))
                }
            }
        }
        val ret = arrayListOf<String>()
        if (d[d.size - 1]) {
            buildSequence(ret, LinkedList<String>(), s, 0)
        }
        return ret
    }

    private fun buildSequence(ret: ArrayList<String>, list: LinkedList<String>, s: String, start: Int) {
        if (start >= s.length) {
            ret.add(buildResult(list))
            return
        }
        record[start]?.forEach {
            var next = it
            if (next.second <= s.length) {
                list.addLast(s.substring(next.first, next.second))
                buildSequence(ret, list, s, next.second)
                list.removeLast()
            }
        }
    }

    private fun buildResult(list: List<String>): String {
        val sb = StringBuilder()
        list.forEachIndexed { index, s ->
            sb.append(s)
            if (index < list.size - 1) {
                sb.append(" ")
            }
        }
        return sb.toString()
    }


    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val s = "catsanddog"
            val arr = listOf("cat", "cats", "and", "sand", "dog")
            Solution().wordBreak(s, arr).forEach {
                System.out.println(it)
            }
        }
    }

}