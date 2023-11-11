import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

class Solution {
    public int[] solution(int[] fees, String[] records) throws ParseException {
        Map<Integer, String> map = new HashMap<>();
        Map<Integer, Integer> diffMap = new HashMap<>();
        for(String s : records) {
            String[] arr = s.split(" ");
            int num = Integer.parseInt(arr[1]);
            if(arr[2].equals("IN")) {
                map.put(num, arr[0]);
            } else {
                long diffMin = calTime(map.get(num), arr[0]);
                map.put(num, String.valueOf(diffMin));
                diffMap.put(num, diffMap.getOrDefault(num,0) + (int)diffMin);
            }
        }

        for(int key : map.keySet()) {
            String s = map.get(key);
            if(s.contains(":")) {
                long diffMin = calTime(s, "23:59");
                diffMap.put(key, diffMap.getOrDefault(key,0) + (int)diffMin);
            }
        }
        List<Integer> list = new ArrayList<>(diffMap.keySet());
        list.sort(null);
        int[] answer = new int[list.size()];
        int index = 0;
        for(int key : list) {
            int diff = diffMap.get(key);
            if(diff <= fees[0]) {
                answer[index++] = fees[1];
            } else {
                int temp = (diff - fees[0]) / fees[2];
                if((diff - fees[0]) % fees[2] != 0) temp++;
                answer[index++] = fees[1] + temp * fees[3];
            }
        }
        return answer;
    }

    private long calTime(String in, String out) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date inTime = sdf.parse(in);
        Date outTime = sdf.parse(out);

        long inTimeMil = inTime.getTime();
        long outTimeMil = outTime.getTime();

        long diff = outTimeMil - inTimeMil;

        long diffMin = diff / (1000 * 60);
        return diffMin;
    }
}