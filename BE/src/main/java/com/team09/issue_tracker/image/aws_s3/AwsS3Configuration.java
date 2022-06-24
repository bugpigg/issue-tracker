package com.team09.issue_tracker.image.aws_s3;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties(AwsS3Properties.class)
public class AwsS3Configuration {

	private final AwsS3Properties s3Properties;

	@Bean
	public AmazonS3 getAmazonS3Client() {
		BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(s3Properties.getAccessKeyId(),
			s3Properties.getAccessKeySecret());
		return AmazonS3ClientBuilder
			.standard()
			.withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
			.withRegion(s3Properties.getRegionName())
			.build();
	}
}
