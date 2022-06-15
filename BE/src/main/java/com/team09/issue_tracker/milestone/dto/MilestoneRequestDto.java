package com.team09.issue_tracker.milestone.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MilestoneRequestDto {

	private String title;
	private String description;
	private LocalDateTime completedAt;

}
