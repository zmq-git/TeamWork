(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-aaa37170"],{"0810":function(t,e,a){},"155a":function(t,e,a){"use strict";a.d(e,"a",(function(){return s})),a.d(e,"b",(function(){return i})),a.d(e,"c",(function(){return c}));a("63ff");var n=a("a8f6"),r=a("0c6d");function s(t){return r["a"].fetchG({url:"/teamwork-api/project/getIterInfoByUserId",data:t})}function i(t){return o.apply(this,arguments)}function o(){return o=Object(n["a"])(regeneratorRuntime.mark((function t(e){return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,r["a"].fetchG({url:"/teamwork-api/project/queryStateInfoById",data:e});case 2:return t.abrupt("return",t.sent);case 3:case"end":return t.stop()}}),t)}))),o.apply(this,arguments)}function c(t){return r["a"].fetchG({url:"/teamwork-api/project/updateTaskState",data:t})}},4509:function(t,e,a){"use strict";var n=a("0810"),r=a.n(n);r.a},5950:function(t,e,a){"use strict";var n=a("c159"),r=a.n(n);r.a},a472:function(t,e,a){"use strict";a.r(e);var n=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",[a("a-layout",[a("a-layout-sider",{style:{background:"#fff"}},[a("a-input-search",{staticStyle:{"margin-bottom":"8px"},attrs:{placeholder:"Search"},on:{change:t.onChange}}),a("a-tree",{attrs:{expandedKeys:t.expandedKeys,autoExpandParent:t.autoExpandParent,treeData:t.treeData},on:{expand:t.onExpand,select:t.onSelect},scopedSlots:t._u([{key:"title",fn:function(e){return[e.title.indexOf(t.searchValue)>-1?a("span",[t._v("\n                "+t._s(e.title.substr(0,e.title.indexOf(t.searchValue)))+"\n                "),a("span",{staticStyle:{color:"#f50"}},[t._v(t._s(t.searchValue))]),t._v("\n                "+t._s(e.title.substr(e.title.indexOf(t.searchValue)+t.searchValue.length))+"\n                "),t.selectedKeys==e.key||t.selectedKeysCopy==e.key?a("span",[a("a-tooltip",{attrs:{placement:"top"}},[a("template",{slot:"title"},[a("span",[t._v("编辑分类")])]),a("a-icon",{attrs:{type:"edit"},on:{click:function(e){return e.stopPropagation(),t.editAuth(e)}}})],2),a("a-icon",{staticStyle:{"margin-left":"5px"},attrs:{type:"plus-circle"},on:{click:function(e){return e.stopPropagation(),t.addAuth(e)}}}),a("a-icon",{staticStyle:{"margin-left":"5px"},attrs:{type:"minus-circle"},on:{click:function(e){return e.stopPropagation(),t.removeAuth(e)}}})],1):t._e()]):a("span",[t._v("\n                  "+t._s(e.title)+"\n                  "),t.selectedKeys==e.key||t.selectedKeysCopy==e.key?a("span",[a("a-tooltip",{attrs:{placement:"top"}},[a("template",{slot:"title"},[a("span",[t._v("编辑分类")])]),a("a-icon",{attrs:{type:"edit"},on:{click:function(e){return e.stopPropagation(),t.editAuth(e)}}})],2),a("a-icon",{staticStyle:{"margin-left":"5px"},attrs:{type:"plus-circle"},on:{click:function(e){return e.stopPropagation(),t.addAuth(e)}}}),a("a-icon",{staticStyle:{"margin-left":"5px"},attrs:{type:"minus-circle"},on:{click:function(e){return e.stopPropagation(),t.removeAuth(e)}}})],1):t._e()])]}}])})],1),a("a-layout",[a("a-layout-content",{style:{margin:"24px 16px",background:"#fff",minHeight:"350px"}},[a("task-content")],1)],1),a("a-modal",{attrs:{visible:t.organizeVisible,cancelText:"取消",okText:"确认"},on:{cancel:t.handleCancel,ok:t.handleConfirm}},[a("span",{staticClass:"common-head-font",attrs:{slot:"title"},slot:"title"},[t._v("上级组织选择")]),a("a-tree",{attrs:{expandedKeys:t.expandedKeysCopy,autoExpandParent:t.autoExpandParentCopy,treeData:t.treeData},on:{expand:t.onExpandCopy,select:t.onSelect}})],1),a("a-modal",{attrs:{visible:t.classDeal,width:800},on:{cancel:function(e){t.classDeal=!1},ok:function(e){t.classDeal=!1}}},[a("a-card",{staticStyle:{"margin-top":"10px"}},[a("template",{slot:"title"},[2==t.type?a("span",{staticClass:"common-head-font"},[t._v("修改任务")]):0==t.type?a("span",{staticClass:"common-head-font"},[t._v("新增分类")]):t._e()]),a("a-row",[a("a-form",{attrs:{form:t.form}},[a("a-col",{attrs:{span:11}},[a("a-form-item",{attrs:{label:"分类名称","label-col":t.formItemLayout.labelCol,"wrapper-col":{span:18}}},[a("a-row",{attrs:{gutter:100}},[a("a-col",{attrs:{span:21}},[a("a-input",{directives:[{name:"decorator",rawName:"v-decorator",value:["className",{rules:[{required:!0,message:"请输入任务分类名称"}]}],expression:"[\n                                    'className',\n                                    {rules: [{ required: true, message: '请输入任务分类名称' }]}\n                                  ]"}]})],1)],1)],1)],1),a("a-col",{attrs:{span:11}},[a("a-form-item",{attrs:{label:"上级分类","label-col":t.formItemLayout.labelCol,"wrapper-col":{span:18}}},[a("a-row",{attrs:{gutter:10}},[a("a-col",{attrs:{span:21}},[a("a-input",{directives:[{name:"decorator",rawName:"v-decorator",value:["parentClassId",{rules:[{required:!0,message:"请输入上级任务分类"}]}],expression:"[\n                                    'parentClassId',\n                                    {rules: [{ required: true, message: '请输入上级任务分类' }]}\n                                  ]"}],attrs:{disabled:!0}})],1)],1)],1)],1),a("a-col",{attrs:{span:2}},[a("a-form-item",{attrs:{"label-col":t.formItemLayout.labelCol,"wrapper-col":{span:18}}},[a("a-icon",{staticStyle:{"font-size":"20px",color:"#18c4b0",cursor:"pointer"},attrs:{type:"plus-circle"},on:{click:function(e){return e.stopPropagation(),t.addOrganize(e)}}})],1)],1)],1)],1),a("a-row",{attrs:{justify:"end",type:"flex"}},[a("a-button",{staticStyle:{width:"100px"},attrs:{type:"primary"},on:{click:t.submitOrg}},[t._v("提交")])],1)],2)],1)],1),a("a-modal",{attrs:{title:"再次确认",okText:"确认",cancelText:"取消"},on:{ok:t.hideModal},model:{value:t.visible,callback:function(e){t.visible=e},expression:"visible"}},[a("p",[t._v("确认要删除吗？")])])],1)},r=[],s=(a("5279"),a("9f92")),i=a("22a0"),o=a("d552"),c=a("5fd0"),l=a("a78e"),u=[],d=function t(e,a){for(var n,r=0;r<a.length;r++){var s=a[r];s.children&&(s.children.some((function(t){return t.key===e}))?n=s.key:t(e,s.children)&&(n=t(e,s.children)))}return n},p={components:{ARow:l["a"],ACol:c["b"],AModal:o["a"],TaskContent:s["a"]},name:"taskSort",methods:{hideModal:function(){var t=this;Object(i["a"])({type:1,classId:this.selectedKeys}).then((function(e){t.$message.success(e),t.form.setFieldsValue({className:"",parentClassId:""}),t.formCopy.setFieldsValue({parentClassId:""}),t.getData()})),this.visible=!1},removeAuth:function(){this.visible=!0},addAuth:function(){var t=this;this.classDeal=!0,this.addRelatOrgId=this.selectedKeys,this.type=0,this.formCopy.setFieldsValue({countId:"",parentClassId:this.getOrgName(this.selectedKeys)});var e=this.form.setFieldsValue;this.$nextTick((function(){e({className:"",parentClassId:t.getOrgName(t.selectedKeys)})}))},onSelect:function(t){this.Count?this.selectedKeysCopy=t[0]:this.selectedKeys=t[0]},getOrgId:function(t){for(var e,a=0;a<u.length;a++)t==u[a].title&&(e=u[a].key);return e},submitOrg:function(){var t=this;this.form.validateFields((function(e,a){if(!e){var n={className:a.className,parentClassId:t.type&&0!=t.type?t.relatOrgId1:t.addRelatOrgId,type:t.type?t.type:0,classId:t.type&&0!=t.type?t.getOrgId(t.oldOrgName):""};Object(i["a"])(n).then((function(e){t.$message.success(e),t.form.setFieldsValue({className:"",parentClassId:""}),t.getData(),t.classDeal=!1}))}}))},onExpandCopy:function(t){this.expandedKeysCopy=t,this.autoExpandParentCopy=!1},handleConfirm:function(){this.formCopy.setFieldsValue({parentClassId:this.getOrgName(this.selectedKeysCopy)}),this.form.setFieldsValue({parentClassId:this.getOrgName(this.selectedKeys)}),this.organizeVisible=!1,this.selectedKeys&&(this.deleteLable=!0),this.selectedKeysCopy&&(this.deleteLableCopy=!0)},handleCancel:function(){this.organizeVisible=!1},addOrganize:function(){this.Count=!1,this.organizeVisible=!0},getNextOrg:function(t,e,a){for(var n=0;n<t.length;n++){var r=t[n];r.key==e?a.push(r.parent):r.children&&this.getNextOrg(r.children,e,a)}return this.getOrgName(a[0])},getOrgName:function(t){for(var e,a=0;a<u.length;a++)t==u[a].key&&(e=u[a].title);return e},editAuth:function(t){var e=this;this.type=2;var a=[];this.oldOrgName=this.getOrgName(this.selectedKeys),this.relatOrgId1=d(this.selectedKeys,this.treeData),this.classDeal=!0;var n=this.form.setFieldsValue;this.$nextTick((function(){n({className:e.getOrgName(e.selectedKeys),parentClassId:e.getNextOrg(e.treeData,e.selectedKeys,a)})}))},getArray:function(t,e){for(var a=0;a<t.length;a++)e.push({className:t[a].className,classId:t[a].classId}),t[a].child&&t[a].child.result&&this.getArray(t[a].child.result,e);return e},searchMenu:function(t){for(var e=0;e<t.length;e++)t[e].title=t[e].className,t[e].key=t[e].classId,t[e].scopedSlots={title:"title"},t[e].parent=t.parentId,t[e].child&&t[e].child.result&&(t[e].children=t[e].child.result,t[e].children.parentId=t[e].classId,this.searchMenu(t[e].child.result))},onChange:function(t){var e=this,a=t.target.value,n=u.map((function(t){return t.title.indexOf(a)>-1?d(t.key,e.treeData):null})).filter((function(t,e,a){return t&&a.indexOf(t)===e}));Object.assign(this,{expandedKeys:n,searchValue:a,autoExpandParent:!0})},getData:function(){var t=this;Object(i["b"])({taskId:7}).then((function(e){t.treeData=Array.of(e),t.searchMenu(Array.of(e));var a=function t(e){for(var a=0;a<e.length;a++){var n=e[a],r=n.key;u.push({key:r,title:n.title}),n.children&&t(n.children,n.key)}};a(Array.of(e))}))},onExpand:function(t){this.expandedKeys=t,this.autoExpandParent=!1}},mounted:function(){this.getData()},data:function(){return{relatOrgId1:"",addRelatOrgId:"",oldOrgName:"",treeDatas:[],deleteLableCopy:!1,deleteLable:!1,autoExpandParentCopy:!0,expandedKeysCopy:[],formItemLayout:{labelCol:{span:6},wrapperCol:{span:16}},type:"",organizeVisible:!1,classDeal:!1,Count:"",form:this.$form.createForm(this),formCopy:this.$form.createForm(this),selectedKeys:[],selectedKeysCopy:[],searchValue:"",expandedKeys:[],autoExpandParent:!0,treeData:[],createType:"",visible:!1}}},f=p,h=(a("4509"),a("5950"),a("4e82")),m=Object(h["a"])(f,n,r,!1,null,"3d25a881",null);e["default"]=m.exports},c159:function(t,e,a){},d9c4:function(t,e,a){"use strict";a.d(e,"g",(function(){return r})),a.d(e,"f",(function(){return s})),a.d(e,"d",(function(){return i})),a.d(e,"a",(function(){return o})),a.d(e,"c",(function(){return c})),a.d(e,"b",(function(){return l})),a.d(e,"e",(function(){return u})),a.d(e,"h",(function(){return d}));var n=a("0c6d");function r(t){return n["a"].fetchG({url:"/teamwork-api/project/queryTaskByIterationId",data:t})}function s(t){return n["a"].fetchG({url:"/teamwork-api/project/queryAllTaskByIterationId",data:t})}function i(t){return n["a"].fetchP({url:"/teamwork-api/project/editTask",data:t})}function o(t){return n["a"].fetchP({url:"/teamwork-api/project/addChildTask",data:t})}function c(t){return n["a"].fetchG({url:"/teamwork-api/project/deleteTask",data:t})}function l(t){return n["a"].fetchP({url:"/teamwork-api/project/addInterationTask",data:t})}function u(t){return n["a"].fetchG({url:"/teamwork-api/project/queryMemberByIteId",data:t})}function d(t){return n["a"].fetchG({url:"/teamwork-api/project/updateTaskIter",data:t})}},f8a2:function(t,e,a){"use strict";a.d(e,"a",(function(){return r})),a.d(e,"b",(function(){return s})),a.d(e,"d",(function(){return i})),a.d(e,"c",(function(){return o}));var n=a("0c6d");function r(t){return n["a"].fetchP({url:"/teamwork-api/project/addInteraitonInfo",data:t})}function s(t){return n["a"].fetchG({url:"/teamwork-api/project/getIterInfoByProjectId",data:t})}function i(t){return n["a"].fetchG({url:"/teamwork-api/project/findPreciseInterationName",data:t})}function o(t){return n["a"].fetchG({url:"/teamwork-api/project/getTaskClassification",data:t})}}}]);