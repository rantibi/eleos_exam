package eleos.exam.common.zoom.responses;

import lombok.Data;

@Data
public class PageResponse {
    private int pageCount;
    private int pageSize;
    private int totalRecords;
}
