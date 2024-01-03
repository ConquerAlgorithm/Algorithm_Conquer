#include <string>
#include <vector>
#include <algorithm>
#include <cstring>
#include <iostream>

using namespace std;

int height[100][100];
bool check[100][100];
int N;
int max_height = -999999999;
int max_safeplace = -999999999;

int dx[4] = { 0, 0, 1, -1 };
int dy[4] = { 1, -1, 0, 0 };

void DFS(int x, int y, int r)
{
	for (int i = 0; 4 > i; ++i)
	{
		int nx = x + dx[i];
		int ny = y + dy[i];


		if (nx < 0 || ny < 0 || nx >= N || ny >= N) continue;
		if (height[nx][ny] <= r || check[nx][ny]) continue;

		check[nx][ny] = true;
		DFS(nx, ny, r);
	}
}

int main()
{
	ios_base::sync_with_stdio(0);
	cin.tie(0); cout.tie(0);
	
	cin >> N;

	for (int i = 0; N > i; ++i)
	{
		for (int j = 0; N > j; ++j)
		{
			cin >> height[i][j];
			if (height[i][j] > max_height)
			{
				max_height = height[i][j];
			}
		}
	}

	for (int r = 0; r <= max_height; ++r)
	{
		int count = 0;
		memset(check, 0, sizeof(check));

		for (int i = 0; N > i; ++i)
		{
			for (int j = 0; N > j; ++j)
			{
				if ((check[i][j] == false) && (height[i][j] > r))
				{
					count++;
					DFS(i, j, r);
				}
				else
				{
					check[i][j] = true;
				}
			}
			max_safeplace = max(max_safeplace, count);
		}
	}
	cout << max_safeplace << endl;

	return 0;
}
