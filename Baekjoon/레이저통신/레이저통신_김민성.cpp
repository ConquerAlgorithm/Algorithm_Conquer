#include <queue>
#include <vector>
#include <string>
#include <iostream>

using namespace std;

int main()
{
	vector<pair<int, int>> c;
	int d[101][101]; // d[i][j] = i에서 j까지 가는 최단거리
	char map[101][101];
	bool check[101][101];
	int dx[] = {1, -1, 0, 0};
	int dy[] = {0, 0, 1, -1};
	string s;
	int n, m;
	cin >> m >> n;
	cin.ignore();

	for (int i = 0; n > i; ++i)
	{
		getline(cin, s);
		for (int j = 0; s.size() > j; ++j)
		{
			map[i][j] = s[j];
			if (map[i][j] == 'C')
			{
				c.push_back(make_pair(i, j));
			}
		}
	}
	int s1 = c[0].first;
	int s2 = c[0].second;
	queue<pair<int, int>> q;
	q.push(make_pair(s1, s2));
	d[s1][s2] = 0;
	check[s1][s2] = true;

	while (!q.empty())
	{
		int x = q.front().first;
		int y = q.front().second;
		q.pop();
		for (int k = 0; 4 > k; ++k)
		{
			int nx = x + dx[k];
			int ny = y + dy[k];
			while ((nx >= 0) && (nx < n) && (ny >= 0) && (ny < m))
			{
				if (map[nx][ny] == '*')
				{
					break;
				}
				if (!check[nx][ny])
				{
					check[nx][ny] = true;
					d[nx][ny] = d[x][y] + 1;
					q.push(make_pair(nx, ny));
				}
				nx += dx[k];
				ny += dy[k];
			}
		}
	}

	cout << d[c[1].first][c[1].second] - 1 << endl;
}
