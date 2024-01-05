#include <iostream>

using namespace std;

int N, T;

int main()
{
	ios::sync_with_stdio(0);
	cin.tie(0); cout.tie(0);

	cin >> T;

	for (int t = 1; T >= t; t++)
	{
		cin >> N;
		int count = 0, check = 0, num = N;
		while (++count)
		{
			num = N * count;
			while (num)
			{
				check |= (1 << (num % 10));
				num /= 10;
			}
			if (check == ((1 << 10) - 1))
			{
				cout << "#" << t << " " << count * N << endl;
				break;
			}
		}
	}
}
