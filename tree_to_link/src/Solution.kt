import java.util.LinkedList

/**
leetcode 114 二叉树展开为链表
给定一个二叉树，原地将它展开为链表。

例如，给定二叉树

1
/ \
2   5
/ \   \
3   4   6
将其展开为：

1
\
2
\
3
\
4
\
5
\
6
 */

class TreeNode(var `val`: Int) {
    var left: TreeNode? = null
    var right: TreeNode? = null
}

class Solution {
    private var tail: TreeNode? = null

    fun flatten(root: TreeNode?): Unit {
        tail = root
        if (root != null) {
            processLeft(root.left)
            processRight(root.right)
        }
        var curr = root
        while (curr != null) {
            curr.right = curr.left
            val temp = curr
            curr = curr.left
            temp.left = null
        }
    }

    private fun processLeft(node: TreeNode?) {
        if (node != null) {
            tail = node
            processLeft(node.left)
            processRight(node.right)
        }
    }

    private fun processRight(node: TreeNode?) {
        if (node != null) {
            if (tail != null) {
                tail!!.left = node
            }
            tail = node
            processLeft(node.left)
            processRight(node.right)
        }
    }

    fun buildTree(elements: Array<Int?>): TreeNode? {
        var root: TreeNode? = null
        var curr: TreeNode? = null
        val iter = elements.iterator()
        val queue = LinkedList<TreeNode>()
        while (iter.hasNext()) {
            if (root == null) {
                val first = iter.next() ?: break
                root = TreeNode(first)
                curr = root
            }
            if (!queue.isEmpty()) {
                curr = queue.removeFirst()
            }
            var left: Int? = if (iter.hasNext()) iter.next() else null
            left?.let {
                curr!!.left = TreeNode(it)
                queue.addLast(curr!!.left)
            }
            var right: Int? = if (iter.hasNext()) iter.next() else null
            right?.let {
                curr!!.right = TreeNode(it)
                queue.addLast(curr!!.right)
            }

        }
        return root
    }

    fun printWide(node: TreeNode?) {
        print("[")
        var curr: TreeNode? = null
        val queue = LinkedList<TreeNode?>()
        queue.addLast(node)
        while (!queue.isEmpty()) {
            curr = queue.removeFirst()
            print("${curr?.`val`}, ")
            if (curr != null && (curr.left != null || curr.right != null)) {
                queue.addLast(curr.left)
                queue.addLast(curr.right)
            }
        }
        print("]")
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            val arr = arrayOf<Int?>(1, 2, 5, 3, 4, null, 6)
            val solution = Solution()
            val tree = solution.buildTree(arr)
            solution.flatten(tree)
            solution.printWide(tree)
        }
    }
}