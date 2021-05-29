package com.news.voicenews.bloc;

import com.news.voicenews.dto.req.PasswordUpdateReq;
import com.news.voicenews.dto.req.UserUpdateReq;
import com.news.voicenews.dto.res.UserRes;
import com.news.voicenews.error.exception.ValidatorException;
import com.news.voicenews.helper.SecurityHelper;
import com.news.voicenews.mapper.UserMapper;
import com.news.voicenews.model.Category;
import com.news.voicenews.model.Role;
import com.news.voicenews.model.User;
import com.news.voicenews.service.CategoryService;
import com.news.voicenews.service.RoleService;
import com.news.voicenews.service.UserCategoryService;
import com.news.voicenews.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserBloc {

    private final UserService userService;
    private final RoleService roleService;
    private final CategoryService categoryService;
    private final UserCategoryService userCategoryService;
    private final PasswordEncoder passwordEncoder;

    public UserBloc(final UserService userService, final RoleService roleService, final CategoryService categoryService, final UserCategoryService userCategoryService, final PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.categoryService = categoryService;
        this.userCategoryService = userCategoryService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public UserRes getUserProfileByUserId(Long userId) {
        log.info("Get user profile by user id #{}", userId);

        User user = userService.getByUserId(userId);

        UserRes userRes = UserMapper.INSTANCE.toUserRes(user);
        List<Role> roles = roleService.fetchRolesByUserId(userId);
        List<String> roleTypes = roles.stream()
                                      .map(role -> role.getRoleType().toString())
                                      .collect(Collectors.toList());

        List<Category> categories = categoryService.findAllByUserId(userId);

        userRes.setRoles(roleTypes);
        userRes.setCategories(categories);

        return userRes;
    }

    @Transactional
    public void updateUserProfile(final UserUpdateReq userUpdateReq) {
        Long userId = SecurityHelper.getUserId();
        log.info("Update user profile by user login id #{}", userId);

        User user = userService.getByUserId(userId);
        user.setYearOfBirth(userUpdateReq.getYearOfBirth());
        userService.save(user);

        List<Long> categoryIds = userUpdateReq.getCategoryIds();
        userCategoryService.saveNewUserCategories(user.getId(), categoryIds);
    }

    @Transactional
    public void changePassword(final PasswordUpdateReq passwordUpdateReq) {
        Long userId = SecurityHelper.getUserId();
        log.info("Change password for current user login id #{}", userId);

        User user = userService.getByUserId(userId);

        if (passwordEncoder.matches(passwordUpdateReq.getOldPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(passwordUpdateReq.getNewPassword()));
            userService.save(user);
        } else {
            throw new ValidatorException("Incorrect old password", "oldPassword");
        }
    }
}
