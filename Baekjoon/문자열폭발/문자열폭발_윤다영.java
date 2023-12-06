import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        String str = br.readLine();
        String bombStr = br.readLine();

        for(char c : str.toCharArray()) {
            sb.append(c);
            if(sb.length() < bombStr.length()) continue;
            if(sb.substring(sb.length() - bombStr.length()).equals(bombStr)) {
                sb.delete(sb.length() - bombStr.length(), sb.length());
            }
        }

        System.out.println(sb.length() == 0 ? "FRULA" : sb.toString());
    }
}