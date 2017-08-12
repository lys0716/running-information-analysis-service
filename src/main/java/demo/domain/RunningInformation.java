package demo.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

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
    private Date timestamp = new Date();

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
    public RunningInformation(@JsonProperty(value = "runningId") String runningId) {
        this.runningId = runningId;
    }
}
