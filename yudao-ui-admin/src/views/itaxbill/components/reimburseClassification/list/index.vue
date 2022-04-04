<!--
 * @Descripttion:
 * @Author: zhengminjie
 * @Date: 2021-04-06 14:33:43
 * @LastEditors: zhengminjie
 * @LastEditTime: 2021-04-07 14:08:14
-->
<template>
  <el-dialog :title="titleTwo" :visible.sync="openedTwo" @open="open()" :before-close="cancel" width="1200px" destroy-on-close>
    <el-form ref="invoiceForm" :model="invoiceForm" label-width="auto" :inline="true">
      <el-form-item label="选择录入发票的日期" style="width: 38%">
        <el-date-picker @change="changeDate"
          v-model="dateRange"
          size="small"
          style="width: 220px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="选择报销公司的抬头" prop="companyName" style="width: 38%">
        <el-input 
          v-model="invoiceForm.companyName"
          :disabled="true"
          style="width: 220px"
          size="small"
        ></el-input>
      </el-form-item>
      <el-form-item label="金额" prop="price" label-width="100px" style="width: 20%">
        <el-input
          type="text"
          v-model="invoiceForm.price"
          :disabled="true"
          style="width:100px"
        ></el-input>
      </el-form-item>
    </el-form>

    <el-tabs v-model="category" @tab-click="handleClick">
      <el-tab-pane 
        v-for="item in selectionTabs" 
        :key="item.name"
        :label="item.label" 
        :name="item.name" 
        :disabled="item.subjectTabs"
        ></el-tab-pane>
    </el-tabs>

    <el-table v-loading="loading" ref="table" :data="unReimburseListInfo.vat" v-if="category=='vat'" @selection-change="handleSelectionChange">
      <el-table-column	type="selection"  width="55" align="center" />

      <el-table-column  prop="invoiceNumber"	label="发票号码" align="center" :show-overflow-tooltip="true"/>
      
      <el-table-column	prop="price"	label="金额" align="center" :show-overflow-tooltip="true" />
      <el-table-column	prop="event"	label="事由" align="center" :show-overflow-tooltip="true" />
      <el-table-column  prop="company"	label="购买方" align="center" :show-overflow-tooltip="true"/>
      <el-table-column	prop="date"  label="开票时间" align="center" />
      <el-table-column	prop="createDate"  label="录入日期" align="center" />
    </el-table>

    <el-table v-loading="loading" ref="table" :data="unReimburseListInfo.toll" v-else-if="category=='toll'" @selection-change="handleSelectionChange">
      <el-table-column	type="selection"  width="55" align="center" />

      <el-table-column	prop="invoiceNumber"  label="发票号码" align="center" :show-overflow-tooltip="true" />
      
      <el-table-column	prop="enter"  label="入口位置" align="center" :show-overflow-tooltip="true" />
      <el-table-column	prop="exit"  label="出口位置" align="center" :show-overflow-tooltip="true"/>
      <el-table-column	prop="carModel"  label="车型" align="center" :show-overflow-tooltip="true"/>

      <el-table-column	prop="price"	label="金额" align="center" :show-overflow-tooltip="true" />
      <el-table-column	prop="event"	label="事由" align="center" :show-overflow-tooltip="true" />
      <el-table-column  prop="company"	label="购买方" align="center" :show-overflow-tooltip="true"/>
      <el-table-column	prop="date"  label="开票时间" align="center" />
      <el-table-column	prop="createDate"  label="录入日期" align="center" />
    </el-table>

    <el-table v-loading="loading" ref="table" :data="unReimburseListInfo.taxi" v-else-if="category=='taxi'" @selection-change="handleSelectionChange">
      <el-table-column	type="selection"  width="55" align="center" />

      <el-table-column	prop="invoiceNumber"  label="发票号码" width="180" align="center" :show-overflow-tooltip="true" />
      <el-table-column	prop="plateNumber"  label="车牌号码" width="180" align="center" :show-overflow-tooltip="true" />

      <el-table-column	prop="price"	label="金额" align="center" :show-overflow-tooltip="true" />
      <el-table-column	prop="event"	label="事由" align="center" :show-overflow-tooltip="true" />
      <el-table-column  prop="company"	label="购买方" align="center" :show-overflow-tooltip="true"/>
      <el-table-column	prop="date"  label="开票时间" align="center" />
      <el-table-column	prop="createDate"  label="录入日期" align="center" />
    </el-table>

    <el-table v-loading="loading" ref="table" :data="unReimburseListInfo.hsr" v-else-if="category=='hsr'" @selection-change="handleSelectionChange">
      <el-table-column	type="selection"  width="55" align="center" />
      
      <el-table-column	prop="startPlace"  label="起始地" width="180" align="center" :show-overflow-tooltip="true"   />
      <el-table-column	prop="endPlace"  label="到达地" width="180" align="center" :show-overflow-tooltip="true"   />
      <el-table-column	prop="ticketnum"  label="票号" width="180" align="center" :show-overflow-tooltip="true"   />

      <el-table-column	prop="price"	label="金额" align="center" :show-overflow-tooltip="true" />
      <el-table-column	prop="event"	label="事由" align="center" :show-overflow-tooltip="true" />
      <el-table-column  prop="company"	label="购买方" align="center" :show-overflow-tooltip="true"/>
      <el-table-column	prop="date"  label="开票时间" align="center" />
      <el-table-column	prop="createDate"  label="录入日期" align="center" />
    </el-table >

    <el-table v-loading="loading" ref="table" :data="unReimburseListInfo.park" v-else-if="category=='park'" @selection-change="handleSelectionChange">
      <el-table-column	type="selection"  width="55" align="center" />
       
      <el-table-column	prop="parkPlace"  label="停车地点" width="180" align="center" :show-overflow-tooltip="true" />

      <el-table-column	prop="price"	label="金额" align="center" :show-overflow-tooltip="true" />
      <el-table-column	prop="event"	label="事由" align="center" :show-overflow-tooltip="true" />
      <el-table-column  prop="company"	label="购买方" align="center" :show-overflow-tooltip="true"/>
      <el-table-column	prop="date"  label="开票时间" align="center" />
      <el-table-column	prop="createDate"  label="录入日期" align="center" />
    </el-table>

    <el-table v-loading="loading" ref="table" :data="unReimburseListInfo.subway" v-else-if="category=='subway'" @selection-change="handleSelectionChange">
      <el-table-column	type="selection"  width="55" align="center" />
       
      <el-table-column	prop="invoiceCode"  label="发票代码" width="180" align="center" :show-overflow-tooltip="true" />
      <el-table-column	prop="invoiceNumber"  label="发票号码" width="180" align="center" :show-overflow-tooltip="true" />

      <el-table-column	prop="price"	label="金额" align="center" :show-overflow-tooltip="true" />
      <el-table-column	prop="event"	label="事由" align="center" :show-overflow-tooltip="true" />
      <el-table-column  prop="company"	label="购买方" align="center" :show-overflow-tooltip="true"/>
      <el-table-column	prop="date"  label="开票时间" align="center" />
      <el-table-column	prop="createDate"  label="录入日期" align="center" />
    </el-table>

    <el-table v-loading="loading" ref="table" :data="unReimburseListInfo.cheer" v-else-if="category=='cheer'" @selection-change="handleSelectionChange">
      <el-table-column	type="selection"  width="55" align="center" />
       
      <el-table-column	prop="invoiceCode"	label="发票代码" align="center" :show-overflow-tooltip="true"  />
      <el-table-column  prop="invoiceNumber"	label="发票号码" align="center" :show-overflow-tooltip="true"/>
      
      <el-table-column	prop="price"	label="金额" align="center" :show-overflow-tooltip="true" />
      <el-table-column	prop="event"	label="事由" align="center" :show-overflow-tooltip="true" />
      <el-table-column  prop="company"	label="购买方" align="center" :show-overflow-tooltip="true"/>
      <el-table-column	prop="date"  label="开票时间" align="center" />
      <el-table-column	prop="createDate"  label="录入日期" align="center" />
    </el-table>

    <el-table v-loading="loading" ref="table" :data="unReimburseListInfo.other" v-else-if="category=='other'" @selection-change="handleSelectionChange">
      <el-table-column	type="selection"  width="55" align="center" />
      
      <el-table-column  prop="merchantName"	label="商户名"  width="150" align="center" :show-overflow-tooltip="true" />
      <el-table-column	prop="merchantNumber"	label="商户编号"  width="150" align="center" :show-overflow-tooltip="true"  />
      <el-table-column  prop="transactionType"	label="交易类型"  width="150" align="center" :show-overflow-tooltip="true"  />
      
      <el-table-column	prop="price"	label="金额" align="center" :show-overflow-tooltip="true" />
      <el-table-column	prop="event"	label="事由" align="center" :show-overflow-tooltip="true" />
      <el-table-column  prop="company"	label="购买方" align="center" :show-overflow-tooltip="true"/>
      <el-table-column	prop="date"  label="开票时间" align="center" />
      <el-table-column	prop="createDate"  label="录入日期" align="center" />
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="invoiceForm.pageNum"
      :limit.sync="invoiceForm.pageSize"
    />


    <div slot="footer" style="text-align:center">
      <el-button type="primary" @click="submitForm" plain>确认</el-button>
      <el-button @click="cancel">取消</el-button>
    </div>
  </el-dialog>
