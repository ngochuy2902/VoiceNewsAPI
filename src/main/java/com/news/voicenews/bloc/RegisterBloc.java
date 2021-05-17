package com.news.voicenews.bloc;

import com.news.voicenews.dto.req.RegisterReq;
import com.news.voicenews.error.exception.ValidatorException;
import com.news.voicenews.model.User;
import com.news.voicenews.service.RoleService;
import com.news.voicenews.service.UserRoleService;
import com.news.voicenews.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RegisterBloc {

    private final UserService userService;
    private final RoleService roleService;
    private final UserRoleService userRoleService;

    public RegisterBloc(final UserService userService, final RoleService roleService, UserRoleService userRoleService) {
        this.userService = userService;
        this.roleService = roleService;
        this.userRoleService = userRoleService;
    }

    public void register(final RegisterReq registerReq) {
        validateRegisterReq(registerReq);

        User user = User.builder()
                        .username(registerReq.getUsername())
                        .password(registerReq.getPassword())
                        .yearOfBirth(registerReq.getYearOfBirth())
                        .build();
        userService.saveNew(user);
    }

    private void validateRegisterReq(final RegisterReq registerReq) {
        if (userService.existsByUsername(registerReq.getUsername()))
            throw new ValidatorException("Duplicate username", "username");
    }
}
