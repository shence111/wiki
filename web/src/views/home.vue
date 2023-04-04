<template>
  <a-layout-content style="padding: 0 50px">

    <a-layout style="padding: 24px 0; background: #fff">
      <a-layout-sider width="200" style="background: #fff">
        <a-menu
            v-model:selectedKeys="selectedKeys"
            v-model:openKeys="openKeys"
            mode="inline"
            style="height: 100%"
            @click="handlerSelected"
        >
          <a-sub-menu v-for="(category, index) in categorise" :key="'sub' + index">
            <template #title>
                <span>
                  <user-outlined/>
                  {{ category.name }}
                </span>
            </template>
            <a-menu-item v-for="(children, childrenId) in category.children" :key="children.id">
              {{ children.name }}
            </a-menu-item>
          </a-sub-menu>
        </a-menu>
      </a-layout-sider>
      <a-layout-content :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }">
        <!--    list    -->
        <a-list item-layout="vertical" size="large" :data-source="ebooks" :pagination="pagination"
                :grid="{columu:3, gutter:80}">
          <template #renderItem="{ item }">
            <a-list-item key="item.title">
              <template #actions>
          <span v-for="{ type, text } in actions" :key="type">
            <component :is="type" style="margin-right: 8px"/>
            {{ text }}
          </span>
              </template>
              <a-list-item-meta :description="item.description">
                <template #title>
                  {{ item.name }}
                </template>
                <template #avatar>
                  <a-avatar :src="item.cover"/>
                </template>
              </a-list-item-meta>
            </a-list-item>
          </template>
        </a-list>
      </a-layout-content>
    </a-layout>
  </a-layout-content>
</template>

<script>
import {onMounted, ref, defineComponent} from "vue";
import {
  LaptopOutlined,
  NotificationOutlined,
  UserOutlined,
  StarOutlined,
  LikeOutlined,
  MessageOutlined
} from "@ant-design/icons-vue";
import axios from 'axios'

export default {
  name: "Home",
  components: {
    UserOutlined,
    LaptopOutlined,
    NotificationOutlined,
    StarOutlined,
    LikeOutlined,
    MessageOutlined,
  },
  setup() {
    const ebooks = ref();

    const selectedKeys = ref([])


    const getEbook = () => {
      const params = {category2Id :selectedKeys.value[0]}
      axios.get('ebook/list' , {params}).then((res) => {
        ebooks.value = res.data.data;
      })

    }

    const actions = [{
      type: 'StarOutlined',
      text: '156',
    }, {
      type: 'LikeOutlined',
      text: '156',
    }, {
      type: 'MessageOutlined',
      text: '2',
    }];


    const pagination = {

      pageSize: 3,
    };
    //首页菜单

    const handlerSelected = (r) => {
      selectedKeys.value = [r.key]
      getEbook()
    }

    const category = ref()

    const categorise = ref([]);

    const getCategories = () => {
      const params = {};
      axios.get("/category/list", {params}).then((res) => {
        if (res.data.code == 200) {
          categorise.value = res.data.data;
          selectedKeys.value = [categorise.value[0].children[0].id]
          getEbook()
        }
      })
    }

    return {
      selectedKeys,
      collapsed: ref(false),
      openKeys: ref(['sub0']),
      ebooks,
      actions,
      pagination,
      getEbook,
      getCategories,
      categorise,
      category,
      handlerSelected
    };
  },
  mounted() {
    this.getCategories()
  }
}
</script>

<style scoped>

</style>