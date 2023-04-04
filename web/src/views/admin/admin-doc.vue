<template>
  <!--  新增-->
  <div>
    <a-button class="add-btn" type="primary" style="margin-bottom: 8px" @click="addDoc">新增</a-button>
    <!--  对话框-->

    <a-modal v-model:visible="visible1" :title="title" @ok="submit" style="width: 400px">
      <a-form
          :model="doc"
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
          <a-input v-model:value="doc.id"/>
        </a-form-item>
        <a-form-item
            label="名称"
            name="name"
        >
          <a-input v-model:value="doc.name"/>
        </a-form-item>
<!--        <a-form-item-->
<!--            label="父类"-->
<!--            name="parent"-->
<!--        >-->
<!--          <a-space>-->
<!--            <a-select v-model:value="doc.parent" style="width: 120px" loading>-->
<!--              <a-select-option value="0">无</a-select-option>-->
<!--              <a-select-option value="100">前端开发</a-select-option>-->
<!--              <a-select-option value="200">java</a-select-option>-->
<!--              <a-select-option value="300">python</a-select-option>-->
<!--              <a-select-option value="400">数据库</a-select-option>-->
<!--              <a-select-option value="500">其他</a-select-option>-->
<!--            </a-select>-->
<!--          </a-space>-->
<!--        </a-form-item>-->
        <a-form-item
            label="分类"
            name="parent"
        >
          <a-select
              v-model:value="doc.parent"
              show-search
              placeholder="请选择父文档"
              style="width: 180px"
              :options="searchOptions"
              :field-names="{ label: 'name', value: 'id'}"
              :filter-option="filterOption"
          ></a-select>
        </a-form-item>

        <a-form-item
            label="顺序"
            name="sort"
        >
          <a-input v-model:value="doc.sort"/>
        </a-form-item>
      </a-form>
    </a-modal>
  </div>

  <!--  完成对话框-->
  <a-modal v-model:visible="visible2"
           status="success"
           :title="messages"
           @ok="success"
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
           :data-source="docs"
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
    const docs = ref();
    const title = ref();
    const searchOptions = ref([]);
    const loading = ref(true);
    const getDoc = () => {
      const params = {}
      if (searchValue.value) {
        params.name = searchValue.value;
      }
      axios.get('doc/list', {params}).then((res) => {
        console.log(res)
        let data = res.data;
        if (data.code == 200) {
          docs.value = data.data;

          searchOptions.value =JSON.parse(JSON.stringify(data.data));
          searchOptions.value.unshift({name: '无', id:'0'})
          searchLoading.value = false;
        }
      });
    }

    // 对话框默认不显示
    const doc = ref({});
    const visible1 = ref(false);
    const visible2 = ref(false);
    //点击显示对话框
    const addDoc = () => {
      console.log(doc)
      doc.value = {};
      title.value = '新增电子书文档';
      visible1.value = true;
    }

    //提交表单

    const messages = ref();
    const submit = () => {
      axios.post("/doc/addDoc", doc.value).then((res) => {
        if (res.data.code == 200) {
          messages.value = "操作成功";
          visible1.value = false;
          visible2.value = true;
          getDoc()
        } else if (res.data.code == 500) {
          messages.value = "操作失败";
          visible1.value = false;
          visible2.value = true;
        }
      })
    }

    //修改文档
    const edit = (record) => {
      console.log(record)
      title.value = '修改文档信息';
      doc.value = JSON.parse(JSON.stringify(record));
      visible1.value = true;
    }

    const success = () => {
      visible2.value = false;
    }

    //搜索框
    const searchValue = ref('');
    const searchLoading = ref(false);
    const onSearch = (searchValue) => {
      getDoc();
    };

    const filterOption = (input, option) => {
      return option.name.toLowerCase().indexOf(input.toLowerCase()) >= 0;
    };

    //表单验证
    const rules = {
      name: [{
        required: true,
        message: '请输入文档名称',
        trigger: 'blur'
      }],
      parent: [{
        required: true,
        message: '请输入父文档',
        trigger: 'blur'
      }],
    }

    const columns = [{
      title: 'name',
      dataIndex: 'name',
      key: '名称',
    }, {
      title: '父文档',
      dataIndex: 'parent',
      key: 'parent',
    }, {
      title: '排序',
      dataIndex: 'sort',
      key: 'sort',
    }, {
      title: '阅读数',
      dataIndex: 'viewCount',
      key: 'viewCount',
    }, {
      title: '点赞数',
      dataIndex: 'voteCount',
      key: 'voteCount',
    }, {
      title: '操作',
      key: 'action',
    }];


    //删除
    const remove = (id) => {
      axios.delete('/doc/delete/' + id).then((res) => {
        if (res.data.code == 200) {
          message.success(res.data.message);
          getDoc()
        }
      })
    }

    const confirm = (id) => {
      remove(id)
      message.success('Click on Yes');
    };

    return {
      docs,
      columns,
      visible1,
      visible2,
      addDoc,
      submit,
      doc,
      rules,
      confirm,
      onSearch,
      searchValue,
      remove,
      edit,
      title,
      getDoc,
      searchOptions,
      filterOption,
      messages,
      success
    };
  },
  mounted() {
    this.getDoc();
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