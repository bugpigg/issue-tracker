package com.team09.issue_tracker.comment;

import com.team09.issue_tracker.comment.dto.EmogiCreateRequestDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmogiController {

	@PostMapping("/emogi")
	public void create(@RequestBody EmogiCreateRequestDto emogiCreateRequestDto) {

	}
}
