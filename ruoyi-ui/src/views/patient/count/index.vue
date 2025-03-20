<script>
import { countEmergencyPatients, countPatientsByOrganNeeded } from '@/api/patient/patient'
import * as echarts from 'echarts'

export default {
  name: 'PatientStatistics',
  data() {
    return {
      emergencyCount: 0,
      organChart: null,
      organOptions: {
        0: '肝',
        1: '肾'
      }
    }
  },
  mounted() {
    this.initData()
  },
  beforeDestroy() {
    if (this.organChart) {
      this.organChart.dispose()
    }
  },
  methods: {
    async initData() {
      // 获取紧急患者数量
      try {
        const emergencyResponse = await countEmergencyPatients()
        this.emergencyCount = emergencyResponse.data
        console.log('紧急患者数量:', emergencyResponse.data)
      } catch (error) {
        console.error('获取紧急患者数量失败:', error)
        this.$message.error('获取紧急患者数量失败')
      }

      // 获取器官需求分布
      try {
        const organResponse = await countPatientsByOrganNeeded()
        this.initOrganChart(organResponse.data)
        console.log('器官需求分布:', organResponse.data)
      } catch (error) {
        console.error('获取器官需求分布失败:', error)
        this.$message.error('获取器官需求分布失败')
      }
    },
    initOrganChart(data) {
      // 转换数据格式
      const chartData = Object.entries(data).map(([key, value]) => ({
        name: this.organOptions[value.organNeeded] || key,
        value: value.patientCount
      }))

      // 初始化图表
      this.organChart = echarts.init(this.$refs.organChart)

      // 设置图表选项
      const option = {
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b}: {c} ({d}%)'
        },
        legend: {
          orient: 'vertical',
          left: 10,
          data: chartData.map(item => item.name)
        },
        series: [
          {
            name: '器官需求',
            type: 'pie',
            radius: ['50%', '70%'],
            avoidLabelOverlap: false,
            label: {
              show: true,
              position: 'outside',
              formatter: '{b}: {c}人 ({d}%)'
            },
            emphasis: {
              label: {
                show: true,
                fontSize: '16',
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: true
            },
            data: chartData
          }
        ]
      }

      // 使用配置项显示图表
      this.organChart.setOption(option)
    },
    // 处理窗口大小变化
    handleResize() {
      if (this.organChart) {
        this.organChart.resize()
      }
    }
  },
  created() {
    window.addEventListener('resize', this.handleResize)
  },
}
</script>

<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!-- 紧急患者数量卡片 -->
      <el-col :span="8">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>紧急患者数量</span>
          </div>
          <div class="emergency-count">
            <div class="count-number">{{ emergencyCount }}</div>
            <div class="count-label">位患者</div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px;">
      <!-- 器官需求分布图表 -->
      <el-col :span="12">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>器官需求分布</span>
          </div>
          <div ref="organChart" style="height: 400px;"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<style scoped lang="scss">
.app-container {
  padding: 20px;
}

.emergency-count {
  text-align: center;
  padding: 20px;

  .count-number {
    font-size: 36px;
    color: #409EFF;
    font-weight: bold;
    margin-bottom: 10px;
  }

  .count-label {
    font-size: 16px;
    color: #606266;
  }
}

.box-card {
  margin-bottom: 20px;

  .clearfix {
    &:before,
    &:after {
      display: table;
      content: "";
    }
    &:after {
      clear: both;
    }
  }
}
</style>
