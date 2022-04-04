<!--
 * @Descripttion:
 * @Author: zhengminjie
 * @Date: 2021-04-09 14:33:43
 * @LastEditors: zhengminjie
 * @LastEditTime: 2021-04-10 14:08:14
-->
<template>
  <div>
    <el-dialog :title="title" :visible.sync="opened" @open="initDialog()" :before-close="cancel" width="900px" destroy-on-close>
      <el-form ref="form" :model="form" label-width="auto" :inline="true">
        <el-form-item label="输入报销单名称" prop="reimburseName" >
          <el-input 
            v-model="form.reimburseName"
            :disabled="disabled"
            size="small"
            style="width:210px"
            @input="change($event)"
          ></el-input>
        </el-form-item>
        <el-form-item label="时间" prop="date">
          <el-input
            type="text"
            v-model="form.date"
            :disabled="true"
            size="small"
          ></el-input>
        </el-form-item>
        <el-form-item label="报销公司抬头" prop="companyName">
          <el-select v-model="form.payableId" :disabled="disabledPayable">
            <el-option
              v-for="payable in payableList"
              :key="payable.payableId"
              :label="payable.companyName"
              :value="payable.payableId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="报销总金额" prop="priceAll">
          <el-input
          v-model="form.priceAll"
          :disabled="true"
          style="width:120px"
          ></el-input>
        </el-form-item>
        <el-form-item label="报销总张数" prop="invoiceNumberAll">
          <el-input
          v-model="form.invoiceNumberAll"
          :disabled="true"
          style="width:120px"
          ></el-input>
        </el-form-item>

        <el-table v-loading="loading" :data="ReimburseFirstList" :row-class-name="TableRowClassName">
          <el-table-column prop="subject" label="报销科目" align="center" width="250%">
            <template slot-scope="scope">
              <el-select v-model="scope.row.subject" placeholder="请选择报销单科目" :disabled="disabled" style="width:100%">
                <el-option
                  v-for="item in reimburseSubjectList"
                  :key="item.subjectEnglish"
                  :label="item.subjectName"
                  :value="item.subjectEnglish"
                />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column	prop="price"	label="报销金额" align="center" :show-overflow-tooltip="true" />
          <el-table-column  prop="invoiceNumber"	label="报销张数" align="center" :show-overflow-tooltip="true" /> 
          <el-table-column  prop="status"	label="状态" align="center" :show-overflow-tooltip="true" /> 
          <el-table-column label="操作" align="center" width="150%" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-add"
                :disabled="!!scope.row.invoiceNumber"
                @click="handleAdd(scope.row)"
              >增加</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                :disabled="disabled"
                @click="handleEdit(scope.row)"
              >修改</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                :disabled="disabled"
                style="color:red"
                @click="handleDelete(scope.row,scope.$index)"
              >删除</el-button>
            </template>
          </el-table-column>  
        </el-table>

      </el-form>

      <div slot="footer" style="text-align:center">
        <el-button type="primary" @click="onSubmit" plain>确认</el-button>
        <el-button @click="cancel(false)">取消</el-button>
      </div>

      <!-- 添加角色配置对话框 -->
      <reimburse-list-class
        :openedTwo="openedTwo"
        :titleTwo="titleTwo"
        :subjectName="subjectName"
        :deleteInvoiceId ="deleteInvoiceId"
        :selectInvoiceId ="selectInvoiceId"
        :reimburseId ="reimburseId"
        :payableValue ="payableValue"
        @TableRowData="tableRowData"
        @closeReimburseList="listenCloseDialog"
        @addRow="addRow"
      />
    
    </el-dialog>
  </div>
</template>


<script>

import ReimburseListClass from "@/views/itaxbill/components/reimburseClassification/list";
import { addReimburse, updateReimburse, confirmReimburse} from "@/api/itaxbill/reimburse";
import { subjectList } from "@/api/itaxbill/subject";
import {listPayable, getDefaultPayable} from "@/api/itaxbill/payable";

