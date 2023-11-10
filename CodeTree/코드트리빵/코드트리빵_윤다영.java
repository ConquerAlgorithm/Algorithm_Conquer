import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class Main {
    public static int[][] map, visited, check;
    public static int n, m, answer;
    public static List<Pair> campList;
    public static Map<Integer, Person> personList;
    public static Map<Integer, Pair> storeList;

    public static void main(String[] args) {
        init();
        while (true) {
            for (int key : personList.keySet()) {
                if (personList.get(key).isAlive && simulation(key))
                    break;
            }
            answer++;

            boolean flag = true;
            for (Person person : personList.values()) {
                if (person.isAlive) {
                    flag = false;
                    break;
                }
            }
            if (flag)
                break;
        }


        System.out.println(answer);
    }

    private static boolean simulation(int index) {
        Person person = personList.get(index);
        if (!person.isStart) {
            findCamp(index);
            person.isStart = true;
            return true;
        } else {
            movePerson(index);
            return false;
        }
    }

    private static void movePerson(int key) {
        Person person = personList.get(key);
        Pair target = storeList.get(key);

        // 우선순위 이상함
        int[] dx = {0, -1, 1, 0};
        int[] dy = {-1, 0, 0, 1};
        int index = 0, min = Integer.MAX_VALUE;
        calDis(target);
        for (int i = 0; i < 4; i++) {
            int nx = person.pair.x + dx[i];
            int ny = person.pair.y + dy[i];
            if (!outRange(nx, ny) && visited[ny][nx] == 1 && min > check[ny][nx]) {
                min = check[ny][nx];
                index = i;
            }
        }
        person.pair.x = person.pair.x + dx[index];
        person.pair.y = person.pair.y + dy[index];
        if(person.pair.x == target.x && person.pair.y == target.y) {
            person.isAlive = false;
            map[person.pair.y][person.pair.x] = 10;
        }
    }

    private static boolean outRange(int x, int y) {
        return n <= x || n <= y || x < 0 || y < 0 || map[y][x] == 10;
    }

    private static void findCamp(int key) {
        Pair store = storeList.get(key);
        int min = Integer.MAX_VALUE;

        calDis(store);
        int minX = 0, minY = 0;
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if( visited[i][j] == 1 && check[i][j] < min && map[i][j] == 1) {
                    min = check[i][j];
                    minX = j; minY = i;
                }
            }
        }
        Person person = personList.get(key);
        person.pair.x = minX;
        person.pair.y = minY;
        map[person.pair.y][person.pair.x] = 10;
    }

    private static void calDis(Pair pair1) {
        Queue<Pair> queue = new LinkedList<>();
        queue.add(pair1);
        int[] dx = {0, -1, 1, 0};
        int[] dy = {-1, 0, 0, 1};
        visited = new int[n][n];
        check = new int[n][n];
        check[pair1.y][pair1.x] = 0;
        visited[pair1.y][pair1.x] = 1;
        while (!queue.isEmpty()) {
            Pair pair = queue.poll();
            for (int i = 0; i < 4; i++) {
                int nx = pair.x + dx[i];
                int ny = pair.y + dy[i];
                if (outRange(nx, ny) || visited[ny][nx] == 1)
                    continue;
                visited[ny][nx] = 1;
                check[ny][nx] = check[pair.y][pair.x] + 1;
                queue.add(new Pair(nx, ny));
            }
        }
    }

    private static void init() {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        m = sc.nextInt();

        map = new int[n][n];
        storeList = new HashMap<>();
        campList = new ArrayList<>();
        personList = new ConcurrentHashMap<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                map[i][j] = sc.nextInt();
                if (map[i][j] == 1)
                    campList.add(new Pair(j, i));
            }
        }

        for (int i = 0; i < m; i++) {
            int y = sc.nextInt() - 1;
            int x = sc.nextInt() - 1;
            map[y][x] = 2;
            storeList.put(i + 1, new Pair(x, y));
            personList.put(i + 1, new Person(new Pair(-1, -1)));
        }
    }
}

class Pair {
    int x, y;

    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

class Person {
    Pair pair;
    boolean isStart;
    boolean isAlive;

    public Person(Pair pair) {
        this.pair = pair;
        this.isStart = false;
        this.isAlive = true;
    }
}