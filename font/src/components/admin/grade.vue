<template>
    <el-main>
        <div style="padding: 10px 0;">
        <el-input v-model="search"  style="width: 300px;"  placeholder="搜索" suffix-icon="el-icon-search"></el-input><el-button type="primary" style="height: auto;" @click="doSearch">搜索</el-button>
        
        </div>
         <el-button type="success" style="height: auto;">导出数据</el-button>
        <div style="padding: 10px 0;">
       
        </div>
      <el-table :data="tableData" border stripe >
        <el-table-column prop="idCard" label="学生id" width="140">
        </el-table-column>
        <el-table-column prop="ticket" label="准考证号" width="200">
        </el-table-column>
        <el-table-column prop="studentName" label="学生名字" width="140">
        </el-table-column>
        <el-table-column prop="courseName" label="考试科目" width="140">
        </el-table-column>
        <el-table-column prop="grade" label="本次成绩" width="140">
        </el-table-column>
        <el-table-column prop="grade" label="设置成绩" width="350">
          <template slot-scope="scope">    
            <div style="padding: 10px 0;">
        <el-input style="width: 150px;"
          placeholder="输入成绩"
          v-model="grade" 
          ></el-input>
          <el-button type="primary" style="height: auto;" @click="doSet(scope.row)">设置</el-button> 
                 

        </div>
        </template>
        </el-table-column>
        
        
        
        
        
      </el-table>
      <div class="block">
    <span class="demonstration">完整功能</span>
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
  export default {
   name:'admingrade',
   data() {
     const item = {
      
       schoolName:'成都工业学院',
       maxContain:1000,
       plotName:'允明楼',
       middleContain:800,
       minContain:40
     };
     return {
       tableData:[
        {
          idCard:1,
          studentName:'曹欢',
          school:'成都工业学院',
          plot:'允明楼',
          className:'9',
          set:38,
          runTime:'9月',
          grade:100,
          ticket:'230900128191000938xx',    
        },
        {
          idCard:1,
          studentName:'曹欢',
          school:'成都工业学院',
          plot:'允明楼',
          className:'9',
          set:38,
          runTime:'9月',
          grade:100,
          ticket:'230900128191000938xx', 
        }
       ],
      currentPage: 1,
      pageSize:10,
       search:"",
       total:400,
       grade:''
     }
   },methods: {
      handleSizeChange(val) {
        console.log(`每页 ${val} 条`);
      },
      handleCurrentChange(val) {
        console.log(`当前页: ${val}`);
      },
      load(){
        request.post("/admin/getTicket",{
          pageSize:this.pageSize,
          pageNum:this.currentPage,
          search:this.search
        }).then((res)=> {
           this.tableData=res.data.records,
           this.total=res.data.total
        }
    ).catch(function (error){
          console.log(error)
        })
      
      },
      doSearch(){
        this.load()
      },
      doSet(row){
        console.log(row)
       console.log(this.grade)
        if(this.grade<=100&&this.grade>0){
          console.log("do")
          request.post("/admin/setGrade",{
            id:row.id,
            courseId:row.courseId,
            grade:this.grade,
            studentId:row.studentId,

          }).then(res=>{
            if(res.code==200){
              this.load()
            }else{
              alert("设置失败")
            }
          })
        }else{
          alert("请输入正确的成绩")
          return
        }
      }
    },
    created(){
      this.load()
      
    }

   
 }

</script>