import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

const Noticia = () => import('@/entities/noticia/noticia.vue');
const NoticiaUpdate = () => import('@/entities/noticia/noticia-update.vue');
const NoticiaDetails = () => import('@/entities/noticia/noticia-details.vue');

// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'noticia',
      name: 'Noticia',
      component: Noticia,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'noticia/new',
      name: 'NoticiaCreate',
      component: NoticiaUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'noticia/:noticiaId/edit',
      name: 'NoticiaEdit',
      component: NoticiaUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'noticia/:noticiaId/view',
      name: 'NoticiaView',
      component: NoticiaDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
