#include <string>
#include <vector>
#include <queue>
#include <algorithm>

using namespace std;

int straight = 100;
int corner = 600;

struct Node 
{
	int r; int c; int cost; bool flag;
};

int dr[4] = { -1,0,1,0 };
int dc[4] = { 0,-1,0,1 };

int BFS(vector<vector<int>> board) 
{
	int end = board.size() - 1;
	int N = board.size();

	vector<vector<vector<int>>> visited(N, vector<vector<int>>(N, vector<int>(N, 987654321)));
	queue<Node> q;
	visited[0][0][0] = 0;
	visited[0][0][1] = 0;

	if (board[1][0] == 0) 
	{
		visited[1][0][0] = 100;
		q.push({ 1, 0, 100, 0 });
	}
	if (board[0][1] == 0) 
	{
		visited[0][1][1] = 100;
		q.push({ 0, 1, 100, 1 });
	}

	while (!q.empty()) 
	{
		Node temp = q.front();
		q.pop();

		for (int i = 0; i < 4; i++) 
		{
			int newR = temp.r + dr[i];
			int newC = temp.c + dc[i];
			int newCost = temp.cost;
			bool newFlag = temp.flag;
			if (i % 2 == (int)temp.flag) 
			{
				newCost += straight;
			}
			else 
			{
				newCost += corner;
				newFlag = !newFlag;
			}
			if (newR < 0 || newR >= N || newC < 0 || newC >= N) continue;
			if (board[newR][newC] == 1) continue;
			if (visited[newR][newC][newFlag] < newCost) continue;
			visited[newR][newC][newFlag] = newCost;
			q.push({ newR, newC, newCost, newFlag });
		}
	}

	return min(visited[end][end][0], visited[end][end][1]);
}

int solution(vector<vector<int>> board) {
	int answer = 0;
	answer = BFS(board);
	return answer;
}
