<template>
  <div>
    <!-- 添加患者按钮 -->
    <el-row :gutter="20" class="mb-20" style="margin-bottom: 10px; margin-left: 10px; margin-top: 10px">
      <el-col>
        <el-button type="primary" plain icon="el-icon-plus" size="medium" @click="handleAddPatient">
          新增患者
        </el-button>
      </el-col>
    </el-row>

    <!-- 患者列表 -->
    <el-table :data="patientList" v-loading="loading" style="width: 100%" border>
      <el-table-column label="登记ID" prop="id" align="center"></el-table-column>
      <el-table-column label="患者ID" prop="userId" align="center"></el-table-column>
      <el-table-column label="器官需求" prop="organNeeded" align="center" :formatter="formatOrgan"></el-table-column>
      <el-table-column label="血型" prop="bloodType" align="center" :formatter="formatBlood"></el-table-column>
      <el-table-column label="病情描述" prop="medicalCondition" align="center"></el-table-column>
      <el-table-column label="紧急状态" prop="emergencyStatus" align="center" :formatter="formatEmergency"></el-table-column>
      <el-table-column label="状态" prop="status" align="center" :formatter="formatStatus"></el-table-column>
      <el-table-column label="创建时间" prop="createTime" align="center"></el-table-column>
      <el-table-column label="操作" align="center" width="210px">
        <template slot-scope="scope">
          <el-button type="text" icon="el-icon-view" size="mini" @click="handleView(scope.row)">查看</el-button>
          <el-button type="text" icon="el-icon-edit" size="mini" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button type="text" icon="el-icon-delete" size="mini" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <pagination v-show="totalPatients > 0" :total="totalPatients" :page.sync="queryParams.pageNum"
                :limit.sync="queryParams.pageSize" @pagination="fetchPatients"/>

    <!-- 查看患者对话框 -->
    <el-dialog :visible.sync="viewDialogVisible" title="查看患者" width="30%">
      <el-form :model="patientForm" label-width="100px" disabled>
        <el-form-item label="患者ID">
          <el-input v-model="patientForm.userId" disabled></el-input>
        </el-form-item>
        <el-form-item label="器官需求">
          <el-input :value="formatOrgan(patientForm)" disabled></el-input>
        </el-form-item>
        <el-form-item label="血型">
          <el-input :value="formatBlood(patientForm)" disabled></el-input>
        </el-form-item>
        <el-form-item label="病情描述">
          <el-input v-model="patientForm.medicalCondition" disabled></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="viewDialogVisible = false">关闭</el-button>
      </div>
    </el-dialog>

    <!-- 编辑患者对话框 -->
    <el-dialog :visible.sync="editDialogVisible" :title="dialogTitle" width="30%">
      <el-form :model="patientForm" label-width="100px">
        <el-form-item label="患者ID">
          <el-input v-model="patientForm.userId"></el-input>
        </el-form-item>
        <el-form-item label="病情描述">
          <el-input v-model="patientForm.medicalCondition" type=""></el-input>
        </el-form-item>
        <el-form-item label="紧急状态">
          <el-radio-group v-model="patientForm.emergencyStatus">
            <el-radio :label="0">否</el-radio>
            <el-radio :label="1">是</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="器官需求">
          <el-select v-model="patientForm.organNeeded">
            <el-option v-for="(label, value) in organOptions" :key="value" :label="label" :value="value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="血型">
          <el-select v-model="patientForm.bloodType">
            <el-option v-for="(label, value) in bloodOptions" :key="value" :label="label" :value="value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="patientForm.status">
            <el-option v-for="(label, value) in statusOptions" :key="value" :label="label" :value="value"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">{{ dialogButtonText }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listAllPatients, addPatient, updatePatient, deletePatient, getPatientById } from '@/api/patient/patient'

export default {
  data() {
    return {
      loading: false,
      patientList: [],
      totalPatients: 0,
      queryParams: {pageNum: 1, pageSize: 10},
      viewDialogVisible: false,
      editDialogVisible: false,
      dialogTitle: '',
      dialogButtonText: '',
      patientForm: {
      },
      organOptions: {0: '肝', 1: '肾', 2: '心脏'},
      bloodOptions: {0: 'A', 1: 'B', 2: 'AB', 3: 'O'},
      statusOptions: {0: '审核中', 1: '等待移植', 2: '准备移植', 3: '完成移植', 4: '已放弃'}
    }
  },
  created() {
    this.fetchPatients()
  },
  methods: {
    fetchPatients() {
      this.loading = true
      listAllPatients(this.queryParams).then(response => {
        this.patientList = response.rows
        this.totalPatients = response.total
        this.loading = false
      })
    },
    handleAddPatient() {
      this.dialogTitle = '新增患者';
      this.dialogButtonText = '提交';
      this.patientForm = {
        userId: '',
        organNeeded: '',
        bloodType: '',
        medicalCondition: '',
        status: ''
      };
      this.editDialogVisible = true;
    },
    handleView(row) {
      getPatientById(row.id).then(response => {
        this.patientForm = response.data
        this.viewDialogVisible = true
      })
    },
    handleEdit(row) {
      this.dialogTitle = '编辑患者'
      this.dialogButtonText = '更新'
      this.patientForm = Object.assign({}, row)
      this.editDialogVisible = true
    },
    handleSubmit() {
      if (this.dialogTitle === '新增患者') {
        addPatient(this.patientForm).then(() => {
          this.$message.success('新增成功');
          this.editDialogVisible = false;
          this.fetchPatients();
        });
      } else {
        updatePatient(this.patientForm).then(() => {
          this.$message.success('更新成功');
          this.editDialogVisible = false;
          this.fetchPatients();
        });
      }
    },
    handleDelete(row) {
      deletePatient(row.id).then(() => {
        this.$message.success('删除成功')
        this.fetchPatients()
      })
    },
    formatOrgan(row) {
      return this.organOptions[row.organNeeded] || '-'
    },
    formatBlood(row) {
      return this.bloodOptions[row.bloodType] || '-'
    },
    formatEmergency(row) {
      return row.emergencyStatus ? '是' : '否'
    },
    formatStatus(row) {
      return this.statusOptions[row.status] || '-'
    }
  }
}
</script>
