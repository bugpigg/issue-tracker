package com.team09.issue_tracker.issue.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SelectableLabelMilestoneResponse {

	private final List<SelectableLabelResponse> labels;
	private final List<SelectableMilestoneResponse> milestones;

}
