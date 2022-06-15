package com.team09.issue_tracker.issue.dto;

import com.team09.issue_tracker.comment.Comment;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IssueFindByIdResponseDto {

	private Long id;
	private String title;
	private boolean isOpened;
	private String content;
	private String milestoneTitle;
	private List<Comment> comments;

}
