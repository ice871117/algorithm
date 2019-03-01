/**
473 火柴拼正方形

还记得童话《卖火柴的小女孩》吗？现在，你知道小女孩有多少根火柴，请找出一种能使用所有火柴拼成一个正方形的方法。不能折断火柴，可以把火柴连接起来，并且每根火柴都要用到。

输入为小女孩拥有火柴的数目，每根火柴用其长度表示。输出即为是否能用所有的火柴拼成正方形。

示例 1:

输入: [1,1,2,2,2]
输出: true

解释: 能拼成一个边长为2的正方形，每边两根火柴。
示例 2:

输入: [3,3,3,3,4]
输出: false

解释: 不能用所有火柴拼成一个正方形。
 */

#include <iostream>
#include <vector>
#include <set>

using namespace std;

template<class T>
int length(T &t) {
    return sizeof(t) / sizeof(t[0]);
}

class Solution {
public:
    bool makesquare(vector<int> &nums) {
        int sum = get_sum(nums);
        if (sum % 4 != 0) {
            return false;
        }
        auto possibilities = get_sum_set(nums, sum / 4);
        return find_nice_divie(nums, possibilities, 0, 1);
    }

private:
    // layer start from 1 to 4
    bool find_nice_divie(const vector<int> &what_left, vector<vector<int>> &remain_collects, int start, int layer) {
        int size = remain_collects.size();
        if (start >= size || size - start < 5 - layer) {
            return false;
        }
        if (layer == 4) {
            for (int i = start; i < size; i++) {
                if (what_left == remain_collects[i]) {
                    return true;
                }
            }
            return false;
        } else {
            auto complement = get_complement_set(what_left, remain_collects[start]);
            return find_nice_divie(complement, remain_collects, start + 1, layer + 1) ||
                   find_nice_divie(what_left, remain_collects, start + 1, layer);
        }
    }

    vector<int> get_complement_set(const vector<int> &origin, vector<int> target) {
        vector<int> result = origin;
        for (auto i = origin.begin(); i != origin.end(); i++) {
            int curr = *i;
            auto index = find(target.begin(), target.end(), curr);
            if (index != target.end()) {
                target.erase(index);
                auto index_2 = find(result.begin(), result.end(), curr);
                if (index_2 != result.end()) {
                    result.erase(index_2);
                }
            }
        }
        return result;
    }

    vector<vector<int>> get_sum_set(vector<int> &nums, const int sum) {
        vector<vector<int>> result;
        vector<pair<const int, vector<int>>> possibilities;
        if (nums.empty()) {
            return result;
        }
        for (auto iter = nums.begin(); iter != nums.end(); iter++) {
            find_any_possibilities(*iter, sum, possibilities);
        }
        for (auto iter = possibilities.begin(); iter != possibilities.end(); iter++) {
            auto curr = *iter;
            if (curr.first == sum) {
                result.emplace_back(curr.second);
            }
        }
        return result;
    }

    void find_any_possibilities(int t, int target, vector<pair<const int, vector<int>>> &possibilities) {
        if (!possibilities.empty()) {
            set<pair<const int, vector<int>>> temp_set;
            int size = possibilities.size();
            for (int i = 0; i < size; i++) {
                int temp_sum = possibilities[i].first + t;
                if (temp_sum > target) {
                    continue;
                }
                vector<int> new_vector = possibilities[i].second;
                new_vector.push_back(t);
                temp_set.insert(pair<int, vector<int>>(temp_sum, new_vector));
            }
            for (auto i = temp_set.begin(); i != temp_set.end(); i++) {
                possibilities.emplace_back(*i);
            }
        }

        vector<int> temp;
        temp.push_back(t);
        possibilities.emplace_back(t, temp);
        return;
    }


    long get_sum(vector<int> &nums) {
        long result = 0;
        for (auto iter = nums.begin(); iter != nums.end(); iter++) {
            result += *iter;
        }
        return result;
    }
};

int main() {
    int a[] = {1,1,2,2,2};
    vector<int> vec(a, a + length(a));
    Solution solution;
    cout << solution.makesquare(vec) << endl;
    return 0;
}