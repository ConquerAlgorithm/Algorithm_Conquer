
//https://velog.io/@pppp0722/%ED%94%84%EB%A1%9C%EA%B7%B8%EB%9E%98%EB%A8%B8%EC%8A%A4-Level3-%EC%82%AC%EB%9D%BC%EC%A7%80%EB%8A%94-%EB%B0%9C%ED%8C%90-Java
class Solution {
    int[][] map;
    int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public int solution(int[][] board, int[] aloc, int[] bloc) {
        map = board;
        return dfs(aloc[0], aloc[1], bloc[0], bloc[1]);
    }

    private int dfs(int x1, int y1, int x2, int y2) {
        if(map[x1][y1] == 0) return 0;
        int ret = 0;
        for(int[] dir : dirs) {
            int nx = x1 + dir[0];
            int ny = y1 + dir[1];
            if(nx < 0 || nx >= map.length || ny < 0 || ny >= map[0].length || map[nx][ny] == 0) continue;

            map[x1][y1] = 0;
            int temp = dfs(x2, y2, nx, ny) + 1;
            map[x1][y1] = 1;

            if(ret % 2 == 0 && temp % 2 == 1) // 현재 턴이 지는 경우, 새로 계산 된 턴이 이기면 교체
                ret = temp;
            else if(ret % 2 == 0 && temp % 2 == 0) // 모두 지는 경우, 최대한 늦게 지는 걸 선택
                ret = Math.max(ret, temp);
            else if(ret % 2 == 1 && temp % 2 == 1) // 모두 이기는 경우, 최대한 빨리 이기는걸 선택
                ret = Math.min(ret, temp);

        }
        return ret;
    }
}