import java.io.IOException;
import java.util.*;

class cd{
    String genre;
    int play;
    int index;
    cd(String genre, int play, int index){
        this.genre = genre;
        this.play = play;
        this.index = index;
    }
}

class Solution {
    public int[] solution(String[] genres, int[] plays) {
        Map<String, Integer> map = new HashMap<>();
        for(int i = 0; i < genres.length; i++){
            map.put(genres[i], map.getOrDefault(genres[i], 0)+ plays[i]);
        }

        List<String> list = new ArrayList<>(map.keySet());
        list.sort((o1, o2) -> {
            return map.get(o2) - map.get(o1);
        });


        List<cd> result = new ArrayList<>();
        for(String key : list){
            List<cd> temp = new ArrayList<>();
            for(int i = 0; i < genres.length; i++){
                if(genres[i].equals(key)){
                    temp.add(new cd(genres[i], plays[i], i));
                }
            }
            Collections.sort(temp, (o1, o2) -> o2.play - o1.play);
            result.add(temp.get(0));
            if(temp.size() > 1){
                result.add(temp.get(1));
            }
        }

        int[] answer = new int[result.size()];
        for(int i = 0; i < result.size(); i++){
            answer[i] = result.get(i).index;
        }

        return answer;
    }
}