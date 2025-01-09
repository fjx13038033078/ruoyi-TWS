<template>
  <div>
    <!-- 课程管理页面内容 -->
    <div>
      <!-- 添加课程按钮 -->
      <el-row :gutter="20" class="mb-20" style="margin-bottom: 10px; margin-left: 10px; margin-top: 10px">
        <el-col>
          <el-button type="primary"
                     plain
                     icon="el-icon-plus"
                     size="medium"
                     @click="handleAddCourse"
                     v-hasPermi="['student:course:add']">新增课程
          </el-button>
        </el-col>
      </el-row>

      <!-- 课程列表 -->
      <el-table :data="courseList" v-loading="loading" style="width: 100%" border>
        <el-table-column label="课程ID" prop="courseId" align="center"></el-table-column>
        <el-table-column label="课程名称" prop="courseName" align="center"></el-table-column>
        <el-table-column label="学分" prop="courseCredits" align="center"></el-table-column>
        <el-table-column label="学时" prop="courseHours" align="center"></el-table-column>
        <el-table-column label="授课教师" prop="instructorName" align="center"></el-table-column>
        <el-table-column label="开课学期" prop="semester" align="center"></el-table-column>
        <el-table-column label="操作" align="center" width="210px">
          <template slot-scope="scope">
            <el-button type="text" icon="el-icon-view" size="mini" @click="handleView(scope.row)"
                       v-hasPermi="['student:course:view']">查看
            </el-button>
            <el-button type="text" icon="el-icon-edit" size="mini" @click="handleEdit(scope.row)"
                       v-hasPermi="['student:course:edit']">编辑
            </el-button>
            <el-button type="text" icon="el-icon-delete" size="mini" @click="handleDelete(scope.row)"
                       v-hasPermi="['student:course:delete']">删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 分页组件 -->
    <pagination
      v-show="totalCourses > 0"
      :total="totalCourses"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="fetchCourses"
    />

    <!-- 添加/编辑课程对话框 -->
    <el-dialog :visible.sync="dialogVisible" :title="dialogTitle" width="30%" @close="handleCloseDialog">
      <!-- 对话框内容 -->
      <div>
        <el-form :model="courseForm" label-width="100px">
          <el-form-item label="课程名称">
            <el-input v-model="courseForm.courseName" :disabled="isReadOnly"></el-input>
          </el-form-item>
          <el-form-item label="学分">
            <el-input v-model="courseForm.courseCredits" type="number" :min=0 :max=8
                      :disabled="isReadOnly"></el-input>
          </el-form-item>
          <el-form-item label="学时">
            <el-input v-model="courseForm.courseHours" type="number" :min=0 :max=30
                      :disabled="isReadOnly"></el-input>
          </el-form-item>
          <el-form-item label="授课教师">
            <el-select v-model="courseForm.instructorId" placeholder="请选择授课老师" :disabled="isReadOnly">
              <el-option
                v-for="teacher in teacherOptions"
                :key="teacher.teacherId"
                :label="teacher.teacherName"
                :value="teacher.teacherId"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="开课学期">
            <el-select v-model="courseForm.semester" :disabled="isReadOnly">
              <el-option label="春季" value="春季"></el-option>
              <el-option label="秋季" value="秋季"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="课程描述">
            <el-input v-model="courseForm.courseDescription" :disabled="isReadOnly" type="textarea" autosize></el-input>
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
import {listAllCourses, addCourse, updateCourse, deleteCourse, getCourseById} from '@/api/student/course'
import {listTeacher} from "@/api/system/user";

export default {
  data() {
    return {
      loading: false,
      courseList: [],
      teacherOptions: [],
      dialogVisible: false,
      dialogTitle: '',
      dialogButtonText: '',
      totalCourses: 0,
      isReadOnly: false, // 是否只读模式
      courseForm: {
        courseName: '',
        courseCredits: '',
        courseHours: '',
        instructorId: '',
        semester: '',
        courseDescription: ''
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10
      },
    }
  },
  created() {
    this.fetchCourses()
    this.fetchTeachers()
  },
  methods: {
    fetchTeachers() {
      this.loading = true
      listTeacher(this.queryParams).then(response => {
        this.teacherOptions = response.rows.map(teacher => ({
          teacherId: teacher.userId,
          teacherName: teacher.nickName
        }))
        console.log(this.teacherOptions)
      })
    },
    fetchCourses() {
      this.loading = true
      listAllCourses(this.queryParams).then(response => {
        this.courseList = response.rows
        this.totalCourses = response.total
        this.loading = false
      })
    },
    handleView(row) {
      this.dialogTitle = '查看课程'
      this.dialogButtonText = '关闭'
      this.isReadOnly = true
      getCourseById(row.courseId).then(response => {
        this.courseForm = response.data
        this.dialogVisible = true
      })
    },
    handleAddCourse() {
      this.isReadOnly = false
      this.dialogTitle = '新增课程'
      this.dialogButtonText = '添加'
      this.dialogVisible = true
    },
    handleEdit(row) {
      this.isReadOnly = false
      this.dialogTitle = '编辑课程'
      this.dialogButtonText = '更新'
      this.courseForm = Object.assign({}, row)
      this.dialogVisible = true
    },
    handleDelete(row) {
      this.$confirm('确认删除该课程吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteCourse(row.courseId).then(() => {
          this.$message.success('删除成功')
          this.fetchCourses()
        })
      })
    },
    handleCloseDialog() {
      this.dialogVisible = false
      this.clearForm()
    },
    clearForm() {
      this.courseForm = {
        courseName: '',
        courseCredits: '',
        courseHours: '',
        instructorName: '',
        semester: '',
        courseDescription: ''
      }
    },
    handleSubmit() {
      if (this.dialogButtonText === '添加') {
        addCourse(this.courseForm).then(() => {
          this.$message.success('添加成功')
          this.dialogVisible = false
          this.clearForm()
          this.fetchCourses()
        })
      } else if (this.dialogButtonText === '更新') {
        updateCourse(this.courseForm).then(() => {
          this.$message.success('更新成功')
          this.dialogVisible = false
          this.clearForm()
          this.fetchCourses()
        })
      }
    }
  }
}
</script>

<style scoped>
/* 样式可以根据需求调整 */
</style>
