import axios from "axios";
let defaultURL="http://localhost:9090"
// const httpService=axios.create({
//     baseURL:defaultURL,
//     timeout:5000
// });
//请求拦截器
const request= axios.create({
    baseURL:defaultURL,
    timeout:5000
})


request.interceptors.request.use(
    function(config){
    config.headers['Content-Type']='application/json;charset=utf-8';
    config.headers.token=sessionStorage.getItem('token')
    console.log("已经入axios配置js")
    console.log(sessionStorage.getItem('token'))
    return config
    },function(error){
        return Promise.reject(error)
    }
)
request.interceptors.response.use(response=>{
    let res=response.data;
    if(response.config.responseType==='blob'){return res}
    if(typeof res==='string'){
        res=res ? JSON.parse(res):res
    }
    return res;
},
        error=>{
            console.log('err'+error)
            return Promise.reject(error)
        }

)



export default request
