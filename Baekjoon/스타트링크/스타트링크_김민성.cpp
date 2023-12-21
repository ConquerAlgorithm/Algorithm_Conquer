import sys

F, S, G, U, D = map(int, sys.stdin.readline().split())
visited = [0] * (F + 1)
visited[S] = 1
queue = [S]
cnt = 0

while queue:
    for _ in range(len(queue)):
        cur = queue.pop(0)
        if cur == G:
            print(cnt)
            exit()
        if cur + U <= F and not visited[cur + U]:
            visited[cur + U] = 1
            queue.append(cur + U)
        if cur - D > 0 and not visited[cur - D]:
            visited[cur - D] = 1
            queue.append(cur - D)
    cnt += 1

if visited[G] == 0:
    print("use the stairs")
else:
    print(cnt)
