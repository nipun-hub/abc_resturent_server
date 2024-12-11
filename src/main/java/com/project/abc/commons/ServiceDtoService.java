package com.project.abc.commons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.abc.security.ServerConfig;

@Service
public class ServiceDtoService {

    private static String USER_PROFILE_IMAGE_URL = "api/user/profile-picture/image-url/{userid}/image";

    private static String USER_POST_URL = "api/post/{postId}/content/{contentId}/image";

    @Autowired
    private ServerConfig serverConfig;

//    public UserDTO addImageLink(UserDTO userDto) {
//        if (userDto.getProfilePicture() != null) {
//            String url = String.join("/", serverConfig.getAppURL(),
//                    USER_PROFILE_IMAGE_URL.replace("{userid}", userDto.getId()));
//            userDto.setProfilePicture(url);
//        }
//        if (userDto.getCoverPhoto() != null) {
//            String url = String.join("/", serverConfig.getAppURL(),
//                    USER_COVER_IMAGE_URL.replace("{userid}", userDto.getId()));
//            userDto.setCoverPhoto(url);
//        }
//        return userDto;
//    }
}
