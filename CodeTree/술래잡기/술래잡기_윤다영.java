
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static List<Runner> runnerList;
    static List<Pair> treeList;
    static Pair tagger;
    static int answer = 0, n, m, h, k, moveCnt = 0, tagDisIndex = 0, flag = 1, tagCnt = 1, changeDis = 0;
    static int dx[] = {-1, 0, 1, 0};
    static int dy[] = {0, 1, 0, -1};
    public static void main(String[] args) throws IOException {
        init();

        for(int i = 1; i <= k; i++) {
            runnerRun();
            taggerRun();
            int cnt = isTag();
            answer += i * cnt;
            clear();
            if(runnerList.size() == 0) break;
            System.out.println("tagger : " + tagger.x + " " + tagger.y);
            for(Runner runner : runnerList) {
                System.out.println("runner : " + runner.pair.x + " " + runner.pair.y);
            }
            System.out.println("answer: " + answer);
        }
        System.out.println(answer);
    }

    private static void clear() {
        for(int i = 0; i < runnerList.size(); i++) {
            if(!runnerList.get(i).isAlive) runnerList.remove(i--);
        }
    }

    private static int isTag() {
        int cnt = 0;
        for(Runner runner : runnerList) {
            if(!runner.isAlive) continue;
            boolean isTree = false;
            for(Pair tree : treeList) {
                if(tree.x == runner.pair.x && tree.y == runner.pair.y){
                    isTree = true;
                    break;
                }
            }
            if(isTree) continue;

            for(int i = 0; i <= 2; i++) {
                int tagDistX = tagger.x + dx[tagDisIndex] * i;
                int tagDistY = tagger.y + dy[tagDisIndex] * i;
                if(tagDistX == runner.pair.x && tagDistY == runner.pair.y) {
                    cnt++;
                    runner.isAlive = false;
                    break;
                }
            }
        }
        return cnt;
    }

    private static void taggerRun() {
        tagger.x += dx[tagDisIndex];
        tagger.y += dy[tagDisIndex];

        // 걸음 수
        moveCnt++;

        if(flag == 1 && moveCnt == tagCnt){
            tagDisIndex = (tagDisIndex + 1) % 4;
            changeDis++;
            if(changeDis == 2) {
                tagCnt++;
                changeDis = 0;
            }
            moveCnt = 0;
        }
        else if(flag == -1 && moveCnt == tagCnt * 2){
            tagDisIndex = (tagDisIndex + 3) % 4;
            if(changeDis == 2) {
                tagCnt++;
                changeDis = 0;
            }
            moveCnt = 0;
        }

        if(tagger.x == 0 && tagger.y == 0) flag = -1;
        else if(tagger.x == n/2 && tagger.y == n/2) flag = 1;
    }

    private static void runnerRun() {
        for(Runner runner : runnerList) {
            if(calDist(runner.pair.x, runner.pair.y, tagger.x, tagger.y) > 3 || !runner.isAlive) continue;
            // 좌우
            if(runner.dis == 1 || runner.dis == 3) {
                if(outRange(runner.pair.x + dx[runner.disIndex], runner.pair.y + dy[runner.disIndex])) runner.disIndex = runner.disIndex == 1 ? 3 : 1;
            } else {
                if(outRange(runner.pair.x + dx[runner.disIndex], runner.pair.y + dy[runner.disIndex])) runner.disIndex = runner.disIndex == 2 ? 0 : 2;
            }
            int nx = runner.pair.x + dx[runner.disIndex];
            int ny = runner.pair.y + dy[runner.disIndex];
            if(nx == tagger.x && ny == tagger.y) continue;
            runner.pair.x = nx;
            runner.pair.y = ny;
        }
    }

    private static int calDist(int x1, int y1, int x2, int y2){
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    private static boolean outRange(int x, int y) {
        return x < 0 || y < 0 || x >= n || y >= n;
    }

    private static void init() {
        Scanner in = new Scanner(System.in);
        n = in.nextInt();
        m = in.nextInt();
        h = in.nextInt();
        k = in.nextInt();
        runnerList = new ArrayList<>();
        treeList = new ArrayList<>();
        for(int i = 0; i < m; i++) {
            runnerList.add(new Runner(new Pair(in.nextInt() - 1, in.nextInt() - 1), in.nextInt()));
        }

        for(int i = 0; i < h; i++) {
            treeList.add(new Pair(in.nextInt() - 1, in.nextInt() - 1));
        }
        tagger = new Pair(n/2, n/2);
    }
}

class Runner {
    Pair pair;
    // 1 : 좌우
    // 2: 상하
    int dis;
    int disIndex;
    boolean isAlive = true;

    public Runner(Pair pair, int dis) {
        this.pair = pair;
        this.dis = dis;
        this.disIndex = dis == 1 ? 1 : 2;
    }
}

class Pair {
    int x;
    int y;
    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }
}