package com.BryceJensenius.MediaOrganizer.service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;

@Service
public class SnsService {

    
    private final String TOPIC_ARN;

    public SnsService(@Value("${SNS_TOPIC_ARN}") String topicArn) {
        TOPIC_ARN = topicArn;
    }


    public void publishSnsMessage() {
        SnsClient sns = SnsClient.builder().region(Region.US_EAST_2).build();
        sns.publish(PublishRequest.builder()
                .message("This is a test message from Media Organizer")
                .subject("Media Organizer Notification")
                .topicArn(TOPIC_ARN)
                .build());

        sns.close();
        System.out.println("SNS message published successfully.");
    }
}