#include <iostream>
#include <cstring>

/** using asc order **/

using namespace std;

template<class T>
int length(T &t) {
    return sizeof(t) / sizeof(t[0]);
}

void merge(int a[], int left_start, int left_end, int right_start, int right_end, int *temp) {
    int k = left_start;
    int i = left_start, j = right_start;
    while (i <= left_end && j <= right_end) {
        if (a[i] < a[j]) {
            temp[k++] = a[i++];
        } else {
            temp[k++] = a[j++];
        }
    }
    while (i <= left_end) {
        temp[k++] = a[i++];
    }
    while (j <= right_end) {
        temp[k++] = a[j++];
    }
    for (k = left_start; k <= right_end; k++) {
        a[k] = temp[k];
    }
}

void merge_sort(int a[], int start, int end, int *temp) {
    if (end - start > 0) {
        int middle = (start + end) / 2;
        merge_sort(a, start, middle, temp);
        merge_sort(a, middle + 1, end, temp);
        merge(a, start, middle, middle + 1, end, temp);
    } else {
        temp[start] = a[start];
    }
}

void sort(int **a, int len) {
    int *temp = new int[len];
    merge_sort(*a, 0, len - 1, temp);
    swap(*a, temp);
    delete[] *a;
}

int main() {
    int a[] = {30, 12, 5, 2, 9, 14, 3, 7, 44};
    int len = length(a);
    int *pArr = a;
    sort(&pArr, len);
    for (int i = 0; i < len; i++) {
        cout << pArr[i] << " ";
    };
    return 0;
}