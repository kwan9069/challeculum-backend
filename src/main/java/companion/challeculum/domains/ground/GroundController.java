package companion.challeculum.domains.ground;

import companion.challeculum.common.AuthUserManager;
import companion.challeculum.domains.ground.dtos.CreateGroundDto;
import companion.challeculum.domains.ground.dtos.Ground;
import companion.challeculum.domains.ground.dtos.GroundLectureDto;
import companion.challeculum.security.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class GroundController {

    private final GroundService service;

    private final AuthUserManager authUserManager;

    @PostMapping("/api/v1/ground")
    void createGround(@RequestBody CreateGroundDto createGroundDTO, Authentication authentication) {
        if (authentication == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "로그인하지 않았습니다.");
        }
        long userId = authUserManager.getSessionId(authentication);
        createGroundDTO.setCreatedBy(userId);
        service.createGround(createGroundDTO);
    }


    @GetMapping("/api/v1/ground")
    List<GroundLectureDto> getGrounds(@RequestParam(required = false) Integer page,
                                      @RequestParam(required = false) Integer categoryId,
                                      @RequestParam(required = false) Integer level) {
        return service.getGrounds(page, categoryId, level);
    }


    @GetMapping("/api/v1/ground/{groundId}")
    Ground showGroundDetail(@PathVariable long groundId) {
        return service.showGroundDetail(groundId);
    }

    @DeleteMapping("/api/v1/ground/{groundId}")
    void deleteGround(@PathVariable Long groundId) {
        service.deleteGround(groundId);
    }
}
