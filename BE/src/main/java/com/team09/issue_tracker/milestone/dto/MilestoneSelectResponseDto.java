package com.team09.issue_tracker.milestone.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MilestoneSelectResponseDto {

	private Long id;
	private String title;
	private String description;
	private LocalDateTime completedAt;
	private int openCount;
	private int closeCount;
	private int completion;

}
