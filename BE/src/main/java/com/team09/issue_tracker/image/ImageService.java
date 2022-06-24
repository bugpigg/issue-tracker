package com.team09.issue_tracker.image;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.team09.issue_tracker.image.aws_s3.AwsS3Properties;
import com.team09.issue_tracker.image.dto.ImageRequestDto;
import com.team09.issue_tracker.image.dto.ImageResponseDto;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ImageService {

	private final AmazonS3 amazonS3;
	private final AwsS3Properties s3Properties;

	public ImageResponseDto findPreSignedUrl(ImageRequestDto requestDto) {
		String fileName = UUID.randomUUID() + requestDto.getFileName();
		String preSignedUrl =  amazonS3.generatePresignedUrl(s3Properties.getBucketName(), fileName, validDuration(), HttpMethod.PUT).toString();
		return new ImageResponseDto(preSignedUrl);
	}

	private Date validDuration() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MINUTE, 3);
		return calendar.getTime();
	}

}
