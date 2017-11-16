import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { LawyerMySuffix } from './lawyer-my-suffix.model';
import { LawyerMySuffixService } from './lawyer-my-suffix.service';

@Component({
    selector: 'jhi-lawyer-my-suffix-detail',
    templateUrl: './lawyer-my-suffix-detail.component.html'
})
export class LawyerMySuffixDetailComponent implements OnInit, OnDestroy {

    lawyer: LawyerMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private lawyerService: LawyerMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLawyers();
    }

    load(id) {
        this.lawyerService.find(id).subscribe((lawyer) => {
            this.lawyer = lawyer;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInLawyers() {
        this.eventSubscriber = this.eventManager.subscribe(
            'lawyerListModification',
            (response) => this.load(this.lawyer.id)
        );
    }
}
