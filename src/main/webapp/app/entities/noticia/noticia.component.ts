import { type Ref, defineComponent, inject, onMounted, ref, getCurrentInstance } from 'vue';
import { useI18n } from 'vue-i18n';

import NoticiaService from './noticia.service';
import { type INoticia } from '@/shared/model/noticia.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Noticia',
  setup() {
    const { t: t$ } = useI18n();
    const noticiaService = inject('noticiaService', () => new NoticiaService());
    const alertService = inject('alertService', () => useAlertService(), true);
    const { proxy } = getCurrentInstance()!;

    const noticias: Ref<INoticia[]> = ref([]);
    const isFetching = ref(false);

    const retrieveNoticias = async () => {
      isFetching.value = true;
      try {
        const res = await noticiaService().retrieve();
        noticias.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveNoticias();
    };

    onMounted(async () => {
      await retrieveNoticias();
    });

    const removeId: Ref<number | null> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: INoticia) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeNoticia = async () => {
      try {
        await noticiaService().delete(removeId.value!);
        const message = t$('realTimeClimateNewsApp.noticia.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveNoticias();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error);
      }
    };

    const goToNoticiaDetails = (noticiaId: number) => {
      proxy.$router.push({ name: 'NoticiaView', params: { noticiaId } });
    };

    const formatDate = (date: string) => {
      const options: Intl.DateTimeFormatOptions = { year: 'numeric', month: 'long', day: 'numeric' };
      return new Date(date).toLocaleDateString('pt-BR', options);
    };

    return {
      noticias,
      isFetching,
      retrieveNoticias,
      handleSyncList,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeNoticia,
      goToNoticiaDetails,
      formatDate,
      t$,
    };
  },
});
