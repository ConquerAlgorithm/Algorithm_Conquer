#include <string>
#include <vector>
#include <unordered_map>
#include <set>

using namespace std;

vector<int> solution(vector<string> gems) {
    vector<int> answer(2);
    unordered_map<string, int> um;
    set<string> num(gems.begin(), gems.end());
    int min, start = 0, end = 0;

    for (auto& s : gems)
    {
        um[s]++;
        if (um.size() == num.size()) break;
        end++;
    }

    min = end - start;
    answer[0] = start + 1;
    answer[1] = end + 1;

    while (end < gems.size())
    {
        string key = gems[start];
        um[key]--;
        start++;

        if (um[key] == 0)
        {
            end++;
            for (; end < gems.size(); ++end)
            {
                um[gems[end]]++;
                if (key == gems[end])
                {
                    break;
                }
            }
        }
        if (min > (end - start))
        {
            answer[0] = start + 1;
            answer[1] = end + 1;
            min = end - start;
        }
    }

    return answer;
}
