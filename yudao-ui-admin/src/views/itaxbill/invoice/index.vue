<template>
  <div class="app-container">

    <!-- 搜索工作栏 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="抬头ID" prop="payableId">
        <el-input v-model="queryParams.companyName" placeholder="请输入抬头ID" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="开票日期">
        <el-date-picker v-model="dateRangeDate" style="width: 240px" value-format="yyyy-MM-dd"
                        type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" />
      </el-form-item>
      <el-form-item label="发票类型" prop="subject">
        <el-input v-model="queryParams.subject" placeholder="请输入报销科目" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="创建时间">
        <el-date-picker v-model="dateRangeCreateTime" style="width: 240px" value-format="yyyy-MM-dd"
                        type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作工具栏 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
                   v-hasPermi="['itaxbill:invoice:create']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" :loading="exportLoading"
                   v-hasPermi="['itaxbill:invoice:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 列表 -->
    <el-table v-loading="loading" :data="list">
      <el-table-column label="持有人" align="center" prop="userId" :show-overflow-tooltip="true"/>
      <el-table-column label="发票类型" align="center" prop="category" />
      <el-table-column label="抬头ID" align="center" prop="payableId" />
      <el-table-column label="购买方税号" align="center" prop="invoiceTax" />
      <el-table-column label="开票日期" align="center" prop="date" />
      <el-table-column label="合计金额" align="center" prop="price" />
      <el-table-column label="事由" align="center" prop="event" />
      <el-table-column label="购买方" align="center" prop="company" />
      <el-table-column label="录入日期" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="发票状态" align="center" prop="reimburseStatus" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                     v-hasPermi="['itaxbill:invoice:update']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['itaxbill:invoice:delete']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNo" :limit.sync="queryParams.pageSize"
                @pagination="getList"/>

    <!-- 对话框(添加 / 修改) -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户ID" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入用户ID" />
        </el-form-item>
        <el-form-item label="抬头ID" prop="payableId">
          <el-input v-model="form.payableId" placeholder="请输入抬头ID" />
        </el-form-item>
        <el-form-item label="报销单编号" prop="reimburseNumber">
          <el-input v-model="form.reimburseNumber" placeholder="请输入报销单编号" />
        </el-form-item>
        <el-form-item label="购买方税号(发票产生的公司的税号)" prop="invoiceTax">
          <el-input v-model="form.invoiceTax" placeholder="请输入购买方税号(发票产生的公司的税号)" />
        </el-form-item>
        <el-form-item label="事由" prop="event">
          <el-input v-model="form.event" placeholder="请输入事由" />
        </el-form-item>
        <el-form-item label="购买方" prop="company">
          <el-input v-model="form.company" placeholder="请输入购买方" />
        </el-form-item>
        <el-form-item label="发票分类" prop="category">
          <el-input v-model="form.category" placeholder="请输入发票分类" />
        </el-form-item>
        <el-form-item label="发票信息">
          <editor v-model="form.content" :min-height="192"/>
        </el-form-item>
        <el-form-item label="发票金额" prop="price">
          <el-input v-model="form.price" placeholder="请输入发票金额" />
        </el-form-item>
        <el-form-item label="开票日期" prop="date">
          <el-date-picker clearable v-model="form.date" type="date" value-format="yyyy-MM-dd" placeholder="选择开票日期" />
        </el-form-item>
        <el-form-item label="报销科目" prop="subject">
          <el-input v-model="form.subject" placeholder="请输入报销科目" />
        </el-form-item>
        <el-form-item label="报销状态" prop="reimburseStatus">
          <el-radio-group v-model="form.reimburseStatus">
            <el-radio label="1">请选择字典生成</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="图片地址" prop="invoiceImgPath">
          <el-input v-model="form.invoiceImgPath" placeholder="请输入图片地址" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { createInvoice, updateInvoice, deleteInvoice, getInvoice, getInvoicePage, exportInvoiceExcel } from "@/api/itaxbill/invoice";
