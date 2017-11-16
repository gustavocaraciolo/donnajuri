import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DiscoverySharedModule } from '../../shared';
import {
    LegalProcessMySuffixService,
    LegalProcessMySuffixPopupService,
    LegalProcessMySuffixComponent,
    LegalProcessMySuffixDetailComponent,
    LegalProcessMySuffixDialogComponent,
    LegalProcessMySuffixPopupComponent,
    LegalProcessMySuffixDeletePopupComponent,
    LegalProcessMySuffixDeleteDialogComponent,
    legalProcessRoute,
    legalProcessPopupRoute,
    LegalProcessMySuffixResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...legalProcessRoute,
    ...legalProcessPopupRoute,
];

@NgModule({
    imports: [
        DiscoverySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        LegalProcessMySuffixComponent,
        LegalProcessMySuffixDetailComponent,
        LegalProcessMySuffixDialogComponent,
        LegalProcessMySuffixDeleteDialogComponent,
        LegalProcessMySuffixPopupComponent,
        LegalProcessMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        LegalProcessMySuffixComponent,
        LegalProcessMySuffixDialogComponent,
        LegalProcessMySuffixPopupComponent,
        LegalProcessMySuffixDeleteDialogComponent,
        LegalProcessMySuffixDeletePopupComponent,
    ],
    providers: [
        LegalProcessMySuffixService,
        LegalProcessMySuffixPopupService,
        LegalProcessMySuffixResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DiscoveryLegalProcessMySuffixModule {}
