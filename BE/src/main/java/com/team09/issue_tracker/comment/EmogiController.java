package com.team09.issue_tracker.comment;

import com.team09.issue_tracker.comment.dto.EmogiRequestDto;
import com.team09.issue_tracker.common.CommonResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmogiController {

	@PostMapping("/emogi")
	public ResponseEntity<CommonResponseDto> create(
		@RequestBody EmogiRequestDto emogiCreateRequestDto) {
		return ResponseEntity.ok(new CommonResponseDto());
	}
}
