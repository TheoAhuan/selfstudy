<template>
    <el-main>
       <div style="padding: 10px 0;">
       <el-input style="width: 300px;"  
       v-model="search"
       placeholder="搜索已经禁止考试的学生" 
       suffix-icon="el-icon-search"></el-input><el-button type="primary" style="height: auto;">搜索</el-button>
       </div>
     <el-table :data="tableData" border stripe >
       <el-table-column prop="banTime" label="禁止日期" width="250">
       </el-table-column>
       <el-table-column prop="username" label="姓名" width="120">
       </el-table-column>
       <el-table-column prop="msg" label="禁止原因" width="120">
       </el-table-column>
       <el-table-column prop="card" label="idCard" width="300">
       </el-table-column>
       <el-table-column prop="phone" label="电话" width="120">
       </el-table-column>
       <el-table-column
            label="操作"
            >
            <template slot-scope="scope">      
            <el-button type="success" @click="removeBan(scope.row.id)">解除禁止</el-button>
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
  name:'ban',
  data(){
    return { 
      
      tableData:[
      {
      ban: '2016-05-02',
      username: '王小虎',
      idCard: '511526XXXXXXXXX'
    }
      
      ],
      currentPage:1 ,
       pageSize:10,
       search:"",
       total:400
    }   
  },methods: {
     handleSizeChange(val) {
       console.log(`每页 ${val} 条`);
     },
     handleCurrentChange(val) {
       console.log(`当前页: ${val}`);
     },
     load(){ 
        request.post('admin/ban',{
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
      removeBan(id){
        console.log(id)
        request.get("/ban/remove",{
          
          params:{
            id:id
          }
        }).then(res=>{
          alert("解禁成功：")
          this.load()
        })

      }
   },
   created(){
    console.log("现在查询的是"+this.currentPage+"页的数据")
      this.load()
    }
  

  
}

</script>
