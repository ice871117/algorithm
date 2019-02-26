#include <iostream>

using namespace std;

template<class T>
struct Node {

    Node(T _value) : left(nullptr), right(nullptr), value(_value) {}

    Node<T> *left;
    Node<T> *right;
    T value;
};

template<class T>
class BinarySearchTree {

public:
    Node<T> *find(const T &t) {
        Node<T> *curr = root;
        while (curr) {
            if (t < curr->value) {
                curr = curr->left;
            } else if (t > curr->value) {
                curr = curr->right;
            } else {
                return curr;
            }
        }
    }

    void insert(const T t) {
        if (!create_if_not_exist(t)) {
            Node<T> *parent = nullptr;
            Node<T> *curr = root;
            int temp_height = 0;
            while (curr) {
                if (t <= curr->value) {
                    parent = curr;
                    curr = parent->left;
                } else {
                    parent = curr;
                    curr = parent->right;
                }
                temp_height++;
            }
            if (temp_height > height) {
                height = temp_height;
            }
            if (t <= parent->value) {
                parent->left = new Node<T>(t);
            } else {
                parent->right = new Node<T>(t);
            }
        }
    }

    bool remove(const T &t) {
        Node<T> *parent = nullptr;
        Node<T> *curr = root;
        while (curr) {
            if (t < curr->value) {
                parent = curr;
                curr = parent->left;
            } else if (t > curr->value) {
                parent = curr;
                curr = parent->right;
            } else {
                break;
            }
        }
        return do_remove(parent, curr);
    }

    void traverse_mid() {
        traverse_mid_from(root);
        cout << endl;
    }


    Node<T> *root;
    int height;

private:
    bool create_if_not_exist(T value) {
        if (root == nullptr) {
            root = new Node<T>(value);
            return true;
        }
        return false;
    }

    void traverse_mid_from(Node<T> *curr) {
        if (curr) {
            if (curr->left) {
                traverse_mid_from(curr->left);
            }
            cout << curr->value << " ";
            if (curr->right) {
                traverse_mid_from(curr->right);
            }
        }
    }

    Node<T> *get_pioneer(Node<T> *curr) {
        if (curr->left) {
            return curr->left->right;
        }
        return nullptr;
    }

    Node<T> *get_successor(Node<T> *curr) {
        if (curr->right) {
            return curr->right->left;
        }
        return nullptr;
    }

    bool do_remove(Node<T> *parent, Node<T> *curr) {
        if (curr) {
            // 1.leaf
            if (!curr->left && !curr->right) {
                if (parent) {
                    if (curr->value <= parent->value) {
                        parent->left = nullptr;
                    } else {
                        parent->right = nullptr;
                    }
                    delete curr;
                } else {
                    delete root;
                    root = nullptr;
                }
                return true;
            }
            // 2. has pioneer
            Node<T> *pioneer = get_pioneer(curr);
            if (pioneer) {
                curr->value = pioneer->value;
                do_remove(curr->left, pioneer);
                return true;
            }

            // 3. has successor
            Node<T> *successor = get_successor(curr);
            if (successor) {
                curr->value = successor->value;
                do_remove(curr->right, successor);
                return true;
            }

            // 4. has left
            if (curr->left) {
                // use temp to maintain do_remove() parent's value
                int temp = curr->left->value;
                do_remove(curr, curr->left);
                curr->value = temp;
                return true;
            }
            if (curr->right) {
                // 5. no left but has right
                int temp = curr->right->value;
                do_remove(curr, curr->right);
                curr->value = temp;
                return true;
            }

        }
        return false;
    }

};


int main() {
    BinarySearchTree<int> binarySearchTree;
    binarySearchTree.insert(50);
    binarySearchTree.insert(20);
    binarySearchTree.insert(7);
    binarySearchTree.insert(5);
    binarySearchTree.insert(10);
    binarySearchTree.insert(8);
    binarySearchTree.insert(11);
    binarySearchTree.traverse_mid();
    binarySearchTree.insert(25);
    binarySearchTree.insert(23);
    binarySearchTree.insert(30);
    binarySearchTree.insert(60);
    binarySearchTree.insert(58);
    binarySearchTree.insert(66);
    binarySearchTree.insert(63);
    binarySearchTree.traverse_mid();
    binarySearchTree.remove(20);
    binarySearchTree.traverse_mid();
    binarySearchTree.remove(7);
    binarySearchTree.remove(60);
    binarySearchTree.traverse_mid();
    cout << "Hello, World!" << endl;
    return 0;
}