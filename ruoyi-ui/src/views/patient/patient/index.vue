<template>
  <div>
    <!-- 搜索框 -->
    <el-form v-if="isAdmin" :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="68px" style="margin-bottom: 20px">
      <el-form-item label="器官需求" prop="organNeeded">
        <el-select
          v-model="searchParams.organNeeded"
          placeholder="器官需求"
          clearable
          style="width: 200px"
        >
          <el-option
            v-for="(label, value) in organOptions"
            :key="value"
            :label="label"
            :value="value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="血型" prop="bloodType">
        <el-select
          v-model="searchParams.bloodType"
          placeholder="血型"
          clearable
          style="width: 200px"
        >
          <el-option
            v-for="(label, value) in bloodOptions"
            :key="value"
            :label="label"
            :value="value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select
          v-model="searchParams.status"
          placeholder="状态"
          clearable
          style="width: 200px"
        >
          <el-option
            v-for="(label, value) in statusOptions"
            :key="value"
            :label="label"
            :value="value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="紧急状态" prop="emergencyStatus">
        <el-select
          v-model="searchParams.emergencyStatus"
          placeholder="紧急状态"
          clearable
          style="width: 200px"
        >
          <el-option
            v-for="(label, value) in emergencyOptions"
            :key="value"
            :label="label"
            :value="value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          <el-button
            type="warning"
            plain
            icon="el-icon-download"
            size="mini"
            @click="handleExport"
            v-hasPermi="['patient:export']"
          >导出</el-button>
        <el-button
          type="info"
          plain
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
          v-hasPermi="['patient:import']"
        >导入</el-button>
      </el-form-item>
    </el-form>

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
        <el-form-item label="附件查看">
          <FileUpload v-model="patientForm.fileName" :is-show-tip="false" :disabled="true"></FileUpload>
        </el-form-item>
        <template v-if="patientForm.organNeeded === 1">
          <el-form-item label="肌酐(umol/L)">
            <el-input v-model="patientForm.creatinine"></el-input>
          </el-form-item>
          <el-form-item label="谷丙转氨酶(ALT,U/L)">
            <el-input v-model="patientForm.alt"></el-input>
          </el-form-item>
          <el-form-item label="谷草转氨酶(AST,U/L)">
            <el-input v-model="patientForm.ast"></el-input>
          </el-form-item>
          <el-form-item label="是否患有白血病">
            <el-input :value="formatYesNo(patientForm.leukemia)"></el-input>
          </el-form-item>
          <el-form-item label="过去一年内是否曾骨折">
            <el-input :value="formatYesNo(patientForm.fractureLastYear)"></el-input>
          </el-form-item>
          <el-form-item label="是否患有血管性骨坏死">
            <el-input :value="formatYesNo(patientForm.avascularNecrosis)"></el-input>
          </el-form-item>
        </template>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="viewDialogVisible = false">关闭</el-button>
      </div>
    </el-dialog>

    <!-- 编辑患者对话框 -->
    <el-dialog :visible.sync="editDialogVisible" :title="dialogTitle" width="50%">
      <el-form ref="patientForm" :model="patientForm" label-width="140px" :rules="rules">
        <el-form-item label="选择患者" v-if="isAdmin">
          <el-select v-model="selectedUser" placeholder="请选择患者" @change="handleUserChange">
            <el-option
              v-for="item in userList"
              :key="item.userId"
              :label="item.nickName"
              :value="item.userId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="器官需求" prop="organNeeded" required>
          <el-select v-model="patientForm.organNeeded" placeholder="请选择器官需求">
            <el-option v-for="(label, value) in organOptions" :key="value" :label="label" :value="Number(value)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="血型" prop="bloodType" required>
          <el-select v-model="patientForm.bloodType" placeholder="请选择血型">
            <el-option v-for="(label, value) in bloodOptions" :key="value" :label="label" :value="Number(value)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="紧急状态" prop="emergencyStatus" required>
          <el-select v-model="patientForm.emergencyStatus" placeholder="请选择紧急状态">
            <el-option v-for="(label, value) in emergencyOptions" :key="value" :label="label" :value="Number(value)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="HLA配型信息" prop="hlaTyping">
          <el-input v-model="patientForm.hlaTyping"></el-input>
        </el-form-item>
        <el-form-item label="乙肝感染" prop="hbvInfected" required>
          <el-select v-model="patientForm.hbvInfected" placeholder="请选择是否感染">
            <el-option v-for="(label, value) in infectionOptions" :key="value" :label="label" :value="Number(value)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="丙肝感染" prop="hcvInfected" required>
          <el-select v-model="patientForm.hcvInfected" placeholder="请选择是否感染">
            <el-option v-for="(label, value) in infectionOptions" :key="value" :label="label" :value="Number(value)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="艾滋病感染" prop="hivInfected" required>
          <el-select v-model="patientForm.hivInfected" placeholder="请选择是否感染">
            <el-option v-for="(label, value) in infectionOptions" :key="value" :label="label" :value="Number(value)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="医疗状态" prop="medicalStatus" required>
          <el-select v-model="patientForm.medicalStatus" placeholder="请选择医疗状态">
            <el-option v-for="(label, value) in medicalStatusOptions" :key="value" :label="label" :value="Number(value)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="自评状态" prop="selfAssessment" required>
          <el-select v-model="patientForm.selfAssessment" placeholder="请选择自评状态">
            <el-option v-for="(label, value) in selfAssessmentOptions" :key="value" :label="label" :value="Number(value)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="病情描述" prop="medicalCondition">
          <el-input type="textarea" v-model="patientForm.medicalCondition"></el-input>
        </el-form-item>
        <el-form-item label="状态" prop="status" required>
          <el-select v-model="patientForm.status" placeholder="请选择状态">
            <el-option v-for="(label, value) in statusOptions" :key="value" :label="label" :value="Number(value)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="附件上传" prop="fileName">
          <FileUpload v-model="patientForm.fileName"></FileUpload>
        </el-form-item>
        <el-form-item label="是否正在透析" v-if="patientForm.organNeeded === 1" prop="isOnDialysis">
          <el-select v-model="patientForm.isOnDialysis" placeholder="请选择是否透析">
            <el-option v-for="(label, value) in dialysisOptions" :key="value" :label="label" :value="Number(value)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item 
          label="透析类型" 
          v-if="patientForm.organNeeded === 1 && patientForm.isOnDialysis === 1" 
          prop="dialysisType">
          <el-select v-model="patientForm.dialysisType" placeholder="请选择透析类型">
            <el-option v-for="(label, value) in dialysisTypeOptions" :key="value" :label="label" :value="Number(value)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item 
          label="初次透析时间" 
          v-if="patientForm.organNeeded === 1 && patientForm.isOnDialysis === 1" 
          prop="firstDialysisDate">
          <el-date-picker v-model="patientForm.firstDialysisDate" type="date" placeholder="选择日期"></el-date-picker>
        </el-form-item>
        <el-form-item 
          label="透析频率" 
          v-if="patientForm.organNeeded === 1 && patientForm.isOnDialysis === 1" 
          prop="dialysisFrequency">
          <el-input v-model="patientForm.dialysisFrequency" placeholder="例如：每周3次"></el-input>
        </el-form-item>
        <template v-if="patientForm.organNeeded === 1">
          <el-form-item label="肌酐(umol/L)" prop="creatinine">
            <el-input v-model="patientForm.creatinine" type="number"></el-input>
          </el-form-item>
          
          <el-form-item label="谷丙转氨酶(ALT,U/L)" prop="alt">
            <el-input v-model="patientForm.alt" type="number"></el-input>
          </el-form-item>
          
          <el-form-item label="谷草转氨酶(AST,U/L)" prop="ast">
            <el-input v-model="patientForm.ast" type="number"></el-input>
          </el-form-item>
          
          <el-form-item label="是否患有白血病" prop="leukemia">
            <el-select v-model="patientForm.leukemia" placeholder="请选择是否患有白血病">
              <el-option v-for="(label, value) in yesNoOptions" :key="value" :label="label" :value="Number(value)"></el-option>
            </el-select>
          </el-form-item>
          
          <el-form-item label="过去一年内是否曾骨折" prop="fractureLastYear">
            <el-select v-model="patientForm.fractureLastYear" placeholder="请选择是否曾骨折">
              <el-option v-for="(label, value) in yesNoOptions" :key="value" :label="label" :value="Number(value)"></el-option>
            </el-select>
          </el-form-item>
          
          <el-form-item label="是否患有血管性骨坏死" prop="avascularNecrosis">
            <el-select v-model="patientForm.avascularNecrosis" placeholder="请选择是否患有血管性骨坏死">
              <el-option v-for="(label, value) in yesNoOptions" :key="value" :label="label" :value="Number(value)"></el-option>
            </el-select>
          </el-form-item>
        </template>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">{{ dialogButtonText }}</el-button>
      </div>
    </el-dialog>

    <!-- 用户导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <div class="el-upload__tip" slot="tip">
            <el-checkbox v-model="upload.updateSupport" /> 是否更新已经存在的患者数据
          </div>
          <span>仅允许导入xls、xlsx格式文件。</span>
          <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;" @click="importTemplate">下载模板</el-link>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listAllPatients, addPatient, updatePatient, deletePatient, getPatientById } from '@/api/patient/patient'
