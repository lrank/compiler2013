int a[10][10];

int main() {
	int i, j;
	for (i = 1; i < 3; ++i) {
		for (j = 1; j < 3; ++j) {
			a[i][j] = i * j;
			printf("%d ", a[i][j]);
		}
		printf("\n");
	}
	return 0;
}
