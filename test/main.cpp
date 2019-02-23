#include <iostream>

using namespace std;

int count(int a[], int len, int m){
    int count = 0;
    int flag = 0;
    for (int i = 0; i < len; i++) {
        flag = i;
        for (int i = 0; i < len; i++) {
            if (i != flag && ((a[i] ^ a[flag]) > m)) {
                count++;
            }
        }
    }
    return count;
}

int main() {
    int n, m;
    cin >> n >> m;
    int *a = new int[n]();
    cout << count(a, n, m);
}