<template>
  <a-layout-header class="header">
    <div class="logo">知识库</div>
    <a-menu
        v-model:selectedKeys="selectedKeys1"
        theme="dark"
        mode="horizontal"
        :style="{ lineHeight: '64px'}"
    >
      <router-link to="/"><a-menu-item key="1">首页</a-menu-item></router-link>
      <router-link to="/admin/ebook"><a-menu-item key="2">电子书</a-menu-item></router-link>
      <router-link to="/admin/category"><a-menu-item key="3">分类管理</a-menu-item></router-link>
      <router-link to="/about"><a-menu-item key="4">关于我们</a-menu-item></router-link>
    </a-menu>
  </a-layout-header>
</template>

<script>
import {ref} from "vue";

export default {
  name: "Header",
  setup(){
    return{
      selectedKeys1: ref(['1']),
    }
  }
}

</script>

<style scoped>
.logo{
  float: left;
  color: yellow;
}
</style>