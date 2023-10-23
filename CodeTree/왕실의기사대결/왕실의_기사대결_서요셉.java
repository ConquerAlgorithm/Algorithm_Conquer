package CodeTree.삼성스터디;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * - 개체
 *  1. 격자
 *      - L * L 크기
 *      - 좌상단 (1, 1)
 *      - 빈칸 : 0, 함정 : 1, 벽 : 2으로 구성. 격자 밖도 벽으로 간주
 *
 *  2. 기사
 *      - 마력으로 상대방을 밀쳐낼 수 있음.
 *      - 초기 위치는 (r, c)로 주어짐.
 *      - (r, c)를 좌상단으로 하며 h(높이) * w(너비) 크기의 직사각형 형태를 띄고 있음.
 *      - 각 기사의 체력은 k 로 주어짐
 *
 *
 * - 진행
 *  1. 기사 이동
 *      - 상하좌우 사방 중 하나로 한 칸 이동한다.
 *      - 만약 이동하려는 위치에 다른 기사가 있다면 그 기사도 함께 연쇄적으로 한칸 밀려난다.
 *      - 그 옆에 또 기사가 있다면 연쇄적으로 한 칸씩 밀린다.
 *      - 만약 기사가 이동하려는 방향의 끝에 벽이 있다면 모든 기사는 이동할 수 없다.
 *      - 체스판에서 사라진 기사에게 명령을 내리면 아무런 반응이 없다.
 *
 *  2. 대결 데미지
 *      - 밀려난 기사들은 피해를 입는다.
 *      - 이 때, 각 기사들은 해당 기사가 이동한 곳에서 w * h 직사각형 내에 놓여있는 함정 수만큼 피해를 입는다.
 *      - 피해를 입은 만큼 체력이 깎이고 현재 체력 이상의 데미지를 받는 경우 기사는 체스판에서 사라진다.
 *      - 단 명령을 받은 기사는 피해를 입지 않는다. 기사들은 모두 밀린 이후에 데미지를 입는다.
 *      - 밀렸더라도 위치에 함정이 전혀 없다면 그 기사는 피해를 전혀 입지 않는다.
 *
 * - 정답
 *  - Q 번에 걸쳐 왕의 명령이 주어질때 Q 번의 대결 후 생존한 기사들이 받은 데미지의 합을 출력하라.
 *
 */
public class 왕실의_기사대결 {

    static class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static class Night extends Point {
        int id, h, w, hp, damage;
        boolean isAlive;
        List<Point> pointList;

        public Night(int x, int y, int id, int h, int w, int hp, int damage) {
            super(x, y);
            this.id = id;
            this.h = h;
            this.w = w;
            this.hp = hp;
            this.damage = damage;
            this.pointList = new ArrayList<>();
            this.isAlive = true;

            setSelf();
            setBoard();
        }

        // 본인 영역 설정
        public void setSelf() {
            this.pointList.clear();

            for (int i = x; i < x + h; i++) {
                for (int j = y; j < y + w; j++) {
                    this.pointList.add(new Point(i, j));
                }
            }
        }

        // 본인 영역 마킹
        public void setBoard() {
            for (Point p: pointList) {
                int x = p.x;
                int y = p.y;

                nightBoard[x][y] = id;
            }
        }

        // 이전 영역 비우기
        public void setBlank() {
            for (Point p: pointList) {
                int x = p.x;
                int y = p.y;

                nightBoard[x][y] = 0;
            }
        }

        @Override
        public String toString() {
            return id + " 번 기사 좌표 : ( " +
                    x +
                    ", " + y +
                    " ), id=" + id +
                    ", h=" + h +
                    ", w=" + w +
                    ", hp=" + hp +
                    ", damage=" + damage +
                    ", isAlive=" + isAlive +
                    '}';
        }

        public void toStringPointList() {
            System.out.print("  pointList : ");
            for (Point p: pointList) {
                System.out.print("( " + p.x + ", " + p.y + " )  ");
            }
            System.out.println();
        }
    }

