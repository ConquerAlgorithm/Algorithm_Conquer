#include <iostream>
#include <queue>
#include <algorithm>

using namespace std;

int answer = -1;
int m, n, h, tomato[102][102][102];
int dx[] = { 0,0,1,-1,0,0 };
int dy[] = { 1,-1,0,0,0,0 };
int dz[] = { 0,0,0,0,1,-1 };

queue<pair<pair<int, int>, int>> q;

void bfs()
{
	while (!q.empty())
	{
		int x = q.front().first.first;
		int y = q.front().first.second;
		int z = q.front().second;
		q.pop();

		for (int i = 0; 6 > i; ++i)
		{
			int nx = x + dx[i];
			int ny = y + dy[i];
			int nz = z + dz[i];

			if ((nx >= 0) && (ny >= 0) && (nz >= 0) && (nx < n) && (ny < m) && (nz < h) && (tomato[nx][ny][nz] == 0))
			{
				tomato[nx][ny][nz] = tomato[x][y][z] + 1;
				q.push(make_pair(make_pair(nx, ny), nz));
			}
		}
	}
}

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(NULL), cout.tie(nullptr);

	cin >> m >> n >> h;
	for (int k = 0; h > k; ++k)
	{
		for (int i = 0; n > i; ++i)
		{
			for (int j = 0; m > j; ++j)
			{
				cin >> tomato[i][j][k];
				if (tomato[i][j][k] == 1)
				{
					q.push(make_pair(make_pair(i, j), k));
				}
			}
		}
	}

	bfs();
	
	for (int i = 0; n > i; ++i)
	{
		for (int j = 0; m > j; ++j)
		{
			for (int k = 0; h > k; ++k)
			{
				if (tomato[i][j][k] == 0)
				{
					cout << -1;
					return 0;
				}
				answer = max(answer, tomato[i][j][k]);
			}
		}
	}
	cout << answer - 1;
	
	return 0;
}
