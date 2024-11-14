<template>
  <div class="home row">

    <div class="col-md-9">
      <h1 class="display-4" v-text="t$('home.title')"></h1>
      <p class="lead" v-text="t$('home.subtitle')"></p>

      <div>
        <div class="alert alert-success" v-if="authenticated">
          <span v-if="username" v-text="t$('home.logged.message', { username: username })"></span>
        </div>

        <div class="alert alert-warning" v-if="!authenticated">
          <span v-text="t$('global.messages.info.authenticated.prefix')"></span>
          <a class="alert-link" @click="openLogin()" v-text="t$('global.messages.info.authenticated.link')"></a>
          <span v-html="t$('global.messages.info.authenticated.suffix')"></span>
        </div>
        <div class="alert alert-warning" v-if="!authenticated">
          <span v-text="t$('global.messages.info.register.noaccount')"></span>&nbsp;
          <router-link class="alert-link" to="/register" v-text="t$('global.messages.info.register.link')"></router-link>
        </div>
        <div class="d-flex justify-content-center align-items-center">
          <span class="logo-img hipster"></span>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./home.component.ts"></script>

<style scoped>
.home {
  display: flex;
  align-items: center;
  justify-content: center;
}

.logo-img {
  height: 900px;
  width: 900px;
  background: url('/content/images/ClimaTempo.png') no-repeat center center;
  background-size: contain;
  filter: drop-shadow(0 0 0.05rem white);
  margin-top: -200px;
}
</style>
