#include <iostream>

using namespace std;

void heap_adjust(int a[], int len, int i) {
    int left = 2 * i + 1;
    int right = 2 * i + 2;
    if (right < len && a[right] > a[i]) {
        swap(a[right], a[i]);
    }
    if (left < len && a[left] > a[i]) {
        swap(a[left], a[i]);
    }
}

void sort(int a[], int len) {
    while (len > 1) {
        for (int i = len / 2 - 1; i >= 0; i--) {
            heap_adjust(a, len, i);
        }
        swap(a[0], a[len-1]);
        len--;
    }
}

int count_and_fill(int a[], int len) {
    int count = 0;
    int temp_count = 0;
    int temp = 0;
    for (int i = 0; i < len;) {
        if (temp_count == 0) {
            temp_count++;
            i++;
            temp = a[i];
        } else {
            if (a[i] - temp <= 10) {
                temp_count++;
                i++;
                temp = a[i];
            } else {
                temp = temp + 10;
                if (temp > 100) {
                    temp = 100;
                }
                temp_count++;
                count++;
            }
        }
        if (temp_count == 3) {
            temp_count = 0;
        }
    }

    count += (3 - temp_count);
    return count;
}


int main() {
    int n;
    cin >> n;
    int *d = new int[n]();
    for (int i = 0; i < n; i++) {
        cin >> d[i];
    }
    sort(d, n);

    cout << count_and_fill(d, n);
    return 0;
}