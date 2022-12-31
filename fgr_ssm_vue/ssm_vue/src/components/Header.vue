<template>
<!--  管理页面的头部
分成三个部分div
flex: 1  中间填充的弹性布局
-->
  <div style="height: 50px; line-height:50px;border-bottom:1px solid #ccc;display: flex">
    <div style="width: 200px; padding-left: 30px;font-weight: bold;color: dodgerblue">后台管理</div>
    <div style="flex: 1"></div>
    <div style="width: 100px;">
      <el-dropdown>
    <span class="el-dropdown-link" >
      Tom
    </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item>个人信息</el-dropdown-item>
            <el-dropdown-item>退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
export default {
  name: "Header"
}
</script>

<style scoped>

</style>