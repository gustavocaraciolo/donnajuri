import { BaseEntity } from './../../shared';

export class OfficeMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public phone?: string,
        public email?: string,
    ) {
    }
}
