import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { LawyerMySuffixComponent } from './lawyer-my-suffix.component';
import { LawyerMySuffixDetailComponent } from './lawyer-my-suffix-detail.component';
import { LawyerMySuffixPopupComponent } from './lawyer-my-suffix-dialog.component';
import { LawyerMySuffixDeletePopupComponent } from './lawyer-my-suffix-delete-dialog.component';

@Injectable()
export class LawyerMySuffixResolvePagingParams implements Resolve<any> {

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

export const lawyerRoute: Routes = [
    {
        path: 'lawyer-my-suffix',
        component: LawyerMySuffixComponent,
        resolve: {
            'pagingParams': LawyerMySuffixResolvePagingParams
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_COORDENADOR'],
            pageTitle: 'Lawyers'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'lawyer-my-suffix/:id',
        component: LawyerMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_COORDENADOR'],
            pageTitle: 'Lawyers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const lawyerPopupRoute: Routes = [
    {
        path: 'lawyer-my-suffix-new',
        component: LawyerMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_COORDENADOR'],
            pageTitle: 'Lawyers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'lawyer-my-suffix/:id/edit',
        component: LawyerMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_COORDENADOR'],
            pageTitle: 'Lawyers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'lawyer-my-suffix/:id/delete',
        component: LawyerMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_COORDENADOR'],
            pageTitle: 'Lawyers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
