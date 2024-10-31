package com.scheduler.daily_scheduler_api.config.aws;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Getter
@Configuration
public class AWSSnsConfig {

    @Value("${app.sns.topic.arn}")
    private String snsTopicArn;

    @Value("${app.sns.access-key}")
    private String snsAccessKey;

    @Value("${app.sns.secret-key}")
    private String snsSecretKey;

    @Value("${app.sns.region}")
    private String snsRegion;

    @Bean
    @Primary
    public AmazonSNSClient amazonSNSClient() {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(snsAccessKey, snsSecretKey);
        return (AmazonSNSClient) AmazonSNSClientBuilder.standard()
                .withRegion(snsRegion)
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();
    }
}
