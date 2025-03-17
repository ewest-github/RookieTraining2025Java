package Bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class WorkingHoursBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<Integer, String> startTimes = new HashMap<>(); // 各日の開始時刻を格納
    private Map<Integer, String> endTimes = new HashMap<>(); // 各日の終了時刻を格納
    
    
    private double totalHours; // 総合計実働時間

    // 開始時刻の設定（startHour, startMinuteを受け取り、時刻を設定）
    public void setStartTime(int day, String startHour, String startMinute) {
        String startTime = startHour + ":" + startMinute; // 時間と分を結合
        startTimes.put(day, startTime); // 日ごとの開始時刻を格納
    }

    // 終了時刻の設定（endHour, endMinuteを受け取り、時刻を設定）
    public void setEndTime(int day, String endHour, String endMinute) {
        String endTime = endHour + ":" + endMinute; // 時間と分を結合
        endTimes.put(day, endTime); // 日ごとの終了時刻を格納
    }

    // 開始時刻の取得
    public String getStartTime(int day) {
        return startTimes.getOrDefault(day, ""); // 指定した日の開始時刻を返す、なければ空文字を返す
    }

    // 終了時刻の取得
    public String getEndTime(int day) {
        return endTimes.getOrDefault(day, ""); // 指定した日の終了時刻を返す、なければ空文字を返す
    }

    // 実働時間の取得
    public double getTotalHours() {
        return totalHours; // 実働時間を返す
    }

    // 実働時間の設定
    public void setTotalHours(double totalHours) {
        this.totalHours = totalHours; // 実働時間を設定
    }
    
    
    
}
