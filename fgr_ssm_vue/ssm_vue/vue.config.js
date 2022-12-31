const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true
})

module.exports = {
  devServer: {
    port:10000,//启动端口
    //请求地址/api/save  代理至http://localhost:8080/ssm/save
    proxy:{
      '/api':{                  //设置拦截器 拦截器格式
        target:'http://localhost:8080/ssm',   //代理目标地址
        changeOrigin:true,                //是否设置同源
        pathRewrite:{                     //路径重写
          '/api':''                       //选择忽略拦截器里面的单词
        }
      }
    }
  }
}