<template>
    <el-aside width="180px"  style="background-color: rgb(238, 241, 246);overflow-x:hidden; ">
            <el-menu :default-openeds="['1', '3']"   
            background-color='rgb(48,65,86)'
            text-color="#ffff"
            active-text-color="#ffd04b"
            height="100%"
            @select="changeSidebar"
            :router ="true"
            >
                <el-submenu index="1">
                    <template slot="title"><i class="el-icon-menu"></i>学籍管理</template>
                    <el-menu-item-group>
                    <template slot="title">管理学生学籍</template>
                    <el-menu-item index="/noban">查看未禁止考试学生</el-menu-item>
                    <el-menu-item index="/ban">查看已禁止考试学生</el-menu-item>
                    </el-menu-item-group>
                 </el-submenu>
                 <el-submenu index="2">
                    <template slot="title"><i class="el-icon-menu"></i>考务管理</template>
                    <el-menu-item-group>
                    <template slot="title">管理考试内容</template>
                    <el-menu-item index="/test">考试管理</el-menu-item>
                    <el-menu-item index="/plot">考点管理</el-menu-item>
                    <!-- <el-menu-item index="/ticket">学生准考证管理</el-menu-item> -->

                    </el-menu-item-group>
                 </el-submenu>
                 <el-submenu index="3">
                    <template slot="title"><i class="el-icon-menu"></i>成绩管理</template>
                    <el-menu-item-group>
                    <template slot="title">管理学生学籍</template>
                    <el-menu-item index="/admin/grade">设置学生成绩</el-menu-item>
                    <!-- <el-menu-item index="/com">发放毕业证</el-menu-item> -->
                    </el-menu-item-group>
                 </el-submenu>
                 
                 

            </el-menu>
        </el-aside>
</template>
<script>
export default{
    name:'asides',
    data(){
        return{

        }
    },
    mounted() {
        this.sidebarItem = this.$route.name;
    },
    methods: {
        changeSidebar(path) {
            this.$router.push(path);
        },
    }



}

</script>