package companion.challeculum.domains.user.controllers;

import companion.challeculum.domains.user.UserServiceImpl;
import companion.challeculum.domains.user.dtos.UserInfoDto;
import companion.challeculum.domains.user.dtos.UserLoginDto;
import companion.challeculum.domains.user.dtos.UserRegisterDto;
import companion.challeculum.domains.user.dtos.UserUpdateDto;
import companion.challeculum.security.jwt.JwtTokenInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * Created by jonghyeon on 2023/02/13,
 * Package : companion.challeculum.domains.user
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserServiceImpl userService;
    @PostMapping
    public void registerUser(@RequestBody UserRegisterDto dto) {
        userService.registerUser(dto);
    }

    @PostMapping("/login")
    public JwtTokenInfo login(@RequestBody UserLoginDto dto) {
        return userService.login(dto);
    }

    @PatchMapping
    public JwtTokenInfo updateUser(Authentication authentication, @RequestBody UserUpdateDto dto){
        return userService.updateUser(authentication, dto);
    }

    //어드민이 유저계정 삭제
    @DeleteMapping("/{userId}")
    public void deleteUser(Authentication authentication, @PathVariable long userId){
        userService.deleteUser(authentication, userId);
    }

    //본인이 계정 삭제
    @DeleteMapping
    public void deleteUser(Authentication authentication){
        userService.deleteUser(authentication);
    }

    @GetMapping
    public UserInfoDto getMyInfo(Authentication authentication){
        return userService.getMyInfo(authentication);
    }

}
