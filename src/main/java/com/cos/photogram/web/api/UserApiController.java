package com.cos.photogram.web.api;

import com.cos.photogram.config.auth.PrincipalDetails;
import com.cos.photogram.domain.user.User;
import com.cos.photogram.handler.ex.CustomValidationApiException;
import com.cos.photogram.handler.ex.CustomValidationException;
import com.cos.photogram.service.SubscribeService;
import com.cos.photogram.service.UserService;
import com.cos.photogram.web.dto.CMRespDto;
import com.cos.photogram.web.dto.subscribe.SubscribeDto;
import com.cos.photogram.web.dto.subscribe.SubscribeRespDto;
import com.cos.photogram.web.dto.user.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@MultipartConfig(
        fileSizeThreshold = 1024*1024,
        maxFileSize = 1024*1024*50,
        maxRequestSize = 1024*1024*50*5
)
@Slf4j
@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;
    private final SubscribeService subscribeService;

    @PutMapping("/api/user/{principalId}/profileImageUrl")
    public ResponseEntity<?>profileImageUrlUpdate(@PathVariable int principalId, MultipartFile profileImageFile,
                                                  @AuthenticationPrincipal PrincipalDetails principalDetails){

        // MultipartFile profileImageFile ?????? <== <input> tag??? name??? ???????????????.
        User userEntity = userService.???????????????????????????(principalId,profileImageFile);
        principalDetails.setUser(userEntity);
        return new ResponseEntity<>(new CMRespDto<>(1,"????????????????????? ??????",null),HttpStatus.OK);
    }

    @GetMapping("/api/user/{pageUserId}/subscribe")
    public ResponseEntity<?> subscribeList(@AuthenticationPrincipal PrincipalDetails principalDetails,@PathVariable int pageUserId){

        System.out.println("########################### principalDetails.getUser().getId()  =>"+principalDetails.getUser().getId());
        System.out.println("######################## pageUserId =>"+pageUserId);
        List<SubscribeDto> subscribeDto = subscribeService.???????????????(principalDetails.getUser().getId(),pageUserId);

        System.out.println(" ######################## List<SubscribeDto> subscribeDto = subscribeService.???????????????() ###########################");
        return new ResponseEntity<>(new CMRespDto<>(1,"????????? ??????????????? ???????????? ??????",subscribeDto),HttpStatus.OK);
    }



    @PutMapping("/api/user/{id}") // update.js?????? redirect?
    public CMRespDto<?> update(
            @PathVariable int id,
            @Valid UserUpdateDto userUpdateDto,
            BindingResult bindingResult, // ??? Valid ?????? ??????????????? ????????????.
            @AuthenticationPrincipal PrincipalDetails principalDetails){

        if(bindingResult.hasFieldErrors()){
            Map<String, String> errorMap = new HashMap<>();

            for(FieldError error:bindingResult.getFieldErrors()){
                errorMap.put(error.getField(),error.getDefaultMessage());
                System.out.println("=====================================");
                System.out.println(error.getDefaultMessage());
                System.out.println("=====================================");
            }
            throw new CustomValidationApiException("????????? ?????? ?????????.",errorMap);

        }else {

            User userEntity = userService.????????????(id, userUpdateDto.toEntity());
            log.info("============= UserApiController : userUpdateDto.toEntity() =============");
            //log.info("userUpdateDto=" + userUpdateDto.toEntity()); // ???????????? ??????
            //log.info("print userEntity = " + userEntity); // ???????????? ??????
            principalDetails.setUser(userEntity); //????????? ???????????? ?????? ??????.
            return new CMRespDto<>(1, "??????????????????", userEntity);
            // ???????????? userEntity??? ?????? getter????????? ???????????? JSON?????? ???????????? ??????
        }
    }

}
