<template>
  <!--  app.vue页面可以用来布局界面
  <router-view/> 是路由指令
  会把路由到的页面内容展示或替换到这个位置
  根据url地址"/"，"/about"进行不同的路由
  <nav>
      <router-link to="/">Home</router-link> |
      <router-link to="/about">About</router-link>
    </nav>
    <router-view/>
  -->
  <div>
    <!--     引入Header组件  头部-->
    <Header/>
    <!--     主体 -->
    <div style="display: flex">
      <!--       侧边栏-->
      <Aside/>
      <!--       内容区域 通过路由获取 路由到HomeView.vue 根据地址路由相应页面 -->
      <router-view style="flex: 1px"/>
    </div>
  </div>
</template>
<script>
import Header from "@/components/Header";
import Aside from "@/components/Aside";

export default {
  name: "Layout",
  components: {
    Header,
    Aside
  }
}
</script>
<style>

</style>
