<template>
  <div class="home" id="home" name="home">
    
    
   
    <div class="main-box">
      <div class="main">
        
        

        
        <div class="appliance" id="promo-menu">
          <div class="box-hd">
            <div class="title">报名专业</div>
            <div class="more" id="more">
              
            </div>
          </div>
          <div class="box-bd">
            <div class="promo-list">
              <ul>
                <li>
                  
                </li>
                <li>
                  
                  
                </li>
              </ul>
            </div>
            <div class="list">
              <MyList :list="queryList" :isMore="true"></MyList>
            </div>
          </div>
        </div>
      

      </div>
    </div>
      
      
    </div>
</template>
<script>
import MyList from '@/components/student/MyList.vue';
export default{
  name:"homeview",
  components:{
    MyList
  },
  
    data(){
      return{
        queryList:[
          {
            subject_id:1,
            subject_name:'软件工程',
            price:40,
          },
          {
            subject_id:2,
            subject_name:'物联网工程',
            price:40,

          },
          {
            subject_id:3,
            subject_name:'汉语言文学',
            price:40,

          },
          {
            subject_id:4,
            subject_name:'外国语专业',
            price:40,

          },
          {
            subject_id:5,
            subject_name:'金融管理',
            price:40,

          },
          {
            subject_id:6,
            subject_name:'数学',
            price:40,


          },
          {
            subject_id:7,

            subject_name:'计算机科学',
            price:40,

          },
          
        ]
      }
    }
}

</script>
<style scoped>
@import "../assets/css/index.css";
</style>