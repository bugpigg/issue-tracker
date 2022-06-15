package com.team09.issue_tracker.comment.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EmogiRequestDto {

	private Long commentId;
	private String unicodeValue;
}
