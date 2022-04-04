
<template>
  <el-dialog :title="title" :visible.sync="open" width="700px" :before-close="cancel" @open="initDialog" destroy-on-close>
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
      <div style="width:350px;float:left">
        <el-form-item v-model="form.invoiceId" v-if="false"/>
        <el-form-item label="发票抬头" prop="companyName">
          <el-select v-model="form.payableId" placeholder="请选择">
            <el-option
              v-for="payable in payableList"
              :key="payable.payableId"
              :label="payable.companyName"
              :value="payable.payableId"
            />
          </el-select>
        </el-form-item>
        <div v-if="form.category==='vat'">
          <el-form-item label="发票代码" prop="invoiceCode">
            <el-input v-model="form.invoiceCode" placeholder="请输入发票代码" />
          </el-form-item>
          <el-form-item label="发票号码" prop="invoiceNumber">
            <el-input v-model="form.invoiceNumber" placeholder="请输入发票号码" />
          </el-form-item>
        </div>
        <div v-else-if="form.category==='toll'">
          <el-form-item label="发票代码" prop="invoiceCode">
            <el-input v-model="form.invoiceCode" placeholder="请输入发票代码" />
          </el-form-item>
          <el-form-item label="发票号码" prop="invoiceNumber">
            <el-input v-model="form.invoiceNumber" placeholder="请输入发票号码" />
          </el-form-item>
          <el-form-item label="入口位置" prop="enter">
            <el-input v-model="form.enter" placeholder="请输入入口位置" />
          </el-form-item>
          <el-form-item label="出口位置" prop="exit">
            <el-input v-model="form.exit" placeholder="请输入出口位置" />
          </el-form-item>
          <el-form-item label="车型" prop="carModel">
            <el-input v-model="form.carModel" placeholder="请输入过路车型" />
          </el-form-item>
        </div>
        <div v-else-if="form.category==='taxi'">
          <el-form-item label="发票代码" prop="invoiceCode">
            <el-input v-model="form.invoiceCode" placeholder="请输入发票代码" />
          </el-form-item>
          <el-form-item label="发票号码" prop="invoiceNumber">
            <el-input v-model="form.invoiceNumber" placeholder="请输入发票号码" />
          </el-form-item>
          <el-form-item label="车牌号码" prop="plateNumber">
            <el-input v-model="form.plateNumber" placeholder="请输入车牌号码" />
          </el-form-item>
        </div>
        <div v-else-if="form.category==='hsr'">
          <el-form-item label="起始地" prop="startPlace">
            <el-input v-model="form.startPlace" placeholder="请输入起始站" />
          </el-form-item>
          <el-form-item label="到达地" prop="endPlace">
            <el-input v-model="form.endPlace" placeholder="请输入到达站" />
          </el-form-item>
          <el-form-item label="发车日期" prop="departDate">
            <el-date-picker type="date" placeholder="请输入发车日期" v-model="form.departureDate" />
          </el-form-item>
          <el-form-item label="票号" prop="ticketNum">
            <el-input v-model="form.ticketNum" placeholder="请输入票号" />
          </el-form-item>
        </div>
        <div v-else-if="form.category==='park'">
          <el-form-item label="停车地点" prop="parkPlace">
            <el-input v-model="form.parkPlace" placeholder="请输入停车地点" />
          </el-form-item>
        </div>
        <div v-else-if="form.category==='subway'">
          <el-form-item label="发票代码" prop="invoiceCode">
            <el-input v-model="form.invoiceCode" placeholder="请输入发票代码" />
          </el-form-item>
          <el-form-item label="发票号码" prop="invoiceNumber">
            <el-input v-model="form.invoiceNumber" placeholder="请输入发票号码" />
          </el-form-item>
        </div>
        <div v-else-if="form.category==='cheer'">
          <el-form-item label="发票代码" prop="invoiceCode">
            <el-input v-model="form.invoiceCode" placeholder="请输入发票代码" />
          </el-form-item>
          <el-form-item label="发票号码" prop="invoiceNumber">
            <el-input v-model="form.invoiceNumber" placeholder="请输入发票号码" />
          </el-form-item>
        </div>
        <div v-else-if="form.category==='other'">
          <el-form-item label="商户名" prop="merchantName">
            <el-input v-model="form.merchantName" placeholder="请输入商户名" />
          </el-form-item>
          <el-form-item label="商户编号" prop="merchantNumber">
            <el-input v-model="form.merchantNumber" placeholder="请输入商户编号" />
          </el-form-item>
          <el-form-item label="订单编号" prop="orderNumber">
            <el-input v-model="form.orderNumber" placeholder="请输入订单编号" />
          </el-form-item>
          <el-form-item label="交易类型" prop="transactionType">
            <el-input v-model="form.transactionType" placeholder="请输入交易类型" />
          </el-form-item>
          <el-form-item label="交易凭证号" prop="voucherNumber">
            <el-input v-model="form.voucherNumber" placeholder="请输入交易凭证号" />
          </el-form-item>
        </div>
        <el-form-item label="合计金额" prop="price">
          <el-input-number v-model="form.price" controls-position="right" :min="0" />
        </el-form-item>
        <el-form-item label="开票日期" prop="date">
          <el-date-picker type="date" placeholder="选择日期" v-model="form.date" style="width: 100%;" />
        </el-form-item>
        <el-form-item label="事由" prop="event">
          <el-input type="textarea" v-model="form.event" placeholder="请输入事由" />
        </el-form-item>
      </div>
      <div style="float:right">
        <el-form-item label="购买方税号" prop="invoiceTax">
          <el-input v-model="form.invoiceTax" placeholder="请输入购买方税号" />
        </el-form-item>
        <el-form-item label="购买方" prop="company">
          <el-input v-model="form.company" placeholder="请输入购买方公司" />
        </el-form-item>

        <el-form-item label="发票图片" prop="invoiceImgPath">
          <el-upload
            ref="upload"
            :limit="1"
            accept=".jpg, .png"
            :action="upload.url"
            :headers="upload.headers"
            :class="{styleA:upload.status == false,styleB:upload.status == true}"
            :data="paramsData"
            :on-progress="handleFileUploadProgress"
            :on-success="handleFileSuccess"
            :on-remove="handleRemove"
            :disabled="disPic"
            list-type="picture-card">
            <img width="163px" height="147px" v-if="this.form.invoiceImgPath" :src="this.form.bytes" alt="">
            <el-button v-else slot="trigger" size="small" type="primary" :loading="upload.isUploading" @click="submitUpload">点击识别发票</el-button>
            <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
          </el-upload>
        </el-form-item>

      </div>
      <div style="clear:both"></div>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm('form')">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>
