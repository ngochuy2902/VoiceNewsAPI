package com.news.voicenews.bloc;

import com.news.voicenews.dto.res.UserRes;
import com.news.voicenews.mapper.UserMapper;
import com.news.voicenews.model.Category;
import com.news.voicenews.model.Role;
import com.news.voicenews.model.User;
import com.news.voicenews.model.UserRole;
import com.news.voicenews.service.CategoryService;
import com.news.voicenews.service.RoleService;
import com.news.voicenews.service.UserRoleService;
import com.news.voicenews.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserBloc {

    private final UserService userService;
    private final UserRoleService userRoleService;
    private final RoleService roleService;
    private final CategoryService categoryService;

    public UserBloc(final UserService userService, final UserRoleService userRoleService, final RoleService roleService, final CategoryService categoryService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.roleService = roleService;
        this.categoryService = categoryService;
    }

    @Transactional(readOnly = true)
    public UserRes getUserProfileByUserId(Long userId) {
        log.info("Get user profile by user id #{}", userId);

        User user = userService.getByUserId(userId);

        UserRes userRes = UserMapper.INSTANCE.toUserRes(user);
//        UserRes userRes = new UserRes();
        List<Role> roles = roleService.fetchRolesByUserId(userId);
        List<String> roleTypes = roles.stream()
                                      .map(role -> role.getRoleType().toString())
                                      .collect(Collectors.toList());

        List<Category> categories = categoryService.findAllByUserId(userId);

        userRes.setRoles(roleTypes);
        userRes.setCategories(categories);

        return userRes;
    }
}
