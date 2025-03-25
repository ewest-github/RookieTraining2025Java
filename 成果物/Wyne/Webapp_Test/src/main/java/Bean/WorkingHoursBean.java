package Bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

//WorkingHoursBeanはSerializableを継承する
public class WorkingHoursBean implements Serializable {
    
	/*各日の開始時刻を格納するマップ
	  キーは日付（整数）で、値は開始時刻（文字列）。
	  */
	
    private Map<Integer, String> startTimes = new HashMap<>(); 
    
    /*各日の終了時刻を格納するマップ
     * キーは日付（整数）で、値は終了時刻（文字列）。
     */
    
    private Map<Integer, String> endTimes = new HashMap<>(); 
    
    // 実働総計の変数宣言
    private double totalHours; 
    
    //変数に対するgetterとsetter
    
    /* 開始時刻を取得するメソッド
     * @para 指定した日
     * @return 指定した日の開始時刻
     * @return 指定した日の開始時刻がなければ空文字 */
    
    public String getStartTime(int day) {
    
        return startTimes.getOrDefault(day, ""); 
    }

    /* 開始時刻を取得するメソッド
     * @para 指定した日
     * @return 指定した日の終了時刻
     * @return 指定した日の終了時刻がなければ空文字 */
    
    public String getEndTime(int day) {
    	
        return endTimes.getOrDefault(day, ""); 
    }

    /* 実働総計を取得するメソッド
     * @return 実働時間*/
    
    public double getTotalHours() {
    	
        return totalHours; 
    }
    
    /*開始時刻を設定するメソッド
     * @param 指定した日、開始時刻、開始分
     * @put 指定した日と開始時刻をマップに送る*/
    
    public void setStartTime(int day, String startHour, String startMinute) {
        String startTime = startHour + ":" + startMinute; 
        startTimes.put(day, startTime);
    }

    /*終了時刻を設定するメソッド
     * @param 指定した日、終了時刻、開始分
     * @put 指定した日と終了時刻をマップに送る*/
    
    public void setEndTime(int day, String endHour, String endMinute) {
        String endTime = endHour + ":" + endMinute; 
        endTimes.put(day, endTime); 
    }

    /*実働総計を設定するメソッド
     * @param 実働時間*/
    
    public void setTotalHours(double totalHours) {
        this.totalHours = totalHours; 
    }
    
}
