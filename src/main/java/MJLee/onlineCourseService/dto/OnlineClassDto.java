package MJLee.onlineCourseService.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OnlineClassDto {
    String courseId;
    String courseName;
    String getClassDate;
    String fee;
    String needWeek;
    String category;
    String aspId;
    Boolean popular;
    String classNumber;
    String courseGubun;
}
