/**
leetcode 173 二叉树搜索迭代器
实现一个二叉搜索树迭代器。你将使用二叉搜索树的根节点初始化迭代器。

调用 next() 将返回二叉搜索树中的下一个最小的数。


示例：


BSTIterator iterator = new BSTIterator(root);
iterator.next();    // 返回 3
iterator.next();    // 返回 7
iterator.hasNext(); // 返回 true
iterator.next();    // 返回 9
iterator.hasNext(); // 返回 true
iterator.next();    // 返回 15
iterator.hasNext(); // 返回 true
iterator.next();    // 返回 20
iterator.hasNext(); // 返回 false
 */

class TreeNode(var `val`: Int) {
    var left: TreeNode? = null
    var right: TreeNode? = null
}

class BSTIterator(private val root: TreeNode?) {

    private var leftMost: TreeNode? = null
    private val visited = HashSet<TreeNode>()

    init {
        leftMost = root
        while (leftMost?.left != null) {
            leftMost = leftMost?.left
        }
    }

    /** @return the next smallest number */
    fun next(): Int {
        val old = leftMost
        visited.add(old!!)
        leftMost = findLeftMost(root)
        return old!!.`val`
    }

    /** @return whether we have a next smallest number */
    fun hasNext(): Boolean {
        return leftMost != null
    }

    private fun findLeftMost(curr: TreeNode?): TreeNode? {
        if (curr == null) {
            return null
        }
        val left = findLeftMost(curr.left)
        if (left != null && !visited.contains(left)) {
            return left
        }
        if (!visited.contains(curr)) {
            return curr
        }
        val right = findLeftMost(curr.right)
        if (right != null && !visited.contains(right)) {
            return right
        }
        return null
    }

}