<template>
  <div>
    <h2 id="page-heading" data-cy="NoticiaHeading">
      <span v-text="t$('realTimeClimateNewsApp.noticia.home.title')" id="noticia-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" @click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('realTimeClimateNewsApp.noticia.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'NoticiaCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-noticia"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('realTimeClimateNewsApp.noticia.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && noticias && noticias.length === 0">
      <span v-text="t$('realTimeClimateNewsApp.noticia.home.notFound')"></span>
    </div>

    <div v-if="noticias && noticias.length > 0">
      <div
        class="card mb-3 w-100"
        v-for="noticia in noticias"
        :key="noticia.id"
        @click="goToNoticiaDetails(noticia.id)"
        style="cursor: pointer;"
      >
        <div class="row no-gutters">
          <div class="col-md-2">
            <img
              :src="noticia.url"
              class="card-img"
              alt="Imagem da notícia"
            />
          </div>
          <div class="col-md-8 card-text-container">
            <div class="card-body">
              <h5 class="card-title" v-text="noticia.titulo"></h5>
              <p class="card-text text-truncate" v-text="noticia.conteudo"></p>
              <p class="card-text">
                <small class="text-muted" v-text="formatDate(noticia.dataPublicacao)"></small>
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./noticia.component.ts"></script>

<style scoped>
.card {
  transition: transform 0.2s;
}

.card:hover {
  transform: scale(1.02);
}

.card-text.text-truncate {
  max-height: 60px;
  overflow: hidden;
}

.card-img {
  width: 100%;
  height: auto;
  max-width: 300px;
  max-height: 300px;
  object-fit: cover;
}
</style>
