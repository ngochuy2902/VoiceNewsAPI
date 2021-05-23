package com.news.voicenews.bloc;

import com.news.voicenews.dto.req.RegisterReq;
import com.news.voicenews.enums.RoleType;
import com.news.voicenews.error.exception.ValidatorException;
import com.news.voicenews.model.Role;
import com.news.voicenews.model.User;
import com.news.voicenews.service.RoleService;
import com.news.voicenews.service.UserCategoryService;
import com.news.voicenews.service.UserRoleService;
import com.news.voicenews.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class RegisterBloc {

    private final UserService userService;
    private final RoleService roleService;
    private final UserRoleService userRoleService;
    private final UserCategoryService userCategoryService;

    private final static RoleType DEFAULT_ACCOUNT_ROLE = RoleType.ROLE_USER;

    public RegisterBloc(final UserService userService,
                        final RoleService roleService,
                        final UserRoleService userRoleService,
                        final UserCategoryService userCategoryService) {
        this.userService = userService;
        this.roleService = roleService;
        this.userRoleService = userRoleService;
        this.userCategoryService = userCategoryService;
    }

    @Transactional
    public void register(final RegisterReq registerReq) {
        validateRegisterReq(registerReq);

        User user = User.builder()
                        .username(registerReq.getUsername())
                        .password(registerReq.getPassword())
                        .yearOfBirth(registerReq.getYearOfBirth())
                        .build();
        userService.saveNew(user);

        Role role = roleService.findRoleByRoleType(DEFAULT_ACCOUNT_ROLE);
        userRoleService.saveNewUserRoles(user.getId(), Collections.singletonList(role.getId()));

        List<Long> categoryIds = registerReq.getCategoryIds();
        userCategoryService.saveNewUserCategories(user.getId(), categoryIds);
    }

    @Transactional(readOnly = true)
    public void validateRegisterReq(final RegisterReq registerReq) {
        if (userService.existsByUsername(registerReq.getUsername()))
            throw new ValidatorException("Duplicate username", "username");
    }
}