<script>
import {addInvoice, getInvoice, updateInvoice} from "@/api/itaxbill/invoice";
import {listPayable, getDefaultPayable} from "@/api/itaxbill/payable";
import { getToken } from "@/utils/auth";

export default {
  name:"invoiceEditClass",
  props:{
    category: {
      type: String,
      default:"",
    },
    invoiceId: {
      type: Number,
      default:0
    },
    open:{
      type: Boolean,
      default: false,
    },
    title:{
      type: String,
      default:"",
    }
  },
  data(){
    return{
      //取消按钮是否要刷新表格
      refreshValue:false,
      //隐藏表单列
      showFormItem:false,
      //显示图片
      invoiceImgVisible:false,
      //表单参数
      form: {payableId:""},
      // 抬头列表
      payableList:[],
      //上传
      upload: {
        // 是否禁用上传
        isUploading: false,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/itaxbill/invoice/upload",
        // 上传成功
        status:false
      },
      //发票图片列表
      // 发送后台参数
      editInvoiceInfo: {
        invoiceId: this.invoiceId,
        category: this.category,
        userId: this.$store.state.user.id,
      },
      // 隐藏图片
      disPic:false,
      // 表单校验
      rules: {
        //基本的 
        price: [
          {required: true, message: "合计金额不能为空", trigger: "blur"}
        ],
        date: [
          {required: true, message: "开票日期不能为空", trigger: "blur"}
        ],
        // 增值税，过路费，出租车费，地铁票，加油票
        invoiceCode: [
          {required: true, message: "发票代码不能为空", trigger: "blur"}
        ],
        // 高铁票
        ticketNum: [
          {required: true, message: "票号不能为空", trigger: "blur"}
        ],
        // 其他
        orderNumber: [
          {required: true, message: "订单编号不能为空", trigger: "blur"}
        ]
      }
    }
  },
  computed:{
    paramsData: function () {
      let params = {
        category:this.category
      }
      return params
    }
  },
  methods:{
    //初始化对话框
    initDialog(){
      this.form.category = this.category;
      this.upload.status = false;
      listPayable().then(res=>{
        this.payableList=res.rows;
        if(this.title=="添加发票"){
          if(res.rows!==undefined){
            let that = this;
            getDefaultPayable(res.rows[0].payableId).then(element=> that.$set(this.form, "payableId", element.msg))
          }
          else{
            this.$message.error('请先添加自己的发票抬头哟！');
          }
        }
      });
      this.$forceUpdate();
      console.log(this.invoiceId);
      if(this.invoiceId!==0) {
        getInvoice(this.invoiceId).then(res=>{
          console.log(res)
          this.form=res.data;
          this.form.bytes = "data:image/jpeg;base64," + res.data.bytes;
        });
      }
    },
    //关闭对话框
    cancel(refreshValue){
      console.log("before cancel",this.form);
      //清除表单内容
      this.form={};
      this.$emit("closeInvoiceInfo",refreshValue);
    },
    //提交对话框
    submitForm(formName){
      this.$refs[formName].validate((valid) => {
        if (valid) {
          console.log("category",this.category);
          let invoiceContent={}
          switch (this.category) {
            case "vat":
              invoiceContent["code"]=this.form.invoiceCode;
              invoiceContent["number"]=this.form.invoiceNumber;
              break;
            case "toll":
              invoiceContent["code"]=this.form.invoiceCode;
              invoiceContent["number"]=this.form.invoiceNumber;
              invoiceContent["enter"]=this.form.enter;
              invoiceContent["exit"]=this.form.exit;
              invoiceContent["carModel"]=this.form.carModel;
              break;
            case "taxi":
              invoiceContent["code"]=this.form.invoiceCode;
              invoiceContent["number"]=this.form.invoiceNumber;
              invoiceContent["plateNumber"]=this.form.plateNumber;
              break;
            case "hsr":
              invoiceContent["startPlace"]=this.form.startPlace;
              invoiceContent["endPlace"]=this.form.endPlace;
              invoiceContent["departureDate"]=this.form.departureDate;
              invoiceContent["ticketNum"]=this.form.ticketNum;
              break;
            case "park":
              invoiceContent["parkPlace"]=this.form.parkPlace;
              break;
            case "subway":
              invoiceContent["code"]=this.form.invoiceCode;
              invoiceContent["number"]=this.form.invoiceNumber;
              break;
            case "cheer":
              invoiceContent["code"]=this.form.invoiceCode;
              invoiceContent["number"]=this.form.invoiceNumber;
              break;
            case "other":
              invoiceContent["merchantName"]=this.form.merchantName;
              invoiceContent["merchantNumber"]=this.form.merchantNumber;
              invoiceContent["orderNumber"]=this.form.orderNumber;
              invoiceContent["transactionType"]=this.form.transactionType;
              invoiceContent["voucherNumber"]=this.form.voucherNumber;
              break;
          }
          this.editInvoiceInfo["invoiceContent"]= JSON.stringify(invoiceContent);
          this.editInvoiceInfo["invoiceId"]=this.invoiceId;
          this.editInvoiceInfo["category"]=this.category;
          this.editInvoiceInfo["price"]= this.form.price.toFixed(2);
          this.editInvoiceInfo["date"]=this.form.date;
          this.editInvoiceInfo["payableId"]=this.form.payableId;
          this.editInvoiceInfo["invoiceImgPath"]=this.form.invoiceImgPath;
          //console.log("editInvoiceInfo",this.editInvoiceInfo);
          
          if(this.title==="添加发票"){
            addInvoice(this.editInvoiceInfo).then(res=>{
              if(res.msg!="添加发票成功"){
                this.$message.error(res.message);
              }else{
                this.$message({
                  message:res.msg,
                  type:'success'
                })
              }
              this.cancel(true);
            });
          }else if(this.title==="修改发票"){
            updateInvoice(this.editInvoiceInfo).then(res=>{
              this.$message({
                message:res.msg,
                type:'success'
              })
              this.cancel(true);
            });
          }
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    // 文件移除处理
    handleRemove(){
      this.upload.status = false;
      this.form.invoiceImgPath = "";
      this.form.invoiceCode = "";
      this.form.invoiceNumber = "";
      this.form.price = "";
      this.form.date = "";
    },
    // 文件提交处理
    submitUpload() {
      this.$refs.upload.submit();
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      console.log(response);
      this.upload.isUploading = false;
      if(response.status == "true"){
        this.form.invoiceImgPath = response.imgPath;
        if(this.category=="vat"){
          this.form.invoiceCode = response.code;
          this.form.invoiceNumber = response.number;
          this.form.price = response.total;
          this.form.date = response.date;
        }
        this.upload.status = true;
        this.$message.success('图片上传成功');
      }else{
        this.upload.status = false;
        this.$message.error('图片上传失败');
      }
    }
  }

}
</script>
<style rel="stylesheet/scss" lang="scss">
.styleA .el-upload--picture-card{
  width:80%;
}
.styleB .el-upload--picture-card{
  display:none;   
}
</style>
