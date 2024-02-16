<template>
    <div id="student">
        <el-container>
            <div class="topbar">
        <div class="nav">
          <ul>


            <li>

            </li>
            <li v-if="!this.$store.getters.getStudent">
              <el-button type="text" @click="login">登录</el-button>
              <span class="sep">|</span>
              <el-button type="text" @click="register = true">注册</el-button>
            </li>
            <li v-else>
              欢迎
              <el-popover placement="top" width="180" v-model="visible">
                <p>确定退出登录吗？</p>
                <div style="text-align: right; margin: 10px 0 0">
                  <el-button size="mini" type="text" @click="visible = false">取消</el-button>
                  <el-button type="primary" size="mini" @click="logout">确定</el-button>
                </div>
                <el-button type="text" slot="reference">{{this.$store.getters.getStudent}}</el-button>
                
              </el-popover>
              
              <router-link to="/manage">我的</router-link>
            
            </li>
          </ul>
        </div>
            </div>
            <el-header>
        <el-menu
          :default-active="activeIndex"
          class="el-menu-demo"
          mode="horizontal"
          active-text-color="#409eff"
          router
        >
          <div class="logo">
            <router-link to="/">
              <img width="60px" src="../assets/imgs/test.jpg" alt />
            </router-link>
          </div>
          <el-menu-item index="/toStudent">首页</el-menu-item>
          <el-menu-item index="/all">全部专业</el-menu-item>
          <el-menu-item index="/toExam">报名考试</el-menu-item>

          <el-menu-item index="/about">关于我们</el-menu-item>

          <div class="so">
            <el-input placeholder="请输入搜索内容" v-model="search">
              <el-button slot="append" icon="el-icon-search" @click="searchClick"></el-button>
            </el-input>
          </div>
        </el-menu>
      </el-header>

      <studentLogin></studentLogin>

      <studentRegister :register="register" @fromChild="isRegister"></studentRegister>
      <el-main>
        <keep-alive>
          <router-view></router-view>
        </keep-alive>
      </el-main>



      <el-footer>
        <div class="footer">
          <div class="ng-promise-box">
            <div class="ng-promise">
              <p class="text">
                <a class="icon1" href="javascript:;">成绩可查</a>
                <a class="icon2" href="javascript:;">为人民服务</a>
                <a class="icon3" style="margin-right: 0" href="javascript:;">品质保证</a>
              </p>
            </div>
          </div>
          <div class="github">
            <a href="" target="_blank">
              <div class="github-but"></div>
            </a>
          </div>
          <div class="mod_help">
            <p>
              <router-link to="/">首页</router-link>
              <span>|</span>
              <router-link to="/goods">全部商品</router-link>
              <span>|</span>
              <router-link to="/about">关于我们</router-link>
            </p>
            <p class="coty">商城版权所有 &copy; 2022-2023</p>
          </div>
        </div>
      </el-footer>
        </el-container>
    </div>
    
</template>
<script>
import { mapActions } from "vuex";
import { mapGetters } from "vuex";
import studentLogin from "@/components/student/studentLogin.vue";
import studentRegister from "@/components/student/studentRegister.vue";
import request from "@/utils/request";

export default {
    components:{
    studentLogin,
    studentRegister
},
  beforeUpdate() {
    this.activeIndex = this.$route.path;
  },
  data() {
    return {
      activeIndex: "", // 头部导航栏选中的标签
      search: "", // 搜索条件
      register: false, // 是否显示注册组件
      visible: false // 是否退出登录
    };
  },
  created() {
    // 获取浏览器localStorage，判断用户是否已经登录
    if (sessionStorage.getItem("student")) {
      // 如果已经登录，设置vuex登录状态
      this.setStudent(sessionStorage.getItem("student"));
    }
    
  },
  computed: {
    ...mapGetters(["getStudent", "getNum"])
  },
  watch: {
    // 获取vuex的登录状态
    getStudent: function(val) {
      if (val === "") {
        // 用户没有登录
        this.setShoppingCart([]);
      }else{
        console.log("您已经登录")
      }
      
    }
  },
  methods: {
    ...mapActions(["setStudent", "setFlag", "setShoppingCart"]),
    login() {
      // 点击登录按钮, 通过更改vuex的showLogin值显示登录组件
      this.setFlag(true);
    },
    // 退出登录
    logout() {
      this.visible = false;
      // 清空本地登录信息
      sessionStorage.setItem("student", "");
      sessionStorage.setItem("studentId", "");

      // 清空vuex登录信息
      this.setStudent("");
      console.log("您已退出")
    },
    // 接收注册子组件传过来的数据
    isRegister(val) {
      this.register = val;
    },
    // 点击搜索按钮
    searchClick() {
      if (this.search != "") {
        // 跳转到全部商品页面,并传递搜索条件
        this.$router.push({ path: "/goods", query: { search: this.search } });
        this.search = "";
      }
    }
  }
};
</script>

