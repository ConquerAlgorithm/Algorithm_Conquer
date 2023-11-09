import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int answer = Integer.MIN_VALUE;
        int[] dp = new int[n+1];
        int[] arr = new int[n+1];
        dp[1] = 1;
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i = 1; i <= n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        for(int i=1; i<=n; i++){
            int max = Integer.MIN_VALUE;
            for(int j=i-1; j>=0; j--){
                if(arr[j] < arr[i] && dp[j] > max) max = dp[j];
            }
            dp[i] = max + 1;
            answer = Math.max(answer, dp[i]);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(answer).append("\n");
        Stack<Integer> stack = new Stack<>();
        for(int i=n; i>=1; i--){
            if(dp[i] == answer){
                stack.push(arr[i]);
                answer--;
            }
        }
        while(!stack.isEmpty()) sb.append(stack.pop()).append(" ");
        System.out.println(sb);
    }
}