#include <iostream>
#include <queue>

using namespace std;

struct moves
{
	int x;
	int y;
};

moves store[100];
moves festival;
moves home;
bool visited[100];

int abs(int n)
{
	if (n < 0) return -n;
	return n;
}

bool bfs(int n)
{
	queue<pair<int, int>> q;
	q.push({ home.x, home.y });

	while (!q.empty())
	{
		int x = q.front().first;
		int y = q.front().second;
		q.pop();

		if (1000 >= (abs(festival.x - x) + abs(festival.y - y)))
		{
			return true;
		}
		for (int i = 0; n > i; ++i)
		{
			if (visited[i] == 1)
			{
				continue;
			}
			if (1000 >= (abs(store[i].x - x) + abs(store[i].y - y)) )
			{
				visited[i] = 1;
				q.push({ store[i].x, store[i].y });
			}
		}
	}
	return false;
}

int main()
{
	ios::sync_with_stdio(0);
	cin.tie(0); cout.tie(0);

	int t;
	cin >> t;

	while (t--)
	{
		int n;
		cin >> n;
		cin >> home.x >> home.y;

		for (int i = 0; n > i; ++i)
		{
			cin >> store[i].x >> store[i].y;
		}
		cin >> festival.x >> festival.y;

		bool possible = bfs(n);
		if (possible)
		{
			cout << "happy" << endl;
		}
		else
		{
			cout << "sad" << endl;
		}

		fill(visited, visited + 100, false);
	}

	return 0;
}
