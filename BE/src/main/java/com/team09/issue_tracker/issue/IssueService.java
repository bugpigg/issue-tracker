package com.team09.issue_tracker.issue;

import com.team09.issue_tracker.common.CommonResponseDto;
import com.team09.issue_tracker.exception.EditorInvalidException;
import com.team09.issue_tracker.exception.IssueLabelNotFoundException;
import com.team09.issue_tracker.exception.IssueNotFoundException;
import com.team09.issue_tracker.issue.domain.Issue;
import com.team09.issue_tracker.issue.domain.IssueAssignee;
import com.team09.issue_tracker.issue.domain.IssueLabel;
import com.team09.issue_tracker.issue.dto.IssueUpdateRequestDto;
import com.team09.issue_tracker.issue.dto.SelectableLabelMilestoneResponse;
import com.team09.issue_tracker.issue.dto.IssueDetailResponseDto;
import com.team09.issue_tracker.issue.dto.IssueSaveRequestDto;
import com.team09.issue_tracker.issue.dto.IssueListResponseDto;
import com.team09.issue_tracker.issue.dto.IssueSaveServiceDto;
import com.team09.issue_tracker.issue.dto.SelectableLabelResponse;
import com.team09.issue_tracker.issue.dto.SelectableMilestoneResponse;
import com.team09.issue_tracker.label.Label;
import com.team09.issue_tracker.member.Member;
import com.team09.issue_tracker.milestone.Milestone;
import com.team09.issue_tracker.milestone.MilestoneRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class IssueService {

	private final IssueRepository issueRepository;
	private final IssueLabelRepository issueLabelRepository;
	private final IssueAssigneeRepository issueAssigneeRepository;
	private final MilestoneRepository milestoneRepository;

	@Transactional(readOnly = true)
	public List<IssueListResponseDto> selectOpenedList(Long memberId) {
		List<Issue> issues = issueRepository.findByMemberIdAndIsOpened(memberId,
			true);

		List<IssueListResponseDto> response = issues.stream().map(Issue::toListResponse)
			.collect(Collectors.toList());

		return Collections.unmodifiableList(response);
	}

	@Transactional
	public CommonResponseDto create(IssueSaveRequestDto issueCreateRequestDto, Long memberId) {
		boolean isOpened = true;
		//1. milestone 생성 - 이렇게하면 getMilestoneId()가 null이면 어떻게 됨?
		Milestone milestone = Optional.ofNullable(issueCreateRequestDto.getMilestoneId())
			.map(Milestone::of).get();

		//2. Issue 생성
		Issue issue = createIssue(issueCreateRequestDto.getTitle(),
			issueCreateRequestDto.getContent(), memberId,
			isOpened, milestone);

		Issue savedIssue = issueRepository.save(issue);
		Long issueId = savedIssue.getId();

		// IssueLabel, IssueAssignee
		List<Long> labelIds = issueCreateRequestDto.getLabelIds();
		List<Long> assigneeIds = issueCreateRequestDto.getAssigneeIds();

		//3. IssueLabel 생성
		saveIssueLabel(savedIssue, issueId, labelIds);

		//4. IssueAssignee 생성
		savedIssueAssignee(issue, savedIssue, assigneeIds);

		return savedIssue.toCommonResponse();
	}

	private Issue createIssue(String title, String content, Long memberId,
		boolean isOpened, Milestone milestone) {
		IssueSaveServiceDto issueSaveServiceDto = IssueSaveServiceDto.builder()
			.title(title)
			.content(content)
			.milestone(milestone)
			.isOpened(isOpened)
			.memberId(memberId)
			.build();
		Issue issue = Issue.from(issueSaveServiceDto);

		return issue;
	}

	private void saveIssueLabel(Issue savedIssue, Long issueId, List<Long> labelIds) {
		if (!labelIds.isEmpty()) {
			List<IssueLabel> issueLabels = labelIds.stream()
				.map(labelId -> IssueLabel.of(issueId, labelId))
				.collect(Collectors.toList());

			List<IssueLabel> savedIssueLabels = issueLabels.stream()
				.map(issueLabel -> issueLabelRepository.save(issueLabel))
				.collect(Collectors.toList());
			//연관관계 편의 메서드
			savedIssue.addIssueLabel(savedIssueLabels);
		}
	}

	private void savedIssueAssignee(Issue issue, Issue savedIssue, List<Long> assigneeIds) {
		if (assigneeIds.size() > 0) {
			List<IssueAssignee> issueAssignees = assigneeIds.stream()
				.map(assigneeId -> IssueAssignee.of(savedIssue, Member.of(assigneeId)))
				.collect(Collectors.toList());

			List<IssueAssignee> savedIssueAssignees = issueAssignees.stream()
				.map(issueAssignee -> issueAssigneeRepository.save(issueAssignee))
				.collect(Collectors.toList());
			//연관관계 편의메서드
			issue.addIssueAssignee(savedIssueAssignees);
		}
	}

	/**
	 * 해당 issueid가 작성/할당 된 이슈만 "더보기" 버튼 생성 하여 상세정보 수정/삭제 가능 작성자, 할당자 확인 로직
	 *
	 * @param issueId
	 * @param memberId
	 * @return
	 */
	@Transactional(readOnly = true)
	public IssueDetailResponseDto selectDetailById(Long issueId, Long memberId) {
		boolean isEditable = false;

		//1. 조회하면서 작성자인지 확인
		Issue issue = issueRepository.findById(issueId)
			.orElseThrow(() -> new EditorInvalidException());

		if (issue.isWriter(memberId)) {
			isEditable = true;
			return issue.toDetailResponse(isEditable);
		}

		//2. 할당자인지 확인
		List<IssueAssignee> issueAssignees = issue.getIssueAssignees();
		if (issueAssignees.size() != 0) {
			isEditable = true;
		}

		return issue.toDetailResponse(isEditable);
	}

	public CommonResponseDto update(IssueSaveRequestDto issueSaveRequestDto, Long issueId) {

		return null;
	}

	@Transactional(readOnly = true)
	public SelectableLabelMilestoneResponse readyToEditLabelAndMilestone(Long issueId,
		Long memberId) {
		//label
		List<ISelectableLabel> selectableLabels = issueLabelRepository.findBySelectable(issueId,
			memberId);

		List<SelectableLabelResponse> labelsResponse = new ArrayList<>();
		for (ISelectableLabel selectableLabel : selectableLabels) {
			labelsResponse.add(SelectableLabelResponse.builder()
				.labelId(selectableLabel.getLabelId())
				.title(selectableLabel.getTitle())
				.backgroundColor(selectableLabel.getBackgroundColor())
				.darkMode(selectableLabel.getDarkMode())
				.memberId(selectableLabel.getMemberId())
				.issueId(selectableLabel.getIssueId())
				.build());
		}

		//milestone
		List<SelectableMilestoneResponse> milestoneResponse = new ArrayList<>();

		Issue issue = issueRepository.findById(issueId)
			.orElseThrow(() -> new IssueNotFoundException());

		Milestone selectedMilestone = issue.getMilestone();

		List<Milestone> selectableMilestone = milestoneRepository.findByWriter(Member.of(memberId));
		for (Milestone milestone : selectableMilestone) {
			if (selectedMilestone.getId() == milestone.getId()) {
				milestoneResponse.add(SelectableMilestoneResponse.builder()
					.milestoneId(milestone.getId())
					.title(milestone.getTitle())
					.issueId(issue.getId())
					.build());
			} else {
				milestoneResponse.add(SelectableMilestoneResponse.builder()
					.milestoneId(milestone.getId())
					.title(milestone.getTitle())
					.build());
			}
		}

		return new SelectableLabelMilestoneResponse(labelsResponse, milestoneResponse);
	}
}
