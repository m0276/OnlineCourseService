package MJLee.onlineCourseService.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class OnlineClass {

    @Id
    String courseId;

    String courseName;

    String category;

    String getClassDate;

    String fee;

    Boolean popular;

    String classNumber;

    String needWeek;

    String aspId;
}
