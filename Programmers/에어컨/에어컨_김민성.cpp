#include <string>
#include <vector>
#include <iostream>
#include <string>

using namespace std;

const int MAX = 1000001;
const int MAX_TEMP = 40, MIN_TEMP = -10, RANGE = MAX_TEMP - MIN_TEMP;



int solution(int temperature, int t1, int t2, int a, int b, vector<int> onboard) {
    if (t1 <= temperature && temperature <= t2) 
	{
		return 0;
	}

	temperature -= MIN_TEMP;
	t1 -= MIN_TEMP;
	t2 -= MIN_TEMP;

	vector<vector<int>> dp(onboard.size(), vector<int>(RANGE + 1, MAX));

	dp[0][temperature] = 0;

	for (int i = 0; onboard.size() - 1 > i; ++i)
	{
		for (int j = 0; RANGE >= j; ++j)
		{
			if ((onboard[i] == 1) && (t1 > j || t2 < j)) continue;

			int next = j;
			if ((temperature > j) && (RANGE > j)) next = j + 1;
			else if ((temperature < j) && (j > 0)) next = j - 1;
			dp[i + 1][next] = min(dp[i][j], dp[i + 1][next]);


			if (j > 0) dp[i + 1][j - 1] = min(dp[i][j] + a, dp[i + 1][j - 1]);
			if (RANGE > j) dp[i + 1][j + 1] = min(dp[i][j] + a, dp[i + 1][j + 1]);

			dp[i + 1][j] = min(dp[i][j] + b, dp[i + 1][j]);
		}
	}

	int answer = MAX;
	for (int i = 0; RANGE >= i; ++i)
	{
		if ((onboard[onboard.size() - 1] == 1) && (t1 > i || t2 < i)) continue;
		answer = min(answer, dp[onboard.size() - 1][i]);
	}

	return answer;
}

