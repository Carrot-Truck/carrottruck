package com.boyworld.carrot.api.service.fcm;

import com.boyworld.carrot.api.service.fcm.dto.FCMNotificationRequestDto;
import com.boyworld.carrot.domain.member.repository.query.MemberDeviceTokenQueryRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FCMNotificationService {

    private final FirebaseMessaging firebaseMessaging;
    private final MemberDeviceTokenQueryRepository memberDeviceTokenQueryRepository;

    public void sendNotificationByToken(FCMNotificationRequestDto dto) {
        List<String> tokenList = memberDeviceTokenQueryRepository.getMembersLikeFoodTruck(dto.getFoodTruckId());
        Notification  notification = Notification.builder()
            .setTitle(dto.getTitle())
            .setBody(dto.getBody())
            .build();

        for (String token: tokenList) {
            Message message = Message.builder()
                .setToken(token)
                .setNotification(notification)
                .putAllData(dto.getData())
                .build();


            try {
                firebaseMessaging.send(message);
            } catch (FirebaseMessagingException ignored) {
                //
            }
        }
    }
}
