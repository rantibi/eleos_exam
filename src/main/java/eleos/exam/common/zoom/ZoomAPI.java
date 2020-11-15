package eleos.exam.common.zoom;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import eleos.exam.common.zoom.requests.MeetingStatusRequest;
import eleos.exam.common.zoom.responses.MetricsMeetings;
import eleos.exam.model.Meeting;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;


@Service
public class ZoomAPI {
    private static final String URL = "https://api.zoom.us/v2";
    @Value("${zoom.api-key}")
    private String apiKey;
    private CloseableHttpClient client;
    private ObjectMapper mapper;

    @PostConstruct
    public void init() {
        mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        client = HttpClients.createDefault();

    }

    public List<Meeting> getMetricsMeetings(String type) {
        return executeRequest("/metrics/meetings", RequestBuilder.get().addParameter("type", type), MetricsMeetings.class).getMeetings();
    }


    public void updateMeetingStatus(long meetingId, MeetingStatusRequest request) {
        try {
            executeRequest("/meetings/" + meetingId + "/status", RequestBuilder.put()
                            .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                            .setEntity(new StringEntity(mapper.writeValueAsString(request))),
                    Void.class);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T executeRequest(String path, RequestBuilder requestBuilder, Class<T> responseClass) {
        try {
            HttpUriRequest request = requestBuilder.setUri(URL + path)
                    .setHeader(HttpHeaders.ACCEPT, "application/json")
                    .setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey).build();
            try (CloseableHttpResponse response = client.execute(request)) {
                if (response.getStatusLine().getStatusCode() >= 300) {
                    //TODO: handle request failed
                    throw new RuntimeException("request failed");
                }

                if (response.getStatusLine().getStatusCode() == 204) {
                    return null;
                }

                return mapper.readValue(response.getEntity().getContent(), responseClass);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
