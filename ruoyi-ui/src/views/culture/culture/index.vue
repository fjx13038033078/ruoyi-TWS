<template>
  <div>
    <!-- 非遗文化管理页面内容 -->
    <div>
      <!-- 添加非遗文化按钮 -->
      <el-row :gutter="20" class="mb-20" style="margin-bottom: 10px;margin-left: 10px;margin-top: 10px">
        <el-col>
          <el-button type="primary"
                     plain
                     icon="el-icon-plus"
                     size="medium"
                     @click="handleAddCulture"
                     v-hasPermi="['culture:culture:add']">新增非遗文化
          </el-button>
        </el-col>
      </el-row>

      <!-- 非遗文化列表 -->
      <el-table :data="cultureList" v-loading="loading" style="width: 100%" border>
        <el-table-column label="非遗ID" prop="cultureId" align="center"></el-table-column>
        <el-table-column label="非遗名称" prop="cultureName" align="center"></el-table-column>
        <el-table-column label="非遗类型" prop="cultureType" align="center">
          <template slot-scope="scope">
            {{ getCultureTypeLabel(scope.row.cultureType) }}
          </template>
        </el-table-column>
        <el-table-column label="非遗级别" prop="cultureLevel" align="center">
          <template slot-scope="scope">
            {{ getCultureLevelLabel(scope.row.cultureLevel) }}
          </template>
        </el-table-column>
        <el-table-column label="非遗发源地" prop="cultureBirthplace" align="center"></el-table-column>
        <el-table-column label="非遗传承人" prop="cultureHeir" align="center"></el-table-column>
        <el-table-column label="操作" align="center" width="210px">
          <template slot-scope="scope">
            <el-button type="text" icon="el-icon-view" size="mini" @click="handleView(scope.row)" v-hasPermi="['culture:culture:view']">查看</el-button>
            <el-button type="text" icon="el-icon-edit" size="mini" @click="handleEdit(scope.row)" v-hasPermi="['culture:culture:edit']">编辑</el-button>
            <el-button type="text" icon="el-icon-delete" size="mini" @click="handleDelete(scope.row)" v-hasPermi="['culture:culture:delete']">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 分页组件 -->
    <pagination
      v-show="totalCultures > 0"
      :total="totalCultures"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="fetchCultures"
    />

    <!-- 添加/编辑非遗文化对话框 -->
    <el-dialog :visible.sync="dialogVisible" :title="dialogTitle" width="30%" @close="handleCloseDialog">
      <!-- 对话框内容 -->
      <div>
        <el-form :model="cultureForm" label-width="100px">
          <el-form-item label="非遗名称">
            <el-input v-model="cultureForm.cultureName" :disabled="isReadOnly"></el-input>
          </el-form-item>
          <el-form-item label="非遗类型">
            <el-select v-model="cultureForm.cultureType" :disabled="isReadOnly">
              <el-option label="传统舞蹈" :value="1"></el-option>
              <el-option label="传统戏剧" :value="2"></el-option>
              <el-option label="传统音乐" :value="3"></el-option>
              <el-option label="传统美术" :value="4"></el-option>
              <el-option label="传统医药" :value="5"></el-option>
              <el-option label="传统技艺" :value="6"></el-option>
              <el-option label="其他" :value="0"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="非遗级别">
            <el-select v-model="cultureForm.cultureLevel" :disabled="isReadOnly">
              <el-option label="国家级" :value="1"></el-option>
              <el-option label="省级" :value="2"></el-option>
              <el-option label="其他" :value="0"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="非遗发源地">
            <el-input v-model="cultureForm.cultureBirthplace" :disabled="isReadOnly"></el-input>
          </el-form-item>
          <el-form-item label="非遗传承人">
            <el-input v-model="cultureForm.cultureHeir" :disabled="isReadOnly"></el-input>
          </el-form-item>
          <!-- 根据当前模式决定显示 ImageUpload 或 ImagePreview -->
          <el-form-item label="非遗图片">
            <template v-if="!isReadOnly">
              <ImageUpload v-model="cultureForm.cultureImage" :disabled="isReadOnly"><</ImageUpload>
            </template>
            <template v-else>
              <ImagePreview :src="cultureForm.cultureImage" :disabled="isReadOnly"></ImagePreview>
            </template>
          </el-form-item>
          <el-form-item label="非遗描述">
            <el-input v-model="cultureForm.cultureDescription" :disabled="isReadOnly" type="textarea"
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
import {listAllCultures, addCulture, updateCulture, deleteCulture, getCultureById} from '@/api/culture/culture'

