package MJLee.onlineCourseService.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryInterestedDto {
  String userEmail;
  String courseId;
  Long count;
}
