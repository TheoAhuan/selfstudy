import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/HomeView.vue'
import AdminView from '../views/AdminView.vue'

Vue.use(VueRouter)
//解决编程式路由往同一地址跳转时会报错的情况
const originalPush = VueRouter.prototype.push;
const originalReplace = VueRouter.prototype.replace;
//push
VueRouter.prototype.push = function push(location, onResolve, onReject) {
  if (onResolve || onReject)
    return originalPush.call(this, location, onResolve
      , onReject);
  return originalPush.call(this, location).catch(err => err);
};
//replace
VueRouter.prototype.replace = function push(location, onResolve, onReject) {
  if (onResolve || onReject)
    return originalReplace.call(this, location, onResolve, onReject);
  return originalReplace.call(this, location).catch(err => err);
};


const routes = [
  {
    path: '/',
    name: 'plain',
    component:()=>import('../views/Plain.vue'), 
  },

  {
    path:'/toStudent',
    name:'toStudent',
    component:()=>import('../views/StudentView.vue'),
    children:[
      {
        path:'/toStudent',
        name:'da',
        component:HomeView
      },
      {
        path:'/subject/details/:id/:name/:price',
        name:'details',
        component:()=>import('../views/Details.vue'),
        
      },{
        path:'/subject/all',
        name:'all',
        
      },
      {
        path: '/goods',
        name: 'Goods',
        component: () => import('../views/Goods.vue')
      },
      {
        path:'/manage',
        name:'manage',
        component:()=>import('../views/StudentManage.vue')
      },
      {
        path:'/toExam',
        name:'exam',
        component:()=>import('../views/Exam.vue')

      }
      
    ]
  },
  ,
  {
    path:'/admin',
    name:'admin',
    component:AdminView,
    children:[
      {
        path:'/noban',
        name:'noban',
        component:()=>import('../components/admin/noban')
    
      },
      {
        path:'/ban',
        name:'ban',
        component:()=>import('../components/admin/ban')
    
      },{
        path:'/test',
        name:'test',
        component:()=>import('../components/admin/test')
    
      },
      {
        path:'/plot',
        name:'plot',
        component:()=>import('../components/admin/plot')
      },
      {
        path:'/ticket',
        name:'ticket',
        component:()=>import('../views/Ticket.vue')
      },
      {
        path:'/admin/grade',
        name:"checkgrade",
        component:()=>import('../components/admin/grade.vue')
        
      }

    ]


  },

  {
    path:'/table',
    name:'table',
    component:()=>import('../components/admin/tables')

  }
  ,{
    path:'/admin2',
    name:'admin2',
    


  },{
    path:'/asides',
    name:'asides',
    component:()=>import('../components/admin/Aside')

  }
 
]

const router = new VueRouter({
  routes,
  mode:'history'
})

export default router
