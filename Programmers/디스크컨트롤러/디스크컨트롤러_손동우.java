import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Solution {
    private Job[] jobs;
    private PriorityQueue<Job> jobPq = new PriorityQueue<>(Comparator.comparingInt(job -> job.spendingTime));

    public int solution(int[][] jobs) {
        init(jobs);
        return getTurnaroundTime();
    }

    private void init(int[][] jobs) {
        this.jobs = new Job[jobs.length];
        for (int i = 0; i < jobs.length; i++) {
            this.jobs[i] = new Job(jobs[i]);
        }
        Arrays.sort(this.jobs, (job1, job2) -> {
            if (job1.requestTime == job2.requestTime) {
                return job1.spendingTime - job2.spendingTime;
            } else {
                return job1.requestTime - job2.requestTime;
            }
        });
    }

    private int getTurnaroundTime() {
        int turnaroundTime = 0;
        jobPq.offer(jobs[0]);
        int curTime = jobs[0].requestTime;
        int jobIndex = 1;
        while (jobIndex < jobs.length || !jobPq.isEmpty()) {
            if (jobPq.isEmpty()) {
                jobPq.offer(jobs[jobIndex]);
                curTime = jobs[jobIndex].requestTime;
                jobIndex++;
            } else {
                Job job = jobPq.poll();
                curTime += job.spendingTime;
                turnaroundTime += curTime - job.requestTime;
                while (jobIndex < jobs.length && jobs[jobIndex].requestTime <= curTime) {
                    jobPq.offer(jobs[jobIndex++]);
                }
            }
        }

        return turnaroundTime / jobs.length;
    }

    private class Job {
        int requestTime;
        int spendingTime;

        public Job(int[] job) {
            this.requestTime = job[0];
            this.spendingTime = job[1];
        }
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        int[][] jobs = {{0, 3}, {1, 9}, {2, 6}};
        int answer = s.solution(jobs);
        System.out.println("answer = " + answer);
    }
}