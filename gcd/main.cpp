#include <iostream>

/** Greatest Common Divisor **/

int gcd_minus(int a, int b) {
    if (a == 0) {
        return b;
    }
    if (b == 0) {
        return a;
    }
    if (a > b) {
        return gcd_minus(a - b, b);
    } else if (a < b) {
        return gcd_minus(a, b - a);
    } else {
        return a;
    }
}

int gcd_mod(int a, int b) {
    if (a == 0) {
        return b;
    }
    if (b == 0) {
        return a;
    }
    if (a == b) {
        return a;
    }
    if (a > b) {
        a %= b;
    } else {
        b %= a;
    }
    return gcd_mod(a, b);
}

int gcd(int a, int b) {
    if (a == 0) {
        return b;
    }
    if (b == 0) {
        return a;
    }
    if (a == b) {
        return a;
    }
    bool a_odd = a & 1;
    bool b_odd = b & 1;
    if (a_odd && b_odd) {
        if (a > b) {
            return gcd(a - b, b);
        } else {
            return gcd(a, b - a);
        }
    } else if (a_odd && !b_odd) {
        return gcd(a, b >> 1);
    } else if (!a_odd && b_odd) {
        return gcd(a >> 1, b);
    } else {
        return gcd(a >> 1, b >> 1) << 1;
    }
}




int main() {
    int a = 100;
    int b = 56;
    std::cout << "gcd_minus() = " << gcd_minus(a, b) << std::endl;
    std::cout << "gcd_mod() = " << gcd_mod(a, b) << std::endl;
    std::cout << "gcd() = " << gcd(a, b) << std::endl;
    return 0;
}