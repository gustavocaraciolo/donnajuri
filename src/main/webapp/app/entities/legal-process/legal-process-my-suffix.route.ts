import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { LegalProcessMySuffixComponent } from './legal-process-my-suffix.component';
import { LegalProcessMySuffixDetailComponent } from './legal-process-my-suffix-detail.component';
import { LegalProcessMySuffixPopupComponent } from './legal-process-my-suffix-dialog.component';
import { LegalProcessMySuffixDeletePopupComponent } from './legal-process-my-suffix-delete-dialog.component';

@Injectable()
export class LegalProcessMySuffixResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const legalProcessRoute: Routes = [
    {
        path: 'legal-process-my-suffix',
        component: LegalProcessMySuffixComponent,
        resolve: {
            'pagingParams': LegalProcessMySuffixResolvePagingParams
        },
        data: {
            authorities: ['ROLE_ADMIN','ROLE_COORDENADOR','ROLE_ADVOGADO'],
            pageTitle: 'LegalProcesses'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'legal-process-my-suffix/:id',
        component: LegalProcessMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_ADMIN','ROLE_COORDENADOR','ROLE_ADVOGADO'],
            pageTitle: 'LegalProcesses'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const legalProcessPopupRoute: Routes = [
    {
        path: 'legal-process-my-suffix-new',
        component: LegalProcessMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN','ROLE_COORDENADOR','ROLE_ADVOGADO'],
            pageTitle: 'LegalProcesses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'legal-process-my-suffix/:id/edit',
        component: LegalProcessMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN','ROLE_COORDENADOR','ROLE_ADVOGADO'],
            pageTitle: 'LegalProcesses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'legal-process-my-suffix/:id/delete',
        component: LegalProcessMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN','ROLE_COORDENADOR','ROLE_ADVOGADO'],
            pageTitle: 'LegalProcesses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
