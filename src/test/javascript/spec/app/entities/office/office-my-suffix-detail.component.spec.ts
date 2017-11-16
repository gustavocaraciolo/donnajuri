/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { DiscoveryTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { OfficeMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/office/office-my-suffix-detail.component';
import { OfficeMySuffixService } from '../../../../../../main/webapp/app/entities/office/office-my-suffix.service';
import { OfficeMySuffix } from '../../../../../../main/webapp/app/entities/office/office-my-suffix.model';

describe('Component Tests', () => {

    describe('OfficeMySuffix Management Detail Component', () => {
        let comp: OfficeMySuffixDetailComponent;
        let fixture: ComponentFixture<OfficeMySuffixDetailComponent>;
        let service: OfficeMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [DiscoveryTestModule],
                declarations: [OfficeMySuffixDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    OfficeMySuffixService,
                    JhiEventManager
                ]
            }).overrideTemplate(OfficeMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OfficeMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OfficeMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new OfficeMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.office).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
