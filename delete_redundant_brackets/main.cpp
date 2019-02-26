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
        int start = 0;
        int end = 0;
        stack<char> brackets_match;
        for (auto i = s.begin(); i != s.end(); i++, end++) {
            char curr = *i;
            if (brackets_match.empty()) {
                if (curr == '(') {
                    brackets_match.push(curr);
                } else if (curr == ')') {
                    splited.push_back(s.substr(start, end - start + 1));
                    is_splited_valid.push_back(false);
                    start = end + 1;
                    continue;
                }
            } else {
                if (curr == '(') {
                    brackets_match.push(curr);
                } else if (brackets_match.top() == '(' && curr == ')') {
                    brackets_match.pop();
                }
            }
            if (i + 1 == s.end()) {
                splited.push_back(s.substr(start, end - start + 1));
                is_splited_valid.push_back(brackets_match.empty());
            }
        }
        print("split is : ", splited);
        long size = splited.size();
        if (size == 1 && brackets_match.size() > 0) {
            cout << "not implemented for redundant left brackets " << endl;
        } else {
            for (int i = 0; i < size; i++) {
                set<string> temp;
                if (!is_splited_valid[i]) {
                    make_valid(splited[i], temp);
                } else {
                    temp.insert(splited[i]);
                }
                valid_strings.push_back(temp);
            }
        }
        for (auto i = valid_strings.begin(); i != valid_strings.end(); i++) {
            print("new segment : ", *i);
        }


    }
private:
    vector<bool> is_splited_valid;
    vector<string> splited;
    vector<set<string>> valid_strings;

    void make_valid(string &s, set<string> &out) {
        int str_len = s.size();
        for (int i = 0; i < str_len; i++) {
            char curr = s[i];
            if (curr == '(' || curr == ')') {
                string temp = s;
                temp.erase(i, 1);
                if (is_valid(temp)) {
                    out.insert(temp);
                }
            }
        }
    }

    bool is_valid(string &s) {
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

    void print(const string &title, set<string> toprint) {
        cout << title << endl;
        for (auto i = toprint.begin(); i != toprint.end(); i++) {
            cout << *i << endl;
        }
    }
};

int main() {
    Solution solution;
    solution.removeInvalidParentheses("(a)())()");
    return 0;
}