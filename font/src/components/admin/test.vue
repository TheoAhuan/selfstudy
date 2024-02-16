<template>
    <el-main>
        <div style="padding: 10px 0;">
        <el-input style="width: 300px;"
        v-model="search"
          placeholder="查询已经发布的考试信息" 
          suffix-icon="el-icon-search"></el-input>
          <el-button type="primary" style="height: auto;" @click="getByS">搜索</el-button> 
          <el-button type="success" style="height: auto;" @click="addDialogVisible=true">新建</el-button>
          <el-dialog
              title="新建考试"
              :visible.sync="addDialogVisible"
              width="80%"

              :before-close="handleClose">
              <el-form :model="newTable"  label-width="250px" >
                  
                  <el-form-item  label="考试名称">
                  
                  
                  <el-input v-model="newTable.name" placeholder="" ></el-input>
                
                  </el-form-item>
                  <el-form-item  label="报名开始时间"  >
                    <el-date-picker
                        v-model="newTable.enterStartTime"
                        type="datetime"
                        placeholder="选择报名开始时间"
                        default-time="12:00:00">
                      </el-date-picker>
                  </el-form-item>
                  <el-form-item  label="报名截止时间">
                    <el-date-picker
                        v-model="newTable.enterEndTime"
                        type="datetime"
                        placeholder="选择考试报名截止时间"
                        default-time="12:00:00">
                      </el-date-picker>
                  </el-form-item>
                  <el-form-item  label="考试开始时间">
                  
                 
                  <el-date-picker
                        v-model="newTable.outStartTime"
                        type="datetime"
                        placeholder="选择考试开始时间"
                        default-time="12:00:00">
                      </el-date-picker>
                
                </el-form-item>
                  <el-form-item  label="考试结束时间">
                  
                  

                  <el-date-picker
                        v-model="newTable.outEndTime"
                        type="datetime"
                        placeholder="选择考试结束时间"
                        default-time="12:00:00">
                      </el-date-picker>
                
                </el-form-item>
                <el-form-item  label="考试人数">
                  
                  
                  <el-input v-model="newTable.remain" placeholder="" ></el-input>
                
                  </el-form-item>
                  <el-form-item  label="报名费用">
                  
                  
                  <el-input v-model="newTable.price" placeholder="" ></el-input>
                
                  </el-form-item>
              </el-form>    
              <span slot="footer" class="dialog-footer">
                 <el-button @click="addDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="createEnroll">确 定</el-button>
              </span>
              
            </el-dialog>
          

        </div>
      <el-table :data="tableData" border stripe >
        <el-table-column prop="id" label="id" width="100">
        </el-table-column>
        <el-table-column prop="name" label="考试名称"  width="120">
        </el-table-column>
        <el-table-column prop="enterStartTime" label="报名开始时间" width="200" >
        </el-table-column>
        <el-table-column prop="enterEndTime" label="报名截止时间" width="200">
        </el-table-column>
        <el-table-column prop="outStartTime" label="考试开始时间" width="200">
        </el-table-column>
        <el-table-column prop="outEndTime" label="考试结束时间" width="200">
        </el-table-column>
        <el-table-column prop="remain" label="考试人数" width="200">
        </el-table-column>
        <el-table-column prop="price" label="报名费" width="200">
        </el-table-column>
      
        <el-table-column
            label="操作"
            width="300">
            <template slot-scope="scope">
            <el-button type="primary" @click="edit(scope.row)">修改考试</el-button>
            <el-dialog
              title="提示"
              :visible.sync="dialogVisible"
              width="80%"

              :before-close="handleClose">
              <el-form :model="editTable"  label-width="250px" >
                  <el-form-item v-model="editTable.id" label="id">{{editTable.id}}</el-form-item>
                  <el-form-item  label="考试名称">
                  
                  
                  <el-input v-model="editTable.name" placeholder="" ></el-input>
                
                  </el-form-item>
                  <el-form-item  label="报名开始时间" >
                    <el-date-picker
                        v-model="editTable.enterStartTime"
                        type="datetime"
                        placeholder="选择报名开始时间"
                        default-time="12:00:00">
                      </el-date-picker>
                  </el-form-item>
                  <el-form-item  label="报名截止时间">
                    <el-date-picker
                        v-model="editTable.enterEndTime"
                        type="datetime"
                        placeholder="选择考试报名截止时间"
                        default-time="12:00:00">
                      </el-date-picker>
                  </el-form-item>
                  <el-form-item  label="考试开始时间">
                  
                 
                  <el-date-picker
                        v-model="editTable.outStartTime"
                        type="datetime"
                        placeholder="选择考试开始时间"
                        default-time="12:00:00">
                      </el-date-picker>
                
                </el-form-item>
                  <el-form-item  label="考试结束时间">
                  
                  

                  <el-date-picker
                        v-model="editTable.outEndTime"
                        type="datetime"
                        placeholder="选择考试结束时间"
                        default-time="12:00:00">
                      </el-date-picker>
                
                </el-form-item>
                <el-form-item  label="考试人数">
                  
                  
                  <el-input v-model="editTable.remain" placeholder="" ></el-input>
                
                  </el-form-item>
                  <el-form-item  label="报名费用">
                  
                  
                  <el-input v-model="editTable.price" placeholder="" ></el-input>
                
                  </el-form-item>
              </el-form>    
              <span slot="footer" class="dialog-footer">
                 <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="makesure">确 定</el-button>
              </span>
              
            </el-dialog>
            <el-button type="danger" @click="del(scope.row)">删除考试</el-button>
            
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
   name:'test',
   data() {
     const item = {
        id:'1',
        test_name:'四六级',
        enter_start_time:'2016-05-02',
        out_start_time:'2016-05-02',
        enter_end_time:'2016-05-02',
        out_end_time:'2016-05-02',
       school_name: '上海市普陀区金沙江路 1518 弄'
     };
     return {
      newTable:{},
       tableData: Array(10).fill(item),
       addDialogVisible:false,
       currentPage:1 ,
       pageSize:10,
       search:"",
       total:400,
       dialogVisible:false,
       form:{
        // enterEndTime: "",
        // enterStartTime: "",  
        // id: '',
        // name: "",
        // outEndTime: "",
        // outStartTime: ""
      
      
      },
      editTable:'',
     }
   },methods: {
    getByS(){
      console.log(this.search)
      this.load()
    },
    handleClose(done) {
        this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
      },
      handleSizeChange(val) {
        console.log(`每页 ${val} 条`);
        this.load();
      },
      handleCurrentChange(val) {
        console.log(`当前页: ${val}`);
        console.log(this.currentPage)
        console.log(val)
        this.currentPage=val
        this.load();

      },
      load(){
        request.post('admin/getTest',{
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
      edit(editTable){
        this.editTable=editTable
        this.dialogVisible=true


      },
      makesure(){
        console.log(this.form)
        console.log(this.form.id)
        console.log(this.form.name)
        request.post("/enroll/edit",{
          id:this.editTable.id,
          
          enterStartTime:this.editTable.enterStartTime,
          enterEndTime:this.editTable.enterEndTime,
          outStartTime:this.editTable.outStartTime,
          outEndTime:this.editTable.outEndTime,
          name:this.editTable.name,
          price:this.editTable.price
          
        }).then(res=>{
          this.dialogVisible=false
          alert("suc")
          this.load()

        })
      },
      newTest(){
        this.addDialogVisible=true
      },
      createEnroll(){
        request.post("/admin/create/enroll",{
       
          
          enterStartTime:this.newTable.enterStartTime,
          enterEndTime:this.newTable.enterEndTime,
          outStartTime:this.newTable.outStartTime,
          outEndTime:this.newTable.outEndTime,
          name:this.newTable.name,
          price:this.newTable.price,
          remain:this.newTable.remain
          
        }).then(res=>{
          if(res.data=='ok'){
            this.addDialogVisible=false
          alert("suc")
          this.load()

          }else{
            alert("您的数据有错误")
          }
          

        })
      },
      del(row){
        request.post("/admin/del/enroll",{
          id:row.id
        }).then(res=>{
          if(res.data=='ok'){
            
          alert("suc")
          this.load()

          }else{
            alert("删除错误")
          }
          

        })
      }


    },
    created(){
      this.load();
    }
    

   
 }

</script>