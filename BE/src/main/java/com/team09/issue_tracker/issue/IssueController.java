package com.team09.issue_tracker.issue;

import com.team09.issue_tracker.common.CommonResponseDto;
import com.team09.issue_tracker.issue.dto.IssueCreateAndUpdateRequestDto;
import com.team09.issue_tracker.issue.dto.IssueFindAllResponseDto;
import com.team09.issue_tracker.issue.dto.IssueFindByIdResponseDto;
import java.util.Collections;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/issues")
public class IssueController {

	@GetMapping
	public ResponseEntity<List<IssueFindAllResponseDto>> findAll() {
		return ResponseEntity.ok(Collections.singletonList(new IssueFindAllResponseDto()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<IssueFindByIdResponseDto> findById(@PathVariable final Long id) {
		return ResponseEntity.ok(new IssueFindByIdResponseDto());
	}

	@PostMapping
	public ResponseEntity<CommonResponseDto> create(
		@RequestBody IssueCreateAndUpdateRequestDto issueCreateRequestDto) {
		return ResponseEntity.ok(new CommonResponseDto());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<CommonResponseDto> delete(@PathVariable final Long id) {
		return ResponseEntity.ok(new CommonResponseDto());
	}

	@PatchMapping("/{id}")
	public ResponseEntity<CommonResponseDto> updateState(@PathVariable final Long id, @RequestParam final Boolean isClose) {
		return ResponseEntity.ok(new CommonResponseDto());
	}

	@GetMapping(";type=title")
	public ResponseEntity<List<IssueFindAllResponseDto>> findByTitle(
		@RequestParam final String title) {
		return ResponseEntity.ok(Collections.singletonList(new IssueFindAllResponseDto()));
	}

	@GetMapping(";type=filter")
	public ResponseEntity<List<IssueFindAllResponseDto>> findBySearchCondition(
		@ModelAttribute final String issueSearchDto) {
		return ResponseEntity.ok(Collections.singletonList(new IssueFindAllResponseDto()));
	}

	@PatchMapping
	public ResponseEntity<CommonResponseDto> updateAllState(@RequestParam final Boolean isClose,
		@RequestBody final IssueCreateAndUpdateRequestDto issueUpdateAllRequestDto) {
		return ResponseEntity.ok(new CommonResponseDto());
	}
}
