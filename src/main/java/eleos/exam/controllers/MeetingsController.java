package eleos.exam.controllers;

import eleos.exam.common.zoom.ZoomAPI;
import eleos.exam.common.zoom.requests.MeetingStatusRequest;
import eleos.exam.model.Meeting;
import eleos.exam.repositories.MeetingsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController()
@RequestMapping(path = "api/v1/meetings")
@Slf4j
public class MeetingsController {
    @Autowired
    private ZoomAPI zoomAPI;

    @Autowired
    private MeetingsRepository meetingsRepository;

    @RequestMapping(path = "", method = RequestMethod.GET)
    @Transactional
    public List<Meeting> getAllLiveMeetings() {
        List<Meeting> meetings = zoomAPI.getMetricsMeetings("live");
        meetingsRepository.deleteAll();
        meetingsRepository.saveAll(meetings);
        return meetings;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/{meetingId}/end", method = RequestMethod.POST)
    public void endMeeting(@PathVariable long meetingId) {
        zoomAPI.updateMeetingStatus(meetingId, new MeetingStatusRequest("end"));
    }
}
