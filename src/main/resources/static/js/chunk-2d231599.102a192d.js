(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-2d231599"],{efbd:function(e,n,c){"use strict";c.r(n);var o=function(){var e=this,n=e.$createElement,c=e._self._c||n;return c("a-card",{attrs:{"body-style":{padding:"24px 32px"},bordered:!1}},[c("icon-selector",{on:{change:e.handleIconChange},model:{value:e.currentSelectedIcon,callback:function(n){e.currentSelectedIcon=n},expression:"currentSelectedIcon"}}),c("a-divider"),c("p",[e._v("测试 IconSelector 组件 v-model 功能")]),c("a-button",{on:{click:function(n){return e.changeIcon("down")}}},[e._v("改变 Icon-down")]),c("a-divider",{attrs:{type:"vertical"}}),c("a-button",{on:{click:function(n){return e.changeIcon("cloud-download")}}},[e._v("改变 Icon-cloud-download")])],1)},t=[],a=c("13bb"),r={name:"IconSelectorView",components:{IconSelector:a["a"]},data:function(){return{currentSelectedIcon:"pause-circle"}},methods:{handleIconChange:function(e){var n=this.$createElement;console.log("change Icon",e),this.$message.info(n("span",["选中图标 ",n("code",[e])]))},changeIcon:function(e){this.currentSelectedIcon=e}}},l=r,d=c("4e82"),u=Object(d["a"])(l,o,t,!1,null,null,null);n["default"]=u.exports}}]);