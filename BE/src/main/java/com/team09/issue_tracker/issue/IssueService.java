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

	private final IssueValidateService issueValidateService;

	@Transactional(readOnly = true)
	public List<IssueListResponseDto> selectOpenedList(Long memberId) {
		List<Issue> issues = issueRepository.findByMemberIdAndIsOpened(memberId,
			true);

		List<IssueListResponseDto> response = issues.stream().map(Issue::toListResponse)
			.collect(Collectors.toList());

		return Collections.unmodifiableList(response);
	}

	/**
	 * 이슈 생성
	 *
	 * @param issueSaveRequestDto
	 * @param memberId
	 * @return
	 */
	@Transactional
	public CommonResponseDto create(IssueSaveRequestDto issueSaveRequestDto, Long memberId) {
		boolean isOpened = true;

		//1. mileStone
		Milestone milestone = createMilestone(issueSaveRequestDto, memberId);

		//2. Issue
		Issue issue = Issue.of(issueSaveRequestDto.getTitle(),
			issueSaveRequestDto.getContent(), memberId,
			isOpened, milestone);

		Issue savedIssue = issueRepository.save(issue);
		Long issueId = savedIssue.getId();

		//3. labelsIds
		List<Long> labelIds = issueSaveRequestDto.getLabelIds();
		validateLabelIds(labelIds, memberId);

		saveIssueLabel(savedIssue, issueId, labelIds);

		//4. assigneeIds
		List<Long> assigneeIds = issueSaveRequestDto.getAssigneeIds();
		validateAssigneeIds(assigneeIds);

		savedIssueAssignee(issue, savedIssue, assigneeIds);

		return savedIssue.toCommonResponse();
	}

	private Milestone createMilestone(IssueUpdateRequestDto issueUpdateRequestDto, Long memberId) {
		Optional.ofNullable(issueUpdateRequestDto.getMilestoneId())
			.ifPresent(
				milestoneId -> issueValidateService.validateMyMilestoneId(milestoneId, memberId));

		return Optional.ofNullable(issueUpdateRequestDto.getMilestoneId())
			.map(Milestone::of)
			.orElse(null);
	}

	private Milestone createMilestone(IssueSaveRequestDto issueSaveRequestDto, Long memberId) {
		Optional.ofNullable(issueSaveRequestDto.getMilestoneId())
			.ifPresent(
				milestoneId -> issueValidateService.validateMyMilestoneId(milestoneId, memberId));

		Milestone milestone = Optional.ofNullable(issueSaveRequestDto.getMilestoneId())
			.map(Milestone::of)
			.orElse(null);
		return milestone;
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

	/**
	 * 이슈 수정 - 이슈, 이슈 상태,연관 레이블, 마일스톤 모두 수정 가능
	 *
	 * @param IssueUpdateRequestDto
	 * @param issueId
	 * @return
	 */
	@Transactional
	public CommonResponseDto update(IssueUpdateRequestDto issueUpdateRequestDto, Long issueId,
		Long memberId) {

		List<Long> labelIds = issueUpdateRequestDto.getLabelIds();
		validateLabelIds(labelIds, memberId);

		Issue issue = issueRepository.findById(issueId)
			.orElseThrow(() -> new IssueNotFoundException());

		issue.setTitle(issueUpdateRequestDto.getTitle());
		issue.setContent(issueUpdateRequestDto.getContent());
		issue.setOpened(issueUpdateRequestDto.isOpened());
		issue.setMilestone(createMilestone(issueUpdateRequestDto, memberId));


		CommonResponseDto response = updateIssueLabel(
			issueUpdateRequestDto, issueId, issue);

		return response;
	}

	/**
	 * 	label 수정
	 * 		 1.기존리스트 issueLabels 검색
	 * 		 2.기존리스트에서 삭제된 레이블, DB삭제
	 * 		 3.기존리스트에 없는 추가로 선택된 레이블, DB추가
	 * @param issueUpdateRequestDto
	 * @param issueId
	 * @param issue
	 * @return
	 */
	private CommonResponseDto updateIssueLabel(IssueUpdateRequestDto issueUpdateRequestDto,
		Long issueId, Issue issue) {

		List<IssueLabel> issueLabels = issue.getIssueLabels();

		List<Long> editingLabelIds = issueUpdateRequestDto.getLabelIds();
		//Label을 모두 삭제할 경우
		if (issueUpdateRequestDto.getLabelIds().isEmpty()) {
			issueLabels.stream()
				.forEach(issueLabel -> issueLabelRepository.delete(issueLabel));
			issue.addIssueLabel(Collections.emptyList());

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
		deletedIssueLabels.stream()
			.forEach(issueLabel -> issueLabelRepository.delete(issueLabel));

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

	private void validateAssigneeIds(List<Long> assigneeIds) {
		if (assigneeIds.size() > 0) {
			issueValidateService.validateMember(assigneeIds);
		}
	}

	private void validateLabelIds(List<Long> labelIds, Long memberId) {
		if (labelIds.size() > 0) {
			issueValidateService.validateMyLabelIds(labelIds, memberId);
		}
	}
}
