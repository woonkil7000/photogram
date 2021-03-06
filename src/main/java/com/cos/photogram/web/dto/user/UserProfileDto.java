package com.cos.photogram.web.dto.user;

import com.cos.photogram.domain.user.User;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserProfileDto {
    private boolean pageOwnerState; //페이지 주인인가?
    private int imageCount; // 이미지수
    private boolean subscribeState; // 구독상태?
    private int subscribeCount; // 구독수
    private User user; // 유저 Entity

}
