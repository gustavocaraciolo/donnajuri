import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DiscoverySharedModule } from '../../shared';
import {
    LawyerMySuffixService,
    LawyerMySuffixPopupService,
    LawyerMySuffixComponent,
    LawyerMySuffixDetailComponent,
    LawyerMySuffixDialogComponent,
    LawyerMySuffixPopupComponent,
    LawyerMySuffixDeletePopupComponent,
    LawyerMySuffixDeleteDialogComponent,
    lawyerRoute,
    lawyerPopupRoute,
    LawyerMySuffixResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...lawyerRoute,
    ...lawyerPopupRoute,
];

@NgModule({
    imports: [
        DiscoverySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        LawyerMySuffixComponent,
        LawyerMySuffixDetailComponent,
        LawyerMySuffixDialogComponent,
        LawyerMySuffixDeleteDialogComponent,
        LawyerMySuffixPopupComponent,
        LawyerMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        LawyerMySuffixComponent,
        LawyerMySuffixDialogComponent,
        LawyerMySuffixPopupComponent,
        LawyerMySuffixDeleteDialogComponent,
        LawyerMySuffixDeletePopupComponent,
    ],
    providers: [
        LawyerMySuffixService,
        LawyerMySuffixPopupService,
        LawyerMySuffixResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DiscoveryLawyerMySuffixModule {}
