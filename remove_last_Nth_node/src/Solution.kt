// Definition for singly-linked list.
class ListNode(var `val`: Int) {
    var next: ListNode? = null
}

class Solution {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val head = ListNode(1)
            var curr: ListNode = head
            for (i in 2 .. 5) {
                curr.next = ListNode(i)
                curr = curr.next!!
            }
            var res = Solution().removeNthFromEnd(head, 2)
            while (res != null) {
                print("${res.`val`},")
                res = res.next
            }
        }
    }

    fun removeNthFromEnd(head: ListNode?, n: Int): ListNode? {
        var aheadRemove: ListNode? = null
        var toRemove = head
        var prev = head
        var localN = n
        while(prev != null && localN > 0) {
            prev = prev.next
            localN -= 1
        }
        while(prev != null) {
            prev = prev.next
            aheadRemove = toRemove
            toRemove = toRemove?.next
        }
        return if (aheadRemove == null) {
            toRemove?.next
        } else {
            aheadRemove.next = toRemove?.next
            head
        }
    }
}