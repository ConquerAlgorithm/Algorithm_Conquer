#include <iostream>
#include <algorithm>

using namespace std;

int main()
{
	int n;
	long long dp[1000001];

	ios::sync_with_stdio(false);
	cin.tie(NULL), cout.tie(nullptr);

	cin >> n;

	for (int i = 1; n >= i; ++i)
	{
		dp[i] = dp[i - 1] + 1;
		for (int j = 3; i > j; ++j)
		{
			dp[i] = max(dp[i], dp[i - j] * (j - 1));
		}
	}
	cout << dp[n] << endl;

	return 0;
}
