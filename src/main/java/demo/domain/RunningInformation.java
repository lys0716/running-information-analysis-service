package demo.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.tomcat.jni.User;

import javax.persistence.*;
import java.util.Date;
import java.util.Random;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Data
@Entity
@Table(name = "RUNNING_ANALYSIS")
public class RunningInformation {
    private String runningId;
    private double latitude;
    private double longitude;
    private double runningDistance;
    private double totalRunningTime;
    private int heartRate;
    private Date timestamp;

    public static enum HealthWarningLevel {
        Low, Normal, High;
    }
    HealthWarningLevel healthWarningLevel;

    @Embedded
    private UserInfo userInfo;

    @Id
    @GeneratedValue
    private long id;

    public RunningInformation() {}

    @JsonCreator
    public RunningInformation(
            @JsonProperty(value = "runningId") String runningId,
            @JsonProperty(value = "latitude") String latitude,
            @JsonProperty(value = "longitude") String longitude,
            @JsonProperty(value = "runningDistance") String runningDistance,
            @JsonProperty(value = "totalRunningTime") String totalRunningTime,
            @JsonProperty(value = "heartRate") int heartRate,
            @JsonProperty(value = "timestamp") String timestamp,
            @JsonProperty(value = "userInfo") UserInfo userInfo) {
        this.runningId = runningId;
        this.latitude = Double.parseDouble(latitude);
        this.longitude = Double.parseDouble(longitude);
        this.runningDistance = Double.parseDouble(runningDistance);
        this.totalRunningTime = Double.parseDouble(totalRunningTime);
        this.timestamp = new Date();

        this.userInfo = userInfo;

        this.heartRate = _getRandomHeart(60, 200);
        if (heartRate <= 75) {
            setHealthWarningLevel(HealthWarningLevel.Low);
        } else if (heartRate <= 120) {
            setHealthWarningLevel(HealthWarningLevel.Normal);
        } else {
            setHealthWarningLevel(HealthWarningLevel.High);
        }
    }

    private int _getRandomHeart(int min, int max) {
        Random random = new Random();
        return min + random.nextInt(max - min + 1);
    }

    public RunningInformation(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getUserName() {
        return this.userInfo == null ? null : this.userInfo.getUsername();
    }

    public String getAddress() {
        return this.userInfo == null ? null : this.userInfo.getAddress();
    }
}
