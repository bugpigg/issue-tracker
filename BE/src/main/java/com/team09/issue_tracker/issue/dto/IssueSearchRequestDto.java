package com.team09.issue_tracker.issue.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class IssueSearchRequestDto {

	private String title;
	private Long currentMemberId;
	private boolean opened;
	private boolean writtenByMe;
	private boolean assignedToMe;
	private boolean commentByMe;
	private List<Long> writerId;
	private List<Long> labelId;
	private List<Long> milestoneId;

	public void addCurrentMemberToWriters(Long currentMemberId) {
		if (writtenByMe){
			if (writerId == null) {
				writerId = new ArrayList<>();
			}
			writerId.add(currentMemberId);
		}
	}

	public void trimAndFormattingTitle() {
		if (title != null) {
			title = "%" + title.trim() + "%";
		}
	}
}
