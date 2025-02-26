package MJLee.onlineCourseService.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class CategoryInterested {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    String userName;

    @Column
    String courseId;

    @Column
    Long count;
}
