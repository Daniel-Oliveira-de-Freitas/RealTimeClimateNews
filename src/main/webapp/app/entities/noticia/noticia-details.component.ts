import { type Ref, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import NoticiaService from './noticia.service';
import { type INoticia } from '@/shared/model/noticia.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'NoticiaDetails',
  setup() {
    const noticiaService = inject('noticiaService', () => new NoticiaService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const noticia: Ref<INoticia> = ref({});

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

    return {
      alertService,
      noticia,

      previousState,
      t$: useI18n().t,
    };
  },
});
