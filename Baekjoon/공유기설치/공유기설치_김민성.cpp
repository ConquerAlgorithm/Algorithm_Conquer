#include <iostream>
#include <algorithm>

using namespace std;

int n, c, arr[200000], answer;

int main()
{
	ios::sync_with_stdio(false);
	cin.tie(NULL), cout.tie(nullptr);

	cin >> n >> c;

	for (int i = 0; n > i; ++i)
	{
		cin >> arr[i];
	}
	
	sort(arr, arr + n);

	int l = 0, r = arr[n - 1] - arr[0];

	while (l <= r)
	{
		int mid = (l + r) / 2;
		int count = 1;
		bool flag = false;

		for (int i = 0; n > i; ++i)
		{
			if (c == count)
			{
				break;
			}
			for (int j = i; n > j; ++j)
			{
				if ((arr[j] - arr[i]) >= mid)
				{
					i = j - 1;
					flag = true;
					count++;
					break;
				}
				else
				{
					flag = false;
				}
			}
		}
		if (flag)
		{
			l = mid + 1;
			answer = mid;
		}
		else
		{
			r = mid - 1;
		}
	}

	cout << answer << endl;

	return 0;
}
