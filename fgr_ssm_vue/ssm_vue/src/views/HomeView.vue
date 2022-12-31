<template>
  <div >
<!--    增加按钮和搜索框-->
    <div style="margin: 10px 5px">
<!--      @click="add" 表示点击新增会出发add方法-->
      <el-button type="primary" @click="add">新增</el-button>
      <el-button >其他</el-button>
    </div>
    <div style="margin: 10px 5px">
      <el-input v-model="search" style="width: 30%" placeholder="请输入关键字"/>
      <el-button style="margin-left: 10px" type="primary" @click="list">检索</el-button>
    </div>
    <el-table :data="tableData" stripe style="width: 90%">
      <el-table-column prop="id" label="ID" />
      <el-table-column prop="name" label="家居名" />
      <el-table-column prop="maker" label="厂家" />
      <el-table-column prop="price" label="价格" />
      <el-table-column prop="sales" label="销量" />
      <el-table-column prop="stock" label="库存" />
      <el-table-column fixed="right" label="操作" width="120">
<!--        这里handleEdit(scope.row)通过scope.row可以将当前行的数据传递给方法-->
        <template #default="scope">
          <el-button link type="text" size="small" @click="handleEdit(scope.row)"
          >编辑</el-button
          >
<!--          点击确定会触发handleDel，点击取消不会-->
          <el-popconfirm title="确认删除吗?" @confirm="handleDel(scope.row.id)">
            <template #reference>
              <el-button type="danger" size="small">删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
<!--  1. v-model="form.name" 表示表单的input控件，名字为name 需要和后台的javaBean（Furn）属性一致
      2. 前端中对象属性是可以动态生成的 前端技术栈
-->
    <el-dialog v-model="dialogVisible" title="提示" width="40%" >
      <el-form :model="form" :rules="rules" ref="form" label-width="120px">
        <el-form-item label="家居名" prop="name">
          <el-input v-model="form.name" style="width: 50%" ></el-input>
          {{serverValidErrors.name}}
        </el-form-item>
        <el-form-item label="厂商" prop="maker">
          <el-input v-model="form.maker" style="width: 45%" ></el-input>
          {{serverValidErrors.maker}}
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input v-model="form.price" style="width: 50%" ></el-input>
          {{serverValidErrors.price}}
        </el-form-item>
        <el-form-item label="销量" prop="sales">
          <el-input v-model="form.sales" style="width: 50%" ></el-input>
          {{serverValidErrors.sales}}
        </el-form-item>
        <el-form-item label="库存" prop="stock">
          <el-input v-model="form.stock" style="width: 50%" ></el-input>
          {{serverValidErrors.stock}}
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="save">确定</el-button>
      </span>
      </template>
    </el-dialog>
<!--    添加分页导航-->
    <div style="margin: 10px 0">
      <el-pagination
          @size-change="handlePageSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[2,5,10,15]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>
  </div>
</template>

