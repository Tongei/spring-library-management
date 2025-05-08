package org.library.library_management.config.security;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum RoleEnum {
    ADMIN(Set.of(PermissionEnum.BOOKS_WRITE, PermissionEnum.CATEGORY_WRITE, PermissionEnum.BOOKS_READ, PermissionEnum.CATEGORY_READ)),
    USER(Set.of(PermissionEnum.CATEGORY_READ, PermissionEnum.BOOKS_READ));


    private final Set<PermissionEnum> permissionEnums;

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> simpleGrantedAuthority = this.permissionEnums.stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getDescription()))
                .collect(Collectors.toSet());
        SimpleGrantedAuthority role = new SimpleGrantedAuthority("ROLE_" + this.name());
        simpleGrantedAuthority.add(role);
        return simpleGrantedAuthority;
    }

}
