import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LawyerMySuffix } from './lawyer-my-suffix.model';
import { LawyerMySuffixPopupService } from './lawyer-my-suffix-popup.service';
import { LawyerMySuffixService } from './lawyer-my-suffix.service';

@Component({
    selector: 'jhi-lawyer-my-suffix-delete-dialog',
    templateUrl: './lawyer-my-suffix-delete-dialog.component.html'
})
export class LawyerMySuffixDeleteDialogComponent {

    lawyer: LawyerMySuffix;

    constructor(
        private lawyerService: LawyerMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.lawyerService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'lawyerListModification',
                content: 'Deleted an lawyer'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-lawyer-my-suffix-delete-popup',
    template: ''
})
export class LawyerMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private lawyerPopupService: LawyerMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.lawyerPopupService
                .open(LawyerMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
