<template>
  <a-layout>
    <Header/>
    <router-view/>
    <Footer/>
  </a-layout>
</template>

<script>
import { defineComponent, ref } from 'vue';
import Header from "./components/Header.vue";
import Footer from "./components/Footer.vue"
import Home from "./views/Home.vue";

export default defineComponent({
  components: {
    Header,
    Footer,
    Home,
  },
});
</script>

<style scoped>

</style>
