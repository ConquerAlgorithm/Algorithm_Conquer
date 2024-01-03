#include <string>
#include <vector>
#include <algorithm>

using namespace std;

struct stNum
{
    int i;
    int c;
};
stNum dp[5000001];

bool cmp(stNum& a, stNum& b)
{
    if (a.c == b.c)
    {
        return a.i < b.i;
    }
    return a.c > b.c;
}

vector<int> solution(int e, vector<int> starts) {
    vector<int> answer;

    for (int i = 1; e >= i; ++i)
    {
        dp[i].i = i;
        dp[i].c++;
    }

    for (int i = 2; e >= i; ++i)
    {
        for (int j = 1; (e / i) >= j; ++j)
        {
            dp[i * j].c++;
        }
    }

    sort(dp + 1, dp + e + 1, cmp);

    for (auto& s : starts)
    {
        for (int i = 1; e >= i; ++i)
        {
            if ((dp[i].i >= s) && (dp[i].i <= e))
            {
                answer.push_back(dp[i].i);
                break;
            }
        }
    }

    return answer;
}
