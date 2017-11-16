import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { OfficeMySuffix } from './office-my-suffix.model';
import { OfficeMySuffixService } from './office-my-suffix.service';

@Component({
    selector: 'jhi-office-my-suffix-detail',
    templateUrl: './office-my-suffix-detail.component.html'
})
export class OfficeMySuffixDetailComponent implements OnInit, OnDestroy {

    office: OfficeMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private officeService: OfficeMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInOffices();
    }

    load(id) {
        this.officeService.find(id).subscribe((office) => {
            this.office = office;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInOffices() {
        this.eventSubscriber = this.eventManager.subscribe(
            'officeListModification',
            (response) => this.load(this.office.id)
        );
    }
}
