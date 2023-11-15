package Programmers.LV3.주차요금계산;
import java.util.*;

class Solution {
    public int[] solution(int[] fees, String[] records) {
        int[] answer = {};

        Map<String, String> map = new HashMap<>();          // 차 번호, 입차 시간(ex - 00:00)
        Map<String, Integer> timeMap = new HashMap<>();     // 차 번호, 주차 시간

        for (String r : records) {
            timeMap.put(r.split(" ")[1], 0);
        }

        for (String r : records) {
            String[] infos = r.split(" ");

            if (map.containsKey(infos[1])) {
                String[] inTime = map.remove(infos[1]).split(":");
                String[] outTime = infos[0].split(":");

                int hour = Integer.parseInt(outTime[0]) - Integer.parseInt(inTime[0]);
                int minute = Integer.parseInt(outTime[1]) - Integer.parseInt(inTime[1]);

                timeMap.put(infos[1], timeMap.get(infos[1]) + 60 * hour + minute);
            } else {
                map.put(infos[1], infos[0]);
            }
        }

        for (String key : map.keySet()) {
            String[] inTime = map.get(key).split(":");

            int hour = 23 - Integer.parseInt(inTime[0]);
            int minute = 59 - Integer.parseInt(inTime[1]);

            timeMap.replace(key, timeMap.get(key) + 60 * hour + minute);
        }

        List<Map.Entry<String, Integer>> list = new ArrayList<>(timeMap.entrySet());
        Collections.sort(list, (o1, o2) -> {
            return Integer.compare(Integer.parseInt(o1.getKey()), Integer.parseInt(o2.getKey()));
        });

        answer = new int[list.size()];

        for (int i = 0; i < answer.length; i++) {
            if (list.get(i).getValue() > fees[0]) {
                answer[i] = fees[1] + (int)Math.ceil((list.get(i).getValue() - fees[0]) / (double)fees[2] ) * fees[3];
            } else {
                answer[i] = fees[1];
            }
        }

        return answer;
    }
}