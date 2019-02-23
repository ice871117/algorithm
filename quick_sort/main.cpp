#include <iostream>

/** use asc order **/

using namespace std;

template<class T>
int length(T &t) {
    return sizeof(t) / sizeof(t[0]);
}

void quick_sort(int a[], int start, int end) {
    const int pivot = start;
    int i = start;
    int j = end;
    if (start >= end) {
        return;
    }
    while (i < j) {
        while (i < j && a[j] >= a[pivot]) {
            j--;
        }
        while (i < j && a[i] <= a[pivot]) {
            i++;
        }
        if (i < j) {
            swap(a[i], a[j]);
        }
    }
    swap(a[j], a[pivot]);
    quick_sort(a, start, i - 1);
    quick_sort(a, i + 1, end);
}


int main() {
    int a[] = {44, 12, 5, 25, 2, 4, 20, 9, 7};
    int len = length(a);
    quick_sort(a, 0, len - 1);
    for (int i = 0; i < len; i++) {
        cout << a[i] << " ";
    }
    return 0;
}