import {getInfo} from "@/api/login";
import {listStudent} from "@/api/system/user";
import {getToken} from "@/utils/auth";

export default {
  data() {
    // 动态验证规则
    const validateKidneyFields = (rule, value, callback) => {
      if (this.patientForm.organNeeded === 1) {
        if (value === undefined || value === null || value === '') {
          callback(new Error('该字段为必填项'));
        } else {
          callback();
        }
      } else {
        callback();
      }
    };

    // 动态验证透析相关字段
    const validateDialysisFields = (rule, value, callback) => {
      if (this.patientForm.organNeeded === 1 && this.patientForm.isOnDialysis === 1) {
        if (value === undefined || value === null || value === '') {
          callback(new Error('该字段为必填项'));
        } else {
          callback();
        }
      } else {
        callback();
      }
    };

    return {
      loading: false,
      patientList: [],
      totalPatients: 0,
      queryParams: {
        pageNum: 1,
        pageSize: 10
      },
      searchParams: {
        organNeeded: undefined,
        bloodType: undefined,
        status: undefined,
        emergencyStatus: undefined
      },
      // 用户导入参数
      upload: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/patient/importData"
      },
      viewDialogVisible: false,
      editDialogVisible: false,
      dialogTitle: '',
      dialogButtonText: '',
      patientForm: {},
      organOptions: {0: '肝', 1: '肾'},
      bloodOptions: {0: 'A', 1: 'B', 2: 'AB', 3: 'O'},
      // statusOptions: {0: '审核中', 1: '等待移植', 2: '准备移植', 3: '完成移植', 4: '已放弃'},
      statusOptions: { 1: '等待移植', 4: '已放弃'},
      emergencyOptions: {0: '否', 1: '是'},
      infectionOptions: {0: '否', 1: '是'},
      medicalStatusOptions: {0: '在重症监护室', 1: '住院', 2: '不在重症监护室', 3: '未住院'},
      selfAssessmentOptions: {0: '病情稳定', 1: '病情反复', 2: '情况危殆'},
      dialysisOptions: {0: '否', 1: '是'},
      dialysisTypeOptions: {0: '血液透析', 1: '腹膜透析'},
      fileName: '',
      isAdmin: false,
      userList: [],
      selectedUser: null,
      yesNoOptions: {0: '否', 1: '是'},
      rules: {
        organNeeded: [
          { required: true, message: '请选择所需器官', trigger: 'change' }
        ],
        bloodType: [
          { required: true, message: '请选择血型', trigger: 'change' }
        ],
        emergencyStatus: [
          { required: true, message: '请选择是否紧急', trigger: 'change' }
        ],
        status: [
          { required: true, message: '请选择状态', trigger: 'change' }
        ],
        hbvInfected: [
          { required: true, message: '请选择是否感染乙肝', trigger: 'change' }
        ],
        hcvInfected: [
          { required: true, message: '请选择是否感染丙肝', trigger: 'change' }
        ],
        hivInfected: [
          { required: true, message: '请选择是否感染艾滋病', trigger: 'change' }
        ],
        medicalStatus: [
          { required: true, message: '请选择医疗状态', trigger: 'change' }
        ],
        selfAssessment: [
          { required: true, message: '请选择自评状态', trigger: 'change' }
        ],
        // 肾移植特有字段的动态验证
        isOnDialysis: [
          { validator: validateKidneyFields, trigger: 'change' }
        ],
        dialysisType: [
          { validator: validateDialysisFields, trigger: 'change' }
        ],
        firstDialysisDate: [
          { validator: validateDialysisFields, trigger: 'change' }
        ],
        dialysisFrequency: [
          { validator: validateDialysisFields, trigger: 'change' }
        ],
        leukemia: [
          { validator: validateKidneyFields, trigger: 'change' }
        ],
        fractureLastYear: [
          { validator: validateKidneyFields, trigger: 'change' }
        ],
        avascularNecrosis: [
          { validator: validateKidneyFields, trigger: 'change' }
        ]
      }
    }
  },
  created() {
    this.fetchPatients();
    this.getCurrentInfo();
  },
  methods: {
    getCurrentInfo(){
      getInfo().then(response => {
        this.isAdmin = response.roles.includes('admin')
        if (this.isAdmin) {
          this.getPatientInfo()
        } else {
          // 非管理员用户清空搜索参数
          this.searchParams = {
            organNeeded: undefined,
            bloodType: undefined,
            status: undefined
          }
        }
      })
    },
    getPatientInfo(){
      listStudent().then(response => {
        if (response.code === 200) {
          this.userList = response.rows
        }
      })
    },
    handleUserChange(userId) {
      const selectedUser = this.userList.find(user => user.userId === userId)
      if (selectedUser) {
        this.patientForm.userId = selectedUser.userId
        this.patientForm.userName = selectedUser.userName
      }
    },
    fetchPatients() {
      this.loading = true
      const params = {
        ...this.queryParams,
        ...this.searchParams
      }

      if (params.organNeeded !== undefined && params.organNeeded !== '') {
        params.organNeeded = Number(params.organNeeded)
      }
      if (params.bloodType !== undefined && params.bloodType !== '') {
        params.bloodType = Number(params.bloodType)
      }
      if (params.status !== undefined && params.status !== '') {
        params.status = Number(params.status)
      }

      listAllPatients(params).then(response => {
        console.log('params',params)
        this.patientList = response.rows
        this.totalPatients = response.total
        this.loading = false
      })
    },
    handleAddPatient() {
      this.dialogTitle = '新增患者'
      this.dialogButtonText = '提交'
      this.selectedUser = null
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
        status: 1,
        isOnDialysis: 0,
        creatinine: '',
        alt: '',
        ast: '',
        leukemia: 0,
        fractureLastYear: 0,
        avascularNecrosis: 0
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
      this.$refs.patientForm.validate((valid) => {
        if (valid) {
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
        } else {
          return false
        }
      })
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
    },
    formatYesNo(value) {
      return this.yesNoOptions[value] || '-'
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.fetchPatients();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.searchParams = {
        organNeeded: undefined,
        bloodType: undefined,
        status: undefined
      };
      this.handleQuery();
    },
    /** 表单重置 */
    resetForm(formName) {
      if (this.$refs[formName]) {
        this.$refs[formName].resetFields();
      }
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('patient/export', {
        ...this.queryParams
      }, `patient_${new Date().getTime()}.xlsx`)
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "用户导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download('patient/importTemplate', {
      }, `patient_template_${new Date().getTime()}.xlsx`)
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", { dangerouslyUseHTMLString: true });
      this.getList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    }
  },
  watch: {
    // 监听器用于在器官需求改变时重新验证表单
    'patientForm.organNeeded': {
      handler(newVal) {
        // 当值改变时，重新验证相关字段
        if (this.$refs.patientForm) {
          this.$refs.patientForm.validateField([
            'isOnDialysis',
            'dialysisType',
            'firstDialysisDate',
            'dialysisFrequency',
            'leukemia',
            'fractureLastYear',
            'avascularNecrosis'
          ]);
        }
      }
    },
    // 监听器用于在透析状态改变时重新验证表单
    'patientForm.isOnDialysis': {
      handler(newVal) {
        // 当值改变时，重新验证相关字段
        if (this.$refs.patientForm) {
          this.$refs.patientForm.validateField([
            'dialysisType',
            'firstDialysisDate',
            'dialysisFrequency'
          ]);
        }
      }
    }
  }
}
</script>

<style scoped>
.el-form {
  margin-top: 10px;
  margin-left: 10px;
}
</style>
