package companion.challeculum.domains.usermission.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserMissionJoined {
    private final Long userId;
    private final Long missionId;
    private final LocalDate submitAt;
    private final String isAccepted;
    private final String imageUrl;
    //user
    private final String oauthId;
    private final String username;
    private final String password;
    private final String nickname;
    private final String phone;
    private final int point;
    private final int missionScore;
    private final String role;
    //mission
    private final long groundId;
    private final String assignment;
    private final LocalDate startAt;
    private final LocalDate endAt;
}
