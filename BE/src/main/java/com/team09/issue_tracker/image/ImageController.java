package com.team09.issue_tracker.image;

import com.team09.issue_tracker.image.dto.ImageRequestDto;
import com.team09.issue_tracker.image.dto.ImageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ImageController {

	private final ImageService imageService;

	@GetMapping("/images/presigned-url")
	public ResponseEntity<ImageResponseDto> findPreSignedUrl(@RequestBody ImageRequestDto requestDto) {
		ImageResponseDto responseDto =  imageService.findPreSignedUrl(requestDto);
		return ResponseEntity.ok(responseDto);
	}
}