export default {
  name:"ReimburseEditClass",
  components: {ReimburseListClass},
  props:{
    // 报销禁用
    disabled:{
      type:Boolean,
      default: false,
    },
    loading: {
      type: Boolean,
      default: false
    },
    // 对话框
    opened:{
      type: Boolean,
      default: false
    },
    // 弹出层标题
    title:{
      type: String,
      default: ""
    },
    // 对话框数据
    dialogData:{
      type:Array,
      default:[{}]
    }
  },
  data(){
    return {
      // 对话框
      openedTwo:false,
      // 添加科目的隐藏按钮
      showed:false,
      // 取消按钮是否要刷新表格
      refreshValue:false,
      // 表单信息
      form:{
        priceAll:0,
        date:'',
        reimburseName:'',
        invoiceNumberAll:0,
      },
      // 哪一行
      whichRow:0,
      // 报销科目名
      subjectName:"",
      // 第二个弹窗title
      titleTwo:"",
      // 抬头禁用
      disabledPayable:false,
      // 要删的发票数据(不能重复报销某一张发票)
      deleteInvoiceId:[],
      // 某一行的发票数据(修改时，记录被勾选的状态)
      selectInvoiceId:[],
      // 抬头列表
      payableList:[],
      // 传给第二个对话框的抬头和id
      payableValue:[],
      // 修改按钮操作时的报销单id
      reimburseId:"",
      // 报销日期
      reimburseDate:"",
      // 报销发票id
      invoiceId:[],
      // 报销发票的id金额
      invoiceIdPrice:[[{}]],
      // 报销列表
      ReimburseFirstList:[
        {
          invoice:{},
          subject:"",
          price:"",
          invoiceNumber:"",
          status:"",
          reimburseName:"",
          time:"",
          priceAll:0,
          invoiceNumberAll:"",
        }
      ],
      // 差旅费 市内交通充值及私家车费用 出版文献资料费 劳务费 经营业务费 管理费 其他费用
      reimburseSubjectList:[],
    }
  },
  methods:{
    // 初始化对话框
    initDialog(){
      console.log(this.title);
      subjectList().then(res=>{
        this.reimburseSubjectList = res.rows;
      })
      listPayable().then(res=>{
        this.payableList=res.rows;
        if(res.rows!==undefined){
          let that = this;
          getDefaultPayable(res.rows[0].payableId).then(element=> that.$set(this.form, "payableId", element.msg))
        }
        else{
          this.$message.error('请先添加自己的发票抬头哟！');
        }
      });
      if(this.title == "添加报销单"){
        this.$set(this.form,'date',"");
        let date = new Date();
        let year = date.getFullYear(),
        month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1,
        day = date.getDate() < 10 ? '0' +date.getDate() : date.getDate(),
        hours = date.getHours() < 10 ? '0' +date.getHours() : date.getHours(),
        minutes = date.getMinutes() < 10 ? '0' +date.getMinutes() : date.getMinutes(),
        seconds = date.getSeconds() < 10 ? '0' +date.getSeconds() : date.getSeconds();
        this.form.date = year+"-"+month+"-"+day+" "+hours+":"+minutes+":"+seconds;
        this.form.reimburseName = year+month+day+"Invoice";
        this.reimburseDate = this.form.date;
        this.disabledPayable = false;
        this.reimburseId = "";
      }
      else if(this.title == "修改报销单"){
        this.ReimburseFirstList = this.dialogData;
        this.ReimburseFirstList.forEach(element=>{
          Object.keys(element.invoice).forEach(keys=>{
            element.invoice[keys] = element.invoice[keys].substring(0, element.invoice[keys].lastIndexOf(',')).split(",").map(Number);
          })
        })
        this.reimburseId = this.ReimburseFirstList[0].reimburseId;
        this.form.priceAll = this.ReimburseFirstList[0].priceAll;
        this.form.reimburseName = this.ReimburseFirstList[0].reimburseName;
        this.form.date = this.ReimburseFirstList[0].createTime;
        this.form.invoiceNumberAll = this.ReimburseFirstList[0].invoiceNumberAll;
        this.reimburseDate = this.ReimburseFirstList[0].createTime;
        this.addRow(true);
      }
      else if(this.title == "报销单"){
        this.ReimburseFirstList = this.dialogData;
        this.ReimburseFirstList.forEach(element=>{
          Object.keys(element.invoice).forEach(keys=>{
            element.invoice[keys] = element.invoice[keys].substring(0, element.invoice[keys].lastIndexOf(',')).split(",").map(Number);
          })
        })
        this.reimburseId = this.ReimburseFirstList[0].reimburseId;
        this.form.priceAll = this.ReimburseFirstList[0].priceAll;
        this.form.reimburseName = this.ReimburseFirstList[0].reimburseName;
        this.form.date = this.ReimburseFirstList[0].reimburseTime||this.ReimburseFirstList[0].createTime;
        this.form.invoiceNumberAll = this.ReimburseFirstList[0].invoiceNumberAll;
        this.reimburseDate = this.form.date;
        this.disabledPayable = true;
      }
      console.log(this.form);
    },
    // 获取表格的行号
    TableRowClassName({ row, rowIndex }){
      row.row_index = rowIndex;
    },
    // 输入框不能输入的解决
    change(e){
      this.$forceUpdate();
    },
    cancel(refreshValue){
       console.log("before cancel",this.form);
      //清除表单内容
      this.form={};
      this.ReimburseFirstList=[{
          invoice:{},
          subject:"",
          price:"",
          invoiceNumber:"",
          status:"",
          reimburseName:"",
          time:"",
          priceAll:0,
          invoiceNumberAll:"",
        }];
      this.$emit("closeReimburseInfo",refreshValue);
    },
    addRow(row){
      console.log(row);
      if(row){
        var list = {
          invoice:{},
          price:"",
          invoiceNumber:"",
          status:"",
          reimburseName:"",
          time:"",
          priceAll:0,
          invoiceNumberAll:"",
        }
        this.ReimburseFirstList.push(list);
        this.disabledPayable = true;
      }
    },
    subjectAdd(){
      this.showed = true;
    },
    tableRowData(row){
      this.invoiceIdPrice = row;
      console.log(this.invoiceIdPrice);
      console.log(this.whichRow);
      let price = 0;
      let number = 0;
      //深拷贝 切断值的联系
      this.ReimburseFirstList[this.whichRow].invoice = JSON.parse(JSON.stringify(this.invoiceIdPrice));
      this.ReimburseFirstList[this.whichRow].price = this.invoiceIdPrice.priceAll;
      this.ReimburseFirstList[this.whichRow].invoiceNumber = this.invoiceIdPrice.invoiceNumberAll;
      this.ReimburseFirstList[this.whichRow].status = "录入";
      console.log(this.ReimburseFirstList);
      this.ReimburseFirstList.forEach(item=>{
        price += Number(item.price);
        number +=item.invoiceNumber;
      })
      this.form.priceAll = price;
      this.form.invoiceNumberAll = number;
    },
    // 修改按钮
    handleEdit(row){
      this.whichRow = row.row_index;
      console.log(row.subject);
      this.subjectName = row.subject;
      console.log(this.ReimburseFirstList);
      this.deleteInvoiceId = [];
      this.selectInvoiceId = [];
      for(let i=0;i<this.ReimburseFirstList.length -1;i++){
        if(this.whichRow!=i){
          let invoice = this.ReimburseFirstList[i].invoice;
          let type = Object.keys(invoice).filter(item => item && item !== "priceAll" && item !== "invoiceNumber");
          type.forEach(item => {
            this.deleteInvoiceId = this.deleteInvoiceId.concat(invoice[item]);
          })
        }
        else{
          let invoice = this.ReimburseFirstList[i].invoice;
          let type = Object.keys(invoice).filter(item => item && item !== "priceAll" && item !== "invoiceNumber");
          type.forEach(item => {
            this.selectInvoiceId = this.selectInvoiceId.concat(invoice[item]);
          })
        }
      }
      if(row.subject != "" && row.subject!=undefined){
        this.titleTwo = "修改报销发票";
        this.payableValue = [];
        this.payableValue.push(this.form.payableId);
        let obj = {};
        obj = this.payableList.find((item)=>{
            return item.payableId === this.form.payableId;
        });
        this.payableValue.push(obj.companyName);
        this.openedTwo = true;
      }
      else{
        this.$message({
          message: '请选择报销科目哟！',
          type: 'warning'
        });
      }
    },
    // 删除表格某行数据
    handleDelete(row,index){
      console.log(this.ReimburseFirstList);
      console.log(index);
      this.form.priceAll -= this.ReimburseFirstList[index].price;
      this.form.invoiceNumberAll -= this.ReimburseFirstList[index].invoiceNumber;
      this.ReimburseFirstList.splice(index,1);
    },
    // 打开第二个对话框
    handleAdd(row){
      this.whichRow = row.row_index;
      console.log(row.subject);
      this.subjectName = row.subject;
      console.log(this.ReimburseFirstList);
      this.deleteInvoiceId = [];
      for(let i=0;i<this.ReimburseFirstList.length -1;i++){
        if(this.whichRow!=i){
          let invoice = this.ReimburseFirstList[i].invoice;
          let type = Object.keys(invoice).filter(item => item && item !== "priceAll" && item !== "invoiceNumber");
          type.forEach(item => {
            this.deleteInvoiceId = this.deleteInvoiceId.concat(invoice[item]);
          })
        }
      }
      if(row.subject != "" && row.subject != undefined){
        this.titleTwo = "新增报销发票";
        this.selectInvoiceId = [];
        this.payableValue = [];
        this.payableValue.push(this.form.payableId);
        let obj = {};
        obj = this.payableList.find((item)=>{
            return item.payableId === this.form.payableId;
        });
        this.payableValue.push(obj.companyName);
        this.openedTwo = true;
      }
      else{
        this.$message({
          message: '请选择报销科目哟！',
          type: 'warning'
        });
      }
    },
    // 关闭第二个对话框
    listenCloseDialog(){
      this.openedTwo = false;
    },
    // 提交报销
    onSubmit(){
      if(this.ReimburseFirstList[0].status == "已报销"||this.ReimburseFirstList[0].status == "在报销"){
        this.cancel(true);
      }
      else{
        if(this.ReimburseFirstList[0].price==""||this.ReimburseFirstList[0].invoiceNumber==""){
          this.$message({
            message:"请选择需要报销的发票哟！",
            type:"warning"
          })
        }
        else{
          let reimburseArray = [];
          this.invoiceId = [];
          this.ReimburseFirstList.forEach(item=>{
            if(item.price!=""){
              let reimburse = {};
              reimburse.reimburseName = this.form.reimburseName;
              reimburse.price = item.price;
              reimburse.priceAll = this.form.priceAll;
              reimburse.subject = item.subject;
              reimburse.invoiceNumber = item.invoiceNumber;
              reimburse.invoiceNumberAll = this.form.invoiceNumberAll;
              reimburse.status = item.status;
              reimburse.createTime = this.reimburseDate;
              if(this.title == "修改报销单" || this.title == "报销单"){
                reimburse.reimburseId = this.reimburseId;
              }
              let type = Object.keys(item.invoice).filter(item => item && item !== "priceAll" && item !== "invoiceNumberAll");
              type.forEach(element => {
                this.invoiceId = this.invoiceId.concat(item.invoice[element]);
                console.log(this.invoiceId);
              })
              reimburse.invoiceId = this.invoiceId.join(",");
              reimburseArray.push(reimburse);
              this.invoiceId = [];
            }
          })
          console.log(reimburseArray);
          if(this.title == "添加报销单"){
            addReimburse(reimburseArray).then(res=>{
              console.log(res);
              this.msgSuccess("添加成功");
              this.cancel(true);
            })
          }
          else if(this.title == "修改报销单"){
            updateReimburse(reimburseArray).then(res=>{
              console.log(res);
              this.msgSuccess("修改成功");
              this.cancel(true);
            })
          }
          else if(this.title == "报销单"){
            this.$confirm('此操作将报销这些发票, 是否继续?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              //TODO 点击“报销”：相当于将报销单打包发给企业端，成功后将报销状态更改为“在报销”。
              // 每次启动的时候会从服务端获取报销单状态（退回、成功）。
              confirmReimburse(reimburseArray).then(res=>{
                this.msgSuccess("报销成功");
                this.cancel(true);
              })
            }).catch(() => {
              this.$message({
                type: 'info',
                message: '已取消报销'
              });          
            });
          }
        }
      }
    }
  }
}
</script>
