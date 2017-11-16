import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { LegalProcessMySuffix } from './legal-process-my-suffix.model';
import { LegalProcessMySuffixService } from './legal-process-my-suffix.service';

@Component({
    selector: 'jhi-legal-process-my-suffix-detail',
    templateUrl: './legal-process-my-suffix-detail.component.html'
})
export class LegalProcessMySuffixDetailComponent implements OnInit, OnDestroy {

    legalProcess: LegalProcessMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private legalProcessService: LegalProcessMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLegalProcesses();
    }

    load(id) {
        this.legalProcessService.find(id).subscribe((legalProcess) => {
            this.legalProcess = legalProcess;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInLegalProcesses() {
        this.eventSubscriber = this.eventManager.subscribe(
            'legalProcessListModification',
            (response) => this.load(this.legalProcess.id)
        );
    }
}
