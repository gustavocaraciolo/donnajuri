import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { LegalProcessMySuffix } from './legal-process-my-suffix.model';
import { LegalProcessMySuffixPopupService } from './legal-process-my-suffix-popup.service';
import { LegalProcessMySuffixService } from './legal-process-my-suffix.service';
import { User, UserService } from '../../shared';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-legal-process-my-suffix-dialog',
    templateUrl: './legal-process-my-suffix-dialog.component.html'
})
export class LegalProcessMySuffixDialogComponent implements OnInit {

    legalProcess: LegalProcessMySuffix;
    isSaving: boolean;

    lawyers: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private legalProcessService: LegalProcessMySuffixService,
        private userService: UserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.findByAuthority('ROLE_COORDENADOR')
            .subscribe((res: ResponseWrapper) => { this.lawyers = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.legalProcess.id !== undefined) {
            this.subscribeToSaveResponse(
                this.legalProcessService.update(this.legalProcess));
        } else {
            this.subscribeToSaveResponse(
                this.legalProcessService.create(this.legalProcess));
        }
    }

    private subscribeToSaveResponse(result: Observable<LegalProcessMySuffix>) {
        result.subscribe((res: LegalProcessMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: LegalProcessMySuffix) {
        this.eventManager.broadcast({ name: 'legalProcessListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackLawyerById(index: number, item: User) {
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
    selector: 'jhi-legal-process-my-suffix-popup',
    template: ''
})
export class LegalProcessMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private legalProcessPopupService: LegalProcessMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.legalProcessPopupService
                    .open(LegalProcessMySuffixDialogComponent as Component, params['id']);
            } else {
                this.legalProcessPopupService
                    .open(LegalProcessMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
