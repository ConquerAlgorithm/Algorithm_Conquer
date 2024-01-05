#include <string>
#include <vector>

using namespace std;

string solution(string number, int k) {
    string answer = "";

    int size = number.size() - k;
    int start = 0;

    for (int i = 0; size > i; ++i)
    {
        char maxNumber = number[start];
        int maxIndex = start;
        for (int j = start; k + i >= j; ++j)
        {
            if (maxNumber < number[j])
            {
                maxNumber = number[j];
                maxIndex = j;
            }
        }
        start = maxIndex + 1;
        answer += maxNumber;
    }

    return answer;
}
