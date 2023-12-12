import java.util.Arrays;
import java.util.Comparator;

public class Solution {
    private Route[] routes;

    public int solution(int[][] routes) {
        init(routes);
        return countCameras();
    }

    private void init(int[][] routes) {
        this.routes = new Route[routes.length];
        for (int i = 0; i < routes.length; i++) {
            this.routes[i] = new Route(routes[i]);
        }
        Arrays.sort(this.routes, Comparator.comparingInt(route -> route.to));
    }

    private int countCameras() {
        int count = 1;
        int to = routes[0].to;
        for (Route route : routes) {
            if (route.from > to) {
                count++;
                to = route.to;
            }
        }
        return count;
    }


    private static class Route {
        int from;
        int to;

        public Route(int[] route) {
            this.from = route[0];
            this.to = route[1];
        }

        @Override
        public String toString() {
            return "Route{" +
                    "from=" + from +
                    ", to=" + to +
                    '}';
        }
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        int[][] routes = {{-20, -15}, {-14, -5}, {-18, -13}, {-5, -3}};
        int answer = s.solution(routes);
        System.out.println("answer = " + answer);
    }
}