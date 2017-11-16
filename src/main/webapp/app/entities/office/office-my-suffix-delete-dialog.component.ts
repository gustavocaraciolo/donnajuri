import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { OfficeMySuffix } from './office-my-suffix.model';
import { OfficeMySuffixPopupService } from './office-my-suffix-popup.service';
import { OfficeMySuffixService } from './office-my-suffix.service';

@Component({
    selector: 'jhi-office-my-suffix-delete-dialog',
    templateUrl: './office-my-suffix-delete-dialog.component.html'
})
export class OfficeMySuffixDeleteDialogComponent {

    office: OfficeMySuffix;

    constructor(
        private officeService: OfficeMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.officeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'officeListModification',
                content: 'Deleted an office'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-office-my-suffix-delete-popup',
    template: ''
})
export class OfficeMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private officePopupService: OfficeMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.officePopupService
                .open(OfficeMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
