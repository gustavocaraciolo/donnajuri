import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { OfficeMySuffixComponent } from './office-my-suffix.component';
import { OfficeMySuffixDetailComponent } from './office-my-suffix-detail.component';
import { OfficeMySuffixPopupComponent } from './office-my-suffix-dialog.component';
import { OfficeMySuffixDeletePopupComponent } from './office-my-suffix-delete-dialog.component';

@Injectable()
export class OfficeMySuffixResolvePagingParams implements Resolve<any> {

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

export const officeRoute: Routes = [
    {
        path: 'office-my-suffix',
        component: OfficeMySuffixComponent,
        resolve: {
            'pagingParams': OfficeMySuffixResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Offices'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'office-my-suffix/:id',
        component: OfficeMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Offices'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const officePopupRoute: Routes = [
    {
        path: 'office-my-suffix-new',
        component: OfficeMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Offices'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'office-my-suffix/:id/edit',
        component: OfficeMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Offices'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'office-my-suffix/:id/delete',
        component: OfficeMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Offices'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
