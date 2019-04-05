import sun.font.TrueTypeFont

/**
leetcode 749 隔离病毒
病毒扩散得很快，现在你的任务是尽可能地通过安装防火墙来隔离病毒。

假设世界由二维矩阵组成，0 表示该区域未感染病毒，而 1 表示该区域已感染病毒。可以在任意 2 个四方向相邻单元之间的共享边界上安装一个防火墙（并且只有一个防火墙）。

每天晚上，病毒会从被感染区域向相邻未感染区域扩散，除非被防火墙隔离。现由于资源有限，每天你只能安装一系列防火墙来隔离其中一个被病毒感染的区域（一个区域或连续的一片区域），且该感染区域对未感染区域的威胁最大且保证唯一。

你需要努力使得最后有部分区域不被病毒感染，如果可以成功，那么返回需要使用的防火墙个数; 如果无法实现，则返回在世界被病毒全部感染时已安装的防火墙个数。



示例 1：

输入: grid =
[[0,1,0,0,0,0,0,1],
[0,1,0,0,0,0,0,1],
[0,0,0,0,0,0,0,1],
[0,0,0,0,0,0,0,0]]
输出: 10
说明:
一共有两块被病毒感染的区域: 从左往右第一块需要 5 个防火墙，同时若该区域不隔离，晚上将感染 5 个未感染区域（即被威胁的未感染区域个数为 5）;
第二块需要 4 个防火墙，同理被威胁的未感染区域个数是 4。因此，第一天先隔离左边的感染区域，经过一晚后，病毒传播后世界如下:
[[0,1,0,0,0,0,1,1],
[0,1,0,0,0,0,1,1],
[0,0,0,0,0,0,1,1],
[0,0,0,0,0,0,0,1]]
第二题，只剩下一块未隔离的被感染的连续区域，此时需要安装 5 个防火墙，且安装完毕后病毒隔离任务完成。
示例 2：

输入: grid =
[[1,1,1],
[1,0,1],
[1,1,1]]
输出: 4
说明:
此时只需要安装 4 面防火墙，就有一小区域可以幸存，不被病毒感染。
注意不需要在世界边界建立防火墙。


示例 3:

输入: grid =
[[1,1,1,0,0,0,0,0,0],
[1,0,1,0,1,1,1,1,1],
[1,1,1,0,0,0,0,0,0]]
输出: 13
说明:
在隔离右边感染区域后，隔离左边病毒区域只需要 2 个防火墙了。


说明:

grid 的行数和列数范围是 [1, 50]。
grid[i][j] 只包含 0 或 1 。
题目保证每次选取感染区域进行隔离时，一定存在唯一一个对未感染区域的威胁最大的区域。

 */
class Solution {

    private var w: Int = 0
    private var h: Int = 0
    private lateinit var grid: Array<IntArray>
    private val virusSetList = mutableListOf<AdjSet>()
    private var fenceMap = FenceSet()

    fun containVirus(grid: Array<IntArray>): Int {
        this.grid = grid
        h = grid.size
        w = grid[0].size
        var sumMax = 0
        do {
            updateVirusSets()
            val max = putFence(virusSetList)
            if (max <= 0) {
                break
            }
            contamination(virusSetList)
            sumMax += max
        } while (true)

        return sumMax
    }

    private fun updateVirusSets() {
        virusSetList.clear()
        for (i in 0 until h) {
            for (j in 0 until w) {
                val item = grid[i][j]
                if (item == VIRUS) {
                    addToCoordSet(Coord(j, i), virusSetList, true)
                }
            }
        }
    }

    private fun putFence(setList: List<AdjSet>): Int {
        var max = 0
        var maxFenceCopy: FenceSet? = null
        setList.forEach {
            val copy = fenceMap.clone() as FenceSet
            val temp = doPutFence(it, copy)
            if (temp > max) {
                max = temp
                maxFenceCopy = copy
            }
        }
        if (maxFenceCopy != null) {
            fenceMap = maxFenceCopy!!
        }
        return max
    }

