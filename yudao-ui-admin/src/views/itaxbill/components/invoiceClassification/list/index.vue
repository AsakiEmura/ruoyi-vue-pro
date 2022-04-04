<template>
  <el-table v-loading="loading" :data="invoiceInfo" @selection-change="handleSelectionChange" key="vat-table" >
    <el-table-column	type="selection"  width="55" align="center" :selectable="selectable"/>
    <el-table-column	prop="invoiceId"  label="角色ID" align="center" v-if="elTableColumnShow" />
    <el-table-column	prop="account"  label="持有人" align="center" :show-overflow-tooltip="true" />
    <el-table-column	prop="category"	label="发票类型" align="center" v-if="columns[0].visible" :show-overflow-tooltip="true"  />
    <el-table-column	prop="companyName"	label="公司抬头" align="center" v-if="columns[1].visible" :show-overflow-tooltip="true"  />
    <el-table-column	prop="invoiceTax"	label="购买方税号" align="center" v-if="columns[2].visible" :show-overflow-tooltip="true"  />
    <el-table-column	prop="date"  label="开票日期" width="100px" v-if="columns[0].visible" align="center" />
    <el-table-column	prop="price"	label="合计金额" align="center" v-if="columns[3].visible" :show-overflow-tooltip="true" />
    <el-table-column	prop="event"	label="事由" align="center" v-if="columns[4].visible" :show-overflow-tooltip="true" />
    <el-table-column  prop="company"	label="购买方" align="center" v-if="columns[5].visible" :show-overflow-tooltip="true"/>
    <el-table-column  prop="createDate"	label="录入日期"  width="100px" align="center" v-if="columns[6].visible" />
    <el-table-column  prop="reimburseStatus" label="发票状态" width="80px" v-if="columns[7].visible" align="center" :show-overflow-tooltip="true"/>
    <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
      <template slot-scope="scope">
        <el-button
          size="mini"
          type="text"
          icon="el-icon-edit"
          :disabled ="scope.row.reimburseStatus=='已报销'||scope.row.reimburseStatus=='在报销'"
          @click="invoiceInfoToParent(scope.row)"
          v-hasPermi="['itaxbill:invoice:edit']"
        >修改</el-button>
        <el-button
          size="mini"
          type="text"
          icon="el-icon-delete"
          style="color:red"
          :disabled ="scope.row.reimburseStatus=='已报销'||scope.row.reimburseStatus=='在报销'"
          @click="deleted(scope.row)"
        >删除</el-button>
      </template>
    </el-table-column>
  </el-table>

</template>

<script>
import {delInvoice} from "@/api/itaxbill/invoice";

export default {
  name:"invoiceListClass",
  props:{
    columns:{
      type:Array,
      default:[]
    },
    category: {
      type: String,
      default:"",
    },
    invoiceInfo: {
      type: Array,
      default:[]
    },
    loading: {
      type: Boolean,
      default: true
    },
  },
  data(){
    return {
      elTableColumnShow: false,
      ids:[],
    }
  },
  methods:{
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.invoiceId);
      let priceAll = 0;
      selection.forEach(element => {
        priceAll += Number(element.price);
        console.log(priceAll);
      });
      this.$emit('ids',this.ids);
      this.$emit('priceAll',priceAll);
    },
    /** 修改按钮操作 */
    invoiceInfoToParent(row){
      console.log("row",row);
      this.$emit("InvoiceInfoEdit",row);                                                                      
    },
    /** 表格复选框已报销的禁用 */
    selectable(row){
      if(row.reimburseStatus === '录入' || row.reimburseStatus === '退回'){
        return true;
      }else{
        return false;
      }
    },
    /** 删除按钮 */
    deleted(row){
      let that = this;
      this.$confirm('此操作将永久删除该发票, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        delInvoice(row.invoiceId).then(res=>{
          if(res.msg=="删除发票成功"){
            that.$emit("refresh");
          }
        })
      }).then(() => {
        this.msgSuccess("删除成功");
      })
    },
  }
}
</script>
