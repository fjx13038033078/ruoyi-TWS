<template>
  <el-form ref="form" :model="form" :rules="rules" label-width="80px">
    <el-form-item label="用户昵称" prop="nickName">
      <el-input v-model="form.nickName" maxlength="30" />
    </el-form-item>
    <el-form-item label="手机号码" prop="phonenumber">
      <el-input v-model="form.phonenumber" maxlength="11" />
    </el-form-item>
    <el-form-item label="邮箱" prop="email">
      <el-input v-model="form.email" maxlength="50" />
    </el-form-item>
    <el-form-item label="紧急联系人" prop="emergencyContact">
      <el-input v-model="form.emergencyContact" maxlength="11" />
    </el-form-item>
    <el-form-item label="身份证号" prop="idnumber">
      <el-input v-model="form.idnumber" maxlength="18" />
    </el-form-item>
    <el-form-item label="年龄" prop="age">
      <el-input v-model="form.age" type="number" maxlength="50" />
    </el-form-item>
    <el-form-item label="居住地址" prop="address">
      <el-input v-model="form.address" maxlength="50" />
    </el-form-item>
    <el-form-item label="性别">
      <el-radio-group v-model="form.sex">
        <el-radio label="0">男</el-radio>
        <el-radio label="1">女</el-radio>
      </el-radio-group>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" size="mini" @click="submit">保存</el-button>
      <el-button type="danger" size="mini" @click="close">关闭</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import { updateUserProfile } from "@/api/system/user";

export default {
  props: {
    user: {
      type: Object
    }
  },
  data() {
    // 身份证号码验证函数
    const validateIdNumber = (rule, value, callback) => {
      if (!value) {
        callback(new Error('身份证号码不能为空'));
        return;
      }
      
      // 验证是否为18位
      if (value.length !== 18) {
        callback(new Error('身份证号码必须为18位'));
        return;
      }

      // 验证格式是否正确（前17位为数字，最后一位可以是数字或X）
      const reg = /^[1-9]\d{5}(19|20)\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\d|3[01])\d{3}[\dXx]$/;
      if (!reg.test(value)) {
        callback(new Error('请输入正确的身份证号码'));
        return;
      }

      // 验证校验位
      const factor = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2];
      const parity = [1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2];
      const code = value.substring(0, 17);
      const last = value.substring(17, 18).toUpperCase();

      let sum = 0;
      for (let i = 0; i < 17; i++) {
        sum += parseInt(code[i]) * factor[i];
      }
      
      if (parity[sum % 11].toString() !== last) {
        callback(new Error('身份证号码校验位错误'));
        return;
      }

      callback();
    };

    return {
      form: {},
      // 表单校验
      rules: {
        nickName: [
          { required: true, message: "用户姓名不能为空", trigger: "blur" }
        ],
        email: [
          { required: true, message: "邮箱地址不能为空", trigger: "blur" },
          {
            type: "email",
            message: "请输入正确的邮箱地址",
            trigger: ["blur", "change"]
          }
        ],
        phonenumber: [
          { required: true, message: "手机号码不能为空", trigger: "blur" },
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: "请输入正确的手机号码",
            trigger: "blur"
          }
        ],
        emergencyContact: [
          { required: true, message: "紧急联系人号码不能为空", trigger: "blur" },
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: "请输入正确的手机号码",
            trigger: "blur"
          }
        ],
        idnumber: [
          { required: true, message: "身份证号码不能为空", trigger: "blur" },
          { validator: validateIdNumber, trigger: "blur" }
        ]
      }
    };
  },
  watch: {
    user: {
      handler(user) {
        if (user) {
          this.form = { nickName: user.nickName, phonenumber: user.phonenumber, email: user.email,
            sex: user.sex, idnumber: user.idnumber, age: user.age, address: user.address };
        }
      },
      immediate: true
    }
  },
  methods: {
    submit() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          updateUserProfile(this.form).then(response => {
            this.$modal.msgSuccess("修改成功");
            this.user.phonenumber = this.form.phonenumber;
            this.user.email = this.form.email;
            this.user.idnumber = this.form.idnumber;
            this.user.age = this.form.age;
            this.user.address = this.form.address;
            this.user.emergencyContact = this.form.emergencyContact;
          });
        }
      });
    },
    close() {
      this.$tab.closePage();
    }
  }
};
</script>