    private fun doPutFence(coordSet: AdjSet, fenceSet: FenceSet): Int {
        val old = fenceSet.size
        val fenceCoordList = mutableListOf<AdjSet>()
        for (item in coordSet) {
            item.getAdjacentCoord(w, h).filter {
                grid[it.y][it.x] == CLEAN && !fenceSet.existBetween(item, it)
            }.forEach { adjCoord ->
                val previous = addToCoordSet(adjCoord, fenceCoordList)
                if (previous != null) {
                    previous.addFence(item)
                } else {
                    adjCoord.addFence(item)
                }
            }
        }
        for (item in coordSet) {
            item.getVirtualCoord(w, h).filter {
                grid[it.y][it.x] == CLEAN
            }.forEach { virtualCoord ->
                addToCoordSet(virtualCoord, fenceCoordList)
            }
        }
        mergeBoarderSet(fenceCoordList)
        var max = 0
        var ret: AdjSet? = null
        for (item in fenceCoordList) {
            val temp = item.fenceCount()
            if (temp > max) {
                max = temp
                ret = item
            }
        }
        if (ret != null) {
            fenceSet.addAll(ret.getAllFences())
        }
        return fenceSet.size - old
    }

    private fun mergeBoarderSet(setList: MutableList<AdjSet>) {
        if (setList.size > 1) {
            val borders = mutableListOf<Int>()
            for (i in 0 until setList.size) {
                if (setList[i].any { it.atBorder(w, h) }) {
                    borders.add(i)
                }
            }
            if (borders.size > 1) {
                for (i in 1 until borders.size) {
                    setList[borders[0]].addAll(setList[borders[i]])
                    setList[borders[i]].drop()
                }
                val iter = setList.iterator()
                while (iter.hasNext()) {
                    if (iter.next().isDropped()) {
                        iter.remove()
                    }
                }
            }
        }
    }

    private fun contamination(setList: List<AdjSet>) {
        setList.flatten().forEach { coord ->
            coord.getAdjacentCoord(w, h).filter {
                !fenceMap.existBetween(coord, it)
            }.forEach { adjCoord ->
                grid[adjCoord.y][adjCoord.x] = VIRUS
            }
        }
    }

    /**
     * 把一个Coord添加到Adjset的列表中，如果和已有的所有set无交集，则新建一个set
     * 如果添加过程中，两个set连接在一起，则进行合并
     */
    private fun addToCoordSet(coord: Coord, coordSets: MutableList<AdjSet>, considerFence: Boolean = false): Coord? {
        var adjacents = coord.getAdjacentCoord(w, h).plus(coord)
        if (considerFence) {
            adjacents = adjacents.filter { !fenceMap.existBetween(coord, it) }
        }
        var added: AdjSet? = null
        var duplicateIndex = -1
        var previous: Coord? = null
        for (set in coordSets) {
            for (ad in adjacents) {
                if (set.contains(ad)) {
                    previous = set.add(coord)
                    duplicateIndex = duplicateIndex(coord, set, coordSets)
                    added = set
                    break
                }
            }
        }
        if (added == null) {
            val temp = AdjSet()
            temp.add(coord)
            coordSets.add(temp)
        } else if (duplicateIndex >= 0) {
            added.addAll(coordSets[duplicateIndex])
            coordSets.removeAt(duplicateIndex)
        }
        return previous
    }

    /**
     * 找到在其他set中是否存在目标coord，如果有则返回这个set在list中的index
     */
    private fun duplicateIndex(target: Coord, exclude: AdjSet, setList: List<AdjSet>): Int {
        for ((index, item) in setList.withIndex()) {
            if (item == exclude) {
                continue
            }
            if (item.contains(target)) {
                return index
            }
        }
        return -1
    }

    class FenceSet : HashSet<Fence>() {
        fun existBetween(c1: Coord, c2: Coord) = contains(Fence(c1, c2))
    }

    class AdjSet : Iterable<Coord> {

        val innerSet = HashMap<Coord, Coord>()
        var left: Int? = null
        var right: Int? = null
        var top: Int? = null
        var bottom: Int? = null
        var drop = false

        fun drop() {
            drop = true
        }

        fun isDropped() = drop

        fun add(element: Coord): Coord? {
            if (left == null) {
                left = element.x
            } else if (element.x < left!!) {
                left = element.x
            }
            if (right == null) {
                right = element.x
            } else if (element.x > right!!) {
                right = element.x
            }
            if (top == null) {
                top = element.y
            } else if (element.y < top!!) {
                top = element.y
            }
            if (bottom == null) {
                bottom = element.y
            } else if (element.y > bottom!!) {
                bottom = element.y
            }
            if (innerSet.containsKey(element)) {
                return innerSet[element]
            }
            innerSet[element] = element
            return null
        }

