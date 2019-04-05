/**
leetcode 23 合并k个排序链表
合并 k 个排序链表，返回合并后的排序链表。请分析和描述算法的复杂度。

示例:

输入:
[
1->4->5,
1->3->4,
2->6
]
输出: 1->1->2->3->4->4->5->6
 * Example:
 * var li = ListNode(5)
 * var v = li.`val`
 * Definition for singly-linked list.
 */

class ListNode(var `val`: Int) {
    var next: ListNode? = null
}

class Solution {
    fun mergeKLists(lists: Array<ListNode?>): ListNode? {
        var result: ListNode? = null
        var curr: ListNode? = null
        val comparator = ListComparator()
        if (lists.isNotEmpty()) {
            while (!allEmpty(lists)) {
                lists.sortWith(comparator)
                if (result == null) {
                    result = lists[0]
                    curr = result
                } else {
                    curr?.next = lists[0]
                    curr = curr?.next
                }
                lists[0] = lists[0]?.next
            }
        }
        curr?.next = null
        return result
    }

    private fun allEmpty(lists: Array<ListNode?>): Boolean {
        return lists.all { it == null }
    }

    class ListComparator: Comparator<ListNode?> {
        // leave the null item to the end
        override fun compare(o1: ListNode?, o2: ListNode?): Int {
            if (o1 == null && o2 == null) {
                return 0
            }
            if (o1 == null) {
                return 1
            }
            if (o2 == null) {
                return -1
            }
            return o1!!.`val` - o2!!.`val`
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val a0 = ListNode(1)
            val a1 = ListNode(4)
            val a2 = ListNode(5)
            a0.next = a1
            a1.next = a2

            val b0 = ListNode(1)
            val b1 = ListNode(3)
            val b2 = ListNode(4)
            b0.next = b1
            b1.next = b2

            val c0 = ListNode(2)
            val c1 = ListNode(6)
            c0.next = c1

            var ret = Solution().mergeKLists(arrayOf(a0, b0, c0))
            print("[")
            while (ret != null) {
                print("${ret.`val`},")
                ret = ret.next
            }
            print("]")
        }
    }
}