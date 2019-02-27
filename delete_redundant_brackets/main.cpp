/**
 * leetcode 301删除无效括号
 *
 * 删除最小数量的无效括号，使得输入的字符串有效，返回所有可能的结果。
 *
 * 说明: 输入可能包含了除 ( 和 ) 以外的字符。
 *
 * 示例 1:
 *
 * 输入: "()())()"
 * 输出: ["()()()", "(())()"]
 * 示例 2:
 *
 * 输入: "(a)())()"
 * 输出: ["(a)()()", "(a())()"]
 * 示例 3:
 *
 * 输入: ")("
 * 输出: [""]
 */

#include <iostream>
#include <vector>
#include <string>
#include <stack>
#include <set>

using namespace std;

class Solution {
public:
    vector<string> removeInvalidParentheses(string s) {
        vector<string> result;
        stack<char> to_remove;
        s = pre_process(s);
        if (s.empty()) {
            result.push_back(s);
            return result;
        }
        for (auto i = s.begin(); i != s.end(); i++) {
            const char curr = *i;
            if (curr == '(') {
                to_remove.push(curr);
            } else if (curr == ')') {
                if (!to_remove.empty() && to_remove.top() == '(') {
                    to_remove.pop();
                } else {
                    to_remove.push(curr);
                }
            }
        }
        if (to_remove.empty()) {
            result.push_back(s);
            return result;
        }

        set<string> temp;
        find_valid(s, to_remove, temp);
        for (auto i = temp.begin(); i != temp.end(); i++) {
            result.push_back(*i);
        }

        print("result is : ", result);
        return result;
    }

private:
    set<string> cache;

    string pre_process(string s) {
        for (int i = 0; i < s.size();) {
            char curr = s[i];
            if (curr == '(') {
                break;
            } else if (curr == ')') {
                s.erase(i, 1);
            } else {
                i++;
            }
        }
        for (int i = s.size() - 1; i >= 0;) {
            char curr = s[i];
            if (curr == ')') {
                break;
            } else if (curr == '(') {
                s.erase(i, 1);
            } else {
                i--;
            }
        }
        return s;
    }

    void find_valid(string &s, stack<char> &to_remove, set<string> &result) {
        bool last_round = to_remove.size() == 1;
        int size = s.size();
        for (int i = 0; i < size; i++) {
            char curr = s[i];
            char top = to_remove.top();
            if (curr == top) {
                string temp = s;
                if (last_round) {
                    temp.erase(i, 1);
                    if (is_valid(temp)) {
                        result.insert(temp);
                    }
                } else {
                    temp.erase(i, 1);
                    if (cache.find(temp) != cache.end()) {
                        continue;
                    }
                    to_remove.pop();
                    find_valid(temp, to_remove, result);
                    to_remove.push(top);
                }
            }
        }
        cache.insert(s);
    }

    bool is_valid(string &s) {
        if (s.empty()) {
            return false;
        }
        stack<char> brackets_match;
        for (auto i = s.begin(); i != s.end(); i++) {
            char curr = *i;
            if (brackets_match.empty()) {
                if (curr == '(') {
                    brackets_match.push(curr);
                } else if (curr == ')') {
                    return false;
                }
            } else {
                if (curr == '(') {
                    brackets_match.push(curr);
                } else if (brackets_match.top() == '(' && curr == ')') {
                    brackets_match.pop();
                }
            }
        }
        return brackets_match.empty();
    }

    void print(const string &title, vector<string> toprint) {
        cout << title << endl;
        for (auto i = toprint.begin(); i != toprint.end(); i++) {
            cout << *i << endl;
        }
    }
};


int main() {
    Solution solution;
//    solution.removeInvalidParentheses(")()))())))");
//    solution.removeInvalidParentheses("(a)())()");
    solution.removeInvalidParentheses("())((((((((((b))(");
    return 0;
}