<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="noticia">
        <h2 class="jh-entity-heading" data-cy="noticiaDetailsHeading">
          <span v-text="t$('realTimeClimateNewsApp.noticia.detail.title')"></span> {{ noticia.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('realTimeClimateNewsApp.noticia.titulo')"></span>
          </dt>
          <dd>
            <span>{{ noticia.titulo }}</span>
          </dd>
          <dt>
            <span v-text="t$('realTimeClimateNewsApp.noticia.conteudo')"></span>
          </dt>
          <dd>
            <span>{{ noticia.conteudo }}</span>
          </dd>
          <dt>
            <span v-text="t$('realTimeClimateNewsApp.noticia.dataPublicacao')"></span>
          </dt>
          <dd>
            <span>{{ noticia.dataPublicacao }}</span>
          </dd>
          <dt>
            <span v-text="t$('realTimeClimateNewsApp.noticia.autor')"></span>
          </dt>
          <dd>
            <span>{{ noticia.autor }}</span>
          </dd>
          <dt>
            <span v-text="t$('realTimeClimateNewsApp.noticia.categoria')"></span>
          </dt>
          <dd>
            <span>{{ noticia.categoria }}</span>
          </dd>
          <dt>
            <span v-text="t$('realTimeClimateNewsApp.noticia.fonte')"></span>
          </dt>
          <dd>
            <span>{{ noticia.fonte }}</span>
          </dd>
          <dt>
            <span v-text="t$('realTimeClimateNewsApp.noticia.url')"></span>
          </dt>
          <dd>
            <span>{{ noticia.url }}</span>
          </dd>
          <dt>
            <span v-text="t$('realTimeClimateNewsApp.noticia.classificacao')"></span>
          </dt>
          <dd>
            <span>{{ noticia.classificacao }}</span>
          </dd>
        </dl>
        <button type="submit" @click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="noticia.id" :to="{ name: 'NoticiaEdit', params: { noticiaId: noticia.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./noticia-details.component.ts"></script>
