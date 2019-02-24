#include <iostream>
#include <vector>
#include <stack>

using namespace std;

template <class T>
int length(T &t) {
    return sizeof(t) / sizeof(t[0]);
}

vector<int> resolve(int a[], int len) {
    vector<int> result(len-1);
    stack<int> temp_stack;
    for (int i = 0; i < len; i++) {
        int curr = a[i];
        while (!temp_stack.empty() && a[temp_stack.top()] < curr) {
            result[temp_stack.top()] = curr;
            temp_stack.pop();
        }
        temp_stack.push(i);
    }
    return result;
}


int main() {
    int a[] = {3, 1, 2, 5, 6, 0, 9};
    int len = length(a);
    auto ret = resolve(a, len);
    for (auto iter = ret.begin(); iter != ret.end(); iter++) {
        cout << *iter << " ";
    }
    return 0;
}