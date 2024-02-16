<template>
    <el-main align="center">
        <div style="padding: 10px 0;">

          <span>你需要修够下面所有的课程才能获得毕业证。你现在修学的专业是{{result.subjectName}}</span>
        <!-- <el-input style="width: 300px;"
          placeholder="您可以通过准考证号查询自己的单场考试形象" 
          suffix-icon="el-icon-search"></el-input>
          <el-button type="primary" style="height: auto;">搜索</el-button>  -->
          <!-- <el-button type="success" style="height: auto;">申请毕业</el-button>  -->
          <el-button type="primary" @click="loadTicket">查看准考证</el-button>
          <el-dialog
  title="提示"
  :visible.sync="ticketDialogVisible"
  width="100%"
  center>
  <el-table :data="ticketTable" border stripe >
        
        <el-table-column prop="ticket" label="准考证号" width="200">
        </el-table-column>
        <el-table-column prop="school" label="考试学校" width="140">
        </el-table-column>
        <el-table-column prop="plot" label="考试地点" width="140">
        </el-table-column>
        <el-table-column prop="classNum" label="考试教室" width="140">
        </el-table-column>
        <el-table-column prop="setNum" label="考试座位号" width="120">
        </el-table-column>
        <el-table-column prop="courseName" label="科目" width="140">
        </el-table-column>
        <el-table-column prop="runTime" label="考试时间" width="250">
        </el-table-column>
        
        
        
      </el-table>




  <span slot="footer" class="dialog-footer">
    <el-button @click="ticketDialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="ticketDialogVisible = false">确 定</el-button>
  </span>
</el-dialog>
                  

        </div>
      <el-table align="center"
    
      :data="result.tableData" border stripe >
        
        <el-table-column prop="courseName" label="考试名称"  width="120" align="center">
        </el-table-column>
        
        
        
        <el-table-column prop="topGrade" label="历史最高成绩" width="120" align="center">
        </el-table-column>
        <!-- <el-table-column prop="school_name" label="考试学校" width="120">
        </el-table-column> -->
        <el-table-column
            label="操作"
            width="300">
            <template slot-scope="scope">
            
            <el-button type="success" @click="inter(scope.row)">报名</el-button>
            
            </template>
        </el-table-column> 
      </el-table>
      <div class="block">
        <el-dialog
  title="报名窗口"
  :visible.sync="centerDialogVisible"
  width="30%"
  center>
  <el-form :model="workform">
    <el-form-item label="考试名称">{{  workform.courseName}}</el-form-item>
    <el-form-item label="考试价格">{{  workform.price}}</el-form-item>
    <el-form-item label="考试月份" >
      <el-select v-model="month" placeholder="考试时间">
    <el-option
      v-for="item in monthOP"
      :key="item.value"
      :label="item.value"
      :value="item.value">
    </el-option>
  </el-select>
    </el-form-item>
    <el-form-item label="考试地点" >
    <el-select v-model="schoolId" placeholder="考试地点">
    <el-option
      v-for="item in options"
      :key="item.schoolName"
      :label="item.schoolName"
      :value="item.id">
    </el-option>
  </el-select>
</el-form-item>
  </el-form>
  <span slot="footer" class="dialog-footer">
    <el-button @click="centerDialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="makesure">点击报名</el-button>
  </span>
</el-dialog>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="currentPage"
      :page-sizes="[10, 15, 20]"
      :page-size="100"
      layout="total, sizes, prev, pager, next, jumper"
      :total=total>
    </el-pagination>
  </div>
    </el-main>
</template>
<script>
import request from '@/utils/request';

  export default{
   name:'manage',
   data() {
     
     return {
      ticketTable:[
        {
          idCard:1,
          studentName:'曹欢',
          school:'成都工业学院',
          plot:'允明楼',
          classNum:'9',
          setNum:38,
          runTime:'2023-07-01 19:44:32.000000',
          grade:100,
          
          
          ticket:'230900128191000938xx',
          courseName:"计算机网络"
          
        }
       ],
      workform:{
        courseName:'',
        id:'',
        price:'',
        time:7,
        school:''

      },
      schoolId:'',
      
      ticketDialogVisible:false,
      options: [],
      month:'',
      monthOP:[
        {
          value:7
        },
        {
          value:9
        }
      ],
      centerDialogVisible:false,
       tableData:[],
       result:{
        // subjectName:'软件工程',
        // tableData:[
        //   {
        //     courseName:'大学英语',
        //     topGrade:'99',
        //   },
        //   {
        //     courseName:'需求分析',
        //     topGrade:'99',
        //   },
        //   {
        //     courseName:'计算机网络',
        //     topGrade:'99',
        //   }
        // ]

       }
       ,
       currentPage:1 ,
       pageSize:10,
       search:"",
       total:2
     }
   },methods: {
      handleSizeChange(val) {
        console.log(`每页 ${val} 条`);
        this.load();
      },
      handleCurrentChange(val) {
        console.log(`当前页: ${val}`);
        this.load();

      },
      load(){
        console.log("查询前校验自己的id为"+this.$store.getters.getStudent)
        console.log("查询前校验自己的id为"+this.$store.getters.getStudentId)
        console.log("查询前校验自己的id为"+sessionStorage.getItem("studentId"))

        request.post(
        "/student/grade/getMyGrade",
        {
            studentId:sessionStorage.getItem("studentId"),
            
        }
      ).then(res=>{
        this.result.tableData=res.data.vo
        this.result.subjectName=res.data.subjectName
      })   
        
      },
      getSchool(){
        request.get("/student/getSchool").then(res=>{
          this.options=res.data
        })
      },
      inter(row){
        console.log(row)
        this.workform=row
        this.getSchool()
        this.centerDialogVisible=true
      },
      makesure(){

        console.log(sessionStorage.getItem("studentId")+"报名时用户的id为")
        console.log(this.workform.id+"报名的课程id")
        console.log(this.month+"报名的月份")
        console.log(this.schoolId+"考试的学校id")

        if(sessionStorage.getItem("studentId")===''){
          alert("请登录")
        }
        if(this.month===undefined){
          alert("请选择考试的月份")
          return
        }
        request.post("/student/order/create",{
          studentId:sessionStorage.getItem("studentId"),
          courseId:this.workform.id,
          month:this.month,
          school:this.schoolId
        }).then(res=>{
          alert(res.data)
          this.centerDialogVisible=false

        }).catch(err => {
              return Promise.reject(err);
            });


      },
      
      loadTicket(){
        this.ticketDialogVisible = true
        this.doLoadT()
      },
      doLoadT(){

        request.get("/student/getMyticket",{
          params:{
            studentId:sessionStorage.getItem("studentId")
          }
        }).then(res=>{
          this.ticketTable=res.data
        })

      }

    },
    created(){
      if(!this.$store.getters.getStudent){
        alert("请登录")
        return
      }
      this.load()
      this.doLoadT()
      
      
    }
    

   
 }

</script>
<style scoped>


</style>