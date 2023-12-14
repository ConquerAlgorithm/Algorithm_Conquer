#include <string>
#include <vector>

using namespace std;

bool dfs(vector<vector<int>>& computers, int n)
{
    if (!computers[n][n])
    {
        return false;
    }
	computers[n][n] = 0;
    for (int i = 0; computers.size() > i; ++i)
    {
        if (computers[n][i])
        {
			dfs(computers, i);
		}
    }
    return true;
}

int solution(int n, vector<vector<int>> computers) {
    int answer = 0;
    for (int i = 0; n > i; ++i)
    {
        if (computers[i][i] && dfs(computers, i))
        {
			++answer;
		}
    }
    return answer;
}
