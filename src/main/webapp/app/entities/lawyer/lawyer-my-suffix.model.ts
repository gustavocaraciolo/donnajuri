import { BaseEntity } from './../../shared';

export class LawyerMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public fullname?: string,
        public email?: string,
        public phone?: string,
        public legalProcesses?: BaseEntity[],
    ) {
    }
}
