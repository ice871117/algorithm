#include <iostream>

using namespace std;

int resolve(int a[8][8], int w, int h) {
    int max = 0;
    if (h == 0 || w == 0) {
        return 0;
    }
    if (h == 1) {
        for (int i = 0; i < w; i++) {
            if (a[0][i] == 1) {
                return 1;
            }
        }
    }
    // print
    cout << "after resolve:" << endl;
    for (int i = 1; i < h; i++) {
        int max_in_row = 0;
        int acc = 1;
        for (int j = 0; j < w; j++) {
            if (a[i-1][j] > 0 && a[i][j] > 0) {
                a[i][j] = a[i-1][j] + 1;
            }
            int curr = a[i][j];
            if (max_in_row != curr) {
                acc = 1;
                max_in_row = std::max(curr, max_in_row);
            } else {
                acc++;
            }
            if (curr == 1 && curr > max) {
                max = 1;
            } else if (j > 0 && curr == a[i][j-1] && curr > max && curr == acc) {
                max = curr;
            }
            cout << curr << " ";
        }
        cout << endl;
    }

    return max;
}


int main() {
    int a[][8] = {{0, 0, 0, 1, 0, 0, 1, 0},
                  {1, 1, 0, 0, 0, 0, 0, 0},
                  {0, 0, 0, 1, 1, 1, 0, 0},
                  {0, 1, 0, 1, 1, 1, 0, 0},
                  {0, 1, 1, 1, 1, 1, 0, 0},
                  {0, 1, 1, 0, 0, 0, 1, 0},
                  {0, 0, 0, 0, 0, 1, 1, 1},
                  {0, 0, 1, 0, 0, 1, 1, 1}};

    cout << resolve(a, 8, 8) << endl;
    return 0;
}