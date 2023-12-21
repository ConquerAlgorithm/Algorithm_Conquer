#include <iostream>

using namespace std;

int n, m, count;
int p[201];

int find(int x)
{
	if (p[x] == x)
	{
		return x;
	}
	else return p[x] = find(p[x]);
}

void union_node(int x, int y)
{
	x = find(x);
	y = find(y);
	if (x < y)
	{
		p[y] = x;
	}
	else
	{
		p[x] = y;
	}
}

int main()
{
	ios_base::sync_with_stdio(false);
	cin.tie(NULL), cout.tie(nullptr);

	cin >> n >> m;
	for (int i = 1; n >= i; ++i)
	{
		p[i] = i;
	}

	for (int i = 1; n >= i; ++i)
	{
		for (int j = 1; n >= j; ++j)
		{
			int x;
			cin >> x;
			if (x == 1)
			{
				union_node(i, j);
			}
		}
	}

	int root;
	for (int i = 0; m > i; ++i)
	{
		int x;
		cin >> x;
		if (i == 0)
		{
			root = find(x);
		}
		else
		{
			if (root != find(x))
			{
				cout << "NO";
				return 0;
			}
		}
	}
	cout << "YES";

	return 0;
}
