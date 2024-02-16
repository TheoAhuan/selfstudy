<template>
     <el-main>
        <div style="padding: 10px 0;">
        <el-input style="width: 300px;"
         v-model="search"  
         placeholder="搜索现在学籍正常的学生" 
         suffix-icon="el-icon-search"></el-input><el-button type="primary" style="height: auto;">搜索</el-button>
        
        </div>
      <el-table  :data="tableData" border stripe >
        <el-table-column prop="id" label="id" width="100">
        </el-table-column>
        <el-table-column prop="username" label="姓名">
        </el-table-column>
        <el-table-column prop="card" label="身份证号">
        </el-table-column>
        <el-table-column prop="role" label="角色">

        </el-table-column>
        <el-table-column prop="phone" label="电话">
        </el-table-column>
        <el-table-column
            label="操作"
            width="120">
            <template slot-scope="scope">
            <el-button type="danger" @click="ban(scope.row)">禁止考试</el-button>
            <el-dialog
              title="提示"
              v-model="banTable"
                :visible.sync="centerDialogVisible"
                width="30%"
                center>
                <span style="text-align: center">禁止{{banTable.username}}考试</span>
                      <span class="demonstration"></span>
                      <el-date-picker
                        v-model="banTable.banTime"
                        type="datetime"
                        placeholder="选择禁止考试的截止时间"
                        default-time="12:00:00">
                      </el-date-picker>
                      <span>
                        <el-input v-model="banTable.msg" placeholder="禁止考试的原因"></el-input>
                      </span>
                <span slot="footer" class="dialog-footer">
                  <el-button @click="centerDialogVisible = false">取 消</el-button>
                  <el-button type="primary" @click="makesure">确 定</el-button>
                </span>
            </el-dialog>
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
   name:'noban',
   data() {
     const item = {
       id:'1',
       username: '王小虎',
       idCard:'511526',
       address: '上海市普陀区金沙江路 1518 弄',
       role:'社会型考生',
       phone:"167351265165",

     };
     return {
      centerDialogVisible: false,
   

       tableData:[
       {
       id:'1',
       username: '王小虎',
       idCard:'511526',
       address: '上海市普陀区金沙江路 1518 弄',
       role:'社会型考生',
       phone:"167351265165",

     },{
       id:'2',
       username: '王小虎',
       idCard:'511526',
       address: '上海市普陀区金沙江路 1518 弄',
       role:'社会型考生',
       phone:"167351265165",

     },{
       id:'3',
       username: '王小虎',
       idCard:'511526',
       address: '上海市普陀区金沙江路 1518 弄',
       role:'社会型考生',
       phone:"167351265165",

     }
       ],
       currentPage:1 ,
       pageSize:10,
       search:"",
       total:400,
       banTable:''
     }
   },
   methods: {
      handleSizeChange(val) {
        console.log(`每页 ${val} 条`);
      },
      handleCurrentChange(val) {
        console.log(`当前页: ${val}`);
      },
      ban(row){
        console.log(row)
        this.banTable=row
        this.centerDialogVisible=true

        
      
        
      },
      makesure(){
        console.log(this.banTable)
        // this.centerDialogVisible =false;
        console.log(this.banTable.id)
        console.log(this.banTable.msg)
        console.log(this.banTable.banTime)

        this.centerDialogVisible =false;
        


        request.post("/noban/banStudent",{
          id:this.banTable.id,
          time:this.banTable.banTime,
          msg:this.banTable.msg
        }).then(res=>{
          console.log(res)
        this.centerDialogVisible =false; 

          alert("禁止成功")
          this.load();
        })
      },
      load(){
        request.post('admin/noban',{
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
      handleEdit(row){
        console.log(row)
        this.dialogTable=row
        console.log(this.dialogTable)

      }

    },
    created(){
      this.load()
    }

   
 }

</script>
