package beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class WorkTimeBean implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /** 日付ごとの開始時刻のマップ */
    private Map<Integer, String> mStartTimeMap;
    
    /** 日付ごとの終了時刻のマップ */
    private Map<Integer, String> mEndTimeMap;
    
    /** 実働時間の総計 */
    private double mTotalWorkHours;
    
    public WorkTimeBean() {
        mStartTimeMap = new HashMap<>();
        mEndTimeMap = new HashMap<>();
        mTotalWorkHours = 0.0;
        
        // デフォルト値を設定（空文字列）
        for (int i = 1; i <= 31; i++) {
            mStartTimeMap.put(i, "");
            mEndTimeMap.put(i, "");
        }
    }
    
    /**
     * 開始時刻のマップを取得する.
     * 
     * @return 開始時刻のマップ
     */
    public Map<Integer, String> getStartTimeMap() {
        return mStartTimeMap;
    }
    
    /**
     * 開始時刻のマップを設定する.
     * 
     * @param startTimeMap 開始時刻のマップ
     */
    public void setStartTimeMap(Map<Integer, String> startTimeMap) {
        this.mStartTimeMap = startTimeMap;
    }
    
    /**
     * 終了時刻のマップを取得する.
     * 
     * @return 終了時刻のマップ
     */
    public Map<Integer, String> getEndTimeMap() {
        return mEndTimeMap;
    }
    
    /**
     * 終了時刻のマップを設定する.
     * 
     * @param endTimeMap 終了時刻のマップ
     */
    public void setEndTimeMap(Map<Integer, String> endTimeMap) {
        this.mEndTimeMap = endTimeMap;
    }
    
    /**
     * 指定した日の開始時刻を取得する.
     * 
     * @param day 取得対象の日
     * @return 開始時刻（HH:mm形式）
     */
    public String getStartTime(int day) {
        return mStartTimeMap.getOrDefault(day, "");
    }
    
    /**
     * 指定した日の開始時刻を設定する.
     * 
     * @param day 設定対象の日
     * @param time 開始時刻（HH:mm形式）
     */
    public void setStartTime(int day, String time) {
        mStartTimeMap.put(day, time);
    }
    
    /**
     * 指定した日の終了時刻を取得する.
     * 
     * @param day 取得対象の日
     * @return 終了時刻（HH:mm形式）
     */
    public String getEndTime(int day) {
        return mEndTimeMap.getOrDefault(day, "");
    }
    
    /**
     * 指定した日の終了時刻を設定する.
     * 
     * @param day 設定対象の日
     * @param time 終了時刻（HH:mm形式）
     */
    public void setEndTime(int day, String time) {
        mEndTimeMap.put(day, time);
    }
    
    /**
     * 実働時間の総計を取得する.
     * 
     * @return 実働時間の総計（時間単位）
     */
    public double getTotalWorkHours() {
        return mTotalWorkHours;
    }
    
    /**
     * 実働時間の総計を設定する.
     * 
     * @param totalWorkHours 実働時間の総計（時間単位）
     */
    public void setTotalWorkHours(double totalWorkHours) {
        this.mTotalWorkHours = totalWorkHours;
    }
}