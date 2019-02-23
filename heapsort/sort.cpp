#include <utility>
#include <iostream>

/** this code using asc order **/

using namespace std;

template<class T>
int length(T &arr) {
    return sizeof(arr) / sizeof(arr[0]);
}

void heap_adjust(int a[], int curr, int len) {
    int left = 2 * curr + 1;
    int right = 2 * curr + 2;
    if (right < len && a[right] > a[curr]) {
        swap(a[right], a[curr]);
    }
    if (left < len && a[left] > a[curr]) {
        swap(a[left], a[curr]);
    }
}


void sort(int a[], int len) {
    while (len > 1) {
        int i = len / 2 - 1;
        for (; i >= 0; i--) {
            heap_adjust(a, i, len);
        }
        swap(a[0], a[len-1]);
        len--;
    }
}


int main(int argc, char **args) {
    int a[] = {10, 5, 9, 33, 7, 2, 4, 12};
    int len = length(a);
    sort(a, len);
    for (int i = 0; i < len; i++) {
        cout << a[i] << " ";
    }
}