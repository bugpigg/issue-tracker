package com.team09.issue_tracker.issue;

import com.team09.issue_tracker.common.CommonResponseDto;
import com.team09.issue_tracker.exception.EditorInvalidException;
import com.team09.issue_tracker.exception.IssueLabelNotFoundException;
import com.team09.issue_tracker.exception.IssueNotFoundException;
import com.team09.issue_tracker.issue.domain.Issue;
import com.team09.issue_tracker.issue.domain.IssueAssignee;
import com.team09.issue_tracker.issue.domain.IssueLabel;
import com.team09.issue_tracker.issue.dto.IssueSearchRequestDto;
import com.team09.issue_tracker.issue.dto.IssueUpdateRequestDto;
import com.team09.issue_tracker.issue.dto.SelectableLabelMilestoneResponse;
import com.team09.issue_tracker.issue.dto.IssueDetailResponseDto;
import com.team09.issue_tracker.issue.dto.IssueSaveRequestDto;
import com.team09.issue_tracker.issue.dto.IssueListResponseDto;
import com.team09.issue_tracker.issue.dto.SelectableLabelResponse;
import com.team09.issue_tracker.issue.dto.SelectableMilestoneResponse;
import com.team09.issue_tracker.label.Label;
import com.team09.issue_tracker.member.Member;
import com.team09.issue_tracker.milestone.Milestone;
import com.team09.issue_tracker.milestone.MilestoneRepository;
import com.team09.issue_tracker.milestone.MilestoneService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class IssueService {

	private final IssueRepository issueRepository;
	private final IssueLabelRepository issueLabelRepository;
	private final MilestoneRepository milestoneRepository;

	private final MilestoneService milestoneService;
	private final IssueLabelService issueLabelService;
	private final IssueAssigneeService issueAssigneeService;

	@Transactional(readOnly = true)
	public List<IssueListResponseDto> selectOpenedList(Long memberId) {
		List<Issue> issues = issueRepository.findByMemberIdAndIsOpened(memberId,
			true);

		List<IssueListResponseDto> response = issues.stream().map(Issue::toListResponse)
			.collect(Collectors.toList());

		return Collections.unmodifiableList(response);
	}

	@Transactional
	public CommonResponseDto create(IssueSaveRequestDto issueSaveRequestDto, Long memberId) {
		boolean isOpened = true;

		Milestone milestone = milestoneService.createMilestone(issueSaveRequestDto.getMilestoneId()
			, memberId);

		Issue issue = Issue.of(issueSaveRequestDto.getTitle(),
			issueSaveRequestDto.getContent(), memberId,
			isOpened, milestone);

		issue = issueRepository.save(issue);

		issueLabelService.saveIssueLabel(issue, issueSaveRequestDto.getLabelIds());

		issueAssigneeService.savedIssueAssignee(issue, issueSaveRequestDto.getAssigneeIds());

		return issue.toCommonResponse();
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
		Set<IssueAssignee> issueAssignees = issue.getIssueAssignees();
		if (issueAssignees.size() != 0) {
			isEditable = true;
		}

		return issue.toDetailResponse(isEditable);
	}

	@Transactional
	public CommonResponseDto update(IssueUpdateRequestDto issueUpdateRequestDto, Long issueId,
		Long memberId) {

		Issue issue = issueRepository.findById(issueId)
			.orElseThrow(() -> new IssueNotFoundException());

		issue.update(issueUpdateRequestDto.getTitle(), issueUpdateRequestDto.getContent(),
			milestoneService.createMilestone(issueUpdateRequestDto.getMilestoneId()
				, memberId), issueUpdateRequestDto.isOpened());

		CommonResponseDto response = updateIssueLabel(
			issueUpdateRequestDto, issueId, issue);

		return response;
	}

	/**
	 * label 수정 1.기존리스트 issueLabels 검색 2.기존리스트에서 삭제된 레이블, DB삭제 3.기존리스트에 없는 추가로 선택된 레이블, DB추가
	 *
	 * @param issueUpdateRequestDto
	 * @param issueId
	 * @param issue
	 * @return
	 */
	private CommonResponseDto updateIssueLabel(IssueUpdateRequestDto issueUpdateRequestDto,
		Long issueId, Issue issue) {

		Set<IssueLabel> issueLabels = issue.getIssueLabels();

		List<Long> editingLabelIds = issueUpdateRequestDto.getLabelIds();
		//Label을 모두 삭제할 경우
		if (editingLabelIds.isEmpty()) {
			issueLabelRepository.deleteAll(issueLabels);
			issue.addIssueLabel(Collections.emptySet());

			return new CommonResponseDto(issue.getId());
		}

		List<Long> existingLabelIds = issueLabels.stream()
			.map(IssueLabel::getLabel)
			.map(Label::getId)
			.collect(Collectors.toList());
		List<Long> copiedExistingLabelIds = List.copyOf(existingLabelIds);

		existingLabelIds.removeAll(editingLabelIds); //삭제할 라벨 리스트
		editingLabelIds.removeAll(copiedExistingLabelIds); //추가할 라벨 리스트

		//DB 삭제
		List<IssueLabel> deletedIssueLabels = existingLabelIds.stream()
			.map(labelId -> issueLabelRepository.findByIssueAndLabel(Issue.of(issueId),
					Label.of(labelId))
				.orElseThrow(() -> new IssueLabelNotFoundException(issueId, labelId)))
			.collect(Collectors.toList());
		issueLabelRepository.deleteAll(deletedIssueLabels);

		//DB 추가
		List<IssueLabel> savedIssueLabels = editingLabelIds.stream()
			.map(labelId -> issueLabelRepository.save(IssueLabel.of(issueId, labelId)))
			.collect(Collectors.toList());

		//연관관계 편의 메서드 호출
		deletedIssueLabels.stream()
			.forEach(issueLabel -> issueLabels.remove(issueLabel));
		savedIssueLabels.stream()
			.forEach(issueLabel -> issueLabels.add(issueLabel));

		issue.addIssueLabel(issueLabels);

		return new CommonResponseDto(issue.getId());
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


	@Transactional(readOnly = true)
	public List<IssueListResponseDto> findBySearchCondition(IssueSearchRequestDto searchRequestDto,
		Long currentMemberId) {
		searchRequestDto.setCurrentMemberId(currentMemberId);
		addCurrentMemberToWritersInIssueSearchRequestDto(searchRequestDto, currentMemberId);

		List<Issue> issues = issueRepository.findBySearchCondition(searchRequestDto.isOpened(),
			searchRequestDto.getCurrentMemberId(),
			searchRequestDto.isCommentByMe(),
			searchRequestDto.isAssignedToMe(),
			searchRequestDto.getWriterId(),
			searchRequestDto.getLabelId(),
			searchRequestDto.getMilestoneId(),
			searchRequestDto.getTitle());

		return issues.stream()
			.map(Issue::toListResponse).collect(Collectors.toUnmodifiableList());
	}

	private void addCurrentMemberToWritersInIssueSearchRequestDto(IssueSearchRequestDto requestDto,
		Long currentMemberId) {
		if (requestDto.isWrittenByMe()) {
			requestDto.addMemberToWriters(currentMemberId);
		}
	}
}
