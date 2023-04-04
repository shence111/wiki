<template>
  <!--  新增-->
  <div>
    <a-button class="add-btn" type="primary" style="margin-bottom: 8px" @click="addCategory">新增</a-button>
    <!--  对话框-->

    <a-modal v-model:visible="visible1" :title="title" @ok="submit" style="width: 400px">
      <a-form
          :model="category"
          :label-col="{ span: 8 }"
          :wrapper-col="{ span: 16 }"
          autocomplete="off"
          style="margin-right: 80px"
          :rules="rules"
      >
        <a-form-item
            label="id"
            name="id"
            style="display:none"
        >
          <a-input v-model:value="category.id"/>
        </a-form-item>
        <a-form-item
            label="名称"
            name="name"
        >
          <a-input v-model:value="category.name"/>
        </a-form-item>
        <a-form-item
            label="父类"
            name="parent"
        >
          <a-space>
            <a-select v-model:value="category.parent" style="width: 120px" loading>
              <a-select-option value="0">无</a-select-option>
              <a-select-option value="100">前端开发</a-select-option>
              <a-select-option value="200">java</a-select-option>
              <a-select-option value="300">python</a-select-option>
              <a-select-option value="400">数据库</a-select-option>
              <a-select-option value="500">其他</a-select-option>
            </a-select>
          </a-space>
        </a-form-item>
        <a-form-item
            label="顺序"
            name="sort"

        >
          <a-input v-model:value="category.sort"/>
        </a-form-item>
      </a-form>
    </a-modal>
  </div>

  <!--  完成对话框-->
  <a-modal v-model:visible="visible2"
           status="success"
           :title="messages"
           sub-title="Order number: 2017182818828182881 Cloud server configuration takes 1-5 minutes, please wait."
  >

  </a-modal>

  <!--  搜索框-->
  <a-space direction="vertical">
    <a-input-search
        v-model:value="searchValue"
        placeholder="input search text"
        enter-button
        @search="onSearch"
    />
  </a-space>

  <!--  表格-->
  <a-table :columns="columns"
           :data-source="categories"
           :row-key="record => record.id"
           :pagination="false"
           :indent-size="50"

  >
    <template #bodyCell="{ column, record }">
      <template v-if="column.key === 'cover'">
        <img class="cover" :src="record.cover"/>
      </template>
      <template v-else-if="column.key === 'action'">
        <span>
            <span>
              <a-button type="primary" ghost @click="edit(record)">修改</a-button>
              <a-popconfirm
                  title="确定要删除吗?"
                  ok-text="Yes"
                  cancel-text="No"
                  @confirm="confirm(record.id)"
              >
                <a-button type="primary" ghost>删除</a-button>
              </a-popconfirm>
            </span>
          </span>
      </template>
    </template>

  </a-table>

</template>

<script>
import {computed, defineComponent, onMounted, reactive, ref} from 'vue';
import {CheckOutlined, EditOutlined} from '@ant-design/icons-vue';
import axios from "axios";
import {message} from 'ant-design-vue';

export default {
  name: "admin-ebook",

  components: {
    CheckOutlined,
    EditOutlined,
  },

  setup() {
    const categories = ref();
    const title = ref();
    const getCategory = () => {
      const params = {}
      if (searchValue.value) {
        params.name = searchValue.value;
      }
      axios.get('category/list', {params}).then((res) => {
        console.log(res)
        let data = res.data;
        if (data.code == 200) {
          categories.value = data.data;
        }
      });
    }

    // 对话框默认不显示
    const category = ref({});
    const visible1 = ref(false);
    const visible2 = ref(false);
    //点击显示对话框
    const addCategory = () => {
      category.value = {};
      title.value = '新增电子书分类';
      visible1.value = true;
    }
    //提交表单

    const messages = ref();
    const submit = () => {
      axios.post("/category/addCategory", category.value).then((res) => {
        if (res.data.code == 200) {
          messages.value = "操作成功";
          visible1.value = false;
          visible2.value = true;
          getCategory()
        } else if (res.data.code == 500) {
          messages.value = "操作失败";
          visible1.value = false;
          visible2.value = true;
        }
      })
    }

    //修改分类
    const edit = (record) => {
      console.log(record)
      title.value = '修改分类信息';
      category.value = JSON.parse(JSON.stringify(record));
      visible1.value = true;
    }

    //搜索框
    const searchValue = ref('');
    const onSearch = (searchValue) => {
      getCategory();
    };

    //表单验证
    const rules = {
      name: [{
        required: true,
        message: '请输入分类名称',
        trigger: 'blur'
      }],
      parent: [{
        required: true,
        message: '请输入父分类',
        trigger: 'blur'
      }],
    }

    const columns = [{
      title: 'name',
      dataIndex: 'name',
      key: '名称',
    }, {
      title: '父分类',
      dataIndex: 'parent',
      key: 'parent',
    }, {
      title: '排序',
      dataIndex: 'sort',
      key: 'sort',
    }, {
      title: '操作',
      key: 'action',
    }];


    //删除
    const remove = (id) => {
      axios.delete('/category/delete/' + id).then((res) => {
        if (res.data.code == 200) {
          message.success(res.data.message);
          getCategory()
        }
      })
    }

    const confirm = (id) => {
      remove(id)
      message.success('Click on Yes');
    };

    return {
      categories,
      columns,
      visible1,
      visible2,
      addCategory,
      submit,
      category,
      rules,
      confirm,
      onSearch,
      searchValue,
      remove,
      edit,
      title,
      getCategory
    };
  },
  mounted() {
    this.getCategory();
  }
}
</script>

<style scoped>
.cover {
  height: 40px;
}

.add-btn {
  width: 100px;
  height: 30px;
}
</style>