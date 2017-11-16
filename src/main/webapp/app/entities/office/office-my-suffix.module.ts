import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DiscoverySharedModule } from '../../shared';
import {
    OfficeMySuffixService,
    OfficeMySuffixPopupService,
    OfficeMySuffixComponent,
    OfficeMySuffixDetailComponent,
    OfficeMySuffixDialogComponent,
    OfficeMySuffixPopupComponent,
    OfficeMySuffixDeletePopupComponent,
    OfficeMySuffixDeleteDialogComponent,
    officeRoute,
    officePopupRoute,
    OfficeMySuffixResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...officeRoute,
    ...officePopupRoute,
];

@NgModule({
    imports: [
        DiscoverySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        OfficeMySuffixComponent,
        OfficeMySuffixDetailComponent,
        OfficeMySuffixDialogComponent,
        OfficeMySuffixDeleteDialogComponent,
        OfficeMySuffixPopupComponent,
        OfficeMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        OfficeMySuffixComponent,
        OfficeMySuffixDialogComponent,
        OfficeMySuffixPopupComponent,
        OfficeMySuffixDeleteDialogComponent,
        OfficeMySuffixDeletePopupComponent,
    ],
    providers: [
        OfficeMySuffixService,
        OfficeMySuffixPopupService,
        OfficeMySuffixResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DiscoveryOfficeMySuffixModule {}
