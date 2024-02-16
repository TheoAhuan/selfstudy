<template>
    <div id="exam" class="exam" >
        <!-- 查询考试时间在一月的考试 -->
        <div class="1yue">
            <span>2023年7月的考试开始报名</span>
            <ul>
                <li v-for="item in enroll" :key="item.id">
                <el-popover placement="top">
                <p>确定删除吗？</p>
                <div style="text-align: right; margin: 10px 0 0">
                    <el-button type="primary" size="mini" @click="deleteCollect(item.subject_id)">确定</el-button>
                </div>
          <i class="el-icon-close delete" slot="reference" v-show="isDelete"></i>
        </el-popover>
        <router-link :to="{name:'details',params:{id:item.subject_id,name:item.subject_name,price:item.price}}">
          <!-- <img :src="$target +item.product_picture" alt /> -->
          <h2>{{item.name}}考试</h2>
          <h3>课程号--{{item.id}}</h3>

          <h3>..........</h3>

          <p>
            <span>专业报名费用：</span>
            <span
              
            >{{item.price}}元</span>
          </p>
        </router-link>
      </li>
      <!-- 最后浏览更多的点击图标 -->
      <li v-show="isMore && list.length>=1" id="more">
        <router-link :to="{ path: '/goods',   }">
          浏览更多
          <i class="el-icon-d-arrow-right"></i>
        </router-link>
      </li>
    </ul>
        

        </div>





    
    
    
    
        </div>
</template>
<script>
export default {
  name: "exam",
  
  
  data() {
    return {
        enroll:[
            {
                id:1,
                studentRole:'社会型',
                enterStartTime:'2023-04-30',
                enterEndTime:'2023-04-30',
                outStartTime:'2023-04-30',
                outendTime:'2023-04-30',
                name:'需求分析',
                price:'40.00',
                reamin:400,
                address:'成都工业学院'
            },
            {
                id:1,
                studentRole:'社会型',
                enterStartTime:'2023-04-30',
                enterEndTime:'2023-04-30',
                outStartTime:'2023-04-30',
                outendTime:'2023-04-30',
                name:'四级英语',
                price:'40.00',
                reamin:400,
                address:'成都工业学院'
            }, {
                id:1,
                studentRole:'社会型',
                enterStartTime:'2023-04-30',
                enterEndTime:'2023-04-30',
                outStartTime:'2023-04-30',
                outendTime:'2023-04-30',
                name:'计算机网络',
                price:'40.00',
                reamin:400,
                address:'成都工业学院'
            }, {
                id:1,
                studentRole:'社会型',
                enterStartTime:'2023-04-30',
                enterEndTime:'2023-04-30',
                outStartTime:'2023-04-30',
                outendTime:'2023-04-30',
                name:'Java程序设计',
                price:'40.00',
                reamin:400,
                address:'成都工业学院'
            }, {
                id:1,
                studentRole:'社会型',
                enterStartTime:'2023-04-30',
                enterEndTime:'2023-04-30',
                outStartTime:'2023-04-30',
                outendTime:'2023-04-30',
                name:'C++程序设计',
                price:'40.00',
                reamin:400,
                address:'成都工业学院'
            }, {
                id:1,
                studentRole:'社会型',
                enterStartTime:'2023-04-30',
                enterEndTime:'2023-04-30',
                outStartTime:'2023-04-30',
                outendTime:'2023-04-30',
                name:'C++程序设计',
                price:'40.00',
                reamin:400,
                address:'成都工业学院'
            }, {
                id:1,
                studentRole:'社会型',
                enterStartTime:'2023-04-30',
                enterEndTime:'2023-04-30',
                outStartTime:'2023-04-30',
                outendTime:'2023-04-30',
                name:'C++程序设计',
                price:'40.00',
                reamin:400,
                address:'成都工业学院'
            }, {
                id:1,
                studentRole:'社会型',
                enterStartTime:'2023-04-30',
                enterEndTime:'2023-04-30',
                outStartTime:'2023-04-30',
                outendTime:'2023-04-30',
                name:'C++程序设计',
                price:'40.00',
                reamin:400,
                address:'成都工业学院'
            }
        ]
        
    };
  },
  computed: {
    
  },
  methods: {
   
  }
};
</script>
<style scoped>
@import "../assets/css/index.css";

.exam ul li {
  z-index: 1;
  float: left;
  width: 234px;
  height: 280px;
  padding: 10px 0;
  margin: 0 0 14.5px 13.7px;
  background-color: white;
  -webkit-transition: all 0.2s linear;
  transition: all 0.2s linear;
  position: relative;
}
.exam ul li:hover {
  z-index: 2;
  -webkit-box-shadow: 0 15px 30px rgba(0, 0, 0, 0.1);
  box-shadow: 0 15px 30px rgba(0, 0, 0, 0.1);
  -webkit-transform: translate3d(0, -2px, 0);
  transform: translate3d(0, -2px, 0);
}
.exam ul li img {
  display: block;
  width: 160px;
  height: 160px;
  background: url(../assets/imgs/placeholder.png) no-repeat 50%;
  margin: 0 auto;
}
.exam ul li h2 {
  margin: 25px 10px 0;
  font-size: 14px;
  font-weight: 400;
  color: #333;
  text-align: center;
  text-overflow: ellipsis;
  white-space: nowrap;
  overflow: hidden;
}
.exam ul li h3 {
  margin: 5px 10px;
  height: 18px;
  font-size: 12px;
  font-weight: 400;
  color: #b0b0b0;
  text-align: center;
  text-overflow: ellipsis;
  white-space: nowrap;
  overflow: hidden;
}
.exam ul li p {
  margin: 10px 10px 10px;
  text-align: center;
  color: #ff6700;
}
.exam ul li p .del {
  margin-left: 0.5em;
  color: #b0b0b0;
  text-decoration: line-through;
}
.exam #more {
  text-align: center;
  line-height: 280px;
}
.exam #more a {
  font-size: 18px;
  color: #333;
}
.exam #more a:hover {
  color: #ff6700;
}
.exam ul li .delete {
  position: absolute;
  top: 10px;
  right: 10px;
  display: none;
}
.exam ul li:hover .delete {
  display: block
}
.exam ul li .delete:hover {
  color: #ff6700;
}
</style>