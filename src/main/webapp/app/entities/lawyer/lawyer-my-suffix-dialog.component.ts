import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { LawyerMySuffix } from './lawyer-my-suffix.model';
import { LawyerMySuffixPopupService } from './lawyer-my-suffix-popup.service';
import { LawyerMySuffixService } from './lawyer-my-suffix.service';
import { LegalProcessMySuffix, LegalProcessMySuffixService } from '../legal-process';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-lawyer-my-suffix-dialog',
    templateUrl: './lawyer-my-suffix-dialog.component.html'
})
export class LawyerMySuffixDialogComponent implements OnInit {

    lawyer: LawyerMySuffix;
    isSaving: boolean;

    legalprocesses: LegalProcessMySuffix[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private lawyerService: LawyerMySuffixService,
        private legalProcessService: LegalProcessMySuffixService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.legalProcessService.query()
            .subscribe((res: ResponseWrapper) => { this.legalprocesses = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.lawyer.id !== undefined) {
            this.subscribeToSaveResponse(
                this.lawyerService.update(this.lawyer));
        } else {
            this.subscribeToSaveResponse(
                this.lawyerService.create(this.lawyer));
        }
    }

    private subscribeToSaveResponse(result: Observable<LawyerMySuffix>) {
        result.subscribe((res: LawyerMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: LawyerMySuffix) {
        this.eventManager.broadcast({ name: 'lawyerListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackLegalProcessById(index: number, item: LegalProcessMySuffix) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-lawyer-my-suffix-popup',
    template: ''
})
export class LawyerMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private lawyerPopupService: LawyerMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.lawyerPopupService
                    .open(LawyerMySuffixDialogComponent as Component, params['id']);
            } else {
                this.lawyerPopupService
                    .open(LawyerMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
