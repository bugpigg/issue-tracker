package com.team09.issue_tracker.issue.dto;

import com.team09.issue_tracker.label.Label;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IssueFindAllResponseDto {

	private Long id;
	private String title;
	private String content;
	private String milestoneTitle;
	private List<Label> labels;
}
