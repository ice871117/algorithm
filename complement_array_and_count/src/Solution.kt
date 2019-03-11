class Solution {

    fun minPatches(nums: IntArray, n: Int): Int {
        var count = 0
        var max = 1L
        var i = 0
        while (max <= n) {
            if (i < nums.size && nums[i] <= max) {
                max += nums[i]
                i++
            } else {
                max *= 2
                count++
            }
        }
        return count
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val nums = intArrayOf(1,2,31,33)
            val n = Integer.MAX_VALUE
            val solution = Solution()
            System.out.println(solution.minPatches(nums, n))
        }
    }

}