<style>
/* 全局CSS */
* {
  padding: 0;
  margin: 0;
  border: 0;
  list-style: none;
}
#student .el-header {
  padding: 0;
}
#student .el-main {
  min-height: 300px;
  padding: 20px 0;
}
#student .el-footer {
  padding: 0;
}
a,
a:hover {
  text-decoration: none;
}
/* 全局CSS END */

/* 顶部导航栏CSS */
.topbar {
  height: 40px;
  background-color: #3d3d3d;
  margin-bottom: 20px;
}
.topbar .nav {
  width: 1225px;
  margin: 0 auto;
}
.topbar .nav ul {
  float: right;
}
.topbar .nav li {
  float: left;
  height: 40px;
  color: #b0b0b0;
  font-size: 14px;
  text-align: center;
  line-height: 40px;
  margin-left: 20px;
}
.topbar .nav .sep {
  color: #b0b0b0;
  font-size: 12px;
  margin: 0 5px;
}
.topbar .nav li .el-button {
  color: #b0b0b0;
}
.topbar .nav .el-button:hover {
  color: #fff;
}
.topbar .nav li a {
  color: #b0b0b0;
}
.topbar .nav a:hover {
  color: #fff;
}
.topbar .nav .shopCart {
  width: 120px;
  background: #424242;
}
.topbar .nav .shopCart:hover {
  background: #fff;
}
.topbar .nav .shopCart:hover a {
  color: #ff6700;
}
.topbar .nav .shopCart-full {
  width: 120px;
  background: #ff6700;
}
.topbar .nav .shopCart-full a {
  color: white;
}
/* 顶部导航栏CSS END */

/* 顶栏容器CSS */
.el-header .el-menu {
  max-width: 1225px;
  margin: 0 auto;
}
.el-header .logo {
  height: 60px;
  width: 189px;
  float: left;
  margin-right: 100px;
}
.el-header .so {
  margin-top: 10px;
  width: 300px;
  float: right;
}
/* 顶栏容器CSS END */

/* 底栏容器CSS */
.footer {
  width: 100%;
  text-align: center;
  background: #2f2f2f;
  padding-bottom: 20px;
}
.footer .ng-promise-box {
  border-bottom: 1px solid #3d3d3d;
  line-height: 145px;
}
.footer .ng-promise-box {
  margin: 0 auto;
  border-bottom: 1px solid #3d3d3d;
  line-height: 145px;
}
.footer .ng-promise-box .ng-promise p a {
  color: #fff;
  font-size: 20px;
  margin-right: 210px;
  padding-left: 44px;
  height: 40px;
  display: inline-block;
  line-height: 40px;
  text-decoration: none;
  background: url("../assets/imgs/us-icon.png") no-repeat left 0;
}
.footer .github {
  height: 50px;
  line-height: 50px;
  margin-top: 20px;
}
.footer .github .github-but {
  width: 50px;
  height: 50px;
  margin: 0 auto;
  background: url("../assets/imgs/github.png") no-repeat;
}
.footer .mod_help {
  text-align: center;
  color: #888888;
}
.footer .mod_help p {
  margin: 20px 0 16px 0;
}

.footer .mod_help p a {
  color: #888888;
  text-decoration: none;
}
.footer .mod_help p a:hover {
  color: #fff;
}
.footer .mod_help p span {
  padding: 0 22px;
}
/* 底栏容器CSS END */
</style>