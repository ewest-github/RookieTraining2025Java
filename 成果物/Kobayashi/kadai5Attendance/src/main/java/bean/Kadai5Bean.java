package bean;

import java.io.Serializable;

public class Kadai5Bean implements Serializable {
	// int:時計時間(時-分-秒)使うため
    private int[] startHourTimes = new int[31]; // 31日分の開始時刻(時間)
    private int[] endHourTimes = new int[31]; // 31日分の終了時刻(時間)
    private int[] startMinuteTimes = new int[31]; // 31日分の開始時刻(時間)
    private int[] endMinuteTimes = new int[31]; // 31日分の終了時刻(時間)
    private double[] workTimes = new double[31]; // 31日分の実働時間
    private double totalWorkTime; // 1ヶ月の実働総計

    public Kadai5Bean() { }

    public int getstartHourTimes(int day) { // 日付を入力
        return startHourTimes[day - 1]; // 配列は0から始まるから
    }
    public void setstartHourTimes(int day, int startHourTimes) {
        this.startHourTimes[day - 1] = startHourTimes;
    }

    public int getendHourTimes(int day) {
        return endHourTimes[day - 1];
    }
    public void setendHourTimes(int day, int endHourTimes) {
        this.endHourTimes[day - 1] = endHourTimes;
    }
    
    public int getStartMinuteTimes(int day) {
        return startMinuteTimes[day - 1];
    }
    public void setStartMinuteTimes(int day, int startMinuteTimes) {
        this.startMinuteTimes[day - 1] = startMinuteTimes;
    }

    public int getEndMinuteTimes(int day) {
        return endMinuteTimes[day - 1];
    }
    public void setEndMinuteTimes(int day, int endMinuteTimes) {
        this.endMinuteTimes[day - 1] = endMinuteTimes;
    }

    public double getWorkingTime(int day) {
        return workTimes[day - 1];
    }
    public void setWorkingTime(int day, double workingTime) {
        this.workTimes[day - 1] = workingTime;
    }
    
    public double gettotalWorkTime() {
        return totalWorkTime;
    }
    public void settotalWorkTime(double totalWorkTime) {
        this.totalWorkTime = totalWorkTime;
    }
    
    // 労働時間を加算
    public void addtotalWorkTime(int day) {
        this.totalWorkTime += workTimes[day - 1];
    }
}