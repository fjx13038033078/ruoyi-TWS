<template>
  <div>
    <!-- 非遗展览管理页面内容 -->
    <div>
      <!-- 添加非遗展览按钮 -->
      <el-row :gutter="20" class="mb-20" style="margin-bottom: 10px;margin-left: 10px;margin-top: 10px">
        <el-col>
          <el-button type="primary"
                     plain
                     icon="el-icon-plus"
                     size="medium"
                     @click="handleAddExhibition"
                     v-hasPermi="['culture:exhibition:add']">新增非遗展览</el-button>
        </el-col>
      </el-row>

      <!-- 非遗展览列表 -->
      <el-table :data="exhibitionList" v-loading="loading" style="width: 100%" border>
        <el-table-column label="展览ID" prop="exhibitionId" align="center"></el-table-column>
        <el-table-column label="非遗名称" prop="cultureName" align="center"></el-table-column>
        <el-table-column label="展览名称" prop="exhibitionName" align="center"></el-table-column>
        <el-table-column label="展览日期" prop="exhibitionDate" align="center"></el-table-column>
        <el-table-column label="展览地点" prop="exhibitionLocation" align="center"></el-table-column>
        <el-table-column label="操作" align="center" width="280px">
          <template slot-scope="scope">
            <el-button type="text" icon="el-icon-finished" size="mini" @click="handleReservation(scope.row)" v-hasPermi="['culture:exhibition:registration']">预约</el-button>
            <el-button type="text" icon="el-icon-view" size="mini" @click="handleView(scope.row)" v-hasPermi="['culture:exhibition:view']">查看</el-button>
            <el-button type="text" icon="el-icon-edit" size="mini" @click="handleEdit(scope.row)" v-hasPermi="['culture:exhibition:edit']">编辑</el-button>
            <el-button type="text" icon="el-icon-delete" size="mini" @click="handleDelete(scope.row)" v-hasPermi="['culture:exhibition:delete']">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 分页组件 -->
    <pagination
      v-show="totalExhibitions > 0"
      :total="totalExhibitions"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="fetchExhibitions"
    />

    <!-- 添加/编辑非遗展览对话框 -->
    <el-dialog :visible.sync="dialogVisible" :title="dialogTitle" width="30%" @close="handleCloseDialog">
      <!-- 对话框内容 -->
      <div>
        <el-form :model="exhibitionForm" label-width="100px">
          <el-form-item label="展览名称">
            <el-input v-model="exhibitionForm.exhibitionName" :disabled="isReadOnly"></el-input>
          </el-form-item>
          <!-- 非遗名称下拉选择 -->
          <el-form-item label="非遗名称">
            <el-select v-model="exhibitionForm.cultureId" placeholder="请选择非遗" :disabled="isReadOnly">
              <el-option
                v-for="culture in cultureOptions"
                :key="culture.cultureId"
                :label="culture.cultureName"
                :value="culture.cultureId"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="展览日期">
            <el-date-picker v-model="exhibitionForm.exhibitionDate" type="date" :disabled="isReadOnly"
                            placeholder="选择日期"></el-date-picker>
          </el-form-item>
          <el-form-item label="展览地点">
            <el-input v-model="exhibitionForm.exhibitionLocation" :disabled="isReadOnly"></el-input>
          </el-form-item>
          <el-form-item label="展览描述">
            <el-input v-model="exhibitionForm.exhibitionDescription" :disabled="isReadOnly" type="textarea"
                      autosize></el-input>
          </el-form-item>
        </el-form>
      </div>
      <!-- 对话框按钮 -->
      <div slot="footer" class="dialog-footer" style="text-align: center;">
        <el-button @click="handleCloseDialog">取消</el-button>
        <el-button type="primary" @click="handleSubmit">{{ dialogButtonText }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listAllExhibitions,
  addExhibition,
  updateExhibition,
  deleteExhibition,
  getExhibitionById
} from '@/api/culture/exhibition'
import {listAllCultures} from "@/api/culture/culture";
import {addExhibitionRegistration} from "@/api/culture/exhibitionRregistration";


export default {
  data() {
    return {
      loading: false,
      exhibitionList: [],
      cultureOptions: [], // 非遗选项列表
      dialogVisible: false,
      dialogTitle: '',
      dialogButtonText: '',
      totalExhibitions: 0,
      isReadOnly: false, // 是否只读模式
      exhibitionForm: {
        cultureId: '',
        exhibitionName: '',
        exhibitionDate: '',
        exhibitionLocation: '',
        exhibitionDescription: ''
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10
      },
    }
  },
  created() {
    this.fetchExhibitions()
    this.fetchCultures()
  },
  methods: {
    fetchExhibitions() {
      this.loading = true
      listAllExhibitions().then(response => {
        this.exhibitionList = response.rows
        this.totalExhibitions = response.total
        this.loading = false
      })
    },
    // 获取非遗列表
    fetchCultures() {
      listAllCultures().then(response => {
        // 将后端返回的场馆列表赋值给 venueOptions
        this.cultureOptions = response.rows.map(culture => ({
          cultureId: culture.cultureId,
          cultureName: culture.cultureName
        }));
      });
    },
    handleView(row) {
      this.dialogTitle = '查看非遗展览'
      this.dialogButtonText = '关闭'
      this.loading = true // 开启加载状态
      this.isReadOnly = true // 设置为只读模式
      getExhibitionById(row.exhibitionId).then(response => {
        this.exhibitionForm = response.data // 填充表单数据
        this.dialogVisible = true // 打开对话框
        this.loading = false // 关闭加载状态
      }).catch(error => {
        console.error('获取非遗展览详情失败：', error)
        this.$message.error('获取非遗展览详情失败，请稍后重试')
        this.loading = false // 关闭加载状态
      })
    },
    handleAddExhibition() {
      this.isReadOnly = false
      this.dialogTitle = '新增非遗展览'
      this.dialogButtonText = '添加'
      this.dialogVisible = true
    },
    handleEdit(row) {
      this.isReadOnly = false
      this.dialogTitle = '编辑非遗展览'
      this.dialogButtonText = '更新'
      this.exhibitionForm = Object.assign({}, row)
      this.dialogVisible = true
    },
    handleDelete(row) {
      this.$confirm('确认删除该非遗展览吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteExhibition(row.exhibitionId).then(() => {
          this.$message.success('删除成功')
          this.fetchExhibitions()
        })
      })
    },
    handleCloseDialog() {
      this.dialogVisible = false
      this.clearForm()
    },
    clearForm() {
      this.exhibitionForm = {
        exhibitionName: '',
        exhibitionDate: '',
        exhibitionLocation: '',
        exhibitionDescription: ''
      }
    },
    handleSubmit() {
      if (this.dialogButtonText === '添加') {
        addExhibition(this.exhibitionForm).then(() => {
          this.$message.success('添加成功')
          this.dialogVisible = false
          this.clearForm()
          this.fetchExhibitions()
        })
      } else if (this.dialogButtonText === '更新') {
        updateExhibition(this.exhibitionForm).then(() => {
          this.$message.success('更新成功')
          this.dialogVisible = false
          this.clearForm()
          this.fetchExhibitions()
        })
      } else {
        this.handleCloseDialog()
      }
    },
    handleReservation(row) {
      this.$confirm('确认预约该非遗展览吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        addExhibitionRegistration({ exhibitionId: row.exhibitionId }).then(() => {
          this.$message.success('预约成功')
          this.fetchExhibitions()
        }).catch(() => {
          this.$message.error('预约失败')
        })
      })
    },
  }
}
</script>

<style scoped>
/* 样式可以根据需求进行调整 */
</style>
