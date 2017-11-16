import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { OfficeMySuffix } from './office-my-suffix.model';
import { OfficeMySuffixPopupService } from './office-my-suffix-popup.service';
import { OfficeMySuffixService } from './office-my-suffix.service';

@Component({
    selector: 'jhi-office-my-suffix-dialog',
    templateUrl: './office-my-suffix-dialog.component.html'
})
export class OfficeMySuffixDialogComponent implements OnInit {

    office: OfficeMySuffix;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private officeService: OfficeMySuffixService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.office.id !== undefined) {
            this.subscribeToSaveResponse(
                this.officeService.update(this.office));
        } else {
            this.subscribeToSaveResponse(
                this.officeService.create(this.office));
        }
    }

    private subscribeToSaveResponse(result: Observable<OfficeMySuffix>) {
        result.subscribe((res: OfficeMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: OfficeMySuffix) {
        this.eventManager.broadcast({ name: 'officeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-office-my-suffix-popup',
    template: ''
})
export class OfficeMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private officePopupService: OfficeMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.officePopupService
                    .open(OfficeMySuffixDialogComponent as Component, params['id']);
            } else {
                this.officePopupService
                    .open(OfficeMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
