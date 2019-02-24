#include <iostream>

using namespace std;


/**
 *
 * | m00  m01 |
 * |          |
 * | m10  m11 |
 *
 */
struct Matrix_2x2 {
    Matrix_2x2(long a, long b, long c, long d) : m00(a), m01(b), m10(c), m11(d) {}

    long m00;
    long m01;
    long m10;
    long m11;
};

Matrix_2x2 matrix_multiply(const Matrix_2x2 &a, const Matrix_2x2 &b) {
    Matrix_2x2 matrix(0, 0, 0, 0);
    matrix.m00 = a.m00 * b.m00 + a.m01 * b.m10;
    matrix.m01 = a.m00 * b.m01 + a.m01 * b.m11;
    matrix.m10 = a.m10 * b.m00 + a.m11 * b.m10;
    matrix.m11 = a.m10 * b.m01 + a.m11 * b.m11;

    return matrix;
}

Matrix_2x2 matrix_power(int pow) {
    Matrix_2x2 matrix(1, 1, 1, 0);
    if (pow == 1) {
        return matrix;
    }
    if (pow % 2 == 0) {
        Matrix_2x2 ret = matrix_power(pow / 2);
        return matrix_multiply(ret, ret);
    } else {
        Matrix_2x2 ret = matrix_power((pow - 1) / 2);
        Matrix_2x2 even_part = matrix_multiply(ret, ret);
        return matrix_multiply(even_part, matrix);
    }
}

long fibonacci(int n) {
    if (n == 0) {
        return 0;
    }
    if (n == 1) {
        return 1;
    }
    Matrix_2x2 ret = matrix_power(n - 1);
    return ret.m00;
}


int main() {
    cout << "fibonacci(1) = " << fibonacci(1) << endl;
    cout << "fibonacci(5) = " << fibonacci(5) << endl;
    cout << "fibonacci(7) = " << fibonacci(7) << endl;
    cout << "fibonacci(10) = " << fibonacci(10) << endl;
    return 0;
}