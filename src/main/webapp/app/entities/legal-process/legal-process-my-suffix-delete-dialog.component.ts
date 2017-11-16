import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LegalProcessMySuffix } from './legal-process-my-suffix.model';
import { LegalProcessMySuffixPopupService } from './legal-process-my-suffix-popup.service';
import { LegalProcessMySuffixService } from './legal-process-my-suffix.service';

@Component({
    selector: 'jhi-legal-process-my-suffix-delete-dialog',
    templateUrl: './legal-process-my-suffix-delete-dialog.component.html'
})
export class LegalProcessMySuffixDeleteDialogComponent {

    legalProcess: LegalProcessMySuffix;

    constructor(
        private legalProcessService: LegalProcessMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.legalProcessService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'legalProcessListModification',
                content: 'Deleted an legalProcess'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-legal-process-my-suffix-delete-popup',
    template: ''
})
export class LegalProcessMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private legalProcessPopupService: LegalProcessMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.legalProcessPopupService
                .open(LegalProcessMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
