package MJLee.onlineCourseService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
  USER ("ROLE_USER"),
  VISITOR("ROLE_VISITOR");

  private final String roles;

  public static String getIncludingRoles(String role){
    return Role.valueOf(role).getRoles();
  }
  public static String addRole(Role role, String addRole){
    String priorRoles = role.getRoles();
    priorRoles += ","+addRole;
    return priorRoles;
  }
  public static String addRole(String roles, Role role){
    return roles + "," + role.getRoles();

  }
}