<script>
//导入组件 @ is an alias to /src
//导入request 对象
import request from "@/utils/request";
//导出组件
export default {
  name: 'HomeView',
  components: {
  },
  data() {
    return {
      //存放后端校验的错误信息
      serverValidErrors:{},
      //增加分页相应的数据绑定
      currentPage:1,//当前页
      pageSize:5,//每页记录
      total:10,//共有多少记录
      search: '',//检索条件，进行分页时保留上次检索条件
      dialogVisible:false,
      form:{},//定义一个空表单
      tableData:[],
      //编写添加表单的校验规则
      rules:{
        name:[
            //可以写多个针对name属性的校验规则
          {required:true,message:"请输入家居名",trigger:"blur"}
        ],
        maker:[
          {required:true,message:"请输入制造商名",trigger:"blur"}
        ],
        price:[
          {required:true,message:"请输入价格",trigger:"blur"},
          //使用正则表达式对输入的数据进行校验
          {pattern:/^([1-9]\d*|0)(\.\d+)?$/,message: "请输入数字",trigger: "blur"}
        ],
        sales:[
          {required:true,message:"请输入销量",trigger:"blur"},
          {pattern:/^([1-9]\d*|0)$/,message: "请输入整数",trigger: "blur"}
        ],
        stock:[
          {required:true,message:"请输入库存",trigger:"blur"},
          {pattern:/^([1-9]\d*|0)$/,message: "请输入整数",trigger: "blur"}
        ]
      }
    }
  },
  created() {
    this.list();//调用list方法
  },
  methods:{
    add(){//添加时显示对话框-带表单
      // 显示对话框
      this.dialogVisible = true
      //清空添加表单数据
      this.form = {}
      //清空上次校验的信息
      this.$refs['form'].resetFields()
      //清空上次后端校验信息
      this.serverValidErrors = {}
    },
    save(){//表单数据发送给后端
      // 修改和添加走不同通道 通过表单是否含有id判断方法
      if (this.form.id){//表示修改
        //put本质是发出一个ajax请求- 异步处理 发送请求回调函数（res => {）与继续执行同步进行，list()放if外拿到的有可能还是未修改的数据
        request.put("/api/update",this.form).then(res => {
            if (res.code === 200){//修改成功
              //提示一个成功的消息框
              this.$message(
                  {
                    type:"success",
                    message:"更新成功"
                  }
              )
            }else {
              //提示一个错误的消息框
              this.$message(
                  {
                    type:"error",
                    message:"更新失败"
                  }
              )
            }
            //关闭对话框 更新数据
          this.dialogVisible = false
          this.list()
        })
      }else{
        // 表单是否通过
        this.$refs['form'].validate((valid) => {
          // valid = true//先放行用来测试后端校验
          //valid是表单校验后返回的结果
          if (valid){//校验通过
            //this.form 携带的数据
            request.post("/api/save",this.form).then(res => {
              // console.log("res = " ,res)
              if (res.code === 200){
                this.dialogVisible = false
                //调用list方法，刷新数据
                this.list()
              }else if(res.code === 400){//后端校验失败
                //取出校验失败的信息 赋给serverValidErrors
                this.serverValidErrors.name = res.extend.errorMsg.name
                this.serverValidErrors.maker = res.extend.errorMsg.maker
                this.serverValidErrors.price = res.extend.errorMsg.price
                this.serverValidErrors.sales = res.extend.errorMsg.sales
                this.serverValidErrors.stock = res.extend.errorMsg.stock
              }

            })
          }else{//校验没有通过
            //提示一个错误的消息框
            this.$message(
                {
                  type:"error",
                  message:"格式未通过校验，放弃提交"
                }
            )
          }
          return false//放弃提交
        })

      }
    },
    //编写一个list自动触发方法 请求返回家具信息
    list(){
      // request.get("/api/furns").then(res => {
      //   console.log("res-",res)
      //   //根据console控制台输出的数据查看结构
      //   //对返回的response结果进行了统一拦截处理let res = response.data
      //   this.tableData = res.extend.furnList
      // })
      //请求分页的接口-带检索条件
      request.get("/api/furnsByConditionPage",{
        params:{//指定请求携带的参数
          pageNum:this.currentPage,
          pageSize:this.pageSize,
          search:this.search
        }
      }).then(res => {//处理返回的分页信息
        this.tableData = res.extend.pageInfo.list
        this.total = res.extend.pageInfo.total
      })
    },
    handleEdit(row){
      // console.log("row---",row)
      //当前的家具信息绑定到弹出对话框的form
      //1.常规 通过row.id获取对应的家具信息 返回信息绑定到this.form[自留作业]
      request.get("/api/furn/" + row.id).then(res => {
        this.form = res.extend.furn
      })
      //2.把获取到的row的数据通过处理，绑定到this.form
      //将row转成json字符串 json字符串转成json对象
      // this.form = JSON.parse(JSON.stringify(row));
      //
      this.dialogVisible = true
    },
    handleDel(id){
      // console.log("id--",id)
      request.delete("/api/del/" + id).then(res => {
        if (res.code === 200){//删除成功
          //提示一个成功的消息框
          this.$message(
              {
                type:"success",
                message:res.msg
              }
          )
        }else{
          //提示一个错误的消息框
          this.$message(
              {
                type:"error",
                message:res.msg
              }
          )
        }
        // 刷新数据
        this.list()
      })
    },
    handleCurrentChange(pageNum){//处理分页请求
      //用户点击分页超链接时，会携带pageNum
      console.log("pageNum= ",pageNum)
      this.currentPage = pageNum
      //发出分页请求
      this.list()
    },
    handlePageSizeChange(pageSize){
      this.pageSize = pageSize
      this.list()
    }
  }
}
</script>
