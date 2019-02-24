#include <iostream>
#include <vector>

using namespace std;

int resolve(char a[], int len_a, char b[], int len_b, char * common_str) {
    int result = 0;
    vector<vector<int>> table(len_a, vector<int>(len_b, 0));
    int start = -1;

    for (int i = 0; i < len_a; i++) {
        for (int j = 0; j < len_b; j++) {
            if (a[i] == b[j]) {
                if (i - 1 < 0 || j - 1 < 0) {
                    table[i][j] = 1;
                } else {
                    table[i][j] = table[i - 1][j - 1] + 1;
                }
                if (table[i][j] > result) {
                    result = table[i][j];
                    start = i - result + 1;
                }
            }
        }
    }
    cout << "table:" << endl;
    cout << "  ";
    for (int i = 0; i < len_b; i++) {
        cout << b[i] << " ";
    }
    cout << endl;
    for (int i = 0; i < len_a; i++) {
        cout << a[i] << " ";
        for (int j = 0; j < len_b; j++) {
            cout << table[i][j] << " ";
        }
        cout << endl;
    }
    if (start >= 0) {
        strncpy(common_str, a, result);
    }
    return result;
}


int main() {
    char a[] = "ider.cs@gmail.com";
    char b[] = "blog.iderzheng.com";
    char *result = new char[20]();
    int length = resolve(a, strlen(a), b, strlen(b), result);
    cout << endl << "longest substring length: " << length << endl;
    cout << "first common string: " << result;
    return 0;
}