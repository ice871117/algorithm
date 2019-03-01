/**
 *
 * leetcode 416 分割等和子集
 *
 * 给定一个只包含正整数的非空数组。是否可以将这个数组分割成两个子集，使得两个子集的元素和相等。
 *
 * 注意:
 *
 * 每个数组中的元素不会超过 100
 * 数组的大小不会超过 200
 * 示例 1:
 *
 * 输入: [1, 5, 11, 5]
 *
 * 输出: true
 *
 * 解释: 数组可以分割成 [1, 5, 5] 和 [11].
 *
 *
 * 示例 2:
 *
 * 输入: [1, 2, 3, 5]
 *
 * 输出: false
 *
 * 解释: 数组不能分割成两个元素和相等的子集.
 * */

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
    bool canPartition(vector<int> &nums) {
        if (pre_check(nums)) {
            return true;
        }
        long sum = get_sum(nums);
        if (sum % 2 != 0) {
            return false;
        }
        for (auto iter = nums.begin(); iter != nums.end(); iter++) {
            if (find_any_possibilities(*iter, sum / 2)) {
                return true;
            }
        }
        return false;
    }

private:
    set<int> possibilities;

    bool find_any_possibilities(int t, int target) {
        if (possibilities.empty()) {
            possibilities.insert(t);
            return t == target;
        }
        set<int> temp_set;
        for (auto i = possibilities.begin(); i != possibilities.end(); i++) {
            int temp = *i + t;
            if(temp == target) {
                return true;
            } else if (temp < target) {
                temp_set.insert(temp);
            }
        }
        for (auto i = temp_set.begin(); i != temp_set.end(); i++) {
            possibilities.insert(*i);
        }
        return false;
    }


    long get_sum(vector<int> &nums) {
        long result = 0;
        for (auto iter = nums.begin(); iter != nums.end(); iter++) {
            result += *iter;
        }
        return result;
    }

    bool pre_check(vector<int> &nums) {
        if (nums.empty() || nums.size() % 2 == 1) {
            return false;
        }
        bool all_equal = true;
        int first = *nums.begin();
        for (auto iter = nums.begin() + 1; iter != nums.end(); iter++) {
            if (*iter != first) {
                all_equal = false;
                break;
            }
        }
        return all_equal;
    }
};


class Solution_poor {
public:
    bool canPartition(vector<int> &nums) {
        long sum = get_sum(nums);
        if (sum % 2 != 0) {
            return false;
        }
        return find_sub_set_with_sum(nums, sum / 2);
    }

private:

    // make a copy
    bool find_sub_set_with_sum(vector<int> nums, long sum) {
        if (sum <= 0 && !nums.empty()) {
            return false;
        }
        if (nums.size() == 1) {
            return nums.front() == sum;
        }
        int pop = nums.back();
        nums.pop_back();
        bool expect1 = find_sub_set_with_sum(nums, sum);
        bool expect2 = find_sub_set_with_sum(nums, sum - pop);
        return (expect1 || expect2);
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
    int a[] = {2, 2, 3, 5};
    Solution solution;
    vector<int> temp(a, a + length(a));
    cout << solution.canPartition(temp) << endl;
    return 0;
}