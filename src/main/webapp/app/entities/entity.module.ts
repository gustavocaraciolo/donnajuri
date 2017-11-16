import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { DiscoveryOfficeMySuffixModule } from './office/office-my-suffix.module';
import { DiscoveryLawyerMySuffixModule } from './lawyer/lawyer-my-suffix.module';
import { DiscoveryLegalProcessMySuffixModule } from './legal-process/legal-process-my-suffix.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        DiscoveryOfficeMySuffixModule,
        DiscoveryLawyerMySuffixModule,
        DiscoveryLegalProcessMySuffixModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DiscoveryEntityModule {}
