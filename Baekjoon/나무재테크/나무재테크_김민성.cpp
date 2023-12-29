#include <iostream>
#include <algorithm>
#include <vector>

using namespace std;

struct Tree
{
	int age;
	int y;
	int x;
};

int N, M, K;
int index = 0;
int dx[8] = { -1, -1, 0, 1, 1, 1, 0, -1 };
int dy[8] = { 0, -1, -1, -1, 0, 1, 1, 1 };
vector<vector<Tree>> tree;
vector<vector<int>> map, supply;

void input()
{
	cin >> N >> M >> K;
	tree.assign(2, vector<Tree>());
	map.assign(N, vector<int>(N, 5));
	supply.assign(N, vector<int>(N, 0));

	for (int i = 0; N > i; ++i)
	{
		for (int j = 0; N > j; ++j)
		{
			cin >> supply[i][j];
		}
	}

	for (int i = 0; M > i; ++i)
	{
		int x, y, z;
		cin >> y >> x >> z;
		Tree tmp = { z, y - 1, x - 1 };
		tree[index].emplace_back(tmp);
	}
}

bool compare(const struct Tree& t1, const struct Tree& t2)
{
	return t1.age < t2.age;
}

bool isInside(int y, int x)
{
	return (y >= 0 && y < N) && (x >= 0 && x < N);
}

void summer(vector<Tree>& now, vector<Tree>& next)
{
	for (int i = 0; now.size() > i; ++i)
	{
		if (now[i].age < 0)
		{
			map[now[i].y][now[i].x] += -now[i].age / 2;
		}
		else
		{
			next.emplace_back(now[i]);
		}
	}
}

void winter()
{
	for (int i = 0; N > i; ++i)
	{
		for (int j = 0; N > j; ++j)
		{
			map[i][j] += supply[i][j];
		}
	}
}

void Cycleling()
{
	vector<Tree>& now = tree[index];
	vector<Tree>& next = tree[1 - index];
	next.clear();

	for (int i = 0; now.size() > i; ++i)
	{
		int cy = now[i].y;
		int cx = now[i].x;

		if (now[i].age <= map[cy][cx])
		{
			map[cy][cx] -= now[i].age;
			now[i].age++;
			
			if (now[i].age % 5 == 0)
			{
				for (int j = 0; 8 > j; ++j)
				{
					int ny = cy + dy[j];
					int nx = cx + dx[j];

					if (isInside(ny, nx))
					{
						next.push_back({ 1, ny, nx });
					}
				}
			}
		}
		else
		{
			now[i].age = -now[i].age;
		}
	}

	summer(now, next);
	winter();


	index = 1 - index;
}

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(NULL);cout.tie(NULL);

	input();
	sort(tree[index].begin(), tree[index].end(), compare);
	while (K--)
	{
		Cycleling();
	}

	cout << tree[index].size();

	return 0;
}
