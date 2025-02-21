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
    String userEmail;

    @Column
    String category;

    @Column
    Long count;
}
