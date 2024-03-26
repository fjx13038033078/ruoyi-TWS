<template>
  <div>
    <!-- 预约管理页面内容 -->
    <div v-if="activeTab === 'reservation'">
      <div class="app-container">
        <!-- 预约列表 -->
        <el-table :data="reservationList" v-loading="loading" style="width: 100%" border>
          <el-table-column label="预约ID" prop="registrationId" align="center"></el-table-column>
          <el-table-column label="展览名称" prop="exhibitionName" align="center"></el-table-column>
          <el-table-column label="用户名称" prop="userName" align="center"></el-table-column>
          <el-table-column label="预约时间" prop="registrationTime" align="center"></el-table-column>
          <el-table-column label="预约状态" prop="registrationStatus" align="center">
            <template slot-scope="scope">
              {{ scope.row.registrationStatus === 0 ? '已预约' : '已取消' }}
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" width="200px">
            <template slot-scope="scope">
              <el-button type="text" icon="el-icon-remove-outline" size="mini" @click="cancelReservation(scope.row)" v-hasPermi="['culture:registration:cancel']">取消预约</el-button>
              <el-button type="text" icon="el-icon-delete" size="mini" @click="deleteReservation(scope.row)" v-hasPermi="['culture:registration:delete']">删除预约</el-button>
            </template>
          </el-table-column>
        </el-table>

        <!-- 分页组件 -->
        <pagination
          v-show="totalReservations > 0"
          :total="totalReservations"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="fetchReservations"
        />

      </div>
    </div>
  </div>
</template>

<script>
import {
  cancelExhibitionReservation,
  deleteExhibitionRegistration,
  listAllExhibitionRegistrations
} from "@/api/culture/exhibitionRregistration";

export default {
  data() {
    return {
      loading: true,
      activeTab: 'reservation',
      reservationList: [],
      totalReservations: 0,
      queryParams: {
        pageNum: 1,
        pageSize: 10
      }
    };
  },
  created() {
    this.fetchReservations();
  },
  methods: {
    fetchReservations() {
      this.loading = true;
      listAllExhibitionRegistrations(this.queryParams).then(response => {
        this.reservationList = response.rows;
        this.totalReservations = response.total;
        this.loading = false;
      });
    },
    cancelReservation(row) {
      this.$confirm('确认取消该预约吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        cancelExhibitionReservation(row.registrationId).then(() => {
          this.$message.success('取消预约成功');
          this.fetchReservations();
        });
      });
    },
    //删除预约记录
    deleteReservation(row) {
      this.$confirm('确认删除该预约吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteExhibitionRegistration(row.registrationId).then(() => {
          this.$message.success('删除预约成功');
          this.fetchReservations();
        });
      });
    }
  }
};
</script>

<style scoped>

</style>


