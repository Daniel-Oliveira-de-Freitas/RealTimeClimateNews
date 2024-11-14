<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate @submit.prevent="save()">
        <h2
          id="realTimeClimateNewsApp.noticia.home.createOrEditLabel"
          data-cy="NoticiaCreateUpdateHeading"
          v-text="t$('realTimeClimateNewsApp.noticia.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="noticia.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="noticia.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('realTimeClimateNewsApp.noticia.titulo')" for="noticia-titulo"></label>
            <input
              type="text"
              class="form-control"
              name="titulo"
              id="noticia-titulo"
              data-cy="titulo"
              :class="{ valid: !v$.titulo.$invalid, invalid: v$.titulo.$invalid }"
              v-model="v$.titulo.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('realTimeClimateNewsApp.noticia.conteudo')" for="noticia-conteudo"></label>
            <input
              type="text"
              class="form-control"
              name="conteudo"
              id="noticia-conteudo"
              data-cy="conteudo"
              :class="{ valid: !v$.conteudo.$invalid, invalid: v$.conteudo.$invalid }"
              v-model="v$.conteudo.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('realTimeClimateNewsApp.noticia.dataPublicacao')"
              for="noticia-dataPublicacao"
            ></label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="noticia-dataPublicacao"
                  v-model="v$.dataPublicacao.$model"
                  name="dataPublicacao"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="noticia-dataPublicacao"
                data-cy="dataPublicacao"
                type="text"
                class="form-control"
                name="dataPublicacao"
                :class="{ valid: !v$.dataPublicacao.$invalid, invalid: v$.dataPublicacao.$invalid }"
                v-model="v$.dataPublicacao.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('realTimeClimateNewsApp.noticia.autor')" for="noticia-autor"></label>
            <input
              type="text"
              class="form-control"
              name="autor"
              id="noticia-autor"
              data-cy="autor"
              :class="{ valid: !v$.autor.$invalid, invalid: v$.autor.$invalid }"
              v-model="v$.autor.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('realTimeClimateNewsApp.noticia.categoria')" for="noticia-categoria"></label>
            <input
              type="text"
              class="form-control"
              name="categoria"
              id="noticia-categoria"
              data-cy="categoria"
              :class="{ valid: !v$.categoria.$invalid, invalid: v$.categoria.$invalid }"
              v-model="v$.categoria.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('realTimeClimateNewsApp.noticia.fonte')" for="noticia-fonte"></label>
            <input
              type="text"
              class="form-control"
              name="fonte"
              id="noticia-fonte"
              data-cy="fonte"
              :class="{ valid: !v$.fonte.$invalid, invalid: v$.fonte.$invalid }"
              v-model="v$.fonte.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('realTimeClimateNewsApp.noticia.url')" for="noticia-url"></label>
            <input
              type="text"
              class="form-control"
              name="url"
              id="noticia-url"
              data-cy="url"
              :class="{ valid: !v$.url.$invalid, invalid: v$.url.$invalid }"
              v-model="v$.url.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('realTimeClimateNewsApp.noticia.classificacao')"
              for="noticia-classificacao"
            ></label>
            <input
              type="text"
              class="form-control"
              name="classificacao"
              id="noticia-classificacao"
              data-cy="classificacao"
              :class="{ valid: !v$.classificacao.$invalid, invalid: v$.classificacao.$invalid }"
              v-model="v$.classificacao.$model"
            />
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" @click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.cancel')"></span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="v$.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.save')"></span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./noticia-update.component.ts"></script>
