package com.team09.issue_tracker.issue;


import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DisplayName("IssueRepository 이슈 생성")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class IssueCreateRepositoryTest {

	@Autowired
	IssueRepository issueRepository;
	final Long MEMBER_ID = 1L;

	/** TODO : repository 테스트인데, 일부 서비스 테스트 요소가 들어가 있는 것 같다.
	 		 왜냐면, request parameter를 엔티티로 변환하는 과정이 들어가 있는 것 같다.
	 **/
//	@DisplayName("필수 요소만 가진 이슈")
//	@Test
//	void saveOnlyMandatory() {
//		//given
//		IssueSaveRequestDto issueSaveRequestDto = IssueSaveRequestDto.builder()
//			.title("제목")
//			.content("내용")
//			.build();
//		boolean isOpened = true;
//		Issue issue = Issue.from(issueSaveRequestDto, isOpened, MEMBER_ID);
//
//		//when
//		Issue savedIssue = issueRepository.save(issue);
//
//		//then
//		assertAll(
//			() -> assertThat(savedIssue.getTitle()).isEqualTo("제목"),
//			() -> assertThat(savedIssue.getContent()).isEqualTo("내용")
//		);
//	}
	

}
