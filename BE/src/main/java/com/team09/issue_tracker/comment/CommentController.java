package com.team09.issue_tracker.comment;

import com.team09.issue_tracker.comment.dto.CommentCreateRequestDto;
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
	public void create(@RequestBody final CommentCreateRequestDto commentCreateRequestDto) {
	}

	@PatchMapping("/{id}")
	public void update(@PathVariable final Long patchId) {

	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable final Long deleteId) {

	}
}
