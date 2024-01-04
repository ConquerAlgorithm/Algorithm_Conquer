package SWEA.D3.SW_1230_암호문3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Solution {

    static int N, M;
    static List<Integer> list;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = 10;
        for (int t = 1; t <= T; t++) {
            N = Integer.parseInt(br.readLine());
            list = new ArrayList<>();

            // N 개의 암호문 list 에 추가하여 암호문 뭉치 생성
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) {
                list.add(Integer.parseInt(st.nextToken()));
            }

            M = Integer.parseInt(br.readLine());

            // M 개의 명령어 처리
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < M; i++) {

                String command = st.nextToken();

                switch (command) {
                    case "I":
                        int x = Integer.parseInt(st.nextToken());
                        int y = Integer.parseInt(st.nextToken());

                        // y 개의 암호문 tmpList 에 추가 (뒤집기 위함)
                        List<Integer> tmpList = new ArrayList<>();
                        for (int j = 0; j < y; j++) {
                            tmpList.add(Integer.parseInt(st.nextToken()));
                        }

                        // 뒤집어서 원래 list 에 추가
                        for (int j = tmpList.size() - 1; j >= 0; j--) {
                            list.add(x, tmpList.get(j));
                        }

                        break;
                    case "D" :
                        x = Integer.parseInt(st.nextToken());
                        y = Integer.parseInt(st.nextToken());

                        // 만약 y 개가 list.size() - x 보다 크면 list.size() - x 개 만큼 삭제한다.
                        y = Math.min(y, (list.size() - x));
                        for (int j = 0; j < y; j++) {
                            list.remove(x + 1);
                        }

                        break;
                    case "A":
                        y = Integer.parseInt(st.nextToken());

                        for (int j = 0; j < y; j++) {
                            list.add(Integer.parseInt(st.nextToken()));
                        }
                        break;
                }
            }

            StringBuilder sb = new StringBuilder();
            sb.append("#").append(t).append(" ");
            for (int i = 0; i < 10; i++) {
                sb.append(list.get(i)).append(" ");
            }
            System.out.println(sb);
        }

    }

}
