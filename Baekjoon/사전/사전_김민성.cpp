#include <iostream>

using namespace std;

int n, m, k;
long long dp[101][101] = { 0 };

int main()
{
	ios::sync_with_stdio(0);
	cin.tie(0); cout.tie(0);

	cin >> n >> m >> k;

	for (int i = 0; 100 > i; ++i)
	{
		dp[i + 1][0] = 1;
		dp[0][i + 1] = 1;
	}

	for (int i = 1; i <= 100; ++i)
	{
		for (int j = 1; j <= 100; ++j)
		{
			dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
			if (dp[i][j] > 1000000000) dp[i][j] = 1000000000;
		}
	}


	if (dp[n][m] < k)
	{
		cout << -1;
		return 0;
	}

	int a = n, z = m;


	for (int i = 0; n + m > i; ++i)
	{
		int as = dp[a - 1][z];
		int zs = dp[a][z - 1];

		if (a == 0)
		{
			cout << "z";
			z--;
			continue;
		}
		else if (z == 0)
		{
			cout << "a";
			a--;
			continue;
		}
		if (k <= as)
		{
			cout << "a";
			a--;
		}
		else
		{
			k = k - as;
			cout << "z";
			z--;
		}
	}

	return 0;
}
