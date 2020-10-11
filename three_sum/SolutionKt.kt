import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.ForkJoinPool
import java.util.concurrent.ForkJoinTask
import java.util.concurrent.atomic.AtomicInteger

class Solution {

    companion object {
        @JvmStatic
        public fun main(args: Array<String>) {
            val nums = intArrayOf(-1, 0, 1)
            val result = MySolution.threeSum(nums)
            println("[")
            for (list in result) {
                println(list)
            }
            println("]")
        }
    }


    object MySolution {
        private val inc = AtomicInteger(0)
        private val debug = false
        private fun increase() {
            if (debug) {
                println("+ inc: " + inc.incrementAndGet())
            } else {
                inc.incrementAndGet()
            }
        }

        private fun decrease() {
            if (debug) {
                println("- inc: " + inc.decrementAndGet())
            } else {
                inc.decrementAndGet()
            }
        }

        private fun formatPair(first: Int, second: Int, third: Int): String {
            return first.toString() + "_" + second + "_" + third
        }

        fun threeSum(nums: IntArray): List<List<Int>> {
            val pool = ForkJoinPool(30)
            val tempQueue = ConcurrentLinkedQueue<List<Int>>()
            Arrays.sort(nums)
            val start = 0
            val end = nums.size - 1
            pool.invoke<Any>(CalcTask(start, end, nums, tempQueue))
            increase()
            while (true) {
                if (inc.get() == 0) {
                    break
                }
            }
            pool.shutdownNow()
            //HashSet<List<Integer>> set = new HashSet<List<Integer>>();
//Iterator<List<Integer>> iter = result.iterator();
//MyComparator c = new MyComparator();
//while (iter.hasNext()) {
//    List next = iter.next();
//    //Collections.sort(next, c);
//    if (!set.contains(next)) {
//        set.add(next);
//    } else {
//        iter.remove();
//    }
//}
            return ArrayList(tempQueue)
        }

        private fun find(nums: IntArray, start: Int, end: Int, target: Int): Int {
            var low = start
            var high = end
            while (low <= high) {
                val mid = low + high ushr 1
                val cmp = nums[mid] - target
                if (cmp < 0) low = mid + 1 else if (cmp > 0) high = mid - 1 else return mid // key found
            }
            return -(low + 1) // key not found
        }


        private class CalcTask(private var start: Int, private var end: Int, private val nums: IntArray, private val result: ConcurrentLinkedQueue<List<Int>>) : ForkJoinTask<Any?>() {

            protected override fun setRawResult(value: Any?) {}

            override fun getRawResult(): Any? {
                return null
            }

            override fun exec(): Boolean {
                var working = true
                while (start < end + 1 && working) {
                    val startValue = nums[start]
                    val endValue = nums[end]
                    val left = 0 - startValue - endValue
                    val ret: MutableList<Int> = ArrayList()
                    if (find(nums, start + 1, end - 1, left) > 0) {
                        ret.add(startValue)
                        ret.add(left)
                        ret.add(endValue)
                        val pair = formatPair(startValue, left, endValue)
                        if (!visted.contains(pair)) {
                            result.add(ret)
                            visted.add(pair)
                        }
                        CalcTask(start + 1, end, nums, result).fork()
                        increase()
                        CalcTask(start, end - 1, nums, result).fork()
                        increase()
                        working = false
                    } else if (startValue + endValue > 0) {
                        end--
                    } else if (startValue + endValue < 0) {
                        start++
                    } else {
                        CalcTask(start + 1, end, nums, result).fork()
                        increase()
                        CalcTask(start, end - 1, nums, result).fork()
                        increase()
                        working = false
                    }
                }
                decrease()
                return true
            }

            companion object {
                private val visted = HashSet<String>()
            }

        }
    }


}