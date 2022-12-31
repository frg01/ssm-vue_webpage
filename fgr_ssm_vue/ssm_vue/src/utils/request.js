//引入axios包
import axios from "axios";
//通过axios创建对象-request对象，用于发送请求到后端
const request = axios.create({
    timeout:5000
})

//request拦截器的处理
//1.可以对请求做统一处理 比如统一加token，Content-Type等
request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=utf-8'
    return config
},error => {
    return Promise.reject(error)
})

//response拦截器
//调用接口响应后，统一处理返回结果
request.interceptors.response.use(
    response => {
        let res = response.data
        //如果返回的是文件 就继续返回
        if (response.config.responseType === 'blob'){
            return res
        }
        //如果是String 就转成json对象
        if (typeof res === 'string'){
            //如果res不为null，就进行转换成json对象
            res = res ? JSON.parse(res) : res
        }
        return res;
    },
    error => {
        console.log("err".error)
        return Promise.reject(error)
    }
)

//导出request对象，在其他文件引入即可使用
export default request