package com.codestorykh.service.impl;

import com.codestorykh.dto.UserRequest;
import com.codestorykh.dto.UserResponse;
import com.codestorykh.exception.InternalServerErrorException;
import com.codestorykh.exception.UserNotFoundErrorException;
import com.codestorykh.model.Device;
import com.codestorykh.model.User;
import com.codestorykh.repository.DeviceRepository;
import com.codestorykh.repository.UserRepository;
import com.codestorykh.service.handler.UserHandlerService;
import com.codestorykh.service.UserService;
import com.codestorykh.utils.DateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final ModelMapper modeMapper;
    private final UserRepository userRepository;
    private final DeviceRepository deviceRepository;
    private final UserHandlerService userHandlerService;

    public UserServiceImpl(ModelMapper modeMapper,
                           UserRepository userRepository,
                           DeviceRepository deviceRepository,
                           UserHandlerService userHandlerService) {
        this.modeMapper = modeMapper;
        this.userRepository = userRepository;
        this.deviceRepository = deviceRepository;
        this.userHandlerService = userHandlerService;
    }

    @Transactional
    @Override
    public UserResponse create(UserRequest userRequest) {
        userHandlerService.usernameHasText(userRequest.getUsername());
        userHandlerService.phoneNumberHasText(userRequest.getPhoneNumber());

        User user = userHandlerService.convertUserRequestToUser(userRequest);
        log.info("Save user record: {}", user);

        userRepository.saveAndFlush(user);

        if(user.getId() != null){
            Device device = userHandlerService.convertDeviceRequestToUserDevice(user, userRequest.getDeviceRequest());
            log.info("Save device record: {}", device);
            deviceRepository.save(device);
        }
        return userHandlerService.convertUserToUserResponse(user);
    }

    @Transactional
    @Override
    public UserResponse update(Long id, UserRequest userRequest) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
           log.info("User update with id {} not found in DB", id);
            this.create(userRequest);
            return null;
        }
        user.get().setUsername(userRequest.getUsername());
        user.get().setPhoneNumber(userRequest.getPhoneNumber());
        user.get().setDateOfBirth(DateTimeUtils.convertStringToDate(userRequest.getDateOfBirth()));
        user.get().setGender(userRequest.getGender());
        user.get().setAddress(userRequest.getAddress());
        user.get().setFirstName(userRequest.getFirstName());
        user.get().setLastName(userRequest.getLastName());
        user.get().setUpdatedAt(new Date());

        log.info("Update user record: {}", user.get());
        userRepository.saveAndFlush(user.get());

        if(user.get().getDevices() != null) {
           var deviceRequest = userRequest.getDeviceRequest();
           if(StringUtils.hasText(deviceRequest.getDeviceId())) {
               List<Device> devices = user.get().getDevices();
               Device deviceUpdate = devices.stream()
                       .filter(device ->
                               device.getDeviceId().equalsIgnoreCase(deviceRequest.getDeviceId()))
                       .findAny().orElse(null);

               if(deviceUpdate != null) {
                   deviceUpdate.setDeviceModel(deviceRequest.getDeviceModel());
                   deviceUpdate.setDeviceType(deviceRequest.getDeviceType());
                   deviceUpdate.setOsVersion(deviceRequest.getOsVersion());
                   deviceUpdate.setAppVersion(deviceRequest.getAppVersion());
                   deviceUpdate.setTrustDevice(deviceRequest.isTrustDevice());

                   log.info("Update device record: {}", deviceUpdate);
                   deviceRepository.save(deviceUpdate);
               }else {
                   Device device = userHandlerService.convertDeviceRequestToUserDevice(user.get(), deviceRequest);
                   deviceRepository.save(device);
               }
           }
        }

        return userHandlerService.convertUserToUserResponse(user.get());
    }

    @Override
    public UserResponse findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            log.info("User with id {} not found in DB.", id);
            throw new UserNotFoundErrorException("User not found.");
        }
        return userHandlerService.convertUserToUserResponse(user.get());
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserResponse> findAll() {
        List<User> users = userRepository.findAll();

        if(users.isEmpty()){
            log.info("No user found in DB.");
            throw new UserNotFoundErrorException("User not found.");
        }

        List<UserResponse> userResponses = new ArrayList<>();
        for(User user : users) {
            UserResponse userResponse = userHandlerService.convertUserToUserResponse(user);
            userResponses.add(userResponse);
        }
        return userResponses;
    }
}
