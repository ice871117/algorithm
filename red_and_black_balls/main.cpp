#include <iostream>
#include <vector>
using namespace std;

static long count1 = 0;
static long count2 = 0;

void printVec(vector<int> &v) {
    for (auto iter = v.begin(); iter != v.end(); iter++) {
        cout << *iter;
    }
    cout << endl;
}

void swapRange(vector<int> &v, int start, int end) {
    while (start < end) {
        swap(v[start], v[end]);
        start++;
        end--;
        count2++;
    }
}

void printFullSequence(vector<int> &v) {
    const int len = v.size();
    while (1) {
        int index = -1;
        printVec(v);
        if (len < 2) {
            return;
        }
        for (int i = len - 2; i >= 0; i--) {
            if (v[i] < v[i + 1]) {
                index = i;
            }
            count1++;
        }
        if (index < 0) {
            return;
        }
        for (int i = len - 1; i >= 0; i--) {
            if (v[i] > v[index]) {
                swap(v[i], v[index]);
                break;
            }
        }
        swapRange(v, index + 1, len - 1);
    }
}

/**
 * m个红球，n个黑球，求全排列
 */
int main() {
    int m = 5; // red 0
    int n = 5; // black 1
    vector<int> v;
    for (int i = 0; i < m; i++) {
        v.push_back(0);
    }
    for (int i = 0; i < n; i++) {
        v.push_back(1);
    }
    printFullSequence(v);
    cout << ((count1 > count2) ? count1 : count2) << endl;
    return 0;
}


