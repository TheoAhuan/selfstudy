<!--
 * @Description: 商品详情页面组件
 * @Author: hai-27
 * @Date: 2020-02-16 20:20:26
 * @LastEditors: hai-27
 * @LastEditTime: 2020-03-07 21:59:26
 -->
 <template>
  <div id="details">
    <!-- 头部 -->
    <div class="page-header">
      <div class="title">
        <p>{{subject.name}}</p>
        <div class="list">
          <ul>
            <li>
              <router-link to>概述</router-link>
            </li>
            <li>
              <router-link to>参数</router-link>
            </li>
            
          </ul>
        </div>
      </div>
    </div>
    <!-- 头部END -->

    <!-- 主要内容 -->
    <div class="main">
      <div class="content">
        <h1 class="name">{{subject.name}}</h1>
        <p class="intro">涵盖课程：
          <div v-for="item in coures" >{{item}}</div>
        </p>
        <p class="store">=============</p>
        <div class="price">
          <span>报名费：{{subject.price}}元</span>
          
        </div>
        
        <!-- 内容区底部按钮 -->


        
        <el-dialog
          title="提示"
          :visible.sync="dialogVisible"
          width="50%"
          :before-close="handleClose">
          <span>
            <img src="../assets/imgs/wechatpay.jpg" width="400px" height="400px">
          </span>
        <span slot="footer" class="dialog-footer">

            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="getQualification">确 定</el-button>
        </span>
</el-dialog>




        <div class="button">
          <el-button class="shop-cart" type="text" @click="dialogVisible = true">申请专业修习资格</el-button>
          <!-- <el-button class="like" @click="addCollect">喜欢</el-button> -->
        </div>
        <!-- 内容区底部按钮END -->
        <div class="pro-policy">
          <ul>
            <li>
              <i class="el-icon-circle-check"></i> 安全
            </li>
            <li>
              <i class="el-icon-circle-check"></i> 快速
            </li>
            <li>
              <i class="el-icon-circle-check"></i> 值得信赖
            </li>
            <li>
              <i class="el-icon-circle-check"></i> 学籍可查
            </li>
          </ul>
        </div>
      </div>
    </div>
    <!-- 主要内容END -->
  </div>
</template>
<script>

import { mapActions } from "vuex";
import { mapGetters } from "vuex";
import request from "@/utils/request";
import student from "@/store/modules/student";
export default {
  data() {
    return {
      subject:
      {
        name:this.$route.params.name,
        coures:'计算机网络',
        price:this.$route.params.price,
        id:this.$route.params.id
      },
       
      dialogVisible: false,
      //包含的课程
      coures:[
        '计算机网络',
        'daxue'
      ]
    };
  },
  // 通过路由获取商品id
  
  watch: {
    // 监听商品id的变化，请求后端获取商品数据
    productID: function(val) {
      this.getDetails(val);
      this.getDetailsPicture(val);
    }
  },
  created(){
    this.getCoures()

  },
  methods: {
    ...mapActions(["unshiftShoppingCart", "addShoppingCartNum","setFlag"]),
    //从后端获得课程数据
    getCoures(){
      console.log(this.subject.name)
      request.post('test/student/getCourse',{
        
        name:this.subject.name,
      }).then((res)=>{
        console.log("响应"+res.msg)
        const a=[
          "1111111",
          "222222222222"
        ]   
        console.log(a)
        console.log(res.data)
        this.coures=res.data
      })
      
    },
    getQualification(){
      if (!this.$store.getters.getStudent) {
        console.log("请登录")
        alert("请登录")
        this.$store.dispatch("setFlag",true)
        return;
      }
      console.log("登录"+this.$store.getters.getStudent)
      
      console.log("当前用户id"+sessionStorage.getItem("studentId"))
      
      this.dialogVisible=false
      alert("报名成功")
      
      request.post('/subject/getQualification',{
        subjectId:this.subject.id,
        // studentName:this.$store.getters.getStudent,
        studentId:sessionStorage.getItem("studentId"),  
      }).then(res=>{
        console.log("ok")
      })

    },
    handleClose(done) {
        this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
      },
    
    
    addCollect() {
      // 判断是否登录,没有登录则显示登录组件
      if (!this.$store.getters.getUser) {
        this.$store.dispatch("setShowLogin", true);
        return;
      }
      this.$axios
        .post("/api/user/collect/addCollect", {
          user_id: this.$store.getters.getUser.user_id,
          product_id: this.productID
        })
        .then(res => {
          if (res.data.code == "001") {
            // 添加收藏成功
            this.notifySucceed(res.data.msg);
          } else {
            // 添加收藏失败
            this.notifyError(res.data.msg);
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
/* 头部CSS */
#details .page-header {
  height: 64px;
  margin-top: -20px;
  z-index: 4;
  background: #fff;
  border-bottom: 1px solid #e0e0e0;
  -webkit-box-shadow: 0px 5px 5px rgba(0, 0, 0, 0.07);
  box-shadow: 0px 5px 5px rgba(0, 0, 0, 0.07);
}
#details .page-header .title {
  width: 1225px;
  height: 64px;
  line-height: 64px;
  font-size: 18px;
  font-weight: 400;
  color: #212121;
  margin: 0 auto;
}
#details .page-header .title p {
  float: left;
}
#details .page-header .title .list {
  height: 64px;
  float: right;
}
#details .page-header .title .list li {
  float: left;
  margin-left: 20px;
}
#details .page-header .title .list li a {
  font-size: 14px;
  color: #616161;
}
#details .page-header .title .list li a:hover {
  font-size: 14px;
  color: #ff6700;
}
/* 头部CSS END */

