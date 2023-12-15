#include <string>
#include <vector>
#include <map>

using namespace std;

vector<int> solution(vector<string> genres, vector<int> plays) {
    vector<int> answer;
    map<string, int> musicCount;
    map<string, map<int, int>> musicList;

    for (int i = 0; genres.size() > i; ++i)
    {
        musicCount[genres[i]] += plays[i];
        musicList[genres[i]][i] = plays[i];
    }

    while (musicCount.size() > 0)
    {
        string genre{};
        int maxCount{};

        for (auto& m : musicCount)
        {
            if (m.second > maxCount)
            {
                maxCount = m.second;
                genre = m.first;
            }
        }
        for (int i = 0; 2 > i; ++i)
        {
            int val = 0;
            int index = -1;

            for (auto& ml : musicList[genre])
            {
                if (ml.second > val)
                {
					val = ml.second;
					index = ml.first;
				}
            }
            if (index == -1) break;
            answer.push_back(index);
            musicList[genre].erase(index);
        }
        musicCount.erase(genre);
    }

    return answer;
}
