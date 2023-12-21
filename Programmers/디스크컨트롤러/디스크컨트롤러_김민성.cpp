#include <queue>
#include <vector>
#include <algorithm>

using namespace std;

struct cmp 
{
	bool operator()(vector<int> a, vector<int> b)
	{
		return a[1] > b[1];
	}

};

int solution(vector<vector<int>> jobs) {
	int answer = 0, currentTime = 0, count = 0;
	int n = jobs.size();
	priority_queue<vector<int>, vector<vector<int>>, cmp> pq;

	sort(jobs.begin(), jobs.end());

	while ((count < n) || !pq.empty())
	{
		if ((count < n) && (jobs[count][0] <= currentTime))
		{
			pq.push(jobs[count++]);
			continue;
		}

		if (!pq.empty())
		{
			currentTime += pq.top()[1];
			answer += currentTime - pq.top()[0];
			pq.pop();
		}
		else
		{
			currentTime = jobs[count][0];
		}
	}

	return answer / n;
}
