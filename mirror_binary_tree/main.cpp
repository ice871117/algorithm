/**
leetcode 101 对称2叉树
给定一个二叉树，检查它是否是镜像对称的。

例如，二叉树 [1,2,2,3,4,4,3] 是对称的。

    1
   / \
  2   2
 / \ / \
3  4 4  3
但是下面这个 [1,2,2,null,3,null,3] 则不是镜像对称的:

    1
   / \
  2   2
   \   \
   3    3
 */

#include <iostream>
#include <stack>
#include <queue>
#include <string>

using namespace std;

template <class T>
int length(T &t) {
    return sizeof(t) / sizeof(t[0]);
}

struct TreeNode {
    int val;
    TreeNode *left;
    TreeNode *right;

    TreeNode(int x) : val(x), left(NULL), right(NULL) {}
};

class Solution {
public:
    bool isSymmetric(TreeNode *root) {
        if (root == nullptr) {
            return true;
        }
        reverse_tree(root->right);
        return tree_equal(root->left, root->right);
    }

private:

    void reverse_tree(TreeNode *node) {
        if (node) {
            swap(node->left, node->right);
            reverse_tree(node->left);
            reverse_tree(node->right);
        }
    }

    bool tree_equal(TreeNode *left, TreeNode *right) {
        if (!left && !right) {
            return true;
        }
        if ((left && !right) || (!left && right)) {
            return false;
        }
        if (left->val != right->val) return false;
        return tree_equal(left->left, right->left) && tree_equal(left->right, right->right);
    }

    void print(TreeNode *node) {
        queue<TreeNode*> print_queue;
        if (node) {
            print_queue.push(node);
            while(!print_queue.empty()) {
                auto temp = print_queue.front();
                cout << temp->val << endl;
                print_queue.pop();
                if (temp->left) {
                    print_queue.push(temp->left);
                }
                if (temp->right) {
                    print_queue.push(temp->right);
                }
            }
        }
    }

};

TreeNode *stringToTreeNode(vector<int> input) {
    if (!input.size()) {
        return nullptr;
    }

    TreeNode *root = new TreeNode(input[0]);
    queue<TreeNode *> nodeQueue;
    nodeQueue.push(root);

    auto iter = input.begin();

    while (++iter != input.end()) {
        TreeNode *node = nodeQueue.front();
        nodeQueue.pop();

        if (*iter != -99) {
            node->left = new TreeNode(*iter);
            nodeQueue.push(node->left);
        }

        if (++iter == input.end()) {
            break;
        }

        if (*iter != -99) {
            node->right = new TreeNode(*iter);
            nodeQueue.push(node->right);
        }
    }
    return root;
}


int main() {
    int a[] = {5, 4, 1, -99, 1, -99, 4, 2, -99, 2, -99};
    vector<int> vec(a, a + length(a));
    TreeNode *root = stringToTreeNode(vec);

    cout << Solution().isSymmetric(root) << endl;
    return 0;
}