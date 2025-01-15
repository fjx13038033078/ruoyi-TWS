<template>
  <div>
    <!-- 成绩管理页面内容 -->
    <div>
      <!-- 添加成绩按钮 -->
      <el-row :gutter="20" class="mb-20" style="margin-bottom: 10px; margin-left: 10px; margin-top: 10px">
        <el-col>
          <el-button type="primary"
                     plain
                     icon="el-icon-plus"
                     size="medium"
                     @click="handleAddGrade"
                     v-hasPermi="['student:grade:add']">添加成绩
          </el-button>
        </el-col>
      </el-row>

      <!-- 成绩列表 -->
      <el-table :data="gradeList" v-loading="loading" style="width: 100%" border>
        <el-table-column label="成绩ID" prop="gradeId" align="center"></el-table-column>
        <el-table-column label="学生姓名" prop="userName" align="center"></el-table-column>
        <el-table-column label="课程名称" prop="courseName" align="center"></el-table-column>
        <el-table-column label="成绩" prop="grade" align="center"></el-table-column>
        <el-table-column label="学期" prop="semester" align="center"></el-table-column>
        <el-table-column label="操作" align="center" width="210px">
          <template slot-scope="scope">
            <el-button type="text" icon="el-icon-view" size="mini" @click="handleView(scope.row)"
                       v-hasPermi="['student:grade:view']">查看
            </el-button>
            <el-button type="text" icon="el-icon-edit" size="mini" @click="handleEdit(scope.row)"
                       v-hasPermi="['student:grade:edit']">编辑
            </el-button>
            <el-button type="text" icon="el-icon-delete" size="mini" @click="handleDelete(scope.row)"
                       v-hasPermi="['student:grade:delete']">删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 分页组件 -->
    <pagination
      v-show="totalGrades > 0"
      :total="totalGrades"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="fetchGrades"
    />

    <!-- 添加/编辑成绩对话框 -->
    <el-dialog :visible.sync="dialogVisible" :title="dialogTitle" width="30%" @close="handleCloseDialog">
      <!-- 对话框内容 -->
      <div>
        <el-form :model="gradeForm" label-width="100px">
          <el-form-item label="学生姓名">
            <el-select v-model="gradeForm.userId" placeholder="请选择授课老师" :disabled="isReadOnly">
              <el-option
                v-for="student in studentOptions"
                :key="student.userId"
                :label="student.userName"
                :value="student.userId"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="课程名称">
            <el-select v-model="gradeForm.courseId" placeholder="请选择课程" :disabled="isReadOnly">
              <el-option
                v-for="course in courseOptions"
                :key="course.courseId"
                :label="course.courseName"
                :value="course.courseId"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="成绩">
            <el-input v-model="gradeForm.grade" type="number" :min="0" :max="100" :disabled="isReadOnly"></el-input>
          </el-form-item>
          <el-form-item label="备注">
            <el-input v-model="gradeForm.remarks" type="textarea" :disabled="isReadOnly"></el-input>
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
import { listAllGrades, addGrade, updateGrade, deleteGrade, getGradeById } from '@/api/student/grade'
import {listAllCourses} from "@/api/student/course";
import {listStudent} from "@/api/system/user";

export default {
  data() {
    return {
      loading: false,
      gradeList: [],
      courseOptions: [],
      studentOptions: [],
      dialogVisible: false,
      dialogTitle: '',
      dialogButtonText: '',
      totalGrades: 0,
      isReadOnly: false, // 是否只读模式
      gradeForm: {
        userId: '',
        courseId: '',
        courseName: '',
        grade: '',
        semester: '',
        remarks: ''
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10
      },
    }
  },
  created() {
    this.fetchGrades()
    this.fetchCourses()
    this.fetchStudents()
  },
  methods: {
    fetchGrades() {
      this.loading = true
      listAllGrades(this.queryParams).then(response => {
        this.gradeList = response.rows
        this.totalGrades = response.total
        this.loading = false
      })
    },
    fetchCourses() {
      listAllCourses().then(response => {
        this.courseOptions = response.rows.map(course => ({
          courseId: course.courseId,
          courseName: course.courseName
        }))
        console.log(this.teacherOptions)
      })
    },
    fetchStudents() {
      listStudent(this.queryParams).then(response => {
        this.studentOptions = response.rows.map(student => ({
          userId: student.userId,
          userName: student.nickName
        }))
      })
    },
    handleView(row) {
      this.dialogTitle = '查看成绩'
      this.dialogButtonText = '关闭'
      this.isReadOnly = true
      getGradeById(row.gradeId).then(response => {
        this.gradeForm = response.data
        this.dialogVisible = true
      })
    },
    handleAddGrade() {
      this.isReadOnly = false
      this.dialogTitle = '新增成绩'
      this.dialogButtonText = '添加'
      this.dialogVisible = true
    },
    handleEdit(row) {
      this.isReadOnly = false
      this.dialogTitle = '编辑成绩'
      this.dialogButtonText = '更新'
      this.gradeForm = Object.assign({}, row)
      this.dialogVisible = true
    },
    handleDelete(row) {
      this.$confirm('确认删除该成绩吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteGrade(row.gradeId).then(() => {
          this.$message.success('删除成功')
          this.fetchGrades()
        })
      })
    },
    handleCloseDialog() {
      this.dialogVisible = false
      this.clearForm()
    },
    clearForm() {
      this.gradeForm = {
        userName: '',
        courseName: '',
        grade: '',
        semester: '',
        remarks: ''
      }
    },
    handleSubmit() {
      if (this.dialogButtonText === '添加') {
        addGrade(this.gradeForm).then(() => {
          this.$message.success('添加成功')
          this.dialogVisible = false
          this.clearForm()
          this.fetchGrades()
        })
      } else if (this.dialogButtonText === '更新') {
        updateGrade(this.gradeForm).then(() => {
          this.$message.success('更新成功')
          this.dialogVisible = false
          this.clearForm()
          this.fetchGrades()
        })
      }
    }
  }
}
</script>

<style scoped>
/* 样式可以根据需求调整 */
</style>
