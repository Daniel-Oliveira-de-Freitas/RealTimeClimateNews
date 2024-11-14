/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import NoticiaUpdate from './noticia-update.vue';
import NoticiaService from './noticia.service';
import AlertService from '@/shared/alert/alert.service';

type NoticiaUpdateComponentType = InstanceType<typeof NoticiaUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const noticiaSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<NoticiaUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Noticia Management Update Component', () => {
    let comp: NoticiaUpdateComponentType;
    let noticiaServiceStub: SinonStubbedInstance<NoticiaService>;

    beforeEach(() => {
      route = {};
      noticiaServiceStub = sinon.createStubInstance<NoticiaService>(NoticiaService);
      noticiaServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'b-input-group': true,
          'b-input-group-prepend': true,
          'b-form-datepicker': true,
          'b-form-input': true,
        },
        provide: {
          alertService,
          noticiaService: () => noticiaServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(NoticiaUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.noticia = noticiaSample;
        noticiaServiceStub.update.resolves(noticiaSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(noticiaServiceStub.update.calledWith(noticiaSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        noticiaServiceStub.create.resolves(entity);
        const wrapper = shallowMount(NoticiaUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.noticia = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(noticiaServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        noticiaServiceStub.find.resolves(noticiaSample);
        noticiaServiceStub.retrieve.resolves([noticiaSample]);

        // WHEN
        route = {
          params: {
            noticiaId: `${noticiaSample.id}`,
          },
        };
        const wrapper = shallowMount(NoticiaUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.noticia).toMatchObject(noticiaSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        noticiaServiceStub.find.resolves(noticiaSample);
        const wrapper = shallowMount(NoticiaUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