        override fun iterator(): Iterator<Coord> {
            return innerSet.map { it.value }.iterator()
        }

        fun contains(c: Coord): Boolean {
            return innerSet.contains(c)
        }

        fun addAll(c: AdjSet) {
            c.forEach {
                add(it)
            }
        }

        fun addAll(c: Collection<Coord>) {
            c.forEach {
                innerSet[it] = it
            }
        }

        fun fenceCount(): Int {
            var sum = 0
            innerSet.forEach { _, value ->
                sum += value.getFenceCount()
            }
            return sum
        }

        fun getAllFences() = innerSet.flatMap { it.value.getFenceSet() }

    }

    class Fence(private val c1: Coord, private val c2: Coord) {

        override fun hashCode(): Int {
            return c1.hashCode() + c2.hashCode()
        }

        override fun equals(other: Any?): Boolean {
            if (other === this) {
                return true
            }
            if (other !is Fence) {
                return false
            }
            return (c1 == other.c1 && c2 == other.c2) || (c1 == other.c2 && c2 == other.c1)
        }
    }

    /**
     * x means horizontal index, y means vertical index
     */
    class Coord(val x: Int, val y: Int) {

        private val fences = FenceSet()

        fun getAdjacentCoord(w: Int, h: Int): List<Coord> {
            val ret = mutableListOf<Coord>()
            if (x - 1 >= 0) {
                ret.add(Coord(x - 1, y))
            }
            if (y - 1 >= 0) {
                ret.add(Coord(x, y - 1))
            }
            if (x + 1 < w) {
                ret.add(Coord(x + 1, y))
            }
            if (y + 1 < h) {
                ret.add(Coord(x, y + 1))
            }
            return ret
        }

        fun getVirtualCoord(w: Int, h: Int): List<Coord> {
            val ret = mutableListOf<Coord>()
            if (x - 1 >= 0 && y - 1 >= 0) {
                ret.add(Coord(x - 1, y - 1))
            }
            if (x - 1 >= 0 && y + 1 < h) {
                ret.add(Coord(x - 1, y + 1))
            }
            if (x + 1 < w && y - 1 >= 0) {
                ret.add(Coord(x + 1, y - 1))
            }
            if (x + 1 < w && y + 1 < h) {
                ret.add(Coord(x + 1, y + 1))
            }
            return ret
        }

        fun atBorder(w: Int, h: Int): Boolean {
            return x == 0 || y == 0 || x == w - 1 || y == h - 1
        }

        fun addFence(start: Coord) {
            fences.add(Fence(start, this))
        }

        fun getFenceSet() = fences

        fun getFenceCount() = fences.size

        override fun hashCode(): Int {
            var hashCode = 1
            hashCode = 31 * hashCode + x
            hashCode = 31 * hashCode + y
            return hashCode
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) {
                return true
            }
            if (other !is Coord) {
                return false
            }
            return this.x == other.x && this.y == other.y
        }
    }

    companion object {

        const val CLEAN = 0 // clean area
        const val VIRUS = 1 // virus area

        @JvmStatic
        fun main(args: Array<String>) {
//            val arr = arrayOf(
//                intArrayOf(1, 1, 1),
//                intArrayOf(1, 0, 1),
//                intArrayOf(1, 1, 1)
//            )
//            val arr = arrayOf(
//                intArrayOf(0, 1, 0, 0, 0, 0, 0, 1),
//                intArrayOf(0, 1, 0, 1, 0, 0, 0, 1),
//                intArrayOf(0, 0, 0, 0, 0, 0, 0, 1)
//            )
            val arr = arrayOf(
                intArrayOf(0, 1, 0, 0, 0, 0, 0, 1),
                intArrayOf(0, 1, 0, 0, 0, 0, 0, 1),
                intArrayOf(0, 0, 0, 0, 0, 0, 0, 1),
                intArrayOf(0, 0, 0, 0, 0, 0, 0, 0)
            )
//            val arr = arrayOf(
//                intArrayOf(1,1,1,0,0,0,0,0,0),
//                intArrayOf(1,0,1,0,1,1,1,1,1),
//                intArrayOf(1,1,1,0,0,0,0,0,0)
//            )
            print(Solution().containVirus(arr))
        }
    }
}