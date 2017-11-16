/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { DiscoveryTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { LegalProcessMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/legal-process/legal-process-my-suffix-detail.component';
import { LegalProcessMySuffixService } from '../../../../../../main/webapp/app/entities/legal-process/legal-process-my-suffix.service';
import { LegalProcessMySuffix } from '../../../../../../main/webapp/app/entities/legal-process/legal-process-my-suffix.model';

describe('Component Tests', () => {

    describe('LegalProcessMySuffix Management Detail Component', () => {
        let comp: LegalProcessMySuffixDetailComponent;
        let fixture: ComponentFixture<LegalProcessMySuffixDetailComponent>;
        let service: LegalProcessMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [DiscoveryTestModule],
                declarations: [LegalProcessMySuffixDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    LegalProcessMySuffixService,
                    JhiEventManager
                ]
            }).overrideTemplate(LegalProcessMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LegalProcessMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LegalProcessMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new LegalProcessMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.legalProcess).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
