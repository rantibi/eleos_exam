package eleos.exam.common.zoom.responses;

import eleos.exam.model.Meeting;
import lombok.Data;

import java.util.List;

@Data
public class MetricsMeetings extends PageResponse {
    private List<Meeting> meetings;
}
