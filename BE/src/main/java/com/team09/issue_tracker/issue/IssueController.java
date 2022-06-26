package com.team09.issue_tracker.issue;

import com.team09.issue_tracker.common.CommonResponseDto;
import com.team09.issue_tracker.issue.dto.IssueUpdateRequestDto;
import com.team09.issue_tracker.issue.dto.SelectableLabelMilestoneResponse;
import com.team09.issue_tracker.issue.dto.IssueSaveRequestDto;
import com.team09.issue_tracker.issue.dto.IssueListResponseDto;
import com.team09.issue_tracker.issue.dto.IssueDetailResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/issues")
public class IssueController {

	private final IssueService issueService;

	//TODO : 상수로 사용한 MEMBER_ID는 로그인 구현 완료시 http request 에서 가져오는 것으로 변경
	private final static Long MEMBER_ID = 1L;

	@GetMapping
	public ResponseEntity<List<IssueListResponseDto>> selectOpenedList() {
		List<IssueListResponseDto> response = issueService.selectOpenedList(MEMBER_ID);

		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<IssueDetailResponseDto> selectDetailById(@PathVariable final Long id) {
		IssueDetailResponseDto response = issueService.selectDetailById(id, MEMBER_ID);

		return ResponseEntity.ok().body(response);
	}

	/**
	 * 이슈 상세의 편집화면, "더보기"
	 *
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}/editing")
	public ResponseEntity<SelectableLabelMilestoneResponse> readyToEditLabelAndMilestone(
		@PathVariable final Long id) {
		SelectableLabelMilestoneResponse response = issueService.readyToEditLabelAndMilestone(
			id, MEMBER_ID);

		return ResponseEntity.ok().body(response);
	}

	/**
	 * 이슈 생성
	 *
	 * @param IssueSaveRequestDto
	 * @return
	 */
	@PostMapping
	public ResponseEntity<CommonResponseDto> create(
		@RequestBody IssueSaveRequestDto issueSaveRequestDto) {
		CommonResponseDto response = issueService.create(issueSaveRequestDto, MEMBER_ID);

		return ResponseEntity.ok().body(response);
	}

	/**
	 * 이슈 수정
	 *
	 * @param id
	 * @param IssueUpdateRequestDto
	 * @return
	 */
	@PatchMapping("/{id}")
	public ResponseEntity<CommonResponseDto> update(@PathVariable final Long id,
		@RequestBody IssueUpdateRequestDto issueUpdateRequestDto) {

		CommonResponseDto response = issueService.update(issueUpdateRequestDto, id, MEMBER_ID);

		return ResponseEntity.ok().body(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<CommonResponseDto> delete(@PathVariable final Long id) {
		return ResponseEntity.ok().build();
	}

	@PatchMapping("/{id}/state")
	public ResponseEntity<CommonResponseDto> updateState(@PathVariable final Long id,
		@RequestParam final Boolean isClose) {
		return ResponseEntity.ok().build();
	}

	@GetMapping(";type=title")
	public ResponseEntity<List<IssueListResponseDto>> findByTitle(
		@RequestParam final String title) {
		return ResponseEntity.ok().build();
	}

	@GetMapping(";type=filter")
	public ResponseEntity<List<IssueListResponseDto>> findBySearchCondition(
		@ModelAttribute final String issueSearchDto) {
		return ResponseEntity.ok().build();
	}

	@PatchMapping
	public ResponseEntity<CommonResponseDto> updateAllState(@RequestParam final Boolean isClose,
		@RequestBody final IssueSaveRequestDto issueUpdateAllRequestDto) {
		return ResponseEntity.ok().build();
	}

}
