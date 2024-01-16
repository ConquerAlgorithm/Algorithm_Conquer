#include <iostream>
#include <vector>
#include <memory.h>

using namespace std;

int N;
int M;
int Map[400][400];
bool visited[400][400];
int mnum = 0;

vector<pair<int, int>> v;

int dx[] = { -1, 1, 0, 0 };
int dy[] = { 0, 0, -1, 1 };

void melting()
{
	int x, y;
	int nx, ny;
	int temp[400][400];

	for (int i = 0; i < v.size(); ++i)
	{
		x = v[i].first;
		y = v[i].second;
		if (Map[x][y] == 0) continue;

		int count = 0;
		for (int j = 0; 4 > j; ++j)
		{
			nx = x + dx[j];
			ny = y + dy[j];

			if (nx < 0 || nx >= N || ny < 0 || ny >= M) continue;
			if (Map[nx][ny] == 0) count++;
		}

		if (Map[x][y] - count <= 0) {
			mnum--;
			temp[x][y] = 0;
		}
		else temp[x][y] = Map[x][y] - count;
	}

	for (int i = 0; i < v.size(); ++i)
	{
		if (Map[v[i].first][v[i].second] == 0) continue;
		Map[v[i].first][v[i].second] = temp[v[i].first][v[i].second];
	}
}

void dfs(int x, int y)
{
	visited[x][y] = true;
	int nx, ny;

	for (int i = 0; 4 > i; ++i)
	{
		nx = x + dx[i];
		ny = y + dy[i];

		if (nx < 0 || nx >= N || ny < 0 || ny >= M || visited[nx][ny]) continue;
		if (Map[nx][ny] > 0) 
		{
			dfs(nx, ny);
		}
	}
}

bool check()
{
	int count = 0;
	for (int i = 0; N > i; ++i)
	{
		for (int j = 0; M > j; ++j)
		{
			if (!visited[i][j] && (Map[i][j] > 0))
			{
				dfs(i, j);
				count++;
			}
		}
	}
	if (count >= 2) return true;
	else return false;
}

void Solve()
{
	int year = 0;
	while (true)
	{
		year++;
		melting();
		if (mnum == 0)
		{
			cout << 0 << endl;
			return;
		}
		memset(visited, false, sizeof(visited));
		if (check())
		{
			cout << year << endl;
			return;
		}
	}
}

int main()
{
	cin >> N >> M;
	for (int i = 0; N > i; ++i)
	{
		for (int j = 0; M > j; ++j)
		{
			cin >> Map[i][j];
			if (Map[i][j] != 0)
			{
				v.push_back({ i, j });
				mnum++;
			}
		}
		
	}
	Solve();
	return 0;
}
