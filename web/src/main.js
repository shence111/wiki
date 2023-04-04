import { createApp } from 'vue'
import Antd from 'ant-design-vue';
import App from './App.vue'
import 'ant-design-vue/dist/antd.css';
import router from "./router/index.js";
import store from "./store/index.js"
import axios from "axios";

axios.defaults.baseURL = '/api';

// 添加请求拦截器
axios.interceptors.request.use(function (config) {
    console.log("请求参数:", config);
    // 在发送请求之前做些什么
    return config;
}, function (error) {
    // 对请求错误做些什么
    return Promise.reject(error);
});

// 添加响应拦截器
axios.interceptors.response.use(function (response) {
    console.log("响应结果：", response)
    // 2xx 范围内的状态码都会触发该函数。
    // 对响应数据做点什么
    return response;
}, function (error) {
    // 超出 2xx 范围的状态码都会触发该函数。
    // 对响应错误做点什么
    return Promise.reject(error);
});

createApp(App).use(Antd).use(router).use(store).mount('#app')
