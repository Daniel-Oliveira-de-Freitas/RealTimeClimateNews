/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import NoticiaDetails from './noticia-details.vue';
import NoticiaService from './noticia.service';
import AlertService from '@/shared/alert/alert.service';

type NoticiaDetailsComponentType = InstanceType<typeof NoticiaDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const noticiaSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Noticia Management Detail Component', () => {
    let noticiaServiceStub: SinonStubbedInstance<NoticiaService>;
    let mountOptions: MountingOptions<NoticiaDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      noticiaServiceStub = sinon.createStubInstance<NoticiaService>(NoticiaService);

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'router-link': true,
        },
        provide: {
          alertService,
          noticiaService: () => noticiaServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        noticiaServiceStub.find.resolves(noticiaSample);
        route = {
          params: {
            noticiaId: `${123}`,
          },
        };
        const wrapper = shallowMount(NoticiaDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.noticia).toMatchObject(noticiaSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        noticiaServiceStub.find.resolves(noticiaSample);
        const wrapper = shallowMount(NoticiaDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
