<template>
    <el-main>
        <div style="padding: 10px 0;">
        <el-input v-model="search"  style="width: 300px;"  placeholder="搜索" suffix-icon="el-icon-search"></el-input><el-button type="primary" style="height: auto;" @click="doSearch">搜索</el-button>
        
        </div>
         <!-- <el-button type="primary" style="height: auto;"  @click="centerDialogVisible = true">新建考点</el-button> -->
         <el-dialog
  title="提示"
  :visible.sync="centerDialogVisible"
  width="30%"
  center>
  <span>修改考点的信息</span>

  <el-form :model="workform">
    <el-form-item label="归属学校"></el-form-item>
    <el-form-item label="学校的最大容量"></el-form-item>
    <el-form-item label="学校下的考点名称"></el-form-item>
    <el-form-item label="考点的最大容量"></el-form-item>
    <el-form-item label="考点里教室的座位容量"></el-form-item>



  </el-form>
  <span slot="footer" class="dialog-footer">
    <el-button @click="centerDialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="centerDialogVisible = false">确 定</el-button>
  </span>
</el-dialog>
        <div style="padding: 10px 0;">
       
        </div>
      <el-table :data="tableData" border stripe >
        <el-table-column prop="schoolName" label="学校" width="140">
        </el-table-column>
        <el-table-column prop="maxContain" label="学校容量" width="140">
        </el-table-column>
        <el-table-column prop="plotName" label="考点名称" width="120">
        </el-table-column>
        <el-table-column prop="middleContain" label="考点容量" width="140">
        </el-table-column>
        
        <el-table-column prop="minContain" label="教室容量" width="120" >
        </el-table-column>
        <el-table-column prop="able" label="可用状态" width="120" >
        </el-table-column>
        <el-table-column
            label="操作"
            width="200">
            <template slot-scope="scope">      
            <!-- <el-button type="primary" @click="ban(scope.row)">修改</el-button>
            <el-button type="danger" @click="ban(scope.row)">删除考试</el-button> -->
            <el-button type="danger" @click="disable(scope.row)">考点禁用</el-button>
            <el-button type="primary" @click="able(scope.row)">考点启用</el-button>

           
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
      id:1,
       schoolName:'成都工业学院',
       maxContain:1000,
       plotName:'允明楼',
       middleContain:800,
       minContain:40
     };
     return {
       tableData: Array(10).fill(item),
       centerDialogVisible: false,
       workform:{},
       
      currentPage: 1,
      pageSize:10,
       search:"",
       total:400,
     }
   },methods: {
      handleSizeChange(val) {
        console.log(`每页 ${val} 条`);
        this.load()

      },
      handleCurrentChange(val) {
        console.log(`当前页: ${val}`);
        this.load()

      },
      load(){
        request.post("/admin/plot",{
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
      disable(row){
        if(row.able===0){
          alert("该考点已经禁用")
          return
        }
        request.post("/admin/plot/disable",{
          id:row.id
        }).then(res=>{
            if(res.code==200){
              alert("修改成功")
              this.load()
            }else{
              alert("修改失败")
            }
        })
      },
      able(row){
        if(row.able===1){
          alert("该考点已经启用")
        }
        request.post("/admin/plot/able",{
          id:row.id
        }).then(res=>{
          if(res.code==200){
              alert("修改成功")
              this.load()
            }else{
              alert("修改失败")
            }
        })

      }
    },
    created(){
      this.load()
    }

   
 }

</script>