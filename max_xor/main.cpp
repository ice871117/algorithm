/**
 * 给定一个数组，任意两个不同的元素组队，求满足两个元素异或值大于m的有多少对
 */

#include <iostream>

#define MAX_INDEX 16

using namespace std;

using TrieTree = struct TrieTree {
    struct TrieTree *node[2] = {nullptr};
    int count = 1;
    ~TrieTree() {
        if (node[0]) {
            delete node[0];
        }
        if (node[1]) {
            delete node[1];
        }
    }
};

TrieTree *build_trie_tree(int a[], int len, int index) {
    TrieTree *root = new TrieTree();
    TrieTree *curr = nullptr;
    for (int i = 0; i < len; i++) {
        curr = root;
        for (int j = index; j >= 0; j--) {
            int digit = (a[i] >> j) & 1;
            if (curr->node[digit] == nullptr) {
                curr->node[digit] = new TrieTree();
            } else {
                curr->node[digit]->count++;
            }
            curr = curr->node[digit];
        }
    }
    return root;
}

int query_trie_tree(TrieTree *tree, int curr_value, int m, int index) {
    TrieTree  *curr = tree;
    if (tree == nullptr) {
        return 0;
    }
    for (int i = index; i >= 0; i--) {
        int value_digit = (curr_value >> i) & 1;
        int m_digit = (m >> i) & 1;
        if (value_digit == 0 && m_digit == 1) {
            if (curr->node[1] == nullptr) {
                return 0;
            }
            curr = curr->node[1];
        } else if (value_digit == 1 && m_digit == 1) {
            if (curr->node[0] == nullptr) {
                return 0;
            }
            curr = curr->node[0];
        } else if (value_digit == 0 && m_digit == 0) {
            int count_0 = query_trie_tree(curr->node[0], curr_value, m, i - 1);
            int count_1 = curr->node[1] == nullptr ? 0 : curr->node[1]->count;
            return count_0 + count_1;
        } else if (value_digit == 1 && m_digit == 0) {
            int count_1 = query_trie_tree(curr->node[1], curr_value, m, i - 1);
            int count_0 = curr->node[0] == nullptr ? 0 : curr->node[0]->count;
            return count_0 + count_1;
        }
    }
    return 0;
}

long query(int a[], int len, int m) {
    TrieTree *root = build_trie_tree(a, len, MAX_INDEX);
    long count = 0;
    for (int i = 0; i < len; i++) {
        count += query_trie_tree(root, a[i], m, MAX_INDEX);
    }
    delete root;
    return count / 2;
}


int main() {
    int n, m;
    cin >> n >> m;
    int *a = new int[n]();
    for (int i = 0; i < n; i++) {
        cin >> a[i];
    }
    cout << query(a, n, m);
}