</template>



<script>
import { listInvoice} from "@/api/itaxbill/reimburse";

export default {
  name:"ReimburseListClass",
  props:{
    openedTwo:{
      type: Boolean,
      default: false,
    },
    titleTwo:{
      type: String,
      default:"",
    },
    subjectName:{
      type: String,
      default:"",
    },
    deleteInvoiceId:{
      type:Array,
      default:[]
    },
    selectInvoiceId:{
      type:Array,
      default:[]
    },
    reimburseId:{
      type: String,
      default:"",
    },
    payableValue:{
      type:Array,
      default:[]
    }
  },
  data() {
    return {
      // 遮罩层
      loading:false,
      // 发票类别
      category:'',
      // 时间范围
      dateRange:[],
      // 总条数
      total: 0,
      // 查询参数
      invoiceForm: {
        payableId:'',
        price:0,
        titleNum:0,
        pageNum: 1,
        pageSize: 10,
        beginTime:'',
        endTime:'',
        account: undefined,
        reimburseNumber:0,
      },
      disabledSubject: [],
      // 标签页内容
      selectionTabs:[{
        label:"增值税票",
        name:"vat",
        subjectTabs:false
      },{
        label:"过路费",
        name:"toll",
        subjectTabs:false
      },{
        label:"出租车票",
        name:"taxi",
        subjectTabs:false
      },{
        label:"高铁票",
        name:"hsr",
        subjectTabs:false
      },{
        label:"停车票",
        name:"park",
        subjectTabs:false
      },{
        label:"地铁票",
        name:"subway",
        subjectTabs:false
      },{
        label:"油票",
        name:"cheer",
        subjectTabs:false
      },{
        label:"其他",
        name:"other",
        subjectTabs:false
      }],
      // 表格未报销列表
      unReimburseListInfo:{},
      unReimburseData:{},
    };
  },
  watch: {
    // 如果 `disabledSubject` 发生改变，这个函数就会运行
    disabledSubject: function (newValue, oldValue) {
      this.selectionTabs.forEach(element => {
        if (newValue.includes(element.name)) {
          element.subjectTabs = true;
        } else {
          element.subjectTabs = false;
        }
      });
      this.category = this.selectionTabs.find(item => !item.subjectTabs).name;
    },
    /** 多选框被选中的数据还原 即不会被清空 */
    category: function (newValue, oldValue) {
      if(newValue && newValue !== '0'){
          this.unReimburseListInfo[newValue].filter(item => item.selected).forEach(item => {
          this.$nextTick(()=>{
            this.$refs.table.toggleRowSelection(item);
          })
        })
      }
    }
  },
  methods: {
    /*打开对话框前的初始化 */
    open(){
      this.category = '';
     // this.$nextTick(()=>{
        console.log(this.subjectName);
        this.invoiceForm.payableId = this.payableValue[0];
        this.invoiceForm.companyName = this.payableValue[1];
        this.invoiceForm.reimburseNumber = this.reimburseId;
        let price = 0;
        if(this.dateRange!=null){
          this.invoiceForm.endTime = this.dateRange[1];
          this.invoiceForm.beginTime = this.dateRange[0];
        }else{
          this.invoiceForm.endTime = null;
          this.invoiceForm.beginTime = null;
        }
        /* 获取发票数据 */
        listInvoice(this.addDateRange(this.invoiceForm, this.dateRange)).then(
          response => {
            this.loading = true;
            Object.keys(response).forEach(key => {
              let data =response[key].rows.filter(item=>{
                if(!this.deleteInvoiceId.includes(item.invoiceId)){
                  return item;
                }
              });
              data = data.map(item=>{
                if(this.selectInvoiceId.includes(item.invoiceId)){
                  item.selected = true;
                  price += item.price;
                }else{
                  item.selected = false;
                }
                return item;
              })
              this.$set(this.unReimburseListInfo, key, data);//强制更新
            })
            switch (this.subjectName){
              case 'travel':
                this.disabledSubject = ["cheer", "other"];
                break;
              case 'privateCar':
                this.disabledSubject = ["vat", "other"];
                break;
              case 'resource':
                this.disabledSubject = ["vat", "taxi","hsr","park","subway","cheer"];
                break;
              case 'labor':
                this.disabledSubject = ["toll", "taxi","hsr","park","subway","cheer"];
                break;
              case 'management':
                this.disabledSubject = ["toll", "taxi","hsr","park","subway","cheer"];
                break;
              case 'administration':
                this.disabledSubject = ["toll", "taxi","hsr","park","subway","cheer"];
                break;
              case 'other':
                this.disabledSubject = [];
                break;
            }
            if(this.titleTwo == '修改报销发票'){
              this.unReimburseData ={};
              console.log(this.unReimburseListInfo);
              this.invoiceForm.price = price;
              this.loading = false;
            }
            else if(this.titleTwo == '新增报销发票'){
              this.unReimburseData ={};
              this.invoiceForm.price = 0;
              this.loading = false;
            }
          }
        )
     // })
    },
    /** 日期选中时 */
    changeDate(){
      this.open();
    },
    /** 点击标签时 切换category */
    handleClick(tab) {
      this.category = tab.name;
    },
    cancel(){
       console.log("before cancel",this.invoiceForm);
      //清除表单内容
      if(this.titleTwo=="新增报销发票"){
        this.invoiceForm={};
      }
      this.$emit("closeReimburseList",false);
    },
    handleSelectionChange(selection){
      console.log(selection);
      let price = 0;
      let titleNum = 0;
      /** 将被选中的发票的selected属性设置为true */
      this.unReimburseListInfo[this.category].forEach(item => {
        if (selection.find(it => it.invoiceId === item.invoiceId)) {
          item.selected = true;
        } else {
          item.selected = false;
        }
      })
      Object.keys(this.unReimburseListInfo).forEach(key=>{
        this.unReimburseListInfo[key].filter(item=>{
          if(item.selected){
            price +=item.price;
            titleNum++;
          }
        })
      })
      this.invoiceForm.price = price.toFixed(2);
      this.invoiceForm.titleNum = titleNum;
      this.$forceUpdate();
    },
    //发票ID(数组)+总金额+票数给前一个弹窗
    submitForm(){
      Object.keys(this.unReimburseListInfo).forEach(key=>{
        let data = this.unReimburseListInfo[key].filter(item=>{
          if(item.selected){
            return item;
          }
        }).map(element=>element.invoiceId);
        if(data.length){
          this.unReimburseData[key] = data;
        }
      })
      this.unReimburseData.priceAll = this.invoiceForm.price;
      this.unReimburseData.invoiceNumberAll = this.invoiceForm.titleNum;
      if(this.titleTwo=="新增报销发票"){
        this.$emit("TableRowData",this.unReimburseData);
        this.$emit("addRow",true);
      }
      else if(this.titleTwo=="修改报销发票"){
        this.$emit("TableRowData",this.unReimburseData);
      }
      this.cancel();
    }
  }
}
</script>