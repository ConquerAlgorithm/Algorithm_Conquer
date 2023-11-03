import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        long[] arr = new long[1000001];

        arr[1] = 1;
        arr[2] = 2;
        arr[3] = 4;
        for(int i = 4; i < arr.length; i++)
            arr[i] = (arr[i - 1] + arr[i - 2] + arr[i - 3]) % 1000000009;

        StringBuilder sb = new StringBuilder();

        while(n-- > 0) {
            sb.append(arr[Integer.parseInt(br.readLine())]).append("\n");
        }
        System.out.println(sb);
    }
}
