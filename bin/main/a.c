#include <stdio.h>
#include <stdlib.h>

int getcount(int* count) {
    return ++(*count);
}

int main() {
    int* count;
    int v0;
    int v1;
    count = malloc(sizeof(int));
    *count = 0;
    v0 = getcount(count);
    v1 = getcount(count);
    printf("%d ",v0);
    printf("%d ",v1);
    printf("\n");
    printf("%d ",v0);
    printf("%d ",v1);
    printf("\n");
    return 0;
}