    static int L, N, Q;

    static int[][] chessBoard;
    static int[][] nightBoard;
    static Night[] nightPool;
    static Night orderedNight;

    // 기사들이 이동해야 하는 순서를 위해 linkedHashSet 사용
    static LinkedHashSet<Night> movingNightSet;

    // 상 우 하 좌
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, 1, 0, -1};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        L = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());
        Q = Integer.parseInt(st.nextToken());

        chessBoard = new int[L + 1][L + 1];
        nightBoard = new int[L + 1][L + 1];
        nightPool = new Night[N + 1];

        // 격자 상태 입력
        for (int i = 1; i <= L; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= L; j++) {
                chessBoard[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 기사 입력
        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());

            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int h = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            int hp = Integer.parseInt(st.nextToken());

            Night newNight = new Night(x, y, i, h, w, hp, 0);
            nightPool[i] = newNight;
        }

        // 명령 입력
        for (int i = 0; i < Q; i++) {
            st = new StringTokenizer(br.readLine());

            int id = Integer.parseInt(st.nextToken());
            int dir = Integer.parseInt(st.nextToken());

            process(id, dir);
        }

        // 살아남은 기사들의 데미지를 출력
        System.out.println(getDamage());
    }

    static int getDamage() {
        int sum = 0;

        for (int i = 1; i <= N; i++) {
            if (nightPool[i].isAlive) {
                sum += nightPool[i].damage;
            }
        }

        return sum;
    }

    static void process(int id, int dir) {
        // 명령 받은 기사
        orderedNight = nightPool[id];

        // 명령 받은 기사가 죽은 기사라면 영향이 없다.
        if (!orderedNight.isAlive) return;

        movingNightSet = new LinkedHashSet<>();

        if (checkMove(orderedNight, dir)) {
            // 이동할 기사들에 대해 실제 이동 로직을 실행.
            move(dir);

            // 이동하고 난후 데미지 계산
            calDamage();
        }
    }

    static void calDamage() {
        // 밀린 기사들은 밟은 함정만큼 데미지를 입는다.
        for (Night n : movingNightSet) {
            int damage = 0;
            // 명령 받은 기사는 데미지를 입지 않는다.
            if (n.equals(orderedNight)) continue;

            for (Point p: n.pointList) {
                int x = p.x;
                int y = p.y;

                if (chessBoard[x][y] == 1) damage++;
            }

            n.damage += damage;
            n.hp = Math.max(0, n.hp - damage);
            n.isAlive = n.hp != 0;

            if (!n.isAlive) n.setBlank();
        }
    }

    static void move(int dir) {
        for (Night n : movingNightSet) {
            // 이동 전 자신의 영역 0 마킹
            n.setBlank();

            // 좌상단 영역 이동
            n.x += dx[dir];
            n.y += dy[dir];

            // 자기 자신 영역 변경
            n.setSelf();
            n.setBoard();
        }
    }

    static boolean checkMove(Night night, int dir) {
        List<Point> pointList = night.pointList;

        for (Point p : pointList) {
            int nx = p.x + dx[dir];
            int ny = p.y + dy[dir];

            // 경계선 밖이거나 벽이면
            if (isNotBoundary(nx, ny) || chessBoard[nx][ny] == 2) return false;
            // 자기 자신이면
            if (nightBoard[nx][ny] == night.id) continue;
            // 살아있는 다른 기사가 있고 그 기사가 이동이 불가하면
            if (nightBoard[nx][ny] != 0 && nightPool[nightBoard[nx][ny]].isAlive && !checkMove(nightPool[nightBoard[nx][ny]], dir)) return false;
        }

        movingNightSet.add(night);
        return true;
    }

    static boolean isNotBoundary(int x, int y) {
        return !(1 <= x && x <= L && 1 <= y && y <= L);
    }

}
