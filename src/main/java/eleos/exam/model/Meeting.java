package eleos.exam.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "meetings")
public class Meeting {
    @Id
    private long id;
    private int participants;
}
