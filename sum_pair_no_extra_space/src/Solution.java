class Solution {

    public int sumPair(int[] a, int sum) {
        heapSort(a);
        int len = a.length;
        int count = 0;
        for (int i = 0; i < len; i++) {
            final int index = binarySearch(a, sum - a[i], i + 1, len - 1);
            if (index >= 0) {
                count++;
                int temp = index;
                while (--temp > i && a[temp] == a[index]) {
                    count++;
                }
                temp = index;
                while (++temp < len && a[temp] == a[index]) {
                    count++;
                }
            }
        }
        return count;
    }

    private int binarySearch(int[] a, int target, int low, int high) {
        while (low <= high) {
            int mid = (low + high) / 2;
            if (a[mid] == target) {
                return mid;
            } else if (a[mid] > target) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return -1;
    }

    private void heapSort(int[] a) {
        for (int i = a.length; i > 0; i-- ) {
            heapAdjust(a, i);
        }
    }

    private void heapAdjust(int[] a, int len) {
        for (int i = len / 2 - 1; i >= 0; i--) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            if (right < len && a[right] > a[i]) {
                int temp = a[i];
                a[i] = a[right];
                a[right] = temp;
            }
            if (left < len && a[left] > a[i]) {
                int temp = a[i];
                a[i] = a[left];
                a[left] = temp;
            }
        }
        int temp = a[0];
        a[0] = a[len - 1];
        a[len - 1] = temp;
    }

    public static void main(String[] args) {
        int[] a = {1, 99};
        int target = 100;
        System.out.println(new Solution().sumPair(a, target));
    }
}
