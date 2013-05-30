/**
 * calculate factor, nothing special
 * by msh
 */
#include <stdio.h>

int nfactor(int n) {
  if (n == 0) return 1;
  else return n * nfactor(n - 1);
}

int main() {
  printf("%d\n", nfactor(6));
  return a;
}
