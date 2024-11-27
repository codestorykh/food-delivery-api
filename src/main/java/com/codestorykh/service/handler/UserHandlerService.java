package com.codestorykh.service.handler;

import com.codestorykh.dto.DeviceRequest;
import com.codestorykh.dto.DeviceResponse;
import com.codestorykh.dto.UserRequest;
import com.codestorykh.dto.UserResponse;
import com.codestorykh.enumeration.UserType;
import com.codestorykh.model.Device;
import com.codestorykh.model.User;
import com.codestorykh.utils.DateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.codestorykh.constant.Constant.SYSTEM;
import static com.codestorykh.constant.Constant.USER_STATUS_ACTIVE;

@Service
@Slf4j
public class UserHandlerService {

    public void usernameHasText(String username) {
        if(StringUtils.hasText(username)){
            return;
        }
        log.info("Username is empty");
        throw new IllegalArgumentException("Username is empty");
    }

    public void phoneNumberHasText(String phoneNumber) {
         if(StringUtils.hasText(phoneNumber)){
             return;
         }
         log.info("Phone number is empty");
         throw new IllegalArgumentException("Phone number is empty");
    }

    public UserResponse convertUserToUserResponse(final User user) {
       return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .gender(user.getGender())
                .dateOfBirth(user.getDateOfBirth().toString())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .address(user.getAddress())
                .userType(user.getUserType().toString())
                .status(user.getStatus())
                .createdBy(user.getCreatedBy())
                .createdAt(user.getCreatedAt())
                .updatedBy(user.getUpdatedBy())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public User convertUserRequestToUser(final UserRequest userRequest){
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setGender(userRequest.getGender());
        user.setDateOfBirth(DateTimeUtils.convertStringToDate(userRequest.getDateOfBirth()));
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setEmail(userRequest.getEmail());
        user.setAddress(userRequest.getAddress());
        user.setUserType(UserType.valueOf(userRequest.getUserType()));
        user.setStatus(USER_STATUS_ACTIVE);
        user.setCreatedBy(SYSTEM);
        user.setCreatedAt(new Date());

        return user;
    }

    public Device convertDeviceRequestToUserDevice(final User user, final DeviceRequest deviceRequest){
        Device device = new Device();
        device.setDeviceId(deviceRequest.getDeviceId());
        device.setDeviceModel(deviceRequest.getDeviceModel());
        device.setDeviceType(deviceRequest.getDeviceType());
        device.setOsVersion(deviceRequest.getOsVersion());
        device.setAppVersion(deviceRequest.getAppVersion());
        device.setTrustDevice(deviceRequest.isTrustDevice());
        device.setUser(user);

        return device;
    }

    public DeviceResponse convertUserDeviceToDeviceResponse(final Device device) {

        return DeviceResponse.builder()
                .id(device.getId())
                .deviceId(device.getDeviceId())
                .deviceType(device.getDeviceType())
                .deviceModel(device.getDeviceModel())
                .osVersion(device.getOsVersion())
                .appVersion(device.getAppVersion())
                .trustDevice(device.isTrustDevice())
                .lastLogin(device.getLastLogin())
                .status(device.getStatus())
                .build();
    }

}
