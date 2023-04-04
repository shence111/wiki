<template>
  <!--  新增-->
  <div>
    <a-button class="add-btn" type="primary" style="margin-bottom: 8px" @click="addEbook">新增</a-button>
    <!--  对话框-->

    <a-modal v-model:visible="visible1" :title="title" @ok="submit" style="width: 400px">
      <a-form
          :model="ebook"
          :label-col="{ span: 8 }"
          :wrapper-col="{ span: 16 }"
          autocomplete="off"
          style="margin-right: 80px"
          :rules="rules"
      >
        <a-form-item
            label="名称"
            name="name"
        >
          <a-input v-model:value="ebook.name"/>
        </a-form-item>
        <a-form-item
            label="分类"
            name="category"
        >
          <a-cascader v-model:value="category"
                      :options="categorise"
                      placeholder="请选择分类"
                      :field-names="{ label: 'name', value: 'id', children: 'children' }"
                      @change="addCategory"
          />
        </a-form-item>

        <a-form-item

            label="描述"
            name="description"
        >
<!--          <a-input v-model:value="ebook.description"/>-->
          <a-textarea v-model:value="ebook.description" placeholder="Basic usage" :rows="4" />
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
  <a-table :columns="columns" :data-source="ebooks" :row-key="record => record.id" :pagination="{ pageSize: 5 }">
    <template #headerCell="{ column }">
    </template>

    <template #bodyCell="{ column, record }">
      <template v-if="column.key === 'cover'">
        <img class="cover" :src="record.cover">
      </template>
      <template v-if="column.key === 'name'">
        <a :href="record.href">{{ record.name }}</a>
      </template>
      <template v-else-if="column.key === 'action'">
        <span>
            <span>
              <router-link to="/admin/doc"><a-button type="primary">文档管理</a-button></router-link>
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
import { message } from 'ant-design-vue';

export default {
  name: "admin-ebook",

  components: {
    CheckOutlined,
    EditOutlined,
  },

  setup() {
    const ebooks = ref();
    const title = ref()
    const getEbook = () => {
      const params = {}
      if (searchValue.value){
        params.name = searchValue.value;
      }
      axios.get('ebook/list' , {params}).then((res) => {
        let data = res.data;
        if (data.code == 200){
          ebooks.value =data.data;
        }
      });
    }
    // 对话框默认不显示
    const ebook = ref({});
    const visible1 = ref(false);
    const visible2 = ref(false);
    //点击显示对话框
    const addEbook = () => {
      title.value = '新增电子书';
      visible1.value = true;
    }
    const messages = ref()
    const submit = () => {
      axios.post("/ebook/addEbook", ebook.value).then((res) => {
        console.log(ebook.value)
        if (res.data.code == 200) {
          messages.value = '操作成功';
          visible1.value = false;
          visible2.value = true;
          getEbook()
          getCategories()
        } else if (res.data.code == 500) {
          messages.value = '操作失败';
          visible1.value = false;
          visible2.value = true;
          getEbook()
          getCategories()
        }
      })
    }

    const category = ref()

    const categorise = ref([]);

    const params = {}
    const getCategories = () =>{
      axios.get("/category/list" , {params}).then((res) => {
        console.log(res)
        if (res.data.code == 200) {
          categorise.value = res.data.data;
        }
      })
    }

    const addCategory = (value, selectedOptions) => {
      ebook.value.category1Id = value[0];
      ebook.value.category2Id = value[1];
    }

    //修改
    const edit = (record) => {
      title.value = '修改电子书信息';
      ebook.value = JSON.parse(JSON.stringify(record));
      visible1.value = true;
    }

    const success = () => {
      visible2.value = false;
    }

    //搜索框
    const searchValue = ref('');
    const onSearch = (searchValue) => {
      getEbook();
    };

    //表单验证
    const rules = {
      name: [{
        required: true,
        message: '请输入电子书名称',
        trigger: 'blur'
      }],
      category1Id: [{
        required: true,
        message: '请输入分类1，1.后端，2.前端，3.数据库',
        trigger: 'blur'
      }],
      description: [{
        required: true,
        message: '请输入电子书描述',
        trigger: 'blur'
      }],
    }

    const columns = [{
      title: '封面',
      key: 'cover',
      dataIndex: 'cover',
    }, {
      dataIndex: 'name',
      key: 'name',
      title: "名称"
    }, {
      title: '分类一',
      dataIndex: 'category1Name',
      key: 'category1Id',
    }, {
      title: '分类二',
      dataIndex: 'category2Name',
      key: 'category2Id',
    }, {
      title: '文档数',
      dataIndex: 'docCount',
      key: 'docCount',
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
      axios.delete('/ebook/delete/'+ id).then((res)=>{
        if (res.data.code == 200){
          message.success(res.data.message);
          getEbook()
        }
      })
    }

    const confirm = (id) => {
      remove(id)
      message.success('Click on Yes');
    };


    return {
      ebooks,
      columns,
      visible1,
      visible2,
      addEbook,
      submit,
      ebook,
      rules,
      confirm,
      onSearch,
      searchValue,
      title,
      edit,
      category,
      categorise,
      getCategories,
      getEbook,
      addCategory,
      messages,
      success
    };
  },
  mounted() {
    this.getEbook();
    this.getCategories()
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