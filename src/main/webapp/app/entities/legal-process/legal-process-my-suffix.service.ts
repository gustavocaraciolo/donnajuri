import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { LegalProcessMySuffix } from './legal-process-my-suffix.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class LegalProcessMySuffixService {

    private resourceUrl = SERVER_API_URL + 'api/legal-processes';

    constructor(private http: Http) { }

    create(legalProcess: LegalProcessMySuffix): Observable<LegalProcessMySuffix> {
        const copy = this.convert(legalProcess);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(legalProcess: LegalProcessMySuffix): Observable<LegalProcessMySuffix> {
        const copy = this.convert(legalProcess);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<LegalProcessMySuffix> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to LegalProcessMySuffix.
     */
    private convertItemFromServer(json: any): LegalProcessMySuffix {
        const entity: LegalProcessMySuffix = Object.assign(new LegalProcessMySuffix(), json);
        return entity;
    }

    /**
     * Convert a LegalProcessMySuffix to a JSON which can be sent to the server.
     */
    private convert(legalProcess: LegalProcessMySuffix): LegalProcessMySuffix {
        const copy: LegalProcessMySuffix = Object.assign({}, legalProcess);
        return copy;
    }
}
