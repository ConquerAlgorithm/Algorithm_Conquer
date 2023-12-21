#include <iostream>
#include <queue>


using namespace std;

string map[100];
int visited[100][100];
int dx[4] = { 1, -1, 0, 0 };
int dy[4] = { 0, 0, 1, -1 };
int n, m;
queue<pair<int, int>> q;

void bfs(int x, int y) 
{
	q.push(make_pair(x, y));
	
	visited[x][y] = 1;
	
	while (!q.empty()) {
		x = q.front().first;
		y = q.front().second;
		q.pop();
		for (int i = 0; 4 > i; ++i) 
		{
			int nx = x + dx[i];
			int ny = y + dy[i];
			if ((0 <= nx) && (nx < n) && (0 <= ny) && (ny < m) && (visited[nx][ny] == 0) && (map[nx][ny] == '1')) 
			{
				q.push(make_pair(nx, ny));
				visited[nx][ny] = visited[x][y] + 1;
			}
		}
	}
}


int main(void) 
{
	cin >> n >> m;

	for (int i = 0; i < n; i++)
	{
		cin >> map[i];
	}

	bfs(0, 0);
	
	cout << visited[n - 1][m - 1];

	return 0;
}
