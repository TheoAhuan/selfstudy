<template>
    <el-container style="height:100%; border: 1px solid #eee">
        <Aside></Aside>
        
        <el-container height="100%" style="min-height:100%;" direction="vertical">
        <Elheader></Elheader>
        <div>
        <router-view></router-view>
        </div>
        </el-container>
</el-container>
</template>

<script>
import Aside from '@/components/admin/Aside.vue';
import Elheader from '@/components/admin/Elheader.vue';
import Tables from '@/components/admin/tables.vue';
import AdminMain from '@/components/admin/AdminMain'
  export default {
    name:'admin',
    components:{
        Aside,
        Elheader,
        Tables,
        AdminMain
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