/**
 *
leetcode 60 第k个排列

给出集合 [1,2,3,…,n]，其所有元素共有 n! 种排列。

按大小顺序列出所有排列情况，并一一标记，当 n = 3 时, 所有排列如下：

"123"
"132"
"213"
"231"
"312"
"321"
给定 n 和 k，返回第 k 个排列。

说明：

给定 n 的范围是 [1, 9]。
给定 k 的范围是[1,  n!]。
示例 1:

输入: n = 3, k = 3
输出: "213"
示例 2:

输入: n = 4, k = 9
输出: "2314"
 */

#include <iostream>
#include <string>
#include <vector>
#include <sstream>

using namespace std;


class Solution {
public:
    string getPermutation(int n, int k) {
        if (n <= 1) {
            return "1";
        }
        vector<int> a;
        for (int i = 1; i <= n; i++) {
            a.push_back(i);
        }

        for (int i = 0; i < k - 1; i++) {
            print(a);
            int index = 0;
            int j = n;
            for (j = n - 2; j >= 0; j--) {
                if (a[j] < a[j + 1]) {
                    index = j;
                    break;
                }
                if (j == 0) {
                    goto end;
                }
            }
            for (j = n - 1; j >= 0; j--) {
                if (a[j] > a[index]) {
                    swap(a[j], a[index]);
                    break;
                }
            }
            reverse(a, index + 1);
        }
        end:
        stringstream ss;
        for (auto iter = a.begin(); iter != a.end(); iter++) {
            ss << *iter;
        }
        return ss.str();
    }
private:
    void print(vector<int> &vec) {
        cout << "calc:";
        for (auto iter = vec.begin(); iter != vec.end(); iter++) {
            cout << *iter;
        }
        cout << endl;
    }

    void reverse(vector<int> &vec, int index) {
        int k = vec.size() -1;
        while(index < k) {
            swap(vec[index], vec[k]);
            index++;
            k--;
        }
    }
};


int main() {
    Solution solution;
    cout << solution.getPermutation(4, 9) << endl;
    return 0;
}