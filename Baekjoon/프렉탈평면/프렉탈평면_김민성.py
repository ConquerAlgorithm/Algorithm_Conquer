import sys

input = sys.stdin.readline


def check_black(N, K, l, point):
    x, y = point
    center = l // N
    if l == 1:
        return 0
    x_range = (center * (N - K) // 2, center * (N + K) // 2)
    y_range = (center * (N - K) // 2, center * (N + K) // 2)
    if x_range[0] <= x < x_range[1] and y_range[0] <= y < y_range[1]:
        return 1
    x %= center
    y %= center
    return check_black(N, K, l // N, [x, y])


s, N, K, R1, R2, C1, C2 = map(int, input().split())
l = N ** s

for i in range(R1, R2 + 1):
    for j in range(C1, C2 + 1):
        print(check_black(N, K, l, [i, j]), end="")
    print()
    