/***
给定两个字符串 s1 和 s2，写一个函数来判断 s2 是否包含 s1 的排列。

换句话说，第一个字符串的排列之一是第二个字符串的子串。

示例1:

输入: s1 = "ab" s2 = "eidbaooo"
输出: True
解释: s2 包含 s1 的排列之一 ("ba").


示例2:

输入: s1= "ab" s2 = "eidboaoo"
输出: False
 */

#include <iostream>
#include <string>
#include <queue>
#include <map>

using namespace std;


class Solution {
public:
    bool checkInclusion(string s1, string s2) {
        int target_size = s1.size();
        bool found = false;
        map<char, int> temp;
        for (auto iter = s1.begin(); iter != s1.end(); iter++) {
            char c = *iter;
            if (temp.find(c) == temp.end()) {
                temp[c] = 1;
            } else {
                temp[c] += 1;
            }
        }
        map<char, int> copy = temp;
        queue<char> *record_queue = new queue<char>();
        for (auto iter = s2.begin(); iter != s2.end(); iter++) {
            char c = *iter;
            auto result = temp.find(c);
            if (result != temp.end()) {
                while (result->second == 0) {
                    temp[record_queue->front()] += 1;
                    record_queue->pop();
                }
                result->second--;
                record_queue->push(c);
                if (record_queue->size() == target_size) {
                    found = true;
                    break;
                }

            } else if (record_queue->size() > 0) {
                temp = copy;
                delete record_queue;
                record_queue = new queue<char>();
            }

        }
        delete record_queue;
        return found;
    }

};


int main() {
    string s1 = "ab";
    string s2 = "eidbaooo";
    Solution solution;
    cout << solution.checkInclusion(s1, s2) << endl;
    return 0;
}