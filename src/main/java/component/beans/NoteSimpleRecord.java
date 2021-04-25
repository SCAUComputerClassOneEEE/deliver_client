package component.beans;

/**
 * @Author: Sky
 * @Date: 2021/4/20 15:24
 */
public class NoteSimpleRecord {
    private String message;
    private String time;
    private Boolean isRead;

    public NoteSimpleRecord(String message, String time, boolean isRead) {
        this.message = message;
        this.time = time;
        this.isRead = isRead;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}



