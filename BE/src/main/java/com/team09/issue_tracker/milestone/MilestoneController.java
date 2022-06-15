package com.team09.issue_tracker.milestone;

import com.team09.issue_tracker.common.CommonResponseDto;
import com.team09.issue_tracker.milestone.dto.MilestoneRequestDto;
import com.team09.issue_tracker.milestone.dto.MilestoneSelectResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/milestones")
public class MilestoneController {

	@GetMapping
	public ResponseEntity<MilestoneSelectResponseDto> select() {
		return ResponseEntity.ok().build();
	}

	@PostMapping
	public ResponseEntity<CommonResponseDto> create(@RequestBody final MilestoneRequestDto milestoneRequestDto) {
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PatchMapping("/{id}")
	public ResponseEntity<CommonResponseDto> update(@RequestBody final MilestoneRequestDto milestoneRequestDto) {
		return ResponseEntity.ok().build();
	}
}
