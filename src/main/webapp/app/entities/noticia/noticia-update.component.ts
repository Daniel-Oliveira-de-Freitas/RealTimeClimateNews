import { type Ref, computed, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import NoticiaService from './noticia.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { type INoticia, Noticia } from '@/shared/model/noticia.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'NoticiaUpdate',
  setup() {
    const noticiaService = inject('noticiaService', () => new NoticiaService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const noticia: Ref<INoticia> = ref(new Noticia());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'pt-br'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveNoticia = async noticiaId => {
      try {
        const res = await noticiaService().find(noticiaId);
        noticia.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.noticiaId) {
      retrieveNoticia(route.params.noticiaId);
    }

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      titulo: {},
      conteudo: {},
      dataPublicacao: {},
      autor: {},
      categoria: {},
      fonte: {},
      url: {},
      classificacao: {},
    };
    const v$ = useVuelidate(validationRules, noticia as any);
    v$.value.$validate();

    return {
      noticiaService,
      alertService,
      noticia,
      previousState,
      isSaving,
      currentLanguage,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.noticia.id) {
        this.noticiaService()
          .update(this.noticia)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('realTimeClimateNewsApp.noticia.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.noticiaService()
          .create(this.noticia)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('realTimeClimateNewsApp.noticia.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
