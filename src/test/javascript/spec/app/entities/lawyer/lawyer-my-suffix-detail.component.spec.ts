/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { DiscoveryTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { LawyerMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/lawyer/lawyer-my-suffix-detail.component';
import { LawyerMySuffixService } from '../../../../../../main/webapp/app/entities/lawyer/lawyer-my-suffix.service';
import { LawyerMySuffix } from '../../../../../../main/webapp/app/entities/lawyer/lawyer-my-suffix.model';

describe('Component Tests', () => {

    describe('LawyerMySuffix Management Detail Component', () => {
        let comp: LawyerMySuffixDetailComponent;
        let fixture: ComponentFixture<LawyerMySuffixDetailComponent>;
        let service: LawyerMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [DiscoveryTestModule],
                declarations: [LawyerMySuffixDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    LawyerMySuffixService,
                    JhiEventManager
                ]
            }).overrideTemplate(LawyerMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LawyerMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LawyerMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new LawyerMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.lawyer).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
