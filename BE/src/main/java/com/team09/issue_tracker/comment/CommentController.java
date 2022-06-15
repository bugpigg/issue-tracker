package com.team09.issue_tracker.comment;

import com.team09.issue_tracker.comment.dto.CommentRequestDto;
import com.team09.issue_tracker.comment.dto.CommentResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comments")
public class CommentController {

	@PostMapping
	public ResponseEntity<CommentResponseDto> create(
		@RequestBody final CommentRequestDto commentCreateRequestDto) {
		return ResponseEntity.ok(new CommentResponseDto());
	}

	@PatchMapping("/{id}")
	public ResponseEntity<CommentResponseDto> update(@PathVariable final Long patchId) {
		return ResponseEntity.ok(new CommentResponseDto());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<CommentResponseDto> delete(@PathVariable final Long deleteId) {
		return ResponseEntity.ok(new CommentResponseDto());
	}
}
