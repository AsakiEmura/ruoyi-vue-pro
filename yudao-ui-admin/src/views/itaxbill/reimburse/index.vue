<template>
  <div class="app-container">

    <!-- 搜索工作栏 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="报销单名称" prop="reimburseName">
        <el-input v-model="queryParams.reimburseName" placeholder="请输入报销单名称" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable size="small">
          <el-option label="请选择字典生成" value="" />
        </el-select>
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
                   v-hasPermi="['itaxbill:reimburse:create']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" :loading="exportLoading"
                   v-hasPermi="['itaxbill:reimburse:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 列表 -->
    <el-table v-loading="loading" :data="list">
      <el-table-column label="报销单名称" align="center" prop="reimburseName" />
      <el-table-column label="报销单金额" align="center" prop="price" />
      <el-table-column label="报销单总金额" align="center" prop="priceAll" />
      <el-table-column label="状态" align="center" prop="status" />
      <el-table-column label="报销时间" align="center" prop="reimburseTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.reimburseTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                     v-hasPermi="['itaxbill:reimburse:update']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['itaxbill:reimburse:delete']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNo" :limit.sync="queryParams.pageSize"
                @pagination="getList"/>

    <!-- 对话框(添加 / 修改) -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="发票id" prop="invoiceId">
          <el-input v-model="form.invoiceId" placeholder="请输入发票id" />
        </el-form-item>
        <el-form-item label="报销单名称" prop="reimburseName">
          <el-input v-model="form.reimburseName" placeholder="请输入报销单名称" />
        </el-form-item>
        <el-form-item label="报销单金额" prop="price">
          <el-input v-model="form.price" placeholder="请输入报销单金额" />
        </el-form-item>
        <el-form-item label="报销单总金额" prop="priceAll">
          <el-input v-model="form.priceAll" placeholder="请输入报销单总金额" />
        </el-form-item>
        <el-form-item label="发票张数" prop="invoiceNumber">
          <el-input v-model="form.invoiceNumber" placeholder="请输入发票张数" />
        </el-form-item>
        <el-form-item label="发票总张数" prop="invoiceNumberAll">
          <el-input v-model="form.invoiceNumberAll" placeholder="请输入发票总张数" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio label="1">请选择字典生成</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="用户id" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入用户id" />
        </el-form-item>
        <el-form-item label="报销时间" prop="reimburseTime">
          <el-date-picker clearable v-model="form.reimburseTime" type="date" value-format="yyyy-MM-dd" placeholder="选择报销时间" />
        </el-form-item>
        <el-form-item label="创建者" prop="creator">
          <el-input v-model="form.creator" placeholder="请输入创建者" />
        </el-form-item>
        <el-form-item label="创建时间" prop="createTime">
          <el-date-picker clearable v-model="form.createTime" type="date" value-format="yyyy-MM-dd" placeholder="选择创建时间" />
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
import { createReimburse, updateReimburse, deleteReimburse, getReimburse, getReimbursePage, exportReimburseExcel } from "@/api/itaxbill/reimburse";

export default {
  name: "Reimburse",
  components: {
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
      // 发票报销信息列表
      list: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      dateRangeCreateTime: [],
      // 查询参数
      queryParams: {
        pageNo: 1,
        pageSize: 10,
        reimburseName: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        invoiceId: [{ required: true, message: "发票id不能为空", trigger: "blur" }],
        reimburseName: [{ required: true, message: "报销单名称不能为空", trigger: "blur" }],
        price: [{ required: true, message: "报销单金额不能为空", trigger: "blur" }],
        priceAll: [{ required: true, message: "报销单总金额不能为空", trigger: "blur" }],
        invoiceNumber: [{ required: true, message: "发票张数不能为空", trigger: "blur" }],
        invoiceNumberAll: [{ required: true, message: "发票总张数不能为空", trigger: "blur" }],
        status: [{ required: true, message: "状态不能为空", trigger: "blur" }],
        userId: [{ required: true, message: "用户id不能为空", trigger: "blur" }],
        reimburseTime: [{ required: true, message: "报销时间不能为空", trigger: "blur" }],
        createTime: [{ required: true, message: "创建时间不能为空", trigger: "blur" }],
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
      this.addBeginAndEndTime(params, this.dateRangeCreateTime, 'createTime');
      // 执行查询
      getReimbursePage(params).then(response => {
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
        id: undefined,
        invoiceId: undefined,
        reimburseName: undefined,
        price: undefined,
        priceAll: undefined,
        subject: undefined,
        invoiceNumber: undefined,
        invoiceNumberAll: undefined,
        status: undefined,
        userId: undefined,
        reimburseTime: undefined,
        creator: undefined,
        createTime: undefined,
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
      this.dateRangeCreateTime = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加发票报销信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id;
      getReimburse(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改发票报销信息";
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
          updateReimburse(this.form).then(response => {
            this.$modal.msgSuccess("修改成功");
            this.open = false;
            this.getList();
          });
          return;
        }
        // 添加的提交
        createReimburse(this.form).then(response => {
          this.$modal.msgSuccess("新增成功");
          this.open = false;
          this.getList();
        });
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const id = row.id;
      this.$modal.confirm('是否确认删除发票报销信息编号为"' + id + '"的数据项?').then(function() {
          return deleteReimburse(id);
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
      this.addBeginAndEndTime(params, this.dateRangeCreateTime, 'createTime');
      // 执行导出
      this.$modal.confirm('是否确认导出所有发票报销信息数据项?').then(() => {
          this.exportLoading = true;
          return exportReimburseExcel(params);
        }).then(response => {
          this.$download.excel(response, '${table.classComment}.xls');
          this.exportLoading = false;
        }).catch(() => {});
    }
  }
};
</script>
