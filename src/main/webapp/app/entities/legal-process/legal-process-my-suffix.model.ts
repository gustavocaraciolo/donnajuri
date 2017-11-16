import { BaseEntity } from './../../shared';

export const enum Status {
    'PENDENTE',
    'PERDIDO',
    'REALIZADO',
    'REALIZANDO'
}

export class LegalProcessMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public number?: string,
        public status?: Status,
        public adversypart?: string,
        public lawyers?: BaseEntity[],
    ) {
    }
}
