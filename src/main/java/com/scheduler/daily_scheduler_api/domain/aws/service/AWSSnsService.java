package com.scheduler.daily_scheduler_api.domain.aws.service;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.CreateTopicResult;
import com.amazonaws.services.sns.model.PublishResult;
import com.amazonaws.services.sns.model.SubscribeResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Log4j2
@RequiredArgsConstructor
public class AWSSnsService {
    private final AmazonSNSClient snsClient;

    // 토픽 생성
    public void createTopic(String topicName) {
        CreateTopicResult topicResult = snsClient.createTopic(topicName);

        if(topicResult.getSdkHttpMetadata().getHttpStatusCode() != 200) {
            throw new RuntimeException("SNS Topic 생성 Error");
        }
        log.info("topic created {} ", topicResult.getTopicArn());
    }

    // 토픽 구독
    public void subscribe(String endPoint, String topicArn) {
        SubscribeResult subscribeResult = snsClient.subscribe(topicArn, "https", endPoint);

        if (subscribeResult.getSdkHttpMetadata().getHttpStatusCode() != 200) {
            throw new RuntimeException("SNS Subscribe Error");
        }
        log.info("topic subscribed {} ", subscribeResult.getSubscriptionArn());
    }

    // 메세지 발행
    public String publish(String topicArn, Map<String, Object> message) {
        PublishResult publishResult = snsClient.publish(topicArn, message.toString(), "HTTP ENDPOINT TEST MESSAGE");

        if (publishResult.getSdkHttpMetadata().getHttpStatusCode() != 200) {
            throw new RuntimeException("SNS Publish Error");
        }
        log.info("topic published {} ", publishResult.getMessageId());
        return publishResult.getMessageId();
    }
}