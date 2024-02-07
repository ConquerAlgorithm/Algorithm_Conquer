package Programmers.LV3.광고삽입;
import java.util.*;

public class 광고삽입 {

    class Solution {
        public String solution(String play_time, String adv_time, String[] logs) {
            int playTime = getIntTime(play_time);
            int advTime = getIntTime(adv_time);

            int[] times = new int[360_000];

            for (String log : logs) {
                String[] strLog = log.split("-");

                int startTime = getIntTime(strLog[0]);
                int endTime = getIntTime(strLog[1]);

                for (int i = startTime; i < endTime; i++) {
                    times[i]++;
                }
            }

            int maxIdx = 0;
            long totalTime = 0;
            for (int i = 0; i < advTime; i++) {
                totalTime += times[i];
            }

            long maxTotalTime = totalTime;
            for (int i = advTime; i < playTime; i++) {
                totalTime += times[i] - times[i - advTime];

                if (totalTime > maxTotalTime) {
                    maxTotalTime = totalTime;
                    maxIdx = i - advTime + 1;
                }
            }

            return getStrTime(maxIdx);
        }

        int getIntTime(String time) {
            int[] HMS = Arrays.stream(time.split(":")).mapToInt(Integer::parseInt).toArray();

            return 3600 * HMS[0] + 60 * HMS[1] + HMS[2];
        }

        String getStrTime(int time) {
            int H = time / 3600;
            int M = (time - 3600 * H) / 60;
            int S = time - 3600 * H - 60 * M;

            return (H < 10 ? "0" : "") + H + ":" + (M < 10 ? "0" : "") + M + ":" + (S < 10 ? "0" : "") + S;
        }
    }
}
