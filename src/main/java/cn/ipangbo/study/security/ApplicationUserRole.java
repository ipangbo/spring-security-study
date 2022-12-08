package cn.ipangbo.study.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthoritiesContainer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum ApplicationUserRole {
    STUDENT(Set.of()),
    ADMIN(Set.of(
            ApplicationUserPermission.STUDENT_READ,
            ApplicationUserPermission.STUDENT_WRITE,
            ApplicationUserPermission.COURSE_READ,
            ApplicationUserPermission.COURSE_WRITE
    )),
    ADMINTRAINEE(Set.of(
            ApplicationUserPermission.STUDENT_READ,
            ApplicationUserPermission.COURSE_READ
    ));

    private final Set<ApplicationUserPermission> permissions;


    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }

    public Set<GrantedAuthority> getGrantedAuthorities() {
        Set<GrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return permissions;
    }
}