import InvoiceListClass from "@/views/itaxbill/components/invoiceClassification/list";
import InvoiceEditClass from "@/views/itaxbill/components/invoiceClassification/edit";
import Editor from '@/components/Editor';

export default {
  name: "Invoice",
  components: {
    Editor,
    InvoiceEditClass,
    InvoiceListClass
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 导出遮罩层
      exportLoading: false,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 发票信息列表
      list: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      dateRangeDate: [],
      dateRangeCreateTime: [],
      // 查询参数
      queryParams: {
        pageNo: 1,
        pageSize: 10,
        payableId: null,
        subject: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        userId: [{ required: true, message: "用户ID不能为空", trigger: "blur" }],
        payableId: [{ required: true, message: "抬头ID不能为空", trigger: "blur" }],
        invoiceTax: [{ required: true, message: "购买方税号(发票产生的公司的税号)不能为空", trigger: "blur" }],
        event: [{ required: true, message: "事由不能为空", trigger: "blur" }],
        company: [{ required: true, message: "购买方不能为空", trigger: "blur" }],
        category: [{ required: true, message: "发票分类不能为空", trigger: "blur" }],
        content: [{ required: true, message: "发票信息不能为空", trigger: "blur" }],
        price: [{ required: true, message: "发票金额不能为空", trigger: "blur" }],
        date: [{ required: true, message: "开票日期不能为空", trigger: "blur" }],
        subject: [{ required: true, message: "报销科目不能为空", trigger: "blur" }],
        reimburseStatus: [{ required: true, message: "报销状态不能为空", trigger: "blur" }],
        invoiceImgPath: [{ required: true, message: "图片地址不能为空", trigger: "blur" }],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询列表 */
    getList() {
      this.loading = true;
      // 处理查询参数
      let params = {...this.queryParams};
      this.addBeginAndEndTime(params, this.dateRangeDate, 'date');
      this.addBeginAndEndTime(params, this.dateRangeCreateTime, 'createTime');
      // 执行查询
      getInvoicePage(params).then(response => {
        this.list = response.data.list;
        this.total = response.data.total;
        this.loading = false;
      });
    },
    /** 取消按钮 */
    cancel() {
      this.open = false;
      this.reset();
    },
    /** 表单重置 */
    reset() {
      this.form = {
        userId: undefined,
        payableId: undefined,
        reimburseNumber: undefined,
        invoiceTax: undefined,
        event: undefined,
        company: undefined,
        category: undefined,
        content: undefined,
        price: undefined,
        date: undefined,
        subject: undefined,
        reimburseStatus: undefined,
        invoiceImgPath: undefined,
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNo = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRangeDate = [];
      this.dateRangeCreateTime = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加发票信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id;
      getInvoice(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改发票信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (!valid) {
          return;
        }
        // 修改的提交
        if (this.form.id != null) {
          updateInvoice(this.form).then(response => {
            this.$modal.msgSuccess("修改成功");
            this.open = false;
            this.getList();
          });
          return;
        }
        // 添加的提交
        createInvoice(this.form).then(response => {
          this.$modal.msgSuccess("新增成功");
          this.open = false;
          this.getList();
        });
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const id = row.id;
      this.$modal.confirm('是否确认删除发票信息编号为"' + id + '"的数据项?').then(function() {
          return deleteInvoice(id);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      // 处理查询参数
      let params = {...this.queryParams};
      params.pageNo = undefined;
      params.pageSize = undefined;
      this.addBeginAndEndTime(params, this.dateRangeDate, 'date');
      this.addBeginAndEndTime(params, this.dateRangeCreateTime, 'createTime');
      // 执行导出
      this.$modal.confirm('是否确认导出所有发票信息数据项?').then(() => {
          this.exportLoading = true;
          return exportInvoiceExcel(params);
        }).then(response => {
          this.$download.excel(response, '${table.classComment}.xls');
          this.exportLoading = false;
        }).catch(() => {});
    }
  }
};
</script>
