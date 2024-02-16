<template>
    <el-container style="min-height:100%;">
    <el-header  style="text-align: right; font-size: 12px;border: bottom 1px; #ccc;line-height=60px" >
      <el-dropdown>
        <i class="el-icon-setting" style="margin-right: 15px"></i>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item>查看</el-dropdown-item>
          <el-dropdown-item>新增</el-dropdown-item>
          <el-dropdown-item>删除</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
      <span>王小虎</span>
    </el-header>
    <el-main>
        <div style="padding: 10px 0;">
        <el-input style="width: 300px;"  placeholder="搜索" suffix-icon="el-icon-search"></el-input><el-button type="primary" style="height: auto;">搜索</el-button>
        
        </div>
      <el-table :data="tableData" border stripe >
        <el-table-column prop="date" label="日期" width="140">
        </el-table-column>
        <el-table-column prop="name" label="姓名" width="120">
        </el-table-column>
        <el-table-column prop="address" label="地址">
        </el-table-column>
      </el-table>
      <div class="block">
    <span class="demonstration">完整功能</span>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="currentPage4"
      :page-sizes="[10, 15, 20]"
      :page-size="100"
      layout="total, sizes, prev, pager, next, jumper"
      :total="400">
    </el-pagination>
  </div>
    </el-main>
  </el-container>
</template>
<script>
export default {
   
   data() {
     const item = {
       date: '2016-05-02',
       name: '王小虎',
       address: '上海市普陀区金沙江路 1518 弄'
     };
     return {
       tableData: Array(10).fill(item),
       currentPage1: 5,
        currentPage2: 5,
        currentPage3: 5,
        currentPage4: 4
     }
   },methods: {
      handleSizeChange(val) {
        console.log(`每页 ${val} 条`);
      },
      handleCurrentChange(val) {
        console.log(`当前页: ${val}`);
      }
    },



 }


</script>
<style scoped>
  .el-header {
    background-color: #B3C0D1;
    color: #333;
    line-height: 60px;
  }
  
  .el-aside {
    color: #333;
  }
</style>