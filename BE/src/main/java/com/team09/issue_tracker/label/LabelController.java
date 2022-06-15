package com.team09.issue_tracker.label;

import com.team09.issue_tracker.common.CommonResponseDto;
import com.team09.issue_tracker.label.dto.LabelRequestDto;
import com.team09.issue_tracker.label.dto.LabelSelectResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/labels")
public class LabelController {

	@GetMapping
	public ResponseEntity<LabelSelectResponseDto> select() {
		return ResponseEntity.ok().build();
	}

	@PostMapping
	public ResponseEntity<CommonResponseDto> create(@RequestBody final LabelRequestDto labelRequestDto) {
		return ResponseEntity.ok().build();
	}

	@PatchMapping
	public ResponseEntity<CommonResponseDto> update(@RequestBody final LabelRequestDto labelRequestDto){
		return ResponseEntity.ok().build();
	}
}
