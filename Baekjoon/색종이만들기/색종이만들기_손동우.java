import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    private static int N;
    private static int[][] grid;
    private static int whiteCount, blueCount;

    public static void main(String[] args) throws Exception {
        init();
        countPapers(0, N, 0, N);
        System.out.println(whiteCount);
        System.out.println(blueCount);
    }

    private static void countPapers(int y1, int y2, int x1, int x2) {
        int color = isAllSameColor(y1, y2, x1, x2);
        if (color == 0) {
            whiteCount++;
            return;
        } else if (color == 1) {
            blueCount++;
            return;
        }
        countPapers(y1, (y1 + y2) / 2, x1, (x1 + x2) / 2);
        countPapers(y1, (y1 + y2) / 2, (x1 + x2) / 2, x2);
        countPapers((y1 + y2) / 2, y2, x1, (x1 + x2) / 2);
        countPapers((y1 + y2) / 2, y2, (x1 + x2) / 2, x2);
    }

    private static int isAllSameColor(int y1, int y2, int x1, int x2) {
        int color = grid[y1][x1];
        for (int i = y1; i < y2; i++) {
            for (int j = x1; j < x2; j++) {
                if (grid[i][j] != color) {
                    return -1;
                }
            }
        }
        return color;
    }

    private static void init() throws Exception {
        System.setIn(new FileInputStream("input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        grid = new int[N][N];
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                grid[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }
}