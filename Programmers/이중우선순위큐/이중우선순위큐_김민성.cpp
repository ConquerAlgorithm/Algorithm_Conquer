#include <string>
#include <vector>
#include <algorithm>
#include <iostream>

using namespace std;

vector<int> solution(vector<string> operations) 
{
    vector<int> answer = { 0,0 };
    vector<int> v;

    for (auto& s : operations) 
    {
        string order = s.substr(0, 1);
        int num = stoi(s.substr(2));

        if (order == "I") 
        {
            v.push_back(num);
            sort(v.begin(), v.end(), greater<>());
        }
        else {
            if (v.size() == 0) continue;

            (num == -1) ? v.erase(v.end() - 1) : v.erase(v.begin());
        }
    }

    if (!v.empty()) 
    {
        answer.clear();
        answer = { v[0], v[v.size() - 1] };
    }

    return answer;
}
