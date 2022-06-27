//
//  EditingIssueViewModel.swift
//  IssueTracker
//
//  Created by 김한솔 on 2022/06/23.
//

import Foundation
import UIKit

protocol EditingIssueViewModelInput {
    func didTouchCancel()
    func didTouchSave()
    func didChangeSegmentValue(index: Int)
}

protocol EditingIssueViewModelOutput {
    var titleText: Observable<String> { get }
    var contentText: Observable<String> { get }
    var cancelButtonState: Observable<Bool> { get }
    var saveButtonState: Observable<Bool> { get }
    var segmentIndex: Observable<Int> { get }
    var error: Observable<String> { get }
}

protocol EditingIssueViewModelProtocol: EditingIssueViewModelInput, EditingIssueViewModelOutput { }

final class EditingIssueViewModel: EditingIssueViewModelProtocol {
    private let issueManager: EditingIssueManagable

    private(set) var titleText: Observable<String> = Observable("")
    private(set) var contentText: Observable<String> = Observable("")
    private(set) var cancelButtonState: Observable<Bool> = Observable(false)
    private(set) var saveButtonState: Observable<Bool> = Observable(false)
    private(set) var segmentIndex: Observable<Int> = Observable(0)
    private(set) var error: Observable<String> = Observable("")

    init(useCase: EditingIssueManagable) {
        self.issueManager = useCase
    }

    func didChangeSegmentValue(index: Int) {
        segmentIndex.value = index
    }

    func didTouchCancel() {
        cancelButtonState.value = true
    }

    func didTouchSave() {
        saveButtonState.value = true
        let issueEntity = IssueItem(id: -1, title: titleText.value, content: contentText.value, milestoneName: "", labels: [])

        issueManager.sendNewIssue(issueEntity) { [weak self] (result) in
            switch result {
            case .success(let idDictionary):
                guard idDictionary["id"] != nil else { return }
                self?.saveButtonState.value = true
                // IssueViewModel에 저장됐음을 알리는 로직 필요
            case .failure(let error):
                self?.saveButtonState.value = false
                self?.error.value = error.localizedDescription
            }
        }
    }
}
