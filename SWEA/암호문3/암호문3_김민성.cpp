#include <iostream>
#include <list>

using namespace std;

int N;
list<int> code;

int main() 
{
    ios::sync_with_stdio(false);
    cin.tie(0); cout.tie(false);
    int T;
    int test_case;

    T = 10;
    for (test_case = 1; test_case <= T; ++test_case)
    {
        N = 0;
        code.clear();

        cin >> N;
        for (int i = 0; i < N; i++) {
            int num = 0;
            cin >> num;
            code.push_back(num);
        }
        cin >> N;
        for (int i = 0; i < N; i++) 
        {
            char cmd;
            int x, y, s;
            cin >> cmd;
            if (cmd == 'I') 
            {
                cin >> x >> y;
                list<int> add;
                for (int j = 0; j < y; ++j) 
                {
                    cin >> s;
                    add.push_back(s);
                }
                auto iter = code.begin();
                while (x--) iter++;
                code.splice(iter, add);
            }
            else if (cmd == 'D') 
            {
                cin >> x >> y;
                auto iter = code.begin();
                while (x--) iter++;
                while (y--) iter = code.erase(iter);
            }
            else 
            {
                cin >> y;
                for (int j = 0; j < y; j++) 
                {
                    cin >> s;
                    code.push_back(s);
                }
            }
        }
        cout << "#" << test_case << " ";
        for (int i = 0; i < 10; i++) 
        {
            cout << code.front() << " ";
            code.pop_front();
        }
        cout << endl;
    }

    return 0;
}
