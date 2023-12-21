#include <iostream>

using namespace std;

#define MAX 100
int N, M;
int map[MAX][MAX];
int visited[MAX][MAX] = { 0 };

int dx[] = { -1,0,1,0 }; 
int dy[] = { 0,1,0,-1 };


void dfs(int r, int c, int d, int sum) {
	for (int i = 0; 4 > i; ++i)
	{
		int nd = (d + 3 - i) % 4;
		int nr = r + dx[nd];
		int nc = c + dy[nd];

		if ((nr < 0) || (nr >= N) || (nc < 0) || (nc >= M) || map[nr][nc])
		{
			continue;
		}

		if ((map[nr][nc] == 0) && (visited[nr][nc] == 0))
		{
			visited[nr][nc] = 1;
			sum++;
			dfs(nr, nc, nd, sum);
		}
	}
	int backIndex = (d + 2 < 4) ? (d + 2) : (d - 2);
	int br = r + dx[backIndex];
	int bc = c + dy[backIndex];

	if ((br >= 0) && (br <= N) && (bc >= 0) && (bc <= M))
	{
		if (map[br][bc] == 0)
		{
			dfs(br, bc, d, sum);
		}
		else
		{
			cout << sum << endl;
			exit(0);
		}
	}
}

int main() 
{
	ios::sync_with_stdio(false);
	cin.tie(NULL), cout.tie(nullptr);
	int r, c, d;
	cin >> N >> M;
	cin >> r >> c >> d;

	for (int i = 0; N > i; ++i)
	{
		for (int j = 0; M > j; ++j)
		{
			cin >> map[i][j];
		}
	}

	visited[r][c] = 1;
	dfs(r, c, d, 1);

	return 0;
}
