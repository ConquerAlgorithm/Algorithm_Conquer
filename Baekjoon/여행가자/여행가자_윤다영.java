import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    static int N, M;
    static int[] parents;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        N = Integer.parseInt(br.readLine());
        M = Integer.parseInt(br.readLine());
        parents = new int[N+1];
        //자신의 부모노드를 자신의 값으로 세팅
        for(int i=1;i<=N;i++){
            parents[i] = i;
        }
        for(int i=1; i<=N; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=1; j<=N; j++) {
                int num = Integer.parseInt(st.nextToken());
                if(num == 1) {
                    Union(i, j);
                }
            }
        }
        st = new StringTokenizer(br.readLine());
        int parent = Find(Integer.parseInt(st.nextToken()));
        for(int i=0; i<M-1; i++) {
            int now = Integer.parseInt(st.nextToken());
            if(parent != Find(now)) {
                System.out.println("NO");
                System.exit(0);
            }
        }
        System.out.println("YES");
    }

    public static int Find(int v){
        if(v==parents[v]) return v;
        else return parents[v]=Find(parents[v]);
    }

    public static void Union(int a, int b){
        int fa=Find(a);
        int fb=Find(b);
        if(fa!=fb) parents[fa]=fb;
    }
}