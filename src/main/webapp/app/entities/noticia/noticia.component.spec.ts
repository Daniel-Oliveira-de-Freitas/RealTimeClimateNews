/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import Noticia from './noticia.vue';
import NoticiaService from './noticia.service';
import AlertService from '@/shared/alert/alert.service';

type NoticiaComponentType = InstanceType<typeof Noticia>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('Noticia Management Component', () => {
    let noticiaServiceStub: SinonStubbedInstance<NoticiaService>;
    let mountOptions: MountingOptions<NoticiaComponentType>['global'];

    beforeEach(() => {
      noticiaServiceStub = sinon.createStubInstance<NoticiaService>(NoticiaService);
      noticiaServiceStub.retrieve.resolves({ headers: {} });

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          bModal: bModalStub as any,
          'font-awesome-icon': true,
          'b-badge': true,
          'b-button': true,
          'router-link': true,
        },
        directives: {
          'b-modal': {},
        },
        provide: {
          alertService,
          noticiaService: () => noticiaServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        noticiaServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(Noticia, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(noticiaServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.noticias[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: NoticiaComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(Noticia, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        noticiaServiceStub.retrieve.reset();
        noticiaServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        noticiaServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeNoticia();
        await comp.$nextTick(); // clear components

        // THEN
        expect(noticiaServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(noticiaServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
