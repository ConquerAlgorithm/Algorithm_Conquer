import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

class Solution {

    static class Node implements Comparable<Node> {
        int x;
        int y;
        int dir;
        int answer;

        public Node(int x, int y, int dir, int answer) {
            this.x = x;
            this.y = y;
            this.dir = dir;
            this.answer = answer;
        }

        @Override
        public int compareTo(Node o) {
            return this.answer - o.answer;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int w = Integer.parseInt(st.nextToken());
        int h = Integer.parseInt(st.nextToken());
        char[][] board = new char[h][w];
        int startX = 0, startY = 0, endX = 0, endY = 0;
        for(int i = 0; i < h; i++) {
            char[] str = br.readLine().toCharArray();
            for(int j = 0; j < w; j++) {
                board[i][j] = str[j];
                if(str[j] == 'C') {
                    startX = i;
                    startY = j;
                }
            }
        }
        board[startX][startY] = 'S';

        Queue<Node> q = new PriorityQueue<>();
        q.add(new Node(startX, startY, -1, 0));
        int[][] visited = new int[h][w];

        for(int i = 0; i < h; i++) Arrays.fill(visited[i], Integer.MAX_VALUE);

        visited[startX][startY] = 0;
        int[][] dir = {{0,1},{1,0},{0,-1},{-1,0}};
        while(!q.isEmpty()) {
            Node cur = q.poll();
            if(board[cur.x][cur.y] == 'C') {
                System.out.println(cur.answer);
                return;
            }
            for(int i = 0; i < 4; i++) {
                int nx = cur.x + dir[i][0];
                int ny = cur.y + dir[i][1];
                if(nx < 0 || nx >= h || ny < 0 || ny >= w || board[nx][ny] == '*') continue;
                int temp = cur.answer;
                if(cur.dir != -1 && cur.dir != i) temp++;
                if(visited[nx][ny] < temp) continue;
                visited[nx][ny] = temp;
                q.add(new Node(nx, ny, i, temp));
            }
        }
    }

}