/* 主要内容CSS */
#details .main {
  width: 1225px;
  height: 560px;
  padding-top: 30px;
  margin: 0 auto;
}
#details .main .block {
  float: left;
  width: 560px;
  height: 560px;
}
#details .el-carousel .el-carousel__indicator .el-carousel__button {
  background-color: rgba(163, 163, 163, 0.8);
}
#details .main .content {
  float: left;
  margin-left: 25px;
  width: 640px;
}
#details .main .content .name {
  height: 30px;
  line-height: 30px;
  font-size: 24px;
  font-weight: normal;
  color: #212121;
}
#details .main .content .intro {
  color: #b0b0b0;
  padding-top: 10px;
}
#details .main .content .store {
  color: #ff6700;
  padding-top: 10px;
}
#details .main .content .price {
  display: block;
  font-size: 18px;
  color: #ff6700;
  border-bottom: 1px solid #e0e0e0;
  padding: 25px 0 25px;
}
#details .main .content .price .del {
  font-size: 14px;
  margin-left: 10px;
  color: #b0b0b0;
  text-decoration: line-through;
}
#details .main .content .pro-list {
  background: #f9f9fa;
  padding: 30px 60px;
  margin: 50px 0 50px;
}
#details .main .content .pro-list span {
  line-height: 30px;
  color: #616161;
}
#details .main .content .pro-list .pro-price {
  float: right;
}
#details .main .content .pro-list .pro-price .pro-del {
  margin-left: 10px;
  text-decoration: line-through;
}
#details .main .content .pro-list .price-sum {
  color: #ff6700;
  font-size: 24px;
  padding-top: 20px;
}
#details .main .content .button {
  height: 55px;
  margin: 10px 0 20px 0;
}
#details .main .content .button .el-button {
  float: left;
  height: 55px;
  font-size: 16px;
  color: #fff;
  border: none;
  text-align: center;
}
#details .main .content .button .shop-cart {
  width: 340px;
  background-color: #ff6700;
}
#details .main .content .button .shop-cart:hover {
  background-color: #f25807;
}

#details .main .content .button .like {
  width: 260px;
  margin-left: 40px;
  background-color: #b0b0b0;
}
#details .main .content .button .like:hover {
  background-color: #757575;
}
#details .main .content .pro-policy li {
  float: left;
  margin-right: 20px;
  color: #b0b0b0;
}
/* 主要内容CSS END */
</style>