/**
 *
leetcode 746 最小花费楼梯

数组的每个索引做为一个阶梯，第 i个阶梯对应着一个非负数的体力花费值 cost[i](索引从0开始)。

每当你爬上一个阶梯你都要花费对应的体力花费值，然后你可以选择继续爬一个阶梯或者爬两个阶梯。

您需要找到达到楼层顶部的最低花费。在开始时，你可以选择从索引为 0 或 1 的元素作为初始阶梯。

示例 1:

输入: cost = [10, 15, 20]
输出: 15
解释: 最低花费是从cost[1]开始，然后走两步即可到阶梯顶，一共花费15。
 示例 2:

输入: cost = [1, 100, 1, 1, 1, 100, 1, 1, 100, 1]
输出: 6
解释: 最低花费方式是从cost[0]开始，逐个经过那些1，跳过cost[3]，一共花费6。
 */


#include <iostream>
#include <vector>
#include <map>

using namespace std;

template<class T>
int length(T &t) {
    return sizeof(t) / sizeof(t[0]);
}

class Solution {
public:
    int minCostClimbingStairs(vector<int> &cost) {
        if (cost.empty()) {
            return 0;
        }
        return get_min_cost(cost, 0);
    }

private:
    map<int, int> cache;

    int get_min_cost(vector<int> &cost, const int start) {
        if (start >= cost.size()) {
            return 0;
        }
        auto look_up_ret = cache.find(start);
        if (look_up_ret != cache.end()) {
            return (*look_up_ret).second;
        }
        int curr = cost[start];
        int next = start < cost.size() - 1 ? cost[start + 1] : 0;
        int result1 = get_min_cost(cost, start + 1) + curr;
        int result2 = get_min_cost(cost, start + 2) + next;
        int min_ret = min(result1, result2);
        cache[start] = min_ret;
        return min_ret;
    }
};

int main() {
    int a[] = {1, 100, 1, 1, 1, 100, 1, 1, 100, 1};
    vector<int> vec(a, a + length(a));
    Solution solution;
    cout << solution.minCostClimbingStairs(vec) << endl;
    return 0;
}