package com.team09.issue_tracker.image.aws_s3;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@RequiredArgsConstructor
@ConstructorBinding
@ConfigurationProperties(prefix = "aws.s3")
public class AwsS3Properties {

	private final String accessKeyId;

	private final String accessKeySecret;

	private final String regionName;

	private final String bucketName;
}
