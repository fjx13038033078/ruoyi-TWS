<template>
  <div>
    <!-- 添加患者按钮 -->
    <el-row :gutter="20" class="mb-20" style="margin-bottom: 10px; margin-left: 10px; margin-top: 10px">
      <el-col>
        <el-button type="primary" plain icon="el-icon-plus" size="medium" @click="handleAddPatient">
          新增病情信息
        </el-button>
      </el-col>
    </el-row>

    <!-- 患者列表 -->
    <el-table :data="patientList" v-loading="loading" style="width: 100%" border>
      <el-table-column label="登记ID" prop="id" align="center"></el-table-column>
      <el-table-column label="患者姓名" prop="userName" align="center"></el-table-column>
      <el-table-column label="器官需求" prop="organNeeded" align="center" :formatter="formatOrgan"></el-table-column>
      <el-table-column label="血型" prop="bloodType" align="center" :formatter="formatBlood"></el-table-column>
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
    <el-dialog :visible.sync="viewDialogVisible" title="查看患者" width="50%">
      <el-form :model="patientForm" label-width="140px" disabled>
        <el-form-item label="患者ID">
          <el-input v-model="patientForm.userId"></el-input>
        </el-form-item>
        <el-form-item label="患者姓名">
          <el-input v-model="patientForm.userName"></el-input>
        </el-form-item>
        <el-form-item label="器官需求">
          <el-input :value="formatOrgan(patientForm)"></el-input>
        </el-form-item>
        <el-form-item label="血型">
          <el-input :value="formatBlood(patientForm)"></el-input>
        </el-form-item>
        <el-form-item label="紧急状态">
          <el-input :value="formatEmergency(patientForm)"></el-input>
        </el-form-item>
        <el-form-item label="HLA配型信息">
          <el-input v-model="patientForm.hlaTyping"></el-input>
        </el-form-item>
        <el-form-item label="乙肝感染">
          <el-input :value="formatInfection(patientForm.hbvInfected)"></el-input>
        </el-form-item>
        <el-form-item label="丙肝感染">
          <el-input :value="formatInfection(patientForm.hcvInfected)"></el-input>
        </el-form-item>
        <el-form-item label="艾滋病感染">
          <el-input :value="formatInfection(patientForm.hivInfected)"></el-input>
        </el-form-item>
        <el-form-item label="医疗状态">
          <el-input :value="formatMedicalStatus(patientForm)"></el-input>
        </el-form-item>
        <el-form-item label="自评状态">
          <el-input :value="formatSelfAssessment(patientForm)"></el-input>
        </el-form-item>
        <el-form-item label="病情描述">
          <el-input type="textarea" v-model="patientForm.medicalCondition"></el-input>
        </el-form-item>
        <el-form-item label="是否正在透析" v-if="patientForm.organNeeded === 1">
          <el-input :value="formatDialysis(patientForm)"></el-input>
        </el-form-item>
        <el-form-item label="透析类型" v-if="patientForm.organNeeded === 1 && patientForm.isOnDialysis === 1">
          <el-input :value="formatDialysisType(patientForm)"></el-input>
        </el-form-item>
        <el-form-item label="初次透析时间" v-if="patientForm.organNeeded === 1 && patientForm.isOnDialysis === 1">
          <el-input v-model="patientForm.firstDialysisDate"></el-input>
        </el-form-item>
        <el-form-item label="透析频率" v-if="patientForm.organNeeded === 1 && patientForm.isOnDialysis === 1">
          <el-input v-model="patientForm.dialysisFrequency"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="viewDialogVisible = false">关闭</el-button>
      </div>
    </el-dialog>

    <!-- 编辑患者对话框 -->
    <el-dialog :visible.sync="editDialogVisible" :title="dialogTitle" width="50%">
      <el-form :model="patientForm" label-width="140px">
        <el-form-item label="器官需求">
          <el-select v-model="patientForm.organNeeded">
            <el-option v-for="(label, value) in organOptions" :key="value" :label="label" :value="Number(value)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="血型">
          <el-select v-model="patientForm.bloodType">
            <el-option v-for="(label, value) in bloodOptions" :key="value" :label="label" :value="Number(value)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="紧急状态">
          <el-select v-model="patientForm.emergencyStatus">
            <el-option v-for="(label, value) in emergencyOptions" :key="value" :label="label" :value="Number(value)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="HLA配型信息">
          <el-input v-model="patientForm.hlaTyping"></el-input>
        </el-form-item>
        <el-form-item label="乙肝感染">
          <el-select v-model="patientForm.hbvInfected">
            <el-option v-for="(label, value) in infectionOptions" :key="value" :label="label" :value="Number(value)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="丙肝感染">
          <el-select v-model="patientForm.hcvInfected">
            <el-option v-for="(label, value) in infectionOptions" :key="value" :label="label" :value="Number(value)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="艾滋病感染">
          <el-select v-model="patientForm.hivInfected">
            <el-option v-for="(label, value) in infectionOptions" :key="value" :label="label" :value="Number(value)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="医疗状态">
          <el-select v-model="patientForm.medicalStatus">
            <el-option v-for="(label, value) in medicalStatusOptions" :key="value" :label="label" :value="Number(value)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="自评状态">
          <el-select v-model="patientForm.selfAssessment">
            <el-option v-for="(label, value) in selfAssessmentOptions" :key="value" :label="label" :value="Number(value)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="病情描述">
          <el-input type="textarea" v-model="patientForm.medicalCondition"></el-input>
        </el-form-item>
        <el-form-item label="是否正在透析" v-if="patientForm.organNeeded === 1">
          <el-select v-model="patientForm.isOnDialysis">
            <el-option v-for="(label, value) in dialysisOptions" :key="value" :label="label" :value="Number(value)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="透析类型" v-if="patientForm.organNeeded === 1 && patientForm.isOnDialysis === 1">
          <el-select v-model="patientForm.dialysisType">
            <el-option v-for="(label, value) in dialysisTypeOptions" :key="value" :label="label" :value="Number(value)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="初次透析时间" v-if="patientForm.organNeeded === 1 && patientForm.isOnDialysis === 1">
          <el-date-picker v-model="patientForm.firstDialysisDate" type="date" placeholder="选择日期"></el-date-picker>
        </el-form-item>
        <el-form-item label="透析频率" v-if="patientForm.organNeeded === 1 && patientForm.isOnDialysis === 1">
          <el-input v-model="patientForm.dialysisFrequency" placeholder="例如：每周3次"></el-input>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="patientForm.status">
            <el-option v-for="(label, value) in statusOptions" :key="value" :label="label" :value="Number(value)"></el-option>
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
      patientForm: {},
      organOptions: {0: '肝', 1: '肾'},
      bloodOptions: {0: 'A', 1: 'B', 2: 'AB', 3: 'O'},
      statusOptions: {0: '审核中', 1: '等待移植', 2: '准备移植', 3: '完成移植', 4: '已放弃'},
      emergencyOptions: {0: '否', 1: '是'},
      infectionOptions: {0: '否', 1: '是'},
      medicalStatusOptions: {0: '在重症监护室', 1: '住院', 2: '不在重症监护室', 3: '未住院'},
      selfAssessmentOptions: {0: '病情稳定', 1: '病情反复', 2: '情况危殆'},
      dialysisOptions: {0: '否', 1: '是'},
      dialysisTypeOptions: {0: '血液透析', 1: '腹膜透析'}
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
      this.dialogTitle = '新增患者'
      this.dialogButtonText = '提交'
      this.patientForm = {
        organNeeded: '',
        bloodType: '',
        emergencyStatus: 0,
        hbvInfected: 0,
        hcvInfected: 0,
        hivInfected: 0,
        medicalStatus: '',
        selfAssessment: '',
        medicalCondition: '',
        status: 0,
        isOnDialysis: 0
      }
      this.editDialogVisible = true
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
          this.$message.success('新增成功')
          this.editDialogVisible = false
          this.fetchPatients()
        })
      } else {
        updatePatient(this.patientForm).then(() => {
          this.$message.success('更新成功')
          this.editDialogVisible = false
          this.fetchPatients()
        })
      }
    },
    handleDelete(row) {
      this.$confirm('确认删除该患者信息吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deletePatient(row.id).then(() => {
          this.$message.success('删除成功')
          this.fetchPatients()
        })
      })
    },
    formatOrgan(row) {
      return this.organOptions[row.organNeeded] || '-'
    },
    formatBlood(row) {
      return this.bloodOptions[row.bloodType] || '-'
    },
    formatEmergency(row) {
      return this.emergencyOptions[row.emergencyStatus] || '-'
    },
    formatStatus(row) {
      return this.statusOptions[row.status] || '-'
    },
    formatInfection(value) {
      return this.infectionOptions[value] || '-'
    },
    formatMedicalStatus(row) {
      return this.medicalStatusOptions[row.medicalStatus] || '-'
    },
    formatSelfAssessment(row) {
      return this.selfAssessmentOptions[row.selfAssessment] || '-'
    },
    formatDialysis(row) {
      return this.dialysisOptions[row.isOnDialysis] || '-'
    },
    formatDialysisType(row) {
      return this.dialysisTypeOptions[row.dialysisType] || '-'
    }
  }
}
</script>
