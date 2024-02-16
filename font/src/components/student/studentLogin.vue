<!--
 * @Description: 登录组件
 * @Author: hai-27
 * @Date: 2020-02-19 20:55:17
 * @LastEditors: hai-27
 * @LastEditTime: 2020-03-01 15:34:08
 -->
 <template>
    <div id="myLogin">
      <el-dialog title="登录" width="300px" center :visible.sync="isLogin">
        <el-form :model="LoginUser"  status-icon ref="ruleForm" class="demo-ruleForm">
          <el-form-item prop="name">
            <el-input prefix-icon="el-icon-user-solid" placeholder="请输入账号" v-model="LoginUser.name"></el-input>
          </el-form-item>
          <el-form-item prop="pass">
            <el-input
              prefix-icon="el-icon-view"
              type="password"
              placeholder="请输入密码"
              v-model="LoginUser.pass"
            ></el-input>
          </el-form-item>
          <el-form-item>
            <el-button size="medium" type="primary" @click="Login" style="width:100%;">登录</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
    </div>
  </template>
<script>
import { mapActions } from "vuex";
import request from "@/utils/request";

export default {
  name: 'studentLogin',
 
  data(){
    return{
      LoginUser:{
        name:'',
        pass:''
      }
    }
  },
  computed: {
    // 获取vuex中的falg，控制登录组件是否显示
    isLogin: {
      get() {
        return this.$store.getters.getFlag;
      },
      set(val) {
        this.$refs["ruleForm"].resetFields();
        this.setFlag(val);
      }
    }
  },
  methods: {
    ...mapActions(["setStudent", "setFlag","setStudentId"]),
    Login(){
      request.post("/student/login",{
            card:this.LoginUser.name,
            password: this.LoginUser.pass

          }).then(res=>{
            //“200”代表登录成功，其他的均为失败
            console.log(res)
              if (res.code ===200) {
                console.log("ok login suc")
                // 隐藏登录组件
                this.isLogin = false;
                // 登录信息存到本地
                let student = JSON.stringify(res.data.student.username);
                sessionStorage.setItem("student", student);
                sessionStorage.setItem("id",res.data.student.id)
                sessionStorage.setItem("studentId",res.data.student.id)
                
                sessionStorage.setItem("token",res.data.token)
                // 登录信息存到vuex
                this.setStudent(res.data.student.username);
                this.setStudentId(res.data.student.id)
                console.log("登录成功，当前用户id为"+res.data.student.id)
                console.log("验证yi下vuex里的id"+this.$store.getters.getStudentId)
                // 弹出通知框提示登录成功信息
                // this.notifySucceed(res.msg);
              } else {

                console.log("false")
                alert("请检查当前用户是否可用")
                //this.notifyError(res.data.msg);
                // this.noti
              }

          })
            .catch(err => {
              return Promise.reject(err);
            });
        

    }
  }
};
</script>
  <style>
  </style>