export default {
  data() {
    return {
      loading: false,
      cultureList: [],
      dialogVisible: false,
      dialogTitle: '',
      dialogButtonText: '',
      totalCultures: 0,
      isReadOnly: false, // 是否只读模式
      cultureForm: {
        cultureName: '',
        cultureType: '',
        cultureBirthplace: '',
        cultureLevel: '',
        cultureHeir: '',
        cultureImage: '',
        cultureDescription: ''
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10
      },
    }
  },
  computed: {
    // 将非遗类型数字值转换为汉字
    getCultureTypeLabel() {
      return function (cultureType) {
        switch (cultureType) {
          case 0:
            return '其他';
          case 1:
            return '传统舞蹈';
          case 2:
            return '传统戏剧';
          case 3:
            return '传统音乐';
          case 4:
            return '传统美术';
          case 5:
            return '传统医药';
          case 6:
            return '传统技艺';
          default:
            return '未知';
        }
      }
    },
    // 将非遗级别数字值转换为汉字
    getCultureLevelLabel() {
      return function (cultureLevel) {
        switch (cultureLevel) {
          case 0:
            return '其他';
          case 1:
            return '国际级';
          case 2:
            return '省级';
          default:
            return '未知';
        }
      }
    },
  },

  created() {
    this.fetchCultures()
  },
  methods: {
    fetchCultures() {
      this.loading = true
      listAllCultures().then(response => {
        this.cultureList = response.rows
        this.totalCultures = response.total
        this.loading = false
      })
    },


    handleView(row) {
      this.dialogTitle = '查看非遗文化'
      this.dialogButtonText = '关闭'
      this.loading = true // 开启加载状态
      this.isReadOnly = true // 设置为只读模式
      getCultureById(row.cultureId).then(response => {
        this.cultureForm = response.data // 填充表单数据
        this.dialogVisible = true // 打开对话框
        this.loading = false // 关闭加载状态
      }).catch(error => {
        console.error('获取非遗文化详情失败：', error)
        this.$message.error('获取非遗文化详情失败，请稍后重试')
        this.loading = false // 关闭加载状态
      })
    },

    handleAddCulture() {
      this.isReadOnly = false
      this.dialogTitle = '新增非遗文化'
      this.dialogButtonText = '添加'
      this.dialogVisible = true
    },
    handleEdit(row) {
      this.isReadOnly = false
      this.dialogTitle = '编辑非遗文化'
      this.dialogButtonText = '更新'
      this.cultureForm = Object.assign({}, row)
      this.dialogVisible = true
    },
    handleDelete(row) {
      this.$confirm('确认删除该非遗文化吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteCulture(row.cultureId).then(() => {
          this.$message.success('删除成功')
          this.fetchCultures()
        })
      })
    },
    handleCloseDialog() {
      this.dialogVisible = false
      this.clearForm()
    },
    clearForm() {
      this.cultureForm = {
        cultureName: '',
        cultureType: '',
        cultureBirthplace: '',
        cultureLevel: '',
        cultureHeir: '',
        cultureDescription: ''
      }
    },
    handleSubmit() {
      if (this.dialogButtonText === '添加') {
        addCulture(this.cultureForm).then(() => {
          this.$message.success('添加成功')
          this.dialogVisible = false
          this.clearForm()
          this.fetchCultures()
        })
      } else if (this.dialogButtonText === '更新') {
        updateCulture(this.cultureForm).then(() => {
          this.$message.success('更新成功')
          this.dialogVisible = false
          this.clearForm()
          this.fetchCultures()
        })
      } else {
        this.handleCloseDialog()
      }
    }
  }
}
</script>

<style scoped>
/* 样式可以根据需求进行调整 */